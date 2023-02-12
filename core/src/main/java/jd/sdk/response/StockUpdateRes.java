package jd.sdk.response;

import jd.sdk.Response;

public class StockUpdateRes extends Response<StockUpdateRes.Data> {
    public static class Data {
        private Boolean isRet;
        private String retCode;
        /*0：成功，10025：非库存同步时间，10026：门店编号错误，1：失败，111：必填参数校验为空，操作失败，11：orgCode不能为空，7：stationNo门店编号不能为空，8：skuId不能为空，25：此商品暂时不允许修改库存，403：请检查门店是否属于商家，14：库存中不存在该记录，10403：前置仓门店的库存，不允许修改，22：调用门店系统异常;
         */
        private String retMsg;

        public Boolean getRet() {
            return isRet;
        }

        public void setRet(Boolean ret) {
            isRet = ret;
        }

        public String getRetCode() {
            return retCode;
        }

        public void setRetCode(String retCode) {
            this.retCode = retCode;
        }

        public String getRetMsg() {
            return retMsg;
        }

        public void setRetMsg(String retMsg) {
            this.retMsg = retMsg;
        }
    }
}
