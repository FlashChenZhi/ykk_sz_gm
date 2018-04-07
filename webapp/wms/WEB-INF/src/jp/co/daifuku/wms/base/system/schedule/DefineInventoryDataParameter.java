//#CM696778
//$Id: DefineInventoryDataParameter.java,v 1.2 2006/11/13 06:03:12 suresh Exp $

//#CM696779
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import jp.co.daifuku.wms.base.common.Parameter;
 
//#CM696780
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * Use the <CODE>DefineInventoryDataParameter</CODE> class to pass a parameter between the screen and the schedule for setting field items to be reported for the Inventory check data.<BR>
 * Allow this class to maintain the field items to be used in each screen for system package. Use a field item depending on the screen.<BR>
 * <BR>
 * <DIR>
 * Allow the <CODE>DefineInventoryDataParameter</CODE> class to maintain the following field items.<BR>
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
 *     Length_System Stock Qty (Total Bulk Qty) <BR>
 *     Length_Inventory CheckResult Qty (Total Bulk Qty) <BR>
 *     Length_Inventory Check Location <BR>
 *     Length_Inventory Check Result Date <BR>
 * <BR>
 *     Max Length_Consignor Code <BR>
 *     Max Length_Consignor Name <BR>
 *     Max Length_Item Code <BR>
 *     Max Length_Item Name <BR>
 *     Max Length_Packed Qty per Case <BR>
 *     Max Length_System Stock Qty (Total Bulk Qty) <BR>
 *     Max Length_Inventory CheckResult Qty (Total Bulk Qty) <BR>
 *     Max Length_Inventory Check Location <BR>
 *     Max Length_Inventory Check Result Date <BR>
 * <BR>
 *     Position_Consignor Code <BR>
 *     Position_Consignor Name <BR>
 *     Position_Item Code <BR>
 *     Position_Item Name <BR>
 *     Position_Packed Qty per Case <BR>
 *     Position_System Stock Qty (Total Bulk Qty) <BR>
 *     Position_Inventory CheckResult Qty (Total Bulk Qty) <BR>
 *     Position_Inventory Check Location <BR>
 *     Position_Inventory Check Result Date <BR>
 * </DIR>
 * [Checkbox] <BR>
 * <DIR>
 *     Enabled_Consignor Code <BR>
 *     Enabled_Consignor Name <BR>
 *     Enabled_Item Code <BR>
 *     Enabled_Item Name <BR>
 *     Enabled_Packed Qty per Case <BR>
 *     Enabled_System Stock Qty (Total Bulk Qty) <BR>
 *     Enabled_Inventory CheckResult Qty (Total Bulk Qty) <BR>
 *     Enabled_Inventory Check Location <BR>
 *     Enabled_Inventory Check Result Date <BR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/05</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:12 $
 * @author  $Author: suresh $
 */
public class DefineInventoryDataParameter extends Parameter
{
	//#CM696781
	// Class variables -----------------------------------------------
	//#CM696782
	/**
	 * Worker Code
	 */
	private String wWorkerCode;
	
	//#CM696783
	/**
	 * Password
	 */
	private String wPassword;
	
	//#CM696784
	/**
	 * Length_Consignor Code
	 */
	private String wFigure_ConsignorCode;
	
	//#CM696785
	/**
	 * Length_Consignor Name
	 */
	private String wFigure_ConsignorName;
	
	//#CM696786
	/**
	 * Length_Item Code
	 */
	private String wFigure_ItemCode;
	
	//#CM696787
	/**
	 * Length_Item Name
	 */
	private String wFigure_ItemName;
	
	//#CM696788
	/**
	 * Length_Packed Qty per Case
	 */
	private String wFigure_EnteringQty;
	
	//#CM696789
	/**
	 * Length_System Stock Qty (Total Bulk Qty)
	 */
	private String wFigure_StockQty;
	
	//#CM696790
	/**
	 * Length_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	private String wFigure_ResultStockQty;
	
	//#CM696791
	/**
	 * Length_Inventory Check Location
	 */
	private String wFigure_LocationNo;
	
	//#CM696792
	/**
	 * Length_Inventory Check Result Date
	 */
	private String wFigure_InventoryWorkDate;
	
	//#CM696793
	/**
	 * Max Length_Consignor Code
	 */
	private String wMaxFigure_ConsignorCode;
	
	//#CM696794
	/**
	 * Max Length_Consignor Name
	 */
	private String wMaxFigure_ConsignorName;
	
	//#CM696795
	/**
	 * Max Length_Item Code
	 */
	private String wMaxFigure_ItemCode;
	
	//#CM696796
	/**
	 * Max Length_Item Name
	 */
	private String wMaxFigure_ItemName;
	
	//#CM696797
	/**
	 * Max Length_Packed Qty per Case
	 */
	private String wMaxFigure_EnteringQty;
	
	//#CM696798
	/**
	 * Max Length_System Stock Qty (Total Bulk Qty)
	 */
	private String wMaxFigure_StockQty;
	
	//#CM696799
	/**
	 * Max Length_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	private String wMaxFigure_ResultStockQty;
	
	//#CM696800
	/**
	 * Max Length_Inventory Check Location
	 */
	private String wMaxFigure_LocationNo;
	
	//#CM696801
	/**
	 * Max Length_Inventory Check Result Date
	 */
	private String wMaxFigure_InventoryWorkDate;

	//#CM696802
	/**
	 * Position_Consignor Code
	 */
	private String wPosition_ConsignorCode;
	
	//#CM696803
	/**
	 * Position_Consignor Name
	 */
	private String wPosition_ConsignorName;
	
	//#CM696804
	/**
	 * Position_Item Code
	 */
	private String wPosition_ItemCode;
	
	//#CM696805
	/**
	 * Position_Item Name
	 */
	private String wPosition_ItemName;
	
	//#CM696806
	/**
	 * Position_Packed Qty per Case
	 */
	private String wPosition_EnteringQty;
	
	//#CM696807
	/**
	 * Position_System Stock Qty (Total Bulk Qty)
	 */
	private String wPosition_StockQty;
	
	//#CM696808
	/**
	 * Position_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	private String wPosition_ResultStockQty;
	
	//#CM696809
	/**
	 * Position_Inventory Check Location
	 */
	private String wPosition_LocationNo;
	
	//#CM696810
	/**
	 * Position_Inventory Check Result Date
	 */
	private String wPosition_InventoryWorkDate;
	
	//#CM696811
	/**
	 * Enabled_Consignor Code
	 */
	private boolean wValid_ConsignorCode;
	
	//#CM696812
	/**
	 * Enabled_Consignor Name
	 */
	private boolean wValid_ConsignorName;
	
	//#CM696813
	/**
	 * Enabled_Item Code
	 */
	private boolean wValid_ItemCode;
	
	//#CM696814
	/**
	 * Enabled_Item Name
	 */
	private boolean wValid_ItemName;
	
	//#CM696815
	/**
	 * Enabled_Packed Qty per Case
	 */
	private boolean wValid_EnteringQty;
	
	//#CM696816
	/**
	 * Enabled_System Stock Qty (Total Bulk Qty)
	 */
	private boolean wValid_StockQty;
	
	//#CM696817
	/**
	 * Enabled_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	private boolean wValid_ResultStockQty;
	
	//#CM696818
	/**
	 * Enabled_Inventory Check Location
	 */
	private boolean wValid_LocationNo;
	
	//#CM696819
	/**
	 * Enabled_Inventory Check Result Date
	 */
	private boolean wValid_InventoryWorkDate;
	

	//#CM696820
	// Class method --------------------------------------------------
	//#CM696821
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:12 $");
	}

	//#CM696822
	// Constructors --------------------------------------------------
	//#CM696823
	/**
	 * Initialize this class.
	 */
	public DefineInventoryDataParameter()
	{
	}
	
	//#CM696824
	// Public methods ------------------------------------------------

	//#CM696825
	/**
	 * Worker Code
	 * @return	Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM696826
	/**
	 * Password
	 * @return	Password
	 */
	public String getPassword()
	{
		return wPassword;
	}



	//#CM696827
	/**
	 * Length_Consignor Code
	 * @return	Length_Consignor Code
	 */
	public String getFigure_ConsignorCode()
	{
		return wFigure_ConsignorCode;
	}

	//#CM696828
	/**
	 * Length_Consignor Name
	 * @return	Length_Consignor Name
	 */
	public String getFigure_ConsignorName()
	{
		return wFigure_ConsignorName;
	}

	//#CM696829
	/**
	 * Length_Packed Qty per Case
	 * @return	Length_Packed Qty per Case
	 */
	public String getFigure_EnteringQty()
	{
		return wFigure_EnteringQty;
	}

	//#CM696830
	/**
	 * Length_Inventory Check Result Date
	 * @return	Length_Inventory Check Result Date
	 */
	public String getFigure_InventoryWorkDate()
	{
		return wFigure_InventoryWorkDate;
	}

	//#CM696831
	/**
	 * Length_Item Code
	 * @return	Length_Item Code
	 */
	public String getFigure_ItemCode()
	{
		return wFigure_ItemCode;
	}

	//#CM696832
	/**
	 * Length_Item Name
	 * @return	Length_Item Name
	 */
	public String getFigure_ItemName()
	{
		return wFigure_ItemName;
	}

	//#CM696833
	/**
	 * Length_Inventory Check Location
	 * @return	Length_Inventory Check Location
	 */
	public String getFigure_LocationNo()
	{
		return wFigure_LocationNo;
	}

	//#CM696834
	/**
	 * Length_Inventory CheckResult Qty (Total Bulk Qty)
	 * @return	Length_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	public String getFigure_ResultStockQty()
	{
		return wFigure_ResultStockQty;
	}

	//#CM696835
	/**
	 * Length_System Stock Qty (Total Bulk Qty)
	 * @return	Length_System Stock Qty (Total Bulk Qty)
	 */
	public String getFigure_StockQty()
	{
		return wFigure_StockQty;
	}

	//#CM696836
	/**
	 * Max Length_Consignor Code
	 * @return	Max Length_Consignor Code
	 */
	public String getMaxFigure_ConsignorCode()
	{
		return wMaxFigure_ConsignorCode;
	}

	//#CM696837
	/**
	 * Max Length_Consignor Name
	 * @return	Max Length_Consignor Name
	 */
	public String getMaxFigure_ConsignorName()
	{
		return wMaxFigure_ConsignorName;
	}

	//#CM696838
	/**
	 * Max Length_Packed Qty per Case
	 * @return	Max Length_Packed Qty per Case
	 */
	public String getMaxFigure_EnteringQty()
	{
		return wMaxFigure_EnteringQty;
	}

	//#CM696839
	/**
	 * Max Length_Inventory Check Result Date
	 * @return	Max Length_Inventory Check Result Date
	 */
	public String getMaxFigure_InventoryWorkDate()
	{
		return wMaxFigure_InventoryWorkDate;
	}

	//#CM696840
	/**
	 * Max Length_Item Code
	 * @return	Max Length_Item Code
	 */
	public String getMaxFigure_ItemCode()
	{
		return wMaxFigure_ItemCode;
	}

	//#CM696841
	/**
	 * Max Length_Item Name
	 * @return	Max Length_Item Name
	 */
	public String getMaxFigure_ItemName()
	{
		return wMaxFigure_ItemName;
	}

	//#CM696842
	/**
	 * Max Length_Inventory Check Location
	 * @return	Max Length_Inventory Check Location
	 */
	public String getMaxFigure_LocationNo()
	{
		return wMaxFigure_LocationNo;
	}

	//#CM696843
	/**
	 * Max Length_Inventory CheckResult Qty (Total Bulk Qty)
	 * @return	Max Length_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_ResultStockQty()
	{
		return wMaxFigure_ResultStockQty;
	}

	//#CM696844
	/**
	 * Max Length_System Stock Qty (Total Bulk Qty)
	 * @return	Max Length_System Stock Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_StockQty()
	{
		return wMaxFigure_StockQty;
	}

	//#CM696845
	/**
	 * Position_Consignor Code
	 * @return	Position_Consignor Code
	 */
	public String getPosition_ConsignorCode()
	{
		return wPosition_ConsignorCode;
	}

	//#CM696846
	/**
	 * Position_Consignor Name
	 * @return	Position_Consignor Name
	 */
	public String getPosition_ConsignorName()
	{
		return wPosition_ConsignorName;
	}

	//#CM696847
	/**
	 * Position_Packed Qty per Case
	 * @return	Position_Packed Qty per Case
	 */
	public String getPosition_EnteringQty()
	{
		return wPosition_EnteringQty;
	}

	//#CM696848
	/**
	 * Position_Inventory Check Result Date
	 * @return	Position_Inventory Check Result Date
	 */
	public String getPosition_InventoryWorkDate()
	{
		return wPosition_InventoryWorkDate;
	}

	//#CM696849
	/**
	 * Position_Item Code
	 * @return	Position_Item Code
	 */
	public String getPosition_ItemCode()
	{
		return wPosition_ItemCode;
	}

	//#CM696850
	/**
	 * Position_Item Name
	 * @return	Position_Item Name
	 */
	public String getPosition_ItemName()
	{
		return wPosition_ItemName;
	}

	//#CM696851
	/**
	 * Position_Inventory Check Location
	 * @return	Position_Inventory Check Location
	 */
	public String getPosition_LocationNo()
	{
		return wPosition_LocationNo;
	}

	//#CM696852
	/**
	 * Position_Inventory CheckResult Qty (Total Bulk Qty)
	 * @return	Position_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	public String getPosition_ResultStockQty()
	{
		return wPosition_ResultStockQty;
	}

	//#CM696853
	/**
	 * Position_System Stock Qty (Total Bulk Qty)
	 * @return	Position_System Stock Qty (Total Bulk Qty)
	 */
	public String getPosition_StockQty()
	{
		return wPosition_StockQty;
	}

	//#CM696854
	/**
	 * Enabled_Consignor Code
	 * @return	Enabled_Consignor Code
	 */
	public boolean getValid_ConsignorCode()
	{
		return wValid_ConsignorCode;
	}

	//#CM696855
	/**
	 * Enabled_Consignor Name
	 * @return	Enabled_Consignor Name
	 */
	public boolean getValid_ConsignorName()
	{
		return wValid_ConsignorName;
	}

	//#CM696856
	/**
	 * Enabled_Packed Qty per Case
	 * @return	Enabled_Packed Qty per Case
	 */
	public boolean getValid_EnteringQty()
	{
		return wValid_EnteringQty;
	}

	//#CM696857
	/**
	 * Enabled_Inventory Check Result Date
	 * @return	Enabled_Inventory Check Result Date
	 */
	public boolean getValid_InventoryWorkDate()
	{
		return wValid_InventoryWorkDate;
	}

	//#CM696858
	/**
	 * Enabled_Item Code
	 * @return	Enabled_Item Code
	 */
	public boolean getValid_ItemCode()
	{
		return wValid_ItemCode;
	}

	//#CM696859
	/**
	 * Enabled_Item Name
	 * @return	Enabled_Item Name
	 */
	public boolean getValid_ItemName()
	{
		return wValid_ItemName;
	}

	//#CM696860
	/**
	 * Enabled_Inventory Check Location
	 * @return	Enabled_Inventory Check Location
	 */
	public boolean getValid_LocationNo()
	{
		return wValid_LocationNo;
	}

	//#CM696861
	/**
	 * Enabled_Inventory CheckResult Qty (Total Bulk Qty)
	 * @return	Enabled_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	public boolean getValid_ResultStockQty()
	{
		return wValid_ResultStockQty;
	}

	//#CM696862
	/**
	 * Enabled_System Stock Qty (Total Bulk Qty)
	 * @return	Enabled_System Stock Qty (Total Bulk Qty)
	 */
	public boolean getValid_StockQty()
	{
		return wValid_StockQty;
	}

	//#CM696863
	/**
	 * Length_Consignor Code
	 * @param arg	Length_Consignor Code
	 */
	public void setFigure_ConsignorCode(String arg)
	{
		wFigure_ConsignorCode = arg;
	}

	//#CM696864
	/**
	 * Length_Consignor Name
	 * @param arg	Length_Consignor Name
	 */
	public void setFigure_ConsignorName(String arg)
	{
		wFigure_ConsignorName = arg;
	}

	//#CM696865
	/**
	 * Length_Packed Qty per Case
	 * @param arg	Length_Packed Qty per Case
	 */
	public void setFigure_EnteringQty(String arg)
	{
		wFigure_EnteringQty = arg;
	}

	//#CM696866
	/**
	 * Length_Inventory Check Result Date
	 * @param arg	Length_Inventory Check Result Date
	 */
	public void setFigure_InventoryWorkDate(String arg)
	{
		wFigure_InventoryWorkDate = arg;
	}

	//#CM696867
	/**
	 * Length_Item Code
	 * @param arg	Length_Item Code
	 */
	public void setFigure_ItemCode(String arg)
	{
		wFigure_ItemCode = arg;
	}

	//#CM696868
	/**
	 * Length_Item Name
	 * @param arg	Length_Item Name
	 */
	public void setFigure_ItemName(String arg)
	{
		wFigure_ItemName = arg;
	}

	//#CM696869
	/**
	 * Length_Inventory Check Location
	 * @param arg	Length_Inventory Check Location
	 */
	public void setFigure_LocationNo(String arg)
	{
		wFigure_LocationNo = arg;
	}

	//#CM696870
	/**
	 * Length_Inventory CheckResult Qty (Total Bulk Qty)
	 * @param arg	Length_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	public void setFigure_ResultStockQty(String arg)
	{
		wFigure_ResultStockQty = arg;
	}

	//#CM696871
	/**
	 * Length_System Stock Qty (Total Bulk Qty)
	 * @param arg	Length_System Stock Qty (Total Bulk Qty)
	 */
	public void setFigure_StockQty(String arg)
	{
		wFigure_StockQty = arg;
	}

	//#CM696872
	/**
	 * Max Length_Consignor Code
	 * @param arg	Max Length_Consignor Code
	 */
	public void setMaxFigure_ConsignorCode(String arg)
	{
		wMaxFigure_ConsignorCode = arg;
	}

	//#CM696873
	/**
	 * Max Length_Consignor Name
	 * @param arg	Max Length_Consignor Name
	 */
	public void setMaxFigure_ConsignorName(String arg)
	{
		wMaxFigure_ConsignorName = arg;
	}

	//#CM696874
	/**
	 * Max Length_Packed Qty per Case
	 * @param arg	Max Length_Packed Qty per Case
	 */
	public void setMaxFigure_EnteringQty(String arg)
	{
		wMaxFigure_EnteringQty = arg;
	}

	//#CM696875
	/**
	 * Max Length_Inventory Check Result Date
	 * @param arg	Max Length_Inventory Check Result Date
	 */
	public void setMaxFigure_InventoryWorkDate(String arg)
	{
		wMaxFigure_InventoryWorkDate = arg;
	}

	//#CM696876
	/**
	 * Max Length_Item Code
	 * @param arg	Max Length_Item Code
	 */
	public void setMaxFigure_ItemCode(String arg)
	{
		wMaxFigure_ItemCode = arg;
	}

	//#CM696877
	/**
	 * Max Length_Item Name
	 * @param arg	Max Length_Item Name
	 */
	public void setMaxFigure_ItemName(String arg)
	{
		wMaxFigure_ItemName = arg;
	}

	//#CM696878
	/**
	 * Max Length_Inventory Check Location
	 * @param arg	Max Length_Inventory Check Location
	 */
	public void setMaxFigure_LocationNo(String arg)
	{
		wMaxFigure_LocationNo = arg;
	}

	//#CM696879
	/**
	 * Max Length_Inventory CheckResult Qty (Total Bulk Qty)
	 * @param arg	Max Length_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	public void setMaxFigure_ResultStockQty(String arg)
	{
		wMaxFigure_ResultStockQty = arg;
	}

	//#CM696880
	/**
	 * Max Length_System Stock Qty (Total Bulk Qty)
	 * @param arg	Max Length_System Stock Qty (Total Bulk Qty)
	 */
	public void setMaxFigure_StockQty(String arg)
	{
		wMaxFigure_StockQty = arg;
	}

	//#CM696881
	/**
	 * Position_Consignor Code
	 * @param arg	Position_Consignor Code
	 */
	public void setPosition_ConsignorCode(String arg)
	{
		wPosition_ConsignorCode = arg;
	}

	//#CM696882
	/**
	 * Position_Consignor Name
	 * @param arg	Position_Consignor Name
	 */
	public void setPosition_ConsignorName(String arg)
	{
		wPosition_ConsignorName = arg;
	}

	//#CM696883
	/**
	 * Position_Packed Qty per Case
	 * @param arg	Position_Packed Qty per Case
	 */
	public void setPosition_EnteringQty(String arg)
	{
		wPosition_EnteringQty = arg;
	}

	//#CM696884
	/**
	 * Position_Inventory Check Result Date
	 * @param arg	Position_Inventory Check Result Date
	 */
	public void setPosition_InventoryWorkDate(String arg)
	{
		wPosition_InventoryWorkDate = arg;
	}

	//#CM696885
	/**
	 * Position_Item Code
	 * @param arg	Position_Item Code
	 */
	public void setPosition_ItemCode(String arg)
	{
		wPosition_ItemCode = arg;
	}

	//#CM696886
	/**
	 * Position_Item Name
	 * @param arg	Position_Item Name
	 */
	public void setPosition_ItemName(String arg)
	{
		wPosition_ItemName = arg;
	}

	//#CM696887
	/**
	 * Position_Inventory Check Location
	 * @param arg	Position_Inventory Check Location
	 */
	public void setPosition_LocationNo(String arg)
	{
		wPosition_LocationNo = arg;
	}

	//#CM696888
	/**
	 * Position_Inventory CheckResult Qty (Total Bulk Qty)
	 * @param arg	Position_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	public void setPosition_ResultStockQty(String arg)
	{
		wPosition_ResultStockQty = arg;
	}

	//#CM696889
	/**
	 * Position_System Stock Qty (Total Bulk Qty)
	 * @param arg	Position_System Stock Qty (Total Bulk Qty)
	 */
	public void setPosition_StockQty(String arg)
	{
		wPosition_StockQty = arg;
	}

	//#CM696890
	/**
	 * Enabled_Consignor Code
	 * @param arg	Enabled_Consignor Code
	 */
	public void setValid_ConsignorCode(boolean arg)
	{
		wValid_ConsignorCode = arg;
	}

	//#CM696891
	/**
	 * Enabled_Consignor Name
	 * @param arg	Enabled_Consignor Name
	 */
	public void setValid_ConsignorName(boolean arg)
	{
		wValid_ConsignorName = arg;
	}

	//#CM696892
	/**
	 * Enabled_Packed Qty per Case
	 * @param arg	Enabled_Packed Qty per Case
	 */
	public void setValid_EnteringQty(boolean arg)
	{
		wValid_EnteringQty = arg;
	}

	//#CM696893
	/**
	 * Enabled_Inventory Check Result Date
	 * @param arg	Enabled_Inventory Check Result Date
	 */
	public void setValid_InventoryWorkDate(boolean arg)
	{
		wValid_InventoryWorkDate = arg;
	}

	//#CM696894
	/**
	 * Enabled_Item Code
	 * @param arg	Enabled_Item Code
	 */
	public void setValid_ItemCode(boolean arg)
	{
		wValid_ItemCode = arg;
	}

	//#CM696895
	/**
	 * Enabled_Item Name
	 * @param arg	Enabled_Item Name
	 */
	public void setValid_ItemName(boolean arg)
	{
		wValid_ItemName = arg;
	}

	//#CM696896
	/**
	 * Enabled_Inventory Check Location
	 * @param arg	Enabled_Inventory Check Location
	 */
	public void setValid_LocationNo(boolean arg)
	{
		wValid_LocationNo = arg;
	}

	//#CM696897
	/**
	 * Enabled_Inventory CheckResult Qty (Total Bulk Qty)
	 * @param arg	Enabled_Inventory CheckResult Qty (Total Bulk Qty)
	 */
	public void setValid_ResultStockQty(boolean arg)
	{
		wValid_ResultStockQty = arg;
	}

	//#CM696898
	/**
	 * Enabled_System Stock Qty (Total Bulk Qty)
	 * @param arg	Enabled_System Stock Qty (Total Bulk Qty)
	 */
	public void setValid_StockQty(boolean arg)
	{
		wValid_StockQty = arg;
	}

}
//#CM696899
//end of class
