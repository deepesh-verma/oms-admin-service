package org.navya.oms.omsadminservices.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.navya.oms.omsadminservices.model.AddressTypeModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Entity
@Table(name = "address_type", schema = "oms")
public class AddressTypeEntity extends BaseAuditedEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "address_type_id")
    private UUID addressTypeId;

    @NotNull
    @Size(max = 16)
    @Column(name = "address_type_code")
    private String addressTypeCode;

    @NotNull
    @Size(max = 32)
    @Column(name = "address_type_name")
    private String addressTypeName;

    public AddressTypeEntity(AddressTypeModel addressTypeModel) {
        super(addressTypeModel.getCreateTimestamp(), addressTypeModel.getUpdateTimestamp());
        this.addressTypeId = addressTypeModel.getAddressTypeId();
        this.addressTypeCode = addressTypeModel.getAddressTypeCode();
        this.addressTypeName = addressTypeModel.getAddressTypeName();
    }
}
