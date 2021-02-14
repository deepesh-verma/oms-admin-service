package org.navya.oms.omsadminservices.entity;

import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity(name = "user_contact")
public class UserContactEntity extends BaseAuditedEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "user_contact_id")
  private UUID userContactId;

  @OneToOne
  @JoinColumn(name = "contact_type_id")
  private ContactTypeEntity contactTypeEntity;

  @NotNull
  @Column(name = "user_contact_number")
  private String userContactNumber;

  @NotNull
  @OneToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;
}
