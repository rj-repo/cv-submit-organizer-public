package org.rj.user.profile.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserProfile {
    private UserProfileId id;
    private String email;
    private String firstname;
    private String surname;


    public void updateProfile(ModifyUserProfileCommand modifyUserProfileCommand) {
        this.email = modifyUserProfileCommand.email();
    }

    public void isValueAlreadySet(ModifyUserProfileCommand modifyUserProfileCommand) {
        if(email.equals(modifyUserProfileCommand.email())){
            throw new UserProfileDomainException(String.format("Email %s is already use", modifyUserProfileCommand.email()));
        }
    }
}
