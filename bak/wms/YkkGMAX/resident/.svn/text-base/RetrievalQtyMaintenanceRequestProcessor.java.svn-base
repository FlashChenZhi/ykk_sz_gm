package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.RetrievalQtyMaintenanceEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class RetrievalQtyMaintenanceRequestProcessor extends BasicProcessor
{
	private ArrayList list = new ArrayList();

	public RetrievalQtyMaintenanceRequestProcessor(ArrayList list)
	{
		this.list = list;
	}

	protected String getProcedureName()
	{
		return "retrieval_qty_mod_start";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for (int i = 0; i < list.size(); i++)
		{
			RetrievalQtyMaintenanceEntity entity = (RetrievalQtyMaintenanceEntity) list
					.get(i);

			String sqlString = "INSERT INTO FNGSET";
			sqlString += "(syoriflg,schno,necessary_qty,retrieval_qty,retrieval_alloc_qty,nyusyusu,retrieval_plankey)";
			sqlString += "VALUES";
			sqlString += "("
					+ StringUtils
							.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(schno)
					+ ","
					+ String.valueOf(entity.getStockoutNecessaryQty())
					+ ","
					+ String
							.valueOf(entity.getOriginalManagementRetrievalQty())
					+ ","
					+ String.valueOf(entity.getOutQty())
					+ ","
					+ String.valueOf(entity.getManagementRetrievalQty())
					+ ","
					+ StringUtils.surroundWithSingleQuotes(entity
							.getRetrievalPlankey()) + ")";

			DBHandler dbHandler = new DBHandler(conn);
			dbHandler.executeUpdate(sqlString);
		}

	}

}
