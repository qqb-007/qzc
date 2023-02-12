package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.core.dto.PublishTraceDTO;
import info.batcloud.wxc.core.enums.PublishTraceType;
import info.batcloud.wxc.core.service.PublishTraceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/publish-trace")
public class PublishTraceController {

    @Inject
    private PublishTraceService publishTraceService;

    @GetMapping("/search")
    public Object search(PublishTraceService.SearchParam param) {

        return publishTraceService.search(param);
    }

    @GetMapping("/last/{relationId}/{type}")
    public Object findLast(@PathVariable String relationId, @PathVariable PublishTraceType type) {
        PublishTraceDTO dto = publishTraceService.findLastByRelationIdAndType(relationId, type);
        return dto == null ? new PublishTraceDTO() : dto;
    }
}
