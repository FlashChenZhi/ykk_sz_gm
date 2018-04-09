// $Id: ResourceListBusiness.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.listbox.resourcelist;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.dbhandler.TableColumns;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.tools.util.DispResourceMap;
import jp.co.daifuku.tools.util.ToolParam;

/** <jp>
 * リソース一覧の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
 * @author  $Author: mori $
 </en> */
public class ResourceListBusiness extends ResourceList implements ToolConstants, TableColumns
{
	// Class fields --------------------------------------------------	
	/**
	 * 親画面が必要するリソースキーを判別するフラグです
	 */
    public static final String BTNFLAG_KEY = "BTNFLAG_KEY";
    
	/** 
	 * リソースキーの受け渡しに使用するキーです
	 */
	public static final String RESOURCE_KEY = "RESOURCE_KEY";
	
	/** 
	 * リソース名の受け渡しに使用するキーです
	 */
	public static final String RESOURCENAME_KEY = "RESOURCENAME_KEY";	

	/** 
	 * ボタンリソースキーの受け渡しに使用するキーです
	 */
	public static final String BUTTONRESOURCE_KEY = "BUTTONRESOURCE_KEY";
	
	/** 
	 * ボタン名の受け渡しに使用するキーです
	 */
	public static final String BUTTONNAME_KEY = "BUTTONNAME_KEY";	
	
	/** 
	 * ページ名リソースキーの受け渡しに使用するキーです
	 */
	public static final String PAGENAMERESOURCE_KEY = "PAGENAMERESOURCE_KEY";
	
	/** 
	 * ページ名の受け渡しに使用するキーです
	 */
	public static final String PAGENAME_KEY = "PAGENAME_KEY";	
	
	
	// Class variables -----------------------------------------------
	/**
	 * リソースキーの判別に使用するキーです
	 */
    private String wDispSearchKey = null;
	
	
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
	    lbl_ListName.setText(DispResources.getText("TLE-T0003"));
	      
		//呼び出し元画面でセットされたパラメータの取得
	    String flag = request.getParameter(BTNFLAG_KEY);
	    //ViewStateに保存
	    this.getViewState().setString("BTFLAG",flag);
	    //呼び出し元画面を判別します
	    displayFlag(flag);
		
		//Pager初期化
		setPagerValue(0, 0, 0);
		
		//呼び出し元画面でセットされたパラメータの取得
		String[] resourcekey = new String[1]; 
		resourcekey[0] = this.request.getParameter( RESOURCE_KEY );
	    //ViewStateに保存
	    this.getViewState().setString("RESOURCE",resourcekey[0]);
				
		int total = 0;
		if(resourcekey[0].equals(""))
		{
			//データ数合計
			 total = DispResourceMap.getKeyList(wDispSearchKey).size();		    
		}
		else
		{
            //リソースからリストデータを取得
            List list = DispResourceMap.getKeyList(wDispSearchKey);  
            //リストデータの中からテキストボックスの値に一致するものがあるか検索
	        for(int i=0; i<list.size();i++)
	        {
	            String temp = (String)list.get(i);
	            //リストデータとテキストボックスの値が一致
	            if(temp.equals(resourcekey[0]))
	            {
					//データ数合計
					 total = DispResourceMap.getKeyList(temp).size();
					 break;
	            }   
	        }
		}
		 	 
		//1ページの表示件数
		int page = Integer.parseInt(ToolParam.getProperty("ListboxSearchCount"));
		//最初のページの表示終了位置
		int end = 0;
		//データがある場合
		if(total > 0)
		{

			if(total <= page)
			{
				end = total;
			}
			else
			{
				end = page;
			}
			//リストデータをセット
			setList(wDispSearchKey, 0, end);
			//Pagerに値をセット
			setPagerValue(1, total, page);
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
			lst_ResourceList.setVisible(false);
			//対象データはありませんでした
			lbl_InMsg.setResourceKey("MSG-9016");   
		}
	}
	

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/** 
	 * リストセルに値をセットします
	 * @param String disp_key
	 * @param next_index int
	 * @param next_end int 
	 * @throws Exception 
	 */
	private void setList(String disp_key, int next_index, int next_end) throws Exception
	{	
		//ViewStateから取得
	    String resource = this.getViewState().getString("RESOURCE");
	    
		List list = null;
		//リソースキーテキストボックスの値によって場合分け
	    if(resource.equals(""))
	    {
			//リソースからリストデータを取得
		    list = DispResourceMap.getKeyList(disp_key).subList(next_index, next_end);	        
	    }
	    else
	    {
            //リソースからリストデータを取得
            list = DispResourceMap.getKeyList(resource);  
	    }
	    
		//リスト削除
		lst_ResourceList.clearRow();

		Iterator itr = list.iterator();
		while(itr.hasNext())
		{
			String key = (String)itr.next();
			int count = lst_ResourceList.getMaxRows();
			lst_ResourceList.setCurrentRow(count);
			lst_ResourceList.addRow();

			//リストにデータをセット
			lst_ResourceList.setValue(1, Integer.toString(count + next_index));
			lst_ResourceList.setValue(2, key);
			lst_ResourceList.setValue(3, DispResourceMap.getText(key));
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
		//ViewStateから取得
	    String flag = this.getViewState().getString("BTFLAG");
	    //呼び出し元画面でセットされたパラメータの取得
		displayFlag(flag);
	    		
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
		setList(wDispSearchKey, next_index, next_end);
		
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
		//ViewStateから取得
	    String flag = this.getViewState().getString("BTFLAG");
	    //呼び出し元画面でセットされたパラメータの取得
		displayFlag(flag);
	    
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
		setList(wDispSearchKey, next_index, next_end);
		
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
		//ViewStateから取得
	    String flag = this.getViewState().getString("BTFLAG");
	    //呼び出し元画面でセットされたパラメータの取得
		displayFlag(flag);
	    
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
		setList(wDispSearchKey, next_index, next_end);
		
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
		//ViewStateから取得
	    String flag = this.getViewState().getString("BTFLAG");
	    //呼び出し元画面でセットされたパラメータの取得
		displayFlag(flag);
	    
		int total = pgr_U.getMax();
		int page = pgr_U.getPage();

		//リストデータをセット
		setList(wDispSearchKey, 0, page);
		
		//Pagerに値をセット
		setPagerValue(1, total, page);		
	}


	/** 
	 * リストが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ResourceList_Click(ActionEvent e) throws Exception
	{
		//ViewStateから取得
	    String flag = this.getViewState().getString("BTFLAG");
	    //呼び出し元画面でセットされたパラメータの取得
		displayFlag(flag);
	    
		//現在の行をセット
		lst_ResourceList.setCurrentRow(lst_ResourceList.getActiveRow());
		
		//呼び出し元の画面へ渡すパラメータ作成
		ForwardParameters param = new ForwardParameters();

		//メニューリソース検索または機能リソースキー検索の場合
		if(wDispSearchKey.equals(MENURESOUCEKEY) || wDispSearchKey.equals(FUNCTIONRESOUCEKEY))
		{
			param.setParameter(RESOURCE_KEY, lst_ResourceList.getValue(2));
			param.setParameter(RESOURCENAME_KEY, lst_ResourceList.getValue(3));
		    
		}
		//ボタンリソース検索の場合
		else if(wDispSearchKey.equals(BUTTONRESOUCEKEY))
		{
			param.setParameter(BUTTONRESOURCE_KEY, lst_ResourceList.getValue(2));
			param.setParameter(BUTTONNAME_KEY, lst_ResourceList.getValue(3));		    
		}
		//ページ名リソース検索の場合
		else if(wDispSearchKey.equals(PAGENAMERESOUCEKEY))
		{
			param.setParameter(PAGENAMERESOURCE_KEY, lst_ResourceList.getValue(2));
			param.setParameter(PAGENAME_KEY, lst_ResourceList.getValue(3));		    
		}
		
		    
		
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

	/** 
	 * 呼び出し元画面を判別します
	 * @param handle ScrollHandler
	 * @param next_index int
	 * @param next_end int 
	 * @throws Exception 
	 */
	private void displayFlag(String flag) throws Exception
	{
		//呼び出し元画面でセットされたパラメータの取得
		String btnflg = flag;	
		//親画面を判別
		if(btnflg.equals("btn_P_MenuResouceKey"))
		{
		    //メニューリソースキー検索
		    wDispSearchKey = MENURESOUCEKEY;    
		}
		else if(btnflg.equals("btn_P_FunctionResouceKey"))
		{
		    //機能リソースキー検索
		    wDispSearchKey = FUNCTIONRESOUCEKEY;    
		}    
		else if(btnflg.equals("btn_P_ButtonResouceKey"))
		{
		    //ボタンリソースキー検索
		    wDispSearchKey = BUTTONRESOUCEKEY;    
		}    
		else if(btnflg.equals("btn_P_PageResouceKey"))
		{
		    //ページ名リソースキー検索
		    wDispSearchKey = PAGENAMERESOUCEKEY;    
		}    
	}

}
//end of class
