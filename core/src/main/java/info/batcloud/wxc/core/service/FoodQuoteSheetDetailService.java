package info.batcloud.wxc.core.service;

import info.batcloud.wxc.core.dto.FoodQuoteSheetDetailDTO;

import java.util.List;

public interface FoodQuoteSheetDetailService {

    List<FoodQuoteSheetDetailDTO> findByFoodQuoteSheetId(long foodQuoteSheetId);

}
