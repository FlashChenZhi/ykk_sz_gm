//#CM701510
//$Id: Id0023Operate.java,v 1.2 2006/11/14 06:09:05 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701511
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.ZoneViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ZoneViewSearchKey;
import jp.co.daifuku.wms.base.entity.ZoneView;
import jp.co.daifuku.wms.base.rft.IdOperate;

//#CM701512
/**
 * Designer : etakeda<BR>
 * Maker :   <BR>
 * <BR>
 * The class to do Area list request (ID0023) processing. <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * Receive Area No. as a parameter. 
 * Acquire AreaList information from DVZONEVIEW. <BR>
 * <BR>
 * Area list acquisition(<CODE>getLaneList()</CODE> Method)<BR>
 * <DIR>
 *   Receive Area  No. as a parameter, and return AreaList information. <BR>
 * </DIR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/04</TD><TD>etakeda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:05 $
 * @author  $Author: suresh $
 */
public class Id0023Operate extends IdOperate
{
	//#CM701513
	//  Constructors --------------------------------------------------
	//#CM701514
	/**
	 * Generate the instance. 
	 */
	public Id0023Operate()
	{
		super();
	}

	//#CM701515
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0023Operate(Connection conn)
	{
		wConn = conn;
	}

	//#CM701516
	// Public methods ------------------------------------------------
	//#CM701517
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:05 $";
	}

	//#CM701518
	/**
	 * Acquire the Area list in the condition of specifying it from zone information view (DVZONEVIEW). <BR>
	 * 		
	 * <p>
	 * Acquire the list of zone information on the Area when Area  No. is specified. <BR>
	 * Acquire all Area information when Area No. is not specified. 
	 * <BR>
	 *     <DIR>
	 *     [Acquisition item]<BR>
	 *         <DIR>
	 *         	Area No.<BR>
	 * 			Area Name<BR>
	 * 			Zone No<BR>
	 * 			Zone Name<BR>
	 *         </DIR>
	 *     [Search condition]<BR>
	 *         <DIR>
	 * 		<table>
	 *         Area No. (Remove from Search condition when it is empty.) <BR>
	 * 		</table>
	 * 		<BR>
	 *         </DIR>
	 *     
	 * @param  areaNo		Area No.
	 * @return AreaList information
	 * @throws NotFoundException  It is notified when Area information is not found. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public ZoneView[] getAreaList(String areaNo) throws ReadWriteException,
			NotFoundException
	{
		ZoneView[] zoneView = null;
		ZoneViewHandler zvh = new ZoneViewHandler(wConn);
		ZoneViewSearchKey skey = new ZoneViewSearchKey();

		//#CM701519
		//----------
		//#CM701520
		// Search condition
		//#CM701521
		//----------
		if (!StringUtil.isBlank(areaNo))
		{
			skey.setAreaNo(areaNo);
		}
		skey.setAreaNoOrder(1, true);
		skey.setZoneNoOrder(2, true);
		
		//#CM701522
		// Acquisition of Area list
		zoneView = (ZoneView[]) zvh.find(skey);
		if (zoneView.length == 0 || zoneView == null)
		{
			//#CM701523
			// If AreaList information cannot be acquired, throw NotFoundException. 
			NotFoundException e = new NotFoundException();
			throw e;
		}
		
		return zoneView;

	}
}
