// $Id: Id0022Operate.java,v 1.1.1.1 2006/08/17 09:34:15 mori Exp $
package jp.co.daifuku.wms.master.base.rft;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.RftConsignor;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.WorkDetails;
import jp.co.daifuku.wms.master.operator.AreaOperator;

/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * 荷主一覧要求(ID0022)処理を行うためのクラスです。<BR>
 * <CODE>IdOperate</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 作業区分、予定日をパラメータとして受け取り、荷主一覧情報を取得し、<BR>
 * 荷主一覧(荷主コード昇順)ファイルの作成を行います。<BR>
 * <BR>
 * 荷主一覧取得(<CODE>findConsignorCode()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   作業区分、予定日をパラメータとして受取り、荷主一覧情報を返します。<BR>
 *   作業区分により荷主一覧情報取得先を判別します。<BR>
 *   作業区分:"入荷、入庫、出庫、仕分、出荷"の場合、作業情報(dnworkinfo)より取得します。<BR>
 *   作業区分:"移動出庫、予定外出庫" の場合、在庫情報(dmstock)より取得します。但し、在庫管理無しの場合は荷主マスタより取得します。<BR>
 *   作業区分:"移動入庫"             の場合、移動作業情報(dnmovement)より取得します。<BR>
 *   作業区分:"予定外入庫、棚卸し"   の場合、荷主マスタ情報(dmconsignor)より取得します。<BR>
 * </DIR>
 * <BR>
 * <BR>
 * 荷主一覧ファイル作成(<CODE>createTableFile()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   荷主一覧情報、ファイル名(フルパス)をパラメータとして受取り、荷主一覧ファイルを作成します。<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class Id0022Operate extends IdOperate
{
	// Class fields----------------------------------------------------
	// Class variables -----------------------------------------------
	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
	public Id0022Operate()
	{
		super();
	}

	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public Id0022Operate(Connection conn)
	{
		this();
		wConn = conn;
	}

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:15 $";
	}

	// Public methods ------------------------------------------------
	/**
	 * 指定した条件で荷主一覧情報を取得します。<BR>
	 * 荷主一覧取得先は作業区分に依存し、以下内容となります。<BR>
	 *   <DIR>
	 *   [1:入荷、2:入庫、3:出庫、4:仕分、5:出荷]<BR>
	 *     <DIR>
	 *     作業情報より取得します。<BR>
	 *     </DIR>
	 *   [11:移動出庫、22:例外出庫]<BR>
	 *     <DIR>
	 *     在庫情報より取得します。但し、在庫管理無しの場合は荷主マスタ(dmconsignor)より取得します。<BR>
	 *     </DIR>
	 *   [12:移動入庫]<BR>
	 *     <DIR>
	 *     移動作業情報より取得します。<BR>
	 *     </DIR>
	 *   [21:例外入庫、40:棚卸し]<BR>
	 *     <DIR>
	 *     荷主マスタ情報(dmconsignor)より取得します。<BR>
	 *     </DIR>
	 *   </DIR>
	 * @param  workType 作業区分<BR>
	 * <DIR>
	 * <DIR>
	 *                 01:入荷<BR>
	 *                 02:入庫<BR>
	 *                 03:出庫<BR>
	 *                 04:仕分<BR>
	 *                 05:出荷<BR>
	 *                 11:移動出庫<BR>
	 *                 12:移動入庫<BR>
	 *                 21:例外入庫<BR>
	 *                 22:例外出庫<BR>
	 *                 40:棚卸し<BR>
	 * </DIR>
	 * </DIR>
	 * @param  planDate		作業予定日
	 * @param	rftNo		RFT番号
	 * @param	workerCode	作業者コード
	 * @return		荷主一覧
	 * @throws IllegalArgumentException 不正な作業区分の場合に通知されます。
	 * @throws NotFoundException
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 */
	public RftConsignor[] findConsignorCode(
			String planDate,
			String workType,
			String workDetails,
			String rftNo,
			String workerCode)
		throws IllegalArgumentException, NotFoundException, ReadWriteException, ScheduleException
	{
		// 取得した荷主情報を保持します。
		RftConsignor[] data = null;
		// 荷主一覧取得
		if (workType.equals(SystemDefine.JOB_TYPE_INSTOCK)
			|| workType.equals(SystemDefine.JOB_TYPE_STORAGE)
			|| workType.equals(SystemDefine.JOB_TYPE_RETRIEVAL)
			|| workType.equals(SystemDefine.JOB_TYPE_SORTING)
			|| workType.equals(SystemDefine.JOB_TYPE_SHIPINSPECTION))
		{
			// 作業情報より取得
			data = findConsignorFromWorkInfo(planDate, workType, workDetails, rftNo, workerCode);
		}
		else if (workType.equals(SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL)
				|| workType.equals(SystemDefine.JOB_TYPE_EX_RETRIEVAL))
		{
			if (SystemParameter.withStockManagement())
			{
				// 在庫管理パッケージ有りの場合、在庫情報より取得
				data = findConsignorFromStock(workType);
			}
			else
			{
				// 在庫管理パッケージなしの場合は、マスタから取得する
				data = findConsignorFromMaster();
			}

		}
		else if (workType.equals(SystemDefine.JOB_TYPE_MOVEMENT_STORAGE))
		{
			// 移動作業情報より取得
			data = findConsignorFromMovement();
		}
		else if (workType.equals(SystemDefine.JOB_TYPE_EX_STORAGE)
				|| workType.equals(SystemDefine.JOB_TYPE_INVENTORY))
		{
			// 荷主マスタ情報より取得
			data = findConsignorFromMaster();
		}
		else
		{
			// 作業区分異常
			throw new IllegalArgumentException(workType);
		}

		return data;
	}

	// Package methods ----------------------------------------------
	// Protected methods --------------------------------------------
	// Private methods -----------------------------------------------
	/**
	 * 在庫情報(dmstock)より荷主一覧を取得します。<BR>
	 * 在庫ステータスがセンター在庫で在庫数が1以上の在庫データより<BR>
	 * 荷主コード、荷主名称のデータ一覧を荷主コード昇順で取得します。<BR>
	 * 但し、重複データは除きます。<BR>
	 * <BR>
	 *     <DIR>
	 *     [取得項目]<BR>
	 *         <DIR>
	 *         荷主コード<BR>
	 *         荷主名称<BR>
	 *         </DIR>
	 *     [検索条件]<BR>
	 *         <DIR>
	 *         在庫ステータス:センター在庫<BR>
	 *         在庫数        :移動出庫、例外出庫の場合のみ1以上。<BR>
	 *         在庫情報.エリアNo=エリアマスタ情報.エリアNo<BR>
	 *         エリアマスタ情報.エリア区分=2:ASRS以外<BR> 
	 *         </DIR>
	 *     [集約条件]<BR>
	 *         <DIR>
	 *         荷主コード<BR>
	 *         荷主名称<BR>
	 *         </DIR>
	 *     [ソート順]<BR>
	 *         <DIR>
	 *         荷主コード<BR>
	 *         荷主名称<BR>
	 *         </DIR>
	 *     </DIR>
	 * @param	jobType	作業種別
	 * @return		荷主一覧情報
	 * @throws NotFoundException  荷主情報が見つからない場合に通知されます。
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 */
	private RftConsignor[] findConsignorFromStock(String jobType) throws NotFoundException, ReadWriteException, ScheduleException
	{
		StockSearchKey stockSearchKey = new StockSearchKey();
		AreaOperator AreaOperator = new AreaOperator(wConn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		
		//-----------------
		// 取得項目セット
		//-----------------
		// 荷主コード
		stockSearchKey.setConsignorCodeCollect();
		// 荷主名称
		stockSearchKey.setConsignorNameCollect();

		//-----------------
		// 検索条件セット
		//-----------------
		// 状態フラグ(センター在庫)
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// 移動出庫、例外出庫の場合は在庫数が1以上の在庫のみを対象とする。
		if (jobType.equals(SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL)
			|| jobType.equals(SystemDefine.JOB_TYPE_EX_RETRIEVAL))
		{
			stockSearchKey.setAllocationQty(0, ">");
		}
		// ASRS以外のエリアを取得し、検索条件に付加する。
		// 該当エリアがなかった場合はIS NULL検索
		areaNo = AreaOperator.getAreaNo(areaType);
		stockSearchKey.setAreaNo(areaNo);

		//-----------------
		// 集約条件セット
		//-----------------
		stockSearchKey.setConsignorCodeGroup(1);
		stockSearchKey.setConsignorNameGroup(2);

		//-----------------
		// ソート順セット
		//-----------------
		stockSearchKey.setConsignorCodeOrder(1, true);
		stockSearchKey.setConsignorNameOrder(2, true);

		//-----------------
		// 検索処理
		//-----------------
		StockHandler stockHandler = new StockHandler(wConn);
		Stock[] stock = (Stock[]) stockHandler.find(stockSearchKey);

		// 在庫情報が取得できない場合はNotFoundExceptionをthrowする。
		if (stock == null || stock.length == 0)
		{
			throw (new NotFoundException());
		}

		// 荷主一覧情報取得
		RftConsignor[] data = new RftConsignor[stock.length];
		for (int i = 0; i < stock.length; i++)
		{
			data[i] =
				new RftConsignor(
					stock[i].getConsignorCode(),
					stock[i].getConsignorName(),
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"");
		}

		return data;
	}

	/**
	 * 作業情報(dnworkinfo)より指定した条件で荷主一覧を取得します。<BR>
	 * 状態フラグが未開始で作業開始フラグが開始済の作業データより<BR>
	 * 指定した条件(作業種別、作業種別詳細、作業予定日)で
	 * 荷主コード、荷主名称のデータ一覧を荷主コード昇順で取得します。<BR>
	 * 但し、重複データは除きます。また、荷主作業予定日がスペースの場合は検索条件から除外します。<BR>
	 * <BR>
	 *   <DIR>
	 *   [取得項目]<BR>
	 *         <DIR>
	 *         荷主コード<BR>
	 *         荷主名称<BR>
	 *         </DIR>
	 *   [検索条件]<BR>
	 *     <DIR>
	 *     作業区分      :パラメータより取得<BR>
	 *     作業予定日    :パラメータより取得（空白の場合は条件から外す）<BR>
	 *     状態フラグ    :未開始または自端末、自作業者が作業中にしたもの<BR>
	 *     作業開始フラグ:開始済<BR>
	 *     TD/DC区分     :DCまたはクロスTC（作業種別が01かつ、作業種別詳細が1または2の場合のみ）<BR>
	 *     TD/DC区分     :TC（作業種別が01かつ、作業種別詳細が3の場合のみ）<BR>
	 *     オーダーNo.   :空でないもの（作業種別が03かつ、作業種別詳細が1の場合のみ）<BR>
	 *     オーダーNo.   :空のもの（作業種別が03かつ、作業種別詳細が2の場合のみ）<BR>
	 *     </DIR>
	 *   [集約条件]<BR>
	 *     <DIR>
	 *       荷主コード<BR>
	 *       荷主名称<BR>
	 *     </DIR>
	 *   [ソート順]<BR>
	 *     <DIR>
	 *       荷主コード<BR>
	 *       荷主名称<BR>
	 *     </DIR>
	 *   </DIR>
	 * 
	 * @param  planDate 	作業予定日
	 * @param	workType	作業種別
	 * <DIR>
	 * <DIR>
	 *                  1:入荷<BR>
	 *                  2:入庫<BR>
	 *                  3:出庫<BR>
	 *                  4:仕分<BR>
	 *                  5:出荷<BR>
	 * </DIR>
	 * </DIR>
	 * @param	workDetails	作業種別詳細
	 * @param	rftNo		RFT番号
	 * @param	workerCode	作業者コード
	 * @return		荷主一覧情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  荷主情報が見つからない場合に通知されます。
	 */
	private RftConsignor[] findConsignorFromWorkInfo(
	        String planDate,
	        String workType,
	        String workDetails,
	        String rftNo,
	        String workerCode)
		throws ReadWriteException, NotFoundException
	{
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();

		//-----------------
		// 取得項目セット
		//-----------------
		// 荷主コード
		workInfoSearchKey.setConsignorCodeCollect();
		// 荷主名称
		workInfoSearchKey.setConsignorNameCollect();

		//-----------------
		// 検索条件セット
		//-----------------
		// 作業区分
		workInfoSearchKey.setJobType(workType);
		// 作業予定日 スペースの場合は条件に追加しない
		if (planDate.trim().length() > 0)
		{
			workInfoSearchKey.setPlanDate(planDate);
		}
		// 状態フラグ
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		workInfoSearchKey.setWorkerCode(workerCode);
		workInfoSearchKey.setTerminalNo(rftNo, "=", "", "))", "AND");
		// 作業開始フラグ
		workInfoSearchKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);

		if (workType.equals(WorkingInformation.JOB_TYPE_INSTOCK))
		{
			if (workDetails.equals(WorkDetails.INSTOCK_ITEM)
					|| workDetails.equals(WorkDetails.INSTOCK_SUPPLIER))
			{
				// 仕入先単位、または商品単位の場合はDCおよびクロスTCを検索する
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC, "=", "(", "", "OR");
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC, "=", "", ")", "AND");
			}
			else if (workDetails.equals(WorkDetails.INSTOCK_CUSTOMER))
			{
				// 出荷先単位の場合はTCを検索する
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
			}
		}
		else if (workType.equals(WorkingInformation.JOB_TYPE_RETRIEVAL))
		{
			if (workDetails.equals(WorkDetails.RETRIEVAL_ORDER))
			{
				// オーダー出庫の場合はオーダーNoがセットされているデータを検索する
				workInfoSearchKey.setOrderNo("", "IS NOT NULL");
			}
			else if (workDetails.equals(WorkDetails.RETRIEVAL_ITEM))
			{
				// アイテム出庫の場合はオーダーNoが空のデータを検索する
				workInfoSearchKey.setOrderNo("", "=");
			}
		}

		//-----------------
		// 集約条件セット
		//-----------------
		workInfoSearchKey.setConsignorCodeGroup(1);
		workInfoSearchKey.setConsignorNameGroup(2);

		//-----------------
		// ソート順セット
		//-----------------
		workInfoSearchKey.setConsignorCodeOrder(1, true);
		workInfoSearchKey.setConsignorNameOrder(2, true);

		//-----------------
		// 検索処理
		//-----------------
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
		WorkingInformation[] workInfo =
			(WorkingInformation[]) workInfoHandler.find(workInfoSearchKey);

		// 作業情報が取得できない場合はNotFoundExceptionをthrowする。
		if (workInfo == null || workInfo.length == 0)
		{
			throw (new NotFoundException());
		}

		// 荷主一覧情報取得
		RftConsignor[] data = new RftConsignor[workInfo.length];
		for (int i = 0; i < workInfo.length; i++)
		{
			data[i] =
				new RftConsignor(
					workInfo[i].getConsignorCode(),
					workInfo[i].getConsignorName(),
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"");
		}

		return data;
	}

	/**
	 * 移動作業情報(dnmovement)より荷主一覧を取得します。<BR>
	 * 状態フラグが入庫待ちで作業開始フラグが開始済の移動作業データより<BR>
	 * 荷主コード、荷主名称のデータ一覧を荷主コード昇順で取得します。<BR>
	 * 但し、重複データは除きます。<BR>
	 * <BR>
	 *   <DIR>
	 *   [取得項目]<BR>
	 *     <DIR>
	 *     荷主コード<BR>
	 *     荷主名称<BR>
	 *     </DIR>
	 *   [検索条件]<BR>
	 *     <DIR>
	 *     状態フラグ    :入庫待ち<BR>
	 *     作業開始フラグ:開始済<BR>
	 *     移動作業情報.エリアNo=エリアマスタ情報.エリアNo<BR>
	 *     エリアマスタ情報.エリア区分=2:ASRS以外<BR>
	 *     </DIR>
	 *   [集約条件]<BR>
	 *     <DIR>
	 *     荷主コード<BR>
	 *     荷主名称<BR>
	 *     </DIR>
	 *   [ソート順]<BR>
	 *     <DIR>
	 *     荷主コード<BR>
	 *     荷主名称<BR>
	 *     </DIR>
	 *   </DIR>
	 * @return		荷主一覧情報
	 * @throws NotFoundException  荷主情報が見つからない場合に通知されます。
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 */
	private RftConsignor[] findConsignorFromMovement() throws NotFoundException, ReadWriteException, ScheduleException
	{
		MovementSearchKey movementSearchKey = new MovementSearchKey();

		AreaOperator AreaOperator = new AreaOperator(wConn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		
		//-----------------
		// 取得項目セット
		//-----------------
		// 荷主コード
		movementSearchKey.setConsignorCodeCollect();
		// 荷主名称
		movementSearchKey.setConsignorNameCollect();

		//-----------------
		// 検索条件セット
		//-----------------
		// 状態フラグ(入庫待ち)
		movementSearchKey.setStatusFlag(Movement.STATUSFLAG_UNSTART);
		// 作業開始フラグ
		movementSearchKey.setBeginningFlag(Movement.BEGINNING_FLAG_STARTED);

		// ASRS以外のエリアを取得し、検索条件に付加する。
		// 該当エリアがなかった場合はIS NULL検索
		areaNo = AreaOperator.getAreaNo(areaType);
		movementSearchKey.setAreaNo(areaNo);

		//-----------------
		// 集約条件セット
		//-----------------
		movementSearchKey.setConsignorCodeGroup(1);
		movementSearchKey.setConsignorNameGroup(2);

		//-----------------
		// ソート順セット
		//-----------------
		movementSearchKey.setConsignorCodeOrder(1, true);
		movementSearchKey.setConsignorNameOrder(2, true);

		//-----------------
		// 検索処理
		//-----------------
		MovementHandler movementHandler = new MovementHandler(wConn);
		Movement[] movement = (Movement[]) movementHandler.find(movementSearchKey);

		// 在庫情報が取得できない場合はNotFoundExceptionをthrowする。
		if (movement == null || movement.length == 0)
		{
			throw (new NotFoundException());
		}

		// 荷主一覧情報取得
		RftConsignor[] data = new RftConsignor[movement.length];
		for (int i = 0; i < movement.length; i++)
		{
			data[i] =
				new RftConsignor(
					movement[i].getConsignorCode(),
					movement[i].getConsignorName(),
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"");
		}

		return data;
	}

	
	/**
	 * 在庫情報(dmstock)より荷主一覧を取得します。<BR>
	 * 在庫ステータスがセンター在庫で在庫数が1以上の在庫データより<BR>
	 * 荷主コード、荷主名称のデータ一覧を荷主コード昇順で取得します。<BR>
	 * 但し、重複データは除きます。<BR>
	 * <BR>
	 *     <DIR>
	 *     [取得項目]<BR>
	 *         <DIR>
	 *         荷主コード<BR>
	 *         荷主名称<BR>
	 *         </DIR>
	 *     [検索条件]<BR>
	 *         <DIR>
	 *         在庫ステータス:センター在庫<BR>
	 *         在庫数        :移動出庫、例外出庫の場合のみ1以上。<BR>
	 *         在庫情報.エリアNo=エリアマスタ情報.エリアNo<BR>
	 *         エリアマスタ情報.エリア区分=2:ASRS以外<BR> 
	 *         </DIR>
	 *     [集約条件]<BR>
	 *         <DIR>
	 *         荷主コード<BR>
	 *         荷主名称<BR>
	 *         </DIR>
	 *     [ソート順]<BR>
	 *         <DIR>
	 *         荷主コード<BR>
	 *         荷主名称<BR>
	 *         </DIR>
	 *     </DIR>
	 * @return		荷主一覧情報
	 * @throws NotFoundException  荷主情報が見つからない場合に通知されます。
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 */
	private RftConsignor[] findConsignorFromMaster() throws NotFoundException, ReadWriteException, ScheduleException
	{
		Consignor[] consignor = null;
		ConsignorSearchKey skey = new ConsignorSearchKey();
		//-----------------
		// 検索条件セット
		//-----------------
		skey.setDeleteFlag(Consignor.DELETE_FLAG_LIVE);
		//-----------------
		// ソート順セット
		//-----------------
		skey.setConsignorCodeOrder(1, true);
		skey.setConsignorNameOrder(2, true);
		//-----------------
		// 検索処理
		//-----------------
		ConsignorHandler handler = new ConsignorHandler(wConn);
		consignor = (Consignor[])handler.find(skey) ;
		
		if(consignor == null || consignor.length == 0)
		{
			throw new NotFoundException();
		}
		RftConsignor[] data = new RftConsignor[consignor.length];
		for (int i = 0; i < consignor.length; i ++)
		{
			data[i] =
				new RftConsignor(
					consignor[i].getConsignorCode(),
					consignor[i].getConsignorName(),
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"");
		}

		return data;
	}

}
//end of class
