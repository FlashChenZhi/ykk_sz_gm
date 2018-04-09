package jp.co.daifuku.wms.base.common.tool.logViewer;
//#CM642863
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.text.MessageFormat;
import java.util.Vector;
import java.io.EOFException;
import java.io.IOException;

import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RandomAcsFileHandler;


//#CM642864
/**
 * Acquire one communication trace log information. <BR>
 * From Search condition specified on the communication trace log list screen
 * Retrieve information to be displayed,Set it in the Communication trace log data class. 
 * From the Communication trace log data class to the communication trace log list class
 * Hand over information. 
 * 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:47:53 $
 * @author  $Author: suresh $
 */

public class As21TraceLogFile extends TraceLogFile
{
    //#CM642865
    // Class fields --------------------------------------------------
    //#CM642866
    /**
     * Trace log File name for sending Transmission
     */
    protected final static String SendTraceLogFileName = "sendTrace.txt";

    //#CM642867
    /**
     * Trace log File name for reception Transmission
     */
    protected final static String RecvTraceLogFileName = "receiveTrace.txt";

    //#CM642868
    /**
     * Number of digits of ID of default
     */
    protected final static int DefaultIdFigure = 2;

    //#CM642869
    //-----------------
    //#CM642870
    //Class Method
    //#CM642871
    //-----------------
    public As21TraceLogFile()
    {
        super();
    }

    //#CM642872
    /**
     * Communication trace log data is acquired, and it agrees with the specified condition. 
     * Set Communication Trace log data in the Communication trace log data class.
     * Set it in the list class. 
     * 
     * @return Communication trace log list
	 * @throws Exception It reports on all Exception. 
     */
    public TraceList getTraceData() throws Exception
    {
    	TraceList sendList = null;
    	TraceList recvList = null;
    	String fileName = null;
    	
        //#CM642873
        // Making of file name
    	if (displayCondition == 1 || displayCondition == 2)
    	{
            fileName = rftNo + LogViewerParam.SendTraceLogFileName;
            sendList = getTraceData(fileName);
    	}
    	
    	if (displayCondition == 1 || displayCondition == 3)
    	{
            fileName = rftNo + LogViewerParam.RecvTraceLogFileName;
            recvList = getTraceData(fileName);
    	}

    	return merge(sendList, recvList);
    }

    //#CM642874
    /**
     * Communication trace log data is acquired, and it agrees with the specified condition. 
     * Set Communication Trace log data in the Communication trace log data class.
     * Set it in the list class. 
     * 
     * @param fileName File name
     * @return Communication trace log data
	 * @throws Exception It reports on all Exception. 
     */
    public TraceList getTraceData(String fileName) throws Exception
    {
        //#CM642875
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
        
        //#CM642876
        // Trace file storage variable
        RandomAcsFileHandler traceFile = null;
        
        TraceList cFile = null;
        
        try
        {
            //#CM642877
            // Making of file name
            traceFileName = LogViewerParam.RftLogPath + "\\" + fileName;
            
            //#CM642878
            // Reading of file
            traceFile = new RandomAcsFileHandler(traceFileName, TRACE_FILE_MAX_SIZE);
            
            //#CM642879
            // File reading end position acquisition
            int readEnd = traceFile.getSize();
            
            //#CM642880
            // Error processing when ReadEnd is negative
            if (readEnd < 0)
            {
                String param[] = {fileName};
                String msg = MessageResourceFile.getText("6006020");
                msg = MessageFormat.format(msg, param);
                throw new Exception(msg);
            }
            else if(readEnd == 0)
            {
            	return null;
            }
            
            //#CM642881
            // The file is opened. 
            boolean openFlg = true;
    
            if (traceFile.readOpen(openFlg) == false)
            {
            	throw new Exception("6003011");
            }
            
            //#CM642882
            // Storage Vector in file
            Vector vec = new Vector();
            vec = traceFile.read(READ_START, readEnd);
            
            //#CM642883
            // The content of the file is set in LogMessage type Array. 
            LogMessage[] msg = new LogMessage[vec.size()];
            vec.copyInto(msg);
 
            //#CM642884
            // Close of file
            traceFile.readClose();
            
            //#CM642885
            // Flag which judges whether pertinent number exceeded The maximum display qty
            boolean isOverMaxDisplay = false;
            
            cFile = new TraceList(msg.length);
            
            //#CM642886
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
            
            //#CM642887
            // Start date and End date are acquired. 
            java.util.Date startDate = getDate(startProcessDate, startProcessTime, true);
            java.util.Date endDate = getDate(endProcessDate, endProcessTime, false);

			//#CM642888
			// Replace it with Blank for null. 
			if (idNo1 == null)
			{
				idNo1 = "";
			}
			if (idNo2 == null)
			{
				idNo2 = "";
			}

           //#CM642889
           // It must loop by the size of Array. 
            for (int i = 0; i < msg.length ; i++)
            {

                //#CM642890
                // Securing of data in Array
                //#CM642891
                // Writing time
                java.util.Date writeDate = msg[i].getDate();
                
                //#CM642892
                // Sending and receiving flag
                String sendReceiveFlag = msg[i].getClassInfo();
                
                //#CM642893
                // Transmission
                byte[] communicateMessage = null;
                
                //#CM642894
                // Work Buffer generation
                byte[] byteBuf = msg[i].getMessage().getBytes();
                
                //#CM642895
                // Conversion STX and ETX each "[" and "]"
                if (byteBuf.length != 0)
                {

                    for (int j =0; j < byteBuf.length; j++)
                    {
                        //#CM642896
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
                    
                    //#CM642897
                    // Variable for sending and receiving display
                    String sendReceive = "";
                
                    //#CM642898
                    // Process Continuation Flag
                    boolean next = false;

                    //#CM642899
                    // Check at date
                    if (startDate != null)
                    {
                    	if (endDate == null)
                    	{
                    		//#CM642900
                    		// When only Start date is input
                            if(startDate.compareTo(writeDate) <= 0)
                            {
                                //#CM642901
                                // Continuation FlagON
                                next = true;
                            }
                            else
                            {
                                //#CM642902
                                // Continuation FlagOFF
                                next = false;
                            }
                    	}
                    	else
                    	{
                    		//#CM642903
                    		// When both Start date and End date are input
                    		if(startDate.compareTo(writeDate) <= 0 && endDate.compareTo(writeDate) > 0)
                            {
                                //#CM642904
                                // Continuation FlagON
                                next = true;
                            }
                            else
                            {
                                //#CM642905
                                // Continuation FlagOFF
                                next = false;
                            }
                    	}
                    }
                    else
                    {
                    	if (endDate == null)
                    	{
                            //#CM642906
                            // When there is no date input
                            next = true;
                    	}
                    	else
                    	{
                    		//#CM642907
                    		// When only End date is input
                            if(endDate.compareTo(writeDate) > 0)
                            {
                                //#CM642908
                                // Continuation FlagON
                                next = true;
                            }
                            else
                            {
                                //#CM642909
                                // Continuation FlagOFF
                                next = false;
                            }
                    	}
                    }
                        
                    //#CM642910
                    // Acquire the identification number from the cable. 
                	byte idbuf[] = new byte[IdLength];
                	for (int j = 0; j < IdLength; j ++)
                	{
                		idbuf[j] = communicateMessage[j + 4];
                	}
                	String id = new String (idbuf);

                	//#CM642911
                	// Advance to the following processing when continuation Flag is turning on. 
                    if (next == true)
					{
						//#CM642912
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

                    //#CM642913
                    // The continuation flag is turned on. 
                    if (next == true)
                    {
                        //#CM642914
                        // Conversion of sending and receiving flag
                        sendReceive = sendReceiveFlag;

                        if (!isOverMaxDisplay)
                        {
                            //#CM642915
                            // One plus to the writing counter. 
                            count++;
                            
                            //#CM642916
                            // When it writes and the maximum number is exceeded
                            if (count > maxDisplay)
                            {
                                isOverMaxDisplay = true;
                            }
                            else
                            {
                                
                                //#CM642917
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
            	return null;
            }

            return cFile;
        }
        catch (EOFException e1)
        {
            String param[] = {fileName};
            String msg = "";
            msg = MessageResourceFile.getText("6006020");
            msg = MessageFormat.format(msg, param);
            throw new Exception(msg);
        }
        catch (IOException e1) 
        {
            //#CM642918
            // Display error Message in the dialog. 
            e1.printStackTrace();
        }
        
        return null;
    }
    
    //#CM642919
    /**
     * Merge the transmission data and receive data. 
     * 
     * @param sendData Transmission data
     * @param recvData Receive data
     * @return Communication trace log list
	 * @throws Exception It reports on all Exception. 
     */
    public TraceList merge(TraceList sendData, TraceList recvData) throws Exception
    {
    	//#CM642920
    	// The merged list returns it as it is when there are only one. 
    	if (sendData == null)
    	{
    		if (recvData == null)
    		{
            	//#CM642921
            	// Return the object data none when as a result of the retrieval, there is no pertinent data. 
            	throw new Exception("6003011");
    		}

			for (int i = 0; i < recvData.getSize(); i ++)
			{
				recvData.getTraceData(i).setSendRecvDivision("R");
			}
			return recvData;
    	}
    	else if (recvData == null)
    	{
			for (int i = 0; i < sendData.getSize(); i ++)
			{
				sendData.getTraceData(i).setSendRecvDivision("S");
			}
    		return sendData;
    	}

    	TraceList mergedList = new TraceList();
    	int sendIndex = 0;
    	int recvIndex = 0;
    	int sendDataSize = sendData.getSize();
    	int recvDataSize = recvData.getSize();
    	
        int maxDisplay = LogViewerParam.MaxLineCnt;

        for (int i = 0; i < maxDisplay; i ++)
    	{
    		TraceData send = null;
    		TraceData recv = null;
    		if (sendIndex < sendDataSize)
    		{
        		send = sendData.getTraceData(sendIndex);
    			send.setSendRecvDivision("S");
    		}
    		if (recvIndex < recvDataSize)
    		{
        		recv = recvData.getTraceData(recvIndex);
    			recv.setSendRecvDivision("R");
    		}

    		if (send == null && recv == null)
    		{
    			//#CM642922
    			// End the merge only application when there is no data even in each list either. 
    			break;
    		}

    		if (send == null)
    		{
    			mergedList.add(recv);
    			recvIndex ++;
    			continue;
    		}
    		else if (recv == null)
    		{
    			mergedList.add(send);
    			sendIndex ++;
    			continue;
    		}
    		
    		if (send.getProcessDate().compareTo(recv.getProcessDate()) > 0)
    		{
    			mergedList.add(send);
    			sendIndex ++;
    		}
    		else if (send.getProcessDate().compareTo(recv.getProcessDate()) == 0)
    		{
    			if (send.getProcessTime().compareTo(recv.getProcessTime()) > 0)
    			{
        			mergedList.add(send);
        			sendIndex ++;
    			}
    			else
    			{
        			mergedList.add(recv);
        			recvIndex ++;
    			}
    		}
    		if (send.getProcessDate().compareTo(recv.getProcessDate()) < 0)
    		{
    			mergedList.add(recv);
    			recvIndex ++;
    		}
    	}

    	return mergedList;
    }
}
