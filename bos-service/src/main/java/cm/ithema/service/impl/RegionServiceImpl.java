package cm.ithema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cm.ithema.dao.base.RegionDao;
import cm.ithema.domian.Region;
import cm.ithema.service.RegionService;
import cm.ithema.utils.PageUtils;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionDao regionDao;
	
	@Override
	public void saveOrUpdate(List<Region> lists) {
		//ÅúÁ¿±£´æ
		for(Region region : lists) {
			regionDao.saveOrUpdate(region);
		}
		
	}

	@Override
	public Integer findRegionCount() {
		
		
		return regionDao.findRegionCount();
	}

	@Override
	public List<Region> findRegion(PageUtils pageUtil) {
		String sql = "from Region";
		
		return regionDao.findAllByPages(sql, pageUtil.getStartcount(), pageUtil.getEndtcount());
	}

}
