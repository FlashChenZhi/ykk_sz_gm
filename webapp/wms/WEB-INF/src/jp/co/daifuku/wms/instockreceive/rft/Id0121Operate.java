// $Id: Id0121Operate.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
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
 * 入荷検品出荷先一覧要求(ID0121)処理を行うためのクラスです。<BR>
 * <CODE>IdOperate</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 予定日、荷主、仕入先をパラメータとして受け取り、
 * 作業情報から出荷先一覧情報を取得し、<BR>
 * 出荷先一覧一覧(出荷先コード昇順)ファイルの作成を行います。<BR>
 * <BR>
 * 出荷先一覧取得(<CODE>getCustomerList()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   予定日、荷主、仕入先をパラメータとして受取り出荷先一覧情報を返します。<BR>
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
public class Id0121Operate extends IdOperate
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
	 * 作業情報(dnworkinfo)より指定した条件で出荷先別入荷検品の作業対象となる出荷先一覧を取得します。<BR>
	 * 作業区分が入荷、TC/DC区分がTC、状態フラグが未開始で作業開始フラグが開始済の作業データより<BR>
	 * 指定した条件(作業予定日、荷主、仕入先)で、出荷先コード、出荷先名称のデータ一覧を<BR>
	 * 出荷先コード昇順で取得します。<BR>
	 * 検索条件はInstockReceiveOperateクラスの機能を使用して取得します。
	 * 但し、重複データは除きます。<BR>
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
	 *         仕入先コード  :パラメータより取得<BR>
	 *         TC/DC区分     :TC<BR>
	 *         作業区分      :入荷<BR>
	 *         状態フラグ    :未開始<BR>
	 *         作業開始フラグ:開始済または作業中の場合は自端末で更新したもの<BR>
	 *         </DIR>
	 *     [集約条件]<BR>
	 *         <DIR>
	 *         出荷先コード<BR>
	 *         出荷先名称<BR>
	 *         </DIR>
	 *     [ソート順]<BR>
	 *         <DIR>
	 *         出荷先コード<BR>
	 *         出荷先名称<BR>
	 *         </DIR>
	 *     </DIR>
	 * @param  planDate			作業予定日
	 * @param  consignorCode	荷主コード
	 * @param  supplierCode		仕入先コード
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @return 			取得した出荷先情報（作業情報エンティティの配列）
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  出荷先が見つからない場合に通知されます。
	 */
	public WorkingInformation[] getCustomerList(
	        String planDate,
	        String consignorCode,
	        String supplierCode,
	        String workerCode,
	        String rftNo)
		throws ReadWriteException, NotFoundException
	{
		// 取得した出荷先情報を保持します。
		WorkingInformation[] workinfo = null;
		
		InstockReceiveOperate instockOperate = new InstockReceiveOperate();
		instockOperate.setConnection(wConn);
		WorkingInformationSearchKey skey = null;
		skey = instockOperate.getConditionOfInstockByCustomer(
		        planDate,
		        consignorCode,
		        supplierCode,
		        null,
		        workerCode,
		        rftNo
		        );

		//-----------------
		// 集約条件セット
		//-----------------
		skey.setCustomerCodeGroup(1);
		skey.setCustomerName1Group(2);
		
		skey.setCustomerCodeCollect("");
		skey.setCustomerName1Collect("");

		//-----------------
		// ソート順セット
		//-----------------
		skey.setCustomerCodeOrder(1, true);
		skey.setCustomerName1Order(2, true);
		
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
