// $Id: Classification.java,v 1.2 2006/10/30 02:33:26 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49457
/**<en>
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 </en>*/

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

//#CM49458
/**<en>
 * This class controls and operates the information of item classifications of 
 * the article name master.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/08/05</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:26 $
 * @author  $Author: suresh $
 </en>*/
public class Classification extends ToolEntity
{
	//#CM49459
	// Class fields --------------------------------------------------
	//#CM49460
	/**<en>
	 * Field of permission for the mix-loading (mix-loading accepted)
	 </en>*/
	public static final int POSSIBLE = 1 ;
	
	//#CM49461
	/**<en>
	 * Field of permission for the mix-loading (mix-loading not accepted)
	 </en>*/
	public static final int IMPOSSIBLE = 2 ;

	//#CM49462
	// Class variables -----------------------------------------------
	//#CM49463
	/**<en>
	 * classification ID
	 </en>*/
	protected int wClassificationId = 0 ;

	//#CM49464
	/**<en>
	 * name of the classification
	 </en>*/
	protected String wName = "" ;

	//#CM49465
	/**<en>
	 * Flag of accepted mix-loading
	 </en>*/
	protected int wForbiddenMix = POSSIBLE ;

	//#CM49466
	// Class method --------------------------------------------------
	//#CM49467
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:26 $") ;
	}

	//#CM49468
	// Constructors --------------------------------------------------
	//#CM49469
	/**<en>
	 * Construct a new <CODE>Classification</CODE>.
	 </en>*/
	public Classification()
	{
	}

	//#CM49470
	/**<en>
	 * Construct a new <CODE>Classification</CODE>.
	 * This will be used when setting the search result in vector().
	 * @param classid :classification ID
	 * @param name    :name of the classification
	 * @param mix     :flag of accepted mix-loading
	 </en>*/
	public Classification(int		classid, 
						  String	name,
						  int 		mix
			    		 )
	{
		//#CM49471
		//<en> Set as an instance variable.</en>
		setClassificationId(classid);
		setName(name);
		setForbiddenMix(mix);
	}

	//#CM49472
	// Public methods ------------------------------------------------

	//#CM49473
	/**<en>
	 * Set teh valud of classification ID.
	 * @param classid :classification ID to set
	 </en>*/
	public void setClassificationId(int classid)
	{
		wClassificationId = classid ;
	}

	//#CM49474
	/**<en>
	 * Retrieve the classification ID.
	 * @return :classification ID
	 </en>*/
	public int getClassificationId()
	{
		return wClassificationId ;
	}

	//#CM49475
	/**<en>
	 * Set teh value of name of the classification.
	 * @param name :name of the classification to set
	 </en>*/
	public void setName(String name) 
	{
		wName = name ;
	}

	//#CM49476
	/**<en>
	 * Retrieve the name of the classification.
	 * @return :name of the classification
	 </en>*/
	public String getName()
	{
		return wName ;
	}

	//#CM49477
	/**<en>
	 * Set the value of flag of accepted mix-loading.
	 * @param mix :flag of accepted mix-loading to set
	 </en>*/
	public void setForbiddenMix(int mix)
	{
		wForbiddenMix = mix ;
	}

	//#CM49478
	/**<en>
	 * Retrieve the flag of accepted mix-loading.
	 * @return flag of accepted mix-loading
	 </en>*/
	public int getForbiddenMix()
	{
		return wForbiddenMix ;
	}

	//#CM49479
	// Package methods -----------------------------------------------

	//#CM49480
	// Protected methods ---------------------------------------------

	//#CM49481
	// Private methods -----------------------------------------------

}
//#CM49482
//end of class

