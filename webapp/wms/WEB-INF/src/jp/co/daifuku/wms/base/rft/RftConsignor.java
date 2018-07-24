//#CM701883
//$Id: RftConsignor.java,v 1.2 2006/11/14 06:09:10 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import jp.co.daifuku.wms.base.common.Entity;

//#CM701884
/**
 * The class to manage Consignor information. 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>K.Nishiura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:10 $
 * @author  $Author: suresh $
 */
public class RftConsignor extends Entity
{
	//#CM701885
	/** Table name definition */

	public static final String TABLE_NAME = "DMCONSIGNOR" ;

	//#CM701886
	// Class variables -----------------------------------------------
	//#CM701887
	/**
	 * Consignor Code
	 */
	protected String wConsignorCode = "";

	//#CM701888
	/**
	 * Consignor Name
	 */
	protected String wConsignorName = "";

	//#CM701889
	/**
	 * ZIP code
	 */
	protected String wPostcode = "";

	//#CM701890
	/**
	 * Consignor Address 1
	 */
	protected String wAddress1 = "";

	//#CM701891
	/**
	 * Consignor Address 2
	 */
	protected String wAddress2 = "";

	//#CM701892
	/**
	 * TEL
	 */
	protected String wTEL = "";

	//#CM701893
	/**
	 * FAX
	 */
	protected String wFAX = "";

	//#CM701894
	/**
	 * Ticket No.
	 */
	protected String wTicketNo = "";

	//#CM701895
	/**
	 * Deletion flag
	 */
	protected String wDeleteFlag = "";

	//#CM701896
	/**
	 * Registration date
	 */
	protected String wRegistDate = "";

	//#CM701897
	/**
	 * The latest update date
	 */
	protected String wLastUpdateDate = "";

	//#CM701898
	// Class method --------------------------------------------------

	//#CM701899
	// Constructors --------------------------------------------------
	//#CM701900
	/**
	 * Use it when you set the retrieval result in vector(). 
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
	public RftConsignor(
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

	//#CM701901
	// Public methods ------------------------------------------------
	//#CM701902
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/14 06:09:10 $");
	}

	//#CM701903
	/**
	 * Set the value in Person in charge code. 
	 * @param Consignorno Person in charge code to be set
	 */
	public void setConsignorCode(String ConsignorCode)
	{
		wConsignorCode = ConsignorCode;
	}

	//#CM701904
	/**
	 * Acquire the value of Person in charge code. 
	 * @return Person in charge code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM701905
	/**
	 * Set the value in Consignor Name. 
	 * @param ConsignorName Consignor Name to be set
	 */
	public void setConsignorName(String ConsignorName)
	{
		wConsignorName = ConsignorName;
	}

	//#CM701906
	/**
	 * Acquire the value of Consignor Name. 
	 * @return Consignor Name
	 */
	public String getConsignorName()
	{
		return wConsignorName;
	}

	//#CM701907
	/**
	 * Set the value in ZIP code. 
	 * @param Postcode ZIP code to be set
	 */
	public void setPostcode(String Postcode)
	{
		wPostcode = Postcode;
	}

	//#CM701908
	/**
	 * Acquire the value of ZIP code. 
	 * @return ZIP code
	 */
	public String getPostcode()
	{
		return wPostcode;
	}

	//#CM701909
	/**
	 * Set the value in Consignor Address 1. 
	 * @param Address1 Consignor Address 1 to be set
	 */
	public void setAddress1(String Address1)
	{
		wAddress1 = Address1;
	}

	//#CM701910
	/**
	 * Acquire the value of Consignor Address 1. 
	 * @return Consignor Address 1
	 */
	public String getAddress1()
	{
		return wAddress1;
	}

	//#CM701911
	/**
	 * Set the value in Consignor Address 2. 
	 * @param Address2 Consignor Address 2 to be set
	 */
	public void setAddress2(String Address2)
	{
		wAddress2 = Address2;
	}

	//#CM701912
	/**
	 * Acquire the value of Consignor Address 2. 
	 * @return Consignor Address 2
	 */
	public String getAddress2()
	{
		return wAddress2;
	}

	//#CM701913
	/**
	 * Set the value in TEL. 
	 * @param Tel TEL to be Set
	 */
	public void setTEL(String Tel)
	{
		wTEL = Tel;
	}

	//#CM701914
	/**
	 * Acquire the value of TEL.
	 * @return TEL
	 */
	public String getTEL()
	{
		return wTEL;
	}

	//#CM701915
	/**
	 * Set the value in FAX. 
	 * @param Fax FAX to be set.
	 */
	public void setFAX(String Fax)
	{
		wFAX = Fax;
	}

	//#CM701916
	/**
	 * Acquire the value of FAX.
	 * @return FAX
	 */
	public String getFAX()
	{
		return wFAX;
	}

	//#CM701917
	/**
	 * Set the value in Ticket No.
	 * @param TicketNo Ticket No. to be set
	 */
	public void setTicketNo(String TicketNo)
	{
		wTicketNo = TicketNo;
	}

	//#CM701918
	/**
	 * Acquire the value of Ticket No.
	 * @return Ticket No.
	 */
	public String getTicketNo()
	{
		return wTicketNo;
	}

	//#CM701919
	/**
	 * Set the value in Deletion flag.
	 * @param DeleteFlag Deletion flag to be set
	 */
	public void setDeleteFlag(String DeleteFlag)
	{
		wDeleteFlag = DeleteFlag;
	}

	//#CM701920
	/**
	 * Acquire the value of Deletion flag. 
	 * @return Deletion flag
	 */
	public String getDeleteFlag()
	{
		return wDeleteFlag;
	}

	//#CM701921
	/**
	 * Set the value in Registration date. 
	 * @param RegistDate Registration date to be set
	 */
	public void setRegistDate(String RegistDate)
	{
		wRegistDate = RegistDate;
	}

	//#CM701922
	/**
	 * Acquire the value of Registration date. 
	 * @return Registration date
	 */
	public String getRegistDate()
	{
		return wRegistDate;
	}

	//#CM701923
	/**
	 * Set the value in The latest update date. 
	 * @param LastUpdateDate The latest update date to be set
	 */
	public void setLastUpdateDate(String LastUpdateDate)
	{
		wLastUpdateDate = LastUpdateDate;
	}

	//#CM701924
	/**
	 * Acquire the value of The latest update date. 
	 * @return The latest update date
	 */
	public String getLastUpdateDate()
	{
		return wLastUpdateDate;
	}
	
	//#CM701925
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM701926
	// Package methods -----------------------------------------------

	//#CM701927
	// Protected methods ---------------------------------------------

	//#CM701928
	// Private methods -----------------------------------------------

}
//#CM701929
//end of class
