/**
 *
 */
package gp.runner.api.configure;

import java.util.List;

import project.utils.Connection;

/**
 * @author oleksii.zozulenko
 */
public abstract class AbstractConfigReader implements IConfigReader
{
	protected String _path;
	
	protected AbstractConfigReader(String path)
	{
		this._path = path;
	}
	
	public Connection read() throws Exception
	{
		return null;
	}
	
	public List<Connection> readList() throws Exception
	{
		return null;
	}
}
