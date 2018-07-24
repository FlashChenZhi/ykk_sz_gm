// $Id: LvTextField.java,v 1.2 2006/11/07 05:55:05 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643246
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//#CM643247
/**
 * Designer : T.Konishi <BR>
 * Maker : T.Konishi  <BR>
 * <BR>
 * Text field Component used by the log viewer. 
 * <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:55:05 $
 * @author  $Author: suresh $
 */

public class LvTextField extends JTextField
{
	int maxLength = -1;

	//#CM643248
	/**
	 * Default Constructor
	 */
	public LvTextField()
	{
		super();
	}

	//#CM643249
	/**
	 * Constructor
	 * 
	 * @param maxLength		Number of input maximum digits
	 */
	public LvTextField(int maxLength)
	{
		super(maxLength);
		this.maxLength = maxLength;
		initialize();
	}

	//#CM643250
	/**
	 * Initialize text field Component. 
	 */
	public void initialize()
	{
		this.setDocument(new LimitedDocument(maxLength));
	}

	//#CM643251
	/**
	 * Define the Rft Title machine No input column. 
	 * @param columns Title machine NO
	 */
	public void setColumns(int columns)
	{
		super.setColumns(columns);
		maxLength = columns;
		initialize();
	}

	//#CM643252
	/**
	 * Innerclass.
	 */
	class LimitedDocument extends PlainDocument
	{
		int limit;

		//#CM643253
		/**
		 * Constructor
		 */
		LimitedDocument(int limit)
		{
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet a)
		{
			if (limit >= 0 && getLength() >= limit)
			{
				return;
			}
			try
			{
				super.insertString(offset, str, a);
			}
			catch (BadLocationException e)
			{
				System.out.println(e);
			}
		}
	}
}
