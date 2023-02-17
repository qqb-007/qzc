package info.batcloud.wxc.core.service.warehouse.service.impl;

import info.batcloud.wxc.core.dto.PurchaseOrderDto;
import info.batcloud.wxc.core.service.warehouse.dao.PurchaseOrderDao;
import info.batcloud.wxc.core.service.warehouse.service.PurchaseOrderService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @ClassName: PurchaseOrderServiceImpl
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@Service
public class PurchaseOrderServiceImpl  implements PurchaseOrderService {
    @Inject
    private PurchaseOrderDao purchaseOrderDao;
    @Override
    public Integer createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        purchaseOrderDao.save(purchaseOrderDto.getPreShopProcurement());
        return null;
    }
}
