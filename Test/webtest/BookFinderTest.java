package webtest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.db.util.BookFinder;

public class BookFinderTest {
	private BookFinder bf;

	@Before
	public void setUp()  {
		bf=new BookFinder();

		
	}

	@After
	public void tearDown()  {
		
	}
	
	@Test
	public void test() {
		String result = bf.Find("版");
		System.out.println(result);
		result = bf.Find("(");
		System.out.println(result);
		
	}

}
