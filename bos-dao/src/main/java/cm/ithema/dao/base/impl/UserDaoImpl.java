package cm.ithema.dao.base.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cm.ithema.dao.base.UserDao;
import cm.ithema.domian.User;

@Repository
public class UserDaoImpl extends IbaseDaoImpl<User> implements UserDao{

	@Override
	public User findUser(String name, String password) {
		String sql = "From User u where u.username = ? and u.password = ?";
		List<User> users = (List<User>) this.getHibernateTemplate().find(sql, name,password);
		if(users != null && users.size() > 0) {
			return users.get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public void changePassword(User user, String newPass) {
		String sql = "update User set password = ? where id = ?";
		this.getHibernateTemplate().bulkUpdate(sql, newPass,user.getId());
		
	}

}
