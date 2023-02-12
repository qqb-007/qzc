package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.SettlementSheetDetail;
import info.batcloud.wxc.core.enums.SettlementSheetDetailStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface SettlementSheetDetailRepository extends JpaSpecificationExecutor<SettlementSheetDetail>, PagingAndSortingRepository<SettlementSheetDetail, Long> {

    SettlementSheetDetail findByStoreUserIdAndDateBetweenAndStatus(long storeUserId, Date startTime, Date endTime, SettlementSheetDetailStatus status);
    List<SettlementSheetDetail> findByStoreUserIdAndYearAndWeekAndStatus(long storeUserId, int year, int week, SettlementSheetDetailStatus status);

    List<SettlementSheetDetail> findBySettlementSheetId(long settlementSheetId);

    List<SettlementSheetDetail> findByCreateTimeAfterAndStatus(Date time,SettlementSheetDetailStatus status);

    List<SettlementSheetDetail> findByCreateTimeBetweenAndStatus(Date startTime,Date endTime,SettlementSheetDetailStatus status);
}

