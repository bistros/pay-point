package com.kpay.point.api.v1;

import com.kpay.point.api.v1.request.AccumulateMemberShipCommand;
import com.kpay.point.api.v1.request.RegistMemberShipCommand;
import com.kpay.point.api.v1.response.ApiResponseView;
import com.kpay.point.api.v1.response.MemberShipModel;
import com.kpay.point.api.v1.response.SuccessStatusViewModel;
import com.kpay.point.shared.domain.MemberShipType;
import com.kpay.point.shared.domain.User;
import com.kpay.point.usecases.AccumulateMemberShipUsecase;
import com.kpay.point.usecases.DeactivateMemberShipUsecase;
import com.kpay.point.usecases.RegistMemberShipUsecase;
import com.kpay.point.web.UserToken;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/membership")
@AllArgsConstructor
public class MemberShipCommandV1Controller {

    private final RegistMemberShipUsecase registMemberShipUsecase;
    private final AccumulateMemberShipUsecase accumulateMemberShipUsecase;
    private final DeactivateMemberShipUsecase deactivateMemberShipUsecase;


    @PostMapping
    public ApiResponseView<?> registMemberShip(@UserToken User user, @RequestBody RegistMemberShipCommand command) {
        var memberShipType = MemberShipType.getType(command.getMembershipId());
        var point = command.getPoint();

        var request = new RegistMemberShipUsecase.RegistMemberShipRequest(user, memberShipType, point);
        var response = registMemberShipUsecase.apply(request);
        var viewModel = MemberShipModel.toViewModel(response.getMemberShip());
        return new ApiResponseView<>(viewModel);
    }

    @PatchMapping("/{membershipId}")
    public ApiResponseView<SuccessStatusViewModel> deactiveMemberShip(
            @PathVariable("membershipId") String membershipId, @UserToken User user) {
        var memberShipType = MemberShipType.getType(membershipId);

        deactivateMemberShipUsecase.apply(new DeactivateMemberShipUsecase.DeactivateMemberShipRequest(user, memberShipType));

        return ApiResponseView.SUCCESS();
    }

    @PatchMapping("/point")
    public ApiResponseView<SuccessStatusViewModel> accumulateMemberShip(
            @UserToken User user, @RequestBody AccumulateMemberShipCommand accumulate) {
        var memberShipType = MemberShipType.getType(accumulate.getMembershipId());
        var amount = accumulate.getAmount();

        accumulateMemberShipUsecase.apply(new AccumulateMemberShipUsecase.AccumulateMemberShipRequest(user, memberShipType, amount));

        return ApiResponseView.SUCCESS();
    }
}
