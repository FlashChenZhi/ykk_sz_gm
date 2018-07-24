package jp.co.daifuku.wms.base.dbhandler;

//#CM708549
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.utility.WareNaviSystemManager;
import jp.co.daifuku.wms.base.common.WmsParam;

//#CM708550
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * The class which retrieves, and updates the check and the location data of information for the location management. <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/13</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * <TR><TD>2006/01/06</TD><TD>Y.Okamura</TD><TD>The retrieval table of getAreaNo is added. </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/15 04:25:39 $
 * @author  $Author: kamala $
 */
public class LocateOperator extends Object
{

	//#CM708551
	// Class fields --------------------------------------------------

	//#CM708552
	// Class variables -----------------------------------------------
	//#CM708553
	/**
	 * Location information handler
	 */
	private LocateHandler wLocateHandler = null;

	//#CM708554
	/**
	 * Location information retrieval key
	 */
	private LocateSearchKey wLocateKey = null;

	//#CM708555
	/**
	 * Location information update key
	 */
	private LocateAlterKey wLocateAlterKey = null;
	
	//#CM708556
	/**
	 * Shelf information handler
	 */
	private ShelfHandler wShlfHndl = null;

	//#CM708557
	/**
	 * Shelf information retrieval key
	 */
	private ShelfSearchKey wShlfKey = null;

	//#CM708558
	/**
	 * Stock information handler
	 */
	private StockHandler wStockHandler = null;

	//#CM708559
	/**
	 * Stock information retrieval key
	 */
	private StockSearchKey wStockKey = null;

	//#CM708560
	/**
	 * WareNavi system information operation class
	 */
	private WareNaviSystemManager wWmsManager = null;

	//#CM708561
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM708562
	/**
	 * Movement rack location length
	 */
	private int IDM_LOCATION_LENGTH = 8;

	//#CM708563
	// Class method --------------------------------------------------
	//#CM708564
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/15 04:25:39 $");
	}

	//#CM708565
	// Constructors --------------------------------------------------
	//#CM708566
	/**
	 * Use this constructor when DB is retrieved and it updates it by using this class. 
	 */
	public LocateOperator(Connection conn) throws ReadWriteException, ScheduleException
	{
		wLocateHandler = new LocateHandler(conn);
		wLocateKey = new LocateSearchKey();
		wLocateAlterKey = new LocateAlterKey();
		wShlfHndl = new ShelfHandler(conn);
		wShlfKey = new ShelfSearchKey();
		wStockHandler = new StockHandler(conn);
		wStockKey = new StockSearchKey();
		wWmsManager = new WareNaviSystemManager(conn);
	}

	//#CM708567
	/**
	 * Use this constructor when DB is retrieved and it does not update it by using this class. 
	 */
	public LocateOperator()
	{
	}

	//#CM708568
	// Public methods ------------------------------------------------
	//#CM708569
	/**
	 * Confirm with specified shelf No while stocked and do the update processing of the state flag of location information. <BR>
	 * <BR>
	 * <Input data><BR>
	 * Mandatory Item*<BR>
	 * *Location No <BR>
	 * <BR>
	 * Process it in the following order. <BR>
	 * <DIR>
	 *   <U>1.Acquire movement rack package existence information from WmsParam. </U><BR>
	 *   <DIR> Do not do the following processing when there is no movement rack package.  </DIR>
	 *   <U>2.Check whether it is a movement rack location by using the checkIdmLocation method. </U><BR>
	 *   <DIR> Do not do the following processing when it is not a movement rack location.  </DIR>
	 *   <U>3.Do the retrieval processing of location information (DMLOCATE) with specified Location No. (For Update specification)</U><BR>
	 *   <DIR>
	 *     - Corresponding Location No. <BR>
	 *     Do not do the following processing when object location information does not exist.  <BR>
	 *   </DIR>
	 *   <U>4.Decide the presence of inventory information (DMSTOCK) under the following condition. </U><BR>
	 *   <DIR>
	 *     - Corresponding Location No. <BR>
	 *     - The quantity of stock is one or more.  <BR>
	 *     Do not do the following processing when object location information does not exist.  <BR>
	 *   </DIR>
	 *   <U>5.In the presence of inventory information (DMSTOCK), update location information (DMLOCATE). </U><BR>
	 *   <DIR><BR>
	 *     [Condition] <DIR>
	 *       - Corresponding Location No. <BR>
	 *     </DIR><BR>
	 *     [Update] <DIR>
	 *       - Update state flag to "Real shelf" when there is an inventory information. <BR>
	 *       - Update state flag to "Empty shelf" when there is no inventory information. <BR>
	 *     </DIR>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param pLocation Location No of object
	 * @param pClassName Update processing name
	 * @return None
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void modifyLocateStatus(String pLocation, String pClassName) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM708570
			// End processing when there is no movement rack package. 
			if (!wWmsManager.isIdmPack())	return ;

			//#CM708571
			// Location No of the parameter is a movement rack location form or check it. 
			if (!checkIdmLocation(pLocation))		return ;

			//#CM708572
			// Acquire location information with Location No of the parameter. (For Update specification)
			wLocateKey.KeyClear() ;
			wLocateKey.setLocationNo(pLocation) ;
			
			Locate[] locate = (Locate[])wLocateHandler.findForUpdate(wLocateKey) ;
			if ((locate == null) || (locate.length <= 0))		return ;

			//#CM708573
			// Decide the presence of the inventory information with object Location No. 
			wStockKey.KeyClear() ;
			//#CM708574
			// Corresponding Location No.
			wStockKey.setLocationNo(pLocation);
			//#CM708575
			// The quantity of stock is one or more. 
			wStockKey.setStockQty(0, ">");
			//#CM708576
			// The state flag : excluding the deletion. 
			wStockKey.setStatusFlag(Stock.STATUS_FLAG_DELETE, "!=");
			
			int rCount = wStockHandler.count(wStockKey) ;
			
			//#CM708577
			// Do Update of location information. 
			wLocateAlterKey.KeyClear() ;
			wLocateAlterKey.setLocationNo(pLocation) ;
			
			//#CM708578
			// Set the Update value by the presence of the stock. 
			if (rCount <= 0)
			{
				if (locate[0].getStatusFlag().equals(Locate.Locate_StatusFlag_EMPTY))	return;
				//#CM708579
				// Update the state flag to "Empty shelf" because there is no object stock.
				wLocateAlterKey.updateStatusFlag(Locate.Locate_StatusFlag_EMPTY) ;
			}
			else
			{
				if (locate[0].getStatusFlag().equals(Locate.Locate_StatusFlag_OCCUPIED))	return;
				//#CM708580
				// Update the state flag to "Real shelf" because there is an object stock.
				wLocateAlterKey.updateStatusFlag(Locate.Locate_StatusFlag_OCCUPIED) ;
			}
			//#CM708581
			// Set Update processing name
			wLocateAlterKey.updateLastUpdatePname(pClassName);

			wLocateHandler.modify(wLocateAlterKey) ;
			
			return ;
		}
		catch (NotFoundException e)
		{
			return ;
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}

	}
	
	//#CM708582
	/**
	 * Retrieve area No. to which specified Location No corresponds, and return it. <BR>
	 * <BR>
	 * Process it in the following order. <BR>
	 * <DIR>
	 *   <U>1.Retrieve whether to agree to the shelf of the movable rack. Retrieve DmLocate. </U><BR>
	 *   <DIR>Return the area No. when there is corresponding data. </DIR>
	 *   <U>2.Retrieve whether to agree to the shelf of ASRS. Retrieve Shelf. </U><BR>
	 *   <DIR>Return the warehouse station No. when there is corresponding data. </DIR>
	 *   <U>3.Acquire from WmsParam, and return area No. of a flat putting when not applying to the above-mentioned. </U><BR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param pLocation Location No of object
	 * @return None
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public String getAreaNo(String pLocation) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM708583
			// Agree to the shelf of the movable rack or retrieve it. 
			if (wWmsManager.isIdmPack())
			{
				wLocateKey.KeyClear() ;
				wLocateKey.setLocationNo(pLocation);
				if (wLocateHandler.count(wLocateKey) > 0)
				{
					Locate locate = (Locate) wLocateHandler.findPrimary(wLocateKey) ;
					if (locate != null)
					{
						return locate.getAreaNo();
					}
				}
			}
			
			//#CM708584
			// Agree to the shelf of ASRS or retrieve it. 
			wShlfKey.KeyClear();
			wShlfKey.setStationNumber(pLocation);
			if (wShlfHndl.count(wShlfKey) > 0)
			{
				Shelf shlf = (Shelf) wShlfHndl.findPrimary(wShlfKey);
				if (shlf != null)
				{
					return shlf.getWHStationNumber();
				}
			}
			
			return WmsParam.FLOOR_AREA_NO;
			
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}

	}
	
	//#CM708585
	/**
	 * Check whether the location exists in the Shelf table. <BR>
	 * @param locationNo Checked location No
	 * @return boolean 
	 */
	public boolean isAsrsLocation(String locationNo) throws ReadWriteException
	{
		wShlfKey.KeyClear();
		wShlfKey.setStationNumber(locationNo);

		if (wShlfHndl.count(wShlfKey) > 0)
		{
			return true;
		}
		else
		{
			return false;		
		}

	}
	
	//#CM708586
	// Protected methods ------------------------------------------------
	//#CM708587
	/**
	 * Check whether it is a location form of the movable rack. <BR>
	 * @param pLocationNo Checked location No
	 * @return boolean 
	 */
	protected boolean checkIdmLocation(String pLocation)
	{
		//#CM708588
		// Location No recovers from an error at Null or Blank. 
		if (StringUtil.isBlank(pLocation))		return false;
		
		//#CM708589
		// It recovers from an error when the length of the location is not corresponding. 
		if (pLocation.length() != IDM_LOCATION_LENGTH)	return false;
		
		return true;
	}

	//#CM708590
	// Private methods ------------------------------------------------

} 
//#CM708591
//end of class
