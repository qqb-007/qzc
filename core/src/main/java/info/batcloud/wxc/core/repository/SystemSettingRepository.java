package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.SystemSetting;
import info.batcloud.wxc.core.enums.SystemSettingStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SystemSettingRepository extends CrudRepository<SystemSetting, Long>{

    SystemSetting findByKeyAndVersion(String key, int version);

    List<SystemSetting> findByKey(String key);

    @Query(value = "select if(max(`version`) is null, 0, max(`version`)) from setting where type='SystemSetting' and `key`=?1", nativeQuery = true)
    int findMaxVersionByKey(String key);

    List<SystemSetting> findByKeyAndStatus(String key, SystemSettingStatus status);
}
