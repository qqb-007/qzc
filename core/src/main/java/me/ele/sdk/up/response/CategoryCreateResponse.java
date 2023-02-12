package me.ele.sdk.up.response;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Response;

public class CategoryCreateResponse extends Response<CategoryCreateResponse.Data, String> {

    public static class Data {
        @JSONField(name = "category_id")
        private Long categoryId;

        public Long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }
    }

}
