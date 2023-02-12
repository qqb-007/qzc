package info.batcloud.wxc.core.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreUserFoodMapper {

    List<Long> detectChange(@Param("storeUserIdList") List<Long> storeUserIdList, @Param("priceIncrease") float priceIncrease, @Param("page") int page, @Param("pageSize") int pageSize);

}
