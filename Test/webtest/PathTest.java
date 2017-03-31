package webtest;


import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.db.util.BookUploader;
import com.db.util.KeyUtil;
import com.db.util.UserUtil;

public class PathTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		File file = new File("/Users/zhenghejun/Downloads/子不语.mobi");
		System.out.println(file.getName());
	}
	
	@Test
	public void testUP()
	{
		BookUploader bUploader= new BookUploader();
		bUploader.Init();
		bUploader.Save("/Users/zhenghejun/Downloads/mobi");
		bUploader.Destroy();
	}
	
	@Test
	public void getEmail()
	{
		UserUtil uu = new UserUtil();
		
		System.out.println(uu.getEmaiAdress("2008")==null);
	}
	
	@Test
	public void addUser()
	{
		UserUtil uuUserUtil = new UserUtil();
		uuUserUtil.setEmail("oLMyCt68H_xvZh6I2mH1L6eniY0E", "2332333@qq.com");
		uuUserUtil.setEmail("2008", "nihao@qq.com");
	}
	
	@Test
	public void testSb()
	{
		StringBuilder sbBuilder = new StringBuilder();
		String string = sbBuilder.toString();
		System.out.println(string.equals(""));
	}
	
	@Test
	public void testMasking() {
		KeyUtil maskingKeyUtil = new KeyUtil();
		System.out.println(maskingKeyUtil.testMasking("我是长者金三月半"));
		
	}
	
	@Test
	public void testEmail()
	{
		String emailString = "123@qq.com";
		System.out.println(emailString.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"));
		
		
	}

}
