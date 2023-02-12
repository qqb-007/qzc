package info.batcloud.wxc.core.service;

import java.util.Date;

public interface SettlementService {

    void batchReCheck(Date date);

    void batchCheck(Date date);

    Long generateWeekSettlementSheet(long storeUserId, int year, int week);

    void checkSettlementSheet(long id);

    void generateDailySettlementSheetDetail(long storeUserId, Date date);

    void recheckSettlementSheetDetail(long id);

    void reGenerateDailySettlementSheetDetail(long settlementSheetDetailId);

}
