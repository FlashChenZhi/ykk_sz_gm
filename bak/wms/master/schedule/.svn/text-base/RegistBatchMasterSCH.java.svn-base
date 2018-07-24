/*
 * Created on 2006/01/05
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.schedule;

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.ItemOperator;
import jp.co.daifuku.wms.master.operator.SupplierOperator;

/**
 * Designer : mtakeuchi<BR>
 * Maker 	: mtakeuchi<BR> 
 * <CODE>RegistBatchMasterSCH</CODE>クラスは在庫データから必要な情報を取得してマスタに登録する処理を行うクラスです。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1. ユーザは下記のパラメータを入力します。<BR>
 * <DIR>
 *   <Parameter>* 必須入力<BR>
 * </DIR>
 * <DIR>
 * 	作業者コード : WorkerCode* <BR>
 *  パスワード   : Password* <BR>
 *  荷主マスタチェックボックス  : ChkConsignorMaster <BR>
 *  仕入先マスタチェックボックス: ChkSupplierMaster <BR>
 *  商品マスタチェックボックス  : ChkItemMaster <BR>
 * </DIR>
 * <BR><BR>
 * 2.登録ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR><BR><DIR>
 * <DIR>
 *   画面に入力された内容をパラメータとして受け取り、在庫データから必要な情報を取得してマスタに登録登録処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR><BR> 
 *  </DIR>
 * <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスタ情報に定義されていること。 <BR><DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR></DIR>
 *     3.予定データ取込中、日次更新処理中でないこと。 <BR>
 * <BR>
 *   ＜登録処理＞ <code>ConsignorOperator</code>クラスにて以下の処理を行います。 <BR>
 *     詳細は<code>ConsignorOperator</code>を参照のこと。 <BR>
 *     -荷主マスタテーブル(DMCONSIGNOR)の登録 <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/03/27</TD><TD>mtakeuchi</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class RegistBatchMasterSCH extends AbstractMasterSCH
{
	// Class variables -----------------------------------------------
	/**
	 * 在庫情報ハンドラ
	 */
	protected StockHandler wStockHandler = null;

	/**
	 * 荷主マスタ情報ハンドラ
	 */
	protected ConsignorHandler wConsignorHandler = null;

	/**
	 * 仕入先マスタ情報ハンドラ
	 */
	protected SupplierHandler wSupplierHandler = null;

	/**
	 * 商品マスタ情報ハンドラ
	 */
	protected ItemHandler wItemHandler = null;

	/**
	 * 在庫情報検索キー
	 */
	protected StockSearchKey wStockKey = null;

	/**
	 * 荷主マスタ情報検索キー
	 */
	protected ConsignorSearchKey wConsignorKey = null;

	/**
	 * 仕入先マスタ情報検索キー
	 */
	protected SupplierSearchKey wSupplierKey = null;

	/**
	 * 商品マスタ情報検索キー
	 */
	protected ItemSearchKey wItemKey = null;

	/**
	 * クラス名(荷主登録処理)
	 */
	private static final String wProcessName = "RegistBatchMasterSCH";

	/**
	 * このクラスのバージョンを返します。
	 * 
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $. $Date: 2004/08/16 ");
	}

	/**
	 * このクラスの初期化を行ないます。
	 */
	public RegistBatchMasterSCH()
	{
		wMessage = null;
	}

	/**
	 * このクラスの初期化を行ないます。
	 */
	public RegistBatchMasterSCH(Connection conn) throws ScheduleException, ReadWriteException
	{
		wMessage = null;
		wStockHandler = new StockHandler(conn);
		wStockKey = new StockSearchKey();
		wConsignorHandler = new ConsignorHandler(conn);
		wConsignorKey = new ConsignorSearchKey();
		wSupplierHandler = new SupplierHandler(conn);
		wSupplierKey = new SupplierSearchKey();
		wItemHandler = new ItemHandler(conn);
		wItemKey = new ItemSearchKey();
	}
	
	/**
	 * 画面から入力された溜めうちエリアの内容をパラメータとして受け取り、排他チェック結果を返します。 <BR>
	 * パラメータの入庫予定一意キーをキーに作業情報を検索し、該当データが存在しなかった場合、<BR>
	 * または、パラメータの入庫予定一意キーをキーに入庫予定情報を検索し、該当データが存在しなかった場合、 <BR>
	 * または最終更新日時が更新されていた場合は排他エラーとし、falseを返します。 <BR>
	 * 該当データが存在し、最終更新日時に変更がなければtrueを返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンス。 <BR>
	 *        StorageSupportParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @param inputParams ためうちエリアの内容を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        StorageSupportParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean check( Connection conn, Parameter checkParam ) throws ScheduleException, ReadWriteException
	{
		
		// パラメータの検索条件
		MasterParameter wParam = ( MasterParameter )checkParam;

		// 作業者コード、パスワードのチェックを行う。
		if (!checkWorker(conn, wParam, true))
		{
			return false;
		}

		if (!wParam.getChkConsignorMaster() && !wParam.getChkSupplierMaster() && !wParam.getChkItemMaster())
		{
			//6023461=登録対象を選択してください。
			wMessage = "6023461";
			return false;
		}
		
		return true;

	}

	/**
	 * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、<BR>
	 * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
	 * スケジュール処理が成功した場合はtrueを返します。<BR>
	 * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParams データベースとのコネクションオブジェクト
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// DBのcommit,rollbackの判断用
		boolean registFlag = false;
		// 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
		boolean updateLoadDataFlag = false;
		// checkParamの型を変換
		MasterParameter masParam = (MasterParameter) startParams[0];
		Consignor ent = new Consignor();
		
		// 入力情報のチェックを行う。
		if (!check(conn, masParam))
		{
			return false;
		}

		// 日次更新処理中のチェック
		if (isDailyUpdate(conn))
		{
			return false;
		}

		// 取り込みフラグチェック処理 	
		if (isLoadingData(conn))
		{
			return false;
		}
		
		//荷主マスタチェックボックスがチェックされているとき
		if (masParam.getChkConsignorMaster())
		{
			if (!createConsignorMaster(conn))
			{
				return false;
			}
		}

		//仕入先マスタチェックボックスがチェックされているとき
		if (masParam.getChkSupplierMaster())
		{
			if (!createSupplierMaster(conn))
			{
				return false;
			}
		}

		//商品マスタチェックボックスがチェックされているとき
		if (masParam.getChkItemMaster())
		{
			if (!createItemMaster(conn))
			{
				return false;
			}
		}
		
		// 6001003=登録しました。
		wMessage = "6001003";
		return true;
	}
	
	/**
	 * 荷主登録処理を開始します。<BR>
	 * 荷主登録処理が成功した場合はtrueを返します。<BR>
	 * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * @param conn データベースとのコネクションオブジェクト
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	public boolean createConsignorMaster(Connection conn) throws ReadWriteException, ScheduleException
	{
		//在庫データ取得
		wStockKey.KeyClear();
		wStockKey.setConsignorCodeGroup(0);
		wStockKey.setConsignorNameGroup(1);
		wStockKey.setStatusFlag(Stock.STATUS_FLAG_DELETE,"!=");
        wStockKey.setLastUpdateDateOrder(1,false);
		Stock[] stock = (Stock[])wStockHandler.findForUpdate(wStockKey); 
		ConsignorOperator consignorOperate = new ConsignorOperator(conn);
		
		for (int i = 0 ; i < stock.length ; i++)
		{
			wConsignorKey.KeyClear();
			//荷主コード
			wConsignorKey.setConsignorCode(stock[i].getConsignorCode());
			//荷主名称
			wConsignorKey.setConsignorName(stock[i].getConsignorName());

			if (wConsignorHandler.count(wConsignorKey) < 1)
			{
				Consignor ent = new Consignor();
				ent.setConsignorCode(stock[i].getConsignorCode());
				ent.setConsignorName(stock[i].getConsignorName());
				ent.setLastUsedDate(new Date());
				ent.setLastUpdatePname(wProcessName);
				
				int ret = consignorOperate.create(ent);
				
			}
			
		}
		
		return true;
	}

	/**
	 * 仕入先登録処理を開始します。<BR>
	 * 仕入先登録処理が成功した場合はtrueを返します。<BR>
	 * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * @param conn データベースとのコネクションオブジェクト
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	public boolean createSupplierMaster(Connection conn) throws ReadWriteException, ScheduleException
	{
		//在庫データ取得
		wStockKey.KeyClear();
		wStockKey.setConsignorCodeGroup(0);
		wStockKey.setConsignorNameGroup(1);
		wStockKey.setStatusFlag(Stock.STATUS_FLAG_DELETE,"!=");
        wStockKey.setLastUpdateDateOrder(1,false);
		Stock[] stock = (Stock[])wStockHandler.findForUpdate(wStockKey); 
		SupplierOperator supplierOperate = new SupplierOperator(conn);
		
		for (int i = 0 ; i < stock.length ; i++)
		{
			wSupplierKey.KeyClear();
			//荷主コード
			wSupplierKey.setConsignorCode(stock[i].getConsignorCode());
			//仕入先コード
			wSupplierKey.setSupplierCode(stock[i].getSupplierCode());

			if (wSupplierHandler.count(wSupplierKey) < 1 && !StringUtil.isBlank(stock[i].getSupplierCode()))
			{
				Supplier ent = new Supplier();
				ent.setConsignorCode(stock[i].getConsignorCode());
				ent.setSupplierCode(stock[i].getSupplierCode());
				ent.setSupplierName1(stock[i].getSupplierName1());
				ent.setLastUsedDate(new Date());
				ent.setLastUpdatePname(wProcessName);
				
				int ret = supplierOperate.create(ent);
				
			}
			
		}
		
		return true;
	}

	/**
	 * 商品登録処理を開始します。<BR>
	 * 商品登録処理が成功した場合はtrueを返します。<BR>
	 * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * @param conn データベースとのコネクションオブジェクト
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	public boolean createItemMaster(Connection conn) throws ReadWriteException, ScheduleException
	{
		//在庫データ取得
		wStockKey.KeyClear();
		wStockKey.setConsignorCodeGroup(0);
		wStockKey.setConsignorNameGroup(1);
		wStockKey.setStatusFlag(Stock.STATUS_FLAG_DELETE,"!=");
        wStockKey.setLastUpdateDateOrder(1,false);
		Stock[] stock = (Stock[])wStockHandler.findForUpdate(wStockKey); 
		ItemOperator ItemOperate = new ItemOperator(conn);
		
		for (int i = 0 ; i < stock.length ; i++)
		{
			wItemKey.KeyClear();
			//荷主コード
			wItemKey.setConsignorCode(stock[i].getConsignorCode());
			//商品コード
			wItemKey.setItemCode(stock[i].getItemCode());

			if (wItemHandler.count(wItemKey) < 1)
			{
				Item ent = new Item();
				ent.setConsignorCode(stock[i].getConsignorCode());
				ent.setItemCode(stock[i].getItemCode());
				ent.setItemName1(stock[i].getItemName1());
				ent.setEnteringQty(stock[i].getEnteringQty());
				ent.setBundleEnteringQty(stock[i].getBundleEnteringQty());
				ent.setLastUsedDate(new Date());
				ent.setLastUpdatePname(wProcessName);
				
				int ret = ItemOperate.create(ent);
				
			}
			
		}
		
		return true;
	}

}
