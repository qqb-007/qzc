package info.batcloud.wxc.core.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class FoodQuoteSheetPublishResult {

    @Id
    @GeneratedValue
    private Long id;

    private Long foodQuoteSheetId;

    private Boolean success;

    private String errMsg;

    private Date createTime;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Store store;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodQuoteSheetId() {
        return foodQuoteSheetId;
    }

    public void setFoodQuoteSheetId(Long foodQuoteSheetId) {
        this.foodQuoteSheetId = foodQuoteSheetId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
