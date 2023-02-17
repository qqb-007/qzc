package info.batcloud.wxc.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (PreReceiptOrders)实体类
 *
 * @author makejava
 * @since 2023-02-17 14:29:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreReceiptOrders implements Serializable {
    private static final long serialVersionUID = 838912090800227307L;
    
    private Integer id;
    /**
     * 收货单号
     */
    private String receiptNo;
    /**
     * 门店id
     */
    private Integer storeId;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 采购单id
     */
    private Integer procurementId;
    /**
     * 1 已完成 0未完成
     */
    private Integer status;
    /**
     * 已到达总商品金额
     */
    private Double arrivePrice;
    /**
     * 已到达商品总数种类
     */
    private Integer arrivaNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 1删除 0未删除
     */
    private Integer isDel;
    
    private Long createTime;

    private String logisticsNo;

    private String date;

    private String orderNo;


}

