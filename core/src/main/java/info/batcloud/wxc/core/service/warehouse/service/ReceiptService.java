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
                                 String endTime,String logisticsNo,Integer pageNum,String receiptNo,Integer status);

    /**
     *
     * describe 修改收货单信息
     * @author V
     * @date 18/2/2023 下午4:11
     * @param
     * @return
     */
    Integer updateReceiptOrderToApp(Integer id,Double arrivePrice,Integer arrivaNum,String remark,Integer status);



    /**
     *
     * describe获取门店收货单列表
     * @author V
     * @date 20/2/2023 下午2:59
     * @param
     * @return
     */
    PageInfo getReceiptByStoreId(Integer storeId,Integer page);



    /**
     *
     * describe 获取收货单商品详情
     * @author V
     * @date 20/2/2023 下午3:35
     * @param
     * @return
     */
     PageInfo getReceiptGoodsById(Integer id,Integer page);

}
