// $Id: Id0110Operate.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdOperate;

/**
 * Designer : T.Konishi <BR>
 * Maker :   <BR>
 * <BR>
 * 入荷検品仕入先問合せ(ID0110)処理を行うためのクラスです。<BR>
 * <CODE>IdOperate</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 予定日、荷主、TC/DC区分をパラメータとして受け取り、
 * 作業情報から仕入先情報を取得します。<BR>
 * <BR>
 * 仕入先情報取得(<CODE>getSupplierName()</CODE>メソッド)<BR>
 * <DIR>
 *   予定日、荷主、TC/DC区分をパラメータとして受取り仕入先情報を返します。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class Id0110Operate extends IdOperate
{
	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:14 $";
	}

	/**
	 * 作業情報(dnworkinfo)より指定した条件で仕入先名称を取得します。<BR>
	 * 作業区分が入荷、状態フラグが未開始で作業開始フラグが開始済の作業データより<BR>
	 * 指定した条件(作業予定日、荷主、仕入先コード、TC/DC区分)で、仕入先名称を取得します。<BR>
	 * データの検索条件についてはInstockReceiveOperateのメソッドを使用して取得します。<BR>
	 * 但し、重複データは除きます。<BR>
	 * ひとつの仕入先コードに対して複数の仕入先名称が該当する場合は
	 * 辞書順に比較し値の大きい仕入先名称を取得します。
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
	 *         TC/DC区分     :パラメータより取得<BR>
	 *         作業区分      :入荷<BR>
	 *         状態フラグ    :未開始<BR>
	 *         作業開始フラグ:開始済または作業中の場合は自端末で更新したもの<BR>
	 *         </DIR>
	 *     [集約条件]<BR>
	 *         <DIR>
	 *         仕入先コード<BR>
	 *         </DIR>
	 *     </DIR>
	 * @param  planDate			作業予定日
	 * @param  consignorCode	荷主コード
	 * @param  supplierCode		仕入先コード
	 * @param  workType			入荷作業種別（TC or DC、クロスTC）
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT No
	 * @return 仕入先名称
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  オーダー情報が見つからない場合に通知されます。
	 * @throws IllegalAccessException	オブジェクトの生成に失敗した場合に通知されます。
	 */
	public String getSupplierName(
	        String planDate,
	        String consignorCode,
	        String supplierCode,
	        String workType,
	        String workerCode,
	        String rftNo)
		throws ReadWriteException, NotFoundException, IllegalAccessException
	{
		// 取得した荷主情報を保持します。
		WorkingInformation[] workinfo = null;
		
		// InstockReceiveOperateクラスのインスタンスを生成
		InstockReceiveOperate instockOperate =
		    (InstockReceiveOperate) PackageManager.getObject("InstockReceiveOperate");
		instockOperate.setConnection(wConn);

		WorkingInformationSearchKey skey = null;
		if (workType.equals(InstockWorkType.TC))
		{
		    // 入荷作業種別がTCの場合は出荷先別入荷検品用の処理を行う
			skey = instockOperate.getConditionOfInstockByCustomer(
			        planDate,
			        consignorCode,
			        supplierCode,
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
			        supplierCode,
			        null,
			        workerCode,
			        rftNo
			        );
		}

		//-----------------
		// 集約条件セット
		//-----------------
		skey.setSupplierCodeGroup(1);
		skey.setSupplierName1Collect("MAX");
		
		WorkingInformationHandler handler = new WorkingInformationHandler(wConn);
		workinfo = (WorkingInformation[])handler.find(skey);
		
		if (workinfo != null && workinfo.length == 0)
		{
		    // 該当データなし
		    throw new NotFoundException();
		}
		return workinfo[0].getSupplierName1();
	}

	// Private methods -----------------------------------------------
}
//end of class
