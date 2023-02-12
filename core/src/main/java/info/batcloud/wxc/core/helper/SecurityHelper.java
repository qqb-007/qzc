package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.domain.MerchantUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for Spring Security.
 */
public final class SecurityHelper {

    private SecurityHelper() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static MerchantUserDetails merchantUserDetails() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof MerchantUserDetails) {
                MerchantUserDetails details = (MerchantUserDetails) authentication.getPrincipal();
                return details;
            }
        }
        return null;
    }

    public static Long loginStoreUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Long userId = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof MerchantUserDetails) {
                userId = ((MerchantUserDetails) authentication.getPrincipal()).getStoreUserId();
            }
        }
        return userId;
    }

    public static Long loginFoodSupplierId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Long userId = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof MerchantUserDetails) {
                userId = ((MerchantUserDetails) authentication.getPrincipal()).getFoodSupplierId();
            }
        }
        return userId;
    }

}
