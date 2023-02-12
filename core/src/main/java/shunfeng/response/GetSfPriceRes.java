package shunfeng.response;

import com.alibaba.fastjson.annotation.JSONField;

public class GetSfPriceRes extends Response<GetSfPriceRes.Result> {
    public static class Result {
        @JSONField(name = "real_pay_money")
        private Integer realPaymoney;//顺分价格单位是分

        public Integer getRealPaymoney() {
            return realPaymoney;
        }

        public void setRealPaymoney(Integer realPaymoney) {
            this.realPaymoney = realPaymoney;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "realPaymoney=" + realPaymoney +
                    '}';
        }
    }
}
