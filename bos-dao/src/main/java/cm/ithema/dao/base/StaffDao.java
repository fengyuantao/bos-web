package cm.ithema.dao.base;

import java.util.List;

import cm.ithema.domian.Staff;

public interface StaffDao extends IbaseDao<Staff>{

	Integer findAllCount();

	void delete(String[] id);



}
