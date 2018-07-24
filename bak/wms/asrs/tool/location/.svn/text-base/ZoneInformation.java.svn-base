// $Id: ZoneInformation.java,v 1.2 2006/10/30 02:33:22 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM50037
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;

//#CM50038
/**<en>
 * This class controls the zone master information.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:22 $
 * @author  $Author: suresh $
 </en>*/
public class ZoneInformation extends ToolEntity
{
	//#CM50039
	// Class fields --------------------------------------------------
	//#CM50040
	/**<en>
	 * Field of zone type (hard zone)
	 </en>*/
	public static final int HARD = 1;

	//#CM50041
	/**<en>
	 * Field of zone type (soft zone)
	 </en>*/
	public static final int SOFT = 2;


	//#CM50042
	// Class variables -----------------------------------------------
	//#CM50043
	/**<en>
	 * zone no.
	 </en>*/
	protected int wZoneID ;

	//#CM50044
	/**<en>
	 * name of zone
	 </en>*/
	protected String wZoneName ;

	//#CM50045
	/**<en>
	 * operation type
	 </en>*/
	protected int wType ;

	//#CM50046
	/**<en>
	 * Delimiters
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM50047
	// Class method --------------------------------------------------
	//#CM50048
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:22 $") ;
	}

	//#CM50049
	// Constructors --------------------------------------------------

	//#CM50050
	// Public methods ------------------------------------------------

	//#CM50051
	/**<en>
	 * Set the zone no.
	 * @param zid   :zone no. to set
	 </en>*/
	public void setZoneID(int zid)
	{
		wZoneID = zid ;
	}

	//#CM50052
	/**<en>
	 * Retrieve the zone no.
	 * @return    :zone no.
	 </en>*/
	public int getZoneID()
	{
		return wZoneID ;
	}

	//#CM50053
	/**<en>
	 * Set the name of zone.
	 * @param nm :name of zone to set
	 </en>*/
	public void setZoneName(String nm)
	{
		wZoneName = nm ;
	}

	//#CM50054
	/**<en>
	 * Retrieve the name of zone.
	 * @return    :name of zone
	 </en>*/
	public String getZoneName()
	{
		return(wZoneName) ;
	}

	//#CM50055
	/**<en>
	 * Set the operation type.
	 * @param type :operation type
     * @throws InvalidStatusException :Notifies if contents of type is invalid.
	 </en>*/
	public void setType(int type) throws InvalidStatusException
	{
		//#CM50056
		//<en> Check the operation type.</en>
		switch (type)
		{
			//#CM50057
			//<en> List of correct operation type</en>
			case HARD:
			case SOFT:
				break ;
				
			//#CM50058
			//<en> It lets occur the exception if incorrect operation type was to set. It will not alter the operation type.</en>
			default:
				//#CM50059
				//<en> 6126009=Undefined {0} is set.</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = "TYPE";
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, this.getClass().getName(), tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0])) ;
		}
		
		//#CM50060
		//<en> Modify the operation type.</en>
		wType = type ;
	}

	//#CM50061
	/**<en>
	 * Retrieve the operation type.
	 * @return :operation type
	 </en>*/
	public int getType()
	{
		return(wType) ;
	}

	//#CM50062
	/**<en>
	 * Return the string representation of ZoneInformation.
	 * @return    string representation
	 </en>*/
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		buf.append("\nZoneID:" + Integer.toString(wZoneID)) ;
		buf.append("\nZoneName:" + wZoneName) ;
		buf.append("\nType:" + Integer.toString(wType)) ;
		return buf.toString() ;
	}

	//#CM50063
	// Package methods -----------------------------------------------

	//#CM50064
	// Protected methods ---------------------------------------------

	//#CM50065
	// Private methods -----------------------------------------------

}
//#CM50066
//end of class

