//#CM696974
//$Id: DefineMovementDataParameter.java,v 1.2 2006/11/13 06:03:11 suresh Exp $

//#CM696975
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import jp.co.daifuku.wms.base.common.Parameter;
 
//#CM696976
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * Use the <CODE>DefineMovementDataParameter</CODE> class to pass a parameter between the screen and the schedule for setting a field item to report the stock relocation data.<BR>
 * Allow this class to maintain the field items to be used in each screen for system package. Use a field item depending on the screen.<BR>
 * <BR>
 * <DIR>
 * Allow the <CODE>DefineMovementDataParameter</CODE> class to maintain the following field items.<BR>
 * <BR>
 * [Textbox or Label] <BR>
 * <DIR>
 *     Worker Code <BR>
 *     Password <BR>
 * <BR>
 *     Length_Consignor Code <BR>
 *     Length_Consignor Name <BR>
 *     Length_Item Code <BR>
 *     Length_Item Name <BR>
 *     Length_Packed Qty per Case <BR>
 *     Length_Relocated qty (Total Bulk Qty) <BR>
 *     Length_Relocation Source Location <BR>
 *     Length_Relocation Target Location <BR>
 *     Length_Relocation Result Date <BR> 
 * <BR>
 *     Max Length_Consignor Code <BR>
 *     Max Length_Consignor Name <BR>
 *     Max Length_Item Code <BR>
 *     Max Length_Item Name <BR>
 *     Max Length_Packed Qty per Case <BR>
 *     Max Length_Relocated qty (Total Bulk Qty) <BR>
 *     Max Length_Relocation Source Location <BR>
 *     Max Length_Relocation Target Location <BR>
 *     Max Length_Relocation Result Date <BR> 
 * <BR>
 *     Position_Consignor Code <BR>
 *     Position_Consignor Name <BR>
 *     Position_Item Code <BR>
 *     Position_Item Name <BR>
 *     Position_Packed Qty per Case <BR>
 *     Position_Relocated qty (Total Bulk Qty) <BR>
 *     Position_Relocation Source Location <BR>
 *     Position_Relocation Target Location <BR>
 *     Position_Relocation Result Date <BR> 
 * </DIR>
 * [Checkbox] <BR>
 * <DIR>
 *     Enabled_Consignor Code <BR>
 *     Enabled_Consignor Name <BR>
 *     Enabled_Item Code <BR>
 *     Enabled_Item Name <BR>
 *     Enabled_Packed Qty per Case <BR>
 *     Enabled_Relocated qty (Total Bulk Qty) <BR>
 *     Enabled_Relocation Source Location <BR>
 *     Enabled_Relocation Target Location <BR>
 *     Enabled_Relocation Result Date <BR> 
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/05</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:11 $
 * @author  $Author: suresh $
 */
public class DefineMovementDataParameter extends Parameter
{
	//#CM696977
	// Class variables -----------------------------------------------
	//#CM696978
	/**
	 * Worker Code
	 */
	private String wWorkerCode;
	
	//#CM696979
	/**
	 * Password
	 */
	private String wPassword;
	
	//#CM696980
	/**
	 * Length_Consignor Code
	 */
	private String wFigure_ConsignorCode;
	
	//#CM696981
	/**
	 * Length_Consignor Name
	 */
	private String wFigure_ConsignorName;
	
	//#CM696982
	/**
	 * Length_Item Code
	 */
	private String wFigure_ItemCode;
	
	//#CM696983
	/**
	 * Length_Item Name
	 */
	private String wFigure_ItemName;
	
	//#CM696984
	/**
	 * Length_Packed Qty per Case
	 */
	private String wFigure_EnteringQty;
	
	//#CM696985
	/**
	 * Length_Relocated qty (Total Bulk Qty)
	 */
	private String wFigure_MovementResultQty;
	
	//#CM696986
	/**
	 * Length_Relocation Source Location
	 */
	private String wFigure_LocationNo;
	
	//#CM696987
	/**
	 * Length_Relocation Target Location
	 */
	private String wFigure_ResultLocationNo;
	
	//#CM696988
	/**
	 * Length_Relocation Result Date
	 */
	private String wFigure_MovementWorkDate;
	
	//#CM696989
	/**
	 * Max Length_Consignor Code
	 */
	private String wMaxFigure_ConsignorCode;
	
	//#CM696990
	/**
	 * Max Length_Consignor Name
	 */
	private String wMaxFigure_ConsignorName;
	
	//#CM696991
	/**
	 * Max Length_Item Code
	 */
	private String wMaxFigure_ItemCode;
	
	//#CM696992
	/**
	 * Max Length_Item Name
	 */
	private String wMaxFigure_ItemName;
	
	//#CM696993
	/**
	 * Max Length_Packed Qty per Case
	 */
	private String wMaxFigure_EnteringQty;
	
	//#CM696994
	/**
	 * Max Length_Relocated qty (Total Bulk Qty)
	 */
	private String wMaxFigure_MovementResultQty;
	
	//#CM696995
	/**
	 * Max Length_Relocation Source Location
	 */
	private String wMaxFigure_LocationNo;
	
	//#CM696996
	/**
	 * Max Length_Relocation Target Location
	 */
	private String wMaxFigure_ResultLocationNo;
	
	//#CM696997
	/**
	 * Max Length_Relocation Result Date
	 */
	private String wMaxFigure_MovementWorkDate;
	
	//#CM696998
	/**
	 * Position_Consignor Code
	 */
	private String wPosition_ConsignorCode;
	
	//#CM696999
	/**
	 * Position_Consignor Name
	 */
	private String wPosition_ConsignorName;
	
	//#CM697000
	/**
	 * Position_Item Code
	 */
	private String wPosition_ItemCode;
	
	//#CM697001
	/**
	 * Position_Item Name
	 */
	private String wPosition_ItemName;
	
	//#CM697002
	/**
	 * Position_Packed Qty per Case
	 */
	private String wPosition_EnteringQty;
	
	//#CM697003
	/**
	 * Position_Relocated qty (Total Bulk Qty)
	 */
	private String wPosition_MovementResultQty;
	
	//#CM697004
	/**
	 * Position_Relocation Source Location
	 */
	private String wPosition_LocationNo;
	
	//#CM697005
	/**
	 * Position_Relocation Target Location
	 */
	private String wPosition_ResultLocationNo;
	
	//#CM697006
	/**
	 * Position_Relocation Result Date
	 */
	private String wPosition_MovementWorkDate;
	
	//#CM697007
	/**
	 * Enabled_Consignor Code
	 */
	private boolean wValid_ConsignorCode;
	
	//#CM697008
	/**
	 * Enabled_Consignor Name
	 */
	private boolean wValid_ConsignorName;
	
	//#CM697009
	/**
	 * Enabled_Item Code
	 */
	private boolean wValid_ItemCode;
	
	//#CM697010
	/**
	 * Enabled_Item Name
	 */
	private boolean wValid_ItemName;
	
	//#CM697011
	/**
	 * Enabled_Packed Qty per Case
	 */
	private boolean wValid_EnteringQty;
	
	//#CM697012
	/**
	 * Enabled_Relocated qty (Total Bulk Qty)
	 */
	private boolean wValid_MovementResultQty;
	
	//#CM697013
	/**
	 * Enabled_Relocation Source Location
	 */
	private boolean wValid_LocationNo;
	
	//#CM697014
	/**
	 * Enabled_Relocation Target Location
	 */
	private boolean wValid_ResultLocationNo;
	
	//#CM697015
	/**
	 * Enabled_Relocation Result Date
	 */
	private boolean wValid_MovementWorkDate;
	

	//#CM697016
	// Class method --------------------------------------------------
	//#CM697017
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:11 $");
	}

	//#CM697018
	// Constructors --------------------------------------------------
	//#CM697019
	/**
	 * Initialize this class.
	 */
	public DefineMovementDataParameter()
	{
	}
	
	//#CM697020
	// Public methods ------------------------------------------------

	//#CM697021
	/**
	 * Worker Code
	 * @return	Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM697022
	/**
	 * Password
	 * @return	Password
	 */
	public String getPassword()
	{
		return wPassword;
	}



	//#CM697023
	/**
	 * Length_Consignor Code
	 * @return	Length_Consignor Code
	 */
	public String getFigure_ConsignorCode()
	{
		return wFigure_ConsignorCode;
	}

	//#CM697024
	/**
	 * Length_Consignor Name
	 * @return	Length_Consignor Name
	 */
	public String getFigure_ConsignorName()
	{
		return wFigure_ConsignorName;
	}

	//#CM697025
	/**
	 * Length_Packed Qty per Case
	 * @return	Length_Packed Qty per Case
	 */
	public String getFigure_EnteringQty()
	{
		return wFigure_EnteringQty;
	}

	//#CM697026
	/**
	 * Length_Item Code
	 * @return	Length_Item Code
	 */
	public String getFigure_ItemCode()
	{
		return wFigure_ItemCode;
	}

	//#CM697027
	/**
	 * Length_Item Name
	 * @return	Length_Item Name
	 */
	public String getFigure_ItemName()
	{
		return wFigure_ItemName;
	}

	//#CM697028
	/**
	 * Length_Relocation Source Location
	 * @return	Length_Relocation Source Location
	 */
	public String getFigure_LocationNo()
	{
		return wFigure_LocationNo;
	}

	//#CM697029
	/**
	 * Length_Relocated qty (Total Bulk Qty)
	 * @return	Length_Relocated qty (Total Bulk Qty)
	 */
	public String getFigure_MovementResultQty()
	{
		return wFigure_MovementResultQty;
	}

	//#CM697030
	/**
	 * Length_Relocation Result Date
	 * @return	Length_Relocation Result Date
	 */
	public String getFigure_MovementWorkDate()
	{
		return wFigure_MovementWorkDate;
	}

	//#CM697031
	/**
	 * Length_Relocation Target Location
	 * @return	Length_Relocation Target Location
	 */
	public String getFigure_ResultLocationNo()
	{
		return wFigure_ResultLocationNo;
	}

	//#CM697032
	/**
	 * Max Length_Consignor Code
	 * @return	Max Length_Consignor Code
	 */
	public String getMaxFigure_ConsignorCode()
	{
		return wMaxFigure_ConsignorCode;
	}

	//#CM697033
	/**
	 * Max Length_Consignor Name
	 * @return	Max Length_Consignor Name
	 */
	public String getMaxFigure_ConsignorName()
	{
		return wMaxFigure_ConsignorName;
	}

	//#CM697034
	/**
	 * Max Length_Packed Qty per Case
	 * @return	Max Length_Packed Qty per Case
	 */
	public String getMaxFigure_EnteringQty()
	{
		return wMaxFigure_EnteringQty;
	}

	//#CM697035
	/**
	 * Max Length_Item Code
	 * @return	Max Length_Item Code
	 */
	public String getMaxFigure_ItemCode()
	{
		return wMaxFigure_ItemCode;
	}

	//#CM697036
	/**
	 * Max Length_Item Name
	 * @return	Max Length_Item Name
	 */
	public String getMaxFigure_ItemName()
	{
		return wMaxFigure_ItemName;
	}

	//#CM697037
	/**
	 * Max Length_Relocation Source Location
	 * @return	Max Length_Relocation Source Location
	 */
	public String getMaxFigure_LocationNo()
	{
		return wMaxFigure_LocationNo;
	}

	//#CM697038
	/**
	 * Max Length_Relocated qty (Total Bulk Qty)
	 * @return	Max Length_Relocated qty (Total Bulk Qty)
	 */
	public String getMaxFigure_MovementResultQty()
	{
		return wMaxFigure_MovementResultQty;
	}

	//#CM697039
	/**
	 * Max Length_Relocation Result Date
	 * @return	Max Length_Relocation Result Date
	 */
	public String getMaxFigure_MovementWorkDate()
	{
		return wMaxFigure_MovementWorkDate;
	}

	//#CM697040
	/**
	 * Max Length_Relocation Target Location
	 * @return	Max Length_Relocation Target Location
	 */
	public String getMaxFigure_ResultLocationNo()
	{
		return wMaxFigure_ResultLocationNo;
	}

	//#CM697041
	/**
	 * Position_Consignor Code
	 * @return	Position_Consignor Code
	 */
	public String getPosition_ConsignorCode()
	{
		return wPosition_ConsignorCode;
	}

	//#CM697042
	/**
	 * Position_Consignor Name
	 * @return	Position_Consignor Name
	 */
	public String getPosition_ConsignorName()
	{
		return wPosition_ConsignorName;
	}

	//#CM697043
	/**
	 * Position_Packed Qty per Case
	 * @return	Position_Packed Qty per Case
	 */
	public String getPosition_EnteringQty()
	{
		return wPosition_EnteringQty;
	}

	//#CM697044
	/**
	 * Position_Item Code
	 * @return	Position_Item Code
	 */
	public String getPosition_ItemCode()
	{
		return wPosition_ItemCode;
	}

	//#CM697045
	/**
	 * Position_Item Name
	 * @return	Position_Item Name
	 */
	public String getPosition_ItemName()
	{
		return wPosition_ItemName;
	}

	//#CM697046
	/**
	 * Position_Relocation Source Location
	 * @return	Position_Relocation Source Location
	 */
	public String getPosition_LocationNo()
	{
		return wPosition_LocationNo;
	}

	//#CM697047
	/**
	 * Position_Relocated qty (Total Bulk Qty)
	 * @return	Position_Relocated qty (Total Bulk Qty)
	 */
	public String getPosition_MovementResultQty()
	{
		return wPosition_MovementResultQty;
	}

	//#CM697048
	/**
	 * Position_Relocation Result Date
	 * @return	Position_Relocation Result Date
	 */
	public String getPosition_MovementWorkDate()
	{
		return wPosition_MovementWorkDate;
	}

	//#CM697049
	/**
	 * Position_Relocation Target Location
	 * @return	Position_Relocation Target Location
	 */
	public String getPosition_ResultLocationNo()
	{
		return wPosition_ResultLocationNo;
	}

	//#CM697050
	/**
	 * Enabled_Consignor Code
	 * @return	Enabled_Consignor Code
	 */
	public boolean getValid_ConsignorCode()
	{
		return wValid_ConsignorCode;
	}

	//#CM697051
	/**
	 * Enabled_Consignor Name
	 * @return	Enabled_Consignor Name
	 */
	public boolean getValid_ConsignorName()
	{
		return wValid_ConsignorName;
	}

	//#CM697052
	/**
	 * Enabled_Packed Qty per Case
	 * @return	Enabled_Packed Qty per Case
	 */
	public boolean getValid_EnteringQty()
	{
		return wValid_EnteringQty;
	}

	//#CM697053
	/**
	 * Enabled_Item Code
	 * @return	Enabled_Item Code
	 */
	public boolean getValid_ItemCode()
	{
		return wValid_ItemCode;
	}

	//#CM697054
	/**
	 * Enabled_Item Name
	 * @return	Enabled_Item Name
	 */
	public boolean getValid_ItemName()
	{
		return wValid_ItemName;
	}

	//#CM697055
	/**
	 * Enabled_Relocation Source Location
	 * @return	Enabled_Relocation Source Location
	 */
	public boolean getValid_LocationNo()
	{
		return wValid_LocationNo;
	}

	//#CM697056
	/**
	 * Enabled_Relocated qty (Total Bulk Qty)
	 * @return	Enabled_Relocated qty (Total Bulk Qty)
	 */
	public boolean getValid_MovementResultQty()
	{
		return wValid_MovementResultQty;
	}

	//#CM697057
	/**
	 * Enabled_Relocation Result Date
	 * @return	Enabled_Relocation Result Date
	 */
	public boolean getValid_MovementWorkDate()
	{
		return wValid_MovementWorkDate;
	}

	//#CM697058
	/**
	 * Enabled_Relocation Target Location
	 * @return	Enabled_Relocation Target Location
	 */
	public boolean getValid_ResultLocationNo()
	{
		return wValid_ResultLocationNo;
	}

	//#CM697059
	/**
	 * Length_Consignor Code
	 * @param arg	Length_Consignor Code to be set
	 */
	public void setFigure_ConsignorCode(String arg)
	{
		wFigure_ConsignorCode = arg;
	}

	//#CM697060
	/**
	 * Length_Consignor Name
	 * @param arg	Length_Consignor Name to be set
	 */
	public void setFigure_ConsignorName(String arg)
	{
		wFigure_ConsignorName = arg;
	}

	//#CM697061
	/**
	 * Length_Packed Qty per Case
	 * @param arg	Length_Packed Qty per Case to be set
	 */
	public void setFigure_EnteringQty(String arg)
	{
		wFigure_EnteringQty = arg;
	}

	//#CM697062
	/**
	 * Length_Item Code
	 * @param arg	Length_Item Code to be set
	 */
	public void setFigure_ItemCode(String arg)
	{
		wFigure_ItemCode = arg;
	}

	//#CM697063
	/**
	 * Length_Item Name
	 * @param arg	Length_Item Name to be set
	 */
	public void setFigure_ItemName(String arg)
	{
		wFigure_ItemName = arg;
	}

	//#CM697064
	/**
	 * Length_Relocation Source Location
	 * @param arg	Length_Relocation Source Location to be set
	 */
	public void setFigure_LocationNo(String arg)
	{
		wFigure_LocationNo = arg;
	}

	//#CM697065
	/**
	 * Length_Relocated qty (Total Bulk Qty)
	 * @param arg	Length_Relocated qty (Total Bulk Qty) to be set
	 */
	public void setFigure_MovementResultQty(String arg)
	{
		wFigure_MovementResultQty = arg;
	}

	//#CM697066
	/**
	 * Length_Relocation Result Date
	 * @param arg	Length_Relocation Result Date to be set
	 */
	public void setFigure_MovementWorkDate(String arg)
	{
		wFigure_MovementWorkDate = arg;
	}

	//#CM697067
	/**
	 * Length_Relocation Target Location
	 * @param arg	Length_Relocation Target Location to be set
	 */
	public void setFigure_ResultLocationNo(String arg)
	{
		wFigure_ResultLocationNo = arg;
	}

	//#CM697068
	/**
	 * Max Length_Consignor Code
	 * @param arg	Max Length_Consignor Code to be set
	 */
	public void setMaxFigure_ConsignorCode(String arg)
	{
		wMaxFigure_ConsignorCode = arg;
	}

	//#CM697069
	/**
	 * Max Length_Consignor Name
	 * @param arg	Max Length_Consignor Name to be set
	 */
	public void setMaxFigure_ConsignorName(String arg)
	{
		wMaxFigure_ConsignorName = arg;
	}

	//#CM697070
	/**
	 * Max Length_Packed Qty per Case
	 * @param arg	Max Length_Packed Qty per Case to be set
	 */
	public void setMaxFigure_EnteringQty(String arg)
	{
		wMaxFigure_EnteringQty = arg;
	}

	//#CM697071
	/**
	 * Max Length_Item Code
	 * @param arg	Max Length_Item Code to be set
	 */
	public void setMaxFigure_ItemCode(String arg)
	{
		wMaxFigure_ItemCode = arg;
	}

	//#CM697072
	/**
	 * Max Length_Item Name
	 * @param arg	Max Length_Item Name to be set
	 */
	public void setMaxFigure_ItemName(String arg)
	{
		wMaxFigure_ItemName = arg;
	}

	//#CM697073
	/**
	 * Max Length_Relocation Source Location
	 * @param arg	Max Length_Relocation Source Location to be set
	 */
	public void setMaxFigure_LocationNo(String arg)
	{
		wMaxFigure_LocationNo = arg;
	}

	//#CM697074
	/**
	 * Max Length_Relocated qty (Total Bulk Qty)
	 * @param arg	Max Length_Relocated qty (Total Bulk Qty) to be set
	 */
	public void setMaxFigure_MovementResultQty(String arg)
	{
		wMaxFigure_MovementResultQty = arg;
	}

	//#CM697075
	/**
	 * Max Length_Relocation Result Date
	 * @param arg	Max Length_Relocation Result Date to be set
	 */
	public void setMaxFigure_MovementWorkDate(String arg)
	{
		wMaxFigure_MovementWorkDate = arg;
	}

	//#CM697076
	/**
	 * Max Length_Relocation Target Location
	 * @param arg	Max Length_Relocation Target Location to be set
	 */
	public void setMaxFigure_ResultLocationNo(String arg)
	{
		wMaxFigure_ResultLocationNo = arg;
	}

	//#CM697077
	/**
	 * Position_Consignor Code
	 * @param arg	Position_Consignor Code to be set
	 */
	public void setPosition_ConsignorCode(String arg)
	{
		wPosition_ConsignorCode = arg;
	}

	//#CM697078
	/**
	 * Position_Consignor Name
	 * @param arg	Position_Consignor Name to be set
	 */
	public void setPosition_ConsignorName(String arg)
	{
		wPosition_ConsignorName = arg;
	}

	//#CM697079
	/**
	 * Position_Packed Qty per Case
	 * @param arg	Position_Packed Qty per Case to be set
	 */
	public void setPosition_EnteringQty(String arg)
	{
		wPosition_EnteringQty = arg;
	}

	//#CM697080
	/**
	 * Position_Item Code
	 * @param arg	Position_Item Code to be set
	 */
	public void setPosition_ItemCode(String arg)
	{
		wPosition_ItemCode = arg;
	}

	//#CM697081
	/**
	 * Position_Item Name
	 * @param arg	Position_Item Name to be set
	 */
	public void setPosition_ItemName(String arg)
	{
		wPosition_ItemName = arg;
	}

	//#CM697082
	/**
	 * Position_Relocation Source Location
	 * @param arg	Position_Relocation Source Location to be set
	 */
	public void setPosition_LocationNo(String arg)
	{
		wPosition_LocationNo = arg;
	}

	//#CM697083
	/**
	 * Position_Relocated qty (Total Bulk Qty)
	 * @param arg	Position_Relocated qty (Total Bulk Qty) to be set
	 */
	public void setPosition_MovementResultQty(String arg)
	{
		wPosition_MovementResultQty = arg;
	}

	//#CM697084
	/**
	 * Position_Relocation Result Date
	 * @param arg	Position_Relocation Result Date to be set
	 */
	public void setPosition_MovementWorkDate(String arg)
	{
		wPosition_MovementWorkDate = arg;
	}

	//#CM697085
	/**
	 * Position_Relocation Target Location
	 * @param arg	Position_Relocation Target Location to be set
	 */
	public void setPosition_ResultLocationNo(String arg)
	{
		wPosition_ResultLocationNo = arg;
	}

	//#CM697086
	/**
	 * Enabled_Consignor Code
	 * @param arg	Enabled_Consignor Code to be set
	 */
	public void setValid_ConsignorCode(boolean arg)
	{
		wValid_ConsignorCode = arg;
	}

	//#CM697087
	/**
	 *  Enabled_Consignor Name
	 * @param arg	Enabled_Consignor Name to be set
	 */
	public void setValid_ConsignorName(boolean arg)
	{
		wValid_ConsignorName = arg;
	}

	//#CM697088
	/**
	 * Enabled_Packed Qty per Case
	 * @param arg	Enabled_Packed Qty per Case to be set
	 */
	public void setValid_EnteringQty(boolean arg)
	{
		wValid_EnteringQty = arg;
	}

	//#CM697089
	/**
	 * Enabled_Item Code
	 * @param arg	Enabled_Item Code to be set
	 */
	public void setValid_ItemCode(boolean arg)
	{
		wValid_ItemCode = arg;
	}

	//#CM697090
	/**
	 * Enabled_Item Name
	 * @param arg	Enabled_Item Name to be set
	 */
	public void setValid_ItemName(boolean arg)
	{
		wValid_ItemName = arg;
	}

	//#CM697091
	/**
	 * Enabled_Relocation Source Location
	 * @param arg	Enabled_Relocation Source Location to be set
	 */
	public void setValid_LocationNo(boolean arg)
	{
		wValid_LocationNo = arg;
	}

	//#CM697092
	/**
	 * Enabled_Relocated qty (Total Bulk Qty)
	 * @param arg	Enabled_Relocated qty (Total Bulk Qty) to be set
	 */
	public void setValid_MovementResultQty(boolean arg)
	{
		wValid_MovementResultQty = arg;
	}

	//#CM697093
	/**
	 * Enabled_Relocation Result Date
	 * @param arg	Enabled_Relocation Result Date to be set
	 */
	public void setValid_MovementWorkDate(boolean arg)
	{
		wValid_MovementWorkDate = arg;
	}

	//#CM697094
	/**
	 * Enabled_Relocation Target Location
	 * @param arg	Enabled_Relocation Target Location to be set
	 */
	public void setValid_ResultLocationNo(boolean arg)
	{
		wValid_ResultLocationNo = arg;
	}

}
//#CM697095
//end of class
