package info.batcloud.wxc.core.service.warehouse.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sankuai.meituan.waimai.opensdk.util.StringUtil;
import info.batcloud.wxc.core.entity.BaseException;
import info.batcloud.wxc.core.entity.PreReceiptOrders;
import info.batcloud.wxc.core.service.warehouse.dao.PreReceiptOrdersDao;
import info.batcloud.wxc.core.service.warehouse.dao.PurchaseOrderDao;
import info.batcloud.wxc.core.service.warehouse.service.ReceiptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: ReceiptServiceImpl
 * @Description:
 * @Author V
 * @Date 17/2/2023
 * @Version 1.0
 */
@Service
public class ReceiptServiceImpl  implements ReceiptService {
    @Resource
    private PreReceiptOrdersDao preReceiptOrdersDao;
    @Override
    public PageInfo getReceiptOrderList(Integer storeId, String startTime, String endTime, String logisticsNo, Integer pageNum,String receiptNo) {
        PageHelper.startPage(pageNum,10,"`order`.create_time  desc");
        List<PreReceiptOrders> receiptOrder = preReceiptOrdersDao.getReceiptOrder(storeId, convertToTimestamp(startTime) ,convertToTimestamp(endTime) , logisticsNo,receiptNo);
        PageInfo pageInfo=new PageInfo(receiptOrder);
        return pageInfo;
    }

    @Override
    public Integer updateReceiptOrderToApp(Integer id, Double arrivePrice, Integer arrivaNum, String remark,Integer status) {
        PreReceiptOrders preReceiptOrders=new PreReceiptOrders();
        preReceiptOrders.setId(id);
        preReceiptOrders.setRemark(remark);
        preReceiptOrders.setArrivePrice(arrivePrice);
        preReceiptOrders.setArrivaNum(arrivaNum);
        preReceiptOrders.setStatus(status);
        return preReceiptOrdersDao.update(preReceiptOrders);
    }

    //传入指定时间
    public Long convertToTimestamp(String time) {
        if (StringUtils.isEmpty(time)){
            return 0l;
        }
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            long timestamp = cal.getTimeInMillis();
         return timestamp/1000;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }

    }

}
