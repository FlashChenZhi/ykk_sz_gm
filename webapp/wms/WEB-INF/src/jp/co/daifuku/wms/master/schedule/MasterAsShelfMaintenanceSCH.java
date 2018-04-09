//$Id: MasterAsShelfMaintenanceSCH.java,v 1.1.1.1 2006/08/17 09:34:20 mori Exp $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.autoregist.AutoRegisterWrapper;
import jp.co.daifuku.wms.master.merger.StockMGWrapper;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.ItemOperator;
import jp.co.daifuku.wms.master.MasterPrefs;
import jp.co.daifuku.wms.master.merger.MergerWrapper;
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * WEBＡＳＲＳ在庫メンテナンス（変更）処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、ＡＳＲＳ在庫情報の変更処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド） <BR>
 * <BR>
 * <DIR>
 * 	ＡＳＲＳ倉庫の一覧編集を行います。<BR>
 *	<BR>
 *	＜検索条件＞ <BR>
 *	<DIR>
 *		倉庫テーブル<warehouse>より。 <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 2.登録ボタン押下処理（<CODE>check()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェックを行い、チェック結果を返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   [パラメータ] *必須入力  +どちらか必須入力 <BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     倉庫* <BR>
 *     棚No* <BR>
 *   </DIR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   <BR>
 *   -作業者コードチェック処理-(<CODE>AbstructMaintenanceSCH()</CODE>クラス) <BR>
 *   <BR>
 *   -棚情報チェック- <BR>
 *   <BR>
 *     <DIR>
 *     入力された棚Noにて、棚テーブル(SHELF)情報に登録されているかチェックします。<BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * 3.修正ボタン押下処理（<CODE>check()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェックを行い、チェック結果を返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   [パラメータ] *必須入力  +どちらか必須入力 <BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     倉庫* <BR>
 *     棚No* <BR>
 *   </DIR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   <BR>
 *   -作業者コードチェック処理-(<CODE>AbstructMaintenanceSCH()</CODE>クラス) <BR>
 *   <BR>
 *   -棚情報・在庫情報チェック- <BR>
 *     <DIR>
 *     入力された棚Noにて、棚テーブル(SHELF)情報に登録されているかチェックします。<BR>
 *     入力された棚Noにて、在庫情報(DMSTOCK)情報が存在するかチェックします。 <BR>
 *     </DIR>
 *   <BR>
 *   -棚明細表示- <BR><DIR>
 *     棚明細一覧画面表示を行います。 <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 4.削除ボタン押下処理（<CODE>check()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェックを行い、チェック結果を返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   [パラメータ] *必須入力  +どちらか必須入力 <BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     倉庫* <BR>
 *     棚No* <BR>
 *   </DIR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   <BR>
 *   -作業者コードチェック処理-(<CODE>AbstructMaintenanceSCH()</CODE>クラス) <BR>
 *   <BR>
 *   -棚情報・在庫情報チェック- <BR>
 *     <DIR>
 *     入力された棚Noにて、棚テーブル(SHELF)情報に登録されているかチェックします。<BR>
 *     入力された棚Noにて、在庫情報(DMSTOCK)情報が存在するかチェックします。 <BR>
 *     </DIR>
 *   <BR>
 *   -棚明細表示- <BR>
 *   <DIR>
 *     棚明細一覧画面表示を行います。 <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 5.設定ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR>
 * <BR>
 * <DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、ＡＳＲＳ在庫の変更処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   [パラメータ] <BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     倉庫* <BR>
 *     棚No* <BR>
 *     荷主コード* <BR>
 *     荷主名称 <BR>
 *     商品コード* <BR>
 *     商品名称 <BR>
 *     ケース/ピース区分* <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     在庫ケース数+ <BR>
 *     在庫ピース数+ <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     入庫区分 <BR>
 *     賞味期限 <BR>
 *     入庫日付 <BR>
 *     入庫時間 <BR>
 *     端末No. <BR>
 *   </DIR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   <BR>
 *   -作業者コードチェック処理-(<CODE>AbstructStorageSCH()</CODE>クラス)<BR>
 *   <BR>
 *   -日次更新処理中チェック処理-(<CODE>AbstructStorageSCH()</CODE>クラス) <BR>
 *   <BR>
 *   [登録処理] <BR>
 *   <BR>
 *   -在庫情報重複チェック処理- <BR>
 *    ・指定されたロケーションNoの在庫情報と重複チェックを行う。 <BR>
 *     荷主コード、商品コード、ケース/ピース区分、賞味期限をキーにして重複チェックを行います。<BR>
 *     賞味期限は、在庫を一意にする項目としてWmsParamに定義されている場合、重複チェックのキーに含みます。 <BR>
 *   <BR>
 *   [登録更新処理] <BR><DIR>
 *     -指定されたロケーションNoの在庫情報が空パレット在庫時、対象情報の削除を行います。 <BR>
 *     -在庫情報(DMSTOCK)の登録 <BR>
 *       パラメータの内容を元に在庫情報を登録する。 <BR>
 *     <BR>
 *     -パレットテーブル(PALETTE)の更新 <BR>
 *       -指定されたロケーションNoの在庫情報が空パレット在庫時 <BR>
 *        1.空パレット状態に通常パレットをセットする。 <BR>
 *       -指定されたロケーションNoの在庫情報が空棚時 <BR>
 *        パラメータの内容をもとにパレットテーブルを登録する。 <BR>
 *     <BR>
 *     -棚テーブル(SHELF)の更新 <BR>
 *       -指定されたロケーションNoの在庫情報が空棚時 <BR>
 *        状態フラグに実棚をセットする。 <BR>
 *     <BR>
 *     -実績送信情報テーブル(DNHOSTSEND)の登録 <BR>
 *       パラメータの内容をもとにメンテナンス増の実績送信情報を登録する。 <BR>
 *     <BR>
 *     -作業者実績情報テーブル(DNWORKERRESULT)の更新登録 <BR>
 *       パラメータの内容をもとに作業者実績情報を更新登録する。 <BR>
 *   </DIR>
 *   [修正処理] <BR>
 *   <BR>
 *   -在庫情報重複チェック処理- <BR>
 *    ・指定されたロケーションNoの在庫情報と重複チェックを行う。 <BR>
 *     荷主コード、商品コード、ケース/ピース区分、賞味期限をキーにして重複チェックを行います。（パラメータ情報以外：在庫IDにて）<BR>
 *     賞味期限は、在庫を一意にする項目としてWmsParamに定義されている場合、重複チェックのキーに含みます。 <BR>
 *   <BR>
 *   [修正更新処理] <BR><DIR>
 *     -実績送信情報テーブル(DNHOSTSEND)の登録 <BR>
 *       修正対象の在庫情報をもとに、メンテナンス減の実績情報を登録する。 <BR>
 *     -在庫情報(DMSTOCK)の更新 <BR>
 *       パラメータの内容を元に在庫情報を更新する。 <BR>
 *     <BR>
 *     -実績送信情報テーブル(DNHOSTSEND)の登録 <BR>
 *       パラメータの内容をもとにメンテナンス増の実績送信情報を登録する。 <BR>
 *     <BR>
 *     -作業者実績情報テーブル(DNWORKERRESULT)の更新登録 <BR>
 *       パラメータの内容をもとに作業者実績情報を更新登録する。 <BR>
 *   </DIR>
 *   [削除更新処理] <BR>
 *   <DIR>
 *     -実績送信情報テーブル(DNHOSTSEND)の登録 <BR>
 *       パラメータの在庫情報をもとに、メンテナンス減の実績情報を登録する。 <BR>
 *     <BR>
 *     -在庫情報(DMSTOCK)の削除 <BR>
 *       パラメータの内容を元に在庫情報を削除する。 <BR>
 *     <BR>
 *     -対象ロケーションにて、在庫情報なしの場合 <BR>
 *     <DIR>
 *       -パレットテーブルの削除処理を行います。-<BR>
 *         在庫情報のパレットＩＤにて、パレットテーブルの削除します。 <BR>
 *       <BR>
 *       -棚テーブル(SHELF)の更新処理-<BR>
 *       <DIR>
 *         在庫情報のロケーションNoにて、更新します。 <BR>
 *         1.棚状態に空棚をセットします。 <BR>
 *       </DIR>
 *     </DIR>
 *     <BR>
 *     -作業者実績情報テーブル(DNWORKERRESULT)の更新登録 <BR>
 *       パラメータの内容をもとに作業者実績情報を更新登録する。 <BR></DIR>
 *   </DIR>
 *   <BR>
 *</FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/05</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterAsShelfMaintenanceSCH extends jp.co.daifuku.wms.asrs.schedule.AsShelfMaintenanceSCH
{
	//	Class variables -----------------------------------------------

	/**
	 * 在庫データ補完ラッパクラス
	 */
	private StockMGWrapper wMGWrapper = null;

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


	/**
	 * クラス名(AS在庫メンテナンス)
	 */
	public static String PROCESSNAME = "MasterAsShelfMaintenanceSCH";

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
	public MasterAsShelfMaintenanceSCH()
	{
		wMessage = null;
	}

	/**
	 * このクラスを使用してDBの検索・更新を行う場合はこのコンストラクタを使用してください。 <BR>
	 * 各DBの検索・更新に必要なインスタンスを作成します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public MasterAsShelfMaintenanceSCH(Connection conn) throws ReadWriteException, ScheduleException
	{
		wMessage = null;
		wMGWrapper = new StockMGWrapper(conn);
		wConsignorOperator = new ConsignorOperator(conn);
		wItemOperator = new ItemOperator(conn);
	}

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 在庫情報に荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>StockControlParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>StockControlParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{
		// 荷主初期表示設定を行います。
		AsScheduleParameter tempParam = (AsScheduleParameter) super.initFind(conn, searchParam);
		// 取得した荷主を返却パラメータにセットします。
		MasterAsScheduleParameter wParam = new MasterAsScheduleParameter();
		if (tempParam != null)
		{
			wParam.setConsignorCode(tempParam.getConsignorCode());
			wParam.setConsignorName(tempParam.getConsignorName());
		}
		else
		{
			wParam.setConsignorCode("");
			wParam.setConsignorName("");
		}
		
		// 補完タイプをセットします
		MasterPrefs masterPrefs = new MasterPrefs();
		if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_NONE)
		{
			wParam.setMergeType(MasterShippingParameter.MERGE_TYPE_NONE);
		}
		else if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE)
		{
			wParam.setMergeType(MasterShippingParameter.MERGE_TYPE_OVERWRITE);
		}
		else
		{
			wParam.setMergeType(MasterShippingParameter.MERGE_TYPE_FILL_EMPTY);
		}
		return wParam;
	}

	/**
	 * 画面へ表示するデータを取得します。表示ボタン押下時などの操作に対応するメソッドです。<BR>
	 * 必要に応じて、各継承クラスで実装してください。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>クラスを実装したインスタンス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		Stock stock = new Stock();
		
		MasterAsScheduleParameter param = (MasterAsScheduleParameter) searchParam;
		
		// 入力値から補完処理を行う
		stock.setConsignorCode(param.getConsignorCode());
		stock.setConsignorName(param.getConsignorName());
		stock.setItemCode(param.getItemCode());
		stock.setItemName1(param.getItemName());
		stock.setEnteringQty(param.getEnteringQty());
		stock.setBundleEnteringQty(param.getBundleEnteringQty());
		stock.setItf(param.getITF());
		stock.setBundleItf(param.getBundleITF());
		
		MergerWrapper merger = new StockMGWrapper(conn);
		merger.complete(stock);

		// 補完結果を返却パラメータにセットする
		ArrayList tempList = new ArrayList();
		if (stock != null)
		{
			MasterAsScheduleParameter tempParam = (MasterAsScheduleParameter) searchParam;
			
			tempParam.setConsignorName(stock.getConsignorName());
			tempParam.setItemName(stock.getItemName1());
			tempParam.setEnteringQty(stock.getEnteringQty());
			tempParam.setBundleEnteringQty(stock.getBundleEnteringQty());
			tempParam.setITF(stock.getItf());
			tempParam.setBundleITF(stock.getBundleItf());
			
			// 返却するマスタ情報の最終更新日時をセットする
			if (!StringUtil.isBlank(stock.getConsignorCode()))
			{
				MasterParameter mstParam = new MasterParameter();
				mstParam.setConsignorCode(stock.getConsignorCode());
				mstParam.setItemCode(stock.getItemCode());
			}
			tempList.add(tempParam);
		}
		MasterAsScheduleParameter[] result = new MasterAsScheduleParameter[tempList.size()];
		tempList.toArray(result);
		return result;
	}

	/** 
	 * 画面から入力された溜めうちエリアの内容をパラメータとして受け取り、 <BR>
	 * 必須チェック・オーバーフローチェック・重複チェックを行い、チェック結果を返します。 <BR>
	 * 在庫情報に同一行No.の該当データが存在しなかった場合はtrueを、 <BR>
	 * 条件エラーが発生した場合や該当データが存在した場合は排他エラーとし、falseを返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンス。 <BR>
	 *        IdmControlParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @param inputParams ためうちエリアの内容を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        IdmControlParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ScheduleException, ReadWriteException
	{
	    int iRet = 0;
	    wConsignorOperator = new ConsignorOperator(conn);
		wItemOperator = new ItemOperator(conn);
		
		// 入力エリアの内容
		AsScheduleParameter wParam = (AsScheduleParameter) checkParam;
		
		MasterParameter masterParam = new MasterParameter();
		masterParam.setConsignorCode(wParam.getConsignorCode());
		masterParam.setConsignorName (wParam.getConsignorName());
		masterParam.setItemCode(wParam.getItemCode());
		masterParam.setItemName(wParam.getItemName());
		
		iRet = wConsignorOperator.checkConsignorMaster(masterParam);
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

		// 既存の入力チェックを行う
		if (!super.check(conn, checkParam))
		{
			return false;
		}

		// 6001019 = 入力を受け付けました。
		wMessage = "6001019";
		return true;
	}
	
	/**
	 * 補完タイプが上書きまたは空白を補完の場合のチェックを実装します。
	 * また、このメソッドは親クラスのメソッドをオーバーライドしています。
	 * マスタありのチェック以外は親クラスのチェック処理をそのまま呼び出します。
	 * <DIR>
	 * チェック内容<BR>
	 * ・入数が０の場合<BR>
	 *  ケースピース区分にケースは選択不可<BR>
	 *  ケースピース区分が指定なしの場合、ケース数は入力不可<BR>
	 * </DIR>
	 * 
	 * @param  casepieceflag      ケース/ピース区分
	 * @param  enteringqty        ケース入数
	 * @param  caseqty            ケース数
	 * @param  pieceqty           ピース数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	protected boolean stockInputCheck(String casepieceflag, int enteringqty, int caseqty, int pieceqty)
		throws ReadWriteException
	{
		// 補完タイプによって異なるチェックを行います
		MasterPrefs masterPrefs = new MasterPrefs();
		if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE
		 || masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_FILL_EMPTY)
		{
			if (enteringqty <= 0)
			{
				// ケース/ピース区分がケースの場合
				if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
				{
					// 6023345=ケース入数が0の場合、ケース／ピース区分にケースは選択できません。
					wMessage = "6023345";
					return false;
				}				
				// ケース/ピース区分が指定なしの場合
				else if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
				{
					// 入庫ケース数が1以上
					if (caseqty > 0)
					{
						// 6023506=ケース入数が0の場合、{0}は入力できません。
						// LBL-W0371=在庫ケース数
						wMessage = "6023506" + wDelim + DispResources.getText("LBL-W0371");
						return false;
					}
				}
			}
		}
		
		// 親クラスのチェックを行う
		if (!super.stockInputCheck(casepieceflag, enteringqty, caseqty, pieceqty))
		{
			return false;
		}
		
		return true;


	}

}