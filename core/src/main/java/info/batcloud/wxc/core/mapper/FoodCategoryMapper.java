package info.batcloud.wxc.core.mapper;

import info.batcloud.wxc.core.mapper.domain.FirstFoodCategory;
import info.batcloud.wxc.core.mapper.domain.FoodCategoryWithFoodNum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoodCategoryMapper {

    List<FoodCategoryWithFoodNum> findCategoryWithFoodNum();
    List<FirstFoodCategory> findAllFirstCategory();
    FirstFoodCategory findFirstCategoryById(Long id);

}
