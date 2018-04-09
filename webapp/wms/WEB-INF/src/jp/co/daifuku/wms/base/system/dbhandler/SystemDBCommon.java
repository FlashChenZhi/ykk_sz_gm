//#CM699633
//$Id: SystemDBCommon.java,v 1.2 2006/10/30 06:39:20 suresh Exp $
package jp.co.daifuku.wms.base.system.dbhandler;

//#CM699634
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.StringTokenizer;

import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.system.entity.SystemWorkerResult;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM699635
/**
 * Designer : T.Nakajima <BR>
 * Maker : T.Nakajima <BR>
 * <BR>
 * Allow this class to execute process common for the database of the System package.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/09/08</TD><TD>T.Nakajima</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:39:20 $
 * @author  $Author: suresh $
 */
public class SystemDBCommon
{
	//#CM699636
	// Class variables -----------------------------------------------

	//#CM699637
	// Class method --------------------------------------------------
	//#CM699638
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:39:20 $");
	}
	//#CM699639
	// Constructors --------------------------------------------------

	//#CM699640
	// Public methods ------------------------------------------------

	//#CM699641
	/**
	 * Allow this method to set the contents of entity for Parameter again.
	 * @param  entity  Entity class
	 * @return Parameter
	 */
	public Parameter[] convertToParams(Entity[] entity)
	{
		SystemParameter sysParam[] = null;
		SystemWorkerResult[] sysWorkerResult = (SystemWorkerResult[]) entity;

		if ((sysWorkerResult != null) && sysWorkerResult.length != 0)
		{
			sysParam = new SystemParameter[sysWorkerResult.length];

			int size = sysWorkerResult.length;

			for (int i = 0; i < size; i++)
			{

				sysParam[i] = new SystemParameter();
				if ((sysWorkerResult[i].getWorkDate() != null)
					&& !sysWorkerResult[i].getWorkDate().equals(""))
				{
					sysParam[i].setWorkDate(sysWorkerResult[i].getWorkDate());
				}

				if (sysWorkerResult[i].getWorkerCode() != null)
				{
					sysParam[i].setWorkerCode(sysWorkerResult[i].getWorkerCode());
				}

				if (sysWorkerResult[i].getWorkerName() != null)
				{
					sysParam[i].setWorkerName(sysWorkerResult[i].getWorkerName());
				}

				if (sysWorkerResult[i].getJobType() != null)
				{
					sysParam[i].setSelectWorkDetail(
						getJobTypeValues(sysWorkerResult[i].getJobType()));
				}

				if (sysWorkerResult[i].getTerminalNo() != null)
				{
					sysParam[i].setTerminalNumber(sysWorkerResult[i].getTerminalNo());
				}

				if (sysWorkerResult[i].getStartTime() != null)
				{
					//#CM699642
					// Compile it into hh:mm:ss form.			
					sysParam[i].setWorkStartTime(putColon(sysWorkerResult[i].getStartTime()));
				}

				if (sysWorkerResult[i].getEndTime() != null)
				{
					//#CM699643
					// Compile it into hh:mm:ss form.	
					sysParam[i].setWorkEndTime(putColon(sysWorkerResult[i].getEndTime()));
				}

				//#CM699644
				// Calculate the time.
				String workTime = "";

				if ((sysWorkerResult[i].getHrs() != null)
					&& !sysWorkerResult[i].getHrs().equals(""))
				{
					if ((sysWorkerResult[i].getDays() != null)
						&& !sysWorkerResult[i].getDays().equals(""))
					{
						workTime =
							String.valueOf(
								Integer.parseInt(sysWorkerResult[i].getHrs())
									+ (Integer.parseInt(sysWorkerResult[i].getDays()) * 24));
					}
					else
					{
						workTime = String.valueOf(Integer.parseInt(sysWorkerResult[i].getHrs()));
					}
				}
				if ((sysWorkerResult[i].getMin() != null)
					&& !sysWorkerResult[i].getMin().equals(""))
				{
					workTime = workTime + ":" + String.valueOf(sysWorkerResult[i].getMin());
				}

				if ((sysWorkerResult[i].getSec() != null)
					&& !sysWorkerResult[i].getSec().equals(""))
				{
					workTime = workTime + ":" + String.valueOf(sysWorkerResult[i].getSec());
				}

				sysParam[i].setWorkTime(calculateTotalTime("", workTime));

				if (sysWorkerResult[i].getWorkQty() != 0)
				{
					sysParam[i].setWorkQty(sysWorkerResult[i].getWorkQty());
					sysParam[i].setWorkQtyPerHour(
						calculateValuePerHour(
							sysWorkerResult[i].getWorkQty(),
							sysParam[i].getWorkTime()));
				}

				if (sysWorkerResult[i].getWorkCnt() != 0)
				{
					sysParam[i].setWorkCnt(sysWorkerResult[i].getWorkCnt());
					sysParam[i].setWorkCntPerHour(
						calculateValuePerHour(
							sysWorkerResult[i].getWorkCnt(),
							sysParam[i].getWorkTime()));
				}

			}

		}

		return sysParam;
	}

	//#CM699645
	// Protected methods ---------------------------------------------

	//#CM699646
	// Private methods ---------------------------------------------

	//#CM699647
	/**
	 * Allow this method to convert the total of both Date values into hours and obtain it.
	 * @param fromDate  Start Date. 'hhmmss'
	 * @param toDate    End Date 'hhmmss'
	 * @return Value of total of both date values converted into hours.
	 */
	private String calculateTotalTime(String frmDate, String toDate)
	{
		String totalTime = "";
		int intSec = 0;
		int intMin = 0;
		int intHr = 0;

		if ((frmDate != null) && !frmDate.equals(""))
		{
			if ((frmDate != null) && !frmDate.equals(""))
			{

				StringBuffer sb = new StringBuffer(frmDate);
				int count = 0;
				for (int i = 0; i < sb.length(); i++)
				{
					if (sb.charAt(i) == ':')
					{
						count += 1;
					}
				}

				if (count == 2)
				{
					StringTokenizer st = new StringTokenizer(frmDate, ":");
					while (st.hasMoreTokens())
					{
						intHr = intHr + Integer.parseInt(st.nextToken());
						intMin = intMin + Integer.parseInt(st.nextToken());
						intSec = intSec + Integer.parseInt(st.nextToken());
					}
				}
			}
		}
		else if ((toDate != null) && !toDate.equals(""))
		{
			StringBuffer sb = new StringBuffer(toDate);
			int count = 0;
			for (int i = 0; i < sb.length(); i++)
			{
				if (sb.charAt(i) == ':')
				{
					count += 1;
				}
			}

			if (count == 2)
			{
				StringTokenizer st = new StringTokenizer(toDate, ":");
				while (st.hasMoreTokens())
				{
					intHr = intHr + Integer.parseInt(st.nextToken());
					intMin = intMin + Integer.parseInt(st.nextToken());
					intSec = intSec + Integer.parseInt(st.nextToken());
				}
			}
		}

		if (intSec >= 60)
		{
			intMin = intMin + (intSec / 60);
			intSec = intSec % 60;
		}
		if (intMin >= 60)
		{
			intHr = intHr + (intMin / 60);
			intMin = intMin % 60;
		}

		if (String.valueOf(intHr).length() == 1)
		{
			totalTime = totalTime + "00" + String.valueOf(intHr) + ":";
		}
		else if (String.valueOf(intHr).length() == 2)
		{
			totalTime = totalTime + "0" + String.valueOf(intHr) + ":";
		}
		else
		{
			totalTime = totalTime + String.valueOf(intHr) + ":";
		}

		if (String.valueOf(intMin).length() == 1)
		{
			totalTime = totalTime + "0" + String.valueOf(intMin) + ":";
		}
		else
		{
			totalTime = totalTime + String.valueOf(intMin) + ":";
		}

		if (String.valueOf(intSec).length() == 1)
		{
			totalTime = totalTime + "0" + String.valueOf(intSec);
		}
		else
		{
			totalTime = totalTime + String.valueOf(intSec);
		}

		return totalTime;
	}

	//#CM699648
	/**
	 * Obtain the value resulted from dividing a value in time type by Value.
	 * @param value Value to divide a time
	 * @param time  Time type => hhh:mm:ss: or hh:mm:ss
	 * @return Value resulted from dividing a value in time type by Value.
	 */
	private int calculateValuePerHour(long value, String time)
	{
		long valuePerHour = 0;
		int hr = 0;
		int min = 0;
		long sec = 0;

		if ((time != null) & !time.equals(""))
		{
			StringBuffer sb = new StringBuffer(time);
			int count = 0;
			for (int i = 0; i < sb.length(); i++)
			{
				if (sb.charAt(i) == ':')
				{
					count += 1;
				}
			}
			if (count == 2)
			{
				StringTokenizer st = new StringTokenizer(time, ":");
				while (st.hasMoreTokens())
				{
					hr = Integer.parseInt(st.nextToken());
					min = Integer.parseInt(st.nextToken());
					sec = Integer.parseInt(st.nextToken());
				}
			}
			if (min > 0)
			{
				sec = sec + (min * 60);
			}
			if (hr > 0)
			{
				sec = sec + (hr * 60 * 60);
			}
			if ((value > 0) && (sec > 0))
			{
				valuePerHour = (value * 60 * 60) / sec;
			}
		}
		
		//#CM699649
		// Check Overflow.
		if (valuePerHour > WmsParam.WORKER_MAX_TOTAL_QTY)
		{
			valuePerHour = WmsParam.WORKER_MAX_TOTAL_QTY;
		}
		
		return (int)valuePerHour;

	}

	//#CM699650
	/**
	 * Allow this method to compile the dateString into hh:mm:ss form.
	 * @param dateString String of date
	 * @return String in hh:mm:ss form
	 */
	private String putColon(String dateString)
	{
		String resultString = "";
		if ((dateString != null) && !dateString.equals(""))
		{
			if (dateString.trim().length() == 6)
			{
				resultString =
					dateString.substring(0, 2)
						+ ":"
						+ dateString.substring(2, 4)
						+ ":"
						+ dateString.substring(4, 6);
			}
		}
		return resultString;
	}

	//#CM699651
	/**
	 * Allow this method to obtain the work name from the resource.
	 * @param arg Work division
	 * @return Work Name
	 */
	private String getJobTypeValues(String arg)
	{
		if (arg.equals(SystemWorkerResult.ARRIVAL))
		{
			//#CM699652
			// Receiving
			return DisplayText.getText("LBL-W0347");
		}
		else if (arg.equals(SystemWorkerResult.STORAGE))
		{
			//#CM699653
			// Storage
			return DisplayText.getText("LBL-W0234");
		}
		else if (arg.equals(SystemWorkerResult.RETRIEVAL))
		{
			//#CM699654
			// Picking
			return DisplayText.getText("LBL-W0348");
		}
		else if (arg.equals(SystemWorkerResult.SORTING))
		{
			//#CM699655
			// Sorting
			return DisplayText.getText("LBL-W0349");
		}
		else if (arg.equals(SystemWorkerResult.SHIPPING))
		{
			//#CM699656
			// Shipping
			return DisplayText.getText("LBL-W0189");
		}
		else if (arg.equals(SystemWorkerResult.INVENTORY))
		{
			//#CM699657
			// Inventory Check
			return DisplayText.getText("LBL-W0350");
		}
		else if (arg.equals(SystemWorkerResult.INVENTORY_PLUS))
		{
			//#CM699658
			// Inventory Check
			return DisplayText.getText("LBL-W0365");
		}
		else if (arg.equals(SystemWorkerResult.INVENTORY_MINUS))
		{
			//#CM699659
			// Inventory Check
			return DisplayText.getText("LBL-W0366");
		}
		else if (arg.equals(SystemWorkerResult.MOV_STORAGE))
		{
			//#CM699660
			// Relocation for Storage
			return DisplayText.getText("LBL-W0351");
		}
		else if (arg.equals(SystemWorkerResult.MOV_RETRIEVAL))
		{
			//#CM699661
			// Relocation for Retrieval
			return DisplayText.getText("LBL-W0352");
		}
		else if (arg.equals(SystemWorkerResult.EX_STORAGE))
		{
			//#CM699662
			// Unplanned Storage
			return DisplayText.getText("LBL-W0353");
		}
		else if (arg.equals(SystemWorkerResult.EX_RETRIEVAL))
		{
			//#CM699663
			// Unplanned Picking
			return DisplayText.getText("LBL-W0354");
		}
		else if (arg.equals(SystemWorkerResult.MAINT_PLUS))
		{
			//#CM699664
			// Maintenance plus
			return DisplayText.getText("LBL-W0363");
		}
		else if (arg.equals(SystemWorkerResult.MAINT_MINUS))
		{
			//#CM699665
			// Maintenance minus
			return DisplayText.getText("LBL-W0364");
		}
		else
		{
			return "";
		}
	}
}
//#CM699666
//end of class
