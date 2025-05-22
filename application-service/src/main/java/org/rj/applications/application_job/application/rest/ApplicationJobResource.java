package org.rj.applications.application_job.application.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rj.applications.application_job.application.rest.dto.AddJobApplicationRequest;
import org.rj.applications.application_job.application.rest.dto.ModifyJobApplicationRequest;
import org.rj.applications.application_job.application.rest.dto.UserJobApplicationResponse;
import org.rj.applications.application_job.domain.model.command.GetUserApplicationsJobCommand;
import org.rj.applications.application_job.domain.model.response.UserJobApplications;
import org.rj.applications.application_job.domain.ports.in.AddJobApplicationUseCase;
import org.rj.applications.application_job.domain.ports.in.DeleteJobApplicationUseCase;
import org.rj.applications.application_job.domain.ports.in.GetApplicationJobsUseCase;
import org.rj.applications.application_job.domain.ports.in.ModifyJobApplicationUseCase;
import org.rj.cvsubmitorganizer.common.ApiResponseException;
import org.rj.cvsubmitorganizer.common.UserProfileId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationJobResource {

    private final AddJobApplicationUseCase addJobApplicationUseCase;
    private final DeleteJobApplicationUseCase deleteJobApplication;
    private final ModifyJobApplicationUseCase modifyJobApplication;
    private final GetApplicationJobsUseCase getApplicationJobs;

    @RequestBody(content = @Content(schema = @Schema(implementation = AddJobApplicationRequest.class)))
    @Operation(summary = "Add new job application")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "Application job has been added"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))})})
    @PostMapping
    public ResponseEntity<Void> addJob(@RequestHeader("X-User-Id") Long userId,
                                       @Valid @ModelAttribute AddJobApplicationRequest request) {
        addJobApplicationUseCase.addApplicationJob(request.toCommand(userId));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete job application")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "Application job has been deleted"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))})
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@RequestHeader("X-User-Id") Long userId,
                                          @PathVariable("id") Long id) {
        deleteJobApplication.delete(id, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Modify job application")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "Application job has been modified"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))})
            })
    @PatchMapping
    public ResponseEntity<Void> modifyJob(@RequestHeader("X-User-Id") Long userId,
                                          @Valid @ModelAttribute ModifyJobApplicationRequest request) {
        modifyJobApplication.modify(request.toCommand(userId));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all job applications")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "Get all applications job for specific user",content = {@Content(schema = @Schema(implementation = UserJobApplicationResponse.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))})
            })
    @GetMapping
    public ResponseEntity<UserJobApplicationResponse> getJobs(@RequestHeader("X-User-Id") Long userId,
                                                              @RequestParam(value = "page") Long page,
                                                              @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize) {
        UserJobApplications jobs = getApplicationJobs.getJobs(new GetUserApplicationsJobCommand(page,pageSize,new UserProfileId(userId)));
        return ResponseEntity.ok(UserJobApplicationResponse.toResponse(jobs));
    }

}
