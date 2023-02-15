package info.batcloud.wxc.core.service.impl;

import info.batcloud.wxc.core.dto.FoodSkuDTO;
import info.batcloud.wxc.core.entity.FoodSku;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.repository.FoodSkuRepository;
import info.batcloud.wxc.core.service.FoodService;
import info.batcloud.wxc.core.service.FoodSkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoodSkuServiceImpl implements FoodSkuService {
    @Inject
    private FoodSkuRepository foodSkuRepository;

    @Inject
    private FoodService foodService;

    @Override
    public void createFoodSku(CreateParam createParam) {
        if (foodSkuRepository.countByFoodIdAndName(createParam.getFoodId(), createParam.getName()) > 0) {
            throw new BizException("同一个商品sku名称不能重复，请修改规格名称后重试");
        }
        if (foodSkuRepository.countByUpc(createParam.getUpc()) > 0) {
            throw new BizException("upc编码已经存在！");
        }
        FoodSku sku = new FoodSku();
        sku.setFoodId(createParam.getFoodId());
        sku.setUpc(createParam.getUpc());
        sku.setName(createParam.getName());
        sku.setWeight(createParam.getWeight());
        sku.setSpec(createParam.getSpec());
        sku.setInputTax(createParam.getInputTax());
        sku.setOutputTax(createParam.getOutputTax());
        sku.setMinOrderCount(createParam.getMinOrderCount());
        sku.setBoxNum(createParam.getBoxNum());
        sku.setBoxPrice(createParam.getBoxPrice());
        foodSkuRepository.save(sku);
    }

    @Override
    public List<FoodSkuDTO> getFoodSkuList(Long foodId) {
        List<FoodSku> foodSkus = foodSkuRepository.findByFoodId(foodId);
        List<FoodSkuDTO> dtos = new ArrayList<>();
        for (FoodSku skus : foodSkus) {
            dtos.add(toDTO(skus, false));
        }
        return dtos;
    }

    @Override
    public FoodSkuDTO getFoodSku(Long skuId) {
        return toDTO(foodSkuRepository.findOne(skuId), true);
    }

    @Override
    public void updateFoodSku(UpdateParam updateParam) {
        FoodSku foodSku = foodSkuRepository.findOne(updateParam.getId());
        if (!foodSku.getName().equals(updateParam.getName())) {
            if (foodSkuRepository.countByFoodIdAndName(foodSku.getFoodId(), updateParam.getName()) > 0) {
                throw new BizException("规格名称已经存在，请修改后再试");
            }
        }
        if (!foodSku.getUpc().equals(updateParam.getUpc())) {
            if (foodSkuRepository.countByUpc(updateParam.getUpc()) > 0) {
                throw new BizException("商品upc编码已经存在，请核实后再更新");
            }
        }
        foodSku.setUpc(updateParam.getUpc());
        foodSku.setName(updateParam.getName());
        foodSku.setWeight(updateParam.getWeight());
        foodSku.setSpec(updateParam.getSpec());
        foodSku.setInputTax(updateParam.getInputTax());
        foodSku.setOutputTax(updateParam.getOutputTax());
        foodSku.setMinOrderCount(updateParam.getMinOrderCount());
        foodSku.setBoxNum(updateParam.getBoxNum());
        foodSku.setBoxPrice(updateParam.getBoxPrice());
        foodSkuRepository.save(foodSku);
        //更改所有门店商品sku信息（但门店可以更改的信息不做修改）


    }

    @Override
    public void deleteFoodSku(Long skuId) {
        //删除所有门店商品对应的sku  删除所有库位对应的skuid


        foodSkuRepository.delete(skuId);
    }

    @Override
    public FoodSkuDTO getFoodSkuByUpc(String upc) {
        return null;
    }

    private FoodSkuDTO toDTO(FoodSku foodSku, boolean fenchFood) {
        FoodSkuDTO dto = new FoodSkuDTO();
        BeanUtils.copyProperties(foodSku, dto);
        if (fenchFood) {
            dto.setFoodDTO(foodService.findById(foodSku.getId()));
        }
        return dto;
    }
}
