package info.batcloud.wxc.core.service.warehouse.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import info.batcloud.wxc.core.dto.PurchaseOrderDto;
import info.batcloud.wxc.core.dto.UpdatePurchaseDto;
import info.batcloud.wxc.core.entity.BaseException;
import info.batcloud.wxc.core.entity.PreReceiptOrders;
import info.batcloud.wxc.core.entity.PreShopProcurement;
import info.batcloud.wxc.core.entity.PreShopProcurementRelation;
import info.batcloud.wxc.core.service.warehouse.dao.PreReceiptOrdersDao;
import info.batcloud.wxc.core.service.warehouse.dao.PreShopProcurementDao;
import info.batcloud.wxc.core.service.warehouse.dao.PreShopProcurementRelationDao;
import info.batcloud.wxc.core.service.warehouse.dao.PurchaseOrderDao;
import info.batcloud.wxc.core.service.warehouse.service.PurchaseOrderService;
import org.apache.regexp.RE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @ClassName: PurchaseOrderServiceImpl
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@Service
public class PurchaseOrderServiceImpl  implements PurchaseOrderService {
    @Resource
    private PurchaseOrderDao purchaseOrderDao;
    @Resource
    private PreShopProcurementDao preShopProcurementDao;
    @Resource
    private PreShopProcurementRelationDao preShopProcurementRelationDao;
    @Resource
    private PreReceiptOrdersDao receiptOrdersDao;
    @Override
    @Transactional
    public Integer createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        try {
            String no = orderNo("CG");
            PreShopProcurement preShopProcurement=new PreShopProcurement();
            preShopProcurement.setOrderNo(no);
            preShopProcurement.setCreateTime(System.currentTimeMillis()/1000);
            BeanUtils.copyProperties(purchaseOrderDto,preShopProcurement);
            //保存采购订单
            preShopProcurementDao.insert(preShopProcurement);
            //保存收货单
            String sh=orderNo("SH");
            return  createRequireOrder(sh,preShopProcurement.getStoreId(),purchaseOrderDto.getStore(),preShopProcurement.getId());
        } catch (BeansException e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }
    //创建交货单
    public Integer createRequireOrder(String sh,Integer storeId,String storeName,Integer procurementId){
        try {
            PreReceiptOrders preReceiptOrders=new PreReceiptOrders();
            preReceiptOrders.setReceiptNo(sh);
            preReceiptOrders.setCreateTime(System.currentTimeMillis()/1000);
            preReceiptOrders.setStoreId(storeId);
            preReceiptOrders.setStoreName(storeName);
            preReceiptOrders.setIsDel(0);
            preReceiptOrders.setStatus(0);
            preReceiptOrders.setProcurementId(procurementId);
            return receiptOrdersDao.insert(preReceiptOrders);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }
    //生产订单号
    public String orderNo(String name){
        Random r = new Random( System.currentTimeMillis() );
        Integer randomNumber=10000 + r.nextInt(20000);
        String no=name+"-"+System.currentTimeMillis()/1000+randomNumber;
        return no;
    }
    @Override
    public PageInfo<PreShopProcurement> getShopProcurement(String orderNo, Integer storeId, Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        List<PreShopProcurement> preShopProcurements = purchaseOrderDao.selectAllByOrderNo(orderNo, storeId);
        PageInfo<PreShopProcurement> pageInfo=new PageInfo(preShopProcurements);
        return pageInfo;
    }

    @Override
    @Transactional
    public Integer delPurchaseById(Integer id) {
        purchaseOrderDao.delPurchaseById(id);
        return purchaseOrderDao.delPurchaseByIdToRelation(id);
    }

    @Override
    public Integer updatePurchase(UpdatePurchaseDto updatePurchaseDto) {
        //todo 查询交货单 获取交货id 并修改
        return  purchaseOrderDao.updatePurchase(updatePurchaseDto);
    }

    @Override
    public PageInfo getPurchaseRelation(Integer id,Integer pageNum) {
        PageHelper.startPage(pageNum,10);
        List<PreShopProcurementRelation> purchaseRelationByPurchaseId = preShopProcurementRelationDao.getPurchaseRelationByPurchaseId(id);
        PageInfo<PreShopProcurementRelation> page=new PageInfo<>(purchaseRelationByPurchaseId);
        return page;
    }

    @Override
    public PageInfo getPurchaseRelationToReceipt(Integer id, Integer pageNum) {
        PageHelper.startPage(pageNum,10);
        List<PreShopProcurementRelation> purchaseRelationByPurchaseId = preShopProcurementRelationDao.getPurchaseRelationByPurchaseIdToReceipt(id);
        PageInfo<PreShopProcurementRelation> page=new PageInfo<>(purchaseRelationByPurchaseId);
        return page;
    }
}
