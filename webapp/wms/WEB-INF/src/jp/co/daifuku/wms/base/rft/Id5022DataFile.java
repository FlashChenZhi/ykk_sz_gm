// $Id: Id5022DataFile.java,v 1.2 2006/11/14 06:09:06 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import java.io.IOException;
import java.util.Vector;

import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.RftConsignor;
import jp.co.daifuku.wms.base.rft.IdDataFile;

//#CM701598
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM701599
/**
 * The class to operate the Data file sent and received with ID5022. 
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of each line of file of ID5022</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH>	<TH>Content</TH></TR>
 * <tr><td>Consignor Code</td>		<td>16 byte</td><td>  </td></tr>
 * <tr><td>Consignor Name</td>		<td>40 byte</td><td>  </td></tr>
 * <tr><td>CRLF</td>			<td>2 byte</td>	<td>CR + LF</td></tr>
 * </table>
 * </p>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:06 $
 * @author $Author: suresh $
 */
public class Id5022DataFile extends IdDataFile
{
	//#CM701600
	// Class fields----------------------------------------------------
	//#CM701601
	/**
	 * Definition of offset of Consignor Code
	 */
	private static final int OFF_CONSIGNOR_CODE = 0;

	//#CM701602
	/**
	 * Definition of offset of Consignor Name
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

	//#CM701603
	/**
	 * Field which shows Consignor List file name
	 */
	public static final String ANS_FILE_NAME = "ID5022.txt" ;

	//#CM701604
	// Constructors --------------------------------------------------
	//#CM701605
	/**
	 * Generate the instance. 
	 * @param filename File Name
	 */
	public Id5022DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
	}

	//#CM701606
	/**
	 * Generate the instance. 
	 */
	public Id5022DataFile()
	{
		lineLength = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
	}
	//#CM701607
	/**
	 * Read the Data file, put in the array of Consignor, and return it. 
	 * 
	 * @return Array of Consignor(Entity class)
	 * @throws IOException		Error was generated while inputting and outputting the Entity class file. 
	 */
	public Entity[] getCompletionData() throws IOException
	{
		Vector v = new Vector();
		
		openReadOnly();
			
		for (next(); currentLine != null; next())
		{
			RftConsignor consignor =
				new RftConsignor(getConsignorCode(),
						getConsignorName(),
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"");
				
			v.addElement(consignor);
		}
			
		closeReadOnly();

		WorkingInformation[] data = new WorkingInformation[v.size()];
		v.copyInto(data);
		return data;
	}

	//#CM701608
	/**
	 * The file writes Data of the Consignor array specified by the argument. 
	 * 
	 * @param	obj				Data written in file (Consignor array) 
	 * @throws IOException		Error was generated while inputting and outputting the Entity class file. 
	 */
	public void write(Entity[] obj) throws IOException
	{
		RftConsignor[] consignor = (RftConsignor[])obj;
		
		openWritable();
			
		for (int i = 0; i < consignor.length; i ++)
		{
			setConsignorCode(consignor[i].getConsignorCode());
			setConsignorName(consignor[i].getConsignorName());
			
			writeln();
		}
		closeWritable();
	}

	//#CM701609
	/**
	 * Acquire Consignor Code from the Data buffer. 
	 * @return		Consignor Code
	 */
	public String getConsignorCode()
	{
		return getColumn(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
	}
	
	//#CM701610
	/**
	 * Set Consignor Code in the Data buffer. 
	 * @param	consignorCode	Consignor Code
	 */
	public void setConsignorCode(String consignorCode)
	{
		setColumn(consignorCode, OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
	}

	//#CM701611
	/**
	 * Acquire Consignor Name from the Data buffer. 
	 * @return		Consignor Name
	 */
	public String getConsignorName()
	{
		return getColumn(OFF_CONSIGNOR_NAME, LEN_CONSIGNOR_NAME);
	}
	
	//#CM701612
	/**
	 * Set Consignor Name to the Data buffer. 
	 * @param	consignorName	Consignor Name
	 */
	public void setConsignorName(String consignorName)
	{
		setColumn(consignorName, OFF_CONSIGNOR_NAME, LEN_CONSIGNOR_NAME);
	}
}
//#CM701613
//end of class
