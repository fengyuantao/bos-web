package cm.ithema.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cm.ithema.dao.base.UserDao;
import cm.ithema.dao.base.impl.UserDaoImpl;
import cm.ithema.domian.User;
import cm.ithema.service.UserService;
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Override
	public User findAll() {
		
		
		return (User) userDao.findAll();
	}

	@Override
	public User login(User user) {
		
		return userDao.findUser(user.getUsername(), user.getPassword());
	}

	@Override
	public void changePassword(User user, String newPass) {
		userDao.changePassword(user,newPass);
		
	}

}
