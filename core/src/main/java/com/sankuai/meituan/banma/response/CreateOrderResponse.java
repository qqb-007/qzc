package com.sankuai.meituan.banma.response;

import com.sankuai.meituan.banma.vo.OrderIdInfo;

/**
 * 创建订单响应类
 */
public class CreateOrderResponse extends AbstractResponse<OrderIdInfo> {

    @Override
    public String toString() {
        return "CreateOrderResponse {" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
