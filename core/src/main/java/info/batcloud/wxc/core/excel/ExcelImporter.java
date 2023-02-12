package info.batcloud.wxc.core.excel;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.excel.stereotype.XslCol;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class ExcelImporter {

    private final static Logger logger = LoggerFactory.getLogger(ExcelImporter.class);

    public static <T> List<T> execute(File excelFile, Class<T> clazz) {
        try (InputStream is = new FileInputStream(excelFile)) {
            return execute(is, clazz, excelFile.getName().endsWith(".xls"));
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> execute(InputStream is, Class<T> clazz, boolean isXls) {
        return doExecute(is, clazz, isXls);
    }

    private static <T> List<T> doExecute(InputStream is, Class<T> clazz, boolean isXls) {
        List<T> rowList = new ArrayList<>();
        try {
            Sheet hssfSheet;
            if (isXls) {
                //2003文件
                POIFSFileSystem fs = new POIFSFileSystem(is);
                HSSFWorkbook wb = new HSSFWorkbook(fs);
                hssfSheet = wb.getSheetAt(0);
            } else {
                Workbook hssfWorkbook = new XSSFWorkbook(is);
                hssfSheet = hssfWorkbook.getSheetAt(0);
            }
            // 循环行Row
            Map<Integer, String> fieldColumnIndexMap = new HashMap<>();
            ExcelColMapping excelColMapping = getExcelColMapping(clazz);
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                try {
                    Map<String, Object> rowMap = null;
                    if (rowNum > 0) {
                        rowMap = new HashMap<>();
                    }
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    Iterator<Cell> iterator = hssfRow.cellIterator();
                    while (iterator.hasNext()) {
                        Cell cell = iterator.next();
                        if (getCellValue(cell).length() == 0) {
                            continue;
                        }
                        //当rownum 为0的时候，是标题
                        if (rowNum == 0) {
                            fieldColumnIndexMap.put(cell.getColumnIndex(), cell.getStringCellValue().trim());
                        } else {
                            String fieldName = fieldColumnIndexMap.get(cell.getColumnIndex());
                            if (fieldName == null) {
                                continue;
                            }
                            Object value = getCellValue(cell);
                            String field = excelColMapping.getColMap().get(fieldColumnIndexMap.get(cell.getColumnIndex()));
                            if (field != null) {
                                rowMap.put(field, value);
                            }
                        }
                    }

                    if (rowNum > 0) {
                        boolean checkFlag = false;
                        for (Map.Entry<String, Object> entry : rowMap.entrySet()) {
                            //检查row的数据，如果当前row所有cell的数据都是空，那么则忽略该行
                            if (StringUtils.isNotEmpty(entry.getValue().toString())) {
                                checkFlag = true;
                                break;
                            }
                        }
                        if (!checkFlag) {
                            continue;
                        }
                        String json = JSON.toJSONString(rowMap);
                        T entity = JSON.parseObject(json, clazz);
                        rowList.add(entity);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("第" + (rowNum + 1) + "行出错：", e);
                }
            }

        } catch (IOException e) {
            logger.error("导入出错：", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rowList;
    }

    private static ExcelColMapping getExcelColMapping(Class entityClass) {
        ExcelColMapping mapping = new ExcelColMapping();
        for (Field field : entityClass.getDeclaredFields()) {
            XslCol xslCol = field.getAnnotation(XslCol.class);
            if (xslCol != null) {
                mapping.getColMap().put(xslCol.title(), field.getName());
                mapping.getFieldMap().put(field.getName(), xslCol.title());
            }
        }
        if (entityClass.getSuperclass() != null) {
            ExcelColMapping tmp = getExcelColMapping(entityClass.getSuperclass());
            mapping.getColMap().putAll(tmp.getColMap());
            mapping.getFieldMap().putAll(tmp.getFieldMap());
        }
        return mapping;
    }

    private static class ExcelColMapping {
        private Map<String, String> colMap;
        private Map<String, String> fieldMap;

        ExcelColMapping() {
            this.colMap = new HashMap<>();
            this.fieldMap = new HashMap<>();
        }

        public Map<String, String> getColMap() {
            return colMap;
        }

        public void setColMap(Map<String, String> colMap) {
            this.colMap = colMap;
        }

        public Map<String, String> getFieldMap() {
            return fieldMap;
        }

        public void setFieldMap(Map<String, String> fieldMap) {
            this.fieldMap = fieldMap;
        }
    }

    private static String getCellValue(Cell cell) {

        String cellValue;
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");
                } else {
                    cell.setCellType(CellType.STRING);
                    cellValue = cell.getStringCellValue();
                }
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case BLANK:
            case ERROR:
                cellValue = "";
                break;
            case FORMULA:
                cell.setCellType(CellType.NUMERIC);
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

}
