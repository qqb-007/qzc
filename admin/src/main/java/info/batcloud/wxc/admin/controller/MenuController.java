package info.batcloud.wxc.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import info.batcloud.wxc.admin.helper.SecurityHelper;
import info.batcloud.wxc.admin.service.ManagerService;
import info.batcloud.wxc.admin.service.PermissionService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-06-05.
 */
@Controller
public class MenuController {

    @Value("classpath:menu.json")
    private Resource menuResource;

    @Inject
    private PermissionService permissionService;

    @Inject
    private ManagerService managerService;

    @GetMapping("/api/menu")
    @ResponseBody
    public List<Menu> getMenu() throws IOException {
        try(InputStream is = menuResource.getInputStream()){
            String menuJson = IOUtils.toString(is, "utf8");

            List<Menu> menus = JSON.parseObject(menuJson, new TypeReference<List<Menu>>() {
            });
            if(permissionService.checkAdminRole(SecurityHelper.loginManagerId())) {
                return menus;
            }
            List<Menu> filtedMenu = new ArrayList<>();
            List<String> permissions = permissionService.findManagerPermissions(SecurityHelper.loginManagerId());
            for (Menu menu : menus) {
                filterMenu(menu, filtedMenu, permissions);
            }
            return filtedMenu;
        }
    }

    private static void filterMenu(Menu menu, List<Menu> container, List<String> permissions) {
        String permissionId = menu.getPermissionId() != null ? menu.getPermissionId() : menu.getId();
        if (!permissions.contains(permissionId)) {
            return;
        }
        container.add(menu);
        if (menu.getChildren() != null && menu.getChildren().size() > 0) {
            List<Menu> menus = menu.getChildren();
            menu.setChildren(new ArrayList<>());
            menus.forEach((m) -> filterMenu(m, menu.getChildren(), permissions));
        }
    }

    public static class Menu {
        private String id;
        private String title;
        private String icon;
        private String url;
        private String permissionId;
        private List<Menu> children;
        private Boolean open;
        private boolean closeable = true;
        private Map<String, Object> params;

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }

        public String getPermissionId() {
            return permissionId;
        }

        public void setPermissionId(String permissionId) {
            this.permissionId = permissionId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Menu> getChildren() {
            return children;
        }

        public void setChildren(List<Menu> children) {
            this.children = children;
        }

        public Boolean getOpen() {
            return open;
        }

        public void setOpen(Boolean open) {
            this.open = open;
        }

        public boolean isCloseable() {
            return closeable;
        }

        public void setCloseable(boolean closeable) {
            this.closeable = closeable;
        }
    }

}
