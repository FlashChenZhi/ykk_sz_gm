//#CM10206
//$Id: Id5921DataFile.java,v 1.2 2006/09/27 03:00:35 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import java.io.IOException;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdDataFile;

//#CM10207
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM10208
/**
 * This is the class to operate the data file to send and receive by ID5921.
 * 
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:35 $
 * @author $Author: suresh $
 */
public class Id5921DataFile extends IdDataFile
{	
	//#CM10209
	/**
	 * Offset the item code.
	 */
	private static final int OFF_ITEM_CODE = 0;

	//#CM10210
	/**
	 * Offset the Item name.
	 */
	private static final int OFF_ITEM_NAME = OFF_ITEM_CODE + LEN_ITEM_CODE;

	//#CM10211
	/**
	 * Offset the expiry date.
	 */
	private static final int OFF_USE_BY_DATE = OFF_ITEM_NAME + LEN_ITEM_NAME;

	//#CM10212
	/**
	 * Field that represents the stock list file name
	 */
	static final String ANS_FILE_NAME = "ID5921.txt" ;

	//#CM10213
	// Constructors --------------------------------------------------
	//#CM10214
	/**
	 * Constructors
	 */
	public Id5921DataFile()
	{
		lineLength = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
	}
	//#CM10215
	/**
	 * Constructors
	 * @param filename File name
	 */
	public Id5921DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
	}

	//#CM10216
	/**
	 * Read data file
	 * 
	 * @return Stock Array(Entity class)
	 * @throws IOException		error is generated when IO operation of the file.
	 * @throws InvalidStatusException Announce this when any discrepancy is found in the setting value of table update.
	 */
	public Entity[] getCompletionData() throws IOException, InvalidStatusException
	{
		Vector v = new Vector();
		
		openReadOnly();
			
		for (next(); currentLine != null; next())
		{
			Stock stock = new Stock();
				
			stock.setItemCode(getItemCode());
			stock.setItemName1(getItemName());
			stock.setUseByDate(getUseByDate());
			
			v.addElement(stock);
		}
		
		closeReadOnly();

		WorkingInformation[] data = new WorkingInformation[v.size()];
		v.copyInto(data);
		return data;
	}

	//#CM10217
	/**
	 * Write the data of WorkingInformation Array specified by the argument to the file.
	 * 
	 * @param	obj				data to wright on the file（WorkingInformation Array）
	 * @throws IOException		error is generated when IO operation of the file.
	 */
	public void write(Entity[] obj) throws IOException
	{
		Stock[] stock = (Stock[])obj;
		
		openWritable();
			
		for (int i = 0; i < stock.length; i ++)
		{
			setItemCode(stock[i].getItemCode());
			setItemName(stock[i].getItemName1());
			setUseByDate(stock[i].getUseByDate());
			
			writeln();
		}
			
		closeWritable();
	}

	//#CM10218
	/**
	 * Obtain the item code from the data buffer.
	 * @return	Item code
	 */
	public String getItemCode()
	{
		return getColumn(OFF_ITEM_CODE, LEN_ITEM_CODE);
	}

	//#CM10219
	/**
	 * Set the item code for data buffer.
	 * @param	itemCode		Item code
	 */
	public void setItemCode(String itemCode)
	{
		setColumn(itemCode, OFF_ITEM_CODE, LEN_ITEM_CODE);
	}

	//#CM10220
	/**
	 * Obtain the item name from the data buffer.
	 * @return	Item name
	 */
	public String getItemName()
	{
		return getColumn(OFF_ITEM_NAME, LEN_ITEM_NAME);
	}

	//#CM10221
	/**
	 * Set the item name for data buffer.
	 * @param	itemName		Item name
	 */
	public void setItemName(String itemName)
	{
		setColumn(itemName, OFF_ITEM_NAME, LEN_ITEM_NAME);
	}

	//#CM10222
	/**
	 * Obtain the expiry date from the data buffer.
	 * @return	Expiry Date
	 */
	public String getUseByDate()
	{
		return getColumn(OFF_USE_BY_DATE, LEN_USE_BY_DATE);
	}

	//#CM10223
	/**
	 * Set the expiry date for data buffer.
	 * @param	useByDate		Expiry Date
	 */
	public void setUseByDate(String useByDate)
	{
		setColumn(useByDate, OFF_USE_BY_DATE, LEN_USE_BY_DATE);
	}
}
