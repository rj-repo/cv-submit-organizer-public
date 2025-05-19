package org.rj.application_job.application.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rj.application_job.application.rest.dto.AddJobApplicationRequest;
import org.rj.application_job.application.rest.dto.ModifyJobApplicationRequest;
import org.rj.application_job.application.rest.dto.UserJobApplicationResponse;
import org.rj.application_job.domain.model.command.GetUserApplicationsJobCommand;
import org.rj.application_job.domain.model.response.UserJobApplications;
import org.rj.application_job.domain.ports.in.AddJobApplicationUseCase;
import org.rj.application_job.domain.ports.in.DeleteJobApplicationUseCase;
import org.rj.application_job.domain.ports.in.GetApplicationJobsUseCase;
import org.rj.application_job.domain.ports.in.ModifyJobApplicationUseCase;
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

    @PostMapping
    public ResponseEntity<Void> addJob(@RequestHeader("X-User-Id") Long userId,
                                       @Valid @ModelAttribute AddJobApplicationRequest request) {
        addJobApplicationUseCase.addApplicationJob(request.toCommand(userId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@RequestHeader("X-User-Id") Long userId,
                                          @PathVariable("id") Long id) {
        deleteJobApplication.delete(id, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> modifyJob(@RequestHeader("X-User-Id") Long userId,
                                          @Valid @ModelAttribute ModifyJobApplicationRequest request) {
        modifyJobApplication.modify(request.toCommand(userId));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserJobApplicationResponse> getJobs(@RequestHeader("X-User-Id") Long userId,
                                                              @RequestParam(value = "page") Long page,
                                                              @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize) {
        UserJobApplications jobs = getApplicationJobs.getJobs(new GetUserApplicationsJobCommand(page,pageSize,new UserProfileId(userId)));
        return ResponseEntity.ok(UserJobApplicationResponse.toResponse(jobs));
    }

}
