// $Id: AsRetrievalListSCH.java,v 1.2 2006/10/30 00:53:26 suresh Exp $

//#CM44926
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.report.AsItemRetrievalWriter;
import jp.co.daifuku.wms.asrs.report.AsNoPlanRetrievalWriter;
import jp.co.daifuku.wms.asrs.report.AsOrderRetrievalWriter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM44927
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Class to call delivery work ListPrint processing. <BR>
 * Receive the content input from the screen as parameter, and call picking work ListPrint processing Class. <BR>
 * Process it in this Class as follows. <BR>
 * <BR>
 * 1.Print button pressing processing(<CODE>startSCH()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Class which receives the content input from the screen as parameter, and does Order Picking work ListPrint processing. <BR>
 *   Pass parameter to Item Picking work ListPrint processing Class or No plan retrieval work ListPrint processing Class. <BR>
 *   Retrieve the content of the print with WriterClass. <BR>
 *   Receive true from work ListPrint processing Class when succeeding in the print and False when failing.<BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:53:26 $
 * @author  $Author: suresh $
 */
public class AsRetrievalListSCH extends AbstractAsrsControlSCH
{
	//#CM44928
	//	 Public methods ------------------------------------------------
	//#CM44929
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
	
	//#CM44930
	/**
	 * Acquire the number of cases to be printed based on information input from the screen. <BR>
	 * Return 0 when there is no object data or is an input error. <BR>
	 * Acquire Error Message by calling in case of 0 and using < CODE>getMessage</CODE ><BR>
	 * by former processing. <BR>
	 * 
	 * @param conn Connection object with database
	 * @param countParam < CODE>Parameter</CODE > object including Search condition
	 * @return The number of cases to be printed
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated is generated. 
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		int result = 0;
		AsScheduleParameter param = (AsScheduleParameter) countParam;
		if ((param.getJobType().substring(0, 2)).equals(SystemDefine.JOB_TYPE_RETRIEVAL))
		{
			if (param.getJobType().substring(2).equals("0"))
			{
				//#CM44931
				// Order Picking
				AsOrderRetrievalWriter spWriter = new AsOrderRetrievalWriter(conn);
				createOrderWriter(conn, param, spWriter);
				
				//#CM44932
				// Acquire the number of objects. 
				result = spWriter.count();
				if (result == 0)
				{
					//#CM44933
					// 6003010 = There was no print data. 
					wMessage = "6003010";
				}
			}
			else
			{
				//#CM44934
				// Item Picking
				AsItemRetrievalWriter spWriter = new AsItemRetrievalWriter(conn);
				createItemWriter(conn, param, spWriter);
				
				//#CM44935
				// Acquire the number of objects. 
				result = spWriter.count();
				if (result == 0)
				{
					//#CM44936
					// 6003010 = There was no print data. 
					wMessage = "6003010";
				}
			}
		}
		else if (param.getJobType().equals(SystemDefine.JOB_TYPE_EX_RETRIEVAL))
		{
			//#CM44937
			// No plan retrieval
			AsNoPlanRetrievalWriter spWriter = new AsNoPlanRetrievalWriter(conn);
			createNoPlanWriter(conn, param, spWriter);
			
			//#CM44938
			// Acquire the number of objects. 
			result = spWriter.count();
			if (result == 0)
			{
				//#CM44939
				// 6003010 = There was no print data. 
				wMessage = "6003010";
			}
		}
		else
		{
			throw new ScheduleException();
		}
		
		return result;
	
	}
	//#CM44940
	/**
	 * Receive the content input from the screen as parameter, and pass parameter to Storage PlanListPrint processing Class. <BR>
	 * Do not do the print processing when there is no print data. <BR>
	 * Return True from schedule ListPrint processing Class when succeeding in print and False when failing. 
	 * It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
	 * Schedule is Started. Do the schedule processing according to the content set in the parameter array specified<BR>
	 * by < CODE>startParams</CODE >. Mounting the schedule processing is different according to Class which mounts this interface.   <BR>
	 * Return true when the schedule processing succeeds.  <BR>
	 * False when the schedule processing fails because of the condition error etc. <BR>
	 * In this case, the content can be acquired by using < CODE>getMessage() </ CODE> Method. 
	 * 
	 * @param conn Connection object with database
	 * @param startParams Search Condition object of Database.
	 * @return True when processing is normal, False when the schedule processing fails.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException,
			ScheduleException
	{
		boolean isOk = false;
		AsScheduleParameter sParam = (AsScheduleParameter) startParams[0];
		if (sParam != null)
		{
			if ((sParam.getJobType().substring(0, 2)).equals(SystemDefine.JOB_TYPE_RETRIEVAL))
			{
				if (sParam.getJobType().substring(2).equals("0"))
				{
					//#CM44941
					// Order Picking
					AsOrderRetrievalWriter spWriter = new AsOrderRetrievalWriter(conn);
					createOrderWriter(conn, sParam, spWriter);
			
					//#CM44942
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
				else
				{
					//#CM44943
					// Item Picking
					AsItemRetrievalWriter spWriter = new AsItemRetrievalWriter(conn);
					createItemWriter(conn, sParam, spWriter);
			
					//#CM44944
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
			}
			else if (sParam.getJobType().equals(SystemDefine.JOB_TYPE_EX_RETRIEVAL))
			{
				//#CM44945
				// No plan retrieval
				AsNoPlanRetrievalWriter spWriter = new AsNoPlanRetrievalWriter(conn);
				createNoPlanWriter(conn, sParam, spWriter);
			
				//#CM44946
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
	
	//#CM44947
	//	 Protected methods ------------------------------------------------
	//#CM44948
	/** 
	 * Set information input from the screen in Print process Class,  
	 * and generate Print process Class. <BR>
	 * 
	 * @param conn Connection object with database
	 * @param sParam Parameter object including Search condition
	 * @param spWriter ASRS order picking Print process Class
	 */
	protected void createOrderWriter(Connection conn, AsScheduleParameter sParam, AsOrderRetrievalWriter spWriter)
	{
		//#CM44949
		// Set Work Station.
		if (!StringUtil.isBlank(sParam.getStationNo()))
		{
			spWriter.setStationNumber(sParam.getStationNo());
		}
		
		//#CM44950
		// Set Start day.
		if (!StringUtil.isBlank(sParam.getFromDate()))
		{
			spWriter.setFromDate(sParam.getFromDate());
		}
		
		//#CM44951
		// Set End day.
		if (!StringUtil.isBlank(sParam.getToDate()))
		{
			spWriter.setToDate(sParam.getToDate());
		}
		return;
	}

	//#CM44952
	/** 
	 * Set information input from the screen in Print process Class, 
	 * and generate Print process Class. <BR>
	 * 
	 * @param conn Connection object with database
	 * @param sParam Parameter object including Search condition
	 * @param spWriter ASRS order picking Print process Class
	 */
	protected void createItemWriter(Connection conn, AsScheduleParameter sParam, AsItemRetrievalWriter spWriter)
	{
		//#CM44953
		// Set Work Station.
		if (!StringUtil.isBlank(sParam.getStationNo()))
		{
			spWriter.setStationNumber(sParam.getStationNo());
		}
		
		//#CM44954
		// Set Start day.
		if (!StringUtil.isBlank(sParam.getFromDate()))
		{
			spWriter.setFromDate(sParam.getFromDate());
		}
		
		//#CM44955
		// Set End day.
		if (!StringUtil.isBlank(sParam.getToDate()))
		{
			spWriter.setToDate(sParam.getToDate());
		}
		return;
	}

	//#CM44956
	/** 
	 * Set information input from the screen in Print process Class, 
	 * and generate Print process Class. <BR>
	 * 
	 * @param conn Connection object with database
	 * @param sParam Parameter object including Search condition
	 * @param spWriter ASRS order picking Print process Class
	 */
	protected void createNoPlanWriter(Connection conn, AsScheduleParameter sParam, AsNoPlanRetrievalWriter spWriter)
	{
		//#CM44957
		// Set Work Station.
		if (!StringUtil.isBlank(sParam.getStationNo()))
		{
			spWriter.setStationNumber(sParam.getStationNo());
		}
		
		//#CM44958
		// Set Start day.
		if (!StringUtil.isBlank(sParam.getFromDate()))
		{
			spWriter.setFromDate(sParam.getFromDate());
		}
		
		//#CM44959
		// Set End day.
		if (!StringUtil.isBlank(sParam.getToDate()))
		{
			spWriter.setToDate(sParam.getToDate());
		}
		return;
	}
}
//#CM44960
//end of class
