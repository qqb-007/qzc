package info.batcloud.wxc.core.service.warehouse.dao;
import info.batcloud.wxc.core.entity.PreReceiptOrders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (PreReceiptOrders)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-17 14:28:54
 */
@Mapper
public interface PreReceiptOrdersDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreReceiptOrders queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param preReceiptOrders 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<PreReceiptOrders> queryAllByLimit(PreReceiptOrders preReceiptOrders, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param preReceiptOrders 查询条件
     * @return 总行数
     */
    long count(PreReceiptOrders preReceiptOrders);

    /**
     * 新增数据
     *
     * @param preReceiptOrders 实例对象
     * @return 影响行数
     */
    int insert(PreReceiptOrders preReceiptOrders);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreReceiptOrders> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreReceiptOrders> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreReceiptOrders> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreReceiptOrders> entities);

    /**
     * 修改数据
     *
     * @param preReceiptOrders 实例对象
     * @return 影响行数
     */
    int update(PreReceiptOrders preReceiptOrders);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    List<PreReceiptOrders> getReceiptOrder(@Param("storeId") Integer storeId,@Param("startTime")Long startTime,
                                           @Param("endTime") Long endTime,@Param("logisticsNo") String logisticsNo,
                                           @Param("receiptNo") String receiptNo);

}

