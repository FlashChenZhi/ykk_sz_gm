package jp.co.daifuku.wms.base.display.web;

import java.util.StringTokenizer;

import jp.co.daifuku.bluedog.ui.control.LinkedPullDown;
import jp.co.daifuku.bluedog.ui.control.LinkedPullDownItem;
import jp.co.daifuku.bluedog.ui.control.PullDown;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;

// $Id: PulldownHelper.java,v 1.2 2006/11/07 06:53:04 suresh Exp $

//#CM664801
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM664802
/** 
 * Call the method for the setting of the data made in the PulldownData 
 * class in the PullDown control of BlueDOG. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/04</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:53:04 $
 * @author  $Author: suresh $
  */
public class PulldownHelper
{
	//#CM664803
	// Class fields --------------------------------------------------

	//#CM664804
	// Class variables -----------------------------------------------
	//#CM664805
	// Class method --------------------------------------------------
	//#CM664806
	/** 
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:53:04 $") ;
	}

	//#CM664807
	// Constructors --------------------------------------------------


	//#CM664808
	// Public methods ------------------------------------------------
	//#CM664809
	/** 
	 * The method for the setting of data to which Acquisition is done with PulldownData 
	 * in the PullDown control. 
	 * @param pull Pulldown Control
	 * @param data Value to be set in Pulldown
	 */
	public static void setPullDown(PullDown pull, String[] data)
	{
		for(int i = 0; i < data.length; i++)
		{
			pull.addItem(getPullData(data[i]));
		}
		
	}
	//#CM664810
	/** 
	 * The method for the setting of data to which Acquisition is done with PulldownData in 
	 * the LinkedPullDown control. 
	 * @param pull LinkPulldown Control
	 * @param data Data to be set in Pulldown
	 */
	public static void setLinkedPullDown(LinkedPullDown pull, String[] data)
	{
		for(int i = 0; i < data.length; i++)
		{
			pull.addItem(getLinkPullData(data[i]));
		}
	}



	//#CM664811
	// Package methods -----------------------------------------------

	//#CM664812
	// Protected methods ---------------------------------------------

	//#CM664813
	// Private methods -----------------------------------------------
	//#CM664814
	/** 
	 * Acquire Data to be set in Pulldown with the PullDownItem object. 
	 * @param pullData Data to be set in Pulldown
	 * @return PullDownItem Object
	 */
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
	
	
	//#CM664815
	/** 
	 * Acquire Data to be set in Pulldown with the LinkedPullDownItem object. 
	 * @param pullData Data to be set in Pulldown
	 * @return LinkedPullDownItem Object
	 */
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
			if(count==2){ 
				item.setForignKey(value);
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

}
//#CM664816
//end of class

