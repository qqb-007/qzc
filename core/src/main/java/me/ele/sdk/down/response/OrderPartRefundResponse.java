package me.ele.sdk.down.response;

import me.ele.sdk.down.Response;

public class OrderPartRefundResponse extends Response<OrderPartRefundResponse.Data> {

    @Override
    public String getCmd() {
        return "resp.order.partrefund.push";
    }

    public static class Data {
        private String source_order_id;

        public String getSource_order_id() {
            return source_order_id;
        }

        public void setSource_order_id(String source_order_id) {
            this.source_order_id = source_order_id;
        }
    }

}
