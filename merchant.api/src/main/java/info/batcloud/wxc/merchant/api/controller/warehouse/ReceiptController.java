package info.batcloud.wxc.merchant.api.controller.warehouse;

import com.github.pagehelper.PageInfo;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.dto.FoodSkuDTO;
import info.batcloud.wxc.core.dto.StoreUserFoodSkuDTO;
import info.batcloud.wxc.core.entity.CommonResult;
import info.batcloud.wxc.core.entity.CommonResultPage;
import info.batcloud.wxc.core.entity.FoodSku;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.entity.PreReceiptOrders;
import info.batcloud.wxc.core.repository.FoodSkuRepository;
import info.batcloud.wxc.core.service.FoodSkuService;
import info.batcloud.wxc.core.service.StoreUserFoodSkuService;
import info.batcloud.wxc.core.service.warehouse.service.PurchaseOrderService;
import info.batcloud.wxc.core.service.warehouse.service.ReceiptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ReceiptController
 * @Description:
 * @Author V
 * @Date 18/2/2023
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/receipt")
@PreAuthorize("hasRole('STORE_USER')")
public class ReceiptController {
    @Resource
    private ReceiptService receiptService;
    @Resource
    private PurchaseOrderService purchaseOrderService;

    @Resource
    private StoreUserFoodSkuService storeUserFoodSkuService;

    /**
     * describe 修改收货单信息
     *
     * @param
     * @return
     * @author V
     * @date 18/2/2023 下午4:23
     */
    @PostMapping("/updateReceiptInfo")
    public Object updateReceiptInfo(@RequestParam("id") Integer id, @RequestParam("arrivePrice") Double arrivePrice,
                                    @RequestParam("arrivaNum") Integer arrivaNum, @RequestParam("remark") String remark,
                                    @RequestParam("status") Integer status) {
        receiptService.updateReceiptOrderToApp(id, arrivePrice, arrivaNum, remark, status);
        return BusinessResponse.ok("操作成功");
    }

    /**
     * describe 修改采购订单中收货信息
     *
     * @param
     * @return
     * @author V
     * @date 18/2/2023 下午4:23
     */
    @PostMapping("/updatePurchaseGoodsInfo")
    public Object updatePurchaseGoodsInfo(@RequestParam("id") Integer id, @RequestParam("actualArrivalNum") Integer actualArrivalNum,
                                                @RequestParam("actualArrivalSumprice") Double actualArrivalSumprice) {
        purchaseOrderService.updatePurchaseGoodsToApp(id, actualArrivalNum, actualArrivalSumprice);
        return BusinessResponse.ok("操作成功");
    }

    /**
     * describe 获取门店收货单列表
     *
     * @param
     * @return
     * @author V
     * @date 20/2/2023 下午2:51
     */
    @GetMapping("/getReceiptByStoreId")
    public Object getReceiptByStoreId(@RequestParam("page") Integer page) {
        PageInfo receiptByStoreId = receiptService.getReceiptByStoreId(SecurityHelper.loginStoreUserId().intValue(), page);
        Map<String, Object> map = new HashMap<>(1);
        map.put("data", new CommonResultPage(receiptByStoreId, receiptByStoreId.getPageNum(), receiptByStoreId.getTotal(), receiptByStoreId.getPageSize(), receiptByStoreId.getNextPage(), receiptByStoreId.isHasNextPage()));
        return BusinessResponse.ok(map);
    }

    /**
     * describe 获取收货单列表详情列表
     *
     * @param
     * @return
     * @author V
     * @date 20/2/2023 下午3:23
     */
    @GetMapping("/getReceiptById")
    public Object getReceiptById(@RequestParam("id") Integer id, @RequestParam("page") Integer page) {
        PageInfo receiptGoodsById = receiptService.getReceiptGoodsById(id, page);
        Map<String, Object> map = new HashMap<>(1);
        map.put("data", new CommonResultPage(receiptGoodsById, receiptGoodsById.getPageNum(), receiptGoodsById.getTotal(), receiptGoodsById.getPageSize(), receiptGoodsById.getNextPage(), receiptGoodsById.isHasNextPage()));
        return BusinessResponse.ok(map);
    }


    /**
     * describe 获取收货单列表详情 通过sku
     *
     * @param
     * @return
     * @author V
     * @date 21/2/2023 下午3:39
     */
    @GetMapping("/getReceiptListBysku")
    public Object getReceiptListBysku(@RequestParam("skuId") String upc) {
        StoreUserFoodSkuDTO foodSkuDTO = storeUserFoodSkuService.getByUpcAndStoreUserId(SecurityHelper.loginStoreUserId().intValue(), upc);
        if (foodSkuDTO == null) {
            throw new BizException("当前门店不存在此商品，请添加后操作");
        }
        List<PreReceiptOrders> receiptListBysku = receiptService.getReceiptListBysku(foodSkuDTO.getId().intValue(), SecurityHelper.loginStoreUserId().intValue());
        Map<String, Object> map = new HashMap<>(1);
        map.put("data", new CommonResult(receiptListBysku));
        return BusinessResponse.ok(map);
    }
}
