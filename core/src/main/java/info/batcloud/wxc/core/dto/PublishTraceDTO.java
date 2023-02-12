package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.enums.PublishTraceType;

import java.util.Date;

public class PublishTraceDTO {

    private Long id;

    private PublishTraceType type;

    private String relationId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String jsonContent;

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PublishTraceType getType() {
        return type;
    }

    public void setType(PublishTraceType type) {
        this.type = type;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }
}
