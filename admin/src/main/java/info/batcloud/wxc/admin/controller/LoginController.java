package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.entity.Manager;
import info.batcloud.wxc.admin.model.AuthenticationInfo;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017-06-04.
 */
@Controller
public class LoginController {

    @GetMapping("/api/login/check")
    @ResponseBody
    public AuthenticationInfo checkLogin() {
        AuthenticationInfo authenticationInfo = new AuthenticationInfo();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return authenticationInfo;
        }
        if (authentication == null || !authentication.isAuthenticated()) {
            return authenticationInfo;
        }
        authenticationInfo.setAuthenticated(true);
        Manager manager = (Manager) authentication.getPrincipal();
        authenticationInfo.setUsername(manager.getUsername());
        return authenticationInfo;
    }

}
