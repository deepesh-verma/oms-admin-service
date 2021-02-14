package org.navya.oms.omsadminservices.repository;

import java.util.UUID;
import org.navya.oms.omsadminservices.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {}
