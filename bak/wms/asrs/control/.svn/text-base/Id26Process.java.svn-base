// $Id: Id26Process.java,v 1.2 2006/10/26 03:06:46 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM32822
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id26;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;
//#CM32823
/**
 * This class processes the arrival reports. It generates the CarryInformation based on mckey,
 * then handles the arrival process according to the transport section.
 * If the mckey represents the value of dummy arrival (usually '99999999'), it processes the dummy arrival.
 * The actula arrival process will be handled by the class that inherited the Station generated based on the
 * station no. included in arrival report text.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:06:46 $
 * @author  $Author: suresh $
 */
public class Id26Process extends IdProcess
{
	//#CM32824
	// Class fields --------------------------------------------------

	//#CM32825
	// Class variables -----------------------------------------------
	//#CM32826
	/**
	 * Preserves the target AGCNO.
	 */
	 private int  wAgcNumber;

	//#CM32827
	/**
	 * process name
	 */
	private static final String CLASS_NAME = "Id26Process";

	//#CM32828
	// Class method --------------------------------------------------
	//#CM32829
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:06:46 $") ;
	}

	//#CM32830
	// Constructors --------------------------------------------------
	//#CM32831
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id26Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM32832
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id26Process(int num)
	{
		super() ;
		wAgcNumber = num ;
	}

	//#CM32833
	// Public methods ------------------------------------------------
	
	//#CM32834
	// Package methods -----------------------------------------------

	//#CM32835
	// Protected methods ---------------------------------------------
	//#CM32836
	/**
	 * Processing the arrival report.
	 * Based on the MC Key of received communication message, it searches the <code>CarryInformation</code> and processes after pallet arrival.
	 * If received the dummy arrival data, it will generate the CarryInformation instance for dummy arrival.
	 * With regular MCKEY, it searches the corresponding data then generates the CarryInformation instance based on its content. 
	 * Then it updates the load size and current position accordingly.
	 * Finally it executes the arrival process of the Station class abd updates the carry data according to the station.
	 * If transport section of CarryInformation was retrieval with its status 'instructed' or 'wait for response',
	 * it determines that retrieval completion process has been skipped, and carries out the completion process.
	 * Call source needs to commit or rollback the transaction, as they are not done here.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		//#CM32837
		// CarryInformation to update
		CarryInformation ci = null;
		
		CarryCompleteOperator carryCompOpe = new CarryCompleteOperator();
		carryCompOpe.setClassName(CLASS_NAME);
		
		//#CM32838
		// Decomposition of received texts
		As21Id26 id26dt = new As21Id26(rdt) ;
		//#CM32839
		// Creating the arrival station
		Station st = null;
		try
		{
			st = StationFactory.makeStation(wConn, id26dt.getStationNumber()) ;
		}
		catch (NotFoundException e)
		{
			//#CM32840
			// If specified station does not exist, 
			//#CM32841
			// terminates this ID processing jsut as it is. No handling for exception is conducted.
			return;
		}
		
		//#CM32842
		// The arrival process will not be conducted in case the on-line indication is operated on the retrieval side 
		//#CM32843
		// of U-shaped station.
		StationOperator sto = StationOperatorFactory.makeOperator(wConn, st.getStationNumber(), st.getClassName());		
		if (sto instanceof FreeRetrievalStationOperator)
		{
			if (st.getOperationDisplay() == Station.OPERATIONINSTRUCTION)
			{
				return ;
			}
		}
		
		//#CM32844
		// Checks the MCkey. 
		String mckey = id26dt.getMcKey() ;
		if (AsrsParam.DUMMY_MCKEY.equals(mckey))
		{
			//#CM32845
			// In case of dummy arrival, it should create the instance of dummy CarryInforation.
			Palette dpl = new Palette();
			dpl.setHeight(Integer.parseInt(id26dt.getDimensionInformation()));
			dpl.setBcData(id26dt.getBcData());
			ci = new CarryInformation();
			ci.setCarryKind(CarryInformation.CARRYKIND_STORAGE);
			ci.setCarryKey(mckey);
			ci.setPaletteId(dpl.getPaletteId());
			
			//#CM32846
			// Processes the arrival by each station.
			sto.arrival(ci, dpl) ;
		}
		else
		{
			//#CM32847
			// Sets the MC key as a search condition, generates the instance of CarryInforation. 
			CarryInformationHandler cih = new CarryInformationHandler(wConn) ;
			CarryInformationSearchKey cskey = new CarryInformationSearchKey() ;
			cskey.setCarryKey(mckey) ;
			CarryInformation[] earr = (CarryInformation[]) cih.find(cskey);
		
			//#CM32848
			// There is no corresponding data.
			if (earr.length == 0)
			{
				Object[] tObj = new Object[1] ;
				tObj[0] = mckey;
				//#CM32849
				// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
				RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, this.getClass().getName(), tObj);
				return ;
			}
			
			if (earr[0] instanceof CarryInformation)
			{
				ci = (CarryInformation)earr[0] ;
			}
			else
			{
				Object[] tObj = new Object[1] ;
				tObj[0] = "CarryInformation" ;
				//#CM32850
				// 6006008=Object other than {0} was returned.
				RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, this.getClass().getName(), tObj);
				throw (new InvalidProtocolException("6006008" + wDelim + tObj[0])) ;
			}

			//#CM32851
			// Checks the carry status if the transport section of CarryInformation is 'retrieval'.
			//#CM32852
			// If the transport section was 'instructed' or 'wait for response', it determines that retrieval 
			//#CM32853
			// completion process has been skipped and carries out the completion process.
			if (ci.getCarryKind() == CarryInformation.CARRYKIND_RETRIEVAL)
			{
				if ((ci.getCmdStatus() == CarryInformation.CMDSTATUS_INSTRUCTION)
				 || (ci.getCmdStatus() == CarryInformation.CMDSTATUS_WAIT_RESPONSE))
				{
					//#CM32854
					// Logging that work completion report (retrieval) text has been skipped.
					Object[] tObj = new Object[1] ;
					tObj[0] = ci.getCarryKey() ;
					//#CM32855
					// 6022022=Picking completion is not processed. Forcing to complete picking. mckey={0}
					RmiMsgLogClient.write(6022022, LogMessage.F_ERROR, this.getClass().getName(), tObj);
					
					//#CM32856
					// Executes the retrieval completion process for this CarryInformation.
					Id33Process.normalRetrievalCompletion(wConn, ci);
				}
			}
			
			//#CM32857
			// Checks the load presence data in received text. If the load presence is OFF, unit trasfer to be conducted.
			if (!id26dt.getLoad())
			{
				if (st.isReStoringOperation())
				{
					//#CM32858
					// Updates the unit retrieval stocks (with creation of scheduled re-storage data)
					carryCompOpe.unitRetrieval(wConn, ci, true);
				}
				else
				{
					//#CM32859
					// Updates the unit retrieval stocks (no creation ofscheduled re-storage data)
					carryCompOpe.unitRetrieval(wConn, ci, false);
				}
			}
			else
			{
				//#CM32860
				// Updates information of Palette to carry this time.
				PaletteSearchKey pSKey = new PaletteSearchKey();
				PaletteHandler pHandle = new PaletteHandler(wConn);
				
				pSKey.setPaletteId(ci.getPaletteId());
				pSKey.setCurrentStationNumber(ci.getSourceStationNumber());
				
				Palette[] palette = (Palette[])pHandle.find(pSKey);
				
				PaletteAlterKey pAKey = new PaletteAlterKey();
				pAKey.setPaletteId(palette[0].getPaletteId());
				pAKey.setCurrentStationNumber(palette[0].getCurrentStationNumber());
				//#CM32861
				// If the arriving station operates the load size check, set Pallete with the load size information in teh received text.
				if (st.isLoadSizeCheck())
				{
					pAKey.updateHeight(Integer.parseInt(id26dt.getDimensionInformation()));
				}
				pAKey.updateBcData(id26dt.getBcData());
				pHandle.modify(pAKey);
				
				//#CM32862
				// Output the log message if the arrival station of CarryInformation differs from the station that received data.
				if (ci.getDestStationNumber().equals(st.getStationNumber()) == false)
				{
					//#CM32863
					// If parent station match, the instruction is given to main station; therefore no log to be recorded.
					if (ci.getDestStationNumber().equals(st.getParentStationNumber()) == false)
					{
						Object[] tObj = new Object[3] ;
						tObj[0] = ci.getDestStationNumber();
						tObj[1] = st.getStationNumber();
						tObj[2] = mckey;
						//#CM32864
						// 6022019=Mismatch of receiving station in data and arrived station To ST={0} Arrival={1} mckey={2}
						RmiMsgLogClient.write(6022019, LogMessage.F_NOTICE, this.getClass().getName(), tObj);
					}
					
					//#CM32865
					// Update the data of receiving station to the actually arrived station. 
					//#CM32866
					// (in case of direct travel, it i necessary to determine the start position and end position)
					ci.setDestStationNumber(st.getStationNumber());
				}
				
				//#CM32867
				// Processes the arrival per station.				
				StationOperator sOpe = StationOperatorFactory.makeOperator(wConn, st.getStationNumber(), st.getClassName());
				sOpe.arrival(ci, new Palette());
			}
		}
	}

	//#CM32868
	// Private methods -----------------------------------------------

}
//#CM32869
//end of class

