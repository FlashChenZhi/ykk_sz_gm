// $Id: As21Id08.java,v 1.2 2006/10/26 01:41:00 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM28946
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM28947
/**
 * Composes communication message "Command : changing the receiving station ID=08" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:41:00 $
 * @author  $Author: suresh $
 */
public class As21Id08 extends SendIdMessage
{
	 //#CM28948
	 // Class fields --------------------------------------------------
	//#CM28949
	/**
	 * Field of command (to change receiving station)
	 */
	public static final String STATION_CHANGE = "1" ;

	//#CM28950
	/**
	 * Field of command (NOT possible to change the receiving station)
	 */
	public static final String STATION_NOT_CHANGE = "2" ;

	//#CM28951
	/**
	 * Length of MC Key
	 */
	protected static final int LEN_CARRYKEY 		= 8 ;

	//#CM28952
	/**
	 * Length of command classification
	 */
	protected static final int LEN_COMMANDCLASS		= 1 ;

	//#CM28953
	/**
	 * Length of location number
	 */
	protected static final int LEN_LOCATION		= 12 ;

	//#CM28954
	/**
	 * Length of station number
	 */
	protected static final int LEN_STATION	=  4 ;

	//#CM28955
	/**
	 * Length ofAGC data
	 */
	protected static final int LEN_AGCDATA			=  6 ;

	//#CM28956
	/**
	 * Length of the message
	 */
	protected final int LEN_TOTAL = OFF_CONTENT
								+ LEN_CARRYKEY
								+ LEN_COMMANDCLASS
								+ LEN_LOCATION
								+ LEN_STATION
								+ LEN_AGCDATA;

	//#CM28957
	// Class variables -----------------------------------------------
	//#CM28958
	/**
	 * Variable which preserves MC Key.
	 */
	private String wMckey ;

	//#CM28959
	/**
	 * Variable which preserves the segments of instruction.
	 */
	private boolean wModechangecommand ;

	//#CM28960
	/**
	 * Variable which preserves locations
	 */
	private String wLocation ;

	//#CM28961
	/**
	 * Variable that preserves the reject station number
	 */
	private String wStation ;

	//#CM28962
	/**
	 * Variable that preserves AGCDATA.
	 */
	private String wAgcdata ;

	//#CM28963
	// Class method --------------------------------------------------
	//#CM28964
	/**
	 * Returns the version of the the class.
	 * @return version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:41:00 $") ;
	}

	//#CM28965
	// Constructors --------------------------------------------------
	//#CM28966
	/**
	 * Default constructor
	 */
	public As21Id08 ()
	{
	}

	//#CM28967
	/**
	 * Generates the instance of this class by specifying MC Key of carry data, which indicates
	 * the receiving station changes, the detail of contents changed, location number, reject
	 * station number and the communication message requesting to change the receiving station from AGC.
	 *
	 * @param  mcKey 			McKey
	 * @param  modeCommand 	instruction segment
	 *							True:Instruction to change receiving station
	 *							False:NOT possible to change the receiving station
	 * @param  locationNo 		Location number
	 * @param  rejectStationNo Reject station number
	 * @param  agcdata 		Communication message requesting to chnage the receiving station from AGC
	 */
	public As21Id08 (
					String mcKey,
					boolean modeCommand,
					String locationNo,
					String rejectStationNo,
					String agcdata )
	{
		super() ;
		setMckey(mcKey);
		setModeCommand(modeCommand);
		setLocationNo(locationNo);
		setRejectStationNo(rejectStationNo);
		setAgcdata(agcdata);
	}

	 //#CM28968
	 // Public methods ------------------------------------------------

	//#CM28969
	/**
	 * Creates communication message directing to change the receiving station.<BR>
	 * @return    instruction message to change receiving station
	 */
	public String getSendMessage() 
	{
		String sCommand;

		//#CM28970
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;
		//#CM28971
		// Mc Key
		mbuf.append(wMckey) ;
		//#CM28972
		// instruction segment
		if (wModechangecommand){
			sCommand = STATION_CHANGE ;
		}else{
			sCommand = STATION_NOT_CHANGE ;
		}
		mbuf.append(sCommand) ;
		//#CM28973
		// Location No. 
		mbuf.append(wLocation) ;
		//#CM28974
		// Reject Station No.
		mbuf.append(wStation) ;
		//#CM28975
		// AGC Data
		mbuf.append(wAgcdata) ;

		
		//#CM28976
		//-------------------------------------------------
		//#CM28977
		// Sets as sending message buffer
		//#CM28978
		//-------------------------------------------------
		//#CM28979
		// id
		setID("08") ;
		//#CM28980
		// id segment
		setIDClass("00") ;
		//#CM28981
		// time sent
		setSendDate() ;
		//#CM28982
		// time sent to AGC
		setAGCSendDate("000000") ;
		//#CM28983
		// content of text
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL)) ;
	}

	//#CM28984
	/**
	 * Sets value of Mc key
	 * @param mcKey Mc key
	 */
	private void setMckey(String mcKey)
	{
		wMckey = mcKey ;
	}
	
	//#CM28985
	/**
	 * Sets value of ModeCommand.
	 * @param modeCommand ModeCommand
	 */
	private void setModeCommand(boolean modeCommand)
	{
		wModechangecommand = modeCommand;
	}
	
	//#CM28986
	/**
	 * Sets value of Location number
	 * @param locationNo LocationNo
	 */
	private void setLocationNo(String locationNo)
	{
		wLocation = locationNo ;
	}
	
	//#CM28987
	/**
	 * Sets value of RejectStationNo.
	 * @param rejectStationNo RejectStationNo
	 */
	private void setRejectStationNo(String rejectStationNo)
	{
		wStation = rejectStationNo ;
	}
	
	//#CM28988
	/**
	 * Sets value of Agcdata
	 * @param agcdata Agcdata
	 */
	private void setAgcdata(String agcdata)
	{
		wAgcdata = agcdata;
	}
	//#CM28989
	// Package methods -----------------------------------------------

	//#CM28990
	// Protected methods ---------------------------------------------

	//#CM28991
	// Private methods -----------------------------------------------
}

//#CM28992
// end of class
