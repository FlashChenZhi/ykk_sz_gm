// $Id: ToolFindUtil.java,v 1.2 2006/10/30 01:40:55 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.common ;

//#CM46732
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;

import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.HardZone;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;

//#CM46733
/**<en>
 * This class provides the search method of general purpose used in screen display 
 * and forms.<BR>
 * It prevents the implementation of search method of identical content by multiple
 * classes and is designed to make it easier to make corrections when bugs occurred
 * by minimizing search as much as possible.
 * As methods implemented in this class increase, it is planned to bind related methods
 * together and respectively transfer to othre classes.
 * Please ensure not to implement methods to the classes all around. Also please ensure
 * to check, when adding methods, if they have already been implemented.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/12</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:40:55 $
 * @author  $Author: suresh $
 </en>*/
public class ToolFindUtil extends Object
{
	//#CM46734
	// Class fields --------------------------------------------------

	//#CM46735
	// Class private fields ------------------------------------------
	//#CM46736
	/**<en> <CODE>Connection</CODE> </en>*/

	private Connection wConn = null ;

	//#CM46737
	// Class method --------------------------------------------------
	//#CM46738
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:40:55 $") ;
	}
	//#CM46739
	/**<en>
	 * Copy the file.
	 * If file path to the copy source, an empty file will be generated.
	 * @param  source :file path to the copy source
	 * @param  dest   file path to the copy destination
	 </en>*/
	public static boolean copyFile(String  source, String dest) 
	{
		try
		{
			int readByte;
			FileOutputStream output = new FileOutputStream(dest);
			if (source!= null && source.length() > 0)
			{
				FileInputStream input = new FileInputStream(source);

				while ((readByte = input.read()) != -1){
					output.write(readByte);
				}
				input.close();
			}
			else
			{
				output.write(0);
			}
			output.close();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}


	//#CM46740
	// Constructors --------------------------------------------------
	//#CM46741
	/**<en>
	 * Retrieve the <CODE>Connection</CODE> and construcrs <CODE>FindUtil</CODE> object.
	 * @param conn <CODE>Connection</CODE>
	 </en>*/
	public ToolFindUtil(Connection conn)
	{
		wConn = conn ;
	}

	//#CM46742
	// Public methods ------------------------------------------------
	
	//#CM46743
	/**<en>
	 * Retrieve the warehouse number based on the specified warehouse station no.
	 * Ex. 1-> 9000
	 * @param  whNo :warehouse no.
	 * @return :warehouse station no.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String getWarehouseStationNumber(int whNo) throws ReadWriteException
	{
		ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
		ToolWarehouseHandler handler =  new ToolWarehouseHandler(wConn);
		key.setWarehouseNumber(whNo);
		Warehouse house [] = (Warehouse[])handler.find(key);
		if (house.length <= 0 )
		{
			return "NG";
		}

		return house[0].getNumber();
	}

	//#CM46744
	/**<en>
	 * Retrieve the warehouse number based on the specified warehouse station no.
	 * Ex. 1-> 9000
	 * @param  whNo :warehouse no.
	 * @return :warehouse station no.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int getWarehouseNumber(String whStNo) throws ReadWriteException
	{
		ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
		ToolWarehouseHandler handler =  new ToolWarehouseHandler(wConn);
		key.setWarehouseStationNumber(whStNo);
		Warehouse house [] = (Warehouse[])handler.find(key);
		if (house.length <= 0 )
		{
			//#CM46745
			//<en>In case the data is not found, irregular value is returned.</en>
			return 9999;
		}

		return house[0].getWarehouseNumber();
	}

	//#CM46746
	/**<en>
	 * Return the name of warehouse by using warehouse no. as a search key.
	 * @param  stno warehouse no.
	 * @return :name of the warehouse
	 * @throws ReadWriteException :Notifies of the exception as it is which occurred in database connection.
	 </en>*/
	public String getWarehouseName(int whno) throws ReadWriteException
	{
		ToolWarehouseSearchKey  key  = new ToolWarehouseSearchKey() ;
		ToolWarehouseHandler handler = new ToolWarehouseHandler(wConn) ;
		key.setWarehouseNumber(whno);
		
		Warehouse[] house = (Warehouse[])handler.find(key);

		if (house.length <= 0 )
		{
			return "";
		}

		return house[0].getName();
	}

	//#CM46747
	/**<en>
	 * Return the name of station by using station no. as a search key.
	 * @param  stno :station no.
	 * @return :name of the station
	 * @throws ReadWriteException :Notifies of the exception as it is which occurred in database connection.
	 </en>*/
	public String getStationName(String stno) throws ReadWriteException
	{
		if (stno == null || stno.equals(""))
		{
			return "";
		}

		ToolStationSearchKey  skey  = new ToolStationSearchKey() ;
		ToolStationHandler    shdle = new ToolStationHandler(wConn) ;
		skey.setNumber(stno);
		
		Station[] st = (Station[])shdle.find(skey);

		if ( st != null && st.length > 0 )
		{
			return st[0].getName() ;
		}

		ToolAisleSearchKey  akey  = new ToolAisleSearchKey() ;
		ToolAisleHandler    ahdle = new ToolAisleHandler(wConn) ;
		akey.setNumber(stno);
		
		if ( ahdle.count(akey) > 0 )
		{
			return "RM" ;
		}

		return "";
	}
	
	//#CM46748
	/**<en>
	 * Return the name of the model code  by using the model code as a search key.
	 * @param  type :model code
	 * @return :name of the model code
	 </en>*/
	public String getMachineTypeName(int type)
	{
		String typename = "";
		
		switch(type)
		{
			case 11:
				return "RM";
			case 15:
				return "MSS";
			case 21:
				return "CO";
			case 31:
				return "MV";
			case 54:
				return "STVS";
			case 55:
				return "STVL";
			default:
				return "";
		}
	}
	//#CM46749
	/**<en>
	 * Return the zone name by using ZoneId as a search key.
	 * Return the string of 0 byte if the zone name is undefined.
	 * 
	 * @param  zoneid :zone ID
	 * @return  :zone name
	 </en>*/
	public String getHardZoneName(int zoneid ) throws ReadWriteException
	{
		ToolHardZoneHandler   hzh = new ToolHardZoneHandler(wConn) ;
		ToolHardZoneSearchKey hzk = new ToolHardZoneSearchKey() ;

		hzk.setHardZoneID(zoneid) ;
		HardZone[] hardzone = (HardZone[])hzh.find(hzk) ;
		if ( hardzone.length != 0 )
		{
			return hardzone[0].getName() ;
		}
		return "" ;
	}

	//#CM46750
	/**<en>
	 * Search Shelf and deternines whether/not the data exists.
	 * @param  whstno :type of storage
	 * @param  bank   :bank
	 * @param  bay    :bay
	 * @param  level  :level
	 * @return  :return true if the data exists.
	 * @throws ReadWriteException :Notifies of the exception as it is which occurred 
	 * in database connection.
	 </en>*/
	public boolean isExistShelf(int warehousenumber, int bank, int bay, int level) throws ReadWriteException
	{
		try
		{
			ToolShelfSearchKey  skey  = new ToolShelfSearchKey() ;
			ToolShelfHandler    shdle = new ToolShelfHandler(wConn) ;
			
			String whstno = getWarehouseStationNumber(warehousenumber);
			//#CM46751
			//<en> Set the warehouse station no.</en>
			skey.setWarehouseStationNumber(whstno) ;
			//#CM46752
			//<en> Set bank.</en>
			skey.setBank(bank) ;
			//#CM46753
			//<en>Set bay.</en>
			skey.setBay(bay) ;     
			//#CM46754
			//<en> Set level.</en>
			skey.setLevel(level) ;
			//#CM46755
			//<en> This method is executed in order to avoid the </en>
			//<en> generation of unnecessary items.</en>
			shdle.setisScreen() ; 

			if ( shdle.count(skey) > 0 )
			{
				return true ;
			}
			return false;
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
	}	

	//#CM46756
	/**<en>
	 * Check for unacceptable characters and symbols.
	 * They are used in workshop name.
	 * @param  param :target parameter of check.
	 * @return :true if unacceptable characters and symbols were used.
	 </en>*/
	public boolean isUndefinedChar(String param)
	{
		//#CM46757
		//<en>Check the unacceptable characters and symbols.</en>
		return (DisplayText.isPatternMatching(param));
	}
	
	//#CM46758
	/**<en>
	 * Check the unacceptable characters and symbols in list box.
	 * @param  param :target parameter of check.
	 * @return :true if unacceptable characters and symbols were used.
	 </en>*/
	public boolean isUndefinedCharForListBox(String param)
	{
		String ptnchar = CommonParam.getParam("PATTERNMATCHING_CHAR") ;
		String ngchar = CommonParam.getParam("NG_PARAMETER_TEXT") ;
		//#CM46759
		//<en> Detect the PATTERNMATCHING_CHAR characters.</en>
		int num = ngchar.indexOf(ptnchar) ;
		if (num > -1)
		{
			//#CM46760
			//<en> Defines that any characters that PATTERNMATCHING_CHAR characters </en>
			//<en> were ommitted shuold be unacceptable.</en>
			ngchar = ngchar.substring(0, num) + ngchar.substring(num + 1) ;
		}
		//#CM46761
		//<en>Check for unacceptable characters and symbols.</en>
		return (DisplayText.isPatternMatching(param, ngchar));
	}
	//#CM46762
	// Package methods -----------------------------------------------

	//#CM46763
	// Protected methods ---------------------------------------------

	//#CM46764
	// Private methods -----------------------------------------------

}
//#CM46765
//end of class

