package framework.bullet;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;


import org.suites.AbstractFunctionalTestCase;

import project.utils.Connection;

import common.exec.refl.ObjectFactory;

import gp.runner.api.configure.YamlConfigReader;
import gp.wrapers.utils.YamlDataReader;

/**
 * <pre>
 * Class contains functions executed before launch tests.
 * It's parsing command line, create ObjectFactory, etc
 *
 * &lt;b&gt;Creation date:&lt;/b&gt;18.06.2009 by oleksii.zozulenko
 * &lt;b&gt;Modification date:&lt;/b&gt;18.06.2009 by oleksii.zozulenko
 * </pre>
 *
 * @author oleksii.zozulenko
 */
public class RunnerFactory
{
	ProjectConfigReader configReader;

	private Connection _oConn;

	static java.util.logging.Logger logger = java.util.logging.Logger
			.getLogger(RunnerFactory.class.getName());

	public RunnerFactory()
	{
		logger.setLevel(Level.INFO);
	}


	public Properties readProperties(String fileName)
	{
		// Read properties file.
		configReader = new ProjectConfigReader();
		File propFile = new File(fileName);
		return configReader.readPropertiesFile(propFile);
	}

	/**
	 * Function read file with suites and return suiteset
	 *
	 * @param suiteFilePath
	 * @return Map<String, ArrayList>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, ArrayList> readSuite(String suiteFilePath) throws Exception
	{
		IRunnerFactorySuiteReader suiteReader;

		if (suiteFilePath.contains(".yaml"))
		{
			suiteReader = new RunnerFactorySuiteReaderWithPackagesImpl();
		}
		else
		{
			throw new UnsupportedOperationException(
					"Unsupported tyoe of suites file. Only XML or YAML supported.");
		}

		return suiteReader.readSuite(suiteFilePath);
	}

	@SuppressWarnings("unchecked")
	public Class[] getTestConstructorClasses()
	{
		Class[] loConstrParams =
		{ Connection.class, Object.class, };

		return loConstrParams;
	}

	/**
	 * This method creates array with testcases by test names.
	 *
	 * @param lsTestsNames
	 * @param loConstrParams
	 * @param loValues
	 * @param conn
	 * @return
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public AbstractFunctionalTestCase[] buildTCObjects(ArrayList<String> lsTestsNames,
			Class[] loConstrParams, Object[] loValues, Connection conn)
			throws InvocationTargetException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, ClassNotFoundException
	{

		ArrayList<AbstractFunctionalTestCase> loSuite = new ArrayList<AbstractFunctionalTestCase>();
		ObjectFactory oOF = new ObjectFactory(loConstrParams, loValues);

		for (String testName : lsTestsNames)
		{
			try
			{
				/*
				 * Create test classes.
				 * If cant create test by name this test is skipped and message about this event writes to error log
				 */
				Object oObj = oOF.callObject(testName);
				loSuite.add((AbstractFunctionalTestCase) oObj);
				logger.info("Test is created: " + testName);
			}
			catch (ClassNotFoundException oCNFEx)
			{
				oCNFEx.printStackTrace();
				continue;
			}
		}

		ArrayList<AbstractFunctionalTestCase> loSuiteFiltered = loSuite;

		// Collections.shuffle(loSuiteFiltered);
		AbstractFunctionalTestCase[] loTests = loSuiteFiltered
				.toArray(new AbstractFunctionalTestCase[loSuiteFiltered.size()]);

		return loTests;
	}

	/**
	 * @param lsTestsNames
	 * @param loConstrParams
	 * @param loValues
	 * @param conn
	 * @return
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public AbstractFunctionalTestCase[] buildTCObject(String lsTestsNames, Class[] loConstrParams,
			Object[] loValues, Connection conn) throws InvocationTargetException,
			NoSuchMethodException, InstantiationException, IllegalAccessException,
			ClassNotFoundException
	{

		ArrayList<AbstractFunctionalTestCase> loSuite = new ArrayList<AbstractFunctionalTestCase>();

		ObjectFactory oOF = new ObjectFactory(loConstrParams, loValues);
		try
		{
			/*
			 * Create test classes.
			 * If cant create test by name this test is skipped and message about this event writes to error log
			 */
			Object oObj = oOF.callObject(lsTestsNames);
			loSuite.add((AbstractFunctionalTestCase) oObj);
			logger.info("Test is created: " + lsTestsNames);
		}
		catch (ClassNotFoundException oCNFEx)
		{
			oCNFEx.printStackTrace();

		}

		ArrayList<AbstractFunctionalTestCase> loSuiteFiltered = loSuite;

		// Collections.shuffle(loSuiteFiltered);
		AbstractFunctionalTestCase[] loTests = loSuiteFiltered
				.toArray(new AbstractFunctionalTestCase[loSuiteFiltered.size()]);

		return loTests;
	}



	public Connection getConfigOptions(String configPath) throws Exception
	{
		if (configPath.contains(".yaml"))
		{
			this._oConn = new YamlConfigReader(configPath).read();
		}
		else
		{
			throw new UnsupportedOperationException("There is unknown config type " + configPath);
		}
		return this._oConn;
	}

	public List<Connection> getConfig(String configPath) throws Exception
	{
		List<Connection> confs = null;

		if (configPath.contains(".yaml"))
		{

			if (configPath.contains("all."))
			{
				YamlDataReader yamlreader = new YamlDataReader();
				yamlreader.setDataRoot("");
				List<String> configsFileNames = yamlreader
						.readListFixture(configPath, String.class);

				List<Connection> tmpConnList;
				confs = new ArrayList<Connection>();
				for (String configfile : configsFileNames)
				{
					System.err.println(configfile);
					tmpConnList = new YamlConfigReader(configfile).readList();
					confs.addAll(tmpConnList);
					tmpConnList.clear();
				}
			}
			else
			{
				confs = new YamlConfigReader(configPath).readList();
			}

		}
		else
		{
			throw new UnsupportedOperationException("There is unknown config type " + configPath);

		}

		return confs;
	}

	public String renameBrowserIDToName(String browserID)
	{
		String sBrowserName = "";
		Map<String, String> lsBrowsersNameReference = new Hashtable<String, String>();

		// Hardcoded reference between browser id for selenium and name
		lsBrowsersNameReference.put("*safari", "Safari");
		lsBrowsersNameReference.put("*safariproxy", "Safari");
		lsBrowsersNameReference.put("*firefox", "FireFox 3");
		lsBrowsersNameReference.put("*pifirefox", "FireFox Proxy");
		lsBrowsersNameReference.put("*firefoxproxy", "FireFox 3");
		lsBrowsersNameReference.put("*firefoxchrome", "FireFox 3");
		lsBrowsersNameReference.put("*firefox2", "FireFox 2");
		lsBrowsersNameReference.put("*firefox3", "FireFox 2");
		lsBrowsersNameReference.put("*chrome", "FireFox 3");
		lsBrowsersNameReference.put("*iehta", "IE7");
		lsBrowsersNameReference.put("*iexploreproxy", "IE7");
		lsBrowsersNameReference.put("*iexplore", "IE6");
		lsBrowsersNameReference.put("*piiexplore", "IE Proxy");
		lsBrowsersNameReference.put("*opera", "Opera 9");
		lsBrowsersNameReference.put("*googlechrome", "Chrome");

		sBrowserName = lsBrowsersNameReference.get(browserID);

		return sBrowserName;
	}
}
