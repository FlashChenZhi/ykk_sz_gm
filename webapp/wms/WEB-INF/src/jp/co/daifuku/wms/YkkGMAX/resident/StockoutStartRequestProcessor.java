package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutStartEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class StockoutStartRequestProcessor extends BasicProcessor
{
	private ArrayList stockoutStartList = null;

	private String retrievalSt = "";

	private String forcePickingFlag = "";

	public StockoutStartRequestProcessor(ArrayList stockoutStartList,
			String retrievalSt, boolean isAuto)
	{
		this.stockoutStartList = stockoutStartList;
		this.retrievalSt = retrievalSt;
		if (isAuto)
		{
			this.forcePickingFlag = "0";
		}
		else
		{
			this.forcePickingFlag = "1";
		}
	}

	protected String getProcedureName()
	{
		return "normal_stockout_start";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for (int i = 0; i < stockoutStartList.size(); i++)
		{
			StockoutStartEntity entity = (StockoutStartEntity) stockoutStartList
					.get(i);
			String[] mckeyList = entity.getMckey().split(",");
			for (int j = 0; j < mckeyList.length; j++)
			{
				String sqlString = "INSERT INTO FNGSET";
				sqlString += "(syoriflg,schno,systemid,mckey,retrieval_station,force_picking_flag)";
				sqlString += "VALUES";
				sqlString += "("
						+ StringUtils
								.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
						+ ","
						+ StringUtils.surroundWithSingleQuotes(schno)
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getSystemId())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(mckeyList[j])
						+ ","
						+ StringUtils.surroundWithSingleQuotes(retrievalSt)
						+ ","
						+ StringUtils
								.surroundWithSingleQuotes(forcePickingFlag)
						+ ")";

				DBHandler dbHandler = new DBHandler(conn);
				dbHandler.executeUpdate(sqlString);
			}
		}

	}

}
