package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.ctospace.archit.common.pagination.Paging;
import com.sankuai.meituan.banma.PeisongClient;
import com.sankuai.meituan.banma.constants.CancelOrderReasonId;
import com.sankuai.meituan.banma.constants.OrderType;
import com.sankuai.meituan.banma.request.CancelOrderRequest;
import com.sankuai.meituan.banma.request.CreateOrderByShopRequest;
import com.sankuai.meituan.banma.request.PreCreateByShopRequest;
import com.sankuai.meituan.banma.request.RiderLocationRequest;
import com.sankuai.meituan.banma.response.CancelOrderResponse;
import com.sankuai.meituan.banma.response.CreateOrderResponse;
import com.sankuai.meituan.banma.response.PreCreateByShopResponse;
import com.sankuai.meituan.banma.response.RiderLocationResponse;
import com.sankuai.meituan.banma.vo.OpenApiGood;
import com.sankuai.meituan.banma.vo.OpenApiGoods;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.util.StringUtil;
import com.sankuai.meituan.waimai.opensdk.vo.*;
import dada.com.DaDaClient;
import dada.com.request.*;
import dada.com.response.*;
import info.batcloud.wxc.core.amap.AMapClient;
import info.batcloud.wxc.core.amap.response.GeoResponse;
import info.batcloud.wxc.core.bmap.BMapClient;
import info.batcloud.wxc.core.bmap.response.MapResponse;
import info.batcloud.wxc.core.config.MeituanPeisongApp;
import info.batcloud.wxc.core.constants.KafkaConstants;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.domain.LocateInfo;
import info.batcloud.wxc.core.domain.meituan.RefundFood;
import info.batcloud.wxc.core.dto.*;
import info.batcloud.wxc.core.entity.*;
import info.batcloud.wxc.core.entity.Order;
import info.batcloud.wxc.core.enums.*;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.*;
import info.batcloud.wxc.core.mapper.stat.OrderStatMapper;
import info.batcloud.wxc.core.repository.*;
import info.batcloud.wxc.core.service.*;
import jd.sdk.JingdongClient;
import jd.sdk.JingdongzClient;
import jd.sdk.request.*;
import jd.sdk.response.*;
import jdk.nashorn.internal.ir.ReturnNode;
import me.ele.sdk.up.EleClient;
import me.ele.sdk.up.request.*;
import me.ele.sdk.up.response.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import shansong.ShanSongClient;
import shansong.request.CancelRequest;
import shansong.request.GetPriceResquest;
import shansong.request.GetStatusRequest;
import shansong.request.SendOrderRequest;
import shansong.response.CancelResponse;
import shansong.response.GetPriceResponse;
import shansong.response.GetStatusResponse;
import shansong.response.SendOrderResponse;
import shunfeng.ShunfengClent;
import shunfeng.request.*;
import shunfeng.response.*;
import uupt.src.com.uupt.openapi.request.AddOrderReuqest;
import uupt.src.com.uupt.openapi.request.CancelUuRequest;
import uupt.src.com.uupt.openapi.request.GetOrderInfoRequest;
import uupt.src.com.uupt.openapi.request.GetOrderPriceRequest;
import uupt.src.com.uupt.openapi.response.AddOrderResponse;
import uupt.src.com.uupt.openapi.response.GetOrderInfoResponse;
import uupt.src.com.uupt.openapi.response.GetOrderPriceReponse;
import wante.sdk.up.WanteClient;
import wante.sdk.up.request.*;
import wante.sdk.up.response.*;

import javax.inject.Inject;
import javax.persistence.criteria.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private MeituanWaimaiService meituanWaimaiService;

    @Inject
    private WanteClient wanteClient;

    @Inject
    private ShanSongClient shanSongClient;

    @Inject
    private DaDaClient daDaClient;

    @Inject
    private RedisTemplate<String, String> redisTemplate;

    @Inject
    private ClbmWaiMaiService clbmWaiMaiService;

    @Inject
    private ShunfengClent shunfengClent;

    @Inject
    private JingdongzClient jingdongzClient;

    @Inject
    private OrderNotificationRepository notificationRepository;

    @Inject
    private JingdongClient jingdongClient;

    @Inject
    private OrderDetailService orderDetailService;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private FoodRepository foodRepository;

    @Inject
    private WarehouseRepository warehouseRepository;

    @Inject
    private FoodService foodService;

    @Inject
    private AMapClient aMapClient;

    @Inject
    private BMapClient bMapClient;

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private OrderDetailRepository orderDetailRepository;

    @Inject
    private StoreUserFoodRepository storeUserFoodRepository;

    @Inject
    private SettlementService settlementService;

    @Inject
    @Lazy
    private StoreUserFoodService storeUserFoodService;

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private PrinterService printerService;

    @Inject
    private XinYeYunService xinYeYunService;

    @Inject
    private YilainyunPrintService yilainyunPrintService;

    @Inject
    private ZhongWuPrintService zhongWuPrintService;

    @Inject
    private PeisongClient peisongClient;

    @Inject
    private StoreService storeService;

    @Inject
    private OrderService orderService;

    @Inject
    private OssService ossService;

    @Inject
    private OrderStatMapper orderStatMapper;

    @Inject
    private TmpFileService tmpFileService;

    @Inject
    private EleClient eleClient;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Inject
    private KafkaTemplate<String, String> kafkaTemplate;

    @Inject
    private FoodSupplierRepository foodSupplierRepository;

    @Inject
    private MeituanPeisongApp meituanPeisongApp;

    @Inject
    private SingleCallService singleCallService;

    @Inject
    private StoreUserFoodSkuService storeUserFoodSkuService;


    @Override
    public String pullPhoneNumberByStoreId(long storeId) {
        Store store = storeRepository.findOne(storeId);
        if (store.getPlat() == Plat.MEITUAN || store.getPlat() == Plat.CLBM) {
            SystemParam param = null;
            if (store.getPlat() == Plat.MEITUAN) {
                param = meituanWaimaiService.getSystemParam();
            } else {
                param = clbmWaiMaiService.getSystemParam();
            }
            try {
                String str = APIFactory.getOrderAPI().orderBatchPullPhoneNumber(param, store.getCode(), 0, 1000);
                logger.info(str);
            } catch (ApiOpException e) {
                logger.error(e.getMsg(), e);
            } catch (ApiSysException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;

    }

    @Override
    @Transactional
    public OrderSaveResult syncPlatOrder(Plat plat, String platOrderId, Boolean jd) {
        if (plat == Plat.MEITUAN || plat == Plat.CLBM) {
            SystemParam systemParam = null;
            if (plat == Plat.MEITUAN) {
                systemParam = meituanWaimaiService.getSystemParam();
            } else {
                systemParam = clbmWaiMaiService.getSystemParam();
            }
            com.sankuai.meituan.waimai.opensdk.vo.OrderDetailParam orderDetailParam = null;
            OrderService.OrderSaveResult rs = new OrderService.OrderSaveResult();
            try {
                //获取订单详细信息
                orderDetailParam = APIFactory.getOrderAPI().orderGetOrderDetail(systemParam,
                        Long.valueOf(platOrderId.replaceAll("\\s", "")), 0l);
                //订单存储结果
                if (plat == Plat.MEITUAN) {
                    rs = orderService.saveOrder(MeituanWaimaiService.toOrder(orderDetailParam));
                } else {
                    rs = orderService.saveOrder(ClbmWaiMaiService.toOrder(orderDetailParam));
                }

            } catch (ApiOpException e) {
                rs.setSuccess(false);
                rs.setMsg(e.getMsg());
            } catch (ApiSysException e) {
                rs.setSuccess(false);
                rs.setMsg(e.getExceptionEnum().getMsg());
            }
            if (!rs.isSuccess()) {
                return rs;
            }
            //同步order退款信息
            try {
                //获取美团订单退款记录
                List<OrderRefundDetailParam> detailParams = APIFactory.getOrderAPI().orderGetRefundDetail(systemParam, orderDetailParam.getWm_order_id_view(), null);
                //如果订单退订次数大于一次
                if (detailParams.size() == 1) {
                    //？？
                    OrderRefundDetailParam detailParam = detailParams.get(0);
                    if (detailParam.getRefund_type() == 1) {
                        //全款退款
                        OrderRefundStatus status = MeituanHelper.getRefundStatus(detailParam.getRes_type());
                        if (plat == Plat.MEITUAN) {
                            orderService.refundOrder(Plat.MEITUAN, platOrderId, status, null);
                        } else {
                            orderService.refundOrder(Plat.CLBM, platOrderId, status, null);
                        }

                        return rs;
                    }
                }
                OrderPartRefundParam param = new OrderPartRefundParam();
                OrderPartRefundParam refunding = new OrderPartRefundParam();
                OrderPartRefundParam rejectParam = new OrderPartRefundParam();
                if (plat == Plat.MEITUAN) {
                    param.setPlat(Plat.MEITUAN);
                    refunding.setPlat(Plat.MEITUAN);
                    rejectParam.setPlat(Plat.MEITUAN);
                } else {
                    param.setPlat(Plat.CLBM);
                    refunding.setPlat(Plat.CLBM);
                    rejectParam.setPlat(Plat.CLBM);
                }
                rejectParam.setPlatOrderId(platOrderId);
                param.setPlatOrderId(platOrderId);
                refunding.setPlatOrderId(platOrderId);
                param.setStatus(OrderRefundStatus.AGREE);
                refunding.setStatus(OrderRefundStatus.PENDING);
                rejectParam.setStatus(OrderRefundStatus.REJECT);
                Boolean rejectFlage = false;
                Float reFundMoney = 0F;
                //部分退单的食物集合
                List<RefundFood> refundFoods = new ArrayList<>();
                //用来统计数量的map
                Map<String, RefundFood> refundFoodMap = new HashMap<>();
                //正在退款的商品
                List<RefundFood> refundingFoods = new ArrayList<>();
                //用来统计正在退款中数量
                Map<String, RefundFood> refundingFoodMap = new HashMap<>();
                //遍历订单所有的退款记录
                for (OrderRefundDetailParam detailParam1 : detailParams) {
                    OrderRefundStatus status = MeituanHelper.getRefundStatus(detailParam1.getRes_type());
                    if (status == OrderRefundStatus.PENDING) {
                        //正在处理的退款

                        for (com.sankuai.meituan.waimai.opensdk.vo.OrderPartRefundParam rp
                                : detailParam1.getWmAppRetailForOrderPartRefundList()) {
                            //遍历食物集合，判断原来食物集合中有没有退单记录中当前遍历的食物

                            RefundFood rf = refundingFoodMap.get(rp.getSku_id());
                            if (rf == null) {
                                rf = new RefundFood();
                                rf.setAppFoodCode(rp.getApp_food_code());
                                rf.setBoxNum(rp.getBox_num().floatValue());
                                rf.setBoxPrice(rp.getBox_price().floatValue());
                                rf.setCount(getRefundFoodCount(rp));
                                rf.setFoodName(rp.getFood_name());
                                rf.setFoodPrice(rp.getFood_price().floatValue());
                                rf.setOriginFoodPrice(rp.getOrigin_food_price().floatValue());
                                rf.setRefundPrice(rp.getRefund_price().floatValue());
                                rf.setSkuId(rp.getSku_id());
                                rf.setSpec(rp.getSpec());
                                refundingFoods.add(rf);
                                refundingFoodMap.put(rf.getSkuId(), rf);
                            } else {
                                rf.setCount(rf.getCount() + getRefundFoodCount(rp));
                                rf.setRefundPrice(rf.getRefundPrice() + rp.getRefund_price().floatValue());
                            }


                        }

                    } else if (status == OrderRefundStatus.AGREE) {
                        //退款金额累加
                        reFundMoney += detailParam1.getMoney().floatValue();
                        for (com.sankuai.meituan.waimai.opensdk.vo.OrderPartRefundParam rp
                                : detailParam1.getWmAppRetailForOrderPartRefundList()) {
                            //遍历食物集合，判断原来食物集合中有没有退单记录中当前遍历的食物

                            RefundFood rf = refundFoodMap.get(rp.getSku_id());
                            if (rf == null) {
                                rf = new RefundFood();
                                rf.setAppFoodCode(rp.getApp_food_code());
                                rf.setBoxNum(rp.getBox_num().floatValue());
                                rf.setBoxPrice(rp.getBox_price().floatValue());
                                rf.setCount(getRefundFoodCount(rp));
                                rf.setFoodName(rp.getFood_name());
                                rf.setFoodPrice(rp.getFood_price().floatValue());
                                rf.setOriginFoodPrice(rp.getOrigin_food_price().floatValue());
                                rf.setRefundPrice(rp.getRefund_price().floatValue());
                                rf.setSkuId(rp.getSku_id());
                                rf.setSpec(rp.getSpec());
                                refundFoods.add(rf);
                                refundFoodMap.put(rf.getSkuId(), rf);
                            } else {
                                rf.setCount(rf.getCount() + getRefundFoodCount(rp));
                                rf.setRefundPrice(rf.getRefundPrice() + rp.getRefund_price().floatValue());
                            }


                        }
                    } else {
                        rejectFlage = true;
                    }

                }
                param.setRefundMoney(reFundMoney);
                param.setFoodList(refundFoods);
                refunding.setFoodList(refundingFoods);
                if (rejectFlage) {
                    orderService.partRefunding(rejectParam);
                }
                if (refundFoods.size() > 0) {
                    orderService.partRefund(param);
                }

                if (refundingFoods.size() > 0) {
                    orderService.partRefunding(refunding);
                }
            } catch (ApiOpException e) {
                logger.error("同步美团订单退款记录出错，订单可能不存在退款记录 " + platOrderId);
            } catch (ApiSysException e) {
                logger.error("同步美团订单退款记录出错，订单可能不存在退款记录 " + platOrderId);
            }

            return rs;
        } else if (plat == Plat.ELE) {
            //订单存储状态
            OrderService.OrderSaveResult rs = new OrderService.OrderSaveResult();
            //建立请求
            OrderGetRequest req = new OrderGetRequest();
            req.setOrderId(platOrderId);
            //接收响应（退款信息，包含订单所有信息）
            OrderGetResponse res = eleClient.request(req);
            //状态码为0表示响应成功
            if (res.getErrno() == 0) {
                rs = orderService.saveOrder(EleWaimaiService.toOrder(res.getData()));
                /**
                 * 同步退款信息
                 * */
                OrderRefundType refundType = null;
                OrderRefundStatus refundStatus = null;
                OrderGetResponse.Ext ext = res.getData().getOrder().getExt();

                if (ext.getOnlineCancelStatus() != null) {
                    refundType = OrderRefundType.ALL;
                    if (ext.getOnlineCancelStatus() == 10) {
                        refundStatus = OrderRefundStatus.PENDING;
                    } else if (ext.getOnlineCancelStatus() == 30 || ext.getOnlineCancelStatus() == 50) {
                        refundStatus = OrderRefundStatus.REJECT;
                    } else if (ext.getOnlineCancelStatus() == 40 || ext.getOnlineCancelStatus() == 60) {
                        refundStatus = OrderRefundStatus.AGREE;
                    } else {
                        refundStatus = OrderRefundStatus.CANCELED;
                    }

                    orderService.refundOrder(Plat.ELE, platOrderId, refundStatus, null);
                    return rs;
                }

                if (ext.getPartRefundStatus() != null) {
                    refundType = OrderRefundType.PART;
                    if (ext.getPartRefundStatus() == 10) {
                        refundStatus = OrderRefundStatus.PENDING;
                    } else if (ext.getPartRefundStatus() == 50) {
                        refundStatus = OrderRefundStatus.REJECT;
                    } else if (ext.getPartRefundStatus() == 20) {
                        refundStatus = OrderRefundStatus.AGREE;
                    } else {
                        refundStatus = OrderRefundStatus.CANCELED;
                    }
                }

                if (refundStatus != null) {
                    //订单有退款记录
                    if (refundStatus == OrderRefundStatus.AGREE || refundStatus == OrderRefundStatus.PENDING) {
                        OrderPartRefundGetRequest $req = new OrderPartRefundGetRequest();
                        $req.setOrderId(platOrderId);
                        OrderPartRefundGetResponse $res = eleClient.request($req);
                        if ($res.getErrno() == 0) {
                            //获取data信息
                            OrderPartRefundGetResponse.Data data = $res.getData();
                            //判断是否有退款记录
                            if (data.getRefundDetails() != null || data.getHistoryRefundDetail() != null) {

                                List<OrderPartRefundGetResponse.RefundDetail> details = new ArrayList<>();
                                if (data.getRefundDetails() != null) {
                                    List<OrderPartRefundGetResponse.RefundDetail> refundDetails = data.getRefundDetails();
                                    for (OrderPartRefundGetResponse.RefundDetail refundDetail : refundDetails) {
                                        refundDetail.setHistoryFundCalculateType(data.getFundCalculateType());
                                    }
                                    details.addAll(refundDetails);
                                }
                                if (data.getHistoryRefundDetail() != null) {
                                    List<List<OrderPartRefundGetResponse.RefundDetail>> historydetails = data.getHistoryRefundDetail();
                                    for (List<OrderPartRefundGetResponse.RefundDetail> historydetail : historydetails) {
                                        details.addAll(historydetail);
                                    }
                                }

                                //遍历退款记录，把重复的退款记录剔除掉
                                Map<String, OrderPartRefundGetResponse.RefundDetail> detailMap = new HashMap<>();
                                for (OrderPartRefundGetResponse.RefundDetail detail : details) {
                                    detailMap.put(detail.getRefundId() + detail.getSkuId(), detail);
                                }
                                OrderPartRefundParam param = new OrderPartRefundParam();
                                OrderPartRefundParam refunding = new OrderPartRefundParam();
                                refunding.setPlat(Plat.ELE);
                                refunding.setPlatOrderId(platOrderId);
                                refunding.setStatus(OrderRefundStatus.PENDING);
                                param.setPlat(Plat.ELE);
                                param.setPlatOrderId(platOrderId);
                                param.setStatus(OrderRefundStatus.AGREE);
                                //价格单位是分，除以100
                                Float refundPrice = data.getRefundPrice() / 100;
                                param.setRefundMoney(refundPrice);

                                List<RefundFood> refundFoods = new ArrayList<>();
                                Map<String, RefundFood> refundFoodMap = new HashMap<>();

                                List<RefundFood> refundingFoods = new ArrayList<>();
                                Map<String, RefundFood> refundingFoodMap = new HashMap<>();
                                //遍历每一条记录
                                for (Map.Entry<String, OrderPartRefundGetResponse.RefundDetail> entry : detailMap.entrySet()) {
                                    OrderPartRefundGetResponse.RefundDetail detail = entry.getValue();
                                    if (detail.getNumber() == 0) {
                                        detail.setNumber(1);
                                    }
                                    //判断商品的退单状态
                                    if (detail.getStatus() == 10) {
                                        refunding.setEleRefundId(detail.getRefundId());
                                        RefundFood rf = refundingFoodMap.get(detail.getSkuId());
                                        if (rf == null) {
                                            String foodUpc = detail.getUpc();
                                            rf = new RefundFood();
                                            rf.setAppFoodCode(EleHelper.getFoodUpc(foodUpc));
                                            rf.setBoxNum(0f);
                                            rf.setBoxPrice(0f);
                                            rf.setCount(detail.getNumber() + 0.0);
                                            rf.setFoodName(detail.getName());
                                            float foodPrice = (detail.getTotalRefund() / detail.getNumber()) / 100;
                                            rf.setFoodPrice(foodPrice);
                                            rf.setOriginFoodPrice(foodPrice);
                                            rf.setRefundPrice((detail.getTotalRefund() + detail.getShopEleRefund()) / 100);
                                            rf.setSkuId(detail.getSkuId());
                                            refundingFoods.add(rf);
                                            refundingFoodMap.put(rf.getSkuId(), rf);
                                        } else {
                                            rf.setCount(rf.getCount() + detail.getNumber());
                                            rf.setRefundPrice(rf.getRefundPrice() + (detail.getTotalRefund() + detail.getShopEleRefund()) / 100);
                                        }
                                        rf.setType(detail.getHistoryFundCalculateType());

                                    } else if (detail.getStatus() == 20) {
                                        RefundFood rf = refundFoodMap.get(detail.getSkuId());
                                        if (rf == null) {
                                            String foodUpc = detail.getUpc();
                                            rf = new RefundFood();
                                            rf.setAppFoodCode(EleHelper.getFoodUpc(foodUpc));
                                            rf.setBoxNum(0f);
                                            rf.setBoxPrice(0f);
                                            rf.setCount(detail.getNumber() + 0.0);
                                            rf.setFoodName(detail.getName());
                                            float foodPrice = (detail.getTotalRefund() / detail.getNumber()) / 100;
                                            rf.setFoodPrice(foodPrice);
                                            rf.setOriginFoodPrice(foodPrice);
                                            rf.setRefundPrice((detail.getTotalRefund() + detail.getShopEleRefund()) / 100);
                                            rf.setSkuId(detail.getSkuId());
                                            refundFoods.add(rf);
                                            refundFoodMap.put(rf.getSkuId(), rf);
                                        } else {
                                            rf.setCount(rf.getCount() + detail.getNumber());
                                            rf.setRefundPrice(rf.getRefundPrice() + (detail.getTotalRefund() + detail.getShopEleRefund()) / 100);
                                        }
                                        rf.setType(detail.getHistoryFundCalculateType());
                                    }
                                }
                                param.setFoodList(refundFoods);
                                refunding.setFoodList(refundingFoods);


                                if (refundFoods.size() > 0) {
                                    orderService.partRefund(param);
                                }

                                if (refundingFoods.size() > 0) {
                                    orderService.partRefunding(refunding);
                                }

                            }


                        }
                    } else {
                        OrderPartRefundParam param = new OrderPartRefundParam();
                        param.setPlat(Plat.ELE);
                        param.setStatus(OrderRefundStatus.REJECT);
                        param.setPlatOrderId(platOrderId);
                        orderService.partRefunding(param);
                    }

                }


            } else {
                rs.setSuccess(false);
                rs.setMsg(res.getError());
            }
            return rs;
        } else if (plat == Plat.WANTE) {
            OrderService.OrderSaveResult rs = new OrderService.OrderSaveResult();
            OrderGetReq orderGetReq = new OrderGetReq();
            orderGetReq.setOrderId(Integer.valueOf(platOrderId));
            OrderGetRes execute = wanteClient.execute(orderGetReq);
            if (execute != null) {
                Store store = storeRepository.findByCodeAndPlat(String.valueOf(execute.getStoreId()), Plat.WANTE);
                OrderParam param = WanteWaimaiService.toOrder(execute, store);
                param.setDetailParamList(new ArrayList<>());
                List<OrderGetRes.Good> goods = execute.getGoods();
                for (OrderGetRes.Good good : goods) {
                    OrderService.OrderDetailParam p = new OrderService.OrderDetailParam();
                    StoreUserFood storeUserFood = storeUserFoodRepository.findByWanteId(good.getGoodsId().longValue());
                    p.setCode(storeUserFood.getFood().getCode());
                    Map<Integer, String> wanteSkuMap = storeUserFood.getWanteSkuMap();
                    p.setFoodName(storeUserFood.getFood().getName() + good.getSpecs().get(0).getValue());
                    p.setUnit("");
                    p.setSkuId(wanteSkuMap.get(good.getSpecs().get(0).getIdSpec()));
                    p.setPrice(good.getPrice());
                    p.setQuantity(good.getNumber().floatValue());//商品数量
                    p.setBoxPrice(0f);//包装费,客户端没有
                    p.setBoxNum(0f);//包装数量，客户端没有
                    param.getDetailParamList().add(p);
                }
                rs = orderService.saveOrder(param);
                //同步退款信息
                if (execute.getRefund() != null && execute.getRefund().size() > 0) {
                    OrderPartRefundParam rparam = new OrderPartRefundParam();
                    rparam.setPlat(Plat.WANTE);
                    rparam.setPlatOrderId(platOrderId);
                    Float refundPrice = 0f;
                    //rparam.setRefundMoney(refundPrice);
                    List<RefundFood> refundFoods = new ArrayList<>();
                    Map<String, RefundFood> refundFoodMap = new HashMap<>();
                    List<OrderGetRes.Refund> refunds = execute.getRefund();
                    for (OrderGetRes.Refund refund : refunds) {
                        //先处理待确认退款
                        if ("TO_EXAMINE".equals(refund.getStatus())) {
                            orderService.refundOrder(Plat.WANTE, platOrderId, OrderRefundStatus.PENDING, null);
                            rparam.setStatus(OrderRefundStatus.PENDING);
                            break;
                        }
                        if (!"SUCCESS".equals(refund.getStatus())) {
                            continue;
                        }
                        rparam.setStatus(OrderRefundStatus.AGREE);
                        refundPrice = refundPrice + refund.getMoney();
                        if (refundPrice == execute.getPurchasePrice().floatValue()) {
                            //退款金额等于付款金额是全部退款
                            orderService.refundOrder(Plat.WANTE, platOrderId, OrderRefundStatus.AGREE, null);
                            return rs;
                        }
                        String remarks = refund.getRemarks();//规定备注退款信息格式为sku：数量:该商品退款总金额,sku:数量：改商品退款总金额(逗号为英文逗号)
                        try {
                            if (remarks != null && remarks.length() > 0) {
                                String[] rinfo = remarks.split(",");
                                for (String s : rinfo) {
                                    String[] split = s.split(":");
                                    if (split.length > 1) {
                                        String sku = split[0];
                                        Integer count = Integer.valueOf(split[1]);
                                        Float refundMoney = Float.valueOf(split[2]);
                                        RefundFood rf = refundFoodMap.get(sku);
                                        if (rf == null) {
                                            rf = new RefundFood();
                                            rf.setAppFoodCode(sku.split("-")[0]);
                                            rf.setBoxNum(0f);
                                            rf.setBoxPrice(0f);
                                            rf.setCount(count.doubleValue());
                                            rf.setSkuId(sku);
                                            refundFoods.add(rf);
                                            rf.setRefundPrice(refundMoney);
                                            refundFoodMap.put(rf.getSkuId(), rf);
                                        } else {
                                            rf.setCount(rf.getCount() + count);
                                            rf.setRefundPrice(rf.getRefundPrice() + refundMoney);
                                        }
                                    }
                                }
                            }
                        } catch (NumberFormatException e) {
                            logger.error(e.getMessage());
                        }
                    }
                    rparam.setRefundMoney(refundPrice);
                    rparam.setFoodList(refundFoods);
                    orderService.partRefund(rparam);
                }
            } else {
                rs.setSuccess(false);
                rs.setMsg("客户端查询订单失败: " + platOrderId);
            }
            return rs;
        } else if (plat == Plat.JDDJ) {
            OrderSaveResult rs = new OrderSaveResult();
            try {
                OrderQueryReq queryReq = new OrderQueryReq();
                queryReq.setOrderId(Long.valueOf(platOrderId));
                OrderQueryRes queryRes = null;
                if (jd) {
                    queryRes = jingdongClient.request(queryReq);
                } else {
                    queryRes = jingdongzClient.request(queryReq);
                }

                if (!queryRes.getCode().equals("0") || !queryRes.getData().getCode().equals("0")) {
                    rs.setSuccess(false);
                    rs.setMsg(queryRes.getMsg());
                    return rs;
                }
                OrderQueryRes.Result result = queryRes.getData().getResult();
                OrderParam orderParam = JddjWaimaiService.toOrder(result);
                List<OrderQueryRes.OrderProductDTO> product = result.getResultList().get(0).getProduct();
                List<OrderDetailParam> detailParams = new ArrayList<>();
                try {
                    for (OrderQueryRes.OrderProductDTO dto : product) {
                        OrderDetailParam p = new OrderDetailParam();
                        p.setFoodName(dto.getSkuName());
                        p.setQuantity(Float.valueOf(dto.getSkuCount()));
                        String skuIdIsv = dto.getSkuIdIsv();
                        p.setSkuId(this.getJddjFoodSku(skuIdIsv));
                        p.setPrice(Float.valueOf(dto.getSkuJdPrice()) / 100);
                        p.setBoxNum(Float.valueOf(dto.getSkuCount()));
                        p.setBoxPrice(Float.valueOf(dto.getCanteenMoney() / dto.getSkuCount()));
                        p.setUnit("");
                        p.setSpec("");
                        p.setCode(skuIdIsv.substring(0, skuIdIsv.length() - 2));
                        detailParams.add(p);
                    }
                } catch (Exception e) {
                    logger.error("同步京东订单商品出错" + e.getMessage());
                }
                orderParam.setDetailParamList(detailParams);
                rs = orderService.saveOrder(orderParam);
                //同步退款信息
                GetAfsSeriveOrderListReq getRefundReq = new GetAfsSeriveOrderListReq();
                getRefundReq.setOrderId(platOrderId);
                GetAfsSeriveOrderListRes res = null;
                if (jd) {
                    res = jingdongClient.request(getRefundReq);
                } else {
                    res = jingdongzClient.request(getRefundReq);
                }
                if (res.getCode().equals("0") && res.getData().getTotalCount() != null && res.getData().getTotalCount() > 0) {
                    List<GetAfsSeriveOrderListRes.AfsSeriveOrder> list = res.getData().getAfsSeriveOrderList();
                    OrderPartRefundParam param = new OrderPartRefundParam();
                    param.setPlat(Plat.JDDJ);
                    param.setPlatOrderId(platOrderId);
                    param.setStatus(OrderRefundStatus.AGREE);
                    Float reFundMoney = 0F;
                    //部分退单的食物集合
                    List<RefundFood> refundFoods = new ArrayList<>();
                    //用来统计数量的map
                    Map<String, RefundFood> refundFoodMap = new HashMap<>();
                    //遍历订单所有的退款记录
                    for (GetAfsSeriveOrderListRes.AfsSeriveOrder seriveOrder : list) {
                        if (seriveOrder.getAfsServiceState() == 32 || seriveOrder.getAfsServiceState() == 114) {
                            GetAfsServiceReq req = new GetAfsServiceReq();
                            req.setAfsServiceOrder(seriveOrder.getAfsServiceOrder());
                            GetAfsServiceRes serviceRes;
                            if (jd) {
                                serviceRes = jingdongClient.request(req);
                            } else {
                                serviceRes = jingdongzClient.request(req);
                            }
                            if (serviceRes.getCode().equals("0") && serviceRes.getData().getCode() == 0) {
                                GetAfsServiceRes.AfsServiceResponse refudnDetil = serviceRes.getData().getResult();
                                reFundMoney += (refudnDetil.getCashMoney() / 100);
                                List<GetAfsServiceRes.AfsServiceDetail> foods = refudnDetil.getAfsDetailList();
                                for (GetAfsServiceRes.AfsServiceDetail food : foods) {
                                    RefundFood rf = refundFoodMap.get(food.getSkuIdIsv());
                                    if (rf == null) {
                                        rf = new RefundFood();
                                        String skuIdIsv = food.getSkuIdIsv();
                                        rf.setAppFoodCode(skuIdIsv.substring(0, skuIdIsv.length() - 2));
                                        rf.setBoxNum(Float.valueOf(food.getSkuCount()));
                                        rf.setBoxPrice((Float.valueOf(food.getMealBoxMoney()) / 100) / Float.valueOf(food.getSkuCount()));
                                        rf.setCount(Double.valueOf(food.getSkuCount()));
                                        rf.setFoodName(food.getWareName());
                                        rf.setFoodPrice((Float.valueOf(food.getCashMoney()) / 100) / Float.valueOf(food.getSkuCount()));
                                        rf.setOriginFoodPrice(Float.valueOf(food.getPayPrice()) / 100);
                                        rf.setRefundPrice(Float.valueOf(food.getCashMoney()) / 100);
                                        rf.setSkuId(this.getJddjFoodSku(food.getSkuIdIsv()));
                                        refundFoods.add(rf);
                                        refundFoodMap.put(rf.getSkuId(), rf);
                                    } else {
                                        rf.setCount(rf.getCount() + food.getSkuCount());
                                        rf.setRefundPrice(rf.getRefundPrice() + food.getCashMoney() / 100);
                                    }
                                }

                            }
                        }
                    }
                    param.setRefundMoney(reFundMoney);
                    param.setFoodList(refundFoods);
                    if (refundFoods.size() > 0) {
                        orderService.partRefund(param);
                    }
                }
                return rs;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                rs.setSuccess(false);
                return rs;
            }

        } else {
            throw new BizException("暂未支持");
        }
    }

    @Override
    public String getJddjFoodSku(String skuIdIsv) {
        Food food = foodRepository.findByCode(skuIdIsv.substring(0, skuIdIsv.length() - 2));
        Map<String, String> jddjSkuMap = food.getJddjSkuMap();
        return jddjSkuMap.get(skuIdIsv);
    }

    private Double getRefundFoodCount(com.sankuai.meituan.waimai.opensdk.vo.OrderPartRefundParam rp) {
        if (rp.getCount() != 0) {
            return rp.getCount() + 0.0;
        } else {
            Double refunded_weight = rp.getRefunded_weight();
            Food food = foodRepository.findByCode(rp.getApp_food_code());
            FoodDTO foodDTO = foodService.toDTO(food);
            List<FoodSkuDTO> skuList = foodDTO.getSkus();
            Integer weight = null;
            for (FoodSkuDTO foodSku : skuList) {
                if (foodSku.getId().toString().contains(rp.getSku_id())) {
                    weight = foodSku.getWeight();
                    continue;
                }
            }
            double v = refunded_weight / weight;
            return v;
        }
    }

    @Override
    public void setPlatOrderStatus(Plat plat, String platOrderId, OrderStatus status) {
        Order order = orderRepository.findByPlatAndPlatOrderId(plat, platOrderId);
        if (order == null) {
            OrderSaveResult rs = this.syncPlatOrder(plat, platOrderId, true);
            if (rs.isSuccess()) {
                order = orderRepository.findByPlatAndPlatOrderId(plat, platOrderId);
            } else {
                throw new BizException("同步平台订单出错" + rs.getErrMsg());
            }
        }
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public OrderSaveResult saveOrder(OrderParam param) {
        Assert.isTrue(param.getDetailParamList().size() > 0, "明细不存在");
        OrderSaveResult rs = new OrderSaveResult();
        Store store = storeRepository.findByCodeAndPlat(param.getAppPoiCode(), param.getPlat());
        Order order = orderRepository.findByPlatAndPlatOrderId(param.getPlat(), param.getPlatOrderId());
        boolean newFlag = false;
        if (order == null) {
            order = new Order();
            newFlag = true;
            order.setBizStatus(OrderBizStatus.WAIT_CHECK);
            order.setCostTotal(0f);
            order.setCostRefund(0f);

            //根据订单时间判断订单的配送类型
            if (store.getDeliveryType() == DeliveryType.UU_OPEN) {
                DeliveryType type = DeliveryType.UU_OPEN;
                try {
                    type = this.getOrderDeliveryType(store, param.getExpectedDeliveryTime());
                } catch (Exception e) {
                    logger.error("获取订单配送类型出错" + e.getMessage());
                }
                order.setDeliveryType(type);
            } else {
                order.setDeliveryType(store.getDeliveryType());
            }

            if (store.getDeliveryType() == DeliveryType.UNDETERMINED || store.getDeliveryType() == DeliveryType.MEITUAN_OPEN || store.getDeliveryType() == DeliveryType.SHUFENG_OPEN || store.getDeliveryType() == DeliveryType.UU_OPEN || store.getDeliveryType() == DeliveryType.SS_OPEN || store.getDeliveryType() == DeliveryType.ZHONGBAO || store.getDeliveryType() == DeliveryType.SELF || store.getDeliveryType() == DeliveryType.DD_OPEN) {
                order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
            }
            order.setPickType(param.getPickType());
            order.setExpectedDeliveryTime(param.getExpectedDeliveryTime());
            order.setLat(param.getLat());
            order.setLng(param.getLng());
        } else {
            if (order.getBizStatus() != OrderBizStatus.WAIT_CHECK && order.getBizStatus() != OrderBizStatus.WAIT_SETTLE) {
                rs.setMsg("订单：" + order.getId() + "已经结算，无法再次同步");
            }
            param.setShippingFee(order.getShippingFee());
        }
        BeanUtils.copyProperties(param, order);
        order.setStore(store);
        if (store.getStoreUser() == null) {
            rs.setMsg("门店：" + store.getName() + "未绑定门店账号，无法同步订单！");
            return rs;
        }
        Map<String, List<OrderDetail>> orderDetailMap = new HashMap<>();
        if (order.getId() != null) {
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(order.getId());
            for (OrderDetail orderDetail : orderDetailList) {
                String key = orderDetail.getFoodCode() + "#" + orderDetail.getSkuId();
                List<OrderDetail> list = orderDetailMap.get(key);
                if (list == null) {
                    list = new ArrayList<>();
                    orderDetailMap.put(key, list);
                }
                list.add(orderDetail);
            }
        }
        List<OrderDetail> details = new ArrayList<>();
        for (OrderDetailParam dp : param.getDetailParamList()) {
            String key = dp.getCode() + "#" + dp.getSkuId();
            List<OrderDetail> exists = orderDetailMap.get(key);
            OrderDetail detail;
            if (exists == null || exists.size() == 0) {
                detail = new OrderDetail();
            } else {
                detail = exists.remove(0);
            }
            detail.setPrice(dp.getPrice());
            detail.setBoxNum(dp.getBoxNum());
            detail.setBoxPrice(dp.getBoxPrice());
            detail.setFoodName(dp.getFoodName());
            detail.setOrder(order);
            detail.setQuantity(dp.getQuantity());
            detail.setSkuId(dp.getSkuId());
            detail.setSpec(dp.getSpec());
            detail.setUnit(dp.getUnit());
            detail.setFoodCode(dp.getCode());
            detail.setRefundQuantity(0f);
            detail.setQuoteUnitRatio(0f);
            detail.setRefunding(false);
            details.add(detail);
        }
        for (List<OrderDetail> detailList : orderDetailMap.values()) {
            if (detailList.size() == 0) {
                continue;
            }
            /**
             * 删除其余的detail,因为有的商品没有code， 再重新设置了code之后会出现重复的情况
             * */
            orderDetailRepository.delete(detailList);
        }

        order.setExtraList(param.getExtraList());
        orderRepository.save(order);
        orderDetailRepository.save(details);
        this.checkOrder(order.getId());
        if (newFlag) {
            if (order.getPlat() != Plat.WANTE) {
                kafkaTemplate.send(KafkaConstants.TOPIC_ORDER_NEW, JSON.toJSONString(order));
            } else {
                //客户端订单默认都是确认接单
                this.confirmOrderByPlat(Plat.WANTE, order.getPlatOrderId());
            }

        }
        rs.setSuccess(true);
        rs.setOrderId(order.getId());
        rs.setOrderStatus(order.getStatus());
        rs.setStoreUserId(order.getStore().getStoreUser().getId());
        return rs;
    }

    private DeliveryType getOrderDeliveryType(Store store, Long time) {
        logger.info("开始判断订单配送方式");
        if (store.getUuMix()) {
            Float changeUuTime = store.getChangeUuTime();
            if (changeUuTime == null) {
                return store.getDeliveryType();
            }

            if (time == 0) {
                //即时单加上五十分钟是送达时间
                Date date = new Date();
                time = date.getTime() + 3000000;
            } else {
                time = time * 1000;
            }


            Date date = new Date(time);
            Date truncate = DateUtils.truncate(date, Calendar.DATE);
            long initTime = truncate.getTime();
            long exTime = time - initTime;
            //判断送达时间段
            if (exTime > store.getChangeUuTime() * 60 * 60000 && exTime < initTime + 86400000) {
                logger.info("配送方式为UU");
                return DeliveryType.UU_OPEN;
            } else {
                logger.info("配送方式为海葵");
                return DeliveryType.MEITUAN_OPEN;
            }

        } else {
            //没有开通混合送
            return store.getDeliveryType();
        }

    }

    @Override
    public void cancelOrderByPlat(Plat plat, String platOrderId, int cancelCode, String cancelReason) {
        Order order = orderRepository.findByPlatAndPlatOrderId(plat, platOrderId);
        order.setStatus(OrderStatus.CANCELED);
        order.setCancelReason(CautionHelper.getCaution(cancelReason));
        order.setCancelReasonCode(cancelCode);
        orderRepository.save(order);
        kafkaTemplate.send(KafkaConstants.TOPIC_ORDER_CANCEL, JSON.toJSONString(order));
        try {
            if (cancelCode == 1001) {
                String info = order.getWmPoiName();
                switch (plat) {
                    case CLBM:
                        info = info + "菜老板美团";
                        break;
                    case ELE:
                        info = info + "王小菜饿了么";
                        break;
                    case MEITUAN:
                        info = info + "王小菜美团";
                        break;
                    case JDDJ:
                        info = info + "王小菜京东";
                        break;

                }
                info = info + order.getDaySeq();
                singleCallService.sendCustomerCall(info, order.getId());
            }
        } catch (Exception e) {
            logger.error("发送客服处理电话出错：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean sendDeliveryByOrderId(long orderId) {
        Order order = orderRepository.findOne(orderId);
        if (order.getPickType() == PickType.ZQ) {
            logger.info("订单为自取订单，不发起配送" + order.getPlatOrderId());
            return false;
        }
        if (order.getExpectedDeliveryTime() > 0) {
            long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
            boolean lessThen1h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 < 3600;
            if (!lessThen1h) {
                String info = "订单距离期望送达时间超过一小时，暂时不发起配送" + order.getId();
                logger.info(info);
                throw new BizException(info);
            }
        }
        Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
        //京东订单需要等五分钟之后再发单
        if (order.getPlat() == Plat.JDDJ && store.getJd()) {
            if (order.getDadaAccept()) {
                logger.error("订单已被达达接单或者商家未接单");
                return false;
            }
            try {
                OrderQueryReq queryReq = new OrderQueryReq();
                queryReq.setOrderId(Long.valueOf(order.getPlatOrderId()));
                OrderQueryRes queryRes = jingdongClient.request(queryReq);
                if (queryRes.getCode().equals("0")) {
                    OrderQueryRes.OrderInfo orderInfo = queryRes.getData().getResult().getResultList().get(0);
                    Date inputTime = orderInfo.getDeliverInputTime();
                    if (System.currentTimeMillis() - inputTime.getTime() <= 1100000) {
                        return false;
                    } else {
                        ModifySellerDeliveryReq req = new ModifySellerDeliveryReq();
                        req.setOrderId(order.getPlatOrderId());
                        req.setUpdatePin("王小菜");
                        ModifySellerDeliveryRes res = jingdongClient.request(req);
                    }


                } else {
                    return false;
                }
            } catch (Exception e) {
                logger.error("发起京东订单出错");
                logger.error(e.getMessage(), e);
                return false;
            }
        }
        switch (order.getDeliveryType()) {
            case MEITUAN_OPEN:
                return this.sendOrderMeituanPeisong(order);
            case SHUFENG_OPEN:
                return this.sendOrderShunFengPeisong(order);
            case UU_OPEN:
                return this.sendOrderUUPeisong(order);
            case SS_OPEN:
                return this.sendOrderSSPeisong(order);
            case ZHONGBAO:
                return this.sendZbPeisong(order);
            case DD_OPEN:
                return this.sendOrderDDPeisong(order);
            case UNDETERMINED:
                return this.sendBlendPeisong(order);
        }
        return false;
    }

    private boolean sendBlendPeisong(Order order) {
        if (order.getExpectedDeliveryTime() > 0) {
            Date expectedDeliveryTime = new Date(order.getExpectedDeliveryTime() * 1000);
            if (!DateUtils.isSameDay(new Date(), expectedDeliveryTime)) {
                /**
                 * 如果不是当天的预定订单，不发起配送，隔天发起配送
                 * */
                String info = "非当天的预定单，次日发起配送，orderId: " + order.getId() + ", 配送时间：" + DateFormatUtils.format(expectedDeliveryTime, "yyyy-MM-dd HH:mm:ss");
                logger.info(info);
                throw new BizException(info);
            }
        }
        StoreUser su = order.getStore().getStoreUser();
        UuAccountType uuAccountType = UuAccountType.TOTAL;
        if (order.getStore().getDeliveryType() != null) {
            uuAccountType = order.getStore().getUuAcountType();
        }
        SystemParam systemParam;
        if (order.getPlat() == Plat.MEITUAN) {
            systemParam = meituanWaimaiService.getSystemParam();
        } else {
            systemParam = clbmWaiMaiService.getSystemParam();
        }
        Double ddp = 100.0;
        boolean dd = false;
        String ddOrderId = "";
        CityCodeRequest codeRequest = new CityCodeRequest();
        CityCodeResponse codeResponse = daDaClient.request(codeRequest);
        String cityCode = "";
        List<CityCodeResponse.Result> clist = codeResponse.getResult();
        for (CityCodeResponse.Result result : clist) {
            if (su.getCity().getName().contains(result.getCityName())) {
                cityCode = result.getCityCode();
                break;
            }
        }

        Double sfp = 100.0;
        boolean sf = false;


        Double uup = 100.0;
        boolean uu = false;
        String uuPtoken = "";
        String uuTmoney = "";
        String uuNmoney = "";

        Double ssp = 100.0;
        boolean ss = false;
        String ssOrderNum = "";
        boolean sst = false;
        Double hkp = 100.0;
        boolean hk = false;
        Double ptp = 100.0;
        boolean pt = false;
        //首先判断订单哪些配送还没有发起，把没有发起过的配送都获取到对应的价格
        if (StringUtils.isBlank(order.getSfPeisongId()) && StringUtils.isNotBlank(su.getSfpsShopId())) {
            GetSfPriceReq req = new GetSfPriceReq();
            req.setReturnFlag(64);
            req.setWeight(1500);
            req.setUserLng(order.getLng().toString());
            req.setUserlat(order.getLat().toString());
            req.setUserAddress(order.getRecipientAddress());
            req.setShopId(su.getSfpsShopId());
            req.setProductType(6);
            GetSfPriceRes res = shunfengClent.execute(req);
            if (res.getErrorCode() == 0) {
                GetSfPriceRes.Result result = res.getResult();
                sfp = result.getRealPaymoney() / 100.0;
                sf = true;
            }
        }

        if (StringUtils.isBlank(order.getDdPeisongId())) {
            dada.com.request.GetOrderPriceRequest request = new dada.com.request.GetOrderPriceRequest();
            //AddOrderRequest request = new AddOrderRequest();
            request.setShopNo(su.getId().toString());
            request.setOriginId(order.getId().toString());
            request.setCityCode(cityCode);
            request.setCargoPrice(Double.valueOf(order.getTotal().intValue()));
            request.setIsPrepay(0);
            request.setReceiverName(order.getRecipientName());
            request.setReceiverAddress(order.getRecipientAddress());
            request.setReceiverLat(order.getLat());
            request.setReceiverLng(order.getLng());
            request.setCallback("https://api.wangxiaocai.cn/dd-peisong/order/status");
            request.setCargoWeight(1.5);
            request.setReceiverPhone(order.getRecipientPhone());
            request.setInfo(order.getCaution());
            request.setOriginMarkno(order.getPlat().getTitle() + "#" + order.getDaySeq());
            GetOrderPriceResponse response = daDaClient.request(request);
            logger.info(order.getId() + "聚合配送获取达达价格返回" + response.toString());
            if (response.getCode() == 0) {
                dd = true;
                ddp = response.getResult().getFee();
                ddOrderId = response.getResult().getDeliveryNo();
            } else {
                logger.error(order.getId() + "聚合配送获取达达价格出错" + response.toString());
            }

        }
        if (StringUtils.isBlank(order.getUuPeisongId())) {
            //获取到的uu价格
            GetOrderPriceRequest priceRequest = new GetOrderPriceRequest();
            priceRequest.setOrigin_id(order.getId().toString());
            LocateInfo locateInfo = new LocateInfo();
            if (StringUtils.isEmpty(su.getLat()) || StringUtils.isEmpty(su.getLng())) {
                String info = "UU跑腿需要商家经纬度，请添加，orderId: " + order.getId();
                logger.info(order.getPlatOrderId() + "发起uu聚合配送失败" + info);
            } else {
                LocateInfo storeLocal = this.getBaiDuMap(su.getLng(), su.getLat());
                priceRequest.setFrom_lng(String.valueOf(storeLocal.getLongitude()));
                priceRequest.setFrom_lat(String.valueOf(storeLocal.getLatitude()));
                logger.info("获取订单UU价格" + order.getId());
                LocateInfo userLocation = new LocateInfo();
                userLocation = this.getBaiDuMap(String.valueOf(order.getLng()), String.valueOf(order.getLat()));
                priceRequest.setTo_lng(String.valueOf(userLocation.getLongitude()));
                priceRequest.setTo_lat(String.valueOf(userLocation.getLatitude()));
                priceRequest.setTo_address(order.getRecipientAddress());
                priceRequest.setFrom_address(su.getAddress());
                priceRequest.setCity_name(su.getCity().getName());
                priceRequest.setSend_type("0");
                long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
                //小于一个小时 发即时单  大于一个小时  设置取件时间为送达时间的前一个小时
                if (expectedDeliveryTimeVal > 0) {
                    boolean lessThen1h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 < 3600;
                    if (lessThen1h) {
                        priceRequest.setSubscribe_type("0");
                        priceRequest.setSubscribe_time("");
                    } else {
                        priceRequest.setSubscribe_type("1");
                        Date date = new Date((expectedDeliveryTimeVal - 3600) * 1000);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        priceRequest.setSubscribe_time(sdf.format(date));
                    }
                } else {
                    priceRequest.setSubscribe_type("0");
                    priceRequest.setSubscribe_time("");
                }
                logger.info(priceRequest.toString());
                GetOrderPriceReponse priceReponse = priceRequest.excute(uuAccountType);
                if (!"ok".equals(priceReponse.getReturn_code())) {
                    String info = "获取聚合配送uu价格出错，orderId: " + order.getId() + "  " + priceReponse.getReturn_msg();
                    logger.info(info);
                } else {
                    uu = true;
                    uup = Double.valueOf(priceReponse.getNeed_paymoney());
                    uuPtoken = priceReponse.getPrice_token();
                    uuTmoney = priceReponse.getTotal_money();
                    uuNmoney = priceReponse.getNeed_paymoney();
                }
            }

        }
        if (StringUtils.isBlank(order.getSsPeisongId())) {
            //获取到的闪送价格
            GetPriceResquest priceResquest = new GetPriceResquest();
            LocateInfo locateInfo = new LocateInfo();
            if (StringUtils.isEmpty(su.getLat()) || StringUtils.isEmpty(su.getLng())) {
                String info = "闪送需要商家经纬度，请添加，orderId: " + order.getId();
                logger.info(info);
            } else {

            }
            logger.info("获取聚合闪送价格，" + order.getId());
            long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
            //小于一个小时 发即时单  大于一个小时 并且小于两个三时的 不发单  大于三个小时的 发预订单  设置取件时间为送达时间的前一个小时
            if (expectedDeliveryTimeVal > 0) {
                boolean lessThen1h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 < 3600;
                boolean moreThan3h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 > 10800;
                if (lessThen1h) {
                    priceResquest.setAppointType(0);
                    sst = true;
                } else {
                    if (moreThan3h) {
                        //大于3个小时
                        priceResquest.setAppointType(1);
                        Date date = new Date((expectedDeliveryTimeVal - 3600) * 1000);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        priceResquest.setAppointmentDate(sdf.format(date));
                        sst = true;
                    } else {
                        logger.error("聚合配送闪送发单时间不支持");
                    }

                }
            } else {
                priceResquest.setAppointType(0);
                sst = true;
            }
            if (sst) {
                LocateInfo storeLocal = this.getBaiDuMap(su.getLng(), su.getLat());
                GetPriceResquest.Sender sender = new GetPriceResquest.Sender();
                sender.setFromLongitude(String.valueOf(storeLocal.getLongitude()));
                sender.setFromLatitude(String.valueOf(storeLocal.getLatitude()));
                sender.setFromSenderName(su.getName());
                sender.setFromMobile(su.getPhone().replace("_", "#").replace(" ", "").replace(",", "#"));
                sender.setFromAddress(su.getAddress());
                priceResquest.setSender(sender);
                priceResquest.setStoreName(su.getName());
                List<GetPriceResquest.Receiver> receiverList = new ArrayList<>();
                GetPriceResquest.Receiver receiver = new GetPriceResquest.Receiver();
                receiver.setWeight("2");
                receiver.setToReceiverName(order.getRecipientName());
                receiver.setToMobile(order.getRecipientPhone().replace("_", "#").replace(" ", "").replace(",", "#"));
                LocateInfo userLocation = new LocateInfo();
                userLocation = this.getBaiDuMap(String.valueOf(order.getLng()), String.valueOf(order.getLat()));
                receiver.setToLongitude(String.valueOf(userLocation.getLongitude()));
                receiver.setToLatitude(String.valueOf(userLocation.getLatitude()));
                receiver.setToAddress(order.getRecipientAddress());
                String note = "";
                if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
                    receiver.setOrderingSourceType("4");
                    if (order.getPlat() == Plat.MEITUAN) {
                        note = note + "王小菜美团订单";
                    } else {
                        note = note + "菜老板美团订单";
                    }
                } else if (order.getPlat() == Plat.ELE) {
                    receiver.setOrderingSourceType("3");
                } else {
                    receiver.setOrderingSourceType("6");
                }
                receiver.setOrderingSourceNo(String.valueOf(order.getDaySeq()));
                receiver.setRemarks(note + "  " + order.getCaution());
                receiver.setOrderNo(order.getId().toString());
                receiver.setGoodType("10");
                receiverList.add(receiver);
                priceResquest.setReceiverList(receiverList);
                priceResquest.setCityName(su.getCity().getName());
                logger.info(priceResquest.toString());
                GetPriceResponse priceResponse = shanSongClient.request(priceResquest);
                if (priceResponse.getStatus() != 200) {
                    String info = "获取闪送价格出错，orderId: " + order.getId() + "  " + priceResponse.getMsg();
                    logger.info(info);
                    //throw new BizException(info);
                } else {
                    ss = true;
                    ssp = priceResponse.getData().getTotalFeeAfterSave() / 100.0;
                    ssOrderNum = priceResponse.getData().getOrderNumber();
                }
            }

        }
        if (StringUtils.isBlank(order.getMtPeisongId())) {
            //获取到的海葵价格
            PreCreateByShopRequest preCreateByShopRequest = new PreCreateByShopRequest();
            preCreateByShopRequest.setDeliveryId(order.getId());
            preCreateByShopRequest.setOrderId(order.getId().toString());
            if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
                preCreateByShopRequest.setOuterOrderSourceDesc("101");
                preCreateByShopRequest.setOuterOrderSourceNo(order.getPlatOrderId());
            } else {
                preCreateByShopRequest.setOuterOrderSourceDesc("其他");
            }
            preCreateByShopRequest.setShopId(order.getStore().getStoreUser().getMtpsShopId());
            preCreateByShopRequest.setDeliveryServiceCode(meituanPeisongApp.getDeliveryServiceCode());
            preCreateByShopRequest.setReceiverName(order.getRecipientName());
            preCreateByShopRequest.setReceiverAddress(order.getRecipientAddress());
            preCreateByShopRequest.setReceiverPhone(order.getRecipientPhone());
            preCreateByShopRequest.setReceiverLng(Double.valueOf((order.getLng() * Math.pow(10, 6))).intValue());
            preCreateByShopRequest.setReceiverLat(Double.valueOf((order.getLat() * Math.pow(10, 6))).intValue());
            preCreateByShopRequest.setPayTypeCode(0);
            preCreateByShopRequest.setGoodsValue(new BigDecimal(order.getTotal()));
            int i = (int) (10 + Math.random() * (50 - 10 + 1));
            double ami = i / 10.0;
            preCreateByShopRequest.setGoodsWeight(new BigDecimal(ami));
            long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
            OrderType orderType;
            if (expectedDeliveryTimeVal > 0) {
                boolean lessThen1h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 < 3600;
                if (lessThen1h) {
                    orderType = OrderType.NORMAL;
                    expectedDeliveryTimeVal = 0L;
                } else {
                    orderType = OrderType.PREBOOK;
                }
            } else {
                orderType = OrderType.NORMAL;
            }
            preCreateByShopRequest.setExpectedDeliveryTime(expectedDeliveryTimeVal);
            preCreateByShopRequest.setOrderType(orderType.getCode());
            preCreateByShopRequest.setCoordinateType(0);
            PreCreateByShopResponse response = peisongClient.execute(preCreateByShopRequest);
            logger.info(response.toString());
            if (response.getCode().equals("0")) {
                hk = true;
                hkp = response.getData().getDeliveryFee();
            } else {
                logger.info("聚合配送海葵获取价格出错");
            }
        }
        //暂时剔除掉跑腿 避免骑手接到两个订单
//        if ((order.getPlat() == Plat.CLBM || order.getPlat() == Plat.MEITUAN) && order.getMtptId() == null) {
//            //获取到的跑腿价格
//            try {
//                List<OrderZhongbaoShippingFeeParam> orderFee = APIFactory.getOrderAPI().orderZhongbaoShippingFee(systemParam, order.getPlatOrderId());
//                OrderZhongbaoShippingFeeParam feeParam = orderFee.get(0);
//                logger.info("聚合配送跑腿配送费返回" + feeParam.getShipping_fee());
//                pt = true;
//                ptp = feeParam.getShipping_fee();
//            } catch (ApiOpException e) {
//                logger.error("发起美团聚合跑腿出错");
//                logger.error(e.getMsg(), e);
//            } catch (ApiSysException e) {
//                logger.error("发起美团聚合跑腿出错");
//                logger.error(e.getMessage(), e);
//            }
//        }
        if (!uu && !ss && !hk && !pt && !dd) {
            throw new BizException("该订单已经没有可以发起的配送");
        }
        Map<DeliveryType, Double> map = new HashMap<>();
        if (uu && uup != 100.0) {
            map.put(DeliveryType.UU_OPEN, uup);
        }
        if (sf && sfp != 100.0) {
            map.put(DeliveryType.SHUFENG_OPEN, sfp);
        }
        if (dd && ddp != 100.0) {
            map.put(DeliveryType.DD_OPEN, ddp);
        }

        if (ss && ssp != 100.0) {
            map.put(DeliveryType.SS_OPEN, ssp);
        }

        if (hk && hkp != 100.0) {
            map.put(DeliveryType.MEITUAN_OPEN, hkp);
        }

        if (pt && ptp != 100.0) {
            map.put(DeliveryType.ZHONGBAO, ptp);
        }

        List<Map.Entry<DeliveryType, Double>> list = new ArrayList(map.entrySet());
        list.sort(Comparator.comparingDouble(Map.Entry::getValue));
        DeliveryType type = list.get(0).getKey();

        logger.info("最便宜的配送方式" + type.getTitle());

        boolean pc = false;

        if (type == DeliveryType.SS_OPEN) {
            //发起闪送配送
            SendOrderRequest sendOrderRequest = new SendOrderRequest();
            sendOrderRequest.setIssOrderNo(ssOrderNum);
            SendOrderResponse sendOrderResponse = shanSongClient.request(sendOrderRequest);
            if (sendOrderResponse.getStatus() != 200) {
                String info = "闪送发单出错，orderId: " + order.getId() + "  " + sendOrderResponse.getMsg();
                logger.info(info);
                throw new BizException(info);
            }
//            order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
//            order.setSsPeisongId(sendOrderResponse.getData().getOrderNumber());
//            orderRepository.save(order);
            orderRepository.updateSsIdById(order.getId(), sendOrderResponse.getData().getOrderNumber());
            logger.info("发起订单聚合闪送配送成功，" + order.getId());

        } else if (type == DeliveryType.SHUFENG_OPEN) {
            CreateOrderReq req = new CreateOrderReq();
            CreateOrderReq.Receive receive = new CreateOrderReq.Receive();
            logger.info("发起顺丰订单配送，" + order.getId());
            receive.setUserLat(String.valueOf(order.getLat()));
            receive.setUserLng(String.valueOf(order.getLng()));
            receive.setUserName(order.getRecipientName());
            receive.setUserAddress(order.getRecipientAddress());
            receive.setUser_phone(order.getRecipientPhone());
            req.setReceive(receive);
            CreateOrderReq.OrderDetail sforderDetail = new CreateOrderReq.OrderDetail();
            List<CreateOrderReq.ProductDetail> plist = new ArrayList<>();
            List<OrderDetail> detailList = order.getDetailList();
            int count = 0;
            for (OrderDetail orderDetail : detailList) {
                CreateOrderReq.ProductDetail productDetail = new CreateOrderReq.ProductDetail();
                productDetail.setProductName(orderDetail.getFoodName());
                float quantity = orderDetail.getQuantity();
                productDetail.setProductNum((int) quantity);
                count += (int) quantity;
                plist.add(productDetail);
            }
            sforderDetail.setProductDetail(plist);
            sforderDetail.setProductNum(count);
            sforderDetail.setTotalPrice((int) (order.getTotal() * 100));
            sforderDetail.setWeightGram(count * 50);
            sforderDetail.setProductType(6);
            sforderDetail.setProductTypeNum(detailList.size());
            req.setOrderDetail(sforderDetail);
            req.setShopId(su.getSfpsShopId());
            req.setShopOrderId(String.valueOf(order.getId()));
            req.setOrderSequence(String.valueOf(order.getDaySeq()));
            req.setPayType(1);
            req.setOrderTime(order.getCreateTime().getTime() / 1000);
            req.setReturnFlag(511);
            req.setVersion(19);
            req.setRemark(order.getCaution());
            if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
                req.setOrderSource("1");
            } else if (order.getPlat() == Plat.ELE) {
                req.setOrderSource("2");
            } else if (order.getPlat() == Plat.JDDJ) {
                req.setOrderSource("王小菜京东");
            } else {
                req.setOrderSource("王小菜");
            }
            CreateOrderRes res = shunfengClent.execute(req);
            if (res.getErrorCode() == 0) {
                order.setSfPeisongId(res.getResult().getSfOrderId());
                orderRepository.save(order);
                logger.info("发起订单顺分配送成功，" + order.getId());
            } else {
                logger.error("顺丰配送创建失败，" + order.getId() + " " + res.getErrorMsg());
                order.setSfPeisongId("fail");
                orderRepository.save(order);
            }
        } else if (type == DeliveryType.UU_OPEN) {
            AddOrderReuqest addOrderReuqest = new AddOrderReuqest();
            addOrderReuqest.setSpecial_type("0");
            addOrderReuqest.setReceiver_phone(order.getRecipientPhone());
            addOrderReuqest.setReceiver(order.getRecipientName());
            addOrderReuqest.setShortordernum(String.valueOf(order.getDaySeq()));
            addOrderReuqest.setPush_type("0");
            addOrderReuqest.setPubusermobile(su.getPhone());
            addOrderReuqest.setPrice_token(uuPtoken);
            String note = "";
            if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
                addOrderReuqest.setOrdersource("1");
                if (order.getPlat() == Plat.MEITUAN) {
                    note = note + "王小菜美团订单";
                } else {
                    note = note + "菜老板美团订单";
                }
            } else if (order.getPlat() == Plat.ELE) {
                addOrderReuqest.setOrdersource("2");
            } else {
                addOrderReuqest.setOrdersource("3");
            }
            addOrderReuqest.setOrder_price(uuTmoney);
            addOrderReuqest.setNote(note + "  " + order.getCaution());
            addOrderReuqest.setCallme_withtake("1");
            addOrderReuqest.setBalance_paymoney(uuNmoney);
            logger.info(addOrderReuqest.toString());

            AddOrderResponse addOrderResponse = addOrderReuqest.execute(uuAccountType);
            if (!"ok".equals(addOrderResponse.getReturn_code())) {
                String info = "发起uu聚合配送出错，orderId: " + order.getId() + "  " + addOrderResponse.getReturn_msg();
                logger.info(info);
                throw new BizException(info);
            }
//            order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
//            order.setUuPeisongId(addOrderResponse.getOrdercode());
//            orderRepository.save(order);
            orderRepository.updateUuIdById(order.getId(), addOrderResponse.getOrdercode());
            logger.info("发起订单配送成功，" + order.getId());


        } else if (type == DeliveryType.MEITUAN_OPEN) {

            CreateOrderByShopRequest req = new CreateOrderByShopRequest();
            Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
            logger.info("发起订单聚合海葵配送，" + order.getId());
            req.setReceiverLng(Double.valueOf((order.getLng() * Math.pow(10, 6))).intValue());
            req.setReceiverLat(Double.valueOf((order.getLat() * Math.pow(10, 6))).intValue());
            OpenApiGoods openApiGoods = new OpenApiGoods();
            List<OpenApiGood> goods = new ArrayList<>();
            List<OrderDetail> detailList = order.getDetailList();
            for (OrderDetail orderDetail : detailList) {
                OpenApiGood openApiGood = new OpenApiGood();
                openApiGood.setGoodName(orderDetail.getFoodName());
                float quantity = orderDetail.getQuantity();
                openApiGood.setGoodCount((int) quantity);
                openApiGood.setGoodPrice(new BigDecimal(orderDetail.getPrice()));
                openApiGood.setGoodUnit(orderDetail.getUnit());
                goods.add(openApiGood);
            }
            openApiGoods.setGoods(goods);
            req.setGoodsDetail(openApiGoods);
            req.setGoodsValue(new BigDecimal(order.getTotal()));
            int i = (int) (10 + Math.random() * (50 - 10 + 1));
            double ami = i / 10.0;
            req.setGoodsWeight(new BigDecimal(ami));
            req.setDeliveryId(order.getId());
            req.setOrderId(order.getId().toString());
            req.setShopId(su.getMtpsShopId());
            req.setDeliveryServiceCode(meituanPeisongApp.getDeliveryServiceCode());
            req.setReceiverName(order.getRecipientName());
            req.setReceiverAddress(order.getRecipientAddress());
            req.setReceiverPhone(order.getRecipientPhone());
            /**
             * 美团预定单要提前一个小时发单，如果小于1个小时，则发即时单
             * */
            long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
            OrderType orderType;
            if (expectedDeliveryTimeVal > 0) {
                boolean lessThen1h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 < 3600;
                if (lessThen1h) {
                    orderType = OrderType.NORMAL;
                    expectedDeliveryTimeVal = 0L;
                } else {
                    orderType = OrderType.PREBOOK;
                }
            } else {
                orderType = OrderType.NORMAL;
            }
            req.setExpectedDeliveryTime(expectedDeliveryTimeVal);
            req.setOrderType(orderType.getCode());
            req.setCoordinateType(0);
            if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
                req.setPoiSeq(order.getPlat().getTitle() + "#" + order.getDaySeq());
                req.setOuterOrderSourceDesc("101");
                req.setOuterOrderSourceNo(order.getPlatOrderId());
            } else if (order.getPlat() == Plat.JDDJ) {
                req.setPoiSeq("王小菜京东#" + order.getDaySeq());
            } else {
                req.setPoiSeq("王小菜#" + order.getDaySeq());
            }
            req.setNote(order.getCaution());
            CreateOrderResponse res = peisongClient.execute(req);
            if (res.getCode().equals("0")) {
                order.setPsDeliveryId(res.getData().getDeliveryId());
                order.setMtPeisongId(res.getData().getMtPeisongId());
                //order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
                order.setShippingFee(res.getData().getPayAmount().floatValue());
                orderRepository.save(order);
                //orderRepository.updateHkIdById(order.getId(), String.valueOf(res.getData().getMtPeisongId()), String.valueOf(res.getData().getDeliveryId()));
                logger.info("发起订单海葵聚合配送成功，" + order.getId());
                return true;
            } else {
                logger.error("配送海葵聚合创建失败，" + order.getId() + " " + res.getMessage());
            }

        } else if (type == DeliveryType.DD_OPEN) {
            dada.com.request.SendOrderRequest request = new dada.com.request.SendOrderRequest();
            request.setDeliveryNo(ddOrderId);
            dada.com.response.SendOrderResponse response = daDaClient.request(request);
            logger.info(order.getId() + "聚合配送发起达达返回" + response.toString());
            if (response.getCode() != 0) {
                if (response.getCode() == 2064 || response.getCode() == 2105) {
                    logger.info("之前发布过订单，调用重新发单接口");
                    ReAddOrderRequest request1 = new ReAddOrderRequest();
                    request1.setShopNo(su.getId().toString());
                    request1.setOriginId(order.getId().toString());
                    request1.setCityCode(cityCode);
                    request1.setCargoPrice(Double.valueOf(order.getTotal().intValue()));
                    request1.setIsPrepay(0);
                    request1.setReceiverName(order.getRecipientName());
                    request1.setReceiverAddress(order.getRecipientAddress());
                    request1.setReceiverLat(order.getLat());
                    request1.setReceiverLng(order.getLng());
                    request1.setCallback("https://api.wangxiaocai.cn/dd-peisong/order/status");
                    request1.setCargoWeight(1.5);
                    request1.setReceiverPhone(order.getRecipientPhone());
                    request1.setInfo(order.getCaution());
                    request1.setOriginMarkno(order.getPlat().getTitle() + "#" + order.getDaySeq());
                    ReAddOrderResponse response1 = daDaClient.request(request1);
                    if (response1.getCode() != 0) {
                        String info1 = "达达重新发单出错，orderId: " + order.getId() + "  " + response1.getMsg();
                        logger.info(info1);
                        throw new BizException(info1);
                    } else {
                        logger.info(order.getId() + "聚合配送达达发起成功");
                        order.setDdPeisongId(order.getId().toString());
                        orderRepository.save(order);
                    }
                } else {
                    String info1 = "达达聚合发单出错，orderId: " + order.getId() + "  " + response.getMsg();
                    logger.info(info1);
                    throw new BizException(info1);
                }
            } else {
                logger.info(order.getId() + "聚合配送达达发起成功");
                order.setDdPeisongId(order.getId().toString());
                orderRepository.save(order);
            }
        } else if (type == DeliveryType.ZHONGBAO) {
            try {
                String s = APIFactory.getOrderAPI().orderZhongbaoDispatch(systemParam, Long.valueOf(order.getPlatOrderId()), ptp, 0.0);
                logger.info("发起跑腿聚合配送返回" + s);
                if ("ok".equals(s)) {
                    order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
                    order.setMtptId(order.getId());
                    orderRepository.save(order);
                    pc = true;
                } else {
                    order.setMtptId(order.getId());
                    orderRepository.save(order);
                }
            } catch (ApiOpException e) {
                logger.error("发起美团聚合跑腿出错" + order.getId());
                logger.error(e.getMsg(), e);
            } catch (ApiSysException e) {
                logger.error("发起美团聚合跑腿出错" + order.getId());
                logger.error(e.getMessage(), e);
            }
//            if (!pc) {
//                logger.info("美团跑腿发起配送出错，发起下一个价位的配送" + order.getId() + list.get(1).getKey().getTitle());
//                DeliveryType stype = list.get(1).getKey();
//
//
//                if (stype == DeliveryType.SS_OPEN) {
//                    //发起闪送配送
//                    SendOrderRequest sendOrderRequest = new SendOrderRequest();
//                    sendOrderRequest.setIssOrderNo(ssOrderNum);
//                    SendOrderResponse sendOrderResponse = shanSongClient.request(sendOrderRequest);
//                    if (sendOrderResponse.getStatus() != 200) {
//                        String info = "闪送发单出错，orderId: " + order.getId() + "  " + sendOrderResponse.getMsg();
//                        logger.info(info);
//                        throw new BizException(info);
//                    }
//                    order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
//                    order.setSsPeisongId(sendOrderResponse.getData().getOrderNumber());
//                    orderRepository.save(order);
//                    logger.info("发起订单聚合闪送配送成功，" + order.getId());
//
//                } else if (stype == DeliveryType.DD_OPEN) {
//                    dada.com.request.SendOrderRequest request = new dada.com.request.SendOrderRequest();
//                    request.setDeliveryNo(ddOrderId);
//                    dada.com.response.SendOrderResponse response = daDaClient.request(request);
//                    logger.info(order.getId() + "聚合配送发起达达返回" + response.toString());
//                    if (response.getCode() != 0) {
//                        if (response.getCode() == 2064) {
//                            logger.info("之前发布过订单，调用重新发单接口");
//                            ReAddOrderRequest request1 = new ReAddOrderRequest();
//                            request1.setShopNo(su.getId().toString());
//                            request1.setOriginId(order.getId().toString());
//                            request1.setCityCode(cityCode);
//                            request1.setCargoPrice(Double.valueOf(order.getTotal().intValue()));
//                            request1.setIsPrepay(0);
//                            request1.setReceiverName(order.getRecipientName());
//                            request1.setReceiverAddress(order.getRecipientAddress());
//                            request1.setReceiverLat(order.getLat());
//                            request1.setReceiverLng(order.getLng());
//                            request1.setCallback("https://api.wangxiaocai.cn/dd-peisong/order/status");
//                            request1.setCargoWeight(1.5);
//                            request1.setReceiverPhone(order.getRecipientPhone());
//                            request1.setInfo(order.getCaution());
//                            request1.setOriginMarkno(order.getPlat().getTitle() + "#" + order.getDaySeq());
//                            ReAddOrderResponse response1 = daDaClient.request(request1);
//                            if (response1.getCode() != 0) {
//                                String info1 = "达达重新发单出错，orderId: " + order.getId() + "  " + response1.getMsg();
//                                logger.info(info1);
//                                throw new BizException(info1);
//                            } else {
//                                logger.info(order.getId() + "聚合配送达达发起成功");
//                                order.setDdPeisongId(order.getId().toString());
//                                orderRepository.save(order);
//                            }
//                        } else {
//                            String info1 = "达达聚合发单出错，orderId: " + order.getId() + "  " + response.getMsg();
//                            logger.info(info1);
//                            throw new BizException(info1);
//                        }
//                    } else {
//                        logger.info(order.getId() + "聚合配送达达发起成功");
//                        order.setDdPeisongId(order.getId().toString());
//                        orderRepository.save(order);
//                    }
//                } else if (stype == DeliveryType.UU_OPEN) {
//                    AddOrderReuqest addOrderReuqest = new AddOrderReuqest();
//                    addOrderReuqest.setSpecial_type("0");
//                    addOrderReuqest.setReceiver_phone(order.getRecipientPhone());
//                    addOrderReuqest.setReceiver(order.getRecipientName());
//                    addOrderReuqest.setShortordernum(String.valueOf(order.getDaySeq()));
//                    addOrderReuqest.setPush_type("0");
//                    addOrderReuqest.setPubusermobile(su.getPhone());
//                    addOrderReuqest.setPrice_token(uuPtoken);
//                    String note = "";
//                    if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
//                        addOrderReuqest.setOrdersource("1");
//                        if (order.getPlat() == Plat.MEITUAN) {
//                            note = note + "王小菜美团订单";
//                        } else {
//                            note = note + "菜老板美团订单";
//                        }
//                    } else if (order.getPlat() == Plat.ELE) {
//                        addOrderReuqest.setOrdersource("2");
//                    } else {
//                        addOrderReuqest.setOrdersource("3");
//                    }
//                    addOrderReuqest.setOrder_price(uuTmoney);
//                    addOrderReuqest.setNote(note + "  " + order.getCaution());
//                    addOrderReuqest.setCallme_withtake("1");
//                    addOrderReuqest.setBalance_paymoney(uuNmoney);
//                    logger.info(addOrderReuqest.toString());
//
//                    AddOrderResponse addOrderResponse = addOrderReuqest.execute(uuAccountType);
//                    if (!"ok".equals(addOrderResponse.getReturn_code())) {
//                        String info = "发起uu聚合配送出错，orderId: " + order.getId() + "  " + addOrderResponse.getReturn_msg();
//                        logger.info(info);
//                        throw new BizException(info);
//                    }
//                    order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
//                    order.setUuPeisongId(addOrderResponse.getOrdercode());
//                    orderRepository.save(order);
//                    logger.info("发起订单配送成功，" + order.getId());
//
//
//                } else if (stype == DeliveryType.MEITUAN_OPEN) {
//
//                    CreateOrderByShopRequest req = new CreateOrderByShopRequest();
//                    Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
//                    logger.info("发起订单聚合海葵配送，" + order.getId());
//                    req.setReceiverLng(Double.valueOf((order.getLng() * Math.pow(10, 6))).intValue());
//                    req.setReceiverLat(Double.valueOf((order.getLat() * Math.pow(10, 6))).intValue());
//                    OpenApiGoods openApiGoods = new OpenApiGoods();
//                    List<OpenApiGood> goods = new ArrayList<>();
//                    List<OrderDetail> detailList = order.getDetailList();
//                    for (OrderDetail orderDetail : detailList) {
//                        OpenApiGood openApiGood = new OpenApiGood();
//                        openApiGood.setGoodName(orderDetail.getFoodName());
//                        float quantity = orderDetail.getQuantity();
//                        openApiGood.setGoodCount((int) quantity);
//                        openApiGood.setGoodPrice(new BigDecimal(orderDetail.getPrice()));
//                        openApiGood.setGoodUnit(orderDetail.getUnit());
//                        goods.add(openApiGood);
//                    }
//                    openApiGoods.setGoods(goods);
//                    req.setGoodsDetail(openApiGoods);
//                    req.setGoodsValue(new BigDecimal(order.getTotal()));
//                    int i = (int) (10 + Math.random() * (50 - 10 + 1));
//                    double ami = i / 10.0;
//                    req.setGoodsWeight(new BigDecimal(ami));
//                    req.setDeliveryId(order.getId());
//                    req.setOrderId(order.getId().toString());
//                    req.setShopId(su.getMtpsShopId());
//                    req.setDeliveryServiceCode(meituanPeisongApp.getDeliveryServiceCode());
//                    req.setReceiverName(order.getRecipientName());
//                    req.setReceiverAddress(order.getRecipientAddress());
//                    req.setReceiverPhone(order.getRecipientPhone());
//                    /**
//                     * 美团预定单要提前一个小时发单，如果小于1个小时，则发即时单
//                     * */
//                    long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
//                    OrderType orderType;
//                    if (expectedDeliveryTimeVal > 0) {
//                        boolean lessThen1h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 < 3600;
//                        if (lessThen1h) {
//                            orderType = OrderType.NORMAL;
//                            expectedDeliveryTimeVal = 0L;
//                        } else {
//                            orderType = OrderType.PREBOOK;
//                        }
//                    } else {
//                        orderType = OrderType.NORMAL;
//                    }
//                    req.setExpectedDeliveryTime(expectedDeliveryTimeVal);
//                    req.setOrderType(orderType.getCode());
//                    req.setCoordinateType(0);
//                    if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
//                        req.setPoiSeq(order.getPlat().getTitle() + "#" + order.getDaySeq());
//                        req.setOuterOrderSourceDesc("101");
//                        req.setOuterOrderSourceNo(order.getPlatOrderId());
//                    } else if (order.getPlat() == Plat.JDDJ) {
//                        req.setPoiSeq("王小菜京东#" + order.getDaySeq());
//                    } else {
//                        req.setPoiSeq("王小菜#" + order.getDaySeq());
//                    }
//                    req.setNote(order.getCaution());
//                    CreateOrderResponse res = peisongClient.execute(req);
//                    if (res.getCode().equals("0")) {
//                        order.setPsDeliveryId(res.getData().getDeliveryId());
//                        order.setMtPeisongId(res.getData().getMtPeisongId());
//                        order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
//                        orderRepository.save(order);
//                        logger.info("发起订单聚合配送成功，" + order.getId());
//                        return true;
//                    } else {
//                        logger.error("配送海葵聚合创建失败，" + order.getId() + " " + res.getMessage());
//                    }
//
//                }
//
//
//            }
        }

        return true;
    }

    @Override
    public boolean cancelDeliveryByOrderId(long orderId) {
        Order order = orderRepository.findOne(orderId);
        logger.info("查询到的订单信息 订单配送类型" + order.getDeliveryType().getTitle() + " 配送状态" + order.getDeliveryStatus().getTitle() + " 配送取消原因id " + order.getPsCancelReasonId() + " 取消原因 " + order.getCancelReason() + " 配送id " + order.getPsDeliveryId() + " 美团配送id" + order.getMtPeisongId());
        if ((order.getDeliveryType() == DeliveryType.MEITUAN_OPEN || order.getDeliveryType() == DeliveryType.UNDETERMINED || order.getDeliveryType() == DeliveryType.SHUFENG_OPEN || order.getDeliveryType() == DeliveryType.UU_OPEN || order.getDeliveryType() == DeliveryType.SS_OPEN || order.getDeliveryType() == DeliveryType.ZHONGBAO || order.getDeliveryType() == DeliveryType.DD_OPEN)
                && order.getDeliveryStatus() != DeliveryStatus.ARRIVED
                && order.getDeliveryStatus() != DeliveryStatus.CANCELED) {
            if (order.getDeliveryStatus() == DeliveryStatus.WAIT_DELIVERY) {
                logger.info("发起配送前取消" + order.getPlatOrderId());
                orderService.updateDeliveryCancelInfo(order.getId());
                return true;
            }
            if (order.getDeliveryType() == DeliveryType.MEITUAN_OPEN) {
                CancelOrderRequest req = new CancelOrderRequest();
                if (order.getPsCancelReasonId() == null) {
                    req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(199));
                } else {
                    if (CancelOrderReasonId.findByCode(order.getPsCancelReasonId()) == null) {
                        req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(199));
                    } else {
                        req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(order.getPsCancelReasonId()));
                    }

                }
                if (order.getCancelReason() == null || order.getCancelReason() == "") {
                    req.setCancelReason("顾客取消");
                } else {
                    req.setCancelReason(order.getCancelReason());
                }
                req.setDeliveryId(order.getPsDeliveryId());
                req.setMtPeisongId(order.getMtPeisongId());
                CancelOrderResponse res = peisongClient.execute(req);
                if (!res.getCode().equals("0")) {
                    throw new BizException("取消订单错误，" + res.getMessage());
                } else {
                    orderService.updateDeliveryCancelInfo(order.getId());
                    return true;
                }
            } else if (order.getDeliveryType() == DeliveryType.SHUFENG_OPEN) {
                CancelOrderReq req = new CancelOrderReq();
                req.setOrderId(order.getSfPeisongId());
                //req.setOrderType(1);
                CancelOrderRes res = shunfengClent.execute(req);
                if (res.getErrorCode() == 0) {
                    orderService.updateDeliveryCancelInfo(order.getId());
                    return true;
                } else {
                    throw new BizException("取消订单错误，" + res.getErrorMsg());
                }
            } else if (order.getDeliveryType() == DeliveryType.DD_OPEN) {
                dada.com.request.CancelOrderRequest request = new dada.com.request.CancelOrderRequest();
                request.setOrderId(order.getDdPeisongId());
                request.setCancelReasonId("4");
                request.setCancelReason("取消订单配送");
                dada.com.response.CancelOrderResponse response1 = daDaClient.request(request);
                if (response1.getCode() == 0) {
                    orderService.updateDeliveryCancelInfo(order.getId());
                    return true;
                } else {
                    throw new BizException("取消达达订单错误，" + response1.getMsg());
                }
            } else if (order.getDeliveryType() == DeliveryType.UU_OPEN) {
                Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
                CancelUuRequest cancelUuRequest = new CancelUuRequest();
                cancelUuRequest.setOrder_code(order.getUuPeisongId());
                cancelUuRequest.setReason("取消配送");
                logger.info(cancelUuRequest.toString());
                UuAccountType uuAccountType = UuAccountType.TOTAL;
                if (store.getUuAcountType() != null) {
                    uuAccountType = store.getUuAcountType();
                }
                uupt.src.com.uupt.openapi.response.CancelOrderResponse cancelOrderResponse = cancelUuRequest.execute(uuAccountType);
                if (!"ok".equals(cancelOrderResponse.getReturn_code())) {
                    throw new BizException("取消订单错误，" + cancelOrderResponse.getReturn_msg());
                } else {
                    orderService.updateDeliveryCancelInfo(order.getId());
                    return true;
                }
            } else if (order.getDeliveryType() == DeliveryType.SS_OPEN) {
                CancelRequest request = new CancelRequest();
                request.setIssOrderNo(order.getSsPeisongId());
                CancelResponse response = shanSongClient.request(request);
                if (response.getStatus() != 200) {
                    throw new BizException("取消闪送订单错误，" + response.getMsg());
                } else {
                    orderService.updateDeliveryCancelInfo(order.getId());
                    return true;
                }
            } else if (order.getDeliveryType() == DeliveryType.ZHONGBAO) {
                logger.info("取消美团跑腿配送");
                //查询跑腿取消原因
                SystemParam systemParam;
                Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
                if (order.getPlat() == Plat.MEITUAN) {
                    systemParam = meituanWaimaiService.getSystemParam();
                } else {
                    systemParam = clbmWaiMaiService.getSystemParam();
                }
                try {
                    CancelDeliveryReasonParam cancelDeliveryReason = APIFactory.getOrderAPI().getCancelDeliveryReason(systemParam, Long.valueOf(order.getPlatOrderId()), store.getCode());
                    if (cancelDeliveryReason.getReasonList().size() > 0) {
                        CancelReson cancelReson = cancelDeliveryReason.getReasonList().get(0);
                        String s = APIFactory.getOrderAPI().cancelLogisticsByWmOrderId(systemParam, String.valueOf(cancelReson.getCode()), cancelReson.getContent(), store.getCode(), Long.valueOf(order.getPlatOrderId()));
                        logger.info("取消跑腿返回" + s);
                        if ("ok".equals(s)) {
                            orderService.updateDeliveryCancelInfo(order.getId());
                        }
                    } else {
                        logger.error("未获取到取消配送原因列表");

                    }

                } catch (ApiOpException e) {
                    logger.error(e.getMsg(), e);
                } catch (ApiSysException e) {
                    logger.error(e.getMessage(), e);
                }
                return true;
            } else if (order.getDeliveryType() == DeliveryType.UNDETERMINED) {
                this.cancelJuHe(order);
                orderService.updateDeliveryCancelInfo(order.getId());
            }

        }
        return false;

    }

    private boolean sendOrderDDPeisong(Order order) {
//        if (order.getDeliveryStatus() != DeliveryStatus.CANCELED && StringUtils.isNotEmpty(order.getSsPeisongId())) {
//            throw new BizException("当前订单状态不支持发起闪送配送");
//        }
        StoreUser su = order.getStore().getStoreUser();
        logger.info("发起订单达达配送，" + order.getId());
        if (order.getExpectedDeliveryTime() > 0) {
            Date expectedDeliveryTime = new Date(order.getExpectedDeliveryTime() * 1000);
            if (!DateUtils.isSameDay(new Date(), expectedDeliveryTime)) {
                /**
                 * 如果不是当天的预定订单，不发起配送，隔天发起配送
                 * */
                String info = "非当天的预定单，次日发起配送，orderId: " + order.getId() + ", 配送时间：" + DateFormatUtils.format(expectedDeliveryTime, "yyyy-MM-dd HH:mm:ss");
                logger.info(info);
                throw new BizException(info);
            }
        }

        long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
        //预订单提前一个小时发单
        if (expectedDeliveryTimeVal > 0) {
            boolean lessThen1h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 < 3600;
            if (!lessThen1h) {
                throw new BizException("预订单提前一个小时发单");
            }
        }
        //调用城市列表接口，获取城市码
        CityCodeRequest codeRequest = new CityCodeRequest();
        CityCodeResponse codeResponse = daDaClient.request(codeRequest);
        String cityCode = "";
        List<CityCodeResponse.Result> list = codeResponse.getResult();
        for (CityCodeResponse.Result result : list) {
            if (su.getCity().getName().contains(result.getCityName())) {
                cityCode = result.getCityCode();
                break;
            }
        }
        AddOrderRequest request = new AddOrderRequest();
        request.setShopNo(su.getId().toString());
        request.setOriginId(order.getId().toString());
        request.setCityCode(cityCode);
        DecimalFormat format = new DecimalFormat("#.00");
        request.setCargoPrice(Double.valueOf(format.format(order.getTotal().doubleValue())));
        request.setIsPrepay(0);
        request.setReceiverName(order.getRecipientName());
        request.setReceiverAddress(order.getRecipientAddress());
        request.setReceiverLat(order.getLat());
        request.setReceiverLng(order.getLng());
        request.setCallback("https://api.wangxiaocai.cn/dd-peisong/order/status");
        request.setCargoWeight(1.5);
        request.setReceiverPhone(order.getRecipientPhone());
        request.setInfo(order.getCaution());
        request.setOriginMarkno(order.getPlat().getTitle() + "#" + order.getDaySeq());
        dada.com.response.AddOrderResponse response = daDaClient.request(request);
        if (response.getCode() != 0) {
            String info = "达达发单出错，orderId: " + order.getId() + "  " + response.getMsg();
            logger.info(info);
            if (response.getCode() == 2064 || response.getCode() == 2105) {
                logger.info("之前发布过订单，调用重新发单接口");
                ReAddOrderRequest request1 = new ReAddOrderRequest();
                request1.setShopNo(su.getId().toString());
                request1.setOriginId(order.getId().toString());
                request1.setCityCode(cityCode);
                request1.setCargoPrice(Double.valueOf(order.getTotal().intValue()));
                request1.setIsPrepay(0);
                request1.setReceiverName(order.getRecipientName());
                request1.setReceiverAddress(order.getRecipientAddress());
                request1.setReceiverLat(order.getLat());
                request1.setReceiverLng(order.getLng());
                request1.setCallback("https://api.wangxiaocai.cn/dd-peisong/order/status");
                request1.setCargoWeight(1.5);
                request1.setReceiverPhone(order.getRecipientPhone());
                request1.setInfo(order.getCaution());
                request1.setOriginMarkno(order.getPlat().getTitle() + "#" + order.getDaySeq());
                ReAddOrderResponse response1 = daDaClient.request(request1);
                if (response1.getCode() != 0) {
                    String info1 = "达达重新发单出错，orderId: " + order.getId() + "  " + response1.getMsg();
                    logger.info(info1);
                    throw new BizException(info1);
                }
            } else {
                throw new BizException(info);
            }

        }
        order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
        order.setDdPeisongId(order.getId().toString());
        orderRepository.save(order);
        logger.info("发起订单达达配送成功，" + order.getId());
        return true;
    }

    @Override
    public void updateDeliveryCancelInfo(long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.setDeliveryStatus(DeliveryStatus.CANCELED);
        orderRepository.save(order);
    }


    private boolean sendOrderShunFengPeisong(Order order) {
        Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
        StoreUser su = store.getStoreUser();
        if (StringUtils.isBlank(su.getSfpsShopId())) {
            throw new BizException("该商家未填写顺丰店铺Id，请检查后发单");
        }
        if (StringUtils.isNotBlank(order.getSfPeisongId())) {
            //顺丰配送id不是空，先查询订单是否已经被取消过，假如已经被取消，那么就只能补发配送来解决
            GetOrderInfoReq req = new GetOrderInfoReq();
            req.setOrderId(order.getSfPeisongId());
            GetOrderInfoRes res = shunfengClent.execute(req);
            if (res.getResult().getOrderStatus() == 2) {
                throw new BizException("该订单顺丰侧已经取消，请切换其他配送或者补发配送");
            }
        }
        CreateOrderReq req = new CreateOrderReq();
        CreateOrderReq.Receive receive = new CreateOrderReq.Receive();
        logger.info("发起顺丰订单配送，" + order.getId());
        if (order.getLng() == null) {
            try {
                GeoResponse res = aMapClient.geo(order.getRecipientAddress());
                List<GeoResponse.GeoCode> codes = res.getGeocodes();
                if (codes == null || codes.size() == 0) {
                    throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
                }
                String[] location = codes.get(0).getLocation().split(",");
                receive.setUserLng(location[0]);
                receive.setUserLat(location[1]);

            } catch (IOException e) {
                logger.error("获取用户地理位置坐标错误", e);
                throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
            }
        } else {
            receive.setUserLat(String.valueOf(order.getLat()));
            receive.setUserLng(String.valueOf(order.getLng()));
        }
        receive.setUserName(order.getRecipientName());
        receive.setUserAddress(order.getRecipientAddress());
        receive.setUser_phone(order.getRecipientPhone());
        req.setReceive(receive);
        CreateOrderReq.OrderDetail sforderDetail = new CreateOrderReq.OrderDetail();
        List<CreateOrderReq.ProductDetail> list = new ArrayList<>();
        List<OrderDetail> detailList = order.getDetailList();
        int count = 0;
        for (OrderDetail orderDetail : detailList) {
            CreateOrderReq.ProductDetail productDetail = new CreateOrderReq.ProductDetail();
            productDetail.setProductName(orderDetail.getFoodName());
            float quantity = orderDetail.getQuantity();
            productDetail.setProductNum((int) quantity);
            count += (int) quantity;
            list.add(productDetail);
        }
        sforderDetail.setProductDetail(list);
        sforderDetail.setProductNum(count);
        sforderDetail.setTotalPrice((int) (order.getTotal() * 100));
        sforderDetail.setWeightGram(count * 50);
        sforderDetail.setProductType(6);
        sforderDetail.setProductTypeNum(detailList.size());
        req.setOrderDetail(sforderDetail);
        req.setShopId(su.getSfpsShopId());
        req.setShopOrderId(String.valueOf(order.getId()));
        req.setOrderSequence(String.valueOf(order.getDaySeq()));
        req.setPayType(1);
        req.setOrderTime(order.getCreateTime().getTime() / 1000);
        req.setReturnFlag(511);
        req.setVersion(19);
        req.setRemark(order.getCaution());
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            req.setOrderSource("1");
        } else if (order.getPlat() == Plat.ELE) {
            req.setOrderSource("2");
        } else if (order.getPlat() == Plat.JDDJ) {
            req.setOrderSource("王小菜京东");
        } else {
            req.setOrderSource("王小菜");
        }
        CreateOrderRes res = shunfengClent.execute(req);
        if (res.getErrorCode() == 0) {
            //发起成功后再次查询订单状态是否是之前发起过的订单
            GetOrderInfoReq infoReq = new GetOrderInfoReq();
            infoReq.setOrderId(res.getResult().getSfOrderId());
            GetOrderInfoRes infoRes = shunfengClent.execute(infoReq);
            if (infoRes.getResult().getOrderStatus() == 2) {
                throw new BizException("顺丰配送创建失败，该订单在顺丰侧已经被取消，请切换其他配送或者补发顺丰配送");
            }
            order.setSfPeisongId(res.getResult().getSfOrderId());
            if (order.getDeliveryStatus() == DeliveryStatus.WAIT_DELIVERY || order.getDeliveryStatus() == DeliveryStatus.CANCELED) {
                order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
            }
            orderRepository.save(order);
            logger.info("发起订单配送成功，" + order.getId());
            return true;
        } else {
            logger.error("顺丰配送创建失败，" + order.getId() + " " + res.getErrorMsg());
            throw new BizException("顺丰配送创建失败，" + res.getErrorMsg());
        }
    }

    private boolean sendOrderSSPeisong(Order order) {
        if (order.getDeliveryStatus() != DeliveryStatus.CANCELED && StringUtils.isNotEmpty(order.getSsPeisongId())) {
            throw new BizException("当前订单状态不支持发起闪送配送");
        }
        GetPriceResquest priceResquest = new GetPriceResquest();
        LocateInfo locateInfo = new LocateInfo();
        StoreUser su = order.getStore().getStoreUser();
        if (StringUtils.isEmpty(su.getLat()) || StringUtils.isEmpty(su.getLng())) {
            String info = "闪送需要商家经纬度，请添加，orderId: " + order.getId();
            logger.info(info);
            throw new BizException(info);
        }
        logger.info("发起订单闪送配送，" + order.getId());
        if (order.getExpectedDeliveryTime() > 0) {
            Date expectedDeliveryTime = new Date(order.getExpectedDeliveryTime() * 1000);
            if (!DateUtils.isSameDay(new Date(), expectedDeliveryTime)) {
                /**
                 * 如果不是当天的预定订单，不发起配送，隔天发起配送
                 * */
                String info = "非当天的预定单，次日发起配送，orderId: " + order.getId() + ", 配送时间：" + DateFormatUtils.format(expectedDeliveryTime, "yyyy-MM-dd HH:mm:ss");
                logger.info(info);
                throw new BizException(info);
            }
        }

        long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
        //小于一个小时 发即时单  大于一个小时 并且小于两个三时的 不发单  大于三个小时的 发预订单  设置取件时间为送达时间的前一个小时
        if (expectedDeliveryTimeVal > 0) {
            boolean lessThen1h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 < 3600;
            boolean moreThan3h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 > 10800;
            if (lessThen1h) {
                priceResquest.setAppointType(0);
            } else {
                if (moreThan3h) {
                    //大于3个小时
                    priceResquest.setAppointType(1);
                    Date date = new Date((expectedDeliveryTimeVal - 3600) * 1000);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    priceResquest.setAppointmentDate(sdf.format(date));
                } else {
                    //不发单
                    throw new BizException("三个小时之内的预订单，系统会自动发单。");
                }

            }
        } else {
            priceResquest.setAppointType(0);
        }


        LocateInfo storeLocal = this.getBaiDuMap(su.getLng(), su.getLat());
        GetPriceResquest.Sender sender = new GetPriceResquest.Sender();
        sender.setFromLongitude(String.valueOf(storeLocal.getLongitude()));
        sender.setFromLatitude(String.valueOf(storeLocal.getLatitude()));
        sender.setFromSenderName(su.getName());
        sender.setFromMobile(su.getPhone().replace("_", "#").replace(" ", "").replace(",", "#"));
        sender.setFromAddress(su.getAddress());
        priceResquest.setSender(sender);
        priceResquest.setStoreName(su.getName());
        List<GetPriceResquest.Receiver> receiverList = new ArrayList<>();
        GetPriceResquest.Receiver receiver = new GetPriceResquest.Receiver();
        receiver.setWeight("2");
        receiver.setToReceiverName(order.getRecipientName());
        receiver.setToMobile(order.getRecipientPhone().replace("_", "#").replace(" ", "").replace(",", "#"));

        LocateInfo userLocation = new LocateInfo();
        if (order.getLng() == null) {
            try {
                GeoResponse res = aMapClient.geo(order.getRecipientAddress());
                List<GeoResponse.GeoCode> codes = res.getGeocodes();
                if (codes == null || codes.size() == 0) {
                    throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
                }
                String[] location = codes.get(0).getLocation().split(",");
                userLocation = this.getBaiDuMap(location[0], location[1]);
                receiver.setToLatitude(String.valueOf(userLocation.getLatitude()));
                receiver.setToLongitude(String.valueOf(userLocation.getLongitude()));
            } catch (IOException e) {
                logger.error("获取用户地理位置坐标错误", e);
                throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
            }
        } else {
            userLocation = this.getBaiDuMap(String.valueOf(order.getLng()), String.valueOf(order.getLat()));
            receiver.setToLongitude(String.valueOf(userLocation.getLongitude()));
            receiver.setToLatitude(String.valueOf(userLocation.getLatitude()));
        }
        receiver.setToAddress(order.getRecipientAddress());
        String note = "";
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            receiver.setOrderingSourceType("4");
            if (order.getPlat() == Plat.MEITUAN) {
                note = note + "王小菜美团订单";
            } else {
                note = note + "菜老板美团订单";
            }
        } else if (order.getPlat() == Plat.ELE) {
            receiver.setOrderingSourceType("3");
        } else {
            receiver.setOrderingSourceType("6");
        }
        receiver.setOrderingSourceNo(String.valueOf(order.getDaySeq()));
        receiver.setRemarks(note + "  " + order.getCaution());
        receiver.setOrderNo(order.getId().toString());
        receiver.setGoodType("10");
        receiverList.add(receiver);
        priceResquest.setReceiverList(receiverList);
        priceResquest.setCityName(su.getCity().getName());
        logger.info(priceResquest.toString());
        GetPriceResponse priceResponse = shanSongClient.request(priceResquest);
        if (priceResponse.getStatus() != 200) {
            String info = "获取闪送价格出错，orderId: " + order.getId() + "  " + priceResponse.getMsg();
            logger.info(info);
            throw new BizException(info);
        }

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setIssOrderNo(priceResponse.getData().getOrderNumber());
        SendOrderResponse sendOrderResponse = shanSongClient.request(sendOrderRequest);

        if (sendOrderResponse.getStatus() != 200) {
            String info = "闪送发单出错，orderId: " + order.getId() + "  " + sendOrderResponse.getMsg();
            logger.info(info);
            throw new BizException(info);
        }

        order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
        order.setSsPeisongId(sendOrderResponse.getData().getOrderNumber());
        orderRepository.save(order);
        logger.info("发起订单闪送配送成功，" + order.getId());
        return true;
    }

    private boolean sendOrderUUPeisong(Order order) {
        //如果配送状态不是已取消，并且有uu跑腿id，那么不能再次发起配送
        if (order.getDeliveryStatus() != DeliveryStatus.CANCELED && StringUtils.isNotEmpty(order.getUuPeisongId())) {
            throw new BizException("当前订单状态不支持发起uu跑腿配送");
        }
        GetOrderPriceRequest priceRequest = new GetOrderPriceRequest();
        LocateInfo locateInfo = new LocateInfo();
        StoreUser su = order.getStore().getStoreUser();
        if (StringUtils.isEmpty(su.getLat()) || StringUtils.isEmpty(su.getLng())) {
            String info = "UU跑腿需要商家经纬度，请添加，orderId: " + order.getId();
            logger.info(info);
            throw new BizException(info);
        }
        LocateInfo storeLocal = this.getBaiDuMap(su.getLng(), su.getLat());
        priceRequest.setOrigin_id("");
        priceRequest.setFrom_lng(String.valueOf(storeLocal.getLongitude()));
        priceRequest.setFrom_lat(String.valueOf(storeLocal.getLatitude()));
        logger.info("发起订单配送，" + order.getId());
        if (order.getExpectedDeliveryTime() > 0) {
            Date expectedDeliveryTime = new Date(order.getExpectedDeliveryTime() * 1000);
            if (!DateUtils.isSameDay(new Date(), expectedDeliveryTime)) {
                /**
                 * 如果不是当天的预定订单，不发起配送，隔天发起配送
                 * */
                String info = "非当天的预定单，次日发起配送，orderId: " + order.getId() + ", 配送时间：" + DateFormatUtils.format(expectedDeliveryTime, "yyyy-MM-dd HH:mm:ss");
                logger.info(info);
                throw new BizException(info);
            }
        }
        LocateInfo userLocation = new LocateInfo();
        if (order.getLng() == null) {
            try {
                GeoResponse res = aMapClient.geo(order.getRecipientAddress());
                List<GeoResponse.GeoCode> codes = res.getGeocodes();
                if (codes == null || codes.size() == 0) {
                    throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
                }
                String[] location = codes.get(0).getLocation().split(",");
                userLocation = this.getBaiDuMap(location[0], location[1]);
                priceRequest.setTo_lat(String.valueOf(userLocation.getLatitude()));
                priceRequest.setTo_lng(String.valueOf(userLocation.getLongitude()));
            } catch (IOException e) {
                logger.error("获取用户地理位置坐标错误", e);
                throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
            }
        } else {
            userLocation = this.getBaiDuMap(String.valueOf(order.getLng()), String.valueOf(order.getLat()));
            priceRequest.setTo_lng(String.valueOf(userLocation.getLongitude()));
            priceRequest.setTo_lat(String.valueOf(userLocation.getLatitude()));
        }
        priceRequest.setTo_address(order.getRecipientAddress());
        priceRequest.setFrom_address(su.getAddress());
        priceRequest.setCity_name(su.getCity().getName());
        priceRequest.setSend_type("0");


        long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
        //小于一个小时 发即时单  大于一个小时  设置取件时间为送达时间的前一个小时
        if (expectedDeliveryTimeVal > 0) {
            boolean lessThen1h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 < 3600;
            if (lessThen1h) {
                priceRequest.setSubscribe_type("0");
                priceRequest.setSubscribe_time("");
            } else {
                priceRequest.setSubscribe_type("1");
                Date date = new Date((expectedDeliveryTimeVal - 3600) * 1000);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                priceRequest.setSubscribe_time(sdf.format(date));
            }
        } else {
            priceRequest.setSubscribe_type("0");
            priceRequest.setSubscribe_time("");
        }
        logger.info(priceRequest.toString());
        UuAccountType uuAccountType = UuAccountType.TOTAL;
        if (order.getStore().getDeliveryType() != null) {
            uuAccountType = order.getStore().getUuAcountType();
        }
        GetOrderPriceReponse priceReponse = priceRequest.excute(uuAccountType);
        if (!"ok".equals(priceReponse.getReturn_code())) {
            String info = "获取uu价格出错，orderId: " + order.getId() + "  " + priceReponse.getReturn_msg();
            logger.info(info);
            throw new BizException(info);
        }

        AddOrderReuqest addOrderReuqest = new AddOrderReuqest();
        addOrderReuqest.setSpecial_type("0");
        addOrderReuqest.setReceiver_phone(order.getRecipientPhone());
        addOrderReuqest.setReceiver(order.getRecipientName());
        addOrderReuqest.setShortordernum(String.valueOf(order.getDaySeq()));
        addOrderReuqest.setPush_type("0");
        addOrderReuqest.setPubusermobile(su.getPhone());
        addOrderReuqest.setPrice_token(priceReponse.getPrice_token());
        String note = "";
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            addOrderReuqest.setOrdersource("1");
            if (order.getPlat() == Plat.MEITUAN) {
                note = note + "王小菜美团订单";
            } else {
                note = note + "菜老板美团订单";
            }
        } else if (order.getPlat() == Plat.ELE) {
            addOrderReuqest.setOrdersource("2");
        } else {
            addOrderReuqest.setOrdersource("3");
        }
        addOrderReuqest.setOrder_price(priceReponse.getTotal_money());
        addOrderReuqest.setNote(note + "  " + order.getCaution());
        addOrderReuqest.setCallme_withtake("1");
        addOrderReuqest.setBalance_paymoney(priceReponse.getNeed_paymoney());
        logger.info(addOrderReuqest.toString());

        AddOrderResponse addOrderResponse = addOrderReuqest.execute(uuAccountType);
        if (!"ok".equals(addOrderResponse.getReturn_code())) {
            String info = "获取uu价格出错，orderId: " + order.getId() + "  " + addOrderResponse.getReturn_msg();
            logger.info(info);
            throw new BizException(info);
        }
        order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
        order.setUuPeisongId(addOrderResponse.getOrdercode());
        orderRepository.save(order);
        logger.info("发起订单配送成功，" + order.getId());
        return true;
    }

    private LocateInfo getBaiDuMap(String lng, String lat) {
        LocateInfo storeLocal = new LocateInfo();
        try {
            MapResponse location1 = bMapClient.HuoToBai(lng, lat);
            if (location1.getStatus() == 0) {
                storeLocal.setLongitude(Double.valueOf(location1.getResult().get(0).getX()));
                storeLocal.setLatitude(Double.valueOf(location1.getResult().get(0).getY()));
                return storeLocal;
            }
        } catch (Exception e) {
            logger.error("调用百度获取坐标失败");
        }
        storeLocal = CoordinateHelper.gcj02_To_Bd09(Double.valueOf(lng), Double.valueOf(lat));
        return storeLocal;
    }


    private boolean sendOrderMeituanPeisong(Order order) {
        CreateOrderByShopRequest req = new CreateOrderByShopRequest();
        Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
        logger.info("发起订单配送，" + order.getId());
        if (order.getExpectedDeliveryTime() > 0) {
            Date expectedDeliveryTime = new Date(order.getExpectedDeliveryTime() * 1000);
            if (!DateUtils.isSameDay(new Date(), expectedDeliveryTime)) {
                /**
                 * 如果不是当天的预定订单，不发起配送，隔天发起配送
                 * */
                String info = "非当天的预定单，次日发起配送，orderId: " + order.getId() + ", 配送时间：" + DateFormatUtils.format(expectedDeliveryTime, "yyyy-MM-dd HH:mm:ss");
                logger.info(info);
                throw new BizException(info);
            }
        }
        if (order.getLng() == null) {
            try {
                GeoResponse res = aMapClient.geo(order.getRecipientAddress());
                List<GeoResponse.GeoCode> codes = res.getGeocodes();
                if (codes == null || codes.size() == 0) {
                    throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
                }
                String[] location = codes.get(0).getLocation().split(",");
                req.setReceiverLng(Double.valueOf(Double.parseDouble(location[0]) * Math.pow(10, 6)).intValue());
                req.setReceiverLat(Double.valueOf(Double.parseDouble(location[1]) * Math.pow(10, 6)).intValue());

            } catch (IOException e) {
                logger.error("获取用户地理位置坐标错误", e);
                throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
            }
        } else {
            req.setReceiverLng(Double.valueOf((order.getLng() * Math.pow(10, 6))).intValue());
            req.setReceiverLat(Double.valueOf((order.getLat() * Math.pow(10, 6))).intValue());
        }
        OpenApiGoods openApiGoods = new OpenApiGoods();
        List<OpenApiGood> goods = new ArrayList<>();
        List<OrderDetail> detailList = order.getDetailList();
        for (OrderDetail orderDetail : detailList) {
            OpenApiGood openApiGood = new OpenApiGood();
            openApiGood.setGoodName(orderDetail.getFoodName());
            float quantity = orderDetail.getQuantity();
            openApiGood.setGoodCount((int) quantity);
            openApiGood.setGoodPrice(new BigDecimal(orderDetail.getPrice()));
            openApiGood.setGoodUnit(orderDetail.getUnit());
            goods.add(openApiGood);
        }
        openApiGoods.setGoods(goods);
        req.setGoodsDetail(openApiGoods);
        req.setGoodsValue(new BigDecimal(order.getTotal()));
        int i = (int) (10 + Math.random() * (50 - 10 + 1));
        double ami = i / 10.0;
        req.setGoodsWeight(new BigDecimal(ami));
        StoreUser su = order.getStore().getStoreUser();
        req.setDeliveryId(order.getId());
        req.setOrderId(order.getId().toString());
        req.setShopId(su.getMtpsShopId());
        req.setDeliveryServiceCode(meituanPeisongApp.getDeliveryServiceCode());
        req.setReceiverName(order.getRecipientName());
        req.setReceiverAddress(order.getRecipientAddress());
        req.setReceiverPhone(order.getRecipientPhone());
        /**
         * 美团预定单要提前一个小时发单，如果小于1个小时，则发即时单
         * */
        long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
        OrderType orderType;
        if (expectedDeliveryTimeVal > 0) {
            boolean lessThen1h = expectedDeliveryTimeVal - System.currentTimeMillis() / 1000 < 3600;
            if (lessThen1h) {
                orderType = OrderType.NORMAL;
                expectedDeliveryTimeVal = 0L;
            } else {
                orderType = OrderType.PREBOOK;
            }
        } else {
            orderType = OrderType.NORMAL;
        }
        req.setExpectedDeliveryTime(expectedDeliveryTimeVal);
        req.setOrderType(orderType.getCode());
        req.setCoordinateType(0);
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            req.setPoiSeq(order.getPlat().getTitle() + "#" + order.getDaySeq());
            req.setOuterOrderSourceDesc("101");
            req.setOuterOrderSourceNo(order.getPlatOrderId());
        } else if (order.getPlat() == Plat.JDDJ) {
            req.setPoiSeq("王小菜京东#" + order.getDaySeq());
        } else {
            req.setPoiSeq("王小菜#" + order.getDaySeq());
        }
        req.setNote(order.getCaution());
        CreateOrderResponse res = peisongClient.execute(req);
        if (res.getCode().equals("0")) {
            order.setPsDeliveryId(res.getData().getDeliveryId());
            order.setMtPeisongId(res.getData().getMtPeisongId());
            order.setShippingFee(res.getData().getPayAmount().floatValue());
            order.setDeliveryStatus(DeliveryStatus.WAIT_SCHEDULE);
            orderRepository.save(order);
            logger.info("发起订单配送成功，" + order.getId());
            return true;
        } else {
            logger.error("配送创建失败，" + order.getId() + " " + res.getMessage());
            if (res.getCode().equals("110")) {
                //超范围，根据门店第二配送来判断使用什么配送，再次发起
                if (store.getSecondDeliveryType() == DeliveryType.UU_OPEN) {
                    logger.info("超出海葵配送范围或者不在营业时间内，改用uu配送" + order.getPlatOrderId());
                    order.setDeliveryType(DeliveryType.UU_OPEN);
                    order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
                    orderRepository.save(order);
                    return this.sendOrderUUPeisong(order);
                } else if (store.getSecondDeliveryType() == DeliveryType.DD_OPEN) {
                    logger.info("超出海葵配送范围或者不在营业时间内，改用达达配送" + order.getPlatOrderId());
                    order.setDeliveryType(DeliveryType.DD_OPEN);
                    order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
                    orderRepository.save(order);
                    return this.sendOrderDDPeisong(order);
                } else if (store.getSecondDeliveryType() == DeliveryType.SS_OPEN) {
                    logger.info("超出海葵配送范围或者不在营业时间内，改用闪送配送" + order.getPlatOrderId());
                    order.setDeliveryType(DeliveryType.SS_OPEN);
                    order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
                    orderRepository.save(order);
                    return this.sendOrderSSPeisong(order);
                } else if (store.getSecondDeliveryType() == DeliveryType.SHUFENG_OPEN) {
                    logger.info("超出海葵配送范围或者不在营业时间内，改用顺丰配送" + order.getPlatOrderId());
                    order.setDeliveryType(DeliveryType.SHUFENG_OPEN);
                    order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
                    orderRepository.save(order);
                    return this.sendOrderShunFengPeisong(order);
                } else if (store.getSecondDeliveryType() == DeliveryType.DD_OPEN) {
                    logger.info("超出海葵配送范围或者不在营业时间内，改用达达配送" + order.getPlatOrderId());
                    order.setDeliveryType(DeliveryType.DD_OPEN);
                    order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
                    orderRepository.save(order);
                    return this.sendOrderDDPeisong(order);
                } else {
                    if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
                        logger.info("超出海葵配送范围或者不在营业时间内，发起美团跑腿" + order.getPlatOrderId());
                        order.setDeliveryType(DeliveryType.ZHONGBAO);
                        order.setDeliveryStatus(DeliveryStatus.OUTRANGE);
                        orderRepository.save(order);
                        return this.sendZbPeisong(order);
                    } else {
                        throw new BizException("配送创建失败，" + res.getMessage());
                    }

                }
            } else {
                throw new BizException("配送创建失败，" + res.getMessage());
            }

        }
    }


    private boolean sendZbPeisong(Order order) {
        if (order.getPlat() != Plat.MEITUAN && order.getPlat() != Plat.CLBM) {
            logger.error("当前订单不是美团或者菜老板订单 无法发起跑腿配送");
            return false;
        }
        SystemParam systemParam;
        if (order.getPlat() == Plat.MEITUAN) {
            systemParam = meituanWaimaiService.getSystemParam();
        } else {
            systemParam = clbmWaiMaiService.getSystemParam();
        }
        try {
            List<OrderZhongbaoShippingFeeParam> orderFee = APIFactory.getOrderAPI().orderZhongbaoShippingFee(systemParam, order.getPlatOrderId());
            OrderZhongbaoShippingFeeParam feeParam = orderFee.get(0);
            logger.info("跑腿配送费返回" + feeParam.getShipping_fee());
            String s = APIFactory.getOrderAPI().orderZhongbaoDispatch(systemParam, Long.valueOf(order.getPlatOrderId()), feeParam.getShipping_fee(), 0.0);
            logger.info("发起跑腿配送返回" + s);
            if ("ok".equals(s)) {
                order.setDeliveryStatus(DeliveryStatus.TURNRUN);
                order.setDeliveryType(DeliveryType.ZHONGBAO);
                order.setMtptId(order.getId());
                order.setShippingFee(feeParam.getShipping_fee().floatValue());
                orderRepository.save(order);
            }
        } catch (ApiOpException e) {
            logger.error("发起美团跑腿出错");
            logger.error(e.getMsg(), e);
        } catch (ApiSysException e) {
            logger.error("发起美团跑腿出错");
            logger.error(e.getMessage(), e);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeliveryType updateOrderPeisongInfo(PeisongInfo peisongInfo) {
        logger.info("更新订单配送信息：" + peisongInfo);
        Order order;
        DeliveryType type;
        if (peisongInfo.getType() == DeliveryType.MEITUAN_OPEN) {
            Long orderId = Long.valueOf(peisongInfo.getOrderId());
            order = orderRepository.findOne(orderId);
            if (order != null) {
                order.setMtPeisongId(peisongInfo.getMtPeisongId());
                order.setPsDeliveryId(peisongInfo.getDeliveryId());
            }

        } else if (peisongInfo.getType() == DeliveryType.UU_OPEN) {
            if (StringUtils.isNotBlank(peisongInfo.getOrderId())) {
                Long orderId = Long.valueOf(peisongInfo.getOrderId());
                order = orderRepository.findOne(orderId);
                if (order != null) {
                    order.setUuPeisongId(peisongInfo.getUuPeisongId());
                }
            } else {
                order = orderRepository.findByUuPeisongId(peisongInfo.getUuPeisongId());
            }

        } else if (peisongInfo.getType() == DeliveryType.SS_OPEN) {
            //要判断原来的是不是空  然后再比较  不然不能判断为不一样
            Long orderId = Long.valueOf(peisongInfo.getOrderId());
            order = orderRepository.findOne(orderId);
            if (order != null) {
                if (StringUtils.isBlank(order.getSsPeisongId())) {
                    order.setSsPeisongId(peisongInfo.getSsPeisongId());
                } else {
                    //判断配送单号是否一致
                    if (!order.getSsPeisongId().equals(peisongInfo.getSsPeisongId())) {
                        //单号不一致的时候需要取消其中一个配送订单
                        DeliveryStatus ostatus = order.getDeliveryStatus();
                        DeliveryStatus nstatus = peisongInfo.getStatus();
                        if (nstatus == DeliveryStatus.CANCELED) {
                            //本次的配送状态是取消，那么不同步本次的配送状态
                            return null;
                        }
                        if ((ostatus == DeliveryStatus.WAIT_SCHEDULE && nstatus == DeliveryStatus.WAIT_SCHEDULE) || (ostatus == DeliveryStatus.ACCEPT && nstatus == DeliveryStatus.WAIT_SCHEDULE) || (ostatus == DeliveryStatus.ACCEPT && nstatus == DeliveryStatus.ACCEPT) || (ostatus == DeliveryStatus.TAKEN && nstatus == DeliveryStatus.WAIT_SCHEDULE) || (ostatus == DeliveryStatus.TAKEN && nstatus == DeliveryStatus.ACCEPT) || (ostatus == DeliveryStatus.TAKEN && nstatus == DeliveryStatus.TAKEN) || ostatus == DeliveryStatus.ARRIVED) {
                            //原来的配送状态高于新的配送状态，将新的配送取消掉,然后返回null 继续同步原来的配送
                            CancelRequest request = new CancelRequest();
                            request.setIssOrderNo(peisongInfo.getSsPeisongId());
                            CancelResponse response = shanSongClient.request(request);
                            if (response.getStatus() != 200) {
                                logger.error("取消订单重复错误，" + response.getMsg());
                            } else {
                                logger.info("取消闪送重复订单成功");
                            }
                            return null;

                        } else if ((ostatus == DeliveryStatus.WAIT_SCHEDULE && nstatus == DeliveryStatus.ACCEPT) || (ostatus == DeliveryStatus.WAIT_SCHEDULE && nstatus == DeliveryStatus.TAKEN) || (ostatus == DeliveryStatus.WAIT_SCHEDULE && nstatus == DeliveryStatus.ARRIVED) || (ostatus == DeliveryStatus.ACCEPT && nstatus == DeliveryStatus.TAKEN) || (ostatus == DeliveryStatus.ACCEPT && nstatus == DeliveryStatus.ARRIVED)) {
                            //原来的配送低于新的配送，将原来的配送取消掉，同步新的配送
                            CancelRequest request = new CancelRequest();
                            request.setIssOrderNo(order.getSsPeisongId());
                            CancelResponse response = shanSongClient.request(request);
                            if (response.getStatus() != 200) {
                                logger.error("取消订单重复错误，" + response.getMsg());
                            } else {
                                logger.info("取消闪送重复订单成功");
                            }
                            order.setSsPeisongId(peisongInfo.getSsPeisongId());

                        } else if (ostatus == DeliveryStatus.CANCELED) {
                            //原来的配送已经被取消了  那么就更新新的配送
                            order.setSsPeisongId(peisongInfo.getSsPeisongId());
                        }
                    }
                }

            }
        } else if (peisongInfo.getType() == DeliveryType.DD_OPEN) {
            Long orderId = Long.valueOf(peisongInfo.getOrderId());
            order = orderRepository.findOne(orderId);
            if (order != null) {
                order.setDdPeisongId(peisongInfo.getOrderId());
            }
        } else if (peisongInfo.getType() == DeliveryType.ZHONGBAO) {
            order = orderRepository.findByPlatAndPlatOrderId(peisongInfo.getPlat(), peisongInfo.getPtPeisongId());
        } else if (peisongInfo.getType() == DeliveryType.SHUFENG_OPEN) {
            Long orderId = Long.valueOf(peisongInfo.getOrderId());
            order = orderRepository.findOne(orderId);
            if (order != null) {
                order.setSfPeisongId(peisongInfo.getSfPeisongId());
            }
        } else {
            return null;
        }

        if (order == null) {
            logger.info("未找到需要同步配送信息的订单" + peisongInfo);
            return null;
        } else {
            logger.info(order.getId() + "原来的配送方式" + order.getDeliveryType().getTitle());
            type = order.getDeliveryType();
        }

        if (order.getDeliveryType() == DeliveryType.PLATFORM) {
            logger.info("美团平台配送订单不同步状态" + order.getPlatOrderId());
            return null;
        }

        Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());

        if (order.getDeliveryType() != DeliveryType.UNDETERMINED && order.getDeliveryType() != peisongInfo.getType()) {
            if (peisongInfo.getStatus() == DeliveryStatus.CANCELED) {
                logger.info("订单已经更改了其他配送方式，不再同步该配送平台的取消状态" + order.getPlatOrderId());
                return null;
            } else if (store.getDeliveryType() == DeliveryType.UNDETERMINED) {
                logger.info("订单已经更改了其他的配送方式，并且该配送方式不是已取消状态，取消该配送平台的配送" + peisongInfo.getType().getTitle() + order.getId());
                this.cancelPeisong(order, peisongInfo);
                return null;
            } else {
                if (order.getStatus() == OrderStatus.CANCELED) {
                    logger.info("该订单已经被取消，配送也取消掉");
                    this.cancelPeisong(order, peisongInfo);
                    return null;
                } else {
                    logger.info("该订单的店铺不是聚合配送，继续同步订单状态，不取消订单配送");
                    order.setDeliveryType(peisongInfo.getType());
                }

            }
        }

        //排除无用的待调度
        if (peisongInfo.getStatus() == DeliveryStatus.WAIT_SCHEDULE) {
            if (order.getDeliveryStatus() == DeliveryStatus.WAIT_DELIVERY || order.getDeliveryStatus() == DeliveryStatus.CANCELED) {

            } else {
                //除了待配送和已取消的配送状态，其他的配送状态不需要再同步成待调度
                return null;
            }
        }


        if (peisongInfo.getDeliveryId() != 0) {
            order.setPsDeliveryId(peisongInfo.getDeliveryId());
        }
        order.setPsCancelReason(peisongInfo.getCancelReason());
        order.setPsCancelReasonId(peisongInfo.getCancelReasonId());
        if (StringUtils.isNotBlank(peisongInfo.getMtPeisongId())) {
            order.setMtPeisongId(peisongInfo.getMtPeisongId());
        }
        if (StringUtils.isNotBlank(peisongInfo.getCourierName())) {
            order.setPsCourierName(peisongInfo.getCourierName());
        }
        if (StringUtils.isNotBlank(peisongInfo.getCourierPhone())) {
            order.setPsCourierPhone(peisongInfo.getCourierPhone());
        }

        if (type == DeliveryType.UNDETERMINED && peisongInfo.getStatus() == DeliveryStatus.CANCELED) {
            //聚合配送的订单状态不改成已取消的状态
            logger.info("聚合配送中的订单一方配送被取消掉" + order.getId());
        } else {
            order.setDeliveryStatus(peisongInfo.getStatus());
        }

        if (order.getDeliveryType() == DeliveryType.UNDETERMINED && (peisongInfo.getStatus() == DeliveryStatus.ACCEPT || peisongInfo.getStatus() == DeliveryStatus.TAKEN || peisongInfo.getStatus() == DeliveryStatus.ARRIVED)) {
            //聚合配送的订单已经被骑手接单，需要取消其他平台的配送
            //首先改变订单的配送方式，确定配方式之后，其他配送平台的状态就不会同步进来
            order.setDeliveryType(peisongInfo.getType());
            //this.cancelJuHe(order);
        }
        logger.info("订单的详细信息" + order.getId() + "平台id " + order.getPlatOrderId());
        orderRepository.save(order);
        return type;
    }

    private void cancelPeisong(Order order, PeisongInfo peisongInfo) {
        if (peisongInfo.getType() == DeliveryType.MEITUAN_OPEN) {
            logger.info("取消订单海葵配送" + order.getId());
            CancelOrderRequest req = new CancelOrderRequest();
            if (order.getPsCancelReasonId() == null) {
                req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(199));
            } else {
                if (CancelOrderReasonId.findByCode(order.getPsCancelReasonId()) == null) {
                    req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(199));
                } else {
                    req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(order.getPsCancelReasonId()));
                }

            }
            if (order.getCancelReason() == null || order.getCancelReason() == "") {
                req.setCancelReason("顾客取消");
            } else {
                req.setCancelReason(order.getCancelReason());
            }
            req.setDeliveryId(peisongInfo.getDeliveryId());
            req.setMtPeisongId(peisongInfo.getMtPeisongId());
            CancelOrderResponse res = peisongClient.execute(req);
            if (!res.getCode().equals("0")) {
                logger.error("取消海葵聚合配送失败" + res.getMessage());
            } else {
                logger.info("取消海葵聚合配送成功");
                order.setMtPeisongId(null);
                order.setPsDeliveryId(null);
            }

        } else if (peisongInfo.getType() == DeliveryType.UU_OPEN) {
            logger.info("取消订单UU配送" + order.getId());
            Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
            CancelUuRequest cancelUuRequest = new CancelUuRequest();
            cancelUuRequest.setOrder_code(peisongInfo.getUuPeisongId());
            cancelUuRequest.setReason("取消配送");
            logger.info(cancelUuRequest.toString());
            UuAccountType uuAccountType = UuAccountType.TOTAL;
            if (store.getUuAcountType() != null) {
                uuAccountType = store.getUuAcountType();
            }
            uupt.src.com.uupt.openapi.response.CancelOrderResponse cancelOrderResponse = cancelUuRequest.execute(uuAccountType);
            if (!"ok".equals(cancelOrderResponse.getReturn_code())) {
                logger.error("取消订单错误" + cancelOrderResponse.getReturn_msg());
            } else {
                logger.info("取消uu聚合成功");
                order.setUuPeisongId(null);
            }

        } else if (peisongInfo.getType() == DeliveryType.SHUFENG_OPEN) {
            CancelOrderReq req = new CancelOrderReq();
            req.setOrderId(order.getSfPeisongId());
            CancelOrderRes res = shunfengClent.execute(req);
            if (res.getErrorCode() == 0) {
                logger.info("取消顺丰聚合成功");
                order.setSfPeisongId(null);
            } else {
                throw new BizException("取消订单错误，" + res.getErrorMsg());
            }
        } else if (peisongInfo.getType() == DeliveryType.SS_OPEN) {
            logger.info("取消订单闪送配送" + order.getId());
            CancelRequest request = new CancelRequest();
            request.setIssOrderNo(peisongInfo.getSsPeisongId());
            CancelResponse response = shanSongClient.request(request);
            if (response.getStatus() != 200) {
                logger.error("取消订单错误，" + response.getMsg());
            } else {
                logger.info("取消闪送聚合成功");
                order.setSsPeisongId(null);
            }

        } else if (peisongInfo.getType() == DeliveryType.DD_OPEN) {
            logger.info("取消订单达达聚合配送" + order.getId());
            GetReasonRequest reasonRequest = new GetReasonRequest();
            GetReasonResponse response = daDaClient.request(reasonRequest);
            List<GetReasonResponse.Result> resultList = response.getResult();
            Integer reasonId = 0;
            String reason = "需要取消配送";
            for (GetReasonResponse.Result result : resultList) {
                if (result.getReason().contains("其他")) {
                    reasonId = result.getId();
                    reason = result.getReason();
                    break;
                }
            }
            if (reasonId == 0) {
                reasonId = resultList.get(resultList.size() - 1).getId();
            }
            dada.com.request.CancelOrderRequest request = new dada.com.request.CancelOrderRequest();
            request.setOrderId(order.getId().toString());
            request.setCancelReasonId(reasonId.toString());
            request.setCancelReason(reason);
            dada.com.response.CancelOrderResponse response1 = daDaClient.request(request);
            logger.info(order.getId() + "取消达达配送返回" + response1.toString());
            if (response1.getCode() == 0) {
                logger.info("取消达达聚合成功");
                order.setDdPeisongId(null);
            } else {
                logger.error("取消达达订单错误，" + response1.getMsg());
            }
        } else if (peisongInfo.getType() == DeliveryType.ZHONGBAO) {
            logger.info("取消订单跑腿配送" + order.getId());
            SystemParam systemParam;
            if (order.getPlat() == Plat.MEITUAN) {
                systemParam = meituanWaimaiService.getSystemParam();
            } else {
                systemParam = clbmWaiMaiService.getSystemParam();
            }
            try {
                CancelDeliveryReasonParam cancelDeliveryReason = APIFactory.getOrderAPI().getCancelDeliveryReason(systemParam, Long.valueOf(order.getPlatOrderId()), order.getAppPoiCode());
                if (cancelDeliveryReason.getReasonList().size() > 0) {
                    CancelReson cancelReson = cancelDeliveryReason.getReasonList().get(0);
                    String s = APIFactory.getOrderAPI().cancelLogisticsByWmOrderId(systemParam, String.valueOf(cancelReson.getCode()), cancelReson.getContent(), order.getAppPoiCode(), Long.valueOf(order.getPlatOrderId()));
                    logger.info("取消跑腿返回" + s);
                    if ("ok".equals(s)) {
                        logger.info("取消跑腿成功");
                        order.setMtptId(null);
                    }
                }

            } catch (ApiOpException e) {
                logger.error(e.getMsg(), e);
            } catch (ApiSysException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void updatePiesonInfoToPlat(PeisongInfo peisongInfo, DeliveryType otype) {
        logger.info("更新订单平台配送信息：" + peisongInfo);
        Order order;
        if (peisongInfo.getType() == DeliveryType.MEITUAN_OPEN) {
            Long orderId = Long.valueOf(peisongInfo.getOrderId());
            order = orderRepository.findOne(orderId);
        } else if (peisongInfo.getType() == DeliveryType.DD_OPEN) {
            Long orderId = Long.valueOf(peisongInfo.getOrderId());
            order = orderRepository.findOne(orderId);
        } else if (peisongInfo.getType() == DeliveryType.UU_OPEN) {
            order = orderRepository.findByUuPeisongId(peisongInfo.getUuPeisongId());
        } else if (peisongInfo.getType() == DeliveryType.SS_OPEN) {
            order = orderRepository.findBySsPeisongId(peisongInfo.getSsPeisongId());
        } else if (peisongInfo.getType() == DeliveryType.ZHONGBAO) {
            order = orderRepository.findByPlatAndPlatOrderId(peisongInfo.getPlat(), peisongInfo.getPtPeisongId());
        } else if (peisongInfo.getType() == DeliveryType.SHUFENG_OPEN) {
            order = orderRepository.findBySfPeisongId(peisongInfo.getSfPeisongId());
        } else {
            return;
        }
        if (otype == DeliveryType.UNDETERMINED && (peisongInfo.getStatus() == DeliveryStatus.ACCEPT || peisongInfo.getStatus() == DeliveryStatus.TAKEN || peisongInfo.getStatus() == DeliveryStatus.ARRIVED)) {
            logger.info("取消原来的聚合配送" + order.getId());
            try {
                this.cancelJuHe(order);
            } catch (Exception e) {
                logger.error("取消聚合配送出错", e);
            }

        }
        logger.info("开始同步配送信息到平台");

        if (order.getDeliveryType() == DeliveryType.ZHONGBAO || peisongInfo.getType() == DeliveryType.ZHONGBAO || order.getDeliveryType() == DeliveryType.UNDETERMINED) {
            return;
        }

        //如果是因为没有运力或者骑手取消的配送 再次发起配送
        if (peisongInfo.getStatus() == DeliveryStatus.CANCELED && order.getStatus() == OrderStatus.MERCHANT_CONFIRMED) {
            if (peisongInfo.getCancelReasonId() == 1201 || peisongInfo.getCancelReasonId() == 1202 || peisongInfo.getCancelReasonId() == 1203) {
                if (order.getDeliveryType() == DeliveryType.MEITUAN_OPEN) {
                    logger.info("由于运力或骑手原因取消配送 再次发起配送" + order.getId());
                    try {
                        sendOrderMeituanPeisong(order);
                    } catch (Exception e) {
                        logger.error("发起被取消订单出错" + e.getMessage());
                    }

                }

            } else if (peisongInfo.getCancelReasonId() == 1 && order.getDeliveryType() == DeliveryType.DD_OPEN) {
                logger.info("由于运力或骑手原因达达取消配送 再次发起配送" + order.getId());
                try {
                    sendOrderDDPeisong(order);
                } catch (Exception e) {
                    logger.error("发起被取消订单出错" + e.getMessage());
                }
            }
        }
        /**
         * 更新订单状态
         * */
        SyncInfo info = new SyncInfo();
        if (peisongInfo.getStatus() == DeliveryStatus.ACCEPT || peisongInfo.getStatus() == DeliveryStatus.TAKEN || peisongInfo.getStatus() == DeliveryStatus.ARRIVED) {
            info = this.getRiderLocation(order.getId());
            if (peisongInfo.getType() == DeliveryType.SS_OPEN || peisongInfo.getType() == DeliveryType.SHUFENG_OPEN) {
                info.setLongitude(peisongInfo.getLongitude());
                info.setLatitude(peisongInfo.getLatitude());
            }
        }
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            SystemParam systemParam = null;
            if (order.getPlat() == Plat.MEITUAN) {
                systemParam = meituanWaimaiService.getSystemParam();
            } else {
                systemParam = clbmWaiMaiService.getSystemParam();
            }
            OrderLogisticsSyncParam param = new OrderLogisticsSyncParam();
            param.setOrder_id(order.getPlatOrderId());
            if (StringUtils.isNotBlank(peisongInfo.getCourierName())) {
                param.setCourier_name(peisongInfo.getCourierName());
                param.setCourier_phone(peisongInfo.getCourierPhone());
            } else {
                param.setCourier_name(order.getPsCourierName());
                param.setCourier_phone(order.getPsCourierPhone());
            }

            if (peisongInfo.getStatus() == DeliveryStatus.ACCEPT) {
                param.setLogistics_status(10);
            } else if (peisongInfo.getStatus() == DeliveryStatus.ARRIVED) {
                param.setLogistics_status(40);
            } else {
                param.setLogistics_status(20);
            }
            param.setLogistics_provider_code(info.getPsCode());
            param.setThird_carrier_order_id(info.getPsOrderId());
            if (peisongInfo.getStatus() == DeliveryStatus.ACCEPT || peisongInfo.getStatus() == DeliveryStatus.TAKEN) {
                param.setLongitude(info.getLongitude());
                param.setLatitude(info.getLatitude());
            } else if (peisongInfo.getStatus() == DeliveryStatus.ARRIVED) {
                param.setLatitude(order.getLat().toString());
                param.setLongitude(order.getLng().toString());
            }

            try {
                if (order.getDeliveryType() != DeliveryType.MEITUAN_OPEN) {
                    String rs = APIFactory.getOrderAPI().orderLogisticsSync(systemParam, param);
                    logger.info("同步配送信息返回：" + rs);
                } else {
                    logger.info("海葵订单不需要同步到美团");
                }
            } catch (ApiOpException e) {
                logger.error(e.getMsg(), e);
            } catch (ApiSysException e) {
                logger.error(e.getMessage(), e);
            }


        } else if (order.getPlat() == Plat.ELE) {
            if (peisongInfo.getStatus() == DeliveryStatus.ACCEPT || peisongInfo.getStatus() == DeliveryStatus.TAKEN || peisongInfo.getStatus() == DeliveryStatus.ARRIVED) {

                SelfDeliveryStateSyncRequest request = new SelfDeliveryStateSyncRequest();
                request.setOrderId(order.getPlatOrderId());
                request.setDistributorId(201);
                if (peisongInfo.getStatus() == DeliveryStatus.ACCEPT) {
                    request.setState(3);
                } else if (peisongInfo.getStatus() == DeliveryStatus.TAKEN) {
                    request.setState(20);
                } else if (peisongInfo.getStatus() == DeliveryStatus.ARRIVED) {
                    request.setState(30);
                }
                SelfDeliveryStateSyncRequest.Knight knight = new SelfDeliveryStateSyncRequest.Knight();
                knight.setName(peisongInfo.getCourierName());
                knight.setPhone(peisongInfo.getCourierPhone());
                request.setKnight(knight);
                SelfDeliveryStateSyncResponse response = eleClient.request(request);
                if (peisongInfo.getStatus() == DeliveryStatus.ARRIVED) {
                    logger.info("开始更新订单送达信息到饿了么" + order.getPlatOrderId());
                    OrderCompleteRequest completeRequest = new OrderCompleteRequest();
                    completeRequest.setOrderId(order.getPlatOrderId());
                    OrderCompleteResponse completeResponse = eleClient.request(completeRequest);
                    logger.info("饿了么送达同步返回" + completeResponse.getData());
                }
//                OrderSendRequest request = new OrderSendRequest();
//                request.setOrderId(order.getPlatOrderId());
//                request.setPhone(peisongInfo.getCourierPhone());
//                OrderSendResponse response = eleClient.request(request);
                if (response.getErrno() == 0) {
                    logger.info("更新配送信息到饿百成功");
                } else {
                    // 更新配送信息失败
                    logger.error("更新配送信息到饿百失败，原因：" + response.getError());
                }
            }

        } else if (order.getPlat() == Plat.WANTE) {
            if (peisongInfo.getStatus() == DeliveryStatus.ACCEPT) {
                OrderSendReq orderSendReq = new OrderSendReq();
                orderSendReq.setOrderId(Integer.valueOf(order.getPlatOrderId()));
                wanteClient.execute(orderSendReq);
                //更新配送信息到客户端
                UpdateDeliveryReq req = new UpdateDeliveryReq();
                req.setPhone(peisongInfo.getCourierPhone());
                req.setOrderId(Integer.valueOf(order.getPlatOrderId()));
                req.setName(peisongInfo.getCourierName());
                UpdateDeliveryRes execute = wanteClient.execute(req);
                if (execute != null && execute.getId() != null) {
                    logger.info("更新配送信息到客户端成功");
                } else {
                    logger.error("更新配送信息到客户端失败");
                }
            } else if (peisongInfo.getStatus() == DeliveryStatus.ARRIVED) {
                //更新骑手确认到客户端
                OrderConfirmReq req = new OrderConfirmReq();
                req.setOrderId(Integer.valueOf(order.getPlatOrderId()));
                OrderConfirmRes execute = wanteClient.execute(req);
                if (execute != null && execute.getId() != null) {
                    logger.info("更新确认订单到客户端成功");
                } else {
                    logger.error("更新确认订单到客户端失败");
                }
            }

        } else if (order.getPlat() == Plat.JDDJ) {
            Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), Plat.JDDJ);
            if (peisongInfo.getStatus() == DeliveryStatus.TAKEN) {
                //更改京东到家订单状态
                OrderSerllerDeliveryReq req = new OrderSerllerDeliveryReq();
                req.setOrderId(order.getPlatOrderId());
                req.setOperator("王小菜");
                try {
                    OrderSerllerDeliveryRes res;
                    if (store.getJd()) {
                        res = jingdongClient.request(req);
                    } else {
                        res = jingdongzClient.request(req);
                    }
                    if ("0".equals(res.getCode())) {
                        if ("0".equals(res.getData().getCode())) {
                            logger.info("更新京东到家订单状态成功");
                        } else {
                            logger.error("更新京东到家订单状态失败");
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            } else if (peisongInfo.getStatus() == DeliveryStatus.ARRIVED) {
                DeliveryEndOrderReq req = new DeliveryEndOrderReq();
                req.setOperPin("王小菜");
                req.setOrderId(Long.valueOf(order.getPlatOrderId()));
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = formatter.format(currentTime);
                req.setOperTime(time);
                DeliveryEndOrderRes res;
                if (store.getJd()) {
                    res = jingdongClient.request(req);
                } else {
                    res = jingdongzClient.request(req);
                }
                if ("0".equals(res.getCode())) {
                    if ("0".equals(res.getData().getCode())) {
                        logger.info("更新京东到家完成订单状态成功");
                    } else {
                        logger.error("更新京东到家完成订单状态失败");
                    }
                }
            }
        }

        if (peisongInfo.getStatus() == DeliveryStatus.TAKEN || peisongInfo.getStatus() == DeliveryStatus.ARRIVED) {
            if (peisongInfo.getType() == DeliveryType.SS_OPEN || peisongInfo.getType() == DeliveryType.DD_OPEN || peisongInfo.getType() == DeliveryType.SHUFENG_OPEN || peisongInfo.getType() == DeliveryType.UU_OPEN) {
                if (info.getPrice() != null && info.getPrice() > 0) {
                    order.setShippingFee(info.getPrice());
                    orderRepository.save(order);
                    logger.info(order.getId() + "设置订单配送费用" + info.getPrice());
                }
            }
        }


    }


    private void cancelJuHe(Order order) {
        if (order.getDeliveryType() != DeliveryType.ZHONGBAO && order.getMtptId() != null) {
            logger.info("取消聚合众包配送");
            //美团众包配送
            //查询跑腿取消原因
            SystemParam systemParam;
            if (order.getPlat() == Plat.MEITUAN) {
                systemParam = meituanWaimaiService.getSystemParam();
            } else {
                systemParam = clbmWaiMaiService.getSystemParam();
            }
            try {
                CancelDeliveryReasonParam cancelDeliveryReason = APIFactory.getOrderAPI().getCancelDeliveryReason(systemParam, Long.valueOf(order.getPlatOrderId()), order.getAppPoiCode());
                if (cancelDeliveryReason.getReasonList().size() > 0) {
                    CancelReson cancelReson = cancelDeliveryReason.getReasonList().get(0);
                    String s = APIFactory.getOrderAPI().cancelLogisticsByWmOrderId(systemParam, String.valueOf(cancelReson.getCode()), cancelReson.getContent(), order.getAppPoiCode(), Long.valueOf(order.getPlatOrderId()));
                    logger.info("取消跑腿返回" + s);
                    if ("ok".equals(s)) {
                        logger.info("取消跑腿成功");
                        order.setMtptId(null);
                    }
                }

            } catch (ApiOpException e) {
                logger.error(e.getMsg(), e);
            } catch (ApiSysException e) {
                logger.error(e.getMessage(), e);
            }
        }

        if (order.getDeliveryType() != DeliveryType.SHUFENG_OPEN && StringUtils.isNotBlank(order.getSfPeisongId())) {
            CancelOrderReq req = new CancelOrderReq();
            req.setOrderId(order.getSfPeisongId());
            CancelOrderRes res = shunfengClent.execute(req);
            if (res.getErrorCode() == 0) {
                logger.info("取消顺丰聚合成功");
                order.setSfPeisongId(null);
            } else {
                throw new BizException("取消订单错误，" + res.getErrorMsg());
            }
        }

        if (order.getDeliveryType() != DeliveryType.UU_OPEN && StringUtils.isNotBlank(order.getUuPeisongId())) {
            logger.info("取消聚合UU配送");
            //uu配送订单
            Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
            CancelUuRequest cancelUuRequest = new CancelUuRequest();
            cancelUuRequest.setOrder_code(order.getUuPeisongId());
            cancelUuRequest.setReason("取消配送");
            logger.info(cancelUuRequest.toString());
            UuAccountType uuAccountType = UuAccountType.TOTAL;
            if (store.getUuAcountType() != null) {
                uuAccountType = store.getUuAcountType();
            }
            uupt.src.com.uupt.openapi.response.CancelOrderResponse cancelOrderResponse = cancelUuRequest.execute(uuAccountType);
            if (!"ok".equals(cancelOrderResponse.getReturn_code())) {
                logger.error("取消订单错误" + cancelOrderResponse.getReturn_msg());
            } else {
                logger.info("取消uu聚合成功");
                order.setUuPeisongId(null);
            }
        }

        if (order.getDeliveryType() != DeliveryType.SS_OPEN && StringUtils.isNotBlank(order.getSsPeisongId())) {
            CancelRequest request = new CancelRequest();
            request.setIssOrderNo(order.getSsPeisongId());
            CancelResponse response = shanSongClient.request(request);
            if (response.getStatus() != 200) {
                logger.error("取消订单错误，" + response.getMsg());
            } else {
                logger.info("取消闪送聚合成功");
                order.setSsPeisongId(null);
            }
        }

        if (order.getDeliveryType() != DeliveryType.DD_OPEN && StringUtils.isNotBlank(order.getDdPeisongId())) {
            logger.info("取消订单达达聚合配送" + order.getId());
            GetReasonRequest reasonRequest = new GetReasonRequest();
            GetReasonResponse response = daDaClient.request(reasonRequest);
            List<GetReasonResponse.Result> resultList = response.getResult();
            Integer reasonId = 0;
            String reason = "需要取消配送";
            for (GetReasonResponse.Result result : resultList) {
                if (result.getReason().contains("其他")) {
                    reasonId = result.getId();
                    reason = result.getReason();
                    break;
                }
            }
            if (reasonId == 0) {
                reasonId = resultList.get(resultList.size() - 1).getId();
            }
            dada.com.request.CancelOrderRequest request = new dada.com.request.CancelOrderRequest();
            request.setOrderId(order.getId().toString());
            request.setCancelReasonId(reasonId.toString());
            request.setCancelReason(reason);
            dada.com.response.CancelOrderResponse response1 = daDaClient.request(request);
            logger.info(order.getId() + "取消达达配送返回" + response1.toString());
            if (response1.getCode() == 0) {
                logger.info("取消达达聚合成功");
                order.setDdPeisongId(null);
            } else {
                logger.error("取消达达订单错误，" + response1.getMsg());
            }
        }

        if (order.getDeliveryType() != DeliveryType.MEITUAN_OPEN && StringUtils.isNotBlank(order.getMtPeisongId())) {

            CancelOrderRequest req = new CancelOrderRequest();
            if (order.getPsCancelReasonId() == null) {
                req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(199));
            } else {
                if (CancelOrderReasonId.findByCode(order.getPsCancelReasonId()) == null) {
                    req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(199));
                } else {
                    req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(order.getPsCancelReasonId()));
                }

            }
            if (order.getCancelReason() == null || order.getCancelReason() == "") {
                req.setCancelReason("顾客取消");
            } else {
                req.setCancelReason(order.getCancelReason());
            }
            req.setDeliveryId(order.getPsDeliveryId());
            req.setMtPeisongId(order.getMtPeisongId());
            CancelOrderResponse res = peisongClient.execute(req);
            if (!res.getCode().equals("0")) {
                logger.error("取消海葵聚合配送失败" + res.getMessage());
            } else {
                logger.info("取消海葵聚合配送成功");
                order.setMtPeisongId(null);
                order.setPsDeliveryId(null);
            }

        }


    }

    @Override
    public void confirmOrderByPlat(Plat plat, String platOrderId) {
        logger.info("进行订单确认：" + platOrderId);
        Order order = orderRepository.findByPlatAndPlatOrderId(plat, platOrderId);
        logger.info("当前订单状态：：" + order.getStatus().getTitle());
        if (order.getStatus() != OrderStatus.WAIT_MERCHANT_CONFIRM
                && order.getStatus() != OrderStatus.MERCHANT_CONFIRMED) {
            logger.info("当前订单的状态不是WAIT_MERCHANT_CONFIRM状态，订单状态：" + order.getStatus().name());
            return;
        }
        order.setStatus(OrderStatus.MERCHANT_CONFIRMED);
        order.setUpdateTime(new Date());
        orderRepository.save(order);
        logger.info("发送订单确认消息");
        //京东的订单先不发单,只打印
        if (plat != Plat.JDDJ) {
            kafkaTemplate.send(KafkaConstants.TOPIC_ORDER_MERCHANT_CONFIRMED, JSON.toJSONString(order));
        } else {
            Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), Plat.JDDJ);
            if (store.getJd()) {
                this.printById(order.getId());
            } else {
                kafkaTemplate.send(KafkaConstants.TOPIC_ORDER_MERCHANT_CONFIRMED, JSON.toJSONString(order));
            }

        }
        logger.info("发送订单确认消息完成");
    }

    @Override
    public boolean checkExistsByPlatAndPlatOrderId(Plat plat, String platOrderId) {
        return orderRepository.countByPlatAndPlatOrderId(plat, platOrderId) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkOrder(long orderId) {
        Order order = orderRepository.findOne(orderId);
        return checkOrder(order);
    }

    @Override
    public boolean addShunFengOrderFee(double fee, Long id) {
        fee *= 100;
        if (fee < 100) {
            throw new BizException("小费不能小于一元");
        }
        Order order = orderRepository.findOne(id);
        StoreUser storeUser = order.getStore().getStoreUser();

        if (order.getDeliveryStatus() == DeliveryStatus.WAIT_SCHEDULE) {
            AddOrderFeeReq req = new AddOrderFeeReq();
            req.setOrderId(order.getSfPeisongId());
            req.setGratuityFee((int) fee);
            req.setOrderType(1);
            req.setShopId(storeUser.getSfpsShopId());
            req.setShopType(1);
            AddOrderFeeRes res = shunfengClent.execute(req);
            if (res.getErrorCode() == 0) {
                logger.info(order.getId() + "订单加小费成功");
                return true;
            } else {
                logger.error(res.getErrorMsg());
                return false;
            }
        } else {
            throw new BizException("该订单状态不允许加小费");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addCansun(float fee, Long id, CansunType type) {
        Order order = orderRepository.findOne(id);
        if (order.getBizStatus() == OrderBizStatus.SETTLED) {
            if (order.getSettlementSheetDetail() != null && order.getSettlementSheetDetail().getStatus() == SettlementSheetDetailStatus.SETTLED) {
                throw new BizException("当日结算单已经结算，无法添加餐损");
            }
        }
        if (fee < 0) {
            throw new BizException("餐损金额必须大于零小于三百，无法添加餐损");
        }

        if (fee > 300) {
            throw new BizException("超过餐损上限，无法添加");
        }

        order.setCansun(fee);
        order.setCansunType(type);
        orderRepository.save(order);
        if (order.getSettlementSheetDetail() != null && order.getSettlementSheetDetail().getStatus() == SettlementSheetDetailStatus.WAIT_SETTLE) {
            settlementService.recheckSettlementSheetDetail(order.getSettlementSheetDetail().getId());
        }
        return true;
    }

    @Override
    public void updateJddjOrderStatus(JdOrderInfo orderInfo) {
        logger.info("京东达达配送信息" + orderInfo.toString());
        if (orderInfo.getDadaTake() == null) {
            return;
        }
        Order order = orderRepository.findByPlatAndPlatOrderId(Plat.JDDJ, orderInfo.getPlatOrderId());
        order.setDadaAccept(orderInfo.getDadaTake() == null ? false : orderInfo.getDadaTake());
        if (orderInfo.getPhone() != null) {
            order.setShipperPhone(orderInfo.getPhone());
        }
        orderRepository.save(order);
    }

    @Override
    public boolean sendWanteOrder(Long id) {
        Order order = orderRepository.findOne(id);
        OrderSendReq orderSendReq = new OrderSendReq();
        orderSendReq.setOrderId(Integer.valueOf(order.getPlatOrderId()));
        OrderSendRes execute = wanteClient.execute(orderSendReq);
        if (execute != null && execute.getId() != null) {
            logger.info("发货成功");
            return true;
        } else {
            logger.error("发货失败");
            return false;
        }
    }

    @Override
    public List<OrderDTO> fetchTop20ValidLatestOrder() {
        List<Order> orderList = orderRepository.findTop20ByStatusNotOrderByIdDesc(OrderStatus.CANCELED);
        return orderList.stream().map(o -> {
            OrderDTO dto = new OrderDTO();
            BeanUtils.copyProperties(o, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean finishWanteOrder(Long id) {
        Order order = orderRepository.findOne(id);
        OrderConfirmReq req = new OrderConfirmReq();
        req.setOrderId(Integer.valueOf(order.getPlatOrderId()));
        OrderConfirmRes execute = wanteClient.execute(req);
        if (execute != null && execute.getId() != null) {
            logger.info("更新确认订单到客户端成功");
            return true;
        } else {
            logger.error("更新确认订单到客户端失败");
            return false;
        }
    }

    @Override
    public List<OrderDTO> getWaitDeliveryOrder() {
        List<Order> orders = orderRepository.findByStatus(OrderStatus.MERCHANT_CONFIRMED);
        List<OrderDTO> remind = new ArrayList<>();
        for (Order order : orders) {
            if (storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat()).getDeliverySelf()) {
                continue;
            }
            if (order.getDeliveryType() == DeliveryType.MEITUAN_OPEN || order.getDeliveryType() == DeliveryType.UU_OPEN || order.getDeliveryType() == DeliveryType.SS_OPEN) {
                if (order.getDeliveryStatus() == DeliveryStatus.WAIT_DELIVERY) {
                    //待配送的订单
                    if (order.getExpectedDeliveryTime() - System.currentTimeMillis() / 1000 < 30 * 60) {
                        remind.add(this.toDTO(order, true));
                    }
                } else if (order.getDeliveryStatus() == DeliveryStatus.CANCELED) {
                    //被取消的订单
                    remind.add(this.toDTO(order, true));
                } else if (order.getDeliveryStatus() == DeliveryStatus.OUTRANGE) {
                    //超范围
                    remind.add(this.toDTO(order, true));
                } else if (order.getDeliveryStatus() == DeliveryStatus.WAIT_SCHEDULE) {
                    //待调度的订单
                    if (order.getExpectedDeliveryTime() - System.currentTimeMillis() / 1000 < 20 * 60) {
                        remind.add(this.toDTO(order, true));
                    }
                }
            }
        }
        return remind;
    }

    StoreUserFoodSkuDTO getSkuDto(String skuId, List<StoreUserFoodSkuDTO> list) {
        if (list == null) {
            return null;
        }
        for (StoreUserFoodSkuDTO sku : list) {
            if (sku.getFoodSkuId().equals(skuId)) {
                return sku;
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkOrder(Order order) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
        List<StoreUserFood> list = storeUserFoodRepository.findByStoreUserIdAndFoodCodeIn(order.getStore().getStoreUser().getId(),
                orderDetails.stream().map(o -> o.getFoodCode()).collect(Collectors.toList()));
        Map<String, StoreUserFood> storeUserFoodMap = new HashMap<>(10);
        Map<Long, StoreUserFood> storeUserFoodMapByFoodId = new HashMap<>(10);
        for (StoreUserFood storeUserFood : list) {
            storeUserFoodMap.put(storeUserFood.getFood().getCode(), storeUserFood);
            storeUserFoodMapByFoodId.put(storeUserFood.getFood().getId(), storeUserFood);
        }
        float costTotal = 0, costRefund = 0;
        boolean checkFlag = true;
        for (OrderDetail detail : orderDetails) {
            StoreUserFood storeUserFood = storeUserFoodMap.get(detail.getFoodCode());
            if (storeUserFood == null) {
                detail.setRemark("商品不存在，code：" + detail.getFoodCode());
                detail.setOk(false);
                checkFlag = false;
                continue;
            }
            Food food = foodRepository.findOne(storeUserFood.getFood().getId());
            detail.setFood(food);
            detail.setUnit(food.getQuoteUnit());
            List<StoreUserFoodSkuDTO> skuDTOList = storeUserFoodSkuService.getByStoreUserFoodId(storeUserFood.getId());
//            List<FoodSku> foodSkuList = FoodHelper.parseFoodSkuList(food.getSkuJson());
//            FoodSku sku;
            StoreUserFoodSkuDTO sku;
            if (food.getIsSp() == 1) {
                //如果是标品，sku就是默认的sku
                sku = skuDTOList.get(0);
            } else {
                //sku = FoodHelper.findSku(detail.getSkuId(), foodSkuList);
                sku = getSkuDto(detail.getSkuId(), skuDTOList);
            }
            if (sku == null) {
                detail.setRemark("商品SKU不存在，SKU：" + sku);
                checkFlag = false;
                detail.setOk(false);
                continue;
            }
            /**
             * 有的商品是不存在sku的
             * */
            if (detail.getQuotePrice() == null || detail.getQuotePrice() == 0) {
                detail.setQuotePrice(sku.getInputPrice());
            }
            if (StringUtils.isEmpty(detail.getSpec())) {
                detail.setSpec(sku.getSpec());
            }
            StoreUserFood suf = storeUserFoodMapByFoodId.get(detail.getFood().getId());
            if (suf != null) {
                detail.setFoodSupplier(suf.getFoodSupplier());
            }
            detail.setQuoteUnitRatio(1f);

            if (StringUtils.isNotBlank(sku.getWarehouseIds())) {
                String warehouseIds = sku.getWarehouseIds();
                String[] split = warehouseIds.split(",");
                List<String> names = new ArrayList<>();
                for (String s : split) {
                    Warehouse warehouse = warehouseRepository.findOne(Long.valueOf(s));
                    names.add(warehouse.getName());
                }
                detail.setWarehouseName(String.join("、", names));
            } else {
                detail.setWarehouseName("暂未绑定库位");
            }


            detail.setCostTotal(detail.getQuotePrice() * detail.getQuantity());
            detail.setTotal(detail.getPrice() * detail.getQuantity() + detail.getBoxNum() * detail.getBoxPrice());
            detail.setRefund((detail.getPrice() + detail.getBoxNum() * detail.getBoxPrice()) * detail.getRefundQuantity());
            detail.setCostRefund(detail.getQuotePrice() * detail.getRefundQuantity());
            costTotal += detail.getCostTotal();
            costRefund += detail.getCostRefund();
            detail.setOk(true);
        }
        order.setCostTotal(costTotal);
        order.setCostRefund(costRefund);
        if (checkFlag && (order.getBizStatus()
                == OrderBizStatus.WAIT_CHECK || order.getBizStatus()
                == OrderBizStatus.WAIT_SETTLE)) {
            order.setBizStatus(OrderBizStatus.WAIT_SETTLE);
        } else {
            order.setBizStatus(OrderBizStatus.WAIT_CHECK);
        }
        orderRepository.save(order);
        return checkFlag;
    }


    @Override
    public Paging<OrderDTO> search(SearchParam param) {
        Specification<Order> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (param.getFentchStoreUser() == null || param.getFentchStoreUser()) {
                if (query.getResultType() != Long.class) {
                    root.fetch("store", JoinType.LEFT)
                            .fetch("storeUser", JoinType.LEFT);
                }
            }

            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotEmpty(param.getPlatOrderId())) {
                expressions.add(cb.equal(root.get("platOrderId"), param.getPlatOrderId()));
            }
            if (param.getSettlementSheetDetailId() != null) {
                expressions.add(cb.equal(root.get("settlementSheetDetail").get("id"), param.getSettlementSheetDetailId()));
            }
            if (param.getStoreId() != null) {
                expressions.add(cb.equal(root.get("store").get("id"), param.getStoreId()));
            }
            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("store").get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (param.getFoodSupplierId() != null) {
                /**
                 * 使用subquery
                 * */
                Subquery<Long> sufSubquery = query.subquery(Long.class);
                Root sufRoot = sufSubquery.from(OrderDetail.class);
                Predicate subPredicate = cb.conjunction();
                List<Expression<Boolean>> subExpressions = subPredicate.getExpressions();
                subExpressions.add(cb.equal(sufRoot.get("foodSupplier").get("id"), param.getFoodSupplierId()));
                subExpressions.add(cb.equal(sufRoot.get("order").get("id"), root.get("id")));
                sufSubquery.select(sufRoot.get("order").get("id")).where(subPredicate);
                expressions.add(cb.exists(sufSubquery));
            }
            if (param.getDaySeq() != null) {
                expressions.add(cb.equal(root.get("daySeq"), param.getDaySeq()));
                if (param.getDaySeq() != null) {
                    expressions.add(cb.equal(root.get("daySeq"), param.getDaySeq()));
                }
                if (param.getPlat() != null) {
                    expressions.add(cb.equal(root.get("plat"), param.getPlat()));
                }
            }
            if (param.getPlat() != null) {
                expressions.add(cb.equal(root.get("plat"), param.getPlat()));
            }

            if (param.getBizStatus() != null) {
                expressions.add(cb.equal(root.get("bizStatus"), param.getBizStatus()));
            }
            if (param.getOpened() != null) {
                expressions.add(cb.equal(root.get("store").get("storeUser").get("opened"), param.getOpened()));
            }
            if (param.getDeliveryType() != null) {
                expressions.add(cb.equal(root.get("deliveryType"), param.getDeliveryType()));
            }
            if (param.getDeliveryStatus() != null) {
                expressions.add(cb.equal(root.get("deliveryStatus"), param.getDeliveryStatus()));
            }
            if (param.getRefundStatus() != null) {
                expressions.add(cb.equal(root.get("refundStatus"), param.getRefundStatus()));
            }
            if (param.getStartDate() != null) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("createTime"),
                        DateUtils.truncate(param.getStartDate(), Calendar.DATE)));
            }
            if (param.getEndDate() != null) {
                expressions.add(cb.lessThan(root.get("createTime"),
                        DateUtils.truncate(DateUtils.addDays(param.getEndDate(), 1), Calendar.DATE)));
            }
            if (param.getStartExpectedDeliveryTime() != null) {
                expressions.add(cb.ge(root.get("expectedDeliveryTime"), param.getStartExpectedDeliveryTime()));
            }
            if (param.getEndExpectedDeliveryTime() != null) {
                expressions.add(cb.le(root.get("expectedDeliveryTime"), param.getEndExpectedDeliveryTime()));
            }
            if (param.getDate() != null) {
                Date startTime = DateUtils.truncate(param.getDate(), Calendar.DATE);
                Date endTime = DateUtils.addDays(startTime, 1);
                expressions.add(cb.between(root.get("createTime"), startTime, endTime));
            }
            if (param.getStatus() != null) {
                expressions.add(cb.equal(root.get("status"), param.getStatus()));
            } else if (param.getSettlementSheetDetailId() == null) {
                expressions.add(cb.notEqual(root.get("status"), OrderStatus.CANCELED));
            }
            if (StringUtils.isNotEmpty(param.getStoreUserName())) {
                expressions.add(cb.like(root.get("store").get("storeUser").get("name"), "%" + param.getStoreUserName() + "%"));
            }
            if (param.getStatusType() != null) {
                List<OrderStatus> statusList = new ArrayList<>();
                switch (param.getStatusType()) {
                    case CANCELED:
                        statusList.add(OrderStatus.CANCELED);
                        break;
                    case FINISHED:
                        statusList.add(OrderStatus.FINISHED);
                        break;
                    case RESERVE:
                        statusList.add(OrderStatus.PAID);
                        statusList.add(OrderStatus.MERCHANT_CONFIRMED);
                        statusList.add(OrderStatus.WAIT_MERCHANT_CONFIRM);
                        statusList.add(OrderStatus.SHIPPING);
                        statusList.add(OrderStatus.SHIPPED);
                        expressions.add(cb.gt(root.get("deliveryTime"), 0));
                        break;
                    case PROCESSING:
                        statusList.add(OrderStatus.PAID);
                        statusList.add(OrderStatus.MERCHANT_CONFIRMED);
                        statusList.add(OrderStatus.WAIT_MERCHANT_CONFIRM);
                        statusList.add(OrderStatus.SHIPPING);
                        statusList.add(OrderStatus.SHIPPED);
                }
                if (statusList.size() > 0) {
                    expressions.add(cb.in(root.get("status")).value(statusList));
                }
            }
            if (param.getCansun() != null && param.getCansun()) {
                expressions.add(cb.greaterThan(root.get("cansun"), 0));
            }
            return predicate;
        };
        Sort sort;
        if (param.getStatusType() != null && param.getStatusType() == OrderStatusType.RESERVE) {
            sort = new Sort(Sort.Direction.ASC, "deliveryTime");
        } else {
            sort = new Sort(Sort.Direction.DESC, "id");
        }
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<Order> page = orderRepository.findAll(specification, pageable);
        Paging<OrderDTO> paging = PagingHelper.of(page, item -> toDTO(item, param.getFetchDetail()), param.getPage(), param.getPageSize());
        if (param.getFetchDetail() != null && param.getFetchDetail()) {
            List<OrderDTO> orderList = paging.getResults();
            if (orderList.size() > 0) {
                List<Long> orderIdList = new ArrayList<>();
                Map<Long, OrderDTO> orderMap = new HashMap<>();
                for (OrderDTO order : orderList) {
                    orderIdList.add(order.getId());
                    orderMap.put(order.getId(), order);
                    order.setOrderDetailList(new ArrayList<>());
                }
                OrderDetailService.SearchParam sp = new OrderDetailService.SearchParam();
                sp.setOrderIdList(orderIdList);
                sp.setPageable(false);
                if (param.getFoodSupplierId() != null) {
                    sp.setFoodSupplierId(param.getFoodSupplierId());
                }
                Paging<OrderDetailDTO> detailPaging = orderDetailService.search(sp);
                for (OrderDetailDTO detail : detailPaging.getResults()) {
                    OrderDTO order = orderMap.get(detail.getOrder().getId());
                    order.getOrderDetailList().add(detail);
                }
            }
        }
        return paging;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean receiptOrder(long orderId) {
        Order order = orderRepository.findOne(orderId);
        Assert.isTrue(order != null, "订单不存在");
        boolean flag = false;
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            SystemParam param = null;
            if (order.getPlat() == Plat.MEITUAN) {
                param = meituanWaimaiService.getSystemParam();
            } else {
                param = clbmWaiMaiService.getSystemParam();
            }
            String result;
            try {
                result = APIFactory.getOrderAPI().orderConfirm(param, Long.valueOf(order.getPlatOrderId()));
            } catch (Exception e) {
                logger.error("接单失败", e);
                return false;
            }
            if (result.equals("ok")) {
                flag = true;
            } else {
                logger.error("接单失败" + result);
                return false;
            }
        } else if (order.getPlat() == Plat.ELE) {
            OrderConfirmRequest req = new OrderConfirmRequest();
            req.setOrderId(order.getPlatOrderId());
            OrderConfirmResponse res = eleClient.request(req);
            if (res.getErrno() == 0) {
                flag = true;
            } else {
                logger.error("接单失败", res.getError());
                return false;
            }
        } else if (order.getPlat() == Plat.JDDJ) {
            Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), Plat.JDDJ);
            OrderAcceptReq req = new OrderAcceptReq();
            req.setOrderId(order.getPlatOrderId());
            req.setOperator("王小菜");
            req.setIsAgreed(true);
            OrderAcceptRes res;
            if (store.getJd()) {
                res = jingdongClient.request(req);
            } else {
                res = jingdongzClient.request(req);
            }
            if (res.getCode().equals("0") && res.getData().getCode().equals("0")) {
                flag = true;
            } else {
                logger.error("京东接单失败");
                return false;
            }

        }
        orderRepository.updateStatusById(order.getId(), OrderStatus.MERCHANT_CONFIRMED.name());
        return flag;
    }

    @Override
    public boolean cancelOrder(OrderCancelParam param) {
        Order order = orderRepository.findOne(param.getId());
        Assert.isTrue(order != null, "订单不存在");
        boolean flag = false;
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            SystemParam systemParam = null;
            if (order.getPlat() == Plat.MEITUAN) {
                systemParam = meituanWaimaiService.getSystemParam();
            } else {
                systemParam = clbmWaiMaiService.getSystemParam();
            }
            String result;
            try {
                result = APIFactory.getOrderAPI().orderCancel(systemParam, Long.valueOf(order.getPlatOrderId()),
                        param.getReason(), param.getReasonCode());
            } catch (Exception e) {
                logger.error("订单取消失败", e);
                return false;
            }
            if (result.equals("ok")) {
                flag = true;
            } else {
                logger.error("订单取消失败" + result);
                return false;
            }
        } else if (order.getPlat() == Plat.WANTE) {
            OrderCancelReq req = new OrderCancelReq();
            req.setOrderId(Integer.valueOf(order.getPlatOrderId()));
            OrderCancelRes execute = wanteClient.execute(req);
            if (execute != null && execute.getId() != null) {
                logger.info("取消订单成功");
                return true;
            } else {
                logger.error("订单取消失败");
                return false;
            }
        }
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        return flag;
    }

    @Override
    public void refundOrder(Plat plat, String platOrderId, OrderRefundStatus status, String eleRefundId) {
        //全部退款  将订单退款类型设置成全部退款
        Order order = orderRepository.findByPlatAndPlatOrderId(plat, platOrderId);
        Assert.isTrue(order != null, "订单不存在");
        order.setRefundStatus(status);
        order.setRefundType(OrderRefundType.ALL);
        switch (status) {
            case PENDING:
                if (plat == Plat.ELE) {
                    if (StringUtil.isNotBlank(eleRefundId)) {
                        order.setEleRefundId(eleRefundId);
                    }
                }
                break;
            case CANCELED:
                order.setRefundMoney(0f);
                break;
            case REJECT:
                order.setRefundMoney(0f);
                break;
            case AGREE:
                order.setStatus(OrderStatus.CANCELED);
        }
        orderRepository.save(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void partRefund(OrderPartRefundParam param) {
        if (param.getStatus() != OrderRefundStatus.AGREE) {
            return;
        }
        Order order = orderRepository.findByPlatAndPlatOrderId(param.getPlat(), param.getPlatOrderId());
        Assert.isTrue(order != null, "订单不存在");
//        if (order.getRefundStatus() != null) {
//            return;
//        }
        order.setRefundStatus(param.getStatus());
        switch (param.getStatus()) {
            case PENDING:
                break;
            case CANCELED:
                order.setRefundMoney(0f);
                break;
            case REJECT:
                order.setRefundMoney(0f);
                break;
            case AGREE:
                order.setRefundMoney(param.getRefundMoney());
        }
        List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getId());
        Map<String, OrderDetail> orderDetailMap = new HashMap<>();
        Map<String, OrderDetail> orderDetailNameMap = new HashMap<>();
        Map<String, OrderDetail> orderDetailSpecMap = new HashMap<>();
        for (OrderDetail detail : details) {
            orderDetailMap.put(detail.getFoodCode() + "-" + detail.getSkuId(), detail);
            orderDetailSpecMap.put(detail.getFoodCode() + "-" + detail.getSpec(), detail);
            orderDetailNameMap.put(detail.getFoodName(), detail);
        }
        float costRefund = 0f;
        for (RefundFood rf : param.getFoodList()) {
            OrderDetail detail = orderDetailSpecMap.get(rf.getAppFoodCode() + "-" + rf.getSpec());
            if (detail == null) {
                detail = orderDetailMap.get(rf.getAppFoodCode() + "-" + rf.getSkuId());
            }
            if (detail == null) {
                detail = orderDetailNameMap.get(rf.getFoodName());
            }
            if (detail.getQuotePrice() == null) {
                detail.setRemark("报价不存在");
                orderDetailRepository.save(detail);
                continue;
            }
            double count = rf.getCount();
            detail.setRefundQuantity((float) count);
            detail.setRefund(rf.getRefundPrice());
            if (rf.getType() != null && rf.getType() == 1) {
                detail.setCostRefund((detail.getRefund() / detail.getTotal()) * detail.getCostTotal());
                //detail.setCostRefund((detail.getRefund() / detail.getPrice()) * detail.getQuotePrice() * detail.getRefundQuantity());
            } else {
                detail.setCostRefund(detail.getRefundQuantity() * detail.getQuotePrice());
            }

            if (detail.getCostTotal() - detail.getCostRefund() < 0) {
                detail.setCostRefund(detail.getCostTotal());
            }


            costRefund += detail.getCostRefund();
            orderDetailRepository.save(detail);
        }
        order.setCostRefund(costRefund);
        orderRepository.save(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void partRefunding(OrderPartRefundParam param) {
//        if (param.getStatus() != OrderRefundStatus.PENDING) {
//            return;
//        }
        Order order = orderRepository.findByPlatAndPlatOrderId(param.getPlat(), param.getPlatOrderId());
        Assert.isTrue(order != null, "订单不存在");

        order.setRefundStatus(param.getStatus());
        order.setRefundType(OrderRefundType.PART);

        if (param.getStatus() == OrderRefundStatus.PENDING) {
            if (param.getPlat() == Plat.ELE) {
                if (StringUtil.isNotBlank(param.getEleRefundId())) {
                    order.setEleRefundId(param.getEleRefundId());
                }
            }
            List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getId());
            Map<String, OrderDetail> orderDetailMap = new HashMap<>();
            Map<String, OrderDetail> orderDetailNameMap = new HashMap<>();
            Map<String, OrderDetail> orderDetailSpecMap = new HashMap<>();
            for (OrderDetail detail : details) {
                orderDetailMap.put(detail.getFoodCode() + "-" + detail.getSkuId(), detail);
                orderDetailSpecMap.put(detail.getFoodCode() + "-" + detail.getSpec(), detail);
                orderDetailNameMap.put(detail.getFoodName(), detail);
            }
            for (RefundFood rf : param.getFoodList()) {
                OrderDetail detail = orderDetailSpecMap.get(rf.getAppFoodCode() + "-" + rf.getSpec());
                if (detail == null) {
                    detail = orderDetailMap.get(rf.getAppFoodCode() + "-" + rf.getSkuId());
                }
                if (detail == null) {
                    detail = orderDetailNameMap.get(rf.getFoodName());
                }
                if (detail.getQuotePrice() == null) {
                    detail.setRemark("报价不存在");
                    orderDetailRepository.save(detail);
                    continue;
                }
//            double count = rf.getCount();
//            detail.setRefundQuantity((float) count);
//            detail.setRefund(rf.getRefundPrice());
//            detail.setCostRefund(detail.getRefundQuantity() * detail.getQuotePrice());
//            costRefund += detail.getCostRefund();
                detail.setRefunding(true);
                orderDetailRepository.save(detail);
            }
        }


        //order.setCostRefund(costRefund);
        orderRepository.save(order);
    }

    @Override
    public boolean agreeRefund(long orderId, String reason, String remarks, Double money) {
        Order order = orderRepository.findOne(orderId);
        Assert.isTrue(order != null, "订单不存在");
        boolean flag = false;
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            String result;
            SystemParam param = null;
            if (order.getPlat() == Plat.MEITUAN) {
                param = meituanWaimaiService.getSystemParam();
            } else {
                param = clbmWaiMaiService.getSystemParam();
            }
            try {
                result = APIFactory.getOrderAPI().orderRefundAgree(param, Long.valueOf(order.getPlatOrderId()), reason);
            } catch (Exception e) {
                logger.error("同意退货失败", e);
                return false;
            }
            if (result.equals("ok")) {
                flag = true;
            } else {
                logger.error("同意退货失败" + result);
                return false;
            }
        } else if (order.getPlat() == Plat.WANTE) {

            OrderRefundConfirmReq req = new OrderRefundConfirmReq();
            req.setOrderId(Integer.valueOf(order.getPlatOrderId()));
            req.setRemarks(remarks);
            if (money != null) {
                req.setMoney(money.floatValue());
            }

            wanteClient.execute(req);
            flag = true;

        } else if (order.getPlat() == Plat.JDDJ) {
            Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), Plat.JDDJ);
            OrderCancelOperateReq req = new OrderCancelOperateReq();
            req.setIsAgreed(true);
            req.setOrderId(order.getPlatOrderId());
            req.setOperator("王小菜");
            OrderCancelOperateRes res;
            if (store.getJd()) {
                res = jingdongClient.request(req);
            } else {
                res = jingdongzClient.request(req);
            }
            if (res.getCode().equals("0") && res.getData().getCode().equals("0")) {
                flag = true;
            } else {
                flag = false;
            }
        } else if (order.getPlat() == Plat.ELE) {
            if (order.getRefundType() == OrderRefundType.ALL) {
                RefundAgreeRequest refundAgreeRequest = new RefundAgreeRequest();
                refundAgreeRequest.setOrderId(order.getPlatOrderId());
                refundAgreeRequest.setRefundOrderId(order.getEleRefundId());
                RefundAgreeResponse response = eleClient.request(refundAgreeRequest);
                logger.info("同意饿了么全部退款返回" + response.getData());
                flag = response.getData();
            }

        }
        return flag;
    }

    @Override
    public boolean rejectRefund(long orderId, String reason) {
        Order order = orderRepository.findOne(orderId);
        Assert.isTrue(order != null, "订单不存在");
        boolean flag = false;
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            SystemParam param = null;
            if (order.getPlat() == Plat.MEITUAN) {
                param = meituanWaimaiService.getSystemParam();
            } else {
                param = clbmWaiMaiService.getSystemParam();
            }
            String result;
            try {
                result = APIFactory.getOrderAPI().orderRefundReject(param, Long.valueOf(order.getPlatOrderId()), reason);
            } catch (Exception e) {
                logger.error("驳回退货失败", e);
                return false;
            }
            if (result.equals("ok")) {
                flag = true;
            } else {
                logger.error("驳回退货失败" + result);
                return false;
            }
        } else if (order.getPlat() == Plat.WANTE) {
            OrderRefundRejectReq req = new OrderRefundRejectReq();
            req.setOrderId(Integer.valueOf(order.getPlatOrderId()));
            req.setRemarks("驳回退货");
            wanteClient.execute(req);
            this.syncPlatOrder(Plat.WANTE, order.getPlatOrderId(), true);
            flag = true;

        } else if (order.getPlat() == Plat.JDDJ) {
            Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), Plat.JDDJ);
            OrderCancelOperateReq req = new OrderCancelOperateReq();
            req.setOperator("王小菜");
            req.setOrderId(order.getPlatOrderId());
            req.setIsAgreed(false);
            req.setRemark("驳回退货");
            OrderCancelOperateRes res;
            if (store.getJd()) {
                res = jingdongClient.request(req);
            } else {
                res = jingdongzClient.request(req);
            }
            if (res.getCode().equals("0") && res.getData().getCode().equals("0")) {
                flag = true;
            }
        } else if (order.getPlat() == Plat.ELE) {
            RefundRejectRequest rejectRequest = new RefundRejectRequest();
            rejectRequest.setOrderId(order.getPlatOrderId());
            rejectRequest.setReason("商家拒绝");
            rejectRequest.setRefundOrderId(order.getEleRefundId());
            RefundRejectReponse reponse = eleClient.request(rejectRequest);
            logger.info("饿了么拒绝退款返回" + reponse.getData());
            flag = reponse.getData();
        }

        return flag;
    }

    @Override
    public List<OrderDetailDTO> findDetailByOrderId(long orderId) {
        Specification<OrderDetail> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (query.getResultType() != Long.class) {
                root.fetch("food", JoinType.LEFT);
            }
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.equal(root.get("order").get("id"), orderId));
            return predicate;
        };
        return orderDetailRepository.findAll(specification).stream().map(o -> {
            OrderDetailDTO dto = new OrderDetailDTO();
            dto.setWarehouseName(o.getWarehouseName());
            dto.setFoodName(o.getFoodName());
            dto.setFoodCode(o.getFoodCode());
            dto.setBoxNum(o.getBoxNum());
            dto.setBoxPrice(o.getBoxPrice());
            dto.setCostRefund(o.getCostRefund());
            dto.setCostTotal(o.getCostTotal());
            if (o.getFood() == null) {
                dto.setFoodId(-1l);
                dto.setFoodPicture("");
            } else {
                dto.setFoodId(o.getFood().getId());
                dto.setFoodPicture(o.getFood().getPicture());
            }

            dto.setId(o.getId());
            dto.setPrice(o.getPrice());
            dto.setQuantity(o.getQuantity());
            dto.setQuotePrice(o.getQuotePrice());
            dto.setRefund(o.getRefund());
            dto.setRefundQuantity(o.getRefundQuantity());
            dto.setSkuId(o.getSkuId());
            dto.setSpec(o.getSpec());
            dto.setTotal(o.getTotal());
            dto.setRefunding(o.getRefunding());
            if (o.getFoodSupplier() != null) {
                dto.setFoodSupplierId(o.getFoodSupplier().getId());
            }
            dto.setUnit(o.getUnit());

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SupplierOrderDetailGroupDTO> groupSupplierDetailByOrderId(long orderId) {
        List<OrderDetail> detailList = orderDetailRepository.findByOrderId(orderId);
        List<SupplierOrderDetailGroupDTO> list = new ArrayList<>();
        Map<Long, SupplierOrderDetailGroupDTO> groupMapByFoodSupplierId = new HashMap<>();
        for (OrderDetail detail : detailList) {
            FoodSupplier fs = detail.getFoodSupplier();
            Long foodSupplierId;
            if (fs == null) {
                foodSupplierId = 0L;
            } else {
                foodSupplierId = fs.getId();
            }
            SupplierOrderDetailGroupDTO dto = groupMapByFoodSupplierId.get(foodSupplierId);
            if (dto == null) {
                dto = new SupplierOrderDetailGroupDTO();
                if (fs != null) {
                    dto.setSupplierAddress(fs.getAddress());
                    dto.setSupplierName(fs.getName());
                    dto.setSupplierPhone(fs.getPhone());
                }
                dto.setSupplierId(foodSupplierId);
                dto.setOrderId(orderId);
                dto.setOrderDetailList(new ArrayList<>());
                list.add(dto);
                groupMapByFoodSupplierId.put(foodSupplierId, dto);
            }
            SupplierOrderDetailDTO detailDTO = toSupplierOrderDetailDTO(detail);
            dto.setRefundMoney(dto.getRefundMoney() + detailDTO.getSupplierRefund());
            dto.setTotalMoney(dto.getTotalMoney() + detailDTO.getSupplierTotal());
            dto.setRemainMoney(dto.getRemainMoney() + detailDTO.getSupplierIncome());
            dto.setMerchantIncome(dto.getMerchantIncome() + detailDTO.getMerchantIncome());
            dto.getOrderDetailList().add(detailDTO);
        }
        return list;
    }

    @Override
    public List<SupplierOrderDetailDTO> findDetailByOrderIdAndFoodSupplierId(long orderId, long foodSupplierId) {
        Specification<OrderDetail> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (query.getResultType() != Long.class) {
                root.fetch("food", JoinType.LEFT);
            }
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.equal(root.get("order").get("id"), orderId));
            expressions.add(cb.equal(root.get("foodSupplier").get("id"), foodSupplierId));
            return predicate;
        };
        return orderDetailRepository.findAll(specification).stream().map(o -> toSupplierOrderDetailDTO(o)).collect(Collectors.toList());
    }

    private SupplierOrderDetailDTO toSupplierOrderDetailDTO(OrderDetail od) {
        SupplierOrderDetailDTO dto = new SupplierOrderDetailDTO();
        dto.setFoodName(od.getFoodName());
        dto.setFoodCode(od.getFoodCode());
        dto.setBoxNum(od.getBoxNum());
        dto.setBoxPrice(od.getBoxPrice());
        dto.setCostRefund(od.getCostRefund());
        dto.setCostTotal(od.getCostTotal());
        dto.setFoodId(od.getFood().getId());
        dto.setId(od.getId());
        dto.setPrice(od.getPrice());
        dto.setQuantity(od.getQuantity());
        dto.setQuoteUnit(od.getFood().getQuoteUnit());
        dto.setQuotePrice(od.getQuotePrice());
        dto.setRefund(od.getRefund());
        dto.setRefundQuantity(od.getRefundQuantity());
        dto.setSkuId(od.getSkuId());
        dto.setSpec(od.getSpec());
        dto.setTotal(od.getTotal());
        dto.setUnit(od.getUnit());
        dto.setFoodPicture(od.getFood().getPicture());
        float supplierQuotePrice = od.getSupplierQuotePrice() == null ? 0 : od.getSupplierQuotePrice();
        float quoteUnitRatio = od.getQuoteUnitRatio() == null ? 0 : od.getQuoteUnitRatio();
        dto.setSupplierQuotePrice(supplierQuotePrice);
        dto.setSupplierRefund(supplierQuotePrice * od.getRefundQuantity() * quoteUnitRatio);
        dto.setSupplierTotal(supplierQuotePrice * od.getQuantity() * quoteUnitRatio);
        dto.setSupplierIncome(dto.getSupplierTotal() - dto.getSupplierRefund());
        dto.setMerchantIncome(od.getCostTotal() - od.getCostRefund() - dto.getSupplierIncome());
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean printCancelInfo(Plat plat, String platOrderId) {
        Order order = orderRepository.findByPlatAndPlatOrderId(plat, platOrderId);
        String sn = order.getStore().getStoreUser().getFeiePrinterSn();
        if (StringUtils.isEmpty(sn)) {
            logger.info("商家未配置打印机," + order.getPlatOrderId() + " " + order.getStore().getStoreUser().getName());
            return true;
        }
        PrinterService.CancelInfo cancelInfo = new PrinterService.CancelInfo();
        cancelInfo.setAddress(order.getRecipientAddress());
        if (StringUtils.isEmpty(order.getCancelReason())) {
            cancelInfo.setCancelReson("未备注原因");
        } else {
            cancelInfo.setCancelReson(order.getCancelReason());
        }
        cancelInfo.setDate(DateFormatUtils.format(order.getCreateTime(), "yyyy-MM-dd HH:mm"));
        cancelInfo.setDaySeq(order.getDaySeq().toString());
        cancelInfo.setName(order.getRecipientName());
        cancelInfo.setPhone(order.getRecipientPhone());
        cancelInfo.setOrderId(order.getPlatOrderId());
        cancelInfo.setPlat(order.getPlat());
        cancelInfo.setShopName(order.getWmPoiName());
        boolean flag = printerService.print(sn, cancelInfo);
        logger.info("通知打印订单取消信息返回，flag：" + flag + "，订单：" + order.getPlatOrderId());
        return flag;
    }

    private SyncInfo getRiderLocation(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        if (order == null) {
            return null;
        }
        SyncInfo info = new SyncInfo();
        if (order.getDeliveryType() == DeliveryType.UU_OPEN) {
            GetOrderInfoRequest request = new GetOrderInfoRequest();
            request.setOrder_code(order.getUuPeisongId());
            UuAccountType uuAccountType = UuAccountType.TOTAL;
            if (order.getStore().getDeliveryType() != null) {
                uuAccountType = order.getStore().getUuAcountType();
            }
            GetOrderInfoResponse response = request.execute(uuAccountType);
            logger.info("获取到的UU骑手坐标：" + response.toString());
            String driver_lastloc = response.getDriver_lastloc();
            if (StringUtils.isNotBlank(driver_lastloc)) {
                String[] split = driver_lastloc.split(",");
                LocateInfo locateInfo = CoordinateHelper.bd09_To_Gcj02(Double.valueOf(split[0]), Double.valueOf(split[1]));
                info.setLatitude(String.valueOf(locateInfo.getLatitude()));
                info.setLongitude(String.valueOf(locateInfo.getLongitude()));
            }

            info.setPsCode("10005");
            info.setPrice(Float.valueOf(response.getOrder_price()));
            info.setPsOrderId(order.getUuPeisongId());
        } else if (order.getDeliveryType() == DeliveryType.SHUFENG_OPEN) {
            GetOrderInfoReq req = new GetOrderInfoReq();
            req.setOrderId(order.getSfPeisongId());
            GetOrderInfoRes res = shunfengClent.execute(req);
            try {
                info.setPrice(Float.valueOf(res.getResult().getTotalPayMoney() / 100));
                GetSfLocationReq req1 = new GetSfLocationReq();
                req.setOrderId(order.getSfPeisongId());
//                GetSfLocationRes res1 = shunfengClent.execute(req1);
//                if (res1.getErrorCode() == 0) {
//                    info.setLongitude(res1.getResult().getRiderLng());
//                    info.setLatitude(res1.getResult().getRiderLat());
//                } else {
//                    logger.error("获取顺丰经纬度出错" + res.getErrorMsg());
//                }
            } catch (Exception e) {
                logger.error("顺丰同步到美团出错", e);
            }
            info.setPsCode("10001");
            info.setPsOrderId(order.getSfPeisongId());
        } else if (order.getDeliveryType() == DeliveryType.DD_OPEN) {
            OrderInfoRequest request = new OrderInfoRequest();
            request.setOrderId(order.getId().toString());
            OrderInfoResponse response = daDaClient.request(request);
            if (response.getCode() == 0) {
                info.setLongitude(response.getResult().getTransporterLng());
                info.setLatitude(response.getResult().getTransporterLat());
            } else {
                logger.error("获取达达经纬度出错" + response.getMsg());
            }
            info.setPsCode("10002");
            info.setPsOrderId(order.getDdPeisongId());

        } else if (order.getDeliveryType() == DeliveryType.SS_OPEN) {
            GetStatusRequest request = new GetStatusRequest();
            request.setIssOrderNo(order.getSsPeisongId());
            request.setThirdOrderNo(order.getSsPeisongId());
            GetStatusResponse response = shanSongClient.request(request);
            info.setPsOrderId(order.getSsPeisongId());
            info.setPsCode("10003");
            info.setPrice(Float.valueOf(response.getData().getTotalFeeAfterSave() / 100));
        }

        return info;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean printById(long id) {
        Order order = orderRepository.findOne(id);
        StoreUser storeUser = order.getStore().getStoreUser();
        String sn = storeUser.getFeiePrinterSn();
        boolean fe = true;
        boolean yl = true;
        boolean zw = true;
        boolean xx = true;
        if (StringUtils.isEmpty(storeUser.getDevideid())) {
            logger.info("商家未配置中午打印机," + order.getPlatOrderId() + " " + order.getStore().getStoreUser().getName());
            zw = false;
        }
        if (StringUtils.isEmpty(sn)) {
            logger.info("商家未配置飞蛾打印机," + order.getPlatOrderId() + " " + order.getStore().getStoreUser().getName());
            fe = false;
        }

        if (StringUtils.isEmpty(storeUser.getMachineCode())) {
            logger.info("商家未配置易联云打印机," + order.getPlatOrderId() + " " + order.getStore().getStoreUser().getName());
            yl = false;
        }

        if (StringUtils.isEmpty(storeUser.getXyySn())) {
            logger.info("商家未配置芯烨云打印机," + order.getPlatOrderId() + " " + order.getStore().getStoreUser().getName());
            xx = false;
        }

        if (!fe && !yl && !zw && !xx) {
            logger.info("商家未配置任何打印机" + order.getPlatOrderId() + " " + order.getStore().getStoreUser().getName());
            return true;
        }
        List<OrderExtraParam> extra = new ArrayList<>();
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            SystemParam systemParam = null;
            if (order.getPlat() == Plat.MEITUAN) {
                systemParam = meituanWaimaiService.getSystemParam();
            } else {
                systemParam = clbmWaiMaiService.getSystemParam();
            }
            String platOrderId = order.getPlatOrderId();
            com.sankuai.meituan.waimai.opensdk.vo.OrderDetailParam orderDetailParam = null;
            try {
                orderDetailParam = APIFactory.getOrderAPI().orderGetOrderDetail(systemParam,
                        Long.valueOf(platOrderId.replaceAll("\\s", "")), 0l);
                List<OrderExtraParam> extras = orderDetailParam.getExtras();
                for (OrderExtraParam extra1 : extras) {
                    if (extra1.getType() == 5) {
                        extra.add(extra1);
                    }
                    if (extra1.getType() == 23) {
                        extra.add(extra1);
                    }
                }
            } catch (ApiOpException e) {
                logger.error(e.getMsg(), e);
            } catch (ApiSysException e) {
                logger.error(e.getMessage(), e);
            }


        }
        PrinterService.OrderInfo oi = new PrinterService.OrderInfo();
        if (extra.size() > 0) {
            List<String> giftName = new ArrayList<>();
            List<Integer> giftNum = new ArrayList<>();
            for (OrderExtraParam orderExtraParam : extra) {
                giftName.add(orderExtraParam.getAct_extend_msg().getGifts_name());
                giftNum.add(orderExtraParam.getAct_extend_msg().getGift_num());
            }
            oi.setGiftName(giftName);
            oi.setGiftNum(giftNum);

        }
        oi.setOrderId(order.getPlatOrderId());
        oi.setExcepTime(new Date(order.getExpectedDeliveryTime() * 1000));
        oi.setCaution(order.getCaution());
        oi.setCreateTime(order.getCreateTime());
        oi.setDaySeq(order.getDaySeq());
        oi.setPlat(order.getPlat());
        oi.setRecipientAddress(order.getRecipientAddress());
        oi.setRecipientName(order.getRecipientName());
        oi.setRecipientPhone(order.getRecipientPhone());
        oi.setStoreName(order.getStore().getName());
        oi.setDetailList(new ArrayList<>());
        oi.setDeliveryTime(order.getDeliveryTime());
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(order.getId());
        for (OrderDetail od : orderDetailList) {
            PrinterService.OrderDetailInfo odi = new PrinterService.OrderDetailInfo();
            odi.setFoodName(od.getFood().getName());
            odi.setFoodSpec(od.getSpec());
            odi.setQuantity(od.getQuantity());
            oi.getDetailList().add(odi);
        }
        if (fe) {
            boolean flag = printerService.print(sn, oi);
            logger.info("通知飞蛾打印订单返回，flag：" + flag + "，订单：" + order.getPlatOrderId());
        }
        if (yl) {
            boolean print = yilainyunPrintService.print(storeUser.getId(), oi);
            logger.info("通知易联云打印订单返回，flag：" + print + "，订单：" + order.getPlatOrderId());
        }

        if (xx) {
            boolean print = xinYeYunService.print(storeUser.getId(), oi);
            logger.info("通知芯烨云打印订单返回，flag：" + print + "，订单：" + order.getPlatOrderId());
        }

        if (zw) {
            boolean print = zhongWuPrintService.print(storeUser.getId(), oi);
            logger.info("通知中午打印订单返回，flag：" + print + "，订单：" + order.getPlatOrderId());
        }
        return true;
    }

    private String alignText(String[] textList, int[] lengthList) {
        StringBuffer sb = new StringBuffer();
        StringBuffer ap = new StringBuffer();
        for (int i = 0; i < textList.length; i++) {
            String txt = textList[i];
            int len = lengthList[i];
            try {
                int txtLen = txt.getBytes("gbk").length;
                if (txtLen > len) {
                    String str = StringHelper.substring(txt, len);
                    sb.append(str);
                    ap.append(txt.replace(str, ""));
                } else {
                    if (i == 0) {
                        sb.append(txt + StringUtils.repeat(" ", len - txtLen));
                    } else {
                        sb.append(StringUtils.repeat(" ", len - txtLen) + txt);
                    }
                }
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
        }
        sb.append("<BR>");
        if (ap.length() > 0) {
            ap.append("<BR>");
        }
        sb.append(ap);
        return sb.toString();
    }

    private void syncStoreOrder(Store store) {
        SystemParam param = meituanWaimaiService.getSystemParam();
        try {
            long daySeg = APIFactory.getOrderAPI().orderGetDaySeq(param, store.getCode());
//            APIFactory.getOrderAPI().order
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public OrderDTO toDTO(Order order, boolean fetchDetail) {
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(order, dto);
        dto.setStore(storeService.toDTO(order.getStore()));
        if (fetchDetail) {
//            dto.setOrderDetailList(order.getDetailList().stream().map(d -> orderDetailService.toDetailDTO(d, false)).collect(Collectors.toList()));
        }
        return dto;
    }

    @Override
    public String export(ExportParam param) throws IOException {
        List<File> files = new ArrayList<>();
        if (param.getStoreUserIdList() == null || param.getStoreUserIdList().size() == 0) {
            List<Long> userIds = orderStatMapper.statStoreUserIdHasValidOrder(param.getStartTime(), DateUtils.addDays(param.getEndTime(), 1), param.getStatus(), param.getBizStatus(), param.getPlat());
            param.setStoreUserIdList(userIds);
        }
        if (param.getType() == 1) {
            //每个商家都是一个文件
            for (Long storeUserId : param.getStoreUserIdList()) {
                File file = exportStoreUserOrder(storeUserId, param);
                files.add(file);
            }
        } else if (param.getType() == 2) {
            File file = exportWeekOrder(param);
            files.add(file);
        } else if (param.getType() == 3) {
            File file = exportHaiKuiOrder(param);
            files.add(file);
        } else if (param.getType() == 4) {
            File file = exportShanSongOrder(param);
            files.add(file);
        } else if (param.getType() == 5) {
            File file = exportUUOrder(param);
            files.add(file);
        }


        File zipFile = tmpFileService.createFile(System.currentTimeMillis() + "_"
                + DateFormatUtils.format(param.getStartTime(), "yyyy-MM-dd") + "_" + DateFormatUtils.format(param.getEndTime(), "yyyy-MM-dd") + ".zip");
        boolean flag = ZipHelper.packageZip(zipFile, files);
        if (flag) {
            try {
                CompleteMultipartUploadResult result = ossService.uploadFile(zipFile, "order-export/" + zipFile.getName());
                redisTemplate.opsForValue().set("orderExportUrl", OSSImageHelper.toUrl(result.getKey()));
            } catch (Throwable throwable) {
                logger.error("上传订单导出文件失败", throwable);
                return null;
            } finally {
                zipFile.delete();
                for (File file : files) {
                    file.delete();
                }
            }
            logger.info("导出完成");
        } else {
            logger.error("下载出错");
        }
        return null;
    }

    @Override
    public int countStoreUnFinishedOrder(List<Long> storeIdList, Date startTime, Date endTime) {
        return orderRepository.countByCreateTimeBetweenAndStoreIdInAndStatusIn(startTime, endTime, storeIdList, Arrays.asList(OrderStatus.MERCHANT_CONFIRMED,
                OrderStatus.PAID, OrderStatus.SHIPPED, OrderStatus.SHIPPING, OrderStatus.PAID));
    }

    private static String[] TITLE_LIST = new String[]{"日期", "平台", "品名", "约/规格", "数量", "菜农单价", "单品合计", "订单金额", "平台结算单价", "平台订单总计", "盈亏", "比例"};
    private static String[] ORDER_TITLE = new String[]{"门店名称", "日期", "平台", "配送方式", "餐损类型", "餐损成本", "订单成本", "合计成本"};

    private File exportWeekOrder(ExportParam param) throws IOException {
        File excelFile = tmpFileService.createFile("订单报表.xlsx");
        //HSSFWorkbook workbook = new HSSFWorkbook();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1, true);
        XSSFRow titleRow = sheet.createRow(0);
        XSSFFont headFont = workbook.createFont();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 16);
        CellStyle foldStyle = workbook.createCellStyle();
        CellStyle commonStyle = workbook.createCellStyle();
        XSSFFont commonFont = workbook.createFont();
        commonFont.setFontHeightInPoints((short) 12);
        commonStyle.setFont(commonFont);
        foldStyle.setFont(headFont);
        for (int i = 0; i < ORDER_TITLE.length; i++) {
            XSSFCell titleCell = titleRow.createCell(i);
            //给单元格设置内容
            titleCell.setCellValue(ORDER_TITLE[i]);
            titleCell.setCellStyle(foldStyle);
        }
        int rownum = 1;
        List<Long> idList = param.getStoreUserIdList();
        for (int idx = 0; idx < idList.size(); idx++) {
            StoreUser storeUser = storeUserRepository.findOne(idList.get(idx));
            logger.info(storeUser.getName() + "写入，当前位于第" + idx + "共" + idList.size() + "个");
            List<Plat> plats = new ArrayList<>();
            if (param.getPlat() != null) {
                plats.add(param.getPlat());
            } else {
                plats.add(Plat.MEITUAN);
                plats.add(Plat.ELE);
                plats.add(Plat.CLBM);
                plats.add(Plat.JDDJ);
            }

            List<Store> storeList = storeRepository.findByStoreUserIdAndOpeningTrueAndPlatIn(storeUser.getId(), plats);
            for (Store store : storeList) {
                //根据查询出来的订单信息来创建列，然后把列的数量对应加起来
                SearchParam sp = new SearchParam();
                sp.setStoreId(store.getId());
                sp.setStartDate(param.getStartTime());
                sp.setEndDate(param.getEndTime());
                //sp.setStoreUserId(idList.get(idx));
                sp.setStatus(param.getStatus());
                sp.setBizStatus(param.getBizStatus());
                //sp.setPlat(param.getPlat());
                int pageSize = 100;
                sp.setPageSize(pageSize);
                sp.setFetchDetail(false);
                sp.setFentchStoreUser(false);
                sp.setPage(1);
                //sp.setOpened(true);
                Float totleMoney = 0f;
                Float totleCansun = 0f;
                String name;
                String outName;
                if (storeUser.getBizManager() != null) {
                    name = storeUser.getBizManager().getName();
                } else {
                    name = "暂未绑定业务员";
                }
                if (storeUser.getBizManager2() != null) {
                    outName = storeUser.getBizManager2().getName();
                } else {
                    outName = "暂未绑定外包业务员";
                }
                while (true) {
                    Paging<OrderDTO> paging = this.search(sp);
                    for (int j = 0; j < paging.getResults().size(); j++) {
                        totleMoney += (paging.getResults().get(j).getCostTotal() - paging.getResults().get(j).getCostRefund());
                        totleCansun += paging.getResults().get(j).getCansun();
                        //每一个订单都增加一行
                        XSSFRow row = sheet.createRow(rownum++);
                        row.setHeightInPoints(20);
                        for (int i = 0; i < ORDER_TITLE.length; i++) {
                            XSSFCell rowCell = row.createCell(i);
                            rowCell.setCellStyle(commonStyle);
                            switch (ORDER_TITLE[i]) {
                                case "门店名称":
                                    if (j == 0) {
                                        rowCell.setCellValue(storeUser.getName());
                                    }

                                    break;
                                case "日期":
                                    rowCell.setCellValue(DateFormatUtils.format(paging.getResults().get(j).getCreateTime(), "yyyy.M.d") + "/#" + paging.getResults().get(j).getDaySeq());
                                    break;
                                case "平台":
                                    rowCell.setCellValue(paging.getResults().get(j).getPlat().getTitle());
                                    break;
                                case "配送方式":
                                    if (paging.getResults().get(j).getDeliveryType() == DeliveryType.SELF) {
                                        rowCell.setCellValue("商家配送");
                                    } else if (paging.getResults().get(j).getDeliveryType() == DeliveryType.PLATFORM) {
                                        rowCell.setCellValue("平台配送");
                                    } else if (paging.getResults().get(j).getDeliveryType() == DeliveryType.MEITUAN_OPEN || paging.getResults().get(j).getDeliveryType() == DeliveryType.UU_OPEN || paging.getResults().get(j).getDeliveryType() == DeliveryType.SS_OPEN) {
                                        rowCell.setCellValue("第三方配送");
                                    } else if (paging.getResults().get(j).getDeliveryType() == DeliveryType.ZHONGBAO) {
                                        rowCell.setCellValue("美团众包");
                                    }

                                    break;
                                case "业务员":
                                    rowCell.setCellValue(name);
                                    break;
                                case "外包业务员":
                                    rowCell.setCellValue(outName);
                                    break;
                                case "订单成本":
                                    rowCell.setCellType(CellType.NUMERIC);
                                    rowCell.setCellValue(paging.getResults().get(j).getCostTotal() - paging.getResults().get(j).getCostRefund());
                                    break;
                                case "餐损成本":
                                    rowCell.setCellType(CellType.NUMERIC);
                                    rowCell.setCellValue(paging.getResults().get(j).getCansun());
                                    break;
                                case "餐损类型":
                                    rowCell.setCellValue(paging.getResults().get(j).getCansunTypeTitle());
                                    break;
                                case "合计成本":
                                    rowCell.setCellType(CellType.NUMERIC);
                                    rowCell.setCellValue(paging.getResults().get(j).getCostTotal() - paging.getResults().get(j).getCostRefund() + paging.getResults().get(j).getCansun());
                                    break;
                            }
                        }
                    }
                    if (!paging.getHasNext()) {
                        //把已经取消的但是也有餐损的订单也加进去
                        sp.setStatus(OrderStatus.CANCELED);
                        sp.setCansun(true);
                        //sp.setPlat(param.getPlat());
                        sp.setPage(1);
                        while (true) {
                            Paging<OrderDTO> paging1 = this.search(sp);
                            for (int j = 0; j < paging1.getResults().size(); j++) {
                                totleCansun += paging1.getResults().get(j).getCansun();
                                //每一个订单都增加一行
                                XSSFRow row = sheet.createRow(rownum++);
                                row.setHeightInPoints(20);
                                for (int i = 0; i < ORDER_TITLE.length; i++) {
                                    XSSFCell rowCell = row.createCell(i);
                                    rowCell.setCellStyle(commonStyle);
                                    switch (ORDER_TITLE[i]) {
                                        case "门店名称":
                                            break;
                                        case "日期":
                                            rowCell.setCellValue(DateFormatUtils.format(paging1.getResults().get(j).getCreateTime(), "yyyy.M.d") + "/#" + paging1.getResults().get(j).getDaySeq());
                                            break;
                                        case "平台":
                                            rowCell.setCellValue(paging1.getResults().get(j).getPlat().getTitle());
                                            break;
                                        case "配送方式":
                                            if (paging1.getResults().get(j).getDeliveryType() == DeliveryType.SELF) {
                                                rowCell.setCellValue("商家配送");
                                            } else if (paging1.getResults().get(j).getDeliveryType() == DeliveryType.PLATFORM) {
                                                rowCell.setCellValue("平台配送");
                                            } else if (paging1.getResults().get(j).getDeliveryType() == DeliveryType.MEITUAN_OPEN || paging1.getResults().get(j).getDeliveryType() == DeliveryType.UU_OPEN || paging1.getResults().get(j).getDeliveryType() == DeliveryType.SS_OPEN) {
                                                rowCell.setCellValue("第三方配送");
                                            } else if (paging1.getResults().get(j).getDeliveryType() == DeliveryType.ZHONGBAO) {
                                                rowCell.setCellValue("美团众包");
                                            }

                                            break;
                                        case "业务员":
                                            rowCell.setCellValue(name);
                                            break;
                                        case "外包业务员":
                                            rowCell.setCellValue(outName);
                                            break;
                                        case "订单成本":
                                            rowCell.setCellType(CellType.NUMERIC);
                                            rowCell.setCellValue(0);
                                            break;
                                        case "餐损成本":
                                            rowCell.setCellType(CellType.NUMERIC);
                                            rowCell.setCellValue(paging1.getResults().get(j).getCansun());
                                            break;
                                        case "餐损类型":
                                            rowCell.setCellValue(paging1.getResults().get(j).getCansunTypeTitle());
                                            break;
                                        case "合计成本":
                                            rowCell.setCellType(CellType.NUMERIC);
                                            rowCell.setCellValue(paging1.getResults().get(j).getCansun());
                                            break;
                                    }
                                }
                            }

                            if (!paging1.getHasNext()) {
                                XSSFRow row = sheet.createRow(rownum++);
                                row.setHeightInPoints(20);


                                XSSFCell rowCell6 = row.createCell(4);
                                rowCell6.setCellStyle(commonStyle);
                                rowCell6.setCellValue(storeUser.getName() + "合计");


                                XSSFCell rowCell7 = row.createCell(5);
                                rowCell7.setCellStyle(commonStyle);
                                rowCell7.setCellType(CellType.NUMERIC);
                                rowCell7.setCellValue(totleCansun);

                                XSSFCell rowCell8 = row.createCell(6);
                                rowCell8.setCellStyle(commonStyle);
                                rowCell8.setCellType(CellType.NUMERIC);
                                rowCell8.setCellValue(totleMoney);

                                XSSFCell rowCell9 = row.createCell(7);
                                rowCell9.setCellStyle(commonStyle);
                                rowCell9.setCellType(CellType.NUMERIC);
                                rowCell9.setCellValue(totleCansun + totleMoney);
                                break;
                            } else {
                                sp.setPage(sp.getPage() + 1);
                            }

                        }
                        break;
                    }
                    sp.setPage(sp.getPage() + 1);
                }
            }
        }
        //workbook.write(excelFile);
        workbook.write(new FileOutputStream(excelFile));
        return excelFile;
    }

    private static String[] SHANSONG_TITLE = new String[]{"门店名称", "闪送订单号", "日期", "平台", "平台订单号", "业务员"};

    private File exportShanSongOrder(ExportParam param) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        String fileName;
        if (param.getPlat() == Plat.ELE) {
            fileName = "饿了么闪送订单";
        } else if (param.getPlat() == Plat.MEITUAN) {
            fileName = "美团闪送订单";
        } else if (param.getPlat() == Plat.WANTE) {
            fileName = "客户端闪送订单";
        } else {
            fileName = "闪送订单";
        }
        File excelFile = tmpFileService.createFile(fileName + ".xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1, true);
        HSSFRow titleRow = sheet.createRow(0);
        HSSFFont headFont = workbook.createFont();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 16);
        CellStyle foldStyle = workbook.createCellStyle();
        CellStyle commonStyle = workbook.createCellStyle();
        HSSFFont commonFont = workbook.createFont();
        commonFont.setFontHeightInPoints((short) 12);
        commonStyle.setFont(commonFont);
        foldStyle.setFont(headFont);
        for (int i = 0; i < SHANSONG_TITLE.length; i++) {
            HSSFCell titleCell = titleRow.createCell(i);
            //给单元格设置内容
            titleCell.setCellValue(SHANSONG_TITLE[i]);
            titleCell.setCellStyle(foldStyle);
        }
        int rownum = 1;
        for (Long id : param.getStoreUserIdList()) {
            StoreUser storeUser = storeUserRepository.findOne(id);
            //根据查询出来的订单信息来创建列，然后把列的数量对应加起来
            SearchParam sp = new SearchParam();
            sp.setStartExpectedDeliveryTime(param.getStartTime().getTime() / 1000);
            sp.setEndExpectedDeliveryTime(DateUtils.addDays(param.getEndTime(), 1).getTime() / 1000);
            sp.setStoreUserId(id);
            //sp.setDeliveryStatus(DeliveryStatus.ARRIVED);
            sp.setDeliveryType(DeliveryType.SS_OPEN);
            sp.setStatus(param.getStatus());
            sp.setBizStatus(param.getBizStatus());
            sp.setPlat(param.getPlat());
            int pageSize = 100;
            sp.setPageSize(pageSize);
            sp.setFetchDetail(false);
            sp.setPage(1);
            sp.setOpened(true);
            //Float totleMoney = 0f;
            String name;
            if (storeUser.getBizManager() != null) {
                name = storeUser.getBizManager().getName();
            } else {
                name = "暂未绑定业务员";
            }
            while (true) {
                Paging<OrderDTO> paging = this.search(sp);
                for (int j = 0; j < paging.getResults().size(); j++) {
                    HSSFRow row = sheet.createRow(rownum++);
                    row.setHeightInPoints(20);
                    for (int i = 0; i < SHANSONG_TITLE.length; i++) {
                        HSSFCell rowCell = row.createCell(i);
                        rowCell.setCellStyle(commonStyle);
                        switch (SHANSONG_TITLE[i]) {
                            case "门店名称":
                                if (j == 0) {
                                    rowCell.setCellValue(storeUser.getName());
                                }
                                break;
                            case "日期":
                                rowCell.setCellValue(DateFormatUtils.format(paging.getResults().get(j).getCreateTime(), "yyyy.M.d") + "/#" + paging.getResults().get(j).getDaySeq());
                                break;
                            case "平台":
                                rowCell.setCellValue(paging.getResults().get(j).getPlat().getTitle());
                                break;
                            case "平台订单号":
                                rowCell.setCellValue(paging.getResults().get(j).getPlatOrderId());
                                break;
                            case "闪送订单号":
                                rowCell.setCellValue(paging.getResults().get(j).getSsPeisongId());
                                break;
                            case "业务员":
                                rowCell.setCellValue(name);
                                break;
                        }
                    }
                }
                if (!paging.getHasNext()) {
                    break;
                }
                sp.setPage(sp.getPage() + 1);
            }

        }
        workbook.write(excelFile);
        return excelFile;
    }


    private static String[] UU_TITLE = new String[]{"门店名称", "UU订单号", "日期", "平台", "平台订单号", "业务员"};

    private File exportUUOrder(ExportParam param) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        String fileName;
        if (param.getPlat() == Plat.ELE) {
            fileName = "饿了么UU订单";
        } else if (param.getPlat() == Plat.MEITUAN) {
            fileName = "美团UU订单";
        } else if (param.getPlat() == Plat.WANTE) {
            fileName = "客户端UU订单";
        } else {
            fileName = "UU订单";
        }
        File excelFile = tmpFileService.createFile(fileName + ".xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1, true);
        HSSFRow titleRow = sheet.createRow(0);
        HSSFFont headFont = workbook.createFont();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 16);
        CellStyle foldStyle = workbook.createCellStyle();
        CellStyle commonStyle = workbook.createCellStyle();
        HSSFFont commonFont = workbook.createFont();
        commonFont.setFontHeightInPoints((short) 12);
        commonStyle.setFont(commonFont);
        foldStyle.setFont(headFont);
        for (int i = 0; i < UU_TITLE.length; i++) {
            HSSFCell titleCell = titleRow.createCell(i);
            //给单元格设置内容
            titleCell.setCellValue(UU_TITLE[i]);
            titleCell.setCellStyle(foldStyle);
        }
        int rownum = 1;
        for (Long id : param.getStoreUserIdList()) {
            StoreUser storeUser = storeUserRepository.findOne(id);
            //根据查询出来的订单信息来创建列，然后把列的数量对应加起来
            SearchParam sp = new SearchParam();
            sp.setStartExpectedDeliveryTime(param.getStartTime().getTime() / 1000);
            sp.setEndExpectedDeliveryTime(DateUtils.addDays(param.getEndTime(), 1).getTime() / 1000);
            sp.setStoreUserId(id);
            sp.setDeliveryStatus(DeliveryStatus.ARRIVED);
            sp.setDeliveryType(DeliveryType.UU_OPEN);
            sp.setStatus(param.getStatus());
            sp.setBizStatus(param.getBizStatus());
            sp.setPlat(param.getPlat());
            int pageSize = 100;
            sp.setPageSize(pageSize);
            sp.setFetchDetail(false);
            sp.setPage(1);
            sp.setOpened(true);
            //Float totleMoney = 0f;
            String name;
            if (storeUser.getBizManager() != null) {
                name = storeUser.getBizManager().getName();
            } else {
                name = "暂未绑定业务员";
            }
            while (true) {
                Paging<OrderDTO> paging = this.search(sp);
                for (int j = 0; j < paging.getResults().size(); j++) {
                    HSSFRow row = sheet.createRow(rownum++);
                    row.setHeightInPoints(20);
                    for (int i = 0; i < UU_TITLE.length; i++) {
                        HSSFCell rowCell = row.createCell(i);
                        rowCell.setCellStyle(commonStyle);
                        switch (UU_TITLE[i]) {
                            case "门店名称":
                                if (j == 0) {
                                    rowCell.setCellValue(storeUser.getName());
                                }
                                break;
                            case "日期":
                                rowCell.setCellValue(DateFormatUtils.format(paging.getResults().get(j).getCreateTime(), "yyyy.M.d") + "/#" + paging.getResults().get(j).getDaySeq());
                                break;
                            case "平台":
                                rowCell.setCellValue(paging.getResults().get(j).getPlat().getTitle());
                                break;
                            case "平台订单号":
                                rowCell.setCellValue(paging.getResults().get(j).getPlatOrderId());
                                break;
                            case "UU订单号":
                                rowCell.setCellValue(paging.getResults().get(j).getUuPeisongId());
                                break;
                            case "业务员":
                                rowCell.setCellValue(name);
                                break;
                        }
                    }
                }
                if (!paging.getHasNext()) {
                    break;
                }
                sp.setPage(sp.getPage() + 1);
            }

        }
        workbook.write(excelFile);
        return excelFile;
    }


    private static String[] HAIKUI_TITLE = new String[]{"门店名称", "海葵订单号", "日期", "平台", "平台订单号", "业务员"};

    private File exportHaiKuiOrder(ExportParam param) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        String fileName;
        if (param.getPlat() == Plat.ELE) {
            fileName = "饿了么海葵订单";
        } else if (param.getPlat() == Plat.MEITUAN) {
            fileName = "美团海葵订单";
        } else if (param.getPlat() == Plat.WANTE) {
            fileName = "客户端海葵订单";
        } else {
            fileName = "海葵订单";
        }
        File excelFile = tmpFileService.createFile(fileName + ".xls");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1, true);
        XSSFRow titleRow = sheet.createRow(0);
        XSSFFont headFont = workbook.createFont();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 16);
        CellStyle foldStyle = workbook.createCellStyle();
        CellStyle commonStyle = workbook.createCellStyle();
        XSSFFont commonFont = workbook.createFont();
        commonFont.setFontHeightInPoints((short) 12);
        commonStyle.setFont(commonFont);
        foldStyle.setFont(headFont);
        for (int i = 0; i < HAIKUI_TITLE.length; i++) {
            XSSFCell titleCell = titleRow.createCell(i);
            //给单元格设置内容
            titleCell.setCellValue(HAIKUI_TITLE[i]);
            titleCell.setCellStyle(foldStyle);
        }
        int rownum = 1;
        for (int idx = 0; idx < param.getStoreUserIdList().size(); idx++) {
            Long id = param.getStoreUserIdList().get(idx);
            StoreUser storeUser = storeUserRepository.findOne(id);
            logger.info("当前第" + idx + "个，共" + param.getStoreUserIdList().size() + "个，当前门店" + storeUser.getName());
            List<Plat> plats = new ArrayList<>();
            if (param.getPlat() != null) {
                plats.add(param.getPlat());
            } else {
                plats.add(Plat.JDDJ);
                plats.add(Plat.MEITUAN);
                plats.add(Plat.CLBM);
                plats.add(Plat.ELE);
            }
            List<Store> storeList = storeRepository.findByStoreUserIdAndOpeningTrueAndPlatIn(id, plats);
            for (Store store : storeList) {
                //根据查询出来的订单信息来创建列，然后把列的数量对应加起来
                SearchParam sp = new SearchParam();
                sp.setFentchStoreUser(false);
                sp.setStoreId(store.getId());
                sp.setStartExpectedDeliveryTime(param.getStartTime().getTime() / 1000);
                sp.setEndExpectedDeliveryTime(DateUtils.addDays(param.getEndTime(), 1).getTime() / 1000);
                //sp.setStoreUserId(id);
                sp.setDeliveryStatus(DeliveryStatus.ARRIVED);
                sp.setDeliveryType(DeliveryType.MEITUAN_OPEN);
                sp.setStatus(param.getStatus());
                sp.setBizStatus(param.getBizStatus());
                //sp.setPlat(param.getPlat());
                int pageSize = 100;
                sp.setPageSize(pageSize);
                sp.setFetchDetail(false);
                sp.setPage(1);
                //sp.setOpened(true);
                //Float totleMoney = 0f;
                String name;
                if (storeUser.getBizManager() != null) {
                    name = storeUser.getBizManager().getName();
                } else {
                    name = "暂未绑定业务员";
                }
                while (true) {
                    Paging<OrderDTO> paging = this.search(sp);
                    logger.info("开始写入" + storeUser.getName());
                    for (int j = 0; j < paging.getResults().size(); j++) {
                        //totleMoney += (paging.getResults().get(j).getCostTotal() - paging.getResults().get(j).getCostRefund());
                        //每一个订单都增加一行
                        XSSFRow row = sheet.createRow(rownum++);
                        row.setHeightInPoints(20);
                        for (int i = 0; i < HAIKUI_TITLE.length; i++) {
                            XSSFCell rowCell = row.createCell(i);
                            rowCell.setCellStyle(commonStyle);
                            switch (HAIKUI_TITLE[i]) {
                                case "门店名称":
                                    if (j == 0) {
                                        rowCell.setCellValue(storeUser.getName());
                                    }
                                    break;
                                case "日期":
                                    rowCell.setCellValue(DateFormatUtils.format(paging.getResults().get(j).getCreateTime(), "yyyy.M.d") + "/#" + paging.getResults().get(j).getDaySeq());
                                    break;
                                case "平台":
                                    rowCell.setCellValue(paging.getResults().get(j).getPlat().getTitle());
                                    break;
                                case "平台订单号":
                                    rowCell.setCellValue(paging.getResults().get(j).getPlatOrderId());
                                    break;
                                case "海葵订单号":
                                    rowCell.setCellValue(paging.getResults().get(j).getId());
                                    break;
                                case "业务员":
                                    rowCell.setCellValue(name);
                                    break;
                            }
                        }
                    }
                    if (!paging.getHasNext()) {
                        break;
                    }
                    sp.setPage(sp.getPage() + 1);
                }
            }


        }
//        workbook.write(excelFile);
        workbook.write(new FileOutputStream(excelFile));
        return excelFile;
    }

    private File exportStoreUserOrder(Long storeUserId, ExportParam param) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        String sheetName;
        if (storeUserId == null) {
            sheetName = "全部店铺";
        } else {
            StoreUser storeUser = storeUserRepository.findOne(storeUserId);
            sheetName = storeUser.getName();
        }
        //设置文件的名称
        File excelFile = tmpFileService.createFile(sheetName + ".xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1, true);
        HSSFRow headRow = sheet.createRow(0);
        HSSFFont headFont = workbook.createFont();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 16);
        headRow.setHeightInPoints(20);
        HSSFCell cell = headRow.createCell(0);
        CellStyle foldStyle = workbook.createCellStyle();
        CellStyle commonStyle = workbook.createCellStyle();
        HSSFFont commonFont = workbook.createFont();
        commonFont.setFontHeightInPoints((short) 12);
        commonStyle.setFont(commonFont);
        foldStyle.setFont(headFont);
        cell.setCellStyle(foldStyle);
        //给单元格设置内容
        cell.setCellValue(sheetName + "供货明细");
        HSSFRow titleRow = sheet.createRow(1);
        for (int i = 0; i < TITLE_LIST.length; i++) {
            HSSFCell titleCell = titleRow.createCell(i);
            //给单元格设置内容
            titleCell.setCellValue(TITLE_LIST[i]);
            titleCell.setCellStyle(foldStyle);
        }
        SearchParam sp = new SearchParam();
        sp.setStartDate(param.getStartTime());
        sp.setEndDate(param.getEndTime());
        sp.setStoreUserId(storeUserId);
        sp.setStatus(param.getStatus());
        sp.setBizStatus(param.getBizStatus());
        sp.setPlat(param.getPlat());
        int pageSize = 100;
        int rowIndex = 2;
        sp.setPageSize(pageSize);
        sp.setFetchDetail(true);
        sp.setPage(1);
        sp.setOpened(true);
        while (true) {
            Paging<OrderDTO> paging = this.search(sp);
            for (OrderDTO dto : paging.getResults()) {
                for (int j = 0; j < dto.getOrderDetailList().size(); j++) {
                    HSSFRow row = sheet.createRow(rowIndex++);
                    row.setHeightInPoints(20);
                    OrderDetailDTO detail = dto.getOrderDetailList().get(j);
                    for (int i = 0; i < TITLE_LIST.length; i++) {
                        HSSFCell rowCell = row.createCell(i);
                        rowCell.setCellStyle(commonStyle);
                        float val;
                        switch (TITLE_LIST[i]) {
                            case "日期":
                                if (j == 0) {
                                    rowCell.setCellValue(simpleDateFormat.format(dto.getCreateTime()) + "/#" + dto.getDaySeq());
                                }
                                break;
                            case "平台":
                                rowCell.setCellValue(dto.getPlat().getTitle());
                                break;
                            case "品名":
                                rowCell.setCellValue(detail.getFoodName());
                                break;
                            case "约/规格":
                                rowCell.setCellValue(detail.getSpec());
                                break;
                            case "数量":
                                rowCell.setCellType(CellType.NUMERIC);
                                rowCell.setCellValue(detail.getQuantity() - detail.getRefundQuantity());
                                break;
                            case "菜农单价":
                                rowCell.setCellType(CellType.NUMERIC);
                                val = detail.getQuotePrice() == null ? 0 : detail.getQuotePrice();
                                rowCell.setCellValue(Math.round(val * 100) / 100f);
                                break;
                            case "单品合计":
                                rowCell.setCellType(CellType.NUMERIC);
                                val = detail.getCostTotal() - detail.getCostRefund();
                                rowCell.setCellValue(Math.round(val * 100) / 100f);
                                break;
                            case "订单金额":
                                rowCell.setCellType(CellType.NUMERIC);
                                val = detail.getTotal() - detail.getRefund();
                                rowCell.setCellValue(Math.round(val * 100) / 100f);
                                break;
                            case "平台结算单价":
                                rowCell.setCellType(CellType.NUMERIC);
                                rowCell.setCellValue(detail.getPrice());
                                break;
                            case "平台订单总计":
                                rowCell.setCellType(CellType.NUMERIC);
                                if (j == dto.getOrderDetailList().size() - 1) {
                                    val = detail.getTotal() - detail.getRefund();
                                    rowCell.setCellValue(Math.round(val * 100) / 100f);
                                }
                                break;
                            case "盈亏":
                                rowCell.setCellType(CellType.NUMERIC);
                                val = detail.getTotal() - detail.getRefund() - (detail.getCostTotal() - detail.getCostRefund());
                                rowCell.setCellValue(Math.round(val * 100) / 100f);
                                break;
                            case "比例":
                                rowCell.setCellType(CellType.NUMERIC);
                                float rate = detail.getCostTotal().equals(detail.getCostRefund()) ? 0 : (detail.getTotal() - detail.getRefund()) / (detail.getCostTotal() - detail.getCostRefund());
                                rowCell.setCellValue(Math.round(rate * 100) / 100f);
                                break;
                        }
                    }
                }

            }
            if (!paging.getHasNext()) {
                break;
            }
            sp.setPage(sp.getPage() + 1);
        }
        workbook.write(excelFile);
        return excelFile;
    }

    @Override
    public void sycnStock(String platOrderId) {
        Order order = orderRepository.findByPlatAndPlatOrderId(Plat.MEITUAN, platOrderId);
        StoreUser storeUser = order.getStore().getStoreUser();
        List<OrderDetail> detailList = order.getDetailList();
        for (OrderDetail orderDetail : detailList) {
            StoreUserFood storeUserFood = storeUserFoodRepository.findByStoreUserIdAndFoodId(storeUser.getId(), orderDetail.getFood().getId());
            storeUserFoodService.syncStock(storeUserFood.getId());
        }
    }

    @Override
    public boolean setOrderSelf(long id) {
        Order order = orderRepository.findOne(id);
        if (order.getDeliveryType() == DeliveryType.MEITUAN_OPEN || order.getDeliveryType() == DeliveryType.DD_OPEN || order.getDeliveryType() == DeliveryType.SS_OPEN || order.getDeliveryType() == DeliveryType.UU_OPEN || order.getDeliveryType() == DeliveryType.SHUFENG_OPEN || order.getDeliveryType() == DeliveryType.UNDETERMINED || order.getDeliveryType() == DeliveryType.ZHONGBAO) {
            if (order.getDeliveryStatus() == DeliveryStatus.WAIT_SCHEDULE || order.getDeliveryStatus() == DeliveryStatus.TAKEN || order.getDeliveryStatus() == DeliveryStatus.ACCEPT || order.getDeliveryStatus() == DeliveryStatus.TURNRUN) {
                this.cancelDeliveryByOrderId(order.getId());
            }
        }
        order.setDeliveryType(DeliveryType.SELF);
        order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean sycnOrderPiesong(long id) {
        OrderNotification notification = notificationRepository.findOne(id);
        logger.info("对配送通知失败订单同步" + notification.getType());
        Order order;
        if (notification.getPlat() == Plat.JDDJ) {
            logger.info("同步聚合配送发起失败的订单 有orderid" + notification.getPlatOrderId());
            order = orderRepository.findOne(Long.valueOf(notification.getPlatOrderId()));
        } else {
            order = orderRepository.findByUuPeisongId(notification.getPlatOrderId());
        }

        if (order == null) {
            logger.error("未找到对应订单");
        }
        if (order.getDeliveryType() == DeliveryType.UNDETERMINED) {
            logger.info("聚合配送订单，同步UU配送状态");
            GetOrderInfoRequest request = new GetOrderInfoRequest();
            if (StringUtils.isNotBlank(order.getUuPeisongId())) {
                request.setOrder_code(order.getUuPeisongId());
            } else {
                request.setOrder_code(notification.getErrMsg());
            }

            UuAccountType uuAccountType = UuAccountType.TOTAL;
            if (order.getStore().getDeliveryType() != null) {
                uuAccountType = order.getStore().getUuAcountType();
            }
            GetOrderInfoResponse response = request.execute(uuAccountType);
            logger.info("获取到的UU订单状态：" + response.toString());
            String state = response.getState();
            DeliveryStatus status = order.getDeliveryStatus();
            if (state.equals("1")) {
                status = DeliveryStatus.WAIT_DELIVERY;
            } else if (state.equals("3")) {
                status = DeliveryStatus.ACCEPT;
            } else if (state.equals("4")) {
                status = DeliveryStatus.ACCEPT;
            } else if (state.equals("5")) {
                status = DeliveryStatus.TAKEN;
            } else if (state.equals("6")) {
                status = DeliveryStatus.ARRIVED;
            } else if (state.equals("10")) {
                status = DeliveryStatus.ARRIVED;
            } else if (state.equals("-1")) {
                status = DeliveryStatus.CANCELED;
            }


            if (status == DeliveryStatus.ACCEPT || status == DeliveryStatus.TAKEN) {
                order.setDeliveryType(DeliveryType.UU_OPEN);
                order.setDeliveryStatus(status);
                orderRepository.save(order);
                this.cancelJuHe(order);
            } else if (status == DeliveryStatus.CANCELED) {
                logger.info("uu配送被取消，聚合状态不同步" + order.getId());
            } else {
                order.setDeliveryStatus(status);
                orderRepository.save(order);
            }

        } else if (order.getDeliveryType() == DeliveryType.UU_OPEN) {
            GetOrderInfoRequest request = new GetOrderInfoRequest();
            request.setOrder_code(order.getUuPeisongId());
            UuAccountType uuAccountType = UuAccountType.TOTAL;
            if (order.getStore().getDeliveryType() != null) {
                uuAccountType = order.getStore().getUuAcountType();
            }
            GetOrderInfoResponse response = request.execute(uuAccountType);
            logger.info("获取到的UU订单状态：" + response.toString());
            String state = response.getState();
            DeliveryStatus status = order.getDeliveryStatus();
            if (state.equals("1")) {
                status = DeliveryStatus.WAIT_DELIVERY;
            } else if (state.equals("3")) {
                status = DeliveryStatus.ACCEPT;
            } else if (state.equals("4")) {
                status = DeliveryStatus.ACCEPT;
            } else if (state.equals("5")) {
                status = DeliveryStatus.TAKEN;
            } else if (state.equals("6")) {
                status = DeliveryStatus.ARRIVED;
            } else if (state.equals("10")) {
                status = DeliveryStatus.ARRIVED;
            } else if (state.equals("-1")) {
                status = DeliveryStatus.CANCELED;
            }

            order.setDeliveryStatus(status);
            orderRepository.save(order);
        } else {
            //订单已经用了其他的配送  把当前的 uu取消掉
            logger.info("取消订单UU配送" + order.getId());
            Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
            CancelUuRequest cancelUuRequest = new CancelUuRequest();
            if (StringUtils.isNotBlank(order.getUuPeisongId())) {
                cancelUuRequest.setOrder_code(order.getUuPeisongId());
            } else {
                cancelUuRequest.setOrder_code(notification.getErrMsg());
            }
            cancelUuRequest.setReason("取消配送");
            logger.info(cancelUuRequest.toString());
            UuAccountType uuAccountType = UuAccountType.TOTAL;
            if (store.getUuAcountType() != null) {
                uuAccountType = store.getUuAcountType();
            }
            uupt.src.com.uupt.openapi.response.CancelOrderResponse cancelOrderResponse = cancelUuRequest.execute(uuAccountType);
            if (!"ok".equals(cancelOrderResponse.getReturn_code())) {
                logger.error("取消订单错误" + cancelOrderResponse.getReturn_msg());
            } else {
                logger.info("取消uu聚合成功");
                order.setUuPeisongId(null);
            }
        }
        return true;
    }

    @Override
    public boolean updateDeliverySelf(long id, DeliveryStatus deliveryStatus) {
        Order order = orderRepository.findOne(id);
        logger.info("开始同步美团自配送订单配送状态" + order.getPlatOrderId());
//        if (order.getDeliveryType() != DeliveryType.SELF) {
//            throw new BizException("订单配送类型不是自配送，暂不支持更新");
//        }
        if (order.getPlat() != Plat.MEITUAN && order.getPlat() != Plat.CLBM) {
            throw new BizException("该操作只支持美团订单");
        }

        Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());

        SystemParam systemParam = meituanWaimaiService.getSystemParam();
        if (order.getPlat() == Plat.CLBM) {
            systemParam = clbmWaiMaiService.getSystemParam();
        }

        OrderLogisticsSyncParam param = new OrderLogisticsSyncParam();
        param.setOrder_id(order.getPlatOrderId());
        param.setCourier_name("商家自配");
        param.setCourier_phone(store.getPhone());
        param.setThird_carrier_order_id(order.getId().toString());
        param.setLogistics_provider_code("10015");

        if (deliveryStatus == DeliveryStatus.TAKEN) {
            param.setLatitude(order.getStore().getStoreUser().getLat());
            param.setLongitude(order.getStore().getStoreUser().getLng());
            param.setLogistics_status(20);

        } else if (deliveryStatus == DeliveryStatus.ARRIVED) {
            param.setLatitude(order.getLat().toString());
            param.setLongitude(order.getLng().toString());
            param.setLogistics_status(40);
        }

        try {
            String rs = APIFactory.getOrderAPI().orderLogisticsSync(systemParam, param);
            logger.info("同步配送信息返回：" + rs);
            if ("ok".equals(rs)) {
                order.setDeliveryStatus(deliveryStatus);
                order.setPsCourierName("商家自配送");
                order.setPsCourierPhone(store.getPhone());
                orderRepository.save(order);
                return true;
            }
        } catch (ApiOpException e) {
            logger.error(e.getMsg(), e);
        } catch (ApiSysException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean callCourier(long id, DeliveryType deliveryType) {
        Order order = orderRepository.findOne(id);
        switch (deliveryType) {
            case MEITUAN_OPEN:
                return this.callOrderMeituanPeiSong(order);
            case UU_OPEN:
                return this.callOrderUUPeisong(order);
            case SS_OPEN:
                return this.callOrderSSPeisong(order);
            case SHUFENG_OPEN:
                return this.callOrderSfPeisong(order);
            case DD_OPEN:
                return this.callOrderDDPeisong(order);
        }
        return false;
    }

    private boolean callOrderSfPeisong(Order order) {
        Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
        StoreUser su = store.getStoreUser();
        if (StringUtils.isBlank(su.getSfpsShopId())) {
            throw new BizException("该商家未填写顺丰店铺Id，请检查后发单");
        }
        CreateOrderReq req = new CreateOrderReq();
        CreateOrderReq.Receive receive = new CreateOrderReq.Receive();
        logger.info("发起顺丰订单配送，" + order.getId());
        receive.setUserLat(String.valueOf(order.getLat()));
        receive.setUserLng(String.valueOf(order.getLng()));
        receive.setUserName(order.getRecipientName());
        receive.setUserAddress(order.getRecipientAddress());
        receive.setUser_phone(order.getRecipientPhone());
        req.setReceive(receive);
        CreateOrderReq.OrderDetail sforderDetail = new CreateOrderReq.OrderDetail();
        List<CreateOrderReq.ProductDetail> list = new ArrayList<>();
        List<OrderDetail> detailList = order.getDetailList();
        int count = 0;
        for (OrderDetail orderDetail : detailList) {
            CreateOrderReq.ProductDetail productDetail = new CreateOrderReq.ProductDetail();
            productDetail.setProductName(orderDetail.getFoodName());
            float quantity = orderDetail.getQuantity();
            productDetail.setProductNum((int) quantity);
            count += (int) quantity;
            list.add(productDetail);
        }
        sforderDetail.setProductDetail(list);
        sforderDetail.setProductNum(count);
        sforderDetail.setTotalPrice((int) (order.getTotal() * 100));
        sforderDetail.setWeightGram(count * 50);
        sforderDetail.setProductType(6);
        sforderDetail.setProductTypeNum(detailList.size());
        req.setOrderDetail(sforderDetail);
        req.setShopId(su.getSfpsShopId());
        req.setShopOrderId(String.valueOf(System.currentTimeMillis() / 1000));
        req.setOrderSequence(String.valueOf(order.getDaySeq()));
        req.setPayType(1);
        req.setOrderTime(System.currentTimeMillis() / 1000);
        req.setReturnFlag(511);
        req.setVersion(19);
        req.setRemark("补发订单" + order.getCaution());
        req.setOrderSource("王小菜补发订单");
        CreateOrderRes res = shunfengClent.execute(req);
        if (res.getErrorCode() == 0) {
            logger.info("补发订单成功" + order.getId());
            return true;
        } else {
            logger.error("顺丰配送创建失败，" + order.getId() + " " + res.getErrorMsg());
            throw new BizException("顺丰配送创建失败，" + res.getErrorMsg());
        }
    }


    private boolean callOrderDDPeisong(Order order) {
        StoreUser su = order.getStore().getStoreUser();
        //调用城市列表接口，获取城市码
        CityCodeRequest codeRequest = new CityCodeRequest();
        CityCodeResponse codeResponse = daDaClient.request(codeRequest);
        String cityCode = "";
        List<CityCodeResponse.Result> list = codeResponse.getResult();
        for (CityCodeResponse.Result result : list) {
            if (su.getCity().getName().contains(result.getCityName())) {
                cityCode = result.getCityCode();
                break;
            }
        }
        AddOrderRequest request = new AddOrderRequest();
        request.setShopNo(su.getId().toString());
        request.setOriginId(String.valueOf(System.currentTimeMillis() / 1000));
        request.setCityCode(cityCode);
        DecimalFormat format = new DecimalFormat("#.00");
        request.setCargoPrice(Double.valueOf(format.format(order.getTotal().doubleValue())));
        request.setIsPrepay(0);
        request.setReceiverName(order.getRecipientName());
        request.setReceiverAddress(order.getRecipientAddress());
        request.setReceiverLat(order.getLat());
        request.setReceiverLng(order.getLng());
        request.setCallback("https://api.wangxiaocai.cn/dd-peisong/order/status");
        request.setCargoWeight(1.5);
        request.setReceiverPhone(order.getRecipientPhone());
        request.setInfo(order.getCaution());
        request.setOriginMarkno("补发订单" + order.getPlat().getTitle() + "#" + order.getDaySeq());
        dada.com.response.AddOrderResponse response = daDaClient.request(request);
        if (response.getCode() != 0) {
            String info = "达达发单出错，orderId: " + order.getId() + "  " + response.getMsg();
            logger.info(info);
            throw new BizException(info);
        } else {
            logger.info("发起订单配送成功");
        }
        return true;
    }

    private boolean callOrderMeituanPeiSong(Order order) {
        CreateOrderByShopRequest req = new CreateOrderByShopRequest();
        Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
        logger.info("发起手工单订单配送，" + order.getId());
        if (order.getLng() == null) {
            try {
                GeoResponse res = aMapClient.geo(order.getRecipientAddress());
                List<GeoResponse.GeoCode> codes = res.getGeocodes();
                if (codes == null || codes.size() == 0) {
                    throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
                }
                String[] location = codes.get(0).getLocation().split(",");
                req.setReceiverLng(Double.valueOf(Double.parseDouble(location[0]) * Math.pow(10, 6)).intValue());
                req.setReceiverLat(Double.valueOf(Double.parseDouble(location[1]) * Math.pow(10, 6)).intValue());

            } catch (IOException e) {
                logger.error("获取用户地理位置坐标错误", e);
                throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
            }
        } else {
            req.setReceiverLng(Double.valueOf((order.getLng() * Math.pow(10, 6))).intValue());
            req.setReceiverLat(Double.valueOf((order.getLat() * Math.pow(10, 6))).intValue());
        }
        req.setGoodsValue(new BigDecimal(order.getTotal()));
        int i = (int) (10 + Math.random() * (50 - 10 + 1));
        double ami = i / 10.0;
        req.setGoodsWeight(new BigDecimal(ami));
        StoreUser su = order.getStore().getStoreUser();
        long millis = System.currentTimeMillis();
        req.setDeliveryId(millis);
        req.setOrderId(String.valueOf(millis));
        req.setShopId(su.getMtpsShopId());
        req.setDeliveryServiceCode(meituanPeisongApp.getDeliveryServiceCode());
        req.setReceiverName(order.getRecipientName());
        req.setReceiverAddress(order.getRecipientAddress());
        req.setReceiverPhone(order.getRecipientPhone());
        /**
         * 美团预定单要提前一个小时发单，如果小于1个小时，则发即时单
         * */
        long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
        req.setExpectedDeliveryTime(expectedDeliveryTimeVal);
        req.setOrderType(0);
        req.setCoordinateType(0);
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            req.setPoiSeq(order.getPlat().getTitle() + "#" + order.getDaySeq() + "补发单");

        } else if (order.getPlat() == Plat.JDDJ) {
            req.setPoiSeq("王小菜京东#" + order.getDaySeq() + "补发单");
        } else {
            req.setPoiSeq("王小菜#" + order.getDaySeq() + "补发单");
        }
        req.setOuterOrderSourceNo("补发订单");
        req.setNote(order.getCaution());
        CreateOrderResponse res = peisongClient.execute(req);
        if (res.getCode().equals("0")) {
            logger.info("发起订单配送成功，" + order.getId());
            return true;
        } else {
            logger.error("配送创建失败，" + order.getId() + " " + res.getMessage());
            throw new BizException("配送创建失败，" + res.getMessage());
        }
    }

    private boolean callOrderUUPeisong(Order order) {
        GetOrderPriceRequest priceRequest = new GetOrderPriceRequest();
        priceRequest.setOrigin_id("");
        LocateInfo locateInfo = new LocateInfo();
        StoreUser su = order.getStore().getStoreUser();
        if (StringUtils.isEmpty(su.getLat()) || StringUtils.isEmpty(su.getLng())) {
            String info = "UU跑腿需要商家经纬度，请添加，orderId: " + order.getId();
            logger.info(info);
            throw new BizException(info);
        }
        LocateInfo storeLocal = this.getBaiDuMap(su.getLng(), su.getLat());
        priceRequest.setFrom_lng(String.valueOf(storeLocal.getLongitude()));
        priceRequest.setFrom_lat(String.valueOf(storeLocal.getLatitude()));
        logger.info("发起uu手动单订单配送，" + order.getId());

        LocateInfo userLocation = new LocateInfo();
        if (order.getLng() == null) {
            try {
                GeoResponse res = aMapClient.geo(order.getRecipientAddress());
                List<GeoResponse.GeoCode> codes = res.getGeocodes();
                if (codes == null || codes.size() == 0) {
                    throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
                }
                String[] location = codes.get(0).getLocation().split(",");
                userLocation = this.getBaiDuMap(location[0], location[1]);
                priceRequest.setTo_lat(String.valueOf(userLocation.getLatitude()));
                priceRequest.setTo_lng(String.valueOf(userLocation.getLongitude()));
            } catch (IOException e) {
                logger.error("获取用户地理位置坐标错误", e);
                throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
            }
        } else {
            userLocation = this.getBaiDuMap(String.valueOf(order.getLng()), String.valueOf(order.getLat()));
            priceRequest.setTo_lng(String.valueOf(userLocation.getLongitude()));
            priceRequest.setTo_lat(String.valueOf(userLocation.getLatitude()));
        }
        priceRequest.setTo_address(order.getRecipientAddress());
        priceRequest.setFrom_address(su.getAddress());
        priceRequest.setCity_name(su.getCity().getName());
        priceRequest.setSend_type("0");

        priceRequest.setSubscribe_type("0");
        priceRequest.setSubscribe_time("");
        long expectedDeliveryTimeVal = order.getExpectedDeliveryTime();
        logger.info(priceRequest.toString());
        UuAccountType uuAccountType = UuAccountType.TOTAL;
        if (order.getStore().getDeliveryType() != null) {
            uuAccountType = order.getStore().getUuAcountType();
        }
        GetOrderPriceReponse priceReponse = priceRequest.excute(uuAccountType);
        if (!"ok".equals(priceReponse.getReturn_code())) {
            String info = "获取uu价格出错，orderId: " + order.getId() + "  " + priceReponse.getReturn_msg();
            logger.info(info);
            throw new BizException(info);
        }

        AddOrderReuqest addOrderReuqest = new AddOrderReuqest();
        addOrderReuqest.setSpecial_type("0");
        addOrderReuqest.setReceiver_phone(order.getRecipientPhone());
        addOrderReuqest.setReceiver(order.getRecipientName());
        addOrderReuqest.setShortordernum(String.valueOf(order.getDaySeq()));
        addOrderReuqest.setPush_type("0");
        addOrderReuqest.setPubusermobile(su.getPhone());
        addOrderReuqest.setPrice_token(priceReponse.getPrice_token());
        String note = "";
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            addOrderReuqest.setOrdersource("1");
            if (order.getPlat() == Plat.MEITUAN) {
                note = note + "王小菜美团订单 补发";
            } else {
                note = note + "菜老板美团订单 补发";
            }
        } else if (order.getPlat() == Plat.ELE) {
            addOrderReuqest.setOrdersource("2");
        } else {
            addOrderReuqest.setOrdersource("3");
        }
        addOrderReuqest.setOrder_price(priceReponse.getTotal_money());
        addOrderReuqest.setNote(note + " 补发订单 " + order.getCaution());
        addOrderReuqest.setCallme_withtake("1");
        addOrderReuqest.setBalance_paymoney(priceReponse.getNeed_paymoney());
        logger.info(addOrderReuqest.toString());

        AddOrderResponse addOrderResponse = addOrderReuqest.execute(uuAccountType);
        if (!"ok".equals(addOrderResponse.getReturn_code())) {
            String info = "获取手工单uu价格出错，orderId: " + order.getId() + "  " + addOrderResponse.getReturn_msg();
            logger.info(info);
            throw new BizException(info);
        }

        logger.info("发起订单手工单配送成功，" + order.getId());
        return true;
    }

    private boolean callOrderSSPeisong(Order order) {
        GetPriceResquest priceResquest = new GetPriceResquest();
        LocateInfo locateInfo = new LocateInfo();
        StoreUser su = order.getStore().getStoreUser();
        if (StringUtils.isEmpty(su.getLat()) || StringUtils.isEmpty(su.getLng())) {
            String info = "闪送需要商家经纬度，请添加，orderId: " + order.getId();
            logger.info(info);
            throw new BizException(info);
        }
        logger.info("发起订单闪送手工单配送，" + order.getId());
        priceResquest.setAppointType(0);
        LocateInfo storeLocal = this.getBaiDuMap(su.getLng(), su.getLat());
        GetPriceResquest.Sender sender = new GetPriceResquest.Sender();
        sender.setFromLongitude(String.valueOf(storeLocal.getLongitude()));
        sender.setFromLatitude(String.valueOf(storeLocal.getLatitude()));
        sender.setFromSenderName(su.getName());
        sender.setFromMobile(su.getPhone().replace("_", "#").replace(" ", "").replace(",", "#"));
        sender.setFromAddress(su.getAddress());
        priceResquest.setSender(sender);
        priceResquest.setStoreName(su.getName());
        List<GetPriceResquest.Receiver> receiverList = new ArrayList<>();
        GetPriceResquest.Receiver receiver = new GetPriceResquest.Receiver();
        receiver.setWeight("2");
        receiver.setToReceiverName(order.getRecipientName());
        receiver.setToMobile(order.getRecipientPhone().replace("_", "#").replace(" ", "").replace(",", "#"));

        LocateInfo userLocation = new LocateInfo();
        if (order.getLng() == null) {
            try {
                GeoResponse res = aMapClient.geo(order.getRecipientAddress());
                List<GeoResponse.GeoCode> codes = res.getGeocodes();
                if (codes == null || codes.size() == 0) {
                    throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
                }
                String[] location = codes.get(0).getLocation().split(",");
                userLocation = this.getBaiDuMap(location[0], location[1]);
                receiver.setToLatitude(String.valueOf(userLocation.getLatitude()));
                receiver.setToLongitude(String.valueOf(userLocation.getLongitude()));
            } catch (IOException e) {
                logger.error("获取用户地理位置坐标错误", e);
                throw new BizException("无法获取用户地理位置坐标，" + order.getRecipientAddress());
            }
        } else {
            userLocation = this.getBaiDuMap(String.valueOf(order.getLng()), String.valueOf(order.getLat()));
            receiver.setToLongitude(String.valueOf(userLocation.getLongitude()));
            receiver.setToLatitude(String.valueOf(userLocation.getLatitude()));
        }
        receiver.setToAddress(order.getRecipientAddress());
        String note = "补发订单";
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            receiver.setOrderingSourceType("4");
            if (order.getPlat() == Plat.MEITUAN) {
                note = note + "王小菜美团订单";
            } else {
                note = note + "菜老板美团订单";
            }
        } else if (order.getPlat() == Plat.ELE) {
            receiver.setOrderingSourceType("3");
        } else {
            receiver.setOrderingSourceType("6");
        }
        long millis = System.currentTimeMillis();
        receiver.setOrderingSourceNo(String.valueOf(order.getDaySeq()));
        receiver.setRemarks(note + "  " + order.getCaution());
        receiver.setOrderNo(String.valueOf(millis));
        receiver.setGoodType("10");
        receiverList.add(receiver);
        priceResquest.setReceiverList(receiverList);
        priceResquest.setCityName(su.getCity().getName());
        logger.info(priceResquest.toString());
        GetPriceResponse priceResponse = shanSongClient.request(priceResquest);
        if (priceResponse.getStatus() != 200) {
            String info = "获取闪送手动单价格出错，orderId: " + order.getId() + "  " + priceResponse.getMsg();
            logger.info(info);
            throw new BizException(info);
        }

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setIssOrderNo(priceResponse.getData().getOrderNumber());
        SendOrderResponse sendOrderResponse = shanSongClient.request(sendOrderRequest);

        if (sendOrderResponse.getStatus() != 200) {
            String info = "闪送发单出错，orderId: " + order.getId() + "  " + sendOrderResponse.getMsg();
            logger.info(info);
            throw new BizException(info);
        }
        logger.info("发起订单手工单闪送配送成功，" + order.getId());
        return true;
    }

    @Override
    public void preparationMealComplete(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        if (order.getPlat() == Plat.MEITUAN || order.getPlat() == Plat.CLBM) {
            logger.info("进行订单的拣货完成" + orderId);
            if ((order.getDeliveryType() == DeliveryType.MEITUAN_OPEN || order.getDeliveryType() == DeliveryType.DD_OPEN || order.getDeliveryType() == DeliveryType.SHUFENG_OPEN || order.getDeliveryType() == DeliveryType.UNDETERMINED || order.getDeliveryType() == DeliveryType.UU_OPEN || order.getDeliveryType() == DeliveryType.SS_OPEN || order.getDeliveryType() == DeliveryType.ZHONGBAO) && order.getDeliveryStatus() != DeliveryStatus.TURNRUN) {
                order.setPreparation(true);
                orderRepository.save(order);
                return;
            }
            SystemParam param = null;
            if (order.getPlat() == Plat.MEITUAN) {
                param = meituanWaimaiService.getSystemParam();
            } else {
                param = clbmWaiMaiService.getSystemParam();
            }
            String s = null;
            try {
                s = APIFactory.getOrderAPI().preparationMealComplete(param, Long.valueOf(order.getPlatOrderId()));
            } catch (ApiOpException e) {
                logger.error(e.getMsg(), e);
            } catch (ApiSysException e) {
                logger.error(e.getMessage(), e);
            }
            logger.info("订单发起拣货完成返回" + orderId + s);
            //设置订单已经发起过拣货完成
            order.setPreparation(true);
            orderRepository.save(order);
        } else {
            logger.info("订单不是美团或者菜老板，暂不支持拣货完成" + orderId);
        }
    }

    @Override
    public void updateDeliveryState(Long id) {
        Order order = orderRepository.findOne(id);
        if (order.getDeliveryType() == DeliveryType.UU_OPEN) {
            GetOrderInfoRequest request = new GetOrderInfoRequest();
            request.setOrder_code(order.getUuPeisongId());
            UuAccountType uuAccountType = UuAccountType.TOTAL;
            if (order.getStore().getDeliveryType() != null) {
                uuAccountType = order.getStore().getUuAcountType();
            }
            GetOrderInfoResponse response = request.execute(uuAccountType);
            logger.info("获取到的UU订单状态：" + response.toString());
            String state = response.getState();
            DeliveryStatus status = order.getDeliveryStatus();
            if (state.equals("1")) {
                status = DeliveryStatus.WAIT_DELIVERY;
            } else if (state.equals("3")) {
                status = DeliveryStatus.ACCEPT;
            } else if (state.equals("4")) {
                status = DeliveryStatus.ACCEPT;
            } else if (state.equals("5")) {
                status = DeliveryStatus.TAKEN;
            } else if (state.equals("6")) {
                status = DeliveryStatus.ARRIVED;
            } else if (state.equals("10")) {
                status = DeliveryStatus.ARRIVED;
            } else if (state.equals("-1")) {
                status = DeliveryStatus.CANCELED;
            }

            order.setDeliveryStatus(status);
            orderRepository.save(order);
        } else if (order.getDeliveryType() == DeliveryType.SS_OPEN) {
            GetStatusRequest request = new GetStatusRequest();
            request.setIssOrderNo(order.getSsPeisongId());
            request.setThirdOrderNo(order.getSsPeisongId());
            GetStatusResponse response = shanSongClient.request(request);
            if (response.getStatus() != 200) {
                throw new BizException("获取闪送订单状态出错，" + response.getMsg());
            } else {
                logger.info("获取闪送订单状态" + response.toString());
                Integer orderStatus = response.getData().getOrderStatus();
                DeliveryStatus status = order.getDeliveryStatus();
                if (orderStatus == 20) {
                    status = DeliveryStatus.WAIT_DELIVERY;
                }
                if (orderStatus == 30) {
                    status = DeliveryStatus.ACCEPT;
                }
                if (orderStatus == 40) {
                    status = DeliveryStatus.TAKEN;
                }
                if (orderStatus == 50) {
                    status = DeliveryStatus.ARRIVED;
                }
                if (orderStatus == 60) {
                    status = DeliveryStatus.CANCELED;
                }

                order.setDeliveryStatus(status);
                orderRepository.save(order);
            }
        }

    }

    @Override
    public boolean checkOrderDelivery(long id, Integer deliveryType) {
        //首先把原来的配送取消掉 判断订单的配送状态  如果是已经有骑手接单了 那么就不能再次发起配送
        Order order = orderRepository.findOne(id);
        if (order.getDeliveryType() == DeliveryType.PLATFORM || order.getDeliveryType() == DeliveryType.SELF || order.getDeliveryType() == DeliveryType.UNDETERMINED) {
            logger.error("非第三方配送订单  不能切换配送");
            return false;
        }
        if (order.getDeliveryStatus() == DeliveryStatus.ARRIVED || order.getDeliveryStatus() == DeliveryStatus.ACCEPT || order.getDeliveryStatus() == DeliveryStatus.TAKEN) {
            logger.error("当前订单状态不支持转配送：" + order.getDeliveryStatus().getTitle());
        }
        Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
        Boolean canceledSuccess = false;
        if (order.getDeliveryStatus() == DeliveryStatus.CANCELED || order.getDeliveryStatus() == DeliveryStatus.WAIT_DELIVERY || order.getDeliveryStatus() == DeliveryStatus.OUTRANGE) {
            //如果原来的订单状态是取消或者还没有发起配送或者是超范围的订单 那么直接转到其他的配送
            canceledSuccess = true;
        } else {
            //把原来的订单取消配送
            if (order.getDeliveryType() == DeliveryType.MEITUAN_OPEN) {
                //取消海葵配送
                CancelOrderRequest req = new CancelOrderRequest();
                if (order.getPsCancelReasonId() == null) {
                    req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(199));
                } else {
                    if (CancelOrderReasonId.findByCode(order.getPsCancelReasonId()) == null) {
                        req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(199));
                    } else {
                        req.setCancelOrderReasonId(CancelOrderReasonId.findByCode(order.getPsCancelReasonId()));
                    }

                }
                if (order.getCancelReason() == null || order.getCancelReason() == "") {
                    req.setCancelReason("顾客取消");
                } else {
                    req.setCancelReason(order.getCancelReason());
                }
                req.setDeliveryId(order.getPsDeliveryId());
                req.setMtPeisongId(order.getMtPeisongId());
                CancelOrderResponse res = peisongClient.execute(req);
                if (!res.getCode().equals("0")) {
                    throw new BizException("取消订单错误，" + res.getMessage());
                } else {
                    canceledSuccess = true;
                }
            } else if (order.getDeliveryType() == DeliveryType.UU_OPEN) {
                //uu配送订单

                CancelUuRequest cancelUuRequest = new CancelUuRequest();
                cancelUuRequest.setOrder_code(order.getUuPeisongId());
                cancelUuRequest.setReason("取消配送");
                logger.info(cancelUuRequest.toString());
                UuAccountType uuAccountType = UuAccountType.TOTAL;
                if (store.getUuAcountType() != null) {
                    uuAccountType = store.getUuAcountType();
                }
                uupt.src.com.uupt.openapi.response.CancelOrderResponse cancelOrderResponse = cancelUuRequest.execute(uuAccountType);
                if (!"ok".equals(cancelOrderResponse.getReturn_code())) {
                    throw new BizException("取消订单错误，" + cancelOrderResponse.getReturn_msg());
                } else {
                    canceledSuccess = true;
                }

            } else if (order.getDeliveryType() == DeliveryType.DD_OPEN) {
                dada.com.request.CancelOrderRequest request = new dada.com.request.CancelOrderRequest();
                request.setOrderId(order.getDdPeisongId());
                request.setCancelReasonId("4");
                request.setCancelReason("取消订单配送");
                dada.com.response.CancelOrderResponse response1 = daDaClient.request(request);
                if (response1.getCode() == 0) {
                    canceledSuccess = true;
                } else {
                    throw new BizException("取消达达订单错误，" + response1.getMsg());
                }
            } else if (order.getDeliveryType() == DeliveryType.SS_OPEN) {
                CancelRequest request = new CancelRequest();
                request.setIssOrderNo(order.getSsPeisongId());
                CancelResponse response = shanSongClient.request(request);
                if (response.getStatus() != 200) {
                    throw new BizException("取消订单错误，" + response.getMsg());
                } else {
                    canceledSuccess = true;
                }
            } else if (order.getDeliveryType() == DeliveryType.SHUFENG_OPEN) {
                //顺丰配送 暂时不进行处理
                CancelOrderReq req = new CancelOrderReq();
                req.setOrderId(order.getSfPeisongId());
                CancelOrderRes res = shunfengClent.execute(req);
                if (res.getErrorCode() == 0) {
                    logger.info("取消顺丰成功");
                    canceledSuccess = true;
                } else {
                    throw new BizException("取消订单错误，" + res.getErrorMsg());
                }
            } else if (order.getDeliveryType() == DeliveryType.ZHONGBAO) {
                //美团众包配送
                logger.info("取消美团跑腿配送");
                //查询跑腿取消原因
                SystemParam systemParam;
                if (order.getPlat() == Plat.MEITUAN) {
                    systemParam = meituanWaimaiService.getSystemParam();
                } else {
                    systemParam = clbmWaiMaiService.getSystemParam();
                }
                try {
                    CancelDeliveryReasonParam cancelDeliveryReason = APIFactory.getOrderAPI().getCancelDeliveryReason(systemParam, Long.valueOf(order.getPlatOrderId()), order.getStore().getCode());
                    if (cancelDeliveryReason.getReasonList().size() > 0) {
                        CancelReson cancelReson = cancelDeliveryReason.getReasonList().get(0);
                        String s = APIFactory.getOrderAPI().cancelLogisticsByWmOrderId(systemParam, String.valueOf(cancelReson.getCode()), cancelReson.getContent(), order.getStore().getCode(), Long.valueOf(order.getPlatOrderId()));
                        logger.info("取消跑腿返回" + s);
                        if ("ok".equals(s)) {
                            canceledSuccess = true;
                        }
                    }

                } catch (ApiOpException e) {
                    logger.error(e.getMsg(), e);
                } catch (ApiSysException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }


        if (canceledSuccess) {
            //取消订单成功  发起下一个配送
            if (deliveryType == 1) {
                //海葵配送
                order.setDeliveryType(DeliveryType.MEITUAN_OPEN);
                order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
            } else if (deliveryType == 2) {
                //跑腿配送
                //order.setDeliveryStatus(DeliveryStatus.OUTRANGE);
                order.setDeliveryType(DeliveryType.ZHONGBAO);
                order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
            } else if (deliveryType == 3) {
                //uu配送
                order.setDeliveryType(DeliveryType.UU_OPEN);
                order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);

            } else if (deliveryType == 4) {
                order.setDeliveryType(DeliveryType.SS_OPEN);
                order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
            } else if (deliveryType == 5) {
                order.setDeliveryType(DeliveryType.DD_OPEN);
                order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
            } else if (deliveryType == 6) {
                order.setDeliveryType(DeliveryType.SHUFENG_OPEN);
                order.setDeliveryStatus(DeliveryStatus.WAIT_DELIVERY);
            }
            orderRepository.save(order);
            if (store.getDeliveryType() == DeliveryType.UNDETERMINED) {
                //聚合配送店铺的订单等待后台定时任务来发起配送
                return canceledSuccess;
            } else {
                return this.sendDeliveryByOrderId(order.getId());
            }

        } else {
            return canceledSuccess;
        }
    }

    @Override
    public String downLoad() {
        String url = redisTemplate.opsForValue().get("orderExportUrl");
        redisTemplate.delete("orderExportUrl");
        return url;
    }

    @Override
    public int refundNum(Long storeUserId) {
        Integer refundNum = 0;
        List<Store> storeList = storeRepository.findByStoreUserId(storeUserId);
        List<Long> storeIdList = storeList.stream().map(o -> o.getId()).collect(Collectors.toList());
        List<OrderStatus> list = new ArrayList<>();
        list.add(OrderStatus.CANCELED);
        list.add(OrderStatus.FINISHED);
        refundNum = orderRepository.countByStoreIdInAndRefundStatusAndStatusNotIn(storeIdList, OrderRefundStatus.PENDING, list);
        return refundNum;
    }
}
