package info.batcloud.wxc.core.service.impl;

import info.batcloud.wxc.core.dto.FoodSkuDTO;
import info.batcloud.wxc.core.dto.StoreUserFoodSkuDTO;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.entity.StoreUserFood;
import info.batcloud.wxc.core.entity.StoreUserFoodSku;
import info.batcloud.wxc.core.repository.FoodSkuRepository;
import info.batcloud.wxc.core.repository.StoreUserFoodRepository;
import info.batcloud.wxc.core.repository.StoreUserFoodSkuRepository;
import info.batcloud.wxc.core.repository.WarehouseRepository;
import info.batcloud.wxc.core.service.FoodSkuService;
import info.batcloud.wxc.core.service.StoreUserFoodSkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
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
    private WarehouseRepository warehouseRepository;

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

    private StoreUserFoodSkuDTO toDto(StoreUserFoodSku storeUserFoodSku) {
        StoreUserFoodSkuDTO dto = new StoreUserFoodSkuDTO();
        BeanUtils.copyProperties(storeUserFoodSku, dto);
        List<String> wnames = new ArrayList<>();
        if (StringUtils.isNotBlank(dto.getWarehouseIds())) {
            String[] split = dto.getWarehouseIds().split(",");
            for (String s : split) {
                wnames.add(warehouseRepository.findOne(Long.valueOf(s)).getName());
            }
        }
        dto.setWarehouseNames(String.join("、", wnames));
        return dto;
    }
}
