//$Id: AbstractShippingSCH.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * 
 * <BR>
 * 出荷パッケージのスケジュール処理を行なう抽象クラスです。<BR>
 * WmsSchedulerインターフェースを実装し、このインターフェースの実装に必要な処理を実装します。<BR>
 * 共通メソッドはこのクラスに実装され、スケジュール処理の個別の振る舞いについては、
 * このクラスを継承したクラスによって実装されます。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/19</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * <TR><TD>2005/08/04</TD><TD>Y.Okamura</TD><TD>AbstractSCHを継承するように変更</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public abstract class AbstractShippingSCH extends AbstractSCH
{
	//	Class variables -----------------------------------------------
	/**
	 * クラス名
	 */
	public static final String CLASS_NAME = "AbstractShippingSCH";
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $");
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 作業者コード、パスワードの内容が正しいかチェックを行います。<BR>
	 * 内容が正しい場合はtrueを、正しくない場合はfalseを返します。<BR>
	 * 返り値がfalseの場合
	 * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
	 * 
	 * @param  conn              データベースとのコネクションオブジェクト
	 * @param  checkParam        入力チェックを行う内容が含まれたパラメータクラス
	 * @return 作業者コード、パスワードの内容が正しい場合はtrueを、正しくない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean checkWorker(Connection conn, ShippingParameter searchParam) throws ReadWriteException, ScheduleException
	{
		// パラメータから作業者コード、パスワードを取得する。
		String workerCode = searchParam.getWorkerCode();
		String password = searchParam.getPassword();

		return correctWorker(conn, workerCode, password);
	}

	/**
	 * ためうちエリアに表示できない場合、理由ごとに値を返します。<BR>
	 * 対象データがない：ShippingParameter[0]<BR>
	 * 最大表示件数を超えている：null<BR>
	 * <BR>
	 * <U>このメソッドを使用する場合、必ず先にAbstractSCHクラスのcanLowerDisplayメソッドを使用してください</U>
	 * 
	 * @return 理由ごとによる返り値
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected ShippingParameter[] returnNoDiaplayParameter() throws ScheduleException, ReadWriteException
	{
		if (getDisplayNumber() <= 0)
		{
			return new ShippingParameter[0];
		}
		
		if (getDisplayNumber() > WmsParam.MAX_NUMBER_OF_DISP)
		{
			return null;
		}

		doScheduleExceptionHandling(CLASS_NAME);
		return null;
	}
	
	/**
	 * 作業No.をキーに対象の作業情報とそれに紐付く在庫情報を同時にロックします。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param param ロックする対象になるデータの作業No.を含むパラメータインスタンス
	 * @return ロック対象がない場合はfalse、ロックを行った場合はtrueを返します
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	protected boolean lockAll(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{

		ShippingParameter[] wParam = (ShippingParameter[]) checkParam;
		Vector vec = new Vector();

		for (int i = 0; i < wParam.length; i ++)
		{
			vec.addElement(wParam[i].getJobNo());				
		}
		if (vec.isEmpty())
		{
			return false;
		} 			

		String[] jobNoArray = null;
		jobNoArray = new String[vec.size()];
		vec.copyInto(jobNoArray);
		
		return super.lockAll(conn, jobNoArray);

	}
	
	/**
	 * 出荷予定一意キーをキーに対象の作業情報とそれに紐付く在庫情報を同時にロックします。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param param ロックする対象になるデータの出荷予定一意キーを含むパラメータインスタンス
	 * @return ロック対象がない場合はfalse、ロックを行った場合はtrueを返します
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	protected boolean lockPlanUkeyAll(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{

		ShippingParameter[] wParam = (ShippingParameter[]) checkParam;
		Vector vec = new Vector();

		for (int i = 0; i < wParam.length; i ++)
		{
			vec.addElement(wParam[i].getShippingPlanUkey());				
		}
		if (vec.isEmpty())
		{
			return false;
		} 			

		String[] planUKeyArray = new String[vec.size()];
		vec.copyInto(planUKeyArray);
		
		return lockPlanUkeyAll(conn, planUKeyArray);
		
	}

}
//end of class
