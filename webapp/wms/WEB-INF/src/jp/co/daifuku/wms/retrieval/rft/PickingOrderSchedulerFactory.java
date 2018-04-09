//#CM721168
//$Id: PickingOrderSchedulerFactory.java,v 1.3 2007/02/07 04:19:43 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;

//#CM721169
/**
 * Designer : etakeda <BR>
 * Maker :   <BR>
 * <BR>
 * Allow this class to generate an instance of the class that executes the process for determining the Order No. to specify Area.<BR>
 * 
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:43 $
 * @author  $Author: suresh $
 */
public class PickingOrderSchedulerFactory
{
	//#CM721170
	/**
	 * A field that represents a super class to determine the Order No. for specifying Area.
	 */
	private static final String PICKING_ORDER_SCHEDULER = "PickingOrderScheduler";

	//#CM721171
	/**
	 * A field that represents a name of package which stores derived classes to determine the Order No. for specifying Area.
	 */
	private static final String PACKAGE_NAME = "jp.co.daifuku.wms.retrieval.rft";

	//#CM721172
	// Class methods -------------------------------------------------
	//#CM721173
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:43 $");
	}

	//#CM721174
	/**
	 * Generate the instance of the class for process to determine the Area-specified Order No. defined in WmsParam, and return it.
	 * <OL><LI>Obtain the value defined for the <CODE>AREA_PICKING_ORDER_SCHEDULER</CODE> field item, from WMSParam.
	 * 		  	({@link jp.co.daifuku.wms.base.common.WmsParam#AREA_PICKING_ORDER_SCHEDULER WmsParam.AREA_PICKING_ORDER_SCHEDULER})
	 *	   <LI>Use the determinant super class of Order No. to specify area, if no value is defined for the <CODE>AREA_PICKING_ORDER_SCHEDULER</CODE> field item.
	 *			({@link jp.co.daifuku.wms.retrieval.rft.PickingOrderScheduler PickingOrderScheduler})
	 *	   <LI>Generate the obtained class instance.
	 * 
	 * @return				Instance of the class to determine the Order No. for specifying Area
	 * @throws IllegalAccessException	Announce when failed to generate instance.
	 */
	public static Object getInstance() throws IllegalAccessException
	{
		//#CM721175
		// Obtain the Class Name of the derived class to determine the Order No. for the defined Specify Area, from Wmsparam.
		String className = WmsParam.AREA_PICKING_ORDER_SCHEDULER;
		try
		{
			Class cl = null;
			if (StringUtil.isBlank(className))
			{
				//#CM721176
				// If not defined in AREA_PICKING_ORDER_SCHEDULER, generate a super class instance.
				cl = Class.forName(PACKAGE_NAME + "."
						+ PICKING_ORDER_SCHEDULER);
			}
			else
			{
				cl = Class.forName(PACKAGE_NAME + "." + className);
			}
			Object obj = cl.newInstance();
			return obj;

		}
		catch (Exception e)
		{

			throw new IllegalAccessException();

		}

	}

}
