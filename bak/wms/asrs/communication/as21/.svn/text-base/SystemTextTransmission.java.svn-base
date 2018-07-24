// $Id: SystemTextTransmission.java,v 1.2 2006/10/24 09:15:47 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31911
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.text.DecimalFormat;

import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;

//#CM31912
/**
 * This class preserves the method for sending each type of texts according to AS21 communication protocol.<br>
 * The method defined in this class is used when text is being sent to AGC.
 * In this class, text is send is composed based on the request.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/04/01</TD><TD>kubo</TD><TD>Catch the IO Exception by messageSend method.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/24 09:15:47 $
 * @author  $Author: suresh $
 */
public class SystemTextTransmission extends Object
{

	//#CM31913
	// Class fields --------------------------------------------------
	//#CM31914
	/**
	 *  Length of MC Key
	 */
	protected static final int LEN_CARRYKEY			= 8 ;

	//#CM31915
	/**
	 * Classification field for completion (location occupied)
	 */
	public static final int CALSS_DOBULE_STRAGE		= 0 ;

	//#CM31916
	/**
	 * Classification field for completion (empty retrieval)
	 */
	public static final int CALSS_RETRIEVAL_ERROR	= 1 ;

	//#CM31917
	/**
	 * Classification field for completion (load size mismatch)
	 */
	public static final int CALSS_LOAD_MISALIGNMENT	= 2 ;

	//#CM31918
	// Class variables -----------------------------------------------

	//#CM31919
	// Class method --------------------------------------------------
	//#CM31920
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/24 09:15:47 $") ;
	}

	//#CM31921
	// Constructors --------------------------------------------------

	//#CM31922
	// Public methods ------------------------------------------------
	//#CM31923
	/**
	 * Method of transmission of work start request 
	 * @param gcNo			No. of GroupController
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id01send(int gcNo) throws Exception
	{
		As21Id01 as21id01 = new As21Id01();
		String sendMsg = as21id01.getSendMessage();
		messageSend(sendMsg,gcNo);
	}

	//#CM31924
	/**
	 * Transmission of date and time Data
	 * @param agc			No. of agc
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id02send(int agc) throws Exception
	{
		As21Id02 as21id02 = new As21Id02();
		String sendMsg = as21id02.getSendMessage();
		messageSend(sendMsg,agc);
	}

	//#CM31925
	/**
	 * Transmission of work completion request
	 * In this method, status of GroupController will not be shifted to 'reserved for termination'.
	 * Therefore, it must be done by application side(display).
	 * @param gcNo				No. of GroupController
	 * @param reqinfo_class	request classification
	 *								0:forced termination (terminated with no remaining job)<BR>
	 *      						1:forced termination 1 (terminated regardless of remaining jobs)<BR>
	 *      						2:forced termination 2 (terminated ; having deleted any remaining jobs)
	 * @throws Exception		Notifies if trouble occurs.
	 */
	public static void id03send(int gcNo, String reqinfo_class) throws Exception
	{
		As21Id03 as21id03 = new As21Id03();
		as21id03.setRequestClass(reqinfo_class);
		String sendMsg = as21id03.getSendMessage();
		messageSend(sendMsg, gcNo);
	}

	//#CM31926
	/**
	 * Carry Data Cancel request
	 * @param  ci			CarryInformation
	 * @param  conn		Connection
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id04send(CarryInformation ci, Connection conn) throws Exception 
	{
		PaletteSearchKey pSkey = new PaletteSearchKey();
		PaletteHandler pHandler = new PaletteHandler(conn);	
		pSkey.setPaletteId(ci.getPaletteId());					
		Palette palette = (Palette)pHandler.findPrimary(pSkey);
		
		Station st = StationFactory.makeStation(conn, palette.getCurrentStationNumber());
		GroupController agc = null;

		if (st instanceof Shelf)
		{
			st = StationFactory.makeStation(conn, st.getParentStationNumber());
			agc = GroupController.getInstance(conn, st.getControllerNumber());
		}
		else
		{
			agc = GroupController.getInstance(conn, st.getControllerNumber());
		}


		if (agc.getStatus() == GroupController.STATUS_ONLINE)
		{
			As21Id04 as21id04 = new As21Id04(ci);
			String sendMsg = as21id04.getSendMessage();
			messageSend(sendMsg, agc.getNumber());
		}
		else
		{
			//#CM31927
			// 6024003=Cannot send data. Group controller is offline.
			Object[] tObj = new Object[1] ;
			tObj[0] = new String("AGC_CONDITION_ERR") ;
			RmiMsgLogClient.write(6024003, LogMessage.F_ERROR, "SystemTextTransmission id04send", tObj);
			throw new InvalidStatusException("6024003");
		}
	}

	//#CM31928
	/**
	 * Instruction of destination change
	 * @param  ci			CarryInformation
	 * @param  agcdata		AGC data
	 * @param  conn		Connection
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id08send(CarryInformation ci, String agcdata, Connection conn) throws Exception
	{
		PaletteSearchKey pSkey = new PaletteSearchKey();
		PaletteHandler pHandler = new PaletteHandler(conn);	
		pSkey.setPaletteId(ci.getPaletteId());					
		Palette palette = (Palette)pHandler.findPrimary(pSkey);
		
		String mcKey = getMCKey(ci);
		String locationNo = "";
		String rejectStationNo = "";
		boolean modeCommand;
		int agc;
		if(StringUtil.isBlank(ci.getDestStationNumber()))
		{
			locationNo = "000000000000";
			rejectStationNo = "0000";
			modeCommand = false;
			Station st = StationFactory.makeStation(conn,palette.getCurrentStationNumber());

			//#CM31929
			// Determine AGC to send to
			if(st instanceof Shelf)
			{
				st = StationFactory.makeStation(conn, st.getParentStationNumber());
				agc = GroupController.getInstance(conn, st.getControllerNumber()).getNumber();
			}
			else
			{
				agc = GroupController.getInstance(conn, st.getControllerNumber()).getNumber();
			}
		}
		else
		{
			Station st = StationFactory.makeStation(conn,ci.getDestStationNumber());
			if(st instanceof Shelf)
			{
				locationNo = st.getStationNumber();
				rejectStationNo = "0000";
			}
			else if(st instanceof Station)
			{
				rejectStationNo = st.getStationNumber();
				locationNo = "000000000000";
			}
			modeCommand = true;
			
			//#CM31930
			// Determine AGC to send to
			if(st instanceof Shelf)
			{
				st = StationFactory.makeStation(conn, st.getParentStationNumber());
				agc = GroupController.getInstance(conn, st.getControllerNumber()).getNumber();
			}
			else
			{
				agc = GroupController.getInstance(conn, st.getControllerNumber()).getNumber();
			}

		}
		As21Id08 as21id08 = new As21Id08(mcKey,
						modeCommand,
						locationNo,
						rejectStationNo,
						agcdata );
		String sendMsg = as21id08.getSendMessage();
		messageSend(sendMsg,agc);
	}

	//#CM31931
	/**
	 * Machine state request
	 * @param gcNo			no. of GroupController
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id10send(int gcNo) throws Exception
	{
		As21Id10 as21id10 = new As21Id10();
		String sendMsg = as21id10.getSendMessage();
		messageSend(sendMsg,gcNo);
	}

	//#CM31932
	/**
	 * Sending the alternative location instruction.
	 * @param  ci 	CarryInformation
	 * @param  int 	classification 	0:location occupied
	 *					        		1:empty retrieval
	 *					        		2:load size mismatch
	 * @param  type Presence or absence of alternative location. If there are any alternative loaction or station,
	 * it returns 'true'. If there is none. it returns 'false'.
	 * @param  conn		Connection
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id11send(CarryInformation ci, int rclass, boolean stat, Connection conn) throws Exception 
	{
		PaletteSearchKey pSkey = new PaletteSearchKey();
		PaletteHandler pHandler = new PaletteHandler(conn);	
		pSkey.setPaletteId(ci.getPaletteId());					
		Palette palette = (Palette)pHandler.findPrimary(pSkey);
		
		Station st ;
		String locationNo = null ;
		String rejectStationNo = null ;
		String requestClass = null;
		String height ;		

		As21Id11 as21id11 = new As21Id11() ;

		//#CM31933
		// Getting the receiving station number
		String deststno =ci.getDestStationNumber() ;

		if (stat)
		{
			st = StationFactory.makeStation(conn, deststno);
			if(st instanceof Shelf)
			{
				locationNo = st.getStationNumber();
				rejectStationNo = "0000";
				switch(rclass)
				{
					//#CM31934
					// location occupied
					case CALSS_DOBULE_STRAGE:
						requestClass = As21Id11.DUP_NEW_LOCATION ;
						break;
						
					//#CM31935
					// empty retrieval
					case CALSS_RETRIEVAL_ERROR:
						//#CM31936
						// Cancel instruction is sent without conditions according to the standard system.
						requestClass = As21Id11.EMPTY_DATA_CANCEL ;
						break;
						
					//#CM31937
					// load size mismatch
					case CALSS_LOAD_MISALIGNMENT:
						requestClass = As21Id11.DIM_NEW_LOCATION ;
						break;
				}
			}
			else if (st instanceof Station)
			{
				rejectStationNo = st.getStationNumber();
				locationNo = "000000000000";
				switch(rclass)
				{
					//#CM31938
					// location occupied
					case CALSS_DOBULE_STRAGE:
						requestClass = As21Id11.DUP_PAID ;
						break;
						
					//#CM31939
					// empty retrieval
					case CALSS_RETRIEVAL_ERROR:
						//#CM31940
						// Cancel instruction is sent without conditions according to the standard system.
						requestClass = As21Id11.EMPTY_DATA_CANCEL ;
						break;
						
					//#CM31941
					// load size mismatch
					case CALSS_LOAD_MISALIGNMENT:
						requestClass = As21Id11.DIM_PAID ;
						break;
				}
			}
		}
		else
		{
			locationNo = "000000000000";
			rejectStationNo = "0000";
			st = StationFactory.makeStation(conn, palette.getCurrentStationNumber());
			switch(rclass)
			{
				//#CM31942
				// location occupied
				case CALSS_DOBULE_STRAGE:
					requestClass = As21Id11.DUP_NO_SUBSHELF ;
					break;
					
				//#CM31943
				// empty retrieval
				case CALSS_RETRIEVAL_ERROR:
					//#CM31944
					// Cancel instruction is sent without conditions according to the standard system.
					requestClass = As21Id11.EMPTY_DATA_CANCEL ;
					break;
					
				//#CM31945
				// load size mismatch
				case CALSS_LOAD_MISALIGNMENT:
					requestClass = As21Id11.DIM_NO_SUBSHELF ;
					break;
			}
		}
		
		//#CM31946
		// Set the instruciton classification
		as21id11.setRequestClass(requestClass) ;	
		//#CM31947
		// Set the MCKey
		String mcKey = getMCKey(ci) ;
		as21id11.setMcKey(mcKey) ;
		//#CM31948
		// Set the LocationNo
		as21id11.setLocationNo(locationNo) ;
		//#CM31949
		// Set the StationNo
		as21id11.setStationNo(rejectStationNo) ;
		//#CM31950
		// Set the load size if the instruction classification states 'NewLocation, load size mismatch'.
		if(requestClass.equals(As21Id11.DIM_NEW_LOCATION))
		{
			DecimalFormat fmt = new DecimalFormat("00") ;
			height = fmt.format(palette.getHeight()) ;
		} 
		else
		{
			height = "00" ;
		}
		as21id11.setDimensionInfo(height) ;
		//#CM31951
		// Set the BCData
		String tmpBcd = palette.getBcData();
		if(StringUtil.isBlank(tmpBcd))
		{
			tmpBcd = "";
		}
		String bcd = operateMessage(tmpBcd, As21Id11.BC_DATA) ;
		as21id11.setBcData(bcd) ;
		//#CM31952
		// Set the work number.
		String tmpnum = ci.getWorkNumber();
		if(StringUtil.isBlank(tmpnum))
		{
			tmpnum = "";
		}
		String num = operateMessage(tmpnum, As21Id11.WORK_NO) ;
		as21id11.setWorkNumber(num) ;
		//#CM31953
		// Set the control data.
		String tmpinfo = ci.getControlInfo();
		if(StringUtil.isBlank(tmpinfo))
		{
			tmpinfo = "";
		}
		String info = operateMessage(tmpinfo, As21Id11.CONTROL_INFORMATION) ;
		as21id11.setControlInfo(info) ;

		int agc;
		if(st instanceof Shelf)
		{
			st = StationFactory.makeStation(conn, st.getParentStationNumber());
			agc = GroupController.getInstance(conn, st.getControllerNumber()).getNumber();
		}
		else
		{
			agc = GroupController.getInstance(conn, st.getControllerNumber()).getNumber();
		}

		String sendMsg = as21id11.getSendMessage();
		messageSend(sendMsg,agc);
	}

	//#CM31954
	/**
	 * All start/stop
	 * @param gcNo			no. of GroupController
	 * @param b			classificaiton of start/stop
	 *							1:start
	 *							2:stop
	 * @param conn			Connection
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id16send(int gcNo, boolean b, Connection conn) throws Exception
	{
		GroupController gc = GroupController.getInstance(conn, gcNo);
		//#CM31955
		// Check the status of system.
		if( gc.getStatus() == GroupController.STATUS_ONLINE)
		{
			As21Id16 as21id16 = new As21Id16(b);
			String sendMsg = as21id16.getSendMessage();
			messageSend(sendMsg,gcNo);
		}
	}

	//#CM31956
	/**
	 * Respond to the transmission Test
	 * @param gcNo			no. of GroupController
	 * @param msg			SendTestData
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id20send(int gcNo, String msg) throws Exception
	{
		As21Id20 as21id20 = new As21Id20(msg);
		String sendMsg = as21id20.getSendMessage();
		messageSend(sendMsg, gcNo);
	}

	//#CM31957
	/**
	 * Respond to the work mode switch request
	 * @param stnum		StationNo to respond to
	 * @param kbn			Classificaiton of response
	 *							00:normaly received
	 *							01:Error (working)
	 *							02:Error (Station No.)
	 * @param agc			Controller no. to send 
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id41send(String stnum, String kbn, int agc) throws Exception
	{
		As21Id41 as21id41 = new As21Id41();
		as21id41.setStationNo(stnum);
		as21id41.setRequestClass(kbn);
		String sendMsg = as21id41.getSendMessage();
		messageSend(sendMsg, agc);
	}

	//#CM31958
	/**
	 * Work mode switch instruction
	 * @param st			station
	 * @param mode			work mode
	 *							1:storage mode-normal
	 *							2:storage mode-urgent
	 *							3:retrieval mode-normal
	 *							4:retrieval mode-urgent
	 * @param conn			Connection
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id42send(Station st, String mode, Connection conn) throws Exception
	{
		As21Id42 as21id42 = new As21Id42(st,mode);
		String sendMsg = as21id42.getSendMessage();
		int agc;
		if(st instanceof Shelf)
		{
			st = StationFactory.makeStation(conn, st.getParentStationNumber());
			agc = GroupController.getInstance(conn, st.getControllerNumber()).getNumber();
		}
		else
		{
			agc = GroupController.getInstance(conn, st.getControllerNumber()).getNumber();
		}

		messageSend(sendMsg,agc);
	}

	//#CM31959
	/**
	 * MC worl completion report
	 * @param ci			carry information
	 * @param conn			Connection
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id45send(CarryInformation ci, Connection conn) throws Exception
	{
		PaletteSearchKey pSkey = new PaletteSearchKey();
		PaletteHandler pHandler = new PaletteHandler(conn);
		pSkey.setPaletteId(ci.getPaletteId());					
		Palette palette = (Palette)pHandler.findPrimary(pSkey);
		
		Station st = StationFactory.makeStation(conn, palette.getCurrentStationNumber());
		
		if(st instanceof Shelf)
			st = StationFactory.makeStation(conn, ci.getDestStationNumber());
		
		GroupController groupControll = new GroupController(conn, st.getControllerNumber());
		
		if(groupControll.getStatus()==GroupController.STATUS_ONLINE)
		{
			As21Id45 as21id45 = new As21Id45(ci);
			String sendMsg = as21id45.getSendMessage();
			int agc;
			if(st instanceof Shelf)
			{
				st = StationFactory.makeStation(conn, st.getParentStationNumber());
				agc = GroupController.getInstance(conn, st.getControllerNumber()).getNumber();
			}
			else
			{
				agc = GroupController.getInstance(conn, st.getControllerNumber()).getNumber();
			}

			messageSend(sendMsg,agc);
		}
		else
		{
			//#CM31960
			// 6024003=Cannot send data. Group controller is offline.
			Object[] tObj = new Object[1] ;
			tObj[0] = new String("AGC_CONDITION_ERR") ;
			RmiMsgLogClient.write(6024003, LogMessage.F_ERROR, "SystemTextTransmission id04send", tObj);
			throw new InvalidStatusException("6024003");
		}
	}

	//#CM31961
	/**
	 * Inaccessible location request
	 * @param agc			AGC number
	 * @throws Exception	Notifies if trouble occurs.
	 */
	public static void id51send(int agc) throws Exception
	{
		As21Id51 as21id51 = new As21Id51();
		String sendMsg = as21id51.getSendMessage();
		messageSend(sendMsg,agc);
	}

	//#CM31962
	// Package methods -----------------------------------------------

	//#CM31963
	// Protected methods ---------------------------------------------

	//#CM31964
	// Private methods -----------------------------------------------

	//#CM31965
	/**
	 * Send the specified instruction text via RMI server to the text send process task.
	 * @param sendMsg		Send text message
	 * @param wAgcNumber	Group controller (AGC) number 
	 * @throws Exception	Notifies if trouble occurs.
	 */
	private static void messageSend(String sendMsg,int wAgcNumber) throws Exception
	{
		DecimalFormat fmt = new DecimalFormat("00");
		Object[] param = new Object[2];
		
		//#CM31966
		// Calls the write method of As21Sender using RMI.
		RmiSendClient rmiSndC = new RmiSendClient();
		param[0] = sendMsg;
		try
		{
			rmiSndC.write( "AGC"+ fmt.format(wAgcNumber), param );
			rmiSndC = null;
		}
		catch( IOException e )
		{
			//#CM31967
			// 6024030=Cannot send the text since SRC is not connected. SRC No.={0}
			Object[] tObj = new Object[1] ;
			tObj[0] = fmt.format(wAgcNumber);
			RmiMsgLogClient.write(6024030, LogMessage.F_WARN, "SystemTextTransmission", tObj);
			throw new NotBoundException(e.getMessage());
		}
		catch (NotBoundException e)
		{
			//#CM31968
			// 6024030=Cannot send the text since SRC is not connected. SRC No.={0}
			Object[] tObj = new Object[1] ;
			tObj[0] = fmt.format(wAgcNumber);
			RmiMsgLogClient.write(6024030, LogMessage.F_WARN, "SystemTextTransmission", tObj);
			throw e;
		}
	
		param[0] = null ;
		sendMsg = "";
	}

	//#CM31969
	/**
	 * Retrieve MC key from <code>CarryInformation</code>.
	 * @param	ci			CarryInformation
	 * @return		MC Key
	 * @throws InvalidProtocolException : Reports if MC Key is not the allowable length.
	 */
	private static String getMCKey(CarryInformation ci) throws InvalidProtocolException
	{
		StringBuffer stbuff = new StringBuffer(LEN_CARRYKEY );
		//#CM31970
		// MC Key(included in CarryInformation)
		String carryKey = ci.getCarryKey();
		if( carryKey.length() > LEN_CARRYKEY )
		{
			throw new InvalidProtocolException("carryKey = " + LEN_CARRYKEY + "--->" + carryKey) ;
		}
		else
		{
			stbuff.replace(0, LEN_CARRYKEY,  "00000000");
			stbuff.replace(LEN_CARRYKEY - carryKey.length(), LEN_CARRYKEY,  carryKey);
		}
		return (stbuff.toString()) ;
	}

	//#CM31971
	/**
	 * Adjust the message in the specified length.
	 * Cut down the length if the original message is longer than specifiation. If shorter, fill with space
	 * to the specified length.
	 * @param   src			Original message
	 * @param   len			Specification of the string length
	 * @return  String after adjusted
	 * @throws Exception	Notifies if trouble occurs in reading/writing in database.
	 */
	private static String operateMessage(String src, int len)
	{
		int  dif = len - src.length() ;
		if (dif <= 0)
		{
			//#CM31972
			// src too long
			return (src.substring(0, len)) ;
		}
		else
		{
			//#CM31973
			// src too short
			byte[]  wb = new byte[dif] ;
			for (int i=0; i < dif; i++)
			{
				wb[i] = ' ' ;
			}
			return (src + new String(wb)) ;
		}
	}
}
//#CM31974
//end of class

