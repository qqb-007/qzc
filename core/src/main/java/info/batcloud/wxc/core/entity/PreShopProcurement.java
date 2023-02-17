package info.batcloud.wxc.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreShopProcurement  implements Serializable {

  private Integer id;
  private String orderNo;
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
  private Long createTime;
  private String date;

}
