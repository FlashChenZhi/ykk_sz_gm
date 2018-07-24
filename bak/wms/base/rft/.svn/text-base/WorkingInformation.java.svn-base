// $Id: WorkingInformation.java,v 1.2 2006/11/14 06:09:23 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.dbhandler.FieldName;

//#CM702661
/*
 * Copyright 2004-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702662
/**
 * Entity of Work information. 
 * Add insufficient Method. 
 */
public class WorkingInformation extends
        jp.co.daifuku.wms.base.entity.WorkingInformation
{
	//#CM702663
	// Class fields----------------------------------------------------
    //#CM702664
    /**
     * Value of slip Row No when consolidated
     */
    public static final int collectedLineNo = 0;

	//#CM702665
	/** Additional item for P-Cart (LOCATIONSIDE) */

	public static final FieldName LOCATIONSIDE = new FieldName("LOCATION_SIDE");

	//#CM702666
	/** Additional item for P-Cart (WORKTIME) */

	public static final FieldName WORKTIME = new FieldName("WORK_TIME");

	//#CM702667
	// Class variables -----------------------------------------------
    //#CM702668
    /**
     * Value of slip No when consolidated
     */
    protected static String collectedTicketNo = "";

	//#CM702669
	// Constructors --------------------------------------------------
    //#CM702670
    /**
	 * Generate the instance. 
     */
    public WorkingInformation()
    {
        super();
    }
    
	//#CM702671
	// Public methods ------------------------------------------------
	//#CM702672
	/**
	 * Set the value in the work form. 
	 * Do not check whether the set value is correct. 
	 * @param arg Case/Piece flag to be set
	 */
	public void setWorkFormFlag(String arg)
	{
		setValue(WORKFORMFLAG, arg);
	}

	//#CM702673
	/**
	 * Set the value in Location Unit. 
	 * @param arg Location Unit to be set
	 */
	public void setLocationSide(String arg)
	{
		setValue(LOCATIONSIDE, arg);
	}

	//#CM702674
	/**
	 * Acquire Location Unit.
	 * @return Location Unit
	 */
	public String getLocationSide()
	{
		return getValue(WorkingInformation.LOCATIONSIDE).toString();
	}

	//#CM702675
	/**
	 * Set the value in Working time. 
	 * @param arg Working time to be set
	 */
	public void setWorkTime(int arg)
	{
		setValue(WORKTIME, new Integer(arg));
	}

	//#CM702676
	/**
	 * Acquire Working time. 
	 * @return Working time
	 */
	public int getWorkTime()
	{
		return getBigDecimal(WorkingInformation.WORKTIME).intValue();
	}

	//#CM702677
	/**
     * Consolidate it with Work information specified by the argument. 
     * 
     * @param obj	Work information
	 * @throws OverflowException It is notified when the overflow is generated. 
     */
    public void collect(WorkingInformation obj) throws OverflowException
    {
		//#CM702678
		// Numerical addition which can work
	    if (getPlanEnableQty() + obj.getPlanEnableQty() <= SystemParameter.MAXSTOCKQTY)
	    {
			setPlanEnableQty(getPlanEnableQty() + obj.getPlanEnableQty());
	    }
	    else
	    {
	        throw new OverflowException();
	    }
		//#CM702679
		// Actual number addition
	    if (getResultQty() + obj.getResultQty() <= SystemParameter.MAXSTOCKQTY)
	    {
			setResultQty(getResultQty() + obj.getResultQty());
	    }
	    else
	    {
	        throw new OverflowException();
	    }
		//#CM702680
		// Reservation number addition
	    if (getPendingQty() + obj.getPendingQty() <= SystemParameter.MAXSTOCKQTY)
	    {
			setPendingQty(getPendingQty() + obj.getPendingQty());
	    }
	    else
	    {
	        throw new OverflowException();
	    }
		//#CM702681
		// Rewrite it when the Expiry date is small. 
		if (getUseByDate().trim().equals(""))
		{
			setUseByDate(obj.getUseByDate());
		}
		else if (! obj.getUseByDate().trim().equals(""))
		{
			if (getUseByDate().compareTo(obj.getUseByDate()) > 0)
			{
				setUseByDate(obj.getUseByDate());
			}
		}
		
		if (getJobType().equals(WorkingInformation.JOB_TYPE_INSTOCK))
		{
			//#CM702682
			// It is assumed "***..." when slip No is different. 
			if (! getInstockTicketNo().equals(obj.getInstockTicketNo()))
			{
				setInstockTicketNo(getCollectedTicketNo());
			}
			//#CM702683
			// It is assumed 0 when slip Row No is different. 
			if (getInstockLineNo() != obj.getInstockLineNo())
			{
				setInstockLineNo(collectedLineNo);
			}
		}
		else if (getJobType().equals(WorkingInformation.JOB_TYPE_SHIPINSPECTION))
		{
			//#CM702684
			// It is assumed "***..." when slip No is different. 
			if (! getShippingTicketNo().equals(obj.getShippingTicketNo()))
			{
				setShippingTicketNo(getCollectedTicketNo());
			}
			//#CM702685
			// It is assumed 0 when slip Row No is different. 
			if (getShippingLineNo() != obj.getShippingLineNo())
			{
				setShippingLineNo(collectedLineNo);
			}
		}
    }
    
    //#CM702686
    /**
     * Check whether to have the key is same as Work information specified by the argument.
     * 
     * @param obj	Work information
     * @return		Return < CODE>true</CODE > when key is same. 
     * 				Return < CODE>false</CODE > when differing. 
     */
    public boolean hasSameKey(WorkingInformation obj)
    {
        int jobType = SystemParameter.getJobType(getJobType());
		if (SystemParameter.isRftWorkCollect(jobType))
		{
		    if (! getItemCode().equals(obj.getItemCode()))
		    {
		        return false;
		    }

		    if (! SystemParameter.isRftWorkCollectItemCodeOnly(jobType))
			{
			    if (! getItf().equals(obj.getItf()))
			    {
			        return false;
			    }

			    if (! getBundleItf().equals(obj.getBundleItf()))
			    {
			        return false;
			    }
			}
		}
		else
		{
		    //#CM702687
		    // Return false always when not consolidating it. 
		    return false;
		}

        return true;
    }
    
	//#CM702688
	/**
	 * Check whether to have the key is same as Work information specified by the argument. (For Work flag of Sorting) <BR>
	 * Make the location and shipment destinations the key to consolidate at the consolidated setting. 
	 * 
	 * @param obj	Work information
	 * @return		Return < CODE>true</CODE > when key is same. 
	 * 				Return < CODE>false</CODE > when differing. 
	 */
	public boolean hasSameKeyForSort(WorkingInformation obj)
	{
		int jobType = SystemParameter.getJobType(getJobType());
		if (SystemParameter.isRftWorkCollect(jobType))
		{
			if (! getLocationNo().equals(obj.getLocationNo()))
			{
				return false;
			}

			if (! getCustomerCode().equals(obj.getCustomerCode()))
			{
				return false;
			}

		}
		else
		{
			//#CM702689
			// Return false always when not consolidating it. 
			return false;
		}

		return true;
	}
    
	//#CM702690
	/**
	 * Check whether to have the key is same as Work information specified by the argument.
	 *  (For Item picking) <BR>
	 * Make the location and shipment destinations the key to consolidate at the consolidated setting. 
	 * 
	 * @param obj	Work information
	 * @return		Return < CODE>true</CODE > when key is same. 
	 * 				Return < CODE>false</CODE > when differing. 
	 */
	public boolean hasSameKeyForRetrievalItem(WorkingInformation obj)
	{
		int jobType = SystemParameter.getJobType(getJobType());
		if (SystemParameter.isRftWorkCollect(jobType))
		{
			if (! getLocationNo().equals(obj.getLocationNo()))
			{
				return false;
			}

			if (! getItemCode().equals(obj.getItemCode()))
			{
				return false;
			}

			if (! getWorkFormFlag().equals(obj.getWorkFormFlag()))
			{
				return false;
			}

			if (! getUseByDate().equals(obj.getUseByDate()))
			{
				return false;
			}

			if (! SystemParameter.isRftWorkCollectItemCodeOnly(jobType))
			{
				if (! getItf().equals(obj.getItf()))
				{
					return false;
				}

				if (! getBundleItf().equals(obj.getBundleItf()))
				{
					return false;
				}
			}
		}
		else
		{
			//#CM702691
			// Return false always when not consolidating it. 
			return false;
		}

		return true;
	}

	//#CM702692
	/**
	 * It is already decided whether it was transmitted data when requested the data of Item picking. 
	 * @param obj	Work information
	 * @return		Return true when having transmitted. 
	 * 				Return false when not having transmitted. 
	 */
	public boolean isOldWorkData(WorkingInformation obj)
	{
		//#CM702693
		// Skip off the subject compared with standard data. 
	    int cmp = obj.getLocationNo().compareTo(getLocationNo());
		if (cmp < 0)
		{
		    return true;
		}
		if (cmp == 0)
		{
		    cmp = obj.getItemCode().compareTo(getItemCode());
			if (cmp < 0)
			{
			    return true;
			}
			if (cmp == 0)
			{
			    cmp = obj.getWorkFormFlag().compareTo(getWorkFormFlag());
				if (cmp < 0)
				{
				    return true;
				}
				if (cmp == 0)
				{
				    cmp = obj.getUseByDate().compareTo(getUseByDate());
					if (cmp < 0)
					{
					    return true;
					}
					if (cmp == 0)
					{
				        if (SystemParameter.isRftWorkCollect(SystemParameter.JOB_TYPE_RETRIEVAL))
						{
				            return true;
						}

				        //#CM702694
				        // Compare work No when not consolidating it. 
			            //#CM702695
			            // Becomes working data in case of same or less. 
					    cmp = obj.getJobNo().compareTo(getJobNo());
					    if (cmp <= 0)
					    {
					        return true;
					    }
					}
				}
			}
		}

		return false;
	}
	
    //#CM702696
    /**
     * Initialize Value of slip No when work is consolidated. 
     *  (All digits '*') 
     */
    public static void initializeCollectedTicketNo()
    {
        for (int i = 0; i < SendIdMessage.LEN_TICKET_NO; i ++)
        {
            collectedTicketNo += "*";
        }
    }
    
    //#CM702697
    /**
     * Return Value of slip No when work is consolidated. 
     * @return		Value of slip No when work is consolidated
     */
    public static String getCollectedTicketNo()
    {
        if (collectedTicketNo == null)
        {
            initializeCollectedTicketNo();
        }
        
        return collectedTicketNo;
    }
    
    //#CM702698
    /**
	 * Set the value in Status flag. 
	 * @param statusflg Set Status flag
	 */
	public void setStatusFlag(String statusflag) throws InvalidStatusException
	{
		setValue(STATUSFLAG, statusflag);
	}

	//#CM702699
	/**
	 * Generate the reproduction of the object. 
	 * @return Copy of present object
	 */
	public Object clone()
	{
		WorkingInformation dest = new WorkingInformation();
		try
		{
			dest.setAreaNo(getAreaNo());
			dest.setBatchNo(getBatchNo());
			dest.setBeginningFlag(getBeginningFlag());
			dest.setBundleEnteringQty(getBundleEnteringQty());
			dest.setBundleItf(getBundleItf());
			dest.setCasePieceFlag(getCasePieceFlag());
			dest.setCollectJobNo(getCollectJobNo());
			dest.setConsignorCode(getConsignorCode());
			dest.setConsignorName(getConsignorName());
			dest.setCustomerCode(getCustomerCode());
			dest.setCustomerName1(getCustomerName1());
			dest.setEnteringQty(getEnteringQty());
			dest.setHostPlanQty(getHostPlanQty());
			dest.setInstockLineNo(getInstockLineNo());
			dest.setInstockTicketNo(getInstockTicketNo());
			dest.setItemCode(getItemCode());
			dest.setItemName1(getItemName1());
			dest.setItf(getItf());
			dest.setJobNo(getJobNo());
			dest.setJobType(getJobType());
			dest.setLastUpdateDate(getLastUpdateDate());
			dest.setLastUpdatePname(getLastUpdatePname());
			dest.setLocationNo(getLocationNo());
			dest.setLocationSide(getLocationSide());
			dest.setLotNo(getLotNo());
			dest.setOrderingDate(getOrderingDate());
			dest.setOrderNo(getOrderNo());
			dest.setOrderSeqNo(getOrderSeqNo());
			dest.setPendingQty(getPendingQty());
			dest.setPlanDate(getPlanDate());
			dest.setPlanEnableQty(getPlanEnableQty());
			dest.setPlanInformation(getPlanInformation());
			dest.setPlanQty(getPlanQty());
			dest.setPlanRegistDate(getPlanRegistDate());
			dest.setPlanUkey(getPlanUkey());
			dest.setRegistDate(getRegistDate());
			dest.setRegistPname(getRegistPname());
			dest.setReportFlag(getReportFlag());
			dest.setResultLocationNo(getResultLocationNo());
			dest.setResultLotNo(getResultLotNo());
			dest.setResultQty(getResultQty());
			dest.setResultUseByDate(getResultUseByDate());
			dest.setShippingLineNo(getShippingLineNo());
			dest.setShippingTicketNo(getShippingTicketNo());
			dest.setShortageCnt(getShortageCnt());
			dest.setStatusFlag(getStatusFlag());
			dest.setStockId(getStockId());
			dest.setSupplierCode(getSupplierCode());
			dest.setSupplierName1(getSupplierName1());
			dest.setTcdcFlag(getTcdcFlag());
			dest.setTerminalNo(getTerminalNo());
			dest.setUseByDate(getUseByDate());
			dest.setWorkerCode(getWorkerCode());
			dest.setWorkerName(getWorkerName());
			dest.setWorkFormFlag(getWorkFormFlag());
			dest.setSystemConnKey(getSystemConnKey());
			dest.setSystemDiscKey(getSystemDiscKey());
			dest.setWorkTime(getWorkTime());
			dest.setZoneNo(getZoneNo());
		}
		catch (InvalidStatusException e)
		{
			//#CM702700
			// Do not process this exception because it is sure not to be generated. 
		}
			
		return dest;
	}

	//#CM702701
	/**
	 * Generate the reproduction of the base class of the object. 
	 * @return Copy of present object
	 */
	public jp.co.daifuku.wms.base.entity.WorkingInformation getBaseInstance()
	{
		return (jp.co.daifuku.wms.base.entity.WorkingInformation) super.clone();
	}
    
	//#CM702702
	// Package methods -----------------------------------------------

	//#CM702703
	// Protected methods ---------------------------------------------

	//#CM702704
	// Private methods -----------------------------------------------
}
//#CM702705
//end of class
