package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class StockoutAutoRequestProcessor extends BasicProcessor
{
	private ArrayList autoList = null;
	private String userid = "";
	private String subdivide_flag = "";

	public StockoutAutoRequestProcessor(ArrayList autoList, String userid,
			String subdivide_flag)
	{
		this.autoList = autoList;
		this.userid = userid;
		this.subdivide_flag = subdivide_flag;
	}

	protected String getProcedureName()
	{
		return "normal_stockout_auto_setting";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{

		for (int i = 0; i < autoList.size(); i++)
		{
			StockoutEntity entity = (StockoutEntity) autoList.get(i);
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
					+ StringUtils.surroundWithSingleQuotes(subdivide_flag)
					+ "," + StringUtils.surroundWithSingleQuotes(userid) + ")";

			DBHandler dbHandler = new DBHandler(conn);
			dbHandler.executeUpdate(sqlString);
		}

	}

}
