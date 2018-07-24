package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.List;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.FinalProcedureStockoutStationSwitchEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class FinalProcedureStockoutStationSwitchRequestProcessor extends
		BasicProcessor
{

	private List list;
	
	public FinalProcedureStockoutStationSwitchRequestProcessor(List list)
	{
		this.list = list;
	}
	
	protected String getProcedureName()
	{
		return "station_switch_start";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for (int i = 0; i < list.size(); i++)
		{
			FinalProcedureStockoutStationSwitchEntity entity = (FinalProcedureStockoutStationSwitchEntity) list.get(i);
			String sqlString = "INSERT INTO FNGSET";
			sqlString += "(syoriflg,schno,from_retrieval_station,to_retrieval_station)";
			sqlString += "VALUES";
			sqlString += "("
					+ StringUtils
							.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(schno)
					+ ","
					+ StringUtils
							.surroundWithSingleQuotes(entity.getFromStationNo())
					+ ","
					+ StringUtils
							.surroundWithSingleQuotes(entity.getStationNo())
					+ ")";

			DBHandler dbHandler = new DBHandler(conn);
			dbHandler.executeUpdate(sqlString);
		}

	}

}
