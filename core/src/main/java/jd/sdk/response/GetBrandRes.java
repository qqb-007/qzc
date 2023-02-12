package jd.sdk.response;

import jd.sdk.Response;

import java.util.List;

public class GetBrandRes extends Response<GetBrandRes.Data> {
    public static class Data {
        private String code;
        private String msg;
        private PageResponse result;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public PageResponse getResult() {
            return result;
        }

        public void setResult(PageResponse result) {
            this.result = result;
        }
    }

    public static class PageResponse {
        private Integer pageSize;
        private Integer pageNo;
        private Integer count;
        private List<BrandInfo> result;

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getPageNo() {
            return pageNo;
        }

        public void setPageNo(Integer pageNo) {
            this.pageNo = pageNo;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List<BrandInfo> getResult() {
            return result;
        }

        public void setResult(List<BrandInfo> result) {
            this.result = result;
        }
    }

    public static class BrandInfo {
        private Long id;
        private String brandName;
        private Integer brandStatus;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public Integer getBrandStatus() {
            return brandStatus;
        }

        public void setBrandStatus(Integer brandStatus) {
            this.brandStatus = brandStatus;
        }
    }
}
