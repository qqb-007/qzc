package dada.com.request;

import dada.com.response.GetReasonResponse;

public class GetReasonRequest extends Request<GetReasonResponse> {
    @Override
    public String getUrl() {
        return "/api/order/cancel/reasons";
    }

    @Override
    public Class<GetReasonResponse> getResponseClass() {
        return GetReasonResponse.class;
    }
}
