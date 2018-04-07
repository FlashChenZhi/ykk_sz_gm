// $Id: FieldName.java,v 1.2 2006/11/15 04:25:38 kamala Exp $
package jp.co.daifuku.wms.base.dbhandler;

//#CM708486
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM708487
/**
 * The class which maintains the field name used by the parameter and the entity. 
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- Change history -->
 * <tr><td nowrap>2003/05/11</td><td nowrap>The person who created this file.</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/11/15 04:25:38 $
 * @author  S.Suzuki
 * @author  Last commit: $Author: kamala $
 */
public class FieldName
	extends Object
//#CM708488
//							implements TEMP
{
	//#CM708489
	//------------------------------------------------------------
	//#CM708490
	// class variables (prefix '$')
	//#CM708491
	//------------------------------------------------------------
//#CM708492
//	private String	$classVar ;

	//#CM708493
	//------------------------------------------------------------
	//#CM708494
	// fields (upper case only)
	//#CM708495
	//------------------------------------------------------------
	//#CM708496
	/**
	 * field comment.
	 */
//#CM708497
//	public static final int FIELD_VALUE = 1 ;

	//#CM708498
	//------------------------------------------------------------
	//#CM708499
	// properties (prefix 'p_')
	//#CM708500
	//------------------------------------------------------------
	//#CM708501
	/**
	 * The name of this field.
	 */
	private String p_name ;

	//#CM708502
	//------------------------------------------------------------
	//#CM708503
	// instance variables (prefix '_')
	//#CM708504
	//------------------------------------------------------------
//#CM708505
//	private String	_instanceVar ;

	//#CM708506
	//------------------------------------------------------------
	//#CM708507
	// constructors
	//#CM708508
	//------------------------------------------------------------

	//#CM708509
	/**
	 * Creating a instance with this field name.
	 *
	 * @param name String : field name.
	 */
	public FieldName(String name)
	{
		p_name = name ;
	}

	//#CM708510
	//------------------------------------------------------------
	//#CM708511
	// public methods
	//#CM708512
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM708513
	//------------------------------------------------------------
	//#CM708514
	/**
	 * get Hash code.
	 *
	 * @return int
	 */
	public int hashCode()
	{
		return p_name.hashCode() ;
	}

	//#CM708515
	/**
	 * Check same contents.
	 *
	 * @param o Object
	 * @return boolean
	 */
	public boolean equals(Object o)
	{
		if (o instanceof FieldName)
		{
			String tfname = o.toString() ;
			return p_name.equals(tfname) ;
		}
		return false ;
	}

	//#CM708516
	/**
	 * Getting the name of this field.
	 * <br>Usable for coding as "String + FieldName".
	 *
	 * @return String
	 */
	public String toString()
	{
		return getName() ;
	}

	//#CM708517
	//------------------------------------------------------------
	//#CM708518
	// accessor methods
	//#CM708519
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM708520
	//------------------------------------------------------------
	//#CM708521
	/**
	 * Getting the name of this field.
	 *
	 * @return String
	 */
	public String getName()
	{
		return p_name ;
	}

	//#CM708522
	//------------------------------------------------------------
	//#CM708523
	// package methods
	//#CM708524
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM708525
	//------------------------------------------------------------
	//#CM708526
	/**
	 * method comment.
	 * @param none.
	 * @return none.
	 * @throws none.
	 */
//#CM708527
//	void m2()
//#CM708528
//	{
//#CM708529
//	}

	//#CM708530
	//------------------------------------------------------------
	//#CM708531
	// protected methods
	//#CM708532
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM708533
	//------------------------------------------------------------
	//#CM708534
	/**
	 * method comment.
	 * @param none.
	 * @return none.
	 * @throws none.
	 */
//#CM708535
//	protected void m3()
//#CM708536
//	{
//#CM708537
//	}

	//#CM708538
	//------------------------------------------------------------
	//#CM708539
	// private methods
	//#CM708540
	//------------------------------------------------------------
	//#CM708541
	/**
	 * method comment.
	 * @param none.
	 * @return none.
	 * @throws none.
	 */
//#CM708542
//	private void m4()
//#CM708543
//	{
//#CM708544
//	}

	//#CM708545
	//------------------------------------------------------------
	//#CM708546
	// utility methods
	//#CM708547
	//------------------------------------------------------------
	//#CM708548
	/**
	 * Return Revision of this class. 
	 * @return Revision character string. 
	 */
	public static String getVersion()
	{
		return "$Id: FieldName.java,v 1.2 2006/11/15 04:25:38 kamala Exp $" ;
	}
}
