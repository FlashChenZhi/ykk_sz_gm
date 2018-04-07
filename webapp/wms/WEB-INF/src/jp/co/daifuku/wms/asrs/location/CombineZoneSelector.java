// $Id: CombineZoneSelector.java,v 1.2 2006/10/26 08:40:27 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42235
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM42236
/**<en>
 * This class is used for zone control.
 * Zone is a unit of devided area used for warehouse control according to the fixed rules.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:40:27 $
 * @author  $Author: suresh $
 </en>*/
public class CombineZoneSelector implements ZoneSelector
{
	//#CM42237
	// Class fields --------------------------------------------------

	//#CM42238
	// Class variables -----------------------------------------------
	//#CM42239
	/**<en>
	 * Connection with database
	 </en>*/
	Connection wConn ;
	
	//#CM42240
	// Class method --------------------------------------------------
	//#CM42241
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:40:27 $") ;
	}

	//#CM42242
	// Constructors --------------------------------------------------
	//#CM42243
	/**<en>
	 * Generates the instance according to the parameter of Connection instance to connect with database.
	 * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
	 * @param conn  :<code>Connection</code> to connect with database
	 </en>*/
	public CombineZoneSelector(Connection conn)
	{
		wConn = conn ;
	}

	//#CM42244
	// Public methods ------------------------------------------------
	//#CM42245
	/**<en>
	 * Searches the instance of <code>Zone</code>.
	 * The search will be done by the article name master table in the stock table presented by pallet table
	 * which is provided by paramter. 
	 * In this method, it is presumpted that all Items on the Palette are subject to the same classification.
	 * Therefore, if there are any Item requiring different classificaitons, it may return zones unfitting to some 
	 * Items.
	 * It retrieves soft zone information from classificaiton data, and retrieves hard zone data from the load height 
	 * of <code>Palette</code>.
	 * THen it returns <code>Zone</code>, the addition of retrieved soft zone data and hard zone data.<br>
	 * It is required that ZONE table and HARDZONE table must be predefined with IDs eveb if the soft zone operation or
	 * hard zone operation will not be performed.
	 * @param plt :<code>Palette</code> instance which includes the Pallet information subject to empty location search
	 * @param wh  :warehous information which contains teh objective zone of search.
	 * @return    :<code>Zone</code> object array, created according to the paremeter
	 * @throws ReadWriteException :Notifies if it failed the data aceess.
	 * @throws InvalidDefineException :Notifies if there is no such zone data exists.
	 </en>*/
	public Zone[] select(Palette plt, WareHouse wh) throws ReadWriteException, InvalidDefineException
	{
		//#CM42246
		// Generate the zone instance.
		Zone[] zone = new Zone[1];
		zone[0] = makeZone(wh.getStationNumber());

		//#CM42247
		//<en> Retrieves zone array</en>
		return getSearchZones(zone, plt.getHeight(), wh.getStationNumber());
	}

	//#CM42248
	/**<en>
	 * Searches <code>Zone</code> instance (to be used in close operation schedule).
	 * Retrieves hard zone ID from provided load height.
	 * Returns <code>Zone</code>, attachment of soft zone data and hard zone data. <br>
	 * IT is necessary to predefine the ZONE table and HARDZONE table with IDs, even if the softzone operation or 
	 * the hard zone operation are not carried out.
	 * @param classid :classificaiton ID(unused at moment)
	 * @param height  :load height
	 * @param wh   :warehouse information which contains the zone to search.
	 * @return :<code>Zone</code> object array created according to the parameter.
	 * @throws ReadWriteException :Notifies if data access failed.
	 * @throws InvalidDefineException :Notifies if no such zone data is found.
	 </en>*/
	public Zone[] selectCloseZone(int classid, int height, WareHouse wh) throws ReadWriteException, InvalidDefineException
	{
		//#CM42249
		// Generate the zone instance. 
		Zone[] zone = new Zone[1];
		zone[0] = makeZone(wh.getStationNumber());

		//#CM42250
		//<en> Retrieves zone array</en>
		return getSearchZones(zone, height, wh.getStationNumber());
	}

	//#CM42251
	/**<en>
	 * Search the instance:<code>Zone</code> for location search use.
	 * Soft zone and hard zone are connected according to soft zone, load size and warehouse station.
	 * In case the hard zone definition that corresponds to the specified load size is not found,
	 * unless the size is set with valid value (Height=0),
	 * error will not occur but hard zone is searched regarding soft zone ID = hard zone ID.
	 * In this case, the priority with zone is disregarded.
	 * This process is useful when using the zone that is acquired from item master as a hard zone.
	 * Even if the soft zone/hard zone operation are not used,
	 * IDs must be defined in advance in ZONE table/HARDZONE table.
	 * @param zone    :zone instance of the soft zone
	 * @param height  :load size
	 * @param wh      :warehouse data that include the target zone of search
	 * @return :arrya of <code>Zone</code> object that is created according to parameter(hard zone and soft zone)
	 * @throws ReadWriteException :Notify if access to data failed.
	 * @throws InvalidDefineException :Notify if corresponding zone data cannot be found.
	 </en>*/
	public Zone[] getSearchZones(Zone[] zone, int height, String whstno) throws ReadWriteException, InvalidDefineException
	{
		//#CM42252
		//<en> Get the hard zone based on the warehouse and load size.</en>
		HardZoneHandler hhandl = new HardZoneHandler(wConn);
		HardZoneSearchKey hkey = new HardZoneSearchKey();
		hkey.setWHStationNumber(whstno);
		hkey.setHeight(height);
		HardZone[] hardzone = (HardZone[])hhandl.find(hkey);
		Zone[] arrayzone = null;

		if (hardzone.length > 0)
		{
			//#CM42253
			//<en> Get priority item(location search order). Height in hard zone will be unique.</en>
			String pri   = hardzone[0].getPriority();
			//#CM42254
			//Add it only if own ID is not included in Priority.
			if (pri.indexOf(Integer.toString(hardzone[0].getHardZoneID())) == -1)
			{
				pri = Integer.toString(hardzone[0].getHardZoneID()) + pri;
			}
			Vector hvec = new Vector();

			//#CM42255
			//<en>Initialize the hardZoneSearchKey</en>
			hkey = new HardZoneSearchKey();

			//#CM42256
			//<en> Set the warehous data then preserve all hard zone data.</en>
			hkey.setWHStationNumber(whstno);
			HardZone[] tmpzone = (HardZone[])hhandl.find(hkey);

			for (int i = 0 ; i < pri.length() ; i++)
			{
				String tmpzn = pri.substring(i,i + 1);

				for (int l = 0 ; l < tmpzone.length ; l++)
				{
					if(tmpzone[l].getHardZoneID() == Integer.parseInt(tmpzn))
					{
						hvec.addElement(tmpzone[l]);
					}
				}
			}

			HardZone[] arrayhzone = new HardZone[hvec.size()];
			hvec.copyInto(arrayhzone);

			//#CM42257
			//<en> Add the hard zone data to the soft zone data.</en>
			Vector vec = new Vector();
			for(int i = 0; i < zone.length; i++)
			{
				for(int cnt = 0; cnt < arrayhzone.length; cnt++)
				{
					Zone addZone= new Zone();
					addZone.setDirection(zone[i].getDirection());
					addZone.setHardZone(arrayhzone[cnt]);
					addZone.setName(zone[i].getName());
					addZone.setSoftZoneID(zone[i].getSoftZoneID());
					addZone.setWHStationNumber(zone[i].getWHStationNumber());
					vec.addElement(addZone);
				}
			}
			arrayzone = new Zone[vec.size()];
			vec.copyInto(arrayzone);
			return arrayzone;
		}
		else
		{
			//#CM42258
			//<en> Even if the zone definition cannot be found,</en>
			//<en> unless valid value is not set for load size of pallets,</en>
			//<en> error will not occur but hard zone data will be searched regarding</en>
			//<en> the soft zone ID=hard zone ID. In this case, the priority will be disregarded.</en>
			//<en> This process is used when applying the zone acquired from item master as hard zone.</en>
			//#CM42259
			//<en>When an effective value to load high information on the CMENJP3755$CM palette is not set</en>
			//#CM42260
			//<en>Do not do to make an error, and as soft zone ID = hard zone ID</en>
			//#CM42261
			//<en>Retrieve a hard zone. At this time, disregard the priority level. </en>
			//#CM42262
			//<en> This processing : the zone acquired from the commodity master as a hard zone. </en>
			//#CM42263
			//<en> It is used when applying. </en>
		if (height == 0)
			{
				hkey = new HardZoneSearchKey();
				hkey.setWHStationNumber(whstno);
				hardzone = (HardZone[])hhandl.find(hkey); 
				if (hardzone.length == 0)
				{
					//#CM42264
					//<en> 6026060=There is no zone definition for the specified warehouse. Warehouse={0}</en>
					RmiMsgLogClient.write("6026060" + wDelim + whstno, this.getClass().getName());
					throw (new InvalidDefineException("6026060" + wDelim + whstno)) ;
				}
				
				Vector vec = new Vector();
				for(int i = 0; i < zone.length; i++)
				{
					for(int cnt = 0; cnt < hardzone.length; cnt++)
					{
						Zone addZone= new Zone();
						addZone.setDirection(zone[i].getDirection());
						addZone.setHardZone(hardzone[cnt]);
						addZone.setName(zone[i].getName());
						addZone.setSoftZoneID(zone[i].getSoftZoneID());
						addZone.setWHStationNumber(zone[i].getWHStationNumber());
						vec.addElement(addZone);
					}
				}
				arrayzone = new Zone[vec.size()];
				vec.copyInto(arrayzone);
				return arrayzone;

			}
			//#CM42265
			//<en> 6026060=There is no zone definition for the specified warehouse. Warehouse={0}</en>
			RmiMsgLogClient.write("6026060" + wDelim + whstno, this.getClass().getName());
			throw (new InvalidDefineException("6026060" + wDelim + whstno)) ;
		}
	}

	//#CM42266
	/**<en>
	 * Searches the instance of <code>Zone</code>.
	 * Retrieves hard zone ID from provided load height.
	 * Returns <code>Zone</code>, the addiion of soft zone data retrieved and the hard zone data.<br>
	 * If hardzone is not defined for specified load height, search by 'all hard zone'
	 * This method is used when counting the number of empty location.
	 * @param itemkey :temKey subject to empty location search(unused at moment)
	 * @param height  :load height
	 * @param wh      :warehouse information which containes the objective zone to search.
	 * @return :<code>Zone</code> object array created according to the parameter 
	 * @throws ReadWriteException :Notifies if access with data failed.
	 * @throws InvalidDefineException :Notifiese if such zone data cannot be found.
	 </en>*/
	public Zone[] selectcount(String itemkey, int height, WareHouse wh) throws ReadWriteException, InvalidDefineException
	{
		String whstno = wh.getStationNumber();

		//#CM42267
		// Generate the zone instance.
		Zone[] zone = new Zone[1];
		zone[0] = makeZone(wh.getStationNumber());

		//#CM42268
		//<en> Retrieves hard zone based on the warehouse and load height</en>
		HardZoneHandler hhandl = new HardZoneHandler(wConn);
		HardZoneSearchKey hkey = new HardZoneSearchKey();
		hkey.setWHStationNumber(whstno);
		hkey.setHeight(height);
		HardZone[] hardzone = (HardZone[])hhandl.find(hkey);

		//#CM42269
		//<en> If there is no such data. search further by all hard zone.</en>
		if (hardzone.length == 0)
		{
			//#CM42270
			//<en>Initializing hardZoneSearchKey</en>
			hkey = new HardZoneSearchKey();
			hkey.setWHStationNumber(whstno);
			hardzone = (HardZone[])hhandl.find(hkey); 
			if (hardzone.length == 0)
			{
				//#CM42271
				//<en> If there is no definition of zone, throw exception:</en>
				//#CM42272
				//<en> 6026060=There is no zone definition for the specified warehouse. Warehouse={0}</en>
				RmiMsgLogClient.write("6026060" + wDelim + whstno, this.getClass().getName());
				throw (new InvalidDefineException("6026060" + wDelim + whstno)) ;
			}

			Vector vec = new Vector();
			for(int i = 0; i < zone.length; i++)
			{
				for(int cnt = 0; cnt < hardzone.length; cnt++)
				{
					Zone addZone= new Zone();
					addZone.setDirection(zone[i].getDirection());
					addZone.setHardZone(hardzone[cnt]);
					addZone.setName(zone[i].getName());
					addZone.setSoftZoneID(zone[i].getSoftZoneID());
					addZone.setWHStationNumber(zone[i].getWHStationNumber());
					vec.addElement(addZone);
				}
			}
			Zone[] arrayzone = new Zone[vec.size()];
			vec.copyInto(arrayzone);
			return arrayzone;
		}
		
		//#CM42273
		//<en> Retrieves the priority items (height of hard zone should be unique)</en>
		String pri   = hardzone[0].getPriority();
		//#CM42274
		//Add it only if own ID is not included in Priority.
		if (pri.indexOf(Integer.toString(hardzone[0].getHardZoneID())) == -1)
		{
			pri = Integer.toString(hardzone[0].getHardZoneID()) + pri;
		}
		
		Vector hvec = new Vector();

		//#CM42275
		//<en>Initializing hardZoneSearchKey</en>
		hkey = new HardZoneSearchKey();

		//#CM42276
		//<en> Sets the warehouse information adn the type(hard), and preserves all data of hard zone.</en>
		hkey.setWHStationNumber(whstno);
		HardZone[] tmpzone = (HardZone[])hhandl.find(hkey);

		for (int i = 0 ; i < pri.length() ; i++)
		{
			String tmpzn = pri.substring(i,i + 1);

			for (int l = 0 ; l < tmpzone.length ; l++)
			{
				if(tmpzone[l].getHardZoneID() == Integer.parseInt(tmpzn))
				{
					hvec.addElement(tmpzone[l]);
				}
			}
		}

		HardZone[] arrayhzone = new HardZone[hvec.size()];
		hvec.copyInto(arrayhzone);

		//#CM42277
		//<en> Put the soft zone data and hard zone data together.</en>
		Vector vec = new Vector();
		for(int i = 0; i < zone.length; i++)
		{
			for(int cnt = 0; cnt < arrayhzone.length; cnt++)
			{
				Zone addZone= new Zone();
				addZone.setDirection(zone[i].getDirection());
				addZone.setHardZone(arrayhzone[cnt]);
				addZone.setName(zone[i].getName());
				addZone.setSoftZoneID(zone[i].getSoftZoneID());
				addZone.setWHStationNumber(zone[i].getWHStationNumber());
				vec.addElement(addZone);
			}
		}

		Zone[] arrayzone = new Zone[vec.size()];
		vec.copyInto(arrayzone);

		return arrayzone;
	}

	//#CM42278
	// Package methods -----------------------------------------------

	//#CM42279
	// Protected methods ---------------------------------------------

	//#CM42280
	// Private methods -----------------------------------------------
	//#CM42281
	/**<en>
	 * Make empty soft zone information.
	 * Acquire from WmsParam and set 0 and the direction of the empty shelf retrieval in a soft zone.
	 * @param wh Warehouse information where retrieved object zone is included
	 * @return <code>Zone</code>Instance
	 * @throws InvalidDefineException Notify when the direction of the empty shelf retrieval to a pertinent warehouse is not decided in WmsParam.properties.
	 </en>*/
	protected Zone makeZone(String wh) throws InvalidDefineException
	{
		//#CM42282
		// Check whether the warehouse is defined.
		if (!WmsParam.DIRECTION.containsKey(wh))
		{
			//#CM42283
			// 6026080=Definition error. Warehouse where direction where specified warehouse is retrieved empty shelf is not defined ={0}
			RmiMsgLogClient.write("6026088" + wDelim + wh, this.getClass().getName());
			throw new InvalidDefineException("6026088" + wDelim + wh);
		}
		
		//#CM42284
		// Check whether the definition of the direction of the empty shelf retrieval is correct.
		//#CM42285
		// A numeric item, and the range decided by the zone definition.
		boolean correctDirection = true;
		int direction = -1;
		String strDirection = null;
		try
		{
			strDirection = WmsParam.DIRECTION.get(wh).toString();
			direction = Integer.parseInt(strDirection);
			if (direction != Zone.HP_FRONT
			  && direction != Zone.HP_LOWER
			  && direction != Zone.OP_FRONT
			  && direction != Zone.OP_LOWER)
			{
				correctDirection = false;
			}
		}
		catch (NumberFormatException e)
		{
			correctDirection = false;
		}
		finally
		{
			if (!correctDirection)
			{
				//#CM42286
				// 6026089=Definition error. Injustice warehouse = {0} empty shelf retrieval direction ={1}. The direction where the specified warehouse is retrieved an empty shelf
				RmiMsgLogClient.write("6026089" + wDelim + wh + wDelim + strDirection, this.getClass().getName());
				throw new InvalidDefineException("6026089" + wDelim + wh + wDelim + strDirection);
			}
		}
		
		//#CM42287
		// Generate an empty soft zone.
		Zone zn = new Zone();

		//#CM42288
		// Set 0 to a soft zone.
		zn.setSoftZoneID(0);
		//#CM42289
		// Acquire the direction of the empty shelf retrieval from the content set to WMSParam in each warehouse.
		zn.setDirection(direction);

		zn.setWHStationNumber(wh);

		return zn;
	}

}
//#CM42290
//end of class

