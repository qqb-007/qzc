package info.batcloud.wxc.admin.permission.annotation;

import info.batcloud.wxc.admin.permission.ManagerPermissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Permission {
    ManagerPermissions value() default ManagerPermissions.NONE;
    boolean settingPermission() default false;
}
