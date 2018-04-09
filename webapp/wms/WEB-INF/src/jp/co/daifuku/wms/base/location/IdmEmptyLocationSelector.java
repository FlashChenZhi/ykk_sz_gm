package jp.co.daifuku.wms.base.location;

import java.sql.Connection;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM670703
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM670704
/**
 * <FONT COLOR="BLUE">
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono  <BR>
 * <BR>
 * The class to do Empty location retrieval of the mobile rack. <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Acquisition processing of Area  No.(<CODE>getAreaNo()</CODE>Method)  <BR><BR><DIR>
 *   Acquire Area  No. of the mobile rack in which the system is defined. <BR>
 *   Notify ScheduleException when pertinent data does not exist. <BR>
 * </DIR>
 * <BR>
 * 2.Object aisle Information acquisition preparation(<CODE>selectAisleNo()</CODE>Method) <BR><BR><DIR>
 *   Do the acquisition preparation of object Aisle No. of the movable rack from Location management Information(DMLOCATE) in order of the retrieval. <BR>
 * <BR>
 *   <Parameter> <BR><DIR>
 *     Area No. <BR>
 *   </DIR>
 *   <Return information> <BR><DIR>
 *     Aisle  No. array <BR></DIR>
 *   <Search condition> <BR><DIR>
 *     Area No. : Target only Area  No. returned by the Area  No. acquisition.  <BR></DIR>
 *   <Consolidating condition> <BR><DIR>
 *     Like Aisle No. <BR></DIR>
 *   <The acquisition order> <BR><DIR>
 *     Ascending order of Aisle No. <BR></DIR>
 * </DIR>
 * <BR>
 * 3.Condition check processing (<CODE>checkLocation()</CODE>Method) <BR><BR><DIR>
 *   <Parameter> <BR><DIR>
 *     Empty Location Information Instance(EmptyLocation) <BR>
 *   </DIR>
 *   <Return information> <BR><DIR>
 *     int    Status     : Return information <BR>
 *     <DIR>
 *       EmptyLocation.RET_OK Normal <BR>
 *       EmptyLocation.RET_ERR Condition Error <BR>
 *     </DIR>
 *   </DIR>
 *   <Content of processing> <BR><DIR>
 *     3-1. When Information which applies to the following condition exists from work Information(DNWORKINFO), <BR>
 *          it is assumed Condition Error.   <BR>
 *     <DIR>
 *       <Search condition> <BR><DIR>
 *          - Work flag is Storage work <BR>
 *          - Status flag is (Stand By - Started - Working - Partly Completed)  <BR>
 *          - Location No matches with Location No of Parameter. <BR>
 *       </DIR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 4.Empty location retrieval processing (<CODE>selectEmptyLocation()</CODE>Method) <BR><BR><DIR>
 *   Do Empty location retrieval in the mobile rack. <BR>
 *   <BR>
 *   <Parameter> <BR><DIR>
 *     None <BR>
 *   </DIR>
 *   <Return information> <BR><DIR>
 *     Empty Location Information Instance(EmptyLocation) format. <BR><DIR>
 *       String LocationNo : Retrieved Empty Location No. <BR>
 *       String BankNo     : Retrieved Bank No. <BR>
 *       String BayNo      : Retrieved Bay No. <BR>
 *       String LevelNo    : Retrieved Level No. <BR>
 *       int    Status     : Return information <BR>
 *       <DIR>
 *         EmptyLocation.RET_OK Normal <BR>
 *         EmptyLocation.RET_NOEMPTYLOCATION No empty location<BR>
 *       </DIR>
 *     </DIR>
 *   </DIR>
 * <BR>
 *   4-1. Acquire <CODE>getAreaNo() </CODE> of Area No. <BR>
 *   4-2. Object Aisle No. acquisition <CODE>selectAisleNo() </CODE> <BR>
 *   4-3. Do the retrieval preparation  of Empty location retrieval (Aisle specification) by using <CODE>EmptyLocationSelector.selectLocation() </CODE> Method. <BR>
 *   4-4. Acquire Empty Location No. by using Empty Location Information (Aisle specification) Processing <CODE>EmptyLocationSelector.nextEmptyLocation()</CODE> Method.<BR><DIR>
 *     (X)Do the retrieval processing again by next Aisle  No. in 5-3 when No empty location is notified in a pertinent aisle.  <BR></DIR>
 *   4-5. Check the condition by using <CODE>checkLocation() condition check </CODE> Method.  <BR><DIR>
 *     (X)Notify the Location Information Instance format and Empty Location No when the condition check is Normal.  <BR>
 *     (X)Process it from acquisition 4-5 and next Empty Location No when the condition check is abnormal.  <BR></DIR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/11</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:58:09 $
 * @author  $Author: suresh $
 */
public class IdmEmptyLocationSelector
{

	//#CM670705
	// Class variables -----------------------------------------------
	//#CM670706
	/**
	 * Empty location retrieval processing (Aisle specification)  Instance
	 */
	private EmptyLocationSelector wEmpLocSelector = null;

	//#CM670707
	/**
	 * Location InformationFinder
	 */
	private LocateHandler wLocateHandler = null;

	//#CM670708
	/**
	 * Location Information retrieval Key
	 */
	private LocateSearchKey wLocateKey = null;

	//#CM670709
	/**
	 * Order Location Information of aisle
	 */
	private Locate[] wLocOrderAisle = null;

	//#CM670710
	/**
	 * Work Information Handler
	 */
	private WorkingInformationHandler wWorkInfoHandler = null;

	//#CM670711
	/**
	 * Area Information retrieval Key
	 */
	private WorkingInformationSearchKey wWorkInfoKey = null;
	
	//#CM670712
	/**
	 * Area 
	 */
	private int wAreaNum = 0;

	//#CM670713
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM670714
	// Class method --------------------------------------------------
	//#CM670715
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:58:09 $");
	}

	//#CM670716
	// Constructors --------------------------------------------------
	//#CM670717
	/**
	 * Use this constructor when DB is retrieved and updated using this class. 
	 * @param conn Connection object with data base
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	public IdmEmptyLocationSelector(Connection conn) throws ReadWriteException, ScheduleException
	{
		wLocateHandler = new LocateHandler(conn);
		wLocateKey = new LocateSearchKey();
		wWorkInfoHandler = new WorkingInformationHandler(conn);
		wWorkInfoKey = new WorkingInformationSearchKey();
		wEmpLocSelector = new EmptyLocationSelector(conn);
		
		//#CM670718
		// Acquire the object aisle of Mobile Rack. 
		selectAisleNo(getAreaNo());
		
	}

	//#CM670719
	/**
	 * Use this constructor when DB is retrieved and it does not update using this class. 
	 */
	public IdmEmptyLocationSelector()
	{
	}

	//#CM670720
	/**
	 * Acquire Area  No. of Mobile Rack. 
	 * 
	 * @param None
	 * @return Mobile RackArea No.
	 */
	public String getAreaNo()
	{
		//#CM670721
		// Acquire Area  No. for Mobile Rack defined by WMSParam. 
		return (WmsParam.IDM_AREA_NO);
	}

	//#CM670722
	/**
	 * Acquire the object list of Aisle No. of Area.
	 * 
	 * @param pAreaNo Retrieved Area No.
	 * @param pAisleNo Retrieved Aisle  No.
	 * @return None
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void selectAisleNo(String pAreaNo) throws ReadWriteException
	{
		//#CM670723
		// Acquire the object list Aisle No. of Area.
		wLocateKey.KeyClear();
		wLocateKey.setAreaNo(pAreaNo);
		wLocateKey.setAisleNoCollect("DISTINCT");
		
		//#CM670724
		// Ascending order of Aisle No. acquisition.
		wLocateKey.setAisleNoOrder(1, true);
		
		wLocOrderAisle = (Locate[])wLocateHandler.find(wLocateKey);
		
		return;

	}

	//#CM670725
	/**
	 * Check the condition with retrieved Location No. 
	 * 
	 * @return pEmp Empty Location Information Instance Location:LocationNo, RetrunCode:Return information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public int checkLocation(EmptyLocation pEmp) throws ReadWriteException
	{
		//#CM670726
		// Clear work Information retrieval Key. 
		wWorkInfoKey.KeyClear();
		
		//#CM670727
		// Set Search condition.
		//#CM670728
		// Work flag is Storage
		wWorkInfoKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		//#CM670729
		// Status flag is (Stand By - Started - Working - Partly Completed) 
		String[] status = {WorkingInformation.STATUS_FLAG_UNSTART, WorkingInformation.STATUS_FLAG_START,
							WorkingInformation.STATUS_FLAG_NOWWORKING, WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART};
		wWorkInfoKey.setStatusFlag(status);
		//#CM670730
		// Location No matches with Location of Empty Location Information. 
		wWorkInfoKey.setLocationNo(pEmp.getLocation());
		
		//#CM670731
		// Request the number of objects. 
		if (wWorkInfoHandler.count(wWorkInfoKey) > 0)
		{
			//#CM670732
			// Because the Storage work exists for object Location
			//#CM670733
			// Returned by Condition Error.
			return EmptyLocation.RET_ERR;
		}		

		return EmptyLocation.RET_OK;

	}

	//#CM670734
	/**
	 * Do Empty location retrieval processing of Mobile Rack. 
	 * 
	 * @param None
	 * @return EmptyLocation Empty Location Information Instance Location:LocationNo, RetrunCode:Return information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public EmptyLocation selectEmptyLocation() throws ReadWriteException
	{
		EmptyLocation empLoc = new EmptyLocation();
		
		//#CM670735
		// Loop the processing of the object aisle. 
		for (; wAreaNum < wLocOrderAisle.length; wAreaNum++)
		{
			if (!wEmpLocSelector.isNext())
			{
				//#CM670736
				// Acquire object Area  and Empty Location Information of the aisle. 
				wEmpLocSelector.selectLocation(getAreaNo(), wLocOrderAisle[wAreaNum].getAisleNo());
			}
			
			//#CM670737
			// Check Empty Location of the object aisle. 
			while(wEmpLocSelector.isNext())
			{
				//#CM670738
				// Acquire Empty Location Information. 
				empLoc = wEmpLocSelector.getEmptyLocation();
				if (empLoc.getReturnCode() == EmptyLocation.RET_NOEMPTYLOCATION)
				{
					break;
				}

				//#CM670739
				// Check the condition with obtained LocationNo. 
				//#CM670740
				// Shift to the following Empty Location Information, except for condition check OK. 
				if (checkLocation(empLoc) != EmptyLocation.RET_OK)
				{
					continue;
				} 
				
				//#CM670741
				// Do the Normal end return. 
				return empLoc;
			}
			
		}
		
		//#CM670742
		// Empty Location Information is clear (No empty location). 
		empLoc.setLocation(null);
		empLoc.setBankNo(null);
		empLoc.setBayNo(null);
		empLoc.setLevelNo(null);
		empLoc.setReturnCode(EmptyLocation.RET_NOEMPTYLOCATION);
		
		return empLoc;

	}

}
