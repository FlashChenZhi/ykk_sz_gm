package jp.co.daifuku.wms.idm.schedule;
/*
 * Created on 2005/09/14
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
/**
 * Designer : muneendra y<BR>
 * Maker 	: muneendra y<BR> 
 * <BR>
 * ロケーションの設定、取得を行なうクラスです。
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>${date}</TD><TD>muneendra y</TD><TD></TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author muneendra y
 * @version $Revision 1.2 2005/09/14
 */
public class IdmLocationLevelView
{
	/**
	 * レベル
	 */
	private String level;
    
	/**
	 * ロケーションリスト
	 */
	private IdmLocationBayView[] bayView;
    
	/**
	 * レベルを取得します。
	 * @return レベル
	 */
	public String getLevel() 
	{
		return level;
	}
	
	/**
	 * レベルをセットします。
	 * @param level セットするレベル
	 */
	public void setLevel(String level)
	{
		this.level = level;
	}
	
	/**
	 * ロケーションリストを取得します。
	 * @return ロケーションリスト
	 */
	public IdmLocationBayView[] getBayView() 
	{
		return bayView;
	}
	/**
	 * ロケーションリストをセットします。
	 * @param bayView セットするロケーションリスト
	 */
	public void setBayView(IdmLocationBayView[] bayView) 
	{
		this.bayView = bayView;
	}
	
	/**
	 * インナークラスです。
	 */
	public class IdmLocationBayView
	{
		private String bankNo;
		private String levelNo;
		private String bayNo;
		private String location;
		private String status;
		private String accessflg;
		private String hardzone;
		
		/**
		 * バンクNo.を取得します。
		 * @return バンクNo.
		 */
		public String getBankNo() {
			return bankNo;
		}
		
		/**
		 * バンクNo.を設定します。
		 * @param bankNo セットするバンクNo.
		 */
		public void setBankNo(String bankNo)
		{
			this.bankNo = bankNo;
		}
		
		/**
		 * ベイNo.を取得します。
		 * @return ベイNo.
		 */
		public String getBayNo() {
			return bayNo;
		}
		
		/**
		 * ベイNo.をセットします。
		 * @param セットするベイNo.
		 */
		public void setBayNo(String bayNo)
		{
			this.bayNo = bayNo;
		}
		
		/**
		 * レベルNo.を取得します。
		 * @return レベルNo.
		 */
		public String getLevelNo()
		{
			return levelNo;
		}
		
		/**
		 * レベルNo.をセットします。
		 * @param levelNo セットするレベルNo.
		 */
		public void setLevelNo(String levelNo)
		{
			this.levelNo = levelNo;
		}
		
		/**
		 * ロケーションを取得します。
		 * @return ロケーション
		 */
		public String getLocation()
		{
			return location;
		}
		
		/**
		 * ロケーションをセットします。
		 * @param location セットするロケーション
		 */
		public void setLocation(String location)
		{
			this.location = location;
		}
		
		/**
		 * 状態を取得します。
		 * @return 状態
		 */
		public String getStatus()
		{
			return status;
		}
		
		/**
		 * 状態をセットします。
		 * @param status セットする状態
		 */
		public void setStatus(String status)
		{
			this.status = status;
		}
		
		/**
		 * アクセスフラグをセットします。
		 * @param Flg セットするアクセスフラグ
		 */
		public void setAccessFlg(String Flg)
		{
			this.accessflg = Flg;
		}
		
		/**
		 * アクセスフラグを取得します。
		 * @return アクセスフラグ
		 */
		public String getAccessFlg()
		{
			return accessflg;
		}
		
		/**
		 * ハードゾーンを取得します。
		 * @return ハードゾーン
		 */
		public String getHardzone() 
		{
			return hardzone;
		}
		
		/**
		 * ハードゾーンをセットします
		 * @param tmp ハードゾーン
		 */
		public void setHardzone(String tmp)
		{
			this.hardzone = tmp;
		}
	    
		/**
		 * ツールチップ用文字列を取得します。
		 * @return ツールチップ用文字列
		 */
		public String getTooltip()
		{
			StringBuffer sb = new StringBuffer();
			// 棚表示
			sb.append("\n");
			sb.append(location);
			sb.append("\n");
			return sb.toString();
		}
	}
		
}
