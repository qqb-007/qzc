package info.batcloud.wxc.admin.controller;

import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.OrderDetailParam;
import com.sankuai.meituan.waimai.opensdk.vo.PLSProductCategory;
import com.sankuai.meituan.waimai.opensdk.vo.RetailParam;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import info.batcloud.wxc.core.service.MeituanWaimaiService;
import info.batcloud.wxc.core.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;

@RequestMapping("/api/meituan-waimai")
@RestController
public class MeituanWaimaiController {

    @Inject
    private OrderService orderService;

    @Inject
    private MeituanWaimaiService meituanWaimaiService;

    @PostMapping("/order/detail")
    public Object fetchOrderDetail(@RequestParam String orderId) {
        SystemParam systemParam = meituanWaimaiService.getSystemParam();
        OrderDetailParam orderDetailParam = null;
        OrderService.OrderSaveResult rs = new OrderService.OrderSaveResult();
        try {
            orderDetailParam = APIFactory.getOrderAPI().orderGetOrderDetail(systemParam,
                    Long.valueOf(orderId.replaceAll("\\s", "")), 0l);
            rs = orderService.saveOrder(MeituanWaimaiService.toOrder(orderDetailParam));
        } catch (ApiOpException e) {
            e.printStackTrace();
            rs.setSuccess(false);
            rs.setMsg(e.getMsg());
        } catch (ApiSysException e) {
            e.printStackTrace();
            rs.setSuccess(false);
            rs.setMsg(e.getExceptionEnum().getMsg());
        }
        return rs;
    }

    @GetMapping("/retail/list/{appPoiCode}")
    public Object retailList(@PathVariable String appPoiCode) throws ApiSysException, ApiOpException {
        SystemParam systemParam = meituanWaimaiService.getSystemParam();
        List<RetailParam> foodParams = APIFactory.getNewRetailApi().retailList(systemParam, appPoiCode);
        return foodParams;
    }

    @PutMapping("/retail/delete/{appPoiCode}")
    public Object deleteRetailParam(@PathVariable String appPoiCode, @RequestParam String appFoodCode) throws ApiSysException, ApiOpException {
        SystemParam systemParam = meituanWaimaiService.getSystemParam();
        String rs = APIFactory.getNewRetailApi().retailDelete(systemParam, appPoiCode, appFoodCode);
        Map<String, Object> map = new HashMap<>();
        map.put("success", rs.equals("ok"));
        map.put("msg", rs);
        return map;
    }

    @GetMapping("/retail/getSpTagIds")
    public Object getSpTagIds() throws ApiSysException, ApiOpException {
        SystemParam systemParam = meituanWaimaiService.getSystemParam();
        List<PLSProductCategory> categories = APIFactory.getNewRetailApi().retailGetSpTagIds(systemParam, "6558293");
        List<PLSProductCategoryVo> list = new ArrayList<>();
        Map<String, PLSProductCategoryVo> map = new HashMap<>();
        for (PLSProductCategory category : categories) {
            String[] tmps = category.getNamePath().split(",");
            for (int i = 0; i < tmps.length; i++) {
                if (map.containsKey(tmps[i])) {
                    continue;
                }
                if (i == 0) {
                    if (!map.containsKey(tmps[0])) {
                        PLSProductCategoryVo _vo = new PLSProductCategoryVo(tmps[i], join(tmps, i));
                        list.add(_vo);
                        _vo.setLevel(i);
                        map.put(tmps[i], _vo);
                    }
                } else {
                    PLSProductCategoryVo _vo = new PLSProductCategoryVo(tmps[i], join(tmps, i));
                    if (tmps[i].equals(category.getName())) {
                        BeanUtils.copyProperties(category, _vo);
                    }
                    _vo.setNamePath(_vo.getNamePath().replace(",", "/"));
                    map.put(tmps[i], _vo);
                    _vo.setLevel(i);
                    map.get(tmps[i - 1]).getChildren().add(_vo);
                }
            }
        }
        return list;
    }

    private String join(String[] strs, int idx) {
        return String.join("/", Arrays.copyOf(strs, idx));
    }

    public static class PLSProductCategoryVo extends PLSProductCategory {
        private List<PLSProductCategoryVo> children = new ArrayList<>();

        public PLSProductCategoryVo(String name, String namePath) {
            this.name = name;
            this.namePath = namePath;
        }

        public List<PLSProductCategoryVo> getChildren() {
            return children;
        }

        public void setChildren(List<PLSProductCategoryVo> children) {
            this.children = children;
        }
    }

}
