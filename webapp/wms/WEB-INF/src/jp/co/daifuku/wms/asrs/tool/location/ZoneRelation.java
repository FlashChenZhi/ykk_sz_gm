// $Id: ZoneRelation.java,v 1.2 2006/10/30 02:33:21 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM50067
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

//#CM50068
/**<en>
 * This class links the item classification information with the zone information, and
 * controls the zone information that assigned with classifications.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/08/06</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:21 $
 * @author  $Author: suresh $
 </en>*/
public class ZoneRelation extends ToolEntity
{
	//#CM50069
	// Class fields --------------------------------------------------

	//#CM50070
	// Class variables -----------------------------------------------
	//#CM50071
	/**<en>
	 * Soft zone no.
	 </en>*/
	protected int wSoftZoneId ;
	
	//#CM50072
	/**<en>
	 * Classification ID
	 </en>*/
	protected int wClassificationId ;

	//#CM50073
	/**<en>
	 * Priorrity of zone search
	 </en>*/
	protected int wPriority;

	//#CM50074
	// Class method --------------------------------------------------
	//#CM50075
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:21 $") ;
	}

	//#CM50076
	// Constructors --------------------------------------------------
	//#CM50077
	/**<en>
	 * Construct new <CODE>ZoneRelation</CODE>.
	 </en>*/
	public ZoneRelation()
	{
	}

	//#CM50078
	// Public methods ------------------------------------------------
	//#CM50079
	/**<en>
	 * Set the soft zone ID.
	 * @param id :soft zone ID
	 </en>*/
	public void setSoftZoneId(int id)
	{
		wSoftZoneId = id;
	}

	//#CM50080
	/**<en>
	 * Retrieve the soft zone ID.
	 * @return :soft zone ID
	 </en>*/
	public int getSoftZoneId()
	{
		return wSoftZoneId;
	}

	//#CM50081
	/**<en>
	 * Set the classification ID.
	 * @param id :classification ID to set
	 </en>*/
	public void setClassificationId(int id)
	{
		wClassificationId = id ;
	}

	//#CM50082
	/**<en>
	 * Retrieve the classification ID.
	 * @return :classification ID
	 </en>*/
	public int getClassificationId()
	{
		return wClassificationId ;
	}

	//#CM50083
	/**<en>
	 * Set the priority of zone search.
	 * @param pri :the priority of zone search to set
	 </en>*/
	public void setPriority(int pri)
	{
		wPriority = pri ;
	}

	//#CM50084
	/**<en>
	 * Retrieve the priority of zone search.
	 * @return :the priority of zone search
	 </en>*/
	public int getPriority()
	{
		return wPriority ;
	}

	//#CM50085
	// Package methods -----------------------------------------------

	//#CM50086
	// Protected methods ---------------------------------------------

	//#CM50087
	// Private methods -----------------------------------------------

}
//#CM50088
//end of class

