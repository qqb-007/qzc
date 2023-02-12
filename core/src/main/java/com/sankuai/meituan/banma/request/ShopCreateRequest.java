package com.sankuai.meituan.banma.request;

import com.sankuai.meituan.banma.annotation.Param;
import com.sankuai.meituan.banma.response.ShopCreateResponse;

/**
 * 创建门店id
 */
public class ShopCreateRequest extends AbstractRequest<ShopCreateResponse> {
    @Param("shop_id")
    private String shopId;
    @Param("shop_name")
    private String shopName;
    @Param("category")
    private int category;
    @Param("second_category")
    private int secondCategory;
    @Param("contact_name")
    private String contactName;
    @Param("contact_phone")
    private String contactPhone;
    @Param("shop_address")
    private String shopAddress;
    @Param("shop_lng")
    private int shopLng;
    @Param("shop_lat")
    private int shopLat;
    @Param("coordinate_type")
    private int coordinateType;
    @Param("delivery_service_code")
    private String deliveryServiceCodes;
    @Param("business_time")
    private String businessTime;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(int secondCategory) {
        this.secondCategory = secondCategory;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public int getShopLng() {
        return shopLng;
    }

    public void setShopLng(int shopLng) {
        this.shopLng = shopLng;
    }

    public int getShopLat() {
        return shopLat;
    }

    public void setShopLat(int shopLat) {
        this.shopLat = shopLat;
    }

    public int getCoordinateType() {
        return coordinateType;
    }

    public void setCoordinateType(int coordinateType) {
        this.coordinateType = coordinateType;
    }

    public String getDeliveryServiceCodes() {
        return deliveryServiceCodes;
    }

    public void setDeliveryServiceCodes(String deliveryServiceCodes) {
        this.deliveryServiceCodes = deliveryServiceCodes;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    @Override
    public String getEndpoint() {
        return "/shop/create";
    }

    @Override
    public Class getResponseType() {
        return ShopCreateResponse.class;
    }
}
