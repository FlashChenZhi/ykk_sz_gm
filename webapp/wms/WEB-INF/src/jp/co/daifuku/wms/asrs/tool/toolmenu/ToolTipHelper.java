// $Id: ToolTipHelper.java,v 1.2 2006/10/30 04:03:18 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.toolmenu ;

import java.util.ArrayList;
import java.util.Iterator;

import jp.co.daifuku.Constants;

//#CM54632
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM54633
/** <en>
 * It is the class to make a string to set on ToolTip.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>Kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:03:18 $
 * @author  $Author: suresh $
 </en> */
public class ToolTipHelper implements Constants
{
	//#CM54634
	// Class fields --------------------------------------------------


	//#CM54635
	// Class variables -----------------------------------------------
	private ArrayList wList = new ArrayList();

	//#CM54636
	// Class method --------------------------------------------------
	//#CM54637
	/** <en>
	 * Comment for field
	 * @return version of this Class and Updated date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 04:03:18 $") ;
	}
	//#CM54638
	// Constructors --------------------------------------------------
	//#CM54639
	/** <en>
	 * Constructor
	 </en>*/
	public ToolTipHelper()
	{
	}
	//#CM54640
	// Public methods ------------------------------------------------
	//#CM54641
	/** <en>
	 * Add data for ToolTip.
	 * @param title Title
	 * @param value Value of the ToolTip.
	 </en>*/
	public void add(String title, String value)
	{
		wList.add(new ToolTipData(title, value));
	}

	//#CM54642
	/** <en>
	 * Add data for ToolTip.
	 * @param title Title
	 * @param value Value of the ToolTip(int type).
	 </en>*/
	public void add(String title, int value)
	{
		add(title, Integer.toString(value));
	}

	
	//#CM54643
	/** <en>
	 * Return a string to set on ToolTip.
	 * @return string
	 </en>*/
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
	
	//#CM54644
	//InnerClass for data of tool tip.
	class ToolTipData
	{
		public String wTitle = "";
		public String wValue = "";

		public ToolTipData(String title, String value)
		{
			wTitle = title;
			wValue = value;
		}
	}
}
