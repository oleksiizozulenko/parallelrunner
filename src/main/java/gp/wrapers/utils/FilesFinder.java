/*
 * ---------------------------------------------------------------
 * File: FilesFinder.java
 * Project: Runner
 * Created: Oct 27, 2010 11:00:13 AM
 * Author: oleksii.zozulenko
 * ---------------------------------------------------------------
 */
package gp.wrapers.utils;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

/**
 * @author oleksii.zozulenko
 */
public class FilesFinder
{
	@SuppressWarnings("unchecked")
	public static File find(String rootPath, String file, String[] extentions) throws Exception
	{
		File f = null;
		String internalRootPath = new File("").getAbsolutePath() + "/" + rootPath;
		System.out.println("internal root path " + internalRootPath);
		
		File root = new File(internalRootPath);
		try
		{
			
			boolean recursive = true;
			
			//
			// Finds files within a root directory and optionally its
			// subdirectories which match an array of extensions. When the
			// extensions is null all files will be returned.
			//
			// This method will returns matched file as java.io.File
			//
			Collection files = FileUtils.listFiles(root, extentions, recursive);
			
			for (Iterator iterator = files.iterator(); iterator.hasNext();)
			{
				File cfile = (File) iterator.next();
				System.out.println(cfile.getAbsolutePath());
				
				boolean eq = cfile.getCanonicalPath().contains(file);
				
				if (eq)
				{
					f = cfile;
					break;
				}
				
				if (!iterator.hasNext())
				{
					throw new IllegalArgumentException("File '" + file + "' didn't find");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return f;
	}
}
