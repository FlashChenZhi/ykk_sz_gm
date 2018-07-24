// $Id: SessionRet.java,v 1.2 2006/11/07 06:21:40 suresh Exp $
package jp.co.daifuku.wms.base.display.web.listbox.sessionret;

//#CM664508
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;

//#CM664509
/**
 * The class which retrieves and maintains the result. <BR>
 * The SessionCarryInfoRet class etc. should succeed to this class and make it from the class which helps the display of the list box. <BR>
 * Do not do the DataBaseFinder instance generation in this class. <BR>
 * Maintain the instance of the Session*** class which generates the session, and remove the session after use. <BR>
 * The retrieval method must not use find of the Handler class. <BR>
 * The performance falls so that the find method may generate drawers all the retrieval results. <BR>
 * Generate only a necessary amount to the instance by using the Finder class. <BR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:21:40 $
 * @author  $Author: suresh $
 */
public class SessionRet
{
	//#CM664510
	// Class fields --------------------------------------------------
	//#CM664511
	/**
	 * LISTBOX retrieval maximum number
	 */
	public static final int MAXDISP = WmsParam.MAX_NUMBER_OF_DISP_LISTBOX;

	//#CM664512
	// Class variables -----------------------------------------------

	//#CM664513
	/**
	 * <code>Connection</code>
	 */
	protected Connection wConn;

	//#CM664514
	/**
	 * Maintain <code>DatabaseFinder</code> reference. <BR>
	 * Though each class has the reference to Finder by all classes which have succeeded to this <code>SessionRet</code> now
	 * Either corrects the mechanism so that this variable may be Cast and be maintained by each class. 
	 */
	protected DatabaseFinder wFinder;

	//#CM664515
	/**
	 * Display condition
	 */
	protected int wCondition = WmsParam.LISTBOX_SEARCH_COUNT;

	//#CM664516
	/**
	 * It moves to this specified page when selected from the retrieval result. 
	 */
	protected String wNextPage;

	//#CM664517
	/**
	 * It moves to this specified frame when selected from the retrieval result. 
	 */
	protected String wFrameName;

	//#CM664518
	/**
	 * The address on the page of the radical which called the retrieval list to show. 
	 */
	protected String wBasePage;

	//#CM664519
	/**
	 * Retrieval result qty
	 */
	protected int wLength;

	//#CM664520
	/**
	 * Settlement qty of present display
	 */
	protected int wCurrent;

	//#CM664521
	/**
	 * Display beginning position
	 */
	protected int wStartpoint;

	//#CM664522
	/**
	 * Display end position
	 */
	protected int wEndpoint;

	//#CM664523
	/**
	 * Fraction of the number of cases on the final page
	 */
	protected int wFraction;

	//#CM664524
	// Class method --------------------------------------------------
	//#CM664525
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:21:40 $");
	}
	
	//#CM664526
	/**
	 * Close the connection.
	 * @throws Exception Reports on all exceptions. 
	 */
	public void closeConnection() throws Exception
	{
		wConn.close();
	}

	//#CM664527
	// Constructors --------------------------------------------------
	//#CM664528
	/**
	 * Construct new <CODE>SessionRet</CODE>. 
	 */
	public SessionRet()
	{
	}

	//#CM664529
	// Public methods ------------------------------------------------
	//#CM664530
	/**
	 * Return the Display condition.
	 * @return Display condition
	 */
	public int getCondition()
	{
		return wCondition;
	}
	//#CM664531
	/**
	 * Return the DatabaseFinder instance. 
	 * @return DatabaseFinder instance
	 */
	public DatabaseFinder getFinder()
	{
		return wFinder;
	}
	//#CM664532
	/**
	 * The specified page is obtained. 
	 * @return Page
	 */
	public String getNextPage()
	{
		return wNextPage;
	}

	//#CM664533
	/**
	 * The specified frame is obtained. 
	 * @return Frame
	 */
	public String getFrameName()
	{
		return wFrameName;
	}

	//#CM664534
	/**
	 * Page of the retrieved radical is shown. 
	 * @return Page of retrieval radical
	 */
	public String getBasePage()
	{
		return wBasePage;
	}

	//#CM664535
	/**
	 * The display qty is obtained. 
	 * @return Display qty
	 */
	public int getLength()
	{
		return wLength;
	}

	//#CM664536
	/**
	 * The end position is obtained. 
	 * @return End position
	 */
	public int getEnd()
	{
		return wEndpoint;
	}

	//#CM664537
	/**
	 * The display qty is obtained. 
	 * @param searchret_text Present location explanation of list box
	 * @return For ex. Retrieval result: 21-30 of 99 is displayed. 
	 */
	public String getLengthInfo(String[] searchret_text)
	{
		if (getLength() == 0)
		{
			return "";
		}

		String msg =
			searchret_text[0]
				+ getLength()
				+ searchret_text[1]
				+ (getCurrent() + 1)
				+ "-"
				+ (wEndpoint)
				+ searchret_text[2];
		return msg;
	}

	//#CM664538
	/**
	 * Do not delete it though it is not using now because the confirmation is not taken. 
	 * 
	 * The display qty is obtained. 
	 * @param request <CODE>HttpServletRequest</CODE>
	 * @return For ex. Retrieval result: 21-30 of 99 is displayed. 
	 */
	public String getLengthInfo(HttpServletRequest request)
	{
		//#CM664539
		// When you exceed the display maximum qty
		if (getLength() > MAXDISP)
		{
			return getMaxDispInfo();
		}

		//#CM664540
		// It is displayed when data qty is 0, [There was no object data]. 
		if (getLength() == 0)
		{
			MessageResource mr = new MessageResource(request.getLocale());
			return mr.getMessage(6003018, null);
		}

		String[] srhret =
			{
				DisplayText.getText("DISP_SRHRET1_TEXT"),
				DisplayText.getText("DISP_SRHRET2_TEXT"),
				DisplayText.getText("DISP_SRHRET3_TEXT")};

		return getLengthInfo(srhret);
	}

	//#CM664541
	/**
	 * Check the number of Retrieval result. <BR>
	 * Return Message number at the check error. Normally
	 * Return null. <BR>
	 * <Content of check><BR>
	 * Is it not 0?<BR>
	 * Retrieval result Does not qty exceed the display maximum qty?<BR>
	 * @return Message number
	 */
	public String checkLength()
	{
		//#CM664542
		// It is displayed when data qty is 0, [There was no object data]. 
		if (getLength() == 0)
		{
			return "MSG-9016";
		}

		//#CM664543
		// When you exceed the display maximum qty

		if (getLength() > MAXDISP)
		{
			String delim = MessageResource.DELIM;
			
			//#CM664544
			// Correspondeds to {0}. Narrow the search condition so that the number of cases may exceed {1}. 
			return "MSG-9017" + delim + WmsFormatter.getNumFormat(getLength()) + delim + WmsFormatter.getNumFormat(MAXDISP);
		}

		return null;
	}

	//#CM664545
	/**
	 * Data display No is obtained based on Settlement qty of present display. 
	 * @return Data display No
	 */
	public int getCurrent()
	{
		return wStartpoint;
	}

	//#CM664546
	/**
	 * Because the data number becomes a fraction as for the last Page of Retrieval result occasionally<BR>
	 * If the fraction is not maintained, Data display No becomes amusing. 
	 * @return Fraction
	 */
	public int getFraction()
	{
		return wFraction;
	}

	//#CM664547
	/**
	 * Set the position (line) where Retrieval result is displayed. <BR>
	 * <PRE>
	 * For ex. ItemSearchRet.jsp
	 * // The position (line) where Retrieval result is displayed is acquired. 
	 * String scrtrans     = request.getParameter("SCRTRANS");
	 *
	 * // SessionItemRet Acquisition
	 * SessionItemRet itemret = (SessionItemRet)session.getAttribute( "ItemRet" );
	 * itemret.setScreenTrans(scrtrans);
	 * </PRE>
	 * @param scrtrans ***Value in which Acquisition is done on Ret.jsp screen("SCRTRANS" which is request parameter)
	 */
	/**
	 * Set the position (line) where Retrieval result is displayed. <BR>
	 * <PRE>
	 * For ex. ItemSearchRet.jsp
	 * // The position (line) where Retrieval result is displayed is acquired. 
	 * String scrtrans     = request.getParameter("SCRTRANS");
	 *
	 * // SessionItemRet Acquisition
	 * SessionItemRet itemret = (SessionItemRet)session.getAttribute( "ItemRet" );
	 * itemret.setScreenTrans(scrtrans);
	 * </PRE>
	 * @param scrtrans ***Value in which Acquisition is done on Ret.jsp screen("SCRTRANS" which is request parameter)
	 */
	public void setScreenTrans(String scrtrans)
	{
		try
		{
			if (wCurrent == 0)
			{
				wStartpoint = 0;
			}
			else
			{
				wStartpoint = Integer.parseInt(scrtrans);
			}
			wEndpoint = wStartpoint + wCondition;
		}
		catch (Exception e)
		{
		}
	}

	//#CM664548
	/**
	 * Set the position (line) according to the kind of the button pushed with ListBox Where Retrieval result is displayed.  <BR>
	 * <Kind of button action><BR>
	 * first   To the first Page<BR>
	 * prvious To the previous Page<BR>
	 * next    To the next Page<BR>
	 * last    To the last Page<BR>
	 * @param process Kind of button action pushed on ListBox screen
	 */
	public void setActionName(String process)
	{
		try
		{
			//#CM664549
			// Pull wCondition from Retrieval result when the remainder is 0. 
			int remainder = getLength() % wCondition;
			if (remainder == 0)
			{
				remainder = wCondition;
			}
			int end = getLength() - remainder;
			//#CM664550
			// When final Page is displayed by displaying [Previous] or [Next] "Fraction" is added. 
			int back = wEndpoint - (wCondition * 2);
			if (wCurrent == getLength())
			{
				back = wEndpoint - (wCondition + getFraction());
			}

			if (process.equals("first"))
			{
				wStartpoint = 0;
			}
			else if (process.equals("previous"))
			{
				wStartpoint = back;
			}
			else if (process.equals("next"))
			{
				wStartpoint = wEndpoint;
			}
			else if (process.equals("last"))
			{
				wStartpoint = end;

			}

			wEndpoint = wStartpoint + wCondition;

			//#CM664551
			// Fraction calculation of Display end position
			if (wEndpoint >= getLength())
			{
				//#CM664552
				// Fraction calculation
				wFraction = getLength() - (wEndpoint - wCondition);

				wEndpoint = getLength();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//#CM664553
	/**
	 * The content of the message to notify the number of LISTBOX Retrieval result upper bounds to have been exceeded. 
	 * @return LISTBOX Retrieval Content of message to notify number of result upper bounds to have been exceeded
	 */
	public String getMaxDispInfo()
	{
		Object[] fmtObj = new Object[2];
		fmtObj[0] = Integer.toString(getLength());
		fmtObj[1] = Integer.toString(WmsParam.MAX_NUMBER_OF_DISP_LISTBOX);

		return SimpleFormat.format(DisplayText.getText("MAXDISPINFO_TEXT"), fmtObj);
	}

	//#CM664554
	// Package methods -----------------------------------------------

	//#CM664555
	// Protected methods ---------------------------------------------

	//#CM664556
	// Private methods -----------------------------------------------
}
//#CM664557
//end of class
