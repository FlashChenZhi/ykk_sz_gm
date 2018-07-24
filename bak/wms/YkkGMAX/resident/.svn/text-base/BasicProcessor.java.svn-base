package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.DBHandler.InputPair;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKProcedureException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public abstract class BasicProcessor
{
    private String scheduleNo;

    public void afterProcedure(Connection conn) throws YKKSQLException
    {
	if (!StringUtils.IsNullOrEmpty(scheduleNo))
	{
	    String sqlString = "UPDATE fngset SET syoriflg = '1', sakuseihiji = "
		    + StringUtils
			    .surroundWithSingleQuotes(org.apache.commons.lang.time.DateFormatUtils
				    .format(Calendar.getInstance().getTime(),
					    "yyyyMMddHHmmss"))
		    + " WHERE schno = "
		    + StringUtils.surroundWithSingleQuotes(scheduleNo);
	    DBHandler dbHandler = new DBHandler(conn);
	    dbHandler.executeUpdate(sqlString);
	}
    }

    protected void callProcedure(Connection conn, String schno)
	    throws YKKDBException, YKKSQLException, YKKProcedureException
    {
	DBHandler handler = new DBHandler(conn);

	List inputPairs = new ArrayList();
	inputPairs.add(new InputPair(1, schno));
	handler.callProcedure(getProcedureName(), inputPairs);
    }

    protected abstract String getProcedureName();

    protected abstract void insertRecorderIntoFngset(Connection conn,
	    String schno) throws YKKDBException, YKKSQLException;

    public void run(Connection conn, String scheduleNo) throws YKKDBException,
	    YKKSQLException, YKKProcedureException
    {
	this.scheduleNo = scheduleNo;
	insertRecorderIntoFngset(conn, scheduleNo);
	callProcedure(conn, scheduleNo);
    }
}
