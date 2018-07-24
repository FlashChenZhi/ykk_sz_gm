// $Id: SQLAlterKey.java,v 1.3 2006/11/21 04:22:35 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler ;

//#CM708775
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DBFormat ;
import jp.co.daifuku.wms.base.common.AlterKey;


//#CM708776
/**
 * The class which uses it to do the table of the data base in Update. 
 * The instance of the SQLAlterKey class maintains Update content and UpdateCondition, and assembles the SQl sentence based on the maintained content. 
 * The assembled SQl sentence is executed by the DateBaseHandler class, and does Update of the data base. 
 * The method of setting Update content and UpdateCondition is mounted by the class which succeeds to this class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:22:35 $
 * @author  $Author: suresh $
 */
public class SQLAlterKey
		implements AlterKey
{
	//#CM708777
	// Class fields --------------------------------------------------

	//#CM708778
	// Class variables -----------------------------------------------
	//#CM708779
	/**
	 * Array for retrieval Key preservation
	 */
	protected Vector _conditionList = null ;

	//#CM708780
	/**
	 * Vector for automatic operation UpdateKey row name preservation
	 */
	protected Vector _autoUpdateColumnList = null;

	//#CM708781
	/**
	 * Delimiter
	 * When Exception is generated, the delimiter of the parameter of the message of MessageDef. 
	 * 	Example String msginfo = "9000000" + wDelim + "Palette" + wDelim + "Stock" ;
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM708782
	// Class method --------------------------------------------------
	//#CM708783
	/**
	 * Return the version of this class. 
	 * @return Version
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $") ;
	}

	//#CM708784
	// Constructors --------------------------------------------------

	//#CM708785
	// Public methods ------------------------------------------------

	//#CM708786
	/**
	 * Set Retrieval value of the character string in the specified column. 
	 * @param column
	 * @param value
	 * @param compcode
	 * @param left_Paren
	 * @param right_Paren
	 * @param and_or
	 * @throws ReadWriteException
	 */
	public void setValue(String column, String value, String compcode, String left_Paren,
			String right_Paren, String and_or)
			throws ReadWriteException
	{
		//#CM708787
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		//#CM708788
		// Set one blank byte When null or the null character string is set in Retrieval value. 
		//#CM708789
		// The Condition sentence becomes as WHERE XXX  = ''.
		if (value == null)
		{
			Eky.setTableValue(" ") ;
		}
		else if (value.length() == 0)
		{
			Eky.setTableValue(" ") ;
		}
		else
		{
			Eky.setTableValue(value) ;
		}

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708790
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708791
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}

		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708792
	/**
	 * Set Retrieval value of the character string in the specified column. 
	 * Only Retrieval Condition AND
	 * Comparison function = Assumed.
	 * @param column
	 * @param value
	 * @throws ReadWriteException
	 */
	public void setValue(String column, String value)
			throws ReadWriteException
	{
		setValue(column, value, "=", "", "", "AND") ;
	}

	//#CM708793
	/**
	 * Set Retrieval value of the character string in the specified column. 
	 * Only Retrieval Condition AND
	 * @param column
	 * @param value
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, String value, String compcode)
			throws ReadWriteException
	{
		setValue(column, value, compcode, "", "", "AND") ;
	}

	//#CM708794
	/**
	 * Set Retrieval value of the character string in the specified column. 
	 * Comparison function = Assumed.
	 * @param column
	 * @param value
	 * @throws ReadWriteException
	 */
	public void setValue(String column, String value[])
			throws ReadWriteException
	{
		for (int lc = 0; lc < value.length; lc++)
		{
			String pvalue = value[lc] ;
			String left_par = "" ;
			String right_par = "" ;
			String wcomp = "OR" ;
			if (lc == 0)
			{
				left_par = "(" ;
			}
			if (lc == (value.length - 1))
			{
				right_par = ")" ;
				wcomp = "AND" ;
			}
			setValue(column, pvalue, "=", left_par, right_par, wcomp) ;
		}
	}

	//#CM708795
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @param left_Paren
	 * @param right_Paren
	 * @param and_or
	 * @throws ReadWriteException
	 */
	public void setValue(String column, int intval, String compcode, String left_Paren,
			String right_Paren, String and_or)
			throws ReadWriteException
	{
		//#CM708796
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		Eky.setTableValue(new Integer(intval)) ;

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708797
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708798
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}
		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708799
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * Only Retrieval Condition AND
	 * Comparison function = Assumed.
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, int intval)
			throws ReadWriteException
	{
		setValue(column, intval, "=", "", "", "AND") ;
	}

	//#CM708800
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * Only Retrieval Condition AND
	 * @param column
	 * @param intval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, int intval, String compcode)
			throws ReadWriteException
	{
		setValue(column, intval, compcode, "", "", "AND") ;
	}

	//#CM708801
	/**
	 * Set Retrieval value of a numeric row in the specified column. 
	 * Comparison function = Assumed.
	 * @param column
	 * @param value
	 * @throws ReadWriteException
	 */
	public void setValue(String column, int value[])
			throws ReadWriteException
	{
		for (int lc = 0; lc < value.length; lc++)
		{
			int pvalue = value[lc] ;
			String left_par = "" ;
			String right_par = "" ;
			String wcomp = "OR" ;
			if (lc == 0)
			{
				left_par = "(" ;
			}
			if (lc == (value.length - 1))
			{
				right_par = ")" ;
				wcomp = "AND" ;
			}
			setValue(column, pvalue, "=", left_par, right_par, wcomp) ;
		}
	}

	//#CM708802
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @param left_Paren
	 * @param right_Paren
	 * @param and_or
	 * @throws ReadWriteException
	 */
	public void setValue(String column, long intval, String compcode, String left_Paren,
			String right_Paren, String and_or)
			throws ReadWriteException
	{
		//#CM708803
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		Eky.setTableValue(new Long(intval)) ;

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708804
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708805
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}
		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708806
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * Only Retrieval Condition AND
	 * Comparison function = Assumed.
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, long intval)
			throws ReadWriteException
	{
		setValue(column, intval, "=", "", "", "AND") ;
	}

	//#CM708807
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * Only Retrieval Condition AND
	 * @param column
	 * @param intval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, long intval, String compcode)
			throws ReadWriteException
	{
		setValue(column, intval, compcode, "", "", "AND") ;
	}

	//#CM708808
	/**
	 * Set Retrieval value of a numeric row in the specified column. 
	 * Comparison function = Assumed.
	 * @param column
	 * @param value
	 * @throws ReadWriteException
	 */
	public void setValue(String column, long value[])
			throws ReadWriteException
	{
		for (int lc = 0; lc < value.length; lc++)
		{
			long pvalue = value[lc] ;
			String left_par = "" ;
			String right_par = "" ;
			String wcomp = "OR" ;
			if (lc == 0)
			{
				left_par = "(" ;
			}
			if (lc == (value.length - 1))
			{
				right_par = ")" ;
				wcomp = "AND" ;
			}
			setValue(column, pvalue, "=", left_par, right_par, wcomp) ;
		}
	}

	//#CM708809
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @param left_Paren
	 * @param right_Paren
	 * @param and_or
	 * @throws ReadWriteException
	 */
	public void setValue(String column, double intval, String compcode, String left_Paren,
			String right_Paren, String and_or)
			throws ReadWriteException
	{
		//#CM708810
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		Eky.setTableValue(new Double(intval)) ;

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708811
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708812
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}
		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708813
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * Only Retrieval Condition AND
	 * Comparison function = Assumed.
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, double intval)
			throws ReadWriteException
	{
		setValue(column, intval, "=", "", "", "AND") ;
	}

	//#CM708814
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * Only Retrieval Condition AND
	 * @param column
	 * @param intval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, double intval, String compcode)
			throws ReadWriteException
	{
		setValue(column, intval, compcode, "", "", "AND") ;
	}

	//#CM708815
	/**
	 * Set Retrieval value of a numeric row in the specified column. 
	 * Comparison function = Assumed.
	 * @param column
	 * @param value
	 * @throws ReadWriteException
	 */
	public void setValue(String column, double value[])
			throws ReadWriteException
	{
		for (int lc = 0; lc < value.length; lc++)
		{
			double pvalue = value[lc] ;
			String left_par = "" ;
			String right_par = "" ;
			String wcomp = "OR" ;
			if (lc == 0)
			{
				left_par = "(" ;
			}
			if (lc == (value.length - 1))
			{
				right_par = ")" ;
				wcomp = "AND" ;
			}
			setValue(column, pvalue, "=", left_par, right_par, wcomp) ;
		}
	}

	//#CM708816
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * @param column
	 * @param intval
	 * @param compcode
	 * @param left_Paren
	 * @param right_Paren
	 * @param and_or
	 * @throws ReadWriteException
	 */
	public void setValue(String column, float intval, String compcode, String left_Paren,
			String right_Paren, String and_or)
			throws ReadWriteException
	{
		//#CM708817
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		Eky.setTableValue(new Float(intval)) ;

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708818
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708819
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}
		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708820
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * Only Retrieval Condition AND
	 * Comparison function = Assumed.
	 * @param column
	 * @param intval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, float intval)
			throws ReadWriteException
	{
		setValue(column, intval, "=", "", "", "AND") ;
	}

	//#CM708821
	/**
	 * Set Retrieval value of a numeric type in the specified column. 
	 * Only Retrieval Condition AND
	 * @param column
	 * @param intval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, float intval, String compcode)
			throws ReadWriteException
	{
		setValue(column, intval, compcode, "", "", "AND") ;
	}

	//#CM708822
	/**
	 * Set Retrieval value of a numeric row in the specified column. 
	 * Comparison function = Assumed.
	 * @param column
	 * @param value
	 * @throws ReadWriteException
	 */
	public void setValue(String column, float value[])
			throws ReadWriteException
	{
		for (int lc = 0; lc < value.length; lc++)
		{
			float pvalue = value[lc] ;
			String left_par = "" ;
			String right_par = "" ;
			String wcomp = "OR" ;
			if (lc == 0)
			{
				left_par = "(" ;
			}
			if (lc == (value.length - 1))
			{
				right_par = ")" ;
				wcomp = "AND" ;
			}
			setValue(column, pvalue, "=", left_par, right_par, wcomp) ;
		}
	}

	//#CM708823
	/**
	 * Set Retrieval value of the date type in the specified column. 
	 * @param column
	 * @param dtval
	 * @param compcode
	 * @param left_Paren
	 * @param right_Paren
	 * @param and_or
	 * @throws ReadWriteException
	 */
	public void setValue(String column, Date dtval, String compcode, String left_Paren,
			String right_Paren, String and_or)
			throws ReadWriteException
	{
		//#CM708824
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		Eky.setTableValue(dtval) ;

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708825
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk != 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
			}
			//#CM708826
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}
		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708827
	/**
	 * Set Retrieval value of the date type in the specified column. 
	 * Only Retrieval Condition AND
	 * Comparison function = Assumed.
	 * @param column
	 * @param dtval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, Date dtval)
			throws ReadWriteException
	{
		setValue(column, dtval, "=", "", "", "AND") ;
	}

	//#CM708828
	/**
	 * Set Retrieval value of the date type in the specified column. 
	 * Only Retrieval Condition AND
	 * @param column
	 * @param dtval
	 * @param compcode
	 * @throws ReadWriteException
	 */
	public void setValue(String column, Date dtval, String compcode)
			throws ReadWriteException
	{
		setValue(column, dtval, compcode, "", "", "AND") ;
	}

	//#CM708829
	/**
	 * Set Retrieval value of a numeric row in the specified column. 
	 * Comparison function = Assumed.
	 * @param column
	 * @param dtval
	 * @throws ReadWriteException
	 */
	public void setValue(String column, Date dtval[])
			throws ReadWriteException
	{
		for (int lc = 0; lc < dtval.length; lc++)
		{
			Date pdtval = dtval[lc] ;
			String left_par = "" ;
			String right_par = "" ;
			String wcomp = "OR" ;
			if (lc == 0)
			{
				left_par = "(" ;
			}
			if (lc == (dtval.length - 1))
			{
				right_par = ")" ;
				wcomp = "AND" ;
			}
			setValue(column, pdtval, "=", left_par, right_par, wcomp) ;
		}
	}


	//#CM708830
	/**
	 * @see dbhandler.AlterKey#setUpdValue(java.lang.String, java.lang.String)
	 */
	public void setUpdValue(String column, String value)
	{
		Key ky = getKey(column) ;
		ky.setUpdValue(value) ;
		setKey(ky) ;
	}

	//#CM708831
	// Set the Update value of a numeric type in the specified column. 
	//#CM708832
	/**
	 * @see dbhandler.AlterKey#setUpdValue(java.lang.String, int)
	 */
	public void setUpdValue(String column, int intval)
	{
		Key ky = getKey(column) ;
		ky.setUpdValue(new Integer(intval)) ;
		setKey(ky) ;
	}

	//#CM708833
	/**
	 * @see dbhandler.AlterKey#setUpdValue(java.lang.String, double)
	 */
	//#CM708834
	// Set the Update value of a numeric type in the specified column. 
	public void setUpdValue(String column, double intval)
	{
		Key ky = getKey(column) ;
		ky.setUpdValue(new Double(intval)) ;
		setKey(ky) ;
	}

	//#CM708835
	/**
	 * @see dbhandler.AlterKey#setUpdValue(java.lang.String, float)
	 */
	//#CM708836
	// Set the Update value of a numeric type in the specified column. 
	public void setUpdValue(String column, float intval)
	{
		Key ky = getKey(column) ;
		ky.setUpdValue(new Float(intval)) ;
		setKey(ky) ;
	}

	//#CM708837
	/**
	 * @see dbhandler.AlterKey#setUpdValue(java.lang.String, java.util.Date)
	 */
	//#CM708838
	// Set Retrieval value of the date type in the specified column. 
	public void setUpdValue(String column, Date dtval)
	{
		Key ky = getKey(column) ;
		ky.setUpdValue(dtval) ;
		setKey(ky) ;
	}

	//#CM708839
	/**
	 * Generate the WHERE phrase of SQL. 
	 * Generate Condition only with the column defined in the specified table. 
	 * @param tablename Table Name which generates Condition sentence
	 * @return
	 * @throws ReadWriteException
	 */
	public String getReferenceCondition(String tablename)
			throws ReadWriteException
	{

		//#CM708840
		// StringWriter wSW = new StringWriter() ; // Used for LogWrite when Exception is generated. 
		//#CM708841
		// PrintWriter wPW = new PrintWriter(wSW) ; // Used for LogWrite when Exception is generated. 
		String wDelim = MessageResource.DELIM ; // デリミタ

		StringBuffer stbf = new StringBuffer(512) ;
		boolean existFlg = false ;
		int s_cond = 0 ; // AND 又は OR条件
		int st_Parenthesis = 0 ; // ’（’文字数
		int en_Parenthesis = 0 ; // ’）’文字数
		int total_stparen = 0 ; // ’（’トータル文字数
		int total_enparen = 0 ; // ’）’トータル文字数

		for (int i = 0; i < _conditionList.size(); i++)
		{
			Key ky = (Key)_conditionList.get(i) ;
			if (ky.getTableValue() != null)
			{
				s_cond = ky.getTableCondtion() % 10 ;
				st_Parenthesis = ky.getTableCondtion() / 100 ;
				en_Parenthesis = (ky.getTableCondtion() / 10) % 10 ;

				total_stparen += st_Parenthesis ;
				total_enparen += en_Parenthesis ;

				//#CM708842
				// set the '(' character for necessary part. 
				for (int lp = 0; lp < st_Parenthesis; lp++)
					stbf.append("(") ;

				//#CM708843
				// Use the LIKE retrieval when whether the pattern collation is included when the character value is verified, and it exists. 
				if (ky.getTableValue() instanceof String)
				{
					if (DBFormat.isPatternMatching((String)ky.getTableValue()))
					{
						stbf.append(" RTRIM(") ;
						stbf.append(ky.getTableColumn()) ;
						stbf.append(") ") ;
						stbf.append(" LIKE ") ;
					}
					else
					{
						stbf.append(ky.getTableColumn()) ;
						stbf.append(" ") ;
						//#CM708844
						//						}
						//#CM708845
						// Replace it with IS NULL. 
						if (ky.getTableValue() instanceof String
								&& ((String)ky.getTableValue()).trim().equals(""))
						{
							stbf.append("IS NULL") ;
						}
						else
						{
							stbf.append(ky.getTableCompCode()) ;
						}
						stbf.append(" ") ;
					}
				}
				else
				{
					stbf.append(ky.getTableColumn()) ;
					stbf.append(" ") ;
					stbf.append(ky.getTableCompCode()) ;
					stbf.append(" ") ;
				}

				if (ky.getTableValue() instanceof String
						&& !((String)ky.getTableValue()).trim().equals(""))
				{
					stbf.append(DBFormat.format((String)ky.getTableValue())) ;
				}
				else if (ky.getTableValue() instanceof Date)
				{
					stbf.append(DBFormat.format((Date)ky.getTableValue())) ;
				}
				else
				{
					stbf.append(ky.getTableValue()) ;
				}
				//#CM708846
				//				}

				//#CM708847
				// set the ')' character for necessary part. 
				for (int lp = 0; lp < en_Parenthesis; lp++)
					stbf.append(")") ;

				if (s_cond == 0)
				{
					stbf.append(" AND ") ;
				}
				else
				{
					stbf.append(" OR ") ;
				}

				if (existFlg == false)
					existFlg = true ;
			}
		}
		//#CM708848
		// Return null when data is not set in the key value. 
		if (existFlg == false)
			return null ;

		//#CM708849
		// It returns ReadWriteException when Condition () is not corresponding. 
		if (total_stparen != total_enparen)
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLAlterKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLAlterKey")) ;
		}

		//#CM708850
		// The last "AND" is removed because it is extra. 
		int ep = 0 ;
		if (s_cond == 0)
		{
			ep = stbf.toString().lastIndexOf("AND") ;
		}
		else
		{
			ep = stbf.toString().lastIndexOf("OR") ;
		}
		return stbf.toString().substring(0, ep) ;
	}

	//#CM708851
	/**
	 * Set the Update value of automatic operation Update object row. 
	 */
	public void setAutoModify() throws ReadWriteException
	{
		for (int i = 0; i < _autoUpdateColumnList.size(); i++)
		{
			setUpdValue(_autoUpdateColumnList.get(i).toString(), new Date());
		}
	}

	//#CM708852
	/**
	 * Generate the UPDATE SET phrase of SQL from set Key. 
	 * Generate Condition only with the column defined in the specified table. 
	 * @param tablename Table Name which generates Condition sentence
	 * @return Update content
	 */
	public String getModifyContents(String tablename)
	{
		StringBuffer stbf = new StringBuffer(256) ;
		boolean existFlg = false ;

		for (int i = 0; i < _conditionList.size(); i++)
		{
			Key ky = (Key)_conditionList.get(i) ;
			//#CM708853
			// Whether the UPDATE object column is verified to each column. 
			if (ky.isUpdate())
			{
				//#CM708854
				// Acquire Table Name from the column name. TableName.ColumnName -> TableName
				int fp = ky.getTableColumn().indexOf(".") ;
				if (fp == -1)
				{
					//#CM708855
					// Because of an irregular column definition, the log is output. 
					Object[] tObj = new Object[2] ;
					tObj[0] = ky.getTableColumn() ;
					tObj[1] = ky.getTableColumn() ;
					RmiMsgLogClient.write(6006004, LogMessage.F_WARN, this.getClass().getName(),
							tObj) ;
					continue ;
				}
				String str = ky.getTableColumn().substring(0, fp) ;
				//#CM708856
				// Make it to the object column of the Condition sentence if it agrees to Table Name of the call parameter. 
				if (str.equals(tablename))
				{
					//#CM708857
					// A set value is taken out in case of the UPDATE object column and it edits it. 
					stbf.append(ky.getTableColumn()) ;
					stbf.append(" = ") ;
					if (ky.getUpdValue() instanceof String)
					{
						stbf.append(DBFormat.format((String)ky.getUpdValue())) ;
					}
					else if (ky.getUpdValue() instanceof Date)
					{
						stbf.append(DBFormat.format((Date)ky.getUpdValue())) ;
					}
					else
					{
						stbf.append(ky.getUpdValue()) ;
					}
					stbf.append(", ") ;
					if (existFlg == false)
						existFlg = true ;
				}
			}
		}
		//#CM708858
		// Return null when data is not set in the key value. 
		if (existFlg == false)
			return null ;

		//#CM708859
		// The last ""is removed because it is extra. 
		int ep = stbf.toString().lastIndexOf(",") ;
		return stbf.toString().substring(0, ep) ;
	}

	//#CM708860
	// Package methods -----------------------------------------------

	//#CM708861
	// Protected methods ---------------------------------------------
	//#CM708862
	/**
	 * Set the column name. 
	 * @param Array of column name
	 */
	protected void setColumns(String[] columns)
	{
		_conditionList = new Vector(columns.length) ;
		for (int i = 0; i < columns.length; i++)
		{
			//#CM708863
			// Key column set
			_conditionList.addElement(new Key(columns[i])) ;
		}
	}

	//#CM708864
	/**
	 * Maintain the column name of automatic operation Update object. 
	 * @param Array of column name
	 */
	protected void setAutoColumns(String[] columns)
	{
		_autoUpdateColumnList = new Vector(columns.length) ;
		for (int i = 0; i < columns.length; i++)
		{
			//#CM708865
			// Set Update row name Automatically
			_autoUpdateColumnList.addElement(columns[i]) ;
		}
	}

	//#CM708866
	/**
	 * Clear the Vector area of Retrieval Condition and Update Condition. 
	 */
	protected void clearConditions()
	{
		int i ;
		for (i = 0; i < _conditionList.size(); i++)
		{
			Key ky = (Key)_conditionList.get(i) ;
			if (ky.getTableValue() != null)
				break ;
			//#CM708867
			// Clear Update Condition
			ky.setUpdClear() ;
			_conditionList.set(i, ky) ;
		}

		for (int j = i; j < _conditionList.size();)
		{
			_conditionList.removeElementAt(j) ;
		}
		
	}

	//#CM708868
	// Private methods -----------------------------------------------
	//#CM708869
	// Return a corresponding Key instance to the specified key word (column). 
	protected Key getKey(String keyword)
	{
		for (int i = 0; i < _conditionList.size(); i++)
		{
			Key ky = (Key)_conditionList.get(i) ;
			if (ky.getTableColumn().equals(keyword))
			{
				return ky ;
			}
		}
		return null ;
	}

	//#CM708870
	// Set the value in a corresponding Key instance to the specified Key word (column). 
	protected void setKey(Key key)
	{
		for (int i = 0; i < _conditionList.size(); i++)
		{
			Key ky = (Key)_conditionList.get(i) ;
			if (ky.getTableColumn().equals(key.getTableColumn()))
			{
				ky.setUpdValue(key.getUpdValue()) ;
				ky.setUpdate(true) ;
				_conditionList.set(i, ky) ;
				break ;
			}
		}
	}

	//#CM708871
	/**
	 * Retrieve a corresponding Key instance to the specified Key word (column). 
	 * Set the area addition and the column name in Vector. 
	 * @param keyword
	 * @return Key
	 */
	protected Key getKeyAdd(String keyword)
	{
		for (int i = 0; i < _conditionList.size(); i++)
		{
			Key ky = (Key)_conditionList.get(i) ;
			if (ky.getTableColumn().equals(keyword))
			{
				_conditionList.addElement(new Key(ky.getTableColumn())) ;
				int last_idx = _conditionList.size() - 1 ;
				Key key = (Key)_conditionList.get(last_idx) ;
				return key ;
			}
		}
		return null ;
	}

	//#CM708872
	/**
	 * Add it to the SearchKey instance by the specified key word (column). 
	 * @param key
	 */
	protected void setSearchKey(Key key)
	{
		int last_idx = _conditionList.size() - 1 ;
		_conditionList.set(last_idx, key) ;
	}


	//#CM708873
	// Inner Class ---------------------------------------------------
	//#CM708874
	// The class which maintains the column, Retrieval value, The order of sorting, and the update value of the key. 
	protected class Key
	{

		private String Column ; // カラム 

		private Object Value ; // 検索値（様々な型がセットされる可能性がある事を考慮） 

		private int Condtion ; // 検索条件

		private String CompCode ; // 比較条件

		private Object UpdValue ; // アップデート値 

		private boolean Update ; // UPDATE対象カラム true:UPDATE対象 false:UPDATE未対象

		protected Key(String clm)
		{
			Column = clm ;
			Value = null ;
			Condtion = 0 ;
			CompCode = null ;
			UpdValue = null ;
			Update = false ;
		}

		//#CM708875
		// Acquire Column of table. 
		protected String getTableColumn()
		{
			return Column ;
		}

		//#CM708876
		// Set Retrieval value corresponding to Column of table. 
		protected void setTableValue(Object val)
		{
			Value = val ;
		}

		//#CM708877
		// Acquire Retrieval value corresponding to the column of the table. 
		protected Object getTableValue()
		{
			return Value ;
		}

		//#CM708878
		/**
		 * Set Retrieval Condition in the table. 
		 * @param pcond
		 */
		protected void setTableCondtion(int pcond)
		{
			Condtion = pcond ;
		}

		//#CM708879
		/**
		 * Acquire Retrieval Condition corresponding to the column of the table. 
		 * @return Retrieval Condition
		 */
		protected int getTableCondtion()
		{
			return Condtion ;
		}

		//#CM708880
		/**
		 * Set Comparison Condition in the table. 
		 * @param compcode
		 */
		protected void setTableCompCode(String compcode)
		{
			CompCode = compcode ;
		}

		//#CM708881
		/**
		 * Acquire Comparison Condition corresponding to the column of the table. 
		 * @return Comparison Condition
		 */
		protected String getTableCompCode()
		{
			return CompCode ;
		}

		//#CM708882
		// Set the Update value corresponding to Column of table. 
		protected void setUpdValue(Object val)
		{
			UpdValue = val ;
		}

		//#CM708883
		// Acquire the Update value corresponding to Column of table. 
		protected Object getUpdValue()
		{
			return UpdValue ;
		}

		//#CM708884
		// Set UPDATE of the column. 
		protected void setUpdate(boolean bool)
		{
			Update = bool ;
		}

		//#CM708885
		// Acquire the Update value corresponding to Column of table. 
		protected boolean getUpdate()
		{
			return Update ;
		}

		//#CM708886
		// Clear the Update value corresponding to Column of table. 
		//#CM708887
		// Clear UPDATE of the column. 
		protected void setUpdClear()
		{
			UpdValue = null ;
			Update = false ;
		}

		//#CM708888
		// Whether the column is an object of UPDATE is verified. 
		protected boolean isUpdate()
		{
			return Update ;
		}
	}
}
//#CM708889
//end of class

