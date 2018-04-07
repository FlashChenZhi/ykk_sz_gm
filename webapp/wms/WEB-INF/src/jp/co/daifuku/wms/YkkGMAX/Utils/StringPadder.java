package jp.co.daifuku.wms.YkkGMAX.Utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringPadder
{
	private static StringBuffer strb;
	private static StringCharacterIterator sci;

	public static String leftPad(String stringToPad, String padder, int size)
	{
		if (padder.length() == 0)
		{
			return stringToPad;
		}
		strb = new StringBuffer(size);
		sci = new StringCharacterIterator(padder);

		while (strb.length() < (size - stringToPad.length()))
		{
			for (char ch = sci.first(); ch != CharacterIterator.DONE; ch = sci
					.next())
			{
				if (strb.length() < size - stringToPad.length())
				{
					strb.insert(strb.length(), String.valueOf(ch));
				}
			}
		}
		return strb.append(stringToPad).toString();
	}

	public static String rightPad(String stringToPad, String padder, int size)
	{
		if (padder.length() == 0)
		{
			return stringToPad;
		}
		strb = new StringBuffer(stringToPad);
		sci = new StringCharacterIterator(padder);

		while (strb.length() < size)
		{
			for (char ch = sci.first(); ch != CharacterIterator.DONE; ch = sci
					.next())
			{
				if (strb.length() < size)
				{
					strb.append(String.valueOf(ch));
				}
			}
		}
		return strb.toString();
	}
}
