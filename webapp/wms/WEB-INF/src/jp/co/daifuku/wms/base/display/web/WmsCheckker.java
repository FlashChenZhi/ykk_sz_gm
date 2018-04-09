// $Id: WmsCheckker.java,v 1.3 2006/11/07 06:53:03 suresh Exp $
//#CM664834
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web;

import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.ui.control.CheckBox;
import jp.co.daifuku.bluedog.ui.control.FreeTextBox;
import jp.co.daifuku.bluedog.ui.control.Label;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.NumberTextBox;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.ui.control.TextArea;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;

//#CM664835
/**
 * The class which does the input check of eWareNavi screen Input values. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/27</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/07 06:53:03 $
 * @author  $Author: suresh $
 */
public class WmsCheckker
{
	//#CM664836
	// Class fields --------------------------------------------------

	//#CM664837
	// Class variables -----------------------------------------------
	//#CM664838
	/**
	 * For Error message maintenance
	 */
	private String wMessage = null;

	//#CM664839
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM664840
	// Class method --------------------------------------------------

	//#CM664841
	// Constructors --------------------------------------------------

	//#CM664842
	// Public methods ------------------------------------------------

	//#CM664843
	/**
	 * Do Mandatory input check of Consignor code. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Consignor code is blank or null.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Consignor code <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Entered <BR>
	 *      False if blank <BR>
	 * 		False if restricted character "*" is input. <BR>
	 *    </DIR>
	 * </DIR>
	 * @param consignorcode Consignor code
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Entered False: The blank or restricted character is entered.
	 */
	public static boolean consignorCheck(String consignorcode, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM664844
		// Consignor code must be entered.
		if (StringUtil.isBlank(consignorcode))
		{
			//#CM664845
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664846
			// MSG-W0035 = Enter the Consignor code.
			label.setText(DisplayText.getText("MSG-W0035"));

			//#CM664847
			// Value set to Pager
			//#CM664848
			// Maximum qty
			pu.setMax(0);
			//#CM664849
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664850
			// Start position 
			pu.setIndex(0);
			//#CM664851
			// Maximum qty
			pd.setMax(0);
			//#CM664852
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664853
			// Start position 
			pd.setIndex(0);

			return false;
		}
		//#CM664854
		// The restricted character must not be entered in Consignor code.
		if (consignorcode.indexOf("*") >= 0)
		{
			//#CM664855
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664856
			// MSG-W0046=The character which cannot be used as a search condition is included. 
			label.setText(DisplayText.getText("MSG-W0046"));

			//#CM664857
			// Value set to Pager
			//#CM664858
			// Maximum qty
			pu.setMax(0);
			//#CM664859
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664860
			// Start position 
			pu.setIndex(0);
			//#CM664861
			// Maximum qty
			pd.setMax(0);
			//#CM664862
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664863
			// Start position 
			pd.setIndex(0);

			return false;
		}
		
		return true;
	}
	
	//#CM664864
	/**
	 * Do the input check of Receiving plan date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Receiving plan date is not entered.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Receiving plan date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Receiving plan date is entered. <BR>
	 *      False if Receiving plan date is not entered. <BR>
	 * 		False if restricted character is entered.<BR>
	 *    </DIR>
	 * </DIR>
	 * @param instockplandate Receiving plan date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Entered False: The blank or restricted character is entered.
	 */
	public static boolean instockplandateCheck(String instockplandate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		if (StringUtil.isBlank(instockplandate))
		{
			//#CM664865
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664866
			// MSG-W0047=Enter the Receiving plan date. 
			label.setText(DisplayText.getText("MSG-W0057"));

			//#CM664867
			// Value set to Pager
			//#CM664868
			// Maximum qty
			pu.setMax(0);
			//#CM664869
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664870
			// Start position 
			pu.setIndex(0);
			//#CM664871
			// Maximum qty
			pd.setMax(0);
			//#CM664872
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664873
			// Start position 
			pd.setIndex(0);

			return false;
		}
		//#CM664874
		// The restricted character must not be entered in Receiving plan date.
		if (instockplandate.indexOf("*") >= 0)
		{
			//#CM664875
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664876
			// MSG-W0046=The character which cannot be used as a search condition is included. 
			label.setText(DisplayText.getText("MSG-W0046"));

			//#CM664877
			// Value set to Pager
			//#CM664878
			// Maximum qty
			pu.setMax(0);
			//#CM664879
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664880
			// Start position 
			pu.setIndex(0);
			//#CM664881
			// Maximum qty
			pd.setMax(0);
			//#CM664882
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664883
			// Start position 
			pd.setIndex(0);

			return false;
		}		
		return true;
	}
	//#CM664884
	/**
	 * Do the input check of Storage date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Sorting plan date is not entered.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Storage date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Storage date is entered. <BR>
	 *      False if Storage date is not entered. <BR>
	 * 		False if restricted character is entered.<BR>
	 *    </DIR>
	 * </DIR>
	 * @param storagedate Storage date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Entered False: The blank or restricted character is entered.
	 */
	public static boolean storagedateCheck(String storagedate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		if (StringUtil.isBlank(storagedate))
		{
			//#CM664885
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664886
			// MSG-W0042=Enter the Storage date. 
			label.setText(DisplayText.getText("MSG-W0042"));

			//#CM664887
			// Value set to Pager
			//#CM664888
			// Maximum qty
			pu.setMax(0);
			//#CM664889
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664890
			// Start position 
			pu.setIndex(0);
			//#CM664891
			// Maximum qty
			pd.setMax(0);
			//#CM664892
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664893
			// Start position 
			pd.setIndex(0);

			return false;
		}
		//#CM664894
		// The restricted character must not be entered in Storage date.
		if (storagedate.indexOf("*") >= 0)
		{
			//#CM664895
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664896
			// MSG-W0046=The character which cannot be used as a search condition is included. 
			label.setText(DisplayText.getText("MSG-W0046"));

			//#CM664897
			// Value set to Pager
			//#CM664898
			// Maximum qty
			pu.setMax(0);
			//#CM664899
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664900
			// Start position 
			pu.setIndex(0);
			//#CM664901
			// Maximum qty
			pd.setMax(0);
			//#CM664902
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664903
			// Start position 
			pd.setIndex(0);

			return false;
		}		
		return true;
	}

	//#CM664904
	/**
	 * Do the input check of Picking plan date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Picking plan date is not entered.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Picking plan date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Picking plan date is entered. <BR>
	 *      False if Picking plan date is not entered. <BR>
	 * 		False if restricted character is entered.<BR>
	 *    </DIR>
	 * </DIR>
	 * @param retrievalplandate Picking plan date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Entered False: The blank or restricted character is entered.
	 */
	public static boolean retrievalplandateCheck(String retrievalplandate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		if (StringUtil.isBlank(retrievalplandate))
		{
			//#CM664905
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664906
			// MSG-W0047=Enter the Picking plan date.
			label.setText(DisplayText.getText("MSG-W0047"));

			//#CM664907
			// Value set to Pager
			//#CM664908
			// Maximum qty
			pu.setMax(0);
			//#CM664909
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664910
			// Start position 
			pu.setIndex(0);
			//#CM664911
			// Maximum qty
			pd.setMax(0);
			//#CM664912
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664913
			// Start position 
			pd.setIndex(0);

			return false;
		}
		//#CM664914
		// The restricted character must not be entered in Picking plan date.
		if (retrievalplandate.indexOf("*") >= 0)
		{
			//#CM664915
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664916
			// MSG-W0046=The character which cannot be used as a search condition is included. 
			label.setText(DisplayText.getText("MSG-W0046"));

			//#CM664917
			// Value set to Pager
			//#CM664918
			// Maximum qty
			pu.setMax(0);
			//#CM664919
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664920
			// Start position 
			pu.setIndex(0);
			//#CM664921
			// Maximum qty
			pd.setMax(0);
			//#CM664922
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664923
			// Start position 
			pd.setIndex(0);

			return false;
		}		
		return true;
	}
	
	//#CM664924
	/**
	 * Do the input check of Sorting date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Sorting plan date is not entered.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Sorting date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Sorting date is entered. <BR>
	 *      False if Sorting date is not entered. <BR>
	 * 		False if restricted character is entered.<BR>
	 *    </DIR>
	 * </DIR>
	 * @param sortingdate Sorting date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Entered False: The blank or restricted character is entered.
	 */
	public static boolean sortingdateCheck(String sortingdate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		if (StringUtil.isBlank(sortingdate))
		{
			//#CM664925
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664926
			// MSG-W0042=Enter the Sorting date.
			label.setText(DisplayText.getText("MSG-W0052"));

			//#CM664927
			// Value set to Pager
			//#CM664928
			// Maximum qty
			pu.setMax(0);
			//#CM664929
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664930
			// Start position 
			pu.setIndex(0);
			//#CM664931
			// Maximum qty
			pd.setMax(0);
			//#CM664932
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664933
			// Start position 
			pd.setIndex(0);

			return false;
		}
		//#CM664934
		// The restricted character must not be entered in Sorting date.
		if (sortingdate.indexOf("*") >= 0)
		{
			//#CM664935
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664936
			// MSG-W0046=The character which cannot be used as a search condition is included. 
			label.setText(DisplayText.getText("MSG-W0046"));

			//#CM664937
			// Value set to Pager
			//#CM664938
			// Maximum qty
			pu.setMax(0);
			//#CM664939
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664940
			// Start position 
			pu.setIndex(0);
			//#CM664941
			// Maximum qty
			pd.setMax(0);
			//#CM664942
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664943
			// Start position 
			pd.setIndex(0);

			return false;
		}		
		return true;
	}
	
	//#CM664944
	/**
	 * Do the input check of Sorting plan date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Sorting plan date is not entered.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Sorting plan date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Sorting plan date is entered. <BR>
	 *      False if Sorting plan date is not entered. <BR>
	 * 		False if restricted character is entered.<BR>
	 *    </DIR>
	 * </DIR>
	 * @param sortingplandate Sorting plan date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Entered False: The blank or restricted character is entered.
	 */
	public static boolean sortingplandateCheck(String sortingplandate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		if (StringUtil.isBlank(sortingplandate))
		{
			//#CM664945
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664946
			// MSG-W0042=Enter the Sorting plan date.
			label.setText(DisplayText.getText("MSG-W0053"));

			//#CM664947
			// Value set to Pager
			//#CM664948
			// Maximum qty
			pu.setMax(0);
			//#CM664949
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664950
			// Start position 
			pu.setIndex(0);
			//#CM664951
			// Maximum qty
			pd.setMax(0);
			//#CM664952
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664953
			// Start position 
			pd.setIndex(0);

			return false;
		}
		//#CM664954
		// The restricted character must not be entered in Sorting plan date. 
		if (sortingplandate.indexOf("*") >= 0)
		{
			//#CM664955
			// Conceal the header. 
			listcell.setVisible(false);
			//#CM664956
			// MSG-W0046=The character which cannot be used as a search condition is included. 
			label.setText(DisplayText.getText("MSG-W0046"));

			//#CM664957
			// Value set to Pager
			//#CM664958
			// Maximum qty
			pu.setMax(0);
			//#CM664959
			// 1PageDisplay qty
			pu.setPage(0);
			//#CM664960
			// Start position 
			pu.setIndex(0);
			//#CM664961
			// Maximum qty
			pd.setMax(0);
			//#CM664962
			// 1PageDisplay qty
			pd.setPage(0);
			//#CM664963
			// Start position 
			pd.setIndex(0);

			return false;
		}		
		return true;
	}

	//#CM664964
	/**
	 * Check the range of Start Shipping plan date and End Shipping plan date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Start Shipping plan date is after End Shipping plan date. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Shipping plan date <BR>
	 *      End Shipping plan date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Start Shipping plan date is before End Shipping plan date.  <BR>
	 *      False if Start Shipping plan date is after End Shipping plan date.  <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startplandate Start Shipping plan date
	 * @param endplandate End Shipping plan date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Start Shipping plan date is before End Shipping plan date.  False : Start Shipping plan date is after End Shipping plan date. 
	 */
	public static boolean rangePlanDateCheck(String startplandate, String endplandate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM664965
		// Start Shipping plan date must be before End Shipping plan date. 
		if (!StringUtil.isBlank(endplandate))
		{
			if (startplandate.compareTo(endplandate) > 0)
			{
				//#CM664966
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM664967
				// MSG-W0036=Make Start Shipping plan date before End Shipping plan date. 
				label.setText(DisplayText.getText("MSG-W0036"));

				//#CM664968
				// Value set to Pager
				//#CM664969
				// Maximum qty
				pu.setMax(0);
				//#CM664970
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM664971
				// Start position 
				pu.setIndex(0);
				//#CM664972
				// Maximum qty
				pd.setMax(0);
				//#CM664973
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM664974
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}

	//#CM664975
	/**
	 * Check the range of Start Shipping date and End Shipping Date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Start Shipping date is after End Shipping Date. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Shipping date <BR>
	 *      End Shipping Date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Start Shipping date is before End Shipping Date.  <BR>
	 *      False if Start Shipping date is after End Shipping Date.  <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startdate Start Shipping date
	 * @param enddate End Shipping Date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Start Shipping date is before End Shipping Date.  False : Start Shipping date is after End Shipping Date. 
	 */
	public static boolean rangeDateCheck(String startdate, String enddate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM664976
		// Start Shipping date must be before End Shipping Date. 
		if (!StringUtil.isBlank(enddate))
		{
			if (startdate.compareTo(enddate) > 0)
			{
				//#CM664977
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM664978
				// MSG-W0037=Make Start Shipping date before End Shipping Date. 
				label.setText(DisplayText.getText("MSG-W0037"));

				//#CM664979
				// Value set to Pager
				//#CM664980
				// Maximum qty
				pu.setMax(0);
				//#CM664981
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM664982
				// Start position 
				pu.setIndex(0);
				//#CM664983
				// Maximum qty
				pd.setMax(0);
				//#CM664984
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM664985
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}

	//#CM664986
	/**
	 * Check the range of Start Work date and End Work date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Start Work date is after End Work date. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Work date <BR>
	 *      End Work date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Start Work date is before End Work date.  <BR>
	 *      False if Start Work date is after End Work date.  <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startworkdate Start Work date
	 * @param endworkdate End Work date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Start Work date is before End Work date.  False : Start Work date is after End Work date. 
	 */
	public static boolean rangeWorkDateCheck(String startworkdate, String endworkdate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM664987
		// Start Work date must be before End Work date. 
		if (!StringUtil.isBlank(endworkdate))
		{
			if (startworkdate.compareTo(endworkdate) > 0)
			{
				//#CM664988
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM664989
				// MSG-W0034=Make Start Work date before End Work date. 
				label.setText(DisplayText.getText("MSG-W0034"));

				//#CM664990
				// Value set to Pager
				//#CM664991
				// Maximum qty
				pu.setMax(0);
				//#CM664992
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM664993
				// Start position 
				pu.setIndex(0);
				//#CM664994
				// Maximum qty
				pd.setMax(0);
				//#CM664995
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM664996
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}

	//#CM664997
	/**
	 * Check the range of Start Picking plan date and End Picking plan date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Start Picking plan date is after End Picking plan date. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Picking plan date <BR>
	 *      End Picking plan date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Start Picking plan date is before End Picking plan date.  <BR>
	 *      False if Start Picking plan date is after End Picking plan date.  <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startretrievalplandate Start Picking plan date
	 * @param endretrievalplandate End Picking plan date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Start Picking plan date is before End Picking plan date.  False : Start Picking plan date is after End Picking plan date. 
	 */
	public static boolean rangeRetrievalPlanDateCheck(String startretrievalplandate, String endretrievalplandate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM664998
		// Start Picking plan date must be before End Picking plan date. 
		if (!StringUtil.isBlank(endretrievalplandate))
		{
			if (startretrievalplandate.compareTo(endretrievalplandate) > 0)
			{
				//#CM664999
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM665000
				// MSG-W0048=Make Start Picking plan date before End Picking plan date. 
				label.setText(DisplayText.getText("MSG-W0048"));

				//#CM665001
				// Value set to Pager
				//#CM665002
				// Maximum qty
				pu.setMax(0);
				//#CM665003
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM665004
				// Start position 
				pu.setIndex(0);
				//#CM665005
				// Maximum qty
				pd.setMax(0);
				//#CM665006
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM665007
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}

	//#CM665008
	/**
	 * Check the range of Start Picking date and End Picking date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Start Picking date is after End Picking date. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Picking date <BR>
	 *      End Picking date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Start Picking date is before End Picking date.  <BR>
	 *      False if Start Picking date is after End Picking date.  <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startretrievaldate Start Picking date
	 * @param endretrievaldate End Picking date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True:Start Picking date is before End Picking date.  False:Start Picking date is after End Picking date. 
	 */
	public static boolean rangeRetrievalDateCheck(String startretrievaldate, String endretrievaldate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM665009
		// Start Picking date must be before End Picking date. 
		if (!StringUtil.isBlank(endretrievaldate))
		{
			if (startretrievaldate.compareTo(endretrievaldate) > 0)
			{
				//#CM665010
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM665011
				// MSG-W0049=Make Start Picking date Date before End Picking date. 
				label.setText(DisplayText.getText("MSG-W0049"));

				//#CM665012
				// Value set to Pager
				//#CM665013
				// Maximum qty
				pu.setMax(0);
				//#CM665014
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM665015
				// Start position 
				pu.setIndex(0);
				//#CM665016
				// Maximum qty
				pd.setMax(0);
				//#CM665017
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM665018
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}

	//#CM665019
	/**
	 * Check the restricted character. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false when [*] is input to Parameter. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Object text box of restricted character check <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		False if restricted character "*" is input. <BR>
	 *    </DIR>
	 * </DIR>
	 * @param textbox Character string to be checked
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True: Restricted character "*" is not entered.  False: Restricted character "*" is entered. 
	 */
	public static boolean charCheck(String textbox, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		if (!StringUtil.isBlank(textbox))
		{
			//#CM665020
			// The restricted character must not be entered. 
			if (textbox.indexOf("*") >= 0)
			{
				//#CM665021
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM665022
				// MSG-W0046=The character which cannot be used as a search condition is included. 
				label.setText(DisplayText.getText("MSG-W0046"));
	
				//#CM665023
				// Value set to Pager
				//#CM665024
				// Maximum qty
				pu.setMax(0);
				//#CM665025
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM665026
				// Start position 
				pu.setIndex(0);
				//#CM665027
				// Maximum qty
				pd.setMax(0);
				//#CM665028
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM665029
				// Start position 
				pd.setIndex(0);
	
				return false;
			}
		}
		
		return true;
	}

	//#CM665030
	/**
	 * Check the range of Start Location and End Location. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false when Start Location is larger than End Location. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Location <BR>
	 *      End Location <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Start Location is smaller than End Location or same. <BR>
	 *      False if Start Location is larger than End Location. <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startlocation Start Location
	 * @param endlocation End Location
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True:Start Location is smaller or same as End Location.  False:Start Location is larger than End Location. 
	 */
	public static boolean rangeLocationCheck(String startlocation, String endlocation, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM665031
		// Start Location must be smaller value than End Location. 
		if (!StringUtil.isBlank(endlocation))
		{
			if (startlocation.compareTo(endlocation) > 0)
			{
				//#CM665032
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM665033
				// MSG-W0040=Adjust Start Location as smaller value than End Location. 
				label.setText(DisplayText.getText("MSG-W0040"));

				//#CM665034
				// Value set to Pager
				//#CM665035
				// Maximum qty
				pu.setMax(0);
				//#CM665036
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM665037
				// Start position 
				pu.setIndex(0);
				//#CM665038
				// Maximum qty
				pd.setMax(0);
				//#CM665039
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM665040
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}

	//#CM665041
	/**
	 * Check the range of Start Item code and End Item code. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Start Item code is larger than End Item code.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Item code <BR>
	 *      End Item code <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Start Item code is smaller than End Item code. <BR>
	 *      False if Start Item code is larger than End Item code. <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startitem Start Item code
	 * @param enditem End Item code
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Start Item code is smaller than End Item code or the same.  False : Start Item code is larger than End Item code. 
	 */
	public static boolean rangeItemCodeCheck(String startitem, String enditem, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM665042
		// Start Item code must be smaller value than End Item code. 
		if (!StringUtil.isBlank(enditem))
		{
			if (startitem.compareTo(enditem) > 0)
			{
				//#CM665043
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM665044
				// MSG-W0041=Adjust Start Item code as smaller value than End Item code. 
				label.setText(DisplayText.getText("MSG-W0041"));

				//#CM665045
				// Value set to Pager
				//#CM665046
				// Maximum qty
				pu.setMax(0);
				//#CM665047
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM665048
				// Start position 
				pu.setIndex(0);
				//#CM665049
				// Maximum qty
				pd.setMax(0);
				//#CM665050
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM665051
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}
	
	//#CM665052
	/**
	 * Check the range of Start Storage date and End Storage date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Start Storage plan date is after End Storage plan date. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Storage plan date <BR>
	 *      End Storage plan date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Start Storage plan date is before End Storage plan date.  <BR>
	 *      False if Start Storage plan date is after End Storage plan date.  <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startstoragedate Start Storage date
	 * @param endstoragedate End Storage date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True:Start Storage plan date is before End Storage plan date.  False : Start Storage plan date is after End Storage plan date. 
	 */
	public static boolean rangeStorageDateCheck(String startstoragedate, String endstoragedate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM665053
		// Start Storage plan date must be before End Storage plan date. 
		if (!StringUtil.isBlank(endstoragedate))
		{
			if (startstoragedate.compareTo(endstoragedate) > 0)
			{
				//#CM665054
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM665055
				// MSG-W0051=Make Start Storage date before End Storage date. 
				label.setText(DisplayText.getText("MSG-W0051"));

				//#CM665056
				// Value set to Pager
				//#CM665057
				// Maximum qty
				pu.setMax(0);
				//#CM665058
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM665059
				// Start position 
				pu.setIndex(0);
				//#CM665060
				// Maximum qty
				pd.setMax(0);
				//#CM665061
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM665062
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}

	//#CM665063
	/**
	 * Check the range of Start Storage plan date and End Storage plan date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Start Storage plan date is after End Storage plan date. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Storage plan date <BR>
	 *      End Storage plan date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Start Storage plan date is before End Storage plan date.  <BR>
	 *      False if Start Storage plan date is after End Storage plan date.  <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startplandate Start Storage plan date
	 * @param endplandate End Storage plan date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True:Start Storage plan date is before End Storage plan date.  False : Start Storage plan date is after End Storage plan date. 
	 */
	public static boolean rangeStoragePlanDateCheck(String startplandate, String endplandate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM665064
		// Start Storage plan date must be before End Storage plan date. 
		if (!StringUtil.isBlank(endplandate))
		{
			if (startplandate.compareTo(endplandate) > 0)
			{
				//#CM665065
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM665066
				// MSG-W0036=Make Start Storage plan date Date before End Storage plan date. 
				label.setText(DisplayText.getText("MSG-W0043"));

				//#CM665067
				// Value set to Pager
				//#CM665068
				// Maximum qty
				pu.setMax(0);
				//#CM665069
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM665070
				// Start position 
				pu.setIndex(0);
				//#CM665071
				// Maximum qty
				pd.setMax(0);
				//#CM665072
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM665073
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}

	//#CM665074
	/**
	 * Check the range of Start Receiving plan date and End Receiving plan date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false if Start Receiving plan date is after End Receiving plan date.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Receiving plan date <BR>
	 *      End Receiving plan date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True if Start Receiving plan date is before End Receiving plan date. <BR>
	 *      False if Start Receiving plan date is after End Receiving plan date. <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startplandate Start Receiving plan date
	 * @param endplandate End Receiving plan date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : Start Receiving plan date is before End Receiving plan date,  False : Start Receiving plan date is after End Receiving plan date.
	 */
	public static boolean rangeInstockPlanDateCheck(String startplandate, String endplandate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM665075
		// Start Receiving plan date must be before End Receiving plan date. 
		if (!StringUtil.isBlank(endplandate))
		{
			if (startplandate.compareTo(endplandate) > 0)
			{
				//#CM665076
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM665077
				// MSG-W0054=Make the Start Receiving expected date earlier than End Receiving expected date. 
				label.setText(DisplayText.getText("MSG-W0054"));

				//#CM665078
				// Value set to Pager
				//#CM665079
				// Maximum qty
				pu.setMax(0);
				//#CM665080
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM665081
				// Start position 
				pu.setIndex(0);
				//#CM665082
				// Maximum qty
				pd.setMax(0);
				//#CM665083
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM665084
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}

	//#CM665085
	/**
	 * Check the range of Start Receiving acceptance date and End Receiving acceptance date. <BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false in case of Start Receiving acceptance date is not earlier than End Receiving acceptance date.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Receiving acceptance date <BR>
	 *      End Receiving acceptance date <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		True in case of Start Receiving acceptance date is earlier than End Receiving acceptance date. <BR>
	 *      False in case of Start Receiving acceptance date is not earlier than End Receiving acceptance date. <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startdate Start Receiving acceptance date
	 * @param enddate End Receiving acceptance date
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True : when Start Receiving date is earlier than End Receiving date False : when Start Receiving date is not earlier than End Receiving date
	 */
	public static boolean rangeInstockDateCheck(String startdate, String enddate, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM665086
		// The Start Receiving acceptance date must be earlier than the End Receiving acceptance date.
		if (!StringUtil.isBlank(enddate))
		{
			if (startdate.compareTo(enddate) > 0)
			{
				//#CM665087
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM665088
				// MSG-W0055=Make the Start Receiving acceptance Date earlier than the End Receiving acceptance date.
				label.setText(DisplayText.getText("MSG-W0055"));

				//#CM665089
				// Value set to Pager
				//#CM665090
				// Maximum qty
				pu.setMax(0);
				//#CM665091
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM665092
				// Start position 
				pu.setIndex(0);
				//#CM665093
				// Maximum qty
				pd.setMax(0);
				//#CM665094
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM665095
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}

	//#CM665096
	/**
	 * Check the range of Start Slip  No. and End Slip  No.<BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false when Start Slip  No. is larger than End Slip  No.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start Slip No. <BR>
	 *      End Slip No. <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		If Start Slip  No. is smaller than End Slip  No. or the same, true.  <BR>
	 *      If Start Slip  No. is larger than End Slip  No., false.  <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startticketno Start Slip No.
	 * @param endticketno End Slip No.
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True:Start Slip  No. is smaller than End Slip  No. or the same.  False:Start Slip  No. is larger than End Slip  No.
	 */
	public static boolean rangeTicketNoCheck(String startticketno, String endticketno, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM665097
		// Start Item code must be smaller value than End Item code. 
		if (!StringUtil.isBlank(endticketno))
		{
			if (startticketno.compareTo(endticketno) > 0)
			{
				//#CM665098
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM665099
				// MSG-W0056=Adjust Start slip No. to smaller value than end slip No.
				label.setText(DisplayText.getText("MSG-W0056"));

				//#CM665100
				// Value set to Pager
				//#CM665101
				// Maximum qty
				pu.setMax(0);
				//#CM665102
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM665103
				// Start position 
				pu.setIndex(0);
				//#CM665104
				// Maximum qty
				pd.setMax(0);
				//#CM665105
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM665106
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}

	//#CM665107
	/**
	 * Check the range of Start order  No. and End order  No.<BR>
	 * <BR>
	 * Outline:Set the value of string type in Parameter. 
	 * Return false when Start order  No. is larger than End order  No.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Start order No. <BR>
	 *      End order No. <BR>
	 *    </DIR>
	 * <BR>
	 *    [Return data]<BR>
	 *    <DIR>
	 * 		If Start order  No. is smaller than End order  No. or the same, true.  <BR>
	 *      If Start order  No. is larger than End order  No., false.  <BR>
	 *    </DIR>
	 * </DIR>
	 * @param startorderno Start order No.
	 * @param endorderno End order No.
	 * @param listcell List cell
	 * @param pu Page up
	 * @param pd Page down
	 * @param label Label
	 * @throws Exception Reports on all exceptions.  
	 * @return True: Start order No. is smaller than end order No. or the same.  False: Start order No. is larger than end order No.
	 */
	public static boolean rangeOrderNoCheck(String startorderno, String endorderno, ListCell listcell, Pager pu, Pager pd, Label label) throws Exception
	{
		//#CM665108
		// Start order No. must be smaller value than end order No.. 
		if (!StringUtil.isBlank(endorderno))
		{
			if (startorderno.compareTo(endorderno) > 0)
			{
				//#CM665109
				// Conceal the header. 
				listcell.setVisible(false);
				//#CM665110
				// MSG-W0041=Adjust Start order No. as smaller value than the end item code. 
				label.setText(DisplayText.getText("MSG-W0041"));

				//#CM665111
				// Value set to Pager
				//#CM665112
				// Maximum qty
				pu.setMax(0);
				//#CM665113
				// 1PageDisplay qty
				pu.setPage(0);
				//#CM665114
				// Start position 
				pu.setIndex(0);
				//#CM665115
				// Maximum qty
				pd.setMax(0);
				//#CM665116
				// 1PageDisplay qty
				pd.setPage(0);
				//#CM665117
				// Start position 
				pd.setIndex(0);

				return false;
			}
		}
		return true;
	}
	
	//#CM665118
	/**
	 * The numeric usage of the system package and check Position. <BR>
	 * <BR>
	 * Outline:An effective check box, numeric usage, the maximum digit, and Position are received. 
	 * When the effective check box is checked,<BR>
	 * Do the Mandatory input check, Numeric usage check, maximum and minimum number check.<BR>
	 * Throw out ValidateException when caught in the check. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *      Effective CheckBox* <BR>
	 *      NumberTextBox for numbers* <BR>
	 *      Maximum digits for Label* <BR>
	 *      Position NumberTextBox* <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param name Label control
	 * @param chk Checkbox control (effective) 
	 * @param figure Numeric text box control (for numbers) 
	 * @param maxLen Label control (maximum digit) 
	 * @param pst Numeric text box control (position) 
	 * @throws ValidateException
	 */
	public static void checkDefine(Label name, CheckBox chk, NumberTextBox figure, Label maxLen, NumberTextBox pst) throws ValidateException
	{
		boolean check = chk.getChecked();
		
		try
		{
		    figure.validate(check);
		}
		catch(ValidateException e)
		{
			throw new ValidateException("6013264", DisplayText.getText(name.getResourceKey()));
		}
		
		try
		{
		    pst.validate(check);
		}
		catch(ValidateException e)
		{
			throw new ValidateException("6013265", DisplayText.getText(name.getResourceKey()));	    
		}
		
		if (check)
		{
			//#CM665119
			// Usage number check
			if(figure.getInt() <= 0)
			{	
				throw new ValidateException("6023057", DisplayText.getText(name.getResourceKey()));
			}
			
			if(Integer.parseInt(maxLen.getText()) < figure.getInt())
			{
				throw new ValidateException("6023097", DisplayText.getText(name.getResourceKey()));
			}
			
		}
		else
		{
			pst.setText("");
		}
	}
	
	//#CM665120
	/**
	 * Do the input check of the text control passed with Parameter. <BR>
	 * 
	 * @param ctrl Text Control
	 * @return True when input is correct, False when there is a mistake
	 */
	public boolean checkContainNgText(FreeTextBox ctrl)
	{
		return checkContainNgText(ctrl, 0);
	}
	//#CM665121
	/**
	 * Do the input check of the text control passed with Parameter. <BR>
	 * Return true when there is no input in the text. 
	 * <BR>
	 * <Content of check><BR>
	 *  - The first character should not be normal width space. <BR>
	 * 
	 * @param ctrl Text Control
	 * @param rowNo Row No.
	 * @return True when input is correct, False when there is a mistake
	 */
	public boolean checkContainNgText(FreeTextBox ctrl, int rowNo)
	{
		String str = ctrl.getText();
		
		if (str.length() == 0)
		{
			return true;
		}

		if (str.charAt(0) == ' ')
		{
//#CM665122
// TODO OK because using it by the next version though not recommended now becomes possible even if it does not correct it. 
			String ctrlName = DisplayText.getText(ctrl.getErrMsgParamKey());
			if (rowNo == 0)
			{
				//#CM665123
				// 6023497=The first character of {0} is space. Space cannot be input as the first character.
				wMessage = "6023497" + wDelim + ctrlName;
			}
			else
			{
				//#CM665124
				// 6023498=The first character of No. {0} and {1} is space. Space cannot be input as the first character.
				wMessage = "6023498" + wDelim + rowNo + wDelim + ctrlName;
			}

			return false;
		}
		
		return true;
	}

	//#CM665125
	/**
	 * Do the input check of the text control passed with Parameter. <BR>
	 * Return true when there is no input in the text. 
	 * <BR>
	 * <Content of check><BR>
	 *  - The first character should not be normal width space. <BR>
	 * 
	 * @param ctrl Text Control
	 * @return True when input is correct, False when there is a mistake
	 */
	public boolean checkContainNgText(TextArea ctrl)
	{
		String str = ctrl.getText();
		
		if (str.length() == 0)
		{
			return true;
		}

		if (str.charAt(0) == ' ')
		{
//#CM665126
// TODO OK because using it by the next version though not recommended now becomes possible even if it does not correct it. 
			String ctrlName = DisplayText.getText(ctrl.getErrMsgParamKey());
			//#CM665127
			// 6023497=The first character of {0} is space. Space cannot be input as the first character.
			wMessage = "6023497" + wDelim + ctrlName;

			return false;
		}
		
		return true;
	}

	//#CM665128
	/**
	 * Do the input check of the text control passed with Parameter. <BR>
	 * Return true when there is no input in the text. 
	 * <BR>
	 * <Content of check><BR>
	 *  - The first character should not be normal width space. <BR>
	 * 
	 * @param str Input value
	 * @param rowNo Row No.
	 * @param msg Resource key of item name
	 * @return True when input is correct, False when there is a mistake
	 */
	public boolean checkContainNgText(String str, int rowNo,  String msg)
	{		
		if (str.length() == 0)
		{
			return true;
		}

		if (str.charAt(0) == ' ')
		{

			if (rowNo == 0)
			{
				//#CM665129
				// 6023497=The first character of {0} is space. Space cannot be input as the first character.
				wMessage = "6023497" + wDelim +  DisplayText.getText( msg );
			}
			else
			{
				//#CM665130
				// 6023498=The first character of No. {0} and {1} is space. Space cannot be input as the first character.
				wMessage = "6023498" + wDelim + rowNo + wDelim + DisplayText.getText( msg ); 
			}
			return false;
		}
		return true;
	}
	
	//#CM665131
	/**
	 * When NG in the check done in this class
	 * Acquire Error message.
	 * 
	 * @return Error message
	 */
	public String getMessage()
	{
		return wMessage;
	}
	
}
