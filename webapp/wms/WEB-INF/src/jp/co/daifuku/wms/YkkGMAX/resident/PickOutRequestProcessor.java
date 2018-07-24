package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkMaintenancePopupEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class PickOutRequestProcessor extends BasicProcessor
{
	private WorkMaintenancePopupEntity entity = null;

	public PickOutRequestProcessor(
			WorkMaintenancePopupEntity entity)
	{
		this.entity = entity;
	}
	
	protected String getProcedureName()
	{
		return "task_export_start";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		String sqlString = "INSERT INTO FNGSET";
		sqlString += "(syoriflg,schno,mckey)";
		sqlString += "VALUES";
		sqlString += "("
				+ StringUtils
						.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
				+ ","
				+ StringUtils.surroundWithSingleQuotes(schno)
				+ ","
				+ StringUtils.surroundWithSingleQuotes(entity
						.getMckey()) + ")";

		DBHandler dbHandler = new DBHandler(conn);
		dbHandler.executeUpdate(sqlString);

	}
	
}
