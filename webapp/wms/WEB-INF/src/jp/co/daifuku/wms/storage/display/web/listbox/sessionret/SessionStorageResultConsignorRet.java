// $Id: SessionStorageResultConsignorRet.java,v 1.2 2006/12/07 08:57:25 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;
//#CM570576
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
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570577
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * The class to do data retrieval for Consignor list list box Retrieval processing (Results).<BR>
 * Receive the search condition with Parameter, and retrieve the Consignor list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageResultConsignorRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   <CODE>find</CODE> Method is called and Retrieval of Results View information is done. <BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code<BR>
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
 *      , Consignor Code<BR>
 *      , Consignor Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2008/10/29</TD><TD>Y.Okamura</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:25 $
 * @author  $Author: suresh $
 */
public class SessionStorageResultConsignorRet extends SessionRet
{
	//#CM570578
	// Class fields --------------------------------------------------

	//#CM570579
	// Class variables -----------------------------------------------

	//#CM570580
	// Class method --------------------------------------------------
	//#CM570581
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:25 $");
	}

	//#CM570582
	// Constructors --------------------------------------------------
	//#CM570583
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageResultConsignorRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM570584
	// Public methods ------------------------------------------------
	//#CM570585
	/**
	 * Return Retrieval result of < CODE>DvResultView</CODE >. <BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *      , Consignor Code<BR>
	 *      , Consignor Name<BR>
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
				//#CM570586
				// 6006002=The data base error occurred. {0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultParam;
	}

	//#CM570587
	// Package methods -----------------------------------------------

	//#CM570588
	// Protected methods ---------------------------------------------

	//#CM570589
	// Private methods -----------------------------------------------
	//#CM570590
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

		//#CM570591
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		skey.setJobType(ResultView.JOB_TYPE_STORAGE);

		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);
		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");
		skey.setConsignorCodeOrder(1, true);
		skey.setConsignorNameOrder(2, true);

		wFinder = new ResultViewFinder(wConn);
		//#CM570592
		// Cursor open
		wFinder.open();
		count = ((ResultViewFinder) wFinder).search(skey);
		//#CM570593
		// Initialization
		wLength = count;
		wCurrent = 0;
	}
	
	//#CM570594
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
			param.setConsignorCode(result[i].getConsignorCode());
			param.setConsignorName(result[i].getConsignorName());
			
			tempVec.add(param);
		}
		resultParam = new StorageSupportParameter[tempVec.size()];
		tempVec.copyInto(resultParam);

		return resultParam;
	}

}
//#CM570595
//end of class
