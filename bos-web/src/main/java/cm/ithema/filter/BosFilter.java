package cm.ithema.filter;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import cm.ithema.domian.User;
import cm.ithema.utils.BosUtils;

public class BosFilter extends MethodFilterInterceptor{

	// µÇÂ¼¹ýÂËÆ÷
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		User user = BosUtils.getUser();
		if(user == null) {
			return "login";
		}
		return invocation.invoke();
	}

}