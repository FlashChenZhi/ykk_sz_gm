// $Id: SearchKey.java,v 1.2 2006/11/07 06:01:57 suresh Exp $
package jp.co.daifuku.wms.base.common;

import java.util.Date ;

import jp.co.daifuku.common.ReadWriteException ;

//#CM642799
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM642800
/**
 * The interface for key information specified when keeping information is acquired. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:01:57 $
 * @author  $Author: suresh $
 */
public interface SearchKey
{
	//#CM642801
	// Public methods ------------------------------------------------

	//#CM642802
	/**
	 * Set the retrieval value of the character type in the specified column. 
	 * @param column
	 * @param value
	 * @throws ReadWriteException
	 */
	public void setValue(String column, String value)
			throws ReadWriteException ;

	//#CM642803
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, int intval)
			throws ReadWriteException ;

	//#CM642804
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, double intval)
			throws ReadWriteException ;

	//#CM642805
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, float intval)
			throws ReadWriteException ;

	//#CM642806
	/**
	 * Set the retrieval value of the date type in the specified column. 
	 * @param column
	 * @param dtval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, Date dtval)
			throws ReadWriteException ;

	//#CM642807
	/**
	 * Set the retrieval value of the character type in the specified column. 
	 * @param column
	 * @param value
	 * @throws ReadWriteException
	 */
	public void setValue(String column, String value[])
			throws ReadWriteException ;

	//#CM642808
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, int intval[])
			throws ReadWriteException ;

	//#CM642809
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, double intval[])
			throws ReadWriteException ;

	//#CM642810
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, float intval[])
			throws ReadWriteException ;

	//#CM642811
	/**
	 * Set the retrieval value of the date type in the specified column. 
	 * @param column
	 * @param dtval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, Date dtval[])
			throws ReadWriteException ;

	//#CM642812
	/**
	 * Set the order of sorting in the specified column. 
	 * @param column
	 * @param num
	 * @param bool
	 */
	public void setOrder(String column, int num, boolean bool) ;

	//#CM642813
	/**
	 * Set the retrieval value of the character type in the specified column. 
	 * Parameter Addition	Added the comparison function with String. 
	 * @param column
	 * @param value
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, String value, String compcode)
			throws ReadWriteException ;

	//#CM642814
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, int intval, String compcode)
			throws ReadWriteException ;

	//#CM642815
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, double intval, String compcode)
			throws ReadWriteException ;

	//#CM642816
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, float intval, String compcode)
			throws ReadWriteException ;

	//#CM642817
	/**
	 * Set the retrieval value of the date type in the specified column. 
	 * @param column
	 * @param dtval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, Date dtval, String compcode)
			throws ReadWriteException ;

	//#CM642818
	/**
	 * Set the order of the group in the specified column. 
	 * @param column
	 * @param num
	 */
	public void setGroup(String column, int num) ;

	//#CM642819
	/**
	 * Acquire information in the condition specified from the column. 
	 * @param column
	 * @param value
	 */
	public void setCollect(String column, String value) ;

	//#CM642820
	/**
	 * Set the retrieval value of the character type in the specified column. 
	 * 	Parameter Addition	compcode	Added the comparison function with String. 
	 * 	Parameter Addition	left_Paren	Add '(((('.
	 * 	Parameter Addition	right_Paren	Add '))))'.
	 * 	Parameter Addition	Specify 'AND' or 'OR'.
	 * @param column
	 * @param value
	 * @param compcode
	 * @param left_Paren
	 * @param right_Paren
	 * @param and_or
	 * @throws ReadWriteException
	 */

	public void setValue(String column, String value, String compcode, String left_Paren,
			String right_Paren, String and_or)
			throws ReadWriteException ;

	//#CM642821
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @param left_Paren
	 * @param right_Paren
	 * @param and_or
	 * @throws ReadWriteException
	 */
	public void setValue(String column, int intval, String compcode, String left_Paren,
			String right_Paren, String and_or)
			throws ReadWriteException ;

	//#CM642822
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @param left_Paren
	 * @param right_Paren
	 * @param and_or
	 * @throws ReadWriteException
	 */
	public void setValue(String column, double intval, String compcode, String left_Paren,
			String right_Paren, String and_or)
			throws ReadWriteException ;

	//#CM642823
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @param left_Paren
	 * @param right_Paren
	 * @param and_or
	 * @throws ReadWriteException
	 */
	public void setValue(String column, float intval, String compcode, String left_Paren,
			String right_Paren, String and_or)
			throws ReadWriteException ;

	//#CM642824
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param dtval
	 * @param compcode
	 * @param left_Paren
	 * @param right_Paren
	 * @param and_or
	 * @throws ReadWriteException
	 */
	public void setValue(String column, Date dtval, String compcode, String left_Paren,
			String right_Paren, String and_or)
			throws ReadWriteException ;

	//#CM642825
	/**
	 * Generate condition from the set key. Retrieve it on the handler side based on this content. 
	 * @return Condition
	 * @throws ReadWriteException
	 */
	public String getReferenceCondition()
			throws ReadWriteException ;

	//#CM642826
	/**
	 * Generate the order of sorting from the set key. Sort it on the handler side based on this content. 
	 * @return Sorting phrase
	 */
	public String getSortCondition() ;

	//#CM642827
	/**
	 * Acquire definition information on the order of sorting from the set key. 
	 * @return Order+Sorting phrase
	 */
	public String[] getSortConditionTable() ;

	//#CM642828
	/**
	 * Generate the order of the group from the set key. Sort it on the handler side based on this content. 
	 * @return Group phrase
	 * @throws ReadWriteException
	 */
	public String getGroupCondition()
			throws ReadWriteException ;

	//#CM642829
	/**
	 * Judge the presence of the acquisition condition of the item. 
	 * @param Keyword
	 * @return Presence of acquisition condition
	 */
	public boolean checkCollection(String Keyword) ;

	//#CM642830
	/**
	 * Only the set item does the information gathering. 
	 * @return Acquisition item
	 */
	public String getCollectCondition() ;

	//#CM642831
	/**
	 * Only the set item does the information gathering. Used in the count method. 
	 * @return Acquisition item
	 */
	public String getCollectConditionForCount() ;

	//#CM642832
	// Package methods -----------------------------------------------

	//#CM642833
	// Protected methods ---------------------------------------------

	//#CM642834
	// Private methods -----------------------------------------------

}
//#CM642835
//end of interface

