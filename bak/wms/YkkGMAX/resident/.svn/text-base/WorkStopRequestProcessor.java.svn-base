package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKProcedureException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;

public class WorkStopRequestProcessor extends BasicProcessor
{
	private boolean holdData = false;
	public WorkStopRequestProcessor(boolean holdData)
	{
		this.holdData = holdData;
	}
	
	protected void callProcedure(Connection conn, String schno)
	throws YKKDBException, YKKSQLException, YKKProcedureException
	{
		DBHandler handler = new DBHandler(conn);

		List inputPairs = new ArrayList();
		handler.callProcedure(getProcedureName(), inputPairs);
	}
	
	protected String getProcedureName()
	{
		if(holdData)
		{
			return "task_force_offline";
		}
		else
		{
			return "task_normal_offline";		
		}
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
	}

}
