package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.OrderNotificationDTO;
import info.batcloud.wxc.core.entity.OrderNotification;
import info.batcloud.wxc.core.enums.OrderNotificationSort;
import info.batcloud.wxc.core.enums.OrderNotificationType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.helper.CautionHelper;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.OrderNotificationRepository;
import info.batcloud.wxc.core.service.OrderNotificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;

@Service
public class OrderNotificationServiceImpl implements OrderNotificationService {

    @Inject
    private OrderNotificationRepository orderNotificationRepository;

    @Override
    public void saveOrderNotification(Plat plat, OrderNotificationType type, String platOrderId, boolean success, String errMsg, Object data) {
        OrderNotification on = orderNotificationRepository.findByPlatAndPlatOrderId(plat, platOrderId);
        if (on == null) {
            on = new OrderNotification();
        }
        on.setPlat(plat);
        on.setPlatOrderId(platOrderId);
        on.setType(type);
        on.setSuccess(success);
        on.setErrMsg(errMsg);
        on.setCreateTime(new Date());
        on.setData(CautionHelper.getCaution(JSON.toJSONString(data)));
        orderNotificationRepository.save(on);
    }

    @Override
    public Paging<OrderNotificationDTO> search(SearchParam param) {
        Specification<OrderNotification> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotEmpty(param.getPlatOrderId())) {
                expressions.add(cb.equal(root.get("platOrderId"), param.getPlatOrderId()));
            }
            if (param.getPlat() != null) {
                expressions.add(cb.equal(root.get("plat"), param.getPlat()));
            }
            if (param.getSuccess() != null) {
                expressions.add(cb.equal(root.get("success"), param.getSuccess()));
            }
            if (param.getType() != null) {
                expressions.add(cb.equal(root.get("type"), param.getType()));
            }
            return predicate;
        };
        Sort sort = null;
        if (param.getSort() == null) {
            param.setSort(OrderNotificationSort.ID_DESC);
        }
        switch (param.getSort()) {
            case ID_DESC:
                sort = new Sort(Sort.Direction.DESC, "id");
                break;
            case ID_ASC:
                sort = new Sort(Sort.Direction.ASC, "id");
                break;
        }
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<OrderNotification> page = orderNotificationRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item), param.getPage(), param.getPageSize());
    }

    @Override
    public void setSuccess(long id) {
        OrderNotification orderNotification = orderNotificationRepository.findOne(id);
        orderNotification.setSuccess(true);
        orderNotificationRepository.save(orderNotification);
    }

    public OrderNotificationDTO toDTO(OrderNotification order) {
        OrderNotificationDTO dto = new OrderNotificationDTO();
        BeanUtils.copyProperties(order, dto);
        return dto;
    }
}
