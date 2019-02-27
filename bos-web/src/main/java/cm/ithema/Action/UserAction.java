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
		// ��ȡsession
		String valicode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		System.out.println("��֤���ǣ�"+valicode);
		System.out.println("�������֤���ǣ�"+code);
		if(StringUtils.isNoneBlank(code) && code.equals(valicode)) {
			// ��֤����ȷ
			if("".equals(model.getPassword()) || model.getPassword() == null) {
				
				this.addActionError("���������룡");
				return "login";
			}
            if("".equals(model.getUsername()) || model.getUsername() == null) {
				
				this.addActionError("�������¼�˻���");
				return "login";
			}
			User user = userService.login(model);
			if(user != null) {

				ServletActionContext.getRequest().getSession().setAttribute("user", user);
					return "home";
			}else {
				this.addActionError("�û�������!");
				System.out.println("�û�������");
				return "login";
			}
			
			
		}else {
			this.addActionError("�������֤�����");
			return "login";
		}
	}
// �û�ע��
	public String loginout() {
		
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	// �޸��û�����
	private String newPassword;
	private String rePassword;
	
	public String changePassword()  {
		
		if("".equals(newPassword) || "".equals(rePassword)) {
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":false,\"errorMsg\":\"�������������\"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		if(!newPassword.equals(rePassword)) {
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":false,\"errorMsg\":\"�������벻һ��\"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(newPassword.equals(user.getPassword())) {
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":false,\"errorMsg\":\"�¾�����һ�£������������\"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		if(newPassword.length() < 6 || newPassword.length() > 12) {
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":false,\"errorMsg\":\"������6��12λ��������\"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		userService.changePassword(user, newPassword);
		try {
			ServletActionContext.getResponse().getWriter().write("{\"success\":true,\"errorMsg\":\"�����޸ĳɹ���\"}");
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
