package info.batcloud.wxc.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreShopProcurementRelation {

  private Integer id;
  private Integer foodId;
  private String foodName;
  private Integer shopProcurementId;
  private Integer foodNum;
  private Double foodPrice;
  private Integer createTime;
  private String date;
  private Integer actualArrivalNum;
  private Double actualArrivalSumprice;
  private Integer skuid;
  private Integer updateTime;


}
