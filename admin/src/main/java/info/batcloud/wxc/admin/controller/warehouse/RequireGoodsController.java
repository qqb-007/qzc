package info.batcloud.wxc.admin.controller.warehouse;

import com.github.pagehelper.PageInfo;
import info.batcloud.wxc.core.entity.CommonResultPage;
import info.batcloud.wxc.core.service.warehouse.service.RequireGoodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        PageInfo requireGoodsInfo = requireGoodsService.getRequireGoodsInfo(requireNo, storeId, status, page, startTime, endTime);
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
}
