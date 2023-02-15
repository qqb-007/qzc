package info.batcloud.wxc.core.service.warehouse.dao;
import info.batcloud.wxc.core.entity.PreRequireGoodsOrdersRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

/**
 * @ClassName: RequireGoodsRelationRepository
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@Mapper
public interface RequireGoodsRelationDao {
    Integer savaRequireGoodsRelationInfo(@Param("list") List<PreRequireGoodsOrdersRelation> list);
}
