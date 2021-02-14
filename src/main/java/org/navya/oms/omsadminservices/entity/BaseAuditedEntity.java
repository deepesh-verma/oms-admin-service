package org.navya.oms.omsadminservices.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseAuditedEntity {

  @NotNull
  @Column(name = "CREATE_TS")
  protected LocalDateTime createTimestamp;

  @Column(name = "UPDATE_TS")
  protected LocalDateTime updateTimestamp;
}
