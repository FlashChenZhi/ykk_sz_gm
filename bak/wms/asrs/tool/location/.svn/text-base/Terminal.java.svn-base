// $Id: Terminal.java,v 1.2 2006/10/30 02:33:23 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49874
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

//#CM49875
/**<en>
 * This class controls the terminals.
 * The terminal numbers are assigned to each terminal.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:23 $
 * @author  $Author: suresh $
 </en>*/
public class Terminal extends ToolEntity
{
	//#CM49876
	// Class fields --------------------------------------------------

	//#CM49877
	// Class variables -----------------------------------------------
	//#CM49878
	/**<en>
	 * terminal no.
	 </en>*/
	protected String wTerminalNumber ;
	
	//#CM49879
	/**<en>
	 * name of the terminal
	 </en>*/
	protected String wTerminalName ;

	//#CM49880
	/**<en>
	 * IP address
	 </en>*/
	protected String wIPAddress ;

	//#CM49881
	/**<en>
	 * printer (name)
	 </en>*/
	protected String wPrinterName ;

	//#CM49882
	/**<en>
	 * role ID
	 </en>*/
	protected String wRoleId ;

	//#CM49883
	// Class method --------------------------------------------------
	//#CM49884
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:23 $") ;
	}

	//#CM49885
	// Constructors --------------------------------------------------
	//#CM49886
	/**<en>
	 * Construct the new <CODE>Terminal</CODE>.
	 </en>*/
	public Terminal()
	{
	}
	
	//#CM49887
	/**<en>
	 * Construct the new <CODE>Terminal</CODE>.
	 * @param terminalno     :terminal no.
	 * @param terminalname   :name of the terminal
	 * @param ip             :IP address
	 * @param terminalname   :printer name
	 * @param terminalname   :role ID
	 </en>*/
	public Terminal(String 		terminalno,
					String		terminalname,
					String 		ip,
					String 		printer,
					String      roleid
			    )
	{
		//#CM49888
		//<en> Set as an instance variable.</en>
		setTerminalNumber(terminalno);
		setTerminalName(terminalname);
		setIPAddress(ip);
		setPrinterName(printer);
		setRoleId(roleid);
	}

	//#CM49889
	// Public methods ------------------------------------------------
	//#CM49890
	/**<en>
	 * Set the terminal no.
	 * @param terminalnum :terminal no.
	 </en>*/
	public void setTerminalNumber(String terminalnum)
	{
		wTerminalNumber = terminalnum;
	}

	//#CM49891
	/**<en>
	 * Retrieve the terminal no.
	 * @return :terminal no.
	 </en>*/
	public String getTerminalNumber()
	{
		return wTerminalNumber;
	}

	//#CM49892
	/**<en>
	 * Retrieve the name of the terminal.
	 * @return :name of the terminalNo.
	 </en>*/
	public String getTerminalName()
	{
		return wTerminalName;
	}

	//#CM49893
	/**<en>
	 * Set the name of the terminal.
	 * @param terminalname :name of the terminal
	 </en>*/
	public void setTerminalName(String terminalname)
	{
		wTerminalName = terminalname;
	}

	//#CM49894
	/**<en>
	 * Set the IP address.
	 * @param ipaddress   :IP address to set
	 </en>*/
	public void setIPAddress(String ipaddress)
	{
		wIPAddress = ipaddress ;
	}

	//#CM49895
	/**<en>
	 * Retrieve the IP address.
	 * @return    :IP address
	 </en>*/
	public String getIPAddress()
	{
		return wIPAddress ;
	}

	//#CM49896
	/**<en>
	 * Set the printer (name).
	 * @param printer   :printer (name) to set
	 </en>*/
	public void setPrinterName(String printer)
	{
		wPrinterName = printer ;
	}

	//#CM49897
	/**<en>
	 * Retrieve the printer (name).
	 * @return    printer (name)
	 </en>*/
	public String getPrinterName()
	{
		return wPrinterName ;
	}

	//#CM49898
	/**<en>
	 * Set the role ID.
	 * @param id   :role ID to set
	 </en>*/
	public void setRoleId(String id)
	{
		wRoleId = id ;
	}

	//#CM49899
	/**<en>
	 * Retrieve the role ID.
	 * @return    role ID
	 </en>*/
	public String getRoleId()
	{
		return wRoleId ;
	}

	//#CM49900
	/**<en>
	 * Return the string representation of Terminal.
	 * @return    string representation
	 </en>*/
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		buf.append("\nTerminalNumber:" + wTerminalNumber) ;
		buf.append("\nTerminalName:" + wTerminalName) ;
		buf.append("\nIPAddress:" + wIPAddress) ;
		buf.append("\nPrinterName:" + wPrinterName) ;
		return buf.toString() ;
	}

	//#CM49901
	// Package methods -----------------------------------------------

	//#CM49902
	// Protected methods ---------------------------------------------

	//#CM49903
	// Private methods -----------------------------------------------

}
//#CM49904
//end of class

