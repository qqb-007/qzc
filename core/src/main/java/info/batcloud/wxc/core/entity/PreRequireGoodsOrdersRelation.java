package info.batcloud.wxc.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreRequireGoodsOrdersRelation implements Serializable {

  private Integer id;
  private Integer requireGoodsId;
  private Integer foodId;
  private String foodName;
  private Integer foodNum;
  private Integer foodPrice;
  private Long createTime;
  private String date;


}
