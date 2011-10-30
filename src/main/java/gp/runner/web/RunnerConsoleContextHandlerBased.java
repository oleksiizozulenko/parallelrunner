/**
 *
 */
package gp.runner.web;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;

import org.openqa.jetty.http.HttpContext;
import org.openqa.jetty.http.NCSARequestLog;
import org.openqa.jetty.http.SocketListener;
import org.openqa.jetty.jetty.Server;

/**
 * @author oleksii.zozulenko
 */
public class RunnerConsoleContextHandlerBased extends AbstractRunnerConsole implements
		RunnerConsole
{
	protected Server server;
	java.util.logging.Logger logger = null;
	
	public RunnerConsoleContextHandlerBased()
	{
		logger = java.util.logging.Logger.getLogger(this.getClass().getName());
		logger.setLevel(Level.SEVERE);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gp.runner.web.RunnerConsole#createThreadManager(int)
	 */
	@Override
	public void createThreadManager(int port) throws UnknownHostException, Exception
	{
		try
		{
			server = new Server();
			
			// Log jettylog = LogFactory.getLog(this.getClass());
			
			NCSARequestLog serverlog = new NCSARequestLog("./runner-request.log");
			serverlog.setRetainDays(90);
			serverlog.setAppend(true);
			serverlog.setExtended(false);
			serverlog.setLogTimeZone("GMT");
			
			serverlog.start();
			server.setRequestLog(serverlog);
			SocketListener listener = new SocketListener();
			listener.setPort(port);
			server.addListener(listener);
			
			HttpContext context = new HttpContext();
			InetAddress selfaddress = java.net.InetAddress.getLocalHost();
			
			String local_ip = selfaddress.getHostAddress();
			String hostname = selfaddress.getHostName();
			String canhostname = selfaddress.getCanonicalHostName();
			String[] hosts = new String[]
			{ "localhost", local_ip, hostname, canhostname };
			String[] virthosts = new String[]
			{ "localhost", local_ip, hostname, canhostname };
			
			context.setHosts(hosts);
			
			context.setContextPath("/runner");
			context.setVirtualHosts(virthosts);
			context.addHandler(new RunnerContentHandler());
			server.addContext(context);
			
			server.start();
			logger.info("Threads monitoring started on port " + port + " Hosts are: "
					+ server.getHostMap());
		}
		catch (Exception oBExc)
		{
			this.createThreadManager(port + 1);
		}
		
	}
	
	@Override
	public void stopThreadManager()
	{
		try
		{
			server.stop();
		}
		catch (InterruptedException e)
		{
			logger.log(Level.SEVERE, "Threads monnitor has already stopped. " + e.getMessage());
		}
		
	}
	
}
