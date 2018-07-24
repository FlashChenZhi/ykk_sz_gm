//#CM695552
//$Id: AbstractSystemSCH.java,v 1.3 2006/11/21 04:22:38 suresh Exp $

//#CM695553
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

//#CM695554
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this abstract class to execute the process for scheduling the System package.
 * Allow this class to implement the common method. Allow the class that inherits this class to implement the individual behavior of schedule process.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/08/31</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:22:38 $
 * @author  $Author: suresh $
 */
public abstract class AbstractSystemSCH extends AbstractSCH
{
	//#CM695555
	// Class variables -----------------------------------------------
	
	//#CM695556
	// Class method --------------------------------------------------
	//#CM695557
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/21 04:22:38 $");
	}
	//#CM695558
	// Constructors --------------------------------------------------

	//#CM695559
	// Public methods ------------------------------------------------
	
	//#CM695560
	// Protected methods ---------------------------------------------
	//#CM695561
	/**
	 * Check wheter the content of Worker Code, password, and Access Privileges or not.<BR>
	 * Return true if the content is proper, or return false if improper.<BR>
	 * If the returned value is false,
	 * Use the <CODE>getMessage()</CODE> method to obtain the result.<BR>
	 * 
	 * @param  conn               Database connection object
	 * @param  searchParam        This parameter class includes contents to be checked for input.
	 * @param  adminFlag         Ticking off Access Privileges: true if ticked or false if not ticked
	 * @return Return true if the contents of Worker Code and password are proper, or return false if improper.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected boolean checkWorker(Connection conn, SystemParameter searchParam, boolean adminFlag) throws ReadWriteException, ScheduleException
	{
		String workerCode = searchParam.getWorkerCode();
		String password = searchParam.getPassword();
		
		return correctWorker(conn, workerCode, password, adminFlag);
	}
	
	//#CM695562
	/**
	 * Allow this method to update the REPORTDATA in the WarenaviSystem table from 0 to 1. 
	 * @param conn Database connection object
	 * @param status true: Change to "Daily Update in Progress".
	 * 				  false: Reset the process of updating of daily maintenance in progress.
	 * @return Return true when succeeded in the update, or return false when failed in the update.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected boolean changeReportDataFlag(Connection conn, boolean status) throws ReadWriteException
	{
		// Change to "Daily Update in Progress".
		if (status)
		{
			try
			{
				WareNaviSystemAlterKey akey = new WareNaviSystemAlterKey();
				WareNaviSystemHandler wnhdl = new WareNaviSystemHandler(conn);
				akey.setSystemNo(WareNaviSystem.SYSTEM_NO);
				akey.updateReportData(WareNaviSystem.REPORTDATA_LOADING);
				akey.setReportData(WareNaviSystem.REPORTDATA_STOP);
				wnhdl.modify(akey);
				
				return true;
				
			}
			catch (NotFoundException e)
			{
				// 6021015=In process of data report. Cannot process.
				wMessage = "6021015";
				return false;
			}
			catch (InvalidDefineException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
		}
		// Reset the process of updating of daily maintenance in progress.
		else
		{
			try
			{
				WareNaviSystemAlterKey akey = new WareNaviSystemAlterKey();
				WareNaviSystemHandler wnhdl = new WareNaviSystemHandler(conn);
				akey.setSystemNo(WareNaviSystem.SYSTEM_NO);
				akey.updateReportData(WareNaviSystem.REPORTDATA_STOP);
				akey.setReportData(WareNaviSystem.REPORTDATA_LOADING);
				wnhdl.modify(akey);
				
				return true;
				
			}
			catch (NotFoundException e)
			{
				// メンテナンス画面などより、warenaviSystemテーブルを更新された場合に発生する可能性があります。
				// ただし、報告処理終了後のフラグOFFの処理のため、例外扱いにはせずログに落としfalseを返します。
				Object[] tObj = new Object[1] ;
				tObj[0] = "warenavi_system";
				// 6026017=Cannot update. The data you try to update was updated in other process. {0}
				RmiMsgLogClient.write(6026017, this.getClass().getName(), tObj);
				return false;
			}
			catch (InvalidDefineException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
		}
	}
	
}
//#CM695563
//end of class
