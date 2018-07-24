// $Id: DateOperator.java,v 1.2 2006/10/24 08:22:06 suresh Exp $
package jp.co.daifuku.wms.asrs.common ;

//#CM28605
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.co.daifuku.common.InvalidStatusException;

//#CM28606
/**
 * This class is to manage dates and hour.
 * In future, this will be transferred to the package:jp.co.daifuku.common.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/03/13</TD><TD>sawa</TD><TD><CODE>parse(String str, String pattern)</CODE>method added</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/24 08:22:06 $
 * @author  $Author: suresh $
 */
public class DateOperator
{
	//#CM28607
	// Class fields --------------------------------------------------
	//#CM28608
	/**
	 * Format for date "yyyy MM dd HH mm ss SSS z"
	 */
	public static final String FORMAT_GMT = "yyyy MM dd HH mm ss SSS z" ;
	//#CM28609
	/**
	 * Format for date "yyyy/MM/dd"
	 */
	public static final String FORMAT_DATE = "yyyy/MM/dd" ;
	//#CM28610
	/**
	 * ormat for date "yyyy/MM/dd HH:mm:ss"
	 */
	public static final String FORMAT_DATETIME = "yyyy/MM/dd HH:mm:ss" ;

	//#CM28611
	// Class variables -----------------------------------------------

	//#CM28612
	// Class method --------------------------------------------------
	//#CM28613
	/**
	 * Returns the value of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/24 08:22:06 $") ;
	}

	//#CM28614
	/**
	 * Gets the current day and hour and edits in yyyy format.
	 * @return    dateString Current day and hour in yyyy format
	 */
    public static String getSysdateYear() {
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	//#CM28615
	/**
	 * Gets the current day and hour and edits in MM format.
	 * @return    dateString Current day and hour in MM format
	 */
    public static String getSysdateMonth() {
		SimpleDateFormat formatter = new SimpleDateFormat ("MM");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	//#CM28616
	/**
	 * Gets the current day and hour and edits in dd format.
	 * @return    dateString Current day and hour in dd format
	 */
    public static String getSysdateDay() {
		SimpleDateFormat formatter = new SimpleDateFormat ("dd");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	//#CM28617
	/**
	 * Gets the current day and hour and edits in HH format.
	 * @return    dateString Current day and hour in HH format
	 */
    public static String getSysdateHour24() {
		SimpleDateFormat formatter = new SimpleDateFormat ("HH");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	//#CM28618
	/**
	 * Gets the current day and hour and edits in mm format.
	 * @return    dateString Current day and hour in mm format
	 */
    public static String getSysdateMinute() {
		SimpleDateFormat formatter = new SimpleDateFormat ("mm");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	//#CM28619
	/**
	 * Gets the current day and hour and edits in ss formatt.
	 * @return    dateString Current day and hour in ss format
	 */
    public static String getSysdateSecond() {
		SimpleDateFormat formatter = new SimpleDateFormat ("ss");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	//#CM28620
	/**
	 * Gets the current day and hour and edits in yyyyMMdd format.
	 * @return    dateString Current day and hour in dd format
	 */
    public static String getSysdate() {

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);

		return dateString;
	}

	//#CM28621
	/**
	 * Edit in yyyyMMddHH format
	 * @return    dateString Current day and hour in yyyyMMddHH format.
	 */
    public static String getSysdateHour() {

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHH");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);

		return dateString;
	}

	//#CM28622
	/**
	 * Gets the current day and hour and edits in yyyyMMddHHmmss format.
	 * @return    dateString Current day and hour in yyyyMMddHHmmss format.
	 */
    public static String getSysdateTime() {

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);

		return dateString;
    }

	//#CM28623
	/**
	 * Changes the date type variable to string of the type yyyy/MM/dd
	 * @param     date type variable
	 * @return    dateString yyyy/MM/dd type
	 */
    public static String changeDate(Date cgDate)
    {
		if (cgDate == null)
		{
			return "";
		}
		SimpleDateFormat frmt = new SimpleDateFormat ("yyyy/MM/dd");
		return frmt.format(cgDate);
    }

	//#CM28624
	/**
	 * Changes the date type variable to string of the type yyyy/MM/dd HH:mm:ss
	 * @param     date type variable
	 * @return    dateString yyyy/MM/dd HH:mm:ss type
	 */
    public static String changeDateTime(Date cgDate) 
    {
		if (cgDate == null)
		{
			return "";
		}
		SimpleDateFormat frmt = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");
		return frmt.format(cgDate);
    }

	//#CM28625
	/**
	 * Changes the date type variable to string of the type yyyyMMddHHmm
	 * @param     date type variable
	 * @return    dateString yyyyMMddHHmm
	 */
    public static String changeDateTimeSimple(Date cgDate) 
    {
		if (cgDate == null)
		{
			return "";
		}
		SimpleDateFormat frmt = new SimpleDateFormat ("yyyyMMddHHmm");
		return frmt.format(cgDate);
    }
	//#CM28626
	/**
	 * Changes the date type variable to string of the type HH:mm:ss.iii
	 * @param     date type variable
	 * @return    dateString HH:mm:ss.iii format
	 */
    public static String changeDateTimeMillis(Date cgDate) 
    {
		if (cgDate == null)
		{
			return "";
		}
		SimpleDateFormat frmt = new SimpleDateFormat ("HH:mm:ss.SSS");
		return frmt.format(cgDate);
    }
	//#CM28627
	/**
	 * Adds months to current date and hour then returns in String.
	 * Argument of month is int.
	 * @param     Supposing current month 0, set -1 for previous month and 1 for next month.
	 * @return    Current date and hour in yyyy/MM format
	 */
    public static String addMonth(int month)
    {
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM");
		Calendar         curDate   = Calendar.getInstance();
		curDate.add(Calendar.MONTH, month);
		Date   date       = curDate.getTime();
		String dateString = formatter.format(date);
		return dateString;
    }

	//#CM28628
	/**
	 * Adds months to the current date and hour, then retunrs it in String.
	 * Strmonth in argument is String. It returns "" if translation to Integer failed.
	 * @param     Supposing str month 0, set -1 for previous month and 1 for next month.
	 * @return    Current date and hour in dateString yyyy/MM format
	 */
    public static String addMonth(String strmonth)
    {
		int month = 0;
		try
		{
			month = Integer.parseInt(strmonth);
		}
		catch (Exception e)
		{
			return "";
		}

		return addMonth(month);
    }

    //#CM28629
    /**
	 * Gets the current date and hour then edit in HHmmss format.
	 * @return    dateString Current date and hour in HHmmss format
	 */
    public static String getSysTime() {

		SimpleDateFormat formatter = new SimpleDateFormat ("HHmmss");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);

		return dateString;
    }

	//#CM28630
	/**
	 * Gets date and hour of previous day then returns in yyyy/MM/dd, edited.
	 * @return    Current date and hour in String of yyyy/MM/dd format
	 */
	public static String getPrecedingDay()
	{
		Calendar cal = Calendar.getInstance();
		//#CM28631
		//Set back the date to the previous day.
		cal.add(Calendar.DATE, -1);
		
		Date date = cal.getTime();
		return changeDate(date);
	}

	//#CM28632
	/**
	 * Arranges '/' and ':' from deleting.
	 * Ex. 20000101000000 to change to 2000/01/01 00:00:00
	 * @param     date Day and hour before editing
	 * @return    str  Day and hour after editing
	 */
	public static String convDateform (String date) {

		String str = date.substring(0,4);
		str += "/";
		str += date.substring(4,6);
		str += "/";
		str += date.substring(6,8);
		str += " ";
		str += date.substring(8,10);
		str += ":";
		str += date.substring(10,12);
		str += ":";
		str += date.substring(12,14);

		return str;
	}

	//#CM28633
	/**
	 * Specifies String which date/hour is set and also the format type of the String then returns instance.
	 * @param     str     String that date/time is set
	 * @param     pattern Format type of the String
	 * @return    Parsed Date instance
	 * @throws    InvalidStatusException Notifies if the length of the String set with date/hour and that of format type String do not match.
	 */
	public static Date parse(String str, String pattern) throws InvalidStatusException
	{
		if (str.length() != pattern.length())
		{
			throw new InvalidStatusException("FTTB 6020037") ;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.parse(str, new ParsePosition(0));
	}

	//#CM28634
	// Constructors --------------------------------------------------

	//#CM28635
	// Public methods ------------------------------------------------

	//#CM28636
	// Package methods -----------------------------------------------

	//#CM28637
	// Protected methods ---------------------------------------------

	//#CM28638
	// Private methods -----------------------------------------------

}
//#CM28639
//end of class
