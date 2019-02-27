package cm.ithema.Action;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cm.ithema.IbaseAction.IbaseAction;
import cm.ithema.domian.User;
import cm.ithema.service.UserService;
@Controller
@Scope("prototype")
public class UserAction extends IbaseAction<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	
	@Autowired
	private UserService userService;
	
	public String login() {
		// 获取session
		String valicode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		System.out.println("验证码是："+valicode);
		System.out.println("输入的验证码是："+code);
		if(StringUtils.isNoneBlank(code) && code.equals(valicode)) {
			// 验证码正确
			if("".equals(model.getPassword()) || model.getPassword() == null) {
				
				this.addActionError("请输入密码！");
				return "login";
			}
            if("".equals(model.getUsername()) || model.getUsername() == null) {
				
				this.addActionError("请输入登录账户！");
				return "login";
			}
			User user = userService.login(model);
			if(user != null) {

				ServletActionContext.getRequest().getSession().setAttribute("user", user);
					return "home";
			}else {
				this.addActionError("用户不存在!");
				System.out.println("用户不存在");
				return "login";
			}
			
			
		}else {
			this.addActionError("输入的验证码错误！");
			return "login";
		}
	}
// 用户注销
	public String loginout() {
		
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	// 修改用户密码
	private String newPassword;
	private String rePassword;
	
	public String changePassword()  {
		
		if("".equals(newPassword) || "".equals(rePassword)) {
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":false,\"errorMsg\":\"有输入框无数据\"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		if(!newPassword.equals(rePassword)) {
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":false,\"errorMsg\":\"两次密码不一致\"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(newPassword.equals(user.getPassword())) {
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":false,\"errorMsg\":\"新旧密码一致，请更换新密码\"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		if(newPassword.length() < 6 || newPassword.length() > 12) {
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":false,\"errorMsg\":\"请输入6到12位长度密码\"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		userService.changePassword(user, newPassword);
		try {
			ServletActionContext.getResponse().getWriter().write("{\"success\":true,\"errorMsg\":\"密码修改成功！\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public void setCode(String code) {
		this.code = code;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

}
