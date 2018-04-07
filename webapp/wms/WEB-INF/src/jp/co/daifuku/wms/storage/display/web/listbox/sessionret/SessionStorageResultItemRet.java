// $Id: SessionStorageResultItemRet.java,v 1.2 2006/12/07 08:57:24 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM570620
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570621
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * The class to do data retrieval for commodity list box Retrieval processing (Results).<BR>
 * Receive the search condition with Parameter, and retrieve the Consignor list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageResultItemRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   <CODE>find</CODE> Method is called and Retrieval of Results View information is done. <BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code<BR>
 *      , Start Storage date<BR>
 *      , End Storage date<BR>
 *      , Item Code<BR>
 *      , Work type : Storage <BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DVRESULTVIEW <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Display processing(<CODE>getEntities</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Acquire the data to display it on the screen. <BR>
 *   1.Acquire display information from the retrieval result of obtaining in Retrieval processing. <BR>
 * <BR>
 *   <Display Item>
 *   <DIR>
 *      , Item Code<BR>
 *      , Item Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2008/10/29</TD><TD>Y.Okamura</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:24 $
 * @author  $Author: suresh $
 */
public class SessionStorageResultItemRet extends SessionRet
{
	//#CM570622
	// Class fields --------------------------------------------------

	//#CM570623
	// Class variables -----------------------------------------------

	//#CM570624
	// Class method --------------------------------------------------
	//#CM570625
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:24 $");
	}

	//#CM570626
	// Constructors --------------------------------------------------
	//#CM570627
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageResultItemRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;
		//#CM570628
		// Retrieval
		find(param);
	}

	//#CM570629
	// Public methods ------------------------------------------------
	//#CM570630
	/**
	 * Return Retrieval result of < CODE>DvResultView</CODE >. <BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *      , Item Code<BR>
	 *      , Item Name<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DvResultView
	 */
	public StorageSupportParameter[] getEntities()
	{
		StorageSupportParameter[] resultParam = null;
		ResultView[] array = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				array = (ResultView[]) ((ResultViewFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				resultParam = getDispData(array);
			}
			catch (Exception e)
			{
				//#CM570631
				// 6006002=The data base error occurred. {0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultParam;
	}

	//#CM570632
	// Package methods -----------------------------------------------

	//#CM570633
	// Protected methods ---------------------------------------------

	//#CM570634
	// Private methods -----------------------------------------------
	//#CM570635
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>ResultViewFinder</code > as instance variable for Retrieval.<BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter param) throws Exception
	{
		int count = 0;

		ResultViewSearchKey skey = new ResultViewSearchKey();

		//#CM570636
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570637
		// Start Storage date
		if (!StringUtil.isBlank(param.getFromStorageDate()))
		{
			skey.setWorkDate(WmsFormatter.getTimeFormat(param.getFromStorageDate(),"yyyyMMdd"), ">=");
		}
		//#CM570638
		// End Storage date
		if (!StringUtil.isBlank(param.getToStorageDate()))
		{
			skey.setWorkDate(WmsFormatter.getTimeFormat(param.getToStorageDate(),"yyyyMMdd"), "<=");
		}
		//#CM570639
		// Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			skey.setItemCode(param.getItemCode());
		}
		skey.setJobType(ResultView.JOB_TYPE_STORAGE);

		skey.setItemCodeGroup(1);
		skey.setItemName1Group(2);
		skey.setItemCodeCollect("");
		skey.setItemName1Collect("");
		skey.setItemCodeOrder(1, true);
		skey.setItemName1Order(2, true);

		wFinder = new ResultViewFinder(wConn);
		//#CM570640
		// Cursor open
		wFinder.open();
		count = ((ResultViewFinder) wFinder).search(skey);
		//#CM570641
		// Initialization
		wLength = count;
		wCurrent = 0;
	}
	
	//#CM570642
	/**
	 * Set the DvResultView entity in <CODE>StorageSupportParameter</CODE> and return it.  <BR>
	 * <BR>
	 * @param result ResultView[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private StorageSupportParameter[] getDispData(ResultView[] result)
	{
		StorageSupportParameter[] resultParam = null;

		Vector tempVec = new Vector();
		for (int i = 0; i < result.length; i++)
		{
			StorageSupportParameter param = new StorageSupportParameter();
			param.setItemCode(result[i].getItemCode());
			param.setItemName(result[i].getItemName1());
			
			tempVec.add(param);
		}
		resultParam = new StorageSupportParameter[tempVec.size()];
		tempVec.copyInto(resultParam);

		return resultParam;
	}


}
//#CM570643
//end of class
