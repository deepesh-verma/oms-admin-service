package org.navya.oms.omsadminservices.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.navya.oms.omsadminservices.entity.UserEntity;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel extends BaseAuditedModel {

  UUID userId;

  @NotNull String userName;

  @NotNull Integer isSoftDeleted;

  @NotNull String firstName;

  @NotNull String lastName;

  @NotNull LocalDate dateOfBirth;

  @NotNull Integer age;

  @NotNull String sex;

  @NotNull String email;

  LocalDateTime lastLoginTimestamp;

  public UserModel(UserEntity userEntity) {
    this.userId = userEntity.getUserId();
    this.userName = userEntity.getUserName();
    this.isSoftDeleted = userEntity.getIsSoftDeleted();
    this.firstName = userEntity.getUserProfile().getFirstName();
    this.lastName = userEntity.getUserProfile().getLastName();
    this.dateOfBirth = userEntity.getUserProfile().getDateOfBirth();
    ;
    this.age = userEntity.getUserProfile().getAge();
    this.sex = userEntity.getUserProfile().getSex();
    this.email = userEntity.getUserProfile().getSex();
    this.lastLoginTimestamp = userEntity.getLastLoginTimestamp();
  }
}
