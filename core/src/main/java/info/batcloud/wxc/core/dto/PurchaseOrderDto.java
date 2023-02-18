package info.batcloud.wxc.core.dto;

import com.alibaba.fastjson.JSONObject;
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
    private String store;
    private Integer storeId;
    private String supplier;
    private Integer supplierId;
    private Integer procurementPlanId;
    private String procurementPlan;
    private Integer procurementType;
    private Integer procurementNum;
    private Double procurementPrice;
    private Integer arrivalNum;
    private Double arrivalPrice;
    private String logisticsNo;
    private String receiptNo;
    private Integer receiptId;
    private Integer createTime;
    private List<GoodsDto> foodList;

}
