package info.batcloud.wxc.admin.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.admin.entity.Manager;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ManagerService extends UserDetailsService {

    Manager findByUsername(String username);

    Manager findById(Long id);

    void modifyPassword(long id, String originPassword, String password);

    boolean checkPassword(Manager manager, String password);

    void saveManager(Manager manager);

    void updateManager(Manager manager);

    void lockManager(long id);

    void unLockManager(long id);

    void modifyPassword(long id, String password);

    Paging<Manager> search(SearchParam param);

    void plusLoginTimes(long id);

    void deleteById(long id);

    class SearchParam extends PagingParam {
        private String name;
        private String username;
        private Boolean locked;

        public Boolean getLocked() {
            return locked;
        }

        public void setLocked(Boolean locked) {
            this.locked = locked;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
