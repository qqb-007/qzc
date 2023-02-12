package info.batcloud.wxc.admin.controller.report;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.mybatis.plugin.interceptor.PagingHelper;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.mapper.report.OrderBizDataMapper;
import info.batcloud.wxc.core.mapper.report.domain.OrderBizData;
import info.batcloud.wxc.core.mapper.report.domain.StoreUserOrderBizData;
import info.batcloud.wxc.core.mapper.stat.domain.StoreUserBizData;
import info.batcloud.wxc.core.service.TmpFileService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/api/order-biz-data")
public class OrderBizDataReportController {

    @Inject
    private OrderBizDataMapper orderBizDataMapper;

    @Inject
    private TmpFileService tmpFileService;

    @GetMapping()
    public Object rangeBizData(RangeBizDataParam param) {
        Date now = new Date();
        if (param.getStartDate() == null) {
            param.setStartDate(now);
        }
        if (param.getEndDate() == null) {
            param.setEndDate(now);
        }
        param.setStartDate(DateUtils.truncate(param.getStartDate(), Calendar.DATE));
        param.setEndDate(DateUtils.truncate(DateUtils.addDays(param.getEndDate(), 1), Calendar.DATE));
        List<OrderBizData> list;
        if (param.getType() == null || param.getType() == 0) {
            list = orderBizDataMapper.findOrderBizDataByDateRange(param.getStartDate(), param.getEndDate());
        } else {
            list = orderBizDataMapper.findOrderBizDataByDeliveryDateRange(param.getStartDate().getTime() / 1000, param.getEndDate().getTime() / 1000);
        }
        Map<String, Object> map = new HashMap<>(7);
        float total = 0, refundMoney = 0, costTotal = 0, costRefund = 0;
        int totalNum = 0;
        for (OrderBizData orderBizData : list) {
            map.put(orderBizData.getPlat().name(), orderBizData);
            total += orderBizData.getTotal();
            refundMoney += orderBizData.getRefundMoney();
            costTotal += orderBizData.getCostTotal();
            costRefund += orderBizData.getCostRefund();
            totalNum += orderBizData.getTotalNum();
        }
        map.put("total", total);
        map.put("refundMoney", refundMoney);
        map.put("costTotal", costTotal);
        map.put("costRefund", costRefund);
        map.put("totalNum", totalNum);
        return map;
    }

    @GetMapping("/store-user")
    public Object storeUserData(OrderBizDataMapper.StoreUserOrderBizDataParam param, @RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "20") int pageSize) {
        Date now = new Date();
        if (param.getStartDate() == null) {
            param.setStartDate(DateUtils.addDays(now, -6));
        }
        if (param.getEndDate() == null) {
            param.setEndDate(now);
        }
        param.setStartDate(DateUtils.truncate(param.getStartDate(), Calendar.DATE));
        param.setEndDate(DateUtils.truncate(DateUtils.addDays(param.getEndDate(), 1), Calendar.DATE));
        PagingHelper.setPage(page);
        PagingHelper.setPageSize(pageSize);
        orderBizDataMapper.findStoreUserOrderBizDataByDateRange(param);
        return PagingHelper.take();
    }

    private static String[] TITLE_LIST = new String[]{"日期", "商家", "有效单量", "有效营业额", "有效成本", "有效利润"};

    @GetMapping("/store-user/export")
    public Object storeUserDataExport(OrderBizDataMapper.StoreUserOrderBizDataParam param, HttpServletResponse response) throws IOException {
        Date now = new Date();
        if (param.getStartDate() == null) {
            param.setStartDate(DateUtils.addDays(now, -6));
        }
        if (param.getEndDate() == null) {
            param.setEndDate(now);
        }
        param.setStartDate(DateUtils.truncate(param.getStartDate(), Calendar.DATE));
        param.setEndDate(DateUtils.truncate(DateUtils.addDays(param.getEndDate(), 1), Calendar.DATE));
        File excelFile = tmpFileService.createFile("lrb_" + System.currentTimeMillis() + ".xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1, true);
        HSSFFont boldFond = workbook.createFont();
        boldFond.setBold(true);
        boldFond.setFontHeightInPoints((short) 16);
        CellStyle foldStyle = workbook.createCellStyle();
        CellStyle commonStyle = workbook.createCellStyle();
        HSSFFont commonFont = workbook.createFont();
        commonFont.setFontHeightInPoints((short) 14);
        commonStyle.setFont(commonFont);
        foldStyle.setFont(boldFond);
        HSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < TITLE_LIST.length; i++) {
            HSSFCell titleCell = titleRow.createCell(i);
            //给单元格设置内容
            titleCell.setCellValue(TITLE_LIST[i]);
            titleCell.setCellStyle(foldStyle);
        }
        int page = 1;
        int pageSize = 100;
        int rowIndex = 1;
        PagingHelper.setPageSize(pageSize);
        while (true) {
            PagingHelper.setPage(page++);
            orderBizDataMapper.findStoreUserOrderBizDataByDateRange(param);
            Paging<StoreUserOrderBizData> paging = PagingHelper.take();
            for (StoreUserOrderBizData dto : paging.getResults()) {
                HSSFRow row = sheet.createRow(rowIndex++);
                row.setHeightInPoints(20);
                for (int i = 0; i < TITLE_LIST.length; i++) {
                    HSSFCell rowCell = row.createCell(i);
                    rowCell.setCellStyle(commonStyle);
                    switch (TITLE_LIST[i]) {
                        case "日期":
                            if (dto.getDate() != null) {
                                rowCell.setCellValue(DateFormatUtils.format(dto.getDate(), "yyyy-MM-dd"));
                            } else {
                                rowCell.setCellValue(DateFormatUtils.format(dto.getStartTime(), "yyyy-MM-dd")
                                        + "-" + DateFormatUtils.format(dto.getEndTime(), "yyyy-MM-dd"));
                            }
                            break;
                        case "商家":
                            rowCell.setCellValue(dto.getStoreUserName());
                            break;
                        case "有效单量":
                            rowCell.setCellValue(dto.getTotalNum());
                            rowCell.setCellType(CellType.NUMERIC);
                            break;
                        case "有效营业额":
                            rowCell.setCellValue(Math.round((dto.getTotal() - dto.getRefundMoney()) * 100) / 100.0);
                            rowCell.setCellType(CellType.NUMERIC);
                            break;
                        case "有效成本":
                            rowCell.setCellValue(Math.round((dto.getCostTotal() - dto.getCostRefund()) * 100f) / 100.0);
                            rowCell.setCellType(CellType.NUMERIC);
                            break;
                        case "有效利润":
                            rowCell.setCellValue(Math.round((dto.getTotal() - dto.getRefundMoney() - dto.getCostTotal() + dto.getCostRefund()) * 100) / 100.0);
                            rowCell.setCellType(CellType.NUMERIC);
                            break;
                    }
                }
            }
            if (!paging.getHasNext()) {
                break;
            }
        }
        workbook.write(excelFile);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("商家利润表", "UTF-8") + "_" + System.currentTimeMillis() + ".xls");
        try (InputStream is = new FileInputStream(excelFile);
             OutputStream os = response.getOutputStream()) {
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            excelFile.delete();
        }
        return null;
    }

    public static class RangeBizDataParam {
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date startDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date endDate;
        private Integer type;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }

}
