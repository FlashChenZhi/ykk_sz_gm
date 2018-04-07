//#CM707718
//$Id: WorkingData.java,v 1.4 2006/11/13 04:30:55 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM707719
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.WorkingData;

//#CM707720
/**
 * Entity class of WORKINGDATA
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
 * @version $Revision: 1.4 $, $Date: 2006/11/13 04:30:55 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WorkingData
		extends AbstractEntity
{
	//#CM707721
	//------------------------------------------------------------
	//#CM707722
	// class variables (prefix '$')
	//#CM707723
	//------------------------------------------------------------
	//#CM707724
	//	private String	$classVar ;

	//#CM707725
	//------------------------------------------------------------
	//#CM707726
	// fields (upper case only)
	//#CM707727
	//------------------------------------------------------------
	//#CM707728
	/*
	 *  * Table name : WORKINGDATA
	 * machine number :                RFTNO               varchar2(3)
	 * file name :                     FILENAME            varchar2(30)
	 * line number :                   LINENO              number
	 * data :                          CONTENTS            varchar2(1024)
	 */

	//#CM707729
	/**Table name definition*/

	public static final String TABLE_NAME = "DNWORKINGDATA";

	//#CM707730
	/** Column Definition (RFTNO) */

	public static final FieldName RFTNO = new FieldName("RFT_NO");

	//#CM707731
	/** Column Definition (FILENAME) */

	public static final FieldName FILENAME = new FieldName("FILE_NAME");

	//#CM707732
	/** Column Definition (LINENO) */

	public static final FieldName LINENO = new FieldName("LINE_NO");

	//#CM707733
	/** Column Definition (CONTENTS) */

	public static final FieldName CONTENTS = new FieldName("CONTENTS");


	//#CM707734
	//------------------------------------------------------------
	//#CM707735
	// properties (prefix 'p_')
	//#CM707736
	//------------------------------------------------------------
	//#CM707737
	//	private String	p_Name ;


	//#CM707738
	//------------------------------------------------------------
	//#CM707739
	// instance variables (prefix '_')
	//#CM707740
	//------------------------------------------------------------
	//#CM707741
	//	private String	_instanceVar ;

	//#CM707742
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM707743
	//------------------------------------------------------------
	//#CM707744
	// constructors
	//#CM707745
	//------------------------------------------------------------

	//#CM707746
	/**
	 * Prepare class name list and generate instance
	 */
	public WorkingData()
	{
		super() ;
		prepare() ;
	}

	//#CM707747
	//------------------------------------------------------------
	//#CM707748
	// accessors
	//#CM707749
	//------------------------------------------------------------

	//#CM707750
	/**
	 * Set value to machine number
	 * @param arg machine number to be set
	 */
	public void setRftNo(String arg)
	{
		setValue(RFTNO, arg);
	}

	//#CM707751
	/**
	 * Fetch machine number
	 * @return machine number
	 */
	public String getRftNo()
	{
		return getValue(WorkingData.RFTNO).toString();
	}

	//#CM707752
	/**
	 * Set value to file name
	 * @param arg file name to be set
	 */
	public void setFileName(String arg)
	{
		setValue(FILENAME, arg);
	}

	//#CM707753
	/**
	 * Fetch file name
	 * @return file name
	 */
	public String getFileName()
	{
		return getValue(WorkingData.FILENAME).toString();
	}

	//#CM707754
	/**
	 * Set value to line number
	 * @param arg line number to be set
	 */
	public void setLineNo(int arg)
	{
		setValue(LINENO, new Integer(arg));
	}

	//#CM707755
	/**
	 * Fetch line number
	 * @return line number
	 */
	public int getLineNo()
	{
		return getBigDecimal(WorkingData.LINENO).intValue();
	}

	//#CM707756
	/**
	 * Set value to data
	 * @param arg data to be set
	 */
	public void setContents(String arg)
	{
		setValue(CONTENTS, arg);
	}

	//#CM707757
	/**
	 * Fetch data
	 * @return data
	 */
	public String getContents()
	{
		return getValue(WorkingData.CONTENTS).toString();
	}


	//#CM707758
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM707759
	//------------------------------------------------------------
	//#CM707760
	// package methods
	//#CM707761
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707762
	//------------------------------------------------------------


	//#CM707763
	//------------------------------------------------------------
	//#CM707764
	// protected methods
	//#CM707765
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707766
	//------------------------------------------------------------


	//#CM707767
	//------------------------------------------------------------
	//#CM707768
	// private methods
	//#CM707769
	//------------------------------------------------------------
	//#CM707770
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + RFTNO);
		lst.add(prefix + FILENAME);
		lst.add(prefix + LINENO);
		lst.add(prefix + CONTENTS);
	}


	//#CM707771
	//------------------------------------------------------------
	//#CM707772
	// utility methods
	//#CM707773
	//------------------------------------------------------------
	//#CM707774
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: WorkingData.java,v 1.4 2006/11/13 04:30:55 suresh Exp $" ;
	}
}
