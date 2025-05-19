package org.rj.auth.user.domain.user.ports.in;

import org.rj.auth.user.domain.user.command.UserModifyCommand;

public interface ModifyUserUserCase {

    void modify(String email, String id, UserModifyCommand userModifyCommand);
}
