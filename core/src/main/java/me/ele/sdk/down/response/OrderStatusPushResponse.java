package me.ele.sdk.down.response;

import me.ele.sdk.down.Response;

public class OrderStatusPushResponse extends Response<String> {

    @Override
    public String getCmd() {
        return "resp.order.status.push";
    }

}
