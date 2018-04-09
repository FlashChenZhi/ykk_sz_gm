// $Id: AsUnavailableLocationSCH.java,v 1.2 2006/10/30 00:46:58 suresh Exp $
package jp.co.daifuku.wms.asrs.schedule ;

//#CM45783
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;

//#CM45784
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda  <BR>
 * <BR>
 * Class to set the WEB restricted location.  <BR>
 * Receive the content input from the screen as parameter, and do the restricted location setting processing.  <BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must use the Connection object and<BR>
 * do the update processing of the receipt data base.   <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Submit button is pressed.(<CODE>startSCH()</CODE>Method)<BR><BR><DIR>
 *   Specify if it make to the [Restricted location] for specified Shelf No or [Useable location].
 *   Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 * <BR>
 *   <Search condition> <BR><DIR>
 *    Corresponding shelf information (<CODE>shelf</CODE>) data to Shelf  No. of parameter <BR></DIR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Shelf No.* <BR>
 *     Status * <BR></DIR>
 * <BR>
 *   <Restricted Location setting processing> <BR>
 * <DIR>
 *   <Processing condition check> <BR>
 *     1.Do the existence check of Shelf  No. input from the screen.  <BR>
 *     2.The shelf of an abnormal shelf and Working is not good at thing to do the change setting.  <BR>
 * <BR>
 *   <Restricted Location setting> <BR>
 *       1.The shelf which cannot access correspondence Shelf No is renewed to Status  of parameter with Shelf No  of parameter.  <BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/01</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:46:58 $
 * @author  $Author: suresh $
 */

public class AsUnavailableLocationSCH extends AbstractAsrsControlSCH
{
	//#CM45785
	// Class fields --------------------------------------------------

	//#CM45786
	// Class variables -----------------------------------------------

	//#CM45787
	// Class method --------------------------------------------------

	//#CM45788
	/**
	 * Class Name(Restricted Location setting)
	 */
	public static String PROCESSNAME = "AsUnavailableLocationSCH";

	//#CM45789
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:46:58 $");
	}

	//#CM45790
	// Constructors --------------------------------------------------
	//#CM45791
	/**
	 * Initialize this Class. 
	 * @param conn Connection object with database
	 * @param kind Process Flag
	 */
	//#CM45792
	/**
	 * Initialize this Class. 
	 */
	public AsUnavailableLocationSCH()
	{
		wMessage = null;
	}

	//#CM45793
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

	//#CM45794
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
		return null;
	}

	//#CM45795
	/**
	 * The content input from the screen is received as parameter, and Starts the Restricted Location setting schedule. <BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation. <BR>
	 * Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
	 * Return null when the schedule does not End because of the condition error etc.
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Set the content <CODE>AsSystemScheduleParameter</CODE>Class  Array of Instances. <BR>
	 *         AsSystemScheduleParameter <CODE>ScheduleException</CODE> when things except the instance are specified is slow.<BR>
	 *         It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		//#CM45796
		// Check on Worker code and Password
		if (!checkWorker(conn, (AsScheduleParameter) startParams[0]))
		{
			 return false;
		}
		
		ShelfHandler wShHandler = new ShelfHandler(conn);
		ShelfAlterKey wShAlterKey = new ShelfAlterKey();
		try
		{
			//#CM45797
			// Next day update Processing check
			if (isDailyUpdate(conn))
			{
				return false;
			}

			AsScheduleParameter param = (AsScheduleParameter) startParams[0];
System.out.println("SCH-LOC[" + param.getLocationNo() + ":" + param.getSelectLocationStatus());

			try
			{
				//#CM45798
				// Check Location Status. 
				if (!checkLocation(conn, param.getSelectLocationStatus(), param.getLocationNo()))
				{
					return false;
				}
			}
			catch (NoPrimaryException e)
			{
				throw new ScheduleException(e.getMessage());
			}
				
			wShAlterKey.KeyClear();
			//#CM45799
			// WarehouseFlag + Input Shelf No.
			wShAlterKey.setStationNumber(param.getLocationNo());
			//#CM45800
			// Set the update value of Location Status. 
			if (param.getSelectLocationStatus().equals(AsScheduleParameter.ASRS_LOCATIONSTATUS_POSSIBLE))
			{
				wShAlterKey.updateStatus(Shelf.STATUS_OK);
			}
			else if (param.getSelectLocationStatus().equals(AsScheduleParameter.ASRS_LOCATIONSTATUS_PROHIBITION))
			{
				wShAlterKey.updateStatus(Shelf.STATUS_NG);
			}
			
			wShHandler.modify(wShAlterKey);

			//#CM45801
			//<en> 6001006 = It has been set.</en>
			setMessage("6001006");
				
			return true;

		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM45802
	/**
	 * Whether Status of the shelf be able to be checked and maintain it are decided. 
	 * @param conn Connection
	 * @param p_StatusFlag Change Location Status  
	 * @param p_location Shelf No. 
	 * @return true: Maintenance OK
	 * @throws Exception 
	 */
	private boolean checkLocation(Connection conn, String p_StatusFlag, String p_location) throws ReadWriteException, NoPrimaryException
	{
		ShelfHandler wShHandler = new ShelfHandler(conn);
		ShelfSearchKey wShSearchKey = new ShelfSearchKey();
				
		wShSearchKey.KeyClear();
		//#CM45803
		// WarehouseFlag + Input Shelf No.
		wShSearchKey.setStationNumber(p_location);
		
		Shelf wShelf = (Shelf)wShHandler.findPrimaryForUpdate(wShSearchKey);

		if (wShelf == null)
		{
			//#CM45804
			//<en> Please enter the existing location no.</en>
			wMessage = "6013090" ;
			return false;
		}

		//#CM45805
		// Check the condition to the restricted location by Change and Location Status. 
		if (p_StatusFlag.equals(AsScheduleParameter.ASRS_LOCATIONSTATUS_PROHIBITION))
		{
			//#CM45806
			//<en> Status of the location is checked. </en>
			if (wShelf.getPresence() == (Shelf.PRESENCE_RESERVATION))
			{
				//#CM45807
				//<en> The location is reserved. Unable to make corrections.</en>
				wMessage = "6013158" ;	
				return false;
			}
		
			//#CM45808
			//<en> The following checks are unnecessary for an empty shelf. </en>
			if (wShelf.getPresence() == (Shelf.PRESENCE_EMPTY))	return true;

			Palette[] wPalette = null;
			//#CM45809
			//<en> If the location is occupied, check the allcation status and erroe location.</en>
			PaletteHandler wPlHandler = new PaletteHandler(conn);
			PaletteSearchKey wPlSearchKey = new PaletteSearchKey();
			
			//#CM45810
			// Set the Search condition.
			wPlSearchKey.KeyClear();
			wPlSearchKey.setCurrentStationNumber(p_location);
			
			wPalette = (Palette[])wPlHandler.find(wPlSearchKey);
			
			int pltstatus = wPalette[0].getStatus();
			switch (pltstatus)
			{
				//#CM45811
				//<en> occupied location</en>
				case Palette.REGULAR:
					break;
				//#CM45812
				//<en> reserved for storage</en>
				case Palette.STORAGE_PLAN:
				//#CM45813
				//<en> reserved for retrieval</en>
				case Palette.RETRIEVAL_PLAN:
				//#CM45814
				//<en> being retrieved</en>
				case Palette.RETRIEVAL:
					//#CM45815
					//<en> Specified location is allocated at moment.</en>
					wMessage = "6013135" ;	
					return false;
				//#CM45816
				//<en> error</en>
				case Palette.IRREGULAR:
					//#CM45817
					//<en> 6013199 = The specified shelf is not good at Restricted Location setting due to abnormality. </en>
					setMessage("6013199");
					return false;
			}
		}
		
		return true;
	}
}
