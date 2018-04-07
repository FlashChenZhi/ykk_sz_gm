
package jp.co.daifuku.tools.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import jp.co.daifuku.util.PulldownHandler;

/** <jp>
 * Toolで使用するためのプルダウンを表示するためのクラスです。
 * PulldownHandlerを継承し、getTransStringメソッドをオーバライドしています。
 * TOOLで使用するプルダウンは、参照するリソースはTOOLのリソースではなく、設定する
 * プロダクトのリソースとなります。getTransStringメソッドがリソースを解釈する場合
 * は各プロダクトのリソースを参照するように実装を変更しています。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/28</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </jp> */
/** <en>
 * This is the class for pulldown. 
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/28</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </en> */
public class ToolPulldownHandler extends PulldownHandler 
{
	/** <jp>
	 * 引数の@で挟まれた範囲の文字列をリソースキーと判断し、メッセージへ変換を行います。
	 * Toolでは自分自身のリソースではなく、設定するプロダクトのリソースから値を取得する
	 * 必要があります。
	 * ・引数で、@で両端を区切られているものをリソース番号として認識する<br>
	 * ・指定されたリソースが存在しない場合は、引数としてセットされている文字をそのまま表示する<br>
	 * ・複数のメッセージをセットすることも可能
	 * 	'@6011023@' -> '処理中です。しばらくお待ち下さい。'<br>
	 * 	'@6011023@','@LBL-0001@' -> '処理中です。しばらくお待ち下さい。', '品名コード'
	 * 
	 * @param str
	 * @return 変換した文字列
	 </jp> */
	/** <en>
	 * The string of the range that it is put with @ of the argument is judged a resource key, and it changes into the message.	
	 * It is not its resource with Tool, but you must acquire value from the resource of a product to set up. 
	 * It has both ends divided with @ is recognized in the argument of the user script as a resource number.
	 * Resource number and the range that it was recognized.<br>
	 * When a specified resource doesn't exist, the character being set as an argument is indicated as it is.<br>
	 * It is possible that more than one message is set, too.
	 * ex.<br>
	 * '@6011023@' -> 'Operation accepted,please wait...'<br>
	 * '@6011023@','@LBL-0001@' -> 'Operation accepted,please wait...', 'ItemCode'
	 * 
	 * @param str
	 * @return Changed string.
	 </en> */
	protected String getTransString(String str)
	{
		try
		{
			int pos = 0;
			List list = new ArrayList();
			for(int i = 0; i < str.length(); i++)
			{
				int start_pos = str.indexOf(MSG_DEF_CHAR, pos);
				if(start_pos >= 0)
				{
					int end_pos = str.indexOf(MSG_DEF_CHAR, start_pos+1);
					if(end_pos >= 0)
					{
						list.add(str.substring(start_pos, end_pos+1));
						pos = end_pos + 1;
					}
					else
					{
						//Error
						break;
					}
				}
				else
				{
					break;
				}
			}

			String retValue = str;
			Iterator itr = list.iterator();
			while(itr.hasNext())
			{
				String orgmsg = ((String)itr.next());
				//Remove @ mark.
				String msgkey = orgmsg.replaceAll(MSG_DEF_CHAR, "");

				retValue = retValue.replaceAll(orgmsg, DispResourceMap.getText(msgkey));
			}
			return retValue;
		}
		catch(Exception e)
		{
			return str;
		}
	}

}
