//$Id: WorkerSearchKey.java,v 1.3 2006/11/13 04:32:54 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.Worker;

/**
 * This is the search key class for WORKER use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:32:54 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WorkerSearchKey
		extends AbstractSQLSearchKey
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
	public WorkerSearchKey()
	{
		super(new Worker()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
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
	 * Set sort order for Worker code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.WORKERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Worker code
	 * @param num grouping order
	 */
	public void setWorkerCodeGroup(int num)
	{
		setGroup(_Prefix + Worker.WORKERCODE.toString(), num);
	}

	/**
	 * Fetch Worker code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkerCodeCollect()
	{
		setCollect(_Prefix + Worker.WORKERCODE.toString(), "");
	}

	/**
	 * Fetch Worker code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkerCodeCollect(String compcode)
	{
		setCollect(_Prefix + Worker.WORKERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.NAME.toString(), arg);
	}
	/**
	 *Set search value for  Name
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
	 * Set sort order for Name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.NAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Name
	 * @param num grouping order
	 */
	public void setNameGroup(int num)
	{
		setGroup(_Prefix + Worker.NAME.toString(), num);
	}

	/**
	 * Fetch Name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setNameCollect()
	{
		setCollect(_Prefix + Worker.NAME.toString(), "");
	}

	/**
	 * Fetch Name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setNameCollect(String compcode)
	{
		setCollect(_Prefix + Worker.NAME.toString(), compcode);
	}

	/**
	 *Set search value for  Worker job type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerJobType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.WORKERJOBTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Worker job type
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
	 * Set sort order for Worker job type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkerJobTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.WORKERJOBTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order Worker job type
	 * @param num grouping order
	 */
	public void setWorkerJobTypeGroup(int num)
	{
		setGroup(_Prefix + Worker.WORKERJOBTYPE.toString(), num);
	}

	/**
	 * Fetch Worker job type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkerJobTypeCollect()
	{
		setCollect(_Prefix + Worker.WORKERJOBTYPE.toString(), "");
	}

	/**
	 * Fetch Worker job type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkerJobTypeCollect(String compcode)
	{
		setCollect(_Prefix + Worker.WORKERJOBTYPE.toString(), compcode);
	}

	/**
	 *Set search value for  Furigana
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFurigana(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.FURIGANA.toString(), arg);
	}
	/**
	 *Set search value for  Furigana
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
	 * Set sort order for Furigana
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setFuriganaOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.FURIGANA.toString(), num, bool);
	}

	/**
	 * Set grouping order Furigana
	 * @param num grouping order
	 */
	public void setFuriganaGroup(int num)
	{
		setGroup(_Prefix + Worker.FURIGANA.toString(), num);
	}

	/**
	 * Fetch Furigana info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setFuriganaCollect()
	{
		setCollect(_Prefix + Worker.FURIGANA.toString(), "");
	}

	/**
	 * Fetch Furigana info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setFuriganaCollect(String compcode)
	{
		setCollect(_Prefix + Worker.FURIGANA.toString(), compcode);
	}

	/**
	 *Set search value for  Sex
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSex(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.SEX.toString(), arg);
	}
	/**
	 *Set search value for  Sex
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
	 * Set sort order for Sex
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSexOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.SEX.toString(), num, bool);
	}

	/**
	 * Set grouping order Sex
	 * @param num grouping order
	 */
	public void setSexGroup(int num)
	{
		setGroup(_Prefix + Worker.SEX.toString(), num);
	}

	/**
	 * Fetch Sex info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSexCollect()
	{
		setCollect(_Prefix + Worker.SEX.toString(), "");
	}

	/**
	 * Fetch Sex info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSexCollect(String compcode)
	{
		setCollect(_Prefix + Worker.SEX.toString(), compcode);
	}

	/**
	 *Set search value for  Access level
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessAuthority(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.ACCESSAUTHORITY.toString(), arg);
	}
	/**
	 *Set search value for  Access level
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
	 * Set sort order for Access level
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAccessAuthorityOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.ACCESSAUTHORITY.toString(), num, bool);
	}

	/**
	 * Set grouping order Access level
	 * @param num grouping order
	 */
	public void setAccessAuthorityGroup(int num)
	{
		setGroup(_Prefix + Worker.ACCESSAUTHORITY.toString(), num);
	}

	/**
	 * Fetch Access level info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAccessAuthorityCollect()
	{
		setCollect(_Prefix + Worker.ACCESSAUTHORITY.toString(), "");
	}

	/**
	 * Fetch Access level info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAccessAuthorityCollect(String compcode)
	{
		setCollect(_Prefix + Worker.ACCESSAUTHORITY.toString(), compcode);
	}

	/**
	 *Set search value for  Password
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPassword(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.PASSWORD.toString(), arg);
	}
	/**
	 *Set search value for  Password
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
	 * Set sort order for Password
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPasswordOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.PASSWORD.toString(), num, bool);
	}

	/**
	 * Set grouping order Password
	 * @param num grouping order
	 */
	public void setPasswordGroup(int num)
	{
		setGroup(_Prefix + Worker.PASSWORD.toString(), num);
	}

	/**
	 * Fetch Password info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPasswordCollect()
	{
		setCollect(_Prefix + Worker.PASSWORD.toString(), "");
	}

	/**
	 * Fetch Password info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPasswordCollect(String compcode)
	{
		setCollect(_Prefix + Worker.PASSWORD.toString(), compcode);
	}

	/**
	 *Set search value for  Memo1
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMemo1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.MEMO1.toString(), arg);
	}
	/**
	 *Set search value for  Memo1
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
	 * Set sort order for Memo1
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setMemo1Order(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.MEMO1.toString(), num, bool);
	}

	/**
	 * Set grouping order Memo1
	 * @param num grouping order
	 */
	public void setMemo1Group(int num)
	{
		setGroup(_Prefix + Worker.MEMO1.toString(), num);
	}

	/**
	 * Fetch Memo1 info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setMemo1Collect()
	{
		setCollect(_Prefix + Worker.MEMO1.toString(), "");
	}

	/**
	 * Fetch Memo1 info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setMemo1Collect(String compcode)
	{
		setCollect(_Prefix + Worker.MEMO1.toString(), compcode);
	}

	/**
	 *Set search value for  Memo2
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMemo2(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.MEMO2.toString(), arg);
	}
	/**
	 *Set search value for  Memo2
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
	 * Set sort order for Memo2
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setMemo2Order(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.MEMO2.toString(), num, bool);
	}

	/**
	 * Set grouping order Memo2
	 * @param num grouping order
	 */
	public void setMemo2Group(int num)
	{
		setGroup(_Prefix + Worker.MEMO2.toString(), num);
	}

	/**
	 * Fetch Memo2 info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setMemo2Collect()
	{
		setCollect(_Prefix + Worker.MEMO2.toString(), "");
	}

	/**
	 * Fetch Memo2 info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setMemo2Collect(String compcode)
	{
		setCollect(_Prefix + Worker.MEMO2.toString(), compcode);
	}

	/**
	 *Set search value for  Delete flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.DELETEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Delete flag
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
	 * Set sort order for Delete flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setDeleteFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.DELETEFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Delete flag
	 * @param num grouping order
	 */
	public void setDeleteFlagGroup(int num)
	{
		setGroup(_Prefix + Worker.DELETEFLAG.toString(), num);
	}

	/**
	 * Fetch Delete flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setDeleteFlagCollect()
	{
		setCollect(_Prefix + Worker.DELETEFLAG.toString(), "");
	}

	/**
	 * Fetch Delete flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setDeleteFlagCollect(String compcode)
	{
		setCollect(_Prefix + Worker.DELETEFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
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
	 * Set sort order for Registered date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.REGISTDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered date
	 * @param num grouping order
	 */
	public void setRegistDateGroup(int num)
	{
		setGroup(_Prefix + Worker.REGISTDATE.toString(), num);
	}

	/**
	 * Fetch Registered date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistDateCollect()
	{
		setCollect(_Prefix + Worker.REGISTDATE.toString(), "");
	}

	/**
	 * Fetch Registered date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistDateCollect(String compcode)
	{
		setCollect(_Prefix + Worker.REGISTDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
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
	 * Set sort order for Registered name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistPnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.REGISTPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered name
	 * @param num grouping order
	 */
	public void setRegistPnameGroup(int num)
	{
		setGroup(_Prefix + Worker.REGISTPNAME.toString(), num);
	}

	/**
	 * Fetch Registered name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistPnameCollect()
	{
		setCollect(_Prefix + Worker.REGISTPNAME.toString(), "");
	}

	/**
	 * Fetch Registered name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistPnameCollect(String compcode)
	{
		setCollect(_Prefix + Worker.REGISTPNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
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
	 * Set sort order for Last update date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.LASTUPDATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update date
	 * @param num grouping order
	 */
	public void setLastUpdateDateGroup(int num)
	{
		setGroup(_Prefix + Worker.LASTUPDATEDATE.toString(), num);
	}

	/**
	 * Fetch Last update date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdateDateCollect()
	{
		setCollect(_Prefix + Worker.LASTUPDATEDATE.toString(), "");
	}

	/**
	 * Fetch Last update date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdateDateCollect(String compcode)
	{
		setCollect(_Prefix + Worker.LASTUPDATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Worker.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
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
	 * Set sort order for Last update name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdatePnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Worker.LASTUPDATEPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update name
	 * @param num grouping order
	 */
	public void setLastUpdatePnameGroup(int num)
	{
		setGroup(_Prefix + Worker.LASTUPDATEPNAME.toString(), num);
	}

	/**
	 * Fetch Last update name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdatePnameCollect()
	{
		setCollect(_Prefix + Worker.LASTUPDATEPNAME.toString(), "");
	}

	/**
	 * Fetch Last update name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdatePnameCollect(String compcode)
	{
		setCollect(_Prefix + Worker.LASTUPDATEPNAME.toString(), compcode);
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
		return "$Id: WorkerSearchKey.java,v 1.3 2006/11/13 04:32:54 suresh Exp $" ;
	}
}
