// $Id: Warehouse.java,v 1.2 2006/10/30 02:33:23 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49929
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;

//#CM49930
/**<en>
 * This class is used to control the warehouse.
 * There are automated warehouses and floor storage warehouses; this class handles 
 * these warehouses basically as groups of locations.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:23 $
 * @author  $Author: suresh $
 </en>*/
public class Warehouse extends Station
{
	//#CM49931
	// Class fields --------------------------------------------------
	
	//#CM49932
	/**<en>
	 * Field of warehouse type (automated warehouse)
	 </en>*/
	public static final int AUTOMATID_WAREHOUSE = 1 ;

	//#CM49933
	/**<en>
	 * Field of warehouse type (floor storage warehouse)
	 </en>*/
	public static final int CONVENTIONAL_WAREHOUSE = 2 ;

	//#CM49934
	/**<en>
	 * Field of zone retrieval (hard zone operation)
	 </en>*/
	public static final int HARD = 1 ;

	//#CM49935
	/**<en>
	 * Field of zone retrieval (soft zone operation)
	 </en>*/
	public static final int SOFT = 2 ;

	//#CM49936
	/**<en>
	 * Field of zone retrieval (hard zone operation, retrieved from ITEM table)
	 </en>*/
	public static final int HARD_ITEM = 3 ;

	//#CM49937
	/**<en>
	 * Field of automated warehouse operation (open)
	 </en>*/
	public static final int OPEN = 1 ;

	//#CM49938
	/**<en>
	 *  Field of automated warehouse operation (close)
	 </en>*/
	public static final int CLOSE = 2 ;

	//#CM49939
	// Class variables -----------------------------------------------
	//#CM49940
	/**<en>
	 * Preserve the warehouse number (storage type).
	 </en>*/
	protected int wWHNumber ;

	//#CM49941
	/**<en>
	 * Preserve the warehouse type.
	 </en>*/
	protected int wWarehouseType ;

	//#CM49942
	/**<en>
	 * Preserve the max. mix-load quantity of the warehouse.
	 </en>*/
	protected int wMaxMixedPalette ;

	//#CM49943
	/**<en>
	 * Preserve the operation type of automated warehouse.
	 </en>*/
	protected int wEmploymentType = 0 ;

	//#CM49944
	// Class method --------------------------------------------------
	//#CM49945
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:23 $") ;
	}

	//#CM49946
	// Constructors --------------------------------------------------
	//#CM49947
	/**<en>
	 * Construct new <CODE>Warehouse</CODE>.
	 </en>*/
	public Warehouse()
	{
	}
	
	//#CM49948
	/**<en>
	 * Construct new isntance of <code>Warehouse</code>. Please use <code>StationFactory</code> 
	 * class if the instance which already has the defined station is required.
	 * @param snum  :station no. preserved
	 * @see StationFactory
	 </en>*/
	public Warehouse(String snum)
	{
		super(snum) ;
	}

	//#CM49949
	// Public methods ------------------------------------------------
	//#CM49950
	/**<en>
	 * Set the warehouse no. (storage type) of the warehouse.
	 * @param whnum :warehouse no.(not the station no.)
	 </en>*/
	public void setWarehouseNumber(int whnum)
	{
		wWHNumber = whnum ;
	}

	//#CM49951
	/**<en>
	 * Retrieve the warehouse number of the warehouse (storage type).
	 * @return :warehouse no.(not the station no.)
	 </en>*/
	public int getWarehouseNumber()
	{
		return wWHNumber ;
	}

	//#CM49952
	/**<en>
	 * Set the warehouse type.
	 * @param type :warehouse type
	 * @throws InvalidStatusException :Notifies if contents of type is invalid.
	 </en>*/
	public void setWarehouseType(int type) throws InvalidStatusException
	{
		//#CM49953
		//<en> Check the type of warehouse.</en>
		switch (type)
		{
			//#CM49954
			//<en> List of correct type of warehouse</en>
			case AUTOMATID_WAREHOUSE:
			case CONVENTIONAL_WAREHOUSE:
				break ;
			//#CM49955
			//<en> If incorrect type of warehouse were to set, it lets the exception occur and will not modify the type of warehouse.</en>
			default:
				//#CM49956
				//<en> 6126009=Undefined {0} is set.</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = "WAREHOUSETYPE";
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "warehouse", tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0])) ;
		}
		
		//#CM49957
		//<en> Set the warehouse type.</en>
		wWarehouseType = type ;
	}

	//#CM49958
	/**<en>
	 * Retrieve the warehouse type.
	 * @return warehouse type
	 </en>*/
	public int getWarehouseType()
	{
		return wWarehouseType ;
	}

	//#CM49959
	/**<en>
	 * Set the max mix-load quantity of the warehouse.
	 * @param maxnum :the max mix-load quantity of the warehouse
	 </en>*/
	public void setMaxMixedPalette(int maxnum)
	{
		wMaxMixedPalette = maxnum ;
	}

	//#CM49960
	/**<en>
	 * Retrieve the max mix-load quantity of the warehouse.
	 * @return :the max mix-load quantity of the warehouse
	 </en>*/
	public int getMaxMixedPalette()
	{
		return wMaxMixedPalette ;
	}

	//#CM49961
	/**<en>
	 * Set the operatin type of automated warehouse.
	 * @param employ:the operatin type of automated warehouse (1: opern, 2: close )
     * @throws InvalidStatusException :Notifies if contents of employ is invalid.
	 </en>*/
	public void setEmploymentType(int employ) throws InvalidStatusException
	{
		switch( employ )
		{
			//#CM49962
			//<en> If the operatin type of automated warehouse is correct,</en>
			case OPEN :
			case CLOSE :
				break ;
				
			//#CM49963
			//<en> If incorrect operatin type of automated warehouse were to set, </en>
			//<en> it lets the exception occur and will not modify the operatin type of automated warehouse.</en>
			default:
				//#CM49964
				//<en> 6126009=Undefined {0} is set.</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = "WAREHOUSE.EMPLOYMENTTYPE";
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, this.getClass().getName(), tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0])) ;
		}
		
		//#CM49965
		//<en> Modify the operatin type of automated warehouse.</en>
		wEmploymentType = employ ;
	}
	
	//#CM49966
	/**<en>
	 * Retrieve the operation type of the automated warehouse.
	 * @return :operation type of the automated warehouse
	 </en>*/
	public int getEmploymentType()
	{
		return wEmploymentType ;
	}

	//#CM49967
	/**<en>
	 * Return the string representation.
	 * @return    string representation
	 </en>*/
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		try
		{
			buf.append("\nStation Number:" + wNumber) ;
			buf.append("\nWarehouse Number:" + wWHNumber) ;
			buf.append("\nWarehouseType:" + wWarehouseType) ;
			buf.append("\nMaxMixedPalette:" + wMaxMixedPalette) ;
			buf.append("\nParent Station:" + wParentStationNumber) ;
			buf.append("\nName:" + wName ) ;
			buf.append("\nLastUsedStation:" + wLastUsedStationNumber);
			buf.append("\nEmploymentType:" + wEmploymentType) ;
		}
		catch (Exception e)
		{
		}
		
		return (buf.toString()) ;
	}

	//#CM49968
	// Package methods -----------------------------------------------

	//#CM49969
	// Protected methods ---------------------------------------------

	//#CM49970
	// Private methods -----------------------------------------------

}
//#CM49971
//end of class

