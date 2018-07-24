//#CM643055
//$Id: LogViewer.java,v 1.2 2006/11/07 05:51:31 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643056
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.NoSuchElementException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;

import jp.co.daifuku.common.text.StringUtil;

//#CM643057
/**
 * Communication trace log list screen<BR>
 * Make the Communication trace log list display. 
 * 
 * <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:51:31 $
 * @author $Author: suresh $
 */

class LogViewer extends JFrame implements ActionListener
{
	//#CM643058
	/**
	 * Window title
	 */
	final String WindowTitle = DispResourceFile.getText("TLE-9000");
    
    //#CM643059
    /**
     * List display number
     */
    private static int TraceLogFileDispCnt;

    //#CM643060
    /**
     * Operation panel
     */
    LvOperationPanel comOperationPanel;
    
    //#CM643061
    /**
     * Rft Title machine No input panel
     */
    LvRftNo comRftNo;

    //#CM643062
    /**
     * ID input panel
     */
    LvIdNo comIdNo;

    //#CM643063
    /**
     * Search condition beginning panel
     */
    LvDateTime comStDateTime;
    
    //#CM643064
    /**
     * Search condition end panel
     */
    LvDateTime comEdDateTime;

    //#CM643065
    /**
     * Display flag radio Button panel
     */
    LvDisplayConditionButton comDisplayConditionButton;

    //#CM643066
    /**
     * Trace data list table
     */
    TraceTable comTraceLogTable = new TraceTable();

    //#CM643067
    /**
     * End Button
     */
    LvButton btnExit;

    //#CM643068
    /**
     * Set List display number. 
     * @param Trace log List display number
     * @author tsukoi
     */
    public static void setTraceLogFileDispCnt(int Count)
    {
       TraceLogFileDispCnt = Count;
    }
    
    //#CM643069
    /**
     * Acquire List display number. 
     * @return List display number
     * @author tsukoi
     */
    public static int getTraceLogFileDispCnt()
    {
        return TraceLogFileDispCnt;
    }

    //#CM643070
    /**
     * Generate the instance.
     */
    LogViewer()
    {

        //#CM643071
        // The screen size is acquired from the configuration file. 
        final int windowWidth = LogViewerParam.WindowWidth;
        final int windowHeight = LogViewerParam.WindowHeight;

        //#CM643072
        // Make Control
        this.setTitle(WindowTitle);
        comRftNo = OperationMode.getRftNoInstance(); // RFT号機Noパネル
        comIdNo = new LvIdNo(); // IDパネル
        comStDateTime = new LvDateTime(DispResourceFile.getText("LBL-W0221")); // 検索条件開始パネル
        comEdDateTime = new LvDateTime(DispResourceFile.getText("LBL-W0058")); // 検索条件終了パネル
        comDisplayConditionButton = new LvDisplayConditionButton();// 表示区分ラジオボタンパネル
        comOperationPanel = new LvOperationPanel(this); // 表示、クリアボタンパネル
    	btnExit = new LvButton("MBTN-W0040");
    	btnExit.addActionListener(this);

    	//#CM643073
    	// Container generation
        Container contentPane = this.getContentPane();

        //#CM643074
        // Setup of Frame
        contentPane.setLayout(new FlowLayout());

        //#CM643075
        // Add Control
        contentPane.add(comRftNo); // RFT号機No
        contentPane.add(comIdNo); // ID
        contentPane.add(comStDateTime); // 検索条件開始
        contentPane.add(comEdDateTime); // 検索条件終了
        contentPane.add(comDisplayConditionButton); // 表示区分
        contentPane.add(comOperationPanel); // 
        contentPane.add(comTraceLogTable); // テーブル
        contentPane.add(btnExit); // 終了ボタン

        //#CM643076
        // Definition of layout
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        //#CM643077
        // Setting of Control length position
        layout.putConstraint(SpringLayout.NORTH, comRftNo, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, comIdNo, 0, SpringLayout.SOUTH, comRftNo);
        layout.putConstraint(SpringLayout.NORTH, comStDateTime, 0, SpringLayout.SOUTH, comIdNo);
        layout.putConstraint(SpringLayout.NORTH, comEdDateTime, 0, SpringLayout.SOUTH, comIdNo);
        layout.putConstraint(SpringLayout.NORTH, comDisplayConditionButton, 0, SpringLayout.SOUTH, comEdDateTime);
        layout.putConstraint(SpringLayout.NORTH, comOperationPanel, 0, SpringLayout.SOUTH, comDisplayConditionButton);
        layout.putConstraint(SpringLayout.NORTH, comTraceLogTable, 5, SpringLayout.SOUTH, comOperationPanel);
        layout.putConstraint(SpringLayout.NORTH, btnExit, 10, SpringLayout.SOUTH, comTraceLogTable);

        //#CM643078
        // Setting of Control horizontal position
        layout.putConstraint(SpringLayout.WEST, comRftNo, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, comIdNo, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, comStDateTime, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, comEdDateTime, 260, SpringLayout.WEST, comStDateTime);
        layout.putConstraint(SpringLayout.WEST, comDisplayConditionButton, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, comTraceLogTable, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, comOperationPanel, 10, SpringLayout.WEST, comTraceLogTable);
        layout.putConstraint(SpringLayout.WEST, btnExit, 10, SpringLayout.WEST, comTraceLogTable);

        //#CM643079
        // Setting of form
        int[] formBackColor = LogViewerParam.BackColor;
        this.getContentPane().setBackground(new Color(formBackColor[0], formBackColor[1], formBackColor[2]));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(windowWidth, windowHeight);
        this.setVisible(true);
        this.setResizable(false);

		//#CM643080
		// Focus must move with the ENTER key. 
		HashSet keySet = new HashSet();
		AWTKeyStroke s = AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0);
		keySet.add(s);
		s = AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, 0);
		keySet.add(s);
		this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, keySet);
    }

    //#CM643081
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        
    	Object sender = e.getSource();
    	if (sender == btnExit)
    	{
            //#CM643082
            // End the application when you press End Button. 
            System.exit(0);
    	}
    	else
    	{
    		if (sender == comOperationPanel.getClearButton())
    		{
    			clearInputArea();
    		}
    		else if (sender == comOperationPanel.getDisplayButton())
    		{
    			displayList();
    		}
    	}
    }

    //#CM643083
    /**
     * Communication trace log list screen
     * 
     * @param args Operation mode
     */
    public static void main(String[] args)
    {
    	//#CM643084
    	// Set Operation mode specified by the argument. 
    	OperationMode.setMode(args[0]);
    	
    	try
    	{
    		LogViewerParam.initialize();
    	}
    	catch (Exception e)
    	{
    		//#CM643085
    		// After notified by the dialog when failing in setting Reading of file
    		//#CM643086
    		// Start by the default setting. 
    		String param[] = {LogViewerParam.resourcePath};
    		String msg = MessageResourceFile.getText(e.getMessage());
			msg = MessageFormat.format(msg, param);
    		JOptionPane.showMessageDialog(null, msg);
    	}

        //#CM643087
        //The main processing
        new LogViewer();
    }
    
    //#CM643088
    /**
     * Display the list of the communication and the race log. 
     */
    protected void displayList()
    {
        //#CM643089
        // For Communication trace log list class maintenance
        TraceList dispData = null;

        if (! checkInputData())
        {
        	return;
        }
        
        TraceLogFile traceLogFile = OperationMode.getTraceLogFileInstance();
        traceLogFile.setRftNo(comRftNo.getRftNo());
        traceLogFile.setIdNo1(comIdNo.getIdNo1());
        traceLogFile.setIdNo2(comIdNo.getIdNo2());
        traceLogFile.setStartProcessDate(comStDateTime.getDate());
        traceLogFile.setStartProcessTime(comStDateTime.getTime());
        traceLogFile.setEndProcessDate(comEdDateTime.getDate());
        traceLogFile.setEndProcessTime(comEdDateTime.getTime());
        traceLogFile.setDisplayCondition(comDisplayConditionButton.getSendRecvDivision());

        //#CM643090
        // The list data is acquired. 
        try
        {
            dispData = traceLogFile.getTraceData();
            dispData.setRftNo(comRftNo.getRftNo());
            comTraceLogTable.setData(dispData);
        }
        catch (EOFException e)
        {
            comTraceLogTable.clear();
            String param[] = null;
            param = new String[1];
            param[0] = "RFT" + comRftNo.getRftNo() + TraceLogFile.TraceLogFileName;
            String msg = "";
            msg = MessageResourceFile.getText("6006020");
            msg = MessageFormat.format(msg, param);
            JOptionPane.showMessageDialog(null, msg);
        }
        catch (NoSuchElementException e)
        {
            comTraceLogTable.clear();
            String param[] = null;
            param = new String[1];
            param[0] = "RFT" + comRftNo.getRftNo() + TraceLogFile.TraceLogFileName;
            String msg = "";
            msg = MessageResourceFile.getText("6006020");
            msg = MessageFormat.format(msg, param);
            JOptionPane.showMessageDialog(null, msg);
        }
        catch (Exception e)
        {
            comTraceLogTable.clear();
        	String msgCode = e.getMessage();
    		JOptionPane.showMessageDialog(this, MessageResourceFile.getText(msgCode));
        }
    }
    
    //#CM643091
    /**
     * Check Search condition of the input area. 
     * 
     * @return	Return true when it is error-free, false when there is an error. 
     */
    protected boolean checkInputData()
    {
    	String param[] = null;

    	try
    	{
        	String rftNo = comRftNo.getRftNo();
        	if (StringUtil.isBlank(rftNo))
        	{
        		//#CM643092
        		// Title machine NO uninput
        		throw new Exception("6023179");
        	}

        	String id1 = comIdNo.getIdNo1();
        	String id2 = comIdNo.getIdNo2();
        	if (! StringUtil.isBlank(id1))
        	{
        		try
        		{
        			Integer.parseInt(id1);
        		}
        		catch (NumberFormatException e)
        		{
        			param = new String[1];
        			param[0] = "ID1";
        			throw new Exception("6003104");
        		}
        	}
        	if (! StringUtil.isBlank(id2))
        	{
        		try
        		{
        			Integer.parseInt(id2);
        		}
        		catch (NumberFormatException e)
        		{
        			param = new String[1];
        			param[0] = "ID2";
        			throw new Exception("6003104");
        		}
        	}
        	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
        	dateFormatter.setLenient(false);
        	SimpleDateFormat datetimeFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        	datetimeFormatter.setLenient(false);
        	String startDate = comStDateTime.getDate();
    		String startTime = comStDateTime.getTime();
    		java.util.Date startDateTime = null;
    		java.util.Date endDateTime = null;
        	if (! StringUtil.isBlank(startDate))
        	{
        		if (startDate.length() < 8)
        		{
    				throw new Exception("6023038");
        		}

        		try
    			{
        			if (StringUtil.isBlank(startTime))
        			{
        				startDateTime = dateFormatter.parse(startDate);
        			}
        			else
        			{
        				startDateTime = datetimeFormatter.parse(startDate + startTime);
        			}
    			}
    			catch (ParseException e)
    			{
    				throw new Exception("6023180");
    			}
        	}
        	else if (! StringUtil.isBlank(startTime))
        	{
        		throw new Exception("6023180");
        	}

        	String endDate = comEdDateTime.getDate();
    		String endTime = comEdDateTime.getTime();
        	if (! StringUtil.isBlank(endDate))
        	{
        		if (endDate.length() < 8)
        		{
    				throw new Exception("6023038");
        		}

        		try
    			{
        			if (StringUtil.isBlank(endTime))
        			{
        				endDateTime = dateFormatter.parse(endDate);
        			}
        			else
        			{
        				endDateTime = datetimeFormatter.parse(endDate + endTime);
        			}
    			}
    			catch (ParseException e)
    			{
    				throw new Exception("6023181");
    			}
        	}
        	else if (! StringUtil.isBlank(endTime))
        	{
        		throw new Exception("6023181");
        	}

        	if (startDateTime != null && endDateTime != null)
        	{
        		if (StringUtil.isBlank(endTime))
        		{
    				endDateTime = datetimeFormatter.parse(endDate + "235959");
        		}

        		if (startDateTime.compareTo(endDateTime) > 0)
        		{
        			//#CM643093
        			// The error of Start date is the following case from End date. 
        			throw new Exception("6023182");
        		}
        	}

        	return true;
    	}
    	catch (Exception e)
    	{
    		String msg = "";
			msg = MessageResourceFile.getText(e.getMessage());
    		if (param != null)
    		{
    			msg = MessageFormat.format(msg, param);
    		}
    		JOptionPane.showMessageDialog(this, msg);
    		return false;
    	}
    }

    //#CM643094
    /**
     * Initialize the input area. 
     */
    protected void clearInputArea()
    {
    	comRftNo.clear();
    	comIdNo.clear();
    	comStDateTime.clear();
    	comEdDateTime.clear();
    	comDisplayConditionButton.clear();
        comTraceLogTable.clear();
    }
}
