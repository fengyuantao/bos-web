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

//根据POI读取excel
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
		// 查询总条数
		try {
		Integer regionCount = regionService.findRegionCount();
		
		PageUtils pageUtil = new PageUtils<Region>(page,rows,regionCount);
		// 查询分页数据
		List<Region> region = regionService.findRegion(pageUtil);
		
		pageUtil.setRows(region);
		
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
