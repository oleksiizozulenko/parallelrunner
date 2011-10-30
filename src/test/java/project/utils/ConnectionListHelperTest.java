/*
 * ---------------------------------------------------------------
 * File: ConnectionListHelperTest.java
 * Project: Runner
 * Created: Dec 7, 2010 10:33:35 AM
 * Author: oleksii.zozulenko
 * ---------------------------------------------------------------
 */
package project.utils;

import static org.junit.Assert.fail;
import gp.wrapers.utils.YamlDataReader;

import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author oleksii.zozulenko
 */
public class ConnectionListHelperTest
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
	 * Test method for {@link project.utils.ConnectionListHelper#getDuplicatedConfigs(java.util.Collection)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	@Ignore
	public void testGetDuplicatedConfigs()
	{
		
		// FIXME: collection compares wrong
		try
		{
			YamlDataReader yaml_reader = new YamlDataReader();
			List<Connection> indata = yaml_reader.readListFixture(
					"connectionListHelperDuplicatesIndata.yaml",
					Connection.class);
			
			Map<Connection, List<Connection>> duplicates = yaml_reader
					.readFixture("connectionListHelperDuplicatesoutdata.yaml");
			
			Set expKeys = duplicates.keySet();
			Map actRes = ConnectionListHelper.getDuplicatedConfigs(
					indata);
			Set actSet = actRes.keySet();
			
			if (!(CollectionUtils.isEqualCollection(actSet, expKeys)))
			{
				Assert.fail("Key sets are not equals. Actual: '" + actSet + "' Expected: '"
						+ expKeys + "'");
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail(e.toString());
		}
		
	}
}
