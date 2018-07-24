// $Id: Id0120Operate.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdOperate;

/**
 * Designer : T.Konishi <BR>
 * Maker :   <BR>
 * <BR>
 * 仕入先一覧要求(ID0120)処理を行うためのクラスです。<BR>
 * <CODE>IdOperate</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 予定日、荷主、入荷作業種別をパラメータとして受け取り、
 * 作業情報から仕入先一覧情報を取得し、<BR>
 * 仕入先一覧(仕入先コード昇順)ファイルの作成を行います。<BR>
 * <BR>
 * 仕入先一覧取得(<CODE>getSupplierList()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   予定日、荷主、入荷作業種別をパラメータとして受け取り、仕入先一覧情報を返します。<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class Id0120Operate extends IdOperate
{
	// Constructors --------------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:14 $";
	}

	// Public methods ------------------------------------------------
	/**
	 * 作業情報(dnworkinfo)より指定した条件で入荷検品の作業対象となる仕入先一覧を取得します。<BR>
	 * 作業区分が入荷、入荷作業種別に応じたTC/DC区分、状態フラグが未開始で作業開始フラグが開始済の作業データより<BR>
	 * 指定した条件(作業予定日、荷主)で、仕入先コード、仕入先名称のデータ一覧を<BR>
	 * 仕入先コード、仕入先名称昇順で取得します。<BR>
	 * 検索条件はInstockReceiveOperateクラスの機能を使用して取得します。
	 * 但し、重複データは除きます。<BR>
	 * <BR>
	 *     <DIR>
	 *     [取得項目]<BR>
	 *         <DIR>
	 *         仕入先コード<BR>
	 *         仕入先名称<BR>
	 *         </DIR>
	 *     [検索条件]<BR>
	 *         <DIR>
	 *         作業予定日    :パラメータより取得<BR>
	 *         荷主コード    :パラメータより取得<BR>
	 *         TC/DC区分     :入荷作業種別が1の場合TC。2の場合はDCまたはクロスTC。<BR>
	 *         作業区分      :入荷<BR>
	 *         状態フラグ    :未開始<BR>
	 *         作業開始フラグ:開始済または作業中の場合は自端末で更新したもの<BR>
	 *         オーダーNo    :空以外<BR>
	 *         </DIR>
	 *     [集約条件]<BR>
	 *         <DIR>
	 *         仕入先コード<BR>
	 *         仕入先名称<BR>
	 *         </DIR>
	 *     [ソート順]<BR>
	 *         <DIR>
	 *         仕入先コード<BR>
	 *         仕入先名称<BR>
	 *         </DIR>
	 *     </DIR>
	 * @param  planDate			作業予定日
	 * @param  consignorCode	荷主コード
	 * @param  workType			入荷作業種別
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @return 			取得した仕入先情報（作業情報エンティティの配列）
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  オーダー情報が見つからない場合に通知されます。
	 */
	public WorkingInformation[] getSupplierList(
	        String planDate,
	        String consignorCode,
	        String workType,
	        String workerCode,
	        String rftNo)
		throws ReadWriteException, NotFoundException
	{
		// 取得した仕入先情報を保持します。
		WorkingInformation[] workinfo = null;
		
		InstockReceiveOperate instockOperate = new InstockReceiveOperate();
		instockOperate.setConnection(wConn);
		WorkingInformationSearchKey skey = null;
		if (workType.equals(InstockWorkType.TC))
		{
		    // 入荷作業種別がTCの場合は出荷先別入荷検品用の処理を行う
			skey = instockOperate.getConditionOfInstockByCustomer(
			        planDate,
			        consignorCode,
			        null,
			        null,
			        workerCode,
			        rftNo
			        );
		}
		else
		{
		    // 入荷作業種別がTCでない場合は仕入先一括または商品単位入荷検品用の処理を行う
			skey = instockOperate.getConditionOfInstockBySupplier(
			        planDate,
			        consignorCode,
			        null,
			        null,
			        workerCode,
			        rftNo
			        );
		}

		//-----------------
		// 集約条件セット
		//-----------------
		skey.setSupplierCodeGroup(1);
		skey.setSupplierName1Group(2);
		
		skey.setSupplierCodeCollect("");
		skey.setSupplierName1Collect("");

		//-----------------
		// ソート順セット
		//-----------------
		skey.setSupplierCodeOrder(1, true);
		skey.setSupplierName1Order(2, true);
		
		WorkingInformationHandler handler = new WorkingInformationHandler(wConn);
		workinfo = (WorkingInformation[])handler.find(skey);
		
		if (workinfo != null && workinfo.length == 0)
		{
		    // 該当データなし
		    throw new NotFoundException();
		}
		return workinfo;
	}

	// Private methods -----------------------------------------------
}
//end of class
