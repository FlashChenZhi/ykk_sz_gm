// $Id: StationListBusiness.java,v 1.2 2006/10/30 04:59:54 suresh Exp $

//#CM53216
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.stationlist;
import java.sql.Connection;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolDatabaseFinder;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolTipHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.ToolSessionRet;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.ToolSessionStationRet;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.util.CollectionUtils;

//#CM53217
/**
 * Station list screen Class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:59:54 $
 * @author  $Author: suresh $
 */
public class StationListBusiness extends StationList implements WMSToolConstants
{
	//#CM53218
	// Class fields --------------------------------------------------
	//#CM53219
	/** 
	 * The key used to hand over Area ID.
	 */
	public static final String AREAID_KEY = "AREAID_KEY";

	//#CM53220
	// Class variables -----------------------------------------------
	//#CM53221
	/** 
	 * The key used to hand over Screen work flag.
	 */
	public static final String MAINSTATION_KEY = "MAINSTATION_KEY";
	//#CM53222
	/** 
	 * The key used to hand over Warehouse No.
	 */
	public static final String WHSTATIONNO_KEY = "WHSTATIONNO_KEY";
	//#CM53223
	/** 
	 * The key used to hand over Parent Station No.
	 */
	public static final String PARENTSTNO_KEY = "PARENTSTNO_KEY";
	//#CM53224
	/** 
	 * The key used to hand over Station No.
	 */
	public static final String STATIONNO_KEY = "STATIONNO_KEY";
	//#CM53225
	/** 
	 * The key used to hand over Station Name.
	 */
	public static final String STATIONNAME_KEY = "STATIONNAME_KEY";
	//#CM53226
	/** 
	 * The key used to hand over Workshop Type. 
	 */
	public static final String WORKPLACETYPE_KEY = "WORKPLACETYPE_KEY";
	//#CM53227
	/** 
	 * The key used to hand over Aisle Station No.
	 */
	public static final String AISLESTATIONNO_KEY = "AISLESTATIONNO_KEY";
	//#CM53228
	/** 
	 * The key used to hand over AGC No.
	 */
	public static final String AGCNO_KEY = "AGCNO_KEY";

	//#CM53229
	/**<en> Class name (dedicated for storage)</en>*/

	public static String CLASS_STORAGE  = "jp.co.daifuku.wms.asrs.location.StorageStationOperator" ;
	
	//#CM53230
	/**<en> Class name (dedicated for retrieva)</en>*/

	public static String CLASS_RETRIEVAL  = "jp.co.daifuku.wms.asrs.location.RetrievalStationOperator" ;
	
	//#CM53231
	/**<en> Class name (P&D stand, powered cart)</en>*/

	public static String CLASS_INOUTSTATION  = "jp.co.daifuku.wms.asrs.location.InOutStationOperator" ;
	
	//#CM53232
	/**<en> Class name  (U-shaped storage) </en>*/

	public static String CLASS_FREESTORAGE  = "jp.co.daifuku.wms.asrs.location.FreeStorageStationOperator" ;
	
	//#CM53233
	/**<en> Class name  (U-shaped retrieval)</en>*/

	public static String CLASS_FREERETRIEVAL  = "jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator" ;

	//#CM53234
	// Class method --------------------------------------------------

	//#CM53235
	// Constructors --------------------------------------------------

	//#CM53236
	// Public methods ------------------------------------------------

	//#CM53237
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM53238
		//Set the screen name. 
		lbl_ListName.setText(DisplayText.getText("TTLE-W0002"));
		
		//#CM53239
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
			//#CM53240
			//Do Deletion from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM53241
		//Acquisition of Connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		ToolFindUtil findutil = new ToolFindUtil(conn);
		//#CM53242
		//Acquisition of parameter set on call previous screen
		//#CM53243
		//<en> search key (whether/not with on-line indication)</en>
		String mainstation = this.request.getParameter( MAINSTATION_KEY );
		//#CM53244
		//<en> search key (warehouse no.)</en>
		String whstation = this.request.getParameter( WHSTATIONNO_KEY );
		//#CM53245
		//<en> search key (parent station no.)</en>
		String parentstnumber = this.request.getParameter( PARENTSTNO_KEY );
		//#CM53246
		//<en> search key (station no.)</en>
		String station = this.request.getParameter( STATIONNO_KEY );
		//#CM53247
		//<en> search key (workshop type)</en>
		String worktype = this.request.getParameter( WORKPLACETYPE_KEY );

		String whstno = null;
		if (whstation != null && !whstation.equals(""))
		{
			whstno = findutil.getWarehouseStationNumber(Integer.parseInt(whstation));
		}
		//#CM53248
		//SessionItem instance generation
		ToolSessionStationRet listbox = new ToolSessionStationRet( conn, station, whstno, parentstnumber, worktype, Integer.parseInt(mainstation), null ) ;
		//#CM53249
		//Listbox is maintained in Session. 
		this.getSession().setAttribute("LISTBOX", listbox);
		//#CM53250
		//The first page is displayed. 
		setList(listbox, conn, "first");
	}

	//#CM53251
	// Package methods -----------------------------------------------

	//#CM53252
	// Protected methods ---------------------------------------------

	//#CM53253
	// Private methods -----------------------------------------------
	//#CM53254
	// Private methods -----------------------------------------------
	//#CM53255
	/** <en>
	 * Set data to listcell.
	 * @param listbox  ToolSessionStationRet
	 * @param conn  Connection
	 * @param actionName String
	 * @throws Exception 	 
	 </en> */
	protected void setList(ToolSessionStationRet listbox, Connection conn, String actionName) throws Exception
	{
		//#CM53256
		//The page information is set. 
		listbox.setActionName(actionName);
		
		//#CM53257
		//<en> Check the number of search result data.</en>
		String errorMsg = listbox.checkLength();
		//#CM53258
		//<en> No errors.</en>
		if (errorMsg == null)
		{
			Station[] station = listbox.getStation();
			int len = 0;
			if (station != null) len = station.length;

			if (len > 0)
			{
				//#CM53259
				//Value set in Pager
				pgr_U.setMax(listbox.getLength());		//最大件数
				pgr_U.setPage(listbox.getCondition());	//1Page表示件数
				pgr_U.setIndex(listbox.getCurrent()+1);	//開始位置
				pgr_D.setMax(listbox.getLength());		//最大件数
				pgr_D.setPage(listbox.getCondition());	//1Page表示件数
				pgr_D.setIndex(listbox.getCurrent()+1);	//開始位置
	
				//#CM53260
				//Delete all the lines
				lst_StationList.clearRow();
				
				String ailestno = "";
				String stationno = "";
				String stationname = "";
				String parentstno = "";
				String agcno = "";
				String clname = "";
//#CM53261
//				String workplacetype = "";
				String workplacetypechar = "";

				//#CM53262
				//Message used for Tip
				String title_StationName       = DisplayText.getText("TLBL-W0091");
				String title_WorkPlaceType      = DisplayText.getText("TLBL-W0077");

				for (int i = 0; i < len; i++)
				{
					if(!station[i].getAisleStationNumber().equals(""))
					{
						ailestno = station[i].getAisleStationNumber();
					}
					else
					{
						ailestno = DisplayText.getText("TLBL-W0069");
					}

					//#CM53263
					//<en> station no.</en>
					stationno = station[i].getNumber();
					//#CM53264
					//<en> station name</en>
					stationname = station[i].getName();
					//#CM53265
					//<en> station type</en>
//#CM53266
//					workplacetype = Integer.toString(station[i].getType());
					//#CM53267
					//<en> workshop no./ main station no.</en>
					if(!station[i].getParentStationNumber().equals(""))
					{
						parentstno = station[i].getParentStationNumber();
					}
					else
					{
						parentstno = DisplayText.getText("TLBL-W0069");
					}
					//#CM53268
					//<en> AGCNo.</en>
					agcno = Integer.toString(station[i].getGroupController().getNumber());
					
					//#CM53269
					//<en> Class name</en>
					clname = station[i].getClassName();
					
					if (clname.equals(CLASS_STORAGE))
					{
						//#CM53270
						//<en>dedicated for the storage</en>
						workplacetypechar = DisplayText.getText("TRDB-W0004");
					}
					else if (clname.equals(CLASS_RETRIEVAL))
					{
						//#CM53271
						//<en>dedicated for the retrieval</en>
						workplacetypechar = DisplayText.getText("TRDB-W0005");
					}
					else if (clname.equals(CLASS_INOUTSTATION))
					{
						//#CM53272
						//<en>P&D stand, self-drive cart</en>
						workplacetypechar = DisplayText.getText("TRDB-W0006");
					}
					else if (clname.equals(CLASS_FREESTORAGE))
					{
						//#CM53273
						//<en>U-shaped (storage side)</en>
						workplacetypechar = DisplayText.getText("TRDB-W0007");
					}
					else if (clname.equals(CLASS_FREERETRIEVAL))
					{
						//#CM53274
						//<en>U-shaped (retrieval side)</en>
						workplacetypechar = DisplayText.getText("TRDB-W0008");
					}
					else
					{
						//#CM53275
						//<en>workshop</en>
//#CM53276
//						workplacetypechar = DisplayText.getText("TRDB-W0026");
						workplacetypechar = station[i].getName();
					}

					//#CM53277
					//Line addition
					//#CM53278
					//The final line is acquired. 
					int count = lst_StationList.getMaxRows();
					lst_StationList.setCurrentRow(count);
					lst_StationList.addRow();
					lst_StationList.setValue(0, CollectionUtils.getConnectedString(stationname,""));
					lst_StationList.setValue(1, Integer.toString(count+listbox.getCurrent()));
					lst_StationList.setValue(2, stationno);
					lst_StationList.setValue(3, workplacetypechar);
					lst_StationList.setValue(4, ailestno);
					lst_StationList.setValue(5, agcno);
					lst_StationList.setValue(6, parentstno);

					//#CM53279
					//Set TOOL TIP.
					ToolTipHelper toolTip = new ToolTipHelper();
					toolTip.add(title_StationName, stationname);
					toolTip.add(title_WorkPlaceType, workplacetypechar);
					
					//#CM53280
					//Set TOOL TIP. 	
					lst_StationList.setToolTip(count, toolTip.getText());
				}
			}
			else
			{
				//#CM53281
				//Value set to Pager
				pgr_U.setMax(0);	//最大件数
				pgr_U.setPage(0);	//1Page表示件数
				pgr_U.setIndex(0);	//開始位置
				pgr_D.setMax(0);	//最大件数
				pgr_D.setPage(0);	//1Page表示件数
				pgr_D.setIndex(0);	//開始位置
	
				//#CM53282
				//Conceal the header. 
				lst_StationList.setVisible(false);
				//#CM53283
				//MSG-9016 = There was no object data. 
				lbl_InMsg.setResourceKey("MSG-9016");
			}
		}
		else
		{
			//#CM53284
			//Value set to Pager
			pgr_U.setMax(0);	//最大件数
			pgr_U.setPage(0);	//1Page表示件数
			pgr_U.setIndex(0);	//開始位置
			pgr_D.setMax(0);	//最大件数
			pgr_D.setPage(0);	//1Page表示件数
			pgr_D.setIndex(0);	//開始位置

			//#CM53285
			//Conceal the header. 
			lst_StationList.setVisible(false);
			//#CM53286
			// No data when the number of retrieval results exceeding the maximum display numbers. 
			lbl_InMsg.setText(MessageResource.getMessage(errorMsg));  
		}
	}

	//#CM53287
	// Event handler methods -----------------------------------------
	//#CM53288
	/** <en>
	 * It is called when a close button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		//#CM53289
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

		//#CM53290
		//It calls and it changes to previous screen. 
		parentRedirect(null);
	}
	//#CM53291
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
			//#CM53292
			//Close the Connection
			if(conn != null) conn.close();
		}
	}

	//#CM53293
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
			//#CM53294
			//Close the Connection
			if(conn != null) conn.close();
		}
	}

	//#CM53295
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
			//#CM53296
			//Close the Connection
			if(conn != null) conn.close();
		}
	}

	//#CM53297
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
			//#CM53298
			//Close the Connection
			if(conn != null) conn.close();
		}
	}

	//#CM53299
	/** <en>
	 * It is called when it clicks on the list.	
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_StationList_Click(ActionEvent e) throws Exception
	{
		//#CM53300
		//Current line is set. 
		lst_StationList.setCurrentRow(lst_StationList.getActiveRow());

		//#CM53301
		//Parameter making that is called and passed to previous screen
		ForwardParameters param = new ForwardParameters();
		param.setParameter(STATIONNO_KEY, lst_StationList.getValue(2));
		param.setParameter(STATIONNAME_KEY, CollectionUtils.getString(0,lst_StationList.getValue(0)));
		param.setParameter(WORKPLACETYPE_KEY, lst_StationList.getValue(3));
		param.setParameter(AISLESTATIONNO_KEY, lst_StationList.getValue(4));
		param.setParameter(AGCNO_KEY, lst_StationList.getValue(5));
		
		//#CM53302
		//Termination
		btn_Close_U_Click(null);
		
		//#CM53303
		//It calls and it changes to previous screen. 
		parentRedirect(param);
	}

	//#CM53304
	/** <en>
	 * It is called when a next button of Pager is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		pgr_U_Next(e);
	}

	//#CM53305
	/** <en>
	 * It is called when a previous button of Pager is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		pgr_U_Prev(e);
	}

	//#CM53306
	/** <en>
	 * It is called when a last button of Pager is pushed.	
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		pgr_U_Last(e);
	}

	//#CM53307
	/** <en>
	 * It is called when a first button of Pager is pushed.	
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		pgr_U_First(e);
	}

	//#CM53308
	/** <en>
	 * It is called when a close button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		btn_Close_U_Click(e);
	}
	//#CM53309
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53310
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53311
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53312
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM53313
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM53314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM53315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM53316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM53318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM53319
//end of class
