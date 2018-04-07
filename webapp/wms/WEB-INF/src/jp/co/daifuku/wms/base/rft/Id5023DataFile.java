//#CM701614
//$Id: Id5023DataFile.java,v 1.2 2006/11/14 06:09:06 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import java.io.IOException;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.entity.ZoneView;
import jp.co.daifuku.wms.base.rft.IdDataFile;

//#CM701615
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM701616
/**
 * The class to operate the Data file sent and received with ID5023. 
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:06 $
 * @author $Author: suresh $
 */
public class Id5023DataFile extends IdDataFile
{
    //#CM701617
    /**
	 * Offset of Area No.
	 */
	private static final int OFF_AREA_NO = 0;
	
	//#CM701618
	/**
	 * Offset of Area Name
	 */
	private static final int OFF_AREA_NAME = OFF_AREA_NO + LEN_AREA_NO;
	
	//#CM701619
	/**
	 * Offset of Zone No
	 */
	private static final int OFF_ZONE_NO = OFF_AREA_NAME + LEN_AREA_NAME;
	
	//#CM701620
	/**
	 * Offset of Zone Name
	 */
	private static final int OFF_ZONE_NAME = OFF_ZONE_NO + LEN_ZONE_NO;

	//#CM701621
	/**
	 * Field which shows Area List file name
	 */
	static final String ANS_FILE_NAME = "ID5023.txt" ;

	//#CM701622
	// Constructors --------------------------------------------------
	//#CM701623
	/**
	 * Constructor.
	 */
	public Id5023DataFile()
	{
		lineLength = OFF_ZONE_NAME + LEN_ZONE_NAME;
	}
	//#CM701624
	/**
	 * Constructor.
	 * @param filename File Name
	 */
	public Id5023DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_ZONE_NAME + LEN_ZONE_NAME;
	}

	//#CM701625
	/**
	 * Read the Data file, put in the array of < CODE>ZoneView</CODE >, and return it. 
	 * 
	 * @return Array of Zone view(Entity class)
	 * @throws IOException		Error was generated while inputting and outputting the Entity class file. 
	 */
	public Entity[] getCompletionData() throws IOException, InvalidStatusException
	{
		Vector v = new Vector();
		
		openReadOnly();
			
		for (next(); currentLine != null; next())
		{
			ZoneView zoneview = new ZoneView();
				
			zoneview.setAreaNo(getAreaNo());
			zoneview.setAreaName(getAreaName());
			zoneview.setZoneNo(getZoneNo());
			zoneview.setZoneName(getZoneName());
			
			v.addElement(zoneview);
		}
		
		closeReadOnly();

		WorkingInformation[] data = new WorkingInformation[v.size()];
		v.copyInto(data);
		return data;
	}

	//#CM701626
	/**
	 * The file writes Data of < CODE>ZoneView</CODE > array specified by the argument. 
	 * 
	 * @param	obj				Data written in file (Zone View array) 
	 * @throws IOException		Error was generated while inputting and outputting the Entity class file. 
	 */
	public void write(Entity[] obj) throws IOException
	{
		ZoneView[] zoneview = (ZoneView[])obj;
		
		openWritable();
			
		for (int i = 0; i < zoneview.length; i ++)
		{
			setAreaNo(zoneview[i].getAreaNo());
			setAreaName(zoneview[i].getAreaName());
			setZoneNo(zoneview[i].getZoneNo());
			setZoneName(zoneview[i].getZoneName());
			
			
			writeln();
		}
			
		closeWritable();
	}

	//#CM701627
	/**
	 * Acquire Area  No. from the Data buffer. 
	 * @return	Area No.
	 */
	public String getAreaNo()
	{
		return getColumn(OFF_AREA_NO, LEN_AREA_NO) ;
	}

	//#CM701628
	/**
	 * Set Area No in the Data buffer.
	 * @param	areaNo		Area No.
	 */
	public void setAreaNo(String laneNo)
	{
		setColumn(laneNo, OFF_AREA_NO, LEN_AREA_NO) ;
	}
	
	//#CM701629
	/**
	 * Acquire Area Name from the Data buffer. 
	 * @return Area Name
	 */
	public String getAreaName()
	{
	    return getColumn(OFF_AREA_NAME, LEN_AREA_NAME);
	}
	
	//#CM701630
	/**
	 * Set Area Name in the Data buffer. 
	 * @param Area Name Area Name
	 */
	public void setAreaName(String areaName)
	{
	    setColumn(areaName, OFF_AREA_NAME, LEN_AREA_NAME);
	}
	
	//#CM701631
	/**
	 * Acquire Zone No from the Data buffer. 
	 * @return Zone No
	 */
	public String getZoneNo()
	{
	    return getColumn(OFF_ZONE_NO, LEN_ZONE_NO);
	}
	
	//#CM701632
	/**
	 * Set Zone No in the Data buffer. 
	 * @param zoenNo Zone No
	 */
	public void setZoneNo(String zoneNo)
	{
	    setColumn(zoneNo, OFF_ZONE_NO, LEN_ZONE_NO);
	}
	
	//#CM701633
	/**
	 * Acquire Zone Name from the Data buffer. 
	 * @return Zone Name
	 */
	public String getZoneName()
	{
	    return getColumn(OFF_ZONE_NAME, LEN_ZONE_NAME);
	}
	
	//#CM701634
	/**
	 * Set Zone Name in the Data buffer. 
	 * @param zoenName Zone Name
	 */
	public void setZoneName(String zoneName)
	{
	    setColumn(zoneName, OFF_ZONE_NAME, LEN_ZONE_NAME);
	}
}

