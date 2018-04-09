// $Id: StationType.java,v 1.2 2006/10/30 02:33:24 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49852
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.common.MessageResource;

//#CM49853
/**<en>
 * This class is used to control the informatin of station types according to  
 * the station numbers.<BR>
 * Stations possess the unique numbers. These numbers consist of any alphanumerics and 
 * are handled as strings.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:24 $
 * @author  $Author: suresh $
 </en>*/
public class StationType extends ToolEntity
{
	//#CM49854
	// Class fields --------------------------------------------------

	//#CM49855
	/**<en>
	 * StationNumber Length
	 </en>*/
	public static final int STATIONNUMBER_LEN = 4 ;

	//#CM49856
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM49857
	// Class variables -----------------------------------------------
	//#CM49858
	/**<en>
	 * Preserve the station no.
	 </en>*/
	protected String wNumber = "" ;

	//#CM49859
	/**<en>
	 * Preserve the corresponding HandlerClass.
	 </en>*/
	protected String wHandlerClass = "" ;

	//#CM49860
	// Class method --------------------------------------------------
	//#CM49861
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:24 $") ;
	}

	//#CM49862
	// Constructors --------------------------------------------------
	//#CM49863
	/**<en>
	 * Construct new <CODE>StationType</CODE>.
	 </en>*/
	public StationType()
	{
	}
	
	//#CM49864
	// Public methods ------------------------------------------------
	//#CM49865
	/**<en>
	 * Retrieve the station no.
	 * @return    :station no.
	 </en>*/
	public String getNumber()
	{
		return(wNumber) ;
	}

	//#CM49866
	/**<en>
	 * Set the station no.
	 * @param  arg  : station no.
	 </en>*/
	public void setStationNumber(String arg)
	{
		wNumber = arg;
	}

	//#CM49867
	/**<en>
	 * Retrieve the handler class.
	 * @return    handler class
	 </en>*/
	public String getHandlerClass()
	{
		return(wHandlerClass) ;
	}
	
	//#CM49868
	/**<en>
	 * Set the handler class.
	 * @param  arg  : handler class
	 </en>*/
	public void setHandlerClass(String arg)
	{
		wHandlerClass = arg;
	}

	//#CM49869
	/**<en>
	 * Return the string representation.
	 * @return    string representation
	 </en>*/
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		try
		{
			buf.append("\nStation Number:" + wNumber) ;
			buf.append("\nHandler Class:" + wHandlerClass) ;
		}
		catch (Exception e)
		{
		}
		
		return (buf.toString()) ;
	}

	//#CM49870
	// Package methods -----------------------------------------------

	//#CM49871
	// Protected methods ---------------------------------------------

	//#CM49872
	// Private methods -----------------------------------------------

}
//#CM49873
//end of class

