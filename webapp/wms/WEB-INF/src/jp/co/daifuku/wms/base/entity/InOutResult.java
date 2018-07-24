//#CM704020
//$Id: InOutResult.java,v 1.6 2006/11/16 02:15:44 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM704021
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
import jp.co.daifuku.wms.base.entity.InOutResult;

//#CM704022
/**
 * Entity class of INOUTRESULT
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
 * @version $Revision: 1.6 $, $Date: 2006/11/16 02:15:44 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class InOutResult
		extends AbstractEntity
{
	//#CM704023
	//------------------------------------------------------------
	//#CM704024
	// class variables (prefix '$')
	//#CM704025
	//------------------------------------------------------------
	//#CM704026
	//	private String	$classVar ;
	//#CM704027
	/**<jp>
	 * Field which shows results flag (storage)
	 </jp>*/
	//#CM704028
	/**<en>
	 * Filed of result division (storage)
	 </en>*/
	public static final int STORAGE = 1 ;

	//#CM704029
	/**<jp>
	 * Field which shows results flag (picking)
	 </jp>*/
	//#CM704030
	/**<en>
	 * Filed of result division (retrieval)
	 </en>*/
	public static final int RETRIEVAL = 2 ;

	//#CM704031
	/**<jp>
	 * Field which shows results flag (maintenance increase)
	 </jp>*/
	//#CM704032
	/**<en>
	 * Filed of result division (maintenace increase)
	 </en>*/
	public static final int MAINTENANCE_PLUS = 3 ;

	//#CM704033
	/**<jp>
	 * Field which shows results flag (maintenance decrease)
	 </jp>*/
	//#CM704034
	/**<en>
	 * Filed of result division(maintenance decrease)
	 </en>*/
	public static final int MAINTENANCE_MINUS = 4 ;

	//#CM704035
	/**<jp>
	 * Field which shows processing flag (unprocessed)
	 </jp>*/
	//#CM704036
	/**<en>
	 * Field of process flag (unprocessed)
	 </en>*/
	public static final int UNPROCESSED = 0 ;

	//#CM704037
	/**<jp>
	 * Field which shows processing flag (Processed)
	 </jp>*/
	//#CM704038
	/**<en>
	 * Field of process flag (processed)
	 </en>*/
	public static final int PROCESSED   = 1 ;

	//#CM704039
	/**<jp>
	 * Field which shows report flag (report)
	 </jp>*/
	//#CM704040
	/**<en>
	 * Field of reporting flag to host(report)
	 </en>*/
	public static final int DO_REPORT   = 1 ;

	//#CM704041
	/**<jp>
	 * Field which shows report flag (no report)
	 </jp>*/
	//#CM704042
	/**<en>
	 * Field of reporting flag to host (no report)
	 </en>*/
	public static final int NOT_REPORT   = 0 ;

	//#CM704043
	//------------------------------------------------------------
	//#CM704044
	// fields (upper case only)
	//#CM704045
	//------------------------------------------------------------
	//#CM704046
	/*
	 *  * Table name : INOUTRESULT
	 * storage result date :           STOREDATE           date
	 * result work type :              RESULTKIND          number
	 * consignor code :                CONSIGNORCODE       varchar2(16)
	 * consignor name :                CONSIGNORNAME       varchar2(40)
	 * item key :                      ITEMKEY             varchar2(40)
	 * lot number :                    LOTNUMBER           varchar2(60)
	 * work type :                     WORKTYPE            varchar2(8)
	 * station number :                STATIONNUMBER       varchar2(16)
	 * warehouse station number :      WHSTATIONNUMBER     varchar2(16)
	 * aisle station number :          AISLESTATIONNUMBER  varchar2(16)
	 * quantity per case :             ENTERINGQTY         number
	 * quantity per bundle :           BUNDLEENTERINGQTY   number
	 * storage/picking qty :           INOUTQUANTITY       number
	 * work number :                   WORKNUMBER          varchar2(16)
	 * palette type :                  PALETTEKIND         number
	 * location number :               LOCATIONNUMBER      varchar2(16)
	 * schedule number :               SCHEDULENUMBER      varchar2(9)
	 * palette id :                    PALETTEID           number
	 * carry key :                     CARRYKEY            varchar2(16)
	 * customer code :                 CUSTOMERCODE        varchar2(40)
	 * item name :                     ITEMNAME            varchar2(80)
	 * customer name :                 CUSTOMERNAME        varchar2(40)
	 * restorage flag :                RESTORING           number
	 * storage date :                  INCOMMINGDATE       date
	 * status flag :                   STATUS              number
	 * host report flag :              REPORT              number
	 * order number :                  ORDERNUMBER         varchar2(20)
	 * line number :                   LINENUMBER          number
	 */

	//#CM704047
	/**Table name definition*/

	public static final String TABLE_NAME = "INOUTRESULT";

	//#CM704048
	/** Column Definition (STOREDATE) */

	public static final FieldName STOREDATE = new FieldName("STOREDATE");

	//#CM704049
	/** Column Definition (RESULTKIND) */

	public static final FieldName RESULTKIND = new FieldName("RESULTKIND");

	//#CM704050
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM704051
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM704052
	/** Column Definition (ITEMKEY) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM704053
	/** Column Definition (LOTNUMBER) */

	public static final FieldName LOTNUMBER = new FieldName("LOTNUMBER");

	//#CM704054
	/** Column Definition (WORKTYPE) */

	public static final FieldName WORKTYPE = new FieldName("WORKTYPE");

	//#CM704055
	/** Column Definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM704056
	/** Column Definition (WHSTATIONNUMBER) */

	public static final FieldName WHSTATIONNUMBER = new FieldName("WHSTATIONNUMBER");

	//#CM704057
	/** Column Definition (AISLESTATIONNUMBER) */

	public static final FieldName AISLESTATIONNUMBER = new FieldName("AISLESTATIONNUMBER");

	//#CM704058
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM704059
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM704060
	/** Column Definition (INOUTQUANTITY) */

	public static final FieldName INOUTQUANTITY = new FieldName("INOUTQUANTITY");

	//#CM704061
	/** Column Definition (WORKNUMBER) */

	public static final FieldName WORKNUMBER = new FieldName("WORKNUMBER");

	//#CM704062
	/** Column Definition (PALETTEKIND) */

	public static final FieldName PALETTEKIND = new FieldName("PALETTEKIND");

	//#CM704063
	/** Column Definition (LOCATIONNUMBER) */

	public static final FieldName LOCATIONNUMBER = new FieldName("LOCATIONNUMBER");

	//#CM704064
	/** Column Definition (SCHEDULENUMBER) */

	public static final FieldName SCHEDULENUMBER = new FieldName("SCHEDULENUMBER");

	//#CM704065
	/** Column Definition (PALETTEID) */

	public static final FieldName PALETTEID = new FieldName("PALETTEID");

	//#CM704066
	/** Column Definition (CARRYKEY) */

	public static final FieldName CARRYKEY = new FieldName("CARRYKEY");

	//#CM704067
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMERCODE");

	//#CM704068
	/** Column Definition (ITEMNAME) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM704069
	/** Column Definition (CUSTOMERNAME) */

	public static final FieldName CUSTOMERNAME = new FieldName("CUSTOMERNAME");

	//#CM704070
	/** Column Definition (RESTORING) */

	public static final FieldName RESTORING = new FieldName("RESTORING");

	//#CM704071
	/** Column Definition (INCOMMINGDATE) */

	public static final FieldName INCOMMINGDATE = new FieldName("INCOMMINGDATE");

	//#CM704072
	/** Column Definition (STATUS) */

	public static final FieldName STATUS = new FieldName("STATUS");

	//#CM704073
	/** Column Definition (REPORT) */

	public static final FieldName REPORT = new FieldName("REPORT");

	//#CM704074
	/** Column Definition (ORDERNUMBER) */

	public static final FieldName ORDERNUMBER = new FieldName("ORDERNUMBER");

	//#CM704075
	/** Column Definition (LINENUMBER) */

	public static final FieldName LINENUMBER = new FieldName("LINENUMBER");


	//#CM704076
	//------------------------------------------------------------
	//#CM704077
	// properties (prefix 'p_')
	//#CM704078
	//------------------------------------------------------------
	//#CM704079
	//	private String	p_Name ;


	//#CM704080
	//------------------------------------------------------------
	//#CM704081
	// instance variables (prefix '_')
	//#CM704082
	//------------------------------------------------------------
	//#CM704083
	//	private String	_instanceVar ;

	//#CM704084
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM704085
	//------------------------------------------------------------
	//#CM704086
	// constructors
	//#CM704087
	//------------------------------------------------------------

	//#CM704088
	/**
	 * Prepare class name list and generate instance
	 */
	public InOutResult()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM704089
	//------------------------------------------------------------
	//#CM704090
	// accessors
	//#CM704091
	//------------------------------------------------------------

	//#CM704092
	/**
	 * Set value to storage result date
	 * @param arg storage result date to be set
	 */
	public void setStoreDate(Date arg)
	{
		setValue(STOREDATE, arg);
	}

	//#CM704093
	/**
	 * Fetch storage result date
	 * @return storage result date
	 */
	public Date getStoreDate()
	{
		return (Date)getValue(InOutResult.STOREDATE);
	}

	//#CM704094
	/**
	 * Set value to result work type
	 * @param arg result work type to be set
	 */
	public void setResultKind(int arg)
	{
		setValue(RESULTKIND, new Integer(arg));
	}

	//#CM704095
	/**
	 * Fetch result work type
	 * @return result work type
	 */
	public int getResultKind()
	{
		return getBigDecimal(InOutResult.RESULTKIND).intValue();
	}

	//#CM704096
	/**
	 * Set value to consignor code
	 * @param arg consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM704097
	/**
	 * Fetch consignor code
	 * @return consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(InOutResult.CONSIGNORCODE).toString();
	}

	//#CM704098
	/**
	 * Set value to consignor name
	 * @param arg consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM704099
	/**
	 * Fetch consignor name
	 * @return consignor name
	 */
	public String getConsignorName()
	{
		return getValue(InOutResult.CONSIGNORNAME).toString();
	}

	//#CM704100
	/**
	 * Set value to item key
	 * @param arg item key to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM704101
	/**
	 * Fetch item key
	 * @return item key
	 */
	public String getItemCode()
	{
		return getValue(InOutResult.ITEMCODE).toString();
	}

	//#CM704102
	/**
	 * Set value to lot number
	 * @param arg lot number to be set
	 */
	public void setLotNumber(String arg)
	{
		setValue(LOTNUMBER, arg);
	}

	//#CM704103
	/**
	 * Fetch lot number
	 * @return lot number
	 */
	public String getLotNumber()
	{
		return getValue(InOutResult.LOTNUMBER).toString();
	}

	//#CM704104
	/**
	 * Set value to work type
	 * @param arg work type to be set
	 */
	public void setWorkType(String arg)
	{
		setValue(WORKTYPE, arg);
	}

	//#CM704105
	/**
	 * Fetch work type
	 * @return work type
	 */
	public String getWorkType()
	{
		return getValue(InOutResult.WORKTYPE).toString();
	}

	//#CM704106
	/**
	 * Set value to station number
	 * @param arg station number to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM704107
	/**
	 * Fetch station number
	 * @return station number
	 */
	public String getStationNumber()
	{
		return getValue(InOutResult.STATIONNUMBER).toString();
	}

	//#CM704108
	/**
	 * Set value to warehouse station number
	 * @param arg warehouse station number to be set
	 */
	public void setWHStationNumber(String arg)
	{
		setValue(WHSTATIONNUMBER, arg);
	}

	//#CM704109
	/**
	 * Fetch warehouse station number
	 * @return warehouse station number
	 */
	public String getWHStationNumber()
	{
		return getValue(InOutResult.WHSTATIONNUMBER).toString();
	}

	//#CM704110
	/**
	 * Set value to aisle station number
	 * @param arg aisle station number to be set
	 */
	public void setAisleStationNumber(String arg)
	{
		setValue(AISLESTATIONNUMBER, arg);
	}

	//#CM704111
	/**
	 * Fetch aisle station number
	 * @return aisle station number
	 */
	public String getAisleStationNumber()
	{
		return getValue(InOutResult.AISLESTATIONNUMBER).toString();
	}

	//#CM704112
	/**
	 * Set value to quantity per case
	 * @param arg quantity per case to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM704113
	/**
	 * Fetch quantity per case
	 * @return quantity per case
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(InOutResult.ENTERINGQTY).intValue();
	}

	//#CM704114
	/**
	 * Set value to quantity per bundle
	 * @param arg quantity per bundle to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM704115
	/**
	 * Fetch quantity per bundle
	 * @return quantity per bundle
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(InOutResult.BUNDLEENTERINGQTY).intValue();
	}

	//#CM704116
	/**
	 * Set value to storage/picking qty
	 * @param arg storage/picking qty to be set
	 */
	public void setInOutQuantity(int arg)
	{
		setValue(INOUTQUANTITY, new Integer(arg));
	}

	//#CM704117
	/**
	 * Fetch storage/picking qty
	 * @return storage/picking qty
	 */
	public int getInOutQuantity()
	{
		return getBigDecimal(InOutResult.INOUTQUANTITY).intValue();
	}

	//#CM704118
	/**
	 * Set value to work number
	 * @param arg work number to be set
	 */
	public void setWorkNumber(String arg)
	{
		setValue(WORKNUMBER, arg);
	}

	//#CM704119
	/**
	 * Fetch work number
	 * @return work number
	 */
	public String getWorkNumber()
	{
		return getValue(InOutResult.WORKNUMBER).toString();
	}

	//#CM704120
	/**
	 * Set value to palette type
	 * @param arg palette type to be set
	 */
	public void setPaletteKind(int arg)
	{
		setValue(PALETTEKIND, new Integer(arg));
	}

	//#CM704121
	/**
	 * Fetch palette type
	 * @return palette type
	 */
	public int getPaletteKind()
	{
		return getBigDecimal(InOutResult.PALETTEKIND).intValue();
	}

	//#CM704122
	/**
	 * Set value to location number
	 * @param arg location number to be set
	 */
	public void setLocationNumber(String arg)
	{
		setValue(LOCATIONNUMBER, arg);
	}

	//#CM704123
	/**
	 * Fetch location number
	 * @return location number
	 */
	public String getLocationNumber()
	{
		return getValue(InOutResult.LOCATIONNUMBER).toString();
	}

	//#CM704124
	/**
	 * Set value to schedule number
	 * @param arg schedule number to be set
	 */
	public void setScheduleNumber(String arg)
	{
		setValue(SCHEDULENUMBER, arg);
	}

	//#CM704125
	/**
	 * Fetch schedule number
	 * @return schedule number
	 */
	public String getScheduleNumber()
	{
		return getValue(InOutResult.SCHEDULENUMBER).toString();
	}

	//#CM704126
	/**
	 * Set value to palette id
	 * @param arg palette id to be set
	 */
	public void setPaletteId(int arg)
	{
		setValue(PALETTEID, new Integer(arg));
	}

	//#CM704127
	/**
	 * Fetch palette id
	 * @return palette id
	 */
	public int getPaletteId()
	{
		return getBigDecimal(InOutResult.PALETTEID).intValue();
	}

	//#CM704128
	/**
	 * Set value to carry key
	 * @param arg carry key to be set
	 */
	public void setCarryKey(String arg)
	{
		setValue(CARRYKEY, arg);
	}

	//#CM704129
	/**
	 * Fetch carry key
	 * @return carry key
	 */
	public String getCarryKey()
	{
		return getValue(InOutResult.CARRYKEY).toString();
	}

	//#CM704130
	/**
	 * Set value to customer code
	 * @param arg customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM704131
	/**
	 * Fetch customer code
	 * @return customer code
	 */
	public String getCustomerCode()
	{
		return getValue(InOutResult.CUSTOMERCODE).toString();
	}

	//#CM704132
	/**
	 * Set value to item name
	 * @param arg item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM704133
	/**
	 * Fetch item name
	 * @return item name
	 */
	public String getItemName1()
	{
		return getValue(InOutResult.ITEMNAME1).toString();
	}

	//#CM704134
	/**
	 * Set value to customer name
	 * @param arg customer name to be set
	 */
	public void setCustomerName(String arg)
	{
		setValue(CUSTOMERNAME, arg);
	}

	//#CM704135
	/**
	 * Fetch customer name
	 * @return customer name
	 */
	public String getCustomerName()
	{
		return getValue(InOutResult.CUSTOMERNAME).toString();
	}

	//#CM704136
	/**
	 * Set value to restorage flag
	 * @param arg restorage flag to be set
	 */
	public void setReStoring(int arg)
	{
		setValue(RESTORING, new Integer(arg));
	}

	//#CM704137
	/**
	 * Fetch restorage flag
	 * @return restorage flag
	 */
	public int getReStoring()
	{
		return getBigDecimal(InOutResult.RESTORING).intValue();
	}

	//#CM704138
	/**
	 * Set value to storage date
	 * @param arg storage date to be set
	 */
	public void setInCommingDate(Date arg)
	{
		setValue(INCOMMINGDATE, arg);
	}

	//#CM704139
	/**
	 * Fetch storage date
	 * @return storage date
	 */
	public Date getInCommingDate()
	{
		return (Date)getValue(InOutResult.INCOMMINGDATE);
	}

	//#CM704140
	/**
	 * Set value to status flag
	 * @param arg status flag to be set
	 */
	public void setStatus(int arg)
	{
		setValue(STATUS, new Integer(arg));
	}

	//#CM704141
	/**
	 * Fetch status flag
	 * @return status flag
	 */
	public int getStatus()
	{
		return getBigDecimal(InOutResult.STATUS).intValue();
	}

	//#CM704142
	/**
	 * Set value to host report flag
	 * @param arg host report flag to be set
	 */
	public void setReport(int arg)
	{
		setValue(REPORT, new Integer(arg));
	}

	//#CM704143
	/**
	 * Fetch host report flag
	 * @return host report flag
	 */
	public int getReport()
	{
		return getBigDecimal(InOutResult.REPORT).intValue();
	}

	//#CM704144
	/**
	 * Set value to order number
	 * @param arg order number to be set
	 */
	public void setOrderNumber(String arg)
	{
		setValue(ORDERNUMBER, arg);
	}

	//#CM704145
	/**
	 * Fetch order number
	 * @return order number
	 */
	public String getOrderNumber()
	{
		return getValue(InOutResult.ORDERNUMBER).toString();
	}

	//#CM704146
	/**
	 * Set value to line number
	 * @param arg line number to be set
	 */
	public void setLineNumber(int arg)
	{
		setValue(LINENUMBER, new Integer(arg));
	}

	//#CM704147
	/**
	 * Fetch line number
	 * @return line number
	 */
	public int getLineNumber()
	{
		return getBigDecimal(InOutResult.LINENUMBER).intValue();
	}


	//#CM704148
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM704149
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(RESULTKIND, new Integer(0));
		setValue(ENTERINGQTY, new Integer(0));
		setValue(BUNDLEENTERINGQTY, new Integer(0));
		setValue(INOUTQUANTITY, new Integer(0));
		setValue(PALETTEKIND, new Integer(0));
		setValue(PALETTEID, new Integer(0));
		setValue(RESTORING, new Integer(0));
		setValue(STATUS, new Integer(0));
		setValue(REPORT, new Integer(DO_REPORT));
		setValue(LINENUMBER, new Integer(0));
	}
	//------------------------------------------------------------
	//#CM704150
	// package methods
	//#CM704151
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704152
	//------------------------------------------------------------


	//#CM704153
	//------------------------------------------------------------
	//#CM704154
	// protected methods
	//#CM704155
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704156
	//------------------------------------------------------------


	//#CM704157
	//------------------------------------------------------------
	//#CM704158
	// private methods
	//#CM704159
	//------------------------------------------------------------
	//#CM704160
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + STOREDATE);
		lst.add(prefix + RESULTKIND);
		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + CONSIGNORNAME);
		lst.add(prefix + ITEMCODE);
		lst.add(prefix + LOTNUMBER);
		lst.add(prefix + WORKTYPE);
		lst.add(prefix + STATIONNUMBER);
		lst.add(prefix + WHSTATIONNUMBER);
		lst.add(prefix + AISLESTATIONNUMBER);
		lst.add(prefix + ENTERINGQTY);
		lst.add(prefix + BUNDLEENTERINGQTY);
		lst.add(prefix + INOUTQUANTITY);
		lst.add(prefix + WORKNUMBER);
		lst.add(prefix + PALETTEKIND);
		lst.add(prefix + LOCATIONNUMBER);
		lst.add(prefix + SCHEDULENUMBER);
		lst.add(prefix + PALETTEID);
		lst.add(prefix + CARRYKEY);
		lst.add(prefix + CUSTOMERCODE);
		lst.add(prefix + ITEMNAME1);
		lst.add(prefix + CUSTOMERNAME);
		lst.add(prefix + RESTORING);
		lst.add(prefix + INCOMMINGDATE);
		lst.add(prefix + STATUS);
		lst.add(prefix + REPORT);
		lst.add(prefix + ORDERNUMBER);
		lst.add(prefix + LINENUMBER);
	}


	//#CM704161
	//------------------------------------------------------------
	//#CM704162
	// utility methods
	//#CM704163
	//------------------------------------------------------------
	//#CM704164
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: InOutResult.java,v 1.6 2006/11/16 02:15:44 suresh Exp $" ;
	}
}
