package info.batcloud.wxc.core.service.warehouse.dao;

import info.batcloud.wxc.core.entity.PreShopProcurement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (PreShopProcurement)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-17 10:28:07
 */
@Mapper
public interface PreShopProcurementDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreShopProcurement queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param preShopProcurement 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<PreShopProcurement> queryAllByLimit(PreShopProcurement preShopProcurement, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param preShopProcurement 查询条件
     * @return 总行数
     */
    long count(PreShopProcurement preShopProcurement);

    /**
     * 新增数据
     *
     * @param preShopProcurement 实例对象
     * @return 影响行数
     */
    int insert(PreShopProcurement preShopProcurement);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreShopProcurement> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreShopProcurement> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreShopProcurement> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreShopProcurement> entities);

    /**
     * 修改数据
     *
     * @param preShopProcurement 实例对象
     * @return 影响行数
     */
    int update(PreShopProcurement preShopProcurement);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

