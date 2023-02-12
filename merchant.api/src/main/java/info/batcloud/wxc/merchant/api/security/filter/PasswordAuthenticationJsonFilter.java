package info.batcloud.wxc.merchant.api.security.filter;

import info.batcloud.wxc.merchant.api.security.handler.LoginAuthenticationFailureHandler;
import info.batcloud.wxc.merchant.api.security.handler.LoginAuthenticationSuccessHandler;
import info.batcloud.wxc.core.security.authentication.NoopAuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PasswordAuthenticationJsonFilter extends UsernamePasswordAuthenticationFilter {


    @Inject
    private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;


    @Inject
    private LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;

    @PostConstruct
    public void init() {
        this.setAuthenticationSuccessHandler(loginAuthenticationSuccessHandler);
        this.setAuthenticationFailureHandler(loginAuthenticationFailureHandler);
        this.setAuthenticationManager(new NoopAuthenticationManager());
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("phone");
    }
}
