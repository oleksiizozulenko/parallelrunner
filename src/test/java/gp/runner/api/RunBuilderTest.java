package gp.runner.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gp.wrapers.utils.YamlDataReader;

import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import qa.tool.runner.fixtures.mapping.RunBuilderGetRunSuitesInputData;
import qa.tool.runner.fixtures.mapping.RunBuilderParseCmdLineInputData;

public class RunBuilderTest
{
	RunBuilder instance;
	
	protected Logger logger;
	
	YamlDataReader reader;
	
	public RunBuilderTest()
	{
		logger = Logger.getLogger(this.getClass().getName());
		reader = new YamlDataReader();
		reader.setDataRoot("src/test/resources/");
	}
	
	@Before
	public void setUp() throws Exception
	{
		instance = new RunBuilder(RunnerMode.DEV);
	}
	
	@After
	public void tearDown() throws Exception
	{
	}
	
	@Test
	public void testParseCmdLine() throws ClassNotFoundException, SAXException,
			ParserConfigurationException
	{
		
		try
		{
			
			List<RunBuilderParseCmdLineInputData> data = reader.readListFixture(
					"runbuilderParseCmdLineInputData.yaml",
					RunBuilderParseCmdLineInputData.class);
			
			for (RunBuilderParseCmdLineInputData tdata : data)
			{
				logger.info(tdata.toString());
				assertEquals(tdata.getExpected(), instance.parseCmdLine(tdata.getArrCmdLine()));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void testGetRunSuites()
	{
		try
		{
			List<RunBuilderGetRunSuitesInputData> data = reader.readListFixture(
					"runbuilderGetRunSuitesInputData.yaml",
					RunBuilderGetRunSuitesInputData.class);
			
			for (RunBuilderGetRunSuitesInputData tdate : data)
			{
				logger.info(tdate.toString());
				List<String> act = instance.getRunSuites(tdate.getFilesuite(), tdate
						.getIgnore(), tdate.getInclude());
				
				assertEquals(tdate.getExpected(), act);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
}
