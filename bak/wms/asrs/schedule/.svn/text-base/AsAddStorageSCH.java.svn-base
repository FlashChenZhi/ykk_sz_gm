//#CM44109
//$Id: AsAddStorageSCH.java,v 1.2 2006/10/30 01:04:34 suresh Exp $

//#CM44110
/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.dbhandler.ASPaletteHandler;
import jp.co.daifuku.wms.asrs.dbhandler.ASWorkPlaceHandler;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.asrs.report.AsNoPlanStorageWriter;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM44111
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * The class to do setting of the stock outside the WEBASRS schedule (Procuct increase) processing. <BR>
 * Receive the content input from the screen as parameter, and do the making processing of stock (Product increase) outside the ASRS schedule work information. <BR>
 * Do not do committing rollback of the transaction though each method of this class must wear the connection object and
 * do the update processing of the receipt data base.  <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial display processing(< CODE>initFind() </ CODE > method) <BR>
 * <BR>
 * <DIR>
 *	Return Consignor Code when Status retrieves stand by Consignor Code of the inventory information, 
 *	and only same Consignor Code exists. <BR>
 * 	Return null when two or more Consignor Code exists. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *     Stock status is center stocking.<BR>
 *     Stock qty is one or more. <BR>
 *     AS/RS Stock <BR>
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	Do the edit of the list of ASRSWarehouse. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		From the Warehouse table < warehouse >.  <BR>
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	Do the edit of the list of a hard zone. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		From the shelf table < shelf >. <BR>
 *      Link information with Warehouse. <BR>
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	Do the edit of the list of the workshop. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		From the Station table < station >. <BR>
 *      Link information with Warehouse. <BR>
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	Do the edit of the list of Station. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		From the Station table < station >. <BR>
 *      Link information with workshop. <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 2.Process when Next button is pressed 1(< CODE>nextCheck() </ CODE > method) <BR>
 * <BR>
 * <DIR>
 *	Receive the content input from the screen as parameter, and return the result of the check on Worker code / Password and the existence check of pertinent data. <BR>
 *	Return true when the inventory information exists in input Shelf  No., and the content of Worker code and Password is correct. <BR>
 *	Set Error Message  "The specified shelf is an empty shelf" when the inventory information does not exist in input Shelf  No., and return false. <BR>
 *	The content can be acquired by using < CODE>getMessage() </ CODE > method about the content of the error. <BR>
 *	<BR>
 *	<Parameter> *Mandatory Input<BR>
 *	<DIR>
 *		Worker code* <BR>
 *		Password*<BR>
 *      Warehouse*<BR>
 *      WarehouseName*<BR>
 *		Consignor Code<BR>
 *		Item Code<BR>
 *		Case/Piece flag*<BR>
 *		Shelf No.*<BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 3.Process when Next button is pressed 2(< CODE>query() </ CODE > method) <BR>
 * <BR>
 * <DIR>
 *	Receive the content input from the screen as parameter, and check the condition. <BR>
 *	Return < CODE>Parameter</CODE > arrays of number 0 of elements when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *	It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *	<BR>
 *	<Parameter> *Mandatory Input<BR>
 *	<DIR>
 *		Worker code* <BR>
 *		Password*<BR>
 *		Warehouse*<BR>
 *		WarehouseName*<BR>
 *		Consignor Code<BR>
 *		Item Code<BR>
 *		Case/Piece flag*<BR>
 *		Shelf No.*<BR>
 *	</DIR>
 *	<BR>
 *	<return data> <BR>
 *	<DIR>
 *		Worker code <BR>
 *		Password <BR>
 *      Warehouse <BR>
 *      WarehouseName <BR>
 *		Shelf No. <BR>
 *	</DIR>
 *   -Shelf Status check processing-<BR>
 *     Thing where inventory information exists in input Shelf  No.<BR>
 *     Shelf Status must be a real shelf. (Drawing must not be done by other work. )<BR>
 *   <BR>
 * </DIR>
 * <BR>
 * 4.Input button pressing processing(< CODE>check() </ CODE > method)<BR>
 * <BR>
 * <DIR>
 *   Receive the content input from the screen as parameter, do an mandatory check and Overflow check and the repetition check, and return the check result. <BR>
 *   Return true when pertinent data of same line No. does not exist in the inventory information and return false when the condition error occurs and pertinent data exists. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   [parameter] *Mandatory Input <BR><DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Warehouse* <BR>
 *     Shelf No..* <BR>
 *     Workshop* <BR>
 *     Station* <BR>
 *     Consignor Code* <BR>
 *     Consignor Name <BR>
 *     Storage plan date* <BR>
 *     Item Code* <BR>
 *     Item Name <BR>
 *     Case/Piece flag* <BR>
 *     Qty per case <BR>
 *     Qty per bundle <BR>
 *     Storage Case qty+ <BR>
 *     Storage Piece qty+ <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry date <BR>
 *   </DIR>
 *   <BR>
 *   [Processing condition check] <BR>
 *   <BR>
 *   -Shelf Status check processing-<BR>
 *       Thing where inventory information exists in input Shelf  No.<BR>
 *       Shelf Status must be a real shelf. (Drawing must not be done by other work. )<BR>
 *   <BR>
 *   -Worker code check processing-(<CODE>AbstructStorageSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Input value check processing-(<CODE>AbstructStorageSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Overflow check- <BR>
 *   <BR>
 *   -Display number check- <BR>
 *   <BR>
 *   -Checking for duplication- <BR>
 *     Make Consignor Code, Item Code, Case/Piece flag, and Expiry date a key and do Checking for duplication. <BR>
 *     The key to Checking for duplication contains Expiry date when defined in WmsParam as an item which uniquely stocks it. <BR>
 * </DIR>
 * <BR>
 * 5.Stock beginning button pressing processing (<CODE>startSCH()</CODE>Method) <BR><BR>
 * <BR>
 * <DIR>
 *   Straighten, receive the content displayed in the area as parameter, and do setting of the stock outside the ASRS schedule (Product increase) processing. <BR>
 *   Return true when processing is normally completed and return false when it does not complete the schedule because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   [parameter] <BR><DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Warehouse* <BR>
 *     Shelf No.* <BR>
 *     Workshop* <BR>
 *     Station* <BR>
 *     Consignor Code* <BR>
 *     Consignor Name <BR>
 *     Storage plan date* <BR>
 *     Item Code* <BR>
 *     Item Name <BR>
 *     Case/Piece flag* <BR>
 *     Qty per case <BR>
 *     Qty per bundle <BR>
 *     Storage Case qty+ <BR>
 *     Storage Piece qty+ <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry date <BR>
 *     Terminal No. <BR>
 *   </DIR>
 *   <BR>
 *   [Processing condition check] <BR>
 *   <BR>
 *   -Worker code check processing-(<CODE>AbstructStorageSCH()</CODE>Class)<BR>
 *   <BR>
 *   -Next day update Processing check processing-(<CODE>AbstructStorageSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Shelf Status check processing-<BR>
 *     Thing where inventory information exists in input Shelf  No.<BR>
 *     Shelf Status must be a real shelf. (Drawing must not be done by other work. )<BR>
 *   <BR>
 *   [Update registration processing] <BR>
 *   <BR>
 *   -Registration of work information table (DNWORKINFO)-<BR>
 *   <DIR>
 *     Process Registration of work information of the adding storing by input Shelf  No.<BR>
 *   </DIR>  
 *   <BR>
 *   -Registration of transportation table (CARRYINFO)-<BR>
 *   <DIR>
 *     Register the transportation table by the content of the input. <BR>
 *   </DIR>  
 *   <BR>
 *   -Registration and renewal of stock information table (DMSTOCK)-<BR>
 *   <DIR>
 *     Register the stock information table by the content of the input. <BR>
 *     [Processing condition check] <BR>
 *     <BR>
 *     -Overflow check-<BR>  
 *     <BR>
 *   </DIR>
 *   -Renewal of palette table (PALETTE)- <BR>
 *   <DIR>
 *       Renew Status flag to the delivery reservation. <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/03</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:04:34 $
 * @author  $Author: suresh $
 */
public class AsAddStorageSCH extends AbstractAsrsControlSCH
{
	//#CM44112
	// Class variables -----------------------------------------------
	//#CM44113
	/**
	 * Class Name(Commit of No plan storage (Product increase))
	 */
	public static String CLASS_ADDSTORAGE = "AsAddStorageSCH";

	//#CM44114
	/**
	 * Batch No.
	 */
	public static String[] wBachino = new String[255];
	public static int wBachi_Seq ;

	//#CM44115
	// Class method --------------------------------------------------
	//#CM44116
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:04:34 $");
	}
	//#CM44117
	// Constructors --------------------------------------------------
	//#CM44118
	/**
	 * Initialize this Class. 
	 */
	public AsAddStorageSCH()
	{
		wMessage = null;
	}

	//#CM44119
	// Public methods ------------------------------------------------
	//#CM44120
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen. <BR>
	 * Return corresponding Consignor Code when only one Consignor Code exists in the inventory information. <BR>
	 * Return null when pertinent data does not exist or it exists by two or more.  <BR>
	 * Set null in < CODE>searchParam</CODE > because it does not need the search condition. 
	 * @param conn Instance to maintain connection with data base. 
	 * @param searchParam Instance of < CODE>AsScheduleParameter</CODE>Class with display data acquisition condition. 
	 * @return Class which mounts < CODE>Parameter</CODE > interface where retrieval result is included
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();

		//#CM44121
		// Data retrieval
		//#CM44122
		// Stock status(Center Stocking)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM44123
		// Stock qty must be one or more. 
		searchKey.setStockQty(1, ">=");

		//#CM44124
		// Acquire only the stock of AS/RS. 
		AreaOperator areaOpe = new AreaOperator(conn);
		int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
		searchKey.setAreaNo(areaOpe.getAreaNo(areaType));

		searchKey.setConsignorCodeCollect(" ");
		searchKey.setConsignorCodeGroup(1);

		AsScheduleParameter dispData = new AsScheduleParameter();

		if (stockFinder.search(searchKey) == 1)
		{
			//#CM44125
			// Data retrieval			
			searchKey.KeyClear();
			//#CM44126
			// Stock status(Center Stocking)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM44127
			// Stock qty must be one or more. 
			searchKey.setStockQty(1, ">=");
			//#CM44128
			// Acquire only the stock of AS/RS. 
			searchKey.setAreaNo(areaOpe.getAreaNo(areaType));
			//#CM44129
			// Acquire Consignor Name that Latest Updated date & time is new. 
			searchKey.setLastUpdateDateOrder(1, false);

			searchKey.setConsignorCodeCollect();
			searchKey.setConsignorNameCollect();

			if (stockFinder.search(searchKey) > 0)
			{				
				Stock[] consignorname = (Stock[]) stockFinder.getEntities(1);

				dispData.setConsignorCode(consignorname[0].getConsignorCode());
				dispData.setConsignorName(consignorname[0].getConsignorName());
			}
		}
		stockFinder.close();

		return dispData;
	}

	//#CM44130
	/**
	 * Receive the content of the area of the accumulation input from the screen as parameter, do an mandatory check, 
	 * Overflow check, Checking for duplication, and return the check result.  <BR>
	 * Assume true to be an exclusive error when an occurrence of the condition error and pertinent data exist,
	 * and return false when pertinent data of same line No. does not exist in the inventory information.  <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param checkParam Instance of <CODE>AsScheduleParameter</CODE>Class with content of input.
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM44131
			// Content of input area
			AsScheduleParameter param = (AsScheduleParameter) checkParam;

			//#CM44132
			// Check on Worker code and Password
			if (!checkWorker(conn, param))
			{
				return false;
			}

			//#CM44133
			// Do the stock check of the input shelf. 
			return checkStockData(conn, param.getLocationNo());

		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM44134
	/** 
	 * Receive the content of the area of the accumulation input from the screen as parameter, do an mandatory check, 
	 * Overflow check, Checking for duplication, and return the check result.  <BR>
	 * Assume true to be an exclusive error when an occurrence of the condition error and pertinent data exist, 
	 * and return false when pertinent data of same line No. does not exist in the inventory information.  <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param checkParam Instance of < CODE>AsScheduleParameter</CODE>Class with content of input. 
	 * @param inputParams Array of instance of <CODE>AsScheduleParameter</CODE>Class with content of area of filtering it. 
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams)
		throws ScheduleException, ReadWriteException
	{
		try
		{
			//#CM44135
			// Content of input area
			AsScheduleParameter param = (AsScheduleParameter) checkParam;
			//#CM44136
			// Content of area of filtering it
			AsScheduleParameter[] paramlist = (AsScheduleParameter[]) inputParams;

			String casepieceflag = param.getCasePieceFlag();
			int enteringqty = param.getEnteringQty();
			int bundleenteringqty = param.getBundleEnteringQty();
			int caseqty = param.getStorageCaseQty();
			int pieceqty = param.getStoragePieceQty();

			//#CM44137
			// Check on correct input when input value is empty palette
			if( !isCorrectEmptyPB(param.getConsignorCode(), param.getItemCode(), param.getCasePieceFlag(),
									param.getUseByDate(), caseqty, pieceqty, enteringqty, bundleenteringqty) )
			{
				return false;
			}
			
			//#CM44138
			// Check on correct input when input value is abnormal shelf
			if (!checkIrregularCode(param.getConsignorCode(), param.getItemCode()))
			{
				return false;
			}

			//#CM44139
			// Display number check
			if (paramlist != null && paramlist.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
			{
				//#CM44140
				// 6023096 = Because the number of cases exceeds the {0} matter, it is not possible to input it. 
				wMessage = "6023096" + wDelim + MAX_NUMBER_OF_DISP_DISP;
				return false;
			}

			//#CM44141
			// Input value check
			if (!storageInputCheck(casepieceflag, enteringqty, caseqty, pieceqty))
			{
				return false;
			}

			//#CM44142
			// Correspondence check of input content and content
			//#CM44143
			// Do this check when there are of filtering. 
			if (paramlist != null)
			{
				//#CM44144
				// Checking for duplication
				//#CM44145
				// Make Consignor Code, Item Code, Case/Piece flag, and Expiry date a key and do Checking for duplication. 
				//#CM44146
				// The key to Checking for duplication contains Expiry date when defined in WmsParam as an item which uniquely stocks it. 
				for (int i = 0; i < paramlist.length; i++)
				{
					if( checkMulti(param.getConsignorCode(), param.getItemCode(),
									param.getCasePieceFlag(), param.getUseByDate(),
									paramlist[i].getConsignorCode(), paramlist[i].getItemCode(),
									paramlist[i].getCasePieceFlag(), paramlist[i].getUseByDate()) )
					{
						//#CM44147
						// 6023090 = Same data already exists. It is not possible to input it. 
						wMessage = "6023090";
						return false;
					}
				}

				//#CM44148
				// Consolidation check of empty palette and other articles
				//#CM44149
				// It is not possible to consolidate it with an empty palette and the other articles. 
				String emppbConsignCode = WmsParam.EMPTYPB_CONSIGNORCODE;
				//#CM44150
				// When empty Palette is input, there is working stock after filtering
				if( param.getConsignorCode().equals(emppbConsignCode) )
				{
					//#CM44151
					// 6013092=A usual stock and an empty palette cannot be consolidated. 
					wMessage = "6013092";
					return false;
				}
				//#CM44152
				// When empty Palette is input, there is working stock after filtering
				else
				{
					for( int i = 0 ; i < paramlist.length ; i++ )
					{
						if( paramlist[i].getConsignorCode().equals(emppbConsignCode))
						{
							//#CM44153
							// 6013092=A usual stock and an empty palette cannot be consolidated. 
							wMessage = "6013092";
							return false;
						}
					}
				}
			}

			//#CM44154
			// Content of input area checks whether same information exists in the specified shelf. 
			//#CM44155
			//   Consignor Code, Item Code, Case/Piece flag, and Expiry date same information
			//#CM44156
			//   The key contains Expiry date when defined in WmsParam as an item which uniquely stocks it. 
			StockHandler stkHandler = new StockHandler(conn);
			StockSearchKey stkKey = new StockSearchKey();

			//#CM44157
			// Stock status(Center Stocking)
			stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM44158
			// Acquire only the stock of AS/RS. 
			AreaOperator areaOpe = new AreaOperator(conn);
			int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
			stkKey.setAreaNo(areaOpe.getAreaNo(areaType));
			stkKey.setLocationNo(param.getLocationNo());
			stkKey.setConsignorCode(param.getConsignorCode());
			stkKey.setItemCode(param.getItemCode());
			stkKey.setCasePieceFlag(param.getCasePieceFlag());
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				stkKey.setUseByDate(param.getUseByDate());
			}
			
			if( stkHandler.count(stkKey) == 0)
			{
				//#CM44159
				// Assortment(Do an assorted check because the same content as the input area does not exist. )

				//#CM44160
				// Overflow check
				long inputqty =
					(long) param.getStorageCaseQty() * (long) param.getEnteringQty()
						+ (long) param.getStoragePieceQty();
				if (inputqty > WmsParam.MAX_STOCK_QTY)
				{
					//#CM44161
					// 6023058 = {0}Input the value below {1}. Check it with Storage qty (total of the rows). 
					wMessage = "6023058" + wDelim
							+ DisplayText.getText("LBL-W0377") + wDelim
							+ MAX_STOCK_QTY_DISP;
					return false;
				}
				
				//#CM44162
				// Acquire all the inventory information on the same shelf. 
				stkKey.KeyClear();
				//#CM44163
				// Stock status(Center Stocking)
				stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
				//#CM44164
				// Acquire only the stock of AS/RS. 
				areaType[0] = Area.SYSTEM_DISC_KEY_ASRS;
				stkKey.setAreaNo(areaOpe.getAreaNo(areaType));
				//#CM44165
				// Shelf No.
				stkKey.setLocationNo(param.getLocationNo());
				Stock[] stks = (Stock[]) stkHandler.find(stkKey);
				if(stks.length == 0)
				{
					//#CM44166
					// No adjustment
					//#CM44167
					// 6013134=The stock does not exist in the specified shelf. 
					wMessage = "6013134";
					return false;
				}
				
				//#CM44168
				// Judge whether it filters and our data assorts only the piling up increase and check the number of consolidation. 
				int newStorageCount = 0;
				if (paramlist != null)
				{
					for (int i = 0; i < paramlist.length; i++)
					{
						stkKey.KeyClear();
						stkKey.setPaletteid(stks[0].getPaletteid());
						stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
						stkKey.setLocationNo(paramlist[i].getLocationNo());
						stkKey.setConsignorCode(paramlist[i].getConsignorCode());
						stkKey.setItemCode(paramlist[i].getItemCode());
						stkKey.setCasePieceFlag(paramlist[i].getCasePieceFlag());
						if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
						{
							stkKey.setUseByDate(paramlist[i].getUseByDate());
						}
						if (stkHandler.count(stkKey) == 0)
						{
							newStorageCount++;
						}
					}
				}
				if ((newStorageCount + stks.length) >= getMaxMixedPalette(conn, param.getWareHouseNo()))
				{
					//#CM44169
					// 6023487=Because the number of consolidation exceeds the number of maximum consolidation, it is not possible to input it. 
					wMessage = "6023487";
					return false;
				}
				
				//#CM44170
				// Do the shelf stock and the consolidation check of Item code for an empty palette. 
				if( !checkEmptyCode(conn, param, stks) )
				{
					return false;
				}

			}
			else
			{
				//#CM44171
				// Piling up increase
				//#CM44172
				// Overflow check
				stkKey.setStockQtyCollect();
				Stock[] stks = (Stock[]) stkHandler.find(stkKey);
				long inputqty = stks[0].getStockQty()
						+ (long) param.getStorageCaseQty() * (long) param.getEnteringQty()
						+ param.getStoragePieceQty();
				if (inputqty > WmsParam.MAX_STOCK_QTY)
				{
					String strMaxQty =
							WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY - stks[0].getStockQty());

					if(WmsParam.MAX_STOCK_QTY - stks[0].getStockQty() == 0){
						//#CM44173
						// 6023502 = Because Storage qty exceeded {0}, it was not possible to set it. 
						wMessage = "6023502" + wDelim
						+ WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY);					    
					}else{
						//#CM44174
						// 6023058 = {0}Input the value below {1}. Check it with Storage qty (total of the rows). 
						wMessage = "6023058" + wDelim
								+ DisplayText.getText("LBL-W0377") + wDelim
								+ strMaxQty;
					}
					return false;
				}
			}

			//#CM44175
			// Do the stock check of the input shelf. 
			if( !checkStockData(conn, param.getLocationNo()) )
			{
				return false;
			}
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		//#CM44176
		// 6001019 = The input was accepted. 
		wMessage = "6001019";
		return true;
	}

	//#CM44177
	/**
	 * Receive the content input from the screen as parameter, and begin the schedule of the No plan storage setting. <BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation. <BR>
	 * Return true when the schedule ends normally and return false when failing.
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Array of instance of < CODE>AsScheduleParameter</CODE>Class with set content. 
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM44178
			// Check on parameter
			AsScheduleParameter workparam = (AsScheduleParameter) startParams[0];

			//#CM44179
			// Next day update Processing check
			if (isDailyUpdate(conn))
			{
				return false;
			}

			try
			{
				//#CM44180
				// Lock processing(inventory information and Location information)
				lockStockData(conn, (AsScheduleParameter[]) startParams);
			}
			catch(LockTimeOutException e)
			{
				//#CM44181
				// 6027008 = This data is not useable because it is updating it with other terminals. 
				wMessage = "6027008";
				return false;
			}

			//#CM44182
			// Do the stock check of the input shelf. 
			if( !checkStockData(conn, workparam.getLocationNo()) )
			{
				return false;
			}

			//#CM44183
			// Check of input and stock after filtering it
			if( !checkList(conn, startParams ) )
			{
				return false;
			}

			//#CM44184
			// Acquire all the inventory information on the same shelf. 
			AsScheduleParameter param = (AsScheduleParameter) startParams[0];

			StockHandler stkHandler = new StockHandler(conn);
			StockSearchKey stkKey = new StockSearchKey();

			//#CM44185
			// Stock status(Center Stocking)
			stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM44186
			// Acquire only the stock of AS/RS. 
			AreaOperator areaOpe = new AreaOperator(conn);
			int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
			stkKey.setAreaNo(areaOpe.getAreaNo(areaType));
			//#CM44187
			// Shelf No.
			stkKey.setLocationNo(param.getLocationNo());
			Stock[] stks = (Stock[]) stkHandler.find(stkKey);

			//#CM44188
			// Acquire palette information with Palette ID of the inventory information. 
			PaletteHandler pltHandler = new PaletteHandler(conn);
			PaletteSearchKey pltKey = new PaletteSearchKey();
			pltKey.setPaletteId(stks[0].getPaletteid());
			Palette palette = (Palette) pltHandler.findPrimary(pltKey);

			Entity tost = null;
			StationSearchKey skey = new StationSearchKey();
			if( param.getToStationNo().equals(Station.AUTO_SELECT_STATIONNO) )
			{
				//#CM44189
				// Acquire the entity of Workshop in case of the automatic distribution. 
				skey.setStationNumber(param.getSagyoba());
				ASWorkPlaceHandler wpHandler = new ASWorkPlaceHandler(conn);
				WorkPlace[] wp = (WorkPlace[]) wpHandler.find(skey);
				tost = wp[0];
			}
			else
			{
				//#CM44190
				// Acquire the entity of Station. 
				skey.setStationNumber(param.getToStationNo());
				StationHandler stnHandler = new StationHandler(conn);
				Station st = (Station) stnHandler.findPrimary(skey);
				tost = st;

				//#CM44191
				// Check Status of selection Station. 
				String r_Msg = isRetrievalStationCheck(conn, (Station)tost, true);
				if (!StringUtil.isBlank(r_Msg))
				{
					wMessage = r_Msg;
					return false;
				}				
			}

			//#CM44192
			// Route check processing
			RouteController rt = new RouteController(conn, false);
			if (rt.retrievalDetermin(palette, (Station)tost, true, true, false) == false)
			{
				//#CM44193
				// Messege of transportation route NG is acquired. 
				wMessage =getRouteErrorMessage(rt.getRouteStatus());
				return false;
			}
			//#CM44194
			// Acquire the transportation destinationStation
			String deststation = rt.getDestStation().getStationNumber();

			//#CM44195
			// Set the date now in Storage date & time. (one setting and commonness)
			Date instockdate = new Date();

			//#CM44196
			// Worker information acquisition
			//#CM44197
			// Worker code
			String workercode = workparam.getWorkerCode();
			//#CM44198
			// Worker Name
			String workerName = getWorkerName(conn, workercode);

			//#CM44199
			// Parameter for Results information making
			//#CM44200
			// Terminal No.
			String terminalno = workparam.getTerminalNumber();
			//#CM44201
			// Work Flag(Exception stock)
			String jobtype = WorkingInformation.JOB_TYPE_EX_STORAGE;

			//#CM44202
			// Acquire each registered one mind key. 
			SequenceHandler sequence = new SequenceHandler(conn);
			//#CM44203
			// Batch No.(One setting commonness)
			String batch_seqno = sequence.nextNoPlanBatchNo();			
			//#CM44204
			// Schedule No of CarryInfo
			String schno_seqno = sequence.nextScheduleNumber() ;
			//#CM44205
			// Transportation key
			String carrykey_seqno = sequence.nextCarryKey() ;

			//#CM44206
			// Shipper code
			String customercode = "" ;
			//#CM44207
			// Shipper name
			String customername = "" ;

			//#CM44208
			// It processes it the number of parameter. 
			for (int i = 0; i < startParams.length; i++)
			{
				//#CM44209
				// Storage plan qty
				param = (AsScheduleParameter) startParams[i];

				int storagePlanQty = (int)((long)param.getStorageCaseQty() * (long)param.getEnteringQty()) +
										param.getStoragePieceQty();

				Stock stock = null;
				boolean multi = false;
				int j = 0;
				for( ; j < stks.length ; j++ )
				{
					//#CM44210
					// Are there same Consignor Code, Item Code, Case/Piece flag, and Expiry date in the stock?
					if( checkMulti(param.getConsignorCode(), param.getItemCode(),
									param.getCasePieceFlag(), param.getUseByDate(),
									stks[j].getConsignorCode(), stks[j].getItemCode(),
									stks[j].getCasePieceFlag(), stks[j].getUseByDate()) )
					{
						multi = true;
						break;
					}
				}
				if( multi )
				{
					//#CM44211
					// Piling up increase
					//#CM44212
					// Update of inventory information (DMSTOCK)
					if( !updateStock( conn, stks[j].getStockId(), storagePlanQty ) )
					{
						return false;
					}
					stock = stks[j];
				}
				else
				{
					//#CM44213
					// Assortment
					//#CM44214
					// Registration of inventory information (DMSTOCK)
					String stockid_seq = sequence.nextStockId();
					stock = createStock(conn,
										param,
										storagePlanQty,
										instockdate,
										stockid_seq,
										param.getLocationNo(),
										stks[0].getPaletteid(),
										CLASS_ADDSTORAGE);
				}

				//#CM44215
				// Work No.
				String jobno_seq = sequence.nextJobNo();

				//#CM44216
				// Registration of work information (DNWORKINFO)
				if (!createWorkinfo(conn,
									param,
									stock.getStockId(),
									workercode,
									workerName,
									terminalno,
									jobtype,
									CLASS_ADDSTORAGE,
									batch_seqno,
									storagePlanQty,
									param.getLocationNo(),
									jobno_seq,
									customercode,
									customername,
									carrykey_seqno,
									param.getAreaNo(),
									""))
				{
					return false;
				}
			}

			//#CM44217
			// Update of palette information (PALETTE)
			if(!updatePalette(conn, stks[0].getPaletteid()))
			{
				return false;
			}

			String workno = sequence.nextRetrievalWorkNumber();
			//#CM44218
			// Aisle Station No..
			//#CM44219
			// Retrieve the shelf table by Shelf  No.
			ShelfHandler wShelfHandler = new ShelfHandler(conn);
			ShelfSearchKey wShelfKey = new ShelfSearchKey();
				
			wShelfKey.KeyClear();
			wShelfKey.setStationNumber(param.getLocationNo());
			
			Shelf rShelf = (Shelf)wShelfHandler.findPrimary(wShelfKey);
			//#CM44220
			// Registration of transportation information (CARRYINFO)
			if (!createCarryinfo(conn,
								CarryInformation.WORKTYPE_ADD_STORAGE,
								schno_seqno,
								param.getLocationNo(),
								workno,
								CarryInformation.RETRIEVALDETAIL_ADD_STORING,
								rShelf.getStationNumber(),
								deststation,
								stks[0].getPaletteid(),
								CarryInformation.CMDSTATUS_START,
								CarryInformation.PRIORITY_EMERGENCY,
								CarryInformation.CARRYKIND_RETRIEVAL,
								carrykey_seqno,
								rShelf.getParentStationNumber()))
			{
				return false;
			}

			//#CM44221
			// Schedule success

			//#CM44222
			// Making of stock list file
			if(param.getListFlg() == true)
			{
				if(startPrint(conn, batch_seqno, deststation))
				{
					//#CM44223
					// 6021012 = The print ended normally after it had set it. 
					wMessage = "6021012";
				}
				else
				{
					//#CM44224
					// 6007042=After submitting, It failed in the print. Refer to the log. 
					wMessage = "6007042";
				}
			}
			else
			{
				//#CM44225
				// 6001006 = Submitted.
				wMessage = "6001006";
			}


			return true;
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM44226
	// Protected methods ---------------------------------------------
	//#CM44227
	/**
	 * Pass Batch  No. to ASRS stock list issue processing Class. <BR>
	 * Do not do the print processing when there is no print data. <BR>
	 * Receive false when failing in true from ASRS stock issue processing Class, and return the result when succeeding in the print. <BR>
	 * It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
	 * @param  conn Connection Connection object with database
	 * @param  batchno Batch No.
	 * @param  stNo Retrieval Station No.
	 * @return True when processing is normal, False when the schedule processing fails.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean startPrint(Connection conn, String batchno, String stNo) throws ReadWriteException, ScheduleException
	{
		String storageStNo = null;
		String retrievalStNo = stNo;

		//#CM44228
		// Station specified on screen
		Station dispSt = null;
		//#CM44229
		// Operator Class of Station specified on screen
		StationOperator stOpe = null;
		try
		{
			//#CM44230
			// Make Station from the screen input. 
			dispSt = StationFactory.makeStation(conn, retrievalStNo);
			//#CM44231
			// Make operator Class.  
			stOpe = StationOperatorFactory.makeOperator(conn, dispSt.getStationNumber(), dispSt.getClassName());
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		//#CM44232
		// Acquire stock side Station for character Station of piece. 
		if (stOpe instanceof FreeRetrievalStationOperator)
		{
			FreeRetrievalStationOperator castStOpe = (FreeRetrievalStationOperator) stOpe;
			storageStNo = castStOpe.getFreeStorageStationNumber();
		}
		//#CM44233
		// Things except piece character Station (insertion delivery using combinedly)
		//#CM44234
		// Set Station specified on screen in stock Station. 
		else
		{
			storageStNo = stOpe.getStation().getStationNumber();
		}
		
		AsNoPlanStorageWriter writer = new AsNoPlanStorageWriter(conn);
		writer.setBatchNumber(batchno);
		writer.setRetrievalStationNo(retrievalStNo);
		writer.setStorageStationNo(storageStNo);

		//#CM44235
		// Begin the printing job. 
		if (writer.startPrint())
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	//#CM44236
	/**
	 * Register the stock information table. 
	 * @param  conn        Instance to maintain connection with data base. 
	 * @param  param       Instance of AsScheduleParameter class with content input from screen. 
	 * @param  inputqty    Storage qty
	 * @param  instockdate Storage date & time
	 * @param  stockid     Stock ID
	 * @param  locationno	Location No.
	 * @param  paletteid   Palette ID
	 * @param  processname Program Name
	 * @return Inventory information
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected Stock createStock(
		Connection conn,
		AsScheduleParameter param,
		int inputqty,
		Date instockdate,
		String stockid,
		String locationno,
		int paletteid,
		String processname)
		throws ReadWriteException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			Stock stock = new Stock();

			//#CM44237
			// Stock ID
			stock.setStockId(stockid);
			//#CM44238
			// Plan unique key
			stock.setPlanUkey(stockid);
			//#CM44239
			// Area No.
			stock.setAreaNo(param.getAreaNo());
			//#CM44240
			// Location No..
			stock.setLocationNo(locationno);
			//#CM44241
			// Item Code
			stock.setItemCode(param.getItemCode());
			//#CM44242
			// Item Name
			stock.setItemName1(param.getItemName());
			//#CM44243
			// Stock status(Storage waiting)
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_RECEIVINGRESERVE);
			//#CM44244
			// Stock qty (Storage qty)
			stock.setStockQty(0);
			//#CM44245
			// Drawing qty
			stock.setAllocationQty(0);
			//#CM44246
			// Storage plan qty
			stock.setPlanQty(inputqty);
			//#CM44247
			// Case/Piece flag(Mode of packing)
			//#CM44248
			// Unspecified
			if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
			//#CM44249
			// Case
			else if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			//#CM44250
			// Piece
			else if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}

			//#CM44251
			// Storage date & time
			stock.setInstockDate(instockdate);
			//#CM44252
			// The final Retrieval date
			stock.setLastShippingDate("");
			//#CM44253
			// Expiry date
			String useByDate = "";
			if (param.getUseByDate().length() <= 8)
			{
				useByDate = param.getUseByDate();
			}
			else
			{
				useByDate = param.getUseByDate().substring(0,4) +
							param.getUseByDate().substring(5,7) + param.getUseByDate().substring(8,10);
			}
			stock.setUseByDate(useByDate);
			//#CM44254
			// Lot No.
			stock.setLotNo("");
			//#CM44255
			// Plan information comment
			stock.setPlanInformation("");
			//#CM44256
			// Consignor Code
			stock.setConsignorCode(param.getConsignorCode());
			//#CM44257
			// Consignor Name
			stock.setConsignorName(param.getConsignorName());
			//#CM44258
			// Supplier code
			stock.setSupplierCode("");
			//#CM44259
			// Supplier name
			stock.setSupplierName1("");
			//#CM44260
			// Qty per case
			stock.setEnteringQty(param.getEnteringQty());
			//#CM44261
			// Qty per bundle
			stock.setBundleEnteringQty(param.getBundleEnteringQty());
			//#CM44262
			// Case ITF
			stock.setItf(param.getITF());
			//#CM44263
			// Bundle ITF
			stock.setBundleItf(param.getBundleITF());
			//#CM44264
			// Registration Processing name
			stock.setRegistPname(processname);
			//#CM44265
			// Last updated Processing name
			stock.setLastUpdatePname(processname);
			//#CM44266
			// Palette ID
			stock.setPaletteid(paletteid);
			//#CM44267
			// Restorage flag
			stock.setRestoring(0);

			//#CM44268
			// Registration of data
			stockHandler.create(stock);

			return stock;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM44269
	/**
	 * Registration of work information table (DNWORKINFO). <BR> 
	 * <BR>     
	 * Register work information based on the content of received parameter.  <BR>
	 * <BR>
	 * @param conn        Instance to maintain connection with data base. 
	 * @param param       Instance of AsScheduleParameter class with content input from screen. 
	 * @param stockid     Stock ID
	 * @param workercode  Worker code
	 * @param workername  Worker name
	 * @param terminalno  Terminal No.
	 * @param jobtype     Work Flag
	 * @param processname Processing name
	 * @param batchno     Batch No.
	 * @param inputqty	   Actual work qty 
	 * @param locationno  Location No..
	 * @param jobno       Work No.
	 * @param customercode Shipper code
	 * @param customername Shipper name
	 * @param carrykey     Transportation key
	 * @param areano		Area No.
	 * @param planukey     Plan unique key
	 * @return True when processing is normal, False when the schedule processing fails.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean createWorkinfo(
		Connection conn,
		AsScheduleParameter param,
		String stockid,
		String workercode,
		String workername,
		String terminalno,
		String jobtype,
		String processname,
		String batchno,
		int inputqty,
		String locationno,
		String jobno,
		String customercode,
		String customername,
		String carrykey,
		String areano,
		String planukey)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler wWorkHandler = new WorkingInformationHandler(conn);
			WorkingInformation workInfo = new WorkingInformation();

			//#CM44270
			// Work No.
			workInfo.setJobNo(jobno);
			//#CM44271
			// Work Flag
			workInfo.setJobType(jobtype);
			//#CM44272
			// Consolidating Work No.
			workInfo.setCollectJobNo(jobno);
			//#CM44273
			// Status flag:Working
			workInfo.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			//#CM44274
			// Work beginning flag:Started
			workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			//#CM44275
			// Plan unique key
			workInfo.setPlanUkey(planukey);
			//#CM44276
			// Stock ID
			workInfo.setStockId(stockid);
			//#CM44277
			// Area No.
			workInfo.setAreaNo(areano);
			//#CM44278
			// Location No..
			workInfo.setLocationNo(locationno);
			//#CM44279
			// Plan Date
			workInfo.setPlanDate(getWorkDate(conn));
			//#CM44280
			// Consignor Code
			workInfo.setConsignorCode(param.getConsignorCode());
			//#CM44281
			// Consignor Name
			workInfo.setConsignorName(param.getConsignorName());
			//#CM44282
			// Supplier code
			workInfo.setSupplierCode("");
			//#CM44283
			// Supplier name
			workInfo.setSupplierName1("");
			//#CM44284
			// Receiving ticket No.
			workInfo.setInstockTicketNo("");
			//#CM44285
			// Receiving line No.
			workInfo.setInstockLineNo(0);
			//#CM44286
			// Shipper code
			workInfo.setCustomerCode(customercode);
			//#CM44287
			// Shipper name
			workInfo.setCustomerName1(customername);
			//#CM44288
			// Item Code
			workInfo.setItemCode(param.getItemCode());
			//#CM44289
			// Item Name
			workInfo.setItemName1(param.getItemName());
			//#CM44290
			// Work plan qty 
			workInfo.setPlanQty(inputqty);
			//#CM44291
			// Work possible qty 
			workInfo.setPlanEnableQty(inputqty);
			//#CM44292
			// Actual work qty 
			workInfo.setResultQty(0);
			//#CM44293
			// Work shortage qty 
			workInfo.setShortageCnt(0);
			//#CM44294
			// Reserved qty
			workInfo.setPendingQty(0);
			//#CM44295
			// Qty per case
			workInfo.setEnteringQty(param.getEnteringQty());
			//#CM44296
			// Qty per bundle
			workInfo.setBundleEnteringQty(param.getBundleEnteringQty());
			//#CM44297
			// Case/Piece flag(Mode of packing)
			workInfo.setCasePieceFlag(param.getCasePieceFlag());
			//#CM44298
			// Case/Piece flag(Work form)
			workInfo.setWorkFormFlag(param.getCasePieceFlag());
			//#CM44299
			// Case ITF 
			workInfo.setItf(param.getITF());
			//#CM44300
			// Bundle ITF
			workInfo.setBundleItf(param.getBundleITF());
			//#CM44301
			// TC/DCFlag
			workInfo.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			//#CM44302
			// Expiry date
			workInfo.setUseByDate(param.getUseByDate());
			//#CM44303
			// Lot No.
			workInfo.setLotNo("");
			//#CM44304
			// Plan information comment
			workInfo.setPlanInformation("");
			//#CM44305
			// Order No.
			workInfo.setOrderNo("");
			//#CM44306
			// Order day
			workInfo.setOrderingDate("");
			//#CM44307
			// Expiry date(Results)
			workInfo.setResultUseByDate("");
			//#CM44308
			// Lot No.(Results)
			workInfo.setResultLotNo("");
			//#CM44309
			// Work resultLocation No..
			workInfo.setResultLocationNo("");
			//#CM44310
			// Unwork report flag
			workInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			//#CM44311
			// Batch No.
			workInfo.setBatchNo(batchno);
			//#CM44312
			// Worker code
			workInfo.setWorkerCode(workercode);
			//#CM44313
			// Worker Name
			workInfo.setWorkerName(workername);
			//#CM44314
			// Terminal No.
			workInfo.setTerminalNo(terminalno);			
			//#CM44315
			// Plan information registration date
			workInfo.setPlanRegistDate(new Date());
			//#CM44316
			// Registration Processing name
			workInfo.setRegistPname(processname);
			//#CM44317
			// Last updated date and time
			workInfo.setLastUpdateDate(new Date());
			//#CM44318
			// Last updated Processing name
			workInfo.setLastUpdatePname(processname);
			//#CM44319
			// Another system connection key
			workInfo.setSystemConnKey(carrykey);
			//#CM44320
			// Another system identification key
			workInfo.setSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_ASRS);

			//#CM44321
			// Registration of work information
			wWorkHandler.create(workInfo);

			return true;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM44322
	/**
	 * Check the stock existence from Inventory information and shelf information. 
	 * @param  conn        Instance to maintain connection with data base. 
	 * @param  param       Instance of AsScheduleParameter class with content input from screen. 
	 * @return True if the stock which can be drawn exists in the shelf, False otherwise.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean checkStockData(Connection conn, String location) throws ReadWriteException
	{
		try
		{
			//#CM44323
			// Acquire stock Status of the palette from input Shelf No.
			ASFindUtil util = new ASFindUtil(conn);
			int status = util.getPaletteStatus(location);
			if( status == -1 )
			{
				//#CM44324
				// 6013134 = The stock does not exist in the specified shelf. 
				wMessage = "6013134";
				return false;
			}
			//#CM44325
			// Check Status of the palette. 
			if( status == Palette.REGULAR )
			{
				//#CM44326
				// Check Status of the shelf.
				return checkShelf(conn, location);
			}
			else
			{
				if( status == Palette.IRREGULAR )
				{
					//#CM44327
					// 6013199 = The specified shelf cannot be set because of an abnormal shelf. 
					wMessage = "6013199";
				}
				else
				{
					//#CM44328
					// 6013135 = The stock of the specified shelf is Drawing now. 
					wMessage = "6013135";
				}
				return false;
			}
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM44329
	/**
	 * Specified Location No. Check Status of the corresponding shelf.<BR>
	 * Specified Shelf  No. must be an effective shelf. <BR>
	 * Specified Shelf  No. must be an accessible shelf. <BR>
	 * The shelf which can use specified Shelf No.<BR>
	 * Return false when it is not necessary to meet either of requirement when it meets all requirements. <BR><BR>
	 * Store the content in the message area. <BR>
	 * @param conn Connection object with database
	 * @param location Shelf  No. of checked shelf
	 * @return Return false if you cannot use true in case of the shelf which can be used. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean checkShelf(Connection conn, String location) throws ReadWriteException
	{
		ShelfHandler slfHandler = new ShelfHandler(conn);
		ShelfSearchKey slfKey = new ShelfSearchKey();

		//#CM44330
		// Location No.
		slfKey.setStationNumber(location);

		Shelf[] shelf = (Shelf[]) slfHandler.find(slfKey);
		if( shelf.length == 0 )
		{
			//#CM44331
			//6013090 = Input Existing Shelf No.
			wMessage = "6013090";
			return false;
		}

		//#CM44332
		// Check which cannot be accessed shelf
		if (shelf[0].getAccessNgFlag() == Shelf.ACCESS_NG)
		{
			//#CM44333
			//6013113 = It is not possible to set it because of Status which cannot access the shelf. 
			wMessage = "6013113";
			return false;
		}
		//#CM44334
		// Status check of shelf
		if (shelf[0].getStatus() == Shelf.STATUS_NG)
		{
			//#CM44335
			//6013114 = Because the shelf is set to the prohibition shelf, it is not possible to set it. 
			wMessage = "6013114";
			return false;
		}
		return true;
	}

	//#CM44336
	/** 
	 * Filter and do the input check. <BR>
	 * < CODE>ScheduleException</CODE > is slow when called.
	 * @param  searchParam Instance of < CODE>AsScheduleParameter</CODE>Class with display data acquisition condition. <BR>
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it..
	 * @throws ScheduleException It is notified when this Method is called. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean checkList(Connection conn, Parameter[] searchParams) throws ReadWriteException, ScheduleException
	{
		AsScheduleParameter[] param = (AsScheduleParameter[]) searchParams;

		StockHandler stkHandler = new StockHandler(conn);
		StockSearchKey stkKey = new StockSearchKey();

		//#CM44337
		// Acquire all the inventory information on the same shelf. 
		stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		AreaOperator areaOpe = new AreaOperator(conn);
		int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
		stkKey.setAreaNo(areaOpe.getAreaNo(areaType));
		stkKey.setLocationNo(param[0].getLocationNo());
		Stock[] stks = (Stock[]) stkHandler.find(stkKey);
		if( stks.length == 0)
		{
			//#CM44338
			// 6013134=The stock does not exist in the specified shelf. 
			wMessage = "6013134";
			return false;
		}

		int mixedCount = 0;
		for (int i = 0 ; i < param.length ; i++)
		{
			//#CM44339
			// Line No.
			int rowno = param[i].getRowNo();

			//#CM44340
			// Content of input area checks whether same information exists in the specified shelf. 
			//#CM44341
			// Consignor Code, Item Code, Case/Piece flag, and Expiry date same information
			//#CM44342
			// The key contains Expiry date when defined in WmsParam as an item which uniquely stocks it. 
			boolean found = false;
			int cnt = 0;
			int multi = 0;
			for( ; cnt < stks.length ; cnt++ )
			{
				if( checkMulti(param[i].getConsignorCode(), param[i].getItemCode(),
								param[i].getCasePieceFlag(), param[i].getUseByDate(),
								stks[cnt].getConsignorCode(), stks[cnt].getItemCode(),
								stks[cnt].getCasePieceFlag(), stks[cnt].getUseByDate()) )
				{
					//#CM44343
					// Same information existed as a stock of the shelf. 
					found = true;
					break;
				}
			}

			//#CM44344
			// Check Piling up increase because same information existed as a stock of the shelf.
			if( found )
			{
				//#CM44345
				// Piling up increase
				multi = 1;
				//#CM44346
				// Overflow check
				long inputqty = stks[cnt].getStockQty()
						+ (long) param[i].getStorageCaseQty() * (long) param[i].getEnteringQty()
						+ (long) param[i].getStoragePieceQty();
				if (inputqty > WmsParam.MAX_STOCK_QTY)
				{
					String strMaxQty =
							WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY - stks[cnt].getStockQty());

					//#CM44347
					// 6023272 = No. {0} Input the value following {2} to {1}. 
					wMessage = "6023272" + wDelim
							+ rowno	+ wDelim
							+ DisplayText.getText("LBL-W0377") + wDelim
							+ strMaxQty;
					return false;
				}
			}
			else
			{
				//#CM44348
				// Assortment
				//#CM44349
				// Item code which does Assortment is for an empty palette. 
				if(param[i].getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY))
				{
					//#CM44350
					// Do the shelf stock and the consolidation check of Item code for an empty palette. 
					if( !checkEmptyCode(conn, param[i], stks) )
					{
						return false;
					}
				}
				//#CM44351
				// Overflow check
				long inputqty =
					(long) param[i].getStorageCaseQty() * (long) param[i].getEnteringQty()
						+ (long) param[i].getStoragePieceQty();
				if (inputqty > WmsParam.MAX_STOCK_QTY)
				{
					//#CM44352
					// 6023058 = {0}Input the value below {1}. Check it with Storage qty (total of the rows). 
					wMessage = "6023058" + wDelim
							+ DisplayText.getText("LBL-W0377") + wDelim
							+ MAX_STOCK_QTY_DISP;
					return false;
				}
			}
			mixedCount += multi;
		}
		
		//#CM44353
		// Add up a stock which does Piling up increase and an existing stock, and check the number of consolidation this time. 
		stkKey.KeyClear();
		stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stkKey.setLocationNo(param[0].getLocationNo());
		if ((mixedCount + stkHandler.count(stkKey)) >= getMaxMixedPalette(conn, param[0].getWareHouseNo()))
		{
			//#CM44354
			// 6023488 = Because the number of consolidation exceeds the number of maximum consolidation, it is not possible to set it. 
			wMessage = "6023488";
			return false;
		}
				
		return true;
	}

	//#CM44355
	/** 
	 * Check empty palette Item code of the input value and the stock data. <BR>
	 * False if input Item code is not for an empty palette when Item code of the stock is for an empty palette. <BR>
	 * False if input Item code is for an empty palette when Item code of the stock is not for an empty palette. <BR>
	 * @param  searchParam Instance of < CODE>AsScheduleParameter</CODE>Class with display data acquisition condition. <BR>
	 * @param  stockData Stock data<BR>
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean checkEmptyCode(Connection conn, Parameter searchParam, Stock[] stocks) throws ReadWriteException
	{
		try
		{
			//#CM44356
			// Refer to Empty palette status of the palette. 
			PaletteHandler pltHandler = new PaletteHandler(conn);
			PaletteSearchKey pltKey = new PaletteSearchKey();
			pltKey.setPaletteId(stocks[0].getPaletteid());
			Palette plt = (Palette) pltHandler.findPrimary(pltKey);
			if( plt.getEmpty() == Palette.STATUS_EMPTY )
			{
				//#CM44357
				// All names of articles can be loaded because there is no usual stock if the palette is an empty palette. 
				return true;
			}

			AsScheduleParameter param = (AsScheduleParameter) searchParam;

			//#CM44358
			// The stock is Item code for an empty palette referring to the stock of the palette or check it. 
			//#CM44359
			// The palette is not empty Palette but the stock is a steps volume palette for empty Pare. 
			boolean emppb = false;
			for( int i = 0 ; i < stocks.length ; i++ )
			{
				if( stocks[i].getConsignorCode().equals(WmsParam.EMPTYPB_CONSIGNORCODE) && 
					stocks[i].getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY) )
				{
					//#CM44360
					// Empty Palette steps volume
					emppb = true;
					break;
				}
			}

			//#CM44361
			// Is the steps volume palette?
			if( emppb )
			{
				//#CM44362
				// When the stock is a steps volume palette though this input commodity is not empty Palette, it is not possible to register. 
				if( !(param.getConsignorCode().equals(WmsParam.EMPTYPB_CONSIGNORCODE) &&
					  param.getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY)) )
				{
					//#CM44363
					// 6013262 = The stock cannot be consolidated an empty palette of the steps volume. 
					wMessage = "6013262";
					return false;
				}
			}
			else
			{
				//#CM44364
				// When empty Palette, and the stock is a working stock, this input commodity cannot be registered. 
				if( param.getConsignorCode().equals(WmsParam.EMPTYPB_CONSIGNORCODE) && 
					param.getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY) )
				{
					//#CM44365
					// 6013092=A usual stock and an empty palette cannot be consolidated. 
					wMessage = "6013092";
					return false;
				}
			}

			return true;
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM44366
	/** 
	 * Check Consignor Code, Item Code, and Case/Piece flag in the same way. <BR>
	 * When Expiry date is used, Expiry date is included in the same check. <BR>
	 * @param  consignorCode1 Consignor Code1
	 * @param  casePieceFlag1 CasePieceFlag1
	 * @param  useByDate1     Expiry date1
	 * @param  consignorCode2 Consignor Code2
	 * @param  casePieceFlag2 CasePieceFlag2
	 * @param  useByDate2     Expiry date2
	 * @return Return True when same information exists. 
	 */
	protected boolean checkMulti(String consignorCode1, String itemCode1, String casePieceFlag1, String useByDate1,
									String consignorCode2, String itemCode2, String casePieceFlag2, String useByDate2)
			
	{
		//#CM44367
		// Check whether Consignor Code, Item Code, Case/Piece flag, and Expiry date are the same. 
		if( consignorCode1.equals(consignorCode2) &&
			itemCode1.equals(itemCode2) &&
			casePieceFlag1.equals(casePieceFlag2) )
		{
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				//#CM44368
				// Expiry date
				if( useByDate1.equals(useByDate2) )
				{
					//#CM44369
					// Same information existed as a stock of the shelf. 
					return true;
				}
			}
			else
			{
				//#CM44370
				// Same information existed as a stock of the shelf. 
				return true;
			}
		}

		return false;
	}

	//#CM44371
	/**
	 * Lock Inventory information to be renewed. 
	 * Lock the stock and the palette which does Piling up increase. 
	 * <DIR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Shelf No..</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param conn Database connection
	 * @param param Screen parameter including Search condition
	 * @throws ReadWriteException   Notified when abnormality occurs by the connection with the data base.
	 * @throws LockTimeOutException It is notified when the lock is not opened even if time passes. 
	 */
	protected void lockStockData(Connection conn, AsScheduleParameter[] param)
							throws ReadWriteException, LockTimeOutException
	{
		ASPaletteHandler asplh = new ASPaletteHandler(conn);
		asplh.lockNowait(param[0].getLocationNo());
	}

	//#CM44372
	/**
	 * Renew the Inventory information table. <BR>
	 * @param conn Connection object with database<BR>
	 * @param stockid			Stock ID
	 * @param storagePlanQty	Storage qty
	 * @return True when it is possible to update it correctly, False when failing.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean updateStock(
		Connection conn,
		String stockid,
		int storagePlanQty)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			StockAlterKey stockAlterKey = new StockAlterKey();

			//#CM44373
			// Stock ID is set. 
			stockAlterKey.setStockId(stockid);

			//#CM44374
			// Renew Last updated Processing name. 
			stockAlterKey.updateLastUpdatePname(CLASS_ADDSTORAGE);

			//#CM44375
			// Stock status : like "Center Stocking".
			//#CM44376
			// Update the number of schedules of Inventory information.
			stockAlterKey.updatePlanQty(storagePlanQty);

			//#CM44377
			// Update of data
			stockHandler.modify(stockAlterKey);

			return true;
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM44378
	/**
	 * Renew the palette information table. <BR>
	 * @param conn Connection object with database<BR>
	 * @param paletteid Palette ID
	 * @return True when it is possible to update it correctly, False when failing.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean updatePalette(Connection conn, int paletteid)
									throws ReadWriteException, ScheduleException
	{
		try
		{
			PaletteHandler pltHandler = new PaletteHandler(conn);
			PaletteSearchKey pltSKey = new PaletteSearchKey();
			PaletteAlterKey pltAKey = new PaletteAlterKey();

			//#CM44379
			// Data retrieval
			//#CM44380
			// Palette ID
			pltSKey.setPaletteId(paletteid);
			Palette[] plt = (Palette[]) pltHandler.find(pltSKey);

			//#CM44381
			// Stock ID is set. 
			pltAKey.setPaletteId(paletteid);

			//#CM44382
			// Set stock Status : Retrieval reservation.
			pltAKey.updateStatus(Palette.RETRIEVAL_PLAN);

			if( plt[0].getStatus() == Palette.REGULAR )
			{
				//#CM44383
				// Set Drawing qty: Drawing settlement. 
				pltAKey.updateAllocation(Palette.ALLOCATED);
			}

			//#CM44384
			// Update of data
			pltHandler.modify(pltAKey);

			return true;
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM44385
	// Private methods ---------------------------------------------
}
//#CM44386
//end of class
