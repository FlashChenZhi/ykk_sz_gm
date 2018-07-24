// $Id: ToolAs21MachineAlterKey.java,v 1.2 2006/10/30 02:17:21 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47334
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM47335
/**<en>
 * This class is the definition of information which is used to update the Machine table.
 * It will be used when updating the tables by AS21MachineStateHandler.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:21 $
 * @author  $Author: suresh $
 </en>*/
public class ToolAs21MachineAlterKey extends ToolSQLAlterKey
{
	//#CM47336
	// Class fields --------------------------------------------------

	//#CM47337
	//<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
	private static final String MACHINETYPE      = "MACHINETYPE";
	private static final String MACHINENUMBER    = "MACHINENUMBER";
	private static final String STATUS           = "STATUS";
	private static final String ERRORCODE        = "ERRORCODE";
	private static final String CONTROLLERNUMBER = "CONTROLLERNUMBER";

	//#CM47338
	// Class variables -----------------------------------------------

	private static final String[] Columns =
	{
		MACHINETYPE,
		MACHINENUMBER,
		STATUS,
		ERRORCODE,
		CONTROLLERNUMBER
	};

	//#CM47339
	// Class method --------------------------------------------------
	//#CM47340
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM47341
	// Constructors --------------------------------------------------
	//#CM47342
	/**<en>
	 * Initialize the declared table column.
	 </en>*/
	public ToolAs21MachineAlterKey()
	{
		setColumns(Columns);
	}

	//#CM47343
	// Public methods ------------------------------------------------

	//#CM47344
	//<en>================<Method of update condition settings>=================</en>

	//#CM47345
	/**<en>
	 * Set the search value of MACHINETYPE.
	 * @param :the search value of MACHINETYPE
	 </en>*/
	public void setType(int type)
	{
		setValue(MACHINETYPE, type);
	}

	//#CM47346
	/**<en>
	 * Retrieve the search value of MACHINETYPE.
	 * @param MACHINETYPE
	 </en>*/
	public int getType()
	{
		Integer intobj = (Integer)getValue(MACHINETYPE);
		return(intobj.intValue());
	}

	//#CM47347
	/**<en>
	 * Set the search value of MACHINENUMBER.
	 * @param :the search value of MACHINENUMBER
	 </en>*/
	public void setNumber(int num)
	{
		setValue(MACHINENUMBER, num);
	}

	//#CM47348
	/**<en>
	 * Retrieve the search value of MACHINENUMBER.
	 * @param MACHINENUMBER
	 </en>*/
	public int getNumber()
	{
		Integer intobj = (Integer)getValue(MACHINENUMBER);
		return(intobj.intValue());
	}

	//#CM47349
	/**<en>
	 * Set the search value of CONTROLLERNUMBER.
	 * @param :the search value of CONTROLLERNUMBER
	 </en>*/
	public void setControllerNumber(int num)
	{
		setValue(CONTROLLERNUMBER, num);
	}

	//#CM47350
	/**<en>
	 * Retrieve the search value of CONTROLLERNUMBER.
	 * @param CONTROLLERNUMBER
	 </en>*/
	public int getControllerNumber()
	{
		Integer intobj = (Integer)getValue(CONTROLLERNUMBER);
		return(intobj.intValue());
	}

	//#CM47351
	//<en>============<Method of update condition settings>================</en>

	//#CM47352
	/*<en>
	 * Set the update value of STATUS.
	 * @param :the update value of STATUS
	 </en>*/
	 /* Set Update value of STATUS.
	 * @param Update value of STATUS
	 </en>*/
	public void updateStatus(int num)
	{
		setUpdValue(STATUS, num);
	}

	//#CM47353
	/*<en>
	 * Set the update value of ERRORCODE.
	 * @param :the update value of ERRORCODE
	</en>*/
	 /* Set Update value of ERROR CODE.
	 * @param Update value of ERROR CODE
	 </en>*/

	public void updateErrorCode(String ercd)
	{
		setUpdValue(ERRORCODE, ercd);
	}


	//#CM47354
	// Package methods -----------------------------------------------

	//#CM47355
	// Protected methods ---------------------------------------------

	//#CM47356
	// Private methods -----------------------------------------------

	//#CM47357
	// Inner Class ---------------------------------------------------

}
//#CM47358
//end of class

