// $Id: TerminalListBusiness.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.listbox.terminallist;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.authentication.AbstractAuthentication;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.dbhandler.ResultMap;
import jp.co.daifuku.dbhandler.ScrollHandler;
import jp.co.daifuku.dbhandler.TableColumns;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.tools.util.ToolParam;
import jp.co.daifuku.ui.web.ToolTipHelper;

/** <jp>
 * 端末一覧の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </jp> */
/** <en>
 * This screen class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </en> */
public class TerminalListBusiness extends TerminalList implements ToolConstants, TableColumns
{
	// Class fields --------------------------------------------------
    /**
     * SCROLLHANDLERをセッションに保持するキー
     */ 
    private final String KEY_SCROLLHANDLER = "KEY_SCROLLHANDLER";
    
	/** 
	 * 端末№の受け渡しに使用するキーです
	 */
	public static final String TERMINALNUMBER_KEY = "TERMINALNUMBER_KEY";
	/** 
	 * 端末名の受け渡しに使用するキーです
	 */
	public static final String TERMINALNAME_KEY = "TERMINALNAME_KEY";
	/** 
	 * 端末IPアドレスの受け渡しに使用するキーです
	 */
	public static final String IPADDRESS_KEY = "IPADDRESS_KEY";	
	/** 
	 * ロールIDの受け渡しに使用するキーです
	 */
	public static final String TERMINAL_ROLEID_KEY = "TERMINAL_ROLEID_KEY";	
	/** 
	 * プリンタ名の受け渡しに使用するキーです
	 */
	public static final String PRINTERNAME_KEY = "PRINTERNAME_KEY";	
	
	/** 
	 * 親画面のメニュータイプの受け渡しに使用するキーです
	 */
	public static final String MENUTYPE_KEY = "MENUTYPE_KEY";	
	

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/** 
	 * 画面の初期化を行います。
	 * @param e ActionEvent
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
	    //画面名をセットする
	    lbl_ListName.setText(DispResources.getText("TLE-T0017"));
	    
		Connection conn = null;		
		try
		{
			//Pager初期化
			setPagerValue(0, 0, 0);
			//前回保持したインスタンスを初期化する
			ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
			if(handle != null)
			{
				handle.close();
			}
			
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			handle = new ScrollHandler(conn);
			BaseHandler baseHandle = new BaseHandler();
			
			//呼び出し元画面でセットされたパラメータの取得
			String[] terminalnumber = new String[1]; 
			terminalnumber[0] = this.request.getParameter( TERMINALNUMBER_KEY );
		    //ViewStateに保存
		    this.getViewState().setString("TerminalNumber",terminalnumber[0]);
		    
		    // 呼び出し元画面メニュータイプの取得
			String menutype = this.request.getParameter( MENUTYPE_KEY );
		    //ViewStateに保存
		    this.getViewState().setString("MenuType",menutype);
			
			int total = 0;			
			//親画面のメニュータイプが客先の場合
			if(menutype.equals(MENU_CUSTOMER))
			{
				//Terminal表の検索条件
				String[] terminal = new String[2];
			    //システム定義の端末
				terminal[0] = AbstractAuthentication.UNDEFINED_TERMINAL;
				
				//端末№テキストボックスの値によって場合分け
				if(terminalnumber[0].equals(""))
				{
					//データ数合計
					total = baseHandle.count("_select-5408", terminal, conn);			    
				}
				else if(terminalnumber[0].indexOf("*") > -1)
				{
				    //「*」を「%」に置換
				    terminal[1]=terminalnumber[0].replace('*','%');
				    //データ数合計
				    total = baseHandle.count("_select-5409", terminal, conn);			    
				}
				else
				{	
				    //端末№をセット
				    terminal[1]=terminalnumber[0];
					//データ数合計
					total = baseHandle.count("_select-5410", terminal, conn);   
				}			 			    
			}
			//メニュータイプが設定者の場合
			else
			{
				//端末№テキストボックスの値によって場合分け
				if(terminalnumber[0].equals(""))
				{
					//データ数合計
					total = baseHandle.count("_select-5405", null, conn);			    
				}
				else if(terminalnumber[0].indexOf("*") > -1)
				{
				    //「*」を「%」に置換
				    terminalnumber[0]=terminalnumber[0].replace('*','%');
				    //データ数合計
				    total = baseHandle.count("_select-5406", terminalnumber, conn);			    
				}
				else
				{
					//データ数合計
					total = baseHandle.count("_select-5402", terminalnumber, conn);   
				}			 
			}
			
			//1ページの表示件数
			int page = Integer.parseInt(ToolParam.getProperty("ListboxSearchCount"));
			//最初のページの表示終了位置
			int end = 0;
			//データがある場合
			if(total > 0)
			{
				handle.open();
				if(total <= page)
				{
					end = total;
				}
				else
				{
					end = page;
				}

				//リストデータをセット
				setList(handle, 0, end);
				//pagerに値をセット
				setPagerValue(1, total, page);
				//セッションにHandlerの値をセット
				this.getSession().setAttribute(KEY_SCROLLHANDLER, handle);
			}
			else
			{
				//Pagerへの値セット
				pgr_U.setMax(0);	//最大件数
				pgr_U.setPage(0);	//1Page表示件数
				pgr_U.setIndex(0);	//開始位置				
				pgr_D.setMax(0);	//最大件数
				pgr_D.setPage(0);	//1Page表示件数
				pgr_D.setIndex(0);	//開始位置
				//ヘッダーを隠します
				lst_TerminalList.setVisible(false);
				//対象データはありませんでした
				lbl_InMsg.setResourceKey("MSG-9016");
				//セッションにHandlerの値をセット
				this.getSession().setAttribute(KEY_SCROLLHANDLER, handle);
			}
		}
		finally
		{

		}
	}


	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/** 
	 * リストセルに値をセットします
	 * @param handle ScrollHandler
	 * @param next_index int
	 * @param next_end int 
	 * @throws Exception 
	 */
	private void setList(ScrollHandler handle, int next_index, int next_end) throws Exception
	{
		//呼び出し元画面でセットされたパラメータの取得
		String[] terminalnumber = new String[1]; 
		//ViewStateから端末№を取得
	    terminalnumber[0] = this.getViewState().getString("TerminalNumber");
		//ViewStateからメニュータイプを取得
	    String menutype = this.getViewState().getString("MenuType");
		
		List list = null;
		//親画面のメニュータイプが客先の場合
		if(menutype.equals(MENU_CUSTOMER))
		{
			//Terminal表の検索条件
			String[] terminal = new String[2];
		    //システム定義の端末
			terminal[0] = AbstractAuthentication.UNDEFINED_TERMINAL;
			
			//端末№テキストボックスの値によって場合分け
			if(terminalnumber[0].equals(""))
			{
			    //データベースからリストデータを取得
				list = handle.find("_select-5411",terminal, next_index, next_end);
			}
			else if(terminalnumber[0].indexOf("*") > -1)
			{
			    //「*」を「%」に置換
			    terminal[1] = terminalnumber[0].replace('*','%');
			    //データベースからリストデータを取得
			    list = handle.find("_select-5412", terminal, next_index, next_end);
			}
			else
			{
			    //端末№をセット
			    terminal[1] = terminalnumber[0];
			    //データベースからリストデータを取得
				list = handle.find("_select-5413",terminal, next_index, next_end);
			}		    		    
		}
		//親画面のメニュータイプが設定者の場合
		else
		{
			//端末№テキストボックスの値によって場合分け
			if(terminalnumber[0].equals(""))
			{
			    //データベースからリストデータを取得
				list = handle.find("_select-5404",null, next_index, next_end);
			}
			else if(terminalnumber[0].indexOf("*") > -1)
			{
			    //「*」を「%」に置換
			    terminalnumber[0] = terminalnumber[0].replace('*','%');
			    //データベースからリストデータを取得
			    list = handle.find("_select-5407", terminalnumber, next_index, next_end);
			}
			else
			{
			    //データベースからリストデータを取得
				list = handle.find("_select-5400",terminalnumber, next_index, next_end);
			}		    
		}
		
		
		//表を削除
		lst_TerminalList.clearRow();

		//Tipで使用する銘板
		String title_TerminalName = DispResources.getText("LBL-T0041");
		String title_IpAddress = DispResources.getText("LBL-T0042");
		String title_PrinterName = DispResources.getText("LBL-T0043");
		
		Iterator itr = list.iterator();
		while(itr.hasNext())
		{
			ResultMap map = (ResultMap)itr.next();

			//最終行を取得
			int count = lst_TerminalList.getMaxRows();
			//行追加
			lst_TerminalList.setCurrentRow(count);
			lst_TerminalList.addRow();

			//表にデータをセット
			lst_TerminalList.setValue(1, Integer.toString(count + next_index));
			lst_TerminalList.setValue(2, (String)map.get(TERMINAL_TERMINALNUMBER));
			lst_TerminalList.setValue(3, (String)map.get(TERMINAL_TERMINALNAME));
			lst_TerminalList.setValue(4, (String)map.get(TERMINAL_IPADDRESS));
			lst_TerminalList.setValue(5, (String)map.get(TERMINAL_ROLEID));
			lst_TerminalList.setValue(6, (String)map.get(TERMINAL_PRINTERNAME));
			
			//端末名のNull処理
			String terminalname = (String)map.get(TERMINAL_TERMINALNAME);
			if(terminalname == null)
			{
			    terminalname = ""; 
			}
			//端末IPのNull処理
			String ipaddress = (String)map.get(TERMINAL_IPADDRESS);
			if(ipaddress == null)
			{
			    ipaddress = ""; 
			}
			//プリンタ名のNull処理
			String printername = (String)map.get(TERMINAL_PRINTERNAME);
			if(printername == null)
			{
			   printername = ""; 
			}
			
			//ToolTipをセット
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(title_TerminalName, terminalname);
			toolTip.add(title_IpAddress, ipaddress);
			toolTip.add(title_PrinterName, printername);	
			lst_TerminalList.setToolTip(count, toolTip.getText());

		}
	}
	
	/** 
	 * ページャーに値をセットします
	 * @param index int
	 * @param total int
	 * @param page int
	 * @throws Exception 
	 */
	private void setPagerValue(int index, int total, int page)
	{
		pgr_U.setIndex(index);
		pgr_U.setMax(total);
		pgr_U.setPage(page);
		pgr_D.setIndex(index);
		pgr_D.setMax(total);
		pgr_D.setPage(page);
	}
	// Event handler methods -----------------------------------------
	/** 
	 * 閉じるボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		//終了処理
		ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
		if(handle != null)
		{
			handle.close();
		}
		this.getSession().removeAttribute(KEY_SCROLLHANDLER);

		//呼び出し元の画面へ遷移します
		parentRedirect(null);
	}

	/** 
	 * Pagerの次へボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{	    
		//Handlerにセッションの値をセット
		ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
		int total = pgr_U.getMax();
		int page = pgr_U.getPage();
		int index = pgr_U.getIndex();
		int next_index = 0;
		int next_end = 0;
		
		if(index + page*2 <= total)
		{
			next_index = index + page -1;
			next_end = index + page*2 -1;
		}
		else
		{
			next_index =index + page -1;
			next_end = total ;
		}
		//リストデータをセット
		setList(handle, next_index, next_end);
		
		//Pagerに値をセット
		setPagerValue(next_index+1, total, page);	
	}

	/** 
	 * Pagerの前へボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
	    // Handlerにセッションの値をセット
		ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
		int total = pgr_U.getMax();
		int page = pgr_U.getPage();
		int index = pgr_U.getIndex();
		int next_index = 0;
		int next_end = 0;
		
		if(index - page <= 0)
		{
			next_index = 0;
			next_end = page;
		}
		else
		{
			next_index = index - page - 1;
			next_end = index - 1;
		}
	
		//リストデータをセット
		setList(handle, next_index, next_end);
		
		//Pagerに値をセット
		setPagerValue(next_index + 1, total, page);	
	}

	/** 
	 * Pagerの最後ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		//Handlerにセッションの値をセット
		ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
		int total = pgr_U.getMax();
		int page = pgr_U.getPage();

		int next_index = 0;
		int next_end = 0;
		if(total%page == 0)
		{
			next_index = total-page;
			next_end = total;
		}
		else
		{
			next_index = total - (total%page);
			next_end = total;
		}
		
		//リストデータをセット
		setList(handle, next_index, next_end);
		
		//Pagerに値をセット
		setPagerValue(next_index+1, total, page);	
	}

	/** 
	 * Pagerの最初ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		//Handlerにセッションの値をセット
		ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
		int total = pgr_U.getMax();
		int page = pgr_U.getPage();

		//リストデータをセット
		setList(handle, 0, page);
		
		//Pagerに値をセット
		setPagerValue(1, total, page);		
	}


	/** 
	 * リストが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_TerminalList_Click(ActionEvent e) throws Exception
	{
		//現在の行をセット
		lst_TerminalList.setCurrentRow(lst_TerminalList.getActiveRow());
		
		//呼び出し元の画面へ渡すパラメータ作成
		ForwardParameters param = new ForwardParameters();
		param.setParameter(TERMINALNUMBER_KEY, lst_TerminalList.getValue(2));
		param.setParameter(TERMINALNAME_KEY, lst_TerminalList.getValue(3));
		param.setParameter(IPADDRESS_KEY, lst_TerminalList.getValue(4));
		param.setParameter(TERMINAL_ROLEID_KEY, lst_TerminalList.getValue(5));
		param.setParameter(PRINTERNAME_KEY, lst_TerminalList.getValue(6));
		
		//終了処理
		btn_Close_U_Click(null);
		
		//呼び出し元の画面へ遷移します
		parentRedirect(param);

	}

	/** 
	 * Pagerの次へボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		pgr_U_Next(e);
	}

	/** 
	 * Pagerの前へボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		pgr_U_Prev(e);
	}

	/** 
	 * Pagerの最後ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		pgr_U_Last(e);
	}

	/** 
	 * Pagerの最初ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		pgr_U_First(e);
	}


	/** 
	 * 閉じるボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		btn_Close_U_Click(e);
	}


}
//end of class
