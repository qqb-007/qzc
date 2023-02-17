package info.batcloud.wxc.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreRequireGoodsOrders implements Serializable {

  private Integer id;
  private String requireGoodsNo;
  private Integer storeId;
  private String storeName;
  private Integer status;
  private Integer requireGoodsNum;
  private Integer requireGoodsPrice;
  private String remark;
  private Integer skuId;
  private Long createTime;
  private String date;




}
