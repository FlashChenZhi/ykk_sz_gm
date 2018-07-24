package jp.co.daifuku.wms.retrieval.schedule;

//#CM724661
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.report.RetrievalPlanDeleteWriter;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalPlanDeleteHandler;
import jp.co.daifuku.wms.retrieval.entity.RetrievalPlanDelete;

//#CM724662
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda. <BR>
 * <BR>
 * Allow this class to delete WEB Picking plan data.  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to delete all the Picking plan data.  <BR>
 * Allow each method contained in this class to receive the Connection object and execute the process for updating the database, but
 * disable to commit nor roll-back the transaction.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind</CODE> method)  <BR>
 * <BR>
 * <DIR>
 * 	Search for the data with status Standby and Consignor Code through the Picking Plan Info. If the same Consignor Code is only found,
 * 	return the Consignor Code. <BR>
 * 	 Return null if two or more data of Consignor Code exist. <BR>
 *	<BR>
 *	<Search conditions> <BR>
 *	<DIR>
 *		 Status flag = 0: Standby 
 *	</DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Display" button1 (<CODE>check</CODE> method) <BR>
 * <BR>
 * <DIR>
 *	 Receive the contents entered via screen as a parameter and return the results of check for Worker Code and password and presence of corresponding data.  <BR>
 *	If the corresponding data exists in the Picking Plan Info and the contents of Worker Code and Password are correct, return true. <BR>
 *	Use <CODE>getMessage()</CODE> method to obtain the content of the error.<BR>
 *	<BR>
 *	<Parameter> *Mandatory Input<BR>
 *	<DIR>
 *		Worker Code* <BR>
 *		 Password *<BR>
 *		Consignor Code*<BR>
 *		Start Planned Picking Date<BR>
 *		End Planned Picking Date<BR>
 *		 Item / Order *<BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 3. Process by clicking "Display" button2 (<CODE>query</CODE> method) <BR>
 * <BR>
 * <DIR>
 *	 Receive the contents entered via screen as a parameter, and obtain the data for output to the preset area, from the database. <BR>
 *	 Obtain maximum Last update date/time of each Planned date of the data obtained after obtaining it and return it together with the output data <BR>
 *	 If no corresponding data is found, return <CODE>Parameter</CODE> array with number of elements equal to 0. Return null when condition error occurs. <BR>
 *	 If the number of corresponding data existing in the Picking Plan Info exceeds the Max count, set the error message: Search resulted in {0} work. Narrow down search since it exceeds {1}, and 
 *	return null. 
 *	Use <CODE>getMessage()</CODE> method to refer to the content of the error.<BR>
 *	<BR>
 *	<Parameter> *Mandatory Input<BR>
 *	<DIR>
 *		Consignor Code*<BR>
 *		Start Planned Picking Date<BR>
 *		End Planned Picking Date<BR>
 *		 Item / Order *<BR>
 *	</DIR>
 *	<BR>
 *	<Returned data> <BR>
 *	<DIR>
 *		Consignor Code<BR>
 *		Consignor Name<BR>
 *		Planned Picking Date <BR>
 *		 Item / Order <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 4.Process by clicking on Delete button (<CODE>startSCHgetParams</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *	Receive the contents displayed in the preset area, as a parameter, and execute the process to delete the Picking plan data.  <BR>
 *	To print the deleted Picking Plan list, print the deleted list using the <CODE>RetrievalPlanDeleteWriter</CODE> class.<BR>
 *	When the process normally completed, obtain the data for output in the Preset area using the input conditions from the database again, and return it. <BR>
 *	 Return null when the schedule failed to complete due to condition error or other causes.  <BR>
 *	Use <CODE>getMessage()</CODE> method to refer to the content of the error. <BR>
 *	<BR>
 *	<Parameter> *Mandatory Input<BR>
 *	<DIR>
 *		Consignor Code* <BR>
 *		Planned Picking Date* <BR>
 *		 Item / Order *<BR>
 *	</DIR>
 *	<BR>
 *	<Returned data> <BR>
 *	<DIR>
 *		Consignor Code<BR>
 *		Consignor Name<BR>
 *		Planned Picking Date <BR>
 *		 Item / Order <BR>
 *	</DIR>
 *	<BR>
 *	 <Process> <BR>
 *	<DIR>
 *		1. Check Exclusion. <BR>
 *		2. Process for deleting data. <BR>
 *		3.Process to print Deleted Data Process <BR>
 *		4. Execute the process to obtain the data to be displayed again. <BR>
 *	</DIR>
 *	<BR>
 *	 <Check Exclusion.> <BR>
 *	<DIR>
 *		-Only for Inventory package disabled, search through the Work Status Info using the following search conditions (Lock the DB), and check for availability of data. If no data, trigger error. <BR>
 *		-Search through the Picking Plan Info using the following search condition, and obtain the Count. Compare the values of data count between this Picking Plan Info and the parameter, and
 *		 regard it as normal if the Data count in the parameter  =  Data count in the Picking Plan Info, or regard it as error if <BR>
 *		<DIR>
 *			<Search conditions><BR>
 *			<DIR>
 *				Consignor Code<BR>
 *				Planned Picking Date<BR>
 *				 Item / Order (for Item data: Order No. is NULL, for Order data: Order No. is not NULL) <BR>
 *				 status = Standby <BR>
 *			</DIR>
 *		</DIR>
 *		<BR>
 *		 Set the error message No.{0} Unable to process this data. It has been updated via other work station. , when error occurs due to either one of above stated checks,
 *		 and return null. <BR>
 *	</DIR>
 * <BR>
 *   <Process for deleting data>  <BR>
 *     Note)Update the table in the order of Work Status Info (only for data with Inventory package disabled), Picking Plan Info, and Inventory Info (only for data with Inventory package disabled) to prevent from dead-locking.  <BR>
 *		<DIR>
 *			-Search through the Picking Plan Info and obtain the Picking Plan unique key. <BR>
 *			<Search conditions><BR>
 *			<DIR>
 *				Consignor Code<BR>
 *				Planned Picking Date<BR>
 *				 Item / Order (for Item data: Order No. is NULL, for Order data: Order No. is not NULL) <BR>
 *				 status = Standby <BR>
 *			</DIR>
 *			<BR>
 *			Execute the following processes for the count of the obtained data of Picking Plan Info. <BR>
 *			<DIR>
 *				- Update the Work Status Info Table (DNWORKINFO) (Only when no inventory package)  <BR>
 *				<DIR>
 *					Update the Status flag of the Work Status linked to the Picking Plan unique key of the obtained Picking Plan Info to Deleted.<BR>
 *				</DIR>
 *				<BR>
 *				-Update the Picking Plan Info table (DNRETRIEVALPLAN).  <BR>
 * 				<DIR>
 *					Update the Status flag of the Picking Plan Info linked to the Picking Plan unique key of the obtained Picking Plan Info to Deleted.<BR>
 *				</DIR>
 *				<BR>
 *				- Update Inventory Info Table (DMSTOCK). (Only for data with Inventory package disabled)  <BR>
 *				<DIR>
 *					- Update the Allocated qty of the Inventory Info linked to the Stock ID of the updated Work Status Info. <BR>
 *					- For data with status flag Standby for Picking of Inventory Info linked to the inventory ID of the updated Work Status Info, if the calculation result of Stock qty - Planned work qty becomes 0, 
 *					  update status flag to "Completed". <BR>
 *					- Update the Stock qty. <BR>
 *				</DIR>
 *			</DIR>
 *		</DIR>
 *	<BR>
 *	 <Print-out process of data to be deleted>  <BR>
 *	<DIR>
 *		-Print the Picking Plan Info linked to the Picking Plan unique key obtained in the Delete process using <CODE>RetrievalPlanDeleteWriter</CODE> class.<BR>
 *	</DIR>
 *	<BR>
 *	 <Process to obtain the data to be displayed again> <BR>
 *	<DIR>
 *		- Using Consignor Code + Start Planned Picking Date + End Planned Picking Date + Item / Order as keys, search through the Picking Plan Info. <BR>
 *		- If the search result count is 1 or more, return the result. If the search result count is 0, return the array with element qty 0 (zero). <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2007/02/07 04:20:01 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanDeleteSCH extends AbstractRetrievalSCH
{

	//#CM724663
	// Class variables -----------------------------------------------
	//#CM724664
	/**
	 * Process name 
	 */
	private static String wProcessName = "RetrievalPlanDeleteSCH";

	//#CM724665
	// Class method --------------------------------------------------
	//#CM724666
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.4 $,$Date: 2007/02/07 04:20:01 $" );
	}
	//#CM724667
	// Constructors --------------------------------------------------
	//#CM724668
	/**
	 * Initialize this class. 
	 */
	public RetrievalPlanDeleteSCH()
	{
		wMessage = null;
	}

	//#CM724669
	// Public methods ------------------------------------------------
	//#CM724670
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * Return the corresponding Consignor Code if only one kind of Consignor Code exists in the Picking Plan Info. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. 
	 * @return Class that implements the <CODE>RetrievalSupportParameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{
		//#CM724671
		// Set the corresponding Consignor code. 
		RetrievalSupportParameter retrievalParameter = new RetrievalSupportParameter();
		
		//#CM724672
		// Generate instance of Picking Plan Info Handlers. 
		RetrievalPlanSearchKey retrievalPlanSearchKey = new RetrievalPlanSearchKey();
		RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler( conn );
		RetrievalPlan retrievalPlan = null;
		
		try
		{
			//#CM724673
			// Set Search Condition
			//#CM724674
			// Status flag = "Standby " 
			retrievalPlanSearchKey.setStatusFlag( RetrievalPlan.STATUS_FLAG_UNSTART );
			//#CM724675
			// Schedule process flag 
			if (isStockPack(conn))
			{
				//#CM724676
				// Not Processed 
				retrievalPlanSearchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=");
			}
			//#CM724677
			// Set the Consignor Code for grouping condition. 
			retrievalPlanSearchKey.setConsignorCodeGroup( 1 );
			retrievalPlanSearchKey.setConsignorCodeCollect( "" );
			
			//#CM724678
			// If the result count is 1, search for it and set it for the retrievalParameter. 
			if(retrievalPlanHandler.count(retrievalPlanSearchKey) == 1)
			{
				try
				{
					retrievalPlan = (RetrievalPlan)retrievalPlanHandler.findPrimary(retrievalPlanSearchKey);
				}
				catch(NoPrimaryException e)
				{
					return new RetrievalSupportParameter();
				}
				retrievalParameter.setConsignorCode(retrievalPlan.getConsignorCode());
			}
			
		}
		catch ( ReadWriteException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
	    
		return retrievalParameter;
	}
	
	//#CM724679
	/**
	 * Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. 
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          If the number of search results exceeds the Max count of data allowable to display, or if error occurs with the input condition, return null. <BR>
	 *          Returning array with the number of elements 0 or null enables to obtain the error content as a message using the <CODE>getMessage</CODE> method. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] query( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{
		
		//#CM724680
		// search result
		RetrievalSupportParameter[] resultParameter = null;
		//#CM724681
		// Consignor Name
		String consignorName = "";
		
		//#CM724682
		// Generate instance of handlers for Picking Plan information. 
		RetrievalPlanHandler planHandler = new RetrievalPlanHandler( conn );
		RetrievalPlanDeleteHandler planDelHandler = new RetrievalPlanDeleteHandler( conn );
		RetrievalPlanSearchKey planSearchKey = new RetrievalPlanSearchKey();
		RetrievalPlanDelete[] retrieval2 = null;
				
		//#CM724683
		// Translate the data type of Parameter. 
		RetrievalSupportParameter parameter = (RetrievalSupportParameter) searchParam;
		
		//#CM724684
		// Check for Worker. 
		if(!checkWorker(conn, parameter))
		{
			return null;
		}
		 
		//#CM724685
		// Check the count of displayed data. 			
		if(!canLowerDisplay(getDisplayDataCount(conn, parameter)))
		{
			return returnNoDisplayParameter();
		}
		
		//#CM724686
		// Set the search conditions.
		setCommonSearchCondition(planSearchKey, parameter, false);
		//#CM724687
		// status = Standby 
		planSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
		//#CM724688
		// Schedule process flag 
		if (isStockPack(conn))
		{
			//#CM724689
			// Not Processed 
			planSearchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=");
		}

		//#CM724690
		// Set the grouping condition. 
		planSearchKey.setConsignorCodeCollect("");
		planSearchKey.setPlanDateCollect("");
		planSearchKey.setPlanDateGroup(1);
		planSearchKey.setConsignorCodeGroup(2);
		
		//#CM724691
		// Set the sorting order. 
		planSearchKey.setPlanDateOrder(1, true);
		
		//#CM724692
		// Search 
		retrieval2 = (RetrievalPlanDelete[])planDelHandler.find(planSearchKey);
		
		//#CM724693
		// Obtain the Consignor Name with the latest Added Date/Time. 
		consignorName = getDisplayConsignorName(conn, parameter);
		
		//#CM724694
		// Generate a Vector instance. 
		Vector vec = new Vector();
		
		boolean wContinueFlag = false;
		
		for ( int i = 0; i < retrieval2.length; i ++ )
		{
			RetrievalPlanSearchKey notDeletePlanSearchKey = new RetrievalPlanSearchKey();

			notDeletePlanSearchKey.setConsignorCode(retrieval2[i].getConsignorCode());
			notDeletePlanSearchKey.setPlanDate(retrieval2[i].getPlanDate());
			if(parameter.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
			{
				notDeletePlanSearchKey.setCaseOrderNo("", "IS NULL");
				notDeletePlanSearchKey.setPieceOrderNo("", "IS NULL");
			}
			else if(parameter.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ORDER))
			{
				notDeletePlanSearchKey.setCaseOrderNo("", "IS NOT NULL", "(", "", "OR");
				notDeletePlanSearchKey.setPieceOrderNo("", "IS NOT NULL", "", ")", "AND");
			}

			//#CM724695
			// Schedule process flag 
			if (isStockPack(conn))
			{
				//#CM724696
				// other than "Not Processed" 
				notDeletePlanSearchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "!=");
			}
			else
			{
				//#CM724697
				// other than Standby or Deleted 
				notDeletePlanSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART, "!=");
				notDeletePlanSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			}
		
			int count = planHandler.count(notDeletePlanSearchKey);
			if (count > 0)
			{
				//#CM724698
				// Disable to display in the preset area. 
				wContinueFlag = true;
				continue;
			}				
			
			RetrievalSupportParameter temp = new RetrievalSupportParameter();
			
			//#CM724699
			// Consignor Code
			temp.setConsignorCode(retrieval2[i].getConsignorCode());
			//#CM724700
			// Consignor Name
			temp.setConsignorName(consignorName);
			//#CM724701
			// Planned Picking Date
			temp.setRetrievalPlanDate(retrieval2[i].getPlanDate());

			temp.setItemOrderFlag(getItemOrderNmValue(retrieval2[i].getItemOrderFlag()));

			vec.addElement(temp);
		}
		resultParameter = new RetrievalSupportParameter[vec.size()];
		vec.copyInto( resultParameter );
		
		if (resultParameter != null && resultParameter.length > 0)
		{
			//#CM724702
			// 6001013 = Data is shown. 
			wMessage = "6001013";
		}
		else if (wContinueFlag)
		{
			if (isStockPack(conn))
			{
				//#CM724703
				// 6023410=Allocated data already exists. Cannot display the data. 
				wMessage = "6023410";
			}
			else
			{
				//#CM724704
				// 6023373=Status flag other than Standby exists. Cannot display. 
				wMessage = "6023373";
			}
			return new RetrievalSupportParameter[0];
		}
	
		return resultParameter;
	}


	//#CM724705
	/**
	 * Receive the contents entered via screen as a parameter, and start the schedule to delete the Picking plan data. <BR>
	 * Assume that two or more data may be input, including setting via preset area. So, require the parameter to receive them as an array. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * Re-obtain the data to be output to the preset area from database when the process normally completed, and return it.  <BR>
	 * Return null when the schedule failed to complete due to condition error or other causes. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalParameter</CODE> class instance that has setting contents 
	 *         Designating any instance other than RetrievalParameter instance throws <CODE>ScheduleException</CODE>. 
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          Return null when the schedule failed to complete due to condition error or other causes. <BR>
	 *          Returning array with the number of elements 0 or null enables to obtain the error content as a message using the <CODE>getMessage</CODE> method. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] startSCHgetParams( Connection conn, Parameter[] startParams ) 
	    throws ReadWriteException, ScheduleException
	{
		boolean registFlag = false;

		//#CM724706
		// Translate the data type of Parameter. 
		RetrievalSupportParameter[] parameter = ( RetrievalSupportParameter[] )startParams;
			
		//#CM724707
		// Check for Worker. 
		if(!checkWorker(conn, parameter[0]))
		{
			return null;
		}
		//#CM724708
		//	 Check if date/time is under updating 
		if(isDailyUpdate(conn))
		{		
			return null;
		}
		//#CM724709
		//	 Check Processing Load 
		if(isLoadingData(conn))
		{		
			return null;
		}
		
		//#CM724710
		// For maintaining the Picking Plan unique key 
		Vector retrievalPlanUKey = new Vector();
		
		//#CM724711
		// Flag to determine message 
		boolean messageFlag = false;

		//#CM724712
		// Allow this flag to determine whether Processing Load flag is updated in its own class. 
		boolean updateLoadDataFlag = false;

		try
		{
			//#CM724713
			// Update "Load" flag: "Loading in process" 
			if (!updateLoadDataFlag(conn, true))
			{
				return null;
			}
			doCommit(conn,wProcessName);
			updateLoadDataFlag = true;
			
			//#CM724714
			// Delete the Plan data. 
			if (isStockPack(conn))
			{
				registFlag = deleteRetrievalPlanStockOn(conn, startParams, retrievalPlanUKey);
			}
			else
			{
				registFlag =  deleteRetrievalPlanStockOff(conn, startParams, retrievalPlanUKey);
			}
			
			if (!registFlag)
			{
				return null;
			}
			
			//#CM724715
			// Search through the Picking Plan Info again. 
			RetrievalSupportParameter[] viewParam = ( RetrievalSupportParameter[] )query( conn, parameter[0] );
					
			if(retrievalPlanUKey.size() != 0)
			{
				if(parameter[0].getDeleteRetrievalListFlag())
				{
					//#CM724716
					// Process to print Deleted Data Process 
					RetrievalPlanDeleteWriter deleteWriter = new RetrievalPlanDeleteWriter(conn);
					deleteWriter.setRetrievalPlanUKey(retrievalPlanUKey);
				
					//#CM724717
					// Print 
					if(deleteWriter.startPrint())
					{
						//#CM724718
						// Update the Message flag to true. 
						messageFlag = true ;
					}
					if(messageFlag)
					{
						//#CM724719
						// 6021013=Printing has been normally completed after deleted. 
						wMessage = "6021013";
					}
					else
					{
						//#CM724720
						// 6007043=Failed to print it after deleted. See log. 
						wMessage = "6007043";
					}
				}
				else
				{
					//#CM724721
					// Deleted. 
					wMessage = "6001005";
				}
			}
			else
			{
				//#CM724722
				// No delete target data found. 
				wMessage = "6003014";
			}
		
			//#CM724723
			// Return the latest Picking Plan Info 
			return viewParam;
			
		}
		catch (NotFoundException e)
		{
			doRollBack(conn, wProcessName);
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			doRollBack(conn, wProcessName);
			throw new ScheduleException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			doRollBack(conn, wProcessName);
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			doRollBack(conn, wProcessName);
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			//#CM724724
			// Failing to add rolls back the transaction. 
			if (!registFlag)
			{
				doRollBack(conn,wProcessName);
			}

			//#CM724725
			// If Processing Extraction flag was updated in its own class,
			//#CM724726
			// update the Loading in-process flag to 0: Stopping. 
			if( updateLoadDataFlag )
			{
				updateLoadDataFlag(conn, false);
				doCommit(conn,wProcessName);
			}
			
		}
	}
	
	//#CM724727
	// Package methods -----------------------------------------------

	//#CM724728
	// Protected methods ---------------------------------------------

	//#CM724729
	// Private methods -----------------------------------------------
	//#CM724730
	/**
	 * Obtain the Consignor Name for display in screen. 
	 * @param	conn Instance to maintain database connection. 
	 * @param	parameter <CODE>RetrievalSupportParameter</CODE> class instance with search conditions
	 * @return	Consignor Name
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	private String getDisplayConsignorName(Connection conn, RetrievalSupportParameter parameter)
		throws ReadWriteException, ScheduleException
	{
		String consignorName = "";
		
		//#CM724731
		// Generate instance of Picking Plan Info Handlers. 
		RetrievalPlanReportFinder retrievalPlanFinder = new RetrievalPlanReportFinder(conn);
		RetrievalPlanSearchKey consignorSearchKey = new RetrievalPlanSearchKey(); 
		RetrievalPlan[] consignor = null;
		
		//#CM724732
		// Set the search conditions.
		setCommonSearchCondition(consignorSearchKey, parameter, false);
		//#CM724733
		// Schedule process flag 
		if (isStockPack(conn))
		{
			//#CM724734
			// Not Processed 
			consignorSearchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=");
		}	
		//#CM724735
		// Obtain the Consignor Name with the latest Added Date/Time. 
		consignorSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		consignorSearchKey.setRegistDateOrder( 1, false );
		retrievalPlanFinder.open();
			
		if (retrievalPlanFinder.search( consignorSearchKey ) > 0)
		{
			consignor = (RetrievalPlan[])retrievalPlanFinder.getEntities(1);
			if(consignor != null && consignor.length > 0)
			{
				consignorName = consignor[0].getConsignorName();
			}
		}
		retrievalPlanFinder.close();
		
		return consignorName;
	}
	
	//#CM724736
	/**
	 * Check for any change via other terminal. <BR>
	 * Search through the Work Status using the data set in the parameter (lock the database) and obtain the target data. <BR>
	 * If the obtained count of data is 0, regard that it was updated via other terminal, and return false. <BR>
	 * @param conn Database connection object
	 * @param parameter <CODE>RetrievalSupportParameter</CODE> class instance with search conditions
	 * @return If it has been modified via other terminal, return false. If not yet modified, return true. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected boolean lock(Connection conn, RetrievalSupportParameter parameter)
		throws ReadWriteException
	{
		//#CM724737
		// Generate instance of Work Status Info Handlers. 
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		WorkingInformation[] workInfo = null;
		
		//#CM724738
		// Set the search conditions.
		//#CM724739
		// Consignor Code
		if(!StringUtil.isBlank(parameter.getConsignorCode()))
		{
			workInfoSearchKey.setConsignorCode(parameter.getConsignorCode());
		}
		//#CM724740
		// Planned Picking Date
		if(!StringUtil.isBlank(parameter.getRetrievalPlanDate()))
		{
			workInfoSearchKey.setPlanDate(parameter.getRetrievalPlanDate());
		}
		//#CM724741
		// Item / Order 
		if(parameter.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
		{
			workInfoSearchKey.setOrderNo("", "IS NULL");
		}
		else if(parameter.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ORDER))
		{
			workInfoSearchKey.setOrderNo("", "IS NOT NULL");
		}
		//#CM724742
		// other than Deleted 
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		
		//#CM724743
		// Search and lock the DB. 
		workInfo = (WorkingInformation[])workInfoHandler.findForUpdate(workInfoSearchKey);
		
		if(workInfo == null && workInfo.length == 0)
		{
			return false;
		}
		
		return true;
	}
	
	//#CM724744
	/**
	 * Check for any change via other terminal. <BR>
	 * Search through the Picking Plan Info using the data set in the parameter (lock the database) and obtain the target data. <BR>
	 * Return false if the obtained count of data is 0. <BR>
	 * @param conn Database connection object
	 * @param parameter <CODE>RetrievalSupportParameter</CODE> class instance with search conditions
	 * @return If it has been modified via other terminal, return false. If not yet modified, return true. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	private boolean lockPlan(Connection conn, RetrievalSupportParameter parameter)
		throws ReadWriteException, ScheduleException
	{

		//#CM724745
		// Search through the DnRetrievalPlan. 
		RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey planKey = new RetrievalPlanSearchKey();
		RetrievalPlan[] retPlan = null;
		
		//#CM724746
		// Set the search conditions.
		//#CM724747
		// Consignor Code
		if(!StringUtil.isBlank(parameter.getConsignorCode()))
		{
			planKey.setConsignorCode(parameter.getConsignorCode());
		}
		//#CM724748
		// Planned Picking Date
		if(!StringUtil.isBlank(parameter.getRetrievalPlanDate()))
		{
			planKey.setPlanDate(parameter.getRetrievalPlanDate());
		}
		//#CM724749
		// other than Deleted 
		planKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		
		//#CM724750
		// Schedule process flag 
		if (isStockPack(conn))
		{
			//#CM724751
			// Not Processed 
			planKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=");
		}

		//#CM724752
		// Search and lock the Plan Info. 
		retPlan = (RetrievalPlan[]) planHandler.findForUpdate(planKey);

		//#CM724753
		// If there is no search result (data was deleted) 
		if (retPlan == null && retPlan.length == 0)
		{
			return false;
		}

		return true;
	}
	
	//#CM724754
	/**
	 * Set the search condition for Picking Plan Info commonly used in the <CODE>RetrievalPlanDeleteSCH</CODE> class 
	 * for the searchKey. 
	 * @param	key			Key object to set the search conditions 
	 * @param	parameter	 Parameter input via screen 
	 * @param	dateType	 Search type of Planned date: true: Planned Picking Date, or false: Start/End Planned Picking Date 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	protected void setCommonSearchCondition(RetrievalPlanSearchKey key, RetrievalSupportParameter parameter, boolean dateType)
		throws ReadWriteException, ScheduleException
	{		
		//#CM724755
		// Set the search conditions.
		//#CM724756
		// Consignor Code
		if(!StringUtil.isBlank(parameter.getConsignorCode()))
		{
			key.setConsignorCode(parameter.getConsignorCode());
		}
		//#CM724757
		// Planned Picking Date
		if(dateType)
		{
			key.setPlanDate(parameter.getRetrievalPlanDate());
		}
		else
		{
			//#CM724758
			// Start Planned Picking Date
			if(!StringUtil.isBlank(parameter.getFromRetrievalPlanDate()))
			{
				key.setPlanDate(parameter.getFromRetrievalPlanDate(), ">=");
			}
			//#CM724759
			// End Planned Picking Date
			if(!StringUtil.isBlank(parameter.getToRetrievalPlanDate()))
			{
				key.setPlanDate(parameter.getToRetrievalPlanDate(), "<=");
			}
		}
		//#CM724760
		// Item / Order 
		if(parameter.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
		{
			key.setCaseOrderNo("", "IS NULL");
			key.setPieceOrderNo("", "IS NULL");
		}
		else if(parameter.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ORDER))
		{
			key.setCaseOrderNo("", "IS NOT NULL", "(", "", "OR");
			key.setPieceOrderNo("", "IS NOT NULL", "", ")", "AND");
		}
	}
	

	
	//#CM724761
	/**
	 * Obtain the count of data to be displayed in the Preset area. <BR>
	 * @param	conn		 Instance to maintain database connection. 
	 * @param	parameter	 <CODE>RetrievalSupportParameter</CODE> object for which search conditions are set 
	 * @return Count of displayed data 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	private int getDisplayDataCount(Connection conn, RetrievalSupportParameter parameter) throws ReadWriteException, ScheduleException
	{
		//#CM724762
		// Count of the data that can be displayed 
		int count = 0;
		
		//#CM724763
		// Generate instance of Picking Plan Info Handlers. 
		RetrievalPlanSearchKey retrievalPlanSearchKey = new RetrievalPlanSearchKey();
		RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler( conn );
			
		//#CM724764
		// Set Search Condition 
		setCommonSearchCondition(retrievalPlanSearchKey, parameter, false);
		//#CM724765
		// status = Standby 
		retrievalPlanSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
		//#CM724766
		// Schedule process flag 
		if (isStockPack(conn))
		{
			//#CM724767
			// Not Processed 
			retrievalPlanSearchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=");
		}	
		//#CM724768
		// Set the grouping condition. 
		retrievalPlanSearchKey.setConsignorCodeCollect("");
		retrievalPlanSearchKey.setPlanDateCollect("");
		retrievalPlanSearchKey.setPlanDateGroup(1);
		retrievalPlanSearchKey.setConsignorCodeGroup(2);
			
		//#CM724769
		// Set the sorting order. 
		retrievalPlanSearchKey.setPlanDateOrder(1, true);
			
		//#CM724770
		// Search 
		RetrievalPlan[] retrieval = (RetrievalPlan[])retrievalPlanHandler.find(retrievalPlanSearchKey);
			
		//#CM724771
		// Obtain the Count of data that can be displayed. 
		int notDeleteCount = 0 ;
		RetrievalPlanSearchKey notDeletePlanSearchKey = new RetrievalPlanSearchKey();
		for(int i = 0; i < retrieval.length; i++)
		{
			notDeletePlanSearchKey.setConsignorCode(retrieval[i].getConsignorCode());
			notDeletePlanSearchKey.setPlanDate(retrieval[i].getPlanDate());
			if(parameter.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
			{
				notDeletePlanSearchKey.setCaseOrderNo("", "IS NULL");
				notDeletePlanSearchKey.setPieceOrderNo("", "IS NULL");
			}
			else if(parameter.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ORDER))
			{
				notDeletePlanSearchKey.setCaseOrderNo("", "IS NOT NULL", "(", "", "OR");
				notDeletePlanSearchKey.setPieceOrderNo("", "IS NOT NULL", "", ")", "AND");
			}
				
			//#CM724772
			// Schedule process flag 
			if (isStockPack(conn))
			{
				notDeletePlanSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
				//#CM724773
				// Not Processed 
				notDeletePlanSearchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=");
			}
			else
			{
				notDeletePlanSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART, "!=");
				notDeletePlanSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			}
				
			int rCount = retrievalPlanHandler.count(notDeletePlanSearchKey);
			if (rCount > 0)
			{
				notDeleteCount++;
			}
			notDeletePlanSearchKey.KeyClear();
		}
		count = retrieval.length;
		
		return count;
	}
	
	//#CM724774
	/**
	 * Delete the Picking Plan data using the Plan unique key passed as a parameter. 
	 * @param	conn				 Instance to maintain database connection. 
	 * @param	retrievalPlanUKey	Picking Plan unique key
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws NotFoundException Announce when info to be changed is not found.
	 * @throws InvalidDefineException Announce when the designated value is abnormal (blank or containing prohibited characters).
	 */
	protected void deleteRetrievalPlan(Connection conn, String retrievalPlanUKey) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		RetrievalPlanHandler retrievalHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanAlterKey retrievalAlterKey = new RetrievalPlanAlterKey();
		
		//#CM724775
		// Set a value for update. 
		retrievalAlterKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE);
		retrievalAlterKey.updateLastUpdatePname(wProcessName);
		//#CM724776
		// Set Search Condition 
		retrievalAlterKey.setRetrievalPlanUkey(retrievalPlanUKey);
		
		//#CM724777
		// Update 
		retrievalHandler.modify(retrievalAlterKey);
	}
	
	//#CM724778
	/**
	 * Return the respective names of the passed Item/Order divisions. 
	 * @param itemOrderFlag Item/Order division
	 * @return	 Item/ Order division name 
	 */
	private String getItemOrderNmValue(String itemOrderFlag)
	{
		String value = "";
		
		//#CM724779
		// All 
		if(itemOrderFlag.equals(RetrievalSupportParameter.ITEMORDERFLAG_ALL))
		{
			value = DisplayText.getText("LBL-W0346");
		}
		//#CM724780
		// Item 
		else if(itemOrderFlag.equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
		{
			value = DisplayText.getText("LBL-W0434");
		}
		//#CM724781
		// Order 
		else if(itemOrderFlag.equals(RetrievalSupportParameter.ITEMORDERFLAG_ORDER))
		{
			value = DisplayText.getText("LBL-W0435");
		}
		
		return value;
	}
	
	//#CM724782
	/**
	 * Allow this method to execute the process for deleting the data with Inventory package enabled. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalParameter</CODE> class instance that has setting contents
	 * @param retrievalPlanUKey Picking Plan unique key
	 * @return Result of delete (Normal: true, Error: false) 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws Exception Announce when all of exceptions occur. 
	 */
	protected boolean deleteRetrievalPlanStockOn(Connection conn, Parameter[] startParams, Vector retrievalPlanUKey) throws ReadWriteException, Exception
	{
		boolean registFlag = false;
		
		//#CM724783
		// Translate the data type of Parameter. 
		RetrievalSupportParameter[] parameter = ( RetrievalSupportParameter[] )startParams;
			
		//#CM724784
		// Generate instance of Picking Plan Info Handlers. 
		RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler( conn );
		RetrievalPlanSearchKey retrievalPlanSearchKey = new RetrievalPlanSearchKey();
		RetrievalPlan[] retrieval = null;
			
		for(int i=0; i < parameter.length; i++)
		{
			//#CM724785
			// Lock the potential target data. 
			if(!lockPlan(conn, parameter[i]))
			{
				//#CM724786
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + parameter[i].getRowNo();
				return registFlag;
			}
			
			//#CM724787
			// Check whether The status of the target data changed or not. 
			RetrievalPlanSearchKey notDeletePlanSearchKey = new RetrievalPlanSearchKey();

			setCommonSearchCondition(notDeletePlanSearchKey, parameter[i], true);
			//#CM724788
			// other than "Not Processed" 
			notDeletePlanSearchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "!=");
			
			int count = retrievalPlanHandler.count(notDeletePlanSearchKey);
			if (count > 0)
			{
				String delim = MessageResource.DELIM;
				//#CM724789
				// No.{0} Unable to process this data. It has been updated via other work station. 
				String msg = "6023209" + delim + parameter[i].getRowNo();
				wMessage = msg;
			
				return registFlag;
			}
			
			//#CM724790
			// Process for deleting data. 
			//#CM724791
			// Obtain the target data. 
			//#CM724792
			// Set the search conditions.
			retrievalPlanSearchKey.KeyClear();
			setCommonSearchCondition(retrievalPlanSearchKey, parameter[i], true);
			//#CM724793
			// Not Processed 
			notDeletePlanSearchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=");
			retrievalPlanSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			//#CM724794
			// Search 
			retrieval = (RetrievalPlan[])retrievalPlanHandler.find(retrievalPlanSearchKey);

			//#CM724795
			// Delete the picking plan info. 
			for(int j = 0; j < retrieval.length; j++)
			{
				//#CM724796
				// Delete the Picking plan data. 
				deleteRetrievalPlan(conn, retrieval[j].getRetrievalPlanUkey());

				retrievalPlanUKey.addElement(retrieval[j].getRetrievalPlanUkey());
			}
			
		}
		
		registFlag = true;
		return registFlag;
	}
	
	//#CM724797
	/**
	 * Allow this method to execute the process for deleting the data with Inventory package disabled. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalParameter</CODE> class instance that has setting contents
	 * @param retrievalPlanUKey Picking Plan unique key
	 * @return Result of delete (Normal: true, Error: false) 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws Exception Announce when all of exceptions occur. 
	 */
	protected boolean deleteRetrievalPlanStockOff(Connection conn, Parameter[] startParams, Vector retrievalPlanUKey) throws ReadWriteException, Exception
	{
		boolean registFlag = false;

		//#CM724798
		// Translate the data type of Parameter. 
		RetrievalSupportParameter[] parameter = ( RetrievalSupportParameter[] )startParams;
			
		//#CM724799
		// Generate instance of Picking Plan Info Handlers. 
		RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler( conn );
		RetrievalPlanSearchKey retrievalPlanSearchKey = new RetrievalPlanSearchKey();
		RetrievalPlan[] retrieval = null;

		for(int i=0; i < parameter.length; i++)
		{
			//#CM724800
			// Lock the potential target data. 
			if(!lock(conn, parameter[i]))
			{
				//#CM724801
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + parameter[i].getRowNo();
				return registFlag;
			}
			
			//#CM724802
			// Check whether The status of the target data changed or not. 
			RetrievalPlanSearchKey notDeletePlanSearchKey = new RetrievalPlanSearchKey();

			setCommonSearchCondition(notDeletePlanSearchKey, parameter[i], true);
			notDeletePlanSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART, "!=");
			notDeletePlanSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			
			int count = retrievalPlanHandler.count(notDeletePlanSearchKey);
			if (count > 0)
			{
				String delim = MessageResource.DELIM;
				//#CM724803
				// No.{0} Unable to process this data. It has been updated via other work station. 
				String msg = "6023209" + delim + parameter[i].getRowNo();
				wMessage = msg;
			
				return registFlag;
			}
			
			//#CM724804
			// Process for deleting data. 
			//#CM724805
			// Obtain the target data. 
			//#CM724806
			// Set the search conditions.
			retrievalPlanSearchKey.KeyClear();
			setCommonSearchCondition(retrievalPlanSearchKey, parameter[i], true);
			retrievalPlanSearchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			//#CM724807
			// Search 
			retrieval = (RetrievalPlan[])retrievalPlanHandler.find(retrievalPlanSearchKey);

			//#CM724808
			// Delete the picking plan info and cancel its allocation. 
			RetrievalAllocateOperator retrievalAllocateOperator = new RetrievalAllocateOperator(conn);
			for(int j = 0; j < retrieval.length; j++)
			{
				//#CM724809
				// Delete the Picking plan data. 
				deleteRetrievalPlan(conn, retrieval[j].getRetrievalPlanUkey());
				
				//#CM724810
				// Cancel allocation 
				retrievalAllocateOperator.cancel(
					conn,
					retrieval[j].getRetrievalPlanUkey(),
					parameter[0].getWorkerCode(),
					parameter[0].getWorkerName(),
					parameter[0].getTerminalNumber(),
					wProcessName);

				retrievalPlanUKey.addElement(retrieval[j].getRetrievalPlanUkey());
			}
			
		}
		
		registFlag = true;
		return registFlag;
	}
}
//#CM724811
//end of class
