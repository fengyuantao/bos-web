package cm.ithema.Action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import cm.ithema.IbaseAction.IbaseAction;
import cm.ithema.domian.Region;
import cm.ithema.service.RegionService;
import cm.ithema.utils.PageUtils;
import cm.ithema.utils.PinYin4jUtils;

@Controller
@Scope("prototype")
public class ReginAction extends IbaseAction<Region>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File reginFile;
	
	@Autowired
	private RegionService regionService;

	
	
	public String reginUpload() throws  IOException {

//����POI��ȡexcel
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(reginFile));
		HSSFSheet sheet = workbook.getSheetAt(0);
		List<Region> lists = new ArrayList<Region>();
		
		for(Row row: sheet) {
			if(row.getRowNum() == 0) {
				continue;
			}
			String provice = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = 	row.getCell(3).getStringCellValue()	;
			
			String proviceTo = provice.substring(0, provice.length()-1);
			String cityTo = city.substring(0, city.length()-1);
			String districtTo = district.substring(0, district.length()-1);
			
			 String[] toPinyin = PinYin4jUtils.getHeadByString(proviceTo + cityTo +districtTo);
			 
			 String shortcode = StringUtils.join(toPinyin);
			String citycode = PinYin4jUtils.hanziToPinyin(cityTo,"");
					
			
			Region region = new Region(
					
					row.getCell(0).getStringCellValue(),
					provice,
					city,
					district,
					row.getCell(4).getStringCellValue(),
					shortcode,
					citycode,
					null
					);
			
		    lists.add(region);	
		}
		try {
			regionService.saveOrUpdate(lists);
			ServletActionContext.getResponse().getWriter().print("{\"success\":true}");
		} catch (Exception e) {
			ServletActionContext.getResponse().getWriter().print("{\"success\":false}");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}
	private Integer page;
	private Integer rows;
	
	public String findRegion() {
		// ��ѯ������
		try {
		Integer regionCount = regionService.findRegionCount();
		
		PageUtils pageUtil = new PageUtils<Region>(page,rows,regionCount);
		// ��ѯ��ҳ����
		List<Region> region = regionService.findRegion(pageUtil);
		
		pageUtil.setRows(region);
		
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
		String JsonStaff = JSON.toJSONString(pageUtil,filter,SerializerFeature.DisableCircularReferenceDetect);
		System.out.println(JsonStaff);
		
			ServletActionContext.getResponse().getWriter().print(JsonStaff);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return NONE;
	}
	
	public void setReginFile(File reginFile) {
		this.reginFile = reginFile;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
	
	
	
}
