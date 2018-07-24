// $Id: ReceiptClass.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import jp.co.daifuku.wms.base.rft.CompletionClass;

/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * RFT通信の電文での受付区分の値を定義します。
 * ここで定義されていない値はCompletionClassの値を使用します。
 * 
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public interface ReceiptClass extends CompletionClass
{
	/**
	 * 受付区分の分納を表すフィールド
	 */
	public static final String INSTALLMENT = "2";
}
//end of class
