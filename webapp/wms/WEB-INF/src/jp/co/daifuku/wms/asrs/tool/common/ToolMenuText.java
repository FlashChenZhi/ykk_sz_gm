// $Id: ToolMenuText.java,v 1.2 2006/10/30 01:40:54 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.common ;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
//#CM46792
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM46793
/**<en>
 * Defines the size and max. length of input tags of list boxes/labels, which will be displayed
 * on the screen for item code, etc., in the resource file and retrives via methods of this class.<BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/15</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:40:54 $
 * @author  $Author: suresh $
 </en>*/
public class ToolMenuText
{
	//#CM46794
	// Class fields --------------------------------------------------
	//#CM46795
	/**<en>
	 * Name of default resource file
	 </en>*/
	public static final String DEFAULT_RESOURCE = "MenuText" ;
	//#CM46796
	/**<en>
	 * Delimiter between the displaying item for screen and the data.
	 </en>*/
	private static final String SEPARATE = "@";
	//#CM46797
	/**<en>
	 * Delimiter of KEY. Default delimiter is "=".
	 </en>*/
	public static final String wSeparate = "=";
	//#CM46798
	// Class variables -----------------------------------------------
	//#CM46799
	/**<en>
	 * Preserve keys.
	 </en>*/
	protected String wKey = "" ;
	//#CM46800
	/**<en>
	 * Preserve the values.
	 </en>*/
	protected String wValue = "" ;
	//#CM46801
	/**<en>
	 * Preserve the order of display.
	 </en>*/
	protected String wDispNo = "" ;
	//#CM46802
	/**<en>
	 * Preserve the authority (administrator).
	 </en>*/
	protected String wAdmin = "" ;
	//#CM46803
	/**<en>
	 * Preserve the autority (maintenance conductor)
	 </en>*/
	protected String wMaintenance = "" ;
	//#CM46804
	/**<en>
	 * Preserve the autority (work operator).
	 </en>*/
	protected String wWork = "" ;
	//#CM46805
	/**<en>
	 * Preserve the autority (visitor).
	 </en>*/
	protected String wShow = "" ;
	//#CM46806
	/**<en>
	 * Preserve the name of the frame.
	 </en>*/
	protected String wFrame = "" ;
	//#CM46807
	/**<en>
	 * Preserve the file path.
	 </en>*/
	protected String wFilePath = "" ;

	//#CM46808
	// Public Class method -------------------------------------------
	//#CM46809
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:40:54 $") ;
	}

	//#CM46810
	/**<en>
	 * Retrieve the contents of paramter based on the key.
	 * Read the DispMessage resource file, and if the strings are not switched,
	 * please call this method.
	 * @param key  :key of the retrieving parameter.
	 * @return     :string representation of parameter
	 </en>*/
	public static String getText(String key)
	{
		return (getText(DEFAULT_RESOURCE, key, null)) ;
	}

	//#CM46811
	/**<en>
	 * Retrieve the contents of paramter based on the key.
	 * @param key  :key of the retrieving parameter.
	 * @param request <code>HttpServletRequest</code>
	 *                the variable required to read the DispMessage resource file
	 * @return     :string representation of parameter
	 </en>*/
	public static String getText(String key, HttpServletRequest request)
	{
		return (getText(DEFAULT_RESOURCE, key, request)) ;
	}

	//#CM46812
	/**<en>
	 * Retrieve the contents of paramter based on the key.
	 * @param resource  :name of the resource file of retrieving parameter
	 * @param key       :key of the retrieving parameter.
	 * @param request <code>HttpServletRequest</code>
	 *                 the variable required to read the DispMessage resource file
	 * @return   :string representation of parameter
	 </en>*/
	public static String getText(String resource, String key, HttpServletRequest request)
	{
		ResourceBundle rb = null;
		if (request == null)
		{
			rb = ResourceBundle.getBundle(resource, Locale.getDefault()) ;
		}
		else
		{
			rb = ResourceBundle.getBundle(resource, request.getLocale()) ;
		}
		String str = rb.getString(key) ;
		int start = str.indexOf(SEPARATE);
		if (start < 0)
			return str;
		else
			return str.substring(0,str.indexOf(SEPARATE));
	}
	//#CM46813
	// Constructors --------------------------------------------------
	//#CM46814
	/**<en>
	 * Constructor
	 </en>*/
	public ToolMenuText()
	{
	}
	//#CM46815
	/**<en>
	 * Constructor
	 </en>*/
	public ToolMenuText(String key, String value, String dispNo, String admin, String main, String work, String show, String frame, String filepath)
	{
		wKey = key;
		wValue = value;
		wDispNo = dispNo ;
		wAdmin = admin ;
		wMaintenance = main;
		wWork = work;
		wShow = show;
		wFrame = frame;
		wFilePath = filepath;
	}
	//#CM46816
	/**<en>
	 * Constructor
	 </en>*/
	public ToolMenuText(String param)
	{
		String _chk0  = wSeparate;
		int    start0 = param.indexOf(_chk0) ;
		if (start0 >= 0)
		{
			wKey =  (param.substring(0, start0)) ;
		}

		String _chk_value  = "@";
		int    start_value = param.indexOf(_chk_value) ;
		if (start_value >= 0)
		{
			wValue =  (param.substring(start0+1, start_value)) ;
		}
		else
		{
			wValue = param.substring(start0+1,param.length());
		}
		
		String _chk2  = "DISPNO=\"" ;
		int    start2 = param.indexOf(_chk2) ;
		if (start2 >= 0)
		{
			int    end2   = param.indexOf("\"", start2 + _chk2.length()) ;
			wDispNo =  (param.substring(start2 + _chk2.length(), end2)) ;
		}

		String _chk3  = "ADMIN=\"" ;
		int    start3 = param.indexOf(_chk3) ;
		if (start3 >= 0)
		{
			int    end3   = param.indexOf("\"", start3 + _chk3.length()) ;
			wAdmin =  (param.substring(start3 + _chk3.length(), end3)) ;
		}

		String _chk4  = "MAINTENANCE=\"" ;
		int    start4 = param.indexOf(_chk4) ;
		if (start4 >= 0)
		{
			int    end4   = param.indexOf("\"", start4 + _chk4.length()) ;
			wMaintenance =  (param.substring(start4 + _chk4.length(), end4)) ;
		}

		String _chk5  = "WORK=\"" ;
		int    start5 = param.indexOf(_chk5) ;
		if (start5 >= 0)
		{
			int    end5   = param.indexOf("\"", start5 + _chk5.length()) ;
			wWork =  (param.substring(start5 + _chk5.length(), end5)) ;
		}

		String _chk6  = "SHOW=\"" ;
		int    start6 = param.indexOf(_chk6) ;
		if (start6 >= 0)
		{
			int    end6   = param.indexOf("\"", start6 + _chk6.length()) ;
			wShow =  (param.substring(start6 + _chk6.length(), end6)) ;
		}

		String _chk7  = "FRAME=\"" ;
		int    start7 = param.indexOf(_chk7) ;
		if (start7 >= 0)
		{
			int    end7   = param.indexOf("\"", start7 + _chk7.length()) ;
			wFrame =  (param.substring(start7 + _chk7.length(), end7)) ;
		}
		String _chk8  = "FILEPATH=\"" ;
		int    start8 = param.indexOf(_chk8) ;
		if (start8 >= 0)
		{
			int    end8   = param.indexOf("\"", start8 + _chk8.length()) ;
			wFilePath =  (param.substring(start8 + _chk8.length(), end8)) ;
		}
		
	}
	//#CM46817
	// Public methods ------------------------------------------------
	//#CM46818
	/**<en>
	 * Set the key.
	 * @param key :key
	 </en>*/
	public void setKey(String key)
	{
		wKey = key;
	}

	//#CM46819
	/**<en>
	 * Retrieve the key.
	 * @return :key
	 </en>*/
	public String getKey()
	{
		return wKey;
	}
	//#CM46820
	/**<en>
	 * Set the value.
	 * @param value :value
	 </en>*/
	public void setValue(String value)
	{
		wValue = value;
	}

	//#CM46821
	/**<en>
	 * Retrieve the value.
	 * @return :value
	 </en>*/
	public String getValue()
	{
		return wValue;
	}

	//#CM46822
	/**<en>
	 * Set the order of display.
	 * @param dispNo :the order of display
	 </en>*/
	public void setDispNo(String dispNo)
	{
		wDispNo = dispNo;
	}

	//#CM46823
	/**<en>
	 * Retrieve the order of display.
	 * @return :the order of display
	 </en>*/
	public String getDispNo()
	{
		return wDispNo;
	}

	//#CM46824
	/**<en>
	 * Set the authority (administrator).
	 * @param admin :authority (administrator)
	 </en>*/
	public void setAdmin(String admin)
	{
		wAdmin = admin;
	}

	//#CM46825
	/**<en>
	 * Retrieve the authority (administrator).
	 * @return :authority (administrator)
	 </en>*/
	public String getAdmin()
	{
		return wAdmin;
	}

	//#CM46826
	/**<en>
	 * Set the authority (maintenance conductor).
	 * @param main :authority (maintenance conductor)
	 </en>*/
	public void setMaintenance(String main)
	{
		wMaintenance = main;
	}

	//#CM46827
	/**<en>
	 * Retrieve the authority (maintenance conductor).
	 * @return :authority (maintenance conductor)
	 </en>*/
	public String getMaintenance()
	{
		return wMaintenance;
	}

	//#CM46828
	/**<en>
	 * Set the authority (operator).
	 * @param work :authority (operator)
	 </en>*/
	public void setWork(String work)
	{
		wWork = work;
	}

	//#CM46829
	/**<en>
	 * Retrieve the authority (operator).
	 * @return :authority (operator)
	 </en>*/
	public String getWork()
	{
		return wWork;
	}

	//#CM46830
	/**<en>
	 * Set the authority (visitor).
	 * @param show :authority (visitor)
	 </en>*/
	public void setShow(String show)
	{
		wShow = show;
	}

	//#CM46831
	/**<en>
	 * Retrieve the authority (visitor).
	 * @return :authority (visitor)
	 </en>*/
	public String getShow()
	{
		return wShow;
	}
	//#CM46832
	/**<en>
	 * Set the name of the frame.
	 * @param fp :name of the frame
	 </en>*/
	public void setFilePath(String fp)
	{
		wFilePath = fp;
	}

	//#CM46833
	/**<en>
	 * Retrieve the name of the frame.
	 * @return :the name of the frame.
	 </en>*/
	public String getFrame()
	{
		return wFrame;
	}
	//#CM46834
	/**<en>
	 * Set the name of the frame.
	 * @param fn :the name of the frame.
	 </en>*/
	public void setFrame(String fn)
	{
		wFrame = fn;
	}

	//#CM46835
	/**<en>
	 * Retrieve the file path.
	 * @return :file path
	 </en>*/
	public String getFilePath()
	{
		return wFilePath;
	}

	//#CM46836
	/**<en>
	 * Represent within 1 line for the case writing MenuText type in the resouce file.
	 </en>*/
	public String toString()
	{
		if (wKey.length() != 0)
		{
			if (wKey.substring(1,5).equals("0000"))
			{
				return wValue;
			}
			else
			{
				return wValue+"@DISPNO=\""+getDispNo()+"\"ADMIN=\""+getAdmin()+"\"MAINTENANCE=\""+
					getMaintenance()+"\"WORK=\""+getWork()+"\"SHOW=\""+getShow()+"\"FRAME=\""+getFrame()+"\"FILEPATH=\""+getFilePath()+"\"";
			}
		}
		return "";
	}
	//#CM46837
	// Package methods -----------------------------------------------

	//#CM46838
	// Protected methods ---------------------------------------------

	//#CM46839
	// Private methods -----------------------------------------------

	//#CM46840
	// Debug methods -------------------------------------------------

}
//#CM46841
//end of class
