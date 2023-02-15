package info.batcloud.wxc.core.dto;

import info.batcloud.wxc.core.entity.PreShopProcurement;
import info.batcloud.wxc.core.entity.PreShopProcurementRelation;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: PurchaseOrderDto
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@Data
public class PurchaseOrderDto {
    private PreShopProcurement preShopProcurement;
    private List<PreShopProcurementRelation> preShopProcurementRelations;
}
