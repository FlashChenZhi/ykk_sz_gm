package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExternalStockoutStartEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ExternalStockoutStartRequestProcessor extends BasicProcessor
{

	private ArrayList externalStockoutStartList = null;

	private String retrievalSt = "";

	private String forcePickingFlag = "";

	public ExternalStockoutStartRequestProcessor(
			ArrayList externalStockoutStartList, String retrievalSt,
			boolean isAuto)
	{
		this.externalStockoutStartList = externalStockoutStartList;
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
		return "external_stockout_start";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for (int i = 0; i < externalStockoutStartList.size(); i++)
		{
			ExternalStockoutStartEntity entity = (ExternalStockoutStartEntity) externalStockoutStartList
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
