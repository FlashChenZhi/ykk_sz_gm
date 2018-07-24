// $Id: LogHandler.java,v 1.3 2006/10/30 06:29:56 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.common ;

//#CM46659
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.common.MessageResource;

//#CM46660
/**<en>
 * This class is used to write in/read the message log file.
 * Log file is formatted as below. Tabs are used as delimiters; all tabs and breaks
 * in the message paramters can be replaced with space.
 * <pre>
 *	Date Message no. Message parameter [...]
 * </pre>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/10/30 06:29:56 $
 * @author  $Author: suresh $
 </en>*/
public class LogHandler extends Object
{
	//#CM46661
	// Class fields --------------------------------------------------
	//#CM46662
	/**<en>
	 * Delimiter of the log file
	 </en>*/
	protected static final String LOG_DELIM = "\t" ;
	//#CM46663
	/**<en>
	 * Format type of log date
	 </en>*/
	protected static final String DATE_FORMAT = "yyyy MM dd HH mm ss" ;
	//#CM46664
	/**<en>
	 * Encoding of the log
	 </en>*/
	protected static final String LOG_ENCODE = "UTF-8" ;
	//#CM46665
	/**<en>
	 * Formatter which formats the dates
	 </en>*/
	protected static SimpleDateFormat MSG_DATE_FORMATTER = new SimpleDateFormat (DATE_FORMAT);

	//#CM46666
	// Class variables -----------------------------------------------
	//#CM46667
	/**<en>
	 * Variables which preserve locale of messages.
	 </en>*/
	protected Locale wLocale ;
	//#CM46668
	/**<en>
	 *Variables which preserve the name of message log file
	 </en>*/
	protected String wLogfileName ;
	//#CM46669
	/**<en>
	 * Variables which preserve the message resource
	 </en>*/
	protected MessageResource wMsgRes ;
	//#CM46670
	/**<en>
	 * the <code>Reader</code> in order to read the message log file
	 </en>*/
	protected LineNumberReader wLNReader ;

	//#CM46671
	// Class method --------------------------------------------------
	//#CM46672
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/10/30 06:29:56 $") ;
	}

	//#CM46673
	// Constructors --------------------------------------------------
	//#CM46674
	/**<en>
	 * Initialize so that message will be written/ read by default locale.
	 * @param logfile :target log file
	 </en>*/
	public LogHandler(String logfile)
	{
		this(logfile, Locale.getDefault()) ;
	}

	//#CM46675
	/**<en>
	 * Initialize so that message will be read by specified locale
	 * @param locale  :locale to specify
	 * @param logfile :name of the target log file
	 </en>*/
	public LogHandler(String logfile, Locale locale)
	{
		wLocale = locale ;
		wLogfileName = logfile ;
		wMsgRes = new MessageResource(locale) ;
	}

	//#CM46676
	// Public methods ------------------------------------------------
	//#CM46677
	/**<en>
	 * Retrieve the size of the file currently in writing process.
	 * @return :file size
	 </en>*/
	public long getFileSize()
	{
		File file = new File(wLogfileName);
		return file.length();
	}

	//#CM46678
	/**<en>
	 * Write the message. The message will be formatted according to the resource and parameter
	 * array, then will be written in the file.
	 * If the number of parameters does not match then, either the parameter data will be thrown,
	 * or the log will be written before the embedding complete.
	 * The parameters will be stored in the log file as a string delimited by delimiters or characters.
	 * @param msgnum  :message no.
	 * @param fac     :facility code
	 * @param ci      :Class insformation
	 * @param param   :parameter array
	 </en>*/
	public void write( String settingname, String tablename, String msg) throws IOException
	{
		//#CM46679
		//<en> Open streams</en>
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(wLogfileName, true), LOG_ENCODE) ;

		//#CM46680
		//<en> Create msg parameter string</en>
		StringBuffer msgbuf = new StringBuffer(256) ;

		//#CM46681
		//<en> Date first</en>
		Date ndate = new Date() ;
		msgbuf.append(MSG_DATE_FORMATTER.format(ndate)) ;

		//#CM46682
		//<en> Setting name.</en>
		msgbuf.append(LOG_DELIM + settingname) ;

		//#CM46683
		//<en> Table name.</en>
		msgbuf.append(LOG_DELIM + tablename) ;

		//#CM46684
		//<en>Get formatted message.</en>
		String msginfo = MessageResource.getMessage(msg);
		//#CM46685
		//<en> message number</en>
		msgbuf.append(LOG_DELIM + msginfo) ;

		//#CM46686
		//<en> write parameter string to logfile</en>
		String pmsg = msgbuf.toString() + "\n" ;

		synchronized (osw)
		{
			osw.write(pmsg, 0, pmsg.length()) ;
			//#CM46687
			//<en> finish : close streams</en>
			osw.flush() ;
		}
		osw.close() ;
		osw = null ;
	}


	//#CM46688
	// Public accsesser methods --------------------------------------
	//#CM46689
	/**<en>
	 * Retrieve the name of log file which is used at moment.
	 * @return    :name of the log file
	 </en>*/
	public String getLogfile()
	{
		return (wLogfileName) ;
	}
	//#CM46690
	/**<en>
	 * Retrieve the locale which is used at moment.
	 * @return    <code>Locale</code>
	 </en>*/
	public Locale getLocale()
	{
		return (wLocale) ;
	}
	//#CM46691
	/**<en>
	 * Retrieve the message resource which is used at moment.
	 * @return    <code>MessageResource</code>
	 </en>*/
	public MessageResource getMessageResource()
	{
		return (wMsgRes) ;
	}

	//#CM46692
	// Package methods -----------------------------------------------

	//#CM46693
	// Protected methods ---------------------------------------------

	//#CM46694
	// Private methods -----------------------------------------------

	//#CM46695
	// Debug methods -----------------------------------------------
	//#CM46696
	/*<en>
	public static void main(String[] argv)
	{
		try
		{
			MessageLogHandler mlog = new MessageLogHandler("test.log") ;
			Object[] tObj = new Object[3] ;
			for (int i=0; i < 10; i++)
			{
				tObj[0] = new Integer(1) ;
				tObj[1] = new Integer(11000)  ;
				mlog.write(99999, tObj) ;
			}

			mlog.readOpen() ;
			LogMessage msg ;
			while ((msg = mlog.readNext()) != null)
			{
				System.out.print(msg.getDate()) ;
				System.out.print(":") ;
				System.out.print(msg.getMessageNumber()) ;
				System.out.print(":") ;
			}
			mlog.readClose() ;
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}
	}
	</en>*/
/*
	public static void main(String[] argv)
	{
		try
		{
			MessageLogHandler mlog = new MessageLogHandler("test.log") ;
			Object[] tObj = new Object[3] ;
			for (int i=0; i < 10; i++)
			{
				tObj[0] = new Integer(1) ;
				tObj[1] = new Integer(11000)  ;
				mlog.write(99999, tObj) ;
			}

			mlog.readOpen() ;
			LogMessage msg ;
			while ((msg = mlog.readNext()) != null)
			{
				System.out.print(msg.getDate()) ;
				System.out.print(":") ;
				System.out.print(msg.getMessageNumber()) ;
				System.out.print(":") ;
			}
			mlog.readClose() ;
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}
	}
	</en>*/

}
//#CM46697
//end of class

