// $Id: TcdcFlag.java,v 1.2 2006/11/14 06:09:21 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM702558
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702559
/**
 * Define the value of TC/DC flag in telegram of the RFT communication. 
 * It basically becomes the same value as TC/DC flag of Work information. 
 * 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:21 $
 * @author  $Author: suresh $
 */
public class TcdcFlag
{
	//#CM702560
	// Class fields --------------------------------------------------
    //#CM702561
    /**
     * TC/DC flag  : DC
     */
    public static final String DC = WorkingInformation.TCDC_FLAG_DC;

    //#CM702562
    /**
     * TC/DC flag  : Cross TC
     */
    public static final String CROSS_TC = WorkingInformation.TCDC_FLAG_CROSSTC;

    //#CM702563
    /**
     * TC/DC flag  : TC
     */
    public static final String TC = WorkingInformation.TCDC_FLAG_TC;

    //#CM702564
    /**
     * TC/DC flag for batch of Supplier and unit Receiving Inspection of Item : DC
     */
    public static final String DC_BY_SUPPLIER = "0";

    //#CM702565
    /**
     * TC/DC flag for batch of Supplier and unit Receiving Inspection of item : Cross TC
     */
    public static final String CROSS_TC_BY_SUPPLIER = "1";
    
	//#CM702566
	// Class variables -----------------------------------------------
	//#CM702567
	// Class method --------------------------------------------------
    //#CM702568
    /**
     * Convert it into the value of TC/DC flag which uses the value of TC/DC flag of Work information with telegram of 
     * Receiving Inspection of the batch of Supplier and each item. 
     * 
     * @param tcdc		TC/DC flag of Work information 
     * @return			TC/DC flag of telegram 
     * @throws InvalidStatusException	It is notified when illegal TC/DC flag is passed. 
     */
    public static String convertTcdcBySupplier(String tcdc) throws InvalidStatusException
    {
        if (tcdc.equals(DC))
        {
            return DC_BY_SUPPLIER;
        }
        else if (tcdc.equals(CROSS_TC))
        {
            return CROSS_TC_BY_SUPPLIER;
        }
        
        throw new InvalidStatusException();
    }

    //#CM702569
    /**
     * Convert the value of TC/DC flag used with telegram of Receiving Inspection of the batch of Supplier and each item into 
     * the value of TC/DC flag of Work information. 
     * 
     * @param tcdcBySupplier	TC/DC flag of telegram 
     * @return					TC/DC flag of Work information 
     * @throws InvalidStatusException	It is notified when illegal TC/DC flag is passed. 
     */
    public static String convertTcdc(String tcdcBySupplier) throws InvalidStatusException
    {
        if (tcdcBySupplier.equals(DC_BY_SUPPLIER))
        {
            return DC;
        }
        else if (tcdcBySupplier.equals(CROSS_TC_BY_SUPPLIER))
        {
            return CROSS_TC;
        }
        
        throw new InvalidStatusException();
    }
    
	//#CM702570
	// Constructors --------------------------------------------------
	//#CM702571
	// Public methods ------------------------------------------------
	//#CM702572
	// Package methods -----------------------------------------------
	//#CM702573
	// Protected methods ---------------------------------------------
	//#CM702574
	// Private methods -----------------------------------------------
}
//#CM702575
//end of class
