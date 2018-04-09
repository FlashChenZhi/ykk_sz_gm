// $Id: ToolSQLSearchKey.java,v 1.2 2006/10/30 02:17:14 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48289
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.text.DBFormat;

//#CM48290
/**<en>
 * This class is used when searching tables in database.
 * Instance of the SQLSearchKey class preserves the search conditions; it also compose the 
 * SQL string according to the contents preserved.
 * Composed SQL string will be executed by DataBaseHandler calass and carry out the query in database.
 * The method, to which the search conditions will be set, will be implemented by an inherited class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:14 $
 * @author  $Author: suresh $
 </en>*/
public class ToolSQLSearchKey implements ToolSearchKey
{
	//#CM48291
	// Class fields --------------------------------------------------

	//#CM48292
	// Class variables -----------------------------------------------
	//#CM48293
	/**<en>
	 * The array of search key reservation
	 </en>*/
	protected Vector Vec = null;

	//#CM48294
	// Class method --------------------------------------------------
	//#CM48295
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM48296
	// Constructors --------------------------------------------------

	//#CM48297
	// Public methods ------------------------------------------------
	//#CM48298
	/**<en>
	 * Retrieve the name of the table to update.
	 * @return    :name of the table
	 </en>*/
	public String getUpdateTable()
	{
		return null;
	}

	//#CM48299
	/**<en>
	 * Set the string type search value to the specified column.
	 </en>*/
	public void setValue(String column, String value)
	{
		Key ky = getKey(column);

		//#CM48300
		//<en> If null or the empty string has been set as a search value, set spaces of 1 byte</en>
		//#CM48301
		//<en> and shape the conditional string WHERE XXX = ' '.</en>
		if (value == null)
		{
			ky.setTableValue(" ");
		}
		else if (value.length() == 0)
		{
			ky.setTableValue(" ");
		}
		else
		{
			ky.setTableValue(value);
		}
		setKey(ky);
	}

	//#CM48302
	/**<en>
	 * Set the string type search value to the specified column.
	 * Put the specified elements together in the array by forming OR conditions.
	 * column = "ITEM.ITEMKEY";
	 * str[0] = "ABC";
	 * str[1] = "EFG;
	 * For example, if above values have been given, the string should be edited as below.
	 *  (ITEM.ITEMKEY = 'ABC' OR 'EFG')
	 </en>*/
	public void setValue(String column, String[] values)
	{
		Key ky = getKey(column);

		//#CM48303
		//<en> If null or the empty string has been set as a search value, set spaces of 1 byte</en>
		//#CM48304
		//<en> and shape the conditional string WHERE XXX = ' '.</en>
		for (int i = 0 ; i < values.length ; i++)
		{
			if (values[i] == null)
			{
				values[i] = " ";
			}
			else if (values[i].length() == 0)
			{
				values[i] = " ";
			}
		}
		ky.setTableValue(values);
		setKey(ky);
	}

	//#CM48305
	/**<en>
	 * Set the numeric search value to the specified column.
	 </en>*/
	public void setValue(String column, int intval)
	{
		Key ky = getKey(column);
		ky.setTableValue(new Integer(intval));
		setKey(ky);
	}

	//#CM48306
	/**<en>
	 * Set the numeric search value to the specified column.
	 * Put the specified elements together in the array by forming OR conditions.
	 * column = "CARRYINFO.CMDSTATUS";
	 * str[0] =  1;
	 * str[1] =  2;
	 * For example, if above values have been given, the string should be edited as below.
	 *  (CARRYINFO.CMDSTATUS = 1 OR 2)
	 </en>*/
	public void setValue(String column, int[] intvals)
	{
		Key ky = getKey(column);
		Integer[] intObjs = new Integer[intvals.length];
		for (int i = 0 ; i < intvals.length ; i++)
		{
			intObjs[i] = new Integer(intvals[i]);
		}
		ky.setTableValue(intObjs);
		setKey(ky);
	}

	//#CM48307
	/**<en>
	 * Set the date type search value to the specified column.
	 </en>*/
	public void setValue(String column, Date dtval)
	{
		Key ky = getKey(column);
		ky.setTableValue(dtval);
		setKey(ky);
	}

	//#CM48308
	/**<en>
	 * Retrieve the search value of the specified column.
	 </en>*/
	public Object getValue(String column)
	{
		Key ky = getKey(column);
		return(ky.getTableValue());
	}

	//#CM48309
	/**<en>
	 * Set the order of sorting to the specified column.
	 </en>*/
	public void setOrder(String column, int num, boolean bool)
	{
		Key ky = getKey(column);
		ky.setTableOrder(num);
		ky.setTableDesc(bool);
		setKey(ky);
	}

	//#CM48310
	/**<en>
	 * Retrieve the order of sorting to the specified column.
	 </en>*/
	public int getOrder(String column)
	{
		Key ky = getKey(column);
		return(ky.getTableOrder());
	}

	//#CM48311
	/**<en>
	 * Set the string type update value to the specified column.
	 </en>*/ 
	public void setUpdValue(String column, String value)
	{
		Key ky = getKey(column);

		//#CM48312
		//<en> If null or the empty string has been set as a search value, set spaces of 1 byte</en>
		//#CM48313
		//<en> and shape the conditional string WHERE XXX = ' '.</en>

		if (value == null)
		{
			ky.setUpdValue(" ");
		}
		else if (value.length() == 0)
		{
			ky.setUpdValue(" ");
		}
		else
		{
			ky.setUpdValue(value);
		}
		setKey(ky);
	}

	//#CM48314
	/**<en>
	 * Set the string type update value to teh specified column.
	 </en>*/
	public void setUpdValue(String column, int intval)
	{
		Key ky = getKey(column);
		ky.setUpdValue(new Integer(intval));
		setKey(ky);
	}

	//#CM48315
	/**<en>
	 * Set the date type search value to the specified column.
	 </en>*/
	public void setUpdValue(String column, Date dtval)
	{
		Key ky = getKey(column);
		ky.setUpdValue(dtval);
		setKey(ky);
	}

	//#CM48316
	/**<en>
	 * Retrieve the update value of the specified column.
	 </en>*/
	public Object getUpdValue(String column)
	{
		Key ky = getKey(column);
		return(ky.getTableValue());
	}

	//#CM48317
	/**<en>
	 * Generate the WHERE phrase for SQL based on the set key.
	 </en>*/
	public String ReferenceCondition()
	{
		StringBuffer stbf = new StringBuffer(256);
		boolean existFlg = false;

		for (int i = 0 ; i < Vec.size() ; i++)
		{
			Key ky = (Key)Vec.get(i);
			if (ky.getTableValue() != null)
			{
				//#CM48318
				//<en> Edit the search value for AND condition. -> COLUMN = VALUE</en>

				//#CM48319
				//<en> When the letters are given as values, examine whether/not the pattern check letters</en>
				//<en> are included; if they are included, use LIKE search.</en>
				if (ky.getTableValue() instanceof String)
				{
					String key = (String)ky.getTableValue();
					key = DBFormat.exchangeKey(key);
					
					if (DBFormat.isPatternMatching(key))
					{
						stbf.append(" RTRIM(");
						stbf.append(ky.getTableColumn());
						stbf.append(") ");
						stbf.append(" LIKE ");
					}
					else
					{
						stbf.append(ky.getTableColumn());
						stbf.append(" = ");
					}
				}
				else
				{
					stbf.append(ky.getTableColumn());
					stbf.append(" = ");
				}
				if (ky.getTableValue() instanceof String)
				{
					stbf.append(DBFormat.format(DBFormat.exchangeKey((String)ky.getTableValue()))) ;
				}
				else if (ky.getTableValue() instanceof Date)
				{
					stbf.append(DBFormat.format((Date)ky.getTableValue())) ;
				}
				else
				{
					stbf.append(ky.getTableValue());
				}
				stbf.append(" AND ");
				if (existFlg == false) existFlg = true;
			}
			else if (ky.getTableValueArray() != null)
			{
				//#CM48320
				//<en> Edit the search valud for OR condition.  -> (COLUMN = VALUE OR COLUMN = VALUE)</en>
				StringBuffer wkbf = new StringBuffer(256);
				Object[] valobj = ky.getTableValueArray();
				for (int j = 0 ; j < valobj.length ; j++)
				{
					if (j > 0)
					{
						//#CM48321
						//<en> Add OR to the valud of 2nd data and onward.</en>
						wkbf.append(" OR ");
					}
					//#CM48322
					//<en> When the letters are given as values, examine whether/not the pattern check letters</en>
					//#CM48323
					//<en> are included; if they are included, use LIKE search.</en>
					if (valobj[j] instanceof String)
					{
						String key = (String)valobj[j];
						key = DBFormat.exchangeKey(key);
						if (DBFormat.isPatternMatching(key))
						{
							wkbf.append(" RTRIM(");
							wkbf.append(ky.getTableColumn());
							wkbf.append(") ");
							wkbf.append(" LIKE ");
						}
						else
						{
							wkbf.append(ky.getTableColumn());
							wkbf.append(" = ");
						}
					}
					else
					{
						wkbf.append(ky.getTableColumn());
						wkbf.append(" = ");
					}
					if (valobj[j] instanceof String)
					{
						//#CM48324
						//<en> When string type value is given, enclose it between single quotations.</en>
						wkbf.append(DBFormat.format(DBFormat.exchangeKey((String)valobj[j]))) ;
					}
					else if (valobj[j] instanceof Date)
					{
						//#CM48325
						//<en> When date type value is given, use TO_DATE function.</en>
						wkbf.append(DBFormat.format((Date)valobj[j])) ;
					}
					else
					{
						//#CM48326
						//<en> When numeric value is given, set it as it is.</en>
						wkbf.append(valobj[j]);
					}
				}
				//#CM48327
				//<en> When the number of the elements in the array of search value is 0,</en>
				//<en> this SQL string will not be composed.</en>
				if (valobj.length > 0)
				{
					//#CM48328
					//<en> Enclose the edited string in ( ).</en>
					wkbf.insert(0, "(");
					wkbf.append(")");
					stbf.append(wkbf);
					stbf.append(" AND ");
					if (existFlg == false) existFlg = true;
				}
			}
		}
		//#CM48329
		//<en> Return null if no data has been set to the key value.</en>

		if (existFlg == false)
			return null;

		//#CM48330
		//<en> Ommit the unnecesary "AND" of the end.</en>
		int ep = stbf.toString().lastIndexOf("AND");
		return stbf.toString().substring(0, ep);
	}

	//#CM48331
	/**<en>
	 * Generate the ORDER BY phrase of SQL according to the set key.
	 </en>*/
	public String SortCondition()
	{
		Key[] karray = new Key[Vec.size()];

		Vec.copyInto(karray);
		for (int i = 0 ; i < karray.length ; i++)
		{
			for (int j = i ; j < karray.length ; j++)
			{
				if (karray[i].getTableOrder() > karray[j].getTableOrder())
				{
					Key ktmp = karray[i];
					karray[i] = karray[j];
					karray[j] = ktmp;
				}
			}
		}

		StringBuffer stbf = new StringBuffer(256);
		boolean existFlg = false;

		for (int i = 0 ; i < karray.length ; i++)
		{
			if (karray[i].getTableOrder() > 0)
			{
				stbf.append(karray[i].getTableColumn());
				//#CM48332
				//<en> If the data is in descending order, Set the key words of descending order.</en>
				if (karray[i].getTableDesc() == false)
				{
					stbf.append (" DESC");
				}
				stbf.append (", ");
				if (existFlg == false) existFlg = true;
			}
		}			
		//#CM48333
		//<en> Return null if no data has been set to the key value.</en>
		if (existFlg == false)
			return null; 

		//#CM48334
		//<en> Ommit the unnecesary "," of the end.</en>
		int ep = stbf.toString().lastIndexOf(",");
		return stbf.toString().substring(0, ep);
	}

	//#CM48335
	// Package methods -----------------------------------------------

	//#CM48336
	// Protected methods ---------------------------------------------
	//#CM48337
	/**<en>
	 * Set the column name.
	 </en>*/
	protected void setColumns(String[] columns)
	{
		Vec = new Vector(columns.length);
		for (int i = 0 ; i < columns.length ; i++)
		{
			//#CM48338
			//<en> Set the key column.</en>
			Vec.addElement(new Key(columns[i]));
		}
	}

	//#CM48339
	// Private methods -----------------------------------------------
	//#CM48340
	/**<en>
	 * Return the Key instance that matches the specified key word (column).
	 </en>*/
	protected Key getKey(String keyword)
	{
		for (int i = 0 ; i < Vec.size() ; i++)
		{
			Key ky = (Key)Vec.get(i);
			if (ky.getTableColumn().equals(keyword)) return ky;
		}
		return null;
	}

	//#CM48341
	/**<en>
	 * Set a value to the Key instance that matches the specified key word (column).
	 </en>*/
	protected void setKey(Key key)
	{
		for (int i = 0 ; i < Vec.size() ; i++)
		{
			Key ky = (Key)Vec.get(i);
			if (ky.getTableColumn().equals(key.getTableColumn()))
			{
				ky.setTableValue(key.getTableValue());
				ky.setTableOrder(key.getTableOrder());
				Vec.set(i, ky);
			}
		}
	}
	

	//#CM48342
	// Inner Class ---------------------------------------------------
	//#CM48343
	/**<en>
	 * This is the inncer class which preserve key column, search value, the order of sorting
	 * and the update value.
	 </en>*/
	protected class Key
	{
		//#CM48344
		//<en> column</en>
		private String   Column;
     		//#CM48345
		//<en> column</en>
		private Date     dtColumn;
		//#CM48346
		//<en> search value for AND conditions COLUMN = VALUE</en>
		private Object   Value;
		//#CM48347
		//<en> search value for OR conditions (COLUMN = VALUE OR COLUMN = VALUE)</en>
		private Object[] ValueArray;
		//#CM48348
		//<en> order of sorting</en>
		private int      Order;
		//#CM48349
		//<en> direction of sorting - true: ascending order, false: descending order</en>
		private boolean  Desc ;      
		//#CM48350
		//<en> update value</en>
		private Object   UpdValue ;  
		//#CM48351
		//<en> column to update - true:to be updated, false:not the target of udpate</en>
		private boolean  Update ;    

		protected Key (String clm) {
			Column        = clm;
			Value         = null;
			ValueArray    = null;
			Order         = 0;
			Desc          = true;
			UpdValue      = null;
			Update        = false;
		}

		//#CM48352
		/**<en>
		 * Set the column of the table.
		 </en>*/
		protected String getTableColumn()
		{
			return Column;
		}

		//#CM48353
		/**<en>
		 * Retrieve the column of the table.
		 </en>*/
		protected Date getTableDtColumn()
		{
			return dtColumn;
		}

		//#CM48354
		/**<en>
		 * Set the search value which corresponds to the table column.
		 * This is for AND conditions.
		 </en>*/
		protected void setTableValue(Object val)
		{
			Value = val;
		}

		//#CM48355
		/**<en>
		 * Retrieve the search value that corresponds to the table column.
		 * his is for AND conditions.
		 </en>*/
		protected Object getTableValue()
		{
			return Value;
		}

		//#CM48356
		/**<en>
		 * Set the search value which corresponds to the table column.
		 * This is for OR conditions.
		 </en>*/
		protected void setTableValue(Object[] valarray)
		{
			ValueArray = valarray;
		}

		//#CM48357
		/**<en>
		 * Set the search value which corresponds to the table column.
		 * This is for OR conditions.
		 </en>*/
		protected Object[] getTableValueArray()
		{
			return ValueArray;
		}

		//#CM48358
		/**<en>
		 * Set the order of sorting that corresponds to the table column.
		 </en>*/
		protected void setTableOrder(int num)
		{
			Order = num;
		}

		//#CM48359
		/**<en>
		 * Retrieve the order of sorting that corresponds to the table column.
		 </en>*/
		protected int getTableOrder()
		{
			return Order;
		}

		//#CM48360
		/**<en>
		 * Set the direction of the sorting (asecending /descending order).
		 </en>*/
		protected void setTableDesc(boolean bool)
		{
			Desc = bool;
		}

		//#CM48361
		/**<en>
		 * Retrieve the direction of the sorting (asecending /descending order).
		 </en>*/
		protected boolean getTableDesc()
		{
			return Desc;
		}

		//#CM48362
		/**<en>
		 * Set the update value that corresponds to the table column.
		 * Include the column in targe of the update at the same time.
		 </en>*/
		protected void setUpdValue(Object val)
		{
			Update = true;
			UpdValue = val;
		}

		//#CM48363
		/**<en>
		 * Retrieve the update value for the table column.
		 </en>*/
		protected Object getUpdValue()
		{
			return UpdValue;
		}

		//#CM48364
		/**<en>
		 * Examine whether/not the column is the target of update.
		 </en>*/
		protected boolean isUpdate()
		{
			return Update;
		}
	}
}
//#CM48365
//end of class

