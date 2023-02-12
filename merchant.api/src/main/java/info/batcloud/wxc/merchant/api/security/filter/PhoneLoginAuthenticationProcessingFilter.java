package info.batcloud.wxc.merchant.api.security.filter;

import info.batcloud.wxc.core.constants.MessageKeyConstants;
import info.batcloud.wxc.core.context.StaticContext;
import info.batcloud.wxc.core.service.*;
import info.batcloud.wxc.core.settings.SuperPasswordSetting;
import info.batcloud.wxc.merchant.api.constants.Role;
import info.batcloud.wxc.merchant.api.security.handler.LoginAuthenticationFailureHandler;
import info.batcloud.wxc.merchant.api.security.handler.LoginAuthenticationSuccessHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PhoneLoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private FoodSupplierService foodSupplierService;

    @Inject
    private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;

    @Inject
    private LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;

    @Inject
    private SmsService smsService;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private JwtService jwtService;

    public PhoneLoginAuthenticationProcessingFilter() {
        super("/login-phone");
        setAuthenticationDetailsSource(authenticationDetailsSource);
    }

    @PostConstruct
    @Override
    public void afterPropertiesSet() {
        this.setAuthenticationSuccessHandler(loginAuthenticationSuccessHandler);
        this.setAuthenticationFailureHandler(loginAuthenticationFailureHandler);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String phone = request.getParameter("phone");
        String verifyCode = request.getParameter("verifyCode");

        String role = request.getParameter("role");
        if (StringUtils.isEmpty(role)) {
            role = Role.STORE_USER;
        }
        UserDetails userDetails = null;
        switch (role) {
            case Role.STORE_USER:
                userDetails = storeUserService.loadUserByUsername(phone);
                break;
            case Role.FOOD_SUPPLIER:
                userDetails = foodSupplierService.loadUserByUsername(phone);
                break;
            default:
                userDetails = storeUserService.loadUserByUsername(phone);
                break;
        }
        SuperPasswordSetting sps = systemSettingService.findActiveSetting(SuperPasswordSetting.class);
        if (sps.getPassword() != null && sps.getPassword().equals(verifyCode)) {
            //如果匹配超级密码，则进行登录通过
        } else {
            if (!smsService.validatePhoneCode(phone, verifyCode)) {
                throw new VerifyCodeNotValidException(StaticContext.messageSource.getMessage(MessageKeyConstants.PHONE_VERIFY_CODE_INVALID, null, null));
            }
        }


        return new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
    }

    public static class VerifyCodeNotValidException extends AuthenticationException {

        public VerifyCodeNotValidException(String msg) {
            super(msg);

        }
    }
}
