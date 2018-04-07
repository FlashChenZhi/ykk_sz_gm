//$Id: SortingWorkingInformationHandler.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $
package jp.co.daifuku.wms.sorting.dbhandler ;

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
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * 作業情報 (WorkingInformation) を操作するためのクラスです。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/20</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class SortingWorkingInformationHandler extends WorkingInformationHandler
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
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:31 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public SortingWorkingInformationHandler(Connection conn)
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
		String[] ukey = null;
		Vector vec = new Vector();
		String wPlanUkey = "";

		try
		{
			stmt = getConnection().createStatement();

			fmtObj[0] = setlockPlanData(startParams);

			String fmtSQL = "SELECT DW.PLAN_UKEY PLAN_UKEY FROM DNWORKINFO DW"
			+ " WHERE "
			+ " {0} AND DW.STATUS_FLAG !='" + WorkingInformation.STATUS_FLAG_DELETE + "'"
			+ " AND JOB_TYPE = '" + WorkingInformation.JOB_TYPE_SORTING + "'";

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
			rset = stmt.executeQuery(sqlstring);
			if (rset == null || !rset.next())
			{
				return true;
			}
			while (rset.next())
			{
				wPlanUkey	= rset.getString("PLAN_UKEY");
				vec.addElement (wPlanUkey);
			}
			ukey = new String[vec.size()];
			vec.copyInto(ukey);

			WorkingInformationSearchKey searchkey = new WorkingInformationSearchKey();
			searchkey.setPlanUkey(ukey);
			searchkey.setJobNoCollect();
			if (!lock(searchkey))
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
	 * 取得したWoringInformationインスタンスの内容を元に、SQL実行用の文字配列を生成します。
	 * @param  wi 編集対象のWorkingInformationインスタンス
	 * @return SQL実行用の検索式
	 */
	private String setlockPlanData(Parameter[] startParams )
	{
		// パラメータの入力情報
		SortingParameter[] wParam = ( SortingParameter[] )startParams;

		String conditionSQL = "";
		// 荷主コード
		conditionSQL += "DW.CONSIGNOR_CODE = '" + wParam[0].getConsignorCode() + "'";
		// 仕分予定日
		conditionSQL += " AND " + "DW.PLAN_DATE = '" + wParam[0].getPlanDate() + "'";
		// 商品コード
		conditionSQL += " AND " + "DW.ITEM_CODE = '" + wParam[0].getItemCode() + "'";
		conditionSQL += " AND (";
		for (int i = 0;i < wParam.length; i++)
		{
			if (i == 0 )
			{
				conditionSQL += " ( ";
			}
			else
			{
				conditionSQL += " OR (";
			}
			// TC/DC区分
			if (wParam[i].getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
			{
				conditionSQL += " DW.TCDC_FLAG = '" + WorkingInformation.TCDC_FLAG_DC + "'";
			}
			else if (wParam[i].getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
			{
				conditionSQL += " DW.TCDC_FLAG = '" + WorkingInformation.TCDC_FLAG_CROSSTC + "'";
			}
			else if (wParam[i].getTcdcFlag().equals(SortingParameter.TCDC_FLAG_TC))
			{
				conditionSQL += " DW.TCDC_FLAG = '" + WorkingInformation.TCDC_FLAG_TC + "'";
			}
			// 出荷先コード
			conditionSQL += " AND " + "DW.CUSTOMER_CODE = '" + wParam[i].getCustomerCode() + "'";
			// 入力情報のケースピース区分がケースの時
			if (wParam[i].getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				conditionSQL += " AND " + "DW.WORK_FORM_FLAG = '" + WorkingInformation.CASEPIECE_FLAG_CASE + "'";
			}
			// 入力情報のケースピース区分がピースの時
			else if (wParam[i].getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				conditionSQL += " AND " + "DW.WORK_FORM_FLAG = '" + WorkingInformation.CASEPIECE_FLAG_PIECE + "'";
			}
			conditionSQL += " AND " + "DW.LOCATION_NO = '" + wParam[i].getSortingLocation() + "'";
			
			conditionSQL += " )";
		}
		conditionSQL += " )";

		return conditionSQL;
	}
}
//end of class
