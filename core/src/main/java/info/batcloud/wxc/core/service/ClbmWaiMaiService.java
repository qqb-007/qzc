package info.batcloud.wxc.core.service;

import com.sankuai.meituan.waimai.opensdk.vo.OrderDetailParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderExtraParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderFoodDetailParam;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import info.batcloud.wxc.core.domain.OrderExtra;
import info.batcloud.wxc.core.enums.PickType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.helper.CautionHelper;
import info.batcloud.wxc.core.helper.MeituanHelper;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static info.batcloud.wxc.core.enums.PayType.COD;
import static info.batcloud.wxc.core.enums.PayType.ONLINE;

public interface ClbmWaiMaiService {
    SystemParam getSystemParam();

    boolean checkSign(Map<String, String> param, String url);

    static OrderService.OrderParam toOrder(OrderDetailParam order) {
        OrderService.OrderParam param = new OrderService.OrderParam();
        BeanUtils.copyProperties(order, param);
        param.setPlatOrderId(order.getOrder_id().toString());
        param.setWmOrderIdView(order.getWm_order_id_view().toString());
        param.setAppPoiCode(order.getApp_poi_code());
        param.setWmPoiName(order.getWm_poi_name());
        param.setWmPoiAddress(order.getWm_poi_address());
        param.setWmPoiPhone(order.getWm_poi_phone());
        param.setRecipientAddress(CautionHelper.getCaution(order.getRecipient_address()));
        param.setRecipientName(CautionHelper.getCaution(order.getRecipient_name()));
        param.setRecipientPhone(order.getRecipient_phone());
        param.setShippingFee(order.getShipping_fee());
        if (order.getPoi_receive_detail_yuan() != null && order.getPoi_receive_detail_yuan().getPoiReceive() != null) {
            param.setTotal(Float.valueOf(order.getPoi_receive_detail_yuan().getPoiReceive()));
        } else {
            param.setTotal(order.getTotal().floatValue());
        }
        param.setDaySeq(order.getDay_seq());
        param.setOriginalPrice(order.getOriginal_price().floatValue());
        if (order.getPick_type() == null) {
            order.setPick_type(0);
        }
        switch (order.getPick_type()) {
            case 0:
                param.setPickType(PickType.PT);
                break;
            case 1:
                param.setPickType(PickType.ZQ);
                break;
        }
        param.setLat(order.getLatitude());
        param.setLng(order.getLongitude());
        param.setDeliveryTime(order.getDelivery_time());
        if (order.getExpect_deliver_time() > 0) {
            param.setExpectedDeliveryTime(Long.valueOf(order.getExpect_deliver_time()));
        } else {
            param.setExpectedDeliveryTime(order.getCtime() + 30 * 60);
        }
        param.setCaution(CautionHelper.getCaution(order.getCaution()));
        param.setShipperPhone(order.getShipper_phone());
        param.setPlat(Plat.CLBM);
        param.setStatus(MeituanHelper.toOrderStatus(order.getStatus()));
        param.setPayType(order.getPay_type() == 1 ? COD : ONLINE);
        param.setCreateTime(new Date(order.getCtime() * 1000));
        param.setUpdateTime(new Date(order.getUtime() * 1000));
        List<OrderFoodDetailParam> detailList = order.getDetail();
        param.setDetailParamList(new ArrayList<>());
        for (OrderFoodDetailParam od : detailList) {
            OrderService.OrderDetailParam p = new OrderService.OrderDetailParam();
            p.setFoodName(od.getFood_name() + od.getFood_property());
            p.setSkuId(od.getSku_id());
            p.setQuantity(Float.valueOf(od.getQuantity()));
            p.setPrice(od.getPrice());
            p.setBoxNum(od.getBox_num());
            p.setBoxPrice(od.getBox_price());
            p.setUnit(od.getUnit());
            p.setSpec(od.getSpec());
            p.setCode(od.getApp_food_code());
            param.getDetailParamList().add(p);
        }
        List<OrderExtra> extraList = new ArrayList<>();
        if (order.getExtras() != null) {
            for (OrderExtraParam orderExtraParam : order.getExtras()) {
                OrderExtra extra = new OrderExtra();
                extra.setType(orderExtraParam.getType());
                extra.setReduceFee(orderExtraParam.getReduce_fee());
                extra.setPlatCharge(orderExtraParam.getMt_charge() == null ? 0f : orderExtraParam.getMt_charge());
                extra.setRemark(CautionHelper.getCaution(orderExtraParam.getRemark()));
                extra.setStoreCharge(orderExtraParam.getPoi_charge() == null ? 0f : orderExtraParam.getPoi_charge());
                extraList.add(extra);
            }
        }
        param.setExtraList(extraList);
        //优惠信息
        return param;
    }
}
