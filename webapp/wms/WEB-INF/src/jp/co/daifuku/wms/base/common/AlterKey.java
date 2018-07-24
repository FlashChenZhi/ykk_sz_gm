// $Id: AlterKey.java,v 1.2 2006/11/07 05:58:39 suresh Exp $
package jp.co.daifuku.wms.base.common;

//#CM642543
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Date ;

import jp.co.daifuku.common.ReadWriteException ;

//#CM642544
/**
 * The interface for key information specified when keeping information is changed. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/10/05</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:58:39 $
 * @author  $Author: suresh $
 */
public interface AlterKey
{
	//#CM642545
	// Public methods ------------------------------------------------
	//#CM642546
	/**
	 * Set the retrieval value of the character type in the specified column. 
	 * @param column
	 * @param value
	 * @throws ReadWriteException
	 */
	public void setValue(String column, String value)
			throws ReadWriteException ;

	//#CM642547
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, int intval)
			throws ReadWriteException ;

	//#CM642548
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, double intval)
			throws ReadWriteException ;

	//#CM642549
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, float intval)
			throws ReadWriteException ;

	//#CM642550
	/**
	 * Set the retrieval value of the date type in the specified column. 
	 * @param column
	 * @param dtval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, Date dtval)
			throws ReadWriteException ;

	//#CM642551
	/**
	 * Set the retrieval value of the character type in the specified column. 
	 * @param column
	 * @param value
	 * @throws ReadWriteException
	 */
	public void setValue(String column, String value[])
			throws ReadWriteException ;

	//#CM642552
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, int intval[])
			throws ReadWriteException ;

	//#CM642553
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, double intval[])
			throws ReadWriteException ;

	//#CM642554
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, float intval[])
			throws ReadWriteException ;

	//#CM642555
	/**
	 * Set the retrieval value of the date type in the specified column. 
	 * @param column
	 * @param dtval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, Date dtval[])
			throws ReadWriteException ;

	//#CM642556
	/**
	 * Set the retrieval value of the character type in the specified column. 
	 * @param column
	 * @param value
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, String value, String compcode)
			throws ReadWriteException ;

	//#CM642557
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, int intval, String compcode)
			throws ReadWriteException ;

	//#CM642558
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, double intval, String compcode)
			throws ReadWriteException ;

	//#CM642559
	/**
	 * Set the retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, float intval, String compcode)
			throws ReadWriteException ;

	//#CM642560
	/**
	 * Set the retrieval value of the date type in the specified column. 
	 * @param column
	 * @param dtval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, Date dtval, String compcode)
			throws ReadWriteException ;

	//#CM642561
	/**
	 * Set the retrieval value of the character type in the specified column. 
	 * Parameter Addition	compcode	Added the comparison function with String. 
	 * Parameter Addition	left_Paren	Add '(((('.
	 * Parameter Addition	right_Paren	Add '))))'.
	 * Parameter Addition	Specify 'AND' or 'OR'.
	 * 
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

	//#CM642562
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

	//#CM642563
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

	//#CM642564
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

	//#CM642565
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

	//#CM642566
	/**
	 * Set the update value of the character type in the specified column. 
	 * @param column
	 * @param value
	 */
	public void setUpdValue(String column, String value) ;

	//#CM642567
	/**
	 * Set the update value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 */
	public void setUpdValue(String column, int intval) ;

	//#CM642568
	/**
	 * Set the update value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 */
	public void setUpdValue(String column, double intval) ;

	//#CM642569
	/**
	 * Set the update value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 */
	public void setUpdValue(String column, float intval) ;

	//#CM642570
	/**
	 * Set the update value of the date type in the specified column. 
	 * @param column
	 * @param dtval
	 */
	public void setUpdValue(String column, Date dtval) ;

	//#CM642571
	/**
	 * Generate condition from the set key. Retrieve it on the handler side based on this content. 
	 * @param tablename Table name to be changed
	 * @return Condition
	 * @throws ReadWriteException
	 */
	public String getReferenceCondition(String tablename)
			throws ReadWriteException ;

	//#CM642572
	/**
	 * Generate the update sentence from the set key.Update it on the handler side based on this content. 
	 * @param tablename Table name to be changed
	 * @return Update sentence
	 */
	public String getModifyContents(String tablename) ;

	//#CM642573
	/**
	 * Set a peculiar automatic update value to each table. Update it on the handler side based on this content. 
	 */
	public void setAutoModify() throws ReadWriteException;

	//#CM642574
	// Package methods -----------------------------------------------

	//#CM642575
	// Protected methods ---------------------------------------------

	//#CM642576
	// Private methods -----------------------------------------------

}
//#CM642577
//end of interface

