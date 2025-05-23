package org.rj.user.profile.application.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rj.cvsubmitorganizer.common.ApiResponseException;
import org.rj.user.profile.application.rest.dto.UserProfileDetailsResponse;
import org.rj.user.profile.domain.model.GetMyProfileCommand;
import org.rj.user.profile.domain.model.ModifyUserProfileCommand;
import org.rj.user.profile.domain.model.SaveNewUserCommand;
import org.rj.user.profile.domain.model.UserProfile;
import org.rj.user.profile.domain.ports.in.GetMyProfileUseCase;
import org.rj.user.profile.domain.ports.in.ModifyProfileUseCase;
import org.rj.user.profile.domain.ports.in.SaveUserProfileUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileResource {

    private final SaveUserProfileUseCase saveUserProfileUseCase;
    private final GetMyProfileUseCase getMyProfileUseCase;
    private final ModifyProfileUseCase modifyProfileUseCase;

    @Operation(summary = "Register user")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = SaveNewUserCommand.class)))
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "User has been registered"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))})})
    @PostMapping(path = "/registration")
    public ResponseEntity<Void> save(@RequestBody @Valid SaveNewUserCommand saveNewUserCommand) {
        saveUserProfileUseCase.save(saveNewUserCommand);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get user profile")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "Profile user found", content = {@Content(schema = @Schema(implementation = UserProfileDetailsResponse.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))}),
                    @ApiResponse(responseCode = "404", description = "Profile not found", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))})})
    @GetMapping(path = "/my-profile")
    public ResponseEntity<UserProfileDetailsResponse> getMyProfile(@RequestHeader("X-User-Id") String userId,
                                                                   @RequestHeader("X-Email") String email) {
        UserProfile myProfile = getMyProfileUseCase.getMyProfile(new GetMyProfileCommand(Long.valueOf(userId), email));
        return ResponseEntity.ok(UserProfileDetailsResponse.of(myProfile));
    }


    @Operation(summary = "Update user profile email")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = ModifyUserProfileCommand.class)))
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "Profile user found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))}),
                    @ApiResponse(responseCode = "404", description = "Product not found", content = {@Content(schema = @Schema(implementation = ApiResponseException.class))})})
    @PatchMapping(path = "/my-profile")
    public ResponseEntity<Void> updateMyProfile(@RequestHeader("X-User-Id") String userId,
                                                @RequestBody @Valid ModifyUserProfileCommand modifyUserProfileCommand) {
        modifyProfileUseCase.modifyProfile(Long.valueOf(userId), modifyUserProfileCommand);
        return ResponseEntity.ok().build();
    }

}
