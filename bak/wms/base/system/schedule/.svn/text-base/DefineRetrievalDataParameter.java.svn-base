//#CM697173
//$Id: DefineRetrievalDataParameter.java,v 1.2 2006/11/13 06:03:10 suresh Exp $

//#CM697174
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import jp.co.daifuku.wms.base.common.Parameter;
 
//#CM697175
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * Use the <CODE>DefineRetrievalDataParameter</CODE> class to pass a parameter between the screen and the schedule for setting field items to be loaded or reported for the Picking data.<BR>
 * Allow this class to maintain the field items to be used in each screen for system package. Use a field item depending on the screen.<BR>
 * <BR>
 * <DIR>
 * Allow the <CODE>DefineRetrievalDataParameter</CODE> class to maintain the following field items.<BR>
 * <BR>
 * [Textbox or Label] <BR>
 * <DIR>
 *     Worker Code <BR>
 *     Password <BR>
 * <BR>
 *     Length_Planned Retrieval Date <BR>
 *     Length_Consignor Code <BR>
 *     Length_Consignor Name <BR>
 *     Length_Customer Code <BR>
 *     Length_Customer Name <BR>
 *     Length_Ticket No. <BR>
 *     Length_Ticket Line No. <BR>
 *     Length_Item Code <BR>
 *     Length_Bundle ITF <BR>
 *     Length_Case ITF <BR>
 *     Length_Packed qty per bundle <BR>
 *     Length_Packed Qty per Case <BR>
 *     Length_Item Name <BR>
 *     Length_Picking Qty (Total Bulk Qty) <BR> 
 *     Length_Piece Picking Location <BR>
 *     Length_Case Picking Location <BR>
 *     Length_Piece Order No. <BR>
 *     Length_Case Order No. <BR>
 *     Length_Result Piece Qty <BR>
 *     Length_Result Case Qty <BR>
 *     Length_Picking Result Date <BR> 
 *     Length_Result division <BR>
 *     Length_Expiry Date <BR>
 * <BR>
 *     Max Length_Planned Retrieval Date <BR>
 *     Max Length_Consignor Code <BR>
 *     Max Length_Consignor Name <BR>
 *     Max Length_Customer Code <BR>
 *     Max Length_Customer Name <BR>
 *     Max Length_Ticket No. <BR>
 *     Max Length_Ticket Line No. <BR>
 *     Max Length_Item Code <BR>
 *     Max Length_Bundle ITF <BR>
 *     Max Length_Case ITF <BR>
 *     Max Length_Packed qty per bundle <BR>
 *     Max Length_Packed Qty per Case <BR>
 *     Max Length_Item Name <BR>
 *     Max Length_Picking Qty (Total Bulk Qty) <BR> 
 *     Max Length_Piece Picking Location <BR>
 *     Max Length_Case Picking Location <BR>
 *     Max Length_Piece Order No. <BR>
 *     Max Length_Case Order No. <BR>
 *     Max Length_Result Piece Qty <BR>
 *     Max Length_Result Case Qty <BR>
 *     Max Length_Picking Result Date <BR> 
 *     Max Length_Result division <BR>
 *     Max Length_Expiry Date <BR>
 * <BR>
 *     Position_Planned Retrieval Date <BR>
 *     Position_Consignor Code <BR>
 *     Position_Consignor Name <BR>
 *     Position_Customer Code <BR>
 *     Position_Customer Name <BR>
 *     Position_Ticket No. <BR>
 *     Position_Ticket Line No. <BR>
 *     Position_Item Code <BR>
 *     Position_Bundle ITF <BR>
 *     Position_Case ITF <BR>
 *     Position_Packed qty per bundle <BR>
 *     Position_Packed Qty per Case <BR>
 *     Position_Item Name <BR>
 *     Position_Picking Qty (Total Bulk Qty) <BR> 
 *     Position_Piece Picking Location <BR>
 *     Position_Case Picking Location <BR>
 *     Position_Piece Order No. <BR>
 *     Position_Case Order No. <BR>
 *     Position_Result Piece Qty <BR>
 *     Position_Result Case Qty <BR>
 *     Position_Picking Result Date <BR> 
 *     Position_Result division <BR>
 *     Position_Expiry Date <BR>
 * </DIR>
 * [Checkbox] <BR>
 * <DIR>
 *     Enabled_Planned Retrieval Date <BR>
 *     Enabled_Consignor Code <BR>
 *     Enabled_Consignor Name <BR>
 *     Enabled_Customer Code <BR>
 *     Enabled_Customer Name <BR>
 *     Enabled_Ticket No. <BR>
 *     Enabled_Ticket Line No. <BR>
 *     Enabled_Item Code <BR>
 *     Enabled_Bundle ITF <BR>
 *     Enabled_Case ITF <BR>
 *     Enabled_Packed qty per bundle <BR>
 *     Enabled_Packed Qty per Case <BR>
 *     Enabled_Item Name <BR>
 *     Enabled_Picking Qty (Total Bulk Qty) <BR> 
 *     Enabled_Piece Picking Location <BR>
 *     Enabled_Case Picking Location <BR>
 *     Enabled_Piece Order No. <BR>
 *     Enabled_Case Order No. <BR>
 *     Enabled_Result Piece Qty <BR>
 *     Enabled_Result Case Qty <BR>
 *     Enabled_Picking Result Date <BR> 
 *     Enabled_Result division <BR>
 *     Enabled_Expiry Date <BR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/04</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:10 $
 * @author  $Author: suresh $
 */
public class DefineRetrievalDataParameter extends Parameter
{
	//#CM697176
	// Class variables -----------------------------------------------
	//#CM697177
	/**
	 * Worker Code
	 */
	private String wWorkerCode;
	
	//#CM697178
	/**
	 * Password
	 */
	private String wPassword;
	
	//#CM697179
	/**
	 * Length_Planned Retrieval Date
	 */
	private String wFigure_RetrievalPlanDate;
	
	//#CM697180
	/**
	 * Length_Consignor Code
	 */
	private String wFigure_ConsignorCode;
	
	//#CM697181
	/**
	 * Length_Consignor Name
	 */
	private String wFigure_ConsignorName;
	
	//#CM697182
	/**
	 * Length_Customer Code
	 */
	private String wFigure_CustomerCode;
	
	//#CM697183
	/**
	 * Length_Customer Name
	 */
	private String wFigure_CustomerName;
	
	//#CM697184
	/**
	 * Length_Ticket No.
	 */
	private String wFigure_TicketNo;
	
	//#CM697185
	/**
	 * Length_Ticket Line No.
	 */
	private String wFigure_LineNo;
	
	//#CM697186
	/**
	 * Length_Item Code
	 */
	private String wFigure_ItemCode;
	
	//#CM697187
	/**
	 * Length_Bundle ITF
	 */
	private String wFigure_BundleItf;
	
	//#CM697188
	/**
	 * Length_Case ITF
	 */
	private String wFigure_Itf;
	
	//#CM697189
	/**
	 * Length_Packed qty per bundle
	 */
	private String wFigure_BundleEnteringQty;
	
	//#CM697190
	/**
	 * Length_Packed Qty per Case
	 */
	private String wFigure_EnteringQty;
	
	//#CM697191
	/**
	 * Length_Item Name
	 */
	private String wFigure_ItemName;
	
	//#CM697192
	/**
	 * Length_Picking Qty (Total Bulk Qty)
	 */
	private String wFigure_RetrievalPlanQty;

	//#CM697193
	/**
	 * Length_Piece Picking Location
	 */
	private String wFigure_PieceRetrievalLocation;
	
	//#CM697194
	/**
	 * Length_Case Picking Location
	 */
	private String wFigure_CaseRetrievalLocation;
	
	//#CM697195
	/**
	 * Length_Piece Order No.
	 */
	private String wFigure_PieceOrderNo;
	
	//#CM697196
	/**
	 * Length_Case Order No.
	 */
	private String wFigure_CaseOrderNo;
	
	//#CM697197
	/**
	 * Length_Result Piece Qty
	 */
	private String wFigure_PieceResultQty;
	
	//#CM697198
	/**
	 * Length_Result Case Qty
	 */
	private String wFigure_CaseResultQty;
	
	//#CM697199
	/**
	 * Length_Picking Result Date
	 */
	private String wFigure_RetrievalWorkDate;
	
	//#CM697200
	/**
	 * Length_Result division
	 */
	private String wFigure_ResultFlag;
	
	//#CM697201
	/**
	 * Length_Expiry Date
	 */
	private String wFigure_UseByDate;
	
	//#CM697202
	/**
	 * Max Length_Planned Retrieval Date
	 */
	private String wMaxFigure_RetrievalPlanDate;
	
	//#CM697203
	/**
	 * Max Length_Consignor Code
	 */
	private String wMaxFigure_ConsignorCode;
	
	//#CM697204
	/**
	 * Max Length_Consignor Name
	 */
	private String wMaxFigure_ConsignorName;
	
	//#CM697205
	/**
	 * Max Length_Customer Code
	 */
	private String wMaxFigure_CustomerCode;
	
	//#CM697206
	/**
	 * Max Length_Customer Name
	 */
	private String wMaxFigure_CustomerName;
	
	//#CM697207
	/**
	 * Max Length_Ticket No.
	 */
	private String wMaxFigure_TicketNo;
	
	//#CM697208
	/**
	 * Max Length_Ticket Line No.
	 */
	private String wMaxFigure_LineNo;
	
	//#CM697209
	/**
	 * Max Length_Item Code
	 */
	private String wMaxFigure_ItemCode;
	
	//#CM697210
	/**
	 * Max Length_Bundle ITF
	 */
	private String wMaxFigure_BundleItf;
	
	//#CM697211
	/**
	 * Max Length_Case ITF
	 */
	private String wMaxFigure_Itf;
	
	//#CM697212
	/**
	 * Max Length_Packed qty per bundle
	 */
	private String wMaxFigure_BundleEnteringQty;
	
	//#CM697213
	/**
	 * Max Length_Packed Qty per Case
	 */
	private String wMaxFigure_EnteringQty;
	
	//#CM697214
	/**
	 * Max Length_Item Name
	 */
	private String wMaxFigure_ItemName;
	
	//#CM697215
	/**
	 * Max Length_Picking Qty (Total Bulk Qty)
	 */
	private String wMaxFigure_RetrievalPlanQty;

	//#CM697216
	/**
	 * Max Length_Piece Picking Location
	 */
	private String wMaxFigure_PieceRetrievalLocation;
	
	//#CM697217
	/**
	 * Max Length_Case Picking Location
	 */
	private String wMaxFigure_CaseRetrievalLocation;
	
	//#CM697218
	/**
	 * Max Length_Piece Order No.
	 */
	private String wMaxFigure_PieceOrderNo;
	
	//#CM697219
	/**
	 * Max Length_Case Order No.
	 */
	private String wMaxFigure_CaseOrderNo;
	
	//#CM697220
	/**
	 * Max Length_Result Piece Qty
	 */
	private String wMaxFigure_PieceResultQty;
	
	//#CM697221
	/**
	 * Max Length_Result Case Qty
	 */
	private String wMaxFigure_CaseResultQty;
	
	//#CM697222
	/**
	 * Max Length_Picking Result Date
	 */
	private String wMaxFigure_RetrievalWorkDate;
	
	//#CM697223
	/**
	 * Max Length_Result division
	 */
	private String wMaxFigure_ResultFlag;
	
	//#CM697224
	/**
	 * Max Length_Expiry Date
	 */
	private String wMaxFigure_UseByDate;
	
	//#CM697225
	/**
	 * Position_Planned Retrieval Date
	 */
	private String wPosition_RetrievalPlanDate;
	
	//#CM697226
	/**
	 * Position_Consignor Code
	 */
	private String wPosition_ConsignorCode;
	
	//#CM697227
	/**
	 * Position_Consignor Name
	 */
	private String wPosition_ConsignorName;
	
	//#CM697228
	/**
	 * Position_Customer Code
	 */
	private String wPosition_CustomerCode;
	
	//#CM697229
	/**
	 * Position_Customer Name
	 */
	private String wPosition_CustomerName;
	
	//#CM697230
	/**
	 * Position_Ticket No.
	 */
	private String wPosition_TicketNo;
	
	//#CM697231
	/**
	 * Position_Ticket Line No.
	 */
	private String wPosition_LineNo;
	
	//#CM697232
	/**
	 * Position_Item Code
	 */
	private String wPosition_ItemCode;
	
	//#CM697233
	/**
	 * Position_Bundle ITF
	 */
	private String wPosition_BundleItf;
	
	//#CM697234
	/**
	 * Position_Case ITF
	 */
	private String wPosition_Itf;
	
	//#CM697235
	/**
	 * Position_Packed qty per bundle
	 */
	private String wPosition_BundleEnteringQty;
	
	//#CM697236
	/**
	 * Position_Packed Qty per Case
	 */
	private String wPosition_EnteringQty;
	
	//#CM697237
	/**
	 * Position_Item Name
	 */
	private String wPosition_ItemName;
	
	//#CM697238
	/**
	 * Position_Picking Qty (Total Bulk Qty)
	 */
	private String wPosition_RetrievalPlanQty;

	//#CM697239
	/**
	 * Position_Piece Picking Location
	 */
	private String wPosition_PieceRetrievalLocation;
	
	//#CM697240
	/**
	 * Position_Case Picking Location
	 */
	private String wPosition_CaseRetrievalLocation;
	
	//#CM697241
	/**
	 * Position_Piece Order No.
	 */
	private String wPosition_PieceOrderNo;
	
	//#CM697242
	/**
	 * Position_Case Order No.
	 */
	private String wPosition_CaseOrderNo;
	
	//#CM697243
	/**
	 * Position_Result Piece Qty
	 */
	private String wPosition_PieceResultQty;
	
	//#CM697244
	/**
	 * Position_Result Case Qty
	 */
	private String wPosition_CaseResultQty;
	
	//#CM697245
	/**
	 * Position_Picking Result Date
	 */
	private String wPosition_RetrievalWorkDate;
	
	//#CM697246
	/**
	 * Position_Result division
	 */
	private String wPosition_ResultFlag;
	
	//#CM697247
	/**
	 * Position_Expiry Date
	 */
	private String wPosition_UseByDate;
	
	//#CM697248
	/**
	 * Enabled_Planned Retrieval Date
	 */
	private boolean wValid_RetrievalPlanDate;
	
	//#CM697249
	/**
	 * Enabled_Consignor Code
	 */
	private boolean wValid_ConsignorCode;
	
	//#CM697250
	/**
	 * Enabled_Consignor Name
	 */
	private boolean wValid_ConsignorName;
	
	//#CM697251
	/**
	 * Enabled_Customer Code
	 */
	private boolean wValid_CustomerCode;
	
	//#CM697252
	/**
	 * Enabled_Customer Name
	 */
	private boolean wValid_CustomerName;
	
	//#CM697253
	/**
	 * Enabled_Ticket No.
	 */
	private boolean wValid_TicketNo;
	
	//#CM697254
	/**
	 * Enabled_Ticket Line No.
	 */
	private boolean wValid_LineNo;
	
	//#CM697255
	/**
	 * Enabled_Item Code
	 */
	private boolean wValid_ItemCode;
	
	//#CM697256
	/**
	 * Enabled_Bundle ITF
	 */
	private boolean wValid_BundleItf;
	
	//#CM697257
	/**
	 * Enabled_Case ITF
	 */
	private boolean wValid_Itf;
	
	//#CM697258
	/**
	 * Enabled_Packed qty per bundle
	 */
	private boolean wValid_BundleEnteringQty;
	
	//#CM697259
	/**
	 * Enabled_Packed Qty per Case
	 */
	private boolean wValid_EnteringQty;
	
	//#CM697260
	/**
	 * Enabled_Item Name
	 */
	private boolean wValid_ItemName;
	
	//#CM697261
	/**
	 * Enabled_Picking Qty (Total Bulk Qty)
	 */
	private boolean wValid_RetrievalPlanQty;

	//#CM697262
	/**
	 * Enabled_Piece Picking Location
	 */
	private boolean wValid_PieceRetrievalLocation;
	
	//#CM697263
	/**
	 * Enabled_Case Picking Location
	 */
	private boolean wValid_CaseRetrievalLocation;
	
	//#CM697264
	/**
	 * Enabled_Piece Order No.
	 */
	private boolean wValid_PieceOrderNo;
	
	//#CM697265
	/**
	 * Enabled_Case Order No.
	 */
	private boolean wValid_CaseOrderNo;
	
	//#CM697266
	/**
	 * Enabled_Result Piece Qty
	 */
	private boolean wValid_PieceResultQty;
	
	//#CM697267
	/**
	 * Enabled_Result Case Qty
	 */
	private boolean wValid_CaseResultQty;
	
	//#CM697268
	/**
	 * Enabled_Picking Result Date
	 */
	private boolean wValid_RetrievalWorkDate;
	
	//#CM697269
	/**
	 * Enabled_Result division
	 */
	private boolean wValid_ResultFlag;
	
	//#CM697270
	/**
	 * Enabled_Expiry Date
	 */
	private boolean wValid_UseByDate;


	//#CM697271
	// Class method --------------------------------------------------
	//#CM697272
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:10 $");
	}

	//#CM697273
	// Constructors --------------------------------------------------
	//#CM697274
	/**
	 * Initialize this class.
	 */
	public DefineRetrievalDataParameter()
	{
	}
	
	//#CM697275
	// Public methods ------------------------------------------------

	//#CM697276
	/**
	 * Worker Code
	 * @return	Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM697277
	/**
	 * Password
	 * @return	Password
	 */
	public String getPassword()
	{
		return wPassword;
	}

	//#CM697278
	/**
	 * Length_Packed qty per bundle
	 * @return	Length_Packed qty per bundle
	 */
	public String getFigure_BundleEnteringQty()
	{
		return wFigure_BundleEnteringQty;
	}

	//#CM697279
	/**
	 * Length_Bundle ITF
	 * @return	Length_Bundle ITF
	 */
	public String getFigure_BundleItf()
	{
		return wFigure_BundleItf;
	}

	//#CM697280
	/**
	 * Length_Case Order No.
	 * @return	Length_Case Order No.
	 */
	public String getFigure_CaseOrderNo()
	{
		return wFigure_CaseOrderNo;
	}

	//#CM697281
	/**
	 * Length_Result Case Qty
	 * @return	Length_Result Case Qty
	 */
	public String getFigure_CaseResultQty()
	{
		return wFigure_CaseResultQty;
	}

	//#CM697282
	/**
	 * Length_Case Picking Location
	 * @return	Length_Case Picking Location
	 */
	public String getFigure_CaseRetrievalLocation()
	{
		return wFigure_CaseRetrievalLocation;
	}

	//#CM697283
	/**
	 * Length_Consignor Code
	 * @return	Length_Consignor Code
	 */
	public String getFigure_ConsignorCode()
	{
		return wFigure_ConsignorCode;
	}

	//#CM697284
	/**
	 * Length_Consignor Name
	 * @return	Length_Consignor Name
	 */
	public String getFigure_ConsignorName()
	{
		return wFigure_ConsignorName;
	}

	//#CM697285
	/**
	 * Length_Customer Code
	 * @return	Length_Customer Code
	 */
	public String getFigure_CustomerCode()
	{
		return wFigure_CustomerCode;
	}

	//#CM697286
	/**
	 * Length_Customer Name
	 * @return	Length_Customer Name
	 */
	public String getFigure_CustomerName()
	{
		return wFigure_CustomerName;
	}

	//#CM697287
	/**
	 * Length_Packed Qty per Case
	 * @return	Length_Packed Qty per Case
	 */
	public String getFigure_EnteringQty()
	{
		return wFigure_EnteringQty;
	}

	//#CM697288
	/**
	 * Length_Item Code
	 * @return	Length_Item Code
	 */
	public String getFigure_ItemCode()
	{
		return wFigure_ItemCode;
	}

	//#CM697289
	/**
	 * Length_Item Name
	 * @return	Length_Item Name
	 */
	public String getFigure_ItemName()
	{
		return wFigure_ItemName;
	}

	//#CM697290
	/**
	 * Length_Case ITF
	 * @return	Length_Case ITF
	 */
	public String getFigure_Itf()
	{
		return wFigure_Itf;
	}

	//#CM697291
	/**
	 * Length_Ticket Line No.
	 * @return	Length_Ticket Line No.
	 */
	public String getFigure_LineNo()
	{
		return wFigure_LineNo;
	}

	//#CM697292
	/**
	 * Length_Piece Order No.
	 * @return	Length_Piece Order No.
	 */
	public String getFigure_PieceOrderNo()
	{
		return wFigure_PieceOrderNo;
	}

	//#CM697293
	/**
	 * Length_Result Piece Qty
	 * @return	Length_Result Piece Qty
	 */
	public String getFigure_PieceResultQty()
	{
		return wFigure_PieceResultQty;
	}

	//#CM697294
	/**
	 * Length_Piece Picking Location
	 * @return	Length_Piece Picking Location
	 */
	public String getFigure_PieceRetrievalLocation()
	{
		return wFigure_PieceRetrievalLocation;
	}

	//#CM697295
	/**
	 * Length_Result division
	 * @return	Length_Result division
	 */
	public String getFigure_ResultFlag()
	{
		return wFigure_ResultFlag;
	}

	//#CM697296
	/**
	 * Length_Planned Retrieval Date
	 * @return	Length_Planned Retrieval Date
	 */
	public String getFigure_RetrievalPlanDate()
	{
		return wFigure_RetrievalPlanDate;
	}

	//#CM697297
	/**
	 * Length_Picking Qty (Total Bulk Qty)
	 * @return	Length_Picking Qty (Total Bulk Qty)
	 */
	public String getFigure_RetrievalPlanQty()
	{
		return wFigure_RetrievalPlanQty;
	}

	//#CM697298
	/**
	 * Length_Picking Result Date
	 * @return	Length_Picking Result Date
	 */
	public String getFigure_RetrievalWorkDate()
	{
		return wFigure_RetrievalWorkDate;
	}

	//#CM697299
	/**
	 * Length_Ticket No.
	 * @return	Length_Ticket No.
	 */
	public String getFigure_TicketNo()
	{
		return wFigure_TicketNo;
	}

	//#CM697300
	/**
	 * Length_Expiry Date
	 * @return	Length_Expiry Date
	 */
	public String getFigure_UseByDate()
	{
		return wFigure_UseByDate;
	}

	//#CM697301
	/**
	 * Max Length_Packed qty per bundle
	 * @return	Max Length_Packed qty per bundle
	 */
	public String getMaxFigure_BundleEnteringQty()
	{
		return wMaxFigure_BundleEnteringQty;
	}

	//#CM697302
	/**
	 * Max Length_Bundle ITF
	 * @return	Max Length_Bundle ITF
	 */
	public String getMaxFigure_BundleItf()
	{
		return wMaxFigure_BundleItf;
	}

	//#CM697303
	/**
	 * Max Length_Case Order No.
	 * @return	Max Length_Case Order No.
	 */
	public String getMaxFigure_CaseOrderNo()
	{
		return wMaxFigure_CaseOrderNo;
	}

	//#CM697304
	/**
	 * Max Length_Result Case Qty
	 * @return	Max Length_Result Case Qty
	 */
	public String getMaxFigure_CaseResultQty()
	{
		return wMaxFigure_CaseResultQty;
	}

	//#CM697305
	/**
	 * Max Length_Case Picking Location
	 * @return	Max Length_Case Picking Location
	 */
	public String getMaxFigure_CaseRetrievalLocation()
	{
		return wMaxFigure_CaseRetrievalLocation;
	}

	//#CM697306
	/**
	 * Max Length_Consignor Code
	 * @return	Max Length_Consignor Code
	 */
	public String getMaxFigure_ConsignorCode()
	{
		return wMaxFigure_ConsignorCode;
	}

	//#CM697307
	/**
	 * Max Length_Consignor Name
	 * @return	Max Length_Consignor Name
	 */
	public String getMaxFigure_ConsignorName()
	{
		return wMaxFigure_ConsignorName;
	}

	//#CM697308
	/**
	 * Max Length_Customer Code
	 * @return	Max Length_Customer Code
	 */
	public String getMaxFigure_CustomerCode()
	{
		return wMaxFigure_CustomerCode;
	}

	//#CM697309
	/**
	 * Max Length_Customer Name
	 * @return	Max Length_Customer Name
	 */
	public String getMaxFigure_CustomerName()
	{
		return wMaxFigure_CustomerName;
	}

	//#CM697310
	/**
	 * Max Length_Packed Qty per Case
	 * @return	Max Length_Packed Qty per Case
	 */
	public String getMaxFigure_EnteringQty()
	{
		return wMaxFigure_EnteringQty;
	}

	//#CM697311
	/**
	 * Max Length_Item Code
	 * @return	Max Length_Item Code
	 */
	public String getMaxFigure_ItemCode()
	{
		return wMaxFigure_ItemCode;
	}

	//#CM697312
	/**
	 * Max Length_Item Name
	 * @return	Max Length_Item Name
	 */
	public String getMaxFigure_ItemName()
	{
		return wMaxFigure_ItemName;
	}

	//#CM697313
	/**
	 * Max Length_Case ITF
	 * @return	Max Length_Case ITF
	 */
	public String getMaxFigure_Itf()
	{
		return wMaxFigure_Itf;
	}

	//#CM697314
	/**
	 * Max Length_Ticket Line No.
	 * @return	Max Length_Ticket Line No.
	 */
	public String getMaxFigure_LineNo()
	{
		return wMaxFigure_LineNo;
	}

	//#CM697315
	/**
	 * Max Length_Piece Order No.
	 * @return	Max Length_Piece Order No.
	 */
	public String getMaxFigure_PieceOrderNo()
	{
		return wMaxFigure_PieceOrderNo;
	}

	//#CM697316
	/**
	 * Max Length_Result Piece Qty
	 * @return	Max Length_Result Piece Qty
	 */
	public String getMaxFigure_PieceResultQty()
	{
		return wMaxFigure_PieceResultQty;
	}

	//#CM697317
	/**
	 * Max Length_Piece Picking Location
	 * @return	Max Length_Piece Picking Location
	 */
	public String getMaxFigure_PieceRetrievalLocation()
	{
		return wMaxFigure_PieceRetrievalLocation;
	}

	//#CM697318
	/**
	 * Max Length_Result division
	 * @return	Max Length_Result division
	 */
	public String getMaxFigure_ResultFlag()
	{
		return wMaxFigure_ResultFlag;
	}

	//#CM697319
	/**
	 * Max Length_Planned Retrieval Date
	 * @return	Max Length_Planned Retrieval Date
	 */
	public String getMaxFigure_RetrievalPlanDate()
	{
		return wMaxFigure_RetrievalPlanDate;
	}

	//#CM697320
	/**
	 * Max Length_Picking Qty (Total Bulk Qty)
	 * @return	Max Length_Picking Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_RetrievalPlanQty()
	{
		return wMaxFigure_RetrievalPlanQty;
	}

	//#CM697321
	/**
	 * Max Length_Picking Result Date
	 * @return	Max Length_Picking Result Date
	 */
	public String getMaxFigure_RetrievalWorkDate()
	{
		return wMaxFigure_RetrievalWorkDate;
	}

	//#CM697322
	/**
	 * Max Length_Ticket No.
	 * @return	Max Length_Ticket No.
	 */
	public String getMaxFigure_TicketNo()
	{
		return wMaxFigure_TicketNo;
	}

	//#CM697323
	/**
	 * Max Length_Expiry Date
	 * @return	Max Length_Expiry Date
	 */
	public String getMaxFigure_UseByDate()
	{
		return wMaxFigure_UseByDate;
	}

	//#CM697324
	/**
	 * Position_Packed qty per bundle
	 * @return	Position_Packed qty per bundle
	 */
	public String getPosition_BundleEnteringQty()
	{
		return wPosition_BundleEnteringQty;
	}

	//#CM697325
	/**
	 * Position_Bundle ITF
	 * @return	Position_Bundle ITF
	 */
	public String getPosition_BundleItf()
	{
		return wPosition_BundleItf;
	}

	//#CM697326
	/**
	 * Position_Case Order No.
	 * @return	Position_Case Order No.
	 */
	public String getPosition_CaseOrderNo()
	{
		return wPosition_CaseOrderNo;
	}

	//#CM697327
	/**
	 * Position_Result Case Qty
	 * @return	Position_Result Case Qty
	 */
	public String getPosition_CaseResultQty()
	{
		return wPosition_CaseResultQty;
	}

	//#CM697328
	/**
	 * Position_Case Picking Location
	 * @return	Position_Case Picking Location
	 */
	public String getPosition_CaseRetrievalLocation()
	{
		return wPosition_CaseRetrievalLocation;
	}

	//#CM697329
	/**
	 * Position_Consignor Code
	 * @return	Position_Consignor Code
	 */
	public String getPosition_ConsignorCode()
	{
		return wPosition_ConsignorCode;
	}

	//#CM697330
	/**
	 * Position_Consignor Name
	 * @return	Position_Consignor Name
	 */
	public String getPosition_ConsignorName()
	{
		return wPosition_ConsignorName;
	}

	//#CM697331
	/**
	 * Position_Customer Code
	 * @return	Position_Customer Code
	 */
	public String getPosition_CustomerCode()
	{
		return wPosition_CustomerCode;
	}

	//#CM697332
	/**
	 * Position_Customer Name
	 * @return	Position_Customer Name
	 */
	public String getPosition_CustomerName()
	{
		return wPosition_CustomerName;
	}

	//#CM697333
	/**
	 * Position_Packed Qty per Case
	 * @return	Position_Packed Qty per Case
	 */
	public String getPosition_EnteringQty()
	{
		return wPosition_EnteringQty;
	}

	//#CM697334
	/**
	 * Position_Item Code
	 * @return	Position_Item Code
	 */
	public String getPosition_ItemCode()
	{
		return wPosition_ItemCode;
	}

	//#CM697335
	/**
	 * Position_Item Name
	 * @return	Position_Item Name
	 */
	public String getPosition_ItemName()
	{
		return wPosition_ItemName;
	}

	//#CM697336
	/**
	 * Position_Case ITF
	 * @return	Position_Case ITF
	 */
	public String getPosition_Itf()
	{
		return wPosition_Itf;
	}

	//#CM697337
	/**
	 * Position_Ticket Line No.
	 * @return	Position_Ticket Line No.
	 */
	public String getPosition_LineNo()
	{
		return wPosition_LineNo;
	}

	//#CM697338
	/**
	 * Position_Piece Order No.
	 * @return	Position_Piece Order No.
	 */
	public String getPosition_PieceOrderNo()
	{
		return wPosition_PieceOrderNo;
	}

	//#CM697339
	/**
	 * Position_Result Piece Qty
	 * @return	Position_Result Piece Qty
	 */
	public String getPosition_PieceResultQty()
	{
		return wPosition_PieceResultQty;
	}

	//#CM697340
	/**
	 * Position_Piece Picking Location
	 * @return	Position_Piece Picking Location
	 */
	public String getPosition_PieceRetrievalLocation()
	{
		return wPosition_PieceRetrievalLocation;
	}

	//#CM697341
	/**
	 * Position_Result division
	 * @return	Position_Result division
	 */
	public String getPosition_ResultFlag()
	{
		return wPosition_ResultFlag;
	}

	//#CM697342
	/**
	 * Position_Planned Retrieval Date
	 * @return	Position_Planned Retrieval Date
	 */
	public String getPosition_RetrievalPlanDate()
	{
		return wPosition_RetrievalPlanDate;
	}

	//#CM697343
	/**
	 * Position_Picking Qty (Total Bulk Qty)
	 * @return	Position_Picking Qty (Total Bulk Qty)
	 */
	public String getPosition_RetrievalPlanQty()
	{
		return wPosition_RetrievalPlanQty;
	}

	//#CM697344
	/**
	 * Position_Picking Result Date
	 * @return	Position_Picking Result Date
	 */
	public String getPosition_RetrievalWorkDate()
	{
		return wPosition_RetrievalWorkDate;
	}

	//#CM697345
	/**
	 * Position_Ticket No.
	 * @return	Position_Ticket No.
	 */
	public String getPosition_TicketNo()
	{
		return wPosition_TicketNo;
	}

	//#CM697346
	/**
	 * Position_Expiry Date
	 * @return	Position_Expiry Date
	 */
	public String getPosition_UseByDate()
	{
		return wPosition_UseByDate;
	}

	//#CM697347
	/**
	 * Enabled_Packed qty per bundle
	 * @return	Enabled_Packed qty per bundle
	 */
	public boolean getValid_BundleEnteringQty()
	{
		return wValid_BundleEnteringQty;
	}

	//#CM697348
	/**
	 * Enabled_Bundle ITF
	 * @return	Enabled_Bundle ITF
	 */
	public boolean getValid_BundleItf()
	{
		return wValid_BundleItf;
	}

	//#CM697349
	/**
	 * Enabled_Case Order No.
	 * @return	Enabled_Case Order No.
	 */
	public boolean getValid_CaseOrderNo()
	{
		return wValid_CaseOrderNo;
	}

	//#CM697350
	/**
	 * Enabled_Result Case Qty
	 * @return	Enabled_Result Case Qty
	 */
	public boolean getValid_CaseResultQty()
	{
		return wValid_CaseResultQty;
	}

	//#CM697351
	/**
	 * Enabled_Case Picking Location
	 * @return	Enabled_Case Picking Location
	 */
	public boolean getValid_CaseRetrievalLocation()
	{
		return wValid_CaseRetrievalLocation;
	}

	//#CM697352
	/**
	 * Enabled_Consignor Code
	 * @return	Enabled_Consignor Code
	 */
	public boolean getValid_ConsignorCode()
	{
		return wValid_ConsignorCode;
	}

	//#CM697353
	/**
	 * Enabled_Consignor Name
	 * @return	Enabled_Consignor Name
	 */
	public boolean getValid_ConsignorName()
	{
		return wValid_ConsignorName;
	}

	//#CM697354
	/**
	 * Enabled_Customer Code
	 * @return	Enabled_Customer Code
	 */
	public boolean getValid_CustomerCode()
	{
		return wValid_CustomerCode;
	}

	//#CM697355
	/**
	 * Enabled_Customer Name
	 * @return	Enabled_Customer Name
	 */
	public boolean getValid_CustomerName()
	{
		return wValid_CustomerName;
	}

	//#CM697356
	/**
	 * Enabled_Packed Qty per Case
	 * @return	Enabled_Packed Qty per Case
	 */
	public boolean getValid_EnteringQty()
	{
		return wValid_EnteringQty;
	}

	//#CM697357
	/**
	 * Enabled_Item Code
	 * @return	Enabled_Item Code
	 */
	public boolean getValid_ItemCode()
	{
		return wValid_ItemCode;
	}

	//#CM697358
	/**
	 * Enabled_Item Name
	 * @return	Enabled_Item Name
	 */
	public boolean getValid_ItemName()
	{
		return wValid_ItemName;
	}

	//#CM697359
	/**
	 * Enabled_Case ITF
	 * @return	Enabled_Case ITF
	 */
	public boolean getValid_Itf()
	{
		return wValid_Itf;
	}

	//#CM697360
	/**
	 * Enabled_Ticket Line No.
	 * @return	Enabled_Ticket Line No.
	 */
	public boolean getValid_LineNo()
	{
		return wValid_LineNo;
	}

	//#CM697361
	/**
	 * Enabled_Piece Order No.
	 * @return	Enabled_Piece Order No.
	 */
	public boolean getValid_PieceOrderNo()
	{
		return wValid_PieceOrderNo;
	}

	//#CM697362
	/**
	 * Enabled_Result Piece Qty
	 * @return	Enabled_Result Piece Qty
	 */
	public boolean getValid_PieceResultQty()
	{
		return wValid_PieceResultQty;
	}

	//#CM697363
	/**
	 * Enabled_Piece Picking Location
	 * @return	Enabled_Piece Picking Location
	 */
	public boolean getValid_PieceRetrievalLocation()
	{
		return wValid_PieceRetrievalLocation;
	}

	//#CM697364
	/**
	 * Enabled_Result division
	 * @return	Enabled_Result division
	 */
	public boolean getValid_ResultFlag()
	{
		return wValid_ResultFlag;
	}

	//#CM697365
	/**
	 * Enabled_Planned Retrieval Date
	 * @return	Enabled_Planned Retrieval Date
	 */
	public boolean getValid_RetrievalPlanDate()
	{
		return wValid_RetrievalPlanDate;
	}

	//#CM697366
	/**
	 * Enabled_Picking Qty (Total Bulk Qty)
	 * @return	Enabled_Picking Qty (Total Bulk Qty)
	 */
	public boolean getValid_RetrievalPlanQty()
	{
		return wValid_RetrievalPlanQty;
	}

	//#CM697367
	/**
	 * Enabled_Picking Result Date
	 * @return	Enabled_Picking Result Date
	 */
	public boolean getValid_RetrievalWorkDate()
	{
		return wValid_RetrievalWorkDate;
	}

	//#CM697368
	/**
	 * Enabled_Ticket No.
	 * @return	Enabled_Ticket No.
	 */
	public boolean getValid_TicketNo()
	{
		return wValid_TicketNo;
	}

	//#CM697369
	/**
	 * Enabled_Expiry Date
	 * @return	Enabled_Expiry Date
	 */
	public boolean getValid_UseByDate()
	{
		return wValid_UseByDate;
	}

	//#CM697370
	/**
	 * Length_Packed qty per bundle
	 * @param arg	Length_Packed qty per bundle
	 */
	public void setFigure_BundleEnteringQty(String arg)
	{
		wFigure_BundleEnteringQty = arg;
	}

	//#CM697371
	/**
	 * Length_Bundle ITF
	 * @param arg	Length_Bundle ITF
	 */
	public void setFigure_BundleItf(String arg)
	{
		wFigure_BundleItf = arg;
	}

	//#CM697372
	/**
	 * Length_Case Order No.
	 * @param arg	Length_Case Order No.
	 */
	public void setFigure_CaseOrderNo(String arg)
	{
		wFigure_CaseOrderNo = arg;
	}

	//#CM697373
	/**
	 * Length_Result Case Qty
	 * @param arg	Length_Result Case Qty
	 */
	public void setFigure_CaseResultQty(String arg)
	{
		wFigure_CaseResultQty = arg;
	}

	//#CM697374
	/**
	 * Length_Case Picking Location
	 * @param arg	Length_Case Picking Location
	 */
	public void setFigure_CaseRetrievalLocation(String arg)
	{
		wFigure_CaseRetrievalLocation = arg;
	}

	//#CM697375
	/**
	 * Length_Consignor Code
	 * @param arg	Length_Consignor Code
	 */
	public void setFigure_ConsignorCode(String arg)
	{
		wFigure_ConsignorCode = arg;
	}

	//#CM697376
	/**
	 * Length_Consignor Name
	 * @param arg	Length_Consignor Name
	 */
	public void setFigure_ConsignorName(String arg)
	{
		wFigure_ConsignorName = arg;
	}

	//#CM697377
	/**
	 * Length_Customer Code
	 * @param arg	Length_Customer Code
	 */
	public void setFigure_CustomerCode(String arg)
	{
		wFigure_CustomerCode = arg;
	}

	//#CM697378
	/**
	 * Length_Customer Name
	 * @param arg	Length_Customer Name
	 */
	public void setFigure_CustomerName(String arg)
	{
		wFigure_CustomerName = arg;
	}

	//#CM697379
	/**
	 * Length_Packed Qty per Case
	 * @param arg	Length_Packed Qty per Case
	 */
	public void setFigure_EnteringQty(String arg)
	{
		wFigure_EnteringQty = arg;
	}

	//#CM697380
	/**
	 * Length_Item Code
	 * @param arg	Length_Item Code
	 */
	public void setFigure_ItemCode(String arg)
	{
		wFigure_ItemCode = arg;
	}

	//#CM697381
	/**
	 * Length_Item Name
	 * @param arg	Length_Item Name
	 */
	public void setFigure_ItemName(String arg)
	{
		wFigure_ItemName = arg;
	}

	//#CM697382
	/**
	 * Length_Case ITF
	 * @param arg	Length_Case ITF
	 */
	public void setFigure_Itf(String arg)
	{
		wFigure_Itf = arg;
	}

	//#CM697383
	/**
	 * Length_Ticket Line No.
	 * @param arg	Length_Ticket Line No.
	 */
	public void setFigure_LineNo(String arg)
	{
		wFigure_LineNo = arg;
	}

	//#CM697384
	/**
	 * Length_Piece Order No.
	 * @param arg	Length_Piece Order No.
	 */
	public void setFigure_PieceOrderNo(String arg)
	{
		wFigure_PieceOrderNo = arg;
	}

	//#CM697385
	/**
	 * Length_Result Piece Qty
	 * @param arg	Length_Result Piece Qty
	 */
	public void setFigure_PieceResultQty(String arg)
	{
		wFigure_PieceResultQty = arg;
	}

	//#CM697386
	/**
	 * Length_Piece Picking Location
	 * @param arg	Length_Piece Picking Location
	 */
	public void setFigure_PieceRetrievalLocation(String arg)
	{
		wFigure_PieceRetrievalLocation = arg;
	}

	//#CM697387
	/**
	 * Length_Result division
	 * @param arg	Length_Result division
	 */
	public void setFigure_ResultFlag(String arg)
	{
		wFigure_ResultFlag = arg;
	}

	//#CM697388
	/**
	 * Length_Planned Retrieval Date
	 * @param arg	Length_Planned Retrieval Date
	 */
	public void setFigure_RetrievalPlanDate(String arg)
	{
		wFigure_RetrievalPlanDate = arg;
	}

	//#CM697389
	/**
	 * Length_Picking Qty (Total Bulk Qty)
	 * @param arg	Length_Picking Qty (Total Bulk Qty)
	 */
	public void setFigure_RetrievalPlanQty(String arg)
	{
		wFigure_RetrievalPlanQty = arg;
	}

	//#CM697390
	/**
	 * Length_Picking Result Date
	 * @param arg	Length_Picking Result Date
	 */
	public void setFigure_RetrievalWorkDate(String arg)
	{
		wFigure_RetrievalWorkDate = arg;
	}

	//#CM697391
	/**
	 * Length_Ticket No.
	 * @param arg	Length_Ticket No.
	 */
	public void setFigure_TicketNo(String arg)
	{
		wFigure_TicketNo = arg;
	}

	//#CM697392
	/**
	 * Length_Expiry Date
	 * @param arg	Length_Expiry Date
	 */
	public void setFigure_UseByDate(String arg)
	{
		wFigure_UseByDate = arg;
	}

	//#CM697393
	/**
	 * Max Length_Packed qty per bundle
	 * @param arg	Max Length_Packed qty per bundle
	 */
	public void setMaxFigure_BundleEnteringQty(String arg)
	{
		wMaxFigure_BundleEnteringQty = arg;
	}

	//#CM697394
	/**
	 * Max Length_Bundle ITF
	 * @param arg	Max Length_Bundle ITF
	 */
	public void setMaxFigure_BundleItf(String arg)
	{
		wMaxFigure_BundleItf = arg;
	}

	//#CM697395
	/**
	 * Max Length_Case Order No.
	 * @param arg	Max Length_Case Order No.
	 */
	public void setMaxFigure_CaseOrderNo(String arg)
	{
		wMaxFigure_CaseOrderNo = arg;
	}

	//#CM697396
	/**
	 * Max Length_Result Case Qty
	 * @param arg	Max Length_Result Case Qty
	 */
	public void setMaxFigure_CaseResultQty(String arg)
	{
		wMaxFigure_CaseResultQty = arg;
	}

	//#CM697397
	/**
	 * Max Length_Case Picking Location
	 * @param arg	Max Length_Case Picking Location
	 */
	public void setMaxFigure_CaseRetrievalLocation(String arg)
	{
		wMaxFigure_CaseRetrievalLocation = arg;
	}

	//#CM697398
	/**
	 * Max Length_Consignor Code
	 * @param arg	Max Length_Consignor Code
	 */
	public void setMaxFigure_ConsignorCode(String arg)
	{
		wMaxFigure_ConsignorCode = arg;
	}

	//#CM697399
	/**
	 * Max Length_Consignor Name
	 * @param arg	Max Length_Consignor Name
	 */
	public void setMaxFigure_ConsignorName(String arg)
	{
		wMaxFigure_ConsignorName = arg;
	}

	//#CM697400
	/**
	 * Max Length_Customer Code
	 * @param arg	Max Length_Customer Code
	 */
	public void setMaxFigure_CustomerCode(String arg)
	{
		wMaxFigure_CustomerCode = arg;
	}

	//#CM697401
	/**
	 * Max Length_Customer Name
	 * @param arg	Max Length_Customer Name
	 */
	public void setMaxFigure_CustomerName(String arg)
	{
		wMaxFigure_CustomerName = arg;
	}

	//#CM697402
	/**
	 * Max Length_Packed Qty per Case
	 * @param arg	Max Length_Packed Qty per Case
	 */
	public void setMaxFigure_EnteringQty(String arg)
	{
		wMaxFigure_EnteringQty = arg;
	}

	//#CM697403
	/**
	 * Max Length_Item Code
	 * @param arg	Max Length_Item Code
	 */
	public void setMaxFigure_ItemCode(String arg)
	{
		wMaxFigure_ItemCode = arg;
	}

	//#CM697404
	/**
	 * Max Length_Item Name
	 * @param arg	Max Length_Item Name
	 */
	public void setMaxFigure_ItemName(String arg)
	{
		wMaxFigure_ItemName = arg;
	}

	//#CM697405
	/**
	 * Max Length_Case ITF
	 * @param arg	Max Length_Case ITF
	 */
	public void setMaxFigure_Itf(String arg)
	{
		wMaxFigure_Itf = arg;
	}

	//#CM697406
	/**
	 * Max Length_Ticket Line No.
	 * @param arg	Max Length_Ticket Line No.
	 */
	public void setMaxFigure_LineNo(String arg)
	{
		wMaxFigure_LineNo = arg;
	}

	//#CM697407
	/**
	 * Max Length_Piece Order No.
	 * @param arg	Max Length_Piece Order No.
	 */
	public void setMaxFigure_PieceOrderNo(String arg)
	{
		wMaxFigure_PieceOrderNo = arg;
	}

	//#CM697408
	/**
	 * Max Length_Result Piece Qty
	 * @param arg	Max Length_Result Piece Qty
	 */
	public void setMaxFigure_PieceResultQty(String arg)
	{
		wMaxFigure_PieceResultQty = arg;
	}

	//#CM697409
	/**
	 * Max Length_Piece Picking Location
	 * @param arg	Max Length_Piece Picking Location
	 */
	public void setMaxFigure_PieceRetrievalLocation(String arg)
	{
		wMaxFigure_PieceRetrievalLocation = arg;
	}

	//#CM697410
	/**
	 * Max Length_Result division
	 * @param arg	Max Length_Result division
	 */
	public void setMaxFigure_ResultFlag(String arg)
	{
		wMaxFigure_ResultFlag = arg;
	}

	//#CM697411
	/**
	 * Max Length_Planned Retrieval Date
	 * @param arg	Max Length_Planned Retrieval Date
	 */
	public void setMaxFigure_RetrievalPlanDate(String arg)
	{
		wMaxFigure_RetrievalPlanDate = arg;
	}

	//#CM697412
	/**
	 * Max Length_Picking Qty (Total Bulk Qty)
	 * @param arg	Max Length_Picking Qty (Total Bulk Qty)
	 */
	public void setMaxFigure_RetrievalPlanQty(String arg)
	{
		wMaxFigure_RetrievalPlanQty = arg;
	}

	//#CM697413
	/**
	 * Max Length_Picking Result Date
	 * @param arg	Max Length_Picking Result Date
	 */
	public void setMaxFigure_RetrievalWorkDate(String arg)
	{
		wMaxFigure_RetrievalWorkDate = arg;
	}

	//#CM697414
	/**
	 * Max Length_Ticket No.
	 * @param arg	Max Length_Ticket No.
	 */
	public void setMaxFigure_TicketNo(String arg)
	{
		wMaxFigure_TicketNo = arg;
	}

	//#CM697415
	/**
	 * Max Length_Expiry Date
	 * @param arg	Max Length_Expiry Date
	 */
	public void setMaxFigure_UseByDate(String arg)
	{
		wMaxFigure_UseByDate = arg;
	}

	//#CM697416
	/**
	 * Position_Packed qty per bundle
	 * @param arg	Position_Packed qty per bundle
	 */
	public void setPosition_BundleEnteringQty(String arg)
	{
		wPosition_BundleEnteringQty = arg;
	}

	//#CM697417
	/**
	 * Position_Bundle ITF
	 * @param arg	Position_Bundle ITF
	 */
	public void setPosition_BundleItf(String arg)
	{
		wPosition_BundleItf = arg;
	}

	//#CM697418
	/**
	 * Position_Case Order No.
	 * @param arg	Position_Case Order No.
	 */
	public void setPosition_CaseOrderNo(String arg)
	{
		wPosition_CaseOrderNo = arg;
	}

	//#CM697419
	/**
	 * Position_Result Case Qty
	 * @param arg	Position_Result Case Qty
	 */
	public void setPosition_CaseResultQty(String arg)
	{
		wPosition_CaseResultQty = arg;
	}

	//#CM697420
	/**
	 * Position_Case Picking Location
	 * @param arg	Position_Case Picking Location
	 */
	public void setPosition_CaseRetrievalLocation(String arg)
	{
		wPosition_CaseRetrievalLocation = arg;
	}

	//#CM697421
	/**
	 * Position_Consignor Code
	 * @param arg	Position_Consignor Code
	 */
	public void setPosition_ConsignorCode(String arg)
	{
		wPosition_ConsignorCode = arg;
	}

	//#CM697422
	/**
	 * Position_Consignor Name
	 * @param arg	Position_Consignor Name
	 */
	public void setPosition_ConsignorName(String arg)
	{
		wPosition_ConsignorName = arg;
	}

	//#CM697423
	/**
	 * Position_Customer Code
	 * @param arg	Position_Customer Code
	 */
	public void setPosition_CustomerCode(String arg)
	{
		wPosition_CustomerCode = arg;
	}

	//#CM697424
	/**
	 * Position_Customer Name
	 * @param arg	Position_Customer Name
	 */
	public void setPosition_CustomerName(String arg)
	{
		wPosition_CustomerName = arg;
	}

	//#CM697425
	/**
	 * Position_Packed Qty per Case
	 * @param arg	Position_Packed Qty per Case
	 */
	public void setPosition_EnteringQty(String arg)
	{
		wPosition_EnteringQty = arg;
	}

	//#CM697426
	/**
	 * Position_Item Code
	 * @param arg	Position_Item Code
	 */
	public void setPosition_ItemCode(String arg)
	{
		wPosition_ItemCode = arg;
	}

	//#CM697427
	/**
	 * Position_Item Name
	 * @param arg	Position_Item Name
	 */
	public void setPosition_ItemName(String arg)
	{
		wPosition_ItemName = arg;
	}

	//#CM697428
	/**
	 * Position_Case ITF
	 * @param arg	Position_Case ITF
	 */
	public void setPosition_Itf(String arg)
	{
		wPosition_Itf = arg;
	}

	//#CM697429
	/**
	 * Position_Ticket Line No.
	 * @param arg	Position_Ticket Line No.
	 */
	public void setPosition_LineNo(String arg)
	{
		wPosition_LineNo = arg;
	}

	//#CM697430
	/**
	 * Position_Piece Order No.
	 * @param arg	Position_Piece Order No.
	 */
	public void setPosition_PieceOrderNo(String arg)
	{
		wPosition_PieceOrderNo = arg;
	}

	//#CM697431
	/**
	 * Position_Result Piece Qty
	 * @param arg	Position_Result Piece Qty
	 */
	public void setPosition_PieceResultQty(String arg)
	{
		wPosition_PieceResultQty = arg;
	}

	//#CM697432
	/**
	 * Position_Piece Picking Location
	 * @param arg	Position_Piece Picking Location
	 */
	public void setPosition_PieceRetrievalLocation(String arg)
	{
		wPosition_PieceRetrievalLocation = arg;
	}

	//#CM697433
	/**
	 * Position_Result division
	 * @param arg	Position_Result division
	 */
	public void setPosition_ResultFlag(String arg)
	{
		wPosition_ResultFlag = arg;
	}

	//#CM697434
	/**
	 * Position_Planned Retrieval Date
	 * @param arg	Position_Planned Retrieval Date
	 */
	public void setPosition_RetrievalPlanDate(String arg)
	{
		wPosition_RetrievalPlanDate = arg;
	}

	//#CM697435
	/**
	 * Position_Picking Qty (Total Bulk Qty)
	 * @param arg	Position_Picking Qty (Total Bulk Qty)
	 */
	public void setPosition_RetrievalPlanQty(String arg)
	{
		wPosition_RetrievalPlanQty = arg;
	}

	//#CM697436
	/**
	 * Position_Picking Result Date
	 * @param arg	Position_Picking Result Date
	 */
	public void setPosition_RetrievalWorkDate(String arg)
	{
		wPosition_RetrievalWorkDate = arg;
	}

	//#CM697437
	/**
	 * Position_Ticket No.
	 * @param arg	Position_Ticket No.
	 */
	public void setPosition_TicketNo(String arg)
	{
		wPosition_TicketNo = arg;
	}

	//#CM697438
	/**
	 * Position_Expiry Date
	 * @param arg	Position_Expiry Date
	 */
	public void setPosition_UseByDate(String arg)
	{
		wPosition_UseByDate = arg;
	}

	//#CM697439
	/**
	 * Enabled_Packed qty per bundle
	 * @param arg	Enabled_Packed qty per bundle
	 */
	public void setValid_BundleEnteringQty(boolean arg)
	{
		wValid_BundleEnteringQty = arg;
	}

	//#CM697440
	/**
	 * Enabled_Bundle ITF
	 * @param arg	Enabled_Bundle ITF
	 */
	public void setValid_BundleItf(boolean arg)
	{
		wValid_BundleItf = arg;
	}

	//#CM697441
	/**
	 * Enabled_Case Order No.
	 * @param arg	Enabled_Case Order No.
	 */
	public void setValid_CaseOrderNo(boolean arg)
	{
		wValid_CaseOrderNo = arg;
	}

	//#CM697442
	/**
	 * Enabled_Result Case Qty
	 * @param arg	Enabled_Result Case Qty
	 */
	public void setValid_CaseResultQty(boolean arg)
	{
		wValid_CaseResultQty = arg;
	}

	//#CM697443
	/**
	 * Enabled_Case Picking Location
	 * @param arg	Enabled_Case Picking Location
	 */
	public void setValid_CaseRetrievalLocation(boolean arg)
	{
		wValid_CaseRetrievalLocation = arg;
	}

	//#CM697444
	/**
	 * Enabled_Consignor Code
	 * @param arg	Enabled_Consignor Code
	 */
	public void setValid_ConsignorCode(boolean arg)
	{
		wValid_ConsignorCode = arg;
	}

	//#CM697445
	/**
	 * Enabled_Consignor Name
	 * @param arg	Enabled_Consignor Name
	 */
	public void setValid_ConsignorName(boolean arg)
	{
		wValid_ConsignorName = arg;
	}

	//#CM697446
	/**
	 * Enabled_Customer Code
	 * @param arg	Enabled_Customer Code
	 */
	public void setValid_CustomerCode(boolean arg)
	{
		wValid_CustomerCode = arg;
	}

	//#CM697447
	/**
	 * Enabled_Customer Name
	 * @param arg	Enabled_Customer Name
	 */
	public void setValid_CustomerName(boolean arg)
	{
		wValid_CustomerName = arg;
	}

	//#CM697448
	/**
	 * Enabled_Packed Qty per Case
	 * @param arg	Enabled_Packed Qty per Case
	 */
	public void setValid_EnteringQty(boolean arg)
	{
		wValid_EnteringQty = arg;
	}

	//#CM697449
	/**
	 * Enabled_Item Code
	 * @param arg	Enabled_Item Code
	 */
	public void setValid_ItemCode(boolean arg)
	{
		wValid_ItemCode = arg;
	}

	//#CM697450
	/**
	 * Enabled_Item Name
	 * @param arg	Enabled_Item Name
	 */
	public void setValid_ItemName(boolean arg)
	{
		wValid_ItemName = arg;
	}

	//#CM697451
	/**
	 * Enabled_Case ITF
	 * @param arg	Enabled_Case ITF
	 */
	public void setValid_Itf(boolean arg)
	{
		wValid_Itf = arg;
	}

	//#CM697452
	/**
	 * Enabled_Ticket Line No.
	 * @param arg	Enabled_Ticket Line No.
	 */
	public void setValid_LineNo(boolean arg)
	{
		wValid_LineNo = arg;
	}

	//#CM697453
	/**
	 * Enabled_Piece Order No.
	 * @param arg	Enabled_Piece Order No.
	 */
	public void setValid_PieceOrderNo(boolean arg)
	{
		wValid_PieceOrderNo = arg;
	}

	//#CM697454
	/**
	 * Enabled_Result Piece Qty
	 * @param arg	Enabled_Result Piece Qty
	 */
	public void setValid_PieceResultQty(boolean arg)
	{
		wValid_PieceResultQty = arg;
	}

	//#CM697455
	/**
	 * Enabled_Piece Picking Location
	 * @param arg	Enabled_Piece Picking Location
	 */
	public void setValid_PieceRetrievalLocation(boolean arg)
	{
		wValid_PieceRetrievalLocation = arg;
	}

	//#CM697456
	/**
	 * Enabled_Result division
	 * @param arg	Enabled_Result division
	 */
	public void setValid_ResultFlag(boolean arg)
	{
		wValid_ResultFlag = arg;
	}

	//#CM697457
	/**
	 * Enabled_Planned Retrieval Date
	 * @param arg	Enabled_Planned Retrieval Date
	 */
	public void setValid_RetrievalPlanDate(boolean arg)
	{
		wValid_RetrievalPlanDate = arg;
	}

	//#CM697458
	/**
	 * Enabled_Picking Qty (Total Bulk Qty)
	 * @param arg	Enabled_Picking Qty (Total Bulk Qty)
	 */
	public void setValid_RetrievalPlanQty(boolean arg)
	{
		wValid_RetrievalPlanQty = arg;
	}

	//#CM697459
	/**
	 * Enabled_Picking Result Date
	 * @param arg	Enabled_Picking Result Date
	 */
	public void setValid_RetrievalWorkDate(boolean arg)
	{
		wValid_RetrievalWorkDate = arg;
	}

	//#CM697460
	/**
	 * Enabled_Ticket No.
	 * @param arg	Enabled_Ticket No.
	 */
	public void setValid_TicketNo(boolean arg)
	{
		wValid_TicketNo = arg;
	}

	//#CM697461
	/**
	 * Enabled_Expiry Date
	 * @param arg	Enabled_Expiry Date
	 */
	public void setValid_UseByDate(boolean arg)
	{
		wValid_UseByDate = arg;
	}
}
//#CM697462
//end of class
