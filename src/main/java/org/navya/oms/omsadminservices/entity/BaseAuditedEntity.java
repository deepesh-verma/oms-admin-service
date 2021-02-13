package org.navya.oms.omsadminservices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
