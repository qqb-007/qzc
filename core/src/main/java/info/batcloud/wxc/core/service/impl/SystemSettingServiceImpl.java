package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.constants.CacheNameConstants;
import info.batcloud.wxc.core.dto.SystemSettingVersionDTO;
import info.batcloud.wxc.core.entity.SystemSetting;
import info.batcloud.wxc.core.enums.SystemSettingStatus;
import info.batcloud.wxc.core.event.SettingChangeEvent;
import info.batcloud.wxc.core.repository.SystemSettingRepository;
import info.batcloud.wxc.core.service.SystemSettingService;
import info.batcloud.wxc.core.settings.*;
import info.batcloud.wxc.core.settings.annotation.Single;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = CacheNameConstants.SYSTEM_SETTING)
public class SystemSettingServiceImpl implements SystemSettingService {

    private final Map<String, Class> SETTING_CLASS_MAP = new HashMap<>();

    @Inject
    private SystemSettingRepository systemSettingRepository;

    @Inject
    private ApplicationEventPublisher eventPublisher;

    @Inject
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        this.register(FoodSetting.class);
        this.register(MeituanWaimaiAppSetting.class);
        this.register(SettlementSetting.class);
        this.register(WithdrawSetting.class);
        this.register(AlipaySetting.class);
        this.register(SmsSetting.class);
        this.register(FeieyunSetting.class);
        this.register(SuperPasswordSetting.class);
    }

    private void register(Class setting) {
        String key = setting.getSimpleName();

        if (SETTING_CLASS_MAP.containsKey(key)) {
            throw new RuntimeException("setting key is exists, key:" + key);
        }
        SETTING_CLASS_MAP.put(key, setting);
    }

    @Override
    public int saveSetting(Object setting, int version) {
        return saveSetting(setting.getClass(), JSON.toJSONString(setting), version);
    }

    @Override
    public int saveSetting(String key, String content, int version) {
        return saveSetting(SETTING_CLASS_MAP.get(key), content, version);
    }

    @Override
    public int saveSetting(Class clazz, String content, int version) {
        String key = clazz.getSimpleName();
        if (version == -1) {
            version = systemSettingRepository.findMaxVersionByKey(key) + 1;
        }
        SystemSetting systemSetting = systemSettingRepository.findByKeyAndVersion(key, version);
        if (systemSetting == null) {
            systemSetting = new SystemSetting();
            systemSetting.setKey(key);
            systemSetting.setVersion(version);
            systemSetting.setStatus(isSingle(clazz) ? SystemSettingStatus.ACTIVE : SystemSettingStatus.LOCKED);
        } else {
            if (systemSetting.getStatus() == SystemSettingStatus.ACTIVE && !isSingle(clazz)) {
                //如果是当前配置是激活的，那么就需要重新生成一个版本，进行保存
                return saveSetting(clazz, content, -1);
            }
        }
        if (isSingle(clazz)) {
            systemSetting.setVersion(0);
        }
        systemSetting.setContent(content);
        systemSettingRepository.save(systemSetting);
        eventPublisher.publishEvent(new SettingChangeEvent(clazz));
        return systemSetting.getVersion();
    }

    private static boolean isSingle(Class clazz) {
        return clazz.isAnnotationPresent(Single.class);
    }

    @Override
    public <T> T findSetting(Class<T> clazz, int version) {
        String key = clazz.getSimpleName();
        SystemSetting systemSetting = systemSettingRepository.findByKeyAndVersion(key, version);
        return parseSetting(clazz, systemSetting);
    }

    @Override
    public <T> T findSetting(String key, int version) {
        return findSetting((Class<T>) SETTING_CLASS_MAP.get(key), version);
    }

    @Override
    @Cacheable(key = "#clazz.getSimpleName()")
    public <T> T findActiveSetting(Class<T> clazz) {
        String key = clazz.getSimpleName();
        List<SystemSetting> settingList = systemSettingRepository.findByKeyAndStatus(key, SystemSettingStatus.ACTIVE);
        return parseSetting(clazz, settingList.size() > 0 ? settingList.get(0) : null);
    }

    private <T> T parseSetting(Class<T> clazz, SystemSetting systemSetting) {
        if (systemSetting == null) {
            try {
                return (T) Class.forName(clazz.getName()).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return JSON.parseObject(systemSetting.getContent(), clazz);
    }

    @Override
    public <T> T findActiveSetting(String key) {
        return findActiveSetting((Class<T>) SETTING_CLASS_MAP.get(key));
    }

    @Override
    public void deleteSetting(String key, int version) {
        deleteSetting(SETTING_CLASS_MAP.get(key), version);
    }

    @Override
    public void deleteSetting(Class clazz, int version) {
        SystemSetting systemSetting = systemSettingRepository.findByKeyAndVersion(clazz.getSimpleName(), version);
        systemSettingRepository.delete(systemSetting.getId());
    }

    @Override
    @CacheEvict(key = "#key")
    public void activeSetting(String key, int version) {
        applicationContext.getBean(SystemSettingService.class).activeSetting(SETTING_CLASS_MAP.get(key), version);
    }

    @Override
    @CacheEvict(key = "#clazz.getSimpleName()")
    public void activeSetting(Class clazz, int version) {
        String key = clazz.getSimpleName();
        List<SystemSetting> systemSettings = systemSettingRepository.findByKeyAndStatus(key, SystemSettingStatus.ACTIVE);
        if (systemSettings.size() > 0) {
            for (SystemSetting systemSetting : systemSettings) {
                systemSetting.setStatus(SystemSettingStatus.LOCKED);
            }
        }
        SystemSetting systemSetting = systemSettingRepository.findByKeyAndVersion(key, version);
        systemSetting.setStatus(SystemSettingStatus.ACTIVE);
        systemSettings.add(systemSetting);
        systemSettingRepository.save(systemSettings);
        eventPublisher.publishEvent(new SettingChangeEvent(clazz, SettingChangeEvent.Type.ACTIVE));
    }

    @Override
    public List<SystemSettingVersionDTO> findVersionList(String key) {
        return findVersionList(SETTING_CLASS_MAP.get(key));
    }

    @Override
    public List<SystemSettingVersionDTO> findVersionList(Class clazz) {
        String key = clazz.getSimpleName();
        List<SystemSettingVersionDTO> dtos = systemSettingRepository.findByKey(key).stream().map(o -> {
            SystemSettingVersionDTO dto = new SystemSettingVersionDTO();
            dto.setVersion(o.getVersion());
            dto.setStatus(o.getStatus());
            return dto;
        }).collect(Collectors.toList());
        return dtos;
    }
}
