package jp.co.daifuku.wms.master.schedule;
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

/**
 * Designer : E.Takeda <BR>
 * Maker : E.Takeda  <BR>
 * <BR>
 * マスタパッケージのスケジュール処理を行なう抽象クラスです。
 * 共通メソッドはこのクラスに実装され、スケジュール処理の個別の振る舞いについては、
 * このクラスを継承したクラスによって実装されます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/01/10</TD><TD>E.Takeda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  $Author: mori $
 */
public class AbstractMasterSCH extends AbstractSCH
{
	// Class variables -----------------------------------------------
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:19 $");
	}
	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	
	// Protected methods ---------------------------------------------
	/**
	 * 作業者コード、パスワード、アクセス権限の内容が正しいかチェックを行います。<BR>
	 * 内容が正しい場合はtrueを、正しくない場合はfalseを返します。<BR>
	 * 返り値がfalseの場合
	 * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
	 * 
	 * @param  conn               データベースとのコネクションオブジェクト
	 * @param  checkParam        入力チェックを行う内容が含まれたパラメータクラス
	 * @param  adminFlag         アクセス権限のチェック有無 true:有り false:無し
	 * @return 作業者コード、パスワードの内容が正しい場合はtrueを、正しくない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean checkWorker(Connection conn, MasterParameter searchParam, boolean adminFlag) throws ReadWriteException, ScheduleException
	{
		String workerCode = searchParam.getWorkerCode();
		String password = searchParam.getPassword();
		
		return correctWorker(conn, workerCode, password, adminFlag);
	}
	
	/**
	 * このメソッドはWarenaviSystemテーブルのREPORTDATAを0から1に更新するメソッドです 。 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param status true:日次更新中に変更する。
	 * 				  false:日時更新中を解除する。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void changeReportDataFlag(Connection conn, boolean status) throws ReadWriteException
	{
		try
		{
			WareNaviSystemAlterKey akey = new WareNaviSystemAlterKey();
			WareNaviSystemHandler wnhdl = new WareNaviSystemHandler(conn);
			akey.setSystemNo(WareNaviSystem.SYSTEM_NO);
			if (status)
			{
				akey.updateReportData(WareNaviSystem.REPORTDATA_LOADING);
			}
			else
			{
				akey.updateReportData(WareNaviSystem.REPORTDATA_STOP);
			}
			wnhdl.modify(akey);
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
		

}
