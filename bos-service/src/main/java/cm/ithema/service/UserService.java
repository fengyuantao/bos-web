package cm.ithema.service;

import cm.ithema.domian.User;

public interface UserService {

	User findAll();
	User login(User user);
	void changePassword(User user,String newPass);
}
