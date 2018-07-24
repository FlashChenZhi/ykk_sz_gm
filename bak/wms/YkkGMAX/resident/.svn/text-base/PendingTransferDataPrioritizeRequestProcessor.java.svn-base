package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.PendingTransferDataCencelOrPrioritizeEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class PendingTransferDataPrioritizeRequestProcessor extends
		BasicProcessor
{
	private ArrayList pendingTransferDataCencelOrPrioritizeList = null;
	private String priorityDivision = "";

	public PendingTransferDataPrioritizeRequestProcessor(
			ArrayList pendingTransferDataCencelOrPrioritizeList,
			String priorityDivision)
	{
		this.pendingTransferDataCencelOrPrioritizeList = pendingTransferDataCencelOrPrioritizeList;
		this.priorityDivision = priorityDivision;
	}

	protected String getProcedureName()
	{
		return "set_trans_data_priority";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for (int i = 0; i < pendingTransferDataCencelOrPrioritizeList.size(); i++)
		{
			PendingTransferDataCencelOrPrioritizeEntity entity = (PendingTransferDataCencelOrPrioritizeEntity) pendingTransferDataCencelOrPrioritizeList
					.get(i);
			String[] mckeyList = entity.getMcKey().split(",");
			for (int j = 0; j < mckeyList.length; j++)
			{
				String sqlString = "INSERT INTO FNGSET";
				sqlString += "(syoriflg,schno,mckey,retrieval_station)";
				sqlString += "VALUES";
				sqlString += "("
						+ StringUtils
								.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
						+ ","
						+ StringUtils.surroundWithSingleQuotes(schno)
						+ ","
						+ StringUtils.surroundWithSingleQuotes(mckeyList[j])
						+ ","
						+ StringUtils
								.surroundWithSingleQuotes(priorityDivision)
						+ ")";

				DBHandler dbHandler = new DBHandler(conn);
				dbHandler.executeUpdate(sqlString);
			}
		}

	}

}
