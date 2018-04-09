// $Id: WorkPlaceListBusiness.java,v 1.2 2006/10/30 05:00:40 suresh Exp $

//#CM53476
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.workplacelist;
import java.sql.Connection;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolDatabaseFinder;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolTipHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.ToolSessionRet;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.ToolSessionStationRet;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.util.CollectionUtils;

//#CM53477
/**
 * Workshop list screen Class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:00:40 $
 * @author  $Author: suresh $
 */
public class WorkPlaceListBusiness extends WorkPlaceList implements WMSToolConstants
{
	//#CM53478
	// Class fields --------------------------------------------------
	//#CM53479
	/** 
	 * The key used to hand over Storage flag. 
	 */
	public static final String WHSTATIONNO_KEY = "WHSTATIONNO_KEY";
	//#CM53480
	/** 
	 * The key used to hand over Workshop No. 
	 */
	public static final String STATIONNO_KEY = "STATIONNO_KEY";
	//#CM53481
	/** 
	 * The key used to hand over Workshop Name. 
	 */
	public static final String STATIONNAME_KEY = "STATIONNAME_KEY";
	//#CM53482
	/** 
	 * The key used to hand over Workshop Type. 
	 */
	public static final String WORKPLACETYPE_KEY = "WORKPLACETYPE_KEY";

	//#CM53483
	// Class variables -----------------------------------------------

	//#CM53484
	// Class method --------------------------------------------------

	//#CM53485
	// Constructors --------------------------------------------------

	//#CM53486
	// Public methods ------------------------------------------------

	//#CM53487
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM53488
		//Set the screen name. 
		lbl_ListName.setText(DisplayText.getText("TTLE-W0001"));
		
		//#CM53489
		//Close Connection of the object left in Session. 
		ToolSessionRet sRet = (ToolSessionRet)this.getSession().getAttribute("LISTBOX");
		if(sRet != null)
		{
			ToolDatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			sRet.closeConnection();
			//#CM53490
			//Do Deletion from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM53491
		//Acquisition of Connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		ToolFindUtil findutil = new ToolFindUtil(conn);
		//#CM53492
		//Acquisition of parameter set on call previous screen
		//#CM53493
		//<en> search key (warehouse no.)</en>
		String whstation = this.request.getParameter( WHSTATIONNO_KEY );
		//#CM53494
		//<en> search key (workshop no.)</en>
		String station = this.request.getParameter( STATIONNO_KEY );
		String whstno = null;
		if (whstation != null && !whstation.equals(""))
		{
			whstno = findutil.getWarehouseStationNumber(Integer.parseInt(whstation));
		}
		//#CM53495
		//SessionItem instance generation
		ToolSessionStationRet listbox = new ToolSessionStationRet( conn, station, whstno , null ) ;
		//#CM53496
		//Listbox is maintained in Session. 
		this.getSession().setAttribute("LISTBOX", listbox);
		//#CM53497
		//The first page is displayed. 
		setList(listbox, conn, "first");
	}

	//#CM53498
	// Package methods -----------------------------------------------

	//#CM53499
	// Protected methods ---------------------------------------------

	//#CM53500
	// Private methods -----------------------------------------------
	//#CM53501
	/** <en>
	 * Set data to listcell.
	 * @param listbox  ToolSessionStationRet
	 * @param conn  Connection
	 * @param actionName String
	 * @throws Exception 	 
	 </en> */
	protected void setList(ToolSessionStationRet listbox, Connection conn, String actionName) throws Exception
	{
		//#CM53502
		//The page information is set. 
		listbox.setActionName(actionName);
		
		//#CM53503
		//<en> Check the number of search result data.</en>
		String errorMsg = listbox.checkLength();
		//#CM53504
		//<en> No errors.</en>
		if (errorMsg == null)
		{
			Station[] station = listbox.getStation();
			int len = 0;
			if (station != null) len = station.length;

			if (len > 0)
			{
				//#CM53505
				//Value set in Pager
				pgr_U.setMax(listbox.getLength());		//最大件数
				pgr_U.setPage(listbox.getCondition());	//1Page表示件数
				pgr_U.setIndex(listbox.getCurrent()+1);	//開始位置
				pgr_D.setMax(listbox.getLength());		//最大件数
				pgr_D.setPage(listbox.getCondition());	//1Page表示件数
				pgr_D.setIndex(listbox.getCurrent()+1);	//開始位置
	
				//#CM53506
				//Delete all the lines
				lst_WorkList.clearRow();
				
				String whstationno = "";
				String stationno = "";
				String stationname = "";
				int wkpltype;
				String workplacetypechar = "";

				//#CM53507
				//Message used for Tip
				String title_StationName       = DisplayText.getText("TLBL-W0037");
				String title_WorkPlaceType      = DisplayText.getText("TLBL-W0088");

				for (int i = 0; i < len; i++)
				{
					//#CM53508
					//<en> Warehouse name</en>
					ToolWarehouseHandler   whh = new ToolWarehouseHandler(conn) ;
					ToolWarehouseSearchKey wk = new ToolWarehouseSearchKey() ;
					wk.setWarehouseStationNumber(station[i].getWarehouseStationNumber()) ;
					Warehouse[] wh = (Warehouse[])whh.find(wk) ;
					if ( wh.length != 0 )
					{
						whstationno = wh[0].getWarehouseNumber()+":"+wh[0].getName() ;
					}

					//#CM53509
					//<en> Workshop no.</en>
					stationno = station[i].getNumber();
					//#CM53510
					//<en> Woekshop name</en>
					stationname = station[i].getName();
					//#CM53511
					//<en> Workshop type</en>
					wkpltype = station[i].getWorkPlaceType();
					workplacetypechar = DisplayText.getText("TSTATION","WORKPLACETYPE", Integer.toString(wkpltype));

					//#CM53512
					//Line addition
					//#CM53513
					//The final line is acquired. 
					int count = lst_WorkList.getMaxRows();
					lst_WorkList.setCurrentRow(count);
					lst_WorkList.addRow();
					lst_WorkList.setValue(0, CollectionUtils.getConnectedString(whstationno,Integer.toString(wkpltype)));
					lst_WorkList.setValue(1, Integer.toString(count+listbox.getCurrent()));
					lst_WorkList.setValue(2, stationno);
					lst_WorkList.setValue(3, stationname);
					lst_WorkList.setValue(4, workplacetypechar);

					//#CM53514
					//Set TOOL TIP.
					ToolTipHelper toolTip = new ToolTipHelper();
					toolTip.add(title_StationName, stationname);
					toolTip.add(title_WorkPlaceType, workplacetypechar);
					
					//#CM53515
					//Set TOOL TIP. 	
					lst_WorkList.setToolTip(count, toolTip.getText());
				}
			}
			else
			{
				//#CM53516
				//Value set to Pager
				pgr_U.setMax(0);	//最大件数
				pgr_U.setPage(0);	//1Page表示件数
				pgr_U.setIndex(0);	//開始位置
				pgr_D.setMax(0);	//最大件数
				pgr_D.setPage(0);	//1Page表示件数
				pgr_D.setIndex(0);	//開始位置
	
				//#CM53517
				//Conceal the header. 
				lst_WorkList.setVisible(false);
				//#CM53518
				//MSG-9016 = There was no object data. 
				lbl_InMsg.setResourceKey("MSG-9016");
			}
		}
		else
		{
			//#CM53519
			//Value set to Pager
			pgr_U.setMax(0);	//最大件数
			pgr_U.setPage(0);	//1Page表示件数
			pgr_U.setIndex(0);	//開始位置
			pgr_D.setMax(0);	//最大件数
			pgr_D.setPage(0);	//1Page表示件数
			pgr_D.setIndex(0);	//開始位置

			//#CM53520
			//Conceal the header. 
			lst_WorkList.setVisible(false);
			//#CM53521
			// No data when the number of retrieval results exceeding the maximum display numbers. 
			lbl_InMsg.setText(MessageResource.getMessage(errorMsg));  
		}
	}


	//#CM53522
	// Event handler methods -----------------------------------------
	//#CM53523
	/** <en>
	 * It is called when a close button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		//#CM53524
		//Termination
		ToolSessionRet sessionret = (ToolSessionRet)this.getSession().getAttribute("LISTBOX");
		if ( sessionret != null )
		{
			ToolDatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			sessionret.closeConnection();
		}
		this.getSession().removeAttribute("LISTBOX");

		//#CM53525
		//It calls and it changes to previous screen. 
		parentRedirect(null);
	}
	//#CM53526
	/** <en>
	 * It is called when a next button of Pager is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			ToolSessionStationRet listbox = (ToolSessionStationRet)this.getSession().getAttribute("LISTBOX");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			setList(listbox, conn, "next");
		}
		finally
		{
			//#CM53527
			//Close the Connection
			if(conn != null) conn.close();
		}
	}

	//#CM53528
	/** <en>
	 * It is called when a previous button of Pager is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			ToolSessionStationRet listbox = (ToolSessionStationRet)this.getSession().getAttribute("LISTBOX");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			setList(listbox, conn, "previous");
		}
		finally
		{
			//#CM53529
			//Close the Connection
			if(conn != null) conn.close();
		}
	}

	//#CM53530
	/** <en>
	 * It is called when a last button of Pager is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			ToolSessionStationRet listbox = (ToolSessionStationRet)this.getSession().getAttribute("LISTBOX");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			setList(listbox, conn, "last");
		}
		finally
		{
			//#CM53531
			//Close the Connection
			if(conn != null) conn.close();
		}
	}

	//#CM53532
	/** <en>
	 * It is called when a first button of Pager is pushed.	
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			ToolSessionStationRet listbox = (ToolSessionStationRet)this.getSession().getAttribute("LISTBOX");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			setList(listbox, conn, "first");
		}
		finally
		{
			//#CM53533
			//Close the Connection
			if(conn != null) conn.close();
		}
	}

	//#CM53534
	/** <en>
	 * It is called when it clicks on the list.	
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_WorkList_Click(ActionEvent e) throws Exception
	{
		//#CM53535
		//Current line is set. 
		lst_WorkList.setCurrentRow(lst_WorkList.getActiveRow());

		//#CM53536
		//Parameter making that is called and passed to previous screen
		ForwardParameters param = new ForwardParameters();
		param.setParameter(WHSTATIONNO_KEY, CollectionUtils.getString(0,lst_WorkList.getValue(0)));
		param.setParameter(STATIONNO_KEY, lst_WorkList.getValue(2));
		param.setParameter(STATIONNAME_KEY, lst_WorkList.getValue(3));
		param.setParameter(WORKPLACETYPE_KEY, CollectionUtils.getString(1,lst_WorkList.getValue(0)));
		
		//#CM53537
		//Termination
		btn_Close_U_Click(null);
		
		//#CM53538
		//It calls and it changes to previous screen. 
		parentRedirect(param);
	}

	//#CM53539
	/** <en>
	 * It is called when a next button of Pager is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		pgr_U_Next(e);
	}

	//#CM53540
	/** <en>
	 * It is called when a previous button of Pager is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		pgr_U_Prev(e);
	}

	//#CM53541
	/** <en>
	 * It is called when a last button of Pager is pushed.	
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		pgr_U_Last(e);
	}

	//#CM53542
	/** <en>
	 * It is called when a first button of Pager is pushed.	
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		pgr_U_First(e);
	}

	//#CM53543
	/** <en>
	 * It is called when a close button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		btn_Close_U_Click(e);
	}
	//#CM53544
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53545
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53546
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53547
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM53548
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM53549
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM53550
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM53551
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53552
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM53553
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM53554
//end of class
