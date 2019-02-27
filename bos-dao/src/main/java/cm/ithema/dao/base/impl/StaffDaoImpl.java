package cm.ithema.dao.base.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cm.ithema.dao.base.StaffDao;
import cm.ithema.domian.Staff;
@Repository
public class StaffDaoImpl extends IbaseDaoImpl<Staff> implements StaffDao {

	@Override
	public Integer findAllCount() {
		// 查询总条数
		String sql = "select count(*) from Staff";
		List<Long> counts = (List<Long>) this.getHibernateTemplate().find(sql);
		if(counts.get(0) <1L) {
			return 0;
		}else {
			return counts.get(0).intValue();
		}
		
		
	}

	@Override
	public void delete(String[] id) {
		// TODO Auto-generated method stub
		
	}

	
		
	



	

}
