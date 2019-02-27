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
		// ��ѯ�ܼ�¼��
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
		  //�����ų⼯��,ǿ��ĳЩ���Բ���Ҫ����һ�����ܱ����л�
		   Set<String> excludes = filter.getExcludes();
		   //���԰�������,ǿ������Ҫ���л�ĳЩ����.��������һ��,��ʵ�����.�˴����õ�ǰ��
		   //Set<String> includes = filter.getIncludes();
		   //�ų��������л�������
		   String[] args= {"currentPage","endtcount","pageSize","startcount","totalCount"};
		   for (String string : args) {
		      excludes.add(string);
		   }
		   //����fastJson�ķ���,����תjson,
		   //����һ:��Ҫ�����л��Ķ���
		   //������:���ڹ������ԵĹ�����
		   //������:�ر�ѭ������,���������,ҳ���޷�չʾ�ظ�������ֵ
		String JsonStaff = JSON.toJSONString(pageUtils,filter,SerializerFeature.DisableCircularReferenceDetect);
		System.out.println(JsonStaff);
		
			ServletActionContext.getResponse().getWriter().print(JsonStaff);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return NONE;
	}
	
	// ɾ������
	private String ids;
	public String deleteStaff()   {
		
		System.out.print(ids);
		if(ids == null) {
			try {
				ServletActionContext.getResponse().getWriter().write("{\"success\":\"������ѡ��һ������\"}");
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
	// �༭ �޸�
	
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
