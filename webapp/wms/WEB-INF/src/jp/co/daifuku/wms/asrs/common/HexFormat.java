// $Id: HexFormat.java,v 1.2 2006/10/24 06:03:38 suresh Exp $
package jp.co.daifuku.wms.asrs.common ;

//#CM28640
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

//#CM28641
/**
 * Formatting the numeric value into the String of Hex notation
 * No support is provided for conversion from String to numeric value at moment.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/24 06:03:38 $
 * @author	$Author: suresh $
 */
public class HexFormat extends Format
{
	//#CM28642
	// Class field ------------------------------------------------------------
	//#CM28643
	/**
	 * Format pattern ( 0 or Hex value)
	 */
	public static final char FMT_FILLZERO = '0' ;
	//#CM28644
	/**
	 * FOrmat pattern (Suppress any value preceding Hex value) 
	 */
	public static final char FMT_ZEROSUPP = '#' ;

	//#CM28645
	// Class variables --------------------------------------------------------
	private String wFormatPattern ;

	//#CM28646
	// Class methods --------------------------------------------------------
	//#CM28647
	/**
	 * Returning version of this class
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/24 06:03:38 $") ;
	}
	//#CM28648
	// Constructor ------------------------------------------------------------
	//#CM28649
	/**
	 * Generating format instance by specifying pattern
	 * @param pattern Format pattern
	 */
	public HexFormat (String pattern)
	{
		wFormatPattern = pattern ;
	}

	//#CM28650
	// Public method ----------------------------------------------------------
	//#CM28651
	/**
	 * Formatting numeric value of <code>int</code> into Hex String
	 * Alphanumeric String is in lower-case letters; if requiring capital letters,
	 * use <code>String.toUpper()</code>.
	 * @param number  Numeric value to format
	 * @return        Hex String
	 */
	public String format(int number)
	{
		Integer nber = new Integer(number) ;
		return (format(nber, new StringBuffer(), new FieldPosition(0)).toString()) ;
	}
	
	//#CM28652
	/**
	 * Formatting numeric value of <code>long</code> into Hex String
	 * Alphanumeric String is in lower-case letters; if requiring capital letters,
	 * use <code>String.toUpperCase()</code>.
	 * @param number  Numeric value to format
	 * @return        Hex String
	 * @see java.lang.String
	 */
	public String format(long number)
	{
		Long nber = new Long(number) ;
		return (format(nber, new StringBuffer(), new FieldPosition(0)).toString()) ;
	}
	//#CM28653
	/**
	 * Formats an object to produce a string.
	 * Subclasses will implement for particular object, such as:
	 * <pre>
	 * StringBuffer format (Number obj, StringBuffer toAppendTo)
	 * Number parse (String str)
	 * </pre>
	 * These general routines allow polymorphic parsing and
	 * formatting for objects such as the MessageFormat.
	 * @param obj	 The object to format
	 * @param toAppendTo	where the text is to be appended
	 * @param pos	 On input: an alignment field, if desired.
	 * On output: the offsets of the alignment field.
	 * @return		 the value passed in as toAppendTo (this allows chaining,
	 * as with StringBuffer.append())
	 * @exception IllegalArgumentException when the Format cannot format the
	 * given object.
	 * @see  java.text.Format
	 * @see  java.text.FieldPosition
	 */
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)
	{
		Number nmbr = null ;
		StringBuffer rStr ;
		int spos ;

		if (obj instanceof Number)
		{
			nmbr = (Number)obj ;
			String tstring = Long.toHexString(nmbr.longValue()) ;

			if (wFormatPattern.length() < tstring.length())
			{
				//#CM28654
				// If result length longer than pattern
				rStr = new StringBuffer(tstring) ;
				spos = tstring.length() - wFormatPattern.length() ;

				String finalresult = rStr.substring(spos) ;
				toAppendTo.append(finalresult) ;

				return (new StringBuffer(finalresult)) ;
			}
			else
			{
				//#CM28655
				// If result length less than pattern
				rStr = new StringBuffer(wFormatPattern) ;
				spos = wFormatPattern.length() - tstring.length() ;
				rStr.replace(spos, wFormatPattern.length() , tstring) ;

				String wk = (rStr.toString()).replace(FMT_ZEROSUPP, ' ').trim() ;

				toAppendTo.append(wk) ;
				return (new StringBuffer(wk)) ;
			}
		}
		return(null) ;
	}
	
	//#CM28656
	/**
	 * WARNING !!!  NOT IMPLEMENTED NOW !!!
	 *
	 * Parses a string to produce an object.
	 * Subclasses will typically implement for particular object, such as:
	 * <pre>
	 *		 String format (Number obj);
	 *		 String format (long obj);
	 *		 String format (double obj);
	 *		 Number parse (String str);
	 * </pre>
	 * @param status Input-Output parameter.
	 * <p>Before calling, set status.index to the offset you want to start
	 * parsing at in the source.
	 * After calling, status.index is the end of the text you parsed.
	 * If error occurs, index is unchanged.
	 * <p>When parsing, leading whitespace is discarded
	 * (with successful parse),
	 * while trailing whitespace is left as is.
	 * <p>Example:
	 * Parsing "_12_xy" (where _ represents a space) for a number,
	 * with index == 0 will result in
	 * the number 12, with status.index updated to 3
	 * (just before the second space).
	 * Parsing a second time will result in a ParseException
	 * since "xy" is not a number, and leave index at 3.
	 * <p>Subclasses will typically supply specific parse methods that
	 * return different types of values. Since methods can't overload on
	 * return types, these will typically be named "parse", while this
	 * polymorphic method will always be called parseObject.
	 * Any parse method that does not take a status should
	 * throw ParseException when no text in the required format is at
	 * the start position.
	 * @return Object parsed from string. In case of error, returns null.
	 * @see java.text.ParsePosition
	 */
	public Object parseObject (String source, ParsePosition status)
	{
		return(new String("no")) ;
	}

}
