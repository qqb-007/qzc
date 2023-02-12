package wante.sdk.up.request;

import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.StoreAnnouncementGetResponse;
import wante.sdk.up.response.StoreAnnouncementSetResponse;

public class StoreAnnouncementGetRequest extends AbstractRequest<StoreAnnouncementGetResponse> {
    private Integer id;//公告id，不是商户id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Method getMethod() {
        return Method.GET;
    }

    @Override
    public String getURL() {
        return "http://store.mall.wangxiaocai.cn/api/admin_generator_outer_chain_common/" + this.id;
    }

    @Override
    public Boolean isJson() {
        return false;
    }

    @Override
    public String getJson() {
        return null;
    }

    @Override
    public String getSecret() {
        return "Basic c3RvcmU6TDlJQm9KZDFKUnU1YlR5bw==";
    }

    @Override
    public Class<StoreAnnouncementGetResponse> getResponseType() {
        return StoreAnnouncementGetResponse.class;
    }
}
