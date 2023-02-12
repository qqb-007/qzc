package info.batcloud.wxc.admin.permission;

import java.util.List;

/**
 * Created by Administrator on 2015/1/27.
 */
public interface PermissionItem {

    String getName();
    String getParentId();
    String getId();
    Class[] settingTypes();
    List<PermissionItem> getChildren();
    void addChild(PermissionItem item);

}
