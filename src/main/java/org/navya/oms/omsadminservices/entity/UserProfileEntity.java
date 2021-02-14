package org.navya.oms.omsadminservices.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.navya.oms.omsadminservices.model.SexEnum;
import org.navya.oms.omsadminservices.model.UserModel;

@Data
@NoArgsConstructor
@Entity(name = "user_profile")
public class UserProfileEntity extends BaseAuditedEntity {

  @Id
  @Column(name = "user_id")
  private UUID userId;

  @NotNull
  @Column(name = "first_name")
  private String firstName;

  @NotNull
  @Column(name = "last_name")
  private String lastName;

  @NotNull
  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @NotNull
  @Min(0)
  @Column(name = "age")
  private Integer age;

  @Enumerated(EnumType.STRING)
  @NotNull
  @Column(name = "sex")
  private SexEnum sex;

  @NotNull
  @Email
  @Column(name = "email")
  private String email;

  @NotNull
  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id", updatable = false, insertable = false)
  private UserEntity user;

  public UserProfileEntity(UserModel userModel, UserEntity userEntity) {

    this.userId = userModel.getUserId();
    this.firstName = userModel.getFirstName();
    this.lastName = userModel.getLastName();
    this.dateOfBirth = userModel.getDateOfBirth();
    this.age = userModel.getAge();
    this.sex = userModel.getSex();
    this.email = userModel.getEmail();
    this.createTimestamp = userModel.getCreateTimestamp();
    this.updateTimestamp = LocalDateTime.now();

    this.user = userEntity;
  }
}
