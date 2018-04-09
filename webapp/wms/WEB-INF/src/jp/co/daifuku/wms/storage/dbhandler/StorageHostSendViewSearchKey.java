// $Id: StorageHostSendViewSearchKey.java,v 1.2 2006/12/07 08:56:15 suresh Exp $
package jp.co.daifuku.wms.storage.dbhandler;

import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.HostSendViewSearchKey;

//#CM566383
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM566384
/**
 * The class which sets the key to retrieve HostSendView. <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:56:15 $
 * @author  $Author: suresh $
 */
public class StorageHostSendViewSearchKey extends HostSendViewSearchKey
{
	//#CM566385
	// Class fields --------------------------------------------------
	//#CM566386
	/**
	 * Table name
	 */
	private static final String TABLE = "DVHOSTSENDVIEW"; 

	//#CM566387
	// Define the column with the possibility to be joined here. 
	//#CM566388
	/**
	 * Column : Work flag
	 */
	private static final String JOBTYPE					= "JOB_TYPE";
	
	//#CM566389
	/**
	 * Column : Unique key
	 */
	private static final String PLANUKEY					= "PLAN_UKEY";
	
	//#CM566390
	/**
	 * Column : Location No.
	 */
	private static final String LOCATIONNO				= "LOCATION_NO";
	
	//#CM566391
	/**
	 * Column : Plan date
	 */
	private static final String PLANDATE					= "PLAN_DATE";
	
	//#CM566392
	/**
	 * Column : Consignor Code
	 */
	private static final String CONSIGNORCODE			= "CONSIGNOR_CODE";
	
	//#CM566393
	/**
	 * Column : Item Code
	 */
	private static final String ITEMCODE					= "ITEM_CODE";
	
	//#CM566394
	/**
	 * Column : Case/Piece flag
	 */
	private static final String CASEPIECEFLAG			= "CASE_PIECE_FLAG";
	
	//#CM566395
	/**
	 * Column : Expiry date
	 */
	private static final String USEBYDATE				= "USE_BY_DATE";

	//#CM566396
	// Class variables -----------------------------------------------

	//#CM566397
	/**
	 * For maintaining Table name of table to be joined
	 */
	protected String JoinTable = "JoinTable";

	//#CM566398
	/**
	 * Array for maintaining Select phrase of joining table
	 */
	protected Vector JoinColumnsVec = new Vector() ;

	//#CM566399
	/**
	 * Array for maintaining Where phrase of joining table
	 */
	protected Vector JoinWhereVec = new Vector() ;

	//#CM566400
	// Class method --------------------------------------------------
	//#CM566401
	/**
	 * Return the version of this class. 
	 * @return Version
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM566402
	// Constructors --------------------------------------------------
	//#CM566403
	/**
	 * Constructor who generates instance
	 */
	public StorageHostSendViewSearchKey()
	{
		super();
	}

	//#CM566404
	// Public methods ------------------------------------------------

	//#CM566405
	/**
	 * Set Column of Joining table. 
	 */
	public void setPlanUkeyJoinCollect()
	{
		setJoinColumns(PLANUKEY);
	}

	//#CM566406
	/**
	 * Set Table name of Joining table. 
	 * @param table String Set Table name.
	 */
	public void setJoinTable(String table)
	{
		JoinTable = table;
	}

	//#CM566407
	/**
	 * Acquire Table name of Joining table. 
	 * @return Table name
	 */
	public String getJoinTable()
	{
		return JoinTable;
	}

	//#CM566408
	/**
	 * Set Joining value of Unique key. 
	 */
	public void setPlanUkeyJoin()
	{
		setJoinWhere(PLANUKEY, PLANUKEY);
	}

	//#CM566409
	/**
	 * Set the joining value of Consignor Code. 
	 */
	public void setConsignorCodeJoin()
	{
		setJoinWhere(CONSIGNORCODE, CONSIGNORCODE);
	}

	//#CM566410
	/**
	 * Set Joining value of Plan date. 
	 */
	public void setPlanDateJoin()
	{
		setJoinWhere(PLANDATE, PLANDATE);
	}

	//#CM566411
	/**
	 * Set Joining value of Item Code. 
	 */
	public void setItemCodeJoin()
	{
		setJoinWhere(ITEMCODE, ITEMCODE);
	}

	//#CM566412
	/**
	 * Set Joining value of location No. 
	 */
	public void setLocationNoJoin()
	{
		setJoinWhere(LOCATIONNO, LOCATIONNO);
	}

	//#CM566413
	/**
	 * Set Joining value of Expiry date. 
	 */
	public void setUseByDateJoin()
	{
		setJoinWhere(USEBYDATE, USEBYDATE);
	}

	//#CM566414
	/**
	 * Set Joining value of Case/Piece flag. 
	 */
	public void setCasePieceFlagJoin()
	{
		setJoinWhere(CASEPIECEFLAG, CASEPIECEFLAG);
	}

	//#CM566415
	/**
	 * Set Joining value of Work flag. 
	 */
	public void setJobTypeJoin()
	{
		setJoinWhere(JOBTYPE, JOBTYPE);
	}

	//#CM566416
	/**
	 * Generate the SELECT phrase of SQL from the set key. 
	 * @return Character string of generated SELECT phrase
	 * @throws ReadWriteException It is notified when the trouble occurs under the making condition of SQL sentence. 
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

	//#CM566417
	/**
	 * Generate the WHERE phrase of SQL from the set key. 
	 * @return Character string of generated WHERE phrase
	 * @throws ReadWriteException It is notified when the trouble occurs under the making condition of SQL sentence. 
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

	//#CM566418
	// Package methods -----------------------------------------------

	//#CM566419
	// Protected methods ---------------------------------------------

	//#CM566420
	// Private methods -----------------------------------------------

	//#CM566421
	/**
	 * Set SELECT phrases of the uniting tables. 
	 * @param key String Column Name
	 */
	private void setJoinColumns(String key)
	{
		JoinKey jkey = new JoinKey();
		jkey.setColumn1(key);

		JoinColumnsVec.addElement(jkey);
	}

	//#CM566422
	/**
	 * Set the Where phrase which joins table. 
	 * @param key1 String Column Name
	 * @param key2 String Column Name
	 */
	private void setJoinWhere(String key1, String key2)
	{
		JoinKey jkey = new JoinKey();
		jkey.setColumn1(key1);
		jkey.setColumn2(key2);

		JoinWhereVec.addElement(jkey);
	}

	//#CM566423
	// Inner Class ---------------------------------------------------
	//#CM566424
	/**
	 * An internal class which maintains Column of joining. 
	 */
	protected class JoinKey
	{
		private String  Column1 ;    // カラム
		private String  Column2 ;    // カラム

		//#CM566425
		/**
		 * Set Column. 
		 * @param column String Column to be set
		 */
		protected void setColumn1(String column)
		{
			Column1 = column ;
		}
		//#CM566426
		/**
		 * Acquire Column. 
		 * @return Column
		 */
		protected String getColumn1()
		{
			return Column1 ;
		}

		//#CM566427
		/**
		 * Set Column. 
		 * @param column String Column to be set
		 */
		protected void setColumn2(String column)
		{
			Column2 = column ;
		}
		//#CM566428
		/**
		 * Acquire Column. 
		 * @return Column
		 */
		protected String getColumn2()
		{
			return Column2 ;
		}
	}

}
//#CM566429
//end of class

