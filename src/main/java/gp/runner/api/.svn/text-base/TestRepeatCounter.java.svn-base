package gp.runner.api;

public class TestRepeatCounter
{
	private String configName;
	private String testName;
	private int count;
	
	public TestRepeatCounter(String configName, String testName, int count)
	{
		super();
		this.configName = configName;
		this.testName = testName;
		this.count = count;
	}
	
	public void updateCount()
	{
		count++;
	}
	
	public String getConfigName()
	{
		return configName;
	}
	
	public void setConfigName(String configName)
	{
		this.configName = configName;
	}
	
	public String getTestName()
	{
		return testName;
	}
	
	public void setTestName(String testName)
	{
		this.testName = testName;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public void setCount(int count)
	{
		this.count = count;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((configName == null) ? 0 : configName.hashCode());
		result = prime * result + count;
		result = prime * result + ((testName == null) ? 0 : testName.hashCode());
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
		TestRepeatCounter other = (TestRepeatCounter) obj;
		if (configName == null)
		{
			if (other.configName != null)
				return false;
		}
		else if (!configName.equals(other.configName))
			return false;
		if (count != other.count)
			return false;
		if (testName == null)
		{
			if (other.testName != null)
				return false;
		}
		else if (!testName.equals(other.testName))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "TestReapeatCounter [configName=" + configName + ", count=" + count + ", testName="
				+ testName + "]";
	}
}
