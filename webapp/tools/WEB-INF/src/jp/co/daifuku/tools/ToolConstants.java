// $Id: ToolConstants.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools;

import jp.co.daifuku.Constants;
import jp.co.daifuku.bluedog.util.MessageResources;



/** <jp>
 * 定数定義インターフェイスです。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/06/26</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
 * @author  $Author: mori $
 </jp> */
/** <en>
 * It is the constant definition interface.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/06/26</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
 * @author  $Author: mori $
 </en> */
public interface ToolConstants extends Constants
{

	// Class fields --------------------------------------------------
	
	public static final String[] BACKUP_TABLES={"Loginuser","userattribute","authenticationsystem",
												"terminal","role","rolemap","function","functionmap",
												"mainmenu"};
	
	//登録、修正、削除ボタンをViewStateへ保持するためのキー
	public static final String PROCESS_KEY = "PROCESS_KEY";
	//登録ボタン押下時のキー
	public static final String INSERT = "INSERT";
	//修正ボタン押下時のキー
	public static final String MODIFY = "MODIFY";
	//削除ボタン押下時のキー
	public static final String DELETE = "DELETE";
	
	// Toolで使用するデータソース名
	public static final String DATASOURCE_NAME = "MenuTool";
	
	// Product key ProductType
	public static final String PRODUCTTYPE = "Product";
	// Product key MenuType
	public static final String MENUTYPE = "MenuType";
	
	// Product key AWC
	public static final String PRODUCT_AWC = "awc";
	// Product key WMS
	public static final String PRODUCT_WMS = "wms";
	// Product key SAM
	public static final String PRODUCT_SAM = "sam";
	
	// Menu key SE
	public static final String MENU_SE = "0";
	// Menu key Customer
	public static final String MENU_CUSTOMER = "1";
	
	// MAINMENU表のアイコン表示方法　クリック可能
	public static final int SHOWTYPE_ENABLE = 0;
	// MAINMENU表のアイコン表示方法　未使用
	public static final int SHOWTYPE_DISABLE = 1;
	// MAINMENU表のアイコン表示方法　未使用
	public static final int SHOWTYPE_NOTUSE = 2;
	
	// メニュー表示順・機能表示順　未使用項目
	public static final int NOTDISPITEM = -1;
	
	// パスワード更新間隔　無制限
	public static final int PWDCHANGEINTERVAL_UNRESTRICTED = -1;
	
	// 同時ログイン数　無制限
	public static final int LOGINMAX_UNRESTRICTED = -1;

	// 認証ミスカウント保持期間　無制限
	public static final int FAILEDTIME_UNRESTRICTED = -1;
	
	// 性別　未入力
	public static final int SEX_UNINPUT = 0;
	// 性別　男性
	public static final int SEX_MALE = 1;
	// 性別　女性
	public static final int SEX_FEMALE = 2;

	// 認証ミス猶予回数　無制限
	public static final int FAILEDLOGINATTEMPTS_UNRESTRICTED= -1;
	// 認証ミス猶予回数　ユーザ単位でカウントなし
	public static final int FAILEDLOGINATTEMPTS_NOCOUNT= 0;
	// 認証ミス猶予回数　1
	public static final int FAILEDLOGINATTEMPTS_NUMBER1= 1;
	// 認証ミス猶予回数　3
	public static final int FAILEDLOGINATTEMPTS_NUMBER2= 3;
	// 認証ミス猶予回数　5
	public static final int FAILEDLOGINATTEMPTS_NUMBER3= 5;
	// 認証ミス猶予回数 10
	public static final int FAILEDLOGINATTEMPTS_NUMBER4= 10;
	// 認証ミス猶予回数 20　
	public static final int FAILEDLOGINATTEMPTS_NUMBER5= 20;
		
	// メニューリソースキーの検索キー
	public static final String MENURESOUCEKEY = "MNU.*";
	
	// 機能リソースキーの検索キー
	public static final String FUNCTIONRESOUCEKEY = "MTLE.*";
	
	// ボタンリソースキーの検索キー
	public static final String BUTTONRESOUCEKEY = "MBTN.*";
	
	// ページ名リソースキーの検索キー
	public static final String PAGENAMERESOUCEKEY = "TLE.*";
	
	// FunctionMap表　認証チェック　あり
	public static final int  DOAUTHENTICATION_ON = 1;
	
	// FunctionMap表　認証チェック　なし
	public static final int  DOAUTHENTICATION_OFF = 0;
	
	// AUTHENTICATIONSYSTEM表の同一ユーザログイン　許可する
	public static final int SAMELOGINUSER_PERMISSION = 1;
	// AUTHENTICATIONSYSTEM表の同一ユーザログイン　許可しない
	public static final int SAMELOGINUSER_DENY = 0;
	
	// AUTHENTICATIONSYSTEM表の場所の制約チェック　する
	public static final int RESTRICTIONSPLACE_ENABLE = 1;
	// AUTHENTICATIONSYSTEM表の場所の制約チェック　しない
	public static final int RESTRICTIONSPLACE_DISABLE = 0;
	
	// AUTHENTICATIONSYSTEM表の場所のメインメニューの種類　大きいアイコン
	public static final int MAINMENUTYPE_LARGEICON = 1;
	// AUTHENTICATIONSYSTEM表の場所のメインメニューの種類　小さいアイコン
	public static final int MAINMENUTYPE_SMALLICON = 0;
	
	
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class