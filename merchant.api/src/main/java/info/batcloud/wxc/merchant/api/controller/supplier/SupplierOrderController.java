package info.batcloud.wxc.merchant.api.controller.supplier;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.dto.OrderDTO;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.mapper.OrderDetailMapper;
import info.batcloud.wxc.core.mapper.domain.OrderDetailStat;
import info.batcloud.wxc.core.service.OrderService;
import info.batcloud.wxc.merchant.api.controller.supplier.vo.OrderVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/food-supplier/order")
@PreAuthorize("hasRole('FOOD_SUPPLIER')")
public class SupplierOrderController {

    @Inject
    private OrderService orderService;

    @Inject
    private OrderDetailMapper orderDetailMapper;

    @GetMapping("/search")
    public Object search(OrderService.SearchParam param) {
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        param.setFoodSupplierId(SecurityHelper.loginFoodSupplierId());
        param.setFetchDetail(false);
        if (param.getDate() == null) {
            param.setDate(new Date());
        }
        if (StringUtils.isNotEmpty(param.getPlatOrderId())) {
            param.setDate(null);
            param.setStatusType(null);
        }
        Paging<OrderDTO> paging = orderService.search(param);
        if (paging.getResults().size() == 0) {
            return BusinessResponse.ok(paging);
        }
        List<OrderVo> voList = new ArrayList<>();
        Map<Long, OrderVo> voMap = new HashMap<>(20);
        List<Long> orderIdList = paging.getResults().stream().map(o -> {
            OrderVo vo = new OrderVo();
            voList.add(vo);
            BeanUtils.copyProperties(o, vo);
            voMap.put(o.getId(), vo);
            return o.getId();
        }).collect(Collectors.toList());
        List<OrderDetailStat> detailStatList = orderDetailMapper.statByFoodSupplierIdAndOrderIdList(SecurityHelper.loginFoodSupplierId(), orderIdList);
        for (OrderDetailStat stat : detailStatList) {
            OrderVo vo = voMap.get(stat.getOrderId());
            if (vo != null) {
                vo.setRefundMoneyForSupplier(stat.getRefundMoney());
                vo.setTotalMoneyForSupplier(stat.getTotalMoney());
                vo.setRemainMoneyForSupplier(stat.getRemainMoney());
            }
        }
        return BusinessResponse.ok(PagingHelper.of(voList, paging.getTotal(), paging.getPage(), paging.getPageSize()));
    }

    @GetMapping("/detail-list/{id}")
    public Object search(@PathVariable long id) {
        return BusinessResponse.ok(orderService.findDetailByOrderIdAndFoodSupplierId(id, SecurityHelper.loginFoodSupplierId()));
    }

}
