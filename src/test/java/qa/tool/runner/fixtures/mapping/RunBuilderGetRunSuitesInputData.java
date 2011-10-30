package qa.tool.runner.fixtures.mapping;

import java.util.List;

public class RunBuilderGetRunSuitesInputData
{
	List<String> filesuite;
	
	List<String> ignore;
	
	List<String> include;
	
	List<String> expected;
	
	public RunBuilderGetRunSuitesInputData()
	{
	}
	
	public List<String> getFilesuite()
	{
		return filesuite;
	}
	
	public void setFilesuite(List<String> filesuite)
	{
		this.filesuite = filesuite;
	}
	
	public List<String> getIgnore()
	{
		return ignore;
	}
	
	public void setIgnore(List<String> ignore)
	{
		this.ignore = ignore;
	}
	
	public List<String> getInclude()
	{
		return include;
	}
	
	public void setInclude(List<String> include)
	{
		this.include = include;
	}
	
	public List<String> getExpected()
	{
		return expected;
	}
	
	public void setExpected(List<String> expected)
	{
		this.expected = expected;
	}
	
	@Override
	public String toString()
	{
		return "RunBuilderGetRunSuitesInputData [expected=" + expected + ",\n filesuite="
				+ filesuite
				+ ",\n ignore=" + ignore + ",\n include=" + include + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expected == null) ? 0 : expected.hashCode());
		result = prime * result + ((filesuite == null) ? 0 : filesuite.hashCode());
		result = prime * result + ((ignore == null) ? 0 : ignore.hashCode());
		result = prime * result + ((include == null) ? 0 : include.hashCode());
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
		RunBuilderGetRunSuitesInputData other = (RunBuilderGetRunSuitesInputData) obj;
		if (expected == null)
		{
			if (other.expected != null)
				return false;
		}
		else if (!expected.equals(other.expected))
			return false;
		if (filesuite == null)
		{
			if (other.filesuite != null)
				return false;
		}
		else if (!filesuite.equals(other.filesuite))
			return false;
		if (ignore == null)
		{
			if (other.ignore != null)
				return false;
		}
		else if (!ignore.equals(other.ignore))
			return false;
		if (include == null)
		{
			if (other.include != null)
				return false;
		}
		else if (!include.equals(other.include))
			return false;
		return true;
	}
	
}
