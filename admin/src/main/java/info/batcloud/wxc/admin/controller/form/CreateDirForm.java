package info.batcloud.wxc.admin.controller.form;

import javax.validation.constraints.NotNull;

public class CreateDirForm {

    @NotNull
    private Long parentId;
    @NotNull
    private String name;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
