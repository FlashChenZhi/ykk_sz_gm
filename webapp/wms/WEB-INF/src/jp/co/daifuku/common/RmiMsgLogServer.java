// $Id: RmiMsgLogServer.java,v 1.1.1.1 2006/08/17 09:33:43 mori Exp $
package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;

import jp.co.daifuku.rmi.RmiServAbstImpl;
/**<jp>
 * メッセージログ・サーバアプリケーションのクラスです。
 * RMIのサーバとして自身のインスタンスを登録し、リモート・メソッドを提供します。
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
 * This class is for the application of message log server.
 * It registers own instance as RMI server and provides remote methods.
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
public class RmiMsgLogServer extends RmiServAbstImpl
{
	// Class fields --------------------------------------------------
	/**<jp>
	 * メッセージログ・サーバのオブジェクト名
	 </jp>*/
	/**<en>
	 * Name of the message log server object
	 </en>*/
	public static final String LOGSERVER_NAME = "messagelog_server" ;

	/**<jp>
	 * デフォルトのログファイル名
	 </jp>*/
	/**<en>
	 * Default log file name
	 </en>*/
	static final String DEFAULT_LOGFILE = CommonParam.getParam("LOGS_PATH") + CommonParam.getParam("MESSAGELOG_FILE") ;

	/**<jp>
	 * ログのファイル名に使用する日付フォーマット形式 CommonParamへ移動すべき
	 </jp>*/
	/**<en>
	 * To be tranferred to CommonParam, the format style of the date for use in log file name
	 </en>*/
	protected static final String DATE_FORMAT = "yyyyMMddHHmmssSSS" ;

	/**<jp>
	 * メッセージログのパス
	 </jp>*/
	/**<en>
	 * Path of message log
	 </en>*/
	protected static final String MESSAGELOG_PATH = CommonParam.getParam("LOGS_PATH");

	/**<jp>
	 * メッセージログのファイルネーム（message.log）
	 </jp>*/
	/**<en>
	 * File name of message log(message.log)
	 </en>*/
	protected static final String MESSAGELOG_FILENAME = CommonParam.getParam("MESSAGELOG_FILE") ;

	/**<jp>
	 * メッセージログの最大サイズ。このサイズを超えたときに新しいファイルに書き込みます。
	 </jp>*/
	/**<en>
	 * MAX. size of message log; new file will be used if one exceeds.
	 </en>*/
	protected static final long MESSAGELOG_MAXSIZE = Long.parseLong(CommonParam.getParam("MESSAGELOG_MAXSIZE"));

	/**<jp>
	 * 固定パラメータの数
	 </jp>*/
	/**<en>
	 * Number of fixed parameter
	 </en>*/
	static final int N_FIX_PARAM = 3 ;

	/**<jp>
	 * 固定パラメータのインデックス(メッセージ番号)
	 </jp>*/
	/**<en>
	 * Index for the fixed parameter (message number)
	 </en>*/
	static final int I_MESSAGE_NUM = 0 ;

	/**<jp>
	 * 固定パラメータのインデックス(ファシリティ・コード)
	 </jp>*/
	/**<en>
	 * Index of fixed parameter (facility code)
	 </en>*/
	static final int I_FACILITY = 1 ;

	/**<jp>
	 * 固定パラメータのインデックス(Class情報)
	 </jp>*/
	/**<en>
	 * Index for the fixed parameter (Class information)
	 </en>*/
	static final int I_CLASS_INFO = 2 ;

	// Class variables -----------------------------------------------
	private MessageLogHandler wMsgLogHandler ;

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

	/**<jp>
	 * メッセージログ・サーバを起動し、リモート・オブジェクトを生成します。<br>
	 * パラメータとして、ログ・ファイル名を受け付けます。<br>
	 * パラメータが省略された場合は、デフォルトのログ・ファイルを作成します。
	 * @param args ログ・ファイル名
	 </jp>*/
	/**<en>
	 * Starts up the message log server and instantiate the remote object.F<br>
	 * Log file name will be accepted as a parameter.<br>
	 * If the parameter was omitted, default log file will be created.
	 * @param args log file name
	 </en>*/
	public static void main(String args[])
	{
		RmiMsgLogServer mserv = null ;
		try
		{
			//<jp> ログファイル名が指定されているかチェック</jp>
			//<en> Checking if the log file name is assigned</en>
			if (args.length == 0)
			{
				mserv = new RmiMsgLogServer() ;
			}
			else
			{
				mserv = new RmiMsgLogServer(args[0]) ;
			}
			//<jp> 既にRMI Serverに登録されている場合は終了（多重起動を防ぐ）</jp>
			//<en> Closing if it is already registered in RMI Server. (preventing the start-up overlaps)</en>
			if (mserv.isbind(LOGSERVER_NAME))
			{
				System.exit(0);
				return;
			}
			mserv.bind(LOGSERVER_NAME) ;

			//<jp> メッセージログサーバーを起動します。</jp>
			//<en> Starting up the message log server.</en>
			RmiMsgLogClient.write("6020001", "RmiMsgLogServer");
			System.out.println("Message logging server started.") ;
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}
	}

	// Constructors --------------------------------------------------
	/**<jp>
	 * デフォルトのコンストラクタです。
	 * ログ・ファイルは、起動されたカレント・ディレクトリに<code>message.log</code>
	 * というファイルで作成されます。
	 </jp>*/
	/**<en>
	 * Default constructor.
	 * Log file should be created as <code>message.log</code> in the 
	 * current directory that started up.
	 </en>*/
	public RmiMsgLogServer() throws java.rmi.RemoteException
	{
		setLogFile(MESSAGELOG_PATH+getCurrentLogFileName());
	}

	/**<jp>
	 * ログ・ファイルを指定してインスタンスを作成します。
	 * @deprecated ファイル名を指定することはありません。RmiMsgLogServer()を使用してください。
	 * @param logfile ログを書き込むファイル名
	 </jp>*/
	/**<en>
	 * Specifies log file and create instance
	 * @deprecated No need to specify the file name. Use RmiMsgLogServer().
	 * @param logfile File name that log will be written.
	 </en>*/
	public RmiMsgLogServer(String logfile) throws java.rmi.RemoteException
	{
		super();
		setLogFile(MESSAGELOG_PATH+getCurrentLogFileName());
	}

	// Public methods ------------------------------------------------
	/**<jp>
	 * メッセージ・ログファイルに記録します。
	 * このメソッドは、リモート・オブジェクトによって呼び出されます。
	 * @param params  メッセージ・ログファイルに記録するログ・パラメータ。<br>
	 * <p>
	 * 配列の1つ目は、メッセージログ・番号を示す<code>Integer</code>を設定します。<br>
	 * 実際に書き込む、メッセージ・パラメータは、配列の2番目以降に設定してください。
	 * </p>
	 </jp>*/
	/**<en>
	 * Records the message log file
	 * This method is invoked by remote object.
	 * @param params  Log parameter to record in message log file<br>
	 * <p>
	 * 1st digit of the array is to set the message log and the number <code>Integer</code>.<br>
	 * Set the message parameter for writing at 2nd digit or after in the array.
	 * </p>
	 </en>*/
	public void write(Object[] params) throws IOException
	{
		
		//Check file size. If this is over the MESSAGELOG_MAXSIZE, Get new file name.
		if(wMsgLogHandler.getFileSize() > MESSAGELOG_MAXSIZE)
		{
			String logfile = MESSAGELOG_PATH + getNewLogFileName();
			setLogFile(logfile);
		}

		// check message number is instance of Integer
		if (params[I_MESSAGE_NUM] instanceof Integer)
		{
			// getting message number
			int mnum = ((Integer)params[I_MESSAGE_NUM]).intValue() ;
			String fac = params[I_FACILITY].toString() ;
			String ci = params[I_CLASS_INFO].toString() ;

			// set message parameters
			Object[] mp = new Object[params.length -N_FIX_PARAM] ;
			for (int i=0; i < mp.length; i++)
			{
				mp[i] = params[i +N_FIX_PARAM] ;
			}
			System.out.println("write log:" + ci) ;
			wMsgLogHandler.write(mnum, fac, ci, mp) ;
		}
		else
		{
			throw (new IOException("Parameter 0 instance must be java.lang.Integer")) ;
		}
	}

	/**<jp>
	 * A処理を終了します。
	 * 外部よりこのメソッドを呼び出された場合、通信処理を終了します。
	 </jp>*/
	/**<en>
	 * Terminates the A process.
	 * In case this method is called exterior, communication process should be closed.
	 </en>*/
	public synchronized void stop()
	{
		//<jp> メッセージログサーバーを終了します。</jp>
		//<en> Terminating message log server.</en>
		RmiMsgLogClient.write("6020002", "RmiMsgLogServer");
		this.unbind();
		System.exit(0);
	}

	/**<jp>
	 * 指定フォルダの中から次に書き込むログファイル名を取得します。
	 </jp>*/
	/**<en>
	 * Retrieves the next log file name to write from the specific folder
	 </en>*/
	public static String getCurrentLogFileName()
	{
		//<jp> Fileのオブジェクトを生成</jp>
		//<en> Instantiation of FIle object</en>
		File passfile = new File(MESSAGELOG_PATH);
		//<jp> フォルダの中のファイルを読み込む</jp>
		//<en> Reading file in the folder</en>
		String[] pass = passfile.list();
		Vector  vec     = new Vector();
		String filename = "";
		
		//<jp> フォルダ内のファイルをソートします。</jp>
		//<en> Sorting the file in the folder</en>
		Arrays.sort(pass);

		if(pass.length != 0)
		{
			//<jp> passの中のファイル名の個数分まわします。</jp>
			//<en> Looping the times of file numbers in pass</en>
			for (int i=0; i<pass.length; i++)
			{
				//<jp> ファイル名の中でMESSAGELOG_FILEを含むファイルがあるかを調べます。
				// MESSAGELOG_FILEを含む場合</jp>
				//<en> Checking if there are any file including MESSAGELOG_FILE in the file.
				//If MESSAGELOG_FILE is included</en>
				String name = MESSAGELOG_FILENAME;
				//<jp> 拡張子を取り除き、比較用の文字列を生成する。</jp>
				//<en>String array for comparison is generated by eliminating file extension. </en>
				String dlim = ".";
				StringTokenizer stk  = new StringTokenizer(name, dlim, false) ;
				name = stk.nextToken();

				if(pass[i].indexOf(name) != -1)
				{
					//<jp>ファイル名を取得します。</jp>
					//<en>Retrieving the file name.</en>
					filename = pass[i];
					vec.addElement(filename);
				}
			}

			//<jp>メッセージログが存在しないので新しく名前を採番する。</jp>
			//<en>As there is no message log, get new name for the file.</en>
			if(vec.size() == 0)
			{
				return getNewLogFileName();
			}
			//<jp>昇順にソートされている一番最後のデータを返す</jp>
			//<en>It returns the final of the sorted data in ascending order. </en>
			return (String)vec.lastElement();
		}
		//<jp>メッセージログが存在しないので新しく名前を採番する。</jp>
		//<en>As there is no message log, get new name for the file.</en>
		else
		{
			return getNewLogFileName();
		}
	}

	/**<jp>
	 * 新しいログファイル名を取得します。
	 </jp>*/
	/**<en>
	 * Retrieves the new log file name.
	 </en>*/
	public static String getNewLogFileName()
	{
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//		String datetime = sdf.format(date);
//		
//		String filename = MESSAGELOG_FILENAME;
//		//<jp> BackUpファイル名作成</jp>
//		//<en> Creating the name of BackUp file </en>
//		String dlim = ".";
//		StringTokenizer stk  = new StringTokenizer(filename, dlim, false) ;
//		String name = stk.nextToken() + datetime + dlim + stk.nextToken() ;
		return MESSAGELOG_FILENAME;
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	
	/**<jp>
	 * ログファイルを設定します。
	 </jp>*/
	/**<en>
	 * Sets the log file.
	 </en>*/
	private void setLogFile(String logfile)
	{
		wMsgLogHandler = new MessageLogHandler(logfile) ;
	}


}
//end of class

