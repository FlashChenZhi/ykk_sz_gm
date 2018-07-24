// $Id: MessageLogHandler.java,v 1.1.1.1 2006/08/17 09:33:43 mori Exp $
package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/**<jp>
 * メッセージログ・ファイルへの書き込みと、読み込みを行うためのクラスです。
 * ログ・ファイルの形式は、以下のようになります。デリミタ文字は、タブとなっており、
 * メッセージ・パラメータ内の、タブ,改行は すべてスペースに置き換えられます。
 * <pre>
 *	日付 メッセージ番号 メッセージ・パラメータ [...]
 * </pre>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:43 $
 * @author  $Author: mori $
 </jp>*/
/**<en>
 * This class enables the writing and reading of message log file.
 * The format of the log file is as follows. Delimiters are handled as tab.
 * All tabs and linefeeds in the message parameter will be replaced by space.
 * <pre>
 *	date  message number  message parameter [...]
 * </pre>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:43 $
 * @author  $Author: mori $
 </en>*/
public class MessageLogHandler extends Object
{
	// Class fields --------------------------------------------------
	/**<jp>
	 * ログ・ファイルのデリミタ
	 </jp>*/
	/**<en>
	 * Delimiter in log file
	 </en>*/
	protected static final String LOG_DELIM = "\t" ;
	/**<jp>
	 * ログ日付のフォーマット形式
	 </jp>*/
	/**<en>
	 * Format type of log date
	 </en>*/
	protected static final String DATE_FORMAT = "yyyy MM dd HH mm ss SSS z" ;
	/**<jp>
	 * ログのエンコード形式
	 </jp>*/
	/**<en>
	 * encoding type of the log
	 </en>*/
	protected static final String LOG_ENCODE = "UTF-8" ;
	/**<jp>
	 * 日付のフォーマットを行うためのフォーマッタ
	 </jp>*/
	/**<en>
	 * Formatter for the date formatting
	 </en>*/
	protected static SimpleDateFormat MSG_DATE_FORMATTER = new SimpleDateFormat (DATE_FORMAT);

	// Class variables -----------------------------------------------
	/**<jp>
	 * メッセージのロケールを保持する変数。
	 </jp>*/
	/**<en>
	 * variables that preserve the locale of message
	 </en>*/
	protected Locale wLocale ;
	/**<jp>
	 * メッセージログ・ファイル名を保持する変数。
	 </jp>*/
	/**<en>
	 * variables that preserve the name of message log file
	 </en>*/
	protected String wLogfileName ;
	/**<jp>
	 * メッセージリソースを保持する変数
	 </jp>*/
	/**<en>
	 * variables that preserves the message resource
	 </en>*/
	protected MessageResource wMsgRes ;
	/**<jp>
	 * メッセージログ・ファイルを読み込むための<code>Reader</code>
	 </jp>*/
	/**<en>
	 * <code>Reader</code> to read the message log file
	 </en>*/
	protected LineNumberReader wLNReader ;

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returns the version of this class.
	 * @return version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:33:43 $") ;
	}

	// Constructors --------------------------------------------------
	/**<jp>
	 * デフォルト・ロケールで、メッセージを読み書きするように初期化します。
	 * @param logfile 対象のログファイル名
	 </jp>*/
	/**<en>
	 * Initializes at the default locale to ensure the reading and writing  
	 * of the message.
	 * @param logfile log file name objected
	 </en>*/
	public MessageLogHandler(String logfile)
	{
		this(logfile, Locale.getDefault()) ;
	}

	/**<jp>
	 * ロケールを指定して、メッセージを読みこむように初期化します。
	 * @param locale  指定するロケール
	 * @param logfile 対象のログファイル名
	 </jp>*/
	/**<en>
	 * Specifies the locale and initializes so that message shall be read.
	 * @param locale  locale to be sprcified
	 * @param logfile log file name objected
	 </en>*/
	public MessageLogHandler(String logfile, Locale locale)
	{
		wLocale = locale ;
		wLogfileName = logfile ;
//		wMsgRes = new MessageResource(locale) ;
		wMsgRes = new MessageResource("LogMessageDef",locale) ;
	}

	// Public methods ------------------------------------------------
	/**<jp>
	 * 現在書き込みを行っているファイルのサイズを取得します。
	 * @return ファイルサイズ
	 </jp>*/
	/**<en>
	 * Retrieves the size of the file currently under writing procedure.
	 * @return file size
	 </en>*/
	public long getFileSize()
	{
		File file = new File(wLogfileName);
		return file.length();
	}

	/**<jp>
	 * メッセージを書き込みます。メッセージは、リソースとパラメータ・配列から
	 * フォーマットされ、ファイルに書き込まれます。
	 * このとき、パラメータの数が合わない場合は、パラメータ情報が捨てられるか、
	 * 埋め込みが完了していない状態でログが書き込まれます。
	 * ログ・ファイルには、パラメータがデリミタ・文字で区切られた文字列として格納されます。
	 * @param msgnum  メッセージ番号
	 * @param fac  ファシリティ・コード
	 * @param ci  Class情報
	 * @param param  パラメータ・配列
	 </jp>*/
	/**<en>
	 * Messages to be written. Message will be formatted based on the resource and parameter
	 * array, then will be written in file.
	 * In case the number of parameter do not match, this parameter will be either abondonned 
	 * or the log will be written while embedding be incomplete.
	 * Parameter will be recognized as string, devided by delimeter characters, then will 
	   be stored in the log file. 
	 * @param msgnum  message number
	 * @param fac  facility code
	 * @param ci  class information
	 * @param param  parameter array
	 </en>*/
	public void write(int msgnum, String fac, String ci, Object[] param) throws IOException
	{
		// open streams
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(wLogfileName, true), LOG_ENCODE) ;

		// create msg parameter string
		StringBuffer msgbuf = new StringBuffer(256) ;

		// date first
		Date ndate = new Date() ;
		msgbuf.append(MSG_DATE_FORMATTER.format(ndate)) ;

		// message number
		msgbuf.append(LOG_DELIM + Integer.toString(msgnum)) ;

		// facility code
		msgbuf.append(LOG_DELIM + fac) ;

		// class information
		msgbuf.append(LOG_DELIM + ci) ;

		// set parameters to string buffer
		if (param != null)
		{
			for (int i=0; i < param.length; i++)
			{
				if (param[i] != null)
				{
					// remove delimiter charactor from parameter and triming space char.
					String pst = param[i].toString()
							.replace(LOG_DELIM.charAt(0), ' ')
							.replace('\n', ' ')
							.replace('\r', ' ')
							.trim() ;
					msgbuf.append(LOG_DELIM + pst) ;
				}
			}
		}

		// write parameter string to logfile
		String pmsg = msgbuf.toString() + "\n" ;

		osw.write(pmsg, 0, pmsg.length()) ;
		// finish : close streams
		osw.flush() ;
		osw.close() ;
		osw = null ;
	}

	/**<jp>
	 * 通信トレースを書き出すために用いられます。ここでは最後の空白文字が有っても切り捨てられません。
	 * メッセージを書き込みます。メッセージは、リソースとパラメータ・配列から
	 * フォーマットされ、ファイルに書き込まれます。
	 * このとき、パラメータの数が合わない場合は、パラメータ情報が捨てられるか、
	 * 埋め込みが完了していない状態でログが書き込まれます。
	 * ログ・ファイルには、パラメータがデリミタ・文字で区切られた文字列として格納されます。
	 * @param msgnum  メッセージ番号
	 * @param fac  ファシリティ・コード
	 * @param ci  Class情報
	 * @param param  パラメータ・配列
	 </jp>*/
	/**<en>
	 * This is used to write up the trace of communication. This time, the blank character in 
	 * the end of the line must be maintained. 
	 * Writing the message - The message will be formatted based on the resource and parameter array,
	 * then writen into the file.
	 * In case the number of parameters do not match, this parameter will be either abondonned or the 
	 * log will be written while the embedding be icomplete.
	 * Parameter will be recognized as string, devided by delimeter characters, then will 
	 * be stored in the log file. 
	 * @param msgnum  message number
	 * @param fac  facility code
	 * @param ci  Class information
	 * @param param  parameter, array
	 </en>*/
	public void write_communication(int msgnum, String fac, String ci, Object[] param) throws IOException
	{
		// open streams
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(wLogfileName, true), LOG_ENCODE) ;

		// create msg parameter string
		StringBuffer msgbuf = new StringBuffer(256) ;

		// date first
		Date ndate = new Date() ;
		msgbuf.append(MSG_DATE_FORMATTER.format(ndate)) ;

		// message number
		msgbuf.append(LOG_DELIM + Integer.toString(msgnum)) ;

		// facility code
		msgbuf.append(LOG_DELIM + fac) ;

		// class information
		msgbuf.append(LOG_DELIM + ci) ;

		// set parameters to string buffer
		if (param != null)
		{
			for (int i=0; i < param.length; i++)
			{
				if (param[i] != null)
				{
					// remove delimiter charactor from parameter and triming space char.
					String pst = param[i].toString()
							.replace(LOG_DELIM.charAt(0), ' ')
							.replace('\n', ' ')
							.replace('\r', ' ');
					msgbuf.append(LOG_DELIM + pst) ;
				}
			}
		}

		// write parameter string to logfile
		String pmsg = msgbuf.toString() + "\n" ;

		synchronized (osw)
		{
			osw.write(pmsg, 0, pmsg.length()) ;
			// finish : close streams
			osw.flush() ;
		}
		osw.close() ;
		osw = null ;
	}

	/**<jp>
	 * ログ・ファイルを読み込むためにオープンします。
	 </jp>*/
	/**<en>
	 * Opens log file in order to read.
	 </en>*/
	public void readOpen() throws IOException
	{
		// close stream if opened
		if (wLNReader != null)
		{
			readClose() ;
		}
		
		File file = new File(wLogfileName);
		if(file.exists())
		{
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), LOG_ENCODE) ;
			wLNReader = new LineNumberReader(isr) ;
		}
	}
	/**<jp>
	 * 読み込みのためにオープンされているログファイルをクローズします。
	 </jp>*/
	/**<en>
	 * Closes the log file opened for reading.
	 </en>*/
	public void readClose()
	{
		try
		{
			if (wLNReader != null)
			{
				wLNReader.close() ;
			}
		}
		catch (Exception e)
		{
		}
		wLNReader = null ;
	}

	/**<jp>
	 * ログ・ファイルから次の行のメッセージを読み込みます。
	 * @return  ログ・メッセージ。最後まで読み込みが完了している場合は、null
	 </jp>*/
	/**<en>
	 * Reads the next message line of the log file.
	 * @return  log message. If all lines are completely read, enter 'null'.
	 </en>*/
	public LogMessage readNext() throws IOException
	{
		// read 1 line from logfile (format parameter)
		String smsg = wLNReader.readLine() ;
		// check end of file
		if (smsg == null)
		{
			return (null) ;
		}

		StringTokenizer stk = new StringTokenizer(smsg, LOG_DELIM, false) ;
		String wst ="";
		Date dt = null;
		int msgnum = 0;
		String fac = "";
		String ci = "";
		if(stk.countTokens() >= 4)
		{
			// getting date
			wst = stk.nextToken() ;
			dt = MSG_DATE_FORMATTER.parse(wst, new ParsePosition(0)) ;
			// getting message number
			wst = stk.nextToken() ;
			msgnum = Integer.parseInt(wst) ;
			// getting facility code
			fac = stk.nextToken() ;
			// getting class info
			ci = stk.nextToken() ;
			// getting parameter objects
			String[] params = new String[stk.countTokens()] ;
			for (int i=0; i < params.length; i++)
			{
				params[i] = stk.nextToken() ;
			}
			// getting message format string from resource
			//<jp> メッセージNoが0、1はeAWCの送受信トレースで使用する番号</jp>
			//<en> Message number 0 and 1 are used in sending/receiving trace of eAWC.</en>
			if (msgnum == 0 || msgnum == 1)
			{
				String msgstr = "" ;
				for (int i=0; i < params.length; i++)
				{
					msgstr = msgstr + params[i] ;
				}
				return (new LogMessage(dt, msgnum, fac, ci, msgstr)) ;
			}
			//<jp> 通常のメッセージNoがセットされている場合</jp>
			//<en> In case the ordinary message number is set;</en>
			else
			{
				String msgstr = wMsgRes.getMessage(msgnum, params) ;

				return (new LogMessage(dt, msgnum, fac, ci, msgstr)) ;
			}
		}
		return (new LogMessage(dt, msgnum, fac, ci, "Bad Message Format")) ;
	}

	// Public accsesser methods --------------------------------------
	/**<jp>
	 * 現在使用中のログ・ファイル名を取得します。
	 * @return    ログ・ファイル名
	 </jp>*/
	/**<en>
	 * Retrieves the log file name currently in use.
	 * @return    log file name
	 </en>*/
	public String getLogfile()
	{
		return (wLogfileName) ;
	}
	/**<jp>
	 * 現在使用中のロケールを取得します。
	 * @return    <code>Locale</code>
	 </jp>*/
	/**<en>
	 * Retrieves the locale currently in use.
	 * @return    <code>Locale</code>
	 </en>*/
	public Locale getLocale()
	{
		return (wLocale) ;
	}
	/**<jp>
	 * 現在使用中のメッセージ・リソースを取得します。
	 * @return    <code>MessageResource</code>
	 </jp>*/
	/**<en>
	 * Retrieves the message resource currently in use.
	 * @return    <code>MessageResource</code>
	 </en>*/
	public MessageResource getMessageResource()
	{
		return (wMsgRes) ;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	// Debug methods -----------------------------------------------

}
//end of class

