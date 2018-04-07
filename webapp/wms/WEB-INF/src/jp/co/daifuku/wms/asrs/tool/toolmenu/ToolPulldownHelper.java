package jp.co.daifuku.wms.asrs.tool.toolmenu;


import java.util.StringTokenizer;

import jp.co.daifuku.bluedog.ui.control.LinkedPullDown;
import jp.co.daifuku.bluedog.ui.control.LinkedPullDownItem;
import jp.co.daifuku.bluedog.ui.control.PullDown;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;

// $Id: ToolPulldownHelper.java,v 1.2 2006/10/30 04:04:21 suresh Exp $

//#CM54618
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM54619
/** <en>
 *The method to set the data made in the ToolPulldownData class to the PullDown control 
 *of BlueDOG is mounted.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:04:21 $
 * @author  $Author: suresh $
 </en> */
public class ToolPulldownHelper
{
	//#CM54620
	// Class fields --------------------------------------------------

	//#CM54621
	// Class variables -----------------------------------------------
	//#CM54622
	// Class method --------------------------------------------------
	//#CM54623
	/** <en>
	 * Return version of this Class.
	 * @return version of this Class and Updated date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 04:04:21 $") ;
	}

	//#CM54624
	// Constructors --------------------------------------------------


	//#CM54625
	// Public methods ------------------------------------------------
	//#CM54626
	/** <en>
	 * It is the method to set the data acquired with PulldownData to the PullDown control.
	 * @param pull PullDown control.
	 * @param data  PulldownData
	 </en>*/
	public static void setPullDown(PullDown pull, String[] data)
	{
		for(int i = 0; i < data.length; i++)
		{
			pull.addItem(getPullData(data[i]));
		}
	}
	
	//#CM54627
	/** <en>
	 * It is the method to set the data acquired with PulldownData to the LinkedPullDown control.
	 * @param pull LinkedPullDown control.
	 * @param data  PulldownData
	 </en>*/
	public static void setLinkedPullDown(LinkedPullDown pull, String[] data)
	{
		for(int i = 0; i < data.length; i++)
		{
			pull.addItem(getLinkPullData(data[i]));
		}
	}

	//#CM54628
	// Package methods -----------------------------------------------

	//#CM54629
	// Protected methods ---------------------------------------------

	//#CM54630
	// Private methods -----------------------------------------------
	private static PullDownItem getPullData(String pullData)
	{
		PullDownItem item = new PullDownItem();
		StringTokenizer stk = new StringTokenizer(pullData, ",", false) ;
		int count = 0;
		while ( stk.hasMoreTokens() )
		{
			String value = (String)stk.nextToken().trim();
			if(count == 0)	item.setValue(value);
			if(count == 1)	item.setText(value);
			if(count==2){ 

			}
			if(count == 3)
			{
				if(value.equals("0")){
					item.setSelected(false);
				}
				else{
					item.setSelected(true);
				}
			}
			count++;
		}
		return item;
	}	
	
	
	private static LinkedPullDownItem getLinkPullData(String pullData)
	{
		LinkedPullDownItem item = new LinkedPullDownItem();
		StringTokenizer stk = new StringTokenizer(pullData, ",", false) ;
		int count = 0;
		while ( stk.hasMoreTokens() )
		{
			String value = (String)stk.nextToken().trim();
			if(count == 0)	item.setValue(value);
			if(count == 1)	item.setText(value);
			if(count==2)
			{ 
				item.setForignKey(value);
			}
			if(count == 3)
			{
				if(value.equals("0"))
				{
					item.setSelected(false);
				}
				else
				{
					item.setSelected(true);
				}
			}
			count++;
		}
		
		return item;
	}
}
//#CM54631
//end of class

