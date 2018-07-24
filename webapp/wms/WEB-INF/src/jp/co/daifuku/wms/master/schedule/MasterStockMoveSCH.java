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
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.MasterPrefs;
import jp.co.daifuku.wms.master.merger.MergerWrapper;
import jp.co.daifuku.wms.master.merger.StockMGWrapper;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.ItemOperator;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * WEB在庫移動作業を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、在庫移動処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * <B>1.初期表示処理（<CODE>initFind()</CODE>メソッド）</B> <BR>
 *   画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
 *   在庫管理パッケージが導入されているかどうかと初期表示データを返します。<BR>
 *   初期表示データについては以下のようになります。<BR>
 * <BR>
 * 在庫管理パッケージありの場合
 * <DIR>
 *   ・在庫情報に荷主コードが1件のみ存在した場合、該当する荷主コード・荷主名称を返します。<BR>
 *   ・該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 *   ・検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。<BR>
 *   ・荷主名称が複数存在する場合、一番登録日時が新しいものの荷主名称を取得します。<BR>
 *   ・検索には<CODE>StockReportFinder</CODE>を使用します。<BR>
 *   ・エリア区分=AS/RS以外のエリアNo.に紐付く在庫情報を検索します。<BR>
 * </DIR>
 * 在庫管理パッケージなしの場合
 * <DIR>
 *   ・初期表示は行いません。<BR>
 * </DIR>
 * <BR>
 * ＜検索テーブル＞
 * <DIR>
 *   DmStock<BR> 
 * </DIR>
 * <BR>
 * ＜検索条件＞
 * <DIR>
 *   センター在庫<BR>
 * </DIR>
 * <BR>
 * <B>2.入力ボタン押下処理<BR>（<CODE>check(Connection conn, Parameter checkParam, Parameter[] inputParams)</CODE>メソッド、<BR><CODE>query(Connection conn, Parameter searchParam)</CODE>メソッド）</B><BR>
 *   在庫パッケージなしの場合、checkメソッドのみを使用してください。<BR>
 *   在庫パッケージありの場合、checkメソッドを呼び、帰り値がtrueだった場合のみqueryメソッドを呼んでください。<BR>
 * <BR>
 * ＜パラメータ＞ *必須入力
 * <DIR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   荷主コード* <BR>
 *   荷主名称<BR>
 *   商品コード*<BR>
 *   商品名称<BR>
 *   移動元棚*<BR>
 *   賞味期限(在庫管理パッケージありの場合必須)<BR>
 *   ケース入数<BR>
 *   移動ケース数<BR>
 *   移動ピース数<BR>
 *   移動先棚*<BR>
 * </DIR>
 *   <BR>
 *   <I>checkメソッドは以下の処理を行います。<BR></I>
 *   入力データをためうちエリアに入力する際、そのデータが正しいかをチェックします。<BR>
 *   入力が正しい場合はtrue、誤っている場合はfalseを返します。<BR>
 *   以下のチェックを行います。<BR>
 * <DIR>
 *   ・作業者コード・パスワードの入力が正しいこと<BR>
 *   ・表示件数が最大表示件数をこえないこと<BR>
 *   ・移動ケース数か移動ピース数に入力があること<BR>
 *   ・ケース入数が0の場合、移動ケース数は入力不可<BR>
 *   ・移動元棚と移動先棚が同一棚でないこと<BR>
 *   ・総移動数がオーバーフローしないこと<BR>
 *   ・ためうちエリアに同一データが入力されていないこと<BR>
 *   ・指定された棚がAS/RS棚情報に存在しないこ。Shelfテーブルチェック-(<CODE>isAsrsLocation()</CODE>メソッド)<BR>
 * </DIR>
 * <BR>
 * ＜返却データ＞
 * <DIR>
 *   true：入力が正しい<BR>
 *   false：入力が誤り<BR>
 * </DIR>
 * <BR>
 *   <I>queryメソッドは以下のチェック・処理を行います。<BR></I>
 *   引き当て可能かをチェックし、引き当て可能ならば荷主名称・商品名称を取得します。<BR>
 *   なお、在庫パッケージなしの場合に本メソッドが呼ばれた場合、<CODE>ScheduleException</CODE>を投げます。<BR>
 *   以下の処理を行います。<BR>
 * <DIR>
 *   ・対象在庫が存在していること<BR>
 *   ・引当て可能数が入力移動数以上であること<BR>
 *   ・上記条件を満たし、荷主名称・商品名称が入力されていない場合、在庫情報から名称を取得します。<BR>
 * </DIR>
 * <BR>
 * ＜返却データ＞
 * <DIR>
 *   表示情報を含む<CODE>Parameter</CODE>インターフェースを実装したクラス<BR>
 * </DIR>
 * <BR>
 * <B>3.移動ボタン押下処理（<CODE>startSCH()</CODE>メソッド）</B> <BR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、在庫移動処理を行います。 <BR>
 *   スケジュールが正常に完了した場合はtrueを返します。<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   更新順は在庫情報更新(出庫引当)、在庫移動情報作成(移動出庫)、在庫移動情報更新(移動入庫)、
 * 在庫情報更新・登録(移動元・移動先の完了処理)、実績情報作成、作業者実績作成で行います。<BR>
 *   また、スケジュールが成功し、作業リスト発行にチェックがついている場合、
 * <CODE>startPrint</CODE>メソッドを呼び出し印刷を行います。<BR>
 *   印刷が成功・失敗どちらでもこのメソッドはtrueを返します。<BR>
 * <BR>
 * ＜パラメータ＞ *必須入力
 * <DIR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   荷主コード* <BR>
 *   荷主名称 <BR>
 *   商品コード* <BR>
 *   商品名称 <BR>
 *   移動元棚* <BR>
 *   賞味期限(在庫管理パッケージありの場合必須)<BR>
 *   ケース入数<BR>
 *   移動ケース数<BR>
 *   移動ピース数<BR>
 *   移動先棚*<BR>
 *   作業状態* <BR>
 *   移動作業リスト発行可否区分* <BR>
 *   ためうち行No*<BR>
 * </DIR>
 * <BR>
 * 以下の順で処理を行います。<BR>
 * <BR>
 * 1.処理条件チェック
 * <DIR>
 *   ・日次更新中でないこと。 <BR>
 *   ・作業者コードとパスワードが作業者マスターに定義されていること。 <BR>
 *   ・作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR>
 *   ・指定された棚がAS/RS棚情報に存在しないこ。Shelfテーブルチェック-(<CODE>isAsrsLocation()</CODE>メソッド)<BR>
 * </DIR>
 * 2.在庫情報更新(出庫引当)
 * <DIR>
 *   ・更新テーブル：DmStock<BR>
 *   ・移動元在庫のロックを行い、引当を行う。(<CODE>StockAllocateOperater.stockMovementAllocate</CODE>メソッド)<BR>
 *   ・引当できなかった場合、在庫が更新されているので他端末エラーとする。<BR>
 *   ＊在庫パッケージなしの場合、DmStockの更新は行わず、インスタンスのみを作成します。<BR>
 * </DIR>
 * 3.在庫移動情報作成(移動出庫)
 * <DIR>
 *   ・更新テーブル：DnMovement<BR>
 *   ・複数在庫に対して引当てた場合、1作業に対して同一集約作業No.で複数移動情報を作成する。<BR>
 * </DIR>
 * 4.在庫移動情報更新(移動入庫)
 * <DIR>
 *   ・更新テーブル：DnMovement<BR>
 *   ・3.で作成した移動情報を集約作業No.で取得し1件づつ更新する。<BR>
 *   ・(<CODE>MovementCompleteOperator.complete</CODE>メソッド)<BR>
 * </DIR>
 * 5.在庫情報更新・登録(移動入庫)、実績情報登録
 * <DIR>
 *   ・更新テーブル：DmStock、DnHostSend<BR>
 *   ・3.で作成した移動情報を集約作業No.で取得し1件づつ更新する。<BR>
 *   ・移動元・移動先の在庫をロックし更新を行う。(<CODE>MovementCompleteOperator.complete</CODE>メソッド)<BR>
 *   ・作業No.単位で実績情報を作成する。(<CODE>MovementCompleteOperator.complete</CODE>メソッド)<BR>
 * </DIR>
 * 6.作業者実績情報更新・登録
 * <DIR>
 *   ・更新テーブル：DnWorkerResult<BR>
 *   ・作業者実績情報を更新・登録する。<BR>
 * </DIR>
 * 7.印刷処理
 * <DIR>
 *   ・作業リスト発行にチェックがある場合<CODE>startPrint</CODE>メソッドを呼び出す。<BR>
 * </DIR>
 * 
 * <B>4.印刷処理(<CODE>startPrint(conn, jobNoVec)</CODE>メソッド) </B> <BR>
 *   設定が行われた作業No.を受け取り、在庫移動作業リスト発行処理クラスにパラメータを渡します。<BR>
 *   印刷内容の検索はWriterクラスで行います。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 * <BR>
 * ＜パラメータ＞ *必須入力<BR>
 * <DIR>
 *   作業No.(Vector)* <BR>
 * </DIR>
 * <BR>
 * ＜処理条件チェック＞
 * <DIR>
 *   なし<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterStockMoveSCH extends jp.co.daifuku.wms.storage.schedule.StockMoveSCH
{

	// Class variables -----------------------------------------------
	/**
	 * 処理名
	 */
	private static String wProcessName = "MasterStockMoveSCH";

	/**
	 * 補完ラッパクラス
	 */
	private StockMGWrapper wMGWrapper = null;

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
	public MasterStockMoveSCH()
	{
		wMessage = null;
	}

	/**
	 * このクラスを使用してDBの検索・更新を行う場合はこのコンストラクタを使用してください。 <BR>
	 * 各DBの検索・更新に必要なインスタンスを作成します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public MasterStockMoveSCH(Connection conn) throws ReadWriteException, ScheduleException
	{
		wMessage = null;
		wMGWrapper = new StockMGWrapper(conn);
		wConsignorOperator = new ConsignorOperator(conn);
		wItemOperator = new ItemOperator(conn);
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 在庫管理パッケージが導入されているかどうか、賞味期限管理の有無と初期表示データを返します。<BR>
	 * 初期表示データについては以下のようになります。<BR>
	 * <BR>
	 * 在庫管理パッケージありの場合<BR>
	 * <DIR>
	 *   在庫情報に荷主コードが1件のみ存在した場合、該当する荷主コード・荷主名称を返します。<BR>
	 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 *   検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。<BR>
	 *   荷主名称が複数存在する場合、一番登録日時が新しいものの荷主名称を取得します。<BR>
	 * </DIR>
	 * 在庫管理パッケージなしの場合<BR>
	 * <DIR>
	 *   初期表示は行いません。<BR>
	 * </DIR>
	 * <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>StorageSupportParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		// 荷主初期表示設定を行います。
		StorageSupportParameter tempParam = (StorageSupportParameter) super.initFind(conn, searchParam);
		// 取得した荷主を返却パラメータにセットします。
		MasterStorageSupportParameter wParam = new MasterStorageSupportParameter();
		if (tempParam != null)
		{
			wParam.setConsignorCode(tempParam.getConsignorCode());
			wParam.setConsignorName(tempParam.getConsignorName());
			wParam.setStockPackageFlag(tempParam.getStockPackageFlag());
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
	 * 入力データから在庫情報を検索し、ためうちエリア表示用に荷主名称・商品名称をセットして返します。 <BR>
	 * 賞味期限管理ありの場合、賞味期限を検索条件に加えます。<BR>
	 * なお、在庫パッケージなしの場合に本メソッドが呼ばれた場合、例外を投げます。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>StorageSupportParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return Parameter[] ためうち表示用データ
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException このメソッドが呼び出された場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		Stock stock = new Stock();
		
		MasterStorageSupportParameter param = (MasterStorageSupportParameter) searchParam;
		
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
			MasterStorageSupportParameter tempParam = (MasterStorageSupportParameter) searchParam;
			
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
		MasterStorageSupportParameter[] result = new MasterStorageSupportParameter[tempList.size()];
		tempList.toArray(result);
		return result;
	}

	/**
	 * 在庫移動画面より、入力ボタンが押された際に使用されるメソッドです。<BR>
	 * このメソッドは、入力データをためうちに追加できるかをチェックします。<BR>
	 * <DIR>
	 *   ・入力データが正しい場合：true<BR>
	 *   ・入力データが不正な場合：false<BR>
	 * </DIR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス
	 * @param checkParam 入力データをもつ<CODE>StorageSupportParameter</CODE>クラスのインスタンス
	 * @param inputParams 入力データと比較するためのためうちエリアの情報
	 * @return boolean 入力データが正しいかどうか
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException このメソッドが呼び出された場合に通知されます。
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ReadWriteException, ScheduleException
	{
		// 在庫管理パッケージなしの場合
		if (!isStockPack(conn))
		{
		    int iRet = 0;
			// 入力エリアの内容
			StorageSupportParameter param = (StorageSupportParameter) checkParam;
			// ためうちエリアの内容
			StorageSupportParameter[] paramlist = (StorageSupportParameter[]) inputParams;

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
		}
		
		// 既存の入力チェックを行う
		if (!super.check(conn, checkParam, inputParams))
		{
			return false;
		}
		
		// 6001019 = 入力を受け付けました。
		wMessage = "6001019";
		return true;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、在庫移動のスケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         StorageSupportParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return boolean スケジュールが正常終了したかどうか
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		if (!isStockPack(conn))
		{
			// 入力エリアの内容
			MasterStorageSupportParameter[] params = (MasterStorageSupportParameter[]) startParams;

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
	protected boolean checkModifyLastUpdateDate(MasterParameter param, MasterStorageSupportParameter inputParam, int rowNo)
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

	
	// Private methods -----------------------------------------------
}
//end of class
