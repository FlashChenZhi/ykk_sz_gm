//#CM697096
//$Id: DefineReportDataSCH.java,v 1.2 2006/11/13 06:03:11 suresh Exp $

//#CM697097
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;

//#CM697098
/**
 * Designer : T.Yamashita <BR>
 * Maker : T.Yamashita   <BR>
 * <BR>
 * Allow the <CODE>DefineReportDataSCH</CODE>class to execute the process for setting each field item to report its data.<BR>
 * Set Enabled/Disabled, Length, and Position for each field item of a report file by introduced package.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for setting the environment.(<CODE>startSCH(Connection conn, Parameter[] startParams)</CODE>method)<BR>
 * <DIR>
 * Clicking on Add button in the second screen of the screen for reporting data (setting of field items) starts-up this.<BR>
 * Reflect the value set in the screen ( Enabled, Disabled, Length, Position) on the Environment setting file (EnvironmentInformation).<BR>
 * <BR>
 * </DIR>
 * <BR>
 * 2.Initial Display Process(<CODE>initFind(Connection conn, Parameter searchParam)</CODE> method)<BR>
 * <DIR>
 * Execute the process for initial display of buttons in the Report Data screen (setting of field items).<BR>
 * Referring to the System definition table(WARENAVI_SYSTEM), display only radio button of the Report data with package flag "1: Available".<BR>
 * <BR>
 * </DIR>
 * <BR>
 * 3.Input check process(<CODE>check(Connection conn, Parameter checkParam)</CODE> method)<BR>
 * <DIR>
 * Check the Worker Code, password, and privilege.<BR>
 * </DIR>
 * <BR>
 * 5.Process of checking for shifting to the next screen invokes this.<BR>
 * <BR>
 * 4.Process of checking for shifting to the next screen (<CODE>nextCheck(Connection conn, Parameter checkParam)</CODE> method)<BR>
 * <DIR>
 * Check the Worker Code, password, and privilege.<BR>
 * </DIR>
 * <BR>
 * 5.Process for initial display of the second screen (<CODE>query(Connection conn, Parameter searchParam)</CODE> method)<BR>
 * <DIR>
 * Obtain a value of the field item related to the package selected via the original screen, from the Environment setting file (EnvironmentInformation).<BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/24</TD><TD>T.Yamashita</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:11 $
 * @author  $Author: suresh $
 */
public class DefineReportDataSCH extends AbstractSystemSCH
{

	//#CM697099
	// Class fields --------------------------------------------------
	//#CM697100
	/** Field item */

	private final String Chk_Off = "0";

	//#CM697101
	/** Field item */

	private final String Chk_On = "1";

	//#CM697102
	/** Return a mandatory/enabled flag for each field item. */

	private String[] ItemReq = new String[29];

	//#CM697103
	/** Length of each field item */

	private int[] ItemFigure = new int[29];

	//#CM697104
	/** Max length of each field item */

	private int[] ItemMaxFigure = new int[29];

	//#CM697105
	/** Position of each field item */

	private int[] ItemPos = new int[29];

	//#CM697106
	/** Field item: Receiving */

	private final int Type_Instock = 0;

	//#CM697107
	/** Field item: Storage */

	private final int Type_Strage = 1;

	//#CM697108
	/** Field item: Picking */

	private final int Type_Retrieval = 2;

	//#CM697109
	/** Field item: Sorting */

	private final int Type_Picking = 3;

	//#CM697110
	/** Field item: Shipping */

	private final int Type_Shipping = 4;

	//#CM697111
	/** Field item: Inventory Relocation */

	private final int Type_Moving = 5;

	//#CM697112
	/** Field item: Inventory Check */

	private final int Type_Stocktaking = 6;

	//#CM697113
	/** Field item: Unplanned Storage */

	private final int Type_NoPlanStorage = 7;

	//#CM697114
	/** Field item: Unplanned Picking */

	private final int Type_NoPlanRetrieval = 8;

	//#CM697115
	/** Section name of Receiving environment variable **/

	private final String[] Sec_Name =
		{
			"DATAREPORT_INSTOCKRECEIVE",
			"DATAREPORT_STRAGESUPPORT",
			"DATAREPORT_RETRIEVALSUPPORT",
			"DATAREPORT_PICKINGSUPPORT",
			"DATAREPORT_SHIPPINGINSPECTION",
			"DATAREPORT_MOVINGSUPPORT",
			"DATAREPORT_STOCKTAKINGSUPPORT",
			"DATAREPORT_NOPLANSTRAGESUPPORT",
			"DATAREPORT_NOPLANRETRIEVALSUPPORT" };

	//#CM697116
	/** Section name of Enabled flag **/

	private final String Sec_Enable = "_ENABLE";

	//#CM697117
	/** Section name of Length **/

	private final String Sec_Figure = "_FIGURE";

	//#CM697118
	/** Section name of Max Length **/

	private final String Sec_Max = "_FIGURE_MAX";

	//#CM697119
	/** Section name of Position **/

	private final String Sec_Pos = "_POSITION";

	//#CM697120
	/** Field item name of the environment info **/

	private final String[][] Item_Name = { { "PLAN_DATE", // 入荷
			"ORDERING_DATE",
				"CONSIGNOR_CODE",
				"CONSIGNOR_NAME",
				"SUPPLIER_CODE",
				"SUPPLIER_NAME1",
				"",
				"",
				"ITEM_CODE",
				"BUNDLE_ITF",
				"ITF",
				"BUNDLE_ENTERING_QTY",
				"ENTERING_QTY",
				"ITEM_NAME1",
				"PLAN_QTY",
				"TCDC_FLAG",
				"CUSTOMER_CODE",
				"CUSTOMER_NAME1",
				"",
				"",
				"",
				"",
				"TICKET_NO",
				"LINE_NO",
				"RESULT_QTY",
				"",
				"COMPLETE_DATE",
				"RESULT_FLAG",
				"USE_BY_DATE" },
				{
			"PLAN_DATE", // 入庫
			"",
				"CONSIGNOR_CODE",
				"CONSIGNOR_NAME",
				"",
				"",
				"",
				"",
				"ITEM_CODE",
				"BUNDLE_ITF",
				"ITF",
				"BUNDLE_ENTERING_QTY",
				"ENTERING_QTY",
				"ITEM_NAME1",
				"PLAN_QTY",
				"",
				"",
				"",
				"JOB_LOCATION_PIECE",
				"JOB_LOCATION_CASE",
				"",
				"",
				"",
				"",
				"RESULT_QTY_PIECE",
				"RESULT_QTY_CASE",
				"COMPLETE_DATE",
				"RESULT_FLAG",
				"USE_BY_DATE" },
				{
			"PLAN_DATE", // 出庫
			"",
				"CONSIGNOR_CODE",
				"CONSIGNOR_NAME",
				"",
				"",
				"SHIPPING_TICKET_NO",
				"SHIPPING_LINE_NO",
				"ITEM_CODE",
				"BUNDLE_ITF",
				"ITF",
				"BUNDLE_ENTERING_QTY",
				"ENTERING_QTY",
				"ITEM_NAME1",
				"PLAN_QTY",
				"",
				"CUSTOMER_CODE",
				"CUSTOMER_NAME",
				"JOB_LOCATION_PIECE",
				"JOB_LOCATION_CASE",
				"ORDER_NO_PIECE",
				"ORDER_NO_CASE",
				"",
				"",
				"RESULT_QTY_PIECE",
				"RESULT_QTY_CASE",
				"COMPLETE_DATE",
				"RESULT_FLAG",
				"USE_BY_DATE" },
				{
			"PLAN_DATE", // 仕分
			"",
				"CONSIGNOR_CODE",
				"CONSIGNOR_NAME",
				"SUPPLIER_CODE",
				"SUPPLIER_NAME1",
				"SHIPPING_TICKET_NO",
				"SHIPPING_LINE_NO",
				"ITEM_CODE",
				"BUNDLE_ITF",
				"ITF",
				"BUNDLE_ENTERING_QTY",
				"ENTERING_QTY",
				"ITEM_NAME1",
				"PLAN_QTY",
				"TCDC_FLAG",
				"CUSTOMER_CODE",
				"CUSTOMER_NAME",
				"JOB_LOCATION_PIECE",
				"JOB_LOCATION_CASE",
				"",
				"",
				"INSTOCK_TICKET_NO",
				"INSTOCK_LINE_NO",
				"RESULT_QTY_PIECE",
				"RESULT_QTY_CASE",
				"COMPLETE_DATE",
				"RESULT_FLAG",
				"" },
				{
			"SHIPPING_DAY", // 出荷
			"ORDERING_DATE",
				"CONSIGNOR_CODE",
				"CONSIGNOR_NAME",
				"SUPPLIER_CODE",
				"SUPPLIER_NAME",
				"TICKET_NO",
				"LINE_NO",
				"ITEM_CODE",
				"BUNDLE_ITF",
				"ITF",
				"BUNDLE_ENTERING_QTY",
				"ENTERING_QTY",
				"ITEM_NAME",
				"PLAN_QTY",
				"SHIPPING_TYPE",
				"CUSTOMER_CODE",
				"CUSTOMER_NAME",
				"",
				"",
				"",
				"",
				"INSTOCK_TICKET_NO",
				"INSTOCK_LINE_NO",
				"RESULT_QTY",
				"",
				"COMPLETE_DATE",
				"RESULT_FLAG",
				"USE_BY_DATE" },
				{
			"", // 在庫移動
			"",
				"CONSIGNOR_CODE",
				"CONSIGNOR_NAME",
				"",
				"",
				"",
				"",
				"ITEM_CODE",
				"",
				"",
				"",
				"ENTERING_QTY",
				"ITEM_NAME1",
				"",
				"",
				"",
				"",
				"JOB_LOCATION_PIECE",
				"JOB_LOCATION_CASE",
				"",
				"",
				"",
				"",
				"",
				"RESULT_QTY_CASE",
				"COMPLETE_DATE",
				"",
				"USE_BY_DATE" },
				{
			"", // 棚卸し
			"", "CONSIGNOR_CODE", "CONSIGNOR_NAME", "", "", "", "", "ITEM_CODE", "", "", // 10
			"", "ENTERING_QTY", "ITEM_NAME1", "PLAN_QTY", "", // 15
			"", "", "", "JOB_LOCATION_CASE", "", // 20
			"", "", "", "", "RESULT_QTY_CASE", // 25
			"COMPLETE_DATE", "", "USE_BY_DATE" }, {
			"", // 予定外入庫
			"",
				"CONSIGNOR_CODE",
				"CONSIGNOR_NAME",
				"",
				"",
				"",
				"",
				"ITEM_CODE",
				"BUNDLE_ITF",
				"ITF",
				"BUNDLE_ENTERING_QTY",
				"ENTERING_QTY",
				"ITEM_NAME1",
				"",
				"",
				"",
				"",
				"JOB_LOCATION_PIECE",
				"JOB_LOCATION_CASE",
				"",
				"",
				"",
				"",
				"RESULT_QTY_PIECE",
				"RESULT_QTY_CASE",
				"COMPLETE_DATE",
				"",
				"USE_BY_DATE" },
				{
			"", // 予定外出庫
			"",
				"CONSIGNOR_CODE",
				"CONSIGNOR_NAME",
				"",
				"",
				"",
				"",
				"ITEM_CODE",
				"BUNDLE_ITF",
				"ITF",
				"BUNDLE_ENTERING_QTY",
				"ENTERING_QTY",
				"ITEM_NAME1",
				"",
				"",
				"CUSTOMER_CODE",
				"CUSTOMER_NAME",
				"JOB_LOCATION_PIECE",
				"JOB_LOCATION_CASE",
				"",
				"",
				"",
				"",
				"RESULT_QTY_PIECE",
				"RESULT_QTY_CASE",
				"COMPLETE_DATE",
				"",
				"USE_BY_DATE" }
	};

	//#CM697121
	// Class variables -----------------------------------------------

	//#CM697122
	// Class method --------------------------------------------------

	//#CM697123
	// Constructors --------------------------------------------------
	//#CM697124
	/**
	 * Constructor
	 */
	public DefineReportDataSCH()
	{
	}

	//#CM697125
	// Public methods ------------------------------------------------
	//#CM697126
	/**
	 * This method obtains the data required for initial display.<BR>
	 * Search conditions passes a class that inherits the<CODE>Parameter</CODE> class.<BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		//#CM697127
		// Translate the type of searchParam.
		SystemParameter param = (SystemParameter) searchParam;

		//#CM697128
		// Receiving Plan checkbox
		param.setSelectReportInstockData(isInstockPack(conn));
		//#CM697129
		// Storage Plan checkbox
		param.setSelectReportStorageData(isStoragePack(conn));
		//#CM697130
		// Picking Plan checkbox
		param.setSelectReportRetrievalData(isRetrievalPack(conn));
		//#CM697131
		// Sorting Plan checkbox
		param.setSelectReportSortingData(isSortingPack(conn));
		//#CM697132
		// Shipping Plan checkbox
		param.setSelectReportShippingData(isShippingPack(conn));
		//#CM697133
		// Stock Relocation Result checkbox
		param.setSelectReportMovementData(isStoragePack(conn));
		//#CM697134
		// Inventory Check Result checkbox
		param.setSelectReportInventoryData(isStoragePack(conn));
		//#CM697135
		// Unplanned Storage Result checkbox
		param.setSelectReportNoPlanStorageData(isStockPack(conn));
		//#CM697136
		// Unplanned Picking Result checkbox
		param.setSelectReportNoPlanRetrievalData(isStockPack(conn));

		return param;

	}

	//#CM697137
	/**
	 * Obtain the data to be displayed on screen.Allow this method to support the operation such as clicking Display button.<BR>
	 * Search conditions passes a class that inherits the<CODE>Parameter</CODE> class.<BR>
	 * The data to be displayed may exist in two or more records. Return the result in the form of an array<BR>
	 * If no corresponding data is found, return array with number of elements equal to 0<BR>
	 * Return null if failed to process for searching due to input condition error.<BR>
	 * In this case, use the <CODE>getMessage()</CODE> method to obtain the contents.
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM697138
		// Translate the type of searchParam.
		DefineDataParameter param = (DefineDataParameter) searchParam;

		DefineDataParameter[] defParam = new DefineDataParameter[1];

		String select = param.getSelectDefineReportData();
		EnvironmentinformationLoad(getDataType(select));

		defParam[0] = (DefineDataParameter) setItems();

		//#CM697139
		// Obtain the designated field item info of introduced package from the Environment setting file (EnvironmentInformation).
		return defParam;
	}

	//#CM697140
	/**
	 * Check the contents of the parameter for its properness.According to the contents set for the parameter designated in <CODE>checkParam</CODE>,<BR>
	 * execute the process for checking the input in the parameter.Implement of the check process depends on the class that implements this interface.<BR>
	 * Return true if the contents of the parameter is correct.<BR>
	 * Return false if the content of the parameter has some problem,Use the <CODE>getMessage()</CODE> method to obtain the contents .
	 * @param conn Database connection object
	 * @param checkParam This parameter class includes contents to be checked for input.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM697141
		// Translate the data type of checkParam.
		SystemParameter param = (SystemParameter) checkParam;

		//#CM697142
		// Check the Worker Code, password, and Access Privileges.
		if (!checkWorker(conn, param, true))
		{
			return false;
		}

		return true;
	}

	//#CM697143
	/**
	 * Check the contents of the parameter for its properness.According to the contents set for the parameter designated in <CODE>checkParam</CODE>,<BR>
	 * execute the process for checking the input in the parameter.Implement of the check process depends on the class that implements this interface.<BR>
	 * Return true if the contents of the parameter is correct.<BR>
	 * Return false if the content of the parameter has some problem,Use the <CODE>getMessage()</CODE> method to obtain the contents .<BR>
	 * Allow this method to implement the check for input to shift from the original screen to the second screen between two screens.
	 * @param conn Database connection object
	 * @param checkParam This parameter class includes contents to be checked for input.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM697144
		// Invoke "check" and
		//#CM697145
		// execute the check process.			
		return check(conn, checkParam);
	}

	//#CM697146
	/**
	 * Start the schedule. According to the contents set in the parameter array designated in the <CODE>startParams</CODE>,<BR>
	 * execute the process for the schedule. Implement a scheduling process depending on the class implementing this interface.<BR>
	 * Return true when the schedule process completed successfully.<BR>
	 * Return false if failed to schedule due to condition error or other causes.<BR>
	 * In this case, use the <CODE>getMessage()</CODE> method to obtain the contents.
	 * @param conn Database connection object
	 * @param startParams Database connection object
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		if (startParams.length == 0)
		{
			//#CM697147
			// 6004001=Invalid Data was found.
			wMessage = "6004001";
			return false;
		}

		DefineDataParameter[] param = (DefineDataParameter[]) startParams;

		//#CM697148
		// Submit/Add Process
		String select = param[0].getSelectDefineReportData();
		int dataType = getDataType(select);
		getItems(dataType, param[0]);
		EnvironmentinformationSave(dataType);

		//#CM697149
		// 6001006 = Data was committed.
		wMessage = "6001006";

		return true;
	}

	//#CM697150
	// Package methods -----------------------------------------------

	//#CM697151
	// Protected methods ---------------------------------------------

	//#CM697152
	// Private methods -----------------------------------------------

	//#CM697153
	/**
	 * Read the designated information from the environment info.
	 * @param ReadType Work Type
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void EnvironmentinformationLoad(int ReadType) throws ReadWriteException
	{

		//#CM697154
		// Generate an instance for operating the environment info.
		IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

		String wFigure = null;
		String wMax = null;
		String wPos = null;

		//#CM697155
		// Obtain each information.
		for (int i = 0; i < 29; i++)
		{

			//#CM697156
			// Read the content of a field item if it is to be used.
			if (!Item_Name[ReadType][i].equals(""))
			{

				ItemReq[i] = IO.get(Sec_Name[ReadType] + Sec_Enable, Item_Name[ReadType][i]);
				wFigure = IO.get(Sec_Name[ReadType] + Sec_Figure, Item_Name[ReadType][i]);
				wMax = IO.get(Sec_Name[ReadType] + Sec_Max, Item_Name[ReadType][i]);
				wPos = IO.get(Sec_Name[ReadType] + Sec_Pos, Item_Name[ReadType][i]);

				if (wFigure != null && !wFigure.trim().equals(""))
				{
					ItemFigure[i] = Integer.parseInt(wFigure);
				}
				if (wMax != null && !wMax.trim().equals(""))
				{
					ItemMaxFigure[i] = Integer.parseInt(wMax);
				}
				if (wPos != null && !wPos.trim().equals(""))
				{
					ItemPos[i] = Integer.parseInt(wPos);
				}
			}
			else
			{
				ItemReq[i] = "";
				ItemFigure[i] = 0;
				ItemMaxFigure[i] = 0;
				ItemPos[i] = 0;
			}
		}
	}
	
	//#CM697157
	/**
	 * Write the information designated as environment info.
	 * @param ReadType Work Type
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void EnvironmentinformationSave(int ReadType) throws ReadWriteException
	{

		//#CM697158
		// Generate an instance for operating the environment info.
		IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

		//#CM697159
		// Obtain each information.
		for (int i = 0; i < 29; i++)
		{

			//#CM697160
			// Write the content in a field item if it is to be used.
			if (!Item_Name[ReadType][i].equals(""))
			{

				String ireq;
				if (!ItemReq[i].trim().equals(""))
					ireq = ItemReq[i];
				else
					ireq = "0";

				//#CM697161
				// Set the field item if enabled.
				IO.set(Sec_Name[ReadType] + Sec_Enable, Item_Name[ReadType][i], ireq);

				if (!ireq.equals("0"))
				{
					IO.set(
						Sec_Name[ReadType] + Sec_Figure,
						Item_Name[ReadType][i],
						String.valueOf(ItemFigure[i]));
					IO.set(
						Sec_Name[ReadType] + Sec_Pos,
						Item_Name[ReadType][i],
						String.valueOf(ItemPos[i]));
				}
				else
				{
					IO.set(Sec_Name[ReadType] + Sec_Pos, Item_Name[ReadType][i], "0");
					ItemPos[i] = 0;
				}
			}
		}

		IO.flush();
	}
	
	//#CM697162
	/**
	 * Return the selected work type.
	 * @param select String of work type.
	 * @return	Work Type
	 */
	private int getDataType(String select)
	{
		if (select.equals(SystemParameter.SELECTDEFINEREPORTDATA_INSTOCK))
			return Type_Instock;
		else if (select.equals(SystemParameter.SELECTDEFINEREPORTDATA_STORAGE))
			return Type_Strage;
		else if (select.equals(SystemParameter.SELECTDEFINEREPORTDATA_RETRIEVAL))
			return Type_Retrieval;
		else if (select.equals(SystemParameter.SELECTDEFINEREPORTDATA_SORTING))
			return Type_Picking;
		else if (select.equals(SystemParameter.SELECTDEFINEREPORTDATA_SHIPPING))
			return Type_Shipping;
		else if (select.equals(SystemParameter.SELECTDEFINEREPORTDATA_MOVEMENT))
			return Type_Moving;
		else if (select.equals(SystemParameter.SELECTDEFINEREPORTDATA_NOPLANSTORAGE))
			return Type_NoPlanStorage;
		else if (select.equals(SystemParameter.SELECTDEFINEREPORTDATA_NOPLANRETRIEVAL))
			return Type_NoPlanRetrieval;
		else
			return Type_Stocktaking;
	}
	
	//#CM697163
	/**
	 * Set the value for the parameter.
	 * @return Parameter that has been setup.
	 */
	private Parameter setItems()
	{

		DefineDataParameter param = new DefineDataParameter();

		//#CM697164
		// Obtain Enabled Flag
		param.setValid_PlanDate(ItemReq[0].equals(Chk_On));
		param.setValid_OrderingDate(ItemReq[1].equals(Chk_On));
		param.setValid_ConsignorCode(ItemReq[2].equals(Chk_On));
		param.setValid_ConsignorName(ItemReq[3].equals(Chk_On));
		param.setValid_SupplierCode(ItemReq[4].equals(Chk_On));
		param.setValid_SupplierName(ItemReq[5].equals(Chk_On));
		param.setValid_ShippingTicketNo(ItemReq[6].equals(Chk_On));
		param.setValid_ShippingLineNo(ItemReq[7].equals(Chk_On));
		param.setValid_ItemCode(ItemReq[8].equals(Chk_On));
		param.setValid_BundleItf(ItemReq[9].equals(Chk_On));
		param.setValid_Itf(ItemReq[10].equals(Chk_On));
		param.setValid_BundleEnteringQty(ItemReq[11].equals(Chk_On));
		param.setValid_EnteringQty(ItemReq[12].equals(Chk_On));
		param.setValid_ItemName(ItemReq[13].equals(Chk_On));
		param.setValid_PlanQty(ItemReq[14].equals(Chk_On));
		param.setValid_TcDcFlag(ItemReq[15].equals(Chk_On));
		param.setValid_CustomerCode(ItemReq[16].equals(Chk_On));
		param.setValid_CustomerName(ItemReq[17].equals(Chk_On));
		param.setValid_PieceLocation(ItemReq[18].equals(Chk_On));
		param.setValid_CaseLocation(ItemReq[19].equals(Chk_On));
		param.setValid_PieceOrderNo(ItemReq[20].equals(Chk_On));
		param.setValid_CaseOrderNo(ItemReq[21].equals(Chk_On));
		param.setValid_InstockTicketNo(ItemReq[22].equals(Chk_On));
		param.setValid_InstockLineNo(ItemReq[23].equals(Chk_On));
		param.setValid_PieceResultQty(ItemReq[24].equals(Chk_On));
		param.setValid_CaseResultQty(ItemReq[25].equals(Chk_On));
		param.setValid_WorkDate(ItemReq[26].equals(Chk_On));
		param.setValid_ResultFlag(ItemReq[27].equals(Chk_On));
		param.setValid_UseByDate(ItemReq[28].equals(Chk_On));

		//#CM697165
		// Obtain Length
		for (int i = 0; i < 29; i++)
		{
			if (ItemFigure[i] == 0)
			{
				continue;
			}
			switch (i)
			{
				case 0 :
					param.setFigure_PlanDate(Integer.toString(ItemFigure[i]));
					break;
				case 1 :
					param.setFigure_OrderingDate(Integer.toString(ItemFigure[i]));
					break;
				case 2 :
					param.setFigure_ConsignorCode(Integer.toString(ItemFigure[i]));
					break;
				case 3 :
					param.setFigure_ConsignorName(Integer.toString(ItemFigure[i]));
					break;
				case 4 :
					param.setFigure_SupplierCode(Integer.toString(ItemFigure[i]));
					break;
				case 5 :
					param.setFigure_SupplierName(Integer.toString(ItemFigure[i]));
					break;
				case 6 :
					param.setFigure_ShippingTicketNo(Integer.toString(ItemFigure[i]));
					break;
				case 7 :
					param.setFigure_ShippingLineNo(Integer.toString(ItemFigure[i]));
					break;
				case 8 :
					param.setFigure_ItemCode(Integer.toString(ItemFigure[i]));
					break;
				case 9 :
					param.setFigure_BundleItf(Integer.toString(ItemFigure[i]));
					break;
				case 10 :
					param.setFigure_Itf(Integer.toString(ItemFigure[i]));
					break;
				case 11 :
					param.setFigure_BundleEnteringQty(Integer.toString(ItemFigure[i]));
					break;
				case 12 :
					param.setFigure_EnteringQty(Integer.toString(ItemFigure[i]));
					break;
				case 13 :
					param.setFigure_ItemName(Integer.toString(ItemFigure[i]));
					break;
				case 14 :
					param.setFigure_PlanQty(Integer.toString(ItemFigure[i]));
					break;
				case 15 :
					param.setFigure_TcDcFlag(Integer.toString(ItemFigure[i]));
					break;
				case 16 :
					param.setFigure_CustomerCode(Integer.toString(ItemFigure[i]));
					break;
				case 17 :
					param.setFigure_CustomerName(Integer.toString(ItemFigure[i]));
					break;
				case 18 :
					param.setFigure_PieceLocation(Integer.toString(ItemFigure[i]));
					break;
				case 19 :
					param.setFigure_CaseLocation(Integer.toString(ItemFigure[i]));
					break;
				case 20 :
					param.setFigure_PieceOrderNo(Integer.toString(ItemFigure[i]));
					break;
				case 21 :
					param.setFigure_CaseOrderNo(Integer.toString(ItemFigure[i]));
					break;
				case 22 :
					param.setFigure_InstockTicketNo(Integer.toString(ItemFigure[i]));
					break;
				case 23 :
					param.setFigure_InstockLineNo(Integer.toString(ItemFigure[i]));
					break;
				case 24 :
					param.setFigure_PieceResultQty(Integer.toString(ItemFigure[i]));
					break;
				case 25 :
					param.setFigure_CaseResultQty(Integer.toString(ItemFigure[i]));
					break;
				case 26 :
					param.setFigure_WorkDate(Integer.toString(ItemFigure[i]));
					break;
				case 27 :
					param.setFigure_ResultFlag(Integer.toString(ItemFigure[i]));
					break;
				case 28 :
					param.setFigure_UseByDate(Integer.toString(ItemFigure[i]));
					break;
			}
		}

		//#CM697166
		// Obtain Max Length
		param.setMaxFigure_PlanDate(Integer.toString(ItemMaxFigure[0]));
		param.setMaxFigure_OrderingDate(Integer.toString(ItemMaxFigure[1]));
		param.setMaxFigure_ConsignorCode(Integer.toString(ItemMaxFigure[2]));
		param.setMaxFigure_ConsignorName(Integer.toString(ItemMaxFigure[3]));
		param.setMaxFigure_SupplierCode(Integer.toString(ItemMaxFigure[4]));
		param.setMaxFigure_SupplierName(Integer.toString(ItemMaxFigure[5]));
		param.setMaxFigure_ShippingTicketNo(Integer.toString(ItemMaxFigure[6]));
		param.setMaxFigure_ShippingLineNo(Integer.toString(ItemMaxFigure[7]));
		param.setMaxFigure_ItemCode(Integer.toString(ItemMaxFigure[8]));
		param.setMaxFigure_BundleItf(Integer.toString(ItemMaxFigure[9]));
		param.setMaxFigure_Itf(Integer.toString(ItemMaxFigure[10]));
		param.setMaxFigure_BundleEnteringQty(Integer.toString(ItemMaxFigure[11]));
		param.setMaxFigure_EnteringQty(Integer.toString(ItemMaxFigure[12]));
		param.setMaxFigure_ItemName(Integer.toString(ItemMaxFigure[13]));
		param.setMaxFigure_PlanQty(Integer.toString(ItemMaxFigure[14]));
		param.setMaxFigure_TcDcFlag(Integer.toString(ItemMaxFigure[15]));
		param.setMaxFigure_CustomerCode(Integer.toString(ItemMaxFigure[16]));
		param.setMaxFigure_CustomerName(Integer.toString(ItemMaxFigure[17]));
		param.setMaxFigure_PieceLocation(Integer.toString(ItemMaxFigure[18]));
		param.setMaxFigure_CaseLocation(Integer.toString(ItemMaxFigure[19]));
		param.setMaxFigure_PieceOrderNo(Integer.toString(ItemMaxFigure[20]));
		param.setMaxFigure_CaseOrderNo(Integer.toString(ItemMaxFigure[21]));
		param.setMaxFigure_InstockTicketNo(Integer.toString(ItemMaxFigure[22]));
		param.setMaxFigure_InstockLineNo(Integer.toString(ItemMaxFigure[23]));
		param.setMaxFigure_PieceResultQty(Integer.toString(ItemMaxFigure[24]));
		param.setMaxFigure_CaseResultQty(Integer.toString(ItemMaxFigure[25]));
		param.setMaxFigure_WorkDate(Integer.toString(ItemMaxFigure[26]));
		param.setMaxFigure_ResultFlag(Integer.toString(ItemMaxFigure[27]));
		param.setMaxFigure_UseByDate(Integer.toString(ItemMaxFigure[28]));

		//#CM697167
		// Obtain Position
		for (int i = 0; i < 29; i++)
		{
			if (ItemPos[i] == 0)
			{
				continue;
			}
			switch (i)
			{
				case 0 :
					param.setPosition_PlanDate(Integer.toString(ItemPos[i]));
					break;
				case 1 :
					param.setPosition_OrderingDate(Integer.toString(ItemPos[i]));
					break;
				case 2 :
					param.setPosition_ConsignorCode(Integer.toString(ItemPos[i]));
					break;
				case 3 :
					param.setPosition_ConsignorName(Integer.toString(ItemPos[i]));
					break;
				case 4 :
					param.setPosition_SupplierCode(Integer.toString(ItemPos[i]));
					break;
				case 5 :
					param.setPosition_SupplierName(Integer.toString(ItemPos[i]));
					break;
				case 6 :
					param.setPosition_ShippingTicketNo(Integer.toString(ItemPos[i]));
					break;
				case 7 :
					param.setPosition_ShippingLineNo(Integer.toString(ItemPos[i]));
					break;
				case 8 :
					param.setPosition_ItemCode(Integer.toString(ItemPos[i]));
					break;
				case 9 :
					param.setPosition_BundleItf(Integer.toString(ItemPos[i]));
					break;
				case 10 :
					param.setPosition_Itf(Integer.toString(ItemPos[i]));
					break;
				case 11 :
					param.setPosition_BundleEnteringQty(Integer.toString(ItemPos[i]));
					break;
				case 12 :
					param.setPosition_EnteringQty(Integer.toString(ItemPos[i]));
					break;
				case 13 :
					param.setPosition_ItemName(Integer.toString(ItemPos[i]));
					break;
				case 14 :
					param.setPosition_PlanQty(Integer.toString(ItemPos[i]));
					break;
				case 15 :
					param.setPosition_TcDcFlag(Integer.toString(ItemPos[i]));
					break;
				case 16 :
					param.setPosition_CustomerCode(Integer.toString(ItemPos[i]));
					break;
				case 17 :
					param.setPosition_CustomerName(Integer.toString(ItemPos[i]));
					break;
				case 18 :
					param.setPosition_PieceLocation(Integer.toString(ItemPos[i]));
					break;
				case 19 :
					param.setPosition_CaseLocation(Integer.toString(ItemPos[i]));
					break;
				case 20 :
					param.setPosition_PieceOrderNo(Integer.toString(ItemPos[i]));
					break;
				case 21 :
					param.setPosition_CaseOrderNo(Integer.toString(ItemPos[i]));
					break;
				case 22 :
					param.setPosition_InstockTicketNo(Integer.toString(ItemPos[i]));
					break;
				case 23 :
					param.setPosition_InstockLineNo(Integer.toString(ItemPos[i]));
					break;
				case 24 :
					param.setPosition_PieceResultQty(Integer.toString(ItemPos[i]));
					break;
				case 25 :
					param.setPosition_CaseResultQty(Integer.toString(ItemPos[i]));
					break;
				case 26 :
					param.setPosition_WorkDate(Integer.toString(ItemPos[i]));
					break;
				case 27 :
					param.setPosition_ResultFlag(Integer.toString(ItemPos[i]));
					break;
				case 28 :
					param.setPosition_UseByDate(Integer.toString(ItemPos[i]));
					break;
			}
		}

		return param;

	}
	
	//#CM697168
	/**
	 * Allow this method to set the content of a parameter in an array.
	 * @param ReadType String of work type.
	 * @param param Parameter in DefineDataParameter type.
	 */
	private void getItems(int ReadType, DefineDataParameter param)
	{

		//#CM697169
		// Obtain each information.
		for (int i = 0; i < 29; i++)
		{

			//#CM697170
			// Set the field item if used.
			if (!Item_Name[ReadType][i].equals(""))
			{
				ItemFigure[i] = 0;
				ItemPos[i] = 0;

				switch (i)
				{
					case 0 :
						ItemReq[i] = getReq(param.getValid_PlanDate());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_PlanDate());
							ItemPos[i] = Integer.parseInt(param.getPosition_PlanDate());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_PlanDate());
						break;
					case 1 :
						ItemReq[i] = getReq(param.getValid_OrderingDate());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_OrderingDate());
							ItemPos[i] = Integer.parseInt(param.getPosition_OrderingDate());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_OrderingDate());
						break;
					case 2 :
						ItemReq[i] = getReq(param.getValid_ConsignorCode());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ConsignorCode());
							ItemPos[i] = Integer.parseInt(param.getPosition_ConsignorCode());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ConsignorCode());
						break;
					case 3 :
						ItemReq[i] = getReq(param.getValid_ConsignorName());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ConsignorName());
							ItemPos[i] = Integer.parseInt(param.getPosition_ConsignorName());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ConsignorName());
						break;
					case 4 :
						ItemReq[i] = getReq(param.getValid_SupplierCode());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_SupplierCode());
							ItemPos[i] = Integer.parseInt(param.getPosition_SupplierCode());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_SupplierCode());
						break;
					case 5 :
						ItemReq[i] = getReq(param.getValid_SupplierName());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_SupplierName());
							ItemPos[i] = Integer.parseInt(param.getPosition_SupplierName());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_SupplierName());
						break;
					case 6 :
						ItemReq[i] = getReq(param.getValid_ShippingTicketNo());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ShippingTicketNo());
							ItemPos[i] = Integer.parseInt(param.getPosition_ShippingTicketNo());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ShippingTicketNo());
						break;
					case 7 :
						ItemReq[i] = getReq(param.getValid_ShippingLineNo());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ShippingLineNo());
							ItemPos[i] = Integer.parseInt(param.getPosition_ShippingLineNo());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ShippingLineNo());
						break;
					case 8 :
						ItemReq[i] = getReq(param.getValid_ItemCode());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ItemCode());
							ItemPos[i] = Integer.parseInt(param.getPosition_ItemCode());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ItemCode());
						break;
					case 9 :
						ItemReq[i] = getReq(param.getValid_BundleItf());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_BundleItf());
							ItemPos[i] = Integer.parseInt(param.getPosition_BundleItf());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_BundleItf());
						break;
					case 10 :
						ItemReq[i] = getReq(param.getValid_Itf());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_Itf());
							ItemPos[i] = Integer.parseInt(param.getPosition_Itf());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_Itf());
						break;
					case 11 :
						ItemReq[i] = getReq(param.getValid_BundleEnteringQty());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_BundleEnteringQty());
							ItemPos[i] = Integer.parseInt(param.getPosition_BundleEnteringQty());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_BundleEnteringQty());
						break;
					case 12 :
						ItemReq[i] = getReq(param.getValid_EnteringQty());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_EnteringQty());
							ItemPos[i] = Integer.parseInt(param.getPosition_EnteringQty());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_EnteringQty());
						break;
					case 13 :
						ItemReq[i] = getReq(param.getValid_ItemName());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ItemName());
							ItemPos[i] = Integer.parseInt(param.getPosition_ItemName());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ItemName());
						break;
					case 14 :
						ItemReq[i] = getReq(param.getValid_PlanQty());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_PlanQty());
							ItemPos[i] = Integer.parseInt(param.getPosition_PlanQty());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_PlanQty());
						break;
					case 15 :
						ItemReq[i] = getReq(param.getValid_TcDcFlag());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_TcDcFlag());
							ItemPos[i] = Integer.parseInt(param.getPosition_TcDcFlag());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_TcDcFlag());
						break;
					case 16 :
						ItemReq[i] = getReq(param.getValid_CustomerCode());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_CustomerCode());
							ItemPos[i] = Integer.parseInt(param.getPosition_CustomerCode());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_CustomerCode());
						break;
					case 17 :
						ItemReq[i] = getReq(param.getValid_CustomerName());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_CustomerName());
							ItemPos[i] = Integer.parseInt(param.getPosition_CustomerName());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_CustomerName());
						break;
					case 18 :
						ItemReq[i] = getReq(param.getValid_PieceLocation());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_PieceLocation());
							ItemPos[i] = Integer.parseInt(param.getPosition_PieceLocation());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_PieceLocation());
						break;
					case 19 :
						ItemReq[i] = getReq(param.getValid_CaseLocation());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_CaseLocation());
							ItemPos[i] = Integer.parseInt(param.getPosition_CaseLocation());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_CaseLocation());
						break;
					case 20 :
						ItemReq[i] = getReq(param.getValid_PieceOrderNo());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_PieceOrderNo());
							ItemPos[i] = Integer.parseInt(param.getPosition_PieceOrderNo());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_PieceOrderNo());
						break;
					case 21 :
						ItemReq[i] = getReq(param.getValid_CaseOrderNo());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_CaseOrderNo());
							ItemPos[i] = Integer.parseInt(param.getPosition_CaseOrderNo());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_CaseOrderNo());
						break;
					case 22 :
						ItemReq[i] = getReq(param.getValid_InstockTicketNo());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_InstockTicketNo());
							ItemPos[i] = Integer.parseInt(param.getPosition_InstockTicketNo());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_InstockTicketNo());
						break;
					case 23 :
						ItemReq[i] = getReq(param.getValid_InstockLineNo());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_InstockLineNo());
							ItemPos[i] = Integer.parseInt(param.getPosition_InstockLineNo());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_InstockLineNo());
						break;
					case 24 :
						ItemReq[i] = getReq(param.getValid_PieceResultQty());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_PieceResultQty());
							ItemPos[i] = Integer.parseInt(param.getPosition_PieceResultQty());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_PieceResultQty());
						break;
					case 25 :
						ItemReq[i] = getReq(param.getValid_CaseResultQty());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_CaseResultQty());
							ItemPos[i] = Integer.parseInt(param.getPosition_CaseResultQty());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_CaseResultQty());
						break;
					case 26 :
						ItemReq[i] = getReq(param.getValid_WorkDate());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_WorkDate());
							ItemPos[i] = Integer.parseInt(param.getPosition_WorkDate());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_WorkDate());
						break;
					case 27 :
						ItemReq[i] = getReq(param.getValid_ResultFlag());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ResultFlag());
							ItemPos[i] = Integer.parseInt(param.getPosition_ResultFlag());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ResultFlag());
						break;
					case 28 :
						ItemReq[i] = getReq(param.getValid_UseByDate());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_UseByDate());
							ItemPos[i] = Integer.parseInt(param.getPosition_UseByDate());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_UseByDate());
						break;
					default :
						break;
				}

			}
		}

	}
	
	//#CM697171
	/**
	 * Return a mandatory/enabled flag from mandatory flag/enabled flag.
	 * @param	EnableFlg	Enabled flag (true,false)
	 * @return				A mandatory/enabled flag (0,1,2)
	 */
	private String getReq(boolean EnableFlg)
	{
		String ret;
		if (EnableFlg == false)
		{
			ret = "0";
		}
		else if (EnableFlg == true)
		{
			ret = "1";
		}
		else
		{
			ret = "2";
		}

		return ret;

	}
}
//#CM697172
//end of class
