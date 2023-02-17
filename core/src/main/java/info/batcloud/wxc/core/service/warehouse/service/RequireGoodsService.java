package info.batcloud.wxc.core.service.warehouse.service;

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
}
