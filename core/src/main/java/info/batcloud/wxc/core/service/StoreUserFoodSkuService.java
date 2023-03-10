package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.StoreUserFoodSkuDTO;
import info.batcloud.wxc.core.enums.Plat;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface StoreUserFoodSkuService {

    void syncNewOrderStock(long orderDetailId, long storeUserId, Plat plat);

    void syncCancelOrderStock(long orderDetailId, long storeUserId, Plat plat);

    void receiptStock(long storeUserId, String upc, Integer addStock);

    void updateTFoodInfo(long foodSkuId);

    List<StoreUserFoodSkuDTO> getWhSufList(long wid);

    void storeUpdateSufSku(long sufId, List<String> foodSkuId, List<Integer> stock, List<Integer> boxNums, List<Float> outputPrice, List<Float> boxPrices, List<Integer> minOrderCounts, List<Float> inputPrice);

    void addNewSkus(long fooSkuId);

    void adminUpdateSufSku(long sufId, List<Long> foodSkuId, List<Integer> stock, List<Float> inputPrice, List<Float> outputPrice);

    void createSufSku(Long sufId);

    void updateSufSku(UpdateParam updateParam);

    List<StoreUserFoodSkuDTO> getByStoreUserFoodId(long sufId);

    List<StoreUserFoodSkuDTO> getByFoodSkuId(long foodSkuId);

    StoreUserFoodSkuDTO getByUpcAndStoreUserId(long storeUserId, String upc);

    public Paging<StoreUserFoodSkuDTO> search(SearchParam param);

    class SearchParam extends PagingParam {
        Long storeUserId;
        Long foodId;
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }
    }


    class UpdateParam {
        private Long Id;

        //private String warehouseIds;

        private Integer stock;

        private Boolean sale;

        private Float inputPrice;

        private Float outputPrice;

        private Integer minOrderCount;

        private Integer boxNum;

        private Float boxPrice;

        public Boolean getSale() {
            return sale;
        }

        public void setSale(Boolean sale) {
            this.sale = sale;
        }

        public Long getId() {
            return Id;
        }

        public void setId(Long id) {
            Id = id;
        }

//        public String getWarehouseIds() {
//            return warehouseIds;
//        }
//
//        public void setWarehouseIds(String warehouseIds) {
//            this.warehouseIds = warehouseIds;
//        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
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

        public Integer getBoxNum() {
            return boxNum;
        }

        public void setBoxNum(Integer boxNum) {
            this.boxNum = boxNum;
        }

        public Float getBoxPrice() {
            return boxPrice;
        }

        public void setBoxPrice(Float boxPrice) {
            this.boxPrice = boxPrice;
        }
    }

    class CreateParam {
        private Long storeUserFoodId;

        private Long foodSkuId;

        //private String warehouseIds;

        private Integer stock;

        private Float inputPrice;

        private Float outputPrice;

        private Integer minOrderCount;

        private Integer boxNum;

        private Float boxPrice;

        public Long getStoreUserFoodId() {
            return storeUserFoodId;
        }

        public void setStoreUserFoodId(Long storeUserFoodId) {
            this.storeUserFoodId = storeUserFoodId;
        }

        public Long getFoodSkuId() {
            return foodSkuId;
        }

        public void setFoodSkuId(Long foodSkuId) {
            this.foodSkuId = foodSkuId;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
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

        public Integer getBoxNum() {
            return boxNum;
        }

        public void setBoxNum(Integer boxNum) {
            this.boxNum = boxNum;
        }

        public Float getBoxPrice() {
            return boxPrice;
        }

        public void setBoxPrice(Float boxPrice) {
            this.boxPrice = boxPrice;
        }
    }
}
