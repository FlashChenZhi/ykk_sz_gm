// $Id: IdDataFile.java,v 1.2 2006/11/14 06:09:07 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701635
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.WorkingDataHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingDataSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingData;

//#CM701636
/**
 * Super class of Data file sent and received by RFT communication. 
 * Use the subclass of each ID to operate an actual file. 
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:07 $
 * @author  $Author: suresh $
 */
public abstract class IdDataFile extends Object implements DataColumn
{

	//#CM701637
	// Class fields --------------------------------------------------
	//#CM701638
	/**
	 * Encode definition
	 */
	protected static final String ENCODE = "Shift_JIS";

	//#CM701639
	/**
	 * Offset of Row No
	 */
	protected static final int OFF_LINE_NO = 0;

	//#CM701640
	/**
	 * Regular expression
	 */
	protected static final String DATAFILE_REGEX = "ID\\d\\d\\d\\d.txt";
	
	
	//#CM701641
	/**
	 * Format definition of date
	 */
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	
	//#CM701642
	// Class variables -----------------------------------------------
	//#CM701643
	/**
	 * Path name of Data file
	 */
	protected String wFileName = null;
	
	//#CM701644
	/**
	 * Path name of History Data file
	 */
	protected String historyDataFileName = null;

	//#CM701645
	/**
	 * Stream for Data file reading
	 */
	protected BufferedReader reader = null;
	
	//#CM701646
	/**
	 * Stream for Data file writing
	 */
	protected PrintWriter writer = null;

	//#CM701647
	/**
	 * Byte row of present line
	 */
	protected byte[] currentLine = null;

	//#CM701648
	/**
	 * Length of 1 line of Data file
	 *  (Used only at the time of writing. ) 
	 */
	protected int lineLength = 0;
	
	//#CM701649
	// Class method --------------------------------------------------
	//#CM701650
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/14 06:09:07 $");
	}

	//#CM701651
	/**
	 * Acquire ID where Working data preservation information is retrieved, and Working data exists. 
	 * 
	 * @param rftNo		RFT machine No
	 * @param conn 		Connection object with data base
	 * @return			ID under work
	 * @throws ReadWriteException It is notified when failing in the access of DB. 
	 */
	public static String getWorkingDataId(String rftNo, Connection conn) throws ReadWriteException
	{
		WorkingDataSearchKey skey = new WorkingDataSearchKey();
		WorkingDataHandler handler = new WorkingDataHandler(conn);
		//#CM701652
		// Retrieve Working data Work information. 
		skey.setRftNo(rftNo);
		skey.setFileNameCollect();
		
		WorkingData[] workingdata =(WorkingData[]) handler.find(skey);
		//#CM701653
		// Return null when Working data is not found. 
		if (workingdata == null || workingdata.length == 0)
		{
			return null;
		}

		//#CM701654
		// Acquire ID Number from the name of the Working data file. 
		Matcher m = Pattern.compile("ID(\\d\\d\\d\\d).txt").matcher(workingdata[0].getFileName());
		if (m.find())
		{
			return  m.group(1);
		}

		return null;
	}
	
	//#CM701655
	// Constructors --------------------------------------------------
	//#CM701656
	/**
	 * Generate the instance. 
	 */
	public IdDataFile()
	{
		super();
	}

	//#CM701657
	/**
	 * Generate the instance. 
	 * @param	len		Length of 1 line
	 */
	public IdDataFile(int length)
	{
	    lineLength = length;
	}

	//#CM701658
	/**
	 * Generate the instance. 
	 * 
	 * @param	filename	DataFile Name (Path is unnecessary. ) 
	 */
	public IdDataFile(String filename)
	{
		setFileName(filename);
	}

	//#CM701659
	// Public methods ------------------------------------------------
	//#CM701660
	/**
	 * Set it after converting specified File Name into the path name. 
	 * 
	 * @param	filename	DataFile Name (Path is unnecessary. ) 
	 */
	public void setFileName(String filename)
	{
		//#CM701661
		// Acquire the name of the folder where the file is. 
		String datapath = WmsParam.DAIDATA;		// c:/daifuku/data/
		//#CM701662
		// Generate the path name of the reception file. 
		wFileName = datapath + filename;
		//#CM701663
		// Make the directory when the file or the directory specified by the path name does not exist. 
		File fileObject = new File(wFileName);
		if (! fileObject.exists())
		{
			File dirObject = new File(fileObject.getParent());
			if (! dirObject.isDirectory())
			{
				try
				{
					//#CM701664
					// Make the directory. (Perhaps, must be the directory of "Send". ) 
					dirObject.mkdirs();
					
					File dirObject2 = new File(dirObject.getParent());
					String sendStr = dirObject2.getName();
					File dirObject3 = new File(WmsParam.RFTRECV);
					String recvStr = dirObject3.getName();
					//#CM701665
					// Replace "Send" with "Recv", and make the directory. 
					String recvDirName = dirObject.toString().replaceAll(sendStr,recvStr);
					File recvDirObj = new File(recvDirName);
					recvDirObj.mkdirs();
				}
				catch(SecurityException e)
				{
				}
			}
		}
	}

	//#CM701666
	/**
	 * Open the file in the mode only for reading. 
	 * 
	 * @throws FileNotFountException	The file does not exist. 
	 */
	public void openReadOnly() throws FileNotFoundException
	{
		//#CM701667
		// File reading stream generation
		reader = new BufferedReader(new FileReader(wFileName));
	}
	
	//#CM701668
	/**
	 * Open the file in the mode only for reading. 
	 * 
	 * @param filename File Name
	 * @throws FileNotFountException	The file does not exist. 
	 */
	public void openReadOnly(String filename) throws FileNotFoundException
	{
		//#CM701669
		// File reading stream generation
		reader = new BufferedReader(new FileReader(filename));
	}

	//#CM701670
	/**
	 * Close the stream only for reading. 
	 */
	public void closeReadOnly()
	{
		try
		{
			reader.close();
		}
		catch (IOException e)
		{
			//#CM701671
			// 6006020 = File I/O error occurred. {0}
			RftLogMessage.print(6006020, "IdDataFile", wFileName);
		}
	}

	//#CM701672
	/**
	 * Open the file in the writing mode. 
	 * 
	 * @throws IOException	When failing in the opening of the file
	 */
	public void openWritable() throws IOException
	{
		//#CM701673
		// File writing stream generation
		writer = new PrintWriter(new BufferedWriter(new FileWriter(wFileName)));
		initializeBuffer();
	}

	//#CM701674
	/**
	 * Open the file in the writing mode. 
	 * 
	 * @param filename File Name
	 * @throws IOException	When failing in the opening of the file
	 */
	public void openWritable(String filename) throws IOException
	{
		//#CM701675
		// File reading stream generation
		writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		initializeBuffer();
	}

	//#CM701676
	/**
	 * Close the stream for writing. 
	 */
	public void closeWritable()
	{
		writer.close();
	}

	//#CM701677
	/**
	 * Read the following line from the file, and set it in an internal buffer. <BR>
	 * Set < CODE>null</CODE > when the following line does not exist or Error is generated.
	 * @throws IOException		I/O Error generation
	 */
	public void next() throws IOException
	{
		String buffer = reader.readLine();
		if (buffer == null)
		{
			currentLine = null;
		}
		else
		{
			//#CM701678
			// It converts it into the byte type. 
			currentLine = buffer.getBytes();
		}
	}

	//#CM701679
	/**
	 * Read the following line from the file, and return it by the Byte row. <BR>
	 * Return < CODE>null</CODE > when the following line does not exist or Error is generated.
	 * 
	 * @return		Byte array of the following line of file
	 * @throws IOException		I/O Error generation
	 */
	public byte[] getNext() throws IOException
	{
		next();
		return currentLine;
	}

	//#CM701680
	/**
	 * Write it in the file adding changing line Code at the end of Content in an internal buffer. 
	 */
	public void writeln()
	{
		writer.println(new String(currentLine));
		initializeBuffer();
	}


	//#CM701681
	/**
	 * Initializes an internal buffer. 
	 */
	public void initializeBuffer()
	{
		currentLine = new byte[lineLength];
		for (int i = 0; i < currentLine.length; i ++)
		{
		    currentLine[i] = ' ';
		}
	}

	//#CM701682
	/**
	 * Read Data from the file, put in Entity array of an appropriate type, and return it. <BR>
	 * The type of an actual array is different according to ID. 
	 * @return		Data read from file(Entity array)
	 * @throws IOException		I/O Error generation
	 * @throws InvalidStatusException Notify when there is no adjustment in a set value of Data. 
	 */
	public abstract Entity[] getCompletionData() throws IOException, InvalidStatusException;

	//#CM701683
	/**
	 * The file writes Data of Entity array specified by the argument. 
	 * The type of an actual array is different according to ID. 
	 * @param	obj		Entity array which maintains writing Content
	 * @throws IOException		I/O Error generation
	 * @throws InvalidStatusException Notify when there is no adjustment in a set value of Data. 
	 */
	public abstract void write(Entity[] obj) throws IOException, InvalidStatusException;


	//#CM701684
	// Package methods -----------------------------------------------

	//#CM701685
	// Protected methods ---------------------------------------------
	//#CM701686
	/**
	 * Cut out Length specified from the specified offset from the line now, convert 
	 * into Character string, and return it. 
	 * 
	 * @param	offset		Offset of Data
	 * @param	length		Length of Data
	 * @return				The one that Data cut out was converted into Character string
	 */
	protected String getColumn(int offset, int length)
	{
		String data = new String(currentLine, offset, length);
		return data.trim();
	}
	
	//#CM701687
	/**
	 * Cut out Length specified from the specified offset from the line now, convert 
	 * into Character string, and return it. Do not remove the blank. <BR>
	 * Use it when you register DB of Working data. <BR>
	 * 
	 * @param	offset		Offset of Data
	 * @param	length		Length of Data
	 * @return				The one that Data cut out was converted into Character string
	 */
	protected String getRawColumn(int offset, int length)
	{
		String data = new String(currentLine, offset, length);
		return data;
	}


	//#CM701688
	/**
	 * Cut out Length specified from the specified offset from the line now, convert 
	 * into Integer, and return it. 
	 * 
	 * @param	offset		Offset of Data
	 * @param	length		Length of Data
	 * @return				The one that Data cut out was converted into Numeric value
	 */
	protected int getIntColumn(int offset, int length)
	{
		String data = new String(currentLine, offset, length);
		return Integer.parseInt(data.trim());
	}

	//#CM701689
	/**
	 * Set Data of Length specified for the offset for which the line (Byte row) is specified now. <BR>
	 * Specify set Data with Character string. 
	 * 
	 * @param	data		Character string Data to be Set
	 * @param	offset		Offset of Data
	 * @param	length		Length of Data
	 */
	protected void setColumn(String data, int offset, int length)
	{
		byte[] byteData = data.getBytes();
		byteData = Idutils.createByteDataLeft(byteData, length);
		for (int i = 0; i < length; i ++)
		{
			currentLine[i + offset] = byteData[i];
		}
		return;
	}

	//#CM701690
	/**
	 * Set Data of Length specified for the offset for which the line (Byte row) is specified now. <BR>
	 * Specify set Data with Numerical value(int). 
	 * 
	 * @param	data		Data to be set
	 * @param	offset		Offset of Data
	 * @param	length		Length of Data
	 */
	protected void setIntColumn(int data, int offset, int length)
	{
		byte[] byteData = Integer.toString(data).getBytes();
		byteData = Idutils.createByteDataRight(byteData, length);
		for (int i = 0; i < length; i ++)
		{
			currentLine[i + offset] = byteData[i];
		}
		return;
	}
	
	//#CM701691
	/**
	 * Do processing which copies the file to the specified destination. <BR>
	 * Copy fileInName of the first argument to the second argument fileOutName. 
	 * @param fileInName	File Name at copy source
	 * @param fileOutName  File Name at copy destination
	 * @throws IOException		I/O Error generation
	 */
	public static void copy(String fileInName, String fileOutName) throws IOException
	{
		FileInputStream in = new FileInputStream(fileInName);
		FileOutputStream out = new FileOutputStream(fileOutName);
		
		int i =0;
		while((i= in.read()) != -1)
		{
			out.write(i);
		}
		in.close();
		out.close();
	}

	//#CM701692
	/**
	 * Preserve history work Data. 
	 * 
	 * @throws IOException		I/O Error generation
	 */
	public void saveHistoryFile() throws IOException
	{	
		if(wFileName == null)
		{
			return;
		}
		//#CM701693
		// FTP file backup path
		String histPath = WmsParam.FTP_FILE_HISTORY_PATH;
		//#CM701694
		// FTP file backup maintenance days

		//#CM701695
		// Make the directory when not existing. 
		File dirObject = new File(histPath);
		if (! dirObject.isDirectory())
		{
			dirObject.mkdirs();
		}
		//#CM701696
		// Acquire File Name and the folder name (For two hierarchies) of the file of the backup origin. 
		File targetDataFile =new File(wFileName);
		String targetDataFileName = targetDataFile.getName();

		File level1DirObj = new File(targetDataFile.getParent());
		String level1DirName = level1DirObj.getName();
		
		File level2DirObj = new File(level1DirObj.getParent());
		String level2DirName = level2DirObj.getName();

		String date = DATE_FORMAT.format(new Date());
		//#CM701697
		// Generate the path name of history Data File. 
		historyDataFileName = histPath + level1DirName + "_" + level2DirName + "_" +
										targetDataFileName.replaceAll("ID\\d\\d\\d\\d", ("$0_" + date));
		//#CM701698
		// Copy Content of work Data file onto the history file. 
		copy(wFileName, historyDataFileName);
	}
	
	//#CM701699
	/**
	 * Delete history work Data. 
	 */
	public void deleteHistoryFile()
	{
		File hisFile = new File(historyDataFileName);
		hisFile.delete();
	}
	
	//#CM701700
	// Private methods -----------------------------------------------
}
//#CM701701
//end of class
