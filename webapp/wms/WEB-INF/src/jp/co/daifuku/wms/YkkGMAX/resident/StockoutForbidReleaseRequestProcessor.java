package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutForbidEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class StockoutForbidReleaseRequestProcessor extends BasicProcessor
{
	private StockoutForbidEntity entity = null;

	public StockoutForbidReleaseRequestProcessor(StockoutForbidEntity entity)
	{
		this.entity = entity;
	}

	protected String getProcedureName()
	{
		return "stockout_release_setting";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		String sqlString = "INSERT INTO FNGSET";
		sqlString += "(syoriflg,schno,zaikey,from_ticket_no,to_ticket_no,from_stock_datetime,to_stock_datetime,color_code)";
		sqlString += "VALUES";
		sqlString += "("
				+ StringUtils
						.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
				+ ","
				+ StringUtils.surroundWithSingleQuotes(schno)
				+ ","
				+ StringUtils.surroundWithSingleQuotes(entity.getItemCode())
				+ ","
				+ StringUtils
						.surroundWithSingleQuotes(entity.getTicketNoFrom())
				+ ","
				+ StringUtils.surroundWithSingleQuotes(entity.getTicketNoTo())
				+ ","
				+ StringUtils.surroundWithSingleQuotes(entity
						.getStockInDatetimeFrom())
				+ ","
				+ StringUtils.surroundWithSingleQuotes(entity
						.getStockInDatetimeTo()) + ","
				+ StringUtils.surroundWithSingleQuotes(entity.getColorCode())
				+ ")";

		DBHandler dbHandler = new DBHandler(conn);
		dbHandler.executeUpdate(sqlString);

	}
}
