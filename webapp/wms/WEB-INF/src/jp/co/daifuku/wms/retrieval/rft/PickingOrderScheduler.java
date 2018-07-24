//#CM721157
//$Id: PickingOrderScheduler.java,v 1.3 2007/02/07 04:19:43 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.WorkingInformation;

//#CM721158
/**
 * Designer : etakeda <BR>
 * Maker :   <BR>
 * <BR>
 * Allow this super class to execute the process for determining the Order No. to specify Area.<BR>
 * To change the method to determine the Order No., derive this class, and override the getTergetOrderNomethod method and implement it.<BR>
 * Use the PickingOrderSchedulderFactory class to generate the instance of this class.
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:43 $
 * @author  $Author: suresh $
 */
public class PickingOrderScheduler
{
	//#CM721159
	/**
	 * Connection with database
	 */
	protected Connection conn = null;
	
	//#CM721160
	//Class variables -----------------------------------------------
	//#CM721161
	// Constructors --------------------------------------------------
	//#CM721162
	/**
	 * Constructor<BR>
	 * Generating an object requires to initialize it using initialize() method before using this object.
	 * 
	 */
	public PickingOrderScheduler()
	{
	    
	}
	//#CM721163
	//Class methods -------------------------------------------------
	//#CM721164
	/**
	 * Set <code>Connection</code> for database connection.<BR>
	 * 
	 * @param c Connection for database connection 
	 */
    public void setConnection(Connection c)
    {
        conn = c;
    }
	
	//#CM721165
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.3 $,$Date: 2007/02/07 04:19:43 $");
	}
	  //#CM721166
	  /**
	 * Initialize an instance.<BR>
	 * Set <code>Connection</code> for database connection.<BR>
	 * 
	 * @param c Connection for database connection 
	 * @throws ReadWriteException Announce when error occurs on the connection to the System definition database.
	 */
    public void initialize(Connection c) throws ReadWriteException
    {
        conn = c;
       
        SystemParameter.setConnection(conn);
        
    }
	//#CM721167
	/**
	 * Determine the Order No. to specify Area.<BR>
	 * Obtain the minimum value of Order No. from the work status obtained from parameter.<BR>
	 *
	 * @param	areaNo	Area No.
	 * @param	zoneNo	Zone No.
	 * @param	workinfo	Array of Work Status entity	
	 */
	public String getTargetOrderNo(String areaNo, String zoneNo, WorkingInformation[] workinfo)
	{
	    String orderNo = workinfo[0].getOrderNo();
	    for(int i = 1; i < workinfo.length; i++)
	    {
	        if(orderNo.compareTo(workinfo[i].getOrderNo()) > 0)
	        {
	            orderNo = workinfo[i].getOrderNo();
	        }
	    }
	    return orderNo;
	}
}
