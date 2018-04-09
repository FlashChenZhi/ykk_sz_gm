// $Id: AsStoringStatusSCH.java,v 1.2 2006/10/30 00:47:26 suresh Exp $
package jp.co.daifuku.wms.asrs.schedule ;

//#CM45717
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.dbhandler.ASShelfHandler;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.BankSelectHandler;
import jp.co.daifuku.wms.base.dbhandler.BankSelectSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.BankSelect;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM45718
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda  <BR>
 * <BR>
 * Class to do WEB ASRS storage situation inquiry. <BR>
 * Receive the content input from the screen as parameter, and do ASRS storage situation inquiry. <BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must use the Connection object and<BR>
 * do the update processing of the receipt data base.   <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Inquiry button is pressed(<CODE>query()</CODE>Method)<BR><BR><DIR>
 *   Retrieve Mode of packing, a real shelf, an empty shelf, an empty Palette shelf, an abnormal shelf, the restricted shelf, <BR>
 *   Total Location Qty, and Storage rate of Warehouse Flag and each title machine and retrieve shelf and Inventory information <BR>
 *   view (<CODE>SHELFTOTALVIEW</CODE>).   Display the storage situation.  <BR>
 *   The displayed every particular item makes to Method respectively, acquire the value calculated in Method, and display.  <BR>
 *   Title machine No and the Mode of packing data are calculated, and loop, and calculate qty of each.  <BR>
 *   Return data returns the Array data for the title machine and Mode of packing.  <BR>
 *   Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 * <BR>
 *   <Search condition> <BR><DIR>
 *     Corresponding data to WarehouseFlag of parameter and Title machine No <BR></DIR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     WarehouseFlag* <BR>
 *     Title machine No* <BR></DIR>
 * <BR>
 *   <Return data(Array)><BR><DIR>
 *     Total of Actual Location Qty <BR>
 *     Total of Empty Location Qty <BR>
 *     Total of number of empty Palette shelves <BR>
 *     Total of Abnormal Location Qty <BR>
 *     Total of Restricted Location Qty <BR>
 *     Total of Total Location Qty <BR>
 *     Storage rate <BR></DIR>
 * <BR>
 * <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/01</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:47:26 $
 * @author  $Author: suresh $
 */

public class AsStoringStatusSCH extends AbstractAsrsControlSCH
{
	//#CM45719
	// Class fields --------------------------------------------------

	//#CM45720
	// Class variables -----------------------------------------------

	//#CM45721
	// Class method --------------------------------------------------
	//#CM45722
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:47:26 $");
	}

	//#CM45723
	// Constructors --------------------------------------------------
	//#CM45724
	/**
	 * Initialize this Class. 
	 * @param conn Connection object with database
	 * @param kind Process Flag
	 */
	//#CM45725
	/**
	 * Initialize this Class. 
	 */
	public AsStoringStatusSCH()
	{
		wMessage = null;
	}

	//#CM45726
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen. <BR>
	 * Return null when pertinent data does not exist or it exists by two or more.  <BR>
	 * Set null in < CODE>searchParam</CODE > because it does not need the search condition. 
	 * @param conn Connection object with database
	 * @param searchParam Class which succeeds to < CODE>Parameter</CODE> Class with Search condition
	 * @return Class which mounts < CODE>Parameter</CODE > interface where retrieval result is included
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		return null;
	}

	//#CM45727
	/**
	 * The content input from the screen is received as parameter, and filtered, and acquire from the database and return data for the area output.  <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation.  <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param searchParam Instance of < CODE>AsSystemScheduleParameter</CODE>Class with display data acquisition condition.  <BR>
	 *         <CODE>AsSystemScheduleParameter</CODE> ScheduleException when specified excluding the instance is slow. <BR>
	 * @return Have the retrieval result <CODE>AsSystemScheduleParameter</CODE> Array of Instances. <BR>
	 *          Return the empty array when not even one pertinent record is found.  <BR>
	 *          Return null when the error occurs in the input condition.  <BR>
	 *          When null is returned, the content of the error can be acquired as a message in <CODE>getMessage()</CODE>Method. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		//#CM45728
		// Open or Close
		boolean isCloseSystem = false;
		
		//#CM45729
		// For WareHouse table retrieval
		WareHouseSearchKey wareHouseKey  = new WareHouseSearchKey();
		WareHouseHandler   wareHouseHandle  = new WareHouseHandler(conn);
		
		//#CM45730
		// General-purpose retrieval
		ASFindUtil util = new ASFindUtil(conn);
		
		//#CM45731
		// Aisle list which exists in specified Warehouse
		String[] aisleSTArray = null;
		//#CM45732
		// Bank list which belongs to specified Aisle
		int[] bankArray = null;
		//#CM45733
		// Hard ZoneID list which belongs to specified Bank
		int[] zoneArray = null;
		//#CM45734
		// Parameter for screen display
		AsScheduleParameter dispData = null;
		//#CM45735
		// Vector for temporary data storage
		Vector vec = new Vector();
		
		//#CM45736
		// Input value
		AsScheduleParameter param = (AsScheduleParameter) searchParam;
		String wareHouseNo = param.getWareHouseNo();
		String aislestno = param.getStationNo();

		ShelfSearchKey shelfKey = new ShelfSearchKey();

		//#CM45737
		// Sorts in ascending order of Warehouse Station No. 
		shelfKey.setWHStationNumberOrder(1,true);
		wareHouseKey.setStationNumber(wareHouseNo);
		WareHouse[] wh = (WareHouse[])wareHouseHandle.find(wareHouseKey);
		
		//#CM45738
		// When automatic Warehouse operation Type is close operation
		if (wh[0].getEmploymentType() == WareHouse.EMPLOYMENT_CLOSE) 
		{
			isCloseSystem = true;
		}
		
		//#CM45739
		// Total(initial value)
		int totalStoragedShelf = 0;
		int totalEmptyShelf = 0;
		int totalEmptyPlt = 0;
		int totalIrregularShelf = 0;
		int totalErrorShelf = 0;
		int totalNotAccess = 0;
		int totalShelf = 0;
		double totalRate = 0;
		
		//#CM45740
		// Acquire the Aisles list when all Aisle are selected in PullDown. 
		if(aislestno.equals(AsrsParam.ALL_AISLE_NO))
		{
			AisleSearchKey wAisleKey = new AisleSearchKey();
			AisleHandler wAisleHandle = new AisleHandler(conn);
			wAisleKey.setWHStationNumber(wareHouseNo);
			wAisleKey.setStationNumberCollect();
			wAisleKey.setStationNumberGroup(1);
			wAisleKey.setStationNumberOrder(1, true);

			Aisle[] aile = (Aisle[]) wAisleHandle.find(wAisleKey);
			
			aisleSTArray = new String[aile.length];
			for (int j = 0; j < aile.length; j++)
			{
				aisleSTArray[j] = aile[j].getStationNumber();
			}
		}
		else
		{
			AisleSearchKey wAisleKey = new AisleSearchKey();
			AisleHandler wAisleHandle = new AisleHandler(conn);
			wAisleKey.setWHStationNumber(wareHouseNo);
			wAisleKey.setStationNumber(aislestno);
			wAisleKey.setStationNumberCollect();
			wAisleKey.setStationNumberGroup(1);
			wAisleKey.setStationNumberOrder(1, true);

			Aisle[] aile = (Aisle[]) wAisleHandle.find(wAisleKey);
			
			aisleSTArray = new String[aile.length];
			for (int j = 0; j < aile.length; j++)
			{
				aisleSTArray[j] = aile[j].getStationNumber();
			}
		}
		
		//#CM45741
		// To display a pertinent qty of each zone of each Aisle
		//#CM45742
		// It loops in Aisle and the zone. 
		for(int i = 0; i < aisleSTArray.length; i++)
		{
			String aisleSTNo = aisleSTArray[i];

			//#CM45743
			// Obtain BankArray which belongs to Aisle retrieved this time. 
			BankSelectSearchKey wBankKey = new BankSelectSearchKey();
			BankSelectHandler wBankHandle = new BankSelectHandler(conn);
			wBankKey.setAisleStationNumber(aisleSTNo);
			wBankKey.setNbankOrder(1, true);
			BankSelect[] bank = (BankSelect[]) wBankHandle.find(wBankKey);
			bankArray = new int[bank.length];
			for (int s = 0; s < bank.length; s++)
			{
				bankArray[s] = bank[s].getNbank();
			}
					
			//#CM45744
			// Return the ZONEID list of Hard Zone specifying Warehouse. 
			ShelfSearchKey wShelfKey = new ShelfSearchKey();
			ShelfHandler wShelfHandle = new ShelfHandler(conn);
			wShelfKey.setWHStationNumber(wareHouseNo);
			wShelfKey.setHardZoneIDCollect();
			wShelfKey.setHardZoneIDGroup(1);
			wShelfKey.setHardZoneIDOrder(1, true);
			Shelf[] shelf = (Shelf[]) wShelfHandle.find(wShelfKey);
			zoneArray = new int[shelf.length];
			for (int j = 0; j < shelf.length; j++)
			{
				zoneArray[j] = shelf[j].getHardZoneID();
			}
			
			for(int k = 0; k < zoneArray.length; k++ )
			{
		
				//#CM45745
				// Actual Location Qty
				int storagedShelfQty = getStoragedShelfQty(conn, wareHouseNo, bankArray, zoneArray[k]);
				//#CM45746
				// Empty Location Qty
				int emptyShelfQty = getEmptyShelfQty(conn, wareHouseNo, bankArray, zoneArray[k]);
				//#CM45747
				// Empty PB Qty
				int emptyPaletteQty = getEmptyPaletteQty(conn, wareHouseNo, bankArray, zoneArray[k]);
				//#CM45748
				// Abnormal Location Qty
				int irregularShelfQty = getIrregularShelfQty(conn, wareHouseNo, bankArray, zoneArray[k]);
				//#CM45749
				// Restricted Location Qty
				int errorShelfQty =getErrorShelfQty(conn, wareHouseNo, aisleSTNo, zoneArray[k]);
				
				//#CM45750
				// Access Restricted Location Qty
				int notaccessShelfQty =getNotAccessShelfQty(conn, wareHouseNo, aisleSTNo, zoneArray[k]);
				
				//#CM45751
				// Total Location Qty
				int allShelfQty = getAllShelfQty(conn, wareHouseNo, aisleSTNo, zoneArray[k]);
				//#CM45752
				// Storage rate
				double storagedrate = 0;
				
				if(allShelfQty>0) 
				{
					if( isCloseSystem ) 
					{
						//#CM45753
						// Calculation by close
						storagedrate = (double)((allShelfQty-emptyPaletteQty-emptyShelfQty)*100)/(double)allShelfQty;
					} 
					else 
					{
						//#CM45754
						// Calculation by open
						storagedrate = (double)((allShelfQty-emptyShelfQty)*100)/(double)allShelfQty;
					}
					storagedrate = java.lang.Math.round(storagedrate * 10.0)/10.0;
				}

				//#CM45755
				// Request the number of totals.
				totalStoragedShelf  += storagedShelfQty;
				totalEmptyShelf     += emptyShelfQty;
				totalEmptyPlt       += emptyPaletteQty;
				totalIrregularShelf += irregularShelfQty;
				totalErrorShelf     += errorShelfQty;
				totalNotAccess     += notaccessShelfQty;
				totalShelf          += allShelfQty;
				
				//#CM45756
				//Set the retrieval value in data for the display. 
				dispData = new AsScheduleParameter();
				if(k == 0)
				{
					dispData.setRackmasterNo("RM"+Integer.toString(util.getAisleNumber(aisleSTNo)));	
				}
				else
				{
					dispData.setRackmasterNo("");
				}
				dispData.setHardZone(util.getHardZoneName(zoneArray[k]));
				dispData.setRealLocationCount(storagedShelfQty);
				dispData.setVacantLocationCount(emptyShelfQty);
				dispData.setEmptyPBLocationCount(emptyPaletteQty);
				dispData.setAbnormalLocationCount(irregularShelfQty);
				dispData.setProhibitionLocationCount(errorShelfQty);
				
				dispData.setNotAccessLocationCount(notaccessShelfQty);
				
				dispData.setTotalLocationCount(allShelfQty);
				dispData.setLocationRate(Double.toString(storagedrate)+"%");
				vec.addElement(dispData);
			}
		}
			
		//#CM45757
		//Insert total at the end. 
		dispData = new AsScheduleParameter();
		dispData.setRackmasterNo("");
		dispData.setHardZone(DisplayText.getText("LBL-W0269"));
		dispData.setRealLocationCount(totalStoragedShelf);
		dispData.setVacantLocationCount(totalEmptyShelf);
		dispData.setEmptyPBLocationCount(totalEmptyPlt);
		dispData.setAbnormalLocationCount(totalIrregularShelf);
		dispData.setNotAccessLocationCount(totalNotAccess);
		dispData.setProhibitionLocationCount(totalErrorShelf);
		dispData.setTotalLocationCount(totalShelf);

		if(totalShelf > 0)
		{
			if( isCloseSystem ) 
			{
				//#CM45758
				// Calculation by close (Total Location Qty - Empty Palette Qty - Empty Location Qty)
				totalRate = (double)((totalShelf - totalEmptyPlt - totalEmptyShelf)*100)/(double)totalShelf;
			} 
			else 
			{
				//#CM45759
				// Calculation by open (Total Location Qty-Empty Location Qty)
				totalRate = (double)((totalShelf - totalEmptyShelf)*100)/(double)totalShelf;
			}
			totalRate = java.lang.Math.round(totalRate * 10.0)/10.0;
		}

		dispData.setLocationRate(Double.toString(totalRate)+"%");
		vec.addElement(dispData);
			
		AsScheduleParameter[] fmt = new AsScheduleParameter[vec.size()];
		vec.toArray(fmt);
		
		//#CM45760
		// 6001013 = Displayed.
		wMessage = "6001013";
		return fmt;
	}
	//#CM45761
	/**
	 * Return Total Location Qty. <BR>
	 * @param conn Connection object with database
	 * @param whstno Warehouse Station No.
	 * @param aisleSTNo Aisle Station No..
	 * @param zoneid Zone ID
	 * @return Return Total Location Qty. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private int getAllShelfQty(Connection conn, String whstno, String aisleSTNo, int zoneid) throws ReadWriteException
	{
		ShelfHandler wShelfHandle = new ShelfHandler(conn);
		//#CM45762
		//Total Location Qty within the range of specification
		ShelfSearchKey shelfKey = new ShelfSearchKey() ;
		shelfKey.KeyClear();
		shelfKey.setWHStationNumber(whstno);
		shelfKey.setParentStationNumber(aisleSTNo);
		shelfKey.setHardZoneID(zoneid);
		return wShelfHandle.count(shelfKey);
	}

	//#CM45763
	/**
	 * Return the number of real shelves. <BR>
	 * Real shelf can be accessed, and can be used. 
	 * String applying to it and turning over PALETTE make the one which is not empty abnormality Pare a real shelf. 
	 * @param conn Connection object with database
	 * @param whstno Warehouse Station No.
	 * @param bankArray Bank Array of Numbers.
	 * @param zoneid Zone ID
	 * @return Return the number of real shelves. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private int getStoragedShelfQty(Connection conn, String whstno, int[] bankArray, int zoneid) throws ReadWriteException
	{
		ASShelfHandler storingStatusHandle = new ASShelfHandler(conn);
		
		PaletteSearchKey paletteKey = new PaletteSearchKey();
		ShelfSearchKey shelfKey = new ShelfSearchKey();
		
		shelfKey.KeyClear();
		//#CM45764
		//Warehouse Bank Zone.
		shelfKey.setWHStationNumber(whstno);
		shelfKey.setNBank(bankArray);
		shelfKey.setHardZoneID(zoneid);
		//#CM45765
		//Real shelf
		shelfKey.setPresence(Shelf.PRESENCE_STORAGED);
		//#CM45766
		//Set Inaccessible Location Flag (ACCESS_OK)
		shelfKey.setAccessNgFlag(Shelf.ACCESS_OK);
		//#CM45767
		//Useable Location
		shelfKey.setStatus(Shelf.STATUS_OK);
		//#CM45768
		//Stock Status(Abnormality)
		paletteKey.setStatus(Palette.IRREGULAR, "!=");
		//#CM45769
		//Empty palette status(Empty Palette)
		paletteKey.setEmpty(Palette.STATUS_EMPTY, "!=");	
		
		return storingStatusHandle.count(shelfKey, paletteKey);
		
	}

	//#CM45770
	/**
	 * Return the number of empty shelves. <BR>
	 * SHELF makes an empty shelf or the Stock reservation shelf an empty shelf. 
	 * 
	 * @param conn Connection object with database
	 * @param whstno Warehouse Station No.
	 * @param bankArray Bank Array of Numbers
	 * @param zoneid Zone ID
	 * @return Return an accessible shelf and Useable Location or more with an empty shelf. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private int getEmptyShelfQty(Connection conn, String whstno, int[] bankArray, int zoneid) throws ReadWriteException
	{
		
		ASShelfHandler storingStatusHandle = new ASShelfHandler(conn);
		ShelfSearchKey shelfKey = new ShelfSearchKey();
		PaletteSearchKey paletteKey = new PaletteSearchKey();
		
		shelfKey.KeyClear();
		paletteKey.KeyClear();
		shelfKey.setWHStationNumber(whstno);
		shelfKey.setNBank(bankArray);
		shelfKey.setHardZoneID(zoneid);

		return storingStatusHandle.countEmptyShelf(shelfKey);
	}

	//#CM45771
	/**
	 * Return the number of abnormal shelves. <BR>
	 * SHELF is Real shelf, can be accessible, and be used. 
	 * The string is applied to it and Stock Status of turning over PALETTE returns the one of abnormality. 
	 * 
	 * @param conn Connection object with database
	 * @param whstno Warehouse Station No.
	 * @param bankArray Bank Array of Numbers
	 * @param zoneid Zone ID
	 * @return Return the number of abnormal shelves. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private int getIrregularShelfQty(Connection conn, String whstno, int[] bankArray, int zoneid) throws ReadWriteException
	{
		ASShelfHandler storingStatusHandle = new ASShelfHandler(conn);
		ShelfSearchKey shelfKey = new ShelfSearchKey();
		PaletteSearchKey paletteKey = new PaletteSearchKey();

		shelfKey.KeyClear();
		paletteKey.KeyClear();
		shelfKey.setWHStationNumber(whstno);
		shelfKey.setNBank(bankArray);
		shelfKey.setHardZoneID(zoneid);
		//#CM45772
		//Real shelf
		shelfKey.setPresence(Shelf.PRESENCE_STORAGED);
		//#CM45773
		//Set Inaccessible Location Flag (ACCESS_OK)
		shelfKey.setAccessNgFlag(Shelf.ACCESS_OK);
		//#CM45774
		//Useable Location
		shelfKey.setStatus(Shelf.STATUS_OK);
		//#CM45775
		//Stock Status(Abnormality)
		paletteKey.setStatus(Palette.IRREGULAR);
		
		return storingStatusHandle.count(shelfKey, paletteKey);
	}

	//#CM45776
	/**
	 * Return the qty of empty Palette. <BR>
	 * SHELF is Real shelf, can be accessible, and be used. 
	 * The string is applied to it and Stock Status of turning over PALETTE returns the one of abnormality. 
	 * @param whstno Warehouse Station No.
	 * @param bankArray Bank Array of Numbers
	 * @param zoneid Zone ID
	 * @return Return the qty of empty Palette
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private int getEmptyPaletteQty(Connection conn, String whstno, int[] bankArray, int zoneid) throws ReadWriteException
	{
		ASShelfHandler storingStatusHandle = new ASShelfHandler(conn);
		PaletteSearchKey paletteKey = new PaletteSearchKey();
		ShelfSearchKey shelfKey = new ShelfSearchKey();

		shelfKey.KeyClear();
		paletteKey.KeyClear();
		shelfKey.setWHStationNumber(whstno);
		shelfKey.setNBank(bankArray);
		shelfKey.setHardZoneID(zoneid);
		//#CM45777
		//Real shelf
		shelfKey.setPresence(Shelf.PRESENCE_STORAGED);
		//#CM45778
		//Set Inaccessible Location Flag (ACCESS_OK)
		shelfKey.setAccessNgFlag(Shelf.ACCESS_OK);
		//#CM45779
		//Useable Location
		shelfKey.setStatus(Shelf.STATUS_OK);
		//#CM45780
		//Empty palette status(Empty Palette )
		paletteKey.setEmpty(Palette.STATUS_EMPTY);
		
		return storingStatusHandle.count(shelfKey, paletteKey);
	}

	//#CM45781
	/**
	 * Return the qty of restricted Location.<BR>
	 * Make the number of cases of shelf information on being not able to use Status of SHELF a prohibition shelf. 
	 * 
	 * @param conn Connection object with database
	 * @param whstno Warehouse Station No.
	 * @param aisleSTNo Aisle Station No..
	 * @param zoneid Zone ID
	 * @return Return the qty of restricted Location. .
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private int getErrorShelfQty(Connection conn, String whstno, String aisleSTNo, int zoneid) throws ReadWriteException
	{
		ShelfHandler wShelfHandle = new ShelfHandler(conn);
		ShelfSearchKey shelfKey = new ShelfSearchKey() ;
		
		shelfKey.KeyClear();
		shelfKey.setWHStationNumber(whstno);
		shelfKey.setParentStationNumber(aisleSTNo);
		shelfKey.setHardZoneID(zoneid);
		shelfKey.setStatus(Shelf.STATUS_NG);
		return wShelfHandle.count(shelfKey);
	}
	
	//#CM45782
	/**
	 * Return the qty of access restricted Location. .<BR>
	 * Status of SHELF makes the number of cases of shelf information on the access prohibition an access prohibition shelf. 
	 * 
	 * @param conn Connection object with database
	 * @param whstno Warehouse Station No.
	 * @param aisleSTNo Aisle Station No..
	 * @param zoneid Zone ID
	 * @return Return the qty of restricted Location. .
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private int getNotAccessShelfQty(Connection conn, String whstno, String aisleSTNo, int zoneid) throws ReadWriteException
	{
		ShelfHandler wShelfHandle = new ShelfHandler(conn);
		ShelfSearchKey shelfKey = new ShelfSearchKey() ;
		
		shelfKey.KeyClear();
		shelfKey.setWHStationNumber(whstno);
		shelfKey.setParentStationNumber(aisleSTNo);
		shelfKey.setHardZoneID(zoneid);
		shelfKey.setAccessNgFlag(Shelf.ACCESS_NG);
		return wShelfHandle.count(shelfKey);
	}
}
