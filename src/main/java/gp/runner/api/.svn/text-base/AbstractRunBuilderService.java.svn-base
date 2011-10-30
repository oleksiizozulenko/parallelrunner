/**
 *
 */
package gp.runner.api;

import framework.bullet.RunnerFactory;

import java.util.List;
import java.util.logging.Level;

import project.utils.Connection;

/**
 * @author oleksii.zozulenko
 */
public abstract class AbstractRunBuilderService implements
		RunBuilderConfigsReader, RunBuilderCMDParser
{
	RunnerFactory startupFactory = null;
	java.util.logging.Logger logger = null;
	
	public AbstractRunBuilderService()
	{
		logger = java.util.logging.Logger.getLogger(this.getClass().getName());
		logger.setLevel(Level.SEVERE);
		startupFactory = new RunnerFactory();
	}
	
	protected void printConfigsDependency(List<Connection> configs)
	{
		for (Connection conn : configs)
		{
			System.out.println(conn.getApplicationUrl() + " " + conn.getEmailFrom() + " "
					+ conn.getEmailUserName());
		}
		
	}
	
	public List<Connection> getConfigs(String sConfigPath) throws Exception
	{
		return null;
	}
	
	public RunBuilderConfig parse(String[] args)
	{
		return null;
	}
	
}
