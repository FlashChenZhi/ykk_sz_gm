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
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.MasterPrefs;
import jp.co.daifuku.wms.master.autoregist.AutoRegisterWrapper;
import jp.co.daifuku.wms.master.merger.MergerWrapper;
import jp.co.daifuku.wms.master.merger.StockMGWrapper;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.ItemOperator;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
import jp.co.daifuku.wms.stockcontrol.schedule.StockRegistSCH;

/**
 * Designer : M.Takeuchi <BR>
 * Maker : M.Takeuchi <BR>
 * <BR>
 * 在庫登録処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、在庫登録処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理を行いますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   -初期表示処理-(<CODE>AbstructStockControlSCH()</CODE>クラス)<BR>  
 * </DIR>
 * <BR>
 * 2.入力ボタン押下処理（<CODE>check()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェック・オーバーフローチェック・重複チェックを行い、チェック結果を返します。 <BR>
 *   在庫情報に同一行No.の該当データが存在しなかった場合はtrueを、条件エラーが発生した場合や該当データが存在した場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   [パラメータ] *必須入力  +どちらか必須入力 <BR>
 *   <BR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   荷主コード* <BR>
 *   荷主名称 <BR>
 *   商品コード* <BR>
 *   商品名称 <BR>
 *   ケース/ピース区分* <BR>
 *   入庫棚* <BR>
 *   ケース入数 <BR>
 *   ボール入数 <BR>
 *   在庫ケース数+ <BR>
 *   在庫ピース数+ <BR>
 *   ケースITF <BR>
 *   ボールITF <BR>
 *   賞味期限 <BR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   <BR>
 *   -作業者コードチェック処理-(<CODE>AbstructStockControlSCH()</CODE>クラス) <BR>
 *   <BR>
 *   -入力値チェック処理-(<CODE>AbstructStockControlSCH()</CODE>クラス) <BR>
 *   <BR>
 *   -オーバーフローチェック- <BR>
 *   <BR>
 *   -表示件数チェック- <BR>
 *   <BR>
 *   -重複チェック- <BR>
 *   <BR>  
 *     <DIR>
 *     荷主コード、ロケーションNo.、商品コード、ケース/ピース区分、賞味期限をキーにして重複チェックを行います。<BR>
 *     賞味期限は、在庫を一意にする項目としてWmsParamに定義されている場合、重複チェックのキーに含みます。 <BR>
 *     </DIR>
 *   <BR>
 *   -在庫存在チェック- <BR>
 *   <BR>
 *     <DIR>
 *     荷主コード、ロケーションNo.、商品コード、在庫ステータス(センター在庫)、ケース/ピース区分、賞味期限にてデータを検索し、<BR>
 *     在庫情報の存在チェックを行います。<BR>
 *     賞味期限は、在庫を一意にする項目としてWmsParamに定義されている場合、検索条件に含みます。 <BR>
 *     <BR>
 *     データが存在する場合、エラーとする。 <BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * 3.登録ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR><BR>
 * <BR>
 * <DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、在庫登録処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   [パラメータ] <BR>
 *   <BR>
 *   作業者コード <BR>
 *   パスワード <BR>
 *   荷主コード <BR>
 *   荷主名称 <BR>
 *   商品コード <BR>
 *   商品名称 <BR>
 *   ケース/ピース区分 <BR>
 *   入庫棚 <BR>
 *   ケース入数 <BR>
 *   ボール入数 <BR>
 *   在庫ケース数 <BR>
 *   在庫ピース数 <BR>
 *   ケースITF <BR>
 *   ボールITF <BR>
 *   賞味期限 <BR>
 *   端末No. <BR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   <BR>
 *   -作業者コードチェック処理-(<CODE>AbstructStockControlSCH()</CODE>クラス)<BR>
 *   <BR>
 *   -日次更新処理中チェック処理-(<CODE>AbstructStorageSCH()</CODE>クラス) <BR>
 *   <BR>
 *   [更新登録処理] <BR>
 *   <BR>
 *   -在庫情報テーブル(DMSTOCK)の登録または更新-<BR>
 *   <BR>
 *   荷主コード、ロケーションNo.、商品コード、在庫ステータス(センター在庫)、ケース/ピース区分、賞味期限にてデータを検索し、在庫情報が存在する場合は対象レコードをロックする。<BR>
 *   賞味期限は、在庫を一意にする項目としてWmsParamに定義されている場合、検索条件に含みます。 <BR>
 *   <BR>
 *   データが存在しない場合、<BR>
 *   受け取ったパラメータの内容をもとに在庫情報を登録します。<BR>
 *   <BR>
 *   -実績送信情報テーブル(DNHOSTSEND)の登録-(<CODE>AbstructStockControlSCH()</CODE>クラス)<BR>  
 *   <BR>
 *   受け取ったパラメータの内容をもとに実績送信情報を登録します。<BR>
 *   <BR>
 *   -作業者実績情報テーブル(DNWORKERRESULT)の登録または更新-(<CODE>AbstructStockControlSCH()</CODE>クラス)<BR>  
 *   <BR>
 *   受け取ったパラメータの内容をもとに作業者実績情報を登録または更新します。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/14</TD><TD>Y.Kubo</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterStockRegistSCH extends StockRegistSCH
{
	//	Class variables -----------------------------------------------
	/**
	 * クラス名(在庫登録)
	 */
	public static String PROCESSNAME = "MasterStockRegistSCH";

	/**
	 * 予定データ補完ラッパクラス
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
	public MasterStockRegistSCH()
	{
		wMessage = null;
	}

	/**
	 * このクラスを使用してDBの検索・更新を行う場合はこのコンストラクタを使用してください。 <BR>
	 * 各DBの検索・更新に必要なインスタンスを作成します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public MasterStockRegistSCH(Connection conn) throws ReadWriteException, ScheduleException
	{
		wMessage = null;
		wMGWrapper = new StockMGWrapper(conn);
		wAutoRegistWrapper = new AutoRegisterWrapper(conn);
		wConsignorOperator = new ConsignorOperator(conn);
		wItemOperator = new ItemOperator(conn);
	}

	// Public methods ------------------------------------------------
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
		StockControlParameter tempParam = (StockControlParameter) super.initFind(conn, searchParam);
		// 取得した荷主を返却パラメータにセットします。
		MasterStockControlParameter wParam = new MasterStockControlParameter();
		if (tempParam != null)
		{
			wParam.setConsignorCode(tempParam.getConsignorCode());
			wParam.setConsignorName(tempParam.getConsignorName());
			// 作業日(システム定義日付)
			wParam.setStorageDateString(tempParam.getStorageDateString());
		}
		else
		{
			wParam.setConsignorCode("");
			wParam.setConsignorName("");
			// 作業日(システム定義日付)
			wParam.setStorageDateString(tempParam.getStorageDateString());
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
		
		MasterStockControlParameter param = (MasterStockControlParameter) searchParam;
		
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
			MasterStockControlParameter tempParam = (MasterStockControlParameter) searchParam;
			
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
				tempParam.setConsignorLastUpdateDate(wConsignorOperator.getLastUpdateDate(mstParam));
				tempParam.setItemLastUpdateDate(wItemOperator.getLastUpdateDate(mstParam));
			}
			tempList.add(tempParam);
		}
		MasterStockControlParameter[] result = new MasterStockControlParameter[tempList.size()];
		tempList.toArray(result);
		return result;
	}
	
	/** 
	 * 画面から入力された溜めうちエリアの内容をパラメータとして受け取り、 <BR>
	 * 必須チェック・オーバーフローチェック・重複チェックを行い、チェック結果を返します。 <BR>
	 * 在庫情報に同一行No.の該当データが存在しなかった場合はtrueを、 <BR>
	 * 条件エラーが発生した場合や該当データが存在した場合は排他エラーとし、falseを返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>StockControlParameter</CODE>クラスのインスタンス。 <BR>
	 *        StockControlParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @param inputParams ためうちエリアの内容を持つ<CODE>StockControlParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        StockControlParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ScheduleException,ReadWriteException
	{
	    int iRet = 0;
		// 入力エリアの内容
		StockControlParameter param = (StockControlParameter) checkParam;
		// ためうちエリアの内容
		StockControlParameter[] paramlist = (StockControlParameter[]) inputParams;

		MasterParameter masterParam = new MasterParameter();
		masterParam.setConsignorCode(param.getConsignorCode());
		masterParam.setConsignorName (param.getConsignorName());
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

		// 既存の入力チェックを行う
		if (!super.check(conn, checkParam, inputParams))
		{
			return false;
		}
		
		// 6001019=入力を受け付けました。
		wMessage = "6001019";
		return true;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、在庫登録スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>StockControlParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        StockControlParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// 入力エリアの内容
		MasterStockControlParameter[] params = (MasterStockControlParameter[]) startParams;

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
	protected boolean checkModifyLastUpdateDate(MasterParameter param, MasterStockControlParameter inputParam, int rowNo)
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
	 * @param  caseqty            在庫ケース数
	 * @param  pieceqty           在庫ピース数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	protected boolean stockInputCheck(String casepieceflag, int enteringqty, int caseqty, int pieceqty) 
		throws ReadWriteException, ScheduleException
	{
		// 補完タイプによって異なるチェックを行います
		MasterPrefs masterPrefs = new MasterPrefs();
		if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE
		 || masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_FILL_EMPTY)
		{
			if (enteringqty <= 0)
			{
				// ケース/ピース区分がケースの場合
				if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
				{
					// 6023345=ケース入数が0の場合、ケース／ピース区分にケースは選択できません。
					wMessage = "6023345";
					return false;
				}				
				// ケース/ピース区分が指定なしの場合
				else if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
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
	// Private methods -----------------------------------------------
}
//end of class
