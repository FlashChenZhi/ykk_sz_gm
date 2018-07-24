package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKProcedureException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;

public class AfterRservationDataDeleteRequestProcessor extends BasicProcessor
{

	protected String getProcedureName()
	{
		return "after_reservation_data_delete";
	}

    public void afterProcedure(Connection conn) throws YKKSQLException
    {
    }
    
	protected void callProcedure(Connection conn, String schno)
			throws YKKDBException, YKKSQLException, YKKProcedureException
	{
		DBHandler handler = new DBHandler(conn);

		List inputPairs = new ArrayList();
		handler.callProcedure(getProcedureName(), inputPairs);
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
	}

}
