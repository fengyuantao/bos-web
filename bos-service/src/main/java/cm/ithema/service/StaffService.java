package cm.ithema.service;

import java.util.List;

import cm.ithema.domian.Staff;
import cm.ithema.utils.PageUtils;

public interface StaffService {

	void saveStaff(Staff staff);

	List<Staff> findAllStaff();

	Integer findAllCount();

	List<Staff> findAllStaff(PageUtils pageUtils,String SQL);

	Boolean deleteStaff(String[] id);

	Boolean updateStaff(Staff model);
	

	

	
}
