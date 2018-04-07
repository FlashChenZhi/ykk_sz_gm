// $Id: SQLSearchKey.java,v 1.3 2006/11/15 04:25:40 kamala Exp $
package jp.co.daifuku.wms.base.dbhandler ;

//#CM708890
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
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.common.WmsParam;

//#CM708891
/**
 * The class which uses it to retrieve the table of the data base. 
 * The instance of the SQLSearchKey class maintains Retrieval Condition, and assembles SQL sentence based on the maintained content. 
 * The assembled SQL sentence is executed by the Databasehandler class, and inquires the data base. 
 * The method of setting Retrieval Condition is mounted by the class which succeeds to this class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/15 04:25:40 $
 * @author  $Author: kamala $
 */
public class SQLSearchKey
		implements SearchKey
{
	//#CM708892
	// Class fields --------------------------------------------------

	//#CM708893
	// Class variables -----------------------------------------------
	//#CM708894
	/**
	 * Array for preservation of retrieval key
	 */
	protected Vector _searchKeyList = null ;

	//#CM708895
	// Class method --------------------------------------------------
	//#CM708896
	/**
	 * Return the version of this class. 
	 * @return Version
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $") ;
	}

	//#CM708897
	/**
	 * Delimiter
	 * When Exception is generated, the delimiter of the parameter of the message of MessageDef. 
	 * 	Example String msginfo = "9000000" + wDelim + "Palette" + wDelim + "Stock" ;
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM708898
	// Constructors --------------------------------------------------

	//#CM708899
	// Public methods ------------------------------------------------

	//#CM708900
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
		//#CM708901
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		//#CM708902
		// When null or the null character string is set in Retrieval value
		if (value == null)
		{
			Eky.setTableValue("") ;
		}
		else if (value.length() == 0)
		{
			Eky.setTableValue("") ;
		}
		else
		{
			Eky.setTableValue(value) ;
		}

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708903
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708904
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}

		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708905
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

	//#CM708906
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

	//#CM708907
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

	//#CM708908
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
		//#CM708909
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		Eky.setTableValue(new Integer(intval)) ;

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708910
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708911
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}
		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708912
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

	//#CM708913
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

	//#CM708914
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

	//#CM708915
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
		//#CM708916
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		Eky.setTableValue(new Long(intval)) ;

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708917
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708918
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}
		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708919
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

	//#CM708920
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

	//#CM708921
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

	//#CM708922
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
		//#CM708923
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		Eky.setTableValue(new Double(intval)) ;

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708924
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708925
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}
		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708926
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

	//#CM708927
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

	//#CM708928
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

	//#CM708929
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
		//#CM708930
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		Eky.setTableValue(new Float(intval)) ;

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708931
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708932
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}
		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708933
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

	//#CM708934
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

	//#CM708935
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

	//#CM708936
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
		//#CM708937
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKeyAdd(column) ;
		if (Eky == null)
			return ;

		Eky.setTableValue(dtval) ;

		int pcond = 0 ;
		if (!left_Paren.equals("") && left_Paren.length() > 0)
		{
			int cchk = left_Paren.indexOf("(") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708938
			// Request the number of left parenthesis * 100
			pcond += left_Paren.length() * 100 ;
		}
		if (!right_Paren.equals("") && right_Paren.length() > 0)
		{
			int cchk = right_Paren.indexOf(")") ;
			if (cchk < 0)
			{
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
				throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
			}
			//#CM708939
			// Request the number of right parenthesis * 10
			pcond += right_Paren.length() * 10 ;
		}
		if (!and_or.toUpperCase().equals("AND") && !and_or.toUpperCase().equals("OR"))
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
		}
		if (and_or.toUpperCase().equals("OR"))
		{
			pcond++ ;
		}
		Eky.setTableCondtion(pcond) ;
		Eky.setTableCompCode(compcode) ;
		setSearchKey(Eky) ;
	}

	//#CM708940
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

	//#CM708941
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

	//#CM708942
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

	//#CM708943
	/**
	 * Set The order of sorting in the specified column. 
	 * @param column
	 * @param num
	 * @param bool
	 */
	public void setOrder(String column, int num, boolean bool)
	{
		//#CM708944
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKey(column) ;
		if (Eky == null)
			return ;

		Eky.setTableOrder(num) ;
		Eky.setTableDesc(bool) ;
		setSortKey(Eky) ;
	}

	//#CM708945
	/**
	 * Set The order of group in the specified column. 
	 * @param column
	 * @param num
	 */
	public void setGroup(String column, int num)
	{
		//#CM708946
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKey(column) ;
		if (Eky == null)
			return ;

		Eky.setTableGroup(num) ;
		setGroupKey(Eky) ;
	}

	//#CM708947
	/**
	 * Set acquisition Condition of the specified column. 
	 * @param column
	 * @param value
	 */
	public void setCollect(String column, String value)
	{
		//#CM708948
		// Disregard it when the key which does not exist in Effective is specified. 
		Key Eky = getKey(column) ;
		if (Eky == null)
			return ;

		Eky.setTableCollect(value) ;
		setCollectKey(Eky) ;
	}

	//#CM708949
	/**
	 * Generate the WHERE phrase of SQL from the set key. 
	 * @return
	 * @throws ReadWriteException
	 */
	public String getReferenceCondition()
			throws ReadWriteException
	{
		StringBuffer stbf = new StringBuffer(512) ;
		boolean existFlg = false ;
		int s_cond = 0 ; // AND 又は OR条件
		int st_Parenthesis = 0 ; // ’（’文字数
		int en_Parenthesis = 0 ; // ’）’文字数
		int total_stparen = 0 ; // ’（’トータル文字数
		int total_enparen = 0 ; // ’）’トータル文字数

		for (int i = 0; i < _searchKeyList.size(); i++)
		{
			Key ky = (Key)_searchKeyList.get(i) ;
			if (ky.getTableValue() != null)
			{
				s_cond = ky.getTableCondtion() % 10 ;
				st_Parenthesis = ky.getTableCondtion() / 100 ;
				en_Parenthesis = (ky.getTableCondtion() / 10) % 10 ;

				total_stparen += st_Parenthesis ;
				total_enparen += en_Parenthesis ;

				//#CM708950
				// set the '(' character for necessary part. 
				for (int lp = 0; lp < st_Parenthesis; lp++)
					stbf.append("(") ;

				//#CM708951
				// Use the LIKE retrieval when whether the pattern collation is included when the character value is verified, and it exists. 
				if (ky.getTableValue() instanceof String)
				{
					if (DBFormat.isPatternMatching((String)ky.getTableValue()))
					{
						stbf.append(ky.getTableColumn()) ;
						stbf.append(" LIKE ") ;
					}
					else
					{
						stbf.append(ky.getTableColumn()) ;
						stbf.append(" ") ;
						
						//#CM708952
						// Replace it with IS NULL. 
						if(((String)ky.getTableValue()).trim().equals("") && 
						!ky.getTableCompCode().equals("IS NOT NULL"))
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
					stbf.append(DBFormat.format(((String)ky.getTableValue())).replaceAll(
							"[" + WmsParam.PATTERNMATCHING + "]", "%")) ;
				}
				else if (ky.getTableValue() instanceof Date)
				{
					stbf.append(DBFormat.format((Date)ky.getTableValue())) ;
				}
				else
				{
					stbf.append(ky.getTableValue()) ;
				}

				//#CM708953
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
		//#CM708954
		// Return null when data is not set in the key value. 
		if (existFlg == false)
			return null ;

		//#CM708955
		// It returns ReadWriteException when Condition () is not corresponding. 
		if (total_stparen != total_enparen)
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
		}
		//#CM708956
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

	//#CM708957
	/**
	 * Generate ORDER BY Phrase of SQL from the set key. 
	 * @return ORDER BY Phrase
	 */
	public String getSortCondition()
	{
		Key[] karray = new Key[_searchKeyList.size()] ;

		_searchKeyList.copyInto(karray) ;
		for (int i = 0; i < karray.length; i++)
		{
			for (int j = i; j < karray.length; j++)
			{
				if (karray[i].getTableOrder() > karray[j].getTableOrder())
				{
					Key ktmp = karray[i] ;
					karray[i] = karray[j] ;
					karray[j] = ktmp ;
				}
			}
		}

		StringBuffer stbf = new StringBuffer(256) ;
		boolean existFlg = false ;

		for (int i = 0; i < karray.length; i++)
		{
			if (karray[i].getTableOrder() > 0)
			{
				stbf.append(karray[i].getTableColumn()) ;
				//#CM708958
				// The descending key word is set for the descending order. 
				if (karray[i].getTableDesc() == false)
				{
					stbf.append(" DESC") ;
				}
				stbf.append(", ") ;
				if (existFlg == false)
					existFlg = true ;
			}
		}
		//#CM708959
		// Return null when data is not set in the key value. 
		if (existFlg == false)
			return null ;

		//#CM708960
		// The last ""is removed because it is extra. 
		int ep = stbf.toString().lastIndexOf(",") ;
		return stbf.toString().substring(0, ep) ;
	}

	//#CM708961
	/**
	 * Generate ORDER BY Phrase of SQL from the set key. 
	 * @return ORDER BY Phrase
	 */
	public String[] getSortConditionTable()
	{
		Key[] karray = new Key[_searchKeyList.size()] ;

		_searchKeyList.copyInto(karray) ;
		for (int i = 0; i < karray.length; i++)
		{
			for (int j = i; j < karray.length; j++)
			{
				if (karray[i].getTableOrder() > karray[j].getTableOrder())
				{
					Key ktmp = karray[i] ;
					karray[i] = karray[j] ;
					karray[j] = ktmp ;
				}
			}
		}

		String stbf = "" ;
		Vector vec = new Vector();
		

		for (int i = 0; i < karray.length; i++)
		{
			if (karray[i].getTableOrder() > 0)
			{
				stbf = Integer.toString(karray[i].getTableOrder()) + "," + karray[i].getTableColumn() ;
				//#CM708962
				// The descending key word is set for the descending order. 
				if (karray[i].getTableDesc() == false)
				{
					stbf = stbf + " DESC" ;
				}
				vec.addElement(stbf);
			}
		}

		//#CM708963
		// Return the content of Vector. 
		String[] rStr = new String[vec.size()];
		vec.copyInto(rStr);
		return rStr ;
	}

	//#CM708964
	/**
	 * Generate GROUP BY Phrase of SQL from the set key. 
	 * @return GROUP BY Phrase
	 * @throws ReadWriteException
	 */
	public String getGroupCondition()
			throws ReadWriteException
	{
		Key[] karray = new Key[_searchKeyList.size()] ;

		_searchKeyList.copyInto(karray) ;
		for (int i = 0; i < karray.length; i++)
		{
			for (int j = i; j < karray.length; j++)
			{
				if (karray[i].getTableGroup() > karray[j].getTableGroup())
				{
					Key ktmp = karray[i] ;
					karray[i] = karray[j] ;
					karray[j] = ktmp ;
				}
			}
		}

		StringBuffer stbf = new StringBuffer(256) ;
		boolean existFlg = false ;

		for (int i = 0; i < karray.length; i++)
		{
			if (karray[i].getTableGroup() > 0)
			{
				stbf.append(karray[i].getTableColumn()) ;
				stbf.append(", ") ;
				if (existFlg == false)
					existFlg = true ;
			}
		}
		//#CM708965
		// Return null when data is not set in the key value. 
		if (existFlg == false)
			return null ;

		//#CM708966
		// It returns ReadWriteException when the acquisition item is not defined. 
		boolean colFlg = false ;
		for (int i = 0; i < _searchKeyList.size(); i++)
		{
			Key ky = (Key)_searchKeyList.get(i) ;
			if (ky.getTableCollect() != null)
			{
				colFlg = true ;
				break ;
			}
		}
		if (colFlg == false)
		{
			RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "SQLSearchKey", null) ;
			throw (new ReadWriteException("6006002" + wDelim + "SQLSearchKey")) ;
		}
		//#CM708967
		// The last ""is removed because it is extra. 
		int ep = stbf.toString().lastIndexOf(",") ;
		return stbf.toString().substring(0, ep) ;
	}

	//#CM708968
	/**
	 * Generate SELECT phrase of SQL from the set key. 
	 * @return SELECT phrase
	 */
	public String getCollectCondition()
	{
		StringBuffer stbf = new StringBuffer(256) ;
		boolean existFlg = false ;

		for (int i = 0; i < _searchKeyList.size(); i++)
		{
			Key ky = (Key)_searchKeyList.get(i) ;
			if (ky.getTableCollect() != null)
			{

				//#CM708969
				// Edit acquisition Condition + acquisition column when acquisition Condition is described clearly. 
				if (!ky.getTableCollect().trim().equals(""))
				{
					stbf.append(ky.getTableCollect()) ;
					stbf.append("(") ;
					stbf.append(ky.getTableColumn()) ;
					stbf.append(") ") ;
					int wPnt = ky.getTableColumn().indexOf(".") ;

					if (wPnt >= 0)
					{
						stbf.append(ky.getTableColumn().substring((wPnt + 1))) ;
					}
					else
					{
						stbf.append(ky.getTableColumn()) ;
					}
					stbf.append(", ") ;
				}
				else
				{
					stbf.append(ky.getTableColumn()) ;
					stbf.append(", ") ;
				}

				if (existFlg == false)
					existFlg = true ;
			}
		}
		//#CM708970
		// Return '*' as all item acquisition when data is not set in the key value. 
		if (existFlg == false)
			return null ;

		//#CM708971
		// The last ""is removed because it is extra. 
		int ep = stbf.toString().lastIndexOf(",") ;
		return stbf.toString().substring(0, ep) ;
	}

	//#CM708972
	/**
	 * Generate SELECT phrase of SQL from the set key. <BR>
	 * Use it by <code>count</code> method. <BR>
	 * Do not replace consolidating row name with the row name even if you use the aggregate function. 
	 * @return SELECT phrase
	 */
	public String getCollectConditionForCount()
	{
		for (int i = 0; i < _searchKeyList.size(); i++)
		{
			Key ky = (Key)_searchKeyList.get(i) ;
			if (ky.getTableGroup() > 0)
			{
				return ("COUNT(*)") ;
			}
		}
		//#CM708973
		// Return '*' as all item acquisition when data is not set in the key value. 
		return null ;

	}

	//#CM708974
	/**
	 * Decide Presence of acquisition Condition of the item. 
	 * @param Keyword
	 * @return Presence of acquisition Condition
	 */
	public boolean checkCollection(String Keyword)
	{

		//#CM708975
		// Refer to Key-Table by the item name. 
		Key ky = getKeyCut(Keyword) ;
		if (ky == null)
			return false ;

		if (ky.getTableCollect() == null)
			return false ;

		return true ;
	}

	//#CM708976
	// Package methods -----------------------------------------------

	//#CM708977
	// Protected methods ---------------------------------------------
	//#CM708978
	/**
	 * Set an effective column name. 
	 * @param columns
	 */
	protected void setColumns(String[] columns)
	{
		_searchKeyList = new Vector(columns.length) ;
		for (int i = 0; i < columns.length; i++)
		{
			//#CM708979
			// Key column set
			_searchKeyList.addElement(new Key(columns[i])) ;
		}
	}

	//#CM708980
	/**
	 * Clear the Vector area of Retrieval Condition and the The order of sorting beginning. 
	 */
	protected void clearConditions()
	{
		int i ;

		for (i = 0; i < _searchKeyList.size(); i++)
		{
			Key ky = (Key)_searchKeyList.get(i) ;
			if (ky.getTableValue() != null)
				break ;
			//#CM708981
			// Clear the order of sorting beginning
			ky.setTableOrder(0) ;
			ky.setTableDesc(true) ;
			setSortKey(ky) ;
			//#CM708982
			// Clear the order of group beginning
			ky.setTableGroup(0) ;
			setGroupKey(ky) ;
			//#CM708983
			// Clear Acquisition Condition
			ky.setTableCollect("NULL") ;
			setCollectKey(ky) ;
		}

		for (int j = i; j < _searchKeyList.size();)
		{
			_searchKeyList.removeElementAt(j) ;
		}
		
	}

	//#CM708984
	// Private methods -----------------------------------------------
	//#CM708985
	/**
	 * Return a corresponding Key instance to the specified key word (column). 
	 * @param keyword
	 * @return Key
	 */
	protected Key getKey(String keyword)
	{
		for (int i = 0; i < _searchKeyList.size(); i++)
		{
			Key ky = (Key)_searchKeyList.get(i) ;
			if (ky.getTableColumn().equals(keyword))
				return ky ;
		}
		return null ;
	}

	//#CM708986
	/**
	 * Return a corresponding Key instance to the specified key word (column): Check the table name cutting it. 
	 * @param keyword
	 * @return Key
	 */
	protected Key getKeyCut(String keyword)
	{
		for (int i = 0; i < _searchKeyList.size(); i++)
		{
			Key ky = (Key)_searchKeyList.get(i) ;
			int wPnt = ky.getTableColumn().indexOf(".") ;

			if (wPnt >= 0)
			{
				if (ky.getTableColumn().substring((wPnt + 1)).equals(keyword))
					return ky ;
			}
			else
			{
				if (ky.getTableColumn().equals(keyword))
					return ky ;
			}
		}
		return null ;
	}

	protected Key getKeyAdd(String keyword)
	{
		for (int i = 0; i < _searchKeyList.size(); i++)
		{
			Key ky = (Key)_searchKeyList.get(i) ;
			if (ky.getTableColumn().equals(keyword))
			{
				_searchKeyList.addElement(new Key(ky.getTableColumn())) ;
				int last_idx = _searchKeyList.size() - 1 ;
				Key key = (Key)_searchKeyList.get(last_idx) ;
				return key ;
			}
		}
		return null ;
	}

	//#CM708987
	/**
	 * Add it to the SearchKey instance by the specified key word (column). 
	 * @param key
	 */
	protected void setSearchKey(Key key)
	{
		int last_idx = _searchKeyList.size() - 1 ;
		_searchKeyList.set(last_idx, key) ;
	}

	//#CM708988
	/**
	 * Set sorting Condition by the specified key word (column). 
	 * @param key
	 */
	protected void setSortKey(Key key)
	{
		for (int i = 0; i < _searchKeyList.size(); i++)
		{
			Key ky = (Key)_searchKeyList.get(i) ;
			if (ky.getTableColumn().equals(key.getTableColumn()))
			{
				ky.setTableOrder(key.getTableOrder()) ;
				ky.setTableDesc(key.getTableDesc()) ;
				_searchKeyList.set(i, ky) ;
				break ;
			}
		}
	}

	//#CM708989
	/**
	 * Set group Condition by the specified key word (column). 
	 * @param key
	 */
	protected void setGroupKey(Key key)
	{
		for (int i = 0; i < _searchKeyList.size(); i++)
		{
			Key ky = (Key)_searchKeyList.get(i) ;
			if (ky.getTableColumn().equals(key.getTableColumn()))
			{
				ky.setTableGroup(key.getTableGroup()) ;
				_searchKeyList.set(i, ky) ;
				break ;
			}
		}
	}

	//#CM708990
	/**
	 * Set acquisition Condition by the specified key word (column). 
	 * @param key
	 */
	protected void setCollectKey(Key key)
	{
		for (int i = 0; i < _searchKeyList.size(); i++)
		{
			Key ky = (Key)_searchKeyList.get(i) ;
			if (ky.getTableColumn().equals(key.getTableColumn()))
			{
				if (key.getTableCollect().equals("NULL"))
				{
					ky.setTableCollect(null) ;
				}
				else
				{
					ky.setTableCollect(key.getTableCollect()) ;
				}
				_searchKeyList.set(i, ky) ;
				break ;
			}
		}
	}

	//#CM708991
	// Inner Class ---------------------------------------------------
	//#CM708992
	/**
	 * An internal class which maintains key column, Retrieval value, Retrieval Condition, Comparison Condition, The order of sorting, direction of sorting, The order of group, and extraction Condition. 
	 */
	protected class Key
	{

		private String Column ; // カラム

		private Object Value ; // 検索値（様々な型がセットされる可能性がある事を考慮） 

		private int Condtion ; // 検索条件

		private String CompCode ; // 比較条件

		private int Order ; // ソート順

		private boolean Desc ; // ソート方向 true:昇順 false:降順

		private int Group ; // グループ順

		private String CollectCond ; // 取得条件

		protected Key(String clm)
		{
			Column = clm ;
			Value = null ;
			Condtion = 0 ;
			CompCode = null ;
			Order = 0 ;
			Desc = true ;
			Group = 0 ;
			CollectCond = null ;
		}

		//#CM708993
		/**
		 * Set the column in the table. 
		 * @param column
		 */
		protected void setTableColumn(String column)
		{
			Column = column ;
		}

		//#CM708994
		/**
		 * Acquire Column of table. 
		 * @return Column of table
		 */
		protected String getTableColumn()
		{
			return Column ;
		}

		//#CM708995
		/**
		 * Set Retrieval value in the table. 
		 * @param val
		 */
		protected void setTableValue(Object val)
		{
			Value = val ;
		}

		//#CM708996
		/**
		 * Acquire Retrieval value corresponding to the column of the table. 
		 * @return Retrieval value
		 */
		protected Object getTableValue()
		{
			return Value ;
		}

		//#CM708997
		/**
		 * Set Retrieval Condition in the table. 
		 * @param pcond
		 */
		protected void setTableCondtion(int pcond)
		{
			Condtion = pcond ;
		}

		//#CM708998
		/**
		 * Acquire Retrieval Condition corresponding to the column of the table. 
		 * @return Retrieval Condition
		 */
		protected int getTableCondtion()
		{
			return Condtion ;
		}

		//#CM708999
		/**
		 * Set Comparison Condition in the table. 
		 * @param compcode
		 */
		protected void setTableCompCode(String compcode)
		{
			CompCode = compcode ;
		}

		//#CM709000
		/**
		 * Acquire Comparison Condition corresponding to the column of the table. 
		 * @return Comparison Condition
		 */
		protected String getTableCompCode()
		{
			return CompCode ;
		}

		//#CM709001
		/**
		 * Set The order of sorting in the table. 
		 * @param num
		 */
		protected void setTableOrder(int num)
		{
			Order = num ;
		}

		//#CM709002
		/**
		 * The order of sorting corresponding to the column of Table is acquired. 
		 * @return The order of sorting
		 */
		protected int getTableOrder()
		{
			return Order ;
		}

		//#CM709003
		/**
		 * Set Ascending order and descending order of sorting. 
		 * @param bool
		 */
		protected void setTableDesc(boolean bool)
		{
			Desc = bool ;
		}

		//#CM709004
		/**
		 * Acquire Ascending order and descending order of sorting. 
		 * @return Ascending order and descending order of sorting
		 */
		protected boolean getTableDesc()
		{
			return Desc ;
		}

		//#CM709005
		/**
		 * Set The order of group in the table. 
		 * @param num
		 */
		protected void setTableGroup(int num)
		{
			Group = num ;
		}

		//#CM709006
		/**
		 * Acquire The order of group corresponding to the column of the table. 
		 * @return The order of group
		 */
		protected int getTableGroup()
		{
			return Group ;
		}

		//#CM709007
		/**
		 * Set Comparison Condition in the table. 
		 * @param value
		 */
		protected void setTableCollect(String value)
		{
			CollectCond = value ;
		}

		//#CM709008
		/**
		 * Acquire Comparison Condition corresponding to the column of the table. 
		 * @return Comparison Condition
		 */
		protected String getTableCollect()
		{
			return CollectCond ;
		}
	}
}
//#CM709009
//end of class

