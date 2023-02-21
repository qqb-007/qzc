package info.batcloud.wxc.merchant.api.controller.warehouse;

import com.github.pagehelper.PageInfo;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.dto.RequireGoodsDto;
import info.batcloud.wxc.core.entity.CommonResult;
import info.batcloud.wxc.core.entity.CommonResultPage;
import info.batcloud.wxc.core.entity.PreRequireGoodsOrdersRelation;
import info.batcloud.wxc.core.service.warehouse.service.RequireGoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName: RequirGoodsController
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/requireGoods")
public class RequireGoodsController {
    @Resource
    private RequireGoodsService requireGoodsService;

    /**
     *
     * describe 添加要货单
     * @author V
     * @date 14/2/2023 下午1:15
     * @param
     * @return
     */
    @PostMapping("/addReqioreGoods")
    public Object addRequireGoods(@RequestBody RequireGoodsDto requireGoodsDto){
        try {
            requireGoodsService.addRequireGoodsOrder(requireGoodsDto);
            return BusinessResponse.ok("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return BusinessResponse.error("操作失败");
        }
    }

    /**
     *
     * describe 根据门店获取要货单信息
     * @author V
     * @date 20/2/2023 下午1:30
     * @param
     * @return
     */
    @GetMapping("/getRequireGoodsByStoreId")
    public CommonResultPage getRequireGoodsByStoreId(@RequestParam("storeId")Integer storeId,@RequestParam("page")Integer page){
        PageInfo requireGoodsByStoreId = requireGoodsService.getRequireGoodsByStoreId(storeId, page);
        return new CommonResultPage(requireGoodsByStoreId,requireGoodsByStoreId.getPageNum(),requireGoodsByStoreId.getTotal(),requireGoodsByStoreId.getPageSize(),requireGoodsByStoreId.getNextPage(),requireGoodsByStoreId.isHasNextPage());
    }

    /**
     *
     * describe 根据id获取要货单详情
     * @author V
     * @date 20/2/2023 下午1:39
     * @param
     * @return
     */
    @GetMapping("/getRequireGoodsById")
    public CommonResultPage getRequireGoodsById(@RequestParam("id")Integer id,@RequestParam("page")Integer page){
        PageInfo<PreRequireGoodsOrdersRelation> requireGoodsById = requireGoodsService.getRequireGoodsById(id, page);
        return new CommonResultPage(requireGoodsById,requireGoodsById.getPageNum(),requireGoodsById.getTotal(),requireGoodsById.getPageSize(),requireGoodsById.getNextPage(),requireGoodsById.isHasNextPage());

    }



}
