// $Id: skeltenBusiness.java,v 1.2 2007/03/07 07:45:23 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Inquiry;

import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.YkkGMAX.Entities.RetrievalOrderPrintHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;

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
public class RetrievalOrderPrintBusiness extends RetrievalOrderPrint implements WMSConstants
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    private final String DIALOG_FLAG = "DIALOG_FLAG";

    private final String RETRIEVAL_ORDER_PRINT_HEAD = "RETRIEVAL_ORDER_PRINT_HEAD";

    private final String ITEM_CODE = "ITEM_CODE";

    private final String MANAGE_ITEM_FLAG = "MANAGE_ITEM_FLAG";

    /**
     * Initializes the screen.
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e) throws Exception
    {
        session.setAttribute(RETRIEVAL_ORDER_PRINT_HEAD, new RetrievalOrderPrintHead());
        session.setAttribute(ITEM_CODE, null);
        rdo_ItemNo.setChecked(false);
        rdo_WhenNextWorkBegin.setChecked(true);
    }

    /**
     * Refered before calling each control event.
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Initialize(ActionEvent e) throws Exception
    {
        String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
        if (menuparam != null)
        {
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
            // lbl_SettingName.setResourceKey(title);
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
    public void lbl_SettingName_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Help_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void tab_Click(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ToMenu_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ToMenu_Click(ActionEvent e) throws Exception
    {
        forward(BusinessClassHelper.getSubMenuPath(this.getViewState()
                .getString(M_MENUID_KEY)));
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_DateTimeRange_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_Star1_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_DateFrom_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateFrom_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateFrom_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateFrom_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_TimeFrom_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeFrom_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeFrom_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeFrom_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_to_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_DateTo_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateTo_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateTo_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_DateTo_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_TimeTo_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeTo_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeTo_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_TimeTo_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_Section_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_Star2_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Section_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Section_AutoCompleteItemClick(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Section_AutoComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Section_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Section_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Section_InputComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_Line_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Line_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Line_AutoCompleteItemClick(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Line_AutoComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Line_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Line_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Line_InputComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_WhenNextWorkBegin_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_WhenNextWorkBegin_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_WhenNextWorkBegin_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_WhenNextWorkBegin_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_Dynasty_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_ItemNo_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo_AutoCompleteItemClick(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo_AutoComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo_InputComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Search_POPUP_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Search_POPUP_Click(ActionEvent e) throws Exception
    {
        session.setAttribute(ITEM_CODE, txt_ItemNo.getText());

        session.setAttribute(MANAGE_ITEM_FLAG, null);

        redirect("/YkkGMAX/Popup/ItemView.do", null, "/progress.do");
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName1_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName1_AutoCompleteItemClick(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName1_AutoComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName1_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName1_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName1_InputComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_ColorCode_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ColorCode_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ColorCode_AutoCompleteItemClick(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ColorCode_AutoComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ColorCode_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ColorCode_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ColorCode_InputComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName2_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName2_AutoCompleteItemClick(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName2_AutoComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName2_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName2_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName2_InputComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName3_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName3_AutoCompleteItemClick(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName3_AutoComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName3_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName3_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemName3_InputComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_OrderBy_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_WhenNextWorkBegin_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_WhenNextWorkBegin_Click(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_ItemNoChcked_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_ItemNoChcked_Click(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Show_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Show_Click(ActionEvent e) throws Exception
    {
        setFocus(txt_DateFrom);
        txt_DateFrom.validate();
        setFocus(txt_DateTo);
        txt_DateTo.validate();
        setFocus(txt_Section);
        txt_Section.validate();
        setFocus(null);

        setHeadSession();

        addOnloadScript("slb_Popup.click();");
    }

    private void setHeadSession()
    {
        RetrievalOrderPrintHead head = new RetrievalOrderPrintHead();

        if (!StringUtils.IsNullOrEmpty(txt_TimeFrom.getText()))
        {
            head.setTimeFrom(StringUtils.formatDateFromPageToDB(txt_DateFrom
                    .getText())
                    + StringUtils
                    .formatTimeFormPageToDB(txt_TimeFrom.getText()));
        }
        else
        {
            head.setTimeFrom(StringUtils.formatDateFromPageToDB(txt_DateFrom
                    .getText())
                    + "000000");
        }
        if (!StringUtils.IsNullOrEmpty(txt_TimeTo.getText()))
        {
            head.setTimeTo(StringUtils.formatDateFromPageToDB(txt_DateTo
                    .getText())
                    + StringUtils.formatTimeFormPageToDB(txt_TimeTo.getText()));
        }
        else
        {
            head.setTimeTo(StringUtils.formatDateFromPageToDB(txt_DateTo
                    .getText())
                    + "000000");
        }
        head.setItem(txt_ItemNo.getText());
        head.setSection(txt_Section.getText());
        head.setLine(txt_Line.getText());
        head.setNextWorkBeginDate(StringUtils.formatDateFromPageToDB(txt_WhenNextWorkBegin.getText()));
        head.setColorCode(txt_ColorCode.getText());
        if (rdo_ItemNo.getChecked())
        {
            head.setOrderBy("1");
        }
        else
        {
            head.setOrderBy("2");
        }
        session.setAttribute(RETRIEVAL_ORDER_PRINT_HEAD, head);
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Clear_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Clear_Click(ActionEvent e) throws Exception
    {
        txt_TimeFrom.setText("");
        txt_DateFrom.setText("");
        txt_TimeTo.setText("");
        txt_TimeFrom.setText("");
        txt_Section.setText("");
        txt_Line.setText("");
        txt_WhenNextWorkBegin.setText("");
        txt_ItemNo.setText("");
        txt_ItemName1.setText("");
        txt_ItemName2.setText("");
        txt_ItemName3.setText("");
        txt_ColorCode.setText("");
    }

    /**
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_SortCondition_Server(ActionEvent e) throws Exception
    {
    }


	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void slb_Popup_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void slb_Popup_Click(ActionEvent e) throws Exception
	{
          redirect("/YkkGMAX/Inquiry/RetrievalOrderPrintPopup.do", null, "/progress.do");
	}


	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ItemNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ItemNo_Click(ActionEvent e) throws Exception
	{
	}


}
//end of class
