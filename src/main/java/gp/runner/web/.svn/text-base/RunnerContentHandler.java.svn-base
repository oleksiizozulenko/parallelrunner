/**
 *
 */
package gp.runner.web;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import javax.servlet.http.HttpServletResponse;

import org.openqa.jetty.http.HttpContext;
import org.openqa.jetty.http.HttpException;
import org.openqa.jetty.http.HttpHandler;
import org.openqa.jetty.http.HttpRequest;
import org.openqa.jetty.http.HttpResponse;

/**
 * @author oleksiizozulenko
 */
@SuppressWarnings("serial")
public class RunnerContentHandler implements HttpHandler
{
	
	HttpContext context;
	
	boolean started;
	
	public RunnerContentHandler()
	{
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mortbay.http.HttpHandler#getHttpContext()
	 */
	public HttpContext getHttpContext()
	{
		return context;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mortbay.http.HttpHandler#getName()
	 */
	public String getName()
	{
		return this.getClass().getName();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mortbay.http.HttpHandler#handle(java.lang.String, java.lang.String, org.mortbay.http.HttpRequest, org.mortbay.http.HttpResponse)
	 */
	public void handle(String pathInContext, String pathParams, HttpRequest request,
			HttpResponse response)
			throws HttpException, IOException
	{
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		OutputStreamWriter writter = new OutputStreamWriter(response.getOutputStream());
		
		writter.write("<html>" +
				"<head><meta http-equiv=\"refresh\" content=\"5\"></head>" +
				"<body>");
		
		// Get runtime information
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		
		// Handle thread info
		ThreadMXBean threads = ManagementFactory.getThreadMXBean();
		
		ThreadInfo[] threadInfos = threads.getThreadInfo(threads.getAllThreadIds());
		writter.write("<br/><h1>All Threads:</h1><br/>");
		writter.write("<table align='center'>");
		for (ThreadInfo th : threadInfos)
		{
			String name = th.getThreadName();
			
			if (name.contains("conf") || name.contains("test"))
			{
				writter.write("<tr><td><b>" + name + "</b><td></tr>");
			}
		}
		writter.write("</table>");
		
		writter.write("</body></html>");
		writter.flush();
		writter.close();
		
		request.setHandled(true);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mortbay.http.HttpHandler#initialize(org.mortbay.http.HttpContext)
	 */

	public void initialize(HttpContext arg0)
	{
		this.context = arg0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mortbay.util.LifeCycle#isStarted()
	 */

	public boolean isStarted()
	{
		return started;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mortbay.util.LifeCycle#start()
	 */

	public void start() throws Exception
	{
		started = true;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mortbay.util.LifeCycle#stop()
	 */

	public void stop() throws InterruptedException
	{
		started = false;
		
	}
	
}
