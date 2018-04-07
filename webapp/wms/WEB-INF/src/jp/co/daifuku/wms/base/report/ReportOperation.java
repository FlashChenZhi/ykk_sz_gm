// $Id: ReportOperation.java,v 1.4 2006/12/13 08:52:30 suresh Exp $
package jp.co.daifuku.wms.base.report ;

//#CM670836
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.fit.UCXSingle.UCXSingle;

//#CM670837
/**
 * Provide for the print operation of the report. <BR>
 * The method for an automatic print which uses UniversalConnect is mainly loaded.<BR>
 * The manual report print uses each list print class in <CODE>report.servlet</CODE> package.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>K.Nishiura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 08:52:30 $
 * @author  $Author: suresh $
 */
public class ReportOperation
{
	//#CM670838
	// Class fields --------------------------------------------------
	//#CM670839
	/**
	 * File path of Default (Data file)
	 */
	public static final String DEFAULT_FPATH = WmsParam.DATA_FILE_PATH;

	//#CM670840
	/**
	 * Path of Backup folder of default
	 */
	public static final String BACKUP_FPATH = WmsParam.BACKUP_DATA_FILE_PATH;

	//#CM670841
	/**
	 * File path for automatic print of default (Data file)
	 */
	public static final String AUTOPRINT_FILE_PATH = WmsParam.AUTOPRINT_FILE_PATH;

	//#CM670842
	/**
	 * Resource in which text for display is defined
	 */
	public static  String wReportRes = "ReportDef" ;

	//#CM670843
	/** Field which shows print mode */

	public static final String PRINT_MODE   = "1" ;

	//#CM670844
	/** Field which shows print mode */

	public static final String PREVIEW_MODE = "2" ;

	//#CM670845
	/** Field which shows print mode */

	public static final String NODATA_MODE  = "NODATA" ;

	//#CM670846
	/** Field which shows print mode */

	public static final String NOFILE_MODE  = "NOFILE" ;

	//#CM670847
	/**
	 * Print report for automatic operation
	 */
	public static final int AUTO_REPORT = 1 ;

	//#CM670848
	/**
	 * Print report for manual operation
	 */
	public static final int MANUAL_REPORT = 2 ;

	//#CM670849
	/**
	 * Data file setting information file for UC/X
	 */
	public static final String INI_FILE = "SCHEMA.INI";

	//#CM670850
	// Class variables -----------------------------------------------
	//#CM670851
	/**
	 * Report parameter
	 */
	public String wParameter;

	//#CM670852
	/**
	 * Report data
	 */
	public String wDetails;

	//#CM670853
	/**
	 * Report File Name
	 */
	public String wFileName;

	//#CM670854
	/**
	 * Report List type
	 */
	public String wListType;

	//#CM670855
	// Class method --------------------------------------------------
	//#CM670856
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2006/12/13 08:52:30 $") ;
	}

	//#CM670857
	/**
	 * Format the character string. <BR>
	 * Set " at both ends of the character string when outputting
	 * it to the text file. <BR>
	 * However, return the character string with 0 bytes when the given character string is a null character string and 0 bytes.
	 * @param  fmt Specify the format of target character string.
	 * @return Return the formatted character string.
	 */
	public static String format(String fmt)
	{
		return (SimpleFormat.format(fmt));
	}

	//#CM670858
	// Constructors --------------------------------------------------
	//#CM670859
	/**
	 * <code>ReportOperation</code> Object is constructed. <BR>
	 * Type of writing in the CSV file is by one line.
	 * @param param
	 * @param file
	 * @param type
	 */
	public ReportOperation( String param,
							String file,
							String type
	)
	{
		this(param, null, file, type, null) ;
	}

	//#CM670860
	/**
	 * <code>ReportOperation</code> Object is constructed. <BR>
	 * As for the locale, the default locale is used.
	 * @param param
	 * @param detail
	 * @param file
	 * @param type
	 */
	public ReportOperation( String param,
							String detail,
							String file,
							String type
	)
	{
		this(param, detail, file, type, null) ;
	}

	//#CM670861
	/**
	 * <code>ReportOperation</code> Object is constructed.
	 * @param param
	 * @param detail
	 * @param file
	 * @param type
	 * @param request HttpServletRequest
	 */
	public ReportOperation( String param,
							String detail,
							String file,
							String type,
							HttpServletRequest request
	)
	{
		wParameter = param ;
		wDetails   = detail ;
		wFileName  = file ;
		wListType  = type ;

	}

	//#CM670862
	// Public methods ------------------------------------------------

	//#CM670863
	/**
	 * Delete the report text file in the history according to the parameter.
	 * @param delDate Deleted date
	 */
	public static void delHistory(Date delDate)
	{
		String fpath = WmsParam.BACKUP_DATA_FILE_PATH;

		//#CM670864
		// Print data making folder
		File datapathName = new File(DEFAULT_FPATH);
		File[] datafnames = datapathName.listFiles();
		if (datafnames != null)
		{
			for (int i = 0; i < datafnames.length; i++)
			{
				String fname = datafnames[i].getName().toUpperCase();
				//#CM670865
				// Do not delete schema.ini.
				// Moreover, only the file is deleted.
				if (!fname.equals(INI_FILE) && datafnames[i].isFile())
				{
					datafnames[i].delete();
				}
			}
		}

		File pathName = new File(fpath);
		String[] fnames = pathName.list();
		if (fnames != null)
		{
			for (int i=0; i < fnames.length;i++)
			{
				File tf = new File(pathName.getPath(),fnames[i]);

				if (tf.lastModified() < delDate.getTime())
				{
					if (!tf.delete())
					{
						for (int retry=0 ; retry < 100; retry ++)
						{
							if (tf.delete())
							{
								break ;
							}
							else
							{
								for (int l = 0; l < 1000000; l++)
									for (int m = 0; m < 5; m++)
										;
							}
						}
					}
				}
			}
		}
	}

	//#CM670866
	/**
	 * Acquire the content of the parameter from the key.
	 * @param keyname  Key to acquired parameter
	 * @param lang   Specify the language specification "Ja", "En", etc.
	 * @return       Expression of character string of parameter
	 */
	public static String getText(String keyname, String lang)
	{
		ResourceBundle rb     = null ;
		Locale         locale = null ;

		//#CM670867
		// Check on language
		if (lang != null && lang.equals(Locale.JAPANESE.getLanguage()))
		{
			locale = new Locale( Locale.JAPANESE.getLanguage(), Locale.JAPAN.getCountry() ) ;
			rb = ResourceBundle.getBundle(wReportRes, locale) ;
		}
		else if (lang != null && lang.equals(Locale.ENGLISH.getLanguage()))
		{
			locale = new Locale( Locale.ENGLISH.getLanguage(), Locale.US.getCountry() ) ;
			rb = ResourceBundle.getBundle(wReportRes, locale) ;
		}
		else
		{
			rb = ResourceBundle.getBundle(wReportRes, Locale.getDefault()) ;	// Default
		}

		return rb.getString(keyname) ;
	}

	//#CM670868
	/**
	 * Make the backup of Data file for the specified report for the backup folder. <BR>
	 * Former Data file is deleted.
	 * @param  filename File Name of file which makes backup.
	 * @throws ReadWriteException It is notified when abnormality occurs by the access to the file.
	 */
	public static void createBackupFile(String filename) throws ReadWriteException
	{
	    InputStream  from_stream = null;  // Input File Stream Object
	    OutputStream  to_stream = null;   // Output File Stream Object
	    int rcount;        // The size of the data which was able to be actually read is maintained.
		try
		{
		    //#CM670869
		    // Make the buffer.
		    byte buffer[] = new byte [16*1024];

			//#CM670870
			// File path of copy source
			File from_file = new File(DEFAULT_FPATH + filename) ;
			//#CM670871
			// Path of backup file
			String to_filename = BACKUP_FPATH + filename ;

		    //#CM670872
		    // Open the file of the copy source.
		    from_stream = new FileInputStream(from_file);

		    //#CM670873
		    // Open the file of the copy destination.
		    to_stream = new FileOutputStream(to_filename);

		    //#CM670874
		    // Copy it.
		    while ((rcount = from_stream.read(buffer)) >= 0)
				to_stream.write(buffer, 0, rcount);

			if (from_stream != null)
				from_stream.close();
	   		if (to_stream != null)
				to_stream.close();

			from_file.delete();
	    }
	    catch (Exception e)
	    {
			Object[] tObj = new Object[1] ;
			tObj[0] = e.getMessage();

			//#CM670875
			// It failed in making Print data.

			RmiMsgLogClient.write(new TraceHandler(6006027, e), "ReportOperation.java");
			throw (new ReadWriteException("6006027" + tObj[0])) ;
		}
	}

	//#CM670876
	/**
	 * Execute UCXSingle with specified Data file, and execute the print.
	 * @param settingName  JOBID set with UniversalConnect/X
	 * @param filename	   Data file of Comma Separated Value which makes spool file
	 * @return       Return the result of the UCXSingle execution. True in case of success
	 */
	public static boolean executeUCX( String settingName, String fileName )
	{
		//#CM670877
		// For error content output
		Object[] tObj = new Object[1] ;

		try
		{
			String host 	  = WmsParam.REPORT_HOST;
			int    port       = WmsParam.REPORT_PORT;	// Universal Connect / X Port No. of the Server
			String sourceName = ReportOperation.DEFAULT_FPATH + fileName ;	// Data file name


			UCXSingle ucs = new UCXSingle() ;

			ucs.setUniConXServer(host, port) ;		// Universal Connect / X Specify the Host name and IP address of the server.
			ucs.setSettingName(settingName) ;		// Specify the operation setting name.
			ucs.setSourceName(sourceName) ;			// Specify the data file name.
			ucs.setUndeleteSourceFile(true) ;		// Do not delete the data file when processing ends normally.
			ucs.setUndeleteSourceFileIfError(true);	// Do not delete the data file when processing ends normally.

			ucs.doTransaction();			// Execute processing according to the operation setting.

			//#CM670878
			//  Result of UCXSingle processing. The normal termination by 0.
			int res = ucs.getUCXSingleResult() ;
			if( res ==  0)
			{
				//#CM670879
				//  The processing result of the UniConX server.
				res = ucs.getUniConXResult() ;
				if( res ==  0)
				{
					return true;
				}
			}

			tObj[0] = "UCX ERROR CODE:" + Integer.toString(res) ;
			RmiMsgLogClient.write(6006027, "ReportOperation.java", tObj);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//#CM670880
			// It failed in making Print data.
			RmiMsgLogClient.write(new TraceHandler(6006027, e), "ReportOperation.java");
		}

		return false;
	}

	//#CM670881
	// Package methods -----------------------------------------------

	//#CM670882
	// Protected methods ---------------------------------------------

	//#CM670883
	// Private methods -----------------------------------------------
}

//#CM670884
//end of class
