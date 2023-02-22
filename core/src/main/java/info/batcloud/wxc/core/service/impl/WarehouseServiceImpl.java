package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.StoreUserFoodDTO;
import info.batcloud.wxc.core.dto.WarehouseDTO;
import info.batcloud.wxc.core.entity.Bag;
import info.batcloud.wxc.core.entity.StoreUserFood;
import info.batcloud.wxc.core.entity.StoreUserFoodSku;
import info.batcloud.wxc.core.entity.Warehouse;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.StoreUserFoodRepository;
import info.batcloud.wxc.core.repository.StoreUserFoodSkuRepository;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.repository.WarehouseRepository;
import info.batcloud.wxc.core.service.StoreUserFoodSkuService;
import info.batcloud.wxc.core.service.WarehouseService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {


    @Inject
    private WarehouseRepository warehouseRepository;

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private StoreUserFoodRepository storeUserFoodRepository;

    @Inject
    private StoreUserFoodSkuRepository storeUserFoodSkuRepository;

    @Inject
    private StoreUserFoodSkuService storeUserFoodSkuService;


    @Override
    public void create(AddParam addParam) {
        //判断店铺内的库位是否重复
        int count = warehouseRepository.countByStoreUserIdAndName(addParam.getStoreUserId(), addParam.getName());
        if (count > 0) {
            throw new BizException("库位在店铺内名称重复，请修改后创建");
        }
        Warehouse warehouse = new Warehouse();
        warehouse.setName(addParam.getName());
        warehouse.setAddress(addParam.getAddress());
        warehouse.setStoreUser(storeUserRepository.findOne(addParam.getStoreUserId()));
        warehouse.setCreateTime(new Date());
        warehouse.setUpdateTime(new Date());
        warehouse.setDeleted(false);
        warehouseRepository.save(warehouse);
    }

    @Override
    public void update(UpdateParam updateParam) {
        //判断名称是否重复
        Warehouse warehouse = warehouseRepository.findOne(updateParam.getId());
        if (!warehouse.getName().equals(updateParam.getName())) {
            int count = warehouseRepository.countByStoreUserIdAndName(warehouse.getStoreUser().getId(), updateParam.getName());
            if (count > 0) {
                throw new BizException("库位在店铺内名称重复，请修改后更新");
            }
        }

        warehouse.setAddress(updateParam.getAddress());
        warehouse.setUpdateTime(new Date());
        warehouse.setName(updateParam.getName());
        warehouseRepository.save(warehouse);
    }

    @Override
    public void bindStoreUserFoodSku(String name, long storeUserFoodSkuId) {
        StoreUserFoodSku userFoodSku = storeUserFoodSkuRepository.findOne(storeUserFoodSkuId);
        Warehouse warehouse = warehouseRepository.findByStoreUserIdAndName(userFoodSku.getStoreUserId(), name);
        if (warehouse == null) {
            throw new BizException("库位不存在");
        }
        //判断是否同一个店铺
        if (warehouse.getStoreUser().getId() != userFoodSku.getStoreUserId()) {
            throw new BizException("库位和商品不属于同一个门店，请核对后再进行绑定");
        }
        String food_ids = warehouse.getSkuIds();
        if (StringUtils.isNotBlank(food_ids)) {
            String[] split = food_ids.split(",");
            for (String s : split) {
                if (s.equals(String.valueOf(storeUserFoodSkuId))) {
                    throw new BizException("该商品已经绑定到该库位中，无需重复绑定");
                }
            }
            food_ids = food_ids + "," + storeUserFoodSkuId;
            warehouse.setSkuIds(food_ids);
        } else {
            warehouse.setSkuIds(String.valueOf(storeUserFoodSkuId));
        }
        //在门店商品绑定该库位
        String warehouseIds = userFoodSku.getWarehouseIds();
        if (StringUtils.isNotBlank(warehouseIds)) {
            warehouseIds = warehouseIds + "," + warehouse.getId();
            userFoodSku.setWarehouseIds(warehouseIds);
        } else {
            userFoodSku.setWarehouseIds(warehouse.getId().toString());
        }
        storeUserFoodSkuRepository.save(userFoodSku);
        warehouseRepository.save(warehouse);
    }

    @Override
    public void deleteFoodSku(String name, long storeUserFoodSkuId) {
        //解绑商品  同时商品侧解绑库位
        StoreUserFoodSku userFoodSku = storeUserFoodSkuRepository.findOne(storeUserFoodSkuId);
        Warehouse warehouse = warehouseRepository.findByStoreUserIdAndName(userFoodSku.getStoreUserId(), name);
        if (warehouse == null) {
            throw new BizException("库位不存在");
        }
        warehouse.setSkuIds(deteteString(warehouse.getSkuIds(), String.valueOf(storeUserFoodSkuId)));
        warehouseRepository.save(warehouse);

        userFoodSku.setWarehouseIds(deteteString(userFoodSku.getWarehouseIds(), String.valueOf(warehouse.getId())));
        storeUserFoodSkuRepository.save(userFoodSku);
    }

    @Override
    public void deleteWarehouse(Long id) {
        //删除库位
        Warehouse warehouse = warehouseRepository.findOne(id);
        //将绑定在该库位的门店商品中对应的库位字段删除
        if (StringUtils.isNotBlank(warehouse.getSkuIds())) {
            String[] split = warehouse.getSkuIds().split(",");
            for (String s : split) {
                StoreUserFoodSku userFoodSku = storeUserFoodSkuRepository.findOne(Long.valueOf(s));
                String warehouseIds = userFoodSku.getWarehouseIds();
                userFoodSku.setWarehouseIds(deteteString(warehouseIds, id.toString()));
                storeUserFoodSkuRepository.save(userFoodSku);

            }
        }
        warehouseRepository.delete(id);
    }

//    @Override
//    public void deleteFood(long id, long storeUserFoodId) {
//        //解绑商品  同时商品侧解绑库位
//        Warehouse warehouse = warehouseRepository.findOne(id);
//        warehouse.setSkuIds(deteteString(warehouse.getSkuIds(), String.valueOf(storeUserFoodId)));
//        warehouseRepository.save(warehouse);
//        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(storeUserFoodId);
//        storeUserFood.setWarehouseIds(deteteString(storeUserFood.getWarehouseIds(), String.valueOf(id)));
//        storeUserFoodRepository.save(storeUserFood);
//    }

//    @Override
//    public void delete(Long id) {
//        //删除库位
//        Warehouse warehouse = warehouseRepository.findOne(id);
//        //将绑定在该库位的门店商品中对应的库位字段删除
//        if (StringUtils.isNotBlank(warehouse.getFoodIds())) {
//            String[] split = warehouse.getFoodIds().split(",");
//            for (String s : split) {
//                StoreUserFood storeUserFood = storeUserFoodRepository.findOne(Long.valueOf(s));
//                String warehouseIds = storeUserFood.getWarehouseIds();
//                storeUserFood.setWarehouseIds(deteteString(warehouseIds, id.toString()));
//                storeUserFoodRepository.save(storeUserFood);
//
//            }
//        }
//        warehouseRepository.delete(id);
//    }

    @Override
    public Paging<WarehouseDTO> search(SearchParam param) {
        Specification<Warehouse> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (StringUtils.isNotBlank(param.getName())) {
                expressions.add(cb.like(root.get("name"), "%" + param.getName() + "%"));
            }
            if (param.getCreateStartTime() != null) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("createTime"), param.getCreateStartTime()));
            }

            if (param.getCreateEndTime() != null) {
                expressions.add(cb.lessThanOrEqualTo(root.get("endTime"), param.getCreateEndTime()));
            }

            if (param.getCreateStartDate() != null) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("createTime"), DateUtils.truncate(param.getCreateStartDate(), Calendar.DATE)));
            }

            if (param.getCreateEndDate() != null) {
                expressions.add(cb.lessThan(root.get("createTime"),
                        DateUtils.truncate(DateUtils.addDays(param.getCreateEndDate(), 1), Calendar.DATE)));
            }

            if (param.getCreateEndTime() != null) {
                expressions.add(cb.lessThanOrEqualTo(root.get("endTime"), param.getCreateEndTime()));
            }

            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<Warehouse> page = warehouseRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item), param.getPage(), param.getPageSize());
    }

    @Override
    public List<WarehouseDTO> getskuWhList(Long skuId) {
        StoreUserFoodSku userFoodSku = storeUserFoodSkuRepository.findOne(skuId);
        List<WarehouseDTO> list = new ArrayList<>();
        String ids = userFoodSku.getWarehouseIds();
        if (StringUtils.isNotBlank(ids)) {
            String[] split = ids.split(",");
            for (String s : split) {
                list.add(toDTO(warehouseRepository.findOne(Long.valueOf(s))));
            }
        }
        return list;
    }

//    @Override
//    public List<WarehouseDTO> getsufWhList(Long sufId) {
//        StoreUserFood userFood = storeUserFoodRepository.findOne(sufId);
//        List<WarehouseDTO> list = new ArrayList<>();
//        String ids = userFood.getWarehouseIds();
//        if (StringUtils.isNotBlank(ids)) {
//            String[] split = ids.split(",");
//            for (String s : split) {
//                list.add(toDTO(warehouseRepository.findOne(Long.valueOf(s))));
//            }
//        }
//
//        return list;
//    }

    private WarehouseDTO toDTO(Warehouse warehouse) {
        WarehouseDTO dto = new WarehouseDTO();
        BeanUtils.copyProperties(warehouse, dto);
        dto.setStoreUserName(warehouse.getStoreUser().getName());
        List<StoreUserFoodSku> storeUserFoodSkuS = new ArrayList<>();
        String foodIds = warehouse.getSkuIds();
        if (StringUtils.isNotBlank(foodIds)) {
            String[] split = foodIds.split(",");
            for (String s : split) {
                storeUserFoodSkuS.add(storeUserFoodSkuRepository.findOne(Long.valueOf(s)));
            }
            //dto.setStoreUserFoodS(storeUserFoodS);
            dto.setFoodNum(storeUserFoodSkuS.size());
        } else {
            dto.setFoodNum(0);
        }
        return dto;
    }


    private String deteteString(String ids, String id) {
        if (StringUtils.isNotBlank(ids)) {
            String[] split = ids.split(",");
            List<String> list = new ArrayList<>();
            for (String s : split) {
                if (!s.equals(id)) {
                    list.add(s);
                }
            }
            return String.join(",", list);
        } else {
            return null;
        }

    }
}
