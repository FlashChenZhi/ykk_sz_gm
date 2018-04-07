//#CM44617
//$Id: AsLocationWorkingInquirySCH.java,v 1.2 2006/10/30 01:04:32 suresh Exp $

//#CM44618
/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.asrs.report.AsLocationWorkingInquiryWriter;

//#CM44619
/**
 * Designer : Y.Osawa <BR>
 * Maker : Y.Osawa <BR>
 * <BR>
 * Class to do the stock inquiry according to WEB ASRS Location. <BR>
 * Receive the content input from the screen as parameter, and do another stock inquiry of ASRS Location. <BR>
 * Do not do the commit and rollback of the transaction though each Method of this Class must <BR>
 * use the connection object and do the update processing of the receipt data base.  <BR> 
 * Process it in this Class as follows. <BR>
 * <BR>
 * 1.Initial display processing(< CODE>initFind() </ CODE > method) <BR> 
 * <BR>
 * <DIR>
 *   Return corresponding Consignor Code when only one Consignor Code exists in the inventory information. <BR> 
 *   Return null when pertinent data does not exist or it exists by two or more. <BR> 
 *   <BR>
 *   [Search condition] <BR>
 *   <DIR>
 *     Stock status is center stocking.<BR>
 *     Stock qty is one or more. <BR>
 *     AS/RS Stock <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Process when Print button is pressed.(<CODE>startSCH()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Receive the content input from the screen as parameter, and start the Print processing of stock List according to ASRS Location. <BR>
 *   Check the presence of information for the print by the content of parameter, and do the error notification when Object data none. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 *   <BR>
 *   [parameter] *Mandatory Input<BR>
 *   <DIR>
 *     Warehouse* <BR>
 *     Consignor Code* <BR>
 *     Beginning shelf <BR>
 *     End shelf <BR>
 *     Item Code <BR>
 *     Case/Piece flag <BR>
 *   </DIR>
 *   <BR>
 *   [Return data] <BR>
 *   <DIR>
 *     Result notification <BR>
 *   </DIR>
 *   <BR>
 *   [Processing condition check] <BR>
 *   2-1.Acquire the number of cases of object information from Inventory information under the following condition. <BR>
 *   <DIR>
 *     [Search condition] <BR> 
 *     <DIR>
 *       Condition specified in parameter<BR>
 *       Corresponding Warehouse<BR>
 *       Corresponding Consignor Code<BR>
 *       Above Beginning shelf No<BR>
 *       Below End shelf No<BR>
 *       Corresponding Item Code<BR>
 *       Corresponding Case/Piece flag<BR>
 *       Stock status is center stocking.<BR>
 *       Stock qty is one or more. <BR>
 *     </DIR>
 *     "Object data none" is returned when the number of above-mentioned objects does not exist. (In the message. )
 *   </DIR>
 *   <BR>
 *   [Print process] <BR>
 *   Use <CODE>AsLocationWorkingInquiryWriter</CODE>Class and do Print process of stock List according to ASRS Location. <BR>
 *   <DIR>
 *     <BR>
 *     [parameter] *Mandatory Input<BR>
 *     <DIR>
 *       Warehouse* <BR>
 *       Consignor Code* <BR>
 *       Beginning shelf <BR>
 *       End shelf <BR>
 *       Item Code <BR>
 *       Case/Piece flag <BR>
 *     </DIR>
 *     <BR>
 *     [Return data] <BR>
 *     <DIR>
 *       Print result* <BR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/24</TD><TD>Y.Osawa</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:04:32 $
 * @author  $Author: suresh $
 */
public class AsLocationWorkingInquirySCH extends AbstractAsrsControlSCH
{

	//#CM44620
	// Class variables -----------------------------------------------

	//#CM44621
	// Class method --------------------------------------------------
	//#CM44622
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:04:32 $");
	}

	//#CM44623
	// Constructors --------------------------------------------------
	//#CM44624
	/**
	 * Initialize this Class. 
	 */
	public AsLocationWorkingInquirySCH()
	{
		wMessage = null;
	}

	//#CM44625
	// Public methods ------------------------------------------------

	//#CM44626
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen.  <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation.  <BR>
	 * Moreover, being called when this processing is not used is slow as for <CODE>ScheduleException</CODE >. <BR>
	 * Set null in <CODE>searchParam</CODE> when you do not need Search condition. <BR>
	 * @param conn Connection object with database
	 * @param searchParam Class which succeeds to <CODE>Parameter</CODE> Class with Search condition
	 * @return Class which mounts < CODE>Parameter</CODE > interface where retrieval result is included
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();

		AsScheduleParameter dispData = new AsScheduleParameter();
		
		//#CM44627
		// Data retrieval
		//#CM44628
		// Stock status(Center Stocking)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM44629
		// Stock qty must be one or more. 
		searchKey.setStockQty(1, ">=");

		//#CM44630
		// Acquire only the stock of AS/RS. 
		AreaOperator areaOpe = new AreaOperator(conn);
		int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
		searchKey.setAreaNo(areaOpe.getAreaNo(areaType));

		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		int count = stockHandler.count(searchKey);
		if (count == 1)
		{
			try
			{
				Stock[] stock = (Stock[]) stockHandler.find(searchKey);
				dispData.setConsignorCode(stock[0].getConsignorCode());
			}
			catch (Exception e)
			{
				return new AsScheduleParameter();
			}
		}

		return dispData;	
	}

	//#CM44631
	/**
	 * Receive the content input from the screen as parameter, and start the update process of necessary information and the List processing. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation.  <BR>
	 * Moreover, being called when this processing is not used is slow as for <CODE>ScheduleException</CODE >. <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Array of instance of <CODE>StockControlParameter</CODE> Class with set content. 
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		AsScheduleParameter rparam = (AsScheduleParameter) startParams[0];

		//#CM44632
		// Stock List according to ASRS Location
		AsLocationWorkingInquiryWriter sdWriter = createWriter(conn, rparam);

		if (sdWriter.startPrint())
		{
			//#CM44633
			// The print ended normally. 
			wMessage = "6001010";
			return true;
		}
		else
		{
			wMessage = sdWriter.getMessage();
			return false;
		}

	}
	
	//#CM44634
	/**
	 * Acquire the number of cases to be printed based on information input from the screen. <BR>
	 * Return 0 when there is no object data or is an input error. <BR>
	 * Acquire Error Message by calling in case of 0 and using < CODE>getMessage</CODE > by former processing. <BR>
	 * 
	 * @param conn Connection object with database
	 * @param countParam < CODE>Parameter</CODE > object including Search condition
	 * @return The number of cases to be printed
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated is generated. 
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		AsScheduleParameter param = (AsScheduleParameter) countParam;
		
		//#CM44635
		// Set Search condition and make Print processClass. 
		AsLocationWorkingInquiryWriter writer = createWriter(conn, param);
		//#CM44636
		// Acquire the number of objects. 
		int result = writer.count();
		if (result == 0)
		{
			//#CM44637
			// 6003010 = There was no print data. 
			wMessage = "6003010";
		}
		
		return result;
	}

	//#CM44638
	// Package methods -----------------------------------------------

	//#CM44639
	// Protected methods ---------------------------------------------
	
	//#CM44640
	/**
	 * Set information input from the screen in Print processClass, and generate Print processClass. <BR>
	 * 
	 * @param conn Connection object with database
	 * @param rparam Parameter object including Search condition
	 * @return Print processClass
	 */
	protected AsLocationWorkingInquiryWriter createWriter(Connection conn, AsScheduleParameter rparam)
	{
		AsLocationWorkingInquiryWriter sdWriter = new AsLocationWorkingInquiryWriter(conn);
		
		sdWriter.setAreaNo(rparam.getAreaNo());
		sdWriter.setConsignorCode(rparam.getConsignorCode());
		sdWriter.setFromLocationNo(rparam.getFromLocationNo());
		sdWriter.setToLocationNo(rparam.getToLocationNo());
		sdWriter.setItemCode(rparam.getItemCode());
		if (rparam.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			sdWriter.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
		}
		else if (rparam.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			sdWriter.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
		}
		else if (rparam.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			sdWriter.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
		}
		return sdWriter;
	}

	//#CM44641
	// Private methods -----------------------------------------------

}
//#CM44642
// end of class
