package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExternalStockoutStartEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ExternalStockoutReleaseRequestProcessor extends BasicProcessor
{

	private ArrayList externalStockoutStartList = null;

	public ExternalStockoutReleaseRequestProcessor(
			ArrayList externalStockoutStartList)
	{
		this.externalStockoutStartList = externalStockoutStartList;
	}

	protected String getProcedureName()
	{
		return "stockout_cancel";
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
				sqlString += "(syoriflg,schno,systemid,mckey)";
				sqlString += "VALUES";
				sqlString += "("
						+ StringUtils
								.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
						+ ","
						+ StringUtils.surroundWithSingleQuotes(schno)
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getSystemId()) + ","
						+ StringUtils.surroundWithSingleQuotes(mckeyList[j])
						+ ")";

				DBHandler dbHandler = new DBHandler(conn);
				dbHandler.executeUpdate(sqlString);
			}
		}

	}

}
