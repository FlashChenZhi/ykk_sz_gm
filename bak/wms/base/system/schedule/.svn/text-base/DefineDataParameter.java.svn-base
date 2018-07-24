//#CM696088
//$Id: DefineDataParameter.java,v 1.2 2006/11/13 06:03:13 suresh Exp $

//#CM696089
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;
//#CM696090
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * Use the <CODE>DefineDataParameter</CODE> class to pass a parameter between the screen and the schedule for setting field items to be loaded or reported for each package.<BR>
 * Allow this class to maintain the field items to be used in each screen for system package. Use a field item depending on the screen.<BR>
 * <BR>
 * <DIR>
 * Allow the <CODE>DefineShippingDataParameter</CODE> class to maintain the following field items.<BR>
 * <BR>
 * [Textbox or Label] <BR>
 * <DIR>
 *     Worker Code <BR>
 *     Password <BR>
 * <BR>
 *     Length_Planned Date <BR>
 *     Length_Order Date <BR>
 *     Length_Consignor Code <BR>
 *     Length_Consignor Name <BR>
 *     Length_Customer Code <BR>
 *     Length_Customer Name <BR>
 *     Length_Shipping Ticket No <BR>
 *     Length_Shipping ticket line No. <BR>
 *     Length_Item Code <BR>
 *     Length_Bundle ITF <BR>
 *     Length_Case ITF <BR>
 *     Length_Packed qty per bundle <BR>
 *     Length_Packed Qty per Case <BR>
 *     Length_Item Name <BR>
 *     Length_System Stock Qty (Total Bulk Qty) <BR>
 *     Length_Planned Qty (Total Bulk Qty) <BR>
 *     Length_Piece Location <BR>
 *     Length_Case Location <BR>
 *     Length_Relocation Source Location (Inventory Check Location) <BR>
 *     Length_Relocation Target Location <BR>
 *     Length_Piece Order No. <BR>
 *     Length_Case Order No. <BR>
 *     Length_TC/DC division <BR>
 *     Length_Supplier Code <BR>
 *     Length_Supplier Name <BR>
 *     Length_Receiving Ticket No. <BR>
 *     Length_Receiving Ticket Line No. <BR>
 *     Length_Result Qty (Total Bulk Qty) <BR>
 *     Length_Result Piece Qty <BR>
 *     Length_Result Case Qty <BR>
 *     Length_Result Date <BR> 
 *     Length_Result division <BR>
 *     Length_Expiry Date <BR>
 * <BR>
 *     Max Length_Planned Date <BR>
 *     Max Length_Order Date <BR>
 *     Max Length_Consignor Code <BR>
 *     Max Length_Consignor Name <BR>
 *     Max Length_Customer Code <BR>
 *     Max Length_Customer Name <BR>
 *     Max Length_Shipping Ticket No <BR>
 *     Max Length_Shipping ticket line No. <BR>
 *     Max Length_Item Code <BR>
 *     Max Length_Bundle ITF <BR>
 *     Max Length_Case ITF <BR>
 *     Max Length_Packed qty per bundle <BR>
 *     Max Length_Packed Qty per Case <BR>
 *     Max Length_Item Name <BR>
 *     Max Length_System Stock Qty (Total Bulk Qty) <BR>
 *     Max Length_Planned Qty (Total Bulk Qty) <BR>
 *     Max Length_Piece Location <BR>
 *     Max Length_Case Location <BR>
 *     Max Length_Relocation Source Location (Inventory Check Location) <BR>
 *     Max Length_Relocation Target Location <BR>
 *     Max Length_Piece Order No. <BR>
 *     Max Length_Case Order No. <BR>
 *     Max Length_TC/DC division <BR>
 *     Max Length_Supplier Code <BR>
 *     Max Length_Supplier Name <BR>
 *     Max Length_Receiving Ticket No. <BR>
 *     Max Length_Receiving Ticket Line No. <BR>
 *     Max Length_Result Qty (Total Bulk Qty) <BR>
 *     Max Length_Result Piece Qty <BR>
 *     Max Length_Result Case Qty <BR>
 *     Max Length_Result Date <BR> 
 *     Max Length_Result division <BR>
 *     Max Length_Expiry Date <BR>
 * <BR>
 *     Position_Planned Date <BR>
 *     Position_Order Date <BR>
 *     Position_Consignor Code <BR>
 *     Position_Consignor Name <BR>
 *     Position_Customer Code <BR>
 *     Position_Customer Name <BR>
 *     Position_Shipping Ticket No <BR>
 *     Position_Shipping ticket line No. <BR>
 *     Position_Item Code <BR>
 *     Position_Bundle ITF <BR>
 *     Position_Case ITF <BR>
 *     Position_Packed qty per bundle <BR>
 *     Position_Packed Qty per Case <BR>
 *     Position_Item Name <BR>
 *     Position_System Stock Qty (Total Bulk Qty) <BR>
 *     Position_Planned Qty (Total Bulk Qty) <BR>
 *     Position_Piece Location <BR>
 *     Position_Case Location <BR>
 *     Position_Relocation Source Location (Inventory Check Location) <BR>
 *     Position_Relocation Target Location <BR>
 *     Position_Inventory Check Location <BR>
 *     Position_Piece Order No. <BR>
 *     Position_Case Order No. <BR>
 *     Position_TC/DC division <BR>
 *     Position_Supplier Code <BR>
 *     Position_Supplier Name <BR>
 *     Position_Receiving Ticket No. <BR>
 *     Position_Receiving Ticket Line No. <BR>
 *     Position_Result Qty (Total Bulk Qty) <BR>
 *     Position_Result Piece Qty <BR>
 *     Position_Result Case Qty <BR>
 *     Position_Result Date <BR> 
 *     Position_Result division <BR>
 *     Position_Expiry Date <BR>
 * </DIR>
 * [Checkbox] <BR>
 * <DIR>
 *     Enabled_Planned Date <BR>
 *     Enabled_Order Date <BR>
 *     Enabled_Consignor Code <BR>
 *     Enabled_Consignor Name <BR>
 *     Enabled_Customer Code <BR>
 *     Enabled_Customer Name <BR>
 *     Enabled_Shipping Ticket No <BR>
 *     Enabled_Shipping ticket line No. <BR>
 *     Enabled_Item Code <BR>
 *     Enabled_Bundle ITF <BR>
 *     Enabled_Case ITF <BR>
 *     Enabled_Packed qty per bundle <BR>
 *     Enabled_Packed Qty per Case <BR>
 *     Enabled_Item Name <BR>
 *     Enabled_System Stock Qty (Total Bulk Qty) <BR>
 *     Enabled_Planned Qty (Total Bulk Qty) <BR>
 *     Enabled_Piece Location <BR>
 *     Enabled_Case Location <BR>
 *     Enabled_Relocation Source Location (Inventory Check Location) <BR>
 *     Enabled_Relocation Target Location <BR>
 *     Enabled_Inventory Check Location <BR>
 *     Enabled_Piece Order No. <BR>
 *     Enabled_Case Order No. <BR>
 *     Enabled_TC/DC division <BR>
 *     Enabled_Supplier Code <BR>
 *     Enabled_Supplier Name <BR>
 *     Enabled_Receiving Ticket No. <BR>
 *     Enabled_Receiving Ticket Line No. <BR>
 *     Enabled_Result Qty (Total Bulk Qty) <BR>
 *     Enabled_Result Piece Qty <BR>
 *     Enabled_Result Case Qty <BR>
 *     Enabled_Result Date <BR> 
 *     Enabled_Result division <BR>
 *     Enabled_Expiry Date <BR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/05</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:13 $
 * @author  $Author: suresh $
 */
public class DefineDataParameter extends SystemParameter
{
	//#CM696091
	// Class variables -----------------------------------------------
	//#CM696092
	/**
	 * Worker Code
	 */
	private String wWorkerCode = "";
	
	//#CM696093
	/**
	 * Password
	 */
	private String wPassword = "";
	
	//#CM696094
	/**
	 * Length_Planned Date
	 */
	private String wFigure_PlanDate = "";

	//#CM696095
	/**
	 * Length_Order Date
	 */
	private String wFigure_OrderingDate = "";
	
	//#CM696096
	/**
	 * Length_Consignor Code
	 */
	private String wFigure_ConsignorCode = "";
	
	//#CM696097
	/**
	 * Length_Consignor Name
	 */
	private String wFigure_ConsignorName = "";
	
	//#CM696098
	/**
	 * Length_Customer Code
	 */
	private String wFigure_CustomerCode = "";
	
	//#CM696099
	/**
	 * Length_Customer Name
	 */
	private String wFigure_CustomerName = "";
	
	//#CM696100
	/**
	 * Length_Shipping Ticket No
	 */
	private String wFigure_ShippingTicketNo = "";
	
	//#CM696101
	/**
	 * Length_Shipping ticket line No.
	 */
	private String wFigure_ShippingLineNo = "";
	
	//#CM696102
	/**
	 * Length_Item Code
	 */
	private String wFigure_ItemCode = "";
	
	//#CM696103
	/**
	 * Length_Bundle ITF
	 */
	private String wFigure_BundleItf = "";
	
	//#CM696104
	/**
	 * Length_Case ITF
	 */
	private String wFigure_Itf = "";
	
	//#CM696105
	/**
	 * Length_Packed qty per bundle
	 */
	private String wFigure_BundleEnteringQty = "";
	
	//#CM696106
	/**
	 * Length_Packed Qty per Case
	 */
	private String wFigure_EnteringQty = "";
	
	//#CM696107
	/**
	 * Length_Item Name
	 */
	private String wFigure_ItemName = "";
	
	//#CM696108
	/**
	 * Length_System Stock Qty (Total Bulk Qty)
	 */
	private String wFigure_StockQty = "";

	//#CM696109
	/**
	 * Length_Planned Qty (Total Bulk Qty)
	 */
	private String wFigure_PlanQty = "";
	
	//#CM696110
	/**
	 * Length_Piece Location
	 */
	private String wFigure_PieceLocation = "";
	
	//#CM696111
	/**
	 * Length_Case Location
	 */
	private String wFigure_CaseLocation = "";
	
	//#CM696112
	/**
	 * Length_Relocation Source Location (Inventory Check Location)
	 */
	private String wFigure_LocationNo = "";
	
	//#CM696113
	/**
	 * Length_Relocation Target Location
	 */
	private String wFigure_ResultLocationNo = "";
	
	//#CM696114
	/**
	 * Length_Piece Order No.
	 */
	private String wFigure_PieceOrderNo = "";
	
	//#CM696115
	/**
	 * Length_Case Order No.
	 */
	private String wFigure_CaseOrderNo = "";
	
	//#CM696116
	/**
	 * Length_TC/DC division
	 */
	private String wFigure_TcDcFlag = "";

	//#CM696117
	/**
	 * Length_Supplier Code
	 */
	private String wFigure_SupplierCode = "";
	
	//#CM696118
	/**
	 * Length_Supplier Name
	 */
	private String wFigure_SupplierName = "";
	
	//#CM696119
	/**
	 * Length_Receiving Ticket No.
	 */
	private String wFigure_InstockTicketNo = "";
	
	//#CM696120
	/**
	 * Length_Receiving Ticket Line No.
	 */
	private String wFigure_InstockLineNo = "";
	
	//#CM696121
	/**
	 * Length_Result Qty (Total Bulk Qty)
	 */
	private String wFigure_ResultQty = "";
	
	//#CM696122
	/**
	 * Length_Result Piece Qty
	 */
	private String wFigure_PieceResultQty = "";
	
	//#CM696123
	/**
	 * Length_Result Case Qty
	 */
	private String wFigure_CaseResultQty = "";
	
	//#CM696124
	/**
	 * Length_Result Date
	 */
	private String wFigure_WorkDate = "";
	
	//#CM696125
	/**
	 * Length_Result division
	 */
	private String wFigure_ResultFlag = "";
	
	//#CM696126
	/**
	 * Length_Expiry Date
	 */
	private String wFigure_UseByDate = "";
	
	//#CM696127
	/**
	 * Max Length_Planned Date
	 */
	private String wMaxFigure_PlanDate = "";

	//#CM696128
	/**
	 * Max Length_Order Date
	 */
	private String wMaxFigure_OrderingDate = "";
	
	//#CM696129
	/**
	 * Max Length_Consignor Code
	 */
	private String wMaxFigure_ConsignorCode = "";
	
	//#CM696130
	/**
	 * Max Length_Consignor Name
	 */
	private String wMaxFigure_ConsignorName = "";
	
	//#CM696131
	/**
	 * Max Length_Customer Code
	 */
	private String wMaxFigure_CustomerCode = "";
	
	//#CM696132
	/**
	 * Max Length_Customer Name
	 */
	private String wMaxFigure_CustomerName = "";
	
	//#CM696133
	/**
	 * Max Length_Shipping Ticket No
	 */
	private String wMaxFigure_ShippingTicketNo = "";
	
	//#CM696134
	/**
	 * Max Length_Shipping ticket line No.
	 */
	private String wMaxFigure_ShippingLineNo = "";
	
	//#CM696135
	/**
	 * Max Length_Item Code
	 */
	private String wMaxFigure_ItemCode = "";
	
	//#CM696136
	/**
	 * Max Length_Bundle ITF
	 */
	private String wMaxFigure_BundleItf = "";
	
	//#CM696137
	/**
	 * Max Length_Case ITF
	 */
	private String wMaxFigure_Itf = "";
	
	//#CM696138
	/**
	 * Max Length_Packed qty per bundle
	 */
	private String wMaxFigure_BundleEnteringQty = "";
	
	//#CM696139
	/**
	 * Max Length_Packed Qty per Case
	 */
	private String wMaxFigure_EnteringQty = "";
	
	//#CM696140
	/**
	 * Max Length_Item Name
	 */
	private String wMaxFigure_ItemName = "";
	
	//#CM696141
	/**
	 * Max Length_System Stock Qty (Total Bulk Qty)
	 */
	private String wMaxFigure_StockQty = "";
	
	//#CM696142
	/**
	 * Max Length_Planned Qty (Total Bulk Qty)
	 */
	private String wMaxFigure_PlanQty = "";
	
	//#CM696143
	/**
	 * Max Length_Piece Location
	 */
	private String wMaxFigure_PieceLocation = "";
	
	//#CM696144
	/**
	 * Max Length_Case Location
	 */
	private String wMaxFigure_CaseLocation = "";
	
	//#CM696145
	/**
	 * Max Length_Relocation Source Location (Inventory Check)
	 */
	private String wMaxFigure_LocationNo = "";
	
	//#CM696146
	/**
	 * Max Length_Relocation Target Location
	 */
	private String wMaxFigure_ResultLocationNo = "";
	
	//#CM696147
	/**
	 * Max Length_Piece Order No.
	 */
	private String wMaxFigure_PieceOrderNo = "";
	
	//#CM696148
	/**
	 * Max Length_Case Order No.
	 */
	private String wMaxFigure_CaseOrderNo = "";
	
	//#CM696149
	/**
	 * Max Length_TC/DC division
	 */
	private String wMaxFigure_TcDcFlag = "";

	//#CM696150
	/**
	 * Max Length_Supplier Code
	 */
	private String wMaxFigure_SupplierCode = "";
	
	//#CM696151
	/**
	 * Max Length_Supplier Name
	 */
	private String wMaxFigure_SupplierName = "";
	
	//#CM696152
	/**
	 * Max Length_Receiving Ticket No.
	 */
	private String wMaxFigure_InstockTicketNo = "";
	
	//#CM696153
	/**
	 * Max Length_Receiving Ticket Line No.
	 */
	private String wMaxFigure_InstockLineNo = "";
	
	//#CM696154
	/**
	 * Max Length_Result Qty (Total Bulk Qty)
	 */
	private String wMaxFigure_ResultQty = "";
	
	//#CM696155
	/**
	 * Max Length_Result Piece Qty
	 */
	private String wMaxFigure_PieceResultQty = "";
	
	//#CM696156
	/**
	 * Max Length_Result Case Qty
	 */
	private String wMaxFigure_CaseResultQty = "";
	
	//#CM696157
	/**
	 * Max Length_Result Date
	 */
	private String wMaxFigure_WorkDate = "";
	
	//#CM696158
	/**
	 * Max Length_Result division
	 */
	private String wMaxFigure_ResultFlag = "";
	
	//#CM696159
	/**
	 * Max Length_Expiry Date
	 */
	private String wMaxFigure_UseByDate = "";
	
	
	//#CM696160
	/**
	 * Position_Planned Date
	 */
	private String wPosition_PlanDate = "";

	//#CM696161
	/**
	 * Position_Order Date
	 */
	private String wPosition_OrderingDate = "";
	
	//#CM696162
	/**
	 * Position_Consignor Code
	 */
	private String wPosition_ConsignorCode = "";
	
	//#CM696163
	/**
	 * Position_Consignor Name
	 */
	private String wPosition_ConsignorName = "";
	
	//#CM696164
	/**
	 * Position_Customer Code
	 */
	private String wPosition_CustomerCode = "";
	
	//#CM696165
	/**
	 * Position_Customer Name
	 */
	private String wPosition_CustomerName = "";
	
	//#CM696166
	/**
	 * Position_Shipping Ticket No
	 */
	private String wPosition_ShippingTicketNo = "";
	
	//#CM696167
	/**
	 * Position_Shipping ticket line No.
	 */
	private String wPosition_ShippingLineNo = "";
	
	//#CM696168
	/**
	 * Position_Item Code
	 */
	private String wPosition_ItemCode = "";
	
	//#CM696169
	/**
	 * Position_Bundle ITF
	 */
	private String wPosition_BundleItf = "";
	
	//#CM696170
	/**
	 * Position_Case ITF
	 */
	private String wPosition_Itf = "";
	
	//#CM696171
	/**
	 * Position_Packed qty per bundle
	 */
	private String wPosition_BundleEnteringQty = "";
	
	//#CM696172
	/**
	 * Position_Packed Qty per Case
	 */
	private String wPosition_EnteringQty = "";
	
	//#CM696173
	/**
	 * Position_Item Name
	 */
	private String wPosition_ItemName = "";
	
	//#CM696174
	/**
	 * Position_System Stock Qty (Total Bulk Qty)
	 */
	private String wPosition_StockQty = "";
	
	//#CM696175
	/**
	 * Position_Planned Qty (Total Bulk Qty)
	 */
	private String wPosition_PlanQty = "";
	
	//#CM696176
	/**
	 * Position_Piece Location
	 */
	private String wPosition_PieceLocation = "";
	
	//#CM696177
	/**
	 * Position_Case Location
	 */
	private String wPosition_CaseLocation = "";
	
	//#CM696178
	/**
	 * Position_Relocation Source Location (Inventory Check)
	 */
	private String wPosition_LocationNo = "";
	
	//#CM696179
	/**
	 * Position_Relocation Target Location
	 */
	private String wPosition_ResultLocationNo = "";
	
	//#CM696180
	/**
	 * Position_Piece Order No.
	 */
	private String wPosition_PieceOrderNo = "";
	
	//#CM696181
	/**
	 * Position_Case Order No.
	 */
	private String wPosition_CaseOrderNo = "";
	
	//#CM696182
	/**
	 * Position_TC/DC division
	 */
	private String wPosition_TcDcFlag = "";

	//#CM696183
	/**
	 * Position_Supplier Code
	 */
	private String wPosition_SupplierCode = "";
	
	//#CM696184
	/**
	 * Position_Supplier Name
	 */
	private String wPosition_SupplierName = "";
	
	//#CM696185
	/**
	 * Position_Receiving Ticket No.
	 */
	private String wPosition_InstockTicketNo = "";
	
	//#CM696186
	/**
	 * Position_Receiving Ticket Line No.
	 */
	private String wPosition_InstockLineNo = "";
	
	//#CM696187
	/**
	 * Position_Result Qty (Total Bulk Qty)
	 */
	private String wPosition_ResultQty = "";
	
	//#CM696188
	/**
	 * Position_Result Piece Qty
	 */
	private String wPosition_PieceResultQty = "";
	
	//#CM696189
	/**
	 * Position_Result Case Qty
	 */
	private String wPosition_CaseResultQty = "";
	
	//#CM696190
	/**
	 * Position_Result Date
	 */
	private String wPosition_WorkDate = "";
	
	//#CM696191
	/**
	 * Position_Result division
	 */
	private String wPosition_ResultFlag = "";
	
	//#CM696192
	/**
	 * Position_Expiry Date
	 */
	private String wPosition_UseByDate = "";
	
	//#CM696193
	/**
	 * Enabled_Planned Date
	 */
	private boolean wValid_PlanDate;

	//#CM696194
	/**
	 * Enabled_Order Date
	 */
	private boolean wValid_OrderingDate;
	
	//#CM696195
	/**
	 * Enabled_Consignor Code
	 */
	private boolean wValid_ConsignorCode;
	
	//#CM696196
	/**
	 * Enabled_Consignor Name
	 */
	private boolean wValid_ConsignorName;
	
	//#CM696197
	/**
	 * Enabled_Customer Code
	 */
	private boolean wValid_CustomerCode;
	
	//#CM696198
	/**
	 * Enabled_Customer Name
	 */
	private boolean wValid_CustomerName;
	
	//#CM696199
	/**
	 * Enabled_Shipping Ticket No
	 */
	private boolean wValid_ShippingTicketNo;
	
	//#CM696200
	/**
	 * Enabled_Shipping ticket line No.
	 */
	private boolean wValid_ShippingLineNo;
	
	//#CM696201
	/**
	 * Enabled_Item Code
	 */
	private boolean wValid_ItemCode;
	
	//#CM696202
	/**
	 * Enabled_Bundle ITF
	 */
	private boolean wValid_BundleItf;
	
	//#CM696203
	/**
	 * Enabled_Case ITF
	 */
	private boolean wValid_Itf;
	
	//#CM696204
	/**
	 * Enabled_Packed qty per bundle
	 */
	private boolean wValid_BundleEnteringQty;
	
	//#CM696205
	/**
	 * Enabled_Packed Qty per Case
	 */
	private boolean wValid_EnteringQty;
	
	//#CM696206
	/**
	 * Enabled_Item Name
	 */
	private boolean wValid_ItemName;
	
	//#CM696207
	/**
	 * Enabled_System Stock Qty (Total Bulk Qty)
	 */
	private boolean wValid_StockQty;
	
	//#CM696208
	/**
	 * Enabled_Planned Qty (Total Bulk Qty)
	 */
	private boolean wValid_PlanQty;
	
	//#CM696209
	/**
	 * Enabled_Piece Location
	 */
	private boolean wValid_PieceLocation;
	
	//#CM696210
	/**
	 * Enabled_Case Location
	 */
	private boolean wValid_CaseLocation;
	
	//#CM696211
	/**
	 * Enabled_Relocation Source Location (Inventory Check)
	 */
	private boolean wValid_LocationNo;
	
	//#CM696212
	/**
	 * Enabled_Relocation Target Location
	 */
	private boolean wValid_ResultLocationNo;
	
	//#CM696213
	/**
	 * Enabled_Piece Order No.
	 */
	private boolean wValid_PieceOrderNo;
	
	//#CM696214
	/**
	 * Enabled_Case Order No.
	 */
	private boolean wValid_CaseOrderNo;
	
	//#CM696215
	/**
	 * Enabled_TC/DC division
	 */
	private boolean wValid_TcDcFlag;

	//#CM696216
	/**
	 * Enabled_Supplier Code
	 */
	private boolean wValid_SupplierCode;
	
	//#CM696217
	/**
	 * Enabled_Supplier Name
	 */
	private boolean wValid_SupplierName;
	
	//#CM696218
	/**
	 * Enabled_Receiving Ticket No.
	 */
	private boolean wValid_InstockTicketNo;
	
	//#CM696219
	/**
	 * Enabled_Receiving Ticket Line No.
	 */
	private boolean wValid_InstockLineNo;
	
	//#CM696220
	/**
	 * Enabled_Result Qty (Total Bulk Qty)
	 */
	private boolean wValid_ResultQty;
	
	//#CM696221
	/**
	 * Enabled_Result Piece Qty
	 */
	private boolean wValid_PieceResultQty;
	
	//#CM696222
	/**
	 * Enabled_Result Case Qty
	 */
	private boolean wValid_CaseResultQty;
	
	//#CM696223
	/**
	 * Enabled_Result Date
	 */
	private boolean wValid_WorkDate;
	
	//#CM696224
	/**
	 * Enabled_Result division
	 */
	private boolean wValid_ResultFlag;
	
	//#CM696225
	/**
	 * Enabled_Expiry Date
	 */
	private boolean wValid_UseByDate;
	

	//#CM696226
	// Class method --------------------------------------------------
	//#CM696227
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:13 $");
	}

	//#CM696228
	// Constructors --------------------------------------------------
	//#CM696229
	/**
	 * Initialize this class.
	 */
	public DefineDataParameter()
	{
	}
	
	//#CM696230
	// Public methods ------------------------------------------------

	//#CM696231
	/**
	 * Worker Code
	 * @return	Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM696232
	/**
	 * Password
	 * @return	Password
	 */
	public String getPassword()
	{
		return wPassword;
	}


	//#CM696233
	/**
	 * Length_Packed qty per bundle
	 * @return	Length_Packed qty per bundle
	 */
	public String getFigure_BundleEnteringQty()
	{
		return wFigure_BundleEnteringQty;
	}

	//#CM696234
	/**
	 * Length_Bundle ITF
	 * @return	Length_Bundle ITF
	 */
	public String getFigure_BundleItf()
	{
		return wFigure_BundleItf;
	}

	//#CM696235
	/**
	 * Length_Consignor Code
	 * @return	Length_Consignor Code
	 */
	public String getFigure_ConsignorCode()
	{
		return wFigure_ConsignorCode;
	}

	//#CM696236
	/**
	 * Length_Consignor Name
	 * @return	Length_Consignor Name
	 */
	public String getFigure_ConsignorName()
	{
		return wFigure_ConsignorName;
	}

	//#CM696237
	/**
	 * Length_Customer Code
	 * @return	Length_Customer Code
	 */
	public String getFigure_CustomerCode()
	{
		return wFigure_CustomerCode;
	}

	//#CM696238
	/**
	 * Length_Customer Name
	 * @return	Length_Customer Name
	 */
	public String getFigure_CustomerName()
	{
		return wFigure_CustomerName;
	}

	//#CM696239
	/**
	 * Length_Packed Qty per Case
	 * @return	Length_Packed Qty per Case
	 */
	public String getFigure_EnteringQty()
	{
		return wFigure_EnteringQty;
	}

	//#CM696240
	/**
	 * Length_Receiving Ticket Line No.
	 * @return	Length_Receiving Ticket Line No.
	 */
	public String getFigure_InstockLineNo()
	{
		return wFigure_InstockLineNo;
	}

	//#CM696241
	/**
	 * Length_Receiving Ticket No.
	 * @return	Length_Receiving Ticket No.
	 */
	public String getFigure_InstockTicketNo()
	{
		return wFigure_InstockTicketNo;
	}

	//#CM696242
	/**
	 * Length_Item Code
	 * @return	Length_Item Code
	 */
	public String getFigure_ItemCode()
	{
		return wFigure_ItemCode;
	}

	//#CM696243
	/**
	 * Length_Item Name
	 * @return	Length_Item Name
	 */
	public String getFigure_ItemName()
	{
		return wFigure_ItemName;
	}

	//#CM696244
	/**
	 * Length_System Stock Qty (Total Bulk Qty)
	 * @return	Length_System Stock Qty (Total Bulk Qty)
	 */
	public String getFigure_StockQty()
	{
		return wFigure_StockQty;
	}

	//#CM696245
	/**
	 * Length_Case ITF
	 * @return	Length_Case ITF
	 */
	public String getFigure_Itf()
	{
		return wFigure_Itf;
	}

	//#CM696246
	/**
	 * Length_Shipping ticket line No.
	 * @return	Length_Shipping ticket line No.
	 */
	public String getFigure_ShippingLineNo()
	{
		return wFigure_ShippingLineNo;
	}

	//#CM696247
	/**
	 * Length_Result division
	 * @return	Length_Result division
	 */
	public String getFigure_ResultFlag()
	{
		return wFigure_ResultFlag;
	}

	//#CM696248
	/**
	 * Length_Order Date
	 * @return	Length_Order Date
	 */
	public String getFigure_OrderingDate()
	{
		return wFigure_OrderingDate;
	}

	//#CM696249
	/**
	 * Length_Planned Date
	 * @return	Length_Planned Date
	 */
	public String getFigure_PlanDate()
	{
		return wFigure_PlanDate;
	}

	//#CM696250
	/**
	 * Length_Planned Qty (Total Bulk Qty)
	 * @return	Length_Planned Qty (Total Bulk Qty)
	 */
	public String getFigure_PlanQty()
	{
		return wFigure_PlanQty;
	}

	//#CM696251
	/**
	 * Length_Piece Location
	 * @return	Length_Piece Location
	 */
	public String getFigure_PieceLocation()
	{
		return wFigure_PieceLocation;
	}

	//#CM696252
	/**
	 * Length_Case Location
	 * @return	Length_Case Location
	 */
	public String getFigure_CaseLocation()
	{
		return wFigure_CaseLocation;
	}

	//#CM696253
	/**
	 * Length_Relocation Source Location (Inventory Check Location)
	 * @return	Length_Relocation Source Location (Inventory Check Location)
	 */
	public String getFigure_LocationNo()
	{
		return wFigure_LocationNo;
	}

	//#CM696254
	/**
	 * Length_Relocation Target Location
	 * @return	Length_Relocation Target Location
	 */
	public String getFigure_ResultLocationNo()
	{
		return wFigure_ResultLocationNo;
	}

	//#CM696255
	/**
	 * Length_Piece Order No.
	 * @return	Length_Piece Order No.
	 */
	public String getFigure_PieceOrderNo()
	{
		return wFigure_PieceOrderNo;
	}

	//#CM696256
	/**
	 * Length_Case Order No.
	 * @return	Length_Case Order No.
	 */
	public String getFigure_CaseOrderNo()
	{
		return wFigure_CaseOrderNo;
	}

	//#CM696257
	/**
	 * Length_Result Qty (Total Bulk Qty)
	 * @return	Length_Result Qty (Total Bulk Qty)
	 */
	public String getFigure_ResultQty()
	{
		return wFigure_ResultQty;
	}

	//#CM696258
	/**
	 * Length_Result Piece Qty
	 * @return	Length_Result Piece Qty
	 */
	public String getFigure_PieceResultQty()
	{
		return wFigure_PieceResultQty;
	}

	//#CM696259
	/**
	 * Length_Result Case Qty
	 * @return	Length_Result Case Qty
	 */
	public String getFigure_CaseResultQty()
	{
		return wFigure_CaseResultQty;
	}

	//#CM696260
	/**
	 * Length_Result Date
	 * @return	Length_Result Date
	 */
	public String getFigure_WorkDate()
	{
		return wFigure_WorkDate;
	}

	//#CM696261
	/**
	 * Length_Supplier Code
	 * @return	Length_Supplier Code
	 */
	public String getFigure_SupplierCode()
	{
		return wFigure_SupplierCode;
	}

	//#CM696262
	/**
	 * Length_Supplier Name
	 * @return	Length_Supplier Name
	 */
	public String getFigure_SupplierName()
	{
		return wFigure_SupplierName;
	}

	//#CM696263
	/**
	 * Length_TC/DC division
	 * @return	Length_TC/DC division
	 */
	public String getFigure_TcDcFlag()
	{
		return wFigure_TcDcFlag;
	}

	//#CM696264
	/**
	 * Length_Shipping Ticket No
	 * @return	Length_Shipping Ticket No
	 */
	public String getFigure_ShippingTicketNo()
	{
		return wFigure_ShippingTicketNo;
	}

	//#CM696265
	/**
	 * Length_Expiry Date
	 * @return	Length_Expiry Date
	 */
	public String getFigure_UseByDate()
	{
		return wFigure_UseByDate;
	}

	//#CM696266
	/**
	 * Max Length_Packed qty per bundle
	 * @return	Max Length_Packed qty per bundle
	 */
	public String getMaxFigure_BundleEnteringQty()
	{
		return wMaxFigure_BundleEnteringQty;
	}

	//#CM696267
	/**
	 * Max Length_Bundle ITF
	 * @return	Max Length_Bundle ITF
	 */
	public String getMaxFigure_BundleItf()
	{
		return wMaxFigure_BundleItf;
	}

	//#CM696268
	/**
	 * Max Length_Consignor Code
	 * @return	Max Length_Consignor Code
	 */
	public String getMaxFigure_ConsignorCode()
	{
		return wMaxFigure_ConsignorCode;
	}

	//#CM696269
	/**
	 * Max Length_Consignor Name
	 * @return	Max Length_Consignor Name
	 */
	public String getMaxFigure_ConsignorName()
	{
		return wMaxFigure_ConsignorName;
	}

	//#CM696270
	/**
	 * Max Length_Customer Code
	 * @return	Max Length_Customer Code
	 */
	public String getMaxFigure_CustomerCode()
	{
		return wMaxFigure_CustomerCode;
	}

	//#CM696271
	/**
	 * Max Length_Customer Name
	 * @return	Max Length_Customer Name
	 */
	public String getMaxFigure_CustomerName()
	{
		return wMaxFigure_CustomerName;
	}

	//#CM696272
	/**
	 * Max Length_Packed Qty per Case
	 * @return	Max Length_Packed Qty per Case
	 */
	public String getMaxFigure_EnteringQty()
	{
		return wMaxFigure_EnteringQty;
	}

	//#CM696273
	/**
	 * Max Length_Receiving Ticket Line No.
	 * @return	Max Length_Receiving Ticket Line No.
	 */
	public String getMaxFigure_InstockLineNo()
	{
		return wMaxFigure_InstockLineNo;
	}

	//#CM696274
	/**
	 * Max Length_Receiving Ticket No.
	 * @return	Max Length_Receiving Ticket No.
	 */
	public String getMaxFigure_InstockTicketNo()
	{
		return wMaxFigure_InstockTicketNo;
	}

	//#CM696275
	/**
	 * Max Length_Item Code
	 * @return	Max Length_Item Code
	 */
	public String getMaxFigure_ItemCode()
	{
		return wMaxFigure_ItemCode;
	}

	//#CM696276
	/**
	 * Max Length_Item Name
	 * @return	Max Length_Item Name
	 */
	public String getMaxFigure_ItemName()
	{
		return wMaxFigure_ItemName;
	}

	//#CM696277
	/**
	 * Max Length_System Stock Qty (Total Bulk Qty)
	 * @return	Max Length_System Stock Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_StockQty()
	{
		return wMaxFigure_StockQty;
	}

	//#CM696278
	/**
	 * Max Length_Case ITF
	 * @return	Max Length_Case ITF
	 */
	public String getMaxFigure_Itf()
	{
		return wMaxFigure_Itf;
	}

	//#CM696279
	/**
	 * Max Length_Shipping ticket line No.
	 * @return	Max Length_Shipping ticket line No.
	 */
	public String getMaxFigure_ShippingLineNo()
	{
		return wMaxFigure_ShippingLineNo;
	}

	//#CM696280
	/**
	 * Max Length_Result division
	 * @return	Max Length_Result division
	 */
	public String getMaxFigure_ResultFlag()
	{
		return wMaxFigure_ResultFlag;
	}

	//#CM696281
	/**
	 * Max Length_Order Date
	 * @return	Max Length_Order Date
	 */
	public String getMaxFigure_OrderingDate()
	{
		return wMaxFigure_OrderingDate;
	}

	//#CM696282
	/**
	 * Max Length_Planned Date
	 * @return	Max Length_Planned Date
	 */
	public String getMaxFigure_PlanDate()
	{
		return wMaxFigure_PlanDate;
	}

	//#CM696283
	/**
	 * Max Length_Planned Qty (Total Bulk Qty)
	 * @return	Max Length_Planned Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_PlanQty()
	{
		return wMaxFigure_PlanQty;
	}

	//#CM696284
	/**
	 * Max Length_Piece Picking Location
	 * @return	Max Length_Piece Picking Location
	 */
	public String getMaxFigure_PieceLocation()
	{
		return wMaxFigure_PieceLocation;
	}

	//#CM696285
	/**
	 * Max Length_Case Picking Location
	 * @return	Max Length_Case Picking Location
	 */
	public String getMaxFigure_CaseLocation()
	{
		return wMaxFigure_CaseLocation;
	}

	//#CM696286
	/**
	 * Max Length_Relocation Source Location
	 * @return	Max Length_Relocation Source Location
	 */
	public String getMaxFigure_LocationNo()
	{
		return wMaxFigure_LocationNo;
	}

	//#CM696287
	/**
	 * Max Length_Relocation Target Location
	 * @return	Max Length_Relocation Target Location
	 */
	public String getMaxFigure_ResultLocationNo()
	{
		return wMaxFigure_ResultLocationNo;
	}

	//#CM696288
	/**
	 * Max Length_Piece Order No.
	 * @return	Max Length_Piece Order No.
	 */
	public String getMaxFigure_PieceOrderNo()
	{
		return wMaxFigure_PieceOrderNo;
	}

	//#CM696289
	/**
	 * Max Length_Case Order No.
	 * @return	Max Length_Case Order No.
	 */
	public String getMaxFigure_CaseOrderNo()
	{
		return wMaxFigure_CaseOrderNo;
	}

	//#CM696290
	/**
	 * Max Length_Result Qty (Total Bulk Qty)
	 * @return	Max Length_Result Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_ResultQty()
	{
		return wMaxFigure_ResultQty;
	}

	//#CM696291
	/**
	 * Max Length_Result Piece Qty
	 * @return	Max Length_Result Piece Qty
	 */
	public String getMaxFigure_PieceResultQty()
	{
		return wMaxFigure_PieceResultQty;
	}

	//#CM696292
	/**
	 * Max Length_Result Case Qty
	 * @return	Max Length_Result Case Qty
	 */
	public String getMaxFigure_CaseResultQty()
	{
		return wMaxFigure_CaseResultQty;
	}

	//#CM696293
	/**
	 * Max Length_Result Date
	 * @return	Max Length_Result Date
	 */
	public String getMaxFigure_WorkDate()
	{
		return wMaxFigure_WorkDate;
	}

	//#CM696294
	/**
	 * Max Length_Supplier Code
	 * @return	Max Length_Supplier Code
	 */
	public String getMaxFigure_SupplierCode()
	{
		return wMaxFigure_SupplierCode;
	}

	//#CM696295
	/**
	 * Max Length_Supplier Name
	 * @return	Max Length_Supplier Name
	 */
	public String getMaxFigure_SupplierName()
	{
		return wMaxFigure_SupplierName;
	}

	//#CM696296
	/**
	 * Max Length_TC/DC division
	 * @return	Max Length_TC/DC division
	 */
	public String getMaxFigure_TcDcFlag()
	{
		return wMaxFigure_TcDcFlag;
	}

	//#CM696297
	/**
	 * Max Length_Shipping Ticket No
	 * @return	Max Length_Shipping Ticket No
	 */
	public String getMaxFigure_ShippingTicketNo()
	{
		return wMaxFigure_ShippingTicketNo;
	}

	//#CM696298
	/**
	 * Max Length_Expiry Date
	 * @return	Max Length_Expiry Date
	 */
	public String getMaxFigure_UseByDate()
	{
		return wMaxFigure_UseByDate;
	}

	//#CM696299
	/**
	 * Position_Packed qty per bundle
	 * @return	Position_Packed qty per bundle
	 */
	public String getPosition_BundleEnteringQty()
	{
		return wPosition_BundleEnteringQty;
	}

	//#CM696300
	/**
	 * Position_Bundle ITF
	 * @return	Position_Bundle ITF
	 */
	public String getPosition_BundleItf()
	{
		return wPosition_BundleItf;
	}

	//#CM696301
	/**
	 * Position_Consignor Code
	 * @return	Position_Consignor Code
	 */
	public String getPosition_ConsignorCode()
	{
		return wPosition_ConsignorCode;
	}

	//#CM696302
	/**
	 * Position_Consignor Name
	 * @return	Position_Consignor Name
	 */
	public String getPosition_ConsignorName()
	{
		return wPosition_ConsignorName;
	}

	//#CM696303
	/**
	 * Position_Customer Code
	 * @return	Position_Customer Code
	 */
	public String getPosition_CustomerCode()
	{
		return wPosition_CustomerCode;
	}

	//#CM696304
	/**
	 * Position_Customer Name
	 * @return	Position_Customer Name
	 */
	public String getPosition_CustomerName()
	{
		return wPosition_CustomerName;
	}

	//#CM696305
	/**
	 * Position_Packed Qty per Case
	 * @return	Position_Packed Qty per Case
	 */
	public String getPosition_EnteringQty()
	{
		return wPosition_EnteringQty;
	}

	//#CM696306
	/**
	 * Position_Receiving Ticket Line No.
	 * @return	Position_Receiving Ticket Line No.
	 */
	public String getPosition_InstockLineNo()
	{
		return wPosition_InstockLineNo;
	}

	//#CM696307
	/**
	 * Position_Receiving Ticket No.
	 * @return	Position_Receiving Ticket No.
	 */
	public String getPosition_InstockTicketNo()
	{
		return wPosition_InstockTicketNo;
	}

	//#CM696308
	/**
	 * Position_Item Code
	 * @return	Position_Item Code
	 */
	public String getPosition_ItemCode()
	{
		return wPosition_ItemCode;
	}

	//#CM696309
	/**
	 * Position_Item Name
	 * @return	Position_Item Name
	 */
	public String getPosition_ItemName()
	{
		return wPosition_ItemName;
	}

	//#CM696310
	/**
	 * Position_System Stock Qty (Total Bulk Qty)
	 * @return	Position_System Stock Qty (Total Bulk Qty)
	 */
	public String getPosition_StockQty()
	{
		return wPosition_StockQty;
	}

	//#CM696311
	/**
	 * Position_Case ITF
	 * @return	Position_Case ITF
	 */
	public String getPosition_Itf()
	{
		return wPosition_Itf;
	}

	//#CM696312
	/**
	 * Position_Shipping ticket line No.
	 * @return	Position_Shipping ticket line No.
	 */
	public String getPosition_ShippingLineNo()
	{
		return wPosition_ShippingLineNo;
	}

	//#CM696313
	/**
	 * Position_Result division
	 * @return	Position_Result division
	 */
	public String getPosition_ResultFlag()
	{
		return wPosition_ResultFlag;
	}

	//#CM696314
	/**
	 * Position_Order Date
	 * @return	Position_Order Date
	 */
	public String getPosition_OrderingDate()
	{
		return wPosition_OrderingDate;
	}

	//#CM696315
	/**
	 * Position_Planned Date
	 * @return	Position_Planned Date
	 */
	public String getPosition_PlanDate()
	{
		return wPosition_PlanDate;
	}

	//#CM696316
	/**
	 * Position_Planned Qty (Total Bulk Qty)
	 * @return	Position_Planned Qty (Total Bulk Qty)
	 */
	public String getPosition_PlanQty()
	{
		return wPosition_PlanQty;
	}

	//#CM696317
	/**
	 * Position_Piece Location
	 * @return	Position_Piece Location
	 */
	public String getPosition_PieceLocation()
	{
		return wPosition_PieceLocation;
	}

	//#CM696318
	/**
	 * Position_Case Location
	 * @return	Position_Case Location
	 */
	public String getPosition_CaseLocation()
	{
		return wPosition_CaseLocation;
	}

	//#CM696319
	/**
	 * Position_Relocation Source Location
	 * @return	Position_Relocation Source Location
	 */
	public String getPosition_LocationNo()
	{
		return wPosition_LocationNo;
	}

	//#CM696320
	/**
	 * Position_Relocation Target Location
	 * @return	Position_Relocation Target Location
	 */
	public String getPosition_ResultLocationNo()
	{
		return wPosition_ResultLocationNo;
	}

	//#CM696321
	/**
	 * Position_Piece Order No.
	 * @return	Position_Piece Order No.
	 */
	public String getPosition_PieceOrderNo()
	{
		return wPosition_PieceOrderNo;
	}

	//#CM696322
	/**
	 * Position_Case Order No.
	 * @return	Position_Case Order No.
	 */
	public String getPosition_CaseOrderNo()
	{
		return wPosition_CaseOrderNo;
	}

	//#CM696323
	/**
	 * Position_Result Qty (Total Bulk Qty)
	 * @return	Position_Result Qty (Total Bulk Qty)
	 */
	public String getPosition_ResultQty()
	{
		return wPosition_ResultQty;
	}

	//#CM696324
	/**
	 * Position_Result Piece Qty
	 * @return	Position_Result Piece Qty
	 */
	public String getPosition_PieceResultQty()
	{
		return wPosition_PieceResultQty;
	}

	//#CM696325
	/**
	 * Position_Result Case Qty
	 * @return	Position_Result Case Qty
	 */
	public String getPosition_CaseResultQty()
	{
		return wPosition_CaseResultQty;
	}

	//#CM696326
	/**
	 * Position_Result Date
	 * @return	Position_Result Date
	 */
	public String getPosition_WorkDate()
	{
		return wPosition_WorkDate;
	}

	//#CM696327
	/**
	 * Position_Supplier Code
	 * @return	Position_Supplier Code
	 */
	public String getPosition_SupplierCode()
	{
		return wPosition_SupplierCode;
	}

	//#CM696328
	/**
	 * Position_Supplier Name
	 * @return	Position_Supplier Name
	 */
	public String getPosition_SupplierName()
	{
		return wPosition_SupplierName;
	}

	//#CM696329
	/**
	 * Position_TC/DC division
	 * @return	Position_TC/DC division
	 */
	public String getPosition_TcDcFlag()
	{
		return wPosition_TcDcFlag;
	}

	//#CM696330
	/**
	 * Position_Shipping Ticket No
	 * @return	Position_Shipping Ticket No
	 */
	public String getPosition_ShippingTicketNo()
	{
		return wPosition_ShippingTicketNo;
	}

	//#CM696331
	/**
	 * Position_Expiry Date
	 * @return	Position_Expiry Date
	 */
	public String getPosition_UseByDate()
	{
		return wPosition_UseByDate;
	}

	//#CM696332
	/**
	 * Enabled_Packed qty per bundle
	 * @return	Enabled_Packed qty per bundle
	 */
	public boolean getValid_BundleEnteringQty()
	{
		return wValid_BundleEnteringQty;
	}

	//#CM696333
	/**
	 * Enabled_Bundle ITF
	 * @return	Enabled_Bundle ITF
	 */
	public boolean getValid_BundleItf()
	{
		return wValid_BundleItf;
	}

	//#CM696334
	/**
	 * Enabled_Consignor Code
	 * @return	Enabled_Consignor Code
	 */
	public boolean getValid_ConsignorCode()
	{
		return wValid_ConsignorCode;
	}

	//#CM696335
	/**
	 * Enabled_Consignor Name
	 * @return	Enabled_Consignor Name
	 */
	public boolean getValid_ConsignorName()
	{
		return wValid_ConsignorName;
	}

	//#CM696336
	/**
	 * Enabled_Customer Code
	 * @return	Enabled_Customer Code
	 */
	public boolean getValid_CustomerCode()
	{
		return wValid_CustomerCode;
	}

	//#CM696337
	/**
	 * Enabled_Customer Name
	 * @return	Enabled_Customer Name
	 */
	public boolean getValid_CustomerName()
	{
		return wValid_CustomerName;
	}

	//#CM696338
	/**
	 * Enabled_Packed Qty per Case
	 * @return	Enabled_Packed Qty per Case
	 */
	public boolean getValid_EnteringQty()
	{
		return wValid_EnteringQty;
	}

	//#CM696339
	/**
	 * Enabled_Receiving Ticket Line No.
	 * @return	Enabled_Receiving Ticket Line No.
	 */
	public boolean getValid_InstockLineNo()
	{
		return wValid_InstockLineNo;
	}

	//#CM696340
	/**
	 * Enabled_Receiving Ticket No.
	 * @return	Enabled_Receiving Ticket No.
	 */
	public boolean getValid_InstockTicketNo()
	{
		return wValid_InstockTicketNo;
	}

	//#CM696341
	/**
	 * Enabled_Item Code
	 * @return	Enabled_Item Code
	 */
	public boolean getValid_ItemCode()
	{
		return wValid_ItemCode;
	}

	//#CM696342
	/**
	 * Enabled_Item Name
	 * @return	Enabled_Item Name
	 */
	public boolean getValid_ItemName()
	{
		return wValid_ItemName;
	}

	//#CM696343
	/**
	 * Enabled_Case ITF
	 * @return	Enabled_Case ITF
	 */
	public boolean getValid_Itf()
	{
		return wValid_Itf;
	}

	//#CM696344
	/**
	 * Enabled_Shipping ticket line No.
	 * @return	Enabled_Shipping ticket line No.
	 */
	public boolean getValid_ShippingLineNo()
	{
		return wValid_ShippingLineNo;
	}

	//#CM696345
	/**
	 * Enabled_Result division
	 * @return	Enabled_Result division
	 */
	public boolean getValid_ResultFlag()
	{
		return wValid_ResultFlag;
	}

	//#CM696346
	/**
	 * Enabled_Order Date
	 * @return	Enabled_Order Date
	 */
	public boolean getValid_OrderingDate()
	{
		return wValid_OrderingDate;
	}

	//#CM696347
	/**
	 * Enabled_Planned Date
	 * @return	Enabled_Planned Date
	 */
	public boolean getValid_PlanDate()
	{
		return wValid_PlanDate;
	}

	//#CM696348
	/**
	 * Enabled_Planned Qty (Total Bulk Qty)
	 * @return	Enabled_Planned Qty (Total Bulk Qty)
	 */
	public boolean getValid_PlanQty()
	{
		return wValid_PlanQty;
	}

	//#CM696349
	/**
	 * Enabled_Piece Location
	 * @return	Enabled_Piece Location
	 */
	public boolean getValid_PieceLocation()
	{
		return wValid_PieceLocation;
	}

	//#CM696350
	/**
	 * Enabled_Case Location
	 * @return	Enabled_Case Location
	 */
	public boolean getValid_CaseLocation()
	{
		return wValid_CaseLocation;
	}

	//#CM696351
	/**
	 * Enabled_Relocation Source Location
	 * @return	Enabled_Relocation Source Location
	 */
	public boolean getValid_LocationNo()
	{
		return wValid_LocationNo;
	}

	//#CM696352
	/**
	 * Enabled_Relocation Target Location
	 * @return	Enabled_Relocation Target Location
	 */
	public boolean getValid_ResultLocationNo()
	{
		return wValid_ResultLocationNo;
	}

	//#CM696353
	/**
	 * Enabled_Piece Order No.
	 * @return	Enabled_Piece Order No.
	 */
	public boolean getValid_PieceOrderNo()
	{
		return wValid_PieceOrderNo;
	}

	//#CM696354
	/**
	 * Enabled_Case Order No.
	 * @return	Enabled_Case Order No.
	 */
	public boolean getValid_CaseOrderNo()
	{
		return wValid_CaseOrderNo;
	}

	//#CM696355
	/**
	 * Enabled_System Stock Qty (Total Bulk Qty)
	 * @return	Enabled_System Stock Qty (Total Bulk Qty)
	 */
	public boolean getValid_StockQty()
	{
		return wValid_StockQty;
	}

	//#CM696356
	/**
	 * Enabled_Result Qty (Total Bulk Qty)
	 * @return	Enabled_Result Qty (Total Bulk Qty)
	 */
	public boolean getValid_ResultQty()
	{
		return wValid_ResultQty;
	}

	//#CM696357
	/**
	 * Enabled_Result Piece Qty
	 * @return	Enabled_Result Piece Qty
	 */
	public boolean getValid_PieceResultQty()
	{
		return wValid_PieceResultQty;
	}

	//#CM696358
	/**
	 * Enabled_Result Case Qty
	 * @return	Enabled_Result Case Qty
	 */
	public boolean getValid_CaseResultQty()
	{
		return wValid_CaseResultQty;
	}

	//#CM696359
	/**
	 * Enabled_Result Date
	 * @return	Enabled_Result Date
	 */
	public boolean getValid_WorkDate()
	{
		return wValid_WorkDate;
	}

	//#CM696360
	/**
	 * Enabled_Supplier Code
	 * @return	Enabled_Supplier Code
	 */
	public boolean getValid_SupplierCode()
	{
		return wValid_SupplierCode;
	}

	//#CM696361
	/**
	 * Enabled_Supplier Name
	 * @return	Enabled_Supplier Name
	 */
	public boolean getValid_SupplierName()
	{
		return wValid_SupplierName;
	}

	//#CM696362
	/**
	 * Enabled_TC/DC division
	 * @return	Enabled_TC/DC division
	 */
	public boolean getValid_TcDcFlag()
	{
		return wValid_TcDcFlag;
	}

	//#CM696363
	/**
	 * Enabled_Shipping Ticket No
	 * @return	Enabled_Shipping Ticket No
	 */
	public boolean getValid_ShippingTicketNo()
	{
		return wValid_ShippingTicketNo;
	}

	//#CM696364
	/**
	 * Enabled_Expiry Date
	 * @return	Enabled_Expiry Date
	 */
	public boolean getValid_UseByDate()
	{
		return wValid_UseByDate;
	}

	//#CM696365
	/**
	 * Length_Packed qty per bundle
	 * @param arg	Length_Packed qty per bundle to be set
	 */
	public void setFigure_BundleEnteringQty(String arg)
	{
		wFigure_BundleEnteringQty = arg;
	}

	//#CM696366
	/**
	 * Length_Bundle ITF
	 * @param arg	Length_Bundle ITF to be set
	 */
	public void setFigure_BundleItf(String arg)
	{
		wFigure_BundleItf = arg;
	}

	//#CM696367
	/**
	 * Length_Consignor Code
	 * @param arg	Length_Consignor Code to be set
	 */
	public void setFigure_ConsignorCode(String arg)
	{
		wFigure_ConsignorCode = arg;
	}

	//#CM696368
	/**
	 * Length_Consignor Name
	 * @param arg	Length_Consignor Name to be set
	 */
	public void setFigure_ConsignorName(String arg)
	{
		wFigure_ConsignorName = arg;
	}

	//#CM696369
	/**
	 * Length_Customer Code
	 * @param arg	Length_Customer Code to be set
	 */
	public void setFigure_CustomerCode(String arg)
	{
		wFigure_CustomerCode = arg;
	}

	//#CM696370
	/**
	 * Length_Customer Name
	 * @param arg	Length_Customer Name to be set
	 */
	public void setFigure_CustomerName(String arg)
	{
		wFigure_CustomerName = arg;
	}

	//#CM696371
	/**
	 * Length_Packed Qty per Case
	 * @param arg	Length_Packed Qty per Case to be set
	 */
	public void setFigure_EnteringQty(String arg)
	{
		wFigure_EnteringQty = arg;
	}

	//#CM696372
	/**
	 * Length_Receiving Ticket Line No.
	 * @param arg	Length_Receiving Ticket Line No. to be set
	 */
	public void setFigure_InstockLineNo(String arg)
	{
		wFigure_InstockLineNo = arg;
	}

	//#CM696373
	/**
	 * Length_Receiving Ticket No.
	 * @param arg	Length_Receiving Ticket No. to be set
	 */
	public void setFigure_InstockTicketNo(String arg)
	{
		wFigure_InstockTicketNo = arg;
	}

	//#CM696374
	/**
	 * Length_Item Code
	 * @param arg	Length_Item Code to be set
	 */
	public void setFigure_ItemCode(String arg)
	{
		wFigure_ItemCode = arg;
	}

	//#CM696375
	/**
	 * Length_Item Name
	 * @param arg	Length_Item Name to be set
	 */
	public void setFigure_ItemName(String arg)
	{
		wFigure_ItemName = arg;
	}

	//#CM696376
	/**
	 * Length_System Stock Qty (Total Bulk Qty)
	 * @param arg	Length_System Stock Qty (Total Bulk Qty)
	 */
	public void setFigure_StockQty(String arg)
	{
		wFigure_StockQty = arg;
	}

	//#CM696377
	/**
	 * Length_Case ITF
	 * @param arg	Length_Case ITF to be set
	 */
	public void setFigure_Itf(String arg)
	{
		wFigure_Itf = arg;
	}

	//#CM696378
	/**
	 * Length_Shipping ticket line No.
	 * @param arg	Length_Shipping ticket line No. to be set
	 */
	public void setFigure_ShippingLineNo(String arg)
	{
		wFigure_ShippingLineNo = arg;
	}

	//#CM696379
	/**
	 * Length_Result division
	 * @param arg	Length_Result division to be set
	 */
	public void setFigure_ResultFlag(String arg)
	{
		wFigure_ResultFlag = arg;
	}

	//#CM696380
	/**
	 * Length_Order Date
	 * @param arg	Length_Order Date to be set
	 */
	public void setFigure_OrderingDate(String arg)
	{
		wFigure_OrderingDate = arg;
	}

	//#CM696381
	/**
	 * Length_Planned Date
	 * @param arg	Length_Planned Date to be set
	 */
	public void setFigure_PlanDate(String arg)
	{
		wFigure_PlanDate = arg;
	}

	//#CM696382
	/**
	 * Length_Planned Qty (Total Bulk Qty)
	 * @param arg	Length_Planned Qty (Total Bulk Qty) to be set
	 */
	public void setFigure_PlanQty(String arg)
	{
		wFigure_PlanQty = arg;
	}

	//#CM696383
	/**
	 * Length_Piece Location
	 * @param arg	Length_Piece Location
	 */
	public void setFigure_PieceLocation(String arg)
	{
		wFigure_PieceLocation = arg;
	}

	//#CM696384
	/**
	 * Length_Case Location
	 * @param arg	Length_Case Location
	 */
	public void setFigure_CaseLocation(String arg)
	{
		wFigure_CaseLocation = arg;
	}

	//#CM696385
	/**
	 * Length_Relocation Source Location
	 * @param arg	Length_Relocation Source Location to be set
	 */
	public void setFigure_LocationNo(String arg)
	{
		wFigure_LocationNo = arg;
	}

	//#CM696386
	/**
	 * Length_Relocation Target Location
	 * @param arg	Length_Relocation Target Location to be set
	 */
	public void setFigure_ResultLocationNo(String arg)
	{
		wFigure_ResultLocationNo = arg;
	}

	//#CM696387
	/**
	 * Length_Piece Order No.
	 * @param arg	Length_Piece Order No.
	 */
	public void setFigure_PieceOrderNo(String arg)
	{
		wFigure_PieceOrderNo = arg;
	}

	//#CM696388
	/**
	 * Length_Case Order No.
	 * @param arg	Length_Case Order No.
	 */
	public void setFigure_CaseOrderNo(String arg)
	{
		wFigure_CaseOrderNo = arg;
	}

	//#CM696389
	/**
	 * Length_Result Qty (Total Bulk Qty)
	 * @param arg	Length_Result Qty (Total Bulk Qty) to be set
	 */
	public void setFigure_ResultQty(String arg)
	{
		wFigure_ResultQty = arg;
	}

	//#CM696390
	/**
	 * Length_Result Piece Qty
	 * @param arg	Length_Result Piece Qty
	 */
	public void setFigure_PieceResultQty(String arg)
	{
		wFigure_PieceResultQty = arg;
	}

	//#CM696391
	/**
	 * Length_Result Case Qty
	 * @param arg	Length_Result Case Qty
	 */
	public void setFigure_CaseResultQty(String arg)
	{
		wFigure_CaseResultQty = arg;
	}

	//#CM696392
	/**
	 * Length_Result Date
	 * @param arg	Length_Result Date to be set
	 */
	public void setFigure_WorkDate(String arg)
	{
		wFigure_WorkDate = arg;
	}

	//#CM696393
	/**
	 * Length_Supplier Code
	 * @param arg	Length_Supplier Code to be set
	 */
	public void setFigure_SupplierCode(String arg)
	{
		wFigure_SupplierCode = arg;
	}

	//#CM696394
	/**
	 * Length_Supplier Name
	 * @param arg	Length_Supplier Name to be set
	 */
	public void setFigure_SupplierName(String arg)
	{
		wFigure_SupplierName = arg;
	}

	//#CM696395
	/**
	 * Length_TC/DC division
	 * @param arg	Length_TC/DC division to be set
	 */
	public void setFigure_TcDcFlag(String arg)
	{
		wFigure_TcDcFlag = arg;
	}

	//#CM696396
	/**
	 * Length_Shipping Ticket No
	 * @param arg	Length_Shipping Ticket No to be set
	 */
	public void setFigure_ShippingTicketNo(String arg)
	{
		wFigure_ShippingTicketNo = arg;
	}

	//#CM696397
	/**
	 * Length_Expiry Date
	 * @param arg	Length_Expiry Date to be set
	 */
	public void setFigure_UseByDate(String arg)
	{
		wFigure_UseByDate = arg;
	}

	//#CM696398
	/**
	 * Max Length_Packed qty per bundle
	 * @param arg	Max Length_Packed qty per bundle to be set
	 */
	public void setMaxFigure_BundleEnteringQty(String arg)
	{
		wMaxFigure_BundleEnteringQty = arg;
	}

	//#CM696399
	/**
	 * Max Length_Bundle ITF
	 * @param arg	Max Length_Bundle ITF to be set
	 */
	public void setMaxFigure_BundleItf(String arg)
	{
		wMaxFigure_BundleItf = arg;
	}

	//#CM696400
	/**
	 * Max Length_Consignor Code
	 * @param arg	Max Length_Consignor Code to be set
	 */
	public void setMaxFigure_ConsignorCode(String arg)
	{
		wMaxFigure_ConsignorCode = arg;
	}

	//#CM696401
	/**
	 * Max Length_Consignor Name
	 * @param arg	Max Length_Consignor Name to be set
	 */
	public void setMaxFigure_ConsignorName(String arg)
	{
		wMaxFigure_ConsignorName = arg;
	}

	//#CM696402
	/**
	 * Max Length_Customer Code
	 * @param arg	Max Length_Customer Code to be set
	 */
	public void setMaxFigure_CustomerCode(String arg)
	{
		wMaxFigure_CustomerCode = arg;
	}

	//#CM696403
	/**
	 * Max Length_Customer Name
	 * @param arg	Max Length_Customer Name to be set
	 */
	public void setMaxFigure_CustomerName(String arg)
	{
		wMaxFigure_CustomerName = arg;
	}

	//#CM696404
	/**
	 * Max Length_Packed Qty per Case
	 * @param arg	Max Length_Packed Qty per Case to be set
	 */
	public void setMaxFigure_EnteringQty(String arg)
	{
		wMaxFigure_EnteringQty = arg;
	}

	//#CM696405
	/**
	 * Max Length_Receiving Ticket Line No.
	 * @param arg	Max Length_Receiving Ticket Line No. to be set
	 */
	public void setMaxFigure_InstockLineNo(String arg)
	{
		wMaxFigure_InstockLineNo = arg;
	}

	//#CM696406
	/**
	 * Max Length_Receiving Ticket No.
	 * @param arg	Max Length_Receiving Ticket No. to be set
	 */
	public void setMaxFigure_InstockTicketNo(String arg)
	{
		wMaxFigure_InstockTicketNo = arg;
	}

	//#CM696407
	/**
	 * Max Length_Item Code
	 * @param arg	Max Length_Item Code to be set
	 */
	public void setMaxFigure_ItemCode(String arg)
	{
		wMaxFigure_ItemCode = arg;
	}

	//#CM696408
	/**
	 * Max Length_Item Name
	 * @param arg	Max Length_Item Name to be set
	 */
	public void setMaxFigure_ItemName(String arg)
	{
		wMaxFigure_ItemName = arg;
	}

	//#CM696409
	/**
	 * Max Length_System Stock Qty (Total Bulk Qty)
	 * @param arg	Max Length_System Stock Qty (Total Bulk Qty)
	 */
	public void setMaxFigure_StockQty(String arg)
	{
		wMaxFigure_StockQty = arg;
	}

	//#CM696410
	/**
	 * Max Length_Case ITF
	 * @param arg	Max Length_Case ITF to be set
	 */
	public void setMaxFigure_Itf(String arg)
	{
		wMaxFigure_Itf = arg;
	}

	//#CM696411
	/**
	 * Max Length_Shipping ticket line No.
	 * @param arg	Max Length_Shipping ticket line No. to be set
	 */
	public void setMaxFigure_ShippingLineNo(String arg)
	{
		wMaxFigure_ShippingLineNo = arg;
	}

	//#CM696412
	/**
	 * Max Length_Result division
	 * @param arg	Max Length_Result division to be set
	 */
	public void setMaxFigure_ResultFlag(String arg)
	{
		wMaxFigure_ResultFlag = arg;
	}

	//#CM696413
	/**
	 * Max Length_Order Date
	 * @param arg	Max Length_Order Date to be set
	 */
	public void setMaxFigure_OrderingDate(String arg)
	{
		wMaxFigure_OrderingDate = arg;
	}

	//#CM696414
	/**
	 * Max Length_Planned Date
	 * @param arg	Max Length_Planned Date to be set
	 */
	public void setMaxFigure_PlanDate(String arg)
	{
		wMaxFigure_PlanDate = arg;
	}

	//#CM696415
	/**
	 * Max Length_Planned Qty (Total Bulk Qty)
	 * @param arg	Max Length_Planned Qty (Total Bulk Qty) to be set
	 */
	public void setMaxFigure_PlanQty(String arg)
	{
		wMaxFigure_PlanQty = arg;
	}

	//#CM696416
	/**
	 * Max Length_Piece Picking Location
	 * @param arg	Max Length_Piece Picking Location
	 */
	public void setMaxFigure_PieceLocation(String arg)
	{
		wMaxFigure_PieceLocation = arg;
	}

	//#CM696417
	/**
	 * Max Length_Case Picking Location
	 * @param arg	Max Length_Case Picking Location
	 */
	public void setMaxFigure_CaseLocation(String arg)
	{
		wMaxFigure_CaseLocation = arg;
	}

	//#CM696418
	/**
	 * Max Length_Relocation Source Location
	 * @param arg	Max Length_Relocation Source Location to be set
	 */
	public void setMaxFigure_LocationNo(String arg)
	{
		wMaxFigure_LocationNo = arg;
	}

	//#CM696419
	/**
	 * Max Length_Relocation Target Location
	 * @param arg	Max Length_Relocation Target Location to be set
	 */
	public void setMaxFigure_ResultLocationNo(String arg)
	{
		wMaxFigure_ResultLocationNo = arg;
	}

	//#CM696420
	/**
	 * Max Length_Piece Order No.
	 * @param arg	Max Length_Piece Order No.
	 */
	public void setMaxFigure_PieceOrderNo(String arg)
	{
		wMaxFigure_PieceOrderNo = arg;
	}

	//#CM696421
	/**
	 * Max Length_Case Order No.
	 * @param arg	Max Length_Case Order No.
	 */
	public void setMaxFigure_CaseOrderNo(String arg)
	{
		wMaxFigure_CaseOrderNo = arg;
	}

	//#CM696422
	/**
	 * Max Length_Result Qty (Total Bulk Qty)
	 * @param arg	Max Length_Result Qty (Total Bulk Qty) to be set
	 */
	public void setMaxFigure_ResultQty(String arg)
	{
		wMaxFigure_ResultQty = arg;
	}

	//#CM696423
	/**
	 * Max Length_Result Piece Qty
	 * @param arg	Max Length_Result Piece Qty
	 */
	public void setMaxFigure_PieceResultQty(String arg)
	{
		wMaxFigure_PieceResultQty = arg;
	}

	//#CM696424
	/**
	 * Max Length_Result Case Qty
	 * @param arg	Max Length_Result Case Qty
	 */
	public void setMaxFigure_CaseResultQty(String arg)
	{
		wMaxFigure_CaseResultQty = arg;
	}

	//#CM696425
	/**
	 * Max Length_Result Date
	 * @param arg	Max Length_Result Date to be set
	 */
	public void setMaxFigure_WorkDate(String arg)
	{
		wMaxFigure_WorkDate = arg;
	}

	//#CM696426
	/**
	 * Max Length_Supplier Code
	 * @param arg	Max Length_Supplier Code to be set
	 */
	public void setMaxFigure_SupplierCode(String arg)
	{
		wMaxFigure_SupplierCode = arg;
	}

	//#CM696427
	/**
	 * Max Length_Supplier Name
	 * @param arg	Max Length_Supplier Name to be set
	 */
	public void setMaxFigure_SupplierName(String arg)
	{
		wMaxFigure_SupplierName = arg;
	}

	//#CM696428
	/**
	 * Max Length_TC/DC division
	 * @param arg	Max Length_TC/DC division to be set
	 */
	public void setMaxFigure_TcDcFlag(String arg)
	{
		wMaxFigure_TcDcFlag = arg;
	}

	//#CM696429
	/**
	 * Max Length_Shipping Ticket No
	 * @param arg	Max Length_Shipping Ticket No to be set
	 */
	public void setMaxFigure_ShippingTicketNo(String arg)
	{
		wMaxFigure_ShippingTicketNo = arg;
	}

	//#CM696430
	/**
	 * Max Length_Expiry Date
	 * @param arg	Max Length_Expiry Date to be set
	 */
	public void setMaxFigure_UseByDate(String arg)
	{
		wMaxFigure_UseByDate = arg;
	}

	//#CM696431
	/**
	 * Position_Packed qty per bundle
	 * @param arg	Position_Packed qty per bundle to be set
	 */
	public void setPosition_BundleEnteringQty(String arg)
	{
		wPosition_BundleEnteringQty = arg;
	}

	//#CM696432
	/**
	 * Position_Bundle ITF
	 * @param arg	Position_Bundle ITF to be set
	 */
	public void setPosition_BundleItf(String arg)
	{
		wPosition_BundleItf = arg;
	}

	//#CM696433
	/**
	 * Position_Consignor Code
	 * @param arg	Position_Consignor Code to be set
	 */
	public void setPosition_ConsignorCode(String arg)
	{
		wPosition_ConsignorCode = arg;
	}

	//#CM696434
	/**
	 * Position_Consignor Name
	 * @param arg	Position_Consignor Name to be set
	 */
	public void setPosition_ConsignorName(String arg)
	{
		wPosition_ConsignorName = arg;
	}

	//#CM696435
	/**
	 * Position_Customer Code
	 * @param arg	Position_Customer Code to be set
	 */
	public void setPosition_CustomerCode(String arg)
	{
		wPosition_CustomerCode = arg;
	}

	//#CM696436
	/**
	 * Position_Customer Name
	 * @param arg	Position_Customer Name to be set
	 */
	public void setPosition_CustomerName(String arg)
	{
		wPosition_CustomerName = arg;
	}

	//#CM696437
	/**
	 * Position_Packed Qty per Case
	 * @param arg	Position_Packed Qty per Case to be set
	 */
	public void setPosition_EnteringQty(String arg)
	{
		wPosition_EnteringQty = arg;
	}

	//#CM696438
	/**
	 * Position_Receiving Ticket Line No.
	 * @param arg	Position_Receiving Ticket Line No. to be set
	 */
	public void setPosition_InstockLineNo(String arg)
	{
		wPosition_InstockLineNo = arg;
	}

	//#CM696439
	/**
	 * Position_Receiving Ticket No.
	 * @param arg	Position_Receiving Ticket No. to be set
	 */
	public void setPosition_InstockTicketNo(String arg)
	{
		wPosition_InstockTicketNo = arg;
	}

	//#CM696440
	/**
	 * Position_Item Code
	 * @param arg	Position_Item Code to be set
	 */
	public void setPosition_ItemCode(String arg)
	{
		wPosition_ItemCode = arg;
	}

	//#CM696441
	/**
	 * Position_Item Name
	 * @param arg	Position_Item Name to be set
	 */
	public void setPosition_ItemName(String arg)
	{
		wPosition_ItemName = arg;
	}

	//#CM696442
	/**
	 * Position_System Stock Qty (Total Bulk Qty)
	 * @param arg	Position_System Stock Qty (Total Bulk Qty)
	 */
	public void setPosition_StockQty(String arg)
	{
		wPosition_StockQty = arg;
	}

	//#CM696443
	/**
	 * Position_Case ITF
	 * @param arg	Position_Case ITF to be set
	 */
	public void setPosition_Itf(String arg)
	{
		wPosition_Itf = arg;
	}

	//#CM696444
	/**
	 * Position_Shipping ticket line No.
	 * @param arg	Position_Shipping ticket line No. to be set
	 */
	public void setPosition_ShippingLineNo(String arg)
	{
		wPosition_ShippingLineNo = arg;
	}

	//#CM696445
	/**
	 * Position_Result division
	 * @param arg	Position_Result division to be set
	 */
	public void setPosition_ResultFlag(String arg)
	{
		wPosition_ResultFlag = arg;
	}

	//#CM696446
	/**
	 * Position_Order Date
	 * @param arg	Position_Order Date to be set
	 */
	public void setPosition_OrderingDate(String arg)
	{
		wPosition_OrderingDate = arg;
	}

	//#CM696447
	/**
	 * Position_Planned Date
	 * @param arg	Position_Planned Date to be set
	 */
	public void setPosition_PlanDate(String arg)
	{
		wPosition_PlanDate = arg;
	}

	//#CM696448
	/**
	 * Position_Planned Qty (Total Bulk Qty)
	 * @param arg	Position_Planned Qty (Total Bulk Qty) to be set
	 */
	public void setPosition_PlanQty(String arg)
	{
		wPosition_PlanQty = arg;
	}

	//#CM696449
	/**
	 * Position_Piece Location
	 * @param arg	Position_Piece Location
	 */
	public void setPosition_PieceLocation(String arg)
	{
		wPosition_PieceLocation = arg;
	}

	//#CM696450
	/**
	 * Position_Case Location
	 * @param arg	Position_Case Location
	 */
	public void setPosition_CaseLocation(String arg)
	{
		wPosition_CaseLocation = arg;
	}

	//#CM696451
	/**
	 * Position_Relocation Source Location
	 * @param arg	Position_Relocation Source Location to be set
	 */
	public void setPosition_LocationNo(String arg)
	{
		wPosition_LocationNo = arg;
	}

	//#CM696452
	/**
	 * Position_Relocation Target Location
	 * @param arg	Position_Relocation Target Location to be set
	 */
	public void setPosition_ResultLocationNo(String arg)
	{
		wPosition_ResultLocationNo = arg;
	}

	//#CM696453
	/**
	 * Position_Piece Order No.
	 * @param arg	Position_Piece Order No.
	 */
	public void setPosition_PieceOrderNo(String arg)
	{
		wPosition_PieceOrderNo = arg;
	}

	//#CM696454
	/**
	 * Position_Case Order No.
	 * @param arg	Position_Case Order No.
	 */
	public void setPosition_CaseOrderNo(String arg)
	{
		wPosition_CaseOrderNo = arg;
	}

	//#CM696455
	/**
	 * Position_Result Qty (Total Bulk Qty)
	 * @param arg	Position_Result Qty (Total Bulk Qty) to be set
	 */
	public void setPosition_ResultQty(String arg)
	{
		wPosition_ResultQty = arg;
	}

	//#CM696456
	/**
	 * Position_Result Piece Qty
	 * @param arg	Position_Result Piece Qty
	 */
	public void setPosition_PieceResultQty(String arg)
	{
		wPosition_PieceResultQty = arg;
	}

	//#CM696457
	/**
	 * Position_Result Case Qty
	 * @param arg	Position_Result Case Qty
	 */
	public void setPosition_CaseResultQty(String arg)
	{
		wPosition_CaseResultQty = arg;
	}

	//#CM696458
	/**
	 * Position_Result Date
	 * @param arg	Position_Result Date to be set
	 */
	public void setPosition_WorkDate(String arg)
	{
		wPosition_WorkDate = arg;
	}

	//#CM696459
	/**
	 * Position_Supplier Code
	 * @param arg	Position_Supplier Code to be set
	 */
	public void setPosition_SupplierCode(String arg)
	{
		wPosition_SupplierCode = arg;
	}

	//#CM696460
	/**
	 * Position_Supplier Name
	 * @param arg	Position_Supplier Name to be set
	 */
	public void setPosition_SupplierName(String arg)
	{
		wPosition_SupplierName = arg;
	}

	//#CM696461
	/**
	 * Position_TC/DC division
	 * @param arg	Position_TC/DC division to be set
	 */
	public void setPosition_TcDcFlag(String arg)
	{
		wPosition_TcDcFlag = arg;
	}

	//#CM696462
	/**
	 * Position_Shipping Ticket No
	 * @param arg	Position_Shipping Ticket No to be set
	 */
	public void setPosition_ShippingTicketNo(String arg)
	{
		wPosition_ShippingTicketNo = arg;
	}

	//#CM696463
	/**
	 * Position_Expiry Date
	 * @param arg	Position_Expiry Date to be set
	 */
	public void setPosition_UseByDate(String arg)
	{
		wPosition_UseByDate = arg;
	}

	//#CM696464
	/**
	 * Enabled_Packed qty per bundle
	 * @param arg	Enabled_Packed qty per bundle to be set
	 */
	public void setValid_BundleEnteringQty(boolean arg)
	{
		wValid_BundleEnteringQty = arg;
	}

	//#CM696465
	/**
	 * Enabled_Bundle ITF
	 * @param arg	Enabled_Bundle ITF to be set
	 */
	public void setValid_BundleItf(boolean arg)
	{
		wValid_BundleItf = arg;
	}

	//#CM696466
	/**
	 * Enabled_Consignor Code
	 * @param arg	Enabled_Consignor Code to be set
	 */
	public void setValid_ConsignorCode(boolean arg)
	{
		wValid_ConsignorCode = arg;
	}

	//#CM696467
	/**
	 * Enabled_Consignor Name
	 * @param arg	Enabled_Consignor Name to be set
	 */
	public void setValid_ConsignorName(boolean arg)
	{
		wValid_ConsignorName = arg;
	}

	//#CM696468
	/**
	 * Enabled_Customer Code
	 * @param arg	Enabled_Customer Code to be set
	 */
	public void setValid_CustomerCode(boolean arg)
	{
		wValid_CustomerCode = arg;
	}

	//#CM696469
	/**
	 * Enabled_Customer Name
	 * @param arg	Enabled_Customer Name to be set
	 */
	public void setValid_CustomerName(boolean arg)
	{
		wValid_CustomerName = arg;
	}

	//#CM696470
	/**
	 * Enabled_Packed Qty per Case
	 * @param arg	Enabled_Packed Qty per Case to be set
	 */
	public void setValid_EnteringQty(boolean arg)
	{
		wValid_EnteringQty = arg;
	}

	//#CM696471
	/**
	 * Enabled_Receiving Ticket Line No.
	 * @param arg	Enabled_Receiving Ticket Line No. to be set
	 */
	public void setValid_InstockLineNo(boolean arg)
	{
		wValid_InstockLineNo = arg;
	}

	//#CM696472
	/**
	 * Enabled_Receiving Ticket No.
	 * @param arg	Enabled_Receiving Ticket No. to be set
	 */
	public void setValid_InstockTicketNo(boolean arg)
	{
		wValid_InstockTicketNo = arg;
	}

	//#CM696473
	/**
	 * Enabled_Item Code
	 * @param arg	Enabled_Item Code to be set
	 */
	public void setValid_ItemCode(boolean arg)
	{
		wValid_ItemCode = arg;
	}

	//#CM696474
	/**
	 * Enabled_Item Name
	 * @param arg	Enabled_Item Name to be set
	 */
	public void setValid_ItemName(boolean arg)
	{
		wValid_ItemName = arg;
	}

	//#CM696475
	/**
	 * Enabled_Case ITF
	 * @param arg	Enabled_Case ITF to be set
	 */
	public void setValid_Itf(boolean arg)
	{
		wValid_Itf = arg;
	}

	//#CM696476
	/**
	 * Enabled_Shipping ticket line No.
	 * @param arg	Enabled_Shipping ticket line No. to be set
	 */
	public void setValid_ShippingLineNo(boolean arg)
	{
		wValid_ShippingLineNo = arg;
	}

	//#CM696477
	/**
	 * Enabled_Result division
	 * @param arg	Enabled_Result division to be set
	 */
	public void setValid_ResultFlag(boolean arg)
	{
		wValid_ResultFlag = arg;
	}

	//#CM696478
	/**
	 * Enabled_Order Date
	 * @param arg	Enabled_Order Date to be set
	 */
	public void setValid_OrderingDate(boolean arg)
	{
		wValid_OrderingDate = arg;
	}

	//#CM696479
	/**
	 * Enabled_Planned Date
	 * @param arg	Enabled_Planned Date to be set
	 */
	public void setValid_PlanDate(boolean arg)
	{
		wValid_PlanDate = arg;
	}

	//#CM696480
	/**
	 * Enabled_Planned Qty (Total Bulk Qty)
	 * @param arg	Enabled_Planned Qty (Total Bulk Qty) to be set
	 */
	public void setValid_PlanQty(boolean arg)
	{
		wValid_PlanQty = arg;
	}

	//#CM696481
	/**
	 * Enabled_Piece Location
	 * @param arg	Enabled_Piece Location
	 */
	public void setValid_PieceLocation(boolean arg)
	{
		wValid_PieceLocation = arg;
	}

	//#CM696482
	/**
	 * Enabled_Case Location
	 * @param arg	Enabled_Case Location
	 */
	public void setValid_CaseLocation(boolean arg)
	{
		wValid_CaseLocation = arg;
	}

	//#CM696483
	/**
	 * Enabled_Relocation Source Location
	 * @param arg	Enabled_Relocation Source Location to be set
	 */
	public void setValid_LocationNo(boolean arg)
	{
		wValid_LocationNo = arg;
	}

	//#CM696484
	/**
	 * Enabled_Relocation Target Location
	 * @param arg	Enabled_Relocation Target Location to be set
	 */
	public void setValid_ResultLocationNo(boolean arg)
	{
		wValid_ResultLocationNo = arg;
	}

	//#CM696485
	/**
	 * Enabled_Piece Order No.
	 * @param arg	Enabled_Piece Order No.
	 */
	public void setValid_PieceOrderNo(boolean arg)
	{
		wValid_PieceOrderNo = arg;
	}

	//#CM696486
	/**
	 * Enabled_Case Order No.
	 * @param arg	Enabled_Case Order No.
	 */
	public void setValid_CaseOrderNo(boolean arg)
	{
		wValid_CaseOrderNo = arg;
	}

	//#CM696487
	/**
	 * Enabled_System Stock Qty (Total Bulk Qty)
	 * @param arg	Enabled_System Stock Qty (Total Bulk Qty)
	 */
	public void setValid_StockQty(boolean arg)
	{
		wValid_StockQty = arg;
	}

	//#CM696488
	/**
	 * Enabled_Result Qty (Total Bulk Qty)
	 * @param arg	Enabled_Shipping Qty (Total Bulk Qty) to be set
	 */
	public void setValid_ResultQty(boolean arg)
	{
		wValid_ResultQty = arg;
	}

	//#CM696489
	/**
	 * Enabled_Result Piece Qty
	 * @param arg	Enabled_Result Piece Qty
	 */
	public void setValid_PieceResultQty(boolean arg)
	{
		wValid_PieceResultQty = arg;
	}

	//#CM696490
	/**
	 * Enabled_Result Case Qty
	 * @param arg	Enabled_Result Case Qty
	 */
	public void setValid_CaseResultQty(boolean arg)
	{
		wValid_CaseResultQty = arg;
	}

	//#CM696491
	/**
	 * Enabled_Result Date
	 * @param arg	Enabled_Result Date to be set
	 */
	public void setValid_WorkDate(boolean arg)
	{
		wValid_WorkDate = arg;
	}

	//#CM696492
	/**
	 * Enabled_Supplier Code
	 * @param arg	Enabled_Supplier Code to be set
	 */
	public void setValid_SupplierCode(boolean arg)
	{
		wValid_SupplierCode = arg;
	}

	//#CM696493
	/**
	 * Enabled_Supplier Name
	 * @param arg	Enabled_Supplier Name to be set
	 */
	public void setValid_SupplierName(boolean arg)
	{
		wValid_SupplierName = arg;
	}

	//#CM696494
	/**
	 * Enabled_TC/DC division
	 * @param arg	Enabled_TC/DC division to be set
	 */
	public void setValid_TcDcFlag(boolean arg)
	{
		wValid_TcDcFlag = arg;
	}

	//#CM696495
	/**
	 * Enabled_Shipping Ticket No
	 * @param arg	Enabled_Shipping Ticket No to be set
	 */
	public void setValid_ShippingTicketNo(boolean arg)
	{
		wValid_ShippingTicketNo = arg;
	}

	//#CM696496
	/**
	 * Enabled_Expiry Date
	 * @param arg	Enabled_Expiry Date to be set
	 */
	public void setValid_UseByDate(boolean arg)
	{
		wValid_UseByDate = arg;
	}
}
//#CM696497
//end of class
