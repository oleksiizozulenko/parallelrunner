package project.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import common.log.Logger;
import common.logging.model.LogLevel;

/**
 * <pre>
 * <b>Description:</b>
 * Class contains functions for work with ftp host
 * <b>Creation date: </b>05.02.08
 * <b>Modification date: </b>05.02.08
 * </pre>
 * 
 * @author Oleskii Zozulenko
 */
public class FTPHelper
{
	private String _sFtpHost = null;
	
	private String _sFtpUserName = null;
	
	private String _sFtpPassword = null;
	
	private int _port = 0;
	
	Logger log;
	
	private FTPClient oFtp = null;
	
	/**
	 * <pre>
	 * <b>Description:</b>
	 * Constructor.
	 * </pre>
	 * 
	 * @param String
	 *            sFtpHost -- name connect ftp host
	 * @param String
	 *            sUserName -- connect user name
	 * @param String
	 *            sPassword -- connect user password
	 */
	public FTPHelper(Connection oConn, Logger oLog)
	{
		this(oConn);
		log = oLog;
		
		if (LogLevel.getValue(log.getLogLevel()).ordinal() > LogLevel.DEBUG.ordinal())
		{
			log.setLogLevel(LogLevel.DEBUG);
		}
	}
	
	public FTPHelper(Connection oConn)
	{
		_sFtpHost = oConn.getFtpHost();
		_sFtpUserName = oConn.getFtpUser();
		_sFtpPassword = oConn.getFtpPassword();
	}
	
	public void setPort(int iPort)
	{
		this._port = iPort;
	}
	
	public void setFtpClient(FTPClient ftp)
	{
		this.oFtp = ftp;
	}
	
	/**
	 * <pre>
	 * <b>Description:</b>
	 * Function create ftp folder
	 * <b>Creation date: </b>02.05.08
	 * <b>Modification date: </b>02.05.08
	 * </pre>
	 * 
	 * @param String
	 *            sDirName � directory name
	 * @author Vitalii Fedorets
	 * @return boolean � true if success
	 */
	public boolean createFolder(String sDirName) throws Exception
	{
		try
		{
			FTPClient oFtp = this.initFtpClientLoginChangeWorkingDirectory(sDirName);
			
			if (!oFtp.makeDirectory(sDirName))
			{
				log.setError("Could not create directory `" + sDirName + "`");
				oFtp.disconnect();
				return false;
			}
			
			oFtp.disconnect();
			return true;
		}
		catch (Exception oEx)
		{
			throw oEx;
		}
	}
	
	/**
	 * <pre>
	 * <b>Description:</b>
	 * Function check exists directory on ftp
	 * <b>Creation date: </b>02.05.08
	 * <b>Modification date: </b>02.05.08
	 * </pre>
	 * 
	 * @param String
	 *            String sDirPath � directory name in ftp
	 * @author Vitalii Fedorets
	 * @return boolean � true if success
	 */
	public boolean checkIfFolderExist(String sDirPath) throws Exception
	{
		try
		{
			FTPClient oFtp = this.initFtpClientLoginChangeWorkingDirectory(sDirPath);
			
			oFtp.disconnect();
			return true;
		}
		catch (Exception oEx)
		{
			throw oEx;
		}
	}
	
	/**
	 * <pre>
	 * <b>Description:</b>
	 * Function remove folder from ftp
	 * <b>Creation date: </b>02.05.08
	 * <b>Modification date: </b>02.05.08
	 * </pre>
	 * 
	 * @param String
	 *            sDirPath � removed directory
	 * @author Vitalii Fedorets
	 * @return boolean � true if success
	 */
	public boolean removeFolder(String sDirPath) throws Exception
	{
		try
		{
			File oDir = new File(sDirPath);
			
			FTPClient oFtp = this.initFtpClientLoginChangeWorkingDirectory(sDirPath);
			
			oFtp.changeWorkingDirectory(oDir.getParent().replace("\\", "/"));
			int iReply = oFtp.getReplyCode();
			
			if (!FTPReply.isPositiveCompletion(iReply))
			{
				log.setError("Not change work directory to `" + oDir.getParent().replace("\\", "/")
						+ "`");
				oFtp.disconnect();
				return false;
			}
			
			if (!oFtp.removeDirectory(sDirPath))
			{
				log.setError("Could not remove directory `" + sDirPath + "`");
				oFtp.disconnect();
				return false;
			}
			
			return true;
		}
		catch (Exception oEx)
		{
			throw oEx;
		}
	}
	
	/**
	 * <pre>
	 * <b>Description:</b>
	 * Function check file content on ftp folder
	 * <b>Creation date: </b>02.05.08
	 * <b>Modification date: </b>02.05.08
	 * </pre>
	 * 
	 * @param String
	 *            sDirPath � directory contains file
	 * @param String
	 *            sFileName � file name
	 * @param String
	 *            sText - text which contains in file
	 * @author Vitalii Fedorets
	 * @return boolean � true if file contains text
	 */
	public boolean checkTextInFile(String sDirPath, String sFileName, String sText)
			throws Exception
	{
		try
		{
			FTPClient oFtp = this.initFtpClientLoginChangeWorkingDirectory(sDirPath);
			
			ByteArrayOutputStream oBuf = new ByteArrayOutputStream();
			
			if (!oFtp.retrieveFile(sFileName, oBuf))
			{
				log.setError("Could not read file `" + sFileName + "`");
				oFtp.disconnect();
				return false;
			}
			
			oFtp.disconnect();
			
			String sBuf = oBuf.toString();
			
			oBuf.close();
			
			return sBuf.contains(sText);
		}
		catch (Exception oEx)
		{
			throw oEx;
		}
	}
	
	/**
	 * <pre>
	 * <b>Description:</b>
	 * Function connect to ftp and get attribute value from xml file by tag name
	 * <b>Creation date: </b>02.07.2008
	 * <b>Modification date: </b>02.07.2008
	 * </pre>
	 * 
	 * @param String
	 *            sDirPath -- dirrectory where file located
	 * @param String
	 *            sFileName -- XML file name
	 * @param String
	 *            sTag -- searching tag in file
	 * @param String
	 *            sAttribute -- tag attribute
	 * @author Oleksii Zozulenko.
	 * @return String sAttrValue -- attribute value, or null if error
	 *@exception Exception
	 */
	public String getXMLAttributeValue(String sDirPath, String sFileName, String sTag,
			String sAttribute) throws Exception
	{
		try
		{
			String sAttrValue = null;
			
			FTPClient oFtp = this.initFtpClientLoginChangeWorkingDirectory(sDirPath);
			
			ByteArrayOutputStream oBuf = new ByteArrayOutputStream();
			
			if (!oFtp.retrieveFile(sFileName, oBuf))
			{
				log.setError("Could not read file `" + sFileName + "`");
				oFtp.disconnect();
				return null;
			}
			
			oFtp.disconnect();
			
			String sBuf = oBuf.toString();
			
			oBuf.close();
			
			if (!sBuf.contains(sTag))
			{
				log.setError("File `" + sFileName + "` not contains tag `" + sTag + "`");
				return null;
			}
			
			sBuf = sBuf.substring(sBuf.indexOf(sTag));
			
			if (!sBuf.contains(sAttribute))
			{
				log.setError("File `" + sFileName + "` not contains tag `" + sTag
						+ "` with attribute `" + sAttribute + "`");
				return null;
			}
			sBuf = sBuf.substring(sBuf.indexOf(sAttribute + "=\"") + (sAttribute + "=\"").length());
			sAttrValue = sBuf.substring(0, sBuf.indexOf("\""));
			
			return sAttrValue;
		}
		catch (Exception oEx)
		{
			throw oEx;
		}
	}
	
	/**
	 * @param sDirPath
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	private FTPClient initFtpClientLoginChangeWorkingDirectory(String sDirPath)
			throws SocketException, IOException
	{
		if (oFtp != null)
		{
			return oFtp;
		}
		
		oFtp = initFtpClientAndLogin();
		int iReply;
		oFtp.changeWorkingDirectory(sDirPath);
		iReply = oFtp.getReplyCode();
		
		if (!FTPReply.isPositiveCompletion(iReply))
		{
			log.setError("Not change work directory to `" + sDirPath + "`");
			oFtp.disconnect();
			return null;
		}
		return oFtp;
	}
	
	@Deprecated
	private FTPClient initFtpClientAndLogin() throws SocketException, IOException
	{
		if (oFtp != null)
		{
			return oFtp;
		}
		
		FTPClient oFtp = new FTPClient();
		
		int iReply;
		
		oFtp.connect(_sFtpHost);
		
		iReply = oFtp.getReplyCode();
		
		if (!FTPReply.isPositiveCompletion(iReply))
		{
			log.setError("Could not connect to ftp host" + _sFtpHost);
			return null;
		}
		
		if (!oFtp.login(_sFtpUserName, _sFtpPassword))
		{
			log.error("Could not login with user `" + _sFtpUserName + "` and password `"
					+ _sFtpPassword + "`");
			oFtp.disconnect();
			return null;
		}
		oFtp.enterLocalPassiveMode();
		return oFtp;
	}
	
	/* *****************************************
	 * Section where for methods has unit-tests
	 * *****************************************
	 */

	/**
	 * Function append smth to remote text file.
	 */
	public void appendFile(String fileName, String appendContent) throws SocketException,
			IOException
	{
		
		connect2Server();
		
		ByteArrayInputStream oInStream = new ByteArrayInputStream((appendContent + "\n").getBytes());
		
		if (!oFtp.appendFile(fileName, oInStream))
		{
			logError("File `" + fileName + "` can't append. FTP reply code: " + oFtp.getReplyCode()
					+ " Ftp message: " + Arrays.asList(oFtp.getReplyStrings()));
		}
		
		closeFtpConnection();
		
	}
	
	/**
	 * Function retrieved file on the remote server
	 * NOTE: This function closes output stream after retrieving file content
	 * 
	 * @param fileName
	 * @param fileOutput
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public boolean retrieveFile(String fileName, OutputStream fileOutput) throws SocketException,
			IOException
	{
		boolean fileRetrieved = false;
		
		connect2Server();
		
		if (oFtp.isConnected())
		{
			
			try
			{
				
				fileRetrieved = oFtp.retrieveFile(fileName, fileOutput);
				
				if (fileRetrieved)
				{
					logMessage("File '" + fileName + "' retrieved");
				}
				else
				{
					logError("Can't retrieve file " + fileName + " FTP reply code: "
							+ oFtp.getReplyCode() + " Ftp message: " + oFtp.getReplyString());
				}
				fileOutput.close();
			}
			catch (IOException ioe)
			{
				log.warning("FTPUtil - retrieveFile(): " + ioe.toString());
				return false;
			}
			
		}
		this.closeFtpConnection();
		return fileRetrieved;
	}
	
	/**
	 * @param mask
	 * @param remoteFolder
	 * @throws SocketException
	 * @throws IOException
	 */
	public void removeFilesByMask(String mask, String remoteFolder) throws SocketException,
			IOException
	{
		connect2Server();
		
		if (oFtp.isConnected())
		{
			FTPFile[] fileslist = oFtp.listFiles(remoteFolder);
			
			Pattern pat = Pattern.compile("^" + mask);
			
			for (FTPFile file : fileslist)
			{
				java.util.regex.Matcher m = pat.matcher(file.getName());
				boolean is_pat_file = m.find();
				
				if (is_pat_file)
				{
					oFtp.deleteFile(remoteFolder + file.getName());
				}
			}
		}
		this.closeFtpConnection();
	}
	
	/**
	 * @param remoteFile
	 * @param path
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public boolean isExists(String remoteFile, String path) throws SocketException, IOException
	{
		this.connect2Server();
		
		if (this.oFtp.isConnected())
		{
			
			FTPFile[] files = oFtp.listFiles(path);
			
			for (FTPFile file : files)
			{
				if (file.getName().equalsIgnoreCase(remoteFile))
				{
					return true;
				}
			}
			
		}
		this.closeFtpConnection();
		return false;
	}
	
	/**
	 * @param mask
	 * @param path
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public List<FTPFile> getFilesByMask(String mask, String path) throws SocketException,
			IOException
	{
		List<FTPFile> maskFiles = new ArrayList<FTPFile>();
		connect2Server();
		
		if (oFtp.isConnected())
		{
			FTPFile[] fileslist = oFtp.listFiles(path);
			
			Pattern pat = Pattern.compile("^" + mask);
			
			for (FTPFile file : fileslist)
			{
				java.util.regex.Matcher m = pat.matcher(file.getName());
				boolean is_pat_file = m.find();
				
				if (is_pat_file)
				{
					maskFiles.add(file);
				}
			}
		}
		this.closeFtpConnection();
		return maskFiles;
	}
	
	/**
	 * Delete file from server
	 * 
	 * @param filename
	 * @param ftpFolder
	 * @throws SocketException
	 * @throws IOException
	 */
	public void removeFile(String filename, String ftpFolder) throws SocketException, IOException
	{
		connect2Server();
		
		if (oFtp.isConnected())
		{
			String file = ftpFolder + filename;
			
			if (this.isExists(filename, ftpFolder))
			{
				if (!oFtp.deleteFile(file))
				{
					logError("Can't delete file '" + filename + "' from " + ftpFolder
							+ " FTP reply code: " + oFtp.getReplyCode() + " Ftp message: "
							+ Arrays.asList(oFtp.getReplyStrings()));
				}
				else
				{
					logMessage("File '" + file + "' removed from server");
				}
			}
		}
		this.closeFtpConnection();
	}
	
	/**
	 * The method that handles file uploading, this method takes the absolute file path of a local file to be uploaded to the remote FTP server, and the remote file will then be transfered to the FTP server and saved as the relative path name specified in method setRemoteFile
	 * 
	 * @param localfilename
	 *            – the local absolute file name of the file in local hard drive that needs to FTP over
	 */
	public synchronized boolean uploadFile(String localfilename, String remoteFile)
	{
		
		final BufferedInputStream bis;
		try
		{
			// URL url = new URL(this.getRemoteFileUrl(remoteFile));
			// URLConnection urlFtpConnection = url.openConnection();
			
			InputStream is = new FileInputStream(localfilename);
			bis = new BufferedInputStream(is);
			
			connect2Server();
			
			if (oFtp.isConnected())
			{
				String fileExtension = localfilename.substring(localfilename.lastIndexOf(".") + 1);
				
				String[] fileExtensions =
				{ "png", "gif", "bmp", "jpg", "jpeg", "tiff" };
				
				// If exporting to .csv or .tsv, add the gid parameter to specify which sheet to export
				if (Arrays.asList(fileExtensions).contains(fileExtension))
				{
					oFtp.setFileType(FTP.BINARY_FILE_TYPE);
				}
				
				// OutputStream os = oFtp.storeFileStream(remoteFile);
				// if (os == null)
				// {
				// logError(oFtp.getReplyString());
				// }
				// BufferedOutputStream bos = new BufferedOutputStream(os);
				// byte[] buffer = new byte[1024];
				// int readCount;
				//
				// while ((readCount = bis.read(buffer)) > 0)
				// {
				// bos.write(buffer);
				// // bos.flush();
				// }
				// bos.close();
				
				if (!oFtp.storeFile(remoteFile, bis))
				{
					logError("Can't upload file " + localfilename + " FTP reply code: "
							+ oFtp.getReplyCode() + " Ftp message: " + oFtp.getReplyString());
					
					bis.close();
					is.close();
					
					this.closeFtpConnection();
					return false;
				}
				else
				{
					logMessage("File `" + localfilename + "` is uploaded!");
				}
			}
			
			/*
			 * urlFtpConnection.getOutputStream();
			 * //
			 * log.message("File `" + localfilename + "` is uploaded!");
			 */

			bis.close();
			is.close();
			// this.closeFtpConnection();
			return true;
		}
		catch (Exception ex)
		{
			StringWriter sw0 = new StringWriter();
			PrintWriter p0 = new PrintWriter(sw0, true);
			ex.printStackTrace(p0);
			log.error(sw0.getBuffer().toString());
			log.exception(ex);
			
			return false;
		}
	}
	
	/**
	 * The method to download a file and save it onto the local drive of the client in the specified absolut path
	 * 
	 * @param localfilename
	 *            – the local absolute file name that the file needs to be saved as
	 */
	public synchronized boolean downloadFile(String remoteFile, String localfilename)
	{
		try
		{
			/*
			 * URL url = new URL(this.getRemoteFileUrl(remoteFile));
			 * URLConnection urlFtpConnection = url.openConnection();
			 * InputStream is = urlFtpConnection.getInputStream();
			 * BufferedInputStream bis = new BufferedInputStream(is);
			 */
			OutputStream os = new FileOutputStream(localfilename);
			return this.retrieveFile(remoteFile, os);
			/*
			 * BufferedOutputStream bos = new BufferedOutputStream(os);
			 * byte[] buffer = new byte[1024];
			 * int readCount;
			 * while ((readCount = bis.read(buffer)) > 0)
			 * {
			 * bos.write(buffer, 0, readCount);
			 * }
			 * bos.close();
			 * is.close(); // close the FTP inputstream
			 * logMessage("File `" + localfilename + "` is downloaded!");
			 * bis.close();
			 * return true;
			 */
		}
		catch (Exception ex)
		{
			StringWriter sw0 = new StringWriter();
			PrintWriter p0 = new PrintWriter(sw0, true);
			ex.printStackTrace(p0);
			log.error(sw0.getBuffer().toString());
			log.exception(ex);
			return false;
		}
	}
	
	/*
	 * Additional methods section
	 */

	/** */
	private synchronized void connect2Server() throws SocketException, IOException
	{
		if (oFtp == null)
		{
			this.oFtp = new FTPClient();
			
		}
		
		if (log != null)
		{
			if (LogLevel.getValue(log.getLogLevel()).ordinal() > LogLevel.DEBUG.ordinal())
			{
				oFtp.addProtocolCommandListener(new PrintCommandListener(
						new PrintWriter(System.err)));
			}
		}
		
		if (_port == 0)
		{
			oFtp.connect(this._sFtpHost, 21);
		}
		else
		{
			oFtp.connect(this._sFtpHost, _port);
		}
		if (!oFtp.login(this._sFtpUserName, this._sFtpPassword))
		{
			logError("Can't login to server. FTP responce: " + oFtp.getReplyString());
		}
		
		oFtp.enterLocalPassiveMode();
	}
	
	/** */
	private synchronized void closeFtpConnection() throws IOException
	{
		if (oFtp.isConnected())
		{
			try
			{
				oFtp.logout();
			}
			catch (org.apache.commons.net.ftp.FTPConnectionClosedException e)
			{
			}
		}
		oFtp.disconnect();
	}
	
	String getRemoteFileUrl(String remoteFile)
	{
		return "ftp://" + this._sFtpUserName + ":" + this._sFtpPassword + "@" + this._sFtpHost
				+ "/" + remoteFile + ";type=l";
	}
	
	/*
	 * *************************************************
	 * Section with methods for log errors from ftp
	 * **************************************************
	 */

	private void logError(String err)
	{
		if (this.log != null)
		{
			log.error(err);
		}
	}
	
	private void logMessage(String msg)
	{
		if (this.log != null)
		{
			log.message(msg);
		}
	}
	
}
