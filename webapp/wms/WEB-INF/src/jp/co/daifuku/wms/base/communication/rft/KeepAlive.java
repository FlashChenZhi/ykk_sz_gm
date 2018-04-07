//#CM643856
//$Id: KeepAlive.java,v 1.2 2006/11/07 06:49:38 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

//#CM643857
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.Rft;

//#CM643858
/**
 * Designer : T.Konishi <BR>
 * Maker :   <BR>
 * <BR>
 * The class which does the living confirmation of the terminal. <BR>
 * Start the ping command because the ICMP packet cannot be sent and received directly by Java. 
 * Do the living confirmation of the terminal by analyzing the output. 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:49:38 $
 * @author  $Author: suresh $
 */

public class KeepAlive extends Thread
{
	//#CM643859
	//Class fields --------------------------------------------------
	//#CM643860
	/**
	 * Field which shows this class
	 */
	private static final String CLASS_NAME = "KeepAlive";
	
	//#CM643861
	/**
	 * Ping command name
	 */
	private static final String PING_COMMAND = "ping.exe";
	
	//#CM643862
	/**
	 * Default Polling time (second)
	 */
	private static final int DEFAULT_POLLING_TIME = 60;
	
	//#CM643863
	//Class variables -----------------------------------------------
	//#CM643864
	/**
	 * Connection with data base
	 */
	private Connection wConn = null;
	
	//#CM643865
	//Class methods -------------------------------------------------
	//#CM643866
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/07 06:49:38 $";
	}
	
	//#CM643867
	//Constructors --------------------------------------------------
	//#CM643868
	/**
	 * Generate the instance. 
	 */
	public KeepAlive()
	{
		super();
		try {
			wConn = WmsParam.getConnection();
		} catch (SQLException e) {
			RftLogMessage.printStackTrace(
					6026011,
					LogMessage.F_ERROR,
					CLASS_NAME,
					e);
		}
	}
	
	//#CM643869
	/**
	 * Generate the instance <code>Connection</code> for the specified database connection.
	 * @param conn Database connection
	 */
	public KeepAlive(Connection conn)
	{
		super();
		wConn = conn;
	}
	
	//#CM643870
	//Public methods ------------------------------------------------
	//#CM643871
	/**
	 * The class which does the living confirmation of the terminal. <BR>
	 * Start the ping command because the ICMP packet cannot be sent and received directly by Java. 
	 * Do the living confirmation of the terminal by analyzing the output. 
	 * <BR>
	 * The terminal of the object which does the living confirmation is made only the picking cart under the start. 
	 * Renew a wireless flag of RFT information to one when there is no response. 
	 * <BR>
	 * Acquire the living confirmation from WMSparam at flag and intervals effective/invalid. 
	 * Execute the following processing at the intervals when it is effective. 
	 * 
	 * <OL>
	 *  <LI>Retrieve RFT information, and acquire the list of Internet Protocol address of the picking cart. 
	 *        ({@link #getTerminalList() getTerminalList()}) </LI>
	 *  <LI>Execute ping for acquired Internet Protocol address and confirm whether it is possible to communicate. 
	 *        ({@link #isReachable(String) isReachable()}) </LI>
	 *  <LI>Update RFT information while communicating the acquisition. 
	 *        ({@link #updateCommunicationCondition(Rft) updateCommunicationCondition()}) </LI>
	 * </OL>
	 */
	public void run()
	{
		//#CM643872
		// Check to which polling effective is invalid
		if(! WmsParam.RFT_KEEP_ALIVE_POLLING_ENABLE)
		{
			return;
		}

		//#CM643873
		// Acquisition at polling intervals
		int polling = DEFAULT_POLLING_TIME;
		try
		{
			polling = WmsParam.RFT_KEEP_ALIVE_POLLING_TIME;
		}
		catch (NumberFormatException e)
		{
			//#CM643874
			// Use the initial value when failing in the acquisition of the polling time. 
		}
		polling = polling * 1000;
		
		try
		{
		
			while(true)
			{
				//#CM643875
				// Acquisition of list of picking cart under start
				Rft rftInfo[] = (Rft[])getTerminalList();
				
				if (rftInfo != null)
				{
					for(int i = 0; i < rftInfo.length; i++)
					{
						//#CM643876
						// Flag whether being possible to communicate
						boolean flag = isReachable(rftInfo[i].getIpAddress());
					
						//#CM643877
						// When it is possible to communicate
						if(flag)
						{
							rftInfo[i].setRadioFlag(Rft.RADIO_FLAG_IN);
						}
						//#CM643878
						// When it is not possible to communicate
						else
						{
							rftInfo[i].setRadioFlag(Rft.RADIO_FLAG_OUT);	
						}
						
						//#CM643879
						// Renewal of wireless status flag
						updateCommunicationCondition(rftInfo[i]);
					}
				}
				
				//#CM643880
				// Standby at defined intervals
				Thread.sleep(polling);
			}
			
		}
		catch(InterruptedException e)
		{
			//#CM643881
			// End the thread when there is interrupt. 
		}
	}

	//#CM643882
	/**
	 * Acquire the list of Terminal information which confirms the communication. <BR>
	 * Retrieve RFT information, and acquire the list of the picking cart under the start. <BR>
	 * <BR>
	 * [Search condition]
	 * Status flag: It is starting. 
	 * Terminal flag: Picking Cart
	 * 
	 * @return	Array of RFT information
	 */
	protected Entity[] getTerminalList()
	{
		RftHandler rftHandler = new RftHandler(wConn);
		RftSearchKey skey = new RftSearchKey();
		Entity[] terminalList = null;

		try
		{
			//#CM643883
			//-----------
			//#CM643884
			// Search condition
			//#CM643885
			//-----------
			skey.setStatusFlag(Rft.RFT_STATUS_FLAG_START);
			skey.setTerminalType(Rft.TERMINAL_TYPE_PCART);
			
			terminalList = rftHandler.find(skey);
		} 
		catch (ReadWriteException e)
		{
			RftLogMessage.printStackTrace(
					6006002,
					LogMessage.F_ERROR,
					CLASS_NAME,
					e);
		}
		return terminalList;
	}
	
	//#CM643886
	/**
	 * Update the communication of RFT information. <BR>
	 * <BR>
	 * [Search condition] <BR>
	 * Terminal No.<BR>
	 *
	 * <BR>
	 * [Content of update] <BR>
	 * Wireless Status flag<BR>
	 * Last update processing name
	 * 
	 * @param rftInfo	Terminal information
	 */
	protected void updateCommunicationCondition(Rft rftInfo) 
	{
		RftHandler rftHandler = new RftHandler(wConn);
		RftAlterKey akey = new RftAlterKey();
		try 
		{
			//#CM643887
			//-----------------
			//#CM643888
			// Search condition
			//#CM643889
			//-----------------
			akey.setRftNo(rftInfo.getRftNo());		
			//#CM643890
			//-----------------
			//#CM643891
			// Content of update
			//#CM643892
			//-----------------
			akey.updateRadioFlag(rftInfo.getRadioFlag());
			akey.updateLastUpdatePname(CLASS_NAME);
			
			//#CM643893
			// Renewal of Status flag which can be communicated
			rftHandler.modify(akey);
			
			wConn.commit();
		}
		catch (ReadWriteException e) 
		{
			RftLogMessage.printStackTrace(
					6006002,
					LogMessage.F_ERROR,
					CLASS_NAME,
					e);
		}
		catch (NotFoundException e) 
		{
			//#CM643894
			// Do not do anything here because the log is output in the generation origin of the exception. 
		}
		catch (InvalidDefineException e) 
		{
			//#CM643895
			// Do not do anything here because the log is output in the generation origin of the exception. 
		}
		catch (SQLException e)
		{
			RftLogMessage.printStackTrace(
					6006002,
					LogMessage.F_ERROR,
					CLASS_NAME,
					e);
		}
	}
	
	//#CM643896
	/**
	 * It is examined whether the terminal of specified Internet Protocol address can be communicated. 
	 * 
	 * @param ipAddress		IP Address
	 * @return		Return True when it is possible to communicate, False when it is not so. 
	 */
	public boolean isReachable(String ipAddress)
	{
		String line;				
		
		try
		{
			//#CM643897
			// Execute the ping command. 
			Process proc = Runtime.getRuntime().exec(PING_COMMAND + " " + ipAddress);

			//#CM643898
			// Read the output of the ping command. 
			InputStream in = proc.getInputStream();
			BufferedReader din = new BufferedReader(new InputStreamReader(in));
			while ((line = din.readLine()) != null)
			{
				//#CM643899
				// Check whether there was a response from the output of ping. 
				boolean isAlive = line.matches(".*[rR][eE][pP][lL][yY] [fF][rR][oO][mM].*");
				if (isAlive)
				{
					return true;
				}
			}
		}
		catch (IOException ioe)
		{
			return false;
		}
		catch (IllegalArgumentException iae)
		{
			return false;
		}

		return false;
	}
	
	//#CM643900
	//Package methods -----------------------------------------------

	//#CM643901
	//Protected methods ---------------------------------------------
	
	//#CM643902
	//Private methods -----------------------------------------------
	
}
//#CM643903
//end of class
