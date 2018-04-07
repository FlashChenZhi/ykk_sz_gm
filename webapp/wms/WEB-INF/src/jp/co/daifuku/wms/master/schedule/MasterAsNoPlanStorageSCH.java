//$Id: MasterAsNoPlanStorageSCH.java,v 1.1.1.1 2006/08/17 09:34:20 mori Exp $
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
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.master.autoregist.AutoRegisterWrapper;
import jp.co.daifuku.wms.master.merger.StoragePlanMGWrapper;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.ItemOperator;
import jp.co.daifuku.wms.master.MasterPrefs;
import jp.co.daifuku.wms.master.merger.MergerWrapper;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * WEBＡＳＲＳ予定外入庫設定処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、ＡＳＲＳ予定外入庫作業情報の作成処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド） <BR>
 * <BR>
 * <DIR>
 * 	状態が未開始の荷主コードを在庫情報から検索し、同一の荷主コードしか存在しない場合に、<BR>
 * 	荷主コードを返します。<BR>
 * 	荷主コードが複数存在する場合は、nullを返します。<BR>
 *	<BR>
 *	＜検索条件＞ <BR>
 *	<DIR>
 *     在庫ステータスがセンター在庫<BR>
 *     在庫数が１以上 <BR>
 *     AS/RS在庫 <BR>
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	ＡＳＲＳ倉庫の一覧編集を行います。<BR>
 *	<BR>
 *	＜検索条件＞ <BR>
 *	<DIR>
 *		倉庫テーブル<warehouse>より。 <BR>
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	ハードゾーンの一覧編集を行います。 <BR>
 *	<BR>
 *	＜検索条件＞ <BR>
 *	<DIR>
 *		棚テーブル<shelf>より。 <BR>
 *      倉庫とのリンク情報。 <BR>
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	作業場の一覧編集を行います。 <BR>
 *	<BR>
 *	＜検索条件＞ <BR>
 *	<DIR>
 *		ステーションテーブル<station>より。 <BR>
 *      倉庫とのリンク情報。 <BR>
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	ステーションの一覧編集を行います。 <BR>
 *	<BR>
 *	＜検索条件＞ <BR>
 *	<DIR>
 *		ステーションテーブル<station>より。 <BR>
 *      作業場とのリンク情報。 <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 2.入力ボタン押下処理（<CODE>check()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェック・オーバーフローチェック・重複チェックを行い、チェック結果を返します。 <BR>
 *   在庫情報に同一行No.の該当データが存在しなかった場合はtrueを、条件エラーが発生した場合や該当データが存在した場合はfalseを返します。 <BR>
 *   荷主マスタ、商品マスタの存在チェックを行いデータが存在しなければFalseを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   [パラメータ] *必須入力  +どちらか必須入力 <BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     倉庫* <BR>
 *     ハードゾーン* <BR>
 *     作業場* <BR>
 *     ステーション* <BR>
 *     荷主コード* <BR>
 *     荷主名称 <BR>
 *     入庫予定日* <BR>
 *     商品コード* <BR>
 *     商品名称 <BR>
 *     ケース/ピース区分* <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     入庫ケース数+ <BR>
 *     入庫ピース数+ <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     賞味期限 <BR>
 *   </DIR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   <BR>
 *   -作業者コードチェック処理-(<CODE>AbstructStorageSCH()</CODE>クラス) <BR>
 *   <BR>
 *   -入力値チェック処理-(<CODE>AbstructStorageSCH()</CODE>クラス) <BR>
 *   <BR>
 *   -オーバーフローチェック- <BR>
 *   <BR>
 *   -表示件数チェック- <BR>
 *   <BR>
 *   -重複チェック- <BR>
 *     <DIR>
 *     荷主コード、商品コード、ケース/ピース区分、賞味期限をキーにして重複チェックを行います。<BR>
 *     賞味期限は、在庫を一意にする項目としてWmsParamに定義されている場合、重複チェックのキーに含みます。 <BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * 3.入庫開始ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR><BR>
 * <BR>
 * <DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、ＡＳＲＳ予定外入庫設定処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   [パラメータ] <BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     倉庫* <BR>
 *     ハードゾーン* <BR>
 *     作業場* <BR>
 *     ステーション* <BR>
 *     荷主コード* <BR>
 *     荷主名称 <BR>
 *     入庫予定日* <BR>
 *     商品コード* <BR>
 *     商品名称 <BR>
 *     ケース/ピース区分* <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     入庫ケース数+ <BR>
 *     入庫ピース数+ <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     賞味期限 <BR>
 *     端末No. <BR>
 *   </DIR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   <BR>
 *   -作業者コードチェック処理-(<CODE>AbstructStorageSCH()</CODE>クラス)<BR>
 *   <BR>
 *   -日次更新処理中チェック処理-(<CODE>AbstructStorageSCH()</CODE>クラス) <BR>
 *   <BR>
 *   [更新登録処理] <BR>
 *   <BR>
 *   -入庫棚の決定処理を行う-<BR>
 *   <BR>
 *   -作業情報テーブル(DNWORKINFO)の登録-<BR>
 *   <DIR>
 *     入力内容にて、作業情報の登録処理を行います。 <BR>
 *   </DIR>  
 *   <BR>
 *   -搬送テーブル(CARRYINFO)の登録-<BR>
 *     <DIR>
 *     入力内容にて搬送テーブルの登録を行います。 <BR>
 *     </DIR>  
 *   <BR>
 *   -在庫情報テーブル(DMSTOCK)の登録及び更新-<BR>
 *   <BR>
 *   <DIR>
 *     入力内容にて在庫情報テーブルの登録を行います。 <BR>
 *     [処理条件チェック] <BR>
 *     <BR>
 *     -オーバーフローチェック-<BR>  
 *     <BR>
 *   </DIR>
 *   -パレットテーブル(PALETTE)の登録- <BR>
 *   <BR>
 *</FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/03</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterAsNoPlanStorageSCH extends jp.co.daifuku.wms.asrs.schedule.AsNoPlanStorageSCH
{
	/**
	 * 予定データ補完ラッパクラス
	 */
	private StoragePlanMGWrapper wMGWrapper = null;

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
	 * クラス名(予定外入庫設定（ＡＳＲＳ）)
	 */
	public static String CLASS_STORAGE = "MasterAsNoPlanStorageSCH";

	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:20 $");
	}
	/**
	 * このクラスの初期化を行ないます。
	 */
	public MasterAsNoPlanStorageSCH()
	{
		wMessage = null;

	}

	/**
	 * このクラスを使用してDBの検索・更新を行う場合はこのコンストラクタを使用してください。 <BR>
	 * 各DBの検索・更新に必要なインスタンスを作成します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public MasterAsNoPlanStorageSCH(Connection conn) throws ReadWriteException, ScheduleException
	{
		wMessage = null;
		wMGWrapper = new StoragePlanMGWrapper(conn);
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
		StoragePlan storagePlan = new StoragePlan();
		
		MasterAsScheduleParameter param = (MasterAsScheduleParameter) searchParam;
		
		// 入力値から補完処理を行う
		storagePlan.setConsignorCode(param.getConsignorCode());
		storagePlan.setConsignorName(param.getConsignorName());
		storagePlan.setItemCode(param.getItemCode());
		storagePlan.setItemName1(param.getItemName());
		storagePlan.setEnteringQty(param.getEnteringQty());
		storagePlan.setBundleEnteringQty(param.getBundleEnteringQty());
		storagePlan.setItf(param.getITF());
		storagePlan.setBundleItf(param.getBundleITF());
		
		MergerWrapper merger = new StoragePlanMGWrapper(conn);
		merger.complete(storagePlan);

		// 補完結果を返却パラメータにセットする
		ArrayList tempList = new ArrayList();
		if (storagePlan != null)
		{
			MasterAsScheduleParameter tempParam = (MasterAsScheduleParameter) searchParam;
			
			tempParam.setConsignorName(storagePlan.getConsignorName());
			tempParam.setItemName(storagePlan.getItemName1());
			tempParam.setEnteringQty(storagePlan.getEnteringQty());
			tempParam.setBundleEnteringQty(storagePlan.getBundleEnteringQty());
			tempParam.setITF(storagePlan.getItf());
			tempParam.setBundleITF(storagePlan.getBundleItf());
			
			// 返却するマスタ情報の最終更新日時をセットする
			if (!StringUtil.isBlank(storagePlan.getConsignorCode()))
			{
				MasterParameter mstParam = new MasterParameter();
				mstParam.setConsignorCode(storagePlan.getConsignorCode());
				mstParam.setItemCode(storagePlan.getItemCode());
				tempParam.setConsignorLastUpdateDate(wConsignorOperator.getLastUpdateDate(mstParam));
				tempParam.setItemLastUpdateDate(wItemOperator.getLastUpdateDate(mstParam));
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
	 * @param checkParam 入力内容を持つ<CODE>AsScheduleParameter</CODE>クラスのインスタンス。 <BR>
	 *        AsScheduleParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @param inputParams ためうちエリアの内容を持つ<CODE>AsScheduleParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        AsScheduleParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams)
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
		if(!super.check(conn, checkParam, inputParams)){
			return false;
		}

		// 6001019 = 入力を受け付けました。
		wMessage = "6001019";
		return true;
	}
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、予定外入庫設定スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>AsScheduleParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        AsScheduleParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		// 入力エリアの内容
		MasterAsScheduleParameter[] params = (MasterAsScheduleParameter[]) startParams;

		// 荷主、商品の排他チェックを行う
		for (int i = 0; i < params.length; i++)
		{
			MasterParameter masterParam = new MasterParameter();
			masterParam.setConsignorCode(params[i].getConsignorCode());
			masterParam.setConsignorName (params[i].getConsignorName());
			masterParam.setItemCode(params[i].getItemCode());
			masterParam.setItemName(params[i].getItemName());
			
			if (!checkModifyLastUpdateDate(masterParam, params[i], params[i].getRowNo()))
			{
				return false;
			}
		}
		
		// 既存の処理
		return super.startSCH(conn, startParams);
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
	protected boolean checkModifyLastUpdateDate(MasterParameter param, MasterAsScheduleParameter inputParam, int rowNo)
							throws ReadWriteException
	{
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			// 荷主チェック
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
	 * 補完タイプが上書きまたは空白を補完の場合のチェックを実装します。
	 * また、このメソッドは親クラスのメソッドをオーバーライドしています。
	 * マスタありのチェック以外は親クラスのチェック処理をそのまま呼び出します。
	 * <DIR>
	 * チェック内容<BR>
	 * ・入数が０の場合<BR>
	 *   ケースピース区分にケースは選択不可<BR>
	 *   ケースピース区分が指定なしの場合、ケース数は入力不可<BR>
	 * </DIR>
	 * 
	 * @param  casepieceflag      ケース/ピース区分
	 * @param  enteringqty        ケース入数
	 * @param  caseqty            入庫ケース数
	 * @param  pieceqty           入庫ピース数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	protected boolean storageInputCheck(String casepieceflag, int enteringqty, int caseqty, int pieceqty)
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
						// LBL-W0369=入庫ケース数
						wMessage = "6023506" + wDelim + DispResources.getText("LBL-W0369");
						return false;
					}
				}
			}
		}
		
		// 親クラスのチェックを行う
		if (!super.storageInputCheck(casepieceflag, enteringqty, caseqty, pieceqty))
		{
			return false;
		}
		
		return true;

	}
	// Private methods ---------------------------------------------



}
