//#CM695488
//$Id: AbstractExternalDataLoader.java,v 1.2 2006/11/13 06:03:15 suresh Exp $
package jp.co.daifuku.wms.base.system.schedule;

//#CM695489
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

//#CM695490
/**
 * Designer : T.Yamashita <BR>
 * Maker : T.Yamashita <BR>
 * <BR>
 * Allow this abstract <CODE>AbstractExternalDataLoader</CODE> class to execute the process for loading data.<BR>
 * Implement the <CODE>ExternalDataLoader</CODE> interface and also implement the process required to implement this interface.<BR>
 * Allow this class to implement the common method. Allow the class that inherits this class to implement the individual behavior of the actual maintenance process.<BR>
 * <BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/02</TD><TD>T.Yamashita</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:15 $
 * @author  $Author: suresh $
 */
public abstract class AbstractExternalDataLoader implements ExternalDataLoader
{

	//#CM695491
	// Class fields --------------------------------------------------

	//#CM695492
	// Class variables -----------------------------------------------
	//#CM695493
	/**
	 * Connection for database connection<BR>
	 */
	protected Connection wConn;

	//#CM695494
	/**
	 * Area to maintain the message<BR>
	 * Use this to maintain the contents when condition error etc occurs by invoking a method.
	 */
	private String wMessage = "";

	//#CM695495
	/**
	 * Count of all the read data<BR>
	 */
	public int wAllItemCount = 0;

	//#CM695496
	/**
	 * Count of Added Data<BR>
	 */
	public int wInsertItemCount = 0;

	//#CM695497
	/**
	 * Count of Updated Data<BR>
	 */
	public int wUpdateItemCount = 0;

	//#CM695498
	/**
	 * Count of skipped data<BR>
	 */
	public int wSkipItemCount = 0;

	//#CM695499
	/**
	 * Skip flag<BR>
	 */
	private boolean wSkipFlag = false;

	//#CM695500
	/**
	 * Overwrite Flag<BR>
	 */
	private boolean wOverWriteFlag = false;

	//#CM695501
	/**
	 * Add Flag<BR>
	 */
	private boolean wRegistFlag = false;

	//#CM695502
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM ; 

	//#CM695503
	// Class method --------------------------------------------------
	//#CM695504
	/**
	 * Return the version of this class.<BR>
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:15 $");
	}

	//#CM695505
	// Constructors --------------------------------------------------
	//#CM695506
	/**
	 * Comment by Constructor
	 */
	public AbstractExternalDataLoader()
	{
		System.out.println("AbstractExternalDataLoader_Constructor!");
	}

	//#CM695507
	// Public methods ------------------------------------------------
	//#CM695508
	/**
	 * Obtain the count of all the read data.<BR>
	 * @return  Count of all the read data
	 */
	public int getAllItemCount()
	{
		return wAllItemCount;
	}

	//#CM695509
	/**
	 * Set the count of all read data.<BR>
	 */
	public void setAllItemCount(int allcnt)
	{
		wAllItemCount = allcnt;
	}

	//#CM695510
	/**
	 * Obtain the value of the Overwrite Flag.
	 * @return wOverWriteFlag 
	 */
	public boolean isOverWriteFlag()
	{
		return wOverWriteFlag;
	}
	//#CM695511
	/**
	 * Set a value in the Overwrite Flag.
	 * @param overWriteFlag Overwrite Flag to be set
	 */
	public void setOverWriteFlag(boolean overWriteFlag)
	{
		wOverWriteFlag = overWriteFlag;
	}
	//#CM695512
	/**
	 * Obtain the value of the Skip flag.
	 * @return wSkipFlag 
	 */
	public boolean isSkipFlag()
	{
		return wSkipFlag;
	}
	//#CM695513
	/**
	 * Set a value in the Skip Flag.
	 * @param skipFlag Skip Flag to be set.
	 */
	public void setSkipFlag(boolean skipFlag)
	{
		wSkipFlag = skipFlag;
	}
	//#CM695514
	/**
	 * Obtain the value of the Add Flag.
	 * @return wRegistFlag
	 */
	public boolean isRegistFlag()
	{
		return wRegistFlag;
	}

	//#CM695515
	/**
	 * Set a value in the Add Flag.
	 * @param registFlag Add Flag to be set
	 */
	public void setRegistFlag(boolean registFlag)
	{
		wRegistFlag = registFlag;
	}

	//#CM695516
	/**
	 * Obtain the count of the added data.<BR>
	 * @return  Count of Added Data
	 */
	public int getInsertItemCount()
	{
		return wInsertItemCount;
	}

	//#CM695517
	/**
	 * Set the Count of Added Data.<BR>
	 */
	public void setInsertItemCount(int insertcnt)
	{
		wInsertItemCount = insertcnt;
	}

	//#CM695518
	/**
	 * Obtain the count of updated data.<BR>
	 * @return  Count of Updated Data
	 */
	public int getUpdateItemCount()
	{
		return wUpdateItemCount;
	}

	//#CM695519
	/**
	 * Set the Count of Updated Data.<BR>
	 */
	public void setUpdateItemCount(int updatecnt)
	{
		wUpdateItemCount = updatecnt;
	}

	//#CM695520
	/**
	 * Obtain the count of skipped data.<BR>
	 * @return  Count of skipped data
	 */
	public int getSkipItemCount()
	{
		return wSkipItemCount;
	}

	//#CM695521
	/**
	 * Set the Count of skipped data.<BR>
	 */
	public void setSkipItemCount(int skipcnt)
	{
		wSkipItemCount = skipcnt;
	}

	//#CM695522
	/**
	 * If <CODE>load()</CODE> method returns false,<BR>
	 * obtain a message to display the reason.<BR>
	 * Use this method to obtain the content to be displayed in the Message area on the screen.
	 */
	public String getMessage()
	{
		return wMessage;
	}

	//#CM695523
	/**
	 * Execute the Process for loading data.<BR>
	 * Allow the class that inherits the <code>AbstractExternalDataLoader</code> class to implement this method because the process for loading data depends on the package.
	 * Return true if succeeded in checking for parameter or
	 * return false if failed.
	 * If failed to check for parameter, obtain the reason by means of <code>getMessage</code>.
	 * @param conn Database connection object
	 * @param wns  WareNaviSystem object
	 * @param startParam A class that inherits the <CODE>Parameter</CODE> class.
	 * @return Return true if loading succeeded. Return false if failed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce when unexpected error occurs during loading data.
	 */
	public abstract boolean load(Connection conn, WareNaviSystem wns, Parameter startParam) throws ReadWriteException, ScheduleException;

	//#CM695524
	/**
	 * Initialize the Process Count. <BR>
	 */
	public void InitItemCount()
	{
		setAllItemCount(0);
		setInsertItemCount(0);
		setUpdateItemCount(0);
		setSkipItemCount(0);	
	}
	
	//#CM695525
	// Package methods -----------------------------------------------

	//#CM695526
	// Protected methods ---------------------------------------------
	//#CM695527
	/**
	 * Set for Message maintained by the check.<BR>
	 * Set the details if the content of the ticked field is wrong.<BR>
	 * @param Content of message.
	 */
	protected void setMessage(String msg)
	{
		wMessage = msg;
	}

	//#CM695528
	// Private methods -----------------------------------------------
	
	//#CM695529
	// Private Class
}
//#CM695530
//end of class
