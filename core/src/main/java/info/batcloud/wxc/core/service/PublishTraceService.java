package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.PublishTraceDTO;
import info.batcloud.wxc.core.enums.PublishTraceType;

public interface PublishTraceService {

    void addTrace(TraceParam param);

    PublishTraceDTO findLastByRelationIdAndType(String relationId, PublishTraceType type);

    Paging<PublishTraceDTO> search(SearchParam param);

    class TraceParam<T> {
        private PublishTraceType type;
        private String relationId;
        private T content;

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

        public T getContent() {
            return content;
        }

        public void setContent(T content) {
            this.content = content;
        }
    }

    class SearchParam extends PagingParam {
        private String relationId;
        private PublishTraceType publishTraceType;

        public String getRelationId() {
            return relationId;
        }

        public void setRelationId(String relationId) {
            this.relationId = relationId;
        }

        public PublishTraceType getPublishTraceType() {
            return publishTraceType;
        }

        public void setPublishTraceType(PublishTraceType publishTraceType) {
            this.publishTraceType = publishTraceType;
        }
    }

}
