package org.rj.user.history.infrastructure.persistence.jpa;

import org.rj.user.history.infrastructure.persistence.jpa.entity.UserProfileHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JpaUserProfileHistoryRepo extends JpaRepository<UserProfileHistoryEntity, Long> {

     List<UserProfileHistoryEntity> findByPreviousEmail(String previousEmail);
}
