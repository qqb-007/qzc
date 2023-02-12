package me.ele.sdk.down.response;

import me.ele.sdk.down.Response;

public class OrderRemindResponse extends Response<String> {

    @Override
    public String getCmd() {
        return "resp.order.remind.push";
    }

}
