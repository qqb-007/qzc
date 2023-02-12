package info.batcloud.wxc.core.action.domain;

import java.util.Date;

public abstract class Action {

    //任务时间
    private Date time;

    //用户
    private Long userId;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
