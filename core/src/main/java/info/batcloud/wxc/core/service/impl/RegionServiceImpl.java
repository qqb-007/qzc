package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import info.batcloud.wxc.core.constants.CacheNameConstants;
import info.batcloud.wxc.core.dto.RegionDTO;
import info.batcloud.wxc.core.entity.Region;
import info.batcloud.wxc.core.enums.RegionStatus;
import info.batcloud.wxc.core.repository.RegionRepository;
import info.batcloud.wxc.core.service.RegionService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
@CacheConfig(cacheNames = CacheNameConstants.REGION)
public class RegionServiceImpl implements RegionService {

    private static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

    @Value("${amap.webApiKey}")
    private String amapWebAppKey;

    @Inject
    private RegionRepository regionRepository;

    @Override
    @Cacheable(key = "'CHILDREN_' + #parentId")
    public List<Region> findByParentId(long parentId) {
        return regionRepository.findByParentId(parentId);
    }

    @Override
    @Cacheable(key = "'LEVEL_' + #level")
    public List<Region> findByLevel(int level) {
        return regionRepository.findByLevel(level);
    }

    @Override
    @Cacheable(key = "'REGION_' + #id")
    public Region findById(long id) {
        return regionRepository.findOne(id);
    }

    @Override
    public RegionDTO toDTO(Region region) {
        if (region == null) {
            return null;
        }
        RegionDTO dto = new RegionDTO();
        BeanUtils.copyProperties(region, dto);
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CacheNameConstants.REGION, allEntries = true)
    public boolean sync() {
        regionRepository.deleteAll();
        String url = "https://restapi.amap.com/v3/config/district?key=" + amapWebAppKey + "&keywords=&subdistrict=3&extensions=base";
        try {
            String responseText = IOUtils.toString(new URL(url), "utf8");
            JSONObject jsonObject = JSON.parseObject(responseText);
            if(jsonObject.getIntValue("status") == 1) {
                JSONArray countryList = jsonObject.getJSONArray("districts");
                for (Object country : countryList) {
                    JSONObject countryObject = (JSONObject) country;
                    walkDistrict(countryObject, 0);
                }
            } else {
                logger.error("地区同步失败，" + responseText);
                return false;
            }
        } catch (IOException e) {
            logger.error("地区同步失败，", e);
            return false;
        }
        return false;
    }

    private void walkDistrict(JSONObject district, long parentId) {
        String level = district.getString("level");
        int levelVal = 0;
        switch (level) {
            case "city":
                levelVal = 1;
                break;
            case "district":
                levelVal = 2;
                break;
        }
        if(!level.equals("country")) {
            saveRegion(district.getLong("adcode"), parentId, district.getString("name"), levelVal);
        }
        JSONArray districts = district.getJSONArray("districts");
        for (Object o : districts) {
            walkDistrict((JSONObject) o, district.getLong("adcode"));
        }
    }

    private void saveRegion(long id, long parentId, String name, int level) {
        Region region = new Region();
        region.setLevel(level);
        region.setParentId(parentId);
        region.setName(name);
        region.setId(id);
        region.setStatus(RegionStatus.VALID);
        region.setPinyin(PinyinHelper.convertToPinyinString(name, "", PinyinFormat.WITHOUT_TONE));
        region.setShortPinyin(PinyinHelper.getShortPinyin(name));
        regionRepository.save(region);
    }
}
