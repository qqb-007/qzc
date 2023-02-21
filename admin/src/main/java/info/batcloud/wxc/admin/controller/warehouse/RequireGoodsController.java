package info.batcloud.wxc.admin.controller.warehouse;

import com.github.pagehelper.PageInfo;
import info.batcloud.wxc.core.entity.CommonResult;
import info.batcloud.wxc.core.entity.CommonResultPage;
import info.batcloud.wxc.core.service.warehouse.service.RequireGoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName: RequireGoodsController
 * @Description:
 * @Author V
 * @Date 17/2/2023
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/back/requireGoods")
public class RequireGoodsController {
    @Resource
    private RequireGoodsService requireGoodsService;
    /**
     *
     * describe 获取要货单信息
     * @author V
     * @date 17/2/2023 下午5:11
     * @param
     * @return
     */
    @GetMapping("/getRequireGoods")
    public CommonResultPage getRequireGoods(@RequestParam("requireNo")String requireNo,@RequestParam("storeId") Integer storeId,
                                            @RequestParam("status") Integer status,@RequestParam("page") Integer page,
                                            @RequestParam("startTime")String startTime,@RequestParam("endTime")String endTime){
        String trim = requireNo.trim();
        PageInfo requireGoodsInfo = requireGoodsService.getRequireGoodsInfo(trim, storeId, status, page, startTime, endTime);
        return new CommonResultPage(requireGoodsInfo,requireGoodsInfo.getPageNum(),requireGoodsInfo.getTotal(),requireGoodsInfo.getPageSize(),requireGoodsInfo.getNextPage(),requireGoodsInfo.isHasNextPage());

    }

    /**
     *
     * describe 获取要货单详情
     * @author V
     * @date 17/2/2023 下午5:11
     * @param
     * @return
     */
    @GetMapping("/getRequireGoodsDetail")
    public CommonResultPage getRequireGoodsDetail(@RequestParam("id") Integer id,@RequestParam("page") Integer page){
        PageInfo requireGoodsOrdersRelationList = requireGoodsService.getRequireGoodsOrdersRelationList(id, page);
        return new CommonResultPage(requireGoodsOrdersRelationList,requireGoodsOrdersRelationList.getPageNum(),requireGoodsOrdersRelationList.getTotal(),requireGoodsOrdersRelationList.getPageSize(),requireGoodsOrdersRelationList.getNextPage(),requireGoodsOrdersRelationList.isHasNextPage());

    }

    /**
     *
     * describe 编辑要货单处理状态
     * @author V
     * @date 21/2/2023 上午9:25
     * @param
     * @return
     */
    @PutMapping("/editRequireGoods")
    public CommonResult editRequireGoods(@RequestParam("id") Integer id,@RequestParam("status")Integer status){
        requireGoodsService.editRequireGoods(id,status);
        return new CommonResult("操作成功");
    }

}
