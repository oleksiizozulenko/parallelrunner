/**
 *
 */
package org.suites;

/**
 * @author oleksii.zozulenko
 */
public abstract class AbstractFunctionalTestCase
{
	protected FuncTestStatus testStatus;
	protected Integer testExecId;
	
	/**
	 *
	 */
	public AbstractFunctionalTestCase()
	{
	}
	
	public FuncTestStatus getTestStatus()
	{
		return testStatus;
	}
	
	public void setTestStatus(FuncTestStatus testStatus)
	{
		this.testStatus = testStatus;
	}
	
	/**
	 * Runs the test case and collects the results in TestResult.
	 * 
	 * @throws Throwable
	 */
	public FuncTestResult run()
	{
		try
		{
			runBare();
			
			if (this.testStatus == null)
			{
				this.testStatus = FuncTestStatus.passed;
			}
		}
		catch (Throwable e)
		{
			Throwable cause = e.getCause();
			
			e.printStackTrace(System.err);
			
			if (cause != null)
			{
				cause.printStackTrace(System.err);
			}
			this.setTestStatus(FuncTestStatus.repead);
		}
		return getTestResult();
	}
	
	private FuncTestResult getTestResult()
	{
		return new FuncTestResult(this.testExecId, this.testStatus);
	}
	
	/**
	 * Runs the bare test sequence.
	 * 
	 * @throws Throwable
	 *             if any exception is thrown
	 */
	
	public void runBare() throws Throwable
	{
		Throwable exception = null;
		
		try
		{
			setUp();
			runTest();
		}
		catch (Throwable running)
		{
			exception = running;
		}
		finally
		{
			try
			{
				tearDown();
			}
			catch (Throwable tearingDown)
			{
				if (exception == null)
					exception = tearingDown;
			}
		}
		if (exception != null)
			throw exception;
	}
	
	public abstract void tearDown() throws Throwable;
	
	public abstract void runTest() throws Throwable;
	
	public abstract void setUp() throws Throwable;
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
