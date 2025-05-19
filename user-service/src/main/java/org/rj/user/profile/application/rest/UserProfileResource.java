package org.rj.user.profile.application.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(path = "/registration")
    public ResponseEntity<Void> save(@RequestBody @Valid SaveNewUserCommand saveNewUserCommand) {
        saveUserProfileUseCase.save(saveNewUserCommand);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserProfileDetailsResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content) })
    @GetMapping(path = "/my-profile")
    public ResponseEntity<UserProfileDetailsResponse> getMyProfile(@RequestHeader("X-User-Id") String userId,
                                                                   @RequestHeader("X-Email") String email) {
        UserProfile myProfile = getMyProfileUseCase.getMyProfile(new GetMyProfileCommand(Long.valueOf(userId), email));
        return ResponseEntity.ok(UserProfileDetailsResponse.of(myProfile));
    }

    @PatchMapping(path = "/my-profile")
    public ResponseEntity<Void> updateMyProfile(@RequestHeader("X-User-Id") String userId,
                                                @RequestBody @Valid ModifyUserProfileCommand modifyUserProfileCommand) {
        modifyProfileUseCase.modifyProfile(Long.valueOf(userId), modifyUserProfileCommand);
        return ResponseEntity.ok().build();
    }

}
