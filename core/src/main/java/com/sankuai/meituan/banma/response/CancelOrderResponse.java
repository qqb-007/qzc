package com.sankuai.meituan.banma.response;

import com.sankuai.meituan.banma.vo.OrderIdInfo;

/**
 * 取消订单响应类
 */
public class CancelOrderResponse extends AbstractResponse<OrderIdInfo> {

    @Override
    public String toString() {
        return "CreateOrderResponse {" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
