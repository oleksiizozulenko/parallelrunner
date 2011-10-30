/*
 * ---------------------------------------------------------------
 * File: RunBuilderConfig.java
 * Project: Runner
 * Created: Sep 15, 2010 11:17:37 AM
 * Author: oleksii.zozulenko
 * ---------------------------------------------------------------
 */
package gp.runner.api;

import java.util.ArrayList;

/**
 * @author oleksii.zozulenko
 */
public class RunBuilderConfig
{
	private String configPath;
	
	private String suitePath = "";
	
	private String logPath = "";
	
	private String logLevel = "";
	
	private ArrayList<String> ignoredSuites = null;
	
	private ArrayList<String> includesSuites = null;
	
	private int logType = 0;
	
	private boolean selfTest = false;
	
	private boolean sentResult = false;
	
	private boolean development = false;
	
	private String currentExecutionName = "default";
	
	/**
	 * 
	 */
	public RunBuilderConfig()
	{
	}
	
	public String getConfigPath()
	{
		return configPath;
	}
	
	public void setConfigPath(String configPath)
	{
		this.configPath = configPath;
	}
	
	public String getSuitePath()
	{
		return suitePath;
	}
	
	public void setSuitePath(String suitePath)
	{
		this.suitePath = suitePath;
	}
	
	public String getLogPath()
	{
		return logPath;
	}
	
	public void setLogPath(String logPath)
	{
		this.logPath = logPath;
	}
	
	public String getLogLevel()
	{
		return logLevel;
	}
	
	public void setLogLevel(String logLevel)
	{
		this.logLevel = logLevel;
	}
	
	public ArrayList<String> getIgnoredSuites()
	{
		return ignoredSuites;
	}
	
	public String[] getIgnoredSuitesArr()
	{
		if (ignoredSuites == null)
			return null;
		
		return ignoredSuites.toArray(new String[this.ignoredSuites.size()]);
	}
	
	public void setIgnoredSuites(ArrayList<String> ignoredSuites)
	{
		this.ignoredSuites = ignoredSuites;
	}
	
	public ArrayList<String> getIncludesSuites()
	{
		return includesSuites;
	}
	
	public String[] getIncludesSuitesArr()
	{
		if (includesSuites == null)
			return null;
		
		return includesSuites.toArray(new String[this.includesSuites.size()]);
	}
	
	public void setIncludesSuites(ArrayList<String> includesSuites)
	{
		this.includesSuites = includesSuites;
	}
	
	public int getLogType()
	{
		return logType;
	}
	
	public void setLogType(int logType)
	{
		this.logType = logType;
	}
	
	public boolean isSelfTest()
	{
		return selfTest;
	}
	
	public void setSelfTest(boolean selfTest)
	{
		this.selfTest = selfTest;
	}
	
	public boolean isSentResult()
	{
		return sentResult;
	}
	
	public void setSentResult(boolean sentResult)
	{
		this.sentResult = sentResult;
	}
	
	public boolean isDevelopment()
	{
		return development;
	}
	
	public void setDevelopment(boolean development)
	{
		this.development = development;
	}
	
	public String getCurrentExecutionName()
	{
		return currentExecutionName;
	}
	
	public void setCurrentExecutionName(String currentExecutionName)
	{
		this.currentExecutionName = currentExecutionName;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((configPath == null) ? 0 : configPath.hashCode());
		result = prime * result
				+ ((currentExecutionName == null) ? 0 : currentExecutionName.hashCode());
		result = prime * result + (development ? 1231 : 1237);
		result = prime * result + ((ignoredSuites == null) ? 0 : ignoredSuites.hashCode());
		result = prime * result + ((includesSuites == null) ? 0 : includesSuites.hashCode());
		result = prime * result + ((logLevel == null) ? 0 : logLevel.hashCode());
		result = prime * result + ((logPath == null) ? 0 : logPath.hashCode());
		result = prime * result + logType;
		result = prime * result + (selfTest ? 1231 : 1237);
		result = prime * result + (sentResult ? 1231 : 1237);
		result = prime * result + ((suitePath == null) ? 0 : suitePath.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RunBuilderConfig other = (RunBuilderConfig) obj;
		if (configPath == null)
		{
			if (other.configPath != null)
				return false;
		}
		else if (!configPath.equals(other.configPath))
			return false;
		if (currentExecutionName == null)
		{
			if (other.currentExecutionName != null)
				return false;
		}
		else if (!currentExecutionName.equals(other.currentExecutionName))
			return false;
		if (development != other.development)
			return false;
		if (ignoredSuites == null)
		{
			if (other.ignoredSuites != null)
				return false;
		}
		else if (!ignoredSuites.equals(other.ignoredSuites))
			return false;
		if (includesSuites == null)
		{
			if (other.includesSuites != null)
				return false;
		}
		else if (!includesSuites.equals(other.includesSuites))
			return false;
		if (logLevel == null)
		{
			if (other.logLevel != null)
				return false;
		}
		else if (!logLevel.equals(other.logLevel))
			return false;
		if (logPath == null)
		{
			if (other.logPath != null)
				return false;
		}
		else if (!logPath.equals(other.logPath))
			return false;
		if (logType != other.logType)
			return false;
		if (selfTest != other.selfTest)
			return false;
		if (sentResult != other.sentResult)
			return false;
		if (suitePath == null)
		{
			if (other.suitePath != null)
				return false;
		}
		else if (!suitePath.equals(other.suitePath))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "RunBuilderConfig [configPath=" + configPath + ", currentExecutionName="
				+ currentExecutionName + ", development=" + development + ", ignoredSuites="
				+ ignoredSuites + ", includesSuites=" + includesSuites + ", logLevel=" + logLevel
				+ ", logPath=" + logPath + ", logType=" + logType + ", selfTest=" + selfTest
				+ ", sentResult=" + sentResult + ", suitePath=" + suitePath + "]";
	}
	
}
