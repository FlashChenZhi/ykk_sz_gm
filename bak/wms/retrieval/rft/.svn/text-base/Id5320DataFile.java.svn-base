// $Id: Id5320DataFile.java,v 1.3 2007/02/07 04:19:42 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import java.io.IOException;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdDataFile;

//#CM721041
/*
 * Copyright 2004-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM721042
/**
 * Allow this class to operate a data file to be sent or received by ID5320.
 * 
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:42 $
 * @author $Author: suresh $
 */
public class Id5320DataFile extends IdDataFile
{
	private static final int OFF_ORDER_NO = 0;

	private static final int OFF_CUSTOMER_CODE = OFF_ORDER_NO + LEN_ORDER_NO;

	private static final int OFF_CUSTOMER_NAME = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;

	//#CM721043
	/**
	 * A field that represents a Picking Order List file name.
	 */
	static final String ANS_FILE_NAME = "ID5320.txt" ;

	//#CM721044
	// Constructors --------------------------------------------------
	//#CM721045
	/**
	 * Constructor
	 */
	public Id5320DataFile()
	{
		lineLength = OFF_CUSTOMER_NAME + LEN_CUSTOMER_NAME;
	}
	//#CM721046
	/**
	 * Constructor
	 * @param filename file name
	 */
	public Id5320DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_CUSTOMER_NAME + LEN_CUSTOMER_NAME;
	}

	//#CM721047
	/**
	 * Load the data file and insert it in the WorkingInformation array, and return it.
	 * 
	 * @return Array of WorkingInformation (Entity class)
	 * @throws IOException		Error occurred in the process of input/output of file..
	 */
	public Entity[] getCompletionData() throws IOException, InvalidStatusException
	{
		Vector v = new Vector();
		
		openReadOnly();
			
		for (next(); currentLine != null; next())
		{
			WorkingInformation wi = new WorkingInformation();
				
			wi.setOrderNo(getOrderNo());
			wi.setCustomerCode(getCustomerCode());
			wi.setCustomerName1(getCustomerName());
				
			v.addElement(wi);
		}
		
		closeReadOnly();

		WorkingInformation[] data = new WorkingInformation[v.size()];
		v.copyInto(data);
		return data;
	}

	//#CM721048
	/**
	 * Write the data with WorkingInformation array designated by argument to the file.
	 * 
	 * @param	obj				Data to be written to file (WorkingInformation array)
	 * @throws IOException		Error occurred in the process of input/output of file..
	 */
	public void write(Entity[] obj) throws IOException
	{
		WorkingInformation[] workinfo = (WorkingInformation[])obj;
		
		openWritable();
			
		for (int i = 0; i < workinfo.length; i ++)
		{
			setOrderNo(workinfo[i].getOrderNo());
			setCustomerCode(workinfo[i].getCustomerCode());
			setCustomerName(workinfo[i].getCustomerName1());
			
			writeln();
		}
			
		closeWritable();
	}

	//#CM721049
	/**
	 * Obtain the Order No. from data buffer.
	 * @return	Order No.
	 */
	public String getOrderNo()
	{
		return getColumn(OFF_ORDER_NO, LEN_ORDER_NO) ;
	}

	//#CM721050
	/**
	 * Obtain the customer code from data buffer.
	 * @return		Customer Code
	 */
	public String getCustomerCode()
	{
		return getColumn(OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE) ;
	}
	
	//#CM721051
	/**
	 * Obtain the customer name from data buffer.
	 * @return		Customer Name
	 */
	public String getCustomerName()
	{
		return getColumn(OFF_CUSTOMER_NAME, LEN_CUSTOMER_NAME) ;
	}

	//#CM721052
	/**
	 * Set the Order No. in the data buffer
	 * @param	orderNo		Order No.
	 */
	public void setOrderNo(String orderNo)
	{
		setColumn(orderNo, OFF_ORDER_NO, LEN_ORDER_NO) ;
	}

	//#CM721053
	/**
	 * Set the customer code in the data buffer
	 * @param	customerCode	Customer Code
	 */
	public void setCustomerCode(String customerCode)
	{
		setColumn(customerCode, OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE) ;
	}
	
	//#CM721054
	/**
	 * Set the customer name in the data buffer
	 * @param	customerName	Customer Name
	 */
	public void setCustomerName(String customerName)
	{
		setColumn(customerName, OFF_CUSTOMER_NAME, LEN_CUSTOMER_NAME) ;
	}
}
