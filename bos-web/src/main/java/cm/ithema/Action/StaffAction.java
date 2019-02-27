package cm.ithema.Action;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import cm.ithema.IbaseAction.IbaseAction;
import cm.ithema.domian.Staff;
import cm.ithema.service.StaffService;
import cm.ithema.utils.PageUtils;


@Controller
@Scope("prototype")
public class StaffAction extends IbaseAction<Staff>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private StaffService staffService;
	public String saveStaff() {
		try {
			staffService.saveStaff(model);
			return "staff";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR";
		}
		
		
	}
	
	private Integer page;
	private Integer rows;
	
	public String findAllCount() {
		Integer result = staffService.findAllCount();
		 System.out.println("result:"+result);
		return NONE;
	}
	
	public String findStaff() {
		System.out.println("page:"+page + ", rows:" + rows);
		// 查询总记录数
		try {
		Integer allCount = staffService.findAllCount();
		System.out.println("allCount:"+allCount +" ,page:"+page + ", rows:" + rows);
		PageUtils<Staff> pageUtils = new PageUtils<Staff>(page,rows,allCount);
		String sql = "From Staff";
		List<Staff> staffs = staffService.findAllStaff(pageUtils,sql);
		pageUtils.setRows(staffs);
		/*
		JSONObject obj = (JSONObject) JSON.parse(pageUtils.toString());
		obj.remove("currentPage");
		obj.remove("endtcount");
		obj.remove("pageSize");
		obj.remove("startcount");
		obj.remove("totalCount");
		*/
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		  //属性排斥集合,强调某些属性不需要或者一定不能被序列化
		   Set<String> excludes = filter.getExcludes();
		   //属性包含集合,强调仅需要序列化某些属性.具体用哪一个,看实际情况.此处我用的前者
		   //Set<String> includes = filter.getIncludes();
		   //排除不需序列化的属性
		   String[] args= {"currentPage","endtcount","pageSize","startcount","totalCount"};
		   for (String string : args) {
		      excludes.add(string);
		   }
		   //调用fastJson的方法,对象转json,
		   //参数一:需要被序列化的对象
		   //参数二:用于过滤属性的过滤器
		   //参数三:关闭循环引用,若不加这个,页面无法展示重复的属性值
		String JsonStaff = JSON.toJSONString(pageUtils,filter,SerializerFeature.DisableCircularReferenceDetect);
		System.out.println(JsonStaff);
		
			ServletActionContext.getResponse().getWriter().print(JsonStaff);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return NONE;
	}
	
	// 删除数据
	private String ids;
	public String deleteStaff()   {
		
		System.out.print(ids);
		if(ids == null) {
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":\"请至少选择一条数据\"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return NONE;
		}
		String[] id = ids.split(",");
		Boolean flag = staffService.deleteStaff(id);
		
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":"+flag+"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return NONE;
	}
	// 编辑 修改
	
	public String editStaff() {
		if(model == null) {
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":false}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Boolean flag = staffService.updateStaff(model);
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":"+flag+"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return NONE;
	}
	
	public void setIds(String ids) {
		this.ids = ids;
	}


	public void setPage(Integer page) {
		this.page = page;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
	}
	
	
}
