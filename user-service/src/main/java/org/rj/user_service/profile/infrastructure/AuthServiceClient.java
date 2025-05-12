package org.rj.user_service.profile.infrastructure;

import jakarta.validation.Valid;
import org.rj.user_service.profile.application.rest.dto.UserProfileIdResponse;
import org.rj.user_service.profile.domain.model.ModifyUserProfileCommand;
import org.rj.user_service.profile.domain.model.RegisterAuthCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", path = "/api/v1/auth")
public interface AuthServiceClient {

    @PostMapping("/registration")
    ResponseEntity<UserProfileIdResponse> registerAuthUser(@RequestHeader("X-Internal-Call") String internalCall,
                                                           @RequestBody @Valid RegisterAuthCommand registeredUser);

    @PostMapping("/user/{id}")
    ResponseEntity<Void> modifyAuthUser(@RequestHeader("X-Internal-Call") String internalCall,
                                        @PathVariable("id") String userId,
                                        @RequestHeader("X-Email") String previousEmail,
                                        @RequestBody @Valid ModifyUserProfileCommand registeredUser);

}
