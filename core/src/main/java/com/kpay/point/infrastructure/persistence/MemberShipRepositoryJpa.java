package com.kpay.point.infrastructure.persistence;

import com.kpay.point.infrastructure.persistence.entity.MemberShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberShipRepositoryJpa extends JpaRepository<MemberShipEntity, Long> {

    Optional<MemberShipEntity> findByUserIdAndMembershipId(String userId, String membershipId);

    List<MemberShipEntity> findByUserIdAndActiveIsTrue(String userId);

    List<MemberShipEntity> findByUserId(String userId);


}
