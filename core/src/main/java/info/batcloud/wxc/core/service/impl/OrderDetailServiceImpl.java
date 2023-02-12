package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.dto.OrderDTO;
import info.batcloud.wxc.core.dto.OrderDetailDTO;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.entity.OrderDetail;
import info.batcloud.wxc.core.entity.StoreUserFood;
import info.batcloud.wxc.core.enums.OrderStatus;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.FoodHelper;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.FoodRepository;
import info.batcloud.wxc.core.repository.OrderDetailRepository;
import info.batcloud.wxc.core.repository.StoreUserFoodRepository;
import info.batcloud.wxc.core.service.FoodService;
import info.batcloud.wxc.core.service.OrderDetailService;
import info.batcloud.wxc.core.service.OrderService;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Inject
    private OrderDetailRepository orderDetailRepository;

    @Inject
    private OrderService orderService;

    @Inject
    private FoodService foodService;

    @Inject
    private FoodRepository foodRepository;

    @Inject
    private StoreUserFoodRepository storeUserFoodRepository;

    @Override
    public Paging<OrderDetailDTO> search(SearchParam param) {
        Specification<OrderDetail> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (query.getResultType() != Long.class) {
                root.fetch("order", JoinType.LEFT).fetch("store", JoinType.LEFT)
                        .fetch("storeUser", JoinType.LEFT);
                root.fetch("food", JoinType.LEFT);
                root.fetch("foodSupplier", JoinType.LEFT);
            }
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotEmpty(param.getPlatOrderId())) {
                expressions.add(cb.equal(root.get("order").get("platOrderId"), param.getPlatOrderId()));
            }
            if (param.getFoodSupplierId() != null) {
                expressions.add(cb.equal(root.get("foodSupplier").get("id"), param.getFoodSupplierId()));
            }
            if (param.getStoreId() != null) {
                expressions.add(cb.equal(root.get("order").get("store").get("id"), param.getStoreId()));
            }
            if (param.getOrderIdList() != null && param.getOrderIdList().size() > 0) {
                expressions.add(root.get("order").get("id").in(param.getOrderIdList()));
            }
            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("order").get("store").get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (param.getDaySeq() != null) {
                expressions.add(cb.equal(root.get("order").get("daySeq"), param.getDaySeq()));
            }
            if (param.getPlat() != null) {
                expressions.add(cb.equal(root.get("order").get("plat"), param.getPlat()));
            }
            if (param.getStatus() != null) {
                expressions.add(cb.equal(root.get("order").get("status"), param.getStatus()));
            }
            if (param.getOrderId() != null) {
                expressions.add(cb.equal(root.get("order").get("id"), param.getOrderId()));
            }
            if (param.getStatusType() != null) {
                List<OrderStatus> statusList = new ArrayList<>();
                switch (param.getStatusType()) {
                    case CANCELED:
                        statusList.add(OrderStatus.CANCELED);
                        break;
                    case FINISHED:
                        statusList.add(OrderStatus.FINISHED);
                        break;
                    case RESERVE:
                        statusList.add(OrderStatus.PAID);
                        statusList.add(OrderStatus.MERCHANT_CONFIRMED);
                        statusList.add(OrderStatus.WAIT_MERCHANT_CONFIRM);
                        statusList.add(OrderStatus.SHIPPING);
                        statusList.add(OrderStatus.SHIPPED);
                        expressions.add(cb.gt(root.get("deliveryTime"), 0));
                        break;
                    case PROCESSING:
                        statusList.add(OrderStatus.PAID);
                        statusList.add(OrderStatus.MERCHANT_CONFIRMED);
                        statusList.add(OrderStatus.WAIT_MERCHANT_CONFIRM);
                        statusList.add(OrderStatus.SHIPPING);
                        statusList.add(OrderStatus.SHIPPED);
                }
                if (statusList.size() > 0) {
                    expressions.add(cb.in(root.get("order").get("status")).value(statusList));
                }
            }
            if (param.getDate() != null) {
                Date startTime = DateUtils.truncate(param.getDate(), Calendar.DATE);
                Date endTime = DateUtils.addDays(startTime, 1);
                expressions.add(cb.between(root.get("order").get("createTime"), startTime, endTime));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Paging<OrderDetailDTO> paging;
        if (param.isPageable()) {
            Pageable pageable = new PageRequest(param.getPage() - 1,
                    param.getPageSize(), sort);
            Page<OrderDetail> page = orderDetailRepository.findAll(specification, pageable);
            paging = PagingHelper.of(page, item -> toDetailDTO(item, param.isFetchOrder()), param.getPage(), param.getPageSize());
        } else {
            List<OrderDetail> list = orderDetailRepository.findAll(specification);
            paging = PagingHelper.of(list, item -> toDetailDTO(item, param.isFetchOrder()), list.size(), param.getPage(), param.getPageSize());
        }
        return paging;
    }

    @Override
    public void bindFood(long id, FoodBindParam param) {
        OrderDetail od = orderDetailRepository.findOne(id);
        Food food = foodRepository.findOne(param.getFoodId());
        StoreUserFood suf = storeUserFoodRepository.findByStoreUserIdAndFoodId(od.getOrder().getStore().getStoreUser().getId(), food.getId());
        if (suf == null) {
            throw new BizException(food.getName() + " 未添加到门店，请先添加");
        }
        for (FoodSku foodSku : FoodHelper.parseFoodSkuList(food.getSkuJson())) {
            if (param.getSkuId().equals(foodSku.getSkuId())) {
                od.setSkuId(foodSku.getSkuId());
                od.setSpec(foodSku.getSpec());
                od.setQuotePrice(suf.getQuotePrice() * foodSku.getQuoteUnitRatio());
                od.setFoodCode(food.getCode());
                od.setFood(food);
            }
        }
        orderDetailRepository.save(od);
        orderService.checkOrder(od.getOrder().getId());
    }

    @Override
    public OrderDetailDTO toDetailDTO(OrderDetail orderDetail) {
        return toDetailDTO(orderDetail, true);
    }

    @Override
    public OrderDetailDTO toDetailDTO(OrderDetail orderDetail, boolean fetchOrder) {
        OrderDetailDTO dto = new OrderDetailDTO();
        BeanUtils.copyProperties(orderDetail, dto);
        if (fetchOrder) {
            dto.setOrder(orderService.toDTO(orderDetail.getOrder(), false));
        } else {
            OrderDTO order = new OrderDTO();
            order.setId(orderDetail.getOrder().getId());
            dto.setOrder(order);
        }
        if(orderDetail.getFood() != null) {
            dto.setFoodPicture(orderDetail.getFood().getPicture());
        }
        return dto;
    }
}
