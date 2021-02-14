package org.navya.oms.omsadminservices.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.navya.oms.omsadminservices.model.UserModel;

@Data
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity extends BaseAuditedEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "is_soft_deleted")
  private Integer isSoftDeleted;

  @Column(name = "last_login_ts")
  private LocalDateTime lastLoginTimestamp;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private UserProfileEntity userProfile;

  public UserEntity(UserModel userModel) {
    this.userId = userModel.getUserId();
    this.userName = userModel.getUserName();
    this.isSoftDeleted = userModel.getIsSoftDeleted();
    this.lastLoginTimestamp = userModel.getLastLoginTimestamp();
    this.userProfile = new UserProfileEntity(userModel, this);
    this.createTimestamp = userModel.getCreateTimestamp();
    this.updateTimestamp = LocalDateTime.now();
  }
}
