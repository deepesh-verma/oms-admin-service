package org.navya.oms.omsadminservices.entity;

import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity(name = "contact_type")
public class ContactTypeEntity extends BaseAuditedEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "contact_type_id")
  private UUID contactTypeId;

  @NotNull
  @Size(max = 16)
  @Column(name = "contact_type_code")
  private String contactTypeCode;

  @NotNull
  @Size(max = 32)
  @Column(name = "contact_type_name")
  private String contactTypeName;
}
