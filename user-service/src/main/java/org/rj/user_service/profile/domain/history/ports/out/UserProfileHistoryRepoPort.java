package org.rj.user_service.profile.domain.history.ports.out;

import org.rj.user_service.profile.domain.history.model.UserProfileHistory;

import java.util.List;

public interface UserProfileHistoryRepoPort {

    void saveHistory(UserProfileHistory userProfileHistory);

    List<UserProfileHistory> findByEmail(String email);
}
