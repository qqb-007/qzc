package info.batcloud.wxc.core.mapper;

import info.batcloud.wxc.core.mapper.domain.OrderDetailStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    List<OrderDetailStat> statByFoodSupplierIdAndOrderIdList(@Param("foodSupplierId") long foodSupplierId,
                                                                             @Param("orderIdList") List<Long> orderIdList);

}
