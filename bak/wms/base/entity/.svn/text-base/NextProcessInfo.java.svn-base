//#CM704910
//$Id: NextProcessInfo.java,v 1.5 2006/11/16 02:15:41 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM704911
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
import jp.co.daifuku.wms.base.entity.NextProcessInfo;

//#CM704912
/**
 * Entity class of NEXTPROC
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:41 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class NextProcessInfo
		extends AbstractEntity
{
	//#CM704913
	//------------------------------------------------------------
	//#CM704914
	// class variables (prefix '$')
	//#CM704915
	//------------------------------------------------------------
	//#CM704916
	//	private String	$classVar ;
//#CM704917
/**
	 * Status flag (Unprocessed)
	 */
	public static final String STATUS_FLAG_UNPROCESSING = "0" ;

	//#CM704918
	/**
	 * Status flag (Processing)
	 */
	public static final String STATUS_FLAG_PROCESSING = "1" ;

	//#CM704919
	/**
	 * Status flag (Processed)
	 */
	public static final String STATUS_FLAG_PROCESSING_FINISH = "2" ;

	//#CM704920
	/**
	 * Status flag (Delete)
	 */
	public static final String STATUS_FLAG_DELETE = "9" ;

	//#CM704921
	//------------------------------------------------------------
	//#CM704922
	// fields (upper case only)
	//#CM704923
	//------------------------------------------------------------
	//#CM704924
	/*
	 *  * Table name : NEXTPROC
	 * Next process unique key :       NEXTPROCUKEY        varchar2(16)
	 * Plan unique key :               PLANUKEY            varchar2(16)
	 * Receiving plan unique key :     INSTOCKPLANUKEY     varchar2(16)
	 * Storage plan unique key :       STORAGEPLANUKEY     varchar2(16)
	 * Retrieval plan unique key :     RETRIEVALPLANUKEY   varchar2(16)
	 * Sorting plan unique key :       SORTINGPLANUKEY     varchar2(16)
	 * Shipping plan unique key :      SHIPPINGPLANUKEY    varchar2(16)
	 * Work type :                     WORKKIND            varchar2(2)
	 * Work plan qty :                 PLANQTY             number
	 * Work result qty :               RESULTQTY           number
	 * Work shortage qty :             SHORTAGEQTY         number
	 * Area no :                       AREANO              varchar2(16)
	 * Location no :                   LOCATIONNO          varchar2(16)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Receiving plan date :           INSTPLANDATE        varchar2(8)
	 * Supplier code :                 SUPPLIERCODE        varchar2(16)
	 * Receiving ticket no :           INSTOCKTICKETNO     varchar2(16)
	 * Receiving line no :             INSTOCKLINENO       number
	 * Item code :                     ITEMCODE            varchar2(16)
	 * TC/DC flag :                    TCDCFLAG            varchar2(1)
	 * Shipping plan date :            SHIPPLANDATE        varchar2(8)
	 * Customer code :                 CUSTOMERCODE        varchar2(16)
	 * Shipping ticket no :            SHIPPINGTICKETNO    varchar2(16)
	 * Shipping line no :              SHIPPINGLINENO      number
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM704925
	/**Table name definition*/

	public static final String TABLE_NAME = "DNNEXTPROC";

	//#CM704926
	/** Column Definition (NEXTPROCUKEY) */

	public static final FieldName NEXTPROCUKEY = new FieldName("NEXT_PROC_UKEY");

	//#CM704927
	/** Column Definition (PLANUKEY) */

	public static final FieldName PLANUKEY = new FieldName("PLAN_UKEY");

	//#CM704928
	/** Column Definition (INSTOCKPLANUKEY) */

	public static final FieldName INSTOCKPLANUKEY = new FieldName("INSTOCK_PLAN_UKEY");

	//#CM704929
	/** Column Definition (STORAGEPLANUKEY) */

	public static final FieldName STORAGEPLANUKEY = new FieldName("STORAGE_PLAN_UKEY");

	//#CM704930
	/** Column Definition (RETRIEVALPLANUKEY) */

	public static final FieldName RETRIEVALPLANUKEY = new FieldName("RETRIEVAL_PLAN_UKEY");

	//#CM704931
	/** Column Definition (SORTINGPLANUKEY) */

	public static final FieldName SORTINGPLANUKEY = new FieldName("SORTING_PLAN_UKEY");

	//#CM704932
	/** Column Definition (SHIPPINGPLANUKEY) */

	public static final FieldName SHIPPINGPLANUKEY = new FieldName("SHIPPING_PLAN_UKEY");

	//#CM704933
	/** Column Definition (WORKKIND) */

	public static final FieldName WORKKIND = new FieldName("WORK_KIND");

	//#CM704934
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM704935
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM704936
	/** Column Definition (SHORTAGEQTY) */

	public static final FieldName SHORTAGEQTY = new FieldName("SHORTAGE_QTY");

	//#CM704937
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM704938
	/** Column Definition (LOCATIONNO) */

	public static final FieldName LOCATIONNO = new FieldName("LOCATION_NO");

	//#CM704939
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM704940
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM704941
	/** Column Definition (INSTPLANDATE) */

	public static final FieldName INSTPLANDATE = new FieldName("INST_PLAN_DATE");

	//#CM704942
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM704943
	/** Column Definition (INSTOCKTICKETNO) */

	public static final FieldName INSTOCKTICKETNO = new FieldName("INSTOCK_TICKET_NO");

	//#CM704944
	/** Column Definition (INSTOCKLINENO) */

	public static final FieldName INSTOCKLINENO = new FieldName("INSTOCK_LINE_NO");

	//#CM704945
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM704946
	/** Column Definition (TCDCFLAG) */

	public static final FieldName TCDCFLAG = new FieldName("TCDC_FLAG");

	//#CM704947
	/** Column Definition (SHIPPLANDATE) */

	public static final FieldName SHIPPLANDATE = new FieldName("SHIP_PLAN_DATE");

	//#CM704948
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM704949
	/** Column Definition (SHIPPINGTICKETNO) */

	public static final FieldName SHIPPINGTICKETNO = new FieldName("SHIPPING_TICKET_NO");

	//#CM704950
	/** Column Definition (SHIPPINGLINENO) */

	public static final FieldName SHIPPINGLINENO = new FieldName("SHIPPING_LINE_NO");

	//#CM704951
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM704952
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM704953
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM704954
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM704955
	//------------------------------------------------------------
	//#CM704956
	// properties (prefix 'p_')
	//#CM704957
	//------------------------------------------------------------
	//#CM704958
	//	private String	p_Name ;


	//#CM704959
	//------------------------------------------------------------
	//#CM704960
	// instance variables (prefix '_')
	//#CM704961
	//------------------------------------------------------------
	//#CM704962
	//	private String	_instanceVar ;

	//#CM704963
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM704964
	//------------------------------------------------------------
	//#CM704965
	// constructors
	//#CM704966
	//------------------------------------------------------------

	//#CM704967
	/**
	 * Prepare class name list and generate instance
	 */
	public NextProcessInfo()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM704968
	//------------------------------------------------------------
	//#CM704969
	// accessors
	//#CM704970
	//------------------------------------------------------------

	//#CM704971
	/**
	 * Set value to Next process unique key
	 * @param arg Next process unique key to be set
	 */
	public void setNextProcUkey(String arg)
	{
		setValue(NEXTPROCUKEY, arg);
	}

	//#CM704972
	/**
	 * Fetch Next process unique key
	 * @return Next process unique key
	 */
	public String getNextProcUkey()
	{
		return getValue(NextProcessInfo.NEXTPROCUKEY).toString();
	}

	//#CM704973
	/**
	 * Set value to Plan unique key
	 * @param arg Plan unique key to be set
	 */
	public void setPlanUkey(String arg)
	{
		setValue(PLANUKEY, arg);
	}

	//#CM704974
	/**
	 * Fetch Plan unique key
	 * @return Plan unique key
	 */
	public String getPlanUkey()
	{
		return getValue(NextProcessInfo.PLANUKEY).toString();
	}

	//#CM704975
	/**
	 * Set value to Receiving plan unique key
	 * @param arg Receiving plan unique key to be set
	 */
	public void setInstockPlanUkey(String arg)
	{
		setValue(INSTOCKPLANUKEY, arg);
	}

	//#CM704976
	/**
	 * Fetch Receiving plan unique key
	 * @return Receiving plan unique key
	 */
	public String getInstockPlanUkey()
	{
		return getValue(NextProcessInfo.INSTOCKPLANUKEY).toString();
	}

	//#CM704977
	/**
	 * Set value to Storage plan unique key
	 * @param arg Storage plan unique key to be set
	 */
	public void setStoragePlanUkey(String arg)
	{
		setValue(STORAGEPLANUKEY, arg);
	}

	//#CM704978
	/**
	 * Fetch Storage plan unique key
	 * @return Storage plan unique key
	 */
	public String getStoragePlanUkey()
	{
		return getValue(NextProcessInfo.STORAGEPLANUKEY).toString();
	}

	//#CM704979
	/**
	 * Set value to Retrieval plan unique key
	 * @param arg Retrieval plan unique key to be set
	 */
	public void setRetrievalPlanUkey(String arg)
	{
		setValue(RETRIEVALPLANUKEY, arg);
	}

	//#CM704980
	/**
	 * Fetch Retrieval plan unique key
	 * @return Retrieval plan unique key
	 */
	public String getRetrievalPlanUkey()
	{
		return getValue(NextProcessInfo.RETRIEVALPLANUKEY).toString();
	}

	//#CM704981
	/**
	 * Set value to Sorting plan unique key
	 * @param arg Sorting plan unique key to be set
	 */
	public void setSortingPlanUkey(String arg)
	{
		setValue(SORTINGPLANUKEY, arg);
	}

	//#CM704982
	/**
	 * Fetch Sorting plan unique key
	 * @return Sorting plan unique key
	 */
	public String getSortingPlanUkey()
	{
		return getValue(NextProcessInfo.SORTINGPLANUKEY).toString();
	}

	//#CM704983
	/**
	 * Set value to Shipping plan unique key
	 * @param arg Shipping plan unique key to be set
	 */
	public void setShippingPlanUkey(String arg)
	{
		setValue(SHIPPINGPLANUKEY, arg);
	}

	//#CM704984
	/**
	 * Fetch Shipping plan unique key
	 * @return Shipping plan unique key
	 */
	public String getShippingPlanUkey()
	{
		return getValue(NextProcessInfo.SHIPPINGPLANUKEY).toString();
	}

	//#CM704985
	/**
	 * Set value to Work type
	 * @param arg Work type to be set
	 */
	public void setWorkKind(String arg) throws InvalidStatusException
	{
		if ((arg.equals(JOB_TYPE_INSTOCK))
			|| (arg.equals(JOB_TYPE_STORAGE))
			|| (arg.equals(JOB_TYPE_RETRIEVAL))
			|| (arg.equals(JOB_TYPE_SORTING))
			|| (arg.equals(JOB_TYPE_SHIPINSPECTION)))
		{
			setValue(WORKKIND, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wNextWorkKind";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}


	//#CM704986
	/**
	 * Fetch Work type
	 * @return Work type
	 */
	public String getWorkKind()
	{
		return getValue(NextProcessInfo.WORKKIND).toString();
	}

	//#CM704987
	/**
	 * Set value to Work plan qty
	 * @param arg Work plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM704988
	/**
	 * Fetch Work plan qty
	 * @return Work plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(NextProcessInfo.PLANQTY).intValue();
	}

	//#CM704989
	/**
	 * Set value to Work result qty
	 * @param arg Work result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM704990
	/**
	 * Fetch Work result qty
	 * @return Work result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(NextProcessInfo.RESULTQTY).intValue();
	}

	//#CM704991
	/**
	 * Set value to Work shortage qty
	 * @param arg Work shortage qty to be set
	 */
	public void setShortageQty(int arg)
	{
		setValue(SHORTAGEQTY, new Integer(arg));
	}

	//#CM704992
	/**
	 * Fetch Work shortage qty
	 * @return Work shortage qty
	 */
	public int getShortageQty()
	{
		return getBigDecimal(NextProcessInfo.SHORTAGEQTY).intValue();
	}

	//#CM704993
	/**
	 * Set value to Area no
	 * @param arg Area no to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM704994
	/**
	 * Fetch Area no
	 * @return Area no
	 */
	public String getAreaNo()
	{
		return getValue(NextProcessInfo.AREANO).toString();
	}

	//#CM704995
	/**
	 * Set value to Location no
	 * @param arg Location no to be set
	 */
	public void setLocationNo(String arg)
	{
		setValue(LOCATIONNO, arg);
	}

	//#CM704996
	/**
	 * Fetch Location no
	 * @return Location no
	 */
	public String getLocationNo()
	{
		return getValue(NextProcessInfo.LOCATIONNO).toString();
	}

	//#CM704997
	/**
	 * Set value to Status flag
	 * @param arg Status flag to be set
	 */
	public void setStatusFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(STATUS_FLAG_UNPROCESSING))
			|| (arg.equals(STATUS_FLAG_PROCESSING))
			|| (arg.equals(STATUS_FLAG_PROCESSING_FINISH))
			|| (arg.equals(STATUS_FLAG_DELETE)))
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

	//#CM704998
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(NextProcessInfo.STATUSFLAG).toString();
	}

	//#CM704999
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM705000
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(NextProcessInfo.CONSIGNORCODE).toString();
	}

	//#CM705001
	/**
	 * Set value to Receiving plan date
	 * @param arg Receiving plan date to be set
	 */
	public void setInstPlanDate(String arg)
	{
		setValue(INSTPLANDATE, arg);
	}

	//#CM705002
	/**
	 * Fetch Receiving plan date
	 * @return Receiving plan date
	 */
	public String getInstPlanDate()
	{
		return getValue(NextProcessInfo.INSTPLANDATE).toString();
	}

	//#CM705003
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM705004
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(NextProcessInfo.SUPPLIERCODE).toString();
	}

	//#CM705005
	/**
	 * Set value to Receiving ticket no
	 * @param arg Receiving ticket no to be set
	 */
	public void setInstockTicketNo(String arg)
	{
		setValue(INSTOCKTICKETNO, arg);
	}

	//#CM705006
	/**
	 * Fetch Receiving ticket no
	 * @return Receiving ticket no
	 */
	public String getInstockTicketNo()
	{
		return getValue(NextProcessInfo.INSTOCKTICKETNO).toString();
	}

	//#CM705007
	/**
	 * Set value to Receiving line no
	 * @param arg Receiving line no to be set
	 */
	public void setInstockLineNo(int arg)
	{
		setValue(INSTOCKLINENO, new Integer(arg));
	}

	//#CM705008
	/**
	 * Fetch Receiving line no
	 * @return Receiving line no
	 */
	public int getInstockLineNo()
	{
		return getBigDecimal(NextProcessInfo.INSTOCKLINENO).intValue();
	}

	//#CM705009
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM705010
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(NextProcessInfo.ITEMCODE).toString();
	}

	//#CM705011
	/**
	 * Set value to TC/DC flag
	 * @param arg TC/DC flag to be set
	 */
	public void setTcdcFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(TCDC_FLAG_DC))
			|| (arg.equals(TCDC_FLAG_TC))
			|| (arg.equals(TCDC_FLAG_CROSSTC)))
		{
			setValue(TCDCFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wTcdcFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM705012
	/**
	 * Fetch TC/DC flag
	 * @return TC/DC flag
	 */
	public String getTcdcFlag()
	{
		return getValue(NextProcessInfo.TCDCFLAG).toString();
	}

	//#CM705013
	/**
	 * Set value to Shipping plan date
	 * @param arg Shipping plan date to be set
	 */
	public void setShipPlanDate(String arg)
	{
		setValue(SHIPPLANDATE, arg);
	}

	//#CM705014
	/**
	 * Fetch Shipping plan date
	 * @return Shipping plan date
	 */
	public String getShipPlanDate()
	{
		return getValue(NextProcessInfo.SHIPPLANDATE).toString();
	}

	//#CM705015
	/**
	 * Set value to Customer code
	 * @param arg Customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM705016
	/**
	 * Fetch Customer code
	 * @return Customer code
	 */
	public String getCustomerCode()
	{
		return getValue(NextProcessInfo.CUSTOMERCODE).toString();
	}

	//#CM705017
	/**
	 * Set value to Shipping ticket no
	 * @param arg Shipping ticket no to be set
	 */
	public void setShippingTicketNo(String arg)
	{
		setValue(SHIPPINGTICKETNO, arg);
	}

	//#CM705018
	/**
	 * Fetch Shipping ticket no
	 * @return Shipping ticket no
	 */
	public String getShippingTicketNo()
	{
		return getValue(NextProcessInfo.SHIPPINGTICKETNO).toString();
	}

	//#CM705019
	/**
	 * Set value to Shipping line no
	 * @param arg Shipping line no to be set
	 */
	public void setShippingLineNo(int arg)
	{
		setValue(SHIPPINGLINENO, new Integer(arg));
	}

	//#CM705020
	/**
	 * Fetch Shipping line no
	 * @return Shipping line no
	 */
	public int getShippingLineNo()
	{
		return getBigDecimal(NextProcessInfo.SHIPPINGLINENO).intValue();
	}

	//#CM705021
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM705022
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(NextProcessInfo.REGISTDATE);
	}

	//#CM705023
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM705024
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(NextProcessInfo.REGISTPNAME).toString();
	}

	//#CM705025
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM705026
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(NextProcessInfo.LASTUPDATEDATE);
	}

	//#CM705027
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM705028
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(NextProcessInfo.LASTUPDATEPNAME).toString();
	}


	//#CM705029
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM705030
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(LASTUPDATEDATE, "");
		setValue(REGISTDATE, "");

		setValue(PLANQTY, new Integer(0));
		setValue(RESULTQTY, new Integer(0));
		setValue(SHORTAGEQTY, new Integer(0));
		setValue(INSTOCKLINENO, new Integer(0));
		setValue(SHIPPINGLINENO, new Integer(0));

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
	//#CM705031
	// package methods
	//#CM705032
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705033
	//------------------------------------------------------------


	//#CM705034
	//------------------------------------------------------------
	//#CM705035
	// protected methods
	//#CM705036
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705037
	//------------------------------------------------------------


	//#CM705038
	//------------------------------------------------------------
	//#CM705039
	// private methods
	//#CM705040
	//------------------------------------------------------------
	//#CM705041
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + NEXTPROCUKEY);
		lst.add(prefix + PLANUKEY);
		lst.add(prefix + INSTOCKPLANUKEY);
		lst.add(prefix + STORAGEPLANUKEY);
		lst.add(prefix + RETRIEVALPLANUKEY);
		lst.add(prefix + SORTINGPLANUKEY);
		lst.add(prefix + SHIPPINGPLANUKEY);
		lst.add(prefix + WORKKIND);
		lst.add(prefix + PLANQTY);
		lst.add(prefix + RESULTQTY);
		lst.add(prefix + SHORTAGEQTY);
		lst.add(prefix + AREANO);
		lst.add(prefix + LOCATIONNO);
		lst.add(prefix + STATUSFLAG);
		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + INSTPLANDATE);
		lst.add(prefix + SUPPLIERCODE);
		lst.add(prefix + INSTOCKTICKETNO);
		lst.add(prefix + INSTOCKLINENO);
		lst.add(prefix + ITEMCODE);
		lst.add(prefix + TCDCFLAG);
		lst.add(prefix + SHIPPLANDATE);
		lst.add(prefix + CUSTOMERCODE);
		lst.add(prefix + SHIPPINGTICKETNO);
		lst.add(prefix + SHIPPINGLINENO);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM705042
	//------------------------------------------------------------
	//#CM705043
	// utility methods
	//#CM705044
	//------------------------------------------------------------
	//#CM705045
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: NextProcessInfo.java,v 1.5 2006/11/16 02:15:41 suresh Exp $" ;
	}
}
