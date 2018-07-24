package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.List;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.FlatStockoutDetailEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.FlatStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class FlatStockoutRequestProcessor extends BasicProcessor
{
	private FlatStockoutEntity entity = null;

	private List list = null;

	private String userid = "";

	public FlatStockoutRequestProcessor(FlatStockoutEntity entity, List list,
			String userid)
	{
		this.entity = entity;
		this.list = list;
		this.userid = userid;
	}

	protected String getProcedureName()
	{
		return "flat_stockout_start";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for (int i = 0; i < list.size(); i++)
		{
			FlatStockoutDetailEntity en = (FlatStockoutDetailEntity) list
					.get(i);

			String sqlString = "INSERT INTO FNGSET";
			sqlString += "(syoriflg,schno,ticket_no,nyusyusu,zaikosu,skanosu,retrieval_plankey,retrieval_alloc_qty,userid)";
			sqlString += "VALUES";
			sqlString += "("
					+ StringUtils
							.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(schno)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(en.getTicketNo())
					+ ","
					+ StringUtils.surroundWithSingleQuotes(String.valueOf(en
							.getStockoutCount()))
					+ ","
					+ StringUtils.surroundWithSingleQuotes(String.valueOf(en
							.getInstockCount()))
					+ ","
					+ StringUtils.surroundWithSingleQuotes(String.valueOf(en
							.getStorageCount()))
					+ ","
					+ StringUtils.surroundWithSingleQuotes(entity
							.getRetrievalPlankey())
					+ ","
					+ StringUtils.surroundWithSingleQuotes(String
							.valueOf(entity.getOutQty())) + ","
					+ StringUtils.surroundWithSingleQuotes(userid) + ")";

			DBHandler dbHandler = new DBHandler(conn);
			dbHandler.executeUpdate(sqlString);

		}

	}
}
