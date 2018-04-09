// $Id: Converter.java,v 1.2 2006/10/30 01:49:09 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.converter ;

//#CM47123
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.common.MessageResource;


//#CM47124
/**<en>
 * This class converts the table into texts and inserts texts in tables.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/17</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/12</TD><TD>hondo</TD><TD>Modification has been added to enable the quicker registration processing of text file in DB.<BR>
 * Modification has been added ; it skips the check of DATE type conversion if the same table names were given consecutively.<BR>
 * Modification has been added.<BR>
 * Former processing : All data of text was read, checking and registration.<BR>
 * Current processing : Reading, checking and registration is done by each line of the text.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:49:09 $
 * @author  $Author: suresh $
 </en>*/
public class Converter extends Object
{

	//#CM47125
	// Class fields --------------------------------------------------
	//#CM47126
	/**<en> Delimiters used when creating the messages </en>*/

	protected final String wDelim = MessageResource.DELIM;
	//#CM47127
	/**<en> TOOL versin </en>*/

	public static final String VERSION = "1.00";
	//#CM47128
	/**<en> Delimiters for text </en>*/

	protected static final String DELIM = ",";
	//#CM47129
	/**<en> File extensions </en>*/

	protected static final String EXTENSION = ".txt";
	
	//#CM47130
	/**<en> Format of dates </en>*/

	protected static final String DATEFORMAT = "YYYY/MM/DD HH24:MI:SS";


	//#CM47131
	// Class variables -----------------------------------------------
	//#CM47132
	/**<en> User name when connecting with DB</en>*/

	protected static String USER = ToolParam.getParam("AWC_DB_USER");

	//#CM47133
	// Class method --------------------------------------------------
	//#CM47134
	/**<en>
	 * Returns the version of this class.
	 * @return Versin and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:49:09 $") ;
	}

	//#CM47135
	// Constructors --------------------------------------------------

	//#CM47136
	// Public methods ------------------------------------------------
	//#CM47137
	/**<en>
	 * Conver the table, which was specified by parameter, in text format and store. 
	 * 
	 * @param <code>Connection</code> object
	 * @param tableName :name of the table
	 * @param filename  :file name. Specified with path included. Example:c\dbtext\warehouse.txt
	 </en>*/
	public static void tableToText(Connection conn, String tableName, String filename) throws Exception
	{
System.out.println("Making "+tableName+" text..");
		//#CM47138
		//File name is Table name + extension(.txt)
		String fileName = filename;

		//#CM47139
		//Get column name of Table.
		Vector columnNames = getColumnNames(conn, tableName);
		
		FileWriter dos = new FileWriter(fileName);
		//#CM47140
		//Write version.
		dos.write(VERSION+"\n");	
		//#CM47141
		//Write column name.

		dos.write(getDelimitedText(columnNames)); 
		
		//#CM47142
		//Query sql
		String sql = makeSQL(conn, tableName, columnNames);
		Statement  stmt = conn.createStatement();
		//#CM47143
		//Excute Query
		ResultSet resultSet = stmt.executeQuery(sql);

		while(resultSet.next())
		{
			Vector values = new Vector(10);
			for(int i = 0; i < columnNames.size(); i++)
			{
				String data = resultSet.getString((String)columnNames.get(i));
				//#CM47144
				//When the data is null. convert to space. 
				if(data == null){data = " ";}
				
				values.add(data);
			}
			dos.write(getDelimitedText(values)); 
		}

		dos.close();
	}

	//#CM47145
	/**<en>
	 * Read the text which was specified by parameter, then insert this data to the table that 
	 * parameter specified.
	 * 
	 * @param <code>Connection</code> object
	 * @param tableName :table name
	 * @param filename  :file name. Specified with path included. Example:c\dbtext\warehouse.txt
	 </en>*/
	public static void textToTable(Connection conn, String tablename, String filename) throws Exception
	{
System.out.println("Making "+filename.substring(0,(filename.lastIndexOf(EXTENSION)))+" table...");

		Statement  stmt = conn.createStatement();

		//#CM47146
		//<en>Read the text data.</en>
		String format = getFormat(filename);

		//#CM47147
		//<en>Read the text data.</en>
		String[] key = getSeparatedItem(format);
		//#CM47148
		//<en>Start position of the data</en>
		int dataStartPoint = 2;

		File file = new File(filename);
		if(file.exists())
		{
			//#CM47149
			//<en>Reading file</en>
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String buf = "";
			int count = 0;
			while(( buf = br.readLine())!=null)
			{
				//#CM47150
				//<en>The data begins as of the 3rd line of the text file.</en>
				if(count >= 2)
				{
					Vector vector = new Vector(5);
					Hashtable hash = new Hashtable();
					String[] data = getSeparatedItem(buf);
					//#CM47151
					//<en>If the number of items in format and the number of items differs,</en>
					if(data.length != key.length)
					{
						String errormsg = "Text Format Error ["+filename +"] : The number of items is "+data.length+". This must be "+ key.length + " .";
						throw new IllegalArgumentException( errormsg );
					}
			
					for(int j = 0; j < key.length; j++)	
					{
						hash.put(key[j], data[j]); 
					}
					vector.add(hash);

					Hashtable hs = (Hashtable)vector.get(0);
					String sql = makeSQL(conn, tablename, filename, hs, key);
					stmt.executeUpdate(sql);
				}
				count++;
			}
			fr.close();
			br.close();
			//#CM47152
			//<en>Check the size</en>
			if(count == 2)
			{
				String errormsg = "Text Format Error ["+filename +"] : Can't find DATA AREA.";
			}
		}
		else
		{
			throw new FileNotFoundException("File Not Found Error [" + filename +"] : File not found. ");
		}
	}

	//#CM47153
	// Package methods -----------------------------------------------

	//#CM47154
	// Protected methods ---------------------------------------------

	//#CM47155
	// Private methods -----------------------------------------------
	//#CM47156
	/**<en>
	 * Retrieve the name of table column that parameter specified by using DatabaseMetaData.
	 * @param <code>Connection</code> object
	 * @param tableName 
	 * @return   :store the column in Vector, then return.
	 </en>*/
	public static Vector getColumnNames(Connection conn, String tableName) throws Exception
	{
        //#CM47157
        //<en> Metadata information</en>
		DatabaseMetaData dmd = conn.getMetaData();
		Vector columnNames = new Vector();
		
		//#CM47158
		//<en> Extract the table information.</en>
		ResultSet resultSet = dmd.getColumns(null, USER, tableName, "%");
		while (resultSet.next())
		{
			String column = resultSet.getString(4);

			if(!columnNames.contains(column))
			{
				columnNames.addElement(column);
			}
		}
		resultSet.close();
		resultSet = null;
		return columnNames;
	}
	//#CM47159
	/**<en>
	 * Create the SQL string which will be used to retrieve the table.
	 * @param tableName 
	 * @return   SQL string
	 </en>*/
	private static String makeSQL(Connection conn, String tableName, Vector columnNames) throws Exception
	{
		String fmtSQL = "SELECT ";
		String columnSQL = "";
		for(int i = 0; i < columnNames.size(); i++)
		{
			String columnName = (String)columnNames.get(i);
			if(i == 0)
			{
				if(getColumnType(conn,tableName,columnName).equals("DATE"))
				{
					columnSQL = "TO_CHAR("+columnName+",'"+ DATEFORMAT+"') "+columnName;
				}
				else
				{
					columnSQL =columnName;
				}
			}
			else
			{
				if(getColumnType(conn,tableName,columnName).equals("DATE"))
				{
					columnSQL = columnSQL+","+"TO_CHAR("+columnName+",'"+DATEFORMAT+"') "+columnName;
				}
				else
				{
					columnSQL = columnSQL + "," + columnName ;
				}
			}
		}
		fmtSQL = fmtSQL + columnSQL + " FROM " + tableName;
		
		return fmtSQL;
	}

	private static String getDelimitedText(Vector vec)
	{
		String fmt = "";
		for(int i = 0; i < vec.size(); i++)
		{
			if(i ==  0)
			{
				fmt = (String)vec.get(0);
			}
			else
			{
				fmt = fmt + DELIM + (String)vec.get(i);
			}
		}
		return fmt + "\n";
	}


	private static String getColumnType(Connection conn, String tableName, String columnName) throws Exception
	{
        //#CM47160
        //<en> Metadata information</en>
		DatabaseMetaData dmd = conn.getMetaData();

		//#CM47161
		//<en> Extract the table information.</en>
		ResultSet resultSet = dmd.getColumns(conn.getCatalog(), null, tableName.toUpperCase(), columnName.toUpperCase());
		String typeName = "";
		while (resultSet.next())
		{
			typeName = resultSet.getString("TYPE_NAME");
		}
		resultSet.close();
		resultSet = null;
		
		return typeName;
		
	}

	//#CM47162
	/**<en>
	 * Sprit the String specified by parameter at specified delimtiers, then return the outcome
	 * in form of String array.
	 * Also it automatically delets spaces which follow the strings, if there are any, by 
	 * DisplayText.trim method.
	 * Example<BR>
	 * "AAA,BBB   ,CCC" => ret[0] = "AAA", ret[1] = "BBB", ret[2] = "CCC"<BR>
	 * @param buf :string which will be split
	 * @param buf :string which will be split
	 * @return    :array of the created String
	 </en>*/
	public static String[] getSeparatedItem(String buf)
	{
		Vector bufVec = new Vector();
		//#CM47163
		//<en>If there are consecutive delimiters, insert the space of 1 byte.</en>
		buf = delimiterCheck(buf,DELIM);
		StringTokenizer stk = new StringTokenizer(buf, DELIM, false) ;
		while ( stk.hasMoreTokens() )
		{
			bufVec.addElement(trim((String)stk.nextToken()));
		}
		String[] array = new String[bufVec.size()];
		bufVec.copyInto(array);
		return array;
	}

	//#CM47164
	/**<en>
	 * Replace the consecutive delimeters, of the String received as parameter, 
	 * with a space.<BR>
	 * Ex. item0001,100,200,,300, --> item0001,100,200, ,300,
	 </en>*/
	private static String delimiterCheck(String str, String delim)
	{
		//#CM47165
		//<en>If there are no delimiters, return itself as it is.</en>
		if(str.indexOf(delim) == -1)
		{
			return str;
		}
		StringBuffer sb = new StringBuffer(str) ;
		int len = sb.length() ;
		int i   = 0 ;
		for ( i = 0; i < len; i++)
		{
			if ( i < len - 1 )
			{
				if (sb.substring(i, i+2).equals( delim + delim ))
				{
					sb.replace(i, i+2, delim + " " + delim) ;
				}
			}
			len = sb.length() ;
		}
		if (sb.substring(len-1, len).equals(delim))
		{
			sb = sb.append(" ") ;
		}
		return (sb.toString()) ;
	}
	//#CM47166
	/**<en>
	 * Delete spaces from the end of the string.
	 * ex "   1 22   33333     " --> "   1 22   33333"
	 * @param str  :the string to edit
	 * @return str :the string from which the space was deleted. If the parameter is Null,
	 * return Null without any modification.
	 </en>*/
	private static String trim(String str)
	{
		if (str == null) return null ;

		int len = str.length() ;
		try
		{
			while (str.lastIndexOf(" ", len) == (len - 1))
			{
				len-- ;
				str = str.substring(0, len);
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			str = "";
		}
		return str ;
    }
	//#CM47167
	/**<en>
	 * Read the text specified by parameter, then return the data.
	 * @return <code>Vector</code>object
 	 * @throws Exception :return the exception without any modification.
	 </en>*/
	private static Vector getData(String filename) throws Exception
	{
		//#CM47168
		//<en>Read the text data.</en>
		String[] dataArray = getDataArray(filename);
		//#CM47169
		//<en>Read the format.</en>
		String format = getFormat(filename);

		Vector vec = new Vector(5);
		//#CM47170
		//<en>Split each item of header data to the arrays.</en>

		String[] key = getSeparatedItem(format);
		//#CM47171
		//<en>Mapping of Key and data to Hashtable.</en>
		for(int i = 0; i < dataArray.length; i++)
		{
			Hashtable hash = new Hashtable();
			String[] data = getSeparatedItem(dataArray[i]);

			//#CM47172
			//<en>If the number of items in format and the number of items differs,</en>
			if(data.length != key.length)
			{
				String errormsg = "Text Format Error ["+filename +"] : The number of items is "+data.length+". This must be "+ key.length + " .";
				throw new IllegalArgumentException( errormsg );
			}

			for(int j = 0; j < key.length; j++)	
			{
				hash.put(key[j], data[j]); 
			}
			vec.add(hash);
		}
		return vec;
	}
	
	//#CM47173
	/**<en>
	 * Create the SQL string which is for the creation of table.
	 * @param filename 
	 * @param hs 
	 * @return   SQL string
	 </en>*/
	static String[] columtype = new String[999]; 
	static String tableName0="";
	private static String makeSQL(Connection conn, String tablename, String filename, Hashtable hs, String[] key) throws Exception
	{
		String[] columnNames = key;
		//#CM47174
		//<en>Ommit the extension from the file name.</en>
		String tableName = tablename;

		String fmtSQL = "INSERT INTO " + tableName ;
		String columNameSQL = "";
		for(int i = 0; i < columnNames.length; i++)
		{
			if(i == 0)
			{
				columNameSQL = columnNames[0];
			}
			else
			{
				columNameSQL = columNameSQL + " , " + columnNames[i];
			}
		}
		fmtSQL = fmtSQL + " ( " + columNameSQL +" ) values ";

		//#CM47175
		//<en>The check of a column type is not necessary when the name of the current table is the same as the one previously handled.</en>
		if(!tableName0.equals(tableName))
		{
			for(int i = 0; i < hs.size(); i++)
			{
				columtype[i] = getColumnType(conn,tableName,columnNames[i]);
			}
			tableName0 = tableName;
		}

		String columValueSQL = "";		
		for(int j = 0; j < hs.size(); j++)
		{
			if(j == 0)
			{
				if(columtype[0].equals("DATE"))
				{
					columValueSQL = " TO_DATE('"+(String)hs.get(columnNames[0])+"','"+DATEFORMAT+"')";
				}
				else
				{
					columValueSQL = "'"+(String)hs.get(columnNames[0])+"'";
				}
			}
			else
			{
				if(columtype[j].equals("DATE"))
				{
					columValueSQL = columValueSQL+","+" TO_DATE('"+(String)hs.get(columnNames[j])+"','"+DATEFORMAT+"')";
				}
				else
				{
					columValueSQL = columValueSQL + "," + "'"+(String)hs.get(columnNames[j]) + "'";
				}
			}
		}
		return 	fmtSQL + " ( " + columValueSQL +" )";
	}
	
	//#CM47176
	/**<en>
	 * Read and return the header (version information) of text file.
	 * @param filename :name of the file which will be read
	 * @return  :header 
	 * @throws Exception :if exception occurred.
	 </en>*/
	private static String getVersion(String filename) throws Exception
	{
		String[] buf = getStringArray(filename,0);
		return buf[0];
	}
	//#CM47177
	/**<en>
	 * Read and return the header (format information) of text file.
	 * @param filename :name of the file which will be read
	 * @return :header 
	 * @throws Exception :if exception occurred.
	 </en>*/
	private static String getFormat(String filename) throws Exception
	{
		String[] buf = getStringArray(filename, 1);
		return buf[1];
	}
	
	//#CM47178
	/**<en>
	 * Read and return the data of text file.
	 * @param filename :name of the file which will be read
	 * @return :return the data in String array.
	 * @throws Exception :if exception occurred.
	 </en>*/
	private static String[] getDataArray(String filename) throws Exception
	{
		//#CM47179
		//<en>Start position of the data</en>
		int dataStartPoint = 2;
		
		String[] buf = getStringArray(filename, 2);
		String[] ret = new String[buf.length-dataStartPoint];
		for(int i = dataStartPoint; i < buf.length; i++)
		{
			ret[i-dataStartPoint] = buf[i];
		}
		return ret;
	}
	
	//#CM47180
	/**<en>
	 * Read the specified file and return the result in String array.
	 * @param filename :name of the file which will be read
	 * @param option 0:version retrieved, 1:format information retrieved, 2:data retrieved.
	 * @return :array of created String
	 * @throws FileNotFoundException :if specified data cannot be found.
	 * @throws IllegalArgumentException :this will bethrown in order to indicate that incorrect/inproper
	 * parameter was passed to the method.
	 * @throws IOException :if input/output error occurred.
	 </en>*/
	private static String[] getStringArray(String filename, int option) 
				throws FileNotFoundException, IllegalArgumentException, IOException
	{
		File file = new File(filename);
		if(file.exists())
		{
			//#CM47181
			//<en>Reading file</en>
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			Vector vec = new Vector(10);
			String buf = "";
			int count = 0;
			while(( buf = br.readLine())!=null)
			{
				vec.add(buf);
				if(option == 0 && count == 0)
				{
					break;
				}
				else if(option == 1 && count == 1)
				{
					break;
				}
				count++;
			}
			fr.close();
			br.close();
			//#CM47182
			//<en>Check the size</en>
			if(option != 0 &&count == 0)
			{
				String errormsg = "Text Format Error ["+filename +"] : Can't find VERSION AREA.";
				throw new IllegalArgumentException( errormsg );
			}
			else if(option != 1 &&count == 1)
			{
				String errormsg = "Text Format Error ["+filename +"] : Can't find FORMAT AREA.";
				throw new IllegalArgumentException( errormsg );
			}
			else if(count == 2)
			{
				String errormsg = "Text Format Error ["+filename +"] : Can't find DATA AREA.";
				//#CM47183
				//throw new IllegalArgumentException( errormsg );
			}
			String[] array = new String[vec.size()];
			vec.copyInto(array);
			return array;
		}
		else
		{
			throw new FileNotFoundException("File Not Found Error [" + filename +"] : File not found. ");
		}
	}

}
//#CM47184
//end of class


