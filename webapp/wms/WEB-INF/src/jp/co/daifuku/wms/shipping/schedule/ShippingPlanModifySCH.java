//$Id: ShippingPlanModifySCH.java,v 1.3 2006/11/21 04:22:57 suresh Exp $
package jp.co.daifuku.wms.shipping.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida <BR>
 * <BR>
 * WEB出荷予定データ修正削除を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷予定データ修正削除処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド） <BR><BR><DIR>
 *   出荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 * <BR>
 *   ＜検索条件＞ <BR><DIR>
 *     状態フラグ：未開始</DIR>
 * </DIR>
 * <BR>
 * 2.次へボタン押下処理（<CODE>nextCheck()</CODE>メソッド）<BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、作業者コード・パスワードのチェック、該当データ存在チェック結果を返します。 <BR>
 *   該当データが出荷予定情報に存在し、作業者コード、パスワードの内容が正しい場合はtrueを返します。<BR>
 *   該当データが出荷予定情報に存在しない場合、またはパラメータの内容に問題がある場合はfalseを返します。
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力<BR><DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     荷主コード* <BR>
 *     出荷予定日* <BR>
 *     出荷先コード* <BR>
 *     出荷伝票No.* <BR></DIR>
 * </DIR>
 * <BR>
 * 3.次へボタン押下処理（<CODE>query()</CODE>メソッド）<BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   作業情報の状態フラグが未開始のデータのみを取得し、行No.順に表示を行います。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力<BR><DIR>
 *     荷主コード* <BR>
 *     出荷予定日* <BR>
 *     出荷先コード* <BR>
 *     出荷伝票No.* <BR></DIR>    
 * <BR>
 * </DIR>
 * <BR>
 * 4.入力ボタン押下処理（<CODE>check()</CODE>メソッド） <BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェック・オーバーフローチェック・重複チェック・排他チェック結果を返します。 <BR>
 *   正しい値が入力された場合、該当データの状態フラグ、最終更新日時が変更されていない場合はtrueを、 <BR>
 *   条件エラーが発生した場合や排他エラーが発生した場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力 <BR><DIR>
 *     出荷予定一意キー* <BR>
 *     出荷予定日* <BR>
 *     荷主コード* <BR>
 *     出荷伝票No.* <BR>
 *     出荷伝票行No.* <BR>
 *     商品コード* <BR>
 *     商品名称 <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     ホスト予定ケース数 <BR>
 *     ホスト予定ピース数 <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     TC/DC区分* <BR>
 *     仕入先コード <BR>
 *     仕入先名称 <BR>
 *     入荷伝票No. <BR>
 *     入荷伝票行No. <BR>
 *     最終更新日時* <BR></DIR>
 * <BR>
 *   ＜必須チェック内容＞ <BR>
 *     1.TC/DC区分：クロスまたはTC指定時、仕入先コードが入力されていること。 <BR>
 *     2.TC/DC区分：クロス指定時、入荷伝票No.、入荷伝票行No.が入力されていること。 <BR>
 *     3.ケース数に値が入っていた場合にはケース入数が1以上であること。 <BR>
 * <BR>
 * </DIR>
 * <BR>
 * 5.削除、全削除ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、出荷予定データ削除処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.出荷予定一意キーの出荷予定情報テーブルがデータベースに存在すること。 <BR>
 *     2.最終更新日時と出荷予定情報テーブルの最終更新日時の値が一致すること。（排他チェック） <BR>
 * <BR>
 *   ＜更新処理＞ <BR>
 *     ※デッドロックを防ぐため、作業情報、出荷予定情報、在庫情報の順番でテーブルの更新を行う。 <BR>
 *     -出荷予定情報テーブル(DNSHIPPINGPLAN)の更新 <BR><DIR>
 *       状態フラグを削除に更新する。 <BR></DIR>
 * <BR>
 *     -作業情報テーブル(DNWORKINFO)の更新 <BR><DIR>
 *       更新された出荷予定情報の出荷予定一意キーに紐づく作業情報の状態フラグを削除に更新する。 <BR></DIR>
 * <BR>
 *     -在庫情報テーブル(DMSTOCK)の更新 <BR><DIR>
 *       更新された作業情報の在庫IDに紐づく在庫情報の更新処理を行う。 <BR><DIR>
 *         在庫数を出荷予定数減算した値に更新する。 <BR>
 *         引当数を出荷予定数減算した値に更新する。 <BR></DIR></DIR>
 * <BR>
 *     -次作業情報テーブル(DNNEXTPROC)の更新 <BR><DIR>
 *       更新された出荷予定情報の出荷予定一意キーに紐づく次作業情報の状態フラグを削除に更新する。 <BR></DIR>
 * <BR>
 * </DIR>
 * <BR>
 * 6.修正登録ボタン押下処理（<CODE>startSCHgetParams()</CODE>メソッド） <BR><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、出荷予定データ修正処理を行います。 <BR>
 *   処理が正常に完了した場合はためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はnullを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR><DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR></DIR>
 *     2.出荷予定一意キーの出荷予定情報テーブルがデータベースに存在すること。 <BR>
 *     3.パラメータの最終更新日時と出荷予定情報テーブルの最終更新日時の値が一致すること。（排他チェック） <BR>
 *     4.TC/DC区分：クロスまたはTC指定時、仕入先コードが入力されていること。 <BR>
 *     5.TC/DC区分：クロス指定時、入荷伝票No.、入荷伝票行No.が入力されていること。 <BR>
 *     6.ケース数に値が入っていた場合にはケース入数が1以上であること。 <BR>
 * <BR>
 *   ＜更新処理＞ <BR>
 *     ※デッドロックを防ぐため、作業情報、出荷予定情報、在庫情報の順番でテーブルの更新を行う。 <BR>
 *     1.パラメータの出荷予定一意キーに紐づく作業情報の状態フラグを削除に更新する。 <BR>
 *     2.パラメータの出荷予定一意キーに該当する出荷予定情報の状態フラグを削除に更新する。 <BR>
 *     3.更新した作業情報の在庫IDに紐づく在庫情報の在庫数、引当数を出荷予定数減算した値に更新する。 <BR>
 *     4.パラメータの出荷予定一意キーに紐づく次作業情報の状態フラグを削除に更新する。 <BR>
 * <BR>
 *   ＜登録処理＞ <BR>
 *     -作業情報テーブル(DNWORKINFO)の更新登録 <BR>
 *     今回登録した出荷予定情報の内容をもとに作業情報を登録する。 <BR>
 * <BR>
 *     -出荷予定情報テーブル(DNSHIPPINGPLAN)の更新登録 <BR>
 *     受け取ったパラメータの内容をもとに出荷予定情報を登録する。 <BR>
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
 *     -在庫情報テーブル(DMSTOCK)の更新 <BR>
 *     今回登録した出荷予定情報の内容をもとに在庫情報を登録する。 <BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/04</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * <TR><TD>2004/12/15</TD><TD>Y.Okamura</TD><TD>ShippingPlanOperatorを使用するように変更</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:22:57 $
 * @author  $Author: suresh $
 */
public class ShippingPlanModifySCH extends AbstractShippingSCH
{
	// Class variables -----------------------------------------------

	/**
	 * 処理名
	 */
	private static String wProcessName = "ShippingPlanModifySCH";
	
	/**
	 * 出荷予定情報ハンドラ
	 */
	private ShippingPlanHandler wPlanHandler = null;

	/**
	 * 出荷予定検索キー
	 */
	private ShippingPlanSearchKey wPlanKey = null;

	/**
	 * 作業情報ハンドラ
	 */
	private WorkingInformationHandler wWorkHandler = null;

	/**
	 * 作業情報検索キー
	 */
	private WorkingInformationSearchKey wWorkKey = null;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.3 $,$Date: 2006/11/21 04:22:57 $" );
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public ShippingPlanModifySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 出荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
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
		// 検索条件を設定する。
		// 状態フラグ＝「未開始」
		wKey.setStatusFlag( ShippingPlan.STATUS_FLAG_UNSTART );
		// グルーピング条件に荷主コードをセット
		wKey.setConsignorCodeGroup( 1 );
		wKey.setConsignorCodeCollect( "DISTINCT" );
		ShippingPlanReportFinder sRFinder = new ShippingPlanReportFinder(conn) ;
		
		try
		{
			if(sRFinder.search(wKey) == 1)
			{
				ShippingPlan[] sPlan = null ;
				sPlan = (ShippingPlan[])sRFinder.getEntities(1) ;
				wParam.setConsignorCode(sPlan[0].getConsignorCode());
			}
		
		}
		catch(ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			sRFinder.close();
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
		// 荷主名称
		String wConsignorName = "";
		// 出荷先名称
		String wCustomerName = "";
		
		// 出荷予定情報ハンドラ類のインスタンス生成
		ShippingPlanHandler wObj = new ShippingPlanHandler( conn );
		ShippingPlanSearchKey wKey = new ShippingPlanSearchKey();
		ShippingPlanSearchKey nameKey = new ShippingPlanSearchKey();
		ShippingPlan[] wShipping = null;
		ShippingPlan[] wShippingName = null;

		ShippingParameter wParam = ( ShippingParameter )searchParam;
			
		// 検索条件を設定する。
		// 荷主コード
		wKey.setConsignorCode( wParam.getConsignorCode() );
		nameKey.setConsignorCode( wParam.getConsignorCode() );
		// 出荷予定日
		wKey.setPlanDate( wParam.getPlanDate() );
		nameKey.setPlanDate( wParam.getPlanDate() );
		// 出荷先コード
		wKey.setCustomerCode( wParam.getCustomerCode() );
		nameKey.setCustomerCode( wParam.getCustomerCode() );
		// 出荷伝票No.
		wKey.setShippingTicketNo( wParam.getShippingTicketNo() );
		nameKey.setShippingTicketNo( wParam.getShippingTicketNo() );
		// 状態フラグ（未開始）
		wKey.setStatusFlag( ShippingPlan.STATUS_FLAG_UNSTART );
		nameKey.setStatusFlag( ShippingPlan.STATUS_FLAG_UNSTART );
			
		// 出荷伝票行No.順でソート順をセット
		wKey.setShippingLineNoOrder( 1, true );
		wKey.setItemCodeOrder(2, true);
		wKey.setSupplierCodeOrder(3, true);
			
		if(!super.canLowerDisplay(wObj.count( wKey )))
		{
			return super.returnNoDiaplayParameter();
		}
			
		// 出荷予定情報の検索結果を取得する。
		wShipping = ( ShippingPlan[] )wObj.find( wKey );
			
		// 登録日時の最も新しい荷主、出荷先名称を取得する。
		nameKey.setConsignorNameCollect("");
		nameKey.setCustomerName1Collect("");
		nameKey.setRegistDateOrder( 1, false );
			
		wShippingName = ( ShippingPlan[] )wObj.find( nameKey );
		if ( wShippingName != null && wShippingName.length > 0 )
		{
			wConsignorName = wShippingName[ 0 ].getConsignorName();
			wCustomerName = wShippingName[ 0 ].getCustomerName1();
		}
			
		// Vectorのインスタンス生成
		Vector vec = new Vector();
			
		for ( int i = 0; i < wShipping.length; i ++ )
		{
			ShippingParameter wTemp = new ShippingParameter();
				
			// 荷主コード
			wTemp.setConsignorCode( wShipping[ i ].getConsignorCode() );
			// 荷主名称
			wTemp.setConsignorName( wConsignorName );
			// 出荷予定日
			wTemp.setPlanDate( wShipping[ i ].getPlanDate() );
			// 出荷先コード
			wTemp.setCustomerCode( wShipping[ i ].getCustomerCode() );
			// 出荷先名称
			wTemp.setCustomerName( wCustomerName );
			// 出荷伝票No.
			wTemp.setShippingTicketNo( wShipping[ i ].getShippingTicketNo() );
			// 出荷伝票行No.
			wTemp.setShippingLineNo( wShipping[ i ].getShippingLineNo() );
			// 商品コード
			wTemp.setItemCode( wShipping[ i ].getItemCode() );
			// 商品名称
			wTemp.setItemName( wShipping[ i ].getItemName1() );
			// ケース入数
			wTemp.setEnteringQty( wShipping[ i ].getEnteringQty() );
			// ボール入数
			wTemp.setBundleEnteringQty( wShipping[ i ].getBundleEnteringQty() );
			// ホスト予定ケース数
			wTemp.setPlanCaseQty( DisplayUtil.getCaseQty( wShipping[ i ].getPlanQty(), wShipping[ i ].getEnteringQty() ) );
			// ホスト予定ピース数
			wTemp.setPlanPieceQty( DisplayUtil.getPieceQty( wShipping[ i ].getPlanQty(), wShipping[ i ].getEnteringQty() ) );
			// ケースITF
			wTemp.setITF( wShipping[ i ].getItf() );
			// ボールITF
			wTemp.setBundleITF( wShipping[ i ].getBundleItf() );
			// TC/DC区分名称
			wTemp.setTcdcFlagName( DisplayUtil.getTcDcValue( wShipping[ i ].getTcdcFlag() ) );
			// TC/DC区分
			wTemp.setTcdcFlag( wShipping[ i ].getTcdcFlag() );
			// 仕入先コード
			wTemp.setSupplierCode( wShipping[ i ].getSupplierCode() );
			// 仕入先名称
			wTemp.setSupplierName( wShipping[ i ].getSupplierName1() );
			// 入荷伝票No.
			wTemp.setInstockTicketNo( wShipping[ i ].getInstockTicketNo() );
			// 入荷伝票行No.
			wTemp.setInstockLineNo( wShipping[ i ].getInstockLineNo() );
			// 出荷予定一意キー
			wTemp.setShippingPlanUkey( wShipping[ i ].getShippingPlanUkey() );
			// 最終更新日時
			wTemp.setLastUpdateDate( wShipping[ i ].getLastUpdateDate() );
			vec.addElement( wTemp );
		}
		wSParam = new ShippingParameter[ vec.size() ];
		vec.copyInto( wSParam );
		
		// 6001013 = 表示しました。
		wMessage = "6001013";
		
		return wSParam;
	}

	/** 
	 * 画面から入力された溜めうちエリアの内容をパラメータとして受け取り、排他チェック結果を返します。 <BR>
	 * パラメータの出荷予定一意キーをキーに出荷予定情報を検索し、該当データが存在しなかった場合、 <BR>
	 * または最終更新日時が更新されていた場合は排他エラーとし、falseを返します。 <BR>
	 * 該当データが存在し、最終更新日時に変更がなければtrueを返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンス。 <BR>
	 *        ShippingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public boolean check( Connection conn, Parameter checkParam ) throws ScheduleException,ReadWriteException
	{
		try
		{
			// パラメータの検索条件
			ShippingParameter wParam = ( ShippingParameter )checkParam;
			
			// 検索キーをセットする
			wWorkKey.setPlanUkey(wParam.getShippingPlanUkey());
			
			// 検索結果がなかった場合(データが削除された)
			if (wWorkHandler.count(wWorkKey) == 0)
			{
				return false;
			}

			// 検索条件を設定する。
			// 出荷予定一意キー
			wPlanKey.setShippingPlanUkey( wParam.getShippingPlanUkey() );
			
			 ShippingPlan wShipping = ( ShippingPlan )wPlanHandler.findPrimary( wPlanKey );
			
			// 該当情報がない場合他端末エラー
			if ( wShipping == null )
			{
				return false;
			}
			
			// 最終更新日時が一致しない場合、他端末エラー
			if ( !wShipping.getLastUpdateDate().equals( wParam.getLastUpdateDate() ) )
			{
				return false;
			}
		}
		catch ( NoPrimaryException e )
		{
			throw new ScheduleException( e.getMessage() );
		}
		return true;
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
		
		// 条件による必須チェックを行う。
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
		if ( ( long )wParam.getEnteringQty() * ( long )wParam.getPlanCaseQty() + wParam.getPlanPieceQty() > WmsParam.MAX_TOTAL_QTY)
		{
			// 6023058 = {0}には{1}以下の値を入力してください。
			wMessage = "6023058" + wDelim + DisplayText.getText( "LBL-W0379" ) + wDelim + AbstractSCH.MAX_TOTAL_QTY_DISP;
			return false;
		}
		
		// ハンドラ類の生成を行う
		getShippingPlanHandler(conn);
		getWorkingInformationHandler(conn);
		// 排他チェックを行う。
		if (!check(conn, wParam))
		{
			// 他端末エラーが発生した場合、処理終了
			// 6003006=このデータは、他の端末で更新されたため処理できません。
			wMessage = "6003006";
			return false;
		}
		
		// 重複チェックを行う。
		// ためうちとの重複チェックを行う
		if ( wParamList != null )
		{
			for ( int i = 0; i < wParamList.length; i ++ )
			{
				if ( wParamList[ i ].getShippingLineNo()==wParam.getShippingLineNo() ) 
				{
					// 6023091 = 既に同一の行No．が存在するため、入力できません。
					wMessage = "6023091";
					return false;
				}
			}
		}
		
		// DBとの重複チェックを行う
		ShippingPlanOperator shipPlanOperator = new ShippingPlanOperator(conn);
		ShippingPlan shipPlan = shipPlanOperator.findShippingPlan(wParam);
		
		if (shipPlan != null)
		{
			// 作業中
			if (shipPlan.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_NOWWORKING))
			{
				// 6023269 = 作業中の同一データが存在するため、入力できません。
				wMessage = "6023269";
				return false;
			}
			// 一部受付済み
			else if (shipPlan.getStatusFlag().equals( ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART ))
			{
				// 6023270 = 一部受付済みの同一データが存在するため、入力できません。
				wMessage = "6023270";
				return false;
			}
			// 完了
			else if (shipPlan.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_COMPLETION))
			{
				// 6023337 = 完了の同一データが存在するため、入力できません。
				wMessage = "6023337";
				return false;
			}
			// 未開始、開始済
			else
			{
				// 6023090 = 既に同一データが存在するため、入力できません。
				wMessage = "6023090";
				return false;
			}
			
		}
		if (!shipPlanOperator.findNextProcTc(wParam, false))
		{
			// 6023378=入荷伝票Noには対応する出荷予定が存在する為、修正できません。
			wMessage = "6023378" + wDelim + wParam.getRowNo();
			return false;
		}	

		// 6001019 = 入力を受け付けました。
		wMessage = "6001019";
		
		return true;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、該当データ存在チェック、作業者コード、パスワードのチェック結果を返します。 <BR>
	 * 該当データが存在し、作業者コード、パスワードの内容が正しい場合はtrueを返します。<BR>
	 * 該当データが出荷予定情報に存在しない場合、またはパラメータの内容に問題がある場合はfalseを返します。
	 * エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param checkParam 入力内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンス。 <BR>
	 *        ShippingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean nextCheck( Connection conn, Parameter checkParam ) throws ReadWriteException, ScheduleException
	{	
		
		ShippingParameter wParam = ( ShippingParameter ) checkParam;
		
		// 作業者コード、パスワードチェック
		if (!super.checkWorker(conn, wParam))
		{
			return false;
		}

		// 出荷予定情報ハンドラ類のインスタンス生成
		ShippingPlanHandler wSObj = new ShippingPlanHandler( conn );
		ShippingPlanSearchKey wSKey = new ShippingPlanSearchKey();
	
		// 検索条件を設定する。
		// 荷主コード
		wSKey.setConsignorCode( wParam.getConsignorCode() );
		// 出荷予定日
		wSKey.setPlanDate( wParam.getPlanDate() );
		// 出荷先コード
		wSKey.setCustomerCode( wParam.getCustomerCode() );
		// 出荷伝票No.
		wSKey.setShippingTicketNo( wParam.getShippingTicketNo() );
		// 状態フラグ（未開始）
		wSKey.setStatusFlag( ShippingPlan.STATUS_FLAG_UNSTART );
		
		// 出荷予定情報の検索結果件数を取得する。
		if(!super.canLowerDisplay(wSObj.count( wSKey )))
		{
			return false;
		}
		return true;
		
	}
		
	/**
	 * 画面から入力された内容をパラメータとして受け取り、出荷予定データ削除スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         ShippingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean startSCH( Connection conn, Parameter[] startParams )
		throws ReadWriteException, ScheduleException
	{
		
		// 日次更新処理中のチェック
		if (isDailyUpdate(conn))
		{
			return false;
		}
		// 取込み中フラグのチェック
		if (isLoadingData(conn))
		{
			return false;
		}
		
		// DBのcommit,rollbackの判断用
		boolean registFlag = false;
		// 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
		boolean updateLoadDataFlag = false;

		try
		{
			// 取り込みフラグ更新：取り込み中
			if (!updateLoadDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn,wProcessName);
			updateLoadDataFlag = true;
			
			// パラメータの入力情報
			ShippingParameter[] wParam = ( ShippingParameter[] )startParams;
			
			ShippingPlanOperator shipPlanOperator = new ShippingPlanOperator(conn);
			getShippingPlanHandler(conn);
			getWorkingInformationHandler(conn);
			
			for ( int i = 0; i < wParam.length; i ++ )
			{

				// 排他チェックを行う。
				if ( !lock(wParam[ i ] ) )
				{
					// 他端末エラーが発生した場合、処理終了
					// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
					wMessage = "6023209" + wDelim + wParam[ i ].getRowNo();
					return false;
				}
				
				// 更新処理を行う
				shipPlanOperator.updateShippingPlan(wParam[i].getShippingPlanUkey(), wProcessName);
				
			}

			// 6001005 = 削除しました。
			wMessage = "6001005";
			registFlag = true;
			
			return true;
			
		}
		catch (ReadWriteException e)
		{
			doRollBack(conn, wProcessName);
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			doRollBack(conn, wProcessName);
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			// 登録に失敗した場合はトランザクションをロールバックする
			if (!registFlag)
			{
				doRollBack(conn, wProcessName);
			}
			// 取り込み中フラグが自クラスで更新されたものであるならば、
			// 取り込み中フラグを、0：停止中にする
			if( updateLoadDataFlag )
			{
				// 取り込み中フラグ更新：停止中
				updateLoadDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}
		}	
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、出荷予定データ修正スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * 処理が正常に完了した場合はためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
	 * 条件エラーなどでスケジュールが完了しなかった場合はnullを返します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         ShippingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュール処理が正常した場合は出荷予定情報クラス、スケジュール処理が失敗するか、スケジュールできない場合はnull
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams( Connection conn, Parameter[] startParams ) 
		throws ReadWriteException, ScheduleException
	{
		ShippingParameter[] viewParam = null;

		// パラメータの入力情報
		ShippingParameter[] wParam = ( ShippingParameter[] )startParams;
		
		// 日次更新処理中のチェック
		if (isDailyUpdate(conn))
		{
			return null;
		}
		// 取り込みフラグチェック処理 null：取り込み中
		if (isLoadingData(conn))
		{
			return null;
		}

		// DBのcommit,rollbackの判断用
		boolean registFlag = false;
		// 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
		boolean updateLoadDataFlag = false;

		try
		{
			// 取り込みフラグ更新：取り込み中
			if (!updateLoadDataFlag(conn, true))
			{
				return null;
			}
			doCommit(conn,wProcessName);
			updateLoadDataFlag = true;
			
			if ( wParam != null )
			{
				// 対象データ全件のロックを行う。
				if (!super.lockPlanUkeyAll(conn, startParams))
				{
					// 6003006=このデータは、他の端末で更新されたため処理できません。
					wMessage = "6003006";
					return null;
				}
			}
			
			// SequenceHandlerのインスタンス生成
			SequenceHandler wSequence = new SequenceHandler( conn );
			// バッチNo.
			String batch_seqno = wSequence.nextShippingPlanBatchNo();
			// 出荷予定情報オペレータクラス
			ShippingPlanOperator shipPlanOperator = new ShippingPlanOperator(conn);
			// 作業者名称
			String workerName = super.getWorkerName(conn, wParam[0].getWorkerCode());
			// 検索クラスの初期化
			getShippingPlanHandler(conn);
			getWorkingInformationHandler(conn);
			
			for ( int i = 0; i < wParam.length; i ++ )
			{
				// 排他チェックを行う。
				if ( !this.lock(wParam[ i ] ) )
				{
					// 他端末エラーが発生した場合、処理終了
					// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
					wMessage = "6023209" + wDelim + wParam[ i ].getRowNo();
					return null;
				}

				// DBに同一情報があるかチェックする
				ShippingPlan	shipPlan = shipPlanOperator.findShippingPlanForUpdate(wParam[i]);
				if (shipPlan != null)
				{
					// 削除以外は登録できない
					if (shipPlan.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_UNSTART)
					  || shipPlan.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_START) 
					  || shipPlan.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_NOWWORKING)
					  || shipPlan.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART)
					  || shipPlan.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_COMPLETION))
					{
						// 6023273=No.{0}{1}
						// 6006007=既に同一データが存在するため、登録できません。
						wMessage = "6023273" + wDelim + wParam[i].getRowNo() + wDelim + MessageResource.getMessage("6006007");
						return null;
					}
					else
					{
						// 該当情報を削除する
						shipPlanOperator.updateShippingPlan(shipPlan.getShippingPlanUkey(), wProcessName);
					}
				}
				
				// 取り込みをスキップするかどうか判定
				// クロスドックパッケージありでかつTCでかつ、更新対象データとは別データに同一の入荷伝票Noと入荷伝票行No.が
				// 次作業情報にある場合は、更新せずにスキップする。
				if (shipPlanOperator.findNextProcTc(wParam[i], false))
				{
					// 元情報の削除を行う
					shipPlanOperator.updateShippingPlan(wParam[i].getShippingPlanUkey(), wProcessName);
				
					// パラメータに登録する値をセットする。
					// 出荷予定数(バラ総数)
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
				
					// 入力情報を新規に登録する(仕分予定情報、作業情報、在庫情報、次作業情報)
					shipPlanOperator.createShippingPlan(wParam[i]);
				}
				else
				{
					// 6023379=No.{0} 入荷伝票Noには対応する出荷予定が存在する為、修正できません。
					wMessage = "6023379" + wDelim + wParam[i].getRowNo();
					return null;
				}
			}
			
			registFlag = true;
			
			// 出荷予定情報を再検索する。
			viewParam = ( ShippingParameter[] )query( conn, wParam[ 0 ] );
			
			// 6001004 = 修正しました。
			wMessage = "6001004";	
			
		}		
		catch (ReadWriteException e)
		{
			doRollBack(conn, wProcessName);
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{	
			doRollBack(conn, wProcessName);
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			// 登録に失敗した場合はトランザクションをロールバックする
			if (!registFlag)
			{
				doRollBack(conn, wProcessName);
			}
			// 取り込み中フラグが自クラスで更新されたものであるならば、
			// 取り込み中フラグを、0：停止中にする
			if( updateLoadDataFlag )
			{
				// 取り込み中フラグ更新：停止中
				updateLoadDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}
		}

		// 最新の出荷予定情報を返す。
		return viewParam;

	}
	/**
	 * 出荷予定情報のハンドラと検索キーの生成を行う
	 * 
	 * @param conn データベースへのコネクションオブジェクト
	 */
	protected void getShippingPlanHandler(Connection conn)
	{
		wPlanHandler = new ShippingPlanHandler(conn);
		wPlanKey = new ShippingPlanSearchKey();
	}

	/**
	 * 作業情報のハンドラと検索キーの生成を行う
	 * 
	 * @param conn データベースへのコネクションオブジェクト
	 */
	protected void getWorkingInformationHandler(Connection conn)
	{
		wWorkHandler = new WorkingInformationHandler(conn);
		wWorkKey = new WorkingInformationSearchKey();
	}

	/**
	 * 更新対象となる作業情報・出荷予定情報を検索しロックする。<BR>
	 * このメソッドを呼ぶ前に、インスタンスの生成メソッドを呼ぶこと。<BR>
	 * 更新対象データを正常に検索・ロックできた場合true、他端末エラーなどで検索できなかった場合はfalseを返す。<BR>
	 * 以下の場合、他端末エラーとみなす。<BR>
	 * <DIR>
	 *   ・作業情報を検索し、検索結果がなかった場合<BR>
	 *   ・出荷予定情報を検索し、検索結果がなかった場合<BR>
	 *   ・出荷予定情報を検索し、画面のデータと最終更新日時がちがった場合
	 * </DIR>
	 * 
	 * @param inputParam 更新対象データを含む<CODE>ShippingParameter</CODE>
	 * @return 更新対象データを検索・ロックできたかどうか
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected boolean lock(ShippingParameter inputParam) throws ReadWriteException
	{
		// 作業情報を検索する
		wWorkKey.KeyClear();
		wWorkKey.setPlanUkey(inputParam.getShippingPlanUkey());
		WorkingInformation[] workInfo = null;
		workInfo = (WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);

		// 作業情報に該当データが存在しない(他端末で更新された)
		if (workInfo == null || workInfo.length == 0)
			return false;

		// 出荷予定情報を検索する
		wPlanKey.KeyClear();
		wPlanKey.setShippingPlanUkey(inputParam.getShippingPlanUkey());
		ShippingPlan shipping = null;
		try
		{
			shipping = (ShippingPlan) wPlanHandler.findPrimaryForUpdate(wPlanKey);
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		// 出荷予定情報に該当データが存在しない(他端末で更新された)
		if (shipping == null)
			return false;

		// 出荷予定情報の最終更新日時が一致しない(他端末で更新された)
		if (!shipping.getLastUpdateDate().equals(inputParam.getLastUpdateDate()))
			return false;

		return true;

	}	
}
//end of class
