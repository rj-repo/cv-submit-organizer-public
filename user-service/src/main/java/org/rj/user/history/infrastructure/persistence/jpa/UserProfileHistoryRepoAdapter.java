package org.rj.user.history.infrastructure.persistence.jpa;

import lombok.RequiredArgsConstructor;
import org.rj.user.history.domain.model.UserProfileHistory;
import org.rj.user.history.domain.ports.out.UserProfileHistoryRepoPort;
import org.rj.user.history.infrastructure.persistence.jpa.entity.UserProfileHistoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserProfileHistoryRepoAdapter implements UserProfileHistoryRepoPort {

    private final JpaUserProfileHistoryRepo jpaUserProfileHistoryRepo;

    @Override
    public void saveHistory(UserProfileHistory userProfileHistory) {
        jpaUserProfileHistoryRepo.save(UserProfileHistoryEntity.toEntity(userProfileHistory));
    }

    @Override
    public List<UserProfileHistory> findByEmail(String newEmail) {
        return jpaUserProfileHistoryRepo.findByPreviousEmail(newEmail).stream()
                .map(UserProfileHistoryEntity::toAggregate)
                .toList();
    }
}
