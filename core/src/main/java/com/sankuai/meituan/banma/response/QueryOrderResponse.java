package com.sankuai.meituan.banma.response;

import com.sankuai.meituan.banma.vo.OrderStatusInfo;

/**
 * 查询订单状态响应类
 */
public class QueryOrderResponse extends AbstractResponse<OrderStatusInfo> {

    @Override
    public String toString() {
        return "CreateOrderResponse {" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
