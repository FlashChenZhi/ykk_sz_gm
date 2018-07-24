//$Id: WorkerAlterKey.java,v 1.2 2006/11/09 07:50:21 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.Worker;

/**
 * Update key class for WORKER use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:50:21 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WorkerAlterKey
		extends AbstractSQLAlterKey
{
	//------------------------------------------------------------
	// class variables (Prefix '$')
	//------------------------------------------------------------
	//	private String	$classVar ;

	//------------------------------------------------------------
	// fields (upper case only)
	//------------------------------------------------------------
	//	public static final int FIELD_VALUE = 1 ;

	//------------------------------------------------------------
	// properties (Prefix 'p_')
	//------------------------------------------------------------
	//	private String	p_Name ;

	//------------------------------------------------------------
	// instance variables (Prefix '_')
	//------------------------------------------------------------
	//	private String	_instanceVar ;

	private static String _Prefix = "";

	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------
	/**
	 * Prepare table name and column list and generate instance
	 * 
	 */
	public WorkerAlterKey()
	{
		super(new Worker()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for Worker code
	 * @param arg Worker code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.WORKERCODE.toString(), arg);
	}
	/**
	 * Set search value for Worker code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.WORKERCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.WORKERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Worker code
	 * @param arg Update value for Worker code
	 */
	public void updateWorkerCode(String arg)
	{
		setUpdValue(_Prefix + Worker.WORKERCODE.toString(), arg);
	}

	/**
	 * Set search value for Name
	 * @param arg Name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.NAME.toString(), arg);
	}
	/**
	 * Set search value for Name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.NAME.toString(), arg);
	}
	/**
	 *Set search value for  Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.NAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.NAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Name
	 * @param arg Update value for Name
	 */
	public void updateName(String arg)
	{
		setUpdValue(_Prefix + Worker.NAME.toString(), arg);
	}

	/**
	 * Set search value for Worker job type
	 * @param arg Worker job type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerJobType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.WORKERJOBTYPE.toString(), arg);
	}
	/**
	 * Set search value for Worker job type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerJobType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.WORKERJOBTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Worker job type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerJobType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.WORKERJOBTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Worker job type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerJobType(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.WORKERJOBTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Worker job type
	 * @param arg Update value for Worker job type
	 */
	public void updateWorkerJobType(String arg)
	{
		setUpdValue(_Prefix + Worker.WORKERJOBTYPE.toString(), arg);
	}

	/**
	 * Set search value for Furigana
	 * @param arg Furigana<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFurigana(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.FURIGANA.toString(), arg);
	}
	/**
	 * Set search value for Furigana
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFurigana(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.FURIGANA.toString(), arg);
	}
	/**
	 *Set search value for  Furigana
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFurigana(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.FURIGANA.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Furigana
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFurigana(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.FURIGANA.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Furigana
	 * @param arg Update value for Furigana
	 */
	public void updateFurigana(String arg)
	{
		setUpdValue(_Prefix + Worker.FURIGANA.toString(), arg);
	}

	/**
	 * Set search value for Sex
	 * @param arg Sex<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSex(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.SEX.toString(), arg);
	}
	/**
	 * Set search value for Sex
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSex(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.SEX.toString(), arg);
	}
	/**
	 *Set search value for  Sex
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSex(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.SEX.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Sex
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSex(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.SEX.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Sex
	 * @param arg Update value for Sex
	 */
	public void updateSex(String arg)
	{
		setUpdValue(_Prefix + Worker.SEX.toString(), arg);
	}

	/**
	 * Set search value for Access level
	 * @param arg Access level<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessAuthority(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.ACCESSAUTHORITY.toString(), arg);
	}
	/**
	 * Set search value for Access level
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessAuthority(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.ACCESSAUTHORITY.toString(), arg);
	}
	/**
	 *Set search value for  Access level
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessAuthority(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.ACCESSAUTHORITY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Access level
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessAuthority(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.ACCESSAUTHORITY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Access level
	 * @param arg Update value for Access level
	 */
	public void updateAccessAuthority(String arg)
	{
		setUpdValue(_Prefix + Worker.ACCESSAUTHORITY.toString(), arg);
	}

	/**
	 * Set search value for Password
	 * @param arg Password<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPassword(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.PASSWORD.toString(), arg);
	}
	/**
	 * Set search value for Password
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPassword(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.PASSWORD.toString(), arg);
	}
	/**
	 *Set search value for  Password
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPassword(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.PASSWORD.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Password
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPassword(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.PASSWORD.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Password
	 * @param arg Update value for Password
	 */
	public void updatePassword(String arg)
	{
		setUpdValue(_Prefix + Worker.PASSWORD.toString(), arg);
	}

	/**
	 * Set search value for Memo1
	 * @param arg Memo1<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMemo1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.MEMO1.toString(), arg);
	}
	/**
	 * Set search value for Memo1
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMemo1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.MEMO1.toString(), arg);
	}
	/**
	 *Set search value for  Memo1
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMemo1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.MEMO1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Memo1
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMemo1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.MEMO1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Memo1
	 * @param arg Update value for Memo1
	 */
	public void updateMemo1(String arg)
	{
		setUpdValue(_Prefix + Worker.MEMO1.toString(), arg);
	}

	/**
	 * Set search value for Memo2
	 * @param arg Memo2<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMemo2(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.MEMO2.toString(), arg);
	}
	/**
	 * Set search value for Memo2
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMemo2(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.MEMO2.toString(), arg);
	}
	/**
	 *Set search value for  Memo2
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMemo2(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.MEMO2.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Memo2
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMemo2(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.MEMO2.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Memo2
	 * @param arg Update value for Memo2
	 */
	public void updateMemo2(String arg)
	{
		setUpdValue(_Prefix + Worker.MEMO2.toString(), arg);
	}

	/**
	 * Set search value for Delete flag
	 * @param arg Delete flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.DELETEFLAG.toString(), arg);
	}
	/**
	 * Set search value for Delete flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.DELETEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Delete flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.DELETEFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Delete flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.DELETEFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Delete flag
	 * @param arg Update value for Delete flag
	 */
	public void updateDeleteFlag(String arg)
	{
		setUpdValue(_Prefix + Worker.DELETEFLAG.toString(), arg);
	}

	/**
	 * Set search value for Registered date
	 * @param arg Registered date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.REGISTDATE.toString(), arg);
	}
	/**
	 * Set search value for Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.REGISTDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered date
	 * @param arg Update value for Registered date
	 */
	public void updateRegistDate(Date arg)
	{
		setUpdValue(_Prefix + Worker.REGISTDATE.toString(), arg);
	}

	/**
	 * Set search value for Registered name
	 * @param arg Registered name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.REGISTPNAME.toString(), arg);
	}
	/**
	 * Set search value for Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.REGISTPNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered name
	 * @param arg Update value for Registered name
	 */
	public void updateRegistPname(String arg)
	{
		setUpdValue(_Prefix + Worker.REGISTPNAME.toString(), arg);
	}

	/**
	 * Set search value for Last update date
	 * @param arg Last update date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 * Set search value for Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.LASTUPDATEDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update date
	 * @param arg Update value for Last update date
	 */
	public void updateLastUpdateDate(Date arg)
	{
		setUpdValue(_Prefix + Worker.LASTUPDATEDATE.toString(), arg);
	}

	/**
	 * Set search value for Last update name
	 * @param arg Last update name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 * Set search value for Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Worker.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Worker.LASTUPDATEPNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Worker.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update name
	 * @param arg Update value for Last update name
	 */
	public void updateLastUpdatePname(String arg)
	{
		setUpdValue(_Prefix + Worker.LASTUPDATEPNAME.toString(), arg);
	}

	//------------------------------------------------------------
	// utility methods
	//------------------------------------------------------------
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: WorkerAlterKey.java,v 1.2 2006/11/09 07:50:21 suresh Exp $" ;
	}
}
