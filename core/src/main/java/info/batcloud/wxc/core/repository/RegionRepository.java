package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.Region;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegionRepository extends CrudRepository<Region, Long> {

    List<Region> findByParentId(long parentId);

    List<Region> findByLevel(int level);

    Region findByNameLikeAndLevel(String name, int level);

    Region findByNameLikeAndParentId(String name, long parentId);

}
