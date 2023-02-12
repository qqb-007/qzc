package info.batcloud.wxc.core.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class OrderDetail {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Order order;

    @NotNull
    private String foodCode;

    @NotNull
    private String foodName;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Food food;

    @NotNull
    private String skuId;

    private String warehouseName;

    @NotNull
    private Float quantity;

    @NotNull
    private Float quoteUnitRatio;

    @NotNull
    private Float price;

    //报价
    private Float quotePrice;

    //供应商报价
    private Float supplierQuotePrice;

    /**
     * 供应商
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    private FoodSupplier foodSupplier;

    @NotNull
    private Float boxNum;

    @NotNull
    private Float boxPrice;

    private String unit;

    private String spec;

    @NotNull
    private float refundQuantity;

    @NotNull
    private float total;

    @NotNull
    private float refund;

    @NotNull
    private float costTotal;

    @NotNull
    private float costRefund;

    private String remark;

    private Boolean ok;

    private Boolean refunding;


    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    public FoodSupplier getFoodSupplier() {
        return foodSupplier;
    }

    public void setFoodSupplier(FoodSupplier foodSupplier) {
        this.foodSupplier = foodSupplier;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
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

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
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

    public float getRefundQuantity() {
        return refundQuantity;
    }

    public void setRefundQuantity(float refundQuantity) {
        this.refundQuantity = refundQuantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getRefund() {
        return refund;
    }

    public void setRefund(float refund) {
        this.refund = refund;
    }

    public float getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(float costTotal) {
        this.costTotal = costTotal;
    }

    public float getCostRefund() {
        return costRefund;
    }

    public void setCostRefund(float costRefund) {
        this.costRefund = costRefund;
    }

    public Boolean getRefunding() {
        return refunding;
    }

    public void setRefunding(Boolean refunding) {
        this.refunding = refunding;
    }
}
