package org.navya.oms.omsadminservices.service;

import org.junit.jupiter.api.Test;
import org.navya.oms.omsadminservices.OmsAdminServicesApplicationTests;
import org.navya.oms.omsadminservices.model.AddressTypeModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressTypeServiceTest extends OmsAdminServicesApplicationTests {

    @Autowired private AddressTypeService addressTypeService;

    @Test
    void testSaveAddressType() {
        AddressTypeModel newAddressType = AddressTypeModel.builder()
                .addressTypeCode("BILLING")
                .addressTypeName("Billing Address")
                .createTimestamp(LocalDateTime.of(LocalDate.now(), LocalTime.now()))
                .build();
        AddressTypeModel response = addressTypeService.saveAddressType(newAddressType);
        assertThat(response).isNotNull();
        assertThat(response.getAddressTypeId()).isNotNull();
    }
}
