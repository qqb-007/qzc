package info.batcloud.wxc.core.dto;

import info.batcloud.wxc.core.entity.PreRequireGoodsOrders;
import info.batcloud.wxc.core.entity.PreRequireGoodsOrdersRelation;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: RequireGoodsDto
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@Data
public class RequireGoodsDto {
    private PreRequireGoodsOrders preRequireGoodsOrders;
    private List<PreRequireGoodsOrdersRelation> requireGoodsOrdersRelation;
}
