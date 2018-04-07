// $Id: FileClear.java,v 1.2 2006/11/07 07:13:38 suresh Exp $
package jp.co.daifuku.wms.base.utility;

//#CM686963
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.report.ReportOperation;

//#CM686964
/**
 * This class is used for file delete, backup creation
 * used by day total processing
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/02/05</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 07:13:38 $
 * @author  $Author: suresh $
 */
public class FileClear
{
	//#CM686965
	// Class fields --------------------------------------------------
	//#CM686966
	/** log file path */

	private static String LOGFILE_PATH = WmsParam.LOGS_PATH + WmsParam.MESSAGELOG_FILE;

	//#CM686967
	// Class variables -----------------------------------------------

	//#CM686968
	// Public Class method -------------------------------------------
	//#CM686969
	/**
	 * Return the version of this class
	 * @return version and timestamp
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 07:13:38 $") ;
	}

	//#CM686970
	/**
	 * Clear log files maintained in the system after the hold period defined by the system
	 * @throws IOException if the file input/output fails
	 */
	public static void clearMessageLog() throws IOException
	{
		//#CM686971
		// log hold period
		String deltime = WmsParam.WMS_LOGFILE_KEEP_DAYS;

		File pathName = new File(LOGFILE_PATH) ;
		clearFile(pathName.getParent(), deltime) ;
	}

	//#CM686972
	/**
	 * Clear external data after the hold period defined by the system
	 * @throws IOException if the file input/output fails
	 */
	public static void clearOutData() throws IOException
	{
		//#CM686973
		// external data backup file path
		String filename = WmsParam.HISTRY_HOSTDATA_PATH;
		//#CM686974
		// external data hold period
		String deltime = WmsParam.HOSTDATA_KEEP_DAYS;

		clearFile(filename, deltime) ;
	}

	//#CM686975
	/**
	 * Clear print mage file after the hold period defined by the system
	 */
	public static void clearPrintHistory()
	{
		//#CM686976
		// backup file hold period
		String deltime = WmsParam.PRINTHISTORY_KEEP_DAYS;
		//#CM686977
		// fetch delete date and clear files
		ReportOperation.delHistory(getDate(deltime)) ;
	}

  //#CM686978
  /**
   * Clear tomcat log after the hold period defined by the system
	 * @throws IOException if the file input/output fails
   */
  public static void clearTomcatLog() throws IOException
  {
	  //#CM686979
	  // Tomcat log path
	  String filename = WmsParam.TOMCAT_LOGS_PATH;
	  //#CM686980
	  // Tomcat log hold period
	  String deltime = WmsParam.TOMCATLOGS_KEEP_DAYS;

	  clearFile( filename, deltime );
  }

  //#CM686981
  /**
   * Clear IIS (FTP) log after the hold period defined by the system
	 * @throws IOException if the file input/output fails
   */
  public static void clearIISLog() throws IOException
  {
	  //#CM686982
	  // IIS(FTP) log file path
	  String filename = WmsParam.IIS_LOGS_PATH;
	  //#CM686983
	  // IIS(FTP) log file hold period
	  String deltime = WmsParam.IIS_LOGFILE_KEEP_DAYS;

	  clearFile( filename, deltime );
  }

  //#CM686984
  /**
   * Clear other logs after the hold period defined by the system
	 * @throws IOException if the file input/output fails
   */
  public static void clearOtherLog() throws IOException
  {
	  //#CM686985
	  // log file path of others
	  String filename = WmsParam.ETC_LOGS_PATH;
	  //#CM686986
	  // hold period of other log files
	  String deltime = WmsParam.ETC_LOGFILE_KEEP_DAYS;

	  clearFile( filename, deltime );
  }

  //#CM686987
  /**
   * Clear FTP history file after the hold period defined by the system
	 * @throws IOException if the file input/output fails
   */
  public static void clearFTPBackupFile() throws IOException
  {
	  //#CM686988
	  // Ftp history file path
	  String filename = WmsParam.FTP_FILE_HISTORY_PATH;
	  //#CM686989
	  // Ftp history file hold period
	  String deltime = WmsParam.FTP_FILE_HISTORY_KEEP_DAYS;

	  clearFile( filename, deltime );
  }

	//#CM686990
	// Private Class method ------------------------------------------
	//#CM686991
	/**
	 * Delete the files with the last update date earlier than the delete date specified by deltime
	 * @param path path of file to delete
	 * @param deltime delete date
	 * @throws IOException if the file input/output fails
	 */
	private static void clearFile(String path, String deltime) throws IOException
	{
		//#CM686992
		// fetch deletion date
		Date ddate = getDate(deltime) ;

		File pathName = new File(path) ;
		//#CM686993
		// fetch delete target file path
		File backuppath = new File(pathName.getPath()) ;

		//#CM686994
		// fetch backup file name list
		String[] fnames = backuppath.list() ;

		if ( fnames != null )
		{
			for (int i = 0; i < fnames.length; i++)
			{
				File tf = new File(pathName.getPath(), fnames[i]) ;

				//#CM686995
				// compare with last update date time of backup file
				if (tf.lastModified() < ddate.getTime())
				{
					if (!tf.delete())
					{
						for (int retry = 0 ; retry < 100; retry++)
						{
							if (tf.delete())
							{
								break ;
							}
						}
					}
				}
			}
		}
	}

	//#CM686996
	/**
	 * Fetch the date subtracting keep time in the argument from current time
	 * @param keeptime keep time
	 * @return date calculated by subtracting keep time from current time
	 */
	private static Date getDate(String keeptime)
	{
		//#CM686997
		// fetch file deletion date
		Calendar curdate = Calendar.getInstance() ;
		int kday = Integer.parseInt("-" + keeptime) ;
		curdate.add(Calendar.DAY_OF_YEAR, kday + 1) ;
		curdate.set(Calendar.HOUR_OF_DAY, 0);
		curdate.set(Calendar.MINUTE, 0);
		curdate.set(Calendar.SECOND, 0);

		return (curdate.getTime()) ;
	}

	//#CM686998
	// Constructors --------------------------------------------------

	//#CM686999
	// Public methods ------------------------------------------------

	//#CM687000
	// Package methods -----------------------------------------------

	//#CM687001
	// Protected methods ---------------------------------------------

	//#CM687002
	// Private methods -----------------------------------------------

	//#CM687003
	// Debug methods -------------------------------------------------

}
//#CM687004
//end of class
