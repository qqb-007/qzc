package info.batcloud.wxc.core.service.impl;

import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.BagDTO;
import info.batcloud.wxc.core.dto.OrderDTO;
import info.batcloud.wxc.core.entity.Bag;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.enums.BagStatus;
import info.batcloud.wxc.core.enums.DeliveryStatus;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.OSSImageHelper;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.helper.ZipHelper;
import info.batcloud.wxc.core.repository.BagRepository;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BagServiceImpl implements BagService {

    @Inject
    private BagRepository bagRepository;

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private TmpFileService tmpFileService;

    @Inject
    private OssService ossService;

    @Inject
    private RedisTemplate<String, String> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(BagService.class);


    @Override
    public void update(Long id, Integer num) {
        Bag bag = bagRepository.findOne(id);
        if (bag.getStatus() != BagStatus.WAIT_VERIFY) {
            throw new BizException("该申请不是待审核状态，无法更改数量");
        }

        bag.setBagNum(num);
        bag.setFee((num / 100) * 9.9f);
        bagRepository.save(bag);
    }

    @Override
    public String download() {
        String url = redisTemplate.opsForValue().get("bagExportUrl");
        redisTemplate.delete("bagExportUrl");
        return url;
    }

    @Override
    public String export(ExportParam param) {
        List<File> files = new ArrayList<>();
        File file = null;
        try {
            file = exportWeekOrder(param);
        } catch (IOException e) {
            e.printStackTrace();
        }
        files.add(file);
        File zipFile = tmpFileService.createFile(System.currentTimeMillis() + "_"
                + DateFormatUtils.format(param.getStartTime(), "yyyy-MM-dd") + "_" + DateFormatUtils.format(param.getEndTime(), "yyyy-MM-dd") + ".zip");
        boolean flag = ZipHelper.packageZip(zipFile, files);
        if (flag) {
            try {
                CompleteMultipartUploadResult result = ossService.uploadFile(zipFile, "order-export/" + zipFile.getName());
                redisTemplate.opsForValue().set("bagExportUrl", OSSImageHelper.toUrl(result.getKey()));
            } catch (Throwable throwable) {
                logger.error("上传订单导出文件失败", throwable);
                return null;
            } finally {
                zipFile.delete();
                for (File file1 : files) {
                    file1.delete();
                }
            }
            logger.info("导出完成");
        } else {
            logger.error("下载出错");
        }
        return null;
    }

    private static String[] BAG_TITLE = new String[]{"门店名称", "申请数量", "袋子类型", "申请时间", "运费", "备注", "状态"};

    private File exportWeekOrder(ExportParam param) throws IOException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        String fileName = "购物袋数据";
        File excelFile = tmpFileService.createFile(fileName + ".xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1, true);
        HSSFRow titleRow = sheet.createRow(0);
        HSSFFont headFont = workbook.createFont();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 16);
        CellStyle foldStyle = workbook.createCellStyle();
        CellStyle commonStyle = workbook.createCellStyle();
        HSSFFont commonFont = workbook.createFont();
        commonFont.setFontHeightInPoints((short) 12);
        commonStyle.setFont(commonFont);
        foldStyle.setFont(headFont);
        for (int i = 0; i < BAG_TITLE.length; i++) {
            HSSFCell titleCell = titleRow.createCell(i);
            //给单元格设置内容
            titleCell.setCellValue(BAG_TITLE[i]);
            titleCell.setCellStyle(foldStyle);
        }
        int rownum = 1;
        //根据查询出来的订单信息来创建列，然后把列的数量对应加起来
        SearchParam sp = new SearchParam();
        sp.setCreateStartDate(param.getStartTime());
        sp.setCreateEndDate(param.getEndTime());
        int pageSize = 100;
        sp.setPageSize(pageSize);
        sp.setPage(1);
        while (true) {
            Paging<BagDTO> paging = this.search(sp);
            for (int j = 0; j < paging.getResults().size(); j++) {
                if (paging.getResults().get(j).getStatus() == BagStatus.REJECT) {
                    continue;
                }
                HSSFRow row = sheet.createRow(rownum++);
                row.setHeightInPoints(20);
                for (int i = 0; i < BAG_TITLE.length; i++) {
                    HSSFCell rowCell = row.createCell(i);
                    rowCell.setCellStyle(commonStyle);
                    switch (BAG_TITLE[i]) {
                        case "门店名称":

                            rowCell.setCellValue(paging.getResults().get(j).getStoreUser().getName());

                            break;
                        case "申请数量":
                            rowCell.setCellValue(paging.getResults().get(j).getBagNum());
                            break;
                        case "袋子类型":
                            rowCell.setCellValue(paging.getResults().get(j).getBagTypeTitle());
                            break;
                        case "申请时间":
                            rowCell.setCellValue(DateFormatUtils.format(paging.getResults().get(j).getCreateTime(), "yyyy.M.d"));
                            break;
                        case "运费":
                            rowCell.setCellValue(paging.getResults().get(j).getFee());
                            break;
                        case "备注":
                            rowCell.setCellValue(paging.getResults().get(j).getContext());
                            break;
                        case "状态":
                            rowCell.setCellValue(paging.getResults().get(j).getStatusTitle());
                            break;
                    }
                }
            }
            if (!paging.getHasNext()) {
                break;
            }
            sp.setPage(sp.getPage() + 1);
        }
        workbook.write(excelFile);
        return excelFile;

    }

    @Override
    public Paging<BagDTO> search(SearchParam param) {
        Specification<Bag> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (StringUtils.isNotBlank(param.getStoreUserName())) {
                expressions.add(cb.like(root.get("storeUser").get("name"), "%" + param.getStoreUserName() + "%"));
            }
            if (param.getCreateStartTime() != null) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("createTime"), param.getCreateStartTime()));
            }

            if (param.getCreateEndTime() != null) {
                expressions.add(cb.lessThanOrEqualTo(root.get("endTime"), param.getCreateEndTime()));
            }

            if (param.getCreateStartDate() != null) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("createTime"), DateUtils.truncate(param.getCreateStartDate(), Calendar.DATE)));
            }

            if (param.getCreateEndDate() != null) {
                expressions.add(cb.lessThan(root.get("createTime"),
                        DateUtils.truncate(DateUtils.addDays(param.getCreateEndDate(), 1), Calendar.DATE)));
            }

            if (param.getCreateEndTime() != null) {
                expressions.add(cb.lessThanOrEqualTo(root.get("endTime"), param.getCreateEndTime()));
            }

            if (param.getStatus() != null) {
                expressions.add(cb.equal(root.get("status"), param.getStatus()));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<Bag> page = bagRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item), param.getPage(), param.getPageSize());
    }

    private BagDTO toDTO(Bag bag) {
        BagDTO dto = new BagDTO();
        BeanUtils.copyProperties(bag, dto);
        dto.setStoreUser(storeUserService.toDTO(bag.getStoreUser()));
        return dto;
    }

    @Override
    public boolean applyBag(ApplyParam param) {
        List<Bag> bags = bagRepository.findByStoreUserIdAndStatus(param.getStoreUserId(), BagStatus.WAIT_VERIFY);
        for (Bag bag : bags) {
            if (bag.getBagType() == param.getBagType()) {
                throw new BizException("您上次的申请正在审核中，请耐心等待后再次申请");
            }
        }

        Bag bag = new Bag();
        bag.setStoreUser(storeUserRepository.findOne(param.getStoreUserId()));
        bag.setCreateTime(new Date());
        bag.setBagType(param.getBagType());
        bag.setBagNum(param.getBagNum());
        //bag.setLogistics();
        bag.setStatus(BagStatus.WAIT_VERIFY);
        bag.setFee((float) ((param.getBagNum() / 100) * 9.9));
        bag.setContext(" ");
        bagRepository.save(bag);
        return true;
    }

    @Override
    public void checkApply(long id, String context) {
        Bag bag = bagRepository.findOne(id);
        if (bag.getStatus() != BagStatus.WAIT_VERIFY) {
            throw new BizException("只有待审核申请才可以审核通过");
        }
        bag.setStatus(BagStatus.WAIT_DELIVERY);
        if (StringUtils.isNotBlank(context)) {
            bag.setContext(bag.getContext() + " " + context);
        }
        bagRepository.save(bag);
    }

    @Override
    public void addWuLiu(long id, String wl, String context) {
        Bag bag = bagRepository.findOne(id);
        if (bag.getStatus() != BagStatus.WAIT_DELIVERY) {
            throw new BizException("只有待发货的申请才能填写物流单号");
        }
        bag.setLogistics(wl);
        if (StringUtils.isNotBlank(context)) {
            bag.setContext(bag.getContext() + " " + context);
        }
        bag.setStatus(BagStatus.SHIPPED);
        bagRepository.save(bag);
    }

    @Override
    public void rejectApply(long id, String context) {
        Bag bag = bagRepository.findOne(id);
        if (bag.getStatus() != BagStatus.WAIT_VERIFY) {
            throw new BizException("只有待审核的申请才可以驳回");
        }
        bag.setContext(context);
        bag.setStatus(BagStatus.REJECT);
        bagRepository.save(bag);
    }
}
