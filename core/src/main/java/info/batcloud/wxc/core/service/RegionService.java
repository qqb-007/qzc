package info.batcloud.wxc.core.service;

import info.batcloud.wxc.core.dto.RegionDTO;
import info.batcloud.wxc.core.entity.Region;

import java.util.List;

public interface RegionService {

    List<Region> findByParentId(long parentId);

    List<Region> findByLevel(int level);

    Region findById(long id);

    RegionDTO toDTO(Region region);

    boolean sync();
}
