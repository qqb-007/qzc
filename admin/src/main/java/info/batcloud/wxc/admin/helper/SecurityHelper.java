package info.batcloud.wxc.admin.helper;

import info.batcloud.wxc.admin.entity.Manager;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {

    public static Long loginManagerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Manager manager = (Manager) authentication.getPrincipal();
        return manager.getId();
    }

}
