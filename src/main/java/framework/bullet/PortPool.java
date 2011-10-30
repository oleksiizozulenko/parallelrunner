/**
 * 
 */
package framework.bullet;

import java.util.Vector;
import java.util.logging.Logger;

/**
 * @author oleksii
 */
public class PortPool extends Pool
{
	// Vector contains all ports available for client-server
	Vector<Integer> availablePorts;
	
	// Vector contains all ports used under client
	Vector<Integer> busyPorts;
	
	// Vector contains all ports listen by server
	Vector<Integer> listenPorts;
	
	Logger		  log;
	
	public PortPool()
	{
		log = Logger.getLogger("PortPool log system");
		
		this.availablePorts = new Vector<Integer>();
		this.busyPorts = new Vector<Integer>();
		this.listenPorts = new Vector<Integer>();
	}
	
	/**
	 * @function getAllPorts
	 * 
	 *           <pre>
	 * &lt;b&gt;Description:&lt;/b&gt;&lt;br /&gt;
	 * Return all port in pool 
	 * TODO: In a future must return all ports with status
	 * </pre>
	 * @author oleksii.zozulenko
	 * @return
	 */
	public Vector<Integer> getAllPorts()
	{
		Vector<Integer> liAllPorts = new Vector<Integer>();
		liAllPorts.addAll(this.availablePorts);
		liAllPorts.addAll(this.listenPorts);
		liAllPorts.addAll(this.busyPorts);
		
		return liAllPorts;
	}
	
	/**
	 * @function getAvailablePorts
	 * 
	 *           <pre>
	 * &lt;b&gt;Description:&lt;/b&gt;&lt;br /&gt;
	 * Function return list of all available products. If list is empty or not present return null
	 * </pre>
	 * @author oleksii.zozulenko
	 * @return
	 */
	public Integer[] getAvailablePorts()
	{
		if (isEmpty(this.availablePorts))
		{
			int len = this.availablePorts.size();
			return this.availablePorts.toArray(new Integer[len]);
		}
		else
		{
			return new Integer[] {};
		}
	}
	
	/**
	 * @function getFirstAvailablePort
	 * 
	 *           <pre>
	 * &lt;b&gt;Description:&lt;/b&gt;&lt;br /&gt;
	 * Function return first available port. If any ports available return 0;
	 * </pre>
	 * @author oleksii.zozulenko
	 * @return
	 */
	public int getFirstAvailablePort()
	{
		int port = 0;
		
		if (!isEmpty(this.availablePorts))
		{
			port = this.availablePorts.get(0);
		}
		
		return port;
	}
	
	/**
	 * <pre>
	 * &lt;b&gt;Description:&lt;/b&gt;
	 * Function return first port from pool with status `listen`
	 * </pre>
	 * 
	 * @return int - first port from listened port pool or 0 - if listen pool empty
	 */
	public int getFirstListenPort()
	{
		int port = 0;
		
		if (!isEmpty(this.listenPorts))
		{
			port = this.listenPorts.get(0);
		}
		return port;
	}
	
	/**
	 * <pre>
	 * &lt;b&gt;Description&lt;/b&gt;
	 * Function return all listened ports
	 * </pre>
	 * 
	 * @return Integer []
	 */
	public Integer[] getListenPorts()
	{
		if (!this.isEmpty(this.listenPorts))
		{
			return this.listenPorts.toArray(new Integer[this.listenPorts.size()]);
		}
		else return new Integer[] {};
	}
	
	/**
	 * @param portNum
	 * @return
	 */
	@Override
	public boolean isAvailable(int portNum)
	{
		boolean empty = this.isEmpty(this.availablePorts);
		boolean busy = this.busyPorts.contains(portNum);
		boolean available = this.availablePorts.contains(portNum);
		boolean listen = this.listenPorts.contains(portNum);
		
		return (!(busy && listen && !empty) && available);
	}
	
	/**
	 * @function isBusy
	 * 
	 *           <pre>
	 * &lt;b&gt;Description:&lt;/b&gt;&lt;br /&gt;
	 * Detect is port not busy or reserved  by client
	 * </pre>
	 * @author oleksii.zozulenko
	 * @param portNum
	 * @return
	 */
	public boolean isBusy(int portNum)
	{
		boolean empty = this.isEmpty(this.busyPorts);
		boolean busy = this.busyPorts.contains(portNum);
		boolean available = this.availablePorts.contains(portNum);
		boolean listen = this.listenPorts.contains(portNum);
		
		return (!(available && listen && !empty) && busy);
	}
	
	/**
	 * Function verify if port has status listen
	 * 
	 * @param portNum
	 * @return
	 */
	public boolean isListen(int portNum)
	{
		boolean empty = this.isEmpty(this.listenPorts);
		boolean busy = this.busyPorts.contains(portNum);
		boolean available = this.availablePorts.contains(portNum);
		boolean listen = this.listenPorts.contains(portNum);
		
		return (!(busy && available && !empty) && listen);
	}
	
	/**
	 * @function setAllPortsBusy
	 * 
	 *           <pre>
	 * &lt;b&gt;Description:&lt;/b&gt;&lt;br /&gt;
	 * All available ports sets as busy
	 * </pre>
	 * @author oleksii.zozulenko
	 */
	public void setAllBusy()
	{
		Vector<Integer> liPorts = new Vector<Integer>();
		
		liPorts.addAll(this.availablePorts);
		liPorts.addAll(this.listenPorts);
		
		for (Integer iPort : liPorts)
		{
			if (!this.busyPorts.contains(iPort))
			{
				this.busyPorts.add(iPort);
			}
		}
		
		this.availablePorts.removeAllElements();
		this.listenPorts.removeAllElements();
	}
	
	/**
	 * @function setAllPortsFree
	 * 
	 *           <pre>
	 * &lt;b&gt;Description:&lt;/b&gt;&lt;br /&gt;
	 * All busy  ports sets as free and available to use
	 * </pre>
	 * @author oleksii.zozulenko
	 */
	public void setAllFree()
	{
		Vector<Integer> liPorts = new Vector<Integer>();
		
		liPorts.addAll(this.busyPorts);
		liPorts.addAll(this.listenPorts);
		
		for (Integer iPort : liPorts)
		{
			if (!this.availablePorts.contains(iPort))
			{
				this.availablePorts.add(iPort);
			}
		}
		
		this.busyPorts.removeAllElements();
		this.listenPorts.removeAllElements();
	}
	
	public void setAllListen()
	{
		Vector<Integer> liPorts = new Vector<Integer>();
		
		liPorts.addAll(this.availablePorts);
		
		for (Integer iPort : liPorts)
		{
			if (!this.listenPorts.contains(iPort))
			{
				this.listenPorts.add(iPort);
			}
		}
		
		this.availablePorts.removeAllElements();
	}
	
	public void setBusy(int portNum)
	{
		boolean listen = this.isListen(portNum);
		boolean busy = this.isBusy(portNum);
		boolean available = this.isAvailable(portNum);
		
		if (listen && !available && !busy)
		{
			this.listenPorts.remove(this.listenPorts.indexOf(portNum));
		}
		if (!listen && available && !busy)
		{
			log.warning("Port " + portNum + " is free now and not listened by server.");
			return;
		}
		if (busy)
		{
			log.warning("SKIP: Port " + portNum + " is busy now.");
			return;
		}
		if (!listen)
		{
			log.warning("SKIP: Port " + portNum + " is not listening now.");
			return;
		}
		
		this.busyPorts.add(portNum);
		
	}
	
	public void setFree(int portNum)
	{
		// TODO: I must view that available ports do not contains port from busy array
		boolean listen = this.isListen(portNum);
		boolean busy = this.isBusy(portNum);
		boolean available = this.isAvailable(portNum);
		
		if (!listen && busy)
		{
			log.warning("Port " + portNum + " is busy now and can't be set free");
			return;
		}
		if (listen && !busy)
		{
			this.listenPorts.remove(this.listenPorts.indexOf(portNum));
		}
		if (available)
		{
			log.warning("SKIP: Port " + portNum + " is free now.");
			return;
		}
		
		this.availablePorts.add(portNum);
		
	}
	
	public void setListen(int port)
	{
		boolean available = this.isAvailable(port);
		boolean busy = this.isBusy(port);
		boolean listen = this.isListen(port);
		
		if (available && !busy && !listen)
		{
			this.availablePorts.remove(this.availablePorts.indexOf(port));
		}
		if (busy && !available && !listen)
		{
			this.busyPorts.remove(this.busyPorts.indexOf(port));
		}
		if (listen && !available && !busy)
		{
			log.warning("SKIP: Port " + port + " is listening now.");
			return;
		}
		if (listen && available && !busy)
		{
			this.availablePorts.remove(this.availablePorts.indexOf(port));
			log.warning("SKIP: Port " + port + " is listening now.");
			return;
		}
		if (!available)
		{
			log.warning("Port " + port + " is not free now and cant be set to listen");
			return;
		}
		this.listenPorts.add(port);
		
	}
}
