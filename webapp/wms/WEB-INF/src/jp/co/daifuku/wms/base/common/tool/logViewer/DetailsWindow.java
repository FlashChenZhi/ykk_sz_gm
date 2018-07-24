//#CM642973
//$Id: DetailsWindow.java,v 1.2 2006/11/07 05:50:18 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM642974
/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

//#CM642975
/**
 * Designer :  <BR>
 * Maker :   <BR>
 * <BR>
 * Detailed inquiry screen
 * </pre>
 * @author 1.00 hota
 * @version 1.00 2006/02/02 (MTB) New making
 */
public class DetailsWindow extends JFrame implements ActionListener
{
	//#CM642976
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/07 05:50:18 $";
	}
	
	//#CM642977
	/**
	 * Generate the instance.
	 */
	public DetailsWindow()
	{
		super();
		this.setResizable(false);
	}
	
	//#CM642978
	/**
	 * Size of window(width)
	 */
	final int widthSize = 600;

	//#CM642979
	/**
	 * Size of window(height)
	 */
	final int heightSize = 600;

	//#CM642980
	/**
	 * Header Title
	 */
	final String WindowTitle = DispResourceFile.getText("TLE-9001");

	//#CM642981
	/**
	 * Background color setting
	 */
    final int[] formBackColor = LogViewerParam.BackColor;		

	//#CM642982
	/**
	 * Display Detailed inquiry screen. <BR>
	 * Receive and process each Transmission, Title machine NO, the processing date, <BR>
	 * and ID from the Communication trace log list display.<BR>
	 * 
	 * Shut this window at the error. <BR>
	 * 
	 * @param 	rftNo	Rft Title machine No
	 * @param	traceData	Trace log information
	 */
	public void startPopup (String rftNo, TraceData traceData)
	{
		try 
		{
			//#CM642983
			// For Transmission information maintenance
			IdData telegramData[] = null;
			
			//#CM642984
			// ID item configuration file
			IdLayout idColumns = new IdLayout();
			
			//#CM642985
			// Data for the display is acquired based on Transmission and ID. 
			telegramData = idColumns.getIdColumns(traceData.getCommunicateMessage(), traceData.getIdNo());

			if (telegramData == null)
			{
				//#CM642986
				// Shut the window when you cannot acquire data. 
				closeWindow();
			}
			else
			{
				//#CM642987
				// Content displayed on the screen is arranged. 
				detailsDisp(telegramData, rftNo, traceData.getProcessDate() + " " + traceData.getProcessTime());

				show();
			}
		} 
		catch (Exception e) 
		{
            
		    JOptionPane.showMessageDialog(null, e.getMessage());

    		//#CM642988
    		// Shut a detailed window at the error. 
			closeWindow();
		}	
	}

	//#CM642989
	/**
	 * Display Detailed inquiry screen. <BR>
	 * Specify, and put the layout of the panel, the list, and the 
	 * button made on the frame with each Component. 
	 * 
	 * @param telegramData Transmission information
	 * @param rftNo Rft Title machine No
	 * @param dateTime Processing date
	 */
	public void detailsDisp (IdData telegramData[], String rftNo, String dateTime)
	{
		JScrollPane scrollPane;	// 一覧表示パネル

		Container contentPane = this.getContentPane();

		//#CM642990
		// Screen setting
		//#CM642991
		// Header Title, screen size, frame setting, and background color
		setTitle(WindowTitle);										// タイトル
		this.setSize(widthSize, heightSize);						// サイズ
		contentPane.setVisible(true);								// 可視設定
		
		//#CM642992
		// Background color of frame
		this.getContentPane().setBackground(new Color(formBackColor[0], formBackColor[1], formBackColor[2]));

		//#CM642993
		// Layout manager
		SpringLayout layout = new SpringLayout();
		//#CM642994
		//  Component Container
		this.getContentPane().setLayout(layout);
		
		//#CM642995
		// Generation of instance of Rft Title machine No Component
		LvRftNo comRftNo = OperationMode.getRftNoInstance(false, rftNo); // RFT号機Noパネル

		//#CM642996
		// Generation of instance of Processing date Component
		LvDateTimePanel comDate = new LvDateTimePanel("LBL-W0039", dateTime);

		//#CM642997
		// Instance generation of table Component
		ColumnTable comTable = new ColumnTable();
		
		//#CM642998
		// Detailed inquiry table
		scrollPane = comTable.initialize(telegramData);

		//#CM642999
		// Button Component
		LvButton btnClose = new LvButton("BTN-9003");

		//#CM643000
		// Arrangement of Rft Title machine No
		contentPane.add(comRftNo);
		layout.putConstraint(SpringLayout.NORTH, comRftNo, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, comRftNo, 20, SpringLayout.WEST, this);

        //#CM643001
        // Arrangement of Processing date
    	contentPane.add(comDate);
		layout.putConstraint(SpringLayout.NORTH, comDate, 40, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, comDate, 20, SpringLayout.WEST, this);

        //#CM643002
        // Arrangement of item and Content
		contentPane.add(scrollPane);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 85, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, this);
		
        //#CM643003
        // Arrangement of Button
		contentPane.add(btnClose);
		layout.putConstraint(SpringLayout.NORTH, btnClose, 515, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, btnClose, 500, SpringLayout.WEST, this);
		btnClose.addActionListener(this);
	
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	//#CM643004
	/**
	 * Shut Detailed inquiry screen. 
	 */
	public void closeWindow()
	{
    	this.dispose();
	}

	//#CM643005
	/**
	 * Execute the Button action. 
	 * @param e ActionEvent The class which stores information on the event. 
	 */
	public void actionPerformed(ActionEvent e)
	{
	    //#CM643006
	    // Shut a detailed list when you press end Button. 
		closeWindow();
	}
}
