// $Id: ToolTipHelper.java,v 1.2 2006/11/07 06:53:03 suresh Exp $

package jp.co.daifuku.wms.base.display.web;
import java.util.ArrayList;
import java.util.Iterator;

import jp.co.daifuku.Constants;

//#CM664817
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM664818
/**
 * The class to make the character string set in ToolTip. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/26</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:53:03 $
 * @author  $Author: suresh $
 */
public class ToolTipHelper implements Constants
{
	//#CM664819
	// Class fields --------------------------------------------------


	//#CM664820
	// Class variables -----------------------------------------------
	//#CM664821
	/** 
	 * List of value of ToolTip
	 */
	private ArrayList wList = new ArrayList();

	//#CM664822
	// Class method --------------------------------------------------
	//#CM664823
	/** 
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:53:03 $") ;
	}
	//#CM664824
	// Constructors --------------------------------------------------
	//#CM664825
	/** 
	 * Constructor
	 */
	public ToolTipHelper()
	{
	}
	//#CM664826
	// Public methods ------------------------------------------------
	//#CM664827
	/** 
	 * Add the value in ToolTip.
	 * @param title Title
	 * @param value Value of ToolTip
	 */
	public void add(String title, String value)
	{
		wList.add(new ToolTipData(title, value));
	}

	//#CM664828
	/** 
	 * Add the value in ToolTip.
	 * @param title Title
	 * @param value Value of ToolTip(int type)
	 */
	public void add(String title, int value)
	{
		add(title, Integer.toString(value));
	}

	
	//#CM664829
	/** 
	 * Acquires the character string set in ToolTip. 
	 * @return Character string of ToolTip
	 */
	public String getText()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(LINE_FEED);

		Iterator itr = wList.iterator();
		while(itr.hasNext())
		{
			ToolTipData data = (ToolTipData)itr.next();
			sb.append(data.wTitle).append(TOOLTIP_DELIM).append(data.wValue).append(LINE_FEED);
		}
		return sb.toString();
	}
	
	class ToolTipData
	{
		public String wTitle = "";
		public String wValue = "";

		public ToolTipData(String title, String value)
		{
			wTitle = title;
			wValue = value;
			
			//#CM664830
			// For the character where FTTB ["] is included
		    //#CM664831
		    //      Because it is considered that the character ended by ["] and displays the character only even before ["]
		    //#CM664832
		    //      Replaces the corresponding value in [&quot;]
		    //#CM664833
		    //       Need not this processing if corresponding on the Frame work side. 
		    wValue = value.replaceAll("\"", "&quot;");

		}
	}
}
