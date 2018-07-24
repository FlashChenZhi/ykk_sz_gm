//#CM642939
//$Id: ColumnTable.java,v 1.2 2006/11/07 05:49:59 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM642940
/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

//#CM642941
/**
 * <pre>
 * Detailed inquiry screen display information Component<br>
 * Detailed inquiry display information is returned, <br>
 * and set display information to JTable <br>
 * and return JScrollPane the putting panel. <br>
 * </pre>
 * @author 1.00 hota
 * @version 1.00 2006/02/02 
 */
public class ColumnTable extends JPanel
{
	//#CM642942
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/07 05:49:59 $";
	}

	//#CM642943
	/**
	 * Header Title
	 */
	private static String[] header = {DispResourceFile.getText("LBL-W0570")
                                       ,DispResourceFile.getText("LBL-W0571")};

	//#CM642944
	/**
	 * Width of column (item)
	 */
	private final int NameWidth = 150;

	//#CM642945
	/**
	 * Width of column (Content)
	 */
	private final int ValueWidth = 400;

	//#CM642946
	/**
	 * Number of one line of items Content of maximum displays byte
	 */
	protected final int maxLength = 64;

    //#CM642947
    /**
     * Size of panel for table
     */
    public static final Dimension PanelSize = new Dimension(553, 420);

    //#CM642948
    /**
	 * Background color of Tooltip
	 */
	protected final Color toolTipColor = new Color(255, 255, 229);

	//#CM642949
	/**
	 * Default Constructor
	 */
	public ColumnTable()
	{
		super();
	}

	//#CM642950
	/**
	 * Constructor which specifies number of displayed label Character string
	 * 
	 * @param idInfo Number of DispResource
	 */
	public ColumnTable(IdData[] idInfo)
	{
		super();
		initialize(idInfo);
	}


	//#CM642951
	/**
	 * Decompose Transmission of each ID item after acquiring Transmission information, 
	 * IDItem name, and Content, 
	 * set in the table, 
	 * and return JScrollPane Component. 
	 * 
	 * @param idInfo Array of Transmission information
	 * @return JScrollPane Component
	 */
	public JScrollPane initialize(IdData[] idInfo)
	{
		//#CM642952
		// For setting the Transmission information. 
	    final String data[][] = new String[idInfo.length][3];

	    //#CM642953
	    // Acquire display Content data. 
		for (int i = 0; i < idInfo.length; i ++)
		{
			data[i][0] = idInfo[i].getTelegramData().getName();
			data[i][1] = idInfo[i].getTelegramData().getValue();
			data[i][2] = idInfo[i].getTelegramData().getComment();
		}
	
	    JTable table;

	    //#CM642954
	    // Information is set in the table. 
	    table = new JTable(data, header)
		{
			//#CM642955
			// Mouse event for Tooltip
			public String getToolTipText(MouseEvent me)
			{
				//#CM642956
				// Mouse's position is acquired. 
				Point pt = me.getPoint();
				int row = rowAtPoint(pt);
				
				//#CM642957
				// Tooltip display data acquisition
				String toolTipText = data[row][2];
				if (row < 0) 
				{
					return null;
				}
				else
				{
					//#CM642958
					// Do not display Tooltip when there is no Comment. 
					if(toolTipText.equals(null) || toolTipText.equals(""))
					{
						return null;
					}
					return toolTipText;
				}
			}
			//#CM642959
			// Original Tooltip is made. 
			public JToolTip createToolTip() 
			{
				return new JToolTipEx();
			}
			//#CM642960
			// Set the background color. 
			class JToolTipEx extends JToolTip 
			{
				public void paint(Graphics g) 
				{
					setBackground(toolTipColor);
					super.paint(g);
				}
			}
		}
        ;

        //#CM642961
        // Size setting of table column
        TableColumn nameColumn = table.getColumnModel().getColumn(0);
        nameColumn.setPreferredWidth(NameWidth);

        TableColumn valueColumn = table.getColumnModel().getColumn(1);
        valueColumn.setPreferredWidth(ValueWidth);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //#CM642962
        // Acquire the height of the line. 
        int rowHeight = table.getRowHeight();
        for (int i = 0; i < data.length; i ++)
        {
			//#CM642963
			// When the number of bytes of Transmission flagContent is larger than the number of display maximum byte
			if (data[i][1].length() > maxLength)
			{
				//#CM642964
				// The number in which Length of Transmission flagContent is divided by the number of display maximum byte is assumed to be the number of lines. 
				int rowCnt = data[i][1].length() / maxLength;
				if (data[i][1].length() % maxLength > 0)
				{
					rowCnt ++;
				}

				//#CM642965
				// Set the height of the amount of the specified number of lines. 
				table.setRowHeight(i, rowHeight * rowCnt);
			}
        }

        //#CM642966
        // Set the table header. 
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);
        
        //#CM642967
        // Display table setting
        table.setDefaultRenderer(Object.class, new MyCellRenderer());

        //#CM642968
        // Make the action of the table to unuseable. 
        table.setEnabled(false);

        //#CM642969
        // Table Content is set in JScrollPane. 
        JScrollPane scrollPane = new JScrollPane(table);

        //#CM642970
        // Make the action of the Scroll pane to unuseable. 
		scrollPane.setEnabled(false);
        scrollPane.setPreferredSize(PanelSize);
		this.add(scrollPane);

		return scrollPane;
	}

	//#CM642971
	/**
	 * Original Component setting when table information exceeds width of the maximum display<BR>
	 * Class to change line when TransmissionContent exceeds width of the maximum display row
	 */
	class MyCellRenderer extends JTextArea implements TableCellRenderer
	{
		//#CM642972
		/**
		 * Constructor
		 */
		MyCellRenderer()
		{
			super();
			setLineWrap(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
													   boolean isSelected, boolean hasFocus,
													   int row, int column)
		{
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
}
