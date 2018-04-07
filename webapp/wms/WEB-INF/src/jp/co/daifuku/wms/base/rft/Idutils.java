// $Id: Idutils.java,v 1.2 2006/11/14 06:09:08 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701834
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM701835
/**
 * Class which makes telegram, and makes Data file and offers 
 * utility function used. <BR>
 * Do not generate the instance, and call it with Class Name statically. 
 *
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:08 $
 * @author $Author: suresh $
 */
public class Idutils
{
	//#CM701836
	// Class method --------------------------------------------------
	//#CM701837
	/**
	 * Bury the number of lack digits of specified Data in space, and return byte row of specified Length. <BR>
	 * For left justify
	 * @param	data			Data which has been acquired
	 * @param	length		Width of Data when file is written
	 * @return	Character string where blank part of lack digit is made right justified
	 */
	static byte[] createByteDataLeft(byte[] data, int length)
	{
		byte[] space = new byte[length];

		for (int si = 0; si < length; si++)
		{
			space[si] = ' ';
		}
	
		for (int i = 0; i < data.length; i++)
		{
			space[i] = data[i];
		}
		
		return space;
	}

	//#CM701838
	/**
	 * Method which buries number of lack digits of Data which has been acquired in space. <BR>
	 * For left justify
	 * @param	data			Data which has been acquired
	 * @param	length		Width of Data when file is written
	 * @return	Character string where blank part of lack digit is made right justified
	 */
	static String createDataLeft(byte[] data, int length)
	{
		byte[] space = createByteDataLeft(data, length);
		
		return new String(space);
	}

	//#CM701839
	/**
	 * Bury the number of lack digits of specified Data in space, and return byte row of specified Length. <BR>
	 * For right justify
	 * @param	data			Data which has been acquired
	 * @param	length		Width of Data when file is written
	 * @return	Character string where blank part of lack digit is made left justified
	 */
	static byte[] createByteDataRight(byte[] data, int length)
	{
		byte[] space = new byte[length];
	
		for (int si = 0; si < length; si++)
		{
			space[si] = ' ';
		}
	
		int offSet = length - data.length;
	
		for (int i = 0; i < data.length; i++)
		{
			space[i + offSet] = data[i];
		}
	
		return space;
	}

	//#CM701840
	/**
	 * Method which buries number of lack digits of Data which has been acquired in space. <BR>
	 * For right justify
	 * @param	data			Data which has been acquired
	 * @param	length		Width of Data when file is written
	 * @return	Character string where blank part of lack digit is made left justified
	 */
	static String createDataRight(byte[] data, int length)
	{
		byte[] space = createByteDataRight(data, length);
	
		return new String(space);
	}

	//#CM701841
	/**
	 * Method which buries number of lack digits of Data which has been acquired in space. <BR>
	 * For left justify of character string
	 * @param	data		Character string Data which has been acquired
	 * @param	length		Width of Data when file is written
	 * @return	Character string where blank part of lack digit is made right justified
	 */
	public static String createDataLeft(String data, int length)
	{
		byte[] byteData = data.getBytes();
	
		return createDataLeft(byteData, length);
	}

	//#CM701842
	/**
	 * Method which buries number of lack digits of Data which has been acquired in space. <BR>
	 * For right justify of integer value
	 * @param	data		Numerical value Data which has been acquired
	 * @param	length		Width of Data when file is written
	 * @return	Character string where blank part of lack digit is made left justified
	 */
	public static String createDataRight(int data, int length)
	{
		byte[] byteData =  Integer.toString(data).getBytes() ;

		return createDataRight(byteData, length);
	}
}
//#CM701843
//end of class
