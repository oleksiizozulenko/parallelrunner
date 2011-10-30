/**
 *
 */
package framework.bullet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import oz.runner.annotations.SuiteClass;

import gp.testframework.api.Suite;
import gp.wrapers.utils.YamlDataReader;

/**
 * @author oleksii.zozulenko
 */
@SuppressWarnings("unchecked")
public class RunnerFactorySuiteReaderWithPackagesImpl extends AbstractRunnerFactorySuiteReader
		implements
		IRunnerFactorySuiteReader
{
	public RunnerFactorySuiteReaderWithPackagesImpl()
	{
		super();
	}
	
	@Override
	public Map<String, ArrayList> readSuite(String suiteFilePath) throws Exception
	{
		Map<String, ArrayList> loSuitesMap = new Hashtable<String, ArrayList>();
		
		String intSuitePath = (suiteFilePath.startsWith("./")) ? suiteFilePath.replace("./", "")
				: suiteFilePath;
		
		YamlDataReader yamlreader = new YamlDataReader();
		yamlreader.setDataRoot("");
		List<Suite> configsFileNames = yamlreader
				.readListFixture(intSuitePath, Suite.class);
		
		for (Suite suite : configsFileNames)
		{
			if (suite.getEnabled().booleanValue())
			{
				List<String> pkgs = suite.getPackages();
				
				List<String> testsInPackages = getTestsInPackages(pkgs);
				
				ArrayList<String> tests = new ArrayList<String>();
				List suiteTests = suite.getTests();
				if (suiteTests != null)
				{
					tests.addAll(suiteTests);
				}
				
				if (testsInPackages != null)
				{
					tests.addAll(testsInPackages);
				}
				
				loSuitesMap.put(suite.getName(), tests);
			}
		}
		
		return loSuitesMap;
	}
	
	private List<String> getTestsInPackages(List<String> pkgs) throws ClassNotFoundException,
			IOException
	{
		
		if (pkgs == null)
			return null;
		
		List<String> packageTests = new ArrayList<String>();
		
		for (String pkg : pkgs)
		{
			
			ArrayList<Class> classesInPkg = getClasses(pkg);
			LOGGER.log(Level.INFO, "Classes in package: \n"
					+ classesInPkg.toString().replaceAll(",", "\n-"));
			
			CollectionUtils.filter(classesInPkg, new SuiteClassPredicateFilter());
			
			for (Class clazz : classesInPkg)
			{
				String className = clazz.getName();
				if (!packageTests.contains(className))
				{
					packageTests.add(className);
				}
			}
		}
		
		LOGGER.log(Level.INFO, "Tests in packages: \n"
				+ packageTests.toString().replaceAll(",", "\n-"));
		return packageTests;
	}
	
	class SuiteClassPredicateFilter implements Predicate
	{
		
		@Override
		public boolean evaluate(Object arg0)
		{
			Class clazz = (Class) arg0;
			
			SuiteClass anot = (SuiteClass) clazz.getAnnotation(SuiteClass.class);
			
			if (anot != null)
			{
				if (anot.enabled())
				{
					return true;
				}
				else return false;
			}
			/*
			 * else
			 * {
			 * if (clazz.getCanonicalName().startsWith("OBB_"))
			 * {
			 * return true;
			 * }
			 * }
			 */
			return false;
		}
	}
	
	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 * 
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static ArrayList<Class> getClasses(String packageName)
			throws ClassNotFoundException, IOException
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		ArrayList<Class> classes = new ArrayList<Class>();
		
		LOGGER.log(Level.INFO, "Finded resources: ");
		
		while (resources.hasMoreElements())
		{
			URL resUrl = resources.nextElement();
			File directory = new File(resUrl.getFile());
			LOGGER.log(Level.INFO, "\n- " + resUrl.toString());
			LOGGER.log(Level.INFO, "\nFile:" + directory);
			
			LOGGER.log(Level.INFO, "IsDirectory:" + directory.isDirectory());
			LOGGER.log(Level.INFO, "IsExists:" + directory.exists());
			
			String classpath = System.getProperty("java.class.path");
			
			LOGGER.log(Level.INFO, "ClassPath: " + classpath);
			
			String[] classpathEnries = classpath.split(":");
			
			for (String classPathEntry : classpathEnries)
			{
				if (classPathEntry.endsWith(".jar"))
				{
					File jarName = new File(classPathEntry);
					LOGGER.log(Level.INFO, jarName.getAbsolutePath());
					ZipFile zf = new ZipFile(jarName.getAbsoluteFile());
					
					Enumeration e = zf.entries();
					while (e.hasMoreElements())
					{
						ZipEntry ze = (ZipEntry) e.nextElement();
						
						String zeName = ze.getName();
						
						LOGGER.log(Level.INFO, "Current ZipEntry: " + zeName);
						String pkgInDirFormat = path;
						if (zeName.startsWith(pkgInDirFormat) && zeName.endsWith(".class")
								&& !zeName.contains("$"))
						{
							String classToAdd = zeName.replaceAll("/", "\\.").replace(".class", "");
							Class oClassToAdd = null;
							try
							{
								oClassToAdd = Class.forName(classToAdd);
								classes.add(oClassToAdd);
								if (classes.contains(oClassToAdd))
								{
									LOGGER.log(Level.INFO, "Class " + classToAdd + " was added");
								}
							}
							catch (java.lang.ClassNotFoundException oCNFEx)
							{
								oCNFEx.printStackTrace();
							}
						}
						
					}
					zf.close();
				}
				else
				{
					classes.addAll(findClasses(directory, packageName));
				}
			}
			/*
			 * if (directory.isDirectory())
			 * {
			 * }
			 * else if (directory.getName().split("!")[0].endsWith(".jar"))
			 * {
			 * try
			 * {
			 * JarInputStream jarFile = new JarInputStream
			 * (new FileInputStream(directory));
			 * JarEntry jarEntry;
			 * while (true)
			 * {
			 * jarEntry = jarFile.getNextJarEntry();
			 * if (jarEntry == null)
			 * {
			 * break;
			 * }
			 * if ((jarEntry.getName().startsWith(packageName)) &&
			 * (jarEntry.getName().endsWith(".class")))
			 * {
			 * classes.add(Class.forName(jarEntry.getName().replaceAll("/", "\\.")));
			 * }
			 * }
			 * }
			 * catch (Exception e)
			 * {
			 * e.printStackTrace(System.err);
			 * }
			 * }
			 */

		}
		
		return classes;
	}
	
	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 * 
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class> findClasses(File directory, String packageName)
			throws ClassNotFoundException
	{
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists())
		{
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			}
			else if (file.getName().endsWith(".class"))
			{
				classes.add(Class.forName(packageName + '.'
						+ file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}
	
}
