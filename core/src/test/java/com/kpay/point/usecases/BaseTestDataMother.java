package com.kpay.point.usecases;

import com.kpay.point.infrastructure.persistence.entity.MemberShipEntity;

public class BaseTestDataMother {
    static class DeActiveTestData {
        static MemberShipEntity deActiveMemberShip() {
            var memberShip = new MemberShipEntity();
            memberShip.setActive(false);
            memberShip.setUserId("111");
            memberShip.setMembershipId("cj");
            return memberShip;
        }

        static MemberShipEntity activeMemberShip() {
            var memberShip = new MemberShipEntity();
            memberShip.setActive(true);
            memberShip.setUserId("111");
            memberShip.setMembershipId("cj");
            return memberShip;
        }
    }

    static class RegistTestData {
        static MemberShipEntity registedDeActiveData() {
            var memberShip = new MemberShipEntity();
            memberShip.setActive(false);
            memberShip.setUserId("111");
            memberShip.setMembershipId("cj");
            return memberShip;
        }
    }

    static class ccumulateTestsData {
        static MemberShipEntity hasCjPointData() {
            var memberShip = new MemberShipEntity();
            memberShip.setActive(true);
            memberShip.setUserId("111");
            memberShip.setMembershipId("cj");
            memberShip.setTotalPoint(2100);
            memberShip.setSeq(10);
            return memberShip;
        }
    }
}
