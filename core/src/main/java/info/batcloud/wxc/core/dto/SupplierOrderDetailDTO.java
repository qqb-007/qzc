package info.batcloud.wxc.core.dto;

public class SupplierOrderDetailDTO {

    private Long id;

    private String foodCode;

    private String foodName;

    private String foodPicture;

    private String quoteUnit;

    private Long foodId;

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

    private String remark;

    private Float supplierQuotePrice;

    private Float supplierTotal;
    private Float supplierRefund;

    private Float supplierIncome;

    private Float merchantIncome;

    public Float getMerchantIncome() {
        return merchantIncome;
    }

    public void setMerchantIncome(Float merchantIncome) {
        this.merchantIncome = merchantIncome;
    }

    public String getQuoteUnit() {
        return quoteUnit;
    }

    public void setQuoteUnit(String quoteUnit) {
        this.quoteUnit = quoteUnit;
    }

    public Float getSupplierIncome() {
        return supplierIncome;
    }

    public void setSupplierIncome(Float supplierIncome) {
        this.supplierIncome = supplierIncome;
    }

    public Float getSupplierTotal() {
        return supplierTotal;
    }

    public void setSupplierTotal(Float supplierTotal) {
        this.supplierTotal = supplierTotal;
    }

    public Float getSupplierRefund() {
        return supplierRefund;
    }

    public void setSupplierRefund(Float supplierRefund) {
        this.supplierRefund = supplierRefund;
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
}
