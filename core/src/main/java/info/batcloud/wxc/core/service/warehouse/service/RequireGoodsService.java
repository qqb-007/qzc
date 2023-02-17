package info.batcloud.wxc.core.service.warehouse.service;

import com.github.pagehelper.PageInfo;
import info.batcloud.wxc.core.dto.RequireGoodsDto;

/**
 * @ClassName: RequireGoodsService
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
public interface RequireGoodsService {
    /**
     *
     * describe 新增要货单
     * @author V
     * @date 14/2/2023 上午11:23
     * @param
     * @return
     */
     Integer addRequireGoodsOrder(RequireGoodsDto requireGoodsDto) ;

     /**
      *
      * describe 查询要货单信息
      * @author V
      * @date 17/2/2023 下午4:52
      * @param
      * @return
      */
     PageInfo getRequireGoodsInfo(String requireNo,Integer storeId,Integer status,Integer pageNum,String startTime,String endTime);


     /**
      *
      * describe 获取要货单详情
      * @author V
      * @date 17/2/2023 下午5:05
      * @param
      * @return
      */
     PageInfo getRequireGoodsOrdersRelationList(Integer id,Integer pageNum);
}
