//$Id: ShippingWorkMaintenanceSCH.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $
package jp.co.daifuku.wms.shipping.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.crossdoc.CrossDocOperator;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdMessage;

/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * WEB出荷作業メンテナンスを行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷作業メンテナンス処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド） <BR><BR><DIR>
 *   作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 * <BR>
 *   ＜検索条件＞ <BR><DIR>
 *     作業区分：出荷(5) <BR>
 *     状態フラグ：削除以外 </DIR></DIR>
 * <BR>
 * 2.表示ボタン押下処理（<CODE>query()</CODE>メソッド）<BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   出荷先コード、商品コード、伝票No.、行No.順に表示を行います。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力<BR><DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     荷主コード* <BR>
 *     状態フラグ* <BR>
 *     出荷予定日* <BR>
 *     出荷先コード <BR>
 *     開始伝票No. <BR>
 *     終了伝票No. <BR>
 *     商品コード <BR></DIR>
 * </DIR>
 * <BR>
 * 3.修正登録、一括作業中解除ボタン押下処理（<CODE>startSCHgetParams()</CODE>メソッド） <BR><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、出荷作業メンテナンス処理を行います。 <BR>
 *   パラメータの押下ボタンの種類が0であれば修正登録処理を、1であれば一括作業中解除処理を行います。 <BR>
 *   処理が正常に完了した場合はためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はnullを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力 <BR><DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     作業No.* <BR>
 *     荷主コード * <BR>
 *     状態フラグ* <BR>
 *     出荷予定日* <BR>
 *     出荷先コード <BR>
 *     開始伝票No. <BR>
 *     終了伝票No. <BR>
 *     商品コード <BR>
 *     ケース入数 <BR>
 *     出荷ケース数 <BR>
 *     出荷ピース数 <BR>
 *     賞味期限 <BR>
 *     最終更新日時* <BR>
 *     押下ボタンの種類* <BR></DIR>
 *   ＜修正登録処理＞ <BR>
 * <DIR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR><DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR></DIR>
 *     2.作業No.の作業情報テーブルがデータベースに存在すること。 <BR>
 *     3.パラメータの最終更新日時と作業情報テーブルの最終更新日時の値が一致すること。（排他チェック） <BR>
 * <BR>
 *   ＜更新処理＞ <BR>
 *     -出荷予定情報テーブル(DNSHIPPINGPLAN)の更新 <BR>
 *       1.状態フラグを未開始→開始済み、未開始→作業中に更新する場合 <BR><DIR>
 *         出荷予定情報の状態フラグが未開始の場合のみ、状態フラグを作業中に更新する。 <BR></DIR>
 *       2.状態フラグを開始済み→未開始、作業中→未開始に更新する場合 <BR><DIR>
 *         出荷予定情報の状態フラグが作業中の場合のみ、状態フラグを未開始に更新する。 <BR></DIR>
 *       3.状態フラグを作業中→完了、開始済み→完了、未開始→完了に更新する場合 <BR><DIR>
 *         状態フラグを完了に更新する。 <BR>
 *         パラメータの出荷実績数を出荷予定情報の出荷実績数に加算する。 <BR>
 *         また、出荷欠品数を出荷予定数からパラメータの出荷実績数を減算した値に更新する。 <BR></DIR>
 *       4.状態フラグを完了→完了に更新する場合 <BR><DIR>
 *         出荷実績数をパラメータの出荷実績数の値に更新する。 <BR>
 *         また、出荷欠品数を出荷予定数からパラメータの出荷実績数を減算した値に更新する。 <BR></DIR>
 *       5.最終更新処理名を更新する。 <BR>
 * <BR>
 *     -作業情報テーブル(DNWORKINFO)の更新 <BR>
 *       1.状態フラグを未開始→開始済み、未開始→作業中、開始済み→未開始、作業中→未開始に更新する場合 <BR><DIR>
 *         状態フラグをパラメータの状態フラグの値に更新する。 <BR></DIR>
 *       2.状態フラグをに作業中→完了、開始済み→完了、未開始→完了に更新する場合 <BR><DIR>
 *         パラメータの出荷実績数を作業情報の作業実績数に加算する。 <BR>
 *         また、作業欠品数を作業予定数からパラメータの出荷実績数を減算した値に更新する。 <BR>
 *         パラメータに賞味期限がセットされている場合は、その内容で賞味期限(result_use_by_date)を更新する。 <BR>
 *         ※在庫情報(DMSTOCK)の更新、実績情報(HOSTSEND)の登録、作業者実績情報の登録を出荷完了処理にて行う。 <BR></DIR>
 *       3.状態フラグを完了→完了に更新する場合 <BR><DIR>
 *         パラメータの出荷実績数を作業情報の作業実績数に加算する。 <BR>
 *         また、作業欠品数を作業予定数からパラメータの出荷実績数を減算した値に更新する。 <BR>
 *         パラメータに賞味期限がセットされている場合は、その内容で賞味期限(result_use_by_date)を更新する。 <BR>
 *         ※実績数の更新範囲は作業可能数－保留数とする。 <BR></DIR>
 *       4.最終更新処理名を更新する。 <BR>
 * <BR>
 *     -在庫情報テーブル(DNHOSTSEND)の更新 <BR>
 *       状態フラグが完了→完了に更新する場合、今回更新した作業情報の内容をもとに在庫情報を更新する。 <BR>
 *       1.在庫数を出荷実績数の差異分減算した値に更新する。 <BR>
 *       2.引当数を出荷実績数の差異分減算した値に更新する。 <BR>
 *       3.在庫IDに紐づく作業情報の状態フラグが全て完了の場合、在庫情報の在庫ステータスを完了に更新する。 <BR>
 *       4.最終更新処理名を更新する。 <BR>
 *       ※差異＝元出荷実績数（更新前の作業情報の実績数）－パラメータの出荷実績数 <BR>
 * <BR>
 *     -実績送信情報テーブル(DNHOSTSEND)の登録 <BR>
 *       状態フラグが完了→完了に更新する場合、今回更新した作業情報の内容をもとに実績送信情報を登録する。 <BR>
 * <BR>
 *     -作業者実績情報テーブル(DNWORKERRESULT)の更新登録 <BR>
 *       状態フラグが完了→完了に更新する場合、パラメータの内容をもとに作業者実績情報を更新登録する。 <BR></DIR>
 * <BR>
 *   ＜一括作業中解除処理＞ <BR>
 * <DIR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR><DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR></DIR>
 *     2.作業No.の作業情報テーブルがデータベースに存在すること。 <BR>
 *     3.該当する作業情報テーブルの状態フラグが完了以外であること。 <BR>
 *     4.最終更新日時と作業情報テーブルの最終更新日時の値が一致すること。（排他チェック） <BR>
 * <BR>
 *   ＜更新処理＞ <BR>
 *     -出荷予定情報テーブル(DNSHIPPINGPLAN)の更新 <BR>
 *       出荷予定情報の状態フラグが作業中の場合のみ、受け取ったパラメータの内容をもとに出荷予定情報を更新する。 <BR><DIR>
 *         状態フラグを未開始に更新する。 <BR>
 *         最終更新処理名を更新する。 <BR></DIR>
 * <BR>
 *     -作業情報テーブル(DNWORKINFO)の更新 <BR>
 *       受け取ったパラメータの内容をもとに作業情報を更新する。 <BR><DIR>
 *         状態フラグを未開始に更新する。 <BR>
 *         最終更新処理名を更新する。 <BR></DIR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/04</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class ShippingWorkMaintenanceSCH extends AbstractShippingSCH
{

	// Class variables -----------------------------------------------
	/**
	 * デリミタ
	 */
    protected String wDelim = MessageResource.DELIM;
    
	/**
	 * 処理名
	 */
	private static String wProcessName = "ShippingWorkMaintenance";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:31 $" );
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public ShippingWorkMaintenanceSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
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

		// 該当する荷主コードがセットされます。
		ShippingParameter wParam = new ShippingParameter();

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
	    WorkingInformationReportFinder wRFinder = new WorkingInformationReportFinder(conn);
		// 検索条件を設定する。
		// 作業区分
		wKey.setJobType( WorkingInformation.JOB_TYPE_SHIPINSPECTION );
		// 状態フラグ！＝「削除」
		wKey.setStatusFlag( ShippingPlan.STATUS_FLAG_DELETE, "<>" );
		// グルーピング条件に荷主コードをセット
		wKey.setConsignorCodeGroup( 1 );
		wKey.setConsignorCodeCollect( "DISTINCT" );
		
		try
		{   
		    int count = wRFinder.search(wKey) ;		
		    WorkingInformation[] working = null ;
		    if (count == 1)
		    {				
			    working = (WorkingInformation[])wRFinder.getEntities(1) ;
			    wParam.setConsignorCode(working[0].getConsignorCode());		
		    }
		}
		catch(ReadWriteException e)
		{
		    //6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler( 6006002, e ), this.getClass().getName() );
			throw ( new ReadWriteException( "6006002" + wDelim + wProcessName ) ) ;
		    
		}
		finally
		{
			wRFinder.close();
		}
		return wParam;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>ShippingParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>ShippingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果を持つ<CODE>ShippingParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          検索結果が1000件を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{
		// 検索結果
		ShippingParameter[] wSParam = null;
		// データ件数
		// 荷主名称
		String wConsignorName = "";

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler( conn );
		WorkingInformation[] wWorkinfo = null;
		WorkingInformation[] wWorkinfoName = null;
	
			// パラメータの検索条件
			ShippingParameter wParam = ( ShippingParameter )searchParam;

			// 作業者コード、パスワードのチェックを行う。
			if ( !super.checkWorker( conn, wParam ) )
			{
				return null;
			}
			// 検索条件を設定する。
			// 荷主コード
			wKey.setConsignorCode( wParam.getConsignorCode() );
			// 作業状態
			if ( wParam.getStatusFlag().equals( ShippingParameter.WORKSTATUS_ALL ) )
			{
				// パラメータの作業状態が「全て」であれば、未開始、開始済み、作業中、完了データを表示する。
				wKey.setStatusFlag( WorkingInformation.STATUS_FLAG_NOWWORKING, "<=", "(", "", "OR" );
				wKey.setStatusFlag( WorkingInformation.STATUS_FLAG_COMPLETION, "=", "", ")", "AND" );
			}
			else
			{
				wKey.setStatusFlag( wParam.getStatusFlag() );
			}
			// 出荷予定日
			wKey.setPlanDate( wParam.getPlanDate() );
			// 出荷先コード
			if ( !StringUtil.isBlank( wParam.getCustomerCode() ) )
			{
				wKey.setCustomerCode( wParam.getCustomerCode() );
			}
			// 開始伝票No.
			if ( !StringUtil.isBlank( wParam.getFromTicketNo() ) )
			{
				wKey.setShippingTicketNo( wParam.getFromTicketNo(), ">=" );
			}
			// 終了伝票No.
			if ( !StringUtil.isBlank( wParam.getToTicketNo() ) )
			{
				wKey.setShippingTicketNo( wParam.getToTicketNo(), "<=" );
			}
			// 商品コード
			if ( !StringUtil.isBlank( wParam.getItemCode() ) )
			{
				wKey.setItemCode( wParam.getItemCode() );
			}
			// 作業区分
			wKey.setJobType( WorkingInformation.JOB_TYPE_SHIPINSPECTION );
			// 出荷先コード、商品コード、出荷伝票No.、出荷伝票行No.順、登録日時順でソート順をセット
			wKey.setCustomerCodeOrder( 1, true );
			wKey.setItemCodeOrder( 2, true );
			wKey.setShippingTicketNoOrder( 3, true );
			wKey.setShippingLineNoOrder( 4, true );
			wKey.setRegistDateOrder( 5, true );
			
			if(!super.canLowerDisplay(wObj.count( wKey )))
			{
				return super.returnNoDiaplayParameter();
			}

			// 出荷予定情報を取得する。
			wWorkinfo = ( WorkingInformation[] )wObj.find( wKey );

			// 登録日時の最も新しい荷主名称を取得する。
			wKey.setRegistDateOrder( 1, false );
			wWorkinfoName = ( WorkingInformation[] )wObj.find( wKey );
			if ( wWorkinfoName != null && wWorkinfoName.length > 0 )
			{
				wConsignorName = wWorkinfoName[ 0 ].getConsignorName();
			}

			// Vectorインスタンスの生成
			Vector vec = new Vector();

            // 検索結果を返却パラメータにセットする。
			for ( int i = 0; i < wWorkinfo.length; i ++ )
			{
				ShippingParameter wTemp = new ShippingParameter();
				// 荷主コード
				wTemp.setConsignorCode( wWorkinfo[ i ].getConsignorCode() );
				// 荷主名称
				wTemp.setConsignorName( wConsignorName );
				// 出荷先コード
				wTemp.setCustomerCode( wWorkinfo[ i ].getCustomerCode() );
				// 出荷先名称
				wTemp.setCustomerName( wWorkinfo[ i ].getCustomerName1() );
				// 商品コード
				wTemp.setItemCode( wWorkinfo[ i ].getItemCode() );
				// 商品名称
				wTemp.setItemName( wWorkinfo[ i ].getItemName1() );
				// ケース入数
				wTemp.setEnteringQty( wWorkinfo[ i ].getEnteringQty() );
				// ボール入数
				wTemp.setBundleEnteringQty( wWorkinfo[ i ].getBundleEnteringQty() );
				// 作業予定数
				wTemp.setTotalPlanQty( wWorkinfo[ i ].getPlanEnableQty() );
				// 作業実績数
				wTemp.setTotalResultQty( wWorkinfo[ i ].getResultQty() );
				// 賞味期限
				if ( wWorkinfo[ i ].getStatusFlag().equals( WorkingInformation.STATUS_FLAG_COMPLETION ) )
				{
					wTemp.setUseByDate( wWorkinfo[ i ].getResultUseByDate() );
				}
				else
				{
					wTemp.setUseByDate( "" );
				}
				// 予定ケース数
				wTemp.setPlanCaseQty( DisplayUtil.getCaseQty( wWorkinfo[ i ].getPlanEnableQty(), wWorkinfo[ i ].getEnteringQty() ) );
				// 予定ピース数
				wTemp.setPlanPieceQty( DisplayUtil.getPieceQty( wWorkinfo[ i ].getPlanEnableQty(), wWorkinfo[ i ].getEnteringQty() ) );
				// 実績ケース数
				wTemp.setResultCaseQty( DisplayUtil.getCaseQty( wTemp.getTotalResultQty(), wWorkinfo[ i ].getEnteringQty() ) );
				// 実績ピース数
				wTemp.setResultPieceQty( DisplayUtil.getPieceQty( wTemp.getTotalResultQty(), wWorkinfo[ i ].getEnteringQty() ) );
				// 状態フラグ
				if ( wWorkinfo[ i ].getStatusFlag().equals( WorkingInformation.STATUS_FLAG_UNSTART ) )
				{
					// 未開始
					wTemp.setStatusFlagL( ShippingParameter.WORKSTATUS_S_UNSTARTING );
				}
				else if ( wWorkinfo[ i ].getStatusFlag().equals( WorkingInformation.STATUS_FLAG_START ) )
				{
					// 開始済み
					wTemp.setStatusFlagL( ShippingParameter.WORKSTATUS_S_START );
				}
				else if ( wWorkinfo[ i ].getStatusFlag().equals( WorkingInformation.STATUS_FLAG_NOWWORKING ) )
				{
					// 作業中
					wTemp.setStatusFlagL( ShippingParameter.WORKSTATUS_S_NOWWORKING );
				}
				else if ( wWorkinfo[ i ].getStatusFlag().equals( WorkingInformation.STATUS_FLAG_COMPLETION ) )
				{
					// 完了
					wTemp.setStatusFlagL( ShippingParameter.WORKSTATUS_S_FINISH );
				}
				// 実績報告フラグ
				if (wWorkinfo[i].getReportFlag().equals(WorkingInformation.REPORT_FLAG_NOT_SENT))
				{
					// 未報告
					wTemp.setReportFlag(ShippingParameter.REPORT_FLAG_NOT_SENT);
					wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_NOT_SENT));
				}
				else
				{
					// 報告済
					wTemp.setReportFlag(ShippingParameter.REPORT_FLAG_SENT);
					wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_SENT));
				}
				// 出荷伝票No.
				wTemp.setShippingTicketNo( wWorkinfo[ i ].getShippingTicketNo() );
				// 出荷伝票行No.
				wTemp.setShippingLineNo( wWorkinfo[ i ].getShippingLineNo() );
				// 作業者コード
				wTemp.setWorkerCode( wWorkinfo[ i ].getWorkerCode() );
				// 作業者名
				wTemp.setWorkerName( wWorkinfo[ i ].getWorkerName() );
				// 作業No.
				wTemp.setJobNo( wWorkinfo[ i ].getJobNo() );
				// 最終更新日時
				wTemp.setLastUpdateDate( wWorkinfo[ i ].getLastUpdateDate() );
				vec.addElement( wTemp );
			}

			wSParam = new ShippingParameter[ vec.size() ];
			vec.copyInto( wSParam );

			// 6001013 = 表示しました。
			wMessage = "6001013";		

		return wSParam;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、出荷作業メンテナンスのスケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。<BR>
	 * 作業中のデータの状態を変更した場合はRFT作業情報を検索し作業を行っている端末の電文項目をNULLに更新します。<BR>
	 * また、作業中データ保存情報を検索し該当データが存在した場合はそのレコードを削除します。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams( Connection conn, Parameter[] startParams )
		throws ReadWriteException, ScheduleException
	{
			ShippingParameter[] wParam = ( ShippingParameter[] )startParams;

			// 作業者コード、パスワードのチェックを行う。
			if ( !checkWorker( conn, wParam[ 0 ] ) )
			{
				return null;
			}
			
			// 日次更新処理中のチェック
			if (isDailyUpdate(conn))
			{
				return null;
			}

			// 対象データ全件の排他チェック・ロックを行う。
			if (!lockAll(conn, wParam))
			{
				// 6003006=このデータは、他の端末で更新されたため処理できません。
				wMessage = "6003006";
				return null;
			}
			// ためうちエリアの入力、排他チェックを行う。
			if ( !checkList( conn, wParam ) )
			{
				return null;
			}

			// 一つの作業者コードに対して変更がなされているので、作業者名称は一度だけ取得します。
			String workerName = super.getWorkerName(conn,wParam[ 0 ].getWorkerCode()) ;
			wParam[ 0 ].setWorkerName( workerName );

			CrossDocOperator crossDocOperator = null;
			if(super.isCrossDockPack(conn))
			{
				crossDocOperator  = new CrossDocOperator(); 
			}
			String workDate = super.getWorkDate(conn);
			
			int workqty = 0;

			// 作業中状態を解除したデータの端末リスト
			ArrayList terminalList = new ArrayList();

			for ( int i = 0; i < wParam.length; i ++ )
			{
				// 一括作業中解除ボタンが押下された場合
				if ( !StringUtil.isBlank( wParam[ 0 ].getButtonType() ) &&
					 wParam[ 0 ].getButtonType().equals( ShippingParameter.BUTTON_ALLWORKINGCLEAR ) )
				{
					// パラメータの状態フラグを未開始にする。
					wParam[ i ].setStatusFlagL( ShippingParameter.WORKSTATUS_S_UNSTARTING );
				}

				// 作業情報を更新する。（更新前の作業情報を取得します）
				WorkingInformation wWorkinfo = updateWorkinfo( conn, wParam[ i ], wParam[ 0 ] );

				if ( wWorkinfo != null )
				{
					// 出荷予定情報を更新する。
					updateShippingPlan( conn, wParam[ i ], wWorkinfo.getPlanUkey(), wWorkinfo.getResultQty() );

					// 「未開始」→「完了」「開始済み」→「完了」「作業中」→「完了」の場合、出荷完了処理クラスより
					// 在庫情報(DMSTOCK)の更新、実績情報(HOSTSEND)の登録、作業者実績情報の登録を行う。
					if ( wParam[ i ].getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_FINISH ) &&
					     ( wWorkinfo.getStatusFlag().equals( WorkingInformation.STATUS_FLAG_UNSTART ) ||
					       wWorkinfo.getStatusFlag().equals( WorkingInformation.STATUS_FLAG_START ) ||
					       wWorkinfo.getStatusFlag().equals( WorkingInformation.STATUS_FLAG_NOWWORKING ) ) )
					{
						// 出荷完了処理クラスのインスタンス生成
						ShippingCompleteOperator wCompObj = new ShippingCompleteOperator();
						// 出荷完了処理メソッド

						wCompObj.complete( conn, wParam[ i ].getJobNo(), WorkingInformation.JOB_TYPE_SHIPINSPECTION,
						                   wProcessName, wParam[ 0 ].getWorkerCode(), workerName, wParam[0].getTerminalNumber() );

						//クロスドックパッケージが導入されていて、状態が未開始から作業中または作業中から完了になった時に、
						//CrossDocOperatorクラスを呼び出して処理を完了させます。
						if(crossDocOperator!=null)
						{
							int compelteQty = (wParam[i].getEnteringQty() * wParam[i].getResultCaseQty()) + wParam[i].getResultPieceQty();
							int shortageQty = wWorkinfo.getPlanEnableQty() - compelteQty;
							crossDocOperator.complete(conn,wWorkinfo.getPlanUkey(),compelteQty,shortageQty);
						}

						int inputQty = wParam[ i ].getEnteringQty() * wParam[ i ].getResultCaseQty() + wParam[ i ].getResultPieceQty();
						workqty = super.addWorkQty(workqty,inputQty);
					}

					// 「完了」→「完了」の場合、在庫情報を更新し、実績の差分を実績情報に登録する。
					if ( wParam[ i ].getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_FINISH ) &&
					     wWorkinfo.getStatusFlag().equals( WorkingInformation.STATUS_FLAG_COMPLETION ) )
					{
						// 作業者実績更新のため、今回作業分の作業数を加算する。
						// 計算式は今回の結果 - 変更前の作業情報の実績数とし、マイナスの場合でも加算するため絶対値を取る。
						workqty += Math.abs((wParam[ i ].getEnteringQty() * wParam[ i ].getResultCaseQty() + wParam[ i ].getResultPieceQty())
							- wWorkinfo.getResultQty());
						// 作業数量のオーバーフローチェック
					 	if (workqty > WmsParam.WORKER_MAX_TOTAL_QTY)
					 	{
							workqty = WmsParam.WORKER_MAX_TOTAL_QTY;
					 	}
							
						completionProcess( conn, wParam[ i ], wWorkinfo.getResultQty(),wWorkinfo.getShortageCnt(), wWorkinfo.getResultUseByDate(), wParam[ 0 ],workDate );
					}				

					String rftNo = wWorkinfo.getTerminalNo();
					// 変更前の作業状態が作業中かつ端末Noが空でない場合
					if (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
							&& ! StringUtil.isBlank(rftNo))
					{
						// 作業中データファイルを削除するため、
						// RFT号機Noのリストを生成する。
						if (! terminalList.contains(rftNo))
						{
							terminalList.add(rftNo);
						}
					}
				}
			}
			if (workqty > 0)
			{
				// 作業者実績情報テーブル(DNWORKERRESULT)を登録する。
				super.updateWorkerResult(conn,wParam[ 0 ].getWorkerCode(),workerName,workDate,wParam[0].getTerminalNumber(),
						WorkingInformation.JOB_TYPE_SHIPINSPECTION,workqty);
			}

			// 作業中データファイル削除処理で必要なため、PackageManagerを初期化する。
			PackageManager.initialize(conn);
			// 作業中データファイルを削除する。
			try
			{
				IdMessage.deleteWorkingDataFile(terminalList,
						WorkingInformation.JOB_TYPE_SHIPINSPECTION,
						"",
						wProcessName,
						conn);
			}
			catch (ReadWriteException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
			catch (InvalidDefineException e)
			{
				throw new ScheduleException(e.getMessage());
			}

			// 出荷予定情報を再検索する。
			ShippingParameter[] viewParam = ( ShippingParameter[] )this.query( conn, wParam[ 0 ] );

			// 6001018 = 更新しました。
			wMessage = "6001018";

			// 最新の出荷予定情報を返す。
			return viewParam;
		
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリアの入力チェック、排他チェックを行います。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param param 画面から入力された内容を持つShippingParameterクラスのインスタンス。
	 * @return 入力エラーが無ければtrueを、入力エラー発生した場合はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected boolean checkList( Connection conn, Parameter[] checkParam ) throws ReadWriteException
	{

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationHandler wObj = new WorkingInformationHandler( conn );
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;

	try
		{
			// ためうちエリアの内容
			ShippingParameter[] wParam = ( ShippingParameter[] )checkParam;
			// 入力された出荷数
			long wResultQty = 0;
			// 出荷予定数
			long wPlanQty = 0;

			for ( int i = 0; i < wParam.length; i ++ )
			{
				// 検索条件を設定する。
				wKey.KeyClear();
				// 作業No.
				wKey.setJobNo( wParam[ i ].getJobNo() );
				// 作業情報の検索結果を取得する。
				wWorkinfo = ( WorkingInformation )wObj.findPrimaryForUpdate( wKey );

				if ( wWorkinfo != null )
				{
					// 報告済みデータはメンテできません。
					if (wWorkinfo.getReportFlag().equals(WorkingInformation.REPORT_FLAG_SENT))
					{
						if (!wParam[ i ].getStatusFlagL().equals( ShippingParameter.WORKSTATUS_FINISH)
						|| !wParam[ i ].getStatusFlagL().equals( ShippingParameter.WORKSTATUS_RECEPTION_IN_PART))
						{
							// 6023364=No.{0} 既にデータ報告済みのため、変更できません。
							wMessage = "6023364" + wDelim + wParam[ i ].getRowNo();
							return false;
						}
						else
						{
							// 出荷数を求める
							wResultQty = ( long )wParam[ i ].getResultCaseQty() * ( long )wParam[ i ].getEnteringQty() + wParam[ i ].getResultPieceQty();
							if (wResultQty != wWorkinfo.getResultQty())
							{
								// 6023364=No.{0} 既にデータ報告済みのため、変更できません。
								wMessage = "6023364" + wDelim + wParam[ i ].getRowNo();
								return false;
							}
						}
					}
					
					// 作業開始フラグが未開始の場合はメンテ不可
					if (wWorkinfo.getBeginningFlag().equals(WorkingInformation.BEGINNING_FLAG_NOT_STARTED))
					{
						// 6023377 = No.{0} 前工程の処理が完了していないため、処理できません。
						wMessage = "6023377" + wDelim + wParam[i].getRowNo();
						return false;
					}

					// 完了->完了以外の場合
					if ( wWorkinfo.getStatusFlag().equals( WorkingInformation.STATUS_FLAG_COMPLETION ) &&
					     !wParam[ i ].getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_FINISH ) )
					{
						// 6023276 = No.{0} 既に出荷完了処理を行っているため、状態を変更できません。
						wMessage = "6023276" + wDelim + wParam[ i ].getRowNo();
						return false;
					}

					// ケース入数が0の場合、出荷ケース数入力チェック
					if ( wParam[ i ].getEnteringQty() == 0 && wParam[ i ].getResultCaseQty() > 0 )
					{
						// 6023271 = No.{0} ケース入数が0の場合は、出荷ケース数は入力できません。
						wMessage = "6023271" + wDelim + wParam[ i ].getRowNo();
						return false;
					}

					// 出荷数を求める
					wResultQty = ( long )wParam[ i ].getResultCaseQty() * ( long )wParam[ i ].getEnteringQty() + wParam[ i ].getResultPieceQty();
					// 出荷予定数を求める
					wPlanQty = wWorkinfo.getPlanEnableQty();

					if ( wResultQty > 0 && wParam[ 0 ].getButtonType().equals( ShippingParameter.BUTTON_MODIFYSUBMIT ) )
					{
						// 状態フラグ＝「完了」 かつ 出荷数＞出荷予定数の場合
						if ( wParam[ i ].getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_FINISH ) && wResultQty > wPlanQty )
						{
							// 6023155 = No.{0} 完了数が予定数を超えています。確認後再登録を行ってください。
							wMessage = "6023155" + wDelim + wParam[ i ].getRowNo();
							return false;
						}

						// 状態フラグ＝「未開始」 かつ 出荷数＞０
						if ( wParam[ i ].getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_UNSTARTING ) && wResultQty > 0 )
						{
							// 6023075 = No.{0} 状態が未開始の場合は、出荷数は入力できません。
							wMessage = "6023075" + wDelim + wParam[ i ].getRowNo();
							return false;
						}

						// 状態フラグ＝「開始済」 かつ 出荷数＞０
						if ( wParam[ i ].getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_START ) && wResultQty > 0 )
						{
							// 6023076 = No.{0} 状態が開始済の場合は、出荷数は入力できません。
							wMessage = "6023076" + wDelim + wParam[ i ].getRowNo();
							return false;
						}

						// 状態フラグ＝「作業中」 かつ 出荷数＞０
						if ( wParam[ i ].getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_NOWWORKING ) && wResultQty > 0 )
						{
							// 6023077 = No.{0} 状態が作業中の場合は、出荷数は入力できません。
							wMessage = "6023077" + wDelim + wParam[ i ].getRowNo();
							return false;
						}

						// オーバーフローチェック
						if ( wResultQty >WmsParam.MAX_TOTAL_QTY )
						{
							// 6023272 = No.{0} {1}には{2}以下の値を入力してください。
							wMessage = "6023272" + wDelim + wParam[ i ].getRowNo() + wDelim + DisplayText.getText( "LBL-W0198" ) + wDelim + AbstractSCH.MAX_TOTAL_QTY_DISP;
							return false;
						}

						//完了->完了の場合、出荷数＜＝出荷予定数－保留数チェック
						// 作業予定数（ホスト）-> 作業可能数に変更
						if ( wParam[ i ].getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_FINISH ) &&
						     wWorkinfo.getStatusFlag().equals( ShippingPlan.STATUS_FLAG_COMPLETION ) &&
						     wResultQty > wWorkinfo.getPlanEnableQty() - wWorkinfo.getPendingQty() )
						{
							// 6023275 = No.{0} 保留作業があるため出荷数(ﾊﾞﾗ総量)は{1}以下の値を入力してください。
							wMessage = "6023275" + wDelim + wParam[ i ].getRowNo() + wDelim + ( wWorkinfo.getPlanEnableQty() - wWorkinfo.getPendingQty() );
							return false;
						}
					}
					// 排他チェック
					if ( !wParam[ i ].getLastUpdateDate().equals( wWorkinfo.getLastUpdateDate() ) )
					{
						// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
						wMessage = "6023209" + wDelim + wParam[ i ].getRowNo();
						return false;
					}
				}
				else
				{
					// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
					wMessage = "6023209" + wDelim + wParam[ i ].getRowNo();
					return false;
				}
			}
		}		
		catch ( NoPrimaryException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		return true;
	}


	/**
	 * メンテナンス完了時の在庫情報と実績送信情報、作業者実績情報の更新登録処理を行います。
	 * @param conn       データベースとのコネクションを保持するインスタンス。
	 * @param param      画面から入力された内容を持つShippingParameterクラスのインスタンス。
	 * @param resultQty  元実績数
	 * @param resultShortCnt  欠品数
	 * @param resultUseByDate  賞味期限
	 * @param inputParam 画面から入力された内容を持つShippingParameterクラスのインスタンス。
	 *                   （配列の先頭の値が保持されています）
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void completionProcess( Connection conn, ShippingParameter param, int resultQty, int resultShortCnt,  String resultUseByDate,
									 ShippingParameter inputParam,String workDate ) throws ReadWriteException
	{

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationHandler wObj = new WorkingInformationHandler( conn );
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;     // HostSend作成用
		// 実績送信情報ハンドラ類のインスタンス生成
		HostSendHandler wHObj = new HostSendHandler( conn );
		HostSend wHostSend = null;
		// 在庫情報ハンドラ類のインスタンス生成
		StockHandler wSObj = new StockHandler( conn );
		StockAlterKey wAKey = new StockAlterKey();
		StockSearchKey wSKey = new StockSearchKey();
		Stock wStock = null;
		// 出荷実績数
		int wResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();

		try
		{
			// 検索条件を設定する。
			// 作業No.
			wKey.setJobNo( param.getJobNo() );
			// 作業情報の検索結果を取得する。
			wWorkinfo = ( WorkingInformation )wObj.findPrimary( wKey );
			wWorkinfo.setResultQty(-resultQty);
			wWorkinfo.setShortageCnt(-resultShortCnt );
			wWorkinfo.setResultUseByDate(resultUseByDate);
			// 検索条件を設定する。
			// 在庫ID
			wSKey.setStockId( wWorkinfo.getStockId() );
			// ロックした在庫情報の検索結果を取得する。
			wStock = ( Stock )wSObj.findPrimaryForUpdate( wSKey );

			if ( wStock != null )
			{
				// 更新条件を設定する。
				// 在庫ID
				wAKey.setStockId( wStock.getStockId() );
				// 更新値を設定する。
				// 在庫数（元在庫数－(パラメータの出荷実績数－元出荷実績数)）
				if (wStock.getStockQty() - wWorkinfo.getResultQty() > 0)
				{
					wAKey.updateStockQty( wStock.getStockQty() - wWorkinfo.getResultQty() );
				}
				else
				{
					// マイナス値になる場合は0をSET
					wAKey.updateStockQty( 0 );
				}

				// 最終更新処理名
				wAKey.updateLastUpdatePname( wProcessName );
				// 在庫情報を更新
				wSObj.modify( wAKey );
			}

			// 作業情報のエンティティから実績送信情報のエンティティを生成する。
			
			wHostSend = new HostSend( wWorkinfo, workDate, wProcessName );

			// 実績送信情報を登録
			wHObj.create( wHostSend );

			wWorkinfo.setResultQty( wResultQty);
			wWorkinfo.setResultUseByDate(param.getUseByDate());
			wWorkinfo.setShortageCnt( wWorkinfo.getPlanEnableQty() - wWorkinfo.getResultQty() - wWorkinfo.getPendingQty());
			// 作業情報のエンティティから実績送信情報のエンティティを生成する。
			wHostSend = new HostSend( wWorkinfo, workDate, wProcessName );
			// 実績送信情報を登録(変更の実績)
			wHObj.create( wHostSend );
			
		}
		catch ( DataExistsException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( InvalidDefineException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( NotFoundException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( NoPrimaryException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( ReadWriteException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
	}
	
	/**
	 * 作業情報の更新処理を行います。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param param      画面から入力された内容を持つShippingParameterクラスのインスタンス。
	 * @param inputParam 画面から入力された内容を持つShippingParameterクラスのインスタンス。
	 *                   （配列の先頭の値が保持されています）
	 * @return 更新前の作業情報の内容を持つWorkingInformationクラスのインスタンス。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected WorkingInformation updateWorkinfo( Connection conn, ShippingParameter param, ShippingParameter inputParam ) throws ReadWriteException
	{
		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationHandler wObj = new WorkingInformationHandler( conn );
		WorkingInformationAlterKey wAKey = new WorkingInformationAlterKey();
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;
		// 作業実績数
		int wResultQty = 0;

		try
		{
			// 検索条件を設定する。
			// 作業No.
			wKey.setJobNo( param.getJobNo() );
			// 作業情報の検索結果を取得する。
			wWorkinfo = ( WorkingInformation )wObj.findPrimary( wKey );

			if ( wWorkinfo != null )
			{
				// デッドロックを防止するために予定情報に紐づく他の作業情報をロックする。
				// ここから
				wKey.KeyClear();
				wKey.setPlanUkey(wWorkinfo.getPlanUkey());
				wKey.setJobNo(param.getJobNo(), "!=");
				wObj.findForUpdate(wKey);
				// ここまで
				
				// 更新条件を設定する。
				// 作業No.
				wAKey.setJobNo( param.getJobNo() );

				// 更新値を設定する。
				if ( !wWorkinfo.getStatusFlag().equals( WorkingInformation.STATUS_FLAG_COMPLETION ) )
				{
					// 状態フラグ
					wAKey.updateStatusFlag( param.getStatusFlagL() );

					// 未開始に更新時、集約作業No.に自分が持つ作業No.をセット
					if (param.getStatusFlagL().equals(ShippingParameter.WORKSTATUS_S_UNSTARTING))
					{
						wAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
						wAKey.updateCollectJobNo(wWorkinfo.getJobNo());
						wAKey.updateWorkerCode("");
						wAKey.updateWorkerName("");
						wAKey.updateTerminalNo("");
					}
				}
				// 最終更新処理名
				wAKey.updateLastUpdatePname( wProcessName );

				// 状態フラグを完了に更新する場合
				if ( param.getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_FINISH ) )
				{
					// 作業実績数を求める
					wResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();
					// 作業実績数（パラメータの作業実績数を加算する）
					wAKey.updateResultQty( wResultQty );
					// 欠品数（作業予定数から作業実績数を減算する）
					// 欠品数（作業可能数から作業実績数と保留数を減算する）
					wAKey.updateShortageCnt( wWorkinfo.getPlanEnableQty() - wResultQty - wWorkinfo.getPendingQty());
					// 賞味期限
					wAKey.updateResultUseByDate( param.getUseByDate() );
					// 状態フラグ
					wAKey.updateStatusFlag( WorkingInformation.STATUS_FLAG_COMPLETION );
					// 端末No.
					wAKey.updateTerminalNo( inputParam.getTerminalNumber() );
					// 作業者コード
					wAKey.updateWorkerCode( inputParam.getWorkerCode() );
					// 作業者名
					wAKey.updateWorkerName( inputParam.getWorkerName() );
					// 集約作業No.に自分が持つ作業No.をセット
					wAKey.updateCollectJobNo(wWorkinfo.getJobNo());
				}

				// 作業情報の更新
				wObj.modify( wAKey );

				return wWorkinfo;
			}
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( NotFoundException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( NoPrimaryException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		
		return null;
	}

	/**
	 * 出荷予定情報の更新処理を行います。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param param 画面から入力された内容を持つShippingParameterクラスのインスタンス。
	 * @param key 更新対象となる出荷予定一意キー
	 * @param resultQty 作業情報の元実績数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void updateShippingPlan( Connection conn, ShippingParameter param, String key, int resultQty ) throws ReadWriteException
	{
		// 出荷予定情報ハンドラ類のインスタンス生成
		ShippingPlanHandler wObj = new ShippingPlanHandler( conn );
		ShippingPlanAlterKey wAKey = new ShippingPlanAlterKey();
		ShippingPlanSearchKey wKey = new ShippingPlanSearchKey();
		ShippingPlan wShipping = null;
		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationHandler wWObj = new WorkingInformationHandler( conn );
		WorkingInformationSearchKey wWKey = new WorkingInformationSearchKey();
		WorkingInformation[] wWorkinfo = null;
		// パラメータ出荷実績数
		int wParamResultQty = 0;
		// 出荷実績数
		int wResultQty = 0;

		try
		{
			// 検索条件を設定する。
			// 予定一意キー
			wWKey.setPlanUkey( key );
			// 作業情報の検索結果を取得する。
			wWorkinfo = ( WorkingInformation[] )wWObj.find( wWKey );

			// 検索条件を設定する。
			// 出荷予定一意キー
			wKey.setShippingPlanUkey( key );
			// ロックした出荷予定情報の検索結果を取得する。
			wShipping = ( ShippingPlan )wObj.findPrimaryForUpdate( wKey );

			if ( wShipping != null )
			{
				// 更新条件を設定する。
				// 出荷予定一意キー
				wAKey.setShippingPlanUkey( key );

				// 更新値を設定する。
				// 最終更新処理名
				wAKey.updateLastUpdatePname( wProcessName );

				// 状態フラグ
				// 「未開始」->「開始済み」または「作業中」の場合
				if ( wShipping.getStatusFlag().equals( ShippingPlan.STATUS_FLAG_UNSTART ) &&
				     ( param.getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_START ) ||
				       param.getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_NOWWORKING ) ) )
				{
					wAKey.updateStatusFlag( ShippingPlan.STATUS_FLAG_NOWWORKING );
				}

				// 「開始済み」または「作業中」->「未開始」の場合
				if ( wShipping.getStatusFlag().equals( ShippingPlan.STATUS_FLAG_NOWWORKING ) &&
				     ( param.getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_UNSTARTING ) ) )
				{
					wAKey.updateStatusFlag( ShippingPlan.STATUS_FLAG_UNSTART );
				}

				// 「未開始」「開始済み」「作業中」->「完了」または「完了」->「完了」の場合
				if ( param.getStatusFlagL().equals( ShippingParameter.WORKSTATUS_S_FINISH ) )
				{
					String wStatusFlag = ShippingPlan.STATUS_FLAG_COMPLETION;
					if ( wWorkinfo != null )
					{
						for ( int i = 0; i < wWorkinfo.length; i ++ )
						{
							// 作業情報が割れた時、未開始データが作業情報に存在した場合、出荷予定情報の状態フラグには一部完了をセットする。
							if ( !wWorkinfo[ i ].getStatusFlag().equals( WorkingInformation.STATUS_FLAG_COMPLETION ) )
							{
								wStatusFlag = ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART;
								break;
							}
						}
					}

					wAKey.updateStatusFlag( wStatusFlag );
					// パラメータ出荷実績数を求める
					wParamResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();
					// 出荷実績数を求める（元実績数とパラメータの出荷実績数の差異を元実績数から減算する）
					wResultQty = wShipping.getResultQty() - ( resultQty - wParamResultQty );
					// 出荷実績数
					wAKey.updateResultQty( wResultQty );
					// 欠品数（出荷予定数から出荷実績数を減算する）
					wAKey.updateShortageCnt( wShipping.getPlanQty() - wResultQty );
				}

				// 出荷予定情報の更新
				wObj.modify( wAKey );
			}
		}
		catch ( InvalidDefineException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( NoPrimaryException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( NotFoundException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( ReadWriteException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
	}
}
//end of class
