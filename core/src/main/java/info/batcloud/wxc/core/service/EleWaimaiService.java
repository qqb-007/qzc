package info.batcloud.wxc.core.service;

import info.batcloud.wxc.core.domain.OrderExtra;
import info.batcloud.wxc.core.enums.PickType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.helper.CautionHelper;
import info.batcloud.wxc.core.helper.EleHelper;
import me.ele.sdk.up.response.OrderGetResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static info.batcloud.wxc.core.enums.PayType.ONLINE;

public interface EleWaimaiService {

    static OrderService.OrderParam toOrder(OrderGetResponse.Data data) {
        OrderGetResponse.Order order = data.getOrder();
        OrderGetResponse.Shop shop = data.getShop();
        OrderGetResponse.User user = data.getUser();
        List<OrderGetResponse.Product> detailList = data.getProducts().get(0);
        OrderService.OrderParam param = new OrderService.OrderParam();
        BeanUtils.copyProperties(order, param);
        param.setExpectedDeliveryTime(Long.parseLong(order.getLatestSendTime()));
        if (!"**".equals(user.getCoordAmap().getLatitude())) {
            String lat = String.format("%.6f", Double.parseDouble(user.getCoordAmap().getLatitude()));
            String lng = String.format("%.6f", Double.parseDouble(user.getCoordAmap().getLongitude()));
            param.setLat(Double.parseDouble(lat));
            param.setLng(Double.parseDouble(lng));
        }
        if (order.getSendImmediately() == 1){
            param.setDeliveryTime(0l);
        }else if (order.getSendImmediately() == 2){
            param.setDeliveryTime(Long.parseLong(order.getLatestSendTime()));
        }
        param.setPlatOrderId(order.getOrderId());
//        param.setWmOrderIdView(order.getWm_order_id_view().toString());
        param.setAppPoiCode(shop.getId());
        param.setWmPoiName(shop.getName());
        param.setWmPoiAddress(shop.getName());
        param.setWmPoiPhone(shop.getName());
        param.setRecipientAddress(CautionHelper.getCaution(user.getAddress()));
        param.setRecipientName(CautionHelper.getCaution(user.getName()));
        param.setRecipientPhone(StringUtils.isBlank(user.getPhone()) ? user.getPrivacyPhone() : user.getPhone());
        param.setShippingFee(Float.valueOf(order.getSendFee()) / 100);
        param.setTotal(Float.valueOf(order.getShopFee()) / 100);
        param.setDaySeq(order.getOrderIndex());
        param.setOriginalPrice(Float.valueOf(order.getTotalFee()) / 100);
        param.setCaution(CautionHelper.getCaution(order.getRemark()));
        param.setShipperPhone(order.getDeliveryPhone());
        param.setPlat(Plat.ELE);
        param.setStatus(EleHelper.toOrderStatus(order.getStatus()));
        param.setPayType(ONLINE);
        param.setPickType(PickType.PT);
        param.setCreateTime(new Date(order.getCreateTime() * 1000));
        param.setUpdateTime(new Date());
        param.setDetailParamList(new ArrayList<>());
        for (OrderGetResponse.Product product : detailList) {
            OrderService.OrderDetailParam p = new OrderService.OrderDetailParam();
            p.setFoodName(product.getProductName());
            p.setSkuId(product.getCustomSkuId());
            p.setQuantity(Float.valueOf(product.getProductAmount()));
            p.setPrice(Float.valueOf(product.getProductPrice()) / 100);
            p.setBoxNum(Float.valueOf(product.getPackageAmount()));
            p.setBoxPrice(Float.valueOf(product.getPackageFee()) / 100);
            p.setUnit("");
            p.setSpec("");
            p.setCode(EleHelper.getFoodUpc(product.getUpc()));
            param.getDetailParamList().add(p);
        }
        List<OrderExtra> extraList = new ArrayList<>();
        param.setExtraList(extraList);
        //优惠信息
        return param;
    }
}
