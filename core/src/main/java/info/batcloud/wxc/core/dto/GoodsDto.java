package info.batcloud.wxc.core.dto;

import lombok.Data;

/**
 * @ClassName: GoodsDto
 * @Description:
 * @Author V
 * @Date 17/2/2023
 * @Version 1.0
 */
@Data
public class GoodsDto {
    private Integer foodId;
    private Integer skuId;
    private Double inputPrice;
    private Integer num;
    private String foodName;
}
