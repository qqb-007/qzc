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
        //?????????????????????????????????
        /*List<String> categoryList = foodRepository.findFoodCategoryList();
        //???????????????????????????????????????
        List<FoodCategory> foodCategories = foodCategoryRepository.findAll();
        //????????????????????????????????????
        List<String> existsFoodNameList = foodCategories.stream().map(o -> o.getName()).collect(Collectors.toList());
        for (int i = 0; i < categoryList.size(); i++) {
            if (!existsFoodNameList.remove(categoryList.get(i))) {
                //?????????????????????????????????????????????????????????????????????????????????
                FoodCategory foodCategory = new FoodCategory();
                foodCategory.setIdx(i);
                foodCategory.setLevel(0);
                foodCategory.setName(categoryList.get(i));
                foodCategoryRepository.save(foodCategory);
                //???????????????????????????????????????????????????
                storeService.publishCategoryToAll(foodCategory);
            }
        }*/
        //?????????????????????????????????(????????????????????????????????????)
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
        //??????????????????????????????????????????????????????????????????
        FoodCategory fc = foodCategoryRepository.findOne(id);
        String originName = fc.getName();
        fc.setName(name);
        if (idx != null) {
            fc.setIdx(idx);
        }
        //?????????????????????????????????????????????
        storeService.updateCategoryToAll(fc, originName);
        foodCategoryRepository.save(fc);
        /**
         * ??????food???name
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
                //????????????id????????????????????????
                category.setLevel(0);
            } else {
                //???????????????????????????????????????????????????????????????????????????????????????
                List<Food> byCategoryName = foodRepository.findByCategoryName(parentCategoryName);
                if (byCategoryName == null || byCategoryName.size() == 0) {
                    FoodCategory parentCategory = foodCategoryRepository.findByName(parentCategoryName);
                    category.setLevel(1);
                    category.setParentId(parentCategory.getId());
                } else {
                    throw new BizException("?????????????????????????????????????????????");
                }
            }
            category.setName(categoryName);
            category.setIdx(0);
            foodCategoryRepository.save(category);
            //?????????????????????????????????
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
        //?????????????????????????????????????????????????????????????????????
        if (foodCategory.getLevel() == 0) {
            List<FoodCategory> child = foodCategoryRepository.findByParentId(foodCategory.getId());
            if ((!categoryList.contains(foodCategory.getName())) && (child.size() == 0)) {
                //?????????????????????????????????
                storeService.deleteCategoryToAll(foodCategory);
                foodCategoryRepository.delete(id);
            } else {
                throw new BizException("?????????????????????????????????????????????");
            }
        } else if (foodCategory.getLevel() == 1) {
            if (!categoryList.contains(foodCategory.getName())) {
                //??????????????????????????????
                storeService.deleteCategoryToAll(foodCategory);
                foodCategoryRepository.delete(id);
            }
        }
    }

}
