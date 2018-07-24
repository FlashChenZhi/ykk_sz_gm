package jp.co.daifuku.wms.retrieval.schedule;

//#CM725104
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.utility.WareNaviSystemManager;

//#CM725105
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to search and update data to generate picking plan data. <BR>
 * Use this to load, add, modify, or delete Picking Plan data. <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/20</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:20:02 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanOperator extends AbstractRetrievalSCH
{

	//#CM725106
	// Class fields --------------------------------------------------
	//#CM725107
	/**
	 * Entry status: Not added 
	 */
	public static final String STATUS_NG = "STATUS_NG";

	//#CM725108
	/**
	 * Entry status: Added after Cancel Allocation. 
	 */
	public static final String STATUS_MODIFY = "STATUS_MODIFY";

	//#CM725109
	/**
	 * Entry status: Added New
	 */
	public static final String STATUS_INSERT = "STATUS_INSERT";

	//#CM725110
	// Class variables -----------------------------------------------
	//#CM725111
	/**
	 * Entry status 
	 */
	private String wStatusFlag = "";

	//#CM725112
	/**
	 * Picking Plan Info handler 
	 */
	protected RetrievalPlanHandler wPlanHandler = null;

	//#CM725113
	/**
	 * Picking Plan Info search key 
	 */
	protected RetrievalPlanSearchKey wPlanKey = null;

	//#CM725114
	/**
	 * Picking Plan Info Update key 
	 */
	protected RetrievalPlanAlterKey wPlanAltKey = null;

	//#CM725115
	/**
	 * Sequence handler 
	 */
	protected SequenceHandler wSequenceHandler = null;

	//#CM725116
	/**
	 * Work Status Info handler 
	 */
	protected WorkingInformationHandler wWorkHandler = null;

	//#CM725117
	/**
	 * Work Status Info search key 
	 */
	protected WorkingInformationSearchKey wWorkKey = null;
	
	//#CM725118
	/**
	 * Search for the WareNaviSystem.
	 */
	protected WareNaviSystemManager wWareNaviManager = null;
	
	//#CM725119
	// Class method --------------------------------------------------
	//#CM725120
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:20:02 $");
	}

	//#CM725121
	// Constructors --------------------------------------------------
	//#CM725122
	/**
	 * Use this constructor to search or update DB using this class.  <BR>
	 * Generate an instance needed for searching through each DB and updating. <BR>
	 * @param conn Instance to maintain database connection. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public RetrievalPlanOperator(Connection conn) throws ReadWriteException, ScheduleException
	{
		wPlanHandler = new RetrievalPlanHandler(conn);
		wPlanKey = new RetrievalPlanSearchKey();
		wPlanAltKey = new RetrievalPlanAlterKey();
		wSequenceHandler = new SequenceHandler(conn);
		wWorkHandler = new WorkingInformationHandler(conn);
		wWorkKey = new WorkingInformationSearchKey();
		wWareNaviManager = new WareNaviSystemManager(conn);
	}

	//#CM725123
	/**
	 * Use this constructor not to search or update DB using this class. 
	 */
	public RetrievalPlanOperator()
	{
	}

	//#CM725124
	// Public methods ------------------------------------------------
	//#CM725125
	/**
	 * Allow this method to compute the work type of Plan Info to generate a Picking Plan Info. <BR>
	 * Return the Case/Piece division defined for the Picking Plan Info. <BR>
	 * Determine by following procedures. <BR>
	 * <BR>
	 * - If a value is input in only either one of Location options: None <BR>
	 * - If there are inputs from the both locations, or in the both locations 
	 * <DIR>
	 *   - When Case Qty >0 Piece Qty <0: Case. <BR>
	 *   - When Case Qty <0 Piece Qty >0: Piece. <BR>
	 *   - When Case Qty >0 Piece Qty >0: Mixed. <BR>
	 * </DIR>
	 * Throw ScheduleException if both Case Qty and Piece qty are 0 or smaller. <BR>
	 * <BR>
	 * <Parameter>
	 * <DIR>
	 *   - Planned total qty<BR>
	 *   - Packed Qty per Case<BR>
	 *   - Case Location<BR>
	 *   - Piece Location <BR>
	 * </DIR>
	 * @param param <CODE>RetrievalSupportParameter</CODE> that contains information to compute Case/Piece division 
	 * @return Case/Piece division
	 * @throws ScheduleException Announce it when unexpected exception occurs while executing the schedule process. 
	 */
	public String getCasePieceFlag(RetrievalSupportParameter param) throws ScheduleException
	{
		int planCaseQty = DisplayUtil.getCaseQty(param.getTotalPlanQty(), param.getEnteringQty());
		int planPieceQty = DisplayUtil.getPieceQty(param.getTotalPlanQty(), param.getEnteringQty());
		String caseLocation = param.getCaseLocation();
		String pieceLocation = param.getPieceLocation();

		if (param.getEnteringQty() <= 0 && planCaseQty > 0)
		{
			//#CM725126
			// 6004001=Invalid Data was found. 
			RmiMsgLogClient.write("6004001", this.getClass().getName());
			throw new ScheduleException("6004001");
		}

		if (planCaseQty <= 0 && planPieceQty <= 0)
		{
			//#CM725127
			// 6004001=Invalid Data was found. 
			RmiMsgLogClient.write("6004001", this.getClass().getName());
			throw new ScheduleException("6004001");
		}

		String resultFlag = "";

		//#CM725128
		// For data with input or blank in both Location options. 
		if ((!StringUtil.isBlank(caseLocation) && !StringUtil.isBlank(pieceLocation)) || (StringUtil.isBlank(caseLocation) && StringUtil.isBlank(pieceLocation)))
		{
			//#CM725129
			// When the input is only in Case Qty in the difference location: Case. 
			if (planCaseQty > 0 && planPieceQty == 0)
			{
				resultFlag = RetrievalSupportParameter.CASEPIECE_FLAG_CASE;
			}
			//#CM725130
			// When the input is only in Piece Qty in the difference location: Piece. 
			else if (planCaseQty == 0 && planPieceQty > 0)
			{
				resultFlag = RetrievalSupportParameter.CASEPIECE_FLAG_PIECE;
			}
			//#CM725131
			// When the input is made to the both in the difference location: Mixed. 
			else if (planCaseQty > 0 && planPieceQty > 0)
			{
				resultFlag = RetrievalSupportParameter.CASEPIECE_FLAG_MIXED;
			}

		}
		//#CM725132
		// If a value is input in only either one of Location options: None 
		else
		{
			resultFlag = RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING;
		}

		return resultFlag;

	}

	//#CM725133
	/**
	 * Add the entered or loaded Plan Info into the Picking Plan Info table. <BR>
	 * Check for presence of the same data in the DB, and execute process depending on the status of the Plan Info. <BR>
	 * If failed to add information which was tried to add to the DB, return null. <BR>
	 * Execute the following processes. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Check for presence of the same Picking Plan Info in the DB. Lock the instance if any and obtain it. <BR>
	 *     Use the matching conditions as follows. <BR>
	 *   <DIR>
	 *     - Planned date<BR>
	 *     - Consignor Code<BR>
	 *     - Item Code<BR>
	 *     - Location (locations corresponding to the respective divisions) <BR>
	 *     - Order No. (which corresponds to each division) <BR>
	 *     - Case/Piece division<BR>
	 *   <BR>
	 *     - For Mixed: Search for both Case and Piece respectively. <BR>
	 *     - For data with division None, obtain the Location and the Order No. of either one in which any value is input. Check for presence of the same data with Case or Piece. <BR>
	 *   </DIR>
	 *   2.For data, no same data existing in the DB, with status flag and create a new Picking Plan Info. <BR>
	 *   3.If the same data exists in DB and its status flag is Started, Working, or Partially Completed, the return null. <BR>
	 *   4.If the same data exists in DB and its status flag is Standby, cancel the allocation of the corresponding info and create a new Picking Plan Info. <BR>
	 *     Use the cancel method of RetrievalAllocateOperator to cancel the allocation. <BR>
	 *   5.If the same data exists in DB and its status flag is Completed of Deleted, generate a new Picking Plan Info. <BR>
	 * </DIR>
	 * <BR>
	 * <Parameter>
	 * <DIR>
	 *   - Planned Picking Date<BR>
	 *   - Consignor Code<BR>
	 *   - Consignor Name<BR>
	 *   - Customer Code<BR>
	 *   - Customer Name<BR>
	 *   - Ticket No. <BR>
	 *   - Ticket Line No. <BR>
	 *   - Item Code<BR>
	 *   - Item Name<BR>
	 *   - Planned Picking Qty (Planned total qty) <BR>
	 *   - Packed Qty per Case<BR>
	 *   - Packed qty per bundle<BR>
	 *   - Case ITF<BR>
	 *   - Bundle ITF<BR>
	 *   - Piece Item Picking Location <BR>
	 *   - Case Item Picking Location <BR>
	 *   - Piece Order No.<BR>
	 *   - Case Order No.<BR>
	 *   - Batch No. (Schedule No.) <BR>
	 *   - Worker Code<BR>
	 *   - Worker Name<BR>
	 *   - Terminal No.<BR>
	 *   - Entry type <BR>
	 * </DIR>
	 * <B> Note) Allow this method to add the Case/Piece division as Mixed unless None is input as a parameter of the division. <BR>
	 * 		In some case, depending on the actual allocation status after invoking the allocation class, update the Case Piece flag of the Plan Info again. <BR>
	 * 		Invoke the <code>updateCasePieceFlag</code> method!!!! <BR>
	 * </B>
	 * @param inputParam <CODE>RetrievalSupportParameter</CODE> that contains the information to generate a Picking Plan Info
	 * @param processName Process name 
	 * @return <CODE>RetrievalPlan</CODE> entity with the added content 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public RetrievalPlan createRetrievalPlan(RetrievalSupportParameter inputParam, String processName) throws ReadWriteException, ScheduleException
	{
		RetrievalPlan resultPlan = null;

		if (StringUtil.isBlank(inputParam.getCasePieceflg()))
		{
			inputParam.setCasePieceflg(getCasePieceFlag(inputParam));
		}
		resultPlan = insertRetrievalPlan(inputParam, processName);

		return resultPlan;
	}

	//#CM725134
	/**
	 * Search for the info corresponding to the input info through the Picking Plan Info table and return the search result. <BR>
	 * Allow this method to lock the corresponding information. <BR>
	 * <BR>
	 * <Parameter>
	 * <DIR>
	 *   - Case/Piece division<BR>
	 *   - Consignor Code<BR>
	 *   - Planned Picking Date<BR>
	 *   - Item Code<BR>
	 *   - Case Location<BR>
	 *   - Piece Location <BR>
	 *   - Case Order No.<BR>
	 *   - Piece Order No.<BR>
	 * </DIR>
	 * @param inputParam <CODE>RetrievalSupportParameter</CODE> that contains the information to generate a Picking Plan Info
	 * @param update_flag If the value is true, lock it. If false, disable to lock it. 
	 * @return <CODE>RetrievalPlan</CODE> Entity that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling. 
	 */
	public RetrievalPlan[] findRetrievalPlanForUpdate(RetrievalSupportParameter inputParam, boolean update_flag) throws ReadWriteException, ScheduleException
	{
		//#CM725135
		// Execute exclusive Process(For UPDATE). 
		return this.findRetrievalPlanProc(inputParam, true);
	}

	//#CM725136
	/**
	 * Search for the info corresponding to the input info through the Picking Plan Info table and return the search result. <BR>
	 * Allow this method not to lock the corresponding information. <BR>
	 * <BR>
	 * <Parameter>
	 * <DIR>
	 *   - Case/Piece division<BR>
	 *   - Consignor Code<BR>
	 *   - Planned Picking Date<BR>
	 *   - Item Code<BR>
	 *   - Case Location<BR>
	 *   - Piece Location <BR>
	 *   - Case Order No.<BR>
	 *   - Piece Order No.<BR>
	 * </DIR>
	 * @param inputParam <CODE>RetrievalSupportParameter</CODE> that contains the information to generate a Picking Plan Info
	 * @return <CODE>RetrievalPlan</CODE> Entity that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling. 
	 */
	public RetrievalPlan[] findRetrievalPlan(RetrievalSupportParameter inputParam) throws ReadWriteException, ScheduleException
	{
		//#CM725137
		// Disable For UPDATE. 
		return this.findRetrievalPlanProc(inputParam, false);
	}
	//#CM725138
	/**
	 * Search for the info corresponding to the input info through the Picking Plan Info table and return the search result. <BR>
	 * Determine based on the parameter whether to lock the corresponding information or not. <BR>
	 * <BR>
	 * <Parameter>
	 * <DIR>
	 *   - Case/Piece division<BR>
	 *   - Consignor Code<BR>
	 *   - Planned Picking Date<BR>
	 *   - Item Code<BR>
	 *   - Case Location<BR>
	 *   - Piece Location <BR>
	 *   - Case Order No.<BR>
	 *   - Piece Order No.<BR>
	 * </DIR>
	 * @param inputParam <CODE>RetrievalSupportParameter</CODE> that contains the information to generate a Picking Plan Info
	 * @param update_flag If the value is true, lock it. If false, disable to lock it. 
	 * @return <CODE>RetrievalPlan</CODE> Entity that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling. 
	 */
	public RetrievalPlan[] findRetrievalPlanProc(RetrievalSupportParameter inputParam, boolean update_flag) throws ReadWriteException, ScheduleException
	{
		//#CM725139
		// Obtain the Case/Piece division and set it for the parameter. 
		if (StringUtil.isBlank(inputParam.getCasePieceflg()))
		{
			inputParam.setCasePieceflg(getCasePieceFlag(inputParam));
		}

		//#CM725140
		// Set the search conditions.
		wPlanKey.KeyClear();
		wPlanKey.setConsignorCode(inputParam.getConsignorCode());
		wPlanKey.setPlanDate(inputParam.getRetrievalPlanDate());
		wPlanKey.setItemCode(inputParam.getItemCode());
		wPlanKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		if (!StringUtil.isBlank(inputParam.getRetrievalPlanUkey()))
		{
			wPlanKey.setRetrievalPlanUkey(inputParam.getRetrievalPlanUkey(), "!=");
		}

		boolean isSetLocation = false;
		String location = "";
		//#CM725141
		// Request Order No. 
		String orderNo = "";

		//#CM725142
		// Set the search conditions for each division. (Location, Order No., and Division) 
		//#CM725143
		// Case 
		if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM725144
			// Location 
			if (StringUtil.isBlank(inputParam.getCaseLocation()))
			{
				wPlanKey.setCaseLocation("", "", "(", "", "AND");
				wPlanKey.setPieceLocation("", "", "", ")", "AND");
				isSetLocation = true;
			}

			//#CM725145
			// Order No.
			if (!StringUtil.isBlank(inputParam.getCaseOrderNo()))
				wPlanKey.setCaseOrderNo(inputParam.getCaseOrderNo());
			else
				//#CM725146
				// Search the one without Order No. 
				wPlanKey.setCaseOrderNo("", "");

			//#CM725147
			// Case/Piece division
			String[] casePieceFlg = { RetrievalPlan.CASEPIECE_FLAG_CASE, RetrievalPlan.CASEPIECE_FLAG_MIX };
			wPlanKey.setCasePieceFlag(casePieceFlg);
		}
		//#CM725148
		// Piece 
		else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM725149
			// Location 
			if (StringUtil.isBlank(inputParam.getPieceLocation()))
			{
				wPlanKey.setCaseLocation("", "", "(", "", "AND");
				wPlanKey.setPieceLocation("", "", "", ")", "AND");
				isSetLocation = true;
			}

			//#CM725150
			// Order No.
			if (!StringUtil.isBlank(inputParam.getPieceOrderNo()))
				wPlanKey.setPieceOrderNo(inputParam.getPieceOrderNo());
			else
				//#CM725151
				// Search the one without Order No. 
				wPlanKey.setPieceOrderNo("", "");

			//#CM725152
			// Case/Piece division
			String[] casePieceFlg = { RetrievalPlan.CASEPIECE_FLAG_PIECE, RetrievalPlan.CASEPIECE_FLAG_MIX };
			wPlanKey.setCasePieceFlag(casePieceFlg);
		}
		//#CM725153
		// Mixed 
		else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_MIXED))
		{
			//#CM725154
			// ( (Case Order  AND Case Location  AND (Mixed OR Case))  OR (Piece Order  AND Piece Location  AND (Mixed OR Piece))  )

			//#CM725155
			// Order No.
			if (!StringUtil.isBlank(inputParam.getCaseOrderNo()))
				wPlanKey.setCaseOrderNo(inputParam.getCaseOrderNo(), "=", "((", "", "AND");
			else
				//#CM725156
				// Search the one without Order No. 
				wPlanKey.setCaseOrderNo("", "", "((", "", "AND");

			//#CM725157
			// Location 
			if (StringUtil.isBlank(inputParam.getCaseLocation()))
			{
				wPlanKey.setCaseLocation("", "");
				isSetLocation = true;
			}

			//#CM725158
			// Case/Piece division
			wPlanKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "OR");
			wPlanKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE, "=", "", "))", "OR");

			//#CM725159
			// Order No.
			if (!StringUtil.isBlank(inputParam.getPieceOrderNo()))
				wPlanKey.setPieceOrderNo(inputParam.getPieceOrderNo(), "=", "(", "", "AND");
			else
				//#CM725160
				// Search the one without Order No. 
				wPlanKey.setPieceOrderNo("", "", "(", "", "AND");

			//#CM725161
			// Location 
			if (StringUtil.isBlank(inputParam.getPieceLocation()))
			{
				wPlanKey.setPieceLocation("", "");
				isSetLocation = true;
			}

			//#CM725162
			// Case/Piece division
			wPlanKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "OR");
			wPlanKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE, "=", "", ")))", "AND");

		}
		//#CM725163
		// None 
		else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM725164
			// Request Location No. 
			if (!StringUtil.isBlank(inputParam.getCaseLocation()))
				location = inputParam.getCaseLocation();
			else
				location = inputParam.getPieceLocation();

			//#CM725165
			// Request Order No. 
			orderNo = "";
			if (!StringUtil.isBlank(inputParam.getCaseOrderNo()))
				orderNo = inputParam.getCaseOrderNo();
			else if (!StringUtil.isBlank(inputParam.getPieceOrderNo()))
				orderNo = inputParam.getPieceOrderNo();
			else

				//#CM725166
				// AND (Case Location  AND Piece Location) AND (Case Order  OR Piece Order) AND None 
				//#CM725167
				// Location 
				if (StringUtil.isBlank(location))
				{
					wPlanKey.setCaseLocation("", "", "(", "", "AND");
					wPlanKey.setPieceLocation("", "", "", ")", "AND");
					isSetLocation = true;
				}
			//#CM725168
			// Order No.
			if (StringUtil.isBlank(orderNo))
			{
				wPlanKey.setCaseOrderNo("", "", "(", "", "AND");
				wPlanKey.setPieceOrderNo("", "", "", ")", "AND");
			}
			else
			{
				wPlanKey.setCaseOrderNo(orderNo, "=", "(", "", "OR");
				wPlanKey.setPieceOrderNo(orderNo, "=", "", ")", "AND");
			}
			//#CM725169
			// None 
			wPlanKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING);
		}
		//#CM725170
		// Throw exception when division is not designated. 
		else
		{
			//#CM725171
			// 6004001=Invalid Data was found. 
			RmiMsgLogClient.write("6004001", this.getClass().getName());
			throw new ScheduleException("6004001");
		}

		//#CM725172
		// Search 
		int count = wPlanHandler.count(wPlanKey);

		if (count <= 0)
		{
			return null;
		}

		if (isSetLocation)
		{
			RetrievalPlan[] retrieval = null;
			//#CM725173
			// Obtain the instance. 
			if (update_flag)
				retrieval = (RetrievalPlan[]) wPlanHandler.findForUpdate(wPlanKey);
			else
				retrieval = (RetrievalPlan[]) wPlanHandler.find(wPlanKey);
			return retrieval;
		}
		//#CM725174
		// If Location is not set for the search conditions: 
		else
		{
			RetrievalPlan[] retrieval = null;
			if (update_flag)
				retrieval = (RetrievalPlan[]) wPlanHandler.findForUpdate(wPlanKey);
			else
				retrieval = (RetrievalPlan[]) wPlanHandler.find(wPlanKey);

			for (int i = 0; i < retrieval.length; i++)
			{

				if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
				{
					//#CM725175
					// For data with division Case, compare the values of the Case Location. 
					if (inputParam.getCaseLocation().equals(retrieval[i].getCaseLocation()))
						return retrieval;
				}
				else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
				{
					//#CM725176
					// For data with division Piece, compare the values of the Piece Location.
					if (inputParam.getPieceLocation().equals(retrieval[i].getPieceLocation()))
						return retrieval;
				}
				else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
				{
					//#CM725177
					// For data with division None, compare the values between Case location, Piece location, and DB. 
					if (location.equals(retrieval[i].getPieceLocation()))
						return retrieval;
					if (location.equals(retrieval[i].getCaseLocation()))
						return retrieval;
				}
				else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_MIXED))
				{
					//#CM725178
					// For data with division "Mixed", compare the values between Case Location and Piece Location.
					if (inputParam.getCaseLocation().equals(retrieval[i].getCaseLocation()) || inputParam.getPieceLocation().equals(retrieval[i].getPieceLocation()))
						return retrieval;
				}
			}
		}

		return null;
	}

	//#CM725179
	/**
	 * Search for the info corresponding to the input info through the Work Status Info table and return the search result. <BR>
	 * In such case, lock the corresponding information. <BR> 
	 * @param retrieval <CODE>RetrievalPlan</CODE> Entity in which search conditions are stored 
	 * @return <CODE>WorkingInformation</CODE> Entity that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public WorkingInformation[] findWorkingInfomation(RetrievalPlan retrieval) throws ReadWriteException
	{
		//#CM725180
		// Set the search conditions. 
		wWorkKey.KeyClear();
		wWorkKey.setPlanUkey(retrieval.getRetrievalPlanUkey());
		return (WorkingInformation[]) wWorkHandler.find(wWorkKey);
	}
	
	//#CM725181
	/**
	 * Return either of Added, Added after canceling allocation, or Not Added as for Picking Plan Info. <BR>
	 * @return Entry status 
	 */
	public String getStatusFlag()
	{
		return wStatusFlag;
	}

	//#CM725182
	/**
	 * Lock the target Inventory Info to update. <BR>
	 * Lock the Inventory Info of the Inventory status (Center Inventory). <BR>
	 * <DIR>
	 *    (search conditions) 
	 *    <UL>
	 *     <LI> Inventory status(Center Inventory) </LI>
	 *    </UL>
	 * </DIR>
	 * @param conn Database connection object
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public void lockStockData(Connection conn) throws ReadWriteException
	{
		StockSearchKey skey = new StockSearchKey();
		StockHandler shandler = new StockHandler(conn);

		skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		shandler.lock(skey);
	}
	
	//#CM725183
	/**
	 * Lock the Work Status Info to ensure the exclusivity.<BR>
	 * <DIR>
	 *    (search conditions) 
	 *    <UL>
	 *     <LI> Status division (Standby) </LI>
	 *     <LI>Work type (Picking) </LI>
	 *    </UL>
	 * </DIR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public void lockWorkingInfoStockData() throws ReadWriteException
	{
		wWorkKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		wWorkKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		wWorkHandler.lock(wWorkKey);
	}
	//#CM725184
	/**
	 * Lock the Picking Plan Info to ensure the exclusivity.<BR>
	 * <DIR>
	 *    (search conditions) 
	 *    <UL>
	 *     <LI> Schedule process flag (Not Processed) </LI>
	 *    </UL>
	 * </DIR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public void lockRetrievalPlan() throws ReadWriteException
	{
		//#CM725185
		// Set the search conditions. 
		wPlanKey.KeyClear();
		wPlanKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART);
		wPlanHandler.lock(wPlanKey);
	}
	//#CM725186
	// Package methods -----------------------------------------------

	//#CM725187
	// Protected methods ---------------------------------------------
	
	//#CM725188
	// Private methods -----------------------------------------------
	//#CM725189
	/**
	 * Generate a Picking Plan Info based on the input info.<BR>
	 * @param inputParam <CODE>RetrievalSupportParameter</CODE> that contains the information to generate a Picking Plan Info
	 * @param processName Process name 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling. 
	 */
	protected RetrievalPlan insertRetrievalPlan(RetrievalSupportParameter inputParam, String processName) throws ReadWriteException, ScheduleException
	{
		try
		{
			RetrievalPlan retrieval = new RetrievalPlan();

			String planUkey_seqno = wSequenceHandler.nextRetrievalPlanKey();

			//#CM725190
			// Execute the Submit/Add Process for the Picking Plan Info. 
			retrieval.setRetrievalPlanUkey(planUkey_seqno);
			//#CM725191
			// Status flag
			retrieval.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			//#CM725192
			// Planned Picking Date
			retrieval.setPlanDate(inputParam.getRetrievalPlanDate());
			//#CM725193
			// Consignor Code
			retrieval.setConsignorCode(inputParam.getConsignorCode());
			//#CM725194
			// Consignor Name
			retrieval.setConsignorName(inputParam.getConsignorName());
			//#CM725195
			// Customer Code
			retrieval.setCustomerCode(inputParam.getCustomerCode());
			//#CM725196
			// Customer Name
			retrieval.setCustomerName1(inputParam.getCustomerName());
			//#CM725197
			// Shipping Ticket No 
			retrieval.setShippingTicketNo(inputParam.getShippingTicketNo());
			//#CM725198
			// Shipping ticket line No. 
			retrieval.setShippingLineNo(inputParam.getShippingLineNo());
			//#CM725199
			// Item Code
			retrieval.setItemCode(inputParam.getItemCode());
			//#CM725200
			// Item Name
			retrieval.setItemName1(inputParam.getItemName());
			//#CM725201
			// Planned Picking Qty 
			retrieval.setPlanQty(inputParam.getTotalPlanQty());
			//#CM725202
			// Picking Result qty
			retrieval.setResultQty(0);
			//#CM725203
			// Picking Shortage Qty 
			retrieval.setShortageCnt(0);
			//#CM725204
			// Packed Qty per Case
			retrieval.setEnteringQty(inputParam.getEnteringQty());
			//#CM725205
			// Packed qty per bundle
			retrieval.setBundleEnteringQty(inputParam.getBundleEnteringQty());
			//#CM725206
			// Case/Piece division 
			if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				retrieval.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE);
			}
			else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				retrieval.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE);
			}
			else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				retrieval.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			}
			else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_MIXED))
			{
				retrieval.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX);
			}

			//#CM725207
			// Case ITF
			retrieval.setItf(inputParam.getITF());
			//#CM725208
			// Bundle ITF
			retrieval.setBundleItf(inputParam.getBundleITF());
			//#CM725209
			// Piece Item Picking Location 
			retrieval.setPieceLocation(inputParam.getPieceLocation());
			//#CM725210
			// Case Item Picking Location 
			retrieval.setCaseLocation(inputParam.getCaseLocation());
			//#CM725211
			// Order No. Piece 
			retrieval.setPieceOrderNo(inputParam.getPieceOrderNo());
			//#CM725212
			// Order No. Case 
			retrieval.setCaseOrderNo(inputParam.getCaseOrderNo());
			//#CM725213
			// Batch No. 
			retrieval.setBatchNo(inputParam.getBatchNo());
		    //#CM725214
		    // Host Aggregation key 
		    retrieval.setHostCollectBatchno("");
			//#CM725215
			// Schedule process flag 
		    if (wWareNaviManager.isStockPack())
		    {
				retrieval.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART);
		    }
		    else
		    {
				retrieval.setSchFlag(RetrievalPlan.SCH_FLAG_COMPLETION);
		    }
			//#CM725216
			// Worker Code
			retrieval.setWorkerCode(inputParam.getWorkerCode());
			//#CM725217
			// Worker Name
			retrieval.setWorkerName(inputParam.getWorkerName());
			//#CM725218
			// Terminal No. and RFT No. 
			retrieval.setTerminalNo(inputParam.getTerminalNumber());
			//#CM725219
			// Entry type 
			retrieval.setRegistKind(inputParam.getRegistKind());
			//#CM725220
			// Name of Add Process
			retrieval.setRegistPname(processName);
			//#CM725221
			// Last update process name
			retrieval.setLastUpdatePname(processName);

			//#CM725222
			// Add the Picking Plan Info. 
			wPlanHandler.create(retrieval);

			return retrieval;

		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}


} //end of class
