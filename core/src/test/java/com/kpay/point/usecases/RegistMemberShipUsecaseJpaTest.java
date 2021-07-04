package com.kpay.point.usecases;

import com.kpay.point.infrastructure.persistence.MemberShipRepositoryJpa;
import com.kpay.point.shared.domain.MemberShipType;
import com.kpay.point.shared.domain.User;
import com.kpay.point.shared.exception.AlreadyExistMemberShipException;
import static com.kpay.point.usecases.RegistMemberShipUsecase.RegistMemberShipRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

@Tag("SpringTest")
@DisplayName("JPA) 멤버쉽 신규 등록")
@DataJpaTest
@ContextConfiguration(classes = RegistMemberShipUsecaseJpaTest.InnerTestConfig.class)
public class RegistMemberShipUsecaseJpaTest {

    @Autowired
    private MemberShipRepositoryJpa repositoryJpa;
    @Autowired
    RegistMemberShipUsecase usecase;

    @Test
    @DisplayName("멤버쉽 정보가 존재할 때 추가로 등록한다")
    public void test1() {
        var request = new RegistMemberShipRequest(new User("111"), MemberShipType.CJONE, 999);
        assertThrows(AlreadyExistMemberShipException.class, () -> usecase.apply(request));
    }

    @Test
    @DisplayName("신규 멤버쉽 정보를 등록한다")
    public void test2() {
        var request = new RegistMemberShipRequest(new User("222"), MemberShipType.CJONE, 999);
        var response = usecase.apply(request).getMemberShip();
        assertAll(
                () -> assertThat(response.getTotalPoint()).isEqualTo(999),
                () -> assertThat(response.getStartDate()).isNotNull()
        );

    }

    @EnableJpaAuditing
    @Configuration
    @EnableJpaRepositories(basePackages = "com.kpay.point.infrastructure.persistence")
    @EntityScan("com.kpay.point.infrastructure.persistence")
    static class InnerTestConfig {
        @Bean
        public RegistMemberShipUsecase registMemberShipUsecase(MemberShipRepositoryJpa memberShipRepositoryJpa) {
            return new RegistMemberShipUsecase(memberShipRepositoryJpa);
        }
    }
}
