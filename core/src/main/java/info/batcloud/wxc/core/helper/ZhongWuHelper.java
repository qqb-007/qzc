package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.service.PrinterService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ZhongWuHelper {
    public static String toContent(PrinterService.OrderInfo order){
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("<S1><C>***#%s%s外卖***</C></S1>", order.getDaySeq() + "", order.getPlat().getTitle()));
        sb.append(String.format("<C>*%s*</C>\r\n", order.getStoreName()));
        sb.append(String.format("下单时间：%s\r", DateFormatUtils.format(order.getCreateTime(), "MM-dd HH:mm")));
        sb.append(String.format("<H1>期望送达时间：%s</H1>\r\n", DateFormatUtils.format(order.getExcepTime(), "MM-dd HH:mm")));
        if (order.getCaution() != null && order.getCaution().length() > 0) {
            sb.append("<H1>备注:" + order.getCaution() + "</H1>\r\n");
        }
        sb.append(StringUtils.repeat("-", 32) + "\r\n");
        sb.append(String.format("<H1><C>%s</C></H1>", order.getRecipientAddress()));
        sb.append(String.format("<C>号码:%s</C>", order.getRecipientPhone()));
        sb.append(String.format("<C>%s</C>", order.getRecipientName()));
        sb.append(StringUtils.repeat("*", 32));
        for (PrinterService.OrderDetailInfo detailInfo : order.getDetailList()) {
            String title = detailInfo.getFoodName() + " " + detailInfo.getFoodSpec();
            String num = String.valueOf(detailInfo.getQuantity());
            num = PrintHelper.addSpace(num, 3);
            String otherStr = " " + num;
            int tl = 0;
            try {
                tl = title.getBytes("GBK").length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int spaceNum = (tl / 28 + 1) * 28 - tl;
            if (tl < 28) {
                for (int k = 0; k < spaceNum; k++) {
                    title += " ";
                }
                title += otherStr;
            } else if (tl == 28) {
                title += otherStr;
            } else {
                List<String> list = null;
                if (PrintHelper.isEn(title)) {
                    list = PrintHelper.getStrList(title, 28);
                } else {
                    list = PrintHelper.getStrList(title, 28 / 2);
                }
                String s0 = PrintHelper.titleAddSpace(list.get(0));
                title = s0 + otherStr + "\r\n";// 添加 单价 数量 总额
                String s = "";
                for (int k = 1; k < list.size(); k++) {
                    s += list.get(k);
                }
                try {
                    s = PrintHelper.getStringByEnter(28, s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                title += s;
            }
            sb.append("\r\n<H1>" + title + "</H1>\r\n");
        }
        if (order.getGiftName() != null && order.getGiftName().size() > 0) {
            for (int i = 0; i < order.getGiftName().size(); i++) {
                sb.append("\r\n<H1>赠品： " + order.getGiftName().get(i) + "</H1>" + " " + "<H1>X" + order.getGiftNum().get(i) + "</H1>\r\n");
            }
        }
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
        sb.append("<H1>订单号:" + order.getOrderId() + "</H1>\r\n");
        return sb.toString();
    }
}
