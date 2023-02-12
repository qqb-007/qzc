package info.batcloud.wxc.admin.service;

import info.batcloud.wxc.admin.domain.PermissionTreeNode;
import info.batcloud.wxc.admin.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    void save(RoleAddParam param);

    void update(RoleUpdateParam param);

    void deleteById(long id);

    List<PermissionTreeNode> permissionTree(long roleId);

    void updatePermissions(long roleId, List<String> permissions);

    List<String> findRolePermission(long roleId);

    class RoleAddParam {
        private String name;

        private Boolean valid;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getValid() {
            return valid;
        }

        public void setValid(Boolean valid) {
            this.valid = valid;
        }
    }

    class RoleUpdateParam {
        private long id;
        private String name;
        private Boolean valid;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getValid() {
            return valid;
        }

        public void setValid(Boolean valid) {
            this.valid = valid;
        }
    }

}
