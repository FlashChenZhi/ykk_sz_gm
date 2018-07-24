// $Id: MasterDefineLoadDataMenuConsignorBusiness.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $
package jp.co.daifuku.wms.master.display.web.masterdefineloaddatamenu;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.master.schedule.MasterDefineDataParameter;
import jp.co.daifuku.wms.master.schedule.MasterDefineLoadDataMenuSCH;

/**
 * Designer : hota <BR>
 * Maker : hota <BR>
 * <BR>
 * マスタデータ取り込み項目設定(荷主マスタ項目設定)画面クラスです。 <BR>
 * 入力エリアから入力した内容をパラメータにセットしスケジュールに渡し、荷主マスタ項目設定を登録します。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、条件エラーなどで <BR>
 * スケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 * スケジュールの結果、スケジュールから取得したメッセージを画面に出力します。 <BR>
 *
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 登録ボタン押下処理（<CODE>btn_Submit_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   入力エリア情報で登録を行います。 <BR>
 * <BR>
 *   [パラメータ] *必須入力<BR>
 * <BR>
 * <DIR>
 * 	 荷主コード	(必須)(使用桁)* <BR>
 * 	 荷主コード	(必須)(最大桁)* <BR>
 * </DIR>
 * <BR>
 *   [パラメータ]+有効にチェックされていれば必須入力 <BR>
 * <BR>
 * <DIR>
 *   荷主名称    (使用桁)+ <BR>
 *   荷主名称    (最大桁)+ <BR>
 * </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:17 $
 * @author  $Author: mori $
 */
public class MasterDefineLoadDataMenuConsignorBusiness extends MasterDefineLoadDataMenuConsignor implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// タイトル
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		dataLoad();
	}

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
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
		// MSG-0009=登録しますか？
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * 画面起動時、クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:入力エリアをクリアします。 <BR>
	 * <BR>
	 * <DIR>
	 *   1.入力エリアの項目を初期値に戻します。 <BR>
	 *   <DIR>
	 *     初期値については<CODE>page_Load(ActionEvent e)</CODE>を参照してください。 <BR>
	 *   </DIR>
	 *   2.カーソルを入荷予定日(必須)位置にセットします。 <BR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。
	 */
	private void dataLoad() throws Exception
	{
		// フォーカスを荷主コード(必須)使用桁に初期セットします
		setFocus(txt_CnsgnrCdLen);

		Connection conn = null;
		try
		{
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			MasterDefineDataParameter initParam = new MasterDefineDataParameter();
			initParam.setSelectDefineLoadData(MasterDefineDataParameter.SELECTDEFINELOADDATA_CONSIGNOR);
			
			WmsScheduler schedule = new MasterDefineLoadDataMenuSCH();
			MasterDefineDataParameter[] param = (MasterDefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{

				// 荷主コード使用桁
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				// 荷主コード最大桁
				lbl_MaxLenConsignorCode.setText(param[0].getMaxFigure_ConsignorCode());
				// 荷主コード位置
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				// 荷主名称有効
				chk_CnsgnrName.setChecked(param[0].getValid_ConsignorName());
				// 荷主名称使用桁
				txt_CnsgnrName.setText(param[0].getFigure_ConsignorName());
				// 荷主名称最大桁
				lbl_MaxLenConsignorName.setText(param[0].getMaxFigure_ConsignorName());
				// 荷主名称位置
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				

			}
		}
		catch (Exception ex)
		{

			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));

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
	 * チェックボックスにチェックが入っているかチェックを行うメソッドです。<BR>
	 * <BR>
	 * 概要:一つもチェックが入っていない場合falseを返します。<BR>
	 * 
	 * @throws Exception 全ての例外を報告します。
	 */
	private boolean checkCheckBox() throws Exception
	{
		// 荷主コード
		if (!chk_CnsgnrCd.getChecked() && 
				//	荷主名称
				!chk_CnsgnrName.getChecked())
		{
			return false;
		}

		return true;
	}
	
	// Event handler methods -----------------------------------------
	/** 
	 * 戻るボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:前画面に戻ります。 <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		// 画面遷移
		forward("/master/masterdefineloaddatamenu/MasterDefineLoadDataMenu.do");
	}

	/** 
	 * メニューボタンが押下された時に呼ばれます。 <BR>
	 * <BR>
	 * メニュー画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
	    forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	/** 
	 * 登録ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:登録を行うかを、ダイアログボックスを表示し、確認します。 <BR>
	 * <BR>
	 * ダイアログの表示を行います。 <BR>
	 * ･登録確認ダイアログボックスを表示します。"登録しますか?" <BR>
	 * <DIR>
	 *   [確認ダイアログ キャンセル] <BR>
	 *   <DIR>
	 *     何も行いません。 <BR>
	 *   </DIR>
	 *   [確認ダイアログ OK] <BR>
	 *   <DIR>
	 *     概要:入力エリア情報で登録を行います。 <BR>
	 *     1.入力チェックを行います。 <BR>
	 *     <DIR>
	 *       各チェックボックスにチェックがない場合:"登録対象を選択してください。"のメッセージを表示します。 <BR>
	 *     </DIR>
	 *     2.入力エリアの入力項目のチェックを行います。(必須入力,文字数,文字属性) <BR>
	 *     <DIR>
	 *       ･使用桁が、最大桁以下であること <BR>
	 *       ･使用桁が、0より大きいこと <BR>
	 *       ･位置が、0より大きいこと <BR>
	 *       *位置の値は重複は可(この場合、指定した各々のデータ項目に同じデータが取り込まれます) <BR>
	 *     </DIR>
	 *     3.スケジュールを開始します。 <BR>
	 *     4.カーソルを入荷予定日(必須)使用桁にセットします。 <BR>
	 *   </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		// フォーカスを入荷予定日(必須)使用桁に初期セットします
		setFocus(txt_CnsgnrCdLen);

		// チェックボックスのチェックを行います。
		if (!checkCheckBox())
		{
			// 6023174=少なくとも１項目以上は選択してください。
			message.setMsgResourceKey("6023174");
			return;
		}

		// 入力チェック
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenConsignorCode, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CnsgnrName, txt_CnsgnrName, lbl_MaxLenConsignorName, txt_CnsgnrNmPst);

		Connection conn = null;
		try
		{
			MasterDefineDataParameter[] param = new MasterDefineDataParameter[1];
			param[0] = new MasterDefineDataParameter();
			
			// パラメータに値をセットします
			param[0].setSelectDefineLoadData(MasterDefineDataParameter.SELECTDEFINELOADDATA_CONSIGNOR);
			
			// 荷主コード有効
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			// 荷主コード使用桁
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			// 荷主コード最大桁
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenConsignorCode.getText());
			// 荷主コード位置
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			// 荷主名称有効
			param[0].setValid_ConsignorName(chk_CnsgnrName.getChecked());
			// 荷主名称使用桁
			param[0].setFigure_ConsignorName(txt_CnsgnrName.getText());
			// 荷主名称最大桁
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenConsignorName.getText());
			// 荷主名称位置
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new MasterDefineLoadDataMenuSCH();
			
			// スケジュールスタート
			if (schedule.startSCH(conn, param))
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
			}

			if (schedule.getMessage() != null)
			{
				// メッセージを表示します
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
		{

			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));

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
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	/** 
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:入力エリアをクリアします。 <BR>
	 * <BR>
	 * <DIR>
	 *   1.入力エリアの項目を初期値に戻します。 <BR>
	 *   <DIR>
	 *     初期値については<CODE>page_Load(ActionEvent e)</CODE>を参照してください。 <BR>
	 *   </DIR>
	 *   2.カーソルを出荷予定日(必須)使用桁にセットします。 <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
	    dataLoad();
	}

}
//end of class
