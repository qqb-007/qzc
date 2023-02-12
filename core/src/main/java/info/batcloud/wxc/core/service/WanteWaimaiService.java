package info.batcloud.wxc.core.service;

import info.batcloud.wxc.core.domain.LocateInfo;
import info.batcloud.wxc.core.domain.OrderExtra;
import info.batcloud.wxc.core.entity.Store;
import info.batcloud.wxc.core.enums.OrderStatus;
import info.batcloud.wxc.core.enums.PickType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.helper.CautionHelper;
import info.batcloud.wxc.core.helper.CoordinateHelper;
import info.batcloud.wxc.core.helper.WanteHelper;
import info.batcloud.wxc.core.repository.StoreRepository;
import wante.sdk.up.response.OrderGetRes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static info.batcloud.wxc.core.enums.PayType.ONLINE;

public interface WanteWaimaiService {

    static OrderService.OrderParam toOrder(OrderGetRes data, Store store) {
        OrderService.OrderParam param = new OrderService.OrderParam();
        OrderGetRes.ReceivingAddress receivingAddress = data.getReceivingAddress();
        param.setPlatOrderId(String.valueOf(data.getId()));
        param.setPlat(Plat.WANTE);
        param.setAppPoiCode(String.valueOf(data.getStoreId()));
        param.setWmPoiName(store.getName());
        param.setWmPoiAddress(store.getAddress());
        param.setWmPoiPhone(store.getPhone());
        boolean pd = true;
        if ("CANCEL".equals(data.getStatus()) && data.getRefund() != null && data.getRefund().size() > 0) {
            List<OrderGetRes.Refund> refunds = data.getRefund();
            for (OrderGetRes.Refund refund : refunds) {
                if ("TO_EXAMINE".equals(refund.getStatus())) {
                    pd = false;
                    break;
                }
            }
        }
        if (pd){
            param.setStatus(WanteHelper.toOrderStatus(data.getStatus()));//订单状态
        }else {
            param.setStatus(OrderStatus.MERCHANT_CONFIRMED);
        }
        param.setRecipientAddress(receivingAddress.getFullAddressIntro() + receivingAddress.getDetailedAddress());
        param.setRecipientName(CautionHelper.getCaution(receivingAddress.getName()));
        param.setRecipientPhone(receivingAddress.getPhone());
        Double lat = receivingAddress.getCoordinate().getLatitude();
        Double lon = receivingAddress.getCoordinate().getLongitude();
        LocateInfo locateInfo = CoordinateHelper.wgs84_To_Gcj02(lat, lon);
        param.setLng(locateInfo.getLongitude());
        param.setLat(locateInfo.getLatitude());
        List<OrderGetRes.Delivery> deliverys = data.getDelivery();
        if (deliverys != null && deliverys.size() > 0) {
            OrderGetRes.Delivery delivery = deliverys.get(0);
            param.setShipperPhone(delivery.getPhone());
        } else {
            param.setShipperPhone("");
        }
        param.setPayType(ONLINE);
        param.setPickType(PickType.PT);
        param.setCaution(CautionHelper.getCaution(data.getUserNotes()));
        List<OrderExtra> extraList = new ArrayList<>();
        param.setExtraList(extraList);
        param.setOriginalPrice(data.getTotalPrice().floatValue());//订单原总价
        param.setTotal(data.getPurchasePrice().floatValue());//实际收费金额
        if (data.getFreight() == null) {
            param.setShippingFee(0.0f);
        } else {
            param.setShippingFee(data.getFreight().floatValue());//配送费
        }
        if (data.getSameDayNumber() != null) {
            param.setDaySeq(Integer.valueOf(data.getSameDayNumber()));//订单流水号
        }

        //优惠信息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            if (data.getDeliveryTimeCustom() != null && data.getDeliveryTimeCustom().length() > 0) {
                param.setExpectedDeliveryTime(df.parse(data.getDeliveryTimeCustom()).getTime() / 1000);
            } else {
                param.setExpectedDeliveryTime((df.parse(data.getCreateTime()).getTime() + 45 * 60 * 1000) / 1000);//下单时间45分钟后
            }
            param.setCreateTime(df.parse(data.getCreateTime()));
            param.setUpdateTime(df.parse(data.getEditTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*
        param.setExpectedDeliveryTime();//期望送达时间（问清楚是不是必推，如果是即时订单推动什么）
        */
        return param;
    }
}
