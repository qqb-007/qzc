package info.batcloud.wxc.merchant.api.security.filter;

import info.batcloud.wxc.core.domain.MerchantUserDetails;
import info.batcloud.wxc.core.service.JwtService;
import info.batcloud.wxc.merchant.api.constants.CacheKeys;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author lvling
 * Date: 2020/2/1
 * Time: 12:16
 */
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    @Inject
    private JwtService jwtService;

    @Inject
    private RedisTemplate<String, MerchantUserDetails> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader("x-auth-token");
        if (StringUtils.isEmpty(authToken) && request.getCookies() != null) {
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
            String key = CacheKeys.join(CacheKeys.SESSION, username);
            MerchantUserDetails userDetails = redisTemplate.opsForValue().get(key);
            if (userDetails != null) {
                /**
                 * 如果过期时间小于一天，则进行更新过期时间的操作
                 * */
                if ((System.currentTimeMillis() - userDetails.getTimestamp()) > 24 * 3600 * 1000 ) {
                    redisTemplate.expire(key, 2, TimeUnit.DAYS);
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
