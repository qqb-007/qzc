package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.StoreUserWithdraw;
import info.batcloud.wxc.core.enums.WithdrawStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StoreUserWithdrawRepository extends PagingAndSortingRepository<StoreUserWithdraw, Long>, JpaSpecificationExecutor<StoreUserWithdraw> {

    List<StoreUserWithdraw> findByWalletFlowDetailIdIn(List<Long> detailIds);

    StoreUserWithdraw findByWalletFlowDetailId(long detailId);

    StoreUserWithdraw findTopByStoreUserIdOrderByIdDesc(long userId);

    List<StoreUserWithdraw> findByStatusOrderByIdAsc(WithdrawStatus status);
}
