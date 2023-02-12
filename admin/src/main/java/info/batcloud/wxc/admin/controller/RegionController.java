package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.core.service.RegionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/region")
public class RegionController {

    @Inject
    private RegionService regionService;

    @GetMapping("/list/{parentId}")
    public Object list(@PathVariable long parentId) {
        return regionService.findByParentId(parentId);
    }

    @GetMapping("/level/{level}")
    public Object levelList(@PathVariable int level) {
        return regionService.findByLevel(level);
    }

    @GetMapping("/sync")
    public Object sync() {
        return regionService.sync();
    }
}
