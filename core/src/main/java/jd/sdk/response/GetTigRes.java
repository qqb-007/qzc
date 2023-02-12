package jd.sdk.response;

import jd.sdk.Response;

import java.util.List;

public class GetTigRes extends Response<GetTigRes.Data> {
    public static class Data {
        private String code;
        private String msg;
        private List<CategoryInfo> result;

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

        public List<CategoryInfo> getResult() {
            return result;
        }

        public void setResult(List<CategoryInfo> result) {
            this.result = result;
        }
    }

    public static class CategoryInfo {
        private Long id;
        private Long pid;
        private String categoryName;
        private Integer categoryLevel;
        private Integer categoryStatus;
        private String fullPath;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getPid() {
            return pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public Integer getCategoryLevel() {
            return categoryLevel;
        }

        public void setCategoryLevel(Integer categoryLevel) {
            this.categoryLevel = categoryLevel;
        }

        public Integer getCategoryStatus() {
            return categoryStatus;
        }

        public void setCategoryStatus(Integer categoryStatus) {
            this.categoryStatus = categoryStatus;
        }

        public String getFullPath() {
            return fullPath;
        }

        public void setFullPath(String fullPath) {
            this.fullPath = fullPath;
        }
    }
}
