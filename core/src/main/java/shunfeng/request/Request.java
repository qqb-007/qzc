package shunfeng.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 抽象request类
 */
public abstract class Request<T> {

    @JSONField(name = "dev_id")
    protected Integer devId;

    @JSONField(name = "push_time")
    protected Long pushTime;

    public abstract String getEndpoint();

    public abstract Class<T> getResponseType();

    public Integer getDevId() {
        return devId;
    }

    public void setDevId(Integer devId) {
        this.devId = devId;
    }

    public Long getPushTime() {
        return pushTime;
    }

    public void setPushTime(Long pushTime) {
        this.pushTime = pushTime;
    }
}
