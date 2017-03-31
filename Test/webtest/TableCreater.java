package webtest;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.db.util.HibernateSessionFactory;

public class TableCreater {
	
	private Session session;
	private Transaction transaction;

	@Before
	public void setUp() throws Exception {
		session = HibernateSessionFactory.getSession();
		transaction = session.beginTransaction();
		
	}

	@After
	public void tearDown() throws Exception {
		transaction.commit();
		session.close();
	}

	@Test
	public void test() {
		
	}

}
