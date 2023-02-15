package info.batcloud.wxc.core.service.warehouse.service;

import info.batcloud.wxc.core.dto.PurchaseOrderDto;

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
}
