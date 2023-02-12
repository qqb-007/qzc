package info.batcloud.wxc.core.security.helper;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GrantedAuthorityHelper {

    public static Collection<SimpleGrantedAuthority> valueOf(List<String> roles) {
        Collection<SimpleGrantedAuthority> list = new ArrayList<>();
        for (String role : roles) {
            list.add(new SimpleGrantedAuthority(role));
        }
        return list;
    }

}
