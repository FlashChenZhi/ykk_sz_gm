package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;
//#CM570364
/*
 * Created on 2004/09/24 Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is
 * subject to license terms.
 */

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570365
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR> 
 * The class to do data retrieval for Storage Plan list box Retrieval processing.<BR>
 * Receive Search condition with Parameter, and do Retrieval of the Storage Plan list.<BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStoragePlanListRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   Retrieval of stock Plan information is done by calling <CODE>find</CODE> Method.<BR>
 * <BR>
 *   <Search condition> MandatoryItem*<BR>
 *   <DIR>
 *      , Consignor Code*<BR>
 *      , Start Storage plan date<BR>
 *      , End Storage plan date<BR>
 *      , Item Code<BR>
 *      , Case , PieceFlag<BR>
 *      , Work Status<BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DNSTORAGEPLAN <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Display processing(<CODE>getEntities</CODE> Method)<BR>
 * <BR>
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
 *      , CaseLocation<BR>
 *      , PieceLocation<BR>
 *      , Packed qty per case<BR>
 *      , Packed qty per bundle<BR>
 *      , PlanCase qty<BR>
 *      , PlanPiece qty<BR>
 *      , Result Case qty<BR>
 *      , Result Piece qty<BR>
 *      , Work Status Description<BR>
 *      , CaseITF<BR>
 *	    , Bundle ITF<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/24</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:26 $
 * @author  $Author: suresh $
 */
public class SessionStoragePlanListRet extends SessionRet
{
	//#CM570366
	// Class fields --------------------------------------------------

	//#CM570367
	// Class variables -----------------------------------------------

	//#CM570368
	// Class method --------------------------------------------------
	//#CM570369
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:26 $");
	}

	//#CM570370
	// Constructors --------------------------------------------------
	//#CM570371
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStoragePlanListRet(Connection conn, StorageSupportParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM570372
	// Public methods ------------------------------------------------
	//#CM570373
	/**
	 * Return partial qty of Retrieval result of <CODE>DnStoragePlan</CODE>.<BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Consignor Code<BR>
	 *  , Consignor Name<BR>
	 *  , Storage plan date<BR>
	 *  , Item Code<BR>
	 *  , Item Name<BR>
	 *  , Case , PieceFlag Description<BR>
	 *  , CaseLocation<BR>
	 *  , PieceLocation<BR>
	 *  , Packed qty per case<BR>
	 *  , Packed qty per bundle<BR>
	 *  , PlanCase qty<BR>
	 *  , PlanPiece qty<BR>
	 *  , Result Case qty<BR>
	 *  , Result Piece qty<BR>
	 *  , Work Status Description<BR>
	 *  , CaseITF<BR>
	 *  , Bundle ITF<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnStoragePlan
	 */
	public Parameter[] getEntities()
	{
		StorageSupportParameter[] resultArray = null;
		StoragePlan[] storagePlan = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				storagePlan = (StoragePlan[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (StorageSupportParameter[]) convertToStorageSupportParams(storagePlan);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM570374
	// Package methods -----------------------------------------------

	//#CM570375
	// Protected methods ---------------------------------------------

	//#CM570376
	// Private methods -----------------------------------------------
	//#CM570377
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>StoragePlanFinder</code> as instance variable for retrieval.<BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter stParam) throws Exception
	{
		StoragePlanSearchKey sKey = new StoragePlanSearchKey();
		//#CM570378
		//Set Search condition
		//#CM570379
		//Consignor Code
		if ((stParam.getConsignorCode() != null) && !stParam.getConsignorCode().equals(""))
		{
			sKey.setConsignorCode(stParam.getConsignorCode());
		}
		//#CM570380
		//Start Storage plan date
		if ((stParam.getFromStoragePlanDate() != null) && !stParam.getFromStoragePlanDate().equals(""))
		{
			sKey.setPlanDate(stParam.getFromStoragePlanDate(), ">=");
		}
		//#CM570381
		//End Storage plan date
		if ((stParam.getToStoragePlanDate() != null) && !stParam.getToStoragePlanDate().equals(""))
		{
			sKey.setPlanDate(stParam.getToStoragePlanDate(), "<=");
		}
		//#CM570382
		//Item Code
		if ((stParam.getItemCode() != null) && !stParam.getItemCode().equals(""))
		{
			sKey.setItemCode(stParam.getItemCode());
		}
		//#CM570383
		//Case , PieceFlag
		if (stParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			sKey.setCasePieceFlag(StoragePlan.CASEPIECE_FLAG_NOTHING);
		}
		else if (stParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
		{
			sKey.setCasePieceFlag(StoragePlan.CASEPIECE_FLAG_CASE);
		}
		else if (stParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			sKey.setCasePieceFlag(StoragePlan.CASEPIECE_FLAG_PIECE);
		}
		else if (stParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_MIXED))
		{
			sKey.setCasePieceFlag(StoragePlan.CASEPIECE_FLAG_MIX);
		}
		else if (stParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM570384
			//do nothing
		}

		//#CM570385
		//Set Status flag
		if (!StringUtil.isBlank(stParam.getWorkStatus()))
		{

			if (stParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM570386
				//Stand by
				sKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
			}
			else if (stParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_STARTED))
			{
				//#CM570387
				//Start
				sKey.setStatusFlag(SystemDefine.STATUS_FLAG_START);
			}
			else if (stParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM570388
				//Working
				sKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
			}
			else if (stParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
			{
				//#CM570389
				//Partially Completed
				sKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETE_IN_PART);
			}
			else if (stParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM570390
				//Completed
				sKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
			}
			else if (stParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_ALL))
			{
				//#CM570391
				//do nothing All
				sKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "<>");
			}

		}

		//#CM570392
		// Set the order of acquisition. 
		//#CM570393
		//Consignor Code
		sKey.setConsignorCodeCollect("");
		//#CM570394
		//Consignor Name
		sKey.setConsignorNameCollect("");
		//#CM570395
		//Storage plan date
		sKey.setPlanDateCollect("");
		//#CM570396
		//Item Code
		sKey.setItemCodeCollect("");
		//#CM570397
		//Item name
		sKey.setItemName1Collect("");
		//#CM570398
		//Case/Piece flag
		sKey.setCasePieceFlagCollect("");
		//#CM570399
		//CaseLocation
		sKey.setCaseLocationCollect("");
		//#CM570400
		//PieceLocation
		sKey.setPieceLocationCollect("");
		//#CM570401
		//Packed qty per case
		sKey.setEnteringQtyCollect("");
		//#CM570402
		//Piece Packed qty
		sKey.setBundleEnteringQtyCollect("");
		//#CM570403
		//Plan Packed qty
		sKey.setPlanQtyCollect("SUM");
		//#CM570404
		//Results Packed qty
		sKey.setResultQtyCollect("SUM");
		//#CM570405
		//Status
		sKey.setStatusFlagCollect("");
		//#CM570406
		//CaseITF
		sKey.setItfCollect("");
		//#CM570407
		//Bundle ITF
		sKey.setBundleItfCollect("");

		//#CM570408
		//The purpose of this Add date is to read the newest Consignor Name from the same Consignor Code. 
		sKey.setRegistDateCollect("MAX");

		//#CM570409
		//Set the order of the group. 
		sKey.setConsignorCodeGroup(1);
		sKey.setConsignorNameGroup(2);
		sKey.setPlanDateGroup(3);
		sKey.setItemCodeGroup(4);
		sKey.setItemName1Group(5);
		sKey.setCasePieceFlagGroup(6);
		sKey.setCaseLocationGroup(7);
		sKey.setPieceLocationGroup(8);
		sKey.setEnteringQtyGroup(9);
		sKey.setBundleEnteringQtyGroup(10);
		sKey.setStatusFlagGroup(11);
		sKey.setItfGroup(12);
		sKey.setBundleItfGroup(13);

		//#CM570410
		//Set the order of sorting. 
		sKey.setPlanDateOrder(1, true);
		sKey.setItemCodeOrder(2, true);
		sKey.setCasePieceFlagOrder(3, true);
		sKey.setCaseLocationOrder(4, true);
		sKey.setPieceLocationOrder(5, true);

		wFinder = new StoragePlanFinder(wConn);
		wFinder.open();
		int count = wFinder.search(sKey);
		wLength = count;
		wCurrent = 0;

	}

	//#CM570411
	/**
	 * Convert the StoragePlan entity into <CODE>StorageSupportParameter</CODE>.  <BR>
	 * <BR>
	 * @param ety Entity[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private Parameter[] convertToStorageSupportParams(Entity[] ety)
	{
		StorageSupportParameter[] stParam = null;
		StoragePlan[] storagePlan = (StoragePlan[]) ety;
		if ((storagePlan != null) && (storagePlan.length != 0))
		{
			stParam = new StorageSupportParameter[storagePlan.length];
			String registDate = "1";
			for (int i = 0; i < storagePlan.length; i++)
			{

				stParam[i] = new StorageSupportParameter();

				//#CM570412
				//Set the newest Consignor Code matching with Add date when Consignor Name differs for Same Consignor Code.
				if ((i == 0) || (Long.parseLong(registDate) < Long.parseLong(getDateValue(storagePlan[i].getRegistDate()))))
				{
					registDate = getDateValue(storagePlan[i].getRegistDate());
					stParam[0].setConsignorCode(storagePlan[i].getConsignorCode()); //荷主コード
					stParam[0].setConsignorName(storagePlan[i].getConsignorName()); //荷主名
				}
				stParam[i].setStoragePlanDate(storagePlan[i].getPlanDate()); //入庫予定日
				stParam[i].setItemCode(storagePlan[i].getItemCode()); //商品コード
				stParam[i].setItemName(storagePlan[i].getItemName1()); //商品名
				//#CM570413
				//Flag
				stParam[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(storagePlan[i].getCasePieceFlag()));
				//#CM570414
				//CaseLocation
				stParam[i].setCaseLocation(storagePlan[i].getCaseLocation()); //入庫棚
				//#CM570415
				//PieceLocation
				stParam[i].setPieceLocation(storagePlan[i].getPieceLocation()); //入庫棚
				stParam[i].setEnteringQty(storagePlan[i].getEnteringQty()); //ケース入数
				stParam[i].setBundleEnteringQty(storagePlan[i].getBundleEnteringQty()); //ピース入数
				//#CM570416
				// Calculation of Plan qty
				//#CM570417
				// Case : 1 
				//#CM570418
				// Displays Plan separately for Case and Piece when Flag is Case.
				//#CM570419
				// Case : 2 
				//#CM570420
				// Displays Plan separately for Case and Piece when Flag is Unspecified.
				//#CM570421
				// Case : 3 
				//#CM570422
				// Displays Packed qty per case in 0 when there is Packed qty per case data and flag is Piece.  
				//#CM570423
				// Display Plan of All in Piece.  
				if (storagePlan[i].getPlanQty() > 0)
				{
					if ((storagePlan[i].getCasePieceFlag() != null) && !storagePlan[i].getCasePieceFlag().equals(""))
					{
						String s1 = storagePlan[i].getCasePieceFlag(); //区分
						if (s1.equals(StoragePlan.CASEPIECE_FLAG_CASE) || s1.equals(StoragePlan.CASEPIECE_FLAG_NOTHING) || s1.equals(StoragePlan.CASEPIECE_FLAG_MIX))
						{
							stParam[i].setPlanCaseQty(DisplayUtil.getCaseQty(storagePlan[i].getPlanQty(), storagePlan[i].getEnteringQty())); //予定ケース入数
							stParam[i].setPlanPieceQty(DisplayUtil.getPieceQty(storagePlan[i].getPlanQty(), storagePlan[i].getEnteringQty())); //予定ピース入数
						}
						else if (s1.equals(StoragePlan.CASEPIECE_FLAG_PIECE))
						{
							stParam[i].setPlanCaseQty(0); //予定ケース入数
							stParam[i].setPlanPieceQty(storagePlan[i].getPlanQty()); //予定ピース入数
						}
					}
				}
				//#CM570424
				// Calculation of Results qty 
				//#CM570425
				// Case : 1 
				//#CM570426
				// Displays Plan separately for Case and Piece when Flag is Case.
				//#CM570427
				// Case : 2 
				//#CM570428
				// Displays Plan separately for Case and Piece when Flag is Unspecified.
				//#CM570429
				// Case : 3 
				//#CM570430
				// Displays Packed qty per case in 0 when there is Packed qty per case data and flag is Piece.  
				//#CM570431
				// Display Results of All in Piece.  
				if (storagePlan[i].getResultQty() > 0)
				{
					if ((storagePlan[i].getCasePieceFlag() != null) && !storagePlan[i].getCasePieceFlag().equals(""))
					{
						String s1 = storagePlan[i].getCasePieceFlag(); //区分
						if (s1.equals(StoragePlan.CASEPIECE_FLAG_CASE) || s1.equals(StoragePlan.CASEPIECE_FLAG_NOTHING) || s1.equals(StoragePlan.CASEPIECE_FLAG_MIX))
						{
							stParam[i].setResultCaseQty(DisplayUtil.getCaseQty(storagePlan[i].getResultQty(), storagePlan[i].getEnteringQty())); //実績ケース入数
							stParam[i].setResultPieceQty(DisplayUtil.getPieceQty(storagePlan[i].getResultQty(), storagePlan[i].getEnteringQty())); //実績ピース入数
						}
						else if (s1.equals(StoragePlan.CASEPIECE_FLAG_PIECE))
						{
							stParam[i].setResultCaseQty(0); //実績ケース入数
							stParam[i].setResultPieceQty(storagePlan[i].getResultQty()); //実績ピース入数
						}
					}
				}
				stParam[i].setWorkStatusName(DisplayUtil.getWorkingStatusValue(storagePlan[i].getStatusFlag())); // 状態
				stParam[i].setITF(storagePlan[i].getItf()); //ケースITF
				stParam[i].setBundleITF(storagePlan[i].getBundleItf()); //ボールITF

			}
		}
		return stParam;
	}

	//#CM570432
	/**
	 * This Method converts the date into the character string. <BR>
	 * <BR>
	 * @param date Date Date
	 * @return Converted character string. 
	 */
	private String getDateValue(Date date)
	{
		String datNumS = null;

		if (date != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			datNumS = sdf.format(date).trim();
		}

		return datNumS;
	}

}
//#CM570433
//end of class
