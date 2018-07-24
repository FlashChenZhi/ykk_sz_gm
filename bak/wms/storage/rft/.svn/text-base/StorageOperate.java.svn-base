//#CM576927
//$Id: StorageOperate.java,v 1.2 2006/12/07 09:00:02 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import java.sql.Connection;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;


//#CM576928
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576929
/**
 * Designer :  <BR>
 * Maker :   <BR>
 * Common function called by RFT Storage<BR>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:02 $
 * @author  $Author: suresh $
 */

public class StorageOperate 
{

	//#CM576930
	// Class fields --------------------------------------------------
    
	//#CM576931
	// Class variables -----------------------------------------------  
	
	//#CM576932
	/**
	 * Process name (Registering process name, Last update Process name)
	 */
	protected String processName = "";

	//#CM576933
	/**
	 * Work start Process name (Registering process name, Last update Process name)
	 */
	protected String startProcessName = "";
	
    //#CM576934
    /**
	 * Database connection
	 */
    protected Connection wConn = null;
    
	//#CM576935
	// Class method --------------------------------------------------

	//#CM576936
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/12/07 09:00:02 $";
	}
	
	//#CM576937
	/**
	 * String when grouping key is Item code<BR>
	 * In other cases, decide grouping key as Item code + ITF + Bundle ITF
	 */
	final int STR_ITEMCODE = 1;

	//#CM576938
	// Constructors --------------------------------------------------
	//#CM576939
	/**
	 * Create instance
	 */
    public StorageOperate()
    {
    	super();
    }
    
	//#CM576940
	// Public methods ------------------------------------------------
    
	//#CM576941
	/**
	 * Set update process name
	 * @param name		Update process name
	 */
	public void setProcessName(String name)
	{
		processName = name;
	}

	//#CM576942
	/**
	 * Set Work start Process name
	 * @param name		Work start Process name
	 */
	public void setStartProcessName(String name)
	{
		startProcessName = name;
	}
    

	//#CM576943
	/**
	 * Set <code>Connection</code> object for database connection<BR>
	 * @param c database connection object Connection
	 */
    public void setConnection(Connection c)
    {
        wConn = c;
    }
	//#CM576944
	/**
	 * Fetch pending Storage work qty<BR>
	 * Make the work possible qty received for worker, RFT in the parameter as pending work. In process data in communication is also included to pending qty<BR>
	 * Search Work info with the following conditions and fetch count<BR>
	 * <DIR>
	 *  <LI>Search conditions</LI><BR>
	 *   Job type = 2(Storage)<BR>
	 *   Status flag = 0:Standby or 2: in processwith same Worker code and RFT no.<BR>
	 *   Work start flag = 1(Started)<BR>
	 *   Consignor code, Storage plan date = fetch based on the parameter values<BR>
	 *   Case piece flag = fetch based on the parameter values(In case of 0:All, don't include in search condition)<BR>
	 *  <LI>Group condition</LI><BR>
	 *   Case piece flag, Location, Expiry date<BR>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	Consignor code<BR>
	 * @param	planDate		Plan Date<BR>
	 * @param	casePieceFlag 		Case piece flag<BR>
	 * @param	rftNo			RFT No.<BR>
	 * @param	workerCode		Worker code<BR>
	 * @return	Pending Storage work qty<BR>
	 * @throws ReadWriteException If abnormal error occurs in database connection<BR>
	 */
	public int countStorageDataOfWorkable(
		String consignorCode,
		String planDate,
		String casePieceFlag,
		String rftNo,
		String workerCode)
		throws ReadWriteException
	{

		WorkingInformationSearchKey pskey = new WorkingInformationSearchKey();
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);

		//#CM576945
		//-----------------
		//#CM576946
		// Search Work info
		//#CM576947
		//-----------------
		//#CM576948
		// Job type is storage
		pskey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		//#CM576949
		// Target data is with Status flag is either [standby] or [in process] ((Workeer code and RFT no. are same)) 
		//#CM576950
		//  SQL query =  (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		pskey.setWorkerCode(workerCode);
		pskey.setTerminalNo(rftNo, "=", "", "))", "AND");

		//#CM576951
		// Work start flag is [1:Started]
		pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		pskey.setConsignorCode(consignorCode);
		pskey.setPlanDate(planDate);
		if (!casePieceFlag.equals(RFTId0230.CASE_PIECE_All))
		{
			pskey.setWorkFormFlag(casePieceFlag);
		}
		//#CM576952
		// Work collection process
		pskey.setWorkFormFlagGroup(1);
		pskey.setLocationNoGroup(2);
		pskey.setUseByDateGroup(3);
		pskey.setWorkFormFlagCollect("");
		pskey.setLocationNoCollect("");
		pskey.setUseByDateCollect("");
		if (WmsParam.STORAGE_JOBCOLLECT)
		{
			pskey.setItemCodeGroup(4);
			pskey.setItemCodeCollect("");
			if (WmsParam.STORAGE_JOBCOLLECT_KEY != STR_ITEMCODE)
			{
				pskey.setItfGroup(5);
				pskey.setBundleItfGroup(6);
				pskey.setItfCollect("");
				pskey.setBundleItfCollect("");
			}
		}
		else
		{
			pskey.setCollectJobNoGroup(4);
			pskey.setCollectJobNoCollect("");
		}
		return pObj.count(pskey);
	}
	
	//#CM576953
	/**
	 * Fetch planned Storage work total qty based on search conditions<BR>
	 * Search Work info with the following conditions and fetch count<BR>
	 * <DIR>
	 *  <LI>Search conditions</LI><BR>
	 *   Consignor code, Storage Plan Date = fetch based on the parameter values<BR>
	 *   Case piece flag = fetch based on the parameter values(In case of 0:All, don't include in search condition)<BR>
	 *   Job type = 2(Storage)<BR>
	 *   Status flag = Other than 9 (Delete)<BR>
	 *   Work start flag = 1(Started)<BR>
	 *  <LI>Group conditions</LI><BR>
	 *   Case piece flag, Location, Expiry date<BR>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode		Consignor code<BR>
	 * @param	planDate		Plan Date<BR>
	 * @param	casePieceFlag 		Case piece flag<BR>
	 * @return	Total plan Storage qty<BR>
	 * @throws ReadWriteException If abnormal error occurs in database connection<BR>
	 */
	public int countStorageDataOfAll(String consignorCode, String planDate, String casePieceFlag)
		throws ReadWriteException
	{

		WorkingInformationSearchKey pskey = new WorkingInformationSearchKey();
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);

		//#CM576954
		//-----------------
		//#CM576955
		// Search Work info
		//#CM576956
		//-----------------
		//#CM576957
		// Job type is storage
		pskey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		//#CM576958
		// Status flag other than [Delete]
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		//#CM576959
		// Work start flag is [1:Started]
		pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		pskey.setConsignorCode(consignorCode);
		pskey.setPlanDate(planDate);
		if (!casePieceFlag.equals(RFTId0230.CASE_PIECE_All))
		{
			pskey.setWorkFormFlag(casePieceFlag);
		}
		//#CM576960
		// Work collection process
		pskey.setWorkFormFlagGroup(1);
		pskey.setLocationNoGroup(2);
		pskey.setUseByDateGroup(3);
		pskey.setWorkFormFlagCollect("");
		pskey.setLocationNoCollect("");
		pskey.setUseByDateCollect("");
		if (WmsParam.STORAGE_JOBCOLLECT)
		{
			pskey.setItemCodeGroup(4);
			pskey.setItemCodeCollect("");
			if (WmsParam.STORAGE_JOBCOLLECT_KEY != STR_ITEMCODE)
			{
				pskey.setItfGroup(5);
				pskey.setBundleItfGroup(6);
				pskey.setItfCollect("");
				pskey.setBundleItfCollect("");
			}
		}
		else
		{
			pskey.setCollectJobNoGroup(4);
			pskey.setCollectJobNoCollect("");
		}
		return pObj.count(pskey);
	}
	
	
	//#CM576961
	// Private methods -----------------------------------------------
}
//#CM576962
//end of class
