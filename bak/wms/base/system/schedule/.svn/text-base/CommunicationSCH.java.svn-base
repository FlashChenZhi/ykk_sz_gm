//#CM695564
//$Id: CommunicationSCH.java,v 1.2 2006/11/13 06:03:14 suresh Exp $

//#CM695565
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.io.IOException;
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RandomAcsFileHandler;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.utility.DateUtil;

//#CM695566
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 *
 * Allow the <CODE>CommunicationSCH</CODE> class to inquire the communication trace log of RFT.<BR>
 * Obtain the line of the target data from the RFTXXXRftCommunicationTrace.txt based on the conditions entered via screen and return it to the screen.<BR>
 * <BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * <Process by clicking "Display" button (<CODE>query() method)</CODE>><BR>
 * <DIR>
 * 	1.Check for presence of RFT Machine No.<BR>
 * 	<DIR>
 * 		Search for RFT Control info using the RFT Machine No. entered via screen as a condition.<BR>
 * 		If the count of the search results is 0, set the error message and return null and close the process.<BR>
 * 		Message to be set："Input any registered RFT Machine No."<BR>
 * 	</DIR>
 * 	<BR>
 * 	2.Check for date/time of searching.<BR>
 * 	<DIR>
 * 		Using the <CODE>DateUtil</CODE> class, check the date/time of searching that was entered via screen.<BR>
 * 		If the checking result is error, set the error message. Return null and close the process.<BR>
 * 		Message to be set<BR>
 * 		<DIR>
 * 			Error caused by the Starting date/time of searching: "Calendar error occurred in the Starting date/time of searching. Enter the correct date"<BR>
 * 			Error caused by the End date/time of searching: "Calendar error occurred in the End date/time of searching. Enter a correct date"<BR>
 * 		</DIR>
 * 	</DIR>
 * 	<BR>
 * 	3.Obtain the file data.<BR>
 * 	<DIR>
 * 		Obtain the file name from WMSParam and maintain the content of the file in Vector.<BR>
 * 	</DIR>
 * 	<BR>
 * 	4.Obtain the data to be displayed<BR>
 * 	<DIR>
 * 		Extract the data corresponding to the date of searching and the display conditions that were entered via screen, from the data maintained at the process 3, and<BR>
 * 		set it in the <CODE>SystemParameter</CODE> class.<BR>
 * 		<BR>
 * 		Setting a larger value than the max display count of WMSParam in the display count field sets an error message and return null, and then close the process.<BR>
 * 		Setting 0 in the diplay count field sets an error message and return <CODE>SystemParameter</CODE> with 0 as the number of elements, and close the process.<BR>
 * 		Message to be set<BR>
 * 			<DIR>
 * 				Max count of data allowable to display: "The count of data to be displayed exceeds {0}. Set additional search conditions."<BR>
 * 				Count of data: 0 	  : "No target data was found"<BR>
 * 			</DIR>
 * 		<BR>
 * 		If the count does not match to the count mentioned above, set a message and return <CODE>SystemParameter</CODE>.
 * 	</DIR>
 * </DIR>
 * <BR>
 * <Parameter>*Mandatory Input<BR>
 * <DIR>
 * 	RFT Machine No..*<BR>
 * 	Starting date/time of searching<BR>
 * 	End date/time of searching (time)<BR>
 * 	Display condition*<BR>
 * </DIR>
 * <BR>
 * <Field items to be returned><BR>
 * <DIR>
 * 	Date/Time<BR>
 * 	Sent/Received<BR>
 * 	Communication Statement<BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/21</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:14 $
 * @author  $Author: suresh $
 */
public class CommunicationSCH extends AbstractSystemSCH
{
	//#CM695567
	// Class variables -----------------------------------------------
	
	//#CM695568
	/**
	 * Max count of data allowable to display in the screen
	 */
	private static final int MAX_DISPLAY = WmsParam.MAX_NUMBER_OF_DISP_TRACE_LOG;
	
	//#CM695569
	/**
	 * The start line of the file to be read
	 */
	private static final int READ_START = 0;

	//#CM695570
	/**
	 * Max size of Trace file
	 */
	private static final int TRACE_FILE_MAX_SIZE = WmsParam.TRACE_MAX_SIZE;

	//#CM695571
	/**
	 * STX
	 */
	private static final byte STX = 0x02;

	//#CM695572
	/**
	 * ETX
	 */
	private static final byte ETX = 0x03;

	//#CM695573
	/**
	 * [
	 */
	private static final byte START_BYTE = '[';

	//#CM695574
	/**
	 * ]
	 */
	private static final byte END_BYTE = ']';
	
	//#CM695575
	// Class method --------------------------------------------------

	//#CM695576
	/**
	 * Obtain the line of the target data from the RFTXXXRftCommunicationTrace.txt based on the conditions entered via screen and return it to the screen.<BR>
	 * <BR>
	 * Allow to refer to the descriptions of the relevant class for details.
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.<BR>
	 * Null if the search results exceeds the max count of display.<BR>
	 * A class that implements <CODE>Parameter</CODE> interface with the number of elements equal to 0 if the search result equal to 0.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		
		//#CM695577
		// Parameter for storing arguments
		SystemParameter systemParameter = null;
		//#CM695578
		// Parameter to be returned to the business class
		SystemParameter[] returnParameter = null;
		
		//#CM695579
		// Translate the type of searchParam.
		systemParameter = (SystemParameter)searchParam;

		//#CM695580
		// Counter for frequency of writing data
		int count = 0;
		
		//#CM695581
		// Variable for storing the Trace file
		RandomAcsFileHandler traceFile = null;
	
		//#CM695582
		// Obtain the RFTNo. from the screen.
		String rftNo = systemParameter.getRftNo();
	
		//#CM695583
		// Overwrite the RFT No. that was input on the string "000", in order to input numeral value for RFT No.
		StringBuffer rftBuf = new StringBuffer(new String("000"));
		rftBuf.replace(rftBuf.length() - rftNo.length(), rftBuf.length(), rftNo);
		rftNo = rftBuf.toString();
	
		//#CM695584
		// Set the RFT No. for searchParam to display it padded with 0.
		systemParameter.setRftNo(rftNo);
	
		try
		{
			//#CM695585
			// If the RFTNo. and the date/time of searching are regarded as normal in the input check.
			if(check(conn, systemParameter))
			{
				//#CM695586
				// Generate a file name.
				String traceFileName = WmsParam.WMS_LOGS_PATH + "RFT" + rftNo + WmsParam.RFT_TRACE_NAME;// "RftCommunicationTrace.txt";

				//#CM695587
				// Read a file.
				traceFile = new RandomAcsFileHandler(traceFileName, TRACE_FILE_MAX_SIZE);

				//#CM695588
				// Obtain the End point of reading a file.
				int readEnd = traceFile.getSizeUTF();

				//#CM695589
				// Close with error if the readEnd is negative.
				if(readEnd <= 0 )
				{
					//#CM695590
					//No target data was found.
					wMessage = "6003018";
					return new SystemParameter[0];
				}

				//#CM695591
				// Open a file.
				if(traceFile.readOpenUTF() == false)
				{
					//#CM695592
					//No target data was found.
					wMessage = "6003018";
					return new SystemParameter[0];
				}

				//#CM695593
				// Allow this vector to store the content of a file.
				Vector vec = new Vector();
				vec = traceFile.readUTF(READ_START, readEnd);
				//#CM695594
				// Store the content of the file into the LogMessage type array.
				LogMessage[] msg = new LogMessage[vec.size()];
				vec.copyInto(msg);
				
				//#CM695595
				// Close a file.
				traceFile.readClose();

				//#CM695596
				// Allow this vector to store the returned data.
				Vector returnVector = new Vector();
				
				//#CM695597
				// A flag to determine wheter the relevant length exceeds the max length.
				boolean isOverMaxDisplay = false;
				
				//#CM695598
				// Loop based on the size of array.
				for(int i = 0; i < msg.length; i++)
				{
					//#CM695599
					// Reserve data in the array.
					//#CM695600
					// Date/Time of Writing Data
					java.util.Date writeDate = msg[i].getDate();
					//#CM695601
					// Division of Sent/Received
					String sendReceiveFlag = msg[i].getClassInfo();
					//#CM695602
					// Communication Statement
					String communicateMessage = "";
					
					//#CM695603
					// Generate a buffer for work.
					byte[] byteBuf = msg[i].getMessage().getBytes();
				
					//#CM695604
					// Translate STX and ETX respectively into "[" and "]".
					if(byteBuf.length != 0)
					{
						for(int j = 0; j < byteBuf.length; j++)
						{
							//#CM695605
							// If STX is found:
							if(byteBuf[j] == STX)
							{
								byteBuf[j] = START_BYTE;
							}
							//#CM695606
							// If ETX is found
							if(byteBuf[j] == ETX)
							{
								byteBuf[j] = END_BYTE;
								break;
							}
						}
						communicateMessage = new String(byteBuf);
					}
				
					//#CM695607
					// Variable for displaying sent/received communications
					String sendReceive = "";
				
					//#CM695608
					// Continue Process flag
					boolean next = false;
					
					//#CM695609
					// Check for sending and receiving.
					//#CM695610
					// Display all data.
					if(systemParameter.getSelectDisplayCondition_CommunicationLog().equals(SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_ALL))
					{
						//#CM695611
						// Continue flag ON
						next = true;
						
						//#CM695612
						// Set a value for the Variable for displaying sent/received communications.
						if(sendReceiveFlag.equals("S"))
						{
							sendReceive = SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_SEND;
						}
						else
						{
							sendReceive = SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_RECEIVE;
						}
					}
					//#CM695613
					// Display only the sent communications.
					else if(systemParameter.getSelectDisplayCondition_CommunicationLog().equals(SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_SEND))
					{
						//#CM695614
						// Set a value for the Variable for displaying sent/received communications.
						if(sendReceiveFlag.equals("S"))
						{
							sendReceive = SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_SEND;

							//#CM695615
							// Continue flag ON
							next = true;
						}
						else
						{
							//#CM695616
							// Continue flag OFF
							next = false;
						}
					}
					//#CM695617
					// Display only received communications.
					else if(systemParameter.getSelectDisplayCondition_CommunicationLog().equals(SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_RECEIVE))
					{
						//#CM695618
						// Set a value for the Variable for displaying sent/received communications.
						if(sendReceiveFlag.equals("S"))
						{
							//#CM695619
							// Continue flag OFF
							next = false;
						}
						else
						{
							sendReceive = SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_RECEIVE;
							
							//#CM695620
							// Continue flag ON
							next = true;
						}
					}

					//#CM695621
					// Proceed to the next process If the Continue flag is ON.
					if(next == true)
					{
						DateUtil dateUtil = new DateUtil();
						
						//#CM695622
						// Obtain the start date/time (Search From) and the end date/time (Search To).
						java.util.Date startDate = dateUtil.getDate(systemParameter.getFromFindDate(), systemParameter.getFromFindTime(), true);
						java.util.Date endDate = dateUtil.getDate(systemParameter.getToFindDate(), systemParameter.getToFindTime(), false);
						
						//#CM695623
						// Check for date/time of searching.
						int date = dateCheck(systemParameter);
						
						//#CM695624
						// Check the date
						switch(date)
						{
							case 1:	 // 開始日時のみ入力
								if(startDate.compareTo(writeDate) <= 0)
								{
									//#CM695625
									// Continue flag ON
									next = true;
								}
								else
								{
									//#CM695626
									// Continue flag OFF
									next = false;
								}
								break;
							case 2:	 // 終了日時のみ入力
								if(endDate.compareTo(writeDate) > 0)
								{
									//#CM695627
									// Continue flag ON
									next = true;
								}
								else
								{
									//#CM695628
									// Continue flag OFF
									next = false;
								}
								break;
							case 3:	 // 開始日時、終了日時両方入力
								if(startDate.compareTo(writeDate) <= 0 && endDate.compareTo(writeDate) > 0)
								{
									//#CM695629
									// Continue flag ON
									next = true;
								}
								else
								{
									//#CM695630
									// Continue flag OFF
									next = false;
								}
								break;
							case 4:	 // 日時入力無し
								//#CM695631
								// Continue flag ON
								next = true;
								break;
							default:
								break;
						}

						//#CM695632
						// Continue the process If the Continue flag is ON.
						if(next == true)
						{
							//#CM695633
							// Increase by 1 to the counter of written data.
							count++;
							
							if (!isOverMaxDisplay)
							{
								//#CM695634
								// If the frequency of writing data exceeds the maximum frequency.
								if(count > MAX_DISPLAY)
								{
									isOverMaxDisplay = true;
								}
								else
								{
									//#CM695635
									// Maintain the data to be displayed.
									SystemParameter temp = new SystemParameter();
									temp.setMessageLogDate(DateOperator.changeDate(writeDate) + " " + DateOperator.changeDateTimeMillis(writeDate));
									temp.setCommunication(sendReceive);
									temp.setCommunicationMessage(communicateMessage.trim().replace('\\', '/'));
									returnVector.addElement(temp);
								}

							}
							
						}
					}
				}
				
				//#CM695636
				// If there is a data to be displayed:
				if(count > 0)
				{
					if (isOverMaxDisplay)
					{
						//#CM695637
						// 6021017={0} data matched. Latest {1} data is shown.
						wMessage = "6021017" + wDelim + count + wDelim + MAX_DISPLAY;
					}
					else
					{
						//#CM695638
						// Set the message: "Displayed".
						wMessage = "6001013";
					}
					
					//#CM695639
					// Return the data to be displayed.
					returnParameter = new SystemParameter[returnVector.size()];
					returnVector.copyInto(returnParameter);
					return returnParameter;
				}
				//#CM695640
				// If there is no data to be displayed:
				else
				{
					//#CM695641
					// No target data was found.
					wMessage = "6003018";
					
					return new SystemParameter[0];
				}
			}
			//#CM695642
			// If the RFTNo. and the date/time of searching are regarded as abnormal in the input check.
			else
			{
				return null;
			}
		}
		//#CM695643
		// Error when operating a file
		catch(IOException e)
		{
			//#CM695644
			// Close a file.
			traceFile.readClose();
		
			//#CM695645
			// 6006020=File I/O error occurred. {0}
			RmiMsgLogClient.write( new TraceHandler(6006020, e), "CommunicationSCH" );
			//#CM695646
			// File I/O error occurred. See log.
			wMessage = "6007031";
			throw new ScheduleException(e.getMessage());
		}
		//#CM695647
		// Other errors
		catch(Exception e)
		{
			//#CM695648
			// Close a file.
			traceFile.readClose();
		
			//#CM695649
			// 6006001=Unexpected error occurred.{0}{0}
			RmiMsgLogClient.write( new TraceHandler(6006001, e), "CommunicationSCH" );
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM695650
	/**
	 * Check for presence of Rft No. entered via screen and validate the date/time of searching.<BR>
	 * Return true when both checking processes completed normally.<BR>
	 * Return false if error occurs in any check.
	 * @param  conn       Connection
	 * @param  checkParam Parameter entered via screen.
	 * @return Return true if checking completed normally.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		
		//#CM695651
		// Translate it into SystemParameter.
		SystemParameter systemParameter = (SystemParameter)checkParam;
		
		//#CM695652
		// Generate an instance for checking RFTNo.
		RftHandler rftHandler = new RftHandler(conn);
		RftSearchKey rftSearchKey = new RftSearchKey();
		
		//#CM695653
		// Check for presence of RFTNo. in the DB.
		rftSearchKey.setRftNo(systemParameter.getRftNo());
		
		//#CM695654
		// If not found:
		if(rftHandler.count(rftSearchKey) <= 0)
		{
			wMessage = "6023230";
			return false;
		}

		//#CM695655
		// Check for date/time of searching.
		int date = dateCheck(systemParameter);

		//#CM695656
		// If error occurs in the input of date:
		//#CM695657
		// Calendar error of Starting date/time of searching
		if(date == DateUtil.START_FORMAT_ERROR)
		{
			wMessage = "6023180";		// 検索開始日時が暦日エラーです。正しい日付を入力してください。
		}
		//#CM695658
		// Calendar error caused by the End date/time of searching.
		else if(date == DateUtil.END_FORMAT_ERROR)
		{
			wMessage = "6023181";		// 検索終了日時が暦日エラーです。正しい日付を入力してください。
		}
		//#CM695659
		// If the Start date/time is later than the End date/time:
		else if(date == DateUtil.DATE_INPUT_ERROR)
		{
			wMessage = "6023182";		// 検索開始日時は、検索終了日時より前の日付にしてください。
		}
		//#CM695660
		// If normal:
		else
		{
			return true;
		}
		return false;
	}
	
	//#CM695661
	// private method --------------------------------------------------
	//#CM695662
	/**
	 * Allow this method to check for date/time of searching.
	 * @param checkParam
	 * @return date/time of searching
	 */
	private int dateCheck(Parameter checkParam)
	{
		//#CM695663
		// Translate the data type of checkParam.
		SystemParameter param = (SystemParameter)checkParam;
		
		//#CM695664
		// Check for date/time of searching.
		DateUtil dateUtil = new DateUtil();
		return dateUtil.checkDateTime(param.getFromFindDate(),
										param.getFromFindTime(),
										param.getToFindDate(),
										param.getToFindTime());
	}
	
	//#CM695665
	/**
	 * Allow this method to feed lines for communication statement. Use this in jsp.<br>Insert a tag in the specified interval.
	 * @param msg Communication Statement
	 * @param interval Interval
	 * @return Communication statement that includes line-feed tag.
	 */
	private String insertBR(String msg, int interval)
	{
System.out.println("msg1st:" + msg);
		
		String insertChar = "\"" + "<BR>" + "\"";

		
		int insertCharLength = insertChar.length();
		msg = msg.trim().replace('\\', '/');
System.out.println("msg2nd:" + msg);
System.out.println("msg.length:" + msg.length());
System.out.println("msg.bytelength:" + msg.getBytes().length);
		
		if(msg.length() > interval)
		{
			StringBuffer stbuf = new StringBuffer(msg);
			int surplus = msg.length()%interval;
			int count = msg.length()/interval;
System.out.println("surplus:" + surplus);
System.out.println("count:" + count);			
			//#CM695666
			//Disable to require to feed a line for the last line if the remainder is 0.
			if(surplus == 0)
			{
				count = count -1;
			}
			int offset = 0;

			for(int i = 1; i <= count; i++)
			{
				offset = interval*i+insertCharLength*(i-1);
System.out.println("offset:" + offset);
				stbuf.insert(offset, insertChar);
			}
			msg = new String(stbuf);
System.out.println("resultmsg:");
System.out.println(msg);
		}
		return msg;
	}

}
//#CM695667
//end of class
