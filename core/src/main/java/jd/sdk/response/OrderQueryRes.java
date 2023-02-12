package jd.sdk.response;

import jd.sdk.Response;

import java.util.Date;
import java.util.List;

public class OrderQueryRes extends Response<OrderQueryRes.Data> {
    public static class Data {
        private String code;
        private String msg;
        private Result result;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }
    }

    public static class Result {
        private Integer pageNo;
        private Integer pageSize;
        private Integer maxPageSize;
        private Integer totalCount;
        private List<OrderInfo> resultList;
        private String detail;

        public Integer getPageNo() {
            return pageNo;
        }

        public void setPageNo(Integer pageNo) {
            this.pageNo = pageNo;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getMaxPageSize() {
            return maxPageSize;
        }

        public void setMaxPageSize(Integer maxPageSize) {
            this.maxPageSize = maxPageSize;
        }

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public List<OrderInfo> getResultList() {
            return resultList;
        }

        public void setResultList(List<OrderInfo> resultList) {
            this.resultList = resultList;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }

    public static class OrderInfo {
        private Long orderId;
        private Integer srcInnerType;
        private Integer orderType;
        private Integer orderStatus;
        private Date orderStatusTime;
        private Date orderStartTime;
        private Date orderPurchaseTime;
        private Integer orderAgingType;
        private Date orderPreStartDeliveryTime;
        private Date orderPreEndDeliveryTime;
        private Date pickDeadline;
        private Date orderCancelTime;
        private String orderCancelRemark;
        private String orgCode;
        private String buyerPin;
        private String buyerFullName;
        private String buyerFullAddress;
        private String buyerTelephone;
        private String buyerMobile;
        private String lastFourDigitsOfBuyerMobile;
        private String deliveryStationNo;
        private String deliveryStationNoIsv;
        private String deliveryStationName;
        private String deliveryCarrierNo;
        private String deliveryCarrierName;
        private String deliveryBillNo;
        private Double deliveryPackageWeight;
        private Date deliveryConfirmTime;
        private Integer orderPayType;
        private Integer payChannel;
        private Long orderTotalMoney;
        private Long orderDiscountMoney;
        private Long orderFreightMoney;
        private Integer localDeliveryMoney;
        private Long merchantPaymentDistanceFreightMoney;
        private Long orderReceivableFreight;
        private Long platformPointsDeductionMoney;
        private Long orderBuyerPayableMoney;
        private Long packagingMoney;
        private Long tips;
        private Boolean adjustIsExists;
        private Long adjustId;
        private Boolean isGroupon;
        private Integer buyerCoordType;
        private Double buyerLng;
        private Double buyerLat;
        private String buyerCity;
        private String buyerCityName;
        private String buyerCountry;
        private String buyerCountryName;
        private String orderBuyerRemark;
        private String businessTag;
        private String equipmentId;
        private String buyerPoi;
        private String ordererName;
        private String ordererMobile;
        private Long orderNum;
        private Long userTip;
        private Date middleNumBindingTime;
        private Date deliverInputTime;
        private Integer businessType;
        private String venderVipCardId;
        private Integer orderInvoiceOpenMark;
        private String specialServiceTag;
        private String srcOrderType;
        private String srcOrderId;
        private OrderInvoice orderInvoice;
        private List<OrderProductDTO> product;
        private List<OrderDiscountDTO> discount;
        private PrescriptionDTO prescriptionDTO;
        private String detail;

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Integer getSrcInnerType() {
            return srcInnerType;
        }

        public void setSrcInnerType(Integer srcInnerType) {
            this.srcInnerType = srcInnerType;
        }

        public Integer getOrderType() {
            return orderType;
        }

        public void setOrderType(Integer orderType) {
            this.orderType = orderType;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Date getOrderStatusTime() {
            return orderStatusTime;
        }

        public void setOrderStatusTime(Date orderStatusTime) {
            this.orderStatusTime = orderStatusTime;
        }

        public Date getOrderStartTime() {
            return orderStartTime;
        }

        public void setOrderStartTime(Date orderStartTime) {
            this.orderStartTime = orderStartTime;
        }

        public Date getOrderPurchaseTime() {
            return orderPurchaseTime;
        }

        public void setOrderPurchaseTime(Date orderPurchaseTime) {
            this.orderPurchaseTime = orderPurchaseTime;
        }

        public Integer getOrderAgingType() {
            return orderAgingType;
        }

        public void setOrderAgingType(Integer orderAgingType) {
            this.orderAgingType = orderAgingType;
        }

        public Date getOrderPreStartDeliveryTime() {
            return orderPreStartDeliveryTime;
        }

        public void setOrderPreStartDeliveryTime(Date orderPreStartDeliveryTime) {
            this.orderPreStartDeliveryTime = orderPreStartDeliveryTime;
        }

        public Date getOrderPreEndDeliveryTime() {
            return orderPreEndDeliveryTime;
        }

        public void setOrderPreEndDeliveryTime(Date orderPreEndDeliveryTime) {
            this.orderPreEndDeliveryTime = orderPreEndDeliveryTime;
        }

        public Date getPickDeadline() {
            return pickDeadline;
        }

        public void setPickDeadline(Date pickDeadline) {
            this.pickDeadline = pickDeadline;
        }

        public Date getOrderCancelTime() {
            return orderCancelTime;
        }

        public void setOrderCancelTime(Date orderCancelTime) {
            this.orderCancelTime = orderCancelTime;
        }

        public String getOrderCancelRemark() {
            return orderCancelRemark;
        }

        public void setOrderCancelRemark(String orderCancelRemark) {
            this.orderCancelRemark = orderCancelRemark;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getBuyerPin() {
            return buyerPin;
        }

        public void setBuyerPin(String buyerPin) {
            this.buyerPin = buyerPin;
        }

        public String getBuyerFullName() {
            return buyerFullName;
        }

        public void setBuyerFullName(String buyerFullName) {
            this.buyerFullName = buyerFullName;
        }

        public String getBuyerFullAddress() {
            return buyerFullAddress;
        }

        public void setBuyerFullAddress(String buyerFullAddress) {
            this.buyerFullAddress = buyerFullAddress;
        }

        public String getBuyerTelephone() {
            return buyerTelephone;
        }

        public void setBuyerTelephone(String buyerTelephone) {
            this.buyerTelephone = buyerTelephone;
        }

        public String getBuyerMobile() {
            return buyerMobile;
        }

        public void setBuyerMobile(String buyerMobile) {
            this.buyerMobile = buyerMobile;
        }

        public String getLastFourDigitsOfBuyerMobile() {
            return lastFourDigitsOfBuyerMobile;
        }

        public void setLastFourDigitsOfBuyerMobile(String lastFourDigitsOfBuyerMobile) {
            this.lastFourDigitsOfBuyerMobile = lastFourDigitsOfBuyerMobile;
        }

        public String getDeliveryStationNo() {
            return deliveryStationNo;
        }

        public void setDeliveryStationNo(String deliveryStationNo) {
            this.deliveryStationNo = deliveryStationNo;
        }

        public String getDeliveryStationNoIsv() {
            return deliveryStationNoIsv;
        }

        public void setDeliveryStationNoIsv(String deliveryStationNoIsv) {
            this.deliveryStationNoIsv = deliveryStationNoIsv;
        }

        public String getDeliveryStationName() {
            return deliveryStationName;
        }

        public void setDeliveryStationName(String deliveryStationName) {
            this.deliveryStationName = deliveryStationName;
        }

        public String getDeliveryCarrierNo() {
            return deliveryCarrierNo;
        }

        public void setDeliveryCarrierNo(String deliveryCarrierNo) {
            this.deliveryCarrierNo = deliveryCarrierNo;
        }

        public String getDeliveryCarrierName() {
            return deliveryCarrierName;
        }

        public void setDeliveryCarrierName(String deliveryCarrierName) {
            this.deliveryCarrierName = deliveryCarrierName;
        }

        public String getDeliveryBillNo() {
            return deliveryBillNo;
        }

        public void setDeliveryBillNo(String deliveryBillNo) {
            this.deliveryBillNo = deliveryBillNo;
        }

        public Double getDeliveryPackageWeight() {
            return deliveryPackageWeight;
        }

        public void setDeliveryPackageWeight(Double deliveryPackageWeight) {
            this.deliveryPackageWeight = deliveryPackageWeight;
        }

        public Date getDeliveryConfirmTime() {
            return deliveryConfirmTime;
        }

        public void setDeliveryConfirmTime(Date deliveryConfirmTime) {
            this.deliveryConfirmTime = deliveryConfirmTime;
        }

        public Integer getOrderPayType() {
            return orderPayType;
        }

        public void setOrderPayType(Integer orderPayType) {
            this.orderPayType = orderPayType;
        }

        public Integer getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(Integer payChannel) {
            this.payChannel = payChannel;
        }

        public Long getOrderTotalMoney() {
            return orderTotalMoney;
        }

        public void setOrderTotalMoney(Long orderTotalMoney) {
            this.orderTotalMoney = orderTotalMoney;
        }

        public Long getOrderDiscountMoney() {
            return orderDiscountMoney;
        }

        public void setOrderDiscountMoney(Long orderDiscountMoney) {
            this.orderDiscountMoney = orderDiscountMoney;
        }

        public Long getOrderFreightMoney() {
            return orderFreightMoney;
        }

        public void setOrderFreightMoney(Long orderFreightMoney) {
            this.orderFreightMoney = orderFreightMoney;
        }

        public Integer getLocalDeliveryMoney() {
            return localDeliveryMoney;
        }

        public void setLocalDeliveryMoney(Integer localDeliveryMoney) {
            this.localDeliveryMoney = localDeliveryMoney;
        }

        public Long getMerchantPaymentDistanceFreightMoney() {
            return merchantPaymentDistanceFreightMoney;
        }

        public void setMerchantPaymentDistanceFreightMoney(Long merchantPaymentDistanceFreightMoney) {
            this.merchantPaymentDistanceFreightMoney = merchantPaymentDistanceFreightMoney;
        }

        public Long getOrderReceivableFreight() {
            return orderReceivableFreight;
        }

        public void setOrderReceivableFreight(Long orderReceivableFreight) {
            this.orderReceivableFreight = orderReceivableFreight;
        }

        public Long getPlatformPointsDeductionMoney() {
            return platformPointsDeductionMoney;
        }

        public void setPlatformPointsDeductionMoney(Long platformPointsDeductionMoney) {
            this.platformPointsDeductionMoney = platformPointsDeductionMoney;
        }

        public Long getOrderBuyerPayableMoney() {
            return orderBuyerPayableMoney;
        }

        public void setOrderBuyerPayableMoney(Long orderBuyerPayableMoney) {
            this.orderBuyerPayableMoney = orderBuyerPayableMoney;
        }

        public Long getPackagingMoney() {
            return packagingMoney;
        }

        public void setPackagingMoney(Long packagingMoney) {
            this.packagingMoney = packagingMoney;
        }

        public Long getTips() {
            return tips;
        }

        public void setTips(Long tips) {
            this.tips = tips;
        }

        public Boolean getAdjustIsExists() {
            return adjustIsExists;
        }

        public void setAdjustIsExists(Boolean adjustIsExists) {
            this.adjustIsExists = adjustIsExists;
        }

        public Long getAdjustId() {
            return adjustId;
        }

        public void setAdjustId(Long adjustId) {
            this.adjustId = adjustId;
        }

        public Boolean getGroupon() {
            return isGroupon;
        }

        public void setGroupon(Boolean groupon) {
            isGroupon = groupon;
        }

        public Integer getBuyerCoordType() {
            return buyerCoordType;
        }

        public void setBuyerCoordType(Integer buyerCoordType) {
            this.buyerCoordType = buyerCoordType;
        }

        public Double getBuyerLng() {
            return buyerLng;
        }

        public void setBuyerLng(Double buyerLng) {
            this.buyerLng = buyerLng;
        }

        public Double getBuyerLat() {
            return buyerLat;
        }

        public void setBuyerLat(Double buyerLat) {
            this.buyerLat = buyerLat;
        }

        public String getBuyerCity() {
            return buyerCity;
        }

        public void setBuyerCity(String buyerCity) {
            this.buyerCity = buyerCity;
        }

        public String getBuyerCityName() {
            return buyerCityName;
        }

        public void setBuyerCityName(String buyerCityName) {
            this.buyerCityName = buyerCityName;
        }

        public String getBuyerCountry() {
            return buyerCountry;
        }

        public void setBuyerCountry(String buyerCountry) {
            this.buyerCountry = buyerCountry;
        }

        public String getBuyerCountryName() {
            return buyerCountryName;
        }

        public void setBuyerCountryName(String buyerCountryName) {
            this.buyerCountryName = buyerCountryName;
        }

        public String getOrderBuyerRemark() {
            return orderBuyerRemark;
        }

        public void setOrderBuyerRemark(String orderBuyerRemark) {
            this.orderBuyerRemark = orderBuyerRemark;
        }

        public String getBusinessTag() {
            return businessTag;
        }

        public void setBusinessTag(String businessTag) {
            this.businessTag = businessTag;
        }

        public String getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(String equipmentId) {
            this.equipmentId = equipmentId;
        }

        public String getBuyerPoi() {
            return buyerPoi;
        }

        public void setBuyerPoi(String buyerPoi) {
            this.buyerPoi = buyerPoi;
        }

        public String getOrdererName() {
            return ordererName;
        }

        public void setOrdererName(String ordererName) {
            this.ordererName = ordererName;
        }

        public String getOrdererMobile() {
            return ordererMobile;
        }

        public void setOrdererMobile(String ordererMobile) {
            this.ordererMobile = ordererMobile;
        }

        public Long getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(Long orderNum) {
            this.orderNum = orderNum;
        }

        public Long getUserTip() {
            return userTip;
        }

        public void setUserTip(Long userTip) {
            this.userTip = userTip;
        }

        public Date getMiddleNumBindingTime() {
            return middleNumBindingTime;
        }

        public void setMiddleNumBindingTime(Date middleNumBindingTime) {
            this.middleNumBindingTime = middleNumBindingTime;
        }

        public Date getDeliverInputTime() {
            return deliverInputTime;
        }

        public void setDeliverInputTime(Date deliverInputTime) {
            this.deliverInputTime = deliverInputTime;
        }

        public Integer getBusinessType() {
            return businessType;
        }

        public void setBusinessType(Integer businessType) {
            this.businessType = businessType;
        }

        public String getVenderVipCardId() {
            return venderVipCardId;
        }

        public void setVenderVipCardId(String venderVipCardId) {
            this.venderVipCardId = venderVipCardId;
        }

        public Integer getOrderInvoiceOpenMark() {
            return orderInvoiceOpenMark;
        }

        public void setOrderInvoiceOpenMark(Integer orderInvoiceOpenMark) {
            this.orderInvoiceOpenMark = orderInvoiceOpenMark;
        }

        public String getSpecialServiceTag() {
            return specialServiceTag;
        }

        public void setSpecialServiceTag(String specialServiceTag) {
            this.specialServiceTag = specialServiceTag;
        }

        public String getSrcOrderType() {
            return srcOrderType;
        }

        public void setSrcOrderType(String srcOrderType) {
            this.srcOrderType = srcOrderType;
        }

        public String getSrcOrderId() {
            return srcOrderId;
        }

        public void setSrcOrderId(String srcOrderId) {
            this.srcOrderId = srcOrderId;
        }

        public OrderInvoice getOrderInvoice() {
            return orderInvoice;
        }

        public void setOrderInvoice(OrderInvoice orderInvoice) {
            this.orderInvoice = orderInvoice;
        }

        public List<OrderProductDTO> getProduct() {
            return product;
        }

        public void setProduct(List<OrderProductDTO> product) {
            this.product = product;
        }

        public List<OrderDiscountDTO> getDiscount() {
            return discount;
        }

        public void setDiscount(List<OrderDiscountDTO> discount) {
            this.discount = discount;
        }

        public PrescriptionDTO getPrescriptionDTO() {
            return prescriptionDTO;
        }

        public void setPrescriptionDTO(PrescriptionDTO prescriptionDTO) {
            this.prescriptionDTO = prescriptionDTO;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }

    public static class PrescriptionDTO {
        private String useDrugName;
        private Integer sex;
        private String identityNumber;
        private String birthday;
        private String phoneNumber;
        private String picUrl;
        private List<String> picUrlList;

        public String getUseDrugName() {
            return useDrugName;
        }

        public void setUseDrugName(String useDrugName) {
            this.useDrugName = useDrugName;
        }

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public String getIdentityNumber() {
            return identityNumber;
        }

        public void setIdentityNumber(String identityNumber) {
            this.identityNumber = identityNumber;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public List<String> getPicUrlList() {
            return picUrlList;
        }

        public void setPicUrlList(List<String> picUrlList) {
            this.picUrlList = picUrlList;
        }
    }

    public static class OrderDiscountDTO {
        private Long orderId;
        private Long adjustId;
        private String skuIds;
        private Integer discountType;
        private Integer discountDetailType;
        private String discountCode;
        private Long discountPrice;
        private Long venderPayMoney;
        private Long platPayMoney;

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Long getAdjustId() {
            return adjustId;
        }

        public void setAdjustId(Long adjustId) {
            this.adjustId = adjustId;
        }

        public String getSkuIds() {
            return skuIds;
        }

        public void setSkuIds(String skuIds) {
            this.skuIds = skuIds;
        }

        public Integer getDiscountType() {
            return discountType;
        }

        public void setDiscountType(Integer discountType) {
            this.discountType = discountType;
        }

        public Integer getDiscountDetailType() {
            return discountDetailType;
        }

        public void setDiscountDetailType(Integer discountDetailType) {
            this.discountDetailType = discountDetailType;
        }

        public String getDiscountCode() {
            return discountCode;
        }

        public void setDiscountCode(String discountCode) {
            this.discountCode = discountCode;
        }

        public Long getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(Long discountPrice) {
            this.discountPrice = discountPrice;
        }

        public Long getVenderPayMoney() {
            return venderPayMoney;
        }

        public void setVenderPayMoney(Long venderPayMoney) {
            this.venderPayMoney = venderPayMoney;
        }

        public Long getPlatPayMoney() {
            return platPayMoney;
        }

        public void setPlatPayMoney(Long platPayMoney) {
            this.platPayMoney = platPayMoney;
        }
    }

    public static class OrderProductDTO {
        private Long orderId;
        private String skuCostumeProperty;
        private Long adjustId;
        private Long skuId;
        private String skuName;
        private String skuIdIsv;
        private Long skuJdPrice;
        private Integer skuCount;
        private Integer adjustMode;
        private String upcCode;
        private Long skuStorePrice;
        private Long skuCostPrice;
        private Integer promotionType;
        private String skuTaxRate;
        private Long promotionId;
        private String relatedSkus;
        private Double skuWeight;
        private Long canteenMoney;
        private Object productExtendInfoMap;

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public String getSkuCostumeProperty() {
            return skuCostumeProperty;
        }

        public void setSkuCostumeProperty(String skuCostumeProperty) {
            this.skuCostumeProperty = skuCostumeProperty;
        }

        public Long getAdjustId() {
            return adjustId;
        }

        public void setAdjustId(Long adjustId) {
            this.adjustId = adjustId;
        }

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public String getSkuIdIsv() {
            return skuIdIsv;
        }

        public void setSkuIdIsv(String skuIdIsv) {
            this.skuIdIsv = skuIdIsv;
        }

        public Long getSkuJdPrice() {
            return skuJdPrice;
        }

        public void setSkuJdPrice(Long skuJdPrice) {
            this.skuJdPrice = skuJdPrice;
        }

        public Integer getSkuCount() {
            return skuCount;
        }

        public void setSkuCount(Integer skuCount) {
            this.skuCount = skuCount;
        }

        public Integer getAdjustMode() {
            return adjustMode;
        }

        public void setAdjustMode(Integer adjustMode) {
            this.adjustMode = adjustMode;
        }

        public String getUpcCode() {
            return upcCode;
        }

        public void setUpcCode(String upcCode) {
            this.upcCode = upcCode;
        }

        public Long getSkuStorePrice() {
            return skuStorePrice;
        }

        public void setSkuStorePrice(Long skuStorePrice) {
            this.skuStorePrice = skuStorePrice;
        }

        public Long getSkuCostPrice() {
            return skuCostPrice;
        }

        public void setSkuCostPrice(Long skuCostPrice) {
            this.skuCostPrice = skuCostPrice;
        }

        public Integer getPromotionType() {
            return promotionType;
        }

        public void setPromotionType(Integer promotionType) {
            this.promotionType = promotionType;
        }

        public String getSkuTaxRate() {
            return skuTaxRate;
        }

        public void setSkuTaxRate(String skuTaxRate) {
            this.skuTaxRate = skuTaxRate;
        }

        public Long getPromotionId() {
            return promotionId;
        }

        public void setPromotionId(Long promotionId) {
            this.promotionId = promotionId;
        }

        public String getRelatedSkus() {
            return relatedSkus;
        }

        public void setRelatedSkus(String relatedSkus) {
            this.relatedSkus = relatedSkus;
        }

        public Double getSkuWeight() {
            return skuWeight;
        }

        public void setSkuWeight(Double skuWeight) {
            this.skuWeight = skuWeight;
        }

        public Long getCanteenMoney() {
            return canteenMoney;
        }

        public void setCanteenMoney(Long canteenMoney) {
            this.canteenMoney = canteenMoney;
        }

        public Object getProductExtendInfoMap() {
            return productExtendInfoMap;
        }

        public void setProductExtendInfoMap(Object productExtendInfoMap) {
            this.productExtendInfoMap = productExtendInfoMap;
        }
    }

    public static class OrderInvoice {
        private Integer invoiceFormType;
        private String invoiceTitle;
        private String invoiceDutyNo;
        private String invoiceMail;
        private Long invoiceMoney;
        private Integer invoiceType;
        private String invoiceMoneyDetail;
        private String invoiceAddress;
        private String invoiceTelNo;
        private String invoiceBankName;
        private String invoiceAccountNo;
        private String invoiceContent;

        public Integer getInvoiceFormType() {
            return invoiceFormType;
        }

        public void setInvoiceFormType(Integer invoiceFormType) {
            this.invoiceFormType = invoiceFormType;
        }

        public String getInvoiceTitle() {
            return invoiceTitle;
        }

        public void setInvoiceTitle(String invoiceTitle) {
            this.invoiceTitle = invoiceTitle;
        }

        public String getInvoiceDutyNo() {
            return invoiceDutyNo;
        }

        public void setInvoiceDutyNo(String invoiceDutyNo) {
            this.invoiceDutyNo = invoiceDutyNo;
        }

        public String getInvoiceMail() {
            return invoiceMail;
        }

        public void setInvoiceMail(String invoiceMail) {
            this.invoiceMail = invoiceMail;
        }

        public Long getInvoiceMoney() {
            return invoiceMoney;
        }

        public void setInvoiceMoney(Long invoiceMoney) {
            this.invoiceMoney = invoiceMoney;
        }

        public Integer getInvoiceType() {
            return invoiceType;
        }

        public void setInvoiceType(Integer invoiceType) {
            this.invoiceType = invoiceType;
        }

        public String getInvoiceMoneyDetail() {
            return invoiceMoneyDetail;
        }

        public void setInvoiceMoneyDetail(String invoiceMoneyDetail) {
            this.invoiceMoneyDetail = invoiceMoneyDetail;
        }

        public String getInvoiceAddress() {
            return invoiceAddress;
        }

        public void setInvoiceAddress(String invoiceAddress) {
            this.invoiceAddress = invoiceAddress;
        }

        public String getInvoiceTelNo() {
            return invoiceTelNo;
        }

        public void setInvoiceTelNo(String invoiceTelNo) {
            this.invoiceTelNo = invoiceTelNo;
        }

        public String getInvoiceBankName() {
            return invoiceBankName;
        }

        public void setInvoiceBankName(String invoiceBankName) {
            this.invoiceBankName = invoiceBankName;
        }

        public String getInvoiceAccountNo() {
            return invoiceAccountNo;
        }

        public void setInvoiceAccountNo(String invoiceAccountNo) {
            this.invoiceAccountNo = invoiceAccountNo;
        }

        public String getInvoiceContent() {
            return invoiceContent;
        }

        public void setInvoiceContent(String invoiceContent) {
            this.invoiceContent = invoiceContent;
        }
    }
}
