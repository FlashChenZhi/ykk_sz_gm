package jp.co.daifuku.wms.base.common.tool.logViewer;
//#CM643407
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;


//#CM643408
/**
 * Acquire one communication trace log information. 
 * From Search condition specified on the communication trace log list screen
 * Retrieve information to be displayed. <BR>
 * Set it in the Communication trace log data class. 
 * From the Communication trace log data class to the communication trace log list class
 * Hand over information. 
 * 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:57:04 $
 * @author  $Author: suresh $
 */

public class TraceTable extends JPanel
{
    //#CM643409
    // Class fields --------------------------------------------------
    //#CM643410
    /**
     * Rft Title machine No
     */
    public String rftNo;
    
    //#CM643411
    /**
     * Communication trace log list table
     */
    private JTable tblTraceList;

    //#CM643412
    /**
     * Scroll panel
     */
    JScrollPane scrollPane;

    //#CM643413
    /**
     * Communication trace log list table model
     */
    private DefaultTableModel model;

    //#CM643414
    /**
     * Width of communication trace log list table line - number cell
     */
    public static final int LineNoWidth = 40;

    //#CM643415
    /**
     * Communication trace log list table processing date width of cell
     */
    public static final int ProcessDateWidth = 80;
    
    //#CM643416
    /**
     * Communication trace log list table processing time width of cell
     */
    public static final int ProcessTimeWidth = 80;

    //#CM643417
    /**
     * Communication trace log list table transmission/width of reception division cell
     */
    public static final int SendReceiveDivisionWidth = 65;

    //#CM643418
    /**
     * Width of communication trace log list table IDNo cell
     */
    public static final int IdNoWidth = 45;

    //#CM643419
    /**
     * Width of communication trace log list table cable cell
     */
    public static final int TraceMessageWidth = LogViewerParam.TraceMessageWidth;

    //#CM643420
    /**
     * Font
     */
   protected final Font TableFont = new Font("Monospaced", Font.PLAIN, 12);    
    
    //#CM643421
    /**
     * Size of panel for table
     */
    public static final Dimension PanelSize
    	= new Dimension(LogViewerParam.WindowWidth - 40,
    					LogViewerParam.WindowHeight - 285);
    
    //#CM643422
    /**
     * RGB value of background color
     */
    final int[] backColor = LogViewerParam.BackColor;

    //#CM643423
    /**
     * Communication trace log list header
     */
    private static String[] ColumnNames =
    	{" ",
    	 DispResourceFile.getText("LBL-W0560"),
    	 DispResourceFile.getText("LBL-W0561"),
    	 DispResourceFile.getText("LBL-W0188"),
    	 DispResourceFile.getText("LBL-W0001"),
    	 DispResourceFile.getText("LBL-W0288")
    	 };

    //#CM643424
    /**
	 * Communication trace log data
	 */
    protected TraceList traceList;
    protected Selection mouseAdapter = new Selection();
    
    //#CM643425
    /**
     * Constructor
     */
    TraceTable()
    {
    	super();

    	model = new DefaultTableModel(ColumnNames, 0);
		tblTraceList = new JTable(model);

		//#CM643426
		// Setting of mouse listener
		tblTraceList.addMouseListener(mouseAdapter);

		setColumnInfo();

		//#CM643427
		// The content of the display should be not able to be edited. 
		tblTraceList.setEnabled(false);

        //#CM643428
        // The change of the width of the cell with the mouse is invalid. 
        tblTraceList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        //#CM643429
        // Set the table header. 
        JTableHeader tableHeader = tblTraceList.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        
        //#CM643430
        // Setting of Scroll panel
        scrollPane = new JScrollPane(tblTraceList);
        scrollPane.setPreferredSize(PanelSize);

        this.setFocusable(false);
        tblTraceList.setFocusable(false);
        scrollPane.setFocusable(false);
		tableHeader.setFocusable(false);
        
        //#CM643431
        // Setting of font
        tblTraceList.setFont(TableFont);
        
		//#CM643432
		// Setting of background color
        this.setBackground(new Color(backColor[0], backColor[1], backColor[2]));

        //#CM643433
        // Display of Scroll panel
        this.add(scrollPane);
    }
    
    //#CM643434
    /**
	 * Processing when [ display ] button is pressed on communication trace log list screen
	 * 
	 * @param list Communication trace log data
	 */
    public void setData(TraceList list)
    {
    	traceList = list;
    	mouseAdapter.setTraceList(list);
    	
        int dataCount = list.getSize();
        String tableData[][] = new String[dataCount][ColumnNames.length];

        for (int idx = 0; idx < dataCount; idx ++)
        {
        	TraceData data = list.getTraceData(idx);
            tableData[idx][0] = String.valueOf(idx + 1);
            tableData[idx][1] = data.getProcessDate();
            tableData[idx][2] = data.getProcessTime();
            if (data.getSendRecvDivision().equals("S"))
            {
                tableData[idx][3] = DispResourceFile.getText("RDB-W0052");
            }
            else
            {
                tableData[idx][3] = DispResourceFile.getText("RDB-W0053");
            }
            tableData[idx][4] = data.getIdNo();
            tableData[idx][5] = data.getStringMessage().trim();
        }
    	
        //#CM643435
        // Data is set in the list. 
        model.setDataVector(tableData, ColumnNames);

        setColumnInfo();
    }

    //#CM643436
    /**
     * Set the attribute of each column of the table. 
     * 
     */
    protected void setColumnInfo()
    {
        //#CM643437
        // Setting of width of cell
        tblTraceList.getColumnModel().getColumn(0).setPreferredWidth(LineNoWidth);
        tblTraceList.getColumnModel().getColumn(1).setPreferredWidth(ProcessDateWidth);
        tblTraceList.getColumnModel().getColumn(2).setPreferredWidth(ProcessTimeWidth);
        tblTraceList.getColumnModel().getColumn(3).setPreferredWidth(SendReceiveDivisionWidth);
        tblTraceList.getColumnModel().getColumn(4).setPreferredWidth(IdNoWidth);
        tblTraceList.getColumnModel().getColumn(5).setMinWidth(TraceMessageWidth);

        //#CM643438
        // Setting of line - number column
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) tblTraceList.getColumnModel();
		for (int i = 0; i < tblTraceList.getColumnCount(); i ++)
		{
			TableColumn tableColumn = columnModel.getColumn(i);
			if (i == 0)
			{
				tableColumn.setCellRenderer(new Column0Recorder());
				tableColumn.setMaxWidth(LineNoWidth);
				tableColumn.setResizable(false);
			}
			if (i == 5)
			{
				tableColumn.setResizable(true);
			}
			else
			{
				tableColumn.setResizable(false);
			}
		}
    }

    //#CM643439
    /**
     * Set Rft Title machine No. 
     * @param value Rft Title machine No
     */
    public void setRftNo(String value)
    {
        rftNo = value;
    }
    
    //#CM643440
    /**
     * Acquire Rft Title machine No. 
     * @return Rft Title machine No
     */
    public String getRftNo()
    {
      return rftNo;  
    }
    
    //#CM643441
    /**
     * Clear the content of the display. 
     */
    public void clear()
    {
        //#CM643442
        // Data is set in the list. 
        model.setDataVector(null, ColumnNames);
        
        setColumnInfo();
    }
}

//#CM643443
/**
 * Click event processing of mouse with communication trace log list card<BR>
 * When the line - number column is clicked, cable information on the selection line is handed over to the detailed inquiry screen. <BR>
 * Display detailed information on each item of a detailed cable. 
 */
class Selection extends MouseAdapter
{
	private TraceList traceList;

	Selection()
	{
		super();
	}

	public void setTraceList(TraceList list)
	{
		traceList = list;
	}
	
	public void mousePressed(MouseEvent e)
	{
		//#CM643444
		// Table information acquisition
		JTable cell = (JTable) e.getSource();

		//#CM643445
		// Selection row acquisition
		int col = cell.columnAtPoint(e.getPoint());

		//#CM643446
		// Selection line acquisition
		int row = cell.rowAtPoint(e.getPoint());

		//#CM643447
		// When line - number is pressed,It changes to the detailed inquiry screen. 
		if (col == 0)
		{
			//#CM643448
			// Acquisition of ID item setting information
			DetailsWindow disp = new DetailsWindow();

			//#CM643449
			// It changes to the detailed inquiry screen. 
			disp.startPopup(traceList.getRftNo(), traceList.getTraceData(row));
		}
	}
}

//#CM643450
/**
 * Set the line - number row of the communication trace log list. <BR>
 * Set the Orange of the background color of the line - number. 
 */
class Column0Recorder extends DefaultTableCellRenderer 
{
    //#CM643451
    /**
	 * Line - number background color
	 */
    final Color LineAreaBackColor = new Color(255, 165, 0);
    
    public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column)
	{
		//#CM643452
		// Orange of the background color. 
		setBackground(LineAreaBackColor);
		//#CM643453
		// Line - number set
		setValue(new Integer(row + 1));
		//#CM643454
		// Setting of ruled line
		setBorder(new BevelBorder(BevelBorder.RAISED));
		setHorizontalAlignment(RIGHT);

		return this;
	}
}


