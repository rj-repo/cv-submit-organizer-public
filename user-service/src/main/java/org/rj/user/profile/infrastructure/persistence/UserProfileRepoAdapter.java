package org.rj.user.profile.infrastructure.persistence;

import lombok.AllArgsConstructor;
import org.rj.user.profile.domain.model.UserProfile;
import org.rj.user.profile.domain.ports.out.UserProfileRepoPort;
import org.rj.user.profile.infrastructure.persistence.entity.UserProfileEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserProfileRepoAdapter implements UserProfileRepoPort {

    private final JpaUserProfileRepo userProfileRepo;

    @Override
    public void save(UserProfile userProfile) {
        userProfileRepo.save(UserProfileEntity.toEntity(userProfile));
    }

    @Override
    public Optional<UserProfile> findByEmail(String email) {
        return userProfileRepo.findByEmail(email).map(UserProfileEntity::toAggregate);
    }

    @Override
    public Optional<UserProfile> findByIdAndEmail(Long id, String email) {
        return userProfileRepo.findByIdAndEmail(id,email).map(UserProfileEntity::toAggregate);
    }

    @Override
    public Optional<UserProfile> findById(Long id) {
        return userProfileRepo.findById(id).map(UserProfileEntity::toAggregate);
    }


}
