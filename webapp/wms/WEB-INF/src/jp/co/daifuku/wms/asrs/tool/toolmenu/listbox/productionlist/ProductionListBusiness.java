// $Id: ProductionListBusiness.java,v 1.2 2006/10/30 04:59:10 suresh Exp $

//#CM53163
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.productionlist;
import java.io.File;
import java.io.FilenameFilter;

import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;

//#CM53164
/**
 * Job No. folder screen Class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:59:10 $
 * @author  $Author: suresh $
 */
public class ProductionListBusiness extends ProductionList
{
	//#CM53165
	// Class fields --------------------------------------------------
	//#CM53166
	/** 
	 * The key used to hand over Job No.
	 */
	public static final String PRODUCTIONNO_KEY = "PRODUCTIONNO_KEY";

	//#CM53167
	// Class variables -----------------------------------------------

	//#CM53168
	// Class method --------------------------------------------------

	//#CM53169
	// Constructors --------------------------------------------------

	//#CM53170
	// Public methods ------------------------------------------------

	//#CM53171
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM53172
		// Set the screen name. 
		lbl_ListName.setText(DisplayText.getText("TTLE-W0005"));

		//#CM53173
		//<en> Data for pull-down list</en>
		String listdata[] = null;
		try
		{
			//#CM53174
			//<en> Get the path of project no. file from the resource.</en>
			String currentFolder = ToolParam.getParam("EAWC_ITEM_ROUTE_PATH");
			//#CM53175
			//<en> Get the file list.</en>
			File file = new File(currentFolder);
			String[] list = null;
			//#CM53176
			//<en> If the specified path is readable, search the sub directory.</en>
			if (  file.canRead()  )
			{				
				list =  file.list(new FilenameFilter()
				{
					public boolean accept(File dir, String name)
					{
						//#CM53177
						//<en> Get the directory only.</en>
						return ( (new File( dir , name ) ).isDirectory() );
					}
				});
			}
			if(list != null)
			{
				//#CM53178
				//<en> Set the sub folder of the specified folder.</en>
				listdata = new String[ list.length];
				//#CM53179
				//<en>  Add the file to the array.</en>
				int cnt;
				for ( cnt = 0 ; cnt < list.length ; cnt++ )
				{
					listdata[ cnt ] = list[ cnt ];
				}
			}
			//#CM53180
			//The first page is displayed. 
			setList(listdata);
		}
		catch(Exception ex)
		{
			lbl_InMsg.setText(MessageResources.getText(ExceptionHandler.getDisplayMessage(ex, this)));
		}

	}

	public void page_LoginCheck(ActionEvent e) throws Exception
	{
		//#CM53181
		//This Method is necessary before log in. 
	}

	//#CM53182
	// Package methods -----------------------------------------------

	//#CM53183
	// Protected methods ---------------------------------------------

	//#CM53184
	// Private methods -----------------------------------------------
	//#CM53185
	/** <en>
	 * Set data to listcell.
	 * @param listbox  ToolSessionZoneRet
	 * @param actionName String
	 * @throws Exception 	 
	 </en> */
	protected void setList(String listdata[]) throws Exception
	{
		int len = 0;
		if (listdata != null) len = listdata.length;

		if (len > 0)
		{
			//#CM53186
			//Delete all the lines
			lst_ProductionNumberList.clearRow();
			
			String dirname = "";

			for (int i = 0; i < listdata.length; i++) 
			{
				//#CM53187
				//<en>Set the retrieved data in remote data.</en>
				dirname = listdata[i];
				//#CM53188
				//Line addition
				//#CM53189
				//The final line is acquired. 
				int count = lst_ProductionNumberList.getMaxRows();
				lst_ProductionNumberList.setCurrentRow(count);
				lst_ProductionNumberList.addRow();
				lst_ProductionNumberList.setValue(1, Integer.toString(count));
				lst_ProductionNumberList.setValue(2, dirname);
			}
		}
		else
		{
			//#CM53190
			//Conceal the header. 
			lst_ProductionNumberList.setVisible(false);
			//#CM53191
			//MSG-9016 = There was no object data. 
			lbl_InMsg.setResourceKey("MSG-9016");
		}
	}

	//#CM53192
	// Event handler methods -----------------------------------------
	//#CM53193
	/** <en>
	 * It is called when a close button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		//#CM53194
		//It calls and it changes to previous screen. 
		parentRedirect(null);
	}

	//#CM53195
	/** <en>
	 * It is called when it clicks on the list.	
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_ProductionNumberList_Click(ActionEvent e) throws Exception
	{
		//#CM53196
		//Current line is set. 
		lst_ProductionNumberList.setCurrentRow(lst_ProductionNumberList.getActiveRow());

		//#CM53197
		//Parameter making that is called and passed to previous screen
		ForwardParameters param = new ForwardParameters();
		param.setParameter(PRODUCTIONNO_KEY, lst_ProductionNumberList.getValue(2));
		
		//#CM53198
		//Termination
		btn_Close_U_Click(null);
		
		//#CM53199
		//It calls and it changes to previous screen. 
		parentRedirect(param);
	}

	//#CM53200
	/** <en>
	 * It is called when a close button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		btn_Close_U_Click(e);
	}

	//#CM53201
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53202
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53203
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53204
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ProductionNumberList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM53205
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ProductionNumberList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM53206
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ProductionNumberList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM53207
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ProductionNumberList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM53208
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ProductionNumberList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53209
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ProductionNumberList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM53210
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM53211
//end of class
