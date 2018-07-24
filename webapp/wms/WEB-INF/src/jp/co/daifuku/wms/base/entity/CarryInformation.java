//#CM703144
//$Id: CarryInformation.java,v 1.5 2006/11/16 02:15:47 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM703145
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
import jp.co.daifuku.wms.base.entity.CarryInformation;

//#CM703146
/**
 * Entity class of CARRYINFO
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:47 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class CarryInformation
		extends AbstractEntity
{
	//#CM703147
	//------------------------------------------------------------
	//#CM703148
	// class variables (prefix '$')
	//#CM703149
	//------------------------------------------------------------
	//#CM703150
	//	private String	$classVar ;

	//#CM703151
	/**<jp>
	 * Transportation Status : Allocate
	 </jp>*/
	//#CM703152
	/**<en>
	 * Field to set transport (allocation)
	 </en>*/
	public static final int CMDSTATUS_ALLOCATION = 0 ;

	//#CM703153
	/**<jp>
	 * Transportation Status : Start
	 </jp>*/
	//#CM703154
	/**<en>
	 * Field to set transport (start)
	 </en>*/
	public static final int CMDSTATUS_START = 1 ;

	//#CM703155
	/**<jp>
	 * Transportation Status : Waiting for response
	 </jp>*/
	//#CM703156
	/**<en>
	 * Field to set transport (waiting for a reponse)
	 </en>*/
	public static final int CMDSTATUS_WAIT_RESPONSE = 2 ;

	//#CM703157
	/**<jp>
	 * Transportation Status : Instruction provided
	 </jp>*/
	//#CM703158
	/**<en>
	 * Field to set transport (instruction provided)
	 </en>*/
	public static final int CMDSTATUS_INSTRUCTION =3 ;

	//#CM703159
	/**<jp>
	 * Transportation Status : pickup completed
	 </jp>*/
	//#CM703160
	/**<en>
	 * Field to set transport (pick-up completed)
	 </en>*/
	public static final int CMDSTATUS_PICKUP = 4 ;

	//#CM703161
	/**<jp>
	 * Transportation Status : Retrieval completed
	 </jp>*/
	//#CM703162
	/**<en>
	 * Field to set transport (retrieval completed)
	 </en>*/
	public static final int CMDSTATUS_COMP_RETRIEVAL = 5 ;

	//#CM703163
	/**<jp>
	 * Transportation Status : Arrival
	 </jp>*/
	//#CM703164
	/**<en>
	 * Field to set transport (arrival)
	 </en>*/
	public static final int CMDSTATUS_ARRIVAL = 6 ;

	//#CM703165
	/**<jp>
	 * Transportation Status : Error
	 </jp>*/
	//#CM703166
	/**<en>
	 * Field to set transport (error)
	 </en>*/
	public static final int CMDSTATUS_ERROR = 9 ;

	//#CM703167
	/**<jp>
	 * Priority flag : Urgent
	 </jp>*/
	//#CM703168
	/**<en>
	 * Field to set priority (urgent)
	 </en>*/
	public static final int PRIORITY_EMERGENCY = 1;

	//#CM703169
	/**<jp>
	 * Priority flag : Normal
	 </jp>*/
	//#CM703170
	/**<en>
	 * Field to set priority (normal)
	 </en>*/
	public static final int PRIORITY_NORMAL = 2;

	//#CM703171
	/**<jp>
	 * Priority flag : Check empty location
	 </jp>*/
	//#CM703172
	/**<en>
	 * Field to set priority (check empty location)
	 </en>*/
	public static final int PRIORITY_CHECK_EMPTY = 9;

	//#CM703173
	/**<jp>
	 * Restorage flag : no re-storing in the same location
	 </jp>*/
	//#CM703174
	/**<en>
	 * Field to set re-storing flag (no re-storing in the same location)
	 </en>*/
	public static final int RESTORING_NOT_SAME_LOC = 0;

	//#CM703175
	/**<jp>
	 * Restorage flag : re-storing in the same location
	 </jp>*/
	//#CM703176
	/**<en>
	 * Field to set re-storing flag (re-storing in the same location)
	 </en>*/
	public static final int RESTORING_SAME_LOC = 1;

	//#CM703177
	/**<jp>
	 * Priority flag : Storage
	 </jp>*/
	//#CM703178
	/**<en>
	 * Field to set transport (storage)
	 </en>*/
	public static final int CARRYKIND_STORAGE = 1 ;

	//#CM703179
	/**<jp>
	 * Priority flag : Retrieval
	 </jp>*/
	//#CM703180
	/**<en>
	 * Field to set transport (retrieval)
	 </en>*/
	public static final int CARRYKIND_RETRIEVAL = 2 ;

	//#CM703181
	/**<jp>
	 * Priority flag : Direct travel
	 </jp>*/
	//#CM703182
	/**<en>
	 * Field to set transport (direct travel)
	 </en>*/
	public static final int CARRYKIND_DIRECT_TRAVEL = 3 ;

	//#CM703183
	/**<jp>
	 * Priority flag : Location to location move
	 </jp>*/
	//#CM703184
	/**<en>
	 * Field to set transport (location to location move)
	 </en>*/
	public static final int CARRYKIND_RACK_TO_RACK = 5 ;

	//#CM703185
	/**<jp>
	 * Detailed delivery instruction : Stock confirmation
	 </jp>*/
	//#CM703186
	/**<en>
	 * Field to set detail of retrieval instruction (check inventory)
	 </en>*/
	public static final int RETRIEVALDETAIL_INVENTORY_CHECK = 0;

	//#CM703187
	/**<jp>
	 * Detailed delivery instruction : Retrieval in unit
	 </jp>*/
	//#CM703188
	/**<en>
	 * Field to set detail of retrieval instruction (retrieval in unit)
	 </en>*/
	public static final int RETRIEVALDETAIL_UNIT = 1;

	//#CM703189
	/**<jp>
	 * Detailed delivery instruction : Picking retrieval
	 </jp>*/
	//#CM703190
	/**<en>
	 * Field to set detail of retrieval instruction (picking retrieval)
	 </en>*/
	public static final int RETRIEVALDETAIL_PICKING = 2;

	//#CM703191
	/**<jp>
	 * Detailed delivery instruction : Adding Storage
	 </jp>*/
	//#CM703192
	/**<en>
	 * Field to set detail of retrieval instruction (adding storing)
	 </en>*/
	public static final int RETRIEVALDETAIL_ADD_STORING = 3;

	//#CM703193
	/**<jp>
	 * Detailed delivery instruction : Unspecified
	 </jp>*/
	//#CM703194
	/**<en>
	 * Field to set detail of retrieval instruction (no specification)
	 </en>*/
	public static final int RETRIEVALDETAIL_UNKNOWN = 9;

	//#CM703195
	/**<jp>
	 * Cancellation request flag : No request
	 </jp>*/
	//#CM703196
	/**<en>
	 * Field to set cancel request (no request)
	 </en>*/
	public static final int CANCELREQUEST_UNDEMAND = 0;

	//#CM703197
	/**<jp>
	 * Cancellation request flag : Request to cancel data
	 </jp>*/
	//#CM703198
	/**<en>
	 * Field to set cancel request (requesting to cancel data)
	 </en>*/
	public static final int CANCELREQUEST_CANCEL = 1;

	//#CM703199
	/**<jp>
	 * Cancellation request flag : Request to cancel allocation
	 </jp>*/
	//#CM703200
	/**<en>
	 * Field to set cancel request (requesting to cancel allocation)
	 </en>*/
	public static final int CANCELREQUEST_RELEASE = 2;

	//#CM703201
	/**<jp>
	 * Cancellation request flag : Request to delete inventory data
	 </jp>*/
	//#CM703202
	/**<en>
	 * Field to set cancel request (requesting to delete inventory data)
	 </en>*/
	public static final int CANCELREQUEST_DROP = 3;
	//#CM703203
	//------------------------------------------------------------
	//#CM703204
	// fields (upper case only)
	//#CM703205
	//------------------------------------------------------------
	//#CM703206
	/*
	 *  * Table name : CARRYINFO
	 * carry key :                     CARRYKEY            varchar2(16)
	 * palette id :                    PALETTEID           number
	 * creation date :                 CREATEDATE          date
	 * work type :                     WORKTYPE            varchar2(8)
	 * picking group :                 GROUPNUMBER         number
	 * conveyance status :             CMDSTATUS           number
	 * priority type :                 PRIORITY            number
	 * restorage flag :                RESTORINGFLAG       number
	 * conveyance type :               CARRYKIND           number
	 * picking location number :       RETRIEVALSTATIONNUMBERvarchar2(16)
	 * picking detail :                RETRIEVALDETAIL     number
	 * work number :                   WORKNUMBER          varchar2(16)
	 * source station number :         SOURCESTATIONNUMBER varchar2(16)
	 * destination station number :    DESTSTATIONNUMBER   varchar2(16)
	 * arrival date :                  ARRIVALDATE         date
	 * control info :                  CONTROLINFO         varchar2(30)
	 * cancel request status :         CANCELREQUEST       number
	 * cancellation request date :     CANCELREQUESTDATE   date
	 * schedule number :               SCHEDULENUMBER      varchar2(9)
	 * aisle station number :          AISLESTATIONNUMBER  varchar2(16)
	 * last sation number :            ENDSTATIONNUMBER    varchar2(16)
	 * error code :                    ERRORCODE           number
	 * maintenance terminal number :   MAINTENANCETERMINAL varchar2(4)
	 */

	//#CM703207
	/**Table name definition*/

	public static final String TABLE_NAME = "CARRYINFO";

	//#CM703208
	/** Column Definition (CARRYKEY) */

	public static final FieldName CARRYKEY = new FieldName("CARRYKEY");

	//#CM703209
	/** Column Definition (PALETTEID) */

	public static final FieldName PALETTEID = new FieldName("PALETTEID");

	//#CM703210
	/** Column Definition (CREATEDATE) */

	public static final FieldName CREATEDATE = new FieldName("CREATEDATE");

	//#CM703211
	/** Column Definition (WORKTYPE) */

	public static final FieldName WORKTYPE = new FieldName("WORKTYPE");

	//#CM703212
	/** Column Definition (GROUPNUMBER) */

	public static final FieldName GROUPNUMBER = new FieldName("GROUPNUMBER");

	//#CM703213
	/** Column Definition (CMDSTATUS) */

	public static final FieldName CMDSTATUS = new FieldName("CMDSTATUS");

	//#CM703214
	/** Column Definition (PRIORITY) */

	public static final FieldName PRIORITY = new FieldName("PRIORITY");

	//#CM703215
	/** Column Definition (RESTORINGFLAG) */

	public static final FieldName RESTORINGFLAG = new FieldName("RESTORINGFLAG");

	//#CM703216
	/** Column Definition (CARRYKIND) */

	public static final FieldName CARRYKIND = new FieldName("CARRYKIND");

	//#CM703217
	/** Column Definition (RETRIEVALSTATIONNUMBER) */

	public static final FieldName RETRIEVALSTATIONNUMBER = new FieldName("RETRIEVALSTATIONNUMBER");

	//#CM703218
	/** Column Definition (RETRIEVALDETAIL) */

	public static final FieldName RETRIEVALDETAIL = new FieldName("RETRIEVALDETAIL");

	//#CM703219
	/** Column Definition (WORKNUMBER) */

	public static final FieldName WORKNUMBER = new FieldName("WORKNUMBER");

	//#CM703220
	/** Column Definition (SOURCESTATIONNUMBER) */

	public static final FieldName SOURCESTATIONNUMBER = new FieldName("SOURCESTATIONNUMBER");

	//#CM703221
	/** Column Definition (DESTSTATIONNUMBER) */

	public static final FieldName DESTSTATIONNUMBER = new FieldName("DESTSTATIONNUMBER");

	//#CM703222
	/** Column Definition (ARRIVALDATE) */

	public static final FieldName ARRIVALDATE = new FieldName("ARRIVALDATE");

	//#CM703223
	/** Column Definition (CONTROLINFO) */

	public static final FieldName CONTROLINFO = new FieldName("CONTROLINFO");

	//#CM703224
	/** Column Definition (CANCELREQUEST) */

	public static final FieldName CANCELREQUEST = new FieldName("CANCELREQUEST");

	//#CM703225
	/** Column Definition (CANCELREQUESTDATE) */

	public static final FieldName CANCELREQUESTDATE = new FieldName("CANCELREQUESTDATE");

	//#CM703226
	/** Column Definition (SCHEDULENUMBER) */

	public static final FieldName SCHEDULENUMBER = new FieldName("SCHEDULENUMBER");

	//#CM703227
	/** Column Definition (AISLESTATIONNUMBER) */

	public static final FieldName AISLESTATIONNUMBER = new FieldName("AISLESTATIONNUMBER");

	//#CM703228
	/** Column Definition (ENDSTATIONNUMBER) */

	public static final FieldName ENDSTATIONNUMBER = new FieldName("ENDSTATIONNUMBER");

	//#CM703229
	/** Column Definition (ERRORCODE) */

	public static final FieldName ERRORCODE = new FieldName("ERRORCODE");

	//#CM703230
	/** Column Definition (MAINTENANCETERMINAL) */

	public static final FieldName MAINTENANCETERMINAL = new FieldName("MAINTENANCETERMINAL");


	//#CM703231
	//------------------------------------------------------------
	//#CM703232
	// properties (prefix 'p_')
	//#CM703233
	//------------------------------------------------------------
	//#CM703234
	//	private String	p_Name ;


	//#CM703235
	//------------------------------------------------------------
	//#CM703236
	// instance variables (prefix '_')
	//#CM703237
	//------------------------------------------------------------
	//#CM703238
	//	private String	_instanceVar ;

	//#CM703239
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM703240
	//------------------------------------------------------------
	//#CM703241
	// constructors
	//#CM703242
	//------------------------------------------------------------

	//#CM703243
	/**
	 * Prepare class name list and generate instance
	 */
	public CarryInformation()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM703244
	//------------------------------------------------------------
	//#CM703245
	// accessors
	//#CM703246
	//------------------------------------------------------------

	//#CM703247
	/**
	 * Set value to carry key
	 * @param arg carry key to be set
	 */
	public void setCarryKey(String arg)
	{
		setValue(CARRYKEY, arg);
	}

	//#CM703248
	/**
	 * Fetch carry key
	 * @return carry key
	 */
	public String getCarryKey()
	{
		return getValue(CarryInformation.CARRYKEY).toString();
	}

	//#CM703249
	/**
	 * Set value to palette id
	 * @param arg palette id to be set
	 */
	public void setPaletteId(int arg)
	{
		setValue(PALETTEID, new Integer(arg));
	}

	//#CM703250
	/**
	 * Fetch palette id
	 * @return palette id
	 */
	public int getPaletteId()
	{
		return getBigDecimal(CarryInformation.PALETTEID).intValue();
	}

	//#CM703251
	/**
	 * Set value to creation date
	 * @param arg creation date to be set
	 */
	public void setCreateDate(Date arg)
	{
		setValue(CREATEDATE, arg);
	}

	//#CM703252
	/**
	 * Fetch creation date
	 * @return creation date
	 */
	public Date getCreateDate()
	{
		return (Date)getValue(CarryInformation.CREATEDATE);
	}

	//#CM703253
	/**
	 * Set value to work type
	 * @param arg work type to be set
	 */
	public void setWorkType(String arg)
	{
		setValue(WORKTYPE, arg);
	}

	//#CM703254
	/**
	 * Fetch work type
	 * @return work type
	 */
	public String getWorkType()
	{
		return getValue(CarryInformation.WORKTYPE).toString();
	}

	//#CM703255
	/**
	 * Set value to picking group
	 * @param arg picking group to be set
	 */
	public void setGroupNumber(int arg)
	{
		setValue(GROUPNUMBER, new Integer(arg));
	}

	//#CM703256
	/**
	 * Fetch picking group
	 * @return picking group
	 */
	public int getGroupNumber()
	{
		return getBigDecimal(CarryInformation.GROUPNUMBER).intValue();
	}

	//#CM703257
	/**
	 * Set value to conveyance status
	 * @param arg conveyance status to be set
	 */
	public void setCmdStatus(int arg)
	{
		setValue(CMDSTATUS, new Integer(arg));
	}

	//#CM703258
	/**
	 * Fetch conveyance status
	 * @return conveyance status
	 */
	public int getCmdStatus()
	{
		return getBigDecimal(CarryInformation.CMDSTATUS).intValue();
	}

	//#CM703259
	/**
	 * Set value to priority type
	 * @param arg priority type to be set
	 */
	public void setPriority(int arg)
	{
		setValue(PRIORITY, new Integer(arg));
	}

	//#CM703260
	/**
	 * Fetch priority type
	 * @return priority type
	 */
	public int getPriority()
	{
		return getBigDecimal(CarryInformation.PRIORITY).intValue();
	}

	//#CM703261
	/**
	 * Set value to restorage flag
	 * @param arg restorage flag to be set
	 */
	public void setReStoringFlag(int arg)
	{
		setValue(RESTORINGFLAG, new Integer(arg));
	}

	//#CM703262
	/**
	 * Fetch restorage flag
	 * @return restorage flag
	 */
	public int getReStoringFlag()
	{
		return getBigDecimal(CarryInformation.RESTORINGFLAG).intValue();
	}

	//#CM703263
	/**
	 * Set value to conveyance type
	 * @param arg conveyance type to be set
	 */
	public void setCarryKind(int arg)
	{
		setValue(CARRYKIND, new Integer(arg));
	}

	//#CM703264
	/**
	 * Fetch conveyance type
	 * @return conveyance type
	 */
	public int getCarryKind()
	{
		return getBigDecimal(CarryInformation.CARRYKIND).intValue();
	}

	//#CM703265
	/**
	 * Set value to picking location number
	 * @param arg picking location number to be set
	 */
	public void setRetrievalStationNumber(String arg)
	{
		setValue(RETRIEVALSTATIONNUMBER, arg);
	}

	//#CM703266
	/**
	 * Fetch picking location number
	 * @return picking location number
	 */
	public String getRetrievalStationNumber()
	{
		return getValue(CarryInformation.RETRIEVALSTATIONNUMBER).toString();
	}

	//#CM703267
	/**
	 * Set value to picking detail
	 * @param arg picking detail to be set
	 */
	public void setRetrievalDetail(int arg)
	{
		setValue(RETRIEVALDETAIL, new Integer(arg));
	}

	//#CM703268
	/**
	 * Fetch picking detail
	 * @return picking detail
	 */
	public int getRetrievalDetail()
	{
		return getBigDecimal(CarryInformation.RETRIEVALDETAIL).intValue();
	}

	//#CM703269
	/**
	 * Set value to work number
	 * @param arg work number to be set
	 */
	public void setWorkNumber(String arg)
	{
		setValue(WORKNUMBER, arg);
	}

	//#CM703270
	/**
	 * Fetch work number
	 * @return work number
	 */
	public String getWorkNumber()
	{
		return getValue(CarryInformation.WORKNUMBER).toString();
	}

	//#CM703271
	/**
	 * Set value to source station number
	 * @param arg source station number to be set
	 */
	public void setSourceStationNumber(String arg)
	{
		setValue(SOURCESTATIONNUMBER, arg);
	}

	//#CM703272
	/**
	 * Fetch source station number
	 * @return source station number
	 */
	public String getSourceStationNumber()
	{
		return getValue(CarryInformation.SOURCESTATIONNUMBER).toString();
	}

	//#CM703273
	/**
	 * Set value to destination station number
	 * @param arg destination station number to be set
	 */
	public void setDestStationNumber(String arg)
	{
		setValue(DESTSTATIONNUMBER, arg);
	}

	//#CM703274
	/**
	 * Fetch destination station number
	 * @return destination station number
	 */
	public String getDestStationNumber()
	{
		return getValue(CarryInformation.DESTSTATIONNUMBER).toString();
	}

	//#CM703275
	/**
	 * Set value to arrival date
	 * @param arg arrival date to be set
	 */
	public void setArrivalDate(Date arg)
	{
		setValue(ARRIVALDATE, arg);
	}

	//#CM703276
	/**
	 * Fetch arrival date
	 * @return arrival date
	 */
	public Date getArrivalDate()
	{
		return (Date)getValue(CarryInformation.ARRIVALDATE);
	}

	//#CM703277
	/**
	 * Set value to control info
	 * @param arg control info to be set
	 */
	public void setControlInfo(String arg)
	{
		setValue(CONTROLINFO, arg);
	}

	//#CM703278
	/**
	 * Fetch control info
	 * @return control info
	 */
	public String getControlInfo()
	{
		return getValue(CarryInformation.CONTROLINFO).toString();
	}

	//#CM703279
	/**
	 * Set value to cancel request status
	 * @param arg cancel request status to be set
	 */
	public void setCancelRequest(int arg)
	{
		setValue(CANCELREQUEST, new Integer(arg));
	}

	//#CM703280
	/**
	 * Fetch cancel request status
	 * @return cancel request status
	 */
	public int getCancelRequest()
	{
		return getBigDecimal(CarryInformation.CANCELREQUEST).intValue();
	}

	//#CM703281
	/**
	 * Set value to cancellation request date
	 * @param arg cancellation request date to be set
	 */
	public void setCancelRequestDate(Date arg)
	{
		setValue(CANCELREQUESTDATE, arg);
	}

	//#CM703282
	/**
	 * Fetch cancellation request date
	 * @return cancellation request date
	 */
	public Date getCancelRequestDate()
	{
		return (Date)getValue(CarryInformation.CANCELREQUESTDATE);
	}

	//#CM703283
	/**
	 * Set value to schedule number
	 * @param arg schedule number to be set
	 */
	public void setScheduleNumber(String arg)
	{
		setValue(SCHEDULENUMBER, arg);
	}

	//#CM703284
	/**
	 * Fetch schedule number
	 * @return schedule number
	 */
	public String getScheduleNumber()
	{
		return getValue(CarryInformation.SCHEDULENUMBER).toString();
	}

	//#CM703285
	/**
	 * Set value to aisle station number
	 * @param arg aisle station number to be set
	 */
	public void setAisleStationNumber(String arg)
	{
		setValue(AISLESTATIONNUMBER, arg);
	}

	//#CM703286
	/**
	 * Fetch aisle station number
	 * @return aisle station number
	 */
	public String getAisleStationNumber()
	{
		return getValue(CarryInformation.AISLESTATIONNUMBER).toString();
	}

	//#CM703287
	/**
	 * Set value to last sation number
	 * @param arg last sation number to be set
	 */
	public void setEndStationNumber(String arg)
	{
		setValue(ENDSTATIONNUMBER, arg);
	}

	//#CM703288
	/**
	 * Fetch last sation number
	 * @return last sation number
	 */
	public String getEndStationNumber()
	{
		return getValue(CarryInformation.ENDSTATIONNUMBER).toString();
	}

	//#CM703289
	/**
	 * Set value to error code
	 * @param arg error code to be set
	 */
	public void setErrorCode(int arg)
	{
		setValue(ERRORCODE, new Integer(arg));
	}

	//#CM703290
	/**
	 * Fetch error code
	 * @return error code
	 */
	public int getErrorCode()
	{
		return getBigDecimal(CarryInformation.ERRORCODE).intValue();
	}

	//#CM703291
	/**
	 * Set value to maintenance terminal number
	 * @param arg maintenance terminal number to be set
	 */
	public void setMaintenanceTerminal(String arg)
	{
		setValue(MAINTENANCETERMINAL, arg);
	}

	//#CM703292
	/**
	 * Fetch maintenance terminal number
	 * @return maintenance terminal number
	 */
	public String getMaintenanceTerminal()
	{
		return getValue(CarryInformation.MAINTENANCETERMINAL).toString();
	}


	//#CM703293
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM703294
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(PALETTEID, new Integer(0));
		setValue(GROUPNUMBER, new Integer(0));
		setValue(CMDSTATUS, new Integer(0));
		setValue(PRIORITY, new Integer(0));
		setValue(RESTORINGFLAG, new Integer(0));
		setValue(CARRYKIND, new Integer(0));
		setValue(RETRIEVALDETAIL, new Integer(0));
		setValue(CANCELREQUEST, new Integer(0));
		setValue(ERRORCODE, new Integer(0));
	}
	//------------------------------------------------------------
	//#CM703295
	// package methods
	//#CM703296
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703297
	//------------------------------------------------------------


	//#CM703298
	//------------------------------------------------------------
	//#CM703299
	// protected methods
	//#CM703300
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703301
	//------------------------------------------------------------


	//#CM703302
	//------------------------------------------------------------
	//#CM703303
	// private methods
	//#CM703304
	//------------------------------------------------------------
	//#CM703305
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

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
	}


	//#CM703306
	//------------------------------------------------------------
	//#CM703307
	// utility methods
	//#CM703308
	//------------------------------------------------------------
	//#CM703309
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: CarryInformation.java,v 1.5 2006/11/16 02:15:47 suresh Exp $" ;
	}
}
