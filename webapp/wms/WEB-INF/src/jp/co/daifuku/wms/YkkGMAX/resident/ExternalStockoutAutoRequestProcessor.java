package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExternalStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ExternalStockoutAutoRequestProcessor extends BasicProcessor
{

	private ArrayList autoList = null;
	private String userid = "";

	public ExternalStockoutAutoRequestProcessor(ArrayList autoList,
			String userid)
	{
		this.autoList = autoList;
		this.userid = userid;
	}

	protected String getProcedureName()
	{
		return "external_stockout_auto_setting";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for (int i = 0; i < autoList.size(); i++)
		{
			ExternalStockoutEntity entity = (ExternalStockoutEntity) autoList
					.get(i);
			String sqlString = "INSERT INTO FNGSET";
			sqlString += "(syoriflg,schno,retrieval_plankey,necessary_qty,retrieval_alloc_qty,subdivide_flag,userid)";
			sqlString += "VALUES";
			sqlString += "("
					+ StringUtils
							.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(schno)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(entity
							.getRetrievalPlankey()) + ","
					+ entity.getStockoutNecessaryQty() + ","
					+ entity.getOutQty() + ","
					+ StringUtils.surroundWithSingleQuotes("0") + ","
					+ StringUtils.surroundWithSingleQuotes(userid) + ")";

			DBHandler dbHandler = new DBHandler(conn);
			dbHandler.executeUpdate(sqlString);
		}
	}
}
