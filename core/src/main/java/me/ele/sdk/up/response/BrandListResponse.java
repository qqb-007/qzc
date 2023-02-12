package me.ele.sdk.up.response;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Response;

import java.util.List;

public class BrandListResponse extends Response<BrandListResponse.Data, String> {

    public static class Data {
       private int count;
       @JSONField(name = "page_num")
       private int pageNum;
       @JSONField(name = "page_size")
       private int pageSize;
       @JSONField(name = "max_page_num")
       private int maxPageNum;
       private List<Brand> detail;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getMaxPageNum() {
            return maxPageNum;
        }

        public void setMaxPageNum(int maxPageNum) {
            this.maxPageNum = maxPageNum;
        }

        public List<Brand> getDetail() {
            return detail;
        }

        public void setDetail(List<Brand> detail) {
            this.detail = detail;
        }
    }

    public static class Brand {
        private Long brandId;
        private String brandName;

        public Long getBrandId() {
            return brandId;
        }

        public void setBrandId(Long brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }
    }
}
