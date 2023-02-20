package info.batcloud.wxc.admin.controller;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.admin.controller.vo.FoodQuoteSheetDetailVerifyVo;
import info.batcloud.wxc.admin.controller.vo.FoodQuoteSkuVo;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.dto.FoodDTO;
import info.batcloud.wxc.core.dto.FoodQuoteSheetDTO;
import info.batcloud.wxc.core.dto.FoodQuoteSheetDetailDTO;
import info.batcloud.wxc.core.dto.StoreUserDTO;
import info.batcloud.wxc.core.service.FoodQuoteSheetDetailService;
import info.batcloud.wxc.core.service.FoodQuoteSheetService;
import info.batcloud.wxc.core.service.StoreUserService;
import info.batcloud.wxc.core.service.SystemSettingService;
import info.batcloud.wxc.core.settings.FoodSetting;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/food-quote-sheet")
public class FoodQuoteSheetController {

    @Inject
    private FoodQuoteSheetService foodQuoteSheetService;

    @Inject
    private FoodQuoteSheetDetailService foodQuoteSheetDetailService;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private StoreUserService storeUserService;

    @GetMapping("/search")
    public Object search(FoodQuoteSheetService.SearchParam param) {
        return foodQuoteSheetService.search(param);
    }

    @GetMapping("/wait-verify-num")
    public Object waitVerifyNum() {
        return foodQuoteSheetService.countWaitVerifyNum();
    }

    @PutMapping("/retrial/{id}")
    public Object retrial(@PathVariable long id) {
        foodQuoteSheetService.retrialFoodQuoteSheet(id);
        return true;
    }

    @PutMapping("/publish/{id}")
    public Object publish(@PathVariable long id) {
        Result rs = foodQuoteSheetService.publishToStore(id);
        return rs;
    }

    @PutMapping("/reject/{id}")
    public Object reject(@PathVariable long id, @RequestParam String verifyRemark) {
        foodQuoteSheetService.rejectFoodQuoteSheet(id, verifyRemark);
        return true;
    }

    @GetMapping("/{id}")
    public Object info(@PathVariable long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("foodQuoteSheet", foodQuoteSheetService.findById(id));
        map.put("foodQuoteSheetDetailList", foodQuoteSheetDetailService.findByFoodQuoteSheetId(id));
        return map;
    }

    @PutMapping("/verify/{id}")
    public Object verify(@PathVariable long id, @RequestParam String json) {
//        FoodQuoteSheetService.VerifyParam param = JSON.parseObject(json, FoodQuoteSheetService.VerifyParam.class);
//        Result result = foodQuoteSheetService.verifyFoodQuoteSheet(id, param);
        return null;
    }

    @PostMapping()
    public Object create(@RequestParam String json) {
        FoodQuoteSheetService.FoodQuoteSheetAddParam param = JSON.parseObject(json, FoodQuoteSheetService.FoodQuoteSheetAddParam.class);
        long id = foodQuoteSheetService.addFoodQuoteSheet(param);
        return id;
    }

    @GetMapping("/verify/{id}")
    public Object verifyInfo(@PathVariable long id) {
        List<FoodQuoteSheetDetailDTO> detailList = foodQuoteSheetDetailService.findByFoodQuoteSheetId(id);
        FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
        FoodQuoteSheetDTO dto = foodQuoteSheetService.findById(id);
        StoreUserDTO storeUser = dto.getStoreUser();
        List<FoodQuoteSheetDetailVerifyVo> detailVoList = new ArrayList<>();
        for (FoodQuoteSheetDetailDTO detail : detailList) {
            FoodQuoteSheetDetailVerifyVo vo = new FoodQuoteSheetDetailVerifyVo();
            FoodDTO food = detail.getFood();
            vo.setFoodId(food.getId());
            vo.setId(detail.getId());
            vo.setFoodName(food.getName());
            vo.setFoodPicture(detail.getFoodPicture());
            vo.setPrice(detail.getPrice());
            float perIncrease;
            if (food.getPerIncrease() != null && food.getPerIncrease() > 0) {
                perIncrease = food.getPerIncrease() / 100 + 1;
            } else {
                if (storeUser.getPriceIncrease() != null && storeUser.getPriceIncrease() > 0) {
                    perIncrease = storeUser.getPriceIncrease() / 100 + 1;
                } else {
                    perIncrease = foodSetting.getPerIncrease() / 100 + 1;
                }
            }
            vo.setSalePrice(perIncrease * detail.getPrice());
            vo.setFoodUnit(food.getQuoteUnit());
            List<FoodQuoteSkuVo> skus = new ArrayList<>();
//            for (FoodSku sku : food.getSkuList()) {
//                FoodQuoteSkuVo skuVo = new FoodQuoteSkuVo();
//                BeanUtils.copyProperties(sku, skuVo);
//                skuVo.setSalePrice(vo.getSalePrice() * sku.getPriceRatio());
//                skus.add(skuVo);
//            }
            vo.setFoodSkuList(skus);
            detailVoList.add(vo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("detailList", detailVoList);
        return map;
    }
}
