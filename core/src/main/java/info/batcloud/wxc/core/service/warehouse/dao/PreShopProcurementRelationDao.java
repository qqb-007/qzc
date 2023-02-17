package info.batcloud.wxc.core.service.warehouse.dao;


import info.batcloud.wxc.core.entity.PreShopProcurementRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (PreShopProcurementRelation)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-17 11:19:33
 */
@Mapper
public interface PreShopProcurementRelationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreShopProcurementRelation queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param preShopProcurementRelation 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<PreShopProcurementRelation> queryAllByLimit(PreShopProcurementRelation preShopProcurementRelation, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param preShopProcurementRelation 查询条件
     * @return 总行数
     */
    long count(PreShopProcurementRelation preShopProcurementRelation);

    /**
     * 新增数据
     *
     * @param preShopProcurementRelation 实例对象
     * @return 影响行数
     */
    int insert(PreShopProcurementRelation preShopProcurementRelation);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreShopProcurementRelation> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreShopProcurementRelation> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreShopProcurementRelation> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreShopProcurementRelation> entities);

    /**
     * 修改数据
     *
     * @param preShopProcurementRelation 实例对象
     * @return 影响行数
     */
    int update(PreShopProcurementRelation preShopProcurementRelation);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<PreShopProcurementRelation> getPurchaseRelationByPurchaseId(Integer id);

    List<PreShopProcurementRelation> getPurchaseRelationByPurchaseIdToReceipt(Integer id);

}

