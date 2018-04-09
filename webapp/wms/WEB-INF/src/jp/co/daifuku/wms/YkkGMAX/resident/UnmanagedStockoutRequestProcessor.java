package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.SystemIdSortable;
import jp.co.daifuku.wms.YkkGMAX.Entities.UnmanagedStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class UnmanagedStockoutRequestProcessor extends BasicProcessor
{
	private UnmanagedStockoutEntity unmanagedStockoutEntity = null;
	private String userid = "";

	public UnmanagedStockoutRequestProcessor(
			UnmanagedStockoutEntity unmanagedStockoutEntity, String userid)
	{
		this.unmanagedStockoutEntity = unmanagedStockoutEntity;
		this.userid = userid;
	}

	protected String getProcedureName()
	{
		return "unmanaged_stockout_start";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for(int i = 0 ; i < unmanagedStockoutEntity
				.getUnmanagedStockoutDetailList().size() ; i++)
		{
			SystemIdSortable entity = (SystemIdSortable)unmanagedStockoutEntity
			.getUnmanagedStockoutDetailList().get(i);
			String sqlString = "INSERT INTO FNGSET";
			sqlString += "(syoriflg,schno,systemid,endstno,userid)";
			sqlString += "VALUES";
			sqlString += "("
					+ StringUtils
							.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(schno)
					+ ","
					+ StringUtils
							.surroundWithSingleQuotes(entity.getSystemId())
					+ ","
					+ StringUtils
							.surroundWithSingleQuotes(unmanagedStockoutEntity
									.getEndStation()) 
					+ ","
					+ StringUtils.surroundWithSingleQuotes(userid)
					+ ")";

			DBHandler dbHandler = new DBHandler(conn);
			dbHandler.executeUpdate(sqlString);

		}
	}

}
