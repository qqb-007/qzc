package info.batcloud.wxc.merchant.api.controller.warehouse;

import info.batcloud.wxc.core.entity.CommonResult;
import info.batcloud.wxc.core.service.warehouse.service.PurchaseOrderService;
import info.batcloud.wxc.core.service.warehouse.service.ReceiptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName: ReceiptController
 * @Description:
 * @Author V
 * @Date 18/2/2023
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {
    @Resource
    private ReceiptService receiptService;
    @Resource
    private PurchaseOrderService purchaseOrderService;

    /**
     *
     * describe 修改收货单信息
     * @author V
     * @date 18/2/2023 下午4:23
     * @param
     * @return
     */
    @PostMapping("/updateReceiptInfo")
    public CommonResult updateReceiptInfo(@RequestParam("id") Integer id,@RequestParam("arrivePrice") Double arrivePrice,
                                          @RequestParam("arrivaNum")Integer arrivaNum,@RequestParam("remark")String remark,
                                          @RequestParam("status") Integer status){
        receiptService.updateReceiptOrderToApp(id,arrivePrice,arrivaNum,remark,status);
        return new CommonResult("操作成功");
    }

    /**
     *
     * describe 修改采购订单中收货信息
     * @author V
     * @date 18/2/2023 下午4:23
     * @param
     * @return
     */
    @PostMapping("/updatePurchaseGoodsInfo")
    public CommonResult updatePurchaseGoodsInfo(@RequestParam("id") Integer id,@RequestParam("actualArrivalNum") Integer actualArrivalNum,
                                          @RequestParam("actualArrivalSumprice")Double actualArrivalSumprice){
        purchaseOrderService.updatePurchaseGoodsToApp(id,actualArrivalNum,actualArrivalSumprice);
        return new CommonResult("操作成功");
    }
}
