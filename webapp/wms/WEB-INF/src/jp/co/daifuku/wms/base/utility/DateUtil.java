// $Id: DateUtil.java,v 1.2 2006/11/07 07:13:38 suresh Exp $
package jp.co.daifuku.wms.base.utility ;

//#CM686882
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;



//#CM686883
/**
 * Search method for general purpose use by date, time data<BR>
 * This class eliminates similar methods in different classes and move them to a common place that is easier for any<BR>
 * future modifications. If the methods added to this class keep on increasing, there is a plan to move them to a <BR>
 * separate related class. Please ensure that new methods won't duplicate with existing ones.<BR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/06/27</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 07:13:38 $
 * @author  $Author: suresh $
 */
public class DateUtil extends Object
{
	//#CM686884
	// Class fields --------------------------------------------------

	//#CM686885
	// Class private fields ------------------------------------------
	//#CM686886
	/**
	 * Start date/time format error
	 */
	public static final int START_FORMAT_ERROR = -1 ;
	//#CM686887
	/**
	 * End date/time format error
	 */
	public static final int END_FORMAT_ERROR = -2 ;
	//#CM686888
	/**
	 * Start date is later than end date
	 */
	public static final int DATE_INPUT_ERROR = 0 ;
	//#CM686889
	/**
	 * Start date input is available
	 */
	public static final int INPUT_START_DATE = 1 ;
	//#CM686890
	/**
	 * End date input is available
	 */
	public static final int INPUT_END_DATE = 2 ;
	//#CM686891
	/**
	 * Both start date and end date input are available
	 */
	public static final int INPUT_START_END = 3 ;
	
	//#CM686892
	/**
	 * Both start date and end date input are not available
	 */
	public static final int NULL_START_END = 4 ;
	
	//#CM686893
	// Class variables -----------------------------------------------
	
	//#CM686894
	// Class method --------------------------------------------------
	//#CM686895
	/**
	 * Return the version of this class
	 * @return version and timestamp
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 07:13:38 $") ;
	}
	
	//#CM686896
	/**
	 *  getSystemDateTime method
	 *  fetch system timestamp
	 *  @return String		system timestamp yyyyMMddHHmmss
	 */
    public static String getSystemDateTime() {

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);

		return dateString;
    }

    //#CM686897
    /**
	 *  getSystemDate method
	 *  fetch system date
	 *  @return String		system date YYYYMMDD
	 */
	public static String getSystemDate()
	{     
		//#CM686898
		// fetch system date
		Calendar calendar = new GregorianCalendar();
		Date trialtime = new Date();
		calendar.setTime(trialtime); 
		
		//#CM686899
		// fetch year
		int intTodayY = calendar.get(Calendar.YEAR);
		//#CM686900
		// fetch month
		int intTodayM = calendar.get(Calendar.MONTH) + 1;
		//#CM686901
		// fetch day
		int intTodayD = calendar.get(Calendar.DAY_OF_MONTH);
		
		//#CM686902
		// convert year to string
		String strSdayY = Integer.toString(intTodayY);
		
		//#CM686903
		// convert month to string
		String strSdayM;
		if (intTodayM < 10)
		{
			strSdayM = "0" + Integer.toString(intTodayM);
		}
		else   
		{
			strSdayM = Integer.toString(intTodayM);
		}
		
		//#CM686904
		// convert day to string
		String strSdayD; 
		if (intTodayD < 10)
		{
			strSdayD = "0" + Integer.toString(intTodayD);
		}
		else   
		{
			strSdayD = Integer.toString(intTodayD);
		}
		
		//#CM686905
		// return system date
		return strSdayY + strSdayM + strSdayD;
	}
	
	//#CM686906
	/**
	 *  getDeleteDate method
	 *  fetch result elimination date
	 *  @return String		system date YYYYMMDD
	 */
	public static String getDeleteDate(boolean type)
	{     
		//#CM686907
		// fetch system date
		Calendar calendar = new GregorianCalendar() ;
		Date trialtime = new Date() ;
		calendar.setTime(trialtime) ;
		
		//#CM686908
		// variable to store result hold period
		int resultHoldPeriod = 0 ;
		
		//#CM686909
		// while fetching result hold period from database
		if(type == true)
		{
			//#CM686910
			// while fetching result hold period from database
			try
			{
				//#CM686911
				// fetch db connection using WmsParam class
				Connection conn = WmsParam.getConnection() ;
		
				//#CM686912
				// fetch result hold period from WARENAVI_SYSTEM table
				WareNaviSystemHandler wareNaviSystemHandler = new WareNaviSystemHandler( conn ) ;
				WareNaviSystemSearchKey wareNaviSystemSearchKey = new WareNaviSystemSearchKey() ;
			
				WareNaviSystem[] wareNaviSystem = ( WareNaviSystem[] )wareNaviSystemHandler.find( wareNaviSystemSearchKey ) ;
			
				//#CM686913
				// fetch result hold period
				resultHoldPeriod = wareNaviSystem[0].getResultHoldPeriod() ;
			}
			catch(SQLException e)
			{
				e.printStackTrace() ;
			}
			catch(Exception e)
			{
				e.printStackTrace() ;
			}
		}
		//#CM686914
		// while fetching result hold period from WmsParam file
		else
		{
			//#CM686915
			// while fetching result hold period from WmsParam file
			resultHoldPeriod = Integer.parseInt(WmsParam.WMS_LOGFILE_KEEP_DAYS);
		}
		
		//#CM686916
		// calculate result hold date
		calendar.add(Calendar.DATE, 0 - resultHoldPeriod) ;
		
		//#CM686917
		// fetch year
		int intTodayY = calendar.get(Calendar.YEAR);
		//#CM686918
		// fetch month
		int intTodayM = calendar.get(Calendar.MONTH) + 1;
		//#CM686919
		// fetch day
		int intTodayD = calendar.get(Calendar.DAY_OF_MONTH);
		
		//#CM686920
		// convert year to string
		String strSdayY = Integer.toString(intTodayY);
		
		//#CM686921
		// convert month to string
		String strSdayM;
		if (intTodayM < 10)
		{
			strSdayM = "0" + Integer.toString(intTodayM);
		}
		else   
		{
			strSdayM = Integer.toString(intTodayM);
		}
		
		//#CM686922
		// convert day to string
		String strSdayD; 
		if (intTodayD < 10)
		{
			strSdayD = "0" + Integer.toString(intTodayD);
		}
		else   
		{
			strSdayD = Integer.toString(intTodayD);
		}
		
		//#CM686923
		// return system date
		return strSdayY + strSdayM + strSdayD;
	}
	
	//#CM686924
	// Constructors --------------------------------------------------
	//#CM686925
	/**
	 * create instance
	 */
	public DateUtil()
	{
		
	}

	//#CM686926
	// Public methods ------------------------------------------------
	//#CM686927
	/**
	 * check date
	 * Check enable or not with the string (YYYYMMDD) as date<BR>
	 * check date
	 * Create DfkDateContainer from java.util.Date
	 * @param  myDate  input character string YYYYMMDD
	 * @return boolean  enable true disable false 
	 */
	public boolean DateChk(String myDate)
	{
		//#CM686928
		// error if the input string is not 8 digits
		if (myDate.length() != 8) return false;

		for (int i=0;i < myDate.length();i++) {
			char charData = myDate.charAt(i);   
			if ((charData < '0') || (charData > '9')) {
				return false;
			}
		}
                          
		int intYear;
		int intMonth;
		int intDay; 

		if (myDate.length() > 3)
		{
			intYear = java.lang.Integer.parseInt(myDate.substring(0,4));
		}
		else
		{
			intYear = 0;
		}
		if (myDate.length() > 5)
		{
			intMonth = java.lang.Integer.parseInt(myDate.substring(4,6));
		}
		else
		{
			intMonth = 0;
		}
		if (myDate.length() == 8)
		{
			intDay = java.lang.Integer.parseInt(myDate.substring(6,8));
		}
		else
		{
			intDay = 0;
		}

		Calendar cal = new GregorianCalendar();
		cal.setLenient( false );
		cal.set( intYear , intMonth-1 , intDay );

		try
		{
			java.util.Date ud = cal.getTime();
		}
		catch(IllegalArgumentException iae)
		{
			return false;
		}

		return true;
	}

	//#CM686929
	/**
	 * Check time. Used in trace inquiry screen during date/time search<BR>
	 * Return true if both date and time is empty
	 * @param  String search date
	 * @param  String search time
	 * @return  boolean true if date/time exist. else false
	 */
	public boolean checkDate( String date, String time )
	{
		
		//#CM686930
		// year, month, day is not blank
		if ( DateChk( date ) )
		{
			//#CM686931
			// hour, minute, second is not blank
			if ( time.length() != 0 )
			{
				//#CM686932
				// if all input
				if ( time.length() == 6 )
				{
					//#CM686933
					// 1111/11/11 11:11:11
					return true;
				}
			}
			//#CM686934
			//hour, minute, second is blank
			else
			{
				if ( time.length() == 0 )
				{
					//#CM686935
					// 1111/11/11 __:__:__
					return true;
				}
			}
		}
		//#CM686936
		// year, month, day is blank
		else
		{
			//#CM686937
			// hour, minute, second is not blank
			if ( time.length() != 0 )
			{
				//#CM686938
				// error if year,month,day is empty and only hour,minute,second is input
				return false;
			}
			else
			{
				//#CM686939
				// both are blank
				//#CM686940
				// ____/__/__ __:__:__
				return true;
			}
		}
		return false;
	}
	
	//#CM686941
	/**
	 * Create java.utilDate from (YYYYMMDD) string and (HHMISS) string<BR>
	 * Used in log trance inquiry screen for date/time check<BR>
	 * if dateFlag parameter is true :
	 *   return as it is, if both date and time is input<BR>
	 *   if only date is input then return date+00:00:00<BR>
	 *   return null if both date and time are not input<BR>
	 * if parameter flag is false :
	 *   if both date and time are input, return current date/time + input time + 1 sec<BR>
	 *   if only date is input, return input date + 1 day + 00:00:00<BR>
	 *   null if both date and time are left blank
	 * @param  date date
	 * @param  time time
	 * @param  dateFlag true:start date/time false:end date/time
	 * @return  java.util.Date
	 */
	public Date getDate( String date, String time, boolean dateFlag )
	{
		java.util.Date wDate = null;
		
		//#CM686942
		// current date/time
		Calendar current = new GregorianCalendar();
		int hour = current.get( Calendar.HOUR_OF_DAY );
		int minute = current.get( Calendar.MINUTE );
		int second = current.get( Calendar.SECOND );
		
		//#CM686943
		// year, month, day is not empty
		if ( date.length() != 0 )
		{
			//#CM686944
			// hour, minute, second is not blank
			if ( time.length() != 0 )
			{
				//#CM686945
				// all input
				if ( date.length() == 8 && time.length() == 6 )
				{
					GregorianCalendar wCalendar = new GregorianCalendar(
					                                    Integer.parseInt( date.substring( 0, 4 ) ),
														Integer.parseInt( date.substring( 4, 6 ) ) - 1,
														Integer.parseInt( date.substring( 6, 8 ) ),
														Integer.parseInt( time.substring( 0, 2 ) ),
														Integer.parseInt( time.substring( 2, 4 ) ),
														Integer.parseInt( time.substring( 4 , 6 ) ) );
					if ( !dateFlag )
					{
						wCalendar.add( Calendar.SECOND, 1 );									
					}
					wDate = wCalendar.getTime();
				}
			}
			//#CM686946
			// hour, minute, second is blank
			else
			{
				if ( date.length() == 8 )
				{
					GregorianCalendar wCalendar = new GregorianCalendar(
					                                    Integer.parseInt( date.substring( 0, 4 ) ),
														Integer.parseInt( date.substring( 4, 6 ) ) - 1,
														Integer.parseInt( date.substring( 6, 8 ) ),
														0, 0, 0 );
					if ( !dateFlag )
					{
						wCalendar.add( Calendar.DATE, 1 );									
					}
					wDate = wCalendar.getTime();
				}
			}
		}
		//#CM686947
		// year, month, day is blank
		else
		{
			//#CM686948
			// hour, minute, second is not blank
			if ( time.length() != 0 )
			{
				System.out.println( "Date Format Error" );
			}
		}
		return wDate;
	}

	//#CM686949
	/**
	 * Return date type of date and time passed in the argument<BR>
	 * if date is null:return current date + time<BR>
	 * if time is null :return date + current time<BR>
	 * if both are null:return null<BR>
	 * 
	 * used when date and time are input separately
	 * @param  date
	 * @param time
	 * @return date format
	 */
	public Date getDate(Date date, Date time)
	{
		try
		{
			String dateFormat = "yyyyMMdd";
			String timeFormat = "HHmmss";
			DateFormat dfDate = new SimpleDateFormat(dateFormat);
			DateFormat dfTime = new SimpleDateFormat(timeFormat);
			
			Date now = new Date();
			
			String dateString = "";
			String timeString = "";
			
			if(date == null && time != null)
			{
				dateString = dfDate.format(now);
				timeString = dfTime.format(time);
			}
			else if(date != null && time == null)
			{
				dateString = dfDate.format(date);
				timeString = dfTime.format(now);
			}
			else if(date != null && time != null)
			{
				dateString = dfDate.format(date);
				timeString = dfTime.format(time);
			}
			else
			{
				return null;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat();

			sdf.applyPattern( dateFormat + timeFormat );

			return sdf.parse( dateString + timeString );
		}
		catch(Exception e)
		{
			return null;
		}
	}

	//#CM686950
	/**
	 * Check time from start date/time and end date/time
	 * Used in inquiry trace log search
	 * @param  startDate start date
	 * @param  startTime start time
	 * @param  endDate end date
	 * @param  endTime end time
	 * @return   -1 start date format error<BR>
	 *           -2 end date format error<BR>
	 *            0 Start date is later than end date<BR>
	 *            1 Start date input is available<BR>
	 *            2 End date input is available<BR>
	 *            3 Both start date and end date are available<BR>
	 *            4 Both start date and end date input are not available
	 */
	public int checkDateTime( String startDate, String startTime, String endDate, String endTime )
	{
		
		//#CM686951
		// start check date
		if ( !checkDate( startDate, startTime ) )
		{
			return START_FORMAT_ERROR;
		}
		//#CM686952
		// end check date
		if ( !checkDate( endDate, endTime ) )
		{
			return END_FORMAT_ERROR;
		}
		
		//#CM686953
		// convert start date and end date to date format
		Date wStrDateTime = getDate( startDate, startTime, true );
		Date wEndDateTime = getDate( endDate, endTime, false );
		
		//#CM686954
		// return true if either start date or end date is not input
		if ( wStrDateTime == null )
		{
			if ( wEndDateTime == null )
			{
				return NULL_START_END;
			}
			return INPUT_END_DATE;
		}
		else if ( wEndDateTime == null )
		{
			return INPUT_START_DATE ;
		}
		
		//#CM686955
		// compare start date and end date
		//#CM686956
		// if end date is lesser than start date
		if ( wStrDateTime.compareTo( wEndDateTime ) >= 0 )
		{
			return DATE_INPUT_ERROR ;
		}
		
		//#CM686957
		// if date is normal
		return INPUT_START_END ;
	}

	//#CM686958
	// Package methods -----------------------------------------------

	//#CM686959
	// Protected methods ---------------------------------------------

	//#CM686960
	// Private methods -----------------------------------------------

	//#CM686961
	// Internal class ------------------------------------------------
}
//#CM686962
//end of class

