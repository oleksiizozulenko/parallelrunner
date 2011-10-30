/*
 * ---------------------------------------------------------------
 * File: YamlConfigReader.java
 * Project: Runner
 * Created: Sep 7, 2010 3:42:43 PM
 * Author: oleksii.zozulenko
 * ---------------------------------------------------------------
 */
package gp.runner.api.configure;

import gp.wrapers.utils.YamlDataReader;

import java.util.List;

import project.utils.Connection;

/**
 * @author oleksii.zozulenko
 */
public class YamlConfigReader extends AbstractConfigReader implements IConfigReader
{
	YamlDataReader reader;
	
	/**
	 *
	 */
	public YamlConfigReader(String confPath)
	{
		super(confPath);
		
		reader = new YamlDataReader();
		reader.setDataRoot("");
	}
	
	@Override
	public Connection read() throws Exception
	{
		List<Connection> data = reader.readListFixture(_path, Connection.class);
		return data.get(0);
	}
	
	@Override
	public List<Connection> readList() throws Exception
	{
		List<Connection> data = reader.readListFixture(_path, Connection.class);
		return data;
	}
	
}
