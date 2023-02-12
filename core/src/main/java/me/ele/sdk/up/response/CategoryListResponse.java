package me.ele.sdk.up.response;

import me.ele.sdk.up.Response;

import java.util.List;

public class CategoryListResponse extends Response<List<CategoryListResponse.Data>, String> {
    public static class Data {
        public String cat_id;
        public String cat_name;
        public String depth;
        public String parent_id;

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public String getDepth() {
            return depth;
        }

        public void setDepth(String depth) {
            this.depth = depth;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
}
