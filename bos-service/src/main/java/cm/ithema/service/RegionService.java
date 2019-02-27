package cm.ithema.service;

import java.util.List;

import cm.ithema.domian.Region;
import cm.ithema.utils.PageUtils;

public interface RegionService {

	void saveOrUpdate(List<Region> lists);

	Integer findRegionCount();

	List<Region> findRegion(PageUtils pageUtil);

}
