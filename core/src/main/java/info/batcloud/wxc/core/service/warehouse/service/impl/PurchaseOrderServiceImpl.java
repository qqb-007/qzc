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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            log.info("---????????????????????????????????????????????????---");
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
            //??????????????????
            preShopProcurementDao.insert(preShopProcurement);
            log.info("---????????????????????????????????????????????????---");
            //????????????????????????
            List<PreShopProcurementRelation> list = (List<PreShopProcurementRelation>) goods.get("list");
            List<PreShopProcurementRelation> shopInfo = list.stream().map(v -> {
                v.setShopProcurementId(preShopProcurement.getId());
                v.setCreateTime(System.currentTimeMillis() / 1000);
                return v;
            }).collect(Collectors.toList());
            preShopProcurementRelationDao.insertBatch(shopInfo);
            log.info("---??????????????????????????????????????????????????????---");
            //???????????????
            String sh=orderNo("SH");
            Integer id = createRequireOrder(sh, preShopProcurement.getStoreId(), storeName, preShopProcurement.getId());
            //?????????????????????????????????
            PreShopProcurement preShop=new PreShopProcurement();
            preShop.setId(preShopProcurement.getId());
            preShop.setReceiptNo(sh);
            preShop.setReceiptId(id);
            preShopProcurementDao.update(preShop);
            log.info("---????????????????????????????????????????????????,????????????????????????---");
            return 1;
        } catch (BeansException e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }
    //??????????????????
    public Map<String,Object> createGoods(JSONArray jsonArray){
        log.info("---????????????????????????---");
        try {
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
            log.info("---????????????????????????---");
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("??????????????????");
        }
    }
    //???????????????
    public Integer createRequireOrder(String sh,Integer storeId,String storeName,Integer procurementId){
        try {
            PreReceiptOrders preReceiptOrders=new PreReceiptOrders();
            preReceiptOrders.setReceiptNo(sh);
            preReceiptOrders.setCreateTime(System.currentTimeMillis()/1000);
            preReceiptOrders.setStoreId(storeId);
            preReceiptOrders.setStoreName(storeName);
            preReceiptOrders.setIsDel(0);
            preReceiptOrders.setStatus(2);
            preReceiptOrders.setProcurementId(procurementId);
            receiptOrdersDao.insert(preReceiptOrders);
            log.info("---?????????????????????---");
            return preReceiptOrders.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }
    //???????????????
    public String orderNo(String name){
        Random r = new Random( System.currentTimeMillis() );
        Integer randomNumber=10000 + r.nextInt(20000);
        String no=name+"-"+System.currentTimeMillis()/1000+randomNumber;
        return no;
    }
    @Override
    public PageInfo<PreShopProcurement> getShopProcurement(String orderNo, Integer storeId, Integer pageNum) {
        PageHelper.startPage(pageNum,20);
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
    @Transactional
    public Integer updatePurchase(Integer id,JSONArray data,
                                  String storeName,String logisticsNo,
                                  Double arrivalPrice,Integer arrivalNum,
                                  Integer storeId) {
        try {
            log.info("---????????????????????????????????????????????????---");
            //?????????????????????
            Map<String, Object> goods = createGoods(data);
            PreShopProcurement preShopProcurement=new PreShopProcurement();
            preShopProcurement.setId(id);
            preShopProcurement.setProcurementType(data.size());
            preShopProcurement.setStore(storeName);
            preShopProcurement.setStoreId(storeId);
            preShopProcurement.setLogisticsNo(logisticsNo);
            preShopProcurement.setArrivalPrice(arrivalPrice);
            preShopProcurement.setArrivalNum(arrivalNum);
            preShopProcurement.setProcurementNum(Integer.parseInt(goods.get("sumNum").toString()));
            preShopProcurement.setProcurementPrice(Double.parseDouble(goods.get("sumPrice").toString()));
            preShopProcurementDao.update(preShopProcurement);
            log.info("---????????????????????????????????????????????????---");
            //??????????????????
            preShopProcurementRelationDao.deleteByPurchaseId(id);
            log.info("---????????????????????????????????????????????????????????????---");
            //??????????????????????????????
            List<PreShopProcurementRelation> list = (List<PreShopProcurementRelation>) goods.get("list");
            List<PreShopProcurementRelation> shopInfo = list.stream().map(v -> {
                v.setShopProcurementId(preShopProcurement.getId());
                v.setCreateTime(System.currentTimeMillis() / 1000);
                return v;
            }).collect(Collectors.toList());
            preShopProcurementRelationDao.insertBatch(shopInfo);
            log.info("---????????????????????????????????????????????????---");
            return  1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new BaseException("????????????????????????");
        }
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

    @Override
    public Integer updatePurchaseGoodsToApp(Integer id, Integer actualArrivalNum, Double actualArrivalSumprice) {
        try {
            PreShopProcurementRelation preShopProcurementRelation=new PreShopProcurementRelation();
            preShopProcurementRelation.setId(id);
            preShopProcurementRelation.setActualArrivalNum(actualArrivalNum);
            preShopProcurementRelation.setActualArrivalSumprice(actualArrivalSumprice);
            preShopProcurementRelation.setUpdateTime(System.currentTimeMillis()/1000);
            return preShopProcurementRelationDao.update(preShopProcurementRelation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }
}
