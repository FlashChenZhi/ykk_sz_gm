package jp.co.daifuku.wms.base.location;

import java.sql.Connection;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateReportFinder;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Locate;

//#CM670671
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM670672
/**
 * <FONT COLOR="BLUE">
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono  <BR>
 * <BR>
 * The class to retrieve an empty location by the specifying Bank  No. <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Acquisition processing of method of retrieving empty location (<CODE>getSearchType()</CODE>Method)  <BR><BR><DIR>
 *   Acquire empty location retrieval information from area administrative information (DMAREA) and set the retrieval order.  <BR>
 *   <Parameter> <BR><DIR>
 *     Area No. <BR>
 *   </DIR>
 *   <Return information> <BR><DIR>
 *     None. <BR>
 *   </DIR>
 *   <Content of processing> <BR><DIR>
 *   -- When Bank level processing, Ascending order of Bank, Level and Bay. <BR>
 *   -- When Aisle level processing, Ascending order of Level, Bay and Bank. <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Empty location retrieval (Aisle specification) Preparation processing (<CODE>selectLocation()</CODE>Method) <BR><DIR>
 *   Do Empty location retrieval in the mobile rack. <BR>
 *   <BR>
 *   <Parameter> <BR><DIR>
 *     Area No. <BR>
 *     Aisle No. <BR>
 *   </DIR>
 *   <Return information> <BR><DIR>
 *     None. <BR>
 *   </DIR>
 *   <Content of processing> <BR><DIR>
 *     Acquire Empty Location No from location administrative information (DMLOCATE).  <BR></DIR>
 *   <Search condition> <BR><DIR>
 *     Area No. : Area No. of Parameter <BR>
 *     Aisle No. : Aisle No. of Parameter <BR>
 *     Status flag : Only empty location <BR>
 *   </DIR>
 *   <The acquisition order> <BR><DIR>
 *     Edit it by <CODE>getSearchType() </CODE> processing of acquisition of the Empty location retrieval method. <BR></DIR>
 * </DIR>
 * <BR>
 * 3.Empty location information (Aisle specification) acquisition processing (<CODE>getEmptyLocation()</CODE>Method)  <BR><DIR>
 *   <Parameter> <BR><DIR>
 *    None.
 *   </DIR>
 *   <Return information> <BR><DIR>
 *     Notify empty location information instance (EmptyLocation) from the result of Preparation processing of <BR>
 *      Empty location retrieval (Aisle specification) (<CODE>selectLocation()</CODE>Method). <BR><DIR>
 *       String LocationNo : Retrieved Empty Location No. <BR>
 *       String BankNo     : Retrieved Bank No. <BR>
 *       String BayNo      : Retrieved Bay No. <BR>
 *       String LevelNo    : Retrieved Level No. <BR>
 *       int    Status     : Return information <BR>
 *       <DIR>
 *         EmptyLocation.RET_OK Normal <BR>
 *         EmptyLocation.RET_NOEMPTYLOCATIONNo empty location <BR>
 *       </DIR>
 *     </DIR>
 *   </DIR>
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
public class EmptyLocationSelector
{

	//#CM670673
	// Class variables -----------------------------------------------
	//#CM670674
	/**
	 * Location Information Finder
	 */
	private LocateReportFinder wLocRepFinder = null;

	//#CM670675
	/**
	 * Location Information retrieval Key
	 */
	private LocateSearchKey wLocateKey = null;

	//#CM670676
	/**
	 * Location Storage area
	 */
	private Locate[] wLocate = null;
	
	//#CM670677
	/**
	 * Area Information Handler
	 */
	private AreaHandler wAreaHandler = null;

	//#CM670678
	/**
	 * Area Information retrieval Key
	 */
	private AreaSearchKey wAreaKey = null;

	//#CM670679
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM670680
	// Class method --------------------------------------------------
	//#CM670681
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:58:09 $");
	}

	//#CM670682
	// Constructors --------------------------------------------------
	//#CM670683
	/**
	 * Use this constructor when DB is retrieved and updated using this class. 
	 * @param conn Connection object with data base
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	public EmptyLocationSelector(Connection conn) throws ReadWriteException, ScheduleException
	{
		wLocRepFinder = new LocateReportFinder(conn);
		wLocateKey = new LocateSearchKey();
		wAreaHandler = new AreaHandler(conn);
		wAreaKey = new AreaSearchKey();
	}

	//#CM670684
	/**
	 * Use this constructor when DB is retrieved and it does not update using this class. 
	 */
	public EmptyLocationSelector()
	{
	}

	//#CM670685
	/**
	 * Do Acquisition processing of method of retrieving empty location. <BR>
	 * Set the condition definition to retrieve empty Location Information of the mobile rack from Location Information on the condition of Parameter. <BR>
	 * Acquire empty location retrieval information from area administrative information (DMAREA) and set the retrieval order.  <BR>
	 * 
	 * @param pAreaNo Retrieved Area No.
	 * @return None.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void getSearchType(String pAreaNo) throws ReadWriteException
	{
		try
		{
			wAreaKey.KeyClear();
			//#CM670686
			// Set Area  No. of Parameter in Search condition. 
			wAreaKey.setAreaNo(pAreaNo);
			
			Area rArea = (Area)wAreaHandler.findPrimary(wAreaKey);

			//#CM670687
			// When Area Information does not exist, throws ReadWriteException. 
			if (rArea == null)
			{
				//#CM670688
				// 6023386 = Area  No. is not registered. (Specified Area  No.={0})
				RmiMsgLogClient.write("6023386" + wDelim + pAreaNo, this.getClass().getName());
				//#CM670689
				// Here, throws ReadWriteException. (Setting of the error message is unnecessary. )
				throw (new ReadWriteException());
			}

			//#CM670690
			// Acquire the Empty location retrieval method, and edit Search condition matched to the condition. 
			if (rArea.getVacantSearchType().equals(Area.Area_VACANTSEARCHTYPE_BANKHORIZONTAL))
			{
				//#CM670691
				// The bank level specification
				//#CM670692
				// Ascending order of bank, Level and Bay
				wLocateKey.setBankNoOrder(1, true);
				wLocateKey.setLevelNoOrder(2, true);
				wLocateKey.setBayNoOrder(3, true);
			}
			else if (rArea.getVacantSearchType().equals(Area.Area_VACANTSEARCHTYPE_AISLEHORIZONTAL))
			{
				//#CM670693
				// The aisle level specification
				//#CM670694
				// Ascending order of Level, Bay and Bank
				wLocateKey.setLevelNoOrder(1, true);
				wLocateKey.setBayNoOrder(2, true);
				wLocateKey.setBankNoOrder(3, true);
			}
			else
			{
				//#CM670695
				// 6023388 = An illegal value is specified for the Empty location retrieval method. (Specified Area  No.={0})
				RmiMsgLogClient.write("6023388" + wDelim + pAreaNo, this.getClass().getName());
				//#CM670696
				// Here, throws ReadWriteException. (Setting of the error message is unnecessary. )
				throw (new ReadWriteException());
			}

			return ;
		}
		catch (NoPrimaryException e)
		{
			//#CM670697
			// 6023387 = Registration of Area  No. repeated.(Specified Area  No.={0})
			RmiMsgLogClient.write("6023387" + wDelim + pAreaNo, this.getClass().getName());
			//#CM670698
			// Here, throws ReadWriteException. (Setting of the error message is unnecessary. )
			throw (new ReadWriteException());
		}

	}

	//#CM670699
	/**
	 * Acquire as a result of empty Location Information set. 
	 * 
	 * @param pAreaNo Retrieved Area No.
	 * @param pAisleNo Retrieved Aisle  No.
	 * @return None
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void selectLocation(String pAreaNo, int pAisleNo) throws ReadWriteException
	{
		wLocateKey.KeyClear();
		wLocateKey.setAreaNo(pAreaNo);
		wLocateKey.setAisleNo(pAisleNo);
		wLocateKey.setStatusFlag(Locate.Locate_StatusFlag_EMPTY);
		
		//#CM670700
		// Edit the acquisition order in getSearchType() Method. 
		getSearchType(pAreaNo);

		wLocRepFinder.search(wLocateKey);

	}
	
	//#CM670701
	/**
	 * Judge whether empty Location Information was able to be acquired. 
	 * 
	 * @return Presence of empty Location Information
	 */
	public boolean isNext() throws ReadWriteException
	{
		return wLocRepFinder.isNext();
		
	}

	//#CM670702
	/**
	 * Acquire empty Location Information from Location Information. 
	 * 
	 * @return EmptyLocation Empty Location Information Instance Location:LocationNo, RetrunCode:Return information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public EmptyLocation getEmptyLocation() throws ReadWriteException
	{
		EmptyLocation empLoc = new EmptyLocation();
		wLocate = (Locate[]) wLocRepFinder.getEntities(1);
		if (wLocate == null || wLocate.length == 0)
		{
			empLoc.setReturnCode(EmptyLocation.RET_NOEMPTYLOCATION);
			return empLoc;
		}

		empLoc.setLocation(wLocate[0].getLocationNo());
		empLoc.setBankNo(wLocate[0].getBankNo());
		empLoc.setBayNo(wLocate[0].getBayNo());
		empLoc.setLevelNo(wLocate[0].getLevelNo());
		empLoc.setReturnCode(EmptyLocation.RET_OK);

		return empLoc;

	}

}
