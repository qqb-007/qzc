package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.domain.MerchantUserDetails;
import info.batcloud.wxc.core.dto.EmployeeDTO;
import info.batcloud.wxc.core.dto.FoodSupplierDTO;
import info.batcloud.wxc.core.dto.StoreUserDTO;
import info.batcloud.wxc.core.entity.FoodSupplier;
import info.batcloud.wxc.core.enums.FoodSupplierPriceIncreaseType;
import info.batcloud.wxc.core.enums.FoodSupplierStatus;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.FoodSupplierRepository;
import info.batcloud.wxc.core.service.FoodSupplierService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class FoodSupplierServiceImpl implements FoodSupplierService {

    @Inject
    private FoodSupplierRepository foodSupplierRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        FoodSupplier foodSupplier = foodSupplierRepository.findByPhoneAndStatusNot(s, FoodSupplierStatus.DELETED);
        if (foodSupplier == null) {
            throw new UsernameNotFoundException("未找到" + s + "的账号");
        }
        MerchantUserDetails userDetails = new MerchantUserDetails();
        userDetails.setUsername(foodSupplier.getPhone());
        userDetails.setEnabled(true);
        userDetails.setAccountNonExpired(true);
        userDetails.setAccountNonLocked(true);
        userDetails.setCredentialsNonExpired(true);
        userDetails.setStoreUserId(foodSupplier.getStoreUser().getId());
        userDetails.setFoodSupplierId(foodSupplier.getId());
        //加载账号的权限
        ((Collection<GrantedAuthority>) userDetails.getAuthorities()).add(new SimpleGrantedAuthority("ROLE_FOOD_SUPPLIER"));
        return userDetails;
    }

    @Override
    public FoodSupplierDTO findByPhone(String phone) {
        return toDTO(foodSupplierRepository.findByPhoneAndStatusNot(phone, FoodSupplierStatus.DELETED));
    }

    @Override
    public FoodSupplierDTO findById(Long id) {
        return toDTO(foodSupplierRepository.findOne(id));
    }

    @Override
    public void modifyPassword(long id, String originPassword, String password) {
        FoodSupplier foodSupplier = foodSupplierRepository.findOne(id);
        if (!passwordEncoder.matches(originPassword, foodSupplier.getPassword())) {
            throw new BizException("原始密码输入不正确");
        }
        foodSupplier.setPassword(passwordEncoder.encode(password));
        foodSupplierRepository.save(foodSupplier);
    }

    @Override
    public boolean checkPassword(FoodSupplier foodSupplier, String password) {
        return false;
    }

    @Override
    public void saveFoodSupplier(FoodSupplier foodSupplier) {
        FoodSupplier eFoodSupplier = this.foodSupplierRepository.findByPhoneAndStatusNot(foodSupplier.getPhone(), FoodSupplierStatus.DELETED);
        if (eFoodSupplier != null) {
            throw new BizException("供应商手机号已经存在");
        }
        foodSupplier.setCreateTime(new Date());
        foodSupplier.setPassword(passwordEncoder.encode(foodSupplier.getPassword()));
        foodSupplierRepository.save(foodSupplier);
    }

    @Override
    public void updateFoodSupplier(long id, FoodSupplierUpdateParam param) {
        if (foodSupplierRepository.countByPhoneAndIdNot(param.getPhone(), id) != 0) {
            throw new BizException("手机号已经存在");
        }
        FoodSupplier eFoodSupplier = this.foodSupplierRepository.findOne(id);
        BeanUtils.copyProperties(param, eFoodSupplier);
        foodSupplierRepository.save(eFoodSupplier);
    }

    @Override
    public void lockFoodSupplier(long id) {
        FoodSupplier foodSupplier = this.foodSupplierRepository.findOne(id);
        foodSupplier.setStatus(FoodSupplierStatus.INVALID);
        foodSupplierRepository.save(foodSupplier);
    }

    @Override
    public void unLockFoodSupplier(long id) {
        FoodSupplier foodSupplier = this.foodSupplierRepository.findOne(id);
        foodSupplier.setStatus(FoodSupplierStatus.VALID);
        foodSupplierRepository.save(foodSupplier);
    }

    @Override
    public void modifyPassword(long id, String password) {
        FoodSupplier foodSupplier = this.foodSupplierRepository.findOne(id);
        foodSupplier.setPassword(passwordEncoder.encode(password));
        foodSupplierRepository.save(foodSupplier);
    }

    @Override
    public void setPriceIncreaseType(long id, FoodSupplierPriceIncreaseType type) {
        FoodSupplier foodSupplier = this.foodSupplierRepository.findOne(id);
        foodSupplier.setPriceIncreaseType(type);
        foodSupplierRepository.save(foodSupplier);
    }

    @Override
    public Paging<FoodSupplierDTO> search(SearchParam param) {
        Specification<FoodSupplier> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (query.getResultType() != Long.class) {
                Fetch storeUserFetch = root.fetch("storeUser", JoinType.LEFT);
                storeUserFetch.fetch("bizManager", JoinType.LEFT);
                storeUserFetch.fetch("bizManager2", JoinType.LEFT);
            }
            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (StringUtils.isNotBlank(param.getPhone())) {
                expressions.add(cb.equal(root.get("phone"), param.getPhone()));
            }
            if (StringUtils.isNotBlank(param.getName())) {
                expressions.add(cb.like(root.get("name"), "%" + param.getName() + "%"));
            }
            if (param.getPriceIncreaseType() != null) {
                expressions.add(cb.equal(root.get("priceIncreaseType"), param.getPriceIncreaseType()));
            }
            if (param.getStatus() != null) {
                expressions.add(cb.equal(root.get("status"), param.getStatus()));
            } else {
                expressions.add(cb.notEqual(root.get("status"), FoodSupplierStatus.DELETED));
            }

            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");

        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<FoodSupplier> page = foodSupplierRepository.findAll(specification, pageable);
        return PagingHelper.of(page, user -> toDTO(user), param.getPage(), param.getPageSize());
    }


    @Override
    public void deleteById(long id) {
        FoodSupplier foodSupplier = this.foodSupplierRepository.findOne(id);
        foodSupplier.setStatus(FoodSupplierStatus.DELETED);
        foodSupplierRepository.save(foodSupplier);
    }

    @Override
    public FoodSupplierDTO toDTO(FoodSupplier user) {
        if (user == null) {
            return null;
        }
        FoodSupplierDTO dto = new FoodSupplierDTO();
        BeanUtils.copyProperties(user, dto);
        StoreUserDTO sud = new StoreUserDTO();
        sud.setId(user.getStoreUser().getId());
        sud.setName(user.getStoreUser().getName());
        sud.setPhone(user.getStoreUser().getPhone());
        sud.setAddress(user.getStoreUser().getAddress());


        if (user.getStoreUser().getBizManager() != null) {
            EmployeeDTO bizManager = new EmployeeDTO();
            bizManager.setName(user.getStoreUser().getBizManager().getName());
            bizManager.setPhone(user.getStoreUser().getBizManager().getPhone());
            sud.setBizManager(bizManager);
        }
        if (user.getStoreUser().getBizManager2() != null) {
            EmployeeDTO bizManager2 = new EmployeeDTO();
            bizManager2.setName(user.getStoreUser().getBizManager2().getName());
            bizManager2.setPhone(user.getStoreUser().getBizManager2().getPhone());
            sud.setBizManager2(bizManager2);
        }
        dto.setStoreUser(sud);
        return dto;
    }

}
