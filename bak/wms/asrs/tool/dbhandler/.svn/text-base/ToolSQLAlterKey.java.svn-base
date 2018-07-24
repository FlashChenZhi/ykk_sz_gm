// $Id: ToolSQLAlterKey.java,v 1.2 2006/10/30 02:17:15 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48211
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DBFormat;

//#CM48212
/**<en>
 * This class is used to update table in database.
 * The instance of SQLAlterKey class preserves the contents and conditions of updates.
 * According to the contents preserved, it composes the SQL text.
 * The composed text is executed by DataBaseHandler class and the database will be updated.
 * The method of setting update data and its conditions is implemented by another class 
 * that is the successor of this class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:15 $
 * @author  $Author: suresh $
 </en>*/
public class ToolSQLAlterKey implements ToolAlterKey
{
	//#CM48213
	// Class fields --------------------------------------------------

	//#CM48214
	// Class variables -----------------------------------------------
	//#CM48215
	/**<en>
	 * Arrya that preserves the search key
	 </en>*/
	protected Vector Vec = null;

	//#CM48216
	// Class method --------------------------------------------------
	//#CM48217
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM48218
	// Constructors --------------------------------------------------

	//#CM48219
	// Public methods ------------------------------------------------

	//#CM48220
	//<en> Set the string type search value to the selected column.</en>
	public void setValue(String column, String value)
	{
		Key ky = getKey(column);

		//#CM48221
		//<en> In case the null or empty string array is set for search value, set the blank of 1 byte</en>
		//#CM48222
		//<en> then arrange the form of condition text to be WHERE XXX = ' '.</en>
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

	//#CM48223
	/**<en>
	 * Set the string type search value to the selected column.
	 * Then connect the selected items in the array using OR condition.
	 * column = "ITEM.ITEMKEY";
	 * str[0] = "ABC";
	 * str[1] = "EFG;
	 * If above values are provided, data will be edited as below.
	 *  (ITEM.ITEMKEY = 'ABC' OR 'EFG')
	 </en>*/
	public void setValue(String column, String[] values)
	{
		Key ky = getKey(column);

		//#CM48224
		//<en> In case the null or empty string array is set for search value, set the blank of 1 byte</en>
		//#CM48225
		//<en> then arrange the form of condition text to be WHERE XXX = ' '.</en>
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

	//#CM48226
	//<en> Set the numeric search value to the selected column.</en>
	public void setValue(String column, int intval)
	{
		Key ky = getKey(column);
		ky.setTableValue(new Integer(intval));
		setKey(ky);
	}
	
	//#CM48227
	/**<en>
	 * Set the numeric search value to the selected column.
	 * Then connect the selected items in the array using OR condition.
	 * column = "CARRYINFO.CMDSTATUS";
	 * str[0] =  1;
	 * str[1] =  2;
	 * If above values are provided, data will be edited as below.
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

	//#CM48228
	//<en> Set the date type search value to the specified column.</en>
	public void setValue(String column, Date dtval)
	{
		Key ky = getKey(column);
		ky.setTableValue(dtval);
		setKey(ky);
	}

	//#CM48229
	//<en> Get the search value of the specified column.</en>
	public Object getValue(String column)
	{
		Key ky = getKey(column);
		return(ky.getTableValue());
	}

	//#CM48230
	//<en> Set the string type update value to the specified column.</en>
	public void setUpdValue(String column, String value)
	{
		Key ky = getKey(column);
		ky.setUpdValue(value);
		setKey(ky);
	}

	//#CM48231
	//<en> Set the numeric update value to the specified column.</en>
	public void setUpdValue(String column, int intval)
	{
		Key ky = getKey(column);
		ky.setUpdValue(new Integer(intval));
		setKey(ky);
	}

	//#CM48232
	//<en> Set the numeric update value to the specified column.</en>
	public void setUpdValue(String column, long longval)
	{
		Key ky = getKey(column);
		ky.setUpdValue(new Long(longval));
		setKey(ky);
	}

	//#CM48233
	//<en> Set the date type search value to the specified column.</en>
	public void setUpdValue(String column, Date dtval)
	{
		Key ky = getKey(column);
		ky.setUpdValue(dtval);
		setKey(ky);
	}

	//#CM48234
	//<en> Get the update value to the specified column.</en>
	public Object getUpdValue(String column)
	{
		Key ky = getKey(column);
		return(ky.getTableValue());
	}

	//#CM48235
	/**<en>
	 * Generate the WHERE phrase of SQL.
	 * Conditions are generated only based on the columns defined in set table.
	 * @param :name of the table that condition text is generated for
	 * @param :string array used in where phrase of SQL
	 </en>*/
	public String ReferenceCondition(String tablename)
	{
		//#CM48236
		//<en> this is used in LogWRite when an Exception occurred.</en>
		StringWriter wSW = new StringWriter();   

		//#CM48237
		//<en> this is used in LogWRite when an Exception occurred.</en>
		PrintWriter  wPW = new PrintWriter(wSW); 

		//#CM48238
		//<en> delimiter</en>
		String wDelim = MessageResource.DELIM ;  

		StringBuffer stbf = new StringBuffer(256);
		boolean existFlg = false;

		for (int i = 0 ; i < Vec.size() ; i++)
		{
			Key ky = (Key)Vec.get(i);
			if (ky.getTableValue() != null)
			{
				//#CM48239
				//<en> Edit the search value for AND conditions -> COLUMN = VALUE</en>

				//#CM48240
				//<en> Get the table name from the column name. TableName.ColumnName -> TableName</en>

				int fp = ky.getTableColumn().indexOf(".");
				if (fp == -1)
				{
					//#CM48241
					//<en> Indefinite column definition. Outpul log.</en>
					Object[] tObj = new Object[2];
					tObj[0] = ky.getTableColumn();
					tObj[1] = ky.getTableColumn();
					//#CM48242
					//<en>6124001 = Specified column does not exist in the table. COLUMN={0} TABLE={1}</en>
					RmiMsgLogClient.write(6124001, LogMessage.F_WARN, this.getClass().getName(), tObj);
					continue;
				}
				String str = ky.getTableColumn().substring(0, fp);
				//#CM48243
				//<en> If the column matches the table of calling parameter, regard the column as a target</en>
				//<en> for condition text.</en>
				if (str.equals(tablename))
				{
					stbf.append(ky.getTableColumn());
					stbf.append(" = ");
					if (ky.getTableValue() instanceof String)
					{
						stbf.append(DBFormat.format((String)ky.getTableValue())) ;
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
					if (existFlg == false)
						existFlg = true;
				}
			}
			else if (ky.getTableValueArray() != null)
			{
				//#CM48244
				//<en> Edit the search value for OR conditions. -> (COLUMN = VALUE OR COLUMN = VALUE)</en>
				StringBuffer wkbf = new StringBuffer(256);
				Object[] valobj = ky.getTableValueArray();
				for (int j = 0 ; j < valobj.length ; j++)
				{
					if (j > 0)
					{
						//#CM48245
						//<en> OR is idsabled for 2nd data and further.</en>
						wkbf.append(" OR ");
					}
					//#CM48246
					//<en> In case the characters are given for the value, check if pattern match characters are contained.</en>
					//#CM48247
					//<en>CMENJP5575$CM Use the LIKE retrieval when existing. </en>
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
						//#CM48248
						//<en> Bracket off the string type data using single quotations.</en>
						wkbf.append(DBFormat.format(DBFormat.exchangeKey((String)valobj[j]))) ;
					}
					else if (valobj[j] instanceof Date)
					{
						//#CM48249
						//<en> Use TO_DATE function for date type data.</en>
						wkbf.append(DBFormat.format((Date)valobj[j])) ;
					}
					else
					{
						//#CM48250
						//<en> Set gte numeric data as it is.</en>
						wkbf.append(valobj[j]);
					}
				}
				//#CM48251
				//<en> If there are no items in search value array, SQL wil not be composed.</en>
				if (valobj.length > 0)
				{
					//#CM48252
					//<en> Place the edited string array in between ( and ).</en>
					wkbf.insert(0, "(");
					wkbf.append(")");
					stbf.append(wkbf);
					stbf.append(" AND ");
					if (existFlg == false) existFlg = true;
				}
			}
		}
		//#CM48253
		//<en> Return null if no dat is set for the key value.</en>

		if (existFlg == false)
			return null;

		//#CM48254
		//<en> "AAND" in the end is unnecessary. Deleted.</en>
		int ep = stbf.toString().lastIndexOf("AND");
		return stbf.toString().substring(0, ep);
	}

	//#CM48255
	/**<en>
	 * Generate the UPDATE SET phrase in SQL based on the set key.
	 * Conditions are generated only based on the column defined in specified table.
	 * @param :table name that the condition text is generated
	 * @param :string array used in where phrase of SQL
	 </en>*/
	public String ModifyContents(String tablename)
	{
		StringBuffer stbf = new StringBuffer(256);
		boolean existFlg = false;

		for (int i = 0 ; i < Vec.size() ; i++)
		{
			Key ky = (Key)Vec.get(i);
			//#CM48256
			//<en> Examine if each column are the object for UPDATE.</en>
			if (ky.isUpdate())
			{
				//#CM48257
				//<en> Get the table name from the column name. TableName.ColumnName -> TableName</en>
				int fp = ky.getTableColumn().indexOf(".");
				if (fp == -1)
				{
					//#CM48258
					//<en> Indefinite column definition. Output log.</en>
					Object[] tObj = new Object[2] ;
					tObj[0] = ky.getTableColumn();
					tObj[1] = ky.getTableColumn();
					//#CM48259
					//<en>6124001 = Specified column does not exist in the table. COLUMN={0} TABLE={1}</en>
					RmiMsgLogClient.write(6124001, LogMessage.F_WARN, this.getClass().getName(), tObj);
					continue;
				}
				String str = ky.getTableColumn().substring(0, fp);
				//#CM48260
				//<en> If the column matches the table of calling parameter, regard the column as a target</en>
				//<en> for condition text.</en>
				if (str.equals(tablename))
				{
					//#CM48261
					//<en> If it its the column to be updated, get the set value then edit the data.</en>
					stbf.append(ky.getTableColumn());
					stbf.append(" = ");
					if (ky.getUpdValue() instanceof String)
					{
						stbf.append("'" + ky.getUpdValue() + "'");
					}
					else if (ky.getUpdValue() instanceof Date)
					{
						stbf.append(DBFormat.format((Date)ky.getUpdValue())) ;
					}
					else
					{
						stbf.append(ky.getUpdValue());
					}
					stbf.append(", ");
					if (existFlg == false) existFlg = true;
				}
			}
		}
		//#CM48262
		//<en> Return null if no dat is set for the key value.</en>
		if (existFlg == false)
			return null;

		//#CM48263
		//<en> Last "," is unnecessary. Deleted.</en>
		int ep = stbf.toString().lastIndexOf(",");
		return stbf.toString().substring(0, ep);
	}

	//#CM48264
	//<en> For debug</en>
	public void debug()
	{
		for (int i = 0; i < Vec.size() ; i++)
		{
			Key ky = (Key)Vec.get(i);
		}
	}

	//#CM48265
	// Package methods -----------------------------------------------

	//#CM48266
	// Protected methods ---------------------------------------------
	//#CM48267
	//<en> Set the column name.</en>
	protected void setColumns(String[] columns)
	{
		Vec = new Vector(columns.length);
		for (int i = 0 ; i < columns.length ; i++)
		{
			//#CM48268
			//<en> Set the key column.</en>
			Vec.addElement(new Key(columns[i]));
		}
	}

	//#CM48269
	// Private methods -----------------------------------------------
	//#CM48270
	//<en> Return the Key instance that matches the specified key word (column).</en>
	protected Key getKey(String keyword)
	{
		for (int i = 0 ; i < Vec.size() ; i++)
		{
			Key ky = (Key)Vec.get(i);
			if (ky.getTableColumn().equals(keyword)) return ky;
		}
		return null;
	}

	//#CM48271
	//<en> Set the Key instance that matches the specified key word (column).</en>
	protected void setKey(Key key)
	{
		for (int i = 0 ; i < Vec.size() ; i++)
		{
			Key ky = (Key)Vec.get(i);
			if (ky.getTableColumn().equals(key.getTableColumn()))
			{
				ky.setTableValue(key.getTableValue());
				Vec.set(i, ky);
			}
		}
	}
	

	//#CM48272
	// Inner Class ---------------------------------------------------
	//#CM48273
	//<en> This is the innner class that preserves the key column, search value, sorting order and update values.</en>
	protected class Key
	{
		//#CM48274
		//<en> column</en>
		private String  Column;      
		//#CM48275
		//<en> search value for AND condition  COLUMN = VALUE</en>
		private Object  Value;       
		//#CM48276
		//<en> search value for OR condition (COLUMN = VALUE OR COLUMN = VALUE)</en>
		private Object[] ValueArray; 
		//#CM48277
		//<en> Update value</en>
		private Object  UpdValue ;   
		//#CM48278
		//<en> Column to be UPDATEd true: to be UPDATEd, false: not to be UPDATEd</en>
		private boolean Update ;     

		protected Key (String clm) {
			Column        = clm;
			Value         = null;
			ValueArray    = null;
			UpdValue      = null;
			Update        = false;
		}

		//#CM48279
		//<en> Get the column of the table.</en>
		protected String getTableColumn()
		{
			return Column;
		}

		//#CM48280
		//<en> Set the search value for the column of the table.</en>
		protected void setTableValue(Object val)
		{
			Value = val;
		}

		//#CM48281
		//<en> Get the search value for the column of the table.</en>
		protected Object getTableValue()
		{
			return Value;
		}

		//#CM48282
		/**<en>
		 * Set the search value for the column of the table.
		 * This is for OR condition.
		 </en>*/
		protected void setTableValue(Object[] valarray)
		{
			ValueArray = valarray;
		}

		//#CM48283
		/**<en>
		 * Get the search value for the column of the table.
		 * OThis is for OR condition.
		 </en>*/
		protected Object[] getTableValueArray()
		{
			return ValueArray;
		}

		//#CM48284
		//<en> Set the update value for the column of the table.</en>
		//#CM48285
		//<en> Also regard the column as a target to be UPDATEd.</en>
		protected void setUpdValue(Object val)
		{
			Update = true;
			UpdValue = val;
		}

		//#CM48286
		//<en> Get the update value for the column of the table.</en>
		protected Object getUpdValue()
		{
			return UpdValue;
		}

		//#CM48287
		//<en> Examine to see if the column is the object for UPDATE.</en>
		protected boolean isUpdate()
		{
			return Update;
		}
	}
}
//#CM48288
//end of class

