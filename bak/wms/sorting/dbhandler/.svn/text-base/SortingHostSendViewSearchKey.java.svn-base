// $Id: SortingHostSendViewSearchKey.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $
package jp.co.daifuku.wms.sorting.dbhandler;

import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.HostSendViewSearchKey;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * HostSend表を検索するためのキーをセットするクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class SortingHostSendViewSearchKey extends HostSendViewSearchKey
{
	// Class fields --------------------------------------------------
	// テーブル名
	private static final String TABLE = "DVHOSTSENDVIEW"; 

	// ここに結合される可能性のあるカラムを定義します。
	private static final String WORKDATE					= "WORK_DATE";
	private static final String JOBNO					= "JOB_NO";
	private static final String JOBTYPE					= "JOB_TYPE";
	private static final String COLLECTJOBNO				= "COLLECT_JOB_NO";
	private static final String STATUSFLAG				= "STATUS_FLAG";
	private static final String BEGINNINGFLAG			= "BEGINNING_FLAG";
	private static final String PLANUKEY					= "PLAN_UKEY";
	private static final String STOCKID					= "STOCK_ID";
	private static final String AREANO					= "AREA_NO";
	private static final String LOCATIONNO				= "LOCATION_NO";
	private static final String PLANDATE					= "PLAN_DATE";
	private static final String CONSIGNORCODE			= "CONSIGNOR_CODE";
	private static final String CONSIGNORNAME			= "CONSIGNOR_NAME";
	private static final String SUPPLIERCODE				= "SUPPLIER_CODE";
	private static final String SUPPLIERNAME1			= "SUPPLIER_NAME1";
	private static final String INSTOCKTICKETNO			= "INSTOCK_TICKET_NO";
	private static final String INSTOCKLINENO			= "INSTOCK_LINE_NO";
	private static final String CUSTOMERCODE				= "CUSTOMER_CODE";
	private static final String CUSTOMERNAME1			= "CUSTOMER_NAME1";
	private static final String SHIPPINGTICKETNO			= "SHIPPING_TICKET_NO";
	private static final String SHIPPINGLINENO			= "SHIPPING_LINE_NO";
	private static final String ITEMCODE					= "ITEM_CODE";
	private static final String ITEMNAME1				= "ITEM_NAME1";
	private static final String HOSTPLANQTY				= "HOST_PLAN_QTY";
	private static final String PLANQTY					= "PLAN_QTY";
	private static final String PLANENABLEQTY			= "PLAN_ENABLE_QTY";
	private static final String RESULTQTY				= "RESULT_QTY";
	private static final String SHORTAGECNT				= "SHORTAGE_CNT";
	private static final String PENDINGQTY				= "PENDING_QTY";
	private static final String ENTERINGQTY				= "ENTERING_QTY";
	private static final String BUNDLEENTERINGQTY		= "BUNDLE_ENTERING_QTY";
	private static final String CASEPIECEFLAG			= "CASE_PIECE_FLAG";
	private static final String WORKFORMFLAG				= "WORK_FORM_FLAG";
	private static final String ITF						= "ITF";
	private static final String BUNDLEITF				= "BUNDLE_ITF";
	private static final String TCDCFLAG					= "TCDC_FLAG";
	private static final String PLANINFORMATION			= "PLAN_INFORMATION";
	private static final String ORDERNO					= "ORDER_NO";
	private static final String ORDERINGDATE				= "ORDERING_DATE";
	private static final String USEBYDATE				= "USE_BY_DATE";
	private static final String BATCHNO					= "BATCH_NO";
	private static final String REGISTDATE				= "REGIST_DATE";
	private static final String LASTUPDATEDATE			= "LAST_UPDATE_DATE";

	// Class variables -----------------------------------------------

	/**
	 * テーブル結合のテーブル名 保存用
	 */
	protected String JoinTable = "JoinTable";

	/**
	 * テーブル結合のSelect句 保存用配列
	 */
	protected Vector JoinColumnsVec = new Vector() ;

	/**
	 * テーブル結合のWhere句 保存用配列
	 */
	protected Vector JoinWhereVec = new Vector() ;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョン
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $") ;
	}

	// Constructors --------------------------------------------------
	/**
	 * コンストラクタのコメント
	 * @param ap  Comment for parameter
	 */
	public SortingHostSendViewSearchKey()
	{
		super();
	}

	// Public methods ------------------------------------------------

	/**
	 * テーブル結合のカラムをセット
	 */
	public void setPlanUkeyJoinCollect()
	{
		setJoinColumns(PLANUKEY);
	}

	/**
	 * テーブル結合のテーブル名をセット
	 */
	public void setJoinTable(String table)
	{
		JoinTable = table;
	}

	/**
	 * テーブル結合のテーブル名を取得します。
	 */
	public String getJoinTable()
	{
		return JoinTable;
	}

	/**
	 * 一意キーの結合値をセット
	 */
	public void setPlanUkeyJoin()
	{
		setJoinWhere(PLANUKEY, PLANUKEY);
	}

	/**
	 * 設定されたキーよりSQLのSELECT句のカラムを生成を行います。
	 * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
	 */
	public String ReferenceJoinColumns() throws ReadWriteException
	{
		StringBuffer stbf = new StringBuffer(512) ;
		boolean existFlg = false;

		JoinKey[] columns = new JoinKey[JoinColumnsVec.size()];
		JoinColumnsVec.copyInto(columns);

		for (int i = 0 ; i < columns.length ; i++)
		{
			if(existFlg)
			{
				stbf.append(", ");
			}
			stbf.append(JoinTable + "." + columns[i].getColumn1());
			existFlg = true;
		}

		if(!existFlg)
		{
			return null;
		}

		return stbf.toString().substring(0, stbf.length());
	}

	/**
	 * 設定されたキーよりSQLのWHERE句の結合条件を生成を行います。
	 * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
	 */
	public String ReferenceJoinWhere() throws ReadWriteException
	{
		StringBuffer stbf = new StringBuffer(512) ;
		boolean existFlg = false;

		JoinKey[] columns = new JoinKey[JoinWhereVec.size()];
		JoinWhereVec.copyInto(columns);

		for (int i = 0 ; i < columns.length ; i++)
		{
			if(existFlg)
			{
				stbf.append(" AND ");
			}
			stbf.append(TABLE+"."+columns[i].getColumn1()+" = "+ JoinTable + "."+columns[i].getColumn2());
			existFlg = true;
		}

		if(!existFlg)
		{
			return null;
		}

		return stbf.toString().substring(0, stbf.length());
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * 結合したテーブルのSELECT句にセットするカラムのセットを行います。
	 * @param key SELECT句にセットする文字列
	 */
	private void setJoinColumns(String key)
	{
		JoinKey jkey = new JoinKey();
		jkey.setColumn1(key);

		JoinColumnsVec.addElement(jkey);
	}

	/**
	 * テーブル結合するWhere句のセットを行います。
	 */
	private void setJoinWhere(String key1, String key2)
	{
		JoinKey jkey = new JoinKey();
		jkey.setColumn1(key1);
		jkey.setColumn2(key2);

		JoinWhereVec.addElement(jkey);
	}

	// Inner Class ---------------------------------------------------
	/**
	 * 結合のカラムを保持する内部クラスです。
	 */
	protected class JoinKey
	{
		private String  Column1 ;    // カラム
		private String  Column2 ;    // カラム

		/**
		 * カラムをセットします。
		 */
		protected void setColumn1(String column)
		{
			Column1 = column ;
		}
		/**
		 * カラムを取得します。
		 */
		protected String getColumn1()
		{
			return Column1 ;
		}

		/**
		 * カラムをセットします。
		 */
		protected void setColumn2(String column)
		{
			Column2 = column ;
		}
		/**
		 * カラムを取得します。
		 */
		protected String getColumn2()
		{
			return Column2 ;
		}
	}

}
//end of class

