package info.batcloud.wxc.core.service.impl;

import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.WalletFlowDetailDTO;
import info.batcloud.wxc.core.entity.StoreUserWithdraw;
import info.batcloud.wxc.core.entity.WalletFlowDetail;
import info.batcloud.wxc.core.enums.EnumTitle;
import info.batcloud.wxc.core.enums.WalletFlowDetailType;
import info.batcloud.wxc.core.helper.OSSImageHelper;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.helper.ZipHelper;
import info.batcloud.wxc.core.repository.StoreUserWithdrawRepository;
import info.batcloud.wxc.core.repository.WalletFlowDetailRepository;
import info.batcloud.wxc.core.service.OssService;
import info.batcloud.wxc.core.service.StoreUserService;
import info.batcloud.wxc.core.service.TmpFileService;
import info.batcloud.wxc.core.service.WalletFlowDetailService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WalletFlowDetailServiceImpl implements WalletFlowDetailService {

    @Inject
    private WalletFlowDetailRepository walletFlowDetailRepository;
    @Inject
    private StoreUserWithdrawRepository storeUserWithdrawRepository;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private TmpFileService tmpFileService;

    @Inject
    private RedisTemplate<String, String> redisTemplate;

    @Inject
    private OssService ossService;

    @Override
    public Paging<WalletFlowDetailDTO> search(SearchParam param) {
        Specification<WalletFlowDetail> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (query.getResultType() != Long.class) {
                root.fetch("storeUser", JoinType.LEFT);
            }
            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (param.getType() != null) {
                expressions.add(cb.equal(root.get("type"), param.getType()));
            }
            if (param.getValueType() != null) {
                expressions.add(cb.equal(root.get("valueType"), param.getValueType()));
            }
            if (param.getCreateTime() != null) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("createTime"),
                        DateUtils.truncate(DateUtils.addDays(param.getCreateTime(), -1), Calendar.DATE)));
                expressions.add(cb.lessThan(root.get("createTime"),
                        DateUtils.truncate(DateUtils.addDays(param.getCreateTime(), 1), Calendar.DATE)));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<WalletFlowDetail> page = walletFlowDetailRepository.findAll(specification, pageable);
        //处理withdraw的状态显示
        List<Long> withdrawDetailIds = page.getContent().stream().filter(detail -> detail.getType()
                == WalletFlowDetailType.WITHDRAW).map(detail -> detail.getId())
                .collect(Collectors.toList());
        List<StoreUserWithdraw> storeUserWithdraws = storeUserWithdrawRepository.findByWalletFlowDetailIdIn(withdrawDetailIds);
        Map<Long, EnumTitle> storeUserWithdrawStatusMapByDetailId = new HashMap<>();
        Map<Long, Long> relationIdMapByDetailId = new HashMap<>();
        for (StoreUserWithdraw StoreUserWithdraw : storeUserWithdraws) {
            storeUserWithdrawStatusMapByDetailId.put(StoreUserWithdraw.getWalletFlowDetailId(), StoreUserWithdraw.getStatus());
            relationIdMapByDetailId.put(StoreUserWithdraw.getWalletFlowDetailId(), StoreUserWithdraw.getId());
        }
        return PagingHelper.of(page, detail -> {
            WalletFlowDetailDTO dto = new WalletFlowDetailDTO();
            BeanUtils.copyProperties(detail, dto);
            dto.setStatus(storeUserWithdrawStatusMapByDetailId.get(detail.getId()));
            dto.setRelationId(relationIdMapByDetailId.get(detail.getId()));
            dto.setStoreUser(storeUserService.toDTO(detail.getStoreUser()));
            return dto;
        }, param.getPage(), param.getPageSize());
    }

    @Override
    public String export(SearchParam param) throws IOException {
        List<File> files = new ArrayList<>();
        File wfile = exportDetail(param);
        files.add(wfile);
        File zipFile = tmpFileService.createFile(System.currentTimeMillis() + "_"
                + DateFormatUtils.format(param.getCreateTime(), "yyyy-MM-dd") + "_" + DateFormatUtils.format(param.getCreateTime(), "yyyy-MM-dd") + ".zip");
        boolean flag = ZipHelper.packageZip(zipFile, files);
        if (flag) {
            try {
                CompleteMultipartUploadResult result = ossService.uploadFile(zipFile, "order-export/" + zipFile.getName());
                redisTemplate.opsForValue().set("orderExportUrl", OSSImageHelper.toUrl(result.getKey()));
            } catch (Throwable throwable) {
                System.out.println("上传订单导出文件失败");
                return null;
            } finally {
                zipFile.delete();
                for (File file : files) {
                    file.delete();
                }
            }
        }
        return null;
    }

    private static String[] ORDER_TITLE = new String[]{"门店名称", "变更前余额", "存入", "变更后余额", "日期"};

    private File exportDetail(SearchParam param) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        File excelFile = tmpFileService.createFile("存入明细.xls");
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
        for (int i = 0; i < ORDER_TITLE.length; i++) {
            HSSFCell titleCell = titleRow.createCell(i);
            //给单元格设置内容
            titleCell.setCellValue(ORDER_TITLE[i]);
            titleCell.setCellStyle(foldStyle);
        }
        int rownum = 1;
        int pageSize = 100;
        param.setPageSize(pageSize);
        param.setPage(1);
        while (true) {
            Paging<WalletFlowDetailDTO> paging = this.search(param);
            for (int j = 0; j < paging.getResults().size(); j++) {
                HSSFRow row = sheet.createRow(rownum++);
                row.setHeightInPoints(20);
                WalletFlowDetailDTO dto = paging.getResults().get(j);
                for (int i = 0; i < ORDER_TITLE.length; i++) {
                    HSSFCell rowCell = row.createCell(i);
                    rowCell.setCellStyle(commonStyle);
                    switch (ORDER_TITLE[i]) {
                        case "门店名称":
                            rowCell.setCellValue(dto.getStoreUser().getName());
                            break;
                        case "变更前余额":
                            rowCell.setCellValue(dto.getBeforeValue());
                            break;
                        case "存入":
                            rowCell.setCellValue(dto.getValue());
                            break;
                        case "变更后余额":
                            rowCell.setCellValue(dto.getAfterValue());
                            break;
                        case "日期":
                            rowCell.setCellValue(simpleDateFormat.format(dto.getCreateTime()));
                            break;
                    }
                }
            }
            if (!paging.getHasNext()) {
                break;
            }
            param.setPage(param.getPage() + 1);
        }
        workbook.write(excelFile);
        return excelFile;
    }

}
