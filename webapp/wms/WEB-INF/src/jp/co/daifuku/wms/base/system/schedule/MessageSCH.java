//#CM698480
//$Id: MessageSCH.java,v 1.2 2006/11/13 06:03:07 suresh Exp $

//#CM698481
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.sql.Connection;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.MissingResourceException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Date;

import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.utility.DateUtil;

//#CM698482
/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * Allow this class to inquire the WEB Message log. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for inquiring the message log. <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1.Process by clicking "Display" button(<CODE>query()</CODE>method) <BR><BR><DIR>
 *   Receive the content entered via screen as a parameter and obtain a data for output to the Preset area from the "message.log" file, and then return it.<BR>
 *   If no corresponding message is found, return <CODE>Parameter</CODE> array with number of elements equal to 0.
 *   Return null when condition error occurs.<BR>
 *   Use the <CODE>getMessage()</CODE> method to refer the content of error.<BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Starting date/time of searching (date) <BR>
 *     Starting date/time of searching (time) <BR>
 *     End date/time of searching (date) <BR>
 *     End date/time of searching (time) <BR>
 *     Display condition* <BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Date/Time <BR>
 *     Content <BR>
 *     Class where error occurred. <BR>
 *     Message <BR></DIR>
 * <BR>
 *   <Details of processes> <BR><DIR>
 *   1.Allow the DateUtil class to check the values of Starting date/time of searching and End date/time of searching. <BR><DIR>
 *     Set the message and close the process when error occurred. <BR></DIR>
 *   2.Generate an instance of a class that load a Message log file. <BR><DIR>
 *     Use the file name that is defined for MESSAGELOG_FILE of "CommonParam.properties". <BR></DIR>
 *   3.Obtain the next one line from the Message log file. <BR><DIR>
 *     Loop the data hereafter to the end of the file. <BR></DIR>
 * <BR>
 *     _Start LOOP_ <BR><DIR>
 *       4.Set the obtained single line in the class for dividing data and the StringTokenizer class. <BR>
 *       5.Reserve the data in the array set in the StringTokenizer class. <BR><DIR>
 *         Obtain the writing time, error code, facility code, and class where error occurred, and then parameter in this order. <BR></DIR>
 *       6.Obtain a message by using the MessageResource class based on the obtained error code. <BR><DIR>
 *         If no resource No. exits in the MessageResource.properties, blank the message. <BR></DIR>
 *       7.Translate the obtained facility code into the value defined for the SystemParameter. <BR>
 *       8.Declare the Continue Process flag. <BR><DIR>
 *         Declare false. Set true if the content obtained from the file matches to the content of the Parameter. <BR></DIR>
 *       9.Compare the conditions to determine whether the display conditions of the parameter match the obtained facility code. <BR><DIR>
 *         Set true for the Continue Process flag if matched. <BR>
 *         Set false for the Continue Process flag if not matched, and continue to looping. <BR></DIR>
 *       10.If the Continue Process flag is True, compare the conditions between the obtained values of Starting date/time of searching and End date/time of searching as a parameter and the time at which the values have been entered. <BR><DIR>
 *         Set true for the Continue Process flag, if matched to the search conditions for Starting date/time of searching and End date/time of searching. <BR>
 *         Set false for the Continue Process flag if not matched, and continue to looping. <BR></DIR>
 *       11.Increase the message display count if the Continue Process flag is True. <BR>
 *       12.Check the count of displayed messages exceeds the maximum count of displayed messages. <BR>
 *       13.Delete the oldest data of the retained data if the count of displayed messages exceeds the max display count. <BR>
 *       14.Set the obtained data for the returned parameter. <BR></DIR>
 *     _Close LOOP_ <BR>
 * </DIR>
 * The current program reads through the data sequentially from the leading data.<BR>
 * Allow to operate the data in the unit of a line and display it.<BR>
 * However, if a message.log file contains the data of<BR>
 * <BR>
 * 2004 10 20 XX XXXX XXXXXXXXXXXXXX XXXXXXXXXXXXXXXx<BR>
 * ........<BR>
 * ........<BR>
 * 2004 12 06 XX XXXX XXXXXXXXXXXXXX XXXXXXXXXXXXXXXx<BR>
 * 
 * <code>2004 12 07</code>, for instance, and user desires to display the message log corresponding to such period:<BR>
 * Even if reached to the end of the program, execute the said process and then displays that the program has no data.<BR>
 * <BR>
 * Allow the program to operate in the following logic.<BR>
 * <DIR>
 * 1. Obtain the Start Time and the End time.<BR>
 * 2. Change the format of the time into yyyy MM dd hh mm ss.<BR>
 * 3. Allow it to read a Random Access File.<BR>
 * 4. If the Start Time has been input, check the start time only in the "message.log" file.<BR>
 * 5. If two or more data with the same Start Time are found, store this file pointer in memory.<BR>
 * 6. To read a file in Japanese by utf-8, generate a FileInputStream and read it via the FileDescriptor that was previously stored.
 *    <BR>
 * 7. Read all the data contained within the range upto the end time and set these data for parameter.<BR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/25</TD><TD>S.Yoshida</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:07 $
 * @author  $Author: suresh $
 */
public class MessageSCH extends AbstractSystemSCH
{

	//#CM698483
	// Class variables -----------------------------------------------

	//#CM698484
	/**
	 * Max count of data allowable to display
	 */
	private static final int wMaxDisplay =
		WmsParam.MAX_NUMBER_OF_DISP_MESSAGE_LOG;

	//#CM698485
	/**
	 * Message log file name
	 */
	private static final String wMessageLogPath =
		WmsParam.LOGS_PATH + "/" + WmsParam.MESSAGELOG_FILE;

	//#CM698486
	/**
	 * Format type of log date.
	 */
	private static final String wDateFormat = "yyyy MM dd HH mm ss SSS z";

	//#CM698487
	/**
	 * Class Name (Inquire Message Log)
	 */
	private static String wProcessName = "MessageSCH";

	//#CM698488
	// Class method --------------------------------------------------
	//#CM698489
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:07 $");
	}
	//#CM698490
	// Constructors --------------------------------------------------
	//#CM698491
	/**
	 * Initialize this class.
	 */
	public MessageSCH()
	{
		wMessage = null;
	}

	//#CM698492
	// Public methods ------------------------------------------------

	//#CM698493
	/**
	 * Receive the content entered via screen as a parameter and obtain a data for output to the Preset area from the "message.log" file, and then return it.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Instance to maintain database connection.
	 * @param searchParam An instance of the <CODE>SystemParameter</CODE> class with conditions for obtaining display data.<BR>
	 *         Designating any instance other than <CODE>SystemParameter</CODE> throws ScheduleException.<BR>
	 * @return Array of <CODE>SystemParameter</CODE> instance with search result.<BR>
	 *          If no corresponding message is found, return array with number of elements equal to 0.<BR>
	 *          Return null if the count of the search results exceeds 1000 or if error occurs in the input conditions.<BR>
	 *          Returning array with element qty 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		RandomAccessFile rAFile = null;
		BufferedReader br = null;
		//#CM698494
		// Parameter of Date
		String fromDateToken;
		String toDateToken;
		String fileDate = "";
		//#CM698495
		// File pointer
		long l1 = 0;

		//#CM698496
		// Allow this vector to store the content of a file.
		Vector vec = new Vector();
		//#CM698497
		// Counter for counting messages to be displayed
		int wCnt = 0;

		//#CM698498
		// Generate an instance of the class to check for date.
		DateUtil wDateUtil = new DateUtil();
		SystemParameter[] viewParam = null;

		try
		{
			//#CM698499
			// Search conditions for parameters
			SystemParameter wParam = (SystemParameter) searchParam;

			//#CM698500
			//Check for date/time of searching.
			int wDateCheck = wDateUtil.checkDateTime(wParam.getFromFindDate(), // 検索開始日付 
													  wParam.getFromFindTime(), // 検索開始時間
													  wParam.getToFindDate(), // 検索終了日付
													  wParam.getToFindTime()); // 検索終了時間

			if (wDateCheck > 0)
			{
				//#CM698501
				// Obtain the start date (Search From) and the end date (Search To).
				fromDateToken =
					this.changeDateFormat(wParam.getFromFindDate(), wParam.getFromFindTime());
				toDateToken =
					this.changeToDateFormat(wParam.getToFindDate(), wParam.getToFindTime());

				//#CM698502
				//Create a read-only file of RAF.
				rAFile = new RandomAccessFile(wMessageLogPath, "r");

				String inputLine;
				//#CM698503
				//Print flag
				boolean flag = false;

				//#CM698504
				//Turn the Print flag ON if the corresponding starting time of searching exists.
				if (fromDateToken.length() == 19)
				{
					flag = true;
				}
				else
				{
					//#CM698505
					//Reset the file pointer to 0 if the starting date/time of searching is not proper.
					l1 = rAFile.getFilePointer();

					//#CM698506
					//Set the point for seeking a file.
					rAFile.seek(l1);
				}

				//#CM698507
				//Read the log file from the leading using an identifier and collate the entered start time with the time that exists in the log file.
				//#CM698508
				//
				while ((flag == true) && ((inputLine = rAFile.readLine()) != null))
				{
					StringTokenizer sToken = new StringTokenizer(inputLine, "\t");
					if (sToken.hasMoreTokens())
					{
						String t1 = sToken.nextToken();
						//#CM698509
						//grab date & time from the first token
						StringTokenizer st1 = new StringTokenizer(t1, " ");
						if (st1.hasMoreTokens())
						{
							String date =
								st1.nextToken() + " " + st1.nextToken() + " " + st1.nextToken();
							String time =
								st1.nextToken() + " " + st1.nextToken() + " " + st1.nextToken();
							fileDate = date + " " + time;
						}

						//#CM698510
						//If the start time stored in the log file is larger than the input time,
						//#CM698511
						//set a file pointer.
						//#CM698512
						//Reset the Print flag.
						if ((fromDateToken.length() > 0) && fromDateToken.compareTo(fileDate) <= 0)
						{
							//#CM698513
							//Set the seek point (Set a wild card with a period?) and return to the end line and then set "\r" and "\n". 
							try
							{
								rAFile.seek(l1);
							}
							catch (Exception e)
							{

							}
							flag = false;
						}
					}
					//#CM698514
					//Maintain the file pointer.
					l1 = rAFile.getFilePointer();
				}

				String readInput;

				//#CM698515
				//Create a FileInputStream reader to read Japanese text.
				br =
					new BufferedReader(
						new InputStreamReader(new FileInputStream(rAFile.getFD()), "utf-8"));

				//#CM698516
				//Turn the Print flag ON.
				flag = true;
				//#CM698517
				// A flag to determine wheter the display count exceeds the max display count.
				boolean isOverMaxDisplay = false;
				while (((readInput = br.readLine()) != null) && (flag == true))
				{
					StringTokenizer wDivide = new StringTokenizer(readInput, "\t");
					while ((wDivide.hasMoreElements()) && (flag == true))
					{

						String t1 = wDivide.nextToken();
						//#CM698518
						//Obtain the date from the first token (grab).
						StringTokenizer st1 = new StringTokenizer(t1, " ");
						if (st1.hasMoreTokens())
						{
							String date =
								st1.nextToken() + " " + st1.nextToken() + " " + st1.nextToken();
							String time =
								st1.nextToken() + " " + st1.nextToken() + " " + st1.nextToken();
							fileDate = date + " " + time;
						}

						//#CM698519
						//If the start time stored in the log file is larger than the input time,
						//#CM698520
						//turn the print flat OFF.
						if ((toDateToken.length() > 0) && toDateToken.compareTo(fileDate) < 0)
						{
							flag = false;
						}
						if (flag)
						{
							//#CM698521
							// Error code
							String wErrCode = wDivide.nextToken();
							//#CM698522
							// Facility Code
							String wFacilityCode = wDivide.nextToken();
							//#CM698523
							// Class where error occurred.
							String wClassInfo = wDivide.nextToken();
							//#CM698524
							// Parameter
							int count = wDivide.countTokens();
							String[] wParams = new String[count];
							if (wDivide.hasMoreTokens())
							{
								for (int j = 0; j < count; j++)
								{
									wParams[j] = wDivide.nextToken();
								}
							}

							//#CM698525
							// Translate the data type of date/time of writing data from string type into Date type.
							Date wWriteDate =
								new SimpleDateFormat(wDateFormat).parse(t1, new ParsePosition(0));

							//#CM698526
							// Obtain the message.
							//#CM698527
							// Message
							String wMessageLog = "";
							try
							{
								//#CM698528
								// Obtain the message.
								wMessageLog =
									SimpleFormat.format(
										MessageResource.getMessage(wErrCode),
										wParams);
							}
							//#CM698529
							// If no resource No. is found:
							catch (MissingResourceException e)
							{
								//#CM698530
								// Blank the Message.
								wMessageLog = "";
							}

							//#CM698531
							//Check for facility code.
							//#CM698532
							// Variable for displaying facility code.
							String wDispFacility = "";
							//#CM698533
							// "Continue Process" flag (True: Continue the process, False: Continue the process and obtain the next data).
							boolean wNext = false;

							//#CM698534
							// Display all data.
							if (wParam
								.getSelectDisplayCondition_MessageLog()
								.equals(SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ALL))
							{
								//#CM698535
								// Continue flag ON
								wNext = true;
								//#CM698536
								// Set the value defined in the SystemParameter for the variable for displaying facility code.
								if (wFacilityCode.equals(LogMessage.F_DEBUG))
								{
									wDispFacility = "";
								}
								else if (wFacilityCode.equals(LogMessage.F_INFO))
								{
									//#CM698537
									// Guidance
									wDispFacility =
										SystemParameter
											.SELECTDISPLAYCONDITION_MESSAGELOG_INFORMATION;
								}
								else if (wFacilityCode.equals(LogMessage.F_NOTICE))
								{
									//#CM698538
									// Caution
									wDispFacility =
										SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ATTENTION;
								}
								else if (wFacilityCode.equals(LogMessage.F_WARN))
								{
									//#CM698539
									// Warning
									wDispFacility =
										SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_WARNING;
								}
								else if (wFacilityCode.equals(LogMessage.F_ERROR))
								{
									//#CM698540
									// Error
									wDispFacility =
										SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ERROR;
								}
							}
							//#CM698541
							// Display only the guidance.
							else if (
								wParam.getSelectDisplayCondition_MessageLog().equals(
									SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_INFORMATION))
							{
								if (wFacilityCode.equals(LogMessage.F_INFO))
								{
									wDispFacility =
										SystemParameter
											.SELECTDISPLAYCONDITION_MESSAGELOG_INFORMATION;
									wNext = true;
								}
								else
								{
									wNext = false;
								}
							}
							//#CM698542
							// Display only Cautions.
							else if (
								wParam.getSelectDisplayCondition_MessageLog().equals(
									SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ATTENTION))
							{
								if (wFacilityCode.equals(LogMessage.F_NOTICE))
								{
									wDispFacility =
										SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ATTENTION;
									wNext = true;
								}
								else
								{
									wNext = false;
								}
							}
							//#CM698543
							// Display only warning.
							else if (
								wParam.getSelectDisplayCondition_MessageLog().equals(
									SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_WARNING))
							{
								if (wFacilityCode.equals(LogMessage.F_WARN))
								{
									wDispFacility =
										SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_WARNING;
									wNext = true;
								}
								else
								{
									wNext = false;
								}
							}
							//#CM698544
							// Display only errors.
							else if (
								wParam.getSelectDisplayCondition_MessageLog().equals(
									SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ERROR))
							{
								if (wFacilityCode.equals(LogMessage.F_ERROR))
								{
									wDispFacility =
										SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ERROR;
									wNext = true;
								}
								else
								{
									wNext = false;
								}
							}

							//#CM698545
							// Proceed to the next process If the Continue flag is ON.
							if (wNext)
							{
								//#CM698546
								// Obtain the start date/time (Search From) and the end date/time (Search To).
								Date wStrDate =
									wDateUtil.getDate(
										wParam.getFromFindDate(),
										wParam.getFromFindTime(),
										true);
								Date wEndDate =
									wDateUtil.getDate(
										wParam.getToFindDate(),
										wParam.getToFindTime(),
										false);

								//#CM698547
								// Check the date
								//#CM698548
								// Enter only the Start date/time (Search From).
								if (wDateCheck == DateUtil.INPUT_START_DATE)
								{
									if (wStrDate.compareTo(wWriteDate) <= 0)
									{
										wNext = true;
									}
									else
									{
										wNext = false;
									}
								}
								//#CM698549
								// Enter only the End date/time.
								else if (wDateCheck == DateUtil.INPUT_END_DATE)
								{
									if (wEndDate.compareTo(wWriteDate) > 0)
									{
										wNext = true;
									}
									else
									{
										wNext = false;
									}
								}
								//#CM698550
								// Enter both values for the Start date/time (Search From) and the End date/time (Search To).
								else if (wDateCheck == DateUtil.INPUT_START_END)
								{
									if (wStrDate.compareTo(wWriteDate) <= 0
										&& wEndDate.compareTo(wWriteDate) > 0)
									{
										wNext = true;
									}
									else
									{
										wNext = false;
									}
								}
								//#CM698551
								// No date/time entered
								else if (wDateCheck == DateUtil.NULL_START_END)
								{
									wNext = true;
								}

								//#CM698552
								// Continue the process If the Continue flag is ON.
								if (wNext)
								{
									//#CM698553
									// Plus 1 to the counter of messages to be displayed.
									wCnt ++;

									//#CM698554
									// If exceeding the Max count of data allowable to display:
									if (wCnt > wMaxDisplay)
									{
										//#CM698555
										// Change the flag to ON as it exceeds the max display count.
										isOverMaxDisplay = true;
										//#CM698556
										// To display 100 data.
										//#CM698557
										// delete the leading data (the oldest data).
										vec.removeElementAt(0);

									}

									//#CM698558
									// Set the data to be displayed for the returned parameter.
									SystemParameter temp = new SystemParameter();
									//#CM698559
									// Date/Time
									temp.setMessageLogDate(
										DateOperator.changeDate(wWriteDate)
											+ " "
											+ DateOperator.changeDateTimeMillis(wWriteDate));
									//#CM698560
									// Content
									temp.setError(wDispFacility);
									//#CM698561
									// Class where error occurred.
									temp.setErrorClass(wClassInfo);
									//#CM698562
									// Message
									temp.setMessage(wMessageLog);
									vec.addElement(temp);
								}
							}

							//#CM698563
							//Reset the order to the latest date order.
							Vector viewVec = new Vector();
							for (int i = vec.size() - 1; i >= 0; i--)
							{
								viewVec.addElement(vec.get(i));
							}
							viewParam = new SystemParameter[viewVec.size()];
							viewVec.copyInto(viewParam);
						}
					}
				}

				if (wCnt > 0)
				{
					if (isOverMaxDisplay)
					{
						//#CM698564
						// 6021017={0} data matched. Latest {1} data is shown.
						wMessage = "6021017" + wDelim + wCnt + wDelim + wMaxDisplay;
					}
					else
					{
						//#CM698565
						// 6001013 = Data is shown.
						wMessage = "6001013";
					}
				}
				else
				{
					//#CM698566
					// 6003018 = No target data was found.
					wMessage = "6003018";
				}
				return viewParam;
			}
			else
			{
				//#CM698567
				// If error occurs in the input of date:
				//#CM698568
				// Calendar error of Starting date/time of searching
				if (wDateCheck == DateUtil.START_FORMAT_ERROR)
				{
					//#CM698569
					// 6023180=Error with starting date of search. Please enter the date correctly.
					wMessage = "6023180";
				}
				//#CM698570
				// Calendar error caused by the End date/time of searching.
				else if (wDateCheck == DateUtil.END_FORMAT_ERROR)
				{
					//#CM698571
					// 6023181=Error with end date of search. Please enter the date correctly.
					wMessage = "6023181";
				}
				//#CM698572
				// If the Start date/time is later than the End date/time:
				else if (wDateCheck == DateUtil.DATE_INPUT_ERROR)
				{
					//#CM698573
					// 6023182=Starting date of search must precede the end date.
					wMessage = "6023182";
				}
				return null;
			}
		}
		//#CM698574
		//File open error
		catch (FileNotFoundException e)
		{
			//#CM698575
			// 6006020=File I/O error occurred. {0}
			RmiMsgLogClient.write(new TraceHandler(6006020, e), wProcessName);
			//#CM698576
			// 6003018 = No target data was found.
			throw (new ScheduleException("6003018"));
		}
		//#CM698577
		// Error when operating a file
		catch (IOException e)
		{
			//#CM698578
			// 6006020=File I/O error occurred. {0}
			RmiMsgLogClient.write(new TraceHandler(6006020, e), wProcessName);
			//#CM698579
			// 6007031=File I/O error occurred. See log.
			throw (new ScheduleException("6007031"));
		}
		//#CM698580
		// Other errors
		catch (Exception e)
		{
			//#CM698581
			// 6006020=File I/O error occurred. {0}
			RmiMsgLogClient.write(new TraceHandler(6006020, e), wProcessName);
			//#CM698582
			// 6007031=File I/O error occurred. See log.
			throw (new ScheduleException("6007031"));
		}
		finally
		{
			try
			{
				if (br != null)
				{
					//#CM698583
					// Close the file.
					br.close();
				}
				//#CM698584
				//close raf
				if (rAFile != null)
				{
					rAFile.close();
				}
			}
			catch (IOException e)
			{
				//#CM698585
				// 6006020=File I/O error occurred. {0}
				RmiMsgLogClient.write(new TraceHandler(6006020, e), wProcessName);
				//#CM698586
				// 6007031=File I/O error occurred. See log.
				throw (new ScheduleException("6007031"));
			}
		}

	}

	//#CM698587
	// Package methods -----------------------------------------------

	//#CM698588
	// Protected methods ---------------------------------------------

	//#CM698589
	// Private methods -----------------------------------------------

	//#CM698590
	/**
	 * concatenate the string of Date and Time in the format of "yyyy MM DD HH mm ss".
	 * @param dateStr String of date
	 * @param timeStr String of time.
	 * @return Value with Date and Time combined.
	 */
	private String changeDateFormat(String dateStr, String timeStr)
	{
		String date_s = "";
		String deLimiter = " ";

		if (dateStr.length() == 8)
		{
			date_s =
				dateStr.substring(0, 4)
					+ deLimiter
					+ dateStr.substring(4, 6)
					+ deLimiter
					+ dateStr.substring(6, 8);

			if (timeStr.length() == 6)
			{
				date_s += deLimiter
					+ timeStr.substring(0, 2)
					+ deLimiter
					+ timeStr.substring(2, 4)
					+ deLimiter
					+ timeStr.substring(4, 6);
			}
			else
			{
				date_s += deLimiter + "00" + deLimiter + "00" + deLimiter + "00";
			}
		}
		return date_s;
	}

	//#CM698591
	/**
	 * concatenate the string of Date and Time in the format of "yyyy MM DD HH mm ss".
	 * @param dateStr String of date
	 * @param timeStr String of time.
	 * @return Value with Date and Time combined.
	 */
	private String changeToDateFormat(String dateStr, String timeStr)
	{
		String date_s = "";
		String deLimiter = " ";

		if (dateStr.length() == 8)
		{
			date_s =
				dateStr.substring(0, 4)
					+ deLimiter
					+ dateStr.substring(4, 6)
					+ deLimiter
					+ dateStr.substring(6, 8);

			if (timeStr.length() == 6)
			{
				date_s += deLimiter
					+ timeStr.substring(0, 2)
					+ deLimiter
					+ timeStr.substring(2, 4)
					+ deLimiter
					+ timeStr.substring(4, 6);
			}
			else
			{
				date_s += deLimiter + "23" + deLimiter + "59" + deLimiter + "59";
			}
		}
		return date_s;
	}
}
//#CM698592
//end of class
