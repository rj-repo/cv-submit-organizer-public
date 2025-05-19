package org.rj.auth.user.application.usecase;

import lombok.RequiredArgsConstructor;
import org.rj.auth.user.application.exception.AuthUserEmailModifyException;
import org.rj.auth.user.application.exception.AuthUserNotFoundException;
import org.rj.auth.user.domain.user.command.UserModifyCommand;
import org.rj.auth.user.domain.user.model.AuthUser;
import org.rj.auth.user.domain.user.ports.in.ModifyUserUserCase;
import org.rj.auth.user.domain.user.ports.out.UserAuthRepoPort;
import org.rj.cvsubmitorganizer.common.UseCaseService;

@UseCaseService
@RequiredArgsConstructor
public class ModifyUserService implements ModifyUserUserCase {

    private final UserAuthRepoPort userAuthRepoPort;
    @Override
    public void modify(String email, String id, UserModifyCommand userModifyCommand) {
        AuthUser authUser = userAuthRepoPort.findByEmail(email)
                .orElseThrow(() -> new AuthUserNotFoundException("Auth user with " + email + "not exists"));
        userAuthRepoPort.findByEmail(userModifyCommand.email()).ifPresent(userAuth -> {
            throw new AuthUserEmailModifyException("Cannot changed email address - it might be already in use");
        });
        authUser.alterEmail(userModifyCommand.email());
        userAuthRepoPort.save(authUser);
    }
}
