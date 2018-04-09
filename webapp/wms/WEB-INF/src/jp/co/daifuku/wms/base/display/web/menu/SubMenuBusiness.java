// $Id: SubMenuBusiness.java,v 1.2 2006/11/07 06:21:12 suresh Exp $
//#CM664785
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.menu;
import jp.co.daifuku.bluedog.webapp.ActionEvent;


//#CM664786
/** 
 * The screen class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:21:12 $
 * @author  $Author: suresh $
  */
public class SubMenuBusiness extends SubMenu
{
	//#CM664787
	// Class fields --------------------------------------------------

	//#CM664788
	// Class variables -----------------------------------------------

	//#CM664789
	// Class method --------------------------------------------------

	//#CM664790
	// Constructors --------------------------------------------------

	//#CM664791
	// Public methods ------------------------------------------------

	//#CM664792
	/** 
	 * Initialize the screen. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		httpRequest.setAttribute("id", httpRequest.getParameter("id"));
	}

	//#CM664793
	// Package methods -----------------------------------------------

	//#CM664794
	// Protected methods ---------------------------------------------

	//#CM664795
	// Private methods -----------------------------------------------

	//#CM664796
	// Event handler methods -----------------------------------------
}
//#CM664797
//end of class
