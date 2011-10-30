package framework.bullet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class PropertiesReaderTest
{
	private static final String PROPERTIES_FILE_NAME = "./file.properties";
	
	@Before
	public void setUp() throws IOException
	{
		File file = new File(PROPERTIES_FILE_NAME);
		file.createNewFile();
	}
	
	@After
	public void tearDown()
	{
		File file = new File(PROPERTIES_FILE_NAME);
		file.delete();
	}
	
	@Test
	public void testReadPropertiesFileFile()
	{
		ProjectConfigReader pr = new ProjectConfigReader();
		assertNull(pr.readPropertiesFile(new File("fsfkdl")));
		assertNotNull(pr.readPropertiesFile(new File(PROPERTIES_FILE_NAME)));
	}
	
	@Test
	public void testReadPropertiesFileInputStream()
	{
		ProjectConfigReader pr = new ProjectConfigReader();
		FileInputStream filestream_null;
		try
		{
			filestream_null = new FileInputStream(new File("fsfkdl"));
			assertNull(pr.readPropertiesFile(filestream_null));
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		
		FileInputStream filestream;
		try
		{
			filestream = new FileInputStream(new File(PROPERTIES_FILE_NAME));
			assertNotNull(pr.readPropertiesFile(filestream));
		}
		catch (FileNotFoundException e)
		{
			fail(e.getMessage());
			
		}
		
	}
	
	@Test
	@Ignore
	public void testGetFileFromJar() throws IOException
	{
		ProjectConfigReader pr = new ProjectConfigReader();
		assertNull(pr.getFileFromJar("fjkdshk"));
		assertNotNull(pr.getFileFromJar(PROPERTIES_FILE_NAME.replaceFirst("./tests", "")));
	}
	
	@Test
	public void testIsPropertyFileExists()
	{
		ProjectConfigReader pr = new ProjectConfigReader();
		assertFalse(pr.isPropertyFileExists(new File("bla")));
		assertTrue(pr.isPropertyFileExists(new File(PROPERTIES_FILE_NAME)));
		
	}
	
}
