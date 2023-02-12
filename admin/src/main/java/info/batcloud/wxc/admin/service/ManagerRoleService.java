package info.batcloud.wxc.admin.service;

import info.batcloud.wxc.admin.entity.Role;

import java.util.List;

public interface ManagerRoleService {

    List<Role> findManagerRoles(long managerId);

    void setManagerRoles(long managerId, List<Long> roleIds);

    List<RoleInfo> listManagerRoles(long managerId);
    class RoleInfo {
        private long id;
        private String name;
        private boolean checked;

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

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
}
