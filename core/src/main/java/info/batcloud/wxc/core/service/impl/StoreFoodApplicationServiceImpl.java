package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.StoreFoodApplicationDTO;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.entity.FoodSupplier;
import info.batcloud.wxc.core.entity.StoreFoodApplication;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.enums.StoreFoodApplicationStatus;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.FoodRepository;
import info.batcloud.wxc.core.repository.FoodSupplierRepository;
import info.batcloud.wxc.core.repository.StoreFoodApplicationRepository;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.FoodService;
import info.batcloud.wxc.core.service.StoreFoodApplicationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;

@Service
public class StoreFoodApplicationServiceImpl implements StoreFoodApplicationService {

    @Inject
    private StoreFoodApplicationRepository storeFoodApplicationRepository;

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private FoodSupplierRepository foodSupplierRepository;

    @Inject
    private FoodRepository foodRepository;

    @Override
    public StoreFoodApplicationDTO apply(ApplyParam param) {
        Food food = foodRepository.findByNameAndDeletedFalse(param.getFoodName());
        if (food != null) {
            return new StoreFoodApplicationDTO();
        }
        StoreFoodApplication app = new StoreFoodApplication();
        app.setCreateTime(new Date());
        if (param.getFoodSupplierId() != null) {
            app.setFoodSupplier(foodSupplierRepository.findOne(param.getFoodSupplierId()));
        }
        app.setFoodName(param.getFoodName());
        app.setFoodPicture(param.getFoodPicture());
        app.setPrice(param.getPrice());
        app.setStatus(StoreFoodApplicationStatus.WAIT_VERIFY);
        app.setUpdateTime(new Date());
        app.setUnit(param.getUnit());
        StoreUser su = storeUserRepository.findOne(param.getStoreUserId());
        app.setStoreUser(su);
        storeFoodApplicationRepository.save(app);
        return toDTO(app);
    }

    @Override
    public Paging<StoreFoodApplicationDTO> search(SearchParam param) {
        Specification<StoreFoodApplication> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (query.getResultType() != Long.class) {
                root.fetch("storeUser", JoinType.LEFT);
            }
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (param.getStatus() != null) {
                expressions.add(cb.equal(root.get("status"), param.getStatus()));
            } else {
                expressions.add(cb.notEqual(root.get("status"), StoreFoodApplicationStatus.DELETED));
            }
            if (StringUtils.isNotBlank(param.getStoreUserName())) {
                expressions.add(cb.like(root.get("store").get("name"), "%" + param.getStoreUserName() + "%"));
            }
            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("storeUser").get("id"), param.getStoreUserId()));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<StoreFoodApplication> page = storeFoodApplicationRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item), param.getPage(), param.getPageSize());
    }

    @Override
    public void setStatusById(long id, StoreFoodApplicationStatus status) {
        StoreFoodApplication application = storeFoodApplicationRepository.findOne(id);
        application.setStatus(status);
        application.setUpdateTime(new Date());
        storeFoodApplicationRepository.save(application);
    }

    private StoreFoodApplicationDTO toDTO(StoreFoodApplication app) {
        StoreFoodApplicationDTO dto = new StoreFoodApplicationDTO();
        dto.setCreateTime(app.getCreateTime());
        dto.setFoodName(app.getFoodName());
        dto.setFoodPicture(app.getFoodPicture());
        dto.setStatus(app.getStatus());
        dto.setPrice(app.getPrice());
        StoreUser su = app.getStoreUser();
        FoodSupplier sf = app.getFoodSupplier();
        if (sf != null) {
            dto.setFoodSupplierId(sf.getId());
            dto.setFoodSupplierName(sf.getName());
        }
        dto.setStoreUserId(su.getId());
        dto.setUpdateTime(app.getUpdateTime());
        dto.setStoreUserName(su.getName());
        dto.setUnit(app.getUnit());
        dto.setId(app.getId());
        return dto;
    }

}
