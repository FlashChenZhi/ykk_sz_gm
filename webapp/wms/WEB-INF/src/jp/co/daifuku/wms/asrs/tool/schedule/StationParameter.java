// $Id: StationParameter.java,v 1.2 2006/10/30 02:52:01 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;
//#CM51623
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

//#CM51624
/**<en>
 * This is an entity class which will be used in station.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:01 $
 * @author  $Author: suresh $
 </en>*/
public class StationParameter extends Parameter
{
	//#CM51625
	// Class fields --------------------------------------------------

	//#CM51626
	// Class variables -----------------------------------------------
	//#CM51627
	/**<en>
	* path of the product number folder
	</en>*/
	String wFilePath = "";
	
	//#CM51628
	/**<en> station no. </en>*/

	protected String wNumber = "" ;

	//#CM51629
	/**<en> station name </en>*/

	protected String wName = ""  ;
	
	//#CM51630
	/**<en> station type </en>*/

	protected int wType = 0 ;

    //#CM51631
    /**<en> aisle station no. </en>*/

    protected String wAisleStationNumber = "";

	//#CM51632
	/**<en> controller no.</en>*/

    protected int wControllerNumber = 0;

	//#CM51633
	/**<en> set type </en>*/

	protected int wSettingType = 0 ;

	//#CM51634
	/**<en> arrival report check </en>*/

	protected int wArrivalCheck = 0;

	//#CM51635
	/**<en> load size check </en>*/

	protected int wLoadSizeCheck = 0;
	
	//#CM51636
	/**<en> workshop type </en>*/

	protected int wWorkplaceType = 0 ;
	
	//#CM51637
	/**<en> on-line indication </en>*/

	protected int wOperataionDisplay = 0;

	//#CM51638
	/**<en> availability of restorage works </en>*/

	protected int wReStoringOperation = 0;
	
	//#CM51639
	/**<en> sendability of restorage instructions </en>*/

	protected int wReStoringInstruction = 0;
	
	//#CM51640
	/**<en> removal </en>*/

	protected int wRemove = 0 ;

	//#CM51641
	/**<en> mode switch</en>*/

	protected int wModeType = 0;

	//#CM51642
	/**<en> max. number of carry instruction sendable </en>*/

	protected int wMaxInstruction = 0 ;

	//#CM51643
	/**<en> max. number of retrieval instruction sendable </en>*/

	protected int wMaxPaletteQuantity = 0 ;
	
	//#CM51644
	/**<en> warehouse no. that the station belong to </en>*/

	protected String wWareHouseStationNumber ;

	//#CM51645
	/**<en> name of the warehouse that the station belongs to </en>*/

	protected String wWareHouseName ;
	
	//#CM51646
	/**<en> parent station no.. </en>*/

	protected String wParentNumber = null ;
	
	//#CM51647
	// Class method --------------------------------------------------
	//#CM51648
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:01 $") ;
	}

	//#CM51649
	// Constructors --------------------------------------------------
	//#CM51650
	/**<en>
	 * Create the new <code>Station</code> instance. if the instance which has stations 
	 * already defined, please use <code>StationFactory</code> class.
	 * @param conn  :<code>Connection</code> to connect with database
	 * As transaction controls are not internally conducted, it is necessary to commit externally.
	 * @param snum  :station no. preserved
	 * @see StationFactory
	 </en>*/
	public StationParameter()
	{
	}

	//#CM51651
	// Public methods ------------------------------------------------
	//#CM51652
	/**<en>
	* Set the path of the product number folder.
	</en>*/
	public void setFilePath(String filepath)
	{
		wFilePath = filepath;
	}
	
	//#CM51653
	/**<en>
	* Return the path of the product number folder.
	</en>*/
	public String getFilePath()
	{
		return wFilePath;
	}
	
	//#CM51654
	/**<en>
	 * Set the station no.
	 * @param StationNumber
	 </en>*/
	public void setNumber(String stnm)
	{
		wNumber = stnm ;
	}
	
	//#CM51655
	/**<en>
	 * Retrieve the station no.
	 * @return wNumber
	 </en>*/
	public String getNumber()
	{
		return wNumber ;
	}
	
	//#CM51656
	/**<en>
	 * Set the station name.
	 * @param Name
	 </en>*/
	public void setName(String nm)
	{
		wName = nm ;
	}

	//#CM51657
	/**<en>
	 * Retrieve the station name.
	 * @return wName
	 </en>*/
	public String getName()
	{
		return wName ;
	}
	
	//#CM51658
	/**<en>
	 * Set the station type.
	 * @param Type
	 </en>*/
	public void setType(int type)
	{
		wType = type ;
	}

	//#CM51659
	/**<en>
	 * Retrieve the station type.
	 * @return wType
	 </en>*/
	public int getType()
	{
		return wType;
	}
	
	//#CM51660
	/**<en>
	 * Set the aisle station no.
	 * @param AisleStationNumber
	 </en>*/
	public void setAisleStationNumber(String arg)
	{
		wAisleStationNumber = arg;
	}

	//#CM51661
	/**<en>
	 * Retrieve the aisle station no.
	 * @return wAisleStationNumber
	 </en>*/
	public String getAisleStationNumber()
	{
		return wAisleStationNumber;
	}
	
	//#CM51662
	/**<en>
	 * Set the controller no.
	 * @param ControllerNumber
	 </en>*/
	public void setControllerNumber(int arg)
	{
		wControllerNumber = arg;
	}

	//#CM51663
	/**<en>
	 * Retrieve the controller no.
	 * @return wControllerNumber
	 </en>*/
	public int getControllerNumber()
	{
		return wControllerNumber;
	}
	
	//#CM51664
	/**<en>
	 * Set the set type.
	 * @param SettingType
	 </en>*/
	public void setSettingType(int stype)
	{
		wSettingType = stype ;
	}

	//#CM51665
	/**<en>
	 * Retrieve the set type.
	 * @return wSettingType
	 </en>*/
	public int getSettingType()
	{
		return wSettingType;
	}
	
	//#CM51666
	/**<en>
	 * Set the arrival report check.
	 * @param ArrivalCheck
	 </en>*/
	public void setArrivalCheck(int ari)
	{
		wArrivalCheck = ari ;
	}

	//#CM51667
	/**<en>
	 * Return the arrival report check.
	 * @return wArrivalCheck
	 </en>*/
	public int getArrivalCheck()
	{
		return wArrivalCheck ;
	}
	
	//#CM51668
	/**<en>
	 * Set the load size check.
	 * @param LoadSizeCheck
	 </en>*/
	public void setLoadSizeCheck(int load)
	{
		wLoadSizeCheck = load ;
	}

	//#CM51669
	/**<en>
	 * Return the load size check.
	 * @return wLoadSizeCheck
	 </en>*/
	public int getLoadSizeCheck()
	{
		return wLoadSizeCheck ;
	}
	
	//#CM51670
	/**<en>
	 * Set the workshop type.
	 * @param wptype workshop type
	 </en>*/
	public void setWorkplaceType(int wptype)
	{
		wWorkplaceType = wptype ;
	}
	
	//#CM51671
	/**<en>
	 * Retrieve the workshop type.
	 * @return workshop type
	 </en>*/
	public int getWorkplaceType()
	{
		return wWorkplaceType ;
	}

	//#CM51672
	/**<en>
	 * Set the on-line indication.
	 * @param OperationDisplay
	 </en>*/
	public void setOperationDisplay(int dis)
	{
		wOperataionDisplay = dis ;
	}
	
	//#CM51673
	/**<en>
	 * Retrieve the on-line indication.
	 * @return wOperataionDisplay
	 </en>*/
	public int getOperationDisplay()
	{
		return wOperataionDisplay ;
	}
	
	//#CM51674
	/**<en>
	 * Set the availability of restorage works.
	 * @param ReStoringOperation
	 </en>*/
	public void setReStoringOperation(int type)
	{
		wReStoringOperation = type ;
	}

	//#CM51675
	/**<en>
	 * Retrieve the availability of restorage works.
	 * @return wReStoringOperation
	 </en>*/
	public int getReStoringOperation()
	{
		return wReStoringOperation ;
	}

	//#CM51676
	/**<en>
	 * Set the sendability of restorage instructions.
	 * @param ReStoringInstruction
	 </en>*/
	public void setReStoringInstruction(int retype)
	{
		wReStoringInstruction = retype ;
	}

	//#CM51677
	/**<en>
	 * Retrieve the sendability of restorage instructions.
	 * @return wReStoringInstruction
	 </en>*/
	public int getReStoringInstruction()
	{
		return wReStoringInstruction ;
	}

	//#CM51678
	/**<en>
	 * Set the removal type.
	 * @param mt removal
	 </en>*/
	public void setRemove(int rmv)
	{
		wRemove = rmv;
	}

	//#CM51679
	/**<en>
	 * Retrieve the removal type.
	 * @return wRemove
	 </en>*/
	public int getRemove()
	{
		return wRemove;
	}

	//#CM51680
	/**<en>
	 * Set the type of mode switch.
	 * @param ModeType
	 </en>*/
	public void setModeType(int mt)
	{
		wModeType = mt;
	}

	//#CM51681
	/**<en>
	 * Retrieve the type of mode switch.
	 * @return wModeType
	 </en>*/
	public int getModeType()
	{
		return wModeType;
	}

	//#CM51682
	/**<en>
	 * Set the max. number of carry instruction sendable.
	 * @param MaxInstruction
	 </en>*/
	public void setMaxInstruction(int nm)
	{
		wMaxInstruction = nm ;
	}

	//#CM51683
	/**<en>
	 * Retrieve the max. number of carry instruction sendable.
	 * @return wMaxInstruction
	 </en>*/
	public int getMaxInstruction()
	{
		return wMaxInstruction ;
	}
	
	//#CM51684
	/**<en>
	 * Set the max. number of retrieval instruction sendable.
	 * @param MaxPaletteQuantity
	 </en>*/
	public void setMaxPaletteQuantity(int pnum)
	{
		wMaxPaletteQuantity = pnum ;
	}

	//#CM51685
	/**<en>
	 * Retrieve the max. number of retrieval instruction sendable.
	 * @return wMaxPaletteQuantity
	 </en>*/
	public int getMaxPaletteQuantity()
	{
		return wMaxPaletteQuantity ;
	}
	
	//#CM51686
	/**<en>
	 * Set the warehouse no.
	 * @param WareHouseStationNumber
	 </en>*/
	public void setWareHouseStationNumber(String whnum)
	{
		wWareHouseStationNumber = whnum;
	}

	//#CM51687
	/**<en>
	 * Retrieve the warehouse no.
	 * @return wWareHouseStationNumber
	 </en>*/
	public String getWareHouseStationNumber()
	{
		return wWareHouseStationNumber;
	}


	//#CM51688
	/**<en>
	 * Set the warehouse name.
	 * @param WareHouseName
	 </en>*/
	public void setWareHouseName(String wh)
	{
		wWareHouseName = wh;
	}

	//#CM51689
	/**<en>
	 * Retrieve the warehouse name.
	 * @return wWareHouseName
	 </en>*/
	public String getWareHouseName()
	{
		return wWareHouseName;
	}

	//#CM51690
	/**<en>
	 * Set the parent station no.
	 </en>*/
	public void setParentNumber(String pnum)
	{
		wParentNumber = pnum;
	}

	//#CM51691
	/**<en>
	 * Retrieve the parent station no.
	 * @return wParentNumber
	 </en>*/
	public String getParentNumber()
	{
		return wParentNumber;
	}

}
//#CM51692
//end of class

