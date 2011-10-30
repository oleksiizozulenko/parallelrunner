/**
 * 
 */
package framework.bullet;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author oleksii.zozulenko
 */
public interface IRunnerFactorySuiteReader
{
	@SuppressWarnings("unchecked")
	public Map<String, ArrayList> readSuite(String suiteFilePath) throws Exception;
}
