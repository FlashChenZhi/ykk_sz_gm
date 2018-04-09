package com.worgsoft.ykk.persistence;


import com.worgsoft.util.logging.AppLogger;

import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2008-11-12
 * Time: 16:54:15
 * To change this template use File | Settings | File Templates.
 */
public class SqlExecutor
{
    private static boolean autoWriteLogFlag = true;

    public static boolean isAutoWriteLog()
    {
        return autoWriteLogFlag;
    }

    public static void setAutoWriteLog(boolean autoWriteLogFlag)
    {
        SqlExecutor.autoWriteLogFlag = autoWriteLogFlag;
    }

    private final Connection conn;

    private RecordSet recordSet = null;

    public SqlExecutor(Connection conn)
    {
        this.conn = conn;
        recordSet = new RecordSet();
    }

    public int executeQuery(String queryString) throws SQLException
    {
        return executeQuery(queryString, autoWriteLogFlag);
    }

    public int executeQuery(String queryString, boolean writeLogFlag)
            throws SQLException

    {
        return executeQuery(queryString, 1, 0, writeLogFlag);
    }

    public int executeQuery(String queryString, int beginningRow,
                            int expectedResultCount) throws SQLException
    {
        return executeQuery(queryString, beginningRow, expectedResultCount,
                autoWriteLogFlag);
    }

    public int executeQuery(String queryString, int beginningRow,
                            int expectedResultCount, boolean writeLogFlag) throws SQLException
    {
        recordSet.getRowList().clear();
        recordSet.setActualCount(0);

        expectedResultCount = expectedResultCount < 0 ? 0 : expectedResultCount;
        Statement statement = null;
        ResultSet resultSet = null;
        int actualResultCount = 0;

        try
        {
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(queryString);
            if (writeLogFlag)
            {
               AppLogger.logDebugMessage(queryString);
            }

            if (beginningRow > 1)
            {
                // if (resultSet.absolute(beginningRow - 1) == false)
                // {
                // YKKSQLException ex = new YKKSQLException();
                // ex.setResourceKey("7300003");
                // throw ex;
                // }
            }

            while (resultSet.next())
            {
                RecordSetRow row = new RecordSetRow();

                ResultSetMetaData rsmd = resultSet.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    String colName = rsmd.getColumnName(i);
                    String colValue = resultSet.getString(i);
                    RecordSetColumn col = new RecordSetColumn(colName, colValue);
                    row.addColumn(col);
                }
                recordSet.addRow(row);

                actualResultCount++;
                if (actualResultCount > 0 && expectedResultCount > 0
                        && actualResultCount >= expectedResultCount)
                {
                    break;
                }
            }

            recordSet.setActualCount(actualResultCount);
        }
        finally
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
        return actualResultCount;
    }

    public void executeUpdate(String updateString) throws SQLException
    {
        executeUpdate(updateString, autoWriteLogFlag);
    }

    public void executeUpdate(String updateString, boolean writeLogFlag)
            throws SQLException
    {
        Statement statement = null;
        try
        {
            statement = conn.createStatement();
            statement.executeUpdate(updateString);
            if (writeLogFlag)
            {
                AppLogger.logDebugMessage(updateString);
            }
        }
        finally
        {
            if (statement != null)
            {
                statement.close();
            }
        }
    }

    public RecordSet getRecordSet()
    {
        return recordSet;
    }
}
