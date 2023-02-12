package wante.sdk.up.request;

import com.alibaba.fastjson.JSONObject;
import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.StoreAnnouncementSetResponse;

public class StoreAnnouncementSetRequest extends AbstractRequest<StoreAnnouncementSetResponse> {
    private UpdateInfo updateInfo;

    public UpdateInfo getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(UpdateInfo updateInfo) {
        this.updateInfo = updateInfo;
    }

    public static class UpdateInfo{

        private String title;
        private String webLinks;
        private Integer merchantId;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getWebLinks() {
            return webLinks;
        }

        public void setWebLinks(String webLinks) {
            this.webLinks = webLinks;
        }

        public Integer getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(Integer merchantId) {
            this.merchantId = merchantId;
        }
    }
    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getURL() {
        return "http://store.mall.wangxiaocai.cn/api/admin_generator_outer_chain_common/?ignoreEmptiness=true";
    }

    @Override
    public Boolean isJson() {
        return true;
    }

    @Override
    public String getJson() {
        return JSONObject.toJSONString(this.updateInfo);
    }

    @Override
    public String getSecret() {
        return "Basic c3RvcmU6TDlJQm9KZDFKUnU1YlR5bw==";
    }

    @Override
    public Class<StoreAnnouncementSetResponse> getResponseType() {
        return StoreAnnouncementSetResponse.class;
    }
}
