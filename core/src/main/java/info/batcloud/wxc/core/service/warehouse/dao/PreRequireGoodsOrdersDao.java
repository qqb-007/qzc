package info.batcloud.wxc.core.service.warehouse.dao;
import info.batcloud.wxc.core.entity.PreRequireGoodsOrders;
import info.batcloud.wxc.core.entity.PreRequireGoodsOrdersRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (PreRequireGoodsOrders)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-17 16:50:29
 */
@Mapper
public interface PreRequireGoodsOrdersDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreRequireGoodsOrders queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param preRequireGoodsOrders 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<PreRequireGoodsOrders> queryAll(PreRequireGoodsOrders preRequireGoodsOrders);

    /**
     * 统计总行数
     *
     * @param preRequireGoodsOrders 查询条件
     * @return 总行数
     */
    long count(PreRequireGoodsOrders preRequireGoodsOrders);

    /**
     * 新增数据
     *
     * @param preRequireGoodsOrders 实例对象
     * @return 影响行数
     */
    int insert(PreRequireGoodsOrders preRequireGoodsOrders);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreRequireGoodsOrders> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreRequireGoodsOrders> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreRequireGoodsOrders> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreRequireGoodsOrders> entities);

    /**
     * 修改数据
     *
     * @param preRequireGoodsOrders 实例对象
     * @return 影响行数
     */
    int update(PreRequireGoodsOrders preRequireGoodsOrders);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    @Select("select *, from_unixtime(create_time) as `date` from pre_require_goods_orders_relation where require_goods_id=#{requireGoodsId}")
    List<PreRequireGoodsOrdersRelation> getRequireGoodsOrdersRelationList(Integer requireGoodsId);
}

