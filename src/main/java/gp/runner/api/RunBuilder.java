/**
 *
 */
package gp.runner.api;

import gp.runner.web.RunnerConsole;
import gp.runner.web.RunnerConsoleContextHandlerBased;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.suites.AbstractFunctionalTestCase;
import org.suites.FuncTestResult;
import org.xml.sax.SAXException;

import project.utils.Connection;
import project.utils.ConnectionListHelper;



/**
 * @author oleksii.zozulenko
 */
@SuppressWarnings("unchecked")
public class RunBuilder extends AbstractRunBuilderService
{


	boolean is_development;

	boolean bIsSelfTest;

	boolean bIsSentResult;

	final static long TIMEOUT_BETWEEN_TESTS = 60000; // 5 min(s)

	protected int MAX_RERUN_COUNT_PER_TEST = 1;

	protected int MAX_RERUN_TESTS_COUNT;

	protected TestRepeatCounterList testsCounterList;

	RunnerMode runnerMode;

	public RunBuilder()
	{
		super();
	}

	public RunBuilder(RunnerMode mode)
	{
		super();
		this.runnerMode = mode;
	}


	public static void main(String[] args) throws Exception
	{
		RunnerMode mode = RunnerMode.PROD;

		RunBuilder builder = new RunBuilder();
		RunBuilderConfig cmdconfig = builder.parseCmdLine(args);

		if ((cmdconfig.isDevelopment() || cmdconfig.isSelfTest()) && cmdconfig.getLogType() < 5)
		{
			mode = RunnerMode.DEV;
		}
		else
		{
			mode = RunnerMode.PROD;
		}

		builder.setRunnerMode(mode);

		// builder.initRepeatTestService();
		builder.doRunSuite(cmdconfig);
	}

	private void setRunnerMode(RunnerMode mode)
	{
		this.runnerMode = mode;
	}

	public void doRunSuite(String[] args) throws ClassNotFoundException, SAXException,
			ParserConfigurationException, UnknownHostException
	{
		RunBuilderConfig cmdconfig = this.parseCmdLine(args);
		this.doRunSuite(cmdconfig);
	}

	public void doRunSuite(RunBuilderConfig cmdconfig) throws ClassNotFoundException, SAXException,
			ParserConfigurationException, UnknownHostException
	{
		FileOutputStream errorFile;
		FileOutputStream outputFile;

		// System.err.println(args);

		/*
		 * Default initialisations
		 */

		// Log filename
		SimpleDateFormat oSDF = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date oFileDate = new Date();
		String startTime = oSDF.format(oFileDate);
		String sLogFileName = "java_results_" + startTime + ".";

		// configs values
		Map<String, ArrayList> lsSuitesMap = new Hashtable<String, ArrayList>();

		// Objects
		RunnerConsole runnerConsole = new RunnerConsoleContextHandlerBased();
		// RunnerConsole runnerConsole = new RunnerConsoleServletBased();

		testsCounterList = new TestRepeatCounterList();

		// Parse command line
		/*
		 * if (args.length < 4)
		 * {
		 * printUseError("Main");
		 * return;
		 * }
		 */

		// configuration values
		String sConfigPath = cmdconfig.getConfigPath();
		String sSuitePath = cmdconfig.getSuitePath();
		String sLogPath = cmdconfig.getLogPath();
		String sLogLevel = cmdconfig.getLogLevel();
		List<String> sIgnoredSuites = cmdconfig.getIgnoredSuites();
		List<String> includesSuites = cmdconfig.getIncludesSuites();
		int iLogType = cmdconfig.getLogType();

		bIsSelfTest = cmdconfig.isSelfTest();

		bIsSentResult = cmdconfig.isSentResult();
		is_development = cmdconfig.isDevelopment();

		if (bIsSelfTest)
		{
			System.out.println(cmdconfig);
		}
		if (is_development)
		{
			MAX_RERUN_COUNT_PER_TEST = 0;
		}

		String currentExecutionName = cmdconfig.getCurrentExecutionName();

		ThreadGroup obbtests = new ThreadGroup("obbtests");

		try
		{
			String logsdirectoryname = "logs";
			File logsfolder = new File(logsdirectoryname);
			if (!logsfolder.exists())
			{
				boolean iscreated = logsfolder.mkdir();
				if (!iscreated)
				{
					logsdirectoryname = ".";
				}
			}
			// Section of redirect all System printstream to specific files
			if (!is_development && !bIsSelfTest)
			{
				errorFile = new FileOutputStream(new File(logsdirectoryname + "/"
						+ currentExecutionName + "_errors" + "_" + startTime + ".log"));
				System.setErr(new PrintStream(errorFile));
				outputFile = new FileOutputStream(new File(logsdirectoryname + "/"
						+ currentExecutionName + "_outputs" + "_" + startTime + ".log"));
				System.setOut(new PrintStream(outputFile));
			}

			System.out.println("\n--------------------------------------------------\n"
					+ "***: Runner started: " + startTime
					+ "\n--------------------------------------------------\n\n");

			List<Connection> configs = getConfigs(sConfigPath);


			lsSuitesMap = startupFactory.readSuite(sSuitePath);
			Set<String> keySet = lsSuitesMap.keySet();

			ArrayList<String> suiteNames = this
					.getRunSuites(keySet, sIgnoredSuites, includesSuites);
			Collections.sort(suiteNames);

			if (bIsSelfTest)
			{
				System.out.println("Running suites are: " + suiteNames);
			}

			logger.info("Running suites are: " + suiteNames);

			ArrayList<String> testNames = new ArrayList<String>();

			// Move all tests in one queue
			for (String suiteName : suiteNames)
			{
				ArrayList<String> lsSuite = lsSuitesMap.get(suiteName);
				Collections.shuffle(lsSuite);

				testNames.addAll(lsSuite);

			}

			if (bIsSelfTest)
			{
				System.out.println("Running tests are: \n"
						+ testNames.toString().replaceAll(",", "\n-"));
			}
			runnerConsole.createThreadManager(4040);

			/*
			 * if it's duplicates of connection then create ONE queue of tests
			 */
			logger.info("Enter into exec section");

			MAX_RERUN_TESTS_COUNT = testNames.size() / 2;

			if (bIsSelfTest)
			{
				this.runTestsInDevMode(configs, testNames);
			}
			else
			{
				runTestsInParallel(configs, testNames);
			}
			// end of configuration section

			// TODO: sweet things
			/*
			 * 2) rename browser name
			 * String sBrowserName = "";
			 * String selBrowser = conn.getSeleniumBrowser();
			 * if (iLogType == 5 || iLogType == 6)
			 * {
			 * sBrowserName = selBrowser;
			 * }
			 * else
			 * {
			 * sBrowserName = startupFactory.renameBrowserIDToName(selBrowser);
			 * }
			 */

		}
		/*
		 * Main throws error codes:
		 * 1 - ERROR_INVALID_FUNCTION -- Other different errors
		 * 2 - ERROR_FILE_NOT_FOUND -- Class not found
		 * 6 - ERROR_INVALID_HANDLE -- If parsed xml file incorrect (this can be in suite file or in configuration file)
		 */
		catch (SQLException oSQLEx)
		{
			oSQLEx.printStackTrace(System.err);

			// System.exit(1);
		}
		catch (ClassNotFoundException oCNFEx)
		{
			oCNFEx.printStackTrace(System.err);

			throw oCNFEx;
		}
		catch (IOException oIOEx)
		{
			oIOEx.printStackTrace(System.err);

			// System.exit(1);
		}
		catch (ParserConfigurationException oPCEx)
		{
			oPCEx.printStackTrace(System.err);

			throw oPCEx;
		}
		catch (SAXException oSAXEx)
		{
			oSAXEx.printStackTrace(System.err);

			throw oSAXEx;
		}
		catch (Exception oEx)
		{
			oEx.printStackTrace(System.err);
			oEx.getCause().printStackTrace(System.err);

			// System.exit(1);
		}
		finally
		{

			runnerConsole.stopThreadManager();

			// Interrupt all remaining threads in tests
			obbtests.interrupt();

			String endTime = oSDF.format(new Date());
			System.out.println("\n--------------------------------------------------\n"
					+ "***: Runner finished: " + endTime
					+ "\n--------------------------------------------------\n\n");


		}
	}

	public List<Connection> getConfigs(String sConfigPath) throws Exception
	{
		RunBuilderConfigsReader configReader = new RunBuilderConfigsReaderImpl();
		List<Connection> cnfs = configReader.getConfigs(sConfigPath);

		return cnfs;
	}

	/**
	 * @param obbtests
	 * @param configs
	 * @param testNames
	 * @return
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * @throws UnknownHostException
	 */
	private void runTestsInParallel(List<Connection> configs, final ArrayList<String> testNames)
			throws InvocationTargetException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, InterruptedException,
			UnknownHostException, Exception
	{
		final ThreadGroup obbtests = new ThreadGroup("obbtests");
		final ConcurrentMap<ThreadGroup, Queue<AbstractFunctionalTestCase>> parellConfigs = new ConcurrentHashMap<ThreadGroup, Queue<AbstractFunctionalTestCase>>();

		is_development = false;

		if (!is_development)
		{

			ConcurrentMap<Connection, List<Connection>> connectionsMap = ConnectionListHelper
					.getDuplicatedConfigs(configs);

			int j = 0;
			for (final Connection conf : connectionsMap.keySet())
			{
				List<Connection> currectConnList = connectionsMap.get(conf);
				if (currectConnList == null || currectConnList.size() == 0)
				{
					final Queue<AbstractFunctionalTestCase> testSuite = new ConcurrentLinkedQueue<AbstractFunctionalTestCase>();
					logger.info("Section where for each conf own tests queue");

					// create tests queue for each non duplicated configuration

					Object[] loValues =
					{ conf, null, };

					AbstractFunctionalTestCase[] loadedTests;

					loadedTests = startupFactory.buildTCObjects(testNames, startupFactory
							.getTestConstructorClasses(), loValues, conf);

					testSuite.addAll(Arrays.asList(loadedTests));

					final ThreadGroup confGroup = new ThreadGroup(obbtests, "conf: "
							+ conf.getLabel());
					parellConfigs.put(confGroup, testSuite);

					// for every connection will be created new thread. This thread conatins queue of tests.

					new Thread(confGroup, "conftests: " + confGroup.getName())
					{
						public void run()
						{
							Queue<AbstractFunctionalTestCase> oTests = parellConfigs.get(confGroup);
							String configName = conf.getLabel();

							Thread.currentThread()
									.setName(
											"conftests: "
													+ confGroup.getName()
													+ ". <br>TestsQuery: <ul><li>"
													+ oTests.toString()
															.replaceFirst("\\[", "")
															.replace("\\]", "")
															.replaceAll(",", "</li>")
													+ "</ul><br />");

							do
							{

								AbstractFunctionalTestCase o_t = oTests.poll();
								if (o_t == null)
								{
									break;
								}
								logger.info("tests queue size: " + oTests.size());

								// Run first tests in queue
								FuncTestResult stat = o_t.run();

								String testName = o_t.getClass().getName();

								oTests = checkIfNeedToAddTestsRepeat(stat, configName, oTests, o_t,
										null,
										testName);

							}
							while (!oTests.isEmpty());

							logger.info("Testing of configuration " + confGroup + " finished");
						}
					}.start();

					// Wait 5 min between conf
					Thread.sleep(TIMEOUT_BETWEEN_TESTS);

				}
				else
				{
					logger.info("Section where for many conf but one tests queue");
					List<Connection> sameConfigs = new ArrayList<Connection>();
					sameConfigs.add(conf);
					sameConfigs.addAll(connectionsMap.get(conf));

					final ThreadGroup sameConfTest = new ThreadGroup(obbtests, "duplicatedConf"
							+ (j++));

					// add all test into one queue
					final Queue<String> testsQueue = new ConcurrentLinkedQueue<String>();
					testsQueue.addAll(testNames);

					// Create map which contains connection and it's name. Need for controlling which connetion finished and run next step on this connection.
					final ConcurrentMap<String, Connection> lc = new ConcurrentHashMap<String, Connection>();

					for (Connection conn : sameConfigs)
					{
						lc.put(conn.getLabel(), conn);
					}

					for (final String connLabel : lc.keySet())
					{
						final Connection conn = lc.get(connLabel);
						final Thread th = new Thread(sameConfTest, "conf:" + conn.getLabel())
						{
							public void run()
							{
								Queue<String> tmpQueue = testsQueue;
								do
								{
									logger.log(Level.FINEST, "Tests queue: "
											+ testsQueue.toString().replace(", ", "\n"));
									tmpQueue = runDuplicatedConfigs(sameConfTest, lc, conn,
											testsQueue);
								}
								while (!tmpQueue.isEmpty());

								logger.info("Testing of configuration " + conn.toString()
										+ " finished");
							};
						};
						th.start();

						// Wait 1 min between conf
						Thread.sleep(TIMEOUT_BETWEEN_TESTS);
					}

				}

			}
		}
		else
		{
			runTestsInDevMode(configs, testNames);
		}

		/*
		 * Next code need for waiting while all tests threads will finish.
		 */
		Thread[] obbtestsThreads = new Thread[obbtests.activeCount()];
		obbtests.enumerate(obbtestsThreads);

		for (Thread obbth : obbtestsThreads)
		{
			if (obbth.getName().contains("conf"))
			{
				obbth.join();
			}
		}

		logger.info("Full testing cycles finished");
	}

	/**
	 * @param configs
	 * @param testNames
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private void runTestsInDevMode(List<Connection> configs, final ArrayList<String> testNames)
			throws InvocationTargetException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, ClassNotFoundException
	{
		logger.info("Development section");

		for (Connection conn : configs)
		{
			// conn.setGridMode(false);
			Object[] loValues =
			{ conn, null, };



			AbstractFunctionalTestCase[] loadedTests = startupFactory.buildTCObjects(testNames,
					startupFactory.getTestConstructorClasses(), loValues, conn);
			// TestSuite oSuite = startupFactory.createSuite(loadedTests);

			if (!bIsSelfTest)
			{
					for (AbstractFunctionalTestCase tc : loadedTests)
				{
					tc.run();
				}
			}
		}
	}

	private Queue<String> runDuplicatedConfigs(final ThreadGroup sameConfTest,
			final ConcurrentMap<String, Connection> lc, final Connection conn,
			Queue<String> testsQueue)
	{

		Object[] loValues =
		{ conn, null, };
		final AbstractFunctionalTestCase[] test;

		try
		{
			if (!testsQueue.isEmpty())
			{

				test = startupFactory.buildTCObject(testsQueue.poll(), startupFactory
						.getTestConstructorClasses(), loValues, conn);

				if (test.length > 0)
				{

					AbstractFunctionalTestCase o_t = test[0];
					FuncTestResult status = o_t.run();

					String configName = conn.getDuplicateLabel();
					String testName = o_t.getClass().getName();

					// I Dont' know if queue is updated
					testsQueue = checkIfNeedToAddTestsRepeat(status, (configName == null) ? conn
							.getLabel()
							: configName, null, null, testsQueue, testName);
				}

			}

		}
		catch (Throwable e)
		{
			e.printStackTrace(System.err);
			// return false;
		}
		return testsQueue;
	}

	private Queue checkIfNeedToAddTestsRepeat(FuncTestResult status, String configName,
			Queue<AbstractFunctionalTestCase> oTests, AbstractFunctionalTestCase o_t,
			Queue<String> testsQueue, String testName)
	{
		boolean fail = false;

		if (fail)
		{

			if (testsCounterList.isEmpty())
			{
				return doUpdateRepeadQueue(configName, oTests, o_t, testsQueue, testName, null);
			}
			else
			{
				// MAX count per config
				int testsQueueRepeadSize = testsCounterList.getTestsQueueSize(configName);
				if (testsQueueRepeadSize < MAX_RERUN_TESTS_COUNT)
				{
					TestRepeatCounter counter = testsCounterList.getTestRepeatCounter(configName,
							testName);

					if (counter == null)
					{
						return doUpdateRepeadQueue(configName, oTests, o_t, testsQueue, testName,
								counter);
					}
					else if (counter.getCount() < MAX_RERUN_COUNT_PER_TEST)
					{
						return doUpdateRepeadQueue(configName, oTests, o_t, testsQueue, testName,
								counter);

					}

				}
			}

		}

		if (oTests != null)
		{
			return oTests;
		}
		else
		{
			return testsQueue;
		}
	}

	private Queue doUpdateRepeadQueue(String configName, Queue<AbstractFunctionalTestCase> oTests,
			AbstractFunctionalTestCase o_t, Queue<String> testsQueue, String testName,
			TestRepeatCounter counter)
	{
		if (counter == null)
		{
			counter = new TestRepeatCounter(configName, testName, 0);
			testsCounterList.add(counter);
		}

		if (oTests != null && o_t != null)
		{
			oTests.add(o_t);
		}
		else if (testsQueue != null)
		{
			testsQueue.add(testName);
		}
		counter.updateCount();

		if (oTests != null)
		{
			return oTests;
		}
		else
		{
			return testsQueue;
		}
	}

	/**
	 * Function parsed command line and creates configuratin for excecution of suites
	 *
	 * @param args
	 * @return
	 */
	public RunBuilderConfig parseCmdLine(String[] args)
	{
		RunBuilderCMDParser cmdParser = new RunBuilderCMDParserCicleImpl();

		return cmdParser.parse(args);
	}

	protected ArrayList<String> getRunSuites(Collection<String> suitesInFile,
			Collection<String> ignoredSuites, Collection<String> includedSuites)
	{
		ArrayList<String> runSuites = null;
		ArrayList<String> included = null;

		if (suitesInFile == null && ignoredSuites == null && includedSuites == null)
		{
			return null;
		}

		if (ignoredSuites == null && includedSuites == null)
		{
			return new ArrayList<String>(suitesInFile);
		}

		if (includedSuites == null && ignoredSuites != null)
		{
			if (ignoredSuites.size() != 0)
				return (ArrayList<String>) CollectionUtils.subtract(suitesInFile, ignoredSuites);
			else return new ArrayList<String>(suitesInFile);
		}

		if (includedSuites.size() != 0)
		{
			included = (ArrayList<String>) CollectionUtils.intersection(suitesInFile,
					includedSuites);
		}

		if (ignoredSuites == null)
		{
			return included;
		}

		if (ignoredSuites.size() != 0)
		{
			runSuites = (ArrayList<String>) CollectionUtils.subtract(included, ignoredSuites);
		}
		else
		{
			return new ArrayList<String>(suitesInFile);
		}

		return runSuites;
	}

}
