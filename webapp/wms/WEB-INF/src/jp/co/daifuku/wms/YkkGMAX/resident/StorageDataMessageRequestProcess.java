package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKProcedureException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;

public class StorageDataMessageRequestProcess extends BasicProcessor
{
	protected void callProcedure(Connection conn, String schno)
	throws YKKDBException, YKKSQLException, YKKProcedureException
	{
		DBHandler handler = new DBHandler(conn);

		List inputPairs = new ArrayList();
		handler.callProcedure(getProcedureName(), inputPairs);
	}
	
	protected String getProcedureName()
	{
		return "stock_data_message_send";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno) throws YKKDBException, YKKSQLException
	{
	}

}
