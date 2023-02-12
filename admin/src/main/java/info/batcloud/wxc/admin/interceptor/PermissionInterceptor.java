package info.batcloud.wxc.admin.interceptor;

import info.batcloud.wxc.admin.helper.SecurityHelper;
import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.admin.service.PermissionService;
import org.apache.http.HttpStatus;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Permission permissionRequire = handlerMethod.getMethodAnnotation(Permission.class);

            if (permissionRequire != null) {
                long managerId = SecurityHelper.loginManagerId();
                if (permissionService.checkAdminRole(managerId)) {
                    return super.preHandle(request, response, handler);
                }
                ManagerPermissions permission;
                if (permissionRequire.settingPermission()) {
                    String key = request.getParameter("key");
                    permission = permissionService.getSettingPermission(key);
                } else {
                    permission = permissionRequire.value();
                }
                if(!permissionService.checkPermission(SecurityHelper.loginManagerId(), permission.getId())) {
                    response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    private MethodParameter findByKey(String key, MethodParameter[] methodParameters) {
        if(methodParameters == null) {
            return null;
        }
        for (MethodParameter methodParameter : methodParameters) {
            if(methodParameter.getParameterName().equals(key)) {
                return methodParameter;
            }
        }
        return null;
    }
}
