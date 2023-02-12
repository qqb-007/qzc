package info.batcloud.wxc.core.service;

import java.util.Date;

/**
 * @author lvling
 * Date: 2020/1/16
 * Time: 22:00
 */
public interface JwtService {

    <T> T decode(String token, Class<T> t);
    String encode(Object data, Date expiresTime);
}
