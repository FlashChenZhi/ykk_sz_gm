package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.SystemIdSortable;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class StockoutDesignateRequestProcessor extends BasicProcessor
{
	private ArrayList manuList = null;
	private String userid = "";
	private String subdivide_flag = "";

	public StockoutDesignateRequestProcessor(ArrayList menuList, String userid,
			String subdivide_flag)
	{
		this.manuList = menuList;
		this.userid = userid;
		this.subdivide_flag = subdivide_flag;
	}

	protected String getProcedureName()
	{
		return "normal_stockout_manu_setting";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for (int i = 0; i < manuList.size(); i++)
		{
			StockoutEntity entity = (StockoutEntity) manuList.get(i);

			for (int j = 0; j < entity.getStockoutDetailList().size(); j++)
			{
				SystemIdSortable detailEntity = (SystemIdSortable) entity
						.getStockoutDetailList().get(j);
				String sqlString = "INSERT INTO FNGSET";
				sqlString += "(syoriflg,schno,retrieval_plankey,systemid,necessary_qty,retrieval_alloc_qty,subdivide_flag,userid)";
				sqlString += "VALUES";
				sqlString += "("
						+ StringUtils
								.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
						+ ","
						+ StringUtils.surroundWithSingleQuotes(schno)
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getRetrievalPlankey())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(detailEntity
								.getSystemId()) + ","
						+ entity.getStockoutNecessaryQty() + ","
						+ entity.getOutQty() + ","
						+ StringUtils.surroundWithSingleQuotes(subdivide_flag)
						+ "," + StringUtils.surroundWithSingleQuotes(userid)
						+ ")";

				DBHandler dbHandler = new DBHandler(conn);
				dbHandler.executeUpdate(sqlString);
			}
		}
	}

}
