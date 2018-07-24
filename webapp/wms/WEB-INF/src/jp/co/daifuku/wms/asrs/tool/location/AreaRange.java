// $Id: AreaRange.java,v 1.2 2006/10/30 02:33:27 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49396
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntityHandler;

//#CM49397
/**<en>
 * This class operates the information of warehouse range that the area controls.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/08/06</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:27 $
 * @author  $Author: suresh $
 </en>*/
public class AreaRange extends ToolEntity
{
	//#CM49398
	// Class fields --------------------------------------------------

	//#CM49399
	// Class variables -----------------------------------------------
	//#CM49400
	/**<en>
	 * Area ID
	 </en>*/
	protected int wAreaId ;
	
	//#CM49401
	/**<en>
	 * Warehouse station no.
	 </en>*/
	protected String wWHStationNumber ;
	
	//#CM49402
	/**<en>
	 * Workshop no.
	 </en>*/
	protected String wWPStationNumber ;


	//#CM49403
	// Class method --------------------------------------------------
	//#CM49404
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:27 $") ;
	}

	//#CM49405
	// Constructors --------------------------------------------------
	//#CM49406
	/**<en>
	 * Construct a new <CODE>AreaRange</CODE>.
	 </en>*/
	public AreaRange()
	{
	}

	//#CM49407
	/**<en>
	 * Construct a new <CODE>AreaRange</CODE>.
	 * @param areaid   :area ID
	 </en>*/
	public AreaRange(int areaid )
	{
		//#CM49408
		//<en> Set as an instance variable.</en>
		setAreaId(areaid);
	}
	
	//#CM49409
	/**<en>
	 * Construct a new <CODE>AreaRange</CODE>.
	 * @param areaid     :area ID
	 * @param whstnumber :warehouse station no.
	 * @param wpstnumber :workshop no.
	 * @param ehandl     :instance handler
	 </en>*/
	public AreaRange(int	 areaid,
				  String whstnumber,
				  String wpstnumber,
				  ToolEntityHandler	ehandl)
	{
		wAreaId			 = areaid;
		wWHStationNumber = whstnumber;
		wWPStationNumber = wpstnumber;
		wHandler		 = ehandl;
	}

	//#CM49410
	// Public methods ------------------------------------------------
	//#CM49411
	/**<en>
	 * Set the area ID.
	 * @param id :area ID
	 </en>*/
	public void setAreaId(int id)
	{
		wAreaId = id;
	}

	//#CM49412
	/**<en>
	 * Retrieve the area ID.
	 * @return :area ID
	 </en>*/
	public int getAreaId()
	{
		return wAreaId;
	}

	//#CM49413
	/**<en>
	 * Set the warehouse no.
	 * @param wh :warehouse no.
	 </en>*/
	public void setWHStationNumber(String wh)
	{
		wWHStationNumber = wh;
	}
	
	//#CM49414
	/**<en>
	 * Retrieve the warehouse no.
	 * @return :warehouse no.
	 </en>*/
	public String getWHStationNumber()
	{
		return wWHStationNumber;
	}

	//#CM49415
	/**<en>
	 * Set the workshop no.
	 * @param wp :workshop no.
	 </en>*/
	public void setWPStationNumber(String wp)
	{
		wWPStationNumber = wp;
	}

	//#CM49416
	/**<en>
	 * Retrieve the workshop no.
	 * @return :workshop no.
	 </en>*/
	public String getWPStationNumber()
	{
		return wWPStationNumber;
	}

	//#CM49417
	// Package methods -----------------------------------------------

	//#CM49418
	// Protected methods ---------------------------------------------

	//#CM49419
	// Private methods -----------------------------------------------

}
//#CM49420
//end of class

