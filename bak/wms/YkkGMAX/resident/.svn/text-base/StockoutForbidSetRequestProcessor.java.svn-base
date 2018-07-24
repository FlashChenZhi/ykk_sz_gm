package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutForbidEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringPadder;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class StockoutForbidSetRequestProcessor extends BasicProcessor
{
	private StockoutForbidEntity entity = null;

	public StockoutForbidSetRequestProcessor(StockoutForbidEntity entity)
	{
		this.entity = entity;
	}

	protected String getProcedureName()
	{
		return "stockout_forbidden_setting";
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
				+ "," + StringUtils.surroundWithSingleQuotes(schno) + ","
				+ StringUtils.surroundWithSingleQuotes(entity.getItemCode());
		sqlString += ","
				+ StringUtils.surroundWithSingleQuotes(StringPadder.leftPad(
						entity.getTicketNoFrom(), "0", 10));
		sqlString += ","
				+ StringUtils.surroundWithSingleQuotes(StringPadder.leftPad(
						entity.getTicketNoTo(), "0", 10));
		sqlString += ","
				+ StringUtils.surroundWithSingleQuotes(entity
						.getStockInDatetimeFrom());
		sqlString += ","
				+ StringUtils.surroundWithSingleQuotes(entity
						.getStockInDatetimeTo());
		sqlString += ","
				+ StringUtils.surroundWithSingleQuotes(entity.getColorCode())
				+ ")";

		DBHandler dbHandler = new DBHandler(conn);
		dbHandler.executeUpdate(sqlString);

	}
}
