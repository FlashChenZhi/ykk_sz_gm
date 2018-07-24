// $Id: WmsUser.java,v 1.2 2006/11/07 06:03:55 suresh Exp $
package jp.co.daifuku.wms.base.common ;

//#CM643607
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;

//#CM643608
/**
 * The class to do management and the operation of user information. <BR>
 * The Entity class to do the result of retrieving the content and the WmsUser table where UserInfo.txt was read in the mapping. <BR><BR>
 * Refer to the following to examine if permitted / not permitted for the user to all FunctionId. 
 * <PRE>
 * try
 * {
 *     WmsUser user = session.getAttribute("WMSUSER") ;
 *     String[] funcid = user.getFuncIdList() ;
 *     for (int i = 0; i < funcid.length; i++)
 *     {
 *         if (user.isPermission(funcid[i]))
 *         {
 *             System.out.println("Management activities permitted for FunctionId[" + funcid[i] + "].");
 *         }
 *         else
 *         }
 *             System.out.println("Management activities not permitted for FunctionId[" + funcid[i] + "].");
 *         }
 *     }
 * }
 * catch (NullpointerException npe)
 * {
 *     npe.printStackTrace() ;
 * }
 * catch (InvalidStatusException ise)
 * {
 *     ise.printStackTrace() ;
 * }
 * catch (Exception e)
 * {
 *     e.printStackTrace() ;
 * }
 * </PRE>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/02/18</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:03:55 $
 * @author  $Author: suresh $
 */
public class WmsUser extends Entity
{

	//#CM643609
	// Class fields --------------------------------------------------
	//#CM643610
	/**
	 * Workshop specification not available
	 */
	public static final String PLACE_NOTFIX = "0";
	//#CM643611
	/**
	 * Workshop specification available
	 */
	public static final String PLACE_FIX = "1";

	//#CM643612
	/**
	 * It is shown that the operation to a certain user's Function ID is not permission. 
	 */
	public static final String NOT_PERMISSION = "0";
	//#CM643613
	/**
	 * It is shown that the operation to a certain user's Function ID is permitted. 
	 */
	public static final String PERMISSION = "1";

	//#CM643614
	/**
	 * Manager Authority
	 */
	public static final String AUTH_ADMIN  = "01";
	//#CM643615
	/**
	 * Maintenance Authority
	 */
	public static final String AUTH_MAIN   = "02";
	//#CM643616
	/**
	 * Worker Authority
	 */
	public static final String AUTH_WORK   = "03";
	//#CM643617
	/**
	 * Whole Authority
	 */
	public static final String AUTH_SHOW   = "04";

	//#CM643618
	/**
	 * It is shown not to be able to display Category menu of Menu.jsp. 
	 */
	public static final int MENU_UNSHOW = 0;
	//#CM643619
	/**
	 * It is shown to be able to display Category menu of Menu.jsp. 
	 */
	public static final int MENU_SHOW   = 1;

	//#CM643620
	/**
	 * Category menu qty
	 */
	protected static final int MENU_NUM = 6;

	//#CM643621
	/* Category menu ID Storage */

	protected static final String CATEGORY_STRG   = "01";
	//#CM643622
	/* Category menu ID Picking */

	protected static final String CATEGORY_RETRV  = "02";
	//#CM643623
	/* Category menu ID Inquiry/Maintenance */

	protected static final String CATEGORY_MAIN   = "03";
	//#CM643624
	/* Category menu ID System */

	protected static final String CATEGORY_SYS    = "04";
	//#CM643625
	/* Category menu ID FLOOR */

	protected static final String CATEGORY_FLRLD  = "05";
	//#CM643626
	/* Category menu ID report */

	protected static final String CATEGORY_REPORT = "06";

	//#CM643627
	/**
	 * Number of items defined in one user information
	 * Operation permits or not permits for the function of User Name,Password,Authority,Workshop specification Available/Not available,Warehouse and Workshop
	 */
	protected static final int COLUMN_NUM = 7;

	//#CM643628
	// Class variables -----------------------------------------------
	//#CM643629
	/**
	 * WmsUserKey(User Name)
	 */
	protected String wUserName = "" ;

	//#CM643630
	/**
	 * Password
	 */
	protected String wPassword = "" ;

	//#CM643631
	/**
	 * Authority(Role)
	 */
	protected String wRole = "" ;

	//#CM643632
	/**
	 * Workshop Available/Not available
	 */
	protected String wPlaceFixation = "" ;

	//#CM643633
	/**
	 * Specified warehouse
	 */
	protected String wWareHouse = "" ;

	//#CM643634
	/**
	 * Specified workshop(Station Number)
	 */
	protected String wWorkPlace = "" ;

	//#CM643635
	/**
	 * The user maintains no Operation permission/permission for the function. 
	 */
	protected Hashtable wPermissionTable = null ;

	//#CM643636
	// Class method --------------------------------------------------
	//#CM643637
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:03:55 $") ;
	}

	//#CM643638
	/**
	 * Edit user's Authority. 
	 * @param flg       Authority type
	 * @param request   HttpServletRequest
	 * @return Judge Flag, and return Authority. 
	 */
	public static String editAuth(String flg, HttpServletRequest request)
	{
		String auth = "";

		if ( flg.equals( AUTH_ADMIN ) )
		{
			auth = DisplayText.getText("AUTH_ADMIN_TEXT");
		}
		else if ( flg.equals( AUTH_MAIN ) )
		{
			auth = DisplayText.getText("AUTH_MAIN_TEXT");
		}
		else if ( flg.equals(AUTH_WORK) )
		{
			auth = DisplayText.getText("AUTH_WORK_TEXT");
		}
		else if ( flg.equals(AUTH_SHOW ) )
		{
			auth = DisplayText.getText("AUTH_SHOW_TEXT");
		}
		else
		{
			auth = "ERROR";
		}
		return auth;
	}

	//#CM643639
	// Constructors --------------------------------------------------
	//#CM643640
	/**
	 * Construct <CODE>WmsUser</CODE>. 
	 */
	public WmsUser()
	{
	}

	//#CM643641
	/**
	 * New Construct <CODE>WmsUser</CODE>. 
	 * It is a constructor who uses it when the content which reads UserInfo.txt is done in the mapping. 
	 * @param line   One line which reads UserInfo.txt
	 * @throws InvalidDefineException Notify when the content of UserInfo.txt is correct and it is not defined. 
	 */
	public WmsUser(String line) throws InvalidDefineException
	{
		String          dlim = WmsParam.USERINFO_FIELD_SEPARATOR; // 区切り文字
		StringTokenizer stk  = new StringTokenizer(line, dlim, false) ;
		if (stk.countTokens() != COLUMN_NUM)
		{
			String[] param = {"6006015", "UserInfo.txt"};
			throw new InvalidDefineException(MessageResource.concatenate(param)) ;
		}

		//#CM643642
		// Sets in the instance variable. 
		setUserName(stk.nextToken()) ;
		setPassword(stk.nextToken()) ;
		setRole(stk.nextToken()) ;
		setPlaceFixation(stk.nextToken()) ;
		setWareHouse(stk.nextToken()) ;
		setWorkPlace(stk.nextToken()) ;
		setPermissionTable(stk.nextToken()) ;
	}

	//#CM643643
	/**
	 * Construct <CODE>WmsUser</CODE>. 
	 * It is a constructor who uses it when the result of retrieving the content and the WmsUser table where UserInfo.txt was read is done in the mapping. 
	 * @param username   User Name
	 * @param password   Password
	 * @param role       Authority(Role)
	 * @param placefix   Workshop specification
	 * @param warehouse  Warehouse
	 * @param workplace  Specified workshop
	 * @param permission Operation permission
	 */
	public WmsUser( String            username, 
					String            password,
					String 	          role,
					String	          placefix,
					String            warehouse,
					String            workplace,
					Hashtable         permission
			      )
	{
		//#CM643644
		// Sets in the instance variable. 
		setUserName(username) ;
		setPassword(password) ;
		setRole(role) ;
		setPlaceFixation(placefix) ;
		setWareHouse(warehouse) ;
		setWorkPlace(workplace) ;
		setPermissionTable(permission) ;
	}

	//#CM643645
	// Public methods ------------------------------------------------
	//#CM643646
	/**
	 * Set the value in UserName. 
	 * @param awcuserKey UserName to be set
	 */
	public void setUserName(String awcuserKey)
	{
		wUserName = awcuserKey ;
	}

	//#CM643647
	/**
	 * Acquire UserName. 
	 * @return UserName
	 */
	public String getUserName()
	{
		return wUserName ;
	}

	//#CM643648
	/**
	 * Acquire Password.
	 * @param password Password to be set
	 */
	public void setPassword(String password) 
	{
		wPassword = password ;
	}

	//#CM643649
	/**
	 * Acquire Password.
	 * @return Password
	 */
	public String getPassword()
	{
		return wPassword ;
	}

	//#CM643650
	/**
	 * Set the value in Role. 
	 * @param Role role to be set
	 */
	public void setRole(String role)
	{
		wRole = role ;
	}

	//#CM643651
	/**
	 * Acquire Role. 
	 * @return role
	 */
	public String getRole()
	{
		return wRole ;
	}

	//#CM643652
	/**
	 * Set the value in PlaceFixation. 
	 * @param placefix Workshop specification
	 */
	public void setPlaceFixation(String placefix)
	{
		this.wPlaceFixation = placefix ;
	}

	//#CM643653
	/**
	 * Acquire PlaceFixation. 
	 * @return PLACE_FIX / PLACE_NOTFIX
	 */
	public String getPlaceFixation()
	{
		return this.wPlaceFixation ;
	}

	//#CM643654
	/**
	 * Judge whether the workshop (station) where the user works are specified. 
	 * @return <code>true</code> when specified, <code>false</code> when unspecified
	 * @throws InvalidStatusException Notify the PlaceFixation state when it is abnormal. 
	 */
	public boolean isPlaceFixation() throws InvalidStatusException
	{
		if (this.wPlaceFixation.equals(PLACE_FIX))
		{
			return true ;
		}
		else if (this.wPlaceFixation.equals(PLACE_NOTFIX))
		{
			return false ;
		}
		else
		{
			Object[] tObj = new Object[2] ;
			tObj[0] = "WmsUser" ;
			tObj[1] = "isPlaceFixation()" ;
			String classname = (String)tObj[0];
			RmiMsgLogClient.write(6006016, LogMessage.F_ERROR, classname, tObj);
			String[] param = {"6006016", "PlaceFixation", this.wPlaceFixation};
			throw (new InvalidStatusException(MessageResource.concatenate(param)));
		}
	}

	//#CM643655
	/**
	 * Set the value in WareHouse. 
	 * @param warehouse Specified warehouse
	 */
	public void setWareHouse(String warehouse)
	{
		wWareHouse = warehouse ;
	}

	//#CM643656
	/**
	 * Acquire WareHouse. 
	 * @return WareHouse
	 */
	public String getWareHouse()
	{
		return wWareHouse ;
	}

	//#CM643657
	/**
	 * Set the value in WorkPlace. 
	 * @param workplace Character string of operation permission list
	 */
	public void setWorkPlace(String workplace)
	{
		wWorkPlace = workplace ;
	}

	//#CM643658
	/**
	 * Acquire WorkPlace. 
	 * @return WorkPlace
	 */
	public String getWorkPlace()
	{
		return wWorkPlace ;
	}

	//#CM643659
	/**
	 * Set the value in PermissionTable. 
	 * @param line Character string of operation permission list
	 */
	public void setPermissionTable(String line)
	{
		String          dlim = ";" ;
		String        tmpobj = null ;
		String           key = null ;
		String         value = null ;
		StringTokenizer  stk = new StringTokenizer(line, dlim, false) ;
		wPermissionTable     = new Hashtable() ;

		//#CM643660
		// Sets in Hashtable. 
		while ( stk.hasMoreTokens() )
		{
			tmpobj = stk.nextToken() ;
			key    = tmpobj.substring(0, tmpobj.length() - 1) ;
			value  = tmpobj.substring(tmpobj.length() - 1, tmpobj.length()) ;
			wPermissionTable.put(key, value) ;
		}
	}

	//#CM643661
	/**
	 * Set the value in PermissionTable. 
	 * @param permission Operation permission table
	 */
	public void setPermissionTable(Hashtable permission)
	{
		wPermissionTable = new Hashtable() ;
		wPermissionTable.putAll(permission) ;
	}

	//#CM643662
	/**
	 * Acquire PermissionTable with Character string. 
	 * @throws NullPointerException   Notify when information is not set in PermissionTable. 
	 * @throws InvalidStatusException Notify when an illegal value is set in Permission. 
	 * @return Character string of PermissionTable
	 */
	public String getLinePermissionTbl() throws NullPointerException, InvalidStatusException
	{
		String   line = "" ;
		String[] funcid   = getFuncIdList() ;
		for (int i = 0; i < funcid.length; i++)
		{
			if (isPermission(funcid[i]))
			{
				line += funcid[i] + PERMISSION + ";" ;
			}
			else
			{
				line += funcid[i] + NOT_PERMISSION + ";" ;
			}
		}
		return line ;
	}

	//#CM643663
	/**
	 * Acquire Permission Table. 
	 * @return Operation permission table
	 */
	public Hashtable getPermissionTable()
	{
		return wPermissionTable ;
	}

	//#CM643664
	/**
	 * Operation permission/acquire no of the function specifying FunctionId from PermissionTable. 
	 * @param  functionid Function ID
	 * @return <code>true</code> in case of operation permission, <code>false</code> except for no operation permission
	 * @throws NullPointerException   Notify when information is not set in PermissionTable. 
	 * @throws InvalidStatusException Notify when an illegal value is set in Permission. 
	 */
	public boolean isPermission(String functionid) throws NullPointerException, InvalidStatusException
	{
		//#CM643665
		// Is the value set in wPermissionTable?"
		if (isEmpty())
		{
			String[] param = {"6006017", "PermissionTable"};
			throw new NullPointerException(MessageResource.concatenate(param)) ;
		}

		String permission = (String)wPermissionTable.get(functionid) ;
		if (permission.equals(PERMISSION))
		{
			return  true ;
		}
		else if (permission.equals(NOT_PERMISSION))
		{
			return  false ;
		}
		else
		{
			Object[] tObj = new Object[2] ;
			tObj[0] = "WmsUser" ;
			tObj[1] = "isPlaceFixation()" ;
			String classname = (String)tObj[0];
			RmiMsgLogClient.write(6006016, LogMessage.F_ERROR, classname, tObj);
			String[] param = {"6006016", "Permission", permission};
			throw (new InvalidStatusException(MessageResource.concatenate(param)));
		}
	}

	//#CM643666
	/**
	 * Acquire Function Id List.
	 * @return Function Id List
	 * @throws NullPointerException Notify when information is not set in PermissionTable. 
	 */
	public String[] getFuncIdList() throws NullPointerException
	{
		//#CM643667
		// Is the value set in wPermissionTable?"
		if (isEmpty())
		{
			String[] param = {"6006017", "PermissionTable"};
			throw new NullPointerException(MessageResource.concatenate(param)) ;
		}

		int      i    = 0 ;
		String[] keys = new String[wPermissionTable.size()] ;
		for (Enumeration e = wPermissionTable.keys(); e.hasMoreElements();)
		{
			keys[i] = (String)e.nextElement() ;
			i++ ; 
		}
		//#CM643668
		// Sort it. 
		Arrays.sort(keys);
		return keys ;
	}

	//#CM643669
	/**
	 * Acquire FunctionId list according to category. 
	 * @param category Category ID
	 * @return FunctionId list according to category
	 * @throws NullPointerException Notify when information is not set in PermissionTable. 
	 */
	public String[] getFuncIdList(String category) throws NullPointerException
	{
		//#CM643670
		// Is the value set in wPermissionTable?
		if (isEmpty())
		{
			String[] param = {"6006017", "PermissionTable"} ;
			throw new NullPointerException(MessageResource.concatenate(param)) ;
		}

		String   temp = null ;
		String[] keys = null ;
		Vector   vec  = new Vector() ;
		for (Enumeration e = wPermissionTable.keys(); e.hasMoreElements();)
		{
			temp = (String)e.nextElement() ;
			if (temp.substring(0,2).equals(category))
			{
				vec.addElement(temp) ;
			}
		}
		keys = new String[vec.size()] ;
		vec.copyInto(keys) ; 
		//#CM643671
		// Sort it. 
		Arrays.sort(keys) ;
		return keys ;
	}

	//#CM643672
	/**
	 * Display/non-display of category menu All is returned by <CODE>int[]</CODE>.
	 * @return <CODE>MENU_SHOW</CODE> in case of display and <CODE>MENU_UNSHOW</CODE> in case of non-display
	 * @throws InvalidStatusException Notify when an illegal value is set in Permission. 
	 */
	public int[] getDispMenu() throws InvalidStatusException
	{
		int[] dispmenu = {
							isShow(CATEGORY_STRG),   // 入庫カテゴリメニューの表示OK/NG
							isShow(CATEGORY_RETRV),  // 出庫カテゴリメニューの表示OK/NG
							isShow(CATEGORY_MAIN),   // 問合せ/メンテナンスカテゴリメニューの表示OK/NG
							isShow(CATEGORY_SYS),    // システムカテゴリメニューの表示OK/NG
							isShow(CATEGORY_FLRLD),  // 平置きカテゴリメニューの表示OK/NG
							isShow(CATEGORY_REPORT)  // 帳票カテゴリメニューの表示OK/NG
						 };
		return dispmenu ;
	}

	//#CM643673
	// Package methods -----------------------------------------------

	//#CM643674
	// Protected methods ---------------------------------------------

	//#CM643675
	// Private methods -----------------------------------------------
	//#CM643676
	/**
	 * Judge whether information is set in PermissionTable. 
	 * @return <code>true</code> when empty, <code>false</code> When information is set
	 */
	private boolean isEmpty()
	{
		if (wPermissionTable == null)
		{
			return true ;
		}
		return false ;
	}

	//#CM643677
	/**
	 * Return display/non-display of the menu of each category. 
	 * @param category Category ID
	 * @return <CODE>MENU_SHOW</CODE> in case of display and <CODE>MENU_UNSHOW</CODE> in case of non-display
	 * @throws InvalidStatusException Notify when an illegal value is set in Permission. 
	 */
	private int isShow(String category) throws InvalidStatusException
	{
		String[]        funcid   = getFuncIdList(category) ;
		for (int i = 0; i < funcid.length; i++)
		{
			if (isPermission(funcid[i]))
			{
				return MENU_SHOW ;
			}
		}

		return MENU_UNSHOW ;
	}
}
//#CM643678
//end of class
