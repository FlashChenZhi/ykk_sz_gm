// $Id: As21Id45.java,v 1.2 2006/10/26 01:31:07 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29911
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.wms.base.entity.CarryInformation;

//#CM29912
/**
 * Composes communication message "McWorkCompletionReport" ID=45 according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:31:07 $
 * @author  $Author: suresh $
 */
public class As21Id45 extends SendIdMessage
{
	//#CM29913
	// Class fields --------------------------------------------------
	//#CM29914
	/**
	 * Field of classification for transfer (transfer)
	 */
	public static final String PAYOUT				= "0" ;

	//#CM29915
	/**
	 * Field of classification for transfer (returns storage)
	 */
	public static final String RETURN_STORAGE			= "1" ;

	//#CM29916
	/**
	 * Length of MC Key
	 */
	protected static final int LEN_CARRYKEY				=  8;

	//#CM29917
	/**
	 * Length of station No.
	 */
	protected static final int LEN_STATION				=  4;

	//#CM29918
	/**
	 * Length of classification for transfer
	 */
	protected static final int LEN_PAYOUT_CLASS	=  1;

	//#CM29919
	/**
	 * Length of communication message "McWorkCompletionReport"
	 */
	protected final int LEN_TOTAL = OFF_CONTENT
								+ LEN_CARRYKEY
								+ LEN_STATION
								+ LEN_PAYOUT_CLASS;

	//#CM29920
	// Class variables -----------------------------------------------
	//#CM29921
	/**
	 * Variable which preserves the instance of <code>CarryInformation</code> containing
	 * carrying data
	 */
	private CarryInformation wCarryInfo ;

	//#CM29922
	/**
	 * Field of classification for transfer (true: transfer, false: return storage )
	 */
	private boolean wboo;

	//#CM29923
	// Class method --------------------------------------------------
	//#CM29924
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:31:07 $") ;
	}
	
	//#CM29925
	// Constructors --------------------------------------------------
	//#CM29926
	/**
	 * Default constructor
	 */
	public As21Id45()
	{
	}

	//#CM29927
	/**
	 * Generates the instance of this class by specifying the instance of <code>CarryInformation</code>
	 * containing the carrying data.
	 * @param  ci  instance of <code>CarryInformation</code> containing carrying data
	 */
	public As21Id45(CarryInformation ci)
	{
		super() ;
		setCarryInforma(ci) ;
		setBoo(ci) ;
	}

	//#CM29928
	// Public methods ------------------------------------------------
	//#CM29929
	/**
	 * Creates the communication message "McWorkCompletionReport".
	 * @return    communication message "McWorkCompletionReport"
	 * @throws  InvalidProtocolException :Notifies if provided value is not following the communication message protocol.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		//#CM29930
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM29931
		// MC Key(included in CarryInformation)
		mbuf.append(getMCKey(wCarryInfo)) ;
		//#CM29932
		// Station No.(included in CarryInformation)
		mbuf.append(getStationNumber(wCarryInfo)) ;
		//#CM29933
		// classification for transfer (included in CarryInformation)
		mbuf.append(getPayoutclass(wboo)) ;

		//#CM29934
		//-------------------------------------------------
		//#CM29935
		// Setting for sending message buffer
		//#CM29936
		//-------------------------------------------------
		//#CM29937
		// ID
		setID("45") ;
		//#CM29938
		// ID segment
		setIDClass("00") ;
		//#CM29939
		// MC sending time
		setSendDate() ;
		//#CM29940
		// AGC sending time
		setAGCSendDate("000000") ;
		//#CM29941
		// text contents
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL)) ;
	}

	//#CM29942
	/**
	 * Acquires MC Key from <code>CarryInformation</code>.
	 * @param carryInformation :carrying information which has been input 
	 * @return MC Key
	 * @throws InvalidProtocolException : Reports if MC Key is not the allowable length.
	 */
	private String getMCKey(CarryInformation carryInformation) throws InvalidProtocolException
	{
		StringBuffer stbuff = new StringBuffer( LEN_CARRYKEY );
		//#CM29943
		// MC Key(included in CarryInformation)
		String carryKey = carryInformation.getCarryKey();
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

	//#CM29944
	/**
	 * Takes the sending station No. from <code>CarryInformation</code>.
	 * @param carryInformation carrying information which has been input
	 * @return sending station No.
	 * @throws InvalidProtocolException : Reports if sending station No. is not the allowable length.
	 */
	private String getStationNumber(CarryInformation carryInformation) throws InvalidProtocolException
	{
		//#CM29945
		// sending station No.(included in CarryInformation)
		String sendingStationNumber="";
		if(carryInformation.getCarryKind()==CarryInformation.CARRYKIND_STORAGE)
			sendingStationNumber = carryInformation.getSourceStationNumber();
		else
			sendingStationNumber = carryInformation.getDestStationNumber();
		if( sendingStationNumber.length() != LEN_STATION )
		{
			throw new InvalidProtocolException("sendingStationNumber = " + LEN_STATION + "--->" + sendingStationNumber) ;
		}
		return (sendingStationNumber) ;
	}

	//#CM29946
	/**
	 * Conducts determination process for the transfer classification by parameter.(true: transfer, false: return retrieval)
	 * @param transferClassification true or false
	 * @return transfer classification
	 */
	public String getPayoutclass (boolean transferClassification )
	{
		String payoutclass = "";

		if(transferClassification == true)
		{
			 payoutclass = PAYOUT;
		}
		else
		{
			 payoutclass = RETURN_STORAGE;
		}

		return (payoutclass);
		
	}

	//#CM29947
	/**
	 * Sets instance of CarryInformation
	 * @param ci Carrying data which has been input
	 */
	private void setCarryInforma(CarryInformation ci)
	{
		wCarryInfo = ci ;
	}

	//#CM29948
	/**
	 * Acquires the carry key and the detailed instruction of retrieval, then determines transfer classification.
	 * @param ci Carrying data which has been input
	 */
	private void setBoo(CarryInformation ci)
	{
		//#CM29949
		// Field to set transport (direct travel)
		if (ci.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
		{
			//#CM29950
			// transfer
			wboo = true;
		}
		else
		{
			//#CM29951
			// Field to set detail of retrieval instruction (retrieval in unit)
			if(ci.getRetrievalDetail()==CarryInformation.RETRIEVALDETAIL_UNIT)
			{
				//#CM29952
				// transfer
				wboo=true;
			}
			else
			{
				//#CM29953
				// returns storage
				wboo = false;
			}
		}
	}

	//#CM29954
	// Package methods -----------------------------------------------

	//#CM29955
	// Protected methods ---------------------------------------------

	//#CM29956
	// Private methods -----------------------------------------------

}
//#CM29957
//end of class

