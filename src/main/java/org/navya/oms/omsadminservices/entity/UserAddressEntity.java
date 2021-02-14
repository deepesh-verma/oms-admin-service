package org.navya.oms.omsadminservices.entity;

import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

@Data
@Entity(name = "user_address")
public class UserAddressEntity extends BaseAuditedEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "user_address_id")
  private UUID userAddressId;

  @OneToOne
  @JoinColumn(name = "address_type_id")
  private AddressTypeEntity addressTypeEntity;

  @NotNull
  @Length(min = 2, max = 2)
  @Column(name = "country_code")
  private String countryCode;

  @NotNull
  @Column(name = "state")
  private String state;

  @NotNull
  @Column(name = "district")
  private String district;

  @NotNull
  @Column(name = "city")
  private String city;

  @NotNull
  @Column(name = "po_box")
  private String poBox;

  @NotNull
  @OneToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;
}
