package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.service.PrinterService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PrintHelper {

    public static String toCancelContent(PrinterService.CancelInfo cancelInfo) {
        StringBuffer sb = new StringBuffer();
        sb.append("<B>#" + cancelInfo.getDaySeq() + " " + cancelInfo.getPlat().getTitle() + "外卖申请退款</B><BR>");
        sb.append("<BR><C>*" + cancelInfo.getShopName() + "*</C><BR>");
        sb.append("<BR>订单ID: " + cancelInfo.getOrderId() + "<BR>");
        sb.append("时间: " + cancelInfo.getDate() + "<BR>");
        sb.append("地址: " + cancelInfo.getAddress());
        sb.append("<BR>姓名: " + cancelInfo.getName());
        sb.append("<BR>电话: " + cancelInfo.getPhone());
        sb.append("<BR>原因: " + cancelInfo.getCancelReson());
        sb.append("<BR>" + StringUtils.repeat("-", 32));
        return sb.toString();
    }

    public static String toContent(PrinterService.OrderInfo order) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("<CB>%s#%s外卖</CB><BR>", order.getDaySeq() + "", order.getPlat().getTitle()));
        if (order.getDeliveryTime() != 0) {
            sb.append(String.format("<CB>%s<CB><BR>", "【预订单】"));
        }
        sb.append(String.format("<L>期望送达时间：%s</L><BR>", DateFormatUtils.format(order.getExcepTime(), "MM-dd HH:mm")));
        sb.append(String.format("<L><C>%s</C></L><BR>", order.getStoreName()));
        sb.append(String.format("下单时间：%s<BR>", DateFormatUtils.format(order.getCreateTime(), "MM-dd HH:mm")));
        if (order.getCaution() != null && order.getCaution().length() > 0 && !order.getCaution().equals("0")) {
            sb.append("<L>备注:" + order.getCaution() + "</L><BR>");
//            if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
//                if (!order.getCaution().startsWith("收餐人隐私号") && !order.getCaution().startsWith(" 【如遇缺货】")) {
//                    sb.append(StringUtils.repeat("-", 32) + "<BR>");
//                    sb.append(String.format("<CB>%s<CB><BR>", "[订单有特殊备注]"));
//                }
//            } else if (order.getPlat() == Plat.ELE) {
//                if (!order.getCaution().startsWith("(缺货时")) {
//                    sb.append(StringUtils.repeat("-", 32) + "<BR>");
//                    sb.append(String.format("<CB>%s<CB><BR>", "[订单有特殊备注]"));
//                }
//            } else if (order.getPlat() == Plat.JDDJ) {
//                if (!order.getCaution().startsWith("n缺货时") && !order.getCaution().equals("用户没有备注")) {
//                    sb.append(StringUtils.repeat("-", 32) + "<BR>");
//                    sb.append(String.format("<CB>%s<CB><BR>", "[订单有特殊备注]"));
//                }
//            }
        }
        sb.append(StringUtils.repeat("-", 32) + "<BR>");
        sb.append(String.format("<L><C>%s</C></L>", order.getRecipientAddress()));
        sb.append(String.format("<C>号码:%s</C>", order.getRecipientPhone()));
        sb.append(String.format("<C>%s</C>", order.getRecipientName()));
        sb.append(StringUtils.repeat("*", 32));
        Integer totalNum = 0;
        for (PrinterService.OrderDetailInfo detailInfo : order.getDetailList()) {
            totalNum = totalNum + (int) detailInfo.getQuantity();
            String title = detailInfo.getFoodName() + " " + detailInfo.getFoodSpec();
            String num;
            if ((int) detailInfo.getQuantity() > 1) {
                num = "[X" + String.valueOf((int) detailInfo.getQuantity()) + "]";
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
                title = l1 + "<BR>";// 添加 单价 数量 总额
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
            sb.append("<BR><L>" + title + "</L><BR>");
        }

        if (order.getGiftName() != null && order.getGiftName().size() > 0) {
            for (int i = 0; i < order.getGiftName().size(); i++) {
                totalNum = totalNum + order.getGiftNum().get(i);
                sb.append("<BR><L>赠品： " + order.getGiftName().get(i) + "</L>" + " " + "<L>X" + order.getGiftNum().get(i) + "</L><BR>");
            }
        }
        sb.append(StringUtils.repeat("-", 32) + "<BR>");

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

        sb.append("<BR><L>" + s + "</L><BR>");
        sb.append(StringUtils.repeat("-", 32) + "<BR>");


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
        sb.append("<L>订单号:" + order.getOrderId() + "</L><BR>");
        if (order.getPlat() == Plat.JDDJ) {
            String barCode = getDigitBarCode(order.getOrderId());
            sb.append(barCode);
        }
        sb.append("<PLUGIN>");
        return sb.toString();
    }

    public static String toXyyContent(PrinterService.OrderInfo order) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("<CB>***#%s%s外卖**</CB><BR>", order.getDaySeq() + "", order.getPlat().getTitle()));
        if (order.getDeliveryTime() != null && order.getDeliveryTime() != 0) {
            sb.append(String.format("<CB>%s<CB><BR>", "【预订单】"));
        }
        sb.append(String.format("<HB>期望送达时间：%s</HB><BR>", DateFormatUtils.format(order.getExcepTime(), "MM-dd HH:mm")));
        sb.append(String.format("<C>*%s*</C><BR>", order.getStoreName()));
        sb.append(String.format("下单时间：%s<BR>", DateFormatUtils.format(order.getCreateTime(), "MM-dd HH:mm")));
        if (order.getCaution() != null && order.getCaution().length() > 0 && !order.getCaution().equals("0")) {
            sb.append("<HB>备注:" + order.getCaution() + "</HB><BR>");
            if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
                if (!order.getCaution().startsWith("收餐人隐私号") && !order.getCaution().startsWith(" 【如遇缺货】")) {
                    sb.append(StringUtils.repeat("-", 32) + "<BR>");
                    sb.append(String.format("<CB>%s<CB><BR>", "[订单有特殊备注]"));
                }
            } else if (order.getPlat() == Plat.ELE) {
                if (!order.getCaution().startsWith("(缺货时")) {
                    sb.append(StringUtils.repeat("-", 32) + "<BR>");
                    sb.append(String.format("<CB>%s<CB><BR>", "[订单有特殊备注]"));
                }
            } else if (order.getPlat() == Plat.JDDJ) {
                if (!order.getCaution().startsWith("n缺货时") && !order.getCaution().equals("用户没有备注")) {
                    sb.append(StringUtils.repeat("-", 32) + "<BR>");
                    sb.append(String.format("<CB>%s<CB><BR><BR>", "[订单有特殊备注]"));
                }
            }
        }
        sb.append(StringUtils.repeat("-", 32) + "<BR>");
        sb.append(String.format("<HB><C>%s</C></HB><BR>", order.getRecipientAddress()));
        sb.append(String.format("<C>号码:%s</C><BR>", order.getRecipientPhone()));
        sb.append(String.format("<C>%s</C><BR>", order.getRecipientName()));
        sb.append(StringUtils.repeat("*", 32));
        for (PrinterService.OrderDetailInfo detailInfo : order.getDetailList()) {
            String title = detailInfo.getFoodName() + " " + detailInfo.getFoodSpec();
            String num;
            if ((int) detailInfo.getQuantity() > 1) {
                num = "[X" + String.valueOf((int) detailInfo.getQuantity()) + "]";
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
                title = l1 + "<BR>";// 添加 单价 数量 总额
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
            sb.append("<BR><HB>" + title + "</HB><BR>");
        }
        if (order.getGiftName() != null && order.getGiftName().size() > 0) {
            for (int i = 0; i < order.getGiftName().size(); i++) {
                sb.append("<BR><HB>赠品： " + order.getGiftName().get(i) + "</L>" + " " + "<L>X" + order.getGiftNum().get(i) + "</HB><BR>");
            }
        }
        sb.append(StringUtils.repeat("-", 32) + "<BR>");


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
        sb.append("<HB>订单号:" + order.getOrderId() + "</HB><BR>");
        //sb.append("<PLUGIN>");
        return sb.toString();
    }

    public static String addSpace(String str, int size) {
        int len = str.length();
        if (len < size) {
            for (int i = 0; i < size - len; i++) {
                //str += " ";
                str = " " + str;
            }
        }
        return str;
    }

    public static String titleAddSpace(String str) {
        int k = 0;
        int b = 14;
        try {
            k = str.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < b - k; i++) {
            str += " ";
        }
        return str;
    }

    public static String getStringByEnter(int length, String string) throws Exception {
        for (int i = 1; i <= string.length(); i++) {
            if (string.substring(0, i).getBytes("GBK").length > length) {
                return string.substring(0, i - 1) + "<BR>" + getStringByEnter(length, string.substring(i - 1));
            }
        }
        return string;
    }

    public static Boolean isEn(String str) {
        Boolean b = false;
        try {
            b = str.getBytes("GBK").length == str.length();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }

    public static List<String> getStrList(String inputString, int length, int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length, (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    public static String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }


    private static String getDigitBarCode(String input) {
        String chr = "";
        String laststr = "";
        byte[] codeB = {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39}; // 匹配字符集B
        byte[] codeC = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x20,
                0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2A, 0x2B, 0x2C, 0x2D, 0x2E, 0x2F, 0x30, 0x31,
                0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3A, 0x3B, 0x3C, 0x3D, 0x3E, 0x3F, 0x40, 0x41, 0x42,
                0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4A, 0x4B, 0x4C, 0x4D, 0x4E, 0x4F, 0x50, 0x51, 0x52, 0x53,
                0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5A, 0x5B, 0x5C, 0x5D, 0x5E, 0x5F, 0x60, 0x61, 0x62, 0x63}; // 匹配字符集C
        int length = input.length();
        byte[] b = new byte[100];
        b[0] = 0x1b;
        b[1] = 0x64;
        b[2] = 0x02;
        b[3] = 0x1d;
        b[4] = 0x48;
        b[5] = 0x32; // 0x32打印条形码下的数字， 0x30不打印条形码下的数字
        b[6] = 0x1d;
        b[7] = 0x68;
        b[8] = 0x50; // 7F是最大的高度
        b[9] = 0x1d;
        b[10] = 0x77;
        b[11] = 0x02; // 2-6  条码宽度
        b[12] = 0x1d;
        b[13] = 0x6b;
        b[14] = 0x49; // code128
        b[15] = (byte) (length + 2); // 得出条形码长度
        b[16] = 0x7b;
        b[17] = 0x42;
        boolean result = input.matches("[0-9]+");//判断是否为纯数字
        if (length > 14 && result == true) {
            b[17] = 0x43;
            int j = 0;
            int key = 18;
            int ss = length / 2;// 初始化数组长度
            String temp = "";
            int iindex = 0;
            for (int i = 0; i < ss; i++) {
                temp = input.substring(j, j + 2);
                iindex = Integer.valueOf(temp);
                j = j + 2;
                if (iindex == 0) {
                    chr = "";
                    if (b[key + i - 1] == '0' && b[key + i - 2] == '0') {
                        b[key + i] = codeB[0];
                        b[key + i + 1] = codeB[0];
                        key += 1;
                    } else {
                        if (b[key + i - 1] == 'C' && b[key + i - 2] == '{') {//判断前面的为字符集C时转换字符集B
                            b[key + i - 2] = 0x7b;
                            b[key + i - 1] = 0x42;
                            b[key + i] = codeB[0];
                            b[key + i + 1] = codeB[0];
                            key += 1;
                        } else {
                            b[key + i] = 0x7b;
                            b[key + i + 1] = 0x42;
                            b[key + i + 2] = codeB[0];
                            b[key + i + 3] = codeB[0];
                            key += 3;
                        }
                    }
                } else {
                    // 判断前面的为字符集B,此时要转换字符集C
                    if (b[key + i - 1] == '0' && b[key + i - 2] == '0' && chr != "chr") {
                        b[key + i] = 0x7b;
                        b[key + i + 1] = 0x43;
                        b[key + i + 2] = codeC[iindex];
                        key += 2;
                    } else {
                        chr = "";
                        b[key + i] = codeC[iindex];
                        if (iindex == 48) chr = "chr";//判断chr(48)等于0的情况
                    }
                }
            }
            int lastKey = getLastIndex(b);
            if (length % 2 > 0) {
                int lastnum = Integer.valueOf(input.substring(input.length() - 1)); // 取得字符串的最后一个数字
                if (b[lastKey] == '0' && b[lastKey - 1] == '0') {//判断前面的为字符集B,此时不需要转换字符集
                    b[lastKey + 1] = codeB[lastnum];
                } else {
                    b[lastKey + 1] = 0x7b;
                    b[lastKey + 2] = 0x42;
                    b[lastKey + 3] = codeB[lastnum];
                }
            }
            // 得出条形码长度
            int blength = getLastIndex(b);
            int len = (blength - 15);
            b[15] = (byte) (len);
            String str = "";
            str = new String(b);
            laststr = str;

            String Last_two = input.substring(input.length() - 2, input.length());
            int Last_two_int = 0;
            Last_two_int = Integer.valueOf(Last_two);
            if (Last_two_int > 32) {
                laststr = laststr.trim().substring(1);
            }
        } else { // 1-14位数字的条形码进来这个区间
            b[15] = (byte) (length + 2);
            laststr = new String(b);
            laststr = laststr.substring(0, 18);
            laststr += input;
        }
        return laststr;
    }


    // 获取数组中最后一个不是0x0的元素的下标
    private static int getLastIndex(byte[] b) {
        if (b == null || b.length == 0) {
            return 0;
        }
        int blength = 0;
        for (int i = b.length - 1; i >= 0; i--) {
            if (b[i] != 0x0) {
                blength = i;
                break;
            }
        }
        return blength;
    }

}
