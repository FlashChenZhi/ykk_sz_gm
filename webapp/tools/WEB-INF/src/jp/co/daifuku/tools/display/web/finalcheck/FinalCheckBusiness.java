// $Id: FinalCheckBusiness.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.finalcheck;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.dbhandler.ResultMap;
import jp.co.daifuku.dbhandler.TableColumns;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.tools.util.DispResourceMap;
import jp.co.daifuku.tools.util.ToolValidator;
import jp.co.daifuku.ui.web.BusinessClassHelper;


/** <jp>
 * 整合性確認の画面クラスです。
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
public class FinalCheckBusiness extends FinalCheck implements ToolConstants, TableColumns
{
	// Class fields --------------------------------------------------
    /**
     * エラー内容を保持するリスト
     */ 
    private List wErrorList = null;

    //Table Name
    private final String T_MAINMENU = "MAINMENU";
    private final String T_FUNCTION = "FUNCTION";
    private final String T_FUNCTIONMAP = "FUNCTIONMAP";
    private final String T_ROLE = "ROLE";
    private final String T_ROLEMAP = "ROLEMAP";
    private final String T_LOGINUSER = "LOGINUSER";
    private final String T_TERMINAL = "TERMINAL";
    private final String T_USERATTRIBUTE = "USERATTRIBUTE";
    private final String T_AUTHENTICATIONSYSTEM = "AUTHENTICATIONSYSTEM";
 
    
    
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
	    lbl_SettingName.setResourceKey("TLE-T0019");
		//ヘルプファイルへのパスをセットする
	    btn_Help.setUrl(BusinessClassHelper.getHelpPath("FinalCheck", this.getHttpRequest()) );
	}
    
	/** 
	 * 画面の初期化を行います。
	 * @param e ActionEvent
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
	    //プロダクトタイプをViewStateに保存
	    this.getViewState().setString("PRODUCTTYPE",this.request.getParameter(PRODUCTTYPE));
	    //メニュータイプをViewStateに保存
	    this.getViewState().setString("MENUTYPE",this.request.getParameter(MENUTYPE));
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/** 
	 * リストセルに値をセットします
	 */
	private void setList() 
	{
		for(int i=0; i<wErrorList.size(); i++)
		{
			//最終行を取得
			int count = lst_FinalCheck.getMaxRows();
			//行追加
			lst_FinalCheck.setCurrentRow(count);
			lst_FinalCheck.addRow();
			
			//No.
			lst_FinalCheck.setValue(1, Integer.toString(count));
			ErrorItem item = (ErrorItem)wErrorList.get(i);
			//Level
			lst_FinalCheck.setValue(2, item.getLevel());
			//TableName
			lst_FinalCheck.setValue(3, item.getTableName());
			//Message
			lst_FinalCheck.setValue(4, item.getMessage());
		}

	}

	
	/** 
	 * リソースキーがリソースファイルに存在するか確認 
	 * @param dataList  List 確認を行うテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @param mainColName エラー発見時にメッセージへ記録する主キーの列名
	 * @param resColName リソースキーを定義している列名
	 * @return リソースキーがファイルに存在する場合はtrueを返します
	 * @throws Exception
	 */
	private boolean isDefinedResourceKey(List dataList, String tableName, String mainColName, String resColName) throws Exception
	{	
		//Error flag.
		boolean isOK = true;
	    
	    //登録済みのリソースキーがある場合
		if(dataList != null && dataList.size() != 0)
		{	
		    for(int i=0; i<dataList.size(); i++)
		    {   
		        //リストデータから値を取得
		        ResultMap map = (ResultMap)dataList.get(i);
	            //メニューリソースキーを取得
	            String resourcekey = map.getString(resColName);
	            String mainID = map.getString(mainColName);
		        //取得したリソースキーでリソースファイルを検索
		        int cnt_resource = DispResourceMap.getKeyList(resourcekey).size();    
		        //リソースキーが見つからなかった場合
		        if(cnt_resource == 0)
		        {
		        	//エラー内容をセット
		        	ErrorItem errorItem = new ErrorItem();
		            errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
		            errorItem.setTableName(tableName);
		            String status1 = "Key:" + resourcekey;
		            String status2 = mainColName + "=" + mainID;
		            //{0}はリソースファイルに存在していません。({1})
		            String text = "ERR-0001" + Message.MSG_DELIM + status1 + Message.MSG_DELIM + status2;
		            errorItem.setMessage( DispResources.getText(text) );
		            //リストに追加
		            wErrorList.add(errorItem);
		            isOK = false;
		        }
		    }
		}	
		return isOK;
	}

	
	/** 
	 * 数値の重複確認を行います。数値が-1の場合は重複を認めます。
	 * @param dataList  List 確認を行うテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @param mainColName エラー発見時にメッセージへ記録する主キーの列名
	 * @param targetColName 重複を確認する列名
	 * @return 重複が無い場合にtrueを返します
	 */
	private boolean isNoDuplicateNumber(List dataList, String tableName, String targetColName)
	{
		//Error flag.
		boolean isOK = true;

		List tempList = new ArrayList();
		List duplicateNoList = new ArrayList();
		for(int i=0; i<dataList.size(); i++)
	    {   
	        //リストデータから値を取得
	        ResultMap map = (ResultMap)dataList.get(i);
            //重複を確認する数値を取得
            int number = map.getInt(targetColName);
            Integer val = new Integer(number);
			if(tempList.contains(val) && val.intValue() != -1)
			{
				if(!duplicateNoList.contains(val)) duplicateNoList.add(val);
			}
			else
			{
				tempList.add(val);
			}
	    }
		
		if(duplicateNoList.size() != 0)
		{
			Iterator itr = duplicateNoList.iterator();
			while(itr.hasNext())
			{
				Integer no = (Integer)itr.next();
				
				//エラー内容をセット
	        	ErrorItem errorItem = new ErrorItem();
	            errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
	            errorItem.setTableName(tableName);
	            //{0}が重複してます。
	            String text = "ERR-0002" + Message.MSG_DELIM + targetColName;
	            errorItem.setMessage( DispResources.getText(text) );
	            //リストに追加
	            wErrorList.add(errorItem);	
	            isOK = false;
			}
		}
		return isOK;
	}

	/** 
	 * 数値の重複確認を行います。数値が-1の場合は重複を認めます。
	 * subTargetColNameを指定することで、subTargetColName列の値が
	 * 同一の中での重複チェックを行います。
	 * @param dataList  List 確認を行うテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @param mainColName エラー発見時にメッセージへ記録する主キーの列名
	 * @param targetColName 重複を確認する列名
	 * @param subTargetColName 重複を確認する際のキーとなる列名
	 * @return 重複が無い場合にtrueを返します
	 */
	private boolean isNoDuplicateNumber(List dataList, String tableName, String targetColName, String subTargetColName)
	{
		//Error flag.
		boolean isOK = true;
		
		List subKeyList = getStringList(dataList, subTargetColName, false);
		
		for(int i=0; i<subKeyList.size(); i++)
	    {   
			String subKey = (String)subKeyList.get(i);
			//subKeyを含むデータのリスト
			List subKeyDataList = getDataList(dataList, subTargetColName, subKey);
			List tempList = new ArrayList();
			List duplicateNoList = new ArrayList();
			for(int j=0; j < subKeyDataList.size(); j++)
			{
		        //リストデータから値を取得
		        ResultMap map = (ResultMap)subKeyDataList.get(j);

		        //重複を確認する数値を取得
	            int number = map.getInt(targetColName);

	            Integer val = new Integer(number);
				if(tempList.contains(val) && val.intValue() != -1)
				{
					if(!duplicateNoList.contains(val)) duplicateNoList.add(val);
				}
				else
				{
					tempList.add(val);
				}
			}
			
			if(duplicateNoList.size() != 0)
			{
				Iterator itr = duplicateNoList.iterator();
				while(itr.hasNext())
				{
					Integer no = (Integer)itr.next();
					//エラー内容をセット
		        	ErrorItem errorItem = new ErrorItem();
		            errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
		            errorItem.setTableName(tableName);
		            String status = subTargetColName + "=" + subKey;
		            //{0}が重複してます。({1})
		            String text = "ERR-0007" + Message.MSG_DELIM + targetColName + Message.MSG_DELIM + status;
		            errorItem.setMessage( DispResources.getText(text) );
		            //リストに追加
		            wErrorList.add(errorItem);	
		            isOK = false;
				}
			}
	    }
		return isOK;
	}
	/** 
	 * 数値が指定された範囲にあるかを確認します。
	 * 
	 * @param dataList  List 確認を行うテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @param mainColName エラー発見時にメッセージへ記録する主キーの列名
	 * @param targetColName 範囲確認をする列名
	 * @param range 範囲を指定します。"0-2"と指定すると、0,1,2以外の時はfalseを返します。
	 * @return 指定された範囲にある場合はtrueを返します。
	 */
	private boolean isRangeNumber(List dataList, String tableName, String mainColName, String targetColName, String range) 
	{
		//Error flag.
		boolean isOK = true;
		for(int i=0; i<dataList.size(); i++)
	    {   
	        //リストデータから値を取得
	        ResultMap map = (ResultMap)dataList.get(i);
            //数値確認する値を取得
            int value = map.getInt(targetColName);
            String mainID = map.getString(mainColName);
            if(!ToolValidator.isString(new Integer(value), getRangeFormat(range)))
            {
				//エラー内容をセット
	        	ErrorItem errorItem = new ErrorItem();
	            errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
	            errorItem.setTableName(tableName);
	            String status1 = targetColName + ":" + value;
	            String status2 = range;
	            String status3 = "";
				if(mainColName == null || mainColName.equals(""))
				{
					status3 = "N/A";
				}
				else
				{
					status3 = mainColName + "=" + mainID;	
				}	
	            //{0}は[{1}]の範囲外にあります。({2})
	            String text = "ERR-0006" + Message.MSG_DELIM + status1
								+ Message.MSG_DELIM + status2
								+ Message.MSG_DELIM + status3;
	            errorItem.setMessage( DispResources.getText(text) );
	            //リストに追加
	            wErrorList.add(errorItem);	
	            isOK = false;	
            }
	    }
		return isOK;
	}
	
	
	/** 
	 * 数値かどうかの確認を行います。メニューIDはデータベース上、文字列として定義されますが、数値（0-9）のみを
	 * 定義可能とするために確認を行います。
	 * @param dataList  List 確認を行うテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @param mainColName エラー発見時にメッセージへ記録する主キーの列名
	 * @param targetColName 数値確認をする列名
	 * @return 数値の場合にtrueを返します
	 */
	private boolean isNumber(List dataList, String tableName, String mainColName, String targetColName) 
	{
		//Error flag.
		boolean isOK = true;

		for(int i=0; i<dataList.size(); i++)
	    {   
	        //リストデータから値を取得
	        ResultMap map = (ResultMap)dataList.get(i);
            //数値確認する値を取得
            String value = map.getString(targetColName);
            String mainID = map.getString(mainColName);
             if(!ToolValidator.isString(value, "0123456789"))
            {
				//エラー内容をセット
	        	ErrorItem errorItem = new ErrorItem();
	            errorItem.setLevel(DispResources.getText("T_WARN_TEXT"));
	            errorItem.setTableName(tableName);
	            String status1 = targetColName + ":" + value;
	            String status2 = mainColName + "=" + mainID;
	            //{0}に数値以外の文字が入っています。
	            String text = "ERR-0004" + Message.MSG_DELIM + status1
								+ Message.MSG_DELIM + status2;
	            errorItem.setMessage( DispResources.getText(text) );
	            //リストに追加
	            wErrorList.add(errorItem);	
	            isOK = false;	
            }
	    }
		return isOK;
	}

	/** 
	 * 対象データが指定した値minNoよりも大きな値かどうかを確認します。
	 * minNo以上の場合、trueを返します。
	 * @param dataList  List 確認を行うテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @param mainColName エラー発見時にメッセージへ記録する主キーの列名
	 * @param targetColName 数値確認をする列名
	 * @param minNo 最小値を設定します
	 * @return minNo以上の場合にtrueを返します
	 */
	private boolean isBigNumber(List dataList, String tableName, String mainColName, String targetColName, int minNo) 
	{
		//Error flag.
		boolean isOK = true;

		for(int i=0; i<dataList.size(); i++)
	    {   
	        //リストデータから値を取得
	        ResultMap map = (ResultMap)dataList.get(i);
            //数値確認する値を取得
           int value = map.getInt(targetColName);
            String mainID = map.getString(mainColName);
            
            if(value < minNo)
            {
    			//エラー内容をセット
            	ErrorItem errorItem = new ErrorItem();
                errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
                errorItem.setTableName(tableName);
                String status1 = targetColName + ":" + value;
                String status2 = Integer.toString(minNo);
                String status3 = "";
                if(mainColName == null || mainColName.equals(""))
                {
               		status3 = "N/A";
                }
                else
                {
                	status3 = mainColName + "=" + mainID;	
                }
                //{0}は{1}以上ではありません。({2})
                String text = "ERR-0009" + Message.MSG_DELIM + status1
    							+ Message.MSG_DELIM + status2
    							+ Message.MSG_DELIM + status3;
                errorItem.setMessage( DispResources.getText(text) );
                //リストに追加
                wErrorList.add(errorItem);	
                isOK = false;	
            }
	    }
        return isOK;
	}	
	
	/** 
	 * 対象データが禁止指定した値Noかどうかを確認します。
	 * No以外の場合、trueを返します。
	 * @param dataList  List 確認を行うテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @param mainColName エラー発見時にメッセージへ記録する主キーの列名
	 * @param targetColName 数値確認をする列名
	 * @param No 禁止値を設定します
	 * @return No以外の場合にtrueを返します
	 */
	private boolean isProhibitionNumber(List dataList, String tableName, String mainColName, String targetColName, int No) 
	{
		//Error flag.
		boolean isOK = true;

		for(int i=0; i<dataList.size(); i++)
	    {   
	        //リストデータから値を取得
	        ResultMap map = (ResultMap)dataList.get(i);
            //数値確認する値を取得
           int value = map.getInt(targetColName);
            String mainID = map.getString(mainColName);
            
            if(value == No)
            {
    			//エラー内容をセット
            	ErrorItem errorItem = new ErrorItem();
                errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
                errorItem.setTableName(tableName);
                String status1 = targetColName;
                String status2 = Integer.toString(No);
                String status3 = "";
                if(mainColName == null || mainColName.equals(""))
                {
               		status3 = "N/A";
                }
                else
                {
                	status3 = mainColName + "=" + mainID;	
                }
                //{0}には{1}を設定しないでください。({2})
                String text = "ERR-0014" + Message.MSG_DELIM + status1
    							+ Message.MSG_DELIM + status2
    							+ Message.MSG_DELIM + status3;
                errorItem.setMessage( DispResources.getText(text) );
                //リストに追加
                wErrorList.add(errorItem);	
                isOK = false;	
            }
	    }
        return isOK;
	}	

	/** 
	 * 対象のデータが１から始まる連番かどうかを確認します。
	 * データが重複している場合、連番ではないと判断しfalseを返します。
	 * @param dataList  List 確認を行うテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @param targetColName 連番確認をする列名
	 * @return 連番の場合にtrueを返します
	 * @throws Exception 
	 */
	private boolean isSerialNumber(List dataList, String tableName, String targetColName) 
	{
		//Error flag.
		boolean isOK = true;
		List ret = new ArrayList();
		
		for(int i=0; i<dataList.size(); i++)
	    {   
	        //リストデータから値を取得
	        ResultMap map = (ResultMap)dataList.get(i);
            //連番確認する値を取得
            int value = map.getInt(targetColName);
            Integer val = new Integer(value);
			if(val.intValue() != -1)
			{
				ret.add(val);
			}
	    }
		if(ret.size() == 0) return true;

		//Sort the list
		Collections.sort(ret);
		//This number should start with 1.
		if( ((Integer)ret.get(0)).intValue() != 1)
		{
			//エラー内容をセット
	    	ErrorItem errorItem = new ErrorItem();
	        errorItem.setLevel(DispResources.getText("T_WARN_TEXT"));
	        errorItem.setTableName(tableName);
	        String status = targetColName ;
	        //{0}は1から始まる連番である必要があります。
	        String text = "ERR-0005" + Message.MSG_DELIM + status;
	        errorItem.setMessage( DispResources.getText(text) );
	        //リストに追加
	        wErrorList.add(errorItem);	
	        return false;	
		}
		
		
		Iterator itr = ret.iterator();
		int lastNo = 0;
		while(itr.hasNext())
		{
			int value = ((Integer)itr.next()).intValue();
			if(lastNo != value && value== lastNo+1)
			{
				lastNo = value;
			}
			else
			{
				//エラー内容をセット
		    	ErrorItem errorItem = new ErrorItem();
		        errorItem.setLevel(DispResources.getText("T_WARN_TEXT"));
		        errorItem.setTableName(tableName);
		        String status = targetColName ;
		        //{0}は1から始まる連番である必要があります。
		        String text = "ERR-0005" + Message.MSG_DELIM + status;
		        errorItem.setMessage( DispResources.getText(text) );
		        //リストに追加
		        wErrorList.add(errorItem);	
		        isOK = false;	
				break;
			}
		}
		return isOK;
	}

	/** 
	 * 対象のデータが１から始まる連番かどうかを確認します。
	 * データが重複している場合、連番ではないと判断しfalseを返します。
	 * subTargetColNameを指定することで、subTargetColName列の値が
	 * 同一の中での連番チェックを行います。
	 * @param dataList  List 確認を行うテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @param targetColName 連番確認をする列名
	 * @param subTargetColName 連番を確認する際のキーとなる列名	 
	 * @return 連番の場合にtrueを返します
	 */
	private boolean isSerialNumber(List dataList, String tableName, String targetColName, String subTargetColName)
	{
		//Error flag.
		boolean isOK = true;
		List subKeyList = getStringList(dataList, subTargetColName, false);
		for(int i=0; i<subKeyList.size(); i++)
	    {   
			String subKey = (String)subKeyList.get(i);
			//subKeyを含むデータのリスト
			List subKeyDataList = getDataList(dataList, subTargetColName, subKey);
			List ret = new ArrayList();
			for(int j=0; j < subKeyDataList.size(); j++)
			{
		        //リストデータから値を取得
		        ResultMap map = (ResultMap)subKeyDataList.get(j);

		        //連番確認する値を取得
	            int value = map.getInt(targetColName);
	            Integer val = new Integer(value);
				if(val.intValue() != -1)
				{
					ret.add(val);
				}
			}

			if(ret.size() == 0) return true;
			//Sort the list
			Collections.sort(ret);
			//This number should start with 1.
			if( ((Integer)ret.get(0)).intValue() != 1)
			{
				//エラー内容をセット
		    	ErrorItem errorItem = new ErrorItem();
		        errorItem.setLevel(DispResources.getText("T_WARN_TEXT"));
		        errorItem.setTableName(tableName);

	            String status = subTargetColName + "=" + subKey;
	            //{0}が連番ではありません。({1})
	            String text = "ERR-0008" + Message.MSG_DELIM + targetColName + Message.MSG_DELIM + status;
		        errorItem.setMessage( DispResources.getText(text) );
		        //リストに追加
		        wErrorList.add(errorItem);	
		        return false;	
			}			

			Iterator itr = ret.iterator();
			int lastNo = 0;
			while(itr.hasNext())
			{
				int value = ((Integer)itr.next()).intValue();
				if(lastNo != value && value== lastNo+1 )
				{
					lastNo = value;
				}
				else
				{
					//エラー内容をセット
			    	ErrorItem errorItem = new ErrorItem();
			        errorItem.setLevel(DispResources.getText("T_WARN_TEXT"));
			        errorItem.setTableName(tableName);

		            String status = subTargetColName + "=" + subKey;
		            //{0}が連番ではありません。({1})
		            String text = "ERR-0008" + Message.MSG_DELIM + targetColName + Message.MSG_DELIM + status;
			        errorItem.setMessage( DispResources.getText(text) );
			        //リストに追加
			        wErrorList.add(errorItem);	
			        isOK = false;	
			        break;
				}
			}
	    }
		return isOK;
	}

	/** 
	 * ２つのデータ間での存在確認を行います。
	 * datalistのtargetColNameで指定した項目が、targetListに存在するかを確認します。
	 * @param dataList  確認を行うテーブルのデータ
	 * @param targetList 存在確認を行う対象となるテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @param targetTableName  存在確認を行う対象となるテーブル名
	 * @param mainColName エラー発見時にメッセージへ記録する主キーの列名
	 * @param targetColName 存在確認する列名
	 * @return 存在する場合にtrueを返します
	 */
	private boolean isDefinedString(List dataList, List targetList, String tableName, String targetTableName, String mainColName, String targetColName) 
	{
		//Error flag.
		boolean isOK = true;
		for(int i=0; i<dataList.size(); i++)
	    {
	        //リストデータから値を取得
	        ResultMap map = (ResultMap)dataList.get(i);
            //重複を確認する数値を取得
            String value = map.getString(targetColName);
            if(!isDefinedValue(value, targetList, targetColName))
            {
            	String mainID = map.getString(mainColName);
            	//エラー内容をセット
	        	ErrorItem errorItem = new ErrorItem();
	            errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
	            errorItem.setTableName(tableName);
	            String status1 = targetColName + ":" + value;
	            String status2 = mainColName + "=" + mainID;
	            //{0}は{1}に存在していません。({2})
	            String text = "ERR-0003" + Message.MSG_DELIM + status1 + 
										   Message.MSG_DELIM + targetTableName + 
										   Message.MSG_DELIM + status2;
	            errorItem.setMessage( DispResources.getText(text) );
	            //リストに追加
	            wErrorList.add(errorItem);	
	            isOK = false;
            }
	    }
		return isOK;
	}
	
	/** 
	 * MAINMENU表のアイコン表示方法と表示順の整合性を確認します。
	 * ・SHOWTYPEが０，１の場合：MENUDISPNUMBERは１以上
	 * ・SHOWTYPEが２の場合：MENUDISPNUMBERは－１
	 * であることを確認します。
	 * @param dataList  MAINMENU表のデータ
	 * @return 整合性が確認されればtrueを返します
	 */
	private boolean isCorrectMainMenuShowType(List dataList)
	{
		//Error flag.
		boolean isOK = true;

		for(int i=0; i<dataList.size(); i++)
	    {   
			//リストデータから値を取得
			ResultMap map = (ResultMap)dataList.get(i);
			//数値確認する値を取得
			int showType = map.getInt(MAINMENU_SHOWTYPE);
			int dispNo = map.getInt(MAINMENU_MENUDISPNUMBER);
			String mainID = map.getString(MAINMENU_MENUID);
			if(showType == 2)
			{
				if(dispNo != -1)
				{
					//エラー内容をセット
			    	ErrorItem errorItem = new ErrorItem();
			        errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
			        errorItem.setTableName(T_MAINMENU);

		            String status = "MENUID=" + mainID;
		            //アイコン表示方法が[未使用]の場合、MENUDISPNUMBERは-1として下さい。({0})
		            String text = "ERR-0010" + Message.MSG_DELIM + status;
			        errorItem.setMessage( DispResources.getText(text) );
			        //リストに追加
			        wErrorList.add(errorItem);	
			        isOK = false;	
				}
			}
			else if(showType == 0 || showType == 1)
			{
				if(dispNo < 1)
				{
					//エラー内容をセット
			    	ErrorItem errorItem = new ErrorItem();
			        errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
			        errorItem.setTableName(T_MAINMENU);

		            String status = "MENUID=" + mainID;
		            //アイコン表示方法が[クリック可能/不可]の場合、MENUDISPNUMBERは1以上として下さい。({0})
		            String text = "ERR-0011" + Message.MSG_DELIM + status;
			        errorItem.setMessage( DispResources.getText(text) );
			        //リストに追加
			        wErrorList.add(errorItem);	
			        isOK = false;	
				}				
			}	
	    }		
		return true;
	}
	
	/** 
	 * LOGINUSER表のパスワード更新間隔とパスワード有効期限の整合性を確認します。
	 * ・パスワード更新間隔が-1の場合、パスワード有効期限がnull
	 * であることを確認します。
	 * @param dataList  LOGINUSER表のデータ
	 * @return 整合性が確認されればtrueを返します
	 */
	private boolean isCorrectLoginUserPwdExpires(List dataList)
	{
		//Error flag.
		boolean isOK = true;

		for(int i=0; i<dataList.size(); i++)
	    {   
			//リストデータから値を取得
			ResultMap map = (ResultMap)dataList.get(i);
			//数値確認する値を取得
			int pwdIval = map.getInt(LOGINUSER_PWDCHANGEINTERVAL);
			if(pwdIval == -1)
			{
				Date pwdExp = map.getDate(LOGINUSER_PWDEXPIRES);
				
				if(pwdExp != null)
				{
					String mainID = map.getString(LOGINUSER_USERID);
					
					//エラー内容をセット
			    	ErrorItem errorItem = new ErrorItem();
			        errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
			        errorItem.setTableName(T_LOGINUSER);

		            String status = "USERID=" + mainID;
		            //パスワード更新間隔が[-1]の場合、パスワード有効期限は[null]として下さい。({0})
		            String text = "ERR-0012" + Message.MSG_DELIM + status;
			        errorItem.setMessage( DispResources.getText(text) );
			        //リストに追加
			        wErrorList.add(errorItem);	
			        isOK = false;	
						
				}
			}
	    }
		return isOK;
	}

	/** 
	 * keyが登録されているかを確認します。
	 * @param dataList  確認を行うテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @param targetColName keyの存在確認を行う列名
	 * @param key 検索する文字列
	 * @return 登録する場合にtrueを返します
	 */
	private boolean isDefinedKey(List dataList, String tableName, String targetColName, String key)
	{
		//Error flag.
		boolean isOK = true;
		if(!isDefinedValue(key, dataList, targetColName))
		{
			//エラー内容をセット
	    	ErrorItem errorItem = new ErrorItem();
	        errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
	        errorItem.setTableName(tableName);

            //{0}が登録されていません。
            String text = "ERR-0013" + Message.MSG_DELIM + key;
	        errorItem.setMessage( DispResources.getText(text) );
	        //リストに追加
	        wErrorList.add(errorItem);
	        isOK = false;	
		}
		return true;
	}
	
	
	
	/** 
	 * テーブルデータ（dataList）のtargetColNameで定義される列にvalueが存在するかを確認します。
	 * @param value  
	 * @param dataList 確認を行うデータ
	 * @param targetColName  確認を行う列名
	 * @return 存在する場合にtrueを返します
	 */
	private boolean isDefinedValue(String value, List dataList, String targetColName)
	{
		for(int i=0; i<dataList.size(); i++)
	    {   
	        //リストデータから値を取得
	        ResultMap map = (ResultMap)dataList.get(i);
            //重複を確認する数値を取得
            String target = map.getString(targetColName);
            if(target.equals(value))
            {
            	return true;
            }
	    }
		return false;
	}
	
	/** 
	 * テーブルデータ（dataList）のcolNameで定義される列の値をListとして返します。
	 * isDupuricateがfalseの場合、ユニークな値のみのリストを返します。
	 * @param dataList 確認を行うデータ
	 * @param colName  確認を行う列名
	 * @param isDupuricate  重複した値もリストへ追加する場合はtrue。
	 * @return　値のリスト
	 */
	private List getStringList(List dataList, String colName, boolean isDupuricate)
	{
		List retList = new ArrayList();
		for(int i=0; i<dataList.size(); i++)
	    {   
	        //リストデータから値を取得
	        ResultMap map = (ResultMap)dataList.get(i);
            String value = map.getString(colName);
            
            if(isDupuricate)
            {
            	retList.add(value);
            }
            else
            {
            	if(!retList.contains(value))
            	{
            		retList.add(value);
            	}
            }
	    }
		return retList;
	}
	
	
	/** 
	 * テーブルデータ（dataList）のcolNameで定義される列の値がvalueのデータのみをListで返します。
	 * @param dataList 確認を行うデータ
	 * @param colName  確認を行う列名
	 * @param value 値
	 * @return　データのリスト
	 */
	private List getDataList(List dataList, String colName, String value)
	{
		List retList = new ArrayList();
		for(int i=0; i<dataList.size(); i++)
	    {   
	        //リストデータから値を取得
	        ResultMap map = (ResultMap)dataList.get(i);
            String str = map.getString(colName);
           	if(str.equals(value))
           	{
           		retList.add(map);
           	}
	    }
		return retList;
	}
	
	/** <jp>
	 * isRangeNumberメソッドで使用する範囲の引数を、isStringメソッドのinspectionsで使用できるフォーマット
	 * へ変換します。 "0-2"の場合"012"を返します。
	 *
	 * @param arg	 対象オブジェクト（例　"0-2"）
	 * @return フォーマットした文字列
	 </jp> */
	/** <en>
	 * isRangeNumberメソッドで使用する範囲の引数を、isStringメソッドで使用できるフォーマット
	 * へ変換します。 "0-2"の場合"012"を返します。
	 *
	 * @param arg	 対象オブジェクト（例　"0-2"）
	 * @return フォーマットした文字列
	</en> */
	private String getRangeFormat(String arg)
	{
		if(arg.indexOf("-") == -1)
		{
			return arg;
		}
		
		String[] list = arg.split("-");
		
		int start = Integer.parseInt(list[0]);
		int end = Integer.parseInt(list[1]);
		String ret = "";
		for(int i = start; i <= end; i++)
		{
			ret += i;
		}
		
		return ret;
	}
	
	
	/** 
	 * テーブルにデータが登録されているか確認を行います。 
	 * @param dataList  確認を行うテーブルのデータ
	 * @param tableName  エラー発見時にメッセージへ記録するテーブル名
	 * @return 存在する場合にtrueを返します
	 */
	private boolean isDefinedTableData(List dataList, String tableName) 
	{
		//Error flag.
		boolean isOK = true;
		if(dataList.size() == 0)
		{	
           	//エラー内容をセット
        	ErrorItem errorItem = new ErrorItem();
            errorItem.setLevel(DispResources.getText("T_ERROR_TEXT"));
            errorItem.setTableName(tableName);
            String status1 = tableName;
            //{0}にはデータが登録されていません。
            String text = "ERR-0015" + Message.MSG_DELIM + status1;
            errorItem.setMessage( DispResources.getText(text) );
            //リストに追加
            wErrorList.add(errorItem);	
            isOK = false;	    
		}
		return isOK;
	}

	
	
	// Event handler methods -----------------------------------------

	/** 
	 * メニューへボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
	    //メニュー画面へ遷移
	    forward("/menu/SubMenu.do?Product="+ this.getViewState().getString("PRODUCTTYPE")
	            						   + "&MenuType=" + this.getViewState().getString("MENUTYPE"));    
	}

	/** 
	 * 開始ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Click(ActionEvent e) throws Exception
	{
	    //リストをクリア
	    lst_FinalCheck.clearRow();
	    
	    Connection conn = null;			
		try
		{
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);			
			wErrorList = new ArrayList();
			BaseHandler handle = new BaseHandler();
			//MainMenu表の一覧を取得
			List mainmenuList = handle.find("_select-5915", null, conn);	
			//Function表の一覧を取得
			List functionList = handle.find("_select-5709", null, conn);	
			//FunctionMap表の一覧を取得
			List functionMapList = handle.find("_select-5810", null, conn);	
			//Role表の一覧を取得
			List roleList = handle.find("_select-5508", null, conn);	
			//RoleMap表の一覧を取得
			List roleMapList = handle.find("_select-5603", null, conn);	
			//LoginUser表の一覧を取得
			List loginUserList = handle.find("_select-5114", null, conn);	
			//UserAttribute表の一覧を取得
			List userAttributeList = handle.find("_select-5200", null, conn);	
			//Terminal表の一覧を取得
			List terminalList = handle.find("_select-5404", null, conn);	
			//AuthenticationSystem表の一覧を取得
			List authenticationSystemList = handle.find("_select-5300", null, conn);	
			
			//MAINMENU表にデータが登録されているか確認
			if(isDefinedTableData(mainmenuList, T_MAINMENU))
			{
				//MAINMENU表のメニューリソースキーがリソースファイルに存在するか確認  
				isDefinedResourceKey(mainmenuList, T_MAINMENU, MAINMENU_MENUID, MAINMENU_MENURESOURCEKEY);
				//MAINMENU表のメニュー表示順の重複確認
				isNoDuplicateNumber(mainmenuList, T_MAINMENU, MAINMENU_MENUDISPNUMBER);
				//MAINMENU表のメニュー表示順が連番かどうかを確認
				isSerialNumber(mainmenuList, T_MAINMENU, MAINMENU_MENUDISPNUMBER);
				//MAINMENU表のMENUIDが数値のみで構成されていることを確認
				isNumber(mainmenuList, T_MAINMENU, MAINMENU_MENUID, MAINMENU_MENUID);
				//MAINMENU表のアイコン表示方法が[0-2]の範囲にあることを確認
				isRangeNumber(mainmenuList, T_MAINMENU, MAINMENU_MENUID, MAINMENU_SHOWTYPE, "0-2");
				//MAINMENU表のアイコン表示方法、表示順の整合性確認
				isCorrectMainMenuShowType(mainmenuList);			    
			}
			
			//FUNCTION表にデータが登録されているか確認
			if(isDefinedTableData(functionList, T_FUNCTION))
			{
				//FUNCTION表のMENUIDがMAINMENU表に存在するかを確認
				isDefinedString(functionList, mainmenuList, T_FUNCTION, T_MAINMENU, FUNCTION_MAINFUNCTIONID, FUNCTION_MENUID);
				//FUNCTION表の機能リソースキーがリソースファイルに存在するか確認 
				isDefinedResourceKey(functionList, T_FUNCTION, FUNCTION_MAINFUNCTIONID, FUNCTION_FUNCTIONRESOURCEKEY);
				//FUNCTION表の機能表示順の重複確認
				isNoDuplicateNumber(functionList, T_FUNCTION, FUNCTION_FUNCTIONDISPNUMBER, FUNCTION_MENUID);
				//FUNCTION表の機能表示順が連番かどうかを確認
				isSerialNumber(functionList, T_FUNCTION, FUNCTION_FUNCTIONDISPNUMBER, FUNCTION_MENUID);
				//FUNCTION表の機能表示順が-1以上かどうかを確認
				isBigNumber(functionList, T_FUNCTION, FUNCTION_MAINFUNCTIONID, FUNCTION_FUNCTIONDISPNUMBER,-1);
				//FUNCTION表のMAINFUNCTIONIDが数値のみで構成されていることを確認
				isNumber(functionList, T_FUNCTION, FUNCTION_MAINFUNCTIONID, FUNCTION_MAINFUNCTIONID);			    
			}

			//FUNCTIONMAP表にデータが登録されているか確認  	
			if(isDefinedTableData(functionMapList, T_FUNCTIONMAP))
			{
				//FUNCTIONMAP表のMAINFUNCTIONIDがFUNCTION表に存在するかを確認
				isDefinedString(functionMapList, functionList, T_FUNCTIONMAP, T_FUNCTION, FUNCTIONMAP_FUNCTIONID, FUNCTIONMAP_MAINFUNCTIONID);
				//FUNCTIONMAP表の機能表示順の重複確認
				isNoDuplicateNumber(functionMapList, T_FUNCTIONMAP, FUNCTIONMAP_BUTTONDISPNUMBER, FUNCTIONMAP_MAINFUNCTIONID);
				//FUNCTIONMAP表のアイコン表示方法が[1-4]の範囲にあることを確認
				isRangeNumber(functionMapList, T_FUNCTIONMAP, FUNCTIONMAP_FUNCTIONID, FUNCTIONMAP_BUTTONDISPNUMBER, "1-4");
				//FUNCTIONMAP表のBUTTONRESOURCEKEYがリソースファイルに存在するか確認 
				isDefinedResourceKey(functionMapList, T_FUNCTIONMAP, FUNCTIONMAP_FUNCTIONID, FUNCTIONMAP_BUTTONRESOURCEKEY);
				//FUNCTIONMAP表のPAGENAMERESOURCEKEYがリソースファイルに存在するか確認 
				isDefinedResourceKey(functionMapList, T_FUNCTIONMAP, FUNCTIONMAP_FUNCTIONID, FUNCTIONMAP_PAGENAMERESOURCEKEY);			    
			}
			
			//ROLE表にデータが登録されているか確認
			if(isDefinedTableData(roleList, T_ROLE))
			{
				//ROLE表の認証ミス猶予回数が-1以上かどうかを確認
				isBigNumber(roleList, T_ROLE, ROLE_ROLEID, ROLE_FAILEDLOGINATTEMPTS,-1);			    
			}

			//ROLEMAP表にデータが登録されているか確認  
			if(isDefinedTableData(roleMapList, T_ROLEMAP))
			{
				//ROLEMAP表のROLEIDがROLE表に存在するかを確認
				isDefinedString(roleMapList, roleList, T_ROLEMAP, T_ROLE, ROLEMAP_ROLEID, ROLEMAP_ROLEID);
				//ROLEMAP表のFUNCTIONIDがFUNCTION表に存在するかを確認
				isDefinedString(roleMapList, functionMapList, T_ROLEMAP, T_FUNCTIONMAP, ROLEMAP_ROLEID, ROLEMAP_FUNCTIONID);			    
			}
			
			//LOGINUSER表にデータが登録されているか確認  
			if(isDefinedTableData(loginUserList, T_LOGINUSER))
			{
				//LOGINUSER表のROLEIDがROLE表に存在するかを確認
				isDefinedString(loginUserList, roleList, T_LOGINUSER, T_ROLE, LOGINUSER_USERID, LOGINUSER_ROLEID);
				//LOGINUSER表のUSERIDがUSERATTRIBUTE表に存在するかを確認
				isDefinedString(loginUserList, userAttributeList, T_LOGINUSER, T_USERATTRIBUTE, LOGINUSER_USERID, LOGINUSER_USERID);
				//LOGINUSER表のパスワード更新間隔が-1の場合、パスワード有効期限がnullであることを確認
				isCorrectLoginUserPwdExpires(loginUserList);
				//LOGINUSER表の認証ミス猶予回数が-1以上かどうかを確認
				isBigNumber(loginUserList, T_LOGINUSER, LOGINUSER_USERID, LOGINUSER_FAILEDLOGINATTEMPTS,-1);
				//LOGINUSER表の同一ユーザ可能数が1以上かどうかを確認
				isBigNumber(loginUserList, T_LOGINUSER, LOGINUSER_USERID, LOGINUSER_SAMEUSERLOGINMAX,1);
				//LOGINUSER表のパスワード更新間隔が-1以上かどうかを確認
				isBigNumber(loginUserList, T_LOGINUSER, LOGINUSER_USERID, LOGINUSER_PWDCHANGEINTERVAL,-1);
				//LOGINUSER表のUSERIDにANONYMOUS_USERが登録されているかを確認する
				isDefinedKey(loginUserList, T_LOGINUSER, LOGINUSER_USERID, "ANONYMOUS_USER");			    
			}

			//USERATTRIBUTE表にデータが登録されているか確認  
			if(isDefinedTableData(userAttributeList, T_USERATTRIBUTE))
			{
				//USERATTRIBUTE表のUSERIDがLOGINUSER表に存在するかを確認
				isDefinedString(userAttributeList, loginUserList, T_USERATTRIBUTE, T_LOGINUSER, USERATTRIBUTE_USERID, USERATTRIBUTE_USERID);
				//USERATTRIBUTE表の性別が[0-2]の範囲にあることを確認
				isRangeNumber(userAttributeList, T_USERATTRIBUTE, USERATTRIBUTE_USERID, USERATTRIBUTE_SEX, "0-2");
				//USERATTRIBUTE表のUSERIDにANONYMOUS_USERが登録されているかを確認する
				isDefinedKey(userAttributeList, T_USERATTRIBUTE, USERATTRIBUTE_USERID, "ANONYMOUS_USER");			    
			}
			
			//TERMINAL表にデータが登録されているか確認  
			if(isDefinedTableData(terminalList, T_TERMINAL))
			{
				//TERMINAL表のROLEIDがROLE表に存在するかを確認
				isDefinedString(terminalList, roleList, T_TERMINAL, T_ROLE, TERMINAL_TERMINALNUMBER, TERMINAL_ROLEID);
				//TERMINAL表のIPADRESSにUNDEFINED_TERMINALが登録されているかを確認する
				isDefinedKey(terminalList, T_TERMINAL, TERMINAL_IPADDRESS, "UNDEFINED_TERMINAL");			    
			}
		
			//AUTHENTICATIONSYSTEM表にデータが登録されているか確認 
			if(isDefinedTableData(authenticationSystemList, T_AUTHENTICATIONSYSTEM))
			{
				//AUTHENTICATIONSYSTEM表のログイン数が-1以上かどうかを確認
				isBigNumber(authenticationSystemList, T_AUTHENTICATIONSYSTEM, "", AUTHENTICATIONSYSTEM_LOGINMAX, -1);
				//AUTHENTICATIONSYSTEM表のログイン数が0以外かどうかを確認
				isProhibitionNumber(authenticationSystemList, T_AUTHENTICATIONSYSTEM, "", AUTHENTICATIONSYSTEM_LOGINMAX, 0);
				//AUTHENTICATIONSYSTEM表の認証ミス猶予回数が-1以上かどうかを確認
				isBigNumber(authenticationSystemList, T_AUTHENTICATIONSYSTEM, "", AUTHENTICATIONSYSTEM_FAILEDLOGINATTEMPTS, -1);
				//AUTHENTICATIONSYSTEM表の同一ユーザログインが[0-1]の範囲にあることを確認
				isRangeNumber(authenticationSystemList, T_AUTHENTICATIONSYSTEM, "", AUTHENTICATIONSYSTEM_SAMELOGINUSER, "0-1");
				//AUTHENTICATIONSYSTEM表の場所の制約チェックが[0-1]の範囲にあることを確認
				isRangeNumber(authenticationSystemList, T_AUTHENTICATIONSYSTEM, "", AUTHENTICATIONSYSTEM_RESTRICTIONSPLACE, "0-1");
				//AUTHENTICATIONSYSTEM表のメインメニューの種類が[0-1]の範囲にあることを確認
				isRangeNumber(authenticationSystemList, T_AUTHENTICATIONSYSTEM, "", AUTHENTICATIONSYSTEM_MAINMENUTYPE, "0-1");
				//AUTHENTICATIONSYSTEM表の認証ミスカウント保持期間が-1以上かどうかを確認
				isBigNumber(authenticationSystemList, T_AUTHENTICATIONSYSTEM, "", AUTHENTICATIONSYSTEM_FAILEDTIME, -1);			    
			}
			
			//Set the listdata.
			setList();
			
			//エラーがない場合
			if(wErrorList.size() == 0)
			{
				//エラーはありませんでした。
				message.setMsgResourceKey("6401004"); 
		    }
			else
			{
				//エラーがあります。確認して下さい。
				message.setMsgResourceKey("6403017"); 
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

	/** 
	 * クリアボタンの処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
	    //リストをクリア
	    lst_FinalCheck.clearRow();
	}

	/** 
	 * エラー内容をセット・取得するためのクラス 
	 */
	class ErrorItem
	{
	    //エラー区分
	    private String wLevel = null;
	    //エラー箇所
	    private String wTableName = null;
	    //エラー内容
	    private String wMessage = null;
	    
	    /**
	     * エラー区分をセットします。
	     * @param level エラー区分
	     */
	    public void setLevel(String level)
	    {
	        wLevel = level;
	    }
	    
	    /**
	     * エラー箇所をセットします。
	     * @param tablename エラー箇所
	     */
	    public void setTableName(String tablename)
	    {
	        wTableName = tablename;
	    }

	    /**
	     * エラー内容をセットします。
	     * @param message エラー内容
	     */
	    public void setMessage(String message)
	    {
	        wMessage = message;
	    }
	    
	    /**
	     * エラー区分を取得します。
	     * @return エラー区分
	     */
	    public String getLevel()
	    {
	        return wLevel;
	    }
	    
	    /**
	     * エラー箇所を取得します。
	     * @return エラー箇所
	     */
	    public String getTableName()
	    {
	        return wTableName;
	    }
	    
	    /**
	     * エラー内容を取得します。
	     * @return エラー内容
	     */
	    public String getMessage()
	    {
	        return wMessage;
	    }
	}

	
}
//end of class
