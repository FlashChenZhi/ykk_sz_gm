package jp.co.daifuku.wms.YkkGMAX.Utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;

public class Code39ParityBitGenerator
{

	// 0=0 1=1 2=2 3=3 4=4 5=5 6=6 7=7 8=8 9=9 A=10
	// B=11 C=12 D=13 E=14 F=15 G=16 H=17 I=18 J=19 K=20 L=21
	// M=22 N=23 O=24 P=25 Q=26 R=27 S=28 T=29 U=30 V=31 W=32
	// X=33 Y=34 Z=35 -=36 . = 37 sp=38 $=39 /=40 +=41 %=42

	private static char[] code39CharList = new char[] { '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', '-', '.', ' ', '$', '/', '+', '%' };

	private static HashMap code39CharValueMap = new HashMap();

	static
	{
		for (int i = 0; i < code39CharList.length; i++)
		{
			code39CharValueMap.put(new Character(code39CharList[i]),
					new Integer(i));
		}
	}

	public static String getParityBit(String code39String)
	{
		CharacterIterator it = new StringCharacterIterator(code39String
				.toUpperCase());

		int valueSum = 0;
		for (char ch = it.first(); ch != CharacterIterator.DONE; ch = it.next())
		{
//			Integer value = (Integer) code39CharValueMap.get(new Character(ch));
			valueSum += ((Integer) code39CharValueMap.get(new Character(ch)))
					.intValue();
		}

		int parityBitValue = valueSum % 43;

		return String.valueOf(code39CharList[parityBitValue]);
	}
}
