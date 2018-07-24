// $Id: FunctionMap1Business.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.functionmap;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.dbhandler.ResultMap;
import jp.co.daifuku.dbhandler.ScrollHandler;
import jp.co.daifuku.dbhandler.TableColumns;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.tools.util.DispResourceMap;
import jp.co.daifuku.tools.util.ToolPulldownHandler;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.ui.web.ToolTipHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.PulldownHandler;

/** <jp>
 * サブメニューボタン設定1画面目の画面クラスです。
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
public class FunctionMap1Business extends FunctionMap1 implements ToolConstants, TableColumns
{
	// Class fields --------------------------------------------------
    /**
     * SCROLLHANDLERをセッションに保持するキー
     */ 
    private final String KEY_SCROLLHANDLER = "KEY_SCROLLHANDLER";
    
    /**
     * メニューID・メイン機能IDが紐付いていない項目を表示させるプルダウンアイテムの値
     */ 
    private final String NOTBELONG = "9999";
    
    
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

    /** 
	 * 各コントロールイベント呼び出し前に呼び出されます。
	 * @param e ActionEvent
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//画面名称をセットする
	    lbl_SettingName.setResourceKey("TLE-T0008");
	    //ヘルプファイルへのパスをセットする
	    btn_Help.setUrl(BusinessClassHelper.getHelpPath("FunctionMap", this.getHttpRequest()) );

	    setFocus(pul_MainMenu);
	    lbl_FunctionMapInfoMsg2.setVisible(false);
	}

	/**
	 * 画面の初期化を行います。
	 * @param e ActionEvent
	 */
	public void page_Load(ActionEvent e) throws Exception
	{   
	    
	    //メニューから遷移
	   if(this.request.getParameter(PRODUCTTYPE) != null)
	   {
		    //プロダクトタイプをViewStateに保存
		    this.getViewState().setString("PRODUCTTYPE",this.request.getParameter(PRODUCTTYPE));
		    //メニュータイプをViewStateに保存
		    this.getViewState().setString("MENUTYPE",this.request.getParameter(MENUTYPE));	       
	   }
	   //２画面目から遷移
	   else
	   {
		    //プロダクトタイプをViewStateに保存
		    this.getViewState().setString("PRODUCTTYPE", this.getViewState().getString("ProductType2"));
		    //メニュータイプをViewStateに保存
		    this.getViewState().setString("MENUTYPE", this.getViewState().getString("MenuType2"));	       	       
	   }

	    
	    Connection conn =null;		
		try
		{		
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//プルダウンデータの設定
			ToolPulldownHandler pull = new ToolPulldownHandler();
			PulldownHandler.PulldownParam param = pull.new PulldownParam();

			//初期表示するデータのindexを指定
			param.setSelectedIndex(0);
					
			//メインメニューのアイコン表示方法をセットする
			String notuse = Integer.toString(ToolConstants.SHOWTYPE_NOTUSE);
			String[] showtype = new String[1];
			//未使用をセット
			showtype[0] = notuse; 
			param.setConditions(showtype);
			//プルダウンの検索に使用するSQLリソースを指定
			param.setSqlRes("_select-5000");	

			//メニューID・メイン機能IDが紐付いていない項目を表示させるプルダウンアイテム
			String item = NOTBELONG + "," + DispResources.getText("PUL-T0011") +","+ " ," + "0";
			param.setExtraItem(item);
			
			//全ての項目を表示しない場合falseをセット
			param.setIsAll(false);

			//プルダウンの表示に必要なデータをArrayListにセット
			ArrayList lisp =pull.getPulldown(param, conn);
			//プルダウンデータをプルダウンへセット
			PulldownHelper.setPullDown(pul_MainMenu, lisp );
			
			//ViewStateよりメニューIDを取得
			String menuid = this.getViewState().getString("MenuId");
			if(menuid == null )
			{
			    //初回起動時
			    pul_MainMenu.setSelectedIndex(0);
			}
			else
			{
			    //前回選択していたメニューを選択
			    pul_MainMenu.setSelectedIndex(Integer.parseInt(menuid));
			}
			
			//２画面目から遷移した場合、前回表示のリストを出す
			if(this.getViewState().getString("ActiveCol") != null)
			{
				//リスト表示処理
				btn_View_Click(e);   
			}
		    
		}
		finally
		{
			if(conn != null)
			{
				conn.close();
			}
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
	    	    
		//メインメニューのメニューIDを取得
		String[] menuid = new String[2];
		menuid[0] = pul_MainMenu.getSelectedValue();
	    
		List list = new ArrayList();
		//メニューID・メイン機能IDが紐付いていない項目
		String other = NOTBELONG; 
		boolean delete_flg = false;
		if(menuid[0].equals(other))
		{
		    delete_flg = true; 
			//データベースからリストデータを取得
			list = handle.find("_select-5807", null, next_index, next_end);		        
		}
		else
		{
		    //機能表示順が未使用
		    menuid[1] = Integer.toString(NOTDISPITEM); 
		    //データベースからリストデータを取得
			list = handle.find("_select-5801", menuid, next_index, next_end);
		}		

		//表を削除
		lst_FunctionMap.clearRow();
		
		//Tipで使用する銘板
		String title_FunctionId = DispResources.getText("LBL-T0017");
		
		//列用フラグ
		boolean colflg= true;	
		//データベースのデータ
		String dbdata = "";
		String dbdata_functionid = "";
		
		//リストの行数
		int count = 0;
		
		ToolTipHelper toolTip = new ToolTipHelper();
		//ToolTip格納用
		String[] tooltip_tmp = new String[4];
	    for(int a=0; a<4; a++)
		{
	        tooltip_tmp[a]= "";
		}
		//ToolTip銘板格納用
		String[] tooltiptitle_tmp = new String[4];
	    for(int c=0; c<4; c++)
		{
	        tooltiptitle_tmp[c]= "";
		}
		
	    //Listを初期化
		String[] functionid_list = new String[5];
	    for(int i=0; i<5; i++)
		{
		    functionid_list[i]= "";
		}
	    
		Iterator itr = list.iterator();
		while(itr.hasNext())
		{
			ResultMap map = (ResultMap)itr.next();
			count = lst_FunctionMap.getMaxRows();
						
			//1行目のデータ
			if(count == 1)
			{		
				//操作対象行を設定
				lst_FunctionMap.setCurrentRow(count); 
				//行追加
				lst_FunctionMap.addRow();
				
				 
			    int buttondispnumber = 0;
				if((String)map.get(FUNCTIONMAP_BUTTONDISPNUMBER) != null)
				{
				    //ボタン表示順を取得
				    buttondispnumber = Integer.parseInt((String)map.get(FUNCTIONMAP_BUTTONDISPNUMBER));				    
				}
			    
				String buttonresource = "";
				if((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY) != null)
				{
					 //ボタン名を取得
				    buttonresource = DispResourceMap.getText((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY));
				    //リソースファイルにボタン名が未登録の場合
				    if(buttonresource== null)
				    {
				        //リソースキーをセット
				        buttonresource = (String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY);
				    }
				}
			    
				String functionresource = "";
				if((String)map.get(FUNCTION_FUNCTIONRESOURCEKEY) != null)
				{
					//機能名を取得
				    functionresource = DispResourceMap.getText((String)map.get(FUNCTION_FUNCTIONRESOURCEKEY));
				    //リソースファイルに機能名が未登録の場合
				    if(functionresource == null)
				    {
				        //リソースキーをセット
				        functionresource = (String)map.get(FUNCTION_FUNCTIONRESOURCEKEY);
				    }				    
				}

				//表にデータをセット
				lst_FunctionMap.setValue(1, functionresource);
				if(buttondispnumber > 0)
				{
				    lst_FunctionMap.setValue(1+buttondispnumber, buttonresource);    
				}
				
				//メイン機能IDを格納
				functionid_list[0] = (String)map.get(FUNCTIONMAP_MAINFUNCTIONID);
				//FunctionMap表にメイン機能IDがない場合
				if(functionid_list[0] == null)
				{
				    //Function表のメイン機能IDを格納
				    functionid_list[0] = (String)map.get("FUNCTION_MAINFUNCTIONID");
				}
				
				//機能IDをチェック
				if((String)map.get(FUNCTIONMAP_FUNCTIONID) != null)
				{
					//文字列連結
				    tooltiptitle_tmp[buttondispnumber-1] = buttonresource + "    " + title_FunctionId; 
					//ToolTipを追加
					tooltip_tmp[buttondispnumber-1] = (String)map.get(FUNCTIONMAP_FUNCTIONID);
				    
					//機能IDを追加
					functionid_list[buttondispnumber] = (String)map.get(FUNCTIONMAP_FUNCTIONID);
					
					//データベースの機能IDを取得
					dbdata_functionid = (String)map.get(FUNCTIONMAP_FUNCTIONID);
					if(dbdata_functionid.length() == 5)
					{
						dbdata_functionid = dbdata_functionid.substring(0,3);					    
					}
					else if(dbdata_functionid.length() == 6)
					{
					    dbdata_functionid = dbdata_functionid.substring(0,4);
					}
					else
					{
						message.setMsgResourceKey("6004001");  					    
					    lbl_FunctionMapInfoMsg2.setVisible(false);
					    lst_FunctionMap.clearRow();
					    lst_FunctionMap.setVisible(false);
					    return;
					}

				}
				
				//データベースのメイン機能IDを取得
				dbdata = functionid_list[0];
				
			}
			//2行目からのデータ
			else
			{			    
			    //Functionにメイン機能IDがない場合
			    if(delete_flg == true)
			    {
						//データベースの機能IDを取得
						String nextdata2 = (String)map.get(FUNCTIONMAP_FUNCTIONID);
						if(nextdata2.length() == 5)
						{
						    nextdata2 = nextdata2.substring(0,3);    
						}
						else if(nextdata2.length() == 6)
						{
						    nextdata2 = nextdata2.substring(0,4);
						}
						else
						{
						    message.setMsgResourceKey("6004001");  
						    lbl_FunctionMapInfoMsg2.setVisible(false);
						    lst_FunctionMap.clearRow();
						    lst_FunctionMap.setVisible(false);
						    return;
						}						
						
						//同じ行にデータをセット
						if(nextdata2.equals(dbdata_functionid))
						{
							colflg = false;
							    
							int buttondispnumber = 0;
							if((String)map.get(FUNCTIONMAP_BUTTONDISPNUMBER) != null)
							{
							    //ボタン表示順を取得
							    buttondispnumber = Integer.parseInt((String)map.get(FUNCTIONMAP_BUTTONDISPNUMBER));				    
							}
							
							String buttonresource = "";
							if((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY) != null)
							{
								 //ボタン名を取得
							    buttonresource = DispResourceMap.getText((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY));
							    //リソースファイルにボタン名が未登録の場合
							    if(buttonresource== null)
							    {
							        //リソースキーをセット
							        buttonresource = (String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY);
							    }
							}
							    		    			    
							//表にデータをセット
							if(buttondispnumber > 0)
							{
							    lst_FunctionMap.setValue(1+buttondispnumber, buttonresource);    
							}
								
							//機能IDをチェック
							if((String)map.get(FUNCTIONMAP_FUNCTIONID) != null)
							{
								//文字列連結
							    tooltiptitle_tmp[buttondispnumber-1] = buttonresource + "    " + title_FunctionId;
								//ToolTipを追加
								tooltip_tmp[buttondispnumber-1] = (String)map.get(FUNCTIONMAP_FUNCTIONID);
									
							    //機能IDを追加
								functionid_list[buttondispnumber] = (String)map.get(FUNCTIONMAP_FUNCTIONID);
							}
						}
						//次の行にデータをセット
						else
						{				  
						    colflg = true;
						    
						    int buttondispnumber = 0;
							if((String)map.get(FUNCTIONMAP_BUTTONDISPNUMBER) != null)
							{
							    //ボタン表示順を取得
							    buttondispnumber = Integer.parseInt((String)map.get(FUNCTIONMAP_BUTTONDISPNUMBER));				    
							}
							    
							String buttonresource = "";
							if((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY) != null)
							{
								 //ボタン名を取得
							    buttonresource = DispResourceMap.getText((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY));
							    //リソースファイルにボタン名が未登録の場合
							    if(buttonresource== null)
							    {
							        //リソースキーをセット
							        buttonresource = (String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY);
							    }
							}
							    
						    //機能IDをセット
						    String functionid = CollectionUtils.getConnectedString(Arrays.asList(functionid_list));
						    lst_FunctionMap.setValue(0, functionid);
						    //FuntionIdリストを初期化
						    functionid_list = new String[5];
						    for(int i=0; i<5;i++)
							{
							    functionid_list[i] = "";
							}
							    
						    //ToolTipをセット
						    for(int b=0; b<4; b++)
						    {
						        if(!tooltip_tmp[b].equals(""))
						        {
							        //toolTipを追加
							        toolTip.add(tooltiptitle_tmp[b], tooltip_tmp[b]); 				            
						        }
						    }
							lst_FunctionMap.setToolTip(count-1, toolTip.getText());		    

							    
							//ToolTipを初期化
							toolTip = new ToolTipHelper();
							//ToolTip格納用
							tooltip_tmp = new String[4];
						    for(int a=0; a<4; a++)
							{
						        tooltip_tmp[a]= "";
							}
							//ToolTip銘板格納用
							tooltiptitle_tmp = new String[4];
						    for(int c=0; c<4; c++)
							{
						        tooltiptitle_tmp[c]= "";
							}		
								
							//操作対象行を設定
							lst_FunctionMap.setCurrentRow(count); 
							//行追加
							lst_FunctionMap.addRow();

							String functionresource = "";
							if((String)map.get(FUNCTION_FUNCTIONRESOURCEKEY) != null)
							{
							    //機能名を取得
							    functionresource = DispResourceMap.getText((String)map.get(FUNCTION_FUNCTIONRESOURCEKEY));
							    //リソースファイルに機能名が未登録の場合
							    if(functionresource == null)
							    {
							        //リソースキーをセット
							        functionresource = (String)map.get(FUNCTION_FUNCTIONRESOURCEKEY);
							    }					    
							}
								
							//表にデータをセット
							lst_FunctionMap.setValue(1, functionresource);
							//表にデータをセット
							if(buttondispnumber > 0)
							{
							    lst_FunctionMap.setValue(1+buttondispnumber, buttonresource);   
							}
										
							//メイン機能IDを格納
							functionid_list[0] = (String)map.get(FUNCTIONMAP_MAINFUNCTIONID);
								
							//機能IDをチェック
							if((String)map.get(FUNCTIONMAP_FUNCTIONID) != null)
							{
								//文字列連結
							    tooltiptitle_tmp[buttondispnumber-1] = buttonresource + "    " + title_FunctionId;
								//ToolTipを追加
							    tooltip_tmp[buttondispnumber-1] = (String)map.get(FUNCTIONMAP_FUNCTIONID);
								    
							    //機能IDを追加
								functionid_list[buttondispnumber] = (String)map.get(FUNCTIONMAP_FUNCTIONID);
							}
						}				
						dbdata_functionid = nextdata2;
			    }
			    //Functionにメイン機能IDがある場合
			    else
			    {
			    	String nextdata = "";
			    	if((String)map.get("FUNCTION_MAINFUNCTIONID") != null)
			    	{
						//データベースのメイン機能IDを取得
						nextdata = (String)map.get("FUNCTION_MAINFUNCTIONID");				    				    
			    	}
					
					//同じ行にデータをセット
			    	if(nextdata.equals(dbdata))
					{
					    colflg = false;
					    
					    int buttondispnumber = 0;
						if((String)map.get(FUNCTIONMAP_BUTTONDISPNUMBER) != null)
						{
						    //ボタン表示順を取得
						    buttondispnumber = Integer.parseInt((String)map.get(FUNCTIONMAP_BUTTONDISPNUMBER));				    
						}
						
						String buttonresource = "";
						if((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY) != null)
						{
							 //ボタン名を取得
						    buttonresource = DispResourceMap.getText((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY));
						    //リソースファイルにボタン名が未登録の場合
						    if(buttonresource== null)
						    {
						        //リソースキーをセット
						        buttonresource = (String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY);
						    }
						}
					    		    			    
						//表にデータをセット
						if(buttondispnumber > 0)
						{
						    lst_FunctionMap.setValue(1+buttondispnumber, buttonresource);    
						}
						
						//機能IDをチェック
						if((String)map.get(FUNCTIONMAP_FUNCTIONID) != null)
						{
							//文字列連結
						    tooltiptitle_tmp[buttondispnumber-1] = buttonresource + "    " + title_FunctionId;
							//ToolTipを追加
							tooltip_tmp[buttondispnumber-1] = (String)map.get(FUNCTIONMAP_FUNCTIONID);
							
						    //機能IDを追加
							functionid_list[buttondispnumber] = (String)map.get(FUNCTIONMAP_FUNCTIONID);
						}
					}
					//次の行にデータをセット
					else
					{				  
					    colflg = true;
					    
					    int buttondispnumber = 0;
						if((String)map.get(FUNCTIONMAP_BUTTONDISPNUMBER) != null)
						{
						    //ボタン表示順を取得
						    buttondispnumber = Integer.parseInt((String)map.get(FUNCTIONMAP_BUTTONDISPNUMBER));				    
						}
					    
						String buttonresource = "";
						if((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY) != null)
						{
							 //ボタン名を取得
						    buttonresource = DispResourceMap.getText((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY));
						    //リソースファイルにボタン名が未登録の場合
						    if(buttonresource== null)
						    {
						        //リソースキーをセット
						        buttonresource = (String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY);
						    }
						}
					    
					    //機能IDをセット
					    String functionid = CollectionUtils.getConnectedString(Arrays.asList(functionid_list));
					    lst_FunctionMap.setValue(0, functionid);
					    //FuntionIdリストを初期化
					    functionid_list = new String[5];
					    for(int i=0; i<5;i++)
						{
						    functionid_list[i] = "";
						}
					    
					    //ToolTipをセット
					    for(int b=0; b<4; b++)
					    {
					        if(!tooltip_tmp[b].equals(""))
					        {
						        //toolTipを追加
						        toolTip.add(tooltiptitle_tmp[b], tooltip_tmp[b]); 				            
					        }
					    }
						lst_FunctionMap.setToolTip(count-1, toolTip.getText());		    

					    
						//ToolTipを初期化
						toolTip = new ToolTipHelper();
						//ToolTip格納用
						tooltip_tmp = new String[4];
					    for(int a=0; a<4; a++)
						{
					        tooltip_tmp[a]= "";
						}
						//ToolTip銘板格納用
						tooltiptitle_tmp = new String[4];
					    for(int c=0; c<4; c++)
						{
					        tooltiptitle_tmp[c]= "";
						}
						
						
						//操作対象行を設定
						lst_FunctionMap.setCurrentRow(count); 
						//行追加
						lst_FunctionMap.addRow();

						String functionresource = "";
						if((String)map.get(FUNCTION_FUNCTIONRESOURCEKEY) != null)
						{
						    //機能名を取得
						    functionresource = DispResourceMap.getText((String)map.get(FUNCTION_FUNCTIONRESOURCEKEY));
						    //リソースファイルに機能名が未登録の場合
						    if(functionresource == null)
						    {
						        //リソースキーをセット
						        functionresource = (String)map.get(FUNCTION_FUNCTIONRESOURCEKEY);
						    }					    
						}
						
						//表にデータをセット
						lst_FunctionMap.setValue(1, functionresource);
						//表にデータをセット
						if(buttondispnumber > 0)
						{
						    lst_FunctionMap.setValue(1+buttondispnumber, buttonresource);   
						}
								
						//メイン機能IDを格納
						functionid_list[0] = (String)map.get(FUNCTIONMAP_MAINFUNCTIONID);
						//FunctionMap表にメイン機能IDがない場合
						if(functionid_list[0] == null)
						{
						    //Function表のメイン機能IDを格納
						    functionid_list[0] = (String)map.get("FUNCTION_MAINFUNCTIONID");
						}
												
						//機能IDをチェック
						if((String)map.get(FUNCTIONMAP_FUNCTIONID) != null)
						{
							//文字列連結
						    tooltiptitle_tmp[buttondispnumber-1] = buttonresource + "    " + title_FunctionId;
							//ToolTipを追加
						    tooltip_tmp[buttondispnumber-1] = (String)map.get(FUNCTIONMAP_FUNCTIONID);
						    
						    //機能IDを追加
							functionid_list[buttondispnumber] = (String)map.get(FUNCTIONMAP_FUNCTIONID);
						}
					}				
			    	dbdata = nextdata;

			    }
			}	
		}
		
		//最終行が複数列か判定
		//最終行が１列の場合
		if(colflg == true)
		{
		    //最終行のToolTipをセット
		    for(int b=0; b<4; b++)
		    {
		        if(!tooltip_tmp[b].equals(""))
		        {
			        //toolTipを追加
			        toolTip.add(tooltiptitle_tmp[b], tooltip_tmp[b]); 				            
		        }
		    }
			lst_FunctionMap.setToolTip(count, toolTip.getText());
		}
		//最終行が複数列の場合
		else
		{
		    //最終行のToolTipをセット
		    for(int b=0; b<4; b++)
		    {
		        if(!tooltip_tmp[b].equals(""))
		        {
			        //toolTipを追加
			        toolTip.add(tooltiptitle_tmp[b], tooltip_tmp[b]); 				            
		        }
		    }
			lst_FunctionMap.setToolTip(count-1, toolTip.getText());			    
		}
		
	    //最終行の機能IDをセット
	    String functionid = CollectionUtils.getConnectedString(Arrays.asList(functionid_list));
	    lst_FunctionMap.setValue(0, functionid);
	}

	
	// Event handler methods -----------------------------------------


	/** 
	 * メニューへボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
	    if(this.getViewState().getString("PRODUCTTYPE") != null)
	    {
		    //メニュー画面へ遷移
		    forward("/menu/SubMenu.do?Product="+ this.getViewState().getString("PRODUCTTYPE")
		            						   + "&MenuType=" + this.getViewState().getString("MENUTYPE"));	        
	    }
	    else
	    {
		    //メニュー画面へ遷移
		    forward("/menu/SubMenu.do?Product="+ this.getViewState().getString("ProductType2")
		            						   + "&MenuType=" + this.getViewState().getString("MenuType2"));	        
	    }
	}


	/** 
	 * 表示ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{   
	    Connection conn = null;			
		try
		{		    		    		    
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
			
			//メインメニューのメニューIDを取得
			String[] menuid = new String[2];
			menuid[0] = pul_MainMenu.getSelectedValue();
		
			int total = 0;
			//メニューID・メイン機能IDが紐付いていない項目
			String other = NOTBELONG; 
			//その他項目の場合
			if(menuid[0].equals(other))
			{	
				//データベースのデータ数合計
				total = baseHandle.count("_select-5808", null, conn);
			    
			}
			else
			{
			    //機能表示順が未使用
			    menuid[1] = Integer.toString(NOTDISPITEM); 
				//データベースのデータ数合計
				total = baseHandle.count("_select-5800", menuid, conn);
			}
			
			//最初のページの表示終了位置
			int end = 0;
			//データがある場合
			if(total > 0)
			{
				handle.open();
				end = total;

				//メッセージラベルを表示
			    lbl_FunctionMapInfoMsg2.setVisible(true);
			    //設定するボタンを選択してください
				lbl_FunctionMapInfoMsg2.setResourceKey("MSG-T0003");
				//リストを表示
				lst_FunctionMap.setVisible(true);
				//リストデータをセット
				setList(handle, 0, end);
				//セッションにHandlerの値をセット
				this.getSession().setAttribute(KEY_SCROLLHANDLER, handle);
			}
			//データがない場合
			else
			{
				//リストを隠す
			    lst_FunctionMap.clearRow();
				lst_FunctionMap.setVisible(false);

				if(menuid[0].equals(other))
				{
					//メッセージラベルを表示
				    lbl_FunctionMapInfoMsg2.setVisible(true);
					//対象データはありませんでした
					lbl_FunctionMapInfoMsg2.setResourceKey("MSG-9016"); 
				}
				else
				{
					//指定されたメインメニューのサブメニューは登録されていません。
					message.setMsgResourceKey("6403026"); 
				}
				
				//セッションにHandlerの値をセット
				this.getSession().setAttribute(KEY_SCROLLHANDLER, handle);			        
		    }
		
		}
		finally
		{

		}

	}


	/** 
	 * リストが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_FunctionMap_Click(ActionEvent e) throws Exception
	{	    
		//現在の行をセット
		lst_FunctionMap.setCurrentRow(lst_FunctionMap.getActiveRow());
		//列取得
		int activeCol = lst_FunctionMap.getActiveCol();
		
		String buf = lst_FunctionMap.getValue(0);
		//機能IDを取得
		String functionid = CollectionUtils.getString(activeCol-1, buf);
		//メイン機能IDを取得
		String mainfunctionid = CollectionUtils.getString(0, buf);
		
		//選択列をViewStateに保存
		this.getViewState().setString("ActiveCol",Integer.toString(activeCol));
			
		//メイン機能IDをViewStateに保存
		this.getViewState().setString("MainFunctionId",mainfunctionid);
		//機能IDをViewStateに保存
		this.getViewState().setString("FunctionId",functionid);
		//機能名をViewStateに保存
		this.getViewState().setString("FunctionName",lst_FunctionMap.getValue(1));
		
		//選択しているメニューIDをViewStateに保存
		this.getViewState().setString("MenuId",Integer.toString(pul_MainMenu.getSelectedIndex()));
		
		//2画面目に遷移
		forward("/functionmap/FunctionMap2.do");
		    
	}

}
//end of class
