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
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerReportFinder;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.master.operator.CustomerOperator;
import jp.co.daifuku.wms.master.operator.MasterOperator;

/**
 * Designer : E.Takeda<BR>
 * Maker 	: E.Takeda<BR> 
 *
 * <CODE>CorrectCustomerMasterSCH</CODE>は出荷先データ修正・削除クラスです<BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷先マスタ修正・削除処理を行います。 <BR>
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
 * 	 出荷先コード : CustomerCode* <BR>
 * </DIR><BR>
 * <BR>
 * 2. 次へボタン押下処理①（<CODE>nextCheck(Connection, Parameter)</CODE>メソッド）<BR>
 * <DIR>
 *  画面から入力された内容をパラメータとして受け取り、入力情報のチェックを行います。<BR>
 *  入力が正しい場合はtrue、入力が不正な場合はfalseを返します。<BR>
 *  エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
 *  以下のチェックを行います。<BR>
 *   <BR>
 *     <DIR>
 *       1.作業者コード・パスワードのチェック。<BR>
 *       2.入力情報の該当データが出荷先マスタ情報テーブルに存在すること<BR>
 *     </DIR>
 *   <BR>
 * </DIR>
 * <BR>
 * 3.次へボタン押下処理②（<CODE>query(Connection, Parameter)</CODE>メソッド）<BR>
 * <DIR>
 *  画面から入力された内容を受け取り、修正用のデータをデータベースから検索・取得して返します。<BR>
 *  該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。
 *   また、条件エラーなどが発生した場合はnullを返します。<BR>
 *  エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *  なお、このメソッドはnextCheckメソッドの返り値がtrueだった場合に呼び出してください。<BR>
 *  ＜入力データ＞<BR>
 *   <DIR>
 *     作業者コード : WorkerCode <BR>
 * 	   パスワード   : Password <BR>
 *     荷主コード   : ConsignorCode <BR>
 * 	   出荷先コード : CustomerCode <BR>
 *   </DIR>
 *  ＜返却データ＞<BR>
 *   <DIR>
 * 	   荷主コード   : ConsignorCode <BR>
 * 	   出荷先コード : CustomerCode <BR>
 * 	   出荷先名称   : CustomerName <BR>
 * 	   郵便番号     : PostalCode <BR>
 * 	   県名         : Prefecture <BR>
 * 	   住所         : Address <BR>
 * 	   ビル名等     : Address2 <BR>
 * 	   連絡先1      : Contact1 <BR>
 * 	   連絡先2      : Contact2 <BR>
 * 	   連絡先3      : Contact3 <BR>
 * 	   最終更新日   : LastUpdateDate <BR>
 * 	   最終使用日   : LastUseDate <BR>	
 *   </DIR>
 * </DIR>
 * <BR><BR>
 * 4. 修正登録ボタン押下処理（<CODE>startSCHgetParams(Connection, Parameter[])</CODE>メソッド）<BR>
 * <DIR>
 *  画面に入力されている内容をパラメータとして受け取り、出荷先マスタ修正登録処理を行います。 <BR>
 *  条件エラーなどでスケジュールが完了しなかった場合はnullを返します。 <BR>
 *  エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * 	<CODE>startSCHgetParams</CODE>を実行する。ユーザは登録したパラメータを<CODE>startParams</CODE>に渡す。<BR>
 * 	画面に表示されている最終更新日時を実際にDMCUSTOMERテーブルにある最終更新日時と合わせます。<BR>
 * 	チェックＯＫの場合はDMCUSTOMERテーブルを更新します。<BR>
 * </DIR>
 * <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.パラメータの最終更新日時と出荷先マスタ情報の最終更新日時が一致すること。(排他チェック)<BR>
 *     2.同一荷主コード、出荷先コードの出荷先マスタ情報がデータベースに存在すること。 <BR>
 *     3.荷主コードが荷主マスタ情報に存在すること。 <BR>
 *     4.予定データ取込中、日次更新処理中でないこと。 <BR>
 * <BR>
 *   ＜更新処理＞ <code>CustomerOperator</code>クラスにて以下の処理を行います。 <BR>
 *     詳細は<code>CustomerOperator</code>を参照のこと。 <BR>
 *     -出荷先マスタテーブル(DMCUSTOMER)の更新 <BR>
 *       受け取ったパラメータの内容をもとに出荷先マスタ情報を更新する。 <BR>
 * <BR>
 * <BR>
 * 5. 削除ボタン押下処理（<CODE>startSCH(Connection, Parameter[])</CODE>メソッド）<BR>
 * <DIR>
 * 	画面に入力されている内容をパラメータとして受け取り、出荷先マスタの削除処理を行います。<BR>
 *  エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * 	<CODE>startSCH</CODE>を実行する。ユーザは登録したパラメータを<CODE>startParams</CODE>に渡す。<BR>
 * </DIR>
 * <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.パラメータの最終更新日時と出荷先マスタ情報の最終更新日時が一致すること。(排他チェック)<BR>
 *     2.同一荷主コード、出荷先コードの出荷先マスタ情報がデータベースに存在すること。 <BR>
 *     3.予定データ取込中、日次更新処理中でないこと。 <BR>
 * <BR>
 *   ＜削除処理＞ <code>CustomerOperator</code>クラスにて以下の処理を行います。 <BR>
 *     詳細は<code>CustomerOperator</code>を参照のこと。 <BR>
 *     -出荷先マスタテーブル(DMCUSTOMER)の更新 <BR>
 *       受け取ったパラメータの内容をもとに出荷先マスタ情報を削除する。 <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/01/11</TD><TD>E.Takeda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  $Author: mori $
 */
public class CorrectCustomerMasterSCH extends AbstractMasterSCH
{
	// Class variables -----------------------------------------------

	/**
	 * クラス名(出荷先マスタ修正・削除)
	 */
	private static final String wProcessName = "CorrectCustomerMasterSCH";


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
	public CorrectCustomerMasterSCH()
	{
		wMessage = null;
	}
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 出荷マスタに荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。<BR>
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
	    CustomerSearchKey searchKey = new CustomerSearchKey();
	    CustomerReportFinder customerFinder = new CustomerReportFinder(conn);
	    Customer[] wCustomer = null;

		try
		{
			// 検索条件を設定する。
			// データの検索
			// 状態フラグ＝「削除」以外
			searchKey.setDeleteFlag ("1", "!=");
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (customerFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// 状態フラグ＝「削除」以外
				searchKey.setDeleteFlag("1", "!=");
				// ソート順に登録日時を設定
				searchKey.setLastUpdateDateOrder(1, false);
				
				searchKey.setConsignorCodeCollect("");

				if (customerFinder.search(searchKey) > 0)
				{
					// 登録日時が最も新しい荷主名称を取得します。
				    wCustomer = (Customer[]) customerFinder.getEntities(1);
				    ent.setConsignorCode(wCustomer[0].getConsignorCode());
				}
			}
			customerFinder.close();
			
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			// 必ずCollectionをクローズする
		    customerFinder.close();
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
		// 出荷先ハンドラ類のインスタンス生成
		Customer[] wCustomer = null;
		CustomerSearchKey wKey = new CustomerSearchKey();
		CustomerHandler wHandler = new CustomerHandler(conn);
		
		// 検索条件をセットする
		// 荷主コード
		wKey.setConsignorCode(wParam.getConsignorCode());
		// 出荷先コード
		wKey.setCustomerCode(wParam.getCustomerCode());
		
		wCustomer = (Customer[])wHandler.find(wKey);
		// 出荷先名称
		wParam.setCustomerName(wCustomer[0].getCustomerName1());
		// 郵便番号
		wParam.setPostalCode(wCustomer[0].getPostalCode());
		// 県名
		wParam.setPrefecture(wCustomer[0].getPrefecture());
		// 住所
		wParam.setAddress(wCustomer[0].getAddress());
		// ビル名等
		wParam.setAddress2(wCustomer[0].getAddress2());
		// 連絡先1
		wParam.setContact1(wCustomer[0].getContact1());
		// 連絡先2
		wParam.setContact2(wCustomer[0].getContact2());
		// 連絡先3
		wParam.setContact3(wCustomer[0].getContact3());
		// 最終更新日
		wParam.setLastUpdateDate(wCustomer[0].getLastUpdateDate());
		// 最終使用日
		wParam.setLastUseDate(wCustomer[0].getLastUsedDate());
		viewParam[0] = wParam;
		// 6001013 = 表示しました。
		wMessage = "6001013";
		return viewParam;
	}
	
	/**
	 *	パラメータで取得した出荷先コードの出荷先マスタ情報が更新可能かチェックを行います。<BR>
	 *	<CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、
	 *	出荷先マスタを検索し、パラメータで取得した最終更新日時と出荷先マスタの最終更新日時を比較し、
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
		// 出荷先マスタハンドラ類のインスタンス生成
		CustomerHandler wObj = new CustomerHandler(conn);
		CustomerSearchKey wKey = new CustomerSearchKey();
		Customer customer = null;
		
		// 検索条件をセットする
		// 荷主コード
		wKey.setConsignorCode(wParam.getConsignorCode());
		// 出荷先コード
		wKey.setCustomerCode(wParam.getCustomerCode());
		
		try
		{
			customer = (Customer)wObj.findPrimary(wKey);
			
			if (customer != null && 
			    !customer.getLastUpdateDate().toString().equals(wParam.getLastUpdateDateString()))
			{
				// 6003006=このデータは、他の端末で更新されたため処理できません。
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
		MasterParameter masParam = (MasterParameter) checkParam;
		
		// 作業者コード、パスワードのチェックを行う。
		if (!checkWorker(conn, masParam, true))
		{
			return false;
		}

		// 出荷先マスタ用オペレータクラスのインスタンス生成
		Customer customer = new Customer();
		CustomerOperator cusOperator = new CustomerOperator(conn);

		// 検索条件を設定する
		// 荷主コード
		customer.setConsignorCode(masParam.getConsignorCode());
		// 出荷先コード
		customer.setCustomerCode(masParam.getCustomerCode());

		// 存在チェックを行う
		if (!cusOperator.exist(customer))
		{
			// 6003018=対象データはありませんでした。
			wMessage = "6003018";
			return false;
		}
		return true;
	}
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、出荷先マスタ修正登録のスケジュールを開始します。<BR>
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

		// 商品マスタ用オペレータクラスのインスタンス生成
		Customer wCustomer = new Customer();
		CustomerOperator wOperate = new CustomerOperator(conn);

		// 更新値をセットする
		// 荷主コード
		wCustomer.setConsignorCode(wParam[0].getConsignorCode());
		// 出荷先コード
		wCustomer.setCustomerCode(wParam[0].getCustomerCode());
		// 出荷先名称
		wCustomer.setCustomerName1(wParam[0].getCustomerName());
		// 郵便番号
		wCustomer.setPostalCode(wParam[0].getPostalCode());
		// 県名
		wCustomer.setPrefecture(wParam[0].getPrefecture());
		// 住所
		wCustomer.setAddress(wParam[0].getAddress());
		// ビル名等
		wCustomer.setAddress2(wParam[0].getAddress2());
		// 連絡先1
		wCustomer.setContact1(wParam[0].getContact1());
		// 連絡先2
		wCustomer.setContact2(wParam[0].getContact2());
		// 連絡先3
		wCustomer.setContact3(wParam[0].getContact3());
		// 最終更新処理名
		wCustomer.setLastUpdatePname(wProcessName);
		// 最終使用日時
		wCustomer.setLastUsedDate(new Date());

		// 出荷先マスタ情報更新
		int ret = wOperate.modify(wCustomer);
		
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
	 * 画面から入力された内容をパラメータとして受け取り、出荷先マスタ削除のスケジュールを開始します。<BR>
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

		// 更新可能データチェック処理
		if (!check(conn, masParams))
		{
			return false;
		}

		// 出荷先マスタ用オペレータクラスのインスタンス生成
		Customer wCustomer = new Customer();
		CustomerOperator wOperate = new CustomerOperator(conn);
		
		// 削除条件をセットする
		// 荷主コード
		wCustomer.setConsignorCode(masParams.getConsignorCode());
		// 出荷先コード
		wCustomer.setCustomerCode(masParams.getCustomerCode());
		
		// 出荷先マスタ情報削除
		int ret = wOperate.actualDrop(wCustomer);

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
