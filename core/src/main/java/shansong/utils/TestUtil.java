package shansong.utils;

import org.apache.commons.lang3.time.DateUtils;
import shansong.ShanSongClient;
import shansong.request.*;
import shansong.response.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestUtil {
    public static void main(String[] args) {
        String phone = "15658469214,5721";
        System.out.println(phone);
        String replace = phone.replace("_", "#").replace(" ","").replace(",","#");

        System.out.println(replace);
//        ShanSongClient client = new ShanSongClient();
//        GetPriceResquest resquest = new GetPriceResquest();
//        resquest.setAppointType(0);
//        resquest.setCityName("杭州市");
//        GetPriceResquest.Sender sender = new GetPriceResquest.Sender();
//        sender.setFromAddress("钱塘新区新加坡科技园高科技孵化园区");
//        sender.setFromMobile("18339173308");
//        sender.setFromSenderName("大猫");
//        sender.setFromLatitude("30.309738");
//        sender.setFromLongitude("120.375794");
//        resquest.setSender(sender);
//        GetPriceResquest.Receiver receiver = new GetPriceResquest.Receiver();
//        receiver.setGoodType("10");
//        receiver.setOrderingSourceNo("110");
//        receiver.setOrderingSourceType("4");
//        receiver.setOrderNo("110110");
//        receiver.setRemarks("测试订单");
//        receiver.setToAddress("君和商务楼");
//        receiver.setToLatitude("30.310459");
//        receiver.setToLongitude("120.28578");
//        receiver.setToMobile("18339173308");
//        receiver.setToReceiverName("秦");
//        receiver.setWeight("2");
//        List<GetPriceResquest.Receiver> list = new ArrayList<>();
//        list.add(receiver);
//        resquest.setReceiverList(list);
//        GetPriceResponse response = client.request(resquest);
//        System.out.println(response.getData());
//
//        SendOrderRequest request = new SendOrderRequest();
//        request.setIssOrderNo(response.getData().getOrderNumber());
//        SendOrderResponse response1 = client.request(request);
//        System.out.println(response1.getData());

//        AddFeeRequest request = new AddFeeRequest();
//        request.setAdditionAmount("200");
//        request.setIssOrderNo("TDH2022022817625978");
//        AddFeeResponse response = client.request(request);

//        GetLocationRequest request = new GetLocationRequest();
//        request.setIssOrderNo("TDH2022022817625978");
//        GetLocationResponse response = client.request(request);

//        GetStatusRequest request = new GetStatusRequest();
//        request.setIssOrderNo("TDH2022022817625978");
//        request.setThirdOrderNo("110110");
//        GetStatusResponse response = client.request(request);

//        CancelRequest re = new CancelRequest();
//        re.setIssOrderNo("TDH2022022817625978");
//        CancelResponse cancelResponse = client.request(re);

//        CreateShopRequest request = new CreateShopRequest();
//        request.setAddress("河南理工大学");
//        request.setAddressDetail("西门");
//        request.setCityName("焦作市");
//        request.setGoodType("10");
//        request.setLatitude("35.193959");
//        request.setLongitude("113.274063");
//        request.setOperationType("1");
//        request.setPhone("18339173308");
//        request.setStoreName("测试店铺");
//        CreateShopResponse response = client.request(request);
    }
}
