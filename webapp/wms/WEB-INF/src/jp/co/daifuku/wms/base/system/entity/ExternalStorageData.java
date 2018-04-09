//#CM695238
//$Id: ExternalStorageData.java,v 1.2 2006/10/18 08:15:09 suresh Exp $

//#CM695239
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.entity;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM695240
/**
 * This class controls the HOST Storage Plan data
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/18 08:15:09 $
 * @author  $Author: suresh $
 */
public class ExternalStorageData extends Entity implements SystemDefine
{
	//#CM695241
	// Class feilds ------------------------------------------------

	//#CM695242
	// Class variables -----------------------------------------------
	//#CM695243
	/**
	 * Plan Date
	 */
	protected String wPlanDate = "";

	//#CM695244
	/**
	 * Consignor Code
	 */
	protected String wConsignorCode = "";

	//#CM695245
	/**
	 * Consignor Name
	 */
	protected String wConsignorName = "";

	//#CM695246
	/**
	 * Item Code
	 */
	protected String wItemCode = "";

	//#CM695247
	/**
	 * Bundle ITF
	 */
	protected String wBundleItf = "";

	//#CM695248
	/**
	 * Case ITF
	 */
	protected String wItf = "";

	//#CM695249
	/**
	 * Packed Qty Per Bundle
	 */
	protected int wBundleEnteringQty = 0;

	//#CM695250
	/**
	 * Packed Qty Per Case
	 */
	protected int wEnteringQty = 0;

	//#CM695251
	/**
	 * Item Name
	 */
	protected String wItemName = "";

	//#CM695252
	/**
	 * Work Plan Qty
	 */
	protected int wPlanQty = 0;

	//#CM695253
	/**
	 * Piece item location no.
	 */
	protected String wPieceLocation = "";

	//#CM695254
	/**
	 * Case item location no.
	 */
	protected String wCaseLocation = "";

	//#CM695255
	// Preserve data for data loadingSTART ------------------------------

	//#CM695256
	/**
	 * Work Plan Qty (Case)
	 */
	protected int wPlanCaseQty = 0;

	//#CM695257
	/**
	 * Work Plan Qty (Piece)
	 */
	protected int wPlanPieceQty = 0;

	//#CM695258
	/**
	 * Case Piece division
	 */
	protected String wCasePieceFlag ;

	//#CM695259
	// Preserve data for data loadingEND   ------------------------------

	//#CM695260
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM695261
	// Class method --------------------------------------------------

	//#CM695262
	// Constructors --------------------------------------------------
	//#CM695263
	/**
	 * Set default value for all the instance variables
	 */
	public ExternalStorageData()
	{
	}

	//#CM695264
	// Public methods ------------------------------------------------
	//#CM695265
	/**
	 * Return version of this class
	 * @return version and date/time
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/18 08:15:09 $");
	}

	//#CM695266
	// Package methods -----------------------------------------------

	//#CM695267
	// Protected methods ---------------------------------------------

	//#CM695268
	// Private methods -----------------------------------------------

	//#CM695269
	/**
	 * Fetch Plan Date
	 * @return Plan Date
	 */
	public String getPlanDate()
	{
		return wPlanDate;
	}

	//#CM695270
	/**
	 * Set value to Plan Date
	 * @param planDate Plan Date to be set
	 */
	public void setPlanDate(String planDate)
	{
		wPlanDate = planDate;
	}

	//#CM695271
	/**
	 * Fetch Consignor Code
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM695272
	/**
	 * Set value to Consignor Code
	 * @param consignorCode Consignor Code
	 */
	public void setConsignorCode(String consignorCode)
	{
		wConsignorCode = consignorCode;
	}

	//#CM695273
	/**
	 * Fetch Consignor Name
	 * @return Consignor Name
	 */
	public String getConsignorName()
	{
		return wConsignorName;
	}

	//#CM695274
	/**
	 * Set value to Consignor Name
	 * @param consignorName Consignor Name
	 */
	public void setConsignorName(String consignorName)
	{
		wConsignorName = consignorName;
	}

	//#CM695275
	/**
	 * Fetch Item Code
	 * @return Item Code
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	//#CM695276
	/**
	 * Set value to Item Code
	 * @param itemCode Item Code
	 */
	public void setItemCode(String itemCode)
	{
		wItemCode = itemCode;
	}

	//#CM695277
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return wBundleItf;
	}

	//#CM695278
	/**
	 * Set value to Bundle ITF
	 * @param bundleItf Bundle ITF
	 */
	public void setBundleItf(String bundleItf)
	{
		wBundleItf = bundleItf;
	}

	//#CM695279
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return wItf;
	}

	//#CM695280
	/**
	 * Set value to Case ITF
	 * @param itf Case ITF
	 */
	public void setItf(String itf)
	{
		wItf = itf;
	}

	//#CM695281
	/**
	 * Set value to Packed Qty Per Bundle
	 * @return Packed Qty Per Bundle
	 */
	public int getBundleEnteringQty()
	{
		return wBundleEnteringQty;
	}

	//#CM695282
	/**
	 * Set value to Packed Qty Per Bundle
	 * @param bundleEnteringQty Packed Qty Per Bundle
	 */
	public void setBundleEnteringQty(int bundleEnteringQty)
	{
		wBundleEnteringQty = bundleEnteringQty;
	}

	//#CM695283
	/**
	 * Fetch Packed Qty Per Case
	 * @return Packed Qty Per Case
	 */
	public int getEnteringQty()
	{
		return wEnteringQty;
	}

	//#CM695284
	/**
	 * Set value to Packed Qty Per Case
	 * @param enteringQty Packed Qty Per Case
	 */
	public void setEnteringQty(int enteringQty)
	{
		wEnteringQty = enteringQty;
	}

	//#CM695285
	/**
	 * Fetch Item Name
	 * @return Item Name
	 */
	public String getItemName()
	{
		return wItemName;
	}

	//#CM695286
	/**
	 * Set value to Item Name
	 * @param itemName Item Name
	 */
	public void setItemName(String itemName)
	{
		wItemName = itemName;
	}

	//#CM695287
	/**
	 * Fetch Work Plan Qty
	 * @return Work Plan Qty
	 */
	public int getPlanQty()
	{
		return wPlanQty;
	}

	//#CM695288
	/**
	 * Set value to Work Plan Qty
	 * @param planQty Work Plan Qty
	 */
	public void setPlanQty(int planQty)
	{
		wPlanQty = planQty;
	}

	//#CM695289
	/**
	 * Fetch Piece item location no.
	 * @return Piece item location no.
	 */
	public String getPieceLocation()
	{
		return wPieceLocation;
	}

	//#CM695290
	/**
	 * Set value to Piece item location no.
	 * @param pieceLocation Piece item location no.
	 */
	public void setPieceLocation(String pieceLocation)
	{
		wPieceLocation = pieceLocation;
	}

	//#CM695291
	/**
	 * Fetch Case item location no.
	 * @return Case item location no.
	 */
	public String getCaseLocation()
	{
		return wCaseLocation;
	}

	//#CM695292
	/**
	 * Set value to Case item location no.
	 * @param caseLocation Case item location no.
	 */
	public void setCaseLocation(String caseLocation)
	{
		wCaseLocation = caseLocation;
	}
	
	//#CM695293
	/**
	 * Fetch Work Plan Qty (Case)
	 * @return Work Plan Qty (Case)
	 */
	public int getPlanCaseQty()
	{
		return wPlanCaseQty;
	}

	//#CM695294
	/**
	 * Set value to Work Plan Qty (Case)
	 * @param planCaseQty Work Plan Qty (Case)
	 */
	public void setPlanCaseQty(int planCaseQty)
	{
		wPlanCaseQty = planCaseQty;
	}

	//#CM695295
	/**
	 * Fetch Work Plan Qty (Piece)
	 * @return Work Plan Qty (Piece)
	 */
	public int getPlanPieceQty()
	{
		return wPlanPieceQty;
	}

	//#CM695296
	/**
	 * Set value to Work Plan Qty (Piece)
	 * @param planPieceQty Work Plan Qty (Piece)
	 */
	public void setPlanPieceQty(int planPieceQty)
	{
		wPlanPieceQty = planPieceQty;
	}

	//#CM695297
	/**
	 * Set value to Case Piece division
	 * @param casepieceflag Case Piece division
	 */
	public void setCasePieceFlag(String casepieceflag) throws InvalidStatusException
	{
		if ((casepieceflag.equals(ExternalStorageData.CASEPIECE_FLAG_CASE))
			|| (casepieceflag.equals(ExternalStorageData.CASEPIECE_FLAG_PIECE))
			|| (casepieceflag.equals(ExternalStorageData.CASEPIECE_FLAG_NOTHING))
			|| (casepieceflag.equals(ExternalStorageData.CASEPIECE_FLAG_MIX)))
		{
			wCasePieceFlag = casepieceflag;
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wCasePieceFlag";
			tObj[2] = casepieceflag;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM695298
	/**
	 * Fetch Case Piece division
	 * @return Case Piece division
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

}
//#CM695299
//end of class
