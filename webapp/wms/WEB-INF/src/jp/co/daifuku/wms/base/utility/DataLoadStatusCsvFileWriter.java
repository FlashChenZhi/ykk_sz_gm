package jp.co.daifuku.wms.base.utility;

//#CM686799
//$Id: DataLoadStatusCsvFileWriter.java,v 1.2 2006/11/07 07:13:39 suresh Exp $
//#CM686800
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;

//#CM686801
/**
 * This class creates the error list during data load process
 * implement the required methods for list process
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/23</TD><TD>T.Yamashita</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 07:13:39 $
 * @author  $Author: suresh $
 */	
public class DataLoadStatusCsvFileWriter
{
	//#CM686802
	// Class fields --------------------------------------------------
	//#CM686803
	/**
	 * normal/abnormal divisioon (abnormal)
	 */	
	public static final int STATUS_ERROR = -1;
	//#CM686804
	/**
	 * normal/abnormal divisioon (normal)
	 */
	public static final int STATUS_NORMAL = 0;

	//#CM686805
	// Class variables -----------------------------------------------

	//#CM686806
	/**
	 * message resource
	 */
	protected MessageResource mr = null;
	//#CM686807
	/**
	* It becomes mandatory to delete the current normal and abnormal data list,
	* while data writing starts with writeStatusList<BR>
	* This class decides that
	*/
	protected boolean isFirstCall = true;
	
	//#CM686808
	/**
	 * message (Vector)
	 */
	protected Vector wMessageVec = null;

	//#CM686809
	/**
	 * file name
	 */
	private  String wFileName = ""; 
	
	//#CM686810
	/**
	 * sub error file
	 * load error file name = load file + sub error file
	 */
	private static final String STATUS_FILE_MARK = "_STATUS" ;

	//#CM686811
	/**
	 * specify separator
	 */
	private static String separate = WmsParam.USERINFO_FIELD_SEPARATOR;
	
	//#CM686812
	// Class method --------------------------------------------------
	//#CM686813
	/**
	 * Return the version of this class
	 * @return version and timestamp
	 */
	public String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 07:13:39 $") ;
	}

	//#CM686814
	// Constructors --------------------------------------------------
	//#CM686815
	/**
	 * create instance
	 */
	public DataLoadStatusCsvFileWriter() 
	{
		//#CM686816
		// use the default locale
		mr = new MessageResource(Locale.getDefault() ) ; 
		isFirstCall = true;
		wMessageVec = new Vector(200);
	}

	//#CM686817
	/**
	 * create instance
	 * @param file name to write error
	 */
	public DataLoadStatusCsvFileWriter(String str) 
	{
		//#CM686818
		// use the default locale
		mr = new MessageResource(Locale.getDefault() ) ; 
		isFirstCall = true;
		wMessageVec = new Vector(200);
		setFileName(str);
	}

	//#CM686819
	/**
	 * create instance
	 * @param locale Locale
	 * @param file name to write error
	 */
	public DataLoadStatusCsvFileWriter(Locale locale,String str) 
	{
		mr = new MessageResource(locale) ; 
		isFirstCall = true;
		wMessageVec = new Vector(200);
		setFileName(str);
	}

	//#CM686820
	// Public methods ------------------------------------------------
	//#CM686821
	/**
	 * Add the data for csv file creation to a vector.<BR>
	 * After executing this method, write to a file using <code>writeStatusList</code>
	 * method
	 * @param procStatus normal/abnormal division
	 * @param line[]   one line data array
	 * @param msg    message
	 */
	public void addStatusCsvFile(int procStatus, String[] line, String msg)
	{
		String buf = Integer.toString(procStatus) + separate;
		String message = "";

		for(int i = 0; i < line.length; i++)
		{
			if(line[i] == null)
				line[i] = " ";
			buf = buf + line[i] + separate;
		}

		//#CM686822
		// if the message passed in the argument is not empty
		//#CM686823
		// fetch message from MessageResource
		if(!msg.equals(""))
		{
			message =  MessageResource.getMessage(msg);
		}
		buf = buf + message;
		wMessageVec.add(buf);
	}
	//#CM686824
	/**
	 * delete the csv file creation data
	 */
	public void removeAllElements()
	{
		wMessageVec.removeAllElements();
	}

	//#CM686825
	/**
	 * Create csv file that contains the data load errors. <BR>wMessageVec
	 * <BR>
	 * write all the data stored in vector
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public void writeStatusCsvFile() throws ScheduleException
	{
		try
		{
			//#CM686826
			// fetch file name
			String outFile = getFileName();

			//#CM686827
			// *** first delete the currently pending files ***
			File normal = new File(outFile);
			normal.delete();

			//#CM686828
			// create error file
			FileWriter dosOut = new FileWriter(outFile, true);
			String buf = "";
			//#CM686829
			// write all the data to file
			for(int i = 0; i < wMessageVec.size(); i++)
			{
				buf = (String)wMessageVec.get(i);
				dosOut.write(buf);
				dosOut.write("\n");
			}
			dosOut.close();
		}
		catch(IOException e)
		{
			//#CM686830
			// 6006020 = File I/O error occurred. {0}
			RmiMsgLogClient.write( new TraceHandler(6006020, e), "DataLoadStatusCsvFileWriter" ) ;
			//#CM686831
			// 6007031 = File I/O error occurred. See log.
			throw new ScheduleException("6007031");
		}
	}
	
	//#CM686832
	/**
	 * specify file name to write error
	 * pass it as path + file name
	 * @param str  file name
	 */
	public void setFileName(String str)
	{
		//#CM686833
		// file path + error file status
		String toPath = WmsParam.HISTRY_HOSTDATA_PATH;
		String toFile = makeFileName(str,STATUS_FILE_MARK);
		int last = toFile.lastIndexOf("\\");
		wFileName = toPath + toFile.substring(last+1);
	}
	//#CM686834
	/**
	 * This method decides the data load list storage destination
	 * return suitable file name by process type
	 * @return file name
	 */
	public String getFileName()
	{
		return wFileName;
	}
	//#CM686835
	// Package methods -----------------------------------------------
	//#CM686836
	// Protected methods ---------------------------------------------
	//#CM686837
	// Private methods -----------------------------------------------

	//#CM686838
	/**
	 * This method creates the history file name
	 * insert string supplied in the argument before extension
	 * @param org  original file name
	 * @param str  character string to insert
	 * @return file name for history
	 */
	private String makeFileName(String org, String str)
	{
		int last = org.lastIndexOf(".");
		StringBuffer stBuf = new StringBuffer(org);
		return stBuf.insert(last, str).toString();
	}
	
}

