// $Id: DateOperator.java,v 1.1.1.1 2006/08/17 09:33:43 mori Exp $
package jp.co.daifuku.common ;

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
// import jp.co.daifuku.common.* ;

/**
 * 日時を操作するクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/03/13</TD><TD>sawa</TD><TD><CODE>parse(String str, String pattern)</CODE>?a??u/TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:43 $
 * @author  $Author: mori $
 */
public class DateOperator
{
	// Class fields --------------------------------------------------
	/**
	 * 日付のフォーマット形式 "yyyy MM dd HH mm ss SSS z"
	 */
	public static final String FORMAT_GMT = "yyyy MM dd HH mm ss SSS z" ;
	/**
	 * 日付のフォーマット形式 "yyyy/MM/dd"
	 */
	public static final String FORMAT_DATE = "yyyy/MM/dd" ;
	/**
	 * 日付のフォーマット形式 "yyyy/MM/dd HH:mm:ss"
	 */
	public static final String FORMAT_DATETIME = "yyyy/MM/dd HH:mm:ss" ;

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:33:43 $") ;
	}

	/**
	 * 現在の日時を取得し、yyyyの形式で編集
	 * @return    dateString yyyyの形式の現在の日時
	 */
    public static String getSysdateYear() {
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	/**
	 * 現在の日時を取得し、MMの形式で編集
	 * @return    dateString MMの形式の現在の日時
	 */
    public static String getSysdateMonth() {
		SimpleDateFormat formatter = new SimpleDateFormat ("MM");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	/**
	 * 現在の日時を取得し、ddの形式で編集
	 * @return    dateString ddの形式の現在の日時
	 */
    public static String getSysdateDay() {
		SimpleDateFormat formatter = new SimpleDateFormat ("dd");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	/**
	 * 現在の日時を取得し、 HHの形式で編集
	 * @return    dateString HHの形式の現在の日時
	 */
    public static String getSysdateHour24() {
		SimpleDateFormat formatter = new SimpleDateFormat ("HH");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	/**
	 * 現在の日時を取得し、 mmの形式で編集
	 * @return    dateString mmの形式の現在の日時
	 */
    public static String getSysdateMinute() {
		SimpleDateFormat formatter = new SimpleDateFormat ("mm");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	/**
	 * 現在の日時を取得し、 ssの形式で編集
	 * @return    dateString ssの形式の現在の日時
	 */
    public static String getSysdateSecond() {
		SimpleDateFormat formatter = new SimpleDateFormat ("ss");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	/**
	 * 現在の日時を取得し、 yyyyMMddの形式で編集
	 * @return    dateString yyyyMMddの形式の現在の日時
	 */
    public static String getSysdate() {

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);

		return dateString;
	}

	/**
	 * 現在の日時を取得し、 yyyyMMddHHの形式で編集
	 * @return    dateString yyyyMMddHHの形式の現在の日時
	 */
    public static String getSysdateHour() {

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHH");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);

		return dateString;
	}

	/**
	 * 現在の日時を取得し、 yyyyMMddHHmmssの形式で編集
	 * @return    dateString yyyyMMddHHmmssの形式の現在の日時
	 */
    public static String getSysdateTime() {

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);

		return dateString;
    }

	/**
	 * Changes the date type variable to string of the type yyyy/MM/dd
	 * @param     date type variable
	 * @return    dateString yyyy/MM/ddの形式
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

	/**
	 * Changes the date type variable to string of the type yyyy/MM/dd HH:mm:ss
	 * @param     date type variable
	 * @return    dateString yyyy/MM/dd HH:mm:ssの形式
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

	/**
	 * Changes the date type variable to string of the type yyyyMMddHHmm
	 * @param     date type variable
	 * @return    dateString yyyyMMddHHmmの形式
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
	/**
	 * Changes the date type variable to string of the type HH:mm:ss.iii
	 * @param     date type variable
	 * @return    dateString HH:mm:ss.iiiの形式
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
	/**
	 * 現在日時に月を加算し、Stringで返します。
	 * 引数のmonthはintです。
	 * @param     month 当月を0とし、前月であれは-1、翌月であれば1をセットして下さい。
	 * @return    dateString yyyy/MMの形式の現在の日付
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

	/**
	 * 現在日時に月を加算し、Stringで返します。
	 * 引数のsrtmouthはStringです。Integerに変換失敗した時は""を返します。
	 * @param     strmonth 当月を0とし、前月であれば-1、翌月であれば1をセットして下さい。
	 * @return    dateString yyyy/MMの形式の現在の日付
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

    /**
	 * 現在の日時を取得し、HHmmssの形式で編集
	 * @return    dateString HHmmssの形式の現在の日時
	 */
    public static String getSysTime() {

		SimpleDateFormat formatter = new SimpleDateFormat ("HHmmss");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);

		return dateString;
    }

	/**
	 * 前日の日時を取得し、yyyy/MM/ddの形式で編集したものを返します。
	 * @return    String yyyy/MM/ddの形式の前日の日付
	 */
	public static String getPrecedingDay()
	{
		Calendar cal = Calendar.getInstance();
		//??p????
		cal.add(Calendar.DATE, -1);
		
		Date date = cal.getTime();
		return changeDate(date);
	}
	/**
	 * 現在日時に日付加算し、Stringで返します。
	 * 引数のintdateはintです。
	 * @param     intdate 当日を0とし、前日であれは-1、翌日であれば1をセットして下さい。
	 * @return    dateString yyyy/MMの形式の加算された日付
	 */
    public static String addDate(int intdate)
    {
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd");
		Calendar         curDate   = Calendar.getInstance();
		curDate.add(Calendar.DATE, intdate);
		Date   date       = curDate.getTime();
		String dateString = formatter.format(date);
		return dateString;
    }

	/**
	 * 現在日時に日付加算し、Stringで返します。
	 * 引数のstrdateはStringです。Integerに変換失敗した時は""を返します。
	 * @param     strdate 当日を0とし、前日であれば-1、翌日であれば1をセットして下さい。
	 * @return    dateString yyyy/MMの形式の加算された日付
	 */
    public static String addDate(String strdate)
    {
		int date = 0;
		try
		{
			date = Integer.parseInt(strdate);
		}
		catch (Exception e)
		{
			return "";
		}

		return addDate(date);
    }

	/**
	 * 日時の'/',':'編集を行います。
	 * Σ 20000101000000 ?2000/01/01 00:00:00 へ変更
	 * @param     date 編集前の日付
	 * @return    str  編集後の日付
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

	/**
	 * 日付の'/'編集を行います。
	 * Σ 20000101 ?2000/01/01 へ変更
	 * @param     date 編集前の日付
	 * @return    str  編集後の日付
	 */
	public static String convDateformat (String date) {

		String str = date.substring(0,4);
		str += "/";
		str += date.substring(4,6);
		str += "/";
		str += date.substring(6,8);

		return str;
	}

	/**
	 * 時間の':'編集を行います。
	 * Σ 103045 ?10:30:45 へ変更
	 * @param     date 編集前の時間
	 * @return    str  編集後の時間
	 */
	public static String convTimeformat (String time) {

		String str = time.substring(0,4);
		str += ":";
		str += time.substring(4,6);
		str += ":";
		str += time.substring(6,8);

		return str;
	}

	/**
	 * 日付がセットされている文字列と文字列のフォーマット形式を指定してDateインスタンスを返します。
	 * @param     str     日時がセットされている文字列
	 * @param     pattern 文字列のフォーマット形式
	 * @return    バースしたDateインスタンス
	 * @throws    InvalidStatusException 日時がセットされている文字列とフォーマット形式文字列の長さがｱﾝマッチの時、通知します。
	 */
	public static Date parse(String str, String pattern) throws InvalidStatusException
	{
		if (str.length() != pattern.length())
		{
			throw new InvalidStatusException("6004001") ;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.parse(str, new ParsePosition(0));
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
