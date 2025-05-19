package org.rj.user.history.infrastructure.persistence.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.rj.user.history.domain.model.UserProfileHistory;
import org.rj.user.history.domain.model.UserProfileHistoryId;
import org.rj.user.profile.domain.model.UserProfileId;
import org.rj.user.profile.infrastructure.persistence.entity.UserProfileEntity;

@Entity
@Table(name = "user_history",schema = "profiles")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserProfileHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String previousEmail;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserProfileEntity userId;

    public UserProfileHistory toAggregate(){
        return UserProfileHistory.builder()
                .id(new UserProfileHistoryId(id))
                .previousEmail(previousEmail)
                .userId(new UserProfileId(userId.getId()))
                .build();
    }

    public static UserProfileHistoryEntity toEntity(UserProfileHistory userProfileHistory){
        UserProfileEntity userProfileEntity = UserProfileEntity.builder().id(userProfileHistory.getUserId().id()).build();
        return UserProfileHistoryEntity.builder()
                .id(userProfileHistory.getId() != null ? userProfileHistory.getId().id() : null)
                .previousEmail(userProfileHistory.getPreviousEmail())
                .userId(userProfileEntity)
                .build();
    }
}
