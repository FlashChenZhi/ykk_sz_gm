/*
 * Created on 2006/01/11
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.schedule;

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ConsignorReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.CustomerOperator;
import jp.co.daifuku.wms.master.operator.MasterOperator;
/**
 * Designer : E.Takeda<BR>
 * Maker 	: E.Takeda<BR> 
 * <CODE>RegisterCustomerMasterSCH</CODE>クラスは出荷先マスタ登録処理を行うクラスです。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1. ユーザは下記のパラメータを入力します。<BR>
 * <DIR>
 *   <Parameter>* 必須入力<BR>
 * </DIR>
 * <DIR>
 * 	作業者コード : WorkerCode* <BR>
 *  パスワード   : Password* <BR>
 *  荷主コード   : ConsignorCode* <BR>
 *  出荷先コード : CustomerCode* <BR>
 *  出荷先名称   : CustomerName <BR>
 *  郵便番号     : PostalCode <BR>
 *  県名         : Prefecture <BR>
 *  住所         : Address <BR>
 *  ビル名等     : Address2 <BR>
 *  連絡先1      : Contact1 <BR>
 *  連絡先2      : Contact2 <BR>
 *  連絡先3      : Contact3 <BR>
 * </DIR>
 * <BR>
 * <BR>
 * 2.登録ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR><BR><DIR>
 * <DIR>
 *   画面に入力された内容をパラメータとして受け取り、出荷先マスタ登録処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR><BR> 
 * </DIR>
 * <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスタ情報に定義されていること。 <BR><DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR></DIR>
 *     2.同一荷主コード、出荷先コードの出荷先マスタ情報がデータベースに存在しないこと。 <BR>
 *     3.荷主コードが荷主マスタ情報に存在すること。 <BR>
 *     4.予定データ取込中、日次更新処理中でないこと。 <BR>
 * <BR>
 *   ＜登録処理＞ <code>CustomerOperator</code>クラスにて以下の処理を行います。 <BR>
 *     詳細は<code>CustomerOperator</code>を参照のこと。 <BR>
 *     -出荷先マスタテーブル(DMCUSTOMER)の登録 <BR>
 *       受け取ったパラメータの内容をもとに出荷先マスタ情報を登録する。 <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/01/10</TD><TD>E.Takeda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class RegistCustomerMasterSCH extends AbstractMasterSCH
{
	// Class variables -----------------------------------------------

	/**
	 * クラス名(出荷先登録処理)
	 */
	private static final String wProcessName = "RegisterCustomerMasterSCH";

	/**
	 * このクラスのバージョンを返します。
	 * 
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $. $Date: 2004/08/16 ");
	}

	/**
	 * このクラスの初期化を行ないます。
	 */
	public RegistCustomerMasterSCH()
	{
		wMessage = null;
	}
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 出荷マスタに荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// 該当する荷主コードがセットされます。
	    MasterParameter ent = new MasterParameter();

		// 荷主マスタハンドラ類のインスタンス生成
	    ConsignorSearchKey searchKey = new ConsignorSearchKey();
	    ConsignorReportFinder consignorFinder = new ConsignorReportFinder(conn);
	    Consignor[] wConsignor = null;

		try
		{
			// 検索条件を設定する。
			// データの検索
			// 状態フラグ＝「削除」以外
			searchKey.setDeleteFlag ("1", "!=");
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (consignorFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// 状態フラグ＝「削除」以外
				searchKey.setDeleteFlag("1", "!=");
				// ソート順に登録日時を設定
				searchKey.setLastUpdateDateOrder(1, false);
				
				searchKey.setConsignorNameCollect("");
				searchKey.setConsignorCodeCollect("");

				if (consignorFinder.search(searchKey) > 0)
				{
					// 登録日時が最も新しい荷主名称を取得します。
				    wConsignor = (Consignor[]) consignorFinder.getEntities(1);
				    ent.setConsignorName(wConsignor[0].getConsignorName());
				    ent.setConsignorCode(wConsignor[0].getConsignorCode());
				}
			}
			consignorFinder.close();
			
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			// 必ずCollectionをクローズする
		    consignorFinder.close();
		}
		
		return ent;
		
	}	
	/**
	 * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、<BR>
	 * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
	 * スケジュール処理が成功した場合はtrueを返します。<BR>
	 * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParams データベースとのコネクションオブジェクト
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// checkParamの型を変換
		MasterParameter masParam = (MasterParameter) startParams[0];
		Customer ent = new Customer();
		
		// 作業者コード、パスワードのチェックを行う。
		if (!checkWorker(conn, masParam, true))
		{
			return false;
		}

		// 日次更新処理中のチェック
		if (isDailyUpdate(conn))
		{
			return false;
		}

		// 取り込みフラグチェック処理 	
		if (isLoadingData(conn))
		{
			return false;
		}
		
		// 荷主マスタにパラメータの荷主コードが存在するかチェックを行う。
		ConsignorOperator consignorOperator = new ConsignorOperator(conn);
		Consignor consignor = new Consignor();
		consignor.setConsignorCode(masParam.getConsignorCode());

		if (consignorOperator.exist(consignor))
		{
			// 荷主コード
			ent.setConsignorCode(masParam.getConsignorCode());
			// 出荷先コード
			ent.setCustomerCode(masParam.getCustomerCode());
			// 出荷先名称
			ent.setCustomerName1(masParam.getCustomerName());
			// 郵便番号
			ent.setPostalCode(masParam.getPostalCode());
			// 県名
			ent.setPrefecture(masParam.getPrefecture());
			// 住所
			ent.setAddress(masParam.getAddress());
			// ビル名等
			ent.setAddress2(masParam.getAddress2());
			// 連絡先1
			ent.setContact1(masParam.getContact1());
			// 連絡先2
			ent.setContact2(masParam.getContact2());
			// 連絡先3
			ent.setContact3(masParam.getContact3());
			// 最終使用日
			ent.setLastUsedDate(new Date());
			// 最終更新処理名
			ent.setLastUpdatePname(wProcessName);
			
			// CustomerOperatorクラスのインスタンス生成
			CustomerOperator cOperate = new CustomerOperator(conn);
			
			// 出荷先マスタ登録処理
			int ret = cOperate.create(ent);
			
			// すでに登録済みの場合
			if (ret == MasterOperator.RET_EXIST)
			{
				// 6007007=すでに同一データが存在するため、登録できません。
				wMessage = "6007007";
				return false;
			}
			// 重複エラーの場合
			else if (ret == MasterOperator.RET_CONSIST_NAME_EXIST)
			{
				// 6023440=指定された名称はすでに使用されているため登録できません。
				wMessage = "6023440";
				return false;
			}	
			
			// 6001003=登録しました。
			wMessage = "6001003";
			return true;
		}
		else
		{
			// 6023439=荷主コードが登録されていません。（指定荷主コード={0}）
			wMessage = "6023439" + wDelim + masParam.getConsignorCode();
			return false;
		}
	}
}
