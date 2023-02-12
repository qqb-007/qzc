package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.Food;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface FoodRepository extends PagingAndSortingRepository<Food, Long>, JpaSpecificationExecutor<Food> {

    List<Food> findByNameContainingAndDeletedFalse(String name);

    Food findByCodeAndDeletedFalse(String code);
    Food findByCode(String code);
    Food findByNameAndDeletedFalse(String name);

    int countByActualFoodId(long actualFoodId);

    List<Food> findByMeituanTagIdNullAndMeituanTagNameIsNotNullOrderByIdDesc();

    List<Food> findByIdInAndDeletedFalse(List<Long> idList);

    List<Food> findByCategoryName(String categoryName);
    List<Food> findByUpdateTime(Date date);
    List<Food> findByActualFoodId(long actualFoodId);
    List<Food> findByCategoryNameOrderByIdxAsc(String categoryName);

    List<Food> findByCodeInOrNameInAndDeletedFalse(List<String> codeList, List<String> nameList);

    Food findByCodeOrNameAndDeletedFalse(String code, String name);

    int countByCodeAndDeletedFalse(String code);

    int countByCodeOrNameAndDeletedFalse(String code, String name);

    int countByNameAndDeletedFalse(String name);

    int countByCodeAndIdNotAndDeletedFalse(String code, long id);

    int countByNameAndIdNotAndDeletedFalse(String name, long id);

    List<Food> findByCodeInAndDeletedFalse(List<String> codeList);

    @Query(nativeQuery = true, value = "select category_name from food f " +
            "where f.deleted=0 group by category_name")
    List<String> findFoodCategoryList();
}
