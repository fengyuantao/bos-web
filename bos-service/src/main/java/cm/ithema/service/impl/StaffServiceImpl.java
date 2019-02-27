package cm.ithema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cm.ithema.dao.base.StaffDao;
import cm.ithema.domian.Staff;
import cm.ithema.service.StaffService;
import cm.ithema.utils.PageUtils;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffDao staffDao;


	public void saveStaff(Staff staff) {
		staffDao.save(staff);

	}


	@Override
	public List<Staff> findAllStaff() {
		List<Staff> staffs = staffDao.findAll();
		return staffs;
	}


	@Override
	public Integer findAllCount() {
		
		return staffDao.findAllCount();
	}


	@Override
	public List<Staff> findAllStaff(PageUtils pageUtils,String SQL) {
		// TODO Auto-generated method stub
		
		List<Staff> staffs = staffDao.findAllByPages(SQL,pageUtils.getStartcount(),pageUtils.getEndtcount());
		return staffs;
	}


	@Override
	public Boolean deleteStaff(String[] id) {
		// É¾³ýÊý¾Ý
		String sql = "delete Staff where id in (:ids)";
		try {
			staffDao.delete(id,sql);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
		
	}


	@Override
	public Boolean updateStaff(Staff model) {
		// ÐÞ¸Ästaff
		try {
			staffDao.update(model);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}






}
