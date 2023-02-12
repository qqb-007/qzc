package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.PublishTrace;
import info.batcloud.wxc.core.enums.PublishTraceType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PublishTraceRepository extends PagingAndSortingRepository<PublishTrace, Long>, JpaSpecificationExecutor<PublishTrace> {

    PublishTrace findTopByRelationIdAndTypeOrderByIdDesc(String relationId, PublishTraceType type);

}
