package info.batcloud.wxc.core.dto;

public class OrderDetailDTO {

    private Long id;

    private OrderDTO order;

    private String foodCode;

    private String foodName;

    private String foodPicture;

    private Long foodId;

    private String warehouseName;

    private String skuId;

    private Float quantity;

    private Float price;

    //报价
    private Float quotePrice;

    private Float boxNum;

    private Float boxPrice;

    private String unit;

    private String spec;

    private Float refundQuantity;

    private Float total;

    private Float refund;

    private Float costTotal;

    private Float costRefund;

    private Boolean ok;

    private Boolean refunding;

    private String remark;


    private Float supplierQuotePrice;

    private Float quoteUnitRatio;

    private Long foodSupplierId;

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getFoodSupplierId() {
        return foodSupplierId;
    }

    public void setFoodSupplierId(Long foodSupplierId) {
        this.foodSupplierId = foodSupplierId;
    }

    public Float getQuoteUnitRatio() {
        return quoteUnitRatio;
    }

    public void setQuoteUnitRatio(Float quoteUnitRatio) {
        this.quoteUnitRatio = quoteUnitRatio;
    }

    public Float getSupplierQuotePrice() {
        return supplierQuotePrice;
    }

    public void setSupplierQuotePrice(Float supplierQuotePrice) {
        this.supplierQuotePrice = supplierQuotePrice;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
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

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(Float quotePrice) {
        this.quotePrice = quotePrice;
    }

    public Float getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(Float boxNum) {
        this.boxNum = boxNum;
    }

    public Float getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(Float boxPrice) {
        this.boxPrice = boxPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Float getRefundQuantity() {
        return refundQuantity;
    }

    public void setRefundQuantity(Float refundQuantity) {
        this.refundQuantity = refundQuantity;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getRefund() {
        return refund;
    }

    public void setRefund(Float refund) {
        this.refund = refund;
    }

    public Float getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(Float costTotal) {
        this.costTotal = costTotal;
    }

    public Float getCostRefund() {
        return costRefund;
    }

    public void setCostRefund(Float costRefund) {
        this.costRefund = costRefund;
    }

    public Boolean getRefunding() {
        return refunding;
    }

    public void setRefunding(Boolean refunding) {
        this.refunding = refunding;
    }

}
