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
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.master.MasterPrefs;
import jp.co.daifuku.wms.master.merger.MergerWrapper;
import jp.co.daifuku.wms.master.merger.RetrievalPlanMGWrapper;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.CustomerOperator;
import jp.co.daifuku.wms.master.operator.ItemOperator;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanRegistSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

/**
 * Designer : M.Takeuchi <BR>
 * Maker : M.Takeuchi  <BR>
 * <BR>
 * WEB出庫予定データ登録を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、出庫予定データ登録処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド） <BR><BR><DIR>
 *   出庫予定情報に荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。 <BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 * <BR>
 *   ＜検索条件＞ <BR>
 *     <DIR>
 *     状態フラグ：削除以外
 *     </DIR>
 * </DIR>
 * <BR>  
 * 2.入力ボタン押下処理（<CODE>check()</CODE>メソッド） <BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェック・オーバーフローチェック・重複チェック・桁数チェックを行い、チェック結果を返します。 <BR>
 *   出庫予定情報に同一行No.の該当データが存在しなかった場合はtrueを、条件エラーが発生した場合や該当データが存在した場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   荷主コード、出庫予定日、商品コード、ケースピース区分、出庫棚、オーダーNoをキーにして重複チェックを行います。 <BR>
 *   また、状態フラグが削除の同一行No.が存在した場合は重複エラーとせず、trueを返します。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力 <BR>
 * <DIR>
 *     荷主コード* <BR>
 *     荷主名称 <BR>
 *     出庫予定日* <BR>
 *     商品コード* <BR>
 *     商品名称 <BR>
 *     オーダーNo <BR>
 *     出荷先コード <BR>
 *     出荷先名称 <BR>
 *     伝票No <BR>
 *     行No <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     予定ケース数 <BR>
 *     予定ピース数 <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     ロケーションNo <BR>
 *     ケースピース区分* <BR>
 * </DIR>
 * <BR>
 *   ＜必須チェック内容＞ <BR>
 *     1.ケース数に値が入っていた場合にはケース入数が1以上であること。 <BR>
 *     2.予定ケース、予定ピースのトータルで入力値が1以上であること。 <BR>
 *     3.指定なしまたはケース品の場合、ピース数はケース入数以下であること。<BR>
 *     4.在庫パッケージなしの場合、出庫棚に入力があること。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * 3.登録ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、出庫予定データ登録処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力 <BR>
 * <DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     荷主コード* <BR>
 *     荷主名称* <BR>
 *     出庫予定日* <BR>
 *     商品コード* <BR>
 *     商品名称* <BR>
 *     オーダーNo* <BR>
 *     出荷先コード* <BR>
 *     出荷先名称* <BR>
 *     伝票No* <BR>
 *     行No* <BR>
 *     ケース入数* <BR>
 *     ボール入数* <BR>
 *     予定ケース数* <BR>
 *     予定ピース数* <BR>
 *     ケースITF* <BR>
 *     ボールITF* <BR>
 *     ロケーションNo* <BR>
 *     ケースピース区分* <BR>
 *     登録区分* <BR>
 *     端末No.* <BR>
 *     ためうち行No.* <BR>
 * </DIR>
 * <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR>
 *       <DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR>
 *       </DIR>
 *     2.同一行No.の出庫予定情報がデータベースに存在しないこと。（排他チェック） <BR>
 *       <DIR>
 *       ※状態フラグが未開始、削除の出庫予定情報は重複対象データとしない。
 *       </DIR>
 * <BR>
 *   ＜処理詳細＞ <BR>
 *     1.荷主コード、出庫予定日、商品コード、ケースピース区分、出庫棚、オーダーNoをキーに <BR>
 *       出庫予定情報<CODE>(DNRETRIEVALPLAN)</CODE>を検索する。 <BR>
 *     2.上記１より出庫予定情報検索にて未開始データがある場合は引当処理クラス<CODE>（RetrievalAllocator）</CODE>の <BR>
 *       Cancelメソッドをコールし引当の解除処理を行います。 <BR>
 *     3.画面からのパラメータより新規に出庫予定情報<CODE>(DNRETRIEVALPLAN)</CODE>を登録します。 <BR>
 *     4.引当処理クラス<CODE>(RetrievalAllocator)</CODE>のAllocateメソッドをコールし引当処理を行います。 <BR>
 *     5.上記１～４を入力一行毎に処理していく中で引当数が予定数に満たない場合は処理中断します。 <BR>
 *     RollBack処理を行う。（今回設定分を全て元に戻します。）<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/21</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterRetrievalPlanRegistSCH extends RetrievalPlanRegistSCH
{

	// Class variables -----------------------------------------------
	/**
	 * デリミタ
	 */
	protected String wDelim = MessageResource.DELIM;

	/**
	 * 処理名
	 */
	private static String wProcessName = "MasterRetrievalPlanRegistSCH";

	/**
	 * 荷主マスタオペレータクラス
	 */
	private ConsignorOperator wConsignorOperator = null;
	
	/**
	 * 出荷先マスタオペレータクラス
	 */
	private CustomerOperator wCustomerOperator = null;
	
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
	public MasterRetrievalPlanRegistSCH()
	{
		wMessage = null;
	}

	/**
	 * このクラスを使用してDBの検索・更新を行う場合はこのコンストラクタを使用してください。 <BR>
	 * 各DBの検索・更新に必要なインスタンスを作成します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public MasterRetrievalPlanRegistSCH(Connection conn) throws ReadWriteException, ScheduleException
	{
		wMessage = null;
		wConsignorOperator = new ConsignorOperator(conn);
		wCustomerOperator = new CustomerOperator(conn);
		wItemOperator = new ItemOperator(conn);
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 作業情報に荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>RetrievalSupportParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>RetrievalSupportParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{
		// 荷主初期表示設定を行います。
		RetrievalSupportParameter tempParam = (RetrievalSupportParameter) super.initFind(conn, searchParam);
		// 取得した荷主を返却パラメータにセットします。
		MasterRetrievalSupportParameter wParam = new MasterRetrievalSupportParameter();
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
		RetrievalPlan retrievalPlan = new RetrievalPlan();
		
		MasterRetrievalSupportParameter param = (MasterRetrievalSupportParameter) searchParam;
		
		// 入力値から補完処理を行う
		retrievalPlan.setConsignorCode(param.getConsignorCode());
		retrievalPlan.setConsignorName(param.getConsignorName());
		retrievalPlan.setCustomerCode(param.getCustomerCode());
		retrievalPlan.setCustomerName1(param.getCustomerName());
		retrievalPlan.setItemCode(param.getItemCode());
		retrievalPlan.setItemName1(param.getItemName());
		retrievalPlan.setEnteringQty(param.getEnteringQty());
		retrievalPlan.setBundleEnteringQty(param.getBundleEnteringQty());
		retrievalPlan.setItf(param.getITF());
		retrievalPlan.setBundleItf(param.getBundleITF());
		
		MergerWrapper merger = new RetrievalPlanMGWrapper(conn);
		merger.complete(retrievalPlan);

		// 補完結果を返却パラメータにセットする
		ArrayList tempList = new ArrayList();
		if (retrievalPlan != null)
		{
			MasterRetrievalSupportParameter tempParam = (MasterRetrievalSupportParameter) searchParam;
			
			tempParam.setConsignorName(retrievalPlan.getConsignorName());
			tempParam.setCustomerName(retrievalPlan.getCustomerName1());
			tempParam.setItemName(retrievalPlan.getItemName1());
			tempParam.setEnteringQty(retrievalPlan.getEnteringQty());
			tempParam.setBundleEnteringQty(retrievalPlan.getBundleEnteringQty());
			tempParam.setITF(retrievalPlan.getItf());
			tempParam.setBundleITF(retrievalPlan.getBundleItf());
			
			// 返却するマスタ情報の最終更新日時をセットする
			if (!StringUtil.isBlank(retrievalPlan.getConsignorCode()))
			{
				MasterParameter mstParam = new MasterParameter();
				mstParam.setConsignorCode(retrievalPlan.getConsignorCode());
				mstParam.setCustomerCode(retrievalPlan.getCustomerCode());
				mstParam.setItemCode(retrievalPlan.getItemCode());
				tempParam.setConsignorLastUpdateDate(wConsignorOperator.getLastUpdateDate(mstParam));
				tempParam.setCustomerLastUpdateDate(wCustomerOperator.getLastUpdateDate(mstParam));
				tempParam.setItemLastUpdateDate(wItemOperator.getLastUpdateDate(mstParam));
			}
			tempList.add(tempParam);
		}
		MasterRetrievalSupportParameter[] result = new MasterRetrievalSupportParameter[tempList.size()];
		tempList.toArray(result);
		return result;
	}
	
	/** 
	 * 画面から入力された内容をパラメータとして受け取り、該当データの存在チェックの結果を返します。<BR>
	 * 出庫予定情報に同一行No.の該当データが存在しなかった場合はtrueを、 <BR>
	 * 条件エラーが発生した場合や該当データが存在した場合はfalseを返します。 <BR>
	 * 状態フラグが未開始または完了の同一行No.が存在した場合は重複エラーとせず、trueを返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>RetrievalSupportParameter</CODE>クラスのインスタンス。 <BR>
	 *         RetrievalSupportParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean check(Connection conn, Parameter checkParam) throws ScheduleException,ReadWriteException
	{

	    int iRet = 0;
		// 入力エリアの内容
		RetrievalSupportParameter wParam = (RetrievalSupportParameter) checkParam;
		
		MasterParameter masterParam = new MasterParameter();
		masterParam.setConsignorCode(wParam.getConsignorCode());
		masterParam.setConsignorName (wParam.getConsignorName());
		masterParam.setCustomerCode(wParam.getCustomerCode());
		masterParam.setCustomerName(wParam.getCustomerName());
		masterParam.setItemCode(wParam.getItemCode());
		masterParam.setItemName(wParam.getItemName());
		
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
		
		iRet = wCustomerOperator.checkCustomerMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
		    if (iRet == MasterParameter.RET_NG)
		    {
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
		
		// 6001019 = 入力を受け付けました。
		wMessage = "6001019";
		return true;
	}

	/**
	 * 画面から入力された溜めうちエリアの内容をパラメータとして受け取り、 <BR> 
	 * 必須チェック・オーバーフローチェック・重複チェックを行い、チェック結果を返します。 <BR>
	 * 出庫予定情報に同一行No.の該当データが存在しなかった場合はtrueを、 <BR>
	 * 条件エラーが発生した場合や該当データが存在した場合は排他エラーとし、falseを返します。 <BR>
	 * 状態フラグが削除の同一行No.が存在した場合は排他エラーとせず、trueを返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>RetrievalSupportParameter</CODE>クラスのインスタンス。 <BR>
	 *         RetrievalSupportParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @param inputParams ためうちエリアの内容を持つ<CODE>RetrievalSupportParameter</CODE>クラスのインスタンスの配列。<BR>
	 *         RetrievalSupportParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ScheduleException,ReadWriteException
	{
	    int iRet = 0;
		// 入力エリアの内容
		RetrievalSupportParameter wParam = (RetrievalSupportParameter) checkParam;
		
		MasterParameter masterParam = new MasterParameter();
		masterParam.setConsignorCode(wParam.getConsignorCode());
		masterParam.setConsignorName (wParam.getConsignorName());
		masterParam.setCustomerCode(wParam.getCustomerCode());
		masterParam.setCustomerName(wParam.getCustomerName());
		masterParam.setItemCode(wParam.getItemCode());
		masterParam.setItemName(wParam.getItemName());
		
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
		if (!StringUtil.isBlank(masterParam.getCustomerCode()))
		{
			iRet = wCustomerOperator.checkCustomerMaster(masterParam);
			if (iRet != MasterParameter.RET_OK)
			{
			    if (iRet == MasterParameter.RET_NG)
			    {
					// 6023458 = 出荷先コードがマスタに登録されていません。
					wMessage = "6023458";
					return false;
			    } else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
					// 6023472=指定された出荷先名称はすでに使用されているため登録できません。
					wMessage = "6023472";
					return false; 
			    }
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
		
		MasterPrefs masterPrefs = new MasterPrefs();
		RetrievalSupportParameter wCheckParam = (RetrievalSupportParameter) checkParam;

		if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE)
		{
			wCheckParam.setLineCheckFlag(false);
		}
		// 既存の入力チェックを行う
		if(!super.check(conn, wCheckParam, inputParams)){
			return false;
		}
		
		// 6001019 = 入力を受け付けました。
		wMessage = "6001019";
		return true;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、出庫予定データ登録スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>RetrievalSupportParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         RetrievalSupportParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// 入力エリアの内容
		MasterRetrievalSupportParameter[] params = (MasterRetrievalSupportParameter[]) startParams;

		// 荷主、商品の排他チェックを行う
		for (int i = 0; i < params.length; i++)
		{
			MasterParameter masterParam = new MasterParameter();
			masterParam.setConsignorCode(params[i].getConsignorCode());
			masterParam.setConsignorName (params[i].getConsignorName());
			masterParam.setItemCode(params[i].getItemCode());
			masterParam.setItemName(params[i].getItemName());
			masterParam.setCustomerCode(params[i].getCustomerCode());
			masterParam.setCustomerName(params[i].getCustomerName());
			
			if (!checkModifyLastUpdateDate(masterParam, params[i], params[i].getRowNo()))
			{
				return false;
			}
		}
		
		// 既存の処理
		return super.startSCH(conn, startParams);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 他端末で、マスタ情報の更新が行われたかをチェックします。
	 * 
	 * @param param チェックする各コード・名称のセットされた
	 * @param inputParam 画面からの入力値（最終更新日時を取得するために使用）
	 * @param rowNo 行No.
	 * @return 他端末で更新されていない：true。他端末で更新された：false
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected boolean checkModifyLastUpdateDate(MasterParameter param, MasterRetrievalSupportParameter inputParam, int rowNo)
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
		if (!StringUtil.isBlank(param.getConsignorCode())
		 && !StringUtil.isBlank(param.getCustomerCode()))
		{
			// 出荷先チェック
			param.setLastUpdateDate(inputParam.getCustomerLastUpdateDate());
			if (wCustomerOperator.isModify(param))
			{
		 	    // 6023490=No.{0} 指定された{1}は、他の端末で更新されたため登録できません。
				wMessage = "6023490" + wDelim + rowNo + wDelim + DisplayText.getText("CHK-W0027");
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
	 * @param  caseqty            出庫ケース数
	 * @param  pieceqty           出庫ピース数
	 * @return 入力に間違いがなかった場合はtrue、間違いがあった場合はfalseを返します。
	 */
	protected boolean retrievalInputCheck(String casepieceflag, int enteringqty, int caseqty, int pieceqty)
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
				if (casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
				{
					// 6023345=ケース入数が0の場合、ケース／ピース区分にケースは選択できません。
					wMessage = "6023345";
					return false;
				}				
				// ケース/ピース区分が指定なしの場合
				else if (casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
				{
					// 入庫ケース数が1以上
					if (caseqty > 0)
					{
						// 6023506=ケース入数が0の場合、{0}は入力できません。
						// LBL-W0385=ホスト予定ケース数
						wMessage = "6023506" + wDelim + DispResources.getText("LBL-W0385");
						return false;
					}
				}
			}
		}
		// 元ソースのチェックを行う
		if (!super.retrievalInputCheck(casepieceflag, enteringqty, caseqty, pieceqty))
		{
			return false;
		}

		return true;

	}
	
	// Private methods -----------------------------------------------
	
}
//end of class
