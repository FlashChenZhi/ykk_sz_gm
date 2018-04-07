//#CM707631
//$Id: WorkerResult.java,v 1.5 2006/11/16 02:15:33 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM707632
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
import jp.co.daifuku.wms.base.entity.WorkerResult;

//#CM707633
/**
 * Entity class of WORKERRESULT
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:33 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WorkerResult
		extends AbstractEntity
{
	//#CM707634
	//------------------------------------------------------------
	//#CM707635
	// class variables (prefix '$')
	//#CM707636
	//------------------------------------------------------------
	//#CM707637
	//	private String	$classVar ;

	//#CM707638
	//------------------------------------------------------------
	//#CM707639
	// fields (upper case only)
	//#CM707640
	//------------------------------------------------------------
	//#CM707641
	/*
	 *  * Table name : WORKERRESULT
	 * Work date :                     WORKDATE            varchar2(8)
	 * Work Start time :               WORKSTARTTIME       date
	 * Work End time :                 WORKENDTIME         date
	 * Worker code :                   WORKERCODE          varchar2(4)
	 * Worker name :                   WORKERNAME          varchar2(20)
	 * Terminal No :                   TERMINALNO          varchar2(3)
	 * Work type :                     JOBTYPE             varchar2(2)
	 * Work qty :                      WORKQTY             number
	 * Work count :                    WORKCNT             number
	 * Order count :                   ORDERCNT            number
	 * Work time :                     WORKTIME            number
	 * Rest time :                     RESTTIME            number
	 * Actual work time :              REALWORKTIME        number
	 * Miss count :                    MISSSCANCNT         number
	 */

	//#CM707642
	/**Table name definition*/

	public static final String TABLE_NAME = "DNWORKERRESULT";

	//#CM707643
	/** Column Definition (WORKDATE) */

	public static final FieldName WORKDATE = new FieldName("WORK_DATE");

	//#CM707644
	/** Column Definition (WORKSTARTTIME) */

	public static final FieldName WORKSTARTTIME = new FieldName("WORK_START_TIME");

	//#CM707645
	/** Column Definition (WORKENDTIME) */

	public static final FieldName WORKENDTIME = new FieldName("WORK_END_TIME");

	//#CM707646
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM707647
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM707648
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM707649
	/** Column Definition (JOBTYPE) */

	public static final FieldName JOBTYPE = new FieldName("JOB_TYPE");

	//#CM707650
	/** Column Definition (WORKQTY) */

	public static final FieldName WORKQTY = new FieldName("WORK_QTY");

	//#CM707651
	/** Column Definition (WORKCNT) */

	public static final FieldName WORKCNT = new FieldName("WORK_CNT");

	//#CM707652
	/** Column Definition (ORDERCNT) */

	public static final FieldName ORDERCNT = new FieldName("ORDER_CNT");

	//#CM707653
	/** Column Definition (WORKTIME) */

	public static final FieldName WORKTIME = new FieldName("WORK_TIME");

	//#CM707654
	/** Column Definition (RESTTIME) */

	public static final FieldName RESTTIME = new FieldName("REST_TIME");

	//#CM707655
	/** Column Definition (REALWORKTIME) */

	public static final FieldName REALWORKTIME = new FieldName("REAL_WORK_TIME");

	//#CM707656
	/** Column Definition (MISSSCANCNT) */

	public static final FieldName MISSSCANCNT = new FieldName("MISS_SCAN_CNT");


	//#CM707657
	//------------------------------------------------------------
	//#CM707658
	// properties (prefix 'p_')
	//#CM707659
	//------------------------------------------------------------
	//#CM707660
	//	private String	p_Name ;


	//#CM707661
	//------------------------------------------------------------
	//#CM707662
	// instance variables (prefix '_')
	//#CM707663
	//------------------------------------------------------------
	//#CM707664
	//	private String	_instanceVar ;

	//#CM707665
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM707666
	//------------------------------------------------------------
	//#CM707667
	// constructors
	//#CM707668
	//------------------------------------------------------------

	//#CM707669
	/**
	 * Prepare class name list and generate instance
	 */
	public WorkerResult()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM707670
	//------------------------------------------------------------
	//#CM707671
	// accessors
	//#CM707672
	//------------------------------------------------------------

	//#CM707673
	/**
	 * Set value to Work date
	 * @param arg Work date to be set
	 */
	public void setWorkDate(String arg)
	{
		setValue(WORKDATE, arg);
	}

	//#CM707674
	/**
	 * Fetch Work date
	 * @return Work date
	 */
	public String getWorkDate()
	{
		return getValue(WorkerResult.WORKDATE).toString();
	}

	//#CM707675
	/**
	 * Set value to Work Start time
	 * @param arg Work Start time to be set
	 */
	public void setWorkStartTime(Date arg)
	{
		setValue(WORKSTARTTIME, arg);
	}

	//#CM707676
	/**
	 * Fetch Work Start time
	 * @return Work Start time
	 */
	public Date getWorkStartTime()
	{
		return (Date)getValue(WorkerResult.WORKSTARTTIME);
	}

	//#CM707677
	/**
	 * Set value to Work End time
	 * @param arg Work End time to be set
	 */
	public void setWorkEndTime(Date arg)
	{
		setValue(WORKENDTIME, arg);
	}

	//#CM707678
	/**
	 * Fetch Work End time
	 * @return Work End time
	 */
	public Date getWorkEndTime()
	{
		return (Date)getValue(WorkerResult.WORKENDTIME);
	}

	//#CM707679
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM707680
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(WorkerResult.WORKERCODE).toString();
	}

	//#CM707681
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM707682
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(WorkerResult.WORKERNAME).toString();
	}

	//#CM707683
	/**
	 * Set value to Terminal No
	 * @param arg Terminal No to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM707684
	/**
	 * Fetch Terminal No
	 * @return Terminal No
	 */
	public String getTerminalNo()
	{
		return getValue(WorkerResult.TERMINALNO).toString();
	}

	//#CM707685
	/**
	 * Set value to Work type
	 * @param arg Work type to be set
	 */
	public void setJobType(String arg)
	{
		setValue(JOBTYPE, arg);
	}

	//#CM707686
	/**
	 * Fetch Work type
	 * @return Work type
	 */
	public String getJobType()
	{
		return getValue(WorkerResult.JOBTYPE).toString();
	}

	//#CM707687
	/**
	 * Set value to Work qty
	 * @param arg Work qty to be set
	 */
	public void setWorkQty(int arg)
	{
		setValue(WORKQTY, new Integer(arg));
	}

	//#CM707688
	/**
	 * Fetch Work qty
	 * @return Work qty
	 */
	public int getWorkQty()
	{
		return getBigDecimal(WorkerResult.WORKQTY).intValue();
	}

	//#CM707689
	/**
	 * Set value to Work count
	 * @param arg Work count to be set
	 */
	public void setWorkCnt(int arg)
	{
		setValue(WORKCNT, new Integer(arg));
	}

	//#CM707690
	/**
	 * Fetch Work count
	 * @return Work count
	 */
	public int getWorkCnt()
	{
		return getBigDecimal(WorkerResult.WORKCNT).intValue();
	}

	//#CM707691
	/**
	 * Set value to Order count
	 * @param arg Order count to be set
	 */
	public void setOrderCnt(int arg)
	{
		setValue(ORDERCNT, new Integer(arg));
	}

	//#CM707692
	/**
	 * Fetch Order count
	 * @return Order count
	 */
	public int getOrderCnt()
	{
		return getBigDecimal(WorkerResult.ORDERCNT).intValue();
	}

	//#CM707693
	/**
	 * Set value to Work time
	 * @param arg Work time to be set
	 */
	public void setWorkTime(int arg)
	{
		setValue(WORKTIME, new Integer(arg));
	}

	//#CM707694
	/**
	 * Fetch Work time
	 * @return Work time
	 */
	public int getWorkTime()
	{
		return getBigDecimal(WorkerResult.WORKTIME).intValue();
	}

	//#CM707695
	/**
	 * Set value to Rest time
	 * @param arg Rest time to be set
	 */
	public void setRestTime(int arg)
	{
		setValue(RESTTIME, new Integer(arg));
	}

	//#CM707696
	/**
	 * Fetch Rest time
	 * @return Rest time
	 */
	public int getRestTime()
	{
		return getBigDecimal(WorkerResult.RESTTIME).intValue();
	}

	//#CM707697
	/**
	 * Set value to Actual work time
	 * @param arg Actual work time to be set
	 */
	public void setRealWorkTime(int arg)
	{
		setValue(REALWORKTIME, new Integer(arg));
	}

	//#CM707698
	/**
	 * Fetch Actual work time
	 * @return Actual work time
	 */
	public int getRealWorkTime()
	{
		return getBigDecimal(WorkerResult.REALWORKTIME).intValue();
	}

	//#CM707699
	/**
	 * Set value to Miss count
	 * @param arg Miss count to be set
	 */
	public void setMissScanCnt(int arg)
	{
		setValue(MISSSCANCNT, new Integer(arg));
	}

	//#CM707700
	/**
	 * Fetch Miss count
	 * @return Miss count
	 */
	public int getMissScanCnt()
	{
		return getBigDecimal(WorkerResult.MISSSCANCNT).intValue();
	}


	//#CM707701
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM707702
	/**
	*
	*
	*/

	public void setInitCreateColumn()
	{
		setValue(WORKQTY, new Integer(0));
		setValue(WORKCNT, new Integer(0));
		setValue(ORDERCNT, new Integer(0));
		setValue(WORKTIME, new Integer(0));
		setValue(RESTTIME, new Integer(0));
		setValue(REALWORKTIME, new Integer(0));
		setValue(MISSSCANCNT, new Integer(0));
	}
	//------------------------------------------------------------
	//#CM707703
	// package methods
	//#CM707704
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707705
	//------------------------------------------------------------


	//#CM707706
	//------------------------------------------------------------
	//#CM707707
	// protected methods
	//#CM707708
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707709
	//------------------------------------------------------------


	//#CM707710
	//------------------------------------------------------------
	//#CM707711
	// private methods
	//#CM707712
	//------------------------------------------------------------
	//#CM707713
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + WORKDATE);
		lst.add(prefix + WORKSTARTTIME);
		lst.add(prefix + WORKENDTIME);
		lst.add(prefix + WORKERCODE);
		lst.add(prefix + WORKERNAME);
		lst.add(prefix + TERMINALNO);
		lst.add(prefix + JOBTYPE);
		lst.add(prefix + WORKQTY);
		lst.add(prefix + WORKCNT);
		lst.add(prefix + ORDERCNT);
		lst.add(prefix + WORKTIME);
		lst.add(prefix + RESTTIME);
		lst.add(prefix + REALWORKTIME);
		lst.add(prefix + MISSSCANCNT);
	}


	//#CM707714
	//------------------------------------------------------------
	//#CM707715
	// utility methods
	//#CM707716
	//------------------------------------------------------------
	//#CM707717
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: WorkerResult.java,v 1.5 2006/11/16 02:15:33 suresh Exp $" ;
	}
}
