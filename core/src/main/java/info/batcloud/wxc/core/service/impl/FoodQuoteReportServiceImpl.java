package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.FoodDTO;
import info.batcloud.wxc.core.dto.FoodQuoteReportDTO;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.entity.FoodQuoteReport;
import info.batcloud.wxc.core.enums.FoodSort;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.mapper.report.FoodQuoteReportMapper;
import info.batcloud.wxc.core.mapper.report.domain.FoodQuoteReportData;
import info.batcloud.wxc.core.repository.FoodQuoteReportRepository;
import info.batcloud.wxc.core.repository.FoodRepository;
import info.batcloud.wxc.core.service.FoodQuoteReportService;
import info.batcloud.wxc.core.service.FoodService;
import info.batcloud.wxc.core.service.RegionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FoodQuoteReportServiceImpl implements FoodQuoteReportService {

    @Inject
    private FoodQuoteReportMapper foodQuoteReportMapper;

    @Inject
    private FoodQuoteReportRepository foodQuoteReportRepository;

    @Inject
    private FoodService foodService;

    @Inject
    private TransactionTemplate transactionTemplate;

    @Inject
    private RegionService regionService;

    @Inject
    private FoodRepository foodRepository;

    @Override
    public void generateByAllFood() {
        FoodService.SearchParam param = new FoodService.SearchParam();
        param.setSort(FoodSort.ID);
        param.setPageSize(100);
        param.setPage(1);
        while (true) {
            Paging<FoodDTO> pg = transactionTemplate.execute((status) -> {
                Paging<FoodDTO> paging = foodService.search(param);
                List<Long> foodIdList = paging.getResults().stream().map(o -> o.getId()).collect(Collectors.toList());
                if (foodIdList.size() == 0) {
                } else {
                    generateByFoodIdList(foodIdList);
                }
                return paging;
            });
            if (!pg.getHasNext()) {
                break;
            } else {
                param.setPage(param.getPage() + 1);
            }
        }
    }

    @Override
    public void generateByFoodId(long foodId) {
        generateByFoodIdList(Arrays.asList(foodId));
    }

    @Override
    public void setRefPrice(long id, float refPrice) {
        FoodQuoteReport rqr = foodQuoteReportRepository.findOne(id);
        rqr.setRefPrice(refPrice);
        foodQuoteReportRepository.save(rqr);
    }

    @Override
    public void setWarningPrice(long id, float warningPrice) {
        FoodQuoteReport rqr = foodQuoteReportRepository.findOne(id);
        rqr.setWarningPrice(warningPrice);
        foodQuoteReportRepository.save(rqr);
    }

    private void generateByFoodIdList(List<Long> foodIdList) {
        List<FoodQuoteReportData> reportDataList = foodQuoteReportMapper.reportByFoodIdList(foodIdList);
        List<FoodQuoteReport> list = new ArrayList<>();
        List<FoodQuoteReport> eList = foodQuoteReportRepository.findByFoodIdIn(foodIdList);
        Map<String, FoodQuoteReport> map = new HashMap<>(2000);
        for (FoodQuoteReport report : eList) {
            if (report.getCity() == null) {
                continue;
            }
            map.put(report.getFoodId() + "@" + report.getCity().getId(), report);
        }
        for (FoodQuoteReportData foodQuoteReportData : reportDataList) {
            if (foodQuoteReportData.getCityId() == null) {
                continue;
            }
            FoodQuoteReport report = map.get(foodQuoteReportData.getFoodId()+ "@" + foodQuoteReportData.getCityId());
            if (report == null) {
                report = new FoodQuoteReport();
                Food food = foodRepository.findOne(foodQuoteReportData.getFoodId());
                report.setRefPrice(food.getPrice());
                report.setWarningPrice(food.getWarningPrice());
            } else {
                if (report.getRefPrice() == null) {
                    Food food = foodRepository.findOne(foodQuoteReportData.getFoodId());
                    report.setRefPrice(food.getPrice());
                    report.setWarningPrice(food.getWarningPrice());
                }
            }
            BeanUtils.copyProperties(foodQuoteReportData, report);
            report.setUpdateTime(new Date());
            report.setProvince(regionService.findById(foodQuoteReportData.getProvinceId()));
            report.setCity(regionService.findById(foodQuoteReportData.getCityId()));
            list.add(report);
        }
        foodQuoteReportRepository.save(list);
    }

    @Override
    public Paging<FoodQuoteReportDTO> search(SearchParam param) {
        Specification<FoodQuoteReport> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotEmpty(param.getFoodName())) {
                expressions.add(cb.like(root.get("foodName"), "%" + param.getFoodName() + "%"));
            }
            if (param.getFoodId() != null) {
                expressions.add(cb.equal(root.get("foodId"), param.getFoodId()));
            }
            if (param.getProvinceId() != null) {
                expressions.add(cb.equal(root.get("province").get("id"), param.getProvinceId()));
            }
            if (param.getCityId() != null) {
                expressions.add(cb.equal(root.get("city").get("id"), param.getCityId()));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "foodId");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<FoodQuoteReport> page = foodQuoteReportRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item), param.getPage(), param.getPageSize());
    }

    private FoodQuoteReportDTO toDTO(FoodQuoteReport foodQuoteReport) {
        FoodQuoteReportDTO dto = new FoodQuoteReportDTO();
        BeanUtils.copyProperties(foodQuoteReport, dto);
        dto.setCity(regionService.toDTO(foodQuoteReport.getCity()));
        dto.setProvince(regionService.toDTO(foodQuoteReport.getProvince()));
        return dto;
    }
}
