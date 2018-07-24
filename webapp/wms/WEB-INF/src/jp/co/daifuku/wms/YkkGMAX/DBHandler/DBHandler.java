package jp.co.daifuku.wms.YkkGMAX.DBHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKProcedureException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class DBHandler
{

	private final Connection conn;

	private RecordSet recordSet = null;

	public DBHandler(Connection conn)
	{
		this.conn = conn;
		recordSet = new RecordSet();
	}

	public ResultPairList callProcedure(String name, List inputPairs)
			throws YKKSQLException, YKKProcedureException
	{
		return this.callProcedure(name, inputPairs, null);
	}

	public ResultPairList callProcedure(String name, List inputPairs,
			List outputPairs) throws YKKSQLException, YKKProcedureException

	{
		if (outputPairs == null)
		{
			outputPairs = new ArrayList();
		}

		String statement = "?";
		for (int i = 0; i < inputPairs.size() + 1; ++i)
		{
			statement += ",?";
		}

		statement = "{call " + name + "(" + statement + ")}";
		int returnCode = 0;
		String returnMessage = "";
		ResultPairList resultList = new ResultPairList();
		try
		{
			CallableStatement cs = conn.prepareCall(statement);
			Iterator it = inputPairs.iterator();
			int count = 0;
			while (it.hasNext())
			{
				count++;
				InputPair pair = (InputPair) it.next();
				switch (pair.getType())
				{
				case Types.INTEGER:
					cs.setInt(pair.getIndex(), pair.getInteger());
					break;
				case Types.LONGVARCHAR:
					cs.setString(pair.getIndex(), pair.getString());
					break;
				default:
					DebugPrinter
							.print(DebugLevel.ERROR,
									"error occurs when setting parameters for storage procedure. unknown type.");
					YKKSQLException ex = new YKKSQLException();
					ex.setResourceKey("7200004");
					throw ex;

				}
			}
			cs.setInt(count + 1, returnCode);
			cs.setString(count + 2, returnMessage);

			it = outputPairs.iterator();
			while (it.hasNext())
			{
				OutputPair pair = (OutputPair) it.next();
				cs.registerOutParameter(pair.getIndex(), pair.getType());
			}
			cs.registerOutParameter(count + 1, Types.INTEGER);
			cs.registerOutParameter(count + 2, Types.VARCHAR);

			cs.executeUpdate();

			returnCode = cs.getInt(count + 1);
			returnMessage = cs.getString(count + 2);

			if (returnCode != 0)
			{
				YKKProcedureException pEx = new YKKProcedureException();
				pEx.setResourceKey("7200004");
				pEx.setReturnCode(String.valueOf(returnCode));
				pEx.setReturnMessage(returnMessage);
				throw pEx;
			}

			it = outputPairs.iterator();
			count = 0;
			while (it.hasNext())
			{
				count++;
				OutputPair pair = (OutputPair) it.next();

				switch (pair.getType())
				{
				case Types.INTEGER:
					resultList.add(new ResultPair(pair.getIndex(), cs
							.getInt(pair.getIndex())));
					break;
				case Types.LONGVARCHAR:
					resultList.add(new ResultPair(pair.getIndex(), cs
							.getString(pair.getIndex())));
					break;
				default:
					DebugPrinter
							.print(
									DebugLevel.ERROR,
									"error occurs when getting parameters from storage procedure results. unknown type.");
					YKKSQLException ex = new YKKSQLException();
					ex.setResourceKey("7200004");
					throw ex;
				}
			}
		}
		catch (SQLException e)
		{
			DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
			YKKSQLException ex = new YKKSQLException();
			ex.setResourceKey("7200004");
			throw ex;
		}

		return resultList;
	}

	public int executeQuery(String queryString) throws YKKSQLException
	{
		return executeQuery(queryString, true);
	}

	public int executeQuery(String queryString, boolean writeLogFlag)
			throws YKKSQLException
	{
		return executeQuery(queryString, 1, 0, writeLogFlag);
	}

	public int executeQuery(String queryString, int beginningRow,
			int expectedResultCount) throws YKKSQLException
	{
		return executeQuery(queryString, beginningRow, expectedResultCount,
				true);
	}

	public int executeQuery(String queryString, int beginningRow,
			int expectedResultCount, boolean writeLogFlag)
			throws YKKSQLException
	{
		recordSet.getRowList().clear();
		recordSet.setActualCount(0);
		recordSet.setTotalCount(0);

		expectedResultCount = expectedResultCount < 0 ? 0 : expectedResultCount;

		Statement statement = null;
		ResultSet resultSet = null;
		int actualResultCount = 0;

		try
		{
			// <en> Read Only and Scrollable </en>
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			if (writeLogFlag)
			{
				DebugPrinter.print(DebugLevel.DEBUG, queryString);
			}
			resultSet = statement.executeQuery(queryString);

			if (resultSet == null)
			{
				DebugPrinter.print(DebugLevel.DEBUG,
						"ResultSet is null, this should never happen.");
				recordSet.setActualCount(0);
				recordSet.setTotalCount(0);
				return 0;
			}

			if (beginningRow > 1)
			{
				if (resultSet.absolute(beginningRow - 1) == false)
				{
					YKKSQLException ex = new YKKSQLException();
					ex.setResourceKey("7300003");
					throw ex;
				}
			}

			while (resultSet.next())
			{
				// <en> Get row data </en>
				RecordSetRow row = new RecordSetRow();

				ResultSetMetaData rsmd = resultSet.getMetaData();
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
				{
					// <en> Get column data </en>
					String colName = StringUtils.formatStringFromDatabase(rsmd
							.getColumnName(i));
					String colValue = StringUtils
							.formatStringFromDatabase(resultSet.getString(i));
					RecordSetColumn col = new RecordSetColumn(colName, colValue);
					row.addColumn(col);
				}
				recordSet.addRow(row);

				// <en> Selected record count </en>
				actualResultCount++;
				if (actualResultCount > 0 && expectedResultCount > 0
						&& actualResultCount >= expectedResultCount)
				{
					// <en> Break when fetched directed record count </en>
					break;
				}
			}

			recordSet.setActualCount(actualResultCount);
			// resultSet.last();
			recordSet.setTotalCount(resultSet.getRow());
		}
		catch (SQLException e)
		{
			DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
			YKKSQLException ex = new YKKSQLException();
			ex.setResourceKey("7300002");
			throw ex;
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (SQLException e)
			{
				DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
				YKKSQLException ex = new YKKSQLException();
				ex.setResourceKey("7300003");
				throw ex;
			}
		}
		return actualResultCount;
	}

	public void executeUpdate(String strSQL) throws YKKSQLException
	{
		executeUpdate(strSQL, true);
	}

	public int executeUpdate(String strSQL, boolean writeLogFlag)
			throws YKKSQLException
	{
		Statement statement = null;
		try
		{
			statement = conn.createStatement();
			if (writeLogFlag)
			{
				DebugPrinter.print(DebugLevel.DEBUG, strSQL);
			}
			return statement.executeUpdate(strSQL);
		}
		catch (SQLException e)
		{
			DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
			YKKSQLException ex = new YKKSQLException();
			ex.setResourceKey("7300002");
			throw ex;
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (SQLException e)
			{
				DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
				YKKSQLException ex = new YKKSQLException();
				ex.setResourceKey("7300003");
				throw ex;
			}
		}
	}

	public RecordSet getRecordSet()
	{
		return recordSet;
	}
}
