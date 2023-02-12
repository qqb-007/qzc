package info.batcloud.wxc.background.task.scheduler;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.StoreUserFoodDTO;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.entity.StoreUserFood;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.enums.PublishStatus;
import info.batcloud.wxc.core.enums.QuoteStatus;
import info.batcloud.wxc.core.enums.StoreUserFoodSort;
import info.batcloud.wxc.core.repository.FoodRepository;
import info.batcloud.wxc.core.repository.StoreUserFoodRepository;
import info.batcloud.wxc.core.service.StoreUserFoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class StoreUserFoodScheduler {

    @Inject
    private ThreadPoolExecutor threadPoolExecutor;

    @Inject
    @Lazy
    private StoreUserFoodService storeUserFoodService;

    @Inject
    private TransactionTemplate transactionTemplate;

    @Inject
    private StoreUserFoodRepository storeUserFoodRepository;

    @Inject
    private FoodRepository foodRepository;

    private Logger logger = LoggerFactory.getLogger(StoreUserFoodScheduler.class);

    @Scheduled(cron = "${cron.storeUserFood.auto.verify}")
    public void verify() {
        logger.info("开始自动审核商品");
        StoreUserFoodService.SearchParam param = new StoreUserFoodService.SearchParam();
        param.setSort(StoreUserFoodSort.ID_DESC);
        param.setQuoteStatus(QuoteStatus.WAIT_VERIFY);
        param.setPageSize(500);
        param.setPage(1);
        param.setPromotional(false);
        /**
         * 发布待审核商品
         * */
        while (true) {
            Paging<StoreUserFoodDTO> paging = storeUserFoodService.search(param);
            List<StoreUserFoodDTO> result = paging.getResults();
            if (result.size() == 0) {
                break;
            }
            logger.info("找到待审核的商品" + result.size() + "个");
            for (StoreUserFoodDTO dto : result) {
                if (dto.getAlterQuotePrice() <= dto.getQuotePrice()) {
                    logger.info("自动审核并发布商品:" + dto.getFood().getName());
                    storeUserFoodService.verifyQuoteById(dto.getId());
                    try {
                        Thread.sleep(300L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("自动审核并发布商品：ID:" + dto.getId());
                    StoreUserFoodDTO publishDto = storeUserFoodService.publish(dto.getId(), true);
                    logger.info(publishDto.getPublishMsg());
                    logger.info("自动审核并发布商品：ID:" + dto.getId());
                }
            }
            param.setMaxId(result.get(result.size() - 1).getId());
        }
        logger.info("商品自动审核完成");

    }


    @Scheduled(cron = "${cron.storeUserFood.auto.publish}")
    public void publish() {
        List<Food> foods = foodRepository.findByUpdateTime(new Date(10000));
        for (Food food : foods) {
            try {
                logger.info("自动发布换图商品" + food.getName());
                food.setUpdateTime(new Date());
                foodRepository.save(food);
                StoreUserFoodService.SearchParam param = new StoreUserFoodService.SearchParam();
                param.setSort(StoreUserFoodSort.ID_DESC);
                param.setQuoteStatus(QuoteStatus.VERIFY_SUCCESS);
                //param.setWaitPublish(true);
                param.setPageSize(100);
                param.setPage(1);
                //param.setPromotional(false);
                param.setFoodId(food.getId());
                param.setSale(true);

                while (true) {
                    Paging<StoreUserFoodDTO> paging = storeUserFoodService.search(param);
                    List<StoreUserFoodDTO> result = paging.getResults();
                    if (result.size() == 0) {
                        break;
                    }
                    logger.info("找到换图待发布的商品" + result.size() + "个");
                    for (StoreUserFoodDTO dto : result) {
                        try {
                            Thread.sleep(300L);
                            logger.info("自动发布换图商品：ID:" + dto.getId());
                            List<Plat> list = new ArrayList<>();
                            if (dto.getMeituanPublishStatus() != PublishStatus.IN_STOCK) {
                                list.add(Plat.MEITUAN);
                            }
                            if (dto.getElePublishStatus() != PublishStatus.IN_STOCK) {
                                list.add(Plat.ELE);
                            }
                            if (dto.getJddjPublishStatus() != PublishStatus.IN_STOCK) {
                                list.add(Plat.JDDJ);
                            }
                            if (dto.getClbmPublishStatus() != PublishStatus.IN_STOCK) {
                                list.add(Plat.CLBM);
                            }
                            storeUserFoodService.backgroundPublish(dto.getId(), true, list);
                            //StoreUserFoodDTO publishDto = storeUserFoodService.publish(dto.getId(), true);
//                    if(publishDto.getMeituanPublishStatus() != PublishStatus.SUCCESS
//                            || publishDto.getElePublishStatus() != PublishStatus.SUCCESS) {
//                        logger.error("商品发布失败，原因为：" + publishDto.getPublishMsg());
//                    }
                            logger.info("自动发布换图商品完成：ID:" + dto.getId());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Integer page = param.getPage();
                    page = page + 1;
                    param.setPage(page);
                }
                logger.info(food.getName() + "换图发布完成");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        //foodRepository.save(foods);
    }

    @Scheduled(cron = "${cron.storeUserFood.auto.unlock}")
    private void unlock() {
        //每天1点到2点，每隔十分钟执行一次解锁报价的操作
        logger.info("开始自动解锁商品报价");
        long unlockTime = (System.currentTimeMillis() - 3600 * 2 * 1000) / 1000;
        List<StoreUserFood> storeUserFoods = storeUserFoodRepository.findByUnlockTimeBetweenAndQuotePriceLock(unlockTime, System.currentTimeMillis() / 1000, true);
        logger.info("找到待解锁商品" + storeUserFoods.size() + "个");
        if (storeUserFoods.size() > 0) {
            for (StoreUserFood storeUserFood : storeUserFoods) {
                try {
                    storeUserFoodService.unlockQuotePrice(storeUserFood.getId());
                    //解锁后下架对应商品
                    storeUserFoodService.soldOutById(storeUserFood.getId());
                } catch (Exception e) {
                    logger.error("解锁或下架出错" + e.getMessage());
                }
            }
        }

    }

    @Scheduled(cron = "${cron.storeUserFood.auto.fail}")
    private void publishFail() {
        StoreUserFoodService.SearchParam searchParam = new StoreUserFoodService.SearchParam();
        searchParam.setElePublishStatus(PublishStatus.FAIL);
        searchParam.setPageSize(3000);
        Paging<StoreUserFoodDTO> search = storeUserFoodService.search(searchParam);
        List<StoreUserFoodDTO> results = search.getResults();
        List<Plat> list = new ArrayList<>();
        list.add(Plat.ELE);
        logger.info("找到发布失败的饿了么商品" + results.size());
        for (StoreUserFoodDTO result : results) {
            logger.info(result.getFood().getName());
            try {
                storeUserFoodService.backgroundPublish(result.getId(), true, list);
            } catch (Exception e) {

            }

        }

        logger.info("开始发布美团发布失败商品");

        searchParam.setElePublishStatus(null);
        searchParam.setMeituanPublishStatus(PublishStatus.FAIL);
        Paging<StoreUserFoodDTO> search1 = storeUserFoodService.search(searchParam);
        List<StoreUserFoodDTO> results1 = search1.getResults();
        list.clear();
        list.add(Plat.MEITUAN);
        for (StoreUserFoodDTO storeUserFoodDTO : results1) {
            logger.info(storeUserFoodDTO.getFood().getName());
            try {
                storeUserFoodService.backgroundPublish(storeUserFoodDTO.getId(), true, list);
            } catch (Exception e) {

            }
        }


        logger.info("开始发布菜老板发布失败商品");

        searchParam.setMeituanPublishStatus(null);
        searchParam.setClbmPublishStatus(PublishStatus.FAIL);
        Paging<StoreUserFoodDTO> search2 = storeUserFoodService.search(searchParam);
        List<StoreUserFoodDTO> results2 = search2.getResults();
        list.clear();
        list.add(Plat.CLBM);
        for (StoreUserFoodDTO storeUserFoodDTO : results2) {
            logger.info(storeUserFoodDTO.getFood().getName());
            try {
                storeUserFoodService.backgroundPublish(storeUserFoodDTO.getId(), true, list);
            } catch (Exception e) {

            }
        }
    }

}
