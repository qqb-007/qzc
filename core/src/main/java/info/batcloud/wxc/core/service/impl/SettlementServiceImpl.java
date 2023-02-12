package info.batcloud.wxc.core.service.impl;

import info.batcloud.wxc.core.entity.*;
import info.batcloud.wxc.core.enums.OrderBizStatus;
import info.batcloud.wxc.core.enums.OrderStatus;
import info.batcloud.wxc.core.enums.SettlementSheetDetailStatus;
import info.batcloud.wxc.core.enums.SettlementSheetStatus;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.DateHelper;
import info.batcloud.wxc.core.repository.*;
import info.batcloud.wxc.core.service.OrderService;
import info.batcloud.wxc.core.service.SettlementService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

import static info.batcloud.wxc.core.enums.OrderRefundStatus.AGREE;
import static info.batcloud.wxc.core.enums.OrderStatus.CANCELED;
import static info.batcloud.wxc.core.enums.OrderStatus.FINISHED;

@Service
public class SettlementServiceImpl implements SettlementService {

    @Inject
    private SettlementSheetRepository settlementSheetRepository;

    @Inject
    private SettlementSheetDetailRepository settlementSheetDetailRepository;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(SettlementService.class);

    @Override
    public void batchReCheck(Date ctime) {
        long time = ctime.getTime();
        Date startTime = new Date(time + 12 * 60 * 60 * 1000);
        Date endTime = new Date(time + 36 * 60 * 60 * 1000);
        List<SettlementSheetDetail> settlementSheetDetails = settlementSheetDetailRepository.findByCreateTimeBetweenAndStatus(startTime, endTime, SettlementSheetDetailStatus.WAIT_SETTLE);
        for (SettlementSheetDetail settlementSheetDetail : settlementSheetDetails) {
            try {
                this.recheckSettlementSheetDetail(settlementSheetDetail.getId());
            } catch (Exception e) {
                System.out.println("出错的结算单" + settlementSheetDetail.getId());
                logger.error(e.getMessage(), e);
            }
            System.out.println("重检完成");
        }
    }

    @Override
    public void batchCheck(Date ctime) {
        long time = ctime.getTime();
        Date startTime = new Date(time + 12 * 60 * 60 * 1000);
        Date endTime = new Date(time + 36 * 60 * 60 * 1000);
        List<SettlementSheetDetail> settlementSheetDetails = settlementSheetDetailRepository.findByCreateTimeBetweenAndStatus(startTime, endTime, SettlementSheetDetailStatus.WAIT_CHECK);
        for (SettlementSheetDetail settlementSheetDetail : settlementSheetDetails) {
            try {
                this.reGenerateDailySettlementSheetDetail(settlementSheetDetail.getId());
            } catch (Exception e) {
                System.out.println("出错的结算单" + settlementSheetDetail.getId());
                logger.error(e.getMessage(), e);
            }
            System.out.println("核对完成");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long generateWeekSettlementSheet(long storeUserId, int year, int week) {
        List<SettlementSheetDetail> detailList = settlementSheetDetailRepository.findByStoreUserIdAndYearAndWeekAndStatus(storeUserId, year, week, SettlementSheetDetailStatus.WAIT_SETTLE);
        SettlementSheet ss = new SettlementSheet();
        this.checkSettlementSheet(ss, detailList);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        ss.setStartDate(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
        ss.setEndDate(cal.getTime());
        ss.setStoreUser(storeUserRepository.findOne(storeUserId));
        ss.setYear(year);
        ss.setWeek(week);
        settlementSheetRepository.save(ss);
        settlementSheetDetailRepository.save(detailList);
        return ss.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkSettlementSheet(long id) {
        SettlementSheet ss = settlementSheetRepository.findOne(id);
        List<SettlementSheetDetail> detailList = settlementSheetDetailRepository.findBySettlementSheetId(id);
        detailList.forEach(detail -> detail.setStatus(SettlementSheetDetailStatus.WAIT_SETTLE));
        this.checkSettlementSheet(ss, detailList);
        settlementSheetRepository.save(ss);
        settlementSheetDetailRepository.save(detailList);
    }

    private void checkSettlementSheet(SettlementSheet ss, List<SettlementSheetDetail> detailList) {
        float totalOrderAmount = 0, totalRefundAmount = 0, totalDeductAmount = 0, totalCansunAmount = 0;
        int totalOrderNum = 0, totalRefundNum = 0, cansunNum = 0;
        boolean flag = true;
        for (SettlementSheetDetail ssd : detailList) {
            flag = flag && ssd.getStatus() == SettlementSheetDetailStatus.WAIT_SETTLE;
        }
        if (flag) {
            for (SettlementSheetDetail ssd : detailList) {
                this.recheckSettlementSheetDetail(ssd);
            }
            for (SettlementSheetDetail ssd : detailList) {
                totalOrderAmount += ssd.getOrderAmount();
                totalOrderNum += ssd.getOrderNum();
                totalRefundAmount += ssd.getPartRefundAmount();
                totalRefundNum += ssd.getPartRefundNum();
                totalDeductAmount += ssd.getDeductAmount();
                totalCansunAmount += ssd.getCansunAmount();
                cansunNum += ssd.getCansunNum();
                ssd.setSettlementSheet(ss);
                ssd.setStatus(SettlementSheetDetailStatus.SETTLED);
            }
            ss.setStatus(SettlementSheetStatus.WAIT_SETTLE);
        } else {
            ss.setStatus(SettlementSheetStatus.WAIT_CHECK);
        }
        ss.setDeductAmount(totalDeductAmount);
        ss.setCansunAmount(totalCansunAmount);
        ss.setCansunNum(cansunNum);
        ss.setOrderAmount(totalOrderAmount);
        ss.setOrderNum(totalOrderNum);
        ss.setPartRefundAmount(totalRefundAmount);
        ss.setPartRefundNum(totalRefundNum);
        ss.setCreateTime(new Date());
        ss.setUpdateTime(ss.getCreateTime());
        ss.setSettled(false);
        ss.setUpdateTime(ss.getCreateTime());
        ss.setSettlementAmount(totalOrderAmount + totalCansunAmount - totalRefundAmount - totalDeductAmount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateDailySettlementSheetDetail(long storeUserId, Date date) {
        Date startTime = DateUtils.truncate(date, Calendar.DATE);
        Date endTime = DateUtils.truncate(DateUtils.addDays(startTime, 1), Calendar.DATE);
        /**
         * 判断当前是否有待检查的日结算明细单，如果有，则返回，如果没有，则生成
         * */
        SettlementSheetDetail ssd = settlementSheetDetailRepository.findByStoreUserIdAndDateBetweenAndStatus(storeUserId, startTime, endTime, SettlementSheetDetailStatus.WAIT_CHECK);
        if (ssd == null) {
            ssd = new SettlementSheetDetail();
            ssd.setDate(date);
            ssd.setCreateTime(new Date());
            int[] yearWeek = DateHelper.yearAndWeek(date);
            ssd.setYear(yearWeek[0]);
            ssd.setWeek(yearWeek[1]);
            StoreUser storeUser = storeUserRepository.findOne(storeUserId);
            ssd.setStoreUser(storeUser);
            ssd.setOrderAmount(0f);
            ssd.setOrderNum(0);
            ssd.setCansunNum(0);
            ssd.setCansunAmount(0f);
            ssd.setDeductAmount(0f);
            ssd.setPartRefundAmount(0f);
            ssd.setPartRefundNum(0);
            ssd.setSettlementAmount(0f);
        }
        checkSettlementSheetDetail(ssd);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recheckSettlementSheetDetail(long id) {
        SettlementSheetDetail ssd = settlementSheetDetailRepository.findOne(id);
        recheckSettlementSheetDetail(ssd);
    }

    private void recheckSettlementSheetDetail(SettlementSheetDetail ssd) {
        if (ssd.getStatus() != SettlementSheetDetailStatus.WAIT_SETTLE) {
            throw new BizException("只有待结算的订单才能重检");
        }
        List<Long> orderIdList = Arrays.asList(ssd.getOrderIds().split(",")).stream().filter(orderId -> orderId.trim().length() > 0)
                .map(orderId -> Long.valueOf(orderId)).collect(Collectors.toList());
        List<Order> orderList = orderRepository.findByIdIn(orderIdList);
        StoreUser storeUser = ssd.getStoreUser();
        int deductPoint = storeUser.getDeductPoint();
        detectSsdOrder(ssd, orderList, deductPoint);
        settlementSheetDetailRepository.save(ssd);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reGenerateDailySettlementSheetDetail(long settlementSheetDetailId) {
        SettlementSheetDetail ssd = settlementSheetDetailRepository.findOne(settlementSheetDetailId);
        if (ssd.getStatus() != SettlementSheetDetailStatus.WAIT_CHECK) {
            throw new BizException("当前日结算单不是待确认状态，无法重新生成");
        }
        checkSettlementSheetDetail(ssd);
    }

    private void checkSettlementSheetDetail(SettlementSheetDetail ssd) {
        Date startTime = DateUtils.truncate(ssd.getDate(), Calendar.DATE);
        Date endTime = DateUtils.truncate(DateUtils.addDays(startTime, 1), Calendar.DATE);
        StoreUser storeUser = ssd.getStoreUser();
        int deductPoint = storeUser.getDeductPoint();
        List<Store> storeList = storeRepository.findByStoreUserId(storeUser.getId());
        List<Long> storeIdList = storeList.stream().map(o -> o.getId()).collect(Collectors.toList());
        //如果id是空的，那么就是新生成的。新生成的就需要检查订单
        int dayOrderNum = orderRepository.countByCreateTimeBetweenAndSettlementSheetDetailIsNullAndStoreIdInAndBizStatusNotIn(startTime, endTime, storeIdList, Arrays.asList(OrderBizStatus.IGNORE,
                OrderBizStatus.SETTLED));
        if (dayOrderNum == 0) {
            //未找到订单，不进行结算
            return;
        }
        List<Order> orderList = null;
        /**
         * 判断当前有没有异常的订单，如果有，则设置订单状态为待检查
         * */
        int unFinishedOrderNum = orderService.countStoreUnFinishedOrder(storeIdList, startTime, endTime);
        if (unFinishedOrderNum > 0) {
            ssd.setStatus(SettlementSheetDetailStatus.WAIT_CHECK);
        } else {
            ssd.setStatus(SettlementSheetDetailStatus.WAIT_SETTLE);
            orderList = orderRepository.findByCreateTimeBetweenAndStatusAndStoreIdInAndBizStatus(startTime, endTime,
                    FINISHED, storeIdList, OrderBizStatus.WAIT_SETTLE);
            List<Order> list = orderRepository.findByCreateTimeBetweenAndStatusAndStoreIdInAndBizStatusAndCansunGreaterThan(startTime, endTime,
                    CANCELED, storeIdList, OrderBizStatus.WAIT_SETTLE, 0F);
            orderList.addAll(list);
            detectSsdOrder(ssd, orderList, deductPoint);
            List<String> orderIds = orderList.stream().map(o -> String.valueOf(o.getId())).collect(Collectors.toList());
            ssd.setOrderIds(String.join(",", orderIds));
        }
        settlementSheetDetailRepository.save(ssd);
        if (orderList != null) {
            orderRepository.save(orderList);
        }
    }

    private void detectSsdOrder(SettlementSheetDetail ssd, List<Order> orderList, int deductPoint) {
        float orderAmount = 0, refundAmount = 0, cansunAmount = 0;
        int orderNum = 0, refundNum = 0, cansunNum = 0;
        for (Order order : orderList) {
            orderService.checkOrder(order);
        }
        for (Order order : orderList) {
            orderNum++;
            if (order.getStatus() == OrderStatus.CANCELED) {
                cansunAmount += order.getCansun();
                cansunNum++;
            } else {
                orderAmount += order.getCostTotal();
                if (order.getCansun() > 0) {
                    cansunAmount += order.getCansun();
                    cansunNum++;
                }
                if (order.getRefundStatus() == AGREE) {
                    //退款
                    refundNum++;
                    refundAmount += order.getCostRefund();
                }
            }

            order.setBizStatus(OrderBizStatus.SETTLED);
            order.setSettlementSheetDetail(ssd);

        }

        ssd.setOrderAmount(orderAmount);
        ssd.setOrderNum(orderNum);
        ssd.setPartRefundAmount(refundAmount);
        ssd.setPartRefundNum(refundNum);
        ssd.setCansunAmount(cansunAmount);
        ssd.setCansunNum(cansunNum);
        float validAmount = orderAmount + cansunAmount - refundAmount;
        float deductAmount = deductPoint * validAmount / 100;
        ssd.setDeductAmount(deductAmount);
        ssd.setSettlementAmount(validAmount - deductAmount);
    }

    private SettlementSheet createNew(long storeUserId, Date startDate, Date endDate) {
        SettlementSheet ss = new SettlementSheet();
        ss.setCreateTime(new Date());
        ss.setStartDate(DateUtils.truncate(startDate, Calendar.DATE));
        ss.setEndDate(DateUtils.truncate(endDate, Calendar.DATE));
        ss.setSettlementAmount(0f);
        ss.setPartRefundNum(0);
        ss.setPartRefundAmount(0f);
        ss.setOrderNum(0);
        ss.setOrderAmount(0f);
        ss.setCansunNum(0);
        ss.setCansunAmount(0f);
//        ss.setDetailList(new ArrayList<>());
        ss.setSettled(false);
        ss.setStoreUser(storeUserRepository.findOne(storeUserId));
        return ss;
    }
}
