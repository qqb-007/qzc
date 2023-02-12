package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.FoodSupplier;
import info.batcloud.wxc.core.enums.FoodSupplierStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FoodSupplierRepository extends PagingAndSortingRepository<FoodSupplier, Long>, JpaSpecificationExecutor<FoodSupplier> {

    FoodSupplier findByPhoneAndStatusNot(String phone, FoodSupplierStatus status);

    int countByPhone(String phone);

    List<FoodSupplier> findByStatus(FoodSupplierStatus status);

    int countByName(String name);

    int countByNameAndIdNot(String name, long id);

    int countByPhoneAndIdNot(String phone, long id);

}