//$Id: ShippingQtyInquirySCH.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $
package jp.co.daifuku.wms.shipping.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;

/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * 出荷実績照会処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷実績照会処理を行います。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド) <BR> 
 * <BR>
 * <DIR>
 *   出荷実績情報<CODE>(ResultView)</CODE>に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR> 
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR> 
 *   <BR>
 *   ＜検索条件＞ <BR> 
 *   <BR>
 *   <DIR>
 *   作業区分が出荷 <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理(<CODE>query()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   出荷日、出荷予定日、出荷先コード、出荷先名称、伝票No、行No、商品コード、商品名称、ケース入数、ボール入数、作業予定ケース数、 <BR>
 *   作業予定ピース数、実績ケース数、実績ピース数、欠品ケース数、欠品ピース数、状態、賞味期限、作業者コード、作業者名を表示します。 <BR>
 *   作業者名の項目のみ作業者情報<CODE>(Dmworker)</CODE>より取得し、それ以外の項目は出荷実績情報<CODE>(ResultView)</CODE>より取得します。 <BR>
 *   <BR>
 *   ＜パラメータ＞ *必須入力 <BR>
 *   <BR>
 *   <DIR>
 *   荷主コード* <BR>
 *   開始出荷日 <BR>
 *   終了出荷日 <BR>
 *   出荷先コード <BR>
 *   伝票No <BR>
 *   商品コード <BR>
 *   表示順１* <BR>
 *   表示順２* <BR>
 *   </DIR>
 *   <BR>
 *   ＜返却データ＞ <BR>
 *   <BR>
 *   <DIR>
 *   荷主コード <BR>
 *   荷主名称 <BR>
 *   作業日 <BR>
 *   予定日 <BR>
 *   出荷先コード <BR>
 *   出荷先名 <BR>
 *   出荷伝票No <BR>
 *   出荷伝票行 <BR>
 *   商品コード <BR>
 *   商品名称 <BR>
 *   ケース入数 <BR>
 *   ボール入数 <BR>
 *   作業予定ケース数 <BR>
 *   作業予定ピース数 <BR>
 *   作業実績ケース数 <BR>
 *   作業実績ピース数 <BR>
 *   作業欠品ケース数 <BR>
 *   作業欠品ピース数 <BR>
 *   状態フラグ <BR>
 *   作業状態名称 <BR>
 *   賞味期限 <BR>
 *   作業者コード <BR>
 *   作業者名 <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class ShippingQtyInquirySCH extends AbstractShippingSCH
{
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:31 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public ShippingQtyInquirySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。 <BR>
	 * 出荷実績情報<CODE>(ResultView)</CODE>に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
	    ResultViewReportFinder rRFinder = new ResultViewReportFinder(conn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		// データの検索(出荷予定日)
		// 作業区分(出荷)
		searchKey.setJobType(ResultView.JOB_TYPE_SHIPINSPECTION);
		searchKey.setConsignorCodeCollect("");
		searchKey.setConsignorCodeGroup(1);
		
		ShippingParameter dispData = new ShippingParameter();
		try
		{
		    int count = rRFinder.search(searchKey) ;		
		    ResultView[] resultView = null ;
		    if (count == 1)
		    {				
		        resultView = (ResultView[])rRFinder.getEntities(1) ;
				dispData.setConsignorCode(resultView[0].getConsignorCode());		
		    }
		}
		catch(ReadWriteException e)
		{
		    //6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler( 6006002, e ), this.getClass().getName() );
			throw ( new ReadWriteException( "6006002" + wDelim + "DvResultView" ) ) ;
		    
		}
		finally
		{
		    rRFinder.close();
		}
		
		return dispData;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>ShippingParameter</CODE>クラスのインスタンス。 <BR>
	 *         <CODE>ShippingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。 <BR>
	 * @return 検索結果を持つ<CODE>ShippingParameter</CODE>インスタンスの配列。 <BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。 <BR>
	 *          入力条件にエラーが発生した場合はnullを返します。 <BR>
	 *          nullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
			ShippingParameter param = (ShippingParameter) searchParam;

			// 日締め処理チェック
			if (isDailyUpdate(conn))
			{
				wMessage = "6023028";
				return null;
			}

			ResultViewHandler resultviewHandler = new ResultViewHandler(conn);
			ResultViewSearchKey searchKey = new ResultViewSearchKey();
			ResultViewSearchKey nameKey = new ResultViewSearchKey();
			// 作業区分(出荷)
			searchKey.setJobType(ResultView.JOB_TYPE_SHIPINSPECTION);
			nameKey.setJobType(ResultView.JOB_TYPE_SHIPINSPECTION);
			// データの検索
			// 荷主コード
			searchKey.setConsignorCode(param.getConsignorCode());
			nameKey.setConsignorCode(param.getConsignorCode());
			// 開始出荷日
			if (!StringUtil.isBlank(param.getFromShippingDate()))
			{
				searchKey.setWorkDate(param.getFromShippingDate(), ">=");
				nameKey.setWorkDate(param.getFromShippingDate(), ">=");
			}
			// 終了出荷日
			if (!StringUtil.isBlank(param.getToShippingDate()))
			{
				searchKey.setWorkDate(param.getToShippingDate(), "<=");
				nameKey.setWorkDate(param.getToShippingDate(), "<=");
			}
			// 出荷先コード
			if (!StringUtil.isBlank(param.getCustomerCode()))
			{
				searchKey.setCustomerCode(param.getCustomerCode());
				nameKey.setCustomerCode(param.getCustomerCode());
			}
			// 伝票No.
			if (!StringUtil.isBlank(param.getShippingTicketNo()))
			{
				searchKey.setShippingTicketNo(param.getShippingTicketNo());
				nameKey.setShippingTicketNo(param.getShippingTicketNo());
			}
			// 商品コード
			if (!StringUtil.isBlank(param.getItemCode()))
			{
				searchKey.setItemCode(param.getItemCode());
				nameKey.setItemCode(param.getItemCode());
			}

			// 表示順１
			if (param.getDspOrder().equals(ShippingParameter.DSPORDER_SHIPPINGDATE)) //出荷日順
			{
				searchKey.setWorkDateOrder(1, true);
				searchKey.setCustomerCodeOrder(2,true);
			}
			else if (param.getDspOrder().equals(ShippingParameter.DSPORDER_SHIPPINGPLANDATE)) //出荷予定日順
			{
				searchKey.setPlanDateOrder(1, true);
				searchKey.setCustomerCodeOrder(2,true);
			}
			// 表示順２
			if (param.getDspOrder2().equals(ShippingParameter.DSPORDER_TICKET)) //伝票No順
			{
				searchKey.setShippingTicketNoOrder(3, true);
				searchKey.setShippingLineNoOrder(4, true);
				searchKey.setItemCodeOrder(5, true);
				searchKey.setRegistDateOrder(6, true);//登録日時
				searchKey.setResultQtyOrder(7, true);
			}
			else if (param.getDspOrder2().equals(ShippingParameter.DSPORDER_ITEM)) //商品コード順
			{
				searchKey.setItemCodeOrder(3, true);
				searchKey.setShippingTicketNoOrder(4, true);
				searchKey.setShippingLineNoOrder(5, true);
				searchKey.setRegistDateOrder(6, true);//登録日時
				searchKey.setResultQtyOrder(7, true);
			}
			
			if(!super.canLowerDisplay(resultviewHandler.count(searchKey)))
			{
				return super.returnNoDiaplayParameter();
			}	
			// 検索を実行する
			ResultView[] resultEntity = (ResultView[]) resultviewHandler.find(searchKey);
			if (resultEntity == null || resultEntity.length <= 0)
			{
				// 対象データはありませんでした
				wMessage = "6003018";
				return new ShippingParameter[0];
			}

			// 荷主名称を取得する			
			nameKey.setConsignorNameCollect("");
			nameKey.setRegistDateOrder(1, false);

			ResultViewFinder consignorFinder = new ResultViewFinder(conn);
			consignorFinder.open();
			int nameCount = consignorFinder.search(nameKey);
			String consignorName = "";
			if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
			{
				ResultView resultView[] = (ResultView[]) consignorFinder.getEntities(0, 1);

				if (resultView != null && resultView.length != 0)
				{
					consignorName = resultView[0].getConsignorName();
				}
			}
			consignorFinder.close();

			Vector vec = new Vector();
			// 出荷実績データ表示編集
			for (int loop = 0; loop < resultEntity.length; loop++)
			{
				ShippingParameter dispData = new ShippingParameter();

				// 荷主コード
				dispData.setConsignorCode(resultEntity[loop].getConsignorCode());
				// 荷主名称
				dispData.setConsignorName(consignorName);
				// 作業日（出荷日）
				dispData.setShippingDate(resultEntity[loop].getWorkDate());
				// 出荷予定日
				dispData.setPlanDate(resultEntity[loop].getPlanDate());
				// 出荷先コード
				dispData.setCustomerCode(resultEntity[loop].getCustomerCode());
				// 出荷先名称
				dispData.setCustomerName(resultEntity[loop].getCustomerName1());
				// 出荷伝票No.
				dispData.setShippingTicketNo(resultEntity[loop].getShippingTicketNo());
				// 出荷伝票行No.
				dispData.setShippingLineNo(resultEntity[loop].getShippingLineNo());
				// 商品コード
				dispData.setItemCode(resultEntity[loop].getItemCode());
				// 商品名称
				dispData.setItemName(resultEntity[loop].getItemName1());
				// ケース入数
				dispData.setEnteringQty(resultEntity[loop].getEnteringQty());
				// ボール入数
				dispData.setBundleEnteringQty(resultEntity[loop].getBundleEnteringQty());
				// 作業予定ケース数
				dispData.setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getPlanEnableQty(), resultEntity[loop].getEnteringQty()));
				// 作業予定ピース数
				dispData.setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getPlanEnableQty(), resultEntity[loop].getEnteringQty()));
				// 実績ケース数
				dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty()));
				// 実績ピース数
				dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty()));
				// 欠品ケース数
				dispData.setShortageCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getShortageCnt(), resultEntity[loop].getEnteringQty()));
				// 欠品ピース数
				dispData.setShortagePieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getShortageCnt(), resultEntity[loop].getEnteringQty()));
				// 作業状態
				dispData.setStatusFlag(resultEntity[loop].getStatusFlag());
				// 作業状態名称
				dispData.setStatusName(DisplayUtil.getResultStatusName(Math.abs(resultEntity[loop].getShortageCnt()), Math.abs(resultEntity[loop].getPendingQty())));
		
				// 賞味期限
				dispData.setUseByDate(resultEntity[loop].getResultUseByDate());
				// 作業者コード
				dispData.setWorkerCode(resultEntity[loop].getWorkerCode());
				// 作業者名
				dispData.setWorkerName(resultEntity[loop].getWorkerName());
				
				vec.addElement(dispData);
			}

			ShippingParameter[] paramArray = new ShippingParameter[vec.size()];

			vec.copyInto(paramArray);

			// 6001013 = 表示しました。
			wMessage = "6001013";
			return paramArray;
	}
}
//end of class
