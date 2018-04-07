// $Id: Id5120DataFile.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
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
 * 入荷検品仕入先一覧ファイル(ID5120.txt)を操作するためのクラスです。
 * 
 * <p>
 * <table border="1">
 * <CAPTION>ID5120のファイルの各行の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>仕入先コード</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>仕入先名</td>		<td>40 byte</td><td></td></tr>
 * <tr><td>CRLF</td>			<td>2 byte</td>	<td>CR + LF</td></tr>
 * </table>
 * </p>
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author $Author: mori $
 */
public class Id5120DataFile extends IdDataFile
{
	// Class fields----------------------------------------------------
	/**
	 * 仕入先コードのオフセットの定義
	 */
	private static final int OFF_SUPPLIER_CODE = 0;

	/**
	 * 仕入先名称のオフセットの定義
	 */
	private static final int OFF_SUPPLIER_NAME = OFF_SUPPLIER_CODE + LEN_SUPPLIER_CODE;

	/**
	 * 仕入先一覧ファイル名を表すフィールド
	 */
	static final String ANS_FILE_NAME = "ID5120.txt" ;

	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 * @param filename ファイル名
	 */
	public Id5120DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_SUPPLIER_NAME + LEN_SUPPLIER_NAME;
	}

	/**
	 * インスタンスを生成します。
	 */
	public Id5120DataFile()
	{
		lineLength = OFF_SUPPLIER_NAME + LEN_SUPPLIER_NAME;
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
				
			workinfo.setSupplierCode(getSupplierCode());
			workinfo.setSupplierName1(getSupplierName());
				
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
			setSupplierCode(workinfo[i].getSupplierCode());
			setSupplierName(workinfo[i].getSupplierName1());
			
			writeln();
		}
		closeWritable();
	}

	/**
	 * データバッファから仕入先コードを取得します。
	 * @return		仕入先コード
	 */
	public String getSupplierCode()
	{
		return getColumn(OFF_SUPPLIER_CODE, LEN_SUPPLIER_CODE);
	}
	
	/**
	 * データバッファから仕入先名称を取得します。
	 * @return		仕入先名称
	 */
	public String getSupplierName()
	{
		return getColumn(OFF_SUPPLIER_NAME, LEN_SUPPLIER_NAME);
	}

	/**
	 * データバッファに仕入先コードをセットします。
	 * @param	supplierCode	仕入先コード
	 */
	public void setSupplierCode(String supplierCode)
	{
		setColumn(supplierCode, OFF_SUPPLIER_CODE, LEN_SUPPLIER_CODE);
	}
	
	/**
	 * データバッファに仕入先名称をセットします。
	 * @param	supplierName	仕入先名称
	 */
	public void setSupplierName(String supplierName)
	{
		setColumn(supplierName, OFF_SUPPLIER_NAME, LEN_SUPPLIER_NAME);
	}
}
//end of class
