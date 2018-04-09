//#CM706871
//$Id: Stock.java,v 1.5 2006/11/16 02:15:36 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM706872
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM706873
/**
 * Entity class of STOCK
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:36 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Stock
		extends AbstractEntity
{
	//#CM706874
	//------------------------------------------------------------
	//#CM706875
	// class variables (prefix '$')
	//#CM706876
	//------------------------------------------------------------
	//#CM706877
	//	private String	$classVar ;
//#CM706878
/**
	 * Status flag (Receiving)
	 */
	public static final String STOCK_STATUSFLAG_INSTOCK = "0" ;

	//#CM706879
	/**
	 * Status flag (Waiting for Storage)
	 */
	public static final String STOCK_STATUSFLAG_RECEIVINGRESERVE = "1" ;

	//#CM706880
	/**
	 * Status flag (Stock)
	 */
	public static final String STOCK_STATUSFLAG_OCCUPIED = "2" ;

	//#CM706881
	/**
	 * Status flag (Waiting for picking)
	 */
	public static final String STOCK_STATUSFLAG_RETRIVALRESERVE = "3" ;

	//#CM706882
	/**
	 * Status flag (Waiting for sorting)
	 */
	public static final String STOCK_STATUSFLAG_PICKING = "4" ;

	//#CM706883
	/**
	 * Status flag (Waiting for Shipping)
	 */
	public static final String STOCK_STATUSFLAG_SHIPPING = "5" ;

	//#CM706884
	/**
	 * Status flag (Completed)
	 */
	public static final String STOCK_STATUSFLAG_COMPLETE = "9" ;

	//#CM706885
	/**
	 * Restorage flag (New)
	 */
	public static final int STOCK_RESTORING_NEWSTORAGE = 0 ;

	//#CM706886
	/**
	 * Restorage flag (Restorage)
	 */
	public static final int STOCK_RESTORING_RESTORAGE = 1 ;

	//#CM706887
	//------------------------------------------------------------
	//#CM706888
	// fields (upper case only)
	//#CM706889
	//------------------------------------------------------------
	//#CM706890
	/*
	 *  * Table name : STOCK
	 * Stock ID :                      STOCKID             varchar2(8)
	 * Plan unique key :               PLANUKEY            varchar2(16)
	 * Area no :                       AREANO              varchar2(16)
	 * Location no :                   LOCATIONNO          varchar2(16)
	 * Item code :                     ITEMCODE            varchar2(16)
	 * Item name :                     ITEMNAME1           varchar2(40)
	 * Stock status :                  STATUSFLAG          varchar2(1)
	 * Stock qty :                     STOCKQTY            number
	 * Allocation qty :                ALLOCATIONQTY       number
	 * Work plan qty :                 PLANQTY             number
	 * Case/Piece flag :               CASEPIECEFLAG       varchar2(1)
	 * Receiving date :                INSTOCKDATE         date
	 * Last shipping date :            LASTSHIPPINGDATE    varchar2(8)
	 * Use by date :                   USEBYDATE           varchar2(8)
	 * Lot no :                        LOTNO               varchar2(20)
	 * Plan detail comment :           PLANINFORMATION     varchar2(40)
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Consignor name :                CONSIGNORNAME       varchar2(40)
	 * Supplier code :                 SUPPLIERCODE        varchar2(16)
	 * Supplier name :                 SUPPLIERNAME1       varchar2(40)
	 * Entering case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * Restoring flag :                RESTORING           number
	 * Palette ID :                    PALETTEID           number
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM706891
	/**Table name definition*/

	public static final String TABLE_NAME = "DMSTOCK";

	//#CM706892
	/** Column Definition (STOCKID) */

	public static final FieldName STOCKID = new FieldName("STOCK_ID");

	//#CM706893
	/** Column Definition (PLANUKEY) */

	public static final FieldName PLANUKEY = new FieldName("PLAN_UKEY");

	//#CM706894
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM706895
	/** Column Definition (LOCATIONNO) */

	public static final FieldName LOCATIONNO = new FieldName("LOCATION_NO");

	//#CM706896
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM706897
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM706898
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM706899
	/** Column Definition (STOCKQTY) */

	public static final FieldName STOCKQTY = new FieldName("STOCK_QTY");

	//#CM706900
	/** Column Definition (ALLOCATIONQTY) */

	public static final FieldName ALLOCATIONQTY = new FieldName("ALLOCATION_QTY");

	//#CM706901
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM706902
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM706903
	/** Column Definition (INSTOCKDATE) */

	public static final FieldName INSTOCKDATE = new FieldName("INSTOCK_DATE");

	//#CM706904
	/** Column Definition (LASTSHIPPINGDATE) */

	public static final FieldName LASTSHIPPINGDATE = new FieldName("LAST_SHIPPING_DATE");

	//#CM706905
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM706906
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM706907
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM706908
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM706909
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM706910
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM706911
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM706912
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM706913
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM706914
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM706915
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM706916
	/** Column Definition (RESTORING) */

	public static final FieldName RESTORING = new FieldName("RESTORING");

	//#CM706917
	/** Column Definition (PALETTEID) */

	public static final FieldName PALETTEID = new FieldName("PALETTEID");

	//#CM706918
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM706919
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM706920
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM706921
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM706922
	//------------------------------------------------------------
	//#CM706923
	// properties (prefix 'p_')
	//#CM706924
	//------------------------------------------------------------
	//#CM706925
	//	private String	p_Name ;


	//#CM706926
	//------------------------------------------------------------
	//#CM706927
	// instance variables (prefix '_')
	//#CM706928
	//------------------------------------------------------------
	//#CM706929
	//	private String	_instanceVar ;

	//#CM706930
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM706931
	//------------------------------------------------------------
	//#CM706932
	// constructors
	//#CM706933
	//------------------------------------------------------------

	//#CM706934
	/**
	 * Prepare class name list and generate instance
	 */
	public Stock()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM706935
	//------------------------------------------------------------
	//#CM706936
	// accessors
	//#CM706937
	//------------------------------------------------------------

	//#CM706938
	/**
	 * Set value to Stock ID
	 * @param arg Stock ID to be set
	 */
	public void setStockId(String arg)
	{
		setValue(STOCKID, arg);
	}

	//#CM706939
	/**
	 * Fetch Stock ID
	 * @return Stock ID
	 */
	public String getStockId()
	{
		return getValue(Stock.STOCKID).toString();
	}

	//#CM706940
	/**
	 * Set value to Plan unique key
	 * @param arg Plan unique key to be set
	 */
	public void setPlanUkey(String arg)
	{
		setValue(PLANUKEY, arg);
	}

	//#CM706941
	/**
	 * Fetch Plan unique key
	 * @return Plan unique key
	 */
	public String getPlanUkey()
	{
		return getValue(Stock.PLANUKEY).toString();
	}

	//#CM706942
	/**
	 * Set value to Area no
	 * @param arg Area no to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM706943
	/**
	 * Fetch Area no
	 * @return Area no
	 */
	public String getAreaNo()
	{
		return getValue(Stock.AREANO).toString();
	}

	//#CM706944
	/**
	 * Set value to Location no
	 * @param arg Location no to be set
	 */
	public void setLocationNo(String arg)
	{
		setValue(LOCATIONNO, arg);
	}

	//#CM706945
	/**
	 * Fetch Location no
	 * @return Location no
	 */
	public String getLocationNo()
	{
		return getValue(Stock.LOCATIONNO).toString();
	}

	//#CM706946
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM706947
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(Stock.ITEMCODE).toString();
	}

	//#CM706948
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM706949
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(Stock.ITEMNAME1).toString();
	}

	//#CM706950
	/**
	 * Set value to Stock status
	 * @param arg Stock status to be set
	 */
	public void setStatusFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(STOCK_STATUSFLAG_INSTOCK))
			|| (arg.equals(STOCK_STATUSFLAG_RECEIVINGRESERVE))
			|| (arg.equals(STOCK_STATUSFLAG_OCCUPIED))
			|| (arg.equals(STOCK_STATUSFLAG_RETRIVALRESERVE))
			|| (arg.equals(STOCK_STATUSFLAG_PICKING))
			|| (arg.equals(STOCK_STATUSFLAG_SHIPPING))
			|| (arg.equals(STOCK_STATUSFLAG_COMPLETE)))
		{
			setValue(STATUSFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wStatusFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM706951
	/**
	 * Fetch Stock status
	 * @return Stock status
	 */
	public String getStatusFlag()
	{
		return getValue(Stock.STATUSFLAG).toString();
	}

	//#CM706952
	/**
	 * Set value to Stock qty
	 * @param arg Stock qty to be set
	 */
	public void setStockQty(int arg)
	{
		setValue(STOCKQTY, new Integer(arg));
	}

	//#CM706953
	/**
	 * Fetch Stock qty
	 * @return Stock qty
	 */
	public int getStockQty()
	{
		return getBigDecimal(Stock.STOCKQTY).intValue();
	}

	//#CM706954
	/**
	 * Set value to Allocation qty
	 * @param arg Allocation qty to be set
	 */
	public void setAllocationQty(int arg)
	{
		setValue(ALLOCATIONQTY, new Integer(arg));
	}

	//#CM706955
	/**
	 * Fetch Allocation qty
	 * @return Allocation qty
	 */
	public int getAllocationQty()
	{
		return getBigDecimal(Stock.ALLOCATIONQTY).intValue();
	}

	//#CM706956
	/**
	 * Set value to Work plan qty
	 * @param arg Work plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM706957
	/**
	 * Fetch Work plan qty
	 * @return Work plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(Stock.PLANQTY).intValue();
	}

	//#CM706958
	/**
	 * Set value to Case/Piece flag
	 * @param arg Case/Piece flag to be set
	 */
	public void setCasePieceFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(CASEPIECE_FLAG_NOTHING))
			|| (arg.equals(CASEPIECE_FLAG_CASE))
			|| (arg.equals(CASEPIECE_FLAG_PIECE)))
		{
			setValue(CASEPIECEFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wCasePieceFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM706959
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(Stock.CASEPIECEFLAG).toString();
	}

	//#CM706960
	/**
	 * Set value to Receiving date
	 * @param arg Receiving date to be set
	 */
	public void setInstockDate(Date arg)
	{
		setValue(INSTOCKDATE, arg);
	}

	//#CM706961
	/**
	 * Fetch Receiving date
	 * @return Receiving date
	 */
	public Date getInstockDate()
	{
try
		{
			return (Date)getValue(Stock.INSTOCKDATE);
		}
		catch (ClassCastException e)
		{
			return null;
		}
	}

	//#CM706962
	/**
	 * Set value to Last shipping date
	 * @param arg Last shipping date to be set
	 */
	public void setLastShippingDate(String arg)
	{
		setValue(LASTSHIPPINGDATE, arg);
	}

	//#CM706963
	/**
	 * Fetch Last shipping date
	 * @return Last shipping date
	 */
	public String getLastShippingDate()
	{
		return getValue(Stock.LASTSHIPPINGDATE).toString();
	}

	//#CM706964
	/**
	 * Set value to Use by date
	 * @param arg Use by date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM706965
	/**
	 * Fetch Use by date
	 * @return Use by date
	 */
	public String getUseByDate()
	{
		return getValue(Stock.USEBYDATE).toString();
	}

	//#CM706966
	/**
	 * Set value to Lot no
	 * @param arg Lot no to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM706967
	/**
	 * Fetch Lot no
	 * @return Lot no
	 */
	public String getLotNo()
	{
		return getValue(Stock.LOTNO).toString();
	}

	//#CM706968
	/**
	 * Set value to Plan detail comment
	 * @param arg Plan detail comment to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM706969
	/**
	 * Fetch Plan detail comment
	 * @return Plan detail comment
	 */
	public String getPlanInformation()
	{
		return getValue(Stock.PLANINFORMATION).toString();
	}

	//#CM706970
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM706971
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(Stock.CONSIGNORCODE).toString();
	}

	//#CM706972
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM706973
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(Stock.CONSIGNORNAME).toString();
	}

	//#CM706974
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM706975
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(Stock.SUPPLIERCODE).toString();
	}

	//#CM706976
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM706977
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(Stock.SUPPLIERNAME1).toString();
	}

	//#CM706978
	/**
	 * Set value to Entering case qty
	 * @param arg Entering case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM706979
	/**
	 * Fetch Entering case qty
	 * @return Entering case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(Stock.ENTERINGQTY).intValue();
	}

	//#CM706980
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM706981
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(Stock.BUNDLEENTERINGQTY).intValue();
	}

	//#CM706982
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM706983
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(Stock.ITF).toString();
	}

	//#CM706984
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM706985
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(Stock.BUNDLEITF).toString();
	}

	//#CM706986
	/**
	 * Set value to Restoring flag
	 * @param arg Restoring flag to be set
	 */
	public void setRestoring(int arg)
	{
		setValue(RESTORING, new Integer(arg));
	}

	//#CM706987
	/**
	 * Fetch Restoring flag
	 * @return Restoring flag
	 */
	public int getRestoring()
	{
		return getBigDecimal(Stock.RESTORING).intValue();
	}

	//#CM706988
	/**
	 * Set value to Palette ID
	 * @param arg Palette ID to be set
	 */
	public void setPaletteid(int arg)
	{
		setValue(PALETTEID, new Integer(arg));
	}

	//#CM706989
	/**
	 * Fetch Palette ID
	 * @return Palette ID
	 */
	public int getPaletteid()
	{
		return getBigDecimal(Stock.PALETTEID).intValue();
	}

	//#CM706990
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM706991
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(Stock.REGISTDATE);
	}

	//#CM706992
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM706993
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(Stock.REGISTPNAME).toString();
	}

	//#CM706994
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM706995
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Stock.LASTUPDATEDATE);
	}

	//#CM706996
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM706997
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Stock.LASTUPDATEPNAME).toString();
	}


	//#CM706998
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM706999
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(LASTUPDATEDATE, "");
		setValue(REGISTDATE, "");

		setValue(STOCKQTY, new Integer(0));
		setValue(ALLOCATIONQTY, new Integer(0));
		setValue(PLANQTY, new Integer(0));
		setValue(ENTERINGQTY, new Integer(0));
		setValue(BUNDLEENTERINGQTY, new Integer(0));
		setValue(RESTORING, new Integer(0));
		setValue(PALETTEID, new Integer(0));
	}

	/**
	 * <BR>
	 * <BR>
	 * @return
	 */
	public String[] getAutoUpdateColumnArray()
	{
		String prefix = TABLE_NAME + "." ;

		Vector autoColumn = new Vector();
		autoColumn.add(prefix + LASTUPDATEDATE);

		String[] autoColumnArray = new String[autoColumn.size()];
		autoColumn.copyInto(autoColumnArray);

		return autoColumnArray;

	}
	//------------------------------------------------------------
	//#CM707000
	// package methods
	//#CM707001
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707002
	//------------------------------------------------------------


	//#CM707003
	//------------------------------------------------------------
	//#CM707004
	// protected methods
	//#CM707005
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707006
	//------------------------------------------------------------


	//#CM707007
	//------------------------------------------------------------
	//#CM707008
	// private methods
	//#CM707009
	//------------------------------------------------------------
	//#CM707010
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + STOCKID);
		lst.add(prefix + PLANUKEY);
		lst.add(prefix + AREANO);
		lst.add(prefix + LOCATIONNO);
		lst.add(prefix + ITEMCODE);
		lst.add(prefix + ITEMNAME1);
		lst.add(prefix + STATUSFLAG);
		lst.add(prefix + STOCKQTY);
		lst.add(prefix + ALLOCATIONQTY);
		lst.add(prefix + PLANQTY);
		lst.add(prefix + CASEPIECEFLAG);
		lst.add(prefix + INSTOCKDATE);
		lst.add(prefix + LASTSHIPPINGDATE);
		lst.add(prefix + USEBYDATE);
		lst.add(prefix + LOTNO);
		lst.add(prefix + PLANINFORMATION);
		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + CONSIGNORNAME);
		lst.add(prefix + SUPPLIERCODE);
		lst.add(prefix + SUPPLIERNAME1);
		lst.add(prefix + ENTERINGQTY);
		lst.add(prefix + BUNDLEENTERINGQTY);
		lst.add(prefix + ITF);
		lst.add(prefix + BUNDLEITF);
		lst.add(prefix + RESTORING);
		lst.add(prefix + PALETTEID);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM707011
	//------------------------------------------------------------
	//#CM707012
	// utility methods
	//#CM707013
	//------------------------------------------------------------
	//#CM707014
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Stock.java,v 1.5 2006/11/16 02:15:36 suresh Exp $" ;
	}
}
