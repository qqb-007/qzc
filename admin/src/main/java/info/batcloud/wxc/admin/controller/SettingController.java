package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.service.SystemSettingService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/setting")
public class SettingController {

    @Inject
    private SystemSettingService systemSettingService;

    @GetMapping("/version/{key}")
    public Object findVersionByKey(@PathVariable String key) {
        return systemSettingService.findVersionList(key);
    }

    @GetMapping("/{key}/{version}")
    public Object findByKey(@PathVariable String key, @PathVariable int version) {
        return systemSettingService.findSetting(key, version);

    }
    @GetMapping("/{key}")
    public Object findActiveByKey(@PathVariable String key) {
        return systemSettingService.findActiveSetting(key);
    }

    @DeleteMapping("/{version}")
    @Permission(settingPermission = true)
    public Object deleteByKey(@RequestParam String key, @PathVariable int version) {
        systemSettingService.deleteSetting(key, version);
        return 1;
    }

    @PutMapping("/active/{version}")
    @Permission(settingPermission = true)
    public Object active(@RequestParam String key, @PathVariable int version) {
        systemSettingService.activeSetting(key, version);
        return 1;
    }

    @PostMapping()
    @Permission(settingPermission = true)
    public Object save(SettingSaveForm form) {
        int v = systemSettingService.saveSetting(form.getKey(), form.getContent(), form.getVersion());
        return v;
    }

    public static class SettingSaveForm {
        private String content;
        private String key;
        private int version;

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

}
