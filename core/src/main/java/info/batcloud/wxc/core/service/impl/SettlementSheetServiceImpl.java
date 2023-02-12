package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.dto.SettlementSheetDTO;
import info.batcloud.wxc.core.dto.SettlementSheetDetailDTO;
import info.batcloud.wxc.core.entity.SettlementSheet;
import info.batcloud.wxc.core.entity.SettlementSheetDetail;
import info.batcloud.wxc.core.enums.SettlementSheetStatus;
import info.batcloud.wxc.core.enums.WalletFlowDetailType;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.SettlementSheetRepository;
import info.batcloud.wxc.core.service.OrderService;
import info.batcloud.wxc.core.service.SettlementSheetService;
import info.batcloud.wxc.core.service.StoreUserService;
import info.batcloud.wxc.core.service.WalletService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettlementSheetServiceImpl implements SettlementSheetService {

    @Inject
    private SettlementSheetRepository settlementSheetRepository;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private WalletService walletService;

    @Inject
    private OrderService orderService;

    @Override
    public Paging<SettlementSheetDTO> search(SearchParam param) {
        Specification<SettlementSheet> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (query.getResultType() != Long.class) {
//                root.fetch("storeUser", JoinType.LEFT)
//                        .fetch("bizManager", JoinType.LEFT);
                if (param.isFetchDetails()) {
                    root.fetch("detailList", JoinType.LEFT);
                }
            }
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotBlank(param.getStoreUserName())) {
                expressions.add(cb.like(root.get("storeUser").get("name"), "%" + param.getStoreUserName() + "%"));
            }
            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (param.getSettled() != null) {
                expressions.add(cb.equal(root.get("settled"), param.getSettled()));
            }
            if (param.getStatus() != null) {
                expressions.add(cb.equal(root.get("status"), param.getStatus()));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<SettlementSheet> page = settlementSheetRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item, param.isFetchDetails()), param.getPage(), param.getPageSize());
    }

    @Override
    @Transactional
    public Result settleToWallet(long settlementSheetId) {
        SettlementSheet ss = settlementSheetRepository.findOne(settlementSheetId);
        Result rs = new Result();
        if (ss.getStatus() == SettlementSheetStatus.WAIT_SETTLE) {
            ss.setStatus(SettlementSheetStatus.SETTLED);
            if (ss.getSettlementAmount() > 0) {
                walletService.addMoney(ss.getStoreUser().getId(), ss.getSettlementAmount(), WalletFlowDetailType.MERGE_ORDER_SETTLE, settlementSheetId + "");
            }
            settlementSheetRepository.save(ss);
            rs.setSuccess(true);
            rs.setMsg("结算成功");
        } else {
            rs.setSuccess(false);
            rs.setMsg("当前结算单不是待结算状态，无法进行结算");
        }
        return rs;
    }

    @Override
    public SettlementSheetDTO findById(long id) {
        return toDTO(settlementSheetRepository.findOne(id), true);
    }

    private SettlementSheetDTO toDTO(SettlementSheet suf, boolean fetchDetail) {
        SettlementSheetDTO dto = new SettlementSheetDTO();
        BeanUtils.copyProperties(suf, dto);
        dto.setStoreUser(storeUserService.toDTO(suf.getStoreUser()));
        if (fetchDetail) {
            dto.setDetailList(suf.getDetailList().stream().map(o -> toDTO(o, fetchDetail)).collect(Collectors.toList()));
        } else {
            dto.setDetailList(new ArrayList<>());
        }
        return dto;
    }

    private SettlementSheetDetailDTO toDTO(SettlementSheetDetail detail, Boolean fetchOrderDetail) {
        SettlementSheetDetailDTO dto = new SettlementSheetDetailDTO();
        BeanUtils.copyProperties(detail, dto);
        if (fetchOrderDetail) {
//            dto.setOrderList(detail.getOrderList().stream().map(o -> orderService.toDTO(o, fetchOrderDetail)).collect(Collectors.toList()));
        }
        return dto;
    }
}
