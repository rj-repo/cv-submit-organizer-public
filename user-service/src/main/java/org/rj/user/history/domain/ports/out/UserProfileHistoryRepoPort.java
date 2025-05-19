package org.rj.user.history.domain.ports.out;

import org.rj.user.history.domain.model.UserProfileHistory;

import java.util.List;

public interface UserProfileHistoryRepoPort {

    void saveHistory(UserProfileHistory userProfileHistory);

    List<UserProfileHistory> findByEmail(String email);
}
