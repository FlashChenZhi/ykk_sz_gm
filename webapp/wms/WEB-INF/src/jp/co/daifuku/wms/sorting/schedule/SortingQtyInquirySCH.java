package jp.co.daifuku.wms.sorting.schedule;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/*
 * Created on 2004/11/08
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
 * 仕分実績照会処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、仕分実績照会処理を行います。 <BR>
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
 *   作業区分が仕分<BR>
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
 * 		荷主コード <BR>
 * 		仕分日 <BR>
 *		商品コード <BR>
 * 		ケース/ピース区分 <BR>
 * 		クロス/DC区分 <BR>
 * 		作業区分 仕分 (04) <BR>
 *	 	クロス/ＤＣは全ての場合はＴＣ以外 <BR>
 *   </DIR>
 *   <BR>
 *   ＜返却データ＞ <BR>
 *   <BR>
 *   <DIR>
 * 		仕分予定日 : PlanDate <BR>
 * 		ケース/ピース区分 : CasePieceName <BR>
 * 		クロス/ＤＣ: TcdcName <BR>
 * 		出荷先コード : CustomerCode <BR>
 * 		出荷先名称 : CustomerName <BR>
 * 		ケース入数 : EnteringQty <BR>
 * 		ボール入数 : BundleEnteringQty <BR>
 * 		作業予定ケース数 : PlanCaseQty <BR>
 * 		作業予定ピース数 : PlanPieceQty <BR>
 * 		実績ケース数 : ResultCaseQty <BR>
 * 		実績ピース数 : ResultPieceQty <BR>
 * 		欠品ケース数 : ShortageCaseQty <BR>
 * 		欠品ピース数 : ShortagePieceQty <BR>
 * 		仕分場所 : SortingLocation <BR>
 * 		仕入先コード : SupplierCode <BR>
 * 		仕入先名 : SupplierName <BR>
 * 		ケースITF : ITF <BR>
 * 		ボールITF : BundleITF <BR>
 * 		出荷伝票No : ShippingTicketNo <BR>
 * 		出荷行No : ShippingLineNo <BR>
 * 		入荷伝票No : InstockTicketNo <BR>
 * 		入荷行No : InstockLineNo <BR>
 * 		作業者コード : WorkerCode <BR>
 * 		作業者名 : WorkerName <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/28</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author: suresh kayamboo
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 *  
 */
public class SortingQtyInquirySCH extends AbstractSortingSCH
{

	//Constants----------------------------------------------------

	//Attributes---------------------------------------------------

	//Static-------------------------------------------------------

	//Constructors-------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public SortingQtyInquirySCH()
	{
		wMessage = null;
	}

	//Public-------------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * 
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:34 $");
	}

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 実績情報Viewに荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR>
	 * 検索条件を必要としないためsearchParamにはnullがセットしてください。 <BR>
	 * <BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ <CODE>Parameter</CODE> クラスを継承したクラス
	 * @return 検索結果が含まれた <CODE>Parameter</CODE> インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		ResultViewSearchKey sKey = new ResultViewSearchKey();
		SortingParameter param = null;
		sKey.setJobType(SystemDefine.JOB_TYPE_SORTING);
		sKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC, "<>");

		//取得条件をセット
		sKey.setConsignorCodeCollect("");
		//集約条件をセット
		sKey.setConsignorCodeGroup(1);

		ResultViewHandler rvHandler = new ResultViewHandler(conn);

		if (rvHandler.count(sKey) == 1)
		{
			ResultView[] resultView = (ResultView[]) rvHandler.find(sKey);
			param = new SortingParameter();
			param.setConsignorCode(resultView[0].getConsignorCode());
		}
		return param;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam
	 *            表示データ取得条件を持つ <CODE>SortingParameter</CODE> クラスのインスタンス。
	 *            <CODE>SortingParameter</CODE>
	 *            インスタンス以外を指定された場合ScheduleExceptionをスローします。
	 * @return 検索結果を持つ <CODE>SortingParameter</CODE> インスタンスの配列。 <BR>
	 *         該当レコードが一件もみつからない場合は要素数0の配列を返します。 <BR>
	 *         入力条件にエラーが発生した場合はnullを返します。 <BR>
	 *         nullが返された場合は、 <CODE>getMessage()</CODE>
	 *         メソッドでエラー内容をメッセージとして取得できます。 <BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		SortingParameter sParam = (SortingParameter) searchParam;

		if ((sParam == null) || sParam.equals(""))
		{
			wMessage = "6003010";
			return null;
		}

		ResultViewSearchKey sKey = new ResultViewSearchKey();

		if (!StringUtil.isBlank(sParam.getConsignorCode()))
		{
			//荷主コード
			sKey.setConsignorCode(sParam.getConsignorCode());
		}
		else
		{
			wMessage = "6023004";
			return null;
		}
		if (!StringUtil.isBlank(sParam.getSortingDate()))
		{
			//仕分日
			sKey.setWorkDate(sParam.getSortingDate());
		}
		if (!StringUtil.isBlank(sParam.getItemCode()))
		{
			//商品コード
			sKey.setItemCode(sParam.getItemCode());
		}

		//ケース/ピース区分
		if (!StringUtil.isBlank(sParam.getCasePieceFlag()))
		{
			if (sParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				//ケース区分
				sKey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_CASE);
			}
			else if (sParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				//ピース区分
				sKey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
			}
		}
		//クロス/ＤＣ
		if (!StringUtil.isBlank(sParam.getTcdcFlag()))
		{
			if (sParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
			{
				//クロスＴＣ
				sKey.setTcdcFlag(SystemDefine.TCDC_FLAG_CROSSTC);
			}
			else if (sParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
			{
				//ＤＣ
				sKey.setTcdcFlag(SystemDefine.TCDC_FLAG_DC);
			}
			else if (sParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_ALL))
			{
				//TC 以外
				sKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC, "<>");
			}
		}

		//作業区分JobType
		sKey.setJobType(SystemDefine.JOB_TYPE_SORTING);

		//表示する内容のセット
		//荷主コード
		sKey.setConsignorCodeCollect("");
		//荷主名称
		sKey.setConsignorNameCollect("");
		//仕分日
		sKey.setWorkDateCollect("");
		// 商品コード
		sKey.setItemCodeCollect("");
		//商品名称
		sKey.setItemName1Collect("");
		//仕分予定日
		sKey.setPlanDateCollect("");
		//ケース/ピース区分
		sKey.setWorkFormFlagCollect("");
		//クロス/ＤＣ
		sKey.setTcdcFlagCollect("");
		//出荷先コード
		sKey.setCustomerCodeCollect("");
		//出荷先名
		sKey.setCustomerName1Collect("");
		//ケース入数
		sKey.setEnteringQtyCollect("");
		//ボース入数
		sKey.setBundleEnteringQtyCollect("");
		//作業予定数
		sKey.setPlanEnableQtyCollect("");
		//実績数
		sKey.setResultQtyCollect("");
		//欠品数
		sKey.setShortageCntCollect("");
		//仕分場所
		sKey.setLocationNoCollect("");
		//仕入先コード
		sKey.setSupplierCodeCollect("");
		//仕入先名
		sKey.setSupplierName1Collect("");
		//ケースITF
		sKey.setItfCollect("");
		//ボールITF
		sKey.setBundleItfCollect("");
		//出荷伝票No.
		sKey.setShippingTicketNoCollect("");
		//出荷行No.
		sKey.setShippingLineNoCollect("");
		//入荷伝票No.
		sKey.setInstockTicketNoCollect("");
		//入荷行No.
		sKey.setInstockLineNoCollect("");
		//作業者コード
		sKey.setWorkerCodeCollect("");
		//作業者名
		sKey.setWorkerNameCollect("");
		//登録日
		sKey.setRegistDateCollect("");

		//ソート順をセットします
		sKey.setPlanDateOrder(1, true);
		sKey.setCasePieceFlagOrder(2, true);
		sKey.setTcdcFlagOrder(3, false);
		sKey.setCustomerCodeOrder(4, true);
		sKey.setLocationNoOrder(5, true);
		sKey.setRegistDateOrder(6, true);
		sKey.setResultQtyOrder(7, true);

		ResultViewHandler resultHandler = new ResultViewHandler(conn);
		
		// 表示件数チェック
		if(!canLowerDisplay(resultHandler.count(sKey)))
		{
			return returnNoDisplayParameter();
		}
		
		ResultView[] resultEntity = (ResultView[]) resultHandler.find(sKey);

		SortingParameter[] dispData = null;

		if ((resultEntity == null) || (resultEntity.length == 0))
		{
			wMessage = "6003011";
			return null;
		}

		//エンテイテイをパラメータにセットする。
		dispData = (SortingParameter[]) this.setSortingParameter(resultEntity);
		wMessage = "6001013";
		return dispData;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * このメソッドは日付型から文字列に変換します。
	 * @param date
	 * @return Strig
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

	/**
	 * このメソッドはResultViewエンティティをパラメータにセットします。 <BR>
	 * 
	 * @param resultEntity
	 * @return Parameter[]
	 */
	private Parameter[] setSortingParameter(ResultView[] resultEntity)
	{
		SortingParameter[] dispData = new SortingParameter[resultEntity.length];

		// 登録日を1にセットする。
		String registDate = "1";
		for (int i = 0; i < resultEntity.length; i++)
		{
			dispData[i] = new SortingParameter();

			if ((i == 0)
				|| (Long.parseLong(registDate)
					< Long.parseLong(getDateValue(resultEntity[i].getRegistDate()))))
			{
				registDate = getDateValue(resultEntity[i].getRegistDate());
				dispData[0].setConsignorCode(resultEntity[i].getConsignorCode()); //荷主コード
				dispData[0].setConsignorName(resultEntity[i].getConsignorName()); //荷主名
			}

			//仕分日
			dispData[i].setSortingDate(resultEntity[i].getWorkDate());

			//商品コード
			dispData[i].setItemCode(resultEntity[i].getItemCode());
			//商品名称
			dispData[i].setItemName(resultEntity[i].getItemName1());
			//仕分予定日
			dispData[i].setPlanDate(resultEntity[i].getPlanDate());
			//ケース/ピース区分
			if (!StringUtil.isBlank(resultEntity[i].getWorkFormFlag()))
			{
				if (resultEntity[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE)
					|| resultEntity[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING))
				{
					//ケース区分
					dispData[i].setCasePieceName(
						DisplayUtil.getPieceCaseValue(SortingParameter.CASEPIECE_FLAG_CASE));
				}
				else if (
					resultEntity[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE))
				{
					//ピース区分
					dispData[i].setCasePieceName(
						DisplayUtil.getPieceCaseValue(SortingParameter.CASEPIECE_FLAG_PIECE));
				}

			}
			//クロス/ＤＣ
			if (!StringUtil.isBlank(resultEntity[i].getTcdcFlag()))
			{
				if (resultEntity[i].getTcdcFlag().equals(SystemDefine.TCDC_FLAG_CROSSTC))
				{
					//クロスＴＣ
					dispData[i].setTcdcName(
						DisplayUtil.getTcDcValue(SortingParameter.TCDC_FLAG_CROSSTC));
				}
				else if (resultEntity[i].getTcdcFlag().equals(SystemDefine.TCDC_FLAG_DC))
				{
					//ＤＣ
					dispData[i].setTcdcName(
						DisplayUtil.getTcDcValue(SortingParameter.TCDC_FLAG_DC));
				}
			}

			//出荷先コード
			dispData[i].setCustomerCode(resultEntity[i].getCustomerCode());
			//出荷先名
			dispData[i].setCustomerName(resultEntity[i].getCustomerName1());
			//ケース入数
			dispData[i].setEnteringQty(resultEntity[i].getEnteringQty());
			//ボール入数
			dispData[i].setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());
			//予定ケース数
			dispData[i].setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getPlanEnableQty(),
							resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag()));
			//予定ピース数
			dispData[i].setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getPlanEnableQty(),
							resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag()));
			//実績ケース数
			dispData[i].setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getResultQty(),
							resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag()));
			//実績ピース数
			dispData[i].setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getResultQty(),
							resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag()));
			//欠品ケース数
			dispData[i].setShortageCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getShortageCnt(),
							resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag()));
			//欠品ピース数
			dispData[i].setShortagePieceQty(DisplayUtil.getPieceQty(resultEntity[i].getShortageCnt(),
							resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag()));
			//仕分場所
			dispData[i].setSortingLocation(resultEntity[i].getLocationNo());
			//仕入先コード
			dispData[i].setSupplierCode(resultEntity[i].getSupplierCode());
			//仕入先名称
			dispData[i].setSupplierName(resultEntity[i].getSupplierName1());
			//ケースＩＴＦ
			dispData[i].setITF(resultEntity[i].getItf());
			//ボールＩＴＦ
			dispData[i].setBundleITF(resultEntity[i].getBundleItf());
			//出荷伝票
			dispData[i].setShippingTicketNo(resultEntity[i].getShippingTicketNo());
			//出荷行No.
			dispData[i].setShippingLineNo(resultEntity[i].getShippingLineNo());
			//入荷伝票
			dispData[i].setInstockTicketNo(resultEntity[i].getInstockTicketNo());
			//入荷行No.
			dispData[i].setInstockLineNo(resultEntity[i].getInstockLineNo());
			//作業者コード
			dispData[i].setWorkerCode(resultEntity[i].getWorkerCode());
			//作業者名称
			dispData[i].setWorkerName(resultEntity[i].getWorkerName());

		}

		return dispData;
	}

}