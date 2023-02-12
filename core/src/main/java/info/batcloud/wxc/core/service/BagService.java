package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.BagDTO;
import info.batcloud.wxc.core.enums.BagStatus;
import info.batcloud.wxc.core.enums.BagType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public interface BagService {

    void update(Long id, Integer num);

    String download();

    String export(ExportParam param);

    //查找  admin
    Paging<BagDTO> search(SearchParam param);

    //商家申请购物袋
    boolean applyBag(ApplyParam param);

    //客服审核商家申请的购物袋申请
    void checkApply(long id, String context);

    //客服审填入物流单号
    void addWuLiu(long id, String wl, String context);

    //客服驳回商家申请的购物袋申请
    void rejectApply(long id, String context);

    class ExportParam {
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date startTime;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date endTime;

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }
    }

    class UpdataParam {
        private Long id;
        private Integer num;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }

    class AddWuLiuParam {
        @NotNull
        private Long id;
        @NotNull
        private String wuliu;

        private String wuliuType;

        private String remark;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getWuliu() {
            return wuliu;
        }

        public void setWuliu(String wuliu) {
            this.wuliu = wuliu;
        }

        public String getWuliuType() {
            return wuliuType;
        }

        public void setWuliuType(String wuliuType) {
            this.wuliuType = wuliuType;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    class SearchParam extends PagingParam {
        private String storeUserName;
        private Long storeUserId;
        private BagStatus status;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createStartTime;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createEndTime;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date createStartDate;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date createEndDate;

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public Date getCreateStartDate() {
            return createStartDate;
        }

        public void setCreateStartDate(Date createStartDate) {
            this.createStartDate = createStartDate;
        }

        public Date getCreateEndDate() {
            return createEndDate;
        }

        public void setCreateEndDate(Date createEndDate) {
            this.createEndDate = createEndDate;
        }

        public String getStoreUserName() {
            return storeUserName;
        }

        public void setStoreUserName(String storeUserName) {
            this.storeUserName = storeUserName;
        }

        public BagStatus getStatus() {
            return status;
        }

        public void setStatus(BagStatus status) {
            this.status = status;
        }

        public Date getCreateStartTime() {
            return createStartTime;
        }

        public void setCreateStartTime(Date createStartTime) {
            this.createStartTime = createStartTime;
        }

        public Date getCreateEndTime() {
            return createEndTime;
        }

        public void setCreateEndTime(Date createEndTime) {
            this.createEndTime = createEndTime;
        }
    }


    class ApplyParam {
        private Long storeUserId;
        private Integer bagNum;
        private BagType bagType;

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public Integer getBagNum() {
            return bagNum;
        }

        public void setBagNum(Integer bagNum) {
            this.bagNum = bagNum;
        }

        public BagType getBagType() {
            return bagType;
        }

        public void setBagType(BagType bagType) {
            this.bagType = bagType;
        }
    }


}
