// $Id: ToolStationAlterKey.java,v 1.2 2006/10/30 02:17:14 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48366
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Date;

//#CM48367
/**<en>
 * Defined in this class is the informtion to update the definitionStation table.
 * It will be used when updating tables by StationHandler.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:14 $
 * @author  $Author: suresh $
 </en>*/
public class ToolStationAlterKey extends ToolSQLAlterKey
{
	//#CM48368
	// Class fields --------------------------------------------------

	//#CM48369
	//<en> Define here the columns which could be search conditions or the target data of update. </en>
	private static final String STATIONNUMBER         = "TEMP_STATION.STATIONNUMBER";
	private static final String STATUS                = "TEMP_STATION.STATUS";
	private static final String CARRYKEY              = "TEMP_STATION.CARRYKEY";
	private static final String HEIGHT                = "TEMP_STATION.HEIGHT";
	private static final String BCDATA                = "TEMP_STATION.BCDATA";
	private static final String LASTUSEDSTATIONNUMBER = "TEMP_STATION.LASTUSEDSTATIONNUMBER";
	private static final String SUSPEND               = "TEMP_STATION.SUSPEND";
	private static final String CURRENTMODE           = "TEMP_STATION.CURRENTMODE";
	private static final String MODECHANGERQUEST      = "TEMP_STATION.MODEREQUEST";
	private static final String MODECHANGERQUESTTIME  = "TEMP_STATION.MODEREQUESTTIME";
	private static final String INVENTORYCHECKFLAG    = "TEMP_STATION.INVENTORYCHECKFLAG";
	private static final String MODETYPE              = "TEMP_STATION.MODETYPE";
	private static final String PARENTSTATIONNUMBER   = "TEMP_STATION.PARENTSTATIONNUMBER";
	private static final String AISLESTATIONNUMBER     = "TEMP_STATION.AISLESTATIONNUMBER";
	private static final String STATIONTYPE           = "TEMP_STATION.STATIONTYPE";
	private static final String STATIONNAME           = "TEMP_STATION.STATIONNAME";
	private static final String MAXINSTRUCTION        = "TEMP_STATION.MAXINSTRUCTION";
	private static final String MAXPALETTEQUANTITY    = "TEMP_STATION.MAXPALETTEQUANTITY";
	private static final String CONTROLLERNUMBER      = "TEMP_STATION.CONTROLLERNUMBER";
	private static final String CLASSNAME             = "TEMP_STATION.CLASSNAME";
	
	//#CM48370
	// Class variables -----------------------------------------------

	//#CM48371
	//<en> Set the vaeiables, defined with the declared column name, in the array.</en>
	private static final String[] Columns =
	{
		STATIONNUMBER,
		STATUS,
		CARRYKEY,
		HEIGHT,
		BCDATA,
		LASTUSEDSTATIONNUMBER,
		SUSPEND,
		CURRENTMODE,
		MODECHANGERQUEST,
		MODECHANGERQUESTTIME,
		INVENTORYCHECKFLAG,
		MODETYPE,
		PARENTSTATIONNUMBER,
		AISLESTATIONNUMBER,
		STATIONTYPE,
		STATIONNAME,
		MAXINSTRUCTION,
		MAXPALETTEQUANTITY,
		CONTROLLERNUMBER,
		CLASSNAME
	};

	//#CM48372
	// Class method --------------------------------------------------
	//#CM48373
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the dat
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM48374
	// Constructors --------------------------------------------------
	//#CM48375
	/**<en>
	 * Conduct the initial setting of the table column declared.
	 </en>*/
	public ToolStationAlterKey()
	{
		setColumns(Columns);
	}

	//#CM48376
	// Public methods ------------------------------------------------
	//#CM48377
	//<en>=============<Method of update condition settings>=============</en>

	//#CM48378
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 * @param :station no. to be searched
	 </en>*/
	public void setNumber(String stnum)
	{
		setValue(STATIONNUMBER, stnum);
	}

	//#CM48379
	/**<en>
	 * Retrieve the STATIONNUMBER.
	 * @return :station no. to be searched
	 </en>*/
	public String getNumber()
	{
		return (String)getValue(STATIONNUMBER);
	}

	//#CM48380
	/**<en>
	 * Set the search value of PARENTSTATIONNUMBER.
	 * @param :parent station no. to be searched
	 </en>*/
	public void setParentStationNumber(String stnum)
	{
		setValue(PARENTSTATIONNUMBER, stnum);
	}

	//#CM48381
	/**<en>
	 * Retrieve the search value of PARENTSTATIONNUMBER.
	 * @return :parent station no. to be searched
	 </en>*/
	public String getParentStationNumber()
	{
		return (String)getValue(PARENTSTATIONNUMBER);
	}
	
	//#CM48382
	/**<en>
	 * Set the search value of AISLESTATIONNUMBER.
	 * @param :aisle station no. to be searched
	 </en>*/
	public void setAisleStationNumber(String al)
	{
		setValue(AISLESTATIONNUMBER, al);
	}

	//#CM48383
	/**<en>
	 * Retrieve the search value of AISLESTATIONNUMBER.
	 * @return :aisle station no. to be searched
	 </en>*/
	public String getAisleStationNumber()
	{
		return (String)getValue(AISLESTATIONNUMBER);
	}
	
	//#CM48384
	/**<en>
	 * Set the search value of STATIONNAME.
	 * @param :station name to be searched
	 </en>*/
	public void setStationName(String sn)
	{
		setValue(STATIONNAME, sn);
	}

	//#CM48385
	/**<en>
	 * Retrieve the search value of STATIONNAME.
	 * @return :station name to be searched
	 </en>*/
	public String getStationName()
	{
		return (String)getValue(STATIONNAME);
	}
	
	//#CM48386
	/**<en>
	 * Set the update value of STATUS.
	 * @param :update value of STATUS
	 </en>*/
	public void updateStatus(int num)
	{
		setUpdValue(STATUS, num);
	}

	//#CM48387
	/**<en>
	 * Set the update value of CARRYKEY.
	 * @param :update value of CARRYKEY
	 </en>*/
	public void updateCarryKey(String ckey)
	{
		setUpdValue(CARRYKEY, ckey);
	}

	//#CM48388
	/**<en>
	 * Set the update value of HEIGHT.
	 * @param :update value of HEIGHT
	 </en>*/
	public void updateHegiht(int num)
	{
		setUpdValue(HEIGHT, num);
	}

	//#CM48389
	/**<en>
	 * Set the update value of BCDATA.
	 * @param :update value of BCDATA
	 </en>*/
	public void updateBCData(String bcd)
	{
		setUpdValue(BCDATA, bcd);
	}

	//#CM48390
	/**<en>
	 * Set the update value of LASTUSEDSTATIONNUMBER.
	 * @param :update value of LASTUSEDSTATIONNUMBER
	 </en>*/
	public void updateLastUsedStationNumber(String lst)
	{
		setUpdValue(LASTUSEDSTATIONNUMBER, lst);
	}

	//#CM48391
	/**<en>
	 * Set the update value of SUSPEND.
	 * @param :update value of SUSPEND
	 </en>*/
	public void updateSuspend(int sus)
	{
		setUpdValue(SUSPEND, sus) ;
	}

	//#CM48392
	/**<en>
	 * Set the update value of INVENTORYCHECKFLAG.
	 * @param :update value of INVENTORYCHECKFLAG
	 </en>*/
	public void updateInventoryCheck(int flg)
	{
		setUpdValue(INVENTORYCHECKFLAG, flg) ;
	}

	//#CM48393
	/**<en>
	 * Set the update value of CURRENTMODE.
	 * @param :update value of CURRENTMODE
	 </en>*/
	public void updateCurrentMode(int mode)
	{
		setUpdValue(CURRENTMODE, mode);
	}

	//#CM48394
	/**<en>
	 * Set the update value of MODECHANGERQUEST.
	 * @param :update value of MODECHANGERQUEST
	 </en>*/
	public void updateModeChangeRequest(int req)
	{
		setUpdValue(MODECHANGERQUEST, req);
	}

	//#CM48395
	/**<en>
	 * Set the update value of MODECHANGERQUESTTIME.
	 * @param :update value of MODECHANGERQUESTTIME
	 </en>*/
	public void updateModeChangeRequestTime(Date dt)
	{
		setUpdValue(MODECHANGERQUESTTIME, dt);
	}

    //#CM48396
    /**<en>
	 * Set the update value of MODETYPE.
	 * @param :update value of MODETYPE
	 </en>*/ 
	public void updateModeType(int mtype)
	{
		setUpdValue(MODETYPE, mtype);
	}
	
	//#CM48397
	/**<en>
	 * Set the update value of PARENTSTATIONNUMBER.
	 * @param :update value of PARENTSTATIONNUMBER
	 </en>*/ 
	public void updateParentStationNumber(String psn)
	{
		setUpdValue(PARENTSTATIONNUMBER, psn);
	}
	
	//#CM48398
	/**<en>
	 * Set the update value of AISLESTATIONNUMBER.
	 * @param :update value of AISLESTATIONNUMBER
	 </en>*/ 
	public void updateAisleStationNumber(String asn)
	{
		setUpdValue(AISLESTATIONNUMBER, asn);
	}
	
	//#CM48399
	/**<en>
	 * Set the update value of STATIONTYPE.
	 * @param :update value of STATIONTYPE
	 </en>*/
	public void updateStationType(int stt)
	{
		setUpdValue(STATIONTYPE, stt);
	}
	
	//#CM48400
	/**<en>
	 * Set the update value of STATIONNAME.
	 * @param :update value of STATIONNAME
	 </en>*/
	public void updateStationName(String sn)
	{
		setUpdValue(STATIONNAME, sn);
	}
	
	//#CM48401
	/**<en>
	 * Set the update value of MAXINSTRUCTION.
	 * @param :update value of MAXINSTRUCTION
	 </en>*/
	public void updateMaxInstruction(int mi)
	{
		setUpdValue(MAXINSTRUCTION, mi);
	}
	
	//#CM48402
	/**<en>
	 * Set the update value of MAXPALETTEQUANTITY.
	 * @param :update value of MAXPALETTEQUANTITY
	 </en>*/
	public void updateMaxPaletteQuantity(int mp)
	{
		setUpdValue(MAXPALETTEQUANTITY, mp);
	}
	
	//#CM48403
	/**<en>
	 * Set the update value of CONTROLLERNUMBER.
	 * @param :update value of CONTROLLERNUMBER
	 </en>*/
	public void updateControllerNumber(int crn)
	{
		setUpdValue(CONTROLLERNUMBER, crn);
	}
	
	//#CM48404
	/**<en>
	 * Set the update value of CLASSNAME.
	 * @param :update value of CLASSNAME
	 </en>*/
	public void updateClassName(String cln)
	{
		setUpdValue(CLASSNAME, cln);
	}
	
	
	//#CM48405
	// Package methods -----------------------------------------------

	//#CM48406
	// Protected methods ---------------------------------------------

	//#CM48407
	// Private methods -----------------------------------------------

	//#CM48408
	// Inner Class ---------------------------------------------------

}
//#CM48409
//end of class

