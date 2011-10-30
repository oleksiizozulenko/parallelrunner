package framework.bullet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author oleksii.zozulenko
 */
public class PortPoolTest
{
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}
	
	/**
	 * Test method for {@link framework.bullet.PortPool#PortPool()}.
	 */
	@Test
	public void testPortPool()
	{
		PortPool t_pool = new PortPool();
		assertNotNull(t_pool);
		
		PortPool t_pool2 = null;
		assertNull(t_pool2);
	}
	
	/**
	 * Test method for {@link framework.bullet.PortPool#isBusy(int)}.
	 */
	@Test
	public void testIsBusy()
	{
		PortPool t_pool = new PortPool();
		t_pool.setBusy(123);
		assertFalse(t_pool.isBusy(123));
		assertFalse(t_pool.isAvailable(123));
		assertFalse(t_pool.isListen(123));
		
		t_pool.setFree(123456);
		t_pool.setBusy(123456);
		assertFalse(t_pool.isBusy(123456));
		assertTrue(t_pool.isAvailable(123456));
		assertFalse(t_pool.isListen(123456));
		
		t_pool.setFree(12345);
		t_pool.setListen(12345);
		t_pool.setBusy(12345);
		assertTrue(t_pool.isBusy(12345));
		assertFalse(t_pool.isAvailable(12345));
		assertFalse(t_pool.isListen(12345));
		
		t_pool.setListen(12389);
		t_pool.setBusy(12389);
		assertFalse(t_pool.isBusy(12389));
		assertFalse(t_pool.isAvailable(12389));
		assertFalse(t_pool.isListen(12389));
		
		t_pool.setFree(1238);
		t_pool.setListen(1238);
		t_pool.setBusy(1238);
		t_pool.setBusy(1238);
		assertTrue(t_pool.isBusy(1238));
		assertTrue(t_pool.isBusy(1238));
		assertFalse(t_pool.isAvailable(1238));
		assertFalse(t_pool.isListen(1238));
	}
	
	/**
	 * Test method for {@link framework.bullet.PortPool#isAvailable(int)}.
	 */
	@Test
	public void testIsAvailable()
	{
		PortPool t_pool = new PortPool();
		
		assertFalse(t_pool.isAvailable(321));
		assertFalse(t_pool.isBusy(321));
		assertFalse(t_pool.isListen(321));
		
		t_pool.setFree(123);
		assertTrue(t_pool.isAvailable(123));
		assertFalse(t_pool.isBusy(123));
		assertFalse(t_pool.isListen(123));
		
		t_pool.setFree(1238);
		t_pool.setFree(1238);
		assertTrue(t_pool.isAvailable(1238));
		assertTrue(t_pool.isAvailable(1238));
		assertFalse(t_pool.isBusy(1238));
		assertFalse(t_pool.isListen(1238));
	}
	
	/**
	 * Test method for {@link framework.bullet.PortPool#isListen(int)}.
	 */
	@Test
	public void testIsListen()
	{
		PortPool t_pool = new PortPool();
		t_pool.setListen(123);
		assertFalse(t_pool.isListen(123));
		assertFalse(t_pool.isAvailable(123));
		assertFalse(t_pool.isBusy(123));
		
		t_pool.setFree(1234);
		t_pool.setListen(1234);
		assertTrue(t_pool.isListen(1234));
		assertFalse(t_pool.isAvailable(1234));
		assertFalse(t_pool.isBusy(1234));
		
		t_pool.setFree(1238);
		t_pool.setListen(1238);
		t_pool.setListen(1238);
		assertTrue(t_pool.isListen(1238));
		assertTrue(t_pool.isListen(1238));
		assertFalse(t_pool.isBusy(1238));
		assertFalse(t_pool.isAvailable(1238));
	}
	
	/**
	 * Test method for {@link framework.bullet.PortPool#getAvailablePorts()}.
	 */
	@Test
	@Ignore
	public void testGetAvailablePorts()
	{
		PortPool t_pool = new PortPool();
		Integer[] liExp =
		{ 123, 1234 };
		
		for (Integer in : liExp)
		{
			t_pool.setFree(in);
		}
		
		Integer[] li = t_pool.getAvailablePorts();
		
		assertEquals(li, liExp);
	}
	
	/**
	 * Test method for {@link framework.bullet.PortPool#getFirstAvailablePort()}.
	 */
	@Test
	public void testGetFirstAvailablePort()
	{
		PortPool t_pool = new PortPool();
		
		assertEquals(0, t_pool.getFirstAvailablePort());
		
		t_pool.setFree(1234);
		assertEquals(1234, t_pool.getFirstAvailablePort());
		assertTrue(t_pool.isAvailable(1234));
		assertFalse(t_pool.isListen(1234));
		assertFalse(t_pool.isBusy(1234));
		
		t_pool.setFree(123);
		assertEquals(1234, t_pool.getFirstAvailablePort());
		assertTrue(t_pool.isAvailable(1234));
		assertFalse(t_pool.isListen(1234));
		assertFalse(t_pool.isBusy(1234));
	}
	
	/**
	 * Test method for {@link framework.bullet.PortPool#getFirstListenPort()}.
	 */
	@Test
	public void testGetFirstListenPort()
	{
		PortPool t_pool = new PortPool();
		
		t_pool.setFree(1234);
		t_pool.setFree(123);
		
		assertEquals(0, t_pool.getFirstListenPort());
		
		t_pool.setListen(1234);
		
		assertEquals(1234, t_pool.getFirstListenPort());
		assertTrue(t_pool.isListen(1234));
		assertFalse(t_pool.isAvailable(1234));
		assertFalse(t_pool.isBusy(1234));
		
		t_pool.setListen(123);
		
		assertEquals(1234, t_pool.getFirstListenPort());
		assertTrue(t_pool.isListen(1234));
		assertFalse(t_pool.isAvailable(1234));
		assertFalse(t_pool.isBusy(1234));
	}
	
	@Test
	public void testIsEmpty()
	{
		Vector<Integer> lsvec = null;
		PortPool t_pool = new PortPool();
		
		assertTrue(t_pool.isEmpty(lsvec));
		
		lsvec = new Vector<Integer>();
		assertTrue(t_pool.isEmpty(lsvec));
		
		lsvec.add(1);
		assertFalse(t_pool.isEmpty(lsvec));
		
	}
}
