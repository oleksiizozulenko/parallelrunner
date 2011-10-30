package project.ui;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import com.thoughtworks.selenium.Selenium;

/**
 * <pre>
 * &lt;b&gt;Description:&lt;/b&gt;
 * Class contains static functions for performing routines
 * with UI of tested application
 * &lt;b&gt;Creation date: &lt;/b&gt;05.02.08
 * &lt;b&gt;Modification date: &lt;/b&gt;28.11.08
 * </pre>
 * 
 * @author Oleksii Zozulenko
 */
public class Utils
{
	
	/**
	 * <pre>
	 * Method: Activ
	 * &lt;b&gt;Description: &lt;/b&gt;
	 * 		Set active selenium window
	 * &lt;b&gt;Creation date&lt;/b&gt; 02.06.2008
	 * &lt;b&gt;Modification date&lt;/b&gt; 12.09.2008
	 * </pre>
	 * 
	 * @author Oleksii Zozulenko
	 * @exception Exception
	 */
	static public void setActiv() throws Exception
	{
		java.awt.Robot robo = null;
		try
		{
			robo = new java.awt.Robot();
			robo.keyPress(java.awt.event.KeyEvent.VK_ALT);
			robo.keyPress(java.awt.event.KeyEvent.VK_TAB);
			robo.keyRelease(java.awt.event.KeyEvent.VK_TAB);
			robo.keyRelease(java.awt.event.KeyEvent.VK_ALT);
		}
		catch (java.awt.AWTException ae)
		{
			ae.printStackTrace();
		}
	}
	
	/**
	 * <pre>
	 * &lt;b&gt;Description:&lt;/b&gt;
	 * Function emulate the typing from keyboard to control Upload File. 
	 * &lt;b&gt;Creation date: &lt;/b&gt;02.07.2008
	 * &lt;b&gt;Modification date: &lt;/b&gt;02.07.2008
	 * </pre>
	 * 
	 * @param String
	 *            sPath � path, which must be inputted.
	 * @author Vitalii Fedorets.
	 * @return true if success.
	 *@exception Exception
	 */
	static public boolean insertFilePath(Selenium oSel, String sTarget, String sPath)
			throws Exception
	{
		// Activ();
		oSel.windowFocus();
		oSel.setCursorPosition(sTarget, "0");
		oSel.setCursorPosition(sTarget, "0");
		Thread.sleep(1000);
		return inputFilePath(sPath);
		
	}
	
	/**
	 * <pre>
	 * &lt;b&gt;Description:&lt;/b&gt;
	 * Function emulate the typing from keyboard to control Upload File. 
	 * &lt;b&gt;Creation date: &lt;/b&gt;02.07.2008
	 * &lt;b&gt;Modification date: &lt;/b&gt;02.07.2008
	 * </pre>
	 * 
	 * @param String
	 *            sPath � path, which must be inputted.
	 * @author Vitalii Fedorets.
	 * @return true if success.
	 *@exception Exception
	 */
	static public boolean inputFilePath(String sPath) throws Exception
	{
		try
		{
			
			Robot robot = new Robot();
			
			String sStr = sPath.toUpperCase();
			
			for (int i = 0; i < sStr.length(); i++)
			{
				char cCh = sStr.charAt(i);
				
				if ((cCh >= 'A' && cCh <= 'Z') || (cCh >= '0' && cCh <= '9'))
				{
					robot.keyPress(cCh);
					robot.keyRelease(cCh);
				}
				else if (cCh == '\\')
				{
					robot.keyPress(KeyEvent.VK_BACK_SLASH);
					robot.keyRelease(KeyEvent.VK_BACK_SLASH);
				}
				else if (cCh == ':')
				{
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_SEMICOLON);
					robot.keyRelease(KeyEvent.VK_SEMICOLON);
					robot.keyRelease(KeyEvent.VK_SHIFT);
				}
				else if (cCh == '.')
				{
					robot.keyPress(KeyEvent.VK_PERIOD);
					robot.keyRelease(KeyEvent.VK_PERIOD);
				}
				else if (cCh == ' ')
				{
					robot.keyPress(KeyEvent.VK_SPACE);
					robot.keyRelease(KeyEvent.VK_SPACE);
				}
				else if (cCh == '_')
				{
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_MINUS);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_MINUS);
				}
				else if (cCh == '-')
				{
					robot.keyPress(KeyEvent.VK_MINUS);
					robot.keyRelease(KeyEvent.VK_MINUS);
				}
				else if (cCh == '/')
				{
					robot.keyPress(KeyEvent.VK_SLASH);
					robot.keyRelease(KeyEvent.VK_SLASH);
				}
				else
				{
					return false;
				}
			}
			return true;
		}
		catch (Exception oEx)
		{
			throw oEx;
		}
	}
	
	/**
	 * Function wait page loading with some Url
	 * 
	 * @param oSelenium
	 * @param sTimeOut
	 * @param sUrl
	 * @return
	 */
	static public boolean WaitForPageLoading(Selenium oSelenium, String sTimeOut, String sUrl)
	{
		try
		{
			oSelenium.waitForPageToLoad(sTimeOut);
			
			if (sUrl != null)
			{
				if (sUrl.equalsIgnoreCase(oSelenium.getLocation()))
				{
					return true;
				}
				
				return false;
			}
			
			return true;
		}
		catch (Exception oEx)
		{
			return false;
		}
	}
	
	public static String recalculateProductPrice(String basePrice, float tax)
	{
		DecimalFormat dec = new DecimalFormat("###.00#");
		
		float pricenum = Float.parseFloat(basePrice);
		float fprice = pricenum + pricenum * tax;
		String price = "" + dec.format(fprice);
		
		return price.replace(",", ".");
	}
}
