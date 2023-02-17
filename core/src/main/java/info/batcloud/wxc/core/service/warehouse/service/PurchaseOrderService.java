package info.batcloud.wxc.core.service.warehouse.service;

import com.github.pagehelper.PageInfo;
import info.batcloud.wxc.core.dto.PurchaseOrderDto;
import info.batcloud.wxc.core.dto.UpdatePurchaseDto;
import info.batcloud.wxc.core.entity.PreShopProcurement;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName: PurchaseOrderService
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
public interface PurchaseOrderService {
    /**
     *
     * describe 创建采购订单
     * @author V
     * @date 14/2/2023 下午4:17
     * @param
     * @return
     */
    Integer createPurchaseOrder(PurchaseOrderDto purchaseOrderDto);

    /**
     *
     * describe 查询采购订单
     * @author V
     * @date 15/2/2023 上午9:39
     * @param
     * @return
     */
    PageInfo<PreShopProcurement> getShopProcurement(String orderNo, Integer storeId,
                                                Integer pageSize);

    /**
     *
     * describe删除采购单
     * @author V
     * @date 16/2/2023 上午10:21
     * @param
     * @return
     */

    Integer delPurchaseById(Integer id);

    /**
     *
     * describe 修改采购订单
     * @author V
     * @date 16/2/2023 下午1:38
     * @param
     * @return
     */
    Integer updatePurchase(UpdatePurchaseDto updatePurchaseDto);


    /**
     *
     * describe 获取采购单详情数据
     * @author V
     * @date 17/2/2023 上午11:25
     * @param
     * @return
     */
    PageInfo getPurchaseRelation(Integer id,Integer pageNum);

    /**
     *
     * describe 获取交货单详情数据
     * @author V
     * @date 17/2/2023 上午11:25
     * @param
     * @return
     */
    PageInfo getPurchaseRelationToReceipt(Integer id,Integer pageNum);
}
