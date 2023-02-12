package info.batcloud.wxc.core.dto;

import java.util.List;

public class SupplierOrderDetailGroupDTO {

    private Long supplierId;
    private String supplierName;
    private String supplierPhone;
    private String supplierAddress;
    private Long orderId;

    private List<SupplierOrderDetailDTO> orderDetailList;

    private float totalMoney;

    private float refundMoney;

    private float remainMoney;

    private float merchantIncome;

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public float getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(float refundMoney) {
        this.refundMoney = refundMoney;
    }

    public float getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(float remainMoney) {
        this.remainMoney = remainMoney;
    }

    public float getMerchantIncome() {
        return merchantIncome;
    }

    public void setMerchantIncome(float merchantIncome) {
        this.merchantIncome = merchantIncome;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public List<SupplierOrderDetailDTO> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<SupplierOrderDetailDTO> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
