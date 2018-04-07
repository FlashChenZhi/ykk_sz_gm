package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierReportFinder;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.master.operator.MasterOperator;
import jp.co.daifuku.wms.master.operator.SupplierOperator;

/**
 * Designer : S.Yoshida<BR>
 * Maker 	: S.Yoshida<BR> 
 *
 * <CODE>CorrectSupplierMasterSCH</CODE>は仕入先データ修正・削除クラスです<BR>
 * 画面から入力された内容をパラメータとして受け取り、仕入先マスタ修正・削除処理を行います。 <BR><BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1. ユーザは下記のパラメータを入力します。<BR>
 * <DIR>
 *   <Parameter>* 必須入力<BR>
 * </DIR><BR>
 * <DIR>
 *   作業者コード : WorkerCode* <BR>
 * 	 パスワード   : Password* <BR>
 * 	 荷主コード   : ConsignorCode* <BR>
 * 	 仕入先コード : SupplierCode* <BR>
 * </DIR><BR>
 * <BR>
 * 2. 次へボタン押下処理①（<CODE>nextCheck(Connection, Parameter)</CODE>メソッド）<BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、入力情報のチェックを行います。<BR>
 *   入力が正しい場合はtrue、入力が不正な場合はfalseを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
 *   以下のチェックを行います。<BR>
 *   <BR>
 *     <DIR>
 *       1.作業者コード・パスワードのチェック。<BR>
 *       2.入力情報の該当データが仕入先マスタ情報テーブルに存在すること<BR>
 *     </DIR>
 *   <BR>
 * </DIR>
 * <BR>
 * 3.次へボタン押下処理②（<CODE>query(Connection, Parameter)</CODE>メソッド）<BR>
 * <DIR>
 *   画面から入力された内容を受け取り、修正用のデータをデータベースから検索・取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。
 *   また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   なお、このメソッドはnextCheckメソッドの返り値がtrueだった場合に呼び出してください。<BR>
 *   ＜入力データ＞<BR>
 *   <DIR>
 *     作業者コード : WorkerCode <BR>
 * 	   パスワード   : Password <BR>
 *     荷主コード   : ConsignorCode <BR>
 *     仕入先コード : SupplierCode <BR>
 *   </DIR>
 *   ＜返却データ＞<BR>
 *   <DIR>
 *     荷主コード   : ConsignorCode <BR>
 *     仕入先コード : SupplierCode <BR>
 * 	   仕入先名称   : SupplierName <BR>
 *     最終更新日   : LastUpdateDate <BR>
 *     最終使用日   : LastUseDate <BR>	
 *   </DIR>
 * </DIR>
 * <BR><BR>
 * 4. 修正登録ボタン押下処理（<CODE>startSCHgetParams(Connection, Parameter[])</CODE>メソッド）<BR>
 * <DIR>
 *  画面に入力されている内容をパラメータとして受け取り、仕入先マスタ修正登録処理を行います。 <BR>
 *  条件エラーなどでスケジュールが完了しなかった場合はnullを返します。 <BR>
 *  エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * 	<CODE>startSCHgetParams</CODE>を実行する。ユーザは登録したパラメータを<CODE>startParams</CODE>に渡す。<BR>
 * 	画面に表示されている最終更新日時を実際にDMSUPPLIERテーブルにある最終更新日時と合わせます。<BR>
 * 	チェックＯＫの場合はDMSUPPLIERテーブルを更新します。<BR>
 * </DIR>
 * <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.パラメータの最終更新日時と仕入先マスタ情報の最終更新日時が一致すること。(排他チェック)<BR>
 *     2.同一荷主コード、仕入先コードの仕入先マスタ情報がデータベースに存在すること。 <BR>
 *     3.荷主コードが荷主マスタ情報に存在すること。 <BR>
 *     4.予定データ取込中、日次更新処理中でないこと。 <BR>
 * <BR>
 *   ＜更新処理＞ <code>SupplierOperator</code>クラスにて以下の処理を行います。 <BR>
 *     詳細は<code>SupplierOperator</code>を参照のこと。 <BR>
 *     -仕入先マスタテーブル(DMSUPPLIER)の更新 <BR>
 *       受け取ったパラメータの内容をもとに仕入先マスタ情報を更新する。 <BR>
 * <BR>
 * <BR>
 * 5. 削除ボタン押下処理（<CODE>startSCH(Connection, Parameter[])</CODE>メソッド）<BR>
 * <DIR>
 * 	画面に入力されている内容をパラメータとして受け取り、仕入先マスタの削除処理を行います。<BR>
 *  エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * 	<CODE>startSCH</CODE>を実行する。ユーザは登録したパラメータを<CODE>startParams</CODE>に渡す。<BR>
 * </DIR>
 * <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.パラメータの最終更新日時と仕入先マスタ情報の最終更新日時が一致すること。(排他チェック)<BR>
 *     2.同一荷主コード、仕入先コードの仕入先マスタ情報がデータベースに存在すること。 <BR>
 *     3.予定データ取込中、日次更新処理中でないこと。 <BR>
 * <BR>
 *   ＜削除処理＞ <code>SupplierOperator</code>クラスにて以下の処理を行います。 <BR>
 *     詳細は<code>SupplierOperator</code>を参照のこと。 <BR>
 *     -仕入先マスタテーブル(DMSUPPLIER)の更新 <BR>
 *       受け取ったパラメータの内容をもとに仕入先マスタ情報を削除する。 <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/01/17</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  $Author: mori $
 */
public class CorrectSupplierMasterSCH extends AbstractMasterSCH
{

	// Class variables -----------------------------------------------

	/**
	 * クラス名(仕入先マスタ修正・削除)
	 */
	private static final String wProcessName = "CorrectSupplierMasterSCH";


	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $. $Date: 2004/08/16 ");
	}

	/**
	 * このクラスの初期化を行ないます。
	 */
	public CorrectSupplierMasterSCH()
	{
		wMessage = null;
	}
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 仕入先マスタに荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// 該当する荷主コードがセットされます。
	    MasterParameter ent = new MasterParameter();

		// 出荷マスタハンドラ類のインスタンス生成
	    SupplierSearchKey searchKey = new SupplierSearchKey();
	    SupplierReportFinder supplierFinder = new SupplierReportFinder(conn);
	    Supplier[] wSupplier = null;

		try
		{
			// 検索条件を設定する。
			// データの検索
			// 状態フラグ＝「削除」以外
			searchKey.setDeleteFlag ("1", "!=");
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (supplierFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// 状態フラグ＝「削除」以外
				searchKey.setDeleteFlag("1", "!=");
				// ソート順に登録日時を設定
				searchKey.setLastUpdateDateOrder(1, false);
				
				searchKey.setConsignorCodeCollect("");

				if (supplierFinder.search(searchKey) > 0)
				{
					// 登録日時が最も新しい荷主名称を取得します。
				    wSupplier = (Supplier[]) supplierFinder.getEntities(1);
				    ent.setConsignorCode(wSupplier[0].getConsignorCode());
				}
			}
			supplierFinder.close();
			
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			// 必ずCollectionをクローズする
		    supplierFinder.close();
		}
		
		return ent;
		
	}	

	/**
	 * １画面目から入力された内容をパラメータとして受け取り、
	 * ２画面目の出力用のデータをデータベースから取得して返します。<BR>
	 * 詳しい動作はクラス説明を参照してください。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>MasterParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>MasterParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果を持つ<CODE>MasterParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          検索結果が最大表示件数を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		// searchParamの型を変換
		MasterParameter wParam = (MasterParameter) searchParam;
		MasterParameter[] viewParam = new MasterParameter[1];
		// 仕入先ハンドラ類のインスタンス生成
		Supplier[] wSupplier = null;
		SupplierSearchKey wKey = new SupplierSearchKey();
		SupplierHandler wHandler = new SupplierHandler(conn);
		
		// 検索条件をセットする
		// 荷主コード
		wKey.setConsignorCode(wParam.getConsignorCode());
		// 仕入先コード
		wKey.setSupplierCode(wParam.getSupplierCode());
		
		wSupplier = (Supplier[])wHandler.find(wKey);
		// 仕入先名称
		wParam.setSupplierName(wSupplier[0].getSupplierName1());
		// 最終更新日
		wParam.setLastUpdateDate(wSupplier[0].getLastUpdateDate());
		// 最終使用日
		wParam.setLastUseDate(wSupplier[0].getLastUsedDate());
		viewParam[0] = wParam;
		// 6001013 = 表示しました。
		wMessage = "6001013";
		return viewParam;
	}
	
	/**
	 *	パラメータで取得した仕入先コードの仕入先マスタ情報が更新可能かチェックを行います。<BR>
	 *	<CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、
	 *	仕入先マスタを検索し、パラメータで取得した最終更新日時と仕入先マスタの最終更新日時を比較し、
	 *	他端末で更新されていないかのチェックを行います。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param checkParam 入力内容を持つ<CODE>MasterParameter</CODE>クラスのインスタンス。 <BR>
	 *        MasterParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean check(Connection conn, Parameter checkParam)
			throws ReadWriteException, ScheduleException
	{
		// checkParamの型を変換
		MasterParameter wParam = (MasterParameter) checkParam;
		// 仕入先マスタハンドラ類のインスタンス生成
		SupplierHandler wObj = new SupplierHandler(conn);
		SupplierSearchKey wKey = new SupplierSearchKey();
		Supplier wSupplier = null;
		
		// 検索条件をセットする
		// 荷主コード
		wKey.setConsignorCode(wParam.getConsignorCode());
		// 仕入先コード
		wKey.setSupplierCode(wParam.getSupplierCode());
		
		try
		{
			wSupplier = (Supplier)wObj.findPrimary(wKey);
			
			if (wSupplier != null &&
				!wSupplier.getLastUpdateDate().toString().equals(wParam.getLastUpdateDateString()))
			{
				// 6003006 = このデータは、他の端末で更新されたため処理できません。
				wMessage = "6003006";
				return false;				
			}
		}
		catch (ReadWriteException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		return true;
	}
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、チェックを行います。 <BR>
	 * 該当データが存在し、入力が正しい場合はtrueを返します。<BR>
	 * 入力が不正な場合はfalseを返します。<BR>
	 * エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param checkParam 入力内容を持つ<CODE>MasterParameter</CODE>クラスのインスタンス。 <BR>
	 *        MasterParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		MasterParameter wParam = (MasterParameter)checkParam;
		
		// 作業者コード、パスワードのチェックを行う。
		if (!checkWorker(conn, wParam, true))
		{
			return false;
		}

		// 仕入先マスタ用オペレータクラスのインスタンス生成
		Supplier wSupplier = new Supplier();
		SupplierOperator wOperator = new SupplierOperator(conn);
		
		// 検索条件を設定する
		// 荷主コード
		wSupplier.setConsignorCode(wParam.getConsignorCode());
		// 仕入先コード
		wSupplier.setSupplierCode(wParam.getSupplierCode());
		
		// 存在チェックを行う
		if (!wOperator.exist(wSupplier))
		{
			// 6003018 = 対象データはありませんでした。
			wMessage = "6003018";
			return false;
		}
		return true;
	}
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、仕入先マスタ修正登録のスケジュールを開始します。<BR>
	 * スケジュールが正常終了した場合は最終更新日時、最終使用日が格納された配列を返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParams 設定内容を持つ<CODE>MasterParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         <CODE>MasterParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		// startParamsの型を変換
		MasterParameter[] wParam = (MasterParameter[])startParams;

		// 日次更新処理中のチェック
		if (isDailyUpdate(conn))
		{
			return null;
		}

		// 取り込みフラグチェック処理 	
		if (isLoadingData(conn))
		{
			return null;
		}

		// 他端末データチェック処理
		if (!check(conn, wParam[0]))
		{
			return null;
		}

		// 仕入先マスタ用オペレータクラスのインスタンス生成
		Supplier wSupplier = new Supplier();
		SupplierOperator wOperate = new SupplierOperator(conn);

		// 更新値をセットする
		// 荷主コード
		wSupplier.setConsignorCode(wParam[0].getConsignorCode());
		// 仕入先コード
		wSupplier.setSupplierCode(wParam[0].getSupplierCode());
		// 仕入先名称
		wSupplier.setSupplierName1(wParam[0].getSupplierName());
		// 最終更新処理名
		wSupplier.setLastUpdatePname(wProcessName);
		// 最終使用日時
		wSupplier.setLastUsedDate(new Date());
		
		// 仕入先マスタ情報更新
		int ret = wOperate.modify(wSupplier);
		
		if (ret == MasterOperator.RET_EXIST)
		{
			// 6007007=すでに同一データが存在するため、登録できません。
			wMessage = "6007007";
			return null;
		}
		else if (ret == MasterOperator.RET_NOT_EXIST)
		{
			// 6003013 = 修正対象データがありませんでした。
			wMessage = "6003013";
			return null;
		}
		else if (ret == MasterOperator.RET_CONSIST_NAME_EXIST)
		{
			// 6023441=指定された名称はすでに使用されているため修正できません。
			wMessage = "6023441";
			return null;
		}
		else if (ret == MasterOperator.RET_FATAL_ERROR)
		{
			// 6006001=予期しないエラーが発生しました。{0}
			wMessage = "6006001" + wDelim + wProcessName;
			return null;
		}
		
		MasterParameter[] viewParam = (MasterParameter[])this.query(conn, wParam[0]);
		// 6001004=修正しました。
		wMessage = "6001004";
		return viewParam;
	}
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、仕入先マスタ削除のスケジュールを開始します。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParams 設定内容を持つ<CODE>MasterParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         <CODE>MasterParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		// startParamsの型を変換
		MasterParameter masParams = (MasterParameter) startParams[0];

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

		// 他端末データチェック処理
		if (!check(conn, masParams))
		{
			return false;
		}

		// 仕入先マスタ用オペレータクラスのインスタンス生成
		Supplier wSupplier = new Supplier();
		SupplierOperator wOperate = new SupplierOperator(conn);
		
		// 削除条件をセットする
		// 荷主コード
		wSupplier.setConsignorCode(masParams.getConsignorCode());
		// 仕入先コード
		wSupplier.setSupplierCode(masParams.getSupplierCode());
		
		// 仕入先マスタ情報削除
		int ret = wOperate.actualDrop(wSupplier);
		
		if (ret == MasterOperator.RET_NOT_EXIST)
		{
			// 6003014 = 削除対象データがありませんでした。
			wMessage = "6003014";
			return false;
		}
		//6001005="削除しました。"
		wMessage = "6001005";
		return true;
	}
}
