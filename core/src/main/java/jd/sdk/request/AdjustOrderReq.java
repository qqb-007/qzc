package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.AdjustOrderRes;

import java.util.List;

public class AdjustOrderReq extends Request<AdjustOrderRes> {
    private Long orderId;
    private String operPin;
    private String remark;
    private List<OAOSAdjustDTO> oaosAdjustDTOList;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOperPin() {
        return operPin;
    }

    public void setOperPin(String operPin) {
        this.operPin = operPin;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<OAOSAdjustDTO> getOaosAdjustDTOList() {
        return oaosAdjustDTOList;
    }

    public void setOaosAdjustDTOList(List<OAOSAdjustDTO> oaosAdjustDTOList) {
        this.oaosAdjustDTOList = oaosAdjustDTOList;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/orderAdjust/adjustOrder";
    }

    @Override
    public Class<AdjustOrderRes> getResponseClass() {
        return AdjustOrderRes.class;
    }

    public static class OAOSAdjustDTO {
        private String outSkuId;
        private Integer skuCount;

        public String getOutSkuId() {
            return outSkuId;
        }

        public void setOutSkuId(String outSkuId) {
            this.outSkuId = outSkuId;
        }

        public Integer getSkuCount() {
            return skuCount;
        }

        public void setSkuCount(Integer skuCount) {
            this.skuCount = skuCount;
        }
    }
}
