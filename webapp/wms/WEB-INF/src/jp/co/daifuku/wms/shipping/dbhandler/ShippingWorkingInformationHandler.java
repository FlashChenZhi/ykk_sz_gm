//$Id: ShippingWorkingInformationHandler.java,v 1.1.1.1 2006/08/17 09:34:27 mori Exp $
package jp.co.daifuku.wms.shipping.dbhandler ;

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
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * 作業情報 (WorkingInformation) を操作するためのクラスです。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:27 $
 * @author  $Author: mori $
 */
public class ShippingWorkingInformationHandler extends WorkingInformationHandler
{
	// Class feilds ------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:27 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public ShippingWorkingInformationHandler(Connection conn)
	{
		super(conn);
	}

	// Public methods ------------------------------------------------
	/**
	 * DMSTOCK表,DNWORKINFO表を結合し、該当するレコードをロックします。
	 * このメソッドを使用するアプリケーションは必ずトランザクションをcommitまたはrollbackして下さい。
	 * 荷主コード、出荷予定日、出荷先コード、出荷伝票No.、出荷伝票行No.をキーとなりますが、
	 * SQLの条件の組み立てはsetlockPlanDataメソッドで行います。
	 * @param startParams 検索のためのパラメータ
	 * @return true 正常 false 異常
	 * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
	 */
	public boolean lockPlanData(Parameter[] startParams ) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[] fmtObj = new Object[2];

		try
		{
			stmt = getConnection().createStatement();

			fmtObj[0] = setlockPlanData(startParams);

			String fmtSQL = "SELECT DW.JOB_NO FROM DNWORKINFO DW,DMSTOCK DS"
			+ " WHERE DW.STOCK_ID = DS.STOCK_ID"
			+ " AND {0} AND DW.STATUS_FLAG !='" + WorkingInformation.STATUS_FLAG_DELETE + "'"
			+ " AND JOB_TYPE = '" + WorkingInformation.JOB_TYPE_SHIPINSPECTION + "'"
			+ " FOR UPDATE ";

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
			rset = stmt.executeQuery(sqlstring);
			if (rset == null)
			{
				return false;
			}
		}
		catch (SQLException e)
		{
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			// ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
			throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch (SQLException e)
			{
				// 6006002 = データベースエラーが発生しました。{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				// ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
				throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
			}
		}
		return true;
	}

	/**
	 * DNWORKINFO表とDNHOSTSEND表を結合し、DNHOSTSEND表で送信済に更新したレコードに対応するDNWORKINFO表のレコードの
	 * 未作業報告フラグを送信済に更新します。
	 * このメソッドを使用するアプリケーションは必ずトランザクションをcommitまたはrollbackして下さい。
	 * 予定一意キー、作業日がキーとなります。
	 * @param planUkey 予定一意キー
	 * @param workDate 作業日
	 * @param pName 最終更新処理名
	 * @return true 正常 false 異常
	 * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
	 * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
	 */
	public boolean updateReportFlag( String planUkey, String workDate, String pName )
															throws ReadWriteException, NotFoundException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[] fmtObj = new Object[4];
		int count = 0;

		try
		{
			String countSQL = "SELECT COUNT(PLAN_UKEY) COUNT FROM DNHOSTSEND WHERE PLAN_UKEY = {0} AND WORK_DATE = {1}"
			+ " AND REPORT_FLAG = '" + SystemDefine.REPORT_FLAG_SENT + "'";

			String fmtSQL = "UPDATE DNWORKINFO SET REPORT_FLAG = '" + SystemDefine.REPORT_FLAG_SENT + "',"
			+ " LAST_UPDATE_DATE = {2}, LAST_UPDATE_PNAME = {3}"
			+ " WHERE JOB_NO IN ( SELECT JOB_NO FROM DNHOSTSEND WHERE PLAN_UKEY = {0} AND WORK_DATE = {1}"
			+ " AND REPORT_FLAG = '" + SystemDefine.REPORT_FLAG_SENT + "' )";

			fmtObj[0] = DBFormat.format(planUkey);
			fmtObj[1] = DBFormat.format(workDate);
			fmtObj[2] = DBFormat.format(new Date());
			fmtObj[3] = DBFormat.format(pName);
			String countsqlstring = SimpleFormat.format(countSQL, fmtObj);
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

			stmt = getConnection().createStatement();
			rset = stmt.executeQuery(countsqlstring);
			if (rset == null)
			{
				return false;
			}
			while (rset.next())
			{
				count = rset.getInt("COUNT");
			}
			if( count > 0 )
			{
				count = stmt.executeUpdate(sqlstring);
			}
		}
		catch (SQLException e)
		{
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			// ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
			throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch (SQLException e)
			{
				// 6006002 = データベースエラーが発生しました。{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				// ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
				throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
			}
		}
		return true;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * SQL実行用の文字配列を生成します。
	 * @param  startParams SQL文にセットするParameter
	 * @return SQL実行用の検索式
	 */
	private String setlockPlanData(Parameter[] params )
	{
		// パラメータの入力情報
		ShippingParameter[] wParam = ( ShippingParameter[] )params;

		String conditionSQL = "";
		// 荷主コード
		conditionSQL += " DW.CONSIGNOR_CODE = '" + wParam[0].getConsignorCode() + "'";
		// 出荷予定日
		conditionSQL += " AND " + "DW.PLAN_DATE = '" + wParam[0].getPlanDate() + "'";
		// 出荷先コード
		conditionSQL += " AND " + "DW.CUSTOMER_CODE = '" + wParam[0].getCustomerCode() + "'";
		// 出荷伝票No.
		conditionSQL += " AND " + "DW.SHIPPING_TICKET_NO = '" + wParam[0].getShippingTicketNo() + "'";
		conditionSQL += " AND ( ";
		for (int i = 0;i < wParam.length; i++)
		{
			if (i != 0 )
			{
				conditionSQL += "OR ";
			}			
			// 出荷伝票行No.
			conditionSQL += "DW.SHIPPING_LINE_NO = " + wParam[i].getShippingLineNo() + " ";			
		}
		conditionSQL += " )";

		return conditionSQL;
	}

}
//end of class
