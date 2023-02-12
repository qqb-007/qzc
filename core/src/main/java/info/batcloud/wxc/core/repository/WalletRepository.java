package info.batcloud.wxc.core.repository;


import info.batcloud.wxc.core.entity.Wallet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Long> , JpaSpecificationExecutor<Wallet> {

    Wallet findByStoreUserId(long storeUserId);

}
