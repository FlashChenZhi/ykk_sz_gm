//#CM643011
//$Id: IdData.java,v 1.2 2006/11/07 05:50:48 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643012
/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM643013
/**
 * <pre>
 * Transmission information class<br>
 * Maintain Content of display Transmission. 
 * </pre>
 * @author hota
 * @version 
 */
public class IdData 
{
	//#CM643014
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/07 05:50:48 $";
	}

	//#CM643015
	/**
	 * Constructor
	 */
	public IdData()
	{
		super();
	}

	//#CM643016
	/**
	 * Generate the instance of Transmission item information class.
	 */
	protected ColumnInfo columnInfo;	// 電文内容

	//#CM643017
	/**
	 * Acquire Transmission information from Transmission item information class. 
	 * @return Transmission information
	 */
	public ColumnInfo getTelegramData()
	{
		return columnInfo;
	}

	//#CM643018
	/**
	 * Maintain Transmission item information. 
	 * @param columnInfo Transmission item information
	 */
	public void setTelegramData(ColumnInfo columnInfo)
	{
		this.columnInfo = columnInfo;
	}
}
