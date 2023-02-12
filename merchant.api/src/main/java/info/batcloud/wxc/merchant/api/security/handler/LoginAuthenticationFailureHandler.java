package info.batcloud.wxc.merchant.api.security.handler;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.constants.MessageKeyConstants;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.merchant.api.domain.LoginResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        LoginResult loginResult = new LoginResult();
        loginResult.setSuccess(false);
        if (exception instanceof BadCredentialsException) {
            loginResult.setCode(MessageKeyConstants.BAD_CREDENTIALS);
        } else {
            loginResult.setCode(exception.getLocalizedMessage());
        }
        response.getWriter().write(JSON.toJSONString(BusinessResponse.ok(loginResult)));
        response.getWriter().flush();

    }
}
