//#CM43814
//$Id: AbstractAsrsControlSCH.java,v 1.2 2006/10/30 01:04:35 suresh Exp $

//#CM43815
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ASInventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.InOutResultHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.entity.ASInventoryCheck;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.base.entity.OperationDisplay;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM43816
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * The abstraction class which does the schedule processing of the ASRS package. 
 * Mount the WmsScheduler interface, and mount processing necessary for mounting this interface. 
 * A common method is mounted by this class, and mounted by the class which succeeds to this class about individual behavior of the schedule processing. 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/02</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:04:35 $
 * @author  $Author: suresh $
 */
public abstract class AbstractAsrsControlSCH extends AbstractSCH
{
	//#CM43817
	//	Class variables -----------------------------------------------
	//#CM43818
	/**
	 * Error type: Input
	 */
	protected static final String ERROR_INPUT = "ERROR_INPUT";
	
	//#CM43819
	/**
	 * Class Name
	 */
	public static final String CLASS_NAME = "AbstractAsrsControlSCH";

	//#CM43820
	/**
	 * Error type
	 */
	private String wErrorType = null;
	
	//#CM43821
	// Class method --------------------------------------------------
	//#CM43822
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:04:35 $");
	}
	//#CM43823
	// Constructors --------------------------------------------------

	//#CM43824
	// Public methods ------------------------------------------------

	//#CM43825
	// Protected methods ---------------------------------------------
	//#CM43826
	/**
	 * Check whether the content of the worker code and the password is correct. <BR>
	 * Return true when the content is correct and return false when it is not correct. <BR>
	 * Acquire the result by using < CODE>getMessage() </ CODE > method when the return value is false. <BR>
	 * 
	 * @param  conn               Connection object with database
	 * @param  checkParam        Parameter class where content which does input check is included
	 * @return Worker code, True when content of password is correct and False when it is not correct
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean checkWorker(Connection conn, AsScheduleParameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM43827
		// Acquire Worker code and Password from parameter. 
		String workerCode = checkParam.getWorkerCode();
		String password = checkParam.getPassword();

		return correctWorker(conn, workerCode, password);

	}

	//#CM43828
	/**
	 * Return the value of each reason when it filters and it is not possible to display it in the area. <BR>
	 * There is no object data:AsScheduleParameter[0]<BR>
	 * The maximum display number is exceeded:null<BR>
	 * <BR>
	 * <U>Use the canLowerDisplay method of the AbstractSCH class previously when you use this method. </U>
	 * 
	 * @return Depending return value of each reason
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected AsScheduleParameter[] returnNoDisplayParameter() throws ScheduleException, ReadWriteException
	{
		if (getDisplayNumber() <= 0)
		{
			return new AsScheduleParameter[0];
		}

		if (getDisplayNumber() > WmsParam.MAX_NUMBER_OF_DISP)
		{
			return null;
		}

		doScheduleExceptionHandling(CLASS_NAME);
		return null;

	}

	//#CM43829
	/** 
	 * Do the input check for an empty palette for the stock. <BR>
	 * Return true when both Consignor Code-Item Code is not a code for an empty palette. <BR>
	 * The condition judged to be input NG is as follows. <BR>
	 * <BR>
	 * 1.When neither Consignor for empty Pare Item Code is nor a commodity for empty Pare, Consignor Code <BR>
	 * 2.When neither the commodity for empty Pare Consignor Code is nor Consignor for empty Pare, Item Code<BR>
	 * 3.When it is specified specification not is in Case/Piece flag<BR>
	 * 4.The input of Storage Case qty (Or, Stock case qty) is improper. <BR>
	 * 5.Stock Qty per case (Or, it is numerical of the stock peace insertion) input is improper. <BR>
	 * 6.Stock Qty per bundle (Or, stock it Qty per bundle) input is improper. <BR>
	 * 7.The input of Expiry date is improper. <BR>
	 * <BR>
	 * @param  pConsignorCode Consignor Code<BR>
	 * @param  pItemCode Item code<BR>
	 * @param  pCasePiece Case/Piece flag<BR>
	 * @param  pUseBydate Expiry date<BR>
	 * @param  pCaseqty Case qty<BR>
	 * @param  pPieceqty Piece qty<BR>
	 * @param  pCaseEntQty Qty per case<BR>
	 * @param  pBundleEntQty Qty per bundle<BR>
	 * @return True when processing is normal. False when schedule processing fails or it is not possible to schedule it
	 */
	protected boolean isCorrectEmptyPB(
		String pConsignorCode,
		String pItemCode,
		String pCasePiece,
		String pUseBydate,
		int pCaseqty,
		int pPieceqty,
		int pCaseEntQty,
		int pBundleEntQty)
	{
		wErrorType = null;
		String emppbConsignCode = WmsParam.EMPTYPB_CONSIGNORCODE;
		String emppbItemCode = AsrsParam.EMPTYPB_ITEMKEY;

		if (!pConsignorCode.equals(emppbConsignCode) && !pItemCode.equals(emppbItemCode))
		{
			return true;
		}
		
		if (pConsignorCode.equals(emppbConsignCode) && !pItemCode.equals(emppbItemCode))
		{
			//#CM43830
			// 6023431=Specify "{0}" Item Code for an empty palette. 
			wMessage = "6023431" + wDelim + emppbItemCode;
			return false;
		}
		else if (!pConsignorCode.equals(emppbConsignCode) && pItemCode.equals(emppbItemCode))
		{
			//#CM43831
			// 6023432=Specify "{0}" Consignor Code for an empty palette. 
			wMessage = "6023432" + wDelim + emppbConsignCode;
			return false;
		}
		else
		{
			if (!pCasePiece.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM43832
				// 6023430=Specify "There is no specification" for Case/Piece flag for an empty palette. 
				wMessage = "6023430";
				return false;
			}

			if (pCaseEntQty > 0)
			{
				//#CM43833
				// 6023481=Qty per case cannot be specified for a number of stocks of empty palettes. 
				wMessage = "6023481";
				return false;
			}
		
			if (pCaseqty > 0)
			{
				wErrorType = ERROR_INPUT;
				//#CM43834
				// 6023427=Case qty cannot be specified for a number of stocks of empty palettes. Input Piece qty. 
				wMessage = "6023427";
				return false;
			}

			if (pBundleEntQty > 0)
			{
				//#CM43835
				// 6023482=Qty per bundle cannot be specified for a number of stocks of empty palettes. 
				wMessage = "6023482";
				return false;
			}

			if (pPieceqty == 0)
			{
				wErrorType = ERROR_INPUT;
				//#CM43836
				// 6023130=Input the value of one or more to Storage Piece qty. 
				wMessage = "6023130";
				return false;
			}

			if (!StringUtil.isBlank(pUseBydate))
			{
				wErrorType = ERROR_INPUT;
				//#CM43837
				// 6023429=Expiry date cannot be input for an empty palette. 
				wMessage = "6023429";
				return false;
			}
		}

		return true;
	}
	//#CM43838
	/**
	 * Acquire Error type. 
	 * @return Error type
	 */
	protected String getErrorType()
	{
		return wErrorType;
	}
	
	//#CM43839
	/**
	 * Check whether neither Consignor Code for an abnormal shelf nor Item Code are used. <BR>
	 * The condition judged to be input NG is as follows. <BR>
	 * <BR>
	 * 1.When Consignor Code is for an abnormal shelf<BR>
	 * 2.When Item Code is for an abnormal shelf<BR>
	 * 
	 * @param pConsignorCode Consignor Code
	 * @param pItemCode Item Code
	 * @return For the code of a commodity or an abnormal shelf
	 */
	protected boolean checkIrregularCode (String pConsignorCode, String pItemCode)
	{
		if (WmsParam.IRREGULAR_CONSIGNORCODE.equals(pConsignorCode))
		{
			//#CM43840
			// 6023460={0}Consignor Code for an abnormal shelf cannot be used. 
			wMessage = "6023460" + wDelim + pConsignorCode;
			return false;
		}
		if (AsrsParam.IRREGULAR_ITEMKEY.equals(pItemCode))
		{
			//#CM43841
			// 6023434={0}Item Code for an abnormal shelf cannot be used. 
			wMessage = "6023434" + wDelim + pItemCode;
			return false;
		}
		return true;
	}

	//#CM43842
	/**
	 * Check the input value. <BR>
	 * Return the check result. <BR>
	 * The content can be acquired by using <CODE>getMessage() </CODE> method.<BR>
	 * Process it in this method as follows. <BR>
	 * <BR>
	 * 1.The value within the range of specification must be input about Case/Piece flag. <BR>
	 * <BR>
	 * 2.Case/Piece flag : when there is no specification. <BR>
	 *   <DIR>
	 *   Qty per case must be input when Case qty is input. <BR>
	 *   The value of one or more must be input to Storage Case qty or Storage Piece qty. <BR>
	 *   </DIR>
	 * <BR>
	 * 3.When Case/Piece flag is Case<BR>
	 *   <DIR>
	 *   Qty per case must be input. <BR>
	 *   The value of one or more must be input to Storage Case qty. <BR>
	 *   </DIR>
	 * <BR>
	 * 4.When Case/Piece flag is Piece<BR>
	 *   <DIR>
	 *   Storage Case qty cannot be input. <BR>
	 *   The value of one or more must be input to Storage Piece qty. <BR>
	 *   </DIR>
	 * <BR>
	 * @param  casepieceflag      Case/Piece flag
	 * @param  enteringqty        Qty per case
	 * @param  caseqty            Storage Case qty
	 * @param  pieceqty           Storage Piece qty
	 * @return True when processing is normal. False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean storageInputCheck(String casepieceflag, int enteringqty, int caseqty, int pieceqty)
		throws ReadWriteException
	{
		//#CM43843
		//	Case/Piece flag check
		if (!(casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING)
			|| casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE)
			|| casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE)
			|| casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_ALL)))
		{
			//#CM43844
			// 6023145 = Input the value within the range of specification to Case/Piece flag.
			wMessage = "6023145";
			return false;
		}

		//#CM43845
		// Case/Piece flag : when there is no specification. 
		if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM43846
			// Qty per case is one or more.
			if (enteringqty > 0)
			{
				//#CM43847
				// When Storage Case qty and Storage Piece qty are 0
				if (caseqty == 0 && pieceqty == 0)
				{
					//#CM43848
					// 6023198 = Input the value of one or more to Storage Case qty or Storage Piece qty. 
					wMessage = "6023198";
					return false;
				}
			}
			else
			{
				//#CM43849
				// Storage Case qty is one or more. 
				if (caseqty > 0)
				{
					//#CM43850
					// 6023019 = Input the value of one or more to Qty per case.
					wMessage = "6023019";
					return false;
				}
				//#CM43851
				// When Storage Case qty and Storage Piece qty are 0
				else if (caseqty == 0 && pieceqty == 0)
				{
					//#CM43852
					// 6023198 = Input the value of one or more to Storage Case qty or Storage Piece qty. 
					wMessage = "6023198";
					return false;
				}
			}
		}
		//#CM43853
		// When Case/Piece flag is Case
		else if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM43854
			// Qty per case is one or more. 
			if (enteringqty > 0)
			{
				//#CM43855
				// When Storage Case qty is 0
				if (caseqty == 0)
				{
					//#CM43856
					// 6023129 = Input the value of one or more to Storage Case qty.
					wMessage = "6023129";
					return false;
				}
			}
			else
			{
				//#CM43857
				// 6023019 = Input the value of one or more to Qty per case.
				wMessage = "6023019";
				return false;
			}
		}
		//#CM43858
		// When Case/Piece flag is Piece
		else if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM43859
			// Storage Case qty is one or more.
			if (caseqty > 0)
			{
				//#CM43860
				// 6023285 = When Case/Piece flag is Piece, Storage Case qty cannot be input.
				wMessage = "6023285";
				return false;
			}

			//#CM43861
			// When Storage Piece qty is 0
			if (pieceqty == 0)
			{
				//#CM43862
				// 6023130 = Input the value of one or more to Storage Piece qty. 
				wMessage = "6023130";
				return false;
			}

		}
		//#CM43863
		// When Case/Piece flag is ALL
		else if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_ALL))
		{

		}

		return true;

	}

	//#CM43864
	/**
	 * Do the input check at the schedule going out warehouse. <BR>
	 * Return the check result. <BR>
	 * The content can be acquired by using <CODE>getMessage() </CODE> method.<BR>
	 * Process it in this method as follows. <BR>
	 * <BR>
	 * 1.Case/Piece flag : when there is no case or unspecified. <BR>
	 *   <DIR>
	 *   Case qty must not be input when Qty per case is 0. <BR>
	 *   Piece qty must be input when Qty per case is 0 and one or more must be input. <BR>
	 *   When Qty per case is one or more, Case qty or Piece qty must be input. <BR>
	 *   </DIR>
	 * <BR>
	 * 4.When Case/Piece flag is peace <BR>
	 *   <DIR>
	 *   The value of one or more must not be input to Retrieval Case qty. <BR>
	 *   The value of one or more must be input to Retrieval Piece qty. <BR>
	 *   </DIR>
	 * <BR>
	 * @param  pCasePieceFlag      Case/Piece flag
	 * @param  pEnteringQty        Qty per case
	 * @param  pCaseQty            Retrieval Case qty
	 * @param  pPieceQty           Retrieval Piece qty
	 * @param  pRowNo              Line No.
	 * @return True when there is no incompleteness in input, false when there is incompleteness.
	 */
	protected boolean stockRetrievalInputCheck(String pCasePieceFlag, int pEnteringQty, int pCaseQty, int pPieceQty, int pRowNo)
	{
		//#CM43865
		// Case/Piece flag : when there is no case or unspecified.
		if (pCasePieceFlag.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING)
		|| pCasePieceFlag.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			if (pEnteringQty == 0)
			{
				if (pCaseQty > 0)
				{
					//#CM43866
					// 6023299=No. {0} When Qty per case is 0, Retrieval Case qty cannot be input. 
					wMessage = "6023299" + wDelim + pRowNo;
					return false;
				}
				else if (pPieceQty == 0)
				{
					//#CM43867
					// 6023273=No.{0} {1}
					//#CM43868
					// 6023336 = Input the value of one or more to Retrieval Piece qty. 
					wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023336");
					return false;
				}
			}
			else
			{
				//#CM43869
				// When Retrieval Case qty and Retrieval Piece qty are 0
				if (pCaseQty == 0 && pPieceQty == 0)
				{
					//#CM43870
					// 6023334 = Input the value of one or more to Retrieval Case qty or Retrieval Piece qty. 
					wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023334");
					return false;
				}
			}
		}
		//#CM43871
		// When Case/Piece flag is Piece
		else if (pCasePieceFlag.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM43872
			// Retrieval Case qty is one or more. 
			if (pCaseQty > 0)
			{
				//#CM43873
				// 6023286 = When Case/Piece flag is Piece and Retrieval Case qty cannot be input. 
				wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023286");
				return false;
			}

			//#CM43874
			// When Retrieval Piece qty is 0
			if (pPieceQty == 0)
			{
				//#CM43875
				// 6023336 = Input the value of one or more to Retrieval Piece qty. 
				wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023336");
				return false;
			}
		}

		return true;

	}

	//#CM43876
	/**
	 * Check the input value. <BR>
	 * Return the check result. <BR>
	 * The content can be acquired by using <CODE>getMessage() </CODE> method.<BR>
	 * Process it in this method as follows. <BR>
	 * <BR>
	 * 1.The value within the range of specification must be input about Case/Piece flag.<BR>
	 * <BR>
	 * 2.Case/Piece flag : when there is no specification. <BR>
	 *   <DIR>
	 *   Qty per case must be input when Case qty is input. <BR>
	 *   The value of one or more must be input to Storage Case qty or Storage Piece qty. <BR>
	 *   </DIR>
	 * <BR>
	 * 3.When Case/Piece flag is Case<BR>
	 *   <DIR>
	 *   Qty per case must be input. <BR>
	 *   The value of one or more must be input to Storage Case qty. <BR>
	 *   </DIR>
	 * <BR>
	 * 4.When Case/Piece flag is Piece <BR>
	 *   <DIR>
	 *   Storage Case qty cannot be input. <BR>
	 *   The value of one or more must be input to Storage Piece qty. <BR>
	 *   </DIR>
	 * <BR>
	 * @param  casepieceflag      Case/Piece flag
	 * @param  enteringqty        Qty per case
	 * @param  caseqty            Case qty
	 * @param  pieceqty           Piece qty
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean stockInputCheck(String casepieceflag, int enteringqty, int caseqty, int pieceqty)
		throws ReadWriteException
	{
		//#CM43877
		//	Case/Piece flag check
		if (!(casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING)
			|| casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE)
			|| casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE)
			|| casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_ALL)))
		{
			//#CM43878
			// 6023145 =	Input the value within the range of specification to Case/Piece flag. 
			wMessage = "6023145";
			return false;
		}

		//#CM43879
		// Case/Piece flag : when there is no specification. 
		if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM43880
			// Qty per case is one or more. 
			if (enteringqty > 0)
			{
				//#CM43881
				// When Storage Case qty and Storage Piece qty are 0
				if (caseqty == 0 && pieceqty == 0)
				{
					//#CM43882
					// 6023177 = Input the value of one or more to Stock case qty or Stock piece qty.
					wMessage = "6023177";
					return false;
				}
			}
			else
			{
				//#CM43883
				// Storage Case qty is one or more. 
				if (caseqty > 0)
				{
					//#CM43884
					// 6023019 = Input the value of one or more to Qty per case. 
					wMessage = "6023019";
					return false;
				}
				//#CM43885
				// When Storage Case qty and Storage Piece qty are 0
				else if (caseqty == 0 && pieceqty == 0)
				{
					//#CM43886
					// 6023177 = Input the value of one or more to Stock case qty or Stock piece qty. 
					wMessage = "6023177";
					return false;
				}
			}
		}
		//#CM43887
		// When Case/Piece flag is Case
		else if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM43888
			// Qty per case is one or more. 
			if (enteringqty > 0)
			{
				//#CM43889
				// When Storage Case qty is 0
				if (caseqty == 0)
				{
					//#CM43890
					// 6023282 = Input the value of one or more to Stock case qty. 
					wMessage = "6023282";
					return false;
				}
			}
			else
			{
				//#CM43891
				// 6023019 = Input the value of one or more to Qty per case. 
				wMessage = "6023019";
				return false;
			}
		}
		//#CM43892
		// When Case/Piece flag is Piece
		else if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM43893
			// Storage Case qty is one or more. 
			if (caseqty > 0)
			{
				//#CM43894
				// 6023287 = When Case/Piece flag is Piece and Stock case qty cannot be input. 
				wMessage = "6023287";
				return false;
			}

			//#CM43895
			// When Storage Piece qty is 0
			if (pieceqty == 0)
			{
				//#CM43896
				// 6023283 = Input the value of one or more to Stock piece qty. 
				wMessage = "6023283";
				return false;
			}

		}
		//#CM43897
		// When Case/Piece flag is ALL
		else if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_ALL))
		{

		}

		//#CM43898
		// Overflow check
		long inputqty = (long)caseqty * (long)enteringqty + pieceqty;
		if (inputqty > WmsParam.MAX_STOCK_QTY)
		{
			//#CM43899
			// 6023058 = {0}Input the value below {1}. Check it by the quantity of stock (total of the rows). 
			wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0378") + wDelim + MAX_STOCK_QTY_DISP;
			return false;
		}

		return true;

	}

	//#CM43900
	/**
	 * Register results transmission information table (DNHOSTSEND). <BR> 
	 * <BR>     
	 * Register results transmission information based on the content of received parameter. <BR>
	 * The work report flag has been transmitted when work Flag is a maintenance increase, and a maintenance decrease, and, besides, it is assumed the untransmission. <BR>
	 * <BR>
	 * @param conn        Instance to maintain connection with data base. 
	 * @param param       Instance of AsScheduleParameter class with content input from screen. 
	 * @param stockid     Stock ID
	 * @param workercode  Worker code
	 * @param workername  Worker name
	 * @param sysdate     Work day(system definition date)
	 * @param terminalno  Terminal No.
	 * @param jobtype     Work Flag
	 * @param processname Processing name
	 * @param batchno     Batch No.
	 * @param inputqty	   Actual work qty 
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean createHostsend(
		Connection conn,
		AsScheduleParameter param,
		String stockid,
		String workercode,
		String workername,
		String sysdate,
		String terminalno,
		String jobtype,
		String processname,
		String batchno,
		int inputqty)
		throws ReadWriteException
	{
		try
		{
			HostSendHandler hostsendHandler = new HostSendHandler(conn);
			HostSend hostsend = new HostSend();

			//#CM43901
			// Acquire each registered one mind key. 
			SequenceHandler sequence = new SequenceHandler(conn);

			String jobno = sequence.nextJobNo();
			hostsend.setWorkDate(sysdate);
			hostsend.setJobNo(jobno);
			hostsend.setJobType(jobtype);
			hostsend.setCollectJobNo(jobno);
			hostsend.setStatusFlag(HostSend.STATUS_FLAG_COMPLETION);
			hostsend.setBeginningFlag(HostSend.BEGINNING_FLAG_STARTED);
			hostsend.setPlanUkey("");
			hostsend.setStockId(stockid);
			hostsend.setAreaNo(param.getAreaNo());
			hostsend.setLocationNo(param.getLocationNo());
			hostsend.setPlanDate("");
			hostsend.setConsignorCode(param.getConsignorCode());
			hostsend.setConsignorName(param.getConsignorName());
			hostsend.setSupplierCode("");
			hostsend.setSupplierName1("");
			hostsend.setInstockTicketNo("");
			hostsend.setInstockLineNo(0);
			hostsend.setCustomerCode(param.getCustomerCode());
			hostsend.setCustomerName1(param.getCustomerName());
			hostsend.setShippingTicketNo("");
			hostsend.setShippingLineNo(0);
			hostsend.setItemCode(param.getItemCode());
			hostsend.setItemName1(param.getItemName());
			hostsend.setHostPlanQty(0);
			hostsend.setPlanQty(0);
			hostsend.setPlanEnableQty(0);
			hostsend.setResultQty(inputqty);
			hostsend.setShortageCnt(0);
			hostsend.setPendingQty(0);
			hostsend.setEnteringQty(param.getEnteringQty());
			hostsend.setBundleEnteringQty(param.getBundleEnteringQty());
			hostsend.setCasePieceFlag(param.getCasePieceFlag());
			hostsend.setWorkFormFlag(param.getCasePieceFlag());
			hostsend.setItf(param.getITF());
			hostsend.setBundleItf(param.getBundleITF());
			hostsend.setTcdcFlag("0");
			hostsend.setUseByDate(param.getUseByDate());
			hostsend.setLotNo("");
			hostsend.setPlanInformation("");
			hostsend.setOrderNo("");
			hostsend.setOrderingDate("");
			hostsend.setResultUseByDate(param.getUseByDate());
			hostsend.setResultLotNo("");
			hostsend.setResultLocationNo(param.getLocationNo());
			//#CM43902
			// When work Flag is a maintenance increase, and a maintenance decrease, it has transmitted. 
			if((jobtype.equals(SystemDefine.JOB_TYPE_MAINTENANCE_PLUS))
			|| (jobtype.equals(SystemDefine.JOB_TYPE_MAINTENANCE_MINUS)))
			{
				//#CM43903
				// It has transmitted. 
				hostsend.setReportFlag(HostSend.REPORT_FLAG_SENT);
			}
			else
			{
				//#CM43904
				// Untransmission
				hostsend.setReportFlag(HostSend.REPORT_FLAG_NOT_SENT);
			}
			hostsend.setBatchNo(batchno);
			hostsend.setSystemDiscKey(HostSend.SYSTEM_DISC_KEY_ASRS);
			hostsend.setWorkerCode(workercode);
			hostsend.setWorkerName(workername);
			hostsend.setTerminalNo(terminalno);
			hostsend.setPlanRegistDate(null);
			hostsend.setRegistDate(new Date());
			hostsend.setRegistPname(processname);
			hostsend.setLastUpdateDate(new Date());
			hostsend.setLastUpdatePname(processname);

			//#CM43905
			// Registration of data
			hostsendHandler.create(hostsend);

			return true;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM43906
	/**
	 * Register work information table (DNWORKINFO). <BR> 
	 * <BR>     
	 * Register work information based on the content of received parameter. <BR>
	 * <BR>
	 * @param conn        Instance to maintain connection with data base. 
	 * @param param       Instance of AsScheduleParameter class with content input from screen. 
	 * @param stock	   Stock information instance
	 * @param stockid     Stock ID
	 * @param workercode  Worker code
	 * @param workername  Worker name
	 * @param terminalno  Terminal No.
	 * @param jobtype     Work Flag
	 * @param processname Processing name
	 * @param batchno     Batch No.
	 * @param planqty     Host plan qty
	 * @param inputqty	   Actual work qty 
	 * @param locationno  Location No..
	 * @param jobno       Work No.
	 * @param customercode Shipper code
	 * @param customername Shipper name
	 * @param carrykey     Transportation key
	 * @param areano		Area No.
	 * @param planukey     Plan unique key
	 * @param plandate     Work date
	 * @param planregdate  Plan registration date
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean createWorkinfo(
		Connection conn,
		AsScheduleParameter param,
		Stock stock,
		String stockid,
		String workercode,
		String workername,
		String terminalno,
		String jobtype,
		String processname,
		String batchno,
		int hostqty,
		int inputqty,
		String locationno,
		String jobno,
		String customercode,
		String customername,
		String carrykey,
		String areano,
		String planukey,
		String plandate,
		Date planregdate)
		throws ReadWriteException
	{
		try
		{
			WorkingInformationHandler wWorkHandler = new WorkingInformationHandler(conn);
			WorkingInformation workInfo = new WorkingInformation();

			//#CM43907
			// Work No.
			workInfo.setJobNo(jobno);
			//#CM43908
			// Work Flag
			workInfo.setJobType(jobtype);
			//#CM43909
			// Consolidating Work No.
			workInfo.setCollectJobNo(jobno);
			//#CM43910
			// Status flag:Working
			workInfo.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			//#CM43911
			// Work beginning flag:Started
			workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			//#CM43912
			// Plan unique key
			workInfo.setPlanUkey(planukey);
			//#CM43913
			// Stock ID
			workInfo.setStockId(stockid);
			//#CM43914
			// Area No.
			workInfo.setAreaNo(areano);
			//#CM43915
			// Location No..
			workInfo.setLocationNo(locationno);
			//#CM43916
			// Plan Date
			workInfo.setPlanDate(plandate);
			//#CM43917
			// Consignor Code
			workInfo.setConsignorCode(stock.getConsignorCode());
			//#CM43918
			// Consignor Name
			workInfo.setConsignorName(stock.getConsignorName());
			//#CM43919
			// Supplier code
			workInfo.setSupplierCode("");
			//#CM43920
			// Supplier name
			workInfo.setSupplierName1("");
			//#CM43921
			// Receiving ticket No.
			workInfo.setInstockTicketNo("");
			//#CM43922
			// Receiving line No.
			workInfo.setInstockLineNo(0);
			//#CM43923
			// Shipper code
			workInfo.setCustomerCode(customercode);
			//#CM43924
			// Shipper name
			workInfo.setCustomerName1(customername);
			//#CM43925
			// Item Code
			workInfo.setItemCode(param.getItemCode());
			//#CM43926
			// Item Name
			workInfo.setItemName1(param.getItemName());
			//#CM43927
			// Work plan qty (Host plan qty)
			workInfo.setHostPlanQty(hostqty);
			//#CM43928
			// Work plan qty 
			workInfo.setPlanQty(inputqty);
			//#CM43929
			// Work possible qty 
			workInfo.setPlanEnableQty(inputqty);
			//#CM43930
			// Actual work qty 
			workInfo.setResultQty(0);
			//#CM43931
			// Work shortage qty 
			workInfo.setShortageCnt(0);
			//#CM43932
			// Reserved qty
			workInfo.setPendingQty(0);
			//#CM43933
			// Qty per case
			workInfo.setEnteringQty(param.getEnteringQty());
			//#CM43934
			// Qty per bundle
			workInfo.setBundleEnteringQty(param.getBundleEnteringQty());
			//#CM43935
			// Case/Piece flag(Mode of packing)
			workInfo.setCasePieceFlag(param.getCasePieceFlag());
			//#CM43936
			// Case/Piece flag(Work form)
			workInfo.setWorkFormFlag(param.getCasePieceFlag());
			//#CM43937
			// Case ITF 
			workInfo.setItf(param.getITF());
			//#CM43938
			// Bundle ITF
			workInfo.setBundleItf(param.getBundleITF());
			//#CM43939
			// TC/DCFlag
			workInfo.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			//#CM43940
			// Expiry date
			String useByDate = "";
			if (param.getUseByDate().length() <= 8)
			{
				useByDate = param.getUseByDate();
			}
			else
			{
				useByDate =
					param.getUseByDate().substring(0, 4)
						+ param.getUseByDate().substring(5, 7)
						+ param.getUseByDate().substring(8, 10);
			}
			workInfo.setUseByDate(useByDate);
			//#CM43941
			// Lot No.
			workInfo.setLotNo("");
			//#CM43942
			// Plan information comment
			workInfo.setPlanInformation("");
			//#CM43943
			// Order No.
			workInfo.setOrderNo("");
			//#CM43944
			// Order day
			workInfo.setOrderingDate("");
			//#CM43945
			// Expiry date(Results)
			workInfo.setResultUseByDate("");
			//#CM43946
			// Lot No.(Results)
			workInfo.setResultLotNo("");
			//#CM43947
			// Work resultLocation No..
			workInfo.setResultLocationNo("");
			//#CM43948
			// Unwork report flag
			workInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			//#CM43949
			// Batch No.
			workInfo.setBatchNo(batchno);
			//#CM43950
			// Worker code
			workInfo.setWorkerCode(workercode);
			//#CM43951
			// Worker Name
			workInfo.setWorkerName(workername);
			//#CM43952
			// Terminal No.
			workInfo.setTerminalNo(terminalno);
			//#CM43953
			// Plan information registration date
			workInfo.setPlanRegistDate(planregdate);
			//#CM43954
			// Registration Processing name
			workInfo.setRegistPname(processname);
			//#CM43955
			// Last updated date and time
			workInfo.setLastUpdateDate(new Date());
			//#CM43956
			// Last updated Processing name
			workInfo.setLastUpdatePname(processname);
			//#CM43957
			// Another system connection key
			workInfo.setSystemConnKey(carrykey);
			//#CM43958
			// Another system identification key
			workInfo.setSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_ASRS);

			//#CM43959
			// Registration of work information
			wWorkHandler.create(workInfo);

			return true;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM43960
	/**
	 * Register transportation information table (CARRYINFO). <BR> 
	 * <BR>     
	 * Register transportation information based on the content of received parameter. <BR>
	 * <BR>
	 * @param conn        Instance to maintain connection with data base. 
	 * @param worktype    Work Flag(Work type)
	 * @param batchno     Batch No.
	 * @param locationno  Location No..
	 * @param jobno       Work No.
	 * @param retrievaldetail The delivery instruction is detailed. 
	 * @param currentstation Transportation originStation No.
	 * @param deststation At the transportation destinationStation No.
	 * @param paletteid   Palette ID
	 * @param cmdstatus   Status of transportation
	 * @param priority    Priority Flag
	 * @param carrykind   Transportation Flag
	 * @param carrykey    Transportation key
	 * @param ailestation Aisle Station No.
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean createCarryinfo(
		Connection conn,
		String worktype,
		String batchno,
		String locationno,
		String jobno,
		int retrievaldetail,
		String currentstation,
		String deststation,
		int paletteid,
		int cmdstatus,
		int priority,
		int carrykind,
		String carrykey,
		String ailestation)
		throws ReadWriteException
	{
		try
		{
			CarryInformationHandler wCarryHandler = new CarryInformationHandler(conn);
			CarryInformation carryInfo = new CarryInformation();

			//#CM43961
			// Transportation Key
			carryInfo.setCarryKey(carrykey);
			//#CM43962
			// Palette ID
			carryInfo.setPaletteId(paletteid);
			//#CM43963
			// Update date
			carryInfo.setCreateDate(new Date());
			//#CM43964
			// Work type
			carryInfo.setWorkType(worktype);
			//#CM43965
			// Retrieval group No.
			carryInfo.setGroupNumber(000);
			//#CM43966
			// Status of transportation
			carryInfo.setCmdStatus(cmdstatus);
			//#CM43967
			// Priority Flag
			carryInfo.setPriority(priority);

			//#CM43968
			// Restorage flag
			if (carrykind == CarryInformation.CARRYKIND_RETRIEVAL)
			{
				StationHandler sthandler = new StationHandler(conn);
				StationSearchKey stkey = new StationSearchKey();

				stkey.setStationNumber(deststation);
				Station station = (Station) sthandler.findPrimary(stkey);

				//#CM43969
				// Transportation instruction necessity
				if (station.getReStoringInstruction() == Station.AWC_STORAGE_SEND)
				{
					//#CM43970
					// Because Storage shelf is retrieved again by the transportation instruction of the return stock, "Do not stock it in the same shelf again" is set. 
					carryInfo.setReStoringFlag(CarryInformation.RESTORING_NOT_SAME_LOC);
				}
				//#CM43971
				// The transportation instruction is unnecessary. 
				else
				{
					//#CM43972
					// Restore in same shelf
					carryInfo.setReStoringFlag(CarryInformation.RESTORING_SAME_LOC);
				}
			}
			else
			{
				//#CM43973
				// No Restorage
				carryInfo.setReStoringFlag(CarryInformation.RESTORING_NOT_SAME_LOC);
			}

			//#CM43974
			// Transportation Flag
			carryInfo.setCarryKind(carrykind);
			//#CM43975
			// Retrieval Location No.
			carryInfo.setRetrievalStationNumber(locationno);
			//#CM43976
			// The delivery instruction is detailed. 
			carryInfo.setRetrievalDetail(retrievaldetail);
			//#CM43977
			// Work No.
			carryInfo.setWorkNumber(jobno);
			//#CM43978
			// Transportation originStation No.
			carryInfo.setSourceStationNumber(currentstation);
			//#CM43979
			// At the transportation destinationStation No.
			carryInfo.setDestStationNumber(deststation);
			//#CM43980
			// Arrival date
			carryInfo.setArrivalDate(null);
			//#CM43981
			// Control information
			carryInfo.setControlInfo("");
			//#CM43982
			// Cancellation demand Flag
			carryInfo.setCancelRequest(CarryInformation.CANCELREQUEST_UNDEMAND);
			//#CM43983
			// Cancellation demand date
			carryInfo.setCancelRequestDate(null);
			//#CM43984
			// Schedule No.
			carryInfo.setScheduleNumber(batchno);
			//#CM43985
			// Aisle Station No..
			carryInfo.setAisleStationNumber(ailestation);
			//#CM43986
			// Final Station No. 
			carryInfo.setEndStationNumber(deststation);
			//#CM43987
			// Abnormal code
			carryInfo.setErrorCode(0);
			//#CM43988
			// Maintenance Terminal No.
			carryInfo.setMaintenanceTerminal("");

			//#CM43989
			// Registration of transportation information
			wCarryHandler.create(carryInfo);

			return true;
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM43990
	/**
	 * Register the stock information table. 
	 * @param  conn        Instance to maintain connection with data base. 
	 * @param  param       Instance of AsScheduleParameter class with content input from screen. 
	 * @param  inputqty    Storage qty
	 * @param  stockid     Stock ID
	 * @param  locationno	Location No.
	 * @param  paletteid   Palette ID
	 * @param  planukey    Plan unique key
	 * @param  planInfo    Plan information comment
	 * @param  processname Program Name
	 * @return Stock ID in case of normal schedule processing. Null when schedule processing fails or it is not possible to schedule it
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected String createStock(
		Connection conn,
		AsScheduleParameter param,
		int inputqty,
		String stockid,
		String locationno,
		int paletteid,
		String planukey,
		String planInfo,
		String processname,
		String areano)
		throws ReadWriteException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			Stock stock = new Stock();

			//#CM43991
			// Stock ID
			stock.setStockId(stockid);
			//#CM43992
			// Plan unique key
			stock.setPlanUkey(planukey);
			//#CM43993
			// Area No.
			stock.setAreaNo(areano);
			//#CM43994
			// Location No..
			stock.setLocationNo(locationno);
			//#CM43995
			// Item Code
			stock.setItemCode(param.getItemCode());
			//#CM43996
			// Item Name
			stock.setItemName1(param.getItemName());
			//#CM43997
			// Stock status(Storage waiting)
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_RECEIVINGRESERVE);
			//#CM43998
			// Stock qty (Storage qty)
			stock.setStockQty(0);
			//#CM43999
			// Drawing qty
			stock.setAllocationQty(0);
			//#CM44000
			// Storage plan qty
			stock.setPlanQty(inputqty);
			//#CM44001
			// Case/Piece flag(Mode of packing)
			//#CM44002
			// Unspecified
			if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
			//#CM44003
			// Case
			else if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			//#CM44004
			// Piece
			else if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}

			//#CM44005
			// Storage date & time 
			stock.setInstockDate(null);
			//#CM44006
			// The final Retrieval date
			stock.setLastShippingDate("");
			//#CM44007
			// Expiry date
			String useByDate = "";
			if (param.getUseByDate().length() <= 8)
			{
				useByDate = param.getUseByDate();
			}
			else
			{
				useByDate = param.getUseByDate().substring(0, 8);
			}
			stock.setUseByDate(useByDate);
			//#CM44008
			// Lot No.
			stock.setLotNo("");
			//#CM44009
			// Plan information comment
			stock.setPlanInformation(planInfo);
			//#CM44010
			// Consignor Code
			stock.setConsignorCode(param.getConsignorCode());
			//#CM44011
			// Consignor Name
			stock.setConsignorName(param.getConsignorName());
			//#CM44012
			// Supplier code
			stock.setSupplierCode("");
			//#CM44013
			// Supplier name
			stock.setSupplierName1("");
			//#CM44014
			// Qty per case
			stock.setEnteringQty(param.getEnteringQty());
			//#CM44015
			// Qty per bundle
			stock.setBundleEnteringQty(param.getBundleEnteringQty());
			//#CM44016
			// Case ITF
			stock.setItf(param.getITF());
			//#CM44017
			// Bundle ITF
			stock.setBundleItf(param.getBundleITF());
			//#CM44018
			// Registration Processing name
			stock.setRegistPname(processname);
			//#CM44019
			// Last updated Processing name
			stock.setLastUpdatePname(processname);
			//#CM44020
			// Palette ID
			stock.setPaletteid(paletteid);
			//#CM44021
			// Restorage flag
			stock.setRestoring(0);

			//#CM44022
			// Registration of data
			stockHandler.create(stock);

			return stockid;

		}
		catch (DataExistsException e)
		{
			//#CM44023
			 /* Process it abnormally here to search for the cause of the 
			 * phenomenon of generating DataExistException by the stock making. 
			 * This processing is unnecessary and delete it when you understand the cause where the exception is generated. */
			StockHandler stkh = new StockHandler(conn);
			
			StockSearchKey stkKey = new StockSearchKey();
			
			//#CM44024
			// When Stock ID is the same
			stkKey.setStockId(stockid);
			if (stkh.count(stkKey) > 0)
			{
				//#CM44025
				// 6016104 = Because the stock of same StockID already exists, it is not possible to register. StockID={0}
				RmiMsgLogClient.write("6016104" + wDelim + stockid, CLASS_NAME) ;
				throw new ReadWriteException(e.getMessage());
			}
			
			//#CM44026
			// When the condition is the same
			stkKey.KeyClear();
			stkKey.setPlanUkey(planukey);
			stkKey.setConsignorCode(param.getConsignorCode());
			stkKey.setItemCode(param.getItemCode());
			stkKey.setLocationNo(locationno);
			if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
			{
				stkKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
			//#CM44027
			// Case
			else if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
			{
				stkKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			//#CM44028
			// Piece
			else if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
			{
				stkKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}
			stkKey.setUseByDate(param.getUseByDate());
			if (stkh.count(stkKey) > 0)
			{
				Stock[] stks = (Stock[]) stkh.find(stkKey);
				if (stks != null && stks.length != 0)
				{
					String stkId = stks[0].getStockId();
					String updDate = stks[0].getLastUpdateDate().toString();
					String updPname = stks[0].getLastUpdatePname();
					//#CM44029
					// 6016105 = Because the stock of the same data already exists, it is not possible to register.  StockID={0} LastUpdateDate={1} LastUpdatePname={2}
					RmiMsgLogClient.write("6016105" + wDelim + stkId + wDelim + updDate + wDelim + updPname, CLASS_NAME) ;
					throw new ReadWriteException(e.getMessage());
				}
				
			}
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM44030
	/**
	 * Instance making of palette table (PALETTE)<BR> 
	 * <BR>     
	 * It is the palette informational based on the content of received parameter. <BR>
	 * Make the instance. <BR>
	 * Do not do to the registration of the data base. <BR>
	 * <BR>
	 * @param station     Station instance
	 * @param paletteid   Palette ID
	 * @param height      load height
	 * @return Entity of Palette ID
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected Palette createInstancePalette(
		Station station,
		int paletteid,
		int height)
		throws ReadWriteException
	{
		Palette palette = new Palette();

		//#CM44031
		// Palette ID
		palette.setPaletteId(paletteid);
		//#CM44032
		// Present Station
		palette.setCurrentStationNumber(station.getStationNumber());
		//#CM44033
		// Warehouse Station
		palette.setWHStationNumber(station.getWHStationNumber());
		//#CM44034
		// Palette type ID
		palette.setPaletteTypeId(Palette.PALETTE_TYPE_ID);
		//#CM44035
		// Stock Status(Stock reservation)
		palette.setStatus(Palette.STORAGE_PLAN);
		//#CM44036
		// Drawing qty (Drawing settlement)
		palette.setAllocation(Palette.ALLOCATED);
		//#CM44037
		// Empty palette status(Usual palette)
		palette.setEmpty(Palette.NORMAL);
		//#CM44038
		// Updated date & time
		palette.setRefixDate(new Date());
		//#CM44039
		// Filling rate
		palette.setRate(0);
		//#CM44040
		// load height
		palette.setHeight(height);
		//#CM44041
		// Bar code information
		palette.setBcData("");

		return palette;
	}

	//#CM44042
	/**<en>
	 * Retrurns the apropriate message for the status of specified carry route.
	 * The returned message will be used in outputputting the display message area
	 * It provides the route status defined as <code>RouteController</code> class variable for status.
	 * If any status of carry route shich is not defined by RouteController class is provided to status, 
	 * it will notify ScheudleException.
	 * @param status :status of carry route
	 * @return message
	 * @throws ScheduleException :Notifies if unexpected status of carry route has been provided with status.
	 </en>*/
	protected String getRouteErrorMessage(int status) throws ScheduleException
	{
		String msg = null;
		switch (status)
		{
			case RouteController.OFFLINE :
				//#CM44043
				//<en> Cannot carry as some machines off-line were found on the carry route.</en>
				msg = "6013098";
				break;
			case RouteController.FAIL :
				//#CM44044
				//<en> Cannot carry as machine of equipment error is found on the carry route.</en>
				msg = "6013097";
				break;
			case RouteController.NOTFOUND :
				//#CM44045
				//<en> Cannot carry as there is no carry route.</en>
				msg = "6013096";
				break;
			case RouteController.NO_STATION_INTO_WORKPLACE :
				//#CM44046
				//<en> There is no station available for the carry in workshop.</en>
				msg = "6013133";
				break;
			case RouteController.LOCATION_EMPTY :
				//#CM44047
				//<en> There is no empty location available for the storage.</en>
				msg = "6013104";
				break;
			case RouteController.AISLE_INVENTORYCHECK :
				//#CM44048
				//<en> Cannot set as inventory check is in process.</en>
				msg = "6013102";
				break;
			case RouteController.AISLE_EMPTYLOCATIONCHECK :
				//#CM44049
				//<en> Cannot set as empty location check is in process.</en>
				msg = "6013103";
				break;
			case RouteController.AGC_OFFLINE :
				//#CM44050
				//<en> Set the status of system on-line.</en>
				msg = "6013023";
				break;
			default :
				//#CM44051
				//<en> Invalid value was specified. Cannot set. Class={0} Variable={1} Value={2}</en>
				Object[] tObj = new Object[3];
				tObj[0] = this.getClass().getName();
				tObj[1] = "RouteStatus";
				tObj[2] = Integer.toString(status);
				RmiMsgLogClient.write(6016061, (String) tObj[0], tObj);
				throw new ScheduleException("6016061" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]);
		}
		return msg;
	}

	//#CM44052
	/**<en>
	 * Set the specified message in the message storage area.
	 * @param msg :message
	 </en>*/
	protected void setMessage(String msg)
	{
		wMessage = msg;
	}

	//#CM44053
	/**
	 * Register Storage Retrieval Results information table (INOUTRESULT). <BR> 
	 * <BR>     
	 * Register Storage Retrieval Results information based on the content of received parameter. <BR>
	 * <BR>
	 * @param conn        Instance to maintain connection with data base. 
	 * @param param       Instance of AsScheduleParameter class with content input from screen. 
	 * @param jobtype     Work Flag
	 * @param pltkind     Palette type
	 * @param pltid       Palette ID
	 * @param inputqty	   Actual work qty 
	 * @param resultkind  Results making Flag
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean createInOutResult(
		Connection conn,
		AsScheduleParameter param,
		String jobtype,
		int pltkind,
		int pltid,
		int inputqty,
		int resultkind)
		throws ReadWriteException
	{
		try
		{
			InOutResultHandler wIOHandler = new InOutResultHandler(conn);
			InOutResult regInOutResult = new InOutResult();

			regInOutResult.setStoreDate(new Date());
			regInOutResult.setResultKind(resultkind);
			regInOutResult.setConsignorCode(param.getConsignorCode());
			regInOutResult.setConsignorName(param.getConsignorName());
			regInOutResult.setItemCode(param.getItemCode());
			regInOutResult.setLotNumber("");
			regInOutResult.setWorkType(jobtype);
			regInOutResult.setStationNumber("");
			regInOutResult.setWHStationNumber(param.getWareHouseNo());
			regInOutResult.setAisleStationNumber("");
			regInOutResult.setEnteringQty(param.getEnteringQty());
			regInOutResult.setBundleEnteringQty(param.getBundleEnteringQty());
			regInOutResult.setInOutQuantity(inputqty);
			regInOutResult.setWorkNumber("");
			regInOutResult.setPaletteKind(pltkind);
			regInOutResult.setLocationNumber(param.getLocationNo());
			regInOutResult.setScheduleNumber("");
			regInOutResult.setPaletteId(pltid);
			regInOutResult.setCarryKey("");
			regInOutResult.setCustomerCode("");
			regInOutResult.setItemName1(param.getItemName());
			regInOutResult.setCustomerName(param.getCustomerName());
			regInOutResult.setReStoring(Integer.parseInt(param.getStoringStatus()));
			regInOutResult.setInCommingDate(new Date());
			regInOutResult.setStatus(InOutResult.UNPROCESSED);
			regInOutResult.setReport(InOutResult.NOT_REPORT);
			regInOutResult.setOrderNumber("");
			regInOutResult.setLineNumber(0);

			//#CM44054
			// Registration of data
			wIOHandler.create(regInOutResult);

			return true;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM44055
	/**
	 * Register stock confirmation setting table (INVENTORYCHECK). <BR> 
	 * <BR>     
	 * Register transportation information based on the content of received parameter. <BR>
	 * <BR>
	 * @param conn			Instance to maintain connection with data base. 
	 * @param consignorcode Consignor Code
	 * @param schno		Schedule No
	 * @param warehouse	Warehouse
	 * @param fromlocation	Beginning shelf
	 * @param tolocation 	End shelf
	 * @param fromitemcode	Start Item Code
	 * @param toitemcode 	End Item Code
	 * @param station		Station No
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean createInventoryCheck(
		Connection conn,
		String consignorcode,
		String schno,
		String warehouse,
		String fromlocation,
		String tolocation,
		String fromitemcode,
		String toitemcode,
		String station)
		throws ReadWriteException
	{
		try
		{
			ASInventoryCheckHandler inchkHandler = new ASInventoryCheckHandler(conn);
			ASInventoryCheck inchk = new ASInventoryCheck();

			//#CM44056
			// Schedule No
			inchk.setScheduleNumber(schno);
			//#CM44057
			// Update date
			inchk.setCreateDate(new Date());
			//#CM44058
			// Submit type (Confirming Stock)
			inchk.setSettingType(Aisle.INVENTORYCHECK);
			//#CM44059
			// Warehouse Station No
			inchk.setWHStationNumber(warehouse);
			//#CM44060
			// Consignor Code
			inchk.setConsignorCode(consignorcode);
			if( !StringUtil.isBlank(consignorcode) )
			{
				StockHandler stkh = new StockHandler(conn);
				StockSearchKey stkKey = new StockSearchKey();
				stkKey.setConsignorCode(consignorcode);
				stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
				Stock[] stk = (Stock[]) stkh.find(stkKey);
				if( stk.length > 0)
				{
					//#CM44061
					// Consignor Name
					inchk.setConsignorName(stk[0].getConsignorName());
				}
			}
			//#CM44062
			// Beginning shelfNo
			inchk.setFromLocation(fromlocation);
			//#CM44063
			// End shelfNo
			inchk.setToLocation(tolocation);
			//#CM44064
			// Start Item Code
			inchk.setFromItemCode(fromitemcode);
			//#CM44065
			// Start Item Code
			inchk.setToItemCode(toitemcode);
			//#CM44066
			// Lot No.
			inchk.setLotNumber("");
			//#CM44067
			// Station No
			inchk.setStationNumber(station);
			//#CM44068
			// Status (Processing)
			inchk.setStatus(Aisle.INVENTORYCHECK);

			//#CM44069
			// Registration of stock confirmation information
			inchkHandler.create(inchk);

			return true;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM44070
	/**
	 * Register work display table (OPERATIONDISPLAY). <BR> 
	 * <BR>     
	 * Register transportation information based on the content of received parameter. <BR>
	 * <BR>
	 * @param conn			Instance to maintain connection with data base. 
	 * @param carrykey		Transportation key
	 * @param station		Station No
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean createOperationDisplay(Connection conn, String carrykey, String station)
		throws ReadWriteException
	{
		try
		{
			OperationDisplayHandler opdispHandler = new OperationDisplayHandler(conn);
			OperationDisplay opdisp = new OperationDisplay();

			//#CM44071
			// Transportation key
			opdisp.setCarryKey(carrykey);
			//#CM44072
			// Station No
			opdisp.setStationNumber(station);
			//#CM44073
			// Arrival date
			opdisp.setArrivalDate(new Date());

			//#CM44074
			// Registration of work display information
			opdispHandler.create(opdisp);

			return true;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM44075
	/**
	 * Do the judgment which stocks and can work for specified Station. <BR> 
	 * <BR>     
	 * @param conn			Instance to maintain connection with data base. 
	 * @param rStation		Station information instance
	 * @return Error Message : Null is notified when it is possible to work.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected String isStorageStationCheck(Connection conn, Station rStation) throws ReadWriteException
	{
		try
		{
			String w_Msg = null;

			GroupControllerHandler wGcHandler = new GroupControllerHandler(conn);
			GroupControllerSearchKey wGcSearchKey = new GroupControllerSearchKey();
			AisleHandler wAisleHandler = new AisleHandler(conn);
			AisleSearchKey wAisleSearchKey = new AisleSearchKey();

			wGcSearchKey.KeyClear();
			wGcSearchKey.setControllerNumber(rStation.getControllerNumber());

			GroupController[] rGroupControll = (GroupController[]) wGcHandler.find(wGcSearchKey);

			if (rGroupControll[0].getStatus() != GroupController.STATUS_ONLINE)
			{
				//#CM44076
				//<en> Please set the status of system on-line.</en>
				w_Msg = "6013023";
				return w_Msg;
			}
			
			//#CM44077
			//<en> Check the suspention flag.</en>
			if (rStation.isSuspend())
			{
				//#CM44078
				//<en> Cannot set; the station has been suspended.</en>
				w_Msg = "6013099";
				return w_Msg;
			}
			
			//#CM44079
			//<en> Check the type of the station.</en>
			if (!rStation.isInStation())
			{
				//#CM44080
				//<en> Station not to be able to do the stock work cannot be selected. </en>
				w_Msg = "6013100";
				return w_Msg;
			}
			
			//#CM44081
			//<en> Check the retrieval mode.</en>
			if (!rStation.isStorageMode())
			{
				//#CM44082
				//<en> Change the work mode of Station to the stock. </en>
				w_Msg = "6013101";
				return w_Msg;
			}
			
			//#CM44083
			//<en> Check whether/not the inventory check is in process.</en>
			//#CM44084
			//<en> If the aisle station No. is not in defined (due to aisle connected station)</en>
			if (StringUtil.isBlank(rStation.getAisleStationNumber()))
			{
				//#CM44085
				//<en> Search all over the warehouse.</en>
				wAisleSearchKey.KeyClear();
				wAisleSearchKey.setWHStationNumber(rStation.getWHStationNumber());
				wAisleSearchKey.setInventoryCheckFlag(Aisle.INVENTORYCHECK);

				if (wAisleHandler.count(wAisleSearchKey) > 0)
				{
					//#CM44086
					//<en> Cannot set as inventory check is in process.</en>
					w_Msg = "6013102";
					return w_Msg;
				}
			}
			//#CM44087
			//<en> If the aisle station No. is defined (stand alone station)</en>
			else
			{
				//#CM44088
				//<en> Search within the same aisle.</en>
				wAisleSearchKey.KeyClear();
				wAisleSearchKey.setStationNumber(rStation.getAisleStationNumber());

				Aisle rAisle = (Aisle) wAisleHandler.findPrimary(wAisleSearchKey);
				if (rAisle.getInventoryCheckFlag() == Aisle.INVENTORYCHECK)
				{
					//#CM44089
					//<en> Cannot set as inventory check is in process.</en>
					w_Msg = "6013102";
					return w_Msg;
				}
			}

			return w_Msg;
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM44090
	/**
	 * Do the judgment which delivers and can work for specified Station. <BR> 
	 * <BR>     
	 * @param conn			Instance to maintain connection with data base. 
	 * @param rStation		Station information instance
	 * @param piconly      Only when the picking working, true is set. 
	 * @return Error Message : Null is notified when it is possible to work. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected String isRetrievalStationCheck(Connection conn, Station rStation, boolean piconly)
		throws ReadWriteException
	{
		try
		{
			String w_Msg = null;

			GroupControllerHandler wGcHandler = new GroupControllerHandler(conn);
			GroupControllerSearchKey wGcSearchKey = new GroupControllerSearchKey();
			AisleHandler wAisleHandler = new AisleHandler(conn);
			AisleSearchKey wAisleSearchKey = new AisleSearchKey();

			wGcSearchKey.KeyClear();
			wGcSearchKey.setControllerNumber(rStation.getControllerNumber());

			GroupController[] rGroupControll = (GroupController[]) wGcHandler.find(wGcSearchKey);

			if (rGroupControll[0].getStatus() != GroupController.STATUS_ONLINE)
			{
				//#CM44091
				//<en> Please set the status of system on-line.</en>
				w_Msg = "6013023";
				return w_Msg;
			}
			
			//#CM44092
			//<en> Check the suspention flag.</en>
			if (rStation.isSuspend())
			{
				//#CM44093
				//<en> Cannot set; the station has been suspended.</en>
				w_Msg = "6013099";
				return w_Msg;
			}
			
			if (piconly)
			{
				//#CM44094
				//<en> Check unacceptable if specified station is dedicated for unit retrieval.</en>
				if (rStation.isUnitOnly())
				{
					//#CM44095
					//<en>Please select a station that permits picking.</en>
					w_Msg = "6013261";
					return w_Msg;
				}
			}
			
			//#CM44096
			//<en> Check the type of the station.</en>
			if (!rStation.isOutStation())
			{
				//#CM44097
				//<en> Cannot select the station where retrieval work cannot be conducted.</en>
				w_Msg = "6013111";
				return w_Msg;
			}
			
			//#CM44098
			//<en> Check the retrieval mode.</en>
			if (!rStation.isRetrievalMode())
			{
				//#CM44099
				//<en> Canot set as the station is not on retrieval mode.</en>
				w_Msg = "6013112";
				return w_Msg;
			}
			
			//#CM44100
			//<en> Check whether/not the inventory check is in process.</en>
			//#CM44101
			//<en> If the aisle station No. is not in defined (due to aisle connected station)</en>
			if (StringUtil.isBlank(rStation.getAisleStationNumber()))
			{
				//#CM44102
				//<en> Search all over the warehouse.</en>
				wAisleSearchKey.KeyClear();
				wAisleSearchKey.setWHStationNumber(rStation.getWHStationNumber());
				wAisleSearchKey.setInventoryCheckFlag(Aisle.INVENTORYCHECK);

				if (wAisleHandler.count(wAisleSearchKey) > 0)
				{
					//#CM44103
					//<en> Cannot set as inventory check is in process.</en>
					w_Msg = "6013102";
					return w_Msg;
				}
			}
			//#CM44104
			//<en> If the aisle station No. is defined (stand alone station)</en>
			else
			{
				//#CM44105
				//<en> Search within the same aisle.</en>
				wAisleSearchKey.KeyClear();
				wAisleSearchKey.setStationNumber(rStation.getAisleStationNumber());
	
				Aisle rAisle = (Aisle) wAisleHandler.findPrimary(wAisleSearchKey);
				if (rAisle.getInventoryCheckFlag() == Aisle.INVENTORYCHECK)
				{
					//#CM44106
					//<en> Cannot set as inventory check is in process.</en>
					w_Msg = "6013102";
					return w_Msg;
				}
			}

			return w_Msg;
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM44107
	/**
	 * Acquire the number of Warehouse of maximum consolidation. 
	 * 
	 * @param conn Database connection
	 * @param whStationNo Warehouse Station No.
	 * @return Number of maximum consolidation
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected int getMaxMixedPalette(Connection conn, String whStationNo) throws ReadWriteException
	{
		WareHouse wh = null;
		try
		{
			WareHouseHandler whHandler = new WareHouseHandler(conn);
			WareHouseSearchKey whKey = new WareHouseSearchKey();
			whKey.setStationNumber(whStationNo);
			wh = (WareHouse) whHandler.findPrimary(whKey);
			
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		return wh.getMaxMixedPalette();
		
	}
}
//#CM44108
//end of class
