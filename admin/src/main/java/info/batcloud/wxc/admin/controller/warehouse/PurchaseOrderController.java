package info.batcloud.wxc.admin.controller.warehouse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.Map;

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
   public CommonResult createPurchaseOrder(@RequestParam("data") String data,@RequestParam("storeName")String storeName ,@RequestParam("storeId")Integer storeId ){
       System.out.println(data);
       JSONArray array = JSONArray.parseArray(data);
       purchaseOrderService.createPurchaseOrder(array, storeName,storeId);
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
   @PostMapping("/updatePurchase")
    public CommonResult updatePurchase(@RequestParam("id") Integer id,@RequestParam("data")String data,
                                       @RequestParam("storeName")String storeName,@RequestParam("logisticsNo")String logisticsNo,
                                       @RequestParam("arrivalPrice")Double arrivalPrice,@RequestParam("arrivalNum") Integer arrivalNum,
                                       @RequestParam("storeId")Integer storeId){
       System.out.println(data);
       JSONArray array = JSONArray.parseArray(data);
       purchaseOrderService.updatePurchase(id,array,storeName,logisticsNo,arrivalPrice,arrivalNum,storeId);
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

    /**
     *
     * describe 获取采购单编辑数据信息
     * @author V
     * @date 18/2/2023 上午9:55
     * @param
     * @return
     */
    @GetMapping("/getEditPurchaseInfo/{id}")
    public CommonResult getEditPurchaseInfo(@PathVariable("id") Integer id){
        Map<String, Object> editPurchaseInfo = purchaseOrderService.getEditPurchaseInfo(id);
        return new CommonResult(editPurchaseInfo);
    }

}
