package info.batcloud.wxc.core.repository;


import info.batcloud.wxc.core.entity.WalletFlowDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WalletFlowDetailRepository extends PagingAndSortingRepository<WalletFlowDetail, Long>, JpaSpecificationExecutor<WalletFlowDetail> {
}
