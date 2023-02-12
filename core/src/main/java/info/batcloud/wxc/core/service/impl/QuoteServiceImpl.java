package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.FoodDTO;
import info.batcloud.wxc.core.dto.FoodQuoteDTO;
import info.batcloud.wxc.core.entity.StoreUserFood;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.mapper.FoodQuoteMapper;
import info.batcloud.wxc.core.repository.StoreUserFoodRepository;
import info.batcloud.wxc.core.service.FoodQuoteSheetService;
import info.batcloud.wxc.core.service.FoodService;
import info.batcloud.wxc.core.service.QuoteService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl implements QuoteService {

    @Inject
    private FoodService foodService;

    @Inject
    private FoodQuoteSheetService foodQuoteSheetService;

    @Inject
    private FoodQuoteMapper foodQuoteMapper;

    @Inject
    private StoreUserFoodRepository storeUserFoodRepository;

    @Override
    public Paging<FoodQuoteDTO> searchFoodForQuote(FoodForQuoteSearchParam param) {
        FoodService.SearchParam fParam = new FoodService.SearchParam();
        fParam.setName(param.getFoodName());
        fParam.setCategoryName(param.getCategoryName());
        fParam.setQuotable(true);
        fParam.setPageSize(param.getPageSize());
        fParam.setPage(param.getPage());
        fParam.setFoodIdList(param.getFoodIdList());
        Paging<FoodDTO> paging = foodService.search(fParam);
        Map<Long, StoreUserFood> storeUserFoodMap = new HashMap<>();
        if (param.getStoreUserId() != null) {
            List<StoreUserFood> storeUserFoodList = storeUserFoodRepository.findByStoreUserIdAndFoodIdIn(param.getStoreUserId(), paging.getResults().stream().map(o -> o.getId())
                    .collect(Collectors.toList()));
            for (StoreUserFood suf : storeUserFoodList) {
                storeUserFoodMap.put(suf.getFood().getId(), suf);
            }
        }
        return PagingHelper.of(paging.getResults().stream().map(o -> {
                    FoodQuoteDTO dto = toDTO(o);
                    if (storeUserFoodMap.containsKey(dto.getFoodId())) {
                        StoreUserFood storeUserFood = storeUserFoodMap.get(dto.getFoodId());
                        dto.setFoodPrice(storeUserFood.getQuotePrice());
                        dto.setSale(storeUserFood.getSale());
                    } else {
                        dto.setFoodPrice(o.getPrice());
                    }
                    return dto;
                }).collect(Collectors.toList()),
                paging.getTotal(), paging.getPage(), paging.getPageSize());
    }

    @Override
    public void storeUserQuoteFood(FoodQuoteParam param) {
        FoodQuoteSheetService.FoodQuoteSheetAddParam addParam = new FoodQuoteSheetService.FoodQuoteSheetAddParam();
        addParam.setStoreUserId(param.getStoreUserId());
        addParam.setFoodQuoteList(param.getFoodQuoteInfoList());
        foodQuoteSheetService.addFoodQuoteSheet(addParam);
    }

    private FoodQuoteDTO toDTO(FoodDTO food) {
        FoodQuoteDTO dto = new FoodQuoteDTO();
        dto.setFoodId(food.getId());
        dto.setFoodName(food.getName());
        dto.setFoodPicture(food.getPicture());
        dto.setFoodPrice(0f);
        dto.setFoodUnit(food.getQuoteUnit());
        return dto;
    }
}
