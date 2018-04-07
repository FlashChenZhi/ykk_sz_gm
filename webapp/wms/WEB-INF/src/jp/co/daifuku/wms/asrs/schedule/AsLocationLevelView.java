//#CM44582
//$Id: AsLocationLevelView.java,v 1.2 2006/10/30 01:04:33 suresh Exp $

//#CM44583
/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import jp.co.daifuku.common.text.DisplayText;

//#CM44584
/**
 * Designer : muneendra y<BR>
 * Maker 	: muneendra y<BR> 
 * <BR>
 * Class to hand over Location master information. 
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/24</TD><TD>muneendra y</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author muneendra y
 * @version $Revision 1.2 2005/09/14
 */
public class AsLocationLevelView 
{
	//#CM44585
	/**
	 * Level
	 */
	private String level;

	//#CM44586
	/**
	 * Bay
	 */
	private AsLocationBayView[] bayView;

	//#CM44587
	/**
	 * Return level. 
	 * @return Return level. 
	 */
	public String getLevel()
	{
		return level;
	}
	//#CM44588
	/**
	 * Set level. 
	 * @param level Set level. 
	 */
	public void setLevel(String level)
	{
		this.level = level;
	}
	//#CM44589
	/**
	 * Return the array of Location information. 
	 * @return Array of Location information
	 */
	public AsLocationBayView[] getBayView()
	{
		return bayView;
	}
	//#CM44590
	/**
	 * Set the array of Location information. 
	 * @param bayView The array of Location information.

	 */
	public void setBayView(AsLocationBayView[] bayView)
	{
		this.bayView = bayView;
	}
	
	//#CM44591
	/**
	 * Location Information
	 */
	public class AsLocationBayView
	{
		private String bankNo;
		private String levelNo;
		private String bayNo;
		private String location;
		private int status;
		private int accessflg;
		private String hardzone;
		private int presence;
		private int pstatus;
		private int empty;
		private String whnumber;
	
		//#CM44592
		/**
		 * Return Bank No.
		 * @return BankNo.
		 */
		public String getBankNo()
		{
			return bankNo;
		}
		//#CM44593
		/**
		 * Set Bank No.
		 * @param bankNo Bank No. to be set.
		 */
		public void setBankNo(String bankNo)
		{
			this.bankNo = bankNo;
		}
		//#CM44594
		/**
		 * Return Bay No.
		 * @return Bay No.
		 */
		public String getBayNo()
		{
			return bayNo;
		}
		//#CM44595
		/**
		 * Set Bay No.
		 * @param bayNo Bay No to be set.
		 */
		public void setBayNo(String bayNo)
		{
			this.bayNo = bayNo;
		}
		//#CM44596
		/**
		 * Return Level No.
		 * @return Level No.
		 */
		public String getLevelNo()
		{
			return levelNo;
		}
		//#CM44597
		/**
		 * Set Level No.
		 * @param levelNo Level No to be set.
		 */
		public void setLevelNo(String levelNo)
		{
			this.levelNo = levelNo;
		}
		//#CM44598
		/**
		 * Return Location.
		 * @return Location.
		 */
		public String getLocation()
		{
			return location;
		}
		//#CM44599
		/**
		 * Set Location.
		 * @param location Location to be set.
		 */
		public void setLocation(String location)
		{
			this.location = location;
		}
		//#CM44600
		/**
		 * Return Status.
		 * @return Status.
		 */
		public int getStatus()
		{
			return status;
		}
		//#CM44601
		/**
		 * Set Status.
		 * @param status Status to be set.
		 */
		public void setStatus(int status)
		{
			this.status = status;
		}
		//#CM44602
		/**
		 * Return Accessflg.
		 * @return Accessflg.
		 */
		public int getAccessFlg()
		{
			return accessflg;
		}
		//#CM44603
		/**
		 * Set Accessflg.
		 * @param Flg to be set.
		 */
		public void setAccessFlg(int Flg)
		{
			this.accessflg = Flg;
		}

		//#CM44604
		/**
		 * Return Hardzone.
		 * @return Hardzone.
		 */
		public String getHardzone() {
			return hardzone;
		}
		//#CM44605
		/**
		 * Set Hardzone.
		 * @param tmp Hardzone to be set.
		 */
		public void setHardzone(String tmp) {
			this.hardzone = tmp;
		}
		
		//#CM44606
		/**
		 * Return presence.
		 * @return presence.
		 */
		public int getPresence()
		{
			return presence;
		}
		
		//#CM44607
		/**
		 * Set presence.
		 * @param presence to be set.
		 */
		public void setPresence(int presence)
		{
			this.presence = presence;
		}
		
		//#CM44608
		/**
		 * Return pstatus.
		 * @return pstatus.
		 */
		public int getPStatus()
		{
			return pstatus;
		}

		//#CM44609
		/**
		 * Set pstatus.
		 * @param pstatus to be set.
		 */
		public void setPStatus(int pstatus)
		{
			this.pstatus = pstatus;
		}
		
		//#CM44610
		/**
		 * Return empty.
		 * @return empty.
		 */
		public int getEmpty()
		{
			return empty;
		}

		//#CM44611
		/**
		 * Set empty.
		 * @param empty to be set.
		 */
		public void setEmpty(int empty)
		{
			this.empty = empty;
		}

		//#CM44612
		/**
		 * Return whnumber.
		 * @return whnumber.
		 */
		public String getWhNumber()
		{
			return whnumber;
		}
		//#CM44613
		/**
		 * Set whnumber.
		 * @param whnumber whnumber to be set.
		 */
		public void setWhNumber(String whnumber)
		{
			this.bankNo = whnumber;
		}
   
		//#CM44614
		/**
		 * Return the character string for the tooltip. 
		 * @return Tooltip character string
		 */
		public String getTooltip()
		{
			StringBuffer sb = new StringBuffer();
			//#CM44615
			// Location display
			sb.append("\n");
			sb.append(DisplayText.formatLocation(location));
			sb.append("\n");
			return sb.toString();
		}
	}
}
//#CM44616
//end of class
