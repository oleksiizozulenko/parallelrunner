/**
 * 
 */
package gp.wrapers.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ho.yaml.Yaml;

/**
 * @author oleksiizozulenko
 */
public class YamlDataReader
{
	String dataRoot;
	
	public void setDataRoot(String root)
	{
		this.dataRoot = root;
	}
	
	private String getDataRoot()
	{
		if (dataRoot == null)
		{
			return "fixtures/";
		}
		else return dataRoot;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> readListFixture(String file, Class<T> clazz) throws FileNotFoundException,
			Exception
	{
		String fixturesRoot = getDataRoot();
		String fixtureFile = file;
		
		File f = null;
		List<T> out = null;
		
		try
		{
			
			URL uri = Thread.currentThread().getContextClassLoader().getResource(
					fixturesRoot + fixtureFile);
			
			if (uri == null)
			{
				f = findFixtureInFS(file);
			}
			else if (uri.getPath().contains(".jar"))
			{
				f = findFixtureInFS(file);
			}
			else
			{
				f = new File(uri.getPath());
			}
			
			if (f.exists())
			{
				out = Yaml.loadType(f, ArrayList.class);
			}
			else
			{
				return readListFixtureAsStream(fixtureFile, clazz);
			}
		}
		catch (FileNotFoundException e)
		{
			out = readListFixtureAsStream(fixtureFile, clazz);
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap readFixture(String file) throws FileNotFoundException, Exception
	{
		HashMap out = null;
		
		String fixturesRoot = getDataRoot();
		String fixtureFile = file;
		
		File f = null;
		
		try
		{
			
			URL uri = Thread.currentThread().getContextClassLoader().getResource(
					fixturesRoot + fixtureFile);
			
			if (uri == null)
			{
				f = findFixtureInFS(file);
			}
			else if (uri.getPath().contains(".jar"))
			{
				f = findFixtureInFS(file);
			}
			else
			{
				f = new File(uri.getPath());
			}
			
			if (f.exists())
			{
				out = Yaml.loadType(f, HashMap.class);
			}
			else
			{
				return readFixtureAsStream(fixtureFile);
			}
		}
		catch (FileNotFoundException e)
		{
			out = readFixtureAsStream(fixtureFile);
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	private HashMap readFixtureAsStream(String fixtureFile) throws Exception
	{
		HashMap out = null;
		try
		{
			InputStream ios = Thread.currentThread().getClass().getResourceAsStream(fixtureFile);
			
			if (ios == null)
			{
				ios = this.getClass().getResourceAsStream(fixtureFile);
			}
			
			if (ios == null)
			{
				throw new FileNotFoundException();
			}
			out = Yaml.loadType(ios, HashMap.class);
			
		}
		catch (Exception oE)
		{
			
			oE.printStackTrace();
			
			throw oE;
		}
		return out;
	}
	
	/**
	 * @param <T>
	 * @param fixturesRoot
	 * @param fixtureFile
	 * @param out
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> readListFixtureAsStream(String fixtureFile, Class<T> clazz)
			throws Exception
	{
		List<T> out = null;
		try
		{
			InputStream ios = Thread.currentThread().getClass().getResourceAsStream(fixtureFile);
			
			if (ios == null)
			{
				ios = this.getClass().getResourceAsStream(fixtureFile);
			}
			
			if (ios == null)
			{
				throw new FileNotFoundException();
			}
			out = Yaml.loadType(ios, ArrayList.class);
			
		}
		catch (Exception oE)
		{
			
			oE.printStackTrace();
			
			throw oE;
		}
		return out;
	}
	
	private File findFixtureInFS(String file) throws Exception
	{
		System.out.println(file);
		
		String[] extensions =
		{ "yaml", "yml" };
		
		return FilesFinder.find(this.dataRoot, file, extensions);
		
	}
}
