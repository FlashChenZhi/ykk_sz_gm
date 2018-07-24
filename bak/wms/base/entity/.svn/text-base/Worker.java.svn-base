//#CM707534
//$Id: Worker.java,v 1.4 2006/11/16 02:15:34 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM707535
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Worker;

//#CM707536
/**
 * Entity class of WORKER
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
 * @version $Revision: 1.4 $, $Date: 2006/11/16 02:15:34 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Worker
		extends AbstractEntity
{
	//#CM707537
	//------------------------------------------------------------
	//#CM707538
	// class variables (prefix '$')
	//#CM707539
	//------------------------------------------------------------
	//#CM707540
	//	private String	$classVar ;
//#CM707541
/**
	 * Work type (Administrator)
	 */
	public static final String JOB_TYPE_ADMINISTRATOR = "0001";

	//#CM707542
	/**
	 * Work type (General worker)
	 */
	public static final String JOB_TYPE_WORKER = "0002";

	//#CM707543
	/**
	 * Access authority (System administrator)
	 */
	public static final String ACCESS_AUTHORITY_SYSTEMADMINISTRATOR = "00";

	//#CM707544
	/**
	 * Access authority (Administrator)
	 */
	public static final String ACCESS_AUTHORITY_ADMINISTRATOR = "01";

	//#CM707545
	/**
	 * Access authority (Worker)
	 */
	public static final String ACCESS_AUTHORITY_WORKER = "10";

	//#CM707546
	/**
	 * Delete flag (Can be used)
	 */
	public static final String DELETE_FLAG_OPERATION = "0";

	//#CM707547
	/**
	 * Delete flag (Usage restricted)
	 */
	public static final String DELETE_FLAG_SUSPEND = "9";

	//#CM707548
	/**
	 * Male
	 */
	public static final String MALE = "0" ;

	//#CM707549
	/**
	 * Female
	 */
	public static final String FEMALE = "1" ;

	//#CM707550
	//------------------------------------------------------------
	//#CM707551
	// fields (upper case only)
	//#CM707552
	//------------------------------------------------------------
	//#CM707553
	/*
	 *  * Table name : WORKER
	 * Worker code :                   WORKERCODE          varchar2(4)
	 * Name :                          NAME                varchar2(20)
	 * Worker job type :               WORKERJOBTYPE       varchar2(4)
	 * Furigana :                      FURIGANA            varchar2(20)
	 * Sex :                           SEX                 varchar2(1)
	 * Access level :                  ACCESSAUTHORITY     varchar2(2)
	 * Password :                      PASSWORD            varchar2(8)
	 * Memo1 :                         MEMO1               varchar2(30)
	 * Memo2 :                         MEMO2               varchar2(30)
	 * Delete flag :                   DELETEFLAG          varchar2(1)
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM707554
	/**Table name definition*/

	public static final String TABLE_NAME = "DMWORKER";

	//#CM707555
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM707556
	/** Column Definition (NAME) */

	public static final FieldName NAME = new FieldName("NAME");

	//#CM707557
	/** Column Definition (WORKERJOBTYPE) */

	public static final FieldName WORKERJOBTYPE = new FieldName("WORKER_JOB_TYPE");

	//#CM707558
	/** Column Definition (FURIGANA) */

	public static final FieldName FURIGANA = new FieldName("FURIGANA");

	//#CM707559
	/** Column Definition (SEX) */

	public static final FieldName SEX = new FieldName("SEX");

	//#CM707560
	/** Column Definition (ACCESSAUTHORITY) */

	public static final FieldName ACCESSAUTHORITY = new FieldName("ACCESS_AUTHORITY");

	//#CM707561
	/** Column Definition (PASSWORD) */

	public static final FieldName PASSWORD = new FieldName("PASSWORD");

	//#CM707562
	/** Column Definition (MEMO1) */

	public static final FieldName MEMO1 = new FieldName("MEMO1");

	//#CM707563
	/** Column Definition (MEMO2) */

	public static final FieldName MEMO2 = new FieldName("MEMO2");

	//#CM707564
	/** Column Definition (DELETEFLAG) */

	public static final FieldName DELETEFLAG = new FieldName("DELETE_FLAG");

	//#CM707565
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM707566
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM707567
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM707568
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM707569
	//------------------------------------------------------------
	//#CM707570
	// properties (prefix 'p_')
	//#CM707571
	//------------------------------------------------------------
	//#CM707572
	//	private String	p_Name ;


	//#CM707573
	//------------------------------------------------------------
	//#CM707574
	// instance variables (prefix '_')
	//#CM707575
	//------------------------------------------------------------
	//#CM707576
	//	private String	_instanceVar ;

	//#CM707577
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM707578
	//------------------------------------------------------------
	//#CM707579
	// constructors
	//#CM707580
	//------------------------------------------------------------

	//#CM707581
	/**
	 * Prepare class name list and generate instance
	 */
	public Worker()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM707582
	//------------------------------------------------------------
	//#CM707583
	// accessors
	//#CM707584
	//------------------------------------------------------------

	//#CM707585
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM707586
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(Worker.WORKERCODE).toString();
	}

	//#CM707587
	/**
	 * Set value to Name
	 * @param arg Name to be set
	 */
	public void setName(String arg)
	{
		setValue(NAME, arg);
	}

	//#CM707588
	/**
	 * Fetch Name
	 * @return Name
	 */
	public String getName()
	{
		return getValue(Worker.NAME).toString();
	}

	//#CM707589
	/**
	 * Set value to Worker job type
	 * @param arg Worker job type to be set
	 */
	public void setWorkerJobType(String arg)
	{
		setValue(WORKERJOBTYPE, arg);
	}

	//#CM707590
	/**
	 * Fetch Worker job type
	 * @return Worker job type
	 */
	public String getWorkerJobType()
	{
		return getValue(Worker.WORKERJOBTYPE).toString();
	}

	//#CM707591
	/**
	 * Set value to Furigana
	 * @param arg Furigana to be set
	 */
	public void setFurigana(String arg)
	{
		setValue(FURIGANA, arg);
	}

	//#CM707592
	/**
	 * Fetch Furigana
	 * @return Furigana
	 */
	public String getFurigana()
	{
		return getValue(Worker.FURIGANA).toString();
	}

	//#CM707593
	/**
	 * Set value to Sex
	 * @param arg Sex to be set
	 */
	public void setSex(String arg)
	{
		setValue(SEX, arg);
	}

	//#CM707594
	/**
	 * Fetch Sex
	 * @return Sex
	 */
	public String getSex()
	{
		return getValue(Worker.SEX).toString();
	}

	//#CM707595
	/**
	 * Set value to Access level
	 * @param arg Access level to be set
	 */
	public void setAccessAuthority(String arg)
	{
		setValue(ACCESSAUTHORITY, arg);
	}

	//#CM707596
	/**
	 * Fetch Access level
	 * @return Access level
	 */
	public String getAccessAuthority()
	{
		return getValue(Worker.ACCESSAUTHORITY).toString();
	}

	//#CM707597
	/**
	 * Set value to Password
	 * @param arg Password to be set
	 */
	public void setPassword(String arg)
	{
		setValue(PASSWORD, arg);
	}

	//#CM707598
	/**
	 * Fetch Password
	 * @return Password
	 */
	public String getPassword()
	{
		return getValue(Worker.PASSWORD).toString();
	}

	//#CM707599
	/**
	 * Set value to Memo1
	 * @param arg Memo1 to be set
	 */
	public void setMemo1(String arg)
	{
		setValue(MEMO1, arg);
	}

	//#CM707600
	/**
	 * Fetch Memo1
	 * @return Memo1
	 */
	public String getMemo1()
	{
		return getValue(Worker.MEMO1).toString();
	}

	//#CM707601
	/**
	 * Set value to Memo2
	 * @param arg Memo2 to be set
	 */
	public void setMemo2(String arg)
	{
		setValue(MEMO2, arg);
	}

	//#CM707602
	/**
	 * Fetch Memo2
	 * @return Memo2
	 */
	public String getMemo2()
	{
		return getValue(Worker.MEMO2).toString();
	}

	//#CM707603
	/**
	 * Set value to Delete flag
	 * @param arg Delete flag to be set
	 */
	public void setDeleteFlag(String arg)
	{
		setValue(DELETEFLAG, arg);
	}

	//#CM707604
	/**
	 * Fetch Delete flag
	 * @return Delete flag
	 */
	public String getDeleteFlag()
	{
		return getValue(Worker.DELETEFLAG).toString();
	}

	//#CM707605
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM707606
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(Worker.REGISTDATE);
	}

	//#CM707607
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM707608
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(Worker.REGISTPNAME).toString();
	}

	//#CM707609
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM707610
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Worker.LASTUPDATEDATE);
	}

	//#CM707611
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM707612
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Worker.LASTUPDATEPNAME).toString();
	}


	//#CM707613
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}



	public void setInitCreateColumn()
	 {
		 setValue(LASTUPDATEDATE, "");
		 setValue(REGISTDATE, "");
	 }


	//#CM707614
	/**
	  * Set the automatic update row name. <BR>
	  * Use it while updating it.<BR>
	  * @return Array of automatic update row name
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

	//#CM707615
	//------------------------------------------------------------
	//#CM707616
	// package methods
	//#CM707617
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707618
	//------------------------------------------------------------


	//#CM707619
	//------------------------------------------------------------
	//#CM707620
	// protected methods
	//#CM707621
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707622
	//------------------------------------------------------------


	//#CM707623
	//------------------------------------------------------------
	//#CM707624
	// private methods
	//#CM707625
	//------------------------------------------------------------
	//#CM707626
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + WORKERCODE);
		lst.add(prefix + NAME);
		lst.add(prefix + WORKERJOBTYPE);
		lst.add(prefix + FURIGANA);
		lst.add(prefix + SEX);
		lst.add(prefix + ACCESSAUTHORITY);
		lst.add(prefix + PASSWORD);
		lst.add(prefix + MEMO1);
		lst.add(prefix + MEMO2);
		lst.add(prefix + DELETEFLAG);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM707627
	//------------------------------------------------------------
	//#CM707628
	// utility methods
	//#CM707629
	//------------------------------------------------------------
	//#CM707630
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Worker.java,v 1.4 2006/11/16 02:15:34 suresh Exp $" ;
	}
}
