package com.kpay.point.api.v1;

import com.kpay.point.api.v1.response.ApiResponseView;
import com.kpay.point.api.v1.response.MemberShipModel;
import com.kpay.point.shared.domain.MemberShipType;
import com.kpay.point.shared.domain.User;
import com.kpay.point.shared.exception.MemberShipNotFoundException;
import com.kpay.point.usecases.AllCheckMemberShipUsecase;
import com.kpay.point.usecases.AllCheckMemberShipUsecase.AllCheckMemberRequest;
import com.kpay.point.usecases.CheckMemberShipUsecase;
import com.kpay.point.usecases.CheckMemberShipUsecase.CheckMemberShipRequest;
import com.kpay.point.web.UserToken;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/membership")
@AllArgsConstructor
public class MemberShipV1Controller {

    private AllCheckMemberShipUsecase allCheckMemberShipUsecase;
    private CheckMemberShipUsecase checkMemberShipUsecase;

    @GetMapping
    public ApiResponseView<List<MemberShipModel>> showAllMemberInfo(@UserToken User user) {
        var response = allCheckMemberShipUsecase.apply(new AllCheckMemberRequest(user));

        var result =
                response.getMemberShips().stream()
                        .map(MemberShipModel::toViewModel).collect(Collectors.toList());
        if (result.isEmpty()) {
            throw new MemberShipNotFoundException("active 상태의 멤버쉽 정보가 없습니다");
        }
        return new ApiResponseView(result);
    }

    @GetMapping("/{membershipId}")
    public ApiResponseView<MemberShipModel> membershop(
            @PathVariable("membershipId") String membershipId, @UserToken User user) {
        var memberShipType = MemberShipType.getType(membershipId);

        var response =
                checkMemberShipUsecase.apply(new CheckMemberShipRequest(user, memberShipType));
        var viewModel = MemberShipModel.toViewModel(response.getMemberShip());

        return new ApiResponseView<>(viewModel);
    }


}
