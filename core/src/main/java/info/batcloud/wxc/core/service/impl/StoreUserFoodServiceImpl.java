package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.GenericResult;
import com.aliyun.oss.model.ProcessObjectRequest;
import com.ctospace.archit.common.pagination.Paging;
import com.sankuai.meituan.waimai.opensdk.api.API;
import com.sankuai.meituan.waimai.opensdk.api.NewRetailApi;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.*;
import info.batcloud.wxc.core.domain.FoodQuoteSku;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.domain.Propertie;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.dto.*;
import info.batcloud.wxc.core.entity.*;
import info.batcloud.wxc.core.enums.*;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.FoodHelper;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.helper.PictureHelper;
import info.batcloud.wxc.core.helper.StoreUserFoodhelper;
import info.batcloud.wxc.core.mapper.StoreUserFoodMapper;
import info.batcloud.wxc.core.repository.*;
import info.batcloud.wxc.core.service.*;
import info.batcloud.wxc.core.settings.FoodSetting;
import jd.sdk.JingdongClient;
import jd.sdk.JingdongzClient;
import jd.sdk.request.*;
import jd.sdk.response.*;
import me.ele.sdk.up.EleClient;
import me.ele.sdk.up.Response;
import me.ele.sdk.up.request.*;
import me.ele.sdk.up.response.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.aspectj.weaver.ast.Var;
import org.json.JSONObject;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import wante.sdk.up.WanteClient;
import wante.sdk.up.request.*;
import wante.sdk.up.response.*;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class StoreUserFoodServiceImpl implements StoreUserFoodService {

    @Inject
    private ClbmWaiMaiService clbmWaiMaiService;
    @Inject
    private OSSClient ossClient;

    @Inject
    private StoreUserFoodRepository storeUserFoodRepository;

    @Inject
    private SystemSettingService systemSettingService;
    @Inject
    private WanteClient wanteClient;
    @Inject
    @Lazy
    private StoreUserFoodService storeUserFoodService;

    @Inject
    private FoodCategoryRepository foodCategoryRepository;

    @Inject
    private FoodService foodService;

    @Inject
    private TmpFileService tmpFileService;

    @Inject
    private FoodRepository foodRepository;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private FoodCategoryService foodCategoryService;

    @Inject
    private EleClient eleClient;

    @Inject
    private JingdongClient jingdongClient;

    @Inject
    private JingdongzClient jingdongzClient;

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private MeituanWaimaiService meituanWaimaiService;

    @Inject
    private TransactionTemplate transactionTemplate;

    @Inject
    private StoreUserFoodMapper storeUserFoodMapper;

    @Inject
    private FoodSupplierRepository foodSupplierRepository;

    @Inject
    private WarehouseRepository warehouseRepository;

    @Inject
    private FoodSupplierService foodSupplierService;

    @Inject
    private FoodSkuRepository foodSkuRepository;

    @Inject
    private FoodSkuService foodSkuService;

    @Inject
    private StoreUserFoodSkuRepository storeUserFoodSkuRepository;

    @Inject
    private StoreUserFoodSkuService storeUserFoodSkuService;

    @Inject
    private ThreadPoolExecutor threadPoolExecutor;

    private static final Logger logger = LoggerFactory.getLogger(StoreUserFoodServiceImpl.class);

    private static final Double disCountPrice = 2.0;

    private static final Double disCountPrice2 = 2.1;

    private static final int elePriceIncrease = 10;

    @Override
    public void batchSoldOutJddj(List<Long> storeUserId) {
        for (Long id : storeUserId) {
            StoreUser user = storeUserRepository.findOne(id);
            if (user.getJddjOpened()) {
                List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrueAndPlat(id, Plat.JDDJ);
                if (stores.size() == 0 || stores.size() > 1) {
                    throw new BizException("商家未绑定京东店铺或绑定错误" + user.getName());
                }
                Store store = stores.get(0);
                List<StoreUserFood> storeUserFoods = storeUserFoodRepository.findByStoreUserIdAndJddjPublishStatus(id, PublishStatus.SUCCESS);
                for (StoreUserFood storeUserFood : storeUserFoods) {
                    Food food = storeUserFood.getFood();
                    Map<String, String> jddjSkuMap = food.getJddjSkuMap();
                    Set<String> skus = jddjSkuMap.keySet();
                    for (String sku : skus) {
                        UpdateSaleReq req = new UpdateSaleReq();
                        req.setOutSkuId(sku);
                        req.setUserPin("王小菜");
                        req.setOutStationId(store.getCode());
                        req.setDoSale(false);
                        try {
                            UpdateSaleRes res;
                            if (store.getJd()) {
                                res = jingdongClient.request(req);
                            } else {
                                res = jingdongzClient.request(req);
                            }
                            if ("0".equals(res.getCode()) && res.getData().getCode().equals("0")) {
                                logger.info("京东到家下架成功" + food.getName() + store.getName());
                                storeUserFood.setJddjPublishStatus(PublishStatus.IN_STOCK);
                            } else {
                                logger.error("京东到家下架失败" + food.getName() + store.getName() + res.getData().getMsg());
                            }

                        } catch (Exception e) {
                            logger.error("京东到家下架失败", e);
                        }

                    }
                }
            }
        }
    }

    @Override
    public void setMinOrderCount(Long id, Integer count) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        storeUserFood.setMinOrderCount(count);
        storeUserFoodRepository.save(storeUserFood);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetEleStatus(Long id) {
        storeUserFoodRepository.resetEleStatus(id);
        return true;
    }

    @Override
    public List<StoreUserFoodDTO> findByStoreUserIdAndFoodCodeList(long storeUserId, List<String> foodCodeList) {
        Specification<StoreUserFood> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            root.fetch("storeUser", JoinType.LEFT);
            root.fetch("food", JoinType.LEFT);
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.equal(root.get("storeUser").get("id"), storeUserId));
            expressions.add(cb.in(root.get("food").get("code")).value(foodCodeList));
            return predicate;
        };
        List<StoreUserFood> list = storeUserFoodRepository.findAll(specification);
        return list.stream().map(o -> toDTO(o)).collect(Collectors.toList());
    }

    @Override
    public StoreUserFoodDTO findByStoreUserIdAndFoodId(long storeUserId, long foodId) {
        StoreUserFood suf = storeUserFoodRepository.findByStoreUserIdAndFoodId(storeUserId, foodId);
        return toDTO(suf);
    }

    @Override
    public StoreUserFoodDTO findByStoreUserIdAndId(long storeUserId, long id) {
        StoreUserFood suf = storeUserFoodRepository.findByStoreUserIdAndId(storeUserId, id);
        return toDTO(suf);
    }

    @Override
    public void setFoodSupplier(long id, long foodSupplierId) {
        StoreUserFood suf = storeUserFoodRepository.findOne(id);
        suf.setFoodSupplier(foodSupplierRepository.findOne(foodSupplierId));
        storeUserFoodRepository.save(suf);
    }

    @Override
    public boolean soldOut(long storeUserId, long foodId) {
        StoreUserFood suf = storeUserFoodRepository.findByStoreUserIdAndFoodId(storeUserId, foodId);
        return soldOut(suf, null);
    }

    @Override
    public void changePriceIncrease(IncreaseChangeParam param) {
        SearchParam sp = new SearchParam();
        sp.setFoodCategoryName(param.getFoodCategoryName());
        if (param.getOriginIncrease() != 0.0) {
            sp.setPriceIncrease(param.getOriginIncrease());
        }
        //sp.setSale(true);
        sp.setQuoteStatus(QuoteStatus.VERIFY_SUCCESS);


        sp.setSort(StoreUserFoodSort.ID_DESC);
        if (param.getOriginIncrease() == 0.0) {
            List<Long> storeUserIdList = param.getStoreUserIdList();
            sp.setPageSize(5000);
            for (Long aLong : storeUserIdList) {
                sp.setStoreUserId(aLong);
                List<StoreUserFoodDTO> list = this.search(sp).getResults();
                if (list.size() > 0) {
                    transactionTemplate.execute(action -> {
                        for (StoreUserFoodDTO storeUserFood : list) {
                            try {
                                this.batchChangePriceIncrease(storeUserFood.getId(), param.getIncrease() + storeUserFood.getPriceIncrease());
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }
                        }
                        return true;
                    });
                }
            }
            //批量发布有问题，暂时不修改
//            while (true) {
//                List<StoreUserFoodDTO> list = this.search(sp).getResults();
//                if (list.size() > 0) {
//                    transactionTemplate.execute(action -> {
//                        for (StoreUserFoodDTO storeUserFood : list) {
//                            try {
//                                this.batchChangePriceIncrease(storeUserFood.getId(), param.getIncrease() + storeUserFood.getPriceIncrease());
//                            } catch (Exception e) {
//                                logger.error(e.getMessage(), e);
//                            }
//                        }
//                        return true;
//                    });
//                }
//                if (list.size() < 100) {
//                    break;
//                }
//            }
        } else {
            sp.setStoreUserIdList(param.getStoreUserIdList());
            sp.setPageSize(100);
            while (true) {
                List<StoreUserFoodDTO> list = this.search(sp).getResults();
                if (list.size() > 0) {
                    transactionTemplate.execute(action -> {
                        for (StoreUserFoodDTO storeUserFood : list) {
                            try {
                                this.batchChangePriceIncrease(storeUserFood.getId(), param.getIncrease());
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }

                        }
                        return true;
                    });
                }
                if (list.size() < 100) {
                    break;
                }
            }
        }

    }

    @Override
    public void batchChangeQuotePrice(QuoteChangeParam param) {
        SearchParam sp = new SearchParam();
        if (StringUtils.isNotBlank(param.getFoodCategoryName())) {
            sp.setFoodCategoryName(param.getFoodCategoryName());
        }
        sp.setSale(true);
        sp.setQuoteStatus(QuoteStatus.VERIFY_SUCCESS);
        //sp.setStoreUserIdList(param.getStoreUserIdList());
        sp.setPageSize(5000);
        sp.setSort(StoreUserFoodSort.ID_DESC);
        sp.setPage(1);
        List<Long> storeUserIdList = param.getStoreUserIdList();
        for (Long aLong : storeUserIdList) {
            sp.setStoreUserId(aLong);
            List<StoreUserFoodDTO> list = this.search(sp).getResults();
            if (list.size() > 0) {
                transactionTemplate.execute(action -> {
                    for (StoreUserFoodDTO storeUserFood : list) {
                        try {
                            StoreUserFood one = storeUserFoodRepository.findOne(storeUserFood.getId());
                            one.setAlterQuotePrice(one.getQuotePrice() + param.getIncrease());
                            one.setQuotePrice(one.getQuotePrice() + param.getIncrease());
                            float increase = getIncrease(one);
                            one.setSalePrice(one.getQuotePrice() * (1 + increase / 100));
                            one.setPriceIncrease(increase);
                            storeUserFoodRepository.save(one);
                            List<Plat> plats = new ArrayList<>();
                            if (storeUserFood.getMeituanPublishStatus() == PublishStatus.SUCCESS) {
                                plats.add(Plat.MEITUAN);
                            }
                            if (storeUserFood.getElePublishStatus() == PublishStatus.SUCCESS) {
                                plats.add(Plat.ELE);
                            }
                            if (storeUserFood.getClbmPublishStatus() == PublishStatus.SUCCESS) {
                                plats.add(Plat.CLBM);
                            }
                            if (storeUserFood.getJddjPublishStatus() == PublishStatus.SUCCESS) {
                                plats.add(Plat.JDDJ);
                            }
                            this.publish(storeUserFood.getId(), true, plats);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    return true;
                });
            }

        }

        //发布有问题 暂时不修改

//        while (true) {
//            List<StoreUserFoodDTO> list = this.search(sp).getResults();
//            if (list.size() > 0) {
//                transactionTemplate.execute(action -> {
//                    for (StoreUserFoodDTO storeUserFood : list) {
//                        try {
//                            StoreUserFood one = storeUserFoodRepository.findOne(storeUserFood.getId());
//                            one.setAlterQuotePrice(one.getQuotePrice() + param.getIncrease());
//                            one.setQuotePrice(one.getQuotePrice() + param.getIncrease());
//                            float increase = getIncrease(one);
//                            one.setSalePrice(one.getQuotePrice() * (1 + increase / 100));
//                            one.setPriceIncrease(increase);
//                            storeUserFoodRepository.save(one);
//                            List<Plat> plats = new ArrayList<>();
//                            if (storeUserFood.getMeituanPublishStatus() == PublishStatus.SUCCESS) {
//                                plats.add(Plat.MEITUAN);
//                            }
//                            if (storeUserFood.getElePublishStatus() == PublishStatus.SUCCESS) {
//                                plats.add(Plat.ELE);
//                            }
//                            if (storeUserFood.getClbmPublishStatus() == PublishStatus.SUCCESS) {
//                                plats.add(Plat.CLBM);
//                            }
//                            if (storeUserFood.getJddjPublishStatus() == PublishStatus.SUCCESS) {
//                                plats.add(Plat.JDDJ);
//                            }
//                            this.publish(storeUserFood.getId(), true, plats);
//                        } catch (Exception e) {
//                            logger.error(e.getMessage(), e);
//                        }
//                    }
//                    return true;
//                });
//            }
//            if (list.size() < 100) {
//                break;
//            } else {
//                sp.setPage(sp.getPage() + 1);
//            }
//        }

    }

    @Override
    public void lockQuotePrice(Long id, float quotePrice, Date unlockTime) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        storeUserFood.setOriginalQuotePrice(storeUserFood.getQuotePrice());
        storeUserFood.setQuotePrice(quotePrice);
        storeUserFood.setAlterQuotePrice(quotePrice);
        storeUserFood.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
        storeUserFood.setQuotePriceLock(true);
        storeUserFood.setUnlockTime((unlockTime.getTime() + 24 * 60 * 60 * 1000) / 1000);
        logger.info(storeUserFood.getFood().getName() + "锁定报价完成");
        storeUserFoodRepository.save(storeUserFood);
    }

    @Override
    public Boolean unlockQuotePrice(Long id) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        if (storeUserFood.getQuotePriceLock()) {
            if (storeUserFood.getOriginalQuotePrice() != null && storeUserFood.getOriginalQuotePrice() > 0) {
                storeUserFood.setQuotePrice(storeUserFood.getOriginalQuotePrice());
                storeUserFood.setAlterQuotePrice(storeUserFood.getOriginalQuotePrice());
                storeUserFood.setPriceIncrease(40f);
                storeUserFood.setSalePrice(storeUserFood.getOriginalQuotePrice() * 1.4f);
            }
            storeUserFood.setQuotePriceLock(false);
            //storeUserFood.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
            storeUserFoodRepository.save(storeUserFood);
        }
        return true;

    }

    @Override
    public void batchLockQuotePrice(BatchLockParam param) {
        List<LockParam> foodList = param.getFoodList();
        for (LockParam lockParam : foodList) {
            SearchParam sp = new SearchParam();
            sp.setFoodId(lockParam.getFoodId());
            if (!param.getAllStoreUser()) {
                sp.setStoreUserIdList(param.getStoreUserIdList());
            }
            sp.setQuoteStatus(QuoteStatus.VERIFY_SUCCESS);
            sp.setPageSize(100);
            sp.setSort(StoreUserFoodSort.ID_DESC);
            //sp.setPage(1);
            while (true) {
                List<StoreUserFoodDTO> list = this.search(sp).getResults();
                if (list.size() > 0) {
                    transactionTemplate.execute(action -> {
                        for (StoreUserFoodDTO storeUserFood : list) {
                            lockQuotePrice(storeUserFood.getId(), lockParam.getQuotePrice(), lockParam.getStartDate());
                        }
                        return true;
                    });
                }
                if (list.size() < 100) {
                    break;
                }
            }

        }
    }

    @Override
    public void batchChangePrice(BatchChangePriceParam param) {
        List<ChangePriceParam> foodList = param.getFoodList();
        for (ChangePriceParam foodParam : foodList) {
            SearchParam sp = new SearchParam();
            sp.setFoodId(foodParam.getFoodId());
            if (!param.isAllStoreUser()) {
                sp.setStoreUserIdList(param.getStoreUserIdList());
            }
            sp.setQuoteStatus(QuoteStatus.VERIFY_SUCCESS);
            sp.setPageSize(100);
            sp.setSort(StoreUserFoodSort.ID_DESC);
            while (true) {
                List<StoreUserFoodDTO> list = this.search(sp).getResults();
                if (list.size() > 0) {
                    transactionTemplate.execute(action -> {
                        for (StoreUserFoodDTO storeUserFood : list) {
                            StoreUserFood one = storeUserFoodRepository.findOne(storeUserFood.getId());
                            one.setQuotePrice(foodParam.getQuotePrice());
                            storeUserFoodRepository.save(one);
                            this.changePriceIncrease(one.getId(), foodParam.getPerIncrease());
                        }
                        return true;
                    });
                }
                if (list.size() < 100) {
                    break;
                }
            }

        }
    }

    @Override
    public void batchChangePriceIncrease(BatchChangePriceParam param) {
        List<ChangePriceParam> foodList = param.getFoodList();
        for (ChangePriceParam foodParam : foodList) {
            SearchParam sp = new SearchParam();
            sp.setFoodId(foodParam.getFoodId());
            if (!param.isAllStoreUser()) {
                sp.setStoreUserIdList(param.getStoreUserIdList());
            }
            sp.setQuoteStatus(QuoteStatus.VERIFY_SUCCESS);
            sp.setSale(true);
            sp.setPageSize(5000);
            sp.setSort(StoreUserFoodSort.ID_DESC);


            List<StoreUserFoodDTO> list = this.search(sp).getResults();
            if (list.size() > 0) {
                transactionTemplate.execute(action -> {
                    for (StoreUserFoodDTO storeUserFood : list) {
                        StoreUserFood one = storeUserFoodRepository.findOne(storeUserFood.getId());
                        this.changePriceIncrease(one.getId(), foodParam.getPerIncrease());
                    }
                    return true;
                });
            }
        }
    }

    @Override
    public void batchUnlockQuotePrice(BatchLockParam param) {
        List<LockParam> foodList = param.getFoodList();
        for (LockParam lockParam : foodList) {
            SearchParam sp = new SearchParam();
            sp.setFoodId(lockParam.getFoodId());
            sp.setQuotePriceLock(true);
            if (!param.getAllStoreUser()) {
                sp.setStoreUserIdList(param.getStoreUserIdList());
            }
            sp.setQuoteStatus(QuoteStatus.VERIFY_SUCCESS);
            sp.setPageSize(100);
            sp.setSort(StoreUserFoodSort.ID_DESC);
            while (true) {
                List<StoreUserFoodDTO> list = this.search(sp).getResults();
                if (list.size() > 0) {
                    transactionTemplate.execute(action -> {
                        for (StoreUserFoodDTO storeUserFood : list) {
                            unlockQuotePrice(storeUserFood.getId());
                        }
                        return true;
                    });
                }
                if (list.size() < 100) {
                    break;
                }
            }

        }
    }

    @Override
    public void soldOutByFoodId(long foodId) {
        List<StoreUserFood> list = storeUserFoodRepository.findByFoodIdOrFoodActualFoodIdAndSaleTrue(foodId, foodId);
        for (StoreUserFood suf : list) {
            try {
                soldOut(suf, null);
            } catch (Exception e) {
                logger.error("下架失败：", e);
            }
        }
    }

    @Override
    public boolean sale(long storeUserId, long foodId) {
        StoreUserFood suf = storeUserFoodRepository.findByStoreUserIdAndFoodId(storeUserId, foodId);
        if (suf.getQuoteStatus() == QuoteStatus.VERIFY_SUCCESS) {
            publish(suf.getId(), true);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean soldOutById(long foodId) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(foodId);
        return soldOut(storeUserFood, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean soldOutPlatById(long foodId, Plat plat) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(foodId);
        return soldOut(storeUserFood, plat);
    }

    private List<RetailSkuParam> getRetailSkuParam(StoreUserFood storeUserFood) {
        List<RetailSkuParam> list = new ArrayList<>();
        if (StringUtils.isNotBlank(storeUserFood.getSpecialSkuIdList())) {
            List<StoreUserFoodSku> skuList = storeUserFoodSkuRepository.findByStoreUserFoodId(storeUserFood.getId());
            for (StoreUserFoodSku storeUserFoodSku : skuList) {
                if (storeUserFood.getSpecialSkuIdList().indexOf(storeUserFoodSku.getFoodSkuId().toString()) != -1) {
                    RetailSkuParam param = new RetailSkuParam();
                    param.setSku_id(storeUserFoodSku.getFoodSkuId().toString());
                    param.setSpec(storeUserFoodSku.getName());
                    param.setUpc(storeUserFoodSku.getUpc());
                    param.setStock(storeUserFoodSku.getStock().toString());
                    param.setPrice(storeUserFoodSku.getOutputPrice().toString());
                    //param.setLocation_code();
                    //param.setAvailable_times();
                    param.setMin_order_count(storeUserFoodSku.getMinOrderCount());
                    param.setBox_num(storeUserFoodSku.getBoxNum().toString());
                    param.setBox_price(storeUserFoodSku.getBoxPrice().toString());
                    param.setWeight(Long.valueOf(storeUserFoodSku.getWeight()));
                    list.add(param);
                }
            }
        }
        return list;
    }

    // 商品下架（批量删除零售折扣商品）
    private boolean soldOut(StoreUserFood suf, Plat plat) {
        Food food = suf.getFood();
        List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrue(suf.getStoreUser().getId());
        for (Store store : stores) {
            if (plat != null && store.getPlat() != plat) {
                continue;
            }
            if (store.getPlat() == Plat.MEITUAN || store.getPlat() == Plat.CLBM) {
                FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
                SystemParam param = null;
                if (store.getPlat() == Plat.MEITUAN) {
                    param = meituanWaimaiService.getSystemParam();
                } else {
                    param = clbmWaiMaiService.getSystemParam();
                }
                RetailParam rp = new RetailParam();
                rp.setApp_food_code(food.getCode());
                rp.setApp_poi_code(store.getCode());
                rp.setBox_num(foodSetting.getBoxNum());
                rp.setBox_price(foodSetting.getBoxPrice());
                rp.setCategory_name(food.getCategoryName());
                rp.setDescription(food.getDescription());
                rp.setFlavour(food.getFlavour());
                rp.setMin_order_count(suf.getMinOrderCount() == null ? food.getMinOrderCount() : suf.getMinOrderCount());
                rp.setName(food.getName());
                rp.setTag_id(food.getMeituanTagId());
                //rp.setPicture(StringUtils.isNotEmpty(food.getPictureId()) ? food.getPictureId() : food.getPicture());
                rp.setIs_sold_out(1);
                rp.setZh_name(food.getZhName());
                rp.setProduct_name(food.getProductName());
                rp.setOrigin_name(food.getOriginName());
                BigDecimal b = new BigDecimal(suf.getSalePrice());
                //rp.setPrice(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
                //rp.setPrice(suf.getSalePrice());
                rp.setUnit(food.getUnit());
                List<RetailSkuParam> retailSkuParam = getRetailSkuParam(suf);
                if (retailSkuParam.size() == 0) {
                    throw new BizException("请勾选商品规格后再操作");
                }
                rp.setSkus(getRetailSkuParam(suf));
                List<RetailParam> list = new ArrayList<>();
                list.add(rp);
                try {
                    /**
                     * 先查询出门店的折扣活动列表，然后判断当前下架的商品是否参加活动，再执行下架
                     * */
                    List<ActDiscountParam> actList
                            = APIFactory.getActApi().actDiscountList(param, rp.getApp_poi_code(), 0, 200);
                    List<ActDiscountParam> filtedList = actList.stream().filter(p -> p.getApp_food_code().equals(food.getCode())).collect(Collectors.toList());
                    /**
                     * 如果商品存在活动，则调用删除活动的接口，删除商品
                     * */
                    if (filtedList.size() > 0) {
                        //是促销商品 先从活动移除
                        List<String> itemIds = filtedList.stream().map(p -> p.getItem_id().toString()).collect(Collectors.toList());
                        String remove = APIFactory.getActApi().actDiscountDelete(param, rp.getApp_poi_code(), String.join(",", itemIds));
                        logger.info(remove);
                        if (!remove.equals("ok")) {
                        }
                    }


                } catch (Exception e) {
                    logger.error("取消活动失败", e);
                }

                try {
                    //更新商品状态
                    String ok = APIFactory.getNewRetailApi().retailInitData(param, rp);
                    if (!ok.equals("ok")) {
                        logger.error(store.getName() + "美团商品下架失败" + suf.getFood().getName(), ok);
                    } else {
                        logger.info(store.getName() + "美团下架" + suf.getFood().getName() + "成功");
                        if (store.getPlat() == Plat.MEITUAN) {
                            suf.setMeituanPublishStatus(PublishStatus.IN_STOCK);
                        } else {
                            suf.setClbmPublishStatus(PublishStatus.IN_STOCK);
                        }
                    }
                } catch (Exception e) {
                    logger.error("商品下架失败", e);
                }

            } else if (store.getPlat() == Plat.ELE) {
                SkuListRequest skuReq = new SkuListRequest();
                skuReq.setUpc(store.getCode() + "_" + suf.getFood().getCode());
                skuReq.setShopId(store.getCode());
                SkuListResponse skuRes = eleClient.request(skuReq);
                SkuListResponse.Data listData = skuRes.getData();
                try {
                    if (listData.getTotal() > 0) {
                        SkuOfflineRequest req = new SkuOfflineRequest();
                        req.setCustomSkuId(listData.getList().get(0).getCustomSkuId());
                        req.setShopId(store.getCode());
                        SkuOfflineResponse res = eleClient.request(req);
                        if (res.getErrno() == 0) {
                            suf.setElePublishStatus(PublishStatus.IN_STOCK);
                            logger.info("饿百下架成功");
                        } else {
                            logger.error("下架失败，原因：" + res.getError());
                        }
                    } else {
                        logger.error("未找到商品");
                    }
                } catch (Exception e) {
                    logger.error("饿百不存在改商品");
                }

            } else if (store.getPlat() == Plat.WANTE) {
                ProductOffSaleReq offSaleReq = new ProductOffSaleReq();
                offSaleReq.setId(suf.getWanteId().intValue());
                ProductOffsaleRes execute = wanteClient.execute(offSaleReq);
                try {
                    if (execute != null && execute.getCode() == 0) {
                        suf.setWantePublishStatus(PublishStatus.IN_STOCK);
                        logger.info("客户端下架成功" + suf.getFood().getName());
                    } else {
                        logger.error("客户端下架失败，原因：" + execute.getMsg());
                    }
                } catch (Exception e) {
                    logger.error("客户端下架失败");
                }
            } else if (store.getPlat() == Plat.JDDJ) {
                Map<String, String> jddjSkuMap = food.getJddjSkuMap();
                Set<String> skus = jddjSkuMap.keySet();
                for (String sku : skus) {
                    UpdateSaleReq updateSaleReq = new UpdateSaleReq();
                    updateSaleReq.setOutSkuId(sku);
                    updateSaleReq.setUserPin("王小菜");
                    updateSaleReq.setOutStationId(store.getCode());
                    updateSaleReq.setDoSale(false);
                    try {
                        if (store.getJd()) {
                            jingdongClient.request(updateSaleReq);
                        } else {
                            jingdongzClient.request(updateSaleReq);
                        }
                        suf.setJddjPublishStatus(PublishStatus.IN_STOCK);
                    } catch (Exception e) {
                        logger.error("京东到家下架失败" + e.getMessage());
                    }

                }
            }
        }

        boolean isSale = false;
        if (suf.getElePublishStatus() == PublishStatus.SUCCESS && suf.getStoreUser().getEleOpened()) {
            isSale = true;
        }
        if (suf.getMeituanPublishStatus() == PublishStatus.SUCCESS && suf.getStoreUser().getMeituanOpened()) {
            isSale = true;
        }
        if (suf.getJddjPublishStatus() == PublishStatus.SUCCESS && suf.getStoreUser().getJddjOpened()) {
            isSale = true;
        }
        if (suf.getClbmPublishStatus() == PublishStatus.SUCCESS && suf.getStoreUser().getClbmOpened()) {
            isSale = true;
        }

        suf.setSale(isSale);

//        if (suf.getElePublishStatus() != PublishStatus.SUCCESS && suf.getStoreUser().getEleOpened()) {
//            suf.setSale(false);
//        } else if (suf.getMeituanPublishStatus() != PublishStatus.SUCCESS && suf.getStoreUser().getMeituanOpened()) {
//            suf.setSale(false);
//        } else if (suf.getWantePublishStatus() != PublishStatus.SUCCESS && suf.getStoreUser().getWanteOpened()) {
//            suf.setSale(false);
//        } else if (suf.getJddjPublishStatus() != PublishStatus.SUCCESS && suf.getStoreUser().getJddjOpened()) {
//            suf.setSale(false);
//        } else if (suf.getClbmPublishStatus() != PublishStatus.SUCCESS && suf.getStoreUser().getClbmOpened()) {
//            suf.setSale(false);
//        } else {
//            suf.setSale(true);
//        }
        storeUserFoodRepository.save(suf);
        return true;
    }

    //修改门店商品报价
    @Override
    @Transactional
    public StoreUserFoodDTO changeQuotePrice(long id, float quotePrice) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        storeUserFood.setQuotePrice(quotePrice);
        this.changeStoreUserFoodPrice(storeUserFood);
        return toDTO(storeUserFood);
    }

    //修改门店商品加价百分比
    @Override
    public StoreUserFoodDTO changePriceIncrease(long id, float priceIncrease) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        storeUserFood.setPriceIncrease(priceIncrease);
        this.changeStoreUserFoodPrice(storeUserFood);
        return toDTO(storeUserFood);
    }

    private void batchChangePriceIncrease(long id, float priceIncrease) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        storeUserFood.setPriceIncrease(priceIncrease);
        float quotePrice = storeUserFood.getQuotePrice();
        if (storeUserFood.getFood().getActualFoodId() != null) {
            throw new BizException("镜像商品不能修改报价");
        }
        storeUserFood.setQuotePrice(quotePrice);
        storeUserFood.setAlterQuotePrice(quotePrice);
        Food food = storeUserFood.getFood();
        float increase = getIncrease(storeUserFood);
        if (increase == 0) {
            throw new BizException("加价策略为0，无法发布");
        }
        storeUserFood.setFoodUnit(food.getUnit());
        if (storeUserFood.getFood().getActualFoodId() == null) {
            storeUserFood.setSalePrice(quotePrice * (1 + increase / 100));
            storeUserFood.setPriceIncrease(increase);
        }
        if (storeUserFood.getMeituanPublishStatus() == PublishStatus.SUCCESS || storeUserFood.getElePublishStatus() == PublishStatus.SUCCESS || storeUserFood.getJddjPublishStatus() == PublishStatus.SUCCESS || storeUserFood.getClbmPublishStatus() == PublishStatus.SUCCESS) {
            storeUserFood.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
        }
        storeUserFoodRepository.save(storeUserFood);
        /**
         * 同步修改镜像商品的报价
         * */
        List<StoreUserFood> storeUserFoods = storeUserFoodRepository.findByStoreUserIdAndFoodActualFoodId(storeUserFood.getStoreUser().getId(), food.getId());
        for (StoreUserFood suf : storeUserFoods) {
            suf.setQuotePrice(quotePrice);
            if (suf.getMeituanPublishStatus() == PublishStatus.SUCCESS || suf.getElePublishStatus() == PublishStatus.SUCCESS || suf.getJddjPublishStatus() == PublishStatus.SUCCESS || suf.getClbmPublishStatus() == PublishStatus.SUCCESS) {
                suf.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
            }

        }
        if (storeUserFoods.size() > 0) {
            storeUserFoodRepository.save(storeUserFoods);
        }

    }

    private float getIncrease(StoreUserFood storeUserFood) {
        float increase;
        Food food = storeUserFood.getFood();
        if (storeUserFood.getPriceIncrease() != null && storeUserFood.getPriceIncrease() > 0) {
            increase = storeUserFood.getPriceIncrease();
        } else if (storeUserFood.getStoreUser().getPriceIncrease() > 0) {
            increase = storeUserFood.getStoreUser().getPriceIncrease();
        } else if (food.getPerIncrease() > 0) {
            increase = food.getPerIncrease();
        } else {
            FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
            increase = foodSetting.getPerIncrease();
        }
        if (storeUserFood.getQuotePrice() >= 60) {
            if (storeUserFood.getQuotePrice() < 80) {
                increase = 35f;
            } else if (storeUserFood.getQuotePrice() >= 80) {
                increase = 30f;
            }
        }
        return increase;
    }

    private void changeStoreUserFoodPrice(StoreUserFood storeUserFood) {
        float quotePrice = storeUserFood.getQuotePrice();
        if (storeUserFood.getFood().getActualFoodId() != null) {
            throw new BizException("镜像商品不能修改报价");
        }
        storeUserFood.setMeituanPublishStatus(PublishStatus.WAIT);
        storeUserFood.setClbmPublishStatus(PublishStatus.WAIT);
        storeUserFood.setElePublishStatus(PublishStatus.WAIT);
        storeUserFood.setWantePublishStatus(PublishStatus.WAIT);
        storeUserFood.setJddjPublishStatus(PublishStatus.WAIT);
        storeUserFood.setQuotePrice(quotePrice);
        storeUserFood.setAlterQuotePrice(quotePrice);
        Food food = storeUserFood.getFood();
        float increase = getIncrease(storeUserFood);
        if (increase == 0) {
            throw new BizException("加价策略为0，无法发布");
        }
        storeUserFood.setFoodUnit(food.getUnit());
        if (storeUserFood.getFood().getActualFoodId() == null) {
            storeUserFood.setSalePrice(quotePrice * (1 + increase / 100));
            storeUserFood.setPriceIncrease(increase);
        }
        storeUserFood.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
        storeUserFoodRepository.save(storeUserFood);
        /**
         * 同步修改镜像商品的报价
         * */
        List<StoreUserFood> storeUserFoods = storeUserFoodRepository.findByStoreUserIdAndFoodActualFoodId(storeUserFood.getStoreUser().getId(), food.getId());
        for (StoreUserFood suf : storeUserFoods) {
            suf.setQuotePrice(quotePrice);
            suf.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
        }
        if (storeUserFoods.size() > 0) {
            storeUserFoodRepository.save(storeUserFoods);
        }
    }

    @Override
    public StoreUserFoodDTO changeAlterQuotePrice(long storeUserId, long id, float alterQuotePrice) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findByStoreUserIdAndFoodId(storeUserId, id);
        return this.changeAlterQuotePrice(storeUserFood, alterQuotePrice);
    }

    private StoreUserFoodDTO changeAlterQuotePrice(StoreUserFood storeUserFood, float alterQuotePrice) {
        if (storeUserFood.getQuotePriceLock() || storeUserFood.getFood().getCategoryName().equals("赠品专区任选其1")) {
            throw new BizException("活动商品禁止改变报价!");
        } else {
            storeUserFood.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
            storeUserFood.setAlterQuotePrice(alterQuotePrice);
            storeUserFood.setUpdateTime(new Date());
            storeUserFoodRepository.save(storeUserFood);
            return toDTO(storeUserFood);
        }
    }

    @Override
    public List<String> aggregateCategoryOfFoodSupplier(long foodSupplierId) {
        return this.storeUserFoodRepository.aggregateCategoryByFoodSupplierId(foodSupplierId);
    }

    @Override
    @Transactional
    public StoreUserFoodDTO backgroundPublish(long id, boolean checkExists, List<Plat> list) {

        return publish(id, checkExists, list);
    }

    @Override
    @Transactional
    public StoreUserFoodDTO changeSupplierAlterQuotePrice(long foodSupplierId, long id, float quotePrice) {
        StoreUserFood suf = storeUserFoodRepository.findByFoodSupplierIdAndId(foodSupplierId, id);
        return this.changeSupplierAlterQuotePrice(suf, quotePrice);
    }

    @Override
    @Transactional
    public StoreUserFoodDTO changeSupplierAlterQuotePrice(long id, float quotePrice) {
        StoreUserFood suf = storeUserFoodRepository.findOne(id);
        return this.changeSupplierAlterQuotePrice(suf, quotePrice);
    }

    @Override
    @Transactional
    public StoreUserFoodDTO changeSupplierIncrease(long id, float supplierIncrease) {
        StoreUserFood suf = storeUserFoodRepository.findOne(id);
        suf.setSupplierIncrease(supplierIncrease);
        float alterQuotePrice = suf.getSupplierQuotePrice() * (100 + supplierIncrease) / 100;
//        if (alterQuotePrice > suf.getAlterQuotePrice()) {
//            return this.changeAlterQuotePrice(suf, alterQuotePrice);
//        }
        return this.changeAlterQuotePrice(suf, alterQuotePrice);
    }

    private StoreUserFoodDTO changeSupplierAlterQuotePrice(StoreUserFood suf, float supplierAlterQuotePrice) {
        FoodSupplier fs = suf.getFoodSupplier();
        Float alterQuotePrice;
        switch (fs.getPriceIncreaseType()) {
            case NON:
                alterQuotePrice = supplierAlterQuotePrice;
                break;
            case STORE_USER_FOOD:
                alterQuotePrice = supplierAlterQuotePrice * (100 + (suf.getSupplierIncrease() == null ? 10 : suf.getSupplierIncrease())) / 100;
                break;
            default:
                throw new BizException("未设置供应商加价方式，无法修改报价");
        }
        suf.setSupplierAlterQuotePrice(supplierAlterQuotePrice);
        return changeAlterQuotePrice(suf, alterQuotePrice);
    }

    //查找商品
    @Override
    @Transactional
    public Paging<StoreUserFoodDTO> search(SearchParam param) {
        if (StringUtils.isNotBlank(param.getFoodCategoryName())) {
            if (foodCategoryService.isParentCategory(param.getFoodCategoryName())) {
                List<String> childCategory = new ArrayList<>();
                List<FoodCategory> byParentName = foodCategoryService.findByParentName(param.getFoodCategoryName());
                if (byParentName.size() > 0) {
                    for (FoodCategory foodCategory : byParentName) {
                        childCategory.add(foodCategory.getName());
                    }
                }
                if (childCategory.size() > 0) {
                    param.setCategoryNameList(childCategory);
                    param.setFoodCategoryName("");
                }
            }
        } else {
            param.setFoodCategoryName("");
        }
        Specification<StoreUserFood> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            Set<String> joins = new HashSet<>();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
//            expressions.add(cb.gt(root.get("food").get("id"), "%" + param.getFoodName() + "%"));
            if (StringUtils.isNotEmpty(param.getFoodName())) {
//                joins.add("food");
                expressions.add(cb.like(root.get("food").get("name"), "%" + param.getFoodName() + "%"));
            }
            if (StringUtils.isNotEmpty(param.getFoodCode())) {
//                joins.add("food");
                expressions.add(cb.equal(root.get("food").get("code"), param.getFoodCode()));
            }
            if (param.getPriceIncrease() != null) {
                expressions.add(cb.equal(root.get("priceIncrease"), param.getPriceIncrease()));
            }
            if (param.getFoodId() != null) {
//                joins.add("food");
                expressions.add(cb.equal(root.get("food").get("id"), param.getFoodId()));
            }
            if (StringUtils.isNotEmpty(param.getFoodCategoryName())) {
//                joins.add("food");
                expressions.add(cb.equal(root.get("food").get("categoryName"), param.getFoodCategoryName()));
            }
            if (param.getCategoryNameList() != null) {
//                joins.add("food");
                expressions.add(cb.in(root.get("food").get("categoryName")).value(param.getCategoryNameList()));
            }
            if (param.getStoreUserId() != null) {
//                joins.add("storeUser");
                expressions.add(cb.equal(root.get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (param.getStoreUserIdList() != null && param.getStoreUserIdList().size() > 0) {
//                joins.add("storeUser");
                expressions.add(root.get("storeUser").get("id").in(param.getStoreUserIdList()));
            }
            if (param.getWaitPublish() != null && param.getWaitPublish()) {
                expressions.add(cb.or(cb.equal(root.get("meituanPublishStatus"), PublishStatus.WAIT),
                        cb.equal(root.get("elePublishStatus"), PublishStatus.WAIT), cb.equal(root.get("wantePublishStatus"), PublishStatus.WAIT)));
            }
            if (param.getMeituanPublishStatus() != null) {
                expressions.add(cb.equal(root.get("meituanPublishStatus"), param.getMeituanPublishStatus()));
            }
            if (param.getElePublishStatus() != null) {
                expressions.add(cb.equal(root.get("elePublishStatus"), param.getElePublishStatus()));
            }
            if (param.getWantePublishStatus() != null) {
                expressions.add(cb.equal(root.get("wantePublishStatus"), param.getWantePublishStatus()));
            }

            if (param.getJddjPublishStatus() != null) {
                expressions.add(cb.equal(root.get("jddjPublishStatus"), param.getJddjPublishStatus()));
            }

            if (param.getClbmPublishStatus() != null) {
                expressions.add(cb.equal(root.get("clbmPublishStatus"), param.getClbmPublishStatus()));
            }

            if (param.getMaxId() != null) {
                expressions.add(cb.lessThan(root.get("id"), param.getMaxId()));
            }
            if (param.getMinQuotePrice() != null) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("quotePrice"), param.getMinQuotePrice()));
            }
            if (param.getMaxQuotePrice() != null) {
                expressions.add(cb.lessThanOrEqualTo(root.get("quotePrice"), param.getMaxQuotePrice()));
            }
            if (param.getPriceIncreaseLow() != null) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("priceIncrease"), param.getPriceIncreaseLow()));
            }
            if (param.getPriceIncreaseHeigh() != null) {
                expressions.add(cb.lessThanOrEqualTo(root.get("priceIncrease"), param.getPriceIncreaseHeigh()));
            }
            if (param.getQuoteStatus() != null) {
                expressions.add(cb.equal(root.get("quoteStatus"), param.getQuoteStatus()));
            }
            if (param.getPromotional() != null) {
//                joins.add("food");
                expressions.add(cb.equal(root.get("food").get("promotional"), param.getPromotional()));
            }
            if (param.getStoreUserStatus() != null) {
//                joins.add("storeUser");
                expressions.add(cb.equal(root.get("storeUser").get("status"), param.getStoreUserStatus()));
            } else {
//                joins.add("storeUser");
                expressions.add(cb.notEqual(root.get("storeUser").get("status"), StoreUserStatus.DELETED));
            }
            if (param.getStoreUserOpened() != null) {
//                joins.add("storeUser");
                expressions.add(cb.equal(root.get("storeUser").get("opened"), param.getStoreUserOpened()));
            }
            if (param.getFoodIdList() != null && param.getFoodIdList().size() > 0) {
//                joins.add("food");
                expressions.add(cb.in(root.get("food").get("id")).value(param.getFoodIdList()));
            }
            if (param.getQuoteContrast() != null) {
                switch (param.getQuoteContrast()) {
                    case GT_AVG:
//                        joins.add("foodQuoteReport");
                        expressions.add(cb.greaterThan(root.get("quotePrice"), root.get("foodQuoteReport").get("avgQuotePrice")));
                        break;
                    case GT_FOOD:
//                        joins.add("foodQuoteReport");
                        expressions.add(cb.greaterThan(root.get("quotePrice"), root.get("foodQuoteReport").get("warningPrice")));
                        break;
                    case LT_AVG:
//                        joins.add("foodQuoteReport");
                        expressions.add(cb.lessThan(root.get("quotePrice"), root.get("foodQuoteReport").get("avgQuotePrice")));
                        break;
                    case LT_FOOD:
//                        joins.add("foodQuoteReport");
                        expressions.add(cb.lessThan(root.get("quotePrice"), root.get("foodQuoteReport").get("warningPrice")));
                        break;
                }
            }
            if (param.getStoreUserOpened() != null) {
//                joins.add("storeUser");
                expressions.add(cb.equal(root.get("storeUser").get("opened"), param.getStoreUserOpened()));
            }
            if (param.getMeituanOpened() != null) {
//                joins.add("storeUser");
                expressions.add(cb.equal(root.get("storeUser").get("meituanOpened"), param.getMeituanOpened()));
            }
            if (param.getClbmOpened() != null) {
//                joins.add("storeUser");
                expressions.add(cb.equal(root.get("storeUser").get("clbmOpened"), param.getClbmOpened()));
            }
            if (param.getEleOpened() != null) {
//                joins.add("storeUser");
                expressions.add(cb.equal(root.get("storeUser").get("eleOpened"), param.getEleOpened()));
            }
            if (param.getJddjOpened() != null) {
//                joins.add("storeUser");
                expressions.add(cb.equal(root.get("storeUser").get("jddjOpened"), param.getJddjOpened()));
            }
            if (param.getWanteOpened() != null) {
//                joins.add("storeUser");
                expressions.add(cb.equal(root.get("storeUser").get("wanteOpened"), param.getWanteOpened()));
            }
            if (param.getFoodSupplierId() != null) {
                if (param.getFoodSupplierId() == -1) {
                    expressions.add(cb.isNull(root.get("foodSupplier").get("id")));
                } else {
//                    joins.add("foodSupplier");
                    expressions.add(cb.equal(root.get("foodSupplier").get("id"), param.getFoodSupplierId()));
                }
            }
            if (param.getSale() != null) {
                expressions.add(cb.equal(root.get("sale"), param.getSale()));
            }
            if (param.getQuotePriceLock() != null) {
                if (param.getQuotePriceLock()) {
                    expressions.add(cb.equal(root.get("quotePriceLock"), param.getQuotePriceLock()));
                } else {
                    expressions.add(cb.or(cb.equal(root.get("quotePriceLock"), false),
                            cb.isNull(root.get("quotePriceLock"))));
                }

            }
            if (query.getResultType() != Long.class) {
//                root.fetch("food", JoinType.LEFT);
//                root.fetch("foodSupplier", JoinType.LEFT);
//                root.fetch("storeUser", JoinType.LEFT);
//                root.fetch("category", JoinType.LEFT);
//                root.fetch("foodQuoteReport", JoinType.LEFT);
            } else {
                joins.forEach(f -> root.join(f, JoinType.LEFT));
            }
            return predicate;
        };
        Sort sort = null;
        if (param.getSort() == null) {
            param.setSort(StoreUserFoodSort.ID_DESC);
        }
        switch (param.getSort()) {
            case ID_DESC:
                sort = new Sort(Sort.Direction.DESC, "id");
                break;
            case QUOTE_PRICE_DESC:
                sort = new Sort(Sort.Direction.DESC, "quotePrice");
                break;
            case QUOTE_PRICE_ASC:
                sort = new Sort(Sort.Direction.ASC, "quotePrice");
                break;
        }
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<StoreUserFood> page = storeUserFoodRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item), param.getPage(), param.getPageSize());
    }

    //发布
    @Override
    public StoreUserFoodDTO publish(long id, boolean checkExists, List<Plat> platList) {
        //查询出要发布到店铺的商品
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        if (storeUserFood.getQuoteStatus() != QuoteStatus.VERIFY_SUCCESS) {
            throw new BizException("当前商品未审核通过，不能发布！");
        }
        //根据商家查询出要商家的店铺
        List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrueAndPlatIn(storeUserFood.getStoreUser().getId(), platList);
        List<String> msgList = new ArrayList<>();
        boolean success = true;
        for (Store store : stores) {
            //判断店铺是否营业
            if (!store.getOpening()) {
                continue;
            }
            Result rs;
            try {
                rs = publishToStore(storeUserFood, store, checkExists);
            } catch (Exception e) {
                logger.error("发布失败", e);
                rs = new Result();
                //收集发布失败的原因
                rs.setMsg(e.getLocalizedMessage());
                //设置发布失败状态
                if (store.getPlat() == Plat.MEITUAN) {
                    storeUserFood.setMeituanPublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.CLBM) {
                    storeUserFood.setClbmPublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.ELE) {
                    storeUserFood.setElePublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.WANTE) {
                    storeUserFood.setWantePublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.JDDJ) {
                    storeUserFood.setJddjPublishStatus(PublishStatus.FAIL);
                }
            }
            if (!rs.isSuccess()) {
                success = false;
                msgList.add("发布到:" + store.getPlat().getTitle() + " " + store.getName() + "失败，原因：" + rs.getMsg() + " - " + rs.getErrMsg());
            }
        }
        storeUserFood.setPublishMsg(String.join("\n", msgList));
        storeUserFood.setQuotePrice(storeUserFood.getAlterQuotePrice() == null ? storeUserFood.getQuotePrice() : storeUserFood.getAlterQuotePrice());
        if (success) {
            storeUserFood.setSale(true);
            storeUserFood.setFoodVersion(storeUserFood.getFood().getVersion());
        }
        if (storeUserFood.getElePublishStatus() == PublishStatus.SUCCESS
                || storeUserFood.getMeituanPublishStatus() == PublishStatus.SUCCESS || storeUserFood.getClbmPublishStatus() == PublishStatus.SUCCESS || storeUserFood.getWantePublishStatus() == PublishStatus.SUCCESS || storeUserFood.getJddjPublishStatus() == PublishStatus.SUCCESS) {
            storeUserFood.setSale(true);
        } else {
            storeUserFood.setSale(false);
        }
        storeUserFood.setCityId(storeUserFood.getStoreUser().getCity().getId());
        storeUserFoodRepository.save(storeUserFood);
        return toDTO(storeUserFood);
    }

    @Override
    @Transactional
    public StoreUserFoodDTO publish(long id, boolean checkExists) {
        List<Plat> list = new ArrayList<>();
        list.add(Plat.MEITUAN);
        list.add(Plat.ELE);
        list.add(Plat.WANTE);
        list.add(Plat.JDDJ);
        list.add(Plat.CLBM);
        return publish(id, checkExists, list);
    }

    @Override
    @Transactional
    public StoreUserFoodDTO verifyQuoteById(long id) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        changeQuotePrice(id, storeUserFood.getAlterQuotePrice() == null ? storeUserFood.getQuotePrice() : storeUserFood.getAlterQuotePrice());
        storeUserFood.setQuoteStatus(QuoteStatus.VERIFY_SUCCESS);
        storeUserFood.setSupplierQuotePrice(storeUserFood.getSupplierAlterQuotePrice());
        storeUserFood.setElePublishStatus(PublishStatus.WAIT);
        storeUserFood.setMeituanPublishStatus(PublishStatus.WAIT);
        storeUserFood.setClbmPublishStatus(PublishStatus.WAIT);
        storeUserFood.setWantePublishStatus(PublishStatus.WAIT);
        storeUserFood.setJddjPublishStatus(PublishStatus.WAIT);
        storeUserFood.setCityId(storeUserFood.getStoreUser().getCity().getId());
        storeUserFoodRepository.save(storeUserFood);
        return toDTO(storeUserFood);
    }

    @Override
    public void updatePublishByStoreUserId(long storeUserId) {
        long minId = 0;
        List<Store> stores = storeRepository.findByStoreUserIdAndIsOnlineTrue(storeUserId);
        while (true) {
            List<StoreUserFood> storeUserFoodList = storeUserFoodRepository.findTop100ByStoreUserIdAndIdGreaterThanOrderByIdAsc(storeUserId, minId);
            if (storeUserFoodList.size() < 100) {
                break;
            }
            if (storeUserFoodList.size() > 0) {
                minId = storeUserFoodList.get(storeUserFoodList.size() - 1).getId();
            }
            for (Store store : stores) {
                if (store.getPlat() == Plat.MEITUAN) {
                    for (StoreUserFood suf : storeUserFoodList) {
                        if (suf.getMeituanPublishStatus() == PublishStatus.SUCCESS) {
                            publishToStore(suf, store, true);
                        }
                    }
                } else if (store.getPlat() == Plat.ELE) {

                }
            }
        }
    }

    @Override
    public void proofreadSoldOutStoreUserFood(long storeUserId) {
        SearchParam param = new SearchParam();
        param.setStoreUserId(storeUserId);
        param.setSale(false);
        int page = 1;
        param.setPageSize(100);
        while (true) {
            param.setPage(page);
            Paging<StoreUserFoodDTO> paging = search(param);
            if (paging.getResults().size() == 0) {
                break;
            }
            for (StoreUserFoodDTO storeUserFood : paging.getResults()) {
//                if (storeUserFood.getMeituanPublishStatus() != PublishStatus.SUCCESS && storeUserFood.getElePublishStatus() != PublishStatus.SUCCESS) {
//                    continue;
//                }
                try {
                    storeUserFoodService.soldOutById(storeUserFood.getId());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            page++;
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        //如果发布成功，则从网络店铺删除
        Food food = storeUserFood.getFood();
        List<Store> stores = storeRepository.findByStoreUserId(storeUserFood.getStoreUser().getId());
        Boolean pd = true;
        /**
         * 从门店删除商品
         * */
        for (Store store : stores) {
            if ((store.getPlat() == Plat.MEITUAN
                    && (storeUserFood.getMeituanPublishStatus() == PublishStatus.SUCCESS || storeUserFood.getMeituanPublishStatus() == PublishStatus.IN_STOCK)) || (store.getPlat() == Plat.CLBM
                    && (storeUserFood.getClbmPublishStatus() == PublishStatus.SUCCESS || storeUserFood.getClbmPublishStatus() == PublishStatus.IN_STOCK))) {
                SystemParam sp = null;
                if (store.getPlat() == Plat.MEITUAN) {
                    sp = meituanWaimaiService.getSystemParam();
                } else {
                    sp = clbmWaiMaiService.getSystemParam();
                }
                //如果有视频 先把店铺中的视频删除
                if (storeUserFood.getClbmVideoId() != null || storeUserFood.getMeituanVideoId() != null) {
                    if (store.getPlat() == Plat.MEITUAN) {
                        String s = null;
                        try {
                            s = APIFactory.getNewRetailApi().videoDelete(sp, store.getCode(), storeUserFood.getMeituanVideoId());
                        } catch (ApiOpException e) {
                            logger.error(e.getMsg(), e);
                        } catch (ApiSysException e) {
                            logger.error(e.getMessage(), e);
                        }
                        if ("ok".equals(s)) {
                            storeUserFood.setMeituanVideoId(null);
                        }
                    } else {
                        String s = null;
                        try {
                            s = APIFactory.getNewRetailApi().videoDelete(sp, store.getCode(), storeUserFood.getClbmVideoId());
                        } catch (ApiOpException e) {
                            logger.error(e.getMsg(), e);
                        } catch (ApiSysException e) {
                            logger.error(e.getMessage(), e);
                        }
                        if ("ok".equals(s)) {
                            storeUserFood.setClbmVideoId(null);
                        }
                    }
                }
                try {
                    String result = APIFactory.getNewRetailApi().retailDelete(sp, store.getCode(), food.getCode());
                } catch (ApiOpException e) {
                    pd = false;
                    logger.error(e.getMsg(), e);
                } catch (ApiSysException e) {
                    pd = false;
                    logger.error(e.getMessage(), e);
                }
            }
            if (store.getPlat() == Plat.ELE && (storeUserFood.getElePublishStatus() == PublishStatus.SUCCESS || storeUserFood.getElePublishStatus() == PublishStatus.IN_STOCK)) {
                SkuDeleteRequest req = new SkuDeleteRequest();
                req.setCustomSkuId(storeUserFood.getEleSkuId());
                req.setShopId(store.getCode());
                SkuDeleteResponse res = eleClient.request(req);
                if (res.getErrno() == 0) {
                    logger.info("饿百删除成功");
                } else if (res.getErrno() != 0) {
                    pd = false;
                    logger.error("饿百删除失败,原因：" + res.getError());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }

            }
            if (store.getPlat() == Plat.WANTE && (storeUserFood.getWantePublishStatus() == PublishStatus.SUCCESS || storeUserFood.getWantePublishStatus() == PublishStatus.IN_STOCK)) {
                ProductDeleteReq req = new ProductDeleteReq();
                req.setId(storeUserFood.getWanteId().intValue());
                ProductDeleteRes execute = wanteClient.execute(req);
                if (execute.getCode() != null && execute.getCode() == 0) {
                    logger.info("客户端删除成功");

                } else {
                    pd = false;
                    logger.error("客户端删除失败" + execute.getMsg());
                }

            }

            if (store.getPlat() == Plat.JDDJ && (storeUserFood.getJddjPublishStatus() == PublishStatus.SUCCESS || storeUserFood.getJddjPublishStatus() == PublishStatus.IN_STOCK)) {
                Map<String, String> jddjSkuMap = food.getJddjSkuMap();
                Set<String> skus = jddjSkuMap.keySet();
                for (String sku : skus) {
                    UpdateSaleReq updateSaleReq = new UpdateSaleReq();
                    updateSaleReq.setOutSkuId(sku);
                    updateSaleReq.setUserPin("王小菜");
                    updateSaleReq.setOutStationId(store.getCode());
                    updateSaleReq.setDoSale(false);
                    try {
                        if (store.getJd()) {
                            jingdongClient.request(updateSaleReq);
                        } else {
                            jingdongzClient.request(updateSaleReq);
                        }
                    } catch (Exception e) {
                        pd = false;
                        logger.error(e.getMessage(), e);
                    }

                }

            }
        }
        if (pd) {
            storeUserFoodRepository.delete(storeUserFood);
            storeUserFoodSkuRepository.deleteByStoreUserFoodId(id);
        }
    }

    @Override
    public void batchDelete(BatchDeleteParam param) {
        int pageSize = 50;
        param.setPageSize(pageSize);
        while (true) {
            Paging<StoreUserFoodDTO> pg = transactionTemplate.execute((status) -> {
                Paging<StoreUserFoodDTO> paging = search(param);
                for (StoreUserFoodDTO dto : paging.getResults()) {
                    deleteById(dto.getId());
                }
                return paging;
            });
            if (!pg.getHasNext()) {
                break;
            }
        }
    }

    @Override
    public void setCategory(long id, long categoryId) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        FoodCategory category = foodCategoryRepository.findOne(categoryId);
        if (category.getLevel() == 0 && (foodCategoryService.findByParentName(category.getName()) != null || foodCategoryService.findByParentName(category.getName()).size() != 0)) {
            throw new BizException("该分类下不允许直接含有商品，请选择子类后修改");
        }
        storeUserFood.setCategory(category);
        storeUserFoodRepository.save(storeUserFood);
    }

    @Override
    public void clearCategory(long id) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        storeUserFood.setCategory(null);
        storeUserFoodRepository.save(storeUserFood);
    }

    //批量发布
    @Override
    public void batchPublish(BatchPublishParam param) {
        int pageSize = 50;
        SearchParam sp = new SearchParam();
        BeanUtils.copyProperties(param, sp);
        sp.setPageSize(pageSize);
        sp.setQuoteStatus(QuoteStatus.VERIFY_SUCCESS);
        switch (param.getPlat()) {
            case CLBM:
                sp.setClbmPublishStatus(PublishStatus.WAIT);
                sp.setClbmOpened(true);
                break;
            case JDDJ:
                sp.setJddjPublishStatus(PublishStatus.WAIT);
                sp.setJddjOpened(true);
                break;
            case MEITUAN:
                sp.setMeituanPublishStatus(PublishStatus.WAIT);
                sp.setMeituanOpened(true);
                break;
            case ELE:
                sp.setElePublishStatus(PublishStatus.WAIT);
                sp.setEleOpened(true);
                break;
            case WANTE:
                sp.setWantePublishStatus(PublishStatus.WAIT);
                sp.setWanteOpened(true);
                break;
            default:
                throw new BizException("请选择平台");
        }
        sp.setStoreUserOpened(true);
        sp.setStoreUserStatus(StoreUserStatus.VALID);
        while (true) {
            Paging<StoreUserFoodDTO> pg = transactionTemplate.execute((status) -> {
                Paging<StoreUserFoodDTO> paging = search(sp);
                for (StoreUserFoodDTO dto : paging.getResults()) {
                    List<Plat> plats = new ArrayList<>();
                    plats.add(param.getPlat());
                    try {
                        publish(dto.getId(), param.getCheckExists(), plats);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                return paging;
            });
            if (!pg.getHasNext()) {
                break;
            }
        }
        if (param.getStoreUserId() != null && param.getPlat() == Plat.ELE) {
            SearchParam searchParam = new SearchParam();
            searchParam.setElePublishStatus(PublishStatus.FAIL);
            searchParam.setStoreUserId(param.getStoreUserId());
            searchParam.setQuoteStatus(QuoteStatus.VERIFY_SUCCESS);
            while (true) {
                Paging<StoreUserFoodDTO> pg = transactionTemplate.execute((status) -> {
                    Paging<StoreUserFoodDTO> paging = search(searchParam);
                    for (StoreUserFoodDTO dto : paging.getResults()) {
                        List<Plat> plats = new ArrayList<>();
                        plats.add(Plat.ELE);
                        publish(dto.getId(), param.getCheckExists(), plats);
                    }
                    return paging;
                });
                if (!pg.getHasNext()) {
                    break;
                }
            }
        }
        //修改food的更新时间 避免后台定时任务再次发布
        if (param.getFoodId() != null && param.getPlat() == Plat.MEITUAN) {
            Food food = foodRepository.findOne(param.getFoodId());
            food.setUpdateTime(new Date());
            foodRepository.save(food);
        }
    }

    @Override
    public void setSpecialSkuList(long id, List<String> skuIdList) {
        StoreUserFood suf = storeUserFoodRepository.findOne(id);
        suf.setSpecialSkuIdList(skuIdList.size() > 0 ? String.join(",", skuIdList) : null);
        storeUserFoodRepository.save(suf);
    }

    @Override
    public void setEleSkuId(long id, String skuId) {
        StoreUserFood suf = storeUserFoodRepository.findOne(id);
        suf.setEleSkuId(skuId);
        storeUserFoodRepository.save(suf);
    }

    @Override
    public void batchVerify(BatchVerifyParam param) {
        int pageSize = 50;
        param.setPageSize(pageSize);
        param.setQuoteStatus(QuoteStatus.WAIT_VERIFY);

        while (true) {
            Paging<StoreUserFoodDTO> pg = transactionTemplate.execute((status) -> {
                Paging<StoreUserFoodDTO> paging = search(param);
                for (StoreUserFoodDTO dto : paging.getResults()) {
                    verifyQuoteById(dto.getId());
                }
                return paging;
            });
            if (!pg.getHasNext()) {
                break;
            }
        }
    }

    @Override
    public void detectAllChange() {
        List<Store> stores = storeRepository.findByIsOnlineTrue();
        detectChangeByStoreUserIdList(stores.stream().map(o -> o.getId()).collect(Collectors.toList()));
    }

    @Override
    public void detectChangeByStoreUserIdList(List<Long> storeUserIdList) {
        final BigDecimal page = BigDecimal.ONE;
        int pageSize = 200;
        FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
        while (true) {
            boolean flag = transactionTemplate.execute(status -> {

                List<Long> idList = storeUserFoodMapper.detectChange(storeUserIdList, foodSetting.getPerIncrease(), page.intValue(), pageSize);
                if (idList.size() == 0) {
                    return false;
                }
                Specification<StoreUserFood> specification = (root, query, cb) -> {
                    Predicate predicate = cb.conjunction();
                    if (query.getResultType() != Long.class) {
                        root.fetch("food", JoinType.LEFT);
                        root.fetch("storeUser", JoinType.LEFT);
                    }
                    List<Expression<Boolean>> expressions = predicate.getExpressions();
                    expressions.add(cb.in(root.get("id")).value(idList));
                    return predicate;
                };
                List<StoreUserFood> list = storeUserFoodRepository.findAll(specification);
                for (StoreUserFood suf : list) {
                    suf.setPriceIncrease(checkIncrease(suf.getFood(), suf.getStoreUser()));
                    suf.setMeituanPublishStatus(PublishStatus.WAIT);
                    suf.setClbmPublishStatus(PublishStatus.WAIT);
                    suf.setElePublishStatus(PublishStatus.WAIT);
                    suf.setWantePublishStatus(PublishStatus.WAIT);
                    suf.setJddjPublishStatus(PublishStatus.WAIT);
                    suf.setSalePrice(suf.getQuotePrice() * (1 + suf.getPriceIncrease() / 100));
                    suf.setFoodVersion(suf.getFood().getVersion());
                }
                storeUserFoodRepository.save(list);
                return idList.size() == pageSize;
            });
            page.add(BigDecimal.ONE);
            if (!flag) {
                break;
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void resetByFoodId(Long id) {
        List<StoreUserFood> storeUserFoods = storeUserFoodRepository.findByFoodId(id);
        List<Long> onLineFood = new ArrayList<>();
        List<StoreUserFood> offLineFood = new ArrayList<>();
        for (StoreUserFood storeUserFood : storeUserFoods) {
            if (storeUserFood.getSale()) {
                onLineFood.add(storeUserFood.getId());
            } else {
                offLineFood.add(storeUserFood);
            }
        }
        if (onLineFood.size() > 0) {
            storeUserFoodRepository.updateQuoteStatusById(onLineFood, QuoteStatus.WAIT_VERIFY.name());
        }
        if (offLineFood.size() > 0) {
            for (StoreUserFood storeUserFood : offLineFood) {
                try {
                    deleteById(storeUserFood.getId());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        logger.info("更改商品信息完成");

    }

    @Override
    public List<Integer> countCategoryStoreUserFood(long storeUserId, String category) {
        List<Integer> list = new ArrayList<>();
        SearchParam searchParam = new SearchParam();
        searchParam.setStoreUserId(storeUserId);
        searchParam.setFoodCategoryName(category);
        Paging<StoreUserFoodDTO> all = this.search(searchParam);
        list.add(all.getTotal());
        searchParam.setSale(true);
        Paging<StoreUserFoodDTO> sale = this.search(searchParam);
        list.add(sale.getTotal());
        searchParam.setSale(false);
        Paging<StoreUserFoodDTO> onsale = this.search(searchParam);
        list.add(onsale.getTotal());
        return list;
    }

    @Override
    public void updataElePrice(int index) {
        List<Plat> platList = new ArrayList<>();
        platList.add(Plat.ELE);
        List<StoreUser> eleOpened = storeUserRepository.findByStatusAndEleOpenedTrueAndOpenedTrue(StoreUserStatus.VALID);
        for (int i = index; i <= (index + 50); i++) {
            try {
                StoreUser storeUser = eleOpened.get(i);
                List<StoreUserFood> storeUserFoods = storeUserFoodRepository.findByStoreUserIdAndSaleTrueAndElePublishStatus(storeUser.getId(), PublishStatus.SUCCESS);
                for (StoreUserFood storeUserFood : storeUserFoods) {
                    try {
                        logger.info("开始更改" + storeUser.getName() + "饿了么价格,当前位于第" + (i - index));
                        this.publish(storeUserFood.getId(), true, platList);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }
    }

    @Override
    public void setSaleTime(Long id, String saleTime) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        storeUserFood.setSaleTime(saleTime);
        storeUserFoodRepository.save(storeUserFood);
        //发布到各个平台
        if (storeUserFood.getQuoteStatus() == QuoteStatus.VERIFY_SUCCESS) {
            publish(storeUserFood.getId(), true);
        }
    }

    @Override
    public Map<String, String> getSaleTime(Long id) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        return storeUserFood.getSaleTimeMap();
    }

    @Override
    public void batchSoldOutByFoodId(Long foodId, List<Plat> plats) {

        List<StoreUserFood> storeUserFoods = storeUserFoodRepository.findByFoodId(foodId);
        for (StoreUserFood storeUserFood : storeUserFoods) {
            for (Plat plat : plats) {
                try {
                    soldOut(storeUserFood, plat);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void batchSoldOutCategory(BatchSoldCategoryParam param) {
        SearchParam searchParam = new SearchParam();
        searchParam.setFoodCategoryName(param.getFoodCategoryName());
        searchParam.setSale(true);
        searchParam.setStoreUserIdList(param.getStoreUserIdList());
        Integer page = 1;
        searchParam.setPage(page);
        searchParam.setPageSize(100);
        while (true) {
            Paging<StoreUserFoodDTO> paging = this.search(searchParam);
            List<StoreUserFoodDTO> results = paging.getResults();
            System.out.println(results.size());
            for (StoreUserFoodDTO result : results) {
                try {
                    soldOutById(result.getId());
                } catch (Exception e) {
                    logger.error("批量下架分类失败" + e);
                }

            }
            page++;
            searchParam.setPage(page);
            if (!paging.getHasNext()) {
                break;
            }
        }

    }

    @Override
    public void batchChangePrice(float start, float end, float change) {
        SearchParam searchParam = new SearchParam();
        searchParam.setSale(true);
        searchParam.setMinQuotePrice(start);
        searchParam.setMaxQuotePrice(end);
        searchParam.setPageSize(200);
        int page = 1;
        searchParam.setPage(page);
        while (true) {
            Paging<StoreUserFoodDTO> paging = this.search(searchParam);
            List<StoreUserFoodDTO> results = paging.getResults();
            if (results.size() > 0) {
                transactionTemplate.execute(action -> {
                    for (StoreUserFoodDTO storeUserFood : results) {
                        try {
                            this.changePriceIncrease(storeUserFood.getId(), change);
                            this.verifyAndPublish(storeUserFood.getId());
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }

                    }
                    return true;
                });
            }
            if (results.size() < 200) {
                break;
            }
            page++;
            searchParam.setPage(page);
        }
    }

    @Override
    public void updateVideoByFoodId(Long foodId, boolean delete, String video) {
        SystemParam mtParam = meituanWaimaiService.getSystemParam();
        SystemParam clbmParam = clbmWaiMaiService.getSystemParam();
        if (delete) {
            //如果原来有videoId 那么调用美团的接口 先删掉原来的视频
            List<StoreUserFood> storeUserFoods = storeUserFoodRepository.findByFoodId(foodId);
            for (StoreUserFood storeUserFood : storeUserFoods) {
                try {
                    if (storeUserFood.getMeituanVideoId() != null || storeUserFood.getClbmVideoId() != null) {
                        StoreUser storeUser = storeUserFood.getStoreUser();
                        if (storeUserFood.getMeituanVideoId() != null) {
                            Store store = storeRepository.findByStoreUserIdAndPlat(storeUser.getId(), Plat.MEITUAN);
                            String result = APIFactory.getNewRetailApi().videoDelete(mtParam, store.getCode(), storeUserFood.getMeituanVideoId());
                            logger.info("删除视频结果" + store.getName() + result);
                            if ("ok".equals(result)) {
                                storeUserFood.setMeituanVideoId(null);
                            }
                        }
                        if (storeUserFood.getClbmVideoId() != null) {
                            Store store = storeRepository.findByStoreUserIdAndPlat(storeUser.getId(), Plat.CLBM);
                            String result = APIFactory.getNewRetailApi().videoDelete(clbmParam, store.getCode(), storeUserFood.getClbmVideoId());
                            logger.info("删除视频结果" + store.getName() + result);
                            if ("ok".equals(result)) {
                                storeUserFood.setClbmVideoId(null);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }

            }
            storeUserFoodRepository.save(storeUserFoods);
        }
        //删除完成以后 调用批量上传视频接口 来批量上传视频 将回传的视频id绑定到对应的storeUserFood上
        this.updateVideoToPlat(Plat.MEITUAN, foodId, video);
        this.updateVideoToPlat(Plat.CLBM, foodId, video);

    }

    private void updateVideoToPlat(Plat plat, Long foodId, String video) {
        SystemParam param = null;
        if (plat == Plat.MEITUAN) {
            param = meituanWaimaiService.getSystemParam();
        } else if (plat == Plat.CLBM) {
            param = clbmWaiMaiService.getSystemParam();
        } else {
            return;
        }
        SearchParam searchParam = new SearchParam();
        searchParam.setSale(true);
        if (plat == Plat.MEITUAN) {
            searchParam.setMeituanOpened(true);
        } else {
            searchParam.setClbmOpened(true);
        }
        searchParam.setFoodId(foodId);
        int page = 1;
        searchParam.setPage(page);
        int pagesize = 50;
        searchParam.setPageSize(pagesize);
        while (true) {
            try {
                Paging<StoreUserFoodDTO> paging = this.search(searchParam);
                searchParam.setPage(searchParam.getPage() + 1);
                List<StoreUserFoodDTO> dtoList = paging.getResults();
                if (dtoList.size() > 0) {
                    StringBuilder str = new StringBuilder("");
                    for (StoreUserFoodDTO storeUserFoodDTO : dtoList) {
                        try {
                            Store store = storeRepository.findByStoreUserIdAndPlat(storeUserFoodDTO.getStoreUser().getId(), plat);
                            str.append("," + store.getCode());
                        } catch (Exception e) {
                            logger.error(storeUserFoodDTO.getStoreUser().getId() + "品牌" + plat.getTitle());
                        }
                    }
                    str.delete(0, 1);
                    System.out.println(str);
                    if (StringUtils.isBlank(str)) {
                        break;
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                    }
                    List<VideoParam> videoParams = APIFactory.getNewRetailApi().videoUpload(param, String.valueOf(str), PictureHelper.image2byte(video), String.valueOf(foodId));
                    for (VideoParam videoParam : videoParams) {
                        try {
                            Long videoId = videoParam.getVideo_id();
                            String app_poi_code = videoParam.getApp_poi_code();
                            Store store = storeRepository.findByCodeAndPlat(app_poi_code, plat);
                            StoreUser storeUser = store.getStoreUser();
                            StoreUserFood storeUserFood = storeUserFoodRepository.findByStoreUserIdAndFoodId(storeUser.getId(), foodId);
                            if (plat == Plat.MEITUAN) {
                                storeUserFood.setMeituanVideoId(videoId);
                            } else {
                                storeUserFood.setClbmVideoId(videoId);
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
                if (!paging.getHasNext()) {
                    break;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

    }

    @Override
    public void batchCheckPlatFood(BatchCheckPlatFoodParam platFoodParam) {
        if (platFoodParam.isAllStoreUser() && platFoodParam.getStoreUserIdList().size() == 0) {
            List<Store> allStore = new ArrayList<>();
            List<Store> meituanStore = storeRepository.findByOpeningTrueAndPlat(Plat.MEITUAN);
            List<Store> clbmStore = storeRepository.findByOpeningTrueAndPlat(Plat.CLBM);
            allStore.addAll(meituanStore);
            allStore.addAll(clbmStore);
            for (Store store : allStore) {
                try {
                    this.checkStoreUserFood(store.getId());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }

            }
        } else {
            List<Long> storeUserIdList = platFoodParam.getStoreUserIdList();
            List<Plat> platList = new ArrayList<>();
            platList.add(Plat.MEITUAN);
            platList.add(Plat.CLBM);
            for (Long aLong : storeUserIdList) {
                List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrueAndPlatIn(aLong, platList);
                for (Store store : stores) {
                    this.checkStoreUserFood(store.getId());
                }
            }
        }
    }

    @Override
    public void checkStoreUserFood(long id) {
        //List<Store> byOpeningTrueAndPlat = storeRepository.findByOpeningTrueAndPlat(Plat.MEITUAN);
        Store store = storeRepository.findOne(id);
        Plat plat = store.getPlat();
        if (plat != Plat.MEITUAN && plat != Plat.CLBM) {
            return;
        }
        logger.info(store.getName() + "开始核对商品");
        StoreUser storeUser = store.getStoreUser();
        if (storeUser.getStatus() == StoreUserStatus.DELETED) {
            return;
        }
        SystemParam param = null;
        List<Plat> platList = new ArrayList<>();
        platList.add(plat);
        List<RetailParam> retailParams = new ArrayList<>();
        if (plat == Plat.MEITUAN) {
            param = meituanWaimaiService.getSystemParam();
        } else {
            param = clbmWaiMaiService.getSystemParam();
        }
        try {
            retailParams = APIFactory.getNewRetailApi().retailList(param, store.getCode());
        } catch (ApiOpException e) {
            logger.error(e.getMsg(), e);
        } catch (ApiSysException e) {
            logger.error(e.getMessage(), e);
        }
        for (RetailParam retailParam : retailParams) {
            if (retailParam.getIs_sold_out() == 1) {
                //在美团上是下架状态
                try {
                    String foodCode = retailParam.getApp_food_code();
                    StoreUserFood storeUserFood = storeUserFoodRepository.findByStoreUserIdAndFoodId(storeUser.getId(), Long.parseLong(foodCode));
                    if (storeUserFood != null) {
                        if ((plat == Plat.MEITUAN && storeUserFood.getMeituanPublishStatus() == PublishStatus.SUCCESS) || (plat == Plat.CLBM && storeUserFood.getClbmPublishStatus() == PublishStatus.SUCCESS)) {
                            this.publish(storeUserFood.getId(), true, platList);
                        }

                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    logger.error("查找门店商品出错" + storeUser.getName() + retailParam.getName());
                }
            }
        }
        logger.info(store.getName() + "商品核对完成");


    }

    @Override
    public List<StoreUserFoodDTO> getWhSufList(Long wid) {
        Warehouse warehouse = warehouseRepository.findOne(wid);
        List<StoreUserFoodDTO> list = new ArrayList<>();
        if (StringUtils.isNotBlank(warehouse.getFoodIds())) {
            String[] split = warehouse.getFoodIds().split(",");
            for (String s : split) {
                list.add(toDTO(storeUserFoodRepository.findOne(Long.valueOf(s))));
            }
        }
        return list;
    }

    @Override
    public void syncStock(long id) {
//        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
//        List<FoodSku> _foodSkus = JSON.parseObject(storeUserFood.getFoodSkuJson(), new TypeReference<List<FoodSku>>() {
//        });
//
//        StoreUser storeUser = storeUserFood.getStoreUser();
//        Store store = storeRepository.findByStoreUserIdAndPlat(storeUser.getId(), Plat.MEITUAN);
//        try {
//            RetailParam retailGet = APIFactory.getNewRetailApi().retailGet(meituanWaimaiService.getSystemParam(), store.getCode(), storeUserFood.getFood().getCode());
//            List<RetailSkuParam> list = retailGet.getSkus();
//            for (RetailSkuParam retailSkuParam : list) {
//                for (FoodSku foodSkus : _foodSkus) {
//                    if (retailSkuParam.getSku_id().equals(foodSkus.getSkuId())) {
//                        foodSkus.setStock(Integer.valueOf(retailSkuParam.getStock()));
//                    }
//                }
//            }
//            storeUserFood.setFoodSkuJson(JSON.toJSONString(_foodSkus));
//            storeUserFoodRepository.save(storeUserFood);
//        } catch (ApiOpException e) {
//            e.printStackTrace();
//        } catch (ApiSysException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void addSkus(long foodId) {
//        Food food = foodRepository.findOne(foodId);
//        FoodDTO foodDTO = foodService.toDTO(food);
//        List<FoodSku> foodSkus = foodDTO.getSkuList();
//        Map<String, FoodSku> map = new HashMap<>();
//
//        List<StoreUserFood> storeUserFoods = storeUserFoodRepository.findByFoodId(foodId);
//        for (StoreUserFood storeUserFood : storeUserFoods) {
//            List<FoodSku> skuList = JSON.parseObject(storeUserFood.getFoodSkuJson(), new TypeReference<List<FoodSku>>() {
//            });
//            for (FoodSku foodSku : skuList) {
//                map.put(foodSku.getSkuId(), foodSku);
//            }
//
//            for (FoodSku skus : foodSkus) {
//                if (map.get(skus.getSkuId()) == null) {
//                    skuList.add(skus);
//                }
//            }
//
//            storeUserFood.setFoodSkuJson(JSON.toJSONString(skuList));
//            storeUserFoodRepository.save(storeUserFood);
//            //清空
//            map.clear();
//
//        }
    }

    @Override
    public StoreUserFoodDTO findById(Long id) {
        return toDTO(storeUserFoodRepository.findOne(id));
    }

//    @Override
//    public void updateStock(long id, List<String> skuIds, List<Integer> stock) {
//        StoreUserFood userFood = storeUserFoodRepository.findOne(id);
//        List<FoodSku> bzSkuList = JSON.parseObject(userFood.getFood().getSkuJson(), new TypeReference<List<FoodSku>>() {
//        });
//        HashMap<String, FoodSku> map = new HashMap<>();
//        for (FoodSku foodSku : bzSkuList) {
//            map.put(foodSku.getSkuId(), foodSku);
//        }
//        List<FoodSku> newList = new ArrayList<>();
//
//        for (int i = 0; i < skuIds.size(); i++) {
//            FoodSku foodSku = map.get(skuIds.get(i));
//            foodSku.setStock(stock.get(i));
//            newList.add(foodSku);
//        }
//        userFood.setFoodSkuJson(JSON.toJSONString(newList));
//        storeUserFoodRepository.save(userFood);
//        if (userFood.getSale() && userFood.getMeituanPublishStatus() == PublishStatus.SUCCESS) {
//            //门店商品如果已经发布成功了，那么把库存更新上去
//            this.publish(userFood.getId(), true);
//        }
//    }

    @Override
    public void batchSetSaleTime(BatchSaleTimeParam batchSaleTime) {
        //解析售卖时间
        HashMap<String, String> map = new HashMap();
        map.put("monday", "");
        map.put("tuesday", "");
        map.put("wednesday", "");
        map.put("thursday", "");
        map.put("friday", "");
        map.put("saturday", "");
        map.put("sunday", "");
        List<String> weeks = batchSaleTime.getWeeks();
        for (String week : weeks) {
            map.put(week, batchSaleTime.getTimes());
        }
        JSONObject json = new JSONObject(map);
        String s = json.toString();
        //设置售卖时间
        List<LockParam> foodList = batchSaleTime.getFoodList();
        for (LockParam lockParam : foodList) {
            SearchParam sp = new SearchParam();
            sp.setFoodId(lockParam.getFoodId());
            if (!batchSaleTime.getAllStoreUser()) {
                sp.setStoreUserIdList(batchSaleTime.getStoreUserIdList());
            }
            sp.setQuoteStatus(QuoteStatus.VERIFY_SUCCESS);
            sp.setPageSize(100);
            sp.setSort(StoreUserFoodSort.ID_DESC);
            sp.setPage(1);
            while (true) {
                List<StoreUserFoodDTO> list = this.search(sp).getResults();
                if (list.size() > 0) {
                    transactionTemplate.execute(action -> {
                        for (StoreUserFoodDTO storeUserFood : list) {
                            this.setSaleTime(storeUserFood.getId(), s);
                        }
                        return true;
                    });
                }
                if (list.size() < 100) {
                    break;
                } else {
                    sp.setPage(sp.getPage() + 1);
                }
            }

        }
    }

    @Override
    public void batchUpdateTips(Plat plat) {
        List<Store> storeList = storeRepository.findByOpeningTrueAndPlat(plat);
        for (Store store : storeList) {
            try {
                logger.info("更新店铺美团类目：" + store.getName());
                this.updateTips(store.getId());
                Thread.sleep(500);
            } catch (Exception e) {
                logger.error("同步美团类目出错" + store.getName(), e);
            }
        }
    }

    @Override
    public void updateTips(long storeId) {
        Store store = storeRepository.findOne(storeId);
        if (store.getPlat() != Plat.MEITUAN && store.getPlat() != Plat.CLBM) {
            throw new BizException("暂时只支持美团店铺更新");
        }
        List<Plat> plats = new ArrayList<>();
        plats.add(store.getPlat());
        SystemParam systemParam;
        if (store.getPlat() == Plat.MEITUAN) {
            systemParam = meituanWaimaiService.getSystemParam();
        } else {
            systemParam = clbmWaiMaiService.getSystemParam();
        }

        Boolean pd = true;
        while (pd) {
            try {
                List<AuditStatusParam> list = APIFactory.getNewRetailApi().retailAuditStatus(systemParam, store.getCode(), 3, 1, 200);
                if (list.size() < 200) {
                    pd = false;
                }
                for (AuditStatusParam auditStatusParam : list) {
                    try {
                        //首先更改类目信息 把总商品库的信息也改掉
                        String app_spu_code = auditStatusParam.getApp_spu_code();
                        Food food = foodRepository.findByCode(app_spu_code);
                        if (food == null) {
                            logger.error("未找到对应商品 " + auditStatusParam.toString());
                            continue;
                        }
                        if (auditStatusParam.getTag_id() != null && auditStatusParam.getTag_id() != 0 && food.getMeituanTagId() != auditStatusParam.getTag_id()) {
                            foodService.setMeituanTag(food.getId(), "自动/设置/类目", auditStatusParam.getTag_id());
                        }
                        StoreUserFood storeUserFood = storeUserFoodRepository.findByStoreUserIdAndFoodId(store.getStoreUser().getId(), food.getId());
                        if (storeUserFood.getSale()) {
                            publish(storeUserFood.getId(), true, plats);
                        } else {
                            publish(storeUserFood.getId(), true, plats);
                            Thread.sleep(500);
                            soldOut(storeUserFood, Plat.MEITUAN);
                        }
                        Thread.sleep(500);
                    } catch (Exception e) {
                        logger.info("更新类目失败" + e.getMessage(), e);
                    }


                }

                //
            } catch (ApiOpException e1) {
                logger.info("更新类目失败", e1);
            } catch (ApiSysException e1) {
                logger.info("更新类目失败：", e1);
            }
        }

    }

    @Override
    public void batchDiscount(List<Long> foodId, List<Long> stoerUserId, List<Plat> platList, String name, Float fullPrice, Float reducePrice) {
        if (stoerUserId.size() > 0) {
            try {
                List<ActItemParam> params = new ArrayList<>();
                for (Long storeUserId : stoerUserId) {
                    List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrueAndPlatIn(storeUserId, platList);
                    List<StoreUserFood> foodList = storeUserFoodRepository.findByStoreUserIdAndFoodIdIn(storeUserId, foodId);
                    for (StoreUserFood suf : foodList) {
                        if (!suf.getSale()) {
                            continue;
                        }
                        List<String> specialSkuList = suf.getSpecialSkuIdList() == null ? Collections.EMPTY_LIST : Arrays.asList(suf.getSpecialSkuIdList().split(","));
                        if (specialSkuList.size() > 1) {
                            try {
                                //多规格商品不能参加活动，要发布一个规格后再添加到活动中
                                suf.setSpecialSkuIdList(specialSkuList.get(0));
                                publish(suf.getId(), true, platList);
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }

                        }
                        ActItemParam param = new ActItemParam();
                        param.setApp_food_code(suf.getFood().getCode());
                        param.setDay_limit(-1);
                        params.add(param);
                    }
                    for (Store store : stores) {
                        SystemParam param = null;
                        Result result = new Result();
                        if (store.getPlat() == Plat.MEITUAN) {
                            param = meituanWaimaiService.getSystemParam();
                        } else if (store.getPlat() == Plat.CLBM) {
                            param = clbmWaiMaiService.getSystemParam();
                        } else {
                            continue;
                        }
                        try {
                            //首先查找店铺中是否已经做了满减活动
                            List<ActFullDiscountParam> discountList = APIFactory.getActApi().actFullDiscountList(param, store.getCode());
                            boolean pd = true;
                            if (discountList != null && discountList.size() > 0) {
                                //将商品添加到对应的活动满减中(如果名称一样)
                                for (ActFullDiscountParam actFullDiscountParam : discountList) {
                                    if (actFullDiscountParam.getAct_info().getStatus() == 1 && actFullDiscountParam.getAct_info().getAct_name().equals(name)) {
                                        String s = APIFactory.getActApi().actFullDiscountFoodsBatchSave(param, store.getCode(), actFullDiscountParam.getAct_info().getAct_ids(), params);
                                        logger.info("添加商品到指定满减返回" + s);
                                        pd = false;
                                        break;
                                    }
                                }

                            }
                            //没有做指定满减活动或者活动已经过期，创建指定满减活动
                            if (pd) {
                                ActFullDiscountItemParam itemParam = new ActFullDiscountItemParam();
                                itemParam.setAct_name(name);
                                itemParam.setStart_time((int) (System.currentTimeMillis() / 1000 + 60));
                                itemParam.setEnd_time((int) (System.currentTimeMillis() / 1000 + 31449600));
                                List<ActItemParam> list = new ArrayList<>();
                                ActItemParam actItemParam = new ActItemParam();
                                actItemParam.setOrigin_price(fullPrice.doubleValue());
                                actItemParam.setAct_price(reducePrice.doubleValue());
                                list.add(actItemParam);
                                String s = APIFactory.getActApi().actFullDiscountBatchSave(param, store.getCode(), itemParam, list, params);
                                logger.info("创建指定商品满减返回" + s);
                            }
                            Thread.sleep(800);

                        } catch (ApiOpException e) {
                            logger.info("创建指定商品满减失败" + e.getMsg(), e);
                        } catch (ApiSysException e) {
                            logger.info("创建指定商品满减失败" + e.getExceptionEnum().getMsg(), e);

                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }


                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }


        }
    }

    //批量X元M件
    @Override
    public void batchXm(List<Long> foodId, List<Long> stoerUserId, List<Plat> platList, String name, Double actPrice, Integer actNum) {
        if (stoerUserId.size() > 0) {
            try {
                for (Long storeUserId : stoerUserId) {
                    ItemBundParam bundParam = new ItemBundParam();
                    List<BundItemParam> itemParams = new ArrayList<>();
                    List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrueAndPlatIn(storeUserId, platList);
                    List<StoreUserFood> foodList = storeUserFoodRepository.findByStoreUserIdAndFoodIdIn(storeUserId, foodId);
                    for (StoreUserFood suf : foodList) {
                        if (!suf.getSale()) {
                            continue;
                        }
                        List<String> specialSkuList = suf.getSpecialSkuIdList() == null ? Collections.EMPTY_LIST : Arrays.asList(suf.getSpecialSkuIdList().split(","));
                        if (specialSkuList.size() > 1) {
                            try {
                                //多规格商品不能参加活动，要发布一个规格后再添加到活动中
                                suf.setSpecialSkuIdList(specialSkuList.get(0));
                                publish(suf.getId(), true, platList);
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }

                        }
                        BundItemParam bundItemParam = new BundItemParam();
                        bundItemParam.setApp_spu_code(suf.getFood().getCode());
                        bundItemParam.setDay_limit(-1);
                        itemParams.add(bundItemParam);

                    }
                    bundParam.setAct_name(name);
                    bundParam.setAct_num(actNum);
                    bundParam.setAct_price(actPrice);
                    bundParam.setApp_foods(itemParams);
                    bundParam.setStart_time((int) (System.currentTimeMillis() / 1000 + 60));
                    bundParam.setEnd_time((int) (System.currentTimeMillis() / 1000 + 31449600));
                    for (Store store : stores) {
                        SystemParam param = null;
                        Result result = new Result();
                        if (store.getPlat() == Plat.MEITUAN) {
                            param = meituanWaimaiService.getSystemParam();
                        } else if (store.getPlat() == Plat.CLBM) {
                            param = clbmWaiMaiService.getSystemParam();
                        } else {
                            continue;
                        }
                        try {
                            String s = APIFactory.getActRetailApi().actItemBundlesSave(param, store.getCode(), bundParam);
                            logger.info("创建X元M件返回" + s);
                            Thread.sleep(800);

                        } catch (ApiOpException e) {
                            logger.info("创建指定商品满减失败" + e.getMsg(), e);
                        } catch (ApiSysException e) {
                            logger.info("创建指定商品满减失败" + e.getExceptionEnum().getMsg(), e);

                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }


                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }


        }
    }

    //批量商品券
    @Override
    public void batchCouponDiscount(List<Long> foodId, List<Long> stoerUserId, Boolean allStore, List<Plat> platList, String name, Float fullPrice, Float reducePrice) {
        try {
            GoodsCouponParam couponParam = new GoodsCouponParam();
            List<PriceCouponInfo> couponInfos = new ArrayList<>();
            PriceCouponInfo info = new PriceCouponInfo();
            info.setFull_price(fullPrice.intValue());
            info.setReduce_price(reducePrice.intValue());
            info.setStock(9999);
            info.setUser_type(0);
            couponInfos.add(info);
            couponParam.setAct_price_coupon_info(couponInfos);

            couponParam.setCoupon_limit_count(9999);
            couponParam.setCoupon_name(name);
            couponParam.setIs_single_poi(1);
            couponParam.setTake_coupon_end_time((int) (System.currentTimeMillis() / 1000 + 2505600));
            couponParam.setTake_coupon_start_time((int) (System.currentTimeMillis() / 1000));
            couponParam.setType(1);
            couponParam.setUse_coupon_start_time((int) (System.currentTimeMillis() / 1000));
            if (stoerUserId == null && allStore) {
                stoerUserId = new ArrayList<>();
                List<StoreUser> users = storeUserRepository.findByStatusAndOpened(StoreUserStatus.VALID, true);
                for (StoreUser user : users) {
                    stoerUserId.add(user.getId());
                }
            }
            for (Long storeUserId : stoerUserId) {

                try {
                    String foods = "";
                    List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrueAndPlatIn(storeUserId, platList);
                    List<StoreUserFood> foodList = storeUserFoodRepository.findByStoreUserIdAndFoodIdIn(storeUserId, foodId);
                    for (StoreUserFood suf : foodList) {
                        if (!suf.getSale()) {
                            continue;
                        }
                        List<String> specialSkuList = suf.getSpecialSkuIdList() == null ? Collections.EMPTY_LIST : Arrays.asList(suf.getSpecialSkuIdList().split(","));
                        if (specialSkuList.size() > 1) {
                            try {
                                //多规格商品不能参加活动，要发布一个规格后再添加到活动中
                                suf.setSpecialSkuIdList(specialSkuList.get(0));
                                publish(suf.getId(), true, platList);
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }

                        }
                        foods = foods + suf.getFood().getCode() + ",";
                    }
                    couponParam.setApp_spu_codes(foods.substring(0, foods.length() - 1));
                    for (Store store : stores) {
                        SystemParam param = null;
                        Result result = new Result();
                        if (store.getPlat() == Plat.MEITUAN) {
                            param = meituanWaimaiService.getSystemParam();
                        } else if (store.getPlat() == Plat.CLBM) {
                            param = clbmWaiMaiService.getSystemParam();
                        } else {
                            continue;
                        }
                        couponParam.setApp_poi_codes(store.getCode());
                        String s = "";
                        try {
                            s = APIFactory.getActRetailApi().actGoodsCouponSave(param, couponParam);
                        } catch (ApiOpException e1) {
                            logger.info("美团创建商品券活动失败" + e1.getMsg(), e1);
                            result.setMsg(e1.getMsg());
                            result.setSuccess(false);
                        } catch (ApiSysException e1) {
                            logger.info("美团创建商品券活动失败：" + e1.getExceptionEnum().getMsg(), e1);
                            result.setMsg(e1.getExceptionEnum().getMsg());
                            result.setSuccess(false);
                        }
                        logger.info(store.getName() + "批量创建商品券活动返回" + s);

                    }
                } catch (Exception e) {
                    logger.error("商品券出错", e);
                }


            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }


    }

    @Override
    public void batchActive(List<Long> foodId, Boolean allUser, List<Long> stoerUserId, List<Plat> platList, Integer activeType, Float priceIncrease) {
        if (allUser) {
            stoerUserId = new ArrayList<>();
            List<StoreUser> users = storeUserRepository.findByStatusAndOpened(StoreUserStatus.VALID, true);
            for (StoreUser user : users) {
                stoerUserId.add(user.getId());
            }
        }
        if (stoerUserId.size() > 0) {
            try {
                for (Long storeUserId : stoerUserId) {
                    try {
                        List<StoreUserFood> foodList = storeUserFoodRepository.findByStoreUserIdAndFoodIdIn(storeUserId, foodId);
                        Map<Long, Double> map = new HashMap<>();
                        for (StoreUserFood storeUserFood : foodList) {
                            if (!storeUserFood.getSale()) {
                                continue;
                            }
                            FoodDTO foodDTO = foodService.toDTO(storeUserFood.getFood());
                            List<String> specialSkuList = StringUtils.isBlank(storeUserFood.getSpecialSkuIdList()) ? Collections.EMPTY_LIST
                                    : Arrays.asList(storeUserFood.getSpecialSkuIdList().split(","));
                            List<String> skuStringList = new ArrayList<>();
                            if (specialSkuList != null && specialSkuList.size() > 0) {
                                skuStringList.add(specialSkuList.get(0));
                            }
//                            List<FoodQuoteSku> foodQuoteSkus = getFoodQuoteSkuList(foodDTO.getSkuList(), skuStringList, storeUserFood.getSalePrice(), true);
//                            FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
//                            List<RetailSkuParam> skuList = toFoodSkuParamList(storeUserFood.getId(), foodQuoteSkus, foodSetting);
                            List<RetailSkuParam> skuList = getRetailSkuParam(storeUserFood);
                            RetailSkuParam skuParam = skuList.get(0);

                            Double price = 0.0;
                            if (storeUserFood.getActiveType() != null && storeUserFood.getActiveType() == 2) {
                                //美团上卖的规格价格
                                Double oprice = Double.valueOf(skuParam.getPrice());
                                Float skudis = Float.valueOf(skuParam.getPrice()) / storeUserFood.getSalePrice();
                                //原来的报价
                                Double ooprice = Double.valueOf(skudis * storeUserFood.getQuotePrice());
                                //策略2
                                if (ooprice <= disCountPrice2) {
                                    price = 0.1;
                                } else {
                                    BigDecimal bg = new BigDecimal(ooprice - disCountPrice2);
                                    price = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                }
                                if (oprice * 0.3 > price) {
                                    BigDecimal bg = new BigDecimal(oprice * 0.31);
                                    price = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                }

                            } else {

                                Double oprice = Double.valueOf(skuParam.getPrice());
                                if (oprice <= disCountPrice) {
                                    price = 0.1;
                                } else {
                                    BigDecimal bg = new BigDecimal(oprice - disCountPrice);
                                    price = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                }
                                if (oprice * 0.3 > price) {
                                    BigDecimal bg = new BigDecimal(oprice * 0.31);
                                    price = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                }

                            }


                            map.put(storeUserFood.getId(), price);
                        }
                        //按照发布后的价格排序进行发布
                        LinkedHashMap<Long, Double> kvMap = (LinkedHashMap<Long, Double>) StoreUserFoodhelper.sortByValue(map);
                        Set<Long> longs = kvMap.keySet();
                        List<Long> longList = new ArrayList<>(longs);
                        Collections.reverse(longList);
                        for (Long aLong : longList) {
                            try {
                                publishActive(aLong, true, platList, activeType, priceIncrease);
                                Thread.sleep(300);
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }


        }
    }

    @Override
    public StoreUserFoodDTO publishActive(long id, boolean checkExists, List<Plat> platList, Integer activeType, Float priceIncrease) {
        //查询出要发布到店铺的商品
        StoreUserFood storeUserFood = storeUserFoodRepository.findOne(id);
        storeUserFood.setActiveType(activeType);
//        if (activeType == 2) {
//            storeUserFood.setPriceIncrease(priceIncrease);
//        }
        storeUserFood.setPriceIncrease(priceIncrease);
        FoodDTO food = foodService.toDTO(storeUserFood.getFood());
        if (storeUserFood.getQuoteStatus() != QuoteStatus.VERIFY_SUCCESS) {
            throw new BizException("当前商品未审核通过，不能发布！");
        }
        platList.remove(Plat.ELE);
        platList.remove(Plat.JDDJ);
        //根据商家查询出要商家的店铺
        List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrueAndPlatIn(storeUserFood.getStoreUser().getId(), platList);
        List<String> msgList = new ArrayList<>();
        boolean success = true;
        for (Store store : stores) {
            //判断店铺是否营业
            if (!store.getOpening()) {
                continue;
            }
            Result rs;
            try {
                rs = publishtActivityToMeiTuan(storeUserFood, food, store, store.getPlat());
            } catch (Exception e) {
                logger.error("发布活动失败", e);
                rs = new Result();
                //收集发布失败的原因
                rs.setMsg(e.getLocalizedMessage());
                //设置发布失败状态
                if (store.getPlat() == Plat.MEITUAN) {
                    storeUserFood.setMeituanPublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.CLBM) {
                    storeUserFood.setClbmPublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.ELE) {
                    storeUserFood.setElePublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.WANTE) {
                    storeUserFood.setWantePublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.JDDJ) {
                    storeUserFood.setJddjPublishStatus(PublishStatus.FAIL);
                }
            }
            if (!rs.isSuccess()) {
                success = false;
                msgList.add("发布到:" + store.getPlat().getTitle() + " " + store.getName() + "失败，原因：" + rs.getMsg() + " - " + rs.getErrMsg());
                if (store.getPlat() == Plat.MEITUAN) {
                    storeUserFood.setMeituanPublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.CLBM) {
                    storeUserFood.setClbmPublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.ELE) {
                    storeUserFood.setElePublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.WANTE) {
                    storeUserFood.setWantePublishStatus(PublishStatus.FAIL);
                } else if (store.getPlat() == Plat.JDDJ) {
                    storeUserFood.setJddjPublishStatus(PublishStatus.FAIL);
                }

            }
        }
        storeUserFood.setPublishMsg(String.join("\n", msgList));
        storeUserFood.setQuotePrice(storeUserFood.getAlterQuotePrice() == null ? storeUserFood.getQuotePrice() : storeUserFood.getAlterQuotePrice());
        if (success) {
            storeUserFood.setSale(true);
            storeUserFood.setFoodVersion(storeUserFood.getFood().getVersion());
        }
        if (storeUserFood.getElePublishStatus() == PublishStatus.SUCCESS
                || storeUserFood.getMeituanPublishStatus() == PublishStatus.SUCCESS || storeUserFood.getClbmPublishStatus() == PublishStatus.SUCCESS || storeUserFood.getWantePublishStatus() == PublishStatus.SUCCESS || storeUserFood.getJddjPublishStatus() == PublishStatus.SUCCESS) {
            storeUserFood.setSale(true);
        } else {
            storeUserFood.setSale(false);
        }
        storeUserFood.setCityId(storeUserFood.getStoreUser().getCity().getId());
        storeUserFoodRepository.save(storeUserFood);
        return toDTO(storeUserFood);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchResetStatus(long storeUserId, List<Plat> platList) {
        StoreUserDTO byId = storeUserService.findById(storeUserId);
        if (!byId.getEleOpened()) {
            platList.remove(Plat.ELE);
        }
        if (!byId.getMeituanOpened()) {
            platList.remove(Plat.MEITUAN);
        }
        if (!byId.getWanteOpened()) {
            platList.remove(Plat.WANTE);
        }
        if (!byId.getJddjOpened()) {
            platList.remove(Plat.JDDJ);
        }
        if (!byId.getClbmOpened()) {
            platList.remove(Plat.CLBM);
        }
        if (platList.size() > 0) {
            platList.forEach(plat -> {
                switch (plat) {
                    case CLBM:
                        storeUserFoodRepository.updateClbmPublishInstockByStoreUserId(storeUserId, PublishStatus.IN_STOCK.name());
                        break;
                    case JDDJ:
                        storeUserFoodRepository.updateJddjPublishInstockByStoreUserId(storeUserId, PublishStatus.IN_STOCK.name());
                        return;
                    case ELE:
                        storeUserFoodRepository.updateElePublishInstockByStoreUserId(storeUserId, PublishStatus.IN_STOCK.name());
                        return;
                    case WANTE:
                        return;
                    case MEITUAN:
                        storeUserFoodRepository.updateMeituanPublishInstockByStoreUserId(storeUserId, PublishStatus.IN_STOCK.name());
                        return;
                }
            });
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetStoreUserPublish(long storeUserId, List<Plat> platList) {
        StoreUserDTO byId = storeUserService.findById(storeUserId);
        if (!byId.getEleOpened()) {
            platList.remove(Plat.ELE);
        }
        if (!byId.getMeituanOpened()) {
            platList.remove(Plat.MEITUAN);
        }
        if (!byId.getWanteOpened()) {
            platList.remove(Plat.WANTE);
        }
        if (!byId.getJddjOpened()) {
            platList.remove(Plat.JDDJ);
        }
        if (!byId.getClbmOpened()) {
            platList.remove(Plat.CLBM);
        }
        if (platList.size() > 0) {
            platList.forEach(plat -> {
                switch (plat) {
                    case CLBM:
                        storeUserFoodRepository.updateClbmPublishStatusByStoreUserId(storeUserId, PublishStatus.WAIT.name());
                        break;
                    case JDDJ:
                        storeUserFoodRepository.updateJddjPublishStatusByStoreUserId(storeUserId, PublishStatus.WAIT.name());
                        return;
                    case ELE:
                        storeUserFoodRepository.updateElePublishStatusByStoreUserId(storeUserId, PublishStatus.WAIT.name());
                        return;
                    case WANTE:
                        storeUserFoodRepository.updateWantePublishStatusByStoreUserId(storeUserId, PublishStatus.WAIT.name());
                        return;
                    case MEITUAN:
                        storeUserFoodRepository.updateMeituanPublishStatusByStoreUserId(storeUserId, PublishStatus.WAIT.name());
                        return;
                }
            });
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetStoreUserFoodPublish(long storeUserId, List<Long> foodIdList, List<Plat> platList) {
        StoreUserDTO byId = storeUserService.findById(storeUserId);
        if (!byId.getEleOpened()) {
            platList.remove(Plat.ELE);
        }
        if (!byId.getMeituanOpened()) {
            platList.remove(Plat.MEITUAN);
        }
        if (!byId.getClbmOpened()) {
            platList.remove(Plat.CLBM);
        }
        if (!byId.getWanteOpened()) {
            platList.remove(Plat.WANTE);
        }
        if (!byId.getJddjOpened()) {
            platList.remove(Plat.JDDJ);
        }
        if (platList.size() > 0) {
            platList.forEach(plat -> {
                switch (plat) {
                    case CLBM:
                        storeUserFoodRepository.updateClbmPublishStatusByStoreUserIdAndFoodIdList(storeUserId, foodIdList, PublishStatus.WAIT.name());
                        return;
                    case JDDJ:
                        storeUserFoodRepository.updateJddjPublishStatusByStoreUserIdAndFoodIdList(storeUserId, foodIdList, PublishStatus.WAIT.name());
                        return;
                    case ELE:
                        storeUserFoodRepository.updateElePublishStatusByStoreUserIdAndFoodIdList(storeUserId, foodIdList, PublishStatus.WAIT.name());
                        return;
                    case WANTE:
                        storeUserFoodRepository.updateWantePublishStatusByStoreUserIdAndFoodIdList(storeUserId, foodIdList, PublishStatus.WAIT.name());
                        return;
                    case MEITUAN:
                        storeUserFoodRepository.updateMeituanPublishStatusByStoreUserIdAndFoodIdList(storeUserId, foodIdList, PublishStatus.WAIT.name());
                        return;
                }
            });
        }

    }

    private Float checkIncrease(Food food, StoreUser su) {
        float increase;
        if (food.getPerIncrease() > 0) {
            increase = food.getPerIncrease();
        } else {
            if (su.getPriceIncrease() > 0) {
                increase = su.getPriceIncrease();
            } else {
                FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
                increase = foodSetting.getPerIncrease();
            }
        }
        return increase;
    }

    private List<StoreUserFoodSku> getPublishSkus(StoreUserFood storeUserFood) {
        List<StoreUserFoodSku> list = new ArrayList<>();
        if (StringUtils.isNotBlank(storeUserFood.getSpecialSkuIdList())) {
            List<StoreUserFoodSku> skuList = storeUserFoodSkuRepository.findByStoreUserFoodId(storeUserFood.getId());
            for (StoreUserFoodSku storeUserFoodSku : skuList) {
                if (storeUserFood.getSpecialSkuIdList().indexOf(storeUserFoodSku.getFoodSkuId().toString()) != -1) {
                    list.add(storeUserFoodSku);
                }
            }
        }
        return list;
    }

    private StoreUserFoodSku getEleSku(StoreUserFood storeUserFood) {
        List<StoreUserFoodSku> skuList = storeUserFoodSkuRepository.findByStoreUserFoodId(storeUserFood.getId());
        if (StringUtils.isNotBlank(storeUserFood.getEleSkuId())) {
            for (StoreUserFoodSku storeUserFoodSku : skuList) {
                if (storeUserFoodSku.getFoodSkuId().toString().equals(storeUserFood.getEleSkuId())) {
                    return storeUserFoodSku;
                }
            }
        } else {
            for (StoreUserFoodSku storeUserFoodSku : skuList) {
                if (storeUserFood.getSpecialSkuIdList().indexOf(storeUserFoodSku.getFoodSkuId().toString()) != -1) {
                    return storeUserFoodSku;
                }
            }
        }
        return null;
    }


//        private List<FoodQuoteSku> getFoodQuoteSkuList(List<FoodSku> foodSkuList, List<String> specialSkuList,
//                                                   float salePrice, boolean filterIgnore) {
//        List<FoodQuoteSku> foodQuoteSkus = new ArrayList<>();
//        for (FoodSku foodSku : foodSkuList) {
//            if (specialSkuList.size() > 0) {
//                if (specialSkuList.indexOf(foodSku.getSkuId()) == -1) {
//                    continue;
//                }
//            } else {
//                if (foodSku.isIgnore() && filterIgnore) {
//                    continue;
//                }
//            }
//
//            FoodQuoteSku sku = new FoodQuoteSku();
//            sku.setPrice(Math.round(salePrice * foodSku.getPriceRatio() * 100) / 100f);
//            sku.setSkuId(foodSku.getSkuId());
//            sku.setSpec(foodSku.getSpec());
//            sku.setIgnore(foodSku.isIgnore());
//            sku.setWeight(foodSku.getWeight() == null ? 0 : foodSku.getWeight());
//            foodQuoteSkus.add(sku);
//        }
//        return foodQuoteSkus;
//    }

//    private List<FoodQuoteSku> getFoodQuoteSkuList(List<FoodSku> foodSkuList, List<String> specialSkuList,
//                                                   float salePrice, boolean filterIgnore) {
//        List<FoodQuoteSku> foodQuoteSkus = new ArrayList<>();
//        for (FoodSku foodSku : foodSkuList) {
//            if (specialSkuList.size() > 0) {
//                if (specialSkuList.indexOf(foodSku.getSkuId()) == -1) {
//                    continue;
//                }
//            } else {
//                if (foodSku.isIgnore() && filterIgnore) {
//                    continue;
//                }
//            }
//
//            FoodQuoteSku sku = new FoodQuoteSku();
//            sku.setPrice(Math.round(salePrice * foodSku.getPriceRatio() * 100) / 100f);
//            sku.setSkuId(foodSku.getSkuId());
//            sku.setSpec(foodSku.getSpec());
//            sku.setIgnore(foodSku.isIgnore());
//            sku.setWeight(foodSku.getWeight() == null ? 0 : foodSku.getWeight());
//            foodQuoteSkus.add(sku);
//        }
//        return foodQuoteSkus;
//    }

    private Result publishToStore(StoreUserFood storeUserFood, Store store, boolean checkExists) {
        Result rs = new Result();
        StoreUser storeUser = storeUserFood.getStoreUser();
        FoodDTO food = foodService.toDTO(storeUserFood.getFood());
        if (food.getDeleted()) {
            String msg = "商品" + food.getName() + "已经被删除，忽略发布！";
            logger.info(msg);
            rs.setMsg(msg);
            return rs;
        }
        if (storeUserFood.getQuotePrice() >= 60) {
            if (storeUserFood.getQuotePrice() < 80) {
                storeUserFood.setPriceIncrease(35f);
            } else if (storeUserFood.getQuotePrice() >= 80) {
                storeUserFood.setPriceIncrease(30f);
            }
        }
        if ((store.getPlat() == Plat.MEITUAN && storeUser.getMeituanOpened()) || (store.getPlat() == Plat.CLBM && storeUser.getClbmOpened())) {
            List<String> specialSkuList = StringUtils.isBlank(storeUserFood.getSpecialSkuIdList()) ? Collections.EMPTY_LIST
                    : Arrays.asList(storeUserFood.getSpecialSkuIdList().split(","));
//            List<FoodQuoteSku> foodQuoteSkus = getFoodQuoteSkuList(food.getSkuList(), specialSkuList, storeUserFood.getSalePrice(), true);
//            Map<String, FoodQuoteSku> foodQuoteSkuMapBySkuId = new HashMap<>(5);
//            Map<String, FoodQuoteSku> foodQuoteSkuMapBySpec = new HashMap<>(5);
//            for (FoodQuoteSku quoteSkus : foodQuoteSkus) {
//                foodQuoteSkuMapBySkuId.put(quoteSkus.getSkuId(), quoteSkus);
//                foodQuoteSkuMapBySpec.put(quoteSkus.getSpec(), quoteSkus);
//            }
            rs = this.publishToMeituan(storeUserFood, store, checkExists, store.getPlat());
            if (rs.isSuccess()) {
                if (store.getPlat() == Plat.MEITUAN) {
                    storeUserFood.setMeituanPublishStatus(PublishStatus.SUCCESS);
                } else {
                    storeUserFood.setClbmPublishStatus(PublishStatus.SUCCESS);
                }

            } else {
                if (store.getPlat() == Plat.MEITUAN) {
                    storeUserFood.setMeituanPublishStatus(PublishStatus.FAIL);
                } else {
                    storeUserFood.setClbmPublishStatus(PublishStatus.FAIL);
                }

            }

        } else if (store.getPlat() == Plat.ELE && storeUser.getEleOpened()) {
            //查询出商品的sku集合foodQuoteSkus
            //List<FoodQuoteSku> foodQuoteSkus = getFoodQuoteSkuList(food.getSkuList(), new ArrayList<>(), storeUserFood.getSalePrice(), false);
            //以skuid为键的map
//            Map<String, FoodQuoteSku> foodQuoteSkuMapBySkuId = new HashMap<>(5);
//            //以spec规格为键的map
//            Map<String, FoodQuoteSku> foodQuoteSkuMapBySpec = new HashMap<>(5);
//            for (FoodQuoteSku quoteSkus : foodQuoteSkus) {
//                foodQuoteSkuMapBySkuId.put(quoteSkus.getSkuId(), quoteSkus);
//                foodQuoteSkuMapBySpec.put(quoteSkus.getSpec(), quoteSkus);
//            }
            //rs = this.publishToEle(storeUserFood, food, store, foodQuoteSkus, foodQuoteSkuMapBySkuId, checkExists);
            rs = this.publishToEle(storeUserFood, store, checkExists);
            if (rs.isSuccess()) {
                storeUserFood.setElePublishStatus(PublishStatus.SUCCESS);
            } else {
                storeUserFood.setElePublishStatus(PublishStatus.FAIL);
            }
        } else if (store.getPlat() == Plat.WANTE && storeUser.getWanteOpened()) {
//            List<FoodQuoteSku> foodQuoteSkus = getFoodQuoteSkuList(food.getSkuList(), new ArrayList<>(), storeUserFood.getSalePrice(), false);
//            Map<String, FoodQuoteSku> foodQuoteSkuMapBySkuId = new HashMap<>(5);
//            Map<String, FoodQuoteSku> foodQuoteSkuMapBySpec = new HashMap<>(5);
//            for (FoodQuoteSku quoteSkus : foodQuoteSkus) {
//                foodQuoteSkuMapBySkuId.put(quoteSkus.getSkuId(), quoteSkus);
//                foodQuoteSkuMapBySpec.put(quoteSkus.getSpec(), quoteSkus);
//            }
//            rs = this.publishToWante(storeUserFood, food, store, foodQuoteSkus, foodQuoteSkuMapBySkuId);
//            if (rs.isSuccess()) {
//                storeUserFood.setWantePublishStatus(PublishStatus.SUCCESS);
//            } else {
//                storeUserFood.setWantePublishStatus(PublishStatus.FAIL);
//            }
        } else if (store.getPlat() == Plat.JDDJ && storeUser.getJddjOpened()) {
//            List<FoodQuoteSku> foodQuoteSkus = getFoodQuoteSkuList(food.getSkuList(), new ArrayList<>(), storeUserFood.getSalePrice(), false);
//            Map<String, FoodQuoteSku> foodQuoteSkuMapBySkuId = new HashMap<>(5);
//            Map<String, FoodQuoteSku> foodQuoteSkuMapBySpec = new HashMap<>(5);
//            for (FoodQuoteSku quoteSkus : foodQuoteSkus) {
//                foodQuoteSkuMapBySkuId.put(quoteSkus.getSkuId(), quoteSkus);
//                foodQuoteSkuMapBySpec.put(quoteSkus.getSpec(), quoteSkus);
//            }
//            rs = this.publishToJddj(storeUserFood, food, store, foodQuoteSkus, foodQuoteSkuMapBySkuId);
//            if (rs.isSuccess()) {
//                storeUserFood.setJddjPublishStatus(PublishStatus.SUCCESS);
//            } else {
//                storeUserFood.setJddjPublishStatus(PublishStatus.FAIL);
//            }
        }

        if (rs.isSuccess()) {

        }
        return rs;
    }


    private Result publishToJddj(StoreUserFood storeUserFood, FoodDTO foodDto, Store store,
                                 List<FoodQuoteSku> foodQuoteSkus,
                                 Map<String, FoodQuoteSku> foodQuoteSkuMapBySkuId) {
        Result rs = new Result();
        FoodQuoteSku foodQuoteSku = null;
        String eleSkuId = storeUserFood.getEleSkuId();//默认使用和饿了么的同样的规格
        if (StringUtils.isNotBlank(eleSkuId)) {
            foodQuoteSku = foodQuoteSkuMapBySkuId.get(eleSkuId);
        }
        if (foodQuoteSku == null) {
            foodQuoteSkus.sort((f1, f2) -> Float.valueOf(f1.getPrice() - f2.getPrice()).intValue());
            for (FoodQuoteSku sku : foodQuoteSkus) {
                if (sku.isIgnore()) {
                    continue;
                }
                foodQuoteSku = sku;
                break;
            }
            if (foodQuoteSku == null) {
                throw new RuntimeException("没有可发布的规格");
            }
        }
        String customSkuId = foodQuoteSku.getSkuId();
        storeUserFood.setEleSkuId(customSkuId);
        Long foodId = foodDto.getId();
        String publishId = null;
        Food food = storeUserFood.getFood();
        Map<String, String> jddjSkuMap = food.getJddjSkuMap();
//        for (int i = 1; i <= foodDto.getSkuList().size(); i++) {
//            String sku = null;
//            String publish = null;
//            if (i < 10) {
//                publish = foodId + "0" + i;
//                sku = jddjSkuMap.get(publish);
//            } else {
//                publish = foodId + "" + i;
//                sku = jddjSkuMap.get(publish);
//            }
//
//            if (sku == null) {
//                jddjSkuMap.put(publish, customSkuId);
//                publishId = publish;
//                break;
//            }
//            if (customSkuId.equals(sku)) {
//                publishId = publish;
//                break;
//            }
//
//        }
        if (publishId == null) {
            logger.error("设置京东商品规格出错");
            rs.setSuccess(false);
            return rs;
        }
        food.setJddjSkuMap(jddjSkuMap);
        foodRepository.save(food);
        //先查询该商品在京东到家上有没有发布过
        GetSkuStatusReq req = new GetSkuStatusReq();
        req.setOutSkuId(publishId);
        req.setTraceId(UUID.randomUUID().toString());
        GetSkuStatusRes res;
        if (store.getJd()) {
            res = jingdongClient.request(req);
        } else {
            res = jingdongzClient.request(req);
        }
        Float price = foodQuoteSku.getPrice();
        price *= 100;
        FoodCategory foodCategory = foodCategoryRepository.findByName(foodDto.getCategoryName());
        Long brandId = 210660l;
        GetBrandReq getBrandReq = new GetBrandReq();
        getBrandReq.setPageNo(1);
        getBrandReq.setPageSize(50);
        List<String> list = new ArrayList<>();
        list.add("BRAND_ID");
        list.add("BRAND_NAME");
        list.add("BRAND_STATUS");
        getBrandReq.setBrandName("菜");
        getBrandReq.setFields(list);
        GetBrandRes res1;
        if (store.getJd()) {
            res1 = jingdongClient.request(getBrandReq);
        } else {
            res1 = jingdongzClient.request(getBrandReq);
        }
        if (res1.getData() != null && res1.getData().getResult() != null && res1.getData().getResult().getResult() != null) {
            if (res1.getData().getResult().getResult().size() > 0) {
                List<GetBrandRes.BrandInfo> result = res1.getData().getResult().getResult();
                for (GetBrandRes.BrandInfo brandInfo : result) {
                    if (brandInfo.getBrandStatus() == 2) {
                        brandId = brandInfo.getId();
                        break;
                    }
                }
            }
        }
        if ("0".equals(res.getCode()) && "0".equals(res.getData().getCode())) {
            if (res.getData().getResult().getSkuId() == null) {
                //在京东商品库中不存在，进行发布操作
                logger.info("发布商品到京东" + food.getName());
                AddSkuReq addSkuReq = new AddSkuReq();
                addSkuReq.setTraceId(UUID.randomUUID().toString());
                addSkuReq.setOutSkuId(publishId);
                if (store.getJd()) {
                    addSkuReq.setShopCategories(String.valueOf(foodCategory.getJddjId()));
                } else {
                    addSkuReq.setShopCategories(String.valueOf(foodCategory.getJddjzId()));
                }
                addSkuReq.setCategoryId(foodCategory.getJingdongTagId() == null ? 20356l : foodCategory.getJingdongTagId());
                addSkuReq.setBrandId(brandId);
                addSkuReq.setSkuName(foodDto.getName() + " " + foodQuoteSku.getSpec());
                addSkuReq.setSkuPrice(price.longValue());
                addSkuReq.setWeight(Float.valueOf(foodQuoteSku.getWeight()) / 1000);
                List<String> image = new ArrayList<>();
                if (StringUtils.isNotBlank(food.getJdPicture())) {
                    image.add(food.getJdPicture());
                } else {
                    String jdPicture = food.getPicture() + "?x-oss-process=image/resize,m_fixed,w_800,h_800,limit_0";
                    food.setJdPicture(jdPicture);
                    foodRepository.save(food);
                    image.add(jdPicture);
                }
                addSkuReq.setImages(image);
                addSkuReq.setFixedStatus(1);
                addSkuReq.setIsSale(false);//总商品库中的商品为不可售状态
                AddSkuRes addSkuRes;
                if (store.getJd()) {
                    addSkuRes = jingdongClient.request(addSkuReq);
                } else {
                    addSkuRes = jingdongzClient.request(addSkuReq);
                }
            } else {
                //商品已经存在，更新操作
                logger.info("更新商品到京东" + food.getName());
                UpdateSkuReq skuReq = new UpdateSkuReq();
                skuReq.setTraceId(UUID.randomUUID().toString());
                skuReq.setOutSkuId(publishId);
                if (store.getJd()) {
                    skuReq.setShopCategories(String.valueOf(foodCategory.getJddjId()));
                } else {
                    skuReq.setShopCategories(String.valueOf(foodCategory.getJddjzId()));
                }
                skuReq.setCategoryId(foodCategory.getJingdongTagId() == null ? 20356l : foodCategory.getJingdongTagId());
                skuReq.setBrandId(brandId);
                skuReq.setSkuName(foodDto.getName() + " " + foodQuoteSku.getSpec());
                skuReq.setSkuPrice(price.longValue());
                skuReq.setWeight(Float.valueOf(foodQuoteSku.getWeight()) / 1000);
                if (StringUtils.isBlank(food.getJdPicture())) {
                    List<String> image = new ArrayList<>();
                    String jdPicture = food.getPicture() + "?x-oss-process=image/resize,m_fixed,w_800,h_800,limit_0";
                    food.setJdPicture(jdPicture);
                    foodRepository.save(food);
                    image.add(jdPicture);
                    skuReq.setImages(image);
                }
                skuReq.setFixedStatus(1);
                skuReq.setIsSale(false);
                UpdateSkuRes skuRes;
                if (store.getJd()) {
                    skuRes = jingdongClient.request(skuReq);
                } else {
                    skuRes = jingdongzClient.request(skuReq);
                }
//                if (skuRes.getData().getOpenPmsNSkuResult() == null && skuRes.getData().getResult() != null) {
//                    String failedDetail = skuRes.getData().getResult().getFailedDetail();
//                    if(failedDetail != null){
//                        String[] split = failedDetail.split("，");
//                        String s = split[1];
//                        String substring = s.substring(s.indexOf("：") + 1, s.length());
//                        brandId = Long.valueOf(substring);
//
//
//                        UpdateSkuReq newReq = new UpdateSkuReq();
//                        newReq.setTraceId(UUID.randomUUID().toString());
//                        newReq.setOutSkuId(publishId);
//                        if (store.getJd()) {
//                            newReq.setShopCategories(String.valueOf(foodCategory.getJddjId()));
//                        } else {
//                            newReq.setShopCategories(String.valueOf(foodCategory.getJddjzId()));
//                        }
//                        newReq.setCategoryId(foodCategory.getJingdongTagId() == null ? 20356l : foodCategory.getJingdongTagId());
//                        newReq.setBrandId(brandId);
//                        newReq.setSkuName(foodDto.getName() + " " + foodQuoteSku.getSpec());
//                        newReq.setSkuPrice(price.longValue());
//                        newReq.setWeight(Float.valueOf(foodQuoteSku.getWeight()) / 1000);
//                        if (StringUtils.isBlank(food.getJdPicture())) {
//                            List<String> image = new ArrayList<>();
//                            String jdPicture = food.getPicture() + "?x-oss-process=image/resize,m_fixed,w_800,h_800,limit_0";
//                            food.setJdPicture(jdPicture);
//                            foodRepository.save(food);
//                            image.add(jdPicture);
//                            newReq.setImages(image);
//                        }
//                        newReq.setFixedStatus(1);
//                        newReq.setIsSale(false);
//                        UpdateSkuRes newRes;
//                        if (store.getJd()) {
//                            newRes = jingdongClient.request(newReq);
//                        } else {
//                            newRes = jingdongzClient.request(newReq);
//                        }
//
//                        System.out.println(newRes);
//                    }
//
//                }
            }
        } else {
            rs.setSuccess(false);
            return rs;
        }
        //将该商品的其他规格下架
        Set<String> skus = jddjSkuMap.keySet();
        for (String sku : skus) {
            UpdateSaleReq tsaleReq = new UpdateSaleReq();
            tsaleReq.setOutStationId(store.getCode());
            tsaleReq.setUserPin("王小菜");
            tsaleReq.setOutSkuId(sku);
            tsaleReq.setDoSale(false);
            try {
                if (store.getJd()) {
                    jingdongClient.request(tsaleReq);
                } else {
                    jingdongzClient.request(tsaleReq);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        //进行门店商品的更新操作
        //更改价格
        UpdateStationPriceReq priceReq = new UpdateStationPriceReq();
        priceReq.setStationNo(store.getCode());
        priceReq.setOutSkuId(publishId);
        priceReq.setPrice(price.longValue());
        priceReq.setServiceNo(UUID.randomUUID().toString());
        UpdateStationPriceRes priceRes;
        if (store.getJd()) {
            priceRes = jingdongClient.request(priceReq);
        } else {
            priceRes = jingdongzClient.request(priceReq);
        }
        //更改库存和可售状态
        StockUpdateReq stockUpdateReq = new StockUpdateReq();
        stockUpdateReq.setCurrentQty(9999);
        stockUpdateReq.setSkuId(Long.valueOf(publishId));
        stockUpdateReq.setStationNo(store.getCode());
        if (store.getJd()) {
            jingdongClient.request(stockUpdateReq);
        } else {
            jingdongzClient.request(stockUpdateReq);
        }
        UpdateSaleReq saleReq = new UpdateSaleReq();
        saleReq.setOutStationId(store.getCode());
        saleReq.setUserPin("王小菜");
        saleReq.setOutSkuId(publishId);
        saleReq.setDoSale(true);
        UpdateSaleRes saleRes;
        if (store.getJd()) {
            saleRes = jingdongClient.request(saleReq);
        } else {
            saleRes = jingdongzClient.request(saleReq);
        }
        if (priceRes.getData().getCode().equals("0") && saleRes.getData().getCode().equals("0")) {
            rs.setSuccess(true);
        } else {
            rs.setSuccess(false);
            rs.setMsg(priceRes.getData().getMsg() + saleRes.getData().getCode());
        }
        return rs;
    }

    private Result publishToWante(StoreUserFood storeUserFood, FoodDTO food, Store store,
                                  List<FoodQuoteSku> foodQuoteSkus,
                                  Map<String, FoodQuoteSku> foodQuoteSkuMapBySkuId) {
        Result rs = new Result();
        FoodQuoteSku foodQuoteSku = null;
        String eleSkuId = storeUserFood.getEleSkuId();//客户端只能发布一个规格，默认使用和饿了么的同样的规格
        if (StringUtils.isNotBlank(eleSkuId)) {
            foodQuoteSku = foodQuoteSkuMapBySkuId.get(eleSkuId);
        }
        if (foodQuoteSku == null) {
            foodQuoteSkus.sort((f1, f2) -> Float.valueOf(f1.getPrice() - f2.getPrice()).intValue());
            for (FoodQuoteSku sku : foodQuoteSkus) {
                if (sku.isIgnore()) {
                    continue;
                }
                foodQuoteSku = sku;
                break;
            }
            if (foodQuoteSku == null) {
                throw new RuntimeException("没有可发布的规格");
            }
        }
        String customSkuId = foodQuoteSku.getSkuId();
        storeUserFood.setEleSkuId(customSkuId);
        if (storeUserFood.getWanteId() != null) {
            //先判断店铺中是否有改商品，有可能在店铺中被删掉了
            ProductGetReq productGetReq = new ProductGetReq();
            productGetReq.setId(storeUserFood.getWanteId().intValue());
            Map<Integer, String> wanteSkuMap = storeUserFood.getWanteSkuMap();
            ProductGetRes execute = wanteClient.execute(productGetReq);
            if (execute != null && execute.getCode() == 0) {
                //说明商品存在，更新商品信息
                ProductUpdateReq puReq = new ProductUpdateReq();
                ProductUpdateReq.UpdateInfo updateInfo = new ProductUpdateReq.UpdateInfo();
                updateInfo.setId(storeUserFood.getWanteId().intValue());
                updateInfo.setBrandId(127);//可以更改的品牌id
                updateInfo.setProductName(food.getName() + " " + foodQuoteSku.getSpec());
                updateInfo.setEnable(1);
                FoodCategory fc = foodCategoryRepository.findByName(food.getCategoryName());
                updateInfo.setProductCategoryId(fc.getWanteId());
                updateInfo.setProductType(0);
                updateInfo.setProductContent(food.getName());
                updateInfo.setOriginalPrice(foodQuoteSku.getPrice());
                updateInfo.setSellingPrice(foodQuoteSku.getPrice());
                updateInfo.setCostPrice(foodQuoteSku.getPrice());
                updateInfo.setStatus(0);
                updateInfo.setStoreId(Integer.valueOf(store.getCode()));
                updateInfo.setProductStock(9999);
                List<String> images = new ArrayList<>();
                images.add(food.getPicture());
                updateInfo.setImages(images);
                updateInfo.setWeight(String.valueOf(foodQuoteSku.getWeight()));
                updateInfo.setSpecDelete(false);
                puReq.setUpdateInfo(updateInfo);
                ProductUpdateRes puRes = wanteClient.execute(puReq);
                if (puRes.getCode() != null && puRes.getCode() == 0) {
                    //rs.setSuccess(true);
                    logger.info("更新商品到客户端成功" + food.getName());
                    //return rs;
                } else {
                    logger.error("更新商品到客户端失败" + puRes.getMessage());
                    rs.setMsg(puRes.getMessage() + " " + food.getName());
                    rs.setSuccess(false);
                    return rs;
                }
                ProductGetRes.ProductInfo data = execute.getData();
                List<ProductGetRes.ProductStandard> productStandards = data.getProductStandards();
                Boolean pd = true;
                if (productStandards.size() > 0) {
                    ProductGetRes.ProductStandard productStandard = productStandards.get(0);
                    //比较规格是否一致,一致则更新，如果不一致或者没有，删除原来的规格再重新创建
                    Integer id = productStandard.getId();
                    if (wanteSkuMap.containsKey(id)) {
                        //比较规格是否一致
                        String wanteSku = wanteSkuMap.get(id);
                        if (customSkuId.equals(wanteSku)) {
                            //一致则更新
                            pd = false;
                            ProductSkuUpdateReq skuUpdateReq = new ProductSkuUpdateReq();
                            skuUpdateReq.setProductStandardGroupId(117);
                            skuUpdateReq.setProductId(storeUserFood.getWanteId().intValue());
                            skuUpdateReq.setName(foodQuoteSku.getSpec());
                            skuUpdateReq.setId(id);
                            ProductSkuUpdateRes skuUpdateRes = wanteClient.execute(skuUpdateReq);
                            if (skuUpdateRes.getCode() != null && skuUpdateRes.getCode() == 0) {
                                logger.info("客户端规格更新成功" + foodQuoteSku.getSpec());
                                rs.setSuccess(true);
                                return rs;
                            } else {
                                logger.error("客户端更新规格失败" + skuUpdateRes.getMessage());
                                rs.setSuccess(false);
                                rs.setMsg(skuUpdateRes.getMessage());
                            }
                        } else {
                            //不一致要删除原来的规格
                            ProductSkuDeleteReq sdReq = new ProductSkuDeleteReq();
                            sdReq.setId(id);
                            ProductSkuDeleteRes sdRes = wanteClient.execute(sdReq);
                        }
                    } else {
                        //不一致要删除原来的规格
                        ProductSkuDeleteReq sdReq = new ProductSkuDeleteReq();
                        sdReq.setId(id);
                        ProductSkuDeleteRes sdRes = wanteClient.execute(sdReq);
                    }
                }
                //如果没有要发布的规格或者规格不一致,发布新的规格
                if (pd) {
                    ProductSkuSaveReq skuReq = new ProductSkuSaveReq();
                    skuReq.setName(foodQuoteSku.getSpec());
                    skuReq.setProductId(storeUserFood.getWanteId().intValue());
                    skuReq.setProductStandardGroupId(117);//重量规格组分类id
                    ProductSkuSaveRes skuRes = wanteClient.execute(skuReq);
                    if (skuRes.getCode() != null && skuRes.getCode() == 0) {
                        logger.info("创建规格到客户端成功" + foodQuoteSku.getSpec());
                        Integer skuid = skuRes.getData().getId();
                        wanteSkuMap.put(skuid, customSkuId);
                        storeUserFood.setWanteSkuMap(wanteSkuMap);
                        storeUserFoodRepository.save(storeUserFood);
                        rs.setSuccess(true);
                        return rs;
                    } else {
                        logger.error("创建规格到客户端失败" + skuRes.getMessage());
                        rs.setSuccess(false);
                        rs.setMsg(skuRes.getMessage());
                    }

                }
            }
        }
        //客户端没有此商品，进行商品的新增
        ProductSaveReq req = new ProductSaveReq();
        ProductSaveReq.Product product = new ProductSaveReq.Product();
        product.setBrandId(127);//可以更改的品牌id
        product.setProductName(food.getName() + " " + foodQuoteSku.getSpec());
        product.setEnable(1);
        FoodCategory fc = foodCategoryRepository.findByName(food.getCategoryName());
        product.setProductCategoryId(fc.getWanteId());
        product.setProductType(0);
        product.setProductContent(food.getName());
        product.setOriginalPrice(foodQuoteSku.getPrice());
        product.setSellingPrice(foodQuoteSku.getPrice());
        product.setCostPrice(foodQuoteSku.getPrice());
        product.setStatus(0);
        product.setStoreId(Integer.valueOf(store.getCode()));
        product.setProductStock(9999);
        List<String> images = new ArrayList<>();
        images.add(food.getPicture());
        product.setImages(images);
        product.setWeight(String.valueOf(foodQuoteSku.getWeight()));
        req.setProduct(product);
        ProductSaveRes execute = wanteClient.execute(req);
        if (execute.getCode() != null && execute.getCode() == 0) {
            //将返回的id保存并给商品创建规格
            storeUserFood.setWanteId(Long.valueOf(execute.getData()));
            logger.info("发布商品到客户端成功" + food.getName() + storeUserFood.getWanteId());
            ProductSkuSaveReq skuReq = new ProductSkuSaveReq();
            skuReq.setName(foodQuoteSku.getSpec());
            skuReq.setProductId(storeUserFood.getWanteId().intValue());
            skuReq.setProductStandardGroupId(117);//重量规格组分类id
            ProductSkuSaveRes skuRes = wanteClient.execute(skuReq);
            if (skuRes.getCode() != null && skuRes.getCode() == 0) {
                logger.info("创建规格到客户端成功" + foodQuoteSku.getSpec());
                Integer skuid = skuRes.getData().getId();
                Map<Integer, String> wanteSkuMap = storeUserFood.getWanteSkuMap();
                wanteSkuMap.put(skuid, customSkuId);
                storeUserFood.setWanteSkuMap(wanteSkuMap);
            } else {
                logger.error("创建规格到客户端失败" + skuRes.getMessage());
            }
            storeUserFoodRepository.save(storeUserFood);
            rs.setSuccess(true);

        } else {
            logger.error("发布商品到客户端失败" + execute.getMessage());
            rs.setSuccess(false);
            rs.setMsg(execute.getMessage());
        }
        return rs;
    }

    private Result publishToEle(StoreUserFood storeUserFood, Store store, boolean checkExists) {
        Result rs = new Result();
        String photoUrl;
        try {
            photoUrl = uploadPhotoToEle(storeUserFood);
        } catch (Exception e) {
            rs.setMsg("饿了么图片上传失败，原因：" + e.getMessage());
            rs.setSuccess(false);
            return rs;
        }
        //FoodQuoteSku foodQuoteSku = null;
        /**
         * 寻找饿了么自定义的
         * */
        //String eleSkuId = storeUserFood.getEleSkuId();
//        if (StringUtils.isNotBlank(eleSkuId)) {
//            //从以skuid为键的map中取出sku
//            foodQuoteSku = foodQuoteSkuMapBySkuId.get(eleSkuId);
//        }
//        if (foodQuoteSku == null) {
//            /**
//             * 寻找到最小规格的sku
//             * */
//            foodQuoteSkus.sort((f1, f2) -> Float.valueOf(f1.getPrice() - f2.getPrice()).intValue());
//            for (FoodQuoteSku sku : foodQuoteSkus) {
//                if (sku.isIgnore()) {
//                    continue;
//                }
//                foodQuoteSku = sku;
//                break;
//            }
//            if (foodQuoteSku == null) {
//                throw new RuntimeException("没有可发布的规格");
//            }
//        }
        StoreUserFoodSku storeUserFoodSku = this.getEleSku(storeUserFood);
        String customSkuId = storeUserFoodSku.getFoodSkuId().toString();
        //将得到的skuid设置成饿了么的skuid
        storeUserFood.setEleSkuId(customSkuId);
        /**
         * 先判断商品是否存在
         * */
        SkuListRequest skuReq = new SkuListRequest();
        //根据商品的商品码和商家的门店码获取商品集合
        skuReq.setUpc(store.getCode() + "_" + storeUserFood.getFood().getCode());
        skuReq.setShopId(store.getCode());
        SkuListResponse skuRes = eleClient.request(skuReq);
        Response res = null;
        SkuListResponse.Data listData = skuRes.getData();
        //如果获取的商品数量大于零
        if (listData.getTotal() > 0) {

            String checkCustomerId = listData.getList().get(0).getCustomSkuId();
            /**
             * 如果skuId 一样，那么则更新即可
             * */
            if (checkCustomerId.equals(customSkuId)) {
                // 说明商品是存在的，那么名字就不用改，操作更新接口即可
                SkuUpdateRequest req = new SkuUpdateRequest();
                FoodCategory fc = foodCategoryRepository.findByName(storeUserFood.getFood().getCategoryName());
                Map<String, Long> categoryMap = store.getEleCategoryMap();
                if (fc != null) {
                    req.setCategoryId(categoryMap.get(fc.getId().toString()));
                }
//            req.setPredictCat(1);
                //req.setBrandName(food.getEleBrandName());
                req.setUpc(store.getCode() + "_" + storeUserFoodSku.getFoodSkuId());
                req.setName(storeUserFood.getFood().getName() + storeUserFoodSku.getName());
                req.setStatus(1); // 上架
                SkuUpdateRequest.Photo photo = new SkuUpdateRequest.Photo();
                List<SkuUpdateRequest.Photo> photos = new ArrayList<>();
                photos.add(photo);
                req.setPhotos(photos);
                photo.setIsMaster(1);
                photo.setUrl(photoUrl);
                req.setLeftNum(9999);
                Float priceIncrease = storeUserFood.getPriceIncrease();
                //float quotePrice = foodQuoteSku.getPrice() / (1 + priceIncrease / 100);
                req.setSalePrice((int) (storeUserFoodSku.getOutputPrice() * 100));
                //req.setSalePrice(Float.valueOf(foodQuoteSku.getPrice() * 100).intValue());
                req.setCustomSkuId(customSkuId);
                req.setSaleUnit(storeUserFoodSku.getSpec());
                req.setWeight(storeUserFoodSku.getWeight());
                if (StringUtils.isNotBlank(storeUserFood.getFood().getDescription())) {
                    req.setSummary(storeUserFood.getFood().getDescription());
                }
                req.setShopId(store.getCode());
                if (!storeUserFood.getSaleTimeMap().isEmpty()) {
                    //开启限时售卖
                    req.setDurationSaleFlag(true);
                    req.setRepeatDate(this.getEleDayList(storeUserFood.getSaleTimeMap()));
                    req.setEffectTimeMap(this.getEleSaleTime(storeUserFood.getSaleTimeMap()));

                } else {
                    //不开启限时售卖
                    req.setDurationSaleFlag(false);
                }
                res = eleClient.request(req);
            } else {
                //如果skuid不一样，说明要发布的和原来有的是不一样的，就要删除以后重新发布
                SkuDeleteRequest req = new SkuDeleteRequest();
                req.setCustomSkuId(checkCustomerId);
                req.setShopId(store.getCode());
                SkuDeleteResponse offlineResponse = eleClient.request(req);
                if (offlineResponse.getErrno() == 0) {

                } else {
                    throw new BizException("下架失败，原因：" + offlineResponse.getError());
                }
            }

        }
        //说明没有获取到商品或者删除了商品
        if (res == null) {
            customSkuId = storeUserFoodSku.getFoodSkuId().toString();
            storeUserFood.setEleSkuId(customSkuId);
            //操作新建商品接口
            SkuCreateRequest req = new SkuCreateRequest();
            FoodCategory fc = foodCategoryRepository.findByName(storeUserFood.getFood().getCategoryName());
            Map<String, Long> categoryMap = store.getEleCategoryMap();
            if (fc != null) {
                req.setCategoryId(categoryMap.get(fc.getId().toString()));
            }
//            req.setPredictCat(1);
            //req.setBrandName(food.getEleBrandName());
            req.setUpc(store.getCode() + "_" + storeUserFoodSku.getFoodSkuId());
            req.setName(storeUserFood.getFood().getName() + " " + storeUserFoodSku.getName());
            req.setStatus(1); // 上架
            SkuCreateRequest.Photo photo = new SkuCreateRequest.Photo();
            List<SkuCreateRequest.Photo> photos = new ArrayList<>();
            photos.add(photo);
            req.setPhotos(photos);
            photo.setIsMaster(1);
            photo.setUrl(photoUrl);
            req.setLeftNum(9999);
            Float priceIncrease = storeUserFood.getPriceIncrease();
            //float quotePrice = foodQuoteSku.getPrice() / (1 + priceIncrease / 100);
            req.setSalePrice((int) (storeUserFoodSku.getOutputPrice() * 100));
            //req.setSalePrice(Float.valueOf(foodQuoteSku.getPrice() * 100).intValue());
            req.setCustomSkuId(customSkuId);
            req.setSaleUnit(storeUserFoodSku.getSpec());
            req.setShopId(store.getCode());
            if (StringUtils.isNotBlank(storeUserFood.getFood().getDescription())) {
                req.setSummary(storeUserFood.getFood().getDescription());
            }
            req.setWeight(storeUserFoodSku.getWeight());
            System.out.println(JSON.toJSON(req));
            res = eleClient.request(req);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
            //如果是之前删除过的商品  那么需要重新修改一下名称和价格才能正确在饿了么端显示（根据情况来判断是否重新发布商品  如果是因为类目和营业执照原因的话就不重新发布）
            //this.publishToEle(storeUserFood, food, store, foodQuoteSkus, foodQuoteSkuMapBySkuId, checkExists);
            if (res.getErrno() != 5020) {
                logger.info("更新饿了么新发布的商品" + store.getName() + storeUserFood.getFood().getName());
                SkuUpdateRequest req1 = new SkuUpdateRequest();
                //FoodCategory fc = foodCategoryRepository.findByName(food.getCategoryName());
                //Map<String, Long> categoryMap = store.getEleCategoryMap();
                if (fc != null) {
                    req1.setCategoryId(categoryMap.get(fc.getId().toString()));
                }
//            req.setPredictCat(1);
                //req.setBrandName(food.getEleBrandName());
                req1.setUpc(store.getCode() + "_" + storeUserFoodSku.getFoodSkuId());
                req1.setName(storeUserFood.getFood().getName() + " " + storeUserFoodSku.getName());
                req1.setStatus(1); // 上架
                //SkuUpdateRequest.Photo photo1 = new SkuUpdateRequest.Photo();
                //List<SkuUpdateRequest.Photo> photos1 = new ArrayList<>();
                //photos1.add(photo1);
                //req1.setPhotos(photos1);
                //photo1.setIsMaster(1);
                //photo1.setUrl(photoUrl);
                req1.setLeftNum(9999);
                //Float priceIncrease = storeUserFood.getPriceIncrease();
                //float quotePrice = foodQuoteSku.getPrice() / (1 + priceIncrease / 100);
                req.setSalePrice((int) (storeUserFoodSku.getOutputPrice() * 100));
                //req.setSalePrice(Float.valueOf(foodQuoteSku.getPrice() * 100).intValue());
                req1.setCustomSkuId(customSkuId);
                req1.setSaleUnit(storeUserFoodSku.getSpec());
                req1.setWeight(storeUserFoodSku.getWeight());
                if (StringUtils.isNotBlank(storeUserFood.getFood().getDescription())) {
                    req1.setSummary(storeUserFood.getFood().getDescription());
                }
                if (!storeUserFood.getSaleTimeMap().isEmpty()) {
                    //开启限时售卖
                    req1.setDurationSaleFlag(true);
                    req1.setRepeatDate(this.getEleDayList(storeUserFood.getSaleTimeMap()));
                    req1.setEffectTimeMap(this.getEleSaleTime(storeUserFood.getSaleTimeMap()));

                } else {
                    //不开启限时售卖
                    req1.setDurationSaleFlag(false);
                }
                req1.setShopId(store.getCode());
                res = eleClient.request(req1);
            }
        }
        if (res.getErrno() == 0) {
            rs.setSuccess(true);
            logger.info("发布到饿了么成功：" + storeUserFood.getFood().getName());
            return rs;
        } else {
            rs.setSuccess(false);
            logger.info("发布到饿了么失败：" + storeUserFood.getFood().getName() + " " + res.getError());
            rs.setMsg(res.getError() + " " + storeUserFood.getFood().getName());
        }
        return rs;
    }

    private Map<String, String> getEleSaleTime(Map<String, String> map) {
        String timeStr = "";
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (StringUtils.isNotBlank(value)) {
                timeStr = value;
                break;
            }
        }
        Map<String, String> timeMap = new HashMap<>();
        String[] timeList = timeStr.split(",");
        for (String s : timeList) {
            String[] split = s.split("-");
            String start = split[0];
            String[] sc = start.split(":");
            Integer startHour = Integer.valueOf(sc[0]);
            Integer startMinus = Integer.valueOf(sc[1]);
            Integer startScend = startHour * 60 * 60 + startMinus * 60;

            String end = split[1];
            String[] ends = end.split(":");
            Integer endHours = Integer.valueOf(ends[0]);
            Integer endMinus = Integer.valueOf(ends[1]);
            Integer endScends = endHours * 60 * 60 + endMinus * 60;

            Integer value = endScends - startScend;

            timeMap.put(start + ":00", value.toString());


        }

        return timeMap;
    }

    private List<String> getEleDayList(Map<String, String> map) {
        List<String> dataList = new ArrayList<>();
        if (StringUtils.isNotBlank(map.get("sunday"))) {
            dataList.add("1");
        }
        if (StringUtils.isNotBlank(map.get("monday"))) {
            dataList.add("2");
        }
        if (StringUtils.isNotBlank(map.get("tuesday"))) {
            dataList.add("3");
        }
        if (StringUtils.isNotBlank(map.get("wednesday"))) {
            dataList.add("4");
        }
        if (StringUtils.isNotBlank(map.get("thursday"))) {
            dataList.add("5");
        }
        if (StringUtils.isNotBlank(map.get("friday"))) {
            dataList.add("6");
        }
        if (StringUtils.isNotBlank(map.get("saturday"))) {
            dataList.add("7");
        }
        return dataList;
    }

    private String uploadPhotoToEle(StoreUserFood suf) throws Exception {
        Map<String, String> map = suf.getElePhotos();
        String picture = suf.getFood().getPicture();
        if (map.containsKey(picture)) {
            return map.get(picture);
        }
        PictureUploadRequest req = new PictureUploadRequest();
        req.setUrl(picture);
        PictureUploadResponse res = eleClient.request(req);
        if (res.getErrno() == 0) {
            String url = res.getData().getUrl();
            map.clear();
            map.put(picture, url);
            suf.setElePhotos(map);
            return url;
        } else {
            throw new Exception(res.getError());
        }
    }

//    private Integer getSkuStock(Long id, String skuId) {
//        StoreUserFood userFood = storeUserFoodRepository.findOne(id);
//        String skuJson = userFood.getFoodSkuJson();
//        List<FoodSku> skuList = JSON.parseObject(skuJson, new TypeReference<List<FoodSku>>() {
//        });
//        for (FoodSku foodSku : skuList) {
//            if (skuId.equals(foodSku.getSkuId())) {
//                return foodSku.getStock();
//            }
//        }
//        return 0;
//    }

    private Result publishToMeituan(StoreUserFood storeUserFood, Store store, boolean checkExists, Plat plat) {
        Result rs = new Result();
        SystemParam param = null;
        if (store.getPlat() == Plat.MEITUAN) {
            param = meituanWaimaiService.getSystemParam();
        } else {
            param = clbmWaiMaiService.getSystemParam();
        }

        RetailParam rp = null;
        FoodDTO food = foodService.toDTO(storeUserFood.getFood());
        if (checkExists) {
            try {
                rp = APIFactory.getNewRetailApi().retailGet(param, store.getCode(), food.getCode());
            } catch (ApiOpException e) {
                String msg = e.getMsg();
                logger.warn(msg);
            } catch (ApiSysException e) {
                logger.warn(e.getExceptionEnum().getMsg(), e);
            }
        }
        FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
        List<RetailSkuParam> skuParams = this.getRetailSkuParam(storeUserFood);
        if (skuParams.size() == 0) {
            throw new BizException("请勾选规格后再进行操作");
        }
        Map<String, RetailSkuParam> retailBySkuId = new HashMap<>();
        for (RetailSkuParam skuParam : skuParams) {
            retailBySkuId.put(skuParam.getSku_id(), skuParam);
        }
        String actId = null;
        if (rp == null) {
            rp = new RetailParam();
        } else {
            if (rp.getIs_sp() == 1) {
                /**
                 * 如果是标品，美团是不允许修改商品信息的，只能通过发布sku来进行处理
                 * */
                RetailSkuParam standardSku = new RetailSkuParam();
                RetailSkuParam eSku = rp.getSkus().get(0);
                RetailSkuParam fos = skuParams.get(0);
                standardSku.setSku_id(eSku.getSku_id());
                standardSku.setSpec(eSku.getSpec());
                standardSku.setPrice(String.valueOf(fos.getPrice()));
                //设置库存
                standardSku.setStock(fos.getStock());
                standardSku.setBox_price(String.valueOf(foodSetting.getBoxPrice()));
                standardSku.setBox_num(String.valueOf(foodSetting.getBoxNum()));
                try {
                    String ok = APIFactory.getNewRetailApi().retailSkuSave(param, store.getCode(), food.getCode(), standardSku, new ArrayList<>());
                    if (ok.equals("ok")) {
                        //发布成功
                        logger.info("更新标品sku成功：" + food.getName() + ":" + food.getCode());
                    } else {
                        String msg = "更新标品sku失败：" + food.getName() + ":" + food.getCode() + "\n" + ok;
                        logger.error(msg);
                        rs.setMsg(msg);
                        return rs;
                    }
                } catch (ApiOpException e) {
                    String msg = "更新标品sku失败：" + food.getName() + ":" + food.getCode() + "，" + e.getMsg();
                    logger.error(msg, e);
                    rs.setMsg(msg);
                    return rs;
                } catch (Exception e) {
                    String msg = "更新标品sku失败：" + food.getName() + ":" + food.getCode();
                    logger.error(msg, e);
                    rs.setMsg(msg);
                    return rs;
                }

            } else {

                /**
                 * 找到需要删除的sku
                 * */
                List<RetailSkuParam> waitDeleteSkuList = new ArrayList<>();
                if (rp.getSkus() != null) {
                    for (RetailSkuParam skuParam : rp.getSkus()) {
                        /**
                         * 如果一个sku的sku_id为空，那么删除，
                         * 如果一个sku的sku_id不在本次发布的sku列表中，那么也删除
                         * */
                        if (StringUtils.isEmpty(skuParam.getSku_id())) {
                            waitDeleteSkuList.add(skuParam);
                            continue;
                        }
                        if (!retailBySkuId.containsKey(skuParam.getSku_id())) {
                            waitDeleteSkuList.add(skuParam);
                            continue;
                        }
                    }
                }

                /**
                 * 更新sku信息
                 * */
                //List<RetailSkuParam> skuList = toFoodSkuParamList(storeUserFood.getId(), foodQuoteSkus, foodSetting);
                try {
                    String ok = APIFactory.getNewRetailApi().retailSkuSave(param, store.getCode(), food.getCode(), null, skuParams
                    );
                    if (ok.equals("ok")) {
                        //发布成功
                        logger.info("更新非标品sku成功：" + food.getName());
                    } else {
                        String msg = "更新非标品sku失败：" + food.getName() + "\n" + ok;
                        logger.error(msg);
                        rs.setMsg(msg);
                        return rs;
                    }
                } catch (ApiOpException e) {
                    String msg = "更新非标品sku失败：" + food.getName() + " " + e.getMsg();
                    logger.error(msg);
                    //查询商品是否参加了满减活动
                    //GetActListParam getActListParam = APIFactory.getActRetailApi().actAllGetByAppFoodCodes(param, store.getCode(), food.getCode(), 1, 20);
                    //actId = getFullDiscountId(param, store.getCode(), food.getCode());
                    rs.setMsg(msg);
                } catch (Exception e) {
                    String msg = "更新非标品sku失败：" + food.getName();
                    logger.error(msg, e);
                    rs.setMsg(msg);
                }

                for (RetailSkuParam skuParam : waitDeleteSkuList) {
                    try {
                        String ok = APIFactory.getNewRetailApi().retailSkuDelete(param, store.getCode(), food.getCode(), skuParam.getSku_id());
                        if (ok.equals("ok")) {
                            //发布成功
                            logger.info("删除sku成功：" + food.getName() + " " + skuParam.getSpec());
                        } else {
                            String msg = "删除sku失败：" + food.getName() + " " + skuParam.getSpec();
                            logger.info(msg);
                            rs.setMsg(msg);
                            return rs;
                        }
                    } catch (ApiOpException e) {
                        String msg = "删除sku失败：" + food.getName() + ":" + skuParam.getSpec() + "，" + e.getMsg();
                        logger.error(msg, e);
                        rs.setMsg(msg);
                    } catch (Exception e) {
                        String msg = "删除sku失败：" + food.getName() + ":" + skuParam.getSpec();
                        logger.error(msg, e);
                        rs.setMsg(msg);
                    }
                }

            }
        }


        /**
         * 如果商品不存在，则进行发布
         * */
        rp.setApp_food_code(food.getCode());
        rp.setApp_poi_code(store.getCode());
        rp.setBox_num(foodSetting.getBoxNum());
        rp.setBox_price(foodSetting.getBoxPrice());
        if (storeUserFood.getCategory() != null) {
            rp.setCategory_name(storeUserFood.getCategory().getName());
        } else {
            rp.setCategory_name(food.getCategoryName());
        }
        rp.setDescription(food.getDescription());
        rp.setFlavour(food.getFlavour());
        if (food.getPropertieList().size() > 0) {
            List<Propertie> list = food.getPropertieList();
            List<FoodProperty> properties = new ArrayList<>();
            for (Propertie propertie : list) {
                FoodProperty property = new FoodProperty();
                property.setProperty_name(propertie.getPropertyName());
                property.setValues(propertie.getValues());
                properties.add(property);
            }
            rp.setProperties(properties);
        }
        rp.setMin_order_count(storeUserFood.getMinOrderCount() == null ? food.getMinOrderCount() : storeUserFood.getMinOrderCount());
        if (store.getPlat() == Plat.MEITUAN) {
            if (food.getVideo() != null && StringUtils.isNotBlank(food.getVideo())) {
                if (storeUserFood.getMeituanVideoId() != null) {
                    rp.setVideo_id(storeUserFood.getMeituanVideoId());
                } else {
                    //调用美团接口上传视频
                    try {
                        List<VideoParam> videoParams = APIFactory.getNewRetailApi().videoUpload(param, store.getCode(), PictureHelper.image2byte(food.getVideo()), UUID.randomUUID().toString().trim().replaceAll("-", ""));
                        for (VideoParam videoParam : videoParams) {
                            System.out.println(videoParam.getVideo_id());
                            rp.setVideo_id(videoParam.getVideo_id());
                            storeUserFood.setMeituanVideoId(videoParam.getVideo_id());
                        }
                    } catch (ApiOpException e) {
                        logger.error(e.getMsg(), e);
                    } catch (ApiSysException e) {
                        logger.error(e.getMessage(), e);
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
            if (StringUtils.isBlank(food.getPictureId())) {
                //没有商品图片
                String pid = null;
                String pname = PictureHelper.getPicSuffix(food.getPicture());
                try {
                    pid = APIFactory.getImageApi().imageUpload(param, store.getCode(), PictureHelper.image2byte(food.getPicture()), pname);
                } catch (ApiOpException e) {
                    logger.error(e.getMsg(), e);
                } catch (ApiSysException e) {
                    logger.error(e.getMessage(), e);
                }
                food.setPictureId(pid);
                Food byCode = foodRepository.findByCode(food.getCode());
                byCode.setPictureId(pid);
                foodRepository.save(byCode);
            }
            rp.setPicture(food.getPictureId());
        } else {
            Food byCode = foodRepository.findByCode(food.getCode());
            if (byCode.getVideo() != null && StringUtils.isNotBlank(byCode.getVideo())) {
                if (storeUserFood.getClbmVideoId() != null) {
                    rp.setVideo_id(storeUserFood.getClbmVideoId());
                } else {
                    //调用美团接口上传视频
                    try {
                        List<VideoParam> videoParams = APIFactory.getNewRetailApi().videoUpload(param, store.getCode(), PictureHelper.image2byte(byCode.getVideo()), UUID.randomUUID().toString().trim().replaceAll("-", ""));
                        for (VideoParam videoParam : videoParams) {
                            rp.setVideo_id(videoParam.getVideo_id());
                            storeUserFood.setClbmVideoId(videoParam.getVideo_id());
                        }
                    } catch (ApiOpException e) {
                        logger.error(e.getMsg(), e);
                    } catch (ApiSysException e) {
                        logger.error(e.getMessage(), e);
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
            if (StringUtils.isBlank(food.getClbPictureId())) {
                //没有菜老板的图品id
                if (StringUtils.isBlank(food.getClbPicture())) {
                    //给原来的图片加上水印 生成新的图片并设置为菜老板的图片
                    byCode = addClbPhoto(byCode);
                }
                String pid = null;
                String pname = PictureHelper.getPicSuffix(byCode.getClbPicture());
                try {
                    pid = APIFactory.getImageApi().imageUpload(param, store.getCode(), PictureHelper.image2byte(byCode.getClbPicture()), pname);
                } catch (ApiOpException e) {
                    logger.error(e.getMsg(), e);
                } catch (ApiSysException e) {
                    logger.error(e.getMessage(), e);
                }
                food.setClbPictureId(pid);
                byCode.setClbPictureId(pid);
                foodRepository.save(byCode);
            }
            rp.setPicture(food.getClbPictureId());
        }
        rp.setIs_sold_out(0);
        rp.setZh_name(food.getZhName());
        rp.setProduct_name(food.getProductName());
        rp.setOrigin_name(food.getOriginName());
        BigDecimal b = new BigDecimal(storeUserFood.getSalePrice());
        //rp.setPrice(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        rp.setUnit(food.getUnit());
        FoodCategory category = foodCategoryRepository.findByName(food.getCategoryName());
        //rp.setTag_id(category.getMeituanTagId() == null ? food.getMeituanTagId() : category.getMeituanTagId());
        rp.setTag_id(food.getMeituanTagId());
        rp.setSequence(food.getIdx());
        //List<RetailSkuParam> skuList = toFoodSkuParamList(storeUserFood.getId(), foodQuoteSkus, foodSetting);
        if (skuParams.size() == 1) {
            rp.setName(food.getName() + skuParams.get(0).getSpec());
        } else {
            rp.setName(food.getName());
        }
        if (!storeUserFood.getSaleTimeMap().isEmpty()) {
            for (RetailSkuParam retailSkuParam : skuParams) {
                retailSkuParam.setAvailable_times(meituanWaimaiService.getTimeList(storeUserFood.getSaleTimeMap()));
            }
        }
        rp.setSkus(skuParams);
        String result;
        try {
            result = APIFactory.getNewRetailApi().retailInitData(param, rp);
        } catch (ApiOpException e) {
            if (!checkExists) {
                return publishToStore(storeUserFood, store, true);
            }
            logger.info("发布到美团失败：" + e.getMsg(), e);
            if (e.getMsg() != null && e.getMsg().contains("折扣商品") && (store.getPlat() == Plat.MEITUAN || store.getPlat() == Plat.CLBM)) {
                return publishtActivityToMeiTuan(storeUserFood, food, store, store.getPlat());
            } else if (e.getMsg() != null && e.getMsg().contains("商品券") && (store.getPlat() == Plat.MEITUAN || store.getPlat() == Plat.CLBM)) {
                logger.info("商品参加商品券活动，改价后重新发布");
                return publishCouponToMeiTuan(storeUserFood, food, store, store.getPlat());
            } else if (e.getMsg() != null && e.getMsg().contains("X件优惠") && (store.getPlat() == Plat.MEITUAN || store.getPlat() == Plat.CLBM)) {
                logger.info("商品参加X件优惠中活动，改价后重新发布");
                return publishtXmToMeiTuan(storeUserFood, food, store, store.getPlat());
            }
//                JSONArray jsonArray = JSON.parseArray(e.getMsg());
//                JSONObject object = jsonArray.getJSONObject(0);
            rs.setMsg(e.getMsg());
            return rs;
        } catch (ApiSysException e) {
            logger.info("发布到美团失败：" + e.getExceptionEnum().getMsg(), e);
            rs.setMsg(e.getExceptionEnum().getMsg());
            return rs;
        }
        logger.info("发布到美团，返回结果：" + result);
        if (result.equals("ok")) {
            logger.info("发布到美团成功");
            rs.setSuccess(true);
        } else {
            logger.info("发布到美团失败");
            rs.setMsg(result);
            rs.setSuccess(false);
            return rs;
        }
        if (actId != null) {
            logger.info("商品参加了满减活动 " + food.getName() + store.getName());
            //publishFullDiscountToMeiTuan(storeUserFood, food, store, actId, param);
        }
        return rs;
    }

    private Result publishtXmToMeiTuan(StoreUserFood storeUserFood, FoodDTO food, Store store, Plat plat) {
        SystemParam param = null;
        Result result = new Result();
        if (store.getPlat() == Plat.MEITUAN) {
            param = meituanWaimaiService.getSystemParam();
        } else if (store.getPlat() == Plat.CLBM) {
            param = clbmWaiMaiService.getSystemParam();
        } else {
            result.setSuccess(false);
            result.setMsg("非美团平台暂不支持");
            return result;
        }
        //查询商品参加的X元M件活动信息，保存对应的活动id;
        Long actId = 0l;
        try {
            GetActListParam actListParam = APIFactory.getActRetailApi().actAllGetByAppFoodCodes(param, store.getCode(), food.getCode(), 1, 0, 1, 50);
            List<ActListParam> act_list = actListParam.getAct_list();
            for (ActListParam listParam : act_list) {
                if (listParam.getType() == 43) {
                    actId = listParam.getAct_id();
                    break;
                }
            }

            if (actId == 0) {
                result.setSuccess(false);
                result.setMsg("X件M元查询失败");
                return result;
            } else {
                //移除活动
                String s = APIFactory.getActRetailApi().actDeleteActsByProducts(param, store.getCode(), food.getCode(), 43);
                logger.info("移除活动返回" + s);
                if (!"ok".equals(s)) {
                    logger.error("移除X件M元活动失败" + s);
                    result.setMsg("移除X件M元活动失败" + s);
                    result.setSuccess(false);
                    return result;
                }
            }
        } catch (ApiOpException e1) {
            logger.info("查询X元M件失败" + e1.getMsg(), e1);
            result.setMsg(e1.getMsg());
            result.setSuccess(false);
            return result;
        } catch (ApiSysException e1) {
            logger.info("查询X元M件失败：" + e1.getExceptionEnum().getMsg(), e1);
            result.setMsg(e1.getExceptionEnum().getMsg());
            result.setSuccess(false);
            return result;
        }

        //重新发布一遍 把规格设置成最小的
        List<String> specialSkuList = StringUtils.isBlank(storeUserFood.getSpecialSkuIdList()) ? Collections.EMPTY_LIST
                : Arrays.asList(storeUserFood.getSpecialSkuIdList().split(","));
        if (specialSkuList.size() > 1) {
            storeUserFood.setSpecialSkuIdList(specialSkuList.get(0));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Result publishResult = publishToStore(storeUserFood, store, true);
        //如果发布失败 返回
        if (!publishResult.isSuccess()) {
            result.setSuccess(false);
            result.setMsg(publishResult.getMsg());
            return result;
        }
        //重新把商品添加到活动中
        BundItemParam bundItemParam = new BundItemParam();
        bundItemParam.setDay_limit(-1);
        bundItemParam.setApp_spu_code(food.getCode());
        List<BundItemParam> list = new ArrayList<>();
        list.add(bundItemParam);
        ItemBundParam bundParam = new ItemBundParam();
        bundParam.setAct_id(actId);
        bundParam.setApp_foods(list);
        String s;
        try {
            s = APIFactory.getActRetailApi().actItemBundlesSave(param, store.getCode(), bundParam);
        } catch (ApiOpException e1) {
            logger.info("添加X元M件活动返回" + e1.getMsg(), e1);
            result.setMsg(e1.getMsg());
            result.setSuccess(false);
            return result;
        } catch (ApiSysException e1) {
            logger.info("添加X元M件活动返回：" + e1.getExceptionEnum().getMsg(), e1);
            result.setMsg(e1.getExceptionEnum().getMsg());
            result.setSuccess(false);
            return result;
        }
        logger.info("添加X元M件活动返回，返回结果：" + s);
        result.setSuccess(true);
        return result;
    }

    private void publishFullDiscountToMeiTuan(StoreUserFood storeUserFood, FoodDTO food, Store store, String actId, SystemParam param) {
        List<String> specialSkuList = StringUtils.isBlank(storeUserFood.getSpecialSkuIdList()) ? Collections.EMPTY_LIST
                : Arrays.asList(storeUserFood.getSpecialSkuIdList().split(","));
        if (specialSkuList.size() > 1) {
            //多个规格，不支持参加活动
            storeUserFood.setSpecialSkuIdList(specialSkuList.get(0));
            Result publishResult = publishToStore(storeUserFood, store, true);
            if (!publishResult.isSuccess()) {
                logger.error("添加满减活动失败，原因是发布失败");
                return;
            }
        }
        List<ActItemParam> actFood = new ArrayList<>();
        ActItemParam actItemParam = new ActItemParam();
        actItemParam.setDay_limit(999);
        actItemParam.setApp_food_code(food.getCode());
        actFood.add(actItemParam);
        try {
            String s = APIFactory.getActApi().actFullDiscountFoodsBatchSave(param, store.getCode(), actId, actFood);
            logger.info("添加活动返回" + s);
        } catch (ApiOpException e) {
            logger.error(e.getMsg(), e);
        } catch (ApiSysException e) {
            logger.error(e.getMessage(), e);
        }

    }

    private String getFullDiscountId(SystemParam param, String storeCode, String foodCode) {
        List<ActFullDiscountParam> actFullDiscountParams = new ArrayList<>();
        try {
            actFullDiscountParams = APIFactory.getActApi().actFullDiscountList(param, storeCode);
            if (actFullDiscountParams.size() == 0) {
                return null;
            }
            for (ActFullDiscountParam actFullDiscountParam : actFullDiscountParams) {
                ActFullDiscountItemParam act_info = actFullDiscountParam.getAct_info();
                String act_ids = act_info.getAct_ids();
                ActFullDiscountParam foodsList = APIFactory.getActApi().actFullDiscountFoodsList(param, storeCode, act_ids, 0, 199);
                System.out.println(foodsList);
                List<ActItemParam> app_foods = foodsList.getApp_foods();
                if (app_foods.size() == 0) {
                    continue;
                }
                for (ActItemParam app_food : app_foods) {
                    if (foodCode.equals(app_food.getApp_food_code())) {
                        return act_ids;
                    }
                }
            }
        } catch (ApiOpException e) {
            logger.error(e.getMsg(), e);
        } catch (ApiSysException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    //单个商品改价的时候来重新发布商品券
    private Result publishCouponToMeiTuan(StoreUserFood storeUserFood, FoodDTO food, Store store, Plat plat) {
        SystemParam param = null;
        Result result = new Result();
        if (store.getPlat() == Plat.MEITUAN) {
            param = meituanWaimaiService.getSystemParam();
        } else if (store.getPlat() == Plat.CLBM) {
            param = clbmWaiMaiService.getSystemParam();
        } else {
            result.setSuccess(false);
            result.setMsg("非美团平台暂不支持");
            return result;
        }
        //首先查询商品参加的商品券详情
        String actName = "";
        Integer fullPrice = 0;
        Integer reducePrice = 0;
        Long actId = 0l;
        Integer start = 0;
        Integer end = 0;
        try {
            //美团目前没有返回对应的id  先注释  等后期有了对应的id再加
            GetActListParam actListParam = APIFactory.getActRetailApi().actAllGetByAppFoodCodes(param, store.getCode(), food.getCode(), 1, 2, 1, 50);
            List<ActListParam> actList = actListParam.getAct_list();
            if (actList.size() > 0) {
                for (ActListParam listParam : actList) {
                    if (listParam.getType() == 117 && listParam.getStatus() == 1) {
                        actId = listParam.getAct_id();
                        start = listParam.getStart_time();
                        end = listParam.getEnd_time();
                        break;
                    }
                }
                List<ActCouponListParam> couponList = APIFactory.getActRetailApi().actGoodsCouponList(param, store.getCode(), start - 1, end + 1, 1, 1, 50);
                for (ActCouponListParam actCouponListParam : couponList) {
                    //if (actCouponListParam.getActId() == actId) {
                    actName = actCouponListParam.getCoupon_name();
                    fullPrice = actCouponListParam.getAct_price_coupon_info().get(0).getFull_price();
                    reducePrice = actCouponListParam.getAct_price_coupon_info().get(0).getReduce_price();
                    break;
                    //}
                }
            } else {
                logger.error("未查询到商品券活动");
                result.setMsg("未查询到商品券活动");
                result.setSuccess(false);
                return result;
            }

        } catch (ApiOpException e) {
            logger.info("商品券活动查询失败" + e.getMsg(), e);
            result.setMsg(e.getMsg());
            result.setSuccess(false);
            return result;
        } catch (ApiSysException e) {
            logger.info("商品券活动查询失败" + e.getExceptionEnum().getMsg(), e);
            result.setMsg(e.getExceptionEnum().getMsg());
            result.setSuccess(false);
            return result;
        }
        if (fullPrice == 0 || reducePrice == 0) {
            logger.info("商品券活动查询失败");
            result.setMsg("商品券活动查询失败");
            result.setSuccess(false);
            return result;
        }
        //首先移除活动
        String deleteS;
        try {
            deleteS = APIFactory.getActRetailApi().actDeleteActsByProducts(param, store.getCode(), food.getCode(), 117);
        } catch (ApiOpException e) {
            logger.info("商品券活动移除失败" + e.getMsg(), e);
            result.setMsg(e.getMsg());
            result.setSuccess(false);
            return result;
        } catch (ApiSysException e) {
            logger.info("商品券活动移除失败" + e.getExceptionEnum().getMsg(), e);
            result.setMsg(e.getExceptionEnum().getMsg());
            result.setSuccess(false);
            return result;
        }
        logger.info("移除商品券活动返回" + deleteS);
        //重新发布一遍 把规格设置成最小的
        List<String> specialSkuList = StringUtils.isBlank(storeUserFood.getSpecialSkuIdList()) ? Collections.EMPTY_LIST
                : Arrays.asList(storeUserFood.getSpecialSkuIdList().split(","));
        if (specialSkuList.size() > 1) {
            storeUserFood.setSpecialSkuIdList(specialSkuList.get(0));
        }
        Result publishResult = publishToStore(storeUserFood, store, true);
        //如果发布失败 返回
        if (!publishResult.isSuccess()) {
            result.setSuccess(false);
            result.setMsg(publishResult.getMsg());
            return result;
        }
        GoodsCouponParam couponParam = new GoodsCouponParam();
        couponParam.setApp_poi_codes(store.getCode());
        List<PriceCouponInfo> couponInfos = new ArrayList<>();
        PriceCouponInfo info = new PriceCouponInfo();
        info.setFull_price(fullPrice);
        info.setReduce_price(reducePrice);
        info.setStock(9999);
        info.setUser_type(0);
        couponInfos.add(info);
        couponParam.setAct_price_coupon_info(couponInfos);
        couponParam.setApp_spu_codes(food.getCode());
        couponParam.setCoupon_limit_count(9999);
        couponParam.setCoupon_name(actName);
        couponParam.setIs_single_poi(1);
        couponParam.setTake_coupon_end_time((int) (System.currentTimeMillis() / 1000 + 43200));
        couponParam.setTake_coupon_start_time((int) (System.currentTimeMillis() / 1000));
        couponParam.setType(1);
        couponParam.setUse_coupon_start_time((int) (System.currentTimeMillis() / 1000));
        String s;
        try {
            s = APIFactory.getActRetailApi().actGoodsCouponSave(param, couponParam);
        } catch (ApiOpException e1) {
            logger.info("美团创建商品券活动失败" + e1.getMsg(), e1);
            result.setMsg(e1.getMsg());
            result.setSuccess(false);
            return result;
        } catch (ApiSysException e1) {
            logger.info("美团创建商品券活动失败：" + e1.getExceptionEnum().getMsg(), e1);
            result.setMsg(e1.getExceptionEnum().getMsg());
            result.setSuccess(false);
            return result;
        }
        logger.info("创建商品券活动到美团，返回结果：" + s);
        if (s.equals("ok")) {
            logger.info("创建商品券活动到美团成功");
            result.setSuccess(true);
        } else {
            logger.info("创建商品券活动到美团失败");
            result.setMsg(s);
            result.setSuccess(false);
        }
        return result;
    }

    private Result publishtActivityToMeiTuan(StoreUserFood storeUserFood, FoodDTO food, Store store, Plat plat) {
        SystemParam param = null;
        Result result = new Result();
        if (store.getPlat() == Plat.MEITUAN) {
            param = meituanWaimaiService.getSystemParam();
        } else if (store.getPlat() == Plat.CLBM) {
            param = clbmWaiMaiService.getSystemParam();
        } else {
            result.setSuccess(false);
            result.setMsg("非美团平台暂不支持");
            return result;
        }
        //首先下架商品 移除活动
        soldOut(storeUserFood, store.getPlat());
        //如果下架失败 则返回
        if (plat == Plat.MEITUAN && storeUserFood.getMeituanPublishStatus() != PublishStatus.IN_STOCK) {
            result.setSuccess(false);
            result.setMsg("下架该商品失败 请检查后再发布活动");
            return result;
        } else if (plat == Plat.CLBM && storeUserFood.getClbmPublishStatus() != PublishStatus.IN_STOCK) {
            result.setSuccess(false);
            result.setMsg("下架该商品失败 请检查后再发布活动");
            return result;
        }
        if (storeUserFood.getActiveType() != null) {
            //活动策略2 先加价百分比 发布过去
            storeUserFood.setSalePrice(storeUserFood.getQuotePrice() * (1 + storeUserFood.getPriceIncrease() / 100));
        }
        //重新发布一遍 把规格设置成最小的
        List<String> specialSkuList = StringUtils.isBlank(storeUserFood.getSpecialSkuIdList()) ? Collections.EMPTY_LIST
                : Arrays.asList(storeUserFood.getSpecialSkuIdList().split(","));
        if (specialSkuList.size() > 1) {
            storeUserFood.setSpecialSkuIdList(specialSkuList.get(0));
        }
        Result publishResult = publishToStore(storeUserFood, store, true);
        //如果发布失败 返回
        if (!publishResult.isSuccess()) {
            result.setSuccess(false);
            result.setMsg(publishResult.getMsg());
            return result;
        }

        List<String> newSkuList = StringUtils.isBlank(storeUserFood.getSpecialSkuIdList()) ? Collections.EMPTY_LIST
                : Arrays.asList(storeUserFood.getSpecialSkuIdList().split(","));
//        List<FoodQuoteSku> foodQuoteSkus = getFoodQuoteSkuList(food.getSkuList(), newSkuList, storeUserFood.getSalePrice(), true);
//        FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);

        //List<RetailSkuParam> skuList = toFoodSkuParamList(storeUserFood.getId(), foodQuoteSkus, foodSetting);
        List<RetailSkuParam> skuList = this.getRetailSkuParam(storeUserFood);
        RetailSkuParam skuParam = skuList.get(0);
        List<ActRetailDiscountParam> list = new ArrayList<>();
        ActRetailDiscountParam actDiscountParam = new ActRetailDiscountParam();
        Double price = 0.0;

        if (storeUserFood.getActiveType() != null && storeUserFood.getActiveType() == 2) {
            //美团上卖的规格价格
            Double oprice = Double.valueOf(skuParam.getPrice());
            Float skudis = Float.valueOf(skuParam.getPrice()) / storeUserFood.getSalePrice();
            //原来的报价
            Double ooprice = Double.valueOf(skudis * storeUserFood.getQuotePrice());
            //策略2
            if (ooprice <= disCountPrice2) {
                price = 0.1;
            } else {
                BigDecimal bg = new BigDecimal(ooprice - disCountPrice2);
                price = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (oprice * 0.3 > price) {
                BigDecimal bg = new BigDecimal(oprice * 0.31);
                actDiscountParam.setAct_price(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            } else {
                actDiscountParam.setAct_price(price);
            }

        } else {
            Double oprice = Double.valueOf(skuParam.getPrice());
            //策略1
            if (oprice <= disCountPrice) {
                price = 0.1;
            } else {
                BigDecimal bg = new BigDecimal(oprice - disCountPrice);
                price = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (oprice * 0.3 > price) {
                BigDecimal bg = new BigDecimal(oprice * 0.31);
                actDiscountParam.setAct_price(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            } else {
                actDiscountParam.setAct_price(price);
            }

        }
        actDiscountParam.setApp_food_code(food.getCode());
        Date date = new Date();
        long time = date.getTime() / 1000;
        int startTime = (int) time + 10;
        int endTime = (int) startTime + 360 * 24 * 60 * 60;
        actDiscountParam.setStart_time(startTime);
        actDiscountParam.setEnd_time(endTime);
        actDiscountParam.setOrder_limit(1);
        actDiscountParam.setDay_limit(999);
        actDiscountParam.setSetting_type(1);
        list.add(actDiscountParam);

        String s;
        try {
            s = APIFactory.getActRetailApi().actRetailDiscountBatchsave(param, store.getCode(), list);
        } catch (ApiOpException e1) {
            logger.info("美团创建活动失败" + e1.getMsg(), e1);
            result.setMsg(e1.getMsg());
            result.setSuccess(false);
            return result;
        } catch (ApiSysException e1) {
            logger.info("美团创建活动失败：" + e1.getExceptionEnum().getMsg(), e1);
            result.setMsg(e1.getExceptionEnum().getMsg());
            result.setSuccess(false);
            return result;
        }
        logger.info("创建活动到美团，返回结果：" + s);
        if (s.equals("ok")) {
            logger.info("创建活动到美团成功");
            result.setSuccess(true);
        } else {
            logger.info("创建活动到美团失败");
            result.setMsg(s);
            result.setSuccess(false);
        }
        try {
            //修改每单限购总数量
            String s1 = APIFactory.getActApi().actDiscountActivityOrderLimit(param, store.getCode(), 1);
            System.out.println(s1);
        } catch (ApiOpException e) {
            logger.error(e.getMsg(), e);
        } catch (ApiSysException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

//    private List<RetailSkuParam> toFoodSkuParamList(Long sufId, List<FoodQuoteSku> foodQuoteSkus, FoodSetting foodSetting) {
//        List<RetailSkuParam> skuList = new ArrayList<>();
//        for (int i = 0; i < foodQuoteSkus.size(); i++) {
//            FoodQuoteSku quoteSku = foodQuoteSkus.get(i);
//            RetailSkuParam skuParam = new RetailSkuParam();
//            skuParam.setSku_id(quoteSku.getSkuId());
//            skuParam.setSpec(quoteSku.getSpec());
//            skuParam.setPrice(String.valueOf(quoteSku.getPrice()));
//            skuParam.setBox_price(String.valueOf(foodSetting.getBoxPrice()));
//            skuParam.setBox_num(Float.valueOf(foodSetting.getBoxNum()).intValue() + "");
//            String[] split = quoteSku.getSkuId().split("-");
//            skuParam.setStock(getSkuStock(sufId, quoteSku.getSkuId()).toString());
//            skuParam.setWeight(Long.valueOf(quoteSku.getWeight()));
//            skuList.add(skuParam);
//        }
//        return skuList;
//    }

    private Food addClbPhoto(Food food) {

        if (food.getPicture().startsWith("http://p")) {
            food.setClbPicture(food.getPicture());
            return food;
        }
        String s = food.getPicture();
        int i = s.lastIndexOf("cn/");
        String fullname = s.substring(i + 3);
        int i1 = fullname.lastIndexOf("/");
        String name = fullname.substring(i1 + 1);
        String bucketName = "tgyx";
        String newName = "clb" + name;
        String targetImage = fullname.substring(0, i1 + 1) + newName;
        StringBuilder sbStyle = new StringBuilder();
        sbStyle.delete(0, sbStyle.length());
        String styleType = "image/watermark,image_YWR2L2NhaWxhb2Jhbi5qcGc=,t_100,g_nw,x_0,y_0";
        Formatter styleFormatter = new Formatter(sbStyle);
        styleFormatter.format("%s|sys/saveas,o_%s,b_%s", styleType,
                BinaryUtil.toBase64String(targetImage.getBytes()),
                BinaryUtil.toBase64String(bucketName.getBytes()));
        ProcessObjectRequest request = new ProcessObjectRequest(bucketName, fullname, sbStyle.toString());
        GenericResult processResult = ossClient.processObject(request);
        String json = null;
        try {
            json = IOUtils.readStreamAsString(processResult.getResponse().getContent(), "UTF-8");
            processResult.getResponse().getContent().close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        System.out.println("菜老板图片添加成功" + json);
        food.setClbPicture(s.substring(0, i + 3) + targetImage);
        foodRepository.save(food);
        return food;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveStoreUserFood(BatchSaveParam param) {
        List<Long> foodIds = param.getFoodList().stream().map(o -> o.getFoodId()).collect(Collectors.toList());
        List<Food> foodList = foodRepository.findByIdInAndDeletedFalse(foodIds);
        Map<Long, Food> foodMap = new HashMap<>();
        for (Food food : foodList) {
            foodMap.put(food.getId(), food);
        }
        if (param.getAllStoreUser()) {
            StoreUserService.SearchParam suParam = new StoreUserService.SearchParam();
            suParam.setStatus(StoreUserStatus.VALID);
            suParam.setOpened(true);
            int pageSize = 50;
            suParam.setPageSize(pageSize);
            while (true) {
                Paging<StoreUserDTO> pg = transactionTemplate.execute((status) -> {
                    Paging<StoreUserDTO> paging = storeUserService.search(suParam);
                    for (StoreUserDTO dto : paging.getResults()) {
                        saveStoreUserFood(dto.getId(), param.getFoodList(), foodMap);
                    }
                    return paging;
                });
                if (!pg.getHasNext()) {
                    break;
                }
                suParam.setPage(suParam.getPage() + 1);
            }
        } else {
            for (Long id : param.getStoreUserIdList()) {
                saveStoreUserFood(id, param.getFoodList(), foodMap);
            }

        }
    }

    @Override
    public LowPrice getStoreUserFoodPrice(StoreUserFood storeUserFood) {
        LowPrice lowPrice = new LowPrice();
        List<StoreUserFoodSku> skuList = storeUserFoodSkuRepository.findByStoreUserFoodId(storeUserFood.getId());
        List<StoreUserFoodSku> xSkuList = new ArrayList<>();
        for (StoreUserFoodSku storeUserFoodSku : skuList) {
            if (storeUserFood.getSpecialSkuIdList().indexOf(storeUserFoodSku.getFoodSkuId().toString()) != -1) {
                xSkuList.add(storeUserFoodSku);
            }
        }
        if (xSkuList.size() == 0) {
            throw new BizException("请勾选规格后再进行操作");
        }
        lowPrice.setInputPrice(xSkuList.get(0).getInputPrice());
        lowPrice.setOutputPrice(xSkuList.get(0).getOutputPrice());
        for (StoreUserFoodSku storeUserFoodSku : xSkuList) {
            if (storeUserFoodSku.getInputPrice() <= lowPrice.getInputPrice()) {
                lowPrice.setInputPrice(storeUserFoodSku.getInputPrice());
            }

            if (storeUserFoodSku.getOutputPrice() <= lowPrice.getOutputPrice()) {
                lowPrice.setOutputPrice(storeUserFoodSku.getOutputPrice());
            }

        }
        return lowPrice;
    }

    @Override
    public void batchSaveNew(BatchAddNewParam param) {
        //首先判断门店中是否存在该商品，不存在先添加商品
        //假如存在，再判断是否有该skuid

        StoreUser storeUser = storeUserRepository.findOne(param.getStoreUserId().get(0));
        for (SaveNewParam saveParam : param.getFoodList()) {
            info.batcloud.wxc.core.entity.FoodSku foodSku = foodSkuRepository.findOne(saveParam.getSkuId());
            StoreUserFood storeUserFood = storeUserFoodRepository.findByStoreUserIdAndFoodId(storeUser.getId(), foodSku.getFoodId());
            if (storeUserFood == null) {
                //新增商品并且新增规格
                Food food = foodRepository.findOne(foodSku.getFoodId());
                FoodCategory category = foodCategoryRepository.findByName(food.getCategoryName());
                StoreUserFood userFood = new StoreUserFood();
                userFood.setMeituanVideoId(null);
                userFood.setClbmVideoId(null);
                userFood.setClbmPublishStatus(PublishStatus.WAIT);
                userFood.setMinOrderCount(1);
                userFood.setWanteSkuMap(null);
                userFood.setSaleTimeMap(null);
                userFood.setSaleTime(null);
                userFood.setWanteSkuMapJson(null);
                userFood.setWanteId(null);
                userFood.setSupplierAlterQuotePrice(0f);
                userFood.setSupplierQuotePrice(0f);
                userFood.setSupplierIncrease(0f);
                userFood.setFoodSupplier(null);
                userFood.setEleSkuId(null);
                userFood.setCityId(storeUser.getCity().getId());
                userFood.setFoodQuoteReport(null);
                userFood.setWarehouseIds(null);
                userFood.setElePhotos(null);
                userFood.setElePhotosJson(null);
                userFood.setMeituanPublishStatus(PublishStatus.WAIT);
                userFood.setJddjPublishStatus(PublishStatus.WAIT);
                userFood.setWantePublishStatus(PublishStatus.WAIT);
                userFood.setElePublishStatus(PublishStatus.WAIT);
                userFood.setSpecialSkuIdList(null);
                userFood.setCategory(category);
                userFood.setFoodVersion(0);
                userFood.setPublishMsg("");
                userFood.setAlterQuotePrice(saveParam.getInputPrice());
                userFood.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
                userFood.setPriceIncrease(0f);
                userFood.setFoodUnit(food.getUnit());
                //userFood.setFoodSkuJson("");
                userFood.setSalePrice(saveParam.getOutputPrice());
                userFood.setSale(false);
                userFood.setStoreUser(storeUser);
                userFood.setCreateTime(new Date());
                userFood.setUpdateTime(new Date());
                userFood.setQuotePrice(saveParam.getInputPrice());
                userFood.setFood(food);
                userFood.setQuotePriceLock(false);
                userFood.setUnlockTime(null);
                userFood.setOriginalQuotePrice(saveParam.getInputPrice());
                userFood.setActiveType(null);
                StoreUserFood save = storeUserFoodRepository.save(userFood);
                //将所有的规格全部添加到门店商品中，并设置为0库存
                storeUserFoodSkuService.createSufSku(save.getId());
                //更新对应的规格数据
                StoreUserFoodSku storeUserFoodSku = storeUserFoodSkuRepository.findByStoreUserIdAndUpc(storeUser.getId(), foodSku.getUpc());
                storeUserFoodSku.setStock(saveParam.getStock());
                storeUserFoodSku.setInputPrice(saveParam.getInputPrice());
                storeUserFoodSku.setOutputPrice(saveParam.getOutputPrice());
                storeUserFoodSku.setMinOrderCount(saveParam.getMinOrderCount());
                storeUserFoodSku.setBoxNum(foodSku.getBoxNum());
                storeUserFoodSku.setBoxPrice(foodSku.getBoxPrice());
                storeUserFoodSkuRepository.save(storeUserFoodSku);
                //将该规格设置为勾选规格(往后加)
                save.setSpecialSkuIdList(storeUserFoodSku.getFoodSkuId().toString());
                storeUserFoodRepository.save(save);
                LowPrice lowPrice = this.getStoreUserFoodPrice(save);
                save.setQuotePrice(lowPrice.getInputPrice());
                save.setSalePrice(lowPrice.getOutputPrice());
                storeUserFoodRepository.save(save);
            } else {
                //判断该商品是否含有已经存在的规格
                StoreUserFoodSku sku = storeUserFoodSkuRepository.findByStoreUserIdAndUpc(storeUser.getId(), foodSku.getUpc());
                sku.setStock(saveParam.getStock());
                sku.setInputPrice(saveParam.getInputPrice());
                sku.setOutputPrice(saveParam.getOutputPrice());
                sku.setSpec(foodSku.getSpec());
                sku.setInputTax(foodSku.getInputTax());
                sku.setOutputTax(foodSku.getOutputTax());
                sku.setMinOrderCount(foodSku.getMinOrderCount());
                sku.setBoxNum(foodSku.getBoxNum());
                sku.setBoxPrice(foodSku.getBoxPrice());
                storeUserFoodSkuRepository.save(sku);
                if (StringUtils.isNotBlank(storeUserFood.getSpecialSkuIdList())) {
                    storeUserFood.setSpecialSkuIdList(storeUserFood.getSpecialSkuIdList() + "," + sku.getFoodSkuId());
                } else {
                    storeUserFood.setSpecialSkuIdList(sku.getFoodSkuId().toString());
                }
                storeUserFoodRepository.save(storeUserFood);
                LowPrice lowPrice = this.getStoreUserFoodPrice(storeUserFood);
                storeUserFood.setQuotePrice(lowPrice.getInputPrice());
                storeUserFood.setSalePrice(lowPrice.getOutputPrice());
                storeUserFoodRepository.save(storeUserFood);
            }
        }
    }

    private void saveStoreUserFood(long storeUserId, List<SaveParam> foodList, Map<Long, Food> foodMap) {
        List<StoreUserFood> storeUserFoods = storeUserFoodRepository.findByStoreUserIdAndFoodIdIn(storeUserId,
                foodList.stream().map(o -> o.getFoodId()).collect(Collectors.toList()));
        Map<Long, StoreUserFood> foodMapByFoodId = new HashMap<>();
        StoreUser su = storeUserRepository.findOne(storeUserId);
        for (StoreUserFood suf : storeUserFoods) {
            foodMapByFoodId.put(suf.getFood().getId(), suf);
        }
        for (SaveParam param : foodList) {
            /**
             * 如果已经存在了，则不进行处理
             * */
            if (foodMapByFoodId.containsKey(param.getFoodId())) {
                continue;
            }
            Food food = foodMap.get(param.getFoodId());
            if (food == null) {
                continue;
            }
            StoreUserFood suf = getStoreUserFood(food, su, param);
            suf.setSale(false);
            suf.setCityId(suf.getStoreUser().getCity().getId());
            storeUserFoodRepository.save(suf);
        }

    }

    @Override
    public StoreUserFoodDTO saveStoreUserFood(long storeUserId, SaveParam param) {
        StoreUserFood storeUserFood = this.doSaveStoreUserFood(storeUserId, param);
        return toDTO(storeUserFood);
    }

    private StoreUserFood doSaveStoreUserFood(long storeUserId, SaveParam param) {
        StoreUserFood storeUserFood = storeUserFoodRepository.findByStoreUserIdAndFoodId(storeUserId, param.getFoodId());
        if (storeUserFood != null) {
            return storeUserFood;
        }
        Food food = foodRepository.findOne(param.getFoodId());
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        StoreUserFood suf = getStoreUserFood(food, storeUser, param);
        suf.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
        if (param.getFoodSupplierId() != null) {
            suf.setFoodSupplier(foodSupplierRepository.findOne(param.getFoodSupplierId()));
        }
        suf.setCityId(suf.getStoreUser().getCity().getId());
        storeUserFoodRepository.save(suf);
        return suf;
    }

    @Override
    public StoreUserFoodDTO saveSupplierStoreUserFood(long foodSupplierId, SaveParam saveParam) {
        FoodSupplier supplier = foodSupplierRepository.findOne(foodSupplierId);
        StoreUserFood storeUserFood = doSaveStoreUserFood(supplier.getStoreUser().getId(), saveParam);
        if (storeUserFood.getFoodSupplier() == null) {
            storeUserFood.setFoodSupplier(supplier);
            storeUserFoodRepository.save(storeUserFood);
        }
        return toDTO(storeUserFood);
    }

    private StoreUserFood getStoreUserFood(Food food, StoreUser su, SaveParam param) {
        StoreUserFood suf = new StoreUserFood();
        if ((param.getQuotePrice() == null || param.getQuotePrice() == 0) &&
                (param.getSalePrice() == null || param.getSalePrice() == 0)) {
            param.setQuotePrice(food.getPrice());
        }
        float increase = checkIncrease(food, su);
        suf.setPriceIncrease(food.getPerIncrease() > 0 ? food.getPerIncrease() : increase);
        if (param.getQuotePrice() != null) {
            suf.setQuotePrice(param.getQuotePrice());
            if (param.getSalePrice() == null || param.getSalePrice() == 0) {
                suf.setSalePrice(param.getQuotePrice() * (1 + increase / 100));
            } else {
                suf.setSalePrice(param.getSalePrice());
            }
        } else if (param.getSalePrice() != null) {
            suf.setQuotePrice(param.getSalePrice() / (1 + increase / 100));
            suf.setSalePrice(param.getSalePrice());
        }
        suf.setSale(param.getSale());
        suf.setCreateTime(new Date());
        suf.setMeituanPublishStatus(PublishStatus.WAIT);
        suf.setClbmPublishStatus(PublishStatus.WAIT);
        suf.setElePublishStatus(PublishStatus.WAIT);
        suf.setWantePublishStatus(PublishStatus.WAIT);
        suf.setJddjPublishStatus(PublishStatus.WAIT);
        suf.setFoodUnit(food.getQuoteUnit());
        suf.setStoreUser(su);
        suf.setUpdateTime(new Date());
        suf.setAlterQuotePrice(suf.getQuotePrice());
        suf.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
        suf.setFood(food);
        //suf.setFoodSkuJson(food.getSkuJson());
        suf.setFoodVersion(food.getVersion());
        return suf;
    }

    @Override
    public boolean copyStoreUserFood(long sourceStoreUserId, long targetStoreUserId) {
        int page = 0;
        int pageSize = 200;
        StoreUser targetStoreUser = storeUserRepository.findOne(targetStoreUserId);
        while (true) {
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            Pageable pageable = new PageRequest(page,
                    pageSize, sort);
            boolean flag = transactionTemplate.execute(status -> {

                Specification<StoreUserFood> specification = (root, query, cb) -> {
                    Predicate predicate = cb.conjunction();
                    if (query.getResultType() != Long.class) {
                        root.fetch("food", JoinType.LEFT);
                        root.fetch("storeUser", JoinType.LEFT);
                    }
                    List<Expression<Boolean>> expressions = predicate.getExpressions();
                    expressions.add(cb.equal(root.get("storeUser").get("id"), sourceStoreUserId));
                    expressions.add(cb.equal(root.get("quoteStatus"), QuoteStatus.VERIFY_SUCCESS));
                    expressions.add(cb.equal(root.get("sale"), true));
                    return predicate;
                };

                Page<StoreUserFood> pageInfo = storeUserFoodRepository.findAll(specification, pageable);
                for (StoreUserFood suf : pageInfo.getContent()) {
                    StoreUserFood targetSuf = storeUserFoodRepository.findByStoreUserIdAndFoodId(targetStoreUserId, suf.getFood().getId());
                    if (targetSuf != null) {
                        targetSuf.setQuoteStatus(suf.getQuoteStatus());
                        targetSuf.setAlterQuotePrice(suf.getAlterQuotePrice());
                        targetSuf.setSale(false);
                        targetSuf.setMeituanPublishStatus(PublishStatus.WAIT);
                        targetSuf.setClbmPublishStatus(PublishStatus.WAIT);
                        targetSuf.setElePublishStatus(PublishStatus.WAIT);
                        targetSuf.setWantePublishStatus(PublishStatus.WAIT);
                        targetSuf.setJddjPublishStatus(PublishStatus.WAIT);
                        targetSuf.setUpdateTime(new Date());
                    } else {
                        targetSuf = new StoreUserFood();
                        BeanUtils.copyProperties(suf, targetSuf);
                        targetSuf.setStoreUser(targetStoreUser);
                        targetSuf.setId(null);
                        targetSuf.setCityId(targetStoreUser.getCity().getId());
                        targetSuf.setCreateTime(new Date());
                        targetSuf.setUpdateTime(new Date());
                    }
                    targetSuf.setSale(true);
                    targetSuf.setElePhotos(new HashMap<>());
                    targetSuf.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
                    targetSuf.setElePublishStatus(targetStoreUser.getEleOpened() ? PublishStatus.WAIT : PublishStatus.IN_STOCK);
                    targetSuf.setMeituanPublishStatus(targetStoreUser.getMeituanOpened() ? PublishStatus.WAIT : PublishStatus.IN_STOCK);
                    targetSuf.setClbmPublishStatus(targetStoreUser.getClbmOpened() ? PublishStatus.WAIT : PublishStatus.IN_STOCK);
                    targetSuf.setWantePublishStatus(targetStoreUser.getWanteOpened() ? PublishStatus.WAIT : PublishStatus.IN_STOCK);
                    targetSuf.setJddjPublishStatus(targetStoreUser.getJddjOpened() ? PublishStatus.WAIT : PublishStatus.IN_STOCK);
                    storeUserFoodRepository.save(targetSuf);
                }
                return pageInfo.hasNext();
            });
            page++;
            if (!flag) {
                break;
            }
        }
        return true;
    }

    @Override
    public void batchResetQuoteStatus(BatchResetQuoteStatusParam param) {
        int pageSize = 100;
        param.setPageSize(pageSize);
        param.setSort(StoreUserFoodSort.ID_DESC);
        while (true) {
            Paging<StoreUserFoodDTO> pg = transactionTemplate.execute((status) -> {
                Paging<StoreUserFoodDTO> paging = search(param);
                for (StoreUserFoodDTO dto : paging.getResults()) {
                    StoreUserFood suf = storeUserFoodRepository.findOne(dto.getId());
                    suf.setQuoteStatus(param.getTargetQuoteStatus());
                    storeUserFoodRepository.save(suf);
                }
                if (paging.getResults().size() > 0) {
                    param.setMaxId(paging.getResults().get(paging.getResults().size() - 1).getId());
                }
                return paging;
            });
            if (!pg.getHasNext()) {
                break;
            }
        }
    }

    @Override
    public Result publishFoodCategory(Long storeUserId, Plat plat) {
        //查询出所有的分类
        List<FoodCategory> foodCategories = foodCategoryService.findIdSortedList();
        List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrueAndPlat(storeUserId, plat);
        Result rs = new Result();
        for (Store store : stores) {
            if (store.getPlat() == Plat.MEITUAN || store.getPlat() == Plat.CLBM) {
                List<String> exists = new ArrayList<>();
                List<String> scendExists = new ArrayList<>();
                List<String> allExits = new ArrayList<>();
                SystemParam param = null;
                if (store.getPlat() == Plat.MEITUAN) {
                    param = meituanWaimaiService.getSystemParam();
                } else {
                    param = clbmWaiMaiService.getSystemParam();
                }
                try {
                    List<RetailCatParam> cateList = APIFactory.getNewRetailApi().retailCatList(param, store.getCode());
                    exists.addAll(cateList.stream().map(o -> o.getName()).collect(Collectors.toList()));
                    //将二级分类也添加进去
                    for (RetailCatParam retailCatParam : cateList) {
                        List<RetailCatParam> children = retailCatParam.getChildren();
                        if (children != null && children.size() > 0) {
                            for (RetailCatParam child : children) {
                                scendExists.add(child.getName());
                            }
                        }
                    }
                    allExits.addAll(exists);
                    allExits.addAll(scendExists);
                } catch (ApiOpException e) {
                    logger.error(e.getMsg(), e);
                } catch (ApiSysException e) {
                    logger.error(e.getMessage(), e);
                }
                List<String> categories = new ArrayList<>();
                for (FoodCategory foodCategory : foodCategories) {
                    categories.add(foodCategory.getName());
                }
                for (String name : allExits) {
                    if (!categories.contains(name)) {
                        if ("未分类".equals(name)) {
                            continue;
                        }
                        //删除本地不存在但是美团上存在的类目（如果该分类在美团上存在子分类或者商品那么删除失败）
                        try {
                            APIFactory.getNewRetailApi().retailCatDelete(param, store.getCode(), name);
                        } catch (ApiOpException e) {
                            logger.warn(e.getMsg(), e);
                            rs.setMsg(e.getMsg() + " " + name);
                            return rs;
                        } catch (ApiSysException e) {
                            logger.error(e.getMessage());
                            logger.warn("API调用错误", e);
                            rs.setMsg(e.getLocalizedMessage() + " " + name);
                            return rs;
                        }
                    }
                }
                for (int i = 0; i < foodCategories.size(); i++) {
                    //SystemParam systemParam, String appPoiCode, String originCategoryName, String categoryName, String
                    //            secondaryCategoryName, Integer sequence
                    FoodCategory foodCategory = foodCategories.get(i);
                    if (foodCategory.getLevel() == 1) {
                        continue;
                    }
                    try {
                        //发布新的一级分类到美团
                        String str = APIFactory.getNewRetailApi().retailCatUpdate(param, store.getCode(),
                                exists.remove(foodCategory.getName()) ? foodCategory.getName() : null, foodCategory.getName(), null, foodCategory.getIdx());
                        logger.debug(str);
                    } catch (ApiOpException e) {
                        logger.error(e.getMessage());
                        logger.error(e.getMsg(), e);
                        rs.setMsg(e.getMsg());
                        return rs;
                    } catch (ApiSysException e) {
                        logger.error(e.getMessage());
                        rs.setMsg("API调用错误");
                        return rs;
                    }
                }
                for (int i = 0; i < foodCategories.size(); i++) {
                    //SystemParam systemParam, String appPoiCode, String originCategoryName, String categoryName, String
                    //            secondaryCategoryName, Integer sequence
                    FoodCategory foodCategory = foodCategories.get(i);
                    if (foodCategory.getLevel() == 0) {
                        continue;
                    }
                    FoodCategory parentCategory = foodCategoryService.getParentCategoryByName(foodCategory.getName());
                    try {
                        //发布新的二级分类到美团(可以更新二级分类的类别移动)
                        String str = APIFactory.getNewRetailApi().retailCatUpdate(param, store.getCode(),
                                parentCategory.getName(), parentCategory.getName(), foodCategory.getName(), foodCategory.getIdx());
                        logger.debug(str);
                    } catch (ApiOpException e) {
                        logger.error(e.getMessage());
                        logger.error(e.getMsg(), e);
                        rs.setMsg(e.getMsg());
                        return rs;
                    } catch (ApiSysException e) {
                        logger.error(e.getMessage());
                        rs.setMsg("API调用错误");
                        return rs;
                    }
                }


            } else if (store.getPlat() == Plat.ELE) {
                /**
                 * 先读取当前饿了么已经存在的类目
                 * */
                CategoryGetRequest getRequest = new CategoryGetRequest();
                getRequest.setShopId(store.getCode());
                //一级分类
                Map<String, CategoryGetResponse.Category> mappedByName = new HashMap<>(100);
                Map<Long, CategoryGetResponse.Category> mappedById = new HashMap<>(100);
                try {
                    CategoryGetResponse getRes = eleClient.request(getRequest);
                    for (CategoryGetResponse.Category category : getRes.getData().getCategorys()) {
                        mappedByName.put(category.getName(), category);
                        mappedById.put(category.getCategoryId(), category);
                        List<CategoryGetResponse.Category> children = category.getChildren();
                        if (children != null && children.size() > 0) {
                            for (CategoryGetResponse.Category child : children) {
                                mappedByName.put(child.getName(), child);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                Map<String, Long> eleCategoryMap = store.getEleCategoryMap();
                for (int i = 0; i < foodCategories.size(); i++) {
                    FoodCategory foodCategory = foodCategories.get(i);
                    if (mappedByName.containsKey(foodCategory.getName())) {
                        CategoryGetResponse.Category category = mappedByName.remove(foodCategory.getName());
                        CategoryUpdateRequest req = new CategoryUpdateRequest();
                        req.setName(foodCategory.getName());
                        req.setRank(1000 - foodCategory.getIdx());
                        req.setCategoryId(category.getCategoryId());
                        req.setShopId(store.getCode());
                        CategoryUpdateResponse res = eleClient.request(req);
                        eleCategoryMap.put(foodCategory.getId().toString(), category.getCategoryId());
                        if (res.getErrno() == 0) {
                            logger.info("饿了么分类更新成功");
                        } else {
                            // 发布失败
                            logger.error("饿了么分类发布失败，原因：" + res.getError());
                        }
                    } else {
                        CategoryCreateRequest req = new CategoryCreateRequest();
                        req.setName(foodCategory.getName());
                        req.setRank(1000 - foodCategory.getIdx());
                        if (foodCategory.getLevel() == 0) {
                            req.setParentCategoryId(0L);
                        } else if (foodCategory.getLevel() == 1) {
                            req.setParentCategoryId(eleCategoryMap.get(foodCategory.getParentId().toString()));
                        }

                        req.setShopId(store.getCode());
                        CategoryCreateResponse res = eleClient.request(req);
                        if (res.getErrno() == 0) {

                            eleCategoryMap.put(foodCategory.getId().toString(), res.getData().getCategoryId());
                            store.setEleCategoryMap(eleCategoryMap);
                            logger.error("饿了么分类新增成功");
                        } else {
                            // 发布失败
                            logger.error("饿了么分类发布失败，原因：" + res.getError());
                        }
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                store.setEleCategoryMap(eleCategoryMap);
                storeRepository.save(store);
            }
        }
        rs.setSuccess(true);
        return rs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyAndPublish(long storeUserFoodId) {
        this.verifyQuoteById(storeUserFoodId);
        publish(storeUserFoodId, true);
    }

    @Override
    public void alignStoreUserFood(AlignStoreUserFoodParam param) {
        SearchParam sp = new SearchParam();
        sp.setFoodCategoryName(param.getFoodCategoryName());
        sp.setStoreUserId(param.getSourceStoreUserId());
        sp.setPageSize(100);
        sp.setSort(StoreUserFoodSort.ID_DESC);
        while (true) {
            List<StoreUserFoodDTO> list = this.search(sp).getResults();
            if (list.size() > 0) {
                Map<Long, StoreUserFoodDTO> mapByFoodId = new HashMap(list.size());
                for (StoreUserFoodDTO dto : list) {
                    mapByFoodId.put(dto.getFood().getId(), dto);
                }
                sp.setMaxId(list.get(list.size() - 1).getId());
                List<Long> foodIdList = list.stream().map(suf -> suf.getFood().getId()).collect(Collectors.toList());
                List<StoreUserFood> targetList = storeUserFoodRepository.findByStoreUserIdAndFoodIdIn(param.getTargetStoreUserId(), foodIdList);
                List<StoreUserFood> waitSaveList = new ArrayList<>();
                for (StoreUserFood suf : targetList) {
                    if (!mapByFoodId.containsKey(suf.getFood().getId())) {
                        continue;
                    }
                    StoreUserFoodDTO dto = mapByFoodId.get(suf.getFood().getId());
                    suf.setPriceIncrease(dto.getPriceIncrease());
                    suf.setQuoteStatus(dto.getQuoteStatus());
                    suf.setQuotePrice(dto.getQuotePrice());
                    suf.setAlterQuotePrice(dto.getAlterQuotePrice());
                    if (suf.getQuoteStatus() == QuoteStatus.VERIFY_SUCCESS && suf.getSale()) {
                        suf.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
                    }
                    waitSaveList.add(suf);
                }
                transactionTemplate.execute(action -> {
                    storeUserFoodRepository.save(waitSaveList);
                    return true;
                });
            }
            if (list.size() < 100) {
                break;
            }
        }
    }

    private static String[] TITLE_LIST = new String[]{"商家", "商品ID", "商品名", "分类", "单位", "报价单位", "报价"};

    @Override
    public File export(SearchParam param) throws IOException {
        File excelFile = tmpFileService.createFile("门店商品_" + System.currentTimeMillis() + ".xls");
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
            //给单元格设置内容
            titleCell.setCellValue(TITLE_LIST[i]);
            titleCell.setCellStyle(foldStyle);
        }
        int page = 1;
        int pageSize = 100;
        int rowIndex = 1;
        param.setPageSize(pageSize);
        while (true) {
            Paging<StoreUserFoodDTO> paging = this.search(param);
            param.setPage(page++);
            for (StoreUserFoodDTO dto : paging.getResults()) {
                HSSFRow row = sheet.createRow(rowIndex++);
                row.setHeightInPoints(20);
                for (int i = 0; i < TITLE_LIST.length; i++) {
                    HSSFCell rowCell = row.createCell(i);
                    rowCell.setCellStyle(commonStyle);
                    switch (TITLE_LIST[i]) {
                        case "商家":
                            rowCell.setCellValue(dto.getStoreUser().getName());
                            break;
                        case "商品ID":
                            rowCell.setCellValue(dto.getId());
                            break;
                        case "商品名":
                            rowCell.setCellValue(dto.getFood().getName());
                            break;
                        case "分类":
                            rowCell.setCellValue(dto.getFood().getCategoryName());
                            break;
                        case "单位":
                            rowCell.setCellValue(dto.getFood().getUnit());
                            break;
                        case "报价单位":
                            rowCell.setCellValue(dto.getFood().getQuoteUnit());
                            break;
                        case "报价":
                            rowCell.setCellValue(dto.getQuotePrice());
                            break;
                    }
                }
            }
            if (!paging.getHasNext()) {
                break;
            }
        }
        workbook.write(excelFile);
        return excelFile;
    }

    private StoreUserFoodDTO toDTO(StoreUserFood suf) {
        StoreUserFoodDTO dto = new StoreUserFoodDTO();
        BeanUtils.copyProperties(suf, dto);
        dto.setFood(foodService.toDTO(suf.getFood()));
//        List<FoodSku> skuList = JSON.parseObject(suf.getFoodSkuJson(), new TypeReference<List<FoodSku>>() {
//        });
//        List<FoodSkuDTO> skuDTOS = new ArrayList<>();
//        for (FoodSku foodSku : skuList) {
//            FoodSkuDTO skuDTO = new FoodSkuDTO();
//            BeanUtils.copyProperties(foodSku, skuDTO);
//            if (StringUtils.isNotBlank(foodSku.getWarehouseIds())) {
//                List<String> names = new ArrayList<>();
//                String[] split = foodSku.getWarehouseIds().split(",");
//                for (String s : split) {
//                    Warehouse warehouse = warehouseRepository.findOne(Long.valueOf(s));
//                    names.add(warehouse.getName());
//                }
//                skuDTO.setWareHouseNames(String.join("、", names));
//            } else {
//                skuDTO.setWareHouseNames("暂未绑定库位");
//            }
//            skuDTOS.add(skuDTO);
//        }
        //dto.setSkuList(skuDTOS);
        StoreUserFoodDTO.StoreUser su = new StoreUserFoodDTO.StoreUser();
        su.setId(suf.getStoreUser().getId());
        su.setName(suf.getStoreUser().getName());
        su.setMeituanOpened(suf.getStoreUser().getMeituanOpened());
        su.setEleOpened(suf.getStoreUser().getEleOpened());
        su.setWanteOpened(suf.getStoreUser().getWanteOpened());
        su.setJddjOpened(suf.getStoreUser().getJddjOpened());
        su.setClbmOpened(suf.getStoreUser().getClbmOpened());
        if (suf.getFoodSupplier() != null) {
            FoodSupplierDTO foodSupplier = new FoodSupplierDTO();
            dto.setFoodSupplier(foodSupplier);
            foodSupplier.setId(suf.getFoodSupplier().getId());
            foodSupplier.setName(suf.getFoodSupplier().getName());
        }
        if (suf.getFoodQuoteReport() != null) {
            dto.setAvgQuotePrice(suf.getFoodQuoteReport().getAvgQuotePrice());
            dto.setMaxQuotePrice(suf.getFoodQuoteReport().getMaxQuotePrice());
            dto.setMinQuotePrice(suf.getFoodQuoteReport().getMinQuotePrice());
            dto.setWarningPrice(suf.getFoodQuoteReport().getWarningPrice());
            dto.setRefPrice(suf.getFoodQuoteReport().getRefPrice());
        }
//        if (StringUtils.isNotBlank(suf.getWarehouseIds())) {
//            String warehouseIds = suf.getWarehouseIds();
//            String[] split = warehouseIds.split(",");
//            List<String> names = new ArrayList<>();
//            for (String s : split) {
//                Warehouse warehouse = warehouseRepository.findOne(Long.valueOf(s));
//                names.add(warehouse.getName());
//            }
//            dto.setWarehouseName(String.join("、", names));
//        } else {
//            dto.setWarehouseName("暂未绑定库位");
//        }
        dto.setSkuList(storeUserFoodSkuService.getByStoreUserFoodId(suf.getId()));
        dto.setStoreUser(su);
        return dto;
    }
}
