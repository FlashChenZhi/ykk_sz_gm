// $Id: Id5021DataFile.java,v 1.2 2006/11/14 06:09:06 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import java.io.IOException;
import java.util.Vector;

import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdDataFile;

//#CM701584
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM701585
/**
 * The class to operate the Data file sent and received with ID5021. 
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of each line of file of ID5021</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH>	<TH>Content</TH></TR>
 * <tr><td>Plan date</td>			<td>16 byte</td><td>  </td></tr>
 * <tr><td>CRLF</td>			<td>2 byte</td>	<td>CR + LF</td></tr>
 * </table>
 * </p>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:06 $
 * @author $Author: suresh $
 */
public class Id5021DataFile extends IdDataFile
{
	//#CM701586
	// Class fields----------------------------------------------------
	//#CM701587
	/**
	 * Definition of Offset of Plan date
	 */
	private static final int OFF_PLAN_DATE = 0;

	//#CM701588
	/**
	 * Field which shows Plan dateList file name
	 */
	static final String ANS_FILE_NAME = "ID5021.txt" ;

	//#CM701589
	// Constructors --------------------------------------------------
	//#CM701590
	/**
	 * Generate the instance. 
	 * @param filename File Name
	 */
	public Id5021DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_PLAN_DATE + LEN_PLAN_DATE;
	}

	//#CM701591
	/**
	 * Generate the instance. 
	 */
	public Id5021DataFile()
	{
		lineLength = OFF_PLAN_DATE + LEN_PLAN_DATE;
	}
	//#CM701592
	/**
	 * Read the Data file, put in the array of WorkingInformation, and return it. 
	 * 
	 * @return Array of Working Information(Entity class)
	 * @throws IOException		Error was generated while inputting and outputting the Entity class file. 
	 */
	public Entity[] getCompletionData() throws IOException
	{
		Vector v = new Vector();
		
		openReadOnly();
			
		for (next(); currentLine != null; next())
		{
			WorkingInformation workinfo = new WorkingInformation();

			workinfo.setPlanDate(getPlanDate());
				
			v.addElement(workinfo);
		}
			
		closeReadOnly();

		WorkingInformation[] data = new WorkingInformation[v.size()];
		v.copyInto(data);
		return data;
	}

	//#CM701593
	/**
	 * The file writes Data of the Working Information array specified by the argument. 
	 * 
	 * @param	obj				Data written in file (Working Information array) 
	 * @throws IOException		Error was generated while inputting and outputting the Entity class file. 
	 */
	public void write(Entity[] obj) throws IOException
	{
		WorkingInformation[] workinfo = (WorkingInformation[])obj;
		
		openWritable();
			
		for (int i = 0; i < workinfo.length; i ++)
		{
			setPlanDate(workinfo[i].getPlanDate());
			
			writeln();
		}
		closeWritable();
	}

	//#CM701594
	/**
	 * The file writes Data of the Plan date array specified by the argument. 
	 * 
	 * @param	planDate		Data written in file (String array) 
	 * @throws IOException		Error was generated while inputting and outputting the Entity class file. 
	 */
	public void write(String[] planDate) throws IOException
	{
		openWritable();
			
		for (int i = 0; i < planDate.length; i ++)
		{
			setPlanDate(planDate[i]);
			
			writeln();
		}

		closeWritable();
	}

	//#CM701595
	/**
	 * Acquire Plan date from the Data buffer. 
	 * @return		Plan date
	 */
	public String getPlanDate()
	{
		return getColumn(OFF_PLAN_DATE, LEN_PLAN_DATE);
	}
	
	//#CM701596
	/**
	 * Set Plan date in the Data buffer. 
	 * @param	planDate	Plan date
	 */
	public void setPlanDate(String planDate)
	{
		setColumn(planDate, OFF_PLAN_DATE, LEN_PLAN_DATE);
	}
}
//#CM701597
//end of class
