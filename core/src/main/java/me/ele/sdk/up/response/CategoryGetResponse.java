package me.ele.sdk.up.response;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Response;

import java.util.List;

public class CategoryGetResponse extends Response<CategoryGetResponse.Data, String> {

    public static class Data {
        private List<Category> categorys;

        public List<Category> getCategorys() {
            return categorys;
        }

        public void setCategorys(List<Category> categorys) {
            this.categorys = categorys;
        }
    }


    public static class Category {
        @JSONField(name = "category_id")
        private Long categoryId;

        private String name;

        private Integer rank;

        private List<Category> children;

        public Long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public List<Category> getChildren() {
            return children;
        }

        public void setChildren(List<Category> children) {
            this.children = children;
        }
    }
}
