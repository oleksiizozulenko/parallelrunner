/*
 * ---------------------------------------------------------------
 * File: IConfigReader.java
 * Project: Runner
 * Created: Sep 7, 2010 3:43:16 PM
 * Author: oleksii.zozulenko
 * ---------------------------------------------------------------
 */
package gp.runner.api.configure;

import java.util.List;

import project.utils.Connection;

/**
 * @author oleksii.zozulenko
 */
public interface IConfigReader
{
	/**
	 * @return single configuration where tests will run
	 * @throws Exception
	 */
	Connection read() throws Exception;
	
	/**
	 * @return list of configuration which tests will run
	 * @throws Exception
	 */
	public List<Connection> readList() throws Exception;
}
