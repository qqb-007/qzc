package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.domain.Propertie;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.dto.FoodDTO;
import info.batcloud.wxc.core.dto.OnlineFoodDTO;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.enums.FoodSort;
import info.batcloud.wxc.core.mapper.domain.FoodCategoryWithFoodNum;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface FoodService {

    void setProperties(PropertyParam param);

    boolean addShuiYin(String keyWord);

    boolean syncFromMeituan(String poiCode);

    void quotable(long id, boolean flag);

    void promotional(long id, boolean flag);

    void setFoodCode(long id, String code);

    void setMeituanTag(long id, String tagName, long tagId);

    void setFoodPrice(long id, float price);

    void setFoodWarningPrice(long id, float warningPrice);

    void setPerIncrease(long id, float perIncrease);

    void setQuoteUnit(long id, String unit);

    void deleteById(long id);

    void changeFoodIdx(long id, int idx);

    void bindActualFood(long id, long actualFoodId);

    void relieveActualFood(long id);

    FoodDTO forkFoodMirror(long foodId);

    List<OnlineFoodDTO> onlineStoreFood(long storeId);

    List<FoodCategoryWithFoodNum> findFoodCategoryList();

    List<FoodDTO> detectProblematicFoodList();

    List<FoodDetectInfo> detectStoreFoodList(long storeId);

    FoodDTO findById(long id);

    Paging<FoodDTO> search(SearchParam param);

    void updateFood(long id, UpdateParam param);

    FoodDTO createFood(CreateParam param);

    void setFoodSku(long id, List<FoodSku> foodSkus);

    FoodCodePublishResult foodCodePublishToStore(long storeId, FoodCodePublishParam param);

    FoodDTO toDTO(Food food);

    File export(SearchParam param) throws IOException;

    class FoodCodePublishResult extends Result {
        private List<String> msgList = new ArrayList<>();

        public List<String> getMsgList() {
            return msgList;
        }

        public void setMsgList(List<String> msgList) {
            this.msgList = msgList;
        }
    }

    class FoodDetectInfo {
        private Long foodId;
        private String foodName;
        private String foodCategoryName;
        private String foodPicture;
        private String foodCode;
        private String originCode;
        private String storeCode;
        private String foodUnit;

        public String getFoodUnit() {
            return foodUnit;
        }

        public void setFoodUnit(String foodUnit) {
            this.foodUnit = foodUnit;
        }

        public String getOriginCode() {
            return originCode;
        }

        public void setOriginCode(String originCode) {
            this.originCode = originCode;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getFoodCategoryName() {
            return foodCategoryName;
        }

        public void setFoodCategoryName(String foodCategoryName) {
            this.foodCategoryName = foodCategoryName;
        }

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getFoodPicture() {
            return foodPicture;
        }

        public void setFoodPicture(String foodPicture) {
            this.foodPicture = foodPicture;
        }

        public String getFoodCode() {
            return foodCode;
        }

        public void setFoodCode(String foodCode) {
            this.foodCode = foodCode;
        }
    }

    class FoodCodePublishParam {
        private List<String> foodCodeList;
        private List<String> foodNameList;
        private List<String> foodCategoryNameList;
        private List<String> foodOriginCodeList;

        public List<String> getFoodOriginCodeList() {
            return foodOriginCodeList;
        }

        public void setFoodOriginCodeList(List<String> foodOriginCodeList) {
            this.foodOriginCodeList = foodOriginCodeList;
        }

        public List<String> getFoodCodeList() {
            return foodCodeList;
        }

        public void setFoodCodeList(List<String> foodCodeList) {
            this.foodCodeList = foodCodeList;
        }

        public List<String> getFoodNameList() {
            return foodNameList;
        }

        public void setFoodNameList(List<String> foodNameList) {
            this.foodNameList = foodNameList;
        }

        public List<String> getFoodCategoryNameList() {
            return foodCategoryNameList;
        }

        public void setFoodCategoryNameList(List<String> foodCategoryNameList) {
            this.foodCategoryNameList = foodCategoryNameList;
        }
    }

    class CreateParam {
        private String name;
        private String code;
        private Float boxNum;
        private float boxPrice;
        private String picture;
        private String video;
        private String categoryName;
        private int minOrderCount;
        private String unit;
        private String description;
        private Float perIncrease;
        private Long meituanTagId;
        private String meituanTagName;
        private String eleBrandName;
        private String parentCategoryName;

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getParentCategoryName() {
            return parentCategoryName;
        }

        public void setParentCategoryName(String parentCategoryName) {
            this.parentCategoryName = parentCategoryName;
        }

        public String getEleBrandName() {
            return eleBrandName;
        }

        public void setEleBrandName(String eleBrandName) {
            this.eleBrandName = eleBrandName;
        }

        public Long getMeituanTagId() {
            return meituanTagId;
        }

        public void setMeituanTagId(Long meituanTagId) {
            this.meituanTagId = meituanTagId;
        }

        public String getMeituanTagName() {
            return meituanTagName;
        }

        public void setMeituanTagName(String meituanTagName) {
            this.meituanTagName = meituanTagName;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Float getPerIncrease() {
            return perIncrease;
        }

        public void setPerIncrease(Float perIncrease) {
            this.perIncrease = perIncrease;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Float getBoxNum() {
            return boxNum;
        }

        public void setBoxNum(Float boxNum) {
            this.boxNum = boxNum;
        }

        public float getBoxPrice() {
            return boxPrice;
        }

        public void setBoxPrice(float boxPrice) {
            this.boxPrice = boxPrice;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getMinOrderCount() {
            return minOrderCount;
        }

        public void setMinOrderCount(int minOrderCount) {
            this.minOrderCount = minOrderCount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    class PropertyParam {
        private long id;
        private List<Propertie> plist;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public List<Propertie> getPlist() {
            return plist;
        }

        public void setPlist(List<Propertie> plist) {
            this.plist = plist;
        }
    }

    class UpdateParam {
        private String name;
        private String picture;
        private String video;
        private int boxNum;
        private float boxPrice;
        private String categoryName;
        private String parentCategoryName;
        private int minOrderCount;
        private String unit;
        private String description;
        private Float perIncrease;
        private Long meituanTagId;
        private String meituanTagName;

        private String eleBrandName;

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getParentCategoryName() {
            return parentCategoryName;
        }

        public void setParentCategoryName(String parentCategoryName) {
            this.parentCategoryName = parentCategoryName;
        }

        public String getEleBrandName() {
            return eleBrandName;
        }

        public void setEleBrandName(String eleBrandName) {
            this.eleBrandName = eleBrandName;
        }

        public Long getMeituanTagId() {
            return meituanTagId;
        }

        public void setMeituanTagId(Long meituanTagId) {
            this.meituanTagId = meituanTagId;
        }

        public String getMeituanTagName() {
            return meituanTagName;
        }

        public void setMeituanTagName(String meituanTagName) {
            this.meituanTagName = meituanTagName;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public Float getPerIncrease() {
            return perIncrease;
        }

        public void setPerIncrease(Float perIncrease) {
            this.perIncrease = perIncrease;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBoxNum() {
            return boxNum;
        }

        public void setBoxNum(int boxNum) {
            this.boxNum = boxNum;
        }

        public float getBoxPrice() {
            return boxPrice;
        }

        public void setBoxPrice(float boxPrice) {
            this.boxPrice = boxPrice;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getMinOrderCount() {
            return minOrderCount;
        }

        public void setMinOrderCount(int minOrderCount) {
            this.minOrderCount = minOrderCount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    class SearchParam extends PagingParam {
        private String code;
        private String name;
        private Boolean quotable;
        private String quoteUnit;
        private List<Long> foodIdList;
        private Boolean emptyQuoteUnit;
        private String categoryName;
        private List<String> categoryNameList;
        private Integer sp;
        private Long excludeStoreUserId;
        private Long foodId;

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public Long getExcludeStoreUserId() {
            return excludeStoreUserId;
        }

        public void setExcludeStoreUserId(Long excludeStoreUserId) {
            this.excludeStoreUserId = excludeStoreUserId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<String> getCategoryNameList() {
            return categoryNameList;
        }

        public void setCategoryNameList(List<String> categoryNameList) {
            this.categoryNameList = categoryNameList;
        }

        public Integer getSp() {
            return sp;
        }

        public void setSp(Integer sp) {
            this.sp = sp;
        }

        public String getQuoteUnit() {
            return quoteUnit;
        }

        public void setQuoteUnit(String quoteUnit) {
            this.quoteUnit = quoteUnit;
        }

        private FoodSort sort = FoodSort.ID;

        public Boolean getEmptyQuoteUnit() {
            return emptyQuoteUnit;
        }

        public void setEmptyQuoteUnit(Boolean emptyQuoteUnit) {
            this.emptyQuoteUnit = emptyQuoteUnit;
        }

        public FoodSort getSort() {
            return sort;
        }

        public void setSort(FoodSort sort) {
            this.sort = sort;
        }

        public List<Long> getFoodIdList() {
            return foodIdList;
        }

        public void setFoodIdList(List<Long> foodIdList) {
            this.foodIdList = foodIdList;
        }

        public Boolean getQuotable() {
            return quotable;
        }

        public void setQuotable(Boolean quotable) {
            this.quotable = quotable;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
