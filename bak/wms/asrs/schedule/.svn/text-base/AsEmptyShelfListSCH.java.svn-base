// $Id: AsEmptyShelfListSCH.java,v 1.2 2006/10/30 01:04:34 suresh Exp $

//#CM44433
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule ;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.report.AsEmptyShelfListWriter;
import jp.co.daifuku.wms.base.common.Parameter;

//#CM44434
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * Class to call the WEB empty cell list print processing.  <BR>
 * Receive the content input from the screen as parameter, and call empty shelf List Print Class. <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1. Processing when Print button is pressed (<CODE>startSCH()</CODE>Method)<BR><BR><DIR>
 *   Receive the content input from the screen as parameter, and pass parameter to empty shelf List Print Class. <BR>
 *   Retrieve the content of the print with WriterClass. <BR>
 *   When succeeding in the print, Return True from empty shelf ListPrint processing Class or False when failing. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     WarehouseFlag* <BR>
 *     Title machine No* </DIR>
 * <BR>
 *   <Printing job> <BR>
 * <DIR>
 * 	   1.Set the value in <CODE>FreeShelfWriter</CODE> which was set in Parameter. <BR>
 * 	   2.Print empty List using <CODE>FreeShelfWriter</CODE> Class. <BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/05</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:04:34 $
 * @author  $Author: suresh $
 */

public class AsEmptyShelfListSCH extends AbstractAsrsControlSCH
{
	//#CM44435
	// Class fields --------------------------------------------------

	//#CM44436
	// Class variables -----------------------------------------------

	//#CM44437
	// Class method --------------------------------------------------
	//#CM44438
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:04:34 $");
	}

	//#CM44439
	// Constructors --------------------------------------------------
	//#CM44440
	/**
	 * Initialize this Class. 
	 */
	public AsEmptyShelfListSCH()
	{
		wMessage = null;
	}

	//#CM44441
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen. <BR>
	 * Return null when pertinent data does not exist or it exists by two or more.  <BR>
	 * Set null in < CODE>searchParam</CODE > because it does not need the search condition. 
	 * @param conn Connection object with database
	 * @param searchParam Class which succeeds to < CODE>Parameter</CODE> Class with Search condition
	 * @return Class which mounts < CODE>Parameter</CODE > interface where retrieval result is included
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		return null;
	}

	//#CM44442
	/**
	 * The content input from the screen is received as parameter, and straightened, and acquire from the data base and return data for the area output. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation.  <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param searchParam Instance of < CODE>AsSystemScheduleParameter</CODE>Class with display data acquisition condition. <BR>
	 *         ScheduleException when specified excluding < CODE>AsSystemScheduleParameter</CODE > instance is slow. <BR>
	 * @return Array of < CODE>AsSystemScheduleParameter</CODE > instance with retrieval result. <BR>
	 *          Return the empty array when not even one pertinent record is not found. <BR>
	 *          Return null when the error occurs in the input condition. <BR>
	 *          When null is returned, the content of the error can be acquired as a message in < CODE>getMessage() </ CODE>Method. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		return null;
	}

	//#CM44443
	/**
	 * Receive the content input from the screen as parameter, and begin the storing schedule. <BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation. <BR>
	 * When PrintFlag in < CODE>StorageSupportParameter</CODE > instance is true, <BR>
	 * the print processing of List is done by using < CODE>StorageListReportWriter</CODE>Class. <BR>
	 * Return true when the schedule ends normally and return false when failing. <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Array of instance of < CODE>StorageListReportWriter</CODE>Class with set content.
	 *         < CODE>ScheduleException</CODE > when things except the StorageSupportParameter instance are specified is slow .<BR>
	 *         It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)throws ReadWriteException, ScheduleException
	{
		//#CM44444
		// Input info of parameter
	    AsScheduleParameter asSchParam =  (AsScheduleParameter) startParams[0];

		if (asSchParam == null)
		{
			wMessage = "6027005";
			throw new ScheduleException();
		}
		
		AsEmptyShelfListWriter writer = createWriter(conn, asSchParam);
		
		if (writer.startPrint())
		{
			wMessage = "6001010";
			return true;
		}
		else
		{
			wMessage = writer.getMessage();
			return false;		
		}
	}
	
	//#CM44445
	/**
	 * Acquire the number of cases to be printed based on information input from the screen. <BR>
	 * Return 0 when there is no object data or is an input error. <BR>
	 * Acquire Error Message by calling in case of 0 and using < CODE>getMessage</CODE > by previous processing. <BR>
	 * @param conn Connection object with database
	 * @param countParam < CODE>Parameter</CODE > object including Search condition
	 * @return The number of cases to be printed
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated is generated. 
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
	    AsScheduleParameter param = (AsScheduleParameter) countParam;
		//#CM44446
		// Set Search condition and make printing job Class.
	    AsEmptyShelfListWriter writer = createWriter(conn, param);
		//#CM44447
		// Acquire the number of objects.
		int result = writer.count();
		if (result == 0)
		{
			//#CM44448
			// 6003010 = There was no print data. 
			wMessage = "6003010";
		}
		
		return result;
	}
	
	//#CM44449
	// Protected methods ------------------------------------------------
	
	//#CM44450
	/**
	 * Set information input from the screen in printing job Class, and generate printing job Class.<BR>
	 * @param conn           Connection object with database
	 * @param asSchParameter Parameter object including Search condition
	 * @return Printing job Class
	 */
	protected AsEmptyShelfListWriter createWriter(Connection conn, AsScheduleParameter asSchParameter)
	{
		
	    AsEmptyShelfListWriter writer = new AsEmptyShelfListWriter(conn);
        //#CM44451
        // Set the value in AsItemStorageWriterClass.  
	    writer.setWareHouseNo(asSchParameter.getWareHouseNo());
	    writer.setRackmasterNo(asSchParameter.getRackmasterNo());
	    
		return writer;
	}
}
//#CM44452
//end of class
