package info.batcloud.wxc.core.service.warehouse.service.impl;

import info.batcloud.wxc.core.dto.RequireGoodsDto;
import info.batcloud.wxc.core.entity.BaseException;
import info.batcloud.wxc.core.entity.PreRequireGoodsOrdersRelation;
import info.batcloud.wxc.core.service.warehouse.dao.RequireGoodsRelationDao;
import info.batcloud.wxc.core.service.warehouse.dao.RequireGoodsDao;
import info.batcloud.wxc.core.service.warehouse.service.RequireGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @ClassName: RequireGoodsServiceImpl
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@Slf4j
@Service
public class RequireGoodsServiceImpl implements RequireGoodsService {
    @Resource
    private RequireGoodsDao requireGoodsDao;
    @Resource
    private RequireGoodsRelationDao requireGoodsRelationDao;
    @Override
    @Transactional
    public Integer addRequireGoodsOrder(RequireGoodsDto requireGoodsDto) {
        try {
            Random r = new Random( System.currentTimeMillis() );
            Integer randomNumber=10000 + r.nextInt(20000);
            String no="CG-"+System.currentTimeMillis()/1000+randomNumber;
            requireGoodsDto.getPreRequireGoodsOrders().setRequireGoodsNo(no);
            //保存要货单
            requireGoodsDto.getPreRequireGoodsOrders().setCreateTime(System.currentTimeMillis()/1000);
            requireGoodsDao.save(requireGoodsDto.getPreRequireGoodsOrders());
            List<PreRequireGoodsOrdersRelation> list = requireGoodsDto.getRequireGoodsOrdersRelation().stream().map(v -> {
                v.setRequireGoodsId(requireGoodsDto.getPreRequireGoodsOrders().getId());
                v.setCreateTime(System.currentTimeMillis() / 1000);
                return v;
            }).collect(Collectors.toList());
//            保存采购明细单
            return requireGoodsRelationDao.savaRequireGoodsRelationInfo(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("要货单创建失败");
        }

    }
}
