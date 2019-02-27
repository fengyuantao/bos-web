package cm.ithema.web.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.struts2.StrutsSpringJUnit4TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cm.ithema.Action.StaffAction;
import cm.ithema.dao.base.RegionDao;
import cm.ithema.domian.Region;
import cm.ithema.domian.Staff;
import cm.ithema.service.RegionService;
import cm.ithema.service.StaffService;
import cm.ithema.utils.PageUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:applicationContext.xml"})
public class ServiceTest{
	@Autowired
	private RegionService regionService;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testfindAll() {
		PageUtils<Region> pageUtils = new PageUtils<Region>(10,12,65);
		String SQL = "FROM Region";
		List<Region> regions = regionService.findRegion(pageUtils);
		System.out.println(regions.size());
		assertEquals(10,regions.size());
		
	}
	
	

}
