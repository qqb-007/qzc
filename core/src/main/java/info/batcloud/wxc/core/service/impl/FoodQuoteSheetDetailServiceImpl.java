package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import info.batcloud.wxc.core.domain.FoodQuoteSku;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.dto.FoodQuoteSheetDetailDTO;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.entity.FoodQuoteSheetDetail;
import info.batcloud.wxc.core.repository.FoodQuoteSheetDetailRepository;
import info.batcloud.wxc.core.service.FoodQuoteSheetDetailService;
import info.batcloud.wxc.core.service.FoodService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FoodQuoteSheetDetailServiceImpl implements FoodQuoteSheetDetailService {

    @Inject
    private FoodQuoteSheetDetailRepository foodQuoteSheetDetailRepository;

    @Inject
    private FoodService foodService;

    @Override
    public List<FoodQuoteSheetDetailDTO> findByFoodQuoteSheetId(long foodQuoteSheetId) {
        Specification<FoodQuoteSheetDetail> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            root.fetch("food", JoinType.LEFT);
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.equal(root.get("foodQuoteSheetId"), foodQuoteSheetId));
            expressions.add(cb.equal(root.get("food").get("deleted"), false));
            return predicate;
        };
        List<FoodQuoteSheetDetail> details = foodQuoteSheetDetailRepository.findAll(specification);
        return details.stream().map(o -> toDTO(o)).collect(Collectors.toList());
    }

    private FoodQuoteSheetDetailDTO toDTO(FoodQuoteSheetDetail detail) {
        if (detail == null) {
            return null;
        }
        FoodQuoteSheetDetailDTO dto = new FoodQuoteSheetDetailDTO();
        BeanUtils.copyProperties(detail, dto);
        Food food = detail.getFood();
        dto.setFoodPicture(food.getPicture());
        dto.setFood(foodService.toDTO(food));
        Map<String, FoodSku> foodSkuMap = new HashMap<>();
//        for (FoodSku sku : dto.getFood().getSkuList()) {
//            foodSkuMap.put(sku.getSkuId(), sku);
//        }
        dto.setFoodQuoteSkuList(JSON.parseObject(detail.getFoodSkuJson(), new TypeReference<List<FoodQuoteSku>>() {
        }));
        return dto;
    }
}
