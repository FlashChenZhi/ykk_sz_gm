// $Id: StoragePlanRegistBusiness.java,v 1.2 2006/12/07 08:57:13 suresh Exp $

//#CM573273
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageplanregist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragelocation.ListStorageLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplanregist.ListStoragePlanRegistBusiness;
import jp.co.daifuku.wms.storage.schedule.StoragePlanRegistSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;


//#CM573274
/**
 * Designer : T.Uehata <BR>
 * Maker : T.Uehata <BR>
 * <BR>
 * The Storage Plan Add class. <BR>
 * Set the content input from the screen in Parameter.  The schedule does Storage Plan Add based on the Parameter. <BR>
 * Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 * and false when the schedule does not do Completed because of the condition error etc.<BR>
 * The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * Moreover, do Commit  of Transaction and Rollback on this screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Input button is pressed (<CODE>btn_Input_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 *  Set the content input from the input area in Parameter, and the schedule checks the input condition based on the Parameter. <BR>
 *  Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 *  and false when the schedule does not do Completed because of the condition error etc.<BR>
 *  The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 *  Add information on the input area to Preset area when the result is true. <BR>
 *  Update the data of Preset for the modification in information on the input area when you press the input button after the modify button is pressed. <BR>
 *  <BR>
 * 	[Parameter] *Mandatory input<BR>
 * 	<BR>
 * 	<DIR>
 * 		Worker Code* <BR>
 * 		Password* <BR>
 * 		Consignor Code* <BR>
 * 		Consignor Name <BR>
 * 		Storage plan date* <BR>
 * 		Item Code* <BR>
 * 		Item Name <BR>
 * 		Case/Piece flag* <BR>
 * 		Storage Location <BR>
 * 		Packed qty per case*2 <BR>
 * 		Packed qty per bundle <BR>
 * 		Host Plan Case qty*3 <BR>
 * 		Host Plan Piece qty*3 <BR>
 * 		CaseITF <BR>
 * 		Bundle ITF <BR>
 * 		<BR>
 * 		*2 <BR>
 * 		When entered Host Plan Case qty(>0), Mandatory input <BR>
 * 		*3 <BR>
 * 		The input of one or more is Mandatory condition in either Host Plan Case qty or Host Plan Piece qty.  <BR>
 * 	</DIR>
 *  <BR>
 *  [Output data] <BR>
 *  <BR>
 * 	<DIR>
 *  	Consignor Code <BR>
 *  	Consignor Name <BR>
 *  	Storage plan date <BR>
 *  	Item Code <BR>
 *  	Item Name <BR>
 *  	Flag <BR>
 *  	Storage Location <BR>
 *  	Packed qty per case <BR>
 *  	Packed qty per bundle <BR>
 *  	Host Plan Case qty <BR>
 *  	Host Plan Piece qty <BR>
 *  	CaseITF <BR>
 *  	Bundle ITF <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.Processing when Add button is pressed (<CODE>btn_Submit_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 *  The content input from Preset area is set in Parameter, and the schedule does Storage Plan Add based on the Parameter. <BR>
 *  Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 *  and false when the schedule does not do Completed because of the condition error etc.<BR>
 *  The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * 	<DIR>
 *   [Parameter] *Mandatory input <BR>
 *   <BR>
 * 		Worker Code* <BR>
 * 		Password* <BR>
 * 		Preset.Consignor Code <BR>
 *  	Preset.Consignor Name <BR>
 *  	Preset.Storage plan date <BR>
 *  	Preset.Item Code <BR>
 *  	Preset.Item Name <BR>
 *  	Preset.Flag <BR>
 *  	Preset.Storage Location <BR>
 *  	Preset.Packed qty per case <BR>
 *  	Preset.Packed qty per bundle <BR>
 *  	Preset.Host Plan Case qty <BR>
 *  	Preset.Host Plan Piece qty <BR>
 *  	Preset.CaseITF <BR>
 *  	Preset.Bundle ITF <BR>
 *  	Terminal No. <BR>
 *  	Preset Line No.. <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>T.Uehata</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/05/16</TD><TD>T.Nakajima</TD><TD>Addition of Corresponding Add Flag [2 : For Receiving]</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:13 $
 * @author  $Author: suresh $
 */
public class StoragePlanRegistBusiness extends StoragePlanRegist implements WMSConstants
{
	//#CM573275
	// Address transition ahead
	//#CM573276
	/**
	 * Consignor retrieval pop up address
	 */
	private static final String DO_SRCH_CONSIGNOR =
		"/storage/listbox/liststorageconsignor/ListStorageConsignor.do";
	
	//#CM573277
	/**
	 * Storage plan date retrieval pop up address
	 */
	private static final String DO_SRCH_STORAGEPLANDATE =
		"/storage/listbox/liststorageplandate/ListStoragePlanDate.do";
	
	//#CM573278
	/**
	 * Item list Retrieval Popup address
	 */
	private static final String DO_SRCH_ITEM =
		"/storage/listbox/liststorageitem/ListStorageItem.do";
	
	//#CM573279
	/**
	 * Storage PlanRetrieval Popup address
	 */
	private static final String DO_SRCH_STORAGEPLAN =
		"/storage/listbox/liststorageplanregist/ListStoragePlanRegist.do";
	
	//#CM573280
	/**
	 * Storage Location retrieval Popup address
	 */
	protected static final String DO_SRCH_STRGLCT =
		"/storage/listbox/liststoragelocation/ListStorageLocation.do";
	
	//#CM573281
	/**
	 * Screen address when Retrieval pop up is being called
	 */
	private static final String DO_SRCH_PROCESS = "/progress.do";

	
	//#CM573282
	/**
	 * For ViewState : Preset Line No..
	 */
	private static final String LINENO = "LINENO";

	//#CM573283
	// Class fields --------------------------------------------------

	//#CM573284
	// Class variables -----------------------------------------------

	//#CM573285
	// Class method --------------------------------------------------

	//#CM573286
	// Constructors --------------------------------------------------

	//#CM573287
	// private method --------------------------------------------------
	//#CM573288
	/**
	 * Initialize the input area. <BR>
	 * <BR>
	 * Outline : Set Initial value to each Item of Input area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Worker Code. <BR>
	 * 		2.Set Initial value in each Item. <BR>
	 * 		<BR>
	 *    	Item Name[Initial value]<BR>
	 *		<BR>
	 *    	<DIR>
	 * 			Worker Code[*1] <BR>
	 * 			Password[*1] <BR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Consignor Name[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Storage plan date[None] <BR>
	 * 			Item Code[None] <BR>
	 * 			Item Name[None] <BR>
	 * 			Case , PieceFlag[Case] <BR>
	 * 			Storage Location[None] <BR>
	 * 			Packed qty per case[None] <BR>
	 * 			Packed qty per bundle[None] <BR>
	 * 			Host Plan Case qty[None] <BR>
	 * 			Host Plan Piece qty[None] <BR>
	 * 			CaseITF[None] <BR>
	 * 			Bundle ITF[None] <BR>
	 * 			<BR>
	 * 			*1<BR>
	 * 			<DIR>
	 * 				Clear when workerclearFlg is true. <BR>
	 * 				Do not Clear when workerclearFlg is false. <BR>
	 * 			</DIR>
	 *   	</DIR>
	 * </DIR>
	 * <BR>
	 * @param workerclearFlg boolean Clear flag.
	 * @throws Exception It reports on all exceptions.  
	 */
	private void inputDataClear(boolean workerclearFlg) throws Exception
	{
		//#CM573289
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);

		if (workerclearFlg)
		{
			//#CM573290
			// Worker Code
			txt_WorkerCode.setText("");
			//#CM573291
			// Password
			txt_Password.setText("");
		}

		//#CM573292
		// Consignor Code, Consignor Name
		setConsignor();
		//#CM573293
		// Storage plan date
		txt_StoragePlanDate.setText("");
		//#CM573294
		// Item Code
		txt_ItemCode.setText("");
		//#CM573295
		// Item Name
		txt_ItemName.setText("");
		//#CM573296
		// Case/Piece flag(Piece)
		rdo_CpfCase.setChecked(false);
		//#CM573297
		// Case/Piece flag(Unspecified)
		rdo_CpfAppointOff.setChecked(false);
		//#CM573298
		// Case/Piece flag(Case)
		rdo_CpfCase.setChecked(true);
		//#CM573299
		// Storage Location
		txt_StorageLocation.setText("");
		//#CM573300
		// Packed qty per case
		txt_CaseEntering.setText("");
		//#CM573301
		// Host Plan Case qty
		txt_HostCasePlanQty.setText("");
		//#CM573302
		// CaseITF
		txt_CaseItf.setText("");
		//#CM573303
		// Packed qty per bundle
		txt_BundleEntering.setText("");
		//#CM573304
		// Host Plan Piece qty
		txt_HostPiesePlanQty.setText("");
		//#CM573305
		// Bundle ITF
		txt_BundleItf.setText("");
	}

	//#CM573306
	/**
	 * Method which sets the value of Input area in Preset area. <BR>
	 * <BR>
	 * Outline:Set the value of Input area in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Edit data for the balloon display. <BR>
	 * 		2.Set data in each Item of the list cell. <BR>
	 * 		3.Return Initial value correction Preset No.<BR>
	 * 		<DIR>
	 * 			[List cell row number list]
	 * 			<DIR>
	 * 				3.Consignor Code <BR>
	 * 				4.Storage plan date <BR>
	 * 				5.Item Code <BR>
	 * 				6.Flag <BR>
	 * 				7.Storage Location <BR>
	 * 				8.Packed qty per case <BR>
	 * 				9.Host Plan Case qty <BR>
	 * 				10.CaseITF <BR>
	 * 				11.Consignor Name <BR>
	 * 				12.Item Name <BR>
	 * 				13.Packed qty per bundle <BR>
	 * 				14.Host Plan Piece qty <BR>
	 * 				15.Bundle ITF <BR>
	 *	 		</DIR>
	 *		</DIR>
	 * </DIR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setListRow() throws Exception
	{
		//#CM573307
		// Data for ToolTip is edited. 
		ToolTipHelper toolTip = new ToolTipHelper();
		//#CM573308
		// Consignor Name
		toolTip.add(DisplayText.getText("LBL-W0026"), txt_ConsignorName.getText());
		//#CM573309
		// Item Name
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		//#CM573310
		// Case ITF
		toolTip.add(DisplayText.getText("LBL-W0010"), txt_CaseItf.getText());
		//#CM573311
		// Bundle ITF
		toolTip.add(DisplayText.getText("LBL-W0006"), txt_BundleItf.getText());

		//#CM573312
		//Set the tool tip in Current line. 
		lst_SStoragePlanRegist.setToolTip(
			lst_SStoragePlanRegist.getCurrentRow(),
			toolTip.getText());

		//#CM573313
		// Consignor Code
		lst_SStoragePlanRegist.setValue(3, txt_ConsignorCode.getText());
		//#CM573314
		// Storage Plan
		lst_SStoragePlanRegist.setValue(4, txt_StoragePlanDate.getText());
		//#CM573315
		// Item Code
		lst_SStoragePlanRegist.setValue(5, txt_ItemCode.getText());
		//#CM573316
		// Flag
		lst_SStoragePlanRegist.setValue(6, getCpfName());

		//#CM573317
		// Storage Location
		lst_SStoragePlanRegist.setValue(7, txt_StorageLocation.getText());

		//#CM573318
		// Packed qty per case
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_SStoragePlanRegist.setValue(8, "0");
		}
		else
		{
			lst_SStoragePlanRegist.setValue(
				8,
				WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		//#CM573319
		// Host Plan Case qty
		if (StringUtil.isBlank(txt_HostCasePlanQty.getText()))
		{
			lst_SStoragePlanRegist.setValue(9, "0");
		}
		else
		{
			lst_SStoragePlanRegist.setValue(
				9,
				WmsFormatter.getNumFormat(txt_HostCasePlanQty.getInt()));
		}
		//#CM573320
		// CaseITF
		lst_SStoragePlanRegist.setValue(10, txt_CaseItf.getText());
		//#CM573321
		// Consignor Name
		lst_SStoragePlanRegist.setValue(11, txt_ConsignorName.getText());
		//#CM573322
		// Item Name
		lst_SStoragePlanRegist.setValue(12, txt_ItemName.getText());
		//#CM573323
		// Packed qty per bundle
		if (StringUtil.isBlank(txt_BundleEntering.getText()))
		{
			lst_SStoragePlanRegist.setValue(13, "0");
		}
		else
		{
			lst_SStoragePlanRegist.setValue(
				13,
				WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		}
		//#CM573324
		// Host Plan Piece qty
		if (StringUtil.isBlank(txt_HostPiesePlanQty.getText()))
		{
			lst_SStoragePlanRegist.setValue(14, "0");
		}
		else
		{
			lst_SStoragePlanRegist.setValue(
				14,
				WmsFormatter.getNumFormat(txt_HostPiesePlanQty.getInt()));
		}
		//#CM573325
		// Bundle ITF
		lst_SStoragePlanRegist.setValue(15, txt_BundleItf.getText());

		//#CM573326
		// Return Default correction Status. 
		this.getViewState().setInt(LINENO, -1);
	}

	//#CM573327
	/**
	 * Method which sets the data of Preset area in the Parameter array. <BR>
	 * <BR>
	 * Outline:Set the data of Preset area in the Parameter array, and return it. <BR>
	 * Return null when the set data does not exist. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set Preset data of All in case of new input or Add Button is pressed. (Preset Line No. for correction = -1)<BR>
	 * 		2.Set Preset data in case of the input data under the correction except the line for the correction. <BR>
	 * 		<DIR>
	 *   		[Return Data]<BR>
	 *   		<DIR>
	 * 				Worker Code <BR>
	 * 				Password <BR>
	 * 			 	Preset.Consignor Code <BR>
	 *  			Preset.Consignor Name <BR>
	 *  			Preset.Storage plan date <BR>
	 *  			Preset.Item Code <BR>
	 *  			Preset.Item Name <BR>
	 *  			Preset.Flag <BR>
	 *  			Preset.Storage Location <BR>
	 *  			Preset.Packed qty per case <BR>
	 *  			Preset.Packed qty per bundle <BR>
	 *  			Preset.Host Plan Case qty <BR>
	 *  			Preset.Host Plan Piece qty <BR>
	 *  			Preset.CaseITF <BR>
	 *  			Preset.Bundle ITF <BR>
	 *  			Terminal No. <BR>
	 *  			Preset Line No.. <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param lineno int Line No. for modification
	 * @return <CODE>StorageSupportParameter</CODE> array which stores information on Preset area. 
	 * @throws Exception It reports on all exceptions.  
	 */
	private StorageSupportParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		Locale locale = this.httpRequest.getLocale();

		for (int i = 1; i < lst_SStoragePlanRegist.getMaxRows(); i++)
		{
			//#CM573328
			// Exclude the line for the modification. 
			if (i == lineno)
			{
				continue;
			}

			//#CM573329
			// Line specification
			lst_SStoragePlanRegist.setCurrentRow(i);

			//#CM573330
			// Set in schedule Parameter.
			StorageSupportParameter param = new StorageSupportParameter();
			//#CM573331
			// Input area information
			//#CM573332
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM573333
			// Password
			param.setPassword(txt_Password.getText());
			//#CM573334
			// Consignor Code
			param.setConsignorCode(lst_SStoragePlanRegist.getValue(3));
			//#CM573335
			// Storage plan date
			param.setStoragePlanDate(
				WmsFormatter.toParamDate(lst_SStoragePlanRegist.getValue(4), locale));
			//#CM573336
			// Item Code
			param.setItemCode(lst_SStoragePlanRegist.getValue(5));
			//#CM573337
			// Case/Piece flag
			param.setCasePieceflg(getCpfCdToListCell());
			//#CM573338
			// Storage Location
			param.setStorageLocation(lst_SStoragePlanRegist.getValue(7));
			//#CM573339
			// Packed qty per case
			param.setEnteringQty(Formatter.getInt(lst_SStoragePlanRegist.getValue(8)));
			//#CM573340
			// Host Plan Case qty
			param.setPlanCaseQty(Formatter.getInt(lst_SStoragePlanRegist.getValue(9)));
			//#CM573341
			// CaseITF
			param.setITF(lst_SStoragePlanRegist.getValue(10));
			//#CM573342
			// Consignor Name
			param.setConsignorName(lst_SStoragePlanRegist.getValue(11));
			//#CM573343
			// Item Name
			param.setItemName(lst_SStoragePlanRegist.getValue(12));
			//#CM573344
			// Packed qty per bundle
			param.setBundleEnteringQty(Formatter.getInt(lst_SStoragePlanRegist.getValue(13)));
			//#CM573345
			// Host Plan Piece qty
			param.setPlanPieceQty(Formatter.getInt(lst_SStoragePlanRegist.getValue(14)));
			//#CM573346
			// Bundle ITF
			param.setBundleITF(lst_SStoragePlanRegist.getValue(15));

			//#CM573347
			// Terminal No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			//#CM573348
			// Line No. is maintained. 
			param.setRowNo(i);

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM573349
			// The value is set if there is set Preset data. 
			StorageSupportParameter[] listparam = new StorageSupportParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM573350
			// Null is set if there is no set Preset data. 
			return null;
		}
	}

	//#CM573351
	/**
	 * Acquire corresponding Description from Case/Piece flag radio Button. <BR>
	 * <BR>
	 * Outline : Return the Description if there is corresponding Description. <BR>
	 * Return the empty string if there is no corresponding Description. <BR>
	 * <BR>
	 * @return Case/Piece flag description.
	 * @throws Exception It reports on all exceptions.  
	 */
	private String getCpfName() throws Exception
	{
		//#CM573352
		// Flag
		if (rdo_CpfAppointOff.getChecked())
		{
			//#CM573353
			// Unspecified
			return DisplayText.getText("LBL-W0374");
		}
		else if (rdo_CpfCase.getChecked())
		{
			//#CM573354
			// Case
			return DisplayText.getText("LBL-W0375");
		}
		else if (rdo_CpfPiece.getChecked())
		{
			//#CM573355
			// Piece
			return DisplayText.getText("LBL-W0376");
		}

		return "";
	}
	
	//#CM573356
	/**
	 * Acquire corresponding Description from Flag of List cell. <BR>
	 * <BR>
	 * Outline : Return the Description if there is corresponding Description. <BR>
	 * Return the empty string if there is no corresponding Description. <BR>
	 * <BR>
	 * @return Case/Piece flag description.
	 * @throws Exception It reports on all exceptions.  
	 */
	private String getCpfCdToListCell() throws Exception
	{
		//#CM573357
		// Flag
		if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0374")))
		{
			//#CM573358
			// Unspecified
			return StorageSupportParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0375")))
		{
			//#CM573359
			// Case
			return StorageSupportParameter.CASEPIECE_FLAG_CASE;
		}
		else if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0376")))
		{
			//#CM573360
			// Piece
			return StorageSupportParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}
	
	//#CM573361
	/**
	 * Acquire corresponding Description from Case/Piece flag radio Button. <BR>
	 * <BR>
	 * Outline : Return the Description if there is corresponding Description. <BR>
	 * Return the empty string if there is no corresponding Description. <BR>
	 * <BR>
	 * @return Case/Piece flag description.
	 * @throws Exception It reports on all exceptions.  
	 */
	private String getCpfCdToInputArea() throws Exception
	{
		//#CM573362
		// Flag
		if (rdo_CpfAppointOff.getChecked())
		{
			//#CM573363
			// Unspecified
			return StorageSupportParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (rdo_CpfCase.getChecked())
		{
			//#CM573364
			// Case
			return StorageSupportParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_CpfPiece.getChecked())
		{
			//#CM573365
			// Piece
			return StorageSupportParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}
	
	//#CM573366
	/**
	 * Reflect the content of Flag of List cell in radio Button of Input area. <BR>
	 * <BR>
	 * Outline : Reflect the content of Flag of List cell in Case/Piece flag radio Button of Input area. 
	 * <BR>
	 * @throws Exception It reports on all exceptions.  
	 */
	private void setCpfCheck() throws Exception
	{
		if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0374")))
		{
			//#CM573367
			// Unspecified is checked. 
			rdo_CpfAppointOff.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
		}
		else if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0375")))
		{
			//#CM573368
			// Case is checked. 
			rdo_CpfAppointOff.setChecked(false);
			rdo_CpfCase.setChecked(true);
			rdo_CpfPiece.setChecked(false);
		}
		else if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0376")))
		{
			//#CM573369
			// Piece is checked. 
			rdo_CpfAppointOff.setChecked(false);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(true);
		}
	}
	
	//#CM573370
	// Public methods ------------------------------------------------

	//#CM573371
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set Initial value to correction Preset Line No. <BR>
	 * 		2.Initialize the input area. <BR>
	 * 		3.Initialize Preset area. <BR>
	 * 		<BR>
	 * 		Item[Initial value] <BR>
	 * 		<DIR>
	 * 			Worker Code[None] <BR>
	 * 			Password[None] <BR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Consignor Name[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Storage plan date[None] <BR>
	 * 			Item Code[None] <BR>
	 * 			Item Name[None] <BR>
	 * 			Case , PieceFlag[Case] <BR>
	 * 			Storage Location[None] <BR>
	 * 			Packed qty per case[None] <BR>
	 * 			Packed qty per bundle[None] <BR>
	 * 			Host Plan Case qty[None] <BR>
	 * 			Host Plan Piece qty[None] <BR>
	 * 			CaseITF[None] <BR>
	 * 			Bundle ITF[None] <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		//#CM573372
		// To decide whether Status is Modify when the Modify button is pressed, Preset Line No is set in ViewState.
		//#CM573373
		// (Default:-1)
		this.getViewState().setInt(LINENO, -1);

		//#CM573374
		// Initialize Input area
		inputDataClear(true);
		
		//#CM573375
		// Initializes Preset area.
		btn_Submit.setEnabled(false);
		btn_ListClear.setEnabled(false);
		lst_SStoragePlanRegist.clearRow();
		
	}

	//#CM573376
	/**
	 * It is called before the call of each control event. <BR>
	 * <BR>
	 * Outline:Display the Confirmation dialog.<BR>
	 * Or, Set the cursor in Worker Code. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM573377
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM573378
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM573379
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM573380
		// Confirmation Dialog when Add Button is pressed "Do Add?"
		btn_Submit.setBeforeConfirm("MSG-W0009");

		//#CM573381
		// Confirmation Dialog when Start Clear list button is pressed  "Do you clear the list?"
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM573382
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM573383
	/**
	 * When returning from the pop up window, this Method is called. <BR>
	 * Override the <CODE>page_DlgBack</CODE> set in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Outline:Acquire and set Return Data of the retrieval screen. <BR>
	 * <BR>
	 * <DIR>
	 *      1.Acquire the value returned from the retrieval screen of pop up. <BR>
	 *      2.Set it on the screen when the value is not empty. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM573384
		// Parameter selected from list box is acquired. 
		//#CM573385
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM573386
		// Consignor Name
		String consignorname = param.getParameter(ListStorageConsignorBusiness.CONSIGNORNAME_KEY);
		//#CM573387
		// Storage plan date
		String storageplandate =
			param.getParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY);
		//#CM573388
		// Item Code
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM573389
		// Item Name
		String itemname = param.getParameter(ListStorageItemBusiness.ITEMNAME_KEY);
		//#CM573390
		// Storage Location
		String strglct = param.getParameter(ListStorageLocationBusiness.LOCATION_KEY);
		//#CM573391
		// Packed qty per case
		String entering = param.getParameter(ListStoragePlanRegistBusiness.CASEETR_KEY);
		//#CM573392
		// Packed qty per bundle
		String bundleentering = param.getParameter(ListStoragePlanRegistBusiness.BUNDLEETR_KEY);
		//#CM573393
		// CaseITF
		String itf = param.getParameter(ListStoragePlanRegistBusiness.CASEITF_KEY);
		//#CM573394
		// Bundle ITF
		String bundleitf = param.getParameter(ListStoragePlanRegistBusiness.BUNDLEITF_KEY);
		//#CM573395
		// Case/Piece flag
		String casepieceflag = param.getParameter(ListStoragePlanRegistBusiness.CASEPIECEFLAG_KEY);

		//#CM573396
		// Set the value when it is not empty. 
		//#CM573397
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM573398
		// Consignor Name
		if (!StringUtil.isBlank(consignorname))
		{
			txt_ConsignorName.setText(consignorname);
		}
		//#CM573399
		// Storage plan date
		if (!StringUtil.isBlank(storageplandate))
		{
			txt_StoragePlanDate.setText(storageplandate);
		}
		//#CM573400
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM573401
		// Item Name
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemName.setText(itemname);
		}
		//#CM573402
		// Case/Piece flag
		if (!StringUtil.isBlank(casepieceflag))
		{
			if (casepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
			{
				rdo_CpfCase.setChecked(true);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(false);
			}
			else if (casepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(true);
				rdo_CpfAppointOff.setChecked(false);
			}
			else
			{
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(true);
			}
		}
		//#CM573403
		// Storage Location
		if (!StringUtil.isBlank(strglct))
		{
			txt_StorageLocation.setText(strglct);
		}
		//#CM573404
		// Packed qty per case
		if (!StringUtil.isBlank(entering))
		{
			txt_CaseEntering.setText(entering);
		}
		//#CM573405
		// Packed qty per bundle
		if (!StringUtil.isBlank(bundleentering))
		{
			txt_BundleEntering.setText(bundleentering);
		}
		//#CM573406
		// CaseITF
		if (!StringUtil.isBlank(itf))
		{
			txt_CaseItf.setText(itf);
		}
		//#CM573407
		// Bundle ITF
		if (!StringUtil.isBlank(bundleitf))
		{
			txt_BundleItf.setText(bundleitf);
		}
	}

	//#CM573408
	// Package methods -----------------------------------------------

	//#CM573409
	// Protected methods ---------------------------------------------
	//#CM573410
	/**
	 * Do the Input Check.<BR>
	 * <BR>
	 * Outline : Set Message and Return false when abnormality is found. <BR>
	 * <BR>
	 * @return Result of Input Check (true:Normal false:Abnormal) 
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ConsignorName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_StorageLocation))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CaseItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_BundleItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	//#CM573411
	// Private methods -----------------------------------------------
	//#CM573412
	/**
	 * Method to acquire Consignor Code and Consignor Name from the schedule.  <BR>
	 * <BR>
	 * Outline : Set Consignor Code acquired from the schedule and latest Consignor Name to input Item. <BR>
	 * <DIR>
	 *   1.Set corresponding Consignor when there is only one Consignor Code. Clear when it is not so.  <BR>
	 * <DIR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setConsignor() throws Exception
	{
		Connection conn = null;
		try
		{
			txt_ConsignorCode.setText("");
			txt_ConsignorName.setText("");
			
			//#CM573413
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM573414
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new StoragePlanRegistSCH();
			param = (StorageSupportParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
			}
			else
			{
				txt_ConsignorCode.setText("");
				txt_ConsignorName.setText("");
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM573415
			// Close the connection
			if (conn != null)
			{
				conn.rollback();
				conn.close();
			}
		}
	}
	//#CM573416
	// Event handler methods -----------------------------------------
	//#CM573417
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573418
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573419
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573420
	/** 
	 * It is called when the menu button is pressed.<BR>
	 * <BR>
	 * Outline:Change to the menu panel.<BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM573421
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573422
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573423
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573424
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573425
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573426
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573427
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573428
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573429
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573430
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573431
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573432
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573433
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573434
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573435
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573437
	/** 
	 * It is called when the retrieval of Consignor Code button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Consignor list box by the search condition.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PsearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM573438
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573439
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573440
		// Retrieval flag(Work)
		param.setParameter(
			ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_STORAGE_WORK);
		
		//#CM573441
		// Processing Screen -> Result screen
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	//#CM573442
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573443
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573444
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573445
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573446
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573448
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573449
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StoragePlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573450
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StoragePlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573451
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573452
	/** 
	 * It is called when the retrieval button of Storage plan date is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Storage plan date list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Storage plan date <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PsearchStrgPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM573453
		// Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573454
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573455
		// Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		
		//#CM573456
		// Retrieval flag(Work)
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_WORK);
		
		//#CM573457
		// Processing Screen -> Result screen
		redirect(DO_SRCH_STORAGEPLANDATE, param, DO_SRCH_PROCESS);
	}

	//#CM573458
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PStrgPlanSrch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573459
	/** 
	 * It is called when stock Plan retrieval button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display stock Plan retrieval list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input <BR>
	 *    <DIR>
	 *       Consignor Code* <BR>
	 *       Storage plan date <BR>
	 *       Item Code <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PStrgPlanSrch_Click(ActionEvent e) throws Exception
	{
		//#CM573460
		// Set the search condition on stock Plan retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573461
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573462
		// Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		//#CM573463
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		
		//#CM573464
		// Processing Screen -> Result screen
		redirect(DO_SRCH_STORAGEPLAN, param, DO_SRCH_PROCESS);
	}

	//#CM573465
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573466
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573467
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573468
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573469
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573470
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573471
	/** 
	 * It is called when the retrieval button of Item Code is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the item list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Storage plan date <BR>
	 *       Item Code <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PsearchItemCd_Click(ActionEvent e) throws Exception
	{
		//#CM573472
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573473
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573474
		// Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		//#CM573475
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM573476
		// Retrieval flag(Work)
		param.setParameter(
			ListStorageItemBusiness.SEARCHITEM_KEY,
			StorageSupportParameter.SEARCH_STORAGE_WORK);
		
		//#CM573477
		// Processing Screen -> Result screen
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);
	}

	//#CM573478
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573479
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573480
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573481
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573482
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573483
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573484
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573485
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573486
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573487
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573488
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573489
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573490
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573491
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573492
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573493
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573494
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573495
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrgLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573496
	/** 
	 * It is called when the retrieval button of Storage Location is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Storage Location list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Storage plan date <BR>
	 *       Item Code <BR>
	 *       Case/Piece flag <BR>
	 *       Storage Location <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PSearchStrgLocation_Click(ActionEvent e) throws Exception
	{
		//#CM573497
		// Storage Set the search condition on the Location retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573498
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573499
		// Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		//#CM573500
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM573501
		// Storage Location
		param.setParameter(ListStorageLocationBusiness.LOCATION_KEY, txt_StorageLocation.getText());
		//#CM573502
		// CasePieceFlag
		param.setParameter(ListStorageLocationBusiness.CASEPIECE_FLAG_KEY, getCpfCdToInputArea());
		//#CM573503
		// Retrieval flag(Work)
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_STORAGE_LOCATION_KEY,
			StorageSupportParameter.SEARCH_STORAGE_WORK);
		//#CM573504
		// Case/Piece flag for retrieval
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_CASEPIECE_FLAG_KEY,
			StorageSupportParameter.CASEPIECE_FLAG_NOTHING);

		//#CM573505
		// Processing Screen -> Result screen
		redirect(DO_SRCH_STRGLCT, param, DO_SRCH_PROCESS);
	}

	//#CM573506
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573507
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573508
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573509
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573510
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HostCasePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573511
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostCasePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573512
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostCasePlanQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573513
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostCasePlanQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573514
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573515
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573516
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573517
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573518
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573519
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573520
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573521
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573522
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573523
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HostPiesePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573524
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostPiesePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573525
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostPiesePlanQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573526
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostPiesePlanQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573527
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573528
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573529
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573530
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573531
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573532
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Input_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573533
	/** 
	 * It is called when the input button is pressed.<BR>
	 * <BR>
	 * Outline : Check input Item of Input area, and display it in Preset. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Set the cursor in Worker Code. <BR>
	 *   2.Check Input area input Item. (Mandatory input, Number of characters, Character attribute)<BR>
	 *   3.Start Schedule.<BR>
	 * 	 <DIR>
	 *   	[Parameter] *Mandatory input<BR>
	 *   	<DIR>
	 * 			Worker Code* <BR>
	 * 			Password* <BR>
	 * 			Consignor Code* <BR>
	 * 			Consignor Name <BR>
	 * 			Storage plan date* <BR>
	 * 			Item Code* <BR>
	 * 			Item Name <BR>
	 * 			Case/Piece flag* <BR>
	 * 			Storage Location <BR>
	 * 			Packed qty per case*2 <BR>
	 * 			Packed qty per bundle <BR>
	 * 			Host Plan Case qty*3 <BR>
	 * 			Host Plan Piece qty*3 <BR>
	 * 			CaseITF <BR>
	 * 			Bundle ITF <BR>
	 * 			<BR>
	 * 			*2 <BR>
	 * 			When entered Host Plan Case qty(>0), Mandatory input <BR>
	 * 			*3 <BR>
	 * 			The input of one or more is Mandatory condition in either Host Plan Case qty or Host Plan Piece qty.  <BR>
	 *	 	</DIR>
	 *	 </DIR>
	 *   <BR>
	 *   4.If the result of the schedule is true, Add it to Preset area. <BR>
	 *     Update the data of Preset for the modification in information on the input area when you press the input button after the modify button is pressed. <BR>
	 *   5.If the result of the schedule is true, make Add Button , and Clear list Button effective. <BR>
	 *   6.If the result of the schedule is true, Set Preset Line No. of ViewState to Default(Initial value:-1). <BR>
	 *   7.Output the Message acquired from the schedule to the screen. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.   
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		//#CM573534
		// Mandatory Input check
		//#CM573535
		// Worker Code
		txt_WorkerCode.validate();
		//#CM573536
		// Password
		txt_Password.validate();
		//#CM573537
		// Consignor Code
		txt_ConsignorCode.validate();
		//#CM573538
		// Storage plan date
		txt_StoragePlanDate.validate();
		//#CM573539
		// Item Code
		txt_ItemCode.validate();

		//#CM573540
		// Pattern matching
		//#CM573541
		// Consignor Name
		txt_ConsignorName.validate(false);
		//#CM573542
		// Item Name
		txt_ItemName.validate(false);
		//#CM573543
		// Storage Location
		txt_StorageLocation.validate(false);
		//#CM573544
		// Packed qty per case
		txt_CaseEntering.validate(false);
		//#CM573545
		// Packed qty per bundle
		txt_BundleEntering.validate(false);
		//#CM573546
		// Host Plan Case qty
		txt_HostCasePlanQty.validate(false);
		//#CM573547
		// Host Plan Piece qty
		txt_HostPiesePlanQty.validate(false);
		//#CM573548
		// CaseITF
		txt_CaseItf.validate(false);
		//#CM573549
		// Bundle ITF
		txt_BundleItf.validate(false);

		//#CM573550
		// Input character check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			//#CM573551
			// Set in schedule Parameter.
			//#CM573552
			// Input area
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM573553
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM573554
			// Password
			param.setPassword(txt_Password.getText());
			//#CM573555
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM573556
			// Consignor Name
			param.setConsignorName(txt_ConsignorName.getText());
			//#CM573557
			// Storage plan date
			param.setStoragePlanDate(WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
			//#CM573558
			// Item Code
			param.setItemCode(txt_ItemCode.getText());
			//#CM573559
			// Item Name
			param.setItemName(txt_ItemName.getText());
			//#CM573560
			// CasePieceFlag
			param.setCasePieceflg(getCpfCdToInputArea());
			//#CM573561
			// Storage Location
			param.setStorageLocation(txt_StorageLocation.getText());
			//#CM573562
			// CaseStorage Location
			param.setCaseLocation(txt_StorageLocation.getText());
			//#CM573563
			// PieceStorage Location
			param.setPieceLocation(txt_StorageLocation.getText());
			//#CM573564
			// Packed qty per case 
			param.setEnteringQty(Formatter.getInt(txt_CaseEntering.getText()));
			//#CM573565
			// Packed qty per bundle 
			param.setBundleEnteringQty(Formatter.getInt(txt_BundleEntering.getText()));
			//#CM573566
			// Host Plan Case qty 
			param.setPlanCaseQty(Formatter.getInt(txt_HostCasePlanQty.getText()));
			//#CM573567
			// Host Plan Piece qty 
			param.setPlanPieceQty(Formatter.getInt(txt_HostPiesePlanQty.getText()));
			//#CM573568
			// CaseITF 
			param.setITF(txt_CaseItf.getText());
			//#CM573569
			// Bundle ITF 
			param.setBundleITF(txt_BundleItf.getText());

			//#CM573570
			// Set information on Preset area passed to schedule Parameter. 
			//#CM573571
			// For Preset area information storage
			StorageSupportParameter[] listparam = null;

			if (lst_SStoragePlanRegist.getMaxRows() == 1)
			{
				//#CM573572
				// Null is set if there is no data in Preset. 
				listparam = null;
			}
			else
			{
				//#CM573573
				// If data exists, the value is set. 
				listparam = setListParam(this.getViewState().getInt(LINENO));
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StoragePlanRegistSCH();

			//#CM573574
			// Execute the schedule with the connection, Input area information, and Preset area information (Exclude the correction line). 
			if (schedule.check(conn, param, listparam))
			{
				//#CM573575
				// If the result is true, data is set in Preset area.
				if (this.getViewState().getInt(LINENO) == -1)
				{
					//#CM573576
					// Add to Preset in case of new input.
					//#CM573577
					// Add the line to Preset area. 
					lst_SStoragePlanRegist.addRow();
					//#CM573578
					// Make the added line a line for the edit. 
					lst_SStoragePlanRegist.setCurrentRow(lst_SStoragePlanRegist.getMaxRows() - 1);
					//#CM573579
					// Set data. 
					setListRow();
				}
				else
				{
					//#CM573580
					// The data of the line for the modification is updated in case of the input data under the modification.
					//#CM573581
					// Specify the edit line. 
					lst_SStoragePlanRegist.setCurrentRow(this.getViewState().getInt(LINENO));
					//#CM573582
					// Set data. 
					setListRow();
					//#CM573583
					// Return it based on the color of the selection line.
					lst_SStoragePlanRegist.resetHighlight();
				}

				//#CM573584
				// Enable when the Button of Preset area is pressed.
				//#CM573585
				// Add Button
				btn_Submit.setEnabled(true);
				//#CM573586
				// Clear list Button
				btn_ListClear.setEnabled(true);
			}

			//#CM573587
			// Set Message
			message.setMsgResourceKey(schedule.getMessage());
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM573588
				// Close Connection
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}	
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM573589
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573590
	/** 
	 * It is called when the clear button is pressed.<BR>
	 * <BR>
	 * Outline : Clear the Input area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the input area. <BR>
	 * 		2.Set the cursor in Worker Code. <BR>
	 * 		<BR>
	 * 		Item[Initial value] <BR>
	 * 		<DIR>
	 * 			Worker Code[As it is] <BR>
	 * 			Password[As it is] <BR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Consignor Name[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Storage plan date[None] <BR>
	 * 			Item Code[None] <BR>
	 * 			Item Name[None] <BR>
	 * 			Case , PieceFlag[Case] <BR>
	 * 			Storage Location[None] <BR>
	 * 			Packed qty per case[None] <BR>
	 * 			Packed qty per bundle[None] <BR>
	 * 			Host Plan Case qty[None] <BR>
	 * 			Host Plan Piece qty[None] <BR>
	 * 			CaseITF[None] <BR>
	 * 			Bundle ITF[None] <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM573591
		// Initialize input data. 
		//#CM573592
		// (Initialize neither Worker Code nor Password.)
		inputDataClear(false);
	}

	//#CM573593
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573594
	/** 
	 * It is called when Add Button is pressed.<BR>
	 * <BR>
	 * Outline:Stock by information on Preset area Plan Add.<BR>
	 * <BR>
	 * <DIR>
	 *	  1.Set the cursor in Worker Code. <BR>
	 *    2.Display the dialog box, and confirm whether to Add. <BR>
	 *    <DIR>
	 * 		[Confirmation Dialog Cancel]<BR>
	 *			<DIR>
	 *				Do Nothing.
	 *			</DIR>
	 * 		[Confirmation Dialog OK]<BR>
	 *			<DIR>
	 *				1.Start Schedule.<BR>
	 *				<DIR>
	 *   				[Parameter]*Mandatory input<BR>
	 * 					<DIR>
	 * 						Worker Code* <BR>
	 * 						Password* <BR>
	 * 						Preset.Consignor Code <BR>
	 *  					Preset.Consignor Name <BR>
	 *  					Preset.Storage plan date <BR>
	 *  					Preset.Item Code <BR>
	 *  					Preset.Item Name <BR>
	 *  					Preset.Flag <BR>
	 *  					Preset.Storage Location <BR>
	 *  					Preset.Packed qty per case <BR>
	 *  					Preset.Packed qty per bundle <BR>
	 *  					Preset.Host Plan Case qty <BR>
	 *  					Preset.Host Plan Piece qty <BR>
	 *  					Preset.CaseITF <BR>
	 *  					Preset.Bundle ITF <BR>
	 *  					Terminal No. <BR>
	 *  					Preset Line No. <BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.When the result of the schedule is true, Clears information on Preset area. <BR>
	 *				3.Release Modify Status. (Default in Preset Line No of ViewState : Set -1.)<BR>
	 *    			4.Output the Message acquired from the schedule to the screen. <BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM573595
		// Mandatory Input check
		//#CM573596
		// Worker Code
		txt_WorkerCode.validate();
		//#CM573597
		// Password
		txt_Password.validate();

		Connection conn = null;
		try
		{
			//#CM573598
			// Set the cursor in Worker Code. 
			setFocus(txt_WorkerCode);

			//#CM573599
			// For update data storage
			StorageSupportParameter[] param = null;
			//#CM573600
			// All data of Preset area is set.
			param = setListParam(-1);

			//#CM573601
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StoragePlanRegistSCH();

			//#CM573602
			// Begin scheduling. 
			if (schedule.startSCH(conn, param))
			{
				//#CM573603
				// Commit if the result is true. 
				conn.commit();

				//#CM573604
				// Clear the list of Preset area
				lst_SStoragePlanRegist.clearRow();

				//#CM573605
				// Return Default correction Status. 
				this.getViewState().setInt(LINENO, -1);

				//#CM573606
				// Press the button improperly. 
				//#CM573607
				// Start Storage button
				btn_Submit.setEnabled(false);
				//#CM573608
				// Clear list Button
				btn_ListClear.setEnabled(false);
			}
			else
			{
				//#CM573609
				// Rollback if the result is false. 
				conn.rollback();
			}

			//#CM573610
			// Set Message from the schedule. 
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM573611
				// Close Connection
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}	
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM573612
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573613
	/** 
	 * It is called when the clear list button is pressed.<BR>
	 * <BR>
	 * Outline:Clears all display information on Preset. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the dialog box, and confirm whether to Clear Preset information. <BR>
	 *    <DIR>
	 * 		[Confirmation Dialog Cancel]<BR>
	 *			<DIR>
	 *				Do Nothing.
	 *			</DIR>
	 * 		[Confirmation Dialog OK]<BR>
	 *			<DIR>
	 *				1.Clears all List cells.<BR>
	 *				2.Invalidate Button of Preset area. <BR>
	 *				3.Set Initial value in modify Preset Line No. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM573614
		// Delete all Preset lines.
		lst_SStoragePlanRegist.clearRow();

		//#CM573615
		// Press the button improperly. 
		//#CM573616
		// Add Button
		btn_Submit.setEnabled(false);
		//#CM573617
		// Clear list Button
		btn_ListClear.setEnabled(false);

		//#CM573618
		// Make correction Status Default(-1). 
		this.getViewState().setInt(LINENO, -1);
	}

	//#CM573619
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanRegist_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573620
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanRegist_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573621
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanRegist_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573622
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanRegist_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM573623
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanRegist_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573624
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanRegist_Change(ActionEvent e) throws Exception
	{
	}

	//#CM573625
	/** 
	 * It is called when the cancel and modify button are pressed.<BR>
	 * <BR>
	 * Cancel button Outline:Clears pertinent data of Preset. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the dialog box, and confirm whether to Clear Preset information. <BR>
	 *    <DIR>
	 * 			[Confirmation Dialog Cancel]<BR>
	 *			<DIR>
	 *				Do Nothing.
	 *			</DIR>
	 * 			[Confirmation Dialog OK]<BR>
	 *			<DIR>
	 *				1.Clears pertinent data of Preset. <BR>
	 *              2.Invalidate Add Button and Clear list Button when Preset information does not exist. <BR>
	 * 				3.Set Preset Line No. of ViewState to Default(Initial value:-1). <BR>
	 *				4.Set the cursor in Worker Code. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * Modification button Outline:Make pertinent data of Preset modification Status. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display selected Preset information in upper Input area. <BR>
	 *    2.Make the selected line light yellow. <BR>
	 *    3.Set the line number of the line selected by Preset Line No of ViewState. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void lst_SStoragePlanRegist_Click(ActionEvent e) throws Exception
	{
		//#CM573626
		// When Cancel button is clicked
		if (lst_SStoragePlanRegist.getActiveCol() == 1)
		{
			//#CM573627
			// List deletion
			lst_SStoragePlanRegist.removeRow(lst_SStoragePlanRegist.getActiveRow());

			//#CM573628
			// Invalidate Add Button and Clear list Button when Preset information does not exist. 
			//#CM573629
			// Null is set if there is no data in Preset. 
			if (lst_SStoragePlanRegist.getMaxRows() == 1)
			{
				//#CM573630
				// Button of Preset area is not pressed properly. 
				//#CM573631
				// Add Button
				btn_Submit.setEnabled(false);
				//#CM573632
				// Clear list Button
				btn_ListClear.setEnabled(false);
			}

			//#CM573633
			// Return Default correction Status. (Preset Line No. and background color)
			this.getViewState().setInt(LINENO, -1);
			lst_SStoragePlanRegist.resetHighlight();

			//#CM573634
			// Set the cursor in Worker Code. 
			setFocus(txt_WorkerCode);

		}
		//#CM573635
		// When Modify button is clicked 
		else if (lst_SStoragePlanRegist.getActiveCol() == 2)
		{
			//#CM573636
			// Display the line to which the button is pressed in Input area. 
			//#CM573637
			// Specify the line for the correction. 
			lst_SStoragePlanRegist.setCurrentRow(lst_SStoragePlanRegist.getActiveRow());

			//#CM573638
			// Consignor Code
			txt_ConsignorCode.setText(lst_SStoragePlanRegist.getValue(3));
			//#CM573639
			// Storage plan date
			txt_StoragePlanDate.setText(lst_SStoragePlanRegist.getValue(4));
			//#CM573640
			// Item Code
			txt_ItemCode.setText(lst_SStoragePlanRegist.getValue(5));
			//#CM573641
			// Case/Piece flag
			setCpfCheck();
			//#CM573642
			// Storage Location
			txt_StorageLocation.setText(lst_SStoragePlanRegist.getValue(7));
			//#CM573643
			// Packed qty per case
			txt_CaseEntering.setText(lst_SStoragePlanRegist.getValue(8));
			//#CM573644
			// Host Plan Case qty
			txt_HostCasePlanQty.setText(lst_SStoragePlanRegist.getValue(9));
			//#CM573645
			// CaseITF
			txt_CaseItf.setText(lst_SStoragePlanRegist.getValue(10));
			//#CM573646
			// Consignor Name
			txt_ConsignorName.setText(lst_SStoragePlanRegist.getValue(11));
			//#CM573647
			// Item Name
			txt_ItemName.setText(lst_SStoragePlanRegist.getValue(12));
			//#CM573648
			// Packed qty per bundle
			txt_BundleEntering.setText(lst_SStoragePlanRegist.getValue(13));
			//#CM573649
			// Host Plan Piece qty
			txt_HostPiesePlanQty.setText(lst_SStoragePlanRegist.getValue(14));
			//#CM573650
			// Bundle ITF
			txt_BundleItf.setText(lst_SStoragePlanRegist.getValue(15));

			//#CM573651
			// Preserved in ViewState. 
			//#CM573652
			// Set Preset Line No in ViewState to decide modify Status. 
			this.getViewState().setInt(LINENO, lst_SStoragePlanRegist.getActiveRow());
			//#CM573653
			// Change the correction line to light yellow. 
			lst_SStoragePlanRegist.setHighlight(lst_SStoragePlanRegist.getActiveRow());
		}
	}

}
//#CM573654
//end of class
