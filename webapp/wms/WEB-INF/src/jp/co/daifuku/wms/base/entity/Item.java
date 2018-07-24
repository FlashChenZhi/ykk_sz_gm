//#CM704462
//$Id: Item.java,v 1.5 2006/11/16 02:15:43 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM704463
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Item;

//#CM704464
/**
 * Entity class of ITEM
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- update history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:43 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Item
		extends AbstractMaster
{
	//#CM704465
	//------------------------------------------------------------
	//#CM704466
	// class variables (prefix '$')
	//#CM704467
	//------------------------------------------------------------
	//#CM704468
	//	private String	$classVar ;

	//#CM704469
	//------------------------------------------------------------
	//#CM704470
	// fields (upper case only)
	//#CM704471
	//------------------------------------------------------------
	//#CM704472
	/*
	 *  * Table name : ITEM
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Item code :                     ITEMCODE            varchar2(16)
	 * Jan code :                      JAN                 varchar2(13)
	 * Item name :                     ITEMNAME1           varchar2(40)
	 * Entering case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * Item category code :            ITEMCATEGORY        varchar2(4)
	 * Upper qty :                     UPPERQTY            number
	 * Lower qty :                     LOWERQTY            number
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(128)
	 * Last used date :                LASTUSEDDATE        date
	 * Delete flag :                   DELETEFLAG          varchar2(1)
	 */

	//#CM704473
	/**Table name definition*/

	public static final String TABLE_NAME = "DMITEM";

	//#CM704474
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM704475
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM704476
	/** Column Definition (JAN) */

	public static final FieldName JAN = new FieldName("JAN");

	//#CM704477
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM704478
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM704479
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM704480
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM704481
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM704482
	/** Column Definition (ITEMCATEGORY) */

	public static final FieldName ITEMCATEGORY = new FieldName("ITEM_CATEGORY");

	//#CM704483
	/** Column Definition (UPPERQTY) */

	public static final FieldName UPPERQTY = new FieldName("UPPER_QTY");

	//#CM704484
	/** Column Definition (LOWERQTY) */

	public static final FieldName LOWERQTY = new FieldName("LOWER_QTY");

	//#CM704485
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM704486
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");

	//#CM704487
	/** Column Definition (LASTUSEDDATE) */

	public static final FieldName LASTUSEDDATE = new FieldName("LAST_USED_DATE");

	//#CM704488
	/** Column Definition (DELETEFLAG) */

	public static final FieldName DELETEFLAG = new FieldName("DELETE_FLAG");


	//#CM704489
	//------------------------------------------------------------
	//#CM704490
	// properties (prefix 'p_')
	//#CM704491
	//------------------------------------------------------------
	//#CM704492
	//	private String	p_Name ;


	//#CM704493
	//------------------------------------------------------------
	//#CM704494
	// instance variables (prefix '_')
	//#CM704495
	//------------------------------------------------------------
	//#CM704496
	//	private String	_instanceVar ;

	//#CM704497
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM704498
	//------------------------------------------------------------
	//#CM704499
	// constructors
	//#CM704500
	//------------------------------------------------------------

	//#CM704501
	/**
	 * Prepare class name list and generate instance
	 */
	public Item()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM704502
	//------------------------------------------------------------
	//#CM704503
	/**
	 * @see jp.co.daifuku.wms.base.entity.AbstractEntity#getAutoUpdateColumnArray()
	 */
	public String[] getAutoUpdateColumnArray()
	{

		String prefix = TABLE_NAME + "." ;
		String[] autoColumns = {
				prefix + LASTUPDATEDATE
		} ;
		return autoColumns ;
	}
	// accessors
	//#CM704504
	//------------------------------------------------------------

	//#CM704505
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM704506
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(Item.CONSIGNORCODE).toString();
	}

	//#CM704507
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM704508
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(Item.ITEMCODE).toString();
	}

	//#CM704509
	/**
	 * Set value to Jan code
	 * @param arg Jan code to be set
	 */
	public void setJAN(String arg)
	{
		setValue(JAN, arg);
	}

	//#CM704510
	/**
	 * Fetch Jan code
	 * @return Jan code
	 */
	public String getJAN()
	{
		return getValue(Item.JAN).toString();
	}

	//#CM704511
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM704512
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(Item.ITEMNAME1).toString();
	}

	//#CM704513
	/**
	 * Set value to Entering case qty
	 * @param arg Entering case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM704514
	/**
	 * Fetch Entering case qty
	 * @return Entering case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(Item.ENTERINGQTY).intValue();
	}

	//#CM704515
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM704516
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(Item.BUNDLEENTERINGQTY).intValue();
	}

	//#CM704517
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setITF(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM704518
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getITF()
	{
		return getValue(Item.ITF).toString();
	}

	//#CM704519
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM704520
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(Item.BUNDLEITF).toString();
	}

	//#CM704521
	/**
	 * Set value to Item category code
	 * @param arg Item category code to be set
	 */
	public void setItemCategory(String arg)
	{
		setValue(ITEMCATEGORY, arg);
	}

	//#CM704522
	/**
	 * Fetch Item category code
	 * @return Item category code
	 */
	public String getItemCategory()
	{
		return getValue(Item.ITEMCATEGORY).toString();
	}

	//#CM704523
	/**
	 * Set value to Upper qty
	 * @param arg Upper qty to be set
	 */
	public void setUpperQty(int arg)
	{
		setValue(UPPERQTY, new Integer(arg));
	}

	//#CM704524
	/**
	 * Fetch Upper qty
	 * @return Upper qty
	 */
	public int getUpperQty()
	{
		return getBigDecimal(Item.UPPERQTY).intValue();
	}

	//#CM704525
	/**
	 * Set value to Lower qty
	 * @param arg Lower qty to be set
	 */
	public void setLowerQty(int arg)
	{
		setValue(LOWERQTY, new Integer(arg));
	}

	//#CM704526
	/**
	 * Fetch Lower qty
	 * @return Lower qty
	 */
	public int getLowerQty()
	{
		return getBigDecimal(Item.LOWERQTY).intValue();
	}

	//#CM704527
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM704528
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Item.LASTUPDATEDATE);
	}

	//#CM704529
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM704530
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Item.LASTUPDATEPNAME).toString();
	}

	//#CM704531
	/**
	 * Set value to Last used date
	 * @param arg Last used date to be set
	 */
	public void setLastUsedDate(Date arg)
	{
		setValue(LASTUSEDDATE, arg);
	}

	//#CM704532
	/**
	 * Fetch Last used date
	 * @return Last used date
	 */
	public Date getLastUsedDate()
	{
		return (Date)getValue(Item.LASTUSEDDATE);
	}

	//#CM704533
	/**
	 * Set value to Delete flag
	 * @param arg Delete flag to be set
	 */
	public void setDeleteFlag(String arg)
	{
		setValue(DELETEFLAG, arg);
	}

	//#CM704534
	/**
	 * Fetch Delete flag
	 * @return Delete flag
	 */
	public String getDeleteFlag()
	{
		return getValue(Item.DELETEFLAG).toString();
	}


	//#CM704535
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM704536
	//------------------------------------------------------------
	//#CM704537
	// package methods
	//#CM704538
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704539
	//------------------------------------------------------------


	//#CM704540
	//------------------------------------------------------------
	//#CM704541
	// protected methods
	//#CM704542
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704543
	//------------------------------------------------------------


	//#CM704544
	//------------------------------------------------------------
	//#CM704545
	// private methods
	//#CM704546
	//------------------------------------------------------------
	//#CM704547
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + ITEMCODE);
		lst.add(prefix + JAN);
		lst.add(prefix + ITEMNAME1);
		lst.add(prefix + ENTERINGQTY);
		lst.add(prefix + BUNDLEENTERINGQTY);
		lst.add(prefix + ITF);
		lst.add(prefix + BUNDLEITF);
		lst.add(prefix + ITEMCATEGORY);
		lst.add(prefix + UPPERQTY);
		lst.add(prefix + LOWERQTY);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
		lst.add(prefix + LASTUSEDDATE);
		lst.add(prefix + DELETEFLAG);
	}


	//#CM704548
	//------------------------------------------------------------
	//#CM704549
	// utility methods
	//#CM704550
	//------------------------------------------------------------
	//#CM704551
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Item.java,v 1.5 2006/11/16 02:15:43 suresh Exp $" ;
	}
	/**
	 * <BR>
	 * <BR>
	*/
	public void setInitCreateColumn()
	{
		setValue(LASTUPDATEDATE, "");

		setValue(ENTERINGQTY, new Integer(0));
		setValue(BUNDLEENTERINGQTY, new Integer(0));
		setValue(UPPERQTY, new Integer(0));
		setValue(LOWERQTY, new Integer(0));
	}

}
