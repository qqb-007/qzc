package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.dto.StoreUserFoodDTO;
import info.batcloud.wxc.core.entity.StoreUserFood;
import info.batcloud.wxc.core.enums.*;
import org.apache.xpath.operations.Bool;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StoreUserFoodService {

    //List<StoreUserFoodDTO> getWhSufList(Long wid);

    void syncStock(long id);

    void addSkus(long foodId);

    StoreUserFoodDTO findById(Long id);

    //void updateStock(long id, List<String> skuIds, List<Integer> stock);

    void batchSetSaleTime(BatchSaleTimeParam batchSaleTime);

    void batchUpdateTips(Plat plat);

    void updateTips(long storeId);

    void batchDiscount(List<Long> foodId, List<Long> stoerUserId, List<Plat> platList, String name, Float fullPrice, Float reducePrice);

    void batchXm(List<Long> foodId, List<Long> stoerUserId, List<Plat> platList, String name, Double actPrice, Integer actNum);

    void batchCouponDiscount(List<Long> foodId, List<Long> stoerUserId, Boolean allStore, List<Plat> platList, String name, Float fullPrice, Float reducePrice);

    List<Integer> countCategoryStoreUserFood(long storeUserId, String category);


    void updataElePrice(int index);

    void setSaleTime(Long id, String saleTime);

    Map<String, String> getSaleTime(Long id);

    //批量下架单个商品
    void batchSoldOutByFoodId(Long foodId, List<Plat> plats);

    void batchSoldOutCategory(BatchSoldCategoryParam param);

    void batchChangePrice(float start, float end, float change);

    void updateVideoByFoodId(Long foodId, boolean delete, String video);

    void batchCheckPlatFood(BatchCheckPlatFoodParam platFoodParam);

    void checkStoreUserFood(long shopId);

    void batchActive(List<Long> foodId, Boolean allUser, List<Long> stoerUserId, List<Plat> platList, Integer activeType, Float increase);

    StoreUserFoodDTO publishActive(long id, boolean checkExists, List<Plat> platList, Integer activeType, Float priceIncrease);

    void batchResetStatus(long storeUserId, List<Plat> platList);

    //下架京东门店商品
    void batchSoldOutJddj(List<Long> storeUserId);

    void setMinOrderCount(Long id, Integer count);

    boolean resetEleStatus(Long id);

    List<StoreUserFoodDTO> findByStoreUserIdAndFoodCodeList(long storeUserId, List<String> foodCodeList);

    StoreUserFoodDTO findByStoreUserIdAndFoodId(long storeUserId, long foodId);

    StoreUserFoodDTO findByStoreUserIdAndId(long storeUserId, long id);

    void setFoodSupplier(long id, long foodSupplierId);

    /**
     * 下架
     */
    boolean soldOut(long storeUserId, long foodId);

    void changePriceIncrease(IncreaseChangeParam param);

    void batchChangeQuotePrice(QuoteChangeParam param);

    void lockQuotePrice(Long id, float quotePrice, Date unlockTime);

    Boolean unlockQuotePrice(Long id);

    void batchLockQuotePrice(BatchLockParam param);

    void batchChangePrice(BatchChangePriceParam param);

    void batchChangePriceIncrease(BatchChangePriceParam param);

    void batchUnlockQuotePrice(BatchLockParam param);

    void soldOutByFoodId(long foodId);

    boolean sale(long storeUserId, long foodId);

    boolean soldOutById(long storeUserFoodId);

    boolean soldOutPlatById(long storeUserFoodId, Plat plat);

    StoreUserFoodDTO changeQuotePrice(long id, float quotePrice);

    StoreUserFoodDTO changePriceIncrease(long id, float priceIncrease);

    StoreUserFoodDTO changeAlterQuotePrice(long storeUserId, long id, float alterQuotePrice);

    List<String> aggregateCategoryOfFoodSupplier(long foodSupplierId);

    StoreUserFoodDTO backgroundPublish(long id, boolean checkExists, List<Plat> list);

    /**
     * 供应商修改报价
     *
     * @param foodSupplierId 供应商id
     * @param id             商品id
     * @param quotePrice     报价
     * @return {@link StoreUserFoodDTO}
     */
    StoreUserFoodDTO changeSupplierAlterQuotePrice(long foodSupplierId, long id, float quotePrice);

    /**
     * 供应商修改报价
     *
     * @param id         商品id
     * @param quotePrice 报价
     * @return {@link StoreUserFoodDTO}
     */
    StoreUserFoodDTO changeSupplierAlterQuotePrice(long id, float alterQuotePrice);

    StoreUserFoodDTO changeSupplierIncrease(long id, float supplierIncrease);

    Paging<StoreUserFoodDTO> search(SearchParam param);

    StoreUserFoodDTO publish(long id, boolean checkExists, List<Plat> platList);

    StoreUserFoodDTO publish(long id, boolean checkExists);

    StoreUserFoodDTO verifyQuoteById(long id);

    void updatePublishByStoreUserId(long storeUserId);

    void proofreadSoldOutStoreUserFood(long storeUserId);

    void deleteById(long id);

    void batchDelete(BatchDeleteParam param);

    void setCategory(long id, long categoryId);

    void clearCategory(long id);

    void batchPublish(BatchPublishParam param);

    void setSpecialSkuList(long id, List<String> skuIdList);

    void setEleSkuId(long id, String skuId);

    void batchVerify(BatchVerifyParam param);

    void detectAllChange();

    void detectChangeByStoreUserIdList(List<Long> storeUserIdList);

    void resetByFoodId(Long id);

    void resetStoreUserPublish(long storeUserId, List<Plat> platList);

    void resetStoreUserFoodPublish(long storeUserId, List<Long> foodIdList, List<Plat> platList);

    void batchSaveStoreUserFood(BatchSaveParam param);

    void batchSaveNew(BatchAddNewParam param);

    StoreUserFoodDTO saveStoreUserFood(long storeUserId, SaveParam saveParam);

    StoreUserFoodDTO saveSupplierStoreUserFood(long foodSupplierId, SaveParam saveParam);

    boolean copyStoreUserFood(long sourceStoreUserId, long targetStoreUserId);

    void batchResetQuoteStatus(BatchResetQuoteStatusParam param);

    Result publishFoodCategory(Long storeUserId, Plat plat);

    void verifyAndPublish(long storeUserFoodId);

    void alignStoreUserFood(AlignStoreUserFoodParam param);

    File export(SearchParam param) throws IOException;

    LowPrice getStoreUserFoodPrice(StoreUserFood storeUserFood);

    class LowPrice {
        Float inputPrice;
        Float outputPrice;

        public Float getInputPrice() {
            return inputPrice;
        }

        public void setInputPrice(Float inputPrice) {
            this.inputPrice = inputPrice;
        }

        public Float getOutputPrice() {
            return outputPrice;
        }

        public void setOutputPrice(Float outputPrice) {
            this.outputPrice = outputPrice;
        }
    }

    class BatchAddNewParam {
        private List<Long> storeUserId;
        private List<SaveNewParam> foodList;

        public List<Long> getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(List<Long> storeUserId) {
            this.storeUserId = storeUserId;
        }

        public List<SaveNewParam> getFoodList() {
            return foodList;
        }

        public void setFoodList(List<SaveNewParam> foodList) {
            this.foodList = foodList;
        }
    }

    class SaveNewParam{
        private Long skuId;
        private Float inputPrice;
        private Float outputPrice;
        private Integer minOrderCount;
        private Integer stock;

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }

        public Float getInputPrice() {
            return inputPrice;
        }

        public void setInputPrice(Float inputPrice) {
            this.inputPrice = inputPrice;
        }

        public Float getOutputPrice() {
            return outputPrice;
        }

        public void setOutputPrice(Float outputPrice) {
            this.outputPrice = outputPrice;
        }

        public Integer getMinOrderCount() {
            return minOrderCount;
        }

        public void setMinOrderCount(Integer minOrderCount) {
            this.minOrderCount = minOrderCount;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }
    }

    class AlignStoreUserFoodParam {
        private Long targetStoreUserId;
        private Long sourceStoreUserId;
        private String foodCategoryName;

        public Long getTargetStoreUserId() {
            return targetStoreUserId;
        }

        public void setTargetStoreUserId(Long targetStoreUserId) {
            this.targetStoreUserId = targetStoreUserId;
        }

        public Long getSourceStoreUserId() {
            return sourceStoreUserId;
        }

        public void setSourceStoreUserId(Long sourceStoreUserId) {
            this.sourceStoreUserId = sourceStoreUserId;
        }

        public String getFoodCategoryName() {
            return foodCategoryName;
        }

        public void setFoodCategoryName(String foodCategoryName) {
            this.foodCategoryName = foodCategoryName;
        }
    }

    class IncreateChangeByPrice {
        private float start;
        private float end;
        private float change;

        public float getStart() {
            return start;
        }

        public void setStart(float start) {
            this.start = start;
        }

        public float getEnd() {
            return end;
        }

        public void setEnd(float end) {
            this.end = end;
        }

        public float getChange() {
            return change;
        }

        public void setChange(float change) {
            this.change = change;
        }
    }

    class BatchSoldCategoryParam {
        private String foodCategoryName;
        private List<Long> storeUserIdList;

        public String getFoodCategoryName() {
            return foodCategoryName;
        }

        public void setFoodCategoryName(String foodCategoryName) {
            this.foodCategoryName = foodCategoryName;
        }

        public List<Long> getStoreUserIdList() {
            return storeUserIdList;
        }

        public void setStoreUserIdList(List<Long> storeUserIdList) {
            this.storeUserIdList = storeUserIdList;
        }
    }

    class QuoteChangeParam {
        private Float increase;
        private String foodCategoryName;
        private List<Long> storeUserIdList;

        public Float getIncrease() {
            return increase;
        }

        public void setIncrease(Float increase) {
            this.increase = increase;
        }

        public String getFoodCategoryName() {
            return foodCategoryName;
        }

        public void setFoodCategoryName(String foodCategoryName) {
            this.foodCategoryName = foodCategoryName;
        }

        public List<Long> getStoreUserIdList() {
            return storeUserIdList;
        }

        public void setStoreUserIdList(List<Long> storeUserIdList) {
            this.storeUserIdList = storeUserIdList;
        }
    }

    class IncreaseChangeParam {
        private float originIncrease;
        private String foodCategoryName;
        private float increase;
        private List<Long> storeUserIdList;

        public String getFoodCategoryName() {
            return foodCategoryName;
        }

        public void setFoodCategoryName(String foodCategoryName) {
            this.foodCategoryName = foodCategoryName;
        }

        public List<Long> getStoreUserIdList() {
            return storeUserIdList;
        }

        public void setStoreUserIdList(List<Long> storeUserIdList) {
            this.storeUserIdList = storeUserIdList;
        }

        public float getOriginIncrease() {
            return originIncrease;
        }

        public void setOriginIncrease(float originIncrease) {
            this.originIncrease = originIncrease;
        }

        public float getIncrease() {
            return increase;
        }

        public void setIncrease(float increase) {
            this.increase = increase;
        }
    }

    class ChangeDetectParam {
        private Boolean detectPriceIncrease;
        private Boolean detectFoodChange;

        public Boolean getDetectPriceIncrease() {
            return detectPriceIncrease;
        }

        public void setDetectPriceIncrease(Boolean detectPriceIncrease) {
            this.detectPriceIncrease = detectPriceIncrease;
        }

        public Boolean getDetectFoodChange() {
            return detectFoodChange;
        }

        public void setDetectFoodChange(Boolean detectFoodChange) {
            this.detectFoodChange = detectFoodChange;
        }
    }

    class BatchLockParam {
        private List<Long> storeUserIdList;
        private boolean allStoreUser;
        private List<LockParam> foodList;

        public boolean isAllStoreUser() {
            return allStoreUser;
        }

        public List<LockParam> getFoodList() {
            return foodList;
        }

        public void setFoodList(List<LockParam> foodList) {
            this.foodList = foodList;
        }

        public List<Long> getStoreUserIdList() {
            return storeUserIdList;
        }

        public void setStoreUserIdList(List<Long> storeUserIdList) {
            this.storeUserIdList = storeUserIdList;
        }

        public boolean getAllStoreUser() {
            return allStoreUser;
        }

        public void setAllStoreUser(boolean allStoreUser) {
            this.allStoreUser = allStoreUser;
        }

    }

    class BatchSaleTimeParam {
        private List<Long> storeUserIdList;
        private boolean allStoreUser;
        private List<LockParam> foodList;
        private List<String> weeks;
        private String times;

        public List<String> getWeeks() {
            return weeks;
        }

        public void setWeeks(List<String> weeks) {
            this.weeks = weeks;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public boolean isAllStoreUser() {
            return allStoreUser;
        }

        public List<LockParam> getFoodList() {
            return foodList;
        }

        public void setFoodList(List<LockParam> foodList) {
            this.foodList = foodList;
        }

        public List<Long> getStoreUserIdList() {
            return storeUserIdList;
        }

        public void setStoreUserIdList(List<Long> storeUserIdList) {
            this.storeUserIdList = storeUserIdList;
        }

        public boolean getAllStoreUser() {
            return allStoreUser;
        }

        public void setAllStoreUser(boolean allStoreUser) {
            this.allStoreUser = allStoreUser;
        }

    }

    class LockParam {
        private Long foodId;
        private Float quotePrice;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        private Date startDate;

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public Float getQuotePrice() {
            return quotePrice;
        }

        public void setQuotePrice(Float quotePrice) {
            this.quotePrice = quotePrice;
        }
    }

    class BatchCheckPlatFoodParam {
        private List<Long> storeUserIdList;
        private boolean allStoreUser;

        public List<Long> getStoreUserIdList() {
            return storeUserIdList;
        }

        public void setStoreUserIdList(List<Long> storeUserIdList) {
            this.storeUserIdList = storeUserIdList;
        }

        public boolean isAllStoreUser() {
            return allStoreUser;
        }

        public void setAllStoreUser(boolean allStoreUser) {
            this.allStoreUser = allStoreUser;
        }
    }

    class BatchChangePriceParam {
        private List<Long> storeUserIdList;
        private boolean allStoreUser;
        private List<ChangePriceParam> foodList;

        public List<Long> getStoreUserIdList() {
            return storeUserIdList;
        }

        public void setStoreUserIdList(List<Long> storeUserIdList) {
            this.storeUserIdList = storeUserIdList;
        }

        public boolean isAllStoreUser() {
            return allStoreUser;
        }

        public void setAllStoreUser(boolean allStoreUser) {
            this.allStoreUser = allStoreUser;
        }

        public List<ChangePriceParam> getFoodList() {
            return foodList;
        }

        public void setFoodList(List<ChangePriceParam> foodList) {
            this.foodList = foodList;
        }
    }

    class ChangePriceParam {
        private Long foodId;
        private Float quotePrice;
        private Float perIncrease;

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public Float getQuotePrice() {
            return quotePrice;
        }

        public void setQuotePrice(Float quotePrice) {
            this.quotePrice = quotePrice;
        }

        public Float getPerIncrease() {
            return perIncrease;
        }

        public void setPerIncrease(Float perIncrease) {
            this.perIncrease = perIncrease;
        }
    }

    class BatchSaveParam {
        private List<Long> storeUserIdList;
        private boolean allStoreUser;
        private List<Long> excludeStoreUserIdList;
        private List<SaveParam> foodList;

        public boolean isAllStoreUser() {
            return allStoreUser;
        }

        public List<SaveParam> getFoodList() {
            return foodList;
        }

        public void setFoodList(List<SaveParam> foodList) {
            this.foodList = foodList;
        }

        public List<Long> getStoreUserIdList() {
            return storeUserIdList;
        }

        public void setStoreUserIdList(List<Long> storeUserIdList) {
            this.storeUserIdList = storeUserIdList;
        }

        public boolean getAllStoreUser() {
            return allStoreUser;
        }

        public void setAllStoreUser(boolean allStoreUser) {
            this.allStoreUser = allStoreUser;
        }

        public List<Long> getExcludeStoreUserIdList() {
            return excludeStoreUserIdList;
        }

        public void setExcludeStoreUserIdList(List<Long> excludeStoreUserIdList) {
            this.excludeStoreUserIdList = excludeStoreUserIdList;
        }
    }

    class SaveParam {
        private Long foodId;
        private Boolean sale;
        private Float salePrice;
        private Float quotePrice;
        private Long foodSupplierId;

        public Long getFoodSupplierId() {
            return foodSupplierId;
        }

        public void setFoodSupplierId(Long foodSupplierId) {
            this.foodSupplierId = foodSupplierId;
        }

        public Float getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(Float salePrice) {
            this.salePrice = salePrice;
        }

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public Boolean getSale() {
            return sale;
        }

        public void setSale(Boolean sale) {
            this.sale = sale;
        }

        public Float getQuotePrice() {
            return quotePrice;
        }

        public void setQuotePrice(Float quotePrice) {
            this.quotePrice = quotePrice;
        }
    }

    class soldOutJddjParam {
        private List<Long> storeUserIdList;

        public List<Long> getStoreUserIdList() {
            return storeUserIdList;
        }

        public void setStoreUserIdList(List<Long> storeUserIdList) {
            this.storeUserIdList = storeUserIdList;
        }
    }

    class BatchPublishParam {
        private Long foodId;
        private List<String> categoryNameList;
        private Long storeUserId;
        private Boolean checkExists = false;

        private Plat plat;

        public Plat getPlat() {
            return plat;
        }

        public void setPlat(Plat plat) {
            this.plat = plat;
        }

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public List<String> getCategoryNameList() {
            return categoryNameList;
        }

        public void setCategoryNameList(List<String> categoryNameList) {
            this.categoryNameList = categoryNameList;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public Boolean getCheckExists() {
            return checkExists;
        }

        public void setCheckExists(Boolean checkExists) {
            this.checkExists = checkExists;
        }
    }

    class BatchResetQuoteStatusParam extends SearchParam {
        private QuoteStatus targetQuoteStatus;

        public QuoteStatus getTargetQuoteStatus() {
            return targetQuoteStatus;
        }

        public void setTargetQuoteStatus(QuoteStatus targetQuoteStatus) {
            this.targetQuoteStatus = targetQuoteStatus;
        }
    }

    class BatchVerifyParam extends SearchParam {
    }

    class BatchDeleteParam extends SearchParam {

    }

    class SearchParam extends PagingParam {
        private String foodCode;
        private String foodName;
        private List<Long> foodIdList;
        private Long foodId;
        private List<String> categoryNameList;
        private String foodCategoryName;
        private Long storeUserId;
        private List<Long> storeUserIdList;
        private StoreUserStatus storeUserStatus;
        private Boolean storeUserOpened;
        private Boolean eleOpened;
        private Boolean meituanOpened;
        private Boolean wanteOpened;
        private Boolean jddjOpened;
        private Boolean clbmOpened;
        private Boolean sale;
        private QuoteStatus quoteStatus;
        private Float minQuotePrice;
        private Float maxQuotePrice;
        private QuoteContrast quoteContrast;
        private Float priceIncrease;
        private Long maxId;
        private Boolean promotional;
        private Boolean waitPublish;
        private PublishStatus meituanPublishStatus;
        private PublishStatus wantePublishStatus;
        private PublishStatus elePublishStatus;
        private PublishStatus jddjPublishStatus;
        private PublishStatus clbmPublishStatus;
        private Boolean quotePriceLock;
        private Float priceIncreaseLow;
        private Float priceIncreaseHeigh;

        private Long foodSupplierId;

        public Boolean getEleOpened() {
            return eleOpened;
        }

        public Boolean getMeituanOpened() {
            return meituanOpened;
        }

        public void setMeituanOpened(Boolean meituanOpened) {
            this.meituanOpened = meituanOpened;
        }

        public void setEleOpened(Boolean eleOpened) {
            this.eleOpened = eleOpened;
        }

        public Boolean getWanteOpened() {
            return wanteOpened;
        }

        public void setWanteOpened(Boolean wanteOpened) {
            this.wanteOpened = wanteOpened;
        }

        public Boolean getJddjOpened() {
            return jddjOpened;
        }

        public void setJddjOpened(Boolean jddjOpened) {
            this.jddjOpened = jddjOpened;
        }

        public Boolean getClbmOpened() {
            return clbmOpened;
        }

        public void setClbmOpened(Boolean clbmOpened) {
            this.clbmOpened = clbmOpened;
        }

        public PublishStatus getClbmPublishStatus() {
            return clbmPublishStatus;
        }

        public void setClbmPublishStatus(PublishStatus clbmPublishStatus) {
            this.clbmPublishStatus = clbmPublishStatus;
        }

        public PublishStatus getJddjPublishStatus() {
            return jddjPublishStatus;
        }

        public void setJddjPublishStatus(PublishStatus jddjPublishStatus) {
            this.jddjPublishStatus = jddjPublishStatus;
        }

        public Long getFoodSupplierId() {
            return foodSupplierId;
        }

        public void setFoodSupplierId(Long foodSupplierId) {
            this.foodSupplierId = foodSupplierId;
        }

        public Boolean getQuotePriceLock() {
            return quotePriceLock;
        }

        public void setQuotePriceLock(Boolean quotePriceLock) {
            this.quotePriceLock = quotePriceLock;
        }

        public List<Long> getStoreUserIdList() {
            return storeUserIdList;
        }

        public void setStoreUserIdList(List<Long> storeUserIdList) {
            this.storeUserIdList = storeUserIdList;
        }

        public Float getPriceIncrease() {
            return priceIncrease;
        }

        public void setPriceIncrease(Float priceIncrease) {
            this.priceIncrease = priceIncrease;
        }

        public Boolean getWaitPublish() {
            return waitPublish;
        }

        public void setWaitPublish(Boolean waitPublish) {
            this.waitPublish = waitPublish;
        }

        public PublishStatus getMeituanPublishStatus() {
            return meituanPublishStatus;
        }

        public void setMeituanPublishStatus(PublishStatus meituanPublishStatus) {
            this.meituanPublishStatus = meituanPublishStatus;
        }

        public PublishStatus getWantePublishStatus() {
            return wantePublishStatus;
        }

        public void setWantePublishStatus(PublishStatus wantePublishStatus) {
            this.wantePublishStatus = wantePublishStatus;
        }

        public PublishStatus getElePublishStatus() {
            return elePublishStatus;
        }

        public void setElePublishStatus(PublishStatus elePublishStatus) {
            this.elePublishStatus = elePublishStatus;
        }

        public Boolean getPromotional() {
            return promotional;
        }

        public void setPromotional(Boolean promotional) {
            this.promotional = promotional;
        }

        public Long getMaxId() {
            return maxId;
        }

        public void setMaxId(Long maxId) {
            this.maxId = maxId;
        }

        public QuoteContrast getQuoteContrast() {
            return quoteContrast;
        }

        public void setQuoteContrast(QuoteContrast quoteContrast) {
            this.quoteContrast = quoteContrast;
        }

        private StoreUserFoodSort sort;

        public StoreUserFoodSort getSort() {
            return sort;
        }

        public void setSort(StoreUserFoodSort sort) {
            this.sort = sort;
        }

        public Float getMinQuotePrice() {
            return minQuotePrice;
        }

        public void setMinQuotePrice(Float minQuotePrice) {
            this.minQuotePrice = minQuotePrice;
        }

        public Float getMaxQuotePrice() {
            return maxQuotePrice;
        }

        public void setMaxQuotePrice(Float maxQuotePrice) {
            this.maxQuotePrice = maxQuotePrice;
        }

        public List<String> getCategoryNameList() {
            return categoryNameList;
        }

        public void setCategoryNameList(List<String> categoryNameList) {
            this.categoryNameList = categoryNameList;
        }

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public List<Long> getFoodIdList() {
            return foodIdList;
        }

        public void setFoodIdList(List<Long> foodIdList) {
            this.foodIdList = foodIdList;
        }

        public StoreUserStatus getStoreUserStatus() {
            return storeUserStatus;
        }

        public void setStoreUserStatus(StoreUserStatus storeUserStatus) {
            this.storeUserStatus = storeUserStatus;
        }

        public Boolean getStoreUserOpened() {
            return storeUserOpened;
        }

        public void setStoreUserOpened(Boolean storeUserOpened) {
            this.storeUserOpened = storeUserOpened;
        }

        public QuoteStatus getQuoteStatus() {
            return quoteStatus;
        }

        public void setQuoteStatus(QuoteStatus quoteStatus) {
            this.quoteStatus = quoteStatus;
        }

        public Boolean getSale() {
            return sale;
        }

        public void setSale(Boolean sale) {
            this.sale = sale;
        }

        public String getFoodCode() {
            return foodCode;
        }

        public void setFoodCode(String foodCode) {
            this.foodCode = foodCode;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getFoodCategoryName() {
            return foodCategoryName;
        }

        public void setFoodCategoryName(String foodCategoryName) {
            this.foodCategoryName = foodCategoryName;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public Float getPriceIncreaseLow() {
            return priceIncreaseLow;
        }

        public void setPriceIncreaseLow(Float priceIncreaseLow) {
            this.priceIncreaseLow = priceIncreaseLow;
        }

        public Float getPriceIncreaseHeigh() {
            return priceIncreaseHeigh;
        }

        public void setPriceIncreaseHeigh(Float priceIncreaseHeigh) {
            this.priceIncreaseHeigh = priceIncreaseHeigh;
        }
    }
}
