package info.batcloud.wxc.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.PLSProductCategory;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.entity.Region;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.repository.FoodRepository;
import info.batcloud.wxc.core.repository.RegionRepository;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.MeituanWaimaiService;
import info.batcloud.wxc.core.service.RegionService;
import info.batcloud.wxc.core.service.StoreUserFoodService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Inject
    private MeituanWaimaiService meituanWaimaiService;

    @Inject
    private FoodRepository foodRepository;

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private RegionService regionService;

    @Inject
    private RegionRepository regionRepository;

    @GetMapping("/handleFoodMeituan")
    @Transactional
    public Object execute() throws ApiSysException, ApiOpException {
        SystemParam systemParam = meituanWaimaiService.getSystemParam();
        List<PLSProductCategory> categories = APIFactory.getNewRetailApi().retailGetSpTagIds(systemParam,"");
        Map<String, PLSProductCategory> mapByPath = new HashMap<>();
        for (PLSProductCategory category : categories) {
            mapByPath.put(category.getNamePath().replace("/", ","), category);
        }
        List<Food> foodList = foodRepository.findByMeituanTagIdNullAndMeituanTagNameIsNotNullOrderByIdDesc();
        for (Food food : foodList) {
            PLSProductCategory category = mapByPath.get(food.getMeituanTagName().replace("/", ","));
            if (category != null) {
                food.setMeituanTagId(category.getId());
            }
        }
        foodRepository.save(foodList);
        return true;
    }


    @PostMapping()
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchSave(@RequestParam String json) {
        System.out.println(json);
        return true;
    }

    @GetMapping("/{id}")
    public Object info(@PathVariable long id) {
        String Json = "{\"msg\":\"数据获取成功\",\"code\":200,\"data\":{\"data\":[{\"id\":26,\"foodId\":26,\"foodName\":\"干桂圆858\",\"shopProcurementId\":45,\"foodNum\":null,\"foodPrice\":null,\"createTime\":1676683676,\"date\":null,\"actualArrivalNum\":null,\"actualArrivalSumprice\":null,\"skuId\":20364,\"updateTime\":null,\"num\":22,\"inputPrice\":22.0,\"img\":null,\"foodPicture\":null},{\"id\":27,\"foodId\":27,\"foodName\":\"黑芝麻300\",\"shopProcurementId\":45,\"foodNum\":null,\"foodPrice\":null,\"createTime\":1676683676,\"date\":null,\"actualArrivalNum\":null,\"actualArrivalSumprice\":null,\"skuId\":20363,\"updateTime\":null,\"num\":22,\"inputPrice\":22.0,\"img\":null,\"foodPicture\":null}],\"logisticsNo\":\"yt:1312323,sf：2222\",\"arrivalNum\":40,\"arrivalPrice\":1000.0}}";
        JSONObject jsonObject1 = JSONObject.parseObject(Json);
        return jsonObject1;
    }


    @Transactional
    @GetMapping("/handle-store-user-region")
    public Object resetStoreUserRegion() {
        Iterable<StoreUser> list = storeUserRepository.findAll();
        int i = 0;
        for (StoreUser storeUser : list) {
            if (storeUser.getCity() != null) {
                continue;
            }
            System.out.println("更新第" + (i++) + "个");
            if (StringUtils.isBlank(storeUser.getAddress())) {
                continue;
            }
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            String url = null;
            try {
                url = "https://restapi.amap.com/v3/geocode/geo?key=4def189810a8c250f135096fcc21946d&address=" + URLEncoder.encode(storeUser.getAddress(), "utf8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                String responseText = IOUtils.toString(new URL(url), "utf8");
                JSONObject jsonObject = JSON.parseObject(responseText);
                if(jsonObject.getIntValue("status") == 1) {
                    JSONArray geocodes = jsonObject.getJSONArray("geocodes");
                    if (geocodes.size() == 0) {
                        continue;
                    }
                    JSONObject geo = geocodes.getJSONObject(0);
                    String cityName = geo.getString("city");
                    String provinceName = geo.getString("province").replace("市", "");
                    String districtName = geo.getString("district");
                    Region province = regionRepository.findByNameLikeAndLevel(provinceName + "%", 0);
                    if (province == null) {
                        System.out.println("未找到province" + provinceName);
                        continue;
                    }
                    Region city = regionRepository.findByNameLikeAndParentId(cityName + "%", province.getId());
                    if (city == null) {
                        System.out.println("未找到city" + cityName);
                        continue;
                    }
                    Region district = regionRepository.findByNameLikeAndParentId(districtName + "%", city.getId());
                    storeUser.setProvince(province);
                    storeUser.setCity(city);
                    storeUser.setDistrict(district);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        storeUserRepository.save(list);
        return true;
    }
}
