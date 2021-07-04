package com.kpay.point.infrastructure.persistence;

import com.kpay.point.infrastructure.persistence.entity.MemberShipLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberShipLogRepositoryJpa extends JpaRepository<MemberShipLogEntity, Long> {
}
