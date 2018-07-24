// $Id: PackageManager.java,v 1.2 2006/11/07 06:49:16 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.util.MissingResourceException;
import java.util.Vector;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

//#CM643904
/**
 * Manage the package of WMS and the package of JAVA corresponding to it. 
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:49:16 $
 * @author  $Author: suresh $
 */
public class PackageManager
{
	//#CM643905
	// Class fields----------------------------------------------------
	//#CM643906
	/**
	 * Package name of DAIFUKU package
	 */
	static final String DAIFUKU_PACKAGE = "jp.co.daifuku";

	//#CM643907
	/**
	 * Base name of package name in JAVA
	 */
	static final String PACKAGE_BASENAME = DAIFUKU_PACKAGE + "." + "wms";
	
	//#CM643908
	/**
	 * Package name in JAVA of WMS system package
	 */
	static final String SYSTEM_PACKAGE = "base";
	
	//#CM643909
	/**
	 * Package name in JAVA of package of WMS Receiving
	 */
	static final String INSTOCK_PACKAGE = "instockreceive";

	//#CM643910
	/**
	 * Package name in JAVA of package of WMS Storage
	 */
	static final String STORAGE_PACKAGE = "storage";

	//#CM643911
	/**
	 * Package name in JAVA of package of WMS Picking
	 */
	static final String RETRIEVAL_PACKAGE = "retrieval";

	//#CM643912
	/**
	 * Package name in JAVA of package of WMS Sorting
	 */
	static final String PICKING_PACKAGE = "sorting";

	//#CM643913
	/**
	 * Package name in JAVA of package of WMS Shipping
	 */
	static final String SHIPPING_PACKAGE = "shipping";
	
	//#CM643914
	/**
	 * Package name in JAVA of package of WMS Stock
	 */
	static final String STOCK_PACKAGE = "stockcontrol";
	
	//#CM643915
	/**
	 * Package name in JAVA of package of WMS Movement Rack
	 */
	static final String IDM_PACKAGE = "idm";

	//#CM643916
	/**
	 * RFT package name in JAVA
	 */
	static final String RFT_PACKAGE_NAME = "rft";

	//#CM643917
	/**
	 * Connected character of package in JAVA
	 */
	static final String PACKAGE_SEPERATOR = ".";

	//#CM643918
	/**
	 * Package Name
	 */
	private static final String[][] PackageName = {
			{"INSTOCK_PACK", INSTOCK_PACKAGE},
			{"STORAGE_PACK", STORAGE_PACKAGE},
			{"RETRIEVAL_PACK", RETRIEVAL_PACKAGE},
			{"PICKING_PACK", PICKING_PACKAGE},
			{"SHIPPING_PACK", SHIPPING_PACKAGE},
			{"STOCK_PACK", STOCK_PACKAGE},
			{"IDM_PACK", IDM_PACKAGE}
		};
		
	static final private String CLASS_NAME = "PackageManager";

	//#CM643919
	// Class variables -----------------------------------------------
	//#CM643920
	/**
	 * List of Package Names of JAVA
	 */
	private static String[] packageList = null;

	//#CM643921
	/**
	 * Connection
	 */
	private static Connection wConn = null;
	
	//#CM643922
	/**
	 * Enhancing Package Name for Product Number
	 */
	private static String extendedPackageName = null;

	//#CM643923
	// Class method --------------------------------------------------
	//#CM643924
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/07 06:49:16 $";
	}

	//#CM643925
	/**
	 * Initialize the package manager. 
	 * @param conn Database connection
	 */
	public static void initialize(Connection conn)
	{
		wConn = conn;
		
		try
		{
			extendedPackageName = WmsParam.WMS_EXTENDED_PACKAGE;
		}
		catch (MissingResourceException e)
		{
			extendedPackageName = null;
		}

		createPackageList();
	}

	//#CM643926
	/**
	 * Against the package of effective WMS by a present system construction. 
	 * Acquire List of Package Names of JAVA, and return it to the call origin. <BR>
	 * CODE>packageList</CODE <> acquires the installation from WareNavi_System only in case of CODE>null</CODE <>.
	 * Generate the list of effective Package Name. <BR>
	 * Return the package list which has already been acquired when it is not <CODE>null</CODE>. 
	 * 
	 * @return List of JAVA Package Name  
	 */
	public static String[] getPackageList()
	{
		if (packageList == null)
		{
				createPackageList();
		}
		return packageList;
	}

	//#CM643927
	/**
	 * Generate the instance of the specified class name and return it. 
	 * 
	 * @param className		Class name of generated instance
	 * @return				Instance newly generated
	 * @throws IllegalAccessException	When failing in the generation of the instance is notified. 
	 */
	public static Object getObject(String className) throws IllegalAccessException
	{
		getPackageList();
		
		//#CM643928
		// Make the processing instance. 
		//#CM643929
		// Open the loading data file. 
		for (int i = 0; i < packageList.length; i ++)
		{
			try
			{
				Class cl = Class.forName(packageList[i] +  "." + className);
				Object obj = cl.newInstance();
				return obj;
			}
			catch (Exception e)
			{
				//#CM643930
				// When failing in the generation of the instance tries the following package. 
				continue;
			}
		}

		//#CM643931
		// When failing in the generation of the instance throws out the exception 
		//#CM643932
		// in all effective packages.
		throw new IllegalAccessException();
	}
	
	//#CM643933
	/**
	 * Generate the instance of the array of the specified class name and return it. 
	 * 
	 * @param className		Class name of generated instance
	 * @param size			Size of generated array
	 * @return				Instance newly generated
	 * @throws IllegalAccessException	When failing in the generation of the instance is notified. 
	 */
	public static Object getArrayObject(String className, int size) throws IllegalAccessException
	{
		getPackageList();
		
		//#CM643934
		// Make the processing instance. 
		//#CM643935
		// Open the loading data file. 
		for (int i = 0; i < packageList.length; i ++)
		{
			try
			{
				Class cl = Class.forName(packageList[i] +  "." + className);
				Object obj = Array.newInstance(cl, size);
				return obj;
			}
			catch (Exception e)
			{
				//#CM643936
				// When failing in the generation of the instance tries the following package. 
				continue;
			}
		}

		//#CM643937
		// When failing in the generation of the instance throws out the exception 
		//#CM643938
		// in all effective packages. 
		throw new IllegalAccessException();
	}
	
	//#CM643939
	// Constructors --------------------------------------------------
	//#CM643940
	/**
	 * Default Constructor of this class. <BR>
	 * It is not possible to use even if package manager's instance is not 
	 * made, and do not make the instance. <BR>
	 * At present, change Constructor to private though it does not mung Constructor for the 
	 * interchangeability maintenance according to suitable timing. 
	 * @param conn Database connection
	 */
	public PackageManager(Connection conn)
	{
		wConn = conn;
		
		try
		{
			extendedPackageName = WmsParam.WMS_EXTENDED_PACKAGE;
		}
		catch (MissingResourceException e)
		{
			extendedPackageName = null;
		}
	}	
	//#CM643941
	/**
	 * Examine the installed package from the WareNavi_System table of DB, and 
	 * generate List of Package Names of JAVA corresponding to it. 
	 */
	private static void createPackageList()
	{	
		//#CM643942
		// Acquire the record from WareNavi_System. 
		WareNaviSystemHandler wh = new WareNaviSystemHandler(wConn);
		WareNaviSystem wns = new WareNaviSystem();
		try
		{
			wns = (WareNaviSystem) wh.findPrimary(new WareNaviSystemSearchKey());
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6026014, LogMessage.F_ERROR, CLASS_NAME, e);
			packageList = null;
			return;
		}
		//#CM643943
		// Register because the system package is sure to exist. 
		final String PACKAGE_PREFIX = PACKAGE_BASENAME + PACKAGE_SEPERATOR; 
		//#CM643944
		// Generate a variable-length array for the Package Name storage. 
		Vector v = new Vector();
		
		if (extendedPackageName != null && !"".equals(extendedPackageName))
		{	
			String ext = PACKAGE_PREFIX + extendedPackageName.trim() + PACKAGE_SEPERATOR;
			v.add(ext + SYSTEM_PACKAGE + PACKAGE_SEPERATOR + RFT_PACKAGE_NAME);
			//#CM643945
			// Whether it is installed at each package is examined.  (enhancing package for product number)
			for (int i = 0; i < PackageName.length; i ++)
			{
				if (isInstalled(wns, PackageName[i][0]))
				{
					v.add(ext + PackageName[i][1] + PACKAGE_SEPERATOR + RFT_PACKAGE_NAME);
				}
			}
		}
		
		v.add(PACKAGE_PREFIX + SYSTEM_PACKAGE + PACKAGE_SEPERATOR + RFT_PACKAGE_NAME);

		//#CM643946
		// Whether it is installed at each package is examined. (standard package) 
		for (int i = 0; i < PackageName.length; i ++)
		{
			if (isInstalled(wns, PackageName[i][0]))
			{
				v.add(PACKAGE_PREFIX + PackageName[i][1] + PACKAGE_SEPERATOR + RFT_PACKAGE_NAME);
			}
		}

		packageList = new String[v.size()];
		v.copyInto(packageList);
	}
	
	//#CM643947
	/**
	 * Whether a specified package is installed is examined. 
	 * @param wns	System definition information
	 * @param pname	 Package Name
	 * @return	Return True When installed, False when it is not installed. 
	 */
	private static boolean isInstalled(WareNaviSystem wns, String pname)
	{
		String value = null;
		if (pname.equals(PackageName[0][0]))
		{
			value = wns.getInstockPack();
		}
		else if (pname.equals(PackageName[1][0]))
		{
			value = wns.getStoragePack();
		}
		else if (pname.equals(PackageName[2][0]))
		{
			value = wns.getRetrievalPack();
		}
		else if (pname.equals(PackageName[3][0]))
		{
			value = wns.getSortingPack();
		}
		else if (pname.equals(PackageName[4][0]))
		{
			value = wns.getShippingPack();
		}
		else if (pname.equals(PackageName[5][0]))
		{
			value = wns.getStockPack();
			value = WareNaviSystem.PACKAGE_FLAG_ADDON;
		}
		else if (pname.equals(PackageName[6][0]))
		{
			value = wns.getIdmPack();
			value = WareNaviSystem.PACKAGE_FLAG_ADDON;
		}

		if (value != null && value.equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
		{
			return true;
		}
		
		return false;
	}
}
//#CM643948
//end of class
