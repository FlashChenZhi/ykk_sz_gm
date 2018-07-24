package jp.co.daifuku.wms.base.location;

//#CM670642
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.common.Parameter;

//#CM670643
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * <CODE>EmptyLocation</CODE> class uses it to manage empty location information in the location package. <BR>
 * <BR>
 * <DIR>
 * <CODE>EmptyLocation</CODE>Item that this class maintains<BR>
 * <BR>
 *     Empty Location No. <BR>
 *     Return information <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/15</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $
 * @author  $Author: suresh $
 */
public class EmptyLocation extends Parameter
{
	//#CM670644
	// Class feilds ------------------------------------------------
	//#CM670645
	/**
	 * Return information (Normal) 
	 */
	public static final int RET_OK = 0 ;
	
	//#CM670646
	// Class feilds ------------------------------------------------
	//#CM670647
	/**
	 * Return information (Condition Error) 
	 */
	public static final int RET_ERR = 1 ;
	
	//#CM670648
	// Class feilds ------------------------------------------------
	//#CM670649
	/**
	 * Return information (No empty location) 
	 */
	public static final int RET_NOEMPTYLOCATION = 2 ;
	
	//#CM670650
	// Class variables -----------------------------------------------
	//#CM670651
	/**
	 * Empty Location No.
	 */
	private String wLocation;

	//#CM670652
	/**
	 * Bank No.
	 */
	private String wBankNo;

	//#CM670653
	/**
	 * Bay No.
	 */
	private String wBayNo;

	//#CM670654
	/**
	 * Level No.
	 */
	private String wLevelNo;

	//#CM670655
	/**
	 * Return information
	 */
	private int wReturnCode;

	//#CM670656
	// Class method --------------------------------------------------
	//#CM670657
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Date: 2006/11/07 06:58:10 $");
	}

	//#CM670658
	// Constructors --------------------------------------------------
	//#CM670659
	/**
	 * Initialize this class. 
	 */
	public EmptyLocation()
	{
	}

	//#CM670660
	// Public methods ------------------------------------------------
	//#CM670661
	/**
	 * Set Empty Location No.
	 * @param arg Empty Location No. to be set.
	 */
	public void setLocation(String arg)
	{
		wLocation = arg ;
	}

	//#CM670662
	/**
	 * Acquire Empty Location No.
	 * @return Empty Location No.
	 */
	public String getLocation()
	{
		return wLocation ;
	}

	//#CM670663
	/**
	 * Set Bank No.
	 * @param arg Bank No. to be set.
	 */
	public void setBankNo(String arg)
	{
		wBankNo = arg ;
	}

	//#CM670664
	/**
	 * Acquire Bank No.
	 * @return Bank No.
	 */
	public String getBankNo()
	{
		return wBankNo ;
	}

	//#CM670665
	/**
	 * Set Bay No.
	 * @param arg Bay No. to be set
	 */
	public void setBayNo(String arg)
	{
		wBayNo = arg ;
	}

	//#CM670666
	/**
	 * Acquire Bay No.
	 * @return Bay No.
	 */
	public String getBayNo()
	{
		return wBayNo ;
	}

	//#CM670667
	/**
	 * Set Level No.
	 * @param arg Level No. to be set
	 */
	public void setLevelNo(String arg)
	{
		wLevelNo = arg ;
	}

	//#CM670668
	/**
	 * Acquire Level No.
	 * @return Level No.
	 */
	public String getLevelNo()
	{
		return wLevelNo ;
	}

	//#CM670669
	/**
	 * Set Return information.
	 * @param arg Return information to be set
	 */
	public void setReturnCode(int arg)
	{
		wReturnCode = arg ;
	}

	//#CM670670
	/**
	 * Acquire Return information
	 * @return Return information
	 */
	public int getReturnCode()
	{
		return wReturnCode ;
	}

}

