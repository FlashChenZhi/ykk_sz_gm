package jp.co.daifuku.wms.base.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;

//******************************************************************************
//#CM686501
/**
 * This class inputs CSV format file<BR>
 * <B>Usage :</B>
 * <BLOCKQUOTE><PRE>
 *
 * //Instance generation
 * CsvFileReader csvFR = new CsvFileReader();
 *
 * //String array
 * String[] today;
 *
 * try {
 *   csvFR.open("C:\" , "date.csv");  //Read CSV file
 *   today = csvFR.readCsv();   //Read a line record
 * } catch (Exception ex) {
 *   System.exit(1);
 * }
 *
 * System.exit(0);
 * </BLOCKQUOTE></PRE>
 *
 * @author  $$
 * @version $Revision: 1.3 $ $Date: 2006/11/10 00:30:16 $
 */
/**
 * This class inputs CSV format file<BR>
 * <B>Usage :</B>
 * <BLOCKQUOTE><PRE>
 *
 * //Instance generation
 * CsvFileReader csvFR = new CsvFileReader();
 *
 * //String array
 * String[] today;
 *
 * try {
 *   csvFR.open("C:\" , "date.csv");  //Read CSV file
 *   today = csvFR.readCsv();   //Read a line record
 * } catch (Exception ex) {
 *   System.exit(1);
 * }
 *
 * System.exit(0);
 * </BLOCKQUOTE></PRE>
 *
 * @author  $$
 * @version $Revision: 1.3 $ $Date: 2006/11/10 00:30:16 $
 */
//******************************************************************************
public class CsvFileReader extends DataLoader {

  //****************************************************************************
  //#CM686502
  //private field

  //****************************************************************************

  //#CM686503
  /** column separator */

  private char columnSeparator = ',';

  //#CM686504
  /** new line character string*/

  private String lineSeparator;

  //#CM686505
  /** character string separator */

  private char stringSeparator = '"';
  
  
  
  //#CM686506
  //#CM686507
  /* 

  //constructor

  /**
   * Initialize
   */
  //****************************************************************************
  public CsvFileReader() {
    lineSeparator = System.getProperty("line.separator");
  }

  //****************************************************************************
  //#CM686508
  //Access method
  //****************************************************************************

  //****************************************************************************
  //#CM686509
  /**
   * Fetch the separator to use in CSV output
   * <P>
   * @return separator
   */
  //****************************************************************************
  public char getColumnSeparator () {
    return columnSeparator;
  }

  //****************************************************************************
  //#CM686510
  /**
   * Set the separator to use in CSV output
   * <P>
   * @param argColumnSeparator separator
   */
  //****************************************************************************
  public void setColumnSeparator (char argColumnSeparator) {
    columnSeparator = argColumnSeparator;
  }

  //****************************************************************************
  //#CM686511
  /**
   * Fetch the separator to use in CSV output
   * <P>
   * @return separator
   */
  //****************************************************************************
  public char getStringSeparator () {
    return stringSeparator;
  }

  //****************************************************************************
  //#CM686512
  /**
   * Set the separator to use in CSV output
   * <P>
   * @param argStringSeparator separator
   */
  //****************************************************************************
  public void setStringSeparator (char argStringSeparator) {
    stringSeparator = argStringSeparator;
  }

  //****************************************************************************
  //#CM686513
  //public method
  //****************************************************************************


  //****************************************************************************
  //#CM686514
  /**
   * Read a line from the file in CSV format
   * <P>
   * @return String[]   single line of CSV file as string array (Null if EOF)
   * @throws FileNotFoundException if the specified file can't be found
   * @throws IOException if the file input/output fails
   * @throws InvalidDefineException if the specified value is abnormal (empty, illegal characters)
   */
  //****************************************************************************
  public String[] readCsv() throws FileNotFoundException , IOException, InvalidDefineException
  {	
	if (readData == null)
	{
		//#CM686515
		// read the first file
		if (dataFileIndex < dataFiles.length)
		{
			readDataFile();
		}
		else
		{
			return null;
		}
	}
	
	if (lineNo == readData.length)
	{
		if (dataFileIndex == dataFiles.length)
		{
			//#CM686516
			// return null if the last file is read until the last line
			return null;
		}
		else
		{
			//#CM686517
			// if the file is read completely, go to next file
			readDataFile();
		}
	}
	if ( readData == null ) return null;
	
	//#CM686518
	// return null if the last file is read until the last line
	if (lineNo == readData.length && dataFileIndex == dataFiles.length ) return null;
	
	//#CM686519
	// if the file is read completely, go to next file
	if (lineNo == readData.length) readDataFile();

	//#CM686520
	// "modify, where " at the end of an item, gives loading error
	return getSeparatedItemCSV(readData[lineNo++]);

  }
  
  //#CM686521
  /**
   * Divide the String character passed in the argument with a pre-determined separator and return the same<BR>
   * The empty spaces at the end of the string are removed using DisplayText.trim method<BR>
   * Delete " character. If " exists in between string characters, escape it using ""
   * 
   * example<BR>
   * "AAA","BB,B","C""CC" => ret[0] = AAA, ret[1] = BB.B, ret[2] = C"CC<BR>
   * @param arg character string to divide
   * @return string array
   * @throws InvalidDefineException if the specified value is abnormal (empty, illegal characters)
   */
  public String[] getSeparatedItemCSV(String arg) throws InvalidDefineException
  {
	  int delimIndex = 0;
	  int beginIndex = 0;
	    
	  //#CM686522
	  //","
	  String delimiter = new Character(columnSeparator).toString(); 
	  Vector vec = new Vector();
	  String compBuf="";
	  //#CM686523
	  //1 : look for ","
	  //#CM686524
	  //2 : check whether "," is a separator or not
	  //#CM686525
	  //if even number exist before ",", it is a separator
	  //#CM686526
	  //if odd number exist before ",", it is not a separator
	  while(delimIndex <= arg.length())
	  {
		  //#CM686527
		  // specify position of the end separator
		  delimIndex = arg.indexOf(delimiter, delimIndex);
		  //#CM686528
		  // if separator exists
		  if(delimIndex > 0)
		  {
			  //#CM686529
			  // fetch string from beginIndex until the separator
			  String subBuf = arg.substring(beginIndex, delimIndex);
				
			  if(isDelimiter(subBuf, "\""))
			  {
				  compBuf = replace(subBuf);
				  //#CM686530
				  // data error in case of null
				  if(compBuf == null)
				  {
					  throw new InvalidDefineException();
				  }
				  else
				  {
					  vec.add(compBuf.trim());
				  }
				  delimIndex++;
				  beginIndex = delimIndex;
			  }
			  else
			  {
				  delimIndex++;
			  }
		  }
		  //#CM686531
		  //if there is no separator
		  else
		  {
			  //#CM686532
			  // fetch from last separated string until the end
			  compBuf = replace(arg.substring(beginIndex));
			  //#CM686533
			  // data error in case of null
			  if(compBuf == null){
				throw new InvalidDefineException();			
			  }
			  else
			  {
				  vec.add(compBuf.trim());
			  }

			  break;
		  }
	  }

	  String[] array = new String[vec.size()];
	  vec.copyInto(array);
	  return array;
		
		
  }
  //****************************************************************************
  //#CM686534
  //private method
  //****************************************************************************
  //#CM686535
  /**
   * Decide whether "," is a separator or not<BR>
   * If an even delimiter exist before "," then it is a separator<BR>
   * If an odd delimiter exist before "," then it is not a separator<BR>
   * @param buf  string to identify
   * @param delimiter  "(double quotation mark)
   * @return true if it is a separator
   */
  private boolean isDelimiter(String buf, String delimiter)
  {
	  int delimCount = 0;
	  for(int i = 0; i < buf.length(); i++)
	  {
		  String comp = String.valueOf(buf.charAt(i));
		  if( comp.equals(delimiter))
		  {
			  delimCount++;
		  }
	  }

	  if(delimCount%2 == 0)
	  {
		  return true;
	  }
	  return false;
  }
  //#CM686536
  /**
   * double quotation mark delete process<BR>
   * delete any " from string<BR>
   * in case of "", delete one "<BR>
   * If " exists between at the beginning or end of a string, delete it. If " exists
   * as an odd number, throw data error and return null <BR>
   * eg. "AAA" -> AAA, "AAA""BBB" -> AAA"BBB
   * @param arg  string to identify
   * @return the modified string. return null in case of data error
   */
  private String replace(String arg)
  {
	  String delimiter = "\"";
	  StringBuffer strbuf = new StringBuffer();
	  arg = arg.trim();

	  //#CM686537
	  // count the string characters from the beginning
	  int frontDelimCount = countSequentialDelim(arg, delimiter) % 2;
	  //#CM686538
	  // count the string characters from the end
	  int lastDelimCount = countLastOfSequentialDelim(arg, delimiter) % 2;
	  //#CM686539
	  // If both are odd, delete "
	  if (frontDelimCount == 1 && lastDelimCount == 1)
	  {
		  //#CM686540
		  // delete " if it exist either in the beginning or end of a string
		  if(arg.indexOf(delimiter)==0 && arg.lastIndexOf(delimiter)==arg.length()-1)
		  {
			  arg = arg.substring(1,arg.length()-1);
		  }
		  
	  }
	  
	  //#CM686541
	  // If the " count is odd, return null since data is abnormal
	  int delimcount = countDelim(arg, delimiter);
	  if( delimcount != 0 && delimcount%2 != 0)
	  {
		  return null;
	  }
	  //#CM686542
	  // check all the characters one by one and substitute for strbuf
	  //#CM686543
	  // if delimiter is found,
	  //#CM686544
	  // decide whether to add or not to the strbuf
	  for(int i = 0; i < arg.length(); i++)
	  {
		  String comp = String.valueOf(arg.charAt(i));
		  //#CM686545
		  // if delimiter is found,
		  if( comp.equals(delimiter))
		  {
			  String buf = arg.substring(i);
			  int count = countSequentialDelim(buf, delimiter);

			  if(count == 1)
			  {
			      //#CM686546
			      // if the count is 1, return null since it is data error
				  strbuf.append(delimiter);
				  return null;
			  }
			  else
			  {
				  //#CM686547
				  // remaining delimiter = delimiter count / 2
				  for(int k =0; k < count/2; k++ )
				  {
					  strbuf.append(delimiter);
				  }
				  i = i + (count -1);
			  }
		  }
		  else
		  {
			  strbuf.append(comp);
		  }
	  }
	  return strbuf.toString();
  }

   //#CM686548
   /**
    * return the count of character string separator in succession from the beginning<BR>
    * return 0 if there is no continuation
    * @param arg  string to identify
    * @param delimiter  separator
    * @return count of the separator in succession
    */
	private int countSequentialDelim(String arg, String delimiter)
	{
		int count = 0;
		for(int i = 0; i < arg.length(); i++)
		{
			String comp = String.valueOf(arg.charAt(i));
			if(comp.equals(delimiter))
			{
				count++;
			}
			else
			{
				break;
			}
		}
		return count;
	}

	//#CM686549
	/**
	 * return the count of character string separator in succession from the end<BR>
	 * return 0 if there is no continuation
	 * @param arg  string to identify
	 * @param delimiter  separator
	 * @return count of the separator in succession
	 */
	private int countLastOfSequentialDelim(String arg, String delimiter)
	{
		int count = 0;
		for(int i = (arg.length() - 1); i >= 0; i--)
		{
			String comp = String.valueOf(arg.charAt(i));
			if(comp.equals(delimiter))
			{
				count++;
			}
			else
			{
				break;
			}
		}
		return count;
	}

	//#CM686550
	/**
	 * return the count of separator<BR>
	 * return 0 if there is no separator
	 * @param arg  string to identify
	 * @param delimiter  separator
	 * @return separator count
	 */
	private static int countDelim(String arg, String delimiter)
	{
		int count = 0;
		for(int i = 0; i < arg.length(); i++)
		{
			String comp = String.valueOf(arg.charAt(i));
			if(comp.equals(delimiter))
			{
				count++;
			}
		}
		return count;
	}	
}
//#CM686551
//end of class
