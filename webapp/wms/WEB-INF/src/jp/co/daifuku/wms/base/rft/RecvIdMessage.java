// $Id: RecvIdMessage.java,v 1.2 2006/11/14 06:09:09 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701868
/*
 * Copyright 2004-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
//#CM701869
/**
 * A super-class to take out each Receiving telegram common part by the RFT communication. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>K.Nishiura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:09 $
 * @author  $Author: suresh $
 */
  
public abstract class RecvIdMessage extends IdMessage
{
	//#CM701870
	// Class field --------------------------------------------------
	//#CM701871
	// Class fields --------------------------------------------------
	//#CM701872
	// Class variables -----------------------------------------------
	//#CM701873
	// Class method --------------------------------------------------
    //#CM701874
    /**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:09 $";
	}

	//#CM701875
	// Constructors --------------------------------------------------
    //#CM701876
    /**
	 * Generate the instance. 
	 */
	public RecvIdMessage()
	{
		super();
	}

	//#CM701877
	// Public methods ------------------------------------------------
	
	//#CM701878
	// Package methods -----------------------------------------------

	//#CM701879
	// Protected methods ---------------------------------------------
	//#CM701880
	/**
	 * Set telegram received from RFT in an internal buffer. 
	 * @param	rmsg	Byte row of Receiving telegram
	 */
	public void setReceiveMessage(byte[] rmsg)
	{
		for (int i = 0; i < length; i++)
		{
			wLocalBuffer[i] = rmsg[i];
		}
		wDataBuffer = wLocalBuffer;
	}

	//#CM701881
	// Private methods -----------------------------------------------

}
//#CM701882
//end of class
