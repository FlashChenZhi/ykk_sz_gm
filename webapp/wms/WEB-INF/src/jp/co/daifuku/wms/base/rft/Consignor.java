//#CM700244
//$Id: Consignor.java,v 1.2 2006/11/14 06:08:56 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700245
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.common.Entity;

//#CM700246
/**
 * The class to manage Consignor information. 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>K.Nishiura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:56 $
 * @author  $Author: suresh $
 */
public class Consignor extends Entity
{

	//#CM700247
	// Class variables -----------------------------------------------
	//#CM700248
	/**
	 * Consignor Code
	 */
	protected String wConsignorCode = "";

	//#CM700249
	/**
	 * Consignor Name
	 */
	protected String wConsignorName = "";

	//#CM700250
	/**
	 * ZIP code
	 */
	protected String wPostcode = "";

	//#CM700251
	/**
	 * Consignor Address 1
	 */
	protected String wAddress1 = "";

	//#CM700252
	/**
	 * Consignor Address 2
	 */
	protected String wAddress2 = "";

	//#CM700253
	/**
	 * TEL
	 */
	protected String wTEL = "";

	//#CM700254
	/**
	 * FAX
	 */
	protected String wFAX = "";

	//#CM700255
	/**
	 * Ticket No.
	 */
	protected String wTicketNo = "";

	//#CM700256
	/**
	 * Deletion flag
	 */
	protected String wDeleteFlag = "";

	//#CM700257
	/**
	 * Registration date
	 */
	protected String wRegistDate = "";

	//#CM700258
	/**
	 * The latest update date
	 */
	protected String wLastUpdateDate = "";

	//#CM700259
	// Class method --------------------------------------------------

	//#CM700260
	// Constructors --------------------------------------------------
	//#CM700261
	/**
	 * Use it when you set the retrieval result in vector(). 
	 * 
	 * @param ConsignorCode Person in charge code
	 * @param ConsignorName Consignor Name
	 * @param Postcode ZIP code
	 * @param Address1 Consignor Address 1
	 * @param Address2 Consignor Address 2
	 * @param TEL TEL
	 * @param FAX FAX
	 * @param TicketNo Ticket No.
	 * @param DeleteFlag Deletion flag
	 * @param RegistDate Registration date
	 * @param LastUpdateDate Last updated date
	 */
	public Consignor(
		String ConsignorCode,
		String ConsignorName,
		String Postcode,
		String Address1,
		String Address2,
		String TEL,
		String FAX,
		String TicketNo,
		String DeleteFlag,
		String RegistDate,
		String LastUpdateDate)
	{
		wConsignorCode = ConsignorCode;
		wConsignorName = ConsignorName;
		wPostcode = Postcode;
		wAddress1 = Address1;
		wAddress2 = Address2;
		wTEL = TEL;
		wFAX = FAX;
		wTicketNo = TicketNo;
		wDeleteFlag = DeleteFlag;
		wRegistDate = RegistDate;
		wLastUpdateDate = LastUpdateDate;
	}

	//#CM700262
	// Public methods ------------------------------------------------
	//#CM700263
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/14 06:08:56 $");
	}

	//#CM700264
	/**
	 * Set the value in Person in charge code. 
	 * @param Consignorno Person in charge code to be set
	 */
	public void setConsignorCode(String ConsignorCode)
	{
		wConsignorCode = ConsignorCode;
	}

	//#CM700265
	/**
	 * Acquire the value of Person in charge code. 
	 * @return Person in charge code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM700266
	/**
	 * Set the value in Consignor Name. 
	 * @param ConsignorName Consignor Name to be set
	 */
	public void setConsignorName(String ConsignorName)
	{
		wConsignorName = ConsignorName;
	}

	//#CM700267
	/**
	 * Acquire the value of Consignor Name. 
	 * @return Consignor Name
	 */
	public String getConsignorName()
	{
		return wConsignorName;
	}

	//#CM700268
	/**
	 * Set the value in ZIP code. 
	 * @param Postcode ZIP code to be set
	 */
	public void setPostcode(String Postcode)
	{
		wPostcode = Postcode;
	}

	//#CM700269
	/**
	 * Acquire the value of ZIP code. 
	 * @return ZIP code
	 */
	public String getPostcode()
	{
		return wPostcode;
	}

	//#CM700270
	/**
	 * Set the value in Consignor Address 1. 
	 * @param Address1 Consignor Address 1 to be set
	 */
	public void setAddress1(String Address1)
	{
		wAddress1 = Address1;
	}

	//#CM700271
	/**
	 * Acquire the value of Consignor Address 1. 
	 * @return Consignor Address 1
	 */
	public String getAddress1()
	{
		return wAddress1;
	}

	//#CM700272
	/**
	 * Set the value in Consignor Address 2. 
	 * @param Address2 Consignor Address 2 to be set
	 */
	public void setAddress2(String Address2)
	{
		wAddress2 = Address2;
	}

	//#CM700273
	/**
	 * Acquire the value of Consignor Address 2. 
	 * @return Consignor Address 2
	 */
	public String getAddress2()
	{
		return wAddress2;
	}

	//#CM700274
	/**
	 * Set the value in TEL. 
	 * @param Tel TEL to be Set
	 */
	public void setTEL(String Tel)
	{
		wTEL = Tel;
	}

	//#CM700275
	/**
	 * Acquire the value of TEL.
	 * @return TEL
	 */
	public String getTEL()
	{
		return wTEL;
	}

	//#CM700276
	/**
	 * Set the value in FAX. 
	 * @param Fax FAX to be set.
	 */
	public void setFAX(String Fax)
	{
		wFAX = Fax;
	}

	//#CM700277
	/**
	 * Acquire the value of FAX.
	 * @return FAX
	 */
	public String getFAX()
	{
		return wFAX;
	}

	//#CM700278
	/**
	 * Set the value in Ticket No.
	 * @param TicketNo Ticket No. to be set.
	 */
	public void setTicketNo(String TicketNo)
	{
		wTicketNo = TicketNo;
	}

	//#CM700279
	/**
	 * Acquire the value of Ticket No.
	 * @return Ticket No.
	 */
	public String getTicketNo()
	{
		return wTicketNo;
	}

	//#CM700280
	/**
	 * Set the value in Deletion flag.
	 * @param DeleteFlag Deletion flag to be set.
	 */
	public void setDeleteFlag(String DeleteFlag)
	{
		wDeleteFlag = DeleteFlag;
	}

	//#CM700281
	/**
	 * Acquire the value of Deletion flag.
	 * @return Deletion flag
	 */
	public String getDeleteFlag()
	{
		return wDeleteFlag;
	}

	//#CM700282
	/**
	 * Set the value in Registration date.
	 * @param RegistDate Registration date to be set.
	 */
	public void setRegistDate(String RegistDate)
	{
		wRegistDate = RegistDate;
	}

	//#CM700283
	/**
	 * Acquire the value of Registration date.
	 * @return Registration date
	 */
	public String getRegistDate()
	{
		return wRegistDate;
	}

	//#CM700284
	/**
	 * Set the value in the latest update date.
	 * @param LastUpdateDate The latest update date to be set.
	 */
	public void setLastUpdateDate(String LastUpdateDate)
	{
		wLastUpdateDate = LastUpdateDate;
	}

	//#CM700285
	/**
	 * Acquire the value of the latest update date.
	 * @return The latest update date
	 */
	public String getLastUpdateDate()
	{
		return wLastUpdateDate;
	}

	//#CM700286
	// Package methods -----------------------------------------------

	//#CM700287
	// Protected methods ---------------------------------------------

	//#CM700288
	// Private methods -----------------------------------------------

}
//#CM700289
//end of class
