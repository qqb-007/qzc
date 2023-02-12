package me.ele.sdk.down.response;

import me.ele.sdk.down.Response;

public class ShopStatusResponse extends Response<String> {
    @Override
    public String getCmd() {
        return "resp.shop.msg.push";
    }
}
