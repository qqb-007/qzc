package com.sankuai.meituan.banma.response;

import com.sankuai.meituan.banma.vo.OrderIdInfo;

/**
 * 订单创建(送货分拣方式)响应类
 */
public class CreateOrderByCoordinatesResponse extends AbstractResponse<OrderIdInfo> {

    @Override
    public String toString() {
        return "CreateOrderResponse {" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
