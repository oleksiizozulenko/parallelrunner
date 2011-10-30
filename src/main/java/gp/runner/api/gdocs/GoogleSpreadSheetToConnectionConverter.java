/**
 *
 */
package gp.runner.api.gdocs;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import project.utils.Connection;

import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.WorksheetQuery;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

/**
 * @author oleksiizozulenko
 */
public class GoogleSpreadSheetToConnectionConverter extends GoogleSpreedSheetService
{
	
	public GoogleSpreadSheetToConnectionConverter(String googleUser, String googlePassword,
			String spreadsheetName, String worksheetName)
	{
		super(googleUser, googlePassword, spreadsheetName, worksheetName);
	}
	
	public List<Connection> getConfigs() throws Exception
	{
		List<PreConnectionGData> connData = this.getGoogleSpreadsheetData();
		List<Connection> configs = this.convertGData(connData);
		return configs;
	}
	
	private List<Connection> convertGData(List<PreConnectionGData> connData)
	{
		List<Connection> configs = new ArrayList<Connection>();
		
		for (PreConnectionGData predata : connData)
		{
			Connection conn = new Connection();
			
			String label = predata.getLabel();
			String duplicateLabel = predata.getDuplicateLabel();
			conn.setLabel(label);
			
			if (!label.equalsIgnoreCase(duplicateLabel))
			{
				conn.setDuplicateLabel(duplicateLabel);
			}
			String appUrl = predata.getApplicationUrl().trim().replaceAll(" ", "");
			conn.setApplicationUrl("http://" + appUrl + "/"
					+ predata.getSubDirectory().trim().replaceAll(" ", ""));
			conn.setFtpHost(appUrl);
			conn.setFtpUser(predata.getFtpUser());
			conn.setFtpPassword(predata.getFtpPassword());
			
			String mailHost = predata.getEmailHost().trim().replaceAll(" ", "");
			conn.setEmailHost("mail." + mailHost);
			conn.setEmailFrom(predata.getEmailFrom() + "@" + mailHost);
			conn.setEmailUserName(predata.getEmailUserName() + "@" + mailHost);
			
			conn.setResetType(predata.getResetType());
			
			conn.setSeleniumBrowser("*" + predata.getSeleniumBrowser());
			conn.setSeleniumHost(predata.getSeleniumHost());
			conn.setSeleniumPort(Integer.parseInt(predata.getSeleniumPort()));
			
			conn.setSkin(predata.getSkin());
			
			conn.setEmailUserPassword(predata.getEmailUserPassword());
			conn.setEmailTimeout(predata.getEmailTimeout());
			conn.setApplicationTimeout(predata.getApplicationTimeout());
			conn.setSeleniumSpeed(predata.getSeleniumSpeed());
			
			// constant settings
			conn.setEnviromentOs("grid");
			
			conn.setGridMode(true);
			conn.setDeleteCookies(true);
			conn.setResetApplication(true);
			conn.setResetPosition("setup");
			conn.setResetFile("/mydump.sql");
			conn.setDebugMode(true);
			configs.add(conn);
		}
		
		return configs;
	}
	
	private List<PreConnectionGData> getGoogleSpreadsheetData() throws Exception
	{
		try
		{
			List<PreConnectionGData> connData = new ArrayList<PreConnectionGData>();
			
			getSpreadshetService();
			URL feedUrl = getFeedUrl();
			
			SpreadsheetQuery spreadsheetQuery = new SpreadsheetQuery(feedUrl);
			spreadsheetQuery.setTitleQuery(this.spreadSheet);
			SpreadsheetFeed spreadsheetFeed = service
					.query(spreadsheetQuery, SpreadsheetFeed.class);
			List<SpreadsheetEntry> spreadsheets = spreadsheetFeed.getEntries();
			
			SpreadsheetEntry spreadsheetEntry = spreadsheets.get(0);
			
			WorksheetQuery worksheetQuery = new WorksheetQuery(spreadsheetEntry
					.getWorksheetFeedUrl());
			
			worksheetQuery.setTitleQuery("all-matrix");
			WorksheetFeed worksheetFeed = service.query(worksheetQuery, WorksheetFeed.class);
			List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
			
			WorksheetEntry worksheetEntry = worksheets.get(0);
			
			URL listFeedUrl = worksheetEntry.getListFeedUrl();
			
			ListQuery query = new ListQuery(listFeedUrl);
			
			ListFeed feed = service.query(query, ListFeed.class);
			for (ListEntry entry : feed.getEntries())
			{
				PreConnectionGData data = null;
				CustomElementCollection customElements = entry.getCustomElements();
				Set<String> tags = customElements.getTags();
				tags.remove("pc");
				tags.remove("server");
				
				for (String tag : tags)
				{
					
					String cellValue = customElements.getValue(tag);
					
					if (tag.equalsIgnoreCase("runningName".toLowerCase()))
					{
						if (cellValue == null)
						{
							break;
						}
						
						if (!cellValue.equalsIgnoreCase(this.workSheet))
						{
							break;
						}
					}
					
					if (tag.equalsIgnoreCase("configStatus".toLowerCase()))
					{
						if (cellValue == null)
						{
							break;
						}
						
						if (cellValue.equalsIgnoreCase("disable"))
						{
							break;
						}
						data = new PreConnectionGData();
					}
					
					if (tag.equalsIgnoreCase("label".toLowerCase()))
					{
						data.setLabel(cellValue);
					}
					
					if (tag.equalsIgnoreCase("duplicateLabel".toLowerCase()))
					{
						data.setDuplicateLabel(cellValue);
					}
					if (tag.equalsIgnoreCase("applicationUrl".toLowerCase()))
					{
						data.setApplicationUrl(cellValue);
					}
					if (tag.equalsIgnoreCase("ftpUser".toLowerCase()))
					{
						data.setFtpUser(cellValue);
					}
					if (tag.equalsIgnoreCase("ftpPassword".toLowerCase()))
					{
						data.setFtpPassword(cellValue);
					}
					if (tag.equalsIgnoreCase("emailUserName".toLowerCase()))
					{
						data.setEmailUserName(cellValue);
					}
					if (tag.equalsIgnoreCase("emailFrom".toLowerCase()))
					{
						data.setEmailFrom(cellValue);
					}
					if (tag.equalsIgnoreCase("emailHost".toLowerCase()))
					{
						data.setEmailHost(cellValue);
					}
					if (tag.equalsIgnoreCase("emailUserPassword".toLowerCase()))
					{
						data.setEmailUserPassword(cellValue);
					}
					
					if (tag.equalsIgnoreCase("emailTimeout".toLowerCase()))
					{
						data.setEmailTimeout(cellValue);
					}
					
					if (tag.equalsIgnoreCase("skin".toLowerCase()))
					{
						data.setSkin(cellValue);
					}
					if (tag.equalsIgnoreCase("seleniumBrowser".toLowerCase()))
					{
						data.setSeleniumBrowser(cellValue);
					}
					
					if (tag.equalsIgnoreCase("seleniumHost".toLowerCase()))
					{
						data.setSeleniumHost(cellValue);
					}
					
					if (tag.equalsIgnoreCase("seleniumPort".toLowerCase()))
					{
						data.setSeleniumPort(cellValue);
					}
					
					if (tag.equalsIgnoreCase("seleniumSpeed".toLowerCase()))
					{
						data.setSeleniumSpeed(cellValue);
						
					}
					if (tag.equalsIgnoreCase("resetType".toLowerCase()))
					{
						data.setResetType(cellValue);
					}
					
					if (tag.equalsIgnoreCase("applicationTimeout".toLowerCase()))
					{
						data.setApplicationTimeout(cellValue);
					}
					
					if (tag.equalsIgnoreCase("subDirectory".toLowerCase()))
					{
						if (cellValue == null)
						{
							data.setSubDirectory("");
						}
						else
						{
							data.setSubDirectory(cellValue);
						}
					}
					
				}
				
				if (data != null)
				{
					connData.add(data);
				}
			}
			
			return connData;
		}
		catch (Throwable e)
		{
			throw new Exception(e);
		}
	}
	
}
