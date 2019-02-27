package cm.ithema.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cm.ithema.domian.User;

public class BosUtils {

	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	public static User getUser() {
		return (User) getSession().getAttribute("user");
	}
}
