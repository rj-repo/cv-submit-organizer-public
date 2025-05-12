package org.rj.auth_service.application.user.usecase;

import lombok.RequiredArgsConstructor;
import org.rj.auth_service.application.user.exception.AuthUserEmailModifyException;
import org.rj.auth_service.application.user.exception.AuthUserNotFoundException;
import org.rj.auth_service.domain.user.dto.UserModifyRequest;
import org.rj.auth_service.domain.user.model.AuthUser;
import org.rj.auth_service.domain.user.ports.in.ModifyUserUserCase;
import org.rj.auth_service.domain.user.ports.out.UserAuthRepoPort;
import org.rj.cvsubmitorganizer.common.UseCaseService;

@UseCaseService
@RequiredArgsConstructor
public class ModifyUserService implements ModifyUserUserCase {

    private final UserAuthRepoPort userAuthRepoPort;
    @Override
    public void modify(String email, String id, UserModifyRequest userModifyRequest) {
        AuthUser authUser = userAuthRepoPort.findByEmail(email)
                .orElseThrow(() -> new AuthUserNotFoundException("Auth user with " + email + "not exists"));
        userAuthRepoPort.findByEmail(userModifyRequest.email()).ifPresent(userAuth -> {
            throw new AuthUserEmailModifyException("Cannot changed email address - it might be already in use");
        });
        authUser.alterEmail(userModifyRequest.email());
        userAuthRepoPort.save(authUser);
    }
}
