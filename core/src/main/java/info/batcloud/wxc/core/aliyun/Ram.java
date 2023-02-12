package info.batcloud.wxc.core.aliyun;

public class Ram {

    private Users users;

    private Roles roles;

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public static class Roles {
        private Role aliyunFullAccessingRole;

        public Role getAliyunFullAccessingRole() {
            return aliyunFullAccessingRole;
        }

        public void setAliyunFullAccessingRole(Role aliyunFullAccessingRole) {
            this.aliyunFullAccessingRole = aliyunFullAccessingRole;
        }
    }

    public static class Users {
        private User aliyunManager;

        public User getAliyunManager() {
            return aliyunManager;
        }

        public void setAliyunManager(User aliyunManager) {
            this.aliyunManager = aliyunManager;
        }
    }

}
