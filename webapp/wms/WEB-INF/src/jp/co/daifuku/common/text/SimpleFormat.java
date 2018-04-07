// $Id: SimpleFormat.java,v 1.1.1.1 2006/08/17 09:33:44 mori Exp $
package jp.co.daifuku.common.text ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.StringTokenizer;

/**
 * 文字列をフォーマットします。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:44 $
 * @author  $Author: mori $
 */
public class SimpleFormat extends Object
{
	// Class fields --------------------------------------------------
	/*
	 * Comment for field
	 */
	
	// Class variables -----------------------------------------------
	
	/**
	 * <CODE>public static String format(long fmt)</CODE>メソッドにおいて、
	 * 数値項目に対してフォーマットを行うかどうかを決定するフラグです。<BR>
	 *True：数値項目にもフォーマットを行う<BR>
	 *False：数値項目にはフォーマットを行わない<BR>
	**/	
	public static boolean num_format = false;
	
	/**
	 * <CODE>public static String format(String fmt)</CODE>メソッドにおいて、
	 * 文字列内の"に対してエスケープを行うかどうかを決定するフラグです。<BR>
	 * true:"のエスケープを行う<BR>
	 * false:"のエスケープを行わない<BR>
	 */	
	public static boolean escape_flag = true;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:33:44 $") ;
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * 文字列をフォーマットします。{}で囲まれたインデックスに対して、
	 * Object<code>toString()</code>した物をセットします。
	 * {}そのものをエスケープする事は出来ません。
	 * <pre>
	 *  例:
	 *	String fmtStr = "abc {1} def {0} hij" ;
	 *	Object[] fmtObj = new Object[2] ;
	 *	fmtObj[0] = "xxx" ;
	 *	fmtObj[1] = "yyy" ;
	 *	SimpleFormat.format(fmtStr, fmtObj) ;
	 *  結果:
	 *  abc yyy def xxx hij
	 * </pre>
	 * @param fmt  フォーマットを指定します。
	 * @param prm  フォーマットに埋め込む情報の配列。
	 * @return     フォーマットされた文字列を返します。
	 */
	public static String format(String fmt, Object[] prm)
	{
		// フォーマット文字列の最初に { があると動作がおかしいので修正 2001.9.14 860228 村上
		String wkbuf = " ";
		String wkbuf2 = wkbuf + fmt;
		StringBuffer resbuf = new StringBuffer(wkbuf2.length()+64) ;
		StringTokenizer stk = new StringTokenizer(wkbuf2) ;
		while (stk.hasMoreTokens())
		{
			try
			{
				String tkn = stk.nextToken("{") ;
				int idel = tkn.indexOf('}') ;
				resbuf.append(tkn.substring(idel+1)) ;
				String rdelm = (stk.nextToken("}")).substring(1) ;
				resbuf.append(prm[Integer.parseInt(rdelm)].toString()) ;
			}
			catch (Exception e)
			{
			}
		}
		return (resbuf.toString()) ;
	}

	/**
	 * 文字列をフォーマットします。テキストファイルに出力する際、文字列の両端に
	 * "をセットします。ただし与えられた文字列がnull、0バイトの文字列だった場合、0バイトの文字列を返します。<BR>
	 * ＜変換例＞<BR>
	 *	AAA -> "AAA"<BR>
	 * @param  fmt フォーマットの対象となる文字列を指定します。
	 * @return フォーマットされた文字列を返します。
	 */
	public static String format(String fmt)
	{
		if (fmt == null || fmt.equals(""))
		{
			return ("");
		}
		else
		{
			if(escape_flag)
			{
				//"をエスケープします
				fmt = setEscapes(fmt);
			}

			int len = fmt.length() ;
			StringBuffer stbf = new StringBuffer(len + 2) ;
			stbf.append("\"") ;
			stbf.append(fmt) ;
			stbf.append("\"") ;
			return (stbf.toString()) ;
		}
	}

	/**
	 * 数値をフォーマットします。テキストファイルに出力する際、文字列の両端に
	 * "をセットします。ただし与えられた文字列がnulll、0バイトの文字列だった場合、0バイトの文字列を返します。
	 * ＜変換例＞<BR>
	 *	123 -> "123"<BR>
	 * @param  fmt フォーマットの対象となる文字列を指定します。
	 * @return フォーマットされた文字列を返します。
	 */
	public static String format(long fmt)
	{
		String str = Long.toString(fmt);
		//フォーマットを行う場合
		if(num_format)
		{
			int len = str.length() ;
			StringBuffer stbf = new StringBuffer(len + 2) ;
			stbf.append("\"") ;
			stbf.append(str) ;
			stbf.append("\"") ;
			return (stbf.toString()) ;
		}
		//フォーマットを行わない場合
		else
		{
			return str;
		}
	}


	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * 文字列に"が含まれている場合、それを"でエスケープします。
	 * 0バイトの文字列だった場合、0バイトの文字列を返します。<BR>
	 * ＜変換Ex＞<BR>
	 *	AA"A -> AA""A<BR>
	 * @param  fmt フォーマットの対象となる文字列を指定します。
	 * @return フォーマットされた文字列を返します。
	 */
	private static String setEscapes(String fmt)
	{
		String escapes = "\"";
		StringBuffer strbuf = new StringBuffer();
	    
		//1文字ずつ確認し、strbufへ代入する。
		//escapesが見つかったとき、escapesを付加しstrbufへ追加する。
		for(int i = 0; i < fmt.length(); i++)
		{
			String comp = String.valueOf(fmt.charAt(i));
			//escapesが見つかった場合
			if( comp.equals(escapes))
			{
				//エスケープするための文字列
				strbuf.append(escapes);
				//エスケープされる対象文字列
				strbuf.append(comp);
			}
			else
			{
				strbuf.append(comp);
			}
		}
		return strbuf.toString();
	}

}
//end of class

