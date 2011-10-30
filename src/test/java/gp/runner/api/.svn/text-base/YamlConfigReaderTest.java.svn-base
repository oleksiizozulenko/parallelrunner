package gp.runner.api;

import static org.junit.Assert.assertTrue;

import gp.runner.api.configure.YamlConfigReader;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.utils.Connection;

public class YamlConfigReaderTest
{
	YamlConfigReader reader;
	
	@Before
	public void setUp() throws Exception
	{
		File f = new File("src/test/resources/config.yaml");
		reader = new YamlConfigReader(f.getName());
	}
	
	@After
	public void tearDown() throws Exception
	{
		
	}
	
	@Test
	public void testRead() throws Exception
	{
		assertTrue(reader.read() instanceof Connection);
	}
	
	@Test
	public void testReadList() throws Exception
	{
		List<Connection> l = reader.readList();
		
		assertTrue(l.size() == 1);
		
	}
	
}
