package org.rj.user.profile.domain.ports.out;

import org.rj.user.profile.domain.model.UserProfile;

import java.util.Optional;

public interface UserProfileRepoPort {

    void save(UserProfile userProfile);
    Optional<UserProfile> findByEmail(String email);
    Optional<UserProfile> findById(Long id);
    Optional<UserProfile> findByIdAndEmail(Long id,String email);

}
