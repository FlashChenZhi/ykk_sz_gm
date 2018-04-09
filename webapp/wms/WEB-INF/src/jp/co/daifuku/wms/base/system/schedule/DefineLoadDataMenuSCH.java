//#CM696900
//$Id: DefineLoadDataMenuSCH.java,v 1.2 2006/11/13 06:03:11 suresh Exp $

//#CM696901
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


//#CM696902
/**
 * Designer : T.Yamashita <BR>
 * Maker : T.Yamashita   <BR>
 * <BR>
 * Allow the <CODE>DefineLoadDataMenuSCH</CODE> class to execute the process for setting each field item to load its data.<BR>
 * Set Enabled/Disabled, Length, Position for each field item by introduced package of the Plan file.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for setting the environment.(<CODE>startSCH(Connection conn, Parameter[] startParams)</CODE>method)<BR>
 * <DIR>
 * Allow the Add button to activate it, which is shown in the second screen of the Load Data (setting of field items) screen.<BR>
 * Reflect the value set in the screen ( Enabled, Disabled, Length, Position) on the Environment setting file (EnvironmentInformation).<BR>
 * <BR>
 * </DIR>
 * <BR>
 * 2.Initial Display Process(<CODE>initFind(Connection conn, Parameter searchParam)</CODE> method)<BR>
 * <DIR>
 * Execute the process for initial display of buttons in the Load Data screen (setting of field items).<BR>
 * Referring to the System definition table(WARENAVI_SYSTEM), display only radio button of the Plan data with package flag "1: Available".<BR>
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
public class DefineLoadDataMenuSCH extends AbstractSystemSCH
{

	//#CM696903
	// Class fields --------------------------------------------------
	//#CM696904
	/** Field item */

	private final String Chk_Off = "0";

	//#CM696905
	/** Field item */

	private final String Chk_On = "1";

	//#CM696906
	/** Return a mandatory/enabled flag for each field item. */

	private String[] ItemReq = new String[24];

	//#CM696907
	/** Length of each field item */

	private int[] ItemFigure = new int[24];

	//#CM696908
	/** Max length of each field item */

	private int[] ItemMaxFigure = new int[24];

	//#CM696909
	/** Position of each field item */

	private int[] ItemPos = new int[24];

	//#CM696910
	/** Field item: Receiving */

	private final int Type_Instock = 0;

	//#CM696911
	/** Field item: Storage */

	private final int Type_Strage = 1;

	//#CM696912
	/** Field item: Picking */

	private final int Type_Retrieval = 2;

	//#CM696913
	/** Field item: Sorting */

	private final int Type_Picking = 3;

	//#CM696914
	/** Field item: Shipping */

	private final int Type_Shipping = 4;

	//#CM696915
	/** Section name of environment variable **/

	private final String[] Sec_Name =
		{
			"DATALOAD_INSTOCKRECEIVE",
			"DATALOAD_STRAGESUPPORT",
			"DATALOAD_RETRIEVALSUPPORT",
			"DATALOAD_PICKINGSUPPORT",
			"DATALOAD_SHIPPINGINSPECTION" };

	//#CM696916
	/** Section name of Enabled flag **/

	private final String Sec_Enable = "_ENABLE";

	//#CM696917
	/** Section name of Length **/

	private final String Sec_Figure = "_FIGURE";

	//#CM696918
	/** Section name of Max Length **/

	private final String Sec_Max = "_FIGURE_MAX";

	//#CM696919
	/** Section name of Position **/

	private final String Sec_Pos = "_POSITION";

	//#CM696920
	/** Field item name of the environment info **/

	private final String[][] Item_Name =
		{
			{
				"PLAN_DATE",
				"ORDERING_DATE",
				"CONSIGNOR_CODE",
				"CONSIGNOR_NAME",
				"SUPPLIER_CODE",
				"SUPPLIER_NAME1",
				"TICKET_NO",
				"LINE_NO",
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
				"",
				"" },
			{
			"PLAN_DATE",
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
				"" },
				{
			"PLAN_DATE",
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
				"" },
				{
			"PLAN_DATE",
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
				"INSTOCK_LINE_NO" },
				{
			"SHIPPING_DAY",
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
				"INSTOCK_LINE_NO" }
	};

	//#CM696921
	/** Mandatory item (count) name **/

	private final String[][] Item_Request =
		{
			{
				"PLAN_DATE",
				"",
				"",
				"",
				"SUPPLIER_CODE",
				"",
				"TICKET_NO",
				"LINE_NO",
				"ITEM_CODE",
				"",
				"",
				"",
				"",
				"",
				"PLAN_QTY",
				"TCDC_FLAG",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"" },
			{
			"PLAN_DATE",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"ITEM_CODE",
				"",
				"",
				"",
				"",
				"",
				"PLAN_QTY",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"" },
				{
			"PLAN_DATE",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"ITEM_CODE",
				"",
				"",
				"",
				"",
				"",
				"PLAN_QTY",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"" },
				{
			"PLAN_DATE",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"ITEM_CODE",
				"",
				"",
				"",
				"",
				"",
				"PLAN_QTY",
				"TCDC_FLAG",
				"CUSTOMER_CODE",
				"",
				"JOB_LOCATION_PIECE",
				"",
				"",
				"",
				"",
				"" },
				{
			"SHIPPING_DAY",
				"",
				"",
				"",
				"",
				"",
				"TICKET_NO",
				"LINE_NO",
				"ITEM_CODE",
				"",
				"",
				"",
				"",
				"",
				"PLAN_QTY",
				"SHIPPING_TYPE",
				"CUSTOMER_CODE",
				"",
				"",
				"",
				"",
				"",
				"",
				"" }
	};

	//#CM696922
	// Class variables -----------------------------------------------

	//#CM696923
	// Class method --------------------------------------------------

	//#CM696924
	// Constructors --------------------------------------------------
	//#CM696925
	/**
	 * Constructor
	 */
	public DefineLoadDataMenuSCH()
	{
	}

	//#CM696926
	// Public methods ------------------------------------------------
	//#CM696927
	/**
	 * This method obtains the data required for initial display.<BR>
	 * Search conditions passes a class that inherits the<CODE>Parameter</CODE> class.<BR>
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM696928
		// Translate the type of searchParam.
		SystemParameter param = (SystemParameter) searchParam;
		
		//#CM696929
		// Planned Work Date
		param.setPlanDate(getWorkDate(conn));
		//#CM696930
		// Receiving Plan checkbox
		param.setSelectLoadInstockData(isInstockPack(conn));
		//#CM696931
		// Storage Plan checkbox
		param.setSelectLoadStorageData(isStoragePack(conn));
		//#CM696932
		// Picking Plan checkbox
		param.setSelectLoadRetrievalData(isRetrievalPack(conn));
		//#CM696933
		// Sorting Plan checkbox
		param.setSelectLoadSortingData(isSortingPack(conn));
		//#CM696934
		// Shipping Plan checkbox
		param.setSelectLoadShippingData(isShippingPack(conn));
		
		return param;

	}

	//#CM696935
	/**
	 * Obtain the data to be displayed on screen.Allow this method to support the operation such as clicking Display button.<BR>
	 * Search conditions passes a class that inherits the<CODE>Parameter</CODE> class.<BR>
	 * The data to be displayed may exist in two or more records. Return the result in the form of an array<BR>
	 * If no corresponding data is found, return array with number of elements equal to 0<BR>
	 * Return null if failed to process for searching due to input condition error.<BR>
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM696936
		// Translate the type of searchParam.
		SystemParameter param = (SystemParameter) searchParam;
		
		DefineDataParameter[] defParam = new DefineDataParameter[1];

		String select = param.getSelectDefineLoadData();
		EnvironmentinformationLoad(getDataType(select));

		defParam[0] = (DefineDataParameter) setItems();

		//#CM696937
		// Obtain the designated field item info of introduced package from the Environment setting file (EnvironmentInformation).
		return defParam;
	}

	//#CM696938
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
		//#CM696939
		// Translate the data type of checkParam.
		SystemParameter param = (SystemParameter) checkParam;

		//#CM696940
		// Check the Worker Code, password, and Access Privileges.
		if (!checkWorker(conn, param, true))
		{
			return false;
		}

		return true;
	}
	
	//#CM696941
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
		//#CM696942
		// Invoke "check" and
		//#CM696943
		// execute the check process.			
		return check(conn, checkParam);
	}

	//#CM696944
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
			//#CM696945
			// 6004001=Invalid Data was found.
			wMessage = "6004001";
			return false;
		}
		
		//#CM696946
		// Translate the type of startParams.
		DefineDataParameter[] param = (DefineDataParameter[]) startParams;

		//#CM696947
		// Submit/Add Process
		String select = param[0].getSelectDefineLoadData();
		int dataType = getDataType(select);
		getItems(dataType, param[0]);
		EnvironmentinformationSave(dataType);

		//#CM696948
		// 6001006 = Data was committed.
		wMessage = "6001006";

		return true;
	}

	//#CM696949
	// Package methods -----------------------------------------------

	//#CM696950
	// Protected methods ---------------------------------------------

	//#CM696951
	// Private methods -----------------------------------------------
	//#CM696952
	/**
	 * Read the designated information from the environment info.
	 * @param ReadType Work Type
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void EnvironmentinformationLoad(int ReadType) throws ReadWriteException
	{

		//#CM696953
		// Generate an instance for operating the environment info.
		IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

		String wFigure = null;
		String wMax = null;
		String wPos = null;

		//#CM696954
		// Obtain each information.
		for (int i = 0; i < 24; i++)
		{

			//#CM696955
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

	//#CM696956
	/**
	 * Write the information designated as environment info.
	 * @param ReadType Work Type
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void EnvironmentinformationSave(int ReadType) throws ReadWriteException
	{

		//#CM696957
		// Generate an instance for operating the environment info.
		IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

		//#CM696958
		// Obtain each information.
		for (int i = 0; i < 24; i++)
		{

			//#CM696959
			// Write the content in a field item if it is to be used.
			if (!Item_Name[ReadType][i].equals(""))
			{

				String ireq;
				if (!ItemReq[i].trim().equals(""))
					ireq = ItemReq[i];
				else
					ireq = "0";

				//#CM696960
				// Disable to change anything for Mandatory item (count).
				if (Item_Request[ReadType][i].equals(""))
				{
					//#CM696961
					// Set the field item if enabled.
					IO.set(Sec_Name[ReadType] + Sec_Enable, Item_Name[ReadType][i], ireq);
				}
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

	//#CM696962
	/**
	 * Return the selected work type.
	 * @param select String of work type.
	 * @return	Work Type
	 */
	private int getDataType(String select)
	{
		if (select.equals(SystemParameter.SELECTDEFINELOADDATA_INSTOCK))
			return Type_Instock;
		else if (select.equals(SystemParameter.SELECTDEFINELOADDATA_STORAGE))
			return Type_Strage;
		else if (select.equals(SystemParameter.SELECTDEFINELOADDATA_RETRIEVAL))
			return Type_Retrieval;
		else if (select.equals(SystemParameter.SELECTDEFINELOADDATA_SORTING))
			return Type_Picking;
		else
			return Type_Shipping;
	}
	
	//#CM696963
	/**
	 * Set the value for the parameter.
	 * @return Parameter that has been setup.
	 */
	private Parameter setItems()
	{
		DefineDataParameter param = new DefineDataParameter();
		//#CM696964
		// Set the mandatory flag.

		//#CM696965
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

		//#CM696966
		// Obtain Length
		for (int i = 0; i < 24; i++)
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
			}
		}

		//#CM696967
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

		//#CM696968
		// Obtain Position
		for (int i = 0; i < 24; i++)
		{
			if (ItemPos[i] == 0)
			{
				continue;
			}
			switch (i)
			{
				case 0 :
					param.setPosition_PlanDate(Integer.toString(ItemPos[0]));
					break;
				case 1 :
					param.setPosition_OrderingDate(Integer.toString(ItemPos[1]));
					break;
				case 2 :
					param.setPosition_ConsignorCode(Integer.toString(ItemPos[2]));
					break;
				case 3 :
					param.setPosition_ConsignorName(Integer.toString(ItemPos[3]));
					break;
				case 4 :
					param.setPosition_SupplierCode(Integer.toString(ItemPos[4]));
					break;
				case 5 :
					param.setPosition_SupplierName(Integer.toString(ItemPos[5]));
					break;
				case 6 :
					param.setPosition_ShippingTicketNo(Integer.toString(ItemPos[6]));
					break;
				case 7 :
					param.setPosition_ShippingLineNo(Integer.toString(ItemPos[7]));
					break;
				case 8 :
					param.setPosition_ItemCode(Integer.toString(ItemPos[8]));
					break;
				case 9 :
					param.setPosition_BundleItf(Integer.toString(ItemPos[9]));
					break;
				case 10 :
					param.setPosition_Itf(Integer.toString(ItemPos[10]));
					break;
				case 11 :
					param.setPosition_BundleEnteringQty(Integer.toString(ItemPos[11]));
					break;
				case 12 :
					param.setPosition_EnteringQty(Integer.toString(ItemPos[12]));
					break;
				case 13 :
					param.setPosition_ItemName(Integer.toString(ItemPos[13]));
					break;
				case 14 :
					param.setPosition_PlanQty(Integer.toString(ItemPos[14]));
					break;
				case 15 :
					param.setPosition_TcDcFlag(Integer.toString(ItemPos[15]));
					break;
				case 16 :
					param.setPosition_CustomerCode(Integer.toString(ItemPos[16]));
					break;
				case 17 :
					param.setPosition_CustomerName(Integer.toString(ItemPos[17]));
					break;
				case 18 :
					param.setPosition_PieceLocation(Integer.toString(ItemPos[18]));
					break;
				case 19 :
					param.setPosition_CaseLocation(Integer.toString(ItemPos[19]));
					break;
				case 20 :
					param.setPosition_PieceOrderNo(Integer.toString(ItemPos[20]));
					break;
				case 21 :
					param.setPosition_CaseOrderNo(Integer.toString(ItemPos[21]));
					break;
				case 22 :
					param.setPosition_InstockTicketNo(Integer.toString(ItemPos[22]));
					break;
				case 23 :
					param.setPosition_InstockLineNo(Integer.toString(ItemPos[23]));
					break;
			}
		}

		return param;

	}
	
	//#CM696969
	/**
	 * Allow this method to set the content of a parameter in an array.
	 * @param ReadType String of work type.
	 * @param param Parameter in DefineDataParameter type.
	 */
	private void getItems(int ReadType, DefineDataParameter param)
	{
		//#CM696970
		// Obtain each information.
		for (int i = 0; i < 24; i++)
		{
			//#CM696971
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
						ItemMaxFigure[i] =	Integer.parseInt(param.getMaxFigure_BundleEnteringQty());
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
					default :
						break;
				}
			}
		}
	}
	
	//#CM696972
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
//#CM696973
//end of class
