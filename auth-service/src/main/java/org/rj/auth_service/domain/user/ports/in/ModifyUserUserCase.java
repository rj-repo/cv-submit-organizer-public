package org.rj.auth_service.domain.user.ports.in;

import org.rj.auth_service.domain.user.dto.UserModifyRequest;

public interface ModifyUserUserCase {

    void modify(String email, String id, UserModifyRequest userModifyRequest);
}
