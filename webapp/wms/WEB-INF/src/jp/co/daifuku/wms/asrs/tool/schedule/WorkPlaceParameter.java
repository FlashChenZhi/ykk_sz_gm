// $Id: WorkPlaceParameter.java,v 1.2 2006/10/30 02:51:57 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;
//#CM52297
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

//#CM52298
/**<en>
 * This is an entity class used when setting up the stations.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:51:57 $
 * @author  $Author: suresh $
 </en>*/
public class WorkPlaceParameter extends Parameter
{
	//#CM52299
	// Class fields --------------------------------------------------

	//#CM52300
	// Class variables -----------------------------------------------
	
	//#CM52301
	/**<en>
	* path of product number folder
	</en>*/
	String wFilePath = "";
	
	//#CM52302
	/**<en> parent station no. </en>*/

	protected String wParentNumber = "" ;
	
	//#CM52303
	/**<en> name of the parent station </en>*/

	protected String wParentName = "" ;

	//#CM52304
	/**<en> workshop type</en>*/

	protected int wWorkPlaceType = 0 ;
	
	//#CM52305
	/**<en> station no. </en>*/

	protected String wNumber = "" ;

	//#CM52306
	/**<en> station name </en>*/

	protected String wName = ""  ;
	
	//#CM52307
	/**<en> station type </en>*/

	protected int wType = 0 ;
	
	//#CM52308
	/**<en> warehouse no. the station belongs to </en>*/

	protected String wWareHouseStationNumber ;

	//#CM52309
	/**<en> warehouse name the station belongs to </en>*/

	protected String wWareHouseName ;
	
	//#CM52310
	/**<en> class name </en>*/

	protected String wClassName = "" ;
	
	//#CM52311
	/**<en> aisle station no.. </en>*/

	protected String wAisleNumber = "" ;
	
	//#CM52312
	/**<en> main station </en>*/

	protected int wMain = 0 ;
	
	//#CM52313
	/**<en> number of carry instruction sendable </en>*/

	protected int wMaxInstruction = 0 ;
	
	//#CM52314
	/**<en> number of retrieval instruction sendable </en>*/

	protected int wMaxPaletteQuantity = 0 ;
	
	//#CM52315
	/**<en> controller no. </en>*/

    protected int wControllerNumber = 0 ;
    
    //#CM52316
    /**<en> division of sendability </en>*/

	protected int wSendable = 0 ;

	//#CM52317
	// Class method --------------------------------------------------
	//#CM52318
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:51:57 $") ;
	}

	//#CM52319
	// Constructors --------------------------------------------------
	//#CM52320
	/**<en>
	 * Create the new instance of <code>Station</code>. If the instance which owns the stations
	 * already defined is wanted, please utilize <code>StationFactory</code> class.
	 * @param conn  :as <code>Connection</code> transaction control for database connection 
	 * is not conducted internally, it is necessary to commit the transaction externally.
	 * @param snum  :station no. preserved
	 * @see StationFactory
	 </en>*/
	public WorkPlaceParameter()
	{
	}

	//#CM52321
	// Public methods ------------------------------------------------
	//#CM52322
	/**<en>
	* Set the path of product number folder.
	</en>*/
	public void setFilePath(String filepath)
	{
		wFilePath = filepath;
	}
	
	//#CM52323
	/**<en>
	* Return the path of product number folder.
	</en>*/
	public String getFilePath()
	{
		return wFilePath;
	}
	
	//#CM52324
	/**<en>
	 * Set the parent station no.
	 * @param ParentNumber
	 </en>*/
	public void setParentNumber(String pnum)
	{
		wParentNumber = pnum;
	}

	//#CM52325
	/**<en>
	 * Retrieves the parent station no.
	 * @return wParentNumber
	 </en>*/
	public String getParentNumber()
	{
		return wParentNumber;
	}
	
		//#CM52326
		/**<en>
	 * Set the name of the parent station.
	 * @param ParentName
	 </en>*/
	public void setParentName(String pnum)
	{
		wParentName = pnum;
	}

	//#CM52327
	/**<en>
	 * Retrieves the name of the parent station.
	 * @return wParentName
	 </en>*/
	public String getParentName()
	{
		return wParentName;
	}
	
	//#CM52328
	/**<en>
	 * Set the workshop type.
	 * @param WorkPlaceType
	 </en>*/
	public void setWorkPlaceType(int type)
	{
		wWorkPlaceType = type ;
	}
	
	//#CM52329
	/**<en>
	 * Retrieves the workshop type.
	 * @return wWorkPlaceType
	 </en>*/
	public int getWorkPlaceType()
	{
		return wWorkPlaceType ;
	}
		
	//#CM52330
	/**<en>
	 * Set the station no.
	 * @param StationNumber
	 </en>*/
	public void setNumber(String stnm)
	{
		wNumber = stnm ;
	}
	
	//#CM52331
	/**<en>
	 * Retrieves the station no.
	 * @return wNumber
	 </en>*/
	public String getNumber()
	{
		return wNumber ;
	}
	
	//#CM52332
	/**<en>
	 * Set the station name.
	 * @param Name
	 </en>*/
	public void setName(String nm)
	{
		wName = nm ;
	}

	//#CM52333
	/**<en>
	 * Retrieves the station name.
	 * @return wName
	 </en>*/
	public String getName()
	{
		return wName ;
	}
	
	//#CM52334
	/**<en>
	 * Set the station type.
	 * @param Type
	 </en>*/
	public void setType(int type)
	{
		wType = type ;
	}

	//#CM52335
	/**<en>
	 * Retrieves the station type.
	 * @return wType
	 </en>*/
	public int getType()
	{
		return wType;
	}
	
	//#CM52336
	/**<en>
	 * Set the warehouse no.
	 * @param WareHouseStationNumber
	 </en>*/
	public void setWareHouseStationNumber(String whnum)
	{
		wWareHouseStationNumber = whnum;
	}

	//#CM52337
	/**<en>
	 * Retrieves the warehouse no.
	 * @return wWareHouseStationNumber
	 </en>*/
	public String getWareHouseStationNumber()
	{
		return wWareHouseStationNumber;
	}

	//#CM52338
	/**<en>
	 * Set the warehouse name.
	 * @param WareHouseName
	 </en>*/
	public void setWareHouseName(String wh)
	{
		wWareHouseName = wh;
	}

	//#CM52339
	/**<en>
	 * Retrieves the warehouse name.
	 * @return wWareHouseName
	 </en>*/
	public String getWareHouseName()
	{
		return wWareHouseName;
	}
	
	//#CM52340
	/**<en>
	 * Set the class name.
	 * @param ClassName
	 </en>*/
	public void setClassName(String cn)
	{
		wClassName = cn;
	}

	//#CM52341
	/**<en>
	 * Retrieves the class name.
	 * @return wClassName
	 </en>*/
	public String getClassName()
	{
		return wClassName;
	}
	
	//#CM52342
	/**<en>
	 * Set the aisle station no.
	 * @param AisleNumber
	 </en>*/
	public void setAisleNumber(String as)
	{
		wAisleNumber = as;
	}

	//#CM52343
	/**<en>
	 * Retrieves the aisle station no.
	 * @return wAisleNumber
	 </en>*/
	public String getAisleNumber()
	{
		return wAisleNumber;
	}
	
	//#CM52344
	/**<en>
	 * Set the main station.
	 * @param Main
	 </en>*/
	public void setMainStation(int ms)
	{
		wMain = ms ;
	}

	//#CM52345
	/**<en>
	 * Retrieves the main station.
	 * @return wMain
	 </en>*/
	public int getMainStation()
	{
		return wMain;
	}

	//#CM52346
	/**<en>
	 * Set the number of carry instruction sendable.
	 * @param nm number of carry instruction sendable
	 </en>*/
	public void setMaxInstruction(int nm)
	{
		wMaxInstruction = nm ;
	}

	//#CM52347
	/**<en>
	 * Retrieves the number of carry instruction sendable.
	 * @return   number of carry instruction sendable
	 </en>*/
	public int getMaxInstruction()
	{
		return wMaxInstruction;
	}
	
	//#CM52348
	/**<en>
	 * Set the number of retrieval instruction sendable.
	 * @param pnum number of retrieval instruction sendable
	 </en>*/
	public void setMaxPaletteQuantity(int pnum)
	{
		wMaxPaletteQuantity = pnum ;
	}

	//#CM52349
	/**<en>
	 * Retrieves the number of retrieval instruction sendable.
	 * @return    number of retrieval instruction sendable
	 </en>*/
	public int getMaxPaletteQuantity()
	{
		return wMaxPaletteQuantity;
	}

	//#CM52350
	/**<en>
	 * Set the controller no.
	 * @param ControllerNumber
	 </en>*/
	public void setControllerNumber(int arg)
	{
		wControllerNumber = arg;
	}

	//#CM52351
	/**<en>
	 * Retrieves the controller no.
	 * @return wControllerNumber
	 </en>*/
	public int getControllerNumber()
	{
		return wControllerNumber;
	}

	//#CM52352
	/**<en>
	 * Set the division of sendability.
	 * @param Sendable
	 </en>*/
	public void setSendable(int snd)
	{
		wSendable = snd ;
	}

	//#CM52353
	/**<en>
	 * Retrieves the division of sendability.
	 * @param wSendable
	 </en>*/
	public int getSendable()
	{
		return wSendable ;
	}


}
//#CM52354
//end of class

