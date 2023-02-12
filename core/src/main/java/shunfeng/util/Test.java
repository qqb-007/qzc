package shunfeng.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import info.batcloud.wxc.core.config.ShunfengPeisongApp;
import info.batcloud.wxc.core.helper.DistanceHelper;
import org.aspectj.weaver.ast.Var;
import shunfeng.ShunfengClent;
import shunfeng.request.*;
import shunfeng.response.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ShunfengPeisongApp app = new ShunfengPeisongApp();
        app.setDevId(1566410744);
        app.setDevKey("81a9b8bfab59cac62ca22e6011d4f45f");
        ShunfengClent clent = new ShunfengClent();
        clent.setDevId(app.getDevId());
        clent.setDevKey(app.getDevKey());
//        GetSfPriceReq req = new GetSfPriceReq();
//        req.setProductType(6);
//        req.setReturnFlag(511);
//        req.setShopId("3243279847393");
//        req.setUserAddress("清友园");
//        req.setUserlat("40.04");
//        req.setUserLng("116.43");
//        req.setWeight(1);
//        GetSfPriceRes res = clent.execute(req);
//        GetSfPriceRes.Result result = res.getResult();
//        System.out.println(result);

//        CancelOrderReq req = new CancelOrderReq();
//        req.setOrderId("JS4159496257306");
//        CancelOrderRes res = clent.execute(req);
//        CancelOrderRes.Result result = res.getResult();
//        System.out.println(result);

//        GetOrderInfoReq req = new GetOrderInfoReq();
//        req.setOrderId("JS4159496257300");
//        GetOrderInfoRes res = clent.execute(req);
//        GetOrderInfoRes.Result result = res.getResult();
//        System.out.println(result);

        GetSfLocationReq req = new GetSfLocationReq();
        req.setOrderId("JS4159496257306");
        GetSfLocationRes res = clent.execute(req);
        GetSfLocationRes.Result result = res.getResult();
        System.out.println(res);

//        CreateOrderReq req = new CreateOrderReq();
//        req.setOrderSource("测试订单");
//        //req.setIsAppoint();
//        req.setShopOrderId("7819448");
//        //CreateOrderReq.Shop shop = new CreateOrderReq.Shop();
//
//        //req.setShop();
//        //req.setExpectTime();
//        CreateOrderReq.OrderDetail detail = new CreateOrderReq.OrderDetail();
//        List<CreateOrderReq.ProductDetail> list = new ArrayList<>();
//        CreateOrderReq.ProductDetail productDetail = new CreateOrderReq.ProductDetail();
//        productDetail.setProductName("测试商品");
//        productDetail.setProductNum(10);
//        list.add(productDetail);
//        detail.setProductDetail(list);
//        detail.setProductNum(10);
//        detail.setProductType(6);
//        detail.setProductTypeNum(1);
//        detail.setTotalPrice(100);
//        detail.setWeightGram(100);
//
//        req.setOrderDetail(detail);
//        req.setOrderSequence("10");
//        req.setOrderTime(System.currentTimeMillis()/1000);
//        CreateOrderReq.Receive receive = new CreateOrderReq.Receive();
//        receive.setUserLat("40.01");
//        receive.setUserLng("116.35");
//        receive.setUser_phone("13881979410");
//        receive.setUserAddress("北京市海淀区学清嘉创大厦A座15层");
//        receive.setUserName("顺丰同城");
//        //req.setPayType();
//        req.setReceive(receive);
//        req.setRemark("");
//        //req.setReturnFlag();
//        req.setShopId("3243279847393");
//        req.setVersion(19);
//        //req.setIsInsured();
//        CreateOrderRes res = clent.execute(req);
//        CreateOrderRes.Result result = res.getResult();
        System.out.println(res);
//        double v = DistanceHelper.calculateLineDistance(116.18669093,39.90739487 ,116.368904, 39.923423);
//        System.out.println(v);

    }
}
