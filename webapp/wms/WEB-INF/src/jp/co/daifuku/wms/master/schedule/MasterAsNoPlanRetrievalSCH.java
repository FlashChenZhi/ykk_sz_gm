package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.schedule.AsNoPlanRetrievalSCH;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.master.MasterPrefs;
import jp.co.daifuku.wms.master.merger.MergerWrapper;
import jp.co.daifuku.wms.master.merger.ShippingPlanMGWrapper;
import jp.co.daifuku.wms.master.operator.CustomerOperator;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura  <BR>
 * <BR>
 * マスタパッケージありの際に、AS/RS予定外出庫設定画面より使用します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/06/12</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterAsNoPlanRetrievalSCH extends AsNoPlanRetrievalSCH 
{
	// Class variables -----------------------------------------------

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
	public MasterAsNoPlanRetrievalSCH()
	{
		super();
	}
	
	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 在庫情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>AsScheduleParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>AsScheduleParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		// 荷主初期表示設定を行います。
		AsScheduleParameter tempParam = (AsScheduleParameter) super.initFind(conn, searchParam);
		// 取得した荷主を返却パラメータにセットします。
		MasterAsScheduleParameter dispParam = new MasterAsScheduleParameter();
		if (tempParam != null)
		{
			dispParam.setConsignorCode(tempParam.getConsignorCode());
		}
		else
		{
			dispParam.setConsignorCode("");
		}
		
		// 補完タイプをセットします
		MasterPrefs masterPrefs = new MasterPrefs();
		if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_NONE)
		{
			dispParam.setMergeType(MasterAsScheduleParameter.MERGE_TYPE_NONE);
		}
		else if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE)
		{
			dispParam.setMergeType(MasterAsScheduleParameter.MERGE_TYPE_OVERWRITE);
		}
		else
		{
			dispParam.setMergeType(MasterAsScheduleParameter.MERGE_TYPE_FILL_EMPTY);
		}
		return dispParam;
	}

	/**
	 * 処理種別に補完を指定した場合、出荷先コードの補完を行います。<BR>
	 * それ以外の場合は、継承元クラスのqueryメソッドを実行します。
	 * 
	 * 
	 * @see jp.co.daifuku.wms.asrs.schedule.AsNoPlanRetrieval#query
	 */
	public Parameter[] query (Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		if (searchParam instanceof MasterAsScheduleParameter)
		{
			MasterAsScheduleParameter param = (MasterAsScheduleParameter) searchParam;
			// 画面から補完を指定された場合にのみ補完処理を行う。
			if (param.getProcessType() == MasterAsScheduleParameter.PROCESS_TYPE_MERGE)
			{
				ShippingPlan plan = new ShippingPlan();
				
				plan.setConsignorCode(param.getConsignorCode());
				plan.setCustomerCode(param.getCustomerCode());
				plan.setCustomerName1(param.getCustomerName());
				
				MergerWrapper merger = new ShippingPlanMGWrapper(conn);
				merger.complete(plan);

				// 補完結果をパラメータにセットする
				ArrayList tempList = new ArrayList();
				if (plan != null)
				{
					MasterAsScheduleParameter tempParam = new MasterAsScheduleParameter();
					tempParam.setCustomerCode(plan.getCustomerCode());
					tempParam.setCustomerName(plan.getCustomerName1());
					
					tempList.add(tempParam);
				}
				
				MasterAsScheduleParameter[] rtParam = new MasterAsScheduleParameter[tempList.size()];
				tempList.toArray(rtParam);
				
				return rtParam;
				
			}
			
		}
		
		// 通常の問い合わせ処理
		return super.query(conn, searchParam);
		
	}
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、予定外出庫設定のスケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>AsScheduleParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         AsScheduleParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		// 入力エリアの内容
		MasterAsScheduleParameter[] param = (MasterAsScheduleParameter[]) startParams;

		if (!StringUtil.isBlank(param[0].getCustomerCode()))
		{
		    int iRet = 0;

			MasterParameter masterParam = new MasterParameter();
			masterParam.setConsignorCode(param[0].getConsignorCode());
			masterParam.setCustomerCode(param[0].getCustomerCode());
			masterParam.setCustomerName(param[0].getCustomerName());
			
			CustomerOperator customerOp = new CustomerOperator(conn);
			iRet = 	customerOp.checkCustomerMaster(masterParam);
			if (iRet != MasterParameter.RET_OK)
			{
				if(iRet == MasterParameter.RET_NG){
					// 6023458 = 出荷先コードがマスタに登録されていません。
					wMessage = "6023458";
					return null;
				} else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
					// 6023472=指定された出荷先名称はすでに使用されているため登録できません。
					wMessage = "6023472";
					return null; 
				}
			}

			
		}

		return super.startSCHgetParams(conn, startParams);
	}

	// Protected methods ---------------------------------------------
	
	// Private methods -----------------------------------------------
	
}

