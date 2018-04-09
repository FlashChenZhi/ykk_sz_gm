package jp.co.daifuku.wms.base.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

//******************************************************************************
//#CM686552
/**
 * This class prints a CSV format file<BR>
 * <B>Usage :</B>
 * <BLOCKQUOTE><PRE>
 *
 * // generate instance
 * DfkCsvFileWriter csvFW = new DfkCsvFileWriter();
 *
 * // String array
 * Object today[] = new Object[3];
 * today [0] = "December";
 * today [1] = new Integer(24);
 * today [2] = new Integer(1999);
 *
 * try {
 *   csvFW.open("date.csv");  // open csv file
 *   csvFW.writeCsv(today);   // write record
 *   csvFW.close();           // close csv file
 * } catch (Exception ex) {
 *   System.exit(1);
 * }
 *
 * System.exit(0);
 * </BLOCKQUOTE></PRE>
 * <P>
 *   image of the date.csv output
 * <BLOCKQUOTE><PRE>
 * "December",24,1999
 * </BLOCKQUOTE></PRE>
 * <P>
 * @author  Taichi Higo
 * @version $Revision: 1.2 $ $Date: 2006/11/07 07:16:11 $
 */
/**
 * This class prints a CSV format file<BR>
 * <B>Usage :</B>
 * <BLOCKQUOTE><PRE>
 *
 * // generate instance
 * DfkCsvFileWriter csvFW = new DfkCsvFileWriter();
 *
 * // String array
 * Object today[] = new Object[3];
 * today [0] = "December";
 * today [1] = new Integer(24);
 * today [2] = new Integer(1999);
 *
 * try {
 *   csvFW.open("date.csv");  // open csv file
 *   csvFW.writeCsv(today);   // write record
 *   csvFW.close();           // close csv file
 * } catch (Exception ex) {
 *   System.exit(1);
 * }
 *
 * System.exit(0);
 * </BLOCKQUOTE></PRE>
 * <P>
 *   image of the date.csv output
 * <BLOCKQUOTE><PRE>
 * "December",24,1999
 * </BLOCKQUOTE></PRE>
 * <P>
 * @author  Taichi Higo
 * @version $Revision: 1.2 $ $Date: 2006/11/07 07:16:11 $
 */
//******************************************************************************
public class CsvFileWriter {

  //****************************************************************************
  //#CM686553
  //private field
  //****************************************************************************

  //#CM686554
  /** file path */

  private String filePath = null;

  //#CM686555
  /** BufferedWriter */

  private BufferedWriter outWriter;

  //#CM686556
  /** column separator */

  private char columnSeparator = ',';

  //#CM686557
  /** new line character string*/

  private String lineSeparator;

  //#CM686558
  /** character string separator */

  private char stringSeparator = '"';

  //****************************************************************************
  //#CM686559
  //constructor
  //****************************************************************************

  //****************************************************************************
  //#CM686560
  /**
   * Initialize
   */
  //****************************************************************************
  public CsvFileWriter() {
    lineSeparator = System.getProperty("line.separator");
  }

  //****************************************************************************
  //#CM686561
  //Access method
  //****************************************************************************

  //****************************************************************************
  //#CM686562
  /**
   * fetch the separator used in the csv file
   * <P>
   * @return separator
   */
  //****************************************************************************
  public char getColumnSeparator () {
    return columnSeparator;
  }

  //****************************************************************************
  //#CM686563
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
  //#CM686564
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
  //#CM686565
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
  //#CM686566
  //public method
  //****************************************************************************

  //****************************************************************************
  //#CM686567
  /**
   * open the csv out file in the specified path
   * <P>
   * @param argFilePath                 csv output file path name
   * @exception FileNotFoundException   csv output file path name
   */
  //****************************************************************************
  public void open(String argFilePath) throws FileNotFoundException {
    open(argFilePath, false, System.getProperty("file.encoding"));
  }

  //****************************************************************************
  //#CM686568
  /**
   * open the csv out file in the specified path
   * <P>
   * @param argFilePath                 csv output file path name
   * @param     argAppend output mode<BR>
   *            <DL>
   *            <DD>true: write to the end of the file
   *            <DD>false: write from the beginning of the file
   *            </DL>
   * @exception FileNotFoundException   if file open error occurs
   */
  //****************************************************************************
  public void open(String argFilePath, boolean argAppend) throws FileNotFoundException {
    open(argFilePath, argAppend, System.getProperty("file.encoding"));
  }

  //****************************************************************************
  //#CM686569
  /**
   * open the csv out file in the specified path
   * <P>
   * @param argFilePath                 csv output file path name
   * @param     argAppend output mode<BR>
   *            <DL>
   *            <DD>true: write to the end of the file
   *            <DD>false: write from the beginning of the file
   *            </DL>
   * @param argEncoding name of the encoding to use
   * @exception FileNotFoundException   if file open error occurs
   */
  //****************************************************************************
  public void open(String argFilePath, boolean argAppend, String argEncoding) throws FileNotFoundException {
    //#CM686570
    // save file name
    filePath = argFilePath;

    //#CM686571
    // open csv file in write mode
    FileOutputStream stream = new FileOutputStream(argFilePath, argAppend);

    try{
      OutputStreamWriter writer = new OutputStreamWriter(stream, argEncoding);
      outWriter = new BufferedWriter(writer);
    }catch(UnsupportedEncodingException e){
      //#CM686572
      // use the default encoding
      OutputStreamWriter writer = new OutputStreamWriter(stream);
      outWriter = new BufferedWriter(writer);
    }
  }

  //****************************************************************************
  //#CM686573
  /**
   * output one line content in the csv format
   * <P>
   * @param argColumns  array object to write
   * @exception         IOException if file I/O exception occurs
   */
  //****************************************************************************
  public void writeCsv(Object argColumns[]) throws IOException {
    String strColumn;
    StringBuffer strLine = new StringBuffer();

    for (int iColumns = 0; iColumns < argColumns.length; iColumns ++) {
      if (argColumns[iColumns] instanceof String) {
        //#CM686574
        // In case of character string, I convert "into" "and surround the whole with" and ".
        strColumn = formatString((String)argColumns[iColumns]);
      } else {
        strColumn = argColumns[iColumns].toString();
      }

      //#CM686575
      // if not the first column
      if (iColumns > 0) strLine.append(columnSeparator);
      strLine.append(strColumn);
    }
    outWriter.write(strLine.toString());
    outWriter.write(lineSeparator);
  }

  //****************************************************************************
  //#CM686576
  /**
   * flush the opened file
   * <P>
   * @exception         IOException if file I/O exception occurs
   */
  //****************************************************************************
  public void flush() throws IOException {
    outWriter.flush();
  }

  //****************************************************************************
  //#CM686577
  /**
   * close the file that is already open<BR>
   * delete the csv file if the size is 0
   * <P>
   * @exception         IOException if file I/O exception occurs
   */
  //****************************************************************************
  public void close() throws IOException {
    close(true);
  }

  //****************************************************************************
  //#CM686578
  /**
   * close the file that is already open<BR>
   * if the argument argDeleteOnZero is true and if the CSV file size is
   * 0, delete it
   * <P>
   * @param argDeleteOnZero <DL><DD>true:  delete the file if the size is 0
   *                            <DD>false: don't check the file size</DL>
   * @exception         IOException if file I/O exception occurs
   */
  //****************************************************************************
  public void close(boolean argDeleteOnZero) throws IOException {
    if (outWriter != null) {
      outWriter.close();
      outWriter = null;
      if (argDeleteOnZero) fileDelete(true);
    }
  }

  //****************************************************************************
  //#CM686579
  /**
   * delete the csv file that is created
   * <P>
   * @exception         IOException if file I/O exception occurs
   */
  //****************************************************************************
  public void delete() throws IOException {
    //#CM686580
    // close the file
    close(false);

    //#CM686581
    // delete the csv file without concerns about the size
    fileDelete(false);
  }

  //****************************************************************************
  //#CM686582
  //private method
  //****************************************************************************

  //****************************************************************************
  //#CM686583
  /**
   * convert " to "" in the specified string and cover the entire range with ""
   * <P>
   * @param   argString     character string to be modified
   * @return                character string with double quote
   */
  //****************************************************************************
  private String formatString(String argString) {
    int          end;
    int          start = 0;
    StringBuffer buf;

    //#CM686584
    // search for " position in the string
    end = argString.indexOf(stringSeparator);

    //#CM686585
    // if " does not exist, surround the whole string with "" and return it
    if (end == -1) return new String(stringSeparator + argString + stringSeparator);

    //#CM686586
    // if " exist, repeat the process of converting " to "" until the end
    buf = new StringBuffer();
    while (true) {
      if (end == -1) {
        buf.append(argString.substring(start));
        break;
      }
      buf.append(argString.substring(start, end));
      buf.append(stringSeparator);
      buf.append(stringSeparator);
      start = end + 1;
      if (start >= argString.length()) break;
      end = argString.indexOf(stringSeparator, start);
    }

    //#CM686587
    // surround the entire string with "" (double quotes) and return
    return new String(stringSeparator + buf.toString() + stringSeparator);
  }

  //****************************************************************************
  //#CM686588
  /**
   * delete the csv file
   * <P>
   * @param   argCheckSize <DL><DD>true: delete the file if the size is 0
   *                           <DD>false: don't check the file size</DL>
   */
  //****************************************************************************
  private void fileDelete(boolean argCheckSize) {
    File  targetFile;         // 削除対象のファイル

    //#CM686589
    // don't process if the file name is not specified
    if (filePath == null) return;

    //#CM686590
    // fetch the csv file
    targetFile = new File(filePath);

    //#CM686591
    // skip processing if file does not exist
    if (!targetFile.exists()) return;

    if (argCheckSize) {
      //#CM686592
      // if the file size is greater than 0, don't delete
      if (targetFile.length() > 0) return;
    }
    targetFile.delete();
  }
}
