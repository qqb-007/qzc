package info.batcloud.wxc.core.alipay.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.Map;

public class NotifyParam {

    @JSONField(name = "notify_time")
    private Date notifyTime;

    @JSONField(name = "notify_type")
    private String notifyType;

    @JSONField(name = "notify_id")
    private String notifyId;

    @JSONField(name = "app_id")
    private String appId;

    @JSONField(name = "charset")
    private String charset;

    @JSONField(name = "version")
    private String version;

    @JSONField(name = "sign_type")
    private SignType signType;

    @JSONField(name = "sign")
    private String sign;

    @JSONField(name = "trade_no")
    private String tradeNo;

    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @JSONField(name = "out_biz_no")
    private String outBizNo;

    @JSONField(name = "buyer_id")
    private String buyerId;

    @JSONField(name = "buyer_logon_id")
    private String buyerLogonId;

    @JSONField(name = "seller_id")
    private String sellerId;

    @JSONField(name = "seller_email")
    private String sellerEmail;

    @JSONField(name = "trade_status")
    private TradeStatus tradeStatus;

    @JSONField(name = "total_amount")
    private float totalAmount;

    @JSONField(name = "receipt_amount")
    private float receiptAmount;

    @JSONField(name = "invoice_amount")
    private float invoiceAmount;

    @JSONField(name = "buyer_pay_amount")
    private float buyerPayAmount;

    @JSONField(name = "point_amount")
    private float pointAmount;

    @JSONField(name = "refund_fee")
    private float refundFee;

    @JSONField(name = "subject")
    private String subject;

    @JSONField(name = "body")
    private String body;

    @JSONField(name = "gmt_create")
    private Date gmtCreate;

    @JSONField(name = "gmt_payment")
    private Date gmtPayment;

    @JSONField(name = "gmt_refund")
    private Date gmtRefund;

    @JSONField(name = "gmt_close")
    private Date gmtClose;

    @JSONField(name = "fund_bill_list")
    private String fundBillList;

    @JSONField(name = "passback_params")
    private String passbackParams;

    @JSONField(name = "voucher_detail_list")
    private String voucherDetailList;

    private Map<String, String> rawMap;

    public Map<String, String> getRawMap() {
        return rawMap;
    }

    public void setRawMap(Map<String, String> rawMap) {
        this.rawMap = rawMap;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SignType getSignType() {
        return signType;
    }

    public void setSignType(SignType signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerLogonId() {
        return buyerLogonId;
    }

    public void setBuyerLogonId(String buyerLogonId) {
        this.buyerLogonId = buyerLogonId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(float receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public float getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(float invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public float getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(float buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public float getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(float pointAmount) {
        this.pointAmount = pointAmount;
    }

    public float getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(float refundFee) {
        this.refundFee = refundFee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtPayment() {
        return gmtPayment;
    }

    public void setGmtPayment(Date gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

    public Date getGmtRefund() {
        return gmtRefund;
    }

    public void setGmtRefund(Date gmtRefund) {
        this.gmtRefund = gmtRefund;
    }

    public Date getGmtClose() {
        return gmtClose;
    }

    public void setGmtClose(Date gmtClose) {
        this.gmtClose = gmtClose;
    }

    public String getFundBillList() {
        return fundBillList;
    }

    public void setFundBillList(String fundBillList) {
        this.fundBillList = fundBillList;
    }

    public String getPassbackParams() {
        return passbackParams;
    }

    public void setPassbackParams(String passbackParams) {
        this.passbackParams = passbackParams;
    }

    public String getVoucherDetailList() {
        return voucherDetailList;
    }

    public void setVoucherDetailList(String voucherDetailList) {
        this.voucherDetailList = voucherDetailList;
    }
}
