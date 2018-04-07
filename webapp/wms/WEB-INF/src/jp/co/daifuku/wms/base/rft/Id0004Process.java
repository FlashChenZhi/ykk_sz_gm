//#CM700690
//$Id: Id0004Process.java,v 1.2 2006/11/14 06:08:59 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM700691
/**
 * Process it to the online confirmation request by RFT. <BR>
 * Response flag is 0 when online Return (Normal). 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:59 $
 * @author  $Author: suresh $
 */
public class Id0004Process extends IdProcess
{
	//#CM700692
	// Class fields----------------------------------------------------
	//#CM700693
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0004Process";

	//#CM700694
	// Class variables -----------------------------------------------

	//#CM700695
	// Class method --------------------------------------------------
	//#CM700696
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:08:59 $";
	}

	//#CM700697
	// Constructors --------------------------------------------------
	//#CM700698
	/**
	 * Generate the instance. 
	 */
	public Id0004Process()
	{
		super() ;
	}
	
	//#CM700699
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0004Process(Connection conn)
	{
		super() ;
		wConn = conn ;
	}

	//#CM700700
	// Public methods ------------------------------------------------
	//#CM700701
	/**
	 * Process online confirmation telegram. 
	 * 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM700702
		// The instance for reception telegram analysis is generated. 
		RFTId0004 rftId0004 = null;

		//#CM700703
		// The instance for sending telegram making is generated. 
		RFTId5004 rftId5004 = null;

		try
		{
			//#CM700704
			// The instance for reception telegram analysis is generated. 
			rftId0004 = (RFTId0004) PackageManager.getObject("RFTId0004");
			rftId0004.setReceiveMessage(rdt);

			//#CM700705
			// The instance for sending telegram making is generated. 
			rftId5004 = (RFTId5004) PackageManager.getObject("RFTId5004");
		}
		catch (IllegalAccessException e)
		{
        	//#CM700706
        	// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*004");
			throw e;
		}
		//#CM700707
		// RFT machine is acquired from Receiving telegram. 
		String rftNo = rftId0004.getRftNo();

		//#CM700708
		// Response telegram making
		//#CM700709
		// STX
		rftId5004.setSTX();
		
		//#CM700710
		// SEQ
		rftId5004.setSEQ(0);
		
		//#CM700711
		// ID
		rftId5004.setID(RFTId5004.ID);
		
		//#CM700712
		// RFT transmission time
		rftId5004.setRftSendDate(rftId0004.getRftSendDate());
		
		//#CM700713
		// SERVER transmission time
		rftId5004.setServSendDate();
		
		//#CM700714
		// RFT machine
		rftId5004.setRftNo(rftNo);
		
		//#CM700715
		// Response flag
		rftId5004.setAnsCode(RFTId5004.ANS_CODE_NORMAL);
		
		//#CM700716
		// ETX
		rftId5004.setETX() ;

		//#CM700717
		// Acquire response telegram. 
		rftId5004.getSendMessage(sdt);

	}

	//#CM700718
	// Package methods -----------------------------------------------

	//#CM700719
	// Protected methods ---------------------------------------------

	//#CM700720
	// Private methods -----------------------------------------------
}
//#CM700721
//end of class
