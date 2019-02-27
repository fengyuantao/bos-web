package com.httpunit.test;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class TestStaffAction {
	
	@Test
	public void test1() {
		WebConversation wb = new WebConversation();
		HttpUnitOptions.setScriptingEnabled(false);
		try {
			 WebResponse res = wb.getResponse("http:////localhost:8080//bos-web//login.jsp");
			// assertEuals(404,res.getResponseCode());
			 System.out.print(res.getText());
			 String code = res.getNewCookieValue("JSESSION");
			 PostMethodWebRequest resp =  new PostMethodWebRequest("http://localhost:8080/bos-web/userAction_login.action");
			 
			 resp.setParameter("username", "fyt");
			 resp.setParameter("password", "111111");
			 resp.setParameter("code", code);
			 wb.getCookieValue("JSESSION");
			 
			 WebResponse returns = wb.getResource(resp);
			 System.out.print(returns);
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
