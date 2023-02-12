package me.ele.sdk.up.response;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Response;

import java.util.List;

public class SkuListResponse extends Response<SkuListResponse.Data, String> {

    public static class Data {
        private int total;
        private int page;
        private int pages;
        private List<Sku> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<Sku> getList() {
            return list;
        }

        public void setList(List<Sku> list) {
            this.list = list;
        }
    }

    public static class Sku {
        @JSONField(name = "sku_id")
        private Long skuId;
        @JSONField(name = "custom_sku_id")
        private String customSkuId;

        private String name;

        private int status;

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }

        public String getCustomSkuId() {
            return customSkuId;
        }

        public void setCustomSkuId(String customSkuId) {
            this.customSkuId = customSkuId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
