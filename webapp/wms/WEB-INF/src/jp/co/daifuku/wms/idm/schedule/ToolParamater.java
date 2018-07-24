package jp.co.daifuku.wms.idm.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
/**
 * <FONT COLOR="BLUE">
 * Designer : Y.Kawai <BR>
 * Maker : Y.Kawai <BR>
 * <BR>
 * <CODE>ToolParamater</CODE>クラスは<CODE>IdmControlParameter</CODE>を継承し、<BR>
 * 移動ラック初期データ設定の画面～スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは移動ラック初期データ設定で使用される項目を保持します。<BR>
 * <BR>
 * <DIR>
 * <CODE>ToolParamater</CODE>クラスが保持する項目<BR>
 * <BR>
 *     空棚検索方法 <BR>
 * <BR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/13</TD><TD>Y.Kawai</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class ToolParamater extends IdmControlParameter
{
	
	/**
	 * 空棚検索優先順位(バンク)
	 */
	public static final String BANKAISLE_FLAG_BANK = "0";

	/**
	 * 空棚検索優先順位(アイル)
	 */
	public static final String BANKAISLE_FLAG_AISLE = "1";

	/**
	 * 空棚検索方法
	 */   
	private String wBankAisleFlag;
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:10 $" );
	}

	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public ToolParamater()
	{
	}

	// Public methods ------------------------------------------------
	
	/**
	 * 空棚検索方法をセットします。
	 * @param arg セットする空棚検索方法
	 */
	public void setBankAisleFlag(String arg)
	{
		wBankAisleFlag = arg;
	}

	/**
	 * 空棚検索方法を取得します。
	 * @return 空棚検索方法
	 */
	public String getBankAisleFlag()
	{
		return wBankAisleFlag;
	}

}