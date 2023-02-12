package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.StoreUserFood;
import info.batcloud.wxc.core.enums.PublishStatus;
import info.batcloud.wxc.core.enums.QuoteStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StoreUserFoodRepository extends PagingAndSortingRepository<StoreUserFood, Long>, JpaSpecificationExecutor<StoreUserFood> {

    List<StoreUserFood> findByUnlockTimeBetweenAndQuotePriceLock(Long start,Long end,boolean lock);
    List<StoreUserFood> findByStoreUserIdAndSaleTrueAndElePublishStatus(long storeUserId,PublishStatus status);
    List<StoreUserFood> findByStoreUserIdAndFoodIdIn(long storeUserId, List<Long> foodIdList);
    List<StoreUserFood> findByStoreUserIdAndFoodActualFoodId(long storeUserId, long actualFoodId);
    StoreUserFood findByWanteId(Long id);
    List<StoreUserFood> findByStoreUserIdAndJddjPublishStatus(long storeUserId, PublishStatus status);

    //
    List<StoreUserFood> findByStoreUserIdAndFoodCodeIn(long storeUserId, List<String> foodCodeList);

    List<StoreUserFood> findTop100ByStoreUserIdInAndPriceIncreaseOrderByIdDesc(List<Long> storeUserIdList, float priceIncrease);

    List<StoreUserFood> findTop100ByStoreUserIdAndIdGreaterThanOrderByIdAsc(long storeUserId, long minId);

    List<StoreUserFood> findByIdIn(List<Long> idList);
    List<StoreUserFood> findByFoodIdOrFoodActualFoodIdAndSaleTrue(long foodId, long actualFoodId);

    StoreUserFood findByFoodSupplierIdAndId(long foodSupplierId, long id);

    //
    StoreUserFood findByStoreUserIdAndFoodId(long storeUserId, long foodId);
    StoreUserFood findByStoreUserIdAndId(long storeUserId, long foodId);

    //
    List<StoreUserFood> findByStoreUserIdInOrderByIdDesc(List<Long> storeUserIdList, Pageable pageable);

    List<StoreUserFood> findByStoreUserIdOrderByIdDesc(long storeUserId, Pageable pageable);

    List<StoreUserFood> findTop100ByStoreUserIdAndQuoteStatus(long storeUserId, QuoteStatus quoteStatus);

    List<StoreUserFood> findByFoodId(Long id);

    @Query(nativeQuery = true, value = "select if(c.id is null, f.category_name, c.name) from store_user_food suf left join food_category c on c.id=suf.category_id left join food f on f.id = suf.food_id " +
            " where suf.food_supplier_id=?1 group by if(c.id is null, f.category_name, c.name) ")
    List<String> aggregateCategoryByFoodSupplierId(long foodSupplierId);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set quote_status=?2 where store_user_id=?1")
    int updateQuoteStatusByStoreUserId(long storeUserId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set meituan_publish_status=?2, ele_publish_status=?2 where store_user_id=?1")
    int updatePublishStatusByStoreUserId(long storeUserId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set ele_publish_status = 'IN_STOCK' where id in (select a.id from (select f.id from store_user_food f LEFT JOIN store_user u on f.store_user_id = u.id where u.ele_opened = 0 and f.ele_publish_status = 'WAIT' and f.food_id = ?1) as a)")
    void resetEleStatus(long id);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set meituan_publish_status=?2, ele_publish_status=?2 where food_id=?1")
    int updatePublishStatusByFoodId(long foodId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set ele_publish_status=?2, sale=false where store_user_id=?1 and quote_status='VERIFY_SUCCESS' and (ele_publish_status='SUCCESS' or jddj_publish_status='SUCCESS' or clbm_publish_status='SUCCESS' or meituan_publish_status='SUCCESS' or wante_publish_status='SUCCESS')")
    int updateElePublishStatusByStoreUserId(long storeUserId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set ele_publish_status=?2 where store_user_id=?1 and (meituan_publish_status != 'SUCCESS' OR meituan_publish_status IS NULL) and (ele_publish_status != 'SUCCESS' OR ele_publish_status IS NULL) and (clbm_publish_status != 'SUCCESS' OR clbm_publish_status IS NULL) and (jddj_publish_status != 'SUCCESS' OR jddj_publish_status IS NULL)")
    int updateElePublishInstockByStoreUserId(long storeUserId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set meituan_publish_status=?2 where store_user_id=?1 and (meituan_publish_status != 'SUCCESS' OR meituan_publish_status IS NULL) and (ele_publish_status != 'SUCCESS' OR ele_publish_status IS NULL) and (clbm_publish_status != 'SUCCESS' OR clbm_publish_status IS NULL) and (jddj_publish_status != 'SUCCESS' OR jddj_publish_status IS NULL)")
    int updateMeituanPublishInstockByStoreUserId(long storeUserId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set clbm_publish_status=?2 where store_user_id=?1 and (clbm_publish_status != 'SUCCESS' OR clbm_publish_status IS NULL) and (meituan_publish_status != 'SUCCESS' OR meituan_publish_status IS NULL) and (ele_publish_status != 'SUCCESS' OR ele_publish_status IS NULL) and (jddj_publish_status != 'SUCCESS' OR jddj_publish_status IS NULL)")
    int updateClbmPublishInstockByStoreUserId(long storeUserId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set jddj_publish_status=?2 where store_user_id=?1 and (jddj_publish_status != 'SUCCESS' OR jddj_publish_status IS NULL) and (meituan_publish_status != 'SUCCESS' OR meituan_publish_status IS NULL) and (clbm_publish_status != 'SUCCESS' OR clbm_publish_status IS NULL) and (ele_publish_status != 'SUCCESS' OR ele_publish_status IS NULL)")
    int updateJddjPublishInstockByStoreUserId(long storeUserId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set jddj_publish_status=?2, sale=false where store_user_id=?1 and quote_status='VERIFY_SUCCESS' and (ele_publish_status='SUCCESS' or jddj_publish_status='SUCCESS' or clbm_publish_status='SUCCESS' or meituan_publish_status='SUCCESS' or wante_publish_status='SUCCESS')")
    int updateJddjPublishStatusByStoreUserId(long storeUserId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set clbm_publish_status=?2, sale=false where store_user_id=?1 and quote_status='VERIFY_SUCCESS' and (ele_publish_status='SUCCESS' or jddj_publish_status='SUCCESS' or clbm_publish_status='SUCCESS' or meituan_publish_status='SUCCESS' or wante_publish_status='SUCCESS')")
    int updateClbmPublishStatusByStoreUserId(long storeUserId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set wante_publish_status=?2, sale=false where store_user_id=?1 and quote_status='VERIFY_SUCCESS' and (ele_publish_status='SUCCESS' or jddj_publish_status='SUCCESS' or clbm_publish_status='SUCCESS' or meituan_publish_status='SUCCESS' or wante_publish_status='SUCCESS')")
    int updateWantePublishStatusByStoreUserId(long storeUserId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set meituan_publish_status=?2, sale=false where store_user_id=?1 and quote_status='VERIFY_SUCCESS' and (ele_publish_status='SUCCESS' or jddj_publish_status='SUCCESS' or clbm_publish_status='SUCCESS' or meituan_publish_status='SUCCESS' or wante_publish_status='SUCCESS')")
    int updateMeituanPublishStatusByStoreUserId(long storeUserId, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set ele_publish_status=?3 where store_user_id=?1 and food_id in (?2)")
    int updateElePublishStatusByStoreUserIdAndFoodIdList(long storeUserId, List<Long> foodIdList, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set jddj_publish_status=?3 where store_user_id=?1 and food_id in (?2)")
    int updateJddjPublishStatusByStoreUserIdAndFoodIdList(long storeUserId, List<Long> foodIdList, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set clbm_publish_status=?3 where store_user_id=?1 and food_id in (?2)")
    int updateClbmPublishStatusByStoreUserIdAndFoodIdList(long storeUserId, List<Long> foodIdList, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set meituan_publish_status=?3 where store_user_id=?1 and food_id in (?2)")
    int updateMeituanPublishStatusByStoreUserIdAndFoodIdList(long storeUserId, List<Long> foodIdList, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set wante_publish_status=?3 where store_user_id=?1 and food_id in (?2)")
    int updateWantePublishStatusByStoreUserIdAndFoodIdList(long storeUserId, List<Long> foodIdList, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set meituan_publish_status=?2 where id in (?1)")
    int updateMeituanPublishStatusByIdList(List<Long> foodIdList, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set ele_publish_status=?2 where id in (?1)")
    int updateElePublishStatusByIdList(List<Long> foodIdList, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set quote_status=?2 where id in (?1)")
    int updateQuoteStatusById(List<Long> foodIdList, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update store_user_food set meituan_video_id= null,clbm_video_id = null where food_id =?1")
    int updateVideoIdByFoodId(long foodId);

}
