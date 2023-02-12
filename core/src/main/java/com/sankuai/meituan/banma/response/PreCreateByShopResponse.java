package com.sankuai.meituan.banma.response;

import com.sankuai.meituan.banma.vo.PreOrderInfo;

public class PreCreateByShopResponse extends AbstractResponse<PreOrderInfo> {
    @Override
    public String toString() {
        return "PreCreateByShopResponse {" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
