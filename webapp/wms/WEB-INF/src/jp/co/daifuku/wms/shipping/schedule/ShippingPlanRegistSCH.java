//$Id: ShippingPlanRegistSCH.java,v 1.2 2006/11/21 04:22:57 suresh Exp $
package jp.co.daifuku.wms.shipping.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.shipping.dbhandler.ShippingWorkingInformationHandler;

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
 *     ホスト予定ケース数 <BR>
 *     ホスト予定ピース数 <BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:22:57 $
 * @author  $Author: suresh $
 */
public class ShippingPlanRegistSCH extends AbstractShippingSCH
{

	// Class variables -----------------------------------------------
	/**
	 * デリミタ
	 */
	protected String wDelim = MessageResource.DELIM;
	
	/**
	 * 処理名
	 */
	private static String wProcessName = "ShippingPlanRegistSCH";	
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.2 $,$Date: 2006/11/21 04:22:57 $" );
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public ShippingPlanRegistSCH()
	{
		wMessage = null;
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
		
		// 該当する荷主コードがセットされます。
		ShippingParameter wParam = new ShippingParameter();
		
		// 出荷予定情報ハンドラ類のインスタンス生成
		ShippingPlanSearchKey wKey = new ShippingPlanSearchKey();
		ShippingPlan[] wShippingName = null;
		ShippingPlanReportFinder sRFinder = new ShippingPlanReportFinder(conn) ;
		
		// 検索条件を設定する。
		// 状態フラグ＝「削除」以外
		wKey.setStatusFlag( ShippingPlan.STATUS_FLAG_DELETE, "<>" );
		// グルーピング条件に荷主コードをセット
		wKey.setConsignorCodeGroup( 1 );
		wKey.setConsignorCodeCollect( "DISTINCT" );
		
		try
		{
			int count = sRFinder.search(wKey) ;
			ShippingPlan[] sPlan = null ;
			if(count == 1)
			{
				sPlan = (ShippingPlan[])sRFinder.getEntities(1) ;
				wParam.setConsignorCode(sPlan[0].getConsignorCode());

				// 検索条件を設定する。				
				wKey.KeyClear();
				// 荷主コード
				wKey.setConsignorCode( sPlan[ 0 ].getConsignorCode() );
				// ソート順に登録日時を設定
				wKey.setRegistDateOrder( 1, false );
				wKey.setConsignorNameCollect("");
			
				sRFinder.search(wKey) ;
				
				wShippingName = (ShippingPlan[])sRFinder.getEntities(1) ;
				wParam.setConsignorName(wShippingName[0].getConsignorName()) ;				
			}
		    
		}
		catch(ReadWriteException e)
		{

			//6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler( 6006002, e ), this.getClass().getName() );
			throw ( new ReadWriteException( "6006002" + wDelim + "DnShippingPlan" ) ) ;
		    
		}
		finally
		{
			sRFinder.close();
		}
		
		return wParam;
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
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public boolean check( Connection conn, Parameter checkParam, Parameter[] inputParams )
		throws ScheduleException,ReadWriteException
	{
		
		// 入力エリアの内容
		ShippingParameter wParam = ( ShippingParameter )checkParam;
		// ためうちエリアの内容
		ShippingParameter[] wParamList = ( ShippingParameter[] )inputParams;
		
		/********************************
		/* 条件による必須チェックを行う。
		/********************************/
		// 出荷予定ケース数入力時、ケース入数必須チェック 
		if ( wParam.getPlanCaseQty() > 0 && wParam.getEnteringQty() == 0 )
		{
			// 6023019 = ケース入数を入力してください。
			wMessage = "6023019";
			return false;
		}
		
		// 出荷予定ケース数または出荷予定ピース数に1以上の入力チェック
		if ( wParam.getPlanCaseQty() == 0 && wParam.getPlanPieceQty() == 0 )
		{
			// 6023093 = 予定ケース数または予定ピース数を入力してください。
			wMessage = "6023093";
			return false;
		}			
	
		// TC区分１：クロスTC品の場合はクロスドック運用がありになっている事
		if (wParam.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_CROSSTC)
			&& !super.isCrossDockOperation(conn))
		{
			// 6023369=クロスＤＣ区分の予定データ登録時は、クロスＤＣ運用が有効になっている必要があります。
			wMessage = "6023369";
			return false;
		}

		// TC区分２：TC品の場合はTC運用がありになっている事
		if (wParam.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_TC)
			&& !super.isTcOperation(conn))
		{
			// 6023368=ＴＣ区分の予定データ登録時は、ＴＣ運用が有効になっている必要があります。
			wMessage = "6023368";
			return false;
		}
		
		// TC/DC区分の入力コードチェック
		if ( !wParam.getTcdcFlag().equals( ShippingPlan.TCDC_FLAG_DC ) &&
			 !wParam.getTcdcFlag().equals( ShippingPlan.TCDC_FLAG_TC ) &&
			 !wParam.getTcdcFlag().equals( ShippingPlan.TCDC_FLAG_CROSSTC ) )
		{
			// 6023105 = ＴＣ／ＤＣ区分には0～2の値を入力してください。
			wMessage = "6023105";
			return false;
		}
		
		// オーバーフローチェックを行う。
		if ( ( long )wParam.getEnteringQty() * ( long )wParam.getPlanCaseQty() + wParam.getPlanPieceQty() > WmsParam.MAX_TOTAL_QTY )
		{
			// 6023058 = {0}には{1}以下の値を入力してください。
			wMessage = "6023058" + wDelim + DisplayText.getText( "LBL-W0379" ) + wDelim + AbstractSCH.MAX_TOTAL_QTY_DISP;
			return false;
		}
		
		// 表示件数チェックを行う。
		if ( wParamList != null && wParamList.length + 1 > WmsParam.MAX_NUMBER_OF_DISP )
		{
			// 6023096 = 件数が{0}件を超えるため、入力できません。
			wMessage = "6023096" + wDelim + AbstractSCH.MAX_NUMBER_OF_DISP_DISP;
			return false;
		}
		
		// 重複チェックを行う。
		if ( wParamList != null )
		{
			for ( int i = 0; i < wParamList.length; i ++ )
			{
				if ( wParamList[ i ].getShippingLineNo()==wParam.getShippingLineNo())
				{
					// 6023091 = 既に同一の行No．が存在するため、入力できません。
					wMessage = "6023091";
					return false;
				}
			}
		}
		
		ShippingPlanOperator shipPlanOperator = new ShippingPlanOperator(conn);
		ShippingPlan shipPlan = null;
		
		shipPlan = shipPlanOperator.findShippingPlan(wParam);
		if (shipPlan != null && !shipPlan.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_UNSTART))
		{
			if ( shipPlan.getStatusFlag().equals( ShippingPlan.STATUS_FLAG_NOWWORKING ) )
			{
				// 6023269 = 作業中の同一データが存在するため、入力できません。
				wMessage = "6023269";
				return false;
			}
			
			if ( shipPlan.getStatusFlag().equals( ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART ) )
			{
				// 6023270 = 一部受付済みの同一データが存在するため、入力できません。
				wMessage = "6023270";
				return false;
			}

			if ( shipPlan.getStatusFlag().equals( ShippingPlan.STATUS_FLAG_COMPLETION ) )
			{
				// 6023337=完了の同一データが存在するため、入力できません。
				wMessage = "6023337";
				return false;
			}			
		}
		
		// 登録データが、スキップ対象か判定します。
		if (shipPlan != null)
		{
			if (!shipPlanOperator.findNextProcTc(wParam, false))
			{
				// 6023378=入荷伝票Noには対応する出荷予定が存在する為、修正できません。
				wMessage = "6023378" + wDelim + wParam.getRowNo();
				return false;
			}
		}
		else
		{
			if (!shipPlanOperator.findNextProcTc(wParam, true))
			{
				// 6023378=入荷伝票Noには対応する出荷予定が存在する為、修正できません。
				wMessage = "6023378" + wDelim + wParam.getRowNo();
				return false;
			}
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
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean nextCheck( Connection conn, Parameter checkParam ) throws ReadWriteException,ScheduleException
	{	
			ShippingParameter wParam = ( ShippingParameter ) checkParam;
			// 作業者コード、パスワードチェック
			if (!super.checkWorker(conn, wParam))
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
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean startSCH( Connection conn, Parameter[] startParams ) throws ReadWriteException, ScheduleException
	{
		boolean registFlag = false;
		
		// 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
		boolean updateLoadDataFlag = false;
		
		boolean returnFlag = false;

		try
		{
			// パラメータの入力情報
			ShippingParameter[] wParam = ( ShippingParameter[] )startParams;
			
			// 日次更新処理中のチェック
			if (isDailyUpdate(conn))
			{
				return false;
			}
			// 取り込みフラグチェック処理 false：取り込み中	
			if (isLoadingData(conn))
			{
				return false;
			}
			// 取り込みフラグ更新：取り込み中
			if (!updateLoadDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn,wProcessName);
			updateLoadDataFlag = true;
			
			ShippingWorkingInformationHandler wSWObj = new ShippingWorkingInformationHandler( conn );
			if (!wSWObj.lockPlanData(startParams))
			{
				// 6003006=このデータは、他の端末で更新されたため処理できません。
				wMessage = "6003006";
				return false;
			}
			if ( wParam != null )
			{
				// 入荷予定情報オペレータを作成
				ShippingPlanOperator shipPlanOperator = new ShippingPlanOperator(conn);
				ShippingPlan shipPlan = null;
				
				// データ取り込みスキップフラグ
				boolean skip_flag = false;
				
				// 作業者名称
				String workerName = super.getWorkerName(conn, wParam[0].getWorkerCode());
				// SequenceHandlerのインスタンス生成
				SequenceHandler sequence = new SequenceHandler( conn );
				// バッチNo.
				String batch_seqno = sequence.nextShippingPlanBatchNo();
				
				for ( int i = 0; i < wParam.length; i ++ )
				{

					// DBとの同一データチェックを行う
					shipPlan = shipPlanOperator.findShippingPlan(wParam[i]);

					// パラメータに登録する値をセットする。
					// 仕分予定数(バラ総数)
					wParam[i].setTotalPlanQty(wParam[i].getPlanCaseQty() * wParam[i].getEnteringQty() + wParam[i].getPlanPieceQty());
					// 作業者コード
					wParam[i].setWorkerCode(wParam[0].getWorkerCode());
					// 作業者名
					wParam[i].setWorkerName(workerName);
					// バッチNo.
					wParam[i].setBatchNo(batch_seqno);
					// 端末No.
					wParam[i].setTerminalNumber(wParam[0].getTerminalNumber());
					// 登録区分
					wParam[i].setRegistKbn(SystemDefine.REGIST_KIND_WMS);
					// 登録処理名
					wParam[i].setRegistPName(wProcessName);
					
					
					if (shipPlan != null)
					{
						// 出荷予定情報の更新：削除 → 登録

						// 取り込みをスキップするかどうか判定
						// クロスドックパッケージありでかつTCでかつ、更新対象データとは別データに同一の入荷伝票Noと入荷伝票行No.が
						// 次作業情報にある場合は、更新せずにスキップする。
						if (shipPlanOperator.findNextProcTc(wParam[i], false))
						{
							// 未開始以外のデータがあった場合は、登録を行いません
							if (!shipPlan.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_UNSTART))
							{
								if ( shipPlan.getStatusFlag().equals( ShippingPlan.STATUS_FLAG_NOWWORKING ) )
								{
									// 6023394 = 作業中の同一データが存在するため、登録できません。
									wMessage = "6023394";
									return false;
								}
				
								if ( shipPlan.getStatusFlag().equals( ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART ) )
								{
									// 6023395 = 一部受付済みの同一データが存在するため、登録できません。
									wMessage = "6023395";
									return false;
								}
				
								if ( shipPlan.getStatusFlag().equals( ShippingPlan.STATUS_FLAG_COMPLETION ) )
								{
									// 6023396=完了の同一データが存在するため、登録できません。
									wMessage = "6023396";
									return false;
								}
							}
					
							// 未開始のデータがあった場合は出荷予定情報、作業情報、在庫情報、次作業情報の状態を削除に更新する。
							shipPlanOperator.updateShippingPlan(shipPlan.getShippingPlanUkey(), wProcessName);

					
							// 入力情報を新規に登録する(仕分予定情報、作業情報、在庫情報、次作業情報)
							shipPlanOperator.createShippingPlan(wParam[i]);
						}
						else
						{
							// 処理のスキップをログへ記録
							// メッセージの準備
							String[] msg = { Integer.toString(wParam[i].getRowNo())};
							// 6020003=データの取り込みをスキップしました。 行:{0}
							RmiMsgLogClient.write(6020003, wProcessName, msg);
							
							skip_flag = true;
						}
					}
					else
					{
						// 出荷予定情報の新規登録	
						// 取り込みをスキップするかどうか判定
						// クロスドックパッケージありでかつTCでかつ、同一の入荷伝票Noと入荷伝票行No.が
						// 次作業情報にある場合は、更新せずにスキップする。
						if (shipPlanOperator.findNextProcTc(wParam[i], true))
						{
							// 入力情報を新規に登録する(仕分予定情報、作業情報、在庫情報、次作業情報)
							shipPlanOperator.createShippingPlan(wParam[i]);
						}
						else
						{
							// 処理のスキップをログへ記録
							// メッセージの準備
							String[] msg = { Integer.toString(wParam[i].getRowNo())};
							// 6020003=データの取り込みをスキップしました。 行:{0}
							RmiMsgLogClient.write(6020003, wProcessName, msg);
							
							skip_flag = true;
						}
					}
				}
				if (skip_flag)
				{
					// 6021004 = 一部スキップで終了しました。
					wMessage = "6021004"; 
				}
				else
				{
					// 6001003 = 登録しました。
					wMessage = "6001003";					
				}

				registFlag = true;
				returnFlag =  true;
			}
			
		}		
		catch (ReadWriteException e)
		{
			super.doRollBack(conn,wProcessName);
		}
		catch (Exception e)
		{
			super.doRollBack(conn,wProcessName);
			super.doExceptionHandling(e,wProcessName);
		}
		finally
		{
			// 登録に失敗した場合はトランザクションをロールバックする
			if (!registFlag)
			{
				super.doRollBack(conn,wProcessName);
			}
			// 取り込み中フラグが自クラスで更新されたものであるならば、
			// 取り込み中フラグを、0：停止中にする
			if( updateLoadDataFlag )
			{
				super.updateLoadDataFlag(conn, false);
				super.doCommit(conn,wProcessName);
			}
		}
		return returnFlag;
	}	
}
//end of class
