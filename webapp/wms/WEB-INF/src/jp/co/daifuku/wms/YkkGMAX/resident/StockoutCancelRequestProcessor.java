package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class StockoutCancelRequestProcessor extends BasicProcessor
{
	private String ailestno = "";
	
	public StockoutCancelRequestProcessor(String ailestno)
	{
		this.ailestno = ailestno;
	}

	protected String getProcedureName()
	{
		return "stockout_rm_cancel";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		String sqlString = "INSERT INTO FNGSET";
		sqlString += "(syoriflg,schno,motostno)";
		sqlString += "VALUES";
		sqlString += "("
				+ StringUtils
						.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
				+ ","
				+ StringUtils.surroundWithSingleQuotes(schno)
				+ ","
				+ StringUtils.surroundWithSingleQuotes(ailestno) + ")";

		DBHandler dbHandler = new DBHandler(conn);
		dbHandler.executeUpdate(sqlString);

	}

}
