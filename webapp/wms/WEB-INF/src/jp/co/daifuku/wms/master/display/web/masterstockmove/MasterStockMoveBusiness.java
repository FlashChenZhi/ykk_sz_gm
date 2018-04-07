// $Id: MasterStockMoveBusiness.java,v 1.1.1.1 2006/08/17 09:34:18 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterstockmove;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.storage.display.web.listbox.listinventorylocation.ListInventoryLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststockmoveusebydate.ListStockMoveUseByDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragestockmove.ListStorageStockMoveBusiness;
import jp.co.daifuku.wms.storage.schedule.StockMoveSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;
import jp.co.daifuku.wms.master.schedule.MasterStockMoveSCH;
import jp.co.daifuku.wms.master.schedule.MasterStorageSupportParameter;

/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * 在庫移動クラスです。<BR>
 * 画面から入力した内容をパラメータにセットし、<BR>
 * そのパラメータを基にスケジュールが在庫移動設定をします。<BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 * 条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.入力ボタン押下処理（<CODE>btn_Input_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *  入力エリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが入力条件のチェックを行います。<BR>
 *  スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *  条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *  スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *  結果がtrueの時は入力エリアの情報をためうちエリアに追加します。<BR>
 *  修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
 *  <BR>
 * 	[パラメータ] *必須入力<BR>
 * 	<BR>
 * 	<DIR>
 * 		作業者コード* <BR>
 * 		パスワード* <BR>
 * 		荷主コード* <BR>
 * 		荷主名称 <BR>
 * 		商品コード* <BR>
 * 		商品名称 <BR>
 * 		移動元棚* <BR>
 * 		賞味期限 <BR>
 * 		ケース入数 <BR>
 * 		移動ケース数 <BR>
 * 		移動ピース数 <BR>
 * 		移動先棚* <BR>
 *		移動作業リストを発行する <BR>
 * 	</DIR>
 *  <BR>
 *  [出力用のデータ] <BR>
 *  <BR>
 * 	<DIR>
 *  	荷主コード <BR>
 *  	荷主名称 <BR>
 *  	商品コード <BR>
 *  	商品名称 <BR>
 *  	ケース入数 <BR>
 * 		移動ケース <BR>
 * 		移動ピース <BR>
 *  	移動元棚 <BR>
 * 		移動先棚 <BR>
 *  	賞味期限 <BR>
 * 		最終更新日時 <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.移動ボタン押下処理(<CODE>btn_StorageStart_Click()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *  ためうちエリアから入力した内容をパラメータにセットし、そのパラメータをもとにスケジュールが在庫移動を行います。<BR>
 *  スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *  条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *  スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * 	<DIR>
 *   [パラメータ] *必須入力 <BR>
 *   <BR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   荷主コード <BR>
 *   荷主名称 <BR>
 *   商品コード* <BR>
 *   商品名称 <BR>
 *   ケース入数* <BR>
 * 	 移動ケース <BR>
 * 	 移動ピース <BR>
 *   移動元棚* <BR>
 * 	 移動先棚* <BR>
 *   賞味期限 <BR>
 * 	 最終更新日時* <BR>
 *   移動作業リストを発行する <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:18 $
 * @author  $Author: mori $
 */
public class MasterStockMoveBusiness extends MasterStockMove implements WMSConstants
{		
	/**
	 * 在庫管理パッケージフラグを保持するViewState用キーです
	 */
	public static final String STOCK_PACK_FLAG_KEY = "STOCK_PACK_FLAG";
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。<BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *    1.荷主が１件の場合、荷主コード、荷主名称をテキストボックスに表示します。<BR>
	 *    2.カーソルを作業者コードにセットします。<BR>
	 *    3.ためうちのボタン（登録、一覧クリア）を無効にします。<BR>
	 * <BR>
	 *    項目名[初期値]<BR>
	 *    <DIR>
	 * 		作業者コード	[なし] <BR>
	 * 		パスワード		[なし] <BR>
	 * 		荷主コード		[１件の場合、在庫情報テーブルの荷主コードをセット] <BR>
	 * 		荷主名称*		[１件の場合、在庫情報テーブルの荷主名称をセット] <BR>
	 * 		商品コード		[なし] <BR>
	 * 		商品名称*		[なし] <BR>
	 * 		移動元棚		[なし] <BR>
	 * 		賞味期限		[なし] <BR>
	 * 		ケース入数		[なし] <BR>
	 * 		移動ケース数	[なし] <BR>
	 * 		移動ピース数	[なし] <BR>
	 * 		移動先棚		[なし] <BR>
	 * 		移動作業リストを発行する	[チェックあり] <BR>
	 * 		<BR>
	 * 		*<BR>
	 * 		在庫パッケージが入っていなかった場合、利用不可にします。 <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
		// (デフォルト:-1)
		this.getViewState().setInt("LineNo", -1);

		// ボタン押下を不可にする
		// 入庫開始ボタン
		btn_Relocate.setEnabled(false);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(false);

		// 初期表示をする
		setFirstDisp();
	}

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * <BR>
	 * 概要:確認ダイアログを表示します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//パラメータを取得
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//ViewStateへ保持する
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//画面名称をセットする
			lbl_SettingName.setResourceKey(title);
		}
		// 移動ボタン押下時確認ダイアログ "移動しますか？"
		btn_Relocate.setBeforeConfirm("MSG-W0025");

		// 一覧クリアボタン押下時確認ダイアログ "一覧入力情報がクリアされます。宜しいですか？"
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。<BR><CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		// 荷主名称
		String consignorname = param.getParameter(ListStorageConsignorBusiness.CONSIGNORNAME_KEY);
		// 商品コード
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemname = param.getParameter(ListStorageItemBusiness.ITEMNAME_KEY);
		// 移動元棚
		String sourcelocationno = param.getParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY);
		// 賞味期限
		String usebydate = param.getParameter(ListStockMoveUseByDateBusiness.USEBYDATE_KEY);	
		// 移動先棚
		String destlocationno = param.getParameter(ListInventoryLocationBusiness.ENDLOCATION_KEY);
		// ケース入数
		String enteringQty = param.getParameter(ListStorageStockMoveBusiness.ENTERING_KEY);
		// ケース数
		String caseQty = param.getParameter(ListStorageStockMoveBusiness.CASEQTY_KEY);
		// ピース数
		String pieceQty = param.getParameter(ListStorageStockMoveBusiness.PIECEQTY_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 荷主名称
		if (!StringUtil.isBlank(consignorname))
		{
			txt_ConsignorName.setText(consignorname);		    
		}
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		// 商品名称
		if (!StringUtil.isBlank(itemname))
		{
			txt_ItemName.setText(itemname);		    
		}
		// 移動元棚
		if (!StringUtil.isBlank(sourcelocationno))
		{
			txt_MoveFromLocation.setText(sourcelocationno);
		}
		// 賞味期限
		if (!StringUtil.isBlank(usebydate))
		{
			txt_UseByDate.setText(usebydate);
		}
		// 移動先棚
		if (!StringUtil.isBlank(destlocationno))
		{
			txt_MoveToLocation.setText(destlocationno);
		}
		// ケース入数
		if (!StringUtil.isBlank(enteringQty))
		{
			txt_CaseEntering.setText(enteringQty);
		}
		// ケース数
		if (!StringUtil.isBlank(caseQty))
		{
			lbl_JavaSetMoveCaseQty.setText(caseQty);
		}
		// ピース数
		if (!StringUtil.isBlank(pieceQty))
		{
			lbl_JavaSetMovePeaceQty.setText(pieceQty);
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。<BR>
	 * 異常があった場合は、メッセージをセットし、falseを返します。<BR>
	 * 
	 * @return true:異常なし false:異常あり
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		//在庫管理有の場合荷主名称入力フィールドが無効になっているのでそれを使います
		if (!txt_ConsignorName.getReadOnly())
		{
			if (!checker.checkContainNgText(txt_ConsignorCode))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_ConsignorName))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_ItemCode))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_ItemName))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_MoveFromLocation))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_UseByDate))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_MoveToLocation))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
		}
		else
		{
			if (!checker.checkContainNgText(txt_MoveToLocation))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
		}
		return true;
		
	}

	// Private methods -----------------------------------------------
	/**
	 * 初期表示時、スケジュールから荷主を取得するためのメソッドです。 <BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主を入力エリアにセットします。 <BR>
	 * 
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setConsignor() throws Exception
	{
		Connection conn = null;
		try
		{
			txt_ConsignorCode.setText("");
			txt_ConsignorName.setText("");
			
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			// スケジュールから荷主コードを取得します
			WmsScheduler schedule = new StockMoveSCH();
			param = (StorageSupportParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
			}
			else
			{
				txt_ConsignorCode.setText("");
				txt_ConsignorName.setText("");
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			// コネクションのクローズを行う
			if (conn != null)
			{
				conn.rollback();
				conn.close();
			}
		}
	}
	/**
	 * 画面初期表示、クリア処理の場合にこのメソッドが呼ばれます。
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			// 荷主コード、荷主名称
			setConsignor();
			// 商品コード
			txt_ItemCode.setText("");
			// 商品名称
			txt_ItemName.setText("");
			// 移動元棚
			txt_MoveFromLocation.setText("");
			// 賞味期限
			txt_UseByDate.setText("");
			// ケース入数
			txt_CaseEntering.setText("");
			// 移動ケース数
			txt_MovCaseQty.setText("");
			// 移動ピース数
			txt_MovPieseQty.setText("");
			// 移動先棚
			txt_MoveToLocation.setText("");
			// 移動情報リストを発行する
			chk_CommonUse.setChecked(true);
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterStockMoveSCH();

			MasterStorageSupportParameter param = (MasterStorageSupportParameter) schedule.initFind(conn, null);

			// 在庫パッケージがあった場合、荷主名称と商品名称を使用不可にします
			if (param.getStockPackageFlag())
			{
				txt_ConsignorName.setReadOnly(true);
				txt_ItemName.setReadOnly(true);
			}
			else
			{
				if (param.getMergeType() == MasterStorageSupportParameter.MERGE_TYPE_OVERWRITE)
				{
					txt_ConsignorName.setReadOnly(true);
					txt_ItemName.setReadOnly(true);
					txt_CaseEntering.setReadOnly(true);
				}
			}
			
			// viewStateに賞味期限管理フラグをセット
			this.viewState.setBoolean("USEBYDATE", param.getUseByDateFlag());
			// viewStateに在庫管理フラグをセット
			this.viewState.setBoolean(STOCK_PACK_FLAG_KEY, param.getStockPackageFlag());
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				// コネクションクローズ
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}	
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/** Parameterクラスにためうちエリアのデータをセットするメソッドです。<BR>
	 * <BR>
	 * 概要:Parameterクラスにためうちエリアのデータをセットします。<BR>
	 * <BR>
	 * 1.新規入力であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No＝-1)<BR>
	 * 2.修正中の入力データであれば、修正対象行を除いたためうちデータをParameterクラスにセットします。<BR>
	 * 3.登録ボタン押下時であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No＝-1)<BR>
	 * <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			修正対象ためうち行No.* <BR>
	 * 		</DIR>
	 *   	[返却データ]<BR>
	 *   	<DIR>
	 * 			ためうちエリアの内容を持つ<CODE>StockControlParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private MasterStorageSupportParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_StockMove.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineno)
			{
				continue;
			}

			// 行指定
			lst_StockMove.setCurrentRow(i);

			// スケジュールパラメータへセット
			MasterStorageSupportParameter param = new MasterStorageSupportParameter();
			// 入力エリア情報
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 作業リスト発行可否区分
			param.setMoveWorkListFlag(chk_CommonUse.getChecked());
			// ためうちエリア情報
			// 荷主コード
			param.setConsignorCode(lst_StockMove.getValue(3));
			// 商品コード
			param.setItemCode(lst_StockMove.getValue(4));
			// ケース入数
			param.setEnteringQty(Formatter.getInt(lst_StockMove.getValue(5)));
			// 移動ケース数
			param.setMovementCaseQty(Formatter.getLong(lst_StockMove.getValue(6)));
			// 移動元棚
			param.setSourceLocationNo(lst_StockMove.getValue(7));
			// 移動先棚
			param.setDestLocationNo(lst_StockMove.getValue(8));
			// 賞味期限
			param.setUseByDate(lst_StockMove.getValue(9));
			// 荷主名称
			param.setConsignorName(lst_StockMove.getValue(10));
			// 商品名称
			param.setItemName(lst_StockMove.getValue(11));
			// 移動ピース数
			param.setMovementPieceQty(Formatter.getLong(lst_StockMove.getValue(12)));

			// 行No.を保持しておく
			param.setRowNo(i);
			
			// 荷主コード最終使用日
			param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(0, lst_StockMove.getValue(0))));
			// 商品コード最終使用日
			param.setItemLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, lst_StockMove.getValue(0))));
			
			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			MasterStorageSupportParameter[] listparam = new MasterStorageSupportParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			// セットするためうちデータがなければnullをセット
			return null;
		}
	}

	/**
	 * ためうちエリアに値をセットするメソッドです。<BR>
	 * <BR>
	 * 概要:ためうちエリアに値をセットします。<BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setList(String pConsignorName, String pItemName, MasterStorageSupportParameter mergeParam) throws Exception
	{
		String consignorName = "";
		String itemName = "";
		// 荷主名称
		if (!StringUtil.isBlank(pConsignorName))
		{
			consignorName = pConsignorName;
		}
		else
		{
			consignorName = txt_ConsignorName.getText();
		}
		// 商品名称
		if (!StringUtil.isBlank(pItemName))
		{
			itemName = pItemName;
		}
		else
		{
			itemName = txt_ItemName.getText();

		}
		//ToolTip用のデータを編集
		ToolTipHelper toolTip = new ToolTipHelper();
		// 荷主名称
		toolTip.add(DisplayText.getText("LBL-W0026"), consignorName);
		// 商品名称
		toolTip.add(DisplayText.getText("LBL-W0103"), itemName);
		// 移動元棚
		toolTip.add(DisplayText.getText("LBL-W0073"), txt_MoveFromLocation.getText());
		// 移動先棚
		toolTip.add(DisplayText.getText("LBL-W0268"), txt_MoveToLocation.getText());
		// 賞味期限
		toolTip.add(DisplayText.getText("LBL-W0270"), txt_UseByDate.getText());

		//カレント行にTOOL TIPをセットする
		lst_StockMove.setToolTip(lst_StockMove.getCurrentRow(), toolTip.getText());

		// 荷主コード
		lst_StockMove.setValue(3, txt_ConsignorCode.getText());
		// 商品コード
		lst_StockMove.setValue(4, txt_ItemCode.getText());
		// ケース入数
		lst_StockMove.setValue(5, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));
		// 移動ケース数
		lst_StockMove.setValue(6, WmsFormatter.getNumFormat(txt_MovCaseQty.getLong()));
		// 移動元棚
		lst_StockMove.setValue(7, txt_MoveFromLocation.getText());
		// 移動先棚
		lst_StockMove.setValue(8, txt_MoveToLocation.getText());
		// 賞味期限
		lst_StockMove.setValue(9, txt_UseByDate.getText());
		// 荷主名称
		lst_StockMove.setValue(10, consignorName);
		// 商品名称
		lst_StockMove.setValue(11, itemName);
		// 移動ピース数
		lst_StockMove.setValue(12, WmsFormatter.getNumFormat(txt_MovPieseQty.getLong()));
		
		List hiddenList = new Vector();
		// 荷主コード最終使用日
		hiddenList.add(0, WmsFormatter.getTimeStampString(mergeParam.getConsignorLastUpdateDate()));
		// 商品コード最終使用日
		hiddenList.add(1, WmsFormatter.getTimeStampString(mergeParam.getItemLastUpdateDate()));
		lst_StockMove.setValue(0, CollectionUtils.getConnectedString(hiddenList));
	}

	// Event handler methods -----------------------------------------

	/** 
	 * メニューボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:メニュー画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	/** 
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で荷主一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchConsignor_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 検索フラグ
		param.setParameter(
			ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_STORAGE_STOCK);
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		
		if (this.viewState.getBoolean(STOCK_PACK_FLAG_KEY))
		{
			// 処理中画面->結果画面
			redirect(
				"/storage/listbox/liststorageconsignor/ListStorageConsignor.do",
				param,
				"/progress.do");
		}
		else
		{
			// 処理中画面->結果画面
			redirect(
				"/master/listbox/listmasterconsignor/ListMasterConsignor.do",
				param,
				"/progress.do");
		}
	}

	/** 
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchItem_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 検索フラグ
		param.setParameter(
			ListStorageItemBusiness.SEARCHITEM_KEY,
			StorageSupportParameter.SEARCH_STORAGE_STOCK);
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		if (this.viewState.getBoolean(STOCK_PACK_FLAG_KEY))
		{
			// 処理中画面->結果画面
			redirect("/storage/listbox/liststorageitem/ListStorageItem.do", param, "/progress.do");
		}
		else
		{
			// 処理中画面->結果画面
			redirect("/master/listbox/listmasteritem/ListMasterItem.do", param, "/progress.do");
		}
	}

	/** 
	 * 在庫一覧の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で在庫一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 * 		 移動元棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PStockSearch_Click(ActionEvent e) throws Exception
	{
		// 在庫一覧画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 荷主名称
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORNAME_KEY,
			txt_ConsignorName.getText());
		// 商品コード
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 移動元棚
		param.setParameter(
			ListInventoryLocationBusiness.STARTLOCATION_KEY,
			txt_MoveFromLocation.getText());
		// 賞味期限
		param.setParameter(ListStockMoveUseByDateBusiness.USEBYDATE_KEY, txt_UseByDate.getText());
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		// 処理中画面->結果画面
		redirect(
			"/storage/listbox/liststoragestockmove/ListStorageStockMove.do",
			param,
			"/progress.do");
	}

	/** 
	 * 移動元棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 * 		 移動元棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchFrmMovLct_Click(ActionEvent e) throws Exception
	{
		// 棚一覧画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 移動元棚
		param.setParameter(
			ListInventoryLocationBusiness.LOCATION_KEY,
			txt_MoveFromLocation.getText());
		// 検索フラグ
		param.setParameter(
			ListInventoryLocationBusiness.SEARCHLOCATION_KEY,
			StorageSupportParameter.SEARCH_STORAGE_STOCK);
		// 検索フラグ
		param.setParameter(
			ListInventoryLocationBusiness.RANGELOCATION_KEY,
		StorageSupportParameter.RANGE_START);
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		// 処理中画面->結果画面
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do",
			param,
			"/progress.do");
	}

	/** 
	 * 賞味期限の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で賞味期限一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 * 		 移動元棚 <BR>
	 * 		 賞味期限 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchUseByDate_Click(ActionEvent e) throws Exception
	{
		// 賞味期限一覧画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 移動元棚
		param.setParameter(
			ListInventoryLocationBusiness.STARTLOCATION_KEY,
			txt_MoveFromLocation.getText());
		// 賞味期限
		param.setParameter(ListStockMoveUseByDateBusiness.USEBYDATE_KEY, txt_UseByDate.getText());
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		// 処理中画面->結果画面
		redirect(
			"/storage/listbox/liststockmoveusebydate/ListStockMoveUseByDate.do",
			param,
			"/progress.do");
	}

	/** 
	 * 移動先棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 * 		 移動先棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchToMovLct_Click(ActionEvent e) throws Exception
	{
		// 棚一覧画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 移動先棚
		param.setParameter(
			ListInventoryLocationBusiness.LOCATION_KEY,
			txt_MoveToLocation.getText());
		// 検索フラグ
		param.setParameter(
			ListInventoryLocationBusiness.SEARCHLOCATION_KEY,
			StorageSupportParameter.SEARCH_STORAGE_STOCK);
		// 検索フラグ
		param.setParameter(
			ListInventoryLocationBusiness.RANGELOCATION_KEY,
		StorageSupportParameter.RANGE_END);
		// 検索フラグ
		param.setParameter(
			ListInventoryLocationBusiness.RANGELOCATION_KEY,
		StorageSupportParameter.RANGE_END);
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		// 処理中画面->結果画面
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do",
			param,
			"/progress.do");
	}

	/** 
	 * 入力ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目をチェックし、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *   1.カーソルを作業者コードにセットします。<BR>
	 *   2.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *   3.スケジュールを開始します。<BR>
	 * 	 <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 		   作業者コード* <BR>
	 * 		   パスワード* <BR>
	 * 		   荷主コード* <BR>
	 * 		   荷主名称 <BR>
	 * 		   商品コード* <BR>
	 * 		   商品名称 <BR>
	 * 		   移動元棚* <BR>
	 * 		   賞味期限*2 <BR>
	 * 		   ケース入数 <BR>
	 * 		   移動ケース数 <BR>
	 * 		   移動ピース数 <BR>
	 * 		   移動先棚* <BR>
	 * 		   <BR>
	 * 		   *2 <br>
	 * 		   賞味期限管理パッケージがあれば必須入力 <BR>
	 *	 	</DIR>
	 *   </DIR>
	 *   <BR>
	 *   4.スケジュールの結果がtrueであれば、ためうちエリアに追加します。<BR>
	 *     修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
	 *   5.スケジュールの結果がtrueであれば、移動ボタン・一覧クリアボタンを有効にします。<BR>
	 *   6.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *   7.スケジュールから取得したメッセージを画面に出力します。<BR>
	 * 	 8.入力エリアの内容は保持します。<BR>
	 *   <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		// 必須入力チェック
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		txt_ItemCode.validate();
		txt_MoveFromLocation.validate();
		txt_MoveToLocation.validate();

		// パターンマッチング文字
		txt_ConsignorName.validate(false);
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_MovCaseQty.validate(false);
		txt_MovPieseQty.validate(false);
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			MasterStorageSupportParameter mergeParam = null;
			if (!this.viewState.getBoolean(STOCK_PACK_FLAG_KEY))
			{
				mergeParam = new MasterStorageSupportParameter();
				mergeParam.setConsignorCode(txt_ConsignorCode.getText());
				mergeParam.setConsignorName(txt_ConsignorName.getText());
				mergeParam.setItemCode(txt_ItemCode.getText());
				mergeParam.setItemName(txt_ItemName.getText());
				mergeParam.setEnteringQty(txt_CaseEntering.getInt());
				
				WmsScheduler schedule = new MasterStockMoveSCH(conn);
				
				mergeParam = (MasterStorageSupportParameter) schedule.query(conn, mergeParam)[0];
				// 入力エリアに補完結果を表示
				txt_ConsignorName.setText(mergeParam.getConsignorName());
				txt_ItemName.setText(mergeParam.getItemName());
				txt_CaseEntering.setInt(mergeParam.getEnteringQty());
			}
			
			// スケジュールパラメータへセット
			// 入力エリア
			MasterStorageSupportParameter param = new MasterStorageSupportParameter();
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 荷主名称
			param.setConsignorName(txt_ConsignorName.getText());
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 商品名称
			param.setItemName(txt_ItemName.getText());
			// 移動元棚
			param.setSourceLocationNo(txt_MoveFromLocation.getText());
			// 賞味期限		
			param.setUseByDate(txt_UseByDate.getText());
			// ケース入数
			param.setEnteringQty(txt_CaseEntering.getInt());
			// 移動ケース数
			param.setMovementCaseQty(txt_MovCaseQty.getLong());
			// 移動ピース数
			param.setMovementPieceQty(txt_MovPieseQty.getLong());
			// 移動先棚
			param.setDestLocationNo(txt_MoveToLocation.getText());

			// スケジュールパラメータへセット
			// ためうちエリア
			StorageSupportParameter[] listparam = null;

			// ためうちにデータがなければnullをセット
			if (lst_StockMove.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParam(this.getViewState().getInt("LineNo"));
			}

			WmsScheduler schedule = new MasterStockMoveSCH(conn);
			// 入力チェックを行う
			if (schedule.check(conn, param, listparam))
			{
				if (mergeParam != null)
				{
					param.setConsignorLastUpdateDate(mergeParam.getConsignorLastUpdateDate());
					param.setItemLastUpdateDate(mergeParam.getItemLastUpdateDate());
				}
				String consignorName = "";
				String itemName = "";
				// 在庫パッケージありの場合、在庫を検索する(名称取得・引当て可能数チェック)
				//if (txt_ConsignorName.getReadOnly() && txt_ItemName.getReadOnly())
				if (this.getViewState().getBoolean(STOCK_PACK_FLAG_KEY))
				{
					StorageSupportParameter[] viewParam =
						(StorageSupportParameter[]) schedule.query(conn, param);

					// 入力データが不正だった場合、処理を行わない
					if(viewParam == null || viewParam.length == 0)
					{
						// メッセージをセット
						message.setMsgResourceKey(schedule.getMessage());
						return;
					}
					// 名称を保持する
					consignorName = viewParam[0].getConsignorName();
					itemName = viewParam[0].getItemName();
				}

				// ためうちエリアにデータをセット
				if (this.getViewState().getInt("LineNo") == -1)
				{
					// 新規入力であれば、ためうちに追加
					lst_StockMove.addRow();
					lst_StockMove.setCurrentRow(lst_StockMove.getMaxRows() - 1);
					setList(consignorName, itemName, param);
				}
				else
				{
					// 修正中の入力データであれば、修正対象行のデータを更新
					lst_StockMove.setCurrentRow(this.getViewState().getInt("LineNo"));
					setList(consignorName, itemName, param);
					// 選択行の色を元に戻す
					lst_StockMove.resetHighlight();
				}

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt("LineNo", -1);

				// ボタン押下を可能にする
				// 登録ボタン
				btn_Relocate.setEnabled(true);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(true);
			}

			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				// コネクションクローズ
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}	
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/** 
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリアの項目を初期値に戻します。<BR>
	 *    <DIR>
	 *  	･初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。<BR>
	 *    </DIR>
	 *    2.カーソルを作業者コードにセットします。<BR>
	 *    3.ためうちエリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// クリア処理を行う
		setFirstDisp();
	}

	/** 
	 * 移動ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、在庫移動を行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを作業者コードにセットします。<BR>
	 *    2.移動を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "移動しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.スケジュールを開始します。<BR>
	 *				<DIR>
	 *   				[パラメータ]<BR>
	 * 					<DIR>
	 * 						作業者コード <BR>
	 * 						パスワード <BR>
	 * 						移動作業リストを発行する <BR>
	 * 						ためうち.荷主コード <BR>
	 * 						ためうち.荷主名称 <BR>
	 *						ためうち.商品コード <BR>
	 *						ためうち.商品名称 <BR>
	 *						ためうち.ケース入数 <BR>
	 *						ためうち.移動ケース数 <BR>
	 *						ためうち.移動ピース数 <BR>
	 *						ためうち.移動元 <BR>
	 *						ためうち.移動先 <BR>
	 *						ためうち.賞味期限 <BR>
	 *						ためうち.最終更新日時 <BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.スケジュールの結果がtrueの時は入力エリア、ためうちの移動情報をクリアします。<BR>
	 *				3.修正状態を解除します。(ViewStateのためうち行No.にデフォルト:-1を設定します。)<BR>
	 *    			falseの時はスケジュールから取得したメッセージを画面に出力します。<BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Relocate_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// スケジュールパラメータへセット
			MasterStorageSupportParameter[] param = null;
			// ためうちエリアの全データをセット
			param = setListParam(-1);

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterStockMoveSCH(conn);

			if (!schedule.startSCH(conn, param))
			{
				conn.rollback();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
				// 一覧クリア
				lst_StockMove.clearRow();

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt("LineNo", -1);

				// ボタン押下を不可にする
				// 移動ボタン
				btn_Relocate.setEnabled(false);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(false);

			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				// コネクションクローズ
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}	
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/** 
	 * 一覧クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちの表示情報を全てクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ためうち情報のクリアを行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 * 	    "一覧入力情報がクリアされます。宜しいですか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.ためうちの表示情報を全てクリアします。<BR>
	 *				2.登録ボタン・一覧クリアボタンを無効にします。<BR>
	 *				3.入力エリアの項目を全てクリアする。<BR>
	 * 				4.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *				5.カーソルを作業者コードにセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// 行をすべて削除
		lst_StockMove.clearRow();

		// ボタン押下を不可にする
		// 移動ボタン
		btn_Relocate.setEnabled(false);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(false);

		// 修正状態をデフォルトに戻す
		this.getViewState().setInt("LineNo", -1);
	}

	/** 
	 * 取消、修正ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 取消ボタン概要:ためうちの該当データをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ためうち情報のクリアを行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "取消しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.入力エリア、ためうちの該当データをクリアします。<BR>
	 * 				2.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *              3.ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にします。<BR>
	 *				4.カーソルを作業者コードにセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * 修正ボタン概要:ためうちの該当データを修正状態にします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.選択情報を上部の入力領域に表示します。<BR>
	 *    2.選択情報部を薄黄色にします。<BR>
	 *    3.ViewStateのためうち行No.に現在行を設定します。
	 *    4.カーソルを作業者コードにセットします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_StockMove_Click(ActionEvent e) throws Exception
	{
		// 取消ボタンクリック時
		if (lst_StockMove.getActiveCol() == 1)
		{
			// リスト削除
			lst_StockMove.removeRow(lst_StockMove.getActiveRow());

			// ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にする
			// ためうちにデータがなければnullをセット
			if (lst_StockMove.getMaxRows() == 1)
			{
				// ボタン押下を不可にする
				// 登録ボタン
				btn_Relocate.setEnabled(false);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(false);
			}

			// 修正状態をデフォルトに戻す
			this.getViewState().setInt("LineNo", -1);

			// 選択行の色を元に戻す
			lst_StockMove.resetHighlight();
		}
		// 修正ボタンクリック時(Modify動作を行う)
		else if (lst_StockMove.getActiveCol() == 2)
		{
			// 現在の行をセット
			lst_StockMove.setCurrentRow(lst_StockMove.getActiveRow());
			// 荷主コード
			txt_ConsignorCode.setText(lst_StockMove.getValue(3));
			// 商品コード
			txt_ItemCode.setText(lst_StockMove.getValue(4));
			// ケース入数
			txt_CaseEntering.setText(lst_StockMove.getValue(5));
			// 移動ケース数
			txt_MovCaseQty.setText(lst_StockMove.getValue(6));
			// 移動元棚
			txt_MoveFromLocation.setText(lst_StockMove.getValue(7));
			// 移動先棚
			txt_MoveToLocation.setText(lst_StockMove.getValue(8));
			// 賞味期限
			txt_UseByDate.setText(lst_StockMove.getValue(9));
			// 荷主名称
			txt_ConsignorName.setText(lst_StockMove.getValue(10));
			// 商品名称
			txt_ItemName.setText(lst_StockMove.getValue(11));
			// 移動ピース数
			txt_MovPieseQty.setText(lst_StockMove.getValue(12));

			// ViewStateに保存
			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			this.getViewState().setInt("LineNo", lst_StockMove.getActiveRow());

			//修正行を黄色に変更する
			lst_StockMove.setHighlight(lst_StockMove.getActiveRow());
		}
	}

}
//end of class
