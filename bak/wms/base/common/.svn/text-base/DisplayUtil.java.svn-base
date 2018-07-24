// $Id: DisplayUtil.java,v 1.2 2006/11/07 05:59:55 suresh Exp $
package jp.co.daifuku.wms.base.common;

import java.util.Locale;

import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM642609
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM642610
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * The Util class to make the display item. <BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:59:55 $
 * @author  $Author: suresh $a
 */
public class DisplayUtil
{
	//#CM642611
	// Class fields --------------------------------------------------

	//#CM642612
	// Class variables -----------------------------------------------

	//#CM642613
	// Class method --------------------------------------------------
	//#CM642614
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 05:59:55 $");
	}

	//#CM642615
	// Constructors --------------------------------------------------
	//#CM642616
	/**
	 * Construct <CODE>DisplayUtil</CODE>.
	 */
	public DisplayUtil()
	{

	}

	//#CM642617
	// Public methods ------------------------------------------------
	//#CM642618
	/**
	 * Calculate the number of cases from total and the number of case insertions. <BR>
	 * The quotient into which total is divided with the case becomes the case qty. <BR>
	 * Return 0 when the Packed qty per case is 0.<BR>
	 * 
	 * @param  pPlanQty Total
	 * @param  pEnteringQty Packed qty per case
	 * @return Case qty
	 */
	public static int getCaseQty(int pTotalQty, int pEnteringQty)
	{
		if (pEnteringQty == 0)
		{
			return 0;
		}
		return (pTotalQty / pEnteringQty);
	}

	//#CM642619
	/**
	 * Calculate the number of cases from total and the number of case insertions. <BR>
	 * The quotient into which total is divided with the case becomes the case qty. <BR>
	 * Return 0 when Case/Piece flag is Piece. 
	 * Return the numerical value converted by the entering qty.
	 * 
	 * @param  pPlanQty Total
	 * @param  pEnteringQty Packed qty per case
	 * @param  pCasePieceFlag Case/Piece flag
	 * @return Case qty
	 */
	public static int getCaseQty(int pTotalQty, int pEnteringQty, String pCasePieceFlag)
	{
		if (pCasePieceFlag.equals(SystemDefine.CASEPIECE_FLAG_PIECE))
		{
			return 0;
		}
		
		return getCaseQty(pTotalQty, pEnteringQty);

	}

	//#CM642620
	/**
	 * Calculate the number of cases from total and the number of case insertions. <BR>
	 * The quotient into which total is divided with the case becomes the case qty. <BR>
	 * Return 0 when the Packed qty per case is 0.<BR>
	 * 
	 * @param  pPlanQty Total
	 * @param  pEnteringQty Packed qty per case
	 * @return Case qty
	 */
	public static long getCaseQty(long pTotalQty, int pEnteringQty)
	{
		if (pEnteringQty == 0)
		{
			return 0;
		}
		return (pTotalQty / (long)pEnteringQty);
	}

	//#CM642621
	/**
	 * Calculate the number of cases from total and the number of case insertions. <BR>
	 * The quotient into which total is divided with the case becomes the case qty. <BR>
	 * Return 0 when Case/Piece flag is Piece. <BR>
	 * Return the numerical value converted by the entering qty.
	 * 
	 * @param  pPlanQty Total
	 * @param  pEnteringQty Packed qty per case
	 * @param  pCasePieceFlag Case/Piece flag
	 * @return Case qty
	 */
	public static long getCaseQty(long pTotalQty, int pEnteringQty, String pCasePieceFlag)
	{
		if (pCasePieceFlag.equals(SystemDefine.CASEPIECE_FLAG_PIECE))
		{
			return 0;
		}
		
		return getCaseQty(pTotalQty, pEnteringQty);

	}

	//#CM642622
	/**
	 * Calculate the piece qty from Total and Packed qty per case. <BR>
	 * The remainder into which Total is divided with the case becomes the Piece qty. <BR>
	 * Return Total when the entering qty is 0. <BR>
	 * 
	 * @param  pPlanQty Total
	 * @param  pEnteringQty Packed qty per case
	 * @return Piece qty
	 */
	public static int getPieceQty(int pTotalQty, int pEnteringQty)
	{
		if (pEnteringQty == 0)
		{
			return pTotalQty;
		}
		return (pTotalQty % pEnteringQty);
	}

	//#CM642623
	/**
	 * Calculate the piece qty from Total and Packed qty per case. <BR>
	 * The remainder into which Total is divided with the case becomes the Piece qty. <BR>
	 * Return Total when Case/Piece flag is Piece.<BR>
	 * Return the numerical value converted by the entering qty.
	 * 
	 * @param  pPlanQty Total
	 * @param  pEnteringQty Packed qty per case
	 * @param  pCasePieceFlag Case/Piece flag
	 * @return Piece qty
	 */
	public static long getPieceQty(long pTotalQty, int pEnteringQty, String pCasePieceFlag)
	{
		if (pCasePieceFlag.equals(SystemDefine.CASEPIECE_FLAG_PIECE))
		{
			return pTotalQty;
		}
		
		return getPieceQty(pTotalQty, pEnteringQty);

	}

	//#CM642624
	/**
	 * Calculate the piece qty from Total and Packed qty per case. <BR>
	 * The remainder into which Total is divided with the case becomes the Piece qty. <BR>
	 * Return Total when the entering qty is 0. <BR>
	 * 
	 * @param  pPlanQty Total
	 * @param  pEnteringQty Packed qty per case
	 * @return Piece qty
	 */
	public static long getPieceQty(long pTotalQty, int pEnteringQty)
	{
		if (pEnteringQty == 0)
		{
			return pTotalQty;
		}
		return (pTotalQty % (long)pEnteringQty);
	}

	//#CM642625
	/**
	 * Calculate the piece qty from Total and Packed qty per case. <BR>
	 * The remainder into which Total is divided with the case becomes the Piece qty. <BR>
	 * Return Total when Case/Piece flag is Piece.
	 * Return the numerical value converted by the entering qty.
	 * 
	 * @param  pPlanQty Total
	 * @param  pEnteringQty Packed qty per case
	 * @param  pCasePieceFlag Case/Piece flag
	 * @return Piece qty
	 */
	public static int getPieceQty(int pTotalQty, int pEnteringQty, String pCasePieceFlag)
	{
		if (pCasePieceFlag.equals(SystemDefine.CASEPIECE_FLAG_PIECE))
		{
			return pTotalQty;
		}
		
		return getPieceQty(pTotalQty, pEnteringQty);

	}

	//#CM642626
	/**
	 * Return Case/Piece flag in any of the following. "Peace", "Case", "There is no specification", "Case/piece" and "All". <BR>
	 * <code>SystemDefine.CASEPIECE_FLAG_NOTHING</code>:Unspecified <BR>
	 * <code>SystemDefine.CASEPIECE_FLAG_CASE</code>:Case <BR>
	 * <code>SystemDefine.CASEPIECE_FLAG_PIECE</code>:Piece <BR>
	 * <code>SystemDefine.CASEPIECE_FLAG_MIX</code>:Case/Piece <BR>
	 * <code>SystemDefine.CASEPIECE_FLAG_ALL</code>:All
	 * @param  arg Case/Piece flag
	 * @return Value of Case/Piece flag(Character string)
	 */
	public static String getPieceCaseValue(String arg)
	{
		if (arg.equals(SystemDefine.CASEPIECE_FLAG_NOTHING))
		{
			return DisplayText.getText("RDB-W0010");
		}
		else if (arg.equals(SystemDefine.CASEPIECE_FLAG_CASE))
		{
			return DisplayText.getText("RDB-W0008");
		}
		else if (arg.equals(SystemDefine.CASEPIECE_FLAG_PIECE))
		{
			return DisplayText.getText("RDB-W0009");
		}
		else if (arg.equals(SystemDefine.CASEPIECE_FLAG_MIX))
		{
			return DisplayText.getText("RDB-W0059");
		}
		else if (arg.equals(SystemDefine.CASEPIECE_FLAG_ALL))
		{
			return DisplayText.getText("RDB-W0013");
		}
		else
		{
			return "-";
		}

	}

	//#CM642627
	/**
	 * Use this method when processing it by no use of the framework. <BR>
	 * Return Case/Piece flag in any of the following. "Piece", "Case", and "Unspecified" for the locale specification. <BR>
	 * <code>SystemDefine.CASEPIECE_FLAG_NOTHING</code>:Unspecified <BR>
	 * <code>SystemDefine.CASEPIECE_FLAG_CASE</code>:Case <BR>
	 * <code>SystemDefine.CASEPIECE_FLAG_PIECE</code>:Piece <BR>
	 * <code>SystemDefine.CASEPIECE_FLAG_MIX</code>:Case/Piece
	 * @param  arg Case/Piece flag
	 * @return Value of Case/Piece flag(Character string)
	 */
	public static String getPieceCaseValue(Locale locale, String arg)
	{
		if (arg.equals(SystemDefine.CASEPIECE_FLAG_NOTHING))
		{
			return DisplayText.getText(locale, "RDB-W0010");
		}
		else if (arg.equals(SystemDefine.CASEPIECE_FLAG_CASE))
		{
			return DisplayText.getText(locale, "RDB-W0008");
		}
		else if (arg.equals(SystemDefine.CASEPIECE_FLAG_PIECE))
		{
			return DisplayText.getText(locale, "RDB-W0009");
		}
		else if (arg.equals(SystemDefine.CASEPIECE_FLAG_MIX))
		{
			return DisplayText.getText(locale, "RDB-W0059");
		}
		else
		{
			return "";
		}

	}

	//#CM642628
	/**
	 * Return the name of "DC", "TC", and "Crossing dock" to the TC/DC division. <BR>
	 * <code>SystemDefine.TCDC_FLAG_DC</code>:DC <BR>
	 * <code>SystemDefine.TCDC_FLAG_TC</code>:TC <BR>
	 * <code>SystemDefine.TCDC_FLAG_CROSSTC</code>:Cross Dock
	 * @param  arg TC/DC Flag
	 * @return Value of TC/DC flag(Character string)
	 */
	public static String getTcDcValue(String arg)
	{
		//#CM642629
		// When the flag is DC item
		if (arg.equals(SystemDefine.TCDC_FLAG_DC))
		{
			return DisplayText.getText("LBL-W0358") ;
		}
		//#CM642630
		// When the flag is TC item
		else if (arg.equals(SystemDefine.TCDC_FLAG_TC))
		{
			return DisplayText.getText("LBL-W0360") ;
		}
		//#CM642631
		// When the flag is Cross TC item
		else if (arg.equals(SystemDefine.TCDC_FLAG_CROSSTC))
		{
			return DisplayText.getText("LBL-W0359") ;
		}
		else
		{
			return "";
		}
	}


	//#CM642632
	/**
	 * Return the name of "Standby", "Started", "Working", "Finished", and "Deletion" to the state flag of work information. <BR>
	 * <code>WorkingInformation.STATUSFLAG_UNSTART</code>:Stand by <BR>
	 * <code>WorkingInformation.STATUSFLAG_START</code>:Started <BR>
	 * <code>WorkingInformation.STATUSFLAG_NOWWORKING</code>:Working <BR>
	 * <code>WorkingInformation.STATUSFLAG_COMPLETION</code>:Finished <BR>
	 * <code>WorkingInformation.STATUSFLAG_DELETE</code>:Deletion
	 * @param  arg Status flag of work information
	 * @return Value of Status flag of work information(Character string)
	 */
	public static String getWorkingStatusValue(String arg)
	{
		if (arg.equals(SystemDefine.STATUS_FLAG_UNSTART))
		{
			return DisplayText.getText("CMB-W0002");
		}
		else if (arg.equals(SystemDefine.STATUS_FLAG_START))
		{
			return DisplayText.getText("CMB-W0003");
		}
		else if (arg.equals(SystemDefine.STATUS_FLAG_NOWWORKING))
		{
			return DisplayText.getText("CMB-W0004");
		}
		else if (arg.equals(SystemDefine.STATUS_FLAG_COMPLETION))
		{
			return DisplayText.getText("CMB-W0005");
		}
		else if (arg.equals(SystemDefine.STATUS_FLAG_COMPLETE_IN_PART))
		{
			//#CM642633
			// CMB-W0024=Partially finished
			return DisplayText.getText("CMB-W0024");
		}
		else if (arg.equals(SystemDefine.STATUS_FLAG_DELETE))
		{
			return DisplayText.getText("LBL-W0042");
		}
		else
		{
			return "";
		}
	}

	//#CM642634
	/**
	 * Return the name of "Stand by", "Working", "Reserved", "Finished", and "Deletion" to the status flag of the shipping plan. <BR>
	 * <code>ShippingPlan.STATUS_FLAG_UNSTARTING</code>:Stand by <BR>
	 * <code>ShippingPlan.STATUS_FLAG_NOWWORKING</code>:Working <BR>
	 * <code>ShippingPlan.STATUS_FLAG_RECEPTION_IN_PART</code>:Reserved <BR>
	 * <code>ShippingPlan.STATUS_FLAG_RECEPTION_FINISH</code>:Finished <BR>
	 * <code>ShippingPlan.STATUS_FLAG_DELETE</code>:Deletion
	 * @param  arg Status flag of shiping plan
	 * @return Value of Status flag of shiping plan(Character string)
	 */
	public static String getShippingPlanStatusValue(String arg)
	{
		if (arg.equals(SystemDefine.STATUS_FLAG_UNSTART))
		{
			return DisplayText.getText("CMB-W0002");
		}
		else if (arg.equals(SystemDefine.STATUS_FLAG_NOWWORKING))
		{
			return DisplayText.getText("CMB-W0004");
		}
		else if (arg.equals(SystemDefine.STATUS_FLAG_COMPLETE_IN_PART))
		{
			return DisplayText.getText("CMB-W0022");
		}
		else if (arg.equals(SystemDefine.STATUS_FLAG_COMPLETION))
		{
			return DisplayText.getText("CMB-W0005");
		}
		else if (arg.equals(SystemDefine.STATUS_FLAG_DELETE))
		{
			return DisplayText.getText("LBL-W0042");
		}
		else
		{
			return "";
		}
	}

	//#CM642635
	/**
	 * Return the name of "Male" and "Female" to the sex of worker information. <BR>
	 * <CODE>Worker.MALE </CODE>:Male <BR>
	 * <CODE>Worker.FEMALE </CODE>: Female
	 * @param arg Sex
	 * @return Value of Sex(Character string)
	 */
	public static String getWorkerSexValue(String arg)
	{
		if (arg.equals(Worker.MALE))
		{
			return DisplayText.getText("CMB-W0006");
		}
		else if (arg.equals(Worker.FEMALE))
		{
			return DisplayText.getText("CMB-W0007");
		}
		else
		{
			return "";
		}
	}

	//#CM642636
	/**
	 * Return the name of "Manager", "System Admin", and "Worker" to the access authority of Worker information.  <BR>
	 * <CODE>Worker.ACCESS_AUTHORITY_ADMINISTRATOR</CODE> : Manager <BR>
	 * <CODE>Worker.ACCESS_AUTHORITY_SYSTEMADMINISTRATOR</CODE> : System Admin <BR>
	 * <CODE>Worker.ACCESS_AUTHORITY_WORKER</CODE> : Worker
	 * @param arg Access authority
	 * @return Value of Access authority(Character string)
	 */
	public static String getWorkerAccessAuthority(String arg)
	{
		if (arg.equals(Worker.ACCESS_AUTHORITY_ADMINISTRATOR))
		{
			return DisplayText.getText("CMB-W0008");
		}
		else if (arg.equals(Worker.ACCESS_AUTHORITY_SYSTEMADMINISTRATOR))
		{
			return DisplayText.getText("CMB-W0010");
		}
		else if (arg.equals(Worker.ACCESS_AUTHORITY_WORKER))
		{
			return DisplayText.getText("CMB-W0011");
		}
		else
		{
			return "";
		}

	}

	//#CM642637
	/**
	 * 
	 * Return the name of "Manager" and "General Worker" to Occupational category of Worker information.  <BR>
	 * <CODE>Worker.JOB_TYPE_ADMINISTRATOR </CODE> : Manager <BR>
	 * <CODE>Worker.JOB_TYPE_WORKER </CODE> : General Worker
	 * 
	 * @param arg Occupational category
	 * @return Value of Occupational category(Character string)
	 */
	public static String getWorkerJobType(String arg)
	{
		if (arg.equals(Worker.JOB_TYPE_ADMINISTRATOR))
		{
			return DisplayText.getText("CMB-W0008");
		}
		if (arg.equals(Worker.JOB_TYPE_WORKER))
		{
			return DisplayText.getText("CMB-W0009");
		}
		return "";
	}

	//#CM642638
	/**
	 * 
	 * Acquire the name of Work flag from the system definition. <BR>
	 * <code>SystemDefine.JOB_TYPE_UNSTART</code>:Stand by <BR>
	 * <code>SystemDefine.JOB_TYPE_INSTOCK</code>:Receiving <BR>
	 * <code>SystemDefine.JOB_TYPE_RETRIEVAL</code>:Picking <BR>
	 * <code>SystemDefine.JOB_TYPE_STORAGE</code>:Storage <BR>
	 * <code>SystemDefine.JOB_TYPE_SORTING</code>:Sorting <BR>
	 * <code>SystemDefine.JOB_TYPE_SHIPINSPECTION</code>:Shipping inspection <BR>
	 * <code>SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL</code>:Movement Picking <BR>
	 * <code>SystemDefine.JOB_TYPE_MOVEMENT_STORAGE</code>:Movement Storage <BR>
	 * <code>SystemDefine.JOB_TYPE_EX_STORAGE</code>:Exception Storage <BR>
	 * <code>SystemDefine.JOB_TYPE_EX_RETRIEVAL</code>:Exception Picking <BR>
	 * <code>SystemDefine.JOB_TYPE_MAINTENANCE_PLUS</code>:Maintenance increase <BR>
	 * <code>SystemDefine.JOB_TYPE_MAINTENANCE_MINUS</code>:Maintenance decrease <BR>
	 * <code>SystemDefine.JOB_TYPE_INVENTORY</code>:Inventory <BR>
	 * <code>SystemDefine.JOB_TYPE_INVENTORY_PLUS</code>:Inventory Increase <BR>
	 * <code>SystemDefine.JOB_TYPE_INVENTORY_MINUS</code>:Inventory Decrease <BR>
	 * 
	 * @param arg Work flag
	 * @return Work flag(Character string)
	 */
	public static String getJobType(String arg)
	{
		if (arg.equals(SystemDefine.JOB_TYPE_UNSTART))
		{
			return DisplayText.getText("LBL-W0367");

		}
		else if (arg.equals(SystemDefine.JOB_TYPE_INSTOCK))
		{
			return DisplayText.getText("LBL-W0347");

		}
		else if (arg.equals(SystemDefine.JOB_TYPE_RETRIEVAL))
		{
			return DisplayText.getText("LBL-W0348");

		}
		else if (arg.equals(SystemDefine.JOB_TYPE_STORAGE))
		{
			return DisplayText.getText("LBL-W0234");

		}
		else if (arg.equals(SystemDefine.JOB_TYPE_SORTING))
		{
			return DisplayText.getText("LBL-W0349");

		}
		else if (arg.equals(SystemDefine.JOB_TYPE_SHIPINSPECTION))
		{
			return DisplayText.getText("LBL-W0189");
			
		}
		else if (arg.equals(SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL))
		{
			return DisplayText.getText("LBL-W0352");

		}
		else if (arg.equals(SystemDefine.JOB_TYPE_MOVEMENT_STORAGE))
		{
			return DisplayText.getText("LBL-W0351");

		}
		else if (arg.equals(SystemDefine.JOB_TYPE_EX_STORAGE))
		{
			return DisplayText.getText("LBL-W0353");
		}
		else if (arg.equals(SystemDefine.JOB_TYPE_EX_RETRIEVAL))
		{
			return DisplayText.getText("LBL-W0354");
		}
		else if (arg.equals(SystemDefine.JOB_TYPE_MAINTENANCE_PLUS))
		{
			return "MAINTENANCE_PLUS";

		}
		else if (arg.equals(SystemDefine.JOB_TYPE_MAINTENANCE_MINUS))
		{
			return "MAINTENANCE_MINUS";

		}
		else if (arg.equals(SystemDefine.JOB_TYPE_INVENTORY))
		{
			return DisplayText.getText("LBL-W0350");

		}
		else if (arg.equals(SystemDefine.JOB_TYPE_INVENTORY_PLUS))
		{
			return "INVENTORY_PLUS";

		}
		else if (arg.equals(SystemDefine.JOB_TYPE_INVENTORY_MINUS))
		{
			return "INVENTORY_MINUS";

		}
		else
		{
			return "";
		}

	}

	//#CM642639
	/**
	 * 
	 * Acquire the label for the work status display of the status of the RFT work and the RFT work status maintenance. 
	 * 
	 * @param	rft		RFT information
	 * @return			Status string to be displayed
	 */
	public static String getJobType(Rft rft)
	{
		
		if (rft.getRestFlag().equals(SystemDefine.REST_FLAG_REST))
		{
			//#CM642640
			// Return Character string of Resting when the rest flag is [Resting].
			return DisplayText.getText("LBL-W0464");
				
		}
		
		if(rft.getRadioFlag().equals(SystemDefine.RADIO_FLAG_OUT))
		{
			//#CM642641
			// When Rest flag is other than [Resting] and Wireless flag is [Outside wireless area],
			//#CM642642
			// Return character string of [Outside wireless area].
			return DisplayText.getText("LBL-W0465");
			
		}
		
		if(rft.getJobType().equals(SystemDefine.JOB_TYPE_UNSTART))
		{
			if(!rft.getTerminalType().equals(SystemDefine.TERMINAL_TYPE_PCART))
			{
				//#CM642643
				// When Rest flag is other than [Resting] and Wireless flag is [Outside wireless area],
				//#CM642644
				// When Work flag is [Stand by] and Terminal flag is [PCart], return [Stand by] character string.
				return DisplayText.getText("LBL-W0367");
				
			}
			//#CM642645
			// When Rest flag is other than [Resting] and Wireless flag is [Outside wireless area],
			//#CM642646
			// When Work flag is [Stand by] and Terminal flag is [PCart],
			//#CM642647
			// Return [Starting] or [Stopping] as character string according to the Status flag.
			if(rft.getStatusFlag().equals(SystemDefine.RFT_STATUS_FLAG_START)){
				return DisplayText.getText("LBL-W0466");
				
			}
			else if(rft.getStatusFlag().equals(SystemDefine.RFT_STATUS_FLAG_STOP))
			{
				return DisplayText.getText("LBL-W0472");
				
			}
		}
		//#CM642648
		// When Rest flag is other than [Resting] and Wireless flag is [Outside wireless area],
		//#CM642649
		// When work flag is Stand by, return the character string of work flag.
		return getJobType(rft.getJobType());
		
	}

	//#CM642650
	/**
	 * Acquire the name of the condition for consolidating.<BR>
	 * <CODE>SystemParameter.SELECTAGGREGATECONDITION_TERM</CODE> : Display total time <BR>
	 * <CODE>SystemParameter.SELECTAGGREGATECONDITION_DAILY</CODE> : Display total days <BR>
	 * <CODE>SystemParameter.SELECTAGGREGATECONDITION_DETAIL</CODE> : Detailed display <BR>
	 * @param arg Consolidating condition
	 * @return Consolidating condition as String
	 */
	public static String getSelectAggregateCondition(String arg)
	{
		if (arg.equals(SystemParameter.SELECTAGGREGATECONDITION_TERM))
		{
			return DisplayText.getText("TLE-W0057");
		}
		else if (arg.equals(SystemParameter.SELECTAGGREGATECONDITION_DAILY))
		{
			return DisplayText.getText("TLE-W0049");
		}
		else if (arg.equals(SystemParameter.SELECTAGGREGATECONDITION_DETAIL))
		{
			return DisplayText.getText("TLE-W0044");
		}
		else
		{
			return "";
		}
	}

	//#CM642651
	/**
	 * Acquire the name of the content of work.  <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_ALL</CODE> : All <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_INSTOCK</CODE> : Receiving <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_STORAGE</CODE> : Storage  <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_RETRIEVAL</CODE> : Picking <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_SORTING</CODE> : Sorting <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_SHIPPING</CODE> : Shipping <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_INVENTORY_INCREASE</CODE> : Inventory Increase <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_INVENTORY_DECREASE</CODE> : Inventory Decrease <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_MOVEMENT_STORAGE</CODE> : Movement Storage <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_MOVEMENT_RETRIEVAL</CODE> : Movement Picking <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDSTORAGE</CODE> : No Plan Storage <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDRETRIEVAL</CODE> : No Plan Picking <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_MAINTENANCE_INCREASE</CODE> : Stock Maintenance increase <BR>
	 * <CODE>SystemParameter.SELECTWORKDETAIL_MAINTENANCE_DECREASE</CODE> : Stock Maintenance decrease <BR>
	 * @param arg Content of work
	 * @return Content of work as String
	 */
	public static String getSelectWorkDetail(String arg)
	{

		if (arg.equals(SystemParameter.SELECTWORKDETAIL_ALL))
		{
			//#CM642652
			// All
			return DisplayText.getText("LBL-W0346");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_INSTOCK))
		{
			//#CM642653
			// Receiving
			return DisplayText.getText("LBL-W0347");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_STORAGE))
		{
			//#CM642654
			// Storage
			return DisplayText.getText("LBL-W0234");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_RETRIEVAL))
		{
			//#CM642655
			// Picking
			return DisplayText.getText("LBL-W0348");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_SORTING))
		{
			//#CM642656
			// Sorting
			return DisplayText.getText("LBL-W0349");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_SHIPPING))
		{
			//#CM642657
			// Shipping
			return DisplayText.getText("LBL-W0189");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_INVENTORY))
		{
			//#CM642658
			// LBL-W0350=Inventory
			return DisplayText.getText("LBL-W0350");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_INCREASE))
		{
			//#CM642659
			// Inventory Increase
			return DisplayText.getText("LBL-W0365");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_DECREASE))
		{
			//#CM642660
			// Inventory Decrease
			return DisplayText.getText("LBL-W0366");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_STORAGE))
		{
			//#CM642661
			// Movement Storage
			return DisplayText.getText("LBL-W0351");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_RETRIEVAL))
		{
			//#CM642662
			// Movement Picking
			return DisplayText.getText("LBL-W0352");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDSTORAGE))
		{
			//#CM642663
			// No Plan Storage
			return DisplayText.getText("LBL-W0353");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDRETRIEVAL))
		{
			//#CM642664
			// No Plan Picking
			return DisplayText.getText("LBL-W0354");
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_INCREASE))
		{
			//#CM642665
			// Stock Maintenance increase
			return DisplayText.getText("LBL-W0363");
		}
		else if(arg.equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_DECREASE))
		{
			//#CM642666
			// Stock Maintenance decrease
			return DisplayText.getText("LBL-W0364") ;
		}
		else
		{
			return "";
		}
	}
	
	//#CM642667
	/**
	 * Acquire the name in the status of results.  <BR>
	 * [All Qty] when Shortage and Reserved qty are 0<BR>
	 * [Shortage] when Shortage qty is more than 0<BR>
	 * [Reserved] when Reserved qty is more than 0<BR>
	 * @param shortageCnt	Shortage qty
	 * @param pendingQty	Reserved qty
	 * @return	All qty or Shortage or Reserved
	 */
	public static String getResultStatusName(int shortageCnt, int pendingQty)
	{
		String resultStatusName = "" ;
		
		if(shortageCnt > 0)
		{
			resultStatusName = DisplayText.getText("LBL-W0207");
		}
		if(pendingQty > 0)
		{
			resultStatusName = DisplayText.getText("LBL-W0373");
		}
		else
		{
			resultStatusName = DisplayText.getText("LBL-W0004");
		}
				
		return resultStatusName;
	}


	//#CM642668
	/**
	 * Acquire the name of the results report. <BR>
	 * <CODE>SystemParameter.REPORT_FLAG_NOT_SENT</CODE> : Report not sent<BR>
	 * <CODE>SystemParameter.REPORT_FLAG_SENT</CODE> : Report sent
	 * @param	arg	Report flag
	 * @return	Report not sent or Report sent
	 */
	public static String getReportFlagValue(String arg)
	{
		String resultStatusName = "" ;
		
		if(arg.equals(WorkingInformation.REPORT_FLAG_NOT_SENT))
		{
			//#CM642669
			//Report not sent
			resultStatusName = DisplayText.getText("LBL-W0381");
		}
		else
		{
			//#CM642670
			//Report sent
			resultStatusName = DisplayText.getText("LBL-W0382");
		}		
		return resultStatusName;
	}
	
	//#CM642671
	/**
	 * Acquire the name of the workshop. <BR>
	 * <CODE>StorageSupportParameter.SYSTEM_DISC_KEY_FLOOR</CODE> : FLOOR<BR>
	 * <CODE>StorageSupportParameter.SYSTEM_DISC_KEY_ASRS</CODE> : ASRS<BR>
	 * <CODE>StorageSupportParameter.SYSTEM_DISC_KEY_MOVEMENT_RACK</CODE> : Movement Rack
	 * @param	arg	System identification key
	 * @return	FLOOR or ASRS or Movement Rack
	 */
	public static String getSystemDiscKeyValue(int arg)
	{
		String resultSystemDiscKey = "" ;
		
		if(arg == StorageSupportParameter.SYSTEM_DISC_KEY_FLOOR)
		{
			//#CM642672
			// FLOOR
			resultSystemDiscKey = DisplayText.getText("MBTN-W0029");
		}
		else if(arg == StorageSupportParameter.SYSTEM_DISC_KEY_ASRS)
		{
			//#CM642673
			// ASRS
			resultSystemDiscKey = DisplayText.getText("MBTN-W0031");
		}
		else if(arg == StorageSupportParameter.SYSTEM_DISC_KEY_MOVEMENT_RACK)
		{
		    //#CM642674
		    // Movement Rack
			resultSystemDiscKey = DisplayText.getText("MBTN-W0030");
		}
		return resultSystemDiscKey;
	}
	
	//#CM642675
	/**
	 * Return the name of "Stand by", "Started", "Working", "Partially finished", "Finished", and "Deletion" to the status flag of Picking plan information. <BR>
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_UNSTARTING</code>:Stand by
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_NOWWORKING</code>:Started
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_NOWWORKING</code>:Working
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_RECEPTION_IN_PART</code>:Partially finished
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_RECEPTION_FINISH</code>:Finished
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_DELETE</code>:Deletion
	 * @param  arg Status flag of Picking plan information
	 * @return Value of Status flag of shipping plan(Character string)
	 */
	public static String getRetrievalPlanStatusValue(String arg)
	{
		if (arg.equals(RetrievalPlan.STATUS_FLAG_UNSTART))
		{
			//#CM642676
			// LBL-W0367=Stand by
			return DisplayText.getText("LBL-W0367");
		}
		else if (arg.equals(RetrievalPlan.STATUS_FLAG_START))
		{
			//#CM642677
			// CMB-W0003=Started
			return DisplayText.getText("CMB-W0003");
		}
		else if (arg.equals(RetrievalPlan.STATUS_FLAG_NOWWORKING))
		{
			//#CM642678
			// LBL-W0275=Working
			return DisplayText.getText("LBL-W0275");
		}
		else if (arg.equals(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART))
		{
			//#CM642679
			// CMB-W0024=Partially finished
			return DisplayText.getText("CMB-W0024");
		}
		else if (arg.equals(RetrievalPlan.STATUS_FLAG_COMPLETION))
		{
			//#CM642680
			// LBL-W0022=Finished
			return DisplayText.getText("LBL-W0022");
		}
		else if (arg.equals(RetrievalPlan.STATUS_FLAG_DELETE))
		{
			//#CM642681
			// LBL-W0042=Deletion
			return DisplayText.getText("LBL-W0042");
		}
		else
		{
			return "";
		}
	}
	
	//#CM642682
	/**
	 * Return the name of "Stand by", "Started", "Working", "Partially finished", "Finished", and "Deletion" to the status flag of Storage plan information. <BR>
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_UNSTARTING</code>:Stand by
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_NOWWORKING</code>:Started
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_NOWWORKING</code>:Working
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_RECEPTION_IN_PART</code>:Partially finished
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_RECEPTION_FINISH</code>:Finished
	 * <code>RetrievalPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_DELETE</code>:Deletion
	 * @param  arg Status flag of Storage plan
	 * @return Value of Status flag of Storage plan(Character string)
	 */
	public static String getStoragePlanStatusValue(String arg)
	{
		if (arg.equals(StoragePlan.STATUS_FLAG_UNSTART))
		{
			//#CM642683
			// LBL-W0367=Stand by
			return DisplayText.getText("LBL-W0367");
		}
		else if (arg.equals(StoragePlan.STATUS_FLAG_START))
		{
			//#CM642684
			// CMB-W0003=Started
			return DisplayText.getText("CMB-W0003");
		}
		else if (arg.equals(StoragePlan.STATUS_FLAG_NOWWORKING))
		{
			//#CM642685
			// LBL-W0275=Working
			return DisplayText.getText("LBL-W0275");
		}
		else if (arg.equals(StoragePlan.STATUS_FLAG_COMPLETE_IN_PART))
		{
			//#CM642686
			// CMB-W0024=Partially finished
			return DisplayText.getText("CMB-W0024");
		}
		else if (arg.equals(StoragePlan.STATUS_FLAG_COMPLETION))
		{
			//#CM642687
			// LBL-W0022=Finished
			return DisplayText.getText("LBL-W0022");
		}
		else if (arg.equals(StoragePlan.STATUS_FLAG_DELETE))
		{
			//#CM642688
			// LBL-W0042=Deletion
			return DisplayText.getText("LBL-W0042");
		}
		else
		{
			return "";
		}
	}

	//#CM642689
	/**
	 * Return the name of "Stand by", "Started", "Working", "Partially finished", "Finished", and "Deletion" to the status flag of Sorting plan information. <BR>
	 * <code>SortingPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_UNSTARTING</code>:Stand by
	 * <code>SortingPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_NOWWORKING</code>:Started
	 * <code>SortingPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_NOWWORKING</code>:Working
	 * <code>SortingPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_RECEPTION_IN_PART</code>:Partially finished
	 * <code>SortingPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_RECEPTION_FINISH</code>:Finished
	 * <code>SortingPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_DELETE</code>:Deletion
	 * @param  arg Status flag of Sorting plan
	 * @return Value of Status flag of Sorting plan(Character string)
	 */
	public static String getSortingPlanStatusValue(String arg)
	{
		if (arg.equals(SortingPlan.STATUS_FLAG_UNSTART))
		{
			//#CM642690
			// LBL-W0367=Stand by
			return DisplayText.getText("LBL-W0367");
		}
		else if (arg.equals(SortingPlan.STATUS_FLAG_START))
		{
			//#CM642691
			// CMB-W0003=Started
			return DisplayText.getText("CMB-W0003");
		}
		else if (arg.equals(SortingPlan.STATUS_FLAG_NOWWORKING))
		{
			//#CM642692
			// LBL-W0275=Working
			return DisplayText.getText("LBL-W0275");
		}
		else if (arg.equals(SortingPlan.STATUS_FLAG_COMPLETE_IN_PART))
		{
			//#CM642693
			// CMB-W0024=Partially finished
			return DisplayText.getText("CMB-W0024");
		}
		else if (arg.equals(SortingPlan.STATUS_FLAG_COMPLETION))
		{
			//#CM642694
			// LBL-W0022=Finished
			return DisplayText.getText("LBL-W0022");
		}
		else if (arg.equals(SortingPlan.STATUS_FLAG_DELETE))
		{
			//#CM642695
			// LBL-W0042=Deletion
			return DisplayText.getText("LBL-W0042");
		}
		else
		{
			return "";
		}
	}

	//#CM642696
	/**
	 * Return the name of "Stand by", "Started", "Working", "Partially finished", "Finished", and "Deletion" to the status flag of Receiving plan information. <BR>
	 * <code>InStockPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_UNSTARTING</code>:Stand by
	 * <code>InStockPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_NOWWORKING</code>:Started
	 * <code>InStockPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_NOWWORKING</code>:Working
	 * <code>InStockPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_RECEPTION_IN_PART</code>:Partially finished
	 * <code>InStockPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_RECEPTION_FINISH</code>:Finished
	 * <code>InStockPlan.STATUS_FLAG_UNSTARTING.STATUS_FLAG_DELETE</code>:Deletion
	 * @param  arg Status flag of Receiving plan
	 * @return Value of Status flag of Receiving plan(Character string)
	 */
	public static String getInstockPlanStatusValue(String arg)
	{
		if (arg.equals(InstockPlan.STATUS_FLAG_UNSTART))
		{
			//#CM642697
			// LBL-W0367=Stand by
			return DisplayText.getText("LBL-W0367");
		}
		else if (arg.equals(InstockPlan.STATUS_FLAG_START))
		{
			//#CM642698
			// CMB-W0003=Started
			return DisplayText.getText("CMB-W0003");
		}
		else if (arg.equals(InstockPlan.STATUS_FLAG_NOWWORKING))
		{
			//#CM642699
			// LBL-W0275=Working
			return DisplayText.getText("LBL-W0275");
		}
		else if (arg.equals(InstockPlan.STATUS_FLAG_COMPLETE_IN_PART))
		{
			//#CM642700
			// CMB-W0024=Partially finished
			return DisplayText.getText("CMB-W0024");
		}
		else if (arg.equals(InstockPlan.STATUS_FLAG_COMPLETION))
		{
			//#CM642701
			// LBL-W0022=Finished
			return DisplayText.getText("LBL-W0022");
		}
		else if (arg.equals(InstockPlan.STATUS_FLAG_DELETE))
		{
			//#CM642702
			// LBL-W0042=Deletion
			return DisplayText.getText("LBL-W0042");
		}
		else
		{
			return "";
		}
	}

	//#CM642703
	/**
	 * Return the name of "Waiting for Storage", "Storing", and "StorageFinished" to the state flag of stock movement information. <BR>
	 * <code>Movement.STATUSFLAG_UNSTART</code>:Waiting for Storage <BR>
	 * <code>Movement.STATUSFLAG_NOWWORKING</code>:Storing <BR>
	 * <code>Movement.STATUSFLAG_COMPLETION</code>:Movement finished
	 * @param  arg Status flag of Stock movement information
	 * @return Value of Status flag of Stock movement information(Character string)
	 */
	public static String getMoveStatusValue(String arg)
	{
		if (arg.equals(Movement.STATUSFLAG_UNSTART))
		{
			//#CM642704
			// CMB-W0025=Waiting for Storage
			return DisplayText.getText("CMB-W0025");
		}
		else if (arg.equals(Movement.STATUSFLAG_NOWWORKING))
		{
			//#CM642705
			// CMB-W0026=Storing
			return DisplayText.getText("CMB-W0026");
		}
		else if (arg.equals(Movement.STATUSFLAG_COMPLETION))
		{
			//#CM642706
			// CMB-W0027=Movement finished
			return DisplayText.getText("CMB-W0027");
		}
		else
		{
			return "";
		}
	}
	
	//#CM642707
	/**
	 * Return the name to the flag of Work type of transportation information. <BR>
	 * <code>CarryInformation.WORKTYPE_NEWSTORAGE</code>:New Storage <BR>
	 * <code>CarryInformation.WORKTYPE_LOCATIONSTORAGE</code>:Shelf specification Storage <BR>
	 * <code>CarryInformation.WORKTYPE_PLANSTORAGE</code>:Plan Storage <BR>
	 * <code>CarryInformation.WORKTYPE_ITEMRETRIEVAL</code>:Item code and name specification Picking <BR>
	 * <code>CarryInformation.WORKTYPE_LOCATIONRETRIEVAL</code>:Shelf specification Picking <BR>
	 * <code>CarryInformation.WORKTYPE_ADDSTORAGE</code>:Multiple Storage <BR>
	 * <code>CarryInformation.WORKTYPE_PALNRETRIEVAL</code>:Plan Picking <BR>
	 * <code>CarryInformation.WORKTYPE_INVENTORYCHECK</code>:Stock confirmation
	 * @param  arg Work type flag of transportation information
	 * @return Name of Work type flag of transportation information
	 */
	public static String getCarryWorkTypeValue(String arg)
	{
		return DisplayText.getText("CARRYINFO","WORKTYPE", arg);
	}

	//#CM642708
	/**
	 * Return the name to a detailed flag for the Picking instruction of transportation information. <BR>
	 * <code>CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK</code>:Stock confirmation <BR>
	 * <code>CarryInformation.RETRIEVALDETAIL_UNIT</code>:Unit Picking <BR>
	 * <code>CarryInformation.RETRIEVALDETAIL_PICKING</code>:Picking <BR>
	 * <code>CarryInformation.RETRIEVALDETAIL_ADD_STORING</code>:Multiple Storage <BR>
	 * <code>CarryInformation.RETRIEVALDETAIL_UNKNOWN</code>:----
	 * @param arg Detailed flag for Picking instruction
	 * @return Detailed flag for Picking instruction name of transportation information
	 */
	public static String getRetrievalDetailValue(int arg)
	{
		return DisplayText.getText("CARRYINFO","RETRIEVALDETAIL", Integer.toString(arg));
		
	}

	//#CM642709
	/**
	 * Acquire display Character string of the terminal division displayed on the RFT work status screen. 
	 * 
	 * @param arg	Terminal flag
	 * @return		Display Terminal flag character string(HT/Inspection terminal/PCart)
	 */
	public static String getTerminalType(String arg)
	{
		if (arg.equals(SystemDefine.TERMINAL_TYPE_HT))
		{
			//#CM642710
			// LBL-W0467=HT
			return DisplayText.getText("LBL-W0467");
		}
		else if (arg.equals(SystemDefine.TERMINAL_TYPE_IT))
		{
			//#CM642711
			// LBL-W0468=Inspection Terminal
			return DisplayText.getText("LBL-W0468");
		}
		else if (arg.equals(SystemDefine.TERMINAL_TYPE_PCART))
		{
			//#CM642712
			// LBL-W0469=PCart
			return DisplayText.getText("LBL-W0469");
		}

		return "";
	}
	
	//#CM642713
	/**
	 * Return the name to the print condition (Inventory filling in list print). <BR>
	 * <CODE>StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_ON</CODE> : The same shelf commodity consolidating
	 * <CODE>PRINTINGCONDITION_INTENSIVEPRINTING_OFF </CODE> : No consolidating. 
	 * @param arg Printing condition
	 * @return Consolidating condition as String
	 */
	public static String getPrintCondition(String arg)
	{
		if (arg.equals(StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_ON))
		{
			return DisplayText.getText("RDB-W0028");
		}
		else if (arg.equals(StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_OFF))
		{
			return DisplayText.getText("RDB-W0029");
		}
		else
		{
			return "";
		}
	}

	
	//#CM642714
	// Package methods -----------------------------------------------

	//#CM642715
	// Protected methods ---------------------------------------------

	//#CM642716
	// Private methods -----------------------------------------------

}
//#CM642717
//end of class
