package info.batcloud.wxc.core.service.warehouse.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: ReceiptService
 * @Description:
 * @Author V
 * @Date 17/2/2023
 * @Version 1.0
 */
public interface ReceiptService {
    /**
     *
     * describe 获取收货单
     * @author V
     * @date 17/2/2023 下午2:54
     * @param
     * @return
     */
    PageInfo getReceiptOrderList( Integer storeId, String startTime,
                                 String endTime,String logisticsNo,Integer pageNum,String receiptNo);
}
