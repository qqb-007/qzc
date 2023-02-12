package info.batcloud.wxc.merchant.api.security.handler;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.domain.MerchantUserDetails;
import info.batcloud.wxc.core.service.JwtService;
import info.batcloud.wxc.merchant.api.constants.CacheKeys;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class JsonLogoutHandler implements LogoutHandler {

    @Inject
    private RedisTemplate<String, MerchantUserDetails> redisTemplate;

    @Inject
    private JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String authToken = request.getHeader("x-auth-token");
        if (StringUtils.isEmpty(authToken)) {
            Optional<Cookie> op = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("x-auth-token")).findFirst();
            if (op.isPresent()) {
                Cookie tokenCookie = op.get();
                if (tokenCookie != null) {
                    authToken = tokenCookie.getValue();
                }
            }

        }
        if (StringUtils.isNotEmpty(authToken)) {
            String username = jwtService.decode(authToken, String.class);
            redisTemplate.delete(CacheKeys.join(CacheKeys.SESSION, username));
        }
        try {
            response.getWriter().write(JSON.toJSONString(BusinessResponse.ok(true)));
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
