// $Id: Id0510Operate.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

/*
 * Copyright 2004-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdOperate;

/**
 * Designer : T.Yamashita <BR>
 * Maker :   <BR>
 * <BR>
 * 出荷先問合せ(ID0510)処理を行うためのクラスです。<BR>
 * <CODE>IdOperate</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 予定日、荷主をパラメータとして受け取り、
 * 作業情報から出荷先情報を取得します。<BR>
 * <BR>
 * 出荷先情報取得(<CODE>getOrderList()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   作業予定日、荷主、出荷先、作業者、端末No.をパラメータとして受取り出荷先情報を返します。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/28</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class Id0510Operate extends IdOperate
{
	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public Id0510Operate()
	{
	}

	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public Id0510Operate(Connection conn)
	{
		wConn = conn;
	}

	// Public methods ------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $";
	}

	/**
	 * 作業情報(dnworkinfo)より指定した条件で出荷先名称を取得します。<BR>
	 * 作業区分が出荷、状態フラグが未開始で作業開始フラグが開始済の作業データより<BR>
	 * 指定した条件(作業予定日、荷主、出荷先コード、作業者コード、端末No.)で、出荷先コード、出荷先名称のデータを<BR>
	 * 取得します。<BR>
	 * 但し、重複データは除きます。<BR>
	 * ひとつの出荷先コードに対して複数の出荷先名称が該当する場合は
	 * もっとも値の大きい出荷先名称を取得します。
	 * <BR>
	 *     <DIR>
	 *     [取得項目]<BR>
	 *         <DIR>
	 *         出荷先コード<BR>
	 *         出荷先名称<BR>
	 *         </DIR>
	 *     [検索条件]<BR>
	 *         <DIR>
	 *         作業予定日    :パラメータより取得<BR>
	 *         荷主コード    :パラメータより取得<BR>
	 *         出荷先コード  :パラメータより取得<BR>
	 *         作業者コード  :パラメータより取得<BR>
	 *         端末No.       :パラメータより取得<BR>
	 *         作業区分      :出荷<BR>
	 *         状態フラグ    :未開始または作業中の場合は自端末で更新したもの<BR>
	 *         作業開始フラグ:開始済
	 *         </DIR>
	 *     </DIR>
	 * @param  planDate			作業予定日
	 * @param  consignorCode		荷主コード
	 * @param  customerCode		出荷先コード
	 * @param  workerCode		    作業者コード
	 * @param  rftNo		        端末No.
	 * @return String	 			出荷先名称
	 * @throws NotFoundException  該当データが見つからない場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public String getCustomerName(
	        String planDate,
	        String consignorCode,
	        String customerCode,
	        String workerCode,
	        String rftNo)
		throws ReadWriteException, NotFoundException
	{
		// WorkingInfoを検索した結果を保持する変数
		WorkingInformation[] workinfo = null;

		WorkingInformationSearchKey pskey = new WorkingInformationSearchKey();
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);
		
		// 作業区分が 出荷 を対象にする。
		pskey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		// 状態フラグが「未開始」又は、「作業中」(但し、作業者コードとRFTNoが同じに限る)を対象にする。 
		//  SQL文・・・ (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		pskey.setWorkerCode(workerCode);
		pskey.setTerminalNo(rftNo, "=", "", "))", "AND");

		// 作業開始フラグが「1:開始済」を対象にする。
		pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		pskey.setConsignorCode(consignorCode);
		pskey.setPlanDate(planDate);
		pskey.setCustomerCode(customerCode);
		// 出荷先名称のみ取得
		pskey.setCustomerName1Collect();
		// ソート順に登録日時を設定
		pskey.setRegistDateOrder( 1, false );

		workinfo = (WorkingInformation[])pObj.find(pskey);
		
		if (workinfo != null && workinfo.length == 0)
		{
		    // 該当データなし
		    throw new NotFoundException();
		}
		return workinfo[0].getCustomerName1();
	}
	/**
	 * パラメータとして受け取った作業情報エンティティから、<BR>
	 * その出荷先の未開始商品の数を取得します。<BR>
	 * @param  planDate			作業予定日
	 * @param  consignorCode		荷主コード
	 * @param  customerCode		出荷先コード
	 * @param  workerCode		    作業者コード
	 * @param  rftNo		        端末No.
	 * @return 未作業残アイテム数
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。<BR>
	 */
	public int remainItemCount(
		String planDate,
		String consignorCode,
		String customerCode,
		String workerCode,
		String rftNo)
		throws ReadWriteException
	{
		WorkingInformationSearchKey pskey = new WorkingInformationSearchKey();
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);

		pskey.setConsignorCode(consignorCode);
		pskey.setPlanDate(planDate);
		pskey.setCustomerCode(customerCode);
		// 状態フラグが「未開始」又は、「作業中」(但し、作業者コードとRFTNoが同じに限る)を対象にする。 
		//  SQL文・・・ (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		pskey.setWorkerCode(workerCode);
		pskey.setTerminalNo(rftNo, "=", "", "))", "AND");
		// 作業開始フラグが「1:開始済」を対象にする。
		pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		// 作業区分が 出荷 を対象にする。
		pskey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);

		pskey.setItemCodeCollect("");
		pskey.setItemCodeGroup(1);

		return pObj.count(pskey);

	}

	// Private methods -----------------------------------------------
}
//end of class
