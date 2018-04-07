// $Id: StorageHostSendSearchKey.java,v 1.2 2006/12/07 08:56:16 suresh Exp $
package jp.co.daifuku.wms.storage.dbhandler;

import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;

//#CM566310
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM566311
/**
 * The class which sets the key to retrieve the HostSend table. <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:56:16 $
 * @author  $Author: suresh $
 */
public class StorageHostSendSearchKey extends HostSendSearchKey
{
	//#CM566312
	// Class fields --------------------------------------------------
	//#CM566313
	/**
	 * Table name
	 */
	private static final String TABLE = "DNHOSTSEND"; 

	//#CM566314
	// Define the column with the possibility to be joined here. 
	//#CM566315
	/**
	 * Column : Consignor Code
	 */
	private static final String CONSIGNORCODE			= "CONSIGNOR_CODE";
	
	//#CM566316
	/**
	 * Column : Item Code
	 */
	private static final String ITEMCODE					= "ITEM_CODE";
	
	//#CM566317
	/**
	 * Column : Actual qty
	 */
	private static final String RESULTQTY				= "RESULT_QTY";
	
	//#CM566318
	/**
	 * Column : Location No.
	 */
	private static final String LOCATIONNO				= "LOCATION_NO";
	
	//#CM566319
	/**
	 * Column : Expiry date
	 */
	private static final String RESULTUSEBYDATE			= "RESULT_USE_BY_DATE";
	
	//#CM566320
	/**
	 * Column : Work result Location No. 
	 */
	private static final String RESULTLOCATIONNO			= "RESULT_LOCATION_NO";

	//#CM566321
	// Class variables -----------------------------------------------

	//#CM566322
	/**
	 * For maintaining Table name of table to be joined
	 */
	protected String JoinTable = "JoinTable";

	//#CM566323
	/**
	 * Array for maintaining Select phrase of joining table
	 */
	protected Vector JoinColumnsVec = new Vector() ;

	//#CM566324
	/**
	 * Array for maintaining Where phrase of joining table
	 */
	protected Vector JoinWhereVec = new Vector() ;

	//#CM566325
	// Class method --------------------------------------------------
	//#CM566326
	/**
	 * Return the version of this class. 
	 * @return Version
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM566327
	// Constructors --------------------------------------------------
	//#CM566328
	/**
	 * Constructor who generates instance
	 */
	public StorageHostSendSearchKey()
	{
		super();
	}

	//#CM566329
	// Public methods ------------------------------------------------

	//#CM566330
	/**
	 * Set Column of Joining table. 
	 */
	public void setResultQtyJoinCollect()
	{
		setJoinColumns(RESULTQTY);
	}

	//#CM566331
	/**
	 * Set Table name of Joining table. 
	 * @param table String Set Table name.
	 */
	public void setJoinTable(String table)
	{
		JoinTable = table;
	}

	//#CM566332
	/**
	 * Acquire Table name of Joining table. 
	 * @return Table name
	 */
	public String getJoinTable()
	{
		return JoinTable;
	}

	//#CM566333
	/**
	 * Set the joining value of Consignor Code. 
	 */
	public void setConsignorCodeJoin()
	{
		setJoinWhere(CONSIGNORCODE, CONSIGNORCODE);
	}

	//#CM566334
	/**
	 * Set Joining value of Item Code. 
	 */
	public void setItemCodeJoin()
	{
		setJoinWhere(ITEMCODE, ITEMCODE);
	}

	//#CM566335
	/**
	 * Set Joining value of location No. 
	 */
	public void setLocationNoJoin()
	{
		setJoinWhere(LOCATIONNO, LOCATIONNO);
	}

	//#CM566336
	/**
	 * Set Joining value of Work result location No. 
	 */
	public void setResultLocationNoJoin()
	{
		setJoinWhere(RESULTLOCATIONNO, RESULTLOCATIONNO);
	}

	//#CM566337
	/**
	 * Set Joining value of Expiry date. 
	 */
	public void setResultUseByDateJoin()
	{
		setJoinWhere(RESULTUSEBYDATE, RESULTUSEBYDATE);
	}

	//#CM566338
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

	//#CM566339
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

	//#CM566340
	// Package methods -----------------------------------------------

	//#CM566341
	// Protected methods ---------------------------------------------

	//#CM566342
	// Private methods -----------------------------------------------

	//#CM566343
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

	//#CM566344
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

	//#CM566345
	// Inner Class ---------------------------------------------------
	//#CM566346
	/**
	 * An internal class which maintains Column of joining. 
	 */
	protected class JoinKey
	{
		private String  Column1 ;    // カラム
		private String  Column2 ;    // カラム

		//#CM566347
		/**
		 * Set Column. 
		 * @param column String Column to be set
		 */
		protected void setColumn1(String column)
		{
			Column1 = column ;
		}
		//#CM566348
		/**
		 * Acquire Column. 
		 * @return Column
		 */
		protected String getColumn1()
		{
			return Column1 ;
		}

		//#CM566349
		/**
		 * Set Column. 
		 * @param column String Column to be set
		 */
		protected void setColumn2(String column)
		{
			Column2 = column ;
		}
		//#CM566350
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
//#CM566351
//end of class

