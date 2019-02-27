package cm.ithema.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;
import org.apache.struts2.StrutsSpringJUnit4TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opensymphony.xwork2.ActionProxy;

import cm.ithema.Action.StaffAction;
import cm.ithema.domian.Staff;
import cm.ithema.domian.User;
import cm.ithema.service.StaffService;
import cm.ithema.utils.PageUtils;

/*这里只需要加载使用到的文件，不需要测试的文件不要加载（注意），我们现在是测试action，
由于struts启动会默认加载三个文件，分别是struts-default.xml，struts-plugin.xml，struts.xml
如果我们要单独对某一个struts文件做测试，在struts.xml文件中单独引用即可，比如<includefile="struts/struts-test.xml"/>*/


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:applicationContext.xml"})
public class ActionTest extends StrutsSpringJUnit4TestCase<StaffAction>{

	@Autowired
	private StaffService staffService;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		 //模拟request,response
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		MockHttpServletResponse response = new MockHttpServletResponse();
		 //创建mock对象
		
		staffService = EasyMock.createMock(StaffService.class);
		User user = new User();
		user.setUsername("fyt");
		user.setPassword("123456");
	}
	
	@Test
	public void testQuery() throws Exception{
	    ActionProxy proxy = null;
	    StaffAction staffAction =null;
	    Cookie cookie1 = new Cookie("Hm_lvt_080836300300be57b7f34f4b3e97d911","1545996562");
	    cookie1.setPath("/");
	    Cookie cookie2 = new Cookie("JSESSIONID","1D6F8EA0088863C5D067B0D50E5B928F");
	    cookie2.setPath("/bos-web/");
	    request.setCookies(cookie1,cookie1);
	    
	    request.setParameter("page", "1");
	    request.setParameter("rows", "1");
	
	    request.setParameter("JSESSIONID", "1D6F8EA0088863C5D067B0D50E5B928F");
	    
	    request.setParameter("Hm_lvt_080836300300be57b7f34f4b3e97d911", "1545996562");
	    
	    proxy =getActionProxy("staffAction_findStaff.action"); // 指定测试哪个方法
	    staffAction =(StaffAction)proxy.getAction();

	//模拟对象的行为
	//记录mock对象期望的行为
	    EasyMock.expect(staffService.findAllCount()).andStubReturn(64);//我们期望返回test-mock，则会切断正常的调用
	    
	    Staff staff = new Staff();
	    staff.setDeltag("1");
	    staff.setName("zhangsan");
	    List<Staff> staffs = new ArrayList<Staff>();
	    staffs.add(staff);
	    PageUtils<Staff> pageUtils = new PageUtils<Staff>(1,1,64);
		String sql = "From Staff";
	   // EasyMock.expect(staffService.findAllStaff(pageUtils,sql)).andReturn(staffs);
	//进入replay阶段
	    EasyMock.replay(staffService);//重放Mock对象，测试时以录制的对象预期行为代替真实对象的行为
	    staffAction.setStaffService(staffService);//这里必须要用mock之后的对象，才能达到mock模拟对象行为的作用，比如我们这里让queryResult这个方法返回”test-mock”

	    proxy.execute();
	    EasyMock.verify(staffService);
	    //必须要有，否则被测试的action里面的request值为null
	    //        String queryResult = test.query();
   }

}
