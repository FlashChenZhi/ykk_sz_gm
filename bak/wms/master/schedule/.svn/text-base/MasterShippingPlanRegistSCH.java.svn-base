package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.master.merger.MergerWrapper;
import jp.co.daifuku.wms.master.merger.ShippingPlanMGWrapper;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.CustomerOperator;
import jp.co.daifuku.wms.master.operator.ItemOperator;
import jp.co.daifuku.wms.master.operator.SupplierOperator;
import jp.co.daifuku.wms.master.MasterPrefs;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;


/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * WEB出荷予定データ登録を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷予定データ登録処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド） <BR><BR><DIR>
 *   出荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。 <BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 * <BR>
 *   ＜検索条件＞ <BR><DIR>
 *     状態フラグ：削除以外
 * </DIR>
 * </DIR>
 * <BR>
 * 2.次へボタン押下処理 (<CODE>nextCheck()</CODE>メソッド） <BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、作業者コード、パスワードのチェック結果を返します。 <BR>
 *   作業者コード、パスワードの内容が正しい場合はtrueを返します。パラメータの内容に問題がある場合はfalseを返します。
 *   荷主マスタ、出荷先マスタの存在チェックを行い<BR>
 *   データが存在しなければFalseを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力 <BR><DIR>
 *     作業者コード * <BR>
 *     パスワード * <BR></DIR>
 * </DIR>
 * <BR>  
 * 3.入力ボタン押下処理（<CODE>check()</CODE>メソッド） <BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェック・オーバーフローチェック・重複チェックを行い、チェック結果を返します。 <BR>
 *   出荷予定情報に同一行No.の該当データが存在しなかった場合はtrueを、条件エラーが発生した場合や該当データが存在した場合はfalseを返します。 <BR>
 *   荷主マスタ、出荷先マスタ、商品マスタ、仕入先マスタの存在チェックを行い<BR>
 *   データが存在しなければFalseを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   荷主コード、出荷予定日、出荷先コード、出荷伝票No.、出荷伝票行No.をキーを重複チェックを行います。  <BR>
 *   また、状態フラグが未開始または削除の同一行No.が存在した場合は重複エラーとせず、trueを返します。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力 <BR><DIR>
 *     荷主コード* <BR>
 *     出荷予定日* <BR>
 *     出荷先コード* <BR>
 *     出荷伝票No.* <BR>
 *     出荷伝票行No.* <BR>
 *     商品コード <BR>
 *     商品名称 <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     予定ケース数 <BR>
 *     予定ピース数 <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     TC/DC区分 <BR>
 *     仕入先コード <BR>
 *     仕入先名称 <BR>
 *     入荷伝票No. <BR>
 *     入荷伝票行No. <BR></DIR>
 * <BR>
 *   ＜必須チェック内容＞ <BR>
 *     1.TC/DC区分：クロスまたはTC指定時、仕入先コードが入力されていること。 <BR>
 *     2.TC/DC区分：クロス指定時、入荷伝票No.、入荷伝票行No.が入力されていること。 <BR>
 *     3.ケース数に値が入っていた場合にはケース入数が1以上であること。 <BR>
 * <BR>
 * </DIR>
 * <BR>
 * 4.登録ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、出荷予定データ登録処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力 <BR><DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     荷主コード* <BR>
 *     出荷予定日* <BR>
 *     出荷先コード* <BR>
 *     出荷伝票No.* <BR>
 *     出荷伝票行No.* <BR>
 *     商品コード* <BR>
 *     商品名称 <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     予定ケース数 <BR>
 *     予定ピース数 <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     TC/DC区分* <BR>
 *     仕入先コード <BR>
 *     仕入先名称 <BR>
 *     入荷伝票No. <BR>
 *     入荷伝票行No. <BR></DIR>
 * <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR><DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR></DIR>
 *     2.同一行No.の出荷予定情報がデータベースに存在しないこと。（排他チェック） <BR><DIR>
 *       ※状態フラグが未開始または削除の出荷予定情報は重複対象データとしない。</DIR>
 * <BR>
 *   ＜更新処理＞ <BR>
 *     ※デッドロックを防ぐため、作業情報、出荷予定情報、在庫情報の順番でテーブルの更新を行う。 <BR>
 *     1.荷主コード、出荷予定日、出荷先コード、出荷伝票No.、出荷伝票行No.をキーに出荷予定情報を検索する。 <BR>
 *     2.状態フラグが未開始または完了の出荷予定情報が存在した場合、該当データの状態フラグを削除に更新する。 <BR>
 *     3.更新した出荷予定情報の出荷予定一意キーに紐づく作業情報の状態フラグを削除に更新する。 <BR>
 *     4.更新した作業情報の在庫IDに紐づく在庫情報の在庫数、引当数を出荷予定数減算した値に更新する。 <BR>
 * <BR>
 *   ＜登録処理＞ <BR>
 *     -作業情報テーブル(DNWORKINFO)の登録 <BR>
 *       今回登録した出荷予定情報および在庫情報の内容をもとに作業情報を登録する。 <BR>
 * <BR>
 *     -出荷予定情報テーブル(DNSHIPPINGPLAN)の更新登録 <BR>
 *       受け取ったパラメータの内容をもとに出荷予定情報を登録する。 <BR>
 * <BR>
 *     ※パラメータのTC/DC区分がTCまたはクロスの場合、以下の処理を行う。 <BR>
 *     -次作業情報テーブル(DNNEXTPROC)の更新登録 <BR>
 *       1.出荷予定日、出荷先コード、出荷伝票No.、出荷伝票行No.をキーに次作業情報を検索する。 <BR>
 *       2.次作業情報が存在した場合、次作業情報の更新を行う。 <BR><DIR>
 *         出荷予定一意キーを今回登録した出荷予定情報の一意キーに更新する。 <BR>
 *         作業予定数を出荷予定情報の出荷予定数に更新する。 <BR>
 *         最終更新日時を現在のシステム日時に更新する。 <BR></DIR>
 *       3.次作業情報が存在しない場合、今回登録した出荷予定情報をもとに次作業情報の登録を行う。 <BR><DIR>
 *         入荷伝票No.、入荷伝票行No.をキーに入荷予定情報を検索する。 <BR>
 *         入荷予定情報が存在した場合、その情報で予定一意キー、入荷予定一意キーをセットする。 <BR>
 *         入荷予定情報が存在しない場合、予定一意キー、入荷予定一意キーにはnullをセットする。 <BR></DIR>
 * <BR>
 *     -在庫情報テーブル(DMSTOCK)の登録 <BR>
 *       今回登録した出荷予定情報の内容をもとに在庫情報を登録する。 <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/04</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterShippingPlanRegistSCH extends jp.co.daifuku.wms.shipping.schedule.ShippingPlanRegistSCH
{
	/**
	 * 荷主マスタオペレータクラス
	 */
	private ConsignorOperator wConsignorOperator = null;
	
	/**
	 * 仕入先マスタオペレータクラス
	 */
	private SupplierOperator wSupplierOperator = null;
	
	/**
	 * 出荷先マスタオペレータクラス
	 */
	private CustomerOperator wCustomerOperator = null;
	
	/**
	 * 商品マスタオペレータクラス
	 */
	private ItemOperator wItemOperator = null;  
	// Class variables -----------------------------------------------
	/**
	 * 処理名
	 */
	protected String wDelim = MessageResource.DELIM;
	
	private static String wProcessName = "MasterShippingPlanRegistSCH";
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:20 $" );
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public MasterShippingPlanRegistSCH()
	{
		wMessage = null;
	}
	
	/**
	 * このクラスを使用してDBの検索・更新を行う場合はこのコンストラクタを使用してください。 <BR>
	 * 各DBの検索・更新に必要なインスタンスを作成します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public MasterShippingPlanRegistSCH(Connection conn) throws ReadWriteException, ScheduleException
	{
		wMessage = null;
		wConsignorOperator = new ConsignorOperator(conn);
		wSupplierOperator = new SupplierOperator(conn);
		wCustomerOperator = new CustomerOperator(conn);
		wItemOperator = new ItemOperator(conn);
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 出荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>ShippingParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>ShippingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{
		// 荷主初期表示データを取得します
		ShippingParameter tempParam = (ShippingParameter) super.initFind(conn, searchParam);
		// 取得した荷主データを返却パラメータにセットします
		MasterShippingParameter retParam = new MasterShippingParameter();
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
		ShippingPlan shipPlan = new ShippingPlan();
		
		MasterShippingParameter param = (MasterShippingParameter) searchParam;
		
		// 入力値から補完処理を行う
		shipPlan.setConsignorCode(param.getConsignorCode());
		shipPlan.setConsignorName(param.getConsignorName());
		shipPlan.setCustomerCode(param.getCustomerCode());
		shipPlan.setCustomerName1(param.getCustomerName());
		shipPlan.setItemCode(param.getItemCode());
		shipPlan.setItemName1(param.getItemName());
		shipPlan.setSupplierCode(param.getSupplierCode());
		shipPlan.setSupplierName1(param.getSupplierName());
		shipPlan.setEnteringQty(param.getEnteringQty());
		shipPlan.setBundleEnteringQty(param.getBundleEnteringQty());
		shipPlan.setItf(param.getITF());
		shipPlan.setBundleItf(param.getBundleITF());
		
		MergerWrapper merger = new ShippingPlanMGWrapper(conn);
		merger.complete(shipPlan);

		// 補完結果を返却パラメータにセットする
		ArrayList tempList = new ArrayList();
		if (shipPlan != null)
		{
			MasterShippingParameter tempParam = (MasterShippingParameter) searchParam;
			tempParam.setConsignorName(shipPlan.getConsignorName());
			tempParam.setCustomerName(shipPlan.getCustomerName1());
			tempParam.setSupplierName(shipPlan.getSupplierName1());
			tempParam.setItemName(shipPlan.getItemName1());
			tempParam.setEnteringQty(shipPlan.getEnteringQty());
			tempParam.setBundleEnteringQty(shipPlan.getBundleEnteringQty());
			tempParam.setITF(shipPlan.getItf());
			tempParam.setBundleITF(shipPlan.getBundleItf());
			
			// 返却するマスタ情報の最終更新日時をセットする
			if (!StringUtil.isBlank(shipPlan.getConsignorCode()))
			{
				MasterParameter mstParam = new MasterParameter();
				mstParam.setConsignorCode(shipPlan.getConsignorCode());
				mstParam.setCustomerCode(shipPlan.getCustomerCode());
				mstParam.setItemCode(shipPlan.getItemCode());
				mstParam.setSupplierCode(shipPlan.getSupplierCode());
				tempParam.setConsignorLastUpdateDate(wConsignorOperator.getLastUpdateDate(mstParam));
				tempParam.setCustomerLastUpdateDate(wCustomerOperator.getLastUpdateDate(mstParam));
				tempParam.setSupplierLastUpdateDate(wSupplierOperator.getLastUpdateDate(mstParam));
				tempParam.setItemLastUpdateDate(wItemOperator.getLastUpdateDate(mstParam));
			}
				
			tempList.add(tempParam);
		}
		MasterShippingParameter[] result = new MasterShippingParameter[tempList.size()];
		tempList.toArray(result);
		return result;
	}

	/** 
	 * 画面から入力された溜めうちエリアの内容をパラメータとして受け取り、 <BR>
	 * 必須チェック・オーバーフローチェック・重複チェックを行い、チェック結果を返します。 <BR>
	 * 出荷予定情報に同一行No.の該当データが存在しなかった場合はtrueを、 <BR>
	 * 条件エラーが発生した場合や該当データが存在した場合は排他エラーとし、falseを返します。 <BR>
	 * 状態フラグが未開始または完了の同一行No.が存在した場合は排他エラーとせず、trueを返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンス。 <BR>
	 *        ShippingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @param inputParams ためうちエリアの内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        ShippingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean check( Connection conn, Parameter checkParam, Parameter[] inputParams )
		throws ScheduleException,ReadWriteException
	{
		// 入力エリアの内容
		MasterShippingParameter param = (MasterShippingParameter) checkParam;

		MasterParameter masterParam = new MasterParameter();
		masterParam.setConsignorCode(param.getConsignorCode());
		masterParam.setConsignorName (param.getConsignorName());
		masterParam.setCustomerCode(param.getCustomerCode());
		masterParam.setCustomerName (param.getCustomerName());
		
		// 荷主と出荷先に変更がないかをチェックする
		if (!checkModifyLastUpdateDate(masterParam, param, 0))
		{
			return false;
		}
		
		masterParam.setSupplierCode(param.getSupplierCode());
		masterParam.setSupplierName (param.getSupplierName());
		masterParam.setItemCode(param.getItemCode());
		masterParam.setItemName(param.getItemName());
		int iRet = 0;
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
		
		iRet = 	wCustomerOperator.checkCustomerMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
			if(iRet == MasterParameter.RET_NG){
				// 6023458 = 出荷先コードがマスタに登録されていません。
				wMessage = "6023458";
				return false;
			} else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023472=指定された出荷先名称はすでに使用されているため登録できません。
				wMessage = "6023472";
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
		
		iRet = 	wSupplierOperator.checkSupplierMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
			if(iRet == MasterParameter.RET_NG){
				// 6023457 = 仕入先コードがマスタに登録されていません。
				wMessage = "6023457";
				return false;
			} else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023471=指定された仕入先名称はすでに使用されているため登録できません。
				wMessage = "6023471";
				return false; 
			}
		}		
		
		MasterPrefs masterPrefs = new MasterPrefs();
		ShippingParameter wCheckParam = (ShippingParameter) checkParam;

		if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE)
		{
			wCheckParam.setLineCheckFlag(false);
		}
		
		if (wCheckParam.getPlanCaseQty() > 0 && wCheckParam.getEnteringQty() == 0)
		{
			// 6023506=ケース入数が0の場合、{0}は入力できません。
			// LBL-W0385=ホスト予定ケース数
			wMessage = "6023506" + wDelim + DispResources.getText("LBL-W0385");
			return false;
		}
		
		if(!super.check(conn, wCheckParam, inputParams))
		{
			return false;
		}
		// 6001019 = 入力を受け付けました。
		wMessage = "6001019";
		return true;
	}
	
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、作業者コード、パスワードのチェック結果を返します。 <BR>
	 * 作業者コード、パスワードの内容が正しい場合はtrueを返します。<BR>
	 * パラメータの内容に問題がある場合はfalseを返します。<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param checkParam 入力内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンス。 <BR>
	 *        ShippingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean nextCheck( Connection conn, Parameter checkParam ) throws ReadWriteException,ScheduleException
	{
		// マスタチェック→既存チェック
		ShippingParameter wParam = (ShippingParameter) checkParam;
		MasterParameter masterParam = new MasterParameter();
		masterParam.setConsignorCode(wParam.getConsignorCode());
		masterParam.setConsignorName (wParam.getConsignorName());
		masterParam.setCustomerCode(wParam.getCustomerCode());
		masterParam.setCustomerName (wParam.getCustomerName());

		int iRet = 0;
		
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
		
		iRet = wCustomerOperator.checkCustomerMaster(masterParam);
		if (!StringUtil.isBlank(masterParam.getCustomerCode())
			&& iRet != MasterParameter.RET_OK)
		{
			if(iRet == MasterParameter.RET_NG){
				// 6023458 = 出荷先コードがマスタに登録されていません。
				wMessage = "6023458";
				return false;
			} else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023472=指定された出荷先名称はすでに使用されているため登録できません。
				wMessage = "6023472";
				return false; 
			}
		}
		
		if (!super.nextCheck(conn, checkParam))
		{
			return false;
		}
		return true;
	}
		
	/**
	 * 画面から入力された内容をパラメータとして受け取り、出荷予定データ登録スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        ShippingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean startSCH( Connection conn, Parameter[] startParams ) throws ReadWriteException, ScheduleException
	{
		// 入力エリアの内容
		MasterShippingParameter[] params = (MasterShippingParameter[]) startParams;

		// 荷主、出荷先、商品、仕入先の排他チェックを行う
		for (int i = 0; i < params.length; i++)
		{
			MasterParameter masterParam = new MasterParameter();
			masterParam.setConsignorCode(params[i].getConsignorCode());
			masterParam.setConsignorName (params[i].getConsignorName());
			masterParam.setCustomerCode(params[i].getCustomerCode());
			masterParam.setCustomerName (params[i].getCustomerName());
			masterParam.setSupplierCode(params[i].getSupplierCode());
			masterParam.setSupplierName (params[i].getSupplierName());
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
	protected boolean checkModifyLastUpdateDate(MasterParameter param, MasterShippingParameter inputParam, int rowNo)
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
			// 出荷先チェック
			if (!StringUtil.isBlank(param.getConsignorCode())
			 && !StringUtil.isBlank(param.getCustomerCode()))
			{
				param.setLastUpdateDate(inputParam.getCustomerLastUpdateDate());
				if (wCustomerOperator.isModify(param))
				{
     		        // 6023489=指定された{0}は、他の端末で更新されたため入力できません。
					wMessage = "6023489" + wDelim + DisplayText.getText("CHK-W0027");
					return false;
				}
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
		if (!StringUtil.isBlank(param.getConsignorCode())
		 && !StringUtil.isBlank(param.getSupplierCode()))
		{
			// 仕入先チェック
			param.setLastUpdateDate(inputParam.getSupplierLastUpdateDate());
			if (wSupplierOperator.isModify(param))
			{
			    // 6023490=No.{0} 指定された{1}は、他の端末で更新されたため登録できません。
			    wMessage = "6023490" + wDelim + rowNo + wDelim + DisplayText.getText("CHK-W0026");
			    return false;
			}
		}
		return true;
	}
	
}
//end of class
