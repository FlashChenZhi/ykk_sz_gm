// $Id: RFTId5013.java,v 1.2 2006/11/14 06:09:19 suresh Exp $
package jp.co.daifuku.wms.base.rft;


//#CM702377
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702378
/**
 * Process Item information response ID=5013 telegram. 
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id5013</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td></tr>
 * <tr><td>SEQ No.</td>			<td>4 byte</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No.</td>	<td>3 byte</td></tr>
 * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
 * <tr><td>Consignor Code</td>		<td>16 byte</td></tr>
 * <tr><td>Consignor Name</td>			<td>40 byte</td></tr>
 * <tr><td>Area No.</td>		<td>3 byte</td></tr>
 * <tr><td>LocationNo.</td>	<td>16 byte</td></tr>
 * <tr><td>Item identification Code</td>	<td>16 byte</td></tr>
 * <tr><td>Item Code</td>		<td>16 byte</td></tr>
 * <tr><td>JANCode</td>		<td>16 byte</td></tr>
 * <tr><td>Bundle ITF</td>		<td>16 byte</td></tr>
 * <tr><td>Case ITF</td>		<td>16 byte</td></tr>
 * <tr><td>ItemName</td>			<td>40 byte</td></tr>
 * <tr><td>Packed qty per bundle. </td>		<td>6 byte</td></tr>
 * <tr><td>Packed qty per case</td>		<td>6 byte</td></tr>
 * <tr><td>Unit</td>				<td>6 byte</td></tr>
 * <tr><td>Lot No.</td>		<td>10 byte</td></tr>
 * <tr><td>Expiry date</td>		<td>8 byte</td></tr>
 * <tr><td>Manufacturing date</td>			<td>8 byte</td></tr>
 * <tr><td>Mode of packing</td>				<td>1 byte</td></tr>
 * <tr><td>Movement rack existence flag</td>	<td>1 byte</td></tr>
 * <tr><td>Response flag</td>		<td>1 byte</td></tr>
 * <tr><td> Detailed Error</td>		<td>2 byte</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:19 $
 * @author  $Author: suresh $
 */
public class RFTId5013 extends SendIdMessage
{
	//#CM702379
	// Class fields --------------------------------------------------
	//#CM702380
	/**
	 * Definition of Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM702381
	/**
	 * Definition of offset of Consignor Code
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM702382
	/**
	 * Definition of offset of Consignor Name
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM702383
	/**
	 * Definition of offset of Area No.
	 */
	private static final int OFF_AREA_NO = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
	
	//#CM702384
	/**
	 * Definition of offset of Location No.
	 */
	private static final int OFF_LOCATION = OFF_AREA_NO + LEN_AREA_NO ;
	
	//#CM702385
	/**
	 * Definition of offset of Item identification Code
	 */
	private static final int OFF_ITEM_ID = OFF_LOCATION + LEN_LOCATION ;
	
	//#CM702386
	/**
	 * Definition of offset of Item Code
	 */
	private static final int OFF_ITEM_CODE = OFF_ITEM_ID + LEN_ITEM_ID ;
	
	//#CM702387
	/**
	 * Definition of offset of JANCode
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE ;
	
	//#CM702388
	/**
	 * Definition of offset of Bundle ITF
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE ;
	
	//#CM702389
	/**
	 * Definition of offset of Case ITF
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF ;
	
	//#CM702390
	/**
	 * Definition of offset of Item Name
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF ;
	
	//#CM702391
	/**
	 * Definition of offset of Packed qty per bundle. 
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME ;
	
	//#CM702392
	/**
	 * Definition of offset of Packed qty per case
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;
	
	//#CM702393
	/**
	 * Definition of offset of Unit
	 */
	private static final int OFF_UNIT = OFF_ENTERING_QTY + LEN_ENTERING_QTY ;
	
	//#CM702394
	/**
	 * Definition of offset of Lot No.
	 */
	private static final int OFF_LOT_NO = OFF_UNIT + LEN_UNIT ;
	
	//#CM702395
	/**
	 * Definition of offset of Expiry date 
	 */
	private static final int OFF_USE_BY_DATE = OFF_LOT_NO + LEN_LOT_NO ;
	
	//#CM702396
	/**
	 * Definition of offset of Manufacturing date
	 */
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE ;
	
	//#CM702397
	/**
	 * Definition of offset of Mode of packing
	 */
	private static final int OFF_ITEM_FORM = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE;
	
	//#CM702398
	/**
	 * Definition of offset of Movement rack existence flag
	 */
	private static final int OFF_MOBILE_RACK_FLAG = OFF_ITEM_FORM + LEN_CASE_PIECE_FLAG;

	//#CM702399
	/**
	 * ID Number
	 */
	public static final String ID = "5013";
	
	//#CM702400
	/**
	 * Movement rack package not available flag
	 */
	public static final String NO_MOBILE_RACK_FLAG = "0";
	
	//#CM702401
	/**
	 * Movement rack package available flag
	 */
	public static final String MOBILE_RACK_FLAG = "1";
	//#CM702402
	// Class variables -----------------------------------------------

	//#CM702403
	// Class method --------------------------------------------------
	//#CM702404
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:19 $";
	}

	//#CM702405
	// Constructors --------------------------------------------------
	//#CM702406
	/**
	 * Constructor
	 */
	public RFTId5013 ()
	{
		super() ;

		offAnsCode = OFF_MOBILE_RACK_FLAG + LEN_MOBILE_RACK_FLAG;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	

	//#CM702407
	// Public methods ------------------------------------------------
	//#CM702408
	/**
	 * Set Person in charge code. 
	 * @param  workerCode  Person in charge code
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE) ;
	}
	
	//#CM702409
	/**
	 * Set Consignor Code.
	 * @param  consignorCode  Consignor Code
	 */
	public void setConsignorCode (String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE) ;
	}
	
	//#CM702410
	/**
	 * Set Consignor Name.
	 * @param  consignorName  Consignor Name
	 */
	public void setConsignorName (String consignorName)
	{
		setToBuffer(consignorName, OFF_CONSIGNOR_NAME) ;
	}
	
	//#CM702411
	/**
	 * Set Area No.
	 * @param  areaNo  Storage Area No.
	 */
	public void setAreaNo (String areaNo)
	{
		setToBuffer(areaNo, OFF_AREA_NO) ;
	}
	
	//#CM702412
	/**
	 * Set Location No.
	 * @param  locationNo  StorageLocationNo.
	 */
	public void setLocationNo (String locationNo)
	{
		setToBuffer(locationNo, OFF_LOCATION) ;
	}
	
	//#CM702413
	/**
	 * Set Item identification Code.
	 * @param  itemId  Item identification Code
	 */
	public void setItemId (String itemId)
	{
		setToBuffer(itemId, OFF_ITEM_ID) ;
	}
	
	//#CM702414
	/**
	 * Set Item Code.
	 * @param  Item Code  Item Code
	 */
	public void setItemCode (String itemCode)
	{
		setToBuffer(itemCode, OFF_ITEM_CODE) ;
	}
	
	//#CM702415
	/**
	 * Set JAN Code
	 * @param  JANCode  JANCode
	 */
	public void setJANCode (String JANCode)
	{
		setToBuffer(JANCode, OFF_JAN_CODE) ;
	}
	
	//#CM702416
	/**
	 * Set Bundle ITF
	 * @param  bundleITF  Bundle ITF
	 */
	public void setBundleITF (String bundleITF)
	{
		setToBuffer(bundleITF, OFF_BUNDLE_ITF) ;
	}
	
	//#CM702417
	/**
	 * Set Case ITF
	 * @param  ITF  Case ITF
	 */
	public void setITF (String ITF)
	{
		setToBuffer(ITF, OFF_ITF) ;
	}
	
	//#CM702418
	/**
	 * Set Item Name.
	 * @param  itemName  ItemName
	 */
	public void setItemName (String itemName)
	{
		setToBuffer(itemName, OFF_ITEM_NAME) ;
	}
	
	//#CM702419
	/**
	 * Set Packed qty per bundle. 
	 * @param  bundleEnteringQty  Packed qty per bundle. 
	 */
	public void setBundleEnteringQty (int bundleEnteringQty)
	{
		//#CM702420
		// Store Data by right justify. 
		setToBufferRight(bundleEnteringQty, OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY) ;
	}
	
	//#CM702421
	/**
	 * Set Packed qty per case. 
	 * @param  enteringQty  Packed qty per case
	 */
	public void setEnteringQty (int enteringQty)
	{
		//#CM702422
		// Store Data by right justify. 
		setToBufferRight(enteringQty, OFF_ENTERING_QTY, LEN_ENTERING_QTY) ;
	}
	
	//#CM702423
	/**
	 * Set Unit. 
	 * @param  unit  Unit
	 */
	public void setUnit (String unit)
	{
		setToBuffer(unit, OFF_UNIT) ;
	}
	
	//#CM702424
	/**
	 * Set Lot  No.
	 * @param  lotNo  Lot No.
	 */
	public void setLotNo (String lotNo)
	{
		setToBuffer(lotNo, OFF_LOT_NO) ;
	}
	
	//#CM702425
	/**
	 * Set the Expiry date.
	 * @param  useByDate  Expiry date
	 */
	public void setUseByDate (String useByDate)
	{
		setToBuffer(useByDate, OFF_USE_BY_DATE) ;
	}
	
	//#CM702426
	/**
	 * Set Manufacturing date. 
	 * @param  manufactureDate  Manufacturing date
	 */
	public void setManufactureDate (String manufactureDate)
	{
		setToBuffer(manufactureDate, OFF_MANUFACTURE_DATE) ;
	}
	
	//#CM702427
	/**
	 * Set Mode of packing. 
	 * @param  itemForm  Mode of packing
	 */
	public void setItemForm (String itemForm)
	{
		setToBuffer(itemForm, OFF_ITEM_FORM) ;
	}
	
	//#CM702428
	/**
	 * Set Movement rack existence flag. 
	 * @param  mobileRackFlag  Movement rack existence flag
	 */
	public void setMobileRackFlag (String mobileRackFlag)
	{
		setToBuffer(mobileRackFlag, OFF_MOBILE_RACK_FLAG) ;
	}

	//#CM702429
	// Package methods -----------------------------------------------

	//#CM702430
	// Protected methods ---------------------------------------------

	//#CM702431
	// Private methods -----------------------------------------------

}
//#CM702432
//end of class

