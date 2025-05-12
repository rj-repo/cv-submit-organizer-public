package org.rj.user_service.profile.infrastructure.history.persistence.jpa;

import org.rj.user_service.profile.infrastructure.history.persistence.jpa.entity.UserProfileHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JpaUserProfileHistoryRepo extends JpaRepository<UserProfileHistoryEntity, Long> {

     List<UserProfileHistoryEntity> findByPreviousEmail(String previousEmail);
}
