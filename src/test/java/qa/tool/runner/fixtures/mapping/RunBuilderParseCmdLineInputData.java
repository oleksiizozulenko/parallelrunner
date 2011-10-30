/*
 * ---------------------------------------------------------------
 * File: RunBuilderParseCmdLineInputData.java
 * Project: Runner
 * Created: Sep 15, 2010 11:34:58 AM
 * Author: oleksii.zozulenko
 * ---------------------------------------------------------------
 */
package qa.tool.runner.fixtures.mapping;

import gp.runner.api.RunBuilderConfig;

import java.util.ArrayList;

/**
 * @author oleksii.zozulenko
 */
public class RunBuilderParseCmdLineInputData
{
	private ArrayList<String> cmdline;
	
	private RunBuilderConfig  expected;
	
	/**
	 * 
	 */
	public RunBuilderParseCmdLineInputData()
	{
	}
	
	public ArrayList<String> getCmdline()
	{
		return cmdline;
	}
	
	public String[] getArrCmdLine()
	{
		return cmdline.toArray(new String[this.cmdline.size()]);
	}
	
	public void setCmdline(ArrayList<String> cmdline)
	{
		this.cmdline = cmdline;
	}
	
	public RunBuilderConfig getExpected()
	{
		return expected;
	}
	
	public void setExpected(RunBuilderConfig expected)
	{
		this.expected = expected;
	}
	
	@Override
	public String toString()
	{
		return "RunBuilderParseCmdLineInputData [cmdline=" + cmdline + ", expected=" + expected
				+ "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmdline == null) ? 0 : cmdline.hashCode());
		result = prime * result + ((expected == null) ? 0 : expected.hashCode());
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
		RunBuilderParseCmdLineInputData other = (RunBuilderParseCmdLineInputData) obj;
		if (cmdline == null)
		{
			if (other.cmdline != null)
				return false;
		}
		else if (!cmdline.equals(other.cmdline))
			return false;
		if (expected == null)
		{
			if (other.expected != null)
				return false;
		}
		else if (!expected.equals(other.expected))
			return false;
		return true;
	}
	
}
