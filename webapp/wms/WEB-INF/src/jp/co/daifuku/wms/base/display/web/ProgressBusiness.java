package jp.co.daifuku.wms.base.display.web;
import jp.co.daifuku.bluedog.webapp.ActionEvent;

//#CM664799
/** 
 * The screen class while processing.<br>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:56:12 $
 * @author  $Author: suresh $
 */
public class ProgressBusiness extends Progress
{
	
	//#CM664800
	/**
	 * It is called before the call of each control event.  <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{  
		httpRequest.setAttribute(jp.co.daifuku.bluedog.webapp.Constants.KEY_FOCUS_TAG_SUPPORT, null);
	}
	public void page_Load(ActionEvent e) throws Exception
	{

	}
	public void msg_Server(ActionEvent e) throws Exception
	{
	}


}
