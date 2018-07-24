//#CM703373
//$Id: Customer.java,v 1.5 2006/11/16 02:15:46 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM703374
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
import jp.co.daifuku.wms.base.entity.Customer;

//#CM703375
/**
 * Entity class of CUSTOMER
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:46 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Customer
		extends AbstractMaster
{
	//#CM703376
	//------------------------------------------------------------
	//#CM703377
	// class variables (prefix '$')
	//#CM703378
	//------------------------------------------------------------
	//#CM703379
	//	private String	$classVar ;

	//#CM703380
	//------------------------------------------------------------
	//#CM703381
	// fields (upper case only)
	//#CM703382
	//------------------------------------------------------------
	//#CM703383
	/*
	 *  * Table name : CUSTOMER
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Customer code :                 CUSTOMERCODE        varchar2(16)
	 * Customer name :                 CUSTOMERNAME1       varchar2(40)
	 * Carrier code :                  CARRIERCODE         varchar2(16)
	 * Carrier name :                  CARRIERNAME         varchar2(40)
	 * Route :                         ROUTE               varchar2(4)
	 * Postal code :                   POSTALCODE          varchar2(10)
	 * Prefecture :                    PREFECTURE          varchar2(20)
	 * Address :                       ADDRESS             varchar2(200)
	 * Building name :                 ADDRESS2            varchar2(200)
	 * Contact1 :                      CONTACT1            varchar2(100)
	 * Contact2 :                      CONTACT2            varchar2(100)
	 * Contact3 :                      CONTACT3            varchar2(100)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(128)
	 * Last used date :                LASTUSEDDATE        date
	 * Delete flag :                   DELETEFLAG          varchar2(1)
	 */

	//#CM703384
	/**Table name definition*/

	public static final String TABLE_NAME = "DMCUSTOMER";

	//#CM703385
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM703386
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM703387
	/** Column Definition (CUSTOMERNAME1) */

	public static final FieldName CUSTOMERNAME1 = new FieldName("CUSTOMER_NAME1");

	//#CM703388
	/** Column Definition (CARRIERCODE) */

	public static final FieldName CARRIERCODE = new FieldName("CARRIER_CODE");

	//#CM703389
	/** Column Definition (CARRIERNAME) */

	public static final FieldName CARRIERNAME = new FieldName("CARRIER_NAME");

	//#CM703390
	/** Column Definition (ROUTE) */

	public static final FieldName ROUTE = new FieldName("ROUTE");

	//#CM703391
	/** Column Definition (POSTALCODE) */

	public static final FieldName POSTALCODE = new FieldName("POSTAL_CODE");

	//#CM703392
	/** Column Definition (PREFECTURE) */

	public static final FieldName PREFECTURE = new FieldName("PREFECTURE");

	//#CM703393
	/** Column Definition (ADDRESS) */

	public static final FieldName ADDRESS = new FieldName("ADDRESS");

	//#CM703394
	/** Column Definition (ADDRESS2) */

	public static final FieldName ADDRESS2 = new FieldName("ADDRESS2");

	//#CM703395
	/** Column Definition (CONTACT1) */

	public static final FieldName CONTACT1 = new FieldName("CONTACT1");

	//#CM703396
	/** Column Definition (CONTACT2) */

	public static final FieldName CONTACT2 = new FieldName("CONTACT2");

	//#CM703397
	/** Column Definition (CONTACT3) */

	public static final FieldName CONTACT3 = new FieldName("CONTACT3");

	//#CM703398
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM703399
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");

	//#CM703400
	/** Column Definition (LASTUSEDDATE) */

	public static final FieldName LASTUSEDDATE = new FieldName("LAST_USED_DATE");

	//#CM703401
	/** Column Definition (DELETEFLAG) */

	public static final FieldName DELETEFLAG = new FieldName("DELETE_FLAG");


	//#CM703402
	//------------------------------------------------------------
	//#CM703403
	// properties (prefix 'p_')
	//#CM703404
	//------------------------------------------------------------
	//#CM703405
	//	private String	p_Name ;


	//#CM703406
	//------------------------------------------------------------
	//#CM703407
	// instance variables (prefix '_')
	//#CM703408
	//------------------------------------------------------------
	//#CM703409
	//	private String	_instanceVar ;

	//#CM703410
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM703411
	//------------------------------------------------------------
	//#CM703412
	// constructors
	//#CM703413
	//------------------------------------------------------------

	//#CM703414
	/**
	 * Prepare class name list and generate instance
	 */
	public Customer()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM703415
	//------------------------------------------------------------
	//#CM703416
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
	//#CM703417
	//------------------------------------------------------------

	//#CM703418
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM703419
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(Customer.CONSIGNORCODE).toString();
	}

	//#CM703420
	/**
	 * Set value to Customer code
	 * @param arg Customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM703421
	/**
	 * Fetch Customer code
	 * @return Customer code
	 */
	public String getCustomerCode()
	{
		return getValue(Customer.CUSTOMERCODE).toString();
	}

	//#CM703422
	/**
	 * Set value to Customer name
	 * @param arg Customer name to be set
	 */
	public void setCustomerName1(String arg)
	{
		setValue(CUSTOMERNAME1, arg);
	}

	//#CM703423
	/**
	 * Fetch Customer name
	 * @return Customer name
	 */
	public String getCustomerName1()
	{
		return getValue(Customer.CUSTOMERNAME1).toString();
	}

	//#CM703424
	/**
	 * Set value to Carrier code
	 * @param arg Carrier code to be set
	 */
	public void setCarrierCode(String arg)
	{
		setValue(CARRIERCODE, arg);
	}

	//#CM703425
	/**
	 * Fetch Carrier code
	 * @return Carrier code
	 */
	public String getCarrierCode()
	{
		return getValue(Customer.CARRIERCODE).toString();
	}

	//#CM703426
	/**
	 * Set value to Carrier name
	 * @param arg Carrier name to be set
	 */
	public void setCarrierName(String arg)
	{
		setValue(CARRIERNAME, arg);
	}

	//#CM703427
	/**
	 * Fetch Carrier name
	 * @return Carrier name
	 */
	public String getCarrierName()
	{
		return getValue(Customer.CARRIERNAME).toString();
	}

	//#CM703428
	/**
	 * Set value to Route
	 * @param arg Route to be set
	 */
	public void setRoute(String arg)
	{
		setValue(ROUTE, arg);
	}

	//#CM703429
	/**
	 * Fetch Route
	 * @return Route
	 */
	public String getRoute()
	{
		return getValue(Customer.ROUTE).toString();
	}

	//#CM703430
	/**
	 * Set value to Postal code
	 * @param arg Postal code to be set
	 */
	public void setPostalCode(String arg)
	{
		setValue(POSTALCODE, arg);
	}

	//#CM703431
	/**
	 * Fetch Postal code
	 * @return Postal code
	 */
	public String getPostalCode()
	{
		return getValue(Customer.POSTALCODE).toString();
	}

	//#CM703432
	/**
	 * Set value to Prefecture
	 * @param arg Prefecture to be set
	 */
	public void setPrefecture(String arg)
	{
		setValue(PREFECTURE, arg);
	}

	//#CM703433
	/**
	 * Fetch Prefecture
	 * @return Prefecture
	 */
	public String getPrefecture()
	{
		return getValue(Customer.PREFECTURE).toString();
	}

	//#CM703434
	/**
	 * Set value to Address
	 * @param arg Address to be set
	 */
	public void setAddress(String arg)
	{
		setValue(ADDRESS, arg);
	}

	//#CM703435
	/**
	 * Fetch Address
	 * @return Address
	 */
	public String getAddress()
	{
		return getValue(Customer.ADDRESS).toString();
	}

	//#CM703436
	/**
	 * Set value to Building name
	 * @param arg Building name to be set
	 */
	public void setAddress2(String arg)
	{
		setValue(ADDRESS2, arg);
	}

	//#CM703437
	/**
	 * Fetch Building name
	 * @return Building name
	 */
	public String getAddress2()
	{
		return getValue(Customer.ADDRESS2).toString();
	}

	//#CM703438
	/**
	 * Set value to Contact1
	 * @param arg Contact1 to be set
	 */
	public void setContact1(String arg)
	{
		setValue(CONTACT1, arg);
	}

	//#CM703439
	/**
	 * Fetch Contact1
	 * @return Contact1
	 */
	public String getContact1()
	{
		return getValue(Customer.CONTACT1).toString();
	}

	//#CM703440
	/**
	 * Set value to Contact2
	 * @param arg Contact2 to be set
	 */
	public void setContact2(String arg)
	{
		setValue(CONTACT2, arg);
	}

	//#CM703441
	/**
	 * Fetch Contact2
	 * @return Contact2
	 */
	public String getContact2()
	{
		return getValue(Customer.CONTACT2).toString();
	}

	//#CM703442
	/**
	 * Set value to Contact3
	 * @param arg Contact3 to be set
	 */
	public void setContact3(String arg)
	{
		setValue(CONTACT3, arg);
	}

	//#CM703443
	/**
	 * Fetch Contact3
	 * @return Contact3
	 */
	public String getContact3()
	{
		return getValue(Customer.CONTACT3).toString();
	}

	//#CM703444
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM703445
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Customer.LASTUPDATEDATE);
	}

	//#CM703446
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM703447
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Customer.LASTUPDATEPNAME).toString();
	}

	//#CM703448
	/**
	 * Set value to Last used date
	 * @param arg Last used date to be set
	 */
	public void setLastUsedDate(Date arg)
	{
		setValue(LASTUSEDDATE, arg);
	}

	//#CM703449
	/**
	 * Fetch Last used date
	 * @return Last used date
	 */
	public Date getLastUsedDate()
	{
		return (Date)getValue(Customer.LASTUSEDDATE);
	}

	//#CM703450
	/**
	 * Set value to Delete flag
	 * @param arg Delete flag to be set
	 */
	public void setDeleteFlag(String arg)
	{
		setValue(DELETEFLAG, arg);
	}

	//#CM703451
	/**
	 * Fetch Delete flag
	 * @return Delete flag
	 */
	public String getDeleteFlag()
	{
		return getValue(Customer.DELETEFLAG).toString();
	}


	//#CM703452
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM703453
	//------------------------------------------------------------
	//#CM703454
	// package methods
	//#CM703455
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703456
	//------------------------------------------------------------


	//#CM703457
	//------------------------------------------------------------
	//#CM703458
	// protected methods
	//#CM703459
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703460
	//------------------------------------------------------------


	//#CM703461
	//------------------------------------------------------------
	//#CM703462
	// private methods
	//#CM703463
	//------------------------------------------------------------
	//#CM703464
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + CUSTOMERCODE);
		lst.add(prefix + CUSTOMERNAME1);
		lst.add(prefix + CARRIERCODE);
		lst.add(prefix + CARRIERNAME);
		lst.add(prefix + ROUTE);
		lst.add(prefix + POSTALCODE);
		lst.add(prefix + PREFECTURE);
		lst.add(prefix + ADDRESS);
		lst.add(prefix + ADDRESS2);
		lst.add(prefix + CONTACT1);
		lst.add(prefix + CONTACT2);
		lst.add(prefix + CONTACT3);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
		lst.add(prefix + LASTUSEDDATE);
		lst.add(prefix + DELETEFLAG);
	}


	//#CM703465
	//------------------------------------------------------------
	//#CM703466
	// utility methods
	//#CM703467
	//------------------------------------------------------------
	//#CM703468
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Customer.java,v 1.5 2006/11/16 02:15:46 suresh Exp $" ;
	}

	 /**
	  * <BR>
	  * <BR>
	  */
	 public void setInitCreateColumn()
	 {
		 setValue(LASTUPDATEDATE, "");
	 }

}
