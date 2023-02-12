package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.enums.SettleType;
import info.batcloud.wxc.core.enums.StoreUserStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface StoreUserRepository extends PagingAndSortingRepository<StoreUser, Long>, JpaSpecificationExecutor<StoreUser> {

    StoreUser findByPhoneAndStatusNot(String phone, StoreUserStatus status);

    StoreUser findByFeiePrinterSn(String feiePrinterSn);

    List<StoreUser> findByMachineCodeIsNotNull();

    int countByPhone(String phone);

    List<StoreUser> findByStatusAndEleOpenedTrueAndOpenedTrue(StoreUserStatus status);

    List<StoreUser> findByStatusAndOpened(StoreUserStatus status, Boolean opened);

    List<StoreUser> findByStatus(StoreUserStatus status);

    List<StoreUser> findAllByMachineCodeIsNotNull();

    List<StoreUser> findByStatusAndSettleType(StoreUserStatus status, SettleType settleType);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user set biz_manager_id=?2 where biz_manager_id=?1")
    int updateBizManagerId(long originBizManagerId, long currentBizManagerId);

    List<StoreUser> findByLastSettlementDateBefore(Date date);

    int countByCode(String code);

    int countByName(String name);

    int countByCodeAndIdNot(String code, long id);

    int countByNameAndIdNot(String name, long id);

    int countByPhoneAndIdNot(String phone, long id);

}