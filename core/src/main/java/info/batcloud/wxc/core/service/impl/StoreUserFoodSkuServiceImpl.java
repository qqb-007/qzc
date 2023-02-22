package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.FoodSkuDTO;
import info.batcloud.wxc.core.dto.StoreUserFoodSkuDTO;
import info.batcloud.wxc.core.entity.*;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.*;
import info.batcloud.wxc.core.service.FoodSkuService;
import info.batcloud.wxc.core.service.StoreUserFoodService;
import info.batcloud.wxc.core.service.StoreUserFoodSkuService;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;

@Service
public class StoreUserFoodSkuServiceImpl implements StoreUserFoodSkuService {
    @Inject
    private StoreUserFoodSkuRepository storeUserFoodSkuRepository;

    @Inject
    private StoreUserFoodRepository storeUserFoodRepository;

    @Inject
    private FoodSkuService foodSkuService;

    @Inject
    private FoodSkuRepository foodSkuRepository;

    @Inject
    private FoodRepository foodRepository;

    @Inject
    private WarehouseRepository warehouseRepository;

    @Inject
    private StoreUserFoodService storeUserFoodService;

    @Override
    public void addNewSkus(long fooSkuId) {
        //当商品池商品新增sku的时候，默认所有对应门店商品加上此sku
        FoodSku foodSku = foodSkuRepository.findOne(fooSkuId);
        List<StoreUserFood> storeUserFoodList = storeUserFoodRepository.findByFoodId(foodSku.getFoodId());
        for (StoreUserFood storeUserFood : storeUserFoodList) {
            StoreUserFoodSku userFoodSku = new StoreUserFoodSku();
            userFoodSku.setFoodId(foodSku.getFoodId());
            userFoodSku.setStoreUserId(storeUserFood.getStoreUser().getId());
            userFoodSku.setStoreUserFoodId(storeUserFood.getId());
            userFoodSku.setFoodSkuId(fooSkuId);
            userFoodSku.setWarehouseIds(null);
            userFoodSku.setStock(0);
            userFoodSku.setInputPrice(0f);
            userFoodSku.setOutputPrice(0f);
            userFoodSku.setUpc(foodSku.getUpc());
            userFoodSku.setName(foodSku.getName());
            userFoodSku.setWeight(foodSku.getWeight());
            userFoodSku.setSpec(foodSku.getSpec());
            userFoodSku.setInputTax(foodSku.getInputTax());
            userFoodSku.setOutputTax(foodSku.getOutputTax());
            userFoodSku.setMinOrderCount(foodSku.getMinOrderCount());
            userFoodSku.setBoxNum(foodSku.getBoxNum());
            userFoodSku.setBoxPrice(foodSku.getBoxPrice());
            storeUserFoodSkuRepository.save(userFoodSku);
        }
    }

    @Override
    public void adminUpdateSufSku(long sufId, List<Long> foodSkuId, List<Integer> stock, List<Float> inputPrice, List<Float> outputPrice) {
        for (int i = 0; i < foodSkuId.size(); i++) {
            StoreUserFoodSku storeUserFoodSku = storeUserFoodSkuRepository.findByStoreUserFoodIdAndFoodSkuId(sufId, foodSkuId.get(i));
            storeUserFoodSku.setStock(stock.get(i));
            storeUserFoodSku.setInputPrice(inputPrice.get(i));
            storeUserFoodSku.setOutputPrice(outputPrice.get(i));
            storeUserFoodSkuRepository.save(storeUserFoodSku);
        }
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(sufId);
        StoreUserFoodService.LowPrice lowPrice = storeUserFoodService.getStoreUserFoodPrice(storeUserFood);
        storeUserFood.setSalePrice(lowPrice.getOutputPrice());
        storeUserFood.setQuotePrice(lowPrice.getInputPrice());
        storeUserFoodRepository.save(storeUserFood);
        //发布商品

    }

    @Override
    public void createSufSku(Long sufId) {
        //店铺添加商品时添加sku
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(sufId);
        Food food = storeUserFood.getFood();
        List<FoodSkuDTO> foodSkuList = foodSkuService.getFoodSkuList(food.getId());
        for (FoodSkuDTO foodSkuDTO : foodSkuList) {
            StoreUserFoodSku storeUserFoodSku = new StoreUserFoodSku();
            storeUserFoodSku.setFoodId(food.getId());
            storeUserFoodSku.setStoreUserId(storeUserFood.getStoreUser().getId());
            storeUserFoodSku.setStoreUserFoodId(storeUserFood.getId());
            storeUserFoodSku.setFoodSkuId(foodSkuDTO.getId());
            storeUserFoodSku.setWarehouseIds(null);
            storeUserFoodSku.setStock(0);
            storeUserFoodSku.setInputPrice(food.getPrice());
            storeUserFoodSku.setOutputPrice(food.getPrice());
            storeUserFoodSku.setUpc(foodSkuDTO.getUpc());
            storeUserFoodSku.setName(foodSkuDTO.getName());
            storeUserFoodSku.setWeight(foodSkuDTO.getWeight());
            storeUserFoodSku.setSpec(foodSkuDTO.getSpec());
            storeUserFoodSku.setInputTax(foodSkuDTO.getInputTax());
            storeUserFoodSku.setOutputTax(foodSkuDTO.getOutputTax());
            storeUserFoodSku.setMinOrderCount(foodSkuDTO.getMinOrderCount());
            storeUserFoodSku.setBoxNum(foodSkuDTO.getBoxNum());
            storeUserFoodSku.setBoxPrice(foodSkuDTO.getBoxPrice());
            storeUserFoodSkuRepository.save(storeUserFoodSku);
        }

    }

    @Override
    public void updateSufSku(UpdateParam updateParam) {
        StoreUserFoodSku sku = storeUserFoodSkuRepository.findOne(updateParam.getId());
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(sku.getStoreUserFoodId());
        boolean fb = false;
        if (storeUserFood.getSale() && (updateParam.getBoxNum() != sku.getBoxNum() || updateParam.getBoxPrice() != sku.getBoxPrice() || updateParam.getMinOrderCount() != sku.getMinOrderCount() || updateParam.getStock() != sku.getStock() || updateParam.getOutputPrice() != sku.getOutputPrice())) {
            fb = true;
        }
        sku.setStock(updateParam.getStock());
        sku.setInputPrice(updateParam.getInputPrice());
        sku.setOutputPrice(updateParam.getOutputPrice());
        sku.setMinOrderCount(updateParam.getMinOrderCount());
        sku.setBoxNum(updateParam.getBoxNum());
        sku.setBoxPrice(updateParam.getBoxPrice());
        storeUserFoodSkuRepository.save(sku);
        if (fb) {
            //将商品sku改动发布到平台

        }
    }

    @Override
    public List<StoreUserFoodSkuDTO> getByStoreUserFoodId(long sufId) {
        List<StoreUserFoodSku> userFoodSkus = storeUserFoodSkuRepository.findByStoreUserFoodId(sufId);
        List<StoreUserFoodSkuDTO> skuDTOS = new ArrayList<>();
        for (StoreUserFoodSku foodSkus : userFoodSkus) {
            skuDTOS.add(toDto(foodSkus));
        }
        return skuDTOS;
    }

    @Override
    public List<StoreUserFoodSkuDTO> getByFoodSkuId(long foodSkuId) {
        return null;
    }

    @Override
    public StoreUserFoodSkuDTO getByUpcAndStoreUserId(long storeUserId, String upc) {

        return toDto(storeUserFoodSkuRepository.findByStoreUserIdAndUpc(storeUserId, upc));
    }

    @Override
    public Paging<StoreUserFoodSkuDTO> search(SearchParam param) {
        Specification<StoreUserFoodSku> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("storeUserId"), param.getStoreUserId()));
            }

            if (StringUtils.isNotEmpty(param.getName())) {
                expressions.add(cb.like(root.get("name"), "%" + param.getName() + "%"));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<StoreUserFoodSku> page = storeUserFoodSkuRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDto(item), param.getPage(), param.getPageSize());
    }

    private StoreUserFoodSkuDTO toDto(StoreUserFoodSku storeUserFoodSku) {
        if (storeUserFoodSku == null) {
            return null;
        }
        StoreUserFoodSkuDTO dto = new StoreUserFoodSkuDTO();
        BeanUtils.copyProperties(storeUserFoodSku, dto);
        List<String> wnames = new ArrayList<>();
        if (StringUtils.isNotBlank(dto.getWarehouseIds())) {
            String[] split = dto.getWarehouseIds().split(",");
            for (String s : split) {
                wnames.add(warehouseRepository.findOne(Long.valueOf(s)).getName());
            }
            dto.setWarehouseNames(String.join("、", wnames));
        } else {
            dto.setWarehouseNames("暂未绑定库位");
        }

        dto.setPicture(foodRepository.findOne(dto.getFoodId()).getPicture());
        return dto;
    }
}
