package info.batcloud.wxc.core.service;

import info.batcloud.wxc.core.entity.FoodCategory;
import info.batcloud.wxc.core.mapper.domain.FirstFoodCategory;
import info.batcloud.wxc.core.mapper.domain.FoodCategoryWithFoodNum;

import java.util.List;

public interface FoodCategoryService {

    void setTag(Long id, String mtname, Long mttagId, String jdname, Long jdtagid);

    void initFromFood();

    List<FoodCategory> findIdSortedList();

    List<FoodCategoryWithFoodNum> findList();

    List<FoodCategory> findSortedList();

    void modifyName(long id, String name, Integer idx);

    void saveFoodCategory(String categoryName, String parentCategoryName);

    void sort(List<String> categoryNameList);

    boolean isParentCategory(String name);

    FoodCategory getParentCategoryById(long id);

    FoodCategory getParentCategoryByName(String name);

    List<FirstFoodCategory> findFirstCategoryList();

    List<FoodCategory> findByParentName(String name);

    void deleteCategory(long id);
}
