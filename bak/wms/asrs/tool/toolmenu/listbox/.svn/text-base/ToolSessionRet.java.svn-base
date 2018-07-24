// $Id: ToolSessionRet.java,v 1.2 2006/10/30 04:58:34 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox ;

//#CM53320
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolDatabaseFinder;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM53321
/**<en>
 * This class conducts search and preserves results.
 * This class supports the display of list box; class such as ToolSessionStationRet needs to
 * be created by inheriting this class.<BR>
 * Instance of ToolDatabaseFinder will not be generated in this class.<BR>
 * Please let the screen (JSP) preserve the instance of ToolSession*** class generated in session,
 * then please remove the session after use of this class.<BR>
 * Please do not use 'find' in Handler class as a search method.<BR>
 * It leads to inferior performance since 'find' method will generates instances of all the data
 * from search result.<BR>
 * Please always use Finder class so that only as much instances as requried will be generated.<BR>
 * When creating aaa new list box, it is necessary to create the ToolEntity, ToolFinder, ToolSession***
 * class, ***Info.jsp and ***Ret.jsp.<BR>
 * Please see JSP reference for detailed information about the creation.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/03/01</TD><TD>sawa</TD><TD><code>ToolDatabaseFinder</code> variable added.
 *                                         but not for sub classes derived from <code>ToolSessionRet</code>.</TD></TR>
 * <TR><TD>2002/07/24</TD><TD>sawa</TD><TD>getLengthInfo(HttpServletRequest request) method added.
 *                                         For ***Ret.jsp, please use the added method, 
 *                                         not the getLengthInfo(String[] searchret_text).</TD></TR>
 * <TR><TD>2002/08/13</TD><TD>sawa</TD><TD>upper limit of LISTBOX search results added.</TD></TR>
 * <TR><TD>2003/01/29</TD><TD>miyashita</TD><TD>getFinder() method is added.
 *                                         Therefore getFinder() was deleted from the class derived from ToolSessionRet.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:58:34 $
 * @author  $Author: suresh $
 </en>*/
public class ToolSessionRet
{
	//#CM53322
	// Class fields --------------------------------------------------
	//#CM53323
	/**<en>
	 * LISTBOX uppoer limit of search results
	 </en>*/
//#CM53324
//	public static final int MAXDISP = Integer.parseInt(AwcParam.getParam("MAX_NUMBER_OF_DISP_LISTBOX"));
	public static final int MAXDISP = 10000;
	//#CM53325
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public static String wDelim = MessageResource.DELIM ;


	//#CM53326
	// Class variables -----------------------------------------------
	//#CM53327
	/**<en>
	 * Preparation for LogWrite
	 * This is used when writing the log for the detail of StackTrace.
	 </en>*/
    protected StringWriter      sw           = new StringWriter();

	//#CM53328
	/**<en>
	 * Preparation for LogWrite
	 * This is used when writing the log for the detail of StackTrace.
	 </en>*/
	protected PrintWriter       pw           = new PrintWriter(sw);

	//#CM53329
	/**<en>
	 * Preparation for LogWrite
	 * This is used when writing the log for the detail of StackTrace.
	 </en>*/
    protected String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");

	//#CM53330
	/**<en>
	 * <code>Connection</code>
	 </en>*/
	protected Connection wConn;

	//#CM53331
	/**<en>
	 * Preserve the <code>ToolDatabaseFinder</code> reference. <BR>
	 * All the classes which inherit this <code>ToolSessionRet</code> currently own reference of Finder
	 * of each class. In future the system will be corrected so that the variables will be casted and 
	 * respective classes will preserve sthem.
	 </en>*/
	protected ToolDatabaseFinder wFinder;

	//#CM53332
	/**<en>
	 * Conditions for the display
	 * Retrieve teh number of list boxes to display from system deficnition.
	 </en>*/
//#CM53333
//	protected int wCondition  = Integer.parseInt(AwcParam.getParam("LISTBOX_SEARCH_COUNT"));
	protected int wCondition  = 100;

	//#CM53334
	/**<en>
	 * If a data is selected in search result, screen will jump to this specified page.
	 </en>*/
	protected String wNextPage;

	//#CM53335
	/**<en>
	 *If a data is selected in search result, screen will jump to this specified frame.
	 </en>*/
	protected String wFrameName;

	//#CM53336
	/**<en>
	 * Displays the address for the original page where search list was called.
	 </en>*/
	protected String wBasePage;

	//#CM53337
	/**<en>
	 * number of search results
	 </en>*/
	protected int wLength;

	//#CM53338
	/**<en>
	 * Current number of displayed data
	 </en>*/
	protected int wCurrent;

	//#CM53339
	/**<en>
	 * Start index of data to dispaly
	 </en>*/
	protected int wStartpoint ;

	//#CM53340
	/**<en>
	 * End index of data to dispaly
	 </en>*/
	protected int wEndpoint ;

	//#CM53341
	/**<en>
	 * Fractional data in final page.
	 </en>*/
	protected int wFraction ;

	//#CM53342
	// Class method --------------------------------------------------
	//#CM53343
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 04:58:34 $") ;
	}

	public void closeConnection() throws Exception
	{
		if (wConn != null) wConn.close();
	}
	
	//#CM53344
	// Constructors --------------------------------------------------
	//#CM53345
	/**<en>
	 * Constructs new <CODE>ToolSessionRet</CODE>.
	 </en>*/
	public ToolSessionRet()
	{
	}

	//#CM53346
	// Public methods ------------------------------------------------
	//#CM53347
	/**<en>
	 * Return the condition for data display.
	 * @return wCondition
	 </en>*/
	public int getCondition()
	{
		return wCondition ;
	}
	//#CM53348
	/**<en>
	 * Return the ToolDatabaseFinder instance.
	 * @return wFinder
	 </en>*/
	public ToolDatabaseFinder getFinder()
	{
		return wFinder;
	}
	//#CM53349
	/**<en>
	 * Specified page will be retrieved.
	 * @return wNextPage
	 </en>*/
	public String getNextPage()
	{
		return wNextPage;
	}

	//#CM53350
	/**<en>
	 * Specified frame will be retrieved.
	 * @return wFrameName
	 </en>*/
	public String getFrameName()
	{
		return wFrameName;
	}

	//#CM53351
	/**<en>
	 * Display the original searched page.
	 * @return wBasePage
	 </en>*/
	public String getBasePage()
	{
		return wBasePage;
	}

	//#CM53352
	/**<en>
	 * Numebr of data display will be retrieved.
	 * @return wLength
	 </en>*/
	public int getLength()
	{
		return wLength;
	}

	//#CM53353
	/**<en>
	 * End index of data will be retrieved.
	 * @return wLength
	 </en>*/
	public int getEnd()
	{
		return wEndpoint;
	}

	//#CM53354
	/**<en>
	 * Number of data display will be retrieved.
	 * @param searchret_text :text which describes the current position of list box
	 * @return :ex. displays 21st-30th data of 99 search results 
	 </en>*/
	public String getLengthInfo(String[] searchret_text)
	{
		if (getLength() == 0)
		{
			return "";
		}

		String msg = searchret_text[0] + getLength() 
				   + searchret_text[1] + ( getCurrent() + 1 )
				   + "-"               + ( wEndpoint )
				   + searchret_text[2];
		return msg;
	}

	//#CM53355
	/**<en>
	 * Number of data display will be retrieved.
	 * @param request <CODE>HttpServletRequest</CODE>
	 * @return :ex. displays 21st-30th data of 99 search results 
	 </en>*/
	public String getLengthInfo(HttpServletRequest request)
	{
		//#CM53356
		//<en> If data exceeded the max number of data display,</en>
		if ( getLength() > MAXDISP )
		{
			return getMaxDispInfo(request);
		}

		//#CM53357
		//<en> If there was no data, it displays 'there was no target data.'</en>
		if ( getLength() == 0 )
		{
			MessageResource mr = new MessageResource(request.getLocale());
			return mr.getMessage(6123001, null);
		}

		String[] srhret	= {
							DisplayText.getText("DISP_SRHRET1_TEXT"),
							DisplayText.getText("DISP_SRHRET2_TEXT"),
							DisplayText.getText("DISP_SRHRET3_TEXT")
						  };

		return getLengthInfo(srhret);
	}

	//#CM53358
	/**<en>
	 * Check the number of search results.<BR>
	 * It returns the message no. in case the check error occurred. 
	 * If checked normally, it will return null.<BR>
	 * < detail of the check ><BR>
	 * whether/not there was no data.<BR>
	 * whether/not the search results exceeded the max number of data display.<BR>
	 * @return :message no.
	 </en>*/
	public String checkLength()
	{

		//#CM53359
		//<en> If there was no data, it displays 'there was no target data.'</en>
		if ( getLength() == 0 )
		{
			return "6123001";
		}


		//#CM53360
		//<en> If the data exceeded the max number of data display,</en>
		if ( getLength() > MAXDISP )
		{
			String delim = MessageResource.DELIM ;
			//#CM53361
			//<en> Corresponding data {0}. Please narrow the search as number of data exceeds {1}.</en>
			return "6123114" + delim + getLength() + delim + MAXDISP;
		}

		return null;
	}

	//#CM53362
	/**<en>
	 * Number of data display will be retrieved based on current number of displayed data.
	 * @return :number of data display
	 </en>*/
	public int getCurrent()
	{
		return wStartpoint;
	}

	//#CM53363
	/**<en>
	 * The last page of search results may show fractional numbers of data; 
	 * it is necessary that fraction should be preserved, or incorrect number of data display 
	 * may be provided.
	 * @return :fractional number 
	 </en>*/
	public int getFraction()
	{
		return wFraction;
	}

	//#CM53364
	/**<en>
	 * Set the index(line)of search results to display. <BR>
	 * <PRE>
	 * ex. ItemSearchRet.jsp
	 * Retrieve the index(line) of search results to display. 
	 * String scrtrans     = request.getParameter("SCRTRANS");
	 * 
	 * Retrieve SessionItemRet.
	 * SessionItemRet itemret = (SessionItemRet)session.getAttribute( "ItemRet" );
	 * itemret.setScreenTrans(scrtrans);
	 * </PRE>
	 * @param scrtrans :the value retrieved via ***Ret.jsp screen ("SCRTRANS" which is a request parameter)
	 </en>*/
	public void setScreenTrans(String scrtrans)
	{
		try
		{
			if ( wCurrent == 0 )
			{
				wStartpoint = 0;
			}
			else
			{
				wStartpoint = Integer.parseInt(scrtrans);
			}
			wEndpoint   = wStartpoint + wCondition;
		}
		catch (Exception e)
		{
		}
	}

	//#CM53365
	/**<en>
	 * Set the index(line) of search results to display according to the button type
	 * pressed in ListBox.<BR>
	 * < type of button actions > <BR>
	 * first   :to the first page<BR>
	 * prvious :to the previous pate<BR>
	 * next    :to the next page<BR>
	 * last    :to the last page<BR>
	 * @param process :type of button action pressed on ListBox screen.
	 </en>*/
	public void setActionName(String process)
	{
		try
		{
			//#CM53366
			//<en> Subtract 'wCondition' from the search result in case the remainder is 0.</en>
			int remainder    = getLength() %  wCondition;
			if ( remainder == 0 )
			{
				remainder = wCondition;
			}
			int end    = getLength() - remainder;
			//#CM53367
			//<en> Add fractional number if displaying the last page by indicating 'to previous' </en>
			//<en> and 'to next'.</en>
			int back   = wEndpoint - (wCondition * 2);
			if ( wCurrent == getLength() )
			{
				back = wEndpoint - (wCondition + getFraction());
			}

			if ( process.equals("first"))
			{
				wStartpoint = 0;
			}
			else if ( process.equals("previous"))
			{
				//#CM53368
				//<en> Let no data less than 0 display by indicating 'to previous'.</en>
				if (back > 0 )
					wStartpoint = back;
				else
					wStartpoint = 0;
			}
			else if ( process.equals("next"))
			{
				//#CM53369
				//<en> Let no data greater than max value display by indicating 'to next'.</en>
				if (wEndpoint < getLength())
				{
					wStartpoint = wEndpoint;
				}
			}
			else if ( process.equals("last"))
			{
				wStartpoint = end;
			}
			
			wEndpoint   = wStartpoint + wCondition;

		}
		catch (Exception e)
		{
e.printStackTrace();
		}
	}

	//#CM53370
	/**<en>
	 * Return the String which contains the Html tag for 'to previous' and 'to next' indications
	 * on search result screen.
	 * @param  html      :file name to display
	 * @param  back_text :'to previous'
	 * @param  next_text :'to next'
	 * @return           :string which contains Html tag
	 </en>*/
	public String getDispInfo(String html, String back_text, String next_text)
	{
		String space     = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		String minispace = "&nbsp;&nbsp;";
		int next_trans   = wEndpoint;
		int back_trans   = wEndpoint - (wCondition * 2);
		int start_trans  = 0;

		//#CM53371
		//<en> Subtract 'wCondition' from the search result in case the remainder is 0.</en>
		int remainder    = getLength() %  wCondition;
		if ( remainder == 0 )
		{
			remainder = wCondition;
		}
		int end_trans    = getLength() - remainder;

		//#CM53372
		//<en> Add fractional number if displaying the last page by indicating 'to previous' </en>
		//<en> and 'to next'.</en>
		if ( wCurrent == getLength() )
		{
			back_trans = wEndpoint - (wCondition + getFraction());
		}

		//#CM53373
		//<en> 'top page'</en>
		String start_info = space + "<A HREF = \"" + html + "?SCRTRANS=" + 
					           start_trans + "\">" + "&lt;&lt;" + "</A>"  + minispace;
		//#CM53374
		//<en> 'to the previous page'</en>
		String back_info = minispace + "<A HREF = \"" + html + "?SCRTRANS=" + 
					           back_trans + "\">" + back_text + "</A>"  + space;
		//#CM53375
		//<en> 'to the next page'</en>
		String next_info = space + "<A HREF = \"" + html + "?SCRTRANS=" + 
					           next_trans + "\">" + next_text + "</A>"  + minispace;
		//#CM53376
		//<en> 'last page'</en>
		String end_info  = minispace + "<A HREF = \"" + html + "?SCRTRANS=" + 
					           end_trans + "\">" + "&gt;&gt;" + "</A>"  + space;

		//#CM53377
		//<en> In case there was no search result, or in case the data display exceeds the search results,</en>
		//#CM53378
		//<en> let no display of 'to previous' and 'to next'.</en>
		if ( getLength() == 0 || wCondition >= getLength())
		{
			return "";
		}

		//#CM53379
		//<en> 'to the previous' should not appear when there is no previous page to display.</en>
		//<en> ( Only 'to the next' should be displayed in the beginning of the display of search result.)</en>
		if ( wCurrent == wCondition )
		{
			return (next_info + end_info);
		}
		//#CM53380
		//<en> 'to the next' should not appear when there is no next page to display.</en>
		//<en> (Only 'to previous' should be displayed at the last page.)</en>
		else if ( wCurrent == getLength() )
		{
			return  (start_info + back_info);
		}
		else
		{
			return (start_info + back_info + next_info + end_info);
		}
	}

	//#CM53381
	/**<en>
	 * Return the String which contains the Html tag for 'to previous' and 'to next' indications
	 * on search result screen.
	 * @param  request <CODE>HttpServletRequest</CODE>
	 * @return  :hyperlink string which contains 'back'.
	 </en>*/
	public String getDispInfo(HttpServletRequest request)
	{
		return getDispInfo(request, "BACK_TEXT", getBasePage(), getFrameName());
	}

	//#CM53382
	/**<en>
	 * Return the String which contains the Html tag for 'to previous' and 'to next' indications
	 * on search result screen.
	 * @param  request <CODE>HttpServletRequest</CODE>
	 * @param  cancelname :string used in hyperlink such as 'back' (close)
	 * @param  page       :name of the screen where the hyperlink jumps to
	 * @param  frame      :name of the frame where the hyperlink jumps to
	 * @return :hyperlink string which contains 'back'(close).
	 </en>*/
	public String getDispInfo(HttpServletRequest request, String cancelname, String page, String frame)
	{
		String space     = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		String minispace = "&nbsp;&nbsp;";
		String html      = request.getRequestURI();
		int next_trans   = wEndpoint;
		int back_trans   = wEndpoint - (wCondition * 2);
		int start_trans  = 0;

		//#CM53383
		//<en> Subtract wCondition from search result when remainder is 0.</en>
		int remainder    = getLength() %  wCondition;
		if ( remainder == 0 )
		{
			remainder = wCondition;
		}
		int end_trans    = getLength() - remainder;

		//#CM53384
		//<en> Add fractional number when displaying the last page by indicating 'to previous'</en>
		//<en> and 'to next'.</en>
		if ( wCurrent == getLength() )
		{
			back_trans = wEndpoint - (wCondition + getFraction());
		}

		//#CM53385
		//<en> 'top page'</en>
		String start_info = space + "<A HREF = \"" + html + "?SCRTRANS=" + 
					           start_trans + "\">" + "&lt;&lt;" + "</A>"  + minispace;
		//#CM53386
		//<en> 'to previous'</en>
		String back_info = minispace + "<A HREF = \"" + html + "?SCRTRANS=" + 
					           back_trans + "\">" + DisplayText.getText("BACK_ARW_TEXT") + "</A>"  + space;

		//#CM53387
		//<en> 'back'</en>
		String cancel_info = getCancelInfo(request, cancelname, page, frame);

		//#CM53388
		//<en> 'to next'</en>
		String next_info = space + "<A HREF = \"" + html + "?SCRTRANS=" + 
					           next_trans + "\">" + DisplayText.getText("NEXT_ARW_TEXT") + "</A>"  + minispace;
		//#CM53389
		//<en> 'last page'</en>
		String end_info  = minispace + "<A HREF = \"" + html + "?SCRTRANS=" + 
					           end_trans + "\">" + "&gt;&gt;" + "</A>"  + space;

		//#CM53390
		//<en> If the data exceeded the max number of data display,</en>
		if ( getLength() > MAXDISP )
		{
			return cancel_info;
		}

		//#CM53391
		//<en> If there is no search result, or if the max data display exceeded the search results,</en>
		//#CM53392
		//<en> let no display of 'to previous' or 'to next'.</en>
		if ( getLength() == 0 || wCondition >= getLength())
		{
			return cancel_info;
		}

		//#CM53393
		//<en> Let no 'to previous' appear at the first display of 'to previous' and 'to next'.</en>
		//<en> (as there is no previous page.)</en>
		if ( wCurrent == wCondition )
		{
			return (space + space + space + cancel_info + next_info + end_info);
		}
		//#CM53394
		//<en> Let no 'to next' appear when displaying the last page.</en>
		//<en> (as there is no next page.)</en>
		else if ( wCurrent == getLength() )
		{
			return  (start_info + back_info + cancel_info + space + space + space);
		}
		else
		{
			return (start_info + back_info + cancel_info + next_info + end_info);
		}
	}

	//#CM53395
	/**<en>
	 * This is the contents of the notification message when data exceeded the uppoer limit of 
	 * LISTBOX search results.
	 * @param  request    <CODE>HttpServletRequest</CODE>
	 * @return LISTBOX :the contents of the notification message when data exceeded the uppoer 
	 * limit of LISTBOX search results.
	 </en>*/
	public String getMaxDispInfo(HttpServletRequest request)
	{
		Object[] fmtObj   = new Object[2];
		fmtObj[0] = Integer.toString(getLength());
//#CM53396
//		fmtObj[1] = AwcParam.getParam("MAX_NUMBER_OF_DISP_LISTBOX");
		fmtObj[1] = Integer.toString(MAXDISP);


		return SimpleFormat.format(DisplayText.getText("MAXDISPINFO_TEXT"), fmtObj);
	}

	//#CM53397
	// Package methods -----------------------------------------------

	//#CM53398
	// Protected methods ---------------------------------------------

	//#CM53399
	// Private methods -----------------------------------------------
	//#CM53400
	/**<en>
	 * Return the String which contains the Html tag for 'to previous' and 'to next' indications
	 * on search result screen.
	 * @param  request    <CODE>HttpServletRequest</CODE>
	 * @param  cancelname :string used in hyperlink such as back(close)
	 * @param  page       :name of the screen whehre it jumps to by the hyperlink
	 * @param  frame      :name of the frame it jumps to by the hyperlink
	 * @return            :Back(close) hyperlink string that contains Html tag
	 </en>*/
	private String getCancelInfo(HttpServletRequest request, String cancelname, String page, String frame)
	{
		String linkname = DisplayText.getText(cancelname);
		return ("<A HREF=\"javascript:window.open('" + page + "','" + frame + "')\">" + linkname + "</A>");
	}
}
//#CM53401
//end of class
