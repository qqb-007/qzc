package info.batcloud.wxc.admin.controller.warehouse;

import com.github.pagehelper.PageInfo;
import info.batcloud.wxc.core.entity.CommonResultPage;
import info.batcloud.wxc.core.service.warehouse.service.PurchaseOrderService;
import info.batcloud.wxc.core.service.warehouse.service.ReceiptService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: ReceiptController
 * @Description:
 * @Author V
 * @Date 17/2/2023
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/back/receipt")
public class ReceiptController {
    @Resource
    private ReceiptService receiptService;
    @Resource
    private PurchaseOrderService purchaseOrderService;

    /**
     *
     * describe 收货单列表
     * @author V
     * @date 18/2/2023 下午4:50
     * @param
     * @return
     */
    @GetMapping("/getReceiptList")
    public CommonResultPage getReceiptList(@RequestParam("storeId") Integer storeId, @RequestParam("startTime")String startTime,
                                           @RequestParam("endTime") String endTime, @RequestParam("logisticsNo") String logisticsNo,
                                           @RequestParam("page")Integer page,@RequestParam("receiptNo") String receiptNo){
        PageInfo receiptOrderList = receiptService.getReceiptOrderList(storeId, startTime, endTime, logisticsNo, page,receiptNo);
        return new CommonResultPage(receiptOrderList,receiptOrderList.getPageNum(),receiptOrderList.getTotal(),receiptOrderList.getPageSize(),receiptOrderList.getNextPage(),receiptOrderList.isHasNextPage());

    }
    /**
     *
     * describe 获取收货订单sku 详情
     * @author V
     * @date 17/2/2023 上午11:32
     * @param
     * @return
     */
    @GetMapping("/getPurchaseDetail")
    public CommonResultPage getPurchaseDetail(@RequestParam("procurementId") Integer procurementId,@RequestParam("page") Integer page){
        PageInfo purchaseRelation = purchaseOrderService.getPurchaseRelationToReceipt(procurementId, page);
        return new CommonResultPage(purchaseRelation,purchaseRelation.getPageNum(),purchaseRelation.getTotal(),purchaseRelation.getPageSize(),purchaseRelation.getNextPage(),purchaseRelation.isHasNextPage());

    }

}
