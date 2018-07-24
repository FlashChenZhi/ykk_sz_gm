//#CM697463
//$Id: DefineShippingDataParameter.java,v 1.2 2006/11/13 06:03:10 suresh Exp $

//#CM697464
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import jp.co.daifuku.wms.base.common.Parameter;
 
//#CM697465
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * Use the <CODE>DefineShippingDataParameter</CODE> class to pass a parameter between the screen and the schedule for setting field items to be loaded or reported for the Shipping data.<BR>
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
 *     Length_Planned Shipping Date <BR>
 *     Length_Order Date <BR>
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
 *     Length_Planned Shipping Qty (Total Bulk Qty) <BR>
 *     Length_TC/DC division <BR>
 *     Length_Supplier Code <BR>
 *     Length_Supplier Name <BR>
 *     Length_Receiving Ticket No. <BR>
 *     Length_Receiving Ticket Line No. <BR>
 *     Length_Shipping Qty (Total Bulk Qty) <BR>
 *     Length_Shipping Result Date <BR> 
 *     Length_Result division <BR>
 *     Length_Expiry Date <BR>
 * <BR>
 *     Max Length_Planned Shipping Date <BR>
 *     Max Length_Order Date <BR>
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
 *     Max Length_Planned Shipping Qty (Total Bulk Qty) <BR>
 *     Max Length_TC/DC division <BR>
 *     Max Length_Supplier Code <BR>
 *     Max Length_Supplier Name <BR>
 *     Max Length_Receiving Ticket No. <BR>
 *     Max Length_Receiving Ticket Line No. <BR>
 *     Max Length_Shipping Qty (Total Bulk Qty) <BR>
 *     Max Length_Shipping Result Date <BR> 
 *     Max Length_Result division <BR>
 *     Max Length_Expiry Date <BR>
 * <BR>
 *     Position_Planned Shipping Date <BR>
 *     Position_Order Date <BR>
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
 *     Position_Planned Shipping Qty (Total Bulk Qty) <BR>
 *     Position_TC/DC division <BR>
 *     Position_Supplier Code <BR>
 *     Position_Supplier Name <BR>
 *     Position_Receiving Ticket No. <BR>
 *     Position_Receiving Ticket Line No. <BR>
 *     Position_Shipping Qty (Total Bulk Qty) <BR>
 *     Position_Shipping Result Date <BR> 
 *     Position_Result division <BR>
 *     Position_Expiry Date <BR>
 * </DIR>
 * [Checkbox] <BR>
 *     Enabled_Planned Shipping Date <BR>
 *     Enabled_Order Date <BR>
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
 *     Enabled_Planned Shipping Qty (Total Bulk Qty) <BR>
 *     Enabled_TC/DC division <BR>
 *     Enabled_Supplier Code <BR>
 *     Enabled_Supplier Name <BR>
 *     Enabled_Receiving Ticket No. <BR>
 *     Enabled_Receiving Ticket Line No. <BR>
 *     Enabled_Shipping Qty (Total Bulk Qty) <BR>
 *     Enabled_Shipping Result Date <BR> 
 *     Enabled_Result division <BR>
 *     Enabled_Expiry Date <BR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/05</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:10 $
 * @author  $Author: suresh $
 */
public class DefineShippingDataParameter extends Parameter
{
	//#CM697466
	// Class variables -----------------------------------------------
	//#CM697467
	/**
	 * Worker Code
	 */
	private String wWorkerCode;
	
	//#CM697468
	/**
	 * Password
	 */
	private String wPassword;
	
	//#CM697469
	/**
	 * Length_Planned Shipping Date
	 */
	private String wFigure_ShippingPlanDate;

	//#CM697470
	/**
	 * Length_Order Date
	 */
	private String wFigure_ShippingOrderingDate;
	
	//#CM697471
	/**
	 * Length_Consignor Code
	 */
	private String wFigure_ConsignorCode;
	
	//#CM697472
	/**
	 * Length_Consignor Name
	 */
	private String wFigure_ConsignorName;
	
	//#CM697473
	/**
	 * Length_Customer Code
	 */
	private String wFigure_CustomerCode;
	
	//#CM697474
	/**
	 * Length_Customer Name
	 */
	private String wFigure_CustomerName;
	
	//#CM697475
	/**
	 * Length_Ticket No.
	 */
	private String wFigure_TicketNo;
	
	//#CM697476
	/**
	 * Length_Ticket Line No.
	 */
	private String wFigure_LineNo;
	
	//#CM697477
	/**
	 * Length_Item Code
	 */
	private String wFigure_ItemCode;
	
	//#CM697478
	/**
	 * Length_Bundle ITF
	 */
	private String wFigure_BundleItf;
	
	//#CM697479
	/**
	 * Length_Case ITF
	 */
	private String wFigure_Itf;
	
	//#CM697480
	/**
	 * Length_Packed qty per bundle
	 */
	private String wFigure_BundleEnteringQty;
	
	//#CM697481
	/**
	 * Length_Packed Qty per Case
	 */
	private String wFigure_EnteringQty;
	
	//#CM697482
	/**
	 * Length_Item Name
	 */
	private String wFigure_ItemName;
	
	//#CM697483
	/**
	 * Length_Planned Shipping Qty (Total Bulk Qty)
	 */
	private String wFigure_ShippingPlanQty;
	
	//#CM697484
	/**
	 * Length_TC/DC division
	 */
	private String wFigure_TcDcFlag;

	//#CM697485
	/**
	 * Length_Supplier Code
	 */
	private String wFigure_SupplierCode;
	
	//#CM697486
	/**
	 * Length_Supplier Name
	 */
	private String wFigure_SupplierName;
	
	//#CM697487
	/**
	 * Length_Receiving Ticket No.
	 */
	private String wFigure_InstockTicketNo;
	
	//#CM697488
	/**
	 * Length_Receiving Ticket Line No.
	 */
	private String wFigure_InstockLineNo;
	
	//#CM697489
	/**
	 * Length_Shipping Qty (Total Bulk Qty)
	 */
	private String wFigure_ShippingResultQty;
	
	//#CM697490
	/**
	 * Length_Shipping Result Date
	 */
	private String wFigure_ShippingWorkDate;
	
	//#CM697491
	/**
	 * Length_Result division
	 */
	private String wFigure_ResultFlag;
	
	//#CM697492
	/**
	 * Length_Expiry Date
	 */
	private String wFigure_UseByDate;
	
	//#CM697493
	/**
	 * Max Length_Planned Shipping Date
	 */
	private String wMaxFigure_ShippingPlanDate;

	//#CM697494
	/**
	 * Max Length_Order Date
	 */
	private String wMaxFigure_ShippingOrderingDate;
	
	//#CM697495
	/**
	 * Max Length_Consignor Code
	 */
	private String wMaxFigure_ConsignorCode;
	
	//#CM697496
	/**
	 * Max Length_Consignor Name
	 */
	private String wMaxFigure_ConsignorName;
	
	//#CM697497
	/**
	 * Max Length_Customer Code
	 */
	private String wMaxFigure_CustomerCode;
	
	//#CM697498
	/**
	 * Max Length_Customer Name
	 */
	private String wMaxFigure_CustomerName;
	
	//#CM697499
	/**
	 * Max Length_Ticket No.
	 */
	private String wMaxFigure_TicketNo;
	
	//#CM697500
	/**
	 * Max Length_Ticket Line No.
	 */
	private String wMaxFigure_LineNo;
	
	//#CM697501
	/**
	 * Max Length_Item Code
	 */
	private String wMaxFigure_ItemCode;
	
	//#CM697502
	/**
	 * Max Length_Bundle ITF
	 */
	private String wMaxFigure_BundleItf;
	
	//#CM697503
	/**
	 * Max Length_Case ITF
	 */
	private String wMaxFigure_Itf;
	
	//#CM697504
	/**
	 * Max Length_Packed qty per bundle
	 */
	private String wMaxFigure_BundleEnteringQty;
	
	//#CM697505
	/**
	 * Max Length_Packed Qty per Case
	 */
	private String wMaxFigure_EnteringQty;
	
	//#CM697506
	/**
	 * Max Length_Item Name
	 */
	private String wMaxFigure_ItemName;
	
	//#CM697507
	/**
	 * Max Length_Planned Shipping Qty (Total Bulk Qty)
	 */
	private String wMaxFigure_ShippingPlanQty;
	
	//#CM697508
	/**
	 * Max Length_TC/DC division
	 */
	private String wMaxFigure_TcDcFlag;

	//#CM697509
	/**
	 * Max Length_Supplier Code
	 */
	private String wMaxFigure_SupplierCode;
	
	//#CM697510
	/**
	 * Max Length_Supplier Name
	 */
	private String wMaxFigure_SupplierName;
	
	//#CM697511
	/**
	 * Max Length_Receiving Ticket No.
	 */
	private String wMaxFigure_InstockTicketNo;
	
	//#CM697512
	/**
	 * Max Length_Receiving Ticket Line No.
	 */
	private String wMaxFigure_InstockLineNo;
	
	//#CM697513
	/**
	 * Max Length_Shipping Qty (Total Bulk Qty)
	 */
	private String wMaxFigure_ShippingResultQty;
	
	//#CM697514
	/**
	 * Max Length_Shipping Result Date
	 */
	private String wMaxFigure_ShippingWorkDate;
	
	//#CM697515
	/**
	 * Max Length_Result division
	 */
	private String wMaxFigure_ResultFlag;
	
	//#CM697516
	/**
	 * Max Length_Expiry Date
	 */
	private String wMaxFigure_UseByDate;
	
	
	//#CM697517
	/**
	 * Position_Planned Shipping Date
	 */
	private String wPosition_ShippingPlanDate;

	//#CM697518
	/**
	 * Position_Order Date
	 */
	private String wPosition_ShippingOrderingDate;
	
	//#CM697519
	/**
	 * Position_Consignor Code
	 */
	private String wPosition_ConsignorCode;
	
	//#CM697520
	/**
	 * Position_Consignor Name
	 */
	private String wPosition_ConsignorName;
	
	//#CM697521
	/**
	 * Position_Customer Code
	 */
	private String wPosition_CustomerCode;
	
	//#CM697522
	/**
	 * Position_Customer Name
	 */
	private String wPosition_CustomerName;
	
	//#CM697523
	/**
	 * Position_Ticket No.
	 */
	private String wPosition_TicketNo;
	
	//#CM697524
	/**
	 * Position_Ticket Line No.
	 */
	private String wPosition_LineNo;
	
	//#CM697525
	/**
	 * Position_Item Code
	 */
	private String wPosition_ItemCode;
	
	//#CM697526
	/**
	 * Position_Bundle ITF
	 */
	private String wPosition_BundleItf;
	
	//#CM697527
	/**
	 * Position_Case ITF
	 */
	private String wPosition_Itf;
	
	//#CM697528
	/**
	 * Position_Packed qty per bundle
	 */
	private String wPosition_BundleEnteringQty;
	
	//#CM697529
	/**
	 * Position_Packed Qty per Case
	 */
	private String wPosition_EnteringQty;
	
	//#CM697530
	/**
	 * Position_Item Name
	 */
	private String wPosition_ItemName;
	
	//#CM697531
	/**
	 * Position_Planned Shipping Qty (Total Bulk Qty)
	 */
	private String wPosition_ShippingPlanQty;
	
	//#CM697532
	/**
	 * Position_TC/DC division
	 */
	private String wPosition_TcDcFlag;

	//#CM697533
	/**
	 * Position_Supplier Code
	 */
	private String wPosition_SupplierCode;
	
	//#CM697534
	/**
	 * Position_Supplier Name
	 */
	private String wPosition_SupplierName;
	
	//#CM697535
	/**
	 * Position_Receiving Ticket No.
	 */
	private String wPosition_InstockTicketNo;
	
	//#CM697536
	/**
	 * Position_Receiving Ticket Line No.
	 */
	private String wPosition_InstockLineNo;
	
	//#CM697537
	/**
	 * Position_Shipping Qty (Total Bulk Qty)
	 */
	private String wPosition_ShippingResultQty;
	
	//#CM697538
	/**
	 * Position_Shipping Result Date
	 */
	private String wPosition_ShippingWorkDate;
	
	//#CM697539
	/**
	 * Position_Result division
	 */
	private String wPosition_ResultFlag;
	
	//#CM697540
	/**
	 * Position_Expiry Date
	 */
	private String wPosition_UseByDate;
	
	//#CM697541
	/**
	 * Enabled_Planned Shipping Date
	 */
	private boolean wValid_ShippingPlanDate;

	//#CM697542
	/**
	 * Enabled_Order Date
	 */
	private boolean wValid_ShippingOrderingDate;
	
	//#CM697543
	/**
	 * Enabled_Consignor Code
	 */
	private boolean wValid_ConsignorCode;
	
	//#CM697544
	/**
	 * Enabled_Consignor Name
	 */
	private boolean wValid_ConsignorName;
	
	//#CM697545
	/**
	 * Enabled_Customer Code
	 */
	private boolean wValid_CustomerCode;
	
	//#CM697546
	/**
	 * Enabled_Customer Name
	 */
	private boolean wValid_CustomerName;
	
	//#CM697547
	/**
	 * Enabled_Ticket No.
	 */
	private boolean wValid_TicketNo;
	
	//#CM697548
	/**
	 * Enabled_Ticket Line No.
	 */
	private boolean wValid_LineNo;
	
	//#CM697549
	/**
	 * Enabled_Item Code
	 */
	private boolean wValid_ItemCode;
	
	//#CM697550
	/**
	 * Enabled_Bundle ITF
	 */
	private boolean wValid_BundleItf;
	
	//#CM697551
	/**
	 * Enabled_Case ITF
	 */
	private boolean wValid_Itf;
	
	//#CM697552
	/**
	 * Enabled_Packed qty per bundle
	 */
	private boolean wValid_BundleEnteringQty;
	
	//#CM697553
	/**
	 * Enabled_Packed Qty per Case
	 */
	private boolean wValid_EnteringQty;
	
	//#CM697554
	/**
	 * Enabled_Item Name
	 */
	private boolean wValid_ItemName;
	
	//#CM697555
	/**
	 * Enabled_Planned Shipping Qty (Total Bulk Qty)
	 */
	private boolean wValid_ShippingPlanQty;
	
	//#CM697556
	/**
	 * Enabled_TC/DC division
	 */
	private boolean wValid_TcDcFlag;

	//#CM697557
	/**
	 * Enabled_Supplier Code
	 */
	private boolean wValid_SupplierCode;
	
	//#CM697558
	/**
	 * Enabled_Supplier Name
	 */
	private boolean wValid_SupplierName;
	
	//#CM697559
	/**
	 * Enabled_Receiving Ticket No.
	 */
	private boolean wValid_InstockTicketNo;
	
	//#CM697560
	/**
	 * Enabled_Receiving Ticket Line No.
	 */
	private boolean wValid_InstockLineNo;
	
	//#CM697561
	/**
	 * Enabled_Shipping Qty (Total Bulk Qty)
	 */
	private boolean wValid_ShippingResultQty;
	
	//#CM697562
	/**
	 * Enabled_Shipping Result Date
	 */
	private boolean wValid_ShippingWorkDate;
	
	//#CM697563
	/**
	 * Enabled_Result division
	 */
	private boolean wValid_ResultFlag;
	
	//#CM697564
	/**
	 * Enabled_Expiry Date
	 */
	private boolean wValid_UseByDate;
	

	//#CM697565
	// Class method --------------------------------------------------
	//#CM697566
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:10 $");
	}

	//#CM697567
	// Constructors --------------------------------------------------
	//#CM697568
	/**
	 * Initialize this class.
	 */
	public DefineShippingDataParameter()
	{
	}
	
	//#CM697569
	// Public methods ------------------------------------------------

	//#CM697570
	/**
	 * Worker Code
	 * @return	Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM697571
	/**
	 * Password
	 * @return	Password
	 */
	public String getPassword()
	{
		return wPassword;
	}


	//#CM697572
	/**
	 * Length_Packed qty per bundle
	 * @return	Length_Packed qty per bundle
	 */
	public String getFigure_BundleEnteringQty()
	{
		return wFigure_BundleEnteringQty;
	}

	//#CM697573
	/**
	 * Length_Bundle ITF
	 * @return	Length_Bundle ITF
	 */
	public String getFigure_BundleItf()
	{
		return wFigure_BundleItf;
	}

	//#CM697574
	/**
	 * Length_Consignor Code
	 * @return	Length_Consignor Code
	 */
	public String getFigure_ConsignorCode()
	{
		return wFigure_ConsignorCode;
	}

	//#CM697575
	/**
	 * Length_Consignor Name
	 * @return	Length_Consignor Name
	 */
	public String getFigure_ConsignorName()
	{
		return wFigure_ConsignorName;
	}

	//#CM697576
	/**
	 * Length_Customer Code
	 * @return	Length_Customer Code
	 */
	public String getFigure_CustomerCode()
	{
		return wFigure_CustomerCode;
	}

	//#CM697577
	/**
	 * Length_Customer Name
	 * @return	Length_Customer Name
	 */
	public String getFigure_CustomerName()
	{
		return wFigure_CustomerName;
	}

	//#CM697578
	/**
	 * Length_Packed Qty per Case
	 * @return	Length_Packed Qty per Case
	 */
	public String getFigure_EnteringQty()
	{
		return wFigure_EnteringQty;
	}

	//#CM697579
	/**
	 * Length_Receiving Ticket Line No.
	 * @return	Length_Receiving Ticket Line No.
	 */
	public String getFigure_InstockLineNo()
	{
		return wFigure_InstockLineNo;
	}

	//#CM697580
	/**
	 * Length_Receiving Ticket No.
	 * @return	Length_Receiving Ticket No.
	 */
	public String getFigure_InstockTicketNo()
	{
		return wFigure_InstockTicketNo;
	}

	//#CM697581
	/**
	 * Length_Item Code
	 * @return	Length_Item Code
	 */
	public String getFigure_ItemCode()
	{
		return wFigure_ItemCode;
	}

	//#CM697582
	/**
	 * Length_Item Name
	 * @return	Length_Item Name
	 */
	public String getFigure_ItemName()
	{
		return wFigure_ItemName;
	}

	//#CM697583
	/**
	 * Length_Case ITF
	 * @return	Length_Case ITF
	 */
	public String getFigure_Itf()
	{
		return wFigure_Itf;
	}

	//#CM697584
	/**
	 * Length_Ticket Line No.
	 * @return	Length_Ticket Line No.
	 */
	public String getFigure_LineNo()
	{
		return wFigure_LineNo;
	}

	//#CM697585
	/**
	 * Length_Result division
	 * @return	Length_Result division
	 */
	public String getFigure_ResultFlag()
	{
		return wFigure_ResultFlag;
	}

	//#CM697586
	/**
	 * Length_Order Date
	 * @return	Length_Order Date
	 */
	public String getFigure_ShippingOrderingDate()
	{
		return wFigure_ShippingOrderingDate;
	}

	//#CM697587
	/**
	 * Length_Planned Shipping Date
	 * @return	Length_Planned Shipping Date
	 */
	public String getFigure_ShippingPlanDate()
	{
		return wFigure_ShippingPlanDate;
	}

	//#CM697588
	/**
	 * Length_Planned Shipping Qty (Total Bulk Qty)
	 * @return	Length_Planned Shipping Qty (Total Bulk Qty)
	 */
	public String getFigure_ShippingPlanQty()
	{
		return wFigure_ShippingPlanQty;
	}

	//#CM697589
	/**
	 * Length_Shipping Qty (Total Bulk Qty)
	 * @return	Length_Shipping Qty (Total Bulk Qty)
	 */
	public String getFigure_ShippingResultQty()
	{
		return wFigure_ShippingResultQty;
	}

	//#CM697590
	/**
	 * Length_Shipping Result Date
	 * @return	Length_Shipping Result Date
	 */
	public String getFigure_ShippingWorkDate()
	{
		return wFigure_ShippingWorkDate;
	}

	//#CM697591
	/**
	 * Length_Supplier Code
	 * @return	Length_Supplier Code
	 */
	public String getFigure_SupplierCode()
	{
		return wFigure_SupplierCode;
	}

	//#CM697592
	/**
	 * Length_Supplier Name
	 * @return	Length_Supplier Name
	 */
	public String getFigure_SupplierName()
	{
		return wFigure_SupplierName;
	}

	//#CM697593
	/**
	 * Length_TC/DC division
	 * @return	Length_TC/DC division
	 */
	public String getFigure_TcDcFlag()
	{
		return wFigure_TcDcFlag;
	}

	//#CM697594
	/**
	 * Length_Ticket No.
	 * @return	Length_Ticket No.
	 */
	public String getFigure_TicketNo()
	{
		return wFigure_TicketNo;
	}

	//#CM697595
	/**
	 * Length_Expiry Date
	 * @return	Length_Expiry Date
	 */
	public String getFigure_UseByDate()
	{
		return wFigure_UseByDate;
	}

	//#CM697596
	/**
	 * Max Length_Packed qty per bundle
	 * @return	Max Length_Packed qty per bundle
	 */
	public String getMaxFigure_BundleEnteringQty()
	{
		return wMaxFigure_BundleEnteringQty;
	}

	//#CM697597
	/**
	 * Max Length_Bundle ITF
	 * @return	Max Length_Bundle ITF
	 */
	public String getMaxFigure_BundleItf()
	{
		return wMaxFigure_BundleItf;
	}

	//#CM697598
	/**
	 * Max Length_Consignor Code
	 * @return	Max Length_Consignor Code
	 */
	public String getMaxFigure_ConsignorCode()
	{
		return wMaxFigure_ConsignorCode;
	}

	//#CM697599
	/**
	 * Max Length_Consignor Name
	 * @return	Max Length_Consignor Name
	 */
	public String getMaxFigure_ConsignorName()
	{
		return wMaxFigure_ConsignorName;
	}

	//#CM697600
	/**
	 * Max Length_Customer Code
	 * @return	Max Length_Customer Code
	 */
	public String getMaxFigure_CustomerCode()
	{
		return wMaxFigure_CustomerCode;
	}

	//#CM697601
	/**
	 * Max Length_Customer Name
	 * @return	Max Length_Customer Name
	 */
	public String getMaxFigure_CustomerName()
	{
		return wMaxFigure_CustomerName;
	}

	//#CM697602
	/**
	 * Max Length_Packed Qty per Case
	 * @return	Max Length_Packed Qty per Case
	 */
	public String getMaxFigure_EnteringQty()
	{
		return wMaxFigure_EnteringQty;
	}

	//#CM697603
	/**
	 * Max Length_Receiving Ticket Line No.
	 * @return	Max Length_Receiving Ticket Line No.
	 */
	public String getMaxFigure_InstockLineNo()
	{
		return wMaxFigure_InstockLineNo;
	}

	//#CM697604
	/**
	 * Max Length_Receiving Ticket No.
	 * @return	Max Length_Receiving Ticket No.
	 */
	public String getMaxFigure_InstockTicketNo()
	{
		return wMaxFigure_InstockTicketNo;
	}

	//#CM697605
	/**
	 * Max Length_Item Code
	 * @return	Max Length_Item Code
	 */
	public String getMaxFigure_ItemCode()
	{
		return wMaxFigure_ItemCode;
	}

	//#CM697606
	/**
	 * Max Length_Item Name
	 * @return	Max Length_Item Name
	 */
	public String getMaxFigure_ItemName()
	{
		return wMaxFigure_ItemName;
	}

	//#CM697607
	/**
	 * Max Length_Case ITF
	 * @return	Max Length_Case ITF
	 */
	public String getMaxFigure_Itf()
	{
		return wMaxFigure_Itf;
	}

	//#CM697608
	/**
	 * Max Length_Ticket Line No.
	 * @return	Max Length_Ticket Line No.
	 */
	public String getMaxFigure_LineNo()
	{
		return wMaxFigure_LineNo;
	}

	//#CM697609
	/**
	 * Max Length_Result division
	 * @return	Max Length_Result division
	 */
	public String getMaxFigure_ResultFlag()
	{
		return wMaxFigure_ResultFlag;
	}

	//#CM697610
	/**
	 * Max Length_Order Date
	 * @return	Max Length_Order Date
	 */
	public String getMaxFigure_ShippingOrderingDate()
	{
		return wMaxFigure_ShippingOrderingDate;
	}

	//#CM697611
	/**
	 * Max Length_Planned Shipping Date
	 * @return	Max Length_Planned Shipping Date
	 */
	public String getMaxFigure_ShippingPlanDate()
	{
		return wMaxFigure_ShippingPlanDate;
	}

	//#CM697612
	/**
	 * Max Length_Planned Shipping Qty (Total Bulk Qty)
	 * @return	Max Length_Planned Shipping Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_ShippingPlanQty()
	{
		return wMaxFigure_ShippingPlanQty;
	}

	//#CM697613
	/**
	 * Max Length_Shipping Qty (Total Bulk Qty)
	 * @return	Max Length_Shipping Qty (Total Bulk Qty)
	 */
	public String getMaxFigure_ShippingResultQty()
	{
		return wMaxFigure_ShippingResultQty;
	}

	//#CM697614
	/**
	 * Max Length_Shipping Result Date
	 * @return	Max Length_Shipping Result Date
	 */
	public String getMaxFigure_ShippingWorkDate()
	{
		return wMaxFigure_ShippingWorkDate;
	}

	//#CM697615
	/**
	 * Max Length_Supplier Code
	 * @return	Max Length_Supplier Code
	 */
	public String getMaxFigure_SupplierCode()
	{
		return wMaxFigure_SupplierCode;
	}

	//#CM697616
	/**
	 * Max Length_Supplier Name
	 * @return	Max Length_Supplier Name
	 */
	public String getMaxFigure_SupplierName()
	{
		return wMaxFigure_SupplierName;
	}

	//#CM697617
	/**
	 * Max Length_TC/DC division
	 * @return	Max Length_TC/DC division
	 */
	public String getMaxFigure_TcDcFlag()
	{
		return wMaxFigure_TcDcFlag;
	}

	//#CM697618
	/**
	 * Max Length_Ticket No.
	 * @return	Max Length_Ticket No.
	 */
	public String getMaxFigure_TicketNo()
	{
		return wMaxFigure_TicketNo;
	}

	//#CM697619
	/**
	 * Max Length_Expiry Date
	 * @return	Max Length_Expiry Date
	 */
	public String getMaxFigure_UseByDate()
	{
		return wMaxFigure_UseByDate;
	}

	//#CM697620
	/**
	 * Position_Packed qty per bundle
	 * @return	Position_Packed qty per bundle
	 */
	public String getPosition_BundleEnteringQty()
	{
		return wPosition_BundleEnteringQty;
	}

	//#CM697621
	/**
	 * Position_Bundle ITF
	 * @return	Position_Bundle ITF
	 */
	public String getPosition_BundleItf()
	{
		return wPosition_BundleItf;
	}

	//#CM697622
	/**
	 * Position_Consignor Code
	 * @return	Position_Consignor Code
	 */
	public String getPosition_ConsignorCode()
	{
		return wPosition_ConsignorCode;
	}

	//#CM697623
	/**
	 * Position_Consignor Name
	 * @return	Position_Consignor Name
	 */
	public String getPosition_ConsignorName()
	{
		return wPosition_ConsignorName;
	}

	//#CM697624
	/**
	 * Position_Customer Code
	 * @return	Position_Customer Code
	 */
	public String getPosition_CustomerCode()
	{
		return wPosition_CustomerCode;
	}

	//#CM697625
	/**
	 * Position_Customer Name
	 * @return	Position_Customer Name
	 */
	public String getPosition_CustomerName()
	{
		return wPosition_CustomerName;
	}

	//#CM697626
	/**
	 * Position_Packed Qty per Case
	 * @return	Position_Packed Qty per Case
	 */
	public String getPosition_EnteringQty()
	{
		return wPosition_EnteringQty;
	}

	//#CM697627
	/**
	 * Position_Receiving Ticket Line No.
	 * @return	Position_Receiving Ticket Line No.
	 */
	public String getPosition_InstockLineNo()
	{
		return wPosition_InstockLineNo;
	}

	//#CM697628
	/**
	 * Position_Receiving Ticket No.
	 * @return	Position_Receiving Ticket No.
	 */
	public String getPosition_InstockTicketNo()
	{
		return wPosition_InstockTicketNo;
	}

	//#CM697629
	/**
	 * Position_Item Code
	 * @return	Position_Item Code
	 */
	public String getPosition_ItemCode()
	{
		return wPosition_ItemCode;
	}

	//#CM697630
	/**
	 * Position_Item Name
	 * @return	Position_Item Name
	 */
	public String getPosition_ItemName()
	{
		return wPosition_ItemName;
	}

	//#CM697631
	/**
	 * Position_Case ITF
	 * @return	Position_Case ITF
	 */
	public String getPosition_Itf()
	{
		return wPosition_Itf;
	}

	//#CM697632
	/**
	 * Position_Ticket Line No.
	 * @return	Position_Ticket Line No.
	 */
	public String getPosition_LineNo()
	{
		return wPosition_LineNo;
	}

	//#CM697633
	/**
	 * Position_Result division
	 * @return	Position_Result division
	 */
	public String getPosition_ResultFlag()
	{
		return wPosition_ResultFlag;
	}

	//#CM697634
	/**
	 * Position_Order Date
	 * @return	Position_Order Date
	 */
	public String getPosition_ShippingOrderingDate()
	{
		return wPosition_ShippingOrderingDate;
	}

	//#CM697635
	/**
	 * Position_Planned Shipping Date
	 * @return	Position_Planned Shipping Date
	 */
	public String getPosition_ShippingPlanDate()
	{
		return wPosition_ShippingPlanDate;
	}

	//#CM697636
	/**
	 * Position_Planned Shipping Qty (Total Bulk Qty)
	 * @return	Position_Planned Shipping Qty (Total Bulk Qty)
	 */
	public String getPosition_ShippingPlanQty()
	{
		return wPosition_ShippingPlanQty;
	}

	//#CM697637
	/**
	 * Position_Shipping Qty (Total Bulk Qty)
	 * @return	Position_Shipping Qty (Total Bulk Qty)
	 */
	public String getPosition_ShippingResultQty()
	{
		return wPosition_ShippingResultQty;
	}

	//#CM697638
	/**
	 * Position_Shipping Result Date
	 * @return	Position_Shipping Result Date
	 */
	public String getPosition_ShippingWorkDate()
	{
		return wPosition_ShippingWorkDate;
	}

	//#CM697639
	/**
	 * Position_Supplier Code
	 * @return	Position_Supplier Code
	 */
	public String getPosition_SupplierCode()
	{
		return wPosition_SupplierCode;
	}

	//#CM697640
	/**
	 * Position_Supplier Name
	 * @return	Position_Supplier Name
	 */
	public String getPosition_SupplierName()
	{
		return wPosition_SupplierName;
	}

	//#CM697641
	/**
	 * Position_TC/DC division
	 * @return	Position_TC/DC division
	 */
	public String getPosition_TcDcFlag()
	{
		return wPosition_TcDcFlag;
	}

	//#CM697642
	/**
	 * Position_Ticket No.
	 * @return	Position_Ticket No.
	 */
	public String getPosition_TicketNo()
	{
		return wPosition_TicketNo;
	}

	//#CM697643
	/**
	 * Position_Expiry Date
	 * @return	Position_Expiry Date
	 */
	public String getPosition_UseByDate()
	{
		return wPosition_UseByDate;
	}

	//#CM697644
	/**
	 * Enabled_Packed qty per bundle
	 * @return	Enabled_Packed qty per bundle
	 */
	public boolean getValid_BundleEnteringQty()
	{
		return wValid_BundleEnteringQty;
	}

	//#CM697645
	/**
	 * Enabled_Bundle ITF
	 * @return	Enabled_Bundle ITF
	 */
	public boolean getValid_BundleItf()
	{
		return wValid_BundleItf;
	}

	//#CM697646
	/**
	 * Enabled_Consignor Code
	 * @return	Enabled_Consignor Code
	 */
	public boolean getValid_ConsignorCode()
	{
		return wValid_ConsignorCode;
	}

	//#CM697647
	/**
	 * Enabled_Consignor Name
	 * @return	Enabled_Consignor Name
	 */
	public boolean getValid_ConsignorName()
	{
		return wValid_ConsignorName;
	}

	//#CM697648
	/**
	 * Enabled_Customer Code
	 * @return	Enabled_Customer Code
	 */
	public boolean getValid_CustomerCode()
	{
		return wValid_CustomerCode;
	}

	//#CM697649
	/**
	 * Enabled_Customer Name
	 * @return	Enabled_Customer Name
	 */
	public boolean getValid_CustomerName()
	{
		return wValid_CustomerName;
	}

	//#CM697650
	/**
	 * Enabled_Packed Qty per Case
	 * @return	Enabled_Packed Qty per Case
	 */
	public boolean getValid_EnteringQty()
	{
		return wValid_EnteringQty;
	}

	//#CM697651
	/**
	 * Enabled_Receiving Ticket Line No.
	 * @return	Enabled_Receiving Ticket Line No.
	 */
	public boolean getValid_InstockLineNo()
	{
		return wValid_InstockLineNo;
	}

	//#CM697652
	/**
	 * Enabled_Receiving Ticket No.
	 * @return	Enabled_Receiving Ticket No.
	 */
	public boolean getValid_InstockTicketNo()
	{
		return wValid_InstockTicketNo;
	}

	//#CM697653
	/**
	 * Enabled_Item Code
	 * @return	Enabled_Item Code
	 */
	public boolean getValid_ItemCode()
	{
		return wValid_ItemCode;
	}

	//#CM697654
	/**
	 * Enabled_Item Name
	 * @return	Enabled_Item Name
	 */
	public boolean getValid_ItemName()
	{
		return wValid_ItemName;
	}

	//#CM697655
	/**
	 * Enabled_Case ITF
	 * @return	Enabled_Case ITF
	 */
	public boolean getValid_Itf()
	{
		return wValid_Itf;
	}

	//#CM697656
	/**
	 * Enabled_Ticket Line No.
	 * @return	Enabled_Ticket Line No.
	 */
	public boolean getValid_LineNo()
	{
		return wValid_LineNo;
	}

	//#CM697657
	/**
	 * Enabled_Result division
	 * @return	Enabled_Result division
	 */
	public boolean getValid_ResultFlag()
	{
		return wValid_ResultFlag;
	}

	//#CM697658
	/**
	 * Enabled_Order Date
	 * @return	Enabled_Order Date
	 */
	public boolean getValid_ShippingOrderingDate()
	{
		return wValid_ShippingOrderingDate;
	}

	//#CM697659
	/**
	 * Enabled_Planned Shipping Date
	 * @return	Enabled_Planned Shipping Date
	 */
	public boolean getValid_ShippingPlanDate()
	{
		return wValid_ShippingPlanDate;
	}

	//#CM697660
	/**
	 * Enabled_Planned Shipping Qty (Total Bulk Qty)
	 * @return	Enabled_Planned Shipping Qty (Total Bulk Qty)
	 */
	public boolean getValid_ShippingPlanQty()
	{
		return wValid_ShippingPlanQty;
	}

	//#CM697661
	/**
	 * Enabled_Shipping Qty (Total Bulk Qty)
	 * @return	Enabled_Shipping Qty (Total Bulk Qty)
	 */
	public boolean getValid_ShippingResultQty()
	{
		return wValid_ShippingResultQty;
	}

	//#CM697662
	/**
	 * Enabled_Shipping Result Date
	 * @return	Enabled_Shipping Result Date
	 */
	public boolean getValid_ShippingWorkDate()
	{
		return wValid_ShippingWorkDate;
	}

	//#CM697663
	/**
	 * Enabled_Supplier Code
	 * @return	Enabled_Supplier Code
	 */
	public boolean getValid_SupplierCode()
	{
		return wValid_SupplierCode;
	}

	//#CM697664
	/**
	 * Enabled_Supplier Name
	 * @return	Enabled_Supplier Name
	 */
	public boolean getValid_SupplierName()
	{
		return wValid_SupplierName;
	}

	//#CM697665
	/**
	 * Enabled_TC/DC division
	 * @return	Enabled_TC/DC division
	 */
	public boolean getValid_TcDcFlag()
	{
		return wValid_TcDcFlag;
	}

	//#CM697666
	/**
	 * Enabled_Ticket No.
	 * @return	Enabled_Ticket No.
	 */
	public boolean getValid_TicketNo()
	{
		return wValid_TicketNo;
	}

	//#CM697667
	/**
	 * Enabled_Expiry Date
	 * @return	Enabled_Expiry Date
	 */
	public boolean getValid_UseByDate()
	{
		return wValid_UseByDate;
	}

	//#CM697668
	/**
	 * Length_Packed qty per bundle
	 * @param arg	Length_Packed qty per bundle to be set
	 */
	public void setFigure_BundleEnteringQty(String arg)
	{
		wFigure_BundleEnteringQty = arg;
	}

	//#CM697669
	/**
	 * Length_Bundle ITF
	 * @param arg	Length_Bundle ITF to be set
	 */
	public void setFigure_BundleItf(String arg)
	{
		wFigure_BundleItf = arg;
	}

	//#CM697670
	/**
	 * Length_Consignor Code
	 * @param arg	Length_Consignor Code to be set
	 */
	public void setFigure_ConsignorCode(String arg)
	{
		wFigure_ConsignorCode = arg;
	}

	//#CM697671
	/**
	 * Length_Consignor Name
	 * @param arg	Length_Consignor Name to be set
	 */
	public void setFigure_ConsignorName(String arg)
	{
		wFigure_ConsignorName = arg;
	}

	//#CM697672
	/**
	 * Length_Customer Code
	 * @param arg	Length_Customer Code to be set
	 */
	public void setFigure_CustomerCode(String arg)
	{
		wFigure_CustomerCode = arg;
	}

	//#CM697673
	/**
	 * Length_Customer Name
	 * @param arg	Length_Customer Name to be set
	 */
	public void setFigure_CustomerName(String arg)
	{
		wFigure_CustomerName = arg;
	}

	//#CM697674
	/**
	 * Length_Packed Qty per Case
	 * @param arg	Length_Packed Qty per Case to be set
	 */
	public void setFigure_EnteringQty(String arg)
	{
		wFigure_EnteringQty = arg;
	}

	//#CM697675
	/**
	 * Length_Receiving Ticket Line No.
	 * @param arg	Length_Receiving Ticket Line No. to be set
	 */
	public void setFigure_InstockLineNo(String arg)
	{
		wFigure_InstockLineNo = arg;
	}

	//#CM697676
	/**
	 * Length_Receiving Ticket No.
	 * @param arg	Length_Receiving Ticket No. to be set
	 */
	public void setFigure_InstockTicketNo(String arg)
	{
		wFigure_InstockTicketNo = arg;
	}

	//#CM697677
	/**
	 * Length_Item Code
	 * @param arg	Length_Item Code to be set
	 */
	public void setFigure_ItemCode(String arg)
	{
		wFigure_ItemCode = arg;
	}

	//#CM697678
	/**
	 * Length_Item Name
	 * @param arg	Length_Item Name to be set
	 */
	public void setFigure_ItemName(String arg)
	{
		wFigure_ItemName = arg;
	}

	//#CM697679
	/**
	 * Length_Case ITF
	 * @param arg	Length_Case ITF to be set
	 */
	public void setFigure_Itf(String arg)
	{
		wFigure_Itf = arg;
	}

	//#CM697680
	/**
	 * Length_Ticket Line No.
	 * @param arg	Length_Ticket Line No. to be set
	 */
	public void setFigure_LineNo(String arg)
	{
		wFigure_LineNo = arg;
	}

	//#CM697681
	/**
	 * Length_Result division
	 * @param arg	Length_Result division to be set
	 */
	public void setFigure_ResultFlag(String arg)
	{
		wFigure_ResultFlag = arg;
	}

	//#CM697682
	/**
	 * Length_Order Date
	 * @param arg	Length_Order Date to be set
	 */
	public void setFigure_ShippingOrderingDate(String arg)
	{
		wFigure_ShippingOrderingDate = arg;
	}

	//#CM697683
	/**
	 * Length_Planned Shipping Date
	 * @param arg	Length_Planned Shipping Date to be set
	 */
	public void setFigure_ShippingPlanDate(String arg)
	{
		wFigure_ShippingPlanDate = arg;
	}

	//#CM697684
	/**
	 * Length_Planned Shipping Qty (Total Bulk Qty)
	 * @param arg	Length_Planned Shipping Qty (Total Bulk Qty) to be set
	 */
	public void setFigure_ShippingPlanQty(String arg)
	{
		wFigure_ShippingPlanQty = arg;
	}

	//#CM697685
	/**
	 * Length_Shipping Qty (Total Bulk Qty)
	 * @param arg	Length_Shipping Qty (Total Bulk Qty) to be set
	 */
	public void setFigure_ShippingResultQty(String arg)
	{
		wFigure_ShippingResultQty = arg;
	}

	//#CM697686
	/**
	 * Length_Shipping Result Date
	 * @param arg	Length_Shipping Result Date to be set
	 */
	public void setFigure_ShippingWorkDate(String arg)
	{
		wFigure_ShippingWorkDate = arg;
	}

	//#CM697687
	/**
	 * Length_Supplier Code
	 * @param arg	Length_Supplier Code to be set
	 */
	public void setFigure_SupplierCode(String arg)
	{
		wFigure_SupplierCode = arg;
	}

	//#CM697688
	/**
	 * Length_Supplier Name
	 * @param arg	Length_Supplier Name to be set
	 */
	public void setFigure_SupplierName(String arg)
	{
		wFigure_SupplierName = arg;
	}

	//#CM697689
	/**
	 * Length_TC/DC division
	 * @param arg	Length_TC/DC division to be set
	 */
	public void setFigure_TcDcFlag(String arg)
	{
		wFigure_TcDcFlag = arg;
	}

	//#CM697690
	/**
	 * Length_Ticket No.
	 * @param arg	Length_Ticket No. to be set
	 */
	public void setFigure_TicketNo(String arg)
	{
		wFigure_TicketNo = arg;
	}

	//#CM697691
	/**
	 * Length_Expiry Date
	 * @param arg	Length_Expiry Date to be set
	 */
	public void setFigure_UseByDate(String arg)
	{
		wFigure_UseByDate = arg;
	}

	//#CM697692
	/**
	 * Max Length_Packed qty per bundle
	 * @param arg	Max Length_Packed qty per bundle to be set
	 */
	public void setMaxFigure_BundleEnteringQty(String arg)
	{
		wMaxFigure_BundleEnteringQty = arg;
	}

	//#CM697693
	/**
	 * Max Length_Bundle ITF
	 * @param arg	Max Length_Bundle ITF to be set
	 */
	public void setMaxFigure_BundleItf(String arg)
	{
		wMaxFigure_BundleItf = arg;
	}

	//#CM697694
	/**
	 * Max Length_Consignor Code
	 * @param arg	Max Length_Consignor Code to be set
	 */
	public void setMaxFigure_ConsignorCode(String arg)
	{
		wMaxFigure_ConsignorCode = arg;
	}

	//#CM697695
	/**
	 * Max Length_Consignor Name
	 * @param arg	Max Length_Consignor Name to be set
	 */
	public void setMaxFigure_ConsignorName(String arg)
	{
		wMaxFigure_ConsignorName = arg;
	}

	//#CM697696
	/**
	 * Max Length_Customer Code
	 * @param arg	Max Length_Customer Code to be set
	 */
	public void setMaxFigure_CustomerCode(String arg)
	{
		wMaxFigure_CustomerCode = arg;
	}

	//#CM697697
	/**
	 * Max Length_Customer Name
	 * @param arg	Max Length_Customer Name to be set
	 */
	public void setMaxFigure_CustomerName(String arg)
	{
		wMaxFigure_CustomerName = arg;
	}

	//#CM697698
	/**
	 * Max Length_Packed Qty per Case
	 * @param arg	Max Length_Packed Qty per Case to be set
	 */
	public void setMaxFigure_EnteringQty(String arg)
	{
		wMaxFigure_EnteringQty = arg;
	}

	//#CM697699
	/**
	 * Max Length_Receiving Ticket Line No.
	 * @param arg	Max Length_Receiving Ticket Line No. to be set
	 */
	public void setMaxFigure_InstockLineNo(String arg)
	{
		wMaxFigure_InstockLineNo = arg;
	}

	//#CM697700
	/**
	 * Max Length_Receiving Ticket No.
	 * @param arg	Max Length_Receiving Ticket No. to be set
	 */
	public void setMaxFigure_InstockTicketNo(String arg)
	{
		wMaxFigure_InstockTicketNo = arg;
	}

	//#CM697701
	/**
	 * Max Length_Item Code
	 * @param arg	Max Length_Item Code to be set
	 */
	public void setMaxFigure_ItemCode(String arg)
	{
		wMaxFigure_ItemCode = arg;
	}

	//#CM697702
	/**
	 * Max Length_Item Name
	 * @param arg	Max Length_Item Name to be set
	 */
	public void setMaxFigure_ItemName(String arg)
	{
		wMaxFigure_ItemName = arg;
	}

	//#CM697703
	/**
	 * Max Length_Case ITF
	 * @param arg	Max Length_Case ITF to be set
	 */
	public void setMaxFigure_Itf(String arg)
	{
		wMaxFigure_Itf = arg;
	}

	//#CM697704
	/**
	 * Max Length_Ticket Line No.
	 * @param arg	Max Length_Ticket Line No. to be set
	 */
	public void setMaxFigure_LineNo(String arg)
	{
		wMaxFigure_LineNo = arg;
	}

	//#CM697705
	/**
	 * Max Length_Result division
	 * @param arg	Max Length_Result division to be set
	 */
	public void setMaxFigure_ResultFlag(String arg)
	{
		wMaxFigure_ResultFlag = arg;
	}

	//#CM697706
	/**
	 * Max Length_Order Date
	 * @param arg	Max Length_Order Date to be set
	 */
	public void setMaxFigure_ShippingOrderingDate(String arg)
	{
		wMaxFigure_ShippingOrderingDate = arg;
	}

	//#CM697707
	/**
	 * Max Length_Planned Shipping Date
	 * @param arg	Max Length_Planned Shipping Date to be set
	 */
	public void setMaxFigure_ShippingPlanDate(String arg)
	{
		wMaxFigure_ShippingPlanDate = arg;
	}

	//#CM697708
	/**
	 * Max Length_Planned Shipping Qty (Total Bulk Qty)
	 * @param arg	Max Length_Planned Shipping Qty (Total Bulk Qty) to be set
	 */
	public void setMaxFigure_ShippingPlanQty(String arg)
	{
		wMaxFigure_ShippingPlanQty = arg;
	}

	//#CM697709
	/**
	 * Max Length_Shipping Qty (Total Bulk Qty)
	 * @param arg	Max Length_Shipping Qty (Total Bulk Qty) to be set
	 */
	public void setMaxFigure_ShippingResultQty(String arg)
	{
		wMaxFigure_ShippingResultQty = arg;
	}

	//#CM697710
	/**
	 * Max Length_Shipping Result Date
	 * @param arg	Max Length_Shipping Result Date to be set
	 */
	public void setMaxFigure_ShippingWorkDate(String arg)
	{
		wMaxFigure_ShippingWorkDate = arg;
	}

	//#CM697711
	/**
	 * Max Length_Supplier Code
	 * @param arg	Max Length_Supplier Code to be set
	 */
	public void setMaxFigure_SupplierCode(String arg)
	{
		wMaxFigure_SupplierCode = arg;
	}

	//#CM697712
	/**
	 * Max Length_Supplier Name
	 * @param arg	Max Length_Supplier Name to be set
	 */
	public void setMaxFigure_SupplierName(String arg)
	{
		wMaxFigure_SupplierName = arg;
	}

	//#CM697713
	/**
	 * Max Length_TC/DC division
	 * @param arg	Max Length_TC/DC division to be set
	 */
	public void setMaxFigure_TcDcFlag(String arg)
	{
		wMaxFigure_TcDcFlag = arg;
	}

	//#CM697714
	/**
	 * Max Length_Ticket No.
	 * @param arg	Max Length_Ticket No. to be set
	 */
	public void setMaxFigure_TicketNo(String arg)
	{
		wMaxFigure_TicketNo = arg;
	}

	//#CM697715
	/**
	 * Max Length_Expiry Date
	 * @param arg	Max Length_Expiry Date to be set
	 */
	public void setMaxFigure_UseByDate(String arg)
	{
		wMaxFigure_UseByDate = arg;
	}

	//#CM697716
	/**
	 * Position_Packed qty per bundle
	 * @param arg	Position_Packed qty per bundle to be set
	 */
	public void setPosition_BundleEnteringQty(String arg)
	{
		wPosition_BundleEnteringQty = arg;
	}

	//#CM697717
	/**
	 * Position_Bundle ITF
	 * @param arg	Position_Bundle ITF to be set
	 */
	public void setPosition_BundleItf(String arg)
	{
		wPosition_BundleItf = arg;
	}

	//#CM697718
	/**
	 * Position_Consignor Code
	 * @param arg	Position_Consignor Code to be set
	 */
	public void setPosition_ConsignorCode(String arg)
	{
		wPosition_ConsignorCode = arg;
	}

	//#CM697719
	/**
	 * Position_Consignor Name
	 * @param arg	Position_Consignor Name to be set
	 */
	public void setPosition_ConsignorName(String arg)
	{
		wPosition_ConsignorName = arg;
	}

	//#CM697720
	/**
	 * Position_Customer Code
	 * @param arg	Position_Customer Code to be set
	 */
	public void setPosition_CustomerCode(String arg)
	{
		wPosition_CustomerCode = arg;
	}

	//#CM697721
	/**
	 * Position_Customer Name
	 * @param arg	Position_Customer Name to be set
	 */
	public void setPosition_CustomerName(String arg)
	{
		wPosition_CustomerName = arg;
	}

	//#CM697722
	/**
	 * Position_Packed Qty per Case
	 * @param arg	Position_Packed Qty per Case to be set
	 */
	public void setPosition_EnteringQty(String arg)
	{
		wPosition_EnteringQty = arg;
	}

	//#CM697723
	/**
	 * Position_Receiving Ticket Line No.
	 * @param arg	Position_Receiving Ticket Line No. to be set
	 */
	public void setPosition_InstockLineNo(String arg)
	{
		wPosition_InstockLineNo = arg;
	}

	//#CM697724
	/**
	 * Position_Receiving Ticket No.
	 * @param arg	Position_Receiving Ticket No. to be set
	 */
	public void setPosition_InstockTicketNo(String arg)
	{
		wPosition_InstockTicketNo = arg;
	}

	//#CM697725
	/**
	 * Position_Item Code
	 * @param arg	Position_Item Code to be set
	 */
	public void setPosition_ItemCode(String arg)
	{
		wPosition_ItemCode = arg;
	}

	//#CM697726
	/**
	 * Position_Item Name
	 * @param arg	Position_Item Name to be set
	 */
	public void setPosition_ItemName(String arg)
	{
		wPosition_ItemName = arg;
	}

	//#CM697727
	/**
	 * Position_Case ITF
	 * @param arg	Position_Case ITF to be set
	 */
	public void setPosition_Itf(String arg)
	{
		wPosition_Itf = arg;
	}

	//#CM697728
	/**
	 * Position_Ticket Line No.
	 * @param arg	Position_Ticket Line No. to be set
	 */
	public void setPosition_LineNo(String arg)
	{
		wPosition_LineNo = arg;
	}

	//#CM697729
	/**
	 * Position_Result division
	 * @param arg	Position_Result division to be set
	 */
	public void setPosition_ResultFlag(String arg)
	{
		wPosition_ResultFlag = arg;
	}

	//#CM697730
	/**
	 * Position_Order Date
	 * @param arg	Position_Order Date to be set
	 */
	public void setPosition_ShippingOrderingDate(String arg)
	{
		wPosition_ShippingOrderingDate = arg;
	}

	//#CM697731
	/**
	 * Position_Planned Shipping Date
	 * @param arg	Position_Planned Shipping Date to be set
	 */
	public void setPosition_ShippingPlanDate(String arg)
	{
		wPosition_ShippingPlanDate = arg;
	}

	//#CM697732
	/**
	 * Position_Planned Shipping Qty (Total Bulk Qty)
	 * @param arg	Position_Planned Shipping Qty (Total Bulk Qty) to be set
	 */
	public void setPosition_ShippingPlanQty(String arg)
	{
		wPosition_ShippingPlanQty = arg;
	}

	//#CM697733
	/**
	 * Position_Shipping Qty (Total Bulk Qty)
	 * @param arg	Position_Shipping Qty (Total Bulk Qty) to be set
	 */
	public void setPosition_ShippingResultQty(String arg)
	{
		wPosition_ShippingResultQty = arg;
	}

	//#CM697734
	/**
	 * Position_Shipping Result Date
	 * @param arg	Position_Shipping Result Date to be set
	 */
	public void setPosition_ShippingWorkDate(String arg)
	{
		wPosition_ShippingWorkDate = arg;
	}

	//#CM697735
	/**
	 * Position_Supplier Code
	 * @param arg	Position_Supplier Code to be set
	 */
	public void setPosition_SupplierCode(String arg)
	{
		wPosition_SupplierCode = arg;
	}

	//#CM697736
	/**
	 * Position_Supplier Name
	 * @param arg	Position_Supplier Name to be set
	 */
	public void setPosition_SupplierName(String arg)
	{
		wPosition_SupplierName = arg;
	}

	//#CM697737
	/**
	 * Position_TC/DC division
	 * @param arg	Position_TC/DC division to be set
	 */
	public void setPosition_TcDcFlag(String arg)
	{
		wPosition_TcDcFlag = arg;
	}

	//#CM697738
	/**
	 * Position_Ticket No.
	 * @param arg	Position_Ticket No. to be set
	 */
	public void setPosition_TicketNo(String arg)
	{
		wPosition_TicketNo = arg;
	}

	//#CM697739
	/**
	 * Position_Expiry Date
	 * @param arg	Position_Expiry Date to be set
	 */
	public void setPosition_UseByDate(String arg)
	{
		wPosition_UseByDate = arg;
	}

	//#CM697740
	/**
	 * Enabled_Packed qty per bundle
	 * @param arg	Enabled_Packed qty per bundle to be set
	 */
	public void setValid_BundleEnteringQty(boolean arg)
	{
		wValid_BundleEnteringQty = arg;
	}

	//#CM697741
	/**
	 * Enabled_Bundle ITF
	 * @param arg	Enabled_Bundle ITF to be set
	 */
	public void setValid_BundleItf(boolean arg)
	{
		wValid_BundleItf = arg;
	}

	//#CM697742
	/**
	 * Enabled_Consignor Code
	 * @param arg	Enabled_Consignor Code to be set
	 */
	public void setValid_ConsignorCode(boolean arg)
	{
		wValid_ConsignorCode = arg;
	}

	//#CM697743
	/**
	 * Enabled_Consignor Name
	 * @param arg	Enabled_Consignor Name to be set
	 */
	public void setValid_ConsignorName(boolean arg)
	{
		wValid_ConsignorName = arg;
	}

	//#CM697744
	/**
	 * Enabled_Customer Code
	 * @param arg	Enabled_Customer Code to be set
	 */
	public void setValid_CustomerCode(boolean arg)
	{
		wValid_CustomerCode = arg;
	}

	//#CM697745
	/**
	 * Enabled_Customer Name
	 * @param arg	Enabled_Customer Name to be set
	 */
	public void setValid_CustomerName(boolean arg)
	{
		wValid_CustomerName = arg;
	}

	//#CM697746
	/**
	 * Enabled_Packed Qty per Case
	 * @param arg	Enabled_Packed Qty per Case to be set
	 */
	public void setValid_EnteringQty(boolean arg)
	{
		wValid_EnteringQty = arg;
	}

	//#CM697747
	/**
	 * Enabled_Receiving Ticket Line No.
	 * @param arg	Enabled_Receiving Ticket Line No. to be set
	 */
	public void setValid_InstockLineNo(boolean arg)
	{
		wValid_InstockLineNo = arg;
	}

	//#CM697748
	/**
	 * Enabled_Receiving Ticket No.
	 * @param arg	Enabled_Receiving Ticket No. to be set
	 */
	public void setValid_InstockTicketNo(boolean arg)
	{
		wValid_InstockTicketNo = arg;
	}

	//#CM697749
	/**
	 * Enabled_Item Code
	 * @param arg	Enabled_Item Code to be set
	 */
	public void setValid_ItemCode(boolean arg)
	{
		wValid_ItemCode = arg;
	}

	//#CM697750
	/**
	 * Enabled_Item Name
	 * @param arg	Enabled_Item Name to be set
	 */
	public void setValid_ItemName(boolean arg)
	{
		wValid_ItemName = arg;
	}

	//#CM697751
	/**
	 * Enabled_Case ITF
	 * @param arg	Enabled_Case ITF to be set
	 */
	public void setValid_Itf(boolean arg)
	{
		wValid_Itf = arg;
	}

	//#CM697752
	/**
	 * Enabled_Ticket Line No.
	 * @param arg	Enabled_Ticket Line No. to be set
	 */
	public void setValid_LineNo(boolean arg)
	{
		wValid_LineNo = arg;
	}

	//#CM697753
	/**
	 * Enabled_Result division
	 * @param arg	Enabled_Result division to be set
	 */
	public void setValid_ResultFlag(boolean arg)
	{
		wValid_ResultFlag = arg;
	}

	//#CM697754
	/**
	 * Enabled_Order Date
	 * @param arg	Enabled_Order Date to be set
	 */
	public void setValid_ShippingOrderingDate(boolean arg)
	{
		wValid_ShippingOrderingDate = arg;
	}

	//#CM697755
	/**
	 * Enabled_Planned Shipping Date
	 * @param arg	Enabled_Planned Shipping Date to be set
	 */
	public void setValid_ShippingPlanDate(boolean arg)
	{
		wValid_ShippingPlanDate = arg;
	}

	//#CM697756
	/**
	 * Enabled_Planned Shipping Qty (Total Bulk Qty)
	 * @param arg	Enabled_Planned Shipping Qty (Total Bulk Qty) to be set
	 */
	public void setValid_ShippingPlanQty(boolean arg)
	{
		wValid_ShippingPlanQty = arg;
	}

	//#CM697757
	/**
	 * Enabled_Shipping Qty (Total Bulk Qty)
	 * @param arg	Enabled_Shipping Qty (Total Bulk Qty) to be set
	 */
	public void setValid_ShippingResultQty(boolean arg)
	{
		wValid_ShippingResultQty = arg;
	}

	//#CM697758
	/**
	 * Enabled_Shipping Result Date
	 * @param arg	Enabled_Shipping Result Date to be set
	 */
	public void setValid_ShippingWorkDate(boolean arg)
	{
		wValid_ShippingWorkDate = arg;
	}

	//#CM697759
	/**
	 * Enabled_Supplier Code
	 * @param arg	Enabled_Supplier Code to be set
	 */
	public void setValid_SupplierCode(boolean arg)
	{
		wValid_SupplierCode = arg;
	}

	//#CM697760
	/**
	 * Enabled_Supplier Name
	 * @param arg	Enabled_Supplier Name to be set
	 */
	public void setValid_SupplierName(boolean arg)
	{
		wValid_SupplierName = arg;
	}

	//#CM697761
	/**
	 * Enabled_TC/DC division
	 * @param arg	Enabled_TC/DC division to be set
	 */
	public void setValid_TcDcFlag(boolean arg)
	{
		wValid_TcDcFlag = arg;
	}

	//#CM697762
	/**
	 * Enabled_Ticket No.
	 * @param arg	Enabled_Ticket No. to be set
	 */
	public void setValid_TicketNo(boolean arg)
	{
		wValid_TicketNo = arg;
	}

	//#CM697763
	/**
	 * Enabled_Expiry Date
	 * @param arg	Enabled_Expiry Date to be set
	 */
	public void setValid_UseByDate(boolean arg)
	{
		wValid_UseByDate = arg;
	}

}
//#CM697764
//end of class
