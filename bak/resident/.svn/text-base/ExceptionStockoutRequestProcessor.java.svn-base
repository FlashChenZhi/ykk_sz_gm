package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.DBHandler.InputPair;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExceptionStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.SystemIdSortable;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKProcedureException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ExceptionStockoutRequestProcessor extends BasicProcessor
{
	private ExceptionStockoutEntity exceptionStockoutEntity = null;

	private String userid = "";

	private String stockoutMode = "";

	public ExceptionStockoutRequestProcessor(
			ExceptionStockoutEntity exceptionStockoutEntity, String userid,
			String stockoutMode)
	{
		this.exceptionStockoutEntity = exceptionStockoutEntity;
		this.userid = userid;
		this.stockoutMode = stockoutMode;
	}

	protected void callProcedure(Connection conn, String schno)
			throws YKKDBException, YKKSQLException, YKKProcedureException
	{
		DBHandler handler = new DBHandler(conn);

		List inputPairs = new ArrayList();
		inputPairs.add(new InputPair(1, schno));
		inputPairs.add(new InputPair(2,stockoutMode));
		handler.callProcedure(getProcedureName(), inputPairs);
	}

	protected String getProcedureName()
	{
		return "exceptional_stockout_start";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for (int i = 0; i < exceptionStockoutEntity
				.getExceptionStockoutDetailList().size(); i++)
		{
			SystemIdSortable entity = (SystemIdSortable) exceptionStockoutEntity
					.getExceptionStockoutDetailList().get(i);
			String sqlString = "INSERT INTO FNGSET";
			sqlString += "(syoriflg,schno,systemid,endstno,userid)";
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
					+ StringUtils
							.surroundWithSingleQuotes(exceptionStockoutEntity
									.getEndStation()) + ","
					+ StringUtils.surroundWithSingleQuotes(userid) + ")";

			DBHandler dbHandler = new DBHandler(conn);
			dbHandler.executeUpdate(sqlString);
		}

	}

}
