// $Id: MasterDefineLoadDataMenuCustomerBusiness.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $
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
 * マスタデータ取り込み項目設定(出荷先マスタ項目設定)画面クラスです。 <BR>
 * 入力エリアから入力した内容をパラメータにセットしスケジュールに渡し、出荷先マスタ項目設定を登録します。 <BR>
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
 * 	 出荷先コード	(必須)(使用桁)* <BR>
 * 	 出荷先コード	(必須)(最大桁)* <BR>
 * </DIR>
 * <BR>
 *   [パラメータ]+有効にチェックされていれば必須入力 <BR>
 * <BR>
 * <DIR>
 *   出荷先名称    (使用桁)+ <BR>
 *   出荷先名称    (最大桁)+ <BR>
 *   郵便番号    (使用桁)+ <BR>
 *   郵便番号    (最大桁)+ <BR>
 *   県名    (使用桁)+ <BR>
 *   県名    (最大桁)+ <BR>
 *   住所    (使用桁)+ <BR>
 *   住所    (最大桁)+ <BR>
 *   ビル名等    (使用桁)+ <BR>
 *   ビル名等    (最大桁)+ <BR>
 *   連絡先1    (使用桁)+ <BR>
 *   連絡先1    (最大桁)+ <BR>
 *   連絡先2    (使用桁)+ <BR>
 *   連絡先2    (最大桁)+ <BR>
 *   連絡先3    (使用桁)+ <BR>
 *   連絡先3    (最大桁)+ <BR>
 * </DIR>
 * <BR>
 * </DIR>
 * <BR>
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
public class MasterDefineLoadDataMenuCustomerBusiness extends MasterDefineLoadDataMenuCustomer implements WMSConstants
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
			initParam.setSelectDefineLoadData(MasterDefineDataParameter.SELECTDEFINELOADDATA_CUSTOMER);
			
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

				// 出荷先コード使用桁
				txt_CustomerCodeLen.setText(param[0].getFigure_CustomerCode());
				// 出荷先コード最大桁
				lbl_MaxLenCustomerCode.setText(param[0].getMaxFigure_CustomerCode());
				// 出荷先コード位置
				txt_CustCdPst.setText(param[0].getPosition_CustomerCode());
				
				// 出荷先名称有効
				chk_CustomerName.setChecked(param[0].getValid_CustomerName());
				// 出荷先名称使用桁
				txt_CustomerNameLen.setText(param[0].getFigure_CustomerName());
				// 出荷先名称最大桁
				lbl_MaxLenCustomerName.setText(param[0].getMaxFigure_CustomerName());
				// 出荷先名称位置
				txt_CustNmPst.setText(param[0].getPosition_CustomerName());
				
				// 郵便番号有効
				chk_PostCode.setChecked(param[0].getValid_PostalCode());
				// 郵便番号使用桁
				txt_PostCodeLen.setText(param[0].getFigure_PostalCode());
				// 郵便番号最大桁
				lbl_MaxLenPostCode.setText(param[0].getMaxFigure_PostalCode());
				// 郵便番号位置
				txt_PostCodePst.setText(param[0].getPosition_PostalCode());

				// 県名有効
				chk_Prefecture.setChecked(param[0].getValid_Prefecture());
				// 県名使用桁
				txt_PrefectureLen.setText(param[0].getFigure_Prefecture());
				// 県名最大桁
				lbl_MaxLenPrefecture.setText(param[0].getMaxFigure_Prefecture());
				// 県名位置
				txt_PrefecturePst.setText(param[0].getPosition_Prefecture());
				
				// 住所有効
				chk_Adress1.setChecked(param[0].getValid_Adress1());
				// 住所使用桁
				txt_Adress1Len.setText(param[0].getFigure_Adress1());
				// 住所最大桁
				lbl_MaxLenAdress1.setText(param[0].getMaxFigure_Adress1());
				// 住所位置
				txt_Adress1Pst.setText(param[0].getPosition_Adress1());
				
				// ビル名等有効
				chk_Adress2.setChecked(param[0].getValid_Adress2());
				// ビル名等使用桁
				txt_Adress2Len.setText(param[0].getFigure_Adress2());
				// ビル名等最大桁
				lbl_MaxLenAdress2.setText(param[0].getMaxFigure_Adress2());
				// ビル名等位置
				txt_Adress2Pst.setText(param[0].getPosition_Adress2());

				// 連絡先1有効
				chk_Contact1.setChecked(param[0].getValid_Contact1());
				// 連絡先1使用桁
				txt_Contact1Len.setText(param[0].getFigure_Contact1());
				// 連絡先1最大桁
				lbl_MaxLenContact1.setText(param[0].getMaxFigure_Contact1());
				// 連絡先1位置
				txt_Contact1Pst.setText(param[0].getPosition_Contact1());
				
				// 連絡先2有効
				chk_Contact2.setChecked(param[0].getValid_Contact2());
				// 連絡先2使用桁
				txt_Contact2Len.setText(param[0].getFigure_Contact2());
				// 連絡先2最大桁
				lbl_MaxLenContact2Len.setText(param[0].getMaxFigure_Contact2());
				// 連絡先2位置
				txt_Contact2Pst.setText(param[0].getPosition_Contact2());
				
				// 連絡先3有効
				chk_Contact3.setChecked(param[0].getValid_Contact3());
				// 連絡先3使用桁
				txt_Contact3Len.setText(param[0].getFigure_Contact3());
				// 連絡先3最大桁
				lbl_MaxLenContact3.setText(param[0].getMaxFigure_Contact3());
				// 連絡先3位置
				txt_Contact3Pst.setText(param[0].getPosition_Contact3());

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
				// 出荷先コード
				!chk_CustomerCode.getChecked() &&
				// 出荷先名称
				!chk_CustomerName.getChecked() &&
				// 郵便番号
				!chk_PostCode.getChecked() &&
				// 都道府県名
				!chk_Prefecture.getChecked() &&
				// 住所
				!chk_Adress1.getChecked() &&
				// ビル名等
				!chk_Adress2.getChecked() &&
				// 連絡先1
				!chk_Contact1.getChecked() &&
				// 連絡先2
				!chk_Contact1.getChecked() &&
				// 連絡先3
				!chk_Contact1.getChecked())
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
		WmsCheckker.checkDefine(lbl_CustomerCode, chk_CustomerCode, txt_CustomerCodeLen, lbl_MaxLenCustomerCode, txt_CustCdPst);
		WmsCheckker.checkDefine(lbl_CustomerName, chk_CustomerName, txt_CustomerNameLen, lbl_MaxLenCustomerName, txt_CustNmPst);
		WmsCheckker.checkDefine(lbl_PostalCode, chk_PostCode, txt_PostCodeLen, lbl_MaxLenPostCode, txt_PostCodePst);
		WmsCheckker.checkDefine(lbl_Prefecture, chk_Prefecture, txt_PrefectureLen, lbl_MaxLenPrefecture, txt_PrefecturePst);
		WmsCheckker.checkDefine(lbl_Address, chk_Adress1, txt_Adress1Len, lbl_MaxLenAdress1, txt_Adress1Pst);
		WmsCheckker.checkDefine(lbl_Address2, chk_Adress2, txt_Adress2Len, lbl_MaxLenAdress2, txt_Adress2Pst);
		WmsCheckker.checkDefine(lbl_Contact1, chk_Contact1, txt_Contact1Len, lbl_MaxLenContact1, txt_Contact1Pst);
		WmsCheckker.checkDefine(lbl_Contact2, chk_Contact2, txt_Contact2Len, lbl_MaxLenContact2Len, txt_Contact2Pst);
		WmsCheckker.checkDefine(lbl_Contact3, chk_Contact3, txt_Contact3Len, lbl_MaxLenContact3, txt_Contact3Pst);

		Connection conn = null;
		try
		{
			MasterDefineDataParameter[] param = new MasterDefineDataParameter[1];
			param[0] = new MasterDefineDataParameter();
			
			// パラメータに値をセットします
			param[0].setSelectDefineLoadData(MasterDefineDataParameter.SELECTDEFINELOADDATA_CUSTOMER);
			
			// 荷主コード有効
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			// 荷主コード使用桁
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			// 荷主コード最大桁
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenConsignorCode.getText());
			// 荷主コード位置
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			// 出荷先コード有効
			param[0].setValid_CustomerCode(chk_CustomerCode.getChecked());
			// 出荷先コード用桁
			param[0].setFigure_CustomerCode(txt_CustomerCodeLen.getText());
			// 出荷先コード最大桁
			param[0].setMaxFigure_CustomerCode(lbl_MaxLenCustomerCode.getText());
			// 出荷先コード位置
			param[0].setPosition_CustomerCode(txt_CustCdPst.getText());
			
			param[0].setValid_CustomerName(chk_CustomerName.getChecked());
			// 出荷先名称使用桁
			param[0].setFigure_CustomerName(txt_CustomerNameLen.getText());
			// 出荷先名称最大桁
			param[0].setMaxFigure_CustomerName(lbl_MaxLenCustomerName.getText());
			// 出荷先名称位置
			param[0].setPosition_CustomerName(txt_CustNmPst.getText());
			
			// 郵便番号有効
			param[0].setValid_PostalCode(chk_PostCode.getChecked());
			// 郵便番号使用桁
			param[0].setFigure_PostalCode(txt_PostCodeLen.getText());
			// 郵便番号最大桁
			param[0].setMaxFigure_PostalCode(lbl_MaxLenPostCode.getText());
			// 郵便番号位置
			param[0].setPosition_PostalCode(txt_PostCodePst.getText());
			
			// 県名有効
			param[0].setValid_Prefecture(chk_Prefecture.getChecked());
			// 県名用桁
			param[0].setFigure_Prefecture(txt_PrefectureLen.getText());
			// 県名最大桁
			param[0].setMaxFigure_Prefecture(lbl_MaxLenPrefecture.getText());
			// 県名位置
			param[0].setPosition_Prefecture(txt_PrefecturePst.getText());
			
			// 住所有効
			param[0].setValid_Adress1(chk_Adress1.getChecked());
			// 住所使用桁
			param[0].setFigure_Adress1(txt_Adress1Len.getText());
			// 住所最大桁
			param[0].setMaxFigure_Adress1(lbl_MaxLenAdress1.getText());
			// 住所位置
			param[0].setPosition_Adress1(txt_Adress1Pst.getText());
			
			// ビル名等有効
			param[0].setValid_Adress2(chk_Adress2.getChecked());
			// ビル名等使用桁
			param[0].setFigure_Adress2(txt_Adress2Len.getText());
			// ビル名等最大桁
			param[0].setMaxFigure_Adress2(lbl_MaxLenAdress2.getText());
			// ビル名等位置
			param[0].setPosition_Adress2(txt_Adress2Pst.getText());
			
			// 連絡先1有効
			param[0].setValid_Contact1(chk_Contact1.getChecked());
			// 連絡先1用桁
			param[0].setFigure_Contact1(txt_Contact1Len.getText());
			// 連絡先1最大桁
			param[0].setMaxFigure_Contact1(lbl_MaxLenContact1.getText());
			// 連絡先1位置
			param[0].setPosition_Contact1(txt_Contact1Pst.getText());
			
			param[0].setValid_Contact2(chk_Contact2.getChecked());
			// 連絡先2使用桁
			param[0].setFigure_Contact2(txt_Contact2Len.getText());
			// 連絡先2最大桁
			param[0].setMaxFigure_Contact2(lbl_MaxLenContact2Len.getText());
			// 連絡先2位置
			param[0].setPosition_Contact2(txt_Contact2Pst.getText());
			
			param[0].setValid_Contact3(chk_Contact3.getChecked());
			// 連絡先3使用桁
			param[0].setFigure_Contact3(txt_Contact3Len.getText());
			// 連絡先3名称最大桁
			param[0].setMaxFigure_Contact3(lbl_MaxLenContact3.getText());
			// 連絡先3名称位置
			param[0].setPosition_Contact3(txt_Contact3Pst.getText());
			

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
