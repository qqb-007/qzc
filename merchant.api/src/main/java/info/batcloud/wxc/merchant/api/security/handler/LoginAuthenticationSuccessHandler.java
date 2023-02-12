package info.batcloud.wxc.merchant.api.security.handler;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.domain.MerchantUserDetails;
import info.batcloud.wxc.core.service.JwtService;
import info.batcloud.wxc.merchant.api.constants.CacheKeys;
import info.batcloud.wxc.merchant.api.domain.LoginResult;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Inject
    private JwtService jwtService;

    @Inject
    private RedisTemplate<String, MerchantUserDetails> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String state = request.getParameter("state");
        /**
         * 如果有state参数，则说明需要另外处理。
         * */
        if("invitation".equals(state)) {
            return;
        }
        MerchantUserDetails userDetails = (MerchantUserDetails) authentication.getPrincipal();
        /**
         * 过期时间1年
         * */
        String token = jwtService.encode(userDetails.getUsername(), DateUtils.addDays(new Date(), 365));
        /**
         * 2天的时间
         * */
        redisTemplate.opsForValue().set(CacheKeys.join(CacheKeys.SESSION, userDetails.getUsername()), userDetails, 2, TimeUnit.DAYS);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        LoginResult loginResult = new LoginResult();
        loginResult.setToken(token);
        loginResult.setSuccess(true);
        Cookie tokenCookie = new Cookie("x-auth-token", token);
        tokenCookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(tokenCookie);
        response.getWriter().write(JSON.toJSONString(BusinessResponse.ok(loginResult)));
        response.getWriter().flush();
    }

}
