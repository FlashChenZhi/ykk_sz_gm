// $Id: UnavailableLocationParameter.java,v 1.2 2006/10/30 02:51:59 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;
//#CM51919
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

//#CM51920
/**<en>
 * This is an entity class which will be used when setting the unavailable locations.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/28</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:51:59 $
 * @author  $Author: suresh $
 </en>*/
public class UnavailableLocationParameter extends Parameter
{
	//#CM51921
	// Class fields --------------------------------------------------

	//#CM51922
	// Class variables -----------------------------------------------
	//#CM51923
	/**<en>
	* Path of the text which saves the unavailable locations
	</en>*/
	String wFileName = "";

    //#CM51924
    /**<en> Storage type </en>*/

    protected int wWarehouseNumber = 0;

    //#CM51925
    /**<en> BANK </en>*/

    protected int wBank = 0;

    //#CM51926
    /**<en> BAY </en>*/

    protected int wBay = 0;

    //#CM51927
    /**<en> LEVEL </en>*/

    protected int wLevel = 0;

	//#CM51928
	// Class method --------------------------------------------------
	 //#CM51929
	 /**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:51:59 $") ;
	}

	//#CM51930
	// Constructors --------------------------------------------------
	//#CM51931
	/**<en>
	 * This constructor will be used.
	 * @param conn <CODE>Connection</CODE>
	 </en>*/
	public UnavailableLocationParameter()   
	{
	}

	//#CM51932
	// Public methods ------------------------------------------------
	//#CM51933
	/**<en>
	* Set the path of the text which saves the unavailable locations.
	</en>*/
	public void setFileName(String filename)
	{
		wFileName = filename;
	}
	
	//#CM51934
	/**<en>
	* Return the path of the text which saves the unavailable locations.
	</en>*/
	public String getFileName()
	{
		return wFileName;
	}

	//#CM51935
	/**<en>
	 * Retrieve the storage type.
	 * @return wWarehouseNumber
	 </en>*/
	public int getWarehouseNumber()
	{
		return wWarehouseNumber;
	}
	//#CM51936
	/**<en>
	 * Set the storage type.
	 * @param WarehouseNumber
	 </en>*/
	public void setWarehouseNumber(int arg)
	{
		wWarehouseNumber = arg;
	}

	//#CM51937
	/**<en>
	 * Retrieve the BANK.
	 * @return wBank
	 </en>*/
	public int getBank()
	{
		return wBank;
	}
	//#CM51938
	/**<en>
	 * Set the BANK.
	 * @param Bank
	 </en>*/
	public void setBank(int arg)
	{
		wBank = arg;
	}

	//#CM51939
	/**<en>
	 * Retrieve the BAY.
	 * @return wBay
	 </en>*/
	public int getBay()
	{
		return wBay;
	}
	//#CM51940
	/**<en>
	 * Set the BAY.
	 * @param Bay
	 </en>*/
	public void setBay(int arg)
	{
		wBay = arg;
	}
	//#CM51941
	/**<en>
	 * Retrieve the Level.
	 * @return wLevel
	 </en>*/
	public int getLevel()
	{
		return wLevel;
	}
	//#CM51942
	/**<en>
	 * Set the Level.
	 * @param Level
	 </en>*/
	public void setLevel(int arg)
	{
		wLevel = arg;
	}
	//#CM51943
	// Package methods -----------------------------------------------

	//#CM51944
	// Protected methods ---------------------------------------------

	//#CM51945
	// Private methods -----------------------------------------------

	
}
//#CM51946
//end of class
