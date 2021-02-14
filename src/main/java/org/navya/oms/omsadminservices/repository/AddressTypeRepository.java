package org.navya.oms.omsadminservices.repository;

import java.util.UUID;
import org.navya.oms.omsadminservices.entity.AddressTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressTypeEntity, UUID> {}
