// $Id: ListFolderSelectBusiness.java,v 1.2 2006/11/13 08:19:00 suresh Exp $

//#CM692329
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.listfolderselect;
import java.io.File;
import java.io.FilenameFilter;

import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.display.web.PulldownHelper;
import jp.co.daifuku.wms.base.system.display.web.loaddataenvironment.LoadDataEnvironmentBusiness;
import jp.co.daifuku.wms.base.system.display.web.reportdataenvironment.ReportDataEnvironmentBusiness;
import jp.co.daifuku.wms.base.system.display.web.reportdatastock.ReportDataStockBusiness;

//#CM692330
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this class to provide a Listbox for searching the data storage folder.<BR>
 * Search for the data using the data storage folder entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using the data storage folder entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2."Select" button (<CODE>lst_TicketNoSearch_Click</CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the folder name displayed in the pulldown to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:19:00 $
 * @author  $Author: suresh $
 */
public class ListFolderSelectBusiness extends ListFolderSelect implements WMSConstants
{
	//#CM692331
	// Class fields --------------------------------------------------
	//#CM692332
	/** 
	 * Use this key to pass Data Storage Folder .
	 */
	public static final String FOLDER_KEY = "FOLDER_KEY";

	//#CM692333
	/** 
	 * Use this key to pass "Refer" button flag.
	 */
	public static final String BTNFLUG_KEY = "BTNFLUG_KEY";

	//#CM692334
	/** 
	 * Data type: Receiving 
	 */
	private static final int DATATYPE_INSTOCKRECEIVE = 0;

	//#CM692335
	/** 
	 * Data type: Storage 
	 */
	private static final int DATATYPE_STRAGESUPPOR = 1;

	//#CM692336
	/** 
	 * Data type: Picking 
	 */
	private static final int DATATYPE_RETRIEVALSUPPORT = 2;

	//#CM692337
	/** 
	 * Data type: Shipping 
	 */
	private static final int DATATYPE_SHIPPINGINSPECTION = 3;

	//#CM692338
	/**
	 * Data type: Sorting
	 */
	private static final int DATATYPE_PICKINGSUPPORT = 4;

	//#CM692339
	/** 
	 * Data type: Stock relocation
	 */
	private static final int DATATYPE_MOVINGSUPPOR = 5;

	//#CM692340
	/** 
	 * Data type: Inventory Check
	 */
	private static final int DATATYPE_STOCKTAKINGSUPPOR = 6;

	//#CM692341
	/** 
	 * Data type: Inventory
	 */
	private static final int DATATYPE_STOCK = 7;

	//#CM692342
	/** 
	 * Key for setting data environment
	 */
	private static final String[] TYPE_KEY =
		{
			"INSTOCK_RECEIVE",
			"STRAGE_SUPPORT",
			"RETRIEVAL_SUPPORT",
			"SHIPPING_INSPECTION",
			"PICKING_SUPPORT",
			"MOVING_SUPPORT",
			"STOCKTAKING_SUPPORT" ,
			"STOCK_SUPPORT"};

	//#CM692343
	/** 
	 * Section name of Load Data folder 
	 */
	private static final String DATALOAD_FOLDER = "DATALOAD_FOLDER";

	//#CM692344
	/** 
	 * Section name of Report Data folder
	 */
	private static final String REPORTDATA_FOLDER = "REPORTDATA_FOLDER";

	//#CM692345
	// Class variables -----------------------------------------------

	//#CM692346
	// Class method --------------------------------------------------

	//#CM692347
	// Constructors --------------------------------------------------

	//#CM692348
	// Public methods ------------------------------------------------

	//#CM692349
	/**
	 * Initialize the screen.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM692350
		//Obtain the parameter.
		//#CM692351
		// Data Storage Folder
		String folder = request.getParameter(FOLDER_KEY);
		//#CM692352
		// "Refer" button flag
		//#CM692353
		// To determine of which "Refer" button is clicked,
		String flug = request.getParameter(BTNFLUG_KEY);

		viewState.setString(BTNFLUG_KEY, flug);

		//#CM692354
		// obtain it from a Environment setting file if the Folder name is empty.
		if (StringUtil.isBlank(folder))
		{
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);
			if (flug.equals(LoadDataEnvironmentBusiness.LOAD_INSTOCK))
			{
				//#CM692355
				// Loaded Data (Shipping)
				folder = IO.get(DATALOAD_FOLDER, TYPE_KEY[DATATYPE_INSTOCKRECEIVE]);
			}
			else if (flug.equals(LoadDataEnvironmentBusiness.LOAD_STORAGE))
			{
				//#CM692356
				// Loaded Data (Storage)
				folder = IO.get(DATALOAD_FOLDER, TYPE_KEY[DATATYPE_STRAGESUPPOR]);
			}
			else if (flug.equals(LoadDataEnvironmentBusiness.LOAD_RETRIEVAL))
			{
				//#CM692357
				// Loaded Data (Picking)
				folder = IO.get(DATALOAD_FOLDER, TYPE_KEY[DATATYPE_RETRIEVALSUPPORT]);
			}
			else if (flug.equals(LoadDataEnvironmentBusiness.LOAD_PICKING))
			{
				//#CM692358
				// Loaded Data (Sorting)
				folder = IO.get(DATALOAD_FOLDER, TYPE_KEY[DATATYPE_PICKINGSUPPORT]);
			}
			else if (flug.equals(LoadDataEnvironmentBusiness.LOAD_SHIPPING))
			{
				//#CM692359
				// Loaded Data (Picking)
				folder = IO.get(DATALOAD_FOLDER, TYPE_KEY[DATATYPE_SHIPPINGINSPECTION]);
			}
			else if (flug.equals(ReportDataEnvironmentBusiness.REPORT_INSTOCK))
			{
				//#CM692360
				// Report Data (Shipping)
				folder = IO.get(REPORTDATA_FOLDER, TYPE_KEY[DATATYPE_INSTOCKRECEIVE]);
			}
			else if (flug.equals(ReportDataEnvironmentBusiness.REPORT_STORAGE))
			{
				//#CM692361
				// Report Data (Storage)
				folder = IO.get(REPORTDATA_FOLDER, TYPE_KEY[DATATYPE_STRAGESUPPOR]);
			}
			else if (flug.equals(ReportDataEnvironmentBusiness.REPORT_RETRIEVAL))
			{
				//#CM692362
				// Report Data (Picking)
				folder = IO.get(REPORTDATA_FOLDER, TYPE_KEY[DATATYPE_RETRIEVALSUPPORT]);
			}
			else if (flug.equals(ReportDataEnvironmentBusiness.REPORT_PICKING))
			{
				//#CM692363
				// Report Data (Sorting)
				folder = IO.get(REPORTDATA_FOLDER, TYPE_KEY[DATATYPE_PICKINGSUPPORT]);
			}
			else if (flug.equals(ReportDataEnvironmentBusiness.REPORT_SHIPPING))
			{
				//#CM692364
				// Report Data (Picking)
				folder = IO.get(REPORTDATA_FOLDER, TYPE_KEY[DATATYPE_SHIPPINGINSPECTION]);
			}
			else if (flug.equals(ReportDataEnvironmentBusiness.REPORT_MOVE))
			{
				//#CM692365
				// Report Data (Stock Relocation)
				folder = IO.get(REPORTDATA_FOLDER, TYPE_KEY[DATATYPE_MOVINGSUPPOR]);
			}
			else if (flug.equals(ReportDataEnvironmentBusiness.REPORT_INVENTORY))
			{
				//#CM692366
				// Report Data (Picking)
				folder = IO.get(REPORTDATA_FOLDER, TYPE_KEY[DATATYPE_STOCKTAKINGSUPPOR]);
			}
			else if (flug.equals(ReportDataStockBusiness.REPORT_STOCK))
			{
				//#CM692367
				// Report Data (Inventory)
				folder = IO.get(REPORTDATA_FOLDER, TYPE_KEY[DATATYPE_STOCK]);
			}
		}

		//#CM692368
		// Read the path at starting.
		File file = (new File(folder));
		String[] FolderList = getFolderList(file.getAbsolutePath());

		PulldownHelper.setPullDown(pul_Folder, FolderList);
	}

	//#CM692369
	// Package methods -----------------------------------------------

	//#CM692370
	// Protected methods ---------------------------------------------

	//#CM692371
	/**
	 * Obtain the Folder list.
	 * @param  currentFolder  Path of the folder to be obtained.
	 * @return String[]       Number of obtained folders.
	 */
	protected String[] getFolderList(String currentFolder)
	{

		//#CM692372
		// Obtain the file list.
		File file = new File(currentFolder);

		String[] list;

		//#CM692373
		// Search through the sub-directory if the designated path can be read.
		if (file.canRead())
		{

			list = file.list(new FilenameFilter()
			{
				public boolean accept(File dir, String name)
				{
					//#CM692374
					// Obtain the directory only.
					return ((new File(dir, name)).isDirectory());
				}
			});

			//#CM692375
			// Regard the path as invalid if the obtained value is Null.
			if (list == null)
			{
				list = new String[0];
				message.setMsgResourceKey("6003019");
			}

			//#CM692376
			// Set an error message if the path is invalid.
		}
		else
		{
			list = new String[0];
			message.setMsgResourceKey("6003019");
		}

		//#CM692377
		// Obtain the Drive list.
		File[] roots = File.listRoots();

		//#CM692378
		// Set the designated folder for the first.
		String[] res = new String[list.length + roots.length + 1];
		res[0] = file.getAbsolutePath();

		//#CM692379
		// Disable to click on "Up" button while displaying the Drive list in the pulldown.
		for(int i = 0; i < roots.length; i++)
		{
			if(res[0].equals(roots[i].getPath()))
			{
				//#CM692380
				// Disable to click on Up button.
				btn_Up.setEnabled(false);
				break;
			}
			else
			{
				//#CM692381
				// Enable to click on Up button.
				btn_Up.setEnabled(true);
			}
		}

		//#CM692382
		// Exclude if suffixed with a period.
		if (res[0].length() > 0 && (res[0].substring(res[0].length() - 1).equals(".")))
		{
			res[0] = res[0].substring(0, res[0].length() - 1);
		}

		//#CM692383
		// If not suffixed with a path separator, suffix a path separator.
		if (res[0].length() > 0 && (!res[0].substring(res[0].length() - 1).equals(File.separator)))
		{
			res[0] = res[0] + File.separator;
		}

		//#CM692384
		//  Add a file to an array.
		int cnt;
		for (cnt = 0; cnt < list.length; cnt++)
		{
			res[cnt + 1] = res[0] + list[cnt];
		}

		//#CM692385
		// Add a rout list.
		for (int i = 0; i < roots.length; i++)
		{
			res[++cnt] = roots[i].getAbsolutePath();
		}

		return res;

	}

	//#CM692386
	/**
	 * Obtain the array for setting the pulldown from the array of characters in the Folder list.
	 * 
	 * @param  currentFolder  Path of the folder to be obtained.
	 * @return String[]       Number of obtained folders.
	 */
	protected String[] getFolderPullDown(String[] folders)
	{

		String[] files = new String[folders.length];
		for (int cnt = 0; cnt < folders.length; cnt++)
		{
			files[cnt] = folders[cnt] + "," + folders[cnt] + ",0,0";
		}

		return files;

	}

	//#CM692387
	// Private methods -----------------------------------------------

	//#CM692388
	// Event handler methods -----------------------------------------
	//#CM692389
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Folder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692390
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Folder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692391
	/** 
	 * Execute the process defined for the case where the contents of pulldown box is changed.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pul_Folder_Change(ActionEvent e) throws Exception
	{
		//#CM692392
		// Read the sub-directory when the path is changed.
		File fil = (new File(pul_Folder.getSelectedValue()));
		String[] FolderList = getFolderList(fil.getAbsolutePath());

		//#CM692393
		// Delete the pulldown.
		pul_Folder.clearItem();

		PulldownHelper.setPullDown(pul_Folder, getFolderPullDown(FolderList));
	}

	//#CM692394
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Up_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692395
	/** 
	 * Execute the process defined for clicking on "Up" button. <BR>
	 *  <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Up_Click(ActionEvent e) throws Exception
	{
		//#CM692396
		// Shift to the parent screen.
		File fil = (new File(pul_Folder.getSelectedValue())).getParentFile();
		if (fil != null)
		{		
			String[] FolderList = getFolderList(fil.getAbsolutePath());
	
			//#CM692397
			// Delete the pulldown.
			pul_Folder.clearItem();
	
			PulldownHelper.setPullDown(pul_Folder, getFolderPullDown(FolderList));
		}
	}

	//#CM692398
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Select_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692399
	/** 
	 * Execute the process defined for clicking on "Select" button. <BR>
	 *  <BR>
	 *  Set the file path and shift to the parent screen.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Select_Click(ActionEvent e) throws Exception
	{
		//#CM692400
		// Set the Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();

		//#CM692401
		// Set the parameter.
		//#CM692402
		// Folder
		param.setParameter(FOLDER_KEY, pul_Folder.getSelectedValue());
		//#CM692403
		// "Refer" button flag
		param.setParameter(BTNFLUG_KEY, viewState.getString(BTNFLUG_KEY));

		//#CM692404
		// Shift to the parent screen.
		parentRedirect(param);
	}

	//#CM692405
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692406
	/** 
	 * Clicking on "Close" button executes its process. <BR>
	 *  <BR>
	 * Close the listbox and shift to the parent screen. <BR>
	 *  <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Close_Click(ActionEvent e) throws Exception
	{
		//#CM692407
		// Return to the parent screen.
		parentRedirect(null);
	}

}
//#CM692408
//end of class
