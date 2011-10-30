/**
 * 
 */
package framework.bullet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author oleksii.zozulenko
 */
public class ProjectConfigReader
{
	public static final String DEFAULT_PROPERTIES_FOLDER = "props";
	
	public static boolean isCustomProperties = false;
	
	public static String customPropertiesFile = "";
	
	public ProjectConfigReader()
	{
	}
	
	public Properties readPropertiesFile(File propFile)
	{
		Properties oPropertiesContext = new Properties();
		
		FileInputStream propFileContent;
		
		if (isPropertyFileExists(propFile))
		{
			try
			{
				propFileContent = new FileInputStream(propFile);
				oPropertiesContext.load(propFileContent);
				// System.out.println("MSG: Property file readed");
				
			}
			catch (FileNotFoundException oFISEx)
			{
				oFISEx.printStackTrace();
			}
			catch (IOException oIOEx)
			{
				oIOEx.printStackTrace();
			}
		}
		else
		{
			return null;
		}
		
		return oPropertiesContext;
		
	}
	
	public Properties readPropertiesFile(InputStream propFileContent)
	{
		Properties oPropertiesContext = new Properties();
		
		try
		{
			oPropertiesContext.load(propFileContent);
		}
		catch (IOException oIOEx)
		{
			oIOEx.printStackTrace();
		}
		
		return oPropertiesContext;
		
	}
	
	public InputStream getFileFromJar(String filePath) throws IOException
	{
		return getClass().getResourceAsStream(filePath);
	}
	
	public boolean isPropertyFileExists(File propFile)
	{
		if (propFile.exists())
		{
			return true;
		}
		return false;
	}
}
