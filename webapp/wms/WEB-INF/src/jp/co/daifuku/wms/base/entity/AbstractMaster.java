// $Id: AbstractMaster.java,v 1.2 2006/11/09 08:56:14 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM702783
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.dbhandler.FieldName ;


//#CM702784
/**
 * A common class of the master entity. 
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- Change history -->
 * <tr><td nowrap>2005/11/02</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/11/09 08:56:14 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public abstract class AbstractMaster
		extends AbstractEntity
{
	//#CM702785
	//------------------------------------------------------------
	//#CM702786
	// class variables (prefix '$')
	//#CM702787
	//------------------------------------------------------------
	//#CM702788
	//	private String	$classVar ;

	//#CM702789
	//------------------------------------------------------------
	//#CM702790
	// fields (upper case only)
	//#CM702791
	//------------------------------------------------------------
	//#CM702792
	/** Deletion flag value(Being used) */

	public static final String DELETE_FLAG_LIVE = "0" ;

	//#CM702793
	/** Deletion flag value (Deleted) */

	public static final String DELETE_FLAG_DELETED = "9" ;


	//#CM702794
	/** Column definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE") ;

	//#CM702795
	/** Column definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME") ;

	//#CM702796
	/** Column definition (LASTUSEDDATE) */

	public static final FieldName LASTUSEDDATE = new FieldName("LAST_USED_DATE") ;

	//#CM702797
	/** Column definition (DELETEFLAG) */

	public static final FieldName DELETEFLAG = new FieldName("DELETE_FLAG") ;


	//#CM702798
	//------------------------------------------------------------
	//#CM702799
	// properties (prefix 'p_')
	//#CM702800
	//------------------------------------------------------------
	//#CM702801
	//	private String	p_Name ;

	//#CM702802
	//------------------------------------------------------------
	//#CM702803
	// instance variables (prefix '_')
	//#CM702804
	//------------------------------------------------------------
	//#CM702805
	//	private String	_instanceVar ;

	//#CM702806
	//------------------------------------------------------------
	//#CM702807
	// constructors
	//#CM702808
	//------------------------------------------------------------


	//#CM702809
	//------------------------------------------------------------
	//#CM702810
	// public methods
	//#CM702811
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702812
	//------------------------------------------------------------


	//#CM702813
	//------------------------------------------------------------
	//#CM702814
	// accessor methods
	//#CM702815
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702816
	//------------------------------------------------------------


	//#CM702817
	//------------------------------------------------------------
	//#CM702818
	// package methods
	//#CM702819
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702820
	//------------------------------------------------------------


	//#CM702821
	//------------------------------------------------------------
	//#CM702822
	// protected methods
	//#CM702823
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702824
	//------------------------------------------------------------


	//#CM702825
	//------------------------------------------------------------
	//#CM702826
	// private methods
	//#CM702827
	//------------------------------------------------------------


	//#CM702828
	//------------------------------------------------------------
	//#CM702829
	// utility methods
	//#CM702830
	//------------------------------------------------------------
	//#CM702831
	/**
	 * Return Rivision of this class. 
	 * @return Rivision character string. 
	 */
	public static String getVersion()
	{
		return "$Id: AbstractMaster.java,v 1.2 2006/11/09 08:56:14 suresh Exp $" ;
	}
}
