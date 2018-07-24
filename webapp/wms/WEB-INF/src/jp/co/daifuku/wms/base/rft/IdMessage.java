// $Id: IdMessage.java,v 1.2 2006/11/14 06:09:07 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701702
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingDataHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingDataSearchKey;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM701703
/**
 * Assemble and decompose telegram common part in RFT communication
 * Utility Method is offered, and a super-class. <BR>
 * Use the subclass of each ID actually the telegram assembly and to decompose. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>K.Nishiura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:07 $
 * @author  $Author: suresh $
 */
public class IdMessage extends Object implements DataColumn
{

	//#CM701704
	// Class fields --------------------------------------------------

	protected static final byte DEF_STX = 0x02;
	protected static final byte DEF_ETX = 0x03;

	//#CM701705
	/**
	 * Definition of offset of STX
	 */
	static final int OFF_STX = 0;
	//#CM701706
	/**
	 * Definition of offset of SEQ
	 */
	public static final int OFF_SEQ = 1;

	//#CM701707
	/**
	 * Definition of offset of telegramID
	 */
	static final int OFF_ID = 5;

	//#CM701708
	/**
	 * Definition of offset of RFT transmission time
	 */
	static final int OFF_RFTSENDDATE = OFF_ID + LEN_ID;

	//#CM701709
	/**
	 * Definition of offset of SERVER transmission time
	 */
	public static final int OFF_SERVSENDDATE = OFF_RFTSENDDATE + LEN_RFTSENDDATE;

	//#CM701710
	/**
	 * Definition of offset of RFT machine
	 */
	public static final int OFF_RFTNO = OFF_SERVSENDDATE + LEN_SERVSENDDATE;

	//#CM701711
	/**
	 * Definition of offset of content information
	 */
	static final int OFF_CONTENT = OFF_RFTNO + LEN_RFTNO;

	//#CM701712
	/**
	 * The total length of header
	 */
	public static final int LEN_HEADER = OFF_CONTENT;
	
	//#CM701713
	/**
	 * Maximum length of telegram
	 */
	static final int LEN_MAX_PACKET = 1024;

	//#CM701714
	/**
	 * Maximum length of Data
	 */
	static final int LEN_MAX_CONTENT = LEN_MAX_PACKET - LEN_HEADER;

	
	//#CM701715
	// Class variables -----------------------------------------------
	//#CM701716
	/**
	 * Telegram buffer
	 */
	protected byte[] wDataBuffer = new byte[LEN_MAX_PACKET];

	//#CM701717
	/**
	 * Telegram buffer
	 */
	protected byte[] wLocalBuffer = null;

	//#CM701718
	/**
	 * Format definition of transmission time
	 */
	private SimpleDateFormat SEND_TIME_FORMAT = new SimpleDateFormat("HHmmss");
	
	//#CM701719
	/**
	 * Offset of ETX
	 */
	protected int offEtx = 0;

	//#CM701720
	/**
	 * Length of telegram
	 */
	protected int length = 0;

	//#CM701721
	/**
	 * Encode definition
	 */
	public static final String ENCODE = "Shift_JIS";

	//#CM701722
	/**
	 * List of ID to send and receive work Data with file
	 */
	protected static final String[] WorkFileList = {"132", "140", "330", "430", "532"};
	
	
	//#CM701723
	// Class method --------------------------------------------------
	//#CM701724
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:07 $";
	}

	//#CM701725
	/**
	 * Check whether Response telegram of specified RFT machine No exists in RFTWork information. 
	 * @param rftNo RFT machine No
	 * @param conn Connection object with data base
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public static boolean checkResponseId(String rftNo, Connection conn) throws ReadWriteException
	{
		//#CM701726
		// RFTWork information is retrieved. 
		RftSearchKey skey = new RftSearchKey();
		RftHandler handler = new RftHandler(conn);
		
		skey.setRftNo(rftNo);
		skey.setResponseId("", "IS NOT NULL");
		
		if (handler.count(skey) > 0)
		{
			return true;
		}
		
		return false;
	}

	//#CM701727
	/**
	 * Retrieve RFTWork information contingent on RFT machine No and acquire the telegram item. <BR>
	 * Generate response telegram with the acquired value, and return the instance. 
	 * 
	 * @param rftNo RFT machine No
	 * @param conn Connection object with data base
	 * @return	Object of read telegram
	 * @throws IllegalAccessException	It is notified when failing in the instance generation of telegram.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NoPrimaryException It is notified when definition information is abnormal. 
	 */
	public static IdMessage loadMessageFile(String rftNo, Connection conn) throws IllegalAccessException, ReadWriteException, NoPrimaryException
	{
		//#CM701728
		// Retrieve RFTNo and retrieve RFTWork information to the key. 
		RftHandler rHandler = new RftHandler(conn);
		RftSearchKey rkey = new RftSearchKey();
		//#CM701729
		//----------
		//#CM701730
		// Search condition
		//#CM701731
		//----------
		
		rkey.setRftNo(rftNo);
		
		Rft rft = new Rft();
		rft = (Rft)rHandler.findPrimary(rkey);
		
		byte[] buffer = new byte[LEN_MAX_PACKET];
		
		buffer = rft.getResponseId().getBytes();
		
		if(buffer != null && buffer.length > 0)
		{
			IdMessage msg = new IdMessage(buffer);
			
			String id = msg.getID();
			IdMessage responseMessage
				= (IdMessage) PackageManager.getObject("RFTId" + id);
			responseMessage.setReceiveMessage(buffer);
		
			return responseMessage;
		}
		return null;
	}	
	
	//#CM701732
	/**
	 * Do the deletion processing of Working data. <BR>
	 * <BR>
	 * <OL>
	 * <LI>Update the telegram item of RFTWork information to NULL. <BR>
	 * Update the telegram item to NULL when RFTWork information is retrieved contingent on RFT machine No which <BR>
	 * does Acquisition from parameter, and correspondence Data exists. </LI>
	 * <LI>Delete pertinent Record of Working data preservation information. <BR>
	 * Delete it when Working data preservation information is retrieved contingent on RFT machine No which does Acquisition from parameter and correspondence Data exists. </LI>
	 * </OL>
	 * <BR>
	 * @param rftNo RFT machine No
	 * @param className Last update processing name
	 * @param conn Connection object with data base
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws InvalidDefineException The specified value is notified. 
	 */
	public static void deleteWorkingData(String rftNo, String className, Connection conn) throws ReadWriteException, InvalidDefineException
	{
		//#CM701733
		// Retrieve RFTNo and retrieve RFTWork information to the key. 
		RftHandler rHandler = new RftHandler(conn);
		RftSearchKey rkey = new RftSearchKey();

		//#CM701734
		//----------
		//#CM701735
		// Search condition
		//#CM701736
		//----------		
		rkey.setRftNo(rftNo);
		rkey.setResponseId("", "IS NOT NULL");

		RftAlterKey akey = new RftAlterKey();
		akey.setRftNo(rftNo);
		akey.updateLastUpdatePname(className);
		akey.updateResponseId(null);
		try
		{
			//#CM701737
			// Update the telegram item of RFT administrative information to NULL. 
			rHandler.modify(akey);
		}
		catch (NotFoundException e)
		{
			//#CM701738
			// Do not do anything when update Data does not exist. 
		}
		//#CM701739
		// Retrieve RFTNo from key in Working data preservation information.
		WorkingDataHandler wHandler = new WorkingDataHandler(conn);
		WorkingDataSearchKey wkey = new WorkingDataSearchKey();
				
		//#CM701740
		//----------
		//#CM701741
		// Search condition
		//#CM701742
		//----------		
		wkey.setRftNo(rftNo);
		try
		{
			//#CM701743
			// Delete it when Working data exists. 
			if (wHandler.count(wkey) > 0)
			{
				//#CM701744
				// Delete Working data Record of Working data preservation information. 
				wHandler.drop(wkey);
			}
		}
		catch (NotFoundException e)
		{
			//#CM701745
			// Do not do anything when deletion Data does not exist. 
			return;
		}

		
	}
	
	//#CM701746
	/**
	 * It is judged whether it is the one of Work type and Detail Work type from which specific ID is specified. <BR>
	 * Return false when not agreeing and true when agreeing. 
	 * 
	 * @param id ID
	 * @param workType Work type
	 * @param workDetails Detail Work type
	 * @return	True when corresponding work is specified, false otherwise.
	 */
	public static boolean checkJobType(String id, String workType, String workDetails)
	{
		if (id == null || id.equals("")){
			return false;
		}
		
		if (workDetails == null)
		{
			//#CM701747
			// Replace it with empty Character string so as not to generate NullPointerException when comparing it. 
			workDetails = "";
		}

		//#CM701748
		// Receiving 
		if (workType.equals(WorkingInformation.JOB_TYPE_INSTOCK))
		{
			if (workDetails.equals(WorkDetails.INSTOCK_CUSTOMER))
			{
				if (id.substring(1).equals("140"))
				{
					return true;
				}
			}
			else if (workDetails.equals(WorkDetails.INSTOCK_SUPPLIER)
					|| workDetails.equals(WorkDetails.INSTOCK_ITEM))
			{
				if (id.substring(1).equals("130")
						|| id.substring(1).equals("132"))
				{
					return true;
				}
			}
		}
	    //#CM701749
	    // Shipping 
		else if (workType.equals(WorkingInformation.JOB_TYPE_SHIPINSPECTION))
		{
			if (id.substring(1).equals("530")
					|| id.substring(1).equals("532"))
			{
				return true;
			}
		}
	    //#CM701750
	    // Sorting
		else if (workType.equals(WorkingInformation.JOB_TYPE_SORTING))
		{
			if (id.substring(1).equals("430"))
			{
				return true;
			}
		}
	    //#CM701751
	    // Storage
		else if (workType.equals(WorkingInformation.JOB_TYPE_STORAGE))
		{
			if (id.substring(1).equals("230"))
			{
				return true;
			}
		}
	    //#CM701752
	    // Picking 
		else if (workType.equals(WorkingInformation.JOB_TYPE_RETRIEVAL))
		{
			if (workDetails.equals(WorkDetails.RETRIEVAL_ORDER))
			{
				if (id.substring(1).equals("330"))
				{
					return true;
				}
			}
			else if (workDetails.equals(WorkDetails.RETRIEVAL_ITEM))
			{
				if (id.substring(1).equals("340"))
				{
					return true;
				}
			}
		}
	    //#CM701753
	    // Movement Storage
		else if (workType.equals(WorkingInformation.JOB_TYPE_MOVEMENT_STORAGE))
		{
			if (id.substring(1).equals("740"))
			{
				return true;
			}
		}
	    
	    return false;
	}

	//#CM701754
	/**
	 * Delete Working data of Terminal which does the maintained work. <BR>
	 * Retrieve RFTWork information contingent on Title machine  No. of the Terminal list received by the parameter and acquire the telegram item. <BR>
	 * At this time, check whether it is the one which corresponds to Work type for which ID of Working data is specified and Detail Work type when ID is acquired 
	 * from acquired telegram, and deletes it. <BR>
	 * Update the telegram item of RFTWork information to NULL when corresponding. <BR>
	 * Moreover, delete the Record when Working data preservation information is retrieved contingent on Title machine No and correspondence Data exists. <BR>
	 * Do not delete Working data which does not correspond. 
	 * <BR>
	 * (X) PackageManager is done before this call of Method and it is necessary to do Initialization. 
	 * 
	 * @param	terminalList	List of Terminal which does work that state is changed while working
	 * @param	workType		Work type
	 * @param	workDetails		Detail Work type
	 * @param 	className 		Last update processing name
	 * @param 	conn 			Connection object with data base
	 * @throws InvalidDefineException The specified value is notified. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public static void deleteWorkingDataFile(ArrayList terminalList, String workType, String workDetails, String className, Connection conn) throws ReadWriteException, InvalidDefineException
	{
		for (int i = 0; i < terminalList.size(); i ++)
		{
			//#CM701755
			// Acquire RFT machine No. 
			String rftNo = (String) terminalList.get(i);

			//#CM701756
			// When TerminalNo is set
			if (! rftNo.equals(""))
			{
				//#CM701757
				// Acquire ID of Working data. 
				String id = "";
				id = IdMessage.getWorkingDataId(rftNo, conn);
				
				//#CM701758
				// When ID is Receiving
				if (checkJobType(id, workType, workDetails))
				{
					//#CM701759
					// Call the ID message class deletion Method. 
					//#CM701760
					// Delete Working data. 
					//#CM701761
					// It moves to the processing of the following Data when the file does not exist. 
					deleteWorkingData(rftNo, className, conn);
				}
			}
		}
	}

	//#CM701762
	/**
	 * Return Work type of specified ID. 
	 * 
	 * @param id	ID
	 * @return		True in case of ID which sends and receives work Data file, False otherwise
	 */
	public static boolean hasWorkDataFile(String id)
	{
		if (id == null || id.equals(""))
		{
			return false;
		}

		for (int i = 0 ;i < WorkFileList.length;i++)
	    {
    		if (id.substring(1).equals(WorkFileList[i]))
    		{
    			return true;
    		}
	    }

	    return false;
	}

	//#CM701763
	/**
	 * Acquire ID under work of the specified title machine. 
	 * Return empty Character string if Data under work does not exist. 
	 * 
	 * @param rftNo		Title machine No
	 * @param conn		Connection object with data base
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public static String getWorkingDataId(String rftNo, Connection conn) throws ReadWriteException
	{
		//#CM701764
		// Retrieve RFT Work information and acquire the ID name. 
		RftSearchKey wkey = new RftSearchKey();
		RftHandler wHandler = new RftHandler(conn);
		
		wkey.setRftNo(rftNo);
		wkey.setResponseId("", "IS NOT NULL");
		
		Rft[] rft = (Rft[])wHandler.find(wkey);
		
		if (rft == null || rft.length == 0)
		{
			return "";
		}
					
		//#CM701765
		// Acquire ID Number from the name of the Working data file. 
		byte[] buffer = new byte[LEN_MAX_PACKET];
		
		buffer = rft[0].getResponseId().getBytes();
		
		IdMessage msg = new IdMessage(buffer);
		
		return msg.getID();
	}

	//#CM701766
	// Constructors --------------------------------------------------

	//#CM701767
	/**
	 * Generate the instance. An internal buffer is cleared by ' '.
	 */
	public IdMessage()
	{
		//#CM701768
		// Clear the buffer.
		for (int i = 0; i < wDataBuffer.length; i++)
		{
			wDataBuffer[i] = ' ';
		}
	}
	//#CM701769
	/**
	 * Generate the instance. 
	 * @param	dt	Telegram received from RFT
	 */
	public IdMessage(byte[] dt)
	{
		super();
		setReceiveMessage(dt);
	}

	//#CM701770
	// Public methods ------------------------------------------------
	//#CM701771
	/**
	 * Acquire ID. 
	 * @return   telegramID
	 */
	public String getID()
	{
		return (getFromBuffer(OFF_ID, LEN_ID));
	}

	//#CM701772
	/**
	 * Acquire RFT Transmission time. 
	 * @return Transmission time
	 */
	public Date getRftSendDate()
	{
		String wdt = getFromBuffer(OFF_RFTSENDDATE, LEN_RFTSENDDATE);
		return (SEND_TIME_FORMAT.parse(wdt, new ParsePosition(0)));
	}

	//#CM701773
	/**
	 * Acquire SERV transmission time. 
	 * @return SERV transmission time
	 */
	public Date getServSendDate()
	{
		String wdt = getFromBuffer(OFF_SERVSENDDATE, LEN_SERVSENDDATE);
		return (SEND_TIME_FORMAT.parse(wdt, new ParsePosition(0)));
	}

	//#CM701774
	/**
	 * Acquire RFT machine. 
	 * @return RFT machine
	 */
	public String getRftNo()
	{
		return (getFromBuffer(OFF_RFTNO, LEN_RFTNO));
	}

	//#CM701775
	/**
	 * Set STX.
	 */
	public void setSTX()
	{
		setToByteBuffer(DEF_STX, OFF_STX);
	}
	//#CM701776
	/**
	 * Set SEQ
	 * @param seq Set SEQ
	 */
	public void setSEQ(int seq)
	{
		setToBuffer(Integer.toString(seq), OFF_SEQ);
	}

	//#CM701777
	/**
	 * Set ID. 
	 * @param id Set telegramID
	 */
	public void setID(String id)
	{
		setToBuffer(id, OFF_ID);
	}
	//#CM701778
	/**
	 * Set RFT transmission time. Time now is spent at time. <BR>
	 * This part does not have the meaning in the transmission text. 
	 */
	public void setRftSendDate()
	{
		setRftSendDate(new Date());
	}
	//#CM701779
	/**
	 * Set the transmission time. 
	 * This part does not have the meaning in the transmission text. 
	 * @param  sdate Set time
	 */
	public void setRftSendDate(Date sdate)
	{
		String wdt = SEND_TIME_FORMAT.format(sdate);
		setToBuffer(wdt, OFF_RFTSENDDATE);
	}
	//#CM701780
	/**
	 * Set RFT transmission time. 
	 * This part does not have the meaning in the transmission text. 
	 * @param  st Set time
	 */
	public void setRftSendDate(String st)
	{
		setToBuffer(st, OFF_RFTSENDDATE);
	}

	//#CM701781
	/**
	 * Set the SERV transmission time. Time now is spent at time. 
	 */
	public void setServSendDate()
	{
		setServSendDate(new Date());
	}

	//#CM701782
	/**
	 * Set the SERV transmission time. 
	 * @param  adate Set time
	 */
	public void setServSendDate(Date adate)
	{
		String wdt = SEND_TIME_FORMAT.format(adate);
		setToBuffer(wdt, OFF_SERVSENDDATE);
	}

	//#CM701783
	/**
	 * Set the SERV transmission time. 
	 * This part does not have the meaning in the transmission text. 
	 * @param  st Set time
	 */
	public void setServSendDate(String st)
	{
		setToBuffer(st, OFF_SERVSENDDATE);
	}

	//#CM701784
	/**
	 * Set RFT machine. 
	 * @param Rft Set RFT machine
	 */
	public void setRftNo(String rft)
	{
		setToBuffer(rft, OFF_RFTNO);
	}

	//#CM701785
	/**
	 * Acquire the content of Work start report telegram. 
	 * @return Content of work beginning response TEXT
	*/
	public String toString()
	{
		return new String(wLocalBuffer);
	}

	//#CM701786
	/**
	 * Preserve the content of telegram in the file temporarily in telegram while working. 
	 * @param fileName File Name
	 * @throws IOException It is notified when file I/O Error is generated. 
	 */
	public void saveMessageFile(String fileName) throws IOException
	{
		FileOutputStream out = new FileOutputStream(fileName);

		//#CM701787
		// Write Data in the buffer in the file. 
		out.write(wDataBuffer);
		out.close();
	}
	
	//#CM701788
	/**
	 * Update the telegram item of RFTWork information in the content of telegram. <BR>
	 * Update condition<BR>
	 * RFTNo<BR>
	 * <BR>
	 * Content of update<BR>
	 * Response telegram<BR>
	 * Last update processing name<BR>
	 * Last updated date and time<BR>
	 * 
	 * @param rftNo RFT machine No
	 * @param className Last update processing name
	 * @param conn Connection object with data base
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws InvalidDefineException The specified value is notified. 
	 * @throws NotFoundException It is notified when update object Data does not exist. 
	 */
	public void saveMessageData(String rftNo, Connection conn, String className) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		RftAlterKey rkey = new RftAlterKey();
		RftHandler rHandler = new RftHandler(conn);
		
		rkey.setRftNo(rftNo);
		rkey.updateLastUpdatePname(className);
		rkey.updateResponseId(getFromBuffer(OFF_STX, wDataBuffer.length));
		
		rHandler.modify(rkey);
	}
	
	
	//#CM701789
	/**
	 * Return the copy of byte row maintained internally. 
	 * @return		Byte row of telegram
	 */
	public byte[] getDataBuffer()
	{
		byte[] buf = new byte[wDataBuffer.length];
		for (int i = 0; i < buf.length; i ++)
		{
			buf[i] = wDataBuffer[i];
		}
		return buf;
	}

	//#CM701790
	// Package methods -----------------------------------------------

	//#CM701791
	// Protected methods ---------------------------------------------
	//#CM701792
	/**
	 * Set the part of the content of communication telegram. 
	 * It does not check it for contents. 
	 * @param content  Set content of telegram. 
	 */
	protected void setContent(String content)
	{
		setToBuffer(content, OFF_CONTENT);
	}
	//#CM701793
	/**
	 * Acquire the part of the content of communication telegram. 
	 * It does not check it for contents. 
	 * @return Received content of telegram. 
	 */
	protected String getContent()
	{
		int wlen = wDataBuffer.length - OFF_CONTENT;
		return (getFromBuffer(OFF_CONTENT, wlen));
	}

	//#CM701794
	/**
	 * Set information to an internal buffer. 
	 * @param  src   Set information
	 * @param  offset   Offset of set buffer
	 */
	protected void setToBuffer(String src, int offset)
	{
		byte[] wkb = src.getBytes();
		for (int i = 0; i < wkb.length; i++)
		{
			wDataBuffer[i + offset] = wkb[i];
		}
	}

	//#CM701795
	/**
	 * Set Numerical value information to an internal buffer by right justify. 
	 * @param  src   Set Numerical value information(int)
	 * @param  offset   Offset of set buffer
	 * @param  length   Length of set input Data
	 */
	protected void setToBufferRight(int src, int offset, int length)
	{
	    setToBufferRight(Integer.toString(src), offset, length);
	}
	
	//#CM701796
	/**
	 * Set Character string information to an internal buffer by right justify. 
	 * @param  src   Set Character string information
	 * @param  offset   Offset of set buffer
	 * @param  length   Length of set input Data
	 */
	protected void setToBufferRight(String src, int offset, int length)
	{
		//#CM701797
		// Convert input String into the byte type. 
		byte[] buf = src.trim().getBytes();

		//#CM701798
		// Make the space array of input Data length. 
		byte[] space = new byte[length];

		for (int si = 0; si < length; si++)
		{
			space[si] = ' ';
		}

		//#CM701799
		// Calculate the offset. 
		int bufOffset = length - buf.length;

		//#CM701800
		// Set input Data in the made space array. 
		for (int i = 0; i < buf.length; i++)
		{
			space[i + bufOffset] = buf[i];
		}

		//#CM701801
		// Set Data in an internal buffer. 
		for (int i = 0; i < space.length; i++)
		{
			wDataBuffer[i + offset] = space[i];
		}
	}

	//#CM701802
	/**
	 * Set information to an internal buffer. 
	 * @param  src   Set information
	 * @param  offset   Offset of set buffer
	 */
	protected void setToByteBuffer(byte src, int offset)
	{
		wDataBuffer[offset] = src;
	}

	//#CM701803
	/**
	 * Set telegram received from RFT in an internal buffer. 
	 * @param  rmsg   Telegram to be set
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		wDataBuffer = rmsg;
	}

	//#CM701804
	/**
	 * Acquire information from an internal buffer. 
	 * @param  offset  Offset of information acquired from buffer
	 * @param  len     Length of information acquired from buffer(bytes)
	 * @return Acquisition Data(Character string).
	 */
	protected String getFromBuffer(int offset, int len)
	{
		return new String(wDataBuffer, offset, len);
	}

	//#CM701805
	/**
	 * Acquire information converted from an internal buffer into in order Numerical value. 
	 * @param  offset  Offset of information acquired from buffer
	 * @param  len     Length of information acquired from buffer(bytes)
	 * @return Acquisition Data(Numerical value)
	 * @throws	NumberFormatException	It is notified when it is not possible to restore it to the integer value. 
	 */
	protected int getIntFromBuffer(int offset, int len) throws NumberFormatException
	{
	    String str = new String(wDataBuffer, offset, len);
		return Integer.parseInt(str.trim());
	}
	
	//#CM701806
	/**
	 * Retrieve RFTWork information contingent on RFT machine No and acquire the telegram item. <BR>
	 * Generate response telegram with the acquired value, and return the instance. 
	 * (X) Use it for the test. 
	 * @param  fileName File Name
	 * @return	Object of read telegram
	 * @throws IOException It is notified when file I/O Error is generated. 
	 * @throws IllegalAccessException	It is notified when failing in the instance generation of telegram.
	 */
	public static IdMessage loadMessageFile(String fileName) throws IOException, IllegalAccessException
	{
		FileInputStream in = new FileInputStream(fileName);
		byte[] buffer = new byte[LEN_MAX_PACKET];
		int maxLength = LEN_MAX_PACKET;
		int offset = 0;
		int len = 0;
		
		while ((len = in.read(buffer, offset, maxLength)) > 0)
		{
			offset += len;
			maxLength -= len;
		}
		in.close();
		
		if (offset == 0)
		{
			return null;
		}
		
		IdMessage msg = new IdMessage(buffer);
		String id = msg.getID();
		IdMessage responseMessage
			= (IdMessage) PackageManager.getObject("RFTId" + id);
		responseMessage.setReceiveMessage(buffer);
		
		return responseMessage;
		
	}

	//#CM701807
	// Private methods -----------------------------------------------
}
//#CM701808
//end of class
