package project.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.Permissions;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

public class FTPHelperTest
{
	FakeFtpServer fakeFtpServer;
	
	final static String USER = "joe";
	
	final static String PASSWORD = "joe123";
	
	final static int PORT = 4421;
	
	FTPHelper help;
	
	public FTPHelperTest()
	{
		initFakeFtpServer();
	}
	
	@Before
	public void setUp() throws Exception
	{
		Connection conn = new Connection();
		
		conn.setFtpHost("localhost");
		conn.setFtpUser(USER);
		conn.setFtpPassword(PASSWORD);
		
		// Logger log = EasyMock.createMock(Logger.class);
		
		help = new FTPHelper(conn);
		help.setPort(PORT);
	}
	
	private void initFakeFtpServer()
	{
		fakeFtpServer = new FakeFtpServer();
		this.fakeFtpServer.setServerControlPort(PORT);
		
		setFakeFTPFileSystem();
		
		// Create UserAccount with username, password, home-directory
		UserAccount userAccount = new UserAccount(USER, PASSWORD, "/");
		fakeFtpServer.addUserAccount(userAccount);
		UserAccount userAccountWWW = new UserAccount(USER + "-www", PASSWORD, "/");
		fakeFtpServer.addUserAccount(userAccountWWW);
		
		fakeFtpServer.start();
		if (this.fakeFtpServer.isStarted())
		{
			System.out.println("fake server started");
		}
	}
	
	private void setFakeFTPFileSystem()
	{
		FileSystem fileSystem = new UnixFakeFileSystem();
		
		fileSystem = setDirectoryEntries(fileSystem);
		
		fileSystem = setFilesEntries(fileSystem);
		
		fakeFtpServer.setFileSystem(fileSystem);
	}
	
	private FileSystem setFilesEntries(FileSystem fileSystem)
	{
		
		FileEntry fileEntry1 = new FileEntry("/data/file1.txt", "abcdef 1234567890");
		fileEntry1.setPermissionsFromString("rw-rw-rw-");
		fileEntry1.setOwner(USER);
		fileEntry1.setGroup("dev");
		
		FileEntry fileEntry2 = new FileEntry("/data/run.exe");
		fileEntry2.setPermissionsFromString("rwxrwx---");
		fileEntry2.setOwner("mary");
		fileEntry2.setGroup("dev");
		
		FileEntry fileEntry4 = new FileEntry("/rem/deletedfile.txt");
		fileEntry2.setPermissionsFromString("rwxrwx---");
		fileEntry2.setOwner("mary");
		fileEntry2.setGroup("dev");
		
		String[] files_for_mask_deleting =
		{ "/css/generated_123.css", "/css/some.css", "/css/bla_1.c", "/css/bla_2.c",
				"/css/bla_3.c", "/css/abla_css.c" };
		
		for (String file : files_for_mask_deleting)
		{
			FileEntry fileEntry3 = new FileEntry(file, "fkjghd");
			fileEntry3.setPermissionsFromString("rw-rw-rw-");
			fileEntry3.setOwner(USER + "-www");
			fileEntry3.setGroup("dev");
			
			fileSystem.add(fileEntry3);
		}
		
		fileSystem.add(fileEntry1);
		fileSystem.add(fileEntry2);
		fileSystem.add(fileEntry4);
		
		return fileSystem;
	}
	
	private FileSystem setDirectoryEntries(FileSystem fileSystem)
	{
		String[] directories =
		{ "/", "/data", "/css", "/rem", "/incoming" };
		
		for (String dir : directories)
		{
			DirectoryEntry directoryEntry1 = new DirectoryEntry(dir);
			directoryEntry1.setPermissions(new Permissions("rwxrwx---"));
			directoryEntry1.setOwner(USER);
			directoryEntry1.setGroup("dev");
			
			fileSystem.add(directoryEntry1);
		}
		
		return fileSystem;
	}
	
	@After
	public void tearDown() throws Exception
	{
		fakeFtpServer.stop();
	}
	
	@Test
	@Ignore
	public void testAppendRemoteFile() throws SocketException, IOException
	{
		
		String content = "dsads";
		help.appendFile("/data/file1.txt", content);
		// Instantiate the stream you want to use.
		ByteArrayOutputStream sos = new ByteArrayOutputStream();
		
		boolean isretrieved = help.retrieveFile("/data/file1.txt", sos);
		
		if (!isretrieved)
		{
			Assert.fail("Faile is not retrieved");
		}
		String value = sos.toString();
		if (!value.contains(content))
		{
			Assert.fail("File is not appended");
		}
	}
	
	@Test
	public void testRetriveFile() throws IOException
	{
		ByteArrayOutputStream sos = new ByteArrayOutputStream();
		
		try
		{
			help.retrieveFile("/data/file1.txt", sos);
		}
		catch (SocketException oSEx)
		{
			throw oSEx;
		}
		catch (IOException e)
		{
			Assert.fail(e.getLocalizedMessage());
		}
		
		String value = sos.toString();
		if (value.equalsIgnoreCase(""))
		{
			Assert.fail("File is not retrived");
		}
		if (!help.isExists("file1.txt", "/data/"))
		{
			Assert.fail("Retrieved file was deleted from ftp server");
		}
	}
	
	@Test
	public void testRemoveFilesByMask() throws Exception
	{
		
		help.removeFilesByMask("*generated*", "/css/");
		
		if (help.isExists("generated_123.css", "/css/"))
		{
			Assert.fail("File is not deleted by mask");
		}
		
		if (!help.isExists("some.css", "/css/"))
		{
			Assert.fail("After deletion by mask other files deleted also");
		}
		
		String mask = "*bla*";
		help.removeFilesByMask(mask, "/css/");
		String[] fds =
		{ "bla_1.c", "bla_2.c",
				"bla_3.c", "abla_css.c" };
		
		for (String f : fds)
		{
			if (help.isExists(f, "/css/"))
			{
				Assert.fail("File '" + f + "' is not deleted by mask '" + mask + "'");
			}
		}
	}
	
	@Test
	public void testIsExists() throws SocketException, IOException
	{
		Assert.assertTrue(help.isExists("run.exe", "/data/"));
		Assert.assertFalse(help.isExists("notexists.file", "/data/"));
	}
	
	@Test
	public void testGetFilesByMask() throws SocketException, IOException
	{
		List<FTPFile> actfil = help.getFilesByMask("bla*", "/css/");
		
		for (FTPFile f : actfil)
		{
			if (!f.getName().startsWith("bla"))
			{
				Assert.fail("Retrieved extra files than in mask");
			}
		}
	}
	
	@Test
	public void testRemoveFile() throws SocketException, IOException
	{
		help.removeFile("deletedfile.txt", "/rem/");
		
		Assert.assertFalse(help.isExists("deletedfile.txt", "/rem/"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUploadFile() throws SocketException, IOException
	{
		String file = "cmyk_colors.jpg";
		String rootPath = new File("").getAbsolutePath() + "/";
		File root = new File(rootPath);
		File f = null;
		try
		{
			String[] extensions =
			{ "jpg", };
			boolean recursive = true;
			
			Collection files = FileUtils.listFiles(root, extensions, recursive);
			
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
			
		}
		
		help.uploadFile(f.getAbsolutePath(), "/incoming/" + f.getName());
		
		Assert.assertTrue(help.isExists(f.getName(), "/incoming/"));
	}
	
}
