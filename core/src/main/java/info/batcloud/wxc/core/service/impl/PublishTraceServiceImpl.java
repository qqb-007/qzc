package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.PublishTraceDTO;
import info.batcloud.wxc.core.entity.PublishTrace;
import info.batcloud.wxc.core.enums.PublishTraceType;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.PublishTraceRepository;
import info.batcloud.wxc.core.service.PublishTraceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;

@Service
public class PublishTraceServiceImpl implements PublishTraceService {

    @Inject
    private PublishTraceRepository publishTraceRepository;

    @Override
    public void addTrace(TraceParam param) {
        PublishTrace trace = new PublishTrace();
        trace.setCreateTime(new Date());
        trace.setRelationId(param.getRelationId());
        trace.setType(param.getType());
        trace.setJsonContent(JSON.toJSONString(param.getContent()));
        publishTraceRepository.save(trace);
    }

    @Override
    public PublishTraceDTO findLastByRelationIdAndType(String relationId, PublishTraceType type) {
        return toDTO(publishTraceRepository.findTopByRelationIdAndTypeOrderByIdDesc(relationId, type));
    }

    @Override
    public Paging<PublishTraceDTO> search(SearchParam param) {
        Specification<PublishTrace> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotEmpty(param.getRelationId())) {
                expressions.add(cb.equal(root.get("relationId"), param.getRelationId()));
            }
            if (param.getPublishTraceType() != null) {
                expressions.add(cb.equal(root.get("type"), param.getPublishTraceType()));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<PublishTrace> page = publishTraceRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item), param.getPage(), param.getPageSize());
    }

    public PublishTraceDTO toDTO(PublishTrace publishTrace) {
        if (publishTrace == null) {
            return null;
        }
        PublishTraceDTO dto = new PublishTraceDTO();
        BeanUtils.copyProperties(publishTrace, dto);
        dto.setContent(JSON.parseArray(publishTrace.getJsonContent()));
        return dto;
    }
}
