//#CM41934
//$Id: AsWorkingInformation.java,v 1.2 2006/10/26 08:13:13 suresh Exp $
package jp.co.daifuku.wms.asrs.entity ;

//#CM41935
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;

import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM41936
/**
 * The entity class which added the StationNumber item to WorkingInformation.
 * Use SOURCESTATIONNUMBER of CarryInformation for the Column person of StationNumber.
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- Change history -->
 * <tr><td nowrap>2006/01/11</td><td nowrap>yoshino</td>
 * <td>Class created.</td></tr>
 * <tr><td nowrap>2006/01/16</td><td nowrap>Y.Okamura</td>
 * <td>Class name change</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:13:13 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */
public class AsWorkingInformation extends WorkingInformation
{
	//#CM41937
	/** Column definition (CARRYKEY) */

	public static final FieldName CARRYKEY = new FieldName("CARRYKEY");

	//#CM41938
	/** Column definition (PALETTEID) */

	public static final FieldName PALETTEID = new FieldName("PALETTEID");

	//#CM41939
	/** Column definition (CREATEDATE) */

	public static final FieldName CREATEDATE = new FieldName("CREATEDATE");

	//#CM41940
	/** Column definition (WORKTYPE) */

	public static final FieldName WORKTYPE = new FieldName("WORKTYPE");

	//#CM41941
	/** Column definition (GROUPNUMBER) */

	public static final FieldName GROUPNUMBER = new FieldName("GROUPNUMBER");

	//#CM41942
	/** Column definition (CMDSTATUS) */

	public static final FieldName CMDSTATUS = new FieldName("CMDSTATUS");

	//#CM41943
	/** Column definition (PRIORITY) */

	public static final FieldName PRIORITY = new FieldName("PRIORITY");

	//#CM41944
	/** Column definition (RESTORINGFLAG) */

	public static final FieldName RESTORINGFLAG = new FieldName("RESTORINGFLAG");

	//#CM41945
	/** Column definition (CARRYKIND) */

	public static final FieldName CARRYKIND = new FieldName("CARRYKIND");

	//#CM41946
	/** Column definition (RETRIEVALSTATIONNUMBER) */

	public static final FieldName RETRIEVALSTATIONNUMBER = new FieldName("RETRIEVALSTATIONNUMBER");

	//#CM41947
	/** Column definition (RETRIEVALDETAIL) */

	public static final FieldName RETRIEVALDETAIL = new FieldName("RETRIEVALDETAIL");

	//#CM41948
	/** Column definition (WORKNUMBER) */

	public static final FieldName WORKNUMBER = new FieldName("WORKNUMBER");

	//#CM41949
	/** Column definition (SOURCESTATIONNUMBER) */

	public static final FieldName SOURCESTATIONNUMBER = new FieldName("SOURCESTATIONNUMBER");

	//#CM41950
	/** Column definition (DESTSTATIONNUMBER) */

	public static final FieldName DESTSTATIONNUMBER = new FieldName("DESTSTATIONNUMBER");

	//#CM41951
	/** Column definition (ARRIVALDATE) */

	public static final FieldName ARRIVALDATE = new FieldName("ARRIVALDATE");

	//#CM41952
	/** Column definition (CONTROLINFO) */

	public static final FieldName CONTROLINFO = new FieldName("CONTROLINFO");

	//#CM41953
	/** Column definition (CANCELREQUEST) */

	public static final FieldName CANCELREQUEST = new FieldName("CANCELREQUEST");

	//#CM41954
	/** Column definition (CANCELREQUESTDATE) */

	public static final FieldName CANCELREQUESTDATE = new FieldName("CANCELREQUESTDATE");

	//#CM41955
	/** Column definition (SCHEDULENUMBER) */

	public static final FieldName SCHEDULENUMBER = new FieldName("SCHEDULENUMBER");

	//#CM41956
	/** Column definition (AISLESTATIONNUMBER) */

	public static final FieldName AISLESTATIONNUMBER = new FieldName("AISLESTATIONNUMBER");

	//#CM41957
	/** Column definition (ENDSTATIONNUMBER) */

	public static final FieldName ENDSTATIONNUMBER = new FieldName("ENDSTATIONNUMBER");

	//#CM41958
	/** Column definition (ERRORCODE) */

	public static final FieldName ERRORCODE = new FieldName("ERRORCODE");

	//#CM41959
	/** Column definition (MAINTENANCETERMINAL) */

	public static final FieldName MAINTENANCETERMINAL = new FieldName("MAINTENANCETERMINAL");

	//#CM41960
	/** Column definition (STOCKQTY) */

	public static final FieldName STOCKQTY = new FieldName("STOCK_QTY");

	//#CM41961
	/** Column definition (WORKINFORMATION.USE_BY_DATE) */

	public static final FieldName WORK_USE_BY_DATE = new FieldName("WORK_USE_BY_DATE");

	//#CM41962
	/**
	 * Prepare the column name list and generate the instance.
	 */
	public AsWorkingInformation()
	{
		super() ;
		setInitCreateColumn();
	}

	//#CM41963
	/**
	 * Set the value at station No.
	 * @param arg Station No to be set
	 */
	public void setStationNo(String arg)
	{
		setValue(CarryInformation.SOURCESTATIONNUMBER, arg);
	}

	//#CM41964
	/**
	 * Acquire station No.
	 * @return Station No
	 */
	public String getStationNo()
	{
		return getValue(CarryInformation.SOURCESTATIONNUMBER).toString();
	}

	//#CM41965
	/**
	 * Set the value in transportation Key.
	 * @param arg Transportation Key to be set
	 */
	public void setCarryKey(String arg)
	{
		setValue(CARRYKEY, arg);
	}

	//#CM41966
	/**
	 * Acquire transportation Key
	 * @return Transportation Key
	 */
	public String getCarryKey()
	{
		return getValue(CarryInformation.CARRYKEY).toString();
	}

	//#CM41967
	/**
	 * Set the value in palette ID.
	 * @param arg Palette ID to be set
	 */
	public void setPaletteId(int arg)
	{
		setValue(PALETTEID, new Integer(arg));
	}

	//#CM41968
	/**
	 * Acquire palette ID.
	 * @return Palette ID
	 */
	public int getPaletteId()
	{
		return getBigDecimal(CarryInformation.PALETTEID).intValue();
	}

	//#CM41969
	/**
	 * Set the value at the date.
	 * @param arg Date to be set
	 */
	public void setCreateDate(Date arg)
	{
		setValue(CREATEDATE, arg);
	}

	//#CM41970
	/**
	 * Acquire the date.
	 * @return Date
	 */
	public Date getCreateDate()
	{
		return (Date)getValue(CarryInformation.CREATEDATE);
	}

	//#CM41971
	/**
	 * Set the value in the work type.
	 * @param arg Work type to be set
	 */
	public void setWorkType(String arg)
	{
		setValue(WORKTYPE, arg);
	}

	//#CM41972
	/**
	 * Acquire the work type.
	 * @return Work type
	 */
	public String getWorkType()
	{
		return getValue(CarryInformation.WORKTYPE).toString();
	}

	//#CM41973
	/**
	 * Set the value in delivery group No.
	 * @param arg Delivery group No to be set
	 */
	public void setGroupNumber(int arg)
	{
		setValue(GROUPNUMBER, new Integer(arg));
	}

	//#CM41974
	/**
	 * Acquire delivery group No.
	 * @return Delivery group No
	 */
	public int getGroupNumber()
	{
		return getBigDecimal(CarryInformation.GROUPNUMBER).intValue();
	}

	//#CM41975
	/**
	 * Set the value in the state of transportation.
	 * @param arg State of transportation to be set
	 */
	public void setCmdStatus(int arg)
	{
		setValue(CMDSTATUS, new Integer(arg));
	}

	//#CM41976
	/**
	 * Acquire state of transportation.
	 * @return State of transportation
	 */
	public int getCmdStatus()
	{
		return getBigDecimal(CarryInformation.CMDSTATUS).intValue();
	}

	//#CM41977
	/**
	 * Set the value in the priority division.
	 * @param arg Priority division to be set
	 */
	public void setPriority(int arg)
	{
		setValue(PRIORITY, new Integer(arg));
	}

	//#CM41978
	/**
	 * Acquire the priority division.
	 * @return Priority division
	 */
	public int getPriority()
	{
		return getBigDecimal(CarryInformation.PRIORITY).intValue();
	}

	//#CM41979
	/**
	 * Set the value in the flag of the re-stock.
	 * @param arg Flag of re-stock to be set
	 */
	public void setReStoringFlag(int arg)
	{
		setValue(RESTORINGFLAG, new Integer(arg));
	}

	//#CM41980
	/**
	 * Acquire the flag of the re-stock.
	 * @return Flag of re-stock
	 */
	public int getReStoringFlag()
	{
		return getBigDecimal(CarryInformation.RESTORINGFLAG).intValue();
	}

	//#CM41981
	/**
	 * Set the value in the transportation division.
	 * @param arg Transportation division to be set
	 */
	public void setCarryKind(int arg)
	{
		setValue(CARRYKIND, new Integer(arg));
	}

	//#CM41982
	/**
	 * Acquire the transportation division.
	 * @return Transportation division
	 */
	public int getCarryKind()
	{
		return getBigDecimal(CarryInformation.CARRYKIND).intValue();
	}

	//#CM41983
	/**
	 * Set the value in delivery location No.
	 * @param arg Delivery location No to be set
	 */
	public void setRetrievalStationNumber(String arg)
	{
		setValue(RETRIEVALSTATIONNUMBER, arg);
	}

	//#CM41984
	/**
	 * Acquire delivery location No.
	 * @return Delivery location No
	 */
	public String getRetrievalStationNumber()
	{
		return getValue(CarryInformation.RETRIEVALSTATIONNUMBER).toString();
	}

	//#CM41985
	/**
	 * Set the value in a delivery detailed instruction.
	 * @param arg The delivery instruction details to be set. 
	 */
	public void setRetrievalDetail(int arg)
	{
		setValue(RETRIEVALDETAIL, new Integer(arg));
	}

	//#CM41986
	/**
	 * Acquire a delivery detailed instruction.
	 * @return The delivery instruction is detailed.
	 */
	public int getRetrievalDetail()
	{
		return getBigDecimal(CarryInformation.RETRIEVALDETAIL).intValue();
	}

	//#CM41987
	/**
	 * Set the value in work No.
	 * @param arg Work No to be set
	 */
	public void setWorkNumber(String arg)
	{
		setValue(WORKNUMBER, arg);
	}

	//#CM41988
	/**
	 * Acquire work No.
	 * @return Work No
	 */
	public String getWorkNumber()
	{
		return getValue(CarryInformation.WORKNUMBER).toString();
	}

	//#CM41989
	/**
	 * Set the value at Transportation origin station No.
	 * @param arg Transportation origin Station No to be set
	 */
	public void setSourceStationNumber(String arg)
	{
		setValue(SOURCESTATIONNUMBER, arg);
	}

	//#CM41990
	/**
	 * Acquire Transportation origin station No.
	 * @return Transportation origin Station No
	 */
	public String getSourceStationNumber()
	{
		return getValue(CarryInformation.SOURCESTATIONNUMBER).toString();
	}

	//#CM41991
	/**
	 * Set the value at transportation destination station No.
	 * @param arg Transportation destination Station No to be set
	 */
	public void setDestStationNumber(String arg)
	{
		setValue(DESTSTATIONNUMBER, arg);
	}

	//#CM41992
	/**
	 * Acquire Transportation destination station No.
	 * @return Transportation destination Station No
	 */
	public String getDestStationNumber()
	{
		return getValue(CarryInformation.DESTSTATIONNUMBER).toString();
	}

	//#CM41993
	/**
	 * Set the value at the arrival date.
	 * @param arg Arrival date to be set
	 */
	public void setArrivalDate(Date arg)
	{
		setValue(ARRIVALDATE, arg);
	}

	//#CM41994
	/**
	 * Acquire the arrival date.
	 * @return Arrival date
	 */
	public Date getArrivalDate()
	{
		return (Date)getValue(CarryInformation.ARRIVALDATE);
	}

	//#CM41995
	/**
	 * Set the value in control information.
	 * @param arg Control information to be set
	 */
	public void setControlInformation(String arg)
	{
		setValue(CONTROLINFO, arg);
	}

	//#CM41996
	/**
	 * Acquire control information.
	 * @return Control information
	 */
	public String getControlInformation()
	{
		return getValue(CarryInformation.CONTROLINFO).toString();
	}

	//#CM41997
	/**
	 * Set the value in the cancellation demand division.
	 * @param arg Cancellation demand division to be set
	 */
	public void setCancelRequest(int arg)
	{
		setValue(CANCELREQUEST, new Integer(arg));
	}

	//#CM41998
	/**
	 * Acquire the cancellation demand division.
	 * @return Cancellation demand division
	 */
	public int getCancelRequest()
	{
		return getBigDecimal(CarryInformation.CANCELREQUEST).intValue();
	}

	//#CM41999
	/**
	 * Set the value at the cancellation demand date.
	 * @param arg Cancellation demand date to be set
	 */
	public void setCancelRequestDate(Date arg)
	{
		setValue(CANCELREQUESTDATE, arg);
	}

	//#CM42000
	/**
	 * Acquire the cancellation demand date.
	 * @return Cancellation demand date
	 */
	public Date getCancelRequestDate()
	{
		return (Date)getValue(CarryInformation.CANCELREQUESTDATE);
	}

	//#CM42001
	/**
	 * Set the value in schedule No.
	 * @param arg Schedule No to be set
	 */
	public void setScheduleNumber(String arg)
	{
		setValue(SCHEDULENUMBER, arg);
	}

	//#CM42002
	/**
	 * Acquire schedule No.
	 * @return Schedule No
	 */
	public String getScheduleNumber()
	{
		return getValue(CarryInformation.SCHEDULENUMBER).toString();
	}

	//#CM42003
	/**
	 * Set the value at Aisle station No.
	 * @param arg Aisle Station No to be set
	 */
	public void setAisleStationNumber(String arg)
	{
		setValue(AISLESTATIONNUMBER, arg);
	}

	//#CM42004
	/**
	 * Acquire Aisle station No.
	 * @return Aisle Station No
	 */
	public String getAisleStationNumber()
	{
		return getValue(CarryInformation.AISLESTATIONNUMBER).toString();
	}

	//#CM42005
	/**
	 * Set the value at Final station No.
	 * @param arg Final Station No
	 */
	public void setEndStationNumber(String arg)
	{
		setValue(ENDSTATIONNUMBER, arg);
	}

	//#CM42006
	/**
	 * Acquire Final station No.
	 * @return Final Station No
	 */
	public String getEndStationNumber()
	{
		return getValue(CarryInformation.ENDSTATIONNUMBER).toString();
	}

	//#CM42007
	/**
	 * Set the value in an abnormal code.
	 * @param arg Abnormal code to be set
	 */
	public void setErrorCode(int arg)
	{
		setValue(ERRORCODE, new Integer(arg));
	}

	//#CM42008
	/**
	 * Acquire an abnormal code.
	 * @return Abnormal code
	 */
	public int getErrorCode()
	{
		return getBigDecimal(CarryInformation.ERRORCODE).intValue();
	}

	//#CM42009
	/**
	 * Set the value in maintenance terminal No.
	 * @param arg Maintenance terminal No to be set
	 */
	public void setMaintenanceTerminal(String arg)
	{
		setValue(MAINTENANCETERMINAL, arg);
	}

	//#CM42010
	/**
	 * Acquire maintenance terminal No.
	 * @return Maintenance terminal No
	 */
	public String getMaintenanceTerminal()
	{
		return getValue(CarryInformation.MAINTENANCETERMINAL).toString();
	}

	//#CM42011
	/**
	 * Set the value in the quantity of inventory.
	 * @param arg Quantity of inventory to be set
	 */
	public void setStockQty(int arg)
	{
		setValue(STOCKQTY, new Integer(arg));
	}

	//#CM42012
	/**
	 * Acquire the quantity of inventory.
	 * @return Quantity of inventory
	 */
	public int getStockQty()
	{
		return getBigDecimal(STOCKQTY).intValue();
	}
	
	//#CM42013
	/**
	 * Acquire the Expiry date of work information.
	 * @return Expiry date of work information
	 */
	public String getWorkInfoUseByDate()
	{
		return getValue(WORK_USE_BY_DATE).toString();
	}

	//#CM42014
	/**
	 * Set the value at the Expiry date of work information.
	 * @param arg Expiry date of work information to be set
	 */
	public void setWorkInfoUseByDate(String arg)
	{
		setValue(WORK_USE_BY_DATE, arg);
	}

	//#CM42015
	//------------------------------------------------------------
	//#CM42016
	// private methods
	//#CM42017
	//------------------------------------------------------------
	//#CM42018
	/**
	 * Prepare the column name list.(SearchKey, AlterKey usage)
	 * To match with Column definition.
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = "" ;

		lst.add(prefix + CARRYKEY);
		lst.add(prefix + PALETTEID);
		lst.add(prefix + CREATEDATE);
		lst.add(prefix + WORKTYPE);
		lst.add(prefix + GROUPNUMBER);
		lst.add(prefix + CMDSTATUS);
		lst.add(prefix + PRIORITY);
		lst.add(prefix + RESTORINGFLAG);
		lst.add(prefix + CARRYKIND);
		lst.add(prefix + RETRIEVALSTATIONNUMBER);
		lst.add(prefix + RETRIEVALDETAIL);
		lst.add(prefix + WORKNUMBER);
		lst.add(prefix + SOURCESTATIONNUMBER);
		lst.add(prefix + DESTSTATIONNUMBER);
		lst.add(prefix + ARRIVALDATE);
		lst.add(prefix + CONTROLINFO);
		lst.add(prefix + CANCELREQUEST);
		lst.add(prefix + CANCELREQUESTDATE);
		lst.add(prefix + SCHEDULENUMBER);
		lst.add(prefix + AISLESTATIONNUMBER);
		lst.add(prefix + ENDSTATIONNUMBER);
		lst.add(prefix + ERRORCODE);
		lst.add(prefix + MAINTENANCETERMINAL);
		lst.add(prefix + STOCKQTY);
		lst.add(prefix + WORK_USE_BY_DATE);
	}
}
