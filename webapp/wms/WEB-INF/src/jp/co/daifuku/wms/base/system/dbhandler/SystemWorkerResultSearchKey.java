//#CM699930
//$Id: SystemWorkerResultSearchKey.java,v 1.2 2006/10/30 06:39:16 suresh Exp $
package jp.co.daifuku.wms.base.system.dbhandler;
//#CM699931
/*
 * Created on 2004/08/20
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import jp.co.daifuku.wms.base.dbhandler.WorkerResultSearchKey;

//#CM699932
/**
 * Designer : T.Nakajima<BR>
 * Maker 	: T.Nakajima<BR> 
 *
 * Allow this class to set a key to search through the DNWORKERRESULT table.
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/09/27</TD><TD>T.Nakajima</TD><TD></TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author nakajima
 * @version $Revision 1.1 2005/09/27
 */
public class SystemWorkerResultSearchKey extends WorkerResultSearchKey
{
	//#CM699933
	// Class filelds -------------------------------------------------
	
	private static String p_Prefix = "";
	
	private static final String START_TIME = "STARTTIME";
	
	//#CM699934
	// Class method --------------------------------------------------
	
	//#CM699935
	/**
	 * Return the version of this class.
	 * @return Version
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}
	
	//#CM699936
	// Constructors --------------------------------------------------
	
	//#CM699937
	/**
	 * Comment for constructor (Comment for Constructor)
	 */
	public SystemWorkerResultSearchKey()
	{
		super();
		p_Prefix = this.getTableName() + ".";
	}

	//#CM699938
	// Public methods ------------------------------------------------

	//#CM699939
	/**
	 * Set the sorting order of Start work date/time.
	 * @param num Designate the sorting order.
	 * @param bool Designate ascending order (true) or descending order (false).
	 */
	public void setWorkStartTimeOrder(int num, boolean bool)
	{
		//#CM699940
		// Override the setWorkStartTimeOrder.
		//#CM699941
		// Designate the alias name used in the SQL of SystemWorkerResultFinder.
		setOrder(p_Prefix + START_TIME, num, bool);
	}
	
}
//#CM699942
//end of class
