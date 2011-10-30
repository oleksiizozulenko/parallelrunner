/**
 * 
 */
package framework.bullet;

import java.util.Vector;

/**
 * @author oleksii.zozulenko
 *
 */
public abstract class Pool
{
	public abstract boolean isAvailable(int portNum);
	
	public abstract boolean isBusy(int portNum);
	
	public abstract boolean isListen(int portNum);
	
	public boolean isEmpty(Vector<Integer> checkingVector)
	{
		try
		{
			int iSize = checkingVector.size();
			if (iSize == 0)
			{
				return true;
			}
			else return false;
		}
		catch (java.lang.NullPointerException oNPEx)
		{
			return true;
		}
	}
}
