package org.navya.oms.omsadminservices.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AddressTypeModel {

    UUID addressTypeId;
    String addressTypeCode;
    String addressTypeName;
    LocalDateTime createTimestamp;
    LocalDateTime updateTimestamp;
}
