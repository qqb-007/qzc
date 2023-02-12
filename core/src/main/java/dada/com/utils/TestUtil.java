package dada.com.utils;

import dada.com.DaDaClient;
import dada.com.request.*;
import dada.com.response.*;

import java.util.List;

public class TestUtil {
    public static void main(String[] args) {
        DaDaClient client = new DaDaClient();

//        CityCodeRequest request = new CityCodeRequest();
//        CityCodeResponse response = client.request(request);
//        List<CityCodeResponse.Result> list = response.getResult();
//        for (CityCodeResponse.Result result : list) {
//            System.out.println(result.getCityName());
//        }

//        ReAddOrderRequest request = new ReAddOrderRequest();
//        request.setCallback("www.example.com/example");
//        request.setCargoPrice(100.0);
//        request.setCityCode("021");
//        request.setShopNo("20da782f5ba9479d");
//        request.setOriginId("781944869");
//        request.setCityCode("021");
//        request.setIsPrepay(0);
//        request.setCargoWeight(1.0);
//        // 填写收货人信息
//        request.setReceiverName("李四");
//        request.setReceiverAddress("东方渔人码头");
//        request.setReceiverLat(31.257801);
//        request.setReceiverLng(121.538842);
//        request.setReceiverPhone("13011112222");
//        ReAddOrderResponse response = client.request(request);
//        ReAddOrderResponse.Result result = response.getResult();
//        Double tips = result.getTips();



//        OrderInfoRequest request = new OrderInfoRequest();
//        request.setOrderId("781944869");
//        OrderInfoResponse response = client.request(request);
//        OrderInfoResponse.Result result = response.getResult();
//        Integer integer = result.getStatusCode();

        GetReasonRequest reasonRequest = new GetReasonRequest();
        GetReasonResponse response = client.request(reasonRequest);
        List<GetReasonResponse.Result> resultList = response.getResult();
        Integer reasonId = 0;
        String reason = "需要取消配送";
        for (GetReasonResponse.Result result : resultList) {
            if (result.getReason().contains("其他")) {
                reasonId = result.getId();
                reason = result.getReason();
                break;
            }
        }
        if (reasonId == 0) {
            reasonId = resultList.get(resultList.size() - 1).getId();
        }
        CancelOrderRequest request = new CancelOrderRequest();
        request.setOrderId("3282243");
        request.setCancelReasonId(reasonId.toString());
        request.setCancelReason(reason);
        CancelOrderResponse response1 = client.request(request);
        CancelOrderResponse.Result result = response1.getResult();
        Double fee = result.getDeduct_fee();



//        CreateShopRequest request = new CreateShopRequest();
//        request.setBusiness(13);
//        request.setContacName("测试店铺");
//        request.setStationName("测试店铺");
//        request.setLat(30.303488);
//        request.setLng(120.369402);
//        request.setOriginShopId("781944");
//        request.setPhone("18339173308");
//        request.setStationAddress("新加坡科技园");
//        CreateShopResponse response = client.request(request);
//        CreateShopResponse.Result result = response.getResult();
//        System.out.println(result.getSuccess());




//        GetOrderPriceRequest request = new GetOrderPriceRequest();
//        request.setCallback("http://47.96.187.223/dd-peisong/order/status");
//        request.setCargoPrice(100.0);
//        request.setCityCode("021");
//        request.setShopNo("20da782f5ba9479d");
//        request.setOriginId("78194869");
//        request.setCityCode("021");
//        request.setIsPrepay(0);
//        request.setCargoWeight(1.0);
//        // 填写收货人信息
//        request.setReceiverName("李四");
//        request.setReceiverAddress("东方渔人码头");
//        request.setReceiverLat(31.257801);
//        request.setReceiverLng(121.538842);
//        request.setReceiverPhone("13011112222");
//        GetOrderPriceResponse response = client.request(request);
//        GetOrderPriceResponse.Result result = response.getResult();
//        SendOrderRequest request1 = new SendOrderRequest();
//        request1.setDeliveryNo(result.getDeliveryNo());
//        SendOrderResponse response1 = client.request(request1);
//        String result1 = response1.getResult();
//        System.out.println(result);
    }
}
