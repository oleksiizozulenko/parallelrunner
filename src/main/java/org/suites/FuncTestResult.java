/**
 *
 */
package org.suites;

/**
 * @author oleksii.zozulenko
 */
public class FuncTestResult
{
	protected Integer testExecId;
	protected FuncTestStatus status;
	
	public FuncTestResult(Integer testId, FuncTestStatus testStatus)
	{
		this.testExecId = testId;
		this.status = testStatus;
	}
	
	public Integer getTestExecId()
	{
		return testExecId;
	}
	
	public void setTestExecId(int testExecId)
	{
		this.testExecId = testExecId;
	}
	
	public FuncTestStatus getStatus()
	{
		return status;
	}
	
	public void setStatus(FuncTestStatus status)
	{
		this.status = status;
	}
	
}
