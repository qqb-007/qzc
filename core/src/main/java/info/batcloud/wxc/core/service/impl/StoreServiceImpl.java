package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ctospace.archit.common.pagination.Paging;
import com.sankuai.meituan.banma.PeisongClient;
import com.sankuai.meituan.banma.request.ShopAreaQueryRequest;
import com.sankuai.meituan.banma.response.ShopAreaQueryResponse;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.PoiParam;
import com.sankuai.meituan.waimai.opensdk.vo.RetailCatParam;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import info.batcloud.wxc.core.config.MeituanPeisongApp;
import info.batcloud.wxc.core.domain.LocateInfo;
import info.batcloud.wxc.core.dto.StoreDTO;
import info.batcloud.wxc.core.entity.FoodCategory;
import info.batcloud.wxc.core.entity.Store;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.enums.StoreUserStatus;
import info.batcloud.wxc.core.enums.UuAccountType;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.CoordinateHelper;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.FoodCategoryRepository;
import info.batcloud.wxc.core.repository.StoreRepository;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.*;
import info.batcloud.wxc.core.settings.FeieyunSetting;
import info.batcloud.wxc.core.settings.FoodSetting;
import jd.sdk.JingdongClient;
import jd.sdk.JingdongzClient;
import jd.sdk.request.*;
import jd.sdk.response.*;
import me.ele.sdk.up.EleClient;
import me.ele.sdk.up.request.*;
import me.ele.sdk.up.response.*;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wante.sdk.up.WanteClient;
import wante.sdk.up.request.*;
import wante.sdk.up.response.*;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StoreServiceImpl implements StoreService {

    private Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private ClbmWaiMaiService clbmWaiMaiService;

    @Inject
    private MeituanWaimaiService meituanWaimaiService;

    @Inject
    private StoreUserRepository storeUserRepository;


    @Inject
    private FoodCategoryRepository foodCategoryRepository;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private EleClient eleClient;
    @Inject
    private WanteClient wanteClient;

    @Inject
    private JingdongClient jingdongClient;

    @Inject
    private JingdongzClient jingdongzClient;

    @Inject
    private MeituanPeisongApp meituanPeisongApp;

    @Inject
    private PeisongClient peisongClient;

    @Inject
    private FoodCategoryService foodCategoryService;

    @Override
    public boolean closeDeliverySelf(long id) {
        Store store = storeRepository.findOne(id);
        store.setDeliverySelf(false);
        storeRepository.save(store);
        return true;
    }

    @Override
    public boolean openDeliverySelf(long id) {
        Store store = storeRepository.findOne(id);
        store.setDeliverySelf(true);
        storeRepository.save(store);
        return true;
    }

    @Override
    public boolean openShow(long id) {
        Store store = storeRepository.findOne(id);
        store.setShowButton(true);
        storeRepository.save(store);
        return true;
    }

    @Override
    public boolean closeShow(long id) {
        Store store = storeRepository.findOne(id);
        store.setShowButton(false);
        storeRepository.save(store);
        return true;
    }

    @Override
    public boolean openLineStore(long id) {
        Store store = storeRepository.findOne(id);

        if (store.getPlat() == Plat.ELE) {
            ShopOpenRequest req = new ShopOpenRequest();
            req.setShopId(store.getCode());
            ShopOpenResponse res = eleClient.request(req);
            if (res.getErrno() != 0) {
//                throw new BizException(res.getError());
            }
        } else if (store.getPlat() == Plat.WANTE) {
            StoreOpenReq storeOpenReq = new StoreOpenReq();
            storeOpenReq.setId(Integer.valueOf(store.getCode()));
            StoreOpenRes execute = wanteClient.execute(storeOpenReq);
            if (execute != null && execute.getCode() == 0) {
                logger.info("开店成功");
            } else {
                logger.info("开店失败");
            }
        } else if (store.getPlat() == Plat.MEITUAN) {
            SystemParam systemParam = meituanWaimaiService.getSystemParam();
            try {
                String open = APIFactory.getPoiAPI().poiOpen(systemParam, store.getCode());
                logger.info(store.getName() + "开店返回");
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
        } else if (store.getPlat() == Plat.CLBM) {
            SystemParam systemParam = clbmWaiMaiService.getSystemParam();
            try {
                String open = APIFactory.getPoiAPI().poiOpen(systemParam, store.getCode());
                logger.info(store.getName() + "开店返回");
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
        }
//        store.setLineOpen(true);
//        storeRepository.save(store);
        return true;
    }

    @Override
    public boolean closeLineStore(long id) {
        Store store = storeRepository.findOne(id);

        if (store.getPlat() == Plat.ELE) {
            ShopCloseRequest request = new ShopCloseRequest();
            request.setShopId(store.getCode());
            ShopCloseResponse response = eleClient.request(request);
            logger.info(response.toString());
        } else if (store.getPlat() == Plat.WANTE) {
//            StoreOpenReq storeOpenReq = new StoreOpenReq();
//            storeOpenReq.setId(Integer.valueOf(store.getCode()));
//            StoreOpenRes execute = wanteClient.execute(storeOpenReq);
//            if (execute != null && execute.getCode() == 0) {
//                logger.info("开店成功");
//            } else {
//                logger.info("开店失败");
//            }
        } else if (store.getPlat() == Plat.MEITUAN) {
            SystemParam systemParam = meituanWaimaiService.getSystemParam();
            try {
                String open = APIFactory.getPoiAPI().poiClose(systemParam, store.getCode());
                logger.info(store.getName() + "关店返回");
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
        } else if (store.getPlat() == Plat.CLBM) {
            SystemParam systemParam = clbmWaiMaiService.getSystemParam();
            try {
                String open = APIFactory.getPoiAPI().poiClose(systemParam, store.getCode());
                logger.info(store.getName() + "关店返回");
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
        }
//        store.setLineOpen(true);
//        storeRepository.save(store);
        return true;
    }

    @Override
    public boolean toggleUuMix(long id) {
        Store store = storeRepository.findOne(id);
        store.setUuMix(store.getUuMix() == null ? true : !store.getUuMix());
        storeRepository.save(store);
        return store.getUuMix();
    }

    @Override
    public void updateUuTime(long id, float time) {
        Store store = storeRepository.findOne(id);
        store.setChangeUuTime(time);
        storeRepository.save(store);
    }

    @Override
    public void updateStorePic(String poiCodes, String imageUrl) {
        SystemParam param = meituanWaimaiService.getSystemParam();
        List<String> ids;
        if (StringUtils.isBlank(poiCodes)) {
            String poiIds = null;
            try {
                poiIds = APIFactory.getPoiAPI().poiGetIds(param);
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
            ids = JSON.parseObject(poiIds, new TypeReference<List<String>>() {
            });
        } else {
            ids = Arrays.asList(poiCodes.split(","));
        }

        for (String id : ids) {
            PoiParam param1 = new PoiParam();
            param1.setApp_poi_code(id);
            param1.setPic_url(imageUrl);
            //param1.setPromotion_info("欢迎您的光临！本店承诺: 明码标称 足斤足两 用心配菜 热心待客。");
            String result = null;
            try {
                result = APIFactory.getPoiAPI().poiSave(param, param1);
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
            logger.info(id + "更改结果" + result);
        }
        logger.info("门店更换图片完成");
    }

    @Override
    public void updateOpenStatus(String code, Plat plat, Boolean open) {
        Store store = storeRepository.findByCodeAndPlat(code, plat);
        if (store == null) {
            return;
        }
        if (plat == Plat.JDDJ) {
            if (open == true) {
                store.setLineOpen(true);
            } else {
                if (store.getLineOpen()) {
                    store.setLineOpen(false);
                    store.setCloseTime(System.currentTimeMillis() / 1000);
                }
            }
        } else {
            store.setLineOpen(open == null ? false : open);
            store.setCloseTime(System.currentTimeMillis() / 1000);
        }
        storeRepository.save(store);
        logger.info("同步 " + store.getName() + " 营业状态为" + store.getLineOpen());

    }


    @Override
    @Async
    public void publishCategoryToAll(FoodCategory foodCategory) {
        //将新建的分类发布到京东到家
        AddCategoryReq req = new AddCategoryReq();
        if (foodCategory.getLevel() == 0) {
            req.setPid(0l);
            req.setShopCategoryLevel(1);
        } else {
            FoodCategory parentCategory = foodCategoryService.getParentCategoryByName(foodCategory.getName());
            req.setPid(parentCategory.getJddjId());
            req.setShopCategoryLevel(2);
        }
        req.setShopCategoryName(foodCategory.getName());
        AddCategoryRes res = jingdongClient.request(req);
        if (res.getCode().equals("0")) {
            if (res.getData().getCode().equals("0")) {
                foodCategory.setJddjId(Long.valueOf(res.getData().getResult().getId()));
            }
        }
        if (foodCategory.getLevel() != 0) {
            FoodCategory parentCategory = foodCategoryService.getParentCategoryByName(foodCategory.getName());
            req.setPid(parentCategory.getJddjzId());
        }
        AddCategoryRes request = jingdongzClient.request(req);
        if (request.getCode().equals("0")) {
            if (request.getData().getCode().equals("0")) {
                foodCategory.setJddjzId(Long.valueOf(request.getData().getResult().getId()));
            }
        }
        foodCategoryRepository.save(foodCategory);
        List<Store> allStore = getAllStore();
        for (Store store : allStore) {
            if (store.getPlat() == Plat.MEITUAN || store.getPlat() == Plat.CLBM) {
                SystemParam param = null;
                if (store.getPlat() == Plat.MEITUAN) {
                    param = meituanWaimaiService.getSystemParam();
                } else {
                    param = clbmWaiMaiService.getSystemParam();
                }
                List<String> meiTuanCategory = getMeiTuanCategory(store);
                if (!meiTuanCategory.contains(foodCategory.getName())) {
                    try {
                        if (foodCategory.getLevel() == 0) {
                            //发布一级分类
                            String str = APIFactory.getNewRetailApi().retailCatUpdate(param, store.getCode(),
                                    meiTuanCategory.remove(foodCategory.getName()) ? foodCategory.getName() : null, foodCategory.getName(), null, foodCategory.getIdx());
                            logger.info(str + "发布美团分类" + foodCategory.getName() + "到" + store.getName() + "成功");
                        } else {
                            //发布二级分类
                            if (!meiTuanCategory.contains(foodCategory.getName())) {
                                FoodCategory parentCategory = foodCategoryService.getParentCategoryByName(foodCategory.getName());
                                //先判断是否存在其父类，没有的话先发布父类再发布子类
                                if (meiTuanCategory.contains(parentCategory.getName())) {
                                    String str = APIFactory.getNewRetailApi().retailCatUpdate(param, store.getCode(),
                                            parentCategory.getName(), parentCategory.getName(), foodCategory.getName(), foodCategory.getIdx());
                                    logger.info(str + "发布美团分类" + foodCategory.getName() + "到" + store.getName() + "成功");
                                } else {
                                    String str = APIFactory.getNewRetailApi().retailCatUpdate(param, store.getCode(),
                                            meiTuanCategory.remove(parentCategory.getName()) ? parentCategory.getName() : null, parentCategory.getName(), null, parentCategory.getIdx());
                                    logger.info(str + "发布美团分类" + foodCategory.getName() + "到" + store.getName() + "成功");
                                    String str1 = APIFactory.getNewRetailApi().retailCatUpdate(param, store.getCode(),
                                            parentCategory.getName(), parentCategory.getName(), foodCategory.getName(), foodCategory.getIdx());
                                    logger.info(str + "发布美团分类" + foodCategory.getName() + "到" + store.getName() + "成功");
                                }
                            }
                        }
                    } catch (ApiOpException e) {
                        e.printStackTrace();
                        logger.error(store.getName() + "美团发布分类失败" + foodCategory.getName());
                        logger.error(e.getMsg(), e);
                    } catch (ApiSysException e) {
                        e.printStackTrace();
                        logger.error("API调用错误");
                    }
                }


            } else if (store.getPlat() == Plat.ELE) {
                /**
                 * 先读取当前饿了么已经存在的类目
                 * */
                //发布一级分类
                try {
                    Map<String, CategoryGetResponse.Category> mappedByName = getEleCategory(store);
                    if (foodCategory.getLevel() == 0) {
                        if (!mappedByName.containsKey(foodCategory.getName())) {
                            publishCategoryToEle(store, foodCategory);
                        }
                    } else {
                        //发布二级分类
                        if (!mappedByName.containsKey(foodCategory.getName())) {
                            FoodCategory parentCategory = foodCategoryService.getParentCategoryByName(foodCategory.getName());
                            if (mappedByName.containsKey(parentCategory.getName())) {
                                //存在其父类，直接发布
                                publishCategoryToEle(store, foodCategory, parentCategory);
                            } else {
                                //不存在其父类，先发布父类再发布子类
                                if (publishCategoryToEle(store, parentCategory)) {
                                    publishCategoryToEle(store, foodCategory, parentCategory);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error(store.getName() + "饿了么发布分类出错" + foodCategory.getName());
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Async
    public void updateCategoryToAll(FoodCategory foodCategory, String originName) {
        //先更改京东到家类目名称
        UpdateCategoryReq req = new UpdateCategoryReq();
        req.setId(foodCategory.getJddjId());
        req.setShopCategoryName(foodCategory.getName());
        try {
            jingdongClient.request(req);
            req.setId(foodCategory.getJddjzId());
            jingdongzClient.request(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Store> allStore = getAllStore();
        for (Store store : allStore) {
            if (store.getPlat() == Plat.MEITUAN || store.getPlat() == Plat.CLBM) {
                SystemParam param = null;
                if (store.getPlat() == Plat.MEITUAN) {
                    param = meituanWaimaiService.getSystemParam();
                } else {
                    param = clbmWaiMaiService.getSystemParam();
                }
                List<String> meiTuanCategory = getMeiTuanCategory(store);
                if (meiTuanCategory.contains(originName)) {
                    try {
                        String str = APIFactory.getNewRetailApi().retailCatUpdate(param, store.getCode(), originName
                                , foodCategory.getName(), null, foodCategory.getIdx());
                        logger.info(str + "更新美团分类" + foodCategory.getName() + "到" + store.getName() + "成功");
                    } catch (ApiOpException e) {
                        logger.error(store.getName() + "美团更新分类失败" + foodCategory.getName());
                        e.printStackTrace();
                        logger.error(e.getMsg(), e);
                    } catch (ApiSysException e) {
                        e.printStackTrace();
                        logger.error("API调用错误");
                    }
                }

            } else if (store.getPlat() == Plat.ELE) {
                try {
                    Map<String, CategoryGetResponse.Category> eleCategory = getEleCategory(store);
                    if (eleCategory.containsKey(originName)) {
                        updateCategoryToEle(store, foodCategory);
                    }
                } catch (Exception e) {
                    logger.error(store.getName() + "饿百更新分类失败" + foodCategory.getName());
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    @Async
    public void deleteCategoryToAll(FoodCategory foodCategory) {
        //删除京东到家分类
        DelCategoryReq delReq = new DelCategoryReq();
        delReq.setId(foodCategory.getJddjId());
        DelCategoryRes delRes = jingdongClient.request(delReq);
        if (delRes.getCode().equals("0")) {
            logger.info("京东到家删除分类成功");
        } else {
            logger.error("京东到家删除分类失败" + delRes.getMsg());
        }
        delReq.setId(foodCategory.getJddjzId());
        DelCategoryRes delCategoryRes = jingdongzClient.request(delReq);
        if (delCategoryRes.getCode().equals("0")) {
            logger.info("京东到家子账号删除分类成功");
        } else {
            logger.error("京东到家子账号删除分类失败" + delCategoryRes.getMsg());
        }
        //删除自平台上的分类
        CategoryDeleteReq cdr = new CategoryDeleteReq();
        cdr.setId(foodCategory.getWanteId());
        CategoryDeleteRes execute = wanteClient.execute(cdr);
        if (execute != null && execute.getCode() != null && execute.getCode() == 0) {
            logger.info("删除自平台分类成功");
        } else {
            logger.error("删除自平台分类失败");
        }
        List<Store> allStore = getAllStore();
        for (Store store : allStore) {
            if (store.getPlat() == Plat.MEITUAN || store.getPlat() == Plat.CLBM) {
                SystemParam param = null;
                if (store.getPlat() == Plat.MEITUAN) {
                    param = meituanWaimaiService.getSystemParam();
                } else {
                    param = clbmWaiMaiService.getSystemParam();
                }
                List<String> meiTuanCategory = getMeiTuanCategory(store);
                if (meiTuanCategory.contains(foodCategory.getName())) {
                    try {
                        String str = APIFactory.getNewRetailApi().retailCatDelete(param, store.getCode(), foodCategory.getName());
                        logger.info(str + "删除美团分类" + foodCategory.getName() + "到" + store.getName() + "成功");
                    } catch (ApiOpException e) {
                        logger.error(store.getName() + "美团分类删除成功" + foodCategory.getName());
                        e.printStackTrace();
                    } catch (ApiSysException e) {
                        e.printStackTrace();
                    }
                }
            } else if (store.getPlat() == Plat.ELE) {
                try {
                    Map<String, CategoryGetResponse.Category> eleCategory = getEleCategory(store);
                    if (eleCategory.containsKey(foodCategory.getName())) {
                        CategoryGetResponse.Category category = eleCategory.get(foodCategory.getName());
                        CategorDeleteRequest req = new CategorDeleteRequest();
                        req.setCategoryId(category.getCategoryId());
                        req.setShopId(store.getCode());
                        CategoryDeleteResponse res = eleClient.request(req);
                        if (res.getErrno() == 0) {
                            Map<String, Long> eleCategoryMap = store.getEleCategoryMap();
                            eleCategoryMap.remove(foodCategory.getId().toString());
                            store.setEleCategoryMap(eleCategoryMap);
                            storeRepository.save(store);
                            logger.info("删除饿百分类" + foodCategory.getName() + "到" + store.getName() + "成功");
                        } else {
                            logger.error(res.getError());
                        }

                    }
                } catch (Exception e) {
                    logger.error(store.getName() + "饿百分类删除成功" + foodCategory.getName());
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private List<String> getMeiTuanCategory(Store store) {
        SystemParam param = null;
        if (store.getPlat() == Plat.MEITUAN) {
            param = meituanWaimaiService.getSystemParam();
        } else {
            param = clbmWaiMaiService.getSystemParam();
        }
        List<String> exists = new ArrayList<>();
        try {
            List<RetailCatParam> cateList = APIFactory.getNewRetailApi().retailCatList(param, store.getCode());
            exists.addAll(cateList.stream().map(o -> o.getName()).collect(Collectors.toList()));
            for (RetailCatParam retailCatParam : cateList) {
                List<RetailCatParam> children = retailCatParam.getChildren();
                if (children != null && children.size() > 0) {
                    for (RetailCatParam child : children) {
                        exists.add(child.getName());
                    }
                }

            }
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return exists;
    }

    private Map<String, CategoryGetResponse.Category> getEleCategory(Store store) {
        CategoryGetRequest getRequest = new CategoryGetRequest();
        getRequest.setShopId(store.getCode());
        Map<String, CategoryGetResponse.Category> mappedByName = new HashMap<>(100);
        CategoryGetResponse getRes = eleClient.request(getRequest);
        for (CategoryGetResponse.Category category : getRes.getData().getCategorys()) {
            mappedByName.put(category.getName(), category);
            //二级分类
            if (category.getChildren() != null && category.getChildren().size() > 0) {
                for (CategoryGetResponse.Category scendCategory : category.getChildren()) {
                    mappedByName.put(scendCategory.getName(), scendCategory);
                }
            }

        }
        return mappedByName;
    }

    private Boolean publishCategoryToEle(Store store, FoodCategory foodCategory) {
        Map<String, Long> eleCategoryMap = store.getEleCategoryMap();
        CategoryCreateRequest req = new CategoryCreateRequest();
        req.setName(foodCategory.getName());
        req.setRank(1000 - foodCategory.getIdx());
        req.setParentCategoryId(0L);
        req.setShopId(store.getCode());
        CategoryCreateResponse res = eleClient.request(req);
        if (res.getErrno() == 0) {
            eleCategoryMap.put(foodCategory.getId().toString(), res.getData().getCategoryId());
            store.setEleCategoryMap(eleCategoryMap);
            storeRepository.save(store);
            logger.info(store.getName() + "饿了么一级分类" + foodCategory.getName() + "新增成功");
            return true;
        } else {
            // 发布失败
            logger.error(store.getName() + "饿了么一级分类" + foodCategory.getName() + "发布失败，原因：" + res.getError());
            return false;
        }
    }

    private Boolean publishCategoryToEle(Store store, FoodCategory foodCategory, FoodCategory parentFoodCategory) {
        Map<String, Long> eleCategoryMap = store.getEleCategoryMap();
        CategoryCreateRequest req = new CategoryCreateRequest();
        req.setName(foodCategory.getName());
        req.setRank(1000 - foodCategory.getIdx());
        req.setParentCategoryId(eleCategoryMap.get(parentFoodCategory.getId().toString()));
        req.setShopId(store.getCode());
        CategoryCreateResponse res = eleClient.request(req);
        if (res.getErrno() == 0) {
            eleCategoryMap.put(foodCategory.getId().toString(), res.getData().getCategoryId());
            store.setEleCategoryMap(eleCategoryMap);
            storeRepository.save(store);
            logger.info(store.getName() + "饿了么二级分类" + foodCategory.getName() + "新增成功");
            return true;
        } else {
            // 发布失败
            logger.error(store.getName() + "饿了么二级分类" + foodCategory.getName() + "发布失败，原因：" + res.getError());
            return false;
        }
    }

    private Boolean updateCategoryToEle(Store store, FoodCategory foodCategory) {
        Map<String, Long> eleCategoryMap = store.getEleCategoryMap();
        CategoryUpdateRequest req = new CategoryUpdateRequest();
        req.setName(foodCategory.getName());
        req.setRank(1000 - foodCategory.getIdx());
        req.setCategoryId(eleCategoryMap.get(foodCategory.getId().toString()));
        req.setShopId(store.getCode());
        CategoryUpdateResponse res = eleClient.request(req);
        if (res.getErrno() == 0) {
            logger.info(store.getName() + "饿了么二级分类" + foodCategory.getName() + "更新成功");
            return true;
        } else {
            // 发布失败
            logger.error(store.getName() + "饿了么二级分类" + foodCategory.getName() + "发布失败，原因：" + res.getError());
            return false;
        }
    }

    private List<Store> getAllStore() {
        List<Store> allStores = new ArrayList<>();
        List<StoreUser> storeUsers = storeUserRepository.findByStatusAndOpened(StoreUserStatus.VALID, true);
        for (StoreUser storeUser : storeUsers) {
            List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrue(storeUser.getId());
            allStores.addAll(stores);
        }

        return allStores;
    }


    @Override
    public boolean syncFromMeituan(String poiCodes) {
        SystemParam param = meituanWaimaiService.getSystemParam();
        FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
        List<String> ids;
        if (StringUtils.isBlank(poiCodes)) {
            String poiIds = null;
            try {
                poiIds = APIFactory.getPoiAPI().poiGetIds(param);
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
            ids = JSON.parseObject(poiIds, new TypeReference<List<String>>() {
            });
        } else {
            ids = Arrays.asList(poiCodes.split(","));
        }
        int max = ids.size();
        int pageSize = 100;
        Stream.iterate(0, n -> n + 1).limit((max + pageSize - 1) / pageSize).forEach(i -> {
            logger.info("正在同步第" + i + "页");
            List<String> subIds = ids.stream().skip(i * pageSize).limit(pageSize).collect(Collectors.toList());
            List<PoiParam> poiParams = null;
            try {
                poiParams = APIFactory.getPoiAPI().poiMget(param, String.join(",", subIds));
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
            for (PoiParam poiParam : poiParams) {
                Store store = storeRepository.findByCodeAndPlat(poiParam.getApp_poi_code(), Plat.MEITUAN);
                if (store == null) {
                    store = new Store();
                    store.setPriceIncrease(foodSetting.getPerIncrease());
                    store.setShowButton(false);
                    store.setDeliverySelf(false);
                }
                store.setAddress(poiParam.getAddress());
                store.setCode(poiParam.getApp_poi_code());
                store.setName(poiParam.getName());
                store.setOnline(poiParam.getIs_online() == 1 ? true : false);
                store.setPhone(poiParam.getPhone());
                store.setPicUrl(poiParam.getPic_url());
                store.setPlat(Plat.MEITUAN);
                store.setOpening(store.getOnline());
                storeRepository.save(store);
            }
        });
        logger.info("门店同步成功");
        return true;
    }

    @Override
    public boolean syncFromClbm(String poiCodes) {
        SystemParam param = clbmWaiMaiService.getSystemParam();
        FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
        List<String> ids;
        if (StringUtils.isBlank(poiCodes)) {
            String poiIds = null;
            try {
                poiIds = APIFactory.getPoiAPI().poiGetIds(param);
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
            ids = JSON.parseObject(poiIds, new TypeReference<List<String>>() {
            });
        } else {
            ids = Arrays.asList(poiCodes.split(","));
        }
        int max = ids.size();
        int pageSize = 100;
        Stream.iterate(0, n -> n + 1).limit((max + pageSize - 1) / pageSize).forEach(i -> {
            logger.info("正在同步第" + i + "页");
            List<String> subIds = ids.stream().skip(i * pageSize).limit(pageSize).collect(Collectors.toList());
            List<PoiParam> poiParams = null;
            try {
                poiParams = APIFactory.getPoiAPI().poiMget(param, String.join(",", subIds));
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
            for (PoiParam poiParam : poiParams) {
                Store store = storeRepository.findByCodeAndPlat(poiParam.getApp_poi_code(), Plat.CLBM);
                if (store == null) {
                    store = new Store();
                    store.setPriceIncrease(foodSetting.getPerIncrease());
                    store.setShowButton(false);
                    store.setDeliverySelf(false);
                }
                store.setAddress(poiParam.getAddress());
                store.setCode(poiParam.getApp_poi_code());
                store.setName(poiParam.getName());
                store.setOnline(poiParam.getIs_online() == 1 ? true : false);
                store.setPhone(poiParam.getPhone());
                store.setPicUrl(poiParam.getPic_url());
                store.setPlat(Plat.CLBM);
                store.setOpening(store.getOnline());
                storeRepository.save(store);
            }
        });
        logger.info("门店同步成功");
        return true;
    }

    @Override
    public boolean syncListFromEle(Integer sysStatus) {
        ShopListRequest req = new ShopListRequest();
        req.setSysStatus(sysStatus);
        ShopListResponse response = eleClient.request(req);
        if (response.getErrno() != 0) {
            throw new BizException(response.getError());
        }
        for (ShopListResponse.Shop shop : response.getData()) {
            this.syncFromEle(shop.getShopId());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean syncFromEle(String shopId) {
        ShopGetRequest req = new ShopGetRequest();
        req.setShopId(shopId);
        ShopGetResponse response = eleClient.request(req);
        ShopGetResponse.Shop shop = response.getData();
        FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
        Store store = storeRepository.findByCodeAndPlat(shop.getShopId(), Plat.ELE);
        if (store == null) {
            store = new Store();
            store.setPriceIncrease(foodSetting.getPerIncrease());
            store.setOpening(false);
            store.setShowButton(false);
            store.setDeliverySelf(false);
        }
        store.setOnline(shop.getStatus() == 9 ? false : true);
        store.setAddress(shop.getAddress());
        store.setCode(shop.getShopId());
        store.setName(shop.getName());
        store.setPhone(shop.getServicePhone());
        store.setPicUrl(shop.getShopLogo());
        store.setPlat(Plat.ELE);
        storeRepository.save(store);
        return false;
    }

    @Override
    public boolean syncFromWante(String shopId) {
        StoreGetReq req = new StoreGetReq();
        req.setId(Integer.valueOf(shopId));
        StoreGetRes response = wanteClient.execute(req);
        StoreGetRes.Data shop = response.getData();
        FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
        Store store = storeRepository.findByCodeAndPlat(String.valueOf(shop.getId()), Plat.WANTE);
        if (store == null) {
            store = new Store();
            store.setPriceIncrease(foodSetting.getPerIncrease());
            store.setOpening(false);
            store.setShowButton(false);
            store.setDeliverySelf(false);
        }
        store.setOnline(shop.getEnable());
        store.setAddress(shop.getAddress() + shop.getDetailedAddress());
        store.setCode(String.valueOf(shop.getId()));
        store.setName(shop.getName());
        if (shop.getLogo() == null) {
            store.setPicUrl("");
        } else {
            store.setPicUrl(shop.getLogo());
        }
        store.setPhone(shop.getPhone());
        store.setPlat(Plat.WANTE);
        storeRepository.save(store);
        return false;
    }

    @Override
    public boolean syncFromJddj(String shopId, Boolean jd) {
        GetStoreInfoReq req = new GetStoreInfoReq();
        req.setStoreNo(shopId);
        GetStoreInfoRes res;
        if (jd) {
            res = jingdongClient.request(req);
        } else {
            res = jingdongzClient.request(req);
        }
        GetStoreInfoRes.StoreInfo result = res.getData().getResult();
        FoodSetting foodSetting = systemSettingService.findActiveSetting(FoodSetting.class);
        Store store = storeRepository.findByCodeAndPlat(result.getOutSystemId(), Plat.JDDJ);
        if (store == null) {
            store = new Store();
            store.setPriceIncrease(foodSetting.getPerIncrease());
            store.setOpening(false);
            store.setShowButton(false);
            store.setDeliverySelf(false);
        }
        store.setOnline(result.getCloseStatus() == 0 ? true : false);
        store.setAddress(result.getStationAddress());
        store.setCode(result.getOutSystemId());
        store.setName(result.getStationName());
        store.setPicUrl("");
        store.setPhone(result.getMobile());
        store.setPlat(Plat.JDDJ);
        store.setJd(jd);
        storeRepository.save(store);
        return true;
    }

    @Override
    public void bindStoreUser(long id, long storeUserId) {
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        Store store = storeRepository.findOne(id);
        store.setStoreUser(storeUser);
        storeRepository.save(store);
    }

    @Override
    public void setPriceIncrease(long id, float priceIncrease) {
        Store store = storeRepository.findOne(id);
        store.setPriceIncrease(priceIncrease);
        storeRepository.save(store);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setStoreDeliveryType(long id, DeliveryType type) {
        Store store = this.storeRepository.findOne(id);
        store.setDeliveryType(type);
        storeRepository.save(store);
    }

    @Override
    public void setStoreSecendDeliveryType(long id, DeliveryType type) {
        Store store = this.storeRepository.findOne(id);
        store.setSecondDeliveryType(type);
        storeRepository.save(store);
    }

    @Override
    public void setStoreUuAccountType(long id, UuAccountType type) {
        Store store = this.storeRepository.findOne(id);
        store.setUuAcountType(type);
        storeRepository.save(store);
    }

    @Override
    public boolean updateDeliveryArea(long id, String s) {
        String s1 = s.substring(0, s.length() - 1);
        String area = s1.substring(1, s1.length());
        Store store = this.storeRepository.findOne(id);
        StoreUser su = store.getStoreUser();
        Plat plat = store.getPlat();
        String[] split = area.split(",");
        List<Float> q = new ArrayList<>();
        List<Float> h = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (i % 2 != 0) {
                String replace = split[i].replace("]", "");
                h.add(Float.valueOf(replace));
            } else {
                String replace = split[i].replace("[", "");
                q.add(Float.valueOf(replace));
            }
        }
        if (plat == Plat.ELE) {


            ShopDeliveryinfoRequest request1 = new ShopDeliveryinfoRequest();
            request1.setShopId(store.getCode());
            ShopDeliveryinfoResponse response1 = eleClient.request(request1);
            DeliveryinfoSyncRequest request2 = new DeliveryinfoSyncRequest();
            request2.setProductId(response1.getData().get(0).getProductId().toString());
            request2.setShopId(store.getCode());
            DeliveryinfoSyncRequest.DeliveryAreas areas = new DeliveryinfoSyncRequest.DeliveryAreas();
            areas.setUuid(UUID.randomUUID().toString());
            areas.setAgentFee("500");
            areas.setAreaType("0");
            areas.setDeliveryPrice("2000");
            areas.setMaxWeight("100000");

            List<DeliveryinfoSyncRequest.Coordinates> coordinatesList = new ArrayList<>();

            for (int i = 0; i < q.size(); i++) {
                DeliveryinfoSyncRequest.Coordinates coordinates = new DeliveryinfoSyncRequest.Coordinates();
                coordinates.setLatitude(h.get(i).toString());
                coordinates.setLongitude(q.get(i).toString());
                coordinatesList.add(coordinates);
            }


            areas.setCoordinates(coordinatesList);
            List<DeliveryinfoSyncRequest.DeliveryAreas> areasList = new ArrayList<>();
            areasList.add(areas);
            request2.setDeliveryAreas(areasList);

            DeliveryinfoSyncResponse syncResponse = eleClient.request(request2);

            if (syncResponse.getErrno() == 0) {
                logger.info("设置配送范围返回：" + syncResponse.getError());
                return true;
            } else {
                logger.error("修改饿百门店的配送范围出错" + syncResponse.getError());
                throw new BizException("修改饿百门店的配送范围出错：" + syncResponse.getError());
            }


//            ShopUpdateRequest request = new ShopUpdateRequest();
//            request.setShopId(store.getCode());
//            request.setCoodType("amap");//高德经纬度
//            List<ShopUpdateRequest.DeliveryRegion> deliveryRegions = new ArrayList<>();
//            ShopUpdateRequest.DeliveryRegion deliveryRegion = new ShopUpdateRequest.DeliveryRegion();
//            deliveryRegion.setDeliveryFee(String.valueOf(su.getShippingFee() * 100));//饿百的配送费单位是分
//            deliveryRegion.setDeliveryTime(String.valueOf(60));//配送时长设置为60分钟
//            // deliveryRegion.setMinBuyFree("待确认");//已废弃
//            deliveryRegion.setName("饿百配送范围");
//            deliveryRegion.setMinOrderPrice(String.valueOf(su.getMinPrice() * 100));//起送价(饿百单位是分)
//            List<List<ShopUpdateRequest.Region>> regionss = new ArrayList<>();
//            List<ShopUpdateRequest.Region> regions = new ArrayList<>();
//            for (int i = 0; i < q.size(); i++) {
//                ShopUpdateRequest.Region region = new ShopUpdateRequest.Region();
//                region.setLatitude(h.get(i));
//                region.setLongitude(q.get(i));
//                regions.add(region);
//            }
//            regionss.add(regions);
//            deliveryRegion.setRegion(regionss);
//            deliveryRegions.add(deliveryRegion);
//            request.setDeliveryRegions(deliveryRegions);
//            ShopUpdateResponse response = eleClient.request(request);

        } else if (plat == Plat.MEITUAN || plat == Plat.CLBM) {
            SystemParam systemParam = null;
            if (store.getPlat() == Plat.MEITUAN) {
                systemParam = meituanWaimaiService.getSystemParam();
            } else {
                systemParam = clbmWaiMaiService.getSystemParam();
            }
            try {
                List<Map<String, Long>> areaList = new ArrayList<>();
                for (int i = 0; i < q.size(); i++) {
                    Map<String, Long> child = new HashMap<>();
                    areaList.add(child);
                    child.put("x", Double.valueOf(h.get(i) * 1000000).longValue());
                    child.put("y", Double.valueOf(q.get(i) * 1000000).longValue());
                }

                String rs = APIFactory.getShippingAPI().shippingSave(systemParam, store.getCode(), "default", "1",
                        JSON.toJSONString(areaList), su.getMinPrice().toString(), su.getShippingFee().toString());
                logger.info("设置配送范围返回：" + rs);
                return true;
            } catch (ApiOpException e) {
                logger.error("修改美团门店的配送范围出错", e.getMsg());
                throw new BizException("修改美团门店的配送范围出错：" + e.getMsg());
            } catch (ApiSysException e) {
                logger.error("修改美团门店的配送范围出错", e);
                throw new BizException("修改美团门店的配送范围出错：", e);
            }
        }
        return true;

    }

    @Override
    public boolean updateDeliveryAreaById(long id) {
        Store store = this.storeRepository.findOne(id);
        /*if (store.getPlat() != Plat.MEITUAN) {
            throw new BizException("仅支持美团门店修改配送范围");
        }*/
        StoreUser su = store.getStoreUser();
        if (su.getMinPrice() == null) {
            throw new BizException("未设置商家的起送价");
        }
        if (su.getShippingFee() == null) {
            throw new BizException("未设置商家的配送费");
        }
        String mtpsShopId = su.getMtpsShopId();
        ShopAreaQueryRequest req = new ShopAreaQueryRequest();
        req.setDeliveryServiceCode(meituanPeisongApp.getDeliveryServiceCode());
        req.setShopId(mtpsShopId);
        ShopAreaQueryResponse res = peisongClient.execute(req);
        if (!(res.getCode().equals("0"))) {
            throw new BizException(res.getMessage());
        }
        JSONObject jsonObject = JSON.parseObject(res.getData());
        JSONArray areas = jsonObject.getJSONArray("scope");
        if (areas.size() == 0) {
            throw new BizException("未查询到配送范围");
        }
        if (store.getPlat() == Plat.MEITUAN || store.getPlat() == Plat.CLBM) {
            SystemParam systemParam = null;
            if (store.getPlat() == Plat.MEITUAN) {
                systemParam = meituanWaimaiService.getSystemParam();
            } else {
                systemParam = clbmWaiMaiService.getSystemParam();
            }
            try {
//            /**
//             * 首先删除已经有的配送范围
//             * */
//            List<ShippingParam> shippingParams = APIFactory.getShippingAPI().shippingList(systemParam, store.getCode());
//            List<ShippingParam> shippingParamsFetch = APIFactory.getShippingAPI().shippingFetch(systemParam, store.getCode());
//            shippingParams.addAll(shippingParamsFetch);
//            if (shippingParams.size() > 0) {
//                for (ShippingParam shippingParam : shippingParams) {
//                    APIFactory.getShippingAPI().shippingDelete(systemParam, store.getCode(), shippingParam.getApp_shipping_code());
//                    Thread.sleep(600L);
//                }
//            }
                List<Map<String, Long>> areaList = new ArrayList<>();
                for (int i = 0; i < areas.size(); i++) {
                    JSONObject obj = areas.getJSONObject(i);
                    Map<String, Long> area = new HashMap<>();
                    areaList.add(area);
                    area.put("x", Double.valueOf(obj.getDoubleValue("x") * 1000000).longValue());
                    area.put("y", Double.valueOf(obj.getDoubleValue("y") * 1000000).longValue());
                }
                String rs = APIFactory.getShippingAPI().shippingSave(systemParam, store.getCode(), "default", "1",
                        JSON.toJSONString(areaList), su.getMinPrice().toString(), su.getShippingFee().toString());
                logger.info("设置配送范围返回：" + rs);
                return true;
            } catch (ApiOpException e) {
                logger.error("修改美团门店的配送范围出错", e.getMsg());
                throw new BizException("修改美团门店的配送范围出错：" + e.getMsg());
            } catch (ApiSysException e) {
                logger.error("修改美团门店的配送范围出错", e);
                throw new BizException("修改美团门店的配送范围出错：", e);
            }
        } else if (store.getPlat() == Plat.ELE) {


            ShopDeliveryinfoRequest request1 = new ShopDeliveryinfoRequest();
            request1.setShopId(store.getCode());
            ShopDeliveryinfoResponse response1 = eleClient.request(request1);
            DeliveryinfoSyncRequest request2 = new DeliveryinfoSyncRequest();
            request2.setProductId(response1.getData().get(0).getProductId().toString());
            request2.setShopId(store.getCode());
            DeliveryinfoSyncRequest.DeliveryAreas deliveryAreas = new DeliveryinfoSyncRequest.DeliveryAreas();
            deliveryAreas.setUuid(UUID.randomUUID().toString());
            deliveryAreas.setAgentFee("500");
            deliveryAreas.setAreaType("0");
            deliveryAreas.setDeliveryPrice("2000");
            deliveryAreas.setMaxWeight("100000");

            List<DeliveryinfoSyncRequest.Coordinates> coordinatesList = new ArrayList<>();

            for (int i = 0; i < areas.size(); i++) {
                JSONObject obj = areas.getJSONObject(i);
                DeliveryinfoSyncRequest.Coordinates coordinates = new DeliveryinfoSyncRequest.Coordinates();
                coordinates.setLatitude(String.valueOf(obj.getFloatValue("x")));
                coordinates.setLongitude(String.valueOf(obj.getFloatValue("y")));
                coordinatesList.add(coordinates);
            }


            deliveryAreas.setCoordinates(coordinatesList);
            List<DeliveryinfoSyncRequest.DeliveryAreas> areasList = new ArrayList<>();
            areasList.add(deliveryAreas);
            request2.setDeliveryAreas(areasList);

            DeliveryinfoSyncResponse syncResponse = eleClient.request(request2);

            if (syncResponse.getErrno() == 0) {
                logger.info("设置配送范围返回：" + syncResponse.getError());
                return true;
            } else {
                logger.error("修改饿百门店的配送范围出错" + syncResponse.getError());
                throw new BizException("修改饿百门店的配送范围出错：" + syncResponse.getError());
            }


//            ShopUpdateRequest request = new ShopUpdateRequest();
//            request.setShopId(store.getCode());
//            request.setCoodType("amap");//高德经纬度
//            List<ShopUpdateRequest.DeliveryRegion> deliveryRegions = new ArrayList<>();
//            ShopUpdateRequest.DeliveryRegion deliveryRegion = new ShopUpdateRequest.DeliveryRegion();
//            deliveryRegion.setDeliveryFee(String.valueOf(su.getShippingFee() * 100));//饿百的配送费单位是分
//            deliveryRegion.setDeliveryTime(String.valueOf(60));//配送时长设置为60分钟
//            // deliveryRegion.setMinBuyFree("待确认");//已废弃
//            deliveryRegion.setName("饿百配送范围");
//            deliveryRegion.setMinOrderPrice(String.valueOf(su.getMinPrice() * 100));//起送价(饿百单位是分)
//            List<List<ShopUpdateRequest.Region>> regionss = new ArrayList<>();
//            List<ShopUpdateRequest.Region> regions = new ArrayList<>();
//            for (int i = 0; i < areas.size(); i++) {
//                JSONObject obj = areas.getJSONObject(i);
//                ShopUpdateRequest.Region region = new ShopUpdateRequest.Region();
//                region.setLatitude(obj.getFloatValue("x"));
//                region.setLongitude(obj.getFloatValue("y"));
//                regions.add(region);
//            }
//            regionss.add(regions);
//            deliveryRegion.setRegion(regionss);
//            deliveryRegions.add(deliveryRegion);
//            request.setDeliveryRegions(deliveryRegions);
//            ShopUpdateResponse response = eleClient.request(request);
//            if (response.getErrno() == 0) {
//                logger.info("设置配送范围返回：" + response.getError());
//                return true;
//            } else {
//                logger.error("修改饿百门店的配送范围出错" + response.getError());
//                throw new BizException("修改饿百门店的配送范围出错：" + response.getError());
//            }
        } else if (store.getPlat() == Plat.WANTE) {
            StoreDeliveryUpdateReq sreq = new StoreDeliveryUpdateReq();
            sreq.setStoreId(Integer.valueOf(store.getCode()));
            List<StoreDeliveryUpdateReq.UpdateInfo> updateInfos = new ArrayList<>();
            for (int i = 0; i < areas.size(); i++) {
                JSONObject obj = areas.getJSONObject(i);
                StoreDeliveryUpdateReq.UpdateInfo updateInfo = new StoreDeliveryUpdateReq.UpdateInfo();
                LocateInfo locateInfo = CoordinateHelper.gcj02_To_Wgs84(obj.getDoubleValue("x"), obj.getDoubleValue("y"));
                updateInfo.setLongitude(locateInfo.getLongitude());
                updateInfo.setLatitude(locateInfo.getLatitude());
                updateInfo.setAddress("");
                updateInfos.add(updateInfo);
            }
            sreq.setUpdateInfo(updateInfos);
            wanteClient.execute(sreq);
            return true;
        } else if (store.getPlat() == Plat.JDDJ) {
            UpdateStoreInfoReq updateStoreInfoReq = new UpdateStoreInfoReq();
            updateStoreInfoReq.setStationNo(store.getCode());
            updateStoreInfoReq.setOperator("王小菜");
            updateStoreInfoReq.setDeliveryRangeType((byte) 2);
            updateStoreInfoReq.setCoordinateType(3);
            StringBuilder area = new StringBuilder();
            for (int i = 0; i < areas.size(); i++) {
                JSONObject obj = areas.getJSONObject(i);
                double x = obj.getDoubleValue("x");
                double y = obj.getDoubleValue("y");
                area.append(y);
                area.append("," + x + ";");
            }
            updateStoreInfoReq.setCoordinatePoints(area.toString());
            if (store.getJd()) {
                UpdateStoreInfoRes infoRes = jingdongClient.request(updateStoreInfoReq);
            } else {
                UpdateStoreInfoRes infoRes = jingdongzClient.request(updateStoreInfoReq);
            }
            return true;
        }
        return true;
    }

    @Override
    public Paging<StoreDTO> search(SearchParam param) {
        Specification<Store> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (query.getResultType() != Long.class) {
                root.fetch("storeUser", JoinType.LEFT);
            }
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (param.getPlat() != null) {
                expressions.add(cb.equal(root.get("plat"), param.getPlat()));
            }
            if (param.getOpening() != null) {
                expressions.add(cb.equal(root.get("opening"), param.getOpening()));
            }
            if (param.getLineOpen() != null) {
                expressions.add(cb.equal(root.get("lineOpen"), param.getLineOpen()));
            }
            if (param.getShowButton() != null) {
                expressions.add(cb.equal(root.get("showButton"), param.getShowButton()));
            }
            if (param.getDeliverySelf() != null) {
                expressions.add(cb.equal(root.get("deliverySelf"), param.getDeliverySelf()));
            }
            if (param.getCloseDayNum() != null && param.getCloseDayNum() > 0) {
                long time = System.currentTimeMillis() / 1000;
                long stime = time - (param.getCloseDayNum() * 24 * 60 * 60);
                long etime = stime + 24 * 60 * 60;
                //关店时间在指定时间内
                expressions.add(cb.between(root.get("closeTime"), stime, etime));
            }
            if (StringUtils.isNotBlank(param.getName())) {
                expressions.add(cb.like(root.get("name"), "%" + param.getName() + "%"));
            }
            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (StringUtils.isNotEmpty(param.getPhone())) {
                expressions.add(cb.equal(root.get("phone"), param.getPhone()));
            }
            if (param.getDeliveryType() != null) {
                expressions.add(cb.equal(root.get("deliveryType"), param.getDeliveryType()));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<Store> page = storeRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item), param.getPage(), param.getPageSize());
    }

    @Override
    public StoreDTO toDTO(Store store) {
        return toDTO(store, true);
    }

    @Override
    public StoreDTO toDTO(Store store, boolean fetchStoreUser) {
        StoreDTO dto = new StoreDTO();
        BeanUtils.copyProperties(store, dto);
        if (fetchStoreUser) {
            dto.setStoreUser(storeUserService.toDTO(store.getStoreUser()));
        }
        return dto;
    }

    @Override
    public void openStore(long id) {
        Store store = storeRepository.findOne(id);
        store.setOpening(true);
        if (store.getPlat() == Plat.ELE) {
            ShopOpenRequest req = new ShopOpenRequest();
            req.setShopId(store.getCode());
            ShopOpenResponse res = eleClient.request(req);
            if (res.getErrno() != 0) {
//                throw new BizException(res.getError());
            }
        } else if (store.getPlat() == Plat.WANTE) {
            StoreOpenReq storeOpenReq = new StoreOpenReq();
            storeOpenReq.setId(Integer.valueOf(store.getCode()));
            StoreOpenRes execute = wanteClient.execute(storeOpenReq);
            if (execute != null && execute.getCode() == 0) {
                logger.info("开店成功");
            } else {
                logger.info("开店失败");
            }
        }
        storeRepository.save(store);
    }

    @Override
    public void closeStore(long id) {
        Store store = storeRepository.findOne(id);
        if (store.getPlat() == Plat.WANTE) {
            StoreRestReq storeRestReq = new StoreRestReq();
            storeRestReq.setId(Integer.valueOf(store.getCode()));
            StoreRestRes execute = wanteClient.execute(storeRestReq);
            if (execute != null && execute.getCode() == 0) {
                logger.info("关店成功");
            } else {
                logger.info("关店失败");
            }
        }
        store.setOpening(false);
        storeRepository.save(store);
    }

    @Override
    public String findPrinterSnByStoreCode(String code, Plat plat) {
        Store store = storeRepository.findByCodeAndPlat(code, plat);
        String sn = null;
        if (store.getStoreUser() != null) {
            sn = store.getStoreUser().getFeiePrinterSn();
        }
        if (StringUtils.isNotEmpty(sn)) {
            return sn;
        }
        FeieyunSetting setting = systemSettingService.findActiveSetting(FeieyunSetting.class);
        return setting.getSn();
    }
}
