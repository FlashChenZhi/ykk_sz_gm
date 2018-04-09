// $Id: ListDataFile.java,v 1.2 2006/11/14 06:09:09 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701852
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM701853
/**
 * Super-class of list Data file sent and received by RFT communication. <BR>
 * Use the subclass of each ID to operate an actual file. 
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:09 $
 * @author  $Author: suresh $
 */
public abstract class ListDataFile extends IdDataFile
{

	//#CM701854
	// Class fields --------------------------------------------------
	//#CM701855
	/**
	 * Length of Row No.(byte)
	 */
	static final int LEN_LINE_NO = 0;
	
	//#CM701856
	// Class variables -----------------------------------------------
	
	//#CM701857
	// Class method --------------------------------------------------
	//#CM701858
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:09 $";
	}

	
	//#CM701859
	// Constructors --------------------------------------------------
	//#CM701860
	/**
	 * Generate the instance. 
	 */
	public ListDataFile()
	{
		super();
	}

	//#CM701861
	/**
	 * Generate the instance. 
	 * @param	len		Length of 1 line
	 */
	public ListDataFile(int length)
	{
		super(length);
	}

	//#CM701862
	/**
	 * Generate the instance. 
	 * 
	 * @param	filename	DataFile Name (Path is unnecessary. ) 
	 */
	public ListDataFile(String filename)
	{
		super(filename);
	}

	//#CM701863
	// Public methods ------------------------------------------------

	//#CM701864
	// Package methods -----------------------------------------------

	//#CM701865
	// Protected methods ---------------------------------------------

	//#CM701866
	// Private methods -----------------------------------------------
}
//#CM701867
//end of class
