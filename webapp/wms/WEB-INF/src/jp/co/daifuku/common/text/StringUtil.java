// $Id: StringUtil.java,v 1.1.1.1 2006/08/17 09:33:44 mori Exp $
package jp.co.daifuku.common.text ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Date;

import jp.co.daifuku.common.InvalidDefineException;
/**
 * 文字列の編集、判別を行うメソッドを追加してください。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/07/03</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:44 $
 * @author  $Author: mori $
 */
public class StringUtil extends Object
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:33:44 $") ;
	}
	
	/**
	 * 文字の長さ取得します。<BR>
	 * 全角文字もstr.length()では1文字と数えてしまうのでバイト数を返します。
	 * @param str 
	 * @return バイト数
	 */
	public static int getLength(String str)
	{
		byte[] b = str.getBytes();
		return b.length;
	}
	
	/**
	 * 指定された文字列が空白かどうかを確認します。<BR>
	 * ・nullの場合 true<BR>
	 * ・0バイトの文字列 true<BR>
	 * ・すべてスペースの文字列 true<BR>
	 * ・スペース以外の文字が１文字でも含まれる場合 false<BR>
	 * @param  str チェック対象文字列
	 * @return 確認の結果。ブランクの場合trueを返します。
	 */
	public static boolean isBlank(String str)
	{
		if(str == null)
		{
			return true;
		}

		if(str.trim().length() == 0)
		{
			return true;
		}
		return false;
	}

	
	/**
	 * 指定された文字列が数値に変換可能かどうかを確認します。
	 * @param  str チェック対象文字列
	 * @return 数値に変換可能な場合はtrueを返します。
	 */
	public static boolean isNumberFormat(String str)
	{
		try{
			Long.parseLong(str);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	/**
	 * 指定されたDateクラスのインスタンスがnullかどうかを確認します。<BR>
	 * nullの場合trueを返し、それ以外の場合はfalseを返します。
	 * @param  cdate チェック対象となるDateクラスの値
	 * @return 確認の結果。cdateがnullの場合はtrueを返します。それ以外の場合はfalseを返します。
	 */
	public static boolean isBlank(Date cdate)
	{
		if(cdate == null)
		{
			return true;
		}
		return false;
	}

	/**
	 * 指定された文字列を変換します<BR>
	 * ・nullの場合 =>　0バイトの文字列
	 * ・0バイトの文字列 => そのまま　<BR>
	 * ・すべてスペースの文字列　=>　0バイトの文字列<BR>
	 * ・スペース以外の文字が１文字でも含まれる場合 => そのまま<BR>
	 * @param  str チェック対象文字列
	 * @return 変換された文字列
	 */
	public static String convValidStr(String str)
	{
		if(str == null)
		{
			return "";
		}

		if(str.trim().length() == 0)
		{
			return "";
		}
		return str;
	}
	
	
	/**
	 * 指定された文字列を比較します。<BR>
	 * ・無効な文字列（null、0バイトの文字列、すべてスペースの文字列）同士を
	 * 比較した場合はtrueを返します。<BR>
	 * ・チェックの対象文字列は自動的に後ろのスペースを削除してから比較します。<BR>
	 * @param  str1 チェック対象文字列
	 * @param  str2 チェック対象文字列
	 * @return 比較した結果。
	 */
	public static boolean isEqualsStr(String str1, String str2)
	{
		
		str1 = convValidStr(str1);
		str2 = convValidStr(str2);
		
		if(str1.equals(str2))
		{
			return true;
		}
		
		if(str1.length() == str2.length())
		{
			return true;
		}
		
		return false;
		
	}

	/**
	 * 引数で指定したバイト配列の読込開始位置から項目長分のバイト配列を切り出します。
	 * byte[] elm = {a,b,c,d};
	 * byte[] ret = getItem(elm, 1, 2);
	 * 戻り値は　ret = {b,c}
	 * @param elm　バイト配列
	 * @param pointer　読込開始位置
	 * @param itemLength 項目長（バイト数で指定）
	 * @throws InvalidDefineException 読込開始位置＋項目長がバイト配列の長さを超えた場合。
	 */
	public static byte[] getItem(byte[] elm, int pointer, int itemLength) throws InvalidDefineException
	{
		//指定された引数の値に不整合がある場合
		if(elm.length < pointer + itemLength)
		{
			//例外をスローする場合は定義すること。
			throw new InvalidDefineException();
		}
		else
		{
			byte[] b = new byte[itemLength];
			for(int i = 0; i < itemLength; i++)
			{
				b[i] = elm[pointer+i];
			}
			return b;
		}
	}
	
	
	/**
	 * 引数で指定した文字列の読込開始位置から項目長分の文字列を切り出します。
	 * String elm = "abcde";
	 * String ret = getItem(elm, 1, 2);
	 * 戻り値は　ret = "bc"
	 * @param elm　編集元となる文字列
	 * @param pointer　読込開始位置
	 * @param itemLength 項目長（バイト数で指定）
	 * @throws InvalidDefineException 読込開始位置＋項目長がバイト配列の長さを超えた場合。
	 * @return 指定項目長分の文字列
	 */
	public static String getItem(String elm, int pointer, int itemLength) throws InvalidDefineException
	{
		byte[] buf = elm.getBytes();
		return new String(getItem(buf, pointer, itemLength));
	}
	
	/**
	 * 引数で指定された文字列を、指定の文字を加え、指定の長さの文字列にして返します。
	 * 指定文字数よりも、文字列のほうが長い場合は文字列を先頭から指定文字数分きりとってかえします。
	 * @param str		もとの文字列
	 * @param length	フォーマットしたい文字列長
	 * @param addStr	文字列に付加する文字
	 * @return			
	 */
	public static String getConcatItem(String str, int length, String addStr)
	{
		if(str == null)
		{
			str = "" ;
		}
		
		if(str.length() > length)
		{
			return str.substring(0, length);
		}
		
		while(str.length() < length)
		{
			str = str.concat( addStr );
		}

		return str;
	}
	
	// Constructors --------------------------------------------------
	/**
	 * 新しい<CODE>StringUtil</CODE>を構築します。
	 * 現段階では使用されることはありません。よって<CODE>private</CODE>
	 */
	private StringUtil()
	{
	}

	// Public methods ------------------------------------------------

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class
