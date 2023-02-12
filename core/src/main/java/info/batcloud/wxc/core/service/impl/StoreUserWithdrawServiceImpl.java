package info.batcloud.wxc.core.service.impl;

import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.constants.MessageKeyConstants;
import info.batcloud.wxc.core.context.StaticContext;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.dto.OrderDTO;
import info.batcloud.wxc.core.dto.StoreUserWithdrawDTO;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.entity.StoreUserWithdraw;
import info.batcloud.wxc.core.entity.Wallet;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.enums.FundTransferType;
import info.batcloud.wxc.core.enums.WalletFlowDetailType;
import info.batcloud.wxc.core.enums.WithdrawStatus;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.OSSImageHelper;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.helper.ZipHelper;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.repository.StoreUserWithdrawRepository;
import info.batcloud.wxc.core.repository.WalletRepository;
import info.batcloud.wxc.core.service.*;
import info.batcloud.wxc.core.settings.WithdrawSetting;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.python.antlr.ast.Str;
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
import org.springframework.transaction.annotation.Transactional;

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
public class StoreUserWithdrawServiceImpl implements StoreUserWithdrawService {

    @Inject
    private StoreUserWithdrawRepository storeUserWithdrawRepository;

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private WalletService walletService;

    @Inject
    private WalletRepository walletRepository;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private FundTransferService fundTransferService;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private TmpFileService tmpFileService;

    @Inject
    private OssService ossService;

    @Inject
    private RedisTemplate<String, String> redisTemplate;


    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Override
    public boolean withdraw(Long storeUserId) {
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        if (StringUtils.isEmpty(storeUser.getBankAccount()) || StringUtils.isEmpty(storeUser.getBankAccountName()) || StringUtils.isEmpty(storeUser.getBankName())) {
            logger.error("暂未绑定银行卡");
            return false;
        }
        Wallet wallet = walletRepository.findByStoreUserId(storeUserId);
        StoreUserWithdrawParams params = new StoreUserWithdrawParams();
        params.setBankAccount(storeUser.getBankAccount());
        params.setBankAccountName(storeUser.getBankAccountName());
        params.setBankName(storeUser.getBankName());
        params.setMoney(wallet.getMoney());
        this.withdraw(storeUserId, params);
        return true;
    }

    @Override
    @Transactional
    public void BatchCheck(SearchParam param) {
        param.setStatus(WithdrawStatus.WAIT_VERIFY);
        int pageSize = 100;
        param.setPage(pageSize);
        param.setPage(1);
        VerifyParam verifyParam = new VerifyParam();
        verifyParam.setRemark("系统自动审核");
        verifyParam.setSuccess(true);
        while (true) {
            Paging<StoreUserWithdrawDTO> search = this.search(param);
            for (int j = 0; j < search.getResults().size(); j++) {
                StoreUserWithdrawDTO dto = search.getResults().get(j);
                verifyParam.setId(dto.getId());
                this.verify(verifyParam);
            }
            if (!search.getHasNext()) {
                break;
            }
            param.setPage(param.getPage() + 1);
        }

    }

    @Override
    @Transactional
    public void BatchTransfer() {
        List<StoreUserWithdraw> userWithdraws = storeUserWithdrawRepository.findByStatusOrderByIdAsc(WithdrawStatus.WAIT_TRANSFER);
        for (StoreUserWithdraw userWithdraw : userWithdraws) {
            userWithdraw.setFundTransferType(FundTransferType.MANUAL);
            userWithdraw.setStatus(WithdrawStatus.TRANSFER_SUCCESS);
            userWithdraw.setPayDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            userWithdraw.setAlipayOrderId("0000000000");
            storeUserWithdrawRepository.save(userWithdraw);
        }
    }

    @Override
    public String export(SearchParam param) throws IOException {
        List<File> files = new ArrayList<>();
        File afile = getFile(param);
        files.add(afile);
        File zipFile = tmpFileService.createFile(System.currentTimeMillis() + "_"
                + DateFormatUtils.format(param.getCreateStartDate(), "yyyy-MM-dd") + "_" + DateFormatUtils.format(param.getCreateEndDate(), "yyyy-MM-dd") + ".zip");
        boolean flag = ZipHelper.packageZip(zipFile, files);
        if (flag) {
            try {
                CompleteMultipartUploadResult result = ossService.uploadFile(zipFile, "order-export/" + zipFile.getName());
                redisTemplate.opsForValue().set("orderExportUrl", OSSImageHelper.toUrl(result.getKey()));
            } catch (Throwable throwable) {
                logger.error("上传订单导出文件失败", throwable);
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

    private static String[] TITLE_LIST = new String[]{"门店名称", "金额", "银行卡", "持卡人", "状态", "日期"};

    private File getFile(SearchParam param) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        File excelFile = tmpFileService.createFile("提现报表.xls");
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
        for (int i = 0; i < TITLE_LIST.length; i++) {
            HSSFCell titleCell = titleRow.createCell(i);
            //给单元格设置内容
            titleCell.setCellValue(TITLE_LIST[i]);
            titleCell.setCellStyle(foldStyle);
        }
        int rownum = 1;
        int pageSize = 100;
        param.setPage(pageSize);
        param.setPage(1);
        while (true) {
            Paging<StoreUserWithdrawDTO> paging = this.search(param);
            for (int j = 0; j < paging.getResults().size(); j++) {
                StoreUserWithdrawDTO dto = paging.getResults().get(j);
                //每一个提现申请增加一行
                HSSFRow row = sheet.createRow(rownum++);
                row.setHeightInPoints(20);
                for (int i = 0; i < TITLE_LIST.length; i++) {
                    HSSFCell rowCell = row.createCell(i);
                    rowCell.setCellStyle(commonStyle);
                    switch (TITLE_LIST[i]) {
                        case "门店名称":
                            rowCell.setCellValue(dto.getStoreUser().getName());
                            break;
                        case "日期":
                            rowCell.setCellValue(DateFormatUtils.format(dto.getCreateTime(), "yyyy.M.d"));
                            break;
                        case "金额":
                            rowCell.setCellValue(dto.getMoney());
                            break;
                        case "银行卡":
                            if (dto.getBankAccount() != null) {
                                rowCell.setCellValue(dto.getBankAccount());
                            } else {
                                rowCell.setCellValue("暂未绑定银行卡");
                            }
                            break;
                        case "持卡人":
                            if (dto.getBankAccountName() != null) {
                                rowCell.setCellValue(dto.getBankAccountName());
                            } else {
                                rowCell.setCellValue("暂未绑定");
                            }
                            break;
                        case "状态":
                            rowCell.setCellValue(dto.getStatus().getTitle());
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

    @Override
    @Transactional
    public synchronized void withdraw(long storeUserId, StoreUserWithdrawService.StoreUserWithdrawParams params) {
        Wallet wallet = walletRepository.findByStoreUserId(storeUserId);
        //判断账户余额够不够提现的金额
        if (params.getMoney() < 0 || wallet.getMoney() < params.getMoney()) {
            throw new BizException(MessageKeyConstants.NO_ENOUGH_MONEY_FOR_WITHDRAW);
        }
        WithdrawSetting withdrawSetting = systemSettingService.findActiveSetting(WithdrawSetting.class);
        if (params.getMoney() < withdrawSetting.getMinWithdrawValue()) {
            throw new BizException(MessageKeyConstants.LESS_MONEY_FOR_WITHDRAW, new Object[]{withdrawSetting.getMinWithdrawValue()});
        }
        WalletService.WalletChangeResult result = walletService.addMoney(storeUserId, -params.getMoney(), WalletFlowDetailType.WITHDRAW, "");
        StoreUserWithdraw userWithdraw = new StoreUserWithdraw();
        userWithdraw.setStoreUser(storeUserRepository.findOne(storeUserId));
        userWithdraw.setCreateTime(new Date());
        userWithdraw.setMoney(params.getMoney());
        if (withdrawSetting.isAutoVerify()) {
            userWithdraw.setStatus(WithdrawStatus.WAIT_TRANSFER);
        } else {
            userWithdraw.setStatus(WithdrawStatus.WAIT_VERIFY);
        }
        userWithdraw.setUpdateTime(userWithdraw.getCreateTime());
        userWithdraw.setWalletFlowDetailId(result.getWalletDetailId());
        userWithdraw.setBankAccount(params.getBankAccount());
        userWithdraw.setBankAccountName(params.getBankAccountName());
        userWithdraw.setBankName(params.getBankName());
        storeUserWithdrawRepository.save(userWithdraw);
        wallet.setWithdrawMoney(wallet.getWithdrawMoney() + params.getMoney());
        walletRepository.save(wallet);
    }

    @Override
    public Paging<StoreUserWithdrawDTO> search(StoreUserWithdrawService.SearchParam param) {
        Specification<StoreUserWithdraw> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotBlank(param.getStoreUserPhone())) {
                expressions.add(cb.equal(root.get("storeUser").get("phone"), param.getStoreUserPhone()));
            }
            if (StringUtils.isNotBlank(param.getStoreUserPhone())) {
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
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<StoreUserWithdraw> page = storeUserWithdrawRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toBo(item), param.getPage(), param.getPageSize());
    }

    @Override
    public StoreUserWithdrawDTO findById(long id) {
        StoreUserWithdraw userWithdraw = storeUserWithdrawRepository.findOne(id);
        return toBo(userWithdraw);
    }

    @Override
    public StoreUserWithdrawDTO findByWalletFlowDetailId(long walletFlowDetailId) {
        StoreUserWithdraw userWithdraw = storeUserWithdrawRepository.findByWalletFlowDetailId(walletFlowDetailId);
        return toBo(userWithdraw);
    }

    private StoreUserWithdrawDTO toBo(StoreUserWithdraw userWithdraw) {
        StoreUserWithdrawDTO bo = new StoreUserWithdrawDTO();
        BeanUtils.copyProperties(userWithdraw, bo);
        bo.setStoreUser(storeUserService.toDTO(userWithdraw.getStoreUser()));
        return bo;
    }

    @Override
    public synchronized Result withdrawFundTransferById(long id) {
        Result result = new Result();
        StoreUserWithdraw userWithdraw = storeUserWithdrawRepository.findOne(id);
        if (userWithdraw.getStatus() != WithdrawStatus.WAIT_TRANSFER
                && userWithdraw.getStatus() != WithdrawStatus.RETRY_TRANSFER
                && userWithdraw.getStatus() != WithdrawStatus.TRANSFER_FAIL) {
            result.setCode("该状态不允许转账");
            return result;
        }
        userWithdraw.setStatus(WithdrawStatus.ON_TRANSFER);
        storeUserWithdrawRepository.save(userWithdraw);
        FundTransferService.FundTransferParam transferParam = new FundTransferService.FundTransferParam();
        transferParam.setAmount(userWithdraw.getMoney());
        transferParam.setPayeeAccount(userWithdraw.getPayeeAccount());
        transferParam.setPayeeRealName(userWithdraw.getPayeeName());
        transferParam.setRemark(StaticContext.messageSource.getMessage(MessageKeyConstants.USER_WITHDRAW_REMARK, null, "", null));
        FundTransferService.FundTransferResult transferResult = fundTransferService.transferFund(transferParam);
        userWithdraw.setStatus(transferResult.isSuccess() ? WithdrawStatus.TRANSFER_SUCCESS : WithdrawStatus.TRANSFER_FAIL);
        userWithdraw.setRemark(transferResult.getErrorMsg());
        userWithdraw.setFundTransferOrderId(transferResult.getFundTransferOrderId());
        userWithdraw.setPayDate(transferResult.getPayDate());
        userWithdraw.setAlipayOrderId(transferResult.getAlipayOrderId());
        storeUserWithdrawRepository.save(userWithdraw);
        result.setSuccess(transferResult.isSuccess());
        result.setCode(transferResult.getErrorMsg());
        return result;
    }

    @Override
    public String findStoreUserLastAlipayAccount(long storeUserId) {
        StoreUserWithdraw userWithdraw = storeUserWithdrawRepository.findTopByStoreUserIdOrderByIdDesc(storeUserId);
        return userWithdraw == null ? null : userWithdraw.getPayeeAccount();
    }

    @Override
    @Transactional
    public Result withdrawManualFundTransfer(ManualFundTransferParam param) {
        Result result = new Result();
        StoreUserWithdraw userWithdraw = storeUserWithdrawRepository.findOne(param.getId());
        if (userWithdraw.getStatus() != WithdrawStatus.WAIT_TRANSFER
                && userWithdraw.getStatus() != WithdrawStatus.RETRY_TRANSFER) {
            result.setCode("该状态不允许转账");
            return result;
        }
        userWithdraw.setFundTransferType(FundTransferType.MANUAL);
        userWithdraw.setStatus(WithdrawStatus.TRANSFER_SUCCESS);
        userWithdraw.setPayDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        if (StringUtils.isNotEmpty(param.getRemark())) {
            userWithdraw.setRemark(userWithdraw.getRemark() + "   " + param.getRemark());
        }
        userWithdraw.setAlipayOrderId(param.getAlipayOrderId());
        storeUserWithdrawRepository.save(userWithdraw);
        result.setSuccess(true);
        return result;
    }

    @Override
    @Transactional
    public void verify(VerifyParam param) {
        StoreUserWithdraw userWithdraw = storeUserWithdrawRepository.findOne(param.getId());
        /**
         * 只有待审核的才可以进行审核
         * */
        if (userWithdraw.getStatus() != WithdrawStatus.WAIT_VERIFY) {
            throw new BizException("该提现记录不支持审核");
        }
        if (param.isSuccess()) {
            userWithdraw.setStatus(WithdrawStatus.WAIT_TRANSFER);
        } else {
            userWithdraw.setStatus(WithdrawStatus.VERIFY_FAIL);
            /**
             * 如果审核失败，则回退金额到余额
             * */
            walletService.addMoney(userWithdraw.getStoreUser().getId(), userWithdraw.getMoney(), WalletFlowDetailType.WITHDRAW_REJECT_RETURN, userWithdraw.getId().toString());
        }
        userWithdraw.setRemark(param.getRemark());
        storeUserWithdrawRepository.save(userWithdraw);
    }
}
