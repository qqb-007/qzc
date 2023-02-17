package info.batcloud.wxc.admin.controller.warehouse;

import com.github.pagehelper.PageInfo;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.dto.PurchaseOrderDto;
import info.batcloud.wxc.core.dto.RequireGoodsDto;
import info.batcloud.wxc.core.dto.UpdatePurchaseDto;
import info.batcloud.wxc.core.entity.CommonResult;
import info.batcloud.wxc.core.entity.CommonResultPage;
import info.batcloud.wxc.core.entity.PreShopProcurement;
import info.batcloud.wxc.core.service.warehouse.service.PurchaseOrderService;
import info.batcloud.wxc.core.service.warehouse.service.RequireGoodsService;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.List;

/**
 * @ClassName: Controller
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/back/warehouse")
public class PurchaseOrderController {

    @Resource
    private PurchaseOrderService  purchaseOrderService;

   /**
    *
    * describe创建采购订单
    * @author V
    * @date 16/2/2023 下午1:14
    * @param
    * @return
    */
   @PostMapping("/createPurchaseOrder")
   @ResponseBody
   public CommonResult createPurchaseOrder(PurchaseOrderDto purchaseOrderDto){
       purchaseOrderService.createPurchaseOrder(purchaseOrderDto);
       return new CommonResult("操作成功");
   }
   /**
    *
    * describe查询采购订单列表
    * @author V
    * @date 15/2/2023 上午9:46
    * @param
    * @return
    */
   @GetMapping("/getPurchaseList")
    public CommonResultPage getPurchaseList(@RequestParam(value = "orderNo",required = false)String orderNo, @RequestParam(value = "storeId",required = false)Integer storeId,
                                        @RequestParam(value = "page",required = false)Integer page){
       PageInfo<PreShopProcurement> shopProcurement = purchaseOrderService.getShopProcurement(orderNo, storeId, page);
       return new CommonResultPage(shopProcurement,shopProcurement.getPageNum(),shopProcurement.getTotal(),shopProcurement.getPageSize(),shopProcurement.getNextPage(),shopProcurement.isHasNextPage());
   }

   /**
    *
    * describe 删除采购订单
    * @author V
    * @date 16/2/2023 上午9:59
    * @param
    * @return
    */
   @DeleteMapping("/delPurchaseById/{id}")
    public CommonResult delPurchaseById(@PathVariable("id") Integer id){
       purchaseOrderService.delPurchaseById(id);
       return new CommonResult("操作成功");
   }

   /**
    *
    * describe 修改采购订单
    * @author V
    * @date 16/2/2023 下午1:44
    * @param
    * @return
    */
   @PutMapping("/updatePurchase/{id}")
    public CommonResult updatePurchase(@PathVariable("id") Integer id, UpdatePurchaseDto updatePurchaseDto){
       updatePurchaseDto.setId(id);
       purchaseOrderService.updatePurchase(updatePurchaseDto);
       return new CommonResult("操作成功");
   }

   
   /**
    *
    * describe 获取采购订单sku 详情
    * @author V
    * @date 17/2/2023 上午11:32
    * @param 
    * @return 
    */
   @GetMapping("/getPurchaseDetail")
    public CommonResultPage getPurchaseDetail(@RequestParam("id") Integer id,@RequestParam("page") Integer page){
       PageInfo purchaseRelation = purchaseOrderService.getPurchaseRelation(id, page);
       return new CommonResultPage(purchaseRelation,purchaseRelation.getPageNum(),purchaseRelation.getTotal(),purchaseRelation.getPageSize(),purchaseRelation.getNextPage(),purchaseRelation.isHasNextPage());

   }



}
