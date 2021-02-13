package org.navya.oms.omsadminservices.repository;

import org.navya.oms.omsadminservices.entity.AddressTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressTypeEntity, UUID> {
}
