// $Id: Id5121DataFile.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import java.io.IOException;
import java.util.Vector;

import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdDataFile;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 入荷検品出荷先一覧ファイル(ID5121.txt)を操作するためのクラスです。
 * 
 * <p>
 * <table border="1">
 * <CAPTION>ID5121のファイルの各行の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>出荷先コード</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>出荷先名</td>		<td>40 byte</td><td></td></tr>
 * <tr><td>CRLF</td>			<td>2 byte</td>	<td>CR + LF</td></tr>
 * </table>
 * </p>
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author $Author: mori $
 */
public class Id5121DataFile extends IdDataFile
{
	// Class fields----------------------------------------------------
	/**
	 * 出荷先コードのオフセットの定義
	 */
	private static final int OFF_CUSTOMER_CODE = 0;

	/**
	 * 出荷先名称のオフセットの定義
	 */
	private static final int OFF_CUSTOMER_NAME = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;

	/**
	 * 出荷先一覧ファイル名を表すフィールド
	 */
	static final String ANS_FILE_NAME = "ID5121.txt" ;

	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 * @param filename ファイル名
	 */
	public Id5121DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_CUSTOMER_NAME + LEN_CUSTOMER_NAME;
	}

	/**
	 * インスタンスを生成します。
	 */
	public Id5121DataFile()
	{
		lineLength = OFF_CUSTOMER_NAME + LEN_CUSTOMER_NAME;
	}

	// Public methods ------------------------------------------------
	/**
	 * データファイルを読み込み、WorkingInformationの配列に入れて返します。
	 * 
	 * @return WorkingInformationの配列(Entityクラス)
	 * @throws IOException		ファイルの入出力中にエラーが発生しました。
	 */
	public Entity[] getCompletionData() throws IOException
	{
		Vector v = new Vector();
		
		openReadOnly();
			
		for (next(); currentLine != null; next())
		{
			WorkingInformation workinfo = new WorkingInformation();
				
			workinfo.setCustomerCode(getCustomerCode());
			workinfo.setCustomerName1(getCustomerName());
				
			v.addElement(workinfo);
		}
			
		closeReadOnly();

		WorkingInformation[] data = new WorkingInformation[v.size()];
		v.copyInto(data);
		return data;
	}

	/**
	 * 引数で指定されたWorkingInformation配列のデータをファイル書き込みます。
	 * 
	 * @param	obj				ファイルに書き込むデータ（WorkingInformation配列）
	 * @throws IOException		ファイルの入出力中にエラーが発生しました。
	 */
	public void write(Entity[] obj) throws IOException
	{
		WorkingInformation[] workinfo = (WorkingInformation[])obj;
		
		openWritable();
			
		for (int i = 0; i < workinfo.length; i ++)
		{
			setCustomerCode(workinfo[i].getCustomerCode());
			setCustomerName(workinfo[i].getCustomerName1());
			
			writeln();
		}
		closeWritable();
	}

	/**
	 * データバッファから出荷先コードを取得します。
	 * @return		出荷先コード
	 */
	public String getCustomerCode()
	{
		return getColumn(OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE);
	}
	
	/**
	 * データバッファから出荷先名称を取得します。
	 * @return		出荷先名称
	 */
	public String getCustomerName()
	{
		return getColumn(OFF_CUSTOMER_NAME, LEN_CUSTOMER_NAME);
	}

	/**
	 * データバッファに出荷先コードをセットします。
	 * @param	customerCode	出荷先コード
	 */
	public void setCustomerCode(String customerCode)
	{
		setColumn(customerCode, OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE);
	}
	
	/**
	 * データバッファに出荷先名称をセットします。
	 * @param	customerName	出荷先名称
	 */
	public void setCustomerName(String customerName)
	{
		setColumn(customerName, OFF_CUSTOMER_NAME, LEN_CUSTOMER_NAME);
	}
}
//end of class
