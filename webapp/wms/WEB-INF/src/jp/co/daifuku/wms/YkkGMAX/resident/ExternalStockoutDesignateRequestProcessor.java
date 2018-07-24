package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExternalStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.SystemIdSortable;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ExternalStockoutDesignateRequestProcessor extends BasicProcessor
{
	private ArrayList manuList = null;
	private String userid = "";

	public ExternalStockoutDesignateRequestProcessor(ArrayList menuList,
			String userid)
	{
		this.manuList = menuList;
		this.userid = userid;
	}

	protected String getProcedureName()
	{
		return "external_stockout_manu_setting";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for (int i = 0; i < manuList.size(); i++)
		{
			ExternalStockoutEntity entity = (ExternalStockoutEntity) manuList
					.get(i);
			for (int j = 0; j < entity.getExternalStockoutDetailList().size(); j++)
			{
				SystemIdSortable detailEntity = (SystemIdSortable) entity
						.getExternalStockoutDetailList().get(j);
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
						+ StringUtils.surroundWithSingleQuotes("0") + ","
						+ StringUtils.surroundWithSingleQuotes(userid) + ")";

				DBHandler dbHandler = new DBHandler(conn);
				dbHandler.executeUpdate(sqlString);
			}
		}
	}
}
