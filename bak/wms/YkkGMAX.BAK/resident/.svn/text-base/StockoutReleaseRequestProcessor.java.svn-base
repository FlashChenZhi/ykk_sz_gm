package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutStartEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class StockoutReleaseRequestProcessor extends BasicProcessor
{
	private ArrayList stockoutStartList = null;

	public StockoutReleaseRequestProcessor(
			ArrayList stockoutStartList)
	{
		this.stockoutStartList = stockoutStartList;
	}
	
	protected String getProcedureName()
	{
		return "stockout_cancel";
	}
	
	protected void insertRecorderIntoFngset(Connection conn, String schno) throws YKKDBException, YKKSQLException
	{
		for(int i = 0 ; i < stockoutStartList.size() ; i++)
		{
			StockoutStartEntity entity = (StockoutStartEntity)stockoutStartList.get(i);
			String[] mckeyList = entity.getMckey().split(",");
			for(int j = 0 ; j < mckeyList.length ; j++)
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
						+ StringUtils
								.surroundWithSingleQuotes(entity.getSystemId())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(mckeyList[j])
						+ ")";

				DBHandler dbHandler = new DBHandler(conn);
				dbHandler.executeUpdate(sqlString);
			}
		}
		
	}

}
