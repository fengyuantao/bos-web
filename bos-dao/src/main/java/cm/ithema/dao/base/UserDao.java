package cm.ithema.dao.base;

import cm.ithema.domian.User;

public interface UserDao extends IbaseDao<User> {
	User findUser(String name,String password);

	void changePassword(User user, String newPass);

}
