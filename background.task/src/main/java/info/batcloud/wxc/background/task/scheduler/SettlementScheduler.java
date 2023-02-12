package info.batcloud.wxc.background.task.scheduler;

import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.enums.SettleType;
import info.batcloud.wxc.core.enums.StoreUserStatus;
import info.batcloud.wxc.core.helper.DateHelper;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.SettlementService;
import info.batcloud.wxc.core.service.SettlementSheetService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;

@Component
public class SettlementScheduler {

    @Inject
    private SettlementService settlementService;

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private SettlementSheetService settlementSheetService;

    private static final Logger logger = LoggerFactory.getLogger(SettlementScheduler.class);

    @Scheduled(cron = "${cron.order.settlement.weekly.generate}")
    public void generateWeekSettlementSheet() {
        logger.info("开始生成前一周的周结算单");
        /**
         * 生成前一周的周结算单
         * */
        Iterable<StoreUser> storeUserList = storeUserRepository.findByStatusAndSettleType(StoreUserStatus.VALID, SettleType.SYSTEM);
        int[] yearWeek = DateHelper.yearAndWeek(DateUtils.addWeeks(new Date(), -1));
        storeUserList.forEach(storeUser -> {
            try {
                logger.info("开始生成 " + storeUser.getName() + "周结算单");
                Long id = settlementService.generateWeekSettlementSheet(storeUser.getId(), yearWeek[0], yearWeek[1]);
                logger.info("生成周结算单成功,开始自动结算到商家余额");
                Result rs = settlementSheetService.settleToWallet(id);
                if (rs.isSuccess()) {
                    logger.info("自动结算到商家余额成功");
                } else {
                    logger.error(rs.getMsg());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Scheduled(cron = "${cron.order.settlement.daily.generate}")
    public void generateDailySettlementSheet() {
        /**
         * 生成前一天的日结算单
         * */
        Iterable<StoreUser> storeUserList = storeUserRepository.findByStatusAndSettleType(StoreUserStatus.VALID, SettleType.SYSTEM);
        Date now = new Date();
        Date yesterday = DateUtils.addDays(now, -1);
        storeUserList.forEach(storeUser -> {
            try {
                settlementService.generateDailySettlementSheetDetail(storeUser.getId(), yesterday);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
