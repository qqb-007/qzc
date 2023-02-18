package info.batcloud.wxc.core.service.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import info.batcloud.wxc.core.dto.GoodsDto;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
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
    public Integer createPurchaseOrder(JSONArray jsonArray,String storeName,Integer storeId) {
        try {
            Map<String, Object> goods = createGoods(jsonArray);
            String no = orderNo("CG");
            PreShopProcurement preShopProcurement=new PreShopProcurement();
            preShopProcurement.setOrderNo(no);
            preShopProcurement.setProcurementType(jsonArray.size());
            preShopProcurement.setProcurementNum(Integer.parseInt(goods.get("sumNum").toString()));
            preShopProcurement.setProcurementPrice(Double.parseDouble(goods.get("sumPrice").toString()));
            preShopProcurement.setCreateTime(System.currentTimeMillis()/1000);
            preShopProcurement.setStore(storeName);
            preShopProcurement.setStoreId(storeId);
            //保存采购订单
            preShopProcurementDao.insert(preShopProcurement);
            //创建采购商品信息
            List<PreShopProcurementRelation> list = (List<PreShopProcurementRelation>) goods.get("list");
            List<PreShopProcurementRelation> shopInfo = list.stream().map(v -> {
                v.setShopProcurementId(preShopProcurement.getId());
                v.setCreateTime(System.currentTimeMillis() / 1000);
                return v;
            }).collect(Collectors.toList());
            preShopProcurementRelationDao.insertBatch(shopInfo);

            //保存收货单
            String sh=orderNo("SH");
            return  createRequireOrder(sh,preShopProcurement.getStoreId(),storeName,preShopProcurement.getId());
        } catch (BeansException e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }
    //创建商品数据
    public Map<String,Object> createGoods(JSONArray jsonArray){
        AtomicReference<Double> sumPrice= new AtomicReference<>(0.0);
        AtomicReference<Integer> sumNum= new AtomicReference<>(0);
        List<PreShopProcurementRelation> list = jsonArray.stream().map(v -> {
            PreShopProcurementRelation preShopProcurementRelation = new PreShopProcurementRelation();
            Integer foodId = JSONObject.parseObject(v.toString()).getInteger("foodId");
            Double inputPrice = JSONObject.parseObject(v.toString()).getDouble("inputPrice");
            Integer num = JSONObject.parseObject(v.toString()).getInteger("num");
            Integer skuId = JSONObject.parseObject(v.toString()).getInteger("skuId");
            String foodName = JSONObject.parseObject(v.toString()).getString("foodName");
            String img = JSONObject.parseObject(v.toString()).getString("img");
            preShopProcurementRelation.setFoodId(foodId);
            preShopProcurementRelation.setImg(img);
            preShopProcurementRelation.setFoodPrice(inputPrice);
            preShopProcurementRelation.setFoodNum(num);
            preShopProcurementRelation.setSkuId(skuId);
            preShopProcurementRelation.setFoodName(foodName);
            sumPrice.updateAndGet(v1 -> v1 + inputPrice * num);
            sumNum.updateAndGet(v1 -> v1 + num);
            return preShopProcurementRelation;
        }).collect(Collectors.toList());
        Map<String,Object> map=new HashMap<>();
        map.put("list",list);
        map.put("sumPrice",sumPrice);
        map.put("sumNum",sumNum);
        return map;
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

    @Override
    public Map<String, Object> getEditPurchaseInfo(Integer id) {
        Map<String,Object> map=new HashMap<>();
        PreShopProcurementRelation preShopProcurementRelation= new PreShopProcurementRelation();
        preShopProcurementRelation.setShopProcurementId(id);
        List<PreShopProcurementRelation> preShopProcurementRelations = preShopProcurementRelationDao.queryAll(preShopProcurementRelation);
        map.put("data",preShopProcurementRelations);
        PreShopProcurement preShopProcurement = preShopProcurementDao.queryById(id);
        map.put("arrivalNum",preShopProcurement.getArrivalNum());
        map.put("arrivalPrice",preShopProcurement.getArrivalPrice());
        map.put("logisticsNo",preShopProcurement.getLogisticsNo());
        return map;
    }
}
