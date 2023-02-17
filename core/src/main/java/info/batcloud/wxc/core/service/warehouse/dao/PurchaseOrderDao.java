package info.batcloud.wxc.core.service.warehouse.dao;

import info.batcloud.wxc.core.dto.UpdatePurchaseDto;
import info.batcloud.wxc.core.entity.PreShopProcurement;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @ClassName: PurchaseOrderDao
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@Mapper
public interface PurchaseOrderDao  {
    //添加采购单
    Integer sacaPurchase(PreShopProcurement preShopProcurement);
    //获取采购列表
    List<PreShopProcurement> selectAllByOrderNo(@Param("orderNo")String orderNo,@Param("storeId") Integer storeId);

    //删除采购单
    @Delete("delete from pre_shop_procurement where id=#{id}")
    Integer delPurchaseById(Integer id);

    //删除采购单关联的商品信息
    @Delete("delete from pre_shop_procurement_relation where shop_procurement_id=#{id}")
    Integer delPurchaseByIdToRelation(Integer id);

    //编辑采购单
    Integer updatePurchase(UpdatePurchaseDto updatePurchaseDto);


}
