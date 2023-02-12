package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.FoodCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FoodCategoryRepository extends CrudRepository<FoodCategory, Long> {

    List<FoodCategory> findByOrderByIdxAsc();
    List<FoodCategory> findByParentIdIsNullOrderByIdxAsc();
    List<FoodCategory> findByOrderByIdAsc();
    List<FoodCategory> findByNameIn(List<String> nameList);
    List<FoodCategory> findAll();

    FoodCategory findByName(String name);
    //查询出属于某个一级分类的二级分类的集合
    List<FoodCategory> findByParentId(Long parentId);

}
