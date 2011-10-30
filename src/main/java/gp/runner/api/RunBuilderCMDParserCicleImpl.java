/**
 * 
 */
package gp.runner.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author oleksii.zozulenko
 */
public class RunBuilderCMDParserCicleImpl extends AbstractRunBuilderService implements
		RunBuilderCMDParser
{
	
	public RunBuilderCMDParserCicleImpl()
	{
		super();
	}
	
	/* (non-Javadoc)
	 * @see gp.runner.api.RunBuilderCMDParser#parse(java.lang.String[])
	 */
	@Override
	public RunBuilderConfig parse(String[] args)
	{
		if (args == null)
		{
			return null;
		}
		
		RunBuilderConfig conf = new RunBuilderConfig();
		
		for (int i = 0; i < args.length; i++)
		{
			String currenctCmdArgument = args[i];
			if (currenctCmdArgument.equalsIgnoreCase("--development"))
			{
				logger.info("Runner in development mode.");
				
				Properties devproperties = startupFactory.readProperties("props/dev.properties");
				if (devproperties != null && !devproperties.isEmpty())
				{
					conf.setConfigPath(devproperties.getProperty("config.app"));
					conf.setSuitePath(devproperties.getProperty("suite.path"));
					conf.setLogPath(devproperties.getProperty("log.path"));
					conf.setLogLevel(devproperties.getProperty("log.level"));
					conf.setLogType(Integer.parseInt(devproperties.getProperty("log.type")));
				}
				else
				{
					// printUseDevProperties("Main");
				}
				conf.setDevelopment(true);
				continue;
			}
			else if (currenctCmdArgument.equalsIgnoreCase("--self-test"))
			{
				conf.setSelfTest(true);
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-emailreport"))
			{
				conf.setSentResult(true);
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-config"))
			{
				conf.setConfigPath(args[++i]);
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-suite"))
			{
				conf.setSuitePath(args[++i]);
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-log"))
			{
				if (args[i + 1].equalsIgnoreCase("--html"))
				{
					conf.setLogPath(args[i + 2]);
					i += 2;
					// 1 - value of html log
					conf.setLogType(1);
				}
				else if (args[i + 1].equalsIgnoreCase("--file"))
				{
					conf.setLogPath(args[i + 2]);
					i += 2;
					// 2 - value of file log
					conf.setLogType(2);
				}
				else if (args[i + 1].equalsIgnoreCase("--stream"))
				{
					conf.setLogPath(args[i + 2]);
					i += 2;
					// 3 - value of stream output
					conf.setLogType(3);
				}
				else if (args[i + 1].equalsIgnoreCase("--jpa"))
				{
					i += 1;
					conf.setLogType(6);
				}
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-name"))
			{
				String currentExecutionName = args[++i];
				conf.setCurrentExecutionName(currentExecutionName);
				logger.info("Current execution name is: " + currentExecutionName);
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-trace"))
			{
				conf.setLogLevel("trace");
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-debug"))
			{
				conf.setLogLevel("debug");
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-error"))
			{
				conf.setLogLevel("error");
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-warn"))
			{
				conf.setLogLevel("warn");
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-info"))
			{
				conf.setLogLevel("info");
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-ignore"))
			{
				String sIgnoredSuitesTmp = args[i + 1];
				String[] sIgnoredSuites = sIgnoredSuitesTmp.split(",");
				sIgnoredSuites[0] = sIgnoredSuites[0].replaceFirst("\"", "");
				sIgnoredSuites[sIgnoredSuites.length - 1] = sIgnoredSuites[sIgnoredSuites.length - 1]
						.replace("\"", "");
				conf.setIgnoredSuites(new ArrayList<String>(Arrays.asList(sIgnoredSuites)));
				i += 2;
				logger.info("Ignored suites are: " + conf.getIgnoredSuites());
			}
			else if (currenctCmdArgument.equalsIgnoreCase("-include"))
			{
				String sIgnoredSuitesTmp = args[i + 1];
				String[] includesSuites = sIgnoredSuitesTmp.split(",");
				includesSuites[0] = includesSuites[0].replaceFirst("\"", "");
				includesSuites[includesSuites.length - 1] = includesSuites[includesSuites.length - 1]
						.replace("\"", "");
				conf.setIncludesSuites(new ArrayList<String>(Arrays.asList(includesSuites)));
				i += 2;
				
				logger.info("Included suites are: " + conf.getIncludesSuites());
			}
			else if (i == args.length)
			{
				throw new IllegalArgumentException(
						"Wrong command line arguments. See usage documentation.");
			}
		}
		
		String sLogPath = conf.getLogPath().replace("\"", "");
		int iLastSlash = sLogPath.lastIndexOf("/");
		
		if (iLastSlash == -1)
		{
			sLogPath = sLogPath + "/";
		}
		
		conf.setLogPath(sLogPath);
		
		return conf;
	}
	
}
