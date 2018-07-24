//#CM41882
//$Id: ASStock.java,v 1.2 2006/10/26 08:23:43 suresh Exp $
package jp.co.daifuku.wms.asrs.entity;

//#CM41883
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM41884
/**
 * The entity class which maintains the inventory information on the retrieval result. <BR>
 * The string is applied with it in addition to the inventory information, and turn over and maintain and palette information.
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- Change history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:23:43 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */
public class ASStock extends Stock
{
	//#CM41885
	//------------------------------------------------------------
	//#CM41886
	// class variables (prefix '$')
	//#CM41887
	//------------------------------------------------------------
	//#CM41888
	//	private String	$classVar ;

	//#CM41889
	//------------------------------------------------------------
	//#CM41890
	// fields (upper case only)
	//#CM41891
	//------------------------------------------------------------
	//#CM41892
	/*
	 * Table name: 
	 * Station No :                STATIONNUMBER       varchar2(16)
	 */
	//#CM41893
	/** Column definition (SHELF.STATIONNUMBER) */

	public static final FieldName SHELF_STATIONNUMBER = new FieldName("S_STATIONNUMBER");

	//#CM41894
	/** Column definition (SHELF.STATUS) */

	public static final FieldName SHELF_STATUS = new FieldName("S_STATUS");

	//#CM41895
	/** Column definition (SHELF.PRESENCE) */

	public static final FieldName SHELF_PRESENCE = new FieldName("S_PRESENCE");

	//#CM41896
	/** Column definition (SHELF.ACCESSNGFLAG) */

	public static final FieldName SHELF_ACCESSNGFLAG = new FieldName("S_ACCESSNGFLAG");
	
	//#CM41897
	/** Column definition (PALETTE.EMPTY) */

	public static final FieldName PALETTE_EMPTY = new FieldName("P_EMPTY");

	//#CM41898
	/** Column definition (PALETTE.STATUS) */

	public static final FieldName PALETTE_STATUS = new FieldName("P_STATUS");

	//#CM41899
	//------------------------------------------------------------
	//#CM41900
	// properties (prefix 'p_')
	//#CM41901
	//------------------------------------------------------------

	//#CM41902
	//------------------------------------------------------------
	//#CM41903
	// instance variables (prefix '_')
	//#CM41904
	//------------------------------------------------------------
	//#CM41905
	//	private String	_instanceVar ;

	//#CM41906
	//------------------------------------------------------------
	//#CM41907
	// constructors
	//#CM41908
	//------------------------------------------------------------

	//#CM41909
	/**
	 * Prepare the column name list and generate the instance.
	 */
	public ASStock()
	{
		super() ;
		prepare() ;
	}

	//#CM41910
	/**
	 * Acquire Shelf station no.
	 * @return Shelf Station No.
	 */
	public String getStationNumber()
	{
		return getValue(ASStock.SHELF_STATIONNUMBER).toString();
	}

	//#CM41911
	/**
	 * Acquire Shelf status.
	 * @return Shelf status
	 */
	public int getShelfStatus()
	{
		return getBigDecimal(SHELF_STATUS).intValue();
	}

	//#CM41912
	/**
	 * Acqurie Shelf status of shelf.
	 * @return Shelf status of shelf
	 */
	public int getShelfPresence()
	{
		return getBigDecimal(SHELF_PRESENCE).intValue();
	}

	//#CM41913
	/**
	 * Acquire the shelf flag which cannot access the shelf.
	 * @return Shelf flag which cannot access shelf
	 */
	public int getAccessNGFlag()
	{
		return getBigDecimal(SHELF_ACCESSNGFLAG).intValue();
	}

	//#CM41914
	/**
	 * Acquire the state of the palette of an empty palette.
	 * @return State of palette of empty palette
	 */
	public int getPaletteEmpty()
	{
		return getBigDecimal(PALETTE_EMPTY).intValue();
	}

	//#CM41915
	/**
	 * Acquire the stock of the palette.
	 * @return the stock of the palette
	 */
	public int getPaletteStatus()
	{
		return getBigDecimal(PALETTE_STATUS).intValue();
	}

	//#CM41916
	//------------------------------------------------------------
	//#CM41917
	// package methods
	//#CM41918
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM41919
	//------------------------------------------------------------

	//#CM41920
	//------------------------------------------------------------
	//#CM41921
	// protected methods
	//#CM41922
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM41923
	//------------------------------------------------------------

	//#CM41924
	//------------------------------------------------------------
	//#CM41925
	// private methods
	//#CM41926
	//------------------------------------------------------------
	//#CM41927
	/**
	 * Prepare the column name list.(SearchKey, AlterKey use)
	 * To match with Column definition.
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		//#CM41928
		// To acquire it with String in getColumn
		//#CM41929
		// Here, convert it into the String type.
		lst.add(SHELF_STATIONNUMBER.toString());
		lst.add(SHELF_STATUS.toString());
		lst.add(SHELF_PRESENCE.toString());
		lst.add(SHELF_ACCESSNGFLAG.toString());
		lst.add(PALETTE_EMPTY.toString());
		lst.add(PALETTE_STATUS.toString());
	}

	//#CM41930
	//------------------------------------------------------------
	//#CM41931
	// utility methods
	//#CM41932
	//------------------------------------------------------------
	//#CM41933
	/**
	 * Return Rivision of this class.
	 * @return Rivision character string.
	 */
	public static String getVersion()
	{
		return "$Id: ASStock.java,v 1.2 2006/10/26 08:23:43 suresh Exp $" ;
	}	
}
