
package jp.co.daifuku.tools.util;

import java.util.StringTokenizer;

/** <jp>
 * 入力チェック用ユーティリティクラスです。
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 *
 </jp> */
/** <en>
 * The utility class for an input check.
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 *
 </en> */
public class ToolValidator
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/** <jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	</jp> */
	/** <en>
	 * Returns the version of this class and date.
	 * @return - version and date.
	</en> */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:33:11 $");
	}

	/** <jp>
	 * 指定文字列精査。
	 *
	 * @param value	      対象オブジェクト
	 * @param inspections 有効な文字（例：0123456789 とすると数値だけ有効）
	 * @return 有効な文字のみであればtrue。それ以外はfalseを返却します。
	 </jp> */
	/** <en>
	 * Specification character sequence scrutinization.
	 *
	 * @param value	      object
	 * @param inspections An effective character(Example:Only a numerical value is effective when "0123456789")
	 * @return TRUE is returned when it consists of only specified characters. FALSE is returned when the character which is not specified is included.
	</en> */
	public static boolean isString(Object value, String inspections)
	{

		if (value == null)
			value = "";
		String check = value.toString();

		if (check.length() == 0)
		{
			return false;
		}
		StringTokenizer token = new StringTokenizer(check.toLowerCase(), inspections);
		return !token.hasMoreTokens();

	}


	// Constructors --------------------------------------------------
	/** <jp>
	 * このクラスはインスタンス化できません。
	 </jp> */
	/** <en>
	 * This class cannot carry out [ instance ]-izing.
	 </en> */
	private ToolValidator()
	{
		super();
	}

	// Public methods ------------------------------------------------

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	// Public methods ------------------------------------------------

}