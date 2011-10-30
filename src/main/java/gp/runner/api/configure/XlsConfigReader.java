/**
 *
 */
package gp.runner.api.configure;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import project.utils.Connection;

/**
 * @author oleksii.zozulenko
 */
public class XlsConfigReader extends AbstractConfigReader
{
	String _sheet;
	
	public XlsConfigReader(String path)
	{
		super(path);
	}
	
	public XlsConfigReader(String path, String sheet)
	{
		super(path);
		this._sheet = sheet;
	}
	
	@Override
	public Connection read() throws Exception
	{
		throw new Exception("Method has not implemented yet");
	}
	
	@Override
	public List<Connection> readList() throws Exception
	{
		
		if (_path.startsWith("http://"))
		{
			URL uri = new URL(_path);
			BufferedInputStream in = new java.io.BufferedInputStream(uri.openStream());
			File tmpFile = new File("tmpConfigsFile" + System.currentTimeMillis() + ".xls");
			FileOutputStream fos = new FileOutputStream(tmpFile);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1)
			{
				bout.write(data, 0, count);
			}
			bout.close();
			fos.close();
			in.close();
			_path = tmpFile.getAbsolutePath();
		}
		
		Workbook workbook = Workbook.getWorkbook(new File(this._path));
		Sheet sheet = workbook.getSheet("all-matrix");
		
		Cell runName = sheet.findCell("runningName");
		Cell status = sheet.findCell("configStatus");
		Cell label = sheet.findCell("label");
		Cell duplicateLabel = sheet.findCell("duplicateLabel");
		Cell applicationUrl = sheet.findCell("applicationUrl");
		Cell ftpUser = sheet.findCell("ftpUser");
		Cell ftpPassword = sheet.findCell("ftpPassword");
		Cell emailUserName = sheet.findCell("emailUserName");
		Cell emailFrom = sheet.findCell("emailFrom");
		Cell emailHost = sheet.findCell("emailHost");
		Cell emailUserPassword = sheet.findCell("emailUserPassword");
		Cell emailTimeout = sheet.findCell("emailTimeout");
		Cell skin = sheet.findCell("skin");
		Cell seleniumHost = sheet.findCell("seleniumHost");
		Cell seleniumPort = sheet.findCell("seleniumPort");
		Cell seleniumBrowser = sheet.findCell("seleniumBrowser");
		Cell seleniumSpeed = sheet.findCell("seleniumSpeed");
		Cell resetType = sheet.findCell("resetType");
		Cell applicationTimeout = sheet.findCell("applicationTimeout");
		Cell subDirectory = sheet.findCell("subDirectory");
		
		Cell[] runNameCol = sheet.getColumn(runName.getColumn());
		Cell[] statusCol = sheet.getColumn(status.getColumn());
		Cell[] labelCol = sheet.getColumn(label.getColumn());
		Cell[] duplicateLabelCol = sheet.getColumn(duplicateLabel.getColumn());
		Cell[] appUrlCol = sheet.getColumn(applicationUrl.getColumn());
		Cell[] ftpUserCol = sheet.getColumn(ftpUser.getColumn());
		Cell[] ftpPassCol = sheet.getColumn(ftpPassword.getColumn());
		Cell[] emailUserCol = sheet.getColumn(emailUserName.getColumn());
		Cell[] emailUserPassCol = sheet.getColumn(emailUserPassword.getColumn());
		Cell[] emailTimeoutCol = sheet.getColumn(emailTimeout.getColumn());
		Cell[] emailFromCol = sheet.getColumn(emailFrom.getColumn());
		Cell[] emailHostCol = sheet.getColumn(emailHost.getColumn());
		Cell[] skinCol = sheet.getColumn(skin.getColumn());
		Cell[] selHostCol = sheet.getColumn(seleniumHost.getColumn());
		Cell[] selPortCol = sheet.getColumn(seleniumPort.getColumn());
		Cell[] selBrowserCol = sheet.getColumn(seleniumBrowser.getColumn());
		Cell[] selSpeedCol = sheet.getColumn(seleniumSpeed.getColumn());
		Cell[] resetTypeCol = sheet.getColumn(resetType.getColumn());
		Cell[] appTimeoutCol = sheet.getColumn(applicationTimeout.getColumn());
		Cell[] subDirCol = sheet.getColumn(subDirectory.getColumn());
		
		List<Connection> configs = new ArrayList<Connection>();
		
		for (Cell runname : runNameCol)
		{
			int row1 = runname.getRow();
			if (row1 == 0)
			{
				continue;
			}
			
			String rname = runname.getContents();
			
			if (rname.equalsIgnoreCase("") && !rname.contains(_sheet))
			{
				continue;
			}
			
			for (Cell col : statusCol)
			{
				int row = col.getRow();
				if (row == 0)
				{
					continue;
				}
				String enabled = col.getContents();
				
				if (enabled.equalsIgnoreCase("") && enabled.contains("disable"))
				{
					continue;
				}
				
				if (labelCol[row].getType().equals(CellType.EMPTY))
				{
					break;
				}
				
				Connection conn = new Connection();
				
				String labelVal = labelCol[row].getContents();
				String duplicateLabelVal = duplicateLabelCol[row].getContents();
				conn.setLabel(labelVal);
				
				if (!labelVal.equalsIgnoreCase(duplicateLabelVal))
				{
					conn.setDuplicateLabel(duplicateLabelVal);
				}
				String appUrl = appUrlCol[row].getContents().trim().replaceAll(" ", "");
				conn.setApplicationUrl("http://" + appUrl + "/"
						+ subDirCol[row].getContents().trim().replaceAll(" ", ""));
				conn.setFtpHost(appUrl);
				conn.setFtpUser(ftpUserCol[row].getContents());
				conn.setFtpPassword(ftpPassCol[row].getContents());
				
				String mailHost = emailUserCol[row].getContents().trim().replaceAll(" ", "");
				conn.setEmailHost("mail." + mailHost);
				conn.setEmailFrom(emailFromCol[row].getContents() + "@" + mailHost);
				conn.setEmailUserName(emailHostCol[row].getContents() + "@" + mailHost);
				
				conn.setResetType(resetTypeCol[row].getContents());
				
				conn.setSeleniumBrowser("*" + selBrowserCol[row].getContents());
				conn.setSeleniumHost(selHostCol[row].getContents());
				conn.setSeleniumPort(Integer.parseInt(selPortCol[row].getContents()));
				
				conn.setSkin(skinCol[row].getContents());
				
				conn.setEmailUserPassword(emailUserPassCol[row].getContents());
				conn.setEmailTimeout(emailTimeoutCol[row].getContents());
				conn.setApplicationTimeout(appTimeoutCol[row].getContents());
				conn.setSeleniumSpeed(selSpeedCol[row].getContents());
				
				// constant settings
				conn.setEnviromentOs("grid");
				
				conn.setGridMode(true);
				conn.setDeleteCookies(true);
				conn.setResetApplication(true);
				conn.setResetPosition("setup");
				conn.setResetFile("/mydump.sql");
				
				configs.add(conn);
				
			}
		}
		return configs;
	}
}
