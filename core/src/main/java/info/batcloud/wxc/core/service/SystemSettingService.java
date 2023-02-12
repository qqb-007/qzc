package info.batcloud.wxc.core.service;

import info.batcloud.wxc.core.dto.SystemSettingVersionDTO;

import java.util.List;

public interface SystemSettingService {


    int saveSetting(Object setting, int version);

    int saveSetting(String key, String content, int version);

    int saveSetting(Class clazz, String content, int version);

    <T> T findSetting(Class<T> clazz, int version);

    <T> T findSetting(String key, int version);

    <T> T findActiveSetting(Class<T> clazz);

    <T> T findActiveSetting(String key);

    void deleteSetting(String key, int version);

    void deleteSetting(Class clazz, int version);

    void activeSetting(String key, int version);

    void activeSetting(Class clazz, int version);

    List<SystemSettingVersionDTO> findVersionList(String key);

    List<SystemSettingVersionDTO> findVersionList(Class clazz);
}
