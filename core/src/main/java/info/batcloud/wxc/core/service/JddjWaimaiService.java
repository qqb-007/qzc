package info.batcloud.wxc.core.service;

import info.batcloud.wxc.core.domain.LocateInfo;
import info.batcloud.wxc.core.domain.OrderExtra;
import info.batcloud.wxc.core.enums.PickType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.helper.CautionHelper;
import info.batcloud.wxc.core.helper.CoordinateHelper;
import info.batcloud.wxc.core.helper.JddjHelper;
import jd.sdk.response.OrderQueryRes;

import java.util.ArrayList;
import java.util.List;

import static info.batcloud.wxc.core.enums.PayType.ONLINE;

public interface JddjWaimaiService {
    static OrderService.OrderParam toOrder(OrderQueryRes.Result result) {
        OrderService.OrderParam param = new OrderService.OrderParam();
        List<OrderQueryRes.OrderInfo> orderInfos = result.getResultList();
        OrderQueryRes.OrderInfo orderInfo = orderInfos.get(0);
        param.setPlatOrderId(String.valueOf(orderInfo.getOrderId()));
        param.setAppPoiCode(orderInfo.getDeliveryStationNo());
        param.setWmPoiName(orderInfo.getDeliveryStationName());
        param.setWmPoiAddress(orderInfo.getDeliveryStationName());
        param.setWmPoiPhone(orderInfo.getDeliveryCarrierName());
        param.setRecipientAddress(CautionHelper.getCaution(orderInfo.getBuyerFullAddress()));
        param.setRecipientName(CautionHelper.getCaution(orderInfo.getBuyerFullName()));
        param.setRecipientPhone(orderInfo.getBuyerMobile());
        param.setShippingFee(Float.valueOf(orderInfo.getOrderFreightMoney()) / 100);
        param.setTotal(Float.valueOf(orderInfo.getOrderBuyerPayableMoney()) / 100);
        param.setDaySeq(orderInfo.getOrderNum().intValue());
        param.setOriginalPrice(Float.valueOf(orderInfo.getOrderTotalMoney()) / 100);
        param.setPickType(PickType.PT);
//        if (orderInfo.getBuyerCoordType() == null || orderInfo.getBuyerCoordType() == 1) {
//            LocateInfo locateInfo = CoordinateHelper.wgs84_To_Gcj02(orderInfo.getBuyerLat(), orderInfo.getBuyerLng());
//            param.setLat(locateInfo.getLatitude());
//            param.setLng(locateInfo.getLongitude());
//        } else {
            param.setLat(orderInfo.getBuyerLat());
            param.setLng(orderInfo.getBuyerLng());
//        }

        param.setDeliveryTime(orderInfo.getOrderPreEndDeliveryTime().getTime() / 1000);
        param.setExpectedDeliveryTime(orderInfo.getOrderPreEndDeliveryTime().getTime() / 1000);
        param.setCaution(orderInfo.getOrderBuyerRemark() == null ? "用户没有备注" : CautionHelper.getCaution(orderInfo.getOrderBuyerRemark()));
        param.setShipperPhone("");
        param.setPlat(Plat.JDDJ);
        param.setStatus(JddjHelper.toOrderStatus(orderInfo.getOrderStatus()));
        param.setPayType(ONLINE);
        param.setCreateTime(orderInfo.getOrderStartTime());
        param.setUpdateTime(orderInfo.getOrderStatusTime());
        /*param.setDetailParamList(new ArrayList<>());
        List<OrderQueryRes.OrderProductDTO> product = orderInfo.getProduct();
        for (OrderQueryRes.OrderProductDTO dto : product) {
            OrderService.OrderDetailParam p = new OrderService.OrderDetailParam();
            p.setFoodName(dto.getSkuName());
            p.setQuantity(Float.valueOf(dto.getSkuCount()));
            //p.setSkuId(dto.getSkuIdIsv());
            p.setPrice(Float.valueOf(dto.getSkuJdPrice()/100));
            p.setBoxNum(Float.valueOf(dto.getSkuCount()));
            p.setBoxPrice(Float.valueOf(dto.getCanteenMoney()/dto.getSkuCount()));
            p.setUnit("");
            p.setSpec("");
            //p.setCode(dto.getSkuIdIsv().split("-")[0]);
            param.getDetailParamList().add(p);
        }*/
        List<OrderExtra> extraList = new ArrayList<>();
        param.setExtraList(extraList);
        return param;
    }
}
