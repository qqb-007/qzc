package com.sankuai.meituan.banma.response;

import com.sankuai.meituan.banma.vo.OrderIdInfo;

/**
 * 评价骑手响应类
 */
public class EvaluateResponse extends AbstractResponse<OrderIdInfo> {

    @Override
    public String toString() {
        return "CreateOrderResponse {" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
