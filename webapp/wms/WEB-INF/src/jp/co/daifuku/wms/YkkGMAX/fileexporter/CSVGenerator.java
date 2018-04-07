package jp.co.daifuku.wms.YkkGMAX.fileexporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKCommonException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class CSVGenerator
{
	private IExportable csvInfo = null;
	private boolean writeLogFlag = true;
	// private int MAX_LINE = 65535;
	private String root = StringUtils.EMPTY;

	public CSVGenerator(IExportable csvInfo, String root)
	{
		this(csvInfo, root, true);
	}

	public CSVGenerator(IExportable csvInfo, String root, boolean writeLogFlag)
	{
		this.csvInfo = csvInfo;
		this.root = root;
		this.writeLogFlag = writeLogFlag;
	}

	private String gen(ResultSet resultSet, String path, Message message)
			throws YKKCommonException, YKKSQLException
	{
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

		try
		{
			File file = new File(path);

			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "GB2312");
			bw = new BufferedWriter(osw);

			bw.write(csvInfo.generateHead());
			bw.write("\r\n");
			int count = 0;
			while (resultSet.next())
			{
				count++;
				if (count <= csvInfo.getMaxLine())
				{
					String line = csvInfo
							.makeLine(new ResultSetProxy(resultSet));
					bw.write(line);
					bw.write("\r\n");
				} else
				{
					break;
				}

			}
			bw.flush();
			bw.close();
			fos.close();
			osw.close();

			if (count == 0)
			{
				message.setMsgResourceKey("7000030");
				path = StringUtils.EMPTY;
			}
			// else if (count > csvInfo.getMaxLine())
			// {
			// message.setMsgResourceKey("7000031" + "\t" + count + "\t"
			// + csvInfo.getMaxLine());
			// }
			return path;
		} catch (IOException e)
		{
			DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
			YKKCommonException ex = new YKKCommonException();
			ex.setResourceKey("7500002");
			throw ex;
		} catch (SQLException e)
		{
			DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
			YKKSQLException ex = new YKKSQLException();
			ex.setResourceKey("7300002");
			throw ex;
		} finally
		{
			try
			{
				if (bw != null)
				{
					bw.close();
				}
				if (fos != null)
				{
					fos.close();
				}
				if (osw != null)
				{
					osw.close();
				}
			} catch (IOException e)
			{
				DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
				YKKCommonException ex = new YKKCommonException();
				ex.setResourceKey("7500003");
				throw ex;
			}
		}
	}

	private String generatePath(String root)
	{
		String name = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
		String path = StringUtils.EMPTY;

		String osName = System.getProperties().getProperty("os.name");
		if (osName.indexOf("Windows") != -1)
		{
			path = root + "\\" + name + ".csv";
		} else
		{
			path = root + "/" + name + ".csv";
		}
		return path;
	}

	public String generateFile(Message message) throws YKKSQLException,
			YKKDBException, YKKCommonException
	{
		File csvRoot = new File(root);
		if (!csvRoot.exists())
		{
			csvRoot.mkdir();
		}

		String path = generatePath(root);

		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try
		{
			conn = ConnectionManager.getConnection();
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			String queryString = csvInfo.getNativeSQL();
			if (writeLogFlag)
			{
				DebugPrinter.print(DebugLevel.DEBUG, queryString);
			}
			resultSet = statement.executeQuery(queryString);

			return gen(resultSet, path, message);
		} catch (SQLException e)
		{
			DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
			YKKSQLException ex = new YKKSQLException();
			ex.setResourceKey("7300003");
			throw ex;
		} finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
				}

				if (resultSet != null)
				{
					resultSet.close();
				}
				if (statement != null)
				{
					statement.close();
				}
			} catch (SQLException e)
			{
				DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
				YKKSQLException ex = new YKKSQLException();
				ex.setResourceKey("7300003");
				throw ex;
			}
		}
	}
}
