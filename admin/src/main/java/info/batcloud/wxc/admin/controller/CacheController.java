package info.batcloud.wxc.admin.controller;

import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    @Inject
    private CacheManager cacheManager;

    @GetMapping("/clear/{key}")
    public void clear(@PathVariable String key) {
        cacheManager.getCache(key).clear();
    }

}
