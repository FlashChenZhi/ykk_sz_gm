// $Id: Id5920DataFile.java,v 1.1.1.1 2006/08/17 09:34:10 mori Exp $
package jp.co.daifuku.wms.idm.rft;

import java.io.IOException;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdDataFile;

/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ID5920で送受信するデータファイルを操作するためのクラスです。
 * 
 * <p>
 * <table border="1">
 * <CAPTION>ID5920のファイルの各行の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>ロケーション</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>CRLF</td>			<td>2 byte</td>	<td>CR + LF</td></tr>
 * </table>
 * </p>
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author $Author: mori $
 */
public class Id5920DataFile extends IdDataFile
{
	// Class fields----------------------------------------------------

	/**
	 * ロケーションNo.のオフセットの定義
	 */
	private static final int OFF_LOCATION = 0;

	/**
	 * 出荷先一覧ファイル名を表すフィールド
	 */
	static final String ANS_FILE_NAME = "ID5920.txt" ;

	/**
	 * インスタンスを生成します。
	 * @param filename ファイル名
	 */
	public Id5920DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_LOCATION + LEN_LOCATION;
	}

	/**
	 * インスタンスを生成します。
	 */
	public Id5920DataFile()
	{
		lineLength = OFF_LOCATION + LEN_LOCATION;

	}
	
	/** (非 Javadoc)
	 * @see jp.co.daifuku.wms.base.rft.IdDataFile#write(jp.co.daifuku.wms.base.common.Entity[])
	 */
	public void write(Entity[] obj) throws IOException, InvalidStatusException {
	}
	
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
				
			workinfo.setLocationNo(getLocationNo());
				
			v.addElement(workinfo);
		}
			
		closeReadOnly();

		WorkingInformation[] data = new WorkingInformation[v.size()];
		v.copyInto(data);
		return data;
	}

	/**
	 * 引数で指定されたLocation配列のデータをファイル書き込みます。
	 * 
	 * @param	locate				ファイルに書き込むデータ（Location配列）
	 * @param	maxRecord			ファイルに書き込むデータの上限数（行数）
	 * @return	実際に書き込んだ行数
	 * @throws IOException		ファイルの入出力中にエラーが発生しました。
	 */
	public int write(String[] locate,int maxRecord) throws IOException
	{
		int counter = 0;
		openWritable();
			
		for (int i = 0; i < locate.length; i ++)
		{
			if (i < maxRecord)
			{
				setLocationNo(locate[i]);
				writeln();
				counter++;
			}
			else
			{
				break;
			}
		}
		closeWritable();
		return counter;
	}

	/**
	 * データバッファからロケーションNo.を取得します。
	 * @return		ロケーションNo.
	 */
	public String getLocationNo()
	{
		return getColumn(OFF_LOCATION, LEN_LOCATION);
	}
	/**
	 * データバッファにロケーションNo.をセットします。
	 * @param	location	ロケーションNo.
	 */
	public void setLocationNo(String location)
	{
		setColumn(location, OFF_LOCATION, LEN_LOCATION);
	}
}
//end of class
