package jp.co.daifuku.wms.instockreceive.schedule;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/*
 * Created on 2004/11/01
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * クロス/ＤＣ入荷実績照会処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、クロス/ＤＣ入荷実績照会処理を行います。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド) <BR> 
 * <BR>
 * <DIR>
 *   実績情報<CODE>(ResultView)</CODE>に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 *   <BR>
 *   ＜検索条件＞ <BR>
 *   <BR>
 *   <DIR>
 *   作業区分が入荷<BR>
 *   TC/DC区分 クロス、DC<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理(<CODE>query()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   作業日、予定日、仕入先コード、仕入先名称、伝票№、行№、商品コード、商品名称、
 *   ケース入数、ボール入数、作業予定ケース数、作業予定ピース数、実績ケース数、実績ピース数、欠品ケース数、欠品ピース数、賞味期限、
 *   クロス/DC、ケースＩＴＦ、ボールＩＴＦ、作業者コード、作業者名を表示します。 <BR>
 *   作業者名の項目のみ作業者情報<CODE>(Dmworker)</CODE>より取得し、それ以外の項目は実績情報<CODE>(ResultView)</CODE>より取得します。 <BR>
 *   <BR>
 *   ＜パラメータ＞ *必須入力 <BR>
 *   <BR>
 *   <DIR>
 *   荷主コード* <BR>
 *   開始入荷受付日 <BR>
 *   終了入荷受付日 <BR>
 *   仕入先コード <BR>
 *   開始伝票№ <BR>
 *   終了伝票№ <BR>
 *   商品コード <BR>
 *   クロス/DC <BR>
 *   表示順１* <BR>
 *   </DIR>
 *   <BR>
 *   ＜返却データ＞ <BR>
 *   <BR>
 *   <DIR>
 *   荷主コード <BR>
 *   荷主名称 <BR>
 *   作業日 <BR>
 *   予定日 <BR>
 *   仕入先コード <BR>
 *   仕入先名称 <BR>
 *   伝票№ <BR>
 *   行№  <BR>
 *   商品コード <BR>
 *   商品名称 <BR>
 *   ケース入数 <BR>
 *   ボール入数 <BR>
 *   作業予定ケース数 <BR>
 *   作業予定ピース数 <BR>
 *   実績ケース数 <BR>
 *   実績ピース数 <BR>
 *   欠品ケース数 <BR>
 *   欠品ピース数 <BR>
 *   賞味期限 <BR>
 *   クロス/DC <BR>
 *   ケースＩＴＦ <BR>
 *   ボールＩＴＦ <BR>
 *   作業者コード <BR>
 *   作業者名 <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/28</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @author  $Author: mori $
 * @version $Revision 1.2, $Date: 2006/08/17 09:34:15 $
 * 
 */
public class InstockReceiveQtyInquirySCH extends AbstractInstockReceiveSCH
{
	// Class variables -----------------------------------------------
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * 
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:15 $");
	}

	//Constructors-------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public InstockReceiveQtyInquirySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。 <BR>
	 * 実績情報<CODE>(ResultView)</CODE>に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ <CODE>Parameter</CODE> クラスを継承したクラス
	 * @return 検索結果が含まれた <CODE>Parameter</CODE> インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// 該当する荷主コードがセットされます。
		InstockReceiveParameter wParam = new InstockReceiveParameter();

		// 出庫予定情報ハンドラ類のインスタンス生成
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		ResultViewReportFinder resultFinder = new ResultViewReportFinder(conn);
		ResultView[] wResult = null;

		try
		{
			// 検索条件を設定する。
			// データの検索
			// 作業区分：入荷
			searchKey.setJobType(SystemDefine.JOB_TYPE_INSTOCK);
			// ＴＣＤＣフラグ＝「ＴＣ以外」
			searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC, "!=");
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (resultFinder.search(searchKey) == 1)
			{
				// 荷主コードを取得します。
				wResult = (ResultView[]) resultFinder.getEntities(1);
				wParam.setConsignorCode(wResult[0].getConsignorCode());
			}
			resultFinder.close();
			
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			// 必ずConnectionをクローズする
			resultFinder.close();
		}
		
		return wParam;
		
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam
	 *            表示データ取得条件を持つ <CODE>StorageSupportParameter</CODE>
	 *            クラスのインスタンス。 <CODE>StorageSupportParameter</CODE>
	 *            インスタンス以外を指定された場合ScheduleExceptionをスローします。
	 * @return 検索結果を持つ <CODE>StorageSupportParameter</CODE> インスタンスの配列。 <BR>
	 *         該当レコードが一件もみつからない場合は要素数0の配列を返します。 <BR>
	 *         入力条件にエラーが発生した場合はnullを返します。 <BR>
	 *         nullが返された場合は、 <CODE>getMessage()</CODE>
	 *         メソッドでエラー内容をメッセージとして取得できます。 <BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		InstockReceiveParameter param = (InstockReceiveParameter) searchParam;

		if ((param == null) || param.equals(""))
		{
			// 6003001=データを選択してください。
			wMessage = "6003001";
			return null;
		}

		ResultViewSearchKey searchKey = new ResultViewSearchKey();

		// データの検索
		// 作業区分(入荷)
		searchKey.setJobType(ResultView.JOB_TYPE_INSTOCK);
		
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			//荷主コード
			searchKey.setConsignorCode(param.getConsignorCode());
		}
		else
		{
			// 6023004=荷主コードを入力してください。
			wMessage = "6023004";
			return null;
		}
		if (!StringUtil.isBlank(param.getFromInstockReceiveDate()))
		{
			//開始入荷受付日
			searchKey.setWorkDate(param.getFromInstockReceiveDate(), ">=");
		}
		if (!StringUtil.isBlank(param.getToInstockReceiveDate()))
		{
			//終了入荷受付日
			searchKey.setWorkDate(param.getToInstockReceiveDate(), "<=");
		}
		if (!StringUtil.isBlank(param.getSupplierCode()))
		{
			//仕入先コード
			searchKey.setSupplierCode(param.getSupplierCode());
		}
		if (!StringUtil.isBlank(param.getFromTicketNo()))
		{
			//開始伝票
			searchKey.setInstockTicketNo(param.getFromTicketNo(), ">=");
		}
		if (!StringUtil.isBlank(param.getToTicketNo()))
		{
			//終了伝票
			searchKey.setInstockTicketNo(param.getToTicketNo(), "<=");
		}
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			//商品コード
			searchKey.setItemCode(param.getItemCode());
		}

		//クロス/ＤＣ
		if (!StringUtil.isBlank(param.getTcdcFlag()))
		{
			if (param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_CROSSTC))
			{
				//クロスＴＣ
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_CROSSTC);
			}
			else if (param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_DC))
			{
				//ＤＣ
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_DC);
			}
			else if (param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC))
			{
				//TC 以外
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC, "<>");
			}
		}

		// 表示順１
		if (param.getDspOrderDate().equals(InstockReceiveParameter.DISPLAY_ORDER_INSTOCK_DAY)) //入荷受付日順
		{
			searchKey.setWorkDateOrder(1, true);
			searchKey.setSupplierCodeOrder(2,true);
		}
		else if (param.getDspOrderDate().equals(InstockReceiveParameter.DISPLAY_ORDER_INSTOCK_PLAN_DAY)) //入荷予定日順
		{
			searchKey.setPlanDateOrder(1, true);
			searchKey.setSupplierCodeOrder(2,true);
		}
		// 表示順２
		if (param.getDspOrderItem().equals(InstockReceiveParameter.DISPLAY_ORDER_TICKET)) //伝票No順
		{
			searchKey.setInstockTicketNoOrder(3, true);
			searchKey.setInstockLineNoOrder(4, true);
			searchKey.setItemCodeOrder(5, true);
			searchKey.setRegistDateOrder(6, true);//登録日時
			searchKey.setResultQtyOrder(7,true) ;
		}
		else if (param.getDspOrderItem().equals(InstockReceiveParameter.DISPLAY_ORDER_ITEM)) //商品コード順
		{
			searchKey.setItemCodeOrder(3, true);
			searchKey.setInstockTicketNoOrder(4, true);
			searchKey.setInstockLineNoOrder(5, true);
			searchKey.setRegistDateOrder(6, true);//登録日時
			searchKey.setResultQtyOrder(7,true) ;
		}
		
		//表示する内容のセット
		//荷主コード
		searchKey.setConsignorCodeCollect("");
		//荷主名称
		searchKey.setConsignorNameCollect("");
		//入荷日
		searchKey.setWorkDateCollect("");
		//入荷予定日
		searchKey.setPlanDateCollect("");
		//仕入先コード
		searchKey.setSupplierCodeCollect("");
		//仕入先名称
		searchKey.setSupplierName1Collect("");
		//伝票
		searchKey.setInstockTicketNoCollect("");
		//行
		searchKey.setInstockLineNoCollect("");
		//商品コード
		searchKey.setItemCodeCollect("");
		//商品名称
		searchKey.setItemName1Collect("");
		//ケース入数
		searchKey.setEnteringQtyCollect("");
		//ボース入数
		searchKey.setBundleEnteringQtyCollect("");
		//作業予定数
		searchKey.setPlanEnableQtyCollect("");
		//実績数
		searchKey.setResultQtyCollect("");
		//欠品数
		searchKey.setShortageCntCollect("");
		//賞味期限
		searchKey.setResultUseByDateCollect("");
		//クロス/ＤＣ
		searchKey.setTcdcFlagCollect("");
		//ケースITF
		searchKey.setItfCollect("");
		//ボールITF
		searchKey.setBundleItfCollect("");
		//作業者コード
		searchKey.setWorkerCodeCollect("");
		//作業者名称
		searchKey.setWorkerNameCollect("");
		//ケース/ピース区分
		searchKey.setWorkFormFlagCollect("");
		//登録日
		searchKey.setRegistDateCollect("");

		ResultViewHandler rHandler = new ResultViewHandler(conn);
		
		// 表示件数チェック
		if (!canLowerDisplay(rHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}
		
		// 表示データを取得します
		ResultView[] resultEntity = (ResultView[]) rHandler.find(searchKey);
		
		InstockReceiveParameter[] dispData = new InstockReceiveParameter[resultEntity.length];
		//登録日を1にセットする。
		String registDate = "1";
		for (int i = 0; i < resultEntity.length; i++)
		{
			dispData[i] = new InstockReceiveParameter();

			if ((i == 0) || (Long.parseLong(registDate) < Long.parseLong(getDateValue(resultEntity[i].getRegistDate()))))
			{
				registDate = getDateValue(resultEntity[i].getRegistDate());
				dispData[0].setConsignorCode(resultEntity[i].getConsignorCode()); //荷主コード
				dispData[0].setConsignorName(resultEntity[i].getConsignorName()); //荷主名
			}

			//入荷日
			dispData[i].setInstockReceiveDate(resultEntity[i].getWorkDate());

			//入荷予定日
			dispData[i].setPlanDate(resultEntity[i].getPlanDate());
			//仕入先コード
			dispData[i].setSupplierCode(resultEntity[i].getSupplierCode());
			//仕入先名称
			dispData[i].setSupplierName(resultEntity[i].getSupplierName1());
			//伝票
			dispData[i].setInstockTicketNo(resultEntity[i].getInstockTicketNo());

			//行
			dispData[i].setInstockLineNo(resultEntity[i].getInstockLineNo());
			//商品コード
			dispData[i].setItemCode(resultEntity[i].getItemCode());
			//商品名称
			dispData[i].setItemName(resultEntity[i].getItemName1());

			//ケース入数
			dispData[i].setEnteringQty(resultEntity[i].getEnteringQty());
			
			//予定ケース数
			dispData[i].setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag()));
			//予定ピース数
			dispData[i].setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag()));
			
			//実績ケース数
			dispData[i].setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag()));
			//実績ピース数
			dispData[i].setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty()));

			//欠品ケース数
			dispData[i].setShortageCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getShortageCnt(), resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag()));
			//欠品ピース数
			dispData[i].setShortagePieceQty(DisplayUtil.getPieceQty(resultEntity[i].getShortageCnt(), resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag()));

			//ボール入数
			dispData[i].setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());

			//賞味期限
			dispData[i].setUseByDate(resultEntity[i].getResultUseByDate());

			//クロス/ＤＣ
			if (!StringUtil.isBlank(resultEntity[i].getTcdcFlag()))
			{
				if (resultEntity[i].getTcdcFlag().equals(SystemDefine.TCDC_FLAG_CROSSTC))
				{
					//クロスＴＣ
					dispData[i].setTcdcName(DisplayUtil.getTcDcValue(InstockReceiveParameter.TCDC_FLAG_CROSSTC));
				}
				else if (resultEntity[i].getTcdcFlag().equals(SystemDefine.TCDC_FLAG_DC))
				{
					//ＤＣ
					dispData[i].setTcdcName(DisplayUtil.getTcDcValue(InstockReceiveParameter.TCDC_FLAG_DC));
				}
			}

			//ケースＩＴＦ
			dispData[i].setITF(resultEntity[i].getItf());
			//ボールＩＴＦ
			dispData[i].setBundleITF(resultEntity[i].getBundleItf());

			//作業者コード
			dispData[i].setWorkerCode(resultEntity[i].getWorkerCode());
			//作業者名称
			dispData[i].setWorkerName(resultEntity[i].getWorkerName());

		}
		wMessage = "6001013";
		return dispData;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * Dateクラスの日付データを文字列("yyyyMMddHHmmss"の形式)に変換する。
	 * 
	 * @param date Dateクラスの日付データ
	 * @return 日付の文字列
	 */
	private String getDateValue(Date date)
	{
		String datNumS = null;

		if (date != null)
		{
			//24時間に合わせてパターンを作る。
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			datNumS = sdf.format(date).trim();
		}

		return datNumS;
	}

}