package info.batcloud.wxc.merchant.api.controller.warehouse;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.dto.RequireGoodsDto;
import info.batcloud.wxc.core.entity.CommonResult;
import info.batcloud.wxc.core.entity.CommonResultPage;
import info.batcloud.wxc.core.entity.PreRequireGoodsOrdersRelation;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.warehouse.service.RequireGoodsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: RequirGoodsController
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/requireGoods")
@PreAuthorize("hasRole('STORE_USER')")
public class RequireGoodsController {
    @Resource
    private RequireGoodsService requireGoodsService;

    @Inject
    private StoreUserRepository storeUserRepository;

    /**
     * describe 添加要货单
     *
     * @param
     * @return
     * @author V
     * @date 14/2/2023 下午1:15
     */
    @PostMapping("/addReqioreGoods")
    public Object addRequireGoods(@RequestParam String json) {
        try {
            RequireGoodsDto requireGoodsDto = JSON.parseObject(json, RequireGoodsDto.class);
            requireGoodsDto.getPreRequireGoodsOrders().setStoreName(storeUserRepository.findOne(requireGoodsDto.getPreRequireGoodsOrders().getStoreId().longValue()).getName());
            requireGoodsService.addRequireGoodsOrder(requireGoodsDto);
            return BusinessResponse.ok("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return BusinessResponse.error("操作失败");
        }
    }

    /**
     * describe 根据门店获取要货单信息
     *
     * @param
     * @return
     * @author V
     * @date 20/2/2023 下午1:30
     */
    @GetMapping("/getRequireGoodsByStoreId")
    public Object getRequireGoodsByStoreId(@RequestParam("page") Integer page) {
        PageInfo requireGoodsByStoreId = requireGoodsService.getRequireGoodsByStoreId(SecurityHelper.loginStoreUserId().intValue(), page);
        Map<String, Object> map = new HashMap<>(1);
        map.put("data", new CommonResultPage(requireGoodsByStoreId, requireGoodsByStoreId.getPageNum(), requireGoodsByStoreId.getTotal(), requireGoodsByStoreId.getPageSize(), requireGoodsByStoreId.getNextPage(), requireGoodsByStoreId.isHasNextPage()));
        return BusinessResponse.ok(map);
    }

    /**
     * describe 根据id获取要货单详情
     *
     * @param
     * @return
     * @author V
     * @date 20/2/2023 下午1:39
     */
    @GetMapping("/getRequireGoodsById")
    public Object getRequireGoodsById(@RequestParam("id") Integer id, @RequestParam("page") Integer page) {
        PageInfo<PreRequireGoodsOrdersRelation> requireGoodsById = requireGoodsService.getRequireGoodsById(id, page);
        Map<String, Object> map = new HashMap<>(1);
        map.put("data", new CommonResultPage(requireGoodsById, requireGoodsById.getPageNum(), requireGoodsById.getTotal(), requireGoodsById.getPageSize(), requireGoodsById.getNextPage(), requireGoodsById.isHasNextPage()));
        return BusinessResponse.ok(map);

    }


}
