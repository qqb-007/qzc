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
  private Long createTime;
  private String date;
  private Integer actualArrivalNum;
  private Double actualArrivalSumprice;
  private Integer skuId;
  private Long updateTime;
  private Integer num;
  private Double inputPrice;
  private String img;
  private String foodPicture;


}
