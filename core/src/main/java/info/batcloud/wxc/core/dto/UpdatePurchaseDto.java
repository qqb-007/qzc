package info.batcloud.wxc.core.dto;

import lombok.Data;

/**
 * @ClassName: UpdatePurchaseDto
 * @Description:
 * @Author V
 * @Date 16/2/2023
 * @Version 1.0
 */
@Data
public class UpdatePurchaseDto {
    private Integer id;
    private String logisticsNo;
    private String receiptNo;
    private Integer procurementType;
    private Integer procurementNum;
    private Integer procurementPrice;
    private Integer arrivalNum;
    private Integer arrivalPrice;
}
