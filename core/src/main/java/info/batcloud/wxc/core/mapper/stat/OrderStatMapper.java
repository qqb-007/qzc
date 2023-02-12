package info.batcloud.wxc.core.mapper.stat;

import info.batcloud.wxc.core.enums.OrderBizStatus;
import info.batcloud.wxc.core.enums.OrderStatus;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.mapper.stat.domain.FoodSupplierBizData;
import info.batcloud.wxc.core.mapper.stat.domain.StoreUserBizData;
import info.batcloud.wxc.core.mapper.stat.domain.SupplierFoodSaleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderStatMapper {

    StoreUserBizData statStoreUserDayBizDataByStoreUserId(@Param("storeUserId") long storeUserId, @Param("minCreateTime") Date minCreateTime,
                                                          @Param("maxCreateTime") Date maxCreateTime);

    List<Long> statStoreUserIdHasValidOrder(@Param("startTime") Date startTime,
                                            @Param("endTime") Date endTime, @Param("status") OrderStatus status, @Param("bizStatus") OrderBizStatus bizStatus,@Param("plat") Plat plat);

    FoodSupplierBizData statFoodSupplierDayBizDataByFoodSupplierId(@Param("foodSupplierId") long foodSupplierId,
                                                                    @Param("minCreateTime") Date minCreateTime,
                                                             @Param("maxCreateTime") Date maxCreateTime);

    List<SupplierFoodSaleInfo> statFoodSaleInfoOfFoodSupplier(@Param("foodSupplierId") long foodSupplierId, @Param("minCreateTime") Date minCreateTime,
                                                              @Param("maxCreateTime") Date maxCreateTime);
}
