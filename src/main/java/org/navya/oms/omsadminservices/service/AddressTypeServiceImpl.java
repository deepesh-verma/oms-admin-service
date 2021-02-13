package org.navya.oms.omsadminservices.service;

import lombok.extern.slf4j.Slf4j;
import org.navya.oms.omsadminservices.entity.AddressTypeEntity;
import org.navya.oms.omsadminservices.model.AddressTypeModel;
import org.navya.oms.omsadminservices.repository.AddressTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AddressTypeServiceImpl implements AddressTypeService {

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    @Override
    public AddressTypeModel saveAddressType(AddressTypeModel addressTypeModel) {
        AddressTypeEntity newAddressTypeEntity = new AddressTypeEntity(addressTypeModel);
        log.info("Saving a new address type entity : {}", newAddressTypeEntity);
        addressTypeRepository.save(newAddressTypeEntity);
        UUID addressTypeId = newAddressTypeEntity.getAddressTypeId();
        log.info("Saved the entity with addressTypeId: {}", addressTypeId);
        addressTypeModel.setAddressTypeId(addressTypeId);
        return addressTypeModel;
    }
}
