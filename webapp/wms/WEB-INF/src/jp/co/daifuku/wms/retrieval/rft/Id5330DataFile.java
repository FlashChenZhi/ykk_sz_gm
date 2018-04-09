// $Id: Id5330DataFile.java,v 1.3 2007/02/07 04:19:42 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.rft.IdMessage;
import jp.co.daifuku.wms.base.rft.WorkDataFile;
import jp.co.daifuku.wms.base.rft.WorkingInformation;

//#CM721055
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM721056
/**
 * Allow this class to operate a data file to be sent or received by ID0330.
 * <p>
 * <table border="1">
 * <CAPTION>Structure of each line in the ID5330 file</CAPTION>
 * <TR><TH>Field item name</TH>			<TH>Length</TH>	<TH>Content</TH></TR>
 * <tr><td>Line No</td>			<td>4 byte</td>	<td>'   0' to '9999'</td></tr>
 * <tr><td>the setting unit key</td>	<td>16 byte</td><td>Not available</td></tr>
 * <tr><td>Order No.</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>Picking No.</td>			<td>8 byte</td>	<td>Not available</td></tr>
 * <tr><td>Customer Code</td>	<td>16 byte</td><td> </td></tr>
 * <tr><td>Customer Name</td>		<td>40 byte</td><td> </td></tr>
 * <tr><td>Customer title</td>		<td>20 byte</td><td>Not available</td></tr>
 * <tr><td>Area No.</td>		<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Zone No.</td>		<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Location No.</td>	<td>16 byte</td><td> </td></tr>
 * <tr><td>Location Position</td>			<td>1 byte</td>	<td> '0': Both, '1': Right, '2': Left</td></tr>
 * <tr><td>Processing Replenish flag</td>	<td>1 byte</td>	<td>Not available ( '0': Not Replenished, '1': Replenishing in Process)</td></tr>
 * <tr><td>Aggregation Work No.</td>		<td>16 byte</td><td>Set it left aligned.</td></tr>
 * <tr><td>Item Code</td>		<td>16 byte</td><td>Not available</td></tr>
 * <tr><td>JAN Code</td>		<td>16 byte</td><td>Left aligned</td></tr>
 * <tr><td>Bundle ITF</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>Case ITF</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>Item Name</td>		<td>40 byte</td><td> </td></tr>
 * <tr><td>Item Category</td>		<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>Packed qty per bundle</td>		<td>6 byte</td>	<td> </td></tr>
 * <tr><td>Packed Qty per Case</td>		<td>6 byte</td>	<td> </td></tr>
 * <tr><td>Unit</td>			<td>6 byte</td>	<td>Not available</td></tr>
 * <tr><td>Lot No.</td>		<td>10 byte</td><td>Not available</td></tr>
 * <tr><td>Expiry Date</td>		<td>8 byte</td>	<td> </td></tr>
 * <tr><td>Manufactured Date</td>			<td>8 byte</td>	<td>Not available</td></tr>
 * <tr><td>Instructed picking qty</td>		<td>9 byte</td>	<td> </td></tr>
 * <tr><td>Picking Result qty</td>		<td>9 byte</td>	<td>0 set</td><tr>
 * <tr><td>Case/Piece division	<td>1 byte</td>	<td>1: Case, 2: Piece, 3: None</td></tr>
 * <tr><td>Sorting Area No.			<td>3 byte</td>	<td>Not available</td></tr>
 * <tr><td>Sorting Block No.		<td>3 byte</td>	<td>Not available</td></tr>
 * <tr><td>Sorting Location No.	<td>16 byte</td><td>Not available</td></tr>
 * <tr><td>Sorting No.</td>		<td>8 byte</td>	<td>Not available</td></tr>
 * <tr><td>Package No.</td>			<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>Work Time</td>		<td>5 byte</td>	<td>seconds<BR>
 * 												   00000 to send data from Server</td></tr>
 * <tr><td>"Complete" flag</td>		<td>1 byte</td>	<td>0: Not Worked, 1: Completed, 2: Shortage Completed <br>
 * 												   3: Change Box, 5: Completion Processed</td></tr>
 * <tr><td>CRLF</td>			<td>2 byte</td>	<td>CR + LF</td></tr>
 * </table>
 * </p>
 * 
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:42 $
 * @author $Author: suresh $
 */
public class Id5330DataFile extends WorkDataFile
{
	//#CM721057
	/**
	 * Offset the the setting unit key of the Result data.
	 */
	private static final int OFF_SET_UNIT_KEY = OFF_LINE_NO + LEN_LINE_NO;
	
	//#CM721058
	/**
	 * Offset the Order No. of the Result data.
	 */
	private static final int OFF_ORDER_NO = OFF_SET_UNIT_KEY + LEN_SET_UNIT_KEY;
	
	//#CM721059
	/**
	 * Offset the Picking No. of the Result data.
	 */
	private static final int OFF_PICKING_NO = OFF_ORDER_NO + LEN_ORDER_NO;
	
	//#CM721060
	/**
	 * Offset the Customer Code of the Result data.
	 */
	private static final int OFF_CUSTOMER_CODE = OFF_PICKING_NO + LEN_PICKING_NO;
	
	//#CM721061
	/**
	 * Offset the Customer Name of the Result data.
	 */
	private static final int OFF_CUSTOMER_NAME = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;
	
	//#CM721062
	/**
	 * Offset the Customer title of the Result data.
	 */
	private static final int OFF_CUSTOMER_PREFIX = OFF_CUSTOMER_NAME + LEN_CUSTOMER_NAME;
	
	//#CM721063
	/**
	 * Offset the Area No. of the Result data.
	 */
	private static final int OFF_AREA_NO = OFF_CUSTOMER_PREFIX + LEN_CUSTOMER_PREFIX;
	
	//#CM721064
	/**
	 * Offset the Zone No. of the Result data.
	 */
	private static final int OFF_ZONE_NO = OFF_AREA_NO + LEN_AREA_NO;
	
	//#CM721065
	/**
	 * Offset the Location of the Result data.
	 */
	private static final int OFF_JOB_LOCATION = OFF_ZONE_NO + LEN_ZONE_NO;
	
	//#CM721066
	/**
	 * Offset the Location Position.
	 */
	private static final int OFF_LOCATION_SIDE = OFF_JOB_LOCATION + LEN_LOCATION;
	
	//#CM721067
	/**
	 * Offset the Processing Replenish flag of the Result data.
	 */
	private static final int OFF_REPRENISHMENT_FLAG = OFF_LOCATION_SIDE + LEN_LOCATION_SIDE;
	
	//#CM721068
	/**
	 * Offset the Item identification code of the Result data.
	 */
	private static final int OFF_COLLECT_JOB_NO = OFF_REPRENISHMENT_FLAG + LEN_REPLENISHING_FLAG;
	
	//#CM721069
	/**
	 * Offset the Offset Item Code of the Result data.
	 */
	private static final int OFF_ITEM_CODE = OFF_COLLECT_JOB_NO + LEN_COLLECT_JOB_NO;
	
	//#CM721070
	/**
	 * Offset the JAN Code of the Result data.
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE;
	
	//#CM721071
	/**
	 * Offset the Bundle ITF of the Result data.
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE;
	
	//#CM721072
	/**
	 * Offset the Case ITF of the Result data.
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF;
	
	//#CM721073
	/**
	 * Offset the Item Name of the Result data.
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF;
	
	//#CM721074
	/**
	 * Offset the Item Category of the Result data.
	 */
	private static final int OFF_ITEM_CATEGORY_CODE = OFF_ITEM_NAME + LEN_ITEM_NAME;
	
	//#CM721075
	/**
	 * Offset the Packed qty per bundle of the Result data.
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_CATEGORY_CODE + LEN_ITEM_CATEGORY_CODE;
	
	//#CM721076
	/**
	 * Offset the Packed Qty per Case of the Result data.
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY;
	
	//#CM721077
	/**
	 * Offset the Unit of the Result data.
	 */
	private static final int OFF_UNIT = OFF_ENTERING_QTY + LEN_ENTERING_QTY;
	
	//#CM721078
	/**
	 * Offset the Lot No of the Result data.
	 */
	private static final int OFF_LOT_NO = OFF_UNIT + LEN_UNIT;
	
	//#CM721079
	/**
	 * Offset the Offset Expiry Date of the Result data.
	 */
	private static final int OFF_USE_BY_DATE = OFF_LOT_NO + LEN_LOT_NO;
	
	//#CM721080
	/**
	 * Offset the Manufactured Date of the Result data.
	 */
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
	
	//#CM721081
	/**
	 * Offset the Instructed picking qty of the Result data.
	 */
	private static final int OFF_PLAN_QTY = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE;
	
	//#CM721082
	/**
	 * Offset the Picking Result qty of the Result data.
	 */
	private static final int OFF_RESULT_QTY = OFF_PLAN_QTY + LEN_PLAN_QTY;
	
	//#CM721083
	/**
	 * Offset the Work Type (Case/Piece division) of the Result data.
	 */
	private static final int OFF_WORK_FORM = OFF_RESULT_QTY + LEN_RESULT_QTY;
	
	//#CM721084
	/**
	 * Offset the Sorting Area No. of the Result data.
	 */
	private static final int OFF_SORT_AREA_NO = OFF_WORK_FORM + LEN_CASE_PIECE_FLAG;
	
	//#CM721085
	/**
	 * Offset the Sorting Block No. of the Result data.
	 */
	private static final int OFF_SORT_BLOCK_NO = OFF_SORT_AREA_NO + LEN_AREA_NO;
	
	//#CM721086
	/**
	 * Offset the Sorting Location No. of the Result data.
	 */
	private static final int OFF_SORTING_LOCATION = OFF_SORT_BLOCK_NO + LEN_BLOCK_NO;
	
	//#CM721087
	/**
	 * Offset the Sorting No of the Result data.
	 */
	private static final int OFF_GROUPING_NO = OFF_SORTING_LOCATION + LEN_SORT_USE_BLOCK_NO;
	
	//#CM721088
	/**
	 * Offset the Package No of the Result data.
	 */
	private static final int OFF_PACK_NO = OFF_GROUPING_NO + LEN_GROUPING_NO;
	
	//#CM721089
	/**
	 * Offset the Work Time of the Result data.
	 */
	private static final int OFF_WORK_SECONDS = OFF_PACK_NO + LEN_PACK_NO;
	
	//#CM721090
	/**
	 * Offset the "Complete" flag of the Result data.
	 */
	private static final int OFF_COMPLETION_FLAG = OFF_WORK_SECONDS + LEN_WORK_TIME;
	
	//#CM721091
	/**
	 * "Complete" flag of a data file (0 Not Worked).
	 */
	protected static final String COMPLETION_FLAG_UNWORK = "0";
	
	//#CM721092
	/**
	 * "Complete" flag of a data file (1 "Completed").
	 */
	protected static final String COMPLETION_FLAG_COMPLETION = "1";
	
	//#CM721093
	/**
	 * "Complete" flag of a data file (2 ShortageCompleted).
	 */
	protected static final String COMPLETION_FLAG_SHORTAGE = "2";
	
	//#CM721094
	/**
	 * "Complete" flag of a data file (3: Change Box).
	 */
	protected static final String COMPLETION_FLAG_BOX_CHANGE = "3";
	
	//#CM721095
	/**
	 * "Complete" flag of a data file (5  Committed).
	 */
	protected static final String COMPLETION_FLAG_DECISION_PROSSING = "5";
	
	//#CM721096
	/**
	 * A field that represents a Picking Order List file name.
	 */
	static final String ANS_FILE_NAME = "ID5330.txt";

	//#CM721097
	// Constructors --------------------------------------------------
	//#CM721098
	/**
	 * Constructor
	 */
	public Id5330DataFile()
	{
		lineLength = OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG;
	}
	//#CM721099
	/**
	 * Constructor
	 * @param filename file name
	 */
	public Id5330DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG;
	}

	//#CM721100
	/**
	 * Load the data from the designated file and insert it in an appropriate type of Entity array, and return it.
	 * Note that the type of actual array depends on ID.
	 * @return		Data loaded from a file (Array of Entity)
	 * @throws IOException		I/O error occurs.
	 * @throws InvalidStatusException
	 */
    public Entity[] getWorkDataFromFile(String filename) throws IOException,
            InvalidStatusException
    {
        Vector v = new Vector();
		
		openReadOnly(filename);
			
		for (next(); currentLine != null; next())
		{
			WorkingInformation wi = new WorkingInformation();
			
			wi.setOrderNo(getOrderNo());
			wi.setCustomerCode(getCustomerCode());
			wi.setCustomerName1(getCustomerName());
			wi.setAreaNo(getAreaNo());
			wi.setZoneNo(getZoneNo());
			wi.setLocationNo(getLocationNo());
			wi.setLocationSide(getLocationSide());
			wi.setCollectJobNo(getCollectJobNo());
			wi.setItemCode(getJanCode());
			wi.setBundleItf(getBundleItf());
			wi.setItf(getItf());
			wi.setBundleEnteringQty(getBundleEnteringQty());
			wi.setEnteringQty(getEnteringQty());
			wi.setResultUseByDate(getUseByDate());
			wi.setPlanEnableQty(getPlanQty());
			wi.setResultQty(getResultQty());
			wi.setWorkFormFlag(getWorkForm());
			wi.setWorkTime(getWorkSeconds());
			wi.setStatusFlag(getCompletionFlag());
				
			v.addElement(wi);
		}
			
		closeReadOnly();

		WorkingInformation[] data = new WorkingInformation[v.size()];
		v.copyInto(data);
		return data;
	}

	//#CM721101
	/**
	 * Write the data with WorkingInformation array designated by argument to the file.
	 * 
	 * @param	obj				Data to be written to file (WorkingInformation array)
	 * @throws IOException		Error occurred in the process of input/output of file..
	 */
	public void write(Entity[] obj) throws IOException
	{
		WorkingInformation[] workinfo = (WorkingInformation[])obj;
		
		openWritable();
			
		for (int i = 0; i < workinfo.length; i ++)
		{
		    setLineNo(i);
			setOrderNo(workinfo[i].getOrderNo());
			setCustomerCode(workinfo[i].getCustomerCode());
			setCustomerName(workinfo[i].getCustomerName1());
			setAreaNo(workinfo[i].getAreaNo());
			setZoneNo(workinfo[i].getZoneNo());
			setLocationNo(workinfo[i].getLocationNo());
			setLocationSide(workinfo[i].getLocationSide());
			setCollectJobNo(workinfo[i].getCollectJobNo());
			setJanCode(workinfo[i].getItemCode());
			setBundleItf(workinfo[i].getBundleItf());
			setItf(workinfo[i].getItf());
			setItemName(workinfo[i].getItemName1());
			setBundleEnteringQty(workinfo[i].getBundleEnteringQty());
			setEnteringQty(workinfo[i].getEnteringQty());
			setUseByDate(workinfo[i].getUseByDate());
			setPlanQty(workinfo[i].getPlanEnableQty());
			setResultQty(0);
			setWorkForm(workinfo[i].getWorkFormFlag());
			setWorkSeconds(0);
			setCompletionFlag("0");
			
			writeln();
		}
			
		closeWritable();
	}
	
	//#CM721102
	/**
	 * Update the Work status of the data file for "Completed" data.<br>
	 * Update the work data to the following value using the values of the "Complete" flag of the data file.<br>
	 * <br>
	 * For data file with Complete flag "1: Completed" or "2: Shortage Completed":<br>
	 *  <dir>
	 * 		"Complete" flag: 5(Committed)<br>
	 * 	</dir>
	 * For data file with Complete flag "3: Change Box", and Instructed picking qty = Result qty:<br>
	 *	<dir>
	 * 		"Complete" flag: 5(Committed)<br>
	 * 	</dir>
	 * <br>
	 * For data file with Complete flag "3: Box-change " and " Instructed picking qty > Result qty":<br>
	 * 	<dir>
	 * 		"Complete" flag: 0(Not Worked)<br>
	 * 		Instructed picking qty: (Instructed picking qty in the former data file) - (Result qty)<br>
	 * 		Result qty : 0<br>
	 * 	</dir>
	 * 
	 * @param index
	 * @param out
	 * @throws IOException
	 * @throws InvalidStatusException
	 */
	public static void updateDataFileComplete(int index, RandomAccessFile out, jp.co.daifuku.wms.base.entity.WorkingInformation[] resultData)
		throws IOException, InvalidStatusException
	{
	    //#CM721103
	    // For data file with "Complete" flag "1:Completed", "2: Shortage Completed", "3: Change Box", and Planned Picking Qty = Result qty:
	    if(resultData[index].getStatusFlag().equals(COMPLETION_FLAG_COMPLETION)
		        || resultData[index].getStatusFlag().equals(COMPLETION_FLAG_SHORTAGE)
		        || (resultData[index].getStatusFlag().equals(COMPLETION_FLAG_BOX_CHANGE) 
		                && resultData[index].getPlanEnableQty() - resultData[index].getResultQty() == 0))
		{
			//#CM721104
			// Update the Work Update Data.
		    int offset = index * (OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG + LEN_CRLF);
			byte[] readBuffer = new byte[LEN_LINE_NO];
			int pos = 0;
			int len = 0;
			int restLength = LEN_LINE_NO;
			
			//#CM721105
			// Seek to the target data.
			out.seek(offset);
			while ((len = out.read(readBuffer, pos, restLength)) > 0)
			{
				pos += len;
				restLength -= len;
			}
			
			int lineNo = Integer.parseInt(new String(readBuffer).trim());
			if (lineNo != index)
			{
				//#CM721106
				// Index Error
				throw new InvalidStatusException();
			}
			out.seek(offset + OFF_COMPLETION_FLAG);
			out.write(COMPLETION_FLAG_DECISION_PROSSING.getBytes());
		}
	    //#CM721107
	    // For data file with "Complete" flag "3: Change Box", and Instructed picking qty > Result qty:
	    else if(resultData[index].getStatusFlag().equals(COMPLETION_FLAG_BOX_CHANGE)
	            && !(resultData[index].getPlanEnableQty() - resultData[index].getResultQty() == 0))	
	    {
		    //#CM721108
		    // Update the Work Update Data.
		    int offset = index * (OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG + LEN_CRLF);
			byte[] readBuffer = new byte[LEN_LINE_NO];
			int pos = 0;
			int len = 0;
			int restLength = LEN_LINE_NO;
			
			//#CM721109
			// Result qty
			final byte[] bResultQty ={32,32,32,32,32,32,32,32,48};	
			
			//#CM721110
			// Seek to the target data.
			out.seek(offset);
			while ((len = out.read(readBuffer, pos, restLength)) > 0)
			{
				pos += len;
				restLength -= len;
			}
			int lineNo = Integer.parseInt(new String(readBuffer).trim());
			if (lineNo != index)
			{
				//#CM721111
				// Index Error
				throw new InvalidStatusException();
			}
	
			//#CM721112
			// Instructed picking qty
			byte[] planQty = Integer.toString(resultData[index].getPlanEnableQty() - resultData[index].getResultQty()).getBytes();	
			byte[] bPlanQty =new byte[LEN_PLAN_QTY];
			int bcnt = 0;
			for(int cnt= 0; cnt < bPlanQty.length; cnt++)
			{
			    if(cnt < bPlanQty.length - planQty.length)
			    {
			        bPlanQty[cnt] = 32;
			    }
			    else
			    {
			        bPlanQty[cnt] = planQty[bcnt];
			        bcnt++;
			    }			    
			}
			out.seek(offset + OFF_PLAN_QTY);
			out.write(bPlanQty);
			out.seek(offset + OFF_RESULT_QTY);
			out.write(bResultQty);
			out.seek(offset + OFF_COMPLETION_FLAG);
			out.write(COMPLETION_FLAG_UNWORK.getBytes());
	    }
	}
	
	//#CM721113
	/**
	 * Obtain the the setting unit key from the loaded Result data.
	 * @return setUnitKey
	 */
	public String getSetUnitKey()
	{
		return getColumn(OFF_SET_UNIT_KEY, LEN_SET_UNIT_KEY);
	}
	
	//#CM721114
	/**
	 * Obtain the Order No. from the loaded Result data.
	 * @return	Order No.
	 */
	public String getOrderNo()
	{
		return getColumn(OFF_ORDER_NO, LEN_ORDER_NO);
	}
	
	//#CM721115
	/**
	 * Obtain the Picking No. from the loaded Result data.
	 * @return pickingNo
	 */
	public String getPickingNo()
	{
		return getColumn(OFF_PICKING_NO, LEN_PICKING_NO);
	}
	
	//#CM721116
	/**
	 * Obtain the customer code from the loaded Result data.
	 * @return		Customer Code
	 */
	public String getCustomerCode()
	{
		return getColumn(OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE);
	}
	
	//#CM721117
	/**
	 * Obtain the customer name from the loaded Result data.
	 * @return		Customer Name
	 */
	public String getCustomerName()
	{
		return getColumn(OFF_CUSTOMER_NAME, LEN_CUSTOMER_NAME);
	}
	
	//#CM721118
	/**
	 * Obtain the Area No. from the loaded Result data.
	 * @return		Area No.
	 */
	public String getAreaNo()
	{
		return getColumn(OFF_AREA_NO, LEN_AREA_NO);
	}
	
	//#CM721119
	/**
	 * Obtain the Zone No. from the loaded Result data.
	 * @return		Zone No.
	 */
	public String getZoneNo()
	{
		return getColumn(OFF_ZONE_NO, LEN_ZONE_NO);
	}

	//#CM721120
	/**
	 * Obtain the Location No. from the loaded Result data.
	 * @return		Location No.
	 */
	public String getLocationNo()
	{
		return getColumn(OFF_JOB_LOCATION, LEN_LOCATION);
	}
	
	//#CM721121
	/**
	 * Obtain the Location Position from the loaded Result data.
	 * @return		Location Position
	 */
	public String getLocationSide()
	{
		return getColumn(OFF_LOCATION_SIDE, LEN_LOCATION_SIDE);
	}
	
	//#CM721122
	/**
	 * Obtain the Aggregation Work No. from the loaded Result data.
	 * @return		Aggregation Work No.
	 */
	public String getCollectJobNo()
	{
		return getColumn(OFF_COLLECT_JOB_NO, LEN_COLLECT_JOB_NO);
	}
	
	//#CM721123
	/**
	 * Obtain the JAN Code from the loaded Result data.
	 * @return		JAN Code
	 */
	public String getJanCode()
	{
		return getColumn(OFF_JAN_CODE, LEN_JAN_CODE);
	}
	
	//#CM721124
	/**
	 * Obtain the bundle ITF from the loaded Result data.
	 * @return		Bundle ITF
	 */
	public String getBundleItf()
	{
		return getColumn(OFF_BUNDLE_ITF, LEN_BUNDLE_ITF);
	}
	
	//#CM721125
	/**
	 * Obtain the Case ITF from the loaded Result data.
	 * @return		Case ITF
	 */
	public String getItf()
	{
		return getColumn(OFF_ITF, LEN_ITF);
	}
	
	//#CM721126
	/**
	 * Obtain the Packed qty per bundle from the loaded Result data.
	 * @return		Packed qty per bundle
	 */
	public int getBundleEnteringQty()
	{
		return getIntColumn(OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY);
	}

	//#CM721127
	/**
	 * Obtain the Packed qty from the loaded Result data.
	 * @return		Packed qty
	 */
	public int getEnteringQty()
	{
		return getIntColumn(OFF_ENTERING_QTY, LEN_ENTERING_QTY);
	}

	//#CM721128
	/**
	 * Obtain the expiry date from the loaded Result data.
	 * @return		Expiry Date
	 */
	public String getUseByDate()
	{
		return getColumn(OFF_USE_BY_DATE, LEN_USE_BY_DATE);
	}
	
	//#CM721129
	/**
	 * Obtain the Instructed picking qty from the loaded Result data.
	 * @return		Instructed picking qty
	 */
	public int getPlanQty()
	{
		return getIntColumn(OFF_PLAN_QTY, LEN_PLAN_QTY);
	}
	
	//#CM721130
	/**
	 * Obtain the Picking Result qty from the loaded Result data.
	 * @return		Picking Result qty
	 */
	public int getResultQty()
	{
		return getIntColumn(OFF_RESULT_QTY, LEN_RESULT_QTY);
	}
	
	//#CM721131
	/**
	 * Obtain the Work Type (Case/Piece division) from the loaded Result data.
	 * @return		Work Type (Case, Piece, or None)
	 */
	public String getWorkForm()
	{
		return getColumn(OFF_WORK_FORM, LEN_WORK_FORM);
	}
	
	//#CM721132
	/**
	 * Obtain the Work Time from the loaded Result data.
	 * @return		Work Time
	 */
	public int getWorkSeconds()
	{
		return getIntColumn(OFF_WORK_SECONDS, LEN_WORK_TIME);
	}

	//#CM721133
	/**
	 * Obtain the "Complete" flag from the loaded Result data.
	 * @return		"Complete" flag
	 */
	public String getCompletionFlag()
	{
		return getColumn(OFF_COMPLETION_FLAG, LEN_COMPLETION_FLAG);
	}

	//#CM721134
	/**
	 * Set the setting unit key in the data buffer.
	 * @param	setUnitKey	the setting unit key
	 */
	public void setSetUnitKey(String setUnitKey)
	{
		setColumn(setUnitKey, OFF_SET_UNIT_KEY, LEN_SET_UNIT_KEY);
	}

	//#CM721135
	/**
	 * Set the Order No. in the data buffer
	 * @param	orderNo		Order No.
	 */
	public void setOrderNo(String orderNo)
	{
		setColumn(orderNo, OFF_ORDER_NO, LEN_ORDER_NO);
	}

	//#CM721136
	/**
	 * Set the Picking No. in the data buffer
	 * @param	pickingNo	Picking No.
	 */
	public void getPickingNo(String pickingNo)
	{
		setColumn(pickingNo, OFF_PICKING_NO, LEN_PICKING_NO);
	}
	
	//#CM721137
	/**
	 * Set the customer code in the data buffer
	 * @param	customerCode	Customer Code
	 */
	public void setCustomerCode(String customerCode)
	{
		setColumn(customerCode, OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE);
	}
	
	//#CM721138
	/**
	 * Set the customer name in the data buffer
	 * @param	customerName	Customer Name
	 */
	public void setCustomerName(String customerName)
	{
		setColumn(customerName, OFF_CUSTOMER_NAME, LEN_CUSTOMER_NAME);
	}

	//#CM721139
	/**
	 * Set the Area No. in the data buffer
	 * @param	areaNo	Area No.
	 */
	public void setAreaNo(String areaNo)
	{
		setColumn(areaNo, OFF_AREA_NO, LEN_AREA_NO);
	}
	
	//#CM721140
	/**
	 * Set the Zone No. in the data buffer
	 * @param	zoneNo	Zone No.
	 */
	public void setZoneNo(String zoneNo)
	{
		setColumn(zoneNo, OFF_ZONE_NO, LEN_ZONE_NO);
	}

	//#CM721141
	/**
	 * Set the location No. in the data buffer
	 * @param	locationNo	Location No.
	 */
	public void setLocationNo(String locationNo)
	{
		setColumn(locationNo, OFF_JOB_LOCATION, LEN_LOCATION);
	}
	
	//#CM721142
	/**
	 * Set the Location Position in the data buffer
	 * @param	locationSide	Location Position
	 */
	public void setLocationSide(String locationSide)
	{
		setColumn(locationSide, OFF_LOCATION_SIDE, LEN_LOCATION_SIDE);
	}
	
	//#CM721143
	/**
	 * Set the Aggregation Work No. in the data buffer
	 * @param	collectJobNo		Aggregation Work No.
	 */
	public void setCollectJobNo(String collectJobNo)
	{
		setColumn(collectJobNo, OFF_COLLECT_JOB_NO, LEN_COLLECT_JOB_NO);
	}
	
	//#CM721144
	/**
	 * Set the JAN code in the data buffer
	 * @param	janCode		JAN Code
	 */
	public void setJanCode(String janCode)
	{
		setColumn(janCode, OFF_JAN_CODE, LEN_JAN_CODE);
	}
	
	//#CM721145
	/**
	 * Set the bundle ITF in the data buffer
	 * @param	bundleItf	Bundle ITF
	 */
	public void setBundleItf(String bundleItf)
	{
		setColumn(bundleItf, OFF_BUNDLE_ITF, LEN_BUNDLE_ITF);
	}
	
	//#CM721146
	/**
	 * Set the Case ITF in the data buffer
	 * @param	caseItf		Case ITF
	 */
	public void setItf(String caseItf)
	{
		setColumn(caseItf, OFF_ITF, LEN_ITF);
	}
	
	//#CM721147
	/**
	 * Set the item name for data buffer.
	 * @param	itemName	Item Name
	 */
	public void setItemName(String itemName)
	{
		setColumn(itemName, OFF_ITEM_NAME, LEN_ITEM_NAME);
	}

	//#CM721148
	/**
	 * Set the Packed qty per bundle in the data buffer
	 * @param	bundleEnteringQty		Packed qty per bundle
	 */
	public void setBundleEnteringQty(int bundleEnteringQty)
	{
		setIntColumn(bundleEnteringQty, OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY);
	}

	//#CM721149
	/**
	 * Set the Packed qty in the data buffer
	 * @param	enteringQty		Packed qty
	 */
	public void setEnteringQty(int enteringQty)
	{
		setIntColumn(enteringQty, OFF_ENTERING_QTY, LEN_ENTERING_QTY);
	}

	//#CM721150
	/**
	 * Set the expiry date for data buffer.
	 * @param	useByDate	Expiry Date
	 */
	public void setUseByDate(String useByDate)
	{
		setColumn(useByDate, OFF_USE_BY_DATE, LEN_USE_BY_DATE);
	}
	
	//#CM721151
	/**
	 * Set the Instructed picking qty in the data buffer
	 * @param	planQty		Instructed picking qty
	 */
	public void setPlanQty(int planQty)
	{
		setIntColumn(planQty, OFF_PLAN_QTY, LEN_PLAN_QTY);
	}
	
	//#CM721152
	/**
	 * Set the Picking Result qty in the data buffer
	 * @param	resultQty	Picking Result qty
	 */
	public void setResultQty(int resultQty)
	{
		setIntColumn(resultQty, OFF_RESULT_QTY, LEN_RESULT_QTY);
	}
	
	//#CM721153
	/**
	 * Set the Work Type (Case/Piece division) in the data buffer
	 * @param	workForm	Work Type (Case, Piece, or None)
	 */
	public void setWorkForm(String workForm)
	{
		setColumn(workForm, OFF_WORK_FORM, LEN_WORK_FORM);
	}
	
	//#CM721154
	/**
	 * Set the Work Time in the data buffer
	 * @param	workSeconds		Work Time
	 */
	public void setWorkSeconds(int workSeconds)
	{
		setIntColumn(workSeconds, OFF_WORK_SECONDS, LEN_WORK_TIME);
	}

	//#CM721155
	/**
	 * Set the "Complete" flag in the data buffer
	 * @param	completionFlag	"Complete" flag
	 */
	public void setCompletionFlag(String completionFlag)
	{
		setColumn(completionFlag, OFF_COMPLETION_FLAG, LEN_COMPLETION_FLAG);
	}

	//#CM721156
	/**
	 * Obtain the Consignor and Planned date from an electronic statement responding to starting the Order Picking, and set it in the entity array.
	 * @param	workinfo	data with status "Processing"
	 * @param	response	Response electronic statement for starting the Order Picking
	 */
	public void setRequestInfo(jp.co.daifuku.wms.base.entity.WorkingInformation[] workinfo, IdMessage response)
	{
	    RFTId5330 id5330 = (RFTId5330) response;

	    for (int i = 0; i < workinfo.length; i ++)
	    {
	        workinfo[i].setConsignorCode(id5330.getConsignorCode());
	        workinfo[i].setPlanDate(id5330.getPlanDate());
	    }
	}
}
