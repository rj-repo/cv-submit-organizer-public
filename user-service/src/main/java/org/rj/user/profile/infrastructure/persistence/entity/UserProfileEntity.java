package org.rj.user.profile.infrastructure.persistence.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rj.user.profile.domain.model.UserProfile;
import org.rj.user.profile.domain.model.UserProfileId;

@Entity
@Table(name = "users",schema = "profiles")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class UserProfileEntity {

    @Id
    private Long id;
    private String email;
    private String firstname;
    private String surname;

    public UserProfile toAggregate(){
        return UserProfile.builder()
                .id(new UserProfileId(id))
                .email(email)
                .firstname(firstname)
                .surname(surname)
                .build();
    }

    public static UserProfileEntity toEntity(UserProfile userProfile){
        return UserProfileEntity.builder()
                .id(userProfile.getId().id())
                .email(userProfile.getEmail())
                .firstname(userProfile.getFirstname())
                .surname(userProfile.getSurname())
                .build();
    }
}
