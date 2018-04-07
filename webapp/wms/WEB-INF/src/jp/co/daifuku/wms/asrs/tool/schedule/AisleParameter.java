// $Id: AisleParameter.java,v 1.2 2006/10/30 02:52:07 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;
//#CM50343
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

//#CM50344
/**<en>
 * This is an entity class which will be used in aisle setting.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:07 $
 * @author  $Author: suresh $
 </en>*/
public class AisleParameter extends Parameter
{
	//#CM50345
	// Class fields --------------------------------------------------

	//#CM50346
	// Class variables -----------------------------------------------
	//#CM50347
	/**<en>
	* Path of the product number folder
	</en>*/
	String wFilePath = "";
	
    //#CM50348
    /**<en> Storage type </en>*/

    protected int wWarehouseNumber = 0;
    
    //#CM50349
    /**<en> Aisle station no. </en>*/

    protected String wAisleStationNumber = "";

    //#CM50350
    /**<en> Aisle no. </en>*/

    protected int wAisleNumber = 0;

    //#CM50351
    /**<en> AGC No. </en>*/

    protected int wAGCNumber = 0;

    //#CM50352
    /**<en> Starting bank </en>*/

    protected int wSBank = 0;

    //#CM50353
    /**<en> Ending bank </en>*/

    protected int wEBank = 0;

    //#CM50354
    /**<en> Starting bay </en>*/

    protected int wSBay = 0;

    //#CM50355
    /**<en> Ending bay </en>*/

    protected int wEBay = 0;

    //#CM50356
    /**<en> Starting level </en>*/

    protected int wSLevel = 0;

    //#CM50357
    /**<en> Ending level </en>*/

    protected int wELevel = 0;

	//#CM50358
	/** StartAisle position */

	protected int wSAislePosition = 0;

	//#CM50359
	/** End Aisle position */

	protected int wEAislePosition = 0;

	//#CM50360
	// Class method --------------------------------------------------
	 //#CM50361
	 /**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:07 $") ;
	}

	//#CM50362
	// Constructors --------------------------------------------------
	//#CM50363
	/**<en>
	 * Utilize this constructor.
	 * @param conn <CODE>Connection</CODE>
	 </en>*/
	public AisleParameter()   
	{
	}

	//#CM50364
	// Public methods ------------------------------------------------
	//#CM50365
	/**<en>
	* Set the path of product number folder.
	</en>*/
	public void setFilePath(String filepath)
	{
		wFilePath = filepath;
	}
	
	//#CM50366
	/**<en>
	* Return the path of product number folder.
	</en>*/
	public String getFilePath()
	{
		return wFilePath;
	}


	//#CM50367
	/**<en>
	 * Retrieve the storage type.
	 * @return wWarehouseNumber
	 </en>*/
	public int getWarehouseNumber()
	{
		return wWarehouseNumber;
	}
	//#CM50368
	/**<en>
	 * Set the storage type.
	 * @param WarehouseNumber
	 </en>*/
	public void setWarehouseNumber(int arg)
	{
		wWarehouseNumber = arg;
	}


	//#CM50369
	/**<en>
	 * Retrieve the aisle station no.
	 * @return wAisleStationNumber
	 </en>*/
	public String getAisleStationNumber()
	{
		return wAisleStationNumber;
	}
	//#CM50370
	/**<en>
	 * Set the aisle station no.
	 * @param AisleStationNumber
	 </en>*/
	public void setAisleStationNumber(String arg)
	{
		wAisleStationNumber = arg;
	}



	//#CM50371
	/**<en>
	 * Retrieve the aisle no.
	 * @return wAisleNumber
	 </en>*/
	public int getAisleNumber()
	{
		return wAisleNumber;
	}
	//#CM50372
	/**<en>
	 * Set the aisle no.
	 * @param AisleNumber
	 </en>*/
	public void setAisleNumber(int arg)
	{
		wAisleNumber = arg;
	}
	
	//#CM50373
	/**<en>
	 * Retrieve the AGC No.
	 * @return wAGCNumber
	 </en>*/
	public int getAGCNumber()
	{
		return wAGCNumber;
	}
	//#CM50374
	/**<en>
	 * Set the AGC No.
	 * @param AGCNumber
	 </en>*/
	public void setAGCNumber(int arg)
	{
		wAGCNumber = arg;
	}
	//#CM50375
	/**<en>
	 * Retrieve the starting bank.
	 * @return wSBank
	 </en>*/
	public int getSBank()
	{
		return wSBank;
	}
	//#CM50376
	/**<en>
	 * Set the starting bank.
	 * @param :starting bank
	 </en>*/
	public void setSBank(int arg)
	{
		wSBank = arg;
	}
	//#CM50377
	/**<en>
	 * Retrieve the ending bank.
	 * @return wEBank
	 </en>*/
	public int getEBank()
	{
		return wEBank;
	}
	//#CM50378
	/**<en>
	 * Set the ending bank.
	 * @param ending bank
	 </en>*/
	public void setEBank(int arg)
	{
		wEBank = arg;
	}
	//#CM50379
	/**<en>
	 * Retrieve the starting bay.
	 * @return wSBay
	 </en>*/
	public int getSBay()
	{
		return wSBay;
	}
	//#CM50380
	/**<en>
	 * Set the starting bay.
	 * @param starting bay
	 </en>*/
	public void setSBay(int arg)
	{
		wSBay = arg;
	}

	//#CM50381
	/**<en>
	 * Retrieve the ending bay.
	 * @return wEBay
	 </en>*/
	public int getEBay()
	{
		return wEBay;
	}
	//#CM50382
	/**<en>
	 * Set the ending bay.
	 * @param ending bay
	 </en>*/
	public void setEBay(int arg)
	{
		wEBay = arg;
	}
	//#CM50383
	/**<en>
	 * Retrieve hte starting level.
	 * @return wSLevel
	 </en>*/
	public int getSLevel()
	{
		return wSLevel;
	}
	//#CM50384
	/**<en>
	 * Set the starting level.
	 * @param starting level
	 </en>*/
	public void setSLevel(int arg)
	{
		wSLevel = arg;
	}
	
	//#CM50385
	/**<en>
	 * Retrieve the ending level.
	 * @return wELevel
	 </en>*/
	public int getELevel()
	{
		return wELevel;
	}
	//#CM50386
	/**<en>
	 * Set the ending level.
	 * @param ending level
	 </en>*/
	public void setELevel(int arg)
	{
		wELevel = arg;
	}
	
	//#CM50387
	/**
	 * Get StartAisle position.
	 * @return wSAislePosition
	 */
	public int getSAislePosition()
	{
		return wSAislePosition;
	}
	//#CM50388
	/**
	 * Set StartAisle position.
	 * @param StartBank
	 */
	public void setSAislePosition(int arg)
	{
		wSAislePosition = arg;
	}

	//#CM50389
	/**
	 * Get End Aisle position.
	 * @return wEAislePosition
	 */
	public int getEAislePosition()
	{
		return wEAislePosition;
	}
	//#CM50390
	/**
	 * Set End Aisle position.
	 * @param EndBank
	 */
	public void setEAislePosition(int arg)
	{
		wEAislePosition = arg;
	}

	//#CM50391
	// Package methods -----------------------------------------------

	//#CM50392
	// Protected methods ---------------------------------------------

	//#CM50393
	// Private methods -----------------------------------------------
	
}
