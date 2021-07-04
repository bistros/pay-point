package com.kpay.point.api.v1.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kpay.point.shared.domain.MemberShip;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
@Builder
public class MemberShipModel implements ViewModel {
    private long seq;
    private String membershipId;
    private String membershipName;
    private String userId;
    private long point;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime startDate;
    private String membershipStatus;


    public static MemberShipModel toViewModel(MemberShip membership) {
        return builder()
                .seq(membership.getSeq())
                .membershipId(membership.getMemberShipType().getId())
                .membershipName(membership.getMemberShipType().getName())
                .point(membership.getTotalPoint())
                .userId(membership.getUserId())
                .startDate(membership.getStartDate())
                .membershipStatus(membership.isActive() ? "Y" : "N")
                .build();
    }
}
