// $Id: ToolMenuDisplay.java,v 1.2 2006/10/30 01:40:54 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.common ;

//#CM46766
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;


//#CM46767
/**<en>
 * This is an object which preserves the contents of sub-menue display.
 * It refers to the AWCMenu table and MenuText, then set the displaying items of the 
 * session when a user logs in.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/03/12</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:40:54 $
 * @author  $Author: suresh $
 </en>*/
public class ToolMenuDisplay extends Object
{
	//#CM46768
	// Class fields --------------------------------------------------

	//#CM46769
	// Class variables -----------------------------------------------

	//#CM46770
	/**<en>
	 * function no.
	 </en>*/
	protected String wFuncNumber ="";
	//#CM46771
	/**<en>
	 * file path
	 </en>*/
	protected String wPath ="";
	//#CM46772
	/**<en>
	 * button no.
	 </en>*/
	protected Vector wBtnVec = null;
	//#CM46773
	/**<en>
	 * file path
	 </en>*/
	protected Vector wPathVec = null;
	//#CM46774
	/**<en>
	 * name of the frame
	 </en>*/
	protected Vector wFrameVec = null;

	//#CM46775
	// Class method --------------------------------------------------
	//#CM46776
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:40:54 $") ;
	}

	//#CM46777
	// Constructors --------------------------------------------------
	//#CM46778
	/**<en>
	 * Use this constructor.
	 * @param conn <CODE>Connection</CODE>
	 </en>*/
	public ToolMenuDisplay()
	{
		wBtnVec = new Vector();
		wPathVec = new Vector();
		wFrameVec = new Vector();
	}

	//#CM46779
	// Public methods ------------------------------------------------


	//#CM46780
	/**<en>
	 * Set the function no.
	 * @param  func
	 </en>*/
	public void setFuncNumber(String  func)
	{
		wFuncNumber = func ;
	}
	
	//#CM46781
	/**<en>
	 * Get the function no.
	 * @return  wFuncNumber
	 </en>*/
	public String getFuncNumber()
	{
		return wFuncNumber ;
	}
	
	//#CM46782
	/**<en>
	 * Set the file path.
	 * @param  path
	 </en>*/
	public void setFilePath(String  path)
	{
		wPath = path ;
	}
	
	//#CM46783
	/**<en>
	 * Get the file path.
	 * @return  wPath
	 </en>*/
	public String getFilePath()
	{
		return wPath ;
	}

	//#CM46784
	/**<en>
	 * Get the button no.
	 * @return  btnarray
	 </en>*/
	public String[] getButtonArray()
	{
		//#CM46785
		//<en> The number of the buttons is enclosed in 1st [].</en>
		//#CM46786
		//<en> Button key, path, [2]   Delimiter:MessageResource.DELIM</en>
		String []btnarray = new String[wBtnVec.size()];
		String []patharray = new String[wBtnVec.size()];
		String []framearray = new String[wBtnVec.size()];
		wBtnVec.copyInto(btnarray);
		wPathVec.copyInto(patharray);
		wFrameVec.copyInto(framearray);
		Vector vec = new Vector();
		for (int i = 0 ; i < btnarray.length ; i ++)
		{
			vec.addElement(btnarray[i]+MessageResource.DELIM+patharray[i]+MessageResource.DELIM+framearray[i]);
		}
		String st[] = new String[vec.size()];
		vec.copyInto(st);
		return st;
	}

	//#CM46787
	/**<en>
	 * Get the button no.
	 * @param  btn
	 </en>*/
	public void addButton(String btn,String path, String frame)
	{
		wBtnVec.addElement(btn);
		wPathVec.addElement(path);
		wFrameVec.addElement(frame);
	}
	//#CM46788
	// Package methods -----------------------------------------------

	//#CM46789
	// Protected methods ---------------------------------------------

	//#CM46790
	// Private methods -----------------------------------------------

}
//#CM46791
//end of class
