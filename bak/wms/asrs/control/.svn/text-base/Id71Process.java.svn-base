// $Id: Id71Process.java,v 1.2 2006/10/26 02:20:03 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33543
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id71;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM33544
/**
 * This class processes the inaccessible location report. This modifies the access status flag 
 * of the location range to either accessible or inaccessible
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/24</TD><TD>Y.Kawai</TD><TD>eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:20:03 $
 * @author  $Author: suresh $
 */
public class Id71Process extends IdProcess
{
	//#CM33545
	/**
	 * AGCNo.
	 */
	private int  wAgcNumber;
	//#CM33546
	/**
	 * Canceling the inaccessible state.
	 */
	private static final String A_IMPOSSIBLE_CANCEL = "0";
	//#CM33547
	/**
	 * State of inaccessibility
	 */
	private static final String Access_IMPOSSIBLE = "1";

	//#CM33548
	// Class variables -----------------------------------------------

	//#CM33549
	// Class method --------------------------------------------------
	//#CM33550
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:20:03 $") ;
	}

	//#CM33551
	// Constructors --------------------------------------------------
	//#CM33552
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id71Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}
	
	//#CM33553
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id71Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33554
	// Public methods ------------------------------------------------

	//#CM33555
	// Package methods -----------------------------------------------

	//#CM33556
	// Protected methods ---------------------------------------------
	//#CM33557
	/**
	 * Processes the communication message of inaccessible location report.
	 * Based on the received communication message, search <code>Shelf</code> 
	 * then updates the inaccessible location flag.
	 * However the call source needs to commit or rollback the transaction, as they are not done here.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		As21Id71 id71dt = new As21Id71(rdt) ;

		//#CM33558
		// Generate the instance of WareHouseHandler.
		WareHouseHandler wHouseHand = new WareHouseHandler(wConn);

		//#CM33559
		// Generate the instance of WareHouseSearchKey.
		WareHouseSearchKey whkey = new WareHouseSearchKey();

		//#CM33560
		// Generate the instance of ShelfHandler.
		ShelfHandler shelfHand = new ShelfHandler(wConn);
		ShelfAlterKey shlfAKey = new ShelfAlterKey();		

		//#CM33561
		// Processes as much as completion data.
		for (int i=0; i < id71dt.getNumberOfReports(); i++)
		{
			//#CM33562
			// Translate the storage division (warehouse no.) to int.
			int stClass = Integer.parseInt(id71dt.getStatusClass(i));

			//#CM33563
			// Search by using the storage division(warehous no.) as a Key
			whkey.KeyClear();
			whkey.setWareHouseNumber(stClass);
			WareHouse[] wHouseInfo = (WareHouse[])wHouseHand.find(whkey);

			//#CM33564
			// Retrieve the StationNumber serched by the Key.
			String wareHouse = wHouseInfo[0].getStationNumber();

			if(id71dt.getCondition(i).equals(A_IMPOSSIBLE_CANCEL))
			{
				//#CM33565
				// Canceling the inaccessible status
				shlfAKey.KeyClear(); 
				shlfAKey.setNBank(Integer.parseInt(id71dt.getBankNo(i)));
				shlfAKey.setNBay(Integer.parseInt(id71dt.getStartBay(i)),">=");
				shlfAKey.setNBay(Integer.parseInt(id71dt.getEndBay(i)), "<=");
				shlfAKey.setNLevel(Integer.parseInt(id71dt.getStLevelNo(i)), ">=");
				shlfAKey.setNLevel(Integer.parseInt(id71dt.getEnLevelNo(i)), "<=");
				shlfAKey.setWHStationNumber(wareHouse);
				shlfAKey.updateAccessNgFlag(Shelf.ACCESS_OK);
				shelfHand.modify(shlfAKey);				
			}
			else if(id71dt.getCondition(i).equals(Access_IMPOSSIBLE))
			{
				//#CM33566
				// Inaccessible
				shlfAKey.KeyClear();
				shlfAKey.setNBank(Integer.parseInt(id71dt.getBankNo(i)));
				shlfAKey.setNBay(Integer.parseInt(id71dt.getStartBay(i)),">=");
				shlfAKey.setNBay(Integer.parseInt(id71dt.getEndBay(i)), "<=");
				shlfAKey.setNLevel(Integer.parseInt(id71dt.getStLevelNo(i)), ">=");
				shlfAKey.setNLevel(Integer.parseInt(id71dt.getEnLevelNo(i)), "<=");
				shlfAKey.setWHStationNumber(wareHouse);
				shlfAKey.updateAccessNgFlag(Shelf.ACCESS_NG);
				shelfHand.modify(shlfAKey);
			}
			else
			{
				//#CM33567
				// 6024017=Invalid completion category. (Work Completion Report text) Category={0} mckey={1}
				Object[] tObj = new Object[1] ;
				tObj[0] = id71dt.getCondition(i);
				RmiMsgLogClient.write(6024017, LogMessage.F_WARN, "Id71Process", tObj);
				throw (new InvalidProtocolException("6024017" + wDelim + tObj[0])) ;
			}
		}
	}
}
//#CM33568
//end of class
