package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.GenericResult;
import com.aliyun.oss.model.ProcessObjectRequest;
import com.ctospace.archit.common.pagination.Paging;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.util.StringUtil;
import com.sankuai.meituan.waimai.opensdk.vo.RetailParam;
import com.sankuai.meituan.waimai.opensdk.vo.RetailSkuParam;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import info.batcloud.wxc.core.domain.FoodCodePublishContent;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.dto.FoodDTO;
import info.batcloud.wxc.core.dto.OnlineFoodDTO;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.entity.FoodCategory;
import info.batcloud.wxc.core.entity.Store;
import info.batcloud.wxc.core.entity.StoreUserFood;
import info.batcloud.wxc.core.enums.FoodSort;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.FoodHelper;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.mapper.FoodCategoryMapper;
import info.batcloud.wxc.core.mapper.domain.FoodCategoryWithFoodNum;
import info.batcloud.wxc.core.repository.FoodQuoteReportRepository;
import info.batcloud.wxc.core.repository.FoodRepository;
import info.batcloud.wxc.core.repository.StoreRepository;
import info.batcloud.wxc.core.repository.StoreUserFoodRepository;
import info.batcloud.wxc.core.service.*;
import info.batcloud.wxc.core.settings.FoodSetting;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.python.antlr.ast.Str;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Inject
    private FoodRepository foodRepository;

    @Inject
    private FoodSkuService foodSkuService;

    @Inject
    private OSSClient ossClient;

    @Inject
    private PublishTraceService publishTraceService;

    @Inject
    private FoodCategoryMapper foodCategoryMapper;

    @Inject
    private FoodQuoteReportRepository foodQuoteReportRepository;

    @Inject
    private MeituanWaimaiService meituanWaimaiService;

    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private StoreUserFoodRepository storeUserFoodRepository;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private FoodCategoryService foodCategoryService;

    @Inject
    private TmpFileService tmpFileService;

    @Inject
    @Lazy
    private StoreUserFoodService storeUserFoodService;

    @Override
    public void setProperties(PropertyParam param) {
        Food food = foodRepository.findOne(param.getId());
//        if (food.getIsSp() != null &&
//                food.getIsSp() == 1 && foodSkus.size() > 1) {
//            //?????????????????????????????????sku
//            throw new RuntimeException("????????????????????????SKU");
//        }
        food.setPropertieJson(JSON.toJSONString(param.getPlist()));
        foodRepository.save(food);
    }

    @Override
    public boolean addShuiYin(String keyWord) {
        List<Food> byNameLike = foodRepository.findByNameContainingAndDeletedFalse(keyWord);
        for (Food food : byNameLike) {
            try {
                if (food.getPicture().startsWith("http://imgs")) {
                    String shuiYin = getShuiYin(food.getPicture());
                    food.setPicture(shuiYin);
                    food.setPictureId(null);
                    food.setJdPicture(null);
                    food.setClbPictureId(null);
                    food.setClbPicture(null);
                    food.setUpdateTime(new Date(10000));
                    foodRepository.save(food);
                    //????????????sotreuserfood????????????????????????
                    //storeUserFoodService.resetByFoodId(food.getId());
                    foodChange(food);
                }
            } catch (Exception e) {
                logger.error("???????????????" + food.getName() + e.getMessage());
            }

        }
        return false;
    }

    @Override
    @Transactional
    public boolean syncFromMeituan(String poiCode) {
        try {
            FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
            Store store = storeRepository.findByCodeAndPlat(poiCode, Plat.MEITUAN);
            float priceIncrease;
            if (store.getPriceIncrease() != null && store.getPriceIncrease() > 0) {
                priceIncrease = store.getPriceIncrease();
            } else {
                priceIncrease = foodSetting.getPerIncrease();
            }
            List<Food> foodList = fetchFromMeituan(poiCode);
//            for (Food food : foodList) {
////                if (foodRepository.countByCodeAndDeletedFalse(food.getCode()) > 0) {
////                    continue;
////                }
////                if (foodRepository.countByNameAndDeletedFalse(food.getName()) > 0) {
////                    continue;
////                }
////                float price = food.getPrice() / (1 + priceIncrease / 100);
////                if (food.getPrice() == null ||
////                        food.getPrice() == 0 || price < food.getPrice()) {
////                    food.setPrice(price);
////                }
//////                List<FoodSku> _foodSkus = JSON.parseObject(food.getSkuJson(), new TypeReference<List<FoodSku>>() {
//////                });
////                Map<String, FoodSku> skuIdMap = new HashMap<>();
////                for (FoodSku skus : _foodSkus) {
////                    if (StringUtils.isNotEmpty(skus.getSkuId())) {
////                        skuIdMap.put(skus.getSkuId(), skus);
////                    }
////                }
////                for (int i = 0; i < _foodSkus.size(); i++) {
////                    FoodSku fs = _foodSkus.get(i);
////                    if (StringUtils.isEmpty(fs.getSkuId())) {
////                        fs.setSkuId(getSkuId(skuIdMap));
////                    }
////                    if (_foodSkus.size() == 1) {
////                        fs.setQuoteUnitRatio(1);
////                        fs.setPriceRatio(1);
////                    }
////                }
////
////                food.setSkuJson(JSON.toJSONString(_foodSkus));
////            }
            foodRepository.save(foodList);
            List<Food> emptyCodeFoodList = foodList.stream().filter(o -> StringUtils.isEmpty(o.getCode())).collect(Collectors.toList());
            for (Food food : emptyCodeFoodList) {
                food.setCode(food.getId().toString());
            }
            foodRepository.save(foodList);
            return true;
        } catch (ApiSysException e) {
            logger.error(e.getExceptionEnum().getMsg(), e);
            logger.error("????????????????????????", e);
        } catch (ApiOpException e) {
            logger.error("????????????????????????" + e.getMsg());
        }
        return false;
    }

    private String getShuiYin(String url) throws Exception {
        String s = url;
        int i = s.lastIndexOf("cn/");
        String fullname = s.substring(i + 3);
        int i1 = fullname.lastIndexOf("/");
        String name = fullname.substring(i1 + 1);
        String bucketName = "tgyx";
        String newName = "sy1" + name;
        String targetImage = fullname.substring(0, i1 + 1) + newName;
        StringBuilder sbStyle = new StringBuilder();
        sbStyle.delete(0, sbStyle.length());
        String styleType = "image/watermark,image_YWR2LzIwMjAwODE4LzQucG5n,t_100,g_south,x_0,y_0";
        Formatter styleFormatter = new Formatter(sbStyle);
        styleFormatter.format("%s|sys/saveas,o_%s,b_%s", styleType,
                BinaryUtil.toBase64String(targetImage.getBytes()),
                BinaryUtil.toBase64String(bucketName.getBytes()));
        ProcessObjectRequest request = new ProcessObjectRequest(bucketName, fullname, sbStyle.toString());
        GenericResult processResult = ossClient.processObject(request);
        String json = null;

        json = com.aliyun.oss.common.utils.IOUtils.readStreamAsString(processResult.getResponse().getContent(), "UTF-8");
        processResult.getResponse().getContent().close();

        System.out.println("???????????????" + json);
        System.out.println(s.substring(0, i + 3) + targetImage);
        return s.substring(0, i + 3) + targetImage;
    }

    private List<Food> fetchFromMeituan(String poiCode) throws ApiSysException, ApiOpException {
        List<Food> foodList = new ArrayList<>();
        SystemParam param = meituanWaimaiService.getSystemParam();
        List<RetailParam> foodParams = APIFactory.getNewRetailApi().retailList(param, poiCode);
        List<String> codeList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for (RetailParam foodParam : foodParams) {
            if (StringUtils.isNotEmpty(foodParam.getApp_food_code())) {
                codeList.add(foodParam.getApp_food_code());
            }
            nameList.add(foodParam.getName());
        }
        List<Food> checkFoodList = foodRepository.findByCodeInOrNameInAndDeletedFalse(codeList, nameList);
        Map<String, Food> foodMapByName = new HashMap<>();
        Map<String, Food> foodMapByCode = new HashMap<>();
        for (Food food : checkFoodList) {
            foodMapByCode.put(food.getCode(), food);
            foodMapByName.put(food.getName(), food);
        }
//        for (RetailParam foodParam : foodParams) {
//            Food food;
//            if (StringUtils.isNotEmpty(foodParam.getApp_food_code())) {
//                food = foodMapByCode.get(foodParam.getApp_food_code());
//            } else {
//                food = foodMapByName.get(foodParam.getName());
//            }
//            if (food == null || food.getDeleted()) {
//                food = new Food();
//                food.setCreateTime(new Date());
//                food.setDeleted(false);
//                food.setQuotable(true);
//            }
//            food.setBoxNum(foodParam.getBox_num());
//            food.setBoxPrice(foodParam.getBox_price());
//            if (foodParam.getSecondary_category_name() == null) {
//                food.setCategoryName(foodParam.getCategory_name());
//            } else {
//                food.setCategoryName(foodParam.getSecondary_category_name());
//            }
//
//            food.setIsSp(foodParam.getIs_sp());
//            if (StringUtils.isEmpty(food.getCode())) {
//                food.setCode(foodParam.getApp_food_code());
//            }
//            food.setDescription(foodParam.getDescription());
//            food.setMinOrderCount(foodParam.getMin_order_count());
//            food.setName(foodParam.getName());
//            food.setPicture(foodParam.getPicture());
//            //food.setUpdateTime(new Date());
//            //food.setPrice(foodParam.getPrice());
//            food.setIdx(1000);
////            this.detectFoodQuoteUnit(food);
//            List<RetailSkuParam> skuParams = foodParam.getSkus();
//            List<FoodSku> foodSkus = new ArrayList<>();
//            List<FoodSku> _foodSkus = JSON.parseObject(food.getSkuJson(), new TypeReference<List<FoodSku>>() {
//            });
//            _foodSkus = _foodSkus == null ? new ArrayList<>() : _foodSkus;
//            Map<String, FoodSku> skuIdMap = new HashMap<>();
//            Map<String, FoodSku> skuSpecMap = new HashMap<>();
//            for (FoodSku skus : _foodSkus) {
//                if (StringUtils.isNotEmpty(skus.getSkuId())) {
//                    skuIdMap.put(skus.getSkuId(), skus);
//                }
//                skuSpecMap.put(skus.getSpec(), skus);
//            }
//            for (RetailSkuParam skuParam : skuParams) {
//                FoodSku sku = skuIdMap.get(skuParam.getSku_id());
//                if (sku == null) {
//                    sku = skuSpecMap.get(skuParam.getSpec());
//                }
//
//                if (sku == null) {
//                    sku = new FoodSku();
//                }
//
//                sku.setBoxNum(skuParam.getBox_num());
//                sku.setBoxPrice(skuParam.getBox_price());
//                sku.setSpec(skuParam.getSpec());
//                foodSkus.add(sku);
//            }
//            food.setSkuJson(JSON.toJSONString(foodSkus));
//            food.setUnit(foodParam.getUnit());
//
//            foodList.add(food);
//        }
        return foodList;
    }

    private void detectFoodQuoteUnit(Food food) {
        if (food.getName().indexOf("250g") != -1
                || food.getName().indexOf("500g") != -1 || food.getName().indexOf("1000g") != -1) {
            food.setQuoteUnit("???");
        } else if (checkBaseUnit(food)) {

        }
    }

    private static String[] BASE_UNIT = new String[]{"???", "???", "???", "???", "???", "???", "???", "???", "???", "???"};

    private boolean checkBaseUnit(Food food) {
        for (String s : BASE_UNIT) {
            if (food.getName().indexOf(s) != -1) {
                food.setQuoteUnit(s);
                return true;
            }
        }
        return false;
    }

    private String getSkuId(Map<String, FoodSku> map) {
        while (true) {
            String skuId = RandomStringUtils.random(5, false, true);
            if (!map.containsKey(skuId)) {
                map.put(skuId, null);
                return skuId;
            }
        }
    }

    @Override
    public void quotable(long id, boolean flag) {
        Food food = foodRepository.findOne(id);
        if (food.getActualFoodId() != null) {
            return;
        }
        food.setQuotable(flag);
        foodRepository.save(food);
    }

    @Override
    public void promotional(long id, boolean flag) {
        Food food = foodRepository.findOne(id);
        if (food.getActualFoodId() != null) {
            return;
        }
        food.setPromotional(flag);
        foodRepository.save(food);
    }

    @Override
    public void setFoodCode(long id, String code) {
        if (foodRepository.countByCodeAndIdNotAndDeletedFalse(code, id) > 0) {
            throw new BizException("??????CODE????????????");
        }
        Food food = foodRepository.findOne(id);
        food.setCode(code);
        foodRepository.save(food);
    }

    @Override
    public void setMeituanTag(long id, String tagName, long tagId) {
        Food food = foodRepository.findOne(id);
        food.setMeituanTagId(tagId);
        food.setMeituanTagName(tagName);
        foodRepository.save(food);
    }

    @Override
    @Transactional
    public void setFoodPrice(long id, float price) {
        Food food = foodRepository.findOne(id);
        food.setPrice(price);
        foodRepository.save(food);
        foodChange(food);
    }

    @Override
    public void setFoodWarningPrice(long id, float warningPrice) {
        Food food = foodRepository.findOne(id);
        food.setWarningPrice(warningPrice);
        foodRepository.save(food);
        foodChange(food);
    }

    @Override
    public void setPerIncrease(long id, float perIncrease) {
        FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
        Food food = foodRepository.findOne(id);
        if (perIncrease == foodSetting.getPerIncrease()) {
            food.setPerIncrease(null);
        } else {
            food.setPerIncrease(perIncrease);
        }
        foodRepository.save(food);
        foodChange(food);
    }

    @Override
    public void setQuoteUnit(long id, String unit) {
        Food food = foodRepository.findOne(id);
        food.setQuoteUnit(unit);
        foodRepository.save(food);
        foodChange(food);
    }

    @Override
    public void deleteById(long id) {
        Food food = foodRepository.findOne(id);
        food.setDeleted(true);
        foodRepository.save(food);
        foodChange(food);
    }

    @Override
    public void changeFoodIdx(long id, int idx) {
        Food food = foodRepository.findOne(id);
        List<Food> foodList = foodRepository.findByCategoryNameOrderByIdxAsc(food.getCategoryName());
        List<Food> sortedFoodList = new ArrayList<>();
        for (int i = 0; i < foodList.size(); i++) {
            if (i == idx - 1) {
                sortedFoodList.add(food);
            }
            Food f = foodList.get(i);
            if (f.getId() != id) {
                sortedFoodList.add(f);
            }
        }
        for (int i = 0; i < sortedFoodList.size(); i++) {
            sortedFoodList.get(i).setIdx(i + 1);
        }
        foodRepository.save(foodList);
    }

    @Override
    public void bindActualFood(long id, long actualFoodId) {
        Assert.isTrue(id != actualFoodId, "????????????????????????");
        Food food = foodRepository.findOne(id);
        food.setActualFoodId(actualFoodId);
        food.setQuotable(false);
        foodRepository.save(food);
    }

    @Override
    public void relieveActualFood(long id) {
        Food food = foodRepository.findOne(id);
        food.setActualFoodId(null);
        foodRepository.save(food);
    }

    @Override
    public FoodDTO forkFoodMirror(long foodId) {
        Food food = new Food();
        Food actualFood = foodRepository.findOne(foodId);
        Assert.isTrue(actualFood.getActualFoodId() == null, "??????????????????????????????");
        BeanUtils.copyProperties(actualFood, food);
        food.setId(null);
        food.setActualFoodId(foodId);
        food.setQuotable(false);
        int count = foodRepository.countByActualFoodId(foodId);
        food.setCode(actualFood.getCode() + "-" + (count + 1));
        food.setName(actualFood.getName() + RandomStringUtils.random(6, true, true));
        foodRepository.save(food);
        return toDTO(food);
    }

    @Override
    public List<OnlineFoodDTO> onlineStoreFood(long storeId) {
        Store store = storeRepository.findOne(storeId);
//        if (store.getPlat() == Plat.MEITUAN) {
//            try {
//                List<OnlineFoodDTO> foodList = new ArrayList<>();
//                SystemParam param = meituanWaimaiService.getSystemParam();
//                List<RetailParam> foodParams = APIFactory.getNewRetailApi().retailList(param, store.getCode());
//                List<String> codeList = new ArrayList<>();
//                List<String> nameList = new ArrayList<>();
//                for (RetailParam foodParam : foodParams) {
//                    if (StringUtils.isNotEmpty(foodParam.getApp_food_code())) {
//                        codeList.add(foodParam.getApp_food_code());
//                    }
//                    nameList.add(foodParam.getName());
//                }
//                List<Food> checkFoodList = foodRepository.findByCodeInOrNameInAndDeletedFalse(codeList, nameList);
//                Map<String, Food> foodMapByName = new HashMap<>();
//                Map<String, Food> foodMapByCode = new HashMap<>();
//                for (Food food : checkFoodList) {
//                    foodMapByCode.put(food.getCode(), food);
//                    foodMapByName.put(food.getName(), food);
//                }
//                for (RetailParam foodParam : foodParams) {
//                    Food food = foodMapByCode.get(foodParam.getApp_food_code());
//                    OnlineFoodDTO oDto = new OnlineFoodDTO();
//                    oDto.setSalePrice(foodParam.getPrice());
//                    if (food != null) {
//                        oDto.setFoodName(food.getName());
//                        oDto.setFoodPicture(food.getPicture());
//                        oDto.setFoodId(food.getId());
//                        oDto.setFoodUnit(food.getQuoteUnit());
//                        oDto.setQuotePrice(foodParam.getPrice() / (1 + store.getPriceIncrease() / 100));
//                        foodList.add(oDto);
//                    } else {
//                        throw new BizException("?????????" + foodParam.getName() + "??????????????????????????????????????????");
//                    }
////                    oDto.setFoodPerIncrease();
//
//                }
//                return foodList;
//            } catch (ApiSysException e) {
//                logger.error(e.getExceptionEnum().getMsg(), e);
//            } catch (ApiOpException e) {
//                logger.error(e.getMsg(), e);
//            }
//        }
        return new ArrayList<>();
    }

    @Override
    public List<FoodCategoryWithFoodNum> findFoodCategoryList() {
        return foodCategoryMapper.findCategoryWithFoodNum();
    }

    @Override
    public List<FoodDTO> detectProblematicFoodList() {
        List<FoodDTO> foodList = new ArrayList<>();
//        SearchParam param = new SearchParam();
//        param.setSort(FoodSort.ID);
//        param.setPageSize(100);
//        int page = 1;
//        while (true) {
//            param.setPage(page++);
//            Paging<FoodDTO> paging = search(param);
//            for (FoodDTO food : paging.getResults()) {
//                /**
//                 * ??????food???quoteUnit
//                 * */
//                if (StringUtils.isEmpty(food.getQuoteUnit())) {
//                    foodList.add(food);
//                } else {
//                    List<FoodSku> skus = food.getSkuList();
//                    if (skus.size() > 0) {
//                        Set<String> skuIdSet = new HashSet<>();
//                        for (FoodSku sku : skus) {
//                            skuIdSet.add(sku.getSkuId());
//                            if (StringUtils.isEmpty(sku.getSkuId()) || sku.getPriceRatio() == 0
//                                    || sku.getQuoteUnitRatio() == 0) {
//                                foodList.add(food);
//                                break;
//                            }
//                        }
//                        if (skuIdSet.size() != skus.size()) {
//                            foodList.add(food);
//                        }
//                    } else {
//                        foodList.add(food);
//                    }
//                }
//            }
//            if (!paging.getHasNext()) {
//                break;
//            }
//        }
        return foodList;
    }

    @Override
    public List<FoodDetectInfo> detectStoreFoodList(long storeId) {
        Store store = storeRepository.findOne(storeId);
        List<FoodDetectInfo> infoList = new ArrayList<>();
        if (store.getPlat() == Plat.MEITUAN) {
            try {
                SystemParam param = meituanWaimaiService.getSystemParam();
                List<RetailParam> foodParams = APIFactory.getNewRetailApi().retailList(param, store.getCode());
                List<String> codeList = new ArrayList<>();
                List<String> nameList = new ArrayList<>();
                for (RetailParam foodParam : foodParams) {
                    if (StringUtils.isNotEmpty(foodParam.getApp_food_code())) {
                        codeList.add(foodParam.getApp_food_code());
                    }
                    nameList.add(foodParam.getName());
                }
                List<Food> checkFoodList = foodRepository.findByCodeInOrNameInAndDeletedFalse(codeList, nameList);
                Map<String, Food> foodMapByName = new HashMap<>();
                Map<String, Food> foodMapByCode = new HashMap<>();
                for (Food food : checkFoodList) {
                    foodMapByCode.put(food.getCode(), food);
                    foodMapByName.put(food.getName(), food);
                }
                for (RetailParam foodParam : foodParams) {
                    FoodDetectInfo info = new FoodDetectInfo();
                    Food food;
                    if (StringUtils.isNotEmpty(foodParam.getApp_food_code())) {
                        food = foodMapByCode.get(foodParam.getApp_food_code());
                    } else {
                        food = foodMapByName.get(foodParam.getName());
                    }
                    info.setFoodCategoryName(foodParam.getCategory_name());
                    info.setStoreCode(store.getCode());
                    info.setOriginCode(foodParam.getApp_food_code());
                    info.setFoodName(foodParam.getName());
                    info.setFoodPicture(foodParam.getPicture());
                    if (food != null) {
                        info.setFoodId(food.getId());
                        info.setFoodCode(food.getCode());
                        info.setFoodUnit(food.getUnit());
                        info.setFoodId(food.getId());
                    } else {

                    }
                    infoList.add(info);
                }
            } catch (ApiSysException e) {
                logger.error(e.getExceptionEnum().getMsg(), e);
                logger.error("??????????????????????????????", e);
            } catch (ApiOpException e) {
                logger.error(e.getMsg(), e);
            }
        } else {
            throw new BizException("???????????????????????????????????????");
        }

        return infoList;
    }

    @Override
    public FoodDTO findById(long id) {
        Food food = foodRepository.findOne(id);
        return toDTO(food);
    }

    @Override
    public Paging<FoodDTO> search(SearchParam param) {
        if (param.getCategoryName() != null && param.getCategoryName().length() != 0) {
            if (foodCategoryService.isParentCategory(param.getCategoryName())) {
                List<String> childCategory = new ArrayList<>();
                List<FoodCategory> byParentName = foodCategoryService.findByParentName(param.getCategoryName());
                if (byParentName.size() > 0) {
                    for (FoodCategory foodCategory : byParentName) {
                        childCategory.add(foodCategory.getName());
                    }
                }
                if (childCategory.size() > 0) {
                    param.setCategoryNameList(childCategory);
                    param.setCategoryName("");
                }
            }
        }
        Specification<Food> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (query.getResultType() != Long.class) {
//                root.fetch("quoteReport", JoinType.LEFT);
            }
            if (StringUtils.isNotEmpty(param.getName())) {
                expressions.add(cb.like(root.get("name"), "%" + param.getName() + "%"));
            }
            if (StringUtils.isNotEmpty(param.getCode())) {
                expressions.add(cb.equal(root.get("code"), param.getCode()));
            }
            if (StringUtils.isNotEmpty(param.getQuoteUnit())) {
                expressions.add(cb.equal(root.get("quoteUnit"), param.getQuoteUnit()));
            }
            if (StringUtils.isNotEmpty(param.getCategoryName())) {
                expressions.add(cb.equal(root.get("categoryName"), param.getCategoryName()));
            }
            if (param.getCategoryNameList() != null && param.getCategoryNameList().size() > 0) {
                expressions.add(cb.in(root.get("categoryName")).value(param.getCategoryNameList()));
            }
            if (param.getQuotable() != null) {
                expressions.add(cb.equal(root.get("quotable"), param.getQuotable()));
            }
            if (param.getSp() != null) {
                expressions.add(cb.equal(root.get("isSp"), param.getSp()));
            }
            if (param.getFoodIdList() != null && param.getFoodIdList().size() > 0) {
                expressions.add(cb.in(root.get("id")).value(param.getFoodIdList()));
            }
            if (param.getFoodId() != null) {
                expressions.add(cb.equal(root.get("id"), param.getFoodId()));
            }
            if (param.getExcludeStoreUserId() != null) {
                Subquery<Long> sufSubquery = query.subquery(Long.class);
                Root sufRoot = sufSubquery.from(StoreUserFood.class);
                Predicate subPredicate = cb.conjunction();
                List<Expression<Boolean>> subExpressions = subPredicate.getExpressions();
                subExpressions.add(cb.equal(sufRoot.get("storeUser").get("id"), param.getExcludeStoreUserId()));
                subExpressions.add(cb.equal(sufRoot.get("food").get("id"), root.get("id")));
                sufSubquery.select(sufRoot.get("food").get("id")).where(subPredicate);
                expressions.add(cb.not(cb.exists(sufSubquery)));
            }
            if (param.getEmptyQuoteUnit() != null) {
                if (param.getEmptyQuoteUnit()) {
                    expressions.add(cb.or(cb.isNull(root.get("quoteUnit")), cb.equal(root.get("quoteUnit"), "")));
                } else {
                    expressions.add(cb.or(cb.isNotNull(root.get("quoteUnit")), cb.notEqual(root.get("quoteUnit"), "")));
                }
            }
            expressions.add(cb.equal(root.get("deleted"), false));
            return predicate;
        };
        Sort sort;
        if (param.getSort() == null) {
            if (StringUtils.isNotEmpty(param.getCategoryName())) {
                sort = new Sort(Sort.Direction.ASC, "idx");
            } else {
                sort = new Sort(Sort.Direction.DESC, "id");
            }
        } else {
            switch (param.getSort()) {
                case ID:
                    sort = new Sort(Sort.Direction.DESC, "id");
                    break;
                case NAME:
                    sort = new Sort(Sort.Direction.DESC, "name");
                    break;
                default:
                    sort = new Sort(Sort.Direction.DESC, "idx");
            }
        }
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<Food> page = foodRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> {
            FoodDTO dto = toDTO(item);
            return dto;
        }, param.getPage(), param.getPageSize());
    }

    @Override
    @Transactional
    public void updateFood(long id, UpdateParam param) {
        if (foodRepository.countByCodeAndIdNotAndDeletedFalse(param.getName(), id) > 0) {
            throw new BizException("????????????????????????");
        }
        Food food = foodRepository.findOne(id);
        //????????????????????????????????? ????????????
        boolean flage = false;
        //?????????????????????????????????????????? ???????????????
        boolean delete = false;
        if (param.getVideo() != null && StringUtils.isNotBlank(param.getVideo())) {
            //????????????????????? ????????????????????????????????????
            if (!param.getVideo().equals(food.getVideo())) {
                flage = true;
                if (food.getVideo() != null && StringUtil.isNotBlank(food.getVideo())) {
                    //?????????????????? ??????????????????????????????
                    delete = true;
                }
            }
        }
        BeanUtils.copyProperties(param, food);
        if (param.getCategoryName().length() == 0 || param.getCategoryName() == "") {
            String parentName = param.getParentCategoryName();
            if (foodCategoryService.findByParentName(parentName).size() == 0 || foodCategoryService.findByParentName(parentName) == null) {
                food.setCategoryName(parentName);
                food.setParentCategoryName(null);
            } else {
                throw new BizException("??????????????????????????????????????????????????????????????????");
            }
        }
        food.setPictureId(null);
        food.setJdPicture(null);
        food.setClbPictureId(null);
        food.setClbPicture(null);
        food.setUpdateTime(new Date(10000));
        foodRepository.save(food);
        //????????????storeuserfood????????????
        if (flage) {
            storeUserFoodService.updateVideoByFoodId(food.getId(), delete, food.getVideo());
        }
        //????????????sotreuserfood????????????????????????
        storeUserFoodService.resetByFoodId(food.getId());
        foodChange(food);
    }

    @Override
    public FoodDTO createFood(CreateParam param) {
        if (StringUtils.isNotEmpty(param.getCode())) {
            if (foodRepository.countByCodeAndDeletedFalse(param.getCode()) > 0) {
                throw new BizException("??????CODE????????????");
            }
        }
        if (foodRepository.countByNameAndDeletedFalse(param.getName()) > 0) {
            throw new BizException("????????????????????????");
        }
        Food food = new Food();
        BeanUtils.copyProperties(param, food);
        if (param.getCategoryName().length() == 0 || param.getCategoryName() == "") {
            String parentName = param.getParentCategoryName();
            if (foodCategoryService.findByParentName(parentName).size() == 0 || foodCategoryService.findByParentName(parentName) == null) {
                food.setCategoryName(parentName);
                food.setParentCategoryName(null);
            } else {
                throw new BizException("??????????????????????????????????????????????????????????????????");
            }
        }
        food.setQuotable(true);
        //food.setSkuJson("[]");
        food.setDeleted(false);
        food.setQuoteUnit(param.getUnit());
        food.setPrice(0f);
        food.setWarningPrice(0f);
        food.setCreateTime(new Date());
        food.setIdx(1000);
        food.setEleBrandName(param.getEleBrandName());
        food.setUpdateTime(new Date());
        food.setMeituanTagId(param.getMeituanTagId());
        foodRepository.save(food);
        food.setCode(food.getId().toString());
        foodRepository.save(food);
        return toDTO(food);
    }


    @Override
    public void setFoodSku(long id, List<FoodSku> foodSkus) {
        Food food = foodRepository.findOne(id);
        if (food.getIsSp() != null &&
                food.getIsSp() == 1 && foodSkus.size() > 1) {
            //?????????????????????????????????sku
            throw new RuntimeException("????????????????????????SKU");
        }
        //food.setSkuJson(JSON.toJSONString(foodSkus));
        foodRepository.save(food);
        //storeUserFoodService.addSkus(id);
    }

    @Override
    public FoodCodePublishResult foodCodePublishToStore(long storeId, FoodCodePublishParam param) {
        FoodCodePublishResult rs = new FoodCodePublishResult();
        rs.setSuccess(true);
        Store store = storeRepository.findOne(storeId);
        for (int i = 0; i < param.getFoodCategoryNameList().size(); i++) {
            String category = param.getFoodCategoryNameList().get(i);
            String name = param.getFoodNameList().get(i);
            String code = param.getFoodCodeList().get(i);
            String originCode = param.getFoodOriginCodeList().get(i);
            if (StringUtils.isEmpty(code)) {
                continue;
            }
            if (code.equals(originCode)) {
                continue;
            }
            FoodCodePublishContent content = publishCodeToStore(name, code, originCode, category, store);

            if (!content.getSuccess()) {
                rs.getMsgList().add(content.getResult());
            }
            rs.setSuccess(content.getSuccess() && rs.isSuccess());
        }
        return rs;
    }


    private FoodCodePublishContent publishCodeToStore(String foodName, String code, String originCode, String categoryName, Store store) {
        FoodCodePublishContent content = new FoodCodePublishContent();
        content.setStoreId(store.getId());
        content.setStoreName(store.getName());
        content.setPlat(store.getPlat());
        if (store.getPlat() == Plat.MEITUAN) {
            SystemParam param = meituanWaimaiService.getSystemParam();
            try {
                String rs;
                if (StringUtils.isEmpty(originCode)) {

                    rs = APIFactory.getFoodAPI().updateAppFoodCodeByNameAndSpec(param, store.getCode(),
                            foodName, categoryName, code, "", "");
                } else {
                    rs = APIFactory.getFoodAPI().updateAppFoodCodeByOrigin(param, originCode, store.getCode(), code, "", "");
                }
                if (rs.equals("ok")) {
                    content.setSuccess(true);
                } else {
                    content.setSuccess(false);
                    content.setResult(rs);
                }
            } catch (ApiOpException e) {
                String msg = foodName + "[" + originCode + ":" + code + "]" + " ????????????????????????" + e.getMsg();
                logger.error(msg);
                content.setSuccess(false);
                content.setResult(msg);
            } catch (ApiSysException e) {
                logger.error(foodName + " ????????????????????????" + e.getExceptionEnum().getMsg());
                content.setSuccess(false);
                content.setResult(foodName + " ????????????????????????" + e.getExceptionEnum().getMsg());
            }
        }
        return content;
    }


    @Override
    public FoodDTO toDTO(Food food) {
        FoodDTO dto = new FoodDTO();
        BeanUtils.copyProperties(food, dto);
        //dto.setSkuList(FoodHelper.parseFoodSkuList(food.getSkuJson()));
        dto.setSkus(foodSkuService.getFoodSkuList(food.getId()));
        dto.setPropertieList(FoodHelper.parseFoodPropertieList(food.getPropertieJson()));
        return dto;
    }

    private static String[] TITLE_LIST = new String[]{"????????????", "?????????", "??????", "??????", "????????????", "????????????", "????????????", "????????????", "????????????"};

    @Override
    public File export(SearchParam sp) throws IOException {
        File excelFile = tmpFileService.createFile("??????_" + System.currentTimeMillis() + ".xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1, true);
        HSSFFont boldFond = workbook.createFont();
        boldFond.setBold(true);
        boldFond.setFontHeightInPoints((short) 16);
        CellStyle foldStyle = workbook.createCellStyle();
        CellStyle commonStyle = workbook.createCellStyle();
        HSSFFont commonFont = workbook.createFont();
        commonFont.setFontHeightInPoints((short) 14);
        commonStyle.setFont(commonFont);
        foldStyle.setFont(boldFond);
        HSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < TITLE_LIST.length; i++) {
            HSSFCell titleCell = titleRow.createCell(i);
            //????????????????????????
            titleCell.setCellValue(TITLE_LIST[i]);
            titleCell.setCellStyle(foldStyle);
        }
        int page = 1;
        int pageSize = 100;
        int rowIndex = 1;
        sp.setPageSize(pageSize);
        while (true) {
            Paging<FoodDTO> paging = this.search(sp);
            sp.setPage(page++);
            for (FoodDTO dto : paging.getResults()) {
                HSSFRow row = sheet.createRow(rowIndex++);
                row.setHeightInPoints(20);
                for (int i = 0; i < TITLE_LIST.length; i++) {
                    HSSFCell rowCell = row.createCell(i);
                    rowCell.setCellStyle(commonStyle);
                    switch (TITLE_LIST[i]) {
                        case "????????????":
                            rowCell.setCellValue(dto.getCode());
                            break;
                        case "?????????":
                            rowCell.setCellValue(dto.getName());
                            break;
                        case "??????":
                            rowCell.setCellValue(dto.getCategoryName());
                            break;
                        case "??????":
                            rowCell.setCellValue(dto.getUnit());
                            break;
                        case "????????????":
                            rowCell.setCellValue(dto.getQuoteUnit());
                            break;
                        case "????????????":
                            rowCell.setCellValue(dto.getPrice());
                            break;
                    }
                }
                //List<FoodSku> skuList = dto.getSkuList();
//                for (int i = 0; i < skuList.size(); i++) {
//                    FoodSku sku = skuList.get(i);
//                    HSSFCell rowCell = row.createCell(6 + i);
//                    rowCell.setCellStyle(commonStyle);
//                    rowCell.setCellValue(dto.getCode());
//                    rowCell.setCellValue(sku.getSpec());
//                }
            }
            if (!paging.getHasNext()) {
                break;
            }
        }
        workbook.write(excelFile);
        return excelFile;
    }

    private void foodChange(Food food) {
        List<Food> foodList = foodRepository.findByActualFoodId(food.getId());
        for (Food f : foodList) {
            f.setPicture(food.getPicture());
            f.setPictureId(food.getPictureId());
            f.setJdPicture(food.getJdPicture());
            f.setUnit(food.getUnit());
            f.setQuoteUnit(food.getQuoteUnit());
            //f.setUpdateTime(new Date());
            f.setEleBrandName(food.getEleBrandName());
        }
        if (foodList.size() > 0) {
            foodRepository.save(foodList);
        }
    }
}
