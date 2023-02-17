package info.batcloud.wxc.merchant.api.controller.warehouse;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.dto.RequireGoodsDto;
import info.batcloud.wxc.core.service.warehouse.service.RequireGoodsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
