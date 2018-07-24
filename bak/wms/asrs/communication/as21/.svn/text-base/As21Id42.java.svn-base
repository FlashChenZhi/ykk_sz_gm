// $Id: As21Id42.java,v 1.2 2006/10/26 01:31:27 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29874
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.wms.base.entity.Station;

//#CM29875
/**
 * Composes communication message "WorkModeChangeCommand" ID=42, the command to change the work mode according to 
 * AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:31:27 $
 * @author  $Author: suresh $
 */
public class As21Id42 extends SendIdMessage
{
	//#CM29876
	// Class fields --------------------------------------------------
	//#CM29877
	/**
	 * Field of instruction classification (storage mode-normal)
	 */
	public static final int CLASS_STORAGE 		= 1 ;

	//#CM29878
	/**
	 * Field of instruction classification (storage mode-urgent)
	 */
	public static final int CLASS_STORAGE_EMG 	= 2 ;

	//#CM29879
	/**
	 * Field of instruction classification (retrieval mode-normal)
	 */
	public static final int CLASS_RETRIEVAL 		= 3 ;

	//#CM29880
	/**
	 * Field of instruction classification (retrieval mode-urgent)
	 */
	public static final int CLASS_RETRIEVAL_EMG 	= 4 ;

	//#CM29881
	/**
	 * Length of station No.
	 */
	protected static final int LEN_STATIONNUMBER	= 4 ;

	//#CM29882
	/**
	 * Length of instruction classification
	 */
	protected static final int LEN_INSTRUCTIONCLASS	= 1 ;

	//#CM29883
	/**
	 * Length of communication message "WorkModeChangeCommand" 
	 */
	protected final int LEN_TOTAL = OFF_CONTENT
								+ LEN_STATIONNUMBER 
								+ LEN_INSTRUCTIONCLASS ;

	//#CM29884
	// Class variables -----------------------------------------------
	//#CM29885
	/**
	 * Variable which preserves instance of <code>Station</code>, containing station data
	 */
	private Station station ;

	//#CM29886
	/**
	 * Variable which preserves instruction classification
	 */
	private String workModeChangeCommand ;

	//#CM29887
	// Class method --------------------------------------------------
	//#CM29888
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:31:27 $") ;
	}

	//#CM29889
	// Constructors --------------------------------------------------
	//#CM29890
	/**
	 * Default constructor
	 */
	public As21Id42() 
	{
		super() ;
	}

	//#CM29891
	/**
	 * Generates the instance of this class by specifying the instance of <code>Station</code>
	 * containing the station data which directs work mode to change and by specifying the instruction
	 * classification for changing modes.
	 * @param  s 	<code>Station</code> , containing the station data
	 * @param  mode instruction classification
	 */
	public As21Id42(Station s, String mode) 
	{
		super() ;
		setStation(s) ;
		setModeChangeCommand(mode) ;
	}

	//#CM29892
	// Public methods ------------------------------------------------
	//#CM29893
	/**
	 * Creates the communication message "WorkModeChangeCommand". 
	 * @return    communication message "WorkModeChangeCommand"
	 * @throws  InvalidProtocolException Notifies if provided value is not following the communication message protocol.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		//#CM29894
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM29895
		// Station No.
		mbuf.append(station.getStationNumber()) ;

		//#CM29896
		// instruction classification
		mbuf.append(workModeChangeCommand) ;

		//#CM29897
		//-------------------------------------------------
		//#CM29898
		// Setting for sending message buffer
		//#CM29899
		//-------------------------------------------------
		//#CM29900
		// ID
		setID("42") ;
		//#CM29901
		// ID segment
		setIDClass("00") ;
		//#CM29902
		// MC sending time
		setSendDate() ;
		//#CM29903
		// AGC sending time
		setAGCSendDate("000000") ;
		//#CM29904
		// text contents
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL)) ;
	}

	//#CM29905
	// Package methods -----------------------------------------------

	//#CM29906
	// Protected methods ---------------------------------------------

	//#CM29907
	// Private methods -----------------------------------------------
	//#CM29908
	/**
	 * Sets the station data <code>Station</code>
	 * @param st Station instance one has entered
	 */
	private void setStation(Station st)
	{
		station = st ;
	}

	//#CM29909
	/**
	 * Sets the instruction classification in the internal buffer.
	 * @param modecommand instruction classification one has entered
	 */
	private void setModeChangeCommand(String modecommand)
	{
		workModeChangeCommand = modecommand ;
	}
}
//#CM29910
//end of class

