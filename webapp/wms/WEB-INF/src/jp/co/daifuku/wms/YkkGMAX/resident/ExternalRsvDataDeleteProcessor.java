package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.List;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExternalStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKProcedureException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ExternalRsvDataDeleteProcessor extends BasicProcessor
{
	private String ip;
	private List dataList;
	
	public ExternalRsvDataDeleteProcessor(String ip, List dataList)
	{
		this.ip = ip;
		this.dataList = dataList;
	}

	protected String getProcedureName()
	{
		return "reservation_data_delete_start";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		ASRSInfoCentre centre = new ASRSInfoCentre(conn);

		String printerName = centre.getPrinterName(ip);

		String listKey;
		try
		{
			listKey = centre.generateLabelKey();
		} catch (YKKProcedureException ex)
		{

			YKKDBException dbEx = new YKKDBException();
			dbEx.setResourceKey("7200001");
			throw dbEx;
		}

		String sqlString = "INSERT INTO FNPRINTHEAD (listkey,proc_flag,printer_name,listtype,order_flag)VALUES("
				+ StringUtils.surroundWithSingleQuotes(listKey)
				+ ","
				+ StringUtils.surroundWithSingleQuotes("0")
				+ ","
				+ StringUtils.surroundWithSingleQuotes(printerName)
				+ ","
				+ StringUtils.surroundWithSingleQuotes("9")
				+ ","
				+ StringUtils.surroundWithSingleQuotes("0") + ")";

		DBHandler handler = new DBHandler(conn);
		handler.executeUpdate(sqlString, true);

		for (int i = 0; i < dataList.size(); i++)
		{
			ExternalStockoutEntity entity = (ExternalStockoutEntity) dataList.get(i);
			sqlString = "INSERT INTO FNPRINTBODY (listKey,range1,range2,range3,range4,range5,range6,range7)VALUES("
					+ StringUtils.surroundWithSingleQuotes(listKey)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(entity
							.getRetrievalNo())
					+ ","
					+ StringUtils
							.surroundWithSingleQuotes(entity.getItemCode())
					+ ","
					+ StringUtils.surroundWithSingleQuotes(DecimalFormat
							.getIntegerInstance().format(
									entity.getStockoutCount()))
					+ ","
					+ StringUtils.surroundWithSingleQuotes(DecimalFormat
							.getIntegerInstance().format(
									entity.getStockoutCount()))
					+ ","
					+ StringUtils.surroundWithSingleQuotes(StringUtils
							.formatDateFromDBToPage(entity
									.getWhenNextWorkBegin())
							+ " "
							+ StringUtils
									.formatStartTimingFlagFromDBToPage(entity
											.getWhenNextWorkBeginTiming()))
					+ ","
					+ StringUtils
							.surroundWithSingleQuotes(StringUtils
									.formatDateFromDBToPage(entity
											.getWhenThisWorkFinishInPlan())
									+ " "
									+ StringUtils
											.formatStartTimingFlagFromDBToPage(entity
													.getWhenThisWorkFinishInPlanTiming()))
					+ ","
					+ StringUtils.surroundWithSingleQuotes(entity
							.getExternalCode()) + ")";
			handler = new DBHandler(conn);
			handler.executeUpdate(sqlString, true);

			sqlString = "INSERT INTO FNGSET";
			sqlString += "(syoriflg,schno,retrieval_plankey,retrieval_qty, retrieval_alloc_qty, necessary_qty)";
			sqlString += "VALUES";
			sqlString += "("
					+ StringUtils
							.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(schno)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(entity
							.getRetrievalPlankey()) + ","
					+ String.valueOf(entity.getManagementRetrievalQty()) + ","
					+ String.valueOf(entity.getOutQty()) + ","
					+ String.valueOf(entity.getStockoutNecessaryQty()) + ")";

			DBHandler dbHandler = new DBHandler(conn);
			dbHandler.executeUpdate(sqlString);
		}
	}
}
