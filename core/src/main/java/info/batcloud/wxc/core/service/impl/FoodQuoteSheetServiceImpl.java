package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.ctospace.archit.common.pagination.Paging;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.RetailParam;
import com.sankuai.meituan.waimai.opensdk.vo.RetailSkuParam;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import info.batcloud.wxc.core.constants.MessageKeyConstants;
import info.batcloud.wxc.core.domain.FoodQuoteInfo;
import info.batcloud.wxc.core.domain.FoodQuoteSheetStorePublishContent;
import info.batcloud.wxc.core.domain.FoodQuoteSku;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.dto.FoodDTO;
import info.batcloud.wxc.core.dto.FoodQuoteSheetDTO;
import info.batcloud.wxc.core.dto.FoodQuoteSheetDetailDTO;
import info.batcloud.wxc.core.entity.*;
import info.batcloud.wxc.core.enums.FoodQuoteSheetStatus;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.enums.PublishStatus;
import info.batcloud.wxc.core.enums.PublishTraceType;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.*;
import info.batcloud.wxc.core.service.*;
import info.batcloud.wxc.core.settings.FoodSetting;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FoodQuoteSheetServiceImpl implements FoodQuoteSheetService {

    @Inject
    private FoodQuoteSheetRepository foodQuoteSheetRepository;

    @Inject
    private FoodQuoteSheetDetailRepository foodQuoteSheetDetailRepository;

    @Inject
    private FoodRepository foodRepository;

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private MeituanWaimaiService meituanWaimaiService;

    @Inject
    private FoodQuoteSheetDetailService foodQuoteSheetDetailService;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private StoreUserFoodRepository storeUserFoodRepository;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private PublishTraceService publishTraceService;

    @Inject
    private FoodCategoryService foodCategoryService;

    private static final Logger logger = LoggerFactory.getLogger(FoodQuoteSheetServiceImpl.class);

    @Override
    @Transactional
    public long addFoodQuoteSheet(FoodQuoteSheetAddParam param) {
        FoodQuoteSheet sheet = new FoodQuoteSheet();
        sheet.setCreateTime(new Date());
        sheet.setFoodNum(param.getFoodQuoteList().size());
        sheet.setStatus(FoodQuoteSheetStatus.WAIT_VERIFY);
        sheet.setUpdateTime(new Date());
        sheet.setStoreUser(storeUserRepository.findOne(param.getStoreUserId()));
        sheet.setPublishStatus(PublishStatus.WAIT);
        foodQuoteSheetRepository.save(sheet);
        List<FoodQuoteSheetDetail> detailList = new ArrayList<>();
        List<Long> foodIds = new ArrayList<>();
        for (FoodQuoteInfo foodQuoteInfo : param.getFoodQuoteList()) {
            if (!foodIds.contains(foodQuoteInfo.getFoodId())) {
                foodIds.add(foodQuoteInfo.getFoodId());
            } else {
                continue;
            }
            FoodQuoteSheetDetail detail = new FoodQuoteSheetDetail();
            detail.setCreateTime(new Date());
            Food food = foodRepository.findOne(foodQuoteInfo.getFoodId());
            detail.setFood(food);
            detail.setFoodQuoteSheetId(sheet.getId());
            detail.setPrice(foodQuoteInfo.getPrice());
            detail.setFoodName(StringUtils.isEmpty(foodQuoteInfo.getFoodName()) ? food.getName() : foodQuoteInfo.getFoodName());
            detail.setFoodUnit(food.getQuoteUnit());
            detailList.add(detail);
        }
        foodQuoteSheetDetailRepository.save(detailList);
        return sheet.getId();
    }

    @Override
    public FoodQuoteSheetDTO findById(long id) {
        return toDTO(foodQuoteSheetRepository.findOne(id));
    }

    @Override
    public void rejectFoodQuoteSheet(long id, String remark) {
        FoodQuoteSheet sheet = foodQuoteSheetRepository.findOne(id);
        Assert.isTrue(sheet.getStatus() == FoodQuoteSheetStatus.WAIT_VERIFY, "非待审核状态不能拒审");
        sheet.setStatus(FoodQuoteSheetStatus.VERIFY_FAIL);
        sheet.setVerifyRemark(remark);
        foodQuoteSheetRepository.save(sheet);
    }

    @Override
    public Paging<FoodQuoteSheetDTO> search(SearchParam param) {
        Specification<FoodQuoteSheet> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (query.getResultType() != Long.class) {
                root.fetch("storeUser", JoinType.LEFT);
            }
            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (param.getStatus() != null) {
                expressions.add(cb.equal(root.get("status"), param.getStatus()));
            } else {
                expressions.add(cb.notEqual(root.get("status"), FoodQuoteSheetStatus.DELETED));
            }
            if (param.getPublishStatus() != null) {
                expressions.add(cb.equal(root.get("publishStatus"), param.getPublishStatus()));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<FoodQuoteSheet> page = foodQuoteSheetRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item), param.getPage(), param.getPageSize());
    }

    @Override
    @Transactional
    public Result verifyFoodQuoteSheet(long id, VerifyParam param) {
        FoodQuoteSheet sheet = foodQuoteSheetRepository.findOne(id);
        Assert.isTrue(sheet.getStatus() == FoodQuoteSheetStatus.WAIT_VERIFY, "非待审核状态不能审核");
        Result result = new Result();
        sheet.setStatus(FoodQuoteSheetStatus.VERIFY_SUCCESS);
        List<FoodQuoteSheetDetail> details = foodQuoteSheetDetailRepository.findByFoodQuoteSheetId(id);
        Map<Long, FoodQuoteSheetDetail> detailMap = new HashMap<>();
        List<Long> foodIdList = details.stream().map(o -> o.getFood().getId()).collect(Collectors.toList());
        for (FoodQuoteSheetDetail detail : details) {
            detailMap.put(detail.getId(), detail);
        }
        List<StoreUserFood> storeUserFoods = storeUserFoodRepository.findByStoreUserIdAndFoodIdIn(sheet.getStoreUser().getId(), foodIdList);
        Map<Long, StoreUserFood> storeUserFoodMap = new HashMap<>();
        for (StoreUserFood suf : storeUserFoods) {
            storeUserFoodMap.put(suf.getFood().getId(), suf);
        }
        List<StoreUserFood> waitSaveStoreUserFoodList = new ArrayList<>();
        for (FoodQuoteSheetDetailVerifyParam p : param.getDetailList()) {
            FoodQuoteSheetDetail detail = detailMap.get(p.getId());
            if (p.getPrice() > p.getSalePrice()) {
                result.setCode(MessageKeyConstants.QUOTE_PRICE_GREAT_THAN_SALE_PRICE, detail.getFood().getName());
                result.setSuccess(false);
                return result;
            }
            if (p.getSkuList() == null || p.getSkuList().size() == 0) {
                throw new BizException("商品【" + detail.getFood().getName() + "】的SKU信息不存在，请先完善SKU信息！");
            }
            detail.setPrice(p.getPrice());
            detail.setSalePrice(p.getSalePrice());
            detail.setFoodSkuJson(JSON.toJSONString(p.getSkuList()));
            detail.setFoodName(p.getFoodName());
            StoreUserFood suf;
            if (storeUserFoodMap.containsKey(detail.getFood().getId())) {
                suf = storeUserFoodMap.get(detail.getFood().getId());
                suf.setQuotePrice(p.getPrice());

            } else {
                suf = new StoreUserFood();
                suf.setQuotePrice(p.getPrice());
                suf.setCreateTime(new Date());
                suf.setStoreUser(sheet.getStoreUser());
                suf.setFood(detail.getFood());
            }
            suf.setUpdateTime(new Date());
            suf.setSale(true);
            suf.setSalePrice(detail.getSalePrice());
            suf.setFoodUnit(detail.getFoodUnit());
            //suf.setFoodSkuJson(detail.getFoodSkuJson());
            waitSaveStoreUserFoodList.add(suf);
        }
        storeUserFoodRepository.save(waitSaveStoreUserFoodList);
        result.setSuccess(true);
        foodQuoteSheetRepository.save(sheet);
        foodQuoteSheetDetailRepository.save(details);
        return result;
    }

    @Override
    public void retrialFoodQuoteSheet(long id) {
        FoodQuoteSheet sheet = foodQuoteSheetRepository.findOne(id);
        Assert.isTrue(sheet.getStatus() == FoodQuoteSheetStatus.VERIFY_SUCCESS, "非已审核状态不能返审");
        sheet.setStatus(FoodQuoteSheetStatus.WAIT_VERIFY);
        foodQuoteSheetRepository.save(sheet);
    }

    @Override
    @Transactional
    public Result publishToStore(long id) {
        Result rs = new Result();
        FoodQuoteSheet sheet = foodQuoteSheetRepository.findOne(id);
        List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrue(sheet.getStoreUser().getId());
        List<FoodQuoteSheetDetailDTO> details = foodQuoteSheetDetailService.findByFoodQuoteSheetId(id);
        PublishTraceService.TraceParam<List<FoodQuoteSheetStorePublishContent>> traceParam = new PublishTraceService.TraceParam();
        traceParam.setRelationId(sheet.getId().toString());
        List<FoodQuoteSheetStorePublishContent> contents = new ArrayList<>();
        traceParam.setType(PublishTraceType.FOOD_QUOTE_SHEET_PUBLISH);
        if (stores.size() == 0) {
            throw new BizException("暂无平台店铺，无法发布");
        }
        for (Store store : stores) {
            FoodQuoteSheetStorePublishContent _rs = publishToStore(sheet, details, store);
            contents.add(_rs);
        }
        sheet.setPublishStatus(PublishStatus.SUCCESS);
        foodQuoteSheetRepository.save(sheet);
        traceParam.setContent(contents);
        publishTraceService.addTrace(traceParam);
        return rs;
    }

    @Override
    public long countWaitVerifyNum() {
        return foodQuoteSheetRepository.countByStatus(FoodQuoteSheetStatus.WAIT_VERIFY);
    }

    //发布到店铺
    private FoodQuoteSheetStorePublishContent publishToStore(FoodQuoteSheet sheet, List<FoodQuoteSheetDetailDTO> details, Store store) {
        FoodQuoteSheetStorePublishContent rs = new FoodQuoteSheetStorePublishContent();

        rs.setStoreId(store.getId());
        rs.setStoreName(store.getName());
        rs.setPlat(store.getPlat());

        FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);

        if (store.getPlat() == Plat.MEITUAN) {
            SystemParam param = meituanWaimaiService.getSystemParam();
            List<RetailParam> retailParams = null;
            try {
                retailParams = APIFactory.getNewRetailApi().retailList(param, store.getCode());
            } catch (ApiOpException e) {
                String msg = "发布失败：" + e.getMsg();
                logger.error(msg, e);
                rs.getMsgList().add(msg);
                return rs;
            } catch (Exception e) {
                String msg = "发布失败";
                logger.error(msg, e);
                rs.getMsgList().add(msg);
                return rs;
            }
            Map<String, RetailParam> retailParamMapByCode = new HashMap<>();
            for (RetailParam retailParam : retailParams) {
                if (StringUtils.isNotEmpty(retailParam.getApp_food_code())) {
                    retailParamMapByCode.put(retailParam.getApp_food_code(), retailParam);
                }
            }
            List<RetailParam> foodParams = new ArrayList<>();
            for (FoodQuoteSheetDetailDTO detail : details) {
                FoodDTO food = detail.getFood();
                if (food.getDeleted()) {
                    logger.info("商品" + food.getName() + "已经被删除，忽略发布！");
                    rs.getMsgList().add("商品【" + food.getName() + "】已经被删除，忽略发布！");
                    continue;
                }
                RetailParam rp = retailParamMapByCode.get(food.getCode());
                List<FoodQuoteSku> foodQuoteSkus = detail.getFoodQuoteSkuList();
                Map<String, FoodQuoteSku> foodQuoteSkuMapBySkuId = new HashMap<>();
                Map<String, FoodQuoteSku> foodQuoteSkuMapBySpec = new HashMap<>();
                for (FoodQuoteSku quoteSkus : foodQuoteSkus) {
                    foodQuoteSkuMapBySkuId.put(quoteSkus.getSkuId(), quoteSkus);
                    foodQuoteSkuMapBySpec.put(quoteSkus.getSpec(), quoteSkus);
                }
                if (rp == null) {
                    rp = new RetailParam();
                } else {
                    if (rp.getIs_sp() == 1) {
                        /**
                         * 如果是标品，美团是不允许修改商品信息的，只能通过发布sku来进行处理
                         * */
                        RetailSkuParam standardSku = new RetailSkuParam();
                        RetailSkuParam eSku = rp.getSkus().get(0);
                        FoodQuoteSku fos = foodQuoteSkus.get(0);
                        standardSku.setSku_id(eSku.getSku_id());
                        standardSku.setSpec(eSku.getSpec());
                        standardSku.setPrice(String.valueOf(fos.getPrice()));
                        standardSku.setStock("*");
                        standardSku.setBox_price(String.valueOf(foodSetting.getBoxPrice()));
                        standardSku.setBox_num(String.valueOf(foodSetting.getBoxNum()));
                        try {
                            String ok = APIFactory.getNewRetailApi().retailSkuSave(param, store.getCode(), food.getCode(), standardSku, new ArrayList<>());
                            if (ok.equals("ok")) {
                                //发布成功
                                logger.info("更新标品sku成功：" + food.getName() + ":" + food.getCode());
                            } else {
                                logger.error("更新标品sku失败：" + food.getName() + ":" + food.getCode() + "\n" + ok);
                                rs.getMsgList().add(ok);
                                return rs;
                            }
                        } catch (ApiOpException e) {
                            String msg = "更新标品sku失败：" + food.getName() + ":" + food.getCode() + "，" + e.getMsg();
                            logger.error(msg, e);
                            rs.getMsgList().add(msg);
                        } catch (Exception e) {
                            String msg = "更新标品sku失败：" + food.getName() + ":" + food.getCode();
                            logger.error(msg, e);
                            rs.getMsgList().add(msg);
                        }

                        continue;
                    } else {

                        /**
                         * 找到需要删除的sku
                         * */
                        List<RetailSkuParam> waitDeleteSkuList = new ArrayList<>();
                        for (RetailSkuParam skuParam : rp.getSkus()) {
                            /**
                             * 如果一个sku的sku_id为空，那么删除，
                             * 如果一个sku的sku_id不在本次发布的sku列表中，那么也删除
                             * */
                            if (StringUtils.isEmpty(skuParam.getSku_id())) {
                                waitDeleteSkuList.add(skuParam);
                                continue;
                            }
                            if (!foodQuoteSkuMapBySkuId.containsKey(skuParam.getSku_id())) {
                                waitDeleteSkuList.add(skuParam);
                                continue;
                            }
                        }

                        /**
                         * 更新sku信息
                         * */
                        List<RetailSkuParam> skuList = toFoodSkuParamList(foodQuoteSkus);
                        try {
                            String ok = APIFactory.getNewRetailApi().retailSkuSave(param, store.getCode(), food.getCode(), null,
                                    skuList);
                            if (ok.equals("ok")) {
                                //发布成功
                                logger.info("更新非标品sku成功：" + food.getName());
                            } else {
                                logger.error("更新非标品sku失败：" + food.getName() + "\n" + ok);
                                rs.getMsgList().add(ok);
                                return rs;
                            }
                        } catch (ApiOpException e) {
                            String msg = "更新非标品sku失败：" + food.getName() + " " + e.getMsg();
                            logger.error(msg);
                            rs.getMsgList().add(msg);
                        } catch (Exception e) {
                            String msg = "更新非标品sku失败：" + food.getName();
                            logger.error(msg, e);
                            rs.getMsgList().add(msg);
                        }

                        for (RetailSkuParam skuParam : waitDeleteSkuList) {
                            try {
                                String ok = APIFactory.getNewRetailApi().retailSkuDelete(param, store.getCode(), food.getCode(), skuParam.getSku_id());
                                if (ok.equals("ok")) {
                                    //发布成功
                                    logger.info("删除sku成功：" + food.getName() + " " + skuParam.getSpec());
                                } else {
                                    logger.info("删除sku失败：" + food.getName() + " " + skuParam.getSpec());
                                    rs.getMsgList().add(ok);
                                    return rs;
                                }
                            } catch (ApiOpException e) {
                                String msg = "删除sku失败：" + food.getName() + ":" + skuParam.getSpec() + "，" + e.getMsg();
                                logger.error(msg, e);
                                rs.getMsgList().add(msg);
                            } catch (Exception e) {
                                String msg = "删除sku失败：" + food.getName() + ":" + skuParam.getSpec();
                                logger.error(msg, e);
                                rs.getMsgList().add(msg);
                            }
                        }

                    }
                    continue;
                }
                /**
                 * 如果商品不存在，则进行发布
                 * */
                rp.setApp_food_code(food.getCode());
                rp.setApp_poi_code(store.getCode());
                rp.setBox_num(foodSetting.getBoxNum());
                rp.setBox_price(foodSetting.getBoxPrice());
                if (foodCategoryService.isParentCategory(food.getCategoryName())){
                    rp.setCategory_name(food.getCategoryName());
                }else{
                    rp.setSecondary_category_name(food.getCategoryName());
                    rp.setCategory_name(foodCategoryService.getParentCategoryByName(food.getCategoryName()).getName());
                }
                rp.setDescription(food.getDescription());
                rp.setFlavour(food.getFlavour());
                rp.setMin_order_count(food.getMinOrderCount());
                rp.setName(StringUtils.isEmpty(detail.getFoodName()) ? food.getName() : detail.getFoodName());
                rp.setPicture(StringUtils.isNotEmpty(food.getPictureId()) ? food.getPictureId() : food.getPicture());
                rp.setIs_sold_out(0);
                rp.setZh_name(food.getZhName());
                rp.setProduct_name(food.getProductName());
                rp.setOrigin_name(food.getOriginName());
                //rp.setPrice(detail.getSalePrice());
                rp.setUnit(food.getUnit());
                List<RetailSkuParam> skuList = toFoodSkuParamList(foodQuoteSkus);
                rp.setSkus(skuList);
                foodParams.add(rp);
            }
            if (foodParams.size() == 0) {
                rs.setSuccess(true);
                return rs;
            }
            int page = 1, pageSize = 100;
            while (true) {
                try {
                    int start = (page - 1) * pageSize;
                    int end = page * pageSize;
                    if (end > foodParams.size()) {
                        end = foodParams.size();
                    }
                    String result = APIFactory.getNewRetailApi().retailBatchInitData(param, store.getCode(), new ArrayList<>(foodParams.subList(start, end)));
                    rs.getMsgList().add(result);
                    logger.info("发布到美团，返回结果：" + result);
                    sheet.setPublishTime(new Date());
                    if (result.equals("ok")) {
                        logger.info("发布到美团成功");
                        sheet.setPublishStatus(PublishStatus.SUCCESS);
                        rs.setSuccess(true);
                    } else {
                        logger.info("发布到美团失败");
                        rs.getMsgList().add(result);
                        sheet.setPublishStatus(PublishStatus.FAIL);
                        rs.setSuccess(false);
                        return rs;
                    }
                    foodQuoteSheetRepository.save(sheet);
                    if (end >= foodParams.size()) {
                        break;
                    }
                    page++;
                } catch (ApiOpException e) {
                    logger.error("发布失败，" + e.getMsg(), e);
                    rs.getMsgList().add(e.getMsg());
                    break;
                } catch (ApiSysException e) {
                    logger.error("发布失败" + e.getExceptionEnum().getMsg(), e);
                    rs.getMsgList().add(e.getExceptionEnum().getMsg());
                    break;
                }
            }
        }
        return rs;
    }

    private List<RetailSkuParam> toFoodSkuParamList(List<FoodQuoteSku> foodQuoteSkus) {
        List<RetailSkuParam> skuList = new ArrayList<>();
        for (FoodQuoteSku quoteSku : foodQuoteSkus) {
            RetailSkuParam skuParam = new RetailSkuParam();
            skuParam.setSku_id(quoteSku.getSkuId());
            skuParam.setSpec(quoteSku.getSpec());
            skuParam.setPrice(String.valueOf(quoteSku.getPrice()));
            skuParam.setStock("*");
            skuList.add(skuParam);
        }
        return skuList;
    }

    private String updatePictureToMeituan(String picture, String poiCode, String foodName) {
        SystemParam param = meituanWaimaiService.getSystemParam();
        try {
            File file = new File(new URL(picture).toURI());
            String str = APIFactory.getImageApi().imageUpload(param, poiCode, file, foodName);
            System.out.println(str);
            return str;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private FoodQuoteSheetDTO toDTO(FoodQuoteSheet sheet) {
        if (sheet == null) {
            return null;
        }
        FoodQuoteSheetDTO dto = new FoodQuoteSheetDTO();
        BeanUtils.copyProperties(sheet, dto);
        StoreUser su = sheet.getStoreUser();
        dto.setStoreUser(storeUserService.toDTO(su));
        return dto;
    }
}
