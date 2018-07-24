// $Id: WorkMaintenanceWriter.java,v 1.5 2006/12/13 09:03:17 suresh Exp $

//#CM43728
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.report;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.common.DateOperator;
import jp.co.daifuku.wms.asrs.dbhandler.ASCarryInformationReportFinder;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.entity.ASCarryInfomation;
import jp.co.daifuku.wms.asrs.entity.AsWorkingInformation;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

//#CM43729
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * Make the data file for the slit of the ASRS work maintenance, and execute the print. 
 * <BR>
 * < CODE>WorkMaintenanceWriter</CODE > class makes the data file for the slit of the work maintenance list and 
 * the compulsion disbursement list.   Execute the print.  <BR>
 * Retrieve work information (DNWORKINFO) based on the set condition, and make the result as a slit file for 
 * the work maintenance list and the compulsion disbursement list.  <BR>
 * This class processes it as follows. <BR>
 * <BR>
 * Data file making processing for slit(<CODE>startPrint() </CODE> method)<BR>
 * <DIR>
 *	1.Retrieve transportation information (CARRYINFO) with the set transportation key. <BR>
 *	2.Retrieve the number of cases of work information (DNWORKINFO) on the condition set as retrieved information. <BR>
 *	3.Make the slit file if the result is one or more. Return false and end in case of 0. <BR>
 *	4.Execute the printing job. <BR>
 *	5.Return true when the printing job is normal.  <BR>
 *    Return false when the error occurs in the printing job.  <BR>
 * <BR>
 * 	<Parameter>*Mandatory Input <BR><DIR>
 * 	  Batch No. * <BR></DIR>
 * <BR>
 * 	<Search condition> <BR><DIR>
 *    Transportation key <BR></DIR>
 * <BR>
 *  <retrieval order> <BR><DIR>
 *    1.Ascending order of Work result Location <BR>
 *    2.Ascending order of Item Code <BR>
 *    3.Ascending order of Case/Piece flag <BR></DIR>
 * <BR>
 *	<Print data> <BR><DIR>
 *	  Print entry: DB item<BR>
 *	  Consignor			:Consignor Code + Consignor Name <BR>
 *	  Item Code	:Item Code <BR>
 *	  Item Name		:Item Name <BR>
 *    Flag			:Case/Piece flag <BR>
 *    Retrieval Shelf		:Work resultLocation <BR>
 *    Qty per case	:Qty per case <BR>
 *    Qty per bundle	:Qty per bundle <BR>
 *    Retrieval Case qty	:Actual work qty /Qty per case <BR>
 *    Retrieval Piece qty	:Actual work qty %Qty per case <BR>
 *    Case ITF	:Case ITF <BR>
 *    Bundle ITF	:Bundle ITF <BR></DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/01/16</TD><TD>tahara</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/03/15</TD><TD>M.INOUE</TD><TD>The one that "had been added to transportation destination of issue condition/transportation origin is deleted. </TD></TR>
 * <TR><TD>2006/01/30</TD><TD>Y.Okamura</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:03:17 $
 * @author  $Author: suresh $
 */
public class WorkMaintenanceWriter extends CSVWriter
{
	//#CM43730
	// Class fields --------------------------------------------------
	//#CM43731
	/**
	 * Field which shows list type(work maintenance list)
	 */
	public final static int LIST_WORKMAINTENANCE = 0;

	//#CM43732
	/**
	 * Field which shows list type(compulsion disbursement list)
	 */
	public final static int LIST_DROP = 1;

	//#CM43733
	//  Class variables -----------------------------------------------
	//#CM43734
	/**
	 * Transportation key
	 */
	private String wCarryKey = null ;

	//#CM43735
	/**
	 * Status of transportation
	 */
	private int wCmdStatus;

	//#CM43736
	/**
	 * Work type
	 */
	private String wWorkType = null ;

	//#CM43737
	/**
	 * Maintenance Flag
	 */
	private String wJobFlg = null ;

	//#CM43738
	/**
	 * List type
	 */
	private int wListType = LIST_WORKMAINTENANCE ;

	//#CM43739
	// Class method --------------------------------------------------
	//#CM43740
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:03:17 $") ;
	}

	//#CM43741
	// Constructors --------------------------------------------------
	//#CM43742
	/**
	 * Construct the WorkMaintenanceWriter object. 
	 * @param conn <CODE>Connection</CODE> Connection object with database
	 */
	public WorkMaintenanceWriter(Connection conn)
	{
		super(conn);
	}

	//#CM43743
	/**
	 * Construct the WorkMaintenanceWriter object. <BR>
	 * Set the connection. <BR>
	 * @param conn <CODE>Connection</CODE> Connection object with database
	 * @param locale <CODE>Locale</CODE>
	 */
	public WorkMaintenanceWriter(Connection conn, Locale locale)
	{
		super(conn, locale);
	}

	//#CM43744
	// Public methods ------------------------------------------------
	//#CM43745
	/**
	 * Set the value in Transportation key. 
	 * @param carrykey Transportation key to be set
	 */
	public void setCarrykey(String carrykey)
	{
		wCarryKey = carrykey ;
	}

	//#CM43746
	/**
	 * Acquire Transportation key. 
	 * @return Transportation key
	 */
	public String getCarrykey()
	{
		return wCarryKey ;
	}

	//#CM43747
	/**
	 * Set the value in Status of transportation. 
	 * @param cmdstatus Status of transportation to be set
	 */
	public void setCmdStatus(int cmdstatus)
	{
		wCmdStatus = cmdstatus;
	}

	//#CM43748
	/**
	 * Acquire Status of transportation. 
	 * @return Status of transportation
	 */
	public int getCmdStatus()
	{
		return wCmdStatus ;
	}

	//#CM43749
	/**
	 * Set the value in transportation Flag. 
	 * @param carrykind transportation Flag to be set
	 */
	public void setWorkType(String worktype)
	{
		wWorkType = worktype ;
	}

	//#CM43750
	/**
	 * Acquire transportation Flag. 
	 * @return Transportation Flag
	 */
	public String getWorkType()
	{
		return wWorkType ;
	}

	//#CM43751
	/**
	 * Set the value in the processing flag. 
	 * @param jobflg Set processing flag
	 */
	public void setJob(String jobflg)
	{
		wJobFlg = jobflg ;
	}

	//#CM43752
	/**
	 * Acquire the processing flag. 
	 * @return Processing flag
	 */
	public String getJob()
	{
		return wJobFlg ;
	}

	//#CM43753
	/**
	 * Set the value in List type. 
	 * @param type Set List type
	 */
	public void setListType(int type)
	{
		wListType = type ;
	}

	//#CM43754
	/**
	 * Acquire List type. 
	 * @return List type
	 */
	public int getListType()
	{
		return wListType ;
	}
	
	//#CM43755
	/**
	 * Make the CSV file for the ASRS work maintenance list, and call the print execution class. <BR>
	 * Retrieve work information on the search condition input on the screen. <BR>
	 * Do not print when the retrieval result is less than one. <BR>
	 * @return True when the printing job ends normally and False when failing.
	 */
	public boolean startPrint()
	{
		//#CM43756
		// Character set when there is drawing
		String REPORT_YES	= "*";	
		//#CM43757
		// Retrieve pertinent data. End processing when there is no pertinent transportation data. 
		ASCarryInformationReportFinder reportFinder = new ASCarryInformationReportFinder(wConn);
		
		try 
		{ 
			if (wCarryKey == null)
			{
				return false;
			}
			
			//#CM43758
			// Do not use palette ID at the stock information retrieval when Work type (stock outside the stock or the schedule) and Status of transportation < having instructed it. 
			//#CM43759
			// (When stocking it as there is a schedule, the transportation instruction response reception : because palette ID is not set in the inventory information. )
			//#CM43760
			// Do not update the stock at the stock setting with the schedule. 
			if (((wWorkType.equals(CarryInformation.WORKTYPE_PLAN_STORAGE))
			||  (wWorkType.equals(CarryInformation.WORKTYPE_NOPLAN_STORAGE)))
			&&  (wCmdStatus < CarryInformation.CMDSTATUS_INSTRUCTION))
			{
				if (reportFinder.searchNoParette(wCarryKey) == 0)
				{
					//#CM43761
					// 6003010=There was no print data. 
					setMessage("6003010");
					return false;
				}
			}
			//#CM43762
			// Besides, PaletteID is used at the stock information retrieval. 
			else
			{
				if (reportFinder.search(wCarryKey) == 0)
				{
					//#CM43763
					// The data Status of transportation < instructed is abnormal for abnormality Work type (stock outside the stock or the schedule) and Status of transportation. 
					//#CM43764
					// It retrieves it again in consideration of the becoming it case solving by the stock retrieval which does not use palette ID. 
					if (((wWorkType.equals(CarryInformation.WORKTYPE_PLAN_STORAGE))
					||  (wWorkType.equals(CarryInformation.WORKTYPE_NOPLAN_STORAGE)))
					&&  (wCmdStatus == CarryInformation.CMDSTATUS_ERROR))
					{
						if (reportFinder.searchNoParette(wCarryKey) == 0)
						{
							//#CM43765
							// 6003010=There was no print data. 
							setMessage("6003010");
							return false;
						}
					}
					else
					{
						//#CM43766
						// 6003010=There was no print data. 
						setMessage("6003010");
						return false;
					}
				}
			}		

			//#CM43767
			//  Make the PrintWriter class. 
			if (!createPrintWriter(FNAME_WORKMAINTENANCE))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_WORKMAINTENANCE);

			//#CM43768
			//  Make the content of the data file until the retrieval result is lost. 
			ASCarryInfomation[] carryInfo = null;
			AsWorkingInformation wi = null;
			Stock stk = null;
			Locale locale = Locale.getDefault();
			ASFindUtil util = new ASFindUtil(wConn);

			while (reportFinder.isNext())
			{
				//#CM43769
				//  Display 100 retrievals. 
				carryInfo = (ASCarryInfomation[]) reportFinder.getASEntities(100);

				//#CM43770
				//  Make the content output to the data file. 
				for (int i = 0; i < carryInfo.length; i++)
				{
					wi = carryInfo[i].getWorkInfo();
					stk = carryInfo[i].getStock();
					
					wStrText.append(re + "");
					//#CM43771
					// Transportation origin
					wStrText.append(format(util.getDispStationNo(carryInfo[i].getSourceStationNumber())) + tb);
					//#CM43772
					// At the transportation destination
					wStrText.append(format(util.getDispStationNo(carryInfo[i].getDestStationNumber())) + tb);
					//#CM43773
					// Work No
					wStrText.append(format(carryInfo[i].getWorkNumber()) + tb);
					//#CM43774
					// Work type
					wStrText.append(format(
						DisplayText.getText(locale, "CARRYINFO", "WORKTYPE", carryInfo[i].getWorkType()))
						+ tb);
					//#CM43775
					// The delivery instruction details: Display it only for the picking or the unit. 
					if (carryInfo[i].getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_PICKING
					|| carryInfo[i].getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_UNIT)
					{
						wStrText.append(ReportOperation.format(
								DisplayText.getText(locale, "CARRYINFO", "RETRIEVALDETAIL", Integer.toString(carryInfo[i].getRetrievalDetail())))
								+ tb);
					}
					else
					{
						wStrText.append(ReportOperation.format(
								DisplayText.getText(locale, "CARRYINFO", "RETRIEVALDETAIL", Integer.toString(CarryInformation.RETRIEVALDETAIL_UNKNOWN)))
								+ tb);
					}
					//#CM43776
					// Work status
					wStrText.append(format(
						DisplayText.getText(locale, "CARRYINFO", "CMDSTATUS", Integer.toString(carryInfo[i].getCmdStatus())))
						+ tb);
					//#CM43777
					// Transportation Key
					wStrText.append(format(carryInfo[i].getCarryKey()) + tb);
					//#CM43778
					// Schedule No.
					wStrText.append(format(carryInfo[i].getScheduleNumber()) + tb);
					//#CM43779
					// Maintenance Flag
					if (!StringUtil.isBlank(wJobFlg))
					{
						wStrText.append(format(DisplayText.getText(locale, wJobFlg)) + tb);
					}
					else
					{
						wStrText.append(format("") + tb);
					}
					//#CM43780
					// Drawing
					if (wi.getPlanEnableQty() > 0)
					{
						//#CM43781
						// Drawing available
						wStrText.append(format(REPORT_YES) + tb);
					}
					else
					{
						//#CM43782
						// Drawing not available
						wStrText.append(format("") + tb);
					}
					//#CM43783
					// Consignor Code
					wStrText.append(format(stk.getConsignorCode()) + tb);
					//#CM43784
					// Consignor Name
					wStrText.append(format(stk.getConsignorName()) + tb);
					//#CM43785
					// Item Code
					wStrText.append(format(stk.getItemCode()) + tb);
					//#CM43786
					// Item Name
					wStrText.append(ReportOperation.format(stk.getItemName1()) + tb);
					//#CM43787
					// Qty per case
					wStrText.append(stk.getEnteringQty() + tb);
					//#CM43788
					// Qty per bundle
					wStrText.append(stk.getBundleEnteringQty() + tb);
					//#CM43789
					// Stock case qty
					wStrText.append(DisplayUtil.getCaseQty(stk.getStockQty(), stk.getEnteringQty(), stk.getCasePieceFlag()) + tb);
					//#CM43790
					// Stock piece qty
					wStrText.append(DisplayUtil.getPieceQty(stk.getStockQty(), stk.getEnteringQty(), stk.getCasePieceFlag()) + tb);
					if (wi.getPlanEnableQty() > 0)
					{
						//#CM43791
						// Work Case qty
						wStrText.append(DisplayUtil.getCaseQty(wi.getPlanEnableQty(), wi.getEnteringQty(), wi.getCasePieceFlag())+ tb);
						//#CM43792
						// Work Piece qty
						wStrText.append(DisplayUtil.getPieceQty(wi.getPlanEnableQty(), wi.getEnteringQty(), wi.getCasePieceFlag())+ tb);
					}
					else
					{
						//#CM43793
						// Work Case qty
						wStrText.append(format("") + tb);
						//#CM43794
						// Work Piece qty
						wStrText.append(format("") + tb);
					}
					//#CM43795
					// Case/Piece flag
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(locale, stk.getCasePieceFlag()))+ tb);
					//#CM43796
					// Storage date & time
					wStrText.append(format(DateOperator.changeDateTime(stk.getInstockDate())) + tb);
					//#CM43797
					// Storage date & time
					wStrText.append(format(DateOperator.changeDateTimeMillis(stk.getInstockDate())) + tb);
					//#CM43798
					// Expiry date is acquired from work information when there is work information not completed. 
					if (wi.getPlanEnableQty() > 0)
					{
						//#CM43799
						// Expiry date
						wStrText.append(ReportOperation.format(wi.getWorkInfoUseByDate()));
					}
					//#CM43800
					// Besides, Expiry date is acquired from the inventory information. 
					else
					{
						//#CM43801
						// Expiry date
						wStrText.append(ReportOperation.format(stk.getUseByDate()));
					}

					//#CM43802
					// Output data to the file. 
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}

			//#CM43803
			// Close the stream.
			wPWriter.close();

			//#CM43804
			// UCXSingle is executed. .(Print execution)
			if (!executeUCX(JOBID_WORKMAINTENANCE))
			{
				//#CM43805
				// It failed in the print. Refer to the log. 
				return false;
			}

			//#CM43806
			// The data file is moved to the backup folder when it succeeds in the print.
			ReportOperation.createBackupFile(wFileName);	
		}
		catch (ReadWriteException e)
		{
			//#CM43807
			// 6007034=It failed in the print. Refer to the log. 
			setMessage("6007034");
			return false;
		}
		finally
		{
			wStrText.setLength(0);

			try
			{
				//#CM43808
				// Do the close processing of the opening data base cursor. 
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM43809
				// The data base error occurred. Refer to the log. 
				setMessage("6007002");
				return false;			
			}
		}
		return true;

	}

	//#CM43810
	// Package methods -----------------------------------------------

	//#CM43811
	// Protected methods ---------------------------------------------

	//#CM43812
	// Private methods -----------------------------------------------

}
//#CM43813
// end of class
