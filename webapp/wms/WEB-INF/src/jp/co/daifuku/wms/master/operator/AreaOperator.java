//$Id: AreaOperator.java,v 1.2 2006/11/10 00:35:36 suresh Exp $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.operator;

import java.sql.Connection;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.entity.Area;

/**
 * Designer : mtakeuchi <BR>
 * Maker : mtakeuchi <BR>
 * <BR>
 * 指定されたエリアタイプのエリアNoを取得する。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>getAreaNo(Connection conn)</CODE>メソッド)<BR>
 * <DIR>
 *   ＜検索条件＞
 *   <DIR>
 *     エリア区分：areaType<BR>
 *   </DIR>
 *   ＜検索テーブル＞
 *   <DIR>
 *     DMAREA<BR>
 *   </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/01/16</TD><TD>mtakeuchi</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:35:36 $
 * @author  $Author: suresh $
 */
public class AreaOperator extends Object
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * AS/RS棚情報ハンドラ
	 */
	private AreaHandler wAreaHandler = null;

	/**
	 * AS/RS棚情報検索キー
	 */
	private AreaSearchKey wAreaKey = null;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/10 00:35:36 $");
	}

	// Constructors --------------------------------------------------
	public AreaOperator(Connection conn)
	{
		wAreaHandler = new AreaHandler(conn);
		wAreaKey = new AreaSearchKey();
	}

	/**
	 * エリアマスタを検索し、取得したエリアNoを配列で返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.結果セットからエリア情報を取得します。<BR>
	 *   2.エリア情報からデータを取得し<CODE>Area</CODE>にセットします。<BR>
	 *   3.取得したエリア情報からエリアNoを配列に格納します。<BR>
	 *   4.格納したエリアNoを返します。<BR>
	 * </DIR>
	 * 
	 * @param areaType 対象となるエリアのタイプ.
	 * @return <CODE>areaNo</CODE>
	 */
	public String[] getAreaNo(int[] areaType) throws ReadWriteException, ScheduleException
	{
		String[] strArea = new String[areaType.length];
		for (int i = 0; i < areaType.length; i++)
		{
			strArea[i] = Integer.toString(areaType[i]);
		}
		
		//キークリア
		wAreaKey.KeyClear();
		wAreaKey.setAreaType(strArea);
		
		// 件数を取得する
		if (wAreaHandler.count(wAreaKey) > 0)
		{
			Area[] area = (Area[]) wAreaHandler.find(wAreaKey);
			String[] areaNo = new String[area.length];	
			for (int i = 0; i < area.length; i++)
			{
				areaNo[i] = area[i].getAreaNo();
			}
			return areaNo;
			
		}
		else
		{
			throw new ScheduleException();
		}
	}
	
	/**
	 * エリアマスタを検索し、取得したエリア名称を返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.結果セットからエリア情報を取得します。<BR>
	 *   2.エリア情報からデータを取得し<CODE>Area</CODE>にセットします。<BR>
	 *   3.取得したエリア情報からエリア名称を格納します。<BR>
	 *   4.格納したエリア名称を返します。<BR>
	 * </DIR>
	 * 
	 * @param AreaNo 対象となるエリアNo.
	 * @return <CODE>areaNo</CODE>
	 */
	public String getAreaName(String AreaNo) throws ReadWriteException, ScheduleException
	{
		//キークリア
		wAreaKey.KeyClear();
		wAreaKey.setAreaNo(AreaNo);
		
		// 件数を取得する
		if (wAreaHandler.count(wAreaKey) > 0)
		{
			Area[] area = (Area[]) wAreaHandler.find(wAreaKey);
			String getAreaName = "";
			for (int i = 0; i < area.length; i++)
			{
			    getAreaName = area[i].getAreaName();
			}
			return getAreaName;
			
		}
		else
		{
			throw new ScheduleException();
		}
	}

	/**
	 * エリアマスタを検索し、取得したエリア種別を返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * @param AreaNo 対象となるエリアNo.
	 * @return <CODE>areaType</CODE>
	 */
	public String getAreaType(String AreaNo) throws ReadWriteException, ScheduleException
	{
		try
		{
			wAreaKey.KeyClear();
			wAreaKey.setAreaNo(AreaNo);
		
			if (wAreaHandler.count(wAreaKey) > 0)
			{
				Area area = (Area) wAreaHandler.findPrimary(wAreaKey);
				return area.getAreaType();
			
			}
			else
			{
				throw new ScheduleException();
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

}
