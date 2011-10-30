/**
 *
 */
package gp.runner.api.gdocs;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;

/**
 * @author oleksii.zozulenko
 */
public class GoogleSpreedSheetService
{
	Logger logger;
	
	protected String gUser;
	protected String gPass;
	protected String spreadSheet;
	protected String workSheet;
	
	protected SpreadsheetService service;
	
	protected GoogleSpreedSheetService(String googleUser, String googlePassword,
			String spreadsheetName, String worksheetName)
	{
		this.gUser = googleUser;
		this.gPass = googlePassword;
		this.spreadSheet = spreadsheetName;
		this.workSheet = worksheetName;
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	protected SpreadsheetService getSpreadshetService()
	{
		try
		{
			service = new SpreadsheetService("GP-GoogleSpreadSheetToConnectionConverter-1");
			
			service.setUserCredentials(gUser, gPass);
		}
		catch (Throwable tw)
		{
			logger.log(Level.SEVERE, "Unable to create google-docs service.", tw);
			tw.printStackTrace();
		}
		logger.log(Level.INFO, "GoogleDocs service successfully created");
		
		return service;
	}
	
	protected URL getFeedUrl()
	{
		FeedURLFactory factory = FeedURLFactory.getDefault();
		
		return factory.getSpreadsheetsFeedUrl();
	}
}
