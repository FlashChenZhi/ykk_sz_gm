//#CM695300
//$Id: SystemWorkerResult.java,v 1.2 2006/10/18 08:15:40 suresh Exp $
package jp.co.daifuku.wms.base.system.entity;

//#CM695301
/*
 * Created on 2004/08/25
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.entity.WorkerResult;

//#CM695302
/**
 * 
 * Designer : suresh kayamboo<BR>
 * Maker 	: suresh kayamboo<BR> 
 *
 * This class controls the worker result info
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/25</TD><TD>suresh kayamboo</TD><TD></TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author suresh kayamboo
 * @version $Revision 1.2 2004/08/25
 */
public class SystemWorkerResult extends WorkerResult
{
	//#CM695303
	/**
	 * Work Detail
	 */
	public static final String ARRIVAL = "01" ;
	public static final String STORAGE = "02" ;
	public static final String RETRIEVAL = "03" ;
	public static final String SORTING = "04" ;
	public static final String SHIPPING = "05" ;
	public static final String MOV_RETRIEVAL = "11" ;
	public static final String MOV_STORAGE = "12" ;
	public static final String EX_STORAGE = "21" ;
	public static final String EX_RETRIEVAL = "22" ;
	public static final String MAINT_PLUS = "31" ;
	public static final String MAINT_MINUS = "32" ;
	public static final String INVENTORY = "40" ;
	public static final String INVENTORY_PLUS = "41" ;
	public static final String INVENTORY_MINUS = "42" ;
	
	//#CM695304
	/**
	 * Date
	 */
	private String wDays ;
	//#CM695305
	/**
	 * Hour
	 */
	private String wHrs ;
	//#CM695306
	/**
	 * Minutes
	 */
	private String wMin ;
	//#CM695307
	/**
	 * Seconds
	 */
	private String wSec ;
	//#CM695308
	/**
	 * Start Time
	 */
	private String startTime ;
	//#CM695309
	/**
	 * End Time
	 */
	private String endTime ;
	
	//#CM695310
	/**
	 * Initialize
	 */
	public SystemWorkerResult()
	{
		wDays = "";
		wHrs  = "";
		wMin  = "";
		wSec = "" ;		
		startTime = "" ;
		endTime = "" ;
	}
	
	
	//#CM695311
	/**
	 * Fetch data value
	 * @return Date
	 */
	public String getDays()
	{
		return wDays;
	}
	//#CM695312
	/**
	 * Set value to Date
	 * @param days Date
	 */
	public void setDays(String days)
	{
		wDays = days;
	}
	
	//#CM695313
	/**
	 * Fetch hour
	 * @return hour
	 */
	public String getHrs()
	{
		return wHrs;
	}
	//#CM695314
	/**
	 * Set value to hour
	 * @param hrs Hour
	 */
	public void setHrs(String hrs)
	{
		wHrs = hrs;
	}
	//#CM695315
	/**
	 * Fetch minute
	 * @return minute
	 */
	public String getMin()
	{
		return wMin;
	}
	//#CM695316
	/**
	 * Set value to minute
	 * @param min Minute
	 */
	public void setMin(String min)
	{
		wMin = min;
	}
	//#CM695317
	/**
	 * Fetch seconds
	 * @return seconds
	 */
	public String getSec()
	{
		return wSec;
	}
	//#CM695318
	/**
	 * Set value to second
	 * @param sec Second
	 */
	public void setSec(String sec)
	{
		wSec = sec;
	}
	
	//#CM695319
	/**
	 * Fetch End Time
	 * @return End Time 'hhmmss'
	 */
	public String getEndTime()
	{
		return endTime;
	}
	//#CM695320
	/**
	 * Set End Time
	 * @param endTime End Time 'hhmmss'
	 */
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
	//#CM695321
	/**
	 * Fetch Start Time
	 * @return Start Time'hhmmss'
	 */
	public String getStartTime()
	{
		return startTime;
	}
	//#CM695322
	/**
	 * Set Start Time
	 * @param startTime Start Time'hhmmss'
	 */
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}
}
