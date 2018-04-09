//#CM697765
//$Id: DefineSortingDataParameter.java,v 1.2 2006/11/13 06:03:09 suresh Exp $

//#CM697766
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import jp.co.daifuku.wms.base.common.Parameter;
 
//#CM697767
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * Use the <CODE>DefineSortingDataParameter</CODE> class to pass a parameter between the screen and the schedule for setting field items to be loaded or reported for the Sorting data.<BR>
 * Allow this class to maintain the field items to be used in each screen for system package. Use a field item depending on the screen.<BR>
 * <BR>
 * <DIR>
 * Allow the <CODE>DefineSortingDataParameter</CODE> class to maintain the following field items.<BR>
 * <BR>
 * [Textbox or Label] <BR>
 * <DIR>
 *     Worker Code <BR>
 *     Password <BR>
 * <BR>
 *     Length_Planned Sorting Date <BR>
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
 *     Length_Sorting Qty (Total Bulk Qty) <BR> 
 *     Length_Piece Sorting Place <BR>
 *     Length_Case Sorting Place <BR>
 *     Length_Cross/DC division <BR>
 *     Length_Supplier Code <BR>
 *     Length_Supplier Name <BR>
 *     Length_Receiving Ticket No. <BR>
 *     Length_Receiving Ticket Line No. <BR>
 *     Length_Result Piece Qty <BR>
 *     Length_Result Case Qty <BR>
 *     Length_Sorting Result Date <BR> 
 *     Length_Result division <BR>
 * <BR>
 *     Max Length_Planned Sorting Date <BR>
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
 *     Max Length_Sorting Qty (Total Bulk Qty) <BR> 
 *     Max Length_Piece Sorting Place <BR>
 *     Max Length_Case Sorting Place <BR>
 *     Max Length_Cross/DC division <BR>
 *     Max Length_Supplier Code <BR>
 *     Max Length_Supplier Name <BR>
 *     Max Length_Receiving Ticket No. <BR>
 *     Max Length_Receiving Ticket Line No. <BR>
 *     Max Length_Result Piece Qty <BR>
 *     Max Length_Result Case Qty <BR>
 *     Max Length_Sorting Result Date <BR> 
 *     Max Length_Result division <BR>
 * <BR>
 *     Position_Planned Sorting Date <BR>
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
 *     Position_Sorting Qty (Total Bulk Qty) <BR> 
 *     Position_Piece Sorting Place <BR>
 *     Position_Case Sorting Place <BR>
 *     Position_Cross/DC division <BR>
 *     Position_Supplier Code <BR>
 *     Position_Supplier Name <BR>
 *     Position_Receiving Ticket No. <BR>
 *     Position_Receiving Ticket Line No. <BR>
 *     Position_Result Piece Qty <BR>
 *     Position_Result Case Qty <BR>
 *     Position_Sorting Result Date <BR> 
 *     Position_Result division <BR>
 * </DIR>
 * [Checkbox] <BR>
 * <DIR>
 *     Enabled_Planned Sorting Date <BR>
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
 *     Enabled_Sorting Qty (Total Bulk Qty) <BR> 
 *     Enabled_Piece Sorting Place <BR>
 *     Enabled_Case Sorting Place <BR>
 *     Enabled_Cross/DC division <BR>
 *     Enabled_Supplier Code <BR>
 *     Enabled_Supplier Name <BR>
 *     Enabled_Receiving Ticket No. <BR>
 *     Enabled_Receiving Ticket Line No. <BR>
 *     Enabled_Result Piece Qty <BR>
 *     Enabled_Result Case Qty <BR>
 *     Enabled_Sorting Result Date <BR> 
 *     Enabled_Result division <BR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/05</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:09 $
 * @author  $Author: suresh $
 */
public class DefineSortingDataParameter extends Parameter
{
	//#CM697768
	// Class variables -----------------------------------------------
	//#CM697769
	/**
	 * Worker Code
	 */
	private String wWorkerCode;
	
	//#CM697770
	/**
	 * Password
	 */
	private String wPassword;
	
	//#CM697771
	/**
	 * Length_Planned Sorting Date
	 */
	private String wFigure_SortingPlanDate;
	
	//#CM697772
	/**
	 * Length_Consignor Code
	 */
	private String wFigure_ConsignorCode;
	
	//#CM697773
	/**
	 * Length_Consignor Name
	 */
	private String wFigure_ConsignorName;
	
	//#CM697774
	/**
	 * Length_Customer Code
	 */
	private String wFigure_CustomerCode;
	
	//#CM697775
	/**
	 * Length_Customer Name
	 */
	private String wFigure_CustomerName;
	
	//#CM697776
	/**
	 * Length_Shipping Ticket No
	 */
	private String wFigure_ShippingTicketNo;
	
	//#CM697777
	/**
	 * Length_Shipping ticket line No.
	 */
	private String wFigure_ShippingLineNo;
	
	//#CM697778
	/**
	 * Length_Item Code
	 */
	private String wFigure_ItemCode;
	
	//#CM697779
	/**
	 * Length_Bundle ITF
	 */
	private String wFigure_BundleItf;
	
	//#CM697780
	/**
	 * Length_Case ITF
	 */
	private String wFigure_Itf;
	
	//#CM697781
	/**
	 * Length_Packed qty per bundle
	 */
	private String wFigure_BundleEnteringQty;
	
	//#CM697782
	/**
	 * Length_Packed Qty per Case
	 */
	private String wFigure_EnteringQty;
	
	//#CM697783
	/**
	 * Length_Item Name
	 */
	private String wFigure_ItemName;
	
	//#CM697784
	/**
	 * Length_Sorting Qty (Total Bulk Qty)
	 */
	private String wFigure_SortingPlanQty;

	//#CM697785
	/**
	 * Length_Piece Sorting Place
	 */
	private String wFigure_PieceSortingLocation;
	
	//#CM697786
	/**
	 * Length_Case Sorting Place
	 */
	private String wFigure_CaseSortingLocation;
	
	//#CM697787
	/**
	 * Length_Cross/DC division
	 */
	private String wFigure_TcDcFlag;

	//#CM697788
	/**
	 * Length_Supplier Code
	 */
	private String wFigure_SupplierCode;
	
	//#CM697789
	/**
	 * Length_Supplier Name
	 */
	private String wFigure_SupplierName;
	
	//#CM697790
	/**
	 * Length_Receiving Ticket No.
	 */
	private String wFigure_InstockTicketNo;
	
	//#CM697791
	/**
	 * Length_Receiving Ticket Line No.
	 */
	private String wFigure_InstockLineNo;
	
	//#CM697792
	/**
	 * Length_Result Piece Qty
	 */
	private String wFigure_PieceResultQty;
	
	//#CM697793
	/**
	 * Length_Result Case Qty
	 */
	private String wFigure_CaseResultQty;
	
	//#CM697794
	/**
	 * Length_Sorting Result Date
	 */
	private String wFigure_SortingWorkDate;
	
	//#CM697795
	/**
	 * Length_Result division
	 */
	private String wFigure_ResultFlag;
	
	//#CM697796
	/**
	 * Max Length_Planned Sorting Date
	 */
	private String wMaxFigure_SortingPlanDate;
	
	//#CM697797
	/**
	 * Max Length_Consignor Code
	 */
	private String wMaxFigure_ConsignorCode;
	
	//#CM697798
	/**
	 * Max Length_Consignor Name
	 */
	private String wMaxFigure_ConsignorName;
	
	//#CM697799
	/**
	 * Max Length_Customer Code
	 */
	private String wMaxFigure_CustomerCode;
	
	//#CM697800
	/**
	 * Max Length_Customer Name
	 */
	private String wMaxFigure_CustomerName;
	
	//#CM697801
	/**
	 * Max Length_Shipping Ticket No
	 */
	private String wMaxFigure_ShippingTicketNo;
	
	//#CM697802
	/**
	 * Max Length_Shipping ticket line No.
	 */
	private String wMaxFigure_ShippingLineNo;
	
	//#CM697803
	/**
	 * Max Length_Item Code
	 */
	private String wMaxFigure_ItemCode;
	
	//#CM697804
	/**
	 * Max Length_Bundle ITF
	 */
	private String wMaxFigure_BundleItf;
	
	//#CM697805
	/**
	 * Max Length_Case ITF
	 */
	private String wMaxFigure_Itf;
	
	//#CM697806
	/**
	 * Max Length_Packed qty per bundle
	 */
	private String wMaxFigure_BundleEnteringQty;
	
	//#CM697807
	/**
	 * Max Length_Packed Qty per Case
	 */
	private String wMaxFigure_EnteringQty;
	
	//#CM697808
	/**
	 * Max Length_Item Name
	 */
	private String wMaxFigure_ItemName;
	
	//#CM697809
	/**
	 * Max Length_Sorting Qty (Total Bulk Qty)
	 */
	private String wMaxFigure_SortingPlanQty;

	//#CM697810
	/**
	 * Max Length_Piece Sorting Place
	 */
	private String wMaxFigure_PieceSortingLocation;
	
	//#CM697811
	/**
	 * Max Length_Case Sorting Place
	 */
	private String wMaxFigure_CaseSortingLocation;
	
	//#CM697812
	/**
	 * Max Length_Cross/DC division
	 */
	private String wMaxFigure_TcDcFlag;

	//#CM697813
	/**
	 * Max Length_Supplier Code
	 */
	private String wMaxFigure_SupplierCode;
	
	//#CM697814
	/**
	 * Max Length_Supplier Name
	 */
	private String wMaxFigure_SupplierName;
	
	//#CM697815
	/**
	 * Max Length_Receiving Ticket No.
	 */
	private String wMaxFigure_InstockTicketNo;
	
	//#CM697816
	/**
	 * Max Length_Receiving Ticket Line No.
	 */
	private String wMaxFigure_InstockLineNo;
	
	//#CM697817
	/**
	 * Max Length_Result Piece Qty
	 */
	private String wMaxFigure_PieceResultQty;
	
	//#CM697818
	/**
	 * Max Length_Result Case Qty
	 */
	private String wMaxFigure_CaseResultQty;
	
	//#CM697819
	/**
	 * Max Length_Sorting Result Date
	 */
	private String wMaxFigure_SortingWorkDate;
	
	//#CM697820
	/**
	 * Max Length_Result division
	 */
	private String wMaxFigure_ResultFlag;
	
	//#CM697821
	/**
	 * Position_Planned Sorting Date
	 */
	private String wPosition_SortingPlanDate;
	
	//#CM697822
	/**
	 * Position_Consignor Code
	 */
	private String wPosition_ConsignorCode;
	
	//#CM697823
	/**
	 * Position_Consignor Name
	 */
	private String wPosition_ConsignorName;
	
	//#CM697824
	/**
	 * Position_Customer Code
	 */
	private String wPosition_CustomerCode;
	
	//#CM697825
	/**
	 * Position_Customer Name
	 */
	private String wPosition_CustomerName;
	
	//#CM697826
	/**
	 * Position_Shipping Ticket No
	 */
	private String wPosition_ShippingTicketNo;
	
	//#CM697827
	/**
	 * Position_Shipping ticket line No.
	 */
	private String wPosition_ShippingLineNo;
	
	//#CM697828
	/**
	 * Position_Item Code
	 */
	private String wPosition_ItemCode;
	
	//#CM697829
	/**
	 * Position_Bundle ITF
	 */
	private String wPosition_BundleItf;
	
	//#CM697830
	/**
	 * Position_Case ITF
	 */
	private String wPosition_Itf;
	
	//#CM697831
	/**
	 * Position_Packed qty per bundle
	 */
	private String wPosition_BundleEnteringQty;
	
	//#CM697832
	/**
	 * Position_Packed Qty per Case
	 */
	private String wPosition_EnteringQty;
	
	//#CM697833
	/**
	 * Position_Item Name
	 */
	private String wPosition_ItemName;
	
	//#CM697834
	/**
	 * Position_Sorting Qty (Total Bulk Qty)
	 */
	private String wPosition_SortingPlanQty;

	//#CM697835
	/**
	 * Position_Piece Sorting Place
	 */
	private String wPosition_PieceSortingLocation;
	
	//#CM697836
	/**
	 * Position_Case Sorting Place
	 */
	private String wPosition_CaseSortingLocation;
	
	//#CM697837
	/**
	 * Position_Cross/DC division
	 */
	private String wPosition_TcDcFlag;

	//#CM697838
	/**
	 * Position_Supplier Code
	 */
	private String wPosition_SupplierCode;
	
	//#CM697839
	/**
	 * Position_Supplier Name
	 */
	private String wPosition_SupplierName;
	
	//#CM697840
	/**
	 * Position_Receiving Ticket No.
	 */
	private String wPosition_InstockTicketNo;
	
	//#CM697841
	/**
	 * Position_Receiving Ticket Line No.
	 */
	private String wPosition_InstockLineNo;
	
	//#CM697842
	/**
	 * Position_Result Piece Qty
	 */
	private String wPosition_PieceResultQty;
	
	//#CM697843
	/**
	 * Position_Result Case Qty
	 */
	private String wPosition_CaseResultQty;
	
	//#CM697844
	/**
	 * Position_Sorting Result Date
	 */
	private String wPosition_SortingWorkDate;
	
	//#CM697845
	/**
	 * Position_Result division
	 */
	private String wPosition_ResultFlag;
	
	//#CM697846
	/**
	 * Enabled_Planned Sorting Date
	 */
	private boolean wValid_SortingPlanDate;
	
	//#CM697847
	/**
	 * Enabled_Consignor Code
	 */
	private boolean wValid_ConsignorCode;
	
	//#CM697848
	/**
	 * Enabled_Consignor Name
	 */
	private boolean wValid_ConsignorName;
	
	//#CM697849
	/**
	 * Enabled_Customer Code
	 */
	private boolean wValid_CustomerCode;
	
	//#CM697850
	/**
	 * Enabled_Customer Name
	 */
	private boolean wValid_CustomerName;
	
	//#CM697851
	/**
	 * Enabled_Shipping Ticket No
	 */
	private boolean wValid_ShippingTicketNo;
	
	//#CM697852
	/**
	 * Enabled_Shipping ticket line No.
	 */
	private boolean wValid_ShippingLineNo;
	
	//#CM697853
	/**
	 * Enabled_Item Code
	 */
	private boolean wValid_ItemCode;
	
	//#CM697854
	/**
	 * Enabled_Bundle ITF
	 */
	private boolean wValid_BundleItf;
	
	//#CM697855
	/**
	 * Enabled_Case ITF
	 */
	private boolean wValid_Itf;
	
	//#CM697856
	/**
	 * Enabled_Packed qty per bundle
	 */
	private boolean wValid_BundleEnteringQty;
	
	//#CM697857
	/**
	 * Enabled_Packed Qty per Case
	 */
	private boolean wValid_EnteringQty;
	
	//#CM697858
	/**
	 * Enabled_Item Name
	 */
	private boolean wValid_ItemName;
	
	//#CM697859
	/**
	 * Enabled_Sorting Qty (Total Bulk Qty)
	 */
	private boolean wValid_SortingPlanQty;

	//#CM697860
	/**
	 * Enabled_Piece Sorting Place
	 */
	private boolean wValid_PieceSortingLocation;
	
	//#CM697861
	/**
	 * Enabled_Case Sorting Place
	 */
	private boolean wValid_CaseSortingLocation;
	
	//#CM697862
	/**
	 * Enabled_Cross/DC division
	 */
	private boolean wValid_TcDcFlag;

	//#CM697863
	/**
	 * Enabled_Supplier Code
	 */
	private boolean wValid_SupplierCode;
	
	//#CM697864
	/**
	 * Enabled_Supplier Name
	 */
	private boolean wValid_SupplierName;
	
	//#CM697865
	/**
	 * Enabled_Receiving Ticket No.
	 */
	private boolean wValid_InstockTicketNo;
	
	//#CM697866
	/**
	 * Enabled_Receiving Ticket Line No.
	 */
	private boolean wValid_InstockLineNo;
	
	//#CM697867
	/**
	 * Enabled_Result Piece Qty
	 */
	private boolean wValid_PieceResultQty;
	
	//#CM697868
	/**
	 * Enabled_Result Case Qty
	 */
	private boolean wValid_CaseResultQty;
	
	//#CM697869
	/**
	 * Enabled_Sorting Result Date
	 */
	private boolean wValid_SortingWorkDate;
	
	//#CM697870
	/**
	 * Enabled_Result division
	 */
	private boolean wValid_ResultFlag;
	

	//#CM697871
	// Class method --------------------------------------------------
	//#CM697872
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:09 $");
	}

	//#CM697873
	// Constructors --------------------------------------------------
	//#CM697874
	/**
	 * Initialize this class.
	 */
	public DefineSortingDataParameter()
	{
	}
	
	//#CM697875
	// Public methods ------------------------------------------------

	//#CM697876
	/**
	 * Worker Code
	 * @return	Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM697877
	/**
	 * Password
	 * @return	Password
	 */
	public String getPassword()
	{
		return wPassword;
	}


	//#CM697878
	/**
	 * Length_Packed qty per bundle
	 * @return	Length_Packed qty per bundle
	 */
	public String getFigure_BundleEnteringQty()
	{
		return wFigure_BundleEnteringQty;
	}

	//#CM697879
	/**
	 * Length_Bundle ITF
	 * @return	Length_Bundle ITF
	 */
	public String getFigure_BundleItf()
	{
		return wFigure_BundleItf;
	}

	//#CM697880
	/**
	 *  Length_Case Sorting Place
	 * @return	 Length_Case Sorting Place
	 */
	public String getFigure_CaseSortingLocation()
	{
		return wFigure_CaseSortingLocation;
	}

	//#CM697881
	/**
	 * Length_Result Case Qty
	 * @return	Length_Result Case Qty
	 */
	public String getFigure_CaseResultQty()
	{
		return wFigure_CaseResultQty;
	}

	//#CM697882
	/**
	 * Length_Consignor Code
	 * @return	Length_Consignor Code
	 */
	public String getFigure_ConsignorCode()
	{
		return wFigure_ConsignorCode;
	}

	//#CM697883
	/**
	 * Length_Consignor Name
	 * @return	Length_Consignor Name
	 */
	public String getFigure_ConsignorName()
	{
		return wFigure_ConsignorName;
	}

	//#CM697884
	/**
	 * Length_Customer Code
	 * @return	Length_Customer Code
	 */
	public String getFigure_CustomerCode()
	{
		return wFigure_CustomerCode;
	}

	//#CM697885
	/**
	 * Length_Customer Name
	 * @return	Length_Customer Name
	 */
	public String getFigure_CustomerName()
	{
		return wFigure_CustomerName;
	}

	//#CM697886
	/**
	 * Length_Packed Qty per Case
	 * @return	Length_Packed Qty per Case
	 */
	public String getFigure_EnteringQty()
	{
		return wFigure_EnteringQty;
	}

	//#CM697887
	/**
	 * Length_Receiving Ticket Line No.
	 * @return	Length_Receiving Ticket Line No.
	 */
	public String getFigure_InstockLineNo()
	{
		return wFigure_InstockLineNo;
	}

	//#CM697888
	/**
	 * Length_Receiving Ticket No.
	 * @return	Length_Receiving Ticket No.
	 */
	public String getFigure_InstockTicketNo()
	{
		return wFigure_InstockTicketNo;
	}

	//#CM697889
	/**
	 * Length_Item Code
	 * @return	Length_Item Code
	 */
	public String getFigure_ItemCode()
	{
		return wFigure_ItemCode;
	}

	//#CM697890
	/**
	 * Length_Item Name
	 * @return	Length_Item Name
	 */
	public String getFigure_ItemName()
	{
		return wFigure_ItemName;
	}

	//#CM697891
	/**
	 * Length_Case ITF
	 * @return	Length_Case ITF
	 */
	public String getFigure_Itf()
	{
		return wFigure_Itf;
	}

	//#CM697892
	/**
	 * Length_Planned Sorting Date
	 * @return	Length_Planned Sorting Date
	 */
	public String getFigure_SortingPlanDate()
	{
		return wFigure_SortingPlanDate;
	}

	//#CM697893
	/**
	 * Length_Sorting Qty (Total Bulk Qty)
	 * @return	Length_Sorting Qty (Total Bulk Qty)
	 */
	public String getFigure_SortingPlanQty()
	{
		return wFigure_SortingPlanQty;
	}

	//#CM697894
	/**
	 * Length_Sorting Result Date
	 * @return	Length_Sorting Result Date
	 */
	public String getFigure_SortingWorkDate()
	{
		return wFigure_SortingWorkDate;
	}

	//#CM697895
	/**
	 * Length_Piece Sorting Place
	 * @return	Length_Piece Sorting Place
	 */
	public String getFigure_PieceSortingLocation()
	{
		return wFigure_PieceSortingLocation;
	}

	//#CM697896
	/**
	 * Length_Result Piece Qty
	 * @return	Length_Result Piece Qty
	 */
	public String getFigure_PieceResultQty()
	{
		return wFigure_PieceResultQty;
	}

	//#CM697897
	/**
	 * Length_Result division
	 * @return	Length_Result division
	 */
	public String getFigure_ResultFlag()
	{
		return wFigure_ResultFlag;
	}

	//#CM697898
	/**
	 * Length_Shipping ticket line No.
	 * @return	Length_Shipping ticket line No.
	 */
	public String getFigure_ShippingLineNo()
	{
		return wFigure_ShippingLineNo;
	}

	//#CM697899
	/**
	 * Length_Shipping Ticket No
	 * @return	Length_Shipping Ticket No
	 */
	public String getFigure_ShippingTicketNo()
	{
		return wFigure_ShippingTicketNo;
	}

	//#CM697900
	/**
	 * Length_Supplier Code
	 * @return	Length_Supplier Code
	 */
	public String getFigure_SupplierCode()
	{
		return wFigure_SupplierCode;
	}

	//#CM697901
	/**
	 * Length_Supplier Name
	 * @return	Length_Supplier Name
	 */
	public String getFigure_SupplierName()
	{
		return wFigure_SupplierName;
	}

	//#CM697902
	/**
	 * Length_Cross/DC division
	 * @return	Length_Cross/DC division
	 */
	public String getFigure_TcDcFlag()
	{
		return wFigure_TcDcFlag;
	}

	//#CM697903
	/**
	 * Max Length_Packed qty per bundle
	 * @return	Max Length_Packed qty per bundle
	 */
	public String getMaxFigure_BundleEnteringQty()
	{
		return wMaxFigure_BundleEnteringQty;
	}

	//#CM697904
	/**
	 * Max Length_Bundle ITF
	 * @return	Max Length_Bundle ITF
	 */
	public String getMaxFigure_BundleItf()
	{
		return wMaxFigure_BundleItf;
	}

	//#CM697905
	/**
	 * Max Length_Case Sorting Place
	 * @return	Max Length_Case Sorting Place
	 */
	public String getMaxFigure_CaseSortingLocation()
	{
		return wMaxFigure_CaseSortingLocation;
	}

	//#CM697906
	/**
	 * Max Length_Result Case Qty
	 * @return	Max Length_Result Case Qty
	 */
	public String getMaxFigure_CaseResultQty()
	{
		return wMaxFigure_CaseResultQty;
	}

	//#CM697907
	/**
	 * Max Length_Consignor Code
	 * @return	Max Length_Consignor Code
	 */
	public String getMaxFigure_ConsignorCode()
	{
		return wMaxFigure_ConsignorCode;
	}

	//#CM697908
	/**
	 * Max Length_Consignor Name
	 * @return	Max Length_Consignor Name
	 */
	public String getMaxFigure_ConsignorName()
	{
		return wMaxFigure_ConsignorName;
	}

	//#CM697909
	/**
	 * Max Length_Customer Code
	 * @return	Max Length_Customer Code
	 */
	public String getMaxFigure_CustomerCode()
	{
		return wMaxFigure_CustomerCode;
	}

	//#CM697910
	/**
	 * Max Length_Customer Name
	 * @return	 Max Length_Customer Name
	 */
	public String getMaxFigure_CustomerName()
	{
		return wMaxFigure_CustomerName;
	}

	//#CM697911
	/**
	 * Max Length_Packed Qty per Case
	 * @return	Max Length_Packed Qty per Case
	 */
	public String getMaxFigure_EnteringQty()
	{
		return wMaxFigure_EnteringQty;
	}

	//#CM697912
	/**
	 * Max Length_Receiving Ticket Line No.
	 * @return	Max Length_Receiving Ticket Line No.
	 */
	public String getMaxFigure_InstockLineNo()
	{
		return wMaxFigure_InstockLineNo;
	}

	//#CM697913
	/**
	 * Max Length_Receiving Ticket No.
	 * @return	Max Length_Receiving Ticket No.
	 */
	public String getMaxFigure_InstockTicketNo()
	{
		return wMaxFigure_InstockTicketNo;
	}

	//#CM697914
	/**
	 * Max Length_Item Code
	 * @return	Max Length_Item Code
	 */
	public String getMaxFigure_ItemCode()
	{
		return wMaxFigure_ItemCode;
	}

	//#CM697915
	/**
	 * Max Length_Item Name
	 * @return	Max Length_Item Name
	 */
	public String getMaxFigure_ItemName()
	{
		return wMaxFigure_ItemName;
	}

	//#CM697916
	/**
	 * Max Length_Case ITF
	 * @return	Max Length_Case ITF
	 */
	public String getMaxFigure_Itf()
	{
		return wMaxFigure_Itf;
	}

	//#CM697917
	/**
	 * Max Length_Planned Sorting Date
	 * @return	Max Length_Planned Sorting Date
	 */
	public String getMaxFigure_SortingPlanDate()
	{
		return wMaxFigure_SortingPlanDate;
	}

	//#CM697918
	/**
	 * Max Length_Sorting Qty (Total Bulk Qty)
	 * @return	Max Length_Sorting Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_SortingPlanQty()
	{
		return wMaxFigure_SortingPlanQty;
	}

	//#CM697919
	/**
	 * Max Length_Sorting Result Date
	 * @return	Max Length_Sorting Result Date
	 */
	public String getMaxFigure_SortingWorkDate()
	{
		return wMaxFigure_SortingWorkDate;
	}

	//#CM697920
	/**
	 * Max Length_Piece Sorting Place
	 * @return	Max Length_Piece Sorting Place
	 */
	public String getMaxFigure_PieceSortingLocation()
	{
		return wMaxFigure_PieceSortingLocation;
	}

	//#CM697921
	/**
	 * Max Length_Result Piece Qty
	 * @return	Max Length_Result Piece Qty
	 */
	public String getMaxFigure_PieceResultQty()
	{
		return wMaxFigure_PieceResultQty;
	}

	//#CM697922
	/**
	 * Max Length_Result division
	 * @return	Max Length_Result division
	 */
	public String getMaxFigure_ResultFlag()
	{
		return wMaxFigure_ResultFlag;
	}

	//#CM697923
	/**
	 * Max Length_Shipping ticket line No.
	 * @return	Max Length_Shipping ticket line No.
	 */
	public String getMaxFigure_ShippingLineNo()
	{
		return wMaxFigure_ShippingLineNo;
	}

	//#CM697924
	/**
	 * Max Length_Shipping Ticket No
	 * @return	Max Length_Shipping Ticket No
	 */
	public String getMaxFigure_ShippingTicketNo()
	{
		return wMaxFigure_ShippingTicketNo;
	}

	//#CM697925
	/**
	 * Max Length_Supplier Code
	 * @return	Max Length_Supplier Code
	 */
	public String getMaxFigure_SupplierCode()
	{
		return wMaxFigure_SupplierCode;
	}

	//#CM697926
	/**
	 * Max Length_Supplier Name
	 * @return	Max Length_Supplier Name
	 */
	public String getMaxFigure_SupplierName()
	{
		return wMaxFigure_SupplierName;
	}

	//#CM697927
	/**
	 * Max Length_Cross/DC division
	 * @return	Max Length_Cross/DC division
	 */
	public String getMaxFigure_TcDcFlag()
	{
		return wMaxFigure_TcDcFlag;
	}

	//#CM697928
	/**
	 * Position_Packed qty per bundle
	 * @return	Position_Packed qty per bundle
	 */
	public String getPosition_BundleEnteringQty()
	{
		return wPosition_BundleEnteringQty;
	}

	//#CM697929
	/**
	 * Position_Bundle ITF
	 * @return	Position_Bundle ITF
	 */
	public String getPosition_BundleItf()
	{
		return wPosition_BundleItf;
	}

	//#CM697930
	/**
	 * Position_Case Sorting Place
	 * @return	Position_Case Sorting Place
	 */
	public String getPosition_CaseSortingLocation()
	{
		return wPosition_CaseSortingLocation;
	}

	//#CM697931
	/**
	 * Position_Result Case Qty
	 * @return	Position_Result Case Qty
	 */
	public String getPosition_CaseResultQty()
	{
		return wPosition_CaseResultQty;
	}

	//#CM697932
	/**
	 * Position_Consignor Code
	 * @return	Position_Consignor Code
	 */
	public String getPosition_ConsignorCode()
	{
		return wPosition_ConsignorCode;
	}

	//#CM697933
	/**
	 * Position_Consignor Name
	 * @return	Position_Consignor Name
	 */
	public String getPosition_ConsignorName()
	{
		return wPosition_ConsignorName;
	}

	//#CM697934
	/**
	 * Position_Customer Code
	 * @return	Position_Customer Code
	 */
	public String getPosition_CustomerCode()
	{
		return wPosition_CustomerCode;
	}

	//#CM697935
	/**
	 * Position_Customer Name
	 * @return	Position_Customer Name
	 */
	public String getPosition_CustomerName()
	{
		return wPosition_CustomerName;
	}

	//#CM697936
	/**
	 * Position_Packed Qty per Case
	 * @return	Position_Packed Qty per Case
	 */
	public String getPosition_EnteringQty()
	{
		return wPosition_EnteringQty;
	}

	//#CM697937
	/**
	 * Position_Receiving Ticket Line No.
	 * @return	Position_Receiving Ticket Line No.
	 */
	public String getPosition_InstockLineNo()
	{
		return wPosition_InstockLineNo;
	}

	//#CM697938
	/**
	 * Position_Receiving Ticket No.
	 * @return	Position_Receiving Ticket No.
	 */
	public String getPosition_InstockTicketNo()
	{
		return wPosition_InstockTicketNo;
	}

	//#CM697939
	/**
	 * Position_Item Code
	 * @return	Position_Item Code
	 */
	public String getPosition_ItemCode()
	{
		return wPosition_ItemCode;
	}

	//#CM697940
	/**
	 * Position_Item Name
	 * @return	Position_Item Name
	 */
	public String getPosition_ItemName()
	{
		return wPosition_ItemName;
	}

	//#CM697941
	/**
	 * Position_Case ITF
	 * @return	Position_Case ITF
	 */
	public String getPosition_Itf()
	{
		return wPosition_Itf;
	}

	//#CM697942
	/**
	 * Position_Planned Sorting Date
	 * @return	Position_Planned Sorting Date
	 */
	public String getPosition_SortingPlanDate()
	{
		return wPosition_SortingPlanDate;
	}

	//#CM697943
	/**
	 * Position_Sorting Qty (Total Bulk Qty)
	 * @return	Position_Sorting Qty (Total Bulk Qty)
	 */
	public String getPosition_SortingPlanQty()
	{
		return wPosition_SortingPlanQty;
	}

	//#CM697944
	/**
	 * Position_Sorting Result Date
	 * @return	Position_Sorting Result Date
	 */
	public String getPosition_SortingWorkDate()
	{
		return wPosition_SortingWorkDate;
	}

	//#CM697945
	/**
	 * Position_Piece Sorting Place
	 * @return	Position_Piece Sorting Place
	 */
	public String getPosition_PieceSortingLocation()
	{
		return wPosition_PieceSortingLocation;
	}

	//#CM697946
	/**
	 * Position_Result Piece Qty
	 * @return	Position_Result Piece Qty
	 */
	public String getPosition_PieceResultQty()
	{
		return wPosition_PieceResultQty;
	}

	//#CM697947
	/**
	 * Position_Result division
	 * @return	Position_Result division
	 */
	public String getPosition_ResultFlag()
	{
		return wPosition_ResultFlag;
	}

	//#CM697948
	/**
	 * Position_Shipping ticket line No.
	 * @return	Position_Shipping ticket line No.
	 */
	public String getPosition_ShippingLineNo()
	{
		return wPosition_ShippingLineNo;
	}

	//#CM697949
	/**
	 * Position_Shipping Ticket No
	 * @return	Position_Shipping Ticket No
	 */
	public String getPosition_ShippingTicketNo()
	{
		return wPosition_ShippingTicketNo;
	}

	//#CM697950
	/**
	 * Position_Supplier Code
	 * @return	Position_Supplier Code
	 */
	public String getPosition_SupplierCode()
	{
		return wPosition_SupplierCode;
	}

	//#CM697951
	/**
	 * Position_Supplier Name
	 * @return	Position_Supplier Name
	 */
	public String getPosition_SupplierName()
	{
		return wPosition_SupplierName;
	}

	//#CM697952
	/**
	 * Position_Cross/DC division
	 * @return	Position_Cross/DC division
	 */
	public String getPosition_TcDcFlag()
	{
		return wPosition_TcDcFlag;
	}

	//#CM697953
	/**
	 * Enabled_Packed qty per bundle
	 * @return	Enabled_Packed qty per bundle
	 */
	public boolean getValid_BundleEnteringQty()
	{
		return wValid_BundleEnteringQty;
	}

	//#CM697954
	/**
	 * Enabled_Bundle ITF
	 * @return	Enabled_Bundle ITF
	 */
	public boolean getValid_BundleItf()
	{
		return wValid_BundleItf;
	}

	//#CM697955
	/**
	 * Enabled_Case Sorting Place
	 * @return	Enabled_Case Sorting Place
	 */
	public boolean getValid_CaseSortingLocation()
	{
		return wValid_CaseSortingLocation;
	}

	//#CM697956
	/**
	 * Enabled_Result Case Qty
	 * @return	Enabled_Result Case Qty
	 */
	public boolean getValid_CaseResultQty()
	{
		return wValid_CaseResultQty;
	}

	//#CM697957
	/**
	 * Enabled_Consignor Code
	 * @return	Enabled_Consignor Code
	 */
	public boolean getValid_ConsignorCode()
	{
		return wValid_ConsignorCode;
	}

	//#CM697958
	/**
	 * Enabled_Consignor Name
	 * @return	Enabled_Consignor Name
	 */
	public boolean getValid_ConsignorName()
	{
		return wValid_ConsignorName;
	}

	//#CM697959
	/**
	 * Enabled_Customer Code
	 * @return	Enabled_Customer Code
	 */
	public boolean getValid_CustomerCode()
	{
		return wValid_CustomerCode;
	}

	//#CM697960
	/**
	 * Enabled_Customer Name
	 * @return	Enabled_Customer Name
	 */
	public boolean getValid_CustomerName()
	{
		return wValid_CustomerName;
	}

	//#CM697961
	/**
	 * Enabled_Packed Qty per Case
	 * @return	Enabled_Packed Qty per Case
	 */
	public boolean getValid_EnteringQty()
	{
		return wValid_EnteringQty;
	}

	//#CM697962
	/**
	 * Enabled_Receiving Ticket Line No.
	 * @return	Enabled_Receiving Ticket Line No.
	 */
	public boolean getValid_InstockLineNo()
	{
		return wValid_InstockLineNo;
	}

	//#CM697963
	/**
	 * Enabled_Receiving Ticket No.
	 * @return	Enabled_Receiving Ticket No.
	 */
	public boolean getValid_InstockTicketNo()
	{
		return wValid_InstockTicketNo;
	}

	//#CM697964
	/**
	 * Enabled_Item Code
	 * @return	Enabled_Item Code
	 */
	public boolean getValid_ItemCode()
	{
		return wValid_ItemCode;
	}

	//#CM697965
	/**
	 * Enabled_Item Name
	 * @return	Enabled_Item Name
	 */
	public boolean getValid_ItemName()
	{
		return wValid_ItemName;
	}

	//#CM697966
	/**
	 * Enabled_Case ITF
	 * @return	Enabled_Case ITF
	 */
	public boolean getValid_Itf()
	{
		return wValid_Itf;
	}

	//#CM697967
	/**
	 * Enabled_Planned Sorting Date
	 * @return	Enabled_Planned Sorting Date
	 */
	public boolean getValid_SortingPlanDate()
	{
		return wValid_SortingPlanDate;
	}

	//#CM697968
	/**
	 * Enabled_Sorting Qty (Total Bulk Qty)
	 * @return	Enabled_Sorting Qty (Total Bulk Qty)
	 */
	public boolean getValid_SortingPlanQty()
	{
		return wValid_SortingPlanQty;
	}

	//#CM697969
	/**
	 * Enabled_Sorting Result Date
	 * @return	Enabled_Sorting Result Date
	 */
	public boolean getValid_SortingWorkDate()
	{
		return wValid_SortingWorkDate;
	}

	//#CM697970
	/**
	 * Enabled_Piece Sorting Place
	 * @return	Enabled_Piece Sorting Place
	 */
	public boolean getValid_PieceSortingLocation()
	{
		return wValid_PieceSortingLocation;
	}

	//#CM697971
	/**
	 * Enabled_Result Piece Qty
	 * @return	Enabled_Result Piece Qty
	 */
	public boolean getValid_PieceResultQty()
	{
		return wValid_PieceResultQty;
	}

	//#CM697972
	/**
	 * Enabled_Result division
	 * @return	Enabled_Result division
	 */
	public boolean getValid_ResultFlag()
	{
		return wValid_ResultFlag;
	}

	//#CM697973
	/**
	 * Enabled_Shipping ticket line No.
	 * @return	Enabled_Shipping ticket line No.
	 */
	public boolean getValid_ShippingLineNo()
	{
		return wValid_ShippingLineNo;
	}

	//#CM697974
	/**
	 * Enabled_Shipping Ticket No
	 * @return	Enabled_Shipping Ticket No
	 */
	public boolean getValid_ShippingTicketNo()
	{
		return wValid_ShippingTicketNo;
	}

	//#CM697975
	/**
	 * Enabled_Supplier Code
	 * @return	Enabled_Supplier Code
	 */
	public boolean getValid_SupplierCode()
	{
		return wValid_SupplierCode;
	}

	//#CM697976
	/**
	 * Enabled_Supplier Name
	 * @return	Enabled_Supplier Name
	 */
	public boolean getValid_SupplierName()
	{
		return wValid_SupplierName;
	}

	//#CM697977
	/**
	 * Enabled_Cross/DC division
	 * @return	Enabled_Cross/DC division
	 */
	public boolean getValid_TcDcFlag()
	{
		return wValid_TcDcFlag;
	}

	//#CM697978
	/**
	 * Length_Packed qty per bundle
	 * @param arg	Length_Packed qty per bundle to be set
	 */
	public void setFigure_BundleEnteringQty(String arg)
	{
		wFigure_BundleEnteringQty = arg;
	}

	//#CM697979
	/**
	 * Length_Bundle ITF
	 * @param arg	Length_Bundle ITF to be set
	 */
	public void setFigure_BundleItf(String arg)
	{
		wFigure_BundleItf = arg;
	}

	//#CM697980
	/**
	 * Length_Case Sorting Place
	 * @param arg	Length_Case Sorting Place to be set
	 */
	public void setFigure_CaseSortingLocation(String arg)
	{
		wFigure_CaseSortingLocation = arg;
	}

	//#CM697981
	/**
	 * Length_Result Case Qty
	 * @param arg	Length_Result Case Qty to be set
	 */
	public void setFigure_CaseResultQty(String arg)
	{
		wFigure_CaseResultQty = arg;
	}

	//#CM697982
	/**
	 * Length_Consignor Code
	 * @param arg	Length_Consignor Code to be set
	 */
	public void setFigure_ConsignorCode(String arg)
	{
		wFigure_ConsignorCode = arg;
	}

	//#CM697983
	/**
	 * Length_Consignor Name
	 * @param arg	Length_Consignor Name to be set
	 */
	public void setFigure_ConsignorName(String arg)
	{
		wFigure_ConsignorName = arg;
	}

	//#CM697984
	/**
	 * Length_Customer Code
	 * @param arg	Length_Customer Code to be set
	 */
	public void setFigure_CustomerCode(String arg)
	{
		wFigure_CustomerCode = arg;
	}

	//#CM697985
	/**
	 * Length_Customer Name
	 * @param arg	Length_Customer Name to be set
	 */
	public void setFigure_CustomerName(String arg)
	{
		wFigure_CustomerName = arg;
	}

	//#CM697986
	/**
	 * Length_Packed Qty per Case
	 * @param arg	Length_Packed Qty per Case to be set
	 */
	public void setFigure_EnteringQty(String arg)
	{
		wFigure_EnteringQty = arg;
	}

	//#CM697987
	/**
	 * Length_Receiving Ticket Line No.
	 * @param arg	Length_Receiving Ticket Line No. to be set
	 */
	public void setFigure_InstockLineNo(String arg)
	{
		wFigure_InstockLineNo = arg;
	}

	//#CM697988
	/**
	 * Length_Receiving Ticket No.
	 * @param arg	Length_Receiving Ticket No. to be set
	 */
	public void setFigure_InstockTicketNo(String arg)
	{
		wFigure_InstockTicketNo = arg;
	}

	//#CM697989
	/**
	 * Length_Item Code
	 * @param arg	Length_Item Code to be set
	 */
	public void setFigure_ItemCode(String arg)
	{
		wFigure_ItemCode = arg;
	}

	//#CM697990
	/**
	 * Length_Item Name
	 * @param arg	Length_Item Name to be set
	 */
	public void setFigure_ItemName(String arg)
	{
		wFigure_ItemName = arg;
	}

	//#CM697991
	/**
	 * Length_Case ITF
	 * @param arg	Length_Case ITF to be set
	 */
	public void setFigure_Itf(String arg)
	{
		wFigure_Itf = arg;
	}

	//#CM697992
	/**
	 * Length_Planned Sorting Date
	 * @param arg	Length_Planned Sorting Date to be set
	 */
	public void setFigure_SortingPlanDate(String arg)
	{
		wFigure_SortingPlanDate = arg;
	}

	//#CM697993
	/**
	 * Length_Sorting Qty (Total Bulk Qty)
	 * @param arg	Length_Sorting Qty (Total Bulk Qty) to be set
	 */
	public void setFigure_SortingPlanQty(String arg)
	{
		wFigure_SortingPlanQty = arg;
	}

	//#CM697994
	/**
	 * Length_Sorting Result Date
	 * @param arg	Length_Sorting Result Date to be set
	 */
	public void setFigure_SortingWorkDate(String arg)
	{
		wFigure_SortingWorkDate = arg;
	}

	//#CM697995
	/**
	 * Length_Piece Sorting Place
	 * @param arg	Length_Piece Sorting Place to be set
	 */
	public void setFigure_PieceSortingLocation(String arg)
	{
		wFigure_PieceSortingLocation = arg;
	}

	//#CM697996
	/**
	 * Length_Result Piece Qty
	 * @param arg	Length_Result Piece Qty to be set
	 */
	public void setFigure_PieceResultQty(String arg)
	{
		wFigure_PieceResultQty = arg;
	}

	//#CM697997
	/**
	 * Length_Result division
	 * @param arg	Length_Result division to be set
	 */
	public void setFigure_ResultFlag(String arg)
	{
		wFigure_ResultFlag = arg;
	}

	//#CM697998
	/**
	 * Length_Shipping ticket line No.
	 * @param arg	Length_Shipping ticket line No. to be set
	 */
	public void setFigure_ShippingLineNo(String arg)
	{
		wFigure_ShippingLineNo = arg;
	}

	//#CM697999
	/**
	 * Length_Shipping Ticket No
	 * @param arg	Length_Shipping Ticket No to be set
	 */
	public void setFigure_ShippingTicketNo(String arg)
	{
		wFigure_ShippingTicketNo = arg;
	}

	//#CM698000
	/**
	 * Length_Supplier Code
	 * @param arg	Length_Supplier Code to be set
	 */
	public void setFigure_SupplierCode(String arg)
	{
		wFigure_SupplierCode = arg;
	}

	//#CM698001
	/**
	 * Length_Supplier Name
	 * @param arg	Length_Supplier Name to be set
	 */
	public void setFigure_SupplierName(String arg)
	{
		wFigure_SupplierName = arg;
	}

	//#CM698002
	/**
	 * Length_Cross/DC division
	 * @param arg	Length_Cross/DC division to be set
	 */
	public void setFigure_TcDcFlag(String arg)
	{
		wFigure_TcDcFlag = arg;
	}

	//#CM698003
	/**
	 * Max Length_Packed qty per bundle
	 * @param arg	Max Length_Packed qty per bundle to be set
	 */
	public void setMaxFigure_BundleEnteringQty(String arg)
	{
		wMaxFigure_BundleEnteringQty = arg;
	}

	//#CM698004
	/**
	 * Max Length_Bundle ITF
	 * @param arg	Max Length_Bundle ITF to be set
	 */
	public void setMaxFigure_BundleItf(String arg)
	{
		wMaxFigure_BundleItf = arg;
	}

	//#CM698005
	/**
	 * Max Length_Case Sorting Place
	 * @param arg	Max Length_Case Sorting Place to be set
	 */
	public void setMaxFigure_CaseSortingLocation(String arg)
	{
		wMaxFigure_CaseSortingLocation = arg;
	}

	//#CM698006
	/**
	 * Max Length_Result Case Qty
	 * @param arg	Max Length_Result Case Qty to be set
	 */
	public void setMaxFigure_CaseResultQty(String arg)
	{
		wMaxFigure_CaseResultQty = arg;
	}

	//#CM698007
	/**
	 * Max Length_Consignor Code
	 * @param arg	Max Length_Consignor Code to be set
	 */
	public void setMaxFigure_ConsignorCode(String arg)
	{
		wMaxFigure_ConsignorCode = arg;
	}

	//#CM698008
	/**
	 * Max Length_Consignor Name
	 * @param arg	Max Length_Consignor Name to be set
	 */
	public void setMaxFigure_ConsignorName(String arg)
	{
		wMaxFigure_ConsignorName = arg;
	}

	//#CM698009
	/**
	 * Max Length_Customer Code
	 * @param arg	Max Length_Customer Code to be set
	 */
	public void setMaxFigure_CustomerCode(String arg)
	{
		wMaxFigure_CustomerCode = arg;
	}

	//#CM698010
	/**
	 * Max Length_Customer Name
	 * @param arg	Max Length_Customer Name to be set
	 */
	public void setMaxFigure_CustomerName(String arg)
	{
		wMaxFigure_CustomerName = arg;
	}

	//#CM698011
	/**
	 * Max Length_Packed Qty per Case
	 * @param arg	Max Length_Packed Qty per Case to be set
	 */
	public void setMaxFigure_EnteringQty(String arg)
	{
		wMaxFigure_EnteringQty = arg;
	}

	//#CM698012
	/**
	 * Max Length_Receiving Ticket Line No.
	 * @param arg	Max Length_Receiving Ticket Line No. to be set
	 */
	public void setMaxFigure_InstockLineNo(String arg)
	{
		wMaxFigure_InstockLineNo = arg;
	}

	//#CM698013
	/**
	 * Max Length_Receiving Ticket No.
	 * @param arg	Max Length_Receiving Ticket No. to be set
	 */
	public void setMaxFigure_InstockTicketNo(String arg)
	{
		wMaxFigure_InstockTicketNo = arg;
	}

	//#CM698014
	/**
	 * Max Length_Item Code
	 * @param arg	Max Length_Item Code to be set
	 */
	public void setMaxFigure_ItemCode(String arg)
	{
		wMaxFigure_ItemCode = arg;
	}

	//#CM698015
	/**
	 * Max Length_Item Name
	 * @param arg	Max Length_Item Name to be set
	 */
	public void setMaxFigure_ItemName(String arg)
	{
		wMaxFigure_ItemName = arg;
	}

	//#CM698016
	/**
	 * Max Length_Case ITF
	 * @param arg	Max Length_Case ITF to be set
	 */
	public void setMaxFigure_Itf(String arg)
	{
		wMaxFigure_Itf = arg;
	}

	//#CM698017
	/**
	 * Max Length_Planned Sorting Date
	 * @param arg	Max Length_Planned Sorting Date to be set
	 */
	public void setMaxFigure_SortingPlanDate(String arg)
	{
		wMaxFigure_SortingPlanDate = arg;
	}

	//#CM698018
	/**
	 * Max Length_Sorting Qty (Total Bulk Qty)
	 * @param arg	Max Length_Sorting Qty (Total Bulk Qty) to be set
	 */
	public void setMaxFigure_SortingPlanQty(String arg)
	{
		wMaxFigure_SortingPlanQty = arg;
	}

	//#CM698019
	/**
	 * Max Length_Sorting Result Date
	 * @param arg	Max Length_Sorting Result Date to be set
	 */
	public void setMaxFigure_SortingWorkDate(String arg)
	{
		wMaxFigure_SortingWorkDate = arg;
	}

	//#CM698020
	/**
	 * Max Length_Piece Sorting Place
	 * @param arg	Max Length_Piece Sorting Place to be set
	 */
	public void setMaxFigure_PieceSortingLocation(String arg)
	{
		wMaxFigure_PieceSortingLocation = arg;
	}

	//#CM698021
	/**
	 * Max Length_Result Piece Qty
	 * @param arg	Max Length_Result Piece Qty to be set
	 */
	public void setMaxFigure_PieceResultQty(String arg)
	{
		wMaxFigure_PieceResultQty = arg;
	}

	//#CM698022
	/**
	 * Max Length_Result division
	 * @param arg	Max Length_Result division to be set
	 */
	public void setMaxFigure_ResultFlag(String arg)
	{
		wMaxFigure_ResultFlag = arg;
	}

	//#CM698023
	/**
	 * Max Length_Shipping ticket line No.
	 * @param arg	Max Length_Shipping ticket line No. to be set
	 */
	public void setMaxFigure_ShippingLineNo(String arg)
	{
		wMaxFigure_ShippingLineNo = arg;
	}

	//#CM698024
	/**
	 * Max Length_Shipping Ticket No
	 * @param arg	Max Length_Shipping Ticket No to be set
	 */
	public void setMaxFigure_ShippingTicketNo(String arg)
	{
		wMaxFigure_ShippingTicketNo = arg;
	}

	//#CM698025
	/**
	 * Max Length_Supplier Code
	 * @param arg	Max Length_Supplier Code to be set
	 */
	public void setMaxFigure_SupplierCode(String arg)
	{
		wMaxFigure_SupplierCode = arg;
	}

	//#CM698026
	/**
	 * Max Length_Supplier Name
	 * @param arg	Max Length_Supplier Name to be set
	 */
	public void setMaxFigure_SupplierName(String arg)
	{
		wMaxFigure_SupplierName = arg;
	}

	//#CM698027
	/**
	 * Max Length_Cross/DC division
	 * @param arg	Max Length_Cross/DC division to be set
	 */
	public void setMaxFigure_TcDcFlag(String arg)
	{
		wMaxFigure_TcDcFlag = arg;
	}

	//#CM698028
	/**
	 * Position_Packed qty per bundle
	 * @param arg	Position_Packed qty per bundle to be set
	 */
	public void setPosition_BundleEnteringQty(String arg)
	{
		wPosition_BundleEnteringQty = arg;
	}

	//#CM698029
	/**
	 * Position_Bundle ITF
	 * @param arg	Position_Bundle ITF to be set
	 */
	public void setPosition_BundleItf(String arg)
	{
		wPosition_BundleItf = arg;
	}

	//#CM698030
	/**
	 * Position_Case Sorting Place
	 * @param arg	Position_Case Sorting Place to be set
	 */
	public void setPosition_CaseSortingLocation(String arg)
	{
		wPosition_CaseSortingLocation = arg;
	}

	//#CM698031
	/**
	 * Position_Result Case Qty
	 * @param arg	Position_Result Case Qty to be set
	 */
	public void setPosition_CaseResultQty(String arg)
	{
		wPosition_CaseResultQty = arg;
	}

	//#CM698032
	/**
	 * Position_Consignor Code
	 * @param arg	Position_Consignor Code to be set
	 */
	public void setPosition_ConsignorCode(String arg)
	{
		wPosition_ConsignorCode = arg;
	}

	//#CM698033
	/**
	 * Position_Consignor Name
	 * @param arg	Position_Consignor Name to be set
	 */
	public void setPosition_ConsignorName(String arg)
	{
		wPosition_ConsignorName = arg;
	}

	//#CM698034
	/**
	 * Position_Customer Code
	 * @param arg	Position_Customer Code to be set
	 */
	public void setPosition_CustomerCode(String arg)
	{
		wPosition_CustomerCode = arg;
	}

	//#CM698035
	/**
	 * Position_Customer Name
	 * @param arg	Position_Customer Name to be set
	 */
	public void setPosition_CustomerName(String arg)
	{
		wPosition_CustomerName = arg;
	}

	//#CM698036
	/**
	 * Position_Packed Qty per Case
	 * @param arg	Position_Packed Qty per Case to be set
	 */
	public void setPosition_EnteringQty(String arg)
	{
		wPosition_EnteringQty = arg;
	}

	//#CM698037
	/**
	 * Position_Receiving Ticket Line No.
	 * @param arg	Position_Receiving Ticket Line No. to be set
	 */
	public void setPosition_InstockLineNo(String arg)
	{
		wPosition_InstockLineNo = arg;
	}

	//#CM698038
	/**
	 * Position_Receiving Ticket No.
	 * @param arg	Position_Receiving Ticket No. to be set
	 */
	public void setPosition_InstockTicketNo(String arg)
	{
		wPosition_InstockTicketNo = arg;
	}

	//#CM698039
	/**
	 * Position_Item Code
	 * @param arg	Position_Item Code to be set
	 */
	public void setPosition_ItemCode(String arg)
	{
		wPosition_ItemCode = arg;
	}

	//#CM698040
	/**
	 * Position_Item Name
	 * @param arg	Position_Item Name to be set
	 */
	public void setPosition_ItemName(String arg)
	{
		wPosition_ItemName = arg;
	}

	//#CM698041
	/**
	 * Position_Case ITF
	 * @param arg	Position_Case ITF to be set
	 */
	public void setPosition_Itf(String arg)
	{
		wPosition_Itf = arg;
	}

	//#CM698042
	/**
	 * Position_Planned Sorting Date
	 * @param arg	Position_Planned Sorting Date to be set
	 */
	public void setPosition_SortingPlanDate(String arg)
	{
		wPosition_SortingPlanDate = arg;
	}

	//#CM698043
	/**
	 * Position_Sorting Qty (Total Bulk Qty)
	 * @param arg	Position_Sorting Qty (Total Bulk Qty) to be set
	 */
	public void setPosition_SortingPlanQty(String arg)
	{
		wPosition_SortingPlanQty = arg;
	}

	//#CM698044
	/**
	 * Position_Sorting Result Date
	 * @param arg	Position_Sorting Result Date to be set
	 */
	public void setPosition_SortingWorkDate(String arg)
	{
		wPosition_SortingWorkDate = arg;
	}

	//#CM698045
	/**
	 * Position_Piece Sorting Place
	 * @param arg	Position_Piece Sorting Place to be set
	 */
	public void setPosition_PieceSortingLocation(String arg)
	{
		wPosition_PieceSortingLocation = arg;
	}

	//#CM698046
	/**
	 * Position_Result Piece Qty
	 * @param arg	Position_Result Piece Qty to be set
	 */
	public void setPosition_PieceResultQty(String arg)
	{
		wPosition_PieceResultQty = arg;
	}

	//#CM698047
	/**
	 * Position_Result division
	 * @param arg	Position_Result division to be set
	 */
	public void setPosition_ResultFlag(String arg)
	{
		wPosition_ResultFlag = arg;
	}

	//#CM698048
	/**
	 * Position_Shipping ticket line No.
	 * @param arg	Position_Shipping ticket line No. to be set
	 */
	public void setPosition_ShippingLineNo(String arg)
	{
		wPosition_ShippingLineNo = arg;
	}

	//#CM698049
	/**
	 * Position_Shipping Ticket No
	 * @param arg	Position_Shipping Ticket No to be set
	 */
	public void setPosition_ShippingTicketNo(String arg)
	{
		wPosition_ShippingTicketNo = arg;
	}

	//#CM698050
	/**
	 * Position_Supplier Code
	 * @param arg	Position_Supplier Code to be set
	 */
	public void setPosition_SupplierCode(String arg)
	{
		wPosition_SupplierCode = arg;
	}

	//#CM698051
	/**
	 * Position_Supplier Name
	 * @param arg	Position_Supplier Name to be set
	 */
	public void setPosition_SupplierName(String arg)
	{
		wPosition_SupplierName = arg;
	}

	//#CM698052
	/**
	 * Position_Cross/DC division
	 * @param arg	Position_Cross/DC division to be set
	 */
	public void setPosition_TcDcFlag(String arg)
	{
		wPosition_TcDcFlag = arg;
	}

	//#CM698053
	/**
	 * Enabled_Packed qty per bundle
	 * @param arg	Enabled_Packed qty per bundle to be set
	 */
	public void setValid_BundleEnteringQty(boolean arg)
	{
		wValid_BundleEnteringQty = arg;
	}

	//#CM698054
	/**
	 * Enabled_Bundle ITF
	 * @param arg	Enabled_Bundle ITF to be set
	 */
	public void setValid_BundleItf(boolean arg)
	{
		wValid_BundleItf = arg;
	}

	//#CM698055
	/**
	 * Enabled_Case Sorting Place
	 * @param arg	Enabled_Case Sorting Place to be set
	 */
	public void setValid_CaseSortingLocation(boolean arg)
	{
		wValid_CaseSortingLocation = arg;
	}

	//#CM698056
	/**
	 * Enabled_Result Case Qty
	 * @param arg	Enabled_Result Case Qty to be set
	 */
	public void setValid_CaseResultQty(boolean arg)
	{
		wValid_CaseResultQty = arg;
	}

	//#CM698057
	/**
	 * Enabled_Consignor Code
	 * @param arg	Enabled_Consignor Code to be set
	 */
	public void setValid_ConsignorCode(boolean arg)
	{
		wValid_ConsignorCode = arg;
	}

	//#CM698058
	/**
	 * Enabled_Consignor Name
	 * @param arg	Enabled_Consignor Name to be set
	 */
	public void setValid_ConsignorName(boolean arg)
	{
		wValid_ConsignorName = arg;
	}

	//#CM698059
	/**
	 * Enabled_Customer Code
	 * @param arg	Enabled_Customer Code to be set
	 */
	public void setValid_CustomerCode(boolean arg)
	{
		wValid_CustomerCode = arg;
	}

	//#CM698060
	/**
	 * Enabled_Customer Name
	 * @param arg	Enabled_Customer Name to be set
	 */
	public void setValid_CustomerName(boolean arg)
	{
		wValid_CustomerName = arg;
	}

	//#CM698061
	/**
	 * Enabled_Packed Qty per Case
	 * @param arg	Enabled_Packed Qty per Case to be set
	 */
	public void setValid_EnteringQty(boolean arg)
	{
		wValid_EnteringQty = arg;
	}

	//#CM698062
	/**
	 * Enabled_Receiving Ticket Line No.
	 * @param arg	Enabled_Receiving Ticket Line No. to be set
	 */
	public void setValid_InstockLineNo(boolean arg)
	{
		wValid_InstockLineNo = arg;
	}

	//#CM698063
	/**
	 * Enabled_Receiving Ticket No.
	 * @param arg	Enabled_Receiving Ticket No. to be set
	 */
	public void setValid_InstockTicketNo(boolean arg)
	{
		wValid_InstockTicketNo = arg;
	}

	//#CM698064
	/**
	 * Enabled_Item Code
	 * @param arg	Enabled_Item Code to be set
	 */
	public void setValid_ItemCode(boolean arg)
	{
		wValid_ItemCode = arg;
	}

	//#CM698065
	/**
	 * Enabled_Item Name
	 * @param arg	Enabled_Item Name to be set
	 */
	public void setValid_ItemName(boolean arg)
	{
		wValid_ItemName = arg;
	}

	//#CM698066
	/**
	 * Enabled_Case ITF
	 * @param arg	Enabled_Case ITF to be set
	 */
	public void setValid_Itf(boolean arg)
	{
		wValid_Itf = arg;
	}

	//#CM698067
	/**
	 * Enabled_Planned Sorting Date
	 * @param arg	Enabled_Planned Sorting Date to be set
	 */
	public void setValid_SortingPlanDate(boolean arg)
	{
		wValid_SortingPlanDate = arg;
	}

	//#CM698068
	/**
	 * Enabled_Sorting Qty (Total Bulk Qty)
	 * @param arg	Enabled_Sorting Qty (Total Bulk Qty) to be set
	 */
	public void setValid_SortingPlanQty(boolean arg)
	{
		wValid_SortingPlanQty = arg;
	}

	//#CM698069
	/**
	 * Enabled_Sorting Result Date
	 * @param arg	Enabled_Sorting Result Date to be set
	 */
	public void setValid_SortingWorkDate(boolean arg)
	{
		wValid_SortingWorkDate = arg;
	}

	//#CM698070
	/**
	 * Enabled_Piece Sorting Place
	 * @param arg	Enabled_Piece Sorting Place to be set
	 */
	public void setValid_PieceSortingLocation(boolean arg)
	{
		wValid_PieceSortingLocation = arg;
	}

	//#CM698071
	/**
	 * Enabled_Result Piece Qty
	 * @param arg	Enabled_Result Piece Qty to be set
	 */
	public void setValid_PieceResultQty(boolean arg)
	{
		wValid_PieceResultQty = arg;
	}

	//#CM698072
	/**
	 * Enabled_Result division
	 * @param arg	Enabled_Result division to be set
	 */
	public void setValid_ResultFlag(boolean arg)
	{
		wValid_ResultFlag = arg;
	}

	//#CM698073
	/**
	 * Enabled_Shipping ticket line No.
	 * @param arg	Enabled_Shipping ticket line No. to be set
	 */
	public void setValid_ShippingLineNo(boolean arg)
	{
		wValid_ShippingLineNo = arg;
	}

	//#CM698074
	/**
	 * Enabled_Shipping Ticket No
	 * @param arg	Enabled_Shipping Ticket No to be set
	 */
	public void setValid_ShippingTicketNo(boolean arg)
	{
		wValid_ShippingTicketNo = arg;
	}

	//#CM698075
	/**
	 * Enabled_Supplier Code
	 * @param arg	Enabled_Supplier Code to be set
	 */
	public void setValid_SupplierCode(boolean arg)
	{
		wValid_SupplierCode = arg;
	}

	//#CM698076
	/**
	 * Enabled_Supplier Name
	 * @param arg	Enabled_Supplier Name to be set
	 */
	public void setValid_SupplierName(boolean arg)
	{
		wValid_SupplierName = arg;
	}

	//#CM698077
	/**
	 * Enabled_Cross/DC division
	 * @param arg	Enabled_Cross/DC division to be set
	 */
	public void setValid_TcDcFlag(boolean arg)
	{
		wValid_TcDcFlag = arg;
	}

}
//#CM698078
//end of class
