// $Id: SendIdMessage.java,v 1.2 2006/11/14 06:09:20 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;

//#CM702491
/*
 * Copyright 2004-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
//#CM702492
/**
 * A super-class to take out each telegram common part by the RFT communication. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>K.Nishiura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:20 $
 * @author  $Author: suresh $
 */
  
public abstract class SendIdMessage extends IdMessage implements AnswerCode
{
	//#CM702493
	// Class field --------------------------------------------------
	//#CM702494
	// Class fields --------------------------------------------------
	//#CM702495
	// Class variables -----------------------------------------------
	//#CM702496
	/**
	 * Offset of Response flag
	 */
	protected int offAnsCode = 0;

	//#CM702497
	/**
	 *  Offset of Detailed Error
	 */
	protected int offErrorDetails = 0;

	//#CM702498
	// Class method --------------------------------------------------
    //#CM702499
    /**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:20 $";
	}

	//#CM702500
	// Constructors --------------------------------------------------
	//#CM702501
	/**
	 * Generate the instance. 
	 */
	public SendIdMessage()
	{
		super();
	}

	//#CM702502
	// Public methods ------------------------------------------------
	//#CM702503
	/**
	 * Set completed telegram in the buffer. 
	 * @param	smsg	Byte array for Transmission telegram
	 */
	public void getSendMessage(byte[] smsg)
	{
		String wbuf = getFromBuffer(0, length);
		byte[] buf = wbuf.getBytes();

		for (int i = 0; i < length; i ++)
		{
			smsg[i] = buf[i];
		}
	}

	//#CM702504
	/**
	 * Set Response flag. 
	 * @param	ansCode		Response flag to be set
	 */
	public void setAnsCode(String ansCode)
	{
		setToBuffer(ansCode, offAnsCode);
	}

	//#CM702505
	/**
	 *  Set Detailed Error. 
	 * @param errDetails	Detailed error to be set.
	 */
	public void setErrDetails(String errDetails)
	{
		setToBuffer(errDetails, offErrorDetails);
	}

	//#CM702506
	/**
	 * Set ETX. 
	 */
	public void setETX()
	{
		setToByteBuffer(DEF_ETX, offEtx);
	}
	
	//#CM702507
	/**
	 * Check whether Response Code is correct. 
	 * @param ansCode		Response Code
	 * @return				Returned true if it is correct and false if it is not correct.
	 */
	public static boolean checkAnsCode(String ansCode)
	{
	    if (ansCode == null || ansCode.trim().equals(""))
	    {
	        return false;
	    }
	    
	    if (ansCode.trim().equals(ANS_CODE_NORMAL)
	            || ansCode.trim().equals(ANS_CODE_WORKING)
	            || ansCode.trim().equals(ANS_CODE_COMPLETION)
	            || ansCode.trim().equals(ANS_CODE_UPDATE_FINISH)
	            || ansCode.trim().equals(ANS_CODE_DAILY_UPDATING)
	            || ansCode.trim().equals(ANS_CODE_MAINTENANCE)
	            || ansCode.trim().equals(ANS_CODE_SOME_DATA)
	            || ansCode.trim().equals(ANS_CODE_NULL)
	            || ansCode.trim().equals(ANS_CODE_ERROR))
	    {
		    return true;
	    }

	    return false;
	}

	//#CM702508
	/**
	 * Preserve response telegram in RFTWork information. 
	 * 
	 * @param rftNo	 RFT machine No
	 * @param className  Last update processing name
	 * @param conn Connection object with data base
	 * @throws InvalidDefineException The specified value is notified. 
	 * @throws NotFoundException It is notified when Data does not exist. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void saveResponseId(String rftNo, String className, Connection conn) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		
		saveMessageData(rftNo, conn, className);
	}
	
	//#CM702509
	// Package methods -----------------------------------------------

	//#CM702510
	// Protected methods ---------------------------------------------

	//#CM702511
	// Private methods -----------------------------------------------

}
//#CM702512
//end of class
