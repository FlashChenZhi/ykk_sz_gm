// $Id: CarryInformationController.java,v 1.2 2006/10/26 01:07:49 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30959
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;

//#CM30960
/**
 * This class preserves the main method of starting the task for carrying instruction and retrieval instruction.<BR>
 * For processing these instructions, either single deep task or double deep task depending on the facility layouts.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:07:49 $
 * @author  $Author: suresh $
 */
public class CarryInformationController extends Object
{
	//#CM30961
	// Class fields --------------------------------------------------

	//#CM30962
	// Class variables -----------------------------------------------

	//#CM30963
	// Class method --------------------------------------------------
	//#CM30964
	/**
	 * Returnes the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:07:49 $") ;
	}

	//#CM30965
	// Constructors --------------------------------------------------

	//#CM30966
	// Public methods ------------------------------------------------
	//#CM30967
	/**
	 * Starts up the process of carrying instruction and retrieval instruction as treads.
	 */
	public static void main(String[] args)
	{
		//#CM30968
		// Connection with thd database
		Connection conn = null;

		//#CM30969
		// Time keeping
		TimeKeeper timekeeper = null;

		//#CM30970
		// Transmission of the carrying instruction
		StorageSender   sSend = null;

		//#CM30971
		// Transmission of the retrieval instruction
		RetrievalSender rSend = null;

		//#CM30972
		// Transmission of the carrying instruction with automatic mode switching
		AutomaticModeChangeSender srSend = null;

		try
		{
			//#CM30973
			// Generates the connection. Is used only in this method.
			conn = AsrsParam.getConnection();
			//#CM30974
			// Loads from the resource file the interval time (ms) to be taken during the reading of carrying 
			// instruction and retrieval instruction.
			int wSec = AsrsParam.INSTRUCT_CONTROL_SLEEP_SEC;
			//#CM30975
			// While the definition of aisle, it checks whether/not the aisle for double deep use is included.
			AisleHandler ahandl = new AisleHandler(conn);
			AisleSearchKey akey = new AisleSearchKey();
			Aisle[] aisles = (Aisle[])ahandl.find(akey);

			boolean dExist = false;
			for (int i = 0 ; i < aisles.length ; i++)
			{
				if (aisles[i].getDoubleDeepKind() == Aisle.DOUBLE_DEEP)
				{
					dExist = true;
					break;
				}
			}
			if (dExist)
			{
				//#CM30976
				// If the aisle of double deep layout is included,
				//#CM30977
				// it starts up the threads for double deep of transmission(in/out) of carrying.
				sSend = new DoubleDeepStorageSender();
				new Thread(sSend).start();
				rSend = new DoubleDeepRetrievalSender();
				new Thread(rSend).start();
				//#CM30978
				// double deep correspondence
				//#CM30979
				// Starts the carrying instruction with automatic mode switch.
				srSend = new AutomaticModeChangeSender();
				new Thread(srSend).start();
			}
			else
			{
				//#CM30980
				// Starts the carrying instruction.
				sSend = new StorageSender();
				new Thread(sSend).start();
				//#CM30981
				// Starts the retrieval instruction.
				rSend = new RetrievalSender();
				new Thread(rSend).start();
				//#CM30982
				// Starts the carrying instruction with automatic mode switch.
				srSend = new AutomaticModeChangeSender();
				new Thread(srSend).start();
			}
			//#CM30983
			// Starts the time keeping.
			timekeeper = new TimeKeeper(wSec, sSend, rSend, srSend);
			new Thread(timekeeper).start();
			//#CM30984
			// Closes; the connection generated here is no longer necessary.
			conn.close();
		}
		catch (SQLException e)
		{
			Object[] tObj = new Object[1];
			tObj[0] = Integer.toString(e.getErrorCode());
			//#CM30985
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "CarryInformationController", tObj);
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
			//#CM30986
			//Records the errors in the log file.
			//#CM30987
			// 6026041=Failed to execute the transfer/picking instruction.
			e.printStackTrace(pw);
			Object[] tObj = new Object[1];
			tObj[0] = stcomment + sw.toString();
			RmiMsgLogClient.write(6026041, LogMessage.F_ERROR, "CarryInformationController", tObj);
		}
	}

	//#CM30988
	// Package methods -----------------------------------------------

	//#CM30989
	// Protected methods ---------------------------------------------

	//#CM30990
	// Private methods -----------------------------------------------

}
//#CM30991
//end of class
