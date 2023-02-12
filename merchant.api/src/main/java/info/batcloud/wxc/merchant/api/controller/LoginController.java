package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.merchant.api.constants.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/check")
    public Object check(@RequestParam(required = false, defaultValue = Role.STORE_USER) String role) {
        switch (role) {
            case Role.STORE_USER:
                return BusinessResponse.ok(SecurityHelper.loginStoreUserId() != null);
            case Role.FOOD_SUPPLIER:
                return BusinessResponse.ok(SecurityHelper.loginFoodSupplierId() != null);
            default:
                return BusinessResponse.ok(false);
        }
    }

}
