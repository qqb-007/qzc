package info.batcloud.wxc.core.service.impl;

import info.batcloud.wxc.core.constants.CacheNameConstants;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.entity.FoodCategory;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.mapper.FoodCategoryMapper;
import info.batcloud.wxc.core.mapper.domain.FirstFoodCategory;
import info.batcloud.wxc.core.mapper.domain.FoodCategoryWithFoodNum;
import info.batcloud.wxc.core.repository.FoodCategoryRepository;
import info.batcloud.wxc.core.repository.FoodRepository;
import info.batcloud.wxc.core.service.FoodCategoryService;
import info.batcloud.wxc.core.service.StoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = CacheNameConstants.FOOD_CATEGORY)
public class FoodCategoryServiceImpl implements FoodCategoryService {

    @Inject
    private FoodCategoryRepository foodCategoryRepository;

    @Inject
    private FoodRepository foodRepository;

    @Inject
    private FoodCategoryMapper foodCategoryMapper;
    @Inject
    private StoreService storeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CacheNameConstants.FOOD_CATEGORY, allEntries = true)
    public void setTag(Long id, String mtname, Long mttagId, String jdName, Long jdTagid) {
        FoodCategory category = foodCategoryRepository.findOne(id);
        if (mttagId != null) {
            category.setMeituanTagId(mttagId);
            category.setMeituanTagName(mtname);
        }
        if (jdTagid != null) {
            category.setJingdongTagId(jdTagid);
            category.setJingdongTagName(jdName);
        }

        foodCategoryRepository.save(category);
    }

    @Override
    public void initFromFood() {
        //得到所有食物的分类名称
        /*List<String> categoryList = foodRepository.findFoodCategoryList();
        //得到所有的已存在的分类集合
        List<FoodCategory> foodCategories = foodCategoryRepository.findAll();
        //得到已存在分类集合的名称
        List<String> existsFoodNameList = foodCategories.stream().map(o -> o.getName()).collect(Collectors.toList());
        for (int i = 0; i < categoryList.size(); i++) {
            if (!existsFoodNameList.remove(categoryList.get(i))) {
                //如果已存在的分类中没有包含该分类，那么新建一个一级分类
                FoodCategory foodCategory = new FoodCategory();
                foodCategory.setIdx(i);
                foodCategory.setLevel(0);
                foodCategory.setName(categoryList.get(i));
                foodCategoryRepository.save(foodCategory);
                //将创建出的一级分类同步发布到平台上
                storeService.publishCategoryToAll(foodCategory);
            }
        }*/
        //删除没有食物的二级分类(添加到主动删除分类方法中)
       /* for (FoodCategory foodCategory : foodCategories) {
            if (foodCategory.getLevel() == 1 && existsFoodNameList.contains(foodCategory.getName())) {
                foodCategoryRepository.delete(foodCategory);
            }
        }*/
    }

    @Override
    public List<FoodCategory> findIdSortedList() {
        return foodCategoryRepository.findByOrderByIdAsc();
    }

    @Override
    @Cacheable(key = "'FoodCategoryWithFoodNum'")
    public List<FoodCategoryWithFoodNum> findList() {
        List<FoodCategoryWithFoodNum> categoryList = foodCategoryMapper.findCategoryWithFoodNum();
        return categoryList;
    }

    @Override
    @Cacheable(key = "'findSortedList'")
    public List<FoodCategory> findSortedList() {
        return foodCategoryRepository.findByParentIdIsNullOrderByIdxAsc();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CacheNameConstants.FOOD_CATEGORY, allEntries = true)
    public void modifyName(long id, String name, Integer idx) {
        //更改分类名称并更改对应的食物中分类属性的名称
        FoodCategory fc = foodCategoryRepository.findOne(id);
        String originName = fc.getName();
        fc.setName(name);
        if (idx != null) {
            fc.setIdx(idx);
        }
        //更新美团和饿了么对应的分类名称
        storeService.updateCategoryToAll(fc, originName);
        foodCategoryRepository.save(fc);
        /**
         * 修改food的name
         * */
        List<Food> foodList = foodRepository.findByCategoryName(originName);
        for (Food food : foodList) {
            if (name.equals(food.getCategoryName())) {
                continue;
            }
            food.setCategoryName(name);
        }
        foodRepository.save(foodList);
    }


    @Override
    @CacheEvict(cacheNames = CacheNameConstants.FOOD_CATEGORY, allEntries = true)
    public void saveFoodCategory(String categoryName, String parentCategoryName) {
        FoodCategory category = foodCategoryRepository.findByName(categoryName);
        if (category == null) {
            category = new FoodCategory();
            if (parentCategoryName == null) {
                //没有父类id代表创建一级类别
                category.setLevel(0);
            } else {
                //判断该父类下有无商品，如果有商品则不允许在该类目下创建子类
                List<Food> byCategoryName = foodRepository.findByCategoryName(parentCategoryName);
                if (byCategoryName == null || byCategoryName.size() == 0) {
                    FoodCategory parentCategory = foodCategoryRepository.findByName(parentCategoryName);
                    category.setLevel(1);
                    category.setParentId(parentCategory.getId());
                } else {
                    throw new BizException("该分类含有商品，不允许创建子类");
                }
            }
            category.setName(categoryName);
            category.setIdx(0);
            foodCategoryRepository.save(category);
            //将新建分类发布到平台上
            storeService.publishCategoryToAll(category);
        }
    }

    @Override
    public void sort(List<String> categoryNameList) {
        List<FoodCategory> foodCategories = foodCategoryRepository.findByNameIn(categoryNameList);
        for (FoodCategory category : foodCategories) {
            category.setIdx(categoryNameList.indexOf(category.getName()));
        }
        foodCategoryRepository.save(foodCategories);
    }

    @Override
    public boolean isParentCategory(String name) {
        FoodCategory category = foodCategoryRepository.findByName(name);
        if (category.getLevel() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FoodCategory getParentCategoryById(long id) {
        FoodCategory foodCategory = foodCategoryRepository.findOne(id);
        FoodCategory parentCategory = foodCategoryRepository.findOne(foodCategory.getParentId());
        return parentCategory;
    }

    @Override
    public FoodCategory getParentCategoryByName(String name) {
        FoodCategory foodCategory = foodCategoryRepository.findByName(name);
        FoodCategory parentCategory = foodCategoryRepository.findOne(foodCategory.getParentId());
        return parentCategory;
    }

    @Override
    @Cacheable(key = "'findFirstCategoryList'")
    public List<FirstFoodCategory> findFirstCategoryList() {
        List<FirstFoodCategory> allFirstCategory = foodCategoryMapper.findAllFirstCategory();
        return allFirstCategory;
    }

    @Override
    public List<FoodCategory> findByParentName(String name) {
        FoodCategory parent = foodCategoryRepository.findByName(name);
        List<FoodCategory> byParentId = new ArrayList<>();
        if (parent != null) {
            byParentId = foodCategoryRepository.findByParentId(parent.getId());
        }

        return byParentId;
    }

    @Override
    @CacheEvict(cacheNames = CacheNameConstants.FOOD_CATEGORY, allEntries = true)
    public void deleteCategory(long id) {
        FoodCategory foodCategory = foodCategoryRepository.findOne(id);
        List<String> categoryList = foodRepository.findFoodCategoryList();
        //一级分类在没有子类并且没有商品的前提下才能删除
        if (foodCategory.getLevel() == 0) {
            List<FoodCategory> child = foodCategoryRepository.findByParentId(foodCategory.getId());
            if ((!categoryList.contains(foodCategory.getName())) && (child.size() == 0)) {
                //删除所有店铺下的此分类
                storeService.deleteCategoryToAll(foodCategory);
                foodCategoryRepository.delete(id);
            } else {
                throw new BizException("该分类含有子类，不允许直接删除");
            }
        } else if (foodCategory.getLevel() == 1) {
            if (!categoryList.contains(foodCategory.getName())) {
                //删除所有店铺下的分类
                storeService.deleteCategoryToAll(foodCategory);
                foodCategoryRepository.delete(id);
            }
        }
    }

}
