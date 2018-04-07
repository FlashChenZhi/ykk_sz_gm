//$Id: DisplayText.java,v 1.2 2007/03/08 09:34:31 okamura Exp $
package jp.co.daifuku.common.text;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * 画面フィールド情報をリソースから取得するためのクラスです。
 * リソース名称のデフォルトは、<code>DispMessage</code>となっています。<BR>
 * 画面の文字表示は、通常<CODE>getText(String rname, HttpServletRequest request)</CODE>メソッドを使用して下さい。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/02/27</TD><TD>miyashita</TD><TD>wDispRes private->public modify</TD></TR>
 * <TR><TD>2002/06/27</TD><TD>sawa</TD><TD>データベースを検索するメソッドを全て削除</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>miyashita</TD><TD>formatLocation(int bank, int bay, int level) add</TD></TR>
 * <TR><TD>2004/03/23</TD><TD>hondo</TD><TD>ラベル名称がリソースになかった場合の対応をしました。<BR>
 * 番号がリソースになかった場合、英語のリソースから取得する。<BR>
 * 英語のリソースにもなければラベルの番号を表示するように変更しました。</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/03/08 09:34:31 $
 * @author  $Author: okamura $
 </jp>*/
/**<en>
 * This is the class to get the display field information from the resource.
 * Default resource name is <code>DispMessage</code><BR>
 * Use method <CODE>getText(String rname, HttpServletRequest request)</CODE> for the indication of character on the display.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/02/27</TD><TD>miyashita</TD><TD>wDispRes private->public modify</TD></TR>
 * <TR><TD>2002/06/27</TD><TD>sawa</TD><TD>Deleted all search methods in database</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>miyashita</TD><TD>formatLocation(int bank, int bay, int level) add</TD></TR>
 * <TR><TD>2004/03/23</TD><TD>hondo</TD>Measure to take if no label name was found in resource.<TD><BR>
 * Corrected: In case the number did not exist in resource, the number will be acquired from English resource.<BR>
 * If it cannot be found in English resource either, label number will be displayed.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/03/08 09:34:31 $
 * @author  $Author: okamura $
 </en>*/
public class DisplayText
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	private static Locale dummy_locale = Locale.ENGLISH ;

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returning version of this class
	 * @return version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/03/08 09:34:31 $");
	}

	/**<jp>
	 * キーから、パラメータの内容を取得します。
	 * @param rname  取得するパラメータのキー
	 * @return       パラメータの文字列表現
	 </jp>*/
	/**<en>
	 * Getting content of parameter using key
	 * @param rname  Key of parameter to get 
	 * @return       String representation of parameter
	 </en>*/
	public static String getText(String rname)
	{
		return DispResources.getText(rname);
	}

	/**<jp>
	 * キーから、パラメータの内容を取得します。
	 * @param locale 言語情報
	 * @param rname  取得するパラメータのキー
	 * @return       パラメータの文字列表現
	 </jp>*/
	public static String getText(Locale locale, String rname)
	{
		//modified by Suresh Kayamboo for chinese locale
		//return DispResources.getText(locale, rname);
		if( "ja".equals(locale.getLanguage()) || "en".equals(locale.getLanguage()) )
		{
			return DispResources.getText(locale, rname);
		}
		else
		{
			return DispResources.getText(dummy_locale, rname);
		}


		
	}

	/**<jp>
	 * 表名、列名、値(String)をキーにしてパラメータの内容を取得します。
	 * @param tableName  取得するパラメータのキー(表名)
	 * @param colName  取得するパラメータのキー(列名)
	 * @param val  取得するパラメータのキー(値)
	 * @return       パラメータの文字列表現
	 </jp>*/
	/**<en>
	 * Getting contents of parameter using keys according to the name of list, line and value(int).
	 * @param tableName  Key of parameter to get (list name)
	 * @param colName  Key of parameter (line name))
	 * @param val  Key of getting parameter(value)
	 * @return       String representation of parameter
	 </en>*/
	public static String getText(String tableName, String colName, String val)
	{
		String key = tableName + "_" + colName + "_" + val;
		return getText(key);
	}

	/**
	 * 表名、列名、値(String)をキーにしてパラメータの内容を取得します。
	 * @param locale 言語情報
	 * @param tableName  取得するパラメータのキー(表名)
	 * @param colName  取得するパラメータのキー(列名)
	 * @param val  取得するパラメータのキー(値)
	 * @return       パラメータの文字列表現
	 */
	public static String getText(Locale locale, String tableName, String colName, String val)
	{
		String key = tableName + "_" + colName + "_" + val;
		
		//return getText(locale, key);
		if( "ja".equals(locale.getLanguage()) || "en".equals(locale.getLanguage()) )
		{
			return getText(locale, key);
		}
		else
		{
			return getText(dummy_locale, key);
		}

	
	}

	// Constructors --------------------------------------------------
	/**<jp>
	 * <CODE>DisplayText</CODE>オブジェクトを構築することはありません。
	 </jp>*/
	/**<en>
	 * There may be no case constructing <CODE>DisplayText</CODE> obeject.
	 </en>*/
	private DisplayText()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * 倉庫ステーションNo.、画面表示されている棚から
	 * SHELF.StationNumber形式を生成して返します。
	 * <BR>
	 * 画面表示の棚は　バンク＋ベイ＋レベル＋サブロケーション　とします。
	 * バンク、ベイ、レベルは必須ですが、サブロケーションは任意項目とします。
	 * 
	 * @param wnumber 格納区分(Ex:A)
	 * @param locationnumber 棚(Ex:01010100)
	 * @return 編集後の棚ナンバー(Ex:A01001001000)
	 */
	public static String formatLocation(String whStationNo, String locationNo)
	{
		Connection conn = null;
		String stationNo = "";
		
		try
		{
			if (!StringUtil.isBlank(locationNo))
			{
				conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_NAME);
				ASFindUtil fu = new ASFindUtil(conn);
				// 格納区分（Ex:A）取得
				int whNo = fu.getWHNumber(whStationNo);
			
				stationNo = formatLocation(whNo, locationNo);
			}		
		}
		catch (Exception e)
		{
			return "CommonParam Define Error" ;
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				return "CommonParam Define Error" ;
			}
		}
		
		return stationNo;
	}

	/**
	 * 格納区分、画面表示されている棚から
	 * SHELF.StationNumber形式を生成して返します。
	 * <BR>
	 * 画面表示の棚は　バンク＋ベイ＋レベル＋サブロケーション　とします。
	 * バンク、ベイ、レベルは必須ですが、サブロケーションは任意項目とします。
	 * 
	 * @param wnumber 格納区分(Ex:A)
	 * @param locationnumber 棚(Ex:01010100)
	 * @return 編集後の棚ナンバー(Ex:A01001001000)
	 */
	public static String formatLocation(int wnumber, String locationnumber)
	{
		try
		{
			// 画面で表示されている桁数を求める
			int banksz   = WmsParam.BANK_DISP_LENGTH;
			int baysz    = WmsParam.BAY_DISP_LENGTH;
			int levelsz  = WmsParam.LEVEL_DISP_LENGTH;
			int sublocsz = WmsParam.AREA_DISP_LENGTH;
			if ((banksz + baysz + levelsz + sublocsz) != locationnumber.length())
			{
				return "CommonParam Define Error" ;
			}

			// 画面で表示されている棚を倉庫、バンク、ベイ、レベル、サブロケーションに分解する
			String bank     = locationnumber.substring(0, banksz);
			String bay      = locationnumber.substring(banksz, banksz + baysz);
			String level    = locationnumber.substring(banksz + baysz, banksz + baysz + levelsz);
			String subloc   = locationnumber.substring(banksz + baysz + levelsz, banksz + baysz + levelsz + sublocsz);

			// 画面表示からDBフォーマットに置き換える
			String fmWh = "";
			String fmBank = "";
			String fmBay = "";
			String fmLevel = "";
			String fmSubLoc = "";
			fmWh = NumToStr(1, WmsParam.WAREHOUSE_DB_LENGTH,  wnumber) ;
			fmBank = NumToStr(1, WmsParam.BANK_DB_LENGTH,  Integer.parseInt(bank)) ;
			fmBay  = NumToStr(1, WmsParam.BAY_DB_LENGTH,   Integer.parseInt(bay)) ;
			fmLevel= NumToStr(1, WmsParam.LEVEL_DB_LENGTH, Integer.parseInt(level)) ;
			if (!StringUtil.isBlank(fmSubLoc))
			{
				// サブロケーションは任意入力のため、入力がある場合のみフォーマットを行う
				fmSubLoc = NumToStr(1, WmsParam.AREA_DB_LENGTH,  Integer.parseInt(subloc)) ;
			}
			else
			{
				fmSubLoc = NumToStr(1, WmsParam.AREA_DB_LENGTH,  0) ;
			}
			return (fmWh + fmBank + fmBay + fmLevel + fmSubLoc);
		}
		catch (Exception e)
		{
			return "CommonParam Define Error" ;
		}
	}

	/**
	 * SHELF.StationNumber形式のStationNoを
	 * ハイフネーション編集し返します。<BR>
	 * 帳票印字などで使用します。<BR>
	 * [格納区分 - バンク - ベイ - レベル - サブロケーション]を作成します。<BR>
	 * サブロケーションについて、CommonParamde画面表示桁数が０に
	 * 設定されている場合は、サブロケーションは表示から外します。
	 * 
	 * @param stno   編集前のStationNumber(Ex:A01001001000 )
	 * @return       ハイフン編集した表示形式(Ex:A-01-001-001)
	 */
	public static String formatLocation(String stno)
	{
		try
		{
			// ステーションNoがnullのときは空白を返す
			if (StringUtil.isBlank(stno))
			{
				return "" ;
			}
			
			// DBで保持する棚フォーマットを取得する
			int whsz   = WmsParam.WAREHOUSE_DB_LENGTH;
			int banksz   = WmsParam.BANK_DB_LENGTH;
			int baysz    = WmsParam.BAY_DB_LENGTH;
			int levelsz  = WmsParam.LEVEL_DB_LENGTH;
			int sublocsz = WmsParam.AREA_DB_LENGTH;

			if ((whsz + banksz + baysz + levelsz + sublocsz) != stno.length())
			{
				return stno;
			}
			
			// SHELF.StationNumberを倉庫、バンク、ベイ、レベル、サブロケーションに分解する
			String wh       = stno.substring(0, whsz);
			String bank     = stno.substring(whsz, whsz + banksz);
			String bay      = stno.substring(whsz + banksz, whsz + banksz + baysz);
			String level    = stno.substring(whsz + banksz + baysz, whsz + banksz + baysz + levelsz);
			String subloc   = stno.substring(whsz + banksz + baysz + levelsz, whsz + banksz + baysz + levelsz + sublocsz);

			// DBフォーマットから画面表示フォーマットに置き換える
			String fmWh = "";
			String fmBank = "";
			String fmBay = "";
			String fmLevel = "";
			String fmSubLoc = "";
			fmWh = NumToStr(1, WmsParam.WAREHOUSE_DISP_LENGTH,  Integer.parseInt(wh));
			fmBank = NumToStr(1, WmsParam.BANK_DISP_LENGTH,  Integer.parseInt(bank));
			fmBay  = NumToStr(1, WmsParam.BAY_DISP_LENGTH,   Integer.parseInt(bay));
			fmLevel= NumToStr(1, WmsParam.LEVEL_DISP_LENGTH, Integer.parseInt(level));
			fmSubLoc= NumToStr(1, WmsParam.AREA_DISP_LENGTH, Integer.parseInt(subloc));

			// 各項目を連結する
			// warehousenumber-bank-bay-level
			String SEPARATOR = "-";
			StringBuffer wk = new StringBuffer();
			wk.append(fmWh).append(SEPARATOR).append(fmBank).append(SEPARATOR).append(fmBay).append(SEPARATOR).append(fmLevel);
			if (WmsParam.AREA_DISP_LENGTH != 0)
			{
				// warehousenumber-bank-bay-level-sublocation
				// サブロケーションは任意入力なので、画面表示桁数が0に設定されている場合は、フォーマットから除きます
				wk.append(SEPARATOR).append(fmSubLoc);
			}

			return wk.toString();
		}
		catch (Exception e)
		{
			return "CommonParam Define Error" ;
		}
	}

	/**
	 * SHELF.StationNumber形式のStationNoをハイフンなしの棚No.に編集して返します。<BR>
	 * 棚No.は[バンク + ベイ + レベル + サブロケーション]形式になります。<BR>
	 * サブロケーションについて、CommonParamde画面表示桁数が０に
	 * 設定されている場合は、サブロケーションは表示から外します。
	 * 
	 * @param stno   編集前の棚ナンバー(Ex:A01001001000 )
	 * @return       編集後の棚ナンバー(Ex:010101)
	 */
	public static String formatLocationnumber(String stno)
	{
		return getDispFormatLocation(stno, "");
	}
	
	/**
	 * SHELF.StationNumber形式のStationNoをハイフンありの棚No.に編集して返します。<BR>
	 * 棚No.は[バンク - ベイ - レベル - サブロケーション]形式になります。<BR>
	 * サブロケーションについて、CommonParamde画面表示桁数が０に
	 * 設定されている場合は、サブロケーションは表示から外します。
	 * 
	 * @param stno   編集前の棚ナンバー(Ex:A01001001000 )
	 * @return       編集後の棚ナンバー(Ex:01-01-01)
	 */
	public static String formatDispLocation(String stno)
	{
		return getDispFormatLocation(stno, "-");
	}

	/**
	 * SHELF.StationNumber形式のStationNoをハイフンなしの棚No.に編集して返します。<BR>
	 * 棚No.は[バンク + ベイ + レベル + サブロケーション]形式になります。<BR>
	 * サブロケーションについて、CommonParamde画面表示桁数が０に
	 * 設定されている場合は、サブロケーションは表示から外します。<BR>
	 * また、引数でセパレータを指定することにより、バンク、ベイ、レベル、サブロケーションの間に
	 * 挿入する区切りの文字を指定することができます。<BR>
	 * 例1：separator = "-"<BR>
	 * 返り値 = 01-01-01<BR>
	 * <BR>
	 * 例2：separator = ""<BR>
	 * 返り値 = 010101<BR>
	 * 
	 * @param stno   編集前の棚ナンバー(Ex:A01001001000 )
	 * @return       編集後の棚ナンバー(Ex:010101)
	 */
	protected static String getDispFormatLocation(String stno, String separator)
	{
		try
		{
			// ステーションNoがnullのときは空白を返す
			if (StringUtil.isBlank(stno))
			{
				return "" ;
			}

			// DBで保持する棚フォーマットを取得する
			int whsz   = WmsParam.WAREHOUSE_DB_LENGTH;
			int banksz   = WmsParam.BANK_DB_LENGTH;
			int baysz    = WmsParam.BAY_DB_LENGTH;
			int levelsz  = WmsParam.LEVEL_DB_LENGTH;
			int sublocsz = WmsParam.AREA_DB_LENGTH;

			if ((whsz + banksz + baysz + levelsz + sublocsz) != stno.length())
			{
				return stno ;
			}
			
			// SHELF.StationNumberをバンク、ベイ、レベル、サブロケーションに分解する
			String bank     = stno.substring(whsz, whsz + banksz);
			String bay      = stno.substring(whsz + banksz, whsz + banksz + baysz);
			String level    = stno.substring(whsz + banksz + baysz, whsz + banksz + baysz + levelsz);
			String subloc   = stno.substring(whsz + banksz + baysz + levelsz, whsz + banksz + baysz + levelsz + sublocsz);

			// DBフォーマットから画面表示フォーマットに置き換える
			String fmBank = "";
			String fmBay = "";
			String fmLevel = "";
			String fmSubLoc = "";
			fmBank = NumToStr(1, WmsParam.BANK_DISP_LENGTH,  Integer.parseInt(bank));
			fmBay  = NumToStr(1, WmsParam.BAY_DISP_LENGTH,   Integer.parseInt(bay));
			fmLevel= NumToStr(1, WmsParam.LEVEL_DISP_LENGTH, Integer.parseInt(level));
			fmSubLoc= NumToStr(1, WmsParam.AREA_DISP_LENGTH, Integer.parseInt(subloc));

			// 各項目を連結する
			// bank-bay-level
			String SEPARATOR = separator;
			StringBuffer wk = new StringBuffer();
			wk.append(fmBank).append(SEPARATOR).append(fmBay).append(SEPARATOR).append(fmLevel);
			if (WmsParam.AREA_DISP_LENGTH != 0)
			{
				// bank-bay-level-sublocation
				// サブロケーションは任意入力なので、画面表示桁数が0に設定されている場合は、フォーマットから除きます
				wk.append(SEPARATOR).append(fmSubLoc);
			}

			return wk.toString();
		}
		catch (Exception e)
		{
			return "CommonParam Define Error" ;
		}
		
	}
	
	/**<jp>
	 * 数値を文字列に変換します。
	 * @param   func   ゼロサプレスありの場合は0をセットして下さい。
	 * @param   figure 変更する文字の桁数
	 * @param   val    変更したい値
	 * @return  変換した文字列
	 </jp>*/
	/**<en>
	 * Converting the numeric value to the String
	 * @param   func   Set 0 if 0 suppression is required.
	 * @param   figure Number of digits of characters to change
	 * @param   val    Value to change
	 * @return  Converted String
	 </en>*/
	protected static String NumToStr(int func, int figure, int val)
	{
		char 	ch ; // Character that comes before
		String  str = "" ;
		switch(func)
		{
			//<jp> ゼロサプレスあり</jp>
			//<en> with 0 suppression</en>
			case 0:
				ch = ' ' ;
				break ;
			//<jp> ゼロサプレスなし</jp>
			//<en> without 0 suppression</en>
			default:
				ch = '0' ;
				break ;
		}

		for (int i = 0; i < figure; i++)
		{
			str += ch ; 
		}

		DecimalFormat df = new DecimalFormat(str) ;
		return (df.format(val)) ;
	}


	/**<jp>
	 * 引数として受け取ったStringでデリミタが連続しているところをひとつスペースを空ける。<BR>
	 * Ex item0001,100,200,,300, -> item0001,100,200, ,300,
	 * AS/RS設定ツールだけで使用するメソッドです。
	 </jp>*/
	/**<en>
	 * Providing a space in between the consecutive delimiters in the String accepted as argument<BR>
	 * Ex item0001,100,200,,300, -> item0001,100,200, ,300,
	 </en>*/
	public static String DelimiterCheck(String str, String delim)
	{
		StringBuffer sb = new StringBuffer(str) ;
		int len = sb.length() ;
		int i   = 0 ;
		for ( i = 0; i < len; i++)
		{
			if ( i < len - 1 )
			{
				if (sb.substring(i, i+2).equals( delim + delim ))
				{
					sb.replace(i, i+2, delim + " " + delim) ;
				}
			}
			len = sb.length() ;
		}
		if (sb.substring(len-1, len).equals(delim))
		{
			sb = sb.append(" ") ;
		}
		return (sb.toString()) ;
	}

	/**<jp>
	 * 文字列の右端から空白を除去します。 
	 * Ex "   1 22   33333     " -> "   1 22   33333"
	 * @param str 編集対象文字列
	 * @return str 空白を除去された文字列。Nullが引数の場合はそのままNullを返します。
	 * AS/RS設定ツールだけで使用するメソッドです。
	 </jp>*/
	/**<en>
	 * Getting rid of a blank from the right end of String 
	 * Ex "   1 22   33333     " -> "   1 22   33333"
	 * @param str Target String for editing
	 * @return str String, the blank eliminated; retuning null if null is argment.
	 </en>*/
	public static String trim(String str)
	{
		if (str == null) return null ;

		int len = str.length() ;
		try
		{
			while (str.lastIndexOf(" ", len) == (len - 1))
			{
				len-- ;
				str = str.substring(0, len);
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			str = "";
		}
		return str ;
	}

	/**<jp>
	 * 指定された文字列内に、システムで定義された禁止文字が含まれているかどうか検証します。
	 * 禁止文字の定義は、CommonParamにて指定します。
	 * @param pattern 対象となる文字列を指定します。
	 * @return 文字列中に禁止文字が含まれる場合はtrue, 禁止文字が含まれない場合はfalseを返します。
	 </jp>*/
	/**<en>
	 * Examining whether/not system-defined banned character is included in specified String
	 * Definition of banned characters to be fixed at CommonParam.
	 * @param pattern Specifying the target String
	 * @return Returning 'true' if it includes the banned character and 'false' if not.
	 </en>*/
	public static boolean isPatternMatching(String pattern)
	{
		return (isPatternMatching(pattern, CommonParam.getParam("NG_PARAMETER_TEXT"))) ;
	}	

	/**<jp>
	 * 指定された文字列内に、システムで定義された禁止文字が含まれているかどうか検証します。
	 * 禁止文字の定義は、CommonParamにて指定します。
	 * AS/RS設定ツールだけで使用するメソッドです。
	 * @param pattern 対象となる文字列を指定します。
	 * @param ngshars 禁止文字
	 * @return 文字列中に禁止文字が含まれる場合はtrue, 禁止文字が含まれない場合はfalseを返します。
	 </jp>*/
	/**<en>
	 * Examining whether/not system-defined banned character is included in specified String
	 * Definition of banned characters to be fixed at CommonParam
	 * @param pattern Specifying the target String
	 * @param ngshars Banned characters
	 * @return eturning 'true' if it includes the banned character and 'false' if not.
	 </en>*/
	public static boolean isPatternMatching(String pattern, String ngshars)
	{
		if (pattern != null && !pattern.equals(""))
		{
			for (int i = 0; i < ngshars.length() ; i++)
			{
				if (pattern.indexOf(ngshars.charAt(i)) > -1)
				{
					return true ;
				}
			}
		}
		return false ;
	}


	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
