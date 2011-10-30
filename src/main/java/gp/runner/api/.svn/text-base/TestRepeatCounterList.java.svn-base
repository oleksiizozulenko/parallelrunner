/**
 * 
 */
package gp.runner.api;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author oleksii.zozulenko
 */
@SuppressWarnings("serial")
public class TestRepeatCounterList extends ConcurrentLinkedQueue<TestRepeatCounter>
{
	
	public TestRepeatCounter getTestRepeatCounter(String conf, String test)
	{
		if (conf != null && test != null)
		{
			Iterator<TestRepeatCounter> iter = this.iterator();
			
			while (iter.hasNext())
			{
				TestRepeatCounter confStat = iter.next();
				if (confStat != null)
				{
					String currConfName = confStat.getConfigName();
					String testName = confStat.getTestName();
					if (testName != null && currConfName != null)
					{
						boolean isConfSame = currConfName.equalsIgnoreCase(conf);
						boolean isTestSame = testName.equalsIgnoreCase(test);
						
						if (isTestSame && isConfSame)
						{
							return confStat;
						}
					}
				}
			}
		}
		
		return null;
		
	}
	
	public int getTestsQueueSize(String conf)
	{
		int queueSize = 0;
		if (conf != null)
		{
			Iterator<TestRepeatCounter> iter = this.iterator();
			
			while (iter.hasNext())
			{
				TestRepeatCounter confStat = iter.next();
				if (confStat != null)
				{
					String currConfName = confStat.getConfigName();
					if (currConfName != null)
					{
						boolean isConfSame = currConfName.equalsIgnoreCase(conf);
						if (isConfSame)
						{
							queueSize++;
						}
					}
				}
			}
		}
		return queueSize;
	}
	
}
