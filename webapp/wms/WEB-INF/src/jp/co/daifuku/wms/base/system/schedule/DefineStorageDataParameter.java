//#CM698079
//$Id: DefineStorageDataParameter.java,v 1.2 2006/11/13 06:03:09 suresh Exp $

//#CM698080
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import jp.co.daifuku.wms.base.common.Parameter;
 
//#CM698081
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * Use the <CODE>DefineStorageDataParameter</CODE> class to pass a parameter between the screen and the schedule for setting field items to be loaded or reported for the Storage data.<BR>
 * Allow this class to maintain the field items to be used in each screen for system package. Use a field item depending on the screen.<BR>
 * <BR>
 * <DIR>
 * Allow the <CODE>DefineStorageDataParameter</CODE> class to maintain the following field items.<BR>
 * <BR>
 * [Textbox or Label] <BR>
 * <DIR>
 *     Worker Code <BR>
 *     Password <BR>
 * <BR>
 *     Length_Planned Storage Date <BR>
 *     Length_Consignor Code <BR>
 *     Length_Consignor Name <BR>
 *     Length_Item Code <BR>
 *     Length_Bundle ITF <BR>
 *     Length_Case ITF <BR>
 *     Length_Packed qty per bundle <BR>
 *     Length_Packed Qty per Case <BR>
 *     Length_Item Name <BR>
 *     Length_Storage Qty (Total Bulk Qty) <BR> 
 *     Length_Piece Storage Location <BR>
 *     Length_Case Storage Location <BR>
 *     Length_Result Piece Qty <BR>
 *     Length_Result Case Qty <BR>
 *     Length_Storage Result Date <BR> 
 *     Length_Result division <BR>
 *     Length_Expiry Date <BR>
 * <BR>
 *     Max Length_Planned Storage Date <BR>
 *     Max Length_Consignor Code <BR>
 *     Max Length_Consignor Name <BR>
 *     Max Length_Item Code <BR>
 *     Max Length_Bundle ITF <BR>
 *     Max Length_Case ITF <BR>
 *     Max Length_Packed qty per bundle <BR>
 *     Max Length_Packed Qty per Case <BR>
 *     Max Length_Item Name <BR>
 *     Max Length_Storage Qty (Total Bulk Qty) <BR> 
 *     Max Length_Piece Storage Location <BR>
 *     Max Length_Case Storage Location <BR>
 *     Max Length_Result Piece Qty <BR>
 *     Max Length_Result Case Qty <BR>
 *     Max Length_Storage Result Date <BR> 
 *     Max Length_Result division <BR>
 *     Max Length_Expiry Date <BR>
 * <BR>
 *     Position_Planned Storage Date <BR>
 *     Position_Consignor Code <BR>
 *     Position_Consignor Name <BR>
 *     Position_Item Code <BR>
 *     Position_Bundle ITF <BR>
 *     Position_Case ITF <BR>
 *     Position_Packed qty per bundle <BR>
 *     Position_Packed Qty per Case <BR>
 *     Position_Item Name <BR>
 *     Position_Storage Qty (Total Bulk Qty) <BR> 
 *     Position_Piece Storage Location <BR>
 *     Position_Case Storage Location <BR>
 *     Position_Result Piece Qty <BR>
 *     Position_Result Case Qty <BR>
 *     Position_Storage Result Date <BR> 
 *     Position_Result division <BR>
 *     Position_Expiry Date <BR>
 * </DIR>
 * [Checkbox] <BR>
 * <DIR>
 *     Enabled_Planned Storage Date <BR>
 *     Enabled_Consignor Code <BR>
 *     Enabled_Consignor Name <BR>
 *     Enabled_Item Code <BR>
 *     Enabled_Bundle ITF <BR>
 *     Enabled_Case ITF <BR>
 *     Enabled_Packed qty per bundle <BR>
 *     Enabled_Packed Qty per Case <BR>
 *     Enabled_Item Name <BR>
 *     Enabled_Storage Qty (Total Bulk Qty) <BR> 
 *     Enabled_Piece Storage Location <BR>
 *     Enabled_Case Storage Location <BR>
 *     Enabled_Result Piece Qty <BR>
 *     Enabled_Result Case Qty <BR>
 *     Enabled_Storage Result Date <BR> 
 *     Enabled_Result division <BR>
 *     Enabled_Expiry Date <BR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/04</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:09 $
 * @author  $Author: suresh $
 */
public class DefineStorageDataParameter extends Parameter
{
	//#CM698082
	// Class variables -----------------------------------------------
	//#CM698083
	/**
	 * Worker Code
	 */
	private String wWorkerCode;
	
	//#CM698084
	/**
	 * Password
	 */
	private String wPassword;
	
	//#CM698085
	/**
	 * Length_Planned Storage Date
	 */
	private String wFigure_StoragePlanDate;
	
	//#CM698086
	/**
	 * Length_Consignor Code
	 */
	private String wFigure_ConsignorCode;
	
	//#CM698087
	/**
	 * Length_Consignor Name
	 */
	private String wFigure_ConsignorName;
	
	//#CM698088
	/**
	 * Length_Item Code
	 */
	private String wFigure_ItemCode;
	
	//#CM698089
	/**
	 * Length_Bundle ITF
	 */
	private String wFigure_BundleItf;
	
	//#CM698090
	/**
	 * Length_Case ITF
	 */
	private String wFigure_Itf;
	
	//#CM698091
	/**
	 * Length_Packed qty per bundle
	 */
	private String wFigure_BundleEnteringQty;
	
	//#CM698092
	/**
	 * Length_Packed Qty per Case
	 */
	private String wFigure_EnteringQty;
	
	//#CM698093
	/**
	 * Length_Item Name
	 */
	private String wFigure_ItemName;
	
	//#CM698094
	/**
	 * Length_Storage Qty (Total Bulk Qty)
	 */
	private String wFigure_StoragePlanQty;

	//#CM698095
	/**
	 * Length_Piece Storage Location
	 */
	private String wFigure_PieceStorageLocation;
	
	//#CM698096
	/**
	 * Length_Case Storage Location
	 */
	private String wFigure_CaseStorageLocation;
	
	//#CM698097
	/**
	 * Length_Result Piece Qty
	 */
	private String wFigure_PieceResultQty;
	
	//#CM698098
	/**
	 * Length_Result Case Qty
	 */
	private String wFigure_CaseResultQty;
	
	//#CM698099
	/**
	 * Length_Storage Result Date
	 */
	private String wFigure_StorageWorkDate;
	
	//#CM698100
	/**
	 * Length_Result division
	 */
	private String wFigure_ResultFlag;
	
	//#CM698101
	/**
	 * Length_Expiry Date
	 */
	private String wFigure_UseByDate;
	
	//#CM698102
	/**
	 * Max Length_Planned Storage Date
	 */
	private String wMaxFigure_StoragePlanDate;
	
	//#CM698103
	/**
	 * Max Length_Consignor Code
	 */
	private String wMaxFigure_ConsignorCode;
	
	//#CM698104
	/**
	 * Max Length_Consignor Name
	 */
	private String wMaxFigure_ConsignorName;
	
	//#CM698105
	/**
	 * Max Length_Item Code
	 */
	private String wMaxFigure_ItemCode;
	
	//#CM698106
	/**
	 * Max Length_Bundle ITF
	 */
	private String wMaxFigure_BundleItf;
	
	//#CM698107
	/**
	 * Max Length_Case ITF
	 */
	private String wMaxFigure_Itf;
	
	//#CM698108
	/**
	 * Max Length_Packed qty per bundle
	 */
	private String wMaxFigure_BundleEnteringQty;
	
	//#CM698109
	/**
	 * Max Length_Packed Qty per Case
	 */
	private String wMaxFigure_EnteringQty;
	
	//#CM698110
	/**
	 * Max Length_Item Name
	 */
	private String wMaxFigure_ItemName;
	
	//#CM698111
	/**
	 * Max Length_Storage Qty (Total Bulk Qty)
	 */
	private String wMaxFigure_StoragePlanQty;

	//#CM698112
	/**
	 * Max Length_Piece Storage Location
	 */
	private String wMaxFigure_PieceStorageLocation;
	
	//#CM698113
	/**
	 * Max Length_Case Storage Location
	 */
	private String wMaxFigure_CaseStorageLocation;
	
	//#CM698114
	/**
	 * Max Length_Result Piece Qty
	 */
	private String wMaxFigure_PieceResultQty;
	
	//#CM698115
	/**
	 * Max Length_Result Case Qty
	 */
	private String wMaxFigure_CaseResultQty;
	
	//#CM698116
	/**
	 * Max Length_Storage Result Date
	 */
	private String wMaxFigure_StorageWorkDate;
	
	//#CM698117
	/**
	 * Max Length_Result division
	 */
	private String wMaxFigure_ResultFlag;
	
	//#CM698118
	/**
	 * Max Length_Expiry Date
	 */
	private String wMaxFigure_UseByDate;
	
	//#CM698119
	/**
	 * Position_Planned Storage Date
	 */
	private String wPosition_StoragePlanDate;
	
	//#CM698120
	/**
	 * Position_Consignor Code
	 */
	private String wPosition_ConsignorCode;
	
	//#CM698121
	/**
	 * Position_Consignor Name
	 */
	private String wPosition_ConsignorName;
	
	//#CM698122
	/**
	 * Position_Item Code
	 */
	private String wPosition_ItemCode;
	
	//#CM698123
	/**
	 * Position_Bundle ITF
	 */
	private String wPosition_BundleItf;
	
	//#CM698124
	/**
	 * Position_Case ITF
	 */
	private String wPosition_Itf;
	
	//#CM698125
	/**
	 * Position_Packed qty per bundle
	 */
	private String wPosition_BundleEnteringQty;
	
	//#CM698126
	/**
	 * Position_Packed Qty per Case
	 */
	private String wPosition_EnteringQty;
	
	//#CM698127
	/**
	 * Position_Item Name
	 */
	private String wPosition_ItemName;
	
	//#CM698128
	/**
	 * Position_Storage Qty (Total Bulk Qty)
	 */
	private String wPosition_StoragePlanQty;

	//#CM698129
	/**
	 * Position_Piece Storage Location
	 */
	private String wPosition_PieceStorageLocation;
	
	//#CM698130
	/**
	 * Position_Case Storage Location
	 */
	private String wPosition_CaseStorageLocation;
	
	//#CM698131
	/**
	 * Position_Result Piece Qty
	 */
	private String wPosition_PieceResultQty;
	
	//#CM698132
	/**
	 * Position_Result Case Qty
	 */
	private String wPosition_CaseResultQty;
	
	//#CM698133
	/**
	 * Position_Storage Result Date
	 */
	private String wPosition_StorageWorkDate;
	
	//#CM698134
	/**
	 * Position_Result division
	 */
	private String wPosition_ResultFlag;
	
	//#CM698135
	/**
	 * Position_Expiry Date
	 */
	private String wPosition_UseByDate;
	
	//#CM698136
	/**
	 * Enabled_Planned Storage Date
	 */
	private boolean wValid_StoragePlanDate;
	
	//#CM698137
	/**
	 * Enabled_Consignor Code
	 */
	private boolean wValid_ConsignorCode;
	
	//#CM698138
	/**
	 * Enabled_Consignor Name
	 */
	private boolean wValid_ConsignorName;
	
	//#CM698139
	/**
	 * Enabled_Item Code
	 */
	private boolean wValid_ItemCode;
	
	//#CM698140
	/**
	 * Enabled_Bundle ITF
	 */
	private boolean wValid_BundleItf;
	
	//#CM698141
	/**
	 * Enabled_Case ITF
	 */
	private boolean wValid_Itf;
	
	//#CM698142
	/**
	 * Enabled_Packed qty per bundle
	 */
	private boolean wValid_BundleEnteringQty;
	
	//#CM698143
	/**
	 * Enabled_Packed Qty per Case
	 */
	private boolean wValid_EnteringQty;
	
	//#CM698144
	/**
	 * Enabled_Item Name
	 */
	private boolean wValid_ItemName;
	
	//#CM698145
	/**
	 * Enabled_Storage Qty (Total Bulk Qty)
	 */
	private boolean wValid_StoragePlanQty;

	//#CM698146
	/**
	 * Enabled_Piece Storage Location
	 */
	private boolean wValid_PieceStorageLocation;
	
	//#CM698147
	/**
	 * Enabled_Case Storage Location
	 */
	private boolean wValid_CaseStorageLocation;
	
	//#CM698148
	/**
	 * Enabled_Result Piece Qty
	 */
	private boolean wValid_PieceResultQty;
	
	//#CM698149
	/**
	 * Enabled_Result Case Qty
	 */
	private boolean wValid_CaseResultQty;
	
	//#CM698150
	/**
	 * Enabled_Storage Result Date
	 */
	private boolean wValid_StorageWorkDate;
	
	//#CM698151
	/**
	 * Enabled_Result division
	 */
	private boolean wValid_ResultFlag;
	
	//#CM698152
	/**
	 * Enabled_Expiry Date
	 */
	private boolean wValid_UseByDate;


	//#CM698153
	// Class method --------------------------------------------------
	//#CM698154
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:09 $");
	}

	//#CM698155
	// Constructors --------------------------------------------------
	//#CM698156
	/**
	 * Initialize this class.
	 */
	public DefineStorageDataParameter()
	{
	}
	
	//#CM698157
	// Public methods ------------------------------------------------

	//#CM698158
	/**
	 * Worker Code
	 * @return	Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM698159
	/**
	 * Password
	 * @return	Password
	 */
	public String getPassword()
	{
		return wPassword;
	}


	//#CM698160
	/**
	 * Length_Packed qty per bundle
	 * @return	Length_Packed qty per bundle
	 */
	public String getFigure_BundleEnteringQty()
	{
		return wFigure_BundleEnteringQty;
	}

	//#CM698161
	/**
	 * Length_Bundle ITF
	 * @return	Length_Bundle ITF
	 */
	public String getFigure_BundleItf()
	{
		return wFigure_BundleItf;
	}

	//#CM698162
	/**
	 * Length_Result Case Qty
	 * @return	Length_Result Case Qty
	 */
	public String getFigure_CaseResultQty()
	{
		return wFigure_CaseResultQty;
	}

	//#CM698163
	/**
	 * Length_Case Storage Location
	 * @return	Length_Case Storage Location
	 */
	public String getFigure_CaseStorageLocation()
	{
		return wFigure_CaseStorageLocation;
	}

	//#CM698164
	/**
	 * Length_Consignor Code
	 * @return	Length_Consignor Code
	 */
	public String getFigure_ConsignorCode()
	{
		return wFigure_ConsignorCode;
	}

	//#CM698165
	/**
	 * Length_Consignor Name
	 * @return	Length_Consignor Name
	 */
	public String getFigure_ConsignorName()
	{
		return wFigure_ConsignorName;
	}

	//#CM698166
	/**
	 * Length_Packed Qty per Case
	 * @return	Length_Packed Qty per Case
	 */
	public String getFigure_EnteringQty()
	{
		return wFigure_EnteringQty;
	}

	//#CM698167
	/**
	 * Length_Item Code
	 * @return	Length_Item Code
	 */
	public String getFigure_ItemCode()
	{
		return wFigure_ItemCode;
	}

	//#CM698168
	/**
	 * Length_Item Name
	 * @return	Length_Item Name
	 */
	public String getFigure_ItemName()
	{
		return wFigure_ItemName;
	}

	//#CM698169
	/**
	 * Length_Case ITF
	 * @return	Length_Case ITF
	 */
	public String getFigure_Itf()
	{
		return wFigure_Itf;
	}

	//#CM698170
	/**
	 * Length_Result Piece Qty
	 * @return Length_Result Piece Qty
	 */
	public String getFigure_PieceResultQty()
	{
		return wFigure_PieceResultQty;
	}

	//#CM698171
	/**
	 * Length_Piece Storage Location
	 * @return	Length_Piece Storage Location
	 */
	public String getFigure_PieceStorageLocation()
	{
		return wFigure_PieceStorageLocation;
	}

	//#CM698172
	/**
	 * Length_Result division
	 * @return	Length_Result division
	 */
	public String getFigure_ResultFlag()
	{
		return wFigure_ResultFlag;
	}

	//#CM698173
	/**
	 * Length_Planned Storage Date
	 * @return	Length_Planned Storage Date
	 */
	public String getFigure_StoragePlanDate()
	{
		return wFigure_StoragePlanDate;
	}

	//#CM698174
	/**
	 * Length_Storage Qty (Total Bulk Qty)
	 * @return	Length_Storage Qty (Total Bulk Qty)
	 */
	public String getFigure_StoragePlanQty()
	{
		return wFigure_StoragePlanQty;
	}

	//#CM698175
	/**
	 * Length_Storage Result Date
	 * @return	Length_Storage Result Date
	 */
	public String getFigure_StorageWorkDate()
	{
		return wFigure_StorageWorkDate;
	}

	//#CM698176
	/**
	 * Length_Expiry Date
	 * @return	Length_Expiry Date
	 */
	public String getFigure_UseByDate()
	{
		return wFigure_UseByDate;
	}

	//#CM698177
	/**
	 * Max Length_Packed qty per bundle
	 * @return	Max Length_Packed qty per bundle
	 */
	public String getMaxFigure_BundleEnteringQty()
	{
		return wMaxFigure_BundleEnteringQty;
	}

	//#CM698178
	/**
	 * Max Length_Bundle ITF
	 * @return	Max Length_Bundle ITF
	 */
	public String getMaxFigure_BundleItf()
	{
		return wMaxFigure_BundleItf;
	}

	//#CM698179
	/**
	 * Max Length_Result Case Qty
	 * @return	Max Length_Result Case Qty
	 */
	public String getMaxFigure_CaseResultQty()
	{
		return wMaxFigure_CaseResultQty;
	}

	//#CM698180
	/**
	 * Max Length_Case Storage Location
	 * @return	Max Length_Case Storage Location
	 */
	public String getMaxFigure_CaseStorageLocation()
	{
		return wMaxFigure_CaseStorageLocation;
	}

	//#CM698181
	/**
	 * Max Length_Consignor Code
	 * @return	Max Length_Consignor Code
	 */
	public String getMaxFigure_ConsignorCode()
	{
		return wMaxFigure_ConsignorCode;
	}

	//#CM698182
	/**
	 * Max Length_Consignor Name
	 * @return	Max Length_Consignor Name
	 */
	public String getMaxFigure_ConsignorName()
	{
		return wMaxFigure_ConsignorName;
	}

	//#CM698183
	/**
	 * Max Length_Packed Qty per Case
	 * @return	Max Length_Packed Qty per Case
	 */
	public String getMaxFigure_EnteringQty()
	{
		return wMaxFigure_EnteringQty;
	}

	//#CM698184
	/**
	 * Max Length_Item Code
	 * @return	Max Length_Item Code
	 */
	public String getMaxFigure_ItemCode()
	{
		return wMaxFigure_ItemCode;
	}

	//#CM698185
	/**
	 * Max Length_Item Name
	 * @return	Max Length_Item Name
	 */
	public String getMaxFigure_ItemName()
	{
		return wMaxFigure_ItemName;
	}

	//#CM698186
	/**
	 * Max Length_Case ITF
	 * @return	Max Length_Case ITF
	 */
	public String getMaxFigure_Itf()
	{
		return wMaxFigure_Itf;
	}

	//#CM698187
	/**
	 * Max Length_Result Piece Qty
	 * @return	Max Length_Result Piece Qty
	 */
	public String getMaxFigure_PieceResultQty()
	{
		return wMaxFigure_PieceResultQty;
	}

	//#CM698188
	/**
	 * Max Length_Piece Storage Location
	 * @return	Max Length_Piece Storage Location
	 */
	public String getMaxFigure_PieceStorageLocation()
	{
		return wMaxFigure_PieceStorageLocation;
	}

	//#CM698189
	/**
	 * Max Length_Result division
	 * @return	Max Length_Result division
	 */
	public String getMaxFigure_ResultFlag()
	{
		return wMaxFigure_ResultFlag;
	}

	//#CM698190
	/**
	 * Max Length_Planned Storage Date
	 * @return	Max Length_Planned Storage Date
	 */
	public String getMaxFigure_StoragePlanDate()
	{
		return wMaxFigure_StoragePlanDate;
	}

	//#CM698191
	/**
	 * Max Length_Storage Qty (Total Bulk Qty)
	 * @return	Max Length_Storage Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_StoragePlanQty()
	{
		return wMaxFigure_StoragePlanQty;
	}

	//#CM698192
	/**
	 * Max Length_Storage Result Date
	 * @return	Max Length_Storage Result Date
	 */
	public String getMaxFigure_StorageWorkDate()
	{
		return wMaxFigure_StorageWorkDate;
	}

	//#CM698193
	/**
	 * Max Length_Expiry Date
	 * @return	Max Length_Expiry Date
	 */
	public String getMaxFigure_UseByDate()
	{
		return wMaxFigure_UseByDate;
	}

	//#CM698194
	/**
	 * Position_Packed qty per bundle
	 * @return	Position_Packed qty per bundle
	 */
	public String getPosition_BundleEnteringQty()
	{
		return wPosition_BundleEnteringQty;
	}

	//#CM698195
	/**
	 * Position_Bundle ITF
	 * @return	Position_Bundle ITF
	 */
	public String getPosition_BundleItf()
	{
		return wPosition_BundleItf;
	}

	//#CM698196
	/**
	 * Position_Result Case Qty
	 * @return	Position_Result Case Qty
	 */
	public String getPosition_CaseResultQty()
	{
		return wPosition_CaseResultQty;
	}

	//#CM698197
	/**
	 * Position_Case Storage Location
	 * @return	Position_Case Storage Location
	 */
	public String getPosition_CaseStorageLocation()
	{
		return wPosition_CaseStorageLocation;
	}

	//#CM698198
	/**
	 * Position_Consignor Code
	 * @return	Position_Consignor Code
	 */
	public String getPosition_ConsignorCode()
	{
		return wPosition_ConsignorCode;
	}

	//#CM698199
	/**
	 * Position_Consignor Name
	 * @return	Position_Consignor Name
	 */
	public String getPosition_ConsignorName()
	{
		return wPosition_ConsignorName;
	}

	//#CM698200
	/**
	 * Position_Packed Qty per Case
	 * @return	Position_Packed Qty per Case
	 */
	public String getPosition_EnteringQty()
	{
		return wPosition_EnteringQty;
	}

	//#CM698201
	/**
	 * Position_Item Code
	 * @return	Position_Item Code
	 */
	public String getPosition_ItemCode()
	{
		return wPosition_ItemCode;
	}

	//#CM698202
	/**
	 * Position_Item Name
	 * @return	Position_Item Name
	 */
	public String getPosition_ItemName()
	{
		return wPosition_ItemName;
	}

	//#CM698203
	/**
	 * Position_Case ITF
	 * @return	Position_Case ITF
	 */
	public String getPosition_Itf()
	{
		return wPosition_Itf;
	}

	//#CM698204
	/**
	 * Position_Result Piece Qty
	 * @return	Position_Result Piece Qty
	 */
	public String getPosition_PieceResultQty()
	{
		return wPosition_PieceResultQty;
	}

	//#CM698205
	/**
	 * Position_Piece Storage Location
	 * @return	Position_Piece Storage Location
	 */
	public String getPosition_PieceStorageLocation()
	{
		return wPosition_PieceStorageLocation;
	}

	//#CM698206
	/**
	 * Position_Result division
	 * @return	Position_Result division
	 */
	public String getPosition_ResultFlag()
	{
		return wPosition_ResultFlag;
	}

	//#CM698207
	/**
	 * Position_Planned Storage Date
	 * @return	Position_Planned Storage Date
	 */
	public String getPosition_StoragePlanDate()
	{
		return wPosition_StoragePlanDate;
	}

	//#CM698208
	/**
	 * Position_Storage Qty (Total Bulk Qty)
	 * @return	Position_Storage Qty (Total Bulk Qty)
	 */
	public String getPosition_StoragePlanQty()
	{
		return wPosition_StoragePlanQty;
	}

	//#CM698209
	/**
	 * Position_Storage Result Date
	 * @return	Position_Storage Result Date
	 */
	public String getPosition_StorageWorkDate()
	{
		return wPosition_StorageWorkDate;
	}

	//#CM698210
	/**
	 * Position_Expiry Date
	 * @return	Position_Expiry Date
	 */
	public String getPosition_UseByDate()
	{
		return wPosition_UseByDate;
	}

	//#CM698211
	/**
	 * Enabled_Packed qty per bundle
	 * @return	Enabled_Packed qty per bundle
	 */
	public boolean getValid_BundleEnteringQty()
	{
		return wValid_BundleEnteringQty;
	}

	//#CM698212
	/**
	 * Enabled_Bundle ITF
	 * @return	Enabled_Bundle ITF
	 */
	public boolean getValid_BundleItf()
	{
		return wValid_BundleItf;
	}

	//#CM698213
	/**
	 * Enabled_Result Case Qty
	 * @return	Enabled_Result Case Qty
	 */
	public boolean getValid_CaseResultQty()
	{
		return wValid_CaseResultQty;
	}

	//#CM698214
	/**
	 * Enabled_Case Storage Location
	 * @return	Enabled_Case Storage Location
	 */
	public boolean getValid_CaseStorageLocation()
	{
		return wValid_CaseStorageLocation;
	}

	//#CM698215
	/**
	 * Enabled_Consignor Code
	 * @return	Enabled_Consignor Code
	 */
	public boolean getValid_ConsignorCode()
	{
		return wValid_ConsignorCode;
	}

	//#CM698216
	/**
	 * Enabled_Consignor Name
	 * @return	Enabled_Consignor Name
	 */
	public boolean getValid_ConsignorName()
	{
		return wValid_ConsignorName;
	}

	//#CM698217
	/**
	 * Enabled_Packed Qty per Case
	 * @return	Enabled_Packed Qty per Case
	 */
	public boolean getValid_EnteringQty()
	{
		return wValid_EnteringQty;
	}

	//#CM698218
	/**
	 * Enabled_Item Code
	 * @return	Enabled_Item Code
	 */
	public boolean getValid_ItemCode()
	{
		return wValid_ItemCode;
	}

	//#CM698219
	/**
	 * Enabled_Item Name
	 * @return	Enabled_Item Name
	 */
	public boolean getValid_ItemName()
	{
		return wValid_ItemName;
	}

	//#CM698220
	/**
	 * Enabled_Case ITF
	 * @return	Enabled_Case ITF
	 */
	public boolean getValid_Itf()
	{
		return wValid_Itf;
	}

	//#CM698221
	/**
	 * Enabled_Result Piece Qty
	 * @return	Enabled_Result Piece Qty
	 */
	public boolean getValid_PieceResultQty()
	{
		return wValid_PieceResultQty;
	}

	//#CM698222
	/**
	 * Enabled_Piece Storage Location
	 * @return	Enabled_Piece Storage Location
	 */
	public boolean getValid_PieceStorageLocation()
	{
		return wValid_PieceStorageLocation;
	}

	//#CM698223
	/**
	 * Enabled_Result division
	 * @return	Enabled_Result division
	 */
	public boolean getValid_ResultFlag()
	{
		return wValid_ResultFlag;
	}

	//#CM698224
	/**
	 * Enabled_Planned Storage Date
	 * @return	Enabled_Planned Storage Date
	 */
	public boolean getValid_StoragePlanDate()
	{
		return wValid_StoragePlanDate;
	}

	//#CM698225
	/**
	 * Enabled_Storage Qty (Total Bulk Qty)
	 * @return	Enabled_Storage Qty (Total Bulk Qty)
	 */
	public boolean getValid_StoragePlanQty()
	{
		return wValid_StoragePlanQty;
	}

	//#CM698226
	/**
	 * Enabled_Storage Result Date
	 * @return	Enabled_Storage Result Date
	 */
	public boolean getValid_StorageWorkDate()
	{
		return wValid_StorageWorkDate;
	}

	//#CM698227
	/**
	 * Enabled_Expiry Date
	 * @return	Enabled_Expiry Date
	 */
	public boolean getValid_UseByDate()
	{
		return wValid_UseByDate;
	}

	//#CM698228
	/**
	 * Length_Packed qty per bundle
	 * @param arg	Length_Packed qty per bundle to be set
	 */
	public void setFigure_BundleEnteringQty(String arg)
	{
		wFigure_BundleEnteringQty = arg;
	}

	//#CM698229
	/**
	 * Length_Bundle ITF
	 * @param arg	Length_Bundle ITF to be set
	 */
	public void setFigure_BundleItf(String arg)
	{
		wFigure_BundleItf = arg;
	}

	//#CM698230
	/**
	 * Length_Result Case Qty
	 * @param arg	Length_Result Case Qty to be set
	 */
	public void setFigure_CaseResultQty(String arg)
	{
		wFigure_CaseResultQty = arg;
	}

	//#CM698231
	/**
	 * Length_Case Storage Location
	 * @param arg	Length_Case Storage Location to be set
	 */
	public void setFigure_CaseStorageLocation(String arg)
	{
		wFigure_CaseStorageLocation = arg;
	}

	//#CM698232
	/**
	 * Length_Consignor Code
	 * @param arg	Length_Consignor Code to be set
	 */
	public void setFigure_ConsignorCode(String arg)
	{
		wFigure_ConsignorCode = arg;
	}

	//#CM698233
	/**
	 * Length_Consignor Name
	 * @param arg	Length_Consignor Name to be set
	 */
	public void setFigure_ConsignorName(String arg)
	{
		wFigure_ConsignorName = arg;
	}

	//#CM698234
	/**
	 * Length_Packed Qty per Case
	 * @param arg	Length_Packed Qty per Case to be set
	 */
	public void setFigure_EnteringQty(String arg)
	{
		wFigure_EnteringQty = arg;
	}

	//#CM698235
	/**
	 * Length_Item Code
	 * @param arg	Length_Item Code to be set
	 */
	public void setFigure_ItemCode(String arg)
	{
		wFigure_ItemCode = arg;
	}

	//#CM698236
	/**
	 * Length_Item Name
	 * @param arg	Length_Item Name to be set
	 */
	public void setFigure_ItemName(String arg)
	{
		wFigure_ItemName = arg;
	}

	//#CM698237
	/**
	 * Length_Case ITF
	 * @param arg	Length_Case ITF to be set
	 */
	public void setFigure_Itf(String arg)
	{
		wFigure_Itf = arg;
	}

	//#CM698238
	/**
	 * Length_Result Piece Qty
	 * @param arg	Length_Result Piece Qty to be set
	 */
	public void setFigure_PieceResultQty(String arg)
	{
		wFigure_PieceResultQty = arg;
	}

	//#CM698239
	/**
	 * Length_Piece Storage Location
	 * @param arg	Length_Piece Storage Location to be set
	 */
	public void setFigure_PieceStorageLocation(String arg)
	{
		wFigure_PieceStorageLocation = arg;
	}

	//#CM698240
	/**
	 * Length_Result division
	 * @param arg	Length_Result division to be set
	 */
	public void setFigure_ResultFlag(String arg)
	{
		wFigure_ResultFlag = arg;
	}

	//#CM698241
	/**
	 * Length_Planned Storage Date
	 * @param arg	Length_Planned Storage Date to be set
	 */
	public void setFigure_StoragePlanDate(String arg)
	{
		wFigure_StoragePlanDate = arg;
	}

	//#CM698242
	/**
	 * Length_Storage Qty (Total Bulk Qty)
	 * @param arg	Length_Storage Qty (Total Bulk Qty) to be set
	 */
	public void setFigure_StoragePlanQty(String arg)
	{
		wFigure_StoragePlanQty = arg;
	}

	//#CM698243
	/**
	 * Length_Storage Result Date
	 * @param arg	Length_Storage Result Date to be set
	 */
	public void setFigure_StorageWorkDate(String arg)
	{
		wFigure_StorageWorkDate = arg;
	}

	//#CM698244
	/**
	 * Length_Expiry Date
	 * @param arg	Length_Expiry Date to be set
	 */
	public void setFigure_UseByDate(String arg)
	{
		wFigure_UseByDate = arg;
	}

	//#CM698245
	/**
	 * Max Length_Packed qty per bundle
	 * @param arg	Max Length_Packed qty per bundle to be set
	 */
	public void setMaxFigure_BundleEnteringQty(String arg)
	{
		wMaxFigure_BundleEnteringQty = arg;
	}

	//#CM698246
	/**
	 * Max Length_Bundle ITF
	 * @param arg	Max Length_Bundle ITF to be set
	 */
	public void setMaxFigure_BundleItf(String arg)
	{
		wMaxFigure_BundleItf = arg;
	}

	//#CM698247
	/**
	 * Max Length_Result Case Qty
	 * @param arg	Max Length_Result Case Qty to be set
	 */
	public void setMaxFigure_CaseResultQty(String arg)
	{
		wMaxFigure_CaseResultQty = arg;
	}

	//#CM698248
	/**
	 * Max Length_Case Storage Location
	 * @param arg	Max Length_Case Storage Location to be set
	 */
	public void setMaxFigure_CaseStorageLocation(String arg)
	{
		wMaxFigure_CaseStorageLocation = arg;
	}

	//#CM698249
	/**
	 * Max Length_Consignor Code
	 * @param arg	Max Length_Consignor Code to be set
	 */
	public void setMaxFigure_ConsignorCode(String arg)
	{
		wMaxFigure_ConsignorCode = arg;
	}

	//#CM698250
	/**
	 * Max Length_Consignor Name
	 * @param arg	Max Length_Consignor Name to be set
	 */
	public void setMaxFigure_ConsignorName(String arg)
	{
		wMaxFigure_ConsignorName = arg;
	}

	//#CM698251
	/**
	 * Max Length_Packed Qty per Case
	 * @param arg	Max Length_Packed Qty per Case to be set
	 */
	public void setMaxFigure_EnteringQty(String arg)
	{
		wMaxFigure_EnteringQty = arg;
	}

	//#CM698252
	/**
	 * Max Length_Item Code
	 * @param arg	Max Length_Item Code to be set
	 */
	public void setMaxFigure_ItemCode(String arg)
	{
		wMaxFigure_ItemCode = arg;
	}

	//#CM698253
	/**
	 * Max Length_Item Name
	 * @param arg	Max Length_Item Name to be set
	 */
	public void setMaxFigure_ItemName(String arg)
	{
		wMaxFigure_ItemName = arg;
	}

	//#CM698254
	/**
	 * Max Length_Case ITF
	 * @param arg	Max Length_Case ITF to be set
	 */
	public void setMaxFigure_Itf(String arg)
	{
		wMaxFigure_Itf = arg;
	}

	//#CM698255
	/**
	 * Max Length_Result Piece Qty
	 * @param arg	Max Length_Result Piece Qty to be set
	 */
	public void setMaxFigure_PieceResultQty(String arg)
	{
		wMaxFigure_PieceResultQty = arg;
	}

	//#CM698256
	/**
	 * Max Length_Piece Storage Location
	 * @param arg	Max Length_Piece Storage Location to be set
	 */
	public void setMaxFigure_PieceStorageLocation(String arg)
	{
		wMaxFigure_PieceStorageLocation = arg;
	}

	//#CM698257
	/**
	 * Max Length_Result division
	 * @param arg	Max Length_Result division to be set
	 */
	public void setMaxFigure_ResultFlag(String arg)
	{
		wMaxFigure_ResultFlag = arg;
	}

	//#CM698258
	/**
	 * Max Length_Planned Storage Date
	 * @param arg	Max Length_Planned Storage Date to be set
	 */
	public void setMaxFigure_StoragePlanDate(String arg)
	{
		wMaxFigure_StoragePlanDate = arg;
	}

	//#CM698259
	/**
	 * Max Length_Storage Qty (Total Bulk Qty)
	 * @param arg	Max Length_Storage Qty (Total Bulk Qty) to be set
	 */
	public void setMaxFigure_StoragePlanQty(String arg)
	{
		wMaxFigure_StoragePlanQty = arg;
	}

	//#CM698260
	/**
	 * Max Length_Storage Result Date
	 * @param arg	Max Length_Storage Result Date to be set
	 */
	public void setMaxFigure_StorageWorkDate(String arg)
	{
		wMaxFigure_StorageWorkDate = arg;
	}

	//#CM698261
	/**
	 * Max Length_Expiry Date
	 * @param arg	Max Length_Expiry Date to be set
	 */
	public void setMaxFigure_UseByDate(String arg)
	{
		wMaxFigure_UseByDate = arg;
	}

	//#CM698262
	/**
	 * Position_Packed qty per bundle
	 * @param arg	Position_Packed qty per bundle to be set
	 */
	public void setPosition_BundleEnteringQty(String arg)
	{
		wPosition_BundleEnteringQty = arg;
	}

	//#CM698263
	/**
	 * Position_Bundle ITF
	 * @param arg	Position_Bundle ITF to be set
	 */
	public void setPosition_BundleItf(String arg)
	{
		wPosition_BundleItf = arg;
	}

	//#CM698264
	/**
	 * Position_Result Case Qty
	 * @param arg	Position_Result Case Qty to be set
	 */
	public void setPosition_CaseResultQty(String arg)
	{
		wPosition_CaseResultQty = arg;
	}

	//#CM698265
	/**
	 * Position_Case Storage Location
	 * @param arg	Position_Case Storage Location to be set
	 */
	public void setPosition_CaseStorageLocation(String arg)
	{
		wPosition_CaseStorageLocation = arg;
	}

	//#CM698266
	/**
	 * Position_Consignor Code
	 * @param arg	Position_Consignor Code to be set
	 */
	public void setPosition_ConsignorCode(String arg)
	{
		wPosition_ConsignorCode = arg;
	}

	//#CM698267
	/**
	 * Position_Consignor Name
	 * @param arg	Position_Consignor Name to be set
	 */
	public void setPosition_ConsignorName(String arg)
	{
		wPosition_ConsignorName = arg;
	}

	//#CM698268
	/**
	 * Position_Packed Qty per Case
	 * @param arg	Position_Packed Qty per Case to be set
	 */
	public void setPosition_EnteringQty(String arg)
	{
		wPosition_EnteringQty = arg;
	}

	//#CM698269
	/**
	 * Position_Item Code
	 * @param arg	Position_Item Code to be set
	 */
	public void setPosition_ItemCode(String arg)
	{
		wPosition_ItemCode = arg;
	}

	//#CM698270
	/**
	 * Position_Item Name
	 * @param arg	Position_Item Name to be set
	 */
	public void setPosition_ItemName(String arg)
	{
		wPosition_ItemName = arg;
	}

	//#CM698271
	/**
	 * Position_Case ITF
	 * @param arg	Position_Case ITF to be set
	 */
	public void setPosition_Itf(String arg)
	{
		wPosition_Itf = arg;
	}

	//#CM698272
	/**
	 * Position_Result Piece Qty
	 * @param arg	Position_Result Piece Qty to be set
	 */
	public void setPosition_PieceResultQty(String arg)
	{
		wPosition_PieceResultQty = arg;
	}

	//#CM698273
	/**
	 * Position_Piece Storage Location
	 * @param arg	Position_Piece Storage Location to be set
	 */
	public void setPosition_PieceStorageLocation(String arg)
	{
		wPosition_PieceStorageLocation = arg;
	}

	//#CM698274
	/**
	 * Position_Result division
	 * @param arg	Position_Result division to be set
	 */
	public void setPosition_ResultFlag(String arg)
	{
		wPosition_ResultFlag = arg;
	}

	//#CM698275
	/**
	 * Position_Planned Storage Date
	 * @param arg	Position_Planned Storage Date to be set
	 */
	public void setPosition_StoragePlanDate(String arg)
	{
		wPosition_StoragePlanDate = arg;
	}

	//#CM698276
	/**
	 * Position_Storage Qty (Total Bulk Qty)
	 * @param arg	Position_Storage Qty (Total Bulk Qty) to be set
	 */
	public void setPosition_StoragePlanQty(String arg)
	{
		wPosition_StoragePlanQty = arg;
	}

	//#CM698277
	/**
	 * Position_Storage Result Date
	 * @param arg	Position_Storage Result Date to be set
	 */
	public void setPosition_StorageWorkDate(String arg)
	{
		wPosition_StorageWorkDate = arg;
	}

	//#CM698278
	/**
	 * Position_Expiry Date
	 * @param arg	Position_Expiry Date to be set
	 */
	public void setPosition_UseByDate(String arg)
	{
		wPosition_UseByDate = arg;
	}

	//#CM698279
	/**
	 * Enabled_Packed qty per bundle
	 * @param arg	Enabled_Packed qty per bundle to be set
	 */
	public void setValid_BundleEnteringQty(boolean arg)
	{
		wValid_BundleEnteringQty = arg;
	}

	//#CM698280
	/**
	 * Enabled_Bundle ITF
	 * @param arg	Enabled_Bundle ITF to be set
	 */
	public void setValid_BundleItf(boolean arg)
	{
		wValid_BundleItf = arg;
	}

	//#CM698281
	/**
	 * Enabled_Result Case Qty
	 * @param arg	Enabled_Result Case Qty to be set
	 */
	public void setValid_CaseResultQty(boolean arg)
	{
		wValid_CaseResultQty = arg;
	}

	//#CM698282
	/**
	 * Enabled_Case Storage Location
	 * @param arg	Enabled_Case Storage Location to be set
	 */
	public void setValid_CaseStorageLocation(boolean arg)
	{
		wValid_CaseStorageLocation = arg;
	}

	//#CM698283
	/**
	 * Enabled_Consignor Code
	 * @param arg	Enabled_Consignor Code to be set
	 */
	public void setValid_ConsignorCode(boolean arg)
	{
		wValid_ConsignorCode = arg;
	}

	//#CM698284
	/**
	 * Enabled_Consignor Name
	 * @param arg	Enabled_Consignor Name to be set
	 */
	public void setValid_ConsignorName(boolean arg)
	{
		wValid_ConsignorName = arg;
	}

	//#CM698285
	/**
	 * Enabled_Packed Qty per Case
	 * @param arg	Enabled_Packed Qty per Case to be set
	 */
	public void setValid_EnteringQty(boolean arg)
	{
		wValid_EnteringQty = arg;
	}

	//#CM698286
	/**
	 * Enabled_Item Code
	 * @param arg	Enabled_Item Code to be set
	 */
	public void setValid_ItemCode(boolean arg)
	{
		wValid_ItemCode = arg;
	}

	//#CM698287
	/**
	 * Enabled_Item Name
	 * @param arg	Enabled_Item Name to be set
	 */
	public void setValid_ItemName(boolean arg)
	{
		wValid_ItemName = arg;
	}

	//#CM698288
	/**
	 * Enabled_Case ITF
	 * @param arg	Enabled_Case ITF to be set
	 */
	public void setValid_Itf(boolean arg)
	{
		wValid_Itf = arg;
	}

	//#CM698289
	/**
	 * Enabled_Result Piece Qty
	 * @param arg	Enabled_Result Piece Qty to be set
	 */
	public void setValid_PieceResultQty(boolean arg)
	{
		wValid_PieceResultQty = arg;
	}

	//#CM698290
	/**
	 * Enabled_Piece Storage Location
	 * @param arg	Enabled_Piece Storage Location to be set
	 */
	public void setValid_PieceStorageLocation(boolean arg)
	{
		wValid_PieceStorageLocation = arg;
	}

	//#CM698291
	/**
	 * Enabled_Result division
	 * @param arg	Enabled_Result division to be set
	 */
	public void setValid_ResultFlag(boolean arg)
	{
		wValid_ResultFlag = arg;
	}

	//#CM698292
	/**
	 * Enabled_Planned Storage Date
	 * @param arg	Enabled_Planned Storage Date to be set
	 */
	public void setValid_StoragePlanDate(boolean arg)
	{
		wValid_StoragePlanDate = arg;
	}

	//#CM698293
	/**
	 * Enabled_Storage Qty (Total Bulk Qty)
	 * @param arg	Enabled_Storage Qty (Total Bulk Qty) to be set
	 */
	public void setValid_StoragePlanQty(boolean arg)
	{
		wValid_StoragePlanQty = arg;
	}

	//#CM698294
	/**
	 * Enabled_Storage Result Date
	 * @param arg	Enabled_Storage Result Date to be set
	 */
	public void setValid_StorageWorkDate(boolean arg)
	{
		wValid_StorageWorkDate = arg;
	}

	//#CM698295
	/**
	 * Enabled_Expiry Date
	 * @param arg	Enabled_Expiry Date to be set
	 */
	public void setValid_UseByDate(boolean arg)
	{
		wValid_UseByDate = arg;
	}

}
//#CM698296
//end of class
