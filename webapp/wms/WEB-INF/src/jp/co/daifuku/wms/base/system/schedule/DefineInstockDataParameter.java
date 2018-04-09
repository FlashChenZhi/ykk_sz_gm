//#CM696498
//$Id: DefineInstockDataParameter.java,v 1.2 2006/11/13 06:03:12 suresh Exp $

//#CM696499
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import jp.co.daifuku.wms.base.common.Parameter;
 
//#CM696500
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * Use the <CODE>DefineInstockDataParameter</CODE> class to pass a parameter between the screen and the schedule for setting field items to be loaded or reported for the Receiving data.<BR>
 * Allow this class to maintain the field items to be used in each screen for system package. Use a field item depending on the screen.<BR>
 * <BR>
 * <DIR>
 * Allow the <CODE>DefineInstockDataParameter</CODE> class to maintain the following field items.<BR>
 * <BR>
 * [Textbox or Label] <BR>
 * <DIR>
 *     Worker Code <BR>
 *     Password <BR>
 * <BR>
 *     Length_Planned Receiving Date <BR>
 *     Length_Order Date <BR>
 *     Length_Consignor Code <BR>
 *     Length_Consignor Name <BR>
 *     Length_Supplier Code <BR>
 *     Length_Supplier Name <BR>
 *     Length_Ticket No. <BR>
 *     Length_Ticket Line No. <BR>
 *     Length_Item Code <BR>
 *     Length_Bundle ITF <BR>
 *     Length_Case ITF <BR>
 *     Length_Packed qty per bundle <BR>
 *     Length_Packed Qty per Case <BR>
 *     Length_Item Name <BR>
 *     Length_Receiving Plan Qty (Total Bulk Qty) <BR>
 *     Length_TC/DC division <BR> 
 *     Length_Customer Code <BR>
 *     Length_Customer Name <BR>
 *     Length_Receiving Qty (Total Bulk Qty) <BR>
 *     Length_Receiving Result Date <BR> 
 *     Length_Result division <BR>
 *     Length_Expiry Date <BR>
 * <BR>
 *     Max Length_Planned Receiving Date <BR>
 *     Max Length_Order Date <BR>
 *     Max Length_Consignor Code <BR>
 *     Max Length_Consignor Name <BR>
 *     Max Length_Supplier Code <BR>
 *     Max Length_Supplier Name <BR>
 *     Max Length_Ticket No. <BR>
 *     Max Length_Ticket Line No. <BR>
 *     Max Length_Item Code <BR>
 *     Max Length_Bundle ITF <BR>
 *     Max Length_Case ITF <BR>
 *     Max Length_Packed qty per bundle <BR>
 *     Max Length_Packed Qty per Case <BR>
 *     Max Length_Item Name <BR>
 *     Max Length_Receiving Plan Qty (Total Bulk Qty) <BR>
 *     Max Length_TC/DC division <BR>
 *     Max Length_Customer Code <BR>
 *     Max Length_Customer Name <BR>
 *     Max Length_Receiving Qty (Total Bulk Qty) <BR>
 *     Max Length_Receiving Result Date <BR> 
 *     Max Length_Result division <BR>
 *     Max Length_Expiry Date <BR>
 * <BR>
 *     Position_Planned Receiving Date <BR>
 *     Position_Order Date <BR>
 *     Position_Consignor Code <BR>
 *     Position_Consignor Name <BR>
 *     Position_Supplier Code <BR>
 *     Position_Supplier Name <BR>
 *     Position_Ticket No. <BR>
 *     Position_Ticket Line No. <BR>
 *     Position_Item Code <BR>
 *     Position_Bundle ITF <BR>
 *     Position_Case ITF <BR>
 *     Position_Packed qty per bundle <BR>
 *     Position_Packed Qty per Case <BR>
 *     Position_Item Name <BR>
 *     Position_Receiving Plan Qty (Total Bulk Qty) <BR>
 *     Position_TC/DC division <BR>
 *     Position_Customer Code <BR>
 *     Position_Customer Name <BR>
 *     Position_Receiving Qty (Total Bulk Qty) <BR>
 *     Position_Receiving Result Date <BR> 
 *     Position_Result division <BR>
 *     Position_Expiry Date <BR>
 * </DIR>
 * [Checkbox] <BR>
 * <DIR>
 *     Enabled_Planned Receiving Date <BR>
 *     Enabled_Order Date <BR>
 *     Enabled_Consignor Code <BR>
 *     Enabled_Consignor Name <BR>
 *     Enabled_Supplier Code <BR>
 *     Enabled_Supplier Name <BR>
 *     Enabled_Ticket No. <BR>
 *     Enabled_Ticket Line No. <BR>
 *     Enabled_Item Code <BR>
 *     Enabled_Bundle ITF <BR>
 *     Enabled_Case ITF <BR>
 *     Enabled_Packed qty per bundle <BR>
 *     Enabled_Packed Qty per Case <BR>
 *     Enabled_Item Name <BR>
 *     Enabled_Receiving Plan Qty (Total Bulk Qty) <BR>
 *     Enabled_TC/DC division <BR>
 *     Enabled_Customer Code <BR>
 *     Enabled_Customer Name <BR>
 *     Enabled_Receiving Qty (Total Bulk Qty) <BR>
 *     Enabled_Receiving Result Date <BR> 
 *     Enabled_Result division <BR>
 *     Enabled_Expiry Date <BR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/04</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:12 $
 * @author  $Author: suresh $
 */
public class DefineInstockDataParameter extends Parameter
{
	//#CM696501
	// Class variables -----------------------------------------------
	//#CM696502
	/**
	 * Worker Code
	 */
	private String wWorkerCode;
	
	//#CM696503
	/**
	 * Password
	 */
	private String wPassword;
	
	//#CM696504
	/**
	 * Length_Planned Receiving Date
	 */
	private String wFigure_InstockPlanDate;
	
	//#CM696505
	/**
	 * Length_Order Date
	 */
	private String wFigure_InstockOrderingDate;
	
	//#CM696506
	/**
	 * Length_Consignor Code
	 */
	private String wFigure_ConsignorCode;
	
	//#CM696507
	/**
	 * Length_Consignor Name
	 */
	private String wFigure_ConsignorName;
	
	//#CM696508
	/**
	 * Length_Supplier Code
	 */
	private String wFigure_SupplierCode;
	
	//#CM696509
	/**
	 * Length_Supplier Name
	 */
	private String wFigure_SupplierName;
	
	//#CM696510
	/**
	 * Length_Ticket No.
	 */
	private String wFigure_TicketNo;
	
	//#CM696511
	/**
	 * Length_Ticket Line No.
	 */
	private String wFigure_LineNo;
	
	//#CM696512
	/**
	 * Length_Item Code
	 */
	private String wFigure_ItemCode;
	
	//#CM696513
	/**
	 * Length_Bundle ITF
	 */
	private String wFigure_BundleItf;
	
	//#CM696514
	/**
	 * Length_Case ITF
	 */
	private String wFigure_Itf;
	
	//#CM696515
	/**
	 * Length_Packed qty per bundle
	 */
	private String wFigure_BundleEnteringQty;
	
	//#CM696516
	/**
	 * Length_Packed Qty per Case
	 */
	private String wFigure_EnteringQty;
	
	//#CM696517
	/**
	 * Length_Item Name
	 */
	private String wFigure_ItemName;
	
	//#CM696518
	/**
	 * Length_Receiving Plan Qty (Total Bulk Qty)<BR>
	 * <BR>
	 * <BR>
	 * Use Receiving Qty (Total Bulk Qty) as a field item name for loading data. Use Planned Receiving Qty (Total Bulk Qty) as a field item name for reporting data.
	 */
	private String wFigure_InstockPlanQty;
	
	//#CM696519
	/**
	 * Length_TC/DC division
	 */
	private String wFigure_TcDcFlag;
	
	//#CM696520
	/**
	 * Length_Customer Code
	 */
	private String wFigure_CustomerCode;
	
	//#CM696521
	/**
	 * Length_Customer Name
	 */
	private String wFigure_CustomerName;
	
	//#CM696522
	/**
	 * Length_Receiving Qty (Total Bulk Qty)
	 */
	private String wFigure_InstockResultQty;
	
	//#CM696523
	/**
	 * Length_Receiving Result Date
	 */
	private String wFigure_InstockWorkDate;
	
	//#CM696524
	/**
	 * Length_Result division
	 */
	private String wFigure_ResultFlag;
	
	//#CM696525
	/**
	 * Length_Expiry Date
	 */
	private String wFigure_UseByDate;
	
	//#CM696526
	/**
	 * Max Length_Planned Receiving Date
	 */
	private String wMaxFigure_InstockPlanDate;
	
	//#CM696527
	/**
	 * Max Length_Order Date
	 */
	private String wMaxFigure_InstockOrderingDate;
	
	//#CM696528
	/**
	 * Max Length_Consignor Code
	 */
	private String wMaxFigure_ConsignorCode;
	
	//#CM696529
	/**
	 * Max Length_Consignor Name
	 */
	private String wMaxFigure_ConsignorName;
	
	//#CM696530
	/**
	 * Max Length_Supplier Code
	 */
	private String wMaxFigure_SupplierCode;
	
	//#CM696531
	/**
	 * Max Length_Supplier Name
	 */
	private String wMaxFigure_SupplierName;
	
	//#CM696532
	/**
	 * Max Length_Ticket No.
	 */
	private String wMaxFigure_TicketNo;
	
	//#CM696533
	/**
	 * Max Length_Ticket Line No.
	 */
	private String wMaxFigure_LineNo;
	
	//#CM696534
	/**
	 * Max Length_Item Code
	 */
	private String wMaxFigure_ItemCode;
	
	//#CM696535
	/**
	 * Max Length_Bundle ITF
	 */
	private String wMaxFigure_BundleItf;
	
	//#CM696536
	/**
	 * Max Length_Case ITF
	 */
	private String wMaxFigure_Itf;
	
	//#CM696537
	/**
	 * Max Length_Packed qty per bundle
	 */
	private String wMaxFigure_BundleEnteringQty;
	
	//#CM696538
	/**
	 * Max Length_Packed Qty per Case
	 */
	private String wMaxFigure_EnteringQty;
	
	//#CM696539
	/**
	 * Max Length_Item Name
	 */
	private String wMaxFigure_ItemName;
	
	//#CM696540
	/**
	 * Max Length_Receiving Plan Qty (Total Bulk Qty)<BR>
	 * <BR>
	 * <BR>
	 * Use Receiving Qty (Total Bulk Qty) as a field item name for loading data. Use Planned Receiving Qty (Total Bulk Qty) as a field item name for reporting data.
	 */
	private String wMaxFigure_InstockPlanQty;
	
	//#CM696541
	/**
	 * Max Length_TC/DC division
	 */
	private String wMaxFigure_TcDcFlag;
	
	//#CM696542
	/**
	 * Max Length_Customer Code
	 */
	private String wMaxFigure_CustomerCode;
	
	//#CM696543
	/**
	 * Max Length_Customer Name
	 */
	private String wMaxFigure_CustomerName;
	
	//#CM696544
	/**
	 * Max Length_Receiving Qty (Total Bulk Qty)
	 */
	private String wMaxFigure_InstockResultQty;
	
	//#CM696545
	/**
	 * Max Length_Receiving Result Date
	 */
	private String wMaxFigure_InstockWorkDate;
	
	//#CM696546
	/**
	 * Max Length_Result division
	 */
	private String wMaxFigure_ResultFlag;
	
	//#CM696547
	/**
	 * Max Length_Expiry Date
	 */
	private String wMaxFigure_UseByDate;
	
	//#CM696548
	/**
	 * Position_Planned Receiving Date
	 */
	private String wPosition_InstockPlanDate;
	
	//#CM696549
	/**
	 * Position_Order Date
	 */
	private String wPosition_InstockOrderingDate;
	
	//#CM696550
	/**
	 * Position_Consignor Code
	 */
	private String wPosition_ConsignorCode;
	
	//#CM696551
	/**
	 * Position_Consignor Name
	 */
	private String wPosition_ConsignorName;
	
	//#CM696552
	/**
	 * Position_Supplier Code
	 */
	private String wPosition_SupplierCode;
	
	//#CM696553
	/**
	 * Position_Supplier Name
	 */
	private String wPosition_SupplierName;
	
	//#CM696554
	/**
	 * Position_Ticket No.
	 */
	private String wPosition_TicketNo;
	
	//#CM696555
	/**
	 * Position_Ticket Line No.
	 */
	private String wPosition_LineNo;
	
	//#CM696556
	/**
	 * Position_Item Code
	 */
	private String wPosition_ItemCode;
	
	//#CM696557
	/**
	 * Position_Bundle ITF
	 */
	private String wPosition_BundleItf;
	
	//#CM696558
	/**
	 * Position_Case ITF
	 */
	private String wPosition_Itf;
	
	//#CM696559
	/**
	 * Position_Packed qty per bundle
	 */
	private String wPosition_BundleEnteringQty;
	
	//#CM696560
	/**
	 * Position_Packed Qty per Case
	 */
	private String wPosition_EnteringQty;
	
	//#CM696561
	/**
	 * Position_Item Name
	 */
	private String wPosition_ItemName;
	
	//#CM696562
	/**
	 * Position_Receiving Plan Qty (Total Bulk Qty)<BR>
	 * <BR>
	 * <BR>
	 * Use Receiving Qty (Total Bulk Qty) as a field item name for loading data. Use Planned Receiving Qty (Total Bulk Qty) as a field item name for reporting data.
	 */
	private String wPosition_InstockPlanQty;
	
	//#CM696563
	/**
	 * Position_TC/DC division
	 */
	private String wPosition_TcDcFlag;
	
	//#CM696564
	/**
	 * Position_Customer Code
	 */
	private String wPosition_CustomerCode;
	
	//#CM696565
	/**
	 * Position_Customer Name
	 */
	private String wPosition_CustomerName;
	
	//#CM696566
	/**
	 * Position_Receiving Qty (Total Bulk Qty)
	 */
	private String wPosition_InstockResultQty;
	
	//#CM696567
	/**
	 * Position_Receiving Result Date
	 */
	private String wPosition_InstockWorkDate;
	
	//#CM696568
	/**
	 * Position_Result division
	 */
	private String wPosition_ResultFlag;
	
	//#CM696569
	/**
	 * Position_Expiry Date
	 */
	private String wPosition_UseByDate;

	//#CM696570
	/**
	 * Enabled_Planned Receiving Date
	 */
	private boolean wValid_InstockPlanDate;
	
	//#CM696571
	/**
	 * Enabled_Order Date
	 */
	private boolean wValid_InstockOrderingDate;
	
	//#CM696572
	/**
	 * Enabled_Consignor Code
	 */
	private boolean wValid_ConsignorCode;
	
	//#CM696573
	/**
	 * Enabled_Consignor Name
	 */
	private boolean wValid_ConsignorName;
	
	//#CM696574
	/**
	 * Enabled_Supplier Code
	 */
	private boolean wValid_SupplierCode;
	
	//#CM696575
	/**
	 * Enabled_Supplier Name
	 */
	private boolean wValid_SupplierName;
	
	//#CM696576
	/**
	 * Enabled_Ticket No.
	 */
	private boolean wValid_TicketNo;
	
	//#CM696577
	/**
	 * Enabled_Ticket Line No.
	 */
	private boolean wValid_LineNo;
	
	//#CM696578
	/**
	 * Enabled_Item Code
	 */
	private boolean wValid_ItemCode;
	
	//#CM696579
	/**
	 * Enabled_Bundle ITF
	 */
	private boolean wValid_BundleItf;
	
	//#CM696580
	/**
	 * Enabled_Case ITF
	 */
	private boolean wValid_Itf;
	
	//#CM696581
	/**
	 * Enabled_Packed qty per bundle
	 */
	private boolean wValid_BundleEnteringQty;
	
	//#CM696582
	/**
	 * Enabled_Packed Qty per Case
	 */
	private boolean wValid_EnteringQty;
	
	//#CM696583
	/**
	 * Enabled_Item Name
	 */
	private boolean wValid_ItemName;
	
	//#CM696584
	/**
	 * Enabled_Receiving Plan Qty (Total Bulk Qty)<BR>
	 * <BR>
	 * <BR>
	 * Use Receiving Qty (Total Bulk Qty) as a field item name for loading data. Use Planned Receiving Qty (Total Bulk Qty) as a field item name for reporting data.
	 */
	private boolean wValid_InstockPlanQty;
	
	//#CM696585
	/**
	 * Enabled_TC/DC division
	 */
	private boolean wValid_TcDcFlag;
	
	//#CM696586
	/**
	 * Enabled_Customer Code
	 */
	private boolean wValid_CustomerCode;
	
	//#CM696587
	/**
	 * Enabled_Customer Name
	 */
	private boolean wValid_CustomerName;
	
	//#CM696588
	/**
	 * Enabled_Receiving Qty (Total Bulk Qty)
	 */
	private boolean wValid_InstockResultQty;
	
	//#CM696589
	/**
	 * Enabled_Receiving Result Date
	 */
	private boolean wValid_InstockWorkDate;
	
	//#CM696590
	/**
	 * Enabled_Result division
	 */
	private boolean wValid_ResultFlag;
	
	//#CM696591
	/**
	 * Enabled_Expiry Date
	 */
	private boolean wValid_UseByDate;


	//#CM696592
	// Class method --------------------------------------------------
	//#CM696593
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:12 $");
	}

	//#CM696594
	// Constructors --------------------------------------------------
	//#CM696595
	/**
	 * Initialize this class.
	 */
	public DefineInstockDataParameter()
	{
	}
	
	//#CM696596
	// Public methods ------------------------------------------------

	//#CM696597
	/**
	 * Worker Code
	 * @return	Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM696598
	/**
	 * Password
	 * @return	Password
	 */
	public String getPassword()
	{
		return wPassword;
	}

	//#CM696599
	/**
	 * Length_Packed qty per bundle
	 * @return	Length_Packed qty per bundle
	 */
	public String getFigure_BundleEnteringQty()
	{
		return wFigure_BundleEnteringQty;
	}

	//#CM696600
	/**
	 * Length_Bundle ITF
	 * @return	Length_Bundle ITF
	 */
	public String getFigure_BundleItf()
	{
		return wFigure_BundleItf;
	}

	//#CM696601
	/**
	 * Length_Consignor Code
	 * @return	Length_Consignor Code
	 */
	public String getFigure_ConsignorCode()
	{
		return wFigure_ConsignorCode;
	}

	//#CM696602
	/**
	 * Length_Consignor Name
	 * @return	Length_Consignor Name
	 */
	public String getFigure_ConsignorName()
	{
		return wFigure_ConsignorName;
	}

	//#CM696603
	/**
	 * Length_Customer Code
	 * @return	Length_Customer Code
	 */
	public String getFigure_CustomerCode()
	{
		return wFigure_CustomerCode;
	}

	//#CM696604
	/**
	 * Length_Customer Name
	 * @return	Length_Customer Name
	 */
	public String getFigure_CustomerName()
	{
		return wFigure_CustomerName;
	}

	//#CM696605
	/**
	 * Length_Packed Qty per Case
	 * @return	Length_Packed Qty per Case
	 */
	public String getFigure_EnteringQty()
	{
		return wFigure_EnteringQty;
	}

	//#CM696606
	/**
	 * Length_Order Date
	 * @return	Length_Order Date
	 */
	public String getFigure_InstockOrderingDate()
	{
		return wFigure_InstockOrderingDate;
	}

	//#CM696607
	/**
	 * Length_Planned Receiving Date
	 * @return	Length_Planned Receiving Date
	 */
	public String getFigure_InstockPlanDate()
	{
		return wFigure_InstockPlanDate;
	}

	//#CM696608
	/**
	 * Length_Receiving Plan Qty (Total Bulk Qty)
	 * @return	Length_Receiving Plan Qty (Total Bulk Qty)
	 */
	public String getFigure_InstockPlanQty()
	{
		return wFigure_InstockPlanQty;
	}

	//#CM696609
	/**
	 * Length_Receiving Qty (Total Bulk Qty)
	 * @return	Length_Receiving Qty (Total Bulk Qty)
	 */
	public String getFigure_InstockResultQty()
	{
		return wFigure_InstockResultQty;
	}

	//#CM696610
	/**
	 * Length_Receiving Result Date
	 * @return	Length_Receiving Result Date
	 */
	public String getFigure_InstockWorkDate()
	{
		return wFigure_InstockWorkDate;
	}

	//#CM696611
	/**
	 * Length_Item Code
	 * @return	Length_Item Code
	 */
	public String getFigure_ItemCode()
	{
		return wFigure_ItemCode;
	}

	//#CM696612
	/**
	 * Length_Item Name
	 * @return	Length_Item Name
	 */
	public String getFigure_ItemName()
	{
		return wFigure_ItemName;
	}

	//#CM696613
	/**
	 * Length_Case ITF
	 * @return	Length_Case ITF
	 */
	public String getFigure_Itf()
	{
		return wFigure_Itf;
	}

	//#CM696614
	/**
	 * Length_Ticket Line No.
	 * @return	Length_Ticket Line No.
	 */
	public String getFigure_LineNo()
	{
		return wFigure_LineNo;
	}

	//#CM696615
	/**
	 * Length_Result division <BR>
	 * @return	Length_Result division <BR>
	 */
	public String getFigure_ResultFlag()
	{
		return wFigure_ResultFlag;
	}

	//#CM696616
	/**
	 * Length_Supplier Code
	 * @return	Length_Supplier Code
	 */
	public String getFigure_SupplierCode()
	{
		return wFigure_SupplierCode;
	}

	//#CM696617
	/**
	 * Length_Supplier Name
	 * @return	Length_Supplier Name
	 */
	public String getFigure_SupplierName()
	{
		return wFigure_SupplierName;
	}

	//#CM696618
	/**
	 * Length_TC/DC division
	 * @return	Length_TC/DC division
	 */
	public String getFigure_TcDcFlag()
	{
		return wFigure_TcDcFlag;
	}

	//#CM696619
	/**
	 * Length_Ticket No.
	 * @return	Length_Ticket No.
	 */
	public String getFigure_TicketNo()
	{
		return wFigure_TicketNo;
	}

	//#CM696620
	/**
	 * Length_Expiry Date
	 * @return	Length_Expiry Date
	 */
	public String getFigure_UseByDate()
	{
		return wFigure_UseByDate;
	}

	//#CM696621
	/**
	 * Max Length_Packed qty per bundle
	 * @return	Max Length_Packed qty per bundle
	 */
	public String getMaxFigure_BundleEnteringQty()
	{
		return wMaxFigure_BundleEnteringQty;
	}

	//#CM696622
	/**
	 * Max Length_Bundle ITF
	 * @return	Max Length_Bundle ITF
	 */
	public String getMaxFigure_BundleItf()
	{
		return wMaxFigure_BundleItf;
	}

	//#CM696623
	/**
	 * Max Length_Consignor Code
	 * @return	Max Length_Consignor Code
	 */
	public String getMaxFigure_ConsignorCode()
	{
		return wMaxFigure_ConsignorCode;
	}

	//#CM696624
	/**
	 * Max Length_Consignor Name
	 * @return	Max Length_Consignor Name
	 */
	public String getMaxFigure_ConsignorName()
	{
		return wMaxFigure_ConsignorName;
	}

	//#CM696625
	/**
	 * Max Length_Customer Code
	 * @return	Max Length_Customer Code
	 */
	public String getMaxFigure_CustomerCode()
	{
		return wMaxFigure_CustomerCode;
	}

	//#CM696626
	/**
	 * Max Length_Customer Name
	 * @return	Max Length_Customer Name
	 */
	public String getMaxFigure_CustomerName()
	{
		return wMaxFigure_CustomerName;
	}

	//#CM696627
	/**
	 * Max Length_Packed Qty per Case
	 * @return	Max Length_Packed Qty per Case
	 */
	public String getMaxFigure_EnteringQty()
	{
		return wMaxFigure_EnteringQty;
	}

	//#CM696628
	/**
	 * Max Length_Order Date
	 * @return	Max Length_Order Date
	 */
	public String getMaxFigure_InstockOrderingDate()
	{
		return wMaxFigure_InstockOrderingDate;
	}

	//#CM696629
	/**
	 * Max Length_Planned Receiving Date
	 * @return	Max Length_Planned Receiving Date
	 */
	public String getMaxFigure_InstockPlanDate()
	{
		return wMaxFigure_InstockPlanDate;
	}

	//#CM696630
	/**
	 * Max Length_Receiving Plan Qty (Total Bulk Qty)
	 * @return	Max Length_Receiving Plan Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_InstockPlanQty()
	{
		return wMaxFigure_InstockPlanQty;
	}

	//#CM696631
	/**
	 * Max Length_Receiving Qty (Total Bulk Qty)
	 * @return	Max Length_Receiving Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_InstockResultQty()
	{
		return wMaxFigure_InstockResultQty;
	}

	//#CM696632
	/**
	 * Max Length_Receiving Result Date
	 * @return	Max Length_Receiving Result Date
	 */
	public String getMaxFigure_InstockWorkDate()
	{
		return wMaxFigure_InstockWorkDate;
	}

	//#CM696633
	/**
	 * Max Length_Item Code
	 * @return	Max Length_Item Code
	 */
	public String getMaxFigure_ItemCode()
	{
		return wMaxFigure_ItemCode;
	}

	//#CM696634
	/**
	 * Max Length_Item Name
	 * @return	Max Length_Item Name
	 */
	public String getMaxFigure_ItemName()
	{
		return wMaxFigure_ItemName;
	}

	//#CM696635
	/**
	 * Max Length_Case ITF
	 * @return	Max Length_Case ITF
	 */
	public String getMaxFigure_Itf()
	{
		return wMaxFigure_Itf;
	}

	//#CM696636
	/**
	 * Max Length_Ticket Line No.
	 * @return	Max Length_Ticket Line No.
	 */
	public String getMaxFigure_LineNo()
	{
		return wMaxFigure_LineNo;
	}

	//#CM696637
	/**
	 * Max Length_Result division
	 * @return	Max Length_Result division
	 */
	public String getMaxFigure_ResultFlag()
	{
		return wMaxFigure_ResultFlag;
	}

	//#CM696638
	/**
	 * Max Length_Supplier Code
	 * @return	Max Length_Supplier Code
	 */
	public String getMaxFigure_SupplierCode()
	{
		return wMaxFigure_SupplierCode;
	}

	//#CM696639
	/**
	 * Max Length_Supplier Name
	 * @return	Max Length_Supplier Name
	 */
	public String getMaxFigure_SupplierName()
	{
		return wMaxFigure_SupplierName;
	}

	//#CM696640
	/**
	 * Max Length_TC/DC division
	 * @return	Max Length_TC/DC division
	 */
	public String getMaxFigure_TcDcFlag()
	{
		return wMaxFigure_TcDcFlag;
	}

	//#CM696641
	/**
	 * Max Length_Ticket No.
	 * @return	Max Length_Ticket No.
	 */
	public String getMaxFigure_TicketNo()
	{
		return wMaxFigure_TicketNo;
	}

	//#CM696642
	/**
	 * Max Length_Expiry Date
	 * @return	Max Length_Expiry Date
	 */
	public String getMaxFigure_UseByDate()
	{
		return wMaxFigure_UseByDate;
	}

	//#CM696643
	/**
	 * Position_Packed qty per bundle
	 * @return	Position_Packed qty per bundle
	 */
	public String getPosition_BundleEnteringQty()
	{
		return wPosition_BundleEnteringQty;
	}

	//#CM696644
	/**
	 * Position_Bundle ITF
	 * @return	Position_Bundle ITF
	 */
	public String getPosition_BundleItf()
	{
		return wPosition_BundleItf;
	}

	//#CM696645
	/**
	 * Position_Consignor Code
	 * @return	Position_Consignor Code
	 */
	public String getPosition_ConsignorCode()
	{
		return wPosition_ConsignorCode;
	}

	//#CM696646
	/**
	 * Position_Consignor Name
	 * @return	Position_Consignor Name
	 */
	public String getPosition_ConsignorName()
	{
		return wPosition_ConsignorName;
	}

	//#CM696647
	/**
	 * Position_Customer Code
	 * @return	Position_Customer Code
	 */
	public String getPosition_CustomerCode()
	{
		return wPosition_CustomerCode;
	}

	//#CM696648
	/**
	 * Position_Customer Name
	 * @return	Position_Customer Name
	 */
	public String getPosition_CustomerName()
	{
		return wPosition_CustomerName;
	}

	//#CM696649
	/**
	 * Position_Packed Qty per Case
	 * @return	Position_Packed Qty per Case
	 */
	public String getPosition_EnteringQty()
	{
		return wPosition_EnteringQty;
	}

	//#CM696650
	/**
	 * Position_Order Date
	 * @return	Position_Order Date
	 */
	public String getPosition_InstockOrderingDate()
	{
		return wPosition_InstockOrderingDate;
	}

	//#CM696651
	/**
	 * Position_Planned Receiving Date
	 * @return	Position_Planned Receiving Date
	 */
	public String getPosition_InstockPlanDate()
	{
		return wPosition_InstockPlanDate;
	}

	//#CM696652
	/**
	 * Position_Receiving Plan Qty (Total Bulk Qty)
	 * @return	Position_Receiving Plan Qty (Total Bulk Qty)
	 */
	public String getPosition_InstockPlanQty()
	{
		return wPosition_InstockPlanQty;
	}

	//#CM696653
	/**
	 * Position_Receiving Qty (Total Bulk Qty)
	 * @return	Position_Receiving Qty (Total Bulk Qty)
	 */
	public String getPosition_InstockResultQty()
	{
		return wPosition_InstockResultQty;
	}

	//#CM696654
	/**
	 * Position_Receiving Result Date
	 * @return	Position_Receiving Result Date
	 */
	public String getPosition_InstockWorkDate()
	{
		return wPosition_InstockWorkDate;
	}

	//#CM696655
	/**
	 * Position_Item Code
	 * @return	Position_Item Code
	 */
	public String getPosition_ItemCode()
	{
		return wPosition_ItemCode;
	}

	//#CM696656
	/**
	 * Position_Item Name
	 * @return	Position_Item Name
	 */
	public String getPosition_ItemName()
	{
		return wPosition_ItemName;
	}

	//#CM696657
	/**
	 * Position_Case ITF
	 * @return	Position_Case ITF
	 */
	public String getPosition_Itf()
	{
		return wPosition_Itf;
	}

	//#CM696658
	/**
	 * Position_Ticket Line No.
	 * @return	Position_Ticket Line No.
	 */
	public String getPosition_LineNo()
	{
		return wPosition_LineNo;
	}

	//#CM696659
	/**
	 * Position_Result division
	 * @return	Position_Result division
	 */
	public String getPosition_ResultFlag()
	{
		return wPosition_ResultFlag;
	}

	//#CM696660
	/**
	 * Position_Supplier Code
	 * @return	Position_Supplier Code
	 */
	public String getPosition_SupplierCode()
	{
		return wPosition_SupplierCode;
	}

	//#CM696661
	/**
	 * Position_Supplier Name
	 * @return	Position_Supplier Name
	 */
	public String getPosition_SupplierName()
	{
		return wPosition_SupplierName;
	}

	//#CM696662
	/**
	 * Position_TC/DC division
	 * @return	Position_TC/DC division
	 */
	public String getPosition_TcDcFlag()
	{
		return wPosition_TcDcFlag;
	}

	//#CM696663
	/**
	 * Position_Ticket No.
	 * @return	Position_Ticket No.
	 */
	public String getPosition_TicketNo()
	{
		return wPosition_TicketNo;
	}

	//#CM696664
	/**
	 * Position_Expiry Date
	 * @return	Position_Expiry Date
	 */
	public String getPosition_UseByDate()
	{
		return wPosition_UseByDate;
	}

	//#CM696665
	/**
	 * Enabled_Packed qty per bundle
	 * @return	Enabled_Packed qty per bundle
	 */
	public boolean getValid_BundleEnteringQty()
	{
		return wValid_BundleEnteringQty;
	}

	//#CM696666
	/**
	 * Enabled_Bundle ITF
	 * @return	Enabled_Bundle ITF
	 */
	public boolean getValid_BundleItf()
	{
		return wValid_BundleItf;
	}

	//#CM696667
	/**
	 * Enabled_Consignor Code
	 * @return	Enabled_Consignor Code
	 */
	public boolean getValid_ConsignorCode()
	{
		return wValid_ConsignorCode;
	}

	//#CM696668
	/**
	 * Enabled_Consignor Name
	 * @return	Enabled_Consignor Name
	 */
	public boolean getValid_ConsignorName()
	{
		return wValid_ConsignorName;
	}

	//#CM696669
	/**
	 * Enabled_Customer Code
	 * @return	Enabled_Customer Code
	 */
	public boolean getValid_CustomerCode()
	{
		return wValid_CustomerCode;
	}

	//#CM696670
	/**
	 * Enabled_Customer Name
	 * @return	Enabled_Customer Name
	 */
	public boolean getValid_CustomerName()
	{
		return wValid_CustomerName;
	}

	//#CM696671
	/**
	 * Enabled_Packed Qty per Case
	 * @return	Enabled_Packed Qty per Case
	 */
	public boolean getValid_EnteringQty()
	{
		return wValid_EnteringQty;
	}

	//#CM696672
	/**
	 * Enabled_Order Date
	 * @return	Enabled_Order Date
	 */
	public boolean getValid_InstockOrderingDate()
	{
		return wValid_InstockOrderingDate;
	}

	//#CM696673
	/**
	 * Enabled_Planned Receiving Date
	 * @return	Enabled_Planned Receiving Date
	 */
	public boolean getValid_InstockPlanDate()
	{
		return wValid_InstockPlanDate;
	}

	//#CM696674
	/**
	 * Enabled_Receiving Plan Qty (Total Bulk Qty)
	 * @return	Enabled_Receiving Plan Qty (Total Bulk Qty)
	 */
	public boolean getValid_InstockPlanQty()
	{
		return wValid_InstockPlanQty;
	}

	//#CM696675
	/**
	 * Enabled_Receiving Qty (Total Bulk Qty)
	 * @return	Enabled_Receiving Qty (Total Bulk Qty)
	 */
	public boolean getValid_InstockResultQty()
	{
		return wValid_InstockResultQty;
	}

	//#CM696676
	/**
	 * Enabled_Receiving Result Date
	 * @return	Enabled_Receiving Result Date
	 */
	public boolean getValid_InstockWorkDate()
	{
		return wValid_InstockWorkDate;
	}

	//#CM696677
	/**
	 * Enabled_Item Code
	 * @return	Enabled_Item Code
	 */
	public boolean getValid_ItemCode()
	{
		return wValid_ItemCode;
	}

	//#CM696678
	/**
	 * Enabled_Item Name
	 * @return	Enabled_Item Name
	 */
	public boolean getValid_ItemName()
	{
		return wValid_ItemName;
	}

	//#CM696679
	/**
	 * Enabled_Case ITF
	 * @return	Enabled_Case ITF
	 */
	public boolean getValid_Itf()
	{
		return wValid_Itf;
	}

	//#CM696680
	/**
	 * Enabled_Ticket Line No.
	 * @return	Enabled_Ticket Line No.
	 */
	public boolean getValid_LineNo()
	{
		return wValid_LineNo;
	}

	//#CM696681
	/**
	 * Enabled_Result division
	 * @return	Enabled_Result division
	 */
	public boolean getValid_ResultFlag()
	{
		return wValid_ResultFlag;
	}

	//#CM696682
	/**
	 * Enabled_Supplier Code
	 * @return	Enabled_Supplier Code
	 */
	public boolean getValid_SupplierCode()
	{
		return wValid_SupplierCode;
	}

	//#CM696683
	/**
	 * Enabled_Supplier Name
	 * @return	Enabled_Supplier Name
	 */
	public boolean getValid_SupplierName()
	{
		return wValid_SupplierName;
	}

	//#CM696684
	/**
	 * Enabled_TC/DC division
	 * @return	Enabled_TC/DC division
	 */
	public boolean getValid_TcDcFlag()
	{
		return wValid_TcDcFlag;
	}

	//#CM696685
	/**
	 * Enabled_Ticket No.
	 * @return	Enabled_Ticket No.
	 */
	public boolean getValid_TicketNo()
	{
		return wValid_TicketNo;
	}

	//#CM696686
	/**
	 * Enabled_Expiry Date
	 * @return	Enabled_Expiry Date
	 */
	public boolean getValid_UseByDate()
	{
		return wValid_UseByDate;
	}

	//#CM696687
	/**
	 * Worker Code
	 * @param arg	Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		wWorkerCode = arg;
	}

	//#CM696688
	/**
	 * Password
	 * @param arg Password
	 */
	public void setPassword(String arg)
	{
		wPassword = arg;
	}

	//#CM696689
	/**
	 * Length_Packed qty per bundle
	 * @param arg	Length_Packed qty per bundle to be set
	 */
	public void setFigure_BundleEnteringQty(String arg)
	{
		wFigure_BundleEnteringQty = arg;
	}

	//#CM696690
	/**
	 * Length_Bundle ITF
	 * @param arg	Length_Bundle ITF to be set
	 */
	public void setFigure_BundleItf(String arg)
	{
		wFigure_BundleItf = arg;
	}

	//#CM696691
	/**
	 * Length_Consignor Code
	 * @param arg	Length_Consignor Code to be set
	 */
	public void setFigure_ConsignorCode(String arg)
	{
		wFigure_ConsignorCode = arg;
	}

	//#CM696692
	/**
	 * Length_Consignor Name
	 * @param arg	Length_Consignor Name to be set
	 */
	public void setFigure_ConsignorName(String arg)
	{
		wFigure_ConsignorName = arg;
	}

	//#CM696693
	/**
	 * Length_Customer Code
	 * @param arg	Length_Customer Code to be set
	 */
	public void setFigure_CustomerCode(String arg)
	{
		wFigure_CustomerCode = arg;
	}

	//#CM696694
	/**
	 * Length_Customer Name
	 * @param arg	Length_Customer Name to be set
	 */
	public void setFigure_CustomerName(String arg)
	{
		wFigure_CustomerName = arg;
	}

	//#CM696695
	/**
	 * Length_Packed Qty per Case
	 * @param arg	Length_Packed Qty per Case to be set
	 */
	public void setFigure_EnteringQty(String arg)
	{
		wFigure_EnteringQty = arg;
	}

	//#CM696696
	/**
	 * Length_Order Date
	 * @param arg	Length_Order Date to be set
	 */
	public void setFigure_InstockOrderingDate(String arg)
	{
		wFigure_InstockOrderingDate = arg;
	}

	//#CM696697
	/**
	 * Length_Planned Receiving Date
	 * @param arg	Length_Planned Receiving Date to be set
	 */
	public void setFigure_InstockPlanDate(String arg)
	{
		wFigure_InstockPlanDate = arg;
	}

	//#CM696698
	/**
	 * Length_Receiving Plan Qty (Total Bulk Qty)
	 * @param arg	Length_Receiving Plan Qty (Total Bulk Qty) to be set
	 */
	public void setFigure_InstockPlanQty(String arg)
	{
		wFigure_InstockPlanQty = arg;
	}

	//#CM696699
	/**
	 * Length_Receiving Qty (Total Bulk Qty)
	 * @param arg	Length_Receiving Qty (Total Bulk Qty) to be set
	 */
	public void setFigure_InstockResultQty(String arg)
	{
		wFigure_InstockResultQty = arg;
	}

	//#CM696700
	/**
	 * Length_Receiving Result Date
	 * @param arg	Length_Receiving Result Date to be set
	 */
	public void setFigure_InstockWorkDate(String arg)
	{
		wFigure_InstockWorkDate = arg;
	}

	//#CM696701
	/**
	 * Length_Item Code
	 * @param arg	Length_Item Code to be set
	 */
	public void setFigure_ItemCode(String arg)
	{
		wFigure_ItemCode = arg;
	}

	//#CM696702
	/**
	 * Length_Item Name
	 * @param arg	Length_Item Name to be set
	 */
	public void setFigure_ItemName(String arg)
	{
		wFigure_ItemName = arg;
	}

	//#CM696703
	/**
	 * Length_Case ITF
	 * @param arg	Length_Case ITF to be set
	 */
	public void setFigure_Itf(String arg)
	{
		wFigure_Itf = arg;
	}

	//#CM696704
	/**
	 * Length_Ticket Line No.
	 * @param arg	Length_Ticket Line No. to be set
	 */
	public void setFigure_LineNo(String arg)
	{
		wFigure_LineNo = arg;
	}

	//#CM696705
	/**
	 * Length_Result division
	 * @param arg	Length_Result division to be set
	 */
	public void setFigure_ResultFlag(String arg)
	{
		wFigure_ResultFlag = arg;
	}

	//#CM696706
	/**
	 * Length_Supplier Code
	 * @param arg	Length_Supplier Code to be set
	 */
	public void setFigure_SupplierCode(String arg)
	{
		wFigure_SupplierCode = arg;
	}

	//#CM696707
	/**
	 * Length_Supplier Name
	 * @param arg	Length_Supplier Name to be set
	 */
	public void setFigure_SupplierName(String arg)
	{
		wFigure_SupplierName = arg;
	}

	//#CM696708
	/**
	 * Length_TC/DC division
	 * @param arg	Length_TC/DC division to be set
	 */
	public void setFigure_TcDcFlag(String arg)
	{
		wFigure_TcDcFlag = arg;
	}

	//#CM696709
	/**
	 * Length_Ticket No.
	 * @param arg	Length_Ticket No. to be set
	 */
	public void setFigure_TicketNo(String arg)
	{
		wFigure_TicketNo = arg;
	}

	//#CM696710
	/**
	 * Length_Expiry Date
	 * @param arg	Length_Expiry Date to be set
	 */
	public void setFigure_UseByDate(String arg)
	{
		wFigure_UseByDate = arg;
	}

	//#CM696711
	/**
	 * Max Length_Packed qty per bundle
	 * @param arg	Max Length_Packed qty per bundle to be set
	 */
	public void setMaxFigure_BundleEnteringQty(String arg)
	{
		wMaxFigure_BundleEnteringQty = arg;
	}

	//#CM696712
	/**
	 * Max Length_Bundle ITF
	 * @param arg	Max Length_Bundle ITF to be set
	 */
	public void setMaxFigure_BundleItf(String arg)
	{
		wMaxFigure_BundleItf = arg;
	}

	//#CM696713
	/**
	 * Max Length_Consignor Code
	 * @param arg	Max Length_Consignor Code to be set
	 */
	public void setMaxFigure_ConsignorCode(String arg)
	{
		wMaxFigure_ConsignorCode = arg;
	}

	//#CM696714
	/**
	 * Max Length_Consignor Name
	 * @param arg	Max Length_Consignor Name to be set
	 */
	public void setMaxFigure_ConsignorName(String arg)
	{
		wMaxFigure_ConsignorName = arg;
	}

	//#CM696715
	/**
	 * Max Length_Customer Code
	 * @param arg	Max Length_Customer Code to be set
	 */
	public void setMaxFigure_CustomerCode(String arg)
	{
		wMaxFigure_CustomerCode = arg;
	}

	//#CM696716
	/**
	 * Max Length_Customer Name
	 * @param arg	Max Length_Customer Name to be set
	 */
	public void setMaxFigure_CustomerName(String arg)
	{
		wMaxFigure_CustomerName = arg;
	}

	//#CM696717
	/**
	 * Max Length_Packed Qty per Case
	 * @param arg	Max Length_Packed Qty per Case to be set
	 */
	public void setMaxFigure_EnteringQty(String arg)
	{
		wMaxFigure_EnteringQty = arg;
	}

	//#CM696718
	/**
	 * Max Length_Order Date
	 * @param arg	Max Length_Order Dateg to be set
	 */
	public void setMaxFigure_InstockOrderingDate(String arg)
	{
		wMaxFigure_InstockOrderingDate = arg;
	}

	//#CM696719
	/**
	 * Max Length_Planned Receiving Date
	 * @param arg	Max Length_Planned Receiving Date to be set
	 */
	public void setMaxFigure_InstockPlanDate(String arg)
	{
		wMaxFigure_InstockPlanDate = arg;
	}

	//#CM696720
	/**
	 * Max Length_Receiving Plan Qty (Total Bulk Qty)
	 * @param arg	Max Length_Receiving Plan Qty (Total Bulk Qty) to be set
	 */
	public void setMaxFigure_InstockPlanQty(String arg)
	{
		wMaxFigure_InstockPlanQty = arg;
	}

	//#CM696721
	/**
	 * Max Length_Receiving Qty (Total Bulk Qty)
	 * @param arg	Max Length_Receiving Qty (Total Bulk Qty) to be set
	 */
	public void setMaxFigure_InstockResultQty(String arg)
	{
		wMaxFigure_InstockResultQty = arg;
	}

	//#CM696722
	/**
	 * Max Length_Receiving Result Date
	 * @param arg	Max Length_Receiving Result Date to be set
	 */
	public void setMaxFigure_InstockWorkDate(String arg)
	{
		wMaxFigure_InstockWorkDate = arg;
	}

	//#CM696723
	/**
	 * Max Length_Item Code
	 * @param arg	Max Length_Item Code to be set
	 */
	public void setMaxFigure_ItemCode(String arg)
	{
		wMaxFigure_ItemCode = arg;
	}

	//#CM696724
	/**
	 * Max Length_Item Name
	 * @param arg	Max Length_Item Name to be set
	 */
	public void setMaxFigure_ItemName(String arg)
	{
		wMaxFigure_ItemName = arg;
	}

	//#CM696725
	/**
	 * Max Length_Case ITF
	 * @param arg	Max Length_Case ITF to be set
	 */
	public void setMaxFigure_Itf(String arg)
	{
		wMaxFigure_Itf = arg;
	}

	//#CM696726
	/**
	 * Max Length_Ticket Line No.
	 * @param arg	Max Length_Ticket Line No. to be set
	 */
	public void setMaxFigure_LineNo(String arg)
	{
		wMaxFigure_LineNo = arg;
	}

	//#CM696727
	/**
	 * Max Length_Result division
	 * @param arg	Max Length_Result division to be set
	 */
	public void setMaxFigure_ResultFlag(String arg)
	{
		wMaxFigure_ResultFlag = arg;
	}

	//#CM696728
	/**
	 * Max Length_Supplier Code
	 * @param arg	Max Length_Supplier Code to be set
	 */
	public void setMaxFigure_SupplierCode(String arg)
	{
		wMaxFigure_SupplierCode = arg;
	}

	//#CM696729
	/**
	 * Max Length_Supplier Name
	 * @param arg	Max Length_Supplier Name to be set
	 */
	public void setMaxFigure_SupplierName(String arg)
	{
		wMaxFigure_SupplierName = arg;
	}

	//#CM696730
	/**
	 * Max Length_TC/DC division
	 * @param arg	Max Length_TC/DC division to be set
	 */
	public void setMaxFigure_TcDcFlag(String arg)
	{
		wMaxFigure_TcDcFlag = arg;
	}

	//#CM696731
	/**
	 * Max Length_Ticket No.
	 * @param arg	Max Length_Ticket No. to be set
	 */
	public void setMaxFigure_TicketNo(String arg)
	{
		wMaxFigure_TicketNo = arg;
	}

	//#CM696732
	/**
	 * Max Length_Expiry Date
	 * @param arg	Max Length_Expiry Date to be set
	 */
	public void setMaxFigure_UseByDate(String arg)
	{
		wMaxFigure_UseByDate = arg;
	}

	//#CM696733
	/**
	 * Position_Packed qty per bundle
	 * @param arg	Position_Packed qty per bundle to be set
	 */
	public void setPosition_BundleEnteringQty(String arg)
	{
		wPosition_BundleEnteringQty = arg;
	}

	//#CM696734
	/**
	 * Position_Bundle ITF
	 * @param arg	Position_Bundle ITF to be set
	 */
	public void setPosition_BundleItf(String arg)
	{
		wPosition_BundleItf = arg;
	}

	//#CM696735
	/**
	 * Position_Consignor Code
	 * @param arg	Position_Consignor Code to be set
	 */
	public void setPosition_ConsignorCode(String arg)
	{
		wPosition_ConsignorCode = arg;
	}

	//#CM696736
	/**
	 * Position_Consignor Name
	 * @param arg	Position_Consignor Name to be set
	 */
	public void setPosition_ConsignorName(String arg)
	{
		wPosition_ConsignorName = arg;
	}

	//#CM696737
	/**
	 * Position_Customer Code
	 * @param arg	Position_Customer Code to be set
	 */
	public void setPosition_CustomerCode(String arg)
	{
		wPosition_CustomerCode = arg;
	}

	//#CM696738
	/**
	 * Position_Customer Name
	 * @param arg	Position_Customer Name to be set
	 */
	public void setPosition_CustomerName(String arg)
	{
		wPosition_CustomerName = arg;
	}

	//#CM696739
	/**
	 * Position_Packed Qty per Case
	 * @param arg	Position_Packed Qty per Case to be set
	 */
	public void setPosition_EnteringQty(String arg)
	{
		wPosition_EnteringQty = arg;
	}

	//#CM696740
	/**
	 * Position_Order Date
	 * @param arg	Position_Order Date to be set
	 */
	public void setPosition_InstockOrderingDate(String arg)
	{
		wPosition_InstockOrderingDate = arg;
	}

	//#CM696741
	/**
	 * Position_Planned Receiving Date
	 * @param arg	Position_Planned Receiving Date to be set
	 */
	public void setPosition_InstockPlanDate(String arg)
	{
		wPosition_InstockPlanDate = arg;
	}

	//#CM696742
	/**
	 * Position_Receiving Plan Qty (Total Bulk Qty)
	 * @param arg	Position_Receiving Plan Qty (Total Bulk Qty) to be set
	 */
	public void setPosition_InstockPlanQty(String arg)
	{
		wPosition_InstockPlanQty = arg;
	}

	//#CM696743
	/**
	 * Position_Receiving Qty (Total Bulk Qty)
	 * @param arg	Position_Receiving Qty (Total Bulk Qty) to be set
	 */
	public void setPosition_InstockResultQty(String arg)
	{
		wPosition_InstockResultQty = arg;
	}

	//#CM696744
	/**
	 * Position_Receiving Result Date
	 * @param arg	Position_Receiving Result Date to be set
	 */
	public void setPosition_InstockWorkDate(String arg)
	{
		wPosition_InstockWorkDate = arg;
	}

	//#CM696745
	/**
	 * Position_Item Code
	 * @param arg	Position_Item Code to be set
	 */
	public void setPosition_ItemCode(String arg)
	{
		wPosition_ItemCode = arg;
	}

	//#CM696746
	/**
	 * Position_Item Name
	 * @param arg	Position_Item Name to be set
	 */
	public void setPosition_ItemName(String arg)
	{
		wPosition_ItemName = arg;
	}

	//#CM696747
	/**
	 * Position_Case ITF
	 * @param arg	Position_Case ITF to be set
	 */
	public void setPosition_Itf(String arg)
	{
		wPosition_Itf = arg;
	}

	//#CM696748
	/**
	 * Position_Ticket Line No.
	 * @param arg	Position_Ticket Line No. to be set
	 */
	public void setPosition_LineNo(String arg)
	{
		wPosition_LineNo = arg;
	}

	//#CM696749
	/**
	 * Position_Result division
	 * @param arg	Position_Result division to be set
	 */
	public void setPosition_ResultFlag(String arg)
	{
		wPosition_ResultFlag = arg;
	}

	//#CM696750
	/**
	 * Position_Supplier Code
	 * @param arg	Position_Supplier Code to be set
	 */
	public void setPosition_SupplierCode(String arg)
	{
		wPosition_SupplierCode = arg;
	}

	//#CM696751
	/**
	 * Position_Supplier Name
	 * @param arg	Position_Supplier Name to be set
	 */
	public void setPosition_SupplierName(String arg)
	{
		wPosition_SupplierName = arg;
	}

	//#CM696752
	/**
	 * Position_TC/DC division
	 * @param arg	Position_TC/DC division to be set
	 */
	public void setPosition_TcDcFlag(String arg)
	{
		wPosition_TcDcFlag = arg;
	}

	//#CM696753
	/**
	 * Position_Ticket No.
	 * @param arg	Position_Ticket No. to be set
	 */
	public void setPosition_TicketNo(String arg)
	{
		wPosition_TicketNo = arg;
	}

	//#CM696754
	/**
	 * Position_Expiry Date
	 * @param arg Position_Expiry Date
	 */
	public void setPosition_UseByDate(String arg)
	{
		wPosition_UseByDate = arg;
	}

	//#CM696755
	/**
	 * Enabled_Packed qty per bundle
	 * @param arg	Enabled_Packed qty per bundle to be set
	 */
	public void setValid_BundleEnteringQty(boolean arg)
	{
		wValid_BundleEnteringQty = arg;
	}

	//#CM696756
	/**
	 * Enabled_Bundle ITF
	 * @param arg	Enabled_Bundle ITF to be set
	 */
	public void setValid_BundleItf(boolean arg)
	{
		wValid_BundleItf = arg;
	}

	//#CM696757
	/**
	 * Enabled_Consignor Code
	 * @param arg	Enabled_Consignor Code to be set
	 */
	public void setValid_ConsignorCode(boolean arg)
	{
		wValid_ConsignorCode = arg;
	}

	//#CM696758
	/**
	 * Enabled_Consignor Name
	 * @param arg	Enabled_Consignor Name to be set
	 */
	public void setValid_ConsignorName(boolean arg)
	{
		wValid_ConsignorName = arg;
	}

	//#CM696759
	/**
	 * Enabled_Customer Code
	 * @param arg	Enabled_Customer Code to be set
	 */
	public void setValid_CustomerCode(boolean arg)
	{
		wValid_CustomerCode = arg;
	}

	//#CM696760
	/**
	 * Enabled_Customer Name
	 * @param arg	Enabled_Customer Name to be set
	 */
	public void setValid_CustomerName(boolean arg)
	{
		wValid_CustomerName = arg;
	}

	//#CM696761
	/**
	 * Enabled_Packed Qty per Case
	 * @param arg	Enabled_Packed Qty per Case to be set
	 */
	public void setValid_EnteringQty(boolean arg)
	{
		wValid_EnteringQty = arg;
	}

	//#CM696762
	/**
	 * Enabled_Order Date
	 * @param arg	Enabled_Order Date to be set
	 */
	public void setValid_InstockOrderingDate(boolean arg)
	{
		wValid_InstockOrderingDate = arg;
	}

	//#CM696763
	/**
	 * Enabled_Planned Receiving Date
	 * @param arg	Enabled_Planned Receiving Date to be set
	 */
	public void setValid_InstockPlanDate(boolean arg)
	{
		wValid_InstockPlanDate = arg;
	}

	//#CM696764
	/**
	 * Enabled_Receiving Plan Qty (Total Bulk Qty)
	 * @param arg	Enabled_Receiving Plan Qty (Total Bulk Qty) to be set
	 */
	public void setValid_InstockPlanQty(boolean arg)
	{
		wValid_InstockPlanQty = arg;
	}

	//#CM696765
	/**
	 * Enabled_Receiving Qty (Total Bulk Qty)
	 * @param arg	Enabled_Receiving Qty (Total Bulk Qty) to be set
	 */
	public void setValid_InstockResultQty(boolean arg)
	{
		wValid_InstockResultQty = arg;
	}

	//#CM696766
	/**
	 * Enabled_Receiving Result Date
	 * @param arg	Enabled_Receiving Result Date to be set
	 */
	public void setValid_InstockWorkDate(boolean arg)
	{
		wValid_InstockWorkDate = arg;
	}

	//#CM696767
	/**
	 * Enabled_Item Code
	 * @param arg	Enabled_Item Code to be set
	 */
	public void setValid_ItemCode(boolean arg)
	{
		wValid_ItemCode = arg;
	}

	//#CM696768
	/**
	 * Enabled_Item Name
	 * @param arg	Enabled_Item Name to be set
	 */
	public void setValid_ItemName(boolean arg)
	{
		wValid_ItemName = arg;
	}

	//#CM696769
	/**
	 * Enabled_Case ITF
	 * @param arg	Enabled_Case ITF to be set
	 */
	public void setValid_Itf(boolean arg)
	{
		wValid_Itf = arg;
	}

	//#CM696770
	/**
	 * Enabled_Ticket Line No.
	 * @param arg	Enabled_Ticket Line No. to be set
	 */
	public void setValid_LineNo(boolean arg)
	{
		wValid_LineNo = arg;
	}

	//#CM696771
	/**
	 * Enabled_Result division
	 * @param arg	Enabled_Result division to be set
	 */
	public void setValid_ResultFlag(boolean arg)
	{
		wValid_ResultFlag = arg;
	}

	//#CM696772
	/**
	 * Enabled_Supplier Code
	 * @param arg	Enabled_Supplier Code to be set
	 */
	public void setValid_SupplierCode(boolean arg)
	{
		wValid_SupplierCode = arg;
	}

	//#CM696773
	/**
	 * Enabled_Supplier Name
	 * @param arg	Enabled_Supplier Name to be set
	 */
	public void setValid_SupplierName(boolean arg)
	{
		wValid_SupplierName = arg;
	}

	//#CM696774
	/**
	 * Enabled_TC/DC division
	 * @param arg	Enabled_TC/DC division to be set
	 */
	public void setValid_TcDcFlag(boolean arg)
	{
		wValid_TcDcFlag = arg;
	}

	//#CM696775
	/**
	 * Enabled_Ticket No.
	 * @param arg	Enabled_Ticket No. to be set
	 */
	public void setValid_TicketNo(boolean arg)
	{
		wValid_TicketNo = arg;
	}

	//#CM696776
	/**
	 * Enabled_Expiry Date
	 * @param arg	Enabled_Expiry Date to be set
	 */
	public void setValid_UseByDate(boolean arg)
	{
		wValid_UseByDate = arg;
	}

}
//#CM696777
//end of class
