// $Id: WmsFormatter.java,v 1.3 2007/03/08 09:34:41 okamura Exp $
//#CM665132
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.entity.SystemDefine;
//#CM665133
/**
 * The class which formats it with WareNavi for the screen display. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/19</TD><TD>H.Akiyama(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/03/08 09:34:41 $
 * @author  $Author: okamura $
 */
public class WmsFormatter extends Formatter
{
	//#CM665134
	// Class fields --------------------------------------------------

	//#CM665135
	// Class variables -----------------------------------------------
      private static Locale dummy_locale = Locale.ENGLISH;
      
	//#CM665136
	// Class method --------------------------------------------------

	//#CM665137
	// Constructors --------------------------------------------------

	//#CM665138
	// Public methods ------------------------------------------------

	//#CM665139
	/**
	 * Convert it into the Progress rate mark. <BR>
	 * <BR>
	 * Outline:Set the value of double type in Parameter, and return the value of String type by formatting ###0.0%. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *       Progress rate(double type)<BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 *       Progress rate(String type)<BR>
	 *    </DIR>
	 * </DIR>
	 * @param progressRate Progress rate (double type) 
	 * @throws Exception Reports on all exceptions.  
	 */
	public static String changeProgressRate(double progressRate) throws Exception
	{
		String changeProgressRate;
		//#CM665140
		// The progress rate up to one digit is displayed rarely.(DecimalFormat("##0.0%"))
		DecimalFormat dft = new DecimalFormat("##0.0" + DisplayText.getText("LBL-W0343"));
		changeProgressRate = dft.format(progressRate);
		
		return changeProgressRate;
	}
	
	//#CM665141
	/**
	 * Convert it into Date type from Date format of schedule Parameter. <BR>
	 * <BR>
	 * Outline:Convert it into Date type from character string (yyyyMMdd) to pass it to 
	 * schedule Parameter by using SimpleDateFormat. <BR>
	 * When the argument is an empty character, Return null. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 *   <DIR>
	 *   Date(String type:yyyyMMdd)*<BR>
	 *   </DIR>
	 *   <BR>
	 * [Return data]<BR>
	 *   <DIR>
	 *   Date(Date type)<BR>
	 *   </DIR>
	 * 
	 * @param Date format of schedule Parameter
	 * @return Date object
	 */
	public static Date toDate(String paramDate)
	{
		//#CM665142
		// Return null when the argument is an empty character. 
		if(StringUtil.isBlank(paramDate))
		{
			return null;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(WMSConstants.PARAM_DATE_FORMAT);
		Date changeDate = null;

		try
		{
			//#CM665143
			// Convert it from yyyyMMdd to Date. 
			changeDate = dateFormat.parse(paramDate);
		}
		catch(ParseException pe)
		{
			//#CM665144
			// Return null for Exception. 
			return null;
		}
		
		return changeDate;
	}
	
	//#CM665145
	/**
	 * Convert it into Date type from Date format displayed in label. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 *   <DIR>
	 *   Date(ja:yyyy/MM/dd)*<BR>
	 * 	 Locale*<BR>
	 *   </DIR>
	 * <BR>
	 * [Return data]<BR>
	 *   <DIR>
	 *   Date(Date type)<BR>
	 *   </DIR>
	 * 
	 * @param dispDate Date format displayed in label
	 * @param locale <CODE>Locale</CODE> object where regional code is set
	 * @return Date object
	 */
	public static Date toDate(String dispDate, Locale locale)
	{
		//#CM665146
		// Return the empty character when the argument is a empty character. 
		if(StringUtil.isBlank(dispDate))
		{
			return null;
		}
		
		//#CM665147
		// Convert from the format for the display into Date. 
		//return Formatter.getDate(dispDate, Constants.F_DATE, locale); 
		
		
		if( "ja".equals(locale.getLanguage()) || "en".equals(locale.getLanguage()) )
		{
			return Formatter.getDate(dispDate, Constants.F_DATE, locale);
		}
		else
		{
			return Formatter.getDate(dispDate, Constants.F_DATE, dummy_locale);
		}
		

	}

	//#CM665148
	/**
	 * Convert it into the Date format displayed from Date format of schedule Parameter in the label. <BR>
	 * <BR>
	 * Outline:Convert into the Date format according to Locale to display it from Date format (HHmmss) of schedule 
	 * Parameter in the label by using the Formatter class. 
	 * Convert to Yyyy/MM/dd in case of Japan and Yyyy/MM/dd in case of English.<BR>
	 * Return the empty character when the argument is an empty character.<BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 *   <DIR>
	 *   Date(String type:yyyyMMdd)*<BR>
	 * 	 Locale*<BR>
	 *   </DIR>
	 * <BR>
	 * [Return data]<BR>
	 *   <DIR>
	 *   Date(For ex.:yyyy/MM/dd(ja), mm/DD/yyyy(en))<BR>
	 *   </DIR>
	 * 
	 * @param paramDate Date format of schedule Parameter
	 * @param locale <CODE>Locale</CODE> object where regional code is set
	 * @return Format of Date displayed in label 
	 */
	public static String toDispDate(String paramDate, Locale locale)
	{
		//#CM665149
		// Return the empty character when the argument is a empty character. 
		if(StringUtil.isBlank(paramDate))
		{
			return "";
		}

		//#CM665150
		// Converts from String type(yyyyMMdd) to Date type. 
		Date date = toDate(paramDate);

		//#CM665151
		// Use getDateFormat(Date argValue, int formatType, Locale locale) method of the Formatter class. 
		//#CM665152
		// Converts into Date of String type according to Locale.
		
		
//		String changeDate = Formatter.getDateFormat(date, Constants.F_DATE, locale);
		
		String changeDate = ""; 
		
		if( "ja".equals(locale.getLanguage()) || "en".equals(locale.getLanguage()) )
		{
			changeDate = Formatter.getDateFormat(date, Constants.F_DATE, locale);
		}
		else
		{
			changeDate = Formatter.getDateFormat(date, Constants.F_DATE, dummy_locale);
		}
		
		return changeDate;
	}

	//#CM665153
	/**
	 * Convert into Date type from the form of the time of schedule Parameter. <BR>
	 * <BR>
	 * Outline:Convert into Date type from character string (HHmmss) to pass it to schedule 
	 * Parameter by using SimpleDateFormat. <BR>
	 * When the argument is an empty character, Return null. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 *   <DIR>
	 *   Time(String type:HHmmss)*<BR>
	 *   </DIR>
	 *   <BR>
	 * [Return data]<BR>
	 *   <DIR>
	 *   Time(Date type)<BR>
	 *   </DIR>
	 * 
	 * @param Time format of schedule Parameter
	 * @return Date object
	 */
	public static Date toTime(String paramTime)
	{
		//#CM665154
		// Return null when the argument is an empty character. 
		if(StringUtil.isBlank(paramTime))
		{
			return null;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
		Date changeTime = null;

		try
		{
			//#CM665155
			// Convert from HHmmss to Date. 
			changeTime = dateFormat.parse(paramTime);
		}
		catch(ParseException pe)
		{
			//#CM665156
			// Return null for Exception. 
			return null;
		}

		return changeTime;
	}

	//#CM665157
	/**
	 * Convert it into the Time format displayed from Time format of schedule Parameter in the label. <BR>
	 * <BR>
	 * Outline:Convert into the Time format according to Locale to display it from Time format (HHmmss) of schedule 
	 * Parameter in the label by using the Formatter class. <BR>
	 * Return the empty character when the argument is an empty character.<BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 *   <DIR>
	 *   Time(String type:HHmmss)*<BR>
	 * 	 Locale*<BR>
	 *   </DIR>
	 * <BR>
	 * [Return data]<BR>
	 *   <DIR>
	 *   Time HH:MM:SS<BR>
	 *   </DIR>
	 * 
	 * @param paramTime Time format of schedule Parameter
	 * @param locale <CODE>Locale</CODE> object where regional code is set
	 * @return Format of Time displayed in label 
	 */
	public static String toDispTime(String paramTime, Locale locale)
	{
		//#CM665158
		// Return the empty character when the argument is a empty character. 
		if(StringUtil.isBlank(paramTime))
		{
			return "";
		}

		//#CM665159
		// Converts from String type(HHmmss) to Date type. 
		Date date = toTime(paramTime);

		//#CM665160
		// Use getDateFormat(Date argValue, int formatType, Locale locale) method of the Formatter class. 
		//#CM665161
		// Converts into Time of String type according to Locale. 
		String changeDate = "";
		
		if( "ja".equals(locale.getLanguage()) || "en".equals(locale.getLanguage()) )
		{
			changeDate = Formatter.getDateFormat(date, Constants.F_TIME, locale);
		}
		else
		{
			changeDate = Formatter.getDateFormat(date, Constants.F_TIME, dummy_locale);
		}
			 

		return changeDate;
	}

	//#CM665162
	/**
	 * Convert schedule Parameter from Date type into the Date format. <BR>
	 * <BR>
	 * Outline:Convert schedule Parameter into Date form (yyyyMMdd) by using the SimpleDateFormat class. <BR>
	 * Return the empty character when the argument is null. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 *   <DIR>
	 *   Date(Date type)*<BR>
	 *   </DIR>
	 * <BR>
	 * [Return data]<BR>
	 *   <DIR>
	 *   Date(String type:yyyyMMdd)<BR>
	 *   </DIR>
	 * 
	 * @param date Date type
	 * @return Format of Date passed to schedule parameter
	 */
	public static String toParamDate(Date date)
	{
		//#CM665163
		// Return the empty character when the argument is null. 
		if(StringUtil.isBlank(date))
		{
			return "";
		}

		//#CM665164
		// Convert from Date type to String type(yyyyMMdd). 
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(WMSConstants.PARAM_DATE_FORMAT);
		String changeDate = simpleDateFormat.format(date);
		
		return changeDate;
	}

	//#CM665165
	/**
	 * Convert schedule Parameter from the Date format displayed in the label into the Date format. <BR>
	 * <BR>
	 * Outline:By using the Formatter class, from Date format (ja:yyyy/MM/dd) displayed in the label,
	 * Convert schedule Parameter into Date form (yyyyMMdd). <BR>
	 * Return the empty character when the argument is an empty character.<BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 *   <DIR>
	 *   Date(ja:yyyy/MM/dd)*<BR>
	 * 	 Locale*<BR>
	 *   </DIR>
	 * <BR>
	 * [Return data]<BR>
	 *   <DIR>
	 *   Date(String type:yyyyMMdd)<BR>
	 *   </DIR>
	 * 
	 * @param dispDate Format of Date displayed in label
	 * @param locale <CODE>Locale</CODE> object where regional code is set
	 * @return Format of Date passed to schedule parameter
	 * 
	 */
	public static String toParamDate(String dispDate, Locale locale)
	{
		//#CM665166
		// Return the empty character when the argument is a empty character. 
		if(StringUtil.isBlank(dispDate))
		{
			return "";
		}
		
		//#CM665167
		// Converts into the Date type by using getDate(String argValue, int formatType, Locale locale) method of the Formatter class. 
		//#CM665168
		// Converts from yyyy/MM/dd (format decided by the locale) into Date. 
		
		Date date = null;		
		
		if( "ja".equals(locale.getLanguage()) || "en".equals(locale.getLanguage()) )
		{
			date = Formatter.getDate(dispDate, Constants.F_DATE, locale);
		}
		else
		{
			date = Formatter.getDate(dispDate, Constants.F_DATE, dummy_locale);
		}
		
			
		//#CM665169
		// Converts Date into yyyyMMdd format.
		String changeDate = toParamDate(date);

		return changeDate;
		
	}

	//#CM665170
	/**
	 * Convert it into Date type from String type(YYYYMMDDHHMISS). <BR>
     * Use this method to convert from character string type to Date type when you convert the last updated date and time maintained on the screen as the character. 
	 * @param str Set the character string of the YYYYMMDDHHMISS form. 
	 * @return Converted Date object 
	 */
	public static Date getTimeStampDate(String str)
	{
		//#CM665171
		// Return null when the argument is an empty character. 
		if(StringUtil.isBlank(str))
		{
			return null;
		}
	
		SimpleDateFormat fmt = new SimpleDateFormat ("yyyyMMddHHmmss");
		fmt.setLenient(true);
		try
		{
			return fmt.parse(str, new ParsePosition(0));
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	//#CM665172
	/**
	 * Convert into Date type from String type(YYYY/MM/DD) and String type(HH:MM:SS). <BR>
	 * Use this method to convert from character string type to Date type when you set Date and Time maintained in the list cell in Parameter. 
	 * @param str1 Set the character string of the YYYY/MM/DD format. 
	 * @param str2 Set the character string of the HH:MM:SS format. 
	 * @param locale <CODE>Locale</CODE> object where regional code is set
	 * @return Converted Date object 
	 */
	public static Date getTimeStampDate(String str1, String str2, Locale locale)
	{
		//#CM665173
		// Return null when the argument is an empty character. 
		if(StringUtil.isBlank(str1))
		{
			return null;
		}

		if(StringUtil.isBlank(str2))
		{
			str2 = "00:00:00";
		}

		
		//String date = toParamDate(str1, locale);
		//String time = toParamTime(str2, locale);
		
		String date = null;
		String time = null;
		
		if( "ja".equals(locale.getLanguage()) || "en".equals(locale.getLanguage()) )
		{
			date = toParamDate(str1, locale);
			time = toParamTime(str2, locale);
		}
		else
		{
			date = toParamDate(str1, dummy_locale);
			time = toParamTime(str2, dummy_locale);
		}

		
		String str = date + time;

		SimpleDateFormat fmt = new SimpleDateFormat ("yyyyMMddHHmmss");
		fmt.setLenient(true);
		try
		{
			return fmt.parse(str, new ParsePosition(0));
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	//#CM665174
	/**
	 * Convert it into String type(YYYYMMDDHHMMSS) from Date type. <BR>
     * Use this method from Date type for character string type to convert it when you maintain last updated date and time on the screen. 
	 * @param date Date object where Date to be converted is set
	 * @return String object converted into character string in the form of YYYYMMDDHHMISS 
	 */
	public static String getTimeStampString(Date date)
	{
		if (date == null)
		{
			return "";
		}
		else
		{	
			SimpleDateFormat sformatter = new SimpleDateFormat ("yyyyMMddHHmmss");
			return sformatter.format(date);
		}
	}

	//#CM665175
	/**
	 * Convert schedule Parameter from Date type into the Time form. <BR>
	 * <BR>
	 * Outline:Convert schedule Parameter into Time form (HHmmss) by using the SimpleDateFormat class. <BR>
	 * Return the empty character when the argument is null. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 *   <DIR>
	 *   Time(Date type)*<BR>
	 *   </DIR>
	 * <BR>
	 * [Return data]<BR>
	 *   <DIR>
	 *   Time(String type:HHmmss)<BR>
	 *   </DIR>
	 * 
	 * @param time Date type
	 * @return Format of Date passed to schedule parameter
	 */
	public static String toParamTime(Date time)
	{
		//#CM665176
		// Return the empty character when the argument is null. 
		if(StringUtil.isBlank(time))
		{
			return "";
		}

		//#CM665177
		// Converts from the Date type to String type (HHmmss). 
		SimpleDateFormat SDFormat = new SimpleDateFormat("HHmmss");
		String changeTime = SDFormat.format(time);
		
		return changeTime;
	}

	//#CM665178
	/**
	 * Convert schedule Parameter from the time displayed to the label form into the Date form. <BR>
	 * <BR>
	 * Outline:From Date form (ja:HH:mm:ss) displayed to the label by using the Formatter class
	 * Convert schedule Parameter into Date form (HHmmss). <BR>
	 * Return the empty character when the argument is an empty character.<BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 *   <DIR>
	 *   Date(ja:HH:mm:ss)*<BR>
	 * 	 Locale*<BR>
	 *   </DIR>
	 * <BR>
	 * [Return data]<BR>
	 *   <DIR>
	 *   Date(String type:HHmmss)<BR>
	 *   </DIR>
	 * 
	 * @param dispTime Time of form displayed in label
	 * @param locale <CODE>Locale</CODE> object where regional code is set
	 * @return Format of Date passed to schedule parameter
	 * 
	 */
	public static String toParamTime(String dispTime, Locale locale)
	{
		//#CM665179
		// Return the empty character when the argument is a empty character. 
		if(StringUtil.isBlank(dispTime))
		{
			return "";
		}
		
		//#CM665180
		// Converts into the Date type by using getDate(String argValue, int formatType, Locale locale) method of the Formatter class. 
		//#CM665181
		// Converts from yyyy/MM/dd (format decided by the locale) into Date. 
		
		
		//Date time = Formatter.getDate(dispTime, Constants.F_TIME, locale);
		
		Date time = null;
		
		if( "ja".equals(locale.getLanguage()) || "en".equals(locale.getLanguage()) )
		{
			Formatter.getDate(dispTime, Constants.F_TIME, locale);
		}
		else
		{
			Formatter.getDate(dispTime, Constants.F_TIME, dummy_locale);
		}

		
		//#CM665182
		// Converts Date into yyyyMMdd format.
		String changeDate = toParamTime(time);

		return changeDate;
	}
	
	//#CM665183
	/**
	 * Convert it into the Location No format displayed from the Location No format of the schedule parameter to the label. <BR>
	 * <BR>
	 * Outline:When System identification key is AS/RS defined in SystemDefine
	 * Edit the location formatting and return it. Receive it as an argument, except for AS/RS. 
	 * Return the taken location as it is. 
	 * <BR>
	 * @param locationNo Location No.
	 * @param systemDiskKey System identification key
	 * @return In case of AS/RS, format of Location No is 9-99-999-999. Otherwise, 999999999
	 */
	public static String toDispLocation(String locationNo, int systemDiskKey) 
	{
	    if (systemDiskKey == SystemDefine.SYSTEM_DISC_KEY_ASRS && locationNo.length() == 12)
	    {
		  return  DisplayText.formatLocation(locationNo);
	    }
	    else
	    {
	        return locationNo;
	    }
	}
	
	//#CM665184
	/**
	 * Convert it into the Location No format displayed from the Location No format of the schedule parameter to the label. <BR>
	 * <BR>
	 * The outline: In case of AreaNo of AS/RS defined in SystemDefine
	 * Edit the location formatting and return it. Receive it as an argument, except for AreaNo of AS/RS. 
	 * Return the taken location as it is. 
	 * <BR>
	 * @param locationNo Location No.
	 * @param areaNo Area No. of Stock
	 * @param asAreaNo Area No. of AS/RS
	 * @return In case of AS/RS, the format is 9-99-999-999. Otherwise, the format is 999999999
	 */
	public static String toDispLocation(String locationNo,String areaNo ,String[] asAreaNo) 
	{
		if (asAreaNo != null)
		{
			for (int i = 0;i < asAreaNo.length;i++)
			{
				if (asAreaNo[i].equals(areaNo))
				{
					return DisplayText.formatLocation(locationNo);
				}
			}
		}
	    return locationNo;
	}
	
}
