// $Id: AsStorageListSCH.java,v 1.2 2006/10/30 00:47:54 suresh Exp $

//#CM45685
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.location.FreeStorageStationOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.asrs.report.AsItemStorageWriter;
import jp.co.daifuku.wms.asrs.report.AsNoPlanStorageWriter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM45686
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Class to call Storage work ListPrint processing. <BR>
 * Receive the content input from the screen as parameter, and call Storage work ListPrint processing Class. <BR>
 * Process it in this Class as follows. <BR>
 * <BR>
 * 1.Print button pressing processing(<CODE>startSCH()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Receive the content input from the screen as parameter, and pass parameter to Storage work ListPrint processing Class  
 *   or No plan storage work ListPrint processing Class. <BR>
 *   Retrieve the content of the print with WriterClass. <BR>
 *   Receive true from work ListPrint processing Class when succeeding in the print and false when failing. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR>
 *   <DIR>
 * 		Work Station*<BR>
 * 		Start day & time<BR>
 * 		End day & time<BR>
 * 		Work type*<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/20</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:47:54 $
 * @author  $Author: suresh $
 */
public class AsStorageListSCH extends AbstractAsrsControlSCH
{
	//#CM45687
	// Public methods ------------------------------------------------
	//#CM45688
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen.  <BR>
	 * There is a button initial display including passing To as an example initial display and outside of Consignor Code.  <BR>
	 * Search condition passes Class which succeeds to < CODE>Parameter</CODE> Class.  <BR>
	 * Set null in < CODE>searchParam</CODE > when you do not need Search condition. 
	 * 
	 * @param conn Connection object with database
	 * @param searchParam Class which succeeds to < CODE>Parameter</CODE> Class with Search condition
	 * @return Class which mounts < CODE>Parameter</CODE > interface where retrieval result is included
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException,
			ScheduleException
	{
		return null;
	}
	
	//#CM45689
	/**
	 * Acquire the number of cases to be printed based on information input from the screen. <BR>
	 * Return 0 when there is no object data or is an input error. <BR>
	 * Acquire Error Message by calling in case of 0 and using < CODE>getMessage</CODE > by former processing.  
	 * Acquire Error Message. <BR>
	 * 
	 * @param conn Connection object with database
	 * @param countParam < CODE>Parameter</CODE > object including Search condition
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated is generated. 
	 * @return The number of cases to be printed
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		int result = 0;
		AsScheduleParameter param = (AsScheduleParameter) countParam;
		if (param.getJobType().equals(SystemDefine.JOB_TYPE_STORAGE))
		{
			//#CM45690
			// Storage
			AsItemStorageWriter spWriter = new AsItemStorageWriter(conn);
			createItemStorageWriter(param, spWriter);
			
			//#CM45691
			// Acquire the number of objects. 
			result = spWriter.count();
		}
		else if (param.getJobType().equals(SystemDefine.JOB_TYPE_EX_STORAGE))
		{
			//#CM45692
			// No plan storage
			AsNoPlanStorageWriter spWriter = new AsNoPlanStorageWriter(conn);
			createNoPlanStorageWriter(conn, param, spWriter);
			
			//#CM45693
			// Acquire the number of objects. 
			result = spWriter.count();
		}
		else
		{
			throw new ScheduleException();
		}

		if (result == 0)
		{
			//#CM45694
			// 6003010 = There was no print data. 
			wMessage = "6003010";
		}
		
		return result;
	
	}
	//#CM45695
	/**
	 * Receive the content input from the screen as parameter, and pass parameter to Storage PlanListPrint processing Class. <BR>
	 * Do not do the print processing when there is no print data. <BR>
	 * Return True from schedule ListPrint processing Class when succeeding in print and False when failing. 
	 * Return the result. <BR>
	 * It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
	 * Schedule is Started. Do the schedule processing according to the content set in the parameter array specified by < CODE>startParams</CODE >. Mounting the schedule processing is different according to Class which mounts this interface.   <BR>
	 * Do the schedule processing. Mounting the schedule processing is different according to Class which mounts this interface.  <BR>
	 * Return true when the schedule processing succeeds.  <BR>
	 * False when the schedule processing fails because of the condition error etc. <BR>
	 * In this case, the content can be acquired by using < CODE>getMessage() </ CODE> Method. 
	 * 
	 * @param conn Connection object with database
	 * @param startParams Search Condition object of Database.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return True when processing is normal, False when the schedule processing fails.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException,
			ScheduleException
	{
		boolean isOk = false;
		AsScheduleParameter sParam = (AsScheduleParameter) startParams[0];
		if (sParam != null)
		{
			if (sParam.getJobType().equals(SystemDefine.JOB_TYPE_STORAGE))
			{
				//#CM45696
				// Storage
				AsItemStorageWriter spWriter = new AsItemStorageWriter(conn);
				createItemStorageWriter(sParam, spWriter);
			
				//#CM45697
				//Print.
				isOk = spWriter.startPrint();
				if (isOk)
				{
					wMessage = "6001010";
				} else
				{
					wMessage = spWriter.getMessage();
				}
			}
			else if (sParam.getJobType().equals(SystemDefine.JOB_TYPE_EX_STORAGE))
			{
				//#CM45698
				// No plan storage
				AsNoPlanStorageWriter spWriter = new AsNoPlanStorageWriter(conn);
				createNoPlanStorageWriter(conn, sParam, spWriter);
			
				//#CM45699
				//Print.
				isOk = spWriter.startPrint();
				if (isOk)
				{
					wMessage = "6001010";
				} 
				else
				{
					wMessage = spWriter.getMessage();
				}
			}
			else
			{
				throw new ScheduleException();
			}
		}
		else
		{
			wMessage = "6003010";
			return false;
		}
		return isOk;
	}
	
	//#CM45700
	// Protected methods ------------------------------------------------
	//#CM45701
	/** 
	 * Set information input from the screen in Print process Class,  
	 * and generate Print process Class. <BR>
	 * 
	 * @param conn Connection object with database
	 * @param parameter Parameter object including Search condition
	 * @return Print processClass
	 */
	protected void createItemStorageWriter(AsScheduleParameter sParam, AsItemStorageWriter spWriter)
	{
		//#CM45702
		// Set Work Station.
		if (!StringUtil.isBlank(sParam.getStationNo()))
		{
			spWriter.setStationNo(sParam.getStationNo());
		}
		
		//#CM45703
		// Set Start day.
		if (!StringUtil.isBlank(sParam.getFromDate()))
		{
			spWriter.setFromDate(sParam.getFromDate());
		}
		
		//#CM45704
		// Set End day.
		if (!StringUtil.isBlank(sParam.getToDate()))
		{
			spWriter.setToDate(sParam.getToDate());
		}
		return;
	}

	//#CM45705
	/** 
	 * Set information input from the screen in Print process Class, 
	 * and generate Print process Class. <BR>
	 * Acquire Retrieval Station No because it is transported to PickingStation for character Station of piece. 
	 * Set it in WriterClass as Search condition. 
	 * Same Station No as Storage-Picking, except for character Station of piece . Same Station No because it becomes it.
	 * Set it as Search condition.
	 * 
	 * @param conn Connection object with database
	 * @param parameter Parameter object including Search condition
	 * @return Print processClass
	 */
	protected void createNoPlanStorageWriter(Connection conn, AsScheduleParameter sParam, AsNoPlanStorageWriter spWriter)
						throws ReadWriteException
	{
		String storageStNo = sParam.getStationNo();
		String retrievalStNo = null;

		//#CM45706
		// Station specified on screen
		Station dispSt = null;
		//#CM45707
		// Operator Class of Station specified on screen
		StationOperator stOpe = null;
		try
		{
			//#CM45708
			// Make Station from the screen input. 
			dispSt = StationFactory.makeStation(conn, storageStNo);
			//#CM45709
			// Make operator Class.  
			stOpe = StationOperatorFactory.makeOperator(conn, dispSt.getStationNumber(), dispSt.getClassName());
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		//#CM45710
		// Acquire Picking side Station for character Station of piece. 
		if (stOpe instanceof FreeStorageStationOperator)
		{
			FreeStorageStationOperator castStOpe = (FreeStorageStationOperator) stOpe;
			retrievalStNo = castStOpe.getFreeRetrievalStationNumber();
		}
		//#CM45711
		// Things except piece character Station (insertion delivery using combinedly)
		//#CM45712
		// Set Station specified on screen in PickingStation. 
		else
		{
			retrievalStNo = stOpe.getStation().getStationNumber();
		}
		
		//#CM45713
		// Set PickingStation.
		if (!StringUtil.isBlank(retrievalStNo))
		{
			spWriter.setRetrievalStationNo(retrievalStNo);
		}
		//#CM45714
		// Set StorageStation.
		if (!StringUtil.isBlank(sParam.getStationNo()))
		{
			spWriter.setStorageStationNo(storageStNo);
		}
		
		//#CM45715
		// Set Start day.
		if (!StringUtil.isBlank(sParam.getFromDate()))
		{
			spWriter.setFromDate(sParam.getFromDate());
		}
		
		//#CM45716
		// Set End day.
		if (!StringUtil.isBlank(sParam.getToDate()))
		{
			spWriter.setToDate(sParam.getToDate());
		}
		
		return;
	}
}
