package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.MasterPrefs;
import jp.co.daifuku.wms.master.autoregist.AutoRegisterWrapper;
import jp.co.daifuku.wms.master.merger.InventoryMGWrapper;
import jp.co.daifuku.wms.master.merger.MergerWrapper;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.ItemOperator;
import jp.co.daifuku.wms.storage.entity.TotalStock;
import jp.co.daifuku.wms.storage.schedule.InventoryMaintenanceSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

/**
 * Designer : M.Takeuchi <BR>
 * Maker : M.Takeuchi <BR>
 * <BR>
 * 棚卸し処理を行うためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、棚卸し情報処理を行います。<BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR> 
 * <BR>
 * <DIR>
 *   1-1 在庫情報より荷主コードの件数を取得します。<BR> 
 *   <BR>
 *   <DIR>
 *   [検索条件] <BR><DIR> 
 *     状態フラグがセンター在庫 <BR>
 *     在庫数が１以上 <BR>
 *     在庫情報.エリアNo=エリアマスタ情報.エリアNo <BR>
 *     エリアマスタ情報.エリア区分=AS/RS以外 <BR></DIR>
 *   </DIR><BR>
 *   1-2 棚卸作業情報より荷主コードの件数を取得します。<BR> 
 *   <DIR>
 *   [検索条件] <BR><DIR> 
 *   処理フラグが未確定 <BR></DIR>
 *   </DIR><BR>
 *   1-3 在庫情報及び棚卸作業情報にて 荷主コードが１件のみ存在した場合、該当する荷主コードを返します。 <BR>
 *	     該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR> 
 *   <BR>
 * </DIR>
 * <BR>
 * 2.次へボタン押下処理（<CODE>nextCheck()</CODE>メソッド）<BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、作業者コード・パスワードのチェック、該当データ存在チェック結果を返します。 <BR>
 *   該当データが在庫情報又は棚卸し作業情報に存在し、作業者コード、パスワードの内容が正しい場合はtrueを返します。<BR>
 *   該当データが在庫情報又は棚卸し作業情報に存在しない場合、またはパラメータの内容に問題がある場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力<BR><DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     荷主コード* <BR>
 *     開始棚No <BR>
 *     終了棚No <BR></DIR>
 *   ＜システム不可条件＞ <BR><DIR>
 *     状態フラグがセンター在庫 <BR>
 *     在庫数が１以上 <BR></DIR>
 * </DIR>
 * <BR>
 * 3.次へボタン押下処理（<CODE>query()</CODE>メソッド）<BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力<BR><DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     荷主コード* <BR>
 *     開始棚No <BR>
 *     終了棚No <BR></DIR>
 * <BR>
 *   ＜返却データ＞ <BR><DIR>
 *     荷主コード <BR>
 *     荷主名称 <BR>
 *     開始棚No <BR>
 *     終了棚No <BR>
 *     棚卸作業有無 <BR>
 *     棚卸作業有無名称 <BR>
 *     ロケーションNo. <BR>
 *     商品コード <BR>
 *     商品名称 <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     棚卸総数 <BR>
 *     棚卸ケース数(棚卸在庫数/ケース入数) <BR>
 *     棚卸ピース数(棚卸在庫数%ケース入数) <BR>
 *     在庫総数 <BR>
 *     在庫ケース数(在庫数/ケース入数) <BR>
 *     在庫ピース数(在庫数%ケース入数) <BR>
 *     作業No. <BR>
 *     最終更新日時 <BR>
 *     賞味期限日 <BR></DIR>
 *   ＜表示順＞ <BR><DIR>
 *     棚Noの昇順、商品コードの昇順、賞味期限日の昇順 <BR></DIR>
 *   ＜表示条件＞ <BR><DIR>
 *     棚卸作業有無 <BR><DIR>
 *       棚卸し作業情報に対象情報が存在する場合、「登録」を返却する。 <BR>
 *       存在しない場合、「未」を返却する。 <BR></DIR>
 *     棚卸ケース数・ピース数 <BR><DIR>
 *       棚卸し作業情報に対象情報が存在する場合、棚卸作業情報より 編集する。 <BR>
 *       存在しない場合、０を編集する。 <BR></DIR></DIR>
 *     在庫ケース数・ピース数 <BR><DIR>
 *       棚卸し作業情報に対象情報が存在する場合、棚卸作業情報より 編集する。 <BR>
 *       存在しない場合、在庫情報より編集する。 <BR></DIR>
 *   ＜在庫情報 検索・集約条件＞ <BR><DIR>
 *       入力された荷主コードと一致。
 *       入力された開始棚Noから終了棚Noの範囲内。
 *       状態フラグがセンター在庫 <BR>
 *       在庫数が１以上 <BR>
 *       荷主コード・棚No・商品コード・賞味期限日にて集約。 <BR></DIR>
 *   ＜棚卸作業情報 検索条件＞<BR><DIR>
 *       入力された荷主コードと一致。
 *       入力された開始棚Noから終了棚Noの範囲内。
 *       状態フラグが未確定のみ <BR></DIR>
 *   ＜エリアマスタ情報 検索条件＞<BR><DIR>
 *       在庫情報のエリアNoと一致。
 *       エリア区分がAS/RS以外。<BR></DIR>
 * <BR></DIR>
 * 4.入力ボタン押下処理：新規入力時（<CODE>check()</CODE>メソッド） <BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェック・オーバーフローチェック・重複チェック・排他チェック結果を返します。 <BR>
 *   処理が正常に完了した場合はためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
 *   条件エラーが発生した場合や排他エラーが発生した場合はnullを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜入力パラメータ＞ *必須入力 <BR><DIR>
 *     荷主コード* <BR>
 *     荷主名称 <BR>
 *     ロケーションNo.* <BR>
 *     商品コード* <BR>
 *     商品名称 <BR>
 *     ケース入数* <BR>
 *     在庫ケース数* <BR>
 *     在庫ピース数* <BR>
 *     棚卸ケース数 <BR>
 *     棚卸ピース数 <BR>
 *     賞味期限日 <BR>
 *     ためうち行No* <BR></DIR>
 *   ＜チェックパラメータ＞ *必須入力 <BR><DIR>
 *     荷主コード* <BR>
 *     荷主名称* <BR>
 *     棚卸作業有無* <BR>
 *     商品コード* <BR>
 *     商品名称* <BR>
 *     ロケーションNo.* <BR>
 *     ケース入数* <BR>
 *     ボール入数* <BR>
 *     在庫ケース数* <BR>
 *     在庫ピース数* <BR>
 *     棚卸ケース数* <BR>
 *     棚卸ピース数* <BR>
 *     賞味期限日* <BR>
 *     ためうち行No* <BR></DIR>
 * <BR>
 *   ＜必須チェック内容＞ <BR><DIR>
 *     1.在庫情報との重複チェック <BR>
 *     2.棚卸作業情報との重複チェック <BR>
 *     3.ためうちエリアとの重複チェック <BR>
 *     4.棚卸ケース数が１以上の場合、ケース入数が１以上である事。 <BR>
 *     5.棚卸数（合計）が１以上である事。 <BR>
 *     6.商品コード・棚Noが入力されている事。 <BR>
 *     7.指定された棚がAS/RS棚情報に存在しないこ。Shelfテーブルチェック-(<CODE>isAsrsLocation()</CODE>メソッド)<BR>
 *     ※重複チェック 荷主コード・棚No・商品コード・賞味期限日 <BR></DIR>
 * <BR></DIR>
 * <BR>
 * 5.入力ボタン押下処理：一覧表示からの修正時（<CODE>startSCHgetParams()</CODE>メソッド） <BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェック・オーバーフローチェック・重複チェック・排他チェック結果を返します。 <BR>
 *   処理が正常に完了した場合はためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
 *   条件エラーが発生した場合や排他エラーが発生した場合はnullを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜入力パラメータ＞ *必須入力 <BR><DIR>
 *     荷主コード* <BR>
 *     荷主名称 <BR>
 *     ロケーションNo.* <BR>
 *     商品コード* <BR>
 *     商品名称 <BR>
 *     ケース入数* <BR>
 *     在庫ケース数* <BR>
 *     在庫ピース数* <BR>
 *     棚卸ケース数 <BR>
 *     棚卸ピース数 <BR>
 *     賞味期限日 <BR>
 *     ためうち行No* <BR></DIR>
 *   ＜返却データ＞ <BR><DIR>
 *     荷主コード <BR>
 *     荷主名称 <BR>
 *     棚卸作業有無 <BR>
 *     棚卸作業有無名称 <BR>
 *     ロケーションNo. <BR>
 *     商品コード <BR>
 *     商品名称 <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     棚卸総数 <BR>
 *     棚卸ケース数(棚卸在庫数/ケース入数) <BR>
 *     棚卸ピース数(棚卸在庫数%ケース入数) <BR>
 *     在庫総数 <BR>
 *     在庫ケース数(在庫数/ケース入数) <BR>
 *     在庫ピース数(在庫数%ケース入数) <BR>
 *     賞味期限日 <BR>
 *     作業No. <BR>
 *     最終更新日時 <BR>
 *     ためうち行No* <BR>
 *     ※入力チェックOK時、在庫情報より 最新の在庫ケース数・ピース数を再取得し返却データにセットする。 <BR></DIR>
 * <BR>
 *   ＜必須チェック内容＞ <BR><DIR>
 *     ＜在庫情報あり＞ <BR><DIR>
 *       1.ケース入数が０以下の場合、棚卸ケース数は０又は未入力のみ可能とする。 <BR>
 *       2.棚卸数（合計）値が０以上であること。 <BR>
 *       3.棚卸数（合計）値が引当数以上であること。 <BR>
 *       4.指定された棚がAS/RS棚情報に存在しないこ。Shelfテーブルチェック-(<CODE>isAsrsLocation()</CODE>メソッド)<BR></DIR>
 *     ＜在庫情報なし＞ <BR><DIR>
 *       1.ケース入数が０以下の場合、棚卸ケース数は０又は未入力のみ可能とする。 <BR>
 *       2.棚卸数（合計）値が１以上であること。 <BR></DIR>
 *   <BR></DIR>
 * </DIR>
 * <BR>
 * 6.削除ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、棚卸作業情報の削除処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力 <BR><DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     荷主コード* <BR>
 *     商品コード* <BR>
 *     棚No.* <BR>
 *     賞味期限日* <BR>
 *     ためうち行No* <BR>
 *     端末No* <BR>
 *     棚卸作業有無* <BR>
 *     ボタン種別：削除* <BR></DIR>
 * <BR>
 *   注）棚卸作業有無が「登録」の場合のみ、下記チェック及び更新処理を行う。 <BR>
 *   棚卸作業有無が「未」の場合は、新規登録分のため、棚卸作業情報は登録されていない。 正常完了としてtrueを返します。 <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.荷主コード・棚No・商品コード・賞味期限日の棚卸作業情報テーブルがデータベースに存在すること。 <BR>
 * <BR>
 *   ＜更新処理＞ <BR>
 *     -棚卸作業情報テーブル(DNINVENTORYCHECK)の削除 <BR>
 *       パラメータの荷主コード・棚No・商品コード・賞味期限日に紐づく棚卸作業情報の削除処理を行う。 <BR>
 *       処理フラグを削除に更新する。 <BR>
 * <BR></DIR>
 * <BR>
 * 7.棚卸データ更新ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、棚卸作業情報の更新処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力 <BR><DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     荷主コード* <BR>
 *     荷主名称* <BR>
 *     棚卸作業有無* <BR>
 *     商品コード* <BR>
 *     商品名称 <BR>
 *     棚No.* <BR>
 *     ケース入数* <BR>
 *     ボール入数* <BR>
 *     在庫ケース数* <BR>
 *     在庫ピース数* <BR>
 *     棚卸ケース数* <BR>
 *     棚卸ピース数* <BR>
 *     賞味期限日* <BR>
 *     ためうち行No* <BR>
 *     端末No* <BR>
 *     ボタン種別：登録・修正* <BR></DIR>
 * <BR>
 *   ＜修正登録処理＞ <BR>
 * <DIR>
 *   ＜処理条件チェック＞ <BR>
 *     1.ケース入数が０以下の場合、棚卸ケース数は０又は未入力のみ可能とする。 <BR>
 *     ＜在庫情報あり＞ <BR><DIR>
 *       2.棚卸数（合計）値が０以上であること。 <BR>
 *       3.棚卸数（合計）値が引当数以上であること。 <BR></DIR>
 *     ＜在庫情報なし＞ <BR><DIR>
 *       2.棚卸数（合計）値が１以上であること。 <BR></DIR>
 * <BR>
 *   ＜更新処理＞ <BR>
 *     ＜棚卸作業有無が「未」時＞ <BR><DIR>
 *       -棚卸作業情報テーブル(DNINVENTORYCHECK)の登録 <BR>
 *       1.パラメータの内容を元に棚卸作業情報を新規登録する。 <BR></DIR>
 *     ＜棚卸作業有無が「登録」時＞ <BR><DIR>
 *       -棚卸作業情報テーブル(DNINVENTORYCHECK)の更新 <BR>
 *       1.パラメータの内容を元に棚卸作業情報を更新する。 <BR><DIR>
 *       棚卸ケース数・ケース数の更新を行う。 <BR>
 *       作業コード・作業者名称の更新を行う。 <BR></DIR></DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/12</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterInventoryMaintenanceSCH extends InventoryMaintenanceSCH
{
	// Class variables -----------------------------------------------
	/**
	 * クラス名(棚卸し)
	 */
	public static String CLASS_NAME = "MasterInventoryMaintenanceSCH";

	/**
	 * 予定データ補完ラッパクラス
	 */
	private InventoryMGWrapper wMGWrapper = null;

	/**
	 * マスタ自動登録ラッパクラス
	 */
	private AutoRegisterWrapper wAutoRegistWrapper = null;

	/**
	 * 荷主マスタオペレータクラス
	 */
	private ConsignorOperator wConsignorOperator = null;
	
	/**
	 * 商品マスタオペレータクラス
	 */
	private ItemOperator wItemOperator = null;
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:20 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public MasterInventoryMaintenanceSCH()
	{
		wMessage = null;
	}

	/**
	 * このクラスを使用してDBの検索・更新を行う場合はこのコンストラクタを使用してください。 <BR>
	 * 各DBの検索・更新に必要なインスタンスを作成します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public MasterInventoryMaintenanceSCH(Connection conn) throws ReadWriteException, ScheduleException
	{
		wMessage = null;
		wMGWrapper = new InventoryMGWrapper(conn);
		wAutoRegistWrapper = new AutoRegisterWrapper(conn);
		wConsignorOperator = new ConsignorOperator(conn);
		wItemOperator = new ItemOperator(conn);
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 在庫情報及び棚卸作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR>
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{
		// 荷主初期表示設定を行います。
		StorageSupportParameter tempParam = (StorageSupportParameter) super.initFind(conn, searchParam);
		// 取得した荷主を返却パラメータにセットします。
		MasterStorageSupportParameter retParam = new MasterStorageSupportParameter();
		if (tempParam != null)
		{
			retParam.setConsignorCode(tempParam.getConsignorCode());
			retParam.setConsignorName(tempParam.getConsignorName());
		}
		else
		{
			retParam.setConsignorCode("");
			retParam.setConsignorName("");
		}
		
		// 補完タイプをセットします
		MasterPrefs masterPrefs = new MasterPrefs();
		if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_NONE)
		{
			retParam.setMergeType(MasterShippingParameter.MERGE_TYPE_NONE);
		}
		else if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE)
		{
			retParam.setMergeType(MasterShippingParameter.MERGE_TYPE_OVERWRITE);
		}
		else
		{
			retParam.setMergeType(MasterShippingParameter.MERGE_TYPE_FILL_EMPTY);
		}
		return retParam;
	}
	
	/** 
	 * パラメータ入力内容チェック処理を行います。チェック処理の実装はこのインタフェースを実装するクラスによって異なります。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * 又、本処理が使用されない場合に、呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
	 * パラメータの内容が正しい場合はtrueを返します。 <BR>
	 * @throws ScheduleException このメソッドが呼び出された場合に通知されます。 <BR>
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ReadWriteException, ScheduleException
	{
	    int iRet = 0;
		// 入力エリアの内容
		MasterStorageSupportParameter param = (MasterStorageSupportParameter) checkParam;
		// ためうちエリアの内容
		StorageSupportParameter[] paramList = (StorageSupportParameter[]) inputParams;
		
		MasterParameter masterParam = new MasterParameter();
		masterParam.setConsignorCode(param.getConsignorCode());
		masterParam.setConsignorName (param.getConsignorName());
		
		// 荷主に変更がないかをチェックする
		if (!checkModifyLastUpdateDate(masterParam, param, 0))
		{
			return false;
		}
		
		masterParam.setItemCode(param.getItemCode());
		masterParam.setItemName(param.getItemName());
		iRet = 	wConsignorOperator.checkConsignorMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
		    if(iRet == MasterParameter.RET_NG){
				// 6023456 = 荷主コードがマスタに登録されていません。
				wMessage = "6023456";
				return false;
		    } else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023470=指定された荷主名称はすでに使用されているため登録できません。
				wMessage = "6023470";
				return false; 
		    }
		}
		iRet = 	wItemOperator.checkItemMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
		    if(iRet == MasterParameter.RET_NG){
				// 6023459 = 商品コードがマスタに登録されていません。
				wMessage = "6023459";
				return false;
		    } else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023473=指定された商品名称はすでに使用されているため登録できません。
				wMessage = "6023473";
				return false; 
		    }
		}
		
		// ケース数入力時、ケース入数必須チェック 
		if (param.getInventoryCheckCaseQty() > 0 && param.getEnteringQty() == 0)
		{
			// 6023506=ケース入数が0の場合、{0}は入力できません。
			// LBL-W0092=棚卸ケース数
			wMessage = "6023506" + wDelim + DispResources.getText("LBL-W0092");
			return false;
		}
		// 既存の入力チェックを行う
		if (!super.check(conn, checkParam, inputParams))
		{
			return false;
		}
		
		// 6001019 = 入力を受け付けました。
		wMessage = "6001019";
		return true;
	}
	
	/**
	 * パラメータの内容が正しいかチェックを行います。<CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、 <BR>
	 * パラメータ入力内容チェック処理を行います。チェック処理の実装はこのインタフェースを実装するクラスによって異なります。 <BR>
	 * パラメータの内容が正しい場合はtrueを返します。 <BR>
	 * パラメータの内容に問題がある場合はfalseを返します。<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。 <BR>
	 * このメソッドは2画面遷移の1画面目から2画面目への遷移時の入力チェックを実装します。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * 又、本処理が使用されない場合に、呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param checkParam 入力チェックを行う内容が含まれたパラメータクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
	    int iRet = 0;
		// checkParamの型を変換
		StorageSupportParameter param = (StorageSupportParameter) checkParam;
		
		MasterParameter masterParam = new MasterParameter();
		masterParam.setConsignorCode(param.getConsignorCode());
		masterParam.setConsignorName (param.getConsignorName());
		
		iRet = 	wConsignorOperator.checkConsignorMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
		    if(iRet == MasterParameter.RET_NG){
				// 6023456 = 荷主コードがマスタに登録されていません。
				wMessage = "6023456";
				return false;
		    } else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023470=指定された荷主名称はすでに使用されているため登録できません。
				wMessage = "6023470";
				return false; 
		    }
		}
		
		// 既存の入力チェックを行う
		if (!super.nextCheck(conn, checkParam))
		{
			return false;
		}
		
		return true;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * 又、本処理が使用されない場合に、呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param locale 地域コード、画面表示用にローカライズした値を取得するために使用します。
	 * @param searchParam 表示データ取得条件を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンス。
	 *         <CODE>StorageSupportParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。
	 * @return 検索結果を持つ<CODE>StorageSupportParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          nullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		MasterStorageSupportParameter param = (MasterStorageSupportParameter) searchParam;
		if (param.getSearchMode() == MasterStorageSupportParameter.SEARCH_MODE_LIST)
		{
			StorageSupportParameter[] returnParam = (StorageSupportParameter[])super.query(conn, searchParam);
			
			Vector vec = new Vector();
			if (returnParam != null && returnParam.length > 0)
			{
				for (int i = 0; i < returnParam.length; i ++)
				{
					MasterStorageSupportParameter viewParam = new MasterStorageSupportParameter();
					MasterParameter mstParam = new MasterParameter();
					mstParam.setConsignorCode(returnParam[i].getConsignorCode());
					// 荷主コード
					viewParam.setConsignorCode(returnParam[i].getConsignorCode());
					// 荷主コード最終使用日
					viewParam.setConsignorLastUpdateDate(wConsignorOperator.getLastUpdateDate(mstParam));
					// 荷主名称
					viewParam.setConsignorName(returnParam[i].getConsignorName());
					// 開始棚No
					viewParam.setFromLocation(returnParam[i].getFromLocation());
					// 終了棚No
					viewParam.setToLocation(returnParam[i].getToLocation());
					// 棚卸作業有無（なし）
					viewParam.setInventoryKind(returnParam[i].getInventoryKind());
					// 棚卸作業有無名称（未）
					viewParam.setInventoryKindName(returnParam[i].getInventoryKindName());
					// ロケーションNo.
					viewParam.setLocation(returnParam[i].getLocation());
					// 商品コード
					viewParam.setItemCode(returnParam[i].getItemCode());
					// 商品名称
					viewParam.setItemName(returnParam[i].getItemName());
					// ケース入数
					viewParam.setEnteringQty(returnParam[i].getEnteringQty());
					// ボール入数
					viewParam.setBundleEnteringQty(returnParam[i].getBundleEnteringQty());
					// 棚卸総数
					viewParam.setTotalInventoryCheckQty(returnParam[i].getTotalInventoryCheckQty());
					// 棚卸ケース数(棚卸在庫数/ケース入数)
					viewParam.setInventoryCheckCaseQty(returnParam[i].getInventoryCheckCaseQty());
					// 棚卸ピース数(棚卸在庫数%ケース入数)
					viewParam.setInventoryCheckPieceQty(returnParam[i].getInventoryCheckPieceQty());
					// 在庫総数
					viewParam.setTotalStockQty(returnParam[i].getTotalStockQty());
					// 在庫ケース数(在庫数/ケース入数)
					viewParam.setTotalStockCaseQty(returnParam[i].getTotalStockCaseQty());
					// 在庫ピース数(在庫数%ケース入数)
					viewParam.setTotalStockPieceQty(returnParam[i].getTotalStockPieceQty());
					// 賞味期限日
					viewParam.setUseByDate(returnParam[i].getUseByDate());
					// 在庫ID
					viewParam.setStockID(returnParam[i].getStockID());
					// 最終更新日時
					viewParam.setLastUpdateDate(returnParam[i].getLastUpdateDate());
					// 作業No.
					viewParam.setJobNo(returnParam[i].getJobNo());
					viewParam.setITF(returnParam[i].getITF());
					viewParam.setBundleITF(returnParam[i].getBundleITF());
					vec.add(viewParam);
				}
			}
			MasterStorageSupportParameter[] retParam = new MasterStorageSupportParameter[vec.size()];
			vec.copyInto(retParam);
			return retParam;
		}
		else
		{
			InventoryCheck inventory = new InventoryCheck();
			
			// 入力値から補完処理を行う
			inventory.setConsignorCode(param.getConsignorCode());
			inventory.setConsignorName(param.getConsignorName());
			inventory.setItemCode(param.getItemCode());
			inventory.setItemName1(param.getItemName());
			inventory.setEnteringQty(param.getEnteringQty());
			inventory.setBundleEnteringQty(param.getBundleEnteringQty());
			inventory.setItf(param.getITF());
			inventory.setBundleItf(param.getBundleITF());
			
			MergerWrapper merger = new InventoryMGWrapper(conn);
			merger.complete(inventory);

			// 補完結果を返却パラメータにセットする
			ArrayList tempList = new ArrayList();
			if (inventory != null)
			{
				MasterStorageSupportParameter tempParam = (MasterStorageSupportParameter) searchParam;
				tempParam.setConsignorName(inventory.getConsignorName());
				tempParam.setItemName(inventory.getItemName1());
				tempParam.setEnteringQty(inventory.getEnteringQty());
				tempParam.setBundleEnteringQty(inventory.getBundleEnteringQty());
				tempParam.setITF(inventory.getItf());
				tempParam.setBundleITF(inventory.getBundleItf());
				
				// 返却するマスタ情報の最終更新日時をセットする
				if (!StringUtil.isBlank(inventory.getConsignorCode()))
				{
					MasterParameter mstParam = new MasterParameter();
					mstParam.setConsignorCode(inventory.getConsignorCode());
					mstParam.setItemCode(inventory.getItemCode());
					tempParam.setConsignorLastUpdateDate(wConsignorOperator.getLastUpdateDate(mstParam));
					tempParam.setItemLastUpdateDate(wItemOperator.getLastUpdateDate(mstParam));
				}
				tempList.add(tempParam);
			}
			MasterStorageSupportParameter[] result = new MasterStorageSupportParameter[tempList.size()];
			tempList.toArray(result);
			return result;
		}
	}
	
	/** 
	 * パラメータの内容が正しいかチェックを行います。<CODE>startParams</CODE>で指定されたパラメータにセットされた内容に従い、 <BR>
	 * パラメータ入力内容チェック処理を行います。チェック処理の実装はこのインタフェースを実装するクラスによって異なります。 <BR>
	 * 本処理が正常完了後、ためうちエリア出力用のデータをデータベース及び入力内容から取得して返します。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * 又、本処理が使用されない場合に、呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
	 * @throws ScheduleException このメソッドが呼び出された場合に通知されます。 <BR>
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// 入力エリアの内容
		MasterStorageSupportParameter[] params = (MasterStorageSupportParameter[]) startParams;

		// 荷主、出荷先、商品、仕入先の排他チェックを行う
		for (int i = 0; i < params.length; i++)
		{
			MasterParameter masterParam = new MasterParameter();
			masterParam.setConsignorCode(params[i].getConsignorCode());
			masterParam.setConsignorName (params[i].getConsignorName());
			masterParam.setItemCode(params[i].getItemCode());
			masterParam.setItemName(params[i].getItemName());
			
			if (!checkModifyLastUpdateDate(masterParam, params[i], params[i].getRowNo()))
			{
				return null;
			}
		}
		
		// 既存の処理
		return super.startSCHgetParams(conn, startParams);
	}

	/**
	 * 他端末で、マスタ情報の更新が行われたかをチェックします。
	 * 
	 * @param param チェックする各コード・名称のセットされた
	 * @param inputParam 画面からの入力値（最終更新日時を取得するために使用）
	 * @param rowNo 行No.
	 * @return 他端末で更新されていない：true。他端末で更新された：false
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected boolean checkModifyLastUpdateDate(MasterParameter param, MasterStorageSupportParameter inputParam, int rowNo)
							throws ReadWriteException
	{
		// 検索回数を減らすため、行No.が０か１の場合のみチェックを行います。
		// rowNo = 0 : 入力ボタン押下時のチェック
		// rowNo = 1 : 登録ボタン押下時のチェック
		if (rowNo == 0 || rowNo == 1)
		{
			// 荷主チェック
			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				param.setLastUpdateDate(inputParam.getConsignorLastUpdateDate());
				if (wConsignorOperator.isModify(param))
				{
     		        // 6023489=指定された{0}は、他の端末で更新されたため入力できません。
					wMessage = "6023489" + wDelim + DisplayText.getText("CHK-W0025");
					return false;
				}
			}
		}
		
		// 荷主チェック
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			param.setLastUpdateDate(inputParam.getConsignorLastUpdateDate());
			if (wConsignorOperator.isModify(param))
			{
 		        // 6023490=No.{0} 指定された{1}は、他の端末で更新されたため登録できません。
				wMessage = "6023490" + wDelim + rowNo + wDelim + DisplayText.getText("CHK-W0025");
				return false;
			}
		}
		if (!StringUtil.isBlank(param.getConsignorCode())
		 && !StringUtil.isBlank(param.getItemCode()))
		{
			// 商品チェック
			param.setLastUpdateDate(inputParam.getItemLastUpdateDate());
			if (wItemOperator.isModify(param))
			{
 		        // 6023490=No.{0} 指定された{1}は、他の端末で更新されたため登録できません。
				wMessage = "6023490" + wDelim + rowNo + wDelim + DisplayText.getText("CHK-W0023");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 棚卸し情報のセットを行います。<BR>
	 * 次へボタン押下時に呼び出されます。<BR>
	 * 画面内容に紐付く棚卸し情報を画面表示用パラメータにセットして返却します。<BR>
	 * @param  sparam         パラメータ
	 * @param  inventoryCheck 棚卸し情報
	 * @return パラメータ
	 */
	protected StorageSupportParameter MakeToInventory(StorageSupportParameter param, InventoryCheck inventoryCheck)
	{
		StorageSupportParameter retParam = super.MakeToInventory(param, inventoryCheck);

		retParam.setITF(inventoryCheck.getItf());
		retParam.setBundleITF(inventoryCheck.getBundleItf());
		
		return retParam;
		
	}
	
	/**
	 * 在庫情報の検索を行います。<BR>
	 * 次へボタン押下時に呼び出されます。<BR>
	 * 画面内容に紐付く在庫情報に基づいて、在庫情報を再度検索し在庫数の集約を行います。<BR>
	 * @param  conn        コネクション
	 * @param  param       パラメータ
	 * @param  stockData   在庫情報
	 * @return パラメータ
	 * @throws ReadWriteException
	 */
	protected StorageSupportParameter MakeToStock(Connection conn, StorageSupportParameter param, Stock stockData) throws ReadWriteException
	{
		StorageSupportParameter retParam = super.MakeToStock(conn, param,stockData);

		retParam.setITF(stockData.getItf());
		retParam.setBundleITF(stockData.getBundleItf());
		
		return retParam;
		
	}
	
	/**
	 * 棚卸し情報の登録処理を行います。<BR>
	 * @param  conn        コネクション
	 * @param  inputParam パラメータ
	 * @param  stock       在庫情報
	 * @throws ReadWriteException
	 */
	protected void inventoryCreate(Connection conn, Parameter inputParam, TotalStock stock)
		throws ReadWriteException, InvalidStatusException, DataExistsException
	{
		// searchParamの型を変換
		StorageSupportParameter param = (StorageSupportParameter) inputParam;
		
		// 棚卸し情報登録オブジェクト
		InventoryCheckHandler invcheckHandler = new InventoryCheckHandler(conn);
		InventoryCheck inventoryCheck = new InventoryCheck();
		
		// 作業No
		SequenceHandler sequence = new SequenceHandler(conn);
		inventoryCheck.setJobNo(sequence.nextJobNo());
		// 在庫ID
		inventoryCheck.setStockId("");
		// エリアNo
		inventoryCheck.setAreaNo("");
		// 棚No
		inventoryCheck.setLocationNo(param.getLocation());
		// 処理フラグ（未確定）
		inventoryCheck.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		// 棚卸結果バーコード
		inventoryCheck.setInvcheckBcr(param.getItemCode());
		// 商品コード
		inventoryCheck.setItemCode(param.getItemCode());
		// 商品名称
		inventoryCheck.setItemName1(param.getItemName());
		// 在庫数
		inventoryCheck.setStockQty((int)stock.getTotalStockQty());
		// 引当数 2005.12.07 Ishizaki changed.
		inventoryCheck.setAllocationQty((int)stock.getTotalAllocationQty());
		// 作業予定数
		inventoryCheck.setPlanQty(0);
		// 棚卸結果数（棚卸しケース数 * ケース入数 + 棚卸しピース数）
		inventoryCheck.setResultStockQty(param.getInventoryCheckCaseQty() * param.getEnteringQty() + param.getInventoryCheckPieceQty());
		// ケース・ピース区分
		inventoryCheck.setCasePieceFlag(InventoryCheck.CASEPIECE_FLAG_NOTHING);
		// 入庫日
		inventoryCheck.setInstockDate("");
		// 最終出庫日
		inventoryCheck.setLastShippingDate("");
		// 賞味期限日
		inventoryCheck.setUseByDate(param.getUseByDate());
		// ロットNo
		inventoryCheck.setLotNo("");
		// 予定情報コメント
		inventoryCheck.setPlanInformation("");
		// 荷主コード
		inventoryCheck.setConsignorCode(param.getConsignorCode());
		// 荷主名称
		inventoryCheck.setConsignorName(param.getConsignorName());
		// 仕入先コード
		inventoryCheck.setSupplierCode("");
		// 仕入先名称
		inventoryCheck.setSupplierName1("");
		// ケース入数
		inventoryCheck.setEnteringQty(param.getEnteringQty());
		// ボール入数
		inventoryCheck.setBundleEnteringQty(param.getBundleEnteringQty());
		// ケースＩＴＦ
		inventoryCheck.setItf(param.getITF());
		// ボールＩＴＦ
		inventoryCheck.setBundleItf(param.getBundleITF());
		// 作業者コード
		inventoryCheck.setWorkerCode(param.getWorkerCode());
		// 作業者名称
		inventoryCheck.setWorkerName(getWorkerName(conn, param.getWorkerCode()));
		// 端末No
		inventoryCheck.setTerminalNo(param.getTerminalNumber());
		// 登録日時 Handlerにて行うため セット不要。
		// 登録処理名
		inventoryCheck.setRegistPname(CLASS_NAME);
		// 最終更新日時 Handlerにて行うため セット不要。
		// 最終更新処理名
		inventoryCheck.setLastUpdatePname(CLASS_NAME);
		invcheckHandler.create(inventoryCheck);
	}
	
}
//end of class