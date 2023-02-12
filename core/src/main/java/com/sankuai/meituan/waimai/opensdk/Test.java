package com.sankuai.meituan.waimai.opensdk;

import com.sankuai.meituan.waimai.opensdk.constants.PoiQualificationEnum;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.PoiParam;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;

import java.util.List;

/**
 * Created by zhangyuanbo02 on 15/12/9.
 */
public class Test {

    private final static SystemParam sysPram = new SystemParam("这里请填写app_id", "这里请填写门店secret");
//    private final static String appPoiCode = "ceshi_POI_II";
    private final static String appPoiCode = "ceshi_02";

    public static void main(String[] args) {
        poiGetIds();
    }

    public static void poiSave() {
        PoiParam PoiPram = new PoiParam();
        PoiPram.setApp_poi_code("ceshi_poi1");
        PoiPram.setName("我的门店");
        PoiPram.setAddress("我的门店的地址");
        PoiPram.setLatitude(40.810249);
        PoiPram.setLongitude(117.502289);
        PoiPram.setPhone("13425355733");
        PoiPram.setShipping_fee(2f);
        PoiPram.setShipping_time("09:00-13:30,19:00-21:40");
        PoiPram.setPic_url("http://cdwuf.img46.wal8.com/img46/525101_20150811114016/144299728957.jpg");
        PoiPram.setOpen_level(1);
        PoiPram.setIs_online(1);
        PoiPram.setPre_book_min_days(1);
        PoiPram.setPre_book_max_days(2);
        PoiPram.setApp_brand_code("zhajisong");
        PoiPram.setThird_tag_name("麻辣烫");

        try {
            String result = APIFactory.getPoiAPI().poiSave(sysPram, PoiPram);
            System.out.println(result);
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiGetIds() {
        try {
            String result = APIFactory.getPoiAPI().poiGetIds(sysPram);
            System.out.println(result);
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiMget() {
        try {
            List<PoiParam> Poslist = APIFactory.getPoiAPI().poiMget(sysPram, "ceshi_poi1,ceshi_100");
            System.out.println(Poslist);
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiTagList() {
        try {
            System.out.println(APIFactory.getPoiAPI().poiTagList(sysPram));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiOpen() {
        try {
            System.out.println(APIFactory.getPoiAPI().poiOpen(sysPram, "ceshi_poi1"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiClose() {
        try {
            System.out.println(APIFactory.getPoiAPI().poiClose(sysPram, "ceshi_poi1"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiOffline() {
        try {
            System.out.println(APIFactory.getPoiAPI().poiOffline(sysPram, "ceshi_poi1", "缺货"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiOnline() {
        try {
            System.out.println(APIFactory.getPoiAPI().poiOnline(sysPram, "ceshi_poi1"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static void poiQualifySave() {
        try {
            System.out.println(APIFactory.getPoiAPI().poiQualifySave(sysPram, "ceshi_poi1",
                                            PoiQualificationEnum.BUSINESS_LICENSE,
                                            "http://cdwuf.img46.wal8.com/img46/525101_20150811114016/14429972901.jpg",
                                            "2016-01-01", "天安门", "11019123"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiSendTimeSave() {
        try {
            System.out.println(APIFactory.getPoiAPI().poiSendTimeSave(sysPram, "ceshi_poi1,ceshi_100", 50));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiAdditionalSave() {
        try {
            System.out.println(APIFactory.getPoiAPI().poiAdditionalSave(sysPram, "ceshi_poi1","cyinchao@sina.com", "zhajisong", "8888"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiUpdatePromotionInfo() {
        try {
            System.out.println(APIFactory.getPoiAPI().poiUpdatePromotionInfo(sysPram, "ceshi_poi1", "这是一条用于测试的门店公告信息"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }



//    public static void main(String[] args) {
//        byte[] imgData = {85,112,108,111,97,100,115,47,80,114,111,100,117,99,116,115,47,49,53,48,55,47,48,49,47,105,109,103,46,106,112,103};
//        try {
//            System.out.println(
//                APIFactory.getImageApi().imageUpload(systemParam, appPoiCode, imgData, "ceshi.jpg"));
//        } catch (ApiOpException e) {
//            e.printStackTrace();
//        } catch (ApiSysException e) {
//            e.printStackTrace();
//        }
//    }


}
