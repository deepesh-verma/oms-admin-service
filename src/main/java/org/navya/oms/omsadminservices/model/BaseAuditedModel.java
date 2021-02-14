package org.navya.oms.omsadminservices.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BaseAuditedModel {

  protected LocalDateTime createTimestamp;
  protected LocalDateTime updateTimestamp;
}
