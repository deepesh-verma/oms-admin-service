package org.navya.oms.omsadminservices.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressTypeModel {

  UUID addressTypeId;
  String addressTypeCode;
  String addressTypeName;
  LocalDateTime createTimestamp;
  LocalDateTime updateTimestamp;
}
