//#CM705892
//$Id: Rft.java,v 1.5 2006/11/16 02:15:39 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM705893
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
import jp.co.daifuku.wms.base.entity.Rft;

//#CM705894
/**
 * Entity class of RFT
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:39 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Rft
		extends AbstractEntity
{
	//#CM705895
	//------------------------------------------------------------
	//#CM705896
	// class variables (prefix '$')
	//#CM705897
	//------------------------------------------------------------
	//#CM705898
	//	private String	$classVar ;

	//#CM705899
	//------------------------------------------------------------
	//#CM705900
	// fields (upper case only)
	//#CM705901
	//------------------------------------------------------------
	//#CM705902
	/*
	 *  * Table name : RFT
	 * RFT No :                        RFTNO               varchar2(3)
	 * Worker code :                   WORKERCODE          varchar2(4)
	 * Work type :                     JOBTYPE             varchar2(2)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Rest flag :                     RESTFLAG            varchar2(1)
	 * Terminal type :                 TERMINALTYPE        varchar2(2)
	 * IP Address :                    IPADDRESS           varchar2(15)
	 * Radio flag :                    RADIOFLAG           varchar2(1)
	 * Rest start time :               RESTSTARTTIME       date
	 * Response ID :                   RESPONSEID          varchar2(1024)
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM705903
	/**Table name definition*/

	public static final String TABLE_NAME = "DNRFT";

	//#CM705904
	/** Column Definition (RFTNO) */

	public static final FieldName RFTNO = new FieldName("RFT_NO");

	//#CM705905
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM705906
	/** Column Definition (JOBTYPE) */

	public static final FieldName JOBTYPE = new FieldName("JOB_TYPE");

	//#CM705907
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM705908
	/** Column Definition (RESTFLAG) */

	public static final FieldName RESTFLAG = new FieldName("REST_FLAG");

	//#CM705909
	/** Column Definition (TERMINALTYPE) */

	public static final FieldName TERMINALTYPE = new FieldName("TERMINAL_TYPE");

	//#CM705910
	/** Column Definition (IPADDRESS) */

	public static final FieldName IPADDRESS = new FieldName("IP_ADDRESS");

	//#CM705911
	/** Column Definition (RADIOFLAG) */

	public static final FieldName RADIOFLAG = new FieldName("RADIO_FLAG");

	//#CM705912
	/** Column Definition (RESTSTARTTIME) */

	public static final FieldName RESTSTARTTIME = new FieldName("REST_START_TIME");

	//#CM705913
	/** Column Definition (RESPONSEID) */

	public static final FieldName RESPONSEID = new FieldName("RESPONSE_ID");

	//#CM705914
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM705915
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM705916
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM705917
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM705918
	//------------------------------------------------------------
	//#CM705919
	// properties (prefix 'p_')
	//#CM705920
	//------------------------------------------------------------
	//#CM705921
	//	private String	p_Name ;


	//#CM705922
	//------------------------------------------------------------
	//#CM705923
	// instance variables (prefix '_')
	//#CM705924
	//------------------------------------------------------------
	//#CM705925
	//	private String	_instanceVar ;

	//#CM705926
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM705927
	//------------------------------------------------------------
	//#CM705928
	// constructors
	//#CM705929
	//------------------------------------------------------------

	//#CM705930
	/**
	 * Prepare class name list and generate instance
	 */
	public Rft()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM705931
	//------------------------------------------------------------
	//#CM705932
	// accessors
	//#CM705933
	//------------------------------------------------------------

	//#CM705934
	/**
	 * Set value to RFT No
	 * @param arg RFT No to be set
	 */
	public void setRftNo(String arg)
	{
		setValue(RFTNO, arg);
	}

	//#CM705935
	/**
	 * Fetch RFT No
	 * @return RFT No
	 */
	public String getRftNo()
	{
		return getValue(Rft.RFTNO).toString();
	}

	//#CM705936
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM705937
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(Rft.WORKERCODE).toString();
	}

	//#CM705938
	/**
	 * Set value to Work type
	 * @param arg Work type to be set
	 */
public void setJobType(String arg) throws InvalidStatusException
	{

		if ((arg.equals(JOB_TYPE_INSTOCK))
			|| (arg.equals(JOB_TYPE_STORAGE))
			|| (arg.equals(JOB_TYPE_RETRIEVAL))
			|| (arg.equals(JOB_TYPE_SORTING))
			|| (arg.equals(JOB_TYPE_SHIPINSPECTION))
			|| (arg.equals(JOB_TYPE_MOVEMENT_RETRIEVAL))
			|| (arg.equals(JOB_TYPE_MOVEMENT_STORAGE))
			|| (arg.equals(JOB_TYPE_UNSTART))
			|| (arg.equals(JOB_TYPE_INVENTORY))
			|| (arg.equals(JOB_TYPE_INVENTORY_MINUS))
			|| (arg.equals(JOB_TYPE_INVENTORY_PLUS))
			|| (arg.equals(JOB_TYPE_EX_STORAGE))
			|| (arg.equals(JOB_TYPE_EX_RETRIEVAL)))
		{
			setValue(JOBTYPE, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wJobType";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}

	}

	//#CM705939
	/**
	 * Fetch Work type
	 * @return Work type
	 */
	public String getJobType()
	{
		return getValue(Rft.JOBTYPE).toString();
	}

	//#CM705940
	/**
	 * Set value to Status flag
	 * @param arg Status flag to be set
	 */
	public void setStatusFlag(String arg)
	{
		setValue(STATUSFLAG, arg);
	}

	//#CM705941
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(Rft.STATUSFLAG).toString();
	}

	//#CM705942
	/**
	 * Set value to Rest flag
	 * @param arg Rest flag to be set
	 */
	public void setRestFlag(String arg)
	{
		setValue(RESTFLAG, arg);
	}

	//#CM705943
	/**
	 * Fetch Rest flag
	 * @return Rest flag
	 */
	public String getRestFlag()
	{
		return getValue(Rft.RESTFLAG).toString();
	}

	//#CM705944
	/**
	 * Set value to Terminal type
	 * @param arg Terminal type to be set
	 */
	public void setTerminalType(String arg)
	{
		setValue(TERMINALTYPE, arg);
	}

	//#CM705945
	/**
	 * Fetch Terminal type
	 * @return Terminal type
	 */
	public String getTerminalType()
	{
		return getValue(Rft.TERMINALTYPE).toString();
	}

	//#CM705946
	/**
	 * Set value to IP Address
	 * @param arg IP Address to be set
	 */
	public void setIpAddress(String arg)
	{
		setValue(IPADDRESS, arg);
	}

	//#CM705947
	/**
	 * Fetch IP Address
	 * @return IP Address
	 */
	public String getIpAddress()
	{
		return getValue(Rft.IPADDRESS).toString();
	}

	//#CM705948
	/**
	 * Set value to Radio flag
	 * @param arg Radio flag to be set
	 */
	public void setRadioFlag(String arg)
	{
		setValue(RADIOFLAG, arg);
	}

	//#CM705949
	/**
	 * Fetch Radio flag
	 * @return Radio flag
	 */
	public String getRadioFlag()
	{
		return getValue(Rft.RADIOFLAG).toString();
	}

	//#CM705950
	/**
	 * Set value to Rest start time
	 * @param arg Rest start time to be set
	 */
	public void setRestStartTime(Date arg)
	{
		setValue(RESTSTARTTIME, arg);
	}

	//#CM705951
	/**
	 * Fetch Rest start time
	 * @return Rest start time
	 */
	public Date getRestStartTime()
	{
		return (Date)getValue(Rft.RESTSTARTTIME);
	}

	//#CM705952
	/**
	 * Set value to Response ID
	 * @param arg Response ID to be set
	 */
	public void setResponseId(String arg)
	{
		setValue(RESPONSEID, arg);
	}

	//#CM705953
	/**
	 * Fetch Response ID
	 * @return Response ID
	 */
	public String getResponseId()
	{
		return getValue(Rft.RESPONSEID).toString();
	}

	//#CM705954
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM705955
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(Rft.REGISTDATE);
	}

	//#CM705956
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM705957
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(Rft.REGISTPNAME).toString();
	}

	//#CM705958
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM705959
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Rft.LASTUPDATEDATE);
	}

	//#CM705960
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM705961
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Rft.LASTUPDATEPNAME).toString();
	}


	//#CM705962
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM705963
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(LASTUPDATEDATE, "");
		setValue(REGISTDATE, "");
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
	//#CM705964
	// package methods
	//#CM705965
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705966
	//------------------------------------------------------------


	//#CM705967
	//------------------------------------------------------------
	//#CM705968
	// protected methods
	//#CM705969
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705970
	//------------------------------------------------------------


	//#CM705971
	//------------------------------------------------------------
	//#CM705972
	// private methods
	//#CM705973
	//------------------------------------------------------------
	//#CM705974
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + RFTNO);
		lst.add(prefix + WORKERCODE);
		lst.add(prefix + JOBTYPE);
		lst.add(prefix + STATUSFLAG);
		lst.add(prefix + RESTFLAG);
		lst.add(prefix + TERMINALTYPE);
		lst.add(prefix + IPADDRESS);
		lst.add(prefix + RADIOFLAG);
		lst.add(prefix + RESTSTARTTIME);
		lst.add(prefix + RESPONSEID);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM705975
	//------------------------------------------------------------
	//#CM705976
	// utility methods
	//#CM705977
	//------------------------------------------------------------
	//#CM705978
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Rft.java,v 1.5 2006/11/16 02:15:39 suresh Exp $" ;
	}
}
