package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.service.PrinterService;
import info.batcloud.wxc.core.service.YilainyunPrintService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class YiLianYunHelper {
    public static String toContent(PrinterService.OrderInfo order) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("<FH2><center>***#%s%s外卖***</center></FH2>", order.getDaySeq() + "", order.getPlat().getTitle()));
        if (order.getDeliveryTime() != 0) {
            sb.append(String.format("<FH2><center>%s</center></FH2>", "【预订单】"));
        }
        sb.append(String.format("<center>*%s*</center>\r\n", order.getStoreName()));
        sb.append(String.format("下单时间：%s\r", DateFormatUtils.format(order.getCreateTime(), "MM-dd HH:mm")));
        sb.append(String.format("<FH2>期望送达时间：%s</FH2>\r\n", DateFormatUtils.format(order.getExcepTime(), "MM-dd HH:mm")));
        if (order.getCaution() != null && order.getCaution().length() > 0 && !order.getCaution().equals("0")) {
            sb.append("<FH2>备注:" + order.getCaution() + "</FH2>\r\n");
            if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
                if (!order.getCaution().startsWith("收餐人隐私号") && !order.getCaution().startsWith(" 【如遇缺货】")) {
                    sb.append(StringUtils.repeat("-", 32) + "\n");
                    sb.append(String.format("<FH2><center>%s</center></FH2>", "[订单有特殊备注]"));
                }
            } else if (order.getPlat() == Plat.ELE) {
                if (!order.getCaution().startsWith("(缺货时")) {
                    sb.append(StringUtils.repeat("-", 32) + "\n");
                    sb.append(String.format("<FH2><center>%s</center></FH2>", "[订单有特殊备注]"));
                }
            } else if (order.getPlat() == Plat.JDDJ) {
                if (!order.getCaution().startsWith("n缺货时") && !order.getCaution().equals("用户没有备注")) {
                    sb.append(StringUtils.repeat("-", 32) + "\n");
                    sb.append(String.format("<FH2><center>%s</center></FH2>", "[订单有特殊备注]"));
                }
            }
        }


        sb.append(StringUtils.repeat("-", 32) + "\r\n");
        sb.append(String.format("<FH2><center>%s</center></FH2>", order.getRecipientAddress()));
        sb.append(String.format("<center>号码:%s</center>", order.getRecipientPhone()));
        sb.append(String.format("<center>%s</center>", order.getRecipientName()));
        sb.append(StringUtils.repeat("*", 32));
        Integer totalNum = 0;
        for (PrinterService.OrderDetailInfo detailInfo : order.getDetailList()) {
            totalNum = totalNum + (int) detailInfo.getQuantity();
            String title = detailInfo.getFoodName() + " " + detailInfo.getFoodSpec();
            String num;
            if ((int) detailInfo.getQuantity() > 1) {
                num = "<FB>[X" + String.valueOf((int) detailInfo.getQuantity()) + "]</FB>";
            } else {
                num = "X" + String.valueOf((int) detailInfo.getQuantity());
            }
            //String num = "X" + String.valueOf((int) detailInfo.getQuantity());
            String otherStr = PrintHelper.addSpace(num, 5);
            //String otherStr = "X" + num;
            int tl = 0;
            try {
                tl = title.getBytes("GBK").length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int spaceNum = (tl / 26 + 1) * 26 - tl;
            if (tl < 26) {
                for (int k = 0; k < spaceNum; k++) {
                    title += " ";
                }
                title += otherStr;
            } else if (tl == 26) {
                title += otherStr;
            } else {
                List<String> list = null;
                if (PrintHelper.isEn(title)) {
                    list = PrintHelper.getStrList(title, 26);
                } else {
                    list = PrintHelper.getStrList(title, 26 / 2);
                }
                String l1 = list.get(0);
                int fn = 0;
                try {
                    fn = l1.getBytes("GBK").length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                int l1spaceNum = (fn / 26 + 1) * 26 - fn;
                if (fn < 26) {
                    for (int k = 0; k < l1spaceNum; k++) {
                        l1 += " ";
                    }
                    l1 += otherStr;
                } else {
                    l1 += otherStr;
                }
                //String s0 = PrintHelper.titleAddSpace(list.get(0));
                title = l1 + "\n";// 添加 单价 数量 总额
                String s = "";
                for (int k = 1; k < list.size(); k++) {
                    s += list.get(k);
                }
                try {
                    s = PrintHelper.getStringByEnter(26, s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                title += s;
            }
            sb.append("\r\n<FH2>" + title + "</FH2>\r\n");
        }
        if (order.getGiftName() != null && order.getGiftName().size() > 0) {
            for (int i = 0; i < order.getGiftName().size(); i++) {
                totalNum = totalNum + order.getGiftNum().get(i);
                sb.append("\r\n<FH2>赠品： " + order.getGiftName().get(i) + "</FH2>" + " " + "<FH2>X" + order.getGiftNum().get(i) + "</FH2>\r\n");
            }
        }
        sb.append(StringUtils.repeat("-", 32) + "\r\n");
        //统计共有多少个商品
        String s = "共 " + String.valueOf(totalNum) + "件";
        int tl = 0;
        try {
            tl = s.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int spaceNum = (tl / 32 + 1) * 32 - tl;
        for (int k = 0; k < spaceNum; k++) {
            s = " " + s;
        }

        sb.append("\n<FH2><FB>" + s + "</FB></FH2>\n");
        sb.append(StringUtils.repeat("-", 32) + "\r\n");


//        int[] twoPos = new int[]{19, 10};
//        if (order.getPackageBagMoney() != null) {
//            sb.append(alignText(new String[]{"餐盒费", order.getPackageBagMoney().toString()},
//                    twoPos) + "<BR>");
//        }
//        if (order.getShippingFee() != null) {
//            sb.append(alignText(new String[]{"配送费", order.getShippingFee().toString()},
//                    twoPos) + "<BR>");
//        }
//        for (OrderExtra extra : order.getExtraList()) {
//            sb.append(alignText(new String[]{String.format("[%s]", extra.getRemark()), -extra.getReduceFee() + ""},
//                    twoPos) + "<BR>");
//        }
//        sb.append(StringUtils.repeat("*", 32) + "<BR>");
//        sb.append(String.format("<RIGHT>原价：%s元</RIGHT>", order.getTotal()));
        sb.append("<FH2>订单号:" + order.getOrderId() + "</FH2>\r\n");
        return sb.toString();
    }
}
