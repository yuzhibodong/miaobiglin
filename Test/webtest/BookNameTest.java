
package webtest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.db.util.BookFinder;

public class BookNameTest {
	
	private BookFinder bf;

	@Before
	public void setUp() throws Exception {
		bf = new BookFinder();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String content = "b";
		content = content.replaceAll("['\\\"]", "").trim();
		System.out.println(content);
		
		
		System.out.println(bf.Find(content));
		
		
	}

}
