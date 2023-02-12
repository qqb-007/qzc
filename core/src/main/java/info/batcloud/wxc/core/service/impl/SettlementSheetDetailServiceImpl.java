package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.SettlementSheetDetailDTO;
import info.batcloud.wxc.core.entity.SettlementSheetDetail;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.SettlementSheetDetailRepository;
import info.batcloud.wxc.core.service.SettlementSheetDetailService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SettlementSheetDetailServiceImpl implements SettlementSheetDetailService {

    @Inject
    private SettlementSheetDetailRepository settlementSheetDetailRepository;

    @Override
    public Paging<SettlementSheetDetailDTO> search(SearchParam param) {
        Specification<SettlementSheetDetail> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (query.getResultType() != Long.class) {
                root.fetch("storeUser", JoinType.LEFT);
            }
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotBlank(param.getStoreUserName())) {
                expressions.add(cb.like(root.get("storeUser").get("name"), "%" + param.getStoreUserName() + "%"));
            }
            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (param.getStatus() != null) {
                expressions.add(cb.equal(root.get("status"), param.getStatus()));
            }
            if (param.getYear() != null) {
                expressions.add(cb.equal(root.get("year"), param.getYear()));
            }
            if (param.getWeek() != null) {
                expressions.add(cb.equal(root.get("week"), param.getWeek()));
            }
            if (param.getDate() != null) {
                Date startTime = DateUtils.truncate(param.getDate(), Calendar.DATE);
                Date endTime = DateUtils.addDays(startTime, 1);
                expressions.add(cb.between(root.get("date"), startTime, endTime));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<SettlementSheetDetail> page = settlementSheetDetailRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item), param.getPage(), param.getPageSize());
    }

    private SettlementSheetDetailDTO toDTO(SettlementSheetDetail ssd) {
        SettlementSheetDetailDTO dto = new SettlementSheetDetailDTO();
        BeanUtils.copyProperties(ssd, dto);
        dto.setStoreUserId(ssd.getStoreUser().getId());
        dto.setStoreUserName(ssd.getStoreUser().getName());
        return dto;
    }
}
