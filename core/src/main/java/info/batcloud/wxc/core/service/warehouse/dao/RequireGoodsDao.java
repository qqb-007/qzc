package info.batcloud.wxc.core.service.warehouse.dao;

import info.batcloud.wxc.core.entity.PreRequireGoodsOrders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @ClassName: RequireGoodsRepository1
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@Mapper
public interface RequireGoodsDao {
    @Insert("insert into pre_require_goods_orders(require_goods_no,store_id,store_name,status,require_goods_num,require_goods_price,remark,create_time,sku_id)" +
            "values(#{requireGoodsNo},#{storeId},#{storeName},#{status},#{requireGoodsNum},#{requireGoodsPrice},#{remark},#{createTime},#{skuId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(PreRequireGoodsOrders preRequireGoodsOrders);
}
