package jp.co.daifuku.wms.base.common.tool.logViewer;
//#CM643296
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.io.EOFException;
import java.io.IOException;

import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RandomAcsFileHandler;


//#CM643297
/**
 * Acquire one communication trace log information. <BR>
 * From Search condition specified on the communication trace log list screen
 * Retrieve information to be displayed,Set it in the Communication trace log data class. <BR>
 * From the Communication trace log data class to the communication trace log list class
 * Hand over information. 
 * 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:56:43 $
 * @author  $Author: suresh $
 */

public class TraceLogFile
{
    //#CM643298
    // Class fields --------------------------------------------------
    //#CM643299
    /**
     * The maximum number of display lines
     */
    protected final static int MAX_DISPLAY = 10000;
    
    //#CM643300
    /**
     * Trace log File name
     */
    protected final static String TraceLogFileName = "RftCommunicationTrace.txt";
    
    //#CM643301
    /**
     * File reading beginning line
     */
    protected final static int READ_START = 0;
    
    //#CM643302
    /**
     * Size of trace file MAX
     */
    protected final static int TRACE_FILE_MAX_SIZE = 32000000;
    
    //#CM643303
    /**
     * STX
     */
    protected final static byte STX = 0x02;
    
    //#CM643304
    /**
     * ETX
     */
    protected final static byte ETX = 0x03;
    
    //#CM643305
    /**
     * [
     */
    protected final static byte START_BYTE = '[';
    
    //#CM643306
    /**
     * ]
     */
    protected final static byte END_BYTE = ']';
    
    //#CM643307
    /**
     * Start date format error
     */
    public static final int START_FORMAT_ERROR = -1;

    //#CM643308
    /**
     * End date format error
     */
    public static final int END_FORMAT_ERROR = -2;

    //#CM643309
    /**
     * The beginning date is lower than the end date. 
     */
    public static final int DATE_INPUT_ERROR = 0;

    //#CM643310
    /**
     * There is an input only at the beginning date. 
     */
    public static final int INPUT_START_DATE = 1;

    //#CM643311
    /**
     * There is an input only at the end date. 
     */
    public static final int INPUT_END_DATE = 2;

    //#CM643312
    /**
     * There is an input at both end dates and the beginning date. 
     */
    public static final int INPUT_START_END = 3;
    
    //#CM643313
    /**
     * There is no input at both end dates and the beginning date. 
     */
    public static final int NULL_START_END = 4;
    
    //#CM643314
    /**
     * ID Length
     */
    public static final int IdLength = LogViewerParam.IdFigure;
    
    //#CM643315
    /**
     * Number of digits of ID of default
     */
    protected final static int DefaultIdFigure = 4;

    //#CM643316
    //-----------------
    //#CM643317
    // Search condition variable definition
    //#CM643318
    //-----------------
    
    //#CM643319
    /**
     * Rft Title machine No
     */
    protected String rftNo;
    
    //#CM643320
    /**
     * ID
     */
    protected String idNo1;
    protected String idNo2;
    
    //#CM643321
    /**
     * Retrieval start date
     */
    protected String startProcessDate;
    protected String startProcessTime;
    
    //#CM643322
    /**
     * Retrieval end date
     */
    protected String endProcessDate;
    protected String endProcessTime;
    
    //#CM643323
    /**
     * Display condition
     */
    protected int displayCondition;
    
    //#CM643324
    /**
     * File name
     */
    protected String traceFileName;
    
    //#CM643325
    /**
     * Generate the instance. 
     */
    public TraceLogFile()
    {
        super();
    }

    //#CM643326
    /**
     * Set Rft Title machine No. 
     * @param value Rft Title machine No
     */
    public void setRftNo(String value)
    {
         rftNo = value;
    }
    
    //#CM643327
    /**
     * Acquire Rft Title machine No. 
     * @return Rft Title machine No
     */
    public String getRftNo()
    {
        return rftNo;
    }
    
    //#CM643328
    /**
     * Set IDNO1. 
     * @param value IDNO1
     */
    public void setIdNo1(String value)
    {
        idNo1 = value;
    }
    
    //#CM643329
    /**
     * Acquire IDNo1. 
     * @return IDNo1
     */
    public String getIdNo1()
    {
        return idNo1;
    }
    
    //#CM643330
    /**
     * Set IDNo2. 
     * @param value IDNO2
     */
    public void setIdNo2(String value)
    {
        idNo2 = value;
    }
    
    //#CM643331
    /**
     * Acquire IDNO2. 
     * @return idNo2
     */
    public String getIdNo2()
    {
        return idNo2;
    }

    //#CM643332
    /**
     * Set Retrieval beginning date. 
     * @param value Retrieval beginning date
     */
    public void setStartProcessDate(String value)
    {
        startProcessDate = value;
    }
    
    //#CM643333
    /**
     * Set Retrieval start time. 
     * @param value Retrieval start time. 
     */
    public void setStartProcessTime(String value)
    {
        startProcessTime = value;
    }
    
    //#CM643334
    /**
     * Set Retrieval end date. 
     * @param value Retrieval end date
     */
    public void setEndProcessDate(String value)
    {
        endProcessDate = value;
    }
    
    //#CM643335
    /**
     * Set Retrieval end time. 
     * @param value Retrieval end time
     */
    public void setEndProcessTime(String value)
    {
        endProcessTime = value;
    }
    
    //#CM643336
    /**
     * Acquire Retrieval beginning date. 
     * @return Retrieval beginning date
     */
    public String getStartProcessDate()
    {
        return startProcessDate;
    }
    
    //#CM643337
    /**
     * Acquire Retrieval start time. 
     * @return Retrieval start time. 
     */
    public String getStartProcessTime()
    {
        return startProcessDate;
    }
    
    //#CM643338
    /**
     * Set Retrieval end time. 
     * @return Retrieval end time
     */
    public String getEndProcessDate()
    {
        return endProcessDate;
    }
    
    //#CM643339
    /**
     * Set Retrieval end time. 
     * @return Retrieval end time
     */
    public String getEndProcessTime()
    {
        return endProcessTime;
    }
    
    //#CM643340
    /**
     * Set Display flag. 
     * @param value Display flag
     */
    public void setDisplayCondition(int value)
    {
        displayCondition = value;
    }
    
    //#CM643341
    /**
     * Acquire Display flag. 
     * @return Display flag
     */
    public int getDisplayCondition()
    {
        return displayCondition;
    }
    
    //#CM643342
    /**
     * Communication trace log data is acquired, and it agrees with the specified condition. 
     * Set the Communication trace log data in the Communication trace log data class.
     * Set it in the list class. 
     * @return Communication trace log list class
	 * @throws Exception It reports on all Exception. 
     */
    public TraceList getTraceData() throws Exception
    {
        //#CM643343
        // Initialization of writing counter
        int count = 0;
        
        int maxDisplay = MAX_DISPLAY;

        try
        {
            maxDisplay = LogViewerParam.MaxLineCnt;
        }
        catch (NumberFormatException e)
        {
        }
        
        //#CM643344
        // Trace file storage variable
        RandomAcsFileHandler traceFile = null;
        
        TraceList cFile = null;
        
        try
        {
            //#CM643345
            // Making of file name
            traceFileName = LogViewerParam.RftLogPath + "\\RFT" + rftNo + TraceLogFileName;
            
            //#CM643346
            // Reading of file
            traceFile = new RandomAcsFileHandler(traceFileName, TRACE_FILE_MAX_SIZE);
            
            //#CM643347
            // File reading end position acquisition
            int readEnd = traceFile.getSizeUTF();
            
            //#CM643348
            // Error processing when ReadEnd is negative
            if (readEnd < 0)
            {
                String param[] = new String[1];
                param[0] = "RFT" + rftNo + TraceLogFileName;
                String msg = MessageResourceFile.getText("6006020");
                msg = MessageFormat.format(msg, param);
                throw new Exception(msg);
            }
            else if(readEnd == 0)
            {
            	throw new Exception("6003011");
            }
            
            //#CM643349
            // The file is opened. 
            boolean openFlg = true;
    
            if (traceFile.readOpenUTF(openFlg) == false)
            {
            	throw new Exception("6003011");
            }
            
            //#CM643350
            // Storage Vector in file
            Vector vec = new Vector();
            vec = traceFile.readUTF(READ_START, readEnd);
            
            //#CM643351
            // The content of the file is set in LogMessage type Array. 
            LogMessage[] msg = new LogMessage[vec.size()];
            vec.copyInto(msg);
 
            //#CM643352
            // Close of file
            traceFile.readClose();
            
            //#CM643353
            // Flag which judges whether pertinent number exceeded The maximum display qty
            boolean isOverMaxDisplay = false;
            
            cFile = new TraceList(msg.length);
            
            //#CM643354
            // When the date is Blank
            if (startProcessDate == null)
            {
                startProcessDate = "";
            }
            if (startProcessTime == null)
            {
                startProcessTime = "";
            }
            if (endProcessDate == null)
            {
                endProcessDate = "";
            }
            if (endProcessTime == null)
            {
                endProcessTime = "";
            }
            
            //#CM643355
            // Start date and End date are acquired. 
            java.util.Date startDate = getDate(startProcessDate, startProcessTime, true);
            java.util.Date endDate = getDate(endProcessDate, endProcessTime, false);

			//#CM643356
			// Replace it with Blank for null. 
			if (idNo1 == null)
			{
				idNo1 = "";
			}
			if (idNo2 == null)
			{
				idNo2 = "";
			}

           //#CM643357
           // It must loop by the size of Array. 
            for (int i = 0; i < msg.length ; i++)
            {

                //#CM643358
                // Securing of data in Array
                //#CM643359
                // Writing time
                java.util.Date writeDate = msg[i].getDate();
                
                //#CM643360
                // Sending and receiving flag
                String sendReceiveFlag = msg[i].getClassInfo();
                
                //#CM643361
                // Transmission
                byte[] communicateMessage = null;
                
                //#CM643362
                //Work Buffer generation
                byte[] byteBuf = msg[i].getMessage().getBytes();
                
                //#CM643363
                // Conversion STX and ETX each "[" and "]"
                if (byteBuf.length != 0)
                {

                    for (int j =0; j < byteBuf.length; j++)
                    {
                        //#CM643364
                        // When STE is found
                        if (byteBuf[j] == STX)
                        {
                            byteBuf[j] = START_BYTE;
                        }
                        if (byteBuf[j] == ETX)
                        {
                            byteBuf[j] = END_BYTE;
                            communicateMessage = byteBuf;
                            break;
                        }
                        communicateMessage = byteBuf;
                    }
                    
                    //#CM643365
                    // Variable for sending and receiving display
                    String sendReceive = "";
                
                    //#CM643366
                    // Process Continuation Flag
                    boolean next = false;
                    
                    //#CM643367
                    // Sending and receiving check
                    //#CM643368
                    // All Display
                    if (displayCondition == 1)
					{
						//#CM643369
						// Continuation FlagON
						next = true;
					}

                    //#CM643370
                    // Only the transmission is displayed. 
                    else if(displayCondition == 2)
                    {
                        //#CM643371
                        // The value is set in the variable for the sending and receiving display. 
                        if(sendReceiveFlag.equals("S"))
                        {
                            //#CM643372
                            // Continuation FlagON
                            next = true;
                        }
                        else
                        {
                            //#CM643373
                            // Continuation FlagOFF
                            next = false;
                        }
                    }
                    //#CM643374
                    // Only the reception is displayed. 
                    else if(displayCondition == 3)
                    {
                        //#CM643375
                        // The value is set in the variable for the sending and receiving display. 
                        if(sendReceiveFlag.equals("S"))
                        {
                            //#CM643376
                            // Continuation FlagOFF
                            next = false;
                        }
                        else
                        {
                            //#CM643377
                            // Continuation FlagON
                            next = true;
                        }
                    }

                    //#CM643378
                    // Advance to the following processing when continuation Flag is turning on. 
                    if (next == true)
                    {
                        //#CM643379
                        // Check at date
                        if (startDate != null)
                        {
                        	if (endDate == null)
                        	{
                        		//#CM643380
                        		// When only Start date is input
                                if(startDate.compareTo(writeDate) <= 0)
                                {
                                    //#CM643381
                                    // Continuation FlagON
                                    next = true;
                                }
                                else
                                {
                                    //#CM643382
                                    // Continuation FlagOFF
                                    next = false;
                                }
                        	}
                        	else
                        	{
                        		//#CM643383
                        		// When both Start date and End date are input
                        		if(startDate.compareTo(writeDate) <= 0 && endDate.compareTo(writeDate) > 0)
                                {
                                    //#CM643384
                                    // Continuation FlagON
                                    next = true;
                                }
                                else
                                {
                                    //#CM643385
                                    // Continuation FlagOFF
                                    next = false;
                                }
                        	}
                        }
                        else
                        {
                        	if (endDate == null)
                        	{
                                //#CM643386
                                // When there is no date input
                                next = true;
                        	}
                        	else
                        	{
                        		//#CM643387
                        		// When only End date is input
                                if(endDate.compareTo(writeDate) > 0)
                                {
                                    //#CM643388
                                    // Continuation FlagON
                                    next = true;
                                }
                                else
                                {
                                    //#CM643389
                                    // Continuation FlagOFF
                                    next = false;
                                }
                        	}
                        }
                    }
                        
                    //#CM643390
                    // Acquire the identification number from the cable. 
                	byte idbuf[] = new byte[IdLength];
                	for (int j = 0; j < IdLength; j ++)
                	{
                		idbuf[j] = communicateMessage[j + 5];
                	}
                	String id = new String (idbuf);

                	//#CM643391
                	// Advance to the following processing when continuation Flag is turning on. 
                    if (next == true)
					{
						//#CM643392
						// Check on idNo
						if (idNo1.length() != 0)
						{
							if (id.equals(idNo1) || id.equals(idNo2))
							{
								next = true;
							}
							else
							{
								next = false;
							}
						}
						else if (idNo2.length() != 0)
						{
							if (id.equals(idNo2))
							{
								next = true;
							}
							else
							{
								next = false;
							}
							
						}
						else
						{
							next = true;
						}
					}

                    //#CM643393
                    // The continuation flag is turned on. 
                    if (next == true)
                    {
                        //#CM643394
                        // Conversion of sending and receiving flag
                        sendReceive = sendReceiveFlag;

                        if (!isOverMaxDisplay)
                        {
                            //#CM643395
                            // One plus to the writing counter. 
                            count++;
                            
                            //#CM643396
                            // When it writes and the maximum number is exceeded
                            if (count > maxDisplay)
                            {
                                isOverMaxDisplay = true;
                            }
                            else
                            {
                                
                                //#CM643397
                                // The display data is maintained. 
                                TraceData data = new TraceData();
                                data.setProcessDate(DateOperator.changeDate(writeDate));
                                data.setProcessTime(DateOperator.changeDateTimeMillis(writeDate));
                                data.setSendRecvDivision(sendReceive);
                                data.setIdNo(new String(id));
                                data.setCommunicateMessage(communicateMessage);
                                
                                cFile.add(data);
                            }
                        }
                        else
                        {
                            LogViewer.setTraceLogFileDispCnt(count);
                            return cFile;
                        }
                     }
                }
                else
                {
                    LogViewer.setTraceLogFileDispCnt(count);
                    return cFile;
                }
            }
            LogViewer.setTraceLogFileDispCnt(count);
            if (count == 0)
            {
            	//#CM643398
            	// Return the object data none when as a result of the retrieval, there is no pertinent data. 
            	throw new Exception("6003011");
            }

            return cFile;
        }
        catch (EOFException e1)
        {
            String param[] = null;
            param = new String[1];
            param[0] = "RFT" + rftNo + TraceLogFileName;
            String msg = "";
            msg = MessageResourceFile.getText("6006020");
            msg = MessageFormat.format(msg, param);
            throw new Exception(msg);
        }
        catch (IOException e1) 
        {
            //#CM643399
            // Display error Message in the dialog. 
            e1.printStackTrace();
        }
        
        return null;
    }

    //#CM643400
    /**
     * Make java.util.Date from String type (YYYYMMDD) and String type (HHMISS).  <BR>
     * Use it to retrieve the date of the trace inquiry. <BR>
     * When dateFlag of the parameter is True
     *   As it is at the date when both inputting it at the second of the season. <BR>
     *   Input date + 00:00:00 when inputting it only at date<BR>
     *   Return null both at the uninput at the second of the season at the date. <BR>
     * When dateFlag of the parameter is False
     *   Date and second of season Current Date + input + one second when both inputting it at second of season<BR>
     *   Input date + day + 00:00:00 when inputting it only at date<BR>
     *   Return null both at the uninput at the second of the season at the date. 
     * @param  date Date
     * @param  time Time
     * @param  dateFlag Date Flag True:Start date False:End date
     * @return  java.util.Date
	 * @throws Exception It reports on all Exception.
     */
    public static Date getDate(String date, String time, boolean dateFlag) throws Exception
    {
        java.util.Date wDate = null;
        
        //#CM643401
        // The date is not Blank.
        if (date.length() != 0)
		{
			//#CM643402
			// The second of the season is not Blank. 
			if (time.length() != 0)
			{
				//#CM643403
				// At all input
				if (date.length() == 8 && time.length() == 6)
				{
					GregorianCalendar wCalendar =
						new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
												Integer.parseInt(date.substring(4, 6)) - 1,
												Integer.parseInt(date.substring(6, 8)),
												Integer.parseInt(time.substring(0, 2)),
												Integer.parseInt(time.substring(2, 4)),
												Integer.parseInt(time.substring(4, 6)));
					if (!dateFlag)
					{
						wCalendar.add(Calendar.SECOND, 1);
					}
					wDate = wCalendar.getTime();
				}
			}
			//#CM643404
			//The second of the season is Blank. 
			else
			{
				if (date.length() == 8)
				{
					GregorianCalendar wCalendar =
						new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
												Integer.parseInt(date.substring(4, 6)) - 1,
												Integer.parseInt(date.substring(6, 8)),
												0, 0, 0);
					if (!dateFlag)
					{
						wCalendar.add(Calendar.DATE, 1);
					}
					wDate = wCalendar.getTime();
				}
			}
		}
		//#CM643405
		// The date is Blank. 
		else
		{
			//#CM643406
			// The second of the season is not Blank. 
			if (time.length() != 0)
			{
				throw new Exception("6023180");
			}
		}
		return wDate;
    }
}
