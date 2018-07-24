package jp.co.daifuku.wms.master.operator;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.master.schedule.MasterParameter;

/**
 * Designer : muneendra y<BR>
 * Maker 	: muneendra y<BR> 
 * <BR>
 * 各マスタから荷主コードを検索するためのクラスです。
 * <BR>
 * <CODE>ConsignorCodeFinder</CODE><BR>
 * <DIR>
 * 仕入先マスタ修正・削除画面、出荷先マスタ修正・削除画面、商品マスタ修正・画面で荷主コード検索ボタン押下処理のSQL文を発行し、<BR>
 * 荷主マスタに存在する荷主コードから検索画面先の各マスタの荷主コードを検索します。<BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>${date}</TD><TD>muneendra y</TD><TD></TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author: suresh $
 * @version $Revision: 1.2 $ $Date: 2006/11/10 00:35:35 $
 */
public class ConsignorCodeFinder implements DatabaseFinder
{

	/**
	 * デリミタ
	 * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
	 * 	例 String msginfo = "9000000" + wDelim + "Palette" + wDelim + "Stock" ;
	 */
	private String wDelim = MessageResource.DELIM ;

	private int wSearchCondition = 0;

	/**
	 * Statment
	 */
	protected Statement wStatement = null ;
	
	/**
	 * 検索結果を保持する変数。
	 */
	protected ResultSet wResultSet = null ;
	// 仕入先マスタ
	private String dmSupplier = "DMSUPPLIER";
	// 出荷先マスタ
	private String dmCustomer = "DMCUSTOMER";
	// 商品マスタ
	private String dmItem = "DMITEM";
	
	/**
	 * データベース接続用のConnectionインスタンス。
	 * トランザクション管理は、このクラスの中では行わない。
	 */
	protected Connection wConn ;
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/10 00:35:35 $");
	}
	
//	 Public methods ------------------------------------------------
	/**
	 * データベース接続用の<code>Connection</code>を設定します。
	 * @param conn 設定するConnection
	 */
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}
	
	/**
	 * ステートメントを生成し、カーソルをオープンします。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public void open() throws ReadWriteException
	{
		try
		{
			wStatement = wConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY) ;
		}
		catch (SQLException e)
		{
			//6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "StockFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + Integer.toString(e.getErrorCode()))) ;
		}
	}

	/**
	 * ステートメントをクローズします。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public void close() throws ReadWriteException
	{
		try
		{
			if (wResultSet != null) { wResultSet.close();  wResultSet = null; }
			if (wStatement != null) { wStatement.close();  wStatement = null; }
		}
		catch (SQLException e)
		{
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), "ConsignorCodeFinder");
			throw (new ReadWriteException("6006002" + wDelim + "DMSUPPLIER"));
		}
	}

	
	// Constructors --------------------------------------------------
	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public ConsignorCodeFinder(Connection conn)
	{
		setConnection(conn);  
	}
	
	/**
	 * 各画面ので入力された値を<DIR>MasterParameter</DIR>から取得し、<DIR>flg</DIR>でマスタを識別する。<BR>
	 * 荷主マスタに存在する荷主コードから、各マスタの荷主コードを検索する。<BR>
	 * 1件以上ある場合、検索結果を保持する。<BR>
	 * 該当がない場合はnullを返す。<BR>
	 * This Method returns the consignor code which is common between DMSTOCK and DNINVENTORYCHECK tables (UNION)
	 * @param key 検索のためのKey
	 * @return int count of records
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 */
	public int searchConsinor(SearchKey key, String flg)  throws ReadWriteException, ScheduleException
	{
		int count = 0;		
		ResultSet countSet = null;
		this.wSearchCondition = 1;
		
		// テーブル名
		String tableName = null;
		
		// 荷主コード
		String consignor = null;
		
		// 入力画面で荷主コードが入力され場合
		if (key.getReferenceCondition() != null)
		{
			consignor = key.getReferenceCondition();
		}
		// 入力されなかった場合
		else
		{
			consignor = "IS NOT NULL";
		}
		
        try
        {	
        	// 仕入先マスタ
        	if (MasterParameter.SEARCHFLAG_SUPPLIER.equals(flg))
        	{
        		tableName = dmSupplier;
        	}
        	// 出荷先マスタ
        	else if (MasterParameter.SEARCHFLAG_CUSTOMER.equals(flg))
        	{
        		tableName = dmCustomer;
        	}
        	// 商品マスタ
        	else if (MasterParameter.SEARCHFLAG_ITEM.equals(flg))
        	{
        		tableName = dmItem;
        	}
    		String coutSql = "SELECT COUNT(*) AS COUNT FROM DMCONSIGNOR WHERE EXISTS "
    					+"(SELECT * FROM "
						+ tableName
						+ " WHERE DMCONSIGNOR.CONSIGNOR_CODE = "
						+ tableName +".CONSIGNOR_CODE)"
						+" AND "
						+ consignor
						+ " ORDER BY CONSIGNOR_CODE ,CONSIGNOR_NAME";
			String selectSql = "SELECT *  FROM DMCONSIGNOR WHERE EXISTS "
						+"(SELECT * FROM "
						+ tableName
						+ " WHERE DMCONSIGNOR.CONSIGNOR_CODE = "
						+ tableName 
						+ ".CONSIGNOR_CODE)"
						+" AND " 
						+ consignor
						+ " ORDER BY CONSIGNOR_CODE ,CONSIGNOR_NAME";
DEBUG.MSG("FINDER", "Finder COUNT SQL[" + coutSql + "]") ;
        	countSet = wStatement.executeQuery(coutSql);
			
        	while (countSet.next())
			{
				count = countSet.getInt("COUNT");
			}
			//件数がMAXDISP以下の場合にのみ検索を実行します
			if ( count <= DatabaseFinder.MAXDISP )
			{
DEBUG.MSG("FINDER", "Finder COUNT SQL[" + selectSql + "]") ;
				wResultSet = wStatement.executeQuery(selectSql);
			}
			else
			{
				wResultSet = null;
			}
        		
        }
        catch (SQLException se)
        {
        	se.printStackTrace();
            //6006002 = データベースエラーが発生しました。{0}
            RmiMsgLogClient.write( new TraceHandler(6006002, se), "ConsignorCodeFinder" ) ;
            throw (new ReadWriteException("6006002" + wDelim + "DMCONSIGNOR" + "," + tableName)) ;
        }
        return count;
	}
	

	
	/**
	 * データベースの検索結果をエンティティ配列にして返します。
	 * @param 検索結果の指定された開始位置
	 * @param 検索結果の指定された終了位置
	 * @return エンティティ配列
 	 * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
	 */
	public Entity[] getEntities(int start, int end) throws ReadWriteException
	{

	    Consignor temp[] = null;

    	Vector entityList = new Vector() ;

	    Consignor tmpEntity = new Consignor();
		
		try
		{
			// 表示件数
			int count = end - start;
			if (wResultSet.absolute(start+1))
			{
				for (int lc = 0; lc < count; lc++)
				{
					if(lc > 0)
					{
						wResultSet.next();
					}
					for (int i = 1; i <= wResultSet.getMetaData().getColumnCount(); i++)
					{
						String colname = wResultSet.getMetaData().getColumnName(i) ;
						FieldName field = new FieldName(colname) ;

						// 日付時刻のクラスはgetTimestamp()でないとミリ秒を取り漏らすことがある。
						Object value = wResultSet.getObject(i) ;
						if (value instanceof java.util.Date)
						{
							value = wResultSet.getTimestamp(i) ;
						}
						
						tmpEntity.setValue(field, value) ;

					}
					entityList.addElement(tmpEntity) ;
					
					tmpEntity = new Consignor();

				}
	
				temp = new Consignor[entityList.size()];
				entityList.copyInto(temp) ;
			}
			else
			{
				// 指定された行が正しくありません。
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "ConsignorCodeFinder", null);
				throw new ReadWriteException("6006010");
			}
		}
		catch (SQLException e)
		{
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			// ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
			// 6006039 = {0}の検索に失敗しました。ログを参照して下さい。
			throw (new ReadWriteException("6006039")) ;
		}

		return temp;
	}    
	
	public int search(SearchKey key) throws ReadWriteException
	{
		return 0;
	}

}
