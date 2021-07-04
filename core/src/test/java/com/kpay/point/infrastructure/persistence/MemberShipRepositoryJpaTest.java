package com.kpay.point.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

/**
 * 깨지면 기본적으로 사용하는 데이터 셋을 확인하세요
 */
@Tag("SpringTest")
@DisplayName("MemberRepository JPA 기본 제공 데이터 테스트")
@DataJpaTest
@ContextConfiguration(classes = MemberShipRepositoryJpaTest.InnerTestConfig.class)
class MemberShipRepositoryJpaTest {

    @Autowired
    private MemberShipRepositoryJpa memberShipRepository;

    @Test
    @DisplayName("112 고객은 존재하지만 유효한 멤버쉽 정보가 없다")
    void test1() {
        var userId = "112";
        var alls = memberShipRepository.findByUserId(userId);
        var actives = memberShipRepository.findByUserIdAndActiveIsTrue(userId);
        assertAll(
                () -> assertThat(alls).hasSize(1),
                () -> assertThat(actives).hasSize(0));
    }

    @Test
    @DisplayName("999 고객은 등록된 데이터 자체가 없다")
    void test2() {
        var userId = "999";
        var alls = memberShipRepository.findByUserId(userId);
        var actives = memberShipRepository.findByUserIdAndActiveIsTrue(userId);
        assertAll(
                () -> assertThat(alls).hasSize(0),
                () -> assertThat(actives).hasSize(0));
    }


    @Configuration
    @EnableJpaRepositories(basePackages = "com.kpay.point.infrastructure.persistence")
    @EntityScan("com.kpay.point.infrastructure.persistence")
    static class InnerTestConfig {

    }
}