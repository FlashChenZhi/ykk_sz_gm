//#CM719650
//$Id: RetrievalPlanDelete.java,v 1.3 2007/02/07 04:19:34 suresh Exp $
package jp.co.daifuku.wms.retrieval.entity;

//#CM719651
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;

//#CM719652
/**
 * Allow this class to manage Picking Plan Info in the process for deleting Picking Plan data.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/10</TD><TD>Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:34 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanDelete extends RetrievalPlan
{
	//#CM719653
	// Class feilds ------------------------------------------------

	//#CM719654
	// Class variables -----------------------------------------------
	//#CM719655
	/**
	 * Planned Picking Date
	 */
	protected String wPlanDate = "";

	//#CM719656
	/**
	 * Consignor Code
	 */
	protected String wConsignorCode = "";

	//#CM719657
	/**
	 * Item/Order division
	 */
	protected String wItemOrderFlag = "";

	//#CM719658
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM719659
	// Class method --------------------------------------------------

	//#CM719660
	// Constructors --------------------------------------------------
	//#CM719661
	/**
	 * Set the default values for all instance variables.
	 */
	public RetrievalPlanDelete()
	{
	}

	//#CM719662
	/**
	 * Use it to set the search result in the vector().
	 * @param PlanDate Planned date
	 * @param ConsignorCode Consignor Code
	 * @param ItemOrderFlag Item/Order division
	 */
	public RetrievalPlanDelete(
		String PlanDate,
		String ConsignorCode,
		String ItemOrderFlag)
	{
		wPlanDate = PlanDate;
		wConsignorCode = ConsignorCode;
		wItemOrderFlag = ItemOrderFlag;
	}

	//#CM719663
	// Public methods ------------------------------------------------
	//#CM719664
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:34 $");
	}

	//#CM719665
	/**
	 * Set a value in the Planned date.
	 * @param PlanDate Planned date to be set
	 */
	public void setPlanDate(String PlanDate)
	{
		wPlanDate = PlanDate;
	}

	//#CM719666
	/**
	 * Obtain the value of the Planned date.
	 * @return Planned date
	 */
	public String getPlanDate()
	{
		return wPlanDate;
	}

	//#CM719667
	/**
	 * Set a value in the Consignor Code.
	 * @param ConsignorCode Consignor code to be set
	 */
	public void setConsignorCode(String ConsignorCode)
	{
		wConsignorCode = ConsignorCode;
	}

	//#CM719668
	/**
	 * Obtain the value of Consignor Code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM719669
	/**
	 * Set a value in the Item/Order division
	 * @param ItemOrderFlag Item/Order division to be set
	 */
	public void setItemOrderFlag(String ItemOrderFlag)
	{
		wItemOrderFlag = ItemOrderFlag;
	}

	//#CM719670
	/**
	 * Obtain the value of the Item/Order division.
	 * @return Item/Order division
	 */
	public String getItemOrderFlag()
	{
		return wItemOrderFlag;
	}

	//#CM719671
	// Package methods -----------------------------------------------

	//#CM719672
	// Protected methods ---------------------------------------------

	//#CM719673
	// Private methods -----------------------------------------------
	
}
//#CM719674
//end of class
