package info.batcloud.wxc.admin.security.handler;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.admin.helper.SecurityHelper;
import info.batcloud.wxc.admin.service.ManagerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Inject
    private ManagerService managerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=utf-8");
        Map<String, Object> map = new HashMap<>();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        map.put("username", userDetails.getUsername());
        managerService.plusLoginTimes(SecurityHelper.loginManagerId());
        response.getWriter().print(JSON.toJSONString(map));
    }

}
