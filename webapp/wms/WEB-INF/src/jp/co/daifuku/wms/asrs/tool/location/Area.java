// $Id: Area.java,v 1.2 2006/10/30 02:33:27 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49363
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntityHandler;
import jp.co.daifuku.common.MessageResource;


//#CM49364
/**<en>
 * This class controls the area information which is defined with teh range of warehouse
 * and the allocations rules of retrievals.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/08/06</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:27 $
 * @author  $Author: suresh $
 </en>*/
public class Area extends ToolEntity
{
	//#CM49365
	// Class fields --------------------------------------------------

	//#CM49366
	// Class variables -----------------------------------------------
	//#CM49367
	/**<en>
	 * Area ID
	 </en>*/
	protected int wAreaId ;
	
	//#CM49368
	/**<en>
	 * Name of the area
	 </en>*/
	protected String wAreaName ;

	//#CM49369
	/**<en>
	 * Allocation rules
	 </en>*/
	protected String wAllocationRule;
	
	//#CM49370
	/**<en>
	 * Parameter for the allocation rules
	 </en>*/
	protected int wAllocationRuleParam;

	//#CM49371
	/**<en>
	 * Object of warehouse range
	 </en>*/
	protected AreaRange[] wTargetRangeArray;

	//#CM49372
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public static String wDelim = MessageResource.DELIM ;

	//#CM49373
	// Class method --------------------------------------------------
	//#CM49374
	/**<en>
	 * Returns the version of this method.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:27 $") ;
	}

	//#CM49375
	// Constructors --------------------------------------------------
	//#CM49376
	/**<en>
	 * Construct a new <CODE>Area</CODE>.
	 </en>*/
	public Area()
	{
	}

	//#CM49377
	/**<en>
	 * Construct a new <CODE>Area</CODE>.
	 * @param areaid   :area ID
	 </en>*/
	public Area(int areaid )
	{
		//#CM49378
		//<en> Set as an instance variable.</en>
		setAreaId(areaid);
	}

	//#CM49379
	/**<en>
	 * Construct a new <CODE>Area</CODE>.
	 * @param areaid         :area ID
	 * @param areaname       :name of the area
	 * @param allocationrule :allocation rules
	 * @param arearange      :object of warehouse range
	 * @param ehandl         :instance handler
	 </en>*/
	public Area(int			   areaid,
				 String			   areaname,
				 String			   allocationrule,
				 AreaRange[]	   arearange,
				 ToolEntityHandler ehandl)
	{
		wAreaId			  = areaid;
		wAreaName		  = areaname;
		wAllocationRule   = allocationrule;
		wTargetRangeArray = arearange;
		wHandler		  = ehandl;
	}
	
	//#CM49380
	// Public methods ------------------------------------------------
	//#CM49381
	/**<en>
	 * Set the area ID.
	 * @param id :area ID
	 </en>*/
	public void setAreaId(int id)
	{
		wAreaId = id;
	}

	//#CM49382
	/**<en>
	 * Retrieve the area ID.
	 * @return :area ID
	 </en>*/
	public int getAreaId()
	{
		return wAreaId;
	}

	//#CM49383
	/**<en>
	 * Set the name of the area.
	 * @param nm :name of the area
	 </en>*/
	public void setAreaName(String nm)
	{
		wAreaName = nm;
	}

	//#CM49384
	/**<en>
	 * Retrieve the name of the area.
	 * @return :name of the area
	 </en>*/
	public String getAreaName()
	{
		return wAreaName;
	}

	//#CM49385
	/**<en>
	 * Set the allocation rules.
	 * @param rule :allocation rules
	 </en>*/
	public void setAllocationRule(String rule)
	{
		wAllocationRule = rule;
	}

	//#CM49386
	/**<en>
	 * Retrieve the allocation rules.
	 * @return :allocation rules
	 </en>*/
	public String getAllocationRule()
	{
		return wAllocationRule;
	}

	//#CM49387
	/**<en>
	 * Set the parameter for allocation rules.
	 * @param rule :parameter for allocation rules
	 </en>*/
	public void setAllocationRuleParam(int rule)
	{
		wAllocationRuleParam = rule;
	}

	//#CM49388
	/**<en>
	 * Retrieve the parameter for allocation rules.
	 * @return :parameter for allocation rules
	 </en>*/
	public int getAllocationRuleParam()
	{
		return wAllocationRuleParam;
	}

	//#CM49389
	/**<en>
	 * Set the range of the warehouse.
	 * @param range :range of the warehouse
	 </en>*/
	public void setAreaRanges(AreaRange[] range)
	{
		wTargetRangeArray = range;
	}

	//#CM49390
	/**<en>
	 * Retrieve the range of the warehouse.
	 * @return Range of warehouses
	 </en>*/
	public AreaRange[] getAreaRanges()
	{
		return wTargetRangeArray;
	}

	//#CM49391
	/**<en>
	 * Return the warhouse range that matches the specified warehouse no.
	 * Retrieve the warehouse range.
	 * @param whstno :warehouse station no.
	 * @return AreaRange
	 </en>*/
	public AreaRange getAreaRange(String whstno)
	{
		for (int i = 0 ; i < wTargetRangeArray.length ; i++)
		{
			if  (whstno.equals(wTargetRangeArray[i].getWHStationNumber()))
			{
				return wTargetRangeArray[i];
			}
		}
		
		return null;
	}

	//#CM49392
	// Package methods -----------------------------------------------

	//#CM49393
	// Protected methods ---------------------------------------------

	//#CM49394
	// Private methods -----------------------------------------------

}
//#CM49395
//end of class

