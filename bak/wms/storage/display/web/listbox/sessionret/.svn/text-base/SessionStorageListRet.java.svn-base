package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570177
/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR> 
 * The class to do data retrieval for Storage Work list box Retrieval processing.<BR>
 * Receive Search condition with Parameter, and do Retrieval of the Storage Work list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageListRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   < CODE>find</CODE> Method is called and Retrieval of Work information is done. <BR>
 * <BR>
 *   <Search condition> MandatoryItem*<BR>
 *   <DIR>
 *      , WorkFlag : Storage *<BR>
 *      , Consignor Code*<BR>
 *      , Start Storage plan date<BR>
 *      , End Storage plan date<BR>
 *      , Item Code<BR>
 *      , Case , PieceFlag<BR>
 *      , Work Status<BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DNWORKINFO <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Display processing(<CODE>getEntities</CODE> Method)<BR>
 * <DIR>
 *   Acquire the data to display it on the screen. <BR>
 *   1.Acquire display information from the retrieval result of obtaining in Retrieval processing. <BR>
 * <BR>
 *   <Display Item><BR>
 *   <DIR>
 *      , Consignor Code<BR>
 *      , Consignor Name<BR>
 *      , Storage plan date<BR>
 *      , Item Code<BR>
 *      , Item Name<BR>
 *      , Case , PieceFlag Description<BR>
 *      , Storage Location<BR>
 *      , Plan total qty<BR>
 *      , Expiry Date<BR>
 *      , Packed qty per case<BR>
 *      , Packed qty per bundle<BR>
 *      , CaseITF<BR>
 *	    , Bundle ITF<BR>
 *      , PlanCase qty<BR>
 *      , PlanPiece qty<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/30	</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:29 $
 * @author  $Author: suresh $
 */
public class SessionStorageListRet extends SessionRet
{
	//#CM570178
	// Class fields --------------------------------------------------
	//#CM570179
	// Class variables -----------------------------------------------
	//#CM570180
	/**
	 * For Consignor Name acquisition
	 */
	private String wConsignorName = "";
	
	//#CM570181
	// Class method --------------------------------------------------
	
	//#CM570182
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:29 $");
	}

	//#CM570183
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageListRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM570184
	// Public methods ------------------------------------------------
	//#CM570185
	/**
	 * Return partial qty of Retrieval result of <CODE>DnWorkInfo</CODE><BR>
	 * <DIR>
	 * <Retrieval result><BR>
	 * 	<DIR>
	 *      , Consignor Code<BR>
	 *      , Consignor Name<BR>
	 *      , Storage plan date<BR>
	 *      , Item Code<BR>
	 *      , Item Name<BR>
	 *      , Case, Piece Flag description<BR>
	 *      , Storage Location<BR>
	 *      , Plan total qty<BR>
	 *      , Expiry Date<BR>
	 *      , Packed qty per case<BR>
	 *      , Packed qty per bundle<BR>
	 *      , CaseITF<BR>
	 *	    , Bundle ITF<BR>
	 *      , PlanCase qty<BR>
	 *      , PlanPiece qty<BR>
	 *  </DIR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnWorkInfo
	 */
	public Parameter[] getEntities()
	{
		StorageSupportParameter[] resultArray = null;
		WorkingInformation temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	
			try
			{	
				temp = (WorkingInformation[])((WorkingInformationFinder)wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToStorageSupportParams(temp);
			}
			catch (Exception e)
			{
				//#CM570186
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}
	

	//#CM570187
	// Package methods -----------------------------------------------

	//#CM570188
	// Protected methods ---------------------------------------------

	//#CM570189
	// Private methods -----------------------------------------------
	//#CM570190
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>WorkingInformationFinder</code> as instance variable for retrieval. <BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param searchParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter searchParam) throws Exception
	{	

		WorkingInformationSearchKey searchkey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey consignorkey = new WorkingInformationSearchKey();
		//#CM570191
		// Set Work flag
		searchkey.setJobType(SystemDefine.JOB_TYPE_STORAGE);
		consignorkey.setJobType(SystemDefine.JOB_TYPE_STORAGE);
		//#CM570192
		// Set the Consignor code. Mandatory Item. 
		searchkey.setConsignorCode(searchParam.getConsignorCode());
		consignorkey.setConsignorCode(searchParam.getConsignorCode());
		
		if(!StringUtil.isBlank(searchParam.getFromStoragePlanDate())){
			//#CM570193
			// Start Storage plan date
			searchkey.setPlanDate(searchParam.getFromStoragePlanDate(),">=");
			consignorkey.setPlanDate(searchParam.getFromStoragePlanDate(),">=");
		}		
		
		if(!StringUtil.isBlank(searchParam.getToStoragePlanDate())){
			//#CM570194
			// End Storage plan date
			searchkey.setPlanDate(searchParam.getToStoragePlanDate(),"<=");
			consignorkey.setPlanDate(searchParam.getToStoragePlanDate(),"<=");
		}
		
		if(!StringUtil.isBlank(searchParam.getItemCode())){
			//#CM570195
			// Item Code
			searchkey.setItemCode(searchParam.getItemCode());
			consignorkey.setItemCode(searchParam.getItemCode());
		}
		
		//#CM570196
		//Check Case , Piece Flag
		if(!StringUtil.isBlank(searchParam.getCasePieceflg()))
		{			
			
			if(SystemDefine.CASEPIECE_FLAG_CASE.equals(searchParam.getCasePieceflg()))
			{			
				searchkey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_CASE);
				consignorkey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_CASE);
			}else if(SystemDefine.CASEPIECE_FLAG_PIECE.equals(searchParam.getCasePieceflg()))
			{			
				searchkey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
				consignorkey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
			}else if(SystemDefine.CASEPIECE_FLAG_NOTHING.equals(searchParam.getCasePieceflg()))
			{			
				searchkey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
				consignorkey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
			}
		}
		
		//#CM570197
		// Work Status
		if(!StringUtil.isBlank(searchParam.getStatusFlagL()))
		{   
			if(searchParam.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_ALL))
			{
				searchkey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE,"!=");
				consignorkey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE,"!=");
			}
			else
			{
				searchkey.setStatusFlag(searchParam.getStatusFlagL());
				consignorkey.setStatusFlag(searchParam.getStatusFlagL());
			}
		}
		//#CM570198
		// Retrieves excluding ASRS Work. 
		searchkey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		consignorkey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		
		//#CM570199
		// Set the order of acquisition. 		
		searchkey.setConsignorCodeCollect("");
		searchkey.setConsignorNameCollect("");
		searchkey.setPlanDateCollect("");
		searchkey.setItemCodeCollect("");
		searchkey.setItemName1Collect("");
		searchkey.setWorkFormFlagCollect("");
		searchkey.setResultLocationNoCollect("");
		searchkey.setLocationNoCollect("");
		searchkey.setEnteringQtyCollect("");
		searchkey.setBundleEnteringQtyCollect("");
		searchkey.setHostPlanQtyCollect("");
		searchkey.setResultUseByDateCollect("");
		searchkey.setUseByDateCollect("");
		searchkey.setPlanEnableQtyCollect("");
		searchkey.setItfCollect("");
		searchkey.setBundleItfCollect("");
		
		//#CM570200
		// Set the order of sorting. 
		if (!StringUtil.isBlank(searchParam.getStatusFlagL()))
		{
			searchkey.setPlanDateOrder(1,true);
			searchkey.setItemCodeOrder(2,true);
			searchkey.setWorkFormFlagOrder(3,true);
			searchkey.setLocationNoOrder(4,true);
				
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				searchkey.setUseByDateOrder(5,true);
			}	
		}
		
		wFinder = new WorkingInformationFinder(wConn);
		//#CM570201
		// Cursor open
		wFinder.open();
		int count = ((WorkingInformationFinder)wFinder).search(searchkey);
		//#CM570202
		// Initialization
		wLength = count;
		wCurrent = 0;
		
		//#CM570203
		// Acquire Consignor Name. 
		consignorkey.setConsignorNameCollect("");
		consignorkey.setRegistDateOrder(1, false);

		WorkingInformationFinder consignorFinder = new WorkingInformationFinder(wConn);
		consignorFinder.open();
		int nameCountCsg = consignorFinder.search(consignorkey);
		if (nameCountCsg > 0 && nameCountCsg <= DatabaseFinder.MAXDISP)
		{
			WorkingInformation view[] = (WorkingInformation[]) consignorFinder.getEntities(0, 1);

			if (view != null && view.length != 0)
			{
				wConsignorName = view[0].getConsignorName();
			}
		}
		consignorFinder.close();
	}
	
	//#CM570204
	/**
	 * Convert the Working Infomation entity into <CODE>StorageSupportParameter</CODE>. <BR>
	 * <BR>
	 * @param workingInformation WorkingInformation[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private StorageSupportParameter[] convertToStorageSupportParams(WorkingInformation[] workingInformation)
	{			
		StorageSupportParameter[] storageSupport = new StorageSupportParameter[workingInformation.length];
		for(int i=0;i<workingInformation.length;++i)
		{
			storageSupport[i] = new StorageSupportParameter();
			if(i==0)
			{
				storageSupport[0].setConsignorCode(workingInformation[0].getConsignorCode());
				storageSupport[0].setConsignorName(wConsignorName);
			}
			storageSupport[i].setStoragePlanDate(workingInformation[i].getPlanDate());
			storageSupport[i].setItemCode(workingInformation[i].getItemCode());
			storageSupport[i].setItemName(workingInformation[i].getItemName1());			
			storageSupport[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(workingInformation[i].getWorkFormFlag()));
			if (StringUtil.isBlank(workingInformation[i].getResultLocationNo()))
			{
				storageSupport[i].setStorageLocation(workingInformation[i].getLocationNo());
			}
			else
			{
				storageSupport[i].setStorageLocation(workingInformation[i].getResultLocationNo());
			}
			storageSupport[i].setTotalPlanQty(workingInformation[i].getHostPlanQty());
			if (StringUtil.isBlank(workingInformation[i].getResultLocationNo()))
			{
				storageSupport[i].setUseByDate(workingInformation[i].getUseByDate());
			}
			else
			{
				storageSupport[i].setUseByDate(workingInformation[i].getResultUseByDate());
			}
			storageSupport[i].setEnteringQty(workingInformation[i].getEnteringQty());
			storageSupport[i].setBundleEnteringQty(workingInformation[i].getBundleEnteringQty());
			storageSupport[i].setITF(workingInformation[i].getItf());
			storageSupport[i].setBundleITF(workingInformation[i].getBundleItf());
			
			if (SystemDefine.CASEPIECE_FLAG_CASE.equals(workingInformation[i].getWorkFormFlag()) 
					|| SystemDefine.CASEPIECE_FLAG_NOTHING.equals(workingInformation[i].getWorkFormFlag()))
			{	

				int caseQty ;
				int pieceQty ;
				if (workingInformation[i].getEnteringQty() > 0)
				{					
					 caseQty =workingInformation[i].getPlanEnableQty()/workingInformation[i].getEnteringQty();	
					 pieceQty = workingInformation[i].getPlanEnableQty()%workingInformation[i].getEnteringQty();
					 storageSupport[i].setPlanCaseQty(caseQty);
					 storageSupport[i].setPlanPieceQty(pieceQty);
					 
				}else
				{
					storageSupport[i].setPlanCaseQty(0);
					storageSupport[i].setPlanPieceQty(workingInformation[i].getPlanEnableQty());
					
				}
			}else
			{
				storageSupport[i].setPlanCaseQty(0);
				storageSupport[i].setPlanPieceQty(workingInformation[i].getPlanEnableQty());
			}
				
		}
		return storageSupport;
		
	}
	
}
//#CM570205
//end of class

