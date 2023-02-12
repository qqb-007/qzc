package info.batcloud.wxc.admin.security.filter;

import info.batcloud.wxc.admin.security.handler.AdminAuthenticationFailureHandler;
import info.batcloud.wxc.admin.security.handler.AdminAuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
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
public class UsernamePasswordAuthenticationJsonFilter extends UsernamePasswordAuthenticationFilter {

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private AdminAuthenticationFailureHandler adminAuthenticationFailureHandler;

    @Inject
    private AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler;

    @PostConstruct
    public void init() {
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationFailureHandler(adminAuthenticationFailureHandler);
        this.setAuthenticationSuccessHandler(adminAuthenticationSuccessHandler);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
