package cm.ithema.dao.base.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cm.ithema.dao.base.RegionDao;
import cm.ithema.domian.Region;
@Repository
public class RegionDaoImpl extends IbaseDaoImpl<Region> implements RegionDao{

	@Override
	public Integer findRegionCount() {
		String sql = "select count(*) from Region";
		List<Long> counts = (List<Long>) this.getHibernateTemplate().find(sql);
		if(counts != null && counts.size() >0) {
			return counts.get(0).intValue();
		}
		return null;
	}

	

}
