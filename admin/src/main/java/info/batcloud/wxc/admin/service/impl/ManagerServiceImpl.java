package info.batcloud.wxc.admin.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.admin.entity.Manager;
import info.batcloud.wxc.admin.repository.ManagerRepository;
import info.batcloud.wxc.admin.service.ManagerService;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Value("${manager.username}")
    private String managerUsername;
    @Value("${manager.password}")
    private String managerPassword;

    @Inject
    private ManagerRepository managerRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        this.checkOrCreateDefaultManager();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails userDetails = findByUsername(s);
        if (userDetails == null) {
            throw new UsernameNotFoundException("未找到" + s + "的账号");
        }
        //加载账号的权限
        ((Collection<GrantedAuthority>) userDetails.getAuthorities()).add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return userDetails;
    }

    @Override
    public Manager findByUsername(String username) {
        return managerRepository.findByUsernameAndDeleted(username, false);
    }

    @Override
    public Manager findById(Long id) {
        return managerRepository.findOne(id);
    }

    @Override
    public void modifyPassword(long id, String originPassword, String password) {
        Manager manager = managerRepository.findOne(id);
        if (!passwordEncoder.matches(originPassword, manager.getPassword())) {
            throw new BizException("原始密码输入不正确");
        }
        manager.setPassword(passwordEncoder.encode(password));
        managerRepository.save(manager);
    }

    @Override
    public boolean checkPassword(Manager manager, String password) {
        return false;
    }

    @Override
    public void saveManager(Manager manager) {
        Manager eManager = this.managerRepository.findByUsernameAndDeleted(manager.getUsername(), false);
        if (eManager != null) {
            throw new BizException("管理账户已经存在");
        }
        manager.setCreateTime(new Date());
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
        manager.setAdmin(false);
        managerRepository.save(manager);
    }

    @Override
    public void updateManager(Manager manager) {
        Manager eManager = this.managerRepository.findOne(manager.getId());
        if (eManager != null && !eManager.getId().equals(manager.getId())) {
            throw new BizException("管理账户已经存在");
        }
        eManager.setName(manager.getName());
        eManager.setLocked(manager.isLocked());
        eManager.setUpdateTime(new Date());
        managerRepository.save(eManager);
    }

    @Override
    public void lockManager(long id) {
        Manager manager = this.managerRepository.findOne(id);
        manager.setLocked(true);
        managerRepository.save(manager);
    }

    @Override
    public void unLockManager(long id) {
        Manager manager = this.managerRepository.findOne(id);
        manager.setLocked(false);
        managerRepository.save(manager);
    }

    @Override
    public void modifyPassword(long id, String password) {
        Manager manager = this.managerRepository.findOne(id);
        manager.setPassword(passwordEncoder.encode(password));
        managerRepository.save(manager);
    }

    @Override
    public Paging<Manager> search(SearchParam param) {
        Specification<Manager> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotBlank(param.getUsername())) {
                expressions.add(cb.equal(root.get("username"), param.getUsername()));
            }
            if (StringUtils.isNotBlank(param.getName())) {
                expressions.add(cb.equal(root.get("name"), param.getName()));
            }
            if (param.getLocked() != null) {
                expressions.add(cb.equal(root.get("locked"), param.getLocked()));
            }
            expressions.add(cb.equal(root.get("deleted"), false));
            return predicate;
        };
        Sort sort = null;

        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<Manager> page = managerRepository.findAll(specification, pageable);
        return PagingHelper.of(page, param.getPage(), param.getPageSize());
    }

    @Override
    public void plusLoginTimes(long id) {
        Manager manager = this.managerRepository.findOne(id);
        manager.setLoginTimes(manager.getLoginTimes() + 1);
        managerRepository.save(manager);
    }

    @Override
    public void deleteById(long id) {
        Manager manager = this.managerRepository.findOne(id);
        manager.setDeleted(true);
        managerRepository.save(manager);
    }


    private void checkOrCreateDefaultManager() {
        long i = this.managerRepository.count();
        if (i > 0) {
            return;
        }
        Manager manager = new Manager();
        manager.setUsername(this.managerUsername);
        manager.setPassword(this.passwordEncoder.encode(managerPassword));
        manager.setLocked(false);
        manager.setName("");
        manager.setAdmin(true);
        manager.setCreateTime(new Date());
        this.managerRepository.save(manager);
    }
}
