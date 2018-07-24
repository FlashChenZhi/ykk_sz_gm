package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.DBHandler.InputPair;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKProcedureException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;

public class EmptyBucketAddModeAssemblySettingRequestProcessor extends
	BasicProcessor
{
    private String mode = "";

    public EmptyBucketAddModeAssemblySettingRequestProcessor(String mode)
    {
	this.mode = mode;
    }

    public void afterProcedure(Connection conn) throws YKKSQLException
    {
    }

    protected void callProcedure(Connection conn, String schno)
	    throws YKKDBException, YKKSQLException, YKKProcedureException
    {
	DBHandler handler = new DBHandler(conn);

	List inputPairs = new ArrayList();
	inputPairs.add(new InputPair(1, mode));
	handler.callProcedure(getProcedureName(), inputPairs);
    }

    protected String getProcedureName()
    {
	return "assembly_station_mode_change";
    }

    protected void insertRecorderIntoFngset(Connection conn, String schno)
	    throws YKKDBException, YKKSQLException
    {
    }

}
