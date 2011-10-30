package project.utils;

/**
 * <pre>
 * &lt;b&gt;Description:&lt;/b&gt;
 * Class contains connection parameters for functional application tests that use selenium/webdriver drivers
 * 
 * &lt;b&gt;Creation date:&lt;/b&gt; 08.29.2008
 * </pre>
 * 
 * @author Oleksii Zozulenko
 */
@SuppressWarnings("serial")
public class Connection implements java.io.Serializable
{
	/*
	 * Next two paramenters need for paralleling.
	 */

	// Some name of configuration. Need for linking
	private String  label;
	
	// List of labels which current configuration is duplicate
	private String  duplicateLabel;
	
	/*
	 * Parameters for configure email agent
	 */
	private String  emailUserName;
	
	private String  emailUserPassword;
	
	private String  emailHost;
	
	private String  emailTimeout;
	
	private String  emailFrom;
	
	private String  screenshotPath;
	
	private String  seleniumHost;
	
	private Integer seleniumPort;
	
	private String  seleniumBrowser;
	
	private String  seleniumSpeed;
	
	private boolean seleniumTraceLog;
	
	private boolean resetApplication;
	
	private String  resetType;
	
	private String  resetPosition;
	
	private String  resetFile;
	
	private boolean deleteCookies;
	
	private String  skin;
	
	private String  applicationUrl;
	
	private String  applicationTimeout;
	
	private String  enviromentOs;
	
	private String  ftpHost;
	
	private String  ftpUser;
	
	private String  ftpPassword;
	
	private boolean gridMode;
	
	private boolean debugMode;
	
	public Connection()
	{
		super();
	}
	
	public String getFtpHost()
	{
		return ftpHost;
	}
	
	public void setFtpHost(String ftpHost)
	{
		this.ftpHost = ftpHost;
	}
	
	public String getFtpUser()
	{
		return ftpUser;
	}
	
	public void setFtpUser(String ftpUser)
	{
		this.ftpUser = ftpUser;
	}
	
	public String getFtpPassword()
	{
		return ftpPassword;
	}
	
	public void setFtpPassword(String ftpPassword)
	{
		this.ftpPassword = ftpPassword;
	}
	
	public String getEmailUserName()
	{
		return emailUserName;
	}
	
	public void setEmailUserName(String emailUserName)
	{
		this.emailUserName = emailUserName;
	}
	
	public String getEmailUserPassword()
	{
		return emailUserPassword;
	}
	
	public void setEmailUserPassword(String emailUserPassword)
	{
		this.emailUserPassword = emailUserPassword;
	}
	
	public String getEmailHost()
	{
		return emailHost;
	}
	
	public void setEmailHost(String emailHost)
	{
		this.emailHost = emailHost;
	}
	
	public String getEmailTimeout()
	{
		return emailTimeout;
	}
	
	public void setEmailTimeout(String emailTimeout)
	{
		this.emailTimeout = emailTimeout;
	}
	
	public String getEmailFrom()
	{
		return emailFrom;
	}
	
	public void setEmailFrom(String emailFrom)
	{
		this.emailFrom = emailFrom;
	}
	
	public String getScreenshotPath()
	{
		return screenshotPath;
	}
	
	public void setScreenshotPath(String screenshotPath)
	{
		this.screenshotPath = screenshotPath;
	}
	
	public String getSeleniumHost()
	{
		return seleniumHost;
	}
	
	public void setSeleniumHost(String seleniumHost)
	{
		this.seleniumHost = seleniumHost;
	}
	
	public int getSeleniumPort()
	{
		if (seleniumPort == null)
		{
			return 0;
		}
		return seleniumPort;
	}
	
	public void setSeleniumPort(int seleniumPort)
	{
		this.seleniumPort = seleniumPort;
	}
	
	public String getSeleniumBrowser()
	{
		return seleniumBrowser;
	}
	
	public void setSeleniumBrowser(String seleniumBrowser)
	{
		this.seleniumBrowser = seleniumBrowser;
	}
	
	public void setSeleniumSpeed(String seleniumSpeed)
	{
		this.seleniumSpeed = seleniumSpeed;
	}
	
	public String getSeleniumSpeed()
	{
		return seleniumSpeed;
	}
	
	public void setSeleniumTraceLog(boolean seleniumTraceLog)
	{
		this.seleniumTraceLog = seleniumTraceLog;
	}
	
	public boolean isSeleniumTraceLog()
	{
		return seleniumTraceLog;
	}
	
	public String getApplicationUrl()
	{
		return applicationUrl;
	}
	
	public void setApplicationUrl(String applicationUrl)
	{
		this.applicationUrl = applicationUrl;
	}
	
	public String getApplicationTimeout()
	{
		return applicationTimeout;
	}
	
	public void setApplicationTimeout(String timeOout)
	{
		this.applicationTimeout = timeOout;
	}
	
	public String getEnviromentOs()
	{
		return enviromentOs;
	}
	
	public void setEnviromentOs(String enviromentOs)
	{
		this.enviromentOs = enviromentOs;
	}
	
	public boolean isResetApplication()
	{
		return resetApplication;
	}
	
	public void setResetApplication(boolean resetApplication)
	{
		this.resetApplication = resetApplication;
	}
	
	public String getResetType()
	{
		return resetType;
	}
	
	public void setResetType(String resetType)
	{
		this.resetType = resetType;
	}
	
	public String getResetPosition()
	{
		return resetPosition;
	}
	
	public void setResetPosition(String resetPosition)
	{
		this.resetPosition = resetPosition;
	}
	
	public String getResetFile()
	{
		return resetFile;
	}
	
	public void setResetFile(String resetFile)
	{
		this.resetFile = resetFile;
	}
	
	public boolean isDeleteCookies()
	{
		return deleteCookies;
	}
	
	public void setDeleteCookies(boolean deleteCookies)
	{
		this.deleteCookies = deleteCookies;
	}
	
	public String getSkin()
	{
		return skin;
	}
	
	public void setSkin(String skin)
	{
		this.skin = skin;
	}
	
	// -------------------------------------------------------
	// Back compatibility stuff
	// -------------------------------------------------------
	
	@Deprecated
	public String getUrl()
	{
		return this.getApplicationUrl();
	}
	
	@Deprecated
	public String getTimeOut()
	{
		return this.getApplicationTimeout();
	}
	
	@Deprecated
	public String getEmailUName()
	{
		return this.getEmailUserName();
	}
	
	@Deprecated
	public String getEmailUPass()
	{
		return this.getEmailUserPassword();
	}
	
	@Deprecated
	public String getBrowser()
	{
		return this.getSeleniumBrowser();
	}
	
	@Deprecated
	public String getOS()
	{
		return this.getEnviromentOs();
	}
	
	@Deprecated
	public int getPort()
	{
		return this.getSeleniumPort();
	}
	
	@Deprecated
	public String getHost()
	{
		return this.getSeleniumHost();
	}
	
	@Deprecated
	public void setUrl(String url)
	{
		this.setApplicationUrl(url);
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((applicationTimeout == null) ? 0 : applicationTimeout.hashCode());
		result = prime * result + ((applicationUrl == null) ? 0 : applicationUrl.hashCode());
		result = prime * result + (debugMode ? 1231 : 1237);
		result = prime * result + (deleteCookies ? 1231 : 1237);
		result = prime * result + ((duplicateLabel == null) ? 0 : duplicateLabel.hashCode());
		result = prime * result + ((emailFrom == null) ? 0 : emailFrom.hashCode());
		result = prime * result + ((emailHost == null) ? 0 : emailHost.hashCode());
		result = prime * result + ((emailTimeout == null) ? 0 : emailTimeout.hashCode());
		result = prime * result + ((emailUserName == null) ? 0 : emailUserName.hashCode());
		result = prime * result + ((emailUserPassword == null) ? 0 : emailUserPassword.hashCode());
		result = prime * result + ((enviromentOs == null) ? 0 : enviromentOs.hashCode());
		result = prime * result + ((ftpHost == null) ? 0 : ftpHost.hashCode());
		result = prime * result + ((ftpPassword == null) ? 0 : ftpPassword.hashCode());
		result = prime * result + ((ftpUser == null) ? 0 : ftpUser.hashCode());
		result = prime * result + (gridMode ? 1231 : 1237);
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + (resetApplication ? 1231 : 1237);
		result = prime * result + ((resetFile == null) ? 0 : resetFile.hashCode());
		result = prime * result + ((resetPosition == null) ? 0 : resetPosition.hashCode());
		result = prime * result + ((resetType == null) ? 0 : resetType.hashCode());
		result = prime * result + ((screenshotPath == null) ? 0 : screenshotPath.hashCode());
		result = prime * result + ((seleniumBrowser == null) ? 0 : seleniumBrowser.hashCode());
		result = prime * result + ((seleniumHost == null) ? 0 : seleniumHost.hashCode());
		result = prime * result + ((seleniumPort == null) ? 0 : seleniumPort.hashCode());
		result = prime * result + ((seleniumSpeed == null) ? 0 : seleniumSpeed.hashCode());
		result = prime * result + (seleniumTraceLog ? 1231 : 1237);
		result = prime * result + ((skin == null) ? 0 : skin.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connection other = (Connection) obj;
		if (applicationTimeout == null)
		{
			if (other.applicationTimeout != null)
				return false;
		}
		else if (!applicationTimeout.equals(other.applicationTimeout))
			return false;
		if (applicationUrl == null)
		{
			if (other.applicationUrl != null)
				return false;
		}
		else if (!applicationUrl.equals(other.applicationUrl))
			return false;
		if (debugMode != other.debugMode)
			return false;
		if (deleteCookies != other.deleteCookies)
			return false;
		if (duplicateLabel == null)
		{
			if (other.duplicateLabel != null)
				return false;
		}
		else if (!duplicateLabel.equals(other.duplicateLabel))
			return false;
		if (emailFrom == null)
		{
			if (other.emailFrom != null)
				return false;
		}
		else if (!emailFrom.equals(other.emailFrom))
			return false;
		if (emailHost == null)
		{
			if (other.emailHost != null)
				return false;
		}
		else if (!emailHost.equals(other.emailHost))
			return false;
		if (emailTimeout == null)
		{
			if (other.emailTimeout != null)
				return false;
		}
		else if (!emailTimeout.equals(other.emailTimeout))
			return false;
		if (emailUserName == null)
		{
			if (other.emailUserName != null)
				return false;
		}
		else if (!emailUserName.equals(other.emailUserName))
			return false;
		if (emailUserPassword == null)
		{
			if (other.emailUserPassword != null)
				return false;
		}
		else if (!emailUserPassword.equals(other.emailUserPassword))
			return false;
		if (enviromentOs == null)
		{
			if (other.enviromentOs != null)
				return false;
		}
		else if (!enviromentOs.equals(other.enviromentOs))
			return false;
		if (ftpHost == null)
		{
			if (other.ftpHost != null)
				return false;
		}
		else if (!ftpHost.equals(other.ftpHost))
			return false;
		if (ftpPassword == null)
		{
			if (other.ftpPassword != null)
				return false;
		}
		else if (!ftpPassword.equals(other.ftpPassword))
			return false;
		if (ftpUser == null)
		{
			if (other.ftpUser != null)
				return false;
		}
		else if (!ftpUser.equals(other.ftpUser))
			return false;
		if (gridMode != other.gridMode)
			return false;
		if (label == null)
		{
			if (other.label != null)
				return false;
		}
		else if (!label.equals(other.label))
			return false;
		if (resetApplication != other.resetApplication)
			return false;
		if (resetFile == null)
		{
			if (other.resetFile != null)
				return false;
		}
		else if (!resetFile.equals(other.resetFile))
			return false;
		if (resetPosition == null)
		{
			if (other.resetPosition != null)
				return false;
		}
		else if (!resetPosition.equals(other.resetPosition))
			return false;
		if (resetType == null)
		{
			if (other.resetType != null)
				return false;
		}
		else if (!resetType.equals(other.resetType))
			return false;
		if (screenshotPath == null)
		{
			if (other.screenshotPath != null)
				return false;
		}
		else if (!screenshotPath.equals(other.screenshotPath))
			return false;
		if (seleniumBrowser == null)
		{
			if (other.seleniumBrowser != null)
				return false;
		}
		else if (!seleniumBrowser.equals(other.seleniumBrowser))
			return false;
		if (seleniumHost == null)
		{
			if (other.seleniumHost != null)
				return false;
		}
		else if (!seleniumHost.equals(other.seleniumHost))
			return false;
		if (seleniumPort == null)
		{
			if (other.seleniumPort != null)
				return false;
		}
		else if (!seleniumPort.equals(other.seleniumPort))
			return false;
		if (seleniumSpeed == null)
		{
			if (other.seleniumSpeed != null)
				return false;
		}
		else if (!seleniumSpeed.equals(other.seleniumSpeed))
			return false;
		if (seleniumTraceLog != other.seleniumTraceLog)
			return false;
		if (skin == null)
		{
			if (other.skin != null)
				return false;
		}
		else if (!skin.equals(other.skin))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "Connection [applicationTimeout=" + applicationTimeout + ", applicationUrl="
				+ applicationUrl + ", debugMode=" + debugMode + ", deleteCookies=" + deleteCookies
				+ ", duplicateLabel=" + duplicateLabel + ", emailFrom=" + emailFrom
				+ ", emailHost=" + emailHost + ", emailTimeout=" + emailTimeout
				+ ", emailUserName=" + emailUserName + ", emailUserPassword=" + emailUserPassword
				+ ", enviromentOs=" + enviromentOs + ", ftpHost=" + ftpHost + ", ftpPassword="
				+ ftpPassword + ", ftpUser=" + ftpUser + ", gridMode=" + gridMode + ", label="
				+ label + ", resetApplication=" + resetApplication + ", resetFile=" + resetFile
				+ ", resetPosition=" + resetPosition + ", resetType=" + resetType
				+ ", screenshotPath=" + screenshotPath + ", seleniumBrowser=" + seleniumBrowser
				+ ", seleniumHost=" + seleniumHost + ", seleniumPort=" + seleniumPort
				+ ", seleniumSpeed=" + seleniumSpeed + ", seleniumTraceLog=" + seleniumTraceLog
				+ ", skin=" + skin + "]";
	}
	
	public void setGridMode(boolean gridMode)
	{
		this.gridMode = gridMode;
	}
	
	public boolean isGridMode()
	{
		return gridMode;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public String getDuplicateLabel()
	{
		return duplicateLabel;
	}
	
	public void setDuplicateLabel(String duplicateLabel)
	{
		this.duplicateLabel = duplicateLabel;
	}
	
	public boolean isDebugMode()
	{
		return debugMode;
	}
	
	public void setDebugMode(boolean debugMode)
	{
		this.debugMode = debugMode;
	}
	
	public void setSeleniumPort(Integer seleniumPort)
	{
		this.seleniumPort = seleniumPort;
	}
}
