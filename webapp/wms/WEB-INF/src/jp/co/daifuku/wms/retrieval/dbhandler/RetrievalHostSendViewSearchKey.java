// $Id: RetrievalHostSendViewSearchKey.java,v 1.3 2007/02/07 04:16:12 suresh Exp $
package jp.co.daifuku.wms.retrieval.dbhandler;

import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.HostSendViewSearchKey;

//#CM709043
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM709044
/**
 * Allow this class to set the key to search through the Dvhostsendview table.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:16:12 $
 * @author  $Author: suresh $
 */
public class RetrievalHostSendViewSearchKey extends HostSendViewSearchKey
{
	//#CM709045
	// Class fields --------------------------------------------------
	//#CM709046
	// table name
	private static final String TABLE = "DVHOSTSENDVIEW"; 

	//#CM709047
	// Define columns potential to be connected here.
	private static final String PLANUKEY = "PLAN_UKEY";
	
	//#CM709048
	// Class variables -----------------------------------------------

	//#CM709049
	/**
	 * For storing table names to connect table
	 */
	protected String JoinTable = "JoinTable";

	//#CM709050
	/**
	 * Array for storing Select clause to connect table
	 */
	protected Vector JoinColumnsVec = new Vector() ;

	//#CM709051
	/**
	 * Array for storing Where clause to connect table
	 */
	protected Vector JoinWhereVec = new Vector() ;

	//#CM709052
	// Class method --------------------------------------------------
	//#CM709053
	/**
	 * Return the version of this class.
	 * @return Version
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $") ;
	}

	//#CM709054
	// Constructors --------------------------------------------------
	//#CM709055
	/**
	 * Comment for constructor (Comment for Constructor)
	 * @param ap  Comment for parameter
	 */
	public RetrievalHostSendViewSearchKey()
	{
		super();
	}

	//#CM709056
	// Public methods ------------------------------------------------

	//#CM709057
	/**
	 * Set the column (Plan unique key). for combining tables.
	 */
	public void setPlanUkeyJoinCollect()
	{
		setJoinColumns(PLANUKEY);
	}

	//#CM709058
	/**
	 * Set the Table name. for combining tables.
	 */
	public void setJoinTable(String table)
	{
		JoinTable = table;
	}

	//#CM709059
	/**
	 * Obtain the table names to combine tables.
	 */
	public String getJoinTable()
	{
		return JoinTable;
	}

	//#CM709060
	/**
	 * Set the value connected with the plan unique key.
	 */
	public void setPlanUkeyJoin()
	{
		setJoinWhere(PLANUKEY, PLANUKEY);
	}

	//#CM709061
	/**
	 * Generate columns for SELECT clause of SQL from the defined key.
	 */
	public String ReferenceJoinColumns() throws ReadWriteException
	{
		StringBuffer stbf = new StringBuffer(512) ;
		boolean existFlg = false;

		JoinKey[] columns = new JoinKey[JoinColumnsVec.size()];
		JoinColumnsVec.copyInto(columns);

		for (int i = 0 ; i < columns.length ; i++)
		{
			if(existFlg)
			{
				stbf.append(", ");
			}
			stbf.append(JoinTable + "." + columns[i].getColumn1());
			existFlg = true;
		}

		if(!existFlg)
		{
			return null;
		}

		return stbf.toString().substring(0, stbf.length());
	}

	//#CM709062
	/**
	 * Generate conditions to concatenate WHERE clause of SQL from the defined key.
	 */
	public String ReferenceJoinWhere() throws ReadWriteException
	{
		StringBuffer stbf = new StringBuffer(512) ;
		boolean existFlg = false;

		JoinKey[] columns = new JoinKey[JoinWhereVec.size()];
		JoinWhereVec.copyInto(columns);

		for (int i = 0 ; i < columns.length ; i++)
		{
			if(existFlg)
			{
				stbf.append(" AND ");
			}
			stbf.append(TABLE+"."+columns[i].getColumn1()+" = "+ JoinTable + "."+columns[i].getColumn2());
			existFlg = true;
		}

		if(!existFlg)
		{
			return null;
		}

		return stbf.toString().substring(0, stbf.length());
	}

	//#CM709063
	// Package methods -----------------------------------------------

	//#CM709064
	// Protected methods ---------------------------------------------

	//#CM709065
	// Private methods -----------------------------------------------

	//#CM709066
	/**
	 * Set the column to set it in the SELECT clause of the combined table.
	 */
	private void setJoinColumns(String key)
	{
		JoinKey jkey = new JoinKey();
		jkey.setColumn1(key);

		JoinColumnsVec.addElement(jkey);
	}

	//#CM709067
	/**
	 * Set the Where clause for combining tables.
	 */
	private void setJoinWhere(String key1, String key2)
	{
		JoinKey jkey = new JoinKey();
		jkey.setColumn1(key1);
		jkey.setColumn2(key2);

		JoinWhereVec.addElement(jkey);
	}

	//#CM709068
	// Inner Class ---------------------------------------------------
	//#CM709069
	/**
	 * Allow this internal class to maintain columns to be combined.
	 */
	protected class JoinKey
	{
		private String  Column1 ;    // カラム
		private String  Column2 ;    // カラム

		//#CM709070
		/**
		 * Set a column.
		 */
		protected void setColumn1(String column)
		{
			Column1 = column ;
		}
		//#CM709071
		/**
		 * Obtain the column.
		 */
		protected String getColumn1()
		{
			return Column1 ;
		}

		//#CM709072
		/**
		 * Set a column.
		 */
		protected void setColumn2(String column)
		{
			Column2 = column ;
		}
		//#CM709073
		/**
		 * Obtain the column.
		 */
		protected String getColumn2()
		{
			return Column2 ;
		}
	}

}
//#CM709074
//end of class

