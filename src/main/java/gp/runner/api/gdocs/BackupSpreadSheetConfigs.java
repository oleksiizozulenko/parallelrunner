/**
 *
 */
package gp.runner.api.gdocs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.google.gdata.client.GoogleAuthTokenFactory.UserToken;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;

/**
 * @author oleksii.zozulenko
 */
public class BackupSpreadSheetConfigs extends GoogleSpreedSheetService
{
	DocsService client;
	
	protected BackupSpreadSheetConfigs(String googleUser, String googlePassword,
			String spreadsheetName, String worksheetName)
	{
		super(googleUser, googlePassword, spreadsheetName, worksheetName);
		
	}
	
	public static void main(String[] args)
	{
		String localConfPath = args[0].replaceFirst("gdoc://", "");
		
		String[] splitedline = localConfPath.split("/");
		
		String worksheet = splitedline[2];
		String spreadsheet = splitedline[1];
		String[] usrSpl = splitedline[0].split(":");
		String user = usrSpl[0];
		String pass = usrSpl[1];
		
		BackupSpreadSheetConfigs backupper = new BackupSpreadSheetConfigs(user, pass, spreadsheet,
				worksheet);
		
		backupper.backup();
	}
	
	public void backup()
	{
		try
		{
			this.getSpreadshetService();
			this.getDocsService();
			URL feedUrl = getFeedUrl();
			SpreadsheetQuery spreadsheetQuery = new SpreadsheetQuery(feedUrl);
			spreadsheetQuery.setTitleQuery(this.spreadSheet);
			SpreadsheetFeed spreadsheetFeed = service
					.query(spreadsheetQuery, SpreadsheetFeed.class);
			List<SpreadsheetEntry> spreadsheets = spreadsheetFeed.getEntries();
			
			SpreadsheetEntry spreadsheetEntry = spreadsheets.get(0);
			
			URL spreadSheetFeedUrl = spreadsheetEntry
					.getWorksheetFeedUrl();
			List<DocumentListEntry> entries = client.getFeed(spreadSheetFeedUrl,
					DocumentListFeed.class)
					.getEntries();
			
			File backupDir = new File("backup");
			if (!backupDir.exists())
			{
				backupDir.mkdir();
			}
			
			String feed = ((MediaContent) entries.get(0).getContent()).getUri();
			
			String docId = feed.substring(feed.indexOf("list/") + 5, feed
					.indexOf("/oda/private"));
			downloadSpreadsheet(docId, backupDir.getAbsolutePath() + "/"
					+ this.spreadSheet + ".xls");
		}
		catch (Throwable tw)
		{
			tw.printStackTrace();
		}
		
	}
	
	private void getDocsService()
	{
		try
		{
			client = new DocsService("yourCo-yourAppName-v1");
			UserToken docsToken = (UserToken) client.getAuthTokenFactory().getAuthToken();
			UserToken spreadsheetsToken = (UserToken) service.getAuthTokenFactory()
					.getAuthToken();
			client.setUserToken(spreadsheetsToken.getValue());
		}
		catch (Throwable te)
		{
			te.printStackTrace();
		}
	}
	
	private void downloadFile(String exportUrl, String filepath)
			throws IOException, MalformedURLException, ServiceException
	{
		System.out.println("Exporting document from: " + exportUrl);
		
		MediaContent mc = new MediaContent();
		mc.setUri(exportUrl);
		
		MediaSource ms = client.getMedia(mc);
		
		InputStream inStream = null;
		FileOutputStream outStream = null;
		
		try
		{
			inStream = ms.getInputStream();
			outStream = new FileOutputStream(filepath);
			
			int c;
			while ((c = inStream.read()) != -1)
			{
				outStream.write(c);
			}
		}
		finally
		{
			if (inStream != null)
			{
				inStream.close();
			}
			if (outStream != null)
			{
				outStream.flush();
				outStream.close();
			}
		}
	}
	
	void downloadSpreadsheet(String resourceId, String filepath)
			throws IOException, MalformedURLException, ServiceException
	{
		String docId = resourceId.substring(resourceId.lastIndexOf(":") + 1);
		String fileExtension = filepath.substring(filepath.lastIndexOf(".") + 1);
		String exportUrl = "https://spreadsheets.google.com/feeds/download/spreadsheets" +
				"/Export?key=" + docId + "&exportFormat=" + fileExtension;
		
		// If exporting to .csv or .tsv, add the gid parameter to specify which sheet to export
		if (fileExtension.equals("csv") || fileExtension.equals("tsv"))
		{
			exportUrl += "&gid=0"; // gid=0 will download only the first sheet
		}
		
		downloadFile(exportUrl, filepath);
	}
	
	private void downloadSpreadsheet(DocumentListEntry entry, String filepath)
			throws IOException, MalformedURLException, ServiceException
	{
		String fileExtension = filepath.substring(filepath.lastIndexOf(".") + 1);
		String exportUrl = ((MediaContent) entry.getContent()).getUri() + "&exportFormat="
				+ fileExtension;
		String[] fileExtensions =
		{ "xls", "csv", "pdf", "ods", "tsv", "html" };
		
		// If exporting to .csv or .tsv, add the gid parameter to specify which sheet to export
		if (Arrays.asList(fileExtensions).contains(fileExtension))
		{
			exportUrl += "";// will download only the first sheet
		}
		
		downloadFile(exportUrl, filepath);
	}
	
}
