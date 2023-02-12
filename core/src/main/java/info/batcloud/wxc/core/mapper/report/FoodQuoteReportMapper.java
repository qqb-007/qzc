package info.batcloud.wxc.core.mapper.report;

import info.batcloud.wxc.core.mapper.report.domain.FoodQuoteReportData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodQuoteReportMapper {

    List<FoodQuoteReportData> reportByFoodIdList(@Param("foodIdList") List<Long> foodIdList);

}
