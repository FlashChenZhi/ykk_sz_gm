// $Id: skeltenBusiness.java,v 1.2 2007/03/07 07:45:23 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Inquiry;

import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistorySummaryHead;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistorySummaryStationLowEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistorySummaryStationMidEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistorySummaryStationUpEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.IOHistorySummaryStationLowListProxy;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.IOHistorySummaryStationMidListProxy;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.IOHistorySummaryStationUpListProxy;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ths screen business logic has to be implemented in this class.<BR>
 * This class is generated by ScreenGenerator.
 * <p/>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 *
 * @author $Author: suresh $
 * @version $Revision: 1.2 $, $Date: 2007/03/07 07:45:23 $
 */
public class IOHistorySummaryStationBusiness extends IOHistorySummaryStation implements WMSConstants {
    // Class fields --------------------------------------------------
    private final IOHistorySummaryStationUpListProxy upListProxy = new IOHistorySummaryStationUpListProxy(
            lst_IOHistorySummaryStationUp);
    private final IOHistorySummaryStationMidListProxy midListProxy = new IOHistorySummaryStationMidListProxy(
            lst_IOHistorySummaryStationMid);
    private final IOHistorySummaryStationLowListProxy lowListProxy = new IOHistorySummaryStationLowListProxy(
            lst_IOHistorySummaryStationLow);
    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * Initializes the screen.
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e) throws Exception {
    }

    /**
     * Refered before calling each control event.
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Initialize(ActionEvent e) throws Exception {
        String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
        if (menuparam != null) {
            // #CM37828
            // fetch parameter
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);
            // #CM37829
            // save to viewstate
            this.getViewState().setString(M_TITLE_KEY, title);
            this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
            this.getViewState().setString(M_MENUID_KEY, menuID);
            // #CM37830
            // set screen name
            lbl_SettingName.setResourceKey(title);
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_SettingName_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Help_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void tab_Click(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ToMenu_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ToMenu_Click(ActionEvent e) throws Exception {
        forward(BusinessClassHelper.getSubMenuPath(this.getViewState()
                .getString(M_MENUID_KEY)));
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_DateTimeRange_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_DateFrom_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateFrom_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateFrom_EnterKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateFrom_TabKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_TimeFrom_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeFrom_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeFrom_EnterKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeFrom_TabKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_to_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_DateTo_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateTo_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateTo_EnterKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateTo_TabKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_TimeTo_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeTo_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeTo_EnterKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeTo_TabKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Show_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Show_Click(ActionEvent e) throws Exception {
        setFocus(txt_DateFrom);
        txt_DateFrom.validate();
        setFocus(txt_DateTo);
        txt_DateTo.validate();
        setFocus(null);
        IOHistorySummaryHead head = new IOHistorySummaryHead();

        if (!StringUtils.IsNullOrEmpty(txt_TimeFrom.getText())) {
            head.setTimeFrom(StringUtils.formatDateFromPageToDB(txt_DateFrom
                    .getText())
                    + StringUtils
                    .formatTimeFormPageToDB(txt_TimeFrom.getText()));
        } else {
            head.setTimeFrom(StringUtils.formatDateFromPageToDB(txt_DateFrom
                    .getText())
                    + "000000");
        }
        if (!StringUtils.IsNullOrEmpty(txt_TimeTo.getText())) {
            head.setTimeTo(StringUtils.formatDateFromPageToDB(txt_DateTo
                    .getText())
                    + StringUtils.formatTimeFormPageToDB(txt_TimeTo.getText()));
        } else {
            head.setTimeTo(StringUtils.formatDateFromPageToDB(txt_DateTo
                    .getText())
                    + "000000");
        }
        lst_IOHistorySummaryStationUp.clearRow();
        lst_IOHistorySummaryStationMid.clearRow();
        lst_IOHistorySummaryStationLow.clearRow();
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();

            ASRSInfoCentre centre = new ASRSInfoCentre(conn);

            List upList = centre.getIOHistorySummaryUpList(head);
            for (int i = 0; i < upList.size(); i++) {
                IOHistorySummaryStationUpEntity entity = (IOHistorySummaryStationUpEntity) upList.get(i);

                lst_IOHistorySummaryStationUp.addRow();
                lst_IOHistorySummaryStationUp.setCurrentRow(lst_IOHistorySummaryStationUp.getMaxRows() - 1);
                upListProxy.setRowValueByEntity(entity);
            }

            List midList = centre.getIOHistorySummaryMidList(head);
            for (int i = 0; i < midList.size(); i++) {
                IOHistorySummaryStationMidEntity entity = (IOHistorySummaryStationMidEntity) midList.get(i);

                lst_IOHistorySummaryStationMid.addRow();
                lst_IOHistorySummaryStationMid.setCurrentRow(lst_IOHistorySummaryStationMid.getMaxRows() - 1);
                midListProxy.setRowValueByEntity(entity);
            }

            List lowList = centre.getIOHistorySummaryLowList(head);
            for (int i = 0; i < lowList.size(); i++) {
                IOHistorySummaryStationLowEntity entity = (IOHistorySummaryStationLowEntity) lowList.get(i);

                lst_IOHistorySummaryStationLow.addRow();
                lst_IOHistorySummaryStationLow.setCurrentRow(lst_IOHistorySummaryStationLow.getMaxRows() - 1);
                lowListProxy.setRowValueByEntity(entity);
            }
        } catch (YKKDBException dbEx) {
            String msgString = MessageResources.getText(dbEx.getResourceKey());
            DebugPrinter.print(DebugLevel.ERROR, msgString);
            message.setMsgResourceKey("7200001");
            List paramList = new ArrayList();
            paramList.add(msgString);
            message.setMsgParameter(paramList);
        } catch (YKKSQLException sqlEx) {
            String msgString = MessageResources.getText(sqlEx.getResourceKey());
            DebugPrinter.print(DebugLevel.ERROR, msgString);
            message.setMsgResourceKey("7300001");
            List paramList = new ArrayList();
            paramList.add(msgString);
            message.setMsgParameter(paramList);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
                    message.setMsgResourceKey("7200002");
                }
            }
        }
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_CSV_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_CSV_Click(ActionEvent e) throws Exception {
        setFocus(txt_DateFrom);
        txt_DateFrom.validate();
        setFocus(txt_DateTo);
        txt_DateTo.validate();
        setFocus(null);
        IOHistorySummaryHead head = new IOHistorySummaryHead();

        if (!StringUtils.IsNullOrEmpty(txt_TimeFrom.getText())) {
            head.setTimeFrom(StringUtils.formatDateFromPageToDB(txt_DateFrom
                    .getText())
                    + StringUtils
                    .formatTimeFormPageToDB(txt_TimeFrom.getText()));
        } else {
            head.setTimeFrom(StringUtils.formatDateFromPageToDB(txt_DateFrom
                    .getText())
                    + "000000");
        }
        if (!StringUtils.IsNullOrEmpty(txt_TimeTo.getText())) {
            head.setTimeTo(StringUtils.formatDateFromPageToDB(txt_DateTo
                    .getText())
                    + StringUtils.formatTimeFormPageToDB(txt_TimeTo.getText()));
        } else {
            head.setTimeTo(StringUtils.formatDateFromPageToDB(txt_DateTo
                    .getText())
                    + "000000");
        }

        String root = getServletContext().getRealPath("/csv");

        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();
            File csvRoot = new File(root);
            if (!csvRoot.exists()) {
                csvRoot.mkdir();
            }

            String name = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
            String path = "";

            String osName = System.getProperties().getProperty("os.name");
            if (osName.indexOf("Windows") != -1) {
                path = root + "\\" + name + ".csv";
            } else {
                path = root + "/" + name + ".csv";
            }

            File file = new File(path);

            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "GB2312");
            BufferedWriter bw = new BufferedWriter(osw);

            ASRSInfoCentre centre = new ASRSInfoCentre(conn);
            bw.write("单位:箱,1F铸造ST1101,1F压铸ST1102,2F喷漆ST2101,2F组装ST2105");
            bw.write("\r\n");
            List upList = centre.getIOHistorySummaryUpList(head);
            for (int i = 0; i < upList.size(); i++) {
                IOHistorySummaryStationUpEntity entity = (IOHistorySummaryStationUpEntity) upList.get(i);

                bw.write(entity.getUnitBox() + "," + entity.getSt1101() + "," + entity.getSt1102() + "," + entity.getSt2101() + "," + entity.getSt2105());
                bw.write("\r\n");
            }
            bw.write("\r\n");
            bw.write("单位:箱,1F1205,SI.喷漆2204,外部2216,福永客户2217,组装2214");
            bw.write("\r\n");
            List midList = centre.getIOHistorySummaryMidList(head);
            for (int i = 0; i < midList.size(); i++) {
                IOHistorySummaryStationMidEntity entity = (IOHistorySummaryStationMidEntity) midList.get(i);

                bw.write(entity.getUnitBox() + "," + entity.getSt1205() + "," + entity.getSt2204() + "," + entity.getSt2216() + "," + entity.getSt2217() + "," + entity.getSt2214());
                bw.write("\r\n");
            }
            bw.write("\r\n");
            bw.write("单位:箱,铸造压铸1F-1202,PF工程1F-1212,VF工程1F-1215,MF工程1F-1218,涂装工程2F-2206,外部工程2F-2209");
            bw.write("\r\n");
            List lowList = centre.getIOHistorySummaryLowList(head);
            for (int i = 0; i < lowList.size(); i++) {
                IOHistorySummaryStationLowEntity entity = (IOHistorySummaryStationLowEntity) lowList.get(i);

                bw.write(entity.getUnitBox() + "," + entity.getSt1202() + "," + entity.getSt1212() + "," + entity.getSt1215() + "," + entity.getSt1218() + "," + entity.getSt2206() + "," + entity.getSt2209());
                bw.write("\r\n");
            }

            bw.flush();
            bw.close();
            fos.close();
            osw.close();

            if (path.equals("")) {
                return;
            }
            viewState.setString("file", path);
            addOnloadScript("slb_Download.click();");
        } catch (YKKDBException dbEx) {
            String msgString = MessageResources.getText(dbEx.getResourceKey());
            DebugPrinter.print(DebugLevel.ERROR, msgString);
            message.setMsgResourceKey("7200001");
            List paramList = new ArrayList();
            paramList.add(msgString);
            message.setMsgParameter(paramList);
        } catch (YKKSQLException sqlEx) {
            String msgString = MessageResources.getText(sqlEx.getResourceKey());
            DebugPrinter.print(DebugLevel.ERROR, msgString);
            message.setMsgResourceKey("7300001");
            List paramList = new ArrayList();
            paramList.add(msgString);
            message.setMsgParameter(paramList);
        } catch (Exception ex) {
            DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
            message.setMsgResourceKey("7500001");
            List paramList = new ArrayList();
            paramList.add(ex.getMessage());
            message.setMsgParameter(paramList);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
                    message.setMsgResourceKey("7200002");
                }
            }
        }
    }


    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_StockinStation_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationUp_EnterKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationUp_TabKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationUp_InputComplete(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationUp_ColumClick(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationUp_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationUp_Change(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationUp_Click(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_UnitStockoutStation_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationMid_EnterKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationMid_TabKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationMid_InputComplete(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationMid_ColumClick(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationMid_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationMid_Change(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationMid_Click(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_PickingStation_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationLow_EnterKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationLow_TabKey(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationLow_InputComplete(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationLow_ColumClick(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationLow_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationLow_Change(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_IOHistorySummaryStationLow_Click(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void slb_Download_Server(ActionEvent e) throws Exception {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void slb_Download_Click(ActionEvent e) throws Exception {
        String path = viewState.getString("file");

        ForwardParameters param = new ForwardParameters();
        param.addParameter("file", path);

        redirect("/jsp/SheetDownLoadDummy.jsp", param);
    }


}
//end of class
